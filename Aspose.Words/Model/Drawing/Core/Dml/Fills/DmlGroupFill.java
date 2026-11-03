// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.8.35 grpFill (Group Fill)
/// This element specifies a group fill. When specified, this setting indicates that the parent element is part
/// of a group and should inherit the fill properties of the group.
/// </summary>
class DmlGroupFill extends DmlFill
{
    /// <summary>
    /// Gets or sets foreground color of the fill.
    /// </summary>
    /*override*/ DmlColor getDmlColorInternal() { return null; }
    /*override*/ void setDmlColorInternal(DmlColor value)
    {
        // Word VBA allows to set up color for GroupFill and changes the type of the fill to Solid implicitly.
        DmlSolidFill solidFill = new DmlSolidFill(value);
        getParent().setFill(solidFill);
    }

    /*override*/ DmlFill deepClone()
    {
        return new DmlGroupFill();
    }

    /*override*/ /*DmlFillType*/int getDmlFillType() { return DmlFillType.GROUP_FILL; }

    /// <summary>
    /// Gets or sets fill color opacity.
    /// </summary>
    public /*override*/ double getOpacity() { return 0.0; }
    public /*override*/ void setOpacity(double value)
    {
        /*Do nothing*/
    }

    /// <summary>
    /// Gets or sets a boolean value indicating whether a parent object is filled.
    /// </summary>
    public /*override*/ boolean getOn() { return false; }
    public /*override*/ void setOn(boolean value)
    {
        if (value)
            getParent().setFill(new DmlSolidFill());
    }
}

