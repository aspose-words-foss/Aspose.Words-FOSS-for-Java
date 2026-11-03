// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/02/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.LocaleConverter;



/// <summary>
/// Reads w:sdt content controls.
/// </summary>
class DocxSdtReader
{
    DocxSdtReader(DocxInlineReader inlineReader, /*MarkupLevel*/int level)
    {
    	this(inlineReader, level, null);
	
    }

    DocxSdtReader(DocxInlineReader inlineReader, /*MarkupLevel*/int level, TablePr objPr)
    {
        Debug.assertTrue(inlineReader != null);
        mInlineReader = inlineReader;
        mLevel = level;
        mObjPr = objPr;
    }

    /// <summary>
    /// Reads sdt object of any level.
    /// </summary>
    void read(NrxDocumentReaderBase reader) throws Exception
    {
        StructuredDocumentTag sdt = new StructuredDocumentTag(reader.getDocument(), mLevel);
        reader.markupStart(sdt);

        if (reader.getCurrentCustomXmlRevision() != null)
            setRevision(sdt, reader.getCurrentCustomXmlRevision());

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("sdt"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"sdtContent"*/0:
                    readSdtContent(reader);
                    // WORDSNET-26378 When reading the nested empty SDT (<w:sdtContent/>), PossibleSdtEnd node may be very far from the SDT actual
                    // possible end or even turn out to be the end of the SDT preceding the current one, like in the specified ticket. In this case
                    // reader.CurContainer can point to the SDT actual possible end.
                    if (xmlReader.isEmptyElement() && !NodeUtil.isMarkupNode(reader.getCurContainer()) &&
                       ((xmlReader.getPossibleSdtEnd() == null) || (NodeUtil.areNodesInSameStory(reader.getCurContainer(), xmlReader.getPossibleSdtEnd()) &&
                        (NodeUtil.getNodeIndex(reader.getCurContainer()) > NodeUtil.getNodeIndex(xmlReader.getPossibleSdtEnd())))))
                    {
                        xmlReader.setPossibleSdtEnd(reader.getCurContainer());
                    }
                    break;
                case /*"sdtPr"*/1:
                    readSdtPr(reader, sdt);
                    break;
                case /*"sdtEndPr"*/2:
                    readSdtEndPr(reader, sdt, xmlReader);
                    break;
                default:
                    // RESILIENCY WORDSNET-21161 The problem occurred because there is a paragraph that is a direct child of 'std' element.
                    // Earlier we ignored everything except 'sdtContent', 'sdtPr' and 'sdtEndPr',
                    // but it seems any block level element can be child of std. So read them to model.
                    if (mLevel == MarkupLevel.BLOCK)
                    {
                        DocxReaderFactory.getStoryReader().readChild(reader);
                    }
                    // WORDSNET-14295 A 'pict' element is direct child of an inline-level 'sdt' element.
                    else if (mLevel == MarkupLevel.INLINE)
                        mInlineReader.readChild(reader);
                    break;
            }
        }

        reader.markupEnd(sdt, xmlReader.getPossibleSdtEnd());
    }

    /// <summary>
    /// Read rPr element inside SdtEndPr (properties of end character of sdt).
    /// </summary>
    private static void readSdtEndPr(NrxDocumentReaderBase reader, StructuredDocumentTag sdt, NrxXmlReader xmlReader) throws Exception
    {
        while (xmlReader.readChild("sdtEndPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rPr"*/3:
                    DocxReaderFactory.getRunPrReader().read(reader, sdt.getEndCharacterRunPr());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Read sdtPr, where almost all the sdt properties reside.
    /// </summary>
    /// <remarks>
    /// The spec does not say explicitly about whether or not one sdt can contain multiple tags or single tag <see cref="SdtType"/>.
    /// Common sense advises, that there should be only one tag per sdt.
    /// So this read routine is designed such a way, that many tags can be read, but only the last tag is associated with sdt.
    /// </remarks>
    private static void readSdtPr(NrxDocumentReaderBase reader, StructuredDocumentTag sdt) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        while (xmlReader.readChild("sdtPr"))
        {
            // Read Office2010 sdt checkbox.
            if ((msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.W1_4_MARKUP,
                    complianceInfo.getCompliance() == OoxmlComplianceCore.ISO_STRICT))) &&
                ("checkbox".equals(xmlReader.getLocalName())))
            {
                SdtCheckBox checkbox = new SdtCheckBox();
                complianceInfo.isDocxExtensions(true);
                sdt.setControlProperties(checkbox);
                readCheckbox(reader, checkbox);
            }

            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"alias"*/4:
                    // WORDSNET-27233 Ignore empty 'alias' tag.
                    String title = xmlReader.readVal();
                    if(title != null)
                        sdt.setTitle(xmlReader.readVal());
                    break;
                case /*"bibliography"*/5:
                    sdt.setControlProperties(new SdtBibliography());
                    break;
                case /*"citation"*/6:
                    sdt.setControlProperties(new SdtCitation());
                    break;
                case /*"comboBox"*/7:
                {
                    SdtDropDownListBase combo = new SdtComboBox();
                    sdt.setControlProperties(combo);
                    combo.getListItems().setParentSdt(sdt);
                    readListBase(reader, combo);
                    break;
                }
                case /*"dataBinding"*/8:
                    readDataBinding(reader, sdt, xmlReader.getNamespaceURI());
                    break;
                case /*"date"*/9:
                    readDate(reader, sdt);
                    break;
                case /*"docPartList"*/10:
                {
                    SdtDocPart docPart = new SdtBuildingBlockGallery();
                    sdt.setControlProperties(docPart);
                    readDocPart(reader, docPart);
                    break;
                }
                case /*"docPartObj"*/11:
                {
                    SdtDocPart docPart = new SdtDocPartObj();
                    sdt.setControlProperties(docPart);
                    readDocPart(reader, docPart);
                    break;
                }
                case /*"dropDownList"*/12:
                {
                    SdtDropDownListBase list = new SdtDropDownList();
                    sdt.setControlProperties(list);
                    list.getListItems().setParentSdt(sdt);
                    readListBase(reader, list);
                    break;
                }
                case /*"equation"*/13:
                    sdt.setControlProperties(new SdtEquation());
                    break;
                case /*"group"*/14:
                    sdt.setControlProperties(new SdtGroup());
                    break;
                case /*"id"*/15:
                    sdt.setId(xmlReader.readIntVal());
                    break;
                case /*"label"*/16:
                    // we should check that this label really points to some sdt with ID = label, but we don't do this at the moment.
                    sdt.setLabel(xmlReader.readIntVal());
                    break;
                case /*"lock"*/17:
                    setLockProperties(xmlReader, sdt);
                    break;
                case /*"picture"*/18:
                    sdt.setControlProperties(new SdtPicture());
                    break;
                case /*"placeholder"*/19:
                    readPlaceholder(reader, sdt);
                    break;
                case /*"richText"*/20:
                    readText(reader, sdt, true);
                    break;
                case /*"rPr"*/3:
                    DocxReaderFactory.getRunPrReader().read(reader, sdt.getContentsRunPr());
                    break;
                case /*"showingPlcHdr"*/21:
                    sdt.isShowingPlaceholderText(xmlReader.readBoolVal());
                    break;
                case /*"tabIndex"*/22:
                    sdt.setTabIndex(xmlReader.readIntVal());
                    break;
                case /*"tag"*/23:
                    {
                        // Resilience WORDSNET-8694: empty tags are disallowed by spec but should be ignored, not thrown upon.
                        String tag = xmlReader.readVal();
                        if (StringUtil.hasChars(tag))
                            sdt.setTag(tag);
                        break;
                    }
                case /*"temporary"*/24:
                    sdt.isTemporary(xmlReader.readBoolVal());
                    break;
                case /*"text"*/25:
                    readText(reader, sdt, false);
                    break;
                // Only available in Office2013.
                // Element that specifies that the parent structured document tag is a container for repeated items.
                case /*"repeatingSection"*/26: // w15:repeatingSection
                    readRepeatingSection(reader, sdt);
                    break;
                // Only available in Office2013.
                // Element that specifies that the parent structured document tag is a repeated item.
                case /*"repeatingSectionItem"*/27: // w15:repeatingSectionItem
                {
                    complianceInfo.isDocxExtensions(true);
                    sdt.setControlProperties(new SdtRepeatingSectionItem());
                    break;
                }
                // Only available in Office2013.
                // Element that specifies the color on which to base the visual elements of a structured
                // document tag.
                case /*"color"*/28: // w15:color
                    readColor(reader, sdt);
                    break;
                // Only available in Office2013.
                case /*"appearance"*/29: // w15:appearance
                {
                    complianceInfo.isDocxExtensions(true);
                    // WORDSNET-25278 Do not update SDT content while read appearance.
                    sdt.setAppearanceInternal(DocxEnum.docxToSdtAppearance(xmlReader.readVal()));
                    break;
                }
                // Only available in Office2013.
                case /*"webExtensionCreated"*/30: // w15:webExtensionCreated
                {
                    complianceInfo.isDocxExtensions(true);
                    if (xmlReader.readBoolVal())
                        sdt.setWebExtensionRelationship(SdtWebExtensionRelationship.CREATED_BY_WEB_EXTENSION);
                    break;
                }
                // Only available in Office2013.
                case /*"webExtensionLinked"*/31: // w15:webExtensionLinked
                {
                    complianceInfo.isDocxExtensions(true);
                    if (xmlReader.readBoolVal())
                        sdt.setWebExtensionRelationship(SdtWebExtensionRelationship.LINKED_TO_WEB_EXTENSION);
                    break;
                }
                // Available in Office 2010 and higher.
                case /*"entityPicker"*/32: // w14:entityPicker
                {
                    complianceInfo.isDocxExtensions(true);
                    sdt.setControlProperties(new SdtEntityPicker());
                    break;
                }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        // Resiliency 6902: MS Word 2010 defaults RichText type, so it can be omitted.
        if (sdt.getSdtType() == SdtType.NONE)
            sdt.setControlProperties(new SdtText(true));

        // WORDSNET-19609 XPath of RepeatingSection should not contain indexer in last node selector.
        if (sdt.isRepeatingSection() && !sdt.getXmlMapping().isEmpty())
        {
            String xPath = removeLastIndexer(sdt.getXmlMapping().getXPath());
            if (xPath != null)
            {
                sdt.getXmlMapping().setXPath(xPath);
                sdt.getDocument().warn(WarningType.DATA_LOSS, WarningSource.DOCX,
                    WarningStrings.REPEATING_SECTION_X_PATH_LAST_INDEXER_REMOVED);
            }
        }

        // WORDSNET-24520 Ignore checksum for all SDT types exclude rich textbox.
        XmlMapping mapping = sdt.getXmlMapping();
        if ((sdt.getSdtType() != SdtType.RICH_TEXT) && !mapping.isEmpty() && StringUtil.hasChars(mapping.getStoreItemChecksum()))
            mapping.setStoreItemChecksum(null);
    }

    /// <summary>
    /// Reads the 'color' Office2013 extension of the 'sdtPr' element.
    /// </summary>
    private static void readColor(NrxDocumentReaderBase reader, StructuredDocumentTag sdt)
    {
        reader.getComplianceInfo().isDocxExtensions(true);
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"val"*/33:
                    sdt.setBaseColor(NrxXmlUtil.xmlToColor(xmlReader.getValue()));
                    break;
                case /*"themeColor"*/34:
                    sdt.setThemeColor(xmlReader.getValue());
                    break;
                case /*"themeShade"*/35:
                    sdt.setThemeShade(xmlReader.getValue());
                    break;
                case /*"themeTint"*/36:
                    sdt.setThemeTint(xmlReader.getValue());
                    break;
                default:
                    break;
            }
        }
    }

    /// <summary>
    /// Reads Office2013 RepeatingSection properties.
    /// </summary>
    private static void readRepeatingSection(NrxDocumentReaderBase reader, StructuredDocumentTag tag)
    {
        reader.getComplianceInfo().isDocxExtensions(true);
        NrxXmlReader xmlReader = reader.getXmlReader();
        SdtRepeatingSection repeatingSection = new SdtRepeatingSection();
        while (xmlReader.readChild("repeatingSection"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"sectionTitle"*/37:
                    repeatingSection.setSectionTitle(xmlReader.readVal());
                    break;
                case /*"doNotAllowInsertDeleteSection"*/38:
                    repeatingSection.setDoNotAllowInsertDeleteSection(xmlReader.readBoolVal());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        tag.setControlProperties(repeatingSection);
    }

    /// <summary>
    /// Reads and converts <see cref="SdtLockType"/> to boolean properties, which are assigned to sdt.
    /// </summary>
    private static void setLockProperties(NrxXmlReader xmlReader, StructuredDocumentTag sdt)
    {
        /*SdtLockType*/int lockType = DocxEnum.docxToSdtLockType(xmlReader.readVal());
        sdt.setLockContentControl((lockType == SdtLockType.SDT_LOCKED) || (lockType == SdtLockType.SDT_AND_CONTENT_LOCKED));
        sdt.setLockContents((lockType == SdtLockType.CONTENT_LOCKED) || (lockType == SdtLockType.SDT_AND_CONTENT_LOCKED));
    }

    /// <summary>
    /// Read contents of sdt.
    /// </summary>
    private void readSdtContent(NrxDocumentReaderBase reader) throws Exception
    {
        switch (mLevel)
        {
            case MarkupLevel.INLINE:
                mInlineReader.readChildren(reader);
                break;
            case MarkupLevel.BLOCK:
                DocxReaderFactory.getStoryReader().readChildren(reader);
                break;
            case MarkupLevel.ROW:
                DocxTableReader.readChildren(reader, mObjPr);
                break;
            case MarkupLevel.CELL:
                DocxRowReader.readChildren(reader, mObjPr);
                break;
            default:
                throw new IllegalStateException("Please report exception.");
        }
    }

    /// <summary>
    /// Reads control properties for Office2010 sdt checkbox.
    /// </summary>
    /// <param name="reader"></param>
    /// <param name="checkbox"></param>
    private static void readCheckbox(NrxDocumentReaderBase reader, SdtCheckBox checkbox)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("checkbox"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"checked"*/39:
                    checkbox.setChecked(xmlReader.readIntVal() != 0);
                    break;
                case /*"checkedState"*/40:
                case /*"uncheckedState"*/41:
                {
                    // WORDSNET-24098 Use default attribute values while reading a checkbox properties.
                    String fontName = xmlReader.readAttribute("font", "MS Gothic");
                    String charCode = xmlReader.readVal();

                    if ("checkedState".equals(xmlReader.getLocalName()))
                    {
                        // Set read properties as checked state info.
                        checkbox.setCheckedStateInfo(new SdtCheckBoxStateInfo(fontName,
                            parseCharCode(charCode, SdtCheckBoxStateInfo.DEFAULT_CHECKED_STATE_INFO.getCharacterCode())));
                    }
                    else
                    {
                        // Set read properties as unchecked state info.
                        checkbox.setUncheckedStateInfo(new SdtCheckBoxStateInfo(fontName,
                            parseCharCode(charCode, SdtCheckBoxStateInfo.DEFAULT_UNCHECKED_STATE_INFO.getCharacterCode())));
                    }
                    break;
                }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Parses character code safely, returns default value in case of error.
    /// </summary>
    private static int parseCharCode(String charCode, int defaultCharCode)
    {
        int result = FormatterPal.tryParseHex(charCode);

        return (result != Integer.MIN_VALUE)
            ? result
            : defaultCharCode;
    }

    /// <summary>
    /// Reads ComboBox and DropDownList attributes and children.
    /// </summary>
    private static void readListBase(NrxDocumentReaderBase reader, SdtDropDownListBase listContainer)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // read attributes

        String lastValue = "";
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"lastValue"*/42:
                    lastValue = xmlReader.getValue();
                    break;
                default:
                    // Do nothing
                    break;
            }
        }

        xmlReader.moveToElement();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"listItem"*/43:
                    readListItem(reader, listContainer);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        listContainer.getListItems().setLastItemValue(lastValue);
        if (StringUtil.hasChars(lastValue))
            setLastValue(lastValue, listContainer.getListItems());
    }

    /// <summary>
    /// Once we've read lastValue, now we try to find it among values of list items, if succeeded, then we store it
    /// in the collection, otherwise ignore it.
    /// </summary>
    private static void setLastValue(String lastValue, SdtListItemCollection listItems)
    {
        int index = listItems.indexOfItemValue(lastValue);
        if (index >= 0)
            listItems.setSelectedValueInternal(listItems.get(index));
    }

    /// <summary>
    /// Read items from <see cref="SdtComboBox"/> and <see cref="SdtDropDownList"/>
    /// </summary>
    private static void readListItem(NrxDocumentReaderBase reader, SdtDropDownListBase listContainer)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        String displayText = "";
        String value = "";
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"displayText"*/44:
                    displayText = xmlReader.getValue();
                    break;
                case /*"value"*/45:
                    value = xmlReader.getValue();
                    break;
                default:
                    // Do nothing
                    break;
            }
        }

        // WORDSNET-24793 Allow empty value list item during roundtrip.
        SdtListItem listItem = (StringUtil.hasChars(value))
            ? new SdtListItem(displayText, value)
            : new SdtListItem(); {listItem.setDisplayText(displayText);};

        listContainer.getListItems().add(listItem);
    }

    /// <summary>
    /// Read dataBinding info, create dataBinding.
    /// </summary>
    /// <remarks>We do not attempt to bind it to document part, and do not check validity of the binding itself.
    /// The actual custom xml data is read unparsed as <see cref="CustomXmlPartCollection"/>.
    /// This will probably require rework when we decide to support rendering of sdt and public api.</remarks>
    private static void readDataBinding(NrxDocumentReaderBase reader, StructuredDocumentTag sdt, String namespaceURI)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        String prefixMappings = "";
        String storeItemId = "";
        String xPath = "";
        String storeItemChecksum = "";
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"prefixMappings"*/46:
                    prefixMappings = xmlReader.getValue();
                    break;
                case /*"storeItemID"*/47:
                    storeItemId = xmlReader.getValue();
                    break;
                case /*"xpath"*/48:
                    xPath = xmlReader.getValue();
                    break;
                case /*"storeItemChecksum"*/49:
                    storeItemChecksum = xmlReader.getValue();
                    break;
                default:
                    // Do nothing
                    break;
            }
        }

        // w15:dataBinding conforms to [MS-DOCX]: Word Extensions to the Office Open XML (.docx) File
        if (msString.equals(namespaceURI, DocxNamespaces.getNamespace(DocxNamespace.W1_5_MARKUP,
                complianceInfo.getCompliance() == OoxmlComplianceCore.ISO_STRICT)))
            complianceInfo.isDocxExtensions(true);

        if (StringUtil.hasChars(xPath))     // required attribute, if it is empty, then dataBinding is invalid.
            sdt.getXmlMapping().setMapping(storeItemId, xPath, (StringUtil.hasChars(prefixMappings)) ? prefixMappings : "");

        if (StringUtil.hasChars(storeItemChecksum))
            sdt.getXmlMapping().setStoreItemChecksum(storeItemChecksum);
    }

    /// <summary>
    /// Read date sdt and all its properties.
    /// </summary>
    private static void readDate(NrxDocumentReaderBase reader, StructuredDocumentTag sdt)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        SdtDate  date = new SdtDate();
        sdt.setControlProperties(date);

        // read attributes
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"fullDate"*/50:
                    date.setFullDate(FormatterPal.xmlToDateTime(xmlReader.getValue()));
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        xmlReader.moveToElement();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"calendar"*/51:
                    date.setCalendarType(DocxEnum.docxToCaledarType(xmlReader.readVal()));
                    break;
                case /*"dateFormat"*/52:
                    date.setDateFormat(xmlReader.readVal());
                    break;
                case /*"lid"*/53:
                    String lid = xmlReader.readVal();
                    if(StringUtil.hasChars(lid))
                        date.setLid(LocaleConverter.docxTagToLocale(lid));
                    break;
                case /*"storeMappedDataAs"*/54:
                    date.setStoreMappedDataAs(DocxEnum.docxToSdtDateStorageFormat(xmlReader.readVal()));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
        date.setNeedToUpdateContent(false);
    }

    /// <summary>
    /// Read <see cref="SdtDocPart"/> and the related props.
    /// </summary>
    private static void readDocPart(NrxDocumentReaderBase reader, SdtDocPart docPart)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"docPartCategory"*/55:
                    docPart.setBuildingBlockCategory(xmlReader.readVal());
                    break;
                case /*"docPartGallery"*/56:
                    docPart.setBuildingBlockType(xmlReader.readVal());
                    break;
                case /*"docPartUnique"*/57:
                    docPart.isUnique(xmlReader.readBoolVal());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Read placeholder object lookup data in the appropriate docPart.
    /// </summary>
    private static void readPlaceholder(NrxDocumentReaderBase reader, StructuredDocumentTag tag)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("placeholder"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"docPart"*/58:
                    tag.setPlaceholderNameCore(xmlReader.readVal());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Read text tag and its attribute.
    /// </summary>
    private static void readText(NrxDocumentReaderBase reader, StructuredDocumentTag sdt, boolean isRichText)
    {
        SdtText text = new SdtText(isRichText);
        sdt.setControlProperties(text);
        NrxXmlReader xmlReader = reader.getXmlReader();

        // read attributes
        while (xmlReader.moveToNextAttribute())
        {
            if ("multiLine".equals(xmlReader.getLocalName()))
                text.isMultiline(xmlReader.getValueAsBool());
        }
    }

    /// <summary>
    /// Sets specified revision for <paramref name="sdt"/>.
    /// </summary>
    private static void setRevision(StructuredDocumentTag sdt, RevisionBase revision)
    {
        EditRevision editRevision = ms.as(revision, EditRevision.class);
        if (editRevision != null)
        {
            if (editRevision.getType() == EditRevisionType.DELETION)
                ((ITrackableNode)sdt).setDeleteRevision(editRevision.deepClone());
            else
                ((ITrackableNode)sdt).setInsertRevision(editRevision.deepClone());
        }
        else
        {
            MoveRevision moveRevision = ms.as(revision, MoveRevision.class);
            if (moveRevision != null)
            {
                switch (moveRevision.getType())
                {
                    case MoveRevisionType.MOVE_FROM:
                        ((ITrackableNode)sdt).setMoveFromRevision(moveRevision.deepClone());
                        break;
                    case MoveRevisionType.MOVE_TO:
                        ((ITrackableNode)sdt).setMoveToRevision(moveRevision.deepClone());
                        break;
                    case MoveRevisionType.NONE:
                        // Skip.
                        break;
                    default:
                        Debug.assertTrue(false);
                        break;
                }
            }
        }
    }

    /// <summary>
    /// Removes indexer from the last node selector in XPath.
    /// </summary>
    /// <returns>Returns <c>null</c>, if there is no indexer at the last node selector.</returns>
    private static String removeLastIndexer(String xPath)
    {
        // Find start of last node selector.
        int start = xPath.lastIndexOf('/');

        // Find indexer opening character.
        start = xPath.indexOf('[', start + 1);
        if (start == -1)
            return null;

        // Find indexer closing character.
        int end = xPath.indexOf(']', start + 1);
        if (end == -1)
            return null;

        // Remove indexer.
        return msString.remove(xPath, start, end - start + 1);
    }

    /// <summary>
    /// The same Pr is used either for storing TablePr or rowPr.
    /// </summary>
    private /*final*/ TablePr mObjPr;

    private /*final*/ DocxInlineReader mInlineReader;
    private /*final*/ /*MarkupLevel*/int mLevel;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"sdtContent",
		"sdtPr",
		"sdtEndPr",
		"rPr",
		"alias",
		"bibliography",
		"citation",
		"comboBox",
		"dataBinding",
		"date",
		"docPartList",
		"docPartObj",
		"dropDownList",
		"equation",
		"group",
		"id",
		"label",
		"lock",
		"picture",
		"placeholder",
		"richText",
		"showingPlcHdr",
		"tabIndex",
		"tag",
		"temporary",
		"text",
		"repeatingSection",
		"repeatingSectionItem",
		"color",
		"appearance",
		"webExtensionCreated",
		"webExtensionLinked",
		"entityPicker",
		"val",
		"themeColor",
		"themeShade",
		"themeTint",
		"sectionTitle",
		"doNotAllowInsertDeleteSection",
		"checked",
		"checkedState",
		"uncheckedState",
		"lastValue",
		"listItem",
		"displayText",
		"value",
		"prefixMappings",
		"storeItemID",
		"xpath",
		"storeItemChecksum",
		"fullDate",
		"calendar",
		"dateFormat",
		"lid",
		"storeMappedDataAs",
		"docPartCategory",
		"docPartGallery",
		"docPartUnique",
		"docPart"
	);

}

