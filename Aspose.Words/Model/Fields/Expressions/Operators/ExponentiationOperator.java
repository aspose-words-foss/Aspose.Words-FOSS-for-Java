// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an exponentiation operator.
/// </summary>
class ExponentiationOperator extends BinaryOperator
{
    protected /*override*/ Constant evaluateCore(DoubleConstant operand1, DoubleConstant operand2)
    {
        // If field format does not specified, MS Word uses sum numbers of decimal places from arguments of exponentiation operator for result.
        int numberOfDecimalPlaces = operand1.getNumberOfDigitsAfterDecimalPoint() + operand2.getNumberOfDigitsAfterDecimalPoint();
        return DoubleConstant.createFrom(operand1, operand2, Math.pow(operand1.getValueDouble(), operand2.getValueDouble()), numberOfDecimalPlaces);
    }

    /*override*/ int getOrder() { return 2; }
}

