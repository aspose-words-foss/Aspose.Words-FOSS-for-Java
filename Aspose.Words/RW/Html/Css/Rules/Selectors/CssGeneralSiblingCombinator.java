// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/10/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the CSS general sibling combinator. For example, the '~ p' part of 'div ~ p'.
/// </summary>
/// <remarks>
/// The 'a ~ b' selector selects a 'b' element if both 'a' and 'b' elements share the same parent and the 'a' element
/// precedes the 'b' element.
/// See http://www.w3.org/TR/css3-selectors/#general-sibling-combinators
/// </remarks>
class CssGeneralSiblingCombinator extends CssCombinator
{
    public CssGeneralSiblingCombinator(CssCombinableSelector selector)
    {
    	super(selector);
	
        // Empty constructor.
    }

    /*override*/ String toCss()
    {
        return " ~ " + getSelector().toCss();
    }

    /*override*/ String getPreferableStyleName()
    {
        return " ~ " + getSelector().getPreferableStyleName();
    }
}

