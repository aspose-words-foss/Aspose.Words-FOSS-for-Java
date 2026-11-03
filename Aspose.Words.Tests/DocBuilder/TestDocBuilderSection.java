// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDocBuilderSection
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }




    /// <summary>
    /// Test all headers are the same in one section.
    /// </summary>
    @Test
    public void testDocBuilderHeaderSame() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.write("Header");

        builder.moveToSection(0);
        builder.writeln("Page1");
        builder.insertBreak(BreakType.PAGE_BREAK);
        builder.writeln("Page2");
        builder.insertBreak(BreakType.PAGE_BREAK);
        builder.writeln("Page3");

        TestUtil.save(doc, "DocBuilder\\TestDocBuilderHeaderSame.docx", null, false);
    }

    /// <summary>
    /// Test headers different for first, even, odd pages in one section.
    /// </summary>
    @Test
    public void testDocBuilderHeaderDifferent() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getPageSetup().setDifferentFirstPageHeaderFooter(true);
        builder.getPageSetup().setOddAndEvenPagesHeaderFooter(true);

        builder.moveToHeaderFooter(HeaderFooterType.HEADER_FIRST);
        builder.write("Header First");
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_EVEN);
        builder.write("Header Even");
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.write("Header Odd");

        builder.moveToSection(0);
        builder.writeln("Page1");
        builder.insertBreak(BreakType.PAGE_BREAK);
        builder.writeln("Page2");
        builder.insertBreak(BreakType.PAGE_BREAK);
        builder.writeln("Page3");

        TestUtil.save(doc, "DocBuilder\\TestDocBuilderHeaderDifferent.docx", null, false);
    }

    /// <summary>
    /// Headers in several sections, some linked some redefined.
    /// </summary>
    @Test
    public void testDocBuilderHeaderSections() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        //Create document with three sections
        builder.writeln("Page1");
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
        builder.writeln("Page2");
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
        builder.writeln("Page3");

        //This header will apply to both first and second section because
        //the second section does not define any header.
        builder.moveToSection(0);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.write("Header1");

        //This header will apply to the third section.
        builder.moveToSection(2);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.write("Header2");

        TestUtil.save(doc, "DocBuilder\\TestDocBuilderHeaderSections.docx", null, false);
    }



    /// <summary>
    /// WORDSNET-2159 “NullReferenceException” occurs when move to Header/Footer after linking header to previous.
    ///
    /// After set IsLinkedToPrevious to true in header/footer DocumentBuilder moves cursor to first paragraph of current section.
    /// </summary>
    @Test
    public void testJira2159() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder docBuild = new DocumentBuilder(doc);

        testJira2159_Check(docBuild);

        docBuild.moveToDocumentEnd();
        docBuild.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);

        msAssert.areNotSame(doc.getFirstSection(), docBuild.getCurrentSection());
        testJira2159_Check(docBuild);
    }

    private void testJira2159_Check(DocumentBuilder docBuild)
    {
        docBuild.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        docBuild.writeln("test");
        msAssert.isNotNull(docBuild.getCurrentSection());
        msAssert.isNotNull(docBuild.getCurrentStory());
        msAssert.isNotNull(docBuild.getCurrentParagraph());

        Section section = docBuild.getCurrentSection();
        HeaderFooter header = (HeaderFooter)docBuild.getCurrentStory();
        msAssert.isFalse(header.isLinkedToPrevious());

        header.isLinkedToPrevious(true);
        msAssert.areSame(section, docBuild.getCurrentSection());
        msAssert.areSame(section.getBody(), docBuild.getCurrentStory());
        msAssert.areSame(section.getBody().getFirstParagraph(), docBuild.getCurrentParagraph());

        // chack that we don't have exception as in WORDSNET-2159
        docBuild.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);
    }
}

