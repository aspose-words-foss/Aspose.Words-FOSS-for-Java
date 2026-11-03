//Konstantin Sidorenko 8/12/2010

package com.aspose.ms.java.xml;

import org.codehaus.stax2.io.EscapingWriterFactory;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Used to add custom escaping writer to Woodstox XMLStreamWriter.
 */
public class DotNetEscapingWriterFactory implements EscapingWriterFactory
{
    private DotNetEscapingWriterFactory(boolean attributeValueWriter)
    {
        mAttributeValueWriter = attributeValueWriter;
    }

    public static DotNetEscapingWriterFactory forText()
    {
        return new DotNetEscapingWriterFactory(false);
    }

    public static DotNetEscapingWriterFactory forAttributeValue()
    {
        return new DotNetEscapingWriterFactory(true);
    }

    /**
     * Method called to create an instance of escaping writer that will output content using specified writer, and escaping
     * necessary characters (depending on both type [attr, element text] and encoding).
     *
     * @param w   Underlying writer that the encoding writer should output
     * @param enc Encoding to use, as specified by the stream writer (based on information application has passed)
     */
    public Writer createEscapingWriterFor(Writer w, String enc) throws UnsupportedEncodingException
    {
        return new DotNetEscapingWriter(w, mAttributeValueWriter);
    }

    /**
     * Method called to create an instance of escaping writer that will output to the specified stream, using the specified
     * encoding, and escaping necessary characters (depending on both type [attr, element text] and encoding).
     *
     * @param out Underlying stream that the encoding writer should output using
     * @param enc Encoding to use, as specified by the stream writer (based on information application has passed)
     */
    public Writer createEscapingWriterFor(OutputStream out, String enc) throws UnsupportedEncodingException
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private boolean mAttributeValueWriter;
}
