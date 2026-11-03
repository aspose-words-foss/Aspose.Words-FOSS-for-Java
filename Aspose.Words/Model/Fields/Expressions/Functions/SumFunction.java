// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableDouble;


/// <summary>
/// Represents a SUM function.
/// </summary>
class SumFunction extends AggregateFunction
{
    protected /*override*/ NullableDouble apply(NullableDouble currentValue, double parameterValue)
    {
        return new NullableDouble(currentValue.getValueOrDefault() + parameterValue);
    }
}

