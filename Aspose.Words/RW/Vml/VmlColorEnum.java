// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/05/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.StringToObjBidirectionalMap;


/// <summary>
/// Converts color enumerated types between enum and VML string.
/// </summary>
/*static*/ class VmlColorEnum
{
	/* Simulation of static class by using private constructor */
	private VmlColorEnum()
	{}

    /// <summary>
    /// Converts a named VML color into a <see cref="DrColor"/>
    /// If cannot recognize or the color is "none" returns <see cref="DrColor.Empty"/>
    /// </summary>
    static DrColor vmlToPalColor(String value)
    {
        return G_COLOR_MAP.getValue(value, DrColor.EMPTY);
    }

    static String palColorToVml(DrColor value)
    {
        return G_COLOR_MAP.getKey(value, "");
    }

    //JAVA: declarations moved here to exclude java's illegal forward reference.
    private static /*final*/ StringToObjBidirectionalMap<DrColor> G_COLOR_MAP = new StringToObjBidirectionalMap<DrColor>();        

    static/* VmlColorEnum()*/
    {
        // Section 6.1.3.1 ST_ColorType (Color Type) of ECMA-376 documentation.
        G_COLOR_MAP.addEntry("none", DrColor.TRANSPARENT);
        G_COLOR_MAP.addEntry("maroon", DrColor.MAROON);
        G_COLOR_MAP.addEntry("red", DrColor.RED);
        G_COLOR_MAP.addEntry("yellow", DrColor.YELLOW);
        G_COLOR_MAP.addEntry("olive", DrColor.OLIVE);
        G_COLOR_MAP.addEntry("purple", DrColor.PURPLE);
        G_COLOR_MAP.addEntry("fuchsia", DrColor.FUCHSIA);
        G_COLOR_MAP.addEntry("white", DrColor.WHITE);
        G_COLOR_MAP.addEntry("lime", DrColor.LIME);
        G_COLOR_MAP.addEntry("green", DrColor.GREEN);
        G_COLOR_MAP.addEntry("navy", DrColor.NAVY);
        G_COLOR_MAP.addEntry("blue", DrColor.BLUE);
        G_COLOR_MAP.addEntry("aqua", DrColor.AQUA);
        G_COLOR_MAP.addEntry("teal", DrColor.TEAL);
        G_COLOR_MAP.addEntry("black", DrColor.BLACK);
        G_COLOR_MAP.addEntry("silver", DrColor.SILVER);
        G_COLOR_MAP.addEntry("gray", DrColor.GRAY);
    }
}

