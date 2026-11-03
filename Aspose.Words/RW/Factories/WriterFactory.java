// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/*static*/ class WriterFactory
{
	/* Simulation of static class by using private constructor */
	private WriterFactory()
	{}

    /// <summary>
    /// Creates and returns a document writer object for the specified file format.
    /// </summary>
    static IDocumentWriter createWriter(/*SaveFormat*/int fileFormat)
    {
        switch (fileFormat)
        {
            case SaveFormat.DOC:
            case SaveFormat.DOT:
                throw new UnsupportedOperationException("FOSS");

            case SaveFormat.DOCX:
            case SaveFormat.DOCM:
            case SaveFormat.DOTX:
            case SaveFormat.DOTM:
            case SaveFormat.FLAT_OPC:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
            case SaveFormat.FLAT_OPC_TEMPLATE:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                return new DocxWriter();
            case SaveFormat.RTF:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.WORD_ML:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.PDF:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.PS:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.PCL:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.XPS:
            case SaveFormat.OPEN_XPS:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.XAML_FIXED:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.SVG:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.HTML_FIXED:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.HTML:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.MHTML:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.ODT:
            case SaveFormat.OTT:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.TEXT:
                return new TxtWriter();
            case SaveFormat.MARKDOWN:
                return new MarkdownWriter();
            case SaveFormat.EPUB:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.AZW_3:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.MOBI:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.XAML_FLOW:
            case SaveFormat.XAML_FLOW_PACK:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.EMF:
            case SaveFormat.TIFF:
            case SaveFormat.PNG:
            case SaveFormat.BMP:
            case SaveFormat.JPEG:
            case SaveFormat.GIF:
            case SaveFormat.EPS:
            case SaveFormat.WEB_P:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.XLSX:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.DOCLING:
                throw new UnsupportedOperationException("FOSS");
            default:
                throw new IllegalArgumentException("Invalid file format requested.");
        }
    }

    /// <summary>
    /// Creates concrete instance of <see cref="OfficeMathToShapeConverter"/> class.
    /// </summary>
    static OfficeMathToShapeConverter createOfficeMathToShapeConverter(Document doc, SaveOptions saveOptions)
    {
        switch (saveOptions.getSaveFormat())
        {
            case SaveFormat.HTML:
            case SaveFormat.MHTML:
            case SaveFormat.EPUB:
            case SaveFormat.AZW_3:
            case SaveFormat.MOBI:
            {
                HtmlSaveOptions htmlSaveOptions = (HtmlSaveOptions)saveOptions;
                return htmlSaveOptions.isConvertOfficeMathToShape()
                    ? new OfficeMathToShapeConverter(doc, saveOptions.getSaveFormat())
                    : new NullOfficeMathToShapeConverter(doc);
            }
            case SaveFormat.DOC:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.WORD_ML:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.RTF:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.BMP:
            case SaveFormat.DOCM:
            case SaveFormat.DOCX:
            case SaveFormat.DOT:
            case SaveFormat.DOTM:
            case SaveFormat.DOTX:
            case SaveFormat.EMF:
            case SaveFormat.EPS:
            case SaveFormat.FLAT_OPC:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
            case SaveFormat.FLAT_OPC_TEMPLATE:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
            case SaveFormat.GIF:
            case SaveFormat.HTML_FIXED:
            case SaveFormat.JPEG:
            case SaveFormat.ODT:
            case SaveFormat.OPEN_XPS:
            case SaveFormat.OTT:
            case SaveFormat.PDF:
            case SaveFormat.PNG:
            case SaveFormat.PS:
            case SaveFormat.PCL:
            case SaveFormat.SVG:
            case SaveFormat.TEXT:
            case SaveFormat.TIFF:
            case SaveFormat.XAML_FIXED:
            case SaveFormat.XAML_FLOW:
            case SaveFormat.XAML_FLOW_PACK:
            case SaveFormat.XPS:
            case SaveFormat.XLSX:
            case SaveFormat.DOCLING:
            case SaveFormat.WEB_P:
                return new NullOfficeMathToShapeConverter(doc);

            // WORDSNET-27646 Implemented export option for OfficeMath in Markdown.
            case SaveFormat.MARKDOWN:
            {
                MarkdownSaveOptions markdownSaveOptions = (MarkdownSaveOptions)saveOptions;
                return (markdownSaveOptions.getOfficeMathExportMode() == MarkdownOfficeMathExportMode.IMAGE)
                    ? new OfficeMathToShapeConverter(doc, saveOptions.getSaveFormat())
                    : new NullOfficeMathToShapeConverter(doc);
            }

            default:
                Debug.assertTrue(false, "Unexpected file format.");
                return null;
        }
    }

    static IVmlShapeTypeReader createVmlShapeTypeReader()
    {
        return new VmlShapeTypeReader();
    }
}

