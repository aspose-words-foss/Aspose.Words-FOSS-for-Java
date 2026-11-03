// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2016 by Denis Shvydkiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements IDrFontMetrics and provides metrics from TTFont class.
/// </summary>
public abstract class TTFontMetrics implements IDrFontMetrics
{
    protected TTFontMetrics(TTFont font, float sizePoints)
    {
        mFont = font;

        float ascent = font.getAscent();
        float descent = font.getDescent();
        float lineSpacing = font.getLineSpacing();

        setAscentRawPoints(getFont().designUnitsToPoints(ascent, sizePoints));
        setDescentRawPoints(getFont().designUnitsToPoints(descent, sizePoints));

        setAscentPoints(getFont().designUnitsToPoints(ascent, sizePoints));
        setDescentPoints(getFont().designUnitsToPoints(descent, sizePoints));
        setLineSpacingPoints(getFont().designUnitsToPoints(lineSpacing, sizePoints));
    }

    public abstract float getCharWidthPoints(int c, float sizePoints);

    public abstract float getRawCharWidthPoints(int c, float sizePoints);

    public float getTextWidthPoints(String text, float sizePoints)
    {
        float result = 0f;
        // Performance optimization. Use the field instead of creating a new StringUtf32Enumerator object
        // to reduce memory allocations and fragmentation.
        mStringEnumerator.setText(text);
        for (int c : mStringEnumerator)
            result += getCharWidthPoints(c, sizePoints);

        return result;
    }

    public float getAscentPoints() { return mAscentPoints; }; public void setAscentPoints(float value) { mAscentPoints = value; };

    private float mAscentPoints;

    public float getDescentPoints() { return mDescentPoints; }; public void setDescentPoints(float value) { mDescentPoints = value; };

    private float mDescentPoints;

    public float getLineSpacingPoints() { return mLineSpacingPoints; }; public void setLineSpacingPoints(float value) { mLineSpacingPoints = value; };

    private float mLineSpacingPoints;

    public float getAscentRawPoints() { return mAscentRawPoints; }; public void setAscentRawPoints(float value) { mAscentRawPoints = value; };

    private float mAscentRawPoints;

    public float getDescentRawPoints() { return mDescentRawPoints; }; public void setDescentRawPoints(float value) { mDescentRawPoints = value; };

    private float mDescentRawPoints;

    protected TTFont getFont() { return mFont; };

    private TTFont mFont;

    private /*final*/ StringUtf32Enumerator mStringEnumerator = new StringUtf32Enumerator("");
}

