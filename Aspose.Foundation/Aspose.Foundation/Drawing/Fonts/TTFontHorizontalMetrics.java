// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2016 by Denis Shvydkiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements methods responsible for character advance width calculation based on horizontal metrics.
/// </summary>
public class TTFontHorizontalMetrics extends TTFontMetrics
{
    TTFontHorizontalMetrics(TTFont font, float sizePoints)
    {
    	super(font, sizePoints);
	
    }

    public /*override*/ float getCharWidthPoints(int c, float sizePoints)
    {
        return getRawCharWidthPoints(c, sizePoints);
    }

    public /*override*/ float getRawCharWidthPoints(int c, float sizePoints)
    {
        return getFont().designUnitsToPoints(getFont().getCharAdvanceWidthDesignUnits(c), sizePoints);
    }
}

