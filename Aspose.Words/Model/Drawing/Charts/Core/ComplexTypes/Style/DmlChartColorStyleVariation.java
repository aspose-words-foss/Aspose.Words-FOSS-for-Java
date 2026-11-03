// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Represents 2.8.3.3 CT_ColorStyleVariation [MS-ODRAWXML]
/// The complex type specifies a list of transforms that are appended to all colors in a CT_ColorStyle to produce
/// a variation of the color style.
/// </summary>
class DmlChartColorStyleVariation implements Cloneable
{
    /// <summary>
    /// Default ctor.
    /// </summary>
    DmlChartColorStyleVariation()
    {
    }

    /// <summary>
    /// Ctor with specifying color modifiers.
    /// </summary>
    DmlChartColorStyleVariation(/*params*/ IDmlColorModifier... modifiers)
    {
        for (IDmlColorModifier modifier : modifiers)
            getColorModifiers().add(modifier);
    }

    /// <summary>
    /// Clones this <see cref="DmlChartColorStyleVariation"/> object.
    /// </summary>
    DmlChartColorStyleVariation deepClone()
    {
        DmlChartColorStyleVariation lhs = (DmlChartColorStyleVariation)memberwiseClone();

        if (mColorModifiers != null)
        {
            lhs.mColorModifiers = new ArrayList<IDmlColorModifier>();
            for (int i = 0; i < mColorModifiers.size(); i++)
                lhs.mColorModifiers.add(mColorModifiers.get(i).deepClone());
        }

        return lhs;
    }

    /// <summary>
    /// Gets or sets a list of transforms that are appended to colors.
    /// </summary>
    public ArrayList<IDmlColorModifier> getColorModifiers() 
    {
        if (mColorModifiers == null)
            mColorModifiers = new ArrayList<IDmlColorModifier>();
        return mColorModifiers;
    }
    public void setColorModifiers(ArrayList<IDmlColorModifier> value) { mColorModifiers = value; }

    private ArrayList<IDmlColorModifier> mColorModifiers;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

