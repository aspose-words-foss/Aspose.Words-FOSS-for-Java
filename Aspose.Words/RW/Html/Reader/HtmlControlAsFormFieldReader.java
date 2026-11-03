// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Responsible for importing HTML input elements as Form Fields in document.
/// </summary>
class HtmlControlAsFormFieldReader extends HtmlControlReader
{
    HtmlControlAsFormFieldReader(
        DocumentBuilder builder,
        DocumentFormatter documentFormatter)
    {
    	super(builder, documentFormatter);
	
        // Empty constructor.
    }

    protected /*override*/ void insertDateControl(
        String name,
        boolean editable,
        String value,
        int maxLength) throws Exception
    {
        insertAsText(name, value, editable, maxLength, TextFormFieldType.DATE);
    }

    protected /*override*/ void insertCheckboxControl(
        String name,
        boolean disabled,
        boolean editable,
        boolean isChecked) throws Exception
    {
        FormField checkBox = getBuilder().insertCheckBox(name, isChecked, MathUtil.doubleToInt(getBuilder().getFont().getSize()));
        checkBox.setEnabled(!disabled);
    }

    protected /*override*/ void insertTextControl(
        String name,
        String value,
        boolean enabled,
        int maxLength,
        String placeholder) throws Exception
    {
        insertAsText(name, value, enabled, maxLength, TextFormFieldType.REGULAR);
    }

    protected /*override*/ void insertDropDownControl(
        String name,
        boolean enabled,
        Iterable<HtmlOptionElementInfo> items) throws Exception
    {
        FormField formField = createFormFieldDropDown(name);
        for (HtmlOptionElementInfo htmlOptionInfo : items)
            addItemToFormFieldDropDown(formField, htmlOptionInfo.getText(), htmlOptionInfo.isSelected());
    }

    private void insertAsText(
        String name,
        String value,
        boolean enabled,
        int maxLength,
        /*TextFormFieldType*/int textType) throws Exception
    {
        // Password char isn't displayed as "*"
        getFormatter().toFont(getBuilder().getFont(), getBuilder().getCurrentParagraph().getParagraphStyle());
        FormField textInput = getBuilder().insertTextInput(name, textType, "", value, maxLength);
        textInput.setEnabled(enabled);
        if (!StringUtil.hasChars(value))
            textInput.getField().setResult(FormField.DEFAULT_TEXT_INPUT_VALUE);
    }

    private FormField createFormFieldDropDown(String name) throws Exception
    {
        getFormatter().toFont(getBuilder().getFont(), getBuilder().getCurrentParagraph().getParagraphStyle());
        FormField formFieldDropDown = getBuilder().insertComboBox(name, new String[] { "" }, 0);
        getFormatter().toFont(formFieldDropDown.getFont(), getBuilder().getCurrentParagraph().getParagraphStyle());
        formFieldDropDown.getDropDownItems().clear();
        formFieldDropDown.setEnabled(true);
        return formFieldDropDown;
    }

    private void addItemToFormFieldDropDown(
        FormField formField,
        String innerText,
        boolean isSelectedItem)
    {
        if (formField.getDropDownItems().getCount() < DropDownItemCollection.MAX_ITEMS_COUNT)
        {
            formField.getDropDownItems().add(innerText);
        }
        else
        {
            String lostItemText = innerText;

            if (isSelectedItem)
            {
                lostItemText = formField.getDropDownItems().get(DropDownItemCollection.MAX_ITEMS_COUNT - 1);
                formField.getDropDownItems().set(DropDownItemCollection.MAX_ITEMS_COUNT - 1, innerText);
            }
            warn(WarningType.DATA_LOSS,
                msString.format("Item \"{0}\" has been lost while adding DropDown elements.", lostItemText));
        }

        if (isSelectedItem)
            formField.setDropDownSelectedIndex(formField.getDropDownItems().getCount() - 1);
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int warningType, String description)
    {
        if (getBuilder().getDocument().getWarningCallback() != null)
            getBuilder().getDocument().getWarningCallback().warning(new WarningInfo(warningType, WarningSource.HTML, description));
    }
}

