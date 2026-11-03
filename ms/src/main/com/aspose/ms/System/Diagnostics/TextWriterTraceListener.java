package com.aspose.ms.System.Diagnostics;

import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.ms.System.IO.StringWriter;
import com.aspose.ms.System.IO.TextWriter;

/**
 * Directs tracing or debugging output to a TextWriter or to a Stream, such as FileStream.
 * https://msdn.microsoft.com/en-us/library/system.diagnostics.textwritertracelistener(v=vs.110).aspx
 */
public class TextWriterTraceListener extends TraceListener
{

    @Override
    public void writeLine(String message)
    {
        writeLine(message, null);
    }

    @Override
    public void writeLine(String message, String categoryName)
    {
        writeIndent();
        writeCategory(categoryName);
        writeToWriter(message, true);
    }

    @Override
    public void write(String message)
    {
        write(message, null);
    }

    @Override
    public void write(String message, String categoryName)
    {
        writeIndent();
        writeCategory(categoryName);
        writeToWriter(message);
    }

    @Override
    public void writeIndent()
    {
        writeToWriter(getIndent());
    }

    @Override
    public void close()
    {
        if (mWriter != null)
            try
            {
                mWriter.close();
            }
            catch (Exception e)
            {
                if (gTestMode)
                    e.printStackTrace();
            }
    }

    @Override
    public void flush()
    {
        if (mWriter != null)
            try
            {
                mWriter.flush();
            }
            catch (Exception e)
            {
                if (gTestMode)
                    e.printStackTrace();
            }
    }


    public void setWriter(StreamWriter writer)
    {
        this.mWriter = writer;
    }

    private void writeToWriter(String message)
    {
        writeToWriter(message, false);
    }

    private void writeToWriter(String message, boolean newLine)
    {
        if (mWriter != null)
        {
            try
            {
                if (newLine)
                    mWriter.writeLine(message);
                else
                    mWriter.write(message);
            }
            catch (Exception e)
            {
                if (gTestMode)
                    e.printStackTrace();
            }
        }
    }

    private void writeCategory(String categoryName)
    {
        if (categoryName != null && !categoryName.isEmpty())
            writeToWriter(categoryName + ": ");
    }

    public TextWriterTraceListener()
    {}

    public TextWriterTraceListener(StringWriter debugWriter)
    {
        this.mWriter = debugWriter;
    }

    private TextWriter mWriter;
}
