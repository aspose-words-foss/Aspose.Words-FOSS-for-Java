// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/06/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests export emphases to markdown format.
/// </summary>
@Test
public class TestExportMarkdownEmphases extends TestMarkdownBase
{
    /// <summary>
    /// Tests when opening and closing delimiters of different emphases are messed. 
    /// </summary>
    @Test
    public void testExportEmphasesA() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesA", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        
        msAssert.areEqual("1234\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "1", true, false);
        checkRun(paragraph.getRuns().get(1), "2", true, true);
        checkRun(paragraph.getRuns().get(2), "3", false, true);
        checkRun(paragraph.getRuns().get(3), "4", false, false);
    }

    /// <summary>
    /// Tests emphases with line break. 
    /// </summary>
    @Test
    public void testExportEmphasesB() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesB", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("12\u000b34\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "1", true, false);
        checkRun(paragraph.getRuns().get(1), "2", true, true);
        checkRun(paragraph.getRuns().get(2), "\u000b", false, false);
        checkRun(paragraph.getRuns().get(3), "3", false, true);
        checkRun(paragraph.getRuns().get(4), "4", false, false);
    }

    /// <summary>
    /// Tests when Bold and Italic are opened together, but closed at different places. 
    /// </summary>
    @Test
    public void testExportEmphasesC() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesC", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("12\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "1", true, true);
        checkRun(paragraph.getRuns().get(1), "2", false, true);
    }

    /// <summary>
    /// Tests when Bold and Italic are opened together, but closed at different places
    /// and one of them is closed just before LineBreak. 
    /// </summary>
    @Test
    public void testExportEmphasesD() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesD", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("123\u000b4\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "12", true, true);
        checkRun(paragraph.getRuns().get(1), "3", false, true);
        checkRun(paragraph.getRuns().get(2), "\u000b4", false, false);
    }

    /// <summary>
    /// Tests when all delimiters are intraword.  
    /// </summary>
    @Test
    public void testExportEmphasesE() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesE", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("01234\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "0", false, false);
        checkRun(paragraph.getRuns().get(1), "12", true, true);
        checkRun(paragraph.getRuns().get(2), "3", false, true);
        checkRun(paragraph.getRuns().get(3), "4", false, false);
    }

    /// <summary>
    /// Tests emphases are closed not in the same order as was opened, so that one of them (Bold)
    /// is located inside a closed one.
    /// </summary>
    @Test
    public void testExportEmphasesF() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesF", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("1234\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "1", false, true);
        checkRun(paragraph.getRuns().get(1), "2", true, true);
        checkRun(paragraph.getRuns().get(2), "3", true, false);
        checkRun(paragraph.getRuns().get(3), "4", false, false);
    }

    /// <summary>
    /// Tests warning is issued when formatting cannot be preserved in markdown properly. 
    /// This is the case when emphases are closed not in the same order as was opened, so that one of them (Italic)
    /// is located inside another one that is closed. As the emphases are intraword, they cannot be changed to
    /// underscores and therefore cannot be written into markdown properly.
    /// </summary>
    @Test
    public void testExportEmphasesG() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\Emphases\\TestExportEmphasesG.docx");

        TestWarningCallback warnings = new TestWarningCallback();
        doc.setWarningCallback(warnings);

        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\Emphases\\TestExportEmphasesG", UnifiedScenario.DOCX_2_MD);
        
        msAssert.isTrue(warnings.contains(WarningSource.MARKDOWN, WarningType.MAJOR_FORMATTING_LOSS,
                msString.format(WarningStrings.MARKDOWN_FORMATTING_LOST, "*, 0:11")));
        
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        
        msAssert.areEqual("abcde\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "a", false, false, true);
        checkRun(paragraph.getRuns().get(1), "b", true, false, true);
        checkRun(paragraph.getRuns().get(2), "c", true, true, true);
        checkRun(paragraph.getRuns().get(3), "de", false, false, true);
    }

    /// <summary>
    /// Tests Bold emphasis default delimiter is changed to '__' at the place where also strikethrough is opening.  
    /// </summary>
    @Test
    public void testExportEmphasesH() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesH", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("1234\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "1", true, false);
        checkRun(paragraph.getRuns().get(1), "2", true, true);
        checkRun(paragraph.getRuns().get(2), "3", false, true, true);
        checkRun(paragraph.getRuns().get(3), "4", false, false, true);
    }

    /// <summary>
    /// Tests when all three types of emphases are messed (Bold, Italic and StrikeThrough). 
    /// </summary>
    @Test
    public void testExportEmphasesI() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesI", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("01234\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "0", false, false);
        checkRun(paragraph.getRuns().get(1), "1", true, false);
        checkRun(paragraph.getRuns().get(2), "2", true, false, true);
        checkRun(paragraph.getRuns().get(3), "3", false, false, true);
        checkRun(paragraph.getRuns().get(4), "4", false, false);
    }

    /// <summary>
    /// Tests when all three types of emphases are messed (Bold, Italic and StrikeThrough),
    /// the same as above, but in another order.
    /// </summary>
    @Test
    public void testExportEmphasesJ() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesJ", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("12345\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "1", true, false);
        checkRun(paragraph.getRuns().get(1), "2", true, false, true);
        checkRun(paragraph.getRuns().get(2), "3", true, true, true);
        checkRun(paragraph.getRuns().get(3), "4", false, true, true);
        checkRun(paragraph.getRuns().get(4), "5", false, true);
    }

    /// <summary>
    /// Tests various emphases with InlineCode in the middle of a text. 
    /// </summary>
    @Test
    public void testExportEmphasesK() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesK", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("123z45\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "1", true, false);
        checkRun(paragraph.getRuns().get(1), "2", true, false, true);
        checkRun(paragraph.getRuns().get(2), "3", true, true, true);
        checkRun(paragraph.getRuns().get(3), "z", true, true, true);
        checkRun(paragraph.getRuns().get(4), "4", false, true, true);
        checkRun(paragraph.getRuns().get(5), "5", false, true);
    }

    /// <summary>
    /// Tests various emphases messed and Bold changes its delimiter to '__' properly before space.   
    /// </summary>
    @Test
    public void testExportEmphasesL() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Emphases\\TestExportEmphasesL", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("123 45\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "1", true, false);
        checkRun(paragraph.getRuns().get(1), "2", true, false, true);
        checkRun(paragraph.getRuns().get(2), "3", true, true, true);
        checkRun(paragraph.getRuns().get(3), " ", false, false);
        checkRun(paragraph.getRuns().get(4), "4", false, true, true);
        checkRun(paragraph.getRuns().get(5), "5", false, true);
    }

}

