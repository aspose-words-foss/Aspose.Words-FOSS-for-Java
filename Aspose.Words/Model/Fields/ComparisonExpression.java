// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/11/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The comparison expression.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public final class ComparisonExpression
{
    ComparisonExpression(String leftExpression, String comparisonOperator, String rightExpression)
    {
        mLeftExpression = leftExpression;
        mComparisonOperator = comparisonOperator;
        mRightExpression = rightExpression;
    }

    /// <summary>
    /// Gets the left expression.
    /// </summary>
    public String getLeftExpression() { return mLeftExpression; };

    private  String mLeftExpression;

    /// <summary>
    /// Gets the comparison operator.
    /// </summary>
    public String getComparisonOperator() { return mComparisonOperator; };

    private  String mComparisonOperator;

    /// <summary>
    /// Gets the right expression.
    /// </summary>
    public String getRightExpression() { return mRightExpression; };

    private  String mRightExpression;
}

