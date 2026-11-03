// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableBool;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Provides  auto-updatable fields utility functions.
/// </summary>
/*static*/ class FieldsAutoUpdateUtils
{
	/* Simulation of static class by using private constructor */
	private FieldsAutoUpdateUtils()
	{}

    static boolean isFieldUpdatedBeforeSave(FieldUpdateContext context)
    {
        if (isFieldUpdatedBeforeSave(context.getField()))
            return true;

        // For now considers only fields in main story.
        if (context.getField().isInHeaderFooter())
            return false;

        ensureUpdateBeforeSaveState(context.getTopmostParentContextOrSelf());

        return context.isAutoUpdatableBeforeSave() == NullableBool.TRUE;
    }

    private static boolean isFieldUpdatedBeforeSave(Field field)
    {
        return field.isInHeaderFooter()
            ? isUpdatedBeforeSaveInHeader(field.getType())
            : isUpdatedBeforeSaveInBody(field);
    }

    private static void ensureUpdateBeforeSaveState(FieldUpdateContext context)
    {
        Debug.assertTrue(!context.hasParentContext());

        if (context.isAutoUpdatableBeforeSave() != NullableBool.NOT_DEFINED)
            return;

        // 0. Marks field and all its descendants as not auto-updatable.
        disableAutoUpdate(context);

        // 1. Marks certain field types (like REF) as auto-updatable as well as all theirs ancestors.
        ArrayList<FieldUpdateContext> autoUpdatedFields = extractAutoUpdatedFields(context);
        if (autoUpdatedFields.size() == 0)
            return;

        for (FieldUpdateContext field : autoUpdatedFields)
            enableAutoUpdate(field);

        // 2. Marks some nested IF fields in topmost IF field.
        enableIfFieldsAutoUpdate(context);
    }

    private static ArrayList<FieldUpdateContext> extractAutoUpdatedFields(FieldUpdateContext context)
    {
        ArrayList<FieldUpdateContext> result = new ArrayList<FieldUpdateContext>();

        for (FieldUpdateContext child : (Iterable<FieldUpdateContext>) context)
        {
            msArrayList.addRange(result, extractAutoUpdatedFields(child));

            if (isFieldUpdatedBeforeSave(child.getField()))
                msArrayList.add(result, child);
        }

        return result;
    }

    private static void enableIfFieldsAutoUpdate(FieldUpdateContext context)
    {
        if (context.getField().getType() != FieldType.FIELD_IF)
            return;

        // MS Word updates some nested IF fields (even if without auto-updatable child fields) in topmost IF field..
        // There are two kinds of nested IF fields:
        // - without own children fields in true and false result arguments (lets call them as "simple")
        // - with own children fields in true or false result arguments (lets call them as "complex")
        // MS Words stops to update nested IF fields at first "complex" field if it has any "simple" leading siblings.
        boolean isSimpleChildFound = false;
        for (FieldUpdateContext child : (Iterable<FieldUpdateContext>) context)
        {
            if (child.getField().getType() != FieldType.FIELD_IF)
                continue;

            boolean isSimpleChild = isSimpleIfField(child);
            if (isSimpleChildFound && !isSimpleChild)
                break;

            if (isSimpleChild)
                isSimpleChildFound = true;

            child.isAutoUpdatableBeforeSave(NullableBool.TRUE);
        }
    }

    private static boolean isSimpleIfField(FieldUpdateContext context)
    {
        Debug.assertTrue(context.getField().getType() == FieldType.FIELD_IF);

        FieldIf fieldIf = (FieldIf)context.getField();
        FieldArgument trueResultArgument = fieldIf.getTrueResultArgument();
        FieldArgument falseResultArgument = fieldIf.getFalseResultArgument();

        for (FieldUpdateContext child : (Iterable<FieldUpdateContext>) context)
        {
            if (child.isAutoUpdatableBeforeSave() == NullableBool.TRUE)
                continue;

            if (child.getParentArgument() == trueResultArgument)
                return false;

            if (child.getParentArgument() == falseResultArgument)
                return false;
        }

        return true;
    }

    private static void disableAutoUpdate(FieldUpdateContext context)
    {
        context.isAutoUpdatableBeforeSave(NullableBool.FALSE);
        for (FieldUpdateContext child : (Iterable<FieldUpdateContext>) context)
            disableAutoUpdate(child);
    }

    private static void enableAutoUpdate(FieldUpdateContext context)
    {
        context.isAutoUpdatableBeforeSave(NullableBool.TRUE);
        if (context.hasParentContext())
            enableAutoUpdate(context.getParentContext());
    }

    private static boolean isUpdatedBeforeSaveInHeader(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_ASK:
            case FieldType.FIELD_BARCODE:
            case FieldType.FIELD_DATA:
            case FieldType.FIELD_DATE:
            case FieldType.FIELD_DDE_AUTO:
            case FieldType.FIELD_DISPLAY_BARCODE:
            case FieldType.FIELD_MERGE_BARCODE:
            case FieldType.FIELD_EQUATION:
            case FieldType.FIELD_FORM_TEXT_INPUT:
            case FieldType.FIELD_FORMULA:
            case FieldType.FIELD_NOTE_REF:
            case FieldType.FIELD_NUM_PAGES:
            case FieldType.FIELD_PAGE:
            case FieldType.FIELD_PAGE_REF:
            case FieldType.FIELD_SAVE_DATE:
            case FieldType.FIELD_SECTION:
            case FieldType.FIELD_SECTION_PAGES:
            case FieldType.FIELD_SEQUENCE:
            case FieldType.FIELD_SET:
            case FieldType.FIELD_STYLE_REF:
            case FieldType.FIELD_TIME:
            case FieldType.FIELD_IF:
            case FieldType.FIELD_ADDIN:
            case FieldType.FIELD_AUTHOR:
            case FieldType.FIELD_AUTO_TEXT:
            case FieldType.FIELD_COMMENTS:
            case FieldType.FIELD_COMPARE:
            case FieldType.FIELD_CREATE_DATE:
            case FieldType.FIELD_DOC_PROPERTY:
            case FieldType.FIELD_DOC_VARIABLE:
            case FieldType.FIELD_EDIT_TIME:
            case FieldType.FIELD_FILE_NAME:
            case FieldType.FIELD_FILL_IN:
            case FieldType.FIELD_GLOSSARY:
            case FieldType.FIELD_GREETING_LINE:
            case FieldType.FIELD_INFO:
            case FieldType.FIELD_KEYWORD:
            case FieldType.FIELD_LAST_SAVED_BY:
            case FieldType.FIELD_NUM_CHARS:
            case FieldType.FIELD_NUM_WORDS:
            case FieldType.FIELD_PRINT_DATE:
            case FieldType.FIELD_QUOTE:
            case FieldType.FIELD_REF:
            case FieldType.FIELD_REVISION_NUM:
            case FieldType.FIELD_SHAPE:
            case FieldType.FIELD_SUBJECT:
            case FieldType.FIELD_TEMPLATE:
            case FieldType.FIELD_TITLE:
            case FieldType.FIELD_USER_ADDRESS:
            case FieldType.FIELD_USER_INITIALS:
            case FieldType.FIELD_USER_NAME:
                return true;
            default:
                return false;
        }
    }

    static boolean isUpdatedBeforeSaveInBody(Field field)
    {
        switch (field.getType())
        {
            case FieldType.FIELD_ASK:
            case FieldType.FIELD_BARCODE:
            case FieldType.FIELD_DATA:
            case FieldType.FIELD_DATE:
            case FieldType.FIELD_DDE_AUTO:
            case FieldType.FIELD_DISPLAY_BARCODE:
            case FieldType.FIELD_MERGE_BARCODE:
            case FieldType.FIELD_EQUATION:
            case FieldType.FIELD_SAVE_DATE:
            case FieldType.FIELD_SECTION:
            case FieldType.FIELD_SET:
            case FieldType.FIELD_TIME:
            case FieldType.FIELD_NOTE_REF:
            case FieldType.FIELD_NUM_PAGES:
            case FieldType.FIELD_PAGE:
            case FieldType.FIELD_PAGE_REF:
            case FieldType.FIELD_PRINT_DATE:
            case FieldType.FIELD_REF:
            case FieldType.FIELD_SECTION_PAGES:
            case FieldType.FIELD_SEQUENCE:
            case FieldType.FIELD_STYLE_REF:
                return true;
            case FieldType.FIELD_FORM_TEXT_INPUT:
                FormField formField = field.getStart().getFormField();
                if (formField == null)
                    return false;

                return isUpdatedBeforeSaveInBody(formField.getTextInputType());
            default:
                return false;
        }
    }

    private static boolean isUpdatedBeforeSaveInBody(/*TextFormFieldType*/int textInputType)
    {
        switch (textInputType)
        {
            case TextFormFieldType.CURRENT_DATE:
            case TextFormFieldType.CURRENT_TIME:
                return true;
            default:
                return false;
        }
    }
}

