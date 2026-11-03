// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents a single value that occurs in a path of a shape.
/// </summary>
class PathValue implements Cloneable
{
    PathValue()
    {
    	this(0, false);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    PathValue(int value)
    {
    	this(value, false);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    PathValue(int value, boolean isFormula)
    {
        mValue = value;
        mIsFormula = isFormula;
    }

    /// <summary>
    /// Returns true if this <see cref="PathValue"/> equals to <paramref name="other"/>.
    /// </summary>
    public boolean equals(PathValue other)
    {
        if (msObject.referenceEquals(null, other))
            return false;

        if (msObject.referenceEquals(this, other))
            return true;

        return (mValue == other.mValue) && (mIsFormula == other.mIsFormula);
    }

    /// <summary>
    /// Clones this <see cref="PathValue"/>.
    /// </summary>
    PathValue deepClone()
    {
        return (PathValue)memberwiseClone();
    }
    
    /// <summary>
    /// True if the Value refers to a formula by index.
    /// False when the Value is just a value.
    /// </summary>
    boolean isFormula() { return mIsFormula; }

    /// <summary>
    /// The value or an index of a formula.
    /// </summary>
    int getValue() { return mValue; }

    public /*override*/ String toString()
    {
        return msString.format("{0}{1}", isFormula() ? "@" : "", getValue());
    }

    private /*final*/ boolean mIsFormula;
    private /*final*/ int mValue;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

