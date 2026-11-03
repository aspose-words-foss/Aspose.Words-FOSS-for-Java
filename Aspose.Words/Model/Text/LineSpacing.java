// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/09/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Paragraph line spacing attribute. Currently used only internally. Customers still have separate attributes exposed.
/// </summary>
class LineSpacing extends InternableComplexAttr implements IComplexAttr, Cloneable
{
    LineSpacing(int value, /*LineSpacingRule*/int rule)
    {
        mValue = value;
        mRule = rule;
    }

    /// <summary>
    /// Creates model default spacing.
    /// </summary>
    /// <returns></returns>
    public static LineSpacing createDefault()
    {
        LineSpacing defaultLineSpacing = (LineSpacing)ParaPr.fetchDefaultAttr(ParaAttr.LINE_SPACING);
        return defaultLineSpacing.deepClone();
    }

    public LineSpacing deepClone()
    {
        return (LineSpacing)memberwiseClone();
    }

    public boolean isInheritedComplexAttr() { return false; }

    public IComplexAttr deepCloneComplexAttr()
    {
        return (LineSpacing)memberwiseClone();
    }

    public boolean equals(LineSpacing rhs)
    {
        return (mValue == rhs.mValue) && (mRule == rhs.mRule);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (obj == null || getClass() != obj.getClass()) return false;

        return equals((LineSpacing)obj);
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            return (mValue * 397) ^ (int) mRule;
        }
    }

    int getValue() { return mValue; }
    void setValue(int value)
    {
        notifyChanging();

        mValue = value;
    }

    /*LineSpacingRule*/int getRule() { return mRule; }
    void setRule(/*LineSpacingRule*/int value)
    {
        notifyChanging();

        mRule = value;
    }


    private int mValue;
    private /*LineSpacingRule*/int mRule;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

