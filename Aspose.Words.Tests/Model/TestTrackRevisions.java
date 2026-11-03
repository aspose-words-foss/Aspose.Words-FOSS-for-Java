// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.ms.System.Globalization.msCultureInfo;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests revision tracking.
/// </summary>
@Test
public class TestTrackRevisions
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void testSetUp()
    {
        RandomUtil.setTestMode();
    }

    /// <summary>
    /// Tests how a paragraph with single run in it is removed.
    /// Run should be marked as removed. Paragraph should be marked as removed.
    /// </summary>
    @Test
    public void testRemoveSingleParagraph() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveSingleParagraph.docx";
        Document doc = TestUtil.open(TEST_NAME);

        // Start tracking.
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        paragraph.remove();

        checkMarkedForDeletion(paragraph.getFirstRun());
        checkMarkedForDeletion(paragraph);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how single run is removed. Paragraph has few runs. First run is removed.
    /// Run should be marked as removed. Paragraph should not be marked as removed.
    /// </summary>
    @Test
    public void testRemoveSingleRun() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveSingleRun.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        Run run = paragraph.getFirstRun();
        checkNotMarked(run);
        checkNotMarked(paragraph);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        run.remove();

        checkMarkedForDeletion(run);
        checkNotMarked(paragraph);

        TestUtil.save(doc, TEST_NAME);
    }


    /// <summary>
    /// Tests how NodeRemover is tracked.
    /// </summary>
    @Test
    public void testNodeRemover() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestNodeRemoverDontJoin.docx";

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Para1");
        builder.writeln("Para2");
        builder.writeln("Para3");
        builder.writeln("Para4");

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        NodeRange range = new NodeRange(paras.get(1), true, paras.get(2), true);

        NodeRemover nodeRemover = new NodeRemover(range, NodeJoinMode.DONT_JOIN, false);
        nodeRemover.removeCore();

        checkNotMarked(paras.get(0));
        checkNotMarked(paras.get(0).getFirstRun());

        checkMarkedForDeletion(paras.get(1));
        checkMarkedForDeletion(paras.get(1).getFirstRun());

        checkNotMarked(paras.get(2));
        checkMarkedForDeletion(paras.get(2).getFirstRun());

        checkNotMarked(paras.get(3));
        checkNotMarked(paras.get(3).getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how a run in comment is deleted.
    /// Word does not track changes in comments.
    /// </summary>
    @Test
    public void testRemoveRunInComment() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveRunInComment.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Comment comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);
        Paragraph paragraph = comment.getFirstParagraph();
        Run run = paragraph.getFirstRun();

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        checkNotMarked(run);
        checkNotMarked(paragraph);
        msAssert.areEqual("Test comment", run.getText());

        run.remove();

        // Verify that run is really removed, not marked.
        checkNotMarked(run);
        checkNotMarked(paragraph);
        msAssert.isNull(run.getParentNode());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how shape is deleted. Shape.RunPr should be marked as deleted.
    /// </summary>
    @Test
    public void testRemoveShape() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveShape.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        checkNotMarked(shape);
        shape.remove();

        checkMarkedForDeletion(shape);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how textbox is deleted.
    /// Shape.RunPr should be marked but inner paragraphs and runs should not.
    /// </summary>
    @Test
    public void testRemoveTextbox() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveTextbox.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Shape textbox = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        checkNotMarked(textbox);
        textbox.remove();

        checkMarkedForDeletion(textbox);

        // Verify that textbox content is not marked.
        for (Paragraph paragraph : (Iterable<Paragraph>) textbox.getChildNodes(NodeType.PARAGRAPH, true))
        {
            checkNotMarked(paragraph);
            for (Run run : (Iterable<Run>) paragraph.getRuns())
                checkNotMarked(run);
        }

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how paragraph with few runs is deleted.
    /// Paragraph should be marked and all runs are marked as well.
    /// </summary>
    @Test
    public void testRemoveParagraphA() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveParagraphA.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(1);

        checkNotMarked(paragraph);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);
        paragraph.remove();

        // Paragraph itself marked.
        checkMarkedForDeletion(paragraph);

        // Each runs are marked too.
        for(Run run : (Iterable<Run>) paragraph.getRuns())
            checkMarkedForDeletion(run);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how paragraph with few runs and shape is deleted.
    /// Paragraph should be marked, runs should be marked and shape should be marked.
    /// </summary>
    @Test
    public void testRemoveParagraphB() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveParagraphB.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(1);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);
        paragraph.remove();

        checkMarkedForDeletion(paragraph);

        for (Run run : (Iterable<Run>) paragraph.getRuns())
            checkMarkedForDeletion(run);

        for (Shape shape : (Iterable<Shape>) paragraph.getChildNodes(NodeType.SHAPE, true))
            checkMarkedForDeletion(shape);

        TestUtil.save(doc, TEST_NAME);
    }


    /// <summary>
    /// Test that paragraph which inserted and removed during one edit session is really removed.
    /// Word does this way.
    /// </summary>
    @Test
    public void testRemoveParagraphC() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveParagraphC.docx";
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Para1");

        doc.startTrackRevisionsInternal("Author", G_TEST_DATE_TIME);

        builder.writeln("Para2");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(3, paras.getCount());

        checkNotMarked(paras.get(0));
        checkMarkedForInsertion(paras.get(1));

        for(Paragraph paragraph : (Iterable<Paragraph>) paras)
            paragraph.remove();

        msAssert.areEqual(2, paras.getCount());
        checkMarkedForDeletion(paras.get(0));

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how row is deleted.
    /// Row is marked deleted, every paragraph and run are marked as deleted.
    /// </summary>
    @Test
    public void testRemoveRow() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveRow.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        Row row = table.getRows().get(1);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);
        msAssert.areEqual(3, table.getRows().getCount());

        row.remove();

        msAssert.areEqual(3, table.getRows().getCount());
        checkMarkedForDeletion(row);

        // Verify that each paragraph and run in row is marked.
        for(Paragraph paragraph : (Iterable<Paragraph>) row.getChildNodes(NodeType.PARAGRAPH, true))
        {
            checkMarkedForDeletion(paragraph);
            for(Run run : (Iterable<Run>) paragraph.getRuns())
                checkMarkedForDeletion(run);
        }

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how last run is removed.
    /// This run is last not marked for deletion run in the last not marked for deletion paragraph.
    /// Run is marked for deletion but none parent paragraph and parent row are marked.
    /// </summary>
    @Test
    public void testRemoveLastRunA() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveLastRunA.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        Run run = (Run)table.getRows().get(1).getCells().get(1).getParagraphs().get(1).getLastChild();

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);
        run.remove();

        checkMarkedForDeletion(run);
        checkNotMarked(run.getParentParagraph());
        checkNotMarked(run.getParentParagraph().getParentRow());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Test how all runs are removed one by one from paragraph.
    /// All runs are marked eventually but parent paragraph is not.
    /// </summary>
    @Test
    public void testRemoveLastRunB() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveLastRunB.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual(7, paragraph.getRuns().getCount());

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);
        for (Run run : (Iterable<Run>) paragraph.getRuns())
            run.remove();

        // None of runs are actually removed.
        msAssert.areEqual(7, paragraph.getRuns().getCount());

        // But all are marked.
        for(Run run : (Iterable<Run>) paragraph.getRuns())
            checkMarkedForDeletion(run);

        // And paragraph is not.
        checkNotMarked(paragraph);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Test how last run is deleted.
    /// Single run in paragraph in cell is deleted. Run is marked, paragraph is NOT marked. Row is NOT marked.
    /// </summary>
    @Test
    public void testRemoveLastRunC() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveLastRunC.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Run run = (Run)doc.getChild(NodeType.RUN, 0, true);
        Paragraph paragraph = run.getParentParagraph();
        Row row = paragraph.getParentRow();

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);
        run.remove();

        checkMarkedForDeletion(run);
        checkNotMarked(paragraph);
        checkNotMarked(row);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how single paragraph in cell is deleted.
    /// Paragraph should be marked, child run should be marked, parent row should not be marked.
    /// </summary>
    @Test
    public void testRemoveLastParagraph() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveLastParagraph.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Run run = (Run)doc.getChild(NodeType.RUN, 0, true);
        Paragraph paragraph = run.getParentParagraph();
        Row row = paragraph.getParentRow();

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        paragraph.remove();

        checkMarkedForDeletion(run);
        checkMarkedForDeletion(paragraph);
        checkNotMarked(row);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how run insertion is marked.
    /// INserted run should be marked for insertion.
    /// </summary>
    @Test
    public void testInsertRun() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertRun.docx";
        Document doc = TestUtil.open(TEST_NAME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        builder.write("another test");

        Run run1 = (Run)doc.getChild(NodeType.RUN, 0, true);
        Run run2 = (Run)doc.getChild(NodeType.RUN, 1, true);
        Paragraph paragraph = run1.getParentParagraph();

        // Run was in document already.
        checkNotMarked(run1);

        // Parent paragraph is not marked.
        checkNotMarked(paragraph);

        // Inserted run.
        checkMarkedForInsertion(run2);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how paragraph is inserted. Insert paragraph using DocumentBuilder at the document end.
    /// </summary>
    @Test
    public void testInsertParagraphA() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertParagraphA.docx";
        Document doc = TestUtil.open(TEST_NAME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        builder.insertParagraph();

        // First paragraph (whose after new paragraph is inserted should be marked).
        checkMarkedForInsertion(doc.getFirstSection().getBody().getParagraphs().get(0));
        checkNotMarked(doc.getFirstSection().getBody().getParagraphs().get(1));

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how paragraph is inserted with DocumentBuilder.
    /// Insert paragraph with text using DocumentBuilder between existed paragraphs.
    /// </summary>
    @Test
    public void testInsertParagraphB() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertParagraphB.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(0, -1);

        builder.insertParagraph();
        builder.write("Para1.5");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // Only first (whose after was inserted new paragraph) is marked.
        checkMarkedForInsertion(paras.get(0));
        checkNotMarked(paras.get(1));
        checkNotMarked(paras.get(2));

        // Inserted Run is marked.
        checkMarkedForInsertion(paras.get(1).getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Paragraph is split in the middle.
    /// Actually it means that we insert paragraph break character.
    /// First paragraph is marked for insertion (in facts paragraph break character). Runs are not marked.
    /// </summary>
    @Test
    public void testInsertParagraphC() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertParagraphC.docx";
        Document doc = TestUtil.open(TEST_NAME);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        Body body = doc.getFirstSection().getBody();
        Paragraph paragraph = body.getFirstParagraph();

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveTo(paragraph.getRuns().get(1));

        builder.insertParagraph();

        msAssert.areEqual(2, body.getParagraphs().getCount());
        // Only first paragraph get InsertRevision.
        checkMarkedForInsertion(body.getParagraphs().get(0));
        checkNotMarked(body.getParagraphs().get(1));

        // None of run are marked.
        for (Run run : (Iterable<Run>) body.getChildNodes(NodeType.RUN, true))
            checkNotMarked(run);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how paragraph is inserted. Few paragraph copy-pasted from source document.
    /// Every paragraph except last has insertion. Every run has insertion. Shape has insertion.
    /// </summary>
    @Test
    public void testInsertParagraphD() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertParagraphD.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Document src = TestUtil.open("Model\\Revision\\TrackRevisions\\Source1.docx");

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        // import whole document.
        for (Paragraph p : (Iterable<Paragraph>) src.getFirstSection().getBody().getParagraphs())
            doc.getFirstSection().getBody().appendChild(doc.importNode(p, true));

        // Verify content is properly imported.
        for (Paragraph paragraph : (Iterable<Paragraph>) doc.getChildNodes(NodeType.PARAGRAPH, true))
        {
            if (!paragraph.isFirstChild())
            {
                checkMarkedForInsertion(paragraph);

                for (Run run : (Iterable<Run>) paragraph.getRuns())
                    checkMarkedForInsertion(run);

                for (Shape shape : (Iterable<Shape>) paragraph.getChildNodes(NodeType.SHAPE, true))
                    msAssert.isTrue(shape.getRunPr().hasInsertRevision());
            }
        }

        TestUtil.save(doc, TEST_NAME);
    }


    /// <summary>
    /// Tests how run inserted under tracking is deleted. Insert and delete during one session.
    /// Run should be really deleted.
    /// </summary>
    @Test
    public void testDeleteInsertedRunA() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestDeleteInsertedRunA.docx";
        Document doc = new Document();
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insertion text.
        builder.write("inserted text");

        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();

        // Verify that text was tracked.
        msAssert.areEqual(1, p.getRuns().getCount());
        checkMarkedForInsertion(p.getFirstRun());

        // And remove it immediately.
        p.getFirstRun().remove();

        // Verify that run was really removed because
        // both insertion and deletion were made during one edit session.
        msAssert.areEqual(0, p.getRuns().getCount());

        RevisionCollection revisions = new RevisionCollection(doc);
        msAssert.areEqual(0, revisions.getCount());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how run inserted under tracking is deleted. Insert and delete during different sessions.
    /// Run should be marked as deleted.
    /// </summary>
    @Test
    public void testDeleteInsertedRunB() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestDeleteInsertedRunB.docx";
        Document doc = new Document();
        doc.startTrackRevisionsInternal("John", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insertion text.
        builder.write("inserted text");

        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();

        // Verify that text was tracked.
        msAssert.areEqual(1, p.getRuns().getCount());
        checkMarkedForInsertion(p.getFirstRun());

        // Session is always restarted regardless Author name and DateTime.
        doc.startTrackRevisionsInternal("Tom", G_TEST_DATE_TIME);

        // And remove it in "another" session.
        p.getFirstRun().remove();

        // Verify that run is still here and marked for both insertion and deletion.
        msAssert.areEqual(1, p.getRuns().getCount());
        checkMarkedForInsertion(p.getFirstRun());
        checkMarkedForDeletion(p.getFirstRun());

        RevisionCollection revisions = new RevisionCollection(doc);
        msAssert.areEqual(2, revisions.getCount());
        msAssert.areEqual("John", revisions.get(0).getAuthor());
        msAssert.areEqual("Tom", revisions.get(1).getAuthor());

        TestUtil.save(doc, TEST_NAME);
    }



    /// <summary>
    /// Tests how run text changes are tracked. New value is empty string.
    /// </summary>
    @Test
    public void testChangeRunTextEmpty() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestChangeRunTextEmpty.docx";
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("Hello!");

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(1, para.getRuns().getCount());
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        Run run = para.getFirstRun();
        run.setText("");

        // Verify that new run for old text is created and marked for deletion while
        // this run is marked for insertion and has empty text.
        msAssert.areEqual(2, para.getRuns().getCount());

        // New text run.
        run = para.getRuns().get(0);
        msAssert.areEqual("", run.getText());
        checkMarkedForInsertion(run);

        // Old text run.
        run = para.getRuns().get(1);
        msAssert.areEqual("Hello!", run.getText());
        checkMarkedForDeletion(run);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how table insertion is tracked.
    /// Row is tracked, cell paragraph is tracked, run is tracked.
    /// </summary>
    @Test
    public void testInsertTable() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertTable.docx";
        Document doc = TestUtil.open(TEST_NAME);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.startTable();
        builder.insertCell();
        builder.write("Cell");
        builder.endRow();
        builder.endTable();

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        Row row = table.getFirstRow();
        checkMarkedForInsertion(row);

        Cell cell = row.getFirstCell();
        Paragraph p = cell.getFirstParagraph();
        checkMarkedForInsertion(p);
        checkMarkedForInsertion(p.getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests DocumentBuilder.WriteLn. It actually inserts paragraph and run at once.
    /// </summary>
    @Test
    public void testWriteLn() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestWriteLn.docx";
        Document doc = TestUtil.open(TEST_NAME);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(1, 0);
        builder.writeln("3");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(5, paras.getCount());

        // No revisions in para 0
        Paragraph p = paras.get(0);
        checkNotMarked(p);
        checkNotMarked(p.getFirstRun());

        // para 1 and child run are marked
        p = paras.get(1);
        checkMarkedForInsertion(p);
        checkMarkedForInsertion(p.getFirstRun());
        msAssert.areEqual("3", p.getFirstRun().getText());

        // No revisions in para 2 and all next
        p = paras.get(2);
        checkNotMarked(p);
        checkNotMarked(p.getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how document is built when TrackRevisions option is changed during construction.
    /// </summary>
    @Test
    public void testTrackRevisionStateChanged() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestTrackRevisionStateChanged.docx";
        Document doc = new Document();

        doc.getFirstSection().getBody().getLastParagraph().appendChild(new Run(doc, "FirstRun"));

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        doc.getFirstSection().getBody().appendChild(new Paragraph(doc));
        doc.getFirstSection().getBody().getLastParagraph().appendChild(new Run(doc, "SecondRun"));

        doc.stopTrackRevisions();

        doc.getFirstSection().getBody().appendChild(new Paragraph(doc));
        doc.getFirstSection().getBody().getLastParagraph().appendChild(new Run(doc, "ThirdRun"));

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(3, paras.getCount());

        Paragraph p = paras.get(0);
        checkNotMarked(p);
        checkNotMarked(p.getFirstRun());

        p = paras.get(1);
        checkMarkedForInsertion(p);
        checkMarkedForInsertion(p.getFirstRun());

        p = paras.get(2);
        checkNotMarked(p);
        checkNotMarked(p.getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Test insertBefore and appending paragraph with a run inside.
    /// First should be marked and second should not.
    /// </summary>
    @Test
    public void testInsertBefore() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertBefore.docx";
        Document doc = new Document();

        doc.getFirstSection().getBody().getLastParagraph().appendChild(new Run(doc, "FirstRun"));

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        Paragraph p = new Paragraph(doc);
        p.appendChild(new Run(doc, "ZeroRun"));

        doc.getFirstSection().getBody().insertBefore(p, doc.getFirstSection().getBody().getFirstParagraph());

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());

        p = paras.get(0);
        msAssert.areEqual("ZeroRun", p.getFirstRun().getText());
        checkMarkedForInsertion(p);
        checkMarkedForInsertion(p.getFirstRun());

        p = paras.get(1);
        checkNotMarked(p);
        checkNotMarked(p.getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how header tracked.
    /// </summary>
    @Test
    public void testWriteLnInHeader() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestWriteLnInHeader.docx";
        Document doc = TestUtil.open(TEST_NAME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.writeln("WriteLn");

        // Verify that no marked insertion are made.
        for (Paragraph paragraph : (Iterable<Paragraph>) doc.getChildNodes(NodeType.PARAGRAPH, true))
        {
            checkNotMarked(paragraph);
            for (Run run : (Iterable<Run>) paragraph.getRuns())
                checkNotMarked(run);
        }

        doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        builder = new DocumentBuilder(doc);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.writeln("WriteLn");

        // Asserts that every paragraphs and runs in header has insert revision
        for (Paragraph paragraph : (Iterable<Paragraph>) doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getParagraphs())
        {
            checkMarkedForInsertion(paragraph);
            for (Run run : (Iterable<Run>) paragraph.getRuns())
                checkMarkedForInsertion(run);
        }

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Test what's going on when we delete run already marked for deletion.
    /// Run should be marked once again with new user.
    /// </summary>
    @Test
    public void testDeleteAlreadyDeleted() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestDeleteAlreadyDeleted.docx";
        Document doc = TestUtil.open(TEST_NAME);

        RevisionCollection revisions = new RevisionCollection(doc);

        // Both run are marked for deletion.
        msAssert.areEqual(2, revisions.getCount());
        msAssert.areEqual("user", revisions.get(0).getAuthor());
        msAssert.areEqual("user", revisions.get(1).getAuthor());

        doc.startTrackRevisionsInternal("William", G_TEST_DATE_TIME);

        doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().remove();

        // Assert that there are still two revisions but first is new one.
        msAssert.areEqual(2, revisions.getCount());
        msAssert.areEqual("William", revisions.get(0).getAuthor());
        msAssert.areEqual("user", revisions.get(1).getAuthor());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how footnote is inserted.
    /// </summary>
    @Test
    public void testInsertFootnote() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertFootnote.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToDocumentEnd();
        builder.insertFootnote(FootnoteType.FOOTNOTE, "Inserted footnote.");

        Footnote footnote = (Footnote)doc.getChild(NodeType.FOOTNOTE, 0, true);
        msAssert.isTrue(footnote.getRunPr().hasInsertRevision());

        Paragraph p = footnote.getFirstParagraph();
        checkMarkedForInsertion(p.getRuns().get(0));
        checkMarkedForInsertion(p.getRuns().get(1));

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how multi line footnote is inserted.
    /// All runs and paragraphs inside footnote should be marked.
    /// </summary>
    @Test
    public void testInsertMultilineFootnote() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertMultilineFootnote.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();
        builder.insertFootnote(FootnoteType.FOOTNOTE, "Para1\nPara2");

        Footnote footnote = (Footnote)doc.getChild(NodeType.FOOTNOTE, 0, true);
        msAssert.isTrue(footnote.getRunPr().hasInsertRevision());
        for(Paragraph paragraph : (Iterable<Paragraph>) footnote.getParagraphs())
        {
            checkMarkedForInsertion(paragraph);
            for(Run run : (Iterable<Run>) paragraph.getRuns())
                checkMarkedForInsertion(run);
        }

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how bookmark is inserted.
    /// Run should be marked for insertion.
    /// </summary>
    @Test
    public void testInsertBookmark() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertBookmark.docx";
        Document doc = TestUtil.open(TEST_NAME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();
        builder.insertParagraph();

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        builder.startBookmark("Test");
        builder.write("BOOKMARK");
        builder.endBookmark("Test");

        Paragraph paragraph = doc.getFirstSection().getBody().getLastParagraph();
        checkNotMarked(paragraph);
        checkMarkedForInsertion(paragraph.getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how DocumentBuilder.InsertBreak(BreakType.ColumnBreak) works under tracking.
    /// Column break character should be marked.
    /// </summary>
    @Test
    public void testInsertColumnBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertColumnBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(1, 0);

        builder.insertBreak(BreakType.COLUMN_BREAK);

        Paragraph p = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(2, p.getRuns().getCount());
        // First run (column break character itself) is marked.
        msAssert.areEqual("\u000e", p.getRuns().get(0).getText());
        checkMarkedForInsertion(p.getRuns().get(0));

        // Second is not.
        checkNotMarked(p.getRuns().get(1));

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how DocumentBuilder.InsertBreak(BreakType.PageBreak) works under tracking.
    /// PageBreak character should be tracked.
    /// </summary>
    @Test
    public void testInsertPageBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertPageBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(1, 0);

        builder.insertBreak(BreakType.PAGE_BREAK);

        Paragraph p = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(2, p.getRuns().getCount());

        // First run (page break character itself) is marked.
        checkMarkedForInsertion(p.getRuns().get(0));

        // Second is not.
        checkNotMarked(p.getRuns().get(1));

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how DocumentBuilder.InsertBreak(BreakType.LineBreak) works under tracking.
    /// </summary>
    @Test
    public void testInsertLineBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertLineBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(1, 0);

        builder.insertBreak(BreakType.LINE_BREAK);

        Paragraph p = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(2, p.getRuns().getCount());

        // First run (page break character itself) is marked.
        checkMarkedForInsertion(p.getRuns().get(0));

        // Second is not.
        checkNotMarked(p.getRuns().get(1));

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how DocumentBuilder.InsertBreak(BreakType.SectionBreak) works under tracking.
    /// Last paragraph in section should be marked. New section runs and paragraphs should not.
    /// </summary>
    @Test
    public void testInsertSectionBreakA() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertSectionBreakA.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(1, 0);

        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);

        // Verify that new section is created.
        msAssert.areEqual(2, doc.getSections().getCount());

        // Had problem with DeleteRevision. Verify it absent.
        for (Paragraph paragraph : (Iterable<Paragraph>) doc.getChildNodes(NodeType.PARAGRAPH, true))
        {
            checkNotMarkedForDeletion(paragraph);
            for(Run run : (Iterable<Run>) paragraph.getRuns())
                checkNotMarkedForDeletion(run);
        }

        // This is "section break" paragraph.
        Paragraph p = doc.getFirstSection().getBody().getParagraphs().get(1);
        // Has no child.
        msAssert.areEqual(0, p.getChildNodes(NodeType.ANY, false).getCount());
        checkMarkedForInsertion(p);

        // This (old content moved to new section) should not be marked.
        p = doc.getSections().get(1).getBody().getFirstParagraph();
        checkNotMarked(p);
        msAssert.areEqual("After", p.getRuns().get(0).getText());
        checkNotMarked(p.getRuns().get(0));

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests DocumentBuilder.DeleteRow.
    /// Row should be marked. Every paragraphs and runs within row should be marked.
    /// </summary>
    @Test
    public void testRemoveRowA() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveRowA.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.deleteRow(0, 1);

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        // Deleted row
        Row row = table.getRows().get(1);
        checkMarkedForDeletion(row);

        for(Paragraph paragraph : (Iterable<Paragraph>) row.getChildNodes(NodeType.PARAGRAPH, true))
        {
            checkMarkedForDeletion(paragraph);
            for(Run run : (Iterable<Run>) paragraph.getRuns())
                checkMarkedForDeletion(run);
        }

        TestUtil.save(doc, TEST_NAME);
    }


    /// <summary>
    /// Tests how section is removed.
    /// We have no analogue to remove section break because we have no section break at all, we have sections instead.
    /// So I think we should mark whole section content as deleted.
    /// </summary>
    @Test
    public void testDeleteSectionBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestDeleteSectionBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        doc.getSections().get(1).remove();

        // Verify that there is still two sections.
        msAssert.areEqual(2, doc.getSections().getCount());

        Paragraph paragraph = doc.getSections().get(1).getBody().getFirstParagraph();
        checkMarkedForDeletion(paragraph);
        checkMarkedForDeletion(paragraph.getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how StructuredDocumentTag is inserted.
    /// </summary>
    @Test
    public void testInsertSdt() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestDeleteSectionBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, MarkupLevel.BLOCK);
        sdt.updateId();
        Paragraph p = sdt.appendChild(new Paragraph(doc));
        p.appendChild(new Run(doc, "Sample text"));
        p.appendChild(new Run(doc, "."));
        doc.getFirstSection().getBody().appendChild(sdt);

        GlossaryDocument glossaryDocument = new GlossaryDocument();
        doc.setGlossaryDocument(glossaryDocument);

        BuildingBlock buildingBlock = new BuildingBlock(doc.getGlossaryDocument());
        buildingBlock.appendChild(new Section(doc.getGlossaryDocument()));
        buildingBlock.getFirstSection().appendChild(new Body(doc.getGlossaryDocument()));
        buildingBlock.getFirstSection().getBody().appendChild(new Paragraph(doc.getGlossaryDocument()));
        buildingBlock.getFirstSection().getBody().getFirstParagraph().appendChild(new Run(doc.getGlossaryDocument(), "Click here to enter text."));

        doc.getGlossaryDocument().appendChild(buildingBlock);

        // Verify that runs in glossary document is not marked.
        for(Run run : (Iterable<Run>) glossaryDocument.getChildNodes(NodeType.RUN, true))
            checkNotMarked(run);

        // Verify that all runs in SDT marked.
        for (Run run : (Iterable<Run>) sdt.getChildNodes(NodeType.RUN, true))
            checkMarkedForInsertion(run);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Load document with TrackRevision flag test.
    /// During load process some components may do node changing which caused tracking saved.
    /// Verify that no revisions are made during document loading process.
    /// </summary>
    @Test
    public void testLoadDocumentA() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestLoadDocumentA.docx";
        Document doc = TestUtil.open(TEST_NAME);

        msAssert.isTrue(doc.getTrackRevisions());
        checkNotMarked(doc.getFirstSection().getBody().getParagraphs());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests that EditSession class information data written into revisions.
    /// </summary>
    @Test
    public void testEditSessionA() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestEditSessionA.docx";
        Document doc = new Document();

        // Build paragraphs with some runs and shape.
        Paragraph p = new Paragraph(doc);
        p.appendChild(new Run(doc, "First run"));
        p.appendChild(new Run(doc, "Second run"));

        DateTime dateTime = new DateTime(2014, 2, 2);
        doc.startTrackRevisionsInternal("amorozov", dateTime);

        doc.getFirstSection().getBody().appendChild(p);

        checkMarkedForInsertion(p);
        for (Run run : (Iterable<Run>) p.getRuns())
            checkMarkedForInsertion(run);

        RevisionCollection revisions = new RevisionCollection(doc);

        msAssert.areEqual(3, revisions.getCount());
        for(Revision revision : revisions)
        {
            msAssert.areEqual("amorozov", revision.getAuthor());
            msAssert.areEqual(dateTime, revision.getDateTimeInternal());
        }

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests DrawingML insertion.
    /// DrawingML.RunPr should be marked for insertion.
    /// </summary>
    @Test
    public void testInsertDrawingML() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertDrawingML.docx";
        Document dst = new Document();
        Document src = TestUtil.open(TEST_NAME);

        dst.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        dst.appendDocument(src, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        Shape dml = (Shape)dst.getChild(NodeType.SHAPE, 0, true);
        checkMarkedForInsertion(dml);

        TestUtil.save(dst, TEST_NAME);
    }

    /// <summary>
    /// Tests how update fields tracked. It seems it's very hard to track field update.
    /// I think will can do limitation in the first version and do not track UpdateFields.
    /// </summary>
    @Test
    public void testUpdateField() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestUpdateField.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        msAssert.areEqual("Test title", doc.getBuiltInDocumentProperties().getTitle());
        doc.getBuiltInDocumentProperties().setTitle("Updated title");

        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(2, p.getRuns().getCount());       // field code and field result

        doc.updateFields();

        msAssert.areEqual(2, p.getRuns().getCount());       // field code and field result

        checkNotMarked(p);
        for(Run run : (Iterable<Run>) p.getRuns())
            checkNotMarked(run);

        TestUtil.save(doc, TEST_NAME);
    }

    // FOSS: TestLoadDocumentB removed — it verified that a binary-.doc SHAPE-field transformation on load
    // produces no spurious insert/delete revisions. Converting the .doc input to .docx (in Word) bakes in a
    // real insert revision, so the .doc-specific scenario no longer holds.

    /// <summary>
    /// Tests how table is deleted.
    /// Every row should be marked. Every paragraph and every run should be marked.
    /// </summary>
    @Test
    public void testDeleteTable() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestDeleteTable.docx";
        Document doc = TestUtil.open(TEST_NAME);
        doc.startTrackRevisionsInternal("user", new DateTime(2014, 7, 4, 14, 19, 0));

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        table.remove();

        // Table is still here.
        msAssert.isNotNull(table.getDocument());

        for (Row row : (Iterable<Row>) table.getRows())
        {
            checkMarkedForDeletion(row);
            for(Cell cell : (Iterable<Cell>) row.getCells())
            {
                for (Paragraph paragraph : (Iterable<Paragraph>) cell.getParagraphs())
                {
                    checkMarkedForDeletion(paragraph);
                    for(Run run : (Iterable<Run>) paragraph.getRuns())
                        checkMarkedForDeletion(run);
                }
            }
        }

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how cell is deleted.
    /// Word doesn't track cell deletion  so cell should be deleted.
    /// </summary>
    @Test
    public void testDeleteCell() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestDeleteCell.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);

        msAssert.areEqual(6, table.getFirstRow().getCells().getCount());

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);
        table.getFirstRow().getCells().get(2).remove();

        msAssert.areEqual(5, table.getFirstRow().getCells().getCount());

        // Verify no revisions marks.
        checkNotMarked(doc.getFirstSection().getBody().getParagraphs());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how cell is inserted.
    /// Paragraph inside cell is marked.
    /// </summary>
    @Test
    public void testInsertCell() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertCell.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);

        msAssert.areEqual(6, table.getFirstRow().getCells().getCount());

        Cell refCell = table.getFirstRow().getCells().get(1);

        // Construct new cell.
        Cell newCell = new Cell(doc);
        newCell.appendChild(new Paragraph(doc));
        newCell.getLastParagraph().appendChild(new Run(doc, "New Cell"));

        newCell.appendChild(new Paragraph(doc));
        newCell.getLastParagraph().appendChild(new Run(doc, "inserted by DocumentBuilder"));

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        table.getFirstRow().insertBefore(newCell, refCell);

        msAssert.areEqual(7, table.getFirstRow().getCells().getCount());

        checkMarkedForInsertion(newCell.getParagraphs().get(0));
        checkMarkedForInsertion(newCell.getParagraphs().get(0).getFirstRun());

        checkMarkedForInsertion(newCell.getParagraphs().get(1));
        checkMarkedForInsertion(newCell.getParagraphs().get(1).getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how Document.RemoveAllChildren works. Document has Comments.
    /// Paragraphs and runs within comments should not be marked. Other should be marked.
    /// </summary>
    @Test
    public void testRemoveAllChildren() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveAllChildren.docx";
        Document doc = TestUtil.open(TEST_NAME);

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        doc.removeAllChildren();

        // All content except Comment is marked.
        for (Paragraph paragraph : (Iterable<Paragraph>) doc.getFirstSection().getBody().getParagraphs())
        {
            checkMarkedForDeletion(paragraph);

            for(Run run : (Iterable<Run>) paragraph.getRuns())
                checkMarkedForDeletion(run);
        }

        // Comment content is not marked and not deleted in this case.
        Comment comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);
        for (Paragraph paragraph : (Iterable<Paragraph>) comment.getParagraphs())
        {
            checkNotMarked(paragraph);

            for (Run run : (Iterable<Run>) paragraph.getRuns())
                checkNotMarked(run);
        }

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how SDT is updated when document is saved to format where SDT is unsupported.
    /// </summary>
    @Test
    public void testUpdateSdt() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira9999.docx");
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        // Let SDT updater work.
        doc.save(new MemoryStream(), SaveFormat.DOCX);   // FOSS: was Doc

        checkNotMarked(doc.getFirstSection().getBody().getParagraphs());
    }

    /// <summary>
    /// Tests how DocumentBuilder works under tracking.
    /// </summary>
    @Test
    public void testDocumentBuilderA() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestDocumentBuilderA.docx";

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("TrackRevision test.");

        builder.startTable();
        builder.insertCell();
        builder.writeln("Cell 1");

        builder.insertCell();
        builder.writeln("Cell 3");

        doc.startTrackRevisionsInternal("Alexey Morozov", G_TEST_DATE_TIME);

        Cell cell = builder.getCurrentParagraph().getParentCell();
        cell.removeAllChildren();
        builder.writeln("Cell 2");

        builder.endRow();

        builder.insertCell();
        builder.writeln("Second row first cell");
        builder.insertCell();
        builder.writeln("Second row second cell");
        builder.endRow();

        builder.endTable();

        builder.moveToDocumentStart();
        builder.getCurrentParagraph().remove();
        builder.writeln("This is test document to see how DocumentBuilder works when TrackRevision option is turned on.");

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        // First row is not tracked.
        checkNotMarked(table.getRows().get(0));
        // Second row was tracked.
        checkMarkedForInsertion(table.getRows().get(1));

        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        checkMarkedForInsertion(paragraph);

        cell = table.getRows().get(0).getCells().get(1);
        // Text was changed in this cell.
        msAssert.areEqual(3, cell.getParagraphs().getCount());
        paragraph = cell.getParagraphs().get(0);
        msAssert.areEqual("Cell 3", paragraph.getFirstRun().getText());
        checkMarkedForDeletion(paragraph);
        checkMarkedForDeletion(paragraph.getFirstRun());
        checkNotMarkedForInsertion(paragraph);

        paragraph = cell.getParagraphs().get(1);
        msAssert.areEqual("Cell 2", paragraph.getFirstRun().getText());
        checkMarkedForInsertion(paragraph);
        checkMarkedForInsertion(paragraph.getFirstRun());

        TestUtil.save(doc, TEST_NAME);

        doc.acceptAllRevisions();
        msAssert.isTrue(doc.getFirstSection().getBody().getFirstParagraph().getText().startsWith("This is test"));
        table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual(2, table.getRows().getCount());
    }

    /// <summary>
    /// Tests revision made by few authors.
    /// </summary>
    @Test
    public void testDocumentBuilderB() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestDocumentBuilderB.docx";

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        doc.startTrackRevisionsInternal("Guildenstern", G_TEST_DATE_TIME);
        builder.writeln("How long have you suffered from a bad memory?");
        doc.startTrackRevisionsInternal("Rosencrantz", G_TEST_DATE_TIME);
        builder.writeln("I can't remember.");

        doc.startTrackRevisionsInternal("Tom", G_TEST_DATE_TIME);
        builder.writeln("You're both dead!");
        doc.removeAllChildren();

        doc.startTrackRevisionsInternal("Guildenstern", G_TEST_DATE_TIME);
        builder.writeln(" What are you going to do now?");

        TestUtil.save(doc, TEST_NAME);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // First was inserted by Guildenstern and then deleted by Tom.
        Paragraph p = paras.get(0);
        checkMarkedForInsertion(p);
        checkMarkedForInsertion(p.getFirstRun());
        checkMarkedForDeletion(p);
        checkMarkedForDeletion(p.getFirstRun());

        // Second was inserted by Rosencrantz and then deleted by Tom.
        p = paras.get(1);
        checkMarkedForInsertion(p);
        checkMarkedForInsertion(p.getFirstRun());
        checkMarkedForDeletion(p);
        checkMarkedForDeletion(p.getFirstRun());

        // There should be "You're both dead!" paragraph inserted by Tom but because it was deleted
        // during the same session it was really deleted.
        msAssert.isFalse(doc.getText().contains("You're both dead!"));

        // Third was last paragraph and marked for deletion during Tom's Document.RemoveAll.
        p = paras.get(2);
        checkMarkedForDeletion(p);

        // But Guildenstern made new insertion to it.
        checkMarkedForInsertion(p);
        checkMarkedForInsertion(p.getFirstRun());
        checkMarkedForDeletion(p);
        checkNotMarkedForDeletion(p.getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests that tracking is enabled for FindReplace. Full run replacement.
    /// </summary>
    @Test (dataProvider = "testFindReplaceADataProvider")
    public void testFindReplaceA(boolean trackRevisions) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("amorozov");
        builder.writeln("amorozov");

        if(trackRevisions)
            doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        FindReplaceOptions options = new FindReplaceOptions();
        {
            options.setMatchCase(true);
            options.setFindWholeWordsOnly(true);
        }
        FindReplace findReplace = new FindReplace(doc, "amorozov", "morzal", options);

        findReplace.replace();

        // First two paragraphs should be the same.
        for(int i = 0; i < 2; i++)
        {
            Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(i);

            checkNotMarked(para);

            if (trackRevisions)
            {
                msAssert.areEqual(2, para.getRuns().getCount());
                msAssert.areEqual("amorozov", para.getRuns().get(0).getText());
                checkMarkedOnlyForDeletion(para.getRuns().get(0));

                msAssert.areEqual("morzal", para.getRuns().get(1).getText());
                checkMarkedOnlyForInsertion(para.getRuns().get(1));
            }
            else
            {
                msAssert.areEqual(1, para.getRuns().getCount());
                msAssert.areEqual("morzal", para.getRuns().get(0).getText());
                checkNotMarked(para.getRuns().get(0));
            }
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFindReplaceADataProvider")
	public static Object[][] testFindReplaceADataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// Tests that tracking is enabled for FindReplace. Part of run replacement.
    /// </summary>
    @Test
    public void testFindReplaceB() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestFindReplaceB.docx";
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("amorozov");
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(1, paragraph.getRuns().getCount());

        FindReplaceOptions options = new FindReplaceOptions();
        {
            options.setMatchCase(true);
            options.setFindWholeWordsOnly(false);
        }
        FindReplace findReplace = new FindReplace(doc, "o", "0", options);
        int replacementCount = findReplace.replace();
        msAssert.areEqual(3, replacementCount);

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        checkNotMarked(para);

        RunCollection runs = para.getRuns();
        // "am"
        checkNotMarked(runs.get(0));

        // "o" - deleted, "0" inserted
        checkMarkedOnlyForDeletion(runs.get(1));
        checkMarkedOnlyForInsertion(runs.get(2));

        // "r"
        checkNotMarked(runs.get(3));

        // "o" - deleted, "0" inserted
        checkMarkedOnlyForDeletion(runs.get(4));
        checkMarkedOnlyForInsertion(runs.get(5));

        // "z".
        checkNotMarked(runs.get(6));

        // "o" - deleted, "0" inserted
        checkMarkedOnlyForDeletion(runs.get(7));
        checkMarkedOnlyForInsertion(runs.get(8));

        // "v".
        checkNotMarked(runs.get(9));
        msAssert.areEqual(10, runs.getCount());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how Document.JoinRunsWithSameFormatting is tracked.
    /// </summary>
    @Test
    public void testJoinRunWithSameFormatting() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestJoinRunsWithSameFormatting.docx";

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("1");
        builder.write("2");
        builder.write("3");
        builder.write("4");

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);

        doc.joinRunsWithSameFormatting();

        RunCollection runs = doc.getFirstSection().getBody().getFirstParagraph().getRuns();

        checkMarkedForDeletion(runs.get(0));
        checkMarkedForDeletion(runs.get(1));
        checkMarkedForDeletion(runs.get(2));

        // JoinRunsWithSameFormatting method collects runs into last run.
        // When text of last run is changed new run before last is inserted.
        checkMarkedForInsertion(runs.get(3));

        checkMarkedForDeletion(runs.get(4));

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how node insertion is tracked if TrackRevision turned on late.
    /// Row should be marked, paragraphs and run should be marked as well.
    /// </summary>
    @Test
    public void testInsertCompositeNodeTable() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestInsertCompositeNodeTable.docx";
        Document doc = new Document();

        Table table = new Table(doc);
        table.appendChild(new Row(doc));

        Row firstRow = table.getFirstRow();
        firstRow.appendChild(new Cell(doc));
        firstRow.getFirstCell().appendChild(new Paragraph(doc));
        firstRow.getFirstCell().getFirstParagraph().appendChild(new Run(doc, "Test run"));

        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);
        doc.getFirstSection().getBody().appendChild(table);

        checkMarkedForInsertion(firstRow);
        checkMarkedForInsertion(firstRow.getFirstCell().getFirstParagraph());
        checkMarkedForInsertion(firstRow.getFirstCell().getFirstParagraph().getFirstRun());

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how nested table is deleted when we delete outer table.
    /// Inner table should be marked.
    /// </summary>
    @Test
    public void testRemoveNestedTables() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveNestedTables.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Table outerTable = (Table)doc.getChild(NodeType.TABLE, 0, true);
        Table innerTable = (Table)doc.getChild(NodeType.TABLE, 1, true);

        doc.startTrackRevisionsInternal("Alexey Morozov", G_TEST_DATE_TIME);

        outerTable.remove();

        for(Row row : (Iterable<Row>) innerTable.getRows())
            checkMarkedForDeletion(row);

        TestUtil.save(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how inserted run is deleted when user changes.
    /// Run should not be deleted and should be marked instead.
    /// </summary>
    @Test
    public void testRemoveInsertedChildNewRsid() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveInsertedChild.docx";
        Document doc = TestUtil.open(TEST_NAME);

        doc.startTrackRevisionsInternal("Will", G_TEST_DATE_TIME);

        Paragraph p = doc.getFirstSection().getBody().getParagraphs().get(1);
        p.appendChild(new Run(doc, "+Run"));

        doc.startTrackRevisionsInternal("Edgar", G_TEST_DATE_TIME);
        p.remove();

        // Verify that there are still two runs.
        msAssert.areEqual(2, p.getRuns().getCount());

        // Paragraph is marked for deletion.
        checkMarkedForDeletion(p);

        // Both runs are also marked for deletion.
        checkMarkedForDeletion(p.getRuns().get(0));
        checkMarkedForDeletion(p.getRuns().get(1));

        // But last run is still marked for insertion.
        checkMarkedForInsertion(p.getRuns().get(1));
    }

    /// <summary>
    /// Tests how inserted run is deleted when user is not changed.
    /// Run should be really deleted.
    /// </summary>
    @Test
    public void testRemoveInsertedChildSameRsid() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestRemoveInsertedChild.docx";
        Document doc = TestUtil.open(TEST_NAME);

        doc.startTrackRevisionsInternal("Will", G_TEST_DATE_TIME);

        // Paragraph already has one run.
        Paragraph p = doc.getFirstSection().getBody().getParagraphs().get(1);

        // Add new run.
        p.appendChild(new Run(doc, "+Run"));
        checkNotMarked(p.getRuns().get(0));
        checkMarkedForInsertion(p.getRuns().get(1));

        // Run is inserted and deleted during one session so it should be really deleted.
        p.remove();

        // Verify that there is only one run remains.
        msAssert.areEqual(1, p.getRuns().getCount());

        // Paragraph and first run are marked for deletion.
        checkMarkedForDeletion(p);
        checkMarkedForDeletion(p.getRuns().get(0));
    }


    /// <summary>
    /// Tests how inserted runs are deleted when user is changed.
    /// Run inserted by first author is really deleted other marked for deletion.
    /// </summary>
    @Test
    public void testChangeAuthor2() throws Exception
    {
        final String TEST_NAME = "Model\\Revision\\TrackRevisions\\TestChangeAuthor2.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(1);
        RunCollection runs = para.getRuns();

        msAssert.areEqual(3, runs.getCount());
        msAssert.areEqual("WillTomErich\r", para.getText());

        checkMarkedForInsertion(runs.get(0), "Will");
        checkMarkedForInsertion(runs.get(1), "Tom");
        checkMarkedForInsertion(runs.get(2), "Erich");

        doc.startTrackRevisionsInternal("Will", G_TEST_DATE_TIME);
        para.remove();

        // First run is really deleted while others two remains in document.
        msAssert.areEqual(2, runs.getCount());
        msAssert.areEqual("TomErich\r", para.getText());

        checkMarkedForInsertion(para.getRuns().get(0), "Tom");
        checkMarkedForDeletion(para.getRuns().get(0), "Will");

        checkMarkedForInsertion(para.getRuns().get(1), "Erich");
        checkMarkedForDeletion(para.getRuns().get(1), "Will");

        TestUtil.save(doc, TEST_NAME);
    }


    /// <summary>
    /// Tests how DocumentBuilder.InsertTableOfContents is tracked.
    /// Tracking should be locked for this operation.
    /// </summary>
    @Test
    public void testInsertToc() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestInsertTOC.docx");
        doc.startTrackRevisionsInternal("aw", G_TEST_DATE_TIME);
        DocumentBuilder builder = new DocumentBuilder(doc);
        Field field = builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");

        for (Node inline : field.getFieldRange())
            checkMarkedForInsertion((Inline)inline);
    }

    /// <summary>
    /// Tests that cloned document continues tracking with original author and date time.
    /// </summary>
    @Test
    public void testClone() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        doc.startTrackRevisionsInternal("amorozov", G_TEST_DATE_TIME);

        builder.write("Run1");

        Document clone = doc.deepClone();

        // Change author in the original document.
        doc.getEditSession().setAuthor("ddarkin");

        builder = new DocumentBuilder(clone);
        builder.moveToDocumentEnd();
        builder.write("Run2");

        // Both runs are marked by original author.
        RunCollection runs = clone.getFirstSection().getBody().getFirstParagraph().getRuns();
        checkMarkedForInsertion(runs.get(0), "amorozov");
        checkMarkedForInsertion(runs.get(1), "amorozov");
    }

    /// <summary>
    /// WORDSNET-11695 Track Revisions set incorrect author name
    /// Improved Run.Text setter to get more clear behavior.
    /// </summary>
    @Test
    public void testJira11695() throws Exception
    {
        Document doc = new Document();

        DocumentBuilder builder2 = new DocumentBuilder(doc);
        Paragraph para = builder2.insertParagraph();

        Run run = new Run(doc);
        para.appendChild(run);

        doc.startTrackRevisions("Borris");      //first author inserted
        run.setText("");
        run.setText("BORRISSSSS");

        doc.startTrackRevisions("Bonafort");    //second author deleted
        run.remove();

        doc.stopTrackRevisions();

        para = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(1, para.getRuns().getCount());

        run = para.getFirstRun();
        msAssert.areEqual("BORRISSSSS", run.getText());
        checkMarkedForInsertion(run, "Borris");
        checkMarkedForDeletion(run, "Bonafort");
    }


    /// <summary>
    /// WORDSNET-20666 Implemented generation of move revisions on moving nodes when tracking revisions is ON.
    /// </summary>
    @Test
    public void testMoveRevisionGeneration() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        body.removeAllChildren();

        // Check that all kind of node moving works. Check moving to a position before and after a reference node;
        // check node moving in the forward and backward directions.

        // Generate paragraphs.
        for (int i = 1; i <= 4; i++)
        {
            body.appendParagraph(msString.format("Paragraph before moved nodes {0}.", i));
            body.appendParagraph(msString.format("Paragraph after moved nodes {0}.", i));
            body.appendParagraph(msString.format("Move range start paragraph {0}.", i));
            body.appendChild(new BookmarkStart(doc, msString.format("Bookmark{0}.", i)));
            body.appendChild(new BookmarkEnd(doc, msString.format("Bookmark{0}.", i)));
            body.appendParagraph(msString.format("Move range end paragraph {0}.", i));
        }

        doc.startTrackRevisionsInternal("Author", new DateTime(2020, 11, 11, 21, 16, 0));

        Paragraph[] paragraphs = body.getParagraphs().toArray();

        // Insert before a reference node in the forward direction.
        body.insertBefore(paragraphs[2], paragraphs[4], paragraphs[1]);

        // Insert after a reference node in the forward direction.
        body.insertAfter(paragraphs[6], paragraphs[8], paragraphs[4]);

        // Insert before a reference node in the backward direction.
        Node referenceNode = paragraphs[9];
        Node movingNode = paragraphs[11];
        for (int i = 0; i < 4; i++)
        {
            Node prevNode = movingNode.getPreviousSibling();
            referenceNode = body.insertBefore(movingNode, referenceNode);
            movingNode = prevNode;
        }

        // Insert after a reference node in the backward direction.
        referenceNode = paragraphs[12];
        movingNode = paragraphs[15];
        for (int i = 0; i < 4; i++)
        {
            Node prevNode = movingNode.getPreviousSibling();
            body.insertAfter(movingNode, referenceNode);
            movingNode = prevNode;
        }

        TestUtil.save(doc, "Model\\Revision\\TrackRevisions\\TestMoveRevisionGeneration.docx", null, true,
            GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-20666 Tests generating move revisions for a table/row/cell.
    /// </summary>
    @Test
    public void testTableMoveRevisions() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Body body = doc.getFirstSection().getBody();

        Table table1 = builder.startTable();
        for (int i = 1; i <= 5; i++)
        {
            for (int j = 1; j <= 4; j++)
            {
                builder.insertCell();
                builder.write(msString.format("Cell {0}:{1}", i, j));
            }
            builder.endRow();
        }
        builder.endTable();

        builder.writeln("Paragraph 1");
        builder.writeln("Paragraph 2");
        builder.writeln("Paragraph 3");

        Table table2 = builder.startTable();
        builder.insertCell();
        builder.write("Cell 1");
        builder.insertCell();
        builder.write("Cell 2");
        builder.endTable();

        Node paragraph3 = table2.getPreviousSibling();
        Node paragraph2 = paragraph3.getPreviousSibling();

        doc.startTrackRevisionsInternal("Author", new DateTime(2020, 11, 26, 15, 56, 0));

        RowCollection rows = table1.getRows();
        CellCollection cells = rows.get(0).getCells();
        rows.get(0).insertBefore(cells.get(2), null, cells.get(1));

        table1.insertAfter(rows.get(3), null, rows.get(1));

        body.insertBefore(paragraph3, table2.getNextSibling(), paragraph2);

        TestUtil.save(doc, "Model\\Revision\\TrackRevisions\\TestTableMoveRevisions.docx", null, true,
            GoldLevel.EXPORT_ONLY);

        Document clone = doc.deepClone();
        doc.getRevisions().acceptAll();
        TestUtil.save(doc, "Model\\Revision\\TrackRevisions\\TestTableMoveRevisionsAccepted.docx", null, true,
            GoldLevel.EXPORT_ONLY);

        clone.getRevisions().rejectAll();
        TestUtil.save(clone, "Model\\Revision\\TrackRevisions\\TestTableMoveRevisionsRejected.docx", null, true,
            GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-20666 Tests generation of move revisions for SDT, field, Office math, shape, footnote and
    /// other nodes.
    /// </summary>
    @Test
    public void testOtherNodeMoveRevisions() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TrackRevisions\\TestOtherNodeMoveRevisions.docx");
        Body body = doc.getFirstSection().getBody();
        ParagraphCollection paragraphs = body.getParagraphs();

        doc.startTrackRevisionsInternal("Author", new DateTime(2020, 11, 26, 15, 56, 0));

        body.insertAfter(paragraphs.get(2), body.getChildNodes(NodeType.ANY, false).get(body.getChildNodes(NodeType.ANY, false).getCount() - 1), paragraphs.get(0));

        TestUtil.save(doc, "Model\\Revision\\TrackRevisions\\TestOtherNodeMoveRevisions.docx", null, true,
            GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-20666 Tests generating move revisions when a section is moved.
    /// </summary>
    @Test
    public void testSectionMoving() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (int i = 1; i <= 16; i++)
        {
            // Generate a section with two paragraphs.
            Body body = doc.getLastSection().getBody();
            builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
            body.getParagraphs().get(0).appendChild(new Run(doc, "Section " + i));
            body.appendChild(new Paragraph(doc));
        }

        doc.startTrackRevisionsInternal("Author", new DateTime(2020, 11, 27, 19, 40, 0));

        Section[] sections = doc.getSections().toArray();

        // Insert before a reference node in the forward direction.
        doc.insertBefore(sections[2], sections[4], sections[1]);

        // Insert after a reference node in the forward direction.
        doc.insertAfter(sections[6], sections[8], sections[4]);

        // Insert before a reference node in the backward direction.
        Node referenceNode = sections[9];
        Node movingNode = sections[11];
        for (int i = 0; i < 2; i++)
        {
            Node prevNode = movingNode.getPreviousSibling();
            referenceNode = doc.insertBefore(movingNode, referenceNode);
            movingNode = prevNode;
        }

        // Insert after a reference node in the backward direction.
        referenceNode = sections[12];
        movingNode = sections[15];
        for (int i = 0; i < 2; i++)
        {
            Node prevNode = movingNode.getPreviousSibling();
            doc.insertAfter(movingNode, referenceNode);
            movingNode = prevNode;
        }

        TestUtil.save(doc, "Model\\Revision\\TrackRevisions\\TestSectionMoving.docx", null, true, GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-20666 Tests that Insert and Delete revisions are created when moving into a move range..
    /// </summary>
    @Test
    public void testInsertDeleteRevisionsOnMoving() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        body.removeAllChildren();

        body.appendParagraph("Paragraph 1.");
        Paragraph para2 = body.appendParagraph("Paragraph 2.");
        Paragraph para3 = body.appendParagraph("Paragraph 3.");
        body.appendParagraph("Paragraph 4.");
        Paragraph para5 = body.appendParagraph("Paragraph 5.");

        doc.startTrackRevisionsInternal("Author", new DateTime(2020, 11, 11, 21, 16, 0));

        // Generate move ranges.
        body.appendChild(para2);
        body.appendChild(para3);

        // Insert para5 into the move-to range.
        body.insertBefore(para5, para3);

        ITrackableNode inserted5 = para5;
        msAssert.isNotNull(inserted5.getInsertRevision());
        msAssert.isNull(inserted5.getDeleteRevision());
        msAssert.isNull(inserted5.getMoveToRevision());
        msAssert.isNull(inserted5.getMoveFromRevision());

        Paragraph deletedPara5 = body.getParagraphs().get(4);
        msAssert.areEqual("Paragraph 5.", deletedPara5.getFirstRun().getText());
        ITrackableNode deleted5 = deletedPara5;
        msAssert.isNull(deleted5.getInsertRevision());
        msAssert.isNotNull(deleted5.getDeleteRevision());
        msAssert.isNull(deleted5.getMoveToRevision());
        msAssert.isNull(deleted5.getMoveFromRevision());
    }

    /// <summary>
    /// WORDSNET-20666 Tests that when a node is moved from a move range, an insertion revision is assigned to it.
    /// </summary>
    @Test
    public void testMovingFromMoveRange() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        body.removeAllChildren();

        Paragraph para1 = body.appendParagraph("Paragraph 1.");
        Paragraph para2 = body.appendParagraph("Paragraph 2.");
        Paragraph para3 = body.appendParagraph("Paragraph 3.");
        body.appendParagraph("Paragraph 4.");
        body.appendParagraph("Paragraph 5.");

        // Generate a move-to range.
        doc.startTrackRevisionsInternal("Author", new DateTime(2020, 11, 20, 17, 59, 0));
        body.appendChild(para2);
        body.appendChild(para3);

        // Move a node from the move range.
        doc.startTrackRevisionsInternal("Author", new DateTime(2020, 11, 20, 18, 0, 0));
        body.insertBefore(para2, para1);

        TestUtil.save(doc, "Model\\Revision\\TrackRevisions\\TestMovingFromMoveRange.docx", null, true,
            GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-20666 Tests generating a move range from one run to another in different paragraphs.
    /// </summary>
    @Test
    public void testRunLevelMoveRange() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Body body = doc.getFirstSection().getBody();

        builder.write("Run 1. ");
        builder.write("Run 2 (Move Range Start). ");
        Node rangeStart = builder.getCurrentParagraph().getLastChild();
        builder.writeln("Run 3. ");
        builder.writeln("Run 4. ");
        builder.write("Run 5. ");
        builder.write("Run 6 (Move Range End). ");
        Node rangeEnd = builder.getCurrentParagraph().getLastChild();
        builder.writeln("Run 7. ");
        builder.writeln("Run 8. ");
        builder.write("Run 9. ");
        builder.write("Run 10 (Move Destination - Before). ");
        Node moveDestination = builder.getCurrentParagraph().getLastChild();
        builder.writeln("");

        CompositeNode rangeStartParent = rangeStart.getParentNode();
        CompositeNode rangeEndParent = rangeEnd.getParentNode();
        CompositeNode moveDestinationParent = moveDestination.getParentNode();

        // Temporary remove runs of the first moved paragraph to not assign a move revision to.
        ArrayList<Node> nodesBeforeMoveRange = new ArrayList<Node>();
        while (rangeStartParent.getFirstChild() != rangeStart)
        {
            msArrayList.add(nodesBeforeMoveRange, rangeStartParent.getFirstChild());
            rangeStartParent.getFirstChild().remove();
        }

        final String AUTHOR = "Author";
        DateTime revisionDate = new DateTime(2020, 12, 2, 17, 24, 0);
        doc.startTrackRevisionsInternal(AUTHOR, revisionDate);

        // Move paragraphs, which paragraph-break char should be included into a move range.
        // Paragraph of rangeEnd is not included to a movement.
        body.insertBefore(rangeStartParent, rangeEndParent, moveDestinationParent);

        doc.stopTrackRevisions();

        // Find move-from range start node and move it inside the subsequent paragraph. Insert the removed nodes.
        Node moveFromRangeStart = body.getChild(NodeType.MOVE_FROM_RANGE_START, 0, true);
        Debug.assertTrue(moveFromRangeStart.getNodeLevel() == NodeLevel.BLOCK); // We place it on the block level now.
        CompositeNode moveFromRangeStartParent = (CompositeNode)moveFromRangeStart.getNextSibling();
        moveFromRangeStartParent.insertBefore(moveFromRangeStart, moveFromRangeStartParent.getFirstChild());
        for (Node node : nodesBeforeMoveRange)
            moveFromRangeStartParent.insertBefore(node, moveFromRangeStart);

        // Find move-to range start node and move it inside the subsequent paragraph. Move runs before the destination
        // run into the paragraph, they should be before the move-to range.
        Node moveToRangeStart = body.getChild(NodeType.MOVE_TO_RANGE_START, 0, true);
        Debug.assertTrue(moveToRangeStart.getNodeLevel() == NodeLevel.BLOCK); // We place it on the block level now.
        rangeStartParent.insertBefore(moveToRangeStart, rangeStartParent.getFirstChild());
        if (moveDestinationParent.getFirstChild() != moveDestination)
            rangeStartParent.insertBefore(moveDestinationParent.getFirstChild(), moveDestination, moveToRangeStart);

        // Move rangeEnd and all nodes before it. They will be automatically added to the existing move range.
        doc.startTrackRevisionsInternal(AUTHOR, revisionDate);
        moveDestinationParent.insertBefore(rangeEndParent.getFirstChild(), rangeEnd.getNextSibling(), moveDestination);

        TestUtil.save(doc, "Model\\Revision\\TrackRevisions\\TestRunLevelMoveRange.docx", null, true,
            GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-20666 Tests that a move range is not created on moving non-trackable nodes.
    /// </summary>
    @Test
    public void testMovingBookmark() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        body.removeAllChildren();

        body.appendParagraph("Paragraph 1.");
        body.appendParagraph("Paragraph 2.");
        BookmarkStart start = new BookmarkStart(doc, "Bookmark");
        body.appendChild(start);
        BookmarkEnd end = new BookmarkEnd(doc, "Bookmark");
        body.appendChild(end);

        doc.startTrackRevisionsInternal("Author", new DateTime(2020, 12, 10, 22, 57, 0));

        body.insertBefore(start, end.getNextSibling(), body.getParagraphs().get(1));

        msAssert.isNull(doc.getChild(NodeType.MOVE_FROM_RANGE_START, 0, true));
        msAssert.isNull(doc.getChild(NodeType.MOVE_FROM_RANGE_END, 0, true));
        msAssert.isNull(doc.getChild(NodeType.MOVE_TO_RANGE_START, 0, true));
        msAssert.isNull(doc.getChild(NodeType.MOVE_TO_RANGE_END, 0, true));
    }

    /// <summary>
    /// WORDSNET-21481 Tests that move range date is written as local without conversion to UTC similar like
    /// MS Word does.
    /// </summary>
    @Test
    public void testMoveRangeDate() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        body.removeAllChildren();

        body.appendParagraph("Paragraph 1.");
        Paragraph para2 = body.appendParagraph("Paragraph 2.");
        Paragraph para3 = body.appendParagraph("Paragraph 3.");
        body.appendParagraph("Paragraph 4.");
        Paragraph para5 = body.appendParagraph("Paragraph 5.");

        DateTime date = new DateTime(2020, 12, 3, 15, 47, 0, DateTimeKind.LOCAL);

        // Generate move range.
        doc.startTrackRevisionsInternal("Author", date);
        body.appendChild(para2);
        body.appendChild(para3);

        doc = TestUtil.saveOpen(doc, "Model\\Revision\\TrackRevisions\\TestMoveRangeDate",
            UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        ITrackableNode para = doc.getFirstSection().getBody().getParagraphs().get(doc.getFirstSection().getBody().getParagraphs().getCount() - 1);
        msAssert.areEqual(date, para.getMoveToRevision().getDateTime());

        MoveToRangeStart moveRangeStart = (MoveToRangeStart)doc.getChild(NodeType.MOVE_TO_RANGE_START, 0, true);
        msAssert.areEqual(date, moveRangeStart.getDate());
    }

    /// <summary>
    /// WORDSNET-22321 AW generated too complicated revisions in the revision tracking mode when replacing text
    /// containing a paragraph break.
    /// </summary>
    @Test
    public void test22321() throws Exception
    {
        final String FILE_NAME = "Model\\Revision\\TrackRevisions\\Test22321.docx";
        Document doc = TestUtil.open(FILE_NAME);

        doc.startTrackRevisionsInternal("user", new DateTime(2021, 6, 16));
        doc.getRange().replace("one.&pThis", "replaced");
        doc.stopTrackRevisions();

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paragraphs.getCount());

        Paragraph paragraph1 = paragraphs.get(0);
        msAssert.areEqual(2, paragraph1.getRuns().getCount());

        ITrackableNode paragraph1Trackable = paragraph1;
        msAssert.isNotNull(paragraph1Trackable.getDeleteRevision());
        msAssert.isNull(paragraph1Trackable.getInsertRevision());
        msAssert.isNull(paragraph1Trackable.getMoveFromRevision());
        msAssert.isNull(paragraph1Trackable.getMoveToRevision());

        msAssert.isFalse(paragraphs.get(0).getFirstRun().getRunPr().hasRevisions());

        Paragraph paragraph2 = paragraphs.get(1);
        msAssert.areEqual(3, paragraph2.getRuns().getCount());
        msAssert.isFalse(paragraph2.getParaPr().hasRevisions());

        TestUtil.save(doc, FILE_NAME, null, true, GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-22311 AW generated too complicated revisions in the revision tracking mode when inserting
    /// multiple paragraph breaks using <see cref="Range.Replace(string,string)"/>.
    /// </summary>
    /// <remarks>The output after WORDSNET-26924 is exactly the same as in Word.</remarks>
    @Test
    public void test22311() throws Exception
    {
        final String FILE_NAME = "Model\\Revision\\TrackRevisions\\Test22311.docx";
        Document doc = TestUtil.open(FILE_NAME);

        doc.startTrackRevisionsInternal("Author", new DateTime(2021, 6, 16));
        doc.getRange().replace(",", "&p&p");
        doc.stopTrackRevisions();

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        Paragraph paragraph1 = paragraphs.get(0);
        msAssert.areEqual(2, paragraph1.getRuns().getCount());

        ITrackableNode paragraph1Trackable = paragraph1;
        msAssert.isNull(paragraph1Trackable.getDeleteRevision());
        msAssert.isNotNull(paragraph1Trackable.getInsertRevision());
        msAssert.isNull(paragraph1Trackable.getMoveFromRevision());
        msAssert.isNull(paragraph1Trackable.getMoveToRevision());

        msAssert.isFalse(paragraph1.getFirstRun().getRunPr().hasRevisions());

        Paragraph paragraph2 = paragraphs.get(1);
        msAssert.areEqual(0, paragraph2.getRuns().getCount());

        ITrackableNode paragraph2Trackable = paragraph2;
        msAssert.isNull(paragraph2Trackable.getDeleteRevision());
        msAssert.isNotNull(paragraph2Trackable.getInsertRevision());
        msAssert.isNull(paragraph2Trackable.getMoveFromRevision());
        msAssert.isNull(paragraph2Trackable.getMoveToRevision());

        Paragraph paragraph3 = paragraphs.get(2);
        msAssert.areEqual(0, paragraph3.getRuns().getCount());
        msAssert.isFalse(paragraph3.getParaPr().hasRevisions());

        Paragraph paragraph4 = paragraphs.get(3);
        msAssert.areEqual(0, paragraph3.getRuns().getCount());
        msAssert.isFalse(paragraph4.getParaPr().hasRevisions());

        Paragraph paragraph5 = paragraphs.get(4);
        msAssert.areEqual(2, paragraph1.getRuns().getCount());

        ITrackableNode paragraph5Trackable = paragraph5;
        msAssert.isNull(paragraph5Trackable.getDeleteRevision());
        msAssert.isNotNull(paragraph5Trackable.getInsertRevision());
        msAssert.isNull(paragraph5Trackable.getMoveFromRevision());
        msAssert.isNull(paragraph5Trackable.getMoveToRevision());

        msAssert.isFalse(paragraph5.getFirstRun().getRunPr().hasRevisions());

        TestUtil.save(doc, FILE_NAME, null, true, GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-28443 Track changes are lost in dropdown content controls mapped to Custom XML when saving DOCX
    /// Check DropDownList content by item value.
    /// </summary>
    @Test
    public void test28443() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test28443.docx");
        RevisionGroupCollection groups = doc.getRevisions().getGroups();

        msAssert.areEqual(3, groups.getCount());
    }

    /// <summary>
    /// Checks that given ITrackableNode is marked only for insertion.
    /// </summary>
    private static void checkMarkedOnlyForInsertion(ITrackableNode node)
    {
        msAssert.isNull(node.getDeleteRevision());
        msAssert.isNotNull(node.getInsertRevision());
    }

    /// <summary>
    /// Checks that given ITrackableNode is marked only for deletion.
    /// </summary>
    private static void checkMarkedOnlyForDeletion(ITrackableNode node)
    {
        msAssert.isNotNull(node.getDeleteRevision());
        msAssert.isNull(node.getInsertRevision());
    }

    /// <summary>
    /// Checks that given ITrackableNode is marked for deletion.
    /// </summary>
    private static void checkMarkedForDeletion(ITrackableNode node)
    {
        msAssert.isNotNull(node.getDeleteRevision());
    }

    /// <summary>
    /// Checks that given ITrackableNode is marked for deletion by certain author.
    /// </summary>
    private static void checkMarkedForDeletion(ITrackableNode node, String author)
    {
        msAssert.areEqual(author, node.getDeleteRevision().getAuthor());
    }

    /// <summary>
    /// Checks that given ITrackableNode is marked for insertion by certain author.
    /// </summary>
    private static void checkMarkedForInsertion(ITrackableNode node, String author)
    {
        msAssert.areEqual(author, node.getInsertRevision().getAuthor());
    }

    /// <summary>
    /// Checks that given ITrackableNode is marked for insertion.
    /// </summary>
    private static void checkMarkedForInsertion(ITrackableNode node)
    {
        msAssert.isNotNull(node.getInsertRevision());
    }

    /// <summary>
    /// Checks that given ITrackableNode is NOT marked for insertion.
    /// </summary>
    private static void checkNotMarkedForInsertion(ITrackableNode node)
    {
        msAssert.isNull(node.getInsertRevision());
    }

    /// <summary>
    /// Checks that given ITrackableNode is NOT marked for deletion.
    /// </summary>
    private static void checkNotMarkedForDeletion(ITrackableNode node)
    {
        msAssert.isNull(node.getDeleteRevision());
    }

    /// <summary>
    /// Checks that given ITrackableNode is NOT marked either for insertion or deletion.
    /// </summary>
    private static void checkNotMarked(ITrackableNode node)
    {
        checkNotMarkedForInsertion(node);
        checkNotMarkedForDeletion(node);
    }

    /// <summary>
    /// Checks that paragraphs in given ParagraphCollection and their runs are NOT marked.
    /// </summary>
    private static void checkNotMarked(ParagraphCollection paragraphCollection)
    {
        for (Paragraph p : (Iterable<Paragraph>) paragraphCollection)
        {
            checkNotMarked(p);
            for (Run run : (Iterable<Run>) p.getRuns())
                checkNotMarked(run);
        }
    }

    private static /*final*/ DateTime G_TEST_DATE_TIME = DateTime.parseExact("2014-04-15", "yyyy-MM-dd", msCultureInfo.getInvariantCulture());
}

