// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class DmlPercentageTextSpacing extends DmlTextSpacing implements Cloneable
{
    DmlPercentageTextSpacing() // Default is 100% spacing
    { // Default is 100% spacing
    	this(1.0);
	
    }

    DmlPercentageTextSpacing(double value)
    {
        mValue = value;
    }

    /// <summary>
    /// Clones this instance of text spacing.
    /// </summary>
    /*override*/ DmlTextSpacing deepClone()
    {
        return (DmlPercentageTextSpacing)memberwiseClone();
    }

    /// <summary>
    /// Value is in fraction representation.
    /// </summary>
    double getValue() { return mValue; }

    private /*final*/ double mValue;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

