// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.ms.System.msObject;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Represents 2.8.3.6 CT_StyleColor [MS-ODRAWXML]
/// The complex type specifies a color which is retrieved from CT_ColorStyle (section 2.8.3.2).
/// </summary>
class DmlChartStyleColor extends DmlColor
{
    protected /*override*/ DrColor createUnmodifiedColor(IThemeProvider themeProvider)
    {
        // Now only reading/writing is supported.
        return null;
    }

    /// <summary>
    /// Clones this <see cref="DmlChartStyleColor"/> object.
    /// </summary>
    public /*override*/ DmlColor deepClone()
    {
        DmlChartStyleColor result = new DmlChartStyleColor();
        result.setValue(getValue());
        copyColorModifiersTo(result);
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlChartStyleColor value = (DmlChartStyleColor)obj;

        return msObject.equals(value.getValue(), getValue());
    }

    /// <summary>
    /// Serves as a hash function for a particular type.
    /// </summary>
    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getValue().hashCode();
        return hash;
    }

    /// <summary>
    /// Gets type of this color.
    /// </summary>
    public /*override*/ /*DmlColorType*/int getColorType() { return DmlColorType.CHART_STYLE_COLOR; }

    /// <summary>
    /// Specifies what index to use when retrieving a color from a color style.
    /// Represents val: an ST_StyleColorVal attribute that specifies the value which is used to determine the index
    /// of the color in a CT_ColorStyle.
    /// </summary>
    String getValue() { return mValue; }
    void setValue(String value) { mValue = value; }

    /// <summary>
    /// Gets the color index to use when retrieving a color from a color style.
    /// </summary>
    int getColorIndex()
    {
        int index = FormatterPal.tryParseInt(mValue);
        return (index >= 0) ? index : 0;
    }

    /// <summary>
    /// Gets a flag indicating whether a chart element index should be used as a color index.
    /// </summary>
    boolean isAuto() { return msString.equals(getValue(), "auto", StringComparison.ORDINAL); }

    private String mValue;
}

