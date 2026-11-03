// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.words.ref.RefDouble;

/// <summary>
/// Represents a constant whose value is an integer number.
/// </summary>
class Int32Constant extends Constant
{
    Int32Constant(int value)
    {
        mValue = value;
    }

    /*override*/ FieldFormattingResult tryFormatNumber(RichString format, Field field)
    {
        return DoubleConstant.tryFormatNumber(mValue, format, field);
    }

    /*override*/ boolean tryConvertToDouble(/*out*/RefDouble value)
    {
        value.set((double)mValue);
        return true;
    }

    /*override*/ double getValueDouble() { return mValue; }

    /*override*/ boolean getValueBoolean() { return (mValue != 0); }

    /*override*/ String getValueString() { return Integer.toString(mValue); }

    /*override*/ /*ConstantType*/int getConstantType() { return ConstantType.INT_32; }

    private /*final*/ int mValue;
}

