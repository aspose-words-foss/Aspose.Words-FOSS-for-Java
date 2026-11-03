// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implemented by the IF and COMPARE field code objects to represent an input for ComparisonEvaluator.
/// </summary>
interface IComparisonExpression
{
    /// <summary>
    /// Gets the left hand expression.
    /// </summary>
    public 
    String getLeftExpression() throws Exception;

    /// <summary>
    /// Gets the left hand expression field argument.
    /// </summary>
    public FieldArgument getLeftExpressionArgument();

    /// <summary>
    /// Gets the comparison operator.
    /// </summary>
    public 
    String getComparisonOperator() throws Exception;

    /// <summary>
    /// Gets the right hand expression.
    /// </summary>
    public 
    String getRightExpression() throws Exception;

    /// <summary>
    /// Gets the right hand expression field argument.
    /// </summary>
    public FieldArgument getRightExpressionArgument();
}

