// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a constant whose value is true or false.
/// </summary>
class BooleanConstant extends Constant
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="value"></param>
    BooleanConstant(boolean value)
    {
        mValue = value;
    }

    /*override*/ double getValueDouble() { return mValue ? 1d : 0d; }

    /*override*/ boolean getValueBoolean() { return mValue; }

    /*override*/ String getValueString() { return mValue ? "1" : "0"; }

    /*override*/ /*ConstantType*/int getConstantType() { return ConstantType.BOOLEAN; }

    private /*final*/ boolean mValue;
}

