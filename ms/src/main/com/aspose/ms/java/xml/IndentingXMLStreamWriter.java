// 13/12/10 by Roman Korchagin
package com.aspose.ms.java.xml;

import com.aspose.ms.System.msString;
import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.util.StreamWriter2Delegate;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * A filter that indents an XML stream. To apply it, construct a filter that
 * contains another {@link XMLStreamWriter}, which you pass to the constructor.
 * Then call methods of the filter instead of the contained stream. For example:
 * <p/>
 * <p/>
 * The filter inserts characters to format the document as an outline, with
 * nested elements indented. Basically, it inserts a line break and whitespace
 * before:
 * <ul>
 * <li>each DTD, processing instruction or comment that's not preceded by data</li>
 * <li>each starting tag that's not preceded by data</li>
 * <li>each ending tag that's preceded by nested elements but not data</li>
 * </ul>
 * This works well with 'data-oriented' XML, wherein each element contains
 * either data or nested elements but not both. It can work badly with other
 * styles of XML. For example, the data in a 'mixed content' document are apt to
 * be polluted with indentation characters.
 * <p/>
 * Indentation can be adjusted by setting the newLine and indent properties. But
 * set them to whitespace only, for best results. Non-whitespace is apt to cause
 * problems, for example when this class attempts to insert newLine before the
 * root element.
 * <p/>
 * Inspired by http://java.net/projects/stax-utils/sources/svn/content/trunk/src/javanet/staxutils/IndentingXMLStreamWriter.java?rev=236
 */
public class IndentingXMLStreamWriter extends StreamWriter2Delegate
{
    public IndentingXMLStreamWriter(XMLStreamWriter2 out)
    {
        super(out);
    }

    /**
     * How deeply nested the current scope is. The root element is depth 1.
     */
    private int depth = 0; // document scope

    /**
     * stack[depth] indicates what's been written into the current scope.
     */
    private int[] stack = new int[]{0, 0, 0, 0}; // nothing written yet

    private static final int WROTE_MARKUP = 1;

    private static final int WROTE_DATA = 2;

    private boolean indenting;

    private String indent = "\t";

    private String newLine = "\r\n";

    /**
     * newLine followed by copies of indent.
     */
    private char[] linePrefix = null;

    /**
     * Used by calling classes (HtmlBuilderImpl, XmlTextWriter) to turn on/off indents for different tags, i.e.
     * 'p' tag should be indented but 'a' shouldn't, etc.
     */
    public void setIndenting(boolean indenting)
    {
        this.indenting = indenting;
    }

    public void setIndent(String indent)
    {
        if (!indent.equals(this.indent))
        {
            this.indent = indent;
            linePrefix = null;
        }
    }

    public String getIndent()
    {
        return indent;
    }

    public void setNewLine(String newLine)
    {
        if (!newLine.equals(this.newLine))
        {
            this.newLine = newLine;
            linePrefix = null;
        }
    }

    public String getNewLine()
    {
        return newLine;
    }

    public void writeStartDocument() throws XMLStreamException
    {
        beforeMarkup();
        getParent().writeStartDocument();
        afterMarkup();
    }

    public void writeStartDocument(String version) throws XMLStreamException
    {
        beforeMarkup();
        getParent().writeStartDocument(version);
        afterMarkup();
    }

    public void writeStartDocument(String encoding, String version) throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeStartDocument(encoding, version);
        afterMarkup();
    }

    public void writeStartDocumentRaw(String declaration) throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeRaw(declaration);
        afterMarkup();
    }

    public void writeDTD(String dtd) throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeDTD(dtd);
        afterMarkup();
    }

    public void writeDTD(String rootName, String systemId, String publicId, String internalSubset) throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeDTD(rootName, systemId, publicId, internalSubset);
        afterMarkup();
    }

    public void writeRaw(String text) throws XMLStreamException
    {
        mDelegate2.writeRaw(text);
        afterData(text);
    }

    public void writeProcessingInstruction(String target) throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeProcessingInstruction(target);
        afterMarkup();
    }

    public void writeProcessingInstruction(String target, String data) throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeProcessingInstruction(target, data);
        afterMarkup();
    }

    public void writeComment(String data) throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeComment(data);
        afterMarkup();
    }

    public void writeEmptyElement(String localName) throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeEmptyElement(localName);
        afterMarkup();
    }

    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeEmptyElement(namespaceURI, localName);
        afterMarkup();
    }

    public void writeEmptyElement(String prefix, String localName, String namespaceURI)
            throws XMLStreamException
    {
        beforeMarkup();
        mDelegate2.writeEmptyElement(prefix, localName, namespaceURI);
        afterMarkup();
    }

    public void writeStartElement(String localName) throws XMLStreamException
    {
        beforeStartElement();
        mDelegate2.writeStartElement(localName);
        afterStartElement();
        mIsEmptyElement = true;
    }

    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException
    {
        beforeStartElement();
        mDelegate2.writeStartElement(namespaceURI, localName);
        afterStartElement();
        mIsEmptyElement = true;
    }

    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException
    {
        beforeStartElement();
        mDelegate2.writeStartElement(prefix, localName, namespaceURI);
        afterStartElement();
        mIsEmptyElement = true;
    }

    public void writeCharacters(String text) throws XMLStreamException
    {
        mDelegate2.writeCharacters(text);
        afterData(text);
    }

    public void writeCharacters(char[] text, int start, int len) throws XMLStreamException
    {
        mDelegate2.writeCharacters(text, start, len);
        afterData((len > 0) ? " " : "");
    }

    public void writeCData(String data) throws XMLStreamException
    {
        mDelegate2.writeCData(data);
        afterData(data);
    }

    public void writeEntityRef(String name) throws XMLStreamException
    {
        mDelegate2.writeEntityRef(name);
        afterData(name);
    }

    public void writeEndElement() throws XMLStreamException
    {
        beforeEndElement();
        mDelegate2.writeEndElement();
        afterEndElement();

        mIsEmptyElement = false;
    }

    public void writeFullEndElement() throws XMLStreamException
    {
        // This seems to a special case in .NET. When writing a full end for an empty element, .NET puts the end tag on a separate line.
        if (mIsEmptyElement)
            writeNewLine(depth - 1);
        else
            beforeEndElement();

        mDelegate2.writeFullEndElement();
        afterEndElement();

        mIsEmptyElement = false;
    }

    public void writeEndDocument() throws XMLStreamException
    {
        try
        {
            while (depth > 0)
            {
                writeEndElement(); // indented
            }
        }
        catch (Exception ignored)
        {
        }
        //sk: deleted because the method closes underlying stream and buffers - causes errors in some cases (utf-7).
//        mDelegate2.writeEndDocument();
        //sk: corrected too. as we don't close streams the method adds extra new line and some gold tests
        //(epub's .html, .ncx and .opf files) failed.
        afterEndDocument();
    }

    /**
     * Prepare to write markup, by writing a new line and indentation.
     */
    protected void beforeMarkup()
    {
        int soFar = stack[depth];
        if ((soFar & WROTE_DATA) == 0 // no data in this scope
                && (depth > 0 || soFar != 0)) // not the first line
        {
            try
            {
                writeNewLine(depth);
                if (depth > 0 && getIndent().length() > 0)
                {
                    afterMarkup(); // indentation was written
                }
            }
            catch (Exception e)
            {
            }
        }
    }

    /**
     * Note that markup or indentation was written.
     */
    protected void afterMarkup()
    {
        stack[depth] |= WROTE_MARKUP;
    }

    /**
     * Note that data were written.
     */
    protected void afterData(String text)
    {
        stack[depth] |= WROTE_DATA;

        if (!msString.isNullOrEmpty(text))
            mIsEmptyElement = false;
    }

    /**
     * Prepare to start an element, by allocating stack space.
     */
    protected void beforeStartElement()
    {
        beforeMarkup();
        if (stack.length <= depth + 1)
        {
            // Allocate more space for the stack:
            int[] newStack = new int[stack.length * 2];
            System.arraycopy(stack, 0, newStack, 0, stack.length);
            stack = newStack;
        }
        stack[depth + 1] = 0; // nothing written yet
    }

    /**
     * Note that an element was started.
     */
    protected void afterStartElement()
    {
        afterMarkup();
        ++depth;
    }

    /**
     * Prepare to end an element, by writing a new line and indentation.
     */
    protected void beforeEndElement()
    {
        if (depth > 0 && stack[depth] == WROTE_MARKUP)
        { // but not data
            try
            {
                writeNewLine(depth - 1);
            }
            catch (Exception ignored)
            {
            }
        }
    }

    /**
     * Note that an element was ended.
     */
    protected void afterEndElement()
    {
        if (depth > 0)
        {
            --depth;
        }
    }

    /**
     * Note that a document was ended.
     */
    protected void afterEndDocument()
    {
        //sk: deleted because writes extra newline - and epub tests falls since .html, .ncx and .opf compared as
        //text files. anyway this doesn't work in original code since all streams already closed by writeEndDocument().
//        if (stack[depth = 0] == WROTE_MARKUP) { // but not data
//            try {
//                writeNewLine(0);
//            } catch (Exception ignored) {
//            }
//        }
        stack[depth] = 0; // start fresh
    }

    /**
     * Write a line separator followed by indentation.
     */
    protected void writeNewLine(int indentation) throws XMLStreamException
    {
        if (!this.indenting)
            return;

        final int newLineLength = getNewLine().length();
        final int prefixLength = newLineLength + (getIndent().length() * indentation);
        if (prefixLength > 0)
        {
            if (linePrefix == null)
            {
                linePrefix = (getNewLine() + getIndent()).toCharArray();
            }
            while (prefixLength > linePrefix.length)
            {
                // make linePrefix longer:
                char[] newPrefix = new char[newLineLength
                        + ((linePrefix.length - newLineLength) * 2)];
                System.arraycopy(linePrefix, 0, newPrefix, 0, linePrefix.length);
                System.arraycopy(linePrefix, newLineLength, newPrefix, linePrefix.length,
                        linePrefix.length - newLineLength);
                linePrefix = newPrefix;
            }
            mDelegate2.writeCharacters(linePrefix, 0, prefixLength);
        }
    }

    private boolean mIsEmptyElement;
}
