// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/05/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies manual run width.
/// </summary>
/// <remarks>
/// See ECMA 376. 17.3.2.14 fitText (Manual Run Width).
/// </remarks>
class FitText implements IComplexAttr, ICustomEquality, Cloneable
{
    FitText(int value, int id)
    {
        mValue = value;
        mId = id;
    }

    public FitText deepClone()
    {
        return (FitText)memberwiseClone();
    }

    public boolean isInheritedComplexAttr() { return false; }

    public IComplexAttr deepCloneComplexAttr()
    {
        return (FitText)memberwiseClone();
    }

    /// <summary>
    /// Returns <b>true</b> if the current instance and the specified one have the same value.
    /// </summary>
    public boolean /*ICustomEquality.*/hasSameValue(ICustomEquality layout)
    {
        if ((layout == null) || (getClass() != layout.getClass()))
            return false;

        FitText rhs = (FitText)layout;

        // Id is not included because instances with different IDs should be collapsed if the width is equal.
        return (getValue() == rhs.getValue());
    }

    public boolean equals(FitText rhs)
    {
        return (mValue == rhs.mValue) && (mId == rhs.mId);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if ((obj == null) || (getClass() != obj.getClass())) return false;

        return equals((FitText)obj);
    }

    public /*override*/ int hashCode()
    {
        return mValue ^ mId;
    }

    int getValue() { return mValue; }
    void setValue(int value) { mValue = value; }

    int getId() { return mId; }
    void setId(int value) { mId = value; }


    private int mValue;
    private int mId;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

