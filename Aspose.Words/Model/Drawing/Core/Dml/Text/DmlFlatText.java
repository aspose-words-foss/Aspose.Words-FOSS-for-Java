// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.5.8 flatTx (No text in 3D scene)
/// Keep text out of 3D scene entirely.
/// </summary>
class DmlFlatText implements Cloneable
{
    DmlFlatText deepClone()
    {
        return (DmlFlatText)memberwiseClone();
    }

    /// <summary>
    /// Specifies the Z coordinate to be used in positioning the flat text within the 3D scene.
    /// </summary>
    double getZCoordinate() { return mZCoordinate; }
    void setZCoordinate(double value) { mZCoordinate = value; }
    
    private double mZCoordinate;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}
