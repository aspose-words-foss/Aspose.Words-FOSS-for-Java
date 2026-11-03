// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2010 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.DateTime;
import com.aspose.FormatterPal;
import com.aspose.LocaleConverter;


/// <summary>
/// Knows how to write <see cref="StructuredDocumentTag"/> instances.
/// </summary>
/*static*/ class DocxSdtWriter
{
	/* Simulation of static class by using private constructor */
	private DocxSdtWriter()
	{}

    /// <summary>
    /// Begin write of Sdt.
    /// </summary>
    static void writeStart(StructuredDocumentTag sdt, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        int revisionId = writeRevisionStart(sdt, writer);

        builder.startElement("w:sdt");

        writeSdtPr(sdt, writer);
        writeSdtEndPr(sdt, writer);

        builder.startElement("w:sdtContent");

        writeRevisionEnd(sdt, builder, revisionId);
    }

    /// <summary>
    /// end write of sdt.
    /// </summary>
    static void writeEnd(StructuredDocumentTag sdt, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        int revisionId = writeRevisionStart(sdt, writer);

        builder.endElement(); // w:sdtContent
        builder.endElement();   // w:sdt

        writeRevisionEnd(sdt, builder, revisionId);
    }

    private static void writeSdtPr(StructuredDocumentTag sdt, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIso29500Up = (writer.getCompliance() != OoxmlComplianceCore.ECMA_376);

        builder.startElement("w:sdtPr");

        if (sdt.getContentsRunPr().getCount() != 0)
            DocxRunPrWriter.getInstance().writeForNodes(sdt.getContentsRunPr(), null, false, writer);

        builder.writeVal("w:alias", sdt.getTitle());
        builder.writeVal("w:tag", sdt.getTag());
        builder.writeVal("w:id", sdt.getId());

        /*SdtLockType*/int lockType = getLockTypeFromBoolLockValues(sdt.getLockContentControl(), sdt.getLockContents());
        if (lockType != SdtLockType.UNLOCKED)
            builder.writeVal("w:lock", DocxEnum.sdtLockTypeToDocx(lockType));

        writeOptionalPlaceholder(builder, sdt);

        boolean forceShowingPlaceholder = (sdt.getSdtType() == SdtType.PLAIN_TEXT) && writer.getSaveInfo().ForceShowingPlaceholder.contains(sdt);
        if (sdt.isShowingPlaceholderText() || forceShowingPlaceholder)
            builder.writeEmptyElement("w:showingPlcHdr");

        writeOptionalDataBinding(builder, sdt, writer.getCompliance());

        writeSdtControlProperties(builder, sdt, isIso29500Up);

        if (sdt.isLabelDefined())
            builder.writeVal("w:label", sdt.getLabel());

        if (sdt.getTabIndex() != 0)
            builder.writeVal("tabIndex", sdt.getTabIndex());

        if (sdt.isTemporary())
            builder.writeEmptyElement("w:temporary");

        if (isIso29500Up)
        {
            writeColor(builder, sdt);

            if (sdt.getAppearance() != SdtAppearance.DEFAULT)
            {
                builder.writeElementWithAttributes("w15:appearance",
                    "w15:val", DocxEnum.sdtAppearanceToDocx(sdt.getAppearance()));
            }

            writeWebExtensionRelationship(sdt, builder);
        }

        builder.endElement();
    }

    /// <summary>
    /// Writes the 2.5.1.12 webExtensionCreated and 2.5.1.13 webExtensionLinked [MS-DOCX] extensions
    /// of the 'sdtPr' element.
    /// </summary>
    private static void writeWebExtensionRelationship(StructuredDocumentTag sdt, DocxBuilder builder)
    {
        switch (sdt.getWebExtensionRelationship())
        {
            case SdtWebExtensionRelationship.CREATED_BY_WEB_EXTENSION:
                builder.writeEmptyElement("w15:webExtensionCreated");
                break;
            case SdtWebExtensionRelationship.LINKED_TO_WEB_EXTENSION:
                builder.writeEmptyElement("w15:webExtensionLinked");
                break;
            case SdtWebExtensionRelationship.NONE:
                break;
            default:
                throw new IllegalStateException(msString.format("Unexpected WebExtensionRelationship value {0}.",
                    sdt.getWebExtensionRelationship()));
        }
    }

    /// <summary>
    /// Writes the 'color' Office2013 extension of the 'sdtPr' element.
    /// </summary>
    private static void writeColor(DocxBuilder builder, StructuredDocumentTag sdt)
    {
        if ((sdt.getBaseColor() != null) || (sdt.getThemeColor() != null))
            builder.writeElementWithAttributes(
                "w15:color",
                "w:val", (sdt.getBaseColor() != null) ? NrxXmlUtil.colorToXml(sdt.getBaseColor()) : null,
                "w:themeColor", sdt.getThemeColor(),
                "w:themeShade", sdt.getThemeShade(),
                "w:themeTint", sdt.getThemeTint());
    }

    private static void writeOptionalPlaceholder(DocxBuilder builder, StructuredDocumentTag sdt)
    {
        if ((sdt.getPlaceholder() != null) && StringUtil.hasChars(sdt.getPlaceholderName()))
        {
            builder.startElement("w:placeholder");
            builder.writeVal("w:docPart", sdt.getPlaceholderName());
            builder.endElement();
        }
    }

    private static void writeOptionalDataBinding(DocxBuilder builder, StructuredDocumentTag sdt,
        /*OoxmlComplianceCore*/int compliance)
    {
        if (!sdt.getXmlMapping().isEmpty())
        {
            if (sdt.getXmlMapping().isDocx15Extension())
            {
                if (compliance != OoxmlComplianceCore.ECMA_376)
                {
                    builder.startElement("w15:dataBinding");
                }
                else
                {
                    builder.warn(WarningType.DATA_LOSS, WarningStrings.ECMA_376_NOT_SUPPORTED_XML_MAPPING);
                    return;
                }
            }
            else
            {
                builder.startElement("w:dataBinding");
            }

            builder.writeAttribute("w:prefixMappings", sdt.getXmlMapping().getPrefixMappings());
            builder.writeAttribute("w:xpath", sdt.getXmlMapping().getXPath());
            builder.writeAttribute("w:storeItemID", sdt.getXmlMapping().getStoreItemId());
            builder.writeAttribute("w16sdtdh:storeItemChecksum", sdt.getXmlMapping().getStoreItemChecksum());
            builder.endElement();
        }
    }

    private static void writeSdtRepeatingSection(SdtRepeatingSection sdtRepeatingSection, DocxBuilder builder)
    {
        builder.startElement("w15:repeatingSection");

        builder.writeElementWithAttributes("w15:sectionTitle", "w:val", sdtRepeatingSection.getSectionTitle());
        if (sdtRepeatingSection.getDoNotAllowInsertDeleteSection())
            builder.writeElementWithAttributes("w15:doNotAllowInsertDeleteSection", "w:val", 1);

        builder.endElement();
    }

    private static void writeSdtControlProperties(DocxBuilder builder, StructuredDocumentTag sdt, boolean isIso29500Up)
    {
        SdtControlProperties sdtControlProperties = sdt.getControlProperties();

        switch (sdtControlProperties.getType())
        {
            case SdtType.BIBLIOGRAPHY:
                builder.writeEmptyElement("w:bibliography");
                break;
            case SdtType.CITATION:
                builder.writeEmptyElement("w:citation");
                break;
            case SdtType.EQUATION:
                builder.writeEmptyElement("w:equation");
                break;
            case SdtType.DROP_DOWN_LIST:
            case SdtType.COMBO_BOX:
                writeDropDownListBase((sdtControlProperties.getType() == SdtType.DROP_DOWN_LIST) ? "w:dropDownList" : "w:comboBox",
                    ((SdtDropDownListBase)sdtControlProperties).getListItems(), builder);
                break;
            case SdtType.DATE:
                writeDate((SdtDate)sdtControlProperties, builder);
                break;
            case SdtType.BUILDING_BLOCK_GALLERY:
                writeDocPart("w:docPartList", (SdtDocPart)sdtControlProperties, builder);
                break;
            case SdtType.DOC_PART_OBJ:
                writeDocPart("w:docPartObj", (SdtDocPart)sdtControlProperties, builder);
                break;
            case SdtType.GROUP:
                builder.writeEmptyElement("w:group");
                break;
            case SdtType.PICTURE:
                builder.writeEmptyElement("w:picture");
                break;
            case SdtType.RICH_TEXT:
            case SdtType.PLAIN_TEXT:
            {
                SdtText text = (SdtText)sdtControlProperties;
                writeText(builder, text.getType() == SdtType.RICH_TEXT, text.isMultiline());
                break;
            }
            case SdtType.CHECKBOX:
                writeCheckbox((SdtCheckBox)sdtControlProperties, builder);
                break;
            case SdtType.REPEATING_SECTION:
            {
                // Do not write DOCX extensions elements if OoxmlCompliance.Ecma376_2006 is specified.
                if ((sdt.getControlProperties().getType() == SdtType.REPEATING_SECTION) && isIso29500Up)
                    writeSdtRepeatingSection((SdtRepeatingSection)sdt.getControlProperties(), builder);

                break;
            }
            case SdtType.REPEATING_SECTION_ITEM:
            {
                // Do not write DOCX extensions elements if OoxmlCompliance.Ecma376_2006 is specified.
                if (isIso29500Up)
                    builder.writeEmptyElement("w15:repeatingSectionItem");
                break;
            }
            case SdtType.ENTITY_PICKER:
            {
                // Do not write DOCX extensions elements if OoxmlCompliance.Ecma376_2006 is specified.
                if (isIso29500Up)
                    builder.writeEmptyElement("w14:entityPicker");
                break;
            }
            case SdtType.NONE:
                // Do not write anything.sdt type unspecified.
                break;
            default:
                throw new IllegalStateException("Please report exception.");
        }
    }

    /// <summary>
    /// Writes Office2010 checkbox control properties.
    /// </summary>
    private static void writeCheckbox(SdtCheckBox checkbox, DocxBuilder builder)
    {
        builder.startElement("w14:checkbox");

        builder.writeElementWithAttributes("w14:checked", "w14:val", checkbox.getChecked() ? 1 : 0);
        builder.writeElementWithAttributes("w14:checkedState",
            "w14:val", msString.format("{0:x4}", checkbox.getCheckedStateInfo().getCharacterCode()),
            "w14:font", checkbox.getCheckedStateInfo().getFontName());
        builder.writeElementWithAttributes("w14:uncheckedState",
            "w14:val", msString.format("{0:x4}", checkbox.getUncheckedStateInfo().getCharacterCode()),
            "w14:font", checkbox.getUncheckedStateInfo().getFontName());

        builder.endElement();
    }

    private static void writeText(DocxBuilder builder, boolean isRichtext, boolean isMultiline)
    {
        builder.startElement((isRichtext) ? "w:richText" : "w:text");
        builder.writeAttributeIfTrue("w:multiLine", isMultiline);
        builder.endElement();
    }

    private static void writeDropDownListBase(String tagName, SdtListItemCollection li, DocxBuilder builder)
    {
        builder.startElement(tagName);
        if (li.getSelectedValue() != null)
            builder.writeAttribute("w:lastValue", li.getSelectedValue().getValue());
        else if (StringUtil.hasChars(li.getLastItemValue()))
            builder.writeAttribute("w:lastValue", li.getLastItemValue());

        for (int itemIdx = 0; itemIdx < li.getCount(); itemIdx++)
        {
            builder.startElement("w:listItem");

            SdtListItem item = li.get(itemIdx);

            builder.writeAttributeString("w:value", item.getValue());

            // WORDSNET-4693 OOXML Spec says that if displayText is omitted, it is equal to w:val,
            // so we used to skip writing of displayText in case if item.DisplayText == item.Value
            //  But Word does not allow editing of such combo/listboxes. So we now always write displayText.
            builder.writeAttribute("w:displayText", item.getDisplayText());

            builder.endElement();
        }

        builder.endElement();
    }

    private static void writeDate(SdtDate date, DocxBuilder builder)
    {
        builder.startElement("w:date");
        if (!DateTime.equals(date.getFullDate(), DateTime.MinValue))
            builder.writeAttribute("w:fullDate", FormatterPal.dateTimeToXmlUtc(date.getFullDate()));

        builder.writeVal("w:dateFormat", date.getDateFormat());

        if (date.getLid() != SdtDate.EMPTY_LID)
            builder.writeVal("w:lid", LocaleConverter.localeToDocxTag(date.getLid()));

        builder.writeVal("w:storeMappedDataAs", DocxEnum.sdtDateStorageFormatToDocx(date.getStoreMappedDataAs()));

        // RK Work seems to always write this, even if default. So do we.
        builder.writeVal("w:calendar", DocxEnum.calendarTypeToDocx(date.getCalendarType()));

        builder.endElement();
    }

    private static void writeDocPart(String tagName, SdtDocPart docPart, DocxBuilder builder)
    {
        builder.startElement(tagName);
        builder.writeVal("w:docPartCategory", docPart.getBuildingBlockCategory());
        builder.writeVal("w:docPartGallery", docPart.getBuildingBlockType());

        if (docPart.isUnique())
            builder.writeEmptyElement("w:docPartUnique");

        builder.endElement();
    }

    private static void writeSdtEndPr(StructuredDocumentTag sdt, DocxDocumentWriterBase writer) throws Exception
    {
        if (sdt.getEndCharacterRunPr().getCount() != 0)
        {
            DocxBuilder builder = writer.getCurrentBuilder();
            builder.startElement("w:sdtEndPr");
            DocxRunPrWriter.getInstance().writeForNodes(sdt.getEndCharacterRunPr(), null, false, writer);
            builder.endElement();
        }
    }

    /// <summary>
    /// Convert bool values from model to OOXML SdtLockType enum.
    /// </summary>
    private static /*SdtLockType*/int getLockTypeFromBoolLockValues(boolean isLockContentControl, boolean isLockContents)
    {
        /*SdtLockType*/int result;

        if (isLockContentControl && isLockContents)
        {
            result = SdtLockType.SDT_AND_CONTENT_LOCKED;
        }
        else if (isLockContentControl)
        {
            result = SdtLockType.SDT_LOCKED;
        }
        else if (isLockContents)
        {
            result = SdtLockType.CONTENT_LOCKED;
        }
        else
        {
            result = SdtLockType.UNLOCKED;
        }

        return result;
    }

    /// <summary>
    /// Writes edit or move revision start.
    /// </summary>
    /// <returns>
    /// Id of written revision.
    /// If there are no revisions, then returns -1.
    /// </returns>
    private static int writeRevisionStart(ITrackableNode sdt, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        EditRevision editRevision = (sdt.getInsertRevision() != null) ? sdt.getInsertRevision() : sdt.getDeleteRevision();
        if (editRevision != null)
        {
            int id = ((INrxWriterContext)writer).getNextAnnotationId();
            builder.writeSdtRevisionStart(editRevision, id);
            return id;
        }

        MoveRevision moveRevision = (sdt.getMoveFromRevision() != null) ? sdt.getMoveFromRevision() : sdt.getMoveToRevision();
        if ((moveRevision != null) && (moveRevision.getType() != MoveRevisionType.NONE))
        {
            int id = ((INrxWriterContext)writer).getNextAnnotationId();
            builder.writeSdtRevisionStart(moveRevision, id);
            return id;
        }

        return -1;
    }

    /// <summary>
    /// Writes edit or move revision end.
    /// </summary>
    private static void writeRevisionEnd(ITrackableNode sdt, DocxBuilder builder, int id) throws Exception
    {
        EditRevision editRevision = (sdt.getInsertRevision() != null) ? sdt.getInsertRevision() : sdt.getDeleteRevision();
        if (editRevision != null)
        {
            builder.writeSdtRevisionEnd(editRevision, id);
            return;
        }

        MoveRevision moveRevision = (sdt.getMoveFromRevision() != null) ? sdt.getMoveFromRevision() : sdt.getMoveToRevision();
        if ((moveRevision != null) && (moveRevision.getType() != MoveRevisionType.NONE))
            builder.writeSdtRevisionEnd(moveRevision, id);
    }
}

