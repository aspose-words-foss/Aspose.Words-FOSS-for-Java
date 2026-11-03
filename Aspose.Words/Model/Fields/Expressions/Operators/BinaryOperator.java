// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringUtil;

/// <summary>
/// Represents an operator which requires two operands.
/// </summary>
abstract class BinaryOperator extends Operator implements IExecutionItem
{
    public Constant /*IExecutionItem.*/evaluate(ConstantStack calculationStack)
    {
        Constant operand2 = calculationStack.pop();

        if (operand2.isError())
            return operand2;

        // Binary operators works with "double" operands only. When one operands can not be parsed to double, we should show error.
        StringConstant stringConstant2 = ms.as(operand2, StringConstant.class);
        if (stringConstant2 != null)
            return convertStringConstantToError(stringConstant2);

        Constant operand1 = calculationStack.pop();

        if (operand1.isError())
            return operand1;

        StringConstant stringConstant1 = ms.as(operand1, StringConstant.class);
        if (stringConstant1 != null)
            return convertStringConstantToError(stringConstant1);

        DoubleConstant doubleConstant2 = parseDoubleOperand(operand2);
        if (doubleConstant2 == null)
            return new DoubleConstant(0.0);

        DoubleConstant doubleConstant1 = parseDoubleOperand(operand1);
        if (doubleConstant1 == null)
            return new DoubleConstant(0.0);

        return evaluateCore(doubleConstant1, doubleConstant2);
    }

    private static ErrorConstant convertStringConstantToError(StringConstant stringConstant)
    {
        // WORDSNET-13697 Guard against empty string.
        if (StringUtil.hasChars(stringConstant.getValueString()) && Character.isDigit(stringConstant.getValueString().charAt(0)))
            return ErrorConstant.createSyntaxError();

        return ErrorConstant.createBookmarkError(stringConstant.getValueString());
    }

    /// <summary>
    /// Performs the actual evaluation of the operator.
    /// </summary>
    /// <param name="operand1">The first operand to operate on.</param>
    /// <param name="operand2">The second operand to operate on.</param>
    /// <returns>The result of the evaluation.</returns>
    protected abstract Constant evaluateCore(DoubleConstant operand1, DoubleConstant operand2);
}

