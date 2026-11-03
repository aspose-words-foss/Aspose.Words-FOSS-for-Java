// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/12/2016 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackageBase;
import com.aspose.OpcPackagePart;
import com.aspose.OpcPackage;


/// <summary>
/// Helps to create DOCX reader classes.
/// </summary>
class DocxReaderFactory
{
    static DocxDocumentReader createDocumentReader(OpcPackageBase _package,
        OpcPackagePart documentPart,
        Document doc,
        LoadOptions loadOptions,
        OoxmlComplianceInfo complianceInfo) throws Exception
    {
        return new DocxDocumentReader(_package,
            documentPart,
            doc,
            loadOptions,
            complianceInfo,
            G_STYLES_READER,
            G_STORY_READER,
            G_NUMBERING_READER,
            G_SECT_PR_READER);
    }

    static DocxDocumentReader createDocumentReader(OpcPackagePart documentPart) throws Exception
    {
        return new DocxDocumentReader(new OpcPackage(),
            documentPart,
            new Document(DocumentCtorMode.BLANK_DOCUMENT_NODE),
            new LoadOptions(),
            new OoxmlComplianceInfo(),
            G_STYLES_READER,
            G_STORY_READER,
            G_NUMBERING_READER,
            G_SECT_PR_READER);
    }

    static DocxSdtReader createInlineLevelSdtReader()
    {
        return new DocxSdtReader(G_INLINE_READER, MarkupLevel.INLINE);
    }

    static DocxSdtReader createBlockLevelSdtReader()
    {
        return new DocxSdtReader(G_INLINE_READER, MarkupLevel.BLOCK);
    }

    static DocxSdtReader createRowLevelSdtReader(TablePr tablePr)
    {
        return new DocxSdtReader(G_INLINE_READER, MarkupLevel.ROW, tablePr);
    }

    static DocxSdtReader createCellLevelSdtReader(TablePr rowPr)
    {
        return new DocxSdtReader(G_INLINE_READER, MarkupLevel.CELL, rowPr);
    }

    static DocxCustomXmlMarkupReader createInlineLevelCustomXmlReader()
    {
        return new DocxCustomXmlMarkupReader(G_INLINE_READER, MarkupLevel.INLINE);
    }

    static DocxCustomXmlMarkupReader createBlockLevelCustomXmlReader()
    {
        return new DocxCustomXmlMarkupReader(G_INLINE_READER, MarkupLevel.BLOCK);
    }

    static DocxCustomXmlMarkupReader createRowLevelCustomXmlReader(TablePr tablePr)
    {
        DocxCustomXmlMarkupReader reader = new DocxCustomXmlMarkupReader(G_INLINE_READER, MarkupLevel.ROW, tablePr);
        return reader;
    }

    static DocxCustomXmlMarkupReader createCellLevelCustomXmlReader(TablePr rowPr)
    {
        DocxCustomXmlMarkupReader reader = new DocxCustomXmlMarkupReader(G_INLINE_READER, MarkupLevel.CELL, rowPr);
        return reader;
    }

    static DocxMathReader createMathReader(NrxDocumentReaderBase reader)
    {
        return new DocxMathReader(reader,
            G_RUN_PR_READER,
            G_INLINE_READER,
            G_ANNOTATION_READER);
    }

    static DocxGlossaryReader createGlossaryReader(OpcPackageBase _package,
        OpcPackagePart documentPart,
        GlossaryDocument doc,
        LoadOptions loadOptions,
        OoxmlComplianceInfo complianceInfo) throws Exception
    {
        return new DocxGlossaryReader(_package,
            documentPart,
            doc,
            loadOptions,
            complianceInfo,
            G_STYLES_READER,
            G_STORY_READER,
            G_NUMBERING_READER,
            G_SECT_PR_READER);
    }

    static DocxRunPrReader getRunPrReader() { return G_RUN_PR_READER; }

    static DocxAnnotationReader getAnnotationReader() { return G_ANNOTATION_READER; }

    static DocxParaPrReader getParaPrReader() { return G_PARA_PR_READER; }

    static NrxAnnotationReader getNrxAnnotationReader() { return G_NRX_ANNOTATION_READER; }

    static DocxStoryReader getStoryReader() { return G_STORY_READER; }

    static DocxRunReader getRunReader() { return G_RUN_READER; }

    static DocxStylesReader getStylesReader() { return G_STYLES_READER; }

    static DocxNumberingReader getNumberingReader() { return G_NUMBERING_READER; }

    static DocxCellReader getCellReader() { return G_CELL_READER; }

    static DocxCellPrReader getCellPrReader() { return G_CELL_PR_READER; }

    static DocxMailMergePrReader getMailMergePrReader() { return G_MAIL_MERGE_PR_READER; }

    static DocxSectPrReader getSectPrReader() { return G_SECT_PR_READER; }

    private static /*final*/ DocxRunPrReader G_RUN_PR_READER;
    private static /*final*/ DocxAnnotationReader G_ANNOTATION_READER;
    private static /*final*/ DocxParaPrReader G_PARA_PR_READER;
    private static /*final*/ NrxAnnotationReader G_NRX_ANNOTATION_READER;
    private static /*final*/ DocxStoryReader G_STORY_READER;
    private static /*final*/ DocxRunReader G_RUN_READER;
    private static /*final*/ DocxStylesReader G_STYLES_READER;
    private static /*final*/ DocxNumberingReader G_NUMBERING_READER;
    private static /*final*/ DocxCellReader G_CELL_READER;
    private static /*final*/ DocxCellPrReader G_CELL_PR_READER;
    private static /*final*/ DocxMailMergePrReader G_MAIL_MERGE_PR_READER;
    private static /*final*/ DocxInlineReader G_INLINE_READER;
    private static /*final*/ DocxSectPrReader G_SECT_PR_READER;

    static/* DocxReaderFactory()*/
    {
        // Note that any class created here as a static field should be immutable (state cannot be modified after it is created)!
        G_SECT_PR_READER = new DocxSectPrReader();

        G_MAIL_MERGE_PR_READER = new DocxMailMergePrReader();

        DocxFldCharReader fldCharReader = new DocxFldCharReader();

        DocxHyperlinkReader hyperlinkReader = new DocxHyperlinkReader();

        DocxSmartTagReader smartTagReader = new DocxSmartTagReader();

        G_RUN_PR_READER = new DocxRunPrReader(fldCharReader);

        G_INLINE_READER = new DocxInlineReader(hyperlinkReader, smartTagReader);
        hyperlinkReader.setInlineReader(G_INLINE_READER);
        smartTagReader.setInlineReader(G_INLINE_READER);

        NrxFldSimpleReader fldSimpleReader = new NrxFldSimpleReader(G_INLINE_READER);

        G_PARA_PR_READER = new DocxParaPrReader(G_RUN_PR_READER, G_SECT_PR_READER);

        G_NRX_ANNOTATION_READER = new NrxAnnotationReader(G_RUN_PR_READER, G_PARA_PR_READER);

        G_ANNOTATION_READER = new DocxAnnotationReader(G_NRX_ANNOTATION_READER, G_INLINE_READER, G_SECT_PR_READER);
        G_INLINE_READER.setAnnotationReader(G_ANNOTATION_READER);
        G_PARA_PR_READER.setAnnotationReader(G_ANNOTATION_READER);
        G_RUN_PR_READER.setAnnotationReader(G_ANNOTATION_READER);
        G_SECT_PR_READER.setAnnotationReader(G_ANNOTATION_READER);

        DocxParaReader paraReader = new DocxParaReader(G_PARA_PR_READER, G_INLINE_READER);

        G_CELL_PR_READER = new DocxCellPrReader(paraReader);

        G_STORY_READER = new DocxStoryReader(paraReader);

        VmlShapeReader vmlReader = new VmlShapeReader();

        G_RUN_READER = new DocxRunReader(G_STORY_READER, G_RUN_PR_READER, paraReader,
            fldCharReader, fldSimpleReader, hyperlinkReader, G_ANNOTATION_READER, vmlReader);

        G_STYLES_READER = new DocxStylesReader(G_PARA_PR_READER, G_RUN_PR_READER);

        G_NUMBERING_READER = new DocxNumberingReader(G_PARA_PR_READER, G_RUN_PR_READER, vmlReader);

        G_CELL_READER = new DocxCellReader(G_STORY_READER, G_PARA_PR_READER, G_CELL_PR_READER);
    }
}

