// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a CSS style rule. A style rule has two main parts: one or more selectors, and one or more declarations.
/// </summary>
class CssStyleRule extends CssRule
{
    CssStyleRule()
    {
    	super(CssRuleType.STYLE);
	
        // Empty constructor.
    }

    CssStyleRule(CssSelector selector, CssDeclarationCollection declarations)
    {
    	this(new CssSelector[] { selector }, declarations);
	
        // Empty constructor.
    }

    CssStyleRule(CssSelector[] selectors, CssDeclarationCollection declarations)
    {
    	this();
	
        Debug.assertTrue(selectors != null);
        Debug.assertTrue(selectors.length > 0);
        Debug.assertTrue(declarations != null);

        mSelectors = selectors;
        mDeclarations = declarations;
    }

    CssStyleRule(CssSelector selector, CssStyle style)
    {
    	this(selector, style.getDeclarations());
	
        // Empty constructor.
    }

    CssStyleRule(CssSelector[] selectors, CssStyle style)
    {
    	this(selectors, style.getDeclarations());
	
        // Empty constructor.
    }

    /// <summary>
    /// Rule selector.
    /// </summary>
    CssSelector[] getSelectors() { return mSelectors; };

    private CssSelector[] mSelectors;

    /// <summary>
    /// Rule declarations.
    /// </summary>
    CssDeclarationCollection getDeclarations() { return mDeclarations; };

    private CssDeclarationCollection mDeclarations;

    /// <summary>
    /// Gets the CSS declaration of the rule. The returned declaration is compatible with CSS 2.1.
    /// </summary>
    /*override*/ String toCss()
    {
        StringBuilder result = new StringBuilder();
        for (CssSelector selector : getSelectors())
        {
            if (result.length() > 0)
            {
                msStringBuilder.append(result, ", ");
            }
            msStringBuilder.append(result, selector.toCss());
        }

        msStringBuilder.append(result, " { ");
        msStringBuilder.append(result, getDeclarations().getShorthandVersion().toCss());
        msStringBuilder.append(result, " }");
        return result.toString();
    }
}

