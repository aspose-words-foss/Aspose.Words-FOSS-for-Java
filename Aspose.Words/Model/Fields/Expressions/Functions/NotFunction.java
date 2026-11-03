// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a NOT function.
/// </summary>
class NotFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        return new BooleanConstant(!parameters.get(0).getValueBoolean());
    }

    protected /*override*/ int getParameterCountMin() { return 1; }

    protected /*override*/ int getParameterCountMax() { return 1; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        return constantType == ConstantType.BOOLEAN;
    }
}

