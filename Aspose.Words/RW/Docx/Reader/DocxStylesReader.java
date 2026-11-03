// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.LocaleClassifier;


/// <summary>
/// Provides methods for reading "Styles" package part
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxStylesReader
{
    DocxStylesReader(DocxParaPrReader paraPrReader,
        DocxRunPrReader runPrReader)
    {
        Debug.assertTrue(paraPrReader != null);
        Debug.assertTrue(runPrReader != null);
        mParaPrReader = paraPrReader;
        mRunPrReader = runPrReader;
    }

    /// <summary>
    /// Reads "Styles" part for the specified document.
    /// </summary>
    void read(DocxDocumentReaderBase reader) throws Exception
    {
        if (reader.getLoadOptions().getSkipFormatting())
            return;

        boolean defaultsRead = false;
        NrxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getStyles());
        if (xmlReader != null)
        {
            NrxUnresolvedStylePartCollection unresolvedParts = new NrxUnresolvedStylePartCollection();

            while (xmlReader.readChild("styles"))
            {
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"docDefaults"*/0:
                        defaultsRead = true;
                        readDocDefaults(reader);
                        break;
                    case /*"latentStyles"*/1:
                        readLatentStyles(reader);
                        break;
                    case /*"style"*/2:
                        readStyle(reader, unresolvedParts);
                        break;
                    default:
                        xmlReader.ignoreElement();
                        break;
                }
            }

            unresolvedParts.resolveStyleLinks(reader);
            reader.restorePartReader();
        }

        // When either rPrDefault or pPrDefault tags are missing,
        // Word loads some predefined document defaults depending on Word application version.
        if (!defaultsRead)
        {
            initDefaultParaPr(reader);
            initDefaultRunPr(reader);
        }

        // WORDSNET-23715 Fix base styles before we started to resolve toggle issues.
        reader.getDocument().getStyles().fixUpBasedOnStyles();
        // WORDSNET-26869 Fixed style name case issue.
        reader.getDocument().getStyles().fixUpCaseIssue();
    }

    private void readDocDefaults(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        boolean defaultRunPrRead = false;
        boolean defaultParaPrRead = false;

        while (xmlReader.readChild("docDefaults"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rPrDefault"*/3:
                    defaultRunPrRead = true;
                    readRPrDefault(reader);
                    break;
                case /*"pPrDefault"*/4:
                    defaultParaPrRead = true;
                    readPPrDefault(reader);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        // When either rPrDefault or pPrDefault tags are missing,
        // Word loads some predefined document defaults depending on Word application version.
        if (!defaultRunPrRead)
            initDefaultRunPr(reader);
        if (!defaultParaPrRead)
            initDefaultParaPr(reader);
    }

    private void readRPrDefault(DocxDocumentReaderBase reader) throws Exception
    {
        StyleCollection styles = reader.getDocument().getStyles();

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("rPrDefault"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rPr"*/5:
                {
                    RunPr runPr = styles.getDefaultRunPr();
                    mRunPrReader.read(reader, runPr);
                    break;
                }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private void readPPrDefault(DocxDocumentReaderBase reader) throws Exception
    {
        StyleCollection styles = reader.getDocument().getStyles();

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("pPrDefault"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"pPr"*/6:
                {
                    ParaPr paraPr = styles.getDefaultParaPr();
                    RunPr runPr = styles.getDefaultRunPr();
                    mParaPrReader.read(reader, paraPr, runPr);
                    break;
                }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        // andrnosk: WORDSNET-10126 Mimic MSWord behavior, ignore framePr inside ParaPrDefault.
        styles.getDefaultParaPr().removeFloatingAttrs();
    }

    private static void readLatentStyles(DocxDocumentReaderBase reader)
    {
        LatentStyles latentStyles = reader.getDocument().getStyles().getLatentStyles();
        NrxXmlReader xmlReader = reader.getXmlReader();

        // Read attributes.
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"count"*/7:
                    latentStyles.setKnownStylesCount(xmlReader.getValueAsInt());
                    break;
                case /*"defLockedState"*/8:
                    latentStyles.setDefaultLockedState(xmlReader.getValueAsBool());
                    break;
                case /*"defQFormat"*/9:
                    latentStyles.setDefaultQuickFormat(xmlReader.getValueAsBool());
                    break;
                case /*"defSemiHidden"*/10:
                    latentStyles.setDefaultSemiHidden(xmlReader.getValueAsBool());
                    break;
                case /*"defUIPriority"*/11:
                    latentStyles.setDefaultUIPriority(xmlReader.getValueAsInt());
                    break;
                case /*"defUnhideWhenUsed"*/12:
                    latentStyles.setDefaultUnhideWhenUsed(xmlReader.getValueAsBool());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        // Read elements.
        while (xmlReader.readChild("latentStyles"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"lsdException"*/13:
                    readLsdException(reader);
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }
    }

    private static void readLsdException(DocxDocumentReaderBase reader)
    {
        LatentStyles latentStyles = reader.getDocument().getStyles().getLatentStyles();

        boolean locked = latentStyles.getDefaultLockedState();
        String name = "";
        boolean qFormat = latentStyles.getDefaultQuickFormat();
        boolean semiHidden = latentStyles.getDefaultSemiHidden();
        int uiPriority = latentStyles.getDefaultUIPriority();
        boolean unhideWhenUsed = latentStyles.getDefaultUnhideWhenUsed();

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"locked"*/14:
                    locked = xmlReader.getValueAsBool();
                    break;
                case /*"name"*/15:
                    name = xmlReader.getValue();
                    break;
                case /*"qFormat"*/16:
                    qFormat = xmlReader.getValueAsBool();
                    break;
                case /*"semiHidden"*/17:
                    semiHidden = xmlReader.getValueAsBool();
                    break;
                case /*"uiPriority"*/18:
                    uiPriority = xmlReader.getValueAsInt();
                    break;
                case /*"unhideWhenUsed"*/19:
                    unhideWhenUsed = xmlReader.getValueAsBool();
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        /*StyleIdentifier*/int styleIdentifier = StyleConvertUtil.xmlToStyleIdentifier(name);
        if (styleIdentifier != StyleIdentifier.USER)
        {
            latentStyles.add(new LatentStyle(
                styleIdentifier, locked, qFormat, semiHidden, uiPriority, unhideWhenUsed));
        }
    }

    /// <summary>
    /// Reads 'w:style' element from 'w:styles' children.
    /// </summary>
    private void readStyle(DocxDocumentReaderBase reader, NrxUnresolvedStylePartCollection unresolvedParts) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        StyleCollection styles = reader.getDocument().getStyles();

        // Read attributes into temporary local variables.
        boolean isCustomStyle = false;
        String styleId = null;
        /*StyleType*/int styleType = StyleType.PARAGRAPH;

        boolean isDefault = false;
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"customStyle"*/20:
                    isCustomStyle = xmlReader.getValueAsBool();
                    break;
                case /*"default"*/21:
                    // WORDSNET-23886 Read as boolean, instead of just as 'true' value.
                    isDefault = xmlReader.getValueAsBool();
                    break;
                case /*"styleId"*/22:
                    // This string identifies style in DOCX, we convert it to istd below for the model use.
                    styleId = xmlReader.getValue();
                    break;
                case /*"type"*/23:
                    styleType = DocxEnum.docxToStyleType(xmlReader.getValue());
                    break;
                default:
                    {
                        String message = msString.format(WarningStrings.UNEXPECTED_ATTRIBUTE, xmlReader.getLocalName());
                        reader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, message);
                        break;
                    }
            }
        }

        // This is the style we are building.
        Style style = Style.create(styleType);
        NrxUnresolvedStylePart unresolvedStylePart = new NrxUnresolvedStylePart(style);

        String name = null;
        String aliases = null;

        while (xmlReader.readChild("style"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"name"*/15:
                {
                    // We have to do this as soon as we read the name because this is a primary
                    // non-localized style name and we use it to recognize built-in styles.
                    // Further code that reads formatting properties relies on istd properly set.
                    name = xmlReader.readVal();
                    break;
                }
                case /*"aliases"*/24:
                    aliases = xmlReader.readVal();
                    break;
                case /*"basedOn"*/25:
                    unresolvedStylePart.BasedOn = xmlReader.readVal();
                    break;
                case /*"next"*/26:
                    unresolvedStylePart.Next = xmlReader.readVal();
                    break;
                case /*"link"*/27:
                    unresolvedStylePart.Link = xmlReader.readVal();
                    break;
                case /*"autoRedefine"*/28:
                    style.setAutomaticallyUpdate(xmlReader.readBoolVal());
                    break;
                case /*"hidden"*/29:
                    style.setHidden(xmlReader.readBoolVal());
                    break;
                case /*"uiPriority"*/18:
                    style.setPriority(xmlReader.readIntVal());
                    break;
                case /*"unhideWhenUsed"*/19:
                    style.setUnhideWhenUsed(xmlReader.readBoolVal());
                    break;
                case /*"qFormat"*/16:
                    style.isQuickStyle(xmlReader.readBoolVal());
                    break;
                case /*"semiHidden"*/17:
                    style.setSemiHidden(xmlReader.readBoolVal());
                    break;
                case /*"locked"*/14:
                    style.setLocked(xmlReader.readBoolVal());
                    break;
                case /*"personal"*/30:
                    style.setPersonal(xmlReader.readBoolVal());
                    break;
                case /*"personalCompose"*/31:
                    style.setPersonalCompose(xmlReader.readBoolVal());
                    break;
                case /*"personalReply"*/32:
                    style.setPersonalReply(xmlReader.readBoolVal());
                    break;
                case /*"rsid"*/33:
                {
                    // WORDSNET-5028, xmlReader.ReadVal() can be not hex value, so we try parse and on error return int.MinValue
                    int rsid = NrxXmlUtil.tryHexToInt(xmlReader.readVal());
                    if (rsid != Integer.MIN_VALUE)
                        style.setRsid(rsid);
                    break;
                }
                case /*"pPr"*/6:
                    mParaPrReader.read(reader, style.getParaPr(), style.getRunPr());
                    break;
                case /*"rPr"*/5:
                    mRunPrReader.read(reader, style.getRunPr());
                    break;
                default:
                    // All other elements are related to table styles.
                    readTableStyleElement(reader, style);
                    break;
            }
        }

        // RESILIENCY WORDSNET-22563 The problem occurred because the table style does not have name.
        // We ignored such styles, however MS Word read them normally.
        // It seems styleId and StyleName are interchangeable. So if id is not specified we use name of the style instead id,
        // and if name is not specified we use id instead of name.
        if (!StringUtil.hasChars(name) && StringUtil.hasChars(styleId))
            name = styleId;

        // WORDSNET-25354 Do not read styles exceeding maximum allowed count.
        if (!reader.getDocument().getStyles().hasFreeIstd())
            return;

        if (StringUtil.hasChars(name))
        {
            NrxStyleUtil.setStyleNameAndIdentifiers(name, styles, isCustomStyle, !isCustomStyle, true, style);

            // WORDSNET-15791 There is no style id. MS Word reads it okay, so do we now.
            if (!StringUtil.hasChars(styleId))
                styleId = name;

            reader.addStyleIdToIstdMapping(styleId, style.getIstd());
        }

        String[] aliasesArray = null;
        if (aliases != null)
            aliasesArray = msString.split(aliases, ',');

        // WORDSNET-5506 RK If a style has no name, it will not be properly initialized by the above code,
        // it is better to ignore such style completely.
        if (StringUtil.hasChars(style.getName()))
        {
            styles.addForLoad(style, aliasesArray);

            msArrayList.add(unresolvedParts, unresolvedStylePart);
        }

        if (isDefault)
            reader.getDefaultStyles().set((int)style.getType(), style);
    }

    private void readTableStyleElement(DocxDocumentReaderBase reader, Style style) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // RK Safe cast and check for null just in case table style properties occur for a non table style.
        TableStyle tableStyle = ms.as(style, TableStyle.class);
        if (tableStyle == null)
        {
            xmlReader.ignoreElement();
            return;
        }

        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"tblPr"*/34:
                DocxTablePrReader.readStyleTblPr(reader, tableStyle.getTablePr());
                break;
            case /*"trPr"*/35:
                DocxRowPrReader.read(reader, tableStyle.getRowPr());
                break;
            case /*"tcPr"*/36:
            {
                DocxReaderFactory.getCellPrReader().read(reader, tableStyle.getCellPr());
                // WORDSNET-25389 Seems Word ignores TextOrientation property in table style.
                tableStyle.getCellPr().remove(CellAttr.ORIENTATION);
                break;
            }
            case /*"tblStylePr"*/37:
                tableStyle.addConditionalStyle(readConditionalStyle(reader));
                break;
            default:
                xmlReader.ignoreElement();
                break;
        }
    }

    /// <summary>
    /// Reads 2.7.5.6 tblStylePr (Style Conditional Table Formatting Properties).
    /// </summary>
    private ConditionalStyle readConditionalStyle(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        /*TableStyleOverrideType*/int type = TableStyleOverrideType.NONE;

        // Read attributes.
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"type"*/23:
                    type = StyleConvertUtil.xmlToTableStyleOverrideType(xmlReader.getValue());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        ConditionalStyle conditionalStyle = new ConditionalStyle(type);

        // Read elements.
        while (xmlReader.readChild("tblStylePr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"pPr"*/6:
                    conditionalStyle.setParaPr(new ParaPr());
                    mParaPrReader.read(reader, conditionalStyle.getParaPr(), conditionalStyle.getRunPr());
                    break;
                case /*"rPr"*/5:
                    conditionalStyle.setRunPr(new RunPr());
                    mRunPrReader.read(reader, conditionalStyle.getRunPr());
                    break;
                case /*"tblPr"*/34:
                    conditionalStyle.setTablePr(new TablePr());
                    DocxTablePrReader.readStyleTblPr(reader, conditionalStyle.getTablePr());
                    break;
                case /*"trPr"*/35:
                    conditionalStyle.setRowPr(new TablePr());
                    DocxRowPrReader.read(reader, conditionalStyle.getRowPr());
                    break;
                case /*"tcPr"*/36:
                    conditionalStyle.setCellPr(new CellPr());
                    DocxReaderFactory.getCellPrReader().read(reader, conditionalStyle.getCellPr());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }
        return conditionalStyle;
    }

    /// <summary>
    /// Inits document defaults depending on specified version.
    /// </summary>
    private static void initDefaultParaPr(DocxDocumentReaderBase reader)
    {
        // WORDSNET-19598 In case of missing the 'pPrDefault' element inside the 'docDefaults' (styles.xml)
        // Chinese preferred language doesn't need any 'spacing' attributes.
        //
        int defaultEditingLanguage = (int)reader.getLoadOptions().getLanguagePreferences().getDefaultEditingLanguage();
        if (LocaleClassifier.isChineseOrJapanese(defaultEditingLanguage))
            return;

        ParaPr paraPr = reader.getDocument().getStyles().getDefaultParaPr();

        switch (reader.getLoadOptions().getMswVersion())
        {
            case MsWordVersion.WORD_2007:
            case MsWordVersion.WORD_2010:
                set2007DefaultParaPr(paraPr);
                break;
            case MsWordVersion.WORD_2013:
            case MsWordVersion.WORD_2016:
            case MsWordVersion.WORD_2019:
            default:
                set2013DefaultParaPr(paraPr);
                break;
        }
    }

    /// <summary>
    /// Sets paraPr which were the DefaultParaPr in the MSW2007.
    /// </summary>
    private static void set2007DefaultParaPr(ParaPr paraPr)
    {
        paraPr.setSpaceAfter(200);
        paraPr.setLineSpacing(276);
        paraPr.setLineSpacingRule(LineSpacingRule.MULTIPLE);
    }

    /// <summary>
    /// Sets paraPr which were the DefaultParaPr in the MSW2013.
    /// </summary>
    private static void set2013DefaultParaPr(ParaPr paraPr)
    {
        paraPr.setSpaceAfter(160);
        paraPr.setLineSpacing(259);
        paraPr.setLineSpacingRule(LineSpacingRule.MULTIPLE);
    }

    /// <summary>
    /// Inits document defaults depending on specified version.
    /// </summary>
    private static void initDefaultRunPr(DocxDocumentReaderBase reader)
    {
        RunPr runPr = reader.getDocument().getStyles().getDefaultRunPr();

        switch (reader.getLoadOptions().getMswVersion())
        {
            case MsWordVersion.WORD_2007:
            case MsWordVersion.WORD_2010:
            case MsWordVersion.WORD_2013:
            case MsWordVersion.WORD_2016:
            case MsWordVersion.WORD_2019:
            default:
                set2007DefaultRunPr(runPr);
                break;
        }
    }

    /// <summary>
    /// Sets RunPr which were the DefaultRunPr in the MSW2007
    /// </summary>
    private static void set2007DefaultRunPr(RunPr runPr)
    {
        runPr.setComplexNameAscii(ComplexFontName.fromTheme(ThemeFontCore.MINOR_H_ANSI));
        runPr.setComplexNameOther(ComplexFontName.fromTheme(ThemeFontCore.MINOR_H_ANSI));
        runPr.setComplexNameFarEast(ComplexFontName.fromTheme(ThemeFontCore.MINOR_EAST_ASIA));
        runPr.setComplexNameBi(ComplexFontName.fromTheme(ThemeFontCore.MINOR_BIDI));

        runPr.setSize(22);
        runPr.setSizeBi(22);
    }


    private /*final*/ DocxParaPrReader mParaPrReader;
    private /*final*/ DocxRunPrReader mRunPrReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"docDefaults",
		"latentStyles",
		"style",
		"rPrDefault",
		"pPrDefault",
		"rPr",
		"pPr",
		"count",
		"defLockedState",
		"defQFormat",
		"defSemiHidden",
		"defUIPriority",
		"defUnhideWhenUsed",
		"lsdException",
		"locked",
		"name",
		"qFormat",
		"semiHidden",
		"uiPriority",
		"unhideWhenUsed",
		"customStyle",
		"default",
		"styleId",
		"type",
		"aliases",
		"basedOn",
		"next",
		"link",
		"autoRedefine",
		"hidden",
		"personal",
		"personalCompose",
		"personalReply",
		"rsid",
		"tblPr",
		"trPr",
		"tcPr",
		"tblStylePr"
	);

}

