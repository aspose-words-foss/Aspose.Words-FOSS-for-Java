// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements the NEXTIF field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Compares the values designated by the expressions <see cref="LeftExpression"/> and <see cref="RightExpression"/>
/// in comparison using the operator designated by <see cref="ComparisonOperator"/>. If the comparison is true,
/// the next data record is merged into the current merge document. (Merge fields that follow the NEXTIF in the main
/// document are replaced by values from the next data record rather than the current data record.)
/// If the comparison is false, the next data record is merged into a new merge document.
/// </remarks>
public class FieldNextIf extends Field
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        return MergeFieldUtil.updateRuleField(this, "«Next Record If»");
    }

    Constant getExpressionResult() throws Exception
    {
        FieldCodeComparison fieldCode = new FieldCodeComparison(getFieldCodeCache());
        return ComparisonEvaluator.evaluate(this, fieldCode);
    }

    /// <summary>
    /// Gets or sets the left part of the comparison expression.
    /// </summary>
    public String getLeftExpression() { return getFieldCodeCache().getArgumentAsString(LEFT_EXPRESSION_ARGUMENT_INDEX, false); }
    public void setLeftExpression(String value) throws Exception { getFieldCodeCache().setArgument(LEFT_EXPRESSION_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the comparison operator.
    /// </summary>
    public String getComparisonOperator() { return getFieldCodeCache().getArgumentAsString(COMPARISON_OPERATOR_ARGUMENT_INDEX); }
    public void setComparisonOperator(String value) throws Exception { getFieldCodeCache().setArgument(COMPARISON_OPERATOR_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the right part of the comparison expression.
    /// </summary>
    public String getRightExpression() { return getFieldCodeCache().getArgumentAsString(RIGHT_EXPRESSION_ARGUMENT_INDEX, false); }
    public void setRightExpression(String value) throws Exception { getFieldCodeCache().setArgument(RIGHT_EXPRESSION_ARGUMENT_INDEX, value); }

    private static final int LEFT_EXPRESSION_ARGUMENT_INDEX = 0;
    private static final int COMPARISON_OPERATOR_ARGUMENT_INDEX = 1;
    private static final int RIGHT_EXPRESSION_ARGUMENT_INDEX = 2;
}

