// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Implements 'width' CSS property.
/// </summary>
class CssWidthPropertyDef extends CssIndividualSimplePropertyDef
{
    CssWidthPropertyDef()
    {
    	super(
            "width",
            false,
            CssValue.Auto,
            // <length> | <percentage> | auto
            CssValueFilter.anyOf(
                CssValueFilter.NON_NEGATIVE_QUIRKY_LENGTH,
                CssValueFilter.NON_NEGATIVE_PERCENTAGE,
                CssValueFilter.values(CssValue.Auto)));
	
        // Empty constructor.
    }

    /*override*/ void toCellFormat(CssPropertyValue propertyValue, CellFormat cellFormat)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        PreferredWidth preferredWidth = CssWidthStyleConverter.getPreferredWidth(propertyValue);
        if (preferredWidth != null)
            cellFormat.setPreferredWidth(preferredWidth);
    }

    /*override*/ void toHorizontalRule(CssPropertyValue propertyValue, Shape horizontalRuleShape)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        PreferredWidth preferredWidth = CssWidthStyleConverter.getPreferredWidth(propertyValue);
        if (preferredWidth == null)
            return;

        switch (preferredWidth.getType())
        {
            case PreferredWidthType.AUTO:
            {
                // Ignore auto value.
                break;
            }
            case PreferredWidthType.PERCENT:
            {
                Section section = (Section)horizontalRuleShape.getAncestor(NodeType.SECTION);
                Debug.assertTrue(section != null);
                if (preferredWidth.getValue() >= 0)
                {
                    horizontalRuleShape.getHorizontalRule().setPercentSafe(preferredWidth.getValue());
                    horizontalRuleShape.setWidthSafe(section.getPageSetup().getContentWidth() * horizontalRuleShape.getHorizontalRule().getPercent() / 100.0);
                }
                break;
            }
            case PreferredWidthType.POINTS:
            {
                if (preferredWidth.getValue() >= 0)
                {
                    horizontalRuleShape.setWidthSafe(preferredWidth.getValue());
                    horizontalRuleShape.getHorizontalRule().setPercent(0.0);
                }
                break;
            }
            default:
            {
                Debug.assertTrue(false);
                break;
            }
        }
    }
}

