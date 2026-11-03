// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class DmlGuide implements Cloneable
{
    DmlGuide(String name, DmlFormula formula, boolean isPreset)
    {
        mName = name;
        mFormula = formula;
        mIsPreset = isPreset;
    }

    /// <summary>
    /// Clones this instance of DML guid.
    /// </summary>
    DmlGuide deepClone()
    {
        DmlGuide lhs = (DmlGuide)memberwiseClone();

        if (mFormula != null)
            lhs.mFormula = mFormula.deepClone();

        return lhs;
    }

    String getName() { return mName; }
    void setName(String value) { mName = value; }

    DmlFormula getFormula() { return mFormula; }
    void setFormula(DmlFormula value) { mFormula = value; }
    
    /// <summary>
    /// Mark this DmlGuide is read from the XML definitions of preset geometries.
    /// </summary>
    /// <remarks>
    /// Use this flag to avoid writing Guides from the XML definitions of preset geometries.
    /// </remarks>
    boolean isPreset() { return mIsPreset; }
    void isPreset(boolean value) { mIsPreset = value; }

    private DmlFormula mFormula;
    private String mName;
    private boolean mIsPreset;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}
