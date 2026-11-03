// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a division operator.
/// </summary>
class DivisionOperator extends BinaryOperator
{
    protected /*override*/ Constant evaluateCore(DoubleConstant operand1, DoubleConstant operand2)
    {
        if (operand2.getValueDouble() == 0d)
            return new ErrorConstant("!Zero Divide");

        // If field format does not specified, MS Word uses sum numbers of decimal places from arguments of division operator, but at least 2, for result.
        int numberOfDecimalPlaces = operand1.getNumberOfDigitsAfterDecimalPoint() + operand2.getNumberOfDigitsAfterDecimalPoint();
        numberOfDecimalPlaces = Math.max(numberOfDecimalPlaces, 2);

        return DoubleConstant.createFrom(operand1, operand2, operand1.getValueDouble() / operand2.getValueDouble(), numberOfDecimalPlaces);
    }

    /*override*/ int getOrder() { return 3; }
}

