// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/02/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements writing common elements related to document setting for both DOCX and WordML formats.
/// </summary>
/*static*/ class NrxSettingsWriter
{
	/* Simulation of static class by using private constructor */
	private NrxSettingsWriter()
	{}

    static void writeRsids(DocPr docPr, NrxXmlBuilder builder, /*SaveFormat*/int sf)
    {
        Debug.assertTrue((sf == SaveFormat.DOCX) || (sf == SaveFormat.WORD_ML));

        String prefix = (sf == SaveFormat.DOCX)
            ? "w"
            : "wsp";    // WordML

        builder.startElement(msString.format("{0}:rsids", prefix));
        builder.writeVal(msString.format("{0}:rsidRoot", prefix), NrxXmlUtil.intToHex(docPr.RsidRoot));
        for (int i = 0; i < docPr.Rsids.getCount(); i++)
            builder.writeVal(msString.format("{0}:rsid", prefix), NrxXmlUtil.intToHex(docPr.Rsids.get(i)));
        builder.endElement();
    }

    static void writeDivs(Document doc, NrxXmlBuilder builder) throws Exception
    {
        if (doc.getHtmlBlockCollection().getCount() == 0)
            return;

        builder.startElement("w:divs");

        writeDivChild(builder, doc.getHtmlBlockCollection(), 0);

        builder.endElement("w:divs");
    }

    /// <summary>
    /// Writes child HtmlBlocks.
    /// </summary>
    /// <remarks>
    /// AM. This is rare feature and HtmlBlock table is not very large in common. 
    /// So I get child nodes using straight approach.
    /// </remarks>
    private static void writeDivChild(NrxXmlBuilder builder, HtmlBlockCollection htmlBlockCollection, int parentId) throws Exception
    {
        for (int i = 0; i < htmlBlockCollection.getCount(); i++)
        {
            HtmlBlock htmlBlock = htmlBlockCollection.getHtmlBlockByIndex(i);

            if (htmlBlock.getParentId() == parentId)
                writeDiv(builder, htmlBlockCollection, htmlBlock);
        }
    }

    private static void writeDiv(NrxXmlBuilder builder, HtmlBlockCollection htmlBlockCollection, HtmlBlock htmlBlock) throws Exception
    {
        builder.startElement("w:div");
        builder.writeAttribute("w:id", htmlBlock.getId());

        if (htmlBlock.getHtmlBlockType() != HtmlBlockType.DIV)
            builder.writeBoolValExplicit((htmlBlock.getHtmlBlockType() == HtmlBlockType.BLOCK_QUOTE) ? "w:blockQuote" : "w:bodyDiv", true);

        ParaPr paraPr = htmlBlock.getParaPr();
        builder.writeVal("w:marLeft", paraPr.fetchAttr(ParaAttr.HTML_MARGIN_LEFT));
        builder.writeVal("w:marRight", paraPr.fetchAttr(ParaAttr.HTML_MARGIN_RIGHT));
        builder.writeVal("w:marTop", paraPr.fetchAttr(ParaAttr.HTML_MARGIN_TOP));
        builder.writeVal("w:marBottom", paraPr.fetchAttr(ParaAttr.HTML_MARGIN_BOTTOM));

        builder.startElement("w:divBdr");

        builder.writeBorder("w:top", paraPr.getBorderTop());
        builder.writeBorder("w:left", paraPr.getBorderLeft());
        builder.writeBorder("w:bottom", paraPr.getBorderBottom());
        builder.writeBorder("w:right", paraPr.getBorderRight());

        builder.endElement();

        if (htmlBlock.hasChild())
        {
            builder.startElement("w:divsChild");

            writeDivChild(builder, htmlBlockCollection, htmlBlock.getId());

            builder.endElement("w:divsChild");
        }

        builder.endElement();
    }
}

