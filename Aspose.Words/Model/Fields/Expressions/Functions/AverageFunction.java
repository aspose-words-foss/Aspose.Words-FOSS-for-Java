// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableDouble;


/// <summary>
/// Represents an AVERAGE function.
/// </summary>
class AverageFunction extends AggregateFunction
{
    protected /*override*/ NullableDouble apply(NullableDouble currentValue, double parameterValue)
    {
        return new NullableDouble(currentValue.getValueOrDefault() + parameterValue);
    }

    protected /*override*/ int applyNumberOfDecimalPlaces(int currentNumberOfDecimalPlaces, DoubleConstant parameter)
    {
        // If field format does not specified, MS Word uses maximum number of decimal places from arguments of AVERAGE function, but at least 2, for result.
        return Math.max(super.applyNumberOfDecimalPlaces(currentNumberOfDecimalPlaces, parameter), 2);
    }

    protected /*override*/ NullableDouble finalizeCalculation(NullableDouble currentValue, int parameterCount)
    {
        if (!currentValue.hasValue())
            return NullableDouble.NULL;

        return new NullableDouble(currentValue.getValueOrDefault() / parameterCount);
    }

    protected /*override*/ NullableDouble getInitialValue() { return NullableDouble.NULL; }

    protected /*override*/ boolean getCountNullValues() { return false; }
}

