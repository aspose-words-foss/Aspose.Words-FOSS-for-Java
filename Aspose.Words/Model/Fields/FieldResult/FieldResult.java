// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// A field result represented by a constant.
/// </summary>
class FieldResult implements IFieldResult
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="value"></param>
    FieldResult(Constant value)
    {
        mValue = value;
    }

    public Constant /*IFieldResult.*/getFieldResultValue()
    {
       return mValue;
    }

    public NodeRange /*IFieldResult.*/getFieldResultRange()
    {
        return null;
    }

    private /*final*/ Constant mValue;
}

