package com.aspose.ms.System;

/// <summary>
/// Contains extension methods for porting System.Int32 and System.UInt32.
/// </summary>
public class msInt
{
    /// <summary>
    /// Used instead of int.CompareTo(int)
    /// </summary>
    public static int compareTo(int a, int b)
    {
        if (a < b)
            return -1;
        else if (a > b)
            return 1;
        else
            return 0;
    }
}
