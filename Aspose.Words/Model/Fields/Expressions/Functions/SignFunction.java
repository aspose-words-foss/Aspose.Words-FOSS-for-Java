// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a SIGN function.
/// </summary>
class SignFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        DoubleConstant result = DoubleConstant.createFrom(parameters.get(0), Math.signum(parameters.get(0).getValueDouble()));
        result.isUsesNegativeParentheses(false);
        return result;
    }

    protected /*override*/ int getParameterCountMin() { return 1; }

    protected /*override*/ int getParameterCountMax() { return 1; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        return constantType == ConstantType.DOUBLE;
    }
}

