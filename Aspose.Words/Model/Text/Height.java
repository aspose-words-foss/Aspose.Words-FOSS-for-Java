// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/04/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Height attribute. Currently used only internally. Customers still have separate attributes exposed.
/// </summary>
class Height extends InternableComplexAttr implements IComplexAttr, Cloneable
{
    Height(/*HeightRule*/int rule, int value)
    {
        setRule(rule);
        setValue(value);
    }

    /// <summary>
    /// Specifies the rule for determining the height of an object.
    /// </summary>
    /*HeightRule*/int getRule() { return mRule; }
    void setRule(/*HeightRule*/int value)
    {
        notifyChanging();

        mRule = value;

        if (value == HeightRule.AUTO)
            mValue = 0;
    }

    /// <summary>
    /// Specifies the value for determining the height of an object.
    /// </summary>
    int getValue()
    {
        return (mRule == HeightRule.AUTO) ? 0 : mValue;
    }
    void setValue(int value)
    {
        notifyChanging();

        if (value == 0)
        {
            // Reset rule to Auto (Exactly can not be 0 and AtLeast with 0 is equal to Auto).
            mRule = HeightRule.AUTO;
        }
        else
        {
            if (mRule == HeightRule.AUTO)
            {
                // Not 0 value switches rule to AtLeast.
                mRule = HeightRule.AT_LEAST;
            }
        }

        mValue = value;
    }

    public Height deepClone()
    {
        return (Height)memberwiseClone();
    }

    public boolean /*IComplexAttr.*/isInheritedComplexAttr() { return false; }

    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        return (Height)memberwiseClone();
    }

    public boolean equals(Height rhs)
    {
        return (mValue == rhs.mValue) && (mRule == rhs.mRule);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (obj == null || getClass() != obj.getClass())
            return false;

        return equals((Height)obj);
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            return ((/*int.hashCode()*/mValue) * 397) ^ /*enum.hashCode()=(int)value*/mRule;
        }
    }


    private /*HeightRule*/int mRule;
    private int mValue;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

