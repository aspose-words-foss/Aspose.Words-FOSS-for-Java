// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.Language;
import com.aspose.LocaleConverter;
import com.aspose.StringUtil;
import java.util.Map;


/// <summary>
/// Provides static method for building "Settings" package part
/// </summary>
/*static*/ class DocxSettingsWriter
{
	/* Simulation of static class by using private constructor */
	private DocxSettingsWriter()
	{}

    /// <summary>
    /// Writes "Settings" document part for the specified document.
    /// </summary>
    static void write(DocxDocumentWriterBase writer) throws Exception
    {
        DocumentBase doc = writer.getDocument();
        DocPr docPr = doc.getDocPr();
        ViewOptions viewOptions = docPr.ViewOptions;
        /*OoxmlComplianceCore*/int compliance = writer.getCompliance();
        boolean isIso = compliance != OoxmlComplianceCore.ECMA_376;

        DocxBuilder builder = writer.createChildPartAndBuilder("settings.xml", DocxContentType.SETTINGS, writer.getRelTypes().getSettings());
        writer.pushBuilder(builder);

        builder.startSettingsDocumentPart();

        builder.writeValIfTrue("w:hideSpellingErrors", docPr.HideSpellingErrors);
        builder.writeValIfTrue("w:hideGrammaticalErrors", docPr.HideGrammaticalErrors);

        writeWriteProtection(docPr.WriteProtection, builder);

        builder.writeVal("w:view", DocxDopEnum.viewTypeToDocx(viewOptions.getViewType()));
        writeZoom(viewOptions, builder, compliance);
        builder.writeValIfTrue("w:removePersonalInformation", docPr.RemovePersonalInformation);
        builder.writeValIfTrue("w:removeDateAndTime", docPr.RemoveDateAndTime);
        builder.writeValIfTrue("w:doNotDisplayPageBoundaries", viewOptions.getDoNotDisplayPageBoundaries());
        builder.writeValIfTrue("w:displayBackgroundShape", viewOptions.getDisplayBackgroundShape());
        builder.writeValIfTrue("w:printPostScriptOverText", docPr.PrintPostScriptOverText);
        builder.writeValIfTrue("w:printFractionalCharacterWidth", docPr.PrintFractionalCharacterWidth);
        builder.writeValIfTrue("w:printFormsData", docPr.PrintFormsData);
        writeFontEmbeddingOptions(writer);
        builder.writeValIfTrue("w:saveFormsData", docPr.SaveFormsData);
        builder.writeValIfTrue("w:mirrorMargins", (docPr.MultiplePages == MultiplePagesType.MIRROR_MARGINS));
        builder.writeValIfTrue("w:alignBordersAndEdges", docPr.AlignBordersAndEdges);
        builder.writeValIfTrue("w:bordersDoNotSurroundHeader", docPr.BordersDoNotSurroundHeader);
        builder.writeValIfTrue("w:bordersDoNotSurroundFooter", docPr.BordersDoNotSurroundFooter);
        builder.writeValIfTrue("w:gutterAtTop", docPr.GutterAtTop);
        writeProofState(docPr, builder);
        builder.writeValIfTrue("w:formsDesign", viewOptions.getFormsDesign());
        writeLink(builder, "w:attachedTemplate", docPr.AttachedTemplate, writer.getRelTypes().getAttachedTemplate());
        builder.writeValIfTrue("w:linkStyles", docPr.LinkStyles);

        if (docPr.StylePaneFormatFilterSettings.getData() != StylePaneFormatFilterSettings.STYLE_PANE_FORMAT_FILTER_DEFAULT)
        {
            builder.startElement("w:stylePaneFormatFilter");
            if (compliance < OoxmlComplianceCore.ISO_STRICT)
            {
                builder.writeAttributeString(
                    VAL, FormatterPal.intToStrX4(docPr.StylePaneFormatFilterSettings.getData()));
            }

            if (isIso) // only for iso29500 and up
                writeStylePaneFormatFilterSettings(builder, docPr.StylePaneFormatFilterSettings);

            builder.endElement();
        }

        if (docPr._StylePaneSortMethod != StylePaneSortMethod.DEFAULT)
            builder.writeVal("w:stylePaneSortMethod", !isIso ?
                FormatterPal.intToStrX4(StyleConvertUtil.stylePaneSortMethodToInt(docPr._StylePaneSortMethod)) :
                DocxDopEnum.stylePaneSortMethodToDocx(docPr._StylePaneSortMethod));

        builder.writeVal("w:documentType", DocxDopEnum.autoFormatDocumentTypeToDocx(docPr.DocumentType));

        DocxMailMergePrWriter mailMergePrWriter = new DocxMailMergePrWriter();
        mailMergePrWriter.write(docPr.MailMergeSettings, writer);

        if (isAnyRevisionViewDisabled(docPr))
        {
            builder.startElement("w:revisionView");
            builder.writeAttribute("w:comments", docPr.ShowAnnotations);
            builder.writeAttribute("w:formatting", docPr.ShowFormatting);
            builder.writeAttribute("w:inkAnnotations", docPr.ShowInkAnnotations);
            builder.writeAttribute("w:insDel", docPr.ShowInsertionsDeletions);
            builder.writeAttribute("w:markup", docPr.ShowMarkup);
            builder.endElement();
        }

        builder.writeValIfTrue("w:trackRevisions", docPr.TrackRevisions);

        // WORDSNET-9070 To avoid accepting lot of golds we have to always write doNotTrackMoves in test mode.
        boolean doNotTrackMoves = true;
        if (writer.getSaveOptions().getWriteDoNotTrackMovesCorrectly())
            doNotTrackMoves = docPr.DoNotTrackMoves;

        builder.writeValIfTrue("w:doNotTrackMoves", doNotTrackMoves);
        builder.writeValIfTrue("w:doNotTrackFormatting", docPr.DoNotTrackFormatting);

        writeDocumentProtection(docPr.DocumentProtection, builder);
        builder.writeValIfTrue("w:autoFormatOverride", docPr.DocumentProtection.getAutoFormatOverride());
        builder.writeValIfTrue("w:styleLockTheme", docPr.StyleLockTheme);
        builder.writeValIfTrue("w:styleLockQFSet", docPr.StyleLockQuickFormatSet);
        builder.writeVal("w:defaultTabStop", docPr.DefaultTabStop);

        int istd = docPr.ClickTypeParaStyleIstd;
        if ((istd != DocPr.CLICK_TYPE_PARA_STYLE_ISTD_DEFAULT) && (doc.getStyles().getByIstd(istd, false) != null))
            builder.writeVal("w:clickAndTypeStyle", writer.getStyleId(istd));
        istd = docPr.DefaultTableStyleIstd;
        if ((istd != DocPr.DEFAULT_TABLE_STYLE_ISTD_DEFAULT) && (doc.getStyles().getByIstd(istd, false) != null))
            builder.writeVal("w:defaultTableStyle", writer.getStyleId(istd));

        builder.writeValIfTrue("w:updateFields", docPr.UpdateFields);
        builder.writeValIfTrue("w:forceUpgrade", docPr.ForceUpgrade);

        HyphenationOptions hyphenationOptions = docPr.HyphenationOptions;
        builder.writeValIfTrue("w:autoHyphenation", hyphenationOptions.getAutoHyphenation());
        builder.writeValIfPositive("w:consecutiveHyphenLimit", hyphenationOptions.getConsecutiveHyphenLimit());
        builder.writeValIfNotDefault("w:hyphenationZone", hyphenationOptions.getHyphenationZone(),
            HyphenationOptions.HYPHENATION_ZONE_DEFAULT);
        builder.writeValIfTrue("w:doNotHyphenateCaps", !hyphenationOptions.getHyphenateCaps());

        builder.writeValIfTrue("w:showEnvelope", docPr.ShowEnvelope);
        builder.writeValIfTrue("w:evenAndOddHeaders", docPr.EvenAndOddHeaders);

        boolean bookFoldPrinting = (docPr.MultiplePages == MultiplePagesType.BOOK_FOLD_PRINTING);
        boolean bookFoldPrintingReverse = (docPr.MultiplePages == MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE);

        builder.writeValIfTrue("w:bookFoldRevPrinting", bookFoldPrintingReverse);
        builder.writeValIfTrue("w:bookFoldPrinting", bookFoldPrinting);
        if (bookFoldPrinting || bookFoldPrintingReverse)
            builder.writeVal("w:bookFoldPrintingSheets", docPr.BookFoldPrintingSheets);

        builder.writeValIfNotDefault("w:drawingGridHorizontalSpacing", docPr.DrawingGridHorizontalSpacing, DocPr.DRAWING_GRID_HORIZONTAL_SPACING_DEFAULT);
        builder.writeValIfNotDefault("w:drawingGridVerticalSpacing", docPr.DrawingGridVerticalSpacing, DocPr.DRAWING_GRID_VERTICAL_SPACING_DEFAULT);
        builder.writeValIfNotDefault("w:displayHorizontalDrawingGridEvery", docPr.DisplayHorizontalDrawingGridEvery, DocPr.DISPLAY_HORIZONTAL_DRAWING_GRID_EVERY_DEFAULT);
        builder.writeValIfNotDefault("w:displayVerticalDrawingGridEvery", docPr.DisplayVerticalDrawingGridEvery, DocPr.DISPLAY_VERTICAL_DRAWING_GRID_EVERY_DEFAULT);
        // RK Note difference in DOCX name.
        builder.writeValIfTrue("w:doNotUseMarginsForDrawingGridOrigin", !docPr.UseMarginsForDrawingGridOrigin);
        if (!docPr.UseMarginsForDrawingGridOrigin)
        {
            builder.writeValIfNotDefault("w:drawingGridHorizontalOrigin", docPr.DrawingGridHorizontalOrigin, DocPr.DRAWING_GRID_HORIZONTAL_ORIGIN_DEFAULT);
            builder.writeValIfNotDefault("w:drawingGridVerticalOrigin", docPr.DrawingGridVerticalOrigin, DocPr.DRAWING_GRID_VERTICAL_ORIGIN_DEFAULT);
        }

        builder.writeValIfTrue("w:doNotShadeFormData", docPr.DoNotShadeFormData);
        builder.writeValIfTrue("w:noPunctuationKerning", !docPr.PunctuationKerning);
        builder.writeVal("w:characterSpacingControl", DocxDopEnum.kinsokuJustificationToDocx(docPr.CharacterSpacingType));
        builder.writeValIfTrue("w:printTwoOnOne", (docPr.MultiplePages == MultiplePagesType.TWO_PAGES_PER_SHEET));
        builder.writeValIfTrue("w:strictFirstAndLastChars", docPr.StrictFirstAndLastChars);

        NrxDocPropertiesWriter.writeNoLineBreaks(docPr, builder, docPr.NoLineBreaksLanguage == Language.LANGUAGE_NOT_SET
                          ? "en-US"
                          : LocaleConverter.localeToDocxTag((int)docPr.NoLineBreaksLanguage));

        builder.writeValIfTrue("w:doNotEmbedSmartTags", docPr.DoNotEmbedSmartTags);
        builder.writeValIfTrue("w:showEnvelope", docPr.ShowEnvelope);
        builder.writeValIfTrue("w:doNotValidateAgainstSchema", !docPr.ValidateAgainstSchema);
        builder.writeValIfTrue("w:saveInvalidXml", docPr.SaveInvalidXml);
        builder.writeValIfTrue("w:ignoreMixedContent", docPr.IgnoreMixedContent);
        builder.writeValIfTrue("w:alwaysShowPlaceholderText", docPr.AlwaysShowPlaceholderText);
        builder.writeValIfTrue("w:doNotDemarcateInvalidXml", docPr.DoNotUnderlineInvalidXml);
        builder.writeValIfTrue("w:saveXmlDataOnly", docPr.SaveXmlDataOnly);
        builder.writeValIfTrue("w:useXSLTWhenSaving", docPr.UseXsltWhenSaving);
        writeLink(builder, "w:saveThroughXslt", docPr.SaveThroughXslt, writer.getRelTypes().getSaveThroughXslt());
        builder.writeValIfTrue("w:showXMLTags", docPr.ShowXmlTags);
        builder.writeValIfTrue("w:alwaysMergeEmptyNamespace", docPr.AlwaysMergeEmptyNamespace);
        writer.writeFootnotePr(docPr.FootnotePr, true);
        writeCompat(docPr.CompatibilityOptions, builder, compliance);

        if (writer.getSaveOptions().getWriteRsidTable())
            NrxSettingsWriter.writeRsids(docPr, builder, SaveFormat.DOCX);

        writeDocVars(doc.getVariables(), builder);

        if (docPr.MathProperties.isNonDefault()) // at least one property is non default.
            writeMathProperties(docPr.MathProperties, builder);  // need to create mathPr section in the document.

        if (compliance == OoxmlComplianceCore.ECMA_376) // this element is removed from ISO29500 onward.
            builder.writeValIfTrue("w:uiCompat97To2003", docPr.CompatibilityOptions.getUICompat97To2003());

        writeThemeFontLang(docPr.ThemeFontLanguages, builder);

        // WORDSNET-4961 andrnosk: Add this section to make the document editable in MS Word Web App.
        if (writer.getSaveOptions().getWriteClrSchemeMapping())
            writeClrSchemeMapping(builder);

        if (writer.getSaveOptions().getWriteWordCountOption())
            builder.writeValIfTrue("w:doNotIncludeSubdocsInStats", docPr.DoNotIncludeSubDocsInStats);

        builder.writeValIfTrue("w:doNotEmbedSmartTags", docPr.DoNotEmbedSmartTags);
        builder.writeValIfTrue("w:doNotAutoCompressPictures", docPr.DoNotAutoCompressPictures);

        if (compliance < OoxmlComplianceCore.ISO_STRICT)
        {
            NrxShapeDefaultsWriter.write(builder, writer.getSaveInfo(), false);
            NrxShapeDefaultsWriter.write(builder, writer.getSaveInfo(), true);
        }

        if (isIso)
        {
            builder.writeValIfTrue("w14:discardImageEditingData", docPr.DiscardImageEditingData);
            if (docPr.DefaultImageDpi > 0)
                builder.writeElementWithAttributes("w14:defaultImageDpi", "w14:val", docPr.DefaultImageDpi);
            if (writer.getSaveOptions().getWriteW14DocId())
                builder.writeElementWithAttributes("w14:docId", "w14:val", docPr.DocId);
            builder.writeValIfTrue("w15:chartTrackingRefBased", docPr.ChartTrackingRefBased);
            builder.writeElementWithAttributes("w15:docId", "w15:val", docPr.DocumentSetId);
        }

        builder.endDocument(); //w:settings
        writer.popBuilder();
    }

    private static void writeLink(DocxBuilder builder, String elementName, String value, String relType)
    {
        if (StringUtil.hasChars(value))
        {
            // RK This value is escaped when adding to relationships.
            String relId = builder.getPart().getRels().add(relType, value, true);
            builder.startElement(elementName);
            builder.writeAttributeString("r:id", relId);
            builder.endElement();
        }
    }

    /// <summary>
    /// Write ISO 29500 specific attributes.
    /// </summary>
    private static void writeStylePaneFormatFilterSettings(
        DocxBuilder builder,
        StylePaneFormatFilterSettings settings)
    {
        builder.writeAttribute("w:allStyles", settings.getAllStyles());
        builder.writeAttribute("w:alternateStyleNames", settings.getAlternateStyleNames());
        builder.writeAttribute("w:clearFormatting", settings.getClearFormatting());
        builder.writeAttribute("w:customStyles", settings.getCustomStyles());
        builder.writeAttribute("w:directFormattingOnNumbering", settings.getDirectFormattingOnNumbering());
        builder.writeAttribute("w:directFormattingOnParagraphs", settings.getDirectFormattingOnParagraphs());
        builder.writeAttribute("w:directFormattingOnRuns", settings.getDirectFormattingOnRuns());
        builder.writeAttribute("w:directFormattingOnTables", settings.getDirectFormattingOnTables());
        builder.writeAttribute("w:headingStyles", settings.getHeadingStyles());
        builder.writeAttribute("w:latentStyles", settings.getLatentStyles());
        builder.writeAttribute("w:numberingStyles", settings.getNumberingStyles());
        builder.writeAttribute("w:stylesInUse", settings.getStylesInUse());
        builder.writeAttribute("w:tableStyles", settings.getTableStyles());
        builder.writeAttribute("w:top3HeadingStyles", settings.getTop3HeadingStyles());
        builder.writeAttribute("w:visibleStyles", settings.getVisibleStyles());
    }

    private static boolean isAnyRevisionViewDisabled(DocPr docPr)
    {
        return !docPr.ShowAnnotations ||
               !docPr.ShowFormatting ||
               !docPr.ShowInkAnnotations ||
               !docPr.ShowInsertionsDeletions ||
               !docPr.ShowMarkup;
    }

    /// <summary>
    /// RK This seems to be DOCX only setting, not available in WordML.
    /// </summary>
    private static void writeWriteProtection(WriteProtection writeProt, DocxBuilder builder)
    {
        if (writeProt.getReadOnlyRecommended() || writeProt.isWriteProtected())
        {
            builder.startElement("w:writeProtection");

            builder.writeAttributeIfTrue("w:recommended", writeProt.getReadOnlyRecommended());

            writeProt.updateDocxHash();
            writePasswordHashAttrs(writeProt.getPasswordHash(), builder);

            builder.endElement(); //w:writeProtection
        }
    }

    /// <summary>
    /// Without this section in Settings MS Word Web App reports 
    /// that the document (generated by AW) cannot be opened for editing. 
    /// </summary>
    private static void writeClrSchemeMapping(DocxBuilder builder)
    {
        // Write the same default values as MS Word does. 
        // Valid values of attributes are listed in 2.18.12 section in DOCX spec. 
        builder.startElement("w:clrSchemeMapping");

        builder.writeAttribute("w:bg1", "light1");
        builder.writeAttribute("w:t1", "dark1");
        builder.writeAttribute("w:bg2", "light2");
        builder.writeAttribute("w:t2", "dark2");
        builder.writeAttribute("w:accent1", "accent1");
        builder.writeAttribute("w:accent2", "accent2");
        builder.writeAttribute("w:accent3", "accent3");
        builder.writeAttribute("w:accent4", "accent4");
        builder.writeAttribute("w:accent5", "accent5");
        builder.writeAttribute("w:accent6", "accent6");
        builder.writeAttribute("w:hyperlink", "hyperlink");
        builder.writeAttribute("w:followedHyperlink", "followedHyperlink");

        builder.endElement();
    }

    private static void writeZoom(ViewOptions viewOptions, NrxXmlBuilder builder, /*OoxmlComplianceCore*/int compliance)
    {
        builder.startElement("w:zoom");

        if (viewOptions.getZoomType() != ZoomType.NONE)
            builder.writeAttribute(VAL, DocxDopEnum.zoomTypeToDocx(viewOptions.getZoomType()));

        String percent = FormatterPal.intToXml(viewOptions.getZoomPercent());
        if (compliance == OoxmlComplianceCore.ISO_STRICT)
            percent = msString.plusEqOperator(percent, "%");
        builder.writeAttribute("w:percent", percent);

        builder.endElement(); //w:zoom
    }

    private static void writeProofState(DocPr docPr, NrxXmlBuilder builder)
    {
        builder.writeElementWithAttributes(
            "w:proofState",
            "w:spelling", DocxDopEnum.proofStateToDocx(docPr.ProofStateSpelling),
            "w:grammar", DocxDopEnum.proofStateToDocx(docPr.ProofStateGrammar));
    }

    private static void writeDocumentProtection(DocumentProtection prot, DocxBuilder builder)
    {
        if ((prot.getEdit() == ProtectionType.NO_PROTECTION) && (!prot.getFormatting()))
            return;

        builder.startElement("w:documentProtection");

        if (prot.getEdit() != ProtectionType.NO_PROTECTION)
            builder.writeAttribute("w:edit", DocxDopEnum.protectionTypeToDocx(prot.getEdit()));

        builder.writeAttributeIfTrue("w:formatting", prot.getFormatting());
        // WORDSNET-4700 Write enforcement attribute always in case of omitted attribute Word enforces protection in spite of OOXML spec.
        builder.writeAttribute("w:enforcement", prot.getEnforcement());

        prot.updateDocxHash();
        writePasswordHashAttrs(prot.getPasswordHash(), builder);

        builder.endElement(); //w:documentProtection
    }


    private static void writePasswordHashAttrs(PasswordHash hash, DocxBuilder builder)
    {
        if (hash.isEmpty())
            return;

        boolean isIsoStrict = builder.getOoxmlCompliance() == OoxmlComplianceCore.ISO_STRICT;

        if (isIsoStrict)
        {
            builder.writeAttribute("w:algorithmName", PasswordHash.cryptAlgorithmNameFromSid(hash.CryptAlgorithmSid));
        }
        else
        {
            String cryptProviderType =
                (
                    (hash.CryptAlgorithmSid == PasswordHash.cryptAlgorithmSidFromAlgorithmName("SHA-512")) ||
                    // WORDSNET-21134 SHA-256 is also used for AES encryption.
                    (hash.CryptAlgorithmSid == PasswordHash.cryptAlgorithmSidFromAlgorithmName("SHA-256"))
                )
                    ? "rsaAES"
                    : "rsaFull";

            builder.writeAttribute("w:cryptProviderType", cryptProviderType);
            builder.writeAttribute("w:cryptAlgorithmClass", "hash");
            builder.writeAttribute("w:cryptAlgorithmType", "typeAny");
            builder.writeAttribute("w:cryptAlgorithmSid", hash.CryptAlgorithmSid);
        }

        builder.writeAttribute(isIsoStrict ? "w:spinCount" : "w:cryptSpinCount", hash.CryptSpinCount);
        builder.writeAttribute(isIsoStrict ? "w:hashValue" : "w:hash", hash.Hash);
        builder.writeAttribute(isIsoStrict ? "w:saltValue" : "w:salt", hash.Salt);
    }

    private static void writeCompat(CompatibilityOptions co,
        NrxXmlBuilder builder,
        /*OoxmlComplianceCore*/int compliance)
    {
        builder.startElement("w:compat");

        if (compliance == OoxmlComplianceCore.ISO_STRICT)
        {
            builder.writeValIfTrue("w:spaceForUL", co.getSpaceForUL());
            builder.writeValIfTrue("w:balanceSingleByteDoubleByteWidth", co.getBalanceSingleByteDoubleByteWidth());
            builder.writeValIfTrue("w:doNotLeaveBackslashAlone", co.getDoNotLeaveBackslashAlone());
            builder.writeValIfTrue("w:ulTrailSpace", co.getUlTrailSpace());
            builder.writeValIfTrue("w:doNotExpandShiftReturn", co.getDoNotExpandShiftReturn());
            builder.writeValIfTrue("w:adjustLineHeightInTable", co.getAdjustLineHeightInTable());
            builder.writeValIfTrue("w:applyBreakingRules", co.getApplyBreakingRules());
        }
        else
        {
            builder.writeValIfTrue("w:useSingleBorderforContiguousCells", co.getUseSingleBorderforContiguousCells());
            builder.writeValIfTrue("w:wpJustification", co.getWPJustification());
            builder.writeValIfTrue("w:noTabHangInd", co.getNoTabHangInd());
            builder.writeValIfTrue("w:noLeading", co.getNoLeading());
            builder.writeValIfTrue("w:spaceForUL", co.getSpaceForUL());
            builder.writeValIfTrue("w:noColumnBalance", co.getNoColumnBalance());
            builder.writeValIfTrue("w:balanceSingleByteDoubleByteWidth", co.getBalanceSingleByteDoubleByteWidth());
            builder.writeValIfTrue("w:noExtraLineSpacing", co.getNoExtraLineSpacing());
            builder.writeValIfTrue("w:doNotLeaveBackslashAlone", co.getDoNotLeaveBackslashAlone());
            builder.writeValIfTrue("w:ulTrailSpace", co.getUlTrailSpace());
            builder.writeValIfTrue("w:doNotExpandShiftReturn", co.getDoNotExpandShiftReturn());
            builder.writeValIfTrue("w:spacingInWholePoints", co.getSpacingInWholePoints());
            builder.writeValIfTrue("w:lineWrapLikeWord6", co.getLineWrapLikeWord6());
            builder.writeValIfTrue("w:printBodyTextBeforeHeader", co.getPrintBodyTextBeforeHeader());
            builder.writeValIfTrue("w:printColBlack", co.getPrintColBlack());
            builder.writeValIfTrue("w:showBreaksInFrames", co.getShowBreaksInFrames());
            builder.writeValIfTrue("w:subFontBySize", co.getSubFontBySize());
            builder.writeValIfTrue("w:suppressBottomSpacing", co.getSuppressBottomSpacing());
            builder.writeValIfTrue("w:suppressTopSpacing", co.getSuppressTopSpacing());
            builder.writeValIfTrue("w:suppressSpacingAtTopOfPage", co.getSuppressSpacingAtTopOfPage());
            builder.writeValIfTrue("w:suppressTopSpacingWP", co.getSuppressTopSpacingWP());
            builder.writeValIfTrue("w:suppressSpBfAfterPgBrk", co.getSuppressSpBfAfterPgBrk());
            builder.writeValIfTrue("w:swapBordersFacingPages", co.getSwapBordersFacingPgs());
            builder.writeValIfTrue("w:convMailMergeEsc", co.getConvMailMergeEsc());
            builder.writeValIfTrue("w:truncateFontHeightsLikeWP6", co.getTruncateFontHeightsLikeWP6());
            builder.writeValIfTrue("w:mwSmallCaps", co.getMWSmallCaps());
            builder.writeValIfTrue("w:usePrinterMetrics", co.getUsePrinterMetrics());
            builder.writeValIfTrue("w:doNotSuppressParagraphBorders", co.getDoNotSuppressParagraphBorders());
            builder.writeValIfTrue("w:wrapTrailSpaces", co.getWrapTrailSpaces());
            builder.writeValIfTrue("w:footnoteLayoutLikeWW8", co.getFootnoteLayoutLikeWW8());
            builder.writeValIfTrue("w:shapeLayoutLikeWW8", co.getShapeLayoutLikeWW8());
            builder.writeValIfTrue("w:alignTablesRowByRow", co.getAlignTablesRowByRow());
            builder.writeValIfTrue("w:forgetLastTabAlignment", co.getForgetLastTabAlignment());
            builder.writeValIfTrue("w:adjustLineHeightInTable", co.getAdjustLineHeightInTable());
            builder.writeValIfTrue("w:autoSpaceLikeWord95", co.getAutoSpaceLikeWord95());
            builder.writeValIfTrue("w:noSpaceRaiseLower", co.getNoSpaceRaiseLower());
            builder.writeValIfTrue("w:doNotUseHTMLParagraphAutoSpacing", co.getDoNotUseHTMLParagraphAutoSpacing());
            builder.writeValIfTrue("w:layoutRawTableWidth", co.getLayoutRawTableWidth());
            builder.writeValIfTrue("w:layoutTableRowsApart", co.getLayoutTableRowsApart());
            builder.writeValIfTrue("w:useWord97LineBreakRules", co.getUseWord97LineBreakRules());
            builder.writeValIfTrue("w:doNotBreakWrappedTables", co.getDoNotBreakWrappedTables());
            builder.writeValIfTrue("w:doNotSnapToGridInCell", co.getDoNotSnapToGridInCell());
            builder.writeValIfTrue("w:selectFldWithFirstOrLastChar", co.getSelectFldWithFirstOrLastChar());
            builder.writeValIfTrue("w:applyBreakingRules", co.getApplyBreakingRules());
            builder.writeValIfTrue("w:doNotWrapTextWithPunct", co.getDoNotWrapTextWithPunct());
            builder.writeValIfTrue("w:doNotUseEastAsianBreakRules", co.getDoNotUseEastAsianBreakRules());
            builder.writeValIfTrue("w:useWord2002TableStyleRules", co.getUseWord2002TableStyleRules());
            builder.writeValIfTrue("w:growAutofit", co.getGrowAutofit());
            builder.writeValIfTrue("w:useFELayout", co.getUseFELayout());
            builder.writeValIfTrue("w:useNormalStyleForList", co.getUseNormalStyleForList());
            builder.writeValIfTrue("w:doNotUseIndentAsNumberingTabStop", co.getDoNotUseIndentAsNumberingTabStop());
            builder.writeValIfTrue("w:useAltKinsokuLineBreakRules", co.getUseAltKinsokuLineBreakRules());
            builder.writeValIfTrue("w:allowSpaceOfSameStyleInTable", co.getAllowSpaceOfSameStyleInTable());
            builder.writeValIfTrue("w:doNotSuppressIndentation", co.getDoNotSuppressIndentation());
            builder.writeValIfTrue("w:doNotAutofitConstrainedTables", co.getDoNotAutofitConstrainedTables());
            builder.writeValIfTrue("w:autofitToFirstFixedWidthCell", co.getAutofitToFirstFixedWidthCell());
            builder.writeValIfTrue("w:underlineTabInNumList", co.getUnderlineTabInNumList());
            builder.writeValIfTrue("w:displayHangulFixedWidth", co.getDisplayHangulFixedWidth());
            builder.writeValIfTrue("w:splitPgBreakAndParaMark", co.getSplitPgBreakAndParaMark());
            builder.writeValIfTrue("w:doNotVertAlignCellWithSp", co.getDoNotVertAlignCellWithSp());
            builder.writeValIfTrue("w:doNotBreakConstrainedForcedTable", co.getDoNotBreakConstrainedForcedTable());
            builder.writeValIfTrue("w:doNotVertAlignInTxbx", co.getDoNotVertAlignInTxbx());
            builder.writeValIfTrue("w:useAnsiKerningPairs", co.getUseAnsiKerningPairs());
            builder.writeValIfTrue("w:cachedColBalance", co.getCachedColBalance());
            builder.writeValIfTrue("w:wpSpaceWidth", co.getWPSpaceWidth());
        }

        writeCustomCompatSettings(co, compliance, builder);

        builder.endElement(); //w:compat
    }

    /// <summary>
    /// If compliance is other than older ECMA376, then write custom compat settings into docx.
    /// </summary>
    private static void writeCustomCompatSettings(
        CompatibilityOptions co,
        /*OoxmlComplianceCore*/int compliance,
        NrxXmlBuilder builder)
    {
        if ((co.getCustomCompatibilitySettings().getCount() != 0) && // if we have something to write 
            (compliance != OoxmlComplianceCore.ECMA_376))
        {
            CustomCompatibilitySettingCollection settings = co.getCustomCompatibilitySettings();
            for (int iSetting = 0; iSetting < settings.getCount(); iSetting++)
            {
                // WORDSNET-4777 It's essential to write "w" namespace prefix. Otherwise Word2010 shows "Compatibility mode" warning.
                builder.startElement("w:compatSetting"); // w:compatSetting
                builder.writeAttribute("w:name", settings.get(iSetting).getName());
                builder.writeAttribute("w:uri", settings.get(iSetting).getUri());
                builder.writeAttribute(VAL, settings.get(iSetting).getValue());
                builder.endElement();
            }
        }
    }

    private static void writeDocVars(VariableCollection variables, NrxXmlBuilder builder)
    {
        if (variables.getCount() > 0)
        {
            builder.startElement("w:docVars");

            for (Map.Entry<String, String> var : variables)
            {
                // WORDSNET-22135 The correct writing of the Line Break character for the 'w:docVar' element.
                // AM. Someday we need to move all these transformation into NrxXmlBuilder class.
                String value = (var.getValue() != null) ? var.getValue().replace("\u000b", "_x000b_") : null;

                builder.writeElementWithAttributes(
                    "w:docVar",
                    "w:name", var.getKey(),
                    VAL, value);
            }

            builder.endElement(); //w:docVars
        }
    }

    private static void writeThemeFontLang(ThemeFontLanguages languages, NrxXmlBuilder builder)
    {
        String bidi = LocaleConverter.localeToDocxTag((int)languages.Bidi);
        String eastAsia = LocaleConverter.localeToDocxTag((int)languages.EastAsia);
        String other = LocaleConverter.localeToDocxTag((int)languages.Latin);

        builder.writeElementWithAttributes(
            "w:themeFontLang",
            VAL, other,
            "w:eastAsia", eastAsia,
            "w:bidi", bidi);
    }

    private static void writeMathProperties(MathProperties mathPr, NrxXmlBuilder builder)
    {
        builder.startElement("m:mathPr");


        builder.writeVal("m:mathFont", mathPr.getDefaultFont());

        if (mathPr.getBreakOnBinary() != MathBreakOnBinary.DEFAULT)
            builder.writeVal("m:brkBin", DocxDopEnum.mathBreakOnBynaryToDocx(mathPr.getBreakOnBinary()));

        if (mathPr.getBreakOnBinarySubtraction() != MathBreakOnBinarySubtraction.DEFAULT)
            builder.writeVal("m:brkBinSub", DocxDopEnum.mathBreakOnBinarySubtractionToDocx(mathPr.getBreakOnBinarySubtraction()));

        builder.writeValIfTrue("m:smallFrac", mathPr.isSmallFraction());

        if (!mathPr.getUseDisplayMathDefaults())
            builder.writeVal("m:dispDef", mathPr.getUseDisplayMathDefaults());

        builder.writeValIfNotDefault("m:lMargin", mathPr.getLeftMargin(), 0);
        builder.writeValIfNotDefault("m:rMargin", mathPr.getRightMargin(), 0);

        if (mathPr.getDefaultJustification() != OfficeMathJustification.DEFAULT)
            builder.writeVal("m:defJc", DocxEnum.mathJustificationToDocx(mathPr.getDefaultJustification()));

        builder.writeValIfNotDefault("m:preSp", mathPr.getPreParagraphSpacing(), 0);
        builder.writeValIfNotDefault("m:postSp", mathPr.getPostParagraphSpacing(), 0);
        builder.writeValIfNotDefault("m:interSp", mathPr.getInterEquationSpacing(), 0);
        builder.writeValIfNotDefault("m:intraSp", mathPr.getIntraEquationSpacing(), 0);
        builder.writeValIfNotDefault("m:wrapIndent", mathPr.getWrapIndent(), MathProperties.DEFAULT_WRAP_INDENT);
        builder.writeValIfTrue("m:wrapRight", mathPr.getWrapRight());

        if (mathPr.getIntegralLimitLocation() != MathLimitLocation.SUBSCRIPT_SUPERSCRIPT)
            builder.writeVal("m:intLim", DocxEnum.mathLimitLocationToDocx(mathPr.getIntegralLimitLocation()));

        if (mathPr.getNaryLimitLocation() != MathLimitLocation.UNDER_OVER)
            builder.writeVal("m:naryLim", DocxEnum.mathLimitLocationToDocx(mathPr.getNaryLimitLocation()));

        builder.endElement();
    }

    /// <summary>
    /// Writes font embedding options.
    /// </summary>
    private static void writeFontEmbeddingOptions(DocxDocumentWriterBase writer)
    {
        DocumentBase doc = writer.getDocument();
        DocxBuilder builder = writer.getCurrentBuilder();
        DocPr docPr = doc.getDocPr();

        builder.writeValIfTrue("w:embedTrueTypeFonts", docPr.EmbedTrueTypeFonts);
        builder.writeValIfTrue("w:embedSystemFonts", docPr.EmbedSystemFonts);
        builder.writeValIfTrue("w:saveSubsetFonts", docPr.SaveSubsetFonts);
    }

    private static final String VAL = "w:val";
}

