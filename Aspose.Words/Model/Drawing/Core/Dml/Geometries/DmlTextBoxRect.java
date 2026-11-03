// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/28/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.RectangleF;


class DmlTextBoxRect implements Cloneable
{
    DmlTextBoxRect(String l, String t, String r, String b)
    {
        mLeft = l;
        mTop = t;
        mRight = r;
        mBottom = b;
    }

    /// <summary>
    /// Clones this instance of text box rectangle.
    /// </summary>
    DmlTextBoxRect deepClone()
    {
        return (DmlTextBoxRect)memberwiseClone();
    }

    /// <summary>
    /// Gets the value. 
    /// </summary>
    /// <param name="value">The input value that may be a number, a universal measure or a guide name.</param>
    /// <param name="guides">Dictionary of guides.</param>
    /// <returns>Numeric value in EMUs.</returns>
    private static float getValue(String value, DmlGuides guides)
    {
        double result = NrxXmlReader.tryConvertUniversalMeasureToEmus(value, null);
        if (!Double.isNaN(result))
            return (float)result;

        return (float)guides.getValue(value);
    }

    String getLeft() { return mLeft; }

    String getTop() { return mTop; }

    String getRight() { return mRight; }

    String getBottom() { return mBottom; }

    /// <summary>
    /// Returns textbox rectangle defined by the specified guides.
    /// Guides must be calculated in this stage, otherwise empty rectangle will be returned.
    /// Since guides are not calculated on reading stage, we cannot do this in constructor and have to keep rect as strings.
    /// </summary>
    RectangleF getRectangle(DmlGuides guides, boolean isUpArrow)
    {
        try
        {
            float l = getValue(mLeft, guides);
            float t = getValue(mTop, guides);
            float r = getValue(mRight, guides);
            float b = getValue(mBottom, guides);
            RectangleF result = RectangleF.fromLTRB(l, t, r, b);
            if (isUpArrow)
            {
                // WORDSNET-15365 "upArrow" shape is an exception, height specifies Y position instead of height.
                // And height should be calculated as difference between real shape height and Y position.
                float height = getValue("h", guides);
                result = new RectangleF(result.getX(), result.getHeight(), result.getWidth(), height - result.getHeight());
            }

            return result;
        }
        catch(Exception e)
        {
            return RectangleF.Empty;
        }

    }

    private /*final*/ String mLeft;
    private /*final*/ String mTop;
    private /*final*/ String mRight;
    private /*final*/ String mBottom;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

