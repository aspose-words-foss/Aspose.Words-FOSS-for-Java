// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlPointsTextSpacing extends DmlTextSpacing implements Cloneable
{
    DmlPointsTextSpacing()
    {
    	this(new DmlTextPoints());
	
    }

    DmlPointsTextSpacing(DmlTextPoints value)
    {
        mValue = value;
    }

    /// <summary>
    /// Clones this instance of text spacing.
    /// </summary>
    /*override*/ DmlTextSpacing deepClone()
    {
        DmlPointsTextSpacing lhs = (DmlPointsTextSpacing)memberwiseClone();

        if (mValue != null)
            lhs.mValue = mValue.deepClone();

        return lhs;
    }

    /// <summary>
    /// Specifies the size of the white space in point size. Whole points are specified in increments of
    /// 100 starting with 100 being a point size of 1. For instance a font point size of 12 would be 1200
    /// and a font point size of 12.5 would be 1250.
    /// </summary>
    DmlTextPoints getValue() { return mValue; }

    private DmlTextPoints mValue = new DmlTextPoints();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    } // For Java
}

