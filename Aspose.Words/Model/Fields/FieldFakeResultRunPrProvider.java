// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides fake result run properties in a common way for the majority of fields.
/// </summary>
class FieldFakeResultRunPrProvider implements IFieldRunPrProvider
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldFakeResultRunPrProvider(Field field)
    {
        mField = field;
    }

    public RunPr /*IFieldRunPrProvider.*/getRunPr()
    {
        // Fake result run properties should be fully expanded since they are to be applied to runs
        // which do not belong to the model.
        return mField.getStart().getExpandedRunPr(RunPrExpandFlags.LAYOUT);
    }

    private /*final*/ Field mField;
}

