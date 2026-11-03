package com.aspose.ms.System.Diagnostics;

import com.aspose.ms.java.debug.JavaDebug;

/**
 * Provides the abstract base class for the listeners who monitor trace and debug output.
 */
public abstract class TraceListener
{

    /**
     * @return value indicating whether the trace listener is thread safe. At the moment it is always <code>false</code>
     * <p/>
     * The value of IsThreadSafe is used to determine whether to use a global lock when writing to the listener.
     * If the value of IsThreadSafe is false, the global lock is used regardless of the value of UseGlobalLock.
     * The global lock is not used only if the value of IsThreadSafe is true and the value of UseGlobalLock is false.
     * The default behavior is to use the global lock whenever writing to the listener.
     */
    public boolean isThreadSafe()
    {
        return false;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public abstract void writeLine(String message);

    public abstract void writeLine(String message, String categoryName);

    public abstract void write(String message);

    public abstract void write(String message, String category);

    public abstract void writeIndent();

    /**
     * When overridden in a derived class, flushes the output buffer.
     */
    public void flush()
    {

    }

    /**
     * When overridden in a derived class, closes the output stream so it no longer receives tracing or debugging output.
     */
    public void close()
    {

    }

    public void setIndentLevel(int indentLevel)
    {
        mIndentLevel = indentLevel;
    }

    public void setIndentSize(Integer indentSize)
    {
        mIndentSize = indentSize;
    }

    public String getIndent()
    {
        return indent(mIndentLevel, mIndentSize);
    }

    private static String indent(int level, int size)
    {
        char[] indentChars = new char[level * size];
        if (indentChars.length == 0)
            return "";
        for (int i = 0; i < indentChars.length; i++)
            indentChars[i] = ' ';

        return new String(indentChars);
    }

    /**
     * Initializes a new instance of the TraceListener class.
     */
    public TraceListener()
    {
        // left blank
    }

    /**
     * Initializes a new instance of the TraceListener class using the specified name as the listener.
     *
     * @param name the specified name as the listener.
     */
    public TraceListener(String name)
    {
        this.mName = name;
    }

    private String mName;
    protected static final boolean gTestMode = JavaDebug.ON;
    private int mIndentLevel = 0;
    private int mIndentSize = 4;
}
