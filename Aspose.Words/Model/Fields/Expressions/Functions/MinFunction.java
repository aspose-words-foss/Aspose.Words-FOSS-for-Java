// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableDouble;


/// <summary>
/// Represents a MIN function.
/// </summary>
class MinFunction extends AggregateFunction
{
    protected /*override*/ NullableDouble apply(NullableDouble currentValue, double parameterValue)
    {
        if (currentValue.hasValue())
            return new NullableDouble(Math.min(currentValue.getValue(), parameterValue));

        return new NullableDouble(parameterValue);
    }

    protected /*override*/ NullableDouble finalizeCalculation(NullableDouble currentValue, int parameterCount)
    {
        if (currentValue.hasValue())
            return currentValue;

        return new NullableDouble(0d);
    }

    protected /*override*/ NullableDouble getInitialValue() { return NullableDouble.NULL; }

    protected /*override*/ Constant getNullCellReferenceSubstitution() { return new DoubleConstant(0d); }
}

