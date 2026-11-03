// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Implements 'vertical-align' CSS property.
/// </summary>
class CssVerticalAlignPropertyDef extends CssIndividualSimplePropertyDef
{
    CssVerticalAlignPropertyDef()
    {
    	super(
              "vertical-align",
              false,
              CssValue.Baseline,
              // baseline | sub | super | top | text-top | middle | bottom | text-bottom | <percentage> | <length>
              CssValueFilter.anyOf(
                  CssValueFilter.values(
                        CssValue.Baseline,
                        CssValue.Sub,
                        CssValue.Super,
                        CssValue.Top,
                        CssValue.TextTop,
                        CssValue.Middle,
                        CssValue.Bottom,
                        CssValue.TextBottom),
                  CssValueFilter.PERCENTAGE,
                  CssValueFilter.LENGTH));
	
        // Empty constructor.
    }

    /*override*/ void toCellFormat(CssPropertyValue propertyValue, CellFormat cellFormat)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        CssValue cssValue = propertyValue.getFirstValue();
        switch (cssValue.getValueType())
        {
            case CssValueType.LENGTH:
            case CssValueType.PERCENTAGE:
                break;
            case CssValueType.IDENTIFIER:
            {
                // WORDSNET-11645 MS Word imports baseline as bottom though this doesn't provide proper look.
                // Don't see how we can do better so here we duplicates MS Word's behavior.
                if (cssValue.equals(CssValue.Baseline))
                    cellFormat.setVerticalAlignment(CellVerticalAlignment.BOTTOM);
                else if (cssValue.equals(CssValue.Top))
                    cellFormat.setVerticalAlignment(CellVerticalAlignment.TOP);
                else if (cssValue.equals(CssValue.Center))
                    cellFormat.setVerticalAlignment(CellVerticalAlignment.CENTER);
                else if (cssValue.equals(CssValue.Bottom))
                    cellFormat.setVerticalAlignment(CellVerticalAlignment.BOTTOM);
                else
                    cellFormat.setVerticalAlignment(CellVerticalAlignment.CENTER);
                break;
            }
            default:
                Debug.assertTrue(false);
                break;
        }
    }
}

