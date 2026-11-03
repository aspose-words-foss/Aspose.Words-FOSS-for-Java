// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.words.ref.RefDouble;


/// <summary>
/// When implemented, represents a formula constant.
/// </summary>
abstract class Constant implements IExecutionItem
{
    public Constant /*IExecutionItem.*/evaluate(ConstantStack calculationStack)
    {
        // Just return itself.
        return this;
    }

    /// <summary>
    /// Attempts to format the constant's value using numeric format. Returns a formatted string
    /// if successful, null otherwise.
    /// </summary>
    /// <param name="format">Number format.</param>
    /// <param name="field">Field context.</param>
    /*virtual*/ FieldFormattingResult tryFormatNumber(RichString format, Field field) throws Exception
    {
        return null;
    }

    /// <summary>
    /// Attempts to format the constant's value using date/time format. Returns a formatted string
    /// if successful, null otherwise.
    /// </summary>
    /*virtual*/ String tryFormatDateTime(String format, int eastAsianLanguageId, IFieldResultFormatter resultFormatter)
    {
        return null;
    }

    /*virtual*/ boolean tryConvertToDouble(/*out*/RefDouble value)
    {
        value.set((double)0.0);
        return false;
    }

    /// <summary>
    /// Gets the value of the constant as a number.
    /// </summary>
    /*virtual*/ double getValueDouble() { throw new IllegalStateException(); }

    /// <summary>
    /// Gets the value of the constant as true or false.
    /// </summary>
    /*virtual*/ boolean getValueBoolean() { throw new IllegalStateException(); }
    /// <summary>
    /// Gets the value of the constant as a text string.
    /// </summary>
    /*virtual*/ String getValueString() { throw new IllegalStateException(); }

    boolean isError() { return getConstantType() == ConstantType.ERROR; }

    /// <summary>
    /// Gets the type of the constant.
    /// </summary>
    abstract /*ConstantType*/int getConstantType();
}

