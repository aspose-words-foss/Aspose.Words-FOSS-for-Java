// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a negation of a part of a conditional expression. For example: '!vml' or '!(ie &amp; vml)'.
/// </summary>
class Not extends ConditionalExpression
{
    Not(ConditionalExpression expression)
    {
        Debug.assertTrue(expression != null);
        mExpression = expression;
    }
    
    /*override*/ boolean matches(Features features)
    {
        return !mExpression.matches(features);
    }

    public /*override*/ String toString()
    {
        return "!" + mExpression.toStringAsSubexpression();
    }

    private /*final*/ ConditionalExpression mExpression;
}
