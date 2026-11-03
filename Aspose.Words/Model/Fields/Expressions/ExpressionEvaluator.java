// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;

/// <summary>
/// Evaluates an expression.
/// </summary>
/*static*/ class ExpressionEvaluator
{
	/* Simulation of static class by using private constructor */
	private ExpressionEvaluator()
	{}

    /// <summary>
    /// Evaluates an expression.
    /// </summary>
    static Constant evaluateExpression(FieldContext fieldContext, String expression) throws Exception
    {
        return evaluate(fieldContext, expression, new FieldExpressionParserBehavior(false));
    }

    /// <summary>
    /// Evaluates an formula field expression.
    /// </summary>
    static Constant evaluateFormulaExpression(Field field, String expression) throws Exception
    {
        FieldContext fieldContext = new FieldContext(field);
        return evaluate(fieldContext, expression, new FieldExpressionParserBehavior(true));
    }

    private static Constant evaluate(FieldContext fieldContext, String expression, IExpressionParserBehavior behavior) throws Exception
    {
        ExecutionQueue queue = ExpressionParser.parse(fieldContext, expression, behavior);

        if (queue == null)
        {
            // An error has occurred during parsing.
            return ErrorConstant.createSyntaxError();
        }

        return ExpressionCalculator.calculate(queue);
    }

    /// <summary>
    /// Evaluates text from cell or bookmark reference.
    /// </summary>
    static DoubleConstant evaluateReferenceExpression(FieldContext fieldContext, String expression) throws Exception
    {
        return evaluateReferenceExpression(fieldContext, expression, true);
    }

    /// <summary>
    /// Evaluates text from cell or bookmark reference.
    /// </summary>
    static DoubleConstant evaluateReferenceExpression(
        FieldContext fieldContext,
        String expression,
        boolean allowLeadingStringConstants) throws Exception
    {
        ReferenceExpressionResult result = evaluateReferenceExpressionWithInfo(
            fieldContext,
            expression,
            allowLeadingStringConstants);

        return result != null
            ? (DoubleConstant)result.getResult()
            : null;
    }

    /// <summary>
    /// Evaluates text from cell or bookmark reference.
    /// </summary>
    static ReferenceExpressionResult evaluateReferenceExpressionWithInfo(
        FieldContext fieldContext,
        String expression,
        boolean allowLeadingStringConstants) throws Exception
    {
        expression = msString.replace(msString.replace(msString.replace(msString.replace(expression, '=', ' '), ControlChar.CELL_CHAR, ' '), '\t', ' '), '\r', ' ');

        ReferenceExpressionParserBehavior behavior = new ReferenceExpressionParserBehavior(
            allowLeadingStringConstants);

        ExecutionQueue queue = ExpressionParser.parse(
            fieldContext,
            expression,
            behavior);

        if (queue == null)
        {
            // An error has occurred during parsing.
            return null;
        }

        DoubleConstant result = ms.as(ExpressionCalculator.calculate(queue), DoubleConstant.class);
        if (result == null)
            return null;

        return new ReferenceExpressionResult(result, behavior.getExpressionHasLeadingMinusOperator());
    }
}

