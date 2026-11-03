// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/04/2025 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

class CssMsoPaginationPropertyDef extends CssIndividualPropertyDef
{
    CssMsoPaginationPropertyDef()
    {
    	super("mso-pagination", false, null);
	
        // Empty constructor.
    }

    /*override*/ CssDeclaration createIndividualDeclaration(
        CssValueList cssValues,
        int startIndex,
        boolean important,
        boolean isInQuirksMode,
        /*out*/RefInt affectedValues)
    {
        Debug.assertTrue(false, "This property cannot be a shorthand property part.");
        affectedValues.set(0);
        return null;
    }

    protected /*override*/ CssPropertyValue createPropertyValue(
        CssValueList cssValues,
        boolean isInQuirksMode)
    {
        for (CssValue value : cssValues)
        {
            if (!isMsoPaginationValue(value))
            {
                return null;
            }
        }
        return new CssTextDecorationPropertyValue(cssValues);
    }

    private static boolean isMsoPaginationValue(CssValue value)
    {
        return value.equalsAny(CssValue.None, CssValue.WidowOrphan, CssValue.LinesTogether, CssValue.NoLineNumbers);
    }
}

