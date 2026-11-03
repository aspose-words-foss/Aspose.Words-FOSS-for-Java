// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2018 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringUtil;
import com.aspose.UriUtil;
import com.aspose.ms.System.Convert;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;


/// <summary>
/// Writes fields.
/// </summary>
class DocxFieldsWriter extends NrxFieldsWriter
{
    DocxFieldsWriter(DocxDocumentWriterBase writer, DocxRunWriter runWriter)
    {
        mWriter = writer;
        mRunWriter = runWriter;
    }

    /// <summary>
    /// Writes hyperlink field start. Currently some hyperlinks except for inline shapes are written as complex fields,
    /// which is ok for MS Word.
    /// </summary>
    protected /*override*/ void writeHyperlinkStart(
        String dest,
        String bookmark,
        String target,
        String screenTip,
        String docLocation,
        boolean history)
    {
        getBuilder().startElement("w:hyperlink");

        if (StringUtil.hasChars(dest))
            getBuilder().writeAttribute("r:id", mWriter.addHyperlinkRelationship(dest));

        getBuilder().writeAttribute("w:anchor", bookmark);
        getBuilder().writeAttribute("w:tgtFrame", target);
        getBuilder().writeAttribute("w:tooltip", screenTip);
        getBuilder().writeAttribute("w:docLocation", docLocation);
        getBuilder().writeAttributeIfTrue("w:history", history);
    }

    /// <summary>
    /// Closes the 'w:hyperlink' element. Symmetrical to WriteHyperlinkStart.
    /// </summary>
    /*override*/ void writeHyperlinkEnd()
    {
        mWriter.getBuilder().endElement("w:hyperlink");
    }

    /// <summary>
    /// Returns a clone of the shape with the attributes, which stores hyperlink.
    /// </summary>
    Shape getHyperlinkShape(Shape shape)
    {
        Shape hyperlinkShape = (Shape)shape.deepClone(true);

        FieldCodeHyperlink fieldCode = FieldCodeHyperlink.parse(getTopFieldChar().getField().getFieldCode());

        // The hyperlinked node is a shape, the hyperlink is stored as attributes of the shape.
        hyperlinkShape.setShapeAttrInternal(ShapeAttr.HYPERLINK_ADDRESS, UriUtil.appendSubAddress(fieldCode.getAddress(), fieldCode.getSubAddress()));

        if (StringUtil.hasChars(fieldCode.getTarget()))
            hyperlinkShape.setShapeAttrInternal(ShapeAttr.HYPERLINK_TARGET, fieldCode.getTarget());

        if (StringUtil.hasChars(fieldCode.getScreenTip()))
            hyperlinkShape.setShapeAttrInternal(ShapeAttr.SCREEN_TIP, fieldCode.getScreenTip());

        return hyperlinkShape;
    }

    protected /*override*/ void writeField(FieldChar fieldChar, String fldCharName) throws Exception
    {
        // MS Word does not seem to write rsids for field chars of some field types, so do we.
        boolean isWriteRsid = !FieldUtil.isDead(fieldChar.getFieldType());
        mRunWriter.writeRunStart(fieldChar.getRunPr(), fieldChar, isWriteRsid);

        getBuilder().writeElementWithAttributesStart(
            "w:fldChar",
            "w:fldCharType", fldCharName);

        if (fieldChar.getNodeType() == NodeType.FIELD_START)
        {
            getBuilder().writeAttributeIfTrue("w:dirty", fieldChar.isDirty());
            getBuilder().writeAttributeIfTrue("w:fldLock", fieldChar.isLocked());
        }

        writeFfDataIfNeeded(fieldChar);
        writeFieldDataIfNeeded(fieldChar);
        writeNumberingChangeIfNeeded(fieldChar);

        getBuilder().endElement("w:fldChar");
        mRunWriter.writeRunEnd(fieldChar.getRunPr());
    }

    /// <summary>
    /// Word has strange behavior. When more cases will be collected, this solution will be reworked.
    /// </summary>
    /// <remarks>
    /// AM. It seems I got logic, at least part of it. For unknown reason Word does not write hyperlink field
    /// as hyperlink tag if any bookmark node are adjacent to parent paragraph.
    ///
    /// This is the reason why logic misunderstood before, Word writes _GoBack bookmark ar cursor position
    /// in order to preserve cursor position and that's why it looks like Word does not write hyperlink tag
    /// for the first field in document. To see this behavior move cursor to second line and save document.
    ///
    /// I do not understand this behavior reason, it could be some kind of resilience for certain MS Word version.
    /// </remarks>.
    protected /*override*/ boolean canWriteAsHyperlink(FieldChar fieldChar)
    {
        boolean canWriteAsHyperlink = super.canWriteAsHyperlink(fieldChar);
        Document doc = ms.as(fieldChar.getDocument(), Document.class);

        if ((doc == null) || !canWriteAsHyperlink)
        {
            return canWriteAsHyperlink;
        }

        // WORDSNET-23465 Word does not write hyperlink tag in case bookmark node adjacent to parent paragraph.
        Field field = fieldChar.getField();
        Paragraph parentPara = field.getStart().getParentParagraph();
        Node prevSibling = parentPara.getPreviousSibling();

        if ((prevSibling != null) && NodeUtil.isBookmarkNode(prevSibling))
            return false;

        return true;
    }

    /// <summary>
    /// Writes FfData for a form field start node.
    /// Safe to call for any field char any field type.
    /// </summary>
    private void writeFfDataIfNeeded(FieldChar fieldChar)
    {
        if (fieldChar.getNodeType() != NodeType.FIELD_START)
            return;

        if (!FieldUtil.isFormField(fieldChar.getFieldType()))
            return;

        FormField formField = ((FieldStart)fieldChar).getFormField();
        if (formField == null)
            return;

        // RK This code is a quick hack to make 6137 pass. In this document there is no field end and the
        // export-import-export gold will not match because when loading our document, the form field will not be created.
        // A proper fix will be to add field validation logic to document validator then this code can be removed.
        if (formField.getField() == null)
            return;

        getBuilder().startElement("w:ffData");
        getBuilder().writeValEvenIfEmpty("w:name", formField.getName());
        getBuilder().writeVal("w:enabled", formField.getEnabled());
        getBuilder().writeVal("w:calcOnExit", formField.getCalculateOnExit());
        getBuilder().writeVal("w:entryMacro", formField.getEntryMacro());
        getBuilder().writeVal("w:exitMacro", formField.getExitMacro());
        writeFormTextProperty("w:helpText", !formField.getOwnHelp(), formField.getHelpText());
        writeFormTextProperty("w:statusText", !formField.getOwnStatus(), formField.getStatusText());

        switch (fieldChar.getFieldType())
        {
            case FieldType.FIELD_FORM_TEXT_INPUT:
                writeTextInput(formField.getFormFieldPr());
                break;
            case FieldType.FIELD_FORM_CHECK_BOX:
                writeCheckBox(formField.getFormFieldPr());
                break;
            case FieldType.FIELD_FORM_DROP_DOWN:
                writeDropDown(formField.getFormFieldPr());
                break;
            default:
                throw new IllegalStateException("Unknown form field type.");
        }

        getBuilder().endElement("w:ffData");
    }

    private void writeTextInput(FormFieldPr formFieldPr)
    {
        getBuilder().startElement("w:textInput");
        getBuilder().writeVal("w:type", DocxEnum.textFormFieldTypeToDocx(formFieldPr.getTextInputType()));
        getBuilder().writeVal("w:default", formFieldPr.getTextInputDefault());

        // WORDSNET-18021 The value may be in the Int32 range.
        int textInputMaxLength = Convert.toInt32(formFieldPr.getTextInputMaxLength());
        textInputMaxLength = (textInputMaxLength > Short.MAX_VALUE) ? Short.MAX_VALUE : textInputMaxLength;
        getBuilder().writeValIfPositive("w:maxLength", textInputMaxLength);

        getBuilder().writeVal("w:format", formFieldPr.getTextInputFormat());
        getBuilder().endElement("w:textInput");
    }

    private void writeCheckBox(FormFieldPr formFieldPr)
    {
        getBuilder().startElement("w:checkBox");

        if (formFieldPr.getCheckBoxSizeAuto())
            getBuilder().writeEmptyElement("w:sizeAuto");
        else
            getBuilder().writeVal("w:size", FormatterPal.intToXml(formFieldPr.getCheckBoxSizeHalfPoints()));

        getBuilder().writeBoolValExplicit("w:default", formFieldPr.getCheckBoxDefault());

        if (formFieldPr.hasCheckBoxChecked())
            getBuilder().writeBoolValExplicit("w:checked", formFieldPr.getCheckBoxChecked());

        getBuilder().endElement("w:checkBox");
    }

    private void writeDropDown(FormFieldPr formFieldPr)
    {
        getBuilder().startElement("w:ddList");

        if (formFieldPr.getDropDownDefault() != 0)
            getBuilder().writeVal("w:default", formFieldPr.getDropDownDefault());

        if (formFieldPr.hasDropDownResult())
            getBuilder().writeVal("w:result", formFieldPr.getDropDownResult());

        for (String listEntry : formFieldPr.getDropDownItems())
        {
            // andrnosk: WORDSNET-8030 MS Word crashes when empty listEntry value appears.
            if (!StringUtil.hasChars(listEntry))
                mWriter.warn(WarningType.MINOR_FORMATTING_LOSS, "Empty value is not allowed inside DropDown ListEntry, and will be ignored");

            getBuilder().writeVal("w:listEntry", listEntry);
        }

        getBuilder().endElement("w:ddList");
    }

    private void writeFormTextProperty(String elementName, boolean isAuto, String value)
    {
        if (StringUtil.hasChars(value))
        {
            getBuilder().startElement(elementName);
            getBuilder().writeAttributeString("w:type", isAuto ? "autoText" : "text");
            getBuilder().writeAttributeString("w:val", value);
            getBuilder().endElement(elementName);
        }
    }

    private DocxBuilder getBuilder() { return mWriter.getCurrentBuilder(); }

    /// <summary>
    /// Writes <see cref="FieldStart.FieldData"/> for a field start node.
    /// Safe to call for any field char any field type.
    /// </summary>
    private void writeFieldDataIfNeeded(FieldChar fieldChar)
    {
        if (fieldChar.getNodeType() != NodeType.FIELD_START)
            return;

        byte[] fieldData = ((FieldStart)fieldChar).getFieldData();
        if (fieldData == null)
            return;

        // The fldData element was removed in ISO Strict.
        if (mWriter.getCompliance() == OoxmlComplianceCore.ISO_STRICT)
        {
            mWriter.warn(WarningType.DATA_LOSS, msString.format(WarningStrings.NOT_SUPPORTED_BY_ISO_STRICT, "fldData"));
            return;
        }

        getBuilder().startElement("w:fldData");
        getBuilder().writeAttributeString("xml:space", "preserve");
        getBuilder().writeBase64(fieldData, 0, fieldData.length);
        getBuilder().endElement(); //w:fldData
    }

    private void writeNumberingChangeIfNeeded(FieldChar fieldChar)
    {
        if (fieldChar.getNodeType() != NodeType.FIELD_END)
            return;

        FieldEnd fieldEnd = (FieldEnd)fieldChar;
        if (fieldEnd.getNumberRevision() == null)
            return;

        getBuilder().writeRevision(fieldEnd.getNumberRevision(), mWriter.getNextAnnotationId());
    }

    boolean isHyperlinkLastChild(Node lastChild)
    {
        FieldChar fieldChar = getTopFieldChar();
        if (fieldChar.getFieldType() != FieldType.FIELD_HYPERLINK)
            return false;

        FieldChar fieldEnd = fieldChar.getField().getEnd();
        return lastChild.getNextSibling() == fieldEnd;
    }

    private /*final*/ DocxDocumentWriterBase mWriter;
    private /*final*/ DocxRunWriter mRunWriter;
}

