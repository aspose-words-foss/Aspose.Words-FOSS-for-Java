// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Language;
import com.aspose.StringUtil;


/// <summary>
/// Provides methods for building the "Styles" package part.
/// </summary>
/*static*/ class DocxStylesWriter
{
	/* Simulation of static class by using private constructor */
	private DocxStylesWriter()
	{}

    /// <summary>
    /// Writes the "Styles" document part for the specified document.
    /// </summary>
    static void write(DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.createChildPartAndBuilder("styles.xml", DocxContentType.STYLES, writer.getRelTypes().getStyles());
        writer.pushBuilder(builder);

        builder.startStylesDocumentPart();

        writeDocDefaults(writer);

        if(writer.getSaveOptions().getWriteLatentStyles())
            writeLatentStyles(writer);

        writeStyles(writer);

        builder.endDocument(); //w:styles
        writer.popBuilder();
    }

    private static void writeDocDefaults(DocxDocumentWriterBase writer) throws Exception
    {
        StyleCollection styles = writer.getDocument().getStyles();
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("w:docDefaults");

        builder.startElement("w:rPrDefault");

        RunPr defaultRunPr = styles.getDefaultRunPr();

        removeInvariantLocale(defaultRunPr, FontAttr.LOCALE_ID_FAR_EAST);
        removeInvariantLocale(defaultRunPr, FontAttr.LOCALE_ID_BI);

        DocxRunPrWriter.writeForStyles(defaultRunPr, defaultRunPr, writer);
        builder.endElement(); // w:rPrDefault

        builder.startElement("w:pPrDefault");
        NrxParaPrWriter.write(styles.getDefaultParaPr(), writer);
        builder.endElement(); // w:pPrDefault

        builder.endElement(); // w:docDefaults
    }

    /// <summary>
    /// Removes InvariantCulture locale.
    /// </summary>
    /// <remarks>
    /// AM. This is workaround for WORDSNET-23785 to reduce amount of affected golds.
    /// </remarks>
    private static void removeInvariantLocale(RunPr runPr, int key)
    {
        if(runPr.containsKey(key) &&
           ((/*int*/Integer)runPr.get(key) == (int)Language.INVARIANT_CULTURE))
            runPr.remove(key);
    }

    private static void writeLatentStyles(DocxDocumentWriterBase writer)
    {
        DocumentBase doc = writer.getDocument();
        LatentStyles latentStyles = doc.getStyles().getLatentStyles();
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("w:latentStyles");

        builder.writeAttribute("w:defLockedState", latentStyles.getDefaultLockedState());
        builder.writeAttribute("w:defUIPriority", latentStyles.getDefaultUIPriority());
        builder.writeAttribute("w:defSemiHidden", latentStyles.getDefaultSemiHidden());
        builder.writeAttribute("w:defUnhideWhenUsed", latentStyles.getDefaultUnhideWhenUsed());
        builder.writeAttribute("w:defQFormat", latentStyles.getDefaultQuickFormat());
        builder.writeAttribute("w:count", latentStyles.getKnownStylesCount());

        // andrnosk: WORDSNET-5562 If latent styles latentStyle are missing, let's build defaults.
        if (latentStyles.getCount() <=0)
            latentStyles.initToWord2007Default();

        for (int i = 0; i < latentStyles.getCount(); i++)
        {
            LatentStyle latentStyle = latentStyles.get(i);

            String name = StyleConvertUtil.styleIdentifierToXml(latentStyle.getStyleIdentifier(), "");
            if (!StringUtil.hasChars(name))
                continue;

            // WORDSNET-19842 Used to avoid accepting lot of golds.
            if (!writer.getSaveOptions().getWrite2019LatentStyle() &&
                ((latentStyle.getStyleIdentifier() == StyleIdentifier.MENTION) ||
                 (latentStyle.getStyleIdentifier() == StyleIdentifier.SMART_HYPERLINK) ||
                 (latentStyle.getStyleIdentifier() == StyleIdentifier.HASHTAG) ||
                 (latentStyle.getStyleIdentifier() == StyleIdentifier.UNRESOLVED_MENTION)))
                continue;

            builder.startElement("w:lsdException");

            builder.writeAttributeString("w:name", name);

            if (latentStyle.getLocked() != latentStyles.getDefaultLockedState())
                builder.writeAttribute("w:locked", latentStyle.getLocked());

            if (latentStyle.getSemiHidden() != latentStyles.getDefaultSemiHidden())
                builder.writeAttribute("w:semiHidden", latentStyle.getSemiHidden());

            if (latentStyle.getUIPriority() != latentStyles.getDefaultUIPriority())
                builder.writeAttribute("w:uiPriority", latentStyle.getUIPriority());

            if (latentStyle.getUnhideWhenUsed() != latentStyles.getDefaultUnhideWhenUsed())
                builder.writeAttribute("w:unhideWhenUsed", latentStyle.getUnhideWhenUsed());

            if (latentStyle.getQuickStyle() != latentStyles.getDefaultQuickFormat())
                builder.writeAttribute("w:qFormat", latentStyle.getQuickStyle());

            builder.endElement(); // w:lsdException
        }

        builder.endElement(); // w:latentStyles
    }

    private static void writeStyles(DocxDocumentWriterBase writer) throws Exception
    {
        StyleCollection styles = writer.getDocument().getStyles();
        for (int i = 0; i < styles.getCount(); i++)
            writeStyle(writer, styles.get(i));
    }

    private static void writeStyle(DocxDocumentWriterBase writer, Style style) throws Exception
    {
        if(!WordUtil.checkValidAndWarn(style, writer.getDocument().getWarningCallback(), WarningSource.DOCX))
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("w:style");

        builder.writeAttributeString("w:type", DocxEnum.styleTypeToDocx(style.getType()));

        switch (style.getStyleIdentifier())
        {
            case StyleIdentifier.NORMAL:
            case StyleIdentifier.DEFAULT_PARAGRAPH_FONT:
            case StyleIdentifier.NO_LIST:
            case StyleIdentifier.TABLE_NORMAL:
                // Don't know if the default styles could be redefined and where to get info for it.
                // I will always write these styles as default for the time being.
                builder.writeAttributeString("w:default", "1");
                break;
            case StyleIdentifier.USER:
                // These are all custom styles.
                builder.writeAttributeString("w:customStyle", "1");
                break;
            default:
                // All others are built-in styles. Do nothing.
                break;
        }

        builder.writeAttributeString("w:styleId", writer.getStyleId(style.getIstd()));

        builder.writeVal("w:name", StyleConvertUtil.styleIdentifierToXml(style.getStyleIdentifier(), style.getName()));
        builder.writeVal("w:aliases", style.getStyles().getAliases(style, false));

        if(WordUtil.isValidIstd(style.getBasedOnIstd()))
            builder.writeVal("w:basedOn", writer.getStyleId(style.getBasedOnIstd()));

        if ((style.getNextIstd() != style.getIstd()) && (WordUtil.isValidIstd(style.getNextIstd())))
            builder.writeVal("w:next", writer.getStyleId(style.getNextIstd()));

        if(WordUtil.isValidIstd(style.getLinkedIstd()))
            builder.writeVal("w:link", writer.getStyleId(style.getLinkedIstd()));

        builder.writeValIfTrue("w:autoRedefine", style.getAutomaticallyUpdate());
        builder.writeValIfTrue("w:hidden", style.getHidden());

        // DOCX only attribute.
        builder.writeValIfPositive("w:uiPriority", style.getPriority());

        builder.writeValIfTrue("w:semiHidden", style.getSemiHidden());

        // DOCX only attributes.
        builder.writeValIfTrue("w:unhideWhenUsed", style.getUnhideWhenUsed());
        builder.writeValIfTrue("w:qFormat", style.isQuickStyle());

        builder.writeValIfTrue("w:locked", style.getLocked());

        builder.writeValIfTrue("w:personal", style.getPersonal());
        builder.writeValIfTrue("w:personalCompose", style.getPersonalCompose());
        builder.writeValIfTrue("w:personalReply", style.getPersonalReply());

        if (style.getRsid() != 0)
            builder.writeVal("w:rsid", NrxXmlUtil.intToHex(style.getRsid()));

        if ((style.getParaPr().getTabStops() != null) && (style.getParaPr().getTabStops().getCount() <= 0))
            style.getParaPr().remove(ParaAttr.TAB_STOPS);

        if (style.getType() != StyleType.CHARACTER)
            NrxParaPrWriter.write(style.getParaPr(), writer);

        DocxRunPrWriter.writeForStyles(style.getRunPr(), style, writer);

        if (style.getType() == StyleType.TABLE)
        {
            TableStyle tableStyle = (TableStyle)style;
            NrxRowPrWriter.write(tableStyle.getTablePr(), true, true, writer);
            NrxRowPrWriter.write(tableStyle.getRowPr(), false, true, writer);
            NrxCellPrWriter.write(tableStyle.getCellPr(), writer);

            for (ConditionalStyle conditionalStyle : tableStyle.getConditionalStyles().getDefinedStyles())
                writeConditionalStyle(conditionalStyle, writer);
        }

        builder.endElement(); //w:style
    }

    private static void writeConditionalStyle(ConditionalStyle conditionalStyle, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        builder.startElement("w:tblStylePr");
        builder.writeAttribute("w:type", StyleConvertUtil.tableStyleOverrideTypeToXml(conditionalStyle.getOverrideType()));

        if (conditionalStyle.hasParagraphFormatting())
            NrxParaPrWriter.write(conditionalStyle.getParaPr(), writer);
        // RK Passing conditionalStyle.RunPr as an attr source for resolving BoolEx values is probably
        // not correct here, but I do not have proper support for resolving values for table styles yet.
        if (conditionalStyle.hasRunFormatting())
            DocxRunPrWriter.writeForStyles(conditionalStyle.getRunPr(), conditionalStyle.getRunPr(), writer);
        if (conditionalStyle.hasTableFormatting() || conditionalStyle.hasCellFormatting()) // mimic MS Word
            NrxRowPrWriter.write(conditionalStyle.getTablePr(), true, true, writer);
        if (conditionalStyle.hasRowFormatting())
            NrxRowPrWriter.write(conditionalStyle.getRowPr(), false, true, writer);
        if (conditionalStyle.hasCellFormatting())
            NrxCellPrWriter.write(conditionalStyle.getCellPr(), writer);

        builder.endElement(); //w:tblStylePr
    }
}

