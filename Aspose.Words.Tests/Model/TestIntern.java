// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msObject;


/// <summary>
/// Tests attribute interning.
/// </summary>
@Test
public class TestIntern
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests that collection uninterned before changed.
    /// </summary>
    @Test
    public void testIntern01()
    {
        InternManager internManager = new InternManager();

        RunPr runPr1 = new RunPr();
        RunPr runPr2 = new RunPr();

        runPr1.setItalic(AttrBoolEx.TRUE);
        runPr1.setBold(AttrBoolEx.TRUE);
        runPr2.setItalic(AttrBoolEx.TRUE);
        runPr2.setBold(AttrBoolEx.TRUE);

        msAssert.areEqual(InternState.NONE, runPr1.getInternState());
        msAssert.areEqual(InternState.NONE, runPr2.getInternState());

        internManager.add(runPr1);
        msAssert.areEqual(InternState.POOLED, runPr1.getInternState());

        internManager.add(runPr2);
        msAssert.areEqual(InternState.INTERNED, runPr2.getInternState());

        msAssert.areEqual(AttrBoolEx.TRUE, runPr1.getItalic());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr1.getBold());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr2.getItalic());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr2.getBold());

        runPr2.setItalic(AttrBoolEx.FALSE);

        // Collection is detached.
        msAssert.areEqual(InternState.NONE, runPr2.getInternState());

        msAssert.areEqual(AttrBoolEx.FALSE, runPr2.getItalic());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr2.getBold());
    }

    /// <summary>
    /// Tests that collection unpooled before change.
    /// </summary>
    @Test
    public void testIntern02()
    {
        InternManager internManager = new InternManager();

        RunPr runPr1 = new RunPr();
        RunPr runPr2 = new RunPr();

        runPr1.setItalic(AttrBoolEx.TRUE);
        runPr1.setBold(AttrBoolEx.TRUE);
        runPr2.setItalic(AttrBoolEx.TRUE);
        runPr2.setBold(AttrBoolEx.TRUE);

        internManager.add(runPr1);
        msAssert.areEqual(InternState.POOLED, runPr1.getInternState());

        internManager.add(runPr2);
        msAssert.areEqual(InternState.INTERNED, runPr2.getInternState());

        msAssert.areEqual(AttrBoolEx.TRUE, runPr1.getItalic());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr1.getBold());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr2.getItalic());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr2.getBold());

        runPr1.setItalic(AttrBoolEx.FALSE);
        // First collection is removed from pool.
        msAssert.areEqual(InternState.NONE, runPr1.getInternState());

        msAssert.areEqual(AttrBoolEx.FALSE, runPr1.getItalic());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr2.getItalic());
    }

    /// <summary>
    /// Tests that collection with complex attribute uninterned before attribute part is changed.
    /// </summary>
    @Test
    public void testIntern03()
    {
        InternManager internManager = new InternManager();

        AttrCollection pr1 = new RunPr();
        AttrCollection pr2 = new RunPr();

        LineSpacing spacing1 = new LineSpacing(240, LineSpacingRule.MULTIPLE);
        LineSpacing spacing2 = new LineSpacing(240, LineSpacingRule.MULTIPLE);

        pr1.setAttr(ParaAttr.LINE_SPACING, spacing1);
        pr2.setAttr(ParaAttr.LINE_SPACING, spacing2);

        internManager.add(pr1);
        internManager.add(pr2);

        msAssert.areEqual(InternState.POOLED, pr1.getInternState());
        msAssert.areEqual(InternState.INTERNED, pr2.getInternState());

        spacing2.setValue(480);

        // After this we second collection should be uninterned.
        msAssert.areEqual(InternState.NONE, pr2.getInternState());

        // Verify that value will not notify anymore.
        msAssert.isFalse(spacing2.isAttached());

        spacing2.setValue(240);
    }

    /// <summary>
    /// Two complex attributes, one is changed.
    /// </summary>
    @Test
    public void testIntern04()
    {
        InternManager internManager = new InternManager();

        AttrCollection pr1 = new RunPr();
        LineSpacing spacing1 = new LineSpacing(240, LineSpacingRule.MULTIPLE);
        pr1.setAttr(ParaAttr.LINE_SPACING, spacing1);
        Height height1 = new Height(HeightRule.AT_LEAST, 100);
        pr1.setAttr(ParaAttr.FRAME_HEIGHT, height1);
        internManager.add(pr1);

        AttrCollection pr2 = new RunPr();
        LineSpacing spacing2 = new LineSpacing(240, LineSpacingRule.MULTIPLE);
        pr2.setAttr(ParaAttr.LINE_SPACING, spacing2);
        Height height2 = new Height(HeightRule.AT_LEAST, 100);
        pr2.setAttr(ParaAttr.FRAME_HEIGHT, height2);
        internManager.add(pr2);

        msAssert.areEqual(InternState.POOLED, pr1.getInternState());
        msAssert.areEqual(InternState.INTERNED, pr2.getInternState());

        spacing2.setValue(480);

        // After this we second collection should be uninterned.
        msAssert.areEqual(InternState.NONE, pr2.getInternState());

        // Verify that value will not notify anymore.
        msAssert.isFalse(spacing2.isAttached());

        height2.setValue(10);

        // Verify that first value remains unchanged.
        msAssert.areEqual(100, height1.getValue());
    }

    /// <summary>
    /// Two complex attributes, one is changed.
    /// </summary>
    @Test
    public void testIntern05()
    {
        InternManager internManager = new InternManager();

        AttrCollection pr1 = new RunPr();
        LineSpacing spacing1 = new LineSpacing(240, LineSpacingRule.MULTIPLE);
        pr1.setAttr(ParaAttr.LINE_SPACING, spacing1);
        Height height1 = new Height(HeightRule.AT_LEAST, 100);
        pr1.setAttr(ParaAttr.FRAME_HEIGHT, height1);
        internManager.add(pr1);

        AttrCollection pr2 = new RunPr();
        LineSpacing spacing2 = new LineSpacing(240, LineSpacingRule.MULTIPLE);
        pr2.setAttr(ParaAttr.LINE_SPACING, spacing2);
        Height height2 = new Height(HeightRule.AT_LEAST, 100);
        pr2.setAttr(ParaAttr.FRAME_HEIGHT, height2);
        internManager.add(pr2);

        msAssert.areEqual(InternState.POOLED, pr1.getInternState());
        msAssert.areEqual(InternState.INTERNED, pr2.getInternState());

        // Remove attribute from interned collection.
        pr2.remove(ParaAttr.FRAME_HEIGHT);

        // Collection now is uninterned.
        msAssert.areEqual(InternState.NONE, pr2.getInternState());

        // And value is still attached.
        msAssert.isTrue(spacing2.isAttached());

        // But detached after first set.
        spacing2.setRule(LineSpacingRule.AT_LEAST);
        msAssert.isFalse(spacing2.isAttached());
    }

    /// <summary>
    /// Tests that complex attributes are deep cloned when uninterned.
    /// </summary>
    @Test
    public void testIntern06()
    {
        InternManager internManager = new InternManager();

        ParaPr pr1 = new ParaPr();
        LineSpacing spacing1 = new LineSpacing(240, LineSpacingRule.MULTIPLE);
        pr1.setAttr(ParaAttr.LINE_SPACING, spacing1);
        pr1.setAttr(ParaAttr.ALIGNMENT, ParagraphAlignment.CENTER);
        internManager.add(pr1);

        ParaPr pr2 = new ParaPr();
        LineSpacing spacing2 = new LineSpacing(240, LineSpacingRule.MULTIPLE);
        pr2.setAttr(ParaAttr.LINE_SPACING, spacing2);
        pr2.setAttr(ParaAttr.ALIGNMENT, ParagraphAlignment.CENTER);
        internManager.add(pr2);

        msAssert.areEqual(InternState.POOLED, pr1.getInternState());
        msAssert.areEqual(InternState.INTERNED, pr2.getInternState());

        // Change alignment in second collection.
        pr2.setAttr(ParaAttr.ALIGNMENT, ParagraphAlignment.RIGHT);
        msAssert.areEqual(InternState.NONE, pr2.getInternState());

        // Change value of second complex attribute.
        pr2.setLineSpacing(360);

        // Check deep clone.
        msAssert.isFalse(msObject.referenceEquals(pr1.get(ParaAttr.LINE_SPACING), pr2.get(ParaAttr.LINE_SPACING)));
        msAssert.areEqual(240, pr1.getLineSpacing());
        msAssert.areEqual(360, pr2.getLineSpacing());
    }

    /// <summary>
    /// Tests that we correctly deep clone when collection is unpooled.
    /// </summary>
    @Test
    public void testIntern07()
    {
        InternManager internManager = new InternManager();

        ParaPr pr1 = new ParaPr();
        LineSpacing spacing1 = new LineSpacing(240, LineSpacingRule.MULTIPLE);
        pr1.setAttr(ParaAttr.LINE_SPACING, spacing1);
        pr1.setAttr(ParaAttr.ALIGNMENT, ParagraphAlignment.CENTER);
        internManager.add(pr1);

        ParaPr pr2 = new ParaPr();
        LineSpacing spacing2 = new LineSpacing(240, LineSpacingRule.MULTIPLE);
        pr2.setAttr(ParaAttr.LINE_SPACING, spacing2);
        pr2.setAttr(ParaAttr.ALIGNMENT, ParagraphAlignment.CENTER);
        internManager.add(pr2);

        msAssert.areEqual(InternState.POOLED, pr1.getInternState());
        msAssert.areEqual(InternState.INTERNED, pr2.getInternState());

        // Unpool first collection
        pr1.setAlignment(ParagraphAlignment.DISTRIBUTED);

        // Change complex attribute in it.
        pr1.setLineSpacingRule(LineSpacingRule.EXACTLY);

        // Check that second collection is not changed.
        msAssert.areEqual(LineSpacingRule.MULTIPLE, pr2.getLineSpacingRule());

        // Unintern second.
        pr2.setAlignment(ParagraphAlignment.LEFT);

        msAssert.isFalse(msObject.referenceEquals(pr1.get(ParaAttr.LINE_SPACING), pr2.get(ParaAttr.LINE_SPACING)));

        msAssert.areEqual(ParagraphAlignment.DISTRIBUTED, pr1.getAlignment());
        msAssert.areEqual(ParagraphAlignment.LEFT, pr2.getAlignment());
    }

    /// <summary>
    /// Tests that after validator executed all collections are interned.
    /// </summary>
    @Test
    public void test1() throws Exception
    {
        Document doc = TestUtil.open("Model\\Intern\\Table.docx");

        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.MARKDOWN);
        so.setMemoryOptimization(true);
        DocumentValidator validator = TestUtil.executeValidator(doc, so);

        InternManager internManager = doc.InternManager;
        msAssert.areEqual(24, internManager.PooledCount);
        msAssert.areEqual(107, internManager.InternedCount);
        msAssert.areEqual(6, internManager.EmptyCount);

        Row row = doc.getFirstSection().getBody().getTables().get(0).getFirstRow();

        // Get run from first cell. It should be pooled.
        RunPr runPr = row.getCells().get(0).getFirstParagraph().getFirstRun().getRunPr();
        msAssert.areEqual(InternState.POOLED, runPr.getInternState());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr.get(FontAttr.BOLD));
        msAssert.areEqual(22, runPr.get(FontAttr.SIZE));

        // Get run from second cell and it should be interned.
        runPr = row.getCells().get(1).getFirstParagraph().getFirstRun().getRunPr();
        msAssert.areEqual(InternState.INTERNED, runPr.getInternState());
        msAssert.areEqual(AttrBoolEx.TRUE, runPr.get(FontAttr.BOLD));
        msAssert.areEqual(22, runPr.get(FontAttr.SIZE));

        validator.revert();

        // InternManager is removed from document.
        msAssert.isNull(doc.InternManager);

        msAssert.areEqual(24, internManager.UnPooledCount);
        msAssert.areEqual(107, internManager.UnInternedCount);

        // Cell properties are un-pooled and un-interned.
        msAssert.areEqual(InternState.NONE, row.getCells().get(0).getFirstParagraph().getFirstRun().getRunPr().getInternState());
        msAssert.areEqual(InternState.NONE, row.getCells().get(1).getFirstParagraph().getFirstRun().getRunPr().getInternState());
    }

    /// <summary>
    /// Tests that complex attributes are interned.
    /// </summary>
    @Test
    public void testComplex1() throws Exception
    {
        Document doc = TestUtil.open("Model\\Intern\\Complex1.docx");

        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.MARKDOWN);
        so.setMemoryOptimization(true);
        TestUtil.executeValidator(doc, so);

        // FOSS: validator runs with Markdown (Pdf removed); it pools one extra collection vs the old Pdf pass.
        msAssert.areEqual(7, doc.InternManager.PooledCount);
        msAssert.areEqual(3, doc.InternManager.InternedCount);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // First paragraph properties are pooled.
        msAssert.areEqual(InternState.POOLED, paras.get(0).getParaPr().getInternState());

        // and second paragraph is interned.
        msAssert.areEqual(InternState.INTERNED, paras.get(1).getParaPr().getInternState());
    }

    /// <summary>
    /// Tests more complex properties such as borders and shadings.
    /// </summary>
    @Test
    public void testComplex2() throws Exception
    {
        Document doc = TestUtil.open("Model\\Intern\\Complex2.docx");

        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.MARKDOWN);
        so.setMemoryOptimization(true);
        TestUtil.executeValidator(doc, so);

        // FOSS: validator runs with Markdown (Pdf removed); it pools one extra collection vs the old Pdf pass.
        msAssert.areEqual(14, doc.InternManager.PooledCount);
        msAssert.areEqual(19, doc.InternManager.InternedCount);
    }

    /// <summary>
    /// Tests that cloned document gets uninterned collections.
    /// </summary>
    @Test
    public void testClone() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Intern\\Complex2.docx");

        InternManager.add(srcDoc);

        checkInternState(srcDoc, true);

        Document clonedDoc = (Document)srcDoc.deepClone(true);

        // Cloned doc gets normal properties.
        checkInternState(clonedDoc, false);

        // And whole source document still interned.
        checkInternState(srcDoc, true);
    }



    /// <summary>
    /// WORDSNET-19069 NullReferenceException when saving DOCX to PDF with MemoryOptimization enabled
    /// TableStyle cloning issue.
    /// </summary>
    @Test
    public void test19069() throws Exception
    {
        Document doc = TestUtil.open("Model\\Intern\\Test19069.docx");

        InternManager.add(doc);

        Document doc1 = (Document)doc.deepClone(false);

        TableStyle tableNormal = (TableStyle)doc1.getStyles().getBySti(StyleIdentifier.TABLE_NORMAL, false);
        msAssert.areEqual(InternState.NONE, tableNormal.getTablePr().getInternState());

        InternManager.add(doc1);
        InternManager.remove(doc1);

        InternManager.remove(doc);
    }



    /// <summary>
    /// Checks that whole document is interned or not depending on given parameter.
    /// </summary>
    private static void checkInternState(Document doc, boolean interned)
    {
        for (Paragraph para : (Iterable<Paragraph>) doc.getFirstSection().getBody().getParagraphs())
        {
            checkInternState(para.getParaPr(), interned);
            checkInternState(para.getParagraphBreakRunPr(), interned);

            for (Run run : (Iterable<Run>) para.getRuns())
                checkInternState(run.getRunPr(), interned);
        }

        for (Table table : (Iterable<Table>) doc.getFirstSection().getBody().getTables())
        {
            for (Row row : (Iterable<Row>) table.getRows())
            {
                checkInternState(row.getTablePr(), interned);
                for (Cell cell : (Iterable<Cell>) row.getCells())
                    checkInternState(cell.getCellPr(), interned);
            }
        }

        for (Style style : doc.getStyles())
        {
            checkInternState(style.getRunPr(), interned);

            if(style.getType() == StyleType.PARAGRAPH)
                checkInternState(style.getParaPr(), interned);
        }
    }

    private static void checkInternState(AttrCollection pr, boolean interned)
    {
        if (pr.getCount() == 0)
            return;

        msAssert.areEqual(interned, (InternState.NONE != pr.getInternState()));
    }
}

