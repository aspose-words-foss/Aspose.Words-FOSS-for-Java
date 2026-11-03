// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********




/// <summary>
/// Provides static method for building "Web Settings" package part
/// </summary>
/*static*/ class DocxWebSettingsWriter
{
	/* Simulation of static class by using private constructor */
	private DocxWebSettingsWriter()
	{}

    /// <summary>
    /// Writes "Web Settings" document part for the specified document.
    /// </summary>
    static void write(DocxDocumentWriterBase writer, boolean hasHtmlBlockReferences) throws Exception
    {
        DocumentBase doc = writer.getDocument();
        DocPr docPr = doc.getDocPr();

        DocxBuilder builder = writer.createChildPartAndBuilder("webSettings.xml", DocxContentType.WEB_SETTINGS, writer.getRelTypes().getWebSettings());
        builder.startWebSettingsDocumentPart();

        // Write PgpInfo for main document only.
        if ((doc.getNodeType() == NodeType.DOCUMENT) && hasHtmlBlockReferences)
            NrxSettingsWriter.writeDivs((Document)doc, builder);

        builder.writeVal("w:encoding", docPr.WebPageEncoding);

        // Write document frames only for the main document.
        if (doc.getNodeType() == NodeType.DOCUMENT)
            NrxFramesetWriter.write((Document)doc, builder, true, writer.getRelTypes().getFrameset());

        if (docPr.OptimizeForBrowser)
        {
            builder.startElement("w:optimizeForBrowser");
            if ((writer.getCompliance() != OoxmlComplianceCore.ECMA_376) && // if iso29500 or later,
                (docPr._WebTarget != WebTarget.NONE))
                builder.writeAttribute("target", DocxDopEnum.webTargetToDocx(docPr._WebTarget));
            builder.endElement();
        }
        builder.writeValIfTrue("w:relyOnVML", docPr.RelyOnVml);
        builder.writeValIfTrue("w:allowPNG", docPr.AllowPng);
        builder.writeValIfTrue("w:doNotRelyOnCSS", docPr.DoNotRelyOnCss);
        builder.writeValIfTrue("w:doNotSaveAsSingleFile", docPr.DoNotSaveWebPagesAsSingleFile);
        builder.writeValIfTrue("w:doNotOrganizeInFolder", docPr.DoNotOrganizeInFolder);
        builder.writeValIfTrue("w:doNotUseLongFileNames", docPr.DoNotUseLongFileNames);
        if (docPr.PixelsPerInch != DocPr.PIXELS_PER_INCH_DEFAULT)
            builder.writeVal("w:pixelsPerInch", docPr.PixelsPerInch);
        if (docPr.TargetScreenSize != DocPr.TARGET_SCREEN_SIZE_DEFAULT)
            builder.writeVal("w:targetScreenSz", StyleConvertUtil.screenSizeToXml(docPr.TargetScreenSize));

        builder.endDocument(); //w:webSettings
    }
}

