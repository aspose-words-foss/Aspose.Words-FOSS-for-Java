// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;


/// <summary>
/// Helps to apply CSS height to a model horizontal rule height.
/// </summary>
/*static*/ class CssHRuleHeightStyleConverter
{
	/* Simulation of static class by using private constructor */
	private CssHRuleHeightStyleConverter()
	{}

    /// <summary>
    /// Applies CSS height to a model horizontal rule height.
    /// </summary>
    static void toHorizontalRule(CssDeclarationCollection declarations, Shape horizontalRule)
    {
        double height = 0.0;
        CssDeclaration heightDeclaration = declarations.get("height");
        if (heightDeclaration != null)
        {
            height = CssUtil.lengthToPoint(heightDeclaration.getValue());
            if (MathUtil.isMinValue(height))
                height = 0.0;
        }

        // Correct shape height with HTML borders.
        CssDeclaration borderTopStyleDeclaration = declarations.get("border-top-style");
        CssDeclaration borderBottomStyleDeclaration = declarations.get("border-bottom-style");

        double borderTopWidthPt = 0.0;
        double borderBottomWidthPt = 0.0;
        if ((borderTopStyleDeclaration != null) && !borderTopStyleDeclaration.getValue().equals(CssValue.None))
        {
            CssDeclaration borderTopWidthDeclaration = declarations.get("border-top-width");
            if (borderTopWidthDeclaration != null)
                borderTopWidthPt = CssUtil.getBorderLineWidth(borderTopWidthDeclaration.getValue());
        }
        if ((borderBottomStyleDeclaration != null) && !borderBottomStyleDeclaration.getValue().equals(CssValue.None))
        {
            CssDeclaration borderBottomWidthDeclaration = declarations.get("border-bottom-width");
            if (borderBottomWidthDeclaration != null)
                borderBottomWidthPt = CssUtil.getBorderLineWidth(borderBottomWidthDeclaration.getValue());
        }
        horizontalRule.setHeightSafe(height + borderTopWidthPt + borderBottomWidthPt);
    }
}

