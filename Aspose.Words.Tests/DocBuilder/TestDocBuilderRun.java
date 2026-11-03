// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDocBuilderRun
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }



    /// <summary>
    /// WORDSNET-10565 Issues with hebrew (right-to-left) text formatting.
    /// We should correct wrong bidi attribute set by customer to conform text displaying with MSW.
    /// If text has ltr characters and bidi is set to true
    /// or text has rtl characters and bidi is set to false,
    /// then we should split such text and write it separately
    /// with bidi=true for rtl and with bidi=false for ltr text.
    /// </summary>
    @Test
    public void testJira10565() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getParagraphFormat().setBidi(true);
        builder.getFont().setBidi(true);

        // Ltr text.
        builder.write("TODAY IS MONDAY");
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        Run run = para.getFirstRun();
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().get(FontAttr.BIDI));

        builder.write("TODAY-IS-MONDAY");
        run = (Run)run.getNextSibling();
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().get(FontAttr.BIDI));

        builder.write("TODAY_IS_MONDAY");
        run = (Run)run.getNextSibling();
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().get(FontAttr.BIDI));

        // Rtl text.
        builder.write("\u05D1\u05D3\u05D9\u05E7\u05EA \u05D1\u05E2\u05D9\u05D9\u05EA ");
        run = (Run)run.getNextSibling();
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().get(FontAttr.BIDI));
    }

    /// <summary>
    /// WORDSNET-10679 Automatically switch Bidi context of DocumentBuilder for LTR/RTL mixed strings.
    /// See for details TestJira10565().
    /// </summary>
    @Test
    public void testJira10679() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getFont().setBidi(true);
        builder.writeln("Hello שלום");

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        msAssert.areEqual(2, runs.getCount());

        Run run = (Run) runs.get(0);
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().get(FontAttr.BIDI));

        run = (Run) runs.get(1);
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().get(FontAttr.BIDI));
    }

    /// <summary>
    /// WORDSNET-10667 Automatically switch Bidi context of DocumentBuilder for LTR/RTL mixed strings.
    /// See for details TestJira10565().
    /// </summary>
    @Test
    public void testJira10667() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getParagraphFormat().setBidi(true);
        builder.getFont().setBidi(true);
        builder.write("החזרת מכולה 40' ריקה מס' EISU 1775219 לדפו");

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        msAssert.areEqual(3, runs.getCount());

        Run run = (Run)runs.get(0);
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().get(FontAttr.BIDI));
        msAssert.areEqual("החזרת מכולה 40' ריקה מס' ", run.getText());

        run = (Run)runs.get(1);
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().get(FontAttr.BIDI));
        msAssert.areEqual("EISU 1775219", run.getText());

        run = (Run)runs.get(2);
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().get(FontAttr.BIDI));
        msAssert.areEqual(" לדפו", run.getText());
    }
}

