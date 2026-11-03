// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a greater than operator.
/// </summary>
class GreaterThanOperator extends BinaryOperator
{
    protected /*override*/ Constant evaluateCore(DoubleConstant operand1, DoubleConstant operand2)
    {
        return new BooleanConstant(operand1.getValueDouble() > operand2.getValueDouble());
    }

    /*override*/ int getOrder() { return 6; }
}

