// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/11/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Office2010 SDT checkbox control properties.
/// </summary>
class SdtCheckBox extends SdtControlProperties implements Cloneable
{
    SdtCheckBox()
    {
        mCheckedStateInfo = SdtCheckBoxStateInfo.DEFAULT_CHECKED_STATE_INFO;
        mUncheckedStateInfo = SdtCheckBoxStateInfo.DEFAULT_UNCHECKED_STATE_INFO;
    }

    /*override*/ /*SdtType*/int getType() { return SdtType.CHECKBOX; }

    /*override*/ SdtControlProperties deepClone()
    {
        SdtCheckBox lhs = (SdtCheckBox)memberwiseClone();

        lhs.mCheckedStateInfo = mCheckedStateInfo.deepClone();
        lhs.mUncheckedStateInfo = mUncheckedStateInfo.deepClone();
        return lhs;
    }

    /// <summary>
    /// Indicates if checkbox is in checked state.
    /// </summary>
    boolean getChecked() { return mChecked; }
    void setChecked(boolean value) { mChecked = value; }

    /// <summary>
    /// Specifies parameters for checked state.
    /// </summary>
    SdtCheckBoxStateInfo getCheckedStateInfo() { return mCheckedStateInfo; }
    void setCheckedStateInfo(SdtCheckBoxStateInfo value) { mCheckedStateInfo = value; }

    /// <summary>
    /// Specifies parameters for unchecked state.
    /// </summary>
    SdtCheckBoxStateInfo getUncheckedStateInfo() { return mUncheckedStateInfo; }
    void setUncheckedStateInfo(SdtCheckBoxStateInfo value) { mUncheckedStateInfo = value; }

    private boolean mChecked;
    private SdtCheckBoxStateInfo mCheckedStateInfo;
    private SdtCheckBoxStateInfo mUncheckedStateInfo;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

