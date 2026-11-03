// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/10/2012 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Represents utility methods to work with the fields which prompt the user to respond on update
/// such as ASK and FILLIN.
/// </summary>
/*static*/ class FieldUserPromptingUtil
{
	/* Simulation of static class by using private constructor */
	private FieldUserPromptingUtil()
	{}

    /// <summary>
    /// Returns the field update action for the given field.
    /// </summary>
    /// <param name="field"></param>
    /// <returns></returns>
    static FieldUpdateAction getFieldUpdateAction(Field field)
    {
        Document document = field.fetchDocument();
        IFieldUserPromptRespondent respondent = document.getFieldOptions().getUserPromptRespondent();
        if (respondent == null)
        {
            // If respondent is not specified, do not update the field for the backward compatibility.
            return new FieldUpdateActionDoNothing(field);
        }

        // Prompt the user to respond.
        String response = respondent.respond(getPromptText(field), getDefaultResponse(field));
        return getFieldUpdateAction(field, response);
    }

    private static FieldUpdateAction getFieldUpdateAction(Field field, String response)
    {
        // MS Word does not update the field in case when user skips the prompt
        // (i.e. presses "Cancel" button in prompt window).
        if (response == null)
            return new FieldUpdateActionDoNothing(field);

        switch (field.getType())
        {
            case FieldType.FIELD_ASK:
                return new FieldUpdateActionSetBookmarkValue(field, ((FieldAsk)field).getBookmarkName(), response);
            case FieldType.FIELD_FILL_IN:
                return new FieldUpdateActionApplyResult(field, response);
            default:
                throw createWrongFieldTypeException();
        }
    }

    private static IllegalStateException createWrongFieldTypeException()
    {
        return new IllegalStateException("Wrong field type for this operation.");
    }

    /// <summary>
    /// Returns the promt text (i.e. the title of the prompt window) for the given field.
    /// </summary>
    /// <param name="field"></param>
    /// <returns></returns>
    static String getPromptText(Field field)
    {
        return field.getFieldCodeCache().getArgumentAsString(getPromptTextArgumentIndex(field));
    }

    static void setPromptText(Field field, String text) throws Exception
    {
        field.getFieldCodeCache().setArgument(getPromptTextArgumentIndex(field), text);
    }

    private static int getPromptTextArgumentIndex(Field field)
    {
        switch (field.getType())
        {
            case FieldType.FIELD_ASK:
                return 1;
            case FieldType.FIELD_FILL_IN:
                return 0;
            default:
                throw createWrongFieldTypeException();
        }
    }

    /// <summary>
    /// Returns the value, indicating whether the user response should be recieved once per a mail merge operation
    /// for the given field.
    /// </summary>
    static boolean getPromptOnceOnMailMerge(Field field)
    {
        return field.getFieldCodeCache().hasSwitch(PROMPT_ONCE_ON_MAIL_MERGE_SWITCH);
    }

    static void setPromptOnceOnMailMerge(Field field, boolean value) throws Exception
    {
        field.getFieldCodeCache().setSwitch(PROMPT_ONCE_ON_MAIL_MERGE_SWITCH, value);
    }

    /// <summary>
    /// Returns default user response (i.e. initial value contained in the prompt window) for the given field.
    /// </summary>
    /// <param name="field"></param>
    /// <returns></returns>
    static String getDefaultResponse(Field field)
    {
        return field.getFieldCodeCache().getSwitchArgumentAsString(DEFAULT_RESPONSE_SWITCH);
    }

    static void setDefaultResponse(Field field, String value) throws Exception
    {
        field.getFieldCodeCache().setSwitch(DEFAULT_RESPONSE_SWITCH, value);
    }

    /// <summary>
    /// Gets the switch type by the switch name in the context of the fields which prompt the user to respond on update.
    /// </summary>
    /// <param name="switchName"></param>
    /// <returns></returns>
    static /*FieldSwitchType*/int getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*PROMPT_ONCE_ON_MAIL_MERGE_SWITCH*/0:
                return FieldSwitchType.FLAG;
            case /*DEFAULT_RESPONSE_SWITCH*/1:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final String PROMPT_ONCE_ON_MAIL_MERGE_SWITCH = "\\o";
    private static final String DEFAULT_RESPONSE_SWITCH = "\\d";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\o",
		"\\d"
	);

}

