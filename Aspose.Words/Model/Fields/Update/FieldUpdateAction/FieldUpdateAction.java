// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies what to do with the field after it is updated.
/// </summary>
abstract class FieldUpdateAction
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="field"></param>
    protected FieldUpdateAction(Field field)
    {
        mField = field;
    }

    /// <summary>
    /// Performs the update action (sets field result, removes field etc).
    /// </summary>
    abstract void perform() throws Exception;

    protected Document getDocument() { return getField().fetchDocument(); }

    protected Field getField() { return mField; };

    private Field mField;
}

