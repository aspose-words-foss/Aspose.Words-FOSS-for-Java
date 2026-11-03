// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/18/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


abstract class DmlFormula implements Cloneable
{
    public abstract double calculate(IDmlGuideValueProvider guideValueProvider);

    /// <summary>
    /// Clones this instance of formula.
    /// </summary>
    /*virtual*/ DmlFormula deepClone()
    {
        return (DmlFormula)memberwiseClone();
    }

    public String getSource() { return mSource; }
    public void setSource(String value) { mSource = value; }

    private String mSource;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

