// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// by Michael Morozoff
package com.aspose;

import com.aspose.ms.System.Diagnostics.DefaultTraceListener;
import com.aspose.ms.System.Diagnostics.TraceListenerCollection;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.ms;



/// <summary>
/// Replaces standard <see cref="System.Diagnostics.Debug"/> class.n
/// By default routes all calls to the standard implementation.
/// Provides additional configuration options.
/// </summary>
//[JavaManual("This is only really need in C# since it just adds some debugging facilities (like break on assert?).")]
//[CppManual("This is only really need in C# since it just adds some debugging facilities (like break on assert?).")]
public /*static*/ class Debug
{
    /* Simulation of static class by using private constructor */
    private Debug()
    {}

    /// <summary>
    /// If set will show assert dialog if assert fails.
    /// </summary>
    public static void showDialogOnAssert(boolean value)
    {
        DefaultTraceListener listener = ms.as(getListeners().get("Default"), DefaultTraceListener.class);
        if (null != listener)
            listener.setAssertUiEnabled(value);
    }

    /// <summary>
    /// If set will call debugger if assert fails.
    /// </summary>
    public static void breakOnAssert(boolean value)
    {
        //JAVA-deleted
        //gIsBreakOnAssert.set(value);
    }
    //private static boolean gIsBreakOnAssert;

    /// <summary>
    /// If set will throw exception if assert fails.
    /// </summary>
    public static void throwOnAssert(boolean value)
    {
        gIsThrowOnAssert.set(value);
    }

    /// <summary>
    /// Sets ThrowOnAssert(true) and ShowDialogOnAssert(false).
    /// </summary>
    public static void setTestDefaults()
    {
        throwOnAssert(true);
        showDialogOnAssert(false);
    }

    /// <summary>
    /// </summary>
    public static void assertTrue(boolean condition)
    {
        Debug.assertTrue(condition, "", "");
    }

    /// <summary>
    /// </summary>
    public static void assertTrue(boolean condition, String message)
    {
        Debug.assertTrue(condition, message, "");
    }

    /// <summary>
    /// </summary>
    public static void assertTrue(boolean condition, String message, String detailMessage)
    {
        if (!condition)
        {
            //JAVA-deleted
            //if (gIsBreakOnAssert.get())
            //    Debugger.Break();
            if (gIsThrowOnAssert.get())
                throw new IllegalStateException(getCallingStack());
            else
                com.aspose.ms.System.Diagnostics.Debug.assertTrue(false, message, detailMessage);
        }
    }

    /// <summary>
    /// </summary>
    public static void fail(String message)
    {
        Debug.assertTrue(false, message);
    }

    /// <summary>
    /// </summary>
    public static void fail(String message, String detailMessage)
    {
        Debug.assertTrue(false, message, detailMessage);
    }

    /// <summary>
    /// </summary>
    public static void write(String message)
    {
        com.aspose.ms.System.Diagnostics.Debug.write(message);
    }

    /// <summary>
    /// </summary>
    public static void write(Object value)
    {
        com.aspose.ms.System.Diagnostics.Debug.write(value);
    }

    /// <summary>
    /// </summary>
    public static void write(String message, String category)
    {
        com.aspose.ms.System.Diagnostics.Debug.write(message, category);
    }

    /// <summary>
    /// </summary>
    public static void write(Object value, String category)
    {
        com.aspose.ms.System.Diagnostics.Debug.write(value, category);
    }

    /// <summary>
    /// </summary>
    public static void writeIf(boolean condition, String message)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeIf(condition, message);
    }

    /// <summary>
    /// </summary>
    public static void writeIf(boolean condition, Object value)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeIf(condition, value);
    }

    /// <summary>
    /// </summary>
    public static void writeIf(boolean condition, String message, String category)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeIf(condition, message, category);
    }

    /// <summary>
    /// </summary>
    public static void writeIf(boolean condition, Object value, String category)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeIf(condition, value, category);
    }

    /// <summary>
    /// </summary>
    public static void writeLine(String message)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeLine(message);
    }

    /// <summary>
    /// </summary>
    public static void writeLine(Object value)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeLine(value);
    }

    /// <summary>
    /// </summary>
    public static void writeLine(String message, String category)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeLine(message, category);
    }

    /// <summary>
    /// </summary>
    public static void writeLine(Object value, String category)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeLine(value, category);
    }

    /// <summary>
    /// </summary>
    public static void writeLineIf(boolean condition, String message)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeLineIf(condition, message);
    }

    /// <summary>
    /// </summary>
    public static void writeLineIf(boolean condition, Object value)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeLineIf(condition, value);
    }

    /// <summary>
    /// </summary>
    public static void writeLineIf(boolean condition, String message, String category)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeLineIf(condition, message, category);
    }

    /// <summary>
    /// </summary>
    public static void writeLineIf(boolean condition, Object value, String category)
    {
        com.aspose.ms.System.Diagnostics.Debug.writeLineIf(condition, value, category);
    }

    /// <summary>
    /// </summary>
    public static void close()
    {
        com.aspose.ms.System.Diagnostics.Debug.close();
    }

    /// <summary>
    /// </summary>
    public static void flush()
    {
        com.aspose.ms.System.Diagnostics.Debug.flush();
    }

    /// <summary>
    /// </summary>
    public static void indent()
    {
        com.aspose.ms.System.Diagnostics.Debug.indent();
    }

    /// <summary>
    /// </summary>
    public static void unindent()
    {
        com.aspose.ms.System.Diagnostics.Debug.unindent();
    }

    ///<summary>
    ///</summary>
    public static TraceListenerCollection getListeners() { return com.aspose.ms.System.Diagnostics.Debug.getListeners(); }

    /// <summary>
    /// Breaks execution and returns control to debugger.
    /// </summary>
    public static void breakCall()
    {
        //JAVA-deleted
        //Debugger.Break();
    }

    /// <summary>
    /// Checks whether current method was called from a method of class <paramref name="type"/>.
    /// </summary>
    public static void assertCallingClass(Class type)
    {
        //JAVA-added
        if (type == null)
            return;

        String name = type.getName();
        String callingClass = getCallingClass();
        assertTrue(msString.equals(callingClass, name), "Caller '" + callingClass + "' is not the same as expected '" + name + "'");
    }

    /// <summary>
    /// Checks whether current method was called from a method of class named <paramref name="typeName"/>.
    /// </summary>
    public static void assertCallingClass(String typeName)
    {
        String fullyQalifiedClass = getCallingClass();
        int lastDot = fullyQalifiedClass.lastIndexOf('.');
        String className = fullyQalifiedClass.substring(lastDot + 1);
        assertTrue(msString.equals(className, typeName));
    }

    //JAVA-added
    private static String getCallingClass()
    {
        // 4th element is caller we are looking for
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length < 5)
            return null;

        StackTraceElement frame = stackTraceElements[4];
        return frame.getClassName();
    }

    //JAVA-added
    private static String getCallingStack()
    {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder stacktrace = new StringBuilder();
        int startFrame = 2;
        startFrame = startFrame >= stackTraceElements.length ? 0 : startFrame;

        for (int i = startFrame; i < stackTraceElements.length; i++)
            stacktrace.append(stackTraceElements[i].toString()).append("\n");

        return stacktrace.toString();
    }


    /// <summary>
    /// Allows filtering log messages based on the match of a full name of the caller method.
    /// </summary>

    //JAVA-changed: ThreadLocal variable instead of simple static
    private static ThreadLocal<Boolean> gIsThrowOnAssert = new ThreadLocal<Boolean>()
    {
        @Override
        protected Boolean initialValue()
        {
            return true;
        }
    };

}

