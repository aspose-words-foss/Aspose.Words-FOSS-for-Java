// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an IF function.
/// </summary>
/// <remarks>
/// Not in the OOXML specs.
/// </remarks>
class IfFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        if (parameters.get(0) instanceof BooleanConstant)
            return (parameters.get(0).getValueBoolean()) ? parameters.get(1) : parameters.get(2);
        return parameters.get(1);
    }

    protected /*override*/ int getParameterCountMin() { return 3; }

    protected /*override*/ int getParameterCountMax() { return 3; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        return true;
    }
}

