// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class DmlAdjustHandlePolar extends DmlAdjustHandle implements Cloneable
{
    public /*override*/ /*DmlAdjustType*/int getAdjustType(String adjustName)
    {
        if (compareAdjustNames(adjustName, getGdRefAng()))
            return DmlAdjustType.ANGLE;

        if (compareAdjustNames(adjustName, getGdRefR()))
            return DmlAdjustType.COORDINATE;

        return DmlAdjustType.UNKNOWN;
    }

    /// <summary>
    /// Clones this instance of adjust handle.
    /// </summary>
    /*override*/ DmlAdjustHandle deepClone()
    {
        return (DmlAdjustHandle)memberwiseClone();
    }

    public String getGdRefAng() { return mGdRefAng; }
    public void setGdRefAng(String value) { mGdRefAng = value; }

    public String getGdRefR() { return mGdRefR; }
    public void setGdRefR(String value) { mGdRefR = value; }

    private String mGdRefAng;
    private String mGdRefR;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

