// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 18/03/2010 by Roman Korchagin

package com.aspose;

import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.collections.StringSwitchMap;

import java.awt.Color;


/// <summary>
/// Port this class manually to Java.
/// </summary>
class ColorPal
{
    /// <summary>
    /// No ctor.
    /// </summary>
    private ColorPal()
    {
    }

    static Color toNativeColor(DrColor drColor)
    {
        if (drColor.isEmpty())
            return msColor.Empty;

        // check for system color "window"            
        if (DrColor.equals(drColor, DrColor.WINDOW))
            return com.aspose.ms.System.Drawing.SystemColors.getWindow();

        return new Color((drColor.toArgb()), true);
    }

    static DrColor fromNativeColor(Color nativeColor)
    {
        return new DrColor(nativeColor.getRGB());
    }

    static DrColor getSystemColor(String colorName)
    {
        Color color = msColor.Empty;

        switch (gStringSwitchMap.of(msString.toLowerInvariant(colorName)))
        {
            case /*"window"*/0:
                color = com.aspose.ms.System.Drawing.SystemColors.getWindow();
                break;
            case /*"windowtext"*/1:
                color = com.aspose.ms.System.Drawing.SystemColors.getWindowText();
                break;
            default:
                // Ignore all other.
                break;
        }

        return new DrColor(color.getRGB());
    }

    //JAVA-added for string switch emulation
    private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
            (
                    "window",
                    "windowtext"
            );
}

