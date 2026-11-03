// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements the 'content' CSS property.
/// See http://www.w3.org/TR/CSS21/generate.html#propdef-content.
/// </summary>
class CssContentPropertyDef extends CssIndividualPropertyDef
{
    CssContentPropertyDef()
    {
    	super("content", false, new CssPropertyValue(new CssValueList(CssValue.Normal)));
	
        // Empty constructor.
    }

    /*override*/ CssDeclaration createIndividualDeclaration(
        CssValueList cssValues,
        int startIndex,
        boolean important,
        boolean isInQuirksMode,
        /*out*/RefInt affectedValues)
    {
        affectedValues.set(0);
        Debug.assertTrue(false, "This property isn't part of a shorthand property.");
        return null;
    }

    protected /*override*/ CssPropertyValue createPropertyValue(
        CssValueList cssValues,
        boolean isInQuirksMode)
    {
        return new CssPropertyValue(cssValues);
    }

    protected /*override*/ CssPropertyValue toComputedValueCore(CssPropertyValue specifiedValue, CssCascadeContext cssContex)
    {
        if (cssContex.isPseudoElement())
        {
            if ((specifiedValue.getCount() == 1) && specifiedValue.get(0).equals(CssValue.Normal))
            {
                return new CssPropertyValue(new CssValueList(CssValue.None));
            }

            // Here we should also replace 'attr()' functions with their values but we leave them as is. This could affect
            // inheritance but in current version of HTML and CSS it is impossible for pseudo-elements to have children so 
            // there are no negative effects. 
            return specifiedValue;
        }
        return new CssPropertyValue(new CssValueList(CssValue.Normal));
    }
}

