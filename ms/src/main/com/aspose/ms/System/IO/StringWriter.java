package com.aspose.ms.System.IO;

import com.aspose.ms.System.Text.Encoding;

/**
 * Copycat of .Net System.IO.StringWriter
 * <p>
 * This class implements a text writer that writes to a string buffer and allows
 * the resulting sequence of characters to be presented as a string.
 * <p>
 * Note that only methods used by Aspose.Words are implemented.
 * ported by sk May 16, 2007
 */
public class StringWriter extends TextWriter
{
    public StringWriter(StringBuilder sb)
    {
        _sb = sb;
    }

    private StringBuilder _sb;

    public StringWriter()
    {
        _sb = new StringBuilder();
    }

    public Encoding getEncoding()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Writes a string to the underlying string buffer. If the given string is
     * null, nothing is written (note: java.lang.StringWriter writes four
     * characters "null" in such a case).
     */
    public void write(String value)
    {
        if (value != null) _sb.append(value);
    }

    @Override
    public void write(char value) throws Exception
    {
        _sb.append(value);
    }

    /**
     * Returns a string containing the characters written to this TextWriter
     * so far.
     */
    public String toString()
    {
        return _sb.toString();
    }

}
