// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2007 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableDouble;


/// <summary>
/// Represents a COUNT function.
/// </summary>
class CountFunction extends AggregateFunction
{
    protected /*override*/ NullableDouble finalizeCalculation(NullableDouble currentValue, int parameterCount)
    {
        return new NullableDouble(parameterCount);
    }

    protected /*override*/ boolean getCountNullValues() { return false; }
}

