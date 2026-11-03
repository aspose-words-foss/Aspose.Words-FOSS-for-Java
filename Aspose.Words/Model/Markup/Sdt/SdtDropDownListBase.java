// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Common ancestor for <see cref="SdtDropDownList"/> and <see cref="SdtComboBox"/>
/// </summary>
abstract class SdtDropDownListBase extends SdtControlProperties implements Cloneable
{
    /// <summary>
    /// Provides access to all list items <see cref="SdtListItem"/> of this <b>Sdt</b>
    /// </summary>
    SdtListItemCollection getListItems() { return mItems; }

    /*override*/ SdtControlProperties deepClone()
    {
        SdtDropDownListBase lhs = (SdtDropDownListBase)memberwiseClone();
        lhs.mItems = mItems.deepClone();
        return lhs;
    }

    private SdtListItemCollection mItems = new SdtListItemCollection();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

