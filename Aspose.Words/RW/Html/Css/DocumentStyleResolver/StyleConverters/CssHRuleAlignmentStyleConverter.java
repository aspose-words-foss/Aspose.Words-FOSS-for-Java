// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Helps to apply CSS margins to a model horizontal rule alignment.
/// </summary>
/*static*/ class CssHRuleAlignmentStyleConverter
{
	/* Simulation of static class by using private constructor */
	private CssHRuleAlignmentStyleConverter()
	{}

    /// <summary>
    /// Applies CSS margins style to a model horizontal rule alignment.
    /// </summary>
    static void toHorizontalRule(CssDeclarationCollection declarations, Shape horizontalRule)
    {
        // Set shape alignment.
        CssDeclaration marginLeft = declarations.get("margin-left");
        CssDeclaration marginRight = declarations.get("margin-right");
        if ((marginLeft != null) && (marginRight != null))
        {
            if (marginLeft.getValue().equals(CssValue.Auto) && marginRight.getValue().equals(CssValue.Auto))
                horizontalRule.getHorizontalRule().setAlign(HorizontalRuleAlignment.CENTER);
            else if (marginLeft.getValue().equals(CssValue.Zero) && marginRight.getValue().equals(CssValue.Auto))
                horizontalRule.getHorizontalRule().setAlign(HorizontalRuleAlignment.LEFT);
            else if (marginLeft.getValue().equals(CssValue.Auto) && marginRight.getValue().equals(CssValue.Zero))
                horizontalRule.getHorizontalRule().setAlign(HorizontalRuleAlignment.RIGHT);
        }
    }
}

