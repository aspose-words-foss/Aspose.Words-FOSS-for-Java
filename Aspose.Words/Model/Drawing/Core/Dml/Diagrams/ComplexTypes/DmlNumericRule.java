// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.2.24 rule (Rule)
/// </summary>
class DmlNumericRule implements Cloneable
{
    DmlConstraintAttributes getConstraintAttributes() { return mConstraintAttributes; }
    void setConstraintAttributes(DmlConstraintAttributes value) { mConstraintAttributes = value; }

    double getValue() { return mValue; }
    void setValue(double value) { mValue = value; }

    double getFactor() { return mFactor; }
    void setFactor(double value) { mFactor = value; }

    double getMax() { return mMax; }
    void setMax(double value) { mMax = value; }

    DmlNumericRule deepCopy()
    {
        return (DmlNumericRule)memberwiseClone();
    }

    private double mValue = Double.NaN;
    private double mFactor = Double.NaN;
    private double mMax = Double.MAX_VALUE;
    private DmlConstraintAttributes mConstraintAttributes = new DmlConstraintAttributes();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

