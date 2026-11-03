// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringUtil;


/// <summary>
/// Provides static method for building "Numbering" package part
/// </summary>
/*static*/ class DocxNumberingWriter
{
	/* Simulation of static class by using private constructor */
	private DocxNumberingWriter()
	{}

    /// <summary>
    /// Writes "Numbering" document part for the specified document.
    /// </summary>
    static void write(DocxDocumentWriterBase writer) throws Exception
    {
        DocumentBase doc = writer.getDocument();

        if ((doc.getLists().getListDefCount() == 0) && (doc.getLists().getCount() == 0))
            return;

        DocxBuilder builder = writer.createChildPartAndBuilder("numbering.xml", DocxContentType.NUMBERING, writer.getRelTypes().getNumbering());
        writer.pushBuilder(builder);

        builder.startDocumentWithStandardNamespaces("w:numbering");

        writePictureBullets(doc, writer);
        writeListDefs(doc, writer);
        writeListInstances(doc, writer);
        writeNumIdMacAtCleanup(doc, writer);

        builder.endDocument(); //w:numbering
        writer.popBuilder();
    }

    private static void writeNumIdMacAtCleanup(DocumentBase doc, DocxDocumentWriterBase writer)
    {
        Object numIdMac = doc.getLists().getNumIdMacAtCleanup();
        if (numIdMac != null)
            writer.getCurrentBuilder().writeVal("w:numIdMacAtCleanup", (/*int*/Integer)numIdMac);
    }

    private static void writePictureBullets(DocumentBase doc, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (int i = 0; i < doc.getLists().getPictureBulletCount(); i++)
        {
            Shape shape = doc.getLists().getPictureBullet(i);
            builder.startElement("w:numPicBullet");

            builder.writeAttribute("w:numPicBulletId", i);

            if (shape.getFallbackShape() != null)
                writer.startAlternateContent(shape);

            if (shape.getMarkupLanguage() == ShapeMarkupLanguage.VML)
            {
                builder.startElement("w:pict");
                VmlShapeWriter.write(shape, builder, writer);
                builder.endElement(); // RK 2 VA Very ugly! Writing end tag should be automatic.
                builder.endElement(); // pict
            }
            else
            {
                DmlDrawingWriter.writeStart(shape, writer);
                DmlDrawingWriter.writeEnd(shape, writer);
            }

            if (shape.getFallbackShape() != null)
                writer.endAlternateContent(shape);

            builder.endElement();  // numPicBullet
        }
    }

    private static void writeListDefs(DocumentBase doc, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        Document document = ms.as(doc, Document.class);
        boolean isWord2013Up =
            (document != null) &&
            (document.getComplianceInfo() != null) &&
            (document.getComplianceInfo().getMsWordExtensionsVersion() >= MsWordVersionCore.WORD_2013);

        for (int i = 0; i < doc.getLists().getListDefCount(); i++)
        {
            ListDef listDef = doc.getLists().getListDefByIndex(i);

            builder.startElement("w:abstractNum");

            builder.writeAttribute("w:abstractNumId", i);
            
            if (isWord2013Up)
                builder.writeAttribute("w15:restartNumberingAfterBreak", listDef.isRestartAtEachSection());

            builder.writeVal("w:nsid", NrxXmlUtil.intToHex(listDef.getListDefId()));
            builder.writeVal("w:multiLevelType", DocxNumberingEnum.listTypeToDocx(listDef.getListType()));
            builder.writeVal("w:tmpl", NrxXmlUtil.intToHex(listDef.getTemplateCode()));
            builder.writeVal("w:name", listDef.getName());

            if (listDef.isListStyleDefinition())
                builder.writeVal("w:styleLink", writer.getStyleId(listDef.getListStyleIstd()));

            if (listDef.isListStyleReference())
            {
                builder.writeVal("w:numStyleLink", writer.getStyleId(listDef.getListStyleIstd()));
                // Do not write list levels for style reference.
            }
            else
            {
                for (int listLeveNumber = 0; listLeveNumber < listDef.getLevels().getCount(); listLeveNumber++)
                    writeListLevel(listDef.getLevels().get(listLeveNumber), true, writer);
            }

            builder.endElement(); //w:abstractNum
        }
    }

    private static void writeListInstances(DocumentBase doc, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (List list : doc.getLists())
        {
            int abstractNumId = doc.getLists().getIndexOfListDefByListDefId(list.getListDefId());
            // RK Let's not write a list when we cannot find its list definition.
            // What else can we do? We cannot create and write a dummy list definition
            // because we have already written list definitions.
            if (abstractNumId >= 0)
            {
                builder.startElement("w:num");
                builder.writeAttribute("w:numId", list.getListId());

                if ((list.getDurableId() != 0) && (writer.getMsWordExtensionsVersion() >= MsWordVersionCore.WORD_2016))
                    builder.writeAttribute("w16cid:durableId", list.getDurableId());

                builder.writeVal("w:abstractNumId", abstractNumId);

                for (ListLevelOverride levelOverride : list.getOverrides())
                {
                    builder.startElement("w:lvlOverride");
                    builder.writeAttribute("w:ilvl", levelOverride.getListLevel().getLevelNumber());

                    if (levelOverride.IsStartAt)
                        builder.writeValIfNotDefault("w:startOverride", levelOverride.StartAtRaw, 0);

                    if (levelOverride.IsFormatting)
                        writeListLevel(levelOverride.getListLevel(), levelOverride.WriteStartAt, writer);

                    builder.endElement();    //w:lvlOverride
                }

                builder.endElement();    //w:num
            }
        }
    }

    private static void writeListLevel(ListLevel level, boolean writeStartAt, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("w:lvl");
        builder.writeAttribute("w:ilvl", level.getLevelNumber());

        // TODO 3 w:tplc attribute - not found in DOC

        builder.writeAttribute("w:tentative", level.IsTentative ? "1" : "");

        if (writeStartAt)
            builder.writeVal("w:start", level.getStartAt());

        writeNumberStyle(level, writer);

        // Add one since it is 1-based in WordML.
        if (level.isRestartAfterLevelCustom())
            builder.writeVal("w:lvlRestart", level.getRestartAfterLevel() + 1);

        // RK Here was a compare for zero, but I think the compare should be for StyleIndex.Nul
        // because zero istd is the Normal style.
        if (level.getParaStyleIstd() != StyleIndex.NIL)
            builder.writeVal("w:pStyle", writer.getStyleId(level.getParaStyleIstd()));

        builder.writeValIfTrue("w:isLgl", level.isLegal());

        if (level.getTrailingCharacter() != ListTrailingCharacter.TAB)
            builder.writeVal("w:suff", DocxNumberingEnum.listTrailingCharacterToDocx(level.getTrailingCharacter()));

        if (StringUtil.hasChars(level.getNumberFormat()))
        {
            builder.startElement("w:lvlText");
            builder.writeAttributeString("w:val", NrxXmlUtil.numberFormatToXml(level.getNumberFormat()));
            // TODO 3 'w:null' not seen in the current tests. Skip writing it, check later.
            builder.endElement(); //w:lvlText
        }

        if (level.hasPictureBullet())
            builder.writeVal("w:lvlPicBulletId", level.getPictureBulletId());

        if (level.getLegacy())
        {
            builder.writeElementWithAttributes(
                "w:legacy",
                "w:legacy", level.getLegacy(),
                "w:legacySpace", level.getLegacySpace(),
                "w:legacyIndent", level.getLegacyIndent());
        }

        builder.writeVal("w:lvlJc", DocxNumberingEnum.listLevelAlignmentToDocx(level.getAlignment(), 
            writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT));

        NrxParaPrWriter.write(level.getParaPr(), writer);

        DocxRunPrWriter.writeForStyles(level.getRunPr(), level.getRunPr(), writer);

        builder.endElement(); //w:lvl
    }

    /// <summary>
    /// Writes the 17.9.17 numFmt (Numbering Format) element.
    /// </summary>
    private static void writeNumberStyle(ListLevel level, DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        if (level.getNumberStyle() == NumberStyle.CUSTOM)
        {
            builder.startElement("mc:AlternateContent");
            builder.startElement("mc:Choice");
            builder.writeAttribute("Requires", "w14");

            builder.writeElementWithAttributes("w:numFmt", 
                "w:val", DocxEnum.numberStyleToDocx(level.getNumberStyle()), 
                "w:format", StringUtil.hasChars(level.getCustomNumberStyle()) ? level.getCustomNumberStyle() : null);

            builder.endElement("mc:Choice");
            builder.startElement("mc:Fallback");

            builder.writeVal("w:numFmt", DocxEnum.numberStyleToDocx(NumberStyle.ARABIC));

            builder.endElement("mc:Fallback");
            builder.endElement("mc:AlternateContent");
        }
        else
        {
            builder.writeVal("w:numFmt", DocxEnum.numberStyleToDocx(level.getNumberStyle()));
        }
    }
}

