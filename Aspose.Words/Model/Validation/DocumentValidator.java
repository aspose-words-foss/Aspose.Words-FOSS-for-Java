// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import java.util.HashMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Map;
import com.aspose.CaseInsensitiveHashSet;
import com.aspose.StringUtil;
import com.aspose.ms.System.msObject;
import com.aspose.HashSetGeneric;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.msString;
import com.aspose.ISetGeneric;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.words.ref.RefInt;


/// <summary>
/// There are certain tasks that must be performed before a document is saved
/// into DOC, WordML or RTF for the document to be valid in MS Word.
/// Since we have several exporters, this logic is separated into this class.
/// It is automatically invoked during save, before the exporter is invoked.
/// </summary>
class DocumentValidator extends DocumentVisitor
{
    /// <summary>
    /// Call this method to validate a document. Fills a <see cref="SaveInfo"/> that contains
    /// information needed by most of the exporters.
    /// </summary>
    void execute(SaveInfo saveInfo) throws Exception
    {
        Debug.assertTrue(saveInfo != null);

        Document doc = saveInfo.Document;

        // DocumentValidator can do node manipulations, for example updating SDT,
        // converting MathObject to Shapes and so on.
        // These operations should not be tracked so suspend tracking.
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(doc);
        try /*JAVA: was using*/
        {
            SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(doc);
            try /*JAVA: was using*/
            {
                mSaveInfo = saveInfo;
                mIso29500ComplianceEnforcer = new Iso29500ComplianceEnforcer(mSaveInfo.SaveOptions, getWarningCallback(),
                                                                             doc.getComplianceInfo());
                mSaveActions = fetchSaveActions(mSaveInfo.getSaveFormat());
                // FOSS
                mShapeValidator = new ShapeValidator(saveInfo, mSaveActions, this);
                mListValidator = new ListValidator(doc.getLists(), mSaveInfo, getWarningCallback());
                mFootnoteValidator = new FootnoteValidator();
                mCustomXmlValidator = new CustomXmlValidator(mSaveInfo);
                mUnitConverter = new UnitConverter();
                mOfficeMathToShapeConverter = WriterFactory.createOfficeMathToShapeConverter(mSaveInfo.Document,
                    mSaveInfo.SaveOptions);
                mTableFormattingExpanders = new HashMap<DocumentBase, TableFormattingExpander>();

                mRubyConverter = new RubyConverter(this, saveInfo);

                mEQFieldConverter = new EQFieldConverter(this, saveInfo);

                mRsidTable = doc.getDocPr().Rsids;

                mXmlMappingContext = new XmlMappingContext();
                mXmlMappingContext.CustomTimeZone = saveInfo.SaveOptions.getCustomTimeZoneInfo();
                mProgressProcessor = new SavingProgressProcessor(doc, mSaveInfo.SaveOptions, SavingStageType.PROCESSING);

                // WORDSNET-23153 Sets the required Compliance if compatibilityMode is "15".
                CustomCompatibilitySetting compatMode = doc.getCompatibilityOptions().getCustomCompatibilitySettings().get("compatibilityMode");
                if ((compatMode != null) && ("15".equals(compatMode.getValue())) && (saveInfo.getOoxmlCompliance() == OoxmlComplianceCore.ECMA_376))
                {
                    if (doc.getComplianceInfo() == null)
                        doc.setComplianceInfo(new OoxmlComplianceInfo());
                    // Starting from 2013 version MS Word sets the default value of Iso29500_2008_Transitional to a new document.
                    doc.getComplianceInfo().markAsIsoTransitional();
                }

                // AM. Don't really know should we update before or after actual validator processing.
                if (mSaveInfo.SaveOptions.getUpdateXmlMapping())
                    updateMappedCustomXml(doc);

                doc.accept(this);

                // Reset reference to avoid progress call invocation for the glossary document.
                mProgressProcessor = null;

                if (doc.getGlossaryDocument() != null)
                    doc.getGlossaryDocument().accept(this);

                removeUnusedSdtPlaceholders(doc);

                // This performs a visiting over the entire document. Can it be reworked to work "inline" as all other validators here?
                if (isSaveAction(DocumentValidatorActions.UPDATE_LIST_LABELS))
                {
                    ListLabelUpdater.updateListLabels(doc);
                    mListLabelsUpdated = true;
                }

                mShapeValidator.finish();

                // This map is needed for HTML export
                saveInfo.BookmarkNamesSet = mBookmarkValidator.getBookmarkNames();

                validateCustomization(doc);

                checkCanSaveMacros();

                // WORDSNET-26606 Rebuild MacroNames structure to start AutoOpen macros properly.
                if ((doc.getVbaProject() != null) && (doc.getVbaProject().getMacroNames() == null))
                    doc.getVbaProject().rebuildMacroNames();

                // Synchronize public font embedding options stored in FontInfos
                // and internal font embedding options stored in DocPr.
                doc.getDocPr().updateEmbedFontOptions(doc.getFontInfos());

                if (mSaveInfo.SaveOptions.getMemoryOptimization())
                    InternManager.add(doc);
            }
            finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
    }

    /// <summary>
    /// Updates content controls mapped to CustomXML.
    /// </summary>
    /// <dev>
    /// AM. I think we need update outside DocumentVisitor pattern during post-loading stage as well but lets postone.
    /// </dev>
    private void updateMappedCustomXml(Document doc) throws Exception
    {
        /*NodeType*/int[] sdtNodeTypes =
            new /*NodeType*/int[] { NodeType.STRUCTURED_DOCUMENT_TAG, NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START };

        ArrayList<Node> sdtList = doc.getChildNodes(sdtNodeTypes, true).<Node>toList();

        // AM. Use following simple algorithm:
        // First we collect all document markup into the list.
        // Before actual update of the SDT remove all its nested markups from the list as might be replaced during update.
        // After the SDT has been updated restore the list by adding all actual nesting markup back to the list.

        // Do not use enumerator here, list will be modified inside loop.
        for (int i = 0; i < sdtList.size(); i++)
        {
            Node sdtNode = sdtList.get(i);

            StructuredDocumentTag sdt = sdtNode instanceof StructuredDocumentTagRangeStart
                ? ((StructuredDocumentTagRangeStart)sdtNode).getInternalSdt()
                : (StructuredDocumentTag)sdtNode;

            // WORDSNET-16209 Do not update deleted SDTs.
            if (((ITrackableNode)sdt).getDeleteRevision() != null)
                continue;

            // Update data bound SDT.
            if (sdt.getXmlMapping().checksumNotEqual(mXmlMappingContext))
            {
                // We are going to update content.
                // Remove all nested markups from list as it might be replaced.
                for (Node node : (Iterable<Node>) (ms.as(sdt, CompositeNode.class)).getChildNodes(sdtNodeTypes, true))
                    sdtList.remove(node);

                if (sdt.getXmlMapping().updateContent(mXmlMappingContext))
                {
                    // Update Word2019 custom XML CRC checksum.
                    // Note, we do not update original checksum during post-loading to track mapped XML possible changes.
                    // WORDSNET-22617 Write checksum for the mapped content by default as the latest Word does.
                    if ((sdt.getXmlMapping() != null) &&
                        (sdt.getXmlMapping().getCustomXmlPart() != null) &&
                        // In the case of the Ecma376 compliance the AW ignores data binding while writing the  document.
                        (mSaveInfo.getOoxmlCompliance() != OoxmlComplianceCore.ECMA_376) &&
                        // Valid only for rich textbox.
                        (sdt.getSdtType() == SdtType.RICH_TEXT))
                    {
                        sdt.getXmlMapping().setStoreItemChecksum(com.aspose.Crc32Xfer.getBase64String(sdt.getXmlMapping().getCustomXmlPart().getData()));
                    }
                }
                else
                {
                    warn(
                        WarningType.DATA_LOSS,
                        WarningSource.VALIDATOR,
                    WarningStrings.DOCUMENT_VALIDATOR_SDT_XML_MAPPING);
                }

                // Collect all, probably newly created, nested markup.
                msArrayList.addRange(sdtList, (ms.as(sdt, CompositeNode.class)).getChildNodes(sdtNodeTypes, true));
            }
        }
    }

    /// <summary>
    /// Check that the file format allow save macros or writer don't check it.
    /// </summary>
    private void checkCanSaveMacros()
    {
        if (!mSaveInfo.Document.hasMacros())
            return;

        switch (mSaveInfo.getSaveFormat())
        {
            case SaveFormat.DOCX:
            case SaveFormat.DOTX:
            case SaveFormat.FLAT_OPC:
                throw new IllegalStateException(
                    "This document contains macros (VBA project) and you are " +
                    "attempting to save it in a Macro-Free format. " +
                    "Such document will be invalid if created. " +
                    "You need to either save it in a Macro-Enabled format (.DOCM or .DOTM) or " +
                    "remove macros before saving using the Document.RemoveMacros method.");
            default:
                // Do nothing, all ok.
                break;
        }
    }

    /// <summary>
    /// Reverts attributes modified by expanding procedures.
    /// </summary>
    /// <remarks>TableFormattingExpander may modify in table nodes.
    /// All these changes are reverted after document is saved.</remarks>
    void revert() throws Exception
    {
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(mSaveInfo.Document);
        try /*JAVA: was using*/
        {
            if (mSaveInfo.SaveOptions.getMemoryOptimization())
                InternManager.remove(mSaveInfo.Document);

            // Revert changes made by table expanders in document and glossary document.
            for (Map.Entry<DocumentBase, TableFormattingExpander> entry : mTableFormattingExpanders.entrySet())
                entry.getValue().restoreDirectAttrs();

            // WORDSNET-27614 Do not leave the DML shape replaced with its fallback.
            mShapeValidator.revert();

            mOfficeMathToShapeConverter.revert();
            mRubyConverter.revert();
            mEQFieldConverter.revert();
            mTableValidator.revert();
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
    }

    public /*override*/ /*VisitorAction*/int visitDocumentStart(Document doc) throws Exception
    {
        // AM. I tried to avoid accept lot of golds by do not writing rsid table.
        // Unfortunately RTF golds already contains rsid table collected during writing.
        // So I clear original rsid table and it will be rebuilt in test mode.
        if (!mSaveInfo.SaveOptions.getWriteRsidTable())
            mSaveInfo.Document.getDocPr().Rsids.clear();

        if (doc.getDocPr().LinkStyles)
        {
            // AM. There is some issue related to RTF format.
            // RTF format supports update style option but Word does not update style from custom template.
            // If custom template is specified then Word shows "Automatically update styles" checkbox unchecked
            // ignoring /linkstyles keyword in document.
            // So RTF can update styles only from default template specified in RTF as empty string.
            // Currently I decided to do not mimic this issue.
            doc.updateStyles(mSaveInfo.SaveOptions);
        }

        resetOptionsIfOptimizedForVersion(doc);

        documentStartCore(doc);
        mIso29500ComplianceEnforcer.updateCompatibilityOptions(doc.getCompatibilityOptions());

        mCustomXmlValidator.visitDocumentStart(doc);

        if (doc.getDigitalSignatures().getCount() > 0)
            warn(WarningType.DATA_LOSS, WarningSource.VALIDATOR, WarningStrings.DOCUMENT_IS_DIGITALLY_SIGNED);

        // FOSS

        updateDocumentSecurityProperty();
        doc.getBuiltInDocumentProperties().trimSpaces();
        doc.ensureMinimum();

        doc.getStyles().validate();

        if (!doc.getSdtPlaceholderManager().isGlossaryEmpty())
            doc.getSdtPlaceholderManager().startReferenceCounter();

        mUnitConverter.visitDocumentStart(doc);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitDocumentEnd(Document doc) throws Exception
    {
        documentEndCore(doc);

        // Remove non-closed SDT ranges.
        while (mSdtRangeStack.getCount() > 0)
            mSdtRangeStack.pop().remove();

        if (doc.getCompatibilityOptions().isOptimized() &&
            mSaveInfo.isDocxFormat() &&
            (getOoxmlCompliance() == OoxmlComplianceCore.ECMA_376) &&
            (doc.getCompatibilityOptions().getMswVersion() >= MsWordVersionCore.WORD_2010))
        {
            warn(WarningType.HINT, WarningSource.VALIDATOR, WarningStrings.HINT_TO_REMOVE_COMPATIBILITY_MODE);
        }

        // This call need to be sure that validation stage finished with the 100% value.
        if (mProgressProcessor != null)
            mProgressProcessor.complete();

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitGlossaryDocumentStart(GlossaryDocument glossary)
    {
        glossary.getSdtPlaceholderManager().startReferenceCounter();

        documentStartCore(glossary);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitGlossaryDocumentEnd(GlossaryDocument glossary) throws Exception
    {
        documentEndCore(glossary);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Performs re-initialization for the main document and then for the glossary document.
    /// </summary>
    private void documentStartCore(DocumentBase doc)
    {
        // We allow long bookmark names for several document formats.
        boolean allowLongBookmarkNames = isSaveAction(DocumentValidatorActions.LONG_BOOKMARK_NAMES);

        mBookmarkValidator = new BookmarkValidator(getWarningCallback(), !allowLongBookmarkNames);
        mAnnotationValidator = new AnnotationValidator(doc, mSaveInfo, getWarningCallback());
        mFieldValidator = new FieldValidator(getWarningCallback(), mSaveInfo.getSaveFormat());
        mMathValidator = new MathValidator(doc);
        mTableValidator = new TableValidator(mSaveActions, mSaveInfo);
        mShapeValidator.documentStart(doc, mBookmarkValidator);
        mUsedFontNames = isUpdateFontInfos() ? new CaseInsensitiveHashSet() : null;
        mStoryTypeStack = new StoryTypeStack();

        // It only makes sense to expand table styles if the load format supports them.
        // If we expand table styles when the load format does not support them, we get several problems in golds.
        // These are probably because some codecs read table styles partially (e.g. table-level run and paragraph properties only).
        // This results in incorrect (incomplete) expansion. So we pretend we don't have table styles in this case.
        if (loadFormatSupportsTableStyles(mSaveInfo.Document.getOriginalLoadFormat()))
        {
            // Ideally we should only expand table style formatting when it is required for this save format.
            // But (as with WordML for example) we also should expand table styles if document default formatting
            // will be expanded. If we expand only document defaults without expanding table styles we can create
            // situations where default formatting will "override" table style formatting (because it is expanded
            // into paragraph styles) and the output will appear different to the original.
            if (isSaveAction(DocumentValidatorActions.EXPAND_TABLE_STYLES))
            {
                // RK This looks like an optimization. Do we need it?
                if (hasTableStyleFormatting(doc))
                    mTableFormattingExpanders.put(doc, new TableFormattingExpander());
            }
        }

        if ((doc.getPeople().getCount() > 0) && mSaveInfo.isPreIsoTransitionalWordFormat())
            warnOfUnsupportedByFormat(WarningType.DATA_LOSS, WarningStrings.NOT_SUPPORTED_PEOPLE_PART);

        WebExtensionValidator.visitDocumentStart(doc, mSaveInfo, getWarningCallback());

        warnOfUnsupportedSettings(doc);

        mListValidator.visitDocumentStart();
    }

    /// <summary>
    /// Resets document options that are not supported by desired version of MS Word if optimization of document
    /// contents for the version is performed by customer.
    /// </summary>
    private static void resetOptionsIfOptimizedForVersion(Document doc)
    {
        if (!doc.getCompatibilityOptions().isOptimized())
            return;

        /*MsWordVersionCore*/int version = doc.getCompatibilityOptions().getMswVersion();
        DocPr docPr = doc.getDocPr();

        if (version < MsWordVersionCore.WORD_2010)
        {
            docPr.DiscardImageEditingData = false;
            docPr.DefaultImageDpi = 0;
            docPr.DocId = null;
        }

        if (version < MsWordVersionCore.WORD_2013)
        {
            docPr.ChartTrackingRefBased = false;
            docPr.DocumentSetId = null;
        }
    }

    /// <summary>
    /// Generates warnings if some settings are not supported by destination format.
    /// Works only for OOXML, DOC, RTF, WML formats.
    /// </summary>
    private void warnOfUnsupportedSettings(DocumentBase doc)
    {
        DocPr docPr = doc.getDocPr();
        /*SaveFormat*/int format = mSaveInfo.getSaveFormat();

        // [MS-DOCX] settings
        if (mSaveInfo.isPreIsoTransitionalWordFormat())
        {
            if (docPr.DiscardImageEditingData)
                warnOfUnsupportedSetting("discardImageEditingData");

            if (docPr.DefaultImageDpi > 0)
                warnOfUnsupportedSetting("defaultImageDpi");

            if (docPr.ChartTrackingRefBased)
                warnOfUnsupportedSetting("chartTrackingRefBased");

            if (StringUtil.hasChars(docPr.DocumentSetId))
                warnOfUnsupportedSetting("w15:docId");

            if (StringUtil.hasChars(docPr.DocId))
                warnOfUnsupportedSetting("w14:docId");
        }

        if (format == SaveFormat.WORD_ML)
        {
            if (docPr.DoNotAutoCompressPictures)
                warnOfUnsupportedSetting("doNotAutoCompressPictures");

            if (docPr.RemoveDateAndTime)
                warnOfUnsupportedSetting("removeDateAndTime");
        }
        else if (format == SaveFormat.RTF)
        {
            if (docPr.DoNotAutoCompressPictures)
                warnOfUnsupportedSettingByAw("doNotAutoCompressPictures");

            if (docPr.RemoveDateAndTime)
                warnOfUnsupportedSettingByAw("removeDateAndTime");
        }

        if (mSaveInfo.isLegacyFormat())
        {
            if (docPr.UpdateFields)
                warnOfUnsupportedSetting("updateFields");

            if (docPr.ForceUpgrade)
                warnOfUnsupportedSetting("forceUpgrade");
        }
    }

    /// <summary>
    /// Performs finalization for the main document and then for the glossary document.
    /// </summary>
    private void documentEndCore(DocumentBase doc) throws Exception
    {
        // If the document has a theme we need to update theme colors.
        boolean isThemeColorsNeedUpdate = (doc.getThemeInternal() != null);

        // If the font info collection must be updated, for example for ODT documents to work, let's do.
        if (isUpdateFontInfos() || isThemeColorsNeedUpdate)
        {
            RunPrValidator runPrValidator = new RunPrValidator();
            runPrValidator.execute(doc, mUsedFontNames);
        }

        if (isUpdateFontInfos())
            doc.getFontInfos().updateToUsedFonts(mUsedFontNames);

        MailMergeSettings mailMergeSettings = doc.getDocPr().MailMergeSettings;
        if (!mailMergeSettings.isEmpty())
            mailMergeSettings.getOdso().getFieldMapDatas().makeValid();

        mFieldValidator.visitDocumentEnd();
        mAnnotationValidator.visitDocumentEnd();
        mBookmarkValidator.visitDocumentEnd();
        mListValidator.visitDocumentEnd();
        mShapeValidator.documentEnd(doc);

        // WORDSNET-21167 Word adds default footnote separators if it missing and
        // there any footnote in the document.
        if (mSaveInfo.HasFootnotes)
        {
            ensureFootnoteSeparator(doc, FootnoteSeparatorType.FOOTNOTE_SEPARATOR);
            ensureFootnoteSeparator(doc, FootnoteSeparatorType.FOOTNOTE_CONTINUATION_SEPARATOR);
        }

        if (mSaveInfo.HasEndnotes)
        {
            ensureFootnoteSeparator(doc, FootnoteSeparatorType.ENDNOTE_SEPARATOR);
            ensureFootnoteSeparator(doc, FootnoteSeparatorType.ENDNOTE_CONTINUATION_SEPARATOR);
        }
    }

    /// <summary>
    /// A part of the hack that determines whether to expand table formatting.
    /// </summary>
    private static boolean loadFormatSupportsTableStyles(/*LoadFormat*/int loadFormat)
    {
        switch (loadFormat)
        {
            case LoadFormat.DOC:
            case LoadFormat.DOT:
            case LoadFormat.DOCX:
            case LoadFormat.DOCM:
            case LoadFormat.DOTX:
            case LoadFormat.DOTM:
            case LoadFormat.FLAT_OPC:
            case LoadFormat.WORD_ML:
            case LoadFormat.RTF:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true if there is at least one formatting attribute specified for at least one table style.
    /// </summary>
    private static boolean hasTableStyleFormatting(DocumentBase doc)
    {
        for (Style style : doc.getStyles())
        {
            if (style instanceof TableStyle && style.hasFormatting())
                return true;
        }
        return false;
    }

    private void updateDocumentSecurityProperty()
    {
        /*DocumentSecurity*/int security = DocumentSecurity.NONE;

        if (mSaveInfo.Document.getDocPr().WriteProtection.getReadOnlyRecommended())
            security |= DocumentSecurity.READ_ONLY_RECOMMENDED;

        if (mSaveInfo.Document.getDocPr().WriteProtection.isWriteProtected())
            security |= DocumentSecurity.READ_ONLY_ENFORCED;

        // This seems to be what MS Word is doing.
        switch (mSaveInfo.Document.getProtectionType())
        {
            case ProtectionType.ALLOW_ONLY_COMMENTS:
            case ProtectionType.READ_ONLY:
                security |= DocumentSecurity.READ_ONLY_EXCEPT_ANNOTATIONS;
                break;
            case ProtectionType.ALLOW_ONLY_FORM_FIELDS:
            case ProtectionType.ALLOW_ONLY_REVISIONS:
            case ProtectionType.NO_PROTECTION:
                // Do nothing.
                break;
            default:
                throw new IllegalStateException("Unknown protection type.");
        }

        mSaveInfo.Document.getBuiltInDocumentProperties().setSecurity(security);
    }

    public /*override*/ /*VisitorAction*/int visitSectionStart(Section section)
    {
        // A section needs a body with one paragraph to be valid.
        section.ensureMinimum();

        // RK I am not so sure that we need to enforce compliance from 29500 to other file formats.
        // Maybe we just need to make sure all other formats can correctly handle the new values.
        mIso29500ComplianceEnforcer.enforceSectionCompliance(section);

        if ((section.getSectPr().getFootnoteColumns() > 0) && mSaveInfo.isPreIsoTransitionalWordFormat())
        {
            warnOfUnsupportedByFormat(WarningType.MINOR_FORMATTING_LOSS,
                WarningStrings.NOT_SUPPORTED_FOOTNOTE_COLUMNS);
        }

        removeLineNumberingDefaults(section);

        for (int key : SectPr.BORDER_ATTRS)
            validateBorder((Border)section.getSectPr().get(key));

        return VisitorAction.CONTINUE;
    }

    // FOSS

    /// <summary>
    /// Removes specific section global default values.
    /// </summary>
    /// <remarks>
    /// Open office does not handle these defaults correctly.
    /// </remarks>
    private static void removeLineNumberingDefaults(Section section)
    {
        for (int key : G_LINE_NUMBERING_ATTRS)
        {
            if (msObject.equals(section.getSectPr().fetchAttr(key), SectPr.fetchDefaultAttr(key)))
                section.getSectPr().remove(key);
        }
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph paragraph) throws Exception
    {
        // Check whether paragraph list is a legacy list and update document list labels once if needed.
        if (!mListLabelsUpdated && isSaveAction(DocumentValidatorActions.CONVERT_LEGACY_LIST_FORMATTING))
        {
            if (paragraph.isListItem())
            {
                ListLevel level = paragraph.getListLevel();
                // If LegacySpace is specified we need to know the length of the label text to calculate
                // correct leftOfText distance.
                // To calculate the length of the label of text we need to get its formatting
                // that is why we need to call UpdateListLabels method.
                if (level.getLegacy() && (level.getLegacySpace() != 0))
                {
                    mSaveInfo.Document.updateListLabels();
                    mListLabelsUpdated = true;
                }
            }
        }

        // WORDSNET-17459 The paragraph graphic extensions for ODT are located in extra namespace 'xmlns:loext'.
        // We should add this, if there are any paragraphs with such feature.
        mSaveInfo.hasParagraphGraphicsExtension(mSaveInfo.hasParagraphGraphicsExtension() | paragraph.getParagraphBreakRunPr().contains(FontAttr.EFFECT_FILL));

        processParaId(paragraph);

        removeNilShading(paragraph.getParaPr(), ParaAttr.SHADING);

        if (paragraph.isListItemOriginal() || paragraph.isListItemFinal())
            mListValidator.visitParagraphStart(paragraph);

        mUnitConverter.visitParagraphStart(paragraph);

        // WORDSNET-7015 Word removes floating attributes for paragraphs inside text boxes.
        // WORDSNET-12216 It does so even if the paragraph is inside a table.
        // Frames simply cannot be inside text boxes.
        boolean isNestedInsideShape = (paragraph.getAncestor(NodeType.SHAPE) != null);
        if (isNestedInsideShape)
            paragraph.getParaPr().removeFloatingAttrs();

        // Don't write HtmlBlockCollection if there are no paragraphs which are referenced to it.
        // AM. The primary reason is to reduce Gold files affected.
        if (!mSaveInfo.HasHtmlBlockReferences)
        {
            if (paragraph.getParaPr().contains(ParaAttr.HTML_BLOCK_ID))
                mSaveInfo.HasHtmlBlockReferences = true;
        }

        validateFramePr(paragraph);

        for (int key : ParaPr.BORDER_ATTRS)
            validateBorder((Border)paragraph.getParaPr().get(key));

        // WORDSNET-20040 Don't merge paragraphs in header/footer.
        if (!mStoryTypeStack.isInHeaderOrFooter())
            validateDropCapPosition(paragraph);

        validateTabStop(paragraph);

        if (paragraph.getParaPr().getCollapsed() && mSaveInfo.isPreIsoTransitionalWordFormat())
        {
            warnOfUnsupportedByFormat(WarningType.MINOR_FORMATTING_LOSS, WarningStrings.NOT_SUPPORTED_COLLAPSED);
        }

        mRsidTable.add(paragraph.getParaPr().get(ParaAttr.RSID_P));
        mRsidTable.add(paragraph.getParagraphBreakRunPr().get(FontAttr.RSID_R));
        mRsidTable.add(paragraph.getParagraphBreakRunPr().get(FontAttr.RSID_R_PR));

        if (mProgressProcessor != null)
            mProgressProcessor.step();

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Validates and collects unique paragraph identifiers.
    /// </summary>
    private void processParaId(Paragraph para)
    {
        if (para.getParaId() == 0)
            return;

        // Get list for current story type.
        HashSetGeneric<Integer> usedParaId = (HashSetGeneric<Integer>)mSaveInfo.UsedParaIdArray[(int)mStoryTypeStack.getCurrent()];

        if (usedParaId.contains(para.getParaId()))
        {
            // Reset duplicates.
            para.setParaId(0);
        }
        else
        {
            usedParaId.add(para.getParaId());
        }
    }

    /// <summary>
    /// Validates and collects unique comment durable identifiers.
    /// </summary>
    private void processDurableId(Comment comment)
    {
        if (comment.getDurableId() == 0)
            return;

        if (mSaveInfo.UsedCommentDurableIds.contains(comment.getDurableId()))
            comment.setDurableId(0); // Reset duplicates.
        else
            mSaveInfo.UsedCommentDurableIds.add(comment.getDurableId());
    }

    /// <summary>
    /// Validates and collects unique row identifiers.
    /// </summary>
    private void processRowParaId(Row row)
    {
        if (row.getParaId() == 0)
            return;

        if (mSaveInfo.UsedRowParaIds.contains(row.getParaId()))
        {
            // Generate new value.
            for (int i = row.getParaId() + 1; i < Integer.MAX_VALUE; i++)
            {
                if (!mSaveInfo.UsedRowParaIds.contains(i))
                {
                    row.setParaId(i);
                    break;
                }
            }
        }

        mSaveInfo.UsedRowParaIds.add(row.getParaId());
    }

    private static void validateFramePr(Paragraph paragraph)
    {
        ParaPr paraPr = paragraph.getParaPr();

        // andrnosk: WORDSNET-7626 This code violates what is written in specification ECMA 376 (2.3.1.11),
        // but it seems MS Word behaves in this way.
        //
        // AM. We do it because FrameLeft + FrameHorizontalAlignment
        // should be merged into one complex attribute to avoid inheritance problems.
        // The same for FrameTop + FrameVerticalAlignment.
        if (paraPr.getFrameLeft() > 0)
            paraPr.setFrameHorizontalAlignment(HorizontalAlignment.NONE);

        if (paraPr.getFrameTop() > 0)
            paraPr.setFrameVerticalAlignment(VerticalAlignment.NONE);
    }

    private static void validateBorder(Border border)
    {
        if (border != null)
        {
            // WORDSNET-11509 System.ArgumentOutOfRangeException is thrown while saving Docx to Pdf.
            // DistanceFromText has 5 bits, we get only 5 bits
            // WORDSNET-25489 Update directly in RawDistanceFromText instead of DistanceFromText
            // to not affect Border.IsInherited flag.
            border.setRawDistanceFromText((int)border.getDistanceFromText() & Border.MAX_DISTANCE_FROM_TEXT);
            if (border.getLineWidth() > Border.MAX_LINE_WIDTH)
                border.setLineWidthSafe(Border.MAX_LINE_WIDTH);
            ; // WORDSNET-21911
        }
    }

    private static void validateTabStop(Paragraph paragraph)
    {
        if (paragraph.getParentSection() == null)
            return;

        TabStopCollection tabStops = paragraph.getParaPr().getTabStops();

        // WORDSNET-11458 Document.Save generates huge size html for Odt
        //Prepare position of TabStop. Position value is 16bit.
        if (tabStops != null)
        {
            for (int i = 0; i < tabStops.getCount(); i++)
            {
                tabStops.get(i).setPositionTwips((short)(tabStops.get(i).getPositionTwips() & MAX_TAB_STOP_POSITION));
            }
        }
    }

    public /*override*/ /*VisitorAction*/int visitBodyStart(Body body) throws Exception
    {
        mStoryTypeStack.push(body.getStoryType());
        mUnitConverter.visitBodyStart(body);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitBodyEnd(Body body)
    {
        mStoryTypeStack.pop(body.getStoryType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitHeaderFooterStart(HeaderFooter headerFooter) throws Exception
    {
        mStoryTypeStack.push(headerFooter.getStoryType());
        mUnitConverter.visitHeaderFooterStart(headerFooter);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitHeaderFooterEnd(HeaderFooter headerFooter)
    {
        mStoryTypeStack.pop(headerFooter.getStoryType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCommentStart(Comment comment)
    {
        if (removeIfNotInMainStory(comment))
            return VisitorAction.SKIP_THIS_NODE;

        mAnnotationValidator.visitComment(comment);
        mStoryTypeStack.push(comment.getStoryType());

        if (comment.getDone() || (comment.getParentId() != Comment.NO_PARENT))
            OoxmlComplianceInfo.markAsHasDocxExtensions(mSaveInfo.Document);

        if (comment.getDurableId() != 0)
            OoxmlComplianceInfo.markAsHasDocxExtensionsOf(mSaveInfo.Document, MsWordVersionCore.WORD_2016);

        if (comment.isDateTimeUtcDefined() ||
            comment.hasReactions() ||
            comment.isIntelligentPlaceholder() ||
            (mSaveInfo.Document.getCompatibilityOptions().getMswVersion() >= MsWordVersionCore.WORD_2019))
        {
            OoxmlComplianceInfo.markAsHasDocxExtensionsOf(mSaveInfo.Document, MsWordVersionCore.WORD_2019);
        }

        processDurableId(comment);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCommentEnd(Comment comment)
    {
        mStoryTypeStack.pop(comment.getStoryType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCommentRangeStart(CommentRangeStart commentRangeStart)
    {
        if (removeIfNotInMainStory(commentRangeStart))
            return VisitorAction.SKIP_THIS_NODE;

        return mAnnotationValidator.visitCommentRangeStart(commentRangeStart);
    }

    public /*override*/ /*VisitorAction*/int visitCommentRangeEnd(CommentRangeEnd commentRangeEnd)
    {
        if (removeIfNotInMainStory(commentRangeEnd))
            return VisitorAction.SKIP_THIS_NODE;

        return mAnnotationValidator.visitCommentRangeEnd(commentRangeEnd);
    }

    public /*override*/ /*VisitorAction*/int visitFootnoteStart(Footnote footnote)
    {
        // Similar to comments, footnote is an inline node and we have to check what story the footnote is in.
        if (mStoryTypeStack.getCurrent() != StoryType.MAIN_TEXT)
            throw new IllegalStateException("Footnotes are only allowed inside the main text of the document.");

        // Whether the document has footnotes/endnotes is needed later for WordML export.
        if (footnote.getFootnoteType() == FootnoteType.FOOTNOTE)
            mSaveInfo.HasFootnotes = true;
        else
            mSaveInfo.HasEndnotes = true;

        footnote.ensureMinimum();
        mStoryTypeStack.push(footnote.getStoryType());

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFootnoteEnd(Footnote footnote)
    {
        mStoryTypeStack.pop(footnote.getStoryType());

        mFootnoteValidator.visitFootnote(footnote);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCellStart(Cell cell)
    {
        removeNilShading(cell.getCellPr(), CellAttr.SHADING);

        return mTableValidator.visitCellStart(cell);
    }

    public /*override*/ /*VisitorAction*/int visitRowStart(Row row)
    {
        row.ensureTableStyleIsValid();

        processRowParaId(row);

        return mTableValidator.visitRowStart(row);
    }

    public /*override*/ /*VisitorAction*/int visitRowEnd(Row row)
    {
        return mTableValidator.visitRowEnd(row);
    }

    public /*override*/ /*VisitorAction*/int visitTableStart(Table table) throws Exception
    {
        checkIsoProperties(table);

        table.fixNestedTableLayout();

        return mTableValidator.visitTableStart(table);
    }

    /// <summary>
    /// If the specified table has ISO/IEC 29500 properties defined, enforces the document to ISO format or
    /// generates a warning if not possible.
    /// </summary>
    private void checkIsoProperties(Table table)
    {
        // Table.Title and Table.Description are supported only by ISO/IEC 29500 DOCX formats.
        // The properties are not taken directly from table to not affect styles by Table.FetchFirstRowAttr.
        // (The Table style is added into document styles if none yet after calling table.Title or table.Description.)
        if (table.getFirstRow() == null)
            return;

        TablePr tablePr = table.getFirstRow().getTablePr();
        if ((!StringUtil.hasChars(tablePr.getTitle()) &&
             !StringUtil.hasChars(tablePr.getDescription())) ||
            (!mSaveInfo.isPreIsoTransitionalWordFormat() &&
             (mSaveInfo.getSaveFormat() != SaveFormat.ODT) &&
             (mSaveInfo.getSaveFormat() != SaveFormat.OTT)))
        {
            return;
        }

        if (mSaveInfo.isOoxmlFormat() && !isUserDefinedCompliance())
        {
            // Enforce format to ISO.
            Document document = mSaveInfo.Document;
            if (document.getComplianceInfo() == null)
                document.setComplianceInfo(new OoxmlComplianceInfo());
            document.getComplianceInfo().markAsIsoTransitional();
        }
        else
        {
            // Generate warnings.
            if (StringUtil.hasChars(tablePr.getTitle()))
                warnOfUnsupportedByFormat(WarningType.DATA_LOSS, WarningStrings.NOT_SUPPORTED_TABLE_TITLE);
            if (StringUtil.hasChars(tablePr.getDescription()))
                warnOfUnsupportedByFormat(WarningType.DATA_LOSS, WarningStrings.NOT_SUPPORTED_TABLE_DESCRIPTION);
        }
    }

    public /*override*/ /*VisitorAction*/int visitTableEnd(Table table) throws Exception
    {
        // Do not re-calculate table grid until adjacent tables are joined.
        /*VisitorAction*/int result = VisitorAction.CONTINUE;
        Table combinedTable = mTableMerger.joinAdjacentTablesDiscardGrids(table);
        if (combinedTable != null)
        {
            // WORDSNET-21630 Update table layout before direct attributes will be saved to "SysDirectAttrs".
            DocumentPostLoader.updateNestedTableGrids(combinedTable, getNestedTables(), false);

            // WORDSNET-5262 Expander should be invoked at table end to preserve any resilience corrections made (for example WORDSNET-905).
            TableFormattingExpander tableFormattingExpander =
                DictionaryExtension.getValueOrNull(mTableFormattingExpanders, combinedTable.getDocument());

            if (tableFormattingExpander != null)
                tableFormattingExpander.expand(combinedTable);

            // WORDSNET-20640 Validate vertical position of the table.
            // Note, Word does this for all legacy formats, except of RTF.
            if (mSaveInfo.isLegacyFormat() && (mSaveInfo.getSaveFormat() != SaveFormat.RTF))
                validateTableVerticalPosition(combinedTable);

            result = mTableValidator.visitTableEnd(combinedTable);
        }

        return result;
    }

    public /*override*/ /*VisitorAction*/int visitShapeStart(Shape shape) throws Exception
    {
        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            forceShowingPlaceholderIfNeeded();

            // WORDSNET-24308 Update compliance to properly write required namespaces.
            if (mSaveInfo.isOoxmlFormat())
            {
                if (shape.getRunPr().getAlternateContent() != null)
                    OoxmlComplianceInfo.markAsHasDrawingExtensions(mSaveInfo.Document);
            }
        }

        /*VisitorAction*/int result = mShapeValidator.visitShapeStart(shape, mStoryTypeStack);

        if (result == VisitorAction.CONTINUE)
            mStoryTypeStack.push(shape.getStoryType());

        mRsidTable.add(shape.getRunPr().getRsidR());

        return result;
    }

    public /*override*/ /*VisitorAction*/int visitShapeEnd(Shape shape)
    {
        /*VisitorAction*/int result = mShapeValidator.visitShapeEnd(shape);

        mStoryTypeStack.pop(shape.getStoryType());
        return result;
    }

    public /*override*/ /*VisitorAction*/int visitGroupShapeStart(GroupShape groupShape) throws Exception
    {
        mRsidTable.add(groupShape.getRunPr().getRsidR());

        return mShapeValidator.visitGroupShapeStart(groupShape, mStoryTypeStack);
    }

    public /*override*/ /*VisitorAction*/int visitGroupShapeEnd(GroupShape groupShape)
    {
        return mShapeValidator.visitGroupShapeEnd(groupShape);
    }

    public /*override*/ /*VisitorAction*/int visitOfficeMathStart(OfficeMath officeMath)
    {
        /*VisitorAction*/int result = mOfficeMathToShapeConverter.visitOfficeMathStart(officeMath, this);
        return (result != VisitorAction.SKIP_THIS_NODE) ?
            mMathValidator.visitOfficeMathStart(officeMath) : // Only do validation of OfficeMath node if we didn't turn it into a shape.
            result;
    }

    public /*override*/ /*VisitorAction*/int visitOfficeMathEnd(OfficeMath officeMath) throws Exception
    {
        mOfficeMathToShapeConverter.visitOfficeMathEnd(officeMath, this);
        return mMathValidator.visitOfficeMathEnd(officeMath);
    }

    public /*override*/ /*VisitorAction*/int visitRun(Run run) throws Exception
    {
        nilBorderHack(run);

        // WORDSNET-27050 Handle broken surrogate pair.
        if (isBrokenSurrogatePair(run))
        {
            Run nextRun = (Run)run.getNextSibling();

            run.setText(msString.format("{0}{1}", run.getText(), nextRun.getText().charAt(0)));
            nextRun.setText(msString.remove(nextRun.getText(), 0, 1));
        }

        if (run.getRunPr().contains(FontAttr.RUBY))
        {
            mRubyConverter.convert(run);
        }
        else
        {
            // Runs with empty text cause rendering to throw exception. This is a quick fix. I think good enough.
            if ("".equals(run.getText()))
                run.remove();
        }

        if (mSaveInfo.SaveOptions.getUpdateAmbiguousTextFont())
            ComplexScriptRunUpdater.doProcess(run);

        mFootnoteValidator.visitRun(run, mStoryTypeStack);

        mRsidTable.add(run.getRunPr().getRsidR());
        mRsidTable.add(run.getRunPr().getRsidRPr());

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart) throws Exception
    {
        if (fieldStart.getFieldType() == FieldType.FIELD_HYPERLINK)
            forceShowingPlaceholderIfNeeded();

        mFieldValidator.visitFieldStart(fieldStart);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd) throws Exception
    {
        mFieldValidator.visitFieldEnd(fieldEnd);

        // WORDSNET-16333 Convert EQ field to OfficeMath if required.
        if (fieldEnd.getFieldType() == FieldType.FIELD_EQUATION)
            mEQFieldConverter.convert(fieldEnd.getField());

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator)
    {
        // Normally, OLE objects are shapes and not stored in fields in the model.
        // There are only a few cases where linked OLE objects are stored in a field.
        // Because these are linked objects only, add them to AllOleObjects.
        OleObject oleObject = fieldSeparator.getOleObject();
        if (oleObject != null)
            mSaveInfo.AllOleObjects.set(OleUtil.getMsWordId(oleObject), oleObject.getData());

        mFieldValidator.visitFieldSeparator(fieldSeparator);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFormField(FormField formField)
    {
        forceShowingPlaceholderIfNeeded();

        mFieldValidator.visitFormField(formField);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Informs the acceptor node if deleted nodes should be given to the visitor.
    /// </summary>
    /// <remarks>
    /// DocumentValidator does not visit deleted nodes.
    /// </remarks>
    /*override*/ boolean getVisitsDeletedNodes() { return false; }

    public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart)
    {
        // WORDSNET-25159 Bookmark part might be removed during table validation.
        if (isInsideEmptyTable(bookmarkStart))
            return VisitorAction.CONTINUE;

        forceShowingPlaceholderIfNeeded();

        mBookmarkValidator.visitBookmarkStart(bookmarkStart, mStoryTypeStack.getCurrent());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd bookmarkEnd)
    {
        // WORDSNET-25159 Bookmark part might be removed during table validation.
        if (isInsideEmptyTable(bookmarkEnd))
            return VisitorAction.CONTINUE;

        mBookmarkValidator.visitBookmarkEnd(bookmarkEnd, mStoryTypeStack.getCurrent());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitEditableRangeStart(EditableRangeStart editableRangeStart)
    {
        return mAnnotationValidator.visitEditableRangeStart(editableRangeStart);
    }

    public /*override*/ /*VisitorAction*/int visitEditableRangeEnd(EditableRangeEnd editableRangeEnd)
    {
        return mAnnotationValidator.visitEditableRangeEnd(editableRangeEnd);
    }

    public /*override*/ /*VisitorAction*/int visitSmartTagStart(SmartTag smartTag)
    {
        // WORDSNET-7105 An RTF document has smart tags without namespaces, opening it in Word is okay.
        // But when we convert to DOC or DOCX, then the it cannot be opened in MS Word.
        // It seems uri is required, so let's provide default one.
        if (!StringUtil.hasChars(smartTag.getUri()))
            smartTag.setUri("urn:schemas-microsoft-com:office:smarttags");

        addSmartTagType(smartTag);

        // Smart tag namespaces do not seem to be added to attached schemas by MS Word, we do the same.
        addXmlNamespace(smartTag.getUri(), false, true);
        addXmlNamespace(smartTag.getProperties(), false, true);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagStart(StructuredDocumentTag sdt) throws Exception
    {
        validateStructuredDocumentTagStart(sdt);

        mSdtIds.add(sdt.getId());

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Checks the structured document tag and makes any necessary changes or issues warnings if needed.
    /// </summary>
    private void validateStructuredDocumentTagStart(StructuredDocumentTag sdt) throws Exception
    {
        // Custom XML validator should be called before any other code in this method.
        mCustomXmlValidator.visitStructuredDocumentTagStart(sdt, mXmlMappingContext);

        forceShowingPlaceholderIfNeeded();
        mSdtStack.push(sdt);

        // When saving to OOXML format we need compliance data in writers and DocxBuilder to properly construct document.
        if (mSaveInfo.isOoxmlFormat() && sdt.isDocxExtension())
        {
            if (!isUserDefinedCompliance())
                OoxmlComplianceInfo.markAsHasDocxExtensions(mSaveInfo.Document);

            // MS Word asks to remove mapping of a rich-text SDT on saving if compatibility mode is less than 15.
            // It does not happen with other [MS-DOCX] extensions.
            CompatibilityOptions compatibility = mSaveInfo.Document.getCompatibilityOptions();
            if ((sdt.getSdtType() == SdtType.RICH_TEXT) && !sdt.getXmlMapping().isEmpty() &&
                !compatibility.isOptimized() && (compatibility.getMswVersion() < MsWordVersionCore.WORD_2013))
            {
                compatibility.optimizeFor(MsWordVersion.WORD_2013);
            }

            if (!sdt.getXmlMapping().isEmpty() && StringUtil.hasChars(sdt.getXmlMapping().getStoreItemChecksum()))
                OoxmlComplianceInfo.markAsHasDocxExtensionsOf(mSaveInfo.Document, MsWordVersionCore.WORD_2019);
        }
        // In contrast with checkbox SDT, we do not allow entity picker creation. So do not change compliance,
        // just generate a warning. The same is with Web Extensions and Appearance.
        if (mSaveInfo.isPreIsoTransitionalWordFormat())
        {
            if (sdt.getSdtType() == SdtType.ENTITY_PICKER)
                warnOfUnsupportedByFormat(WarningType.DATA_LOSS, WarningStrings.NOT_SUPPORTED_ENTITY_PICKER);

            if (sdt.getWebExtensionRelationship() != SdtWebExtensionRelationship.NONE)
                warnOfUnsupportedByFormat(WarningType.DATA_LOSS, WarningStrings.NOT_SUPPORTED_WEB_EXTENSIONS);

            if (sdt.getAppearance() != SdtAppearance.DEFAULT)
                warnOfUnsupportedByFormat(WarningType.MINOR_FORMATTING_LOSS, WarningStrings.NOT_SUPPORTED_SDT_APPEARANCE);
        }

        // WORDSNET-25482 Update placeholder reference regardless of edit revisions.
        sdt.updatePlaceholderReference(false);

        // WORDSNET-16209 Do not update deleted SDTs.
        if (((ITrackableNode)sdt).getDeleteRevision() == null)
        {
            // WORDSNET-23287, WORDSNET-23487 Add required namespaces when checksum should be written.
            if (!sdt.getXmlMapping().isEmpty() && StringUtil.hasChars(sdt.getXmlMapping().getStoreItemChecksum()))
                OoxmlComplianceInfo.markAsHasDocxExtensionsOf(mSaveInfo.Document, MsWordVersionCore.WORD_2019);

            if (mSaveInfo.isOoxmlFormat())
                validateSdtContent(sdt);
        }
    }

    /// <summary>
    /// Validates contents of the specified SDT and fixes it if necessary.
    /// </summary>
    private static void validateSdtContent(StructuredDocumentTag sdt)
    {
        // WORDSNET-23826 If an SDT ends with a table, all the cells of the last row of which are merged vertically,
        // MS Word gives an error when opening the document. Append an empty paragraph in this case.

        Table table = ms.as(sdt.getLastChild(), Table.class);
        if (table == null)
            return;

        for (Cell cell : (Iterable<Cell>) table.getLastRow().getCells())
        {
            if (cell.getCellFormat().getVerticalMerge() != CellMerge.PREVIOUS)
                return;
        }

        sdt.appendChild(new Paragraph(sdt.getDocument()));
    }

    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagEnd(StructuredDocumentTag sdt)
    {
        mSdtStack.pop();

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagRangeStart(StructuredDocumentTagRangeStart sdtRangeStart) throws Exception
    {
        validateStructuredDocumentTagStart(sdtRangeStart.getInternalSdt());
        visitStructuredDocumentTagEnd(sdtRangeStart.getInternalSdt());

        mSdtRangeStack.push(sdtRangeStart);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagRangeEnd(StructuredDocumentTagRangeEnd sdtRangeEnd)
    {
        validateStructuredDocumentTagRangeEnd(sdtRangeEnd);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Checks the structured document tag range end and regenerates range ID if necessary. If there is an invalid
    /// SDT range start/end sequence, the invalid nodes are removed.
    /// </summary>
    private void validateStructuredDocumentTagRangeEnd(StructuredDocumentTagRangeEnd sdtRangeEnd)
    {
        if (!isOpenSdtRange(sdtRangeEnd.getId()))
        {
            // Wrong SDT range nesting: remove the range end.
            sdtRangeEnd.remove();
            return;
        }

        do
        {
            StructuredDocumentTagRangeStart start = mSdtRangeStack.pop();
            if (start.getId() == sdtRangeEnd.getId())
            {
                if (mSdtIds.contains(sdtRangeEnd.getId()))
                {
                    start.setId(UniqueIdManager.generateInteger());
                    sdtRangeEnd.setIdInternal(start.getId());
                }

                mSdtIds.add(start.getId());
                break;
            }

            // Wrong SDT range nesting: remove the range start.
            start.remove();
        }
        while (mSdtRangeStack.getCount() > 0);
    }

    /// <summary>
    /// Gets a flag indicating whether an SDT range start node with the specified identifier exists in the open SDT
    /// range stack.
    /// </summary>
    private boolean isOpenSdtRange(int rangeId)
    {
        for (StructuredDocumentTagRangeStart start : mSdtRangeStack)
        {
            if (start.getId() == rangeId)
                return true;
        }

        return false;
    }

    /// <summary>
    /// Adds a smart tag to the collection of smart tag types in <see cref="SaveInfo"/>.
    /// We only need to store one instance to provide us with uri and element name for writing later on.
    /// </summary>
    private void addSmartTagType(SmartTag smartTag)
    {
        String key = smartTag.getUri() + smartTag.getElement();
        mSaveInfo.SmartTagTypes.set(key, smartTag);
    }

    /// <summary>
    /// Adds an XML namespace to the collection of namespaces in <see cref="SaveInfo"/> if this namespace has not yet been encountered.
    /// </summary>
    /// <param name="uri">The namespace used.</param>
    /// <param name="isAttachedSchema">True if this namespace is also to be added to the list of attached schemas for DOCX.</param>
    /// <param name="isSmartTag">True if origin of namespace is a SmartTag.</param>
    private void addXmlNamespace(String uri, boolean isAttachedSchema, boolean isSmartTag)
    {
        if (!StringUtil.hasChars(uri))
            return;

        if (!mSaveInfo.XmlNamespaces.containsKey(uri))
        {
            // It is enough to just use the count as an id for the entry.
            // But the ID=0 is reserved by MS Word, it seems to indicate "the parent element's" namespace.
            int id = mSaveInfo.XmlNamespaces.getCount() + 1;
            mSaveInfo.XmlNamespaces.add(uri, id);
            mSaveInfo.IsSmartTagOriginated.add(id, isSmartTag);
        }

        if (isAttachedSchema)
            mSaveInfo.AttachedSchemas.set(uri, uri);
    }

    private void addXmlNamespace(CustomXmlPropertyCollection properties, boolean isAttachedSchema, boolean isSmartTag)
    {
        for (CustomXmlProperty prop : properties)
            addXmlNamespace(prop.getUri(), isAttachedSchema, isSmartTag);
    }

    /// <summary>
    /// If the node is not in the main text story, removes the node and returns true.
    /// </summary>
    private boolean removeIfNotInMainStory(Node node)
    {
        if (mStoryTypeStack.getCurrent() != StoryType.MAIN_TEXT)
        {
            node.remove();
            warn(WarningType.DATA_LOSS, WarningSource.VALIDATOR,
            msString.format(WarningStrings.DOCUMENT_VALIDATOR_WRONG_STORY, Node.nodeTypeToString(node.getNodeType())));
            return true;
        }
        return false;
    }

    /// <summary>
    /// Validates MS Word customization (allocated commands, key maps and toolbars).
    /// </summary>
    /// <param name="doc"></param>
    private void validateCustomization(Document doc)
    {
        if (doc.getAllocatedCommands() == null)
            return;

        for (AllocatedCommand command : doc.getAllocatedCommands())
        {
            // None Fci value is not allowed.
            if (command.FciBasedOn == FixedCommandIdentifier.NONE)
            {
                warn(WarningType.DATA_LOSS, WarningSource.UNKNOWN, WarningStrings.DOCUMENT_VALIDATOR_ALLOCATED_COMMAND_INVALID);

                // Unfortunately we can't just remove invalid command because there can be toolbar referring to this command.
                // Currently we read/write toolbar customization only as raw bytes.
                // So there is just quick workaround - we change invalid command to "ApplyStyle Normal" command.
                command.FciBasedOn = FixedCommandIdentifier.APPLY_STYLE_NAME;
                command.ArgValue = new byte[] { (byte) 0x01, (byte) 0x00 /* style by sti */, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 /* sti of Normal */};
            }
        }
    }

    /// <summary>
    /// Returns true if all bits of the specified save actions are set in the current save operation.
    /// </summary>
    private boolean isSaveAction(/*DocumentValidatorActions*/int actions)
    {
        return (mSaveActions & actions) == actions;
    }

    /// <summary>
    /// Process placeholder usage information and remove unused placeholders.
    /// </summary>
    /// <remarks>
    /// This actually affects a few existing GOLD documents, that contain lost placeholders. Lost placeholders get removed by this function, because their
    /// reference count tracked in SdtPlaceholderManager is 0.
    /// This follows our logic about placeholders: after we delete Sdt, we don't delete placeholders right away, but do this here in validator.
    /// So here in DocumentValidator we can not distinguish between AW-created placeholders and non-AW created placeholders.
    /// </remarks>
    private void removeUnusedSdtPlaceholders(Document doc)
    {
        SdtPlaceholderManager placeholderManager = doc.getSdtPlaceholderManager();
        if (!placeholderManager.isGlossaryEmpty())
        {
            GlossaryDocument glossary = doc.getGlossaryDocument();
            if ((glossary != null) && !glossary.getSdtPlaceholderManager().isGlossaryEmpty())
                placeholderManager.appendPlaceholderReferences(glossary.getSdtPlaceholderManager().getPlaceholdersUsed());

            if (placeholderManager.removeUnusedPlaceholders())
                warn(WarningType.DATA_LOSS, WarningSource.VALIDATOR, WarningStrings.DOCUMENT_VALIDATOR_SDT_PLACEHOLDER_UNUSED);
        }
    }

    /// <summary>
    /// Concatenates DropCap frame paragraph with next paragraph if DropCapPosition equals None.
    /// </summary>
    private static void validateDropCapPosition(Paragraph paragraph)
    {
        ParaPr paraPr = paragraph.getParaPr();
        if (paraPr.containsKey(ParaAttr.DROP_CAP_POSITION) && (paraPr.getDropCapPosition() == DropCapPosition.NONE))
        {
            Paragraph nextPara = (Paragraph)paragraph.nextSiblingOfType(NodeType.PARAGRAPH);

            if (nextPara != null)
            {
                while (paragraph.hasChildNodes())
                {
                    Node nextNode = paragraph.getFirstChild();
                    // We have to restore run formatting, that is why copy RunPr from
                    // the first run of the next paragraph.
                    if ((nextNode != null) && (nextNode.getNodeType() == NodeType.RUN))
                    {
                        ((Run)nextNode).setRunPr(nextPara.getFirstRun() != null
                                                    ? nextPara.getFirstRun().getRunPr()
                                                    : nextPara.getParagraphBreakRunPr());
                    }

                    nextPara.prependChild(nextNode);
                }
                paragraph.remove();
            }
        }
    }

    private static void removeNilShading(AttrCollection attrs, int key)
    {
        Shading shading = (Shading)attrs.get(key);
        if ((shading != null) && (shading.getTexture() == TextureIndex.TEXTURE_NIL))
            attrs.remove(key);
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int type, /*WarningSource*/int source, String description)
    {
        if (getWarningCallback() != null)
            getWarningCallback().warning(new WarningInfo(type, source, description));
    }

    /// <summary>
    /// Logs a warning of a feature, which is not supported by saving format, to the user-provided warning callback.
    /// </summary>
    /// <param name="warningType">
    /// Generating warning type.
    /// </param>
    /// <param name="description">
    /// Warning text that may contain a format item, which will be replaced with saving format name.
    /// </param>
    private void warnOfUnsupportedByFormat(/*WarningType*/int warningType, String description)
    {
        String format = SaveFormat.toString(mSaveInfo.getSaveFormat());
        if (mSaveInfo.isOoxmlFormat())
        {
            switch (getOoxmlCompliance())
            {
                case OoxmlComplianceCore.ECMA_376:
                    format = ECMA_376_FORMAT_TITLE;
                    break;
                case OoxmlComplianceCore.ISO_TRANSITIONAL:
                    format = "OOXML ISO/IEC 29500 Transitional";
                    break;
                case OoxmlComplianceCore.ISO_STRICT:
                    format = "OOXML ISO/IEC 29500 Strict";
                    break;
                default:
                    Debug.assertTrue(false, "Unknown value.");
                    break;
            }
        }

        warn(warningType, WarningSource.VALIDATOR, msString.format(description, format));
    }

    /// <summary>
    /// Logs a warning of a setting that is not supported by saving format.
    /// </summary>
    private void warnOfUnsupportedSetting(String settingName)
    {
        warnOfUnsupportedByFormat(WarningType.DATA_LOSS, msString.format(WarningStrings.NOT_SUPPORTED_SETTING, settingName));
    }

    /// <summary>
    /// Logs a warning of a setting that is not supported by Aspose.Words for saving format.
    /// </summary>
    private void warnOfUnsupportedSettingByAw(String settingName)
    {
        warnOfUnsupportedByFormat(WarningType.DATA_LOSS, msString.format(WarningStrings.NOT_SUPPORTED_SETTING_BY_AW, settingName));
    }

    /// <summary>
    /// Forces ShowingPlaceholder property for parent SDT.
    /// </summary>
    /// <remarks>
    /// WORDSNET-17319 For unknown reason Word is unable to open document if plain text SDT contains some nodes and
    /// seems this can be fixed by writing ShowingPlaceholder property.
    /// </remarks>
    private void forceShowingPlaceholderIfNeeded()
    {
        if (mSdtStack.getCount() > 0)
            mSaveInfo.ForceShowingPlaceholder.add(mSdtStack.peek());
    }

    /// <summary>
    /// Validates vertical position of a specified table.
    /// </summary>
    private static void validateTableVerticalPosition(Table table)
    {
        Row firstRow = table.getFirstRow();
        if (firstRow == null)
            return;

        // Check if table is vertically anchored to a paragraph, then this paragraph must exist.
        // If it is not so, then create a new one before the table.
        if (firstRow.getTablePr().getRelativeVerticalPosition() == RelativeVerticalPosition.PARAGRAPH)
        {
            Node prevNode = table.getPreviousNonAnnotationSibling();
            if ((prevNode != null) && (prevNode.getNodeType() != NodeType.PARAGRAPH))
            {
                Paragraph paragraph = new Paragraph(table.getDocument());
                table.getParentNode().insert(paragraph, prevNode, true);
                paragraph.getParagraphBreakRunPr().setHidden(AttrBoolEx.TRUE);
            }
        }
    }

    /// <summary>
    /// Removes Nil border from run.
    /// </summary>
    /// <remarks>
    /// This is hack solution for WORDSNET-22140 and WORDSNET-23785
    /// Actually seems we need to make Nil border public eventually.
    /// </remarks>
    private void nilBorderHack(Run run)
    {
        if (mSaveInfo.getSaveFormat() == SaveFormat.DOCX)
        {
            if (run.getRunPr().containsKey(FontAttr.BORDER))
            {
                Border border = run.getRunPr().getBorder();

                if (border.isNil())
                    border.isNil(false);
            }
        }
    }

    /// <summary>
    /// Checks if sequential runs contains broken surrogate pair.
    /// </summary>
    private static boolean isBrokenSurrogatePair(Run run)
    {
        // Check if run ends with high surrogate.
        if ((run.getText().length() == 0) || !Character.isHighSurrogate(run.getText().charAt(run.getText().length() - 1)))
            return false;

        Run nextRun = ms.as(run.getNextSibling(), Run.class);

        if (nextRun == null)
            return false;

        // Check if next run start with low surrogate.
        return (nextRun.getText().length() > 0) && Character.isLowSurrogate(nextRun.getText().charAt(0));
    }

    /// <summary>
    /// Maps converted OfficeMath objects to original ShapeBase objects
    /// when document is saved to format which doesn't support OMML.
    /// Keys are instances of <see cref="OfficeMath"/>.
    /// Values are instances of <see cref="Shape"/>.
    /// </summary>
    Map<OfficeMath, Shape> getConvertedOfficeMathToShape() { return mOfficeMathToShapeConverter.getConvertedOfficeMathToShape(); }

    /// <summary>
    /// Holds Shape converted from OfficeMath when document is saved to format which doesn't support OfficeMath.
    /// </summary>
    Map<ShapeBase, OfficeMath> getShapeToConvertedOfficeMath() { return mOfficeMathToShapeConverter.getShapeToConvertedOfficeMath(); }

    private IWarningCallback getWarningCallback() { return mSaveInfo.Document.getWarningCallback(); }

    /// <summary>
    /// Specifies the OOXML version for the output document on saving as DOCX formats.
    /// </summary>
    private /*OoxmlComplianceCore*/int getOoxmlCompliance()
    {
        return mSaveInfo.getOoxmlCompliance();
    }

    /// <summary>
    /// Gets a flag indicating that user has set compliance explicitly.
    /// </summary>
    private boolean isUserDefinedCompliance()
    {
        OoxmlSaveOptions ooxmlSaveOptions = ms.as(mSaveInfo.SaveOptions, OoxmlSaveOptions.class);
        return (ooxmlSaveOptions != null) && ooxmlSaveOptions.getUserSetCompliance();
    }

    /// <summary>
    /// Returns true if we need to update font info collection to used fonts.
    /// </summary>
    private boolean isUpdateFontInfos()
    {
        // WORDSNET-14322 In addition, let's update font infos when embed fonts into DOCX formats.
        // It looks like MS Word behaves similarly.
        return isSaveAction(DocumentValidatorActions.UPDATE_FONT_INFO) ||
            (mSaveInfo.isDocxFormat() && mSaveInfo.Document.getDocPr().EmbedTrueTypeFonts);
    }

    private static void ensureFootnoteSeparator(DocumentBase doc, /*FootnoteSeparatorType*/int footnoteSeparatorType)
    {
        if (doc.getFootnoteSeparators().getByFootnoteSeparatorType(footnoteSeparatorType) != null)
            return;

        FootnoteSeparator footnoteSeparator = new FootnoteSeparator(doc, footnoteSeparatorType);

        char controlChar = (footnoteSeparatorType == FootnoteSeparatorType.FOOTNOTE_CONTINUATION_SEPARATOR) ||
                           (footnoteSeparatorType == FootnoteSeparatorType.ENDNOTE_CONTINUATION_SEPARATOR)
            ? ControlChar.FOOTNOTE_CONTINUATION_CHAR
            : ControlChar.FOOTNOTE_SEPARATOR_CHAR;

        // The SpaceAfter/LineSpacing values are taken from the Word then it adds the missing footnotes.
        Paragraph para = footnoteSeparator.appendChild(new Paragraph(doc));

        para.getParaPr().setSpaceAfter(0);
        para.getParaPr().setLineSpacing(240);
        para.appendChild(new SpecialChar(doc, controlChar, new RunPr()));

        doc.getFootnoteSeparators().add(footnoteSeparator);
    }

    /// <summary>
    /// Checks if node is located inside empty table.
    /// </summary>
    private static boolean isInsideEmptyTable(Node node)
    {
        Table parentTable = ms.as(node.getParentNode(), Table.class);

        return (parentTable != null) && (parentTable.getRows().getCount() == 0);
    }

    private SavingProgressProcessor mProgressProcessor;

    // *** These fields are common for the whole document.
    private SaveInfo mSaveInfo;
    private ShapeValidator mShapeValidator;
    private CustomXmlValidator mCustomXmlValidator;
    /// <summary>
    /// Handle all pre-save ISO29500 specifics in a dedicated class.
    /// This mainly includes manipulating model values to enforce compliance to ISO29500 or older formats.
    /// </summary>
    private Iso29500ComplianceEnforcer mIso29500ComplianceEnforcer;
    /// <summary>
    /// Specifies the actions/flags that the validator must take into account because it is saving in a particular save format.
    /// </summary>
    private /*DocumentValidatorActions*/int mSaveActions;

    // *** These fields are reset for the main document and then for the glossary document.
    private BookmarkValidator mBookmarkValidator;
    private AnnotationValidator mAnnotationValidator;
    private FieldValidator mFieldValidator;
    private MathValidator mMathValidator;
    private TableValidator mTableValidator;
    private ListValidator mListValidator;
    private FootnoteValidator mFootnoteValidator;

    /// <summary>
    /// Font names used in the document.
    /// </summary>
    private ISetGeneric<String> mUsedFontNames;

    /// <summary>
    /// Maintains a stack of story types. Allows to detect some invalid model structures,
    /// such as a comment object outside of the main story.
    /// </summary>
    private StoryTypeStack mStoryTypeStack;

    /// <summary>
    /// Collection of TableFormattingExpander objects used to expand/revert in the main document and glossary document.
    /// </summary>
    private HashMap<DocumentBase, TableFormattingExpander> mTableFormattingExpanders;

    /// <summary>
    /// Converts units spacing/indents into twips spacing/indents.
    /// </summary>
    private UnitConverter mUnitConverter;

    /// <summary>
    /// Knows when to convert office math to shapes.
    /// </summary>
    private OfficeMathToShapeConverter mOfficeMathToShapeConverter;
    /// <summary>
    /// List of adjacent tables which will be merged at document end.
    /// </summary>
    private /*final*/ TableMerger mTableMerger = new TableMerger();

    /// <summary>
    /// Stores nested tables stack for the grid update.
    /// </summary>
    private msStack<Table> getNestedTables() { return mNestedTables; }

    private /*final*/ msStack<Table> mNestedTables = new msStack<Table>();

    private RubyConverter mRubyConverter;

    private EQFieldConverter mEQFieldConverter;

    private RsidTable mRsidTable;

    private /*final*/ msStack<StructuredDocumentTag> mSdtStack = new msStack<StructuredDocumentTag>();
    private /*final*/ HashSetGeneric<Integer> mSdtIds = new HashSetGeneric<Integer>();
    private /*final*/ msStack<StructuredDocumentTagRangeStart> mSdtRangeStack =
        new msStack<StructuredDocumentTagRangeStart>();

    private XmlMappingContext mXmlMappingContext;

    /// <summary>
    /// Indicates list label update process has been executed.
    /// </summary>
    private boolean mListLabelsUpdated;

    static final String ECMA_376_FORMAT_TITLE = "OOXML ECMA-376";

    // [MS-DOC]: Word Binary File Format (.doc) Structure Specification
    // The XAS value is a 16-bit signed integer that specifies horizontal distance in twips. This value MUST
    // be greater than or equal to -31680 and less than or equal to 31680.
    private static final int MAX_TAB_STOP_POSITION = 0xFFFF;

    private static /*final*/ int[] G_LINE_NUMBERING_ATTRS = new int[]
    {
        SectAttr.LINE_STARTING_NUMBER,
        SectAttr.LINE_NUMBER_DISTANCE_FROM_TEXT,
        SectAttr.LINE_NUMBER_COUNT_BY,
        SectAttr.LINE_NUMBER_RESTART_MODE
    };

    private static /*DocumentValidatorActions*/int fetchSaveActions(/*SaveFormat*/int saveFormat)
    {
        /*DocumentValidatorActions*/int result = 0;

        RefInt referenceToResult = new RefInt(result);
        boolean outRefCondition0 = !msDictionary.tryGetValue(G_SAVE_ACTIONS, saveFormat, /*out*/ referenceToResult);
        result = referenceToResult.get();
        if (outRefCondition0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "saveFormat");

        return result;
    }

    /// <summary>
    /// Key is <see cref="SaveFormat"/>, value is <see cref="DocumentValidatorActions"/>.
    /// </summary>
    private static /*final*/ HashMap<Integer, Integer> G_SAVE_ACTIONS =
        new HashMap<Integer, Integer>();

    static/* DocumentValidator()*/
    {
        final /*DocumentValidatorActions*/int DOC_ACTIONS =
            DocumentValidatorActions.EXPAND_TABLE_STYLES |        // Although we support table styles in DOC, we need to write both expanded and direct, therefore expand.
            DocumentValidatorActions.CONVERT_DML_PICTURES_TO_VML |  // DOC does not support DML.
            DocumentValidatorActions.EXPLICIT_AUTO_PREFERRED_WIDTH |
            DocumentValidatorActions.WORD_TABLE_LIMITS;

        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.DOC, DOC_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.DOT, DOC_ACTIONS);

        final /*DocumentValidatorActions*/int DOCX_ACTIONS =
            DocumentValidatorActions.NORMALIZE_HORIZONTAL_MERGE |
            DocumentValidatorActions.WORD_TABLE_LIMITS;

        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.DOCX, DOCX_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.DOTX, DOCX_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.DOCM, DOCX_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.DOTM, DOCX_ACTIONS);

        final /*DocumentValidatorActions*/int FOPC_ACTIONS =
            DocumentValidatorActions.NORMALIZE_HORIZONTAL_MERGE |
            DocumentValidatorActions.WORD_TABLE_LIMITS;
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.FLAT_OPC, FOPC_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.FLAT_OPC_MACRO_ENABLED, FOPC_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.FLAT_OPC_TEMPLATE, FOPC_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED, FOPC_ACTIONS);

        final /*DocumentValidatorActions*/int RTF_ACTIONS =
            DocumentValidatorActions.EXPAND_TABLE_STYLES | // We don't support table styles in RTF and need to expand.
            DocumentValidatorActions.CONVERT_DML_PICTURES_TO_VML | // RTF does not support DML.
            DocumentValidatorActions.UPDATE_LIST_LABELS | // We want to be nice to users and write list labels here.
            DocumentValidatorActions.EXPLICIT_AUTO_PREFERRED_WIDTH;

        // AM. It is interesting that Word allows table to have more than 63 cells for RTF format.
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.RTF, RTF_ACTIONS);

        // We support table styles in WordML hence no need to expand them.
        final /*DocumentValidatorActions*/int WML_ACTIONS =
            DocumentValidatorActions.CONVERT_DML_PICTURES_TO_VML |  // WML does not support DML.
            DocumentValidatorActions.UPDATE_LIST_LABELS |         // We want to be nice to users and write list labels here.
            DocumentValidatorActions.NORMALIZE_HORIZONTAL_MERGE |
            DocumentValidatorActions.WORD_TABLE_LIMITS;
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.WORD_ML, WML_ACTIONS);

        // RK I think expanding default is not necessary here because they are resolved during normal attribute expansion. Should we try?
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.PDF, DocumentValidatorActions.RENDERING |
                                         DocumentValidatorActions.LONG_BOOKMARK_NAMES);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.XPS, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.OPEN_XPS, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.XAML_FIXED, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.SVG, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.HTML_FIXED, DocumentValidatorActions.RENDERING);

        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.TIFF, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.PNG, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.BMP, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.EMF, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.JPEG, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.GIF, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.EPS, DocumentValidatorActions.RENDERING);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.WEB_P, DocumentValidatorActions.RENDERING);
        // When saving to a HTML-based format, DML shapes are handled by the HTML writer itself and the document validator
        // must not replace them with VML fallback shapes.
        final /*DocumentValidatorActions*/int HTML_BASED_ACTIONS =
            DocumentValidatorActions.NON_MS_WORD_FORMAT & ~DocumentValidatorActions.CONVERT_DML_PICTURES_TO_VML;
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.HTML, HTML_BASED_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.MHTML, HTML_BASED_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.EPUB, HTML_BASED_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.AZW_3, HTML_BASED_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.MOBI, HTML_BASED_ACTIONS);

        final /*DocumentValidatorActions*/int ODT_ACTIONS = DocumentValidatorActions.NON_MS_WORD_FORMAT |
            DocumentValidatorActions.UPDATE_FONT_INFO |
            DocumentValidatorActions.NORMALIZE_HORIZONTAL_MERGE;
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.ODT, ODT_ACTIONS);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.OTT, ODT_ACTIONS);

        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.TEXT, DocumentValidatorActions.NON_MS_WORD_FORMAT);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.XAML_FLOW, DocumentValidatorActions.NON_MS_WORD_FORMAT);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.XAML_FLOW_PACK, DocumentValidatorActions.NON_MS_WORD_FORMAT);
        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.MARKDOWN, DocumentValidatorActions.NON_MS_WORD_FORMAT |
                                              DocumentValidatorActions.LONG_BOOKMARK_NAMES);

        final /*DocumentValidatorActions*/int DOCLING_ACTIONS =
            DocumentValidatorActions.EXPAND_TABLE_STYLES |
            DocumentValidatorActions.CONVERT_LEGACY_LIST_FORMATTING |
            DocumentValidatorActions.UPDATE_LIST_LABELS |
            DocumentValidatorActions.NORMALIZE_HORIZONTAL_MERGE;

        msDictionary.add(G_SAVE_ACTIONS, SaveFormat.DOCLING, DOCLING_ACTIONS);
    }
}

