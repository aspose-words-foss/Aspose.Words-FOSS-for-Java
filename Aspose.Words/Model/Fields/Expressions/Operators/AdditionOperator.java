// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an addition operator.
/// </summary>
class AdditionOperator extends BinaryOperator
{
    protected /*override*/ Constant evaluateCore(DoubleConstant operand1, DoubleConstant operand2)
    {
        // If field format does not specified, MS Word uses maximum number of decimal places from arguments of addition operator for result.
        int numberOfDecimalPlaces = Math.max(operand1.getNumberOfDigitsAfterDecimalPoint(),
                                        operand2.getNumberOfDigitsAfterDecimalPoint());
        return DoubleConstant.createFrom(operand1, operand2, operand1.getValueDouble() + operand2.getValueDouble(), numberOfDecimalPlaces);
    }

    /*override*/ int getOrder() { return 4; }
}

