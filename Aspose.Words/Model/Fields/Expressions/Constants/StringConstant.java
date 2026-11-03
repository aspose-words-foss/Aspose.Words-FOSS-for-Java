// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.words.ref.RefDouble;

/// <summary>
/// Represents a constant whose value is a text string.
/// </summary>
class StringConstant extends Constant
{
    StringConstant(String value)
    {
        mValue = value;
    }

    /*override*/ FieldFormattingResult tryFormatNumber(RichString format, Field field) throws Exception
    {
        DoubleConstant value = ExpressionEvaluator.evaluateReferenceExpression(new FieldContext(field), mValue);
        return value != null
            ? value.tryFormatNumber(format, field)
            : null;
    }

    /*override*/ String tryFormatDateTime(String format, int eastAsianLanguageId, IFieldResultFormatter resultFormatter)
    {
        DateTimeConstant value = DateTimeConstant.tryParse(mValue);
        return value != null
            ? value.tryFormatDateTime(format, eastAsianLanguageId, resultFormatter)
            : null;
    }

    /*override*/ boolean tryConvertToDouble(/*out*/RefDouble value)
    {
        DoubleConstant constant = DoubleConstant.tryParse(mValue);
        if (constant != null)
        {
            value.set(constant.getValueDouble());
            return true;
        }
        else
        {
            value.set((double)0.0);
            return false;
        }
    }

    /*override*/ String getValueString() { return mValue; }

    /*override*/ /*ConstantType*/int getConstantType() { return ConstantType.STRING; }

    private /*final*/ String mValue;
}

