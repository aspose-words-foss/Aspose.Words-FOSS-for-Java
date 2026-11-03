// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.HashSetGeneric;


/*static*/ class ReaderFactory
{
	/* Simulation of static class by using private constructor */
	private ReaderFactory()
	{}

    /// <summary>
    /// Creates and returns a document reader object for the specified file format.
    /// </summary>
    static IDocumentReader createReader(
        Stream stream,
        LoadOptions loadOptions,
        /*LoadFormat*/int loadFormat,
        FileFormatInfo fileFormatInfo,
        Document document) throws Exception
    {
        switch (loadFormat)
        {
            case LoadFormat.DOC:
            case LoadFormat.DOT:
            case LoadFormat.DOC_PRE_WORD_60:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.HTML:
                throw new UnsupportedOperationException("FOSS: HTML file format is not supported in the Aspose.Words FOSS version.");
            case LoadFormat.MHTML:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.MOBI:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.AZW_3:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.CHM:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.EPUB:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.RTF:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.WORD_ML:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.DOCX:
            case LoadFormat.DOCM:
            case LoadFormat.DOTX:
            case LoadFormat.DOTM:
            case LoadFormat.FLAT_OPC:
            case LoadFormat.FLAT_OPC_MACRO_ENABLED:
            case LoadFormat.FLAT_OPC_TEMPLATE:
            case LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                return new OpenXmlDocumentReader(stream, loadOptions, fileFormatInfo, document);
            case LoadFormat.ODT:
            case LoadFormat.OTT:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.TEXT:
                return new TxtReader(stream, loadOptions, fileFormatInfo, document);
            case LoadFormat.MARKDOWN:
                return new MarkdownReader(stream, loadOptions, fileFormatInfo, document);
            case LoadFormat.PDF:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.XML:
                throw new UnsupportedOperationException("FOSS");
            case LoadFormat.MS_WORKS:
                throw new UnsupportedOperationException("FOSS");
            default:
                throw new UnsupportedFileFormatException("Unsupported file format: " + LoadFormat.toString(loadFormat));
        }
    }

    static IDocumentReader createHtmlDocumentInserter(
        String html,
        /*HtmlInsertOptions*/int insertionOptions,
        DocumentBuilder documentBuilder,
        HashSetGeneric<String> bookmarkNames)
    {
        return new HtmlDocumentInserter(html, insertionOptions, documentBuilder, bookmarkNames);
    }

    static IChartInserter createChartInserter()
    {
        return new ChartInserter();
    }

    static IDefaultThemeProvider createDefaultThemeProvider()
    {
        return new DocxDefaultThemeReader();
    }
}

