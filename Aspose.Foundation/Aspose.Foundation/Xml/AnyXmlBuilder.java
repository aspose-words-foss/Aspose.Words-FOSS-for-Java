// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2009 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Xml.Formatting;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.ms.System.IO.StringReader;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.TextReader;
import com.aspose.ms.System.Xml.XmlTextWriter;


public class AnyXmlBuilder implements IAnyXmlBuilder
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="stream">Stream to write to.</param>
    /// <param name="encoding">Encoding to use. Do not forget about BOM in case of Encoding.UTF8</param>
    /// <param name="isPrettyFormat">True to use pretty formatting for the output XML.</param>
    public AnyXmlBuilder(Stream stream, Encoding encoding, boolean isPrettyFormat) throws Exception
    {
        // .NET does not seem to write a preamble when writing in the UTF-7 encoding.
        // We write it ourselves here, but before writing check - .NET might have it fixed one day and we don't write in that case.
        final int CODE_PAGE_UTF_7 = 65000;
        byte[] utf7Bom = new byte[] { (byte) 0x2B, (byte) 0x2F, (byte) 0x76, (byte) 0x38, (byte) 0x2D };
        if (encoding.getCodePage() == CODE_PAGE_UTF_7)
        {
            Debug.assertTrue("utf-7".equals(encoding.getWebName()));
            byte[] bom = encoding.getPreamble();
            if (bom.length == 0)
            {
                bom = utf7Bom;
                stream.write(bom, 0, bom.length);
            }
        }

        mXmlWriter = XmlUtilPal.createXmlTextWriter(stream, encoding);
        mXmlCharsFilter = new StringBuilder(2048);

        // SPEED RK: This makes export in .NET almost twice as fast. We take care about namespaces in our code ourselves.
        mXmlWriter.setNamespaces(false);

        if (isPrettyFormat)
        {
            mXmlWriter.setFormatting(Formatting.INDENTED);
            mXmlWriter.setIndentation(1);
            mXmlWriter.setIndentChar('\t');
        }
    }

    /// <summary>
    /// Ctor. Uses UTF8 encoding.
    /// </summary>
    public AnyXmlBuilder(Stream stream, boolean isPrettyFormat) throws Exception
    {
    	this(stream, new UTF8Encoding(false), isPrettyFormat);
	
    }

    /// <summary>
    /// Gets or sets a value indicating whether elements of the output XML are indented.
    /// </summary>
    public boolean getPrettyFormat() { return mXmlWriter.getFormatting() == Formatting.INDENTED; }
    public void setPrettyFormat(boolean value)
    {
        mXmlWriter.setFormatting(value
            ? Formatting.INDENTED
            : Formatting.NONE);
    }

    /// <summary>
    /// Gets the underlying stream object.
    /// </summary>
    public Stream getBaseStream() { return mXmlWriter.getBaseStream(); }

    /// <summary>
    /// Starts the XML document and the root element. The document is marked with the "standalone=yes" attribute.
    /// </summary>
    public /*virtual*/ void startDocument( String rootElementName)
    {
        mXmlWriter.writeStartDocument(true);
        startElement(rootElementName);
    }

    /// <summary>
    /// Starts the XML document. No root element and no standalone attribute are written.
    /// </summary>
    public void startDocument()
    {
        mXmlWriter.writeStartDocument();
    }

    /// <summary>
    /// Starts the XML document with the specified standalone attribute value. No root element is written.
    /// </summary>
    public void startDocument(boolean standalone)
    {
        mXmlWriter.writeStartDocument(standalone);
    }

    /// <summary>
    /// Starts the XML document and the root element.
    /// </summary>
    public void startDocument(String rootElementName, boolean standalone)
    {
        mXmlWriter.writeStartDocument(standalone);
        startElement(rootElementName);
    }

    /// <summary>
    /// Closes all opened elements and the XML document itself and flushes it to the undelying stream.
    /// </summary>
    public /*virtual*/ void endDocument()
    {
        while (mDepth > 0)
        {
            endElement();
        }
        mXmlWriter.writeEndDocument();
        mXmlWriter.flush();
    }

    /// <summary>
    /// Writes start of the element with the specified name.
    /// </summary>
    public void startElement(String elementName)
    {
        startElement(elementName, false);
    }

    /// <summary>
    /// Writes start of the element with the specified name.
    /// </summary>
    /// <remarks>
    /// This overload accepts a flag that indicates whether children of this element are forcibly written without pretty
    /// format, even if pretty format is turned on for the rest of the XML document. This flag allows to correctly track
    /// indentation of elements and, as a result, allows to correctly format inserted multi-line raw text.
    /// </remarks>
    public void startElement(String elementName, boolean forceNoPrettyFormatForChildren)
    {
        restoreIndentationAfterIndentedRawContentIfNeeded();

        mXmlWriter.writeStartElement(elementName);

        ++mDepth;
        if (getPrettyFormat() && !forceNoPrettyFormatForChildren)
        {
            ++mIndentationLevel;
        }
    }

    /// <summary>
    /// Writes end of the current element.
    /// </summary>
    public void endElement()
    {
        endElement(null, false);
    }

    /// <summary>
    /// Writes end of the current element.
    /// In debug mode checks the closing element name.
    /// </summary>
    public void endElement(String elementName)
    {
        endElement(elementName, false);
    }

    /// <summary>
    /// Writes end of the current element (self-closing or full end tag).
    /// In debug mode optionally checks the closing element name.
    /// </summary>
    private void endElement(String elementName, boolean alwaysWriteFullEndTag)
    {
        Debug.assertTrue(mDepth > 0);
        mDepth = Math.max(mDepth - 1, 0);
        if (getPrettyFormat())
        {
            mIndentationLevel = Math.max(mIndentationLevel - 1, 0);
        }
        restoreIndentationAfterIndentedRawContentIfNeeded();
        if (alwaysWriteFullEndTag)
        {
            mXmlWriter.writeFullEndElement();
        }
        else
        {
            mXmlWriter.writeEndElement();
        }
    }

    /// <summary>
    /// Writes the full end tag of the current element.
    /// </summary>
    public void fullEndElement()
    {
        endElement(null, true);
    }

    /// <summary>
    /// Writes the full end tag of the current element.
    /// In debug mode checks the closing element name.
    /// </summary>
    public void fullEndElement(String elementName)
    {
        endElement(elementName, true);
    }

    /// <summary>
    /// Writes text as is without entitizing or filtering invalid chars.
    /// </summary>
    public void writeRaw(String text)
    {
        writeRaw(text, false);
    }

    /// <summary>
    /// Writes text as is without entitizing or filtering invalid chars.
    /// Optionally indents lines of the text to match parent element indentation.
    /// </summary>
    public void writeRaw(String text, boolean indentLinesIfFormattingEnabled)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (indentLinesIfFormattingEnabled)
        	{
            	writeRaw(new StringReader(text), true);
        	}
        	else
        	{
            	mXmlWriter.writeRaw(text);
        	}
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Reads text from the stream and writes it as is without entitizing or filtering invalid chars.
    /// The text must be encoded in UTF8.
    /// Optionally indents lines of the text to match parent element indentation.
    /// </summary>
    public void writeRaw(Stream textStream, boolean indentLinesIfFormattingEnabled) throws Exception
    {
        writeRaw(new StreamReader(textStream, Encoding.getUTF8()), indentLinesIfFormattingEnabled);
    }

    /// <summary>
    /// Writes empty element with the specified name.
    /// </summary>
    public void writeEmptyElement(String elementName)
    {
        writeElement(elementName, null);
    }

    /// <summary>
    /// Writes element with the specified name and value.
    /// </summary>
    public void writeElement(String elementName, String value)
    {
        startElement(elementName);
        writeString(value);
        endElement();
    }

    /// <summary>
    /// Writes element with the specified name and a string value if the string is not null or empty.
    /// </summary>
    public void writeOptionalElement(String elementName, String value)
    {
        if (StringUtil.hasChars(value))
            writeElement(elementName, value);
    }

    /// <summary>
    /// Writes element with the specified name and a DateTime value.
    /// DateTime is validated (year value should be greater than 1).
    /// If DateTime is invalid the element is not written.
    /// </summary>
    public void writeOptionalElement(String elementName, DateTime value)
    {
        if (value.getYear() > 1)
            writeElement(elementName, FormatterPal.dateTimeToXmlUtc(value));
    }

    /// <summary>
    /// Writes element with the specified name and an integer value.
    /// </summary>
    public void writeElement(String elementName, int value)
    {
        writeElement(elementName, FormatterPal.intToXml(value));
    }

    /// <summary>
    /// Writes the given text content. Filters out invalid characters.
    /// </summary>
    public void writeString(String value)
    {
        mXmlWriter.writeString(filterInvalidXmlChars(value));
    }

    /// <summary>
    /// Writes the given text content. Filters out invalid characters. Converts standalone /r character into _x000D string.
    /// </summary>
    /// <remarks>See <see cref="AnyXmlReader.ReadStringConvertSpecialCharacters" /> for explanation.</remarks>
    public void writeStringConvertSpecialCharacters(String value)
    {
        writeString(value.replace("\r\n", "\n").replace("\r", "_x000D_").replace("\n", "\r\n"));
    }

    /// <summary>
    /// Writes an attribute string if value is not default. Always. Filters out invalid characters.
    /// </summary>
    public void writeAttributeStringIfNotDefault(String name, String value, String defaultValue)
    {
        if (msString.equals(value, defaultValue))
            return;

        writeAttributeString(name, value);
    }

    /// <summary>
    /// Writes an attribute string. Always. Filters out invalid characters.
    /// </summary>
    public void writeAttributeString(String name, String value)
    {
        mXmlWriter.writeAttributeString(name, filterInvalidXmlChars(value));
    }

    /// <summary>
    /// Writes MIME-compliant Base64 data. Compliance means that Base64 string is broken into lines, containing
    /// exactly 76 characters each. Only the last line of base64 data can contain less than 76 characters.
    /// </summary>
    public void writeBase64(Stream stream) throws Exception
    {
        // Little optimization. In case of MemoryStream lets access bytes directly.
        MemoryStream streamMemoryStream = ms.as(stream, MemoryStream.class);

        if (streamMemoryStream != null)
            writeBase64(streamMemoryStream.getBuffer(), 0, (int)stream.getLength());
        else
            writeBase64(StreamUtil.copyStreamToByteArray(stream), 0, (int)stream.getLength());
    }

    /// <summary>
    /// Writes MIME-compliant Base64 data. Compliance means that Base64 string is broken into lines, containing
    /// exactly 76 characters each. Only the last line of base64 data can contain less than 76 characters.
    /// </summary>
    public void writeBase64(byte[] buffer, int index, int count)
    {
        Base64Splitter splitter = new Base64Splitter(buffer, index, count);
        while (!splitter.isEof())
        {
            writeString(splitter.getNext());
            mXmlWriter.writeWhitespace(NEW_LINE);
        }
    }

    /// <summary>
    /// Writes out a comment &lt;!--...--&gt; containing the specified text.
    /// </summary>
    public void writeComment(String text)
    {
        mXmlWriter.writeComment(text);
    }

    /// <summary>
    /// Writes out a processing instruction with a space between the name and text as follows: &lt;?name text?&gt;.
    /// </summary>
    public void writeProcessingInstruction(String name, String text)
    {
        mXmlWriter.writeProcessingInstruction(name, text);
    }

    /// <summary>
    /// Flushes whatever is in the buffer to the underlying streams and also flushes the underlying stream.
    /// </summary>
    public void flush()
    {
        mXmlWriter.flush();
    }

    /// <summary>
    /// Removes all characters that are invalid in XML.
    /// </summary>
    public String filterInvalidXmlChars(String text)
    {
        // RK Performance optimization.
        if (!hasInvalidChars(text))
            return text;

        mXmlCharsFilter.setLength(0);
        for (int utf32Char : new StringUtf32Enumerator(text))
        {
            if (isValidXmlChar(utf32Char))
                UnicodeUtil.appendUtf32(mXmlCharsFilter, utf32Char);
        }

        return mXmlCharsFilter.toString();
    }

    public boolean hasInvalidChars(String text)
    {
        if (!StringUtil.hasChars(text))
            return false;

        // Performace optimization. Use the field instead of creating a new StringUtf32Enumerator object
        // to reduce memory allocations and fragmentation.
        mStringEnumerator.setText(text);
        for (int utf32Char : mStringEnumerator)
        {
            if (!isValidXmlChar(utf32Char))
                return true;
        }

        return false;
    }

    /// <summary>
    /// According to the XML specification valid characters are:
    /// #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
    ///
    /// RK In DOC files we could sometimes get characters that will be invalid
    /// from XML point of view. Also, a user can insert text that contains invalid XML
    /// characters via the model. So we should actually take care and filter all text
    /// and document properties (and probably all attribute values) before writing to
    /// any XML format.
    /// </summary>
    private static boolean isValidXmlChar(int utf32Char)
    {
        return
            (utf32Char == 0x0009) ||
            (utf32Char == 0x000a) ||
            (utf32Char == 0x000d) ||
            ((utf32Char >= 0x0020) && (utf32Char <= 0xd7ff)) ||
            ((utf32Char >= 0xe000) && (utf32Char <= 0xfffd)) ||
            ((utf32Char >= 0x10000) && (utf32Char <= 0x10FFFF));
    }

    /// <summary>
    /// Reads text from the reader and writes it as is without entitizing or filtering invalid chars.
    /// The text must be encoded in UTF8.
    /// Optionally indents lines of the text to match parent element indentation.
    /// </summary>
    private void writeRaw(TextReader textReader, boolean indentLines) throws Exception
    {
        // Do not write anything if the reader is empty.
        if (textReader.peek() == -1)
        {
            return;
        }

        if (indentLines)
        {
            String indentation = msString.newString(mXmlWriter.getIndentChar(), mXmlWriter.getIndentation() * mIndentationLevel);

            boolean isMultiline = false;
            String line = textReader.readLine();
            while (line != null)
            {
                if (isMultiline || getPrettyFormat())
                {
                    mXmlWriter.writeRaw(NEW_LINE);
                    mXmlWriter.writeRaw(indentation);
                }
                mXmlWriter.writeRaw(line);

                line = textReader.readLine();
                isMultiline = true;
            }

            mAfterIndentedRawContent = getPrettyFormat();
        }
        else
        {
            mXmlWriter.writeRaw(textReader.readToEnd());
        }
    }

    private void restoreIndentationAfterIndentedRawContentIfNeeded()
    {
        if (mAfterIndentedRawContent)
        {
            mXmlWriter.writeRaw(NEW_LINE);
            String indentation = msString.newString(mXmlWriter.getIndentChar(), mXmlWriter.getIndentation() * mIndentationLevel);
            mXmlWriter.writeRaw(indentation);
            mAfterIndentedRawContent = false;
        }
    }

    private /*final*/ XmlTextWriter mXmlWriter;
    private /*final*/ StringBuilder mXmlCharsFilter;
    private /*final*/ StringUtf32Enumerator mStringEnumerator = new StringUtf32Enumerator("");

    /// <summary>
    /// Current element nesting depth (number of currently opened XML elements).
    /// </summary>
    private int mDepth;

    /// <summary>
    /// Current element indentation level.
    /// </summary>
    /// <remarks>
    /// This is different from the current nesting depth, because some elements might be written with pretty print
    /// turned off. For example, inline contents (spans, images) of HTML paragraphs.
    /// </remarks>
    private int mIndentationLevel;

    /// <summary>
    /// Indicates whether indented raw content was written into the current element. This flag is needed to correctly indent
    /// text after the raw content.
    /// </summary>
    private boolean mAfterIndentedRawContent;

    /// <summary>
    /// Line terminator string (line break characters). Used to split XML code into lines when pretty format is on.
    /// </summary>
    private static final String NEW_LINE = "\r\n";
//<<<<<<<< #endregion  Checking matching start/end element tags
}

