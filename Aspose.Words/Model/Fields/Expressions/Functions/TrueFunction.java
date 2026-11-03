// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a TRUE function.
/// </summary>
class TrueFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        return new BooleanConstant(true);
    }

    protected /*override*/ int getParameterCountMin() { return 0; }

    protected /*override*/ int getParameterCountMax() { return 0; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        return constantType == ConstantType.BOOLEAN;
    }
}

