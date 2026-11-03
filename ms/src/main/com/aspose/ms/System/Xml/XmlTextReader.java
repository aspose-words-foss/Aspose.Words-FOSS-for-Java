// ported 30/07/2008 by Konstantin Sidorenko

package com.aspose.ms.System.Xml;

import com.aspose.ms.System.Collections.SortedList;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.IO.NetInputStream;
import com.aspose.ms.java.util.JavaUtil;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.xml.XmlUtils;
import com.ctc.wstx.dtd.DTDSubset;
import com.ctc.wstx.sr.InputElementStack;
import com.ctc.wstx.sr.ValidatingStreamReader;
import com.ctc.wstx.stax.WstxInputFactory;

import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copycat of System.Xml.XmlTextReader.
 *
 * Only methods used by AW are ported.
 *
 * 1) Woodsotx implementation of StAX parser used to do real xml reading.
 * 2) mCurrentEvent local variable added to emulate .net read() by java next().
 * 3) mAttributeIndex and mAttributeCount local vars added to emulate .net behaviour
 * of walking through Attributes.
 */
public class XmlTextReader extends XmlReader
{
    // Creates an XML text reader that is like in .NET - supports namespaces and parses DTDs.
    public XmlTextReader(Stream stream)
    {
        this(stream, false, false);
    }

    // Creates an XML text reader that is like in .NET - supports namespaces and parses DTDs with custom XMLResolver.
    public XmlTextReader(Stream stream, XMLResolver resolver)
    {
        this(stream, resolver,null, false, false);
    }

    /**
     * The input parameter can be either a Stream or a String.
     *
     * WORDSJAVA-21848 - Reading VML shape library and DML shapes reads with ignoring namespaces
     * The isIgnoreNamespaces parameter is currently used for VML ShapeTypes read. RK to look at this.
     *
     * DTDs can slow things down so you should normally specify to ignore DTDs.
     */
    public XmlTextReader(Object input, boolean isIgnoreNamespaces, boolean isIgnoreDtd)
    {
        this(input, null, null, isIgnoreDtd, isIgnoreNamespaces);
    }

    public XmlTextReader(Object input, Map<String, String> namespaces, boolean isIgnoreDtd)
    {
        this(input, null, namespaces, isIgnoreDtd, namespaces == null ? true : false);
    }

    /**
     * Create XML reader with predefined namespaces
     *
     * @param input The input parameter can be either a Stream or a String
     */
    private XmlTextReader(Object input, XMLResolver resolver, Map<String, String> namespaces, boolean isIgnoreDtd, boolean isIgnoreNamespaces)
    {
        if (input == null)
            throw new NullPointerException("input");

        //preserve source stream or string so readOuter/InnerXml() can get from it raw text
        if (input instanceof Stream)
            mStream = (Stream) input;
        else if (input instanceof String)
            mString = (String) input;
        else
            throw new IllegalStateException("Can only create an XML reader for a stream or a string.");

        mIsIgnoreNamespaces = isIgnoreNamespaces;
        mIsIgnoreDtd = isIgnoreDtd && resolver == null;

        try
        {
            WstxInputFactory factory = XmlUtils.newWstxInputFactory(isIgnoreNamespaces, isIgnoreDtd, resolver);

            if (mStream != null)
                mReader = (ValidatingStreamReader) factory.createXMLStreamReader(new NetInputStream(mStream));
            else
                mReader = (ValidatingStreamReader) factory.createXMLStreamReader(new java.io.StringReader(mString));

            if (namespaces != null)
            {
                InputElementStack stack = mReader.getInputElementStack();
                if (stack != null)
                {
                    for (Object entry : namespaces.entrySet())
                    {
                        Map.Entry entryObject = ((Map.Entry) entry);
                        // Fixing issue with null mCurrElement in stack variable (see WORDSJAVA-2708)
                        String prefix = ((String)entryObject.getKey()).intern().length() == 0 ? " " : ((String)entryObject.getKey()).intern();
                        stack.addNsBinding(prefix, (String) entryObject.getValue());
                    }
                }
            }

            resetAttributes();
        }
        catch (XMLStreamException e)
        {
            // XMLStreamException is obfuscated as part of external lib. So we have to provide clear
            // point to the cause instead of obfuscated symbols:)
            wrapXmlStreamException(e);
        }
    }

    // Read the next node in the input stream.
    public boolean read()
    {
        resetAttributes();
        try
        {
            if (mReader.hasNext())
            {
                mCurrentEvent = mReader.next();

                // FIX WORDSJAVA-916, WORDSJAVA-2738:
                // StAX parser recognizes both new lines and significant whitespaces as text.
                // Fix Xml Node Type to match .NET behavior.
                if(mCurrentEvent == XmlNodeType.TEXT)
                {
                    String value = getValue();

                    if(JavaUtil.isNewLine(value))
                        mCurrentEvent = XmlNodeType.WHITESPACE;
                    else if(JavaUtil.isWhiteSpace(value))
                        mCurrentEvent = XmlNodeType.SIGNIFICANT_WHITESPACE;
                }

                return true;
            }
        }
        catch (XMLStreamException e)
        {
            String message = e.getMessage();

            // WORDSJAVA-1719: Don't read if the document's end was reached (Unexpected EOF)
            if (!message.startsWith(gNativeUnexpectedEOFExceptionMsg))
            {
                //WORDSJAVA-1437: Can't read Docx with DML that contains tag with undeclared namespace.
                if (message.startsWith(gNativeUndeclaredNamespaceExceptionMsg) &&
                        lookupNamespace(getPrefix()) == null)
                {
                    readToDocumentEnd();
                }
            }

            wrapXmlStreamException(e);
        }

        return false;
    }

    // Read the next node in the input stream.
    public boolean read2()
    {
        resetAttributes();
        try
        {
            if (mReader.hasNext())
            {
                mCurrentEvent = mReader.next();

                // WORDSJAVA-944: Charts in Docx xml markup: xml attribute order changed.
                // HACK: We assume that all whitespaces after end element are ignorable and can be skipped.
                if (mCurrentEvent == XmlNodeType.TEXT && JavaUtil.isWhiteSpace(getValue()))
                    mCurrentEvent = XmlNodeType.WHITESPACE;

                return true;
            }
        }
        catch (XMLStreamException e)
        {
            wrapXmlStreamException(e);
        }

        return false;
    }

    // Read the contents of an element or text node as a string.
    public String readString()
    {
        //JAVA: WORDSJAVA-8103 FIXED.
        //Third-parity-generated doc contains <w:t> (text-only in the model) tag that doesn't contain any text
        //but contains another tags instead. Java StAX throws in that case, .Net XmlTextReaderImpl - returns empty sting.
        StringBuilder text = new StringBuilder(msString.Empty);
        try
        {
            if (mCurrentEvent == START_ELEMENT)
            {
                // WORDSJAVA-1956 mReader.getElementText() throws org.codehaus.stax2.typed.TypedXMLStreamException:
                // Element content can not contain child START_ELEMENT when using Typed Access methods.

                if (mReader.isEmptyElement())
                    return msString.Empty;
                else if (!read())
                    return msString.Empty;

                while (isTextualNode(mReader.getEventType()))
                {
                    text.append(mReader.getText());
                    if (!read())
                        break;
                }
            }
            else if (mCurrentEvent == END_ELEMENT)
                text.append(msString.Empty);
            else if (isTextualNode(mCurrentEvent))
                text.append(mReader.getText());
            else
                text.append(msString.Empty);

            //getElementText() can change current event to END_ELEMENT
            mCurrentEvent = mReader.getEventType(); // todo 2 rk I want to delete mCurrentEvent completely.
        }
        catch (XMLStreamException e)
        {
            Log.warn(e);

            text.append(msString.Empty);
        }

        return text.toString();
    }

    private static boolean isTextualNode(int eventType)
    {
        return ((1 << eventType) & MASK_GET_TEXT) != 0;
    }

    /**
     * Used only by readString() (see above).
     * This mask covers all types for which basic
     * mReader.getText() method can be called.
     */
    private static final int MASK_GET_TEXT = (1 << CHARACTERS) | (1 << CDATA) | (1 << SPACE) |
            (1 << COMMENT) | (1 << DTD) | (1 << ENTITY_REFERENCE);

    // Move to the element that owns the current attribute.
    public boolean moveToElement()
    {
        if (mAttributeIndex >= 0)
        {
            resetAttributes();
            return true;
        }

        return false;
    }

    /**
     * Move to the first attribute owned by the current element. Cursor should be positioned on
     * START_ELEMENT if you want to get attributes.
     *
     * NOTE: If mIsIgnoreWhitespace == true, StAX standard does count namespace declarations ('xmlns' attributes) as attributes.
     */
    public boolean moveToFirstAttribute()
    {
        //StAX throws illegal state if improperly positioned but .Net just return false.
        if (mCurrentEvent != START_ELEMENT)
            return false;

        if (mIsIgnoreNamespaces)
        {
            mAttributeCount = mReader.getAttributeCount();
            if (mAttributeCount > 0)
            {
                mAttributeIndex = 0;
                return true;
            }
            else
                return false;
        }
        else
        {
            mNsCount = mReader.getNamespaceCount();
            mAttributeCount = mReader.getAttributeCount();

            if (mNsCount + mAttributeCount > 0)
            {
                mAttributeIndex = 0;
                return true;
            }
            else
                return false;
        }
    }

    // Move to the next attribute owned by the current element.
    public boolean moveToNextAttribute()
    {
        //first run
        if (mAttributeIndex < 0)
            return moveToFirstAttribute();

        //@todo 1 sk check what .net does with cursor in end of cycle.
        if (mIsIgnoreNamespaces)
        {
            if ((mAttributeIndex + 1) >= mAttributeCount)
                return false;
        }
        else
        {
            if ((mAttributeIndex + 1) >= mNsCount + mAttributeCount)
                return false;
        }

        mAttributeIndex++;
        return true;
    }

    //JAVA-moved here from XmlReader.
    // Determine if the current node has attributes.
    public boolean hasAttributes()
    {
        //StAX throws illegal state if improperly positioned but .Net just return false.
        if (mCurrentEvent != START_ELEMENT)
            return false;

        if (mIsIgnoreNamespaces)
            return mReader.getAttributeCount() > 0;
        else
            return (mReader.getNamespaceCount() + mReader.getAttributeCount()) > 0;
    }

    //@todo 2 sk this approach doesn't work - rework as readOuterXml()
    public String readInnerXml()
    {
        //@todo 3 sk read inner xml for attribute nodes.
        if (mCurrentEvent != START_ELEMENT)
            return "";

        String text = "";
        try
        {
            long startInnerText = mReader.getEndingCharOffset();
            mReader.skipElement();
            mCurrentEvent = mReader.getEventType();
            long endInnerText = mReader.getStartingCharOffset();

            if (startInnerText <= endInnerText)
            {
                if (mStream != null)
                {
                    long savePosition = mStream.getPosition();
                    mStream.setPosition(startInnerText);
                    int length = (int) (endInnerText - startInnerText);
                    byte[] bytes = new byte[length];
                    mStream.read(bytes, 0, length);
                    text = getEncoding().getString(bytes);
                    mStream.setPosition(savePosition);
                }
                else if (mString != null)
                {
                    text = mString.substring((int) startInnerText, (int) endInnerText);
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return text;
    }

    public XmlReader readSubtree() throws Exception
    {
        // save stream position
        long startPosition = mStream.getPosition();
        mStream.setPosition(0);

        // copy xmlTextRead and move to current node
        XmlTextReader copyOfReader = new XmlTextReader(mStream);
        copyOfReader.read();
        while (copyOfReader.mReader.getEndingCharOffset() < this.mReader.getEndingCharOffset()){
            copyOfReader.read();
        }
        String subtree = copyOfReader.readOuterXml();

        // FIX WORDSJAVA-2418. move position like InnerXml
        mStream.setPosition(startPosition);
        this.readInnerXml();

        return new XmlTextReader(subtree,false,false);
    }

    // JAVA-added
    public SortedList<String, String> getLocalNamespaces()
    {
        SortedList<String, String> localNamespaces = new SortedList<String, String>();
        for (int i = 0; i < mReader.getNamespaceCount(); i++)
        {
            String prefix = mReader.getNamespacePrefix(i);
            if (prefix != null)
                localNamespaces.add(prefix, mReader.getNamespaceURI(i));
        }

        return localNamespaces;
    }

    // JAVA-added
    public Map<String, String> getLocalNamespacesMap()
    {
        Map<String, String> localNamespaces = new HashMap<String, String>();

        try
        {
            for (int i = 0; i < mReader.getNamespaceCount(); i++)
            {
                String prefix = mReader.getNamespacePrefix(i);
                if (prefix != null)
                    localNamespaces.put(prefix, mReader.getNamespaceURI(i));
            }
        }
        catch (Exception e)
        {
            // error in text element
        }

        return localNamespaces;
    }

    public String readOuterXml()
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            //@todo 3 sk read outer xml for attribute nodes.
            if (mCurrentEvent != START_ELEMENT)
            {
                mCurrentEvent = mReader.next();
                return "";
            }

            XmlElementReader elementReader = new XmlElementReader(mReader, builder);
            mCurrentEvent = elementReader.readOuterXml();
            //elementReader returns mReader with cursor set to END_ELEMENT, but .Net sets the cursor to the next element.
            mCurrentEvent = mReader.next();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    public boolean isEmptyElement()
    {
        try
        {
            return mReader.isEmptyElement();
        }
        catch (XMLStreamException e)
        {
            wrapXmlStreamException(e);
        }
        // This code is never executed actually.
        return false;
    }

    // The element has xml:space=preserve attribute -> we have to preserve whitespaces in the element's text.
    public boolean isXmlSpacePreserve()
    {
       String spaceAttrValue = mReader.getAttributeCollector().getValueByLocalName("space");
       return "preserve".equals(spaceAttrValue);

    }

    // Get the namespace prefix associated with the current node.
    public String getPrefix()
    {
        if (mIsIgnoreNamespaces)
        {
            // STAX parser will only pass namespaces as part of element/attribute name/value information.
            // Namespaces will be considered as ordinal attributes. In this case local name of element/attribute
            // contains xmlns prefix, i.e. getAttributeLocalName() and getLocalName() return full name like 'xmlns:mc'.
            // So we should extract prefix.
            if (mAttributeIndex >= 0)
                return extractPrefix(mReader.getAttributeLocalName(mAttributeIndex));

            if (mReader.hasName())
                return extractPrefix(mReader.getLocalName());

            return "";
        }
        else
        {
            if (mAttributeIndex >= 0 && mAttributeIndex < mNsCount)
                return "xmlns";

            if (mAttributeIndex >= mNsCount && mAttributeIndex < mNsCount + mAttributeCount)
                return mReader.getAttributePrefix(mAttributeIndex - mNsCount);

            if (mReader.hasName())
                return mReader.getPrefix();

            return "";
        }
    }

    private String extractPrefix(String fullName)
    {
        int separatorPos = fullName.indexOf(':');
        if (separatorPos >= 0)
            return fullName.substring(0, separatorPos);
        else
            return "";
    }

    // Get the local name of the current node.
    public String getLocalName()
    {
        if (mCurrentEvent == PROCESSING_INSTRUCTION)
            return mReader.getPITarget();

        if (mCurrentEvent == ENTITY_REFERENCE)
            return mReader.getLocalName();

        if (mIsIgnoreNamespaces)
        {
            if (mAttributeIndex >= 0)
                return extractLocalName(mReader.getAttributeLocalName(mAttributeIndex));
            else if (mReader.hasName())
                return extractLocalName(mReader.getLocalName());
            else
                return "";
        }
        else
        {
            if (mAttributeIndex >= 0 && mAttributeIndex < mNsCount)
                return mReader.getNamespacePrefix(mAttributeIndex);

            if (mAttributeIndex >= mNsCount && mAttributeIndex < mNsCount + mAttributeCount)
                return mReader.getAttributeLocalName(mAttributeIndex - mNsCount);

            if (mReader.hasName())
                return mReader.getLocalName();

            return "";
        }
    }

    private String extractLocalName(String name)
    {
        int separatorPos = name.indexOf(':');
        if (separatorPos >= 0)
            return name.substring(separatorPos + 1);
        else
            return name;
    }

    // Get the namespace URI associated with the current node.
    public String getNamespaceURI()
    {
        if (mIsIgnoreNamespaces)
        {
            if (mAttributeIndex >= 0)
                return mReader.getAttributeNamespace(mAttributeIndex);

            if (mReader.hasName())
                return mReader.getNamespaceURI();

            return "";
        }
        else
        {
            if (mAttributeIndex >= 0 && mAttributeIndex < mNsCount)
                return mReader.getNamespaceURI("xmlns");

            if (mAttributeIndex >= mNsCount && mAttributeIndex < mNsCount + mAttributeCount)
                return mReader.getAttributeNamespace(mAttributeIndex - mNsCount);

            if (mReader.hasName())
                return mReader.getNamespaceURI();

            return "";
        }
    }

    //Resolves a namespace prefix in the current element's scope.
    public String lookupNamespace(String prefix)
    {
        if ("xlink".equals(prefix))
            return "http://www.w3.org/1999/xlink";
        if ("xml".equals(prefix))
            return "http://www.w3.org/XML/1998/namespace";
        return mReader.getNamespaceURI(prefix);
    }

    /**
     * Get the type of the current node.
     *
     * JAVA: returns javax.xml.stream.XMLStreamConstants values, but we can compare them with
     * ours XmlNodeType since it's values corrected accordingly, see XmlNodeType.
     */
    public int getNodeType()
    {
        if (mAttributeIndex >= 0)
            return XmlNodeType.ATTRIBUTE;
        else
            return mCurrentEvent;
    }

    // Get the text value of the current node.
    public String getValue()
    {
        if (mIsIgnoreNamespaces)
        {
            if (mAttributeIndex >= 0)
                return mReader.getAttributeValue(mAttributeIndex);
        }
        else
        {
            if (mAttributeIndex >= 0 && mAttributeIndex < mNsCount)
                return mReader.getNamespaceURI(mAttributeIndex);

            if (mAttributeIndex >= mNsCount && mAttributeIndex < mNsCount + mAttributeCount)
                return mReader.getAttributeValue(mAttributeIndex - mNsCount);
        }

        if (mCurrentEvent == START_ELEMENT ||
                mCurrentEvent == END_ELEMENT ||
                mCurrentEvent == END_DOCUMENT)
            return "";

        if (mCurrentEvent == PROCESSING_INSTRUCTION)
            return mReader.getPIData();

        if (mCurrentEvent == ENTITY_REFERENCE)
        {
            Object dtdObject = mReader.getProcessedDTD();
            if (dtdObject != null && dtdObject instanceof DTDSubset)
            {
                DTDSubset dtd = (DTDSubset)dtdObject;
                return dtd.getGeneralEntityMap().get(mReader.getLocalName()).getReplacementText();
            }

            return "";
        }

        return mReader.getText();
    }

    public void close()
    {
        try
        {
            mReader.close();
        }
        catch (XMLStreamException e)
        {
            wrapXmlStreamException(e);
        }
    }

    /**
     * Returns the encoding of the document.
     * If no encoding attribute exists, and there is no byte-order mark, this defaults to UTF-8.
     */
    public Encoding getEncoding()
    {
        if (mEncoding == null)
        {
            String encoding = mReader.getEncoding();
            if (encoding == null)
                mEncoding = Encoding.getUTF8();
            else
                mEncoding = Encoding.getEncoding(encoding);
        }

        return mEncoding;
    }

    public String getName()
    {
        if (mIsIgnoreNamespaces)
        {
            // STAX parser will only pass namespaces as part of element/attribute name/value information.
            // Namespaces will be considered as ordinal attributes. In this case local name of element/attribute
            // already contains xmlns prefix, i.e. getAttributeLocalName() and getLocalName() already return full
            // name like 'xmlns:mc'.
            if (mAttributeIndex >= 0)
                return mReader.getAttributeLocalName(mAttributeIndex);

            if (mReader.hasName())
                return mReader.getLocalName();

            return "";
        }
        else
        {
            if (mAttributeIndex >= 0 && mAttributeIndex < mNsCount)
            {
                String prefix = mReader.getNamespacePrefix(mAttributeIndex);
                return prefix == null || prefix.isEmpty() ? "xmlns" : "xmlns:" +prefix;
            }

            if (mAttributeIndex >= mNsCount && mAttributeIndex < mNsCount + mAttributeCount)
            {
                int index = mAttributeIndex - mNsCount;
                if (JavaUtil.hasChars(mReader.getAttributePrefix(index)))
                    return mReader.getAttributePrefix(index) + ":" + mReader.getAttributeLocalName(index);
                else
                    return mReader.getAttributeLocalName(index);
            }

            if (mReader.hasName())
            {
                if (JavaUtil.hasChars(mReader.getPrefix()))
                    return mReader.getPrefix() + ":" + mReader.getLocalName();
                else
                    return mReader.getLocalName();
            }

            return "";
        }
    }

    /**
     * JAVA-added: Wraps javax.xml.stream.XMLStreamException into aspose's runtime Exception.
     *
     * XMLStreamException (as part of external lib) is obfuscated so we have to provide clear
     * point to the cause instead of obfuscated symbols:)
     */
    private static void wrapXmlStreamException(XMLStreamException e)
    {
        throw new IllegalStateException("XMLStreamException: " + e.getMessage(), e);
    }

    //JAVA-added
    private void resetAttributes()
    {
        mNsCount = 0;
        mAttributeIndex = -1;
        mAttributeCount = 0;
    }

    //JAVA-added for debug
    @Override
    public String toString()
    {
        return "XmlTextReader{" +
                "mCurrentEvent=" + XmlNodeType.toString(mCurrentEvent) +
                ", localName=" + getLocalName() +
                '}';
    }

    @Override
    public int getDepth()
    {
        return mReader.getDepth();
    }

    @Override
    public int getAttributeCount()
    {
        return (mIsIgnoreNamespaces)
                ? mAttributeCount
                : mNsCount + mAttributeCount;
    }

    @Override
    public boolean moveToAttribute(int idx)
    {
        //StAX throws illegal state if improperly positioned but .Net just return false.
        if (mCurrentEvent != START_ELEMENT)
            return false;

        if (mIsIgnoreNamespaces)
        {
            mAttributeCount = mReader.getAttributeCount();
            if (idx >= 0 && idx < mAttributeCount)
            {
                mAttributeIndex = idx;
                return true;
            }

            return false;
        }
        else
        {
            mNsCount = mReader.getNamespaceCount();
            mAttributeCount = mReader.getAttributeCount();

            if (idx >= 0 && idx < mNsCount + mAttributeCount)
            {
                mAttributeIndex = idx;
                return true;
            }

            return false;
        }
    }

    @Override
    public String getAttribute(int idx)
    {
        if (mIsIgnoreNamespaces)
        {
            mAttributeCount = mReader.getAttributeCount();

            if (idx >= 0 && idx < mAttributeCount)
                return mReader.getAttributeValue(idx);

            throw new IllegalArgumentException();
        }
        else
        {
            mNsCount = mReader.getNamespaceCount();
            if (idx >= 0 && idx < mNsCount)
                return mReader.getNamespaceURI(idx);

            mAttributeCount = mReader.getAttributeCount();
            if (idx >= mNsCount && idx < mNsCount + mAttributeCount)
                return mReader.getAttributeValue(idx - mNsCount);

            throw new IllegalArgumentException();
        }
    }

    public boolean canResolveEntity()
    {
        return true;
    }

    public void resolveEntity()
    {
        // Do nothing: Woodstox resolves entities automatically (?).
    }

    public void setNamespaces(boolean supportNamespaces)
    {
        mIsIgnoreNamespaces = !supportNamespaces;
    }

    public void setDtdProcessing(DtdProcessing processing)
    {
        setProhibitDtd(processing == DtdProcessing.IGNORE);
    }

    public void setProhibitDtd(boolean ProhibitDtd)
    {
        mIsIgnoreDtd = ProhibitDtd;
    }

    private void readToDocumentEnd()
    {
        while (mReader.getEventType() != XMLStreamConstants.END_DOCUMENT)
        {
            try
            {
                read();
            }
            catch (Exception ex)
            {
                // WORDSJAVA-1719: Don't read if the document's end was reached (Unexpected EOF)
                if (ex.getMessage().contains(gNativeUnexpectedEOFExceptionMsg))
                {
                    // Unexpected end of the document
                    return;
                }
                // Ignore all exceptions.
            }
        }
    }

    public enum DtdProcessing {PROHIBIT, IGNORE, PARSE}

    private ValidatingStreamReader mReader;
    private Stream mStream;
    private String mString;
    private int mCurrentEvent;
    private int mAttributeIndex;
    private int mAttributeCount;
    private int mNsCount;
    private Encoding mEncoding;
    private boolean mIsIgnoreNamespaces;
    private boolean mIsIgnoreDtd;

    private static final String gNativeUndeclaredNamespaceExceptionMsg = "Undeclared namespace prefix";
    private static final String gNativeUnexpectedEOFExceptionMsg = "Unexpected EOF";
}