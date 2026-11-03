// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/03/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class MathMatrixColumnPr implements Cloneable
{
    MathMatrixColumnPr deepClone()
    {
        return (MathMatrixColumnPr)memberwiseClone();
    }
    
    /// <summary>
    /// Specifies <see cref="Drawing.HorizontalAlignment"/> of a matrix column within <see cref="MathObjectMatrix"/>.
    /// This property does not allow whole range of <see cref="Drawing.HorizontalAlignment"/> enum values.
    /// Instead it is limited to:
    /// <see cref="Drawing.HorizontalAlignment.Left"/>
    /// <see cref="Drawing.HorizontalAlignment.Right"/>
    /// <see cref="Drawing.HorizontalAlignment.Center"/>, being default value.
    /// </summary>
    /*HorizontalAlignment*/int getHorizontalAlignment() { return mXAlign; }

    void setHorizontalAlignment(/*HorizontalAlignment*/int value)
    {
        switch (value)
        {
            case HorizontalAlignment.LEFT:
            case HorizontalAlignment.RIGHT:
            case HorizontalAlignment.CENTER:
                mXAlign = value;
                break;
            default:
                mXAlign = HorizontalAlignment.CENTER;
                break;
        }
    }
    
    private /*HorizontalAlignment*/int mXAlign = HorizontalAlignment.CENTER;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

