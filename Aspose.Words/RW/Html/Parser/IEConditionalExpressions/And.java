// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a conjunction of subexpressions within a conditional expression. For example, 'ie &amp; vml'.
/// </summary>
class And extends ConditionalExpression
{
    And(ConditionalExpression left, ConditionalExpression right)
    {
        Debug.assertTrue(left != null);
        Debug.assertTrue(right != null);

        mLeftExpression = left;
        mRightExpression = right;
    }

    /*override*/ boolean matches(Features features)
    {
        return mLeftExpression.matches(features) && mRightExpression.matches(features);
    }

    public /*override*/ String toString()
    {
        return mLeftExpression.toStringAsSubexpression() + " & " + mRightExpression.toStringAsSubexpression();
    }

    private /*final*/ ConditionalExpression mLeftExpression;
    private /*final*/ ConditionalExpression mRightExpression;
}
