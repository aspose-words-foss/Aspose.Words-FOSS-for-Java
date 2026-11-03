// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import java.util.Map;


/// <summary>
/// Implements DOCVARIABLE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class FieldDocVariable extends Field
{
    /*override*/ FieldUpdateAction updateCore()
    {
        if (!StringUtil.hasChars(getVariableName()))
            return new FieldUpdateActionInsertErrorMessage(this, "Error! Document Variable not defined.");

        Document doc = fetchDocument();
        String result = doc.getVariables().get(getVariableName());

        if (msString.isNullOrEmpty(result))
        {
            // WORDSNET-18909 Insert error message if variable is empty or not found
            // and there is any not empty variable.
            if (isAnyNotEmpty(doc.getVariables()))
                return new FieldUpdateActionInsertErrorMessage(this, "Error! No document variable supplied.");

            result = "";
        }

        return new FieldUpdateActionApplyResult(this, result);
    }

    private static boolean isAnyNotEmpty(VariableCollection variables)
    {
        if (variables.getCount() == 0)
            return false;

        for (Map.Entry<String, String> variable : variables)
        {
            if (!msString.isNullOrEmpty(variable.getValue()))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Gets or sets the name of the document variable to retrieve.
    /// </summary>
    public String getVariableName() { return getFieldCodeCache().getArgumentAsString(VARIABLE_NAME_ARGUMENT_INDEX); }
    public void setVariableName(String value) throws Exception { getFieldCodeCache().setArgument(VARIABLE_NAME_ARGUMENT_INDEX, value); }

    private static final int VARIABLE_NAME_ARGUMENT_INDEX = 0;
}

