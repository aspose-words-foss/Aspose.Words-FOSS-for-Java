// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;


class DmlFormulaHelper
{
    /// <summary>
    /// If valueString is numeric then return its value otherwise query value in value provider.
    /// </summary>
    public static double getValue(String valueString, IDmlGuideValueProvider valueProvider)
    {
        double result = FormatterPal.tryParseDoubleInvariant(valueString);
        if (!Double.isNaN(result))
            return result;

        return valueProvider.getValue(valueString);
    }
}
