// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/07/2007 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import java.util.Map;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Xml.XmlNodeType;
import com.aspose.ms.System.Guid;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.Xml.XmlTextReader;


/// <summary>
/// Provides useful methods to read any XML file (DOCX, WordML, ODF or whatever).
/// Do not put any MS Word specific methods here.
///
/// This class is implemented as a wrapper over XmlTextReader.
/// Wrapping reduces the interface exposed to the client classes and makes easier porting to Java.
/// </summary>
public class AnyXmlReader
{
    /// <summary>
    /// Initializes XML reader, positions to the first content node.
    /// </summary>
    public AnyXmlReader(Stream stream) throws Exception
    {
    	this(stream, true, false);
	
    }

    /// <summary>
    /// Initializes XML reader.
    /// </summary>
    /// <param name="stream">The stream containing the XML data to read.</param>
    /// <param name="moveToContent">Indicates that reader should skip ahead to the next content node.</param>
    /// <param name="useFullElementName">Reader will use full element name (e.g. "xml:space") instead of local name.</param>
    public AnyXmlReader(Stream stream, boolean moveToContent, boolean useFullElementName) throws Exception
    {
        mUseFullElementName = useFullElementName;
        // Some DOCX documents might require reading the same part twice (for example same footer is referenced
        // twice), therefore the stream's position must be reset to start. I am putting this here, not in DOCX
        // because it covers more cases and it looks like always have to read from stream start.
        stream.setPosition(0);

        mReader = XmlUtilPal.createXmlTextReader(stream);

        if (moveToContent)
            mReader.moveToContent();

        mUnderlyingStream = stream;
    }

    public AnyXmlReader(Stream stream, boolean moveToContent) throws Exception
    {
    	this(stream, moveToContent, false);
	
        // Ctor.
    }

    /// <summary>
    /// Ctor to read from a string, possibly a fragment.
    /// </summary>
    /// <param name="xml">This can be an XML document or fragment.</param>
    /// <param name="namespaces">Key is prefix, value is namespace url. Needed to properly load
    /// fragments that contain elements with prefixes. Optional, can be null.</param>
    /// <param name="useFullElementName">Reader will use full element name (e.g. "xml:space") instead of local name.</param>
    public AnyXmlReader(String xml, Map<String, String> namespaces, boolean useFullElementName)
    {
        mReader = XmlUtilPal.createXmlTextReader(xml, namespaces);
        mReader.moveToContent();
        mUseFullElementName = useFullElementName;
    }

    public AnyXmlReader(String xml, Map<String, String> namespaces)
    {
    	this(xml, namespaces, false);
	
        // Ctor.
    }

    /// <summary>
    /// Reads to the start of the next element in the stream.
    /// Stops reading when reaches the end of the specified element or the end of file.
    /// Used to read child elements of the specified element.
    ///
    /// Reader should already be positioned on the specified element
    /// (or on an attribute of the element).
    /// </summary>
    /// <param name="parentElement">The local name of an element,
    /// end of which limits the scope of the operation.</param>
    /// <returns>Returns true if the element was read.
    /// Returns false if reached the end of the specified element or the end of file.</returns>
    public boolean readChild(String parentElement)
    {
        return readChildWithTextValues(parentElement, AnyXmlTextHandling.NONE);
    }

    /// <summary>
    /// Reads to the start of the next element in the stream.
    /// Stops reading when reaches the end of the specified element, or the end of file.
    ///
    /// Used to read child elements of the specified element.
    ///
    /// Reader should already be positioned on the specified element
    /// (or on an attribute of the element).
    /// </summary>
    /// <param name="parentElement">The local name of an element,
    /// end of which limits the scope of the operation.</param>
    /// <param name="textHandling">Specifies how to return text and whitespace nodes to the handler.</param>
    /// <returns>Returns true if the element was read. Returns false if reached the end of the specified element or the end of file.</returns>
    public boolean readChildWithTextValues(String parentElement, /*AnyXmlTextHandling*/int textHandling)
    {
        // Move to element in case we are positioned on one of its attributes.
        mReader.moveToElement();

        // Signal the caller to stop if we have an empty element.
        if (isEmptyElement() && (msString.equals(getNameInternal(), parentElement)))
            return false;

        // This loop skips XML nodes that are not elements (comments etc).
        while (mReader.read() && mReader.getDepth() < 1800) // WORDSNET-26597 Java limit of depth is around 2000
        {
            switch (mReader.getNodeType())
            {
                case XmlNodeType.ELEMENT:
                {
                    // Found a child element, return it to the caller.
                    return true;
                }
                case XmlNodeType.END_ELEMENT:
                {
                    // If reached the end of the parent element, signal the caller to stop.
                    if (isEndElement(parentElement))
                        return false;
                    break;
                }
                case XmlNodeType.CDATA:
                case XmlNodeType.TEXT:
                {
                    // Found a text value, return it to the caller.
                    if ((textHandling & AnyXmlTextHandling.TEXT) != 0)
                        return true;
                    break;
                }
                case XmlNodeType.SIGNIFICANT_WHITESPACE:
                {
                    // Found a text value, return it to the caller.
                    if ((textHandling & AnyXmlTextHandling.SIGNIFICANT_WHITESPACE) != 0)
                        return true;
                    break;
                }
                case XmlNodeType.WHITESPACE:
                {
                    // If the caller requests, we have to return ignorable whitespace too, because in ODT such whitespace can be significant.
                    if ((textHandling & AnyXmlTextHandling.WHITESPACE) != 0)
                        return true;
                    break;
                }
                case XmlNodeType.PROCESSING_INSTRUCTION:
                {
                    // ProcessingInstruction is significant for C14Transform algorithm.
                    if ((textHandling & AnyXmlTextHandling.PROCESSING_INSTRUCTION) != 0)
                        return true;
                    break;
                }
                case XmlNodeType.ENTITY_REFERENCE:
                {
                    // EntityReference is significant for C14Transform algorithm.
                    if ((textHandling & AnyXmlTextHandling.ENTITY_REFERENCE) != 0)
                    {
                        if (mReader.canResolveEntity())
                            mReader.resolveEntity();

                        return true;
                    }
                    break;
                }
                default:
                    // Do nothing and it will skip.
                    break;
            }
        }

        // If reached end of file, signal the caller to stop.
        return false;
    }

    /// <summary>
    /// Skips the current element content and positions reader to the element's end.
    /// Reader should already be positioned on the specified element
    /// (or on an attribute of the element).
    ///
    /// This method is different from XmlTextReader.Skip in the way it positions the cursor.
    /// Skip positions to start of next element, but this method positions to the end of the skipped element.
    /// This is needed because we read elements using ReadChild and it will read next node when called.
    /// </summary>
    public /*virtual*/ void ignoreElement()
    {
        // Move to element in case we are positioned on one of its attributes.
        mReader.moveToElement();

        // There is nothing to skip if the element is empty.
        if (isEmptyElement())
            return;

        // This is the name of the element we are skipping.
        String tagName = getNameInternal();
        while (!isEndElement(tagName))
        {
            mReader.read();

            // This skips child elements nicely.
            if (mReader.getNodeType() == XmlNodeType.ELEMENT)
                mReader.skip();
        }
    }

    public boolean isEndElement(String elemName)
    {
        return (mReader.getNodeType() == XmlNodeType.END_ELEMENT) && (msString.equals(getNameInternal(), elemName));
    }

    /// <summary>
    /// Reads a value of the attribute with the specified name.
    /// If the attribute is not found returns the default value.
    /// </summary>
    public String readAttribute(String name, String defaultValue)
    {
        String result = defaultValue;

        // RK We are using MoveToNextAttribute, not GetAttribute because we are looking up using local name.
        // GetAttribute takes either a qualified name or a local name and namespace uri.
        // In the future I actually want to rework all xml reading code to specify the namespace everywhere.
        while (mReader.moveToNextAttribute())
        {
            if (msString.equals(getNameInternal(), name))
            {
                result = mReader.getValue();
                break;
            }
        }

        // RK Reset the position so we can read attributes of this element again.
        mReader.moveToElement();

        return result;
    }

    /// <summary>
    /// Reads a value of the attribute with the specified name.
    /// If the attribute is not found returns the default value.
    /// </summary>
    public int readIntAttribute(String localName, int defaultValue)
    {
        String s = readAttribute(localName, null);
        return (s != null) ? FormatterPal.xmlToInt(s) : defaultValue;
    }

    /// <summary>
    /// Reads a value of the attribute with the specified name.
    /// If the attribute is not found returns the default value.
    /// </summary>
    public /*uint*/int readUIntAttribute(String localName, /*uint*/int defaultValue)
    {
        String s = readAttribute(localName, null);
        return (s != null) ? FormatterPal.xmlToUInt(s) : defaultValue;
    }

    /// <summary>
    /// Reads a value of the attribute with the specified name.
    /// If the attribute is not found returns the default value.
    /// </summary>
    public long readLongAttribute(String localName, long defaultValue)
    {
        String s = readAttribute(localName, null);
        return (s != null) ? FormatterPal.xmlToLong(s) : defaultValue;
    }

    /// <summary>
    /// Reads a value of the attribute with the specified name.
    /// If the attribute is not found returns the default value.
    /// </summary>
    public double readDoubleAttribute(String localName, double defaultValue)
    {
        String s = readAttribute(localName, null);
        return (s != null) ? FormatterPal.parseDouble(s) : defaultValue;
    }

    /// <summary>
    /// Reads a value of the attribute with the specified name.
    /// If the attribute is not found returns the default value.
    /// </summary>
    public boolean readBoolAttribute(String localName, boolean defaultValue)
    {
        String s = readAttribute(localName, null);
        if (s == null)
            return defaultValue;
        //XML Schema Part 2: Datatypes
        //3.2.2.1 Lexical representation
        // An instance of a datatype that is defined as boolean can have the following literals {true, false, 1, 0}.
        // Practice shows {t} can be used instead of {true} also.
        return convertToBool(s);
    }

    public static boolean convertToBool(String s)
    {
        return "1".equals(s) || "true".equals(s) || "t".equals(s);
    }

    /// <summary>
    /// Reads a value of the attribute with the specified name.
    /// If the attribute is not found returns the default value.
    /// </summary>
    public Guid readGuidAttribute(String localName, Guid defaultValue)
    {
        String s = readAttribute(localName, null);
        return StringUtil.hasChars(s) ? new Guid(s) : defaultValue;
    }

    /// <summary>
    /// Moves to the element that contains the current attribute node.
    /// </summary>
    public boolean moveToElement()
    {
        return mReader.moveToElement();
    }

    /// <summary>
    /// Moves to the next attribute of the current element.
    ///
    /// RK Note, this method skips attributes with the "xmlns" prefix e.g "xmlns:w" because at the moment
    /// our readers look at the local name only and we don't want "xmlns:w" to be parsed as "w".
    /// In the future I should fix parsers to somehow take namespace into account too.
    /// </summary>
    public boolean moveToNextAttribute()
    {
        return moveToNextAttribute(true);
    }

    public boolean moveToNextAttribute(boolean ignoreXmlns)
    {
        while (mReader.moveToNextAttribute())
        {
            if ((!"xmlns".equals(mReader.getPrefix())) || (!ignoreXmlns))
                return true;
        }
        return false;
    }

    /// <summary>
    /// Reads the contents of an element or text node as a string.
    /// </summary>
    public String readString()
    {
        return mReader.readString();
    }

    /// <summary>
    /// Reads the contents of an element or text node as a string. Converts _x000D_ to \r character and x000B to \v.
    /// </summary>
    /// <remarks>
    /// AM. I think this replacement (_x000D to CR and x000B to Vertical Tab) might be common routine for string reading/writing.
    /// But still not sure and this method is called in certain places only.
    /// It should not be called often that's why implementation is so straight.
    /// If I am right one day we should integrate such replacement into common ReadString().
    /// </remarks>
    public String readStringConvertSpecialCharacters(boolean isLpwstr)
    {
        String result = (isLpwstr) ? mReader.readString().replace("\r", "") : mReader.readString();
        return result.replace("_x000d_", "\r").replace("_x000D_", "\r").replace("_x000b_", "\u000b").replace("_x000B_", "\u000b");
    }

    public int readStringAsInt()
    {
        return FormatterPal.xmlToInt(readString());
    }

    /// <summary>
    /// Recognizes only case-insensitive "true" and "false".
    /// </summary>
    public boolean readStringAsBool()
    {
        return FormatterPal.parseBoolTrueFalse(readString());
    }

    /// <summary>
    /// Reads the contents of an element or text node as <b>double</b>.
    /// </summary>
    public double readStringAsDouble()
    {
        return FormatterPal.parseDouble(readString());
    }

    /// <summary>
    /// Reads the contents, including markup, representing the current node and all its children.
    /// Skips ignorable whitespace after the end till the next node.
    /// </summary>
    public String readOuterXml()
    {
        return readOuterXml(null, null);
    }

    /// <summary>
    /// Reads the contents, including markup, representing the current node and all its children.
    /// Skips ignorable whitespace after the end till the next node.
    /// Allows providing the <see cref="IXmlUpdater"/> interface to update XML on reading.
    /// Also, allows providing the <see cref="XmlTextReaderNamespaceStorage"/> which populates with
    /// used XML namespaces while reading.
    /// </summary>
    public String readOuterXml(IXmlUpdater xmlUpdater, XmlTextReaderNamespaceStorage namespaceStorage)
    {
        // WORDSJAVA-944 Charts in Docx xml markup: xml attribute order changed.
        String result = XmlUtilPal.readOuterXml(mReader, xmlUpdater, namespaceStorage);

        // Ideally, we only need to skip ignorable whitespace after the end till the next node.
        // But if there is a line break, then Java reports it and further indentation as text, whereas .NET skips it as whitespace.
        // This is a reasonably safe autoportable hack that has no effect in .NET but helps Java to skip till the next node correctly.
        while ((mReader.getNodeType() == XmlNodeType.WHITESPACE) ||
                (mReader.getNodeType() == XmlNodeType.TEXT) ||
                (mReader.getNodeType() == XmlNodeType.COMMENT) || // WORDSNET-7199
                (mReader.getNodeType() == XmlNodeType.SIGNIFICANT_WHITESPACE)) // WORDSNET-13242
        {
            mReader.read();
        }

        return result;
    }

    /// <summary>
    /// Gets a collection of all namespaces defined locally at the current node.
    /// </summary>
    public Map<String, String> getNameSpacesDefinedLocally()
    {
        // WORKAROUND: Java doesn't support XmlTextReader.GetNamespacesInScope()

        HashMap<String, String> namespaces = new HashMap<String, String>();

        while (mReader.moveToNextAttribute())
        {
            if ("xmlns".equals(mReader.getPrefix()))
            {
                msDictionary.add(namespaces, mReader.getLocalName(), mReader.getValue());
            }
        }
        mReader.moveToElement();
        return namespaces;
    }

    /// <summary>
    /// Get name space by prefix.
    /// </summary>
    public String lookupNamespace(String prefix)
    {
        return mReader.lookupNamespace(prefix);
    }

    /// <summary>
    /// Reads the next node from the reader stream.
    /// </summary>
    public void readNextNode()
    {
        mReader.read();//ExEnd of empty element
        mReader.read();//ExSpace of empty element
        // Check if the current element is an EndElement.
        // If yes, then there is no need to read again to avoid an infinite loop (see WORDSJAVA-2886).
        if(!isEndElement(mReader.getLocalName()))
            mReader.read();//ExEnd of parent element
    }

    /// <summary>
    /// Gets the namespace prefix associated with the current node.
    /// </summary>
    public String getPrefix() { return mReader.getPrefix(); }

    /// <summary>
    /// Gets the local name of the current node.
    /// </summary>
    public String getLocalName() { return mReader.getLocalName(); }

    /// <summary>
    /// Gets the name of the current node including prefix.
    /// </summary>
    public String getName() { return mReader.getName(); }

    public boolean isEmptyElement() { return mReader.isEmptyElement(); }

    /// <summary>
    /// Gets the text value of the current node.
    /// </summary>
    public String getValue() { return mReader.getValue(); }

    /// <summary>
    /// Parses the text value of the current node into an integer.
    /// </summary>
    public int getValueAsInt() { return FormatterPal.xmlToInt(getValue()); }

    /// <summary>
    /// Parses the text value of the current node into an unsigned integer.
    /// </summary>
    public /*uint*/int getValueAsUInt() { return FormatterPal.xmlToUInt(getValue()); }

    /// <summary>
    /// Parses the text value of the current node into a long.
    /// </summary>
    public long getValueAsLong() { return FormatterPal.xmlToLong(getValue()); }

    public double getValueAsDouble() { return FormatterPal.parseDouble(getValue()); }

    public /*virtual*/ boolean getValueAsBool() { return convertToBool(getValue()); }

    /// <summary>
    /// WORDSNET-4391 Int64 values appear in places where we expect Int32, so we truncate values to the lower 32 bits during this resiliency.
    /// </summary>
    public int getValueAsTruncatedInt() { return (int)(FormatterPal.parseInt64(mReader.getValue()) & 0xFFFFFFFFL); }

    public String getNamespaceURI() { return mReader.getNamespaceURI(); }

    public XmlTextReader getUnderlyingReader() { return mReader; }

    /// <summary>
    /// Underlying stream used by the reader.
    /// </summary>
    /// <remarks>
    /// May be null.
    /// </remarks>
    public Stream getUnderlyingStream() { return mUnderlyingStream; }

    public /*XmlNodeType*/int getNodeType() { return mReader.getNodeType(); }

    private String getNameInternal()
    {
        return mUseFullElementName
            ? getName()
            : getLocalName();
    }

    /// <summary>
    /// Underlying stream used by the reader.
    /// </summary>
    private /*final*/ Stream mUnderlyingStream;

    /// <summary>
    /// Inner XmlTextReader. It must be in the mode that returns all whitespace (significant and ignorable).
    /// </summary>
    private /*final*/ XmlTextReader mReader;

    private /*final*/ boolean mUseFullElementName;
}

