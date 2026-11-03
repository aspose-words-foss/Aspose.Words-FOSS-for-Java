// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2017 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msObject;


/// <summary>
/// Implements tests to check insertion of the style separator.
/// </summary>
@Test
public class TestDocBuilderStyleSeparator
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-15943 Support to insert Style Separator to put different Paragraph styles.
    /// Ability to insert style separator was introduced. Client case.
    /// </summary>
    @Test
    public void testJira15943() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());

        Style paraStyle = builder.getDocument().getStyles().add(StyleType.PARAGRAPH, "MyParaStyle");
        paraStyle.getFont().setBold(false);
        paraStyle.getFont().setSize(8.0);
        paraStyle.getFont().setName("Arial");

        // Insert a table of contents at the beginning of the document.
        Field field = builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.write("Heading 1");

        // Currently document does not contain separate paragraph for "Heading 1".
        // So, model has not paragraph to which style separator has to be applied.
        // Update field and extract appropriate paragraph. 
        field.update();

        // Document builder is positioned on the paragraph with TOC field. 
        // Move document builder to destination paragraph and apply style separator.
        builder.moveTo(builder.getCurrentParagraph().getNextSibling());
        builder.insertStyleSeparator();

        // Append text with another style.
        builder.getParagraphFormat().setStyleName(paraStyle.getName());
        builder.write("This is text with some other formatting ");

        // One more separator like in example attached to issue.
        builder.insertStyleSeparator();

        // One gold for client issue.
        TestUtil.saveOpen(builder.getDocument(), "DocBuilder\\StyleSeparator\\TestJira15943.docx", UnifiedScenario.DOCX_2_DOCX);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// One of base tests with style separator. See explanations inside test.
    /// </summary>
    @Test
    public void testJira15943CheckContent() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        Body body = builder.getDocument().getFirstSection().getBody();

        // 1. Document contains single paragraph. Expected behavior: "Hidden" attributes added to current paragraph, 
        //    new paragraph terminates current line. Current paragraph will be changed. 
        builder.write("Text line");
        Paragraph para = builder.getCurrentParagraph();

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(para, builder.getCurrentParagraph(), 2);
        checkParagraphContent(builder, builder.getCurrentParagraph(), new String[] { " " }, body.getLastParagraph());

        // 2. Document contains two paragraphs with style separator and one more paragraph in new line. Builder positioned to
        //    the end of first paragraph and new style separator is inserted. 
        //    Expected behavior: Paragraphs are line up. Builder position is not changed.
        para = body.appendChild(new Paragraph(builder.getDocument()));
        para.appendChild(new Run(builder.getDocument(), "Second text"));
        para.appendChild(new Run(builder.getDocument(), " line"));
        builder.moveTo(body.getFirstParagraph(), -1);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(1), body.getLastParagraph(), 3);
        checkParagraphContent(builder, body.getLastParagraph(), new String[] { " ", "Second text", " line" },
            body.getFirstParagraph());
        msAssert.isTrue(body.getFirstParagraph() == builder.getCurrentParagraph());

        // 3. Document contains 3 paragraphs. Builder positioned to the end of first of them.
        //    Two style separators will be inserted. This case checks how spaces have to be added
        //    into lined up paragraphs.
        msAssert.areEqual(3, body.getParagraphs().getCount());
        para = body.appendChild(new Paragraph(builder.getDocument()));
        para.appendChild(new Run(builder.getDocument(), "Text line."));
        para = body.appendChild(new Paragraph(builder.getDocument()));
        para.appendChild(new Run(builder.getDocument(), "Second text line."));
        para = body.appendChild(new Paragraph(builder.getDocument()));
        para.appendChild(new Run(builder.getDocument(), " Third line."));
        builder.moveTo(body.getParagraphs().get(3), -1);

        builder.insertStyleSeparator();
        checkParagraphContent(builder, body.getParagraphs().get(4), new String[] { " ", "Second text line." },
            body.getParagraphs().get(4).getRuns().get(1));

        builder.moveTo(body.getParagraphs().get(4), -1);
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(4), body.getParagraphs().get(5), 6);
        checkParagraphContent(builder, body.getParagraphs().get(5), new String[] { " Third line." }, body.getParagraphs().get(5).getFirstRun());
        msAssert.areEqual(6, body.getParagraphs().getCount());

        // 4. This case checks breaking condition of the iteration over paragraphs.
        builder.moveTo(body.getParagraphs().get(4), -1);
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(5), body.getLastParagraph(), 7);
        checkParagraphContent(builder, body.getLastParagraph(), new String[] { " " }, body.getParagraphs().get(4));
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks how builder positon has to be changed after style separator insertion.
    /// </summary>
    @Test
    public void testJira15943CheckBuilderPosition() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        Body body = builder.getDocument().getFirstSection().getBody();

        // 1. Insert separator when builder is positioned to the middle of paragraph.
        //    Expected behavior: Position of the builder will not be changed and new paragraph will be added.
        builder.write("Text ");
        Node cursor = body.getFirstParagraph().getFirstRun();
        builder.write("line");
        builder.moveTo(cursor);
        msAssert.areEqual(1, body.getParagraphs().getCount());

        builder.insertStyleSeparator();
        msAssert.areEqual(2, body.getParagraphs().getCount());
        msAssert.isTrue(body.getFirstParagraph() == builder.getCurrentParagraph());
        msAssert.isTrue(msObject.referenceEquals(cursor, builder.getCurrentNode()));

        // 2. Two paragraphs with content. First paragraph has style separator. 
        //    Builder positioned in the middle of the second paragraph. Insert one more style separator.
        //    Expected behavior: Position of the builder will not be changed and new paragraph will be added.
        Paragraph curPara = body.getLastParagraph();
        cursor = curPara.appendChild(new Run(builder.getDocument(), "Second line"));
        curPara.appendChild(new Run(builder.getDocument(), " text"));
        builder.moveTo(cursor);
        msAssert.areEqual(2, body.getParagraphs().getCount());

        builder.insertStyleSeparator();
        msAssert.areEqual(3, body.getParagraphs().getCount());
        msAssert.isTrue(curPara == builder.getCurrentParagraph());
        msAssert.isTrue(msObject.referenceEquals(cursor, builder.getCurrentNode()));

        // 3. Two paragraphs with contents and style separators. Builder positioned in the end of the second paragraph. 
        //    Insert one more style separator. Expected behavior: Position of the builder will be changed and new paragraph
        //    will be added.
        builder.moveTo(body.getLastParagraph(), -1);

        builder.insertStyleSeparator();
        msAssert.areEqual(4, body.getParagraphs().getCount());
        Paragraph lastPara = body.getLastParagraph();
        msAssert.isTrue(lastPara == builder.getCurrentParagraph());
        msAssert.isTrue(builder.isAtEndOfParagraph());
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks how style separators have to be inserted into document with multiple sections.
    /// </summary>
    @Test
    public void testJira15943MultiSections1() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943MultiSections.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        Body body = doc.getFirstSection().getBody();

        // 1. First section of the document contains two paragraphs. Style separator has to be inserted.
        //    Expected behavior: First paragraph will be marked as style separator.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getFirstParagraph(), body.getLastParagraph(), 3);
        checkParagraphContent(builder, body.getLastParagraph(),
            new String[] { " ", "Second text line" }, body.getFirstParagraph().getFirstRun());

        // 2. Expected behavior: Second paragraph will be marked as style separator.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getFirstParagraph(), body.getLastParagraph(), 4);
        checkStyleSeparatorAttr(null, doc.getLastSection().getBody().getFirstParagraph(), 4);
        msAssert.areEqual(1, doc.getLastSection().getBody().getParagraphs().getCount());

        // 3. Move builder position to the end of the last paragraph of the first section.           
        //    Expected behavior: Last paragraph will be marked as style separator.
        builder.moveTo(body.getLastParagraph());
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getFirstParagraph(), body.getLastParagraph(), 5);
        checkStyleSeparatorAttr(null, doc.getLastSection().getBody().getFirstParagraph(), 5);
        msAssert.areEqual(1, doc.getLastSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Case description:
    /// First section contains two paragraphs which have style separator properties.
    /// One more style separator will be inserted.
    /// Expected behavior: Nothing will change.
    /// </summary>
    @Test
    public void testJira15943MultiSections2() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943MultiSections2.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        Body body = doc.getFirstSection().getBody();

        builder.insertStyleSeparator();
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.write("Heading 1");
        
        checkStyleSeparatorAttr(body.getFirstParagraph(), null, 5);
        checkStyleSeparatorAttr(body.getParagraphs().get(1), null, 5);
        checkParagraphContent(builder, body.getParagraphs().get(1),
            new String[] { " ", "dfvfd" }, body.getFirstParagraph().getRuns().get(1));
        checkStyleSeparatorAttr(null, body.getLastParagraph(), 5);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// One of base tests with SDT. See explanations inside test.
    /// </summary>
    @Test
    public void testJira15943SDT1() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT1.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        Node expectedPos = body.getFirstParagraph().getFirstRun();
        msAssert.areEqual(5, body.getChildNodes(NodeType.PARAGRAPH, true).getCount());
        StructuredDocumentTag sdt = (StructuredDocumentTag)body.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, false);
        NodeCollection sdtParas = sdt.getChildNodes(NodeType.PARAGRAPH, true);

        // 1. Documents contains paragraph and block level SDT. Builder is positioned in the first run of the document.
        //    Style separator will be inserted. Expected behavior: First paragraph is marked as style separator. New
        //    paragraph with the space is added after style separator. Content of the SDT is not changed.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getFirstParagraph(), body.getParagraphs().get(1), 6);           
        checkStyleSeparatorAttr(null, (Paragraph)sdtParas.get(0), 6); // First para into SDT.
        checkParagraphContent(builder, (Paragraph)sdtParas.get(0), new String[] { "Rich" }, expectedPos);            
        checkStyleSeparatorAttr(null, body.getParagraphs().get(1), 6); // Additional paragraph was inserted.
        checkParagraphContent(builder, body.getParagraphs().get(1), new String[] { " " }, expectedPos);

        // 2. Expected behavior: Style separator properties were applied to additional paragraph inserted above with
        //    the style separator and first paragraph into SDT.           
        builder.insertStyleSeparator();
        // First paragraph still has style separator properties.
        checkStyleSeparatorAttr(body.getFirstParagraph(), null, 6);
        // Additional paragraph, which was inserted before SDT.
        checkStyleSeparatorAttr(body.getParagraphs().get(1), null, 6);
        checkParagraphContent(builder, body.getParagraphs().get(1), new String[] { " " }, expectedPos);
        // First paragraph into SDT also has style separator properties.
        checkStyleSeparatorAttr((Paragraph)sdtParas.get(0), null, 6);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(0), new String[] { "Rich" }, expectedPos);
        // Second paragraph of the SDT produces new line.
        checkStyleSeparatorAttr(null, (Paragraph)sdtParas.get(1), 6);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(1), new String[] { " ", "Text" }, expectedPos);

        // 3. Expected behavior: Style separator properties were applied to next paragraph into SDT.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr((Paragraph)sdtParas.get(1), (Paragraph)sdtParas.get(2), 6);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(1), new String[] { " ", "Text" }, expectedPos);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(2), new String[] { " ", "Control" }, expectedPos);

        // 4. Expected behavior: Style separator properties were applied to next paragraph into SDT.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr((Paragraph)sdtParas.get(2), (Paragraph)sdtParas.get(3), 6);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(2), new String[] { " ", "Control" }, expectedPos);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(3), new String[] { " ", "Content" }, expectedPos);

        // 5. Expected behavior: Additional paragraph will be added after SDT. Style separator properties will
        //    be applied to last paragraph of the SDT.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr((Paragraph)sdtParas.get(3), body.getLastParagraph(), 7);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(3), new String[] { " ", "Content" }, expectedPos);
        checkParagraphContent(builder, body.getLastParagraph(), new String[] { " " }, expectedPos);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// One of Base test with SDT. See explanations inside test.
    /// </summary>
    @Test
    public void testJira15943SDT2() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT1.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();

        StructuredDocumentTag sdt = (StructuredDocumentTag)body.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, false);
        NodeCollection sdtParas = sdt.getChildNodes(NodeType.PARAGRAPH, true);

        // Move builder to the end of the first paragraph.
        builder.moveTo(body.getFirstParagraph(), -1);

        // 1. Builder is positioned to the end of the first paragraph.
        //    Style separator will be inserted. 
        //    Expected behavior: First paragraph is marked as style separator. New paragraph with the space is added after
        //    style separator. Content of the SDT is not changed. Builder position is changed to the second run of the
        //    first paragraph inside SDT. However, actually document markup does not contain space before first run of the
        //    SDT. It looks like some inconsistent between document presentation and markup. So, just move to end of
        //    paragraph which was added.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getFirstParagraph(), body.getParagraphs().get(1), 6);
        checkStyleSeparatorAttr(null, (Paragraph)sdtParas.get(0), 6);

        Node expectedPosition = body.getParagraphs().get(1);
        checkParagraphContent(builder, body.getParagraphs().get(1), new String[] { " " }, expectedPosition);
        // Check content of the first paragraph into SDT.
        checkParagraphContent(builder, (Paragraph)sdtParas.get(0), new String[] { "Rich" }, expectedPosition);

        // 2. MSW does not add next style separator when client is positioned to the end of the paragraph before SDT.
        //    Check this case.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getFirstParagraph(), body.getParagraphs().get(1), 6);
        checkStyleSeparatorAttr(null, (Paragraph)sdtParas.get(0), 6);

        checkParagraphContent(builder, body.getParagraphs().get(1), new String[] { " " }, expectedPosition);
        // Check content of the first paragraph into SDT.
        checkParagraphContent(builder, (Paragraph)sdtParas.get(0), new String[] { "Rich" }, expectedPosition);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Case checks style separator insertion when paragraph before SDT has style separator attributes.
    /// </summary>
    @Test
    public void testJira15943SDT3() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT3.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        Node expectedPosition = body.getFirstParagraph();

        StructuredDocumentTag sdt = (StructuredDocumentTag)body.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, false);
        NodeCollection sdtParas = sdt.getChildNodes(NodeType.PARAGRAPH, true);

        builder.moveTo(body.getFirstParagraph(), -1);
        builder.insertStyleSeparator();

        checkStyleSeparatorAttr(body.getFirstParagraph(), null, 5);
        checkStyleSeparatorAttr((Paragraph)sdtParas.get(0), null, 5);
        checkStyleSeparatorAttr(null, (Paragraph)sdtParas.get(1), 5);

        checkParagraphContent(builder, body.getFirstParagraph(), new String[] { "Text line" }, expectedPosition);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(0), new String[] { "Rich" }, expectedPosition);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(1), new String[] { " ", "Text" }, expectedPosition);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Case checks style separator insertion for two sequently positioned SDT's.
    /// </summary>
    @Test
    public void testJira15943SDT4() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT4.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();

        StructuredDocumentTag sdt = (StructuredDocumentTag)body.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, false);
        NodeCollection sdtParas = sdt.getChildNodes(NodeType.PARAGRAPH, true);

        builder.insertStyleSeparator();
        Node expectedPosition = ((Paragraph)sdtParas.get(1)).getRuns().get(1);
        checkStyleSeparatorAttr((Paragraph)sdtParas.get(0), (Paragraph)sdtParas.get(1), 4);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(0), null, expectedPosition);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(1), new String[] { " ", "SDT" }, expectedPosition);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr((Paragraph)sdtParas.get(1), body.getParagraphs().get(2), 5);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(1), new String[] { " ", "SDT" }, expectedPosition);
        checkParagraphContent(builder, body.getParagraphs().get(2), new String[] { " " }, expectedPosition);

        sdt = (StructuredDocumentTag)body.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, false);
        sdtParas = sdt.getChildNodes(NodeType.PARAGRAPH, true);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(2), null, 5);
        checkParagraphContent(builder, body.getParagraphs().get(2), new String[] { " " }, expectedPosition);

        checkStyleSeparatorAttr((Paragraph)sdtParas.get(0), (Paragraph)sdtParas.get(1), 5);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(0), new String[] { "One" }, expectedPosition);
        checkParagraphContent(builder, (Paragraph)sdtParas.get(1), new String[] { " ", "More" }, expectedPosition);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Case with nested SDT.
    /// </summary>
    @Test
    public void testJira15943SDT5() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT5.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        Node expectedPosition = body.getFirstParagraph().getFirstRun();

        // One paragraph in the body and two paragraph in nested SDT.
        // First style separator.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(0), body.getParagraphs().get(1), 5);
        checkParagraphContent(builder, body.getParagraphs().get(0), new String[] { "Line1" }, expectedPosition);
        checkParagraphContent(builder, body.getParagraphs().get(1), new String[] { " " }, expectedPosition);

        // Second style separator.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(1), null, 6);
        checkParagraphContent(builder, body.getParagraphs().get(1), new String[] { " " }, expectedPosition);

        checkStyleSeparatorAttr(body.getParagraphs().get(2), body.getParagraphs().get(3), 6);
        checkParagraphContent(builder, body.getParagraphs().get(2), new String[] { "Line2" }, expectedPosition);
        checkParagraphContent(builder, body.getParagraphs().get(3), new String[] { " " }, expectedPosition);

        // Third style separator.
        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(3), null, 6);
        checkParagraphContent(builder, body.getParagraphs().get(3), new String[] { " " }, expectedPosition);

        checkStyleSeparatorAttr(body.getParagraphs().get(4), body.getParagraphs().get(5), 6);
        checkParagraphContent(builder, body.getParagraphs().get(4), new String[] { "Line3" }, expectedPosition);
        checkParagraphContent(builder, body.getParagraphs().get(5), new String[] { " " }, expectedPosition);

        msAssert.isTrue(body.getParagraphs().get(5).getParentNode() == body);
        msAssert.isTrue(body.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true) == body.getParagraphs().get(4).getParentNode());
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Case with nested SDT.
    /// </summary>
    @Test
    public void testJira15943SDT6() throws Exception
    {   
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT6.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        builder.moveTo(body.getParagraphs().get(2).getFirstRun());
        Node expectedPosition = body.getParagraphs().get(2).getFirstRun();

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(2), body.getParagraphs().get(3), 6);
        checkParagraphContent(builder, body.getParagraphs().get(2), new String[] { "Line3" }, expectedPosition);
        checkParagraphContent(builder, body.getParagraphs().get(3), new String[] { " " }, expectedPosition);
        msAssert.isTrue(body.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true) == body.getParagraphs().get(3).getParentNode());

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(3), null, 6);
        checkParagraphContent(builder, body.getParagraphs().get(3), new String[] { " " }, expectedPosition);

        checkStyleSeparatorAttr(body.getParagraphs().get(4), body.getParagraphs().get(5), 6);
        checkParagraphContent(builder, body.getParagraphs().get(4), new String[] { "Line4" }, expectedPosition);
        checkParagraphContent(builder, body.getParagraphs().get(5), new String[] { " " }, expectedPosition);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Case with nested SDT. Style separator inserts for paragraph into SDT which positioned before another SDT.
    /// </summary>
    @Test
    public void testJira15943SDT7() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT7.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        Node expectedPosition = body.getParagraphs().get(1);
        builder.moveTo(body.getParagraphs().get(1), -1);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(1), body.getParagraphs().get(2), 4);

        checkParagraphContent(builder, body.getParagraphs().get(1), new String[] { "Line2" }, expectedPosition);
        checkParagraphContent(builder, body.getParagraphs().get(2), new String[] { " ", "Line3" }, expectedPosition);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(2), body.getParagraphs().get(3), 4);
        checkParagraphContent(builder, body.getParagraphs().get(2), new String[] { " ", "Line3" }, expectedPosition);
        checkParagraphContent(builder, body.getParagraphs().get(3), new String[] { " " }, expectedPosition);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks that inline SDT is skipped while style separator inserting. 
    /// </summary>
    @Test
    public void testJira15943SDT8() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT8.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        Node expectedPosition = body.getParagraphs().get(1).getFirstRun();
        builder.moveTo(body.getParagraphs().get(0));

        builder.insertStyleSeparator();
        builder.insertStyleSeparator();

        checkStyleSeparatorAttr(body.getParagraphs().get(0), null, 3);
        checkStyleSeparatorAttr(body.getParagraphs().get(1), null, 3);
        checkStyleSeparatorAttr(null, body.getParagraphs().get(2), 3);

        checkParagraphContent(builder, body.getParagraphs().get(0), null, expectedPosition);
        checkParagraphContent(builder, body.getParagraphs().get(2), new String[] { " ", "Line3" }, expectedPosition);

        msAssert.areEqual(4, body.getParagraphs().get(1).getChildNodes(NodeType.ANY, false).getCount());
        msAssert.isTrue(body.getParagraphs().get(1).getBreakIsStyleSeparator());
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks the case when document builder is positioned to the empty paragraph before SDT.
    /// AW behavior: Nothing is changed. MSW behavior: One more paragraph with the space is added.
    /// </summary>
    @Test
    public void testJira15943SDT9() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT9.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        builder.moveTo(body.getFirstParagraph());

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getParagraphs().get(0), body.getParagraphs().get(1), 3);
        checkParagraphContent(builder, body.getParagraphs().get(0), new String[] { "Line1" }, body.getParagraphs().get(1));
        checkParagraphContent(builder, body.getParagraphs().get(1), new String[] { " " }, body.getParagraphs().get(1));

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(null, body.getParagraphs().get(1), 3);
        checkStyleSeparatorAttr(null, body.getParagraphs().get(2), 3);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks the case when paragraph is the last paragraph inside SDT and this paragraph is last in last section.
    /// Also paragraph is empty and builder is positioned to this paragraph. Expected behavior: nothing is happened.
    /// </summary>
    @Test
    public void testJira15943SDT10() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943SDT10.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        builder.moveTo(body.getLastParagraph());

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(null, body.getLastParagraph(), 2);
        checkParagraphContent(builder, body.getLastParagraph(), null, body.getLastParagraph());
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks how style separators have to be inserted into document with table.
    /// </summary>
    @Test
    public void testJira15943Tbl1() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943Tbl1.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        Cell cell = body.getTables().get(0).getFirstRow().getFirstCell();
        builder.moveTo(body.getFirstParagraph());

        Node expectedPosition = cell.getFirstParagraph().getFirstRun();
        
        builder.insertStyleSeparator();
        builder.insertStyleSeparator();
        builder.insertStyleSeparator();

        msAssert.areEqual(3, cell.getParagraphs().getCount());
        checkStyleSeparatorAttr(body.getFirstParagraph(), null, 8);
        checkStyleSeparatorAttr(cell.getParagraphs().get(0), null, 8);
        checkStyleSeparatorAttr(cell.getParagraphs().get(1), null, 8);
        checkStyleSeparatorAttr(null, cell.getParagraphs().get(2), 8);

        checkParagraphContent(builder, body.getFirstParagraph(), new String[] { "Text line" }, expectedPosition);
        checkParagraphContent(builder, cell.getParagraphs().get(0), new String[] { " ", "Cell1" }, expectedPosition);
        checkParagraphContent(builder, cell.getParagraphs().get(1), new String[] { " ", "Text" }, expectedPosition);
        checkParagraphContent(builder, cell.getParagraphs().get(2), new String[] { " ", "Line" }, expectedPosition);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(null, cell.getParagraphs().get(2), 8);
        checkParagraphContent(builder, cell.getParagraphs().get(2), new String[] { " ", "Line" }, expectedPosition);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks the case when style separators insert within table cell which nested into SDT and another tables.
    /// </summary>
    @Test
    public void testJira15943Tbl2() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943Tbl2.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        Cell cell = ((Table)body.getChild(NodeType.TABLE, 1, true)).getFirstRow().getFirstCell();
        Node expectedPosition = body.getFirstParagraph().getFirstRun();

        builder.insertStyleSeparator();
        builder.insertStyleSeparator();

        // Actually MSW removes SDT at this case. Currently implemented simplified solution.
        checkStyleSeparatorAttr(body.getFirstParagraph(), null, 10);
        checkStyleSeparatorAttr(body.getParagraphs().get(1), cell.getParagraphs().get(0), 10);
        checkStyleSeparatorAttr(null, cell.getParagraphs().get(1), 10);

        checkParagraphContent(builder, body.getFirstParagraph(), new String[] { "Line1" }, expectedPosition);
        checkParagraphContent(builder, cell.getParagraphs().get(0), new String[] { " ", "NestedCell1" }, expectedPosition);
        checkParagraphContent(builder, cell.getParagraphs().get(1), new String[] { "Line1.5" }, expectedPosition);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks the case when style separators insert into document where table is following after block level SDT.
    /// </summary>
    @Test
    public void testJira15943Tbl3() throws Exception
    {
        // SDT before table, check behavior.
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943Tbl3.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        Cell cell = body.getTables().get(0).getFirstRow().getFirstCell();
        builder.moveTo(body.getFirstParagraph());

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getFirstParagraph(), cell.getParagraphs().get(0), 5);
        checkParagraphContent(builder, body.getFirstParagraph(), new String[] { "Line1" }, body.getFirstParagraph());
        checkParagraphContent(builder, cell.getParagraphs().get(0), new String[] { " ", "Cell1" }, body.getFirstParagraph());
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks the case when style separator inserts into table cell which placed within shape content.
    /// </summary>

    @Test
    public void testJira15943Tbl4() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943Tbl4.docx"));
        Table tbl = (Table)builder.getDocument().getFirstSection().getBody().getChild(NodeType.TABLE, 0, true);
        builder.moveTo(tbl.getFirstRow().getFirstCell().getFirstParagraph());

        TestWarningCallback wc = new TestWarningCallback();
        builder.getDocument().setWarningCallback(wc);

        builder.insertStyleSeparator();

        msAssert.areEqual(1, wc.getCount());
        msAssert.isTrue(wc.contains(WarningSource.UNKNOWN, WarningType.UNEXPECTED_CONTENT,
                "Insertion of style separator allowed in the main text story."));
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks the case when style separators insert into cell where SDT following after the paragraph. 
    /// </summary>
    @Test
    public void testJira15943Tbl5() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943Tbl5.docx"));
        Cell cell = ((Table)builder.getDocument().getFirstSection().getBody().getChild(NodeType.TABLE, 0, true)).getFirstRow().getFirstCell();
        Node expectedPos = cell.getFirstParagraph().getFirstRun();
        builder.moveTo(expectedPos);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(cell.getFirstParagraph(), cell.getParagraphs().get(1), 9);
        checkParagraphContent(builder, cell.getParagraphs().get(1), new String[] { " " }, expectedPos);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(cell.getParagraphs().get(1), null, 9);
        checkStyleSeparatorAttr(cell.getParagraphs().get(2), cell.getParagraphs().get(3), 9);
        checkParagraphContent(builder, cell.getParagraphs().get(2), new String[] { "SDT line 2" }, expectedPos);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(cell.getParagraphs().get(3), cell.getParagraphs().get(4), 9);
        checkParagraphContent(builder, cell.getParagraphs().get(4), new String[] { " " }, expectedPos);

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(cell.getParagraphs().get(4), cell.getParagraphs().get(5), 9);
        checkParagraphContent(builder, cell.getParagraphs().get(5), null, expectedPos);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks that style separator can not be inserted within header.
    /// </summary>
    @Test
    public void testJira15943Header() throws Exception
    {
        checkHeaderFooterStyleSeparator(HeaderFooterType.HEADER_PRIMARY);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks that style separator can not be inserted within footer.
    /// </summary>

    @Test
    public void testJira15943Footer() throws Exception
    {
        checkHeaderFooterStyleSeparator(HeaderFooterType.FOOTER_PRIMARY);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks that style separator can not be inserted within shape content.
    /// </summary>
    @Test
    public void testJira15943Shape1() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943Shape.docx"));
        Shape shape = builder.getDocument().getFirstSection().getBody().getShapes().get(0);
        builder.moveTo(shape.getFirstParagraph());

        TestWarningCallback wc = new TestWarningCallback();
        builder.getDocument().setWarningCallback(wc);

        builder.insertStyleSeparator();

        msAssert.areEqual(1, wc.getCount());
        msAssert.isTrue(wc.contains(WarningSource.UNKNOWN, WarningType.UNEXPECTED_CONTENT,
                "Insertion of style separator allowed in the main text story."));
        checkStyleSeparatorAttr(null, shape.getFirstParagraph(), 3);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Picture with block level content occurred in paragraph. 
    /// Current case checks that processing is skipped for such content.
    /// </summary>  
    @Test
    public void testJira15943Shape2() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open("DocBuilder\\StyleSeparator\\TestJira15943Tbl4.docx"));
        Body body = builder.getDocument().getFirstSection().getBody();
        builder.moveTo(body.getFirstParagraph());

        builder.insertStyleSeparator();
        builder.insertStyleSeparator();

        checkStyleSeparatorAttr(body.getFirstParagraph(), null, 7);
        checkStyleSeparatorAttr(body.getParagraphs().get(1), body.getLastParagraph(), 7);
        checkStyleSeparatorAttr(null, body.getShapes().get(0).getFirstParagraph(), 7);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks insertion of style separator to footnote.
    /// </summary>
    @Test
    public void testJira15943Footnote() throws Exception
    {
        final String PATH = "DocBuilder\\StyleSeparator\\TestJira15943Common1.docx";
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open(PATH));
        Footnote footnote = (Footnote)builder.getDocument().getChild(NodeType.FOOTNOTE, 0, true);
        msAssert.areEqual(footnote.getFootnoteType(), FootnoteType.FOOTNOTE);

        builder.moveTo(footnote.getFirstParagraph());
        Node expectedPosition = footnote.getLastParagraph().getFirstRun();

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(footnote.getFirstParagraph(), footnote.getLastParagraph(), 10);
        checkParagraphContent(builder, footnote.getLastParagraph(), new String[] { " ", "text line" }, expectedPosition);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Checks insertion of style separator to endnote.
    /// </summary>
    @Test
    public void testJira15943Endnote() throws Exception
    {
        final String PATH = "DocBuilder\\StyleSeparator\\TestJira15943Common1.docx";
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open(PATH));
        Footnote footnote = (Footnote)builder.getDocument().getChild(NodeType.FOOTNOTE, 1, true);
        msAssert.areEqual(footnote.getFootnoteType(), FootnoteType.ENDNOTE);

        builder.moveTo(footnote.getFirstParagraph());
        Node expectedPosition = footnote.getLastParagraph().getFirstRun();

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(footnote.getFirstParagraph(), footnote.getLastParagraph(), 10);
        checkParagraphContent(builder, footnote.getLastParagraph(), new String[] { " ", "line" }, expectedPosition);
    }

    /// <summary>
    /// This test is the part of the WORDSNET-15943
    /// Simple test which inserts style separator to document with one paragraph in the body.
    /// </summary>
    @Test
    public void testJira15943OnePara() throws Exception
    {
        final String PATH = "DocBuilder\\StyleSeparator\\TestJira15943Common2.docx";
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open(PATH));
        Body body = builder.getDocument().getFirstSection().getBody();

        builder.insertStyleSeparator();
        checkStyleSeparatorAttr(body.getFirstParagraph(), body.getLastParagraph(), 2);
    }


    /// <summary>
    /// WORDSNET-24053 Style Separator is not inserted if add an empty section before building the document.
    /// The redundant check removed.
    /// </summary>
    @Test
    public void test24053() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        doc.getSections().add(new Section(doc));
        // Append text with "Heading 1" style.
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.write("Heading 1");
        builder.insertStyleSeparator();
        // Append text with another style.
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.NORMAL);
        builder.write("This is text with some other formatting ");

        TestUtil.saveOpen(doc, "DocBuilder\\StyleSeparator\\Test24053", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(2, doc.getSections().getCount());
        msAssert.isTrue(doc.getFirstSection().getBody().getFirstParagraph().getBreakIsStyleSeparator());
    }

    /// <summary>
    /// Checks that style separator can not be inserted to the header/footer of the specified type.
    /// </summary>
    private static void checkHeaderFooterStyleSeparator(/*HeaderFooterType*/int type) throws Exception
    {
        final String PATH = "DocBuilder\\StyleSeparator\\TestJira15943Common1.docx";
        DocumentBuilder builder = new DocumentBuilder(TestUtil.open(PATH));
        HeaderFooter header = builder.getDocument().getFirstSection().getHeadersFooters().getByHeaderFooterType(type);
        builder.moveTo(header.getFirstParagraph());

        TestWarningCallback wc = new TestWarningCallback();
        builder.getDocument().setWarningCallback(wc);

        builder.insertStyleSeparator();
        msAssert.areEqual(1, wc.getCount());
        msAssert.isTrue(wc.contains(WarningSource.UNKNOWN, WarningType.UNEXPECTED_CONTENT,
                "Insertion of style separator allowed in the main text story."));
        checkStyleSeparatorAttr(null, header.getFirstParagraph(), 10);
    }

    /// <summary>
    /// Checks content of the specified paragraph and current position of the document builder.
    /// </summary>
    private static void checkParagraphContent(DocumentBuilder builder, Paragraph para,
        String[] expectedContent, Node expectedPosition)
    {
        int expectedLenght = (expectedContent == null) ? 0 : expectedContent.length;
        msAssert.areEqual(expectedLenght, para.getChildNodes(NodeType.ANY, false).getCount());

        if (expectedLenght > 0)
        {
            NodeCollection runs = para.getChildNodes(NodeType.RUN, false);

            for (int i = 0; i < expectedContent.length; ++i)
                msAssert.areEqual(expectedContent[i], ((Run)runs.get(i)).getText());
        }

        if (expectedPosition.getNodeType() == NodeType.PARAGRAPH)
        {
            msAssert.isTrue(builder.isAtEndOfParagraph());
            msAssert.isTrue(builder.getCurrentParagraph() == expectedPosition);
        }
        else if (expectedPosition.getNodeType() == NodeType.RUN)
        {
            msAssert.isTrue(expectedPosition == builder.getCurrentNode());
            msAssert.isTrue(builder.getCurrentParagraph() == expectedPosition.getParentNode());
        }
        else
        {
            Assert.fail("Builder was positioned to unexpected node type.");
        }
    }

    /// <summary>
    /// Checks style separator attributes are set for "styleSeparatorPara" and does not exist for "lineBreakPara".
    /// Also checks total amount of paragraphs in the document.
    /// </summary>
    private static void checkStyleSeparatorAttr(Paragraph styleSeparatorPara,
        Paragraph lineBreakPara, int expectedParaCount)
    {
        RunPr paraRunPr;
        DocumentBase doc = null;

        if (styleSeparatorPara != null)
        {
            doc = styleSeparatorPara.getDocument();                
            msAssert.isTrue(styleSeparatorPara.getBreakIsStyleSeparator());
        }

        if (lineBreakPara != null)
        {
            doc = lineBreakPara.getDocument();
            paraRunPr = lineBreakPara.getParagraphBreakRunPr();
            msAssert.isFalse(paraRunPr.contains(FontAttr.SPECIAL_HIDDEN));
            msAssert.isFalse(paraRunPr.contains(FontAttr.HIDDEN));
        }

        if (doc != null)
            msAssert.areEqual(expectedParaCount, doc.getChildNodes(NodeType.PARAGRAPH, true).getCount());
    }
}

