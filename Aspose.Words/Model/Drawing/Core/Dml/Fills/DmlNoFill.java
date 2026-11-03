// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.8.44 noFill (No Fill)
/// This element specifies that no fill is applied to the parent element.
/// </summary>
class DmlNoFill extends DmlFill
{
    /*override*/ DmlFill deepClone()
    {
        return new DmlNoFill();
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

    /// <summary>
    /// Defines the transparency of a fill. Valid range from 0 to 1, where 0 is fully transparent and 1 is fully opaque.
    /// </summary>
    public /*override*/ double getOpacity() { return 0.0; }
    public /*override*/ void setOpacity(double value) { /*Do nothing*/ }

    /*override*/ /*DmlFillType*/int getDmlFillType() { return DmlFillType.NO_FILL; }

    /*override*/ DmlColor getDmlColorInternal() { return null; }
    /*override*/ void setDmlColorInternal(DmlColor value)
    {
        // IN. Word VBA allows to set up color for NoFill and changes the type of the fill to Solid implicitly.
        DmlSolidFill solidFill = new DmlSolidFill(value);
        getParent().setFill(solidFill);
    }
}

