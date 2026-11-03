/**
 * Copyright Aspose.com, 2006
 * Helper Java code to port Aspose.Word C# source, Dec 8, 2006
 *
 * @author Konstantin Sidorenko
 */

package com.aspose.ms.System.Xml;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Text.EncodingProvider;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.IO.NetOutputStream;
import com.aspose.ms.java.xml.DotNetEscapingWriterFactory;
import com.aspose.ms.java.xml.IndentingXMLStreamWriter;
import com.ctc.wstx.stax.WstxOutputFactory;
import org.codehaus.stax2.XMLStreamWriter2;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.CharsetEncoder;

/**
 * Simplified copycat of .Net System.Xml.XmlTextWriter.
 * Backed by Woodstox implementation of StAX XML parser.
 *
 * Only methods used by Aspose.Words are implemented.
 *
 * Note:
 * 1. JAVA: all calls of backing XMLStreamWriter are surrounded by try-catch to wrap XMLStreamException.
 *    Since StAX (together with other external libs) is obfuscated, we have to hide exceptions from public api.
 * 2. Writes BOM signature in the begin of stream (see comments to ctor).
 */
public class XmlTextWriter extends XmlWriter
{
	/**
	 * Instantiates new XmlTextWriter.
	 * If <tt>encoding</tt> == null, defaults it to utf-8.
	 *
	 * .Net uses internally StreamWriter that writes BOM signature for Encodings
	 * that have it: UTF8, UTF32 (not supported in java yet), UNICODE, BIG_ENDIAN_UNICODE.
	 * We emulate this .Net behaviour by writing BOM manually in XmlTextWriter ctor.
	 *
	 * Note! .Net StreamWriter writes BOM only if the Stream is positioned on the begin.
	 * We can't check where java OutputStream positioned so it is the developer responsibility
	 * to pass "non-used" (i.e. positioned to the begin) OutputStream.
	 */
	public XmlTextWriter(Stream stream, Encoding encoding) throws Exception
	{
		this(new NetOutputStream(stream), encoding);
		mStream = stream;
	}

	/**
	 * Used internally for test units.
	 */
	public XmlTextWriter(OutputStream javaStream, Encoding encoding)
	{
		mJavaStream = javaStream;
		mEncoding = encoding == null ? Encoding.getUTF8() : encoding;

		//JAVA-added: writes BOM signature as .Net backing StreamWriter does.
		//.Net writes the BOM only if sees the Stream begin, but we can't check
		//whether it is begin or not of java's OutputStream - so we always write
		//the BOM.
		writeBOMSignature();

		// Manually configure and get Woodstox XMLStreamWriter implementation.
		// Standard StAX XMLOutputFactory doesn't used because we want to get this concrete implementation.
		//@todo 2 sk reuse factory
		WstxOutputFactory factory = new WstxOutputFactory();
		factory.configureForSpeed();
		factory.getConfig().enableAutomaticEmptyElements(true);
		factory.getConfig().doAddSpaceAfterEmptyElem(true);
		factory.getConfig().doEscapeCr(false);
		factory.getConfig().doValidateStructure(false);
		factory.getConfig().enableAutomaticEndElements(true);
		//use custom writers to emulate .Net escaping
		factory.getConfig().setTextEscaperFactory(DotNetEscapingWriterFactory.forText());
		factory.getConfig().setAttrValueEscaperFactory(DotNetEscapingWriterFactory.forAttributeValue());

		try
		{
            // FIX WORDSJAVA-704: Use encoder instead of charset name to factory XMLStreamWriter2:
            // the encoder may be configured to fallback for malformed or unmapped characters.
			CharsetEncoder encoder = mEncoding.getEncoder().toJava();
			OutputStreamWriter writer = new OutputStreamWriter(mJavaStream, encoder);
			mWriter = (XMLStreamWriter2) factory.createXMLStreamWriter(writer);
		}
		catch (XMLStreamException e){ wrapXmlStreamException(e); }
	}


	public Stream getBaseStream()
	{
		return mStream;
	}

	//JAVA-added
	/**
	 * .Net always writes the BOM (Byte Order Mark) signature at the start of text files.
	 * So we have to do this too.
	 */
	private void writeBOMSignature()
	{
		byte[] bom = mEncoding.getPreamble();
		if (bom.length > 0)
		{
			try
			{
				mJavaStream.write(bom);
			}
			catch (IOException e){ wrapIOException(e); }
		}
	}

    public void setNamespaces(boolean value)
    {
        if (value)
            throw new UnsupportedOperationException("Cannot set XmlTextWriter.Namespaces to true.");
    }

    public /*Formatting*/ int getFormatting()
    {
        return mFormatting;
    }

    // It is a mess with formatting since this ms XmlTextWriter is backed by java writer plus
    // filter writer that is used for pretty-format writing. I.e. the logic of single .Net XmlTextWriter
    // is split to three classes.
    //
    // In .Net source we have 3 public properties: Formatting, Indentation and IndentChar plus
    // one internal boolean field _indented_. These are "global" properties. XmlTextWriter doesn't change
    // these properties internally. .Net XmlTextWriter just writes tags with or without indent according
    // to XML specification.
    //
    // Since our ms XmlTextWriter doesn't write actual tags we should properly indicate to backing
    // IndentingXMLStreamWriter when do and when do not indent specific tags. Let's introduce Indenting
    // setter for this task.
    public void setFormatting(/*Formatting*/ int newFormatting)
    {
        if (newFormatting == mFormatting)
            return;

        if (newFormatting == Formatting.INDENTED)
        {
            if (mIndentingWriter == null)
            {
                mIndentingWriter = new IndentingXMLStreamWriter(null);
                // We have to call setParent because passing this in ctor does not work properly - a bug in Woodstox.
                mIndentingWriter.setParent(mWriter);
            }
            mWriter = mIndentingWriter;
            setIndenting(true);
        }
        else if (newFormatting == Formatting.NONE)
        {
            setIndenting(false);
        }

        mFormatting = newFormatting;
    }

    // JAVA-added to Switch indent on or off.
    private void setIndenting(boolean value)
    {
        if (mWriter instanceof IndentingXMLStreamWriter)
            ((IndentingXMLStreamWriter)mWriter).setIndenting(value);
    }

    /**
     * Gets or sets which character to use for indenting when Formatting is set to Formatting.Indented.
     */
    public char getIndentChar()
    {
        return mIndentChar;
    }

    public void setIndentChar(char value)
    {
        if (value == mIndentChar)
            return;

        mIndentChar = value;
        updateIndentInIndentingWriter();
    }

    /**
     * Gets or sets how many IndentChars to write for each level in the hierarchy when Formatting is set to Formatting.Indented.
     */
    public int getIndentation()
    {
        return mIndentation;
    }

    public void setIndentation(int value)
    {
        if (value == mIndentation)
            return;

        mIndentation = value;
        updateIndentInIndentingWriter();
    }

    private void updateIndentInIndentingWriter()
    {
        if (mWriter instanceof IndentingXMLStreamWriter)
            ((IndentingXMLStreamWriter)mWriter).setIndent(msString.newString(mIndentChar, mIndentation));
    }

    public String getNewLine()
    {
        return mNewLine;
    }

    public void setNewLine(String value)
    {
        if (mNewLine.equals(value))
            return;

        mNewLine = value;

        if (mWriter instanceof IndentingXMLStreamWriter)
            ((IndentingXMLStreamWriter)mWriter).setNewLine(mNewLine);
    }

	/**
	 * Writes out the XML declaration with the version "1.0" and encoding
	 * (without standalone attribute).
	 */
	public void writeStartDocument()
	{
		doWriteStartDocument(null);
	}

	/**
	 * Writes out the XML declaration with the version "1.0", encoding
	 * and the standalone attribute.
	 */
	public void writeStartDocument(boolean standalone)
	{
		String standaloneString = standalone ?  "\"yes\"" : "\"no\"";
		doWriteStartDocument(standaloneString);
	}

	/**
	 * Woodstox writes start document declaration using single quotes, but we need in double
	 * quotes for test units. So let's write the declaration as raw text.
	 */
	private void doWriteStartDocument(String standaloneString)
	{
        StringBuilder declaration = new StringBuilder();
        declaration.append("<?xml version=\"1.0\" encoding=\"");
        declaration.append(mEncoding.getHeaderName());
        declaration.append("\"");
		if (standaloneString != null)
        {
            declaration.append(" standalone=");
            declaration.append(standaloneString);
        }
		declaration.append("?>");

		try
		{
            if (mFormatting == Formatting.INDENTED)
                ((IndentingXMLStreamWriter)mWriter).writeStartDocumentRaw(declaration.toString());
            else
                mWriter.writeRaw(declaration.toString());
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	/**
	 * Writes out a processing instruction with a space between the name and text as follows: <?name text?>.
	 */
	public void writeProcessingInstruction(String name, String text)
	{
		try
		{
			mWriter.writeProcessingInstruction(name, text);
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	/**
	 * Closes any open elements or attributes and puts the writer back in the Start state.
	 */
	public void writeEndDocument()
	{
		try
		{
			mWriter.writeEndDocument();
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	/**
	 * Writes out the DOCTYPE declaration with the specified name and optional attributes.
	  */
	public void writeDocType(String name, String pubid, String sysid, String subset)
	{
		if (name == null || name.length() <= 0)
		    throw new IllegalArgumentException("The argument cannot be null or empty string.\r\nParameter name: name");

		try
		{
			mWriter.writeDTD(name, sysid, pubid, subset);
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	/**
	 * Writes out the specified start tag.
	 */
	public void writeStartElement(String localName)
	{
		try
		{
			mWriter.writeStartElement(localName);
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	/**
	 * Closes one element and pops the corresponding namespace scope.
	 *
	 * Woodstox parser can write automatic empty elements, i.e. as in .Net it writes short end tag "/>"
	 * if the element contains no content.
	 */
	public void writeEndElement()
	{
		try
		{
			mWriter.writeEndElement();
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	/**
	 * Closes one element and pops the corresponding namespace scope.
	 *
	 * This method always writes the full end tag.
	 * If element is empty (not used writeString()) - writes the end tag without indent.
	 */
	public void writeFullEndElement()
	{
		try
		{
			mWriter.writeFullEndElement();
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	/**
	 * Writes out the attribute with the specified LocalName and value.
	  */
	public void writeAttributeString(String localName, String value)
	{
		//JAVA: emulates .net behaviour (in java throws if value == null)
		if (value == null)
			value = "";

		try
		{
			mWriter.writeAttribute(localName, value);
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	//JAVA: simplified method may be works not like .net one.
	public void writeWhitespace(String ws)
	{
		//i don't sure that this works like .net one.
		writeRaw(ws);
	}

	/**
	 * Writes out the specified text content.
	 *
	 * C# WriteString does the following:
	 *
	 * 1. The characters &, <, and > are replaced with &amp;, &lt;, and &gt;, respectively.
	 * 2. Character values in the range 0x-0x1F (excluding white space characters 0x9, 0x10, and 0x13) are replaced with numeric character entities (&#0; through &#0x1F).
	 * 3. If WriteString is called in the context of an attribute value, double and single quotes are replaced with &quot; and &apos; respectively.
	 */
	public void writeString(String text)
	{
		if (text == null || text.length() == 0)
			return;

		try
		{
			mWriter.writeCharacters(text);
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	/**
	 * Writes raw markup manually.
	 */
	public void writeRaw(String text)
	{
		try
		{
			mWriter.writeRaw(text);
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

    public void writeCData(String text)
    {
        try
        {
            mWriter.writeCData(text);
        }
        catch (XMLStreamException e) { wrapXmlStreamException(e); }
    }

	/**
	 * Writes out a comment <!--...--> containing the specified text.
	 */
	public void writeComment(String text)
	{
		try
		{
			mWriter.writeComment(text);
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	public void flush()
	{
		try
		{
			// SK HACK: Sun jdk1.4, jdk1.5 Charset.encode() flushing bug: doesn't saves last few bytes in several circumstances.
            // RK There was adding a space at the end and it was causing gold difference so I changed to \r\n.
			if (mEncoding.getCodePage() == EncodingProvider.CODE_PAGE_UTF7)
				writeRaw("\r\n");

			//flushes underlying stream as well
			mWriter.flush();
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

	/**
	 * Closes an output stream as well.
	 */
	public void close()
	{
		try
		{
			mWriter.close();
		}
		catch (XMLStreamException e) { wrapXmlStreamException(e); }
	}

    /**
     * Wraps javax.xml.stream.XMLStreamException into runtime Exception.
     *
     * XMLStreamException (as part of external lib) is obfuscated so we have to provide clear
     * point to the cause instead of obfuscated symbols:)
     */
    private void wrapXmlStreamException(XMLStreamException e)
    {
        throw new IllegalStateException("XMLStreamException: " + e.getMessage(), e);
    }

    private void wrapIOException(IOException e)
    {
        throw new IllegalStateException(e);
    }

    private /*Formatting*/ int mFormatting = Formatting.NONE;
    private char mIndentChar = ' ';
    private int mIndentation = 2;
    private String mNewLine = "\r\n";

	// Using this interface because we need access to stax2 methods.
	private XMLStreamWriter2 mWriter;
    private IndentingXMLStreamWriter mIndentingWriter;
	private OutputStream mJavaStream;
	private Stream mStream;
	private Encoding mEncoding;
}
