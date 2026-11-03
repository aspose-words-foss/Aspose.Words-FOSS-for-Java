// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/05/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a length value and a flag indicating whether the value is custom or comes from default (user agent) CSS.
/// </summary>
/// <remarks>
/// Instances of this class are immutable. All arithmetic operations on instances of this class return a new instance. 
/// </remarks>
class CssLength
{
    CssLength(double value, boolean isCustom)
    {
        mValue = value;
        mIsCustom = isCustom;
    }

    /// <summary>
    /// Length value.
    /// </summary>
    double getValue() { return mValue; }

    /// <summary>
    /// Indicates whether the length value is custom (non-default), i.e. explicitly specified in the source CSS.
    /// </summary>
    boolean isCustom() { return mIsCustom; }

    public /*override*/ String toString()
    {
        String isCustom = (mIsCustom)
            ? "custom"
            : "default";
        return mValue + "; " + isCustom;
    }

    /// <summary>
    /// Sums two length values.
    /// </summary>
    /// <returns>
    /// The sum of the values. The resulting length is considered custom if any of specified lengths is custom.
    /// </returns>
    public static CssLength op_Addition (CssLength a, CssLength b)
    {
        return new CssLength(a.getValue() + b.getValue(), a.isCustom() || b.isCustom());
    }

    /// <summary>
    /// Inverses the sing of this value.
    /// </summary>
    /// <returns>
    /// The value with the opposite sign. The custom flag remains unchanged.
    /// </returns>
    public static CssLength op_UnaryNegation (CssLength a)
    {
        return new CssLength(-a.getValue(), a.isCustom());
    }

    /// <summary>
    /// Subtracts second value from the first one.
    /// </summary>
    /// <returns>
    /// The difference of the values. The resulting length is considered custom if any of specified lengths is custom.
    /// </returns>
    public static CssLength op_Subtraction (CssLength a, CssLength b)
    {
        return new CssLength(a.getValue() - b.getValue(), a.isCustom() || b.isCustom());
    }

    /// <summary>
    /// A precalculated zero default length value.
    /// </summary>
    static CssLength ZeroDefault = new CssLength(0.0, false);

    private /*final*/ double mValue;
    private /*final*/ boolean mIsCustom;
}

