// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/02/2020 by Ilya Egorov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.msString;
import com.aspose.MathUtil;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Provides methods for obtaining <see cref="DrColor"/> from a format string.
/// </summary>

class DmlChartFormatCodeColors
{
    /// <summary>
    /// Gets <see cref="DrColor"/> from the specified string.
    /// </summary>
    /// <remarks>
    /// The text color can be represented by the name or color index (3.8.31 OOXML) in square brackets in the section.
    /// </remarks>
    /// <param name="formatCode">The specified string</param>
    /// <returns><see cref="DrColor"/></returns>
    static DrColor getColor(String formatCode)
    {
        Match match = G_SQUARE_BRACKETS_CODE_REGEX.match(formatCode);
        String stringWithColor = (match.getLength() == 0) ? "" : msString.trimEnd(msString.trimStart(match.getValue(), '['), ']');
        stringWithColor = DmlChartRenderingUtil.SPACE_REGEX.replace(stringWithColor, "");
        
        DrColor color = G_FORMAT_CODE_COLORS.get(stringWithColor);
        return (color != null) ? color : DrColor.EMPTY;
    }

    /// <summary>
    /// Gets the value color according to the specified value and the specified format code.
    /// </summary>
    /// <param name="value">The specified value</param>
    /// <param name="formatCode">The specified format code</param>
    /// <returns><see cref="DrColor"/></returns>
    static DrColor getValueColor(double value, String formatCode)
    {
        DrColor color;
        String[] formats = msString.split(formatCode, DmlChartRenderingUtil.SECTION_SEPARATOR);

        if (MathUtil.isZero(value))
            color = (formats.length > 2) ? getColor(formats[2]) : getColor(formats[0]);
        else if (value > 0 )
            color = getColor(formats[0]);
        else 
            color = (formats.length > 1) ? getColor(formats[1]) : getColor(formatCode);
      
        return color;
    }

    /// <summary>
    /// Regex matches if string contains text like '[....]' representing color settings.
    /// </summary>
    private static /*final*/ Regex G_SQUARE_BRACKETS_CODE_REGEX = new Regex("\\[\\w*\\s*\\d*\\]");

    private static /*final*/ StringToObjDictionary<DrColor> G_FORMAT_CODE_COLORS;

    // Uint values of colors.
    private static final int ARGB_COLOR_17= /*unchecked*/((int)(int) 0xff9999ffL);
    private static final int ARGB_COLOR_18 = /*unchecked*/((int)(int) 0xff993366L);
    private static final int ARGB_COLOR_19 = /*unchecked*/((int)(int) 0xffffffccL);
    private static final int ARGB_COLOR_20 = /*unchecked*/((int)(int) 0xffccffffL);
    private static final int ARGB_COLOR_21 = /*unchecked*/((int)(int) 0xff660066L);
    private static final int ARGB_COLOR_22 = /*unchecked*/((int)(int) 0xffff8080L);
    private static final int ARGB_COLOR_23 = /*unchecked*/((int)(int) 0xff0066CCL);
    private static final int ARGB_COLOR_24 = /*unchecked*/((int)(int) 0xffccccffL);
    private static final int ARGB_COLOR_25 = /*unchecked*/((int)(int) 0xff000080L);
    private static final int ARGB_COLOR_33 = /*unchecked*/((int)(int) 0xff00ccffL);
    private static final int ARGB_COLOR_34 = /*unchecked*/((int)(int) 0xffccffffL);
    private static final int ARGB_COLOR_35 = /*unchecked*/((int)(int) 0xffccffccL);
    private static final int ARGB_COLOR_36 = /*unchecked*/((int)(int) 0xffffff99L);
    private static final int ARGB_COLOR_37 = /*unchecked*/((int)(int) 0xff99ccffL);
    private static final int ARGB_COLOR_38 = /*unchecked*/((int)(int) 0xffff99ccL);
    private static final int ARGB_COLOR_39 = /*unchecked*/((int)(int) 0xffcc99ffL);
    private static final int ARGB_COLOR_40 = /*unchecked*/((int)(int) 0xffffcc99L);
    private static final int ARGB_COLOR_41 = /*unchecked*/((int)(int) 0xff3366ffL);
    private static final int ARGB_COLOR_42 = /*unchecked*/((int)(int) 0xff33ccccL);
    private static final int ARGB_COLOR_43 = /*unchecked*/((int)(int) 0xff99cc00L);
    private static final int ARGB_COLOR_44 = /*unchecked*/((int)(int) 0xffffcc00L);
    private static final int ARGB_COLOR_45 = /*unchecked*/((int)(int) 0xffff9900L);
    private static final int ARGB_COLOR_46 = /*unchecked*/((int)(int) 0xffff6600L);
    private static final int ARGB_COLOR_47 = /*unchecked*/((int)(int) 0xff666699L);
    private static final int ARGB_COLOR_48 = /*unchecked*/((int)(int) 0xff969696L);
    private static final int ARGB_COLOR_49 = /*unchecked*/((int)(int) 0xff003366L);
    private static final int ARGB_COLOR_50 = /*unchecked*/((int)(int) 0xff339966L);
    private static final int ARGB_COLOR_51 = /*unchecked*/((int)(int) 0xff003300L);
    private static final int ARGB_COLOR_52 = /*unchecked*/((int)(int) 0xff333300L);
    private static final int ARGB_COLOR_53 = /*unchecked*/((int)(int) 0xff993300L);
    private static final int ARGB_COLOR_54 = /*unchecked*/((int)(int) 0xff993366L);
    private static final int ARGB_COLOR_55 = /*unchecked*/((int)(int) 0xff333399L);
    private static final int ARGB_COLOR_56 = /*unchecked*/((int)(int) 0xff333333L);

    static/* DmlChartFormatCodeColors()*/
    {
        G_FORMAT_CODE_COLORS = new StringToObjDictionary<DrColor>(false);

        // Colors by Name.
        G_FORMAT_CODE_COLORS.add("Black", DrColor.BLACK);
        G_FORMAT_CODE_COLORS.add("Green", DrColor.GREEN);
        G_FORMAT_CODE_COLORS.add("White", DrColor.WHITE);
        G_FORMAT_CODE_COLORS.add("Blue", DrColor.BLUE);
        G_FORMAT_CODE_COLORS.add("Magenta", DrColor.MAGENTA);
        G_FORMAT_CODE_COLORS.add("Yellow", DrColor.YELLOW);
        G_FORMAT_CODE_COLORS.add("Cyan", DrColor.CYAN);
        G_FORMAT_CODE_COLORS.add("Red", DrColor.RED);
        
        // Colors by index.
        G_FORMAT_CODE_COLORS.add("Color1", DrColor.BLACK);
        G_FORMAT_CODE_COLORS.add("Color2", DrColor.WHITE);
        G_FORMAT_CODE_COLORS.add("Color3", DrColor.RED);
        G_FORMAT_CODE_COLORS.add("Color4", DrColor.LIME);
        G_FORMAT_CODE_COLORS.add("Color5", DrColor.BLUE);
        G_FORMAT_CODE_COLORS.add("Color6", DrColor.YELLOW);
        G_FORMAT_CODE_COLORS.add("Color7", DrColor.MAGENTA);
        G_FORMAT_CODE_COLORS.add("Color8", DrColor.CYAN);
        G_FORMAT_CODE_COLORS.add("Color9", DrColor.MAROON);
        G_FORMAT_CODE_COLORS.add("Color10", DrColor.GREEN);
        G_FORMAT_CODE_COLORS.add("Color11", DrColor.NAVY);
        G_FORMAT_CODE_COLORS.add("Color12", DrColor.OLIVE);
        G_FORMAT_CODE_COLORS.add("Color13", DrColor.PURPLE);
        G_FORMAT_CODE_COLORS.add("Color14", DrColor.TEAL);
        G_FORMAT_CODE_COLORS.add("Color15", DrColor.SILVER);
        G_FORMAT_CODE_COLORS.add("Color16", DrColor.GRAY);
        G_FORMAT_CODE_COLORS.add("Color17", new DrColor(ARGB_COLOR_17));
        G_FORMAT_CODE_COLORS.add("Color18", new DrColor(ARGB_COLOR_18));
        G_FORMAT_CODE_COLORS.add("Color19", new DrColor(ARGB_COLOR_19));
        G_FORMAT_CODE_COLORS.add("Color20", new DrColor(ARGB_COLOR_20));
        G_FORMAT_CODE_COLORS.add("Color21", new DrColor(ARGB_COLOR_21));
        G_FORMAT_CODE_COLORS.add("Color22", new DrColor(ARGB_COLOR_22));
        G_FORMAT_CODE_COLORS.add("Color23", new DrColor(ARGB_COLOR_23));
        G_FORMAT_CODE_COLORS.add("Color24", new DrColor(ARGB_COLOR_24));
        G_FORMAT_CODE_COLORS.add("Color25", new DrColor(ARGB_COLOR_25));
        G_FORMAT_CODE_COLORS.add("Color26", DrColor.MAGENTA);
        G_FORMAT_CODE_COLORS.add("Color27", DrColor.YELLOW);
        G_FORMAT_CODE_COLORS.add("Color28", DrColor.CYAN);
        G_FORMAT_CODE_COLORS.add("Color29", DrColor.PURPLE);
        G_FORMAT_CODE_COLORS.add("Color30", DrColor.MAROON);
        G_FORMAT_CODE_COLORS.add("Color31", DrColor.TEAL);
        G_FORMAT_CODE_COLORS.add("Color32", DrColor.BLUE);
        G_FORMAT_CODE_COLORS.add("Color33", new DrColor(ARGB_COLOR_33));
        G_FORMAT_CODE_COLORS.add("Color34", new DrColor(ARGB_COLOR_34));
        G_FORMAT_CODE_COLORS.add("Color35", new DrColor(ARGB_COLOR_35));
        G_FORMAT_CODE_COLORS.add("Color36", new DrColor(ARGB_COLOR_36));
        G_FORMAT_CODE_COLORS.add("Color37", new DrColor(ARGB_COLOR_37));
        G_FORMAT_CODE_COLORS.add("Color38", new DrColor(ARGB_COLOR_38));
        G_FORMAT_CODE_COLORS.add("Color39", new DrColor(ARGB_COLOR_39));
        G_FORMAT_CODE_COLORS.add("Color40", new DrColor(ARGB_COLOR_40));
        G_FORMAT_CODE_COLORS.add("Color41", new DrColor(ARGB_COLOR_41));
        G_FORMAT_CODE_COLORS.add("Color42", new DrColor(ARGB_COLOR_42));
        G_FORMAT_CODE_COLORS.add("Color43", new DrColor(ARGB_COLOR_43));
        G_FORMAT_CODE_COLORS.add("Color44", new DrColor(ARGB_COLOR_44));
        G_FORMAT_CODE_COLORS.add("Color45", new DrColor(ARGB_COLOR_45));
        G_FORMAT_CODE_COLORS.add("Color46", new DrColor(ARGB_COLOR_46));
        G_FORMAT_CODE_COLORS.add("Color47", new DrColor(ARGB_COLOR_47));
        G_FORMAT_CODE_COLORS.add("Color48", new DrColor(ARGB_COLOR_48));
        G_FORMAT_CODE_COLORS.add("Color49", new DrColor(ARGB_COLOR_49));
        G_FORMAT_CODE_COLORS.add("Color50", new DrColor(ARGB_COLOR_50));
        G_FORMAT_CODE_COLORS.add("Color51", new DrColor(ARGB_COLOR_51));
        G_FORMAT_CODE_COLORS.add("Color52", new DrColor(ARGB_COLOR_52));
        G_FORMAT_CODE_COLORS.add("Color53", new DrColor(ARGB_COLOR_53));
        G_FORMAT_CODE_COLORS.add("Color54", new DrColor(ARGB_COLOR_54));
        G_FORMAT_CODE_COLORS.add("Color55", new DrColor(ARGB_COLOR_55));
        G_FORMAT_CODE_COLORS.add("Color56", new DrColor(ARGB_COLOR_56));
    }
}


