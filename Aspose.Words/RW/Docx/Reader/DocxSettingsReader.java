// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.Language;
import com.aspose.LocaleConverter;


/// <summary>
/// Provides static method for reading "Settings" package part
/// </summary>
/*static*/ class DocxSettingsReader
{
	/* Simulation of static class by using private constructor */
	private DocxSettingsReader()
	{}

    /// <summary>
    /// Reads "Settings" part for the specified document.
    /// </summary>
    static void read(DocxDocumentReaderBase reader) throws Exception
    {
        DocxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getSettings());
        if (xmlReader == null)
            return;

        DocumentBase doc = reader.getDocument();

        DocPr docPr = doc.getDocPr();
        ViewOptions viewOptions = docPr.ViewOptions;
        CompatibilityOptions compat = docPr.CompatibilityOptions;
        HyphenationOptions hyphenationOptions = docPr.HyphenationOptions;
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        // Need to set some defaults here.
        docPr.PunctuationKerning = true;
        viewOptions.setViewType(ViewType.PAGE_LAYOUT);

        while (xmlReader.readChild("settings")) // w:settings
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"doNotIncludeSubdocsInStats"*/0:
                    docPr.DoNotIncludeSubDocsInStats = xmlReader.readBoolVal();
                    break;
                case /*"writeProtection"*/1:
                    readWriteProtection(xmlReader, docPr, reader.getComplianceInfo());
                    break;
                case /*"alwaysMergeEmptyNamespace"*/2:
                    docPr.AlwaysMergeEmptyNamespace = xmlReader.readBoolVal();
                    break;
                case /*"alwaysShowPlaceholderText"*/3:
                    docPr.AlwaysShowPlaceholderText = xmlReader.readBoolVal();
                    break;
                case /*"autoFormatOverride"*/4:
                    docPr.DocumentProtection.setAutoFormatOverride(xmlReader.readBoolVal());
                    break;
                case /*"bookFoldPrinting"*/5:
                case /*"bookFoldRevPrinting"*/6:
                case /*"mirrorMargins"*/7:
                case /*"printTwoOnOne"*/8:
                    NrxSettingsReader.readMultiplePagesType(reader, docPr);
                    break;
                case /*"bookFoldPrintingSheets"*/9:
                    docPr.BookFoldPrintingSheets = xmlReader.readIntVal();
                    break;
                case /*"embedSystemFonts"*/10:
                    docPr.EmbedSystemFonts = xmlReader.readBoolVal();
                    break;
                case /*"printFractionalCharacterWidth"*/11:
                    docPr.PrintFractionalCharacterWidth = xmlReader.readBoolVal();
                    break;
                case /*"printPostScriptOverText"*/12:
                    docPr.PrintPostScriptOverText = xmlReader.readBoolVal();
                    break;
                case /*"removePersonalInformation"*/13:
                    docPr.RemovePersonalInformation = xmlReader.readBoolVal();
                    break;
                case /*"removeDateAndTime"*/14:
                    docPr.RemoveDateAndTime = xmlReader.readBoolVal();
                    break;
                case /*"showEnvelope"*/15:
                    docPr.ShowEnvelope = xmlReader.readBoolVal();
                    break;
                case /*"doNotValidateAgainstSchema"*/16:
                    docPr.ValidateAgainstSchema = !xmlReader.readBoolVal();
                    break;
                case /*"saveInvalidXml"*/17:
                    // Note DOCX name difference.
                    docPr.SaveInvalidXml = xmlReader.readBoolVal();
                    break;
                case /*"saveXmlDataOnly"*/18:
                    docPr.SaveXmlDataOnly = xmlReader.readBoolVal();
                    break;
                case /*"showXMLTags"*/19:
                    docPr.ShowXmlTags = xmlReader.readBoolVal();
                    break;
                case /*"ignoreMixedContent"*/20:
                    docPr.IgnoreMixedContent = xmlReader.readBoolVal();
                    break;
                case /*"useXSLTWhenSaving"*/21:
                    docPr.UseXsltWhenSaving = xmlReader.readBoolVal();
                    break;
                case /*"saveThroughXslt"*/22:
                    // Note DOCX name difference.
                    docPr.SaveThroughXslt = reader.getRelationshipTarget(xmlReader.readId());
                    break;
                case /*"view"*/23:
                    viewOptions.setViewType(DocxDopEnum.docxToViewType(xmlReader.readVal()));
                    break;
                case /*"zoom"*/24:
                    readZoom(xmlReader, viewOptions);
                    break;
                case /*"doNotDisplayPageBoundaries"*/25:
                    viewOptions.setDoNotDisplayPageBoundaries(xmlReader.readBoolVal());
                    break;
                case /*"displayBackgroundShape"*/26:
                    viewOptions.setDisplayBackgroundShape(xmlReader.readBoolVal());
                    break;
                case /*"printFormsData"*/27:
                    docPr.PrintFormsData = xmlReader.readBoolVal();
                    break;
                case /*"embedTrueTypeFonts"*/28:
                    docPr.EmbedTrueTypeFonts = xmlReader.readBoolVal();
                    break;
                case /*"saveSubsetFonts"*/29:
                    docPr.SaveSubsetFonts = xmlReader.readBoolVal();
                    break;
                case /*"saveFormsData"*/30:
                    docPr.SaveFormsData = xmlReader.readBoolVal();
                    break;
                case /*"alignBordersAndEdges"*/31:
                    docPr.AlignBordersAndEdges = xmlReader.readBoolVal();
                    break;
                case /*"bordersDoNotSurroundHeader"*/32:
                    docPr.BordersDoNotSurroundHeader = xmlReader.readBoolVal();
                    break;
                case /*"bordersDoNotSurroundFooter"*/33:
                    docPr.BordersDoNotSurroundFooter = xmlReader.readBoolVal();
                    break;
                case /*"doNotDemarcateInvalidXml"*/34:
                    docPr.DoNotUnderlineInvalidXml = xmlReader.readBoolVal();
                    break;
                case /*"doNotEmbedSmartTags"*/35:
                    docPr.DoNotEmbedSmartTags = xmlReader.readBoolVal();
                    break;
                case /*"gutterAtTop"*/36:
                    docPr.GutterAtTop = xmlReader.readBoolVal();
                    break;
                case /*"proofState"*/37:
                    readProofState(xmlReader, docPr);
                    break;
                case /*"formsDesign"*/38:
                    viewOptions.setFormsDesign(xmlReader.readBoolVal());
                    break;
                case /*"attachedTemplate"*/39:
                    docPr.AttachedTemplate = reader.getRelationshipTarget(xmlReader.readId());
                    break;
                case /*"linkStyles"*/40:
                    docPr.LinkStyles = xmlReader.readBoolVal();
                    break;
                case /*"documentType"*/41:
                    docPr.DocumentType = DocxDopEnum.docxToAutoFormatDocumentType(xmlReader.readVal());
                    break;
                case /*"mailMerge"*/42:
                    DocxReaderFactory.getMailMergePrReader().read(reader, docPr.MailMergeSettings);
                    break;
                case /*"mathPr"*/43:
                    readMathProperties(xmlReader, docPr.MathProperties, complianceInfo);
                    break;
                case /*"styleLockQFSet"*/44:
                    docPr.StyleLockQuickFormatSet = xmlReader.readBoolVal();
                    break;
                case /*"styleLockTheme"*/45:
                    docPr.StyleLockTheme = xmlReader.readBoolVal();
                    break;
                case /*"stylePaneFormatFilter"*/46:
                {
                    String elValue = xmlReader.readVal();
                    if (StringUtil.hasChars(elValue))
                        docPr.StylePaneFormatFilterSettings.setData(NrxXmlUtil.hexToInt(elValue));

                    docPr.StylePaneFormatFilterSettings = 
                        new StylePaneFormatFilterSettings(docPr.StylePaneFormatFilterSettings.getData());
                    readStylePaneFormatFilterSettings(xmlReader, docPr.StylePaneFormatFilterSettings,
                        xmlReader.getComplianceInfo());
                }
                    break;
                case /*"stylePaneSortMethod"*/47:
                    docPr._StylePaneSortMethod = DocxDopEnum.docxToStylePaneSortMethod(xmlReader.readVal(),
                        reader.getComplianceInfo());
                    break;
                case /*"trackRevisions"*/48:
                    docPr.TrackRevisions = xmlReader.readBoolVal();
                    break;
                case /*"doNotTrackMoves"*/49:
                    docPr.DoNotTrackMoves = xmlReader.readBoolVal();
                    break;
                case /*"doNotTrackFormatting"*/50:
                    docPr.DoNotTrackFormatting = xmlReader.readBoolVal();
                    break;
                case /*"documentProtection"*/51:
                    readDocumentProtection(xmlReader, docPr.DocumentProtection, reader.getComplianceInfo());
                    break;
                case /*"defaultTabStop"*/52:
                    docPr.DefaultTabStop = xmlReader.readValAsTwips(complianceInfo);
                    break;
                case /*"autoHyphenation"*/53:
                    hyphenationOptions.setAutoHyphenation(xmlReader.readBoolVal());
                    break;
                case /*"consecutiveHyphenLimit"*/54:
                    hyphenationOptions.setConsecutiveHyphenLimitSafe(xmlReader.readIntVal());
                    break;
                case /*"hyphenationZone"*/55:
                    hyphenationOptions.setHyphenationZoneSafe(xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"doNotHyphenateCaps"*/56:
                    hyphenationOptions.setHyphenateCaps(!xmlReader.readBoolVal());
                    break;
                case /*"evenAndOddHeaders"*/57:
                    docPr.EvenAndOddHeaders = xmlReader.readBoolVal();
                    break;
                case /*"drawingGridHorizontalSpacing"*/58:
                    docPr.DrawingGridHorizontalSpacing = xmlReader.readValAsTwips(complianceInfo);
                    break;
                case /*"drawingGridVerticalSpacing"*/59:
                    docPr.DrawingGridVerticalSpacing = xmlReader.readValAsTwips(complianceInfo);
                    break;
                case /*"displayHorizontalDrawingGridEvery"*/60:
                    docPr.DisplayHorizontalDrawingGridEvery = xmlReader.readIntVal();
                    break;
                case /*"displayVerticalDrawingGridEvery"*/61:
                    docPr.DisplayVerticalDrawingGridEvery = xmlReader.readIntVal();
                    break;
                case /*"doNotUseMarginsForDrawingGridOrigin"*/62:
                    docPr.UseMarginsForDrawingGridOrigin = !xmlReader.readBoolVal();
                    break;
                case /*"drawingGridHorizontalOrigin"*/63:
                    docPr.DrawingGridHorizontalOrigin = xmlReader.readValAsTwips(complianceInfo);
                    break;
                case /*"drawingGridVerticalOrigin"*/64:
                    docPr.DrawingGridVerticalOrigin = xmlReader.readValAsTwips(complianceInfo);
                    break;
                case /*"doNotShadeFormData"*/65:
                    docPr.DoNotShadeFormData = xmlReader.readBoolVal();
                    break;
                case /*"noPunctuationKerning"*/66:
                    docPr.PunctuationKerning = !xmlReader.readBoolVal();
                    break;
                case /*"characterSpacingControl"*/67:
                    docPr.CharacterSpacingType = DocxDopEnum.docxToKinsokuJustification(xmlReader.readVal());
                    break;
                case /*"strictFirstAndLastChars"*/68:
                    docPr.StrictFirstAndLastChars = xmlReader.readBoolVal();
                    break;
                case /*"noLineBreaksAfter"*/69:
                    docPr.NoLineBreaksAfter = readNoLineBreaks(xmlReader, docPr);
                    break;
                case /*"noLineBreaksBefore"*/70:
                    docPr.NoLineBreaksBefore = readNoLineBreaks(xmlReader, docPr);
                    break;
                case /*"footnotePr"*/71:
                case /*"endnotePr"*/72:
                    readFootnotePr(reader, xmlReader.getLocalName(), docPr.FootnotePr);
                    break;
                case /*"compat"*/73:
                    readCompat(xmlReader, compat, reader.getComplianceInfo());
                    break;
                case /*"docVars"*/74:
                    readVars(xmlReader, doc.getVariables());
                    break;
                case /*"uiCompat97To2003"*/75:
                    compat.setUICompat97To2003(xmlReader.readBoolVal());
                    break;
                case /*"revisionView"*/76:
                    readRevisionView(xmlReader, docPr);
                    break;
                case /*"themeFontLang"*/77:
                    readThemeFontLang(xmlReader, docPr.ThemeFontLanguages);
                    break;
                case /*"clrSchemeMapping"*/78:
                    // andrnosk: We do not read this element but write ClrSchemeMapping with default values to Settings (see WORDSNET-4961).
                    xmlReader.ignoreElementNoWarn();
                    break;
                case /*"attachedSchema"*/79:
                    docPr.XmlSchemaReferences.add(xmlReader.readVal(), null);
                    break;
                case /*"schemaLibrary"*/80:
                    readAttachedSchemaLibrary(xmlReader, docPr);
                    break;
                case /*"hideSpellingErrors"*/81:
                    docPr.HideSpellingErrors = xmlReader.readBoolVal();
                    break;
                case /*"hideGrammaticalErrors"*/82:
                    docPr.HideGrammaticalErrors = xmlReader.readBoolVal();
                    break;
                case /*"shapeDefaults"*/83:
                case /*"hdrShapeDefaults"*/84:
                    NrxShapeDefaultsReader.read(xmlReader, reader.ConnectorRules);
                    break;
                case /*"decimalSymbol"*/85:
                case /*"listSeparator"*/86:
                {
                    // Word ignores these elements and always reads the system locale settings
                    // to get the values of the radix point symbol and the list item separator.
                    xmlReader.ignoreElementNoWarn();
                    break;
                }
                case /*"savePreviewPicture"*/87:
                {
                    // Looks like MS Word just removes this option (and a thumbnail), so ignore it.
                    xmlReader.ignoreElementNoWarn();
                    break;
                }
                case /*"clickAndTypeStyle"*/88:
                    reader.ClickAndTypeStyleId = xmlReader.readVal();
                    break;
                case /*"defaultTableStyle"*/89:
                    reader.DefaultTableStyleId = xmlReader.readVal();
                    break;
                case /*"doNotAutoCompressPictures"*/90:
                    docPr.DoNotAutoCompressPictures = xmlReader.readBoolVal();
                    break;
                case /*"updateFields"*/91:
                    docPr.UpdateFields = xmlReader.readBoolVal();
                    break;
                case /*"forceUpgrade"*/92:
                    docPr.ForceUpgrade = xmlReader.readBoolVal();
                    break;
                case /*"chartTrackingRefBased"*/93: // w15:chartTrackingRefBased
                {
                    complianceInfo.isDocxExtensions(true);
                    docPr.ChartTrackingRefBased = xmlReader.readBoolVal();
                    break;
                }
                case /*"docId"*/94:
                {
                    if (msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.W1_5_MARKUP,
                            complianceInfo.getCompliance() == OoxmlComplianceCore.ISO_STRICT)))
                    {
                        complianceInfo.isDocxExtensions(true);
                        docPr.DocumentSetId = xmlReader.readVal();
                    }
                    else if (msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.W1_4_MARKUP,
                            complianceInfo.getCompliance() == OoxmlComplianceCore.ISO_STRICT)))
                    {
                        complianceInfo.isDocxExtensions(true);
                        docPr.DocId = xmlReader.readVal();
                    }
                    else
                    {
                        xmlReader.ignoreElement();
                    }
                    break;
                }
                case /*"discardImageEditingData"*/95: // w14:discardImageEditingData 
                {
                    complianceInfo.isDocxExtensions(true);
                    docPr.DiscardImageEditingData = xmlReader.readBoolVal();
                    break;
                }
                case /*"defaultImageDpi"*/96: // w14:defaultImageDpi
                {
                    complianceInfo.isDocxExtensions(true);
                    docPr.DefaultImageDpi = xmlReader.readIntVal();
                    break;
                }

                case /*"rsids"*/97:
                    NrxSettingsReader.readRsids(xmlReader, docPr);
                    break;

                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.restorePartReader();
    }

    /// <summary>
    /// Read attributes of stylePaneFormatFilter 
    /// (Suggested Filtering for List of Document Styles)
    /// </summary>
    private static void readStylePaneFormatFilterSettings(
        NrxXmlReader xmlReader,
        StylePaneFormatFilterSettings settings,
        OoxmlComplianceInfo complianceInfo)
    {
        while (xmlReader.moveToNextAttribute())
        {
            boolean isUnknownAttribute = false;

            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"allStyles"*/98:
                    settings.setAllStyles(xmlReader.getValueAsBool());
                    break;
                case /*"alternateStyleNames"*/99:
                    settings.setAlternateStyleNames(xmlReader.getValueAsBool());
                    break;
                case /*"clearFormatting"*/100:
                    settings.setClearFormatting(xmlReader.getValueAsBool());
                    break;
                case /*"customStyles"*/101:
                    settings.setCustomStyles(xmlReader.getValueAsBool());
                    break;
                case /*"directFormattingOnNumbering"*/102:
                    settings.setDirectFormattingOnNumbering(xmlReader.getValueAsBool());
                    break;
                case /*"directFormattingOnParagraphs"*/103:
                    settings.setDirectFormattingOnParagraphs(xmlReader.getValueAsBool());
                    break;
                case /*"directFormattingOnRuns"*/104:
                    settings.setDirectFormattingOnRuns(xmlReader.getValueAsBool());
                    break;
                case /*"directFormattingOnTables"*/105:
                    settings.setDirectFormattingOnTables(xmlReader.getValueAsBool());
                    break;
                case /*"headingStyles"*/106:
                    settings.setHeadingStyles(xmlReader.getValueAsBool());
                    break;
                case /*"latentStyles"*/107:
                    settings.setLatentStyles(xmlReader.getValueAsBool());
                    break;
                case /*"numberingStyles"*/108:
                    settings.setNumberingStyles(xmlReader.getValueAsBool());
                    break;
                case /*"stylesInUse"*/109:
                    settings.setStylesInUse(xmlReader.getValueAsBool());
                    break;
                case /*"tableStyles"*/110:
                    settings.setTableStyles(xmlReader.getValueAsBool());
                    break;
                case /*"top3HeadingStyles"*/111:
                    settings.setTop3HeadingStyles(xmlReader.getValueAsBool());
                    break;
                case /*"visibleStyles"*/112:
                    settings.setVisibleStyles(xmlReader.getValueAsBool());
                    break;
                default:
                    // We don't know this attribute. Skip it.
                    isUnknownAttribute = true;
                    xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, xmlReader.getValue());
                    break;
            }

            if (!isUnknownAttribute)
                complianceInfo.markAsIsoTransitional();
        }
    }

    private static String readNoLineBreaks(NrxXmlReader xmlReader, DocPr docPr)
    {
        String value = "";
        while (xmlReader.moveToNextAttribute())
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"val"*/113:
                    value = xmlReader.getValue();
                    break;
                case /*"lang"*/114:
                    docPr.NoLineBreaksLanguage = (/*Language*/int)LocaleConverter.docxTagToLocale(xmlReader.getValue());
                    break;
                default:
                    // We don't know this attribute. Skip it.
                    xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, xmlReader.getValue());
                    break;
            }
        return value;
    }

    private static void readAttachedSchemaLibrary(NrxXmlReader xmlReader, DocPr docPr)
    {
        String uri = null;
        String manifestLocation = null;
        String schemaLanguage = null;
        String schemaLocation = null;

        while (xmlReader.readChild("schemaLibrary"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"schema"*/115:
                {
                    while (xmlReader.moveToNextAttribute())
                        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                        {
                            case /*"uri"*/116:
                                uri = xmlReader.getValue();
                                break;
                            case /*"manifestLocation"*/117:
                                manifestLocation = xmlReader.getValue();
                                break;
                            case /*"schemaLanguage"*/118:
                                schemaLanguage = xmlReader.getValue();
                                break;
                            case /*"schemaLocation"*/119:
                                schemaLocation = xmlReader.getValue();
                                break;
                            default:
                                xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, xmlReader.getValue());
                                break;
                        }
                    XmlNamespace xmlNamespace = new XmlNamespace(uri, manifestLocation, schemaLanguage, schemaLocation);
                    docPr.XmlNamespaces.add(xmlNamespace);
                    break;
                }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readMathProperties(NrxXmlReader xmlReader, MathProperties mathProperties,
        OoxmlComplianceInfo complianceInfo)
    {
        while (xmlReader.readChild("mathPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"brkBin"*/120:
                    mathProperties.setBreakOnBinary(DocxDopEnum.docxToMathBreakOnBinary(xmlReader.readVal()));
                    break;
                case /*"brkBinSub"*/121:
                    mathProperties.setBreakOnBinarySubtraction(DocxDopEnum.docxToMathBreakOnBinarySubtraction(xmlReader.readVal()));
                    break;
                case /*"defJc"*/122:
                    mathProperties.setDefaultJustification(DocxEnum.docxToMathJustification(xmlReader.readVal()));
                    break;
                case /*"dispDef"*/123:
                    mathProperties.setUseDisplayMathDefaults(xmlReader.readBoolVal());
                    break;
                case /*"interSp"*/124:
                    mathProperties.setInterEquationSpacing(xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"intLim"*/125:
                    mathProperties.setIntegralLimitLocation(DocxEnum.docxToMathLimitLocation(xmlReader.readVal()));
                    break;
                case /*"intraSp"*/126:
                    mathProperties.setIntraEquationSpacing(xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"lMargin"*/127:
                    mathProperties.setLeftMargin(xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"mathFont"*/128:
                    mathProperties.setDefaultFont(xmlReader.readVal());
                    break;
                case /*"naryLim"*/129:
                    mathProperties.setNaryLimitLocation(DocxEnum.docxToMathLimitLocation(xmlReader.readVal()));
                    break;
                case /*"postSp"*/130:
                    mathProperties.setPostParagraphSpacing(xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"preSp"*/131:
                    mathProperties.setPreParagraphSpacing(xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"rMargin"*/132:
                    mathProperties.setRightMargin(xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"smallFrac"*/133:
                    mathProperties.isSmallFraction(xmlReader.readBoolVal());
                    break;
                case /*"wrapIndent"*/134:
                    mathProperties.setWrapIndent(xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"wrapRight"*/135:
                    mathProperties.setWrapRight(xmlReader.readBoolVal());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }


    /// <summary>
    /// Reads 'w:zoom' element.
    /// </summary>
    private static void readZoom(DocxXmlReader xmlReader, ViewOptions viewOptions)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"val"*/113:
                    viewOptions.setZoomType(DocxDopEnum.docxToZoomType(xmlReader.getValue()));
                    break;
                case /*"percent"*/136:
                    viewOptions.setZoomPercent(NrxXmlReader.xmlToPercent(xmlReader.getValue(), xmlReader.getComplianceInfo()));
                    break;
                default:
                    // We don't know this attribute. Skip it.
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, xmlReader.getLocalName());
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 'w:proofState' element.
    /// </summary>
    private static void readProofState(NrxXmlReader xmlReader, DocPr docPr)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"spelling"*/137:
                    docPr.ProofStateSpelling = DocxDopEnum.docxToProofState(xmlReader.getValue());
                    break;
                case /*"grammar"*/138:
                    docPr.ProofStateGrammar = DocxDopEnum.docxToProofState(xmlReader.getValue());
                    break;
                default:
                    // We don't know this attribute. Skip it.
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, xmlReader.getLocalName());
                    break;
            }
        }
    }

    private static void readWriteProtection(NrxXmlReader xmlReader, DocPr docPr, OoxmlComplianceInfo ciInfo)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"recommended"*/139:
                    docPr.WriteProtection.setReadOnlyRecommended(xmlReader.getValueAsBool());
                    break;
                default:
                    readPasswordHashAttr(xmlReader, docPr.WriteProtection.getPasswordHash(), ciInfo);
                    break;
            }
        }
    }

    private static void readDocumentProtection(NrxXmlReader xmlReader, DocumentProtection docProt, OoxmlComplianceInfo cInfo)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"edit"*/140:
                    docProt.setEdit(DocxDopEnum.docxToProtectionType(xmlReader.getValue()));
                    break;
                case /*"formatting"*/141:
                    docProt.setFormatting(xmlReader.getValueAsBool());
                    break;
                case /*"enforcement"*/142:
                    docProt.setEnforcement(xmlReader.getValueAsBool());
                    break;
                default:
                    readPasswordHashAttr(xmlReader, docProt.getPasswordHash(), cInfo);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads a document protection password or a write password. 
    /// The reader must be positioned on the protection element.
    /// </summary>
    private static void readPasswordHashAttr(NrxXmlReader xmlReader, PasswordHash passwordHash, OoxmlComplianceInfo cInfo)
    {
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"cryptProviderType"*/143:
                // RK This can be "rsaAES" or "rsaFull" according to the spec.
                // I don't understand what this can be used for, lets ignore.
                break;
            case /*"cryptAlgorithmClass"*/144:
                // RK In current OOXML spec this can only be "hash". Lets ignore.
                break;
            case /*"cryptAlgorithmType"*/145:
                // RK In the current OOXML spec this can only be "typeAny". Lets ignore.
                break;
            case /*"cryptAlgorithmSid"*/146:
                passwordHash.CryptAlgorithmSid = xmlReader.getValueAsInt();
                break;
            case /*"algorithmName"*/147:
            {
                cInfo.markAsIsoTransitional();
                passwordHash.CryptAlgorithmSid =
                    PasswordHash.cryptAlgorithmSidFromAlgorithmName(xmlReader.getValue());
                break;
            }
            case /*"cryptSpinCount"*/148:
                passwordHash.CryptSpinCount = xmlReader.getValueAsInt();
                break;
            case /*"spinCount"*/149: // ISO 29500 defined attribute.
            {
                cInfo.markAsIsoTransitional();
                passwordHash.CryptSpinCount = xmlReader.getValueAsInt();
                break;
            }
            case /*"hash"*/150:
                passwordHash.Hash = tryConvertFromBase64(xmlReader);
                break;
            case /*"hashValue"*/151: // ISO 29500 defined attribute.
            {
                cInfo.markAsIsoTransitional();
                passwordHash.Hash = tryConvertFromBase64(xmlReader);
                break;
            }
            case /*"salt"*/152:
                passwordHash.Salt = tryConvertFromBase64(xmlReader);
                break;
            case /*"saltValue"*/153: // ISO 29500 defined attribute.
            {
                cInfo.markAsIsoTransitional();
                passwordHash.Salt = tryConvertFromBase64(xmlReader);
                break;
            }
            default:
                xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, xmlReader.getLocalName());
                // Do nothing.
                break;
        }
    }

    private static void readCompat(NrxXmlReader xmlReader, CompatibilityOptions compat, OoxmlComplianceInfo cInfo)
    {
        while (xmlReader.readChild("compat"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"adjustLineHeightInTable"*/154:
                    compat.setAdjustLineHeightInTable(xmlReader.readBoolVal());
                    break;
                case /*"alignTablesRowByRow"*/155:
                    compat.setAlignTablesRowByRow(xmlReader.readBoolVal());
                    break;
                case /*"allowSpaceOfSameStyleInTable"*/156:
                    compat.setAllowSpaceOfSameStyleInTable(xmlReader.readBoolVal());
                    break;
                case /*"applyBreakingRules"*/157:
                    compat.setApplyBreakingRules(xmlReader.readBoolVal());
                    break;
                case /*"autofitToFirstFixedWidthCell"*/158:
                    compat.setAutofitToFirstFixedWidthCell(xmlReader.readBoolVal());
                    break;
                case /*"autoSpaceLikeWord95"*/159:
                    compat.setAutoSpaceLikeWord95(xmlReader.readBoolVal());
                    break;
                case /*"balanceSingleByteDoubleByteWidth"*/160:
                    compat.setBalanceSingleByteDoubleByteWidth(xmlReader.readBoolVal());
                    break;
                case /*"cachedColBalance"*/161:
                    compat.setCachedColBalance(xmlReader.readBoolVal());
                    break;
                case /*"convMailMergeEsc"*/162:
                    compat.setConvMailMergeEsc(xmlReader.readBoolVal());
                    break;
                case /*"displayHangulFixedWidth"*/163:
                    compat.setDisplayHangulFixedWidth(xmlReader.readBoolVal());
                    break;
                case /*"doNotAutofitConstrainedTables"*/164:
                    compat.setDoNotAutofitConstrainedTables(xmlReader.readBoolVal());
                    break;
                case /*"doNotBreakConstrainedForcedTable"*/165:
                    compat.setDoNotBreakConstrainedForcedTable(xmlReader.readBoolVal());
                    break;
                case /*"doNotBreakWrappedTables"*/166:
                    compat.setDoNotBreakWrappedTables(xmlReader.readBoolVal());
                    break;
                case /*"doNotExpandShiftReturn"*/167:
                    compat.setDoNotExpandShiftReturn(xmlReader.readBoolVal());
                    break;
                case /*"doNotLeaveBackslashAlone"*/168:
                    compat.setDoNotLeaveBackslashAlone(xmlReader.readBoolVal());
                    break;
                case /*"doNotSnapToGridInCell"*/169:
                    compat.setDoNotSnapToGridInCell(xmlReader.readBoolVal());
                    break;
                case /*"doNotSuppressIndentation"*/170:
                    compat.setDoNotSuppressIndentation(xmlReader.readBoolVal());
                    break;
                case /*"doNotSuppressParagraphBorders"*/171:
                    compat.setDoNotSuppressParagraphBorders(xmlReader.readBoolVal());
                    break;
                case /*"doNotUseEastAsianBreakRules"*/172:
                    compat.setDoNotUseEastAsianBreakRules(xmlReader.readBoolVal());
                    break;
                case /*"doNotUseHTMLParagraphAutoSpacing"*/173:
                    compat.setDoNotUseHTMLParagraphAutoSpacing(xmlReader.readBoolVal());
                    break;
                case /*"doNotUseIndentAsNumberingTabStop"*/174:
                    compat.setDoNotUseIndentAsNumberingTabStop(xmlReader.readBoolVal());
                    break;
                case /*"doNotVertAlignCellWithSp"*/175:
                    compat.setDoNotVertAlignCellWithSp(xmlReader.readBoolVal());
                    break;
                case /*"doNotVertAlignInTxbx"*/176:
                    compat.setDoNotVertAlignInTxbx(xmlReader.readBoolVal());
                    break;
                case /*"doNotWrapTextWithPunct"*/177:
                    compat.setDoNotWrapTextWithPunct(xmlReader.readBoolVal());
                    break;
                case /*"footnoteLayoutLikeWW8"*/178:
                    compat.setFootnoteLayoutLikeWW8(xmlReader.readBoolVal());
                    break;
                case /*"forgetLastTabAlignment"*/179:
                    compat.setForgetLastTabAlignment(xmlReader.readBoolVal());
                    break;
                case /*"growAutofit"*/180:
                    compat.setGrowAutofit(xmlReader.readBoolVal());
                    break;
                case /*"layoutRawTableWidth"*/181:
                    compat.setLayoutRawTableWidth(xmlReader.readBoolVal());
                    break;
                case /*"layoutTableRowsApart"*/182:
                    compat.setLayoutTableRowsApart(xmlReader.readBoolVal());
                    break;
                case /*"lineWrapLikeWord6"*/183:
                    compat.setLineWrapLikeWord6(xmlReader.readBoolVal());
                    break;
                case /*"mwSmallCaps"*/184:
                    compat.setMWSmallCaps(xmlReader.readBoolVal());
                    break;
                case /*"noColumnBalance"*/185:
                    compat.setNoColumnBalance(xmlReader.readBoolVal());
                    break;
                case /*"noExtraLineSpacing"*/186:
                    compat.setNoExtraLineSpacing(xmlReader.readBoolVal());
                    break;
                case /*"noLeading"*/187:
                    compat.setNoLeading(xmlReader.readBoolVal());
                    break;
                case /*"noSpaceRaiseLower"*/188:
                    compat.setNoSpaceRaiseLower(xmlReader.readBoolVal());
                    break;
                case /*"noTabHangInd"*/189:
                    compat.setNoTabHangInd(xmlReader.readBoolVal());
                    break;
                case /*"printBodyTextBeforeHeader"*/190:
                    compat.setPrintBodyTextBeforeHeader(xmlReader.readBoolVal());
                    break;
                case /*"printColBlack"*/191:
                    compat.setPrintColBlack(xmlReader.readBoolVal());
                    break;
                case /*"selectFldWithFirstOrLastChar"*/192:
                    compat.setSelectFldWithFirstOrLastChar(xmlReader.readBoolVal());
                    break;
                case /*"shapeLayoutLikeWW8"*/193:
                    compat.setShapeLayoutLikeWW8(xmlReader.readBoolVal());
                    break;
                case /*"showBreaksInFrames"*/194:
                    compat.setShowBreaksInFrames(xmlReader.readBoolVal());
                    break;
                case /*"spaceForUL"*/195:
                    compat.setSpaceForUL(xmlReader.readBoolVal());
                    break;
                case /*"spacingInWholePoints"*/196:
                    compat.setSpacingInWholePoints(xmlReader.readBoolVal());
                    break;
                case /*"splitPgBreakAndParaMark"*/197:
                    compat.setSplitPgBreakAndParaMark(xmlReader.readBoolVal());
                    break;
                case /*"subFontBySize"*/198:
                    compat.setSubFontBySize(xmlReader.readBoolVal());
                    break;
                case /*"suppressBottomSpacing"*/199:
                    compat.setSuppressBottomSpacing(xmlReader.readBoolVal());
                    break;
                case /*"suppressSpacingAtTopOfPage"*/200:
                    compat.setSuppressSpacingAtTopOfPage(xmlReader.readBoolVal());
                    break;
                case /*"suppressSpBfAfterPgBrk"*/201:
                    compat.setSuppressSpBfAfterPgBrk(xmlReader.readBoolVal());
                    break;
                case /*"suppressTopSpacing"*/202:
                    compat.setSuppressTopSpacing(xmlReader.readBoolVal());
                    break;
                case /*"suppressTopSpacingWP"*/203:
                    compat.setSuppressTopSpacingWP(xmlReader.readBoolVal());
                    break;
                case /*"swapBordersFacingPages"*/204:
                    compat.setSwapBordersFacingPgs(xmlReader.readBoolVal());
                    break;
                case /*"truncateFontHeightsLikeWP6"*/205:
                    compat.setTruncateFontHeightsLikeWP6(xmlReader.readBoolVal());
                    break;
                case /*"ulTrailSpace"*/206:
                    compat.setUlTrailSpace(xmlReader.readBoolVal());
                    break;
                case /*"underlineTabInNumList"*/207:
                    compat.setUnderlineTabInNumList(xmlReader.readBoolVal());
                    break;
                case /*"useAltKinsokuLineBreakRules"*/208:
                    compat.setUseAltKinsokuLineBreakRules(xmlReader.readBoolVal());
                    break;
                case /*"useAnsiKerningPairs"*/209:
                    compat.setUseAnsiKerningPairs(xmlReader.readBoolVal());
                    break;
                case /*"useFELayout"*/210:
                    compat.setUseFELayout(xmlReader.readBoolVal());
                    break;
                case /*"useNormalStyleForList"*/211:
                    compat.setUseNormalStyleForList(xmlReader.readBoolVal());
                    break;
                case /*"usePrinterMetrics"*/212:
                    compat.setUsePrinterMetrics(xmlReader.readBoolVal());
                    break;
                case /*"useSingleBorderforContiguousCells"*/213:
                    compat.setUseSingleBorderforContiguousCells(xmlReader.readBoolVal());
                    break;
                case /*"useWord2002TableStyleRules"*/214:
                    compat.setUseWord2002TableStyleRules(xmlReader.readBoolVal());
                    break;
                case /*"useWord97LineBreakRules"*/215:
                    compat.setUseWord97LineBreakRules(xmlReader.readBoolVal());
                    break;
                case /*"wpJustification"*/216:
                    compat.setWPJustification(xmlReader.readBoolVal());
                    break;
                case /*"wpSpaceWidth"*/217:
                    compat.setWPSpaceWidth(xmlReader.readBoolVal());
                    break;
                case /*"wrapTrailSpaces"*/218:
                    compat.setWrapTrailSpaces(xmlReader.readBoolVal());
                    break;
                case /*"compatSetting"*/219: // ISO 29500 specific.
                    readCustomCompatibilitySetting(compat, xmlReader, cInfo);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readCustomCompatibilitySetting(CompatibilityOptions compat, NrxXmlReader xmlReader, OoxmlComplianceInfo cInfo)
    {
        String name = "";
        String uri = "";
        String val = "";

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"name"*/220:
                    name = xmlReader.getValue();
                    break;
                case /*"uri"*/116:
                    uri = xmlReader.getValue();
                    break;
                case /*"val"*/113:
                    val = xmlReader.getValue();
                    break;
                default:
                    // We don't know this attribute. Skip it.
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, xmlReader.getLocalName());
                    break;
            }
        }
        cInfo.markAsIsoTransitional();
        if ("compatibilityMode".equals(name))
            compat.setMswVersion(OoxmlComplianceInfo.msWordVersionFromCompatibilityMode(val));

        compat.getCustomCompatibilitySettings().add(new CustomCompatibilitySetting(name, uri, val));
    }

    private static void readVars(NrxXmlReader xmlReader, VariableCollection vars)
    {
        while (xmlReader.readChild("docVars"))
        {
            if ("docVar".equals(xmlReader.getLocalName()))
            {
                String name = null;
                String val = null;

                while (xmlReader.moveToNextAttribute())
                {
                    switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                    {
                        case /*"name"*/220:
                            name = xmlReader.getValue();
                            break;
                        case /*"val"*/113:
                            // WORDSNET-9275/9830/11735
                            val = xmlReader.getValue().replace("_x000d_", "\r").replace("_x000D_", "\r").replace("_x000b_", "\u000b").replace("_x000B_", "\u000b");
                            break;
                        default:
                            // We don't know this attribute. Skip it.
                            xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, xmlReader.getLocalName());
                            break;
                    }
                }

                if (name != null)
                    vars.add(name, val);
            }
            else if ("docVars".equals(xmlReader.getLocalName()))
            {
                // WORDSNET-6248 Nested 'docVars' elements. 
                // MS Word loads file successfully and has access to variables so we have to read it.
                readVars(xmlReader, vars);
            }
            else
            {
                xmlReader.ignoreElement();
            }
        }
    }

    private static void readRevisionView(NrxXmlReader xmlReader, DocPr docPr)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"comments"*/221:
                    docPr.ShowAnnotations = xmlReader.getValueAsBool();
                    break;
                case /*"formatting"*/141:
                    docPr.ShowFormatting = xmlReader.getValueAsBool();
                    break;
                case /*"inkAnnotations"*/222:
                    docPr.ShowInkAnnotations = xmlReader.getValueAsBool();
                    break;
                case /*"insDel"*/223:
                    docPr.ShowInsertionsDeletions = xmlReader.getValueAsBool();
                    break;
                case /*"markup"*/224:
                    docPr.ShowMarkup = xmlReader.getValueAsBool();
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, xmlReader.getLocalName());
                    break;
            }
        }
    }

    static void readFootnotePr(DocxDocumentReaderBase reader, String elemName, AttrCollection attrs)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        boolean isEndnote = ("endnotePr".equals(elemName));
        int keyDelta = (isEndnote) ? SectAttr.ENDNOTE_KEY_DELTA : 0;

        while (xmlReader.readChild(elemName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"pos"*/225:
                {
                    attrs.setAttr(SectAttr.FOOTNOTE_LOCATION + keyDelta, isEndnote
                        ? (Object)NrxSectEnum.xmlToEndnotePosition(xmlReader.readVal())
                        : (Object)NrxSectEnum.xmlToFootnotePosition(xmlReader.readVal()));
                    break;
                }
                case /*"numFmt"*/226:
                    attrs.setAttr(SectAttr.FOOTNOTE_NUMBER_STYLE + keyDelta, DocxEnum.docxToNumberStyle(xmlReader.readVal()));
                    break;
                case /*"numStart"*/227:
                    attrs.setAttr(SectAttr.FOOTNOTE_START_NUMBER + keyDelta, xmlReader.readIntVal());
                    break;
                case /*"numRestart"*/228:
                    attrs.setAttr(SectAttr.FOOTNOTE_NUMBERING_RULE + keyDelta, NrxSectEnum.xmlToNoteNumberingRule(xmlReader.readVal()));
                    break;
                case /*"footnote"*/229:
                case /*"endnote"*/230:
                {
                    // WORDSNET-16479 Notify customer that footnote/endnote part is missing.
                    String relType = ("footnote".equals(xmlReader.getLocalName()))
                        ? reader.getRelTypes().getFootnotes()
                        : reader.getRelTypes().getEndnotes();

                    if(reader.getPartByRelationshipType(reader.getDocumentPart(), relType) == null)
                        reader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, WarningStrings.FOOTNOTE_PART_MISSING);

                    // These occur in document-wide element and are references to footnote separators.
                    // It is not ideal, but we read separators straight away without following this reference.
                    // andrnosk: I think we do not need to warn user in this case.
                    xmlReader.ignoreElementNoWarn();
                    break;
                }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readThemeFontLang(NrxXmlReader xmlReader, ThemeFontLanguages languages)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"bidi"*/231:
                    languages.Bidi = (/*Language*/int)LocaleConverter.docxTagToLocale(xmlReader.getValue());
                    break;
                case /*"eastAsia"*/232:
                    languages.EastAsia = (/*Language*/int)LocaleConverter.docxTagToLocale(xmlReader.getValue());
                    break;
                case /*"val"*/113:
                    languages.Latin = (/*Language*/int)LocaleConverter.docxTagToLocale(xmlReader.getValue());
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, xmlReader.getLocalName());
                    break;
            }
        }
    }

    /// <summary>
    /// Returns a byte array that is equivalent to the given base64 string or null if the conversion failed.
    /// </summary>
    private static byte[] tryConvertFromBase64(NrxXmlReader xmlReader)
    {
        String base64String = xmlReader.getValue();
        byte[] bytes = StringUtil.convertFromBase64Safe(base64String);

        if (bytes.length == 0)
        {
            // WORDSNET-27395 We have to inform the user that the password to protect the document is corrupted.
            xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, WarningStrings.UNEXPECTED_BASE_64);

            return null;
        }

        return bytes;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"doNotIncludeSubdocsInStats",
		"writeProtection",
		"alwaysMergeEmptyNamespace",
		"alwaysShowPlaceholderText",
		"autoFormatOverride",
		"bookFoldPrinting",
		"bookFoldRevPrinting",
		"mirrorMargins",
		"printTwoOnOne",
		"bookFoldPrintingSheets",
		"embedSystemFonts",
		"printFractionalCharacterWidth",
		"printPostScriptOverText",
		"removePersonalInformation",
		"removeDateAndTime",
		"showEnvelope",
		"doNotValidateAgainstSchema",
		"saveInvalidXml",
		"saveXmlDataOnly",
		"showXMLTags",
		"ignoreMixedContent",
		"useXSLTWhenSaving",
		"saveThroughXslt",
		"view",
		"zoom",
		"doNotDisplayPageBoundaries",
		"displayBackgroundShape",
		"printFormsData",
		"embedTrueTypeFonts",
		"saveSubsetFonts",
		"saveFormsData",
		"alignBordersAndEdges",
		"bordersDoNotSurroundHeader",
		"bordersDoNotSurroundFooter",
		"doNotDemarcateInvalidXml",
		"doNotEmbedSmartTags",
		"gutterAtTop",
		"proofState",
		"formsDesign",
		"attachedTemplate",
		"linkStyles",
		"documentType",
		"mailMerge",
		"mathPr",
		"styleLockQFSet",
		"styleLockTheme",
		"stylePaneFormatFilter",
		"stylePaneSortMethod",
		"trackRevisions",
		"doNotTrackMoves",
		"doNotTrackFormatting",
		"documentProtection",
		"defaultTabStop",
		"autoHyphenation",
		"consecutiveHyphenLimit",
		"hyphenationZone",
		"doNotHyphenateCaps",
		"evenAndOddHeaders",
		"drawingGridHorizontalSpacing",
		"drawingGridVerticalSpacing",
		"displayHorizontalDrawingGridEvery",
		"displayVerticalDrawingGridEvery",
		"doNotUseMarginsForDrawingGridOrigin",
		"drawingGridHorizontalOrigin",
		"drawingGridVerticalOrigin",
		"doNotShadeFormData",
		"noPunctuationKerning",
		"characterSpacingControl",
		"strictFirstAndLastChars",
		"noLineBreaksAfter",
		"noLineBreaksBefore",
		"footnotePr",
		"endnotePr",
		"compat",
		"docVars",
		"uiCompat97To2003",
		"revisionView",
		"themeFontLang",
		"clrSchemeMapping",
		"attachedSchema",
		"schemaLibrary",
		"hideSpellingErrors",
		"hideGrammaticalErrors",
		"shapeDefaults",
		"hdrShapeDefaults",
		"decimalSymbol",
		"listSeparator",
		"savePreviewPicture",
		"clickAndTypeStyle",
		"defaultTableStyle",
		"doNotAutoCompressPictures",
		"updateFields",
		"forceUpgrade",
		"chartTrackingRefBased",
		"docId",
		"discardImageEditingData",
		"defaultImageDpi",
		"rsids",
		"allStyles",
		"alternateStyleNames",
		"clearFormatting",
		"customStyles",
		"directFormattingOnNumbering",
		"directFormattingOnParagraphs",
		"directFormattingOnRuns",
		"directFormattingOnTables",
		"headingStyles",
		"latentStyles",
		"numberingStyles",
		"stylesInUse",
		"tableStyles",
		"top3HeadingStyles",
		"visibleStyles",
		"val",
		"lang",
		"schema",
		"uri",
		"manifestLocation",
		"schemaLanguage",
		"schemaLocation",
		"brkBin",
		"brkBinSub",
		"defJc",
		"dispDef",
		"interSp",
		"intLim",
		"intraSp",
		"lMargin",
		"mathFont",
		"naryLim",
		"postSp",
		"preSp",
		"rMargin",
		"smallFrac",
		"wrapIndent",
		"wrapRight",
		"percent",
		"spelling",
		"grammar",
		"recommended",
		"edit",
		"formatting",
		"enforcement",
		"cryptProviderType",
		"cryptAlgorithmClass",
		"cryptAlgorithmType",
		"cryptAlgorithmSid",
		"algorithmName",
		"cryptSpinCount",
		"spinCount",
		"hash",
		"hashValue",
		"salt",
		"saltValue",
		"adjustLineHeightInTable",
		"alignTablesRowByRow",
		"allowSpaceOfSameStyleInTable",
		"applyBreakingRules",
		"autofitToFirstFixedWidthCell",
		"autoSpaceLikeWord95",
		"balanceSingleByteDoubleByteWidth",
		"cachedColBalance",
		"convMailMergeEsc",
		"displayHangulFixedWidth",
		"doNotAutofitConstrainedTables",
		"doNotBreakConstrainedForcedTable",
		"doNotBreakWrappedTables",
		"doNotExpandShiftReturn",
		"doNotLeaveBackslashAlone",
		"doNotSnapToGridInCell",
		"doNotSuppressIndentation",
		"doNotSuppressParagraphBorders",
		"doNotUseEastAsianBreakRules",
		"doNotUseHTMLParagraphAutoSpacing",
		"doNotUseIndentAsNumberingTabStop",
		"doNotVertAlignCellWithSp",
		"doNotVertAlignInTxbx",
		"doNotWrapTextWithPunct",
		"footnoteLayoutLikeWW8",
		"forgetLastTabAlignment",
		"growAutofit",
		"layoutRawTableWidth",
		"layoutTableRowsApart",
		"lineWrapLikeWord6",
		"mwSmallCaps",
		"noColumnBalance",
		"noExtraLineSpacing",
		"noLeading",
		"noSpaceRaiseLower",
		"noTabHangInd",
		"printBodyTextBeforeHeader",
		"printColBlack",
		"selectFldWithFirstOrLastChar",
		"shapeLayoutLikeWW8",
		"showBreaksInFrames",
		"spaceForUL",
		"spacingInWholePoints",
		"splitPgBreakAndParaMark",
		"subFontBySize",
		"suppressBottomSpacing",
		"suppressSpacingAtTopOfPage",
		"suppressSpBfAfterPgBrk",
		"suppressTopSpacing",
		"suppressTopSpacingWP",
		"swapBordersFacingPages",
		"truncateFontHeightsLikeWP6",
		"ulTrailSpace",
		"underlineTabInNumList",
		"useAltKinsokuLineBreakRules",
		"useAnsiKerningPairs",
		"useFELayout",
		"useNormalStyleForList",
		"usePrinterMetrics",
		"useSingleBorderforContiguousCells",
		"useWord2002TableStyleRules",
		"useWord97LineBreakRules",
		"wpJustification",
		"wpSpaceWidth",
		"wrapTrailSpaces",
		"compatSetting",
		"name",
		"comments",
		"inkAnnotations",
		"insDel",
		"markup",
		"pos",
		"numFmt",
		"numStart",
		"numRestart",
		"footnote",
		"endnote",
		"bidi",
		"eastAsia"
	);

}

