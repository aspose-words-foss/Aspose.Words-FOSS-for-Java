// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.Debug;


/// <summary>
/// Responsible for importing HTML input elements as SDT controls in document.
/// </summary>
class HtmlControlAsSdtReader extends HtmlControlReader
{
    HtmlControlAsSdtReader(
        DocumentBuilder builder,
        DocumentFormatter documentFormatter)
    {
    	super(builder, documentFormatter);
	
        //Empty constructor.
    }

    protected /*override*/ void insertDateControl(
        String name,
        boolean enabled,
        String value,
        int maxLength) throws Exception
    {
        StructuredDocumentTag sdt = createSdtControlNode(name, SdtType.DATE, enabled);
        sdt.setDateDisplayFormat(FormatterPal.getShortDatePatternCurrent());
        sdt.setFullDateInternal(FormatterPal.tryParseDateTimeCurrent(value));
    }

    protected /*override*/ void insertCheckboxControl(
        String name,
        boolean disabled,
        boolean editable,
        boolean value) throws Exception
    {
        StructuredDocumentTag sdt = createSdtControlNode(name, SdtType.CHECKBOX, editable);
        sdt.setChecked(value);
    }

    protected /*override*/ void insertTextControl(
        String name,
        String value,
        boolean enabled,
        int maxLength,
        String placeholder) throws Exception
    {
        boolean valueExists = StringUtil.hasChars(value);
        boolean placeholderExists = StringUtil.hasChars(placeholder);

        StructuredDocumentTag sdt = createSdt(name, SdtType.PLAIN_TEXT, enabled);
        sdt.setPlaceholderName((placeholderExists)
            ? getBuilder().getDocument().getSdtPlaceholderManager().createCustomPlaceholder(placeholder)
            : getDefaultPlaceholderName(SdtType.PLAIN_TEXT));

        Debug.assertTrue(sdt.isShowingPlaceholderText());
        Debug.assertTrue(sdt.getFirstChild().getNodeType() == NodeType.RUN);
        int placeholderStyle = ((Run)sdt.getFirstChild()).getRunPr().getIstd();
        sdt.getChildNodes(NodeType.ANY, false).clear();

        Run run = new Run(getBuilder().getDocument(), (valueExists) ? value : placeholder);
        getFormatter().toFont(run.getFont(), getBuilder().getCurrentParagraph().getParagraphStyle());

        if (!valueExists && placeholderExists)
            run.getRunPr().setIstd(placeholderStyle);

        sdt.appendChild(run);
        sdt.isShowingPlaceholderText(!valueExists);
    }

    protected /*override*/ void insertDropDownControl(
        String name,
        boolean enabled,
        Iterable<HtmlOptionElementInfo> items) throws Exception
    {
        StructuredDocumentTag dropDown = createSdtControlNode(name, SdtType.DROP_DOWN_LIST, enabled);
        for (HtmlOptionElementInfo htmlOptionInfo : items)
            addItemToSdtDropDown(dropDown, htmlOptionInfo.getText(), htmlOptionInfo.getValue(), htmlOptionInfo.isSelected());
    }

    private StructuredDocumentTag createSdtControlNode(
        String name,
        /*SdtType*/int sdtType,
        boolean enabled) throws Exception
    {
        StructuredDocumentTag sdt = createSdt(name, sdtType, enabled);
        if (sdt.isShowingPlaceholderText())
            sdt.setPlaceholderName(getDefaultPlaceholderName(sdtType));
        return sdt;
    }

    private StructuredDocumentTag createSdt(
        String name,
        /*SdtType*/int sdtType,
        boolean enabled) throws Exception
    {
        StructuredDocumentTag sdt = new StructuredDocumentTag(getBuilder().getDocument(), sdtType, MarkupLevel.INLINE);
        sdt.setLockContents(!enabled);
        if (StringUtil.hasChars(name))
        {
            sdt.setTitle(name);
            sdt.setTag(name);
        }

        getFormatter().toFont(sdt.getContentsFont(), getBuilder().getCurrentParagraph().getParagraphStyle());
        getBuilder().insertNode(sdt);
        return sdt;
    }

    private String getDefaultPlaceholderName(/*SdtType*/int sdtType)
    {
        return getBuilder().getDocument().getSdtPlaceholderManager().fetchPlaceholderByType(sdtType).getName();
    }

    private static void addItemToSdtDropDown(
        StructuredDocumentTag dropDown,
        String displayText,
        String value,
        boolean isSelected)
    {
        if (!StringUtil.hasChars(value) && !StringUtil.hasChars(displayText))
            return;

        if (!StringUtil.hasChars(value))
            value = displayText;

        SdtListItem listItem = StringUtil.hasChars(displayText)
            ? new SdtListItem(displayText, value)
            : new SdtListItem(value);

        dropDown.getListItems().add(listItem);
        if (isSelected || (dropDown.getListItems().getCount() == 1))
            dropDown.getListItems().setSelectedValue(listItem);
    }
}

