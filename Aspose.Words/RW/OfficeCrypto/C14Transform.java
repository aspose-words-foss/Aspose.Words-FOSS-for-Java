// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/03/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.AnyXmlReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.AnyXmlBuilder;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.AnyXmlTextHandlingConsts;
import java.util.ArrayList;
import com.aspose.ms.System.Xml.XmlNodeType;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import java.util.Collections;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.Comparator;


/// <summary>
/// Implements C14N transform. See http://www.w3.org/TR/2001/REC-xml-c14n-20010315.
/// </summary>
class C14Transform extends Transform
{
    protected /*override*/ void read(AnyXmlReader reader)
    {
        // Nothing to read
    }

    /// <summary>
    /// Applies transformation to the specified stream.
    /// </summary>
    /*override*/ MemoryStream apply(Stream xml) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        if (xml.getLength() == 0)
            return stream;

        AnyXmlBuilder builder = new AnyXmlBuilder(stream, new UTF8Encoding(false), false);
        AnyXmlReader reader = new AnyXmlReader(xml, false);
        String curNodeName = reader.getLocalName();
        while (reader.readChildWithTextValues(curNodeName, AnyXmlTextHandlingConsts.ALL))
            processNode(reader, builder, null);

        builder.flush();
        return stream;
    }

    /*override*/ void write(AnyXmlBuilder writer)
    {
        writer.startElement("Transform");
        writer.writeAttributeString("Algorithm", ALGORITHM);
        writer.endElement("Transform");
    }

    /// <summary>
    /// Propagates specified namespace to topmost processing element nodes.
    /// </summary>
    void propagateNamespace(String ns)
    {
        mPropagatingNamespace = ns;
    }

    /// <summary>
    /// Writes canonical form of the XML node to a stream.
    /// </summary>
    private boolean processNode(AnyXmlReader reader, AnyXmlBuilder builder,
        ArrayList<XmlAttribute> parentNamespaces)
    {
        boolean hasData = true;
        /*XmlNodeType*/int nodeType = reader.getNodeType();
        switch (nodeType)
        {
            case XmlNodeType.ELEMENT:
                processElementNode(reader, builder, parentNamespaces);
                break;
            case XmlNodeType.TEXT:
                hasData = processTextNode(reader, builder);
                break;
            case XmlNodeType.SIGNIFICANT_WHITESPACE:
            case XmlNodeType.WHITESPACE:
                hasData = processWhitespaceNode(reader, builder);
                break;
            case XmlNodeType.PROCESSING_INSTRUCTION:
                processPiNode(reader, builder);
                break;
            case XmlNodeType.CDATA:
                hasData = processCDataNode(reader, builder);
                break;
            case XmlNodeType.ENTITY_REFERENCE:
                hasData = processEntityReferenceNode(reader, builder, parentNamespaces);
                break;
            default:
                break;
        }

        // Set the type of previously processed node, which has non-zero data length.
        if (hasData)
            mPrevNodeType = nodeType;

        return hasData;
    }

    /// <summary>
    /// Writes canonical form of the XML element node to a stream.
    /// </summary>
    private void processElementNode(AnyXmlReader reader, AnyXmlBuilder builder,
        ArrayList<XmlAttribute> parentNamespaces)
    {
        Debug.assertTrue(reader.getNodeType() == XmlNodeType.ELEMENT);

        if (mPrevNodeType == XmlNodeType.PROCESSING_INSTRUCTION)
            builder.writeRaw("\n");

        String elementName = reader.getName();
        builder.startElement(elementName);

        String elementLocalName = reader.getLocalName();

        // Process attributes (namespaces and regular attributes).
        ArrayList<XmlAttribute> namespaces = processAttributes(reader, builder, parentNamespaces);

        // We need all namespaces starting from the root node up to the current node.
        if (parentNamespaces != null)
            msArrayList.addRange(namespaces, parentNamespaces);

        // Process nested element nodes.
        while (reader.readChildWithTextValues(elementLocalName, AnyXmlTextHandlingConsts.ALL))
            processNode(reader, builder, namespaces);

        builder.fullEndElement(elementName);
    }

    /// <summary>
    /// Writes canonical form for attributes of the specified element node to a stream.
    /// </summary>
    private ArrayList<XmlAttribute> processAttributes(AnyXmlReader reader, AnyXmlBuilder builder,
        ArrayList<XmlAttribute> parentNamespaces)
    {
        // Collect attributes and namespaces separately to order them according to spec - 2.2 Document Order.
        ArrayList<XmlAttribute> namespaces = new ArrayList<XmlAttribute>();
        ArrayList<XmlAttribute> attributes = new ArrayList<XmlAttribute>();

        XmlAttribute emptyNamespace = null;
        while (reader.moveToNextAttribute(false))
        {
            XmlAttribute xmlAttribute = new XmlAttribute(reader);
            if (xmlAttribute.isNamespace())
            {
                // IN. It is not entirely clear, when Word removes empty xmlns="". Spec says, that:
                // "The latter condition eliminates unnecessary occurrences of xmlns="" in the canonical form..."
                // But actually, it looks like when such namespace is a single namespace of element (no any other
                // namespaces defined for that element node), then Word preserves it.
                if (!msString.isNullOrEmpty(xmlAttribute.getValue()))
                    msArrayList.add(namespaces, xmlAttribute);
                else
                    emptyNamespace = xmlAttribute;
            }
            else
            {
                msArrayList.add(attributes, xmlAttribute);
            }
        }

        // Add propagated namespace to the topmost element nodes, if specified.
        if ((mPropagatingNamespace != null) && (parentNamespaces == null))
        {
            XmlAttribute propagatedNamespace = new XmlAttribute("xmlns", mPropagatingNamespace);
            if (!propagatedNamespace.exists(namespaces))
                msArrayList.add(namespaces, propagatedNamespace);
        }

        // See 2.3 Processing Model - Namespace Nodes.
        removeInheritedNamespaces(namespaces, parentNamespaces);

        // See explanation above.
        if ((emptyNamespace != null) && (namespaces.size() == 0))
            msArrayList.add(namespaces, emptyNamespace);

        XmlAttributesComparer comparer = new XmlAttributesComparer();
        Collections.sort(namespaces, comparer);
        writeAttributes(namespaces, builder);

        Collections.sort(attributes, comparer);
        writeAttributes(attributes, builder);

        return namespaces;
    }

    /// <summary>
    /// Writes canonical form of the XML text node to a stream.
    /// </summary>
    private static boolean processTextNode(AnyXmlReader reader, AnyXmlBuilder builder)
    {
        Debug.assertTrue(reader.getNodeType() == XmlNodeType.TEXT);

        return writeFilteredString(builder, reader.getValue());
    }

    /// <summary>
    /// Writes canonical form of the XML whitespace node to a stream.
    /// </summary>
    private boolean processWhitespaceNode(AnyXmlReader reader, AnyXmlBuilder builder)
    {
        Debug.assertTrue(reader.getNodeType() == XmlNodeType.WHITESPACE || reader.getNodeType() == XmlNodeType.SIGNIFICANT_WHITESPACE);

        if (reader.getUnderlyingReader().getDepth() == 0)
            return false;

        if (mPrevNodeType == XmlNodeType.PROCESSING_INSTRUCTION)
            builder.writeRaw("\n");

        return writeFilteredString(builder, reader.getValue());
    }

    /// <summary>
    /// Writes canonical form of the XML processing instruction node to a stream.
    /// </summary>
    private void processPiNode(AnyXmlReader reader, AnyXmlBuilder builder)
    {
        Debug.assertTrue(reader.getNodeType() == XmlNodeType.PROCESSING_INSTRUCTION);

        if (mPrevNodeType != XmlNodeType.DOCUMENT)
            builder.writeRaw("\n");

        // MSDN at https://msdn.microsoft.com/en-us/library/w36h547w(v=vs.71).aspx says, that if data
        // is empty string, then it writes a ProcessingInstruction with no data content, for example <?name?>.
        // But actually, it writes an extra space before closing '?>' element in such case.
        // To avoid that, I have to write instruction name as a raw string.
        String data = canonicalizeText(reader.getValue());
        if (StringUtil.hasChars(data))
            builder.writeProcessingInstruction(reader.getLocalName(), data);
        else
            builder.writeRaw(msString.format("<?{0}?>", reader.getLocalName()));
    }

    /// <summary>
    /// Writes canonical form of the XML CDATA node to a stream.
    /// </summary>
    private boolean processCDataNode(AnyXmlReader reader, AnyXmlBuilder builder)
    {
        Debug.assertTrue(reader.getNodeType() == XmlNodeType.CDATA);

        String text = canonicalizeText(reader.getValue());
        if (text.length() > 0)
        {
            if (mPrevNodeType == XmlNodeType.PROCESSING_INSTRUCTION)
                builder.writeRaw("\n");

            builder.writeRaw(text);
            return true;
        }

        return false;
    }

    /// <summary>
    /// Writes canonical form of the XML entity reference node to a stream.
    /// </summary>
    private boolean processEntityReferenceNode(AnyXmlReader reader, AnyXmlBuilder builder,
        ArrayList<XmlAttribute> parentNamespaces)
    {
        Debug.assertTrue(reader.getNodeType() == XmlNodeType.ENTITY_REFERENCE);

        String value = reader.getValue();
        if (value != null)
        {
            builder.writeRaw(value);
            return true;
        }

        String entityName = reader.getLocalName();
        boolean isEmpty = true;
        while(reader.readChildWithTextValues(entityName, AnyXmlTextHandlingConsts.ALL))
            if (processNode(reader, builder, parentNamespaces))
                isEmpty = false;

        return isEmpty;
    }

    /// <summary>
    /// Filters out invalid XML characters and writes specified value. Replaces "\r\n" with "\n".
    /// </summary>
    private static boolean writeFilteredString(AnyXmlBuilder builder, String value)
    {
        String text = builder.filterInvalidXmlChars(value.replace("\r\n", "\n"));
        builder.writeString(text);

        return text.length() > 0;
    }

    /// <summary>
    /// Canonicalizes the string as described in W3C Canonical XML specification (see https://www.w3.org/TR/2001/REC-xml-c14n-20010315).
    /// </summary>
    private static String canonicalizeText(String text)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            switch (c)
            {
                case '&':
                    msStringBuilder.append(sb, "&amp;");
                    break;
                case '<':
                    msStringBuilder.append(sb, "&lt;");
                    break;
                case '>':
                    msStringBuilder.append(sb, "&gt;");
                    break;
                case '\r':
                {
                    // Convert "\r\n" to "\n".
                    if (((i + 1) == text.length()) || (text.charAt(i + 1) != '\n'))
                        sb.append(c);
                    break;
                }
                default:
                    sb.append(c);
                    break;
            }
        }

        return sb.toString();
    }

    /// <summary>
    /// Removes equal to parent (inherited) namespaces.
    /// </summary>
    private static void removeInheritedNamespaces(ArrayList<XmlAttribute> namespaces,
        ArrayList<XmlAttribute> parentNamespaces)
    {
        if (parentNamespaces == null)
            return;

        for (XmlAttribute parentNamespace : parentNamespaces)
        {
            removeEqualNamespaces(parentNamespace, namespaces);

            if (namespaces.size() == 0)
                break;
        }
    }

    /// <summary>
    /// Removes namespaces equal to the specified reference namespace.
    /// </summary>
    private static void removeEqualNamespaces(XmlAttribute referenceNamespace, ArrayList<XmlAttribute> namespaces)
    {
        Debug.assertTrue(referenceNamespace.isNamespace());

        for (int i = namespaces.size() - 1; i >= 0; i--)
        {
            XmlAttribute curNamespace = namespaces.get(i);
            if (curNamespace.equals(referenceNamespace))
                namespaces.remove(i);
        }
    }

    /// <summary>
    /// Writes collection of attributes to a stream.
    /// </summary>
    private static void writeAttributes(ArrayList<XmlAttribute> attributes, AnyXmlBuilder builder)
    {
        for (XmlAttribute attribute : attributes)
            builder.writeAttributeString(attribute.getName(), attribute.getValue());
    }

    static final String ALGORITHM = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";

    /// <summary>
    /// The type of previously processed node, which has non-zero data length.
    /// It means that something had to be written to the output stream for that node.
    /// </summary>
    private /*XmlNodeType*/int mPrevNodeType = XmlNodeType.DOCUMENT;

    /// <summary>
    /// A namespace that will be propagated to topmost processing element nodes.
    /// </summary>
    private String mPropagatingNamespace;
}

/// <summary>
/// The helper class for comparing XML attribute nodes
/// in accordance with W3C Canonical XML specification.
/// </summary>
class XmlAttributesComparer implements Comparator<XmlAttribute>
{
    public int compare(XmlAttribute x, XmlAttribute y)
    {
        return x.compareTo(y);
    }
}

/// <summary>
/// Represents XML attribute.
/// </summary>
class XmlAttribute
{
    /// <summary>
    /// Ctor.
    /// </summary>
    XmlAttribute(AnyXmlReader reader)
    {
        mName = reader.getName();
        mValue = reader.getValue();
        mNamespaceUri = reader.getNamespaceURI();
        mPrefix = reader.getPrefix();
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    XmlAttribute(String name, String value)
    {
        mName = name;
        mValue = value;
    }

    /// <summary>
    /// Returns true, if there is equal to this instance XmlAttribute exists in a specified array.
    /// </summary>
    boolean exists(Iterable<XmlAttribute> items)
    {
        for (XmlAttribute attr : items)
            if (equals(attr))
                return true;

        return false;
    }

    /// <summary>
    /// Returns true, if this instance is equal to a specified XML attribute.
    /// </summary>
    boolean equals(XmlAttribute otherXmlAttribute)
    {
        return compareTo(otherXmlAttribute) == 0;
    }

    /// <summary>
    /// Compares this instance with a specified XmlAttribute object and indicates whether this instance
    /// precedes, follows, or appears in the same position in the sort order as the specified XmlAttribute.
    /// </summary>
    int compareTo(XmlAttribute otherXmlAttribute)
    {
        return isNamespace()
            ? compareAsNamespace(otherXmlAttribute)
            : compareAsAttribute(otherXmlAttribute);
    }

    /// <summary>
    /// <para>
    /// Compares this instance with a specified XmlAttribute object and indicates whether this instance
    /// precedes, follows, or appears in the same position in the sort order as the specified XmlAttribute.
    /// </para>
    /// <para>Uses rules for comparing namespaces in accordance with W3C Canonical XML specification.</para>
    /// </summary>
    /// <remarks>See 2.2, 2.3 and also 3. - Examples of specification.</remarks>
    private int compareAsNamespace(XmlAttribute otherNamespace)
    {
        int namesCompare = msString.compareOrdinal(getName(), otherNamespace.getName());
        return (namesCompare != 0)
            ? namesCompare
            : msString.compareOrdinal(getValue(), otherNamespace.getValue());
    }

    /// <summary>
    /// <para>
    /// Compares this instance with a specified XmlAttribute object and indicates whether this instance
    /// precedes, follows, or appears in the same position in the sort order as the specified XmlAttribute.
    /// </para>
    /// <para>Uses rules for comparing regular attributes (not namespaces) in accordance with W3C Canonical XML specification.</para>
    /// </summary>
    /// <remarks>See 2.2, 2.3 and also 3. - Examples of specification.</remarks>
    private int compareAsAttribute(XmlAttribute otherAttr)
    {
        if (msString.equals(getNamespaceUri(), otherAttr.getNamespaceUri()))
            return msString.compareOrdinal(getName(), otherAttr.getName());

        if (StringUtil.hasChars(getNamespaceUri()))
        {
            if (!StringUtil.hasChars(otherAttr.getNamespaceUri()))
                return 1;
            else
                return msString.compareOrdinal(getNamespaceUri(), otherAttr.getNamespaceUri());
        }
        else
        {
            if (StringUtil.hasChars(otherAttr.getNamespaceUri()))
                return -1;
        }

        return 0;
    }

    /// <summary>
    /// Attribute name.
    /// </summary>
    String getName() { return mName; }

    /// <summary>
    /// Attribute value.
    /// </summary>
    String getValue() { return mValue; }

    /// <summary>
    /// Attribute namespace URI.
    /// </summary>
    String getNamespaceUri() { return mNamespaceUri; }

    /// <summary>
    /// Returns true if this attribute is a namespace.
    /// </summary>
    boolean isNamespace()
    {
        final String XMLNS = "xmlns";
        return (XMLNS.equals(mPrefix)) || (XMLNS.equals(mName));
    }

    private /*final*/ String mName;
    private /*final*/ String mValue;
    private /*final*/ String mNamespaceUri;
    private /*final*/ String mPrefix;
}

