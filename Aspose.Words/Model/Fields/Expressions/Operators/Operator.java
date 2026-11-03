// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;

/// <summary>
/// Represents a formula operator.
/// </summary>
abstract class Operator
{
    /// <summary>
    /// Gets the order of evaluation of this operator.
    /// </summary>
    abstract int getOrder();

    /// <summary>
    /// Returns true if this operator is a left parenthesis operator.
    /// </summary>
    /*virtual*/ boolean isLeftParenthesis() { return false; }

    /// <summary>
    /// Returns true if this operator is a right parenthesis operator.
    /// </summary>
    /*virtual*/ boolean isRightParenthesis() { return false; }

    /// <summary>
    /// Returns true if this operator is unary.
    /// </summary>
    /*virtual*/ boolean isUnary() { return false; }

    /// <summary>
    /// Returns true if this operator is unary minus.
    /// </summary>
    /*virtual*/ boolean isUnaryMinus() { return false; }

    protected static DoubleConstant parseDoubleOperand(Constant operand)
    {
        DoubleConstant doubleConstant = ms.as(operand, DoubleConstant.class);
        if (doubleConstant != null)
            return doubleConstant;

        BooleanConstant booleanConstant = ms.as(operand, BooleanConstant.class);
        if (booleanConstant != null)
            return new DoubleConstant(booleanConstant.getValueDouble());

        return null;
    }
}

