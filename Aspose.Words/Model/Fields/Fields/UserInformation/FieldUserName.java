// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;

/// <summary>
/// Implements the USERNAME field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the current user's name.
/// </remarks>
public class FieldUserName extends Field
{
    /*override*/ FieldUpdateAction updateCore()
    {
        String result = getFieldCodeCache().hasArgument(USER_NAME_ARGUMENT_INDEX)
            ? getUserName()
            : fetchDocument().getFieldOptions().getEffectiveUser().getName();

        result = StringUtil.truncate(result, MAX_RESULT_LENGTH);

        return new FieldUpdateActionApplyResult(this, result);
    }

    /// <summary>
    /// Gest or sets the current user's name.
    /// </summary>
    public String getUserName() { return getFieldCodeCache().getArgumentAsString(USER_NAME_ARGUMENT_INDEX); }
    public void setUserName(String value) throws Exception { getFieldCodeCache().setArgument(USER_NAME_ARGUMENT_INDEX, value); }

    private static final int USER_NAME_ARGUMENT_INDEX = 0;
    private static final int MAX_RESULT_LENGTH = 55;
}

