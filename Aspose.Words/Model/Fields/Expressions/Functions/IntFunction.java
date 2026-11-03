// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an INT function.
/// </summary>
class IntFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        return DoubleConstant.createFrom(parameters.get(0), (int)parameters.get(0).getValueDouble());
    }

    protected /*override*/ int getParameterCountMin() { return 1; }

    protected /*override*/ int getParameterCountMax() { return 1; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        return constantType == ConstantType.DOUBLE;
    }
}

