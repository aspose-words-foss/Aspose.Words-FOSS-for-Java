// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Provides a set of utility methods shared between fields of different types participating in a mail merge.
/// </summary>
/*static*/ class MergeFieldUtil
{
	/* Simulation of static class by using private constructor */
	private MergeFieldUtil()
	{}

    /// <summary>
    /// Returns <see cref="FieldUpdateAction"/> setting the specified field's result to the specified static text value,
    /// </summary>
    static FieldUpdateAction processUnusedField(Field field, String staticText)
    {
        return new FieldUpdateActionApplyResult(field, staticText);
    }

    /// <summary>
    /// Updates NEXT, NEXTIF and SKIPIF fields.
    /// </summary>
    static FieldUpdateAction updateRuleField(Field field, String staticText) throws Exception
    {
        Debug.assertTrue(field.getType() == FieldType.FIELD_NEXT || field.getType() == FieldType.FIELD_NEXT_IF || field.getType() == FieldType.FIELD_SKIP_IF);

        Constant value = field.getUpdater().getDataProviders().getValue(field);
        if (value == null)
            return processUnusedField(field, staticText);

        if (value.isError())
        {
            // I'm not sure if this is correct inserting an error message here, but it seems like
            // we have no another option.
            return new FieldUpdateActionInsertErrorMessage(field, value.getValueString());
        }

        return null;
    }
}

