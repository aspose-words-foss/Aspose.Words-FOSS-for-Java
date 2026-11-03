// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;

/// <summary>
/// Represents an ABS function.
/// </summary>
class AbsFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        // If field format does not specified, MS Word uses number of decimal places from argument of ABS function for result.
        DoubleConstant doubleParameter = ms.as(parameters.get(0), DoubleConstant.class);
        int numberOfDecimalPlaces = doubleParameter != null ? doubleParameter.getNumberOfDigitsAfterDecimalPoint() : 0;
        return DoubleConstant.createFrom(parameters.get(0), Math.abs(parameters.get(0).getValueDouble()), numberOfDecimalPlaces);
    }

    protected /*override*/ int getParameterCountMin() { return 1; }

    protected /*override*/ int getParameterCountMax() { return 1; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        return constantType == ConstantType.DOUBLE;
    }
}

