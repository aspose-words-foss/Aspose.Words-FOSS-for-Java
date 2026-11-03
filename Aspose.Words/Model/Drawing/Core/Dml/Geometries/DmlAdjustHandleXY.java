// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class DmlAdjustHandleXY extends DmlAdjustHandle implements Cloneable
{
    public /*override*/ /*DmlAdjustType*/int getAdjustType(String adjustName)
    {
        if (compareAdjustNames(adjustName, getGdRefX()))
            return DmlAdjustType.COORDINATE;

        if (compareAdjustNames(adjustName, getGdRefY()))
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

    public String getGdRefX() { return mGdRefX; }
    public void setGdRefX(String value) { mGdRefX = value; }

    public String getGdRefY() { return mGdRefY; }
    public void setGdRefY(String value) { mGdRefY = value; }

    private String mGdRefX;
    private String mGdRefY;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

