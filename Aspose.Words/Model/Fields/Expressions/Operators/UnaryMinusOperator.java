// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an unary minus operator.
/// </summary>
class UnaryMinusOperator extends UnaryOperator
{
    UnaryMinusOperator()
    {
    	this(false);
	
    }

    UnaryMinusOperator(boolean applyParenthesesNegativeStyle)
    {
        mApplyParenthesesNegativeStyle = applyParenthesesNegativeStyle;
    }

    protected /*override*/ Constant evaluateCore(DoubleConstant operand)
    {
        // If field format does not specified, MS Word uses number of decimal places from argument of minus operator for result.
        DoubleConstant result = DoubleConstant.createFrom(operand, -operand.getValueDouble(), operand.getNumberOfDigitsAfterDecimalPoint());

        if (mApplyParenthesesNegativeStyle)
            result.isUsesNegativeParentheses(true);

        return result;
    }

    /*override*/ int getOrder() { return 1; }

    /*override*/ boolean isUnaryMinus() { return true; }

    private /*final*/ boolean mApplyParenthesesNegativeStyle;
}

