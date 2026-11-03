// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/07/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.SystemPal;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests related to revision group collection feature.
/// </summary>
@Test
public class TestRevisionGroup
{
    @BeforeMethod (alwaysRun = true)
    public void setStandardCulture()
    {
        SystemPal.saveCulture();
        SystemPal.setStandardCulture();
    }

    @AfterMethod (alwaysRun = true)
    public void revertToOldCulture()
    {
        SystemPal.restoreCulture();
    }

    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests that for deleted table only text of first cell is collected to visible nodes.
    /// </summary>
    @Test
    public void testDeletedTable() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\TestDeletedTable.docx");
        RevisionGroupCollection groups = doc.getRevisions().getGroups();
        msAssert.areEqual(1, groups.getCount());
        msAssert.areEqual("1", groups.get(0).getVisibleText());
    }

    /// <summary>
    /// Tests that for two adjacent deleted table only text of first cell of first table is collected.
    /// </summary>
    @Test
    public void testDeletedTwoTables() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\TestDeletedTwoTables.docx");

        RevisionGroupCollection groups = doc.getRevisions().getGroups();
        msAssert.areEqual(1, groups.getCount());
        msAssert.areEqual("rt\rAbc\rcde\rxyz", groups.get(0).getVisibleText());
    }

    /// <summary>
    /// Tests how inline shape deletion is collected.
    /// </summary>
    @Test
    public void testDeletedInlineShape() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\TestDeletedInlineShape.docx");

        RevisionGroupCollection groups = doc.getRevisions().getGroups();
        msAssert.areEqual(1, groups.getCount());
        msAssert.areEqual(NodeType.SHAPE, groups.get(0).getVisibleNodes().get(0).getNodeType());
    }

    /// <summary>
    /// Tests how section break deletion is collected.
    /// </summary>
    @Test
    public void testDeletedSectionBreakMark() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\TestDeletedSectionBreakMark.docx");

        RevisionGroupCollection groups = doc.getRevisions().getGroups();
        msAssert.areEqual(1, groups.getCount());
        msAssert.areEqual("t page\r\fSecond pa", groups.get(0).getVisibleText());
    }

    /// <summary>
    /// Tests how paragraph break deletion is collected when last paragraph break is not included.
    /// </summary>
    @Test
    public void testDeletedParagraphBreakMark() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\TestDeletedParagraphBreakMark.docx");

        RevisionGroupCollection groups = doc.getRevisions().getGroups();
        msAssert.areEqual(2, groups.getCount());

        msAssert.areEqual("st\rSe", groups.get(0).getVisibleText());
    }

    /// <summary>
    /// Tests how paragraph break deletion is collected when last paragraph break is included.
    /// </summary>
    @Test
    public void testDeletedParagraphBreakMark2() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\TestDeletedParagraphBreakMark2.docx");

        RevisionGroupCollection groups = doc.getRevisions().getGroups();
        msAssert.areEqual(1, groups.getCount());

        msAssert.areEqual("First\rSecond\r", groups.get(0).getVisibleText());
    }

    /// <summary>
    /// Tests how few revision groups are collected.
    /// </summary>
    @Test
    public void testFewGroups() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestFewGroups.docx",
            new String[] { "mer hav", "g iss", "e with an RTF do", "ume", "t, on importing docume", "M custom style im" });
    }


    /// <summary>
    /// Tests format string for various borders and combination of borders.
    /// </summary>
    @Test
    public void testBorders() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestBorders.docx", new String[]
            {
                "Border: : (Dotted, Green, 0.5 pt Line width)",
                "Border: Box: (Single solid line, Background 2, 0.5 pt Line width)",
                "Border: Left: (Single solid line, Auto, 0.5 pt Line width)",
                "Border: Right: (Single solid line, Auto, 0.5 pt Line width)",
                "Border: Left: (Single solid line, Auto, 0.5 pt Line width), Right: (Single solid line, Auto, 0.5 pt Line width)",
                "Border: Between: (Single solid line, Auto, 0.5 pt Line width), Bar: (Single solid line, Auto, 0.5 pt Line width), Box: (Single solid line, Auto, 0.5 pt Line width)",
                "Border: Between: (Single solid line, Auto, 0.5 pt Line width)",
                "Border: Right: (Dashed (large gap), Custom Color(RGB(38,154,110)), 4.5 pt Line width)",
                "Border: Top: (Triple solid lines, Orange, 0.5 pt Line width), Bottom: (Dash dot (stroked), Orange, 3 pt Line width), Left: (Dashed (large gap), Dark Red, 4.5 pt Line width), Right: (Double wavy, Custom Color(RGB(59,23,73)), 0.75 pt Line width)",
                "Border: Left: (Triple solid lines, Accent 1, 0.5 pt Line width), Right: (Triple solid lines, Accent 1, 0.5 pt Line width)",
                "Border: Box: (Shadowed Dash dot dot, Accent 6, 1.5 pt Line width)"
            });
    }

    @Test
    public void testShading() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestShading.docx", new String[]{
            "Pattern: Solid (100%) (Accent 6)",
            "Pattern: Dk Horizontal (Accent 2 Foreground, Auto Background)",
            "Pattern: Lt Trellis (Dark Red Foreground, Auto Background)",
            "Pattern: Clear (Accent 4)",
            "Pattern: 62.5% (Accent 1 Foreground, Accent 1 Background)",
            "Pattern: Dk Grid (Accent 4 Foreground, Accent 4 Background)",
            "Pattern: Lt Grid (Custom Color(RGB(204,102,255)) Foreground, Yellow Background)",
            "Pattern: 12.5% (Background 1 Foreground, Background 1 Background)" });
    }

    @Test
    public void testSectionFormat() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestSectionFormat.docx",
            "Left: 2 cm, Right: 2 cm, Top: 3 cm, Bottom: 1.5 cm, Width: 29.7 cm, Height: 21 cm");
    }

    @Test
    public void testUnderline() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestUnderline.docx", new String[]
            { "Underline", "Double underline", "Thick underline", "Dotted underline",
              "Dashed underline", "Dot-dash underline", "Dot-dot-dash underline", "Wave underline",
              "Underline, Underline color: Accent 4", "Wave double underline" });
    }

    @Test
    public void testCrossSections() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestCrossSections.docx", "Font color: Blue");
    }

    @Test
    public void testLineSpacing() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestLineSpacing.docx",
            new String[] { "Line spacing: Single", "Line spacing: 1.5 lines", "Line spacing: Exactly 12 pt", "Line spacing: At least 30 pt" });
    }


    /// <summary>
    /// Test revision text for paragraph formatting.
    /// </summary>
    @Test
    public void testParagraphFormat() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestParagraphFormat.docx", new String[]
            {
                "Centered",
                "Right, Indent: Left: 1 cm, Right: 1 cm, Space Before: 6 pt, After: 12 pt, Line spacing: Double, " +
                "Keep with next, Keep lines together, Border: Box: (Dashed (small gap), Accent 5, 6 pt Line width), Pattern: 20% (Auto Foreground, Accent 4 Background)"
            });
    }

    /// <summary>
    /// Test revision text for paragraph formatting.
    /// </summary>
    @Test
    public void testParagraphBools() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestParagraphBools.docx", new String[]
            {
                "Automatically adjust right indent when grid is defined, " +
                "Add space between paragraphs of the same style, " +
                "No widow/orphan control, " +
                "No page break before, " +
                "Don't keep with next, " +
                "Don't keep lines together, " +
                "Don't suppress line numbers, " +
                "Hyphenate, " +
                "Don't use Asian rules to control first and last character, " +
                "Allow text to wrap in the middle of a word, " +
                "Don't allow hanging punctuation, " +
                "Don't adjust space between Latin and Asian text, " +
                "Don't adjust space between Asian text and numbers, " +
                "Snap to grid, " +
                "Not Don't swap indents on facing pages, " +
                "Compress initial punctuation",

                "Allow text to wrap in the middle of a word",
                "No page break before, Allow text to wrap in the middle of a word, Don't allow hanging punctuation"
            });
    }
    /// <summary>
    /// Tests revision text for various text effects.
    /// </summary>
    @Test
    public void testEffects() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestEffects.docx",
            "Font: Bold, Font color: Accent 4, Text Outline, Glow, Bevel");
    }

    @Test
    public void testRunFormat1() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestRunFormat1.docx", new String[]
            {
                "Font: Bold, Underline, Font color: Purple, Strikethrough, Superscript, Expanded by 1 pt, Raised by 5 pt, Kern at 8 pt, Border: : (Single solid line, Auto, 0.5 pt Line width), Highlight",
                "Font: Bold, Italic, Complex Script Font: Bold, Italic"
            });
    }

    @Test
    public void testRunFormat2() throws Exception
    {
        // There is a strange issue with Highlight. Paragraph break has no highlight set but Word doesn't show separate group for it.
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestRunFormat2.docx", new String[]
            {
                "Font: Arial Rounded MT Bold, 24 pt, Bold, Italic, Underline, Font color: Accent 4, Complex Script Font: 24 pt, Double strikethrough, Subscript, Small caps, Highlight, Text Outline, Reflection",
                "Font: Arial Rounded MT Bold, 24 pt, Bold, Italic, Underline, Font color: Accent 4, Complex Script Font: 24 pt, Double strikethrough, Subscript, Small caps, Text Outline, Reflection"
            });
    }

    @Test
    public void testRunFormat3() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestRunFormat3.docx",
            new String[]
            {
                "Font: Bold, Italic, Underline, Font color: Purple, Complex Script Font: Italic, Strikethrough, Subscript",
                "Font: (Default) Times New Roman, Complex Script Font: Times New Roman",
                "Underline",
                "Font: Italic, Underline",
                "Font: Italic, Strikethrough",
                "Strikethrough",
                "Strikethrough, Superscript",
                "Superscript",
                "Font color: Accent 4, Superscript",
                // Word doesn't show this balloon for some reason.
                "Font color: Accent 4",
                "Font color: Accent 4, Subscript",
                // Word doesn't show this balloon for some reason.
                "Font color: Accent 4",
                "Font: 28 pt, Font color: Accent 4, Complex Script Font: 28 pt",
                "Font: 28 pt, Complex Script Font: 28 pt",
                "Font: 28 pt, Complex Script Font: 28 pt, Double strikethrough",
                "Double strikethrough",
                "Font: 26 pt, Dot-dot-dash underline, Complex Script Font: 26 pt",
                "Font: 26 pt, Dot-dot-dash underline, Underline color: Custom Color(RGB(214,0,147)), Complex Script Font: 26 pt",
                "Dot-dot-dash underline, Underline color: Accent 1",
                "Small caps", "Character scale: 50%",
                "Character scale: 50%, Expanded by 1 pt, Kern at 11 pt",
                "Raised by 6 pt, Kern at 11 pt",
                "Font: Italic, Raised by 6 pt",
                "Font: Bold, Italic",
                "Font: Consolas",
                "Font: Consolas, Italic, Font color: Accent 4, Subscript",
                "Font: Consolas",
            });
    }

    @Test
    public void testColors() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestColors.docx", new String[]
            {
                "Font color: Dark Red",
                "Font color: Yellow",
                "Font color: Green",
                "Font color: Light Green",
                "Font color: Accent 6",
                "Font color: Text 1",
                "Font color: Accent 3",
                "Font color: Background 1",
                "Font color: Accent 6",
                "Font color: Custom Color(RGB(220,169,68))",
                "Font color: Dark Blue",
                "Font color: Purple", "Text Fill"
            });
    }

    @Test
    public void testFormattedDeleted() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestFormattedDeleted.docx",
            new String[] { "formatted ", "Font: 20 pt, Bold, Italic" });
    }

    /// <summary>
    /// Document is taken from WORDSNET-10988 forum links.
    /// </summary>
    @Test
    public void testJira10988A() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestJira10988A.docx",
            new String[] { "Font: Verdana, 15 pt, Bold" });
    }

    @Test
    public void testJira10988B() throws Exception
    {
        // AM. 'Complex Script Font: 18pt' label is shown only if any complex script language (Arabic for example) is
        // in Office Language Preferences list.
        // Actually I don't know how to handle this since Language preferences is load time option but
        // revision group is runtime feature.
        //
        // Maybe we need a parallel property, something like RevisionGroup.ComplexScriptText.

        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestJira10988B.docx",
            new String[] { "Font: 18 pt, Bold, Complex Script Font: 18 pt", "Underline", "Font: Italic", "Centered", "Right" });
    }




    @Test
    public void testIsFormatRevisionFlag() throws Exception
    {
        testRevisionGroups("Model\\Revision\\TestIsFormatRevisionFlag.docx", new String[] { "Font: Italic", "Heading 2" });
    }



    @Test
    public void testHighlight() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestHighlight.docx",
            new String[] { "Font color: Dark Blue, Highlight", "Font color: Dark Blue" });
    }

    /// <summary>
    /// Tests that table formatting change is correctly formatted.
    /// </summary>
    @Test
    public void testTableFormatted() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestTableFormatted.docx", "Table");
    }

    /// <summary>
    /// Tests that theme fonts are correctly formatted.
    /// </summary>
    @Test
    public void testThemeFonts() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestThemeFonts.docx", "Font: +Headings (Calibri Light)");
    }


    /// <summary>
    /// Exception is thrown on layout.
    /// </summary>
    @Test
    public void testStyleRevisions() throws Exception
    {
        testRevisionGroups("Model\\Revision\\TestStyleRevisions.docx", new String[]
            {
                "Emphasis: Font: Bold, Font color: Accent 1, Border: : (Dashed (small gap), Auto, 0.5 pt Line width)",
                "Heading 2: , Space Before: 4 pt, After: 0 pt, Line spacing: Double",
                "T",
                "t",
                "Font: Bold",
                "Border: Bottom: (Single solid line, Accent 1, 0.5 pt Line width)"
            });
    }

    /// <summary>
    /// Tests list revision.
    /// </summary>
    /// <remarks>
    /// AM. This series of tests (TestJira5738-1, 2, etc.) is good for testing List related formatting.
    /// </remarks>
    @Test (enabled = false, description = "List format issue, postpone for a while.")
    public void testJira5738_1() throws Exception
    {
        testRevisionGroups("ImportDocx\\TestJira5738-1.docx", new String[]
            {
                "Normal, No bullets or numbering",
                "List Paragraph, Numbered + Level: 1 + Numbering Style: I, II, III, ... + Start at: 1 + Alignment: Right + Aligned at: 0.64 cm + Indent at: 1.27 cm",
                "Numbered + Level: 1 + Numbering Style: 1, 2, 3, ... + Start at: 1 + Alignment: Left + Aligned at: 0.64 cm + Indent at: 1.27 cm"
            });
    }


    /// <summary>
    /// WORDSNET-15506 Support of move revisions.
    /// </summary>
    @Test
    public void testMoveRevisions() throws Exception
    {
        testRevisionGroups("Model\\Revision\\RevisionGroups\\TestMoveRevisionGroups.docx", new String[]
            {
                "Test\r",
                "Test\rTest\r",
                "Row 5",
                " ",
                "Test\rTest\r",
                "Inserted",
                "Test Test Test\rTest\r",
                "Moved Test Moved\rTest\rTest\rMoved Simple Moved\rMoved Simple",
                "Test Test Test\rTest\r",
                "Moved Test Moved\rMoved Simple Moved\rMoved Simple ",
                "Row 1",
                "Row 1"
            });
    }





    /// <summary>
    /// WORDSNET-24018 Revisions reports no groups while there is a group.
    /// Checking for a special case where Row or Cell is revision.ParentNode for the table revision.
    /// </summary>
    @Test
    public void test24018() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\Test24018.docx");

        RevisionGroup group = doc.getRevisions().getGroups().get(0);
        for (Revision revision : doc.getRevisions())
            msAssert.areEqual(group, revision.getGroup());
    }


    /// <summary>
    /// Related with WORDSNET-24237
    /// Checks that a row splits one revision group.
    /// </summary>
    @Test
    public void test24237Rows() throws Exception
    {
        testRevisionGroups(
            "Model\\Revision\\RevisionGroups\\Test24237Rows.docx",
            new String[]
            {
                "Font: Bold, Complex Script Font: Bold",
                "Font: Bold, Complex Script Font: Bold"
            });
    }

    /// <summary>
    /// Related with WORDSNET-24237
    /// Checks that end table does not slit one revision group.
    /// However the Word splits a revision group at this case. Skip it for a while.
    /// </summary>
    @Test
    public void test24237Rows2() throws Exception
    {
        testRevisionGroups(
            "Model\\Revision\\RevisionGroups\\Test24237Rows2.docx",
            new String[]
            {
                "Font: Bold, Complex Script Font: Bold",
            });
    }





    /// <summary>
    /// WORDSNET-28093 Number of revision returned by Aspose.Words does not match MS Word (test_129.docx).
    /// Seems MS Word ignores complex script related attributes for paragraph break character.
    /// </summary>
    @Test
    public void test28093() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\Test28093.docx");

        RevisionGroupCollection groups = doc.getRevisions().getGroups();
        msAssert.areEqual(4, groups.getCount());

        checkGroup(groups.get(0), "-", "ipsum ");
        checkGroup(groups.get(1), "+", " This is added in target file");
        checkGroup(groups.get(2), "*", "Font: 9 pt, Italic, Underline");
        checkGroup(groups.get(3), "*", "Font: Italic");
    }




    /// <summary>
    /// WORDSNET-28098 Number of revision returned by Aspose.Words does not match MS Word (test_144.docx).
    /// ColumnSpacing is incorrectly read.
    /// </summary>
    @Test
    public void test28098() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\Test28098.docx");

        RevisionGroupCollection groups = doc.getRevisions().getGroups();
        msAssert.areEqual(1, countRevisionType(groups, RevisionType.DELETION));
        msAssert.areEqual(1, countRevisionType(groups, RevisionType.INSERTION));
        msAssert.areEqual(10, countRevisionType(groups, RevisionType.FORMAT_CHANGE));
    }

    /// <summary>
    /// Additional test for WORDSNET-28098.
    /// Original document is slightly modified to have section format revision.
    /// </summary>
    @Test
    public void test28098A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\Test28098A.docx");

        SectPr sectPr = doc.getFirstSection().getSectPr();
        msAssert.isTrue(sectPr.hasFormatRevision());
        msAssert.areEqual(720, sectPr.get(SectAttr.COLUMNS_SPACING));
        msAssert.areEqual(820, sectPr.getFormatRevision().getRevPr().get(SectAttr.COLUMNS_SPACING));

        msAssert.areEqual(11, countRevisionType(doc.getRevisions().getGroups(), RevisionType.FORMAT_CHANGE));
    }











    /// <summary>
    /// Relates to WORDSNET-28091.
    /// Simplified document, no DML fallback.
    /// </summary>
    @Test
    public void test28091B() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\Test28091B.docx");

        // MS Word: 2+, 2-, 0*
        RevisionGroupCollection groups = doc.getRevisions().getGroups();
        msAssert.areEqual(4, groups.getCount());

        checkGroup(groups.get(0), "-", "3Introduction...");
        checkGroup(groups.get(1), "+", "1Introduction...");
        checkGroup(groups.get(2), "+", "1Introduction...");
        checkGroup(groups.get(3), "-", "3Introduction...");
    }


    /// <summary>
    /// WORDSNET-28956 Revision group calculated incorrectly.
    /// Break group when story type is changed.
    /// </summary>
    @Test
    public void test28956() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\Test28956.docx");
        msAssert.areEqual(5, doc.getRevisions().getGroups().getCount());
    }

    /// <summary>
    /// Returns true if document has format revisions.
    /// </summary>
    private static boolean hasFormatRevisions(Document doc)
    {
        for (Revision revision : doc.getRevisions())
            if (revision.getRevisionType() == RevisionType.FORMAT_CHANGE)
                return true;

        return false;
    }

    /// <summary>
    /// Tests how document builds revision groups collection.
    /// </summary>
    private static void testRevisionGroups(String file, String balloon) throws Exception
    {
        testRevisionGroups(file, new String[] { balloon });
    }

    /// <summary>
    /// Tests how document builds revision groups collection.
    /// </summary>
    private static void testRevisionGroups(String file, String[] balloons) throws Exception
    {
        Document doc = TestUtil.open(file);
        testRevisionGroups(doc, balloons);
    }

    /// <summary>
    /// Tests how document builds revision groups collection.
    /// </summary>
    private static void testRevisionGroups(Document doc, String[] balloons)
    {
        msAssert.areEqual(balloons.length, doc.getRevisions().getGroups().getCount());

        for (int i = 0; i < balloons.length; i++)
            msAssert.areEqual(balloons[i], doc.getRevisions().getGroups().get(i).getText());
    }

    private static void checkGroup(RevisionGroup group, String revisionType, String text)
    {
        switch (gStringSwitchMap.of(revisionType))
        {
            case /*"+"*/0:
            case /*"-"*/1:
                msAssert.areEqual(("-".equals(revisionType) ? EditRevisionType.DELETION : EditRevisionType.INSERTION), group.getEditRevision().getType());
                break;

            case /*"*"*/2:
                msAssert.areEqual(RevisionType.FORMAT_CHANGE, group.getRevisionType());
                break;

            case /*"#"*/3:
                msAssert.areEqual(RevisionType.STYLE_DEFINITION_CHANGE, group.getRevisionType());
                break;
        }

        if (text.endsWith("..."))
        {
            String partialText = text.substring((0), (0) + (text.length() - 3));
            msAssert.isTrue(group.getText().startsWith(partialText), msString.format("Text should start with '{0}'", partialText));
        }
        else if (text.startsWith("..."))
        {
            String partialText = text.substring(3);
            msAssert.isTrue(group.getText().endsWith(partialText), msString.format("Text should end with '{0}'", partialText));
        }
        else
            msAssert.areEqual(text, group.getText());
    }

    private static int countRevisionType(RevisionGroupCollection groups, /*RevisionType*/int type)
    {
        int count = 0;
        for (RevisionGroup g : groups)
        {
            if (g.getRevisionType() == type)
                count++;
        }
        return count;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"+",
		"-",
		"*",
		"#"
	);

}

