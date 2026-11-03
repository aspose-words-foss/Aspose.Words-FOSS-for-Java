// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an unary percent operator.
/// </summary>
class UnaryPercentOperator extends UnaryOperator
{
    protected /*override*/ Constant evaluateCore(DoubleConstant operand)
    {
        // If field format does not specified, MS Word uses number of decimal places from argument of percent operator adding 2 for result.
        return DoubleConstant.createFrom(operand, operand.getValueDouble() * 0.01, operand.getNumberOfDigitsAfterDecimalPoint() + 2);
    }

    /*override*/ int getOrder() { return 1; }
}

