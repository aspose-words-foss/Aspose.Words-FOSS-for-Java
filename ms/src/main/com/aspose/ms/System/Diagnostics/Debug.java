package com.aspose.ms.System.Diagnostics;

import com.aspose.ms.java.debug.JavaDebug;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides a set of methods and properties that help debug your code. This class cannot be inherited.
 */
public final class Debug
{
    /**
     * @param value the number of spaces in an indent.
     */
    public static void setIndentSize(int value)
    {
        if (gTestMode)
        {
            gIndentSize.set(value);
            updateIndentSize(gIndentSize.get());
        }
    }

    /**
     * @return the number of spaces in an indent.
     */
    public static int getIndentSize()
    {
        return gIndentSize.get();
    }

    /**
     * @param value the number of spaces in an indent.
     */
    public static void setIndentLevel(int value)
    {
        if (gTestMode)
        {
            gIndentLevel.set(new AtomicInteger(value));
            updateIndentLevel(gIndentLevel.get().get());
        }
    }

    /**
     * @return value the number of spaces in an indent.
     */
    public static int getIndentLevel()
    {
        return gIndentLevel.get().get();
    }

    /**
     * Decreases the current IndentLevel by one.
     */
    public static void unindent()
    {
        if (gTestMode)
        {
            gIndentLevel.get().decrementAndGet();
            updateIndentLevel(gIndentLevel.get().get());
        }
    }

    /**
     * Increases the current IndentLevel by one.
     */
    public static void indent()
    {
        if (gTestMode)
        {
            gIndentLevel.get().incrementAndGet();
            updateIndentLevel(gIndentLevel.get().get());
        }
    }

    public static void flush()
    {
        if (gTestMode)
        {
            for (TraceListener listener : getListeners())
                listener.flush();
        }
    }

    /**
     * @param value indicating whether Flush should be called on the Listeners after every write.
     */
    public static void setAutoFlush(boolean value)
    {
        gAutoFlush.set(value);
    }

    /**
     * @return value indicating whether Flush should be called on the Listeners after every write.
     */
    public static boolean getAutoFlush()
    {
        return gAutoFlush.get();
    }

    /**
     * Flushes the output buffer and then calls the Close method on each of the Listeners.
     */
    public static void close()
    {
        if (gTestMode)
        {
            flush();
            for (TraceListener listener : getListeners())
                listener.close();
        }
    }

    /**
     * Emits the specified error message.
     */
    public static void fail(String expression)
    {
        assertTrue(false, expression);
    }

    /**
     * Checks for a condition; if the condition is false, displays a message box that shows the call stack.
     */
    public static void assertTrue(boolean expression)
    {
        assertTrue(expression, "", "");
    }

    /**
     * Checks for a condition; if the condition is false, outputs a specified message and displays a message box that shows the call stack.
     */
    public static void assertTrue(boolean expression, String message)
    {
        assertTrue(expression, message, "");
    }

    /**
     * Checks for a condition; if the condition is false, outputs two specified messages and displays a message box that shows the call stack.
     */
    public static void assertTrue(boolean condition, String message, String detailMessage)
    {
        if (gTestMode && !condition)
            writeLine(message + " " + detailMessage, "Fail");
    }

    /**
     * Writes a category name and message to the trace listeners in the Listeners collection.
     */
    public static void writeLine(String message, String categoryName)
    {
        if (gTestMode)
        {
            for (TraceListener listener : getListeners())
                listener.writeLine(message, categoryName);

            if (gAutoFlush.get())
                flush();
        }
    }

    public static void writeLine(Object obj, String categoryName)
    {
        writeLine(toString(obj), categoryName);
    }

    public static void writeLine(String message)
    {
        writeLine(message, null);
    }

    /**
     * Writes the value of the object's ToString method to the trace listeners in the Listeners collection.
     */
    public static void writeLine(Object obj)
    {
        writeLine(toString(obj));
    }

    /**
     * Writes a message to the trace listeners in the Listeners collection.
     */
    public static void write(String message)
    {
        write(message, null);
    }

    public static void write(Object obj)
    {
        write(toString(obj));
    }

    public static void write(String message, String category)
    {
        if (gTestMode)
        {
            for (TraceListener listener : getListeners())
                listener.write(message, category);

            if (gAutoFlush.get())
                flush();
        }
    }

    public static void write(Object obj, String category)
    {
        write(toString(obj), category);
    }

    /**
     * Writes a message to the trace listeners in the Listeners collection if a condition is true.
     */
    public static void writeLineIf(boolean condition, String message)
    {
        if (gTestMode && condition)
            writeLine(message);
    }

    public static void writeLineIf(boolean condition, Object obj)
    {
        writeLineIf(condition, toString(obj));
    }

    public static void writeLineIf(boolean condition, String message, String category)
    {
        if (gTestMode && condition)
            writeLine(message, category);
    }

    public static void writeLineIf(boolean condition, Object obj, String category)
    {
        writeLineIf(condition, toString(obj), category);
    }

    public static void writeIf(boolean condition, String message)
    {
        if (gTestMode && condition)
            write(message);
    }

    public static void writeIf(boolean condition, Object obj)
    {
        writeIf(condition, toString(obj));
    }

    public static void writeIf(boolean condition, String messasge, String category)
    {
        if (gTestMode && condition)
            write(messasge, category);
    }

    public static void writeIf(boolean condition, Object obj, String category)
    {
        writeIf(condition, toString(obj), category);
    }

    /**
     * @return collection of listeners that is monitoring the debug output.
     */
    public static TraceListenerCollection getListeners()
    {
        return gListeners.get();
    }

    /**
     * Default ctor with just one {@link DefaultTraceListener}.
     */
    private Debug()
    {
        //
    }

    //////////////// HELPERS //////////////////////

    private static String toString(Object obj)
    {
        return obj != null ? obj.toString() : "null";
    }

    private static void updateIndentLevel(int intentLevel)
    {
        for (TraceListener listener : getListeners())
            listener.setIndentLevel(intentLevel);
    }

    private static void updateIndentSize(int indentSize)
    {
        for (TraceListener listener : getListeners())
            listener.setIndentSize(indentSize);
    }

    public static final boolean gTestMode = JavaDebug.ON;
    private static final ThreadLocal<TraceListenerCollection> gListeners = new ThreadLocal<TraceListenerCollection>()
    {
        @Override
        protected TraceListenerCollection initialValue()
        {
            // init lister collection with just one default listener for debug logging.
            TraceListenerCollection collection = new TraceListenerCollection();
            collection.add(new DefaultTraceListener());
            return collection;
        }
    };
    private static final ThreadLocal<Boolean> gAutoFlush = new ThreadLocal<Boolean>()
    {
        @Override
        protected Boolean initialValue()
        {
            return false;
        }
    };

    //The number of spaces in an indent. The default is four.
    private static final ThreadLocal<Integer> gIndentSize = new ThreadLocal<Integer>()
    {
        @Override
        protected Integer initialValue()
        {
            return 4;
        }
    };

    //The indent level. The default is 0.
    private static final ThreadLocal<AtomicInteger> gIndentLevel = new ThreadLocal<AtomicInteger>()
    {
        @Override
        protected AtomicInteger initialValue()
        {
            return new AtomicInteger(0);
        }
    };
}
