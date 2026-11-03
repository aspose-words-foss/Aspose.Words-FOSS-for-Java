// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2010 by Roman Korchagin

package com.aspose;

import com.aspose.ms.java.text.CollatorProvider;

/// <summary>
/// Port this class manually to Java.
/// </summary>
/*static*/ class StringUtilPal
{
    /* Simulation of static class by using private constructor */
    private StringUtilPal()
    {}

    /// <summary>
    /// Manual implementation for <see cref="StringUtil.IsPrivateUseCategory"/>
    /// </summary>
    public static boolean isPrivateUseCategory(char c)
    {
        return (Character.getType(c) == Character.PRIVATE_USE);
    }

    /// <summary>
    /// Manual implementation for <see cref="StringUtil.CompareStringSort"/>
    /// </summary>
    public static int compareStringSort(String a, String b)
    {
        return CollatorProvider.getStringSortCollator().compare(a, b);
    }

    /// <summary>
    /// Manual implementation for <see cref="String.PadRight"/>
    /// </summary>
    public static String padRight(String s, int totalWidth, char paddingChar)
    {
        int sufixLenght = totalWidth - s.length();
        if (sufixLenght == 0)
            return s;
        String sufix = String.format("%-"+ sufixLenght + "s","").replace(' ', paddingChar);
        return s + sufix;
    }
}
