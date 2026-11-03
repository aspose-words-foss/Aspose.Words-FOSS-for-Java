// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msMath;

/// <summary>
/// Represents a ROUND function.
/// </summary>
class RoundFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        double x = parameters.get(0).getValueDouble();
        double y = parameters.get(1).getValueDouble();

        int numberOfDecimalPlaces = (int)Math.floor(y);
        double result;

        if (numberOfDecimalPlaces < 0)
        {
            double p = Math.pow(10.0, -numberOfDecimalPlaces);
            numberOfDecimalPlaces = 0;
            result = msMath.round(x/p)*p;
        }
        else
        {
            result = msMath.round(x, numberOfDecimalPlaces);
        }

        return DoubleConstant.createFrom(parameters.get(0), parameters.get(1), result, numberOfDecimalPlaces);
    }

    protected /*override*/ int getParameterCountMin() { return 2; }

    protected /*override*/ int getParameterCountMax() { return 2; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        return constantType == ConstantType.DOUBLE;
    }
}

