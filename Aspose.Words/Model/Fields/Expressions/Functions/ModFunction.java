// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;

/// <summary>
/// Represents a MOD function.
/// </summary>
class ModFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        double x = parameters.get(0).getValueDouble();
        double y = parameters.get(1).getValueDouble();

        if (y == 0d)
            return new ErrorConstant("!Zero Divide");

        // If field format does not specified, MS Word uses maximum number of decimal places from arguments of MOD function for result.
        DoubleConstant doubleFirstParameter = ms.as(parameters.get(0), DoubleConstant.class);
        DoubleConstant doubleSecondParameter = ms.as(parameters.get(1), DoubleConstant.class);
        int numberOfDecimalPlaces = Math.max(
            doubleFirstParameter != null ? doubleFirstParameter.getNumberOfDigitsAfterDecimalPoint() : 0,
            doubleSecondParameter != null ? doubleSecondParameter.getNumberOfDigitsAfterDecimalPoint() : 0);
        return DoubleConstant.createFrom(parameters.get(0), parameters.get(1), x % y, numberOfDecimalPlaces);
    }

    protected /*override*/ int getParameterCountMin() { return 2; }

    protected /*override*/ int getParameterCountMax() { return 2; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        return constantType == ConstantType.DOUBLE;
    }
}

