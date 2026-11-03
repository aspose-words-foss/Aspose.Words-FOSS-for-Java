// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;


/// <summary>
/// 20.1.8.47 pattFill (Pattern Fill)
/// This element specifies a pattern fill. A repeated pattern is used to fill the object.
/// </summary>
class DmlPatternFill extends DmlFill
{
    /// <summary>
    /// Set style color in color placeholders used  in the fill.
    /// </summary>
    public /*override*/ void applyStyleColor(DmlColor styleColor)
    {
        getForegroundColor().applyStyleColor(styleColor);
        getBackgroundColor().applyStyleColor(styleColor);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlPatternFill value = (DmlPatternFill)obj;

        return (value.getFillPresetPattern() == getFillPresetPattern()) &&
               msObject.equals(value.getBackgroundColor(), getBackgroundColor()) &&
               msObject.equals(value.getForegroundColor(), getForegroundColor());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= /*enum.hashCode()=(int)value*/getFillPresetPattern();
        hash ^= getBackgroundColor().hashCode();
        hash ^= getForegroundColor().hashCode();
        return hash;
    }

    /*override*/ DmlFill deepClone()
    {
        DmlPatternFill result = new DmlPatternFill();
        result.setFillPresetPattern(getFillPresetPattern());
        result.setBackgroundColor(getBackgroundColor().deepClone());
        result.setForegroundColor(getForegroundColor().deepClone());
        return result;
    }

    /// <summary>
    /// Gets or sets the type of fill.
    /// </summary>
    public /*override*/ /*FillTypeCore*/int getFillType() { return FillTypeCore.PATTERN; }

    /*override*/ /*DmlFillType*/int getDmlFillType() { return DmlFillType.PATTERN_FILL; }

    /*PatternType*/int getFillPresetPattern() { return mFillPresetPattern; }
    void setFillPresetPattern(/*PatternType*/int value) { mFillPresetPattern = value; }

    DmlColor getBackgroundColor()
    {
        if (mBackgroundColor == null)
            mBackgroundColor = new DmlHexRgbColor();
        return mBackgroundColor;
    }
    void setBackgroundColor(DmlColor value) { mBackgroundColor = value; }

    DmlColor getForegroundColor()
    {
        if (mForegroundColor == null)
            mForegroundColor = new DmlHexRgbColor();
        return mForegroundColor;
    }
    void setForegroundColor(DmlColor value) { mForegroundColor = value; }

    /// <summary>
    /// Gets or sets foreground color of the fill.
    /// </summary>
    /*override*/ DmlColor getDmlColorInternal() { return getForegroundColor(); }
    /*override*/ void setDmlColorInternal(DmlColor value) { setForegroundColor(value); }

    /// <summary>
    /// Gets or sets background color of the fill.
    /// </summary>
    /*override*/ DmlColor getDmlColor2Internal() { return getBackgroundColor(); }
    /*override*/ void setDmlColor2Internal(DmlColor value) { setBackgroundColor(value); }

    private DmlColor mBackgroundColor;
    private /*PatternType*/int mFillPresetPattern;
    private DmlColor mForegroundColor;
}

