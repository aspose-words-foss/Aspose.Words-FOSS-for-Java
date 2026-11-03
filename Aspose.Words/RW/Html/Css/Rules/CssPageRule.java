// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/05/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringUtil;


/// <summary>
/// Represents a CSS @page rule.
/// </summary>
/// <remarks>
/// Authors can specify various aspects of a page box, such as its dimensions, orientation, and margins, within an @page
/// rule. @page rules are allowed wherever rule-sets are allowed. An @page rule consists of the keyword ‘@page’, an optional
/// comma-separated list of page selectors and a block of declarations. An @page rule can also contain other at-rules, 
/// interleaved between declarations. http://www.w3.org/TR/css3-page/#at-page-rule
/// </remarks>
class CssPageRule extends CssRule
{
    /// <summary>
    /// Constructor. Initializes a new instance of the class.
    /// </summary>
    /// <param name="pageSelector">Selector of the @page rule.</param>
    /// <param name="declarations">Declarations of the @page rule. Nested at-rules are not supported.</param>
    CssPageRule(CssPageSelector pageSelector, CssDeclarationCollection declarations)
    {
    	super(CssRuleType.PAGE);
	
        Debug.assertTrue(pageSelector != null);
        Debug.assertTrue(declarations != null);

        mSelector = pageSelector;
        mDeclarations = declarations;
    }

    /// <summary>
    /// Rule selector.
    /// </summary>
    CssPageSelector getSelector() { return mSelector; };

    private CssPageSelector mSelector;

    /// <summary>
    /// Rule declarations.
    /// </summary>
    CssDeclarationCollection getDeclarations() { return mDeclarations; };

    private CssDeclarationCollection mDeclarations;

    /// <summary>
    /// Gets the CSS declaration of the rule.
    /// </summary>
    /*override*/ String toCss()
    {
        StringBuilder css = new StringBuilder();
        msStringBuilder.append(css, "@page");

        String selectorCss = getSelector().toCss();
        if (StringUtil.hasChars(selectorCss))
        {
            css.append(' ');
            msStringBuilder.append(css, selectorCss);
        }

        msStringBuilder.append(css, " { ");
        msStringBuilder.append(css, getDeclarations().getShorthandVersion().toCss());
        msStringBuilder.append(css, " }");
        return css.toString();
    }
}

