// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a CSS rule: either style or at-rule.
/// </summary>
abstract class CssRule
{
    protected CssRule(/*CssRuleType*/int type)
    {
        mType = type;
    }

    /*CssRuleType*/int getType() { return mType; };

    private /*CssRuleType*/int mType;

    abstract String toCss();

}

