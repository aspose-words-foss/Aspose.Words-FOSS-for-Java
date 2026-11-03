// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/08/2007 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the properties which shall be applied to a single latent style for this document.
/// Latent styles refer to any set of known style definitions which have not been included
/// in the current document but are known to a hosting application.
/// </summary>
/// <example>
/// [Taken from ECMA TC 45]
/// Consider a WordprocessingML document which contains text specified in one of two styles: Heading1 or Normal.
/// Based on this, the document only needs to store the formatting properties for those two styles,
/// saving the additional overhead which would be required to save all of the styles supported by the hosting application.
/// However, if the documentProtection element (§2.15.1.28) specifies that the hosting application 
/// shall prevent the use of any style whose locked element (§2.7.3.7) is set to false, 
/// then the locking state of all styles known to that application become useful and necessary 
/// to maintain the current state of the document. Using latent styles, this information may be stored
/// without storing any formatting properties for those styles
/// </example>
class LatentStyle implements Cloneable
{
    LatentStyle(
        /*StyleIdentifier*/int styleIdentifier, 
        boolean locked, 
        boolean quickStyle, 
        boolean semiHidden, 
        int uiPriority,
        boolean unhideWhenUsed)
    {
        if (styleIdentifier == StyleIdentifier.USER)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "styleIdentifier");

        mStyleIdentifier = styleIdentifier;
        mUIPriority = uiPriority;
        mLocked = locked;
        mSemiHidden = semiHidden;
        mUnhideWhenUsed = unhideWhenUsed;
        mQuickStyle = quickStyle;
    }

    /// <summary>
    /// Makes a deep clone.
    /// </summary>
    LatentStyle deepClone()
    {
        // RK Memberwise clone is enough.
        return (LatentStyle)memberwiseClone();
    }

    public boolean equals(LatentStyle lsd)
    {
        return
            (lsd.mLocked == mLocked) &&
            (lsd.mQuickStyle == mQuickStyle) &&
            (lsd.mSemiHidden == mSemiHidden) &&
            (lsd.mUIPriority == mUIPriority) &&
            (lsd.mUnhideWhenUsed == mUnhideWhenUsed);
    }
    
    /*StyleIdentifier*/int getStyleIdentifier() { return mStyleIdentifier; }

    /// <summary>
    /// Specifies the default setting for the locked element (§2.7.3.7) which shall be applied to
    /// the latent style with the matching style name value.
    /// </summary>
    boolean getLocked() { return mLocked; }
    void setLocked(boolean value) { mLocked = value; }

    /// <summary>
    /// Specifies the default setting for the qFormat element.
    /// </summary>
    boolean getQuickStyle() { return mQuickStyle; }
    void setQuickStyle(boolean value) { mQuickStyle = value; }

    /// <summary>
    /// Specifies the default setting for the semiHidden element.
    /// </summary>
    boolean getSemiHidden() { return mSemiHidden; }
    void setSemiHidden(boolean value) { mSemiHidden = value; }

    /// <summary>
    /// Specifies the default setting for the uiPriority element.
    /// </summary>
    int getUIPriority() { return mUIPriority; }
    void setUIPriority(int value) { mUIPriority = value; }

    /// <summary>
    /// Specifies the default setting for the unhideWhenUsed element.
    /// </summary>
    boolean getUnhideWhenUsed() { return mUnhideWhenUsed; }
    void setUnhideWhenUsed(boolean value) { mUnhideWhenUsed = value; }

    private /*final*/ /*StyleIdentifier*/int mStyleIdentifier;
    private boolean mLocked;
    private boolean mQuickStyle;
    private boolean mSemiHidden;
    private int mUIPriority;
    private boolean mUnhideWhenUsed;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

