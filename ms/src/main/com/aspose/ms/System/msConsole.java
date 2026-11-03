//Konstantin Sidorenko 30/07/13

package com.aspose.ms.System;

public class msConsole
{
    public static void write(String value)
    {
        System.out.print(value);
    }

    public static void write(Object value)
    {
        System.out.println(value);
    }

   /**
     * This single method replaces 5 Console.Write(string, object...) overloads.
     */
    public static void write(String format, /*params*/ Object... args)
    {
        System.out.print(msString.format(format, args));
    }

    public static void writeLine(String value)
    {
        System.out.println(value);
    }

    public static void writeLine()
    {
        System.out.println();
    }

    public static void writeLine(Object value)
    {
        System.out.println(value);
    }

    /**
     * This single method replaces 5 Console.WriteLine(string, object...) overloads.
     */
    public static void writeLine(String format, /*params*/ Object... args)
    {
        System.out.println(msString.format(format, args));
    }
}
