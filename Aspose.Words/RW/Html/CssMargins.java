// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents CSS margins. 
/// </summary>
class CssMargins extends CssDimensions
{
    CssMargins()
    {
    	super("margin-");
	
    }

    CssMargins(double top, double right, double bottom, double left)
    {
    	this();
	
        setTop(top);
        setRight(right);
        setBottom(bottom);
        setLeft(left);
    }

    CssMargins(CssStyle style)
    {
    	this();
	
        Debug.assertTrue(style != null);
        fromStyle(style);
    }

    private CssMargins(CssMargins other)
    {
    	super(other);
	
    }

    public /*override*/ Object deepClone()
    {
        return new CssMargins(this);
    }

    /// <summary>
    /// Converts logical margins to phisical margins.
    /// </summary>
    /// <param name="blockFlowDirection">Block flow direction.</param>
    /// <param name="rtl">Indicates whether text direction is right-to-left.</param>
    CssMargins toPhisicalMargins(/*CssBlockFlowDirection*/int blockFlowDirection, boolean rtl)
    {
        return (CssMargins)toPhisical(blockFlowDirection, rtl);
    }

    /// <summary>
    /// Converts phisical margins to logical margins.
    /// </summary>
    /// <param name="blockFlowDirection">Block flow direction.</param>
    /// <param name="rtl">Indicates whether text direction is right-to-left.</param>
    CssMargins toLogicalMargins(/*CssBlockFlowDirection*/int blockFlowDirection, boolean rtl)
    {
        return (CssMargins)toLogical(blockFlowDirection, rtl);
    }
}
