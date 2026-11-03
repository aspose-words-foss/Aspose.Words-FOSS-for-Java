//Konstantin Sidorenko, 15/09/2010

package com.aspose.ms.java.xml;

import java.io.IOException;
import java.io.Writer;

/**
 * Emulates .Net escaping.
 * .Net XmlTextWriter writes text content using original escaping rules.
 * Java xml writers can't reproduce this escaping using standard settings.
 * <p/>
 * Fortunately, Woodstox has feature for creating and using custom escaping writers.
 * <p/>
 * Most write methods adopted from Woodstox BufferingXmlWriter.
 */
public class DotNetEscapingWriter extends Writer
{
    /**
     * EscapingWriterFactory uses this ctor.
     */
    public DotNetEscapingWriter(Writer w, boolean isAttributeValueWriter)
    {
        mWriter = w;
        mIsAttributeValueWriter = isAttributeValueWriter;
    }

    /**
     * Implements custom (.Net-like) escaping. Flushes escaped characters to calling xml writer
     * (that writes the characters as is, using writeRaw()).
     * <p/>
     * sk: the bodies of both write() methods are almost identical except of c assigned from
     * char array or from string. The assignment is inside of inner loop and we can't extract
     * a common code without performance degradation. So note: these methods not duplicates it
     * is by-design:)
     */
    public void write(char[] cbuf, int offset, int len) throws IOException
    {
        //Outer loop.
        for (len += offset; offset < len; offset++)
        {
            String ent = null;
            int start = offset;
            int c = 0;
            //Inner loop: break up the loop to flush array of plain symbols if we meet an entity or if we get to end.
            //We have to collect the array because flushing of single symbol is expensive in Woodstox.
            for (; offset < len; offset++)
            {
                c = cbuf[offset];//the only different line in both "duplicate" methods.
                if (c <= HIGHEST_ENCODABLE_TEXT_CHAR)
                {
                    if (c < 0x0020)
                    {
                        if (mIsAttributeValueWriter)
                        {
                            // All characters are to be entetized in attribute values.
                            break;
                        }
                        else
                        {
                            if ((c != '\n') && (c != '\t') && (c != '\r'))  // These chars don't require escaping.
                                break;
                        }
                    }
                    else if (c == '<')
                    {
                        ent = "&lt;";
                        break;
                    }
                    else if (c == '&')
                    {
                        ent = "&amp;";
                        break;
                    }
                    else if (c == '>')
                    {
                        //.Net does this
                        ent = "&gt;";
                        break;
                    }
                    else if (mIsAttributeValueWriter && c == '"')
                    {
                        //.Net does this for attribute value
                        ent = "&quot;";
                        break;
                    }
                }
                // otherwise ok: continue inner loop.
            }
            //Flush array of plain symbols if any.
            int outLen = offset - start;
            if (outLen > 0)
                mWriter.write(cbuf, start, outLen);

            //write entities if we meet them
            if (ent != null)
                mWriter.write(ent);
            else if (offset < len)
                writeAsEntity(c);
        }
    }

    public void write(String text) throws IOException
    {
        write(text, 0, text.length());
    }

    /**
     * Implements custom (.Net-like) escaping. Flushes escaped characters to calling xml writer
     * (that writes the characters as is using writeRaw()).
     * <p/>
     * sk: the bodies of both write() methods are almost identical except of c assigned from
     * char array or from string. The assignment is inside of inner loop and we can't extract
     * a common code without performance degradation. So note: these methods not duplicates it
     * is by-design:)
     */
    public void write(String text, int offset, int len) throws IOException
    {
        //Outer loop.
        for (len += offset; offset < len; offset++)
        {
            String ent = null;
            int start = offset;
            int c = 0;
            //Inner loop: break up the loop to flush array of plain symbols if we meet an entity or if we get to end.
            //We have to collect the array because flushing of single symbol is expensive in Woodstox.
            for (; offset < len; offset++)
            {
                c = text.charAt(offset);//the only different line in both "duplicate" methods.
                if (c <= HIGHEST_ENCODABLE_TEXT_CHAR)
                {
                    if (c < 0x0020)
                    {
                        if (mIsAttributeValueWriter)
                        {
                            // All characters are to be entetized in attribute values.
                            break;
                        }
                        else
                        {
                            if ((c != '\n') && (c != '\t') && (c != '\r'))  // These chars don't require escaping.
                                break;
                        }
                    }
                    else if (c == '<')
                    {
                        ent = "&lt;";
                        break;
                    }
                    else if (c == '&')
                    {
                        ent = "&amp;";
                        break;
                    }
                    else if (c == '>')
                    {
                        //.Net does this
                        ent = "&gt;";
                        break;
                    }
                    else if (mIsAttributeValueWriter && c == '"')
                    {
                        //.Net does this for attribute value
                        ent = "&quot;";
                        break;
                    }
                }
                // otherwise ok: continue inner loop.
            }
            //Flush array of plain symbols if any.
            int outLen = offset - start;
            if (outLen > 0)
                mWriter.write(text, start, outLen);

            //write entities if we meet them
            if (ent != null)
                mWriter.write(ent);
            else if (offset < len)
                writeAsEntity(c);
        }
    }

    public void flush() throws IOException
    {
        mWriter.flush();
    }

    public void close() throws IOException
    {
        flush();
        mWriter.close();
    }

    protected final void writeAsEntity(int c) throws IOException
    {
        char[] buf = new char[10];// &#x [up to 6 hex digits] ;
        int ptr = 0;
        buf[ptr++] = '&';

        // Can use more optimal notation for 8-bit ascii stuff:
        if (c < 256)
        {
            /* Also; although not really mandatory, let's also
                * use pre-defined entities where possible.
                */
            if (c == '&')
            {
                buf[ptr++] = 'a';
                buf[ptr++] = 'm';
                buf[ptr++] = 'p';
            }
            else if (c == '<')
            {
                buf[ptr++] = 'l';
                buf[ptr++] = 't';
            }
            else if (c == '>')
            {
                buf[ptr++] = 'g';
                buf[ptr++] = 't';
            }
            else if (c == '\'')
            {
                buf[ptr++] = 'a';
                buf[ptr++] = 'p';
                buf[ptr++] = 'o';
                buf[ptr++] = 's';
            }
            else if (c == '"')
            {
                buf[ptr++] = 'q';
                buf[ptr++] = 'u';
                buf[ptr++] = 'o';
                buf[ptr++] = 't';
            }
            else
            {
                buf[ptr++] = '#';
                buf[ptr++] = 'x';
                // Can use shortest quoting for tab, cr, lf:
                if (c >= 16)
                {
                    int digit = (c >> 4);
                    buf[ptr++] = (char) ((digit < 10) ? ('0' + digit) : (('A' - 10) + digit));
                    c &= 0xF;
                }
                buf[ptr++] = (char) ((c < 10) ? ('0' + c) : (('A' - 10) + c));
            }
        }
        else
        {
            buf[ptr++] = '#';
            buf[ptr++] = 'x';

            // Ok, let's write the shortest possible sequence then:
            int shift = 20;
            int origPtr = ptr;

            do
            {
                int digit = (c >> shift) & 0xF;
                if (digit > 0 || (ptr != origPtr))
                {
                    buf[ptr++] = (char) ((digit < 10) ? ('0' + digit) : (('A' - 10) + digit));
                }
                shift -= 4;
            } while (shift > 0);
            c &= 0xF;
            buf[ptr++] = (char) ((c < 10) ? ('0' + c) : (('A' - 10) + c));
        }
        buf[ptr++] = ';';
        mWriter.write(buf, 0, ptr);
    }

    /**
     * Underling XmlWriter. All data should be flashed to it.
     */
    private Writer mWriter;
    /**
     * If true - escapes double quote (") symbols as .Net does when writes attribute value.
     */
    private boolean mIsAttributeValueWriter;
    /**
     * Highest valued character that may need to be encoded Dwhen writing attribute values.
     */
    protected final static int HIGHEST_ENCODABLE_TEXT_CHAR = (int) '>';
}
