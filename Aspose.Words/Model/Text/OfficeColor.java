// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.HSLColor;


/// <summary>
/// Implements resolution of FontAttr.ThemeColor, FontAttr.ThemeShade and FontAttr.ThemeTint 
/// into RGB color (FontAttr.Color).
/// </summary>
/// <remarks>
/// This is not full fix but just temporary solution to satisfy ES WORDSNET-9737
/// 
/// This class is first stage template for rework similar to WORDSNET-3312 i.e 
/// I want to combine Color, ThemeColor, ThemeTint and ThemeShade attributes into one complex attribute
/// represented by this class.
/// 
/// This will fix all problems with ThemeColor incorrect inheritance (see RunPr.ThemeColorInheritanceHack) and 
/// problems with themed color exposed to customer.
/// 
/// Such rework also should be applied for Shading colors (both foreground and background) and for UnderlineColor.
/// </remarks>
class OfficeColor
{
    /// <summary>
    /// Determine the final theme color.
    /// Applies theme shading with shade and tint effects if specified.
    /// </summary>
    static DrColor resolve(Object themeFont, Object tint, Object shade, Theme theme)
    {
        // No theme nothing to resolve.
        if (theme == null)
            return null;

        // Both tint and shade passed is invalid case.
        if ((tint != null) && (shade != null))
            return null;

        if (!StringUtil.hasChars((String)themeFont))
            return null;

        DrColor drThemeColor = theme.getColorScheme().getColor((String)themeFont).createDrColor(theme, null);

        if (StringUtil.hasChars((String)tint))
        {
            // Apply tint.
            drThemeColor = tint(drThemeColor, FormatterPal.parseHex((String)tint));
        }
        else if (StringUtil.hasChars((String)shade))
        {
            // Apply shade.
            drThemeColor = shade(drThemeColor, FormatterPal.parseHex((String)shade));
        }

        return drThemeColor;
    }

    /// <summary>
    /// Applies Tint color modifier.
    /// </summary>
    /// <remarks>
    /// According to ISO/IEC 29500-1:2012(E) 17.3.6 Tint Properties (CT_Tint) 
    /// The tint is applied as follows:
    /// - Convert the color to the HSL color format (values from 0 to 1)
    /// - Modify the luminance factor of HSL color as L=L*TintPercent+(1-TintPercent).
    /// - Convert the resultant HSL color to RGB 
    /// Combined with new HSL conversion implemented in HSLColor class it gives 
    /// very close result with minor difference caused by fraction loss.
    /// For a while I don't want this algorithm to be common.
    /// 
    /// It's maybe wrong place for this algorithm here, but I'm going to put it into DmlTint eventually.
    /// 
    /// Exposed for unit testing.
    /// </remarks>
    static DrColor tint(DrColor color, int tint)
    {
        double normalTint = (double)tint/255.0;

        HSLColor hslColor = new HSLColor(color);
        hslColor.setLum(hslColor.getLum()*normalTint + (1.0 - normalTint));

        return hslColor.toDrColor();
    }

    /// <summary>
    /// Applies Shade color modifier.
    /// </summary>
    /// <remarks>
    /// According to ISO/IEC 29500-1:2012(E) 17.3.5 Shading Properties (CT_Shd) 
    /// The shade is applied as follows:
    /// - Convert the color to the HSL color format (values from 0 to 1)
    /// - Modify the luminance factor of HSL color as L=L*ShadeParcent.
    /// - Convert the resultant HSL color to RGB
    /// Combined with new HSL conversion implemented in HSLColor class it gives 
    /// very close result with minor difference caused by fraction loss.
    /// For a while I don't want this algorithm to be common.
    /// 
    /// It's maybe wrong place for this algorithm here, but I'm going to put it into DmlShade eventually.
    /// 
    /// Exposed for unit testing.
    /// </remarks>
    static DrColor shade(DrColor color, int shade)
    {
        double normalShade = (double)shade/255.0;

        HSLColor hslColor = new HSLColor(color);
        hslColor.setLum(hslColor.getLum() * normalShade);

        return hslColor.toDrColor();
    }
}

