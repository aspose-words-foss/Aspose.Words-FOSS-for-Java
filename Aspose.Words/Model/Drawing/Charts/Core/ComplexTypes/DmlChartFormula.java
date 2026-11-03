// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a data reference (such as an Excel formula).
/// (Stores data of the 2.24.3.28 CT_Formula complex types [MS-ODRAWXML].)
/// </summary>
class DmlChartFormula implements Cloneable
{
    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    DmlChartFormula deepClone()
    {
        return (DmlChartFormula)memberwiseClone();
    }

    /// <summary>
    /// Gets or sets value of this formula.
    /// </summary>
    String getValue() { return mValue; }
    void setValue(String value) { mValue = value; }

    /// <summary>
    /// Gets or sets the orientation of the data reference.
    /// </summary>
    /*FormulaDirection*/int getDirection() { return mDirection; }
    void setDirection(/*FormulaDirection*/int value) { mDirection = value; }

    private String mValue;
    private /*FormulaDirection*/int mDirection;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

