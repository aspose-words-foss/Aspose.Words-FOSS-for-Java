// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Helps to apply CSS style to a model horizontal rule noshade property.
/// </summary>
/*static*/ class CssHRuleNoShadeStyleConverter
{
	/* Simulation of static class by using private constructor */
	private CssHRuleNoShadeStyleConverter()
	{}

    /// <summary>
    /// Aapplies CSS style to a model horizontal rule noshade property.
    /// </summary>
    static void toHorizontalRule(CssDeclarationCollection declarations, Shape horizontalRule)
    {
        // Detect noshade property.
        CssDeclaration backgroundColor = declarations.get("background-color");
        CssDeclaration borderTopColor = declarations.get("border-top-color");
        CssDeclaration borderRightColor = declarations.get("border-right-color");
        CssDeclaration borderBottomColor = declarations.get("border-bottom-color");
        CssDeclaration borderLeftColor = declarations.get("border-left-color");
        if ((backgroundColor != null) && (borderTopColor != null) && (borderRightColor != null) &&
            (borderBottomColor != null) && (borderLeftColor != null) &&
            backgroundColor.getValue().equals(borderTopColor.getValue()) &&
            backgroundColor.getValue().equals(borderRightColor.getValue()) &&
            backgroundColor.getValue().equals(borderBottomColor.getValue()) &&
            backgroundColor.getValue().equals(borderLeftColor.getValue()))
        {
            horizontalRule.getHorizontalRule().setNoShade(true);
        }

        CssDeclaration borderTopStyleDeclaration = declarations.get("border-top-style");
        CssDeclaration borderRightStyleDeclaration = declarations.get("border-right-style");
        CssDeclaration borderBottomStyleDeclaration = declarations.get("border-bottom-style");
        CssDeclaration borderLeftStyleDeclaration = declarations.get("border-left-style");
        if ((borderTopStyleDeclaration != null) && borderTopStyleDeclaration.getValue().equals(CssValue.None) &&
            (borderRightStyleDeclaration != null) && borderRightStyleDeclaration.getValue().equals(CssValue.None) &&
            (borderBottomStyleDeclaration != null) && borderBottomStyleDeclaration.getValue().equals(CssValue.None) &&
            (borderLeftStyleDeclaration != null) && borderLeftStyleDeclaration.getValue().equals(CssValue.None))
        {
            horizontalRule.getHorizontalRule().setNoShade(true);
        }
    }
}
