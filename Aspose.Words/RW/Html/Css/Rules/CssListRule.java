// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2024 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a CSS list at-rule.
/// </summary>
class CssListRule extends CssRule
{
    CssListRule(
        CssListRulePrelude prelude,
        CssDeclarationCollection declarations)
    {
    	super(CssRuleType.LIST);
	
        Debug.assertTrue(prelude != null);
        Debug.assertTrue(declarations != null);
        mPrelude = prelude;
        mDeclarations = declarations;
    }

    /*override*/ String toCss()
    {
        StringBuilder cssBuilder = new StringBuilder();
        msStringBuilder.append(cssBuilder, getPrelude().toCss());
        msStringBuilder.append(cssBuilder, " { ");
        msStringBuilder.append(cssBuilder, getDeclarations().getShorthandVersion().toCss());
        msStringBuilder.append(cssBuilder, " }");
        return cssBuilder.toString();
    }

    CssListRulePrelude getPrelude() { return mPrelude; };

    private CssListRulePrelude mPrelude;

    CssDeclarationCollection getDeclarations() { return mDeclarations; };

    private CssDeclarationCollection mDeclarations;
}

