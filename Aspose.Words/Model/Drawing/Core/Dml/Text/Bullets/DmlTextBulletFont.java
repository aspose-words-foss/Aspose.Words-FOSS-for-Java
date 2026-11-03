// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class DmlTextBulletFont implements Cloneable
{
    /// <summary>
    /// Clones this instance of bullet font.
    /// </summary>
    DmlTextBulletFont deepClone()
    {
        DmlTextBulletFont lhs = (DmlTextBulletFont)memberwiseClone();

        if (mFont != null)
            lhs.mFont = mFont.deepClone();

        return lhs;
    }

    /// <summary>
    /// Flag indicates that bullet font must follow text font.
    /// </summary>
    public boolean getFollowText() { return (mFont == null); }

    /// <summary>
    /// Gets or sets bullet font. Returns null if bullet font must follow text font.
    /// </summary>
    public DmlFont getFont() { return mFont; }
    public void setFont(DmlFont value)
    {
        if (value == null)
            return;

        mFont = value;
    }

    private DmlFont mFont;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

