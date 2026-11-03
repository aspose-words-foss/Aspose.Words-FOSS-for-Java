// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;


class DmlTextBullet implements Cloneable
{
    /// <summary>
    /// Clones this instance of bullet.
    /// </summary>
    DmlTextBullet deepClone()
    {
        DmlTextBullet lhs = (DmlTextBullet)memberwiseClone();

        if (mPictureBullet != null)
            lhs.mPictureBullet = mPictureBullet.deepClone();

        return lhs;
    }

    /// <summary>
    /// Returns true if paragraph has bullet.
    /// </summary>
    public boolean hasBullet() { return (mBulletType != DmlTextBulletType.NONE); }

    /// <summary>
    /// Returns true if bullet is picture bullet.
    /// </summary>
    public boolean isPictureBullet() { return (mBulletType == DmlTextBulletType.PICTURE); }

    /// <summary>
    /// Returns true if bullet is character bullet.
    /// </summary>
    public boolean isCharBullet() { return (mBulletType == DmlTextBulletType.CHAR); }

    /// <summary>
    /// Returns true if bullet is auto num bullet.
    /// </summary>
    public boolean isAutoNumBullet() { return hasBullet() && !isPictureBullet() && !isCharBullet(); }

    /// <summary>
    /// Gets or sets bullet type.
    /// </summary>
    public /*DmlTextBulletType*/int getBulletType() { return mBulletType; }
    public void setBulletType(/*DmlTextBulletType*/int value) { mBulletType = value; }

    /// <summary>
    /// Gets or sets picture bullet.
    /// </summary>
    public DmlBlip getPictureBullet() { return mPictureBullet; }
    public void setPictureBullet(DmlBlip value)
    {
        if(value == null)
            return;

        mPictureBullet = value;
        mBulletType = DmlTextBulletType.PICTURE;
    }

    /// <summary>
    /// Gets or sets char bullet character.
    /// </summary>
    public String getBulletChar() { return mBulletChar; }
    public void setBulletChar(String value)
    {
        if (!StringUtil.hasChars(value))
            return;

        mBulletChar = value;
        mBulletType = DmlTextBulletType.CHAR;
    }

    /// <summary>
    /// Specifies the number that starts a given sequence of automatically numbered bullets. 
    /// When the numbering is alphabetical, the number should map to the appropriate letter. 
    /// For instance 1 maps to 'a', 2 to 'b' and so on. If the numbers are larger than 26, 
    /// then multiple letters should be used. For instance 27 should be represented as 'aa' 
    /// and similarly 53 should be 'aaa'.
    /// </summary>
    public int getStartAt() { return mStartAt; }
    public void setStartAt(int value) { mStartAt = value; }

    private /*DmlTextBulletType*/int mBulletType = DmlTextBulletType.NONE;
    private DmlBlip mPictureBullet;
    private String mBulletChar;
    private int mStartAt = 1;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

