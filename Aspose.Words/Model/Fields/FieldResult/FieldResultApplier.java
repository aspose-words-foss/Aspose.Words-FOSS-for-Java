// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// When implemented by a descendant, applies a field result represented by different objects.
/// </summary>
abstract class FieldResultApplier
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="field"></param>
    protected FieldResultApplier(Field field)
    {
    	this(field, true);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="field"></param>
    /// <param name="applyFormat"></param>
    protected FieldResultApplier(Field field, boolean applyFormat)
    {
        mField = field;
        mApplyFormat = applyFormat;
    }

    /// <summary>
    /// Performs all actions required to apply a field result: removes old result, applies new result
    /// and applies result format.
    /// </summary>
    void applyResult() throws Exception
    {
        // Obtain format applier.
        IFieldResultFormatApplier formatApplier = mApplyFormat
            ? getField().getFormat().getFieldResultFormatProvider().getFormatApplier()
            : null;

        // Forcibly insert field separator here.
        getField().ensureSeparator(true);

        // Remove the old result.
        getField().removeFieldResult();

        // Apply the new result.
        applyResultCore();

        // Apply result format.
        if (formatApplier != null)
            formatApplier.applyFormat(getField().getFieldResultRange());
    }

    /// <summary>
    /// Actually applies the result.
    /// </summary>
    protected abstract void applyResultCore() throws Exception;

    protected Field getField() { return mField; };

    private Field mField;

    private /*final*/ boolean mApplyFormat;
}

