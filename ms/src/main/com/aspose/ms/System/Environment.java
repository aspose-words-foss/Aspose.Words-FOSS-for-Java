package com.aspose.ms.System;

public class Environment
{
    public static String getNewLine()
    {
        return NEW_LINE;
    }

    public static int getTickCount()
    {
        return (int) System.nanoTime();
    }

    public static final String NEW_LINE = "\r\n";
}
