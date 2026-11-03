// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/07/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an OR function.
/// </summary>
class OrFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        return new BooleanConstant(parameters.get(0).getValueBoolean() || parameters.get(1).getValueBoolean());
    }

    protected /*override*/ int getParameterCountMin() { return 2; }

    protected /*override*/ int getParameterCountMax() { return 2; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        return constantType == ConstantType.BOOLEAN;
    }
}

