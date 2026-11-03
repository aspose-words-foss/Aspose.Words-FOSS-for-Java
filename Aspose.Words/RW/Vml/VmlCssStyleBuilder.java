// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.SortedList;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.CssEscape;
import java.util.Map;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Builds CSS properties for VML as single string.
/// </summary>
class VmlCssStyleBuilder
{
    VmlCssStyleBuilder()
    {
        mSortedList = new SortedList<String, String>(new CssPropertyNameComparer());
    }

    /// <summary>
    /// Sets property with number value.
    /// </summary>
    void add(String propertyName, int value)
    {
        add(propertyName, FormatterPal.intToStr(value));
    }

    /// <summary>
    /// Sets property with raw value.
    /// </summary>
    void add(String propertyName, String value)
    {
        if (!StringUtil.hasChars(value))
            return;
        mSortedList.add(propertyName, value);
    }

    /// <summary>
    /// Sets 'font-family' CSS property.
    /// </summary>
    void addFontFamily(String fontName)
    {
        String value = fontName;
        if (!CssEscape.isValidIdentifier(fontName))
            value = "'" + fontName + "'";

        add("font-family", value);
    }

    String toCss()
    {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : mSortedList)
        {
            if (sb.length() > 0)
            {
                sb.append(';');
            }
            msStringBuilder.append(sb, entry.getKey());
            sb.append(':');
            msStringBuilder.append(sb, entry.getValue());
        }
        return sb.toString();
    }

    private /*final*/ SortedList<String, String> mSortedList;
}

