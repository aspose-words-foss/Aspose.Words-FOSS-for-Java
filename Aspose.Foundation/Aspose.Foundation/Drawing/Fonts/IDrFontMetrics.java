// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2016 by Denis Shvydkiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This interface allows to specify alternative font metrics in DrFont class.
/// </summary>
public interface IDrFontMetrics
{
    public float getCharWidthPoints(int c, float sizePoints);
    public float getRawCharWidthPoints(int c, float sizePoints);
    public float getTextWidthPoints(String text, float sizePoints);
    public float getAscentPoints(); public void setAscentPoints(float value);
    public float getDescentPoints(); public void setDescentPoints(float value);
    public float getLineSpacingPoints(); public void setLineSpacingPoints(float value);
    public float getAscentRawPoints(); public void setAscentRawPoints(float value);
    public float getDescentRawPoints(); public void setDescentRawPoints(float value);
}

