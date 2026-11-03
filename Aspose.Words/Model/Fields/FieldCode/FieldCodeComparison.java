// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Parses field code of the COMPARE and NEXTIF fields for evaluating comparison expression.
/// Should only be used during field update.
/// </summary>
class FieldCodeComparison implements IComparisonExpression
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="fieldCode"></param>
    FieldCodeComparison(FieldCode fieldCode)
    {
        mFieldCode = fieldCode;
    }

    public String /*IComparisonExpression.*/getLeftExpression() { return mFieldCode.getArgumentAsString(LEFT_EXPRESSION_ARGUMENT_INDEX, false); }

    public FieldArgument /*IComparisonExpression.*/getLeftExpressionArgument() { return mFieldCode.getArgument(LEFT_EXPRESSION_ARGUMENT_INDEX); }

    public String /*IComparisonExpression.*/getComparisonOperator() { return mFieldCode.getArgumentAsString(COMPARISON_OPERATOR_ARGUMENT_INDEX, false); }

    public String /*IComparisonExpression.*/getRightExpression() { return mFieldCode.getArgumentAsString(RIGHT_EXPRESSION_ARGUMENT_INDEX, false); }

    public FieldArgument /*IComparisonExpression.*/getRightExpressionArgument() { return mFieldCode.getArgument(RIGHT_EXPRESSION_ARGUMENT_INDEX); }

    private /*final*/ FieldCode mFieldCode;

    private static final int LEFT_EXPRESSION_ARGUMENT_INDEX = 0;
    private static final int COMPARISON_OPERATOR_ARGUMENT_INDEX = 1;
    private static final int RIGHT_EXPRESSION_ARGUMENT_INDEX = 2;
}

