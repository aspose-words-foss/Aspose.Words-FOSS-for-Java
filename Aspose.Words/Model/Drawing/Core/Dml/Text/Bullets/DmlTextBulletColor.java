// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlTextBulletColor implements Cloneable
{
    /// <summary>
    /// Clones this instance of bullet color.
    /// </summary>
    DmlTextBulletColor deepClone()
    {
        DmlTextBulletColor lhs = (DmlTextBulletColor)memberwiseClone();

        if (mColor != null)
            lhs.mColor = mColor.deepClone();

        return lhs;
    }

    /// <summary>
    /// Flag indicates that bullet color must follow text color.
    /// </summary>
    public boolean getFollowText() { return (mColor == null); }

    /// <summary>
    /// Gets or sets bullet color. Returns null if bullet color must follow text color.
    /// </summary>
    public DmlColor getColor() { return mColor; }
    public void setColor(DmlColor value)
    {
        if(value==null)
            return;

        mColor = value;
    }

    private DmlColor mColor;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

