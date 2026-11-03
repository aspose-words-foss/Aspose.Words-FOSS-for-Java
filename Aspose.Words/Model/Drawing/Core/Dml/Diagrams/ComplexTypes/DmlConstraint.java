// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.2.8 constr (Constraint)
/// </summary>
class DmlConstraint implements Cloneable
{
    /*DmlBooleanOperator*/int getOperator() { return mOperator; }
    void setOperator(/*DmlBooleanOperator*/int value) { mOperator = value; }

    double getValue() { return mValue; }
    void setValue(double value) { mValue = value; }

    double getFactor() { return mFactor; }
    void setFactor(double value) { mFactor = value; }

    DmlConstraintAttributes getConstraintAttributes() { return mConstraintAttributes;}
    void setConstraintAttributes(DmlConstraintAttributes value) { mConstraintAttributes = value; }

    DmlConstraintAttributes getConstraintReferenceAttributes() { return mConstraintReferenceAttributes;}
    void setConstraintReferenceAttributes(DmlConstraintAttributes value) { mConstraintReferenceAttributes = value; }

    DmlConstraint deepCopy()
    {
        return (DmlConstraint)memberwiseClone();
    }

    private /*DmlBooleanOperator*/int mOperator = DmlBooleanOperator.NONE;
    private double mValue = 0d;
    private double mFactor = 1d;
    private DmlConstraintAttributes mConstraintAttributes = new DmlConstraintAttributes();
    private DmlConstraintAttributes mConstraintReferenceAttributes = new DmlConstraintAttributes();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

