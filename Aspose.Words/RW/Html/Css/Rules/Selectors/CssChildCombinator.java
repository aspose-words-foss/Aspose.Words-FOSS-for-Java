// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/10/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the CSS child combinator. For example, the '> p' part of 'div > p'.
/// </summary>
/// <remarks>
/// The selector 'a > b' selects a 'b' element that is a child of an 'a' element.
/// See http://www.w3.org/TR/css3-selectors/#child-combinators
/// </remarks>
class CssChildCombinator extends CssCombinator
{
    public CssChildCombinator(CssCombinableSelector selector)
    {
    	super(selector);
	
        // Empty constructor.
    }

    /*override*/ String toCss()
    {
        return " > " + getSelector().toCss();
    }

    /*override*/ String getPreferableStyleName()
    {
        return " > " + getSelector().getPreferableStyleName();
    }
}

