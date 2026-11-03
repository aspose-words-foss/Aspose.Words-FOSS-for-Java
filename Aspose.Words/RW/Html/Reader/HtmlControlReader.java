// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Base class responsible for importing HTML 'input' and 'select' elements into corresponding controls in document.
/// </summary>
abstract class HtmlControlReader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    protected HtmlControlReader(DocumentBuilder builder, DocumentFormatter documentFormatter)
    {
        Debug.assertTrue(builder != null);
        Debug.assertTrue(documentFormatter != null);

        mBuilder = builder;
        mDocumentFormatter = documentFormatter;
    }

    void handleInput(HtmlElementNode node, String type) throws Exception
    {
        String name = node.getAttributes().getAttributeValue("name", "");
        String value = node.getAttributes().getAttributeValue("value", "");
        int maxLength = node.getAttributes().getAttributeValue("maxlength", 0);
        boolean disabled = node.getAttributes().get("disabled") != null;
        boolean readOnly = node.getAttributes().get("readonly") != null;
        boolean editable = !disabled && !readOnly;

        switch (gStringSwitchMap.of(type))
        {
            case /*"checkbox"*/0:
            {
                boolean isChecked = node.getAttributes().get("checked") != null;
                insertCheckboxControl(name, disabled, editable, isChecked);
                break;
            }
            case /*"date"*/1:
            {
                insertDateControl(name, editable, value, maxLength);
                break;
            }
            case /*"radio"*/2:
            {
                insertRadioButton(node);
                break;
            }
            case /*"reset"*/3:
            {
                mDocumentFormatter.toFont(getBuilder().getFont(), getBuilder().getCurrentParagraph().getParagraphStyle());
                getBuilder().insertField("MACROBUTTON ResetFormField [" + value + "]");
                break;
            }
            case /*"submit"*/4:
            case /*"button"*/5:
            {
                mDocumentFormatter.toFont(getBuilder().getFont(), getBuilder().getCurrentParagraph().getParagraphStyle());
                getBuilder().insertField("MACROBUTTON DoFieldClick  [" + value + "]");
                break;
            }
            case /*"hidden"*/6: //unusable
            {
                break;
            }
            default:
            {
                // WORDSNET-21408 Take into account a placeholder attribute of an input HTML element.
                String placeholder = node.getAttributes().getAttributeValue("placeholder", "");
                insertTextControl(name, value, editable, maxLength, placeholder);
                break;
            }
        }
    }

    void handleSelect(HtmlElementNode node, ArrayList<HtmlOptionElementInfo> items) throws Exception
    {
        String name = node.getAttributes().getAttributeValue("name", "dropdown");
        boolean disabled = node.getAttributes().get("disabled") != null;
        boolean readOnly = node.getAttributes().get("readonly") != null;

        insertDropDownControl(name, !disabled && !readOnly, items);
    }

    protected abstract void insertDateControl(String name, boolean editable, String value, int maxLength) throws Exception;

    protected abstract void insertCheckboxControl(String name, boolean disabled, boolean editable, boolean isChecked) throws Exception;

    protected abstract void insertTextControl(String name, String value, boolean enabled, int maxLength, String placeholder) throws Exception;

    protected abstract void insertDropDownControl(String name, boolean enabled, Iterable<HtmlOptionElementInfo> items) throws Exception;

    protected DocumentBuilder getBuilder() { return mBuilder; }

    protected DocumentFormatter getFormatter() { return mDocumentFormatter; }

    private void insertRadioButton(HtmlElementNode node) throws Exception
    {
        // WORDSNET-14401 Radio buttons were not shown as 'selected' or 'checked' when converting to fixed html.
        boolean isChecked = node.getAttributes().get("checked") != null;
        byte[] imageBytes = getRadioButtonImage(getBuilder().getDocument(), isChecked);

        HtmlElementNode inputNode = new HtmlElementNode("input");
        inputNode.getAttributes().add(new HtmlAttribute("type", "radio"));
        String[] supportedAttributes = new String[] { "name", "disabled", "checked", "value", "required", "form", "alt" };
        for (String attrName : supportedAttributes)
        {
            HtmlAttribute attribute = node.getAttributes().get(attrName);
            if (attribute != null)
            {
                // MS Word "understands" only empty 'checked' or 'checked=true' attributes. We can't generate 
                // empty attributes because use SerializeToXml() method (XML doesn't support empty attributes),
                // so we use 'checked=true' to check on the radio button control.
                if ("checked".equals(attrName))
                {
                    attribute = new HtmlAttribute("checked", "true");
                }

                inputNode.getAttributes().add(attribute);
            }
        }

        HtmlOleControl oleControl = new HtmlOleControl(msString.format("DefOcxName{0}", ++mHtmlOleControlCounter),
            HtmlOleControlType.OPTION, inputNode.serializeToXml());
        Stream memoryStream = new MemoryStream(imageBytes);
        getBuilder().insertHtmlOleControl(oleControl, memoryStream);
    }

    private byte[] getRadioButtonImage(Document doc, boolean isChecked)
    {
        if (isChecked)
        {
            if (mCheckedRadioButtonImage == null)
                mCheckedRadioButtonImage = HtmlImageUtil.createRadioButtonImage(doc, true);
            return mCheckedRadioButtonImage;
        }
        else
        {
            if (mUncheckedRadioButtonImage == null)
                mUncheckedRadioButtonImage = HtmlImageUtil.createRadioButtonImage(doc, false);
            return mUncheckedRadioButtonImage;
        }
    }

    /// <summary>
    /// Cached image for checked radio button.
    /// </summary>
    private byte[] mCheckedRadioButtonImage;
    /// <summary>
    /// Cached image for unchecked radio button.
    /// </summary>
    private byte[] mUncheckedRadioButtonImage;

    private /*final*/ DocumentBuilder mBuilder;
    private /*final*/ DocumentFormatter mDocumentFormatter;
    private int mHtmlOleControlCounter;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"checkbox",
		"date",
		"radio",
		"reset",
		"submit",
		"button",
		"hidden"
	);

}

