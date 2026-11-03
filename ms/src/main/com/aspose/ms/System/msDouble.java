package com.aspose.ms.System;

/// <summary>
/// Contains extension methods for porting System.Double.
/// </summary>
public class msDouble
{
    /// <summary>
    /// Used instead of double.CompareTo(double)
    /// </summary>
    public static int compareTo(double a, double b)
    {
        if (a < b)
            return -1;
        if (a > b)
            return 1;
        if (a == b)
            return 0;

        // At least one of the values is NaN.
        if (Double.isNaN(a))
            return (Double.isNaN(b) ? 0 : -1);
        else
            return 1;
    }
}
