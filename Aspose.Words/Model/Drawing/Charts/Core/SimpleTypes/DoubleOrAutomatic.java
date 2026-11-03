// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Stores values of the 2.24.4.5 ST_DoubleOrAutomatic, 2.24.4.22 ST_ValueAxisUnit, 2.24.4.8 ST_GapWidthRatio
/// simple types [MS-ODRAWXML].
/// </summary>
class DoubleOrAutomatic implements Cloneable
{
    /// <summary>
    /// Ctor that allows instance creation only from static methods.
    /// </summary>
    private DoubleOrAutomatic()
    {
    }

    /// <summary>
    /// Creates numeric value of <see cref="DoubleOrAutomatic"/>.
    /// </summary>
    static DoubleOrAutomatic fromDouble(double value)
    {
        DoubleOrAutomatic result = new DoubleOrAutomatic();
        result.mValue = value;
        return result;
    }

    /// <summary>
    /// Creates not-defined value of <see cref="DoubleOrAutomatic"/>.
    /// </summary>
    static DoubleOrAutomatic asNull(double defaultValue)
    {
        DoubleOrAutomatic result = new DoubleOrAutomatic();
        result.mValue = defaultValue;
        result.isNull(true);
        return result;
    }

    /// <summary>
    /// Creates automatic value of <see cref="DoubleOrAutomatic"/>.
    /// </summary>
    static DoubleOrAutomatic asAuto(double defaultValue)
    {
        DoubleOrAutomatic result = new DoubleOrAutomatic();
        result.mValue = defaultValue;
        result.isAuto(true);
        return result;
    }

    /// <summary>
    /// Creates a clone of this object.
    /// </summary>
    DoubleOrAutomatic deepClone()
    {
        return (DoubleOrAutomatic)memberwiseClone();
    }

    /// <summary>
    /// Gets or sets numeric value of this instance of <see cref="DoubleOrAutomatic"/>.
    /// </summary>
    double getValue() { return mValue; }
    void setValue(double value)
    {
        mValue = value;
        mIsAuto = false;
        mIsNull = false;
    }

    /// <summary>
    /// Gets or sets a flag indicating that automatic value should be used.
    /// </summary>
    boolean isAuto() { return mIsAuto; }
    void isAuto(boolean value)
    {
        mIsAuto = value;
        if (value)
            mIsNull = false;
    }

    /// <summary>
    /// Gets or sets a flag indicating that no value is defined in this instance of <see cref="DoubleOrAutomatic"/>.
    /// </summary>
    boolean isNull() { return mIsNull; }
    void isNull(boolean value)
    {
        mIsNull = value;
        if (value)
            mIsAuto = false;
    }

    /// <summary>
    /// Gets a flag indicating that this instance has not-defined or automatic value.
    /// </summary>
    boolean isNullOrAuto() { return mIsNull || mIsAuto; }

    private boolean mIsNull;
    private boolean mIsAuto;
    private double mValue;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

