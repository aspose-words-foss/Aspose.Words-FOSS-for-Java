// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an operator which requires one operand.
/// </summary>
abstract class UnaryOperator extends Operator implements IExecutionItem
{
    public Constant /*IExecutionItem.*/evaluate(ConstantStack calculationStack)
    {
        Constant operand = calculationStack.pop();
        if (operand.isError())
            return operand;

        DoubleConstant doubleConstant = parseDoubleOperand(operand);
        if (doubleConstant == null)
            return ErrorConstant.createSyntaxError();

        return evaluateCore(doubleConstant);
    }

    /// <summary>
    /// Performs the actual evaluation of the operator.
    /// </summary>
    /// <param name="operand">The operand to operate on.</param>
    /// <returns></returns>
    protected abstract Constant evaluateCore(DoubleConstant operand);

    /*override*/ boolean isUnary() { return true; }
}

