// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/05/2012 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.System.msString;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Basic AltChunk functionality tests.
/// </summary>
/// <remarks>
/// FOSS: tests exercising HTML/RTF-content altChunks were removed - their altChunk parts are .htm/.html/
/// .xhtml/.rtf and require the HTML/RTF readers, which are removed in FOSS (load throws
/// NotSupportedException "FOSS: HTML file format is not supported" / "FOSS"). altChunks whose content is
/// OOXML (.docx) still load, so those tests remain.
/// </remarks>
@Test
public class TestAltChunk
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }

    /// <summary>
    /// WORDSNET-1937 Support AltChunk tags.
    /// andrnosk: Fixed by adding mechanism to read AltChunk to the model.
    /// AltChunk is inserted after destination body.
    /// </summary>
    @Test
    public void altChunkAfterBody() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\AltChunk\\AltChunkAfterBody", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual("This is first AltChunk paragraph", msString.trim(doc.getFirstSection().getBody().getParagraphs().get(1).getText()));
        msAssert.areEqual("This is second AltChunk paragraph", msString.trim(doc.getFirstSection().getBody().getParagraphs().get(2).getText()));
        msAssert.areEqual(5, doc.getFirstSection().getBody().getParagraphs().getCount());
    }



    /// <summary>
    /// Test case when Docx is inserted to the document as AltChunk.
    /// </summary>
    @Test
    public void altChunkContentDocx() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\AltChunk\\AltChunkContentDocx", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        msAssert.areEqual("AltChunk Сontent", msString.trim(doc.getFirstSection().getBody().getParagraphs().get(0).getText()));
        msAssert.areEqual("Test test", msString.trim(doc.getFirstSection().getBody().getParagraphs().get(6).getText()));

    }

    /// <summary>
    /// Test case when AltChunk document contains styles.
    /// </summary>
    @Test
    public void testAltChunkStyles() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\AltChunk\\AltChunkStyles", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(StyleIdentifier.HEADING_1, paragraphs.get(0).getParagraphStyle().getStyleIdentifier());
        msAssert.areEqual(StyleIdentifier.HEADING_2, paragraphs.get(1).getParagraphStyle().getStyleIdentifier());
        msAssert.areEqual(StyleIdentifier.HEADING_3, paragraphs.get(2).getParagraphStyle().getStyleIdentifier());
        msAssert.areEqual(StyleIdentifier.SUBTITLE, paragraphs.get(3).getParagraphStyle().getStyleIdentifier());
        msAssert.areEqual(StyleIdentifier.USER, paragraphs.get(4).getParagraphStyle().getStyleIdentifier());
        msAssert.areEqual("CustomStyle", paragraphs.get(4).getParagraphStyle().getName());
    }


    /// <summary>
    /// Test AltChunk document with content controls.
    /// </summary>
    @Test
    public void testAltChunkContentControls() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\AltChunk\\AltChunkContentControls", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        // Check content controls count.
        msAssert.areEqual(4, doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());
    }

    /// <summary>
    /// Test AltChunk document with Headers/Footers.
    /// </summary>
    @Test
    public void testAltChunkHeaderFooter() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\AltChunk\\AltChunkHeaderFooter.docx");

        msAssert.areEqual(3, doc.getSections().getCount());

        HeaderFooterCollection headerFooters0 = doc.getSections().get(0).getHeadersFooters();
        HeaderFooterCollection headerFooters1 = doc.getSections().get(1).getHeadersFooters();

        msAssert.areEqual(3, headerFooters0.getCount());
        msAssert.areEqual("DestinationHeader0\r\n", headerFooters0.getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getParagraphs().get(0).toString(SaveFormat.TEXT));
        msAssert.areEqual("Section0_Header\r\n", headerFooters0.getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getParagraphs().get(2).toString(SaveFormat.TEXT));

        msAssert.areEqual("DestinationFooter0\r\n", headerFooters0.getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY).getParagraphs().get(0).toString(SaveFormat.TEXT));
        msAssert.areEqual("Section0_Footer\r\n", headerFooters0.getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY).getParagraphs().get(2).toString(SaveFormat.TEXT));

        msAssert.areEqual(2, headerFooters1.getCount());
        msAssert.areEqual("Section1_Header\r\n", headerFooters1.getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getParagraphs().get(0).toString(SaveFormat.TEXT));

        msAssert.areEqual("Section1_Footer\r\n", headerFooters1.getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST).getParagraphs().get(0).toString(SaveFormat.TEXT));

        msAssert.areEqual(2, doc.getSections().get(2).getHeadersFooters().getCount());
    }





    /// <summary>
    /// WORDSNET-10432 Text formatting is not preserved during open/save a DOCX
    /// Word uses ImportFormatMode.UseDestinationStyles to import altchunks.
    /// </summary>
    @Test
    public void testJira10432() throws Exception
    {
        Document doc = TestUtil.open("Model\\AltChunk\\TestJira10432.docx");

        NodeCollection paras = doc.getFirstSection().getBody().getChildNodes(NodeType.PARAGRAPH, true);

        // Verify that altchunks are imported with UseDestinationStyle.
        Paragraph p = (Paragraph)paras.get(0);
        msAssert.areEqual(0x01, p.getParaPr().get(ParaAttr.ISTD));
        msAssert.isNull(p.getFirstRun().getRunPr().get(FontAttr.ISTD));

        // AM. There is minor problem with second paragraph. Paragraph is imported properly but it has theme fonts.
        // Master document has theme part missing.
        //
        // Word creates theme with 'Calibri Light' + 'Calibri' fonts and I could not understand why.
        // Newly created Word document has 'Cambria' + 'Calibri' fonts. Maybe some special theme is used when AltChunks are imported?
        // Lets postpone this problem and wait till customer complains.
        p = (Paragraph)paras.get(2);
        msAssert.areEqual(0x02, p.getParaPr().get(ParaAttr.ISTD));
        msAssert.isNull(p.getFirstRun().getRunPr().get(FontAttr.ISTD));

        p = (Paragraph)paras.get(4);
        msAssert.isNull(p.getParaPr().get(ParaAttr.ISTD));
        msAssert.isNull(p.getFirstRun().getRunPr().get(FontAttr.ISTD));

        // Verify against gold.
        TestUtil.saveOpen(doc, "Model\\AltChunk\\TestJira10432.docx");
    }



    /// <summary>
    /// WORDSNET-16555 Aspose.Words drops header/footer from altchunk document.
    /// AW does not process headers/footers, when "AltChunk" document has just one section. Word takes in attention
    /// headers/footers of "AltChunk" document, which placed as first child in the section body.
    /// </summary>
    @Test
    public void testJira16555() throws Exception
    {
        Document doc = TestUtil.open("Model\\AltChunk\\TestJira16555.docx");
        msAssert.areEqual(1, doc.getSections().getCount());

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(3, paras.getCount());
        msAssert.areEqual("Text from altchunk document", paras.get(0).getFirstRun().getText());
        msAssert.areEqual(0, paras.get(1).getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Text from main document", paras.get(2).getFirstRun().getText());

        HeaderFooter header = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        msAssert.areEqual(1, header.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(1, header.getFirstParagraph().getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Header from altchunk document", header.getFirstParagraph().getFirstRun().getText());

        HeaderFooter footer = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);
        msAssert.areEqual(1, footer.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(1, footer.getFirstParagraph().getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Footer from altchunk document", footer.getFirstParagraph().getFirstRun().getText());
    }

    /// <summary>
    /// Related to WORDSNET-16555
    /// The case, when first element in the document body is not "AltChunk" document.
    /// </summary>
    @Test
    public void testJira16555NoHeaderFooter() throws Exception
    {
        Document doc = TestUtil.open("Model\\AltChunk\\TestJira16555NoHeaderFooter.docx");
        msAssert.areEqual(0, doc.getFirstSection().getHeadersFooters().getCount());

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(3, paras.getCount());
        msAssert.areEqual(0, paras.get(0).getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Text from altchunk document", paras.get(1).getFirstRun().getText());
        msAssert.areEqual("Text from main document", paras.get(2).getFirstRun().getText());
    }
}

