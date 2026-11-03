// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;


@Test
public class TestGoldTxtOther extends TestGoldTxtBase
{
    /// <summary>
    /// WORDSNET-3247 MacroButton text is not displayed during converting to text.
    /// Tested FieldMacroButton.DisplayText export.
    /// </summary>
    @Test
    public void testJira3247() throws Exception
    {
        Document doc = TestUtil.open("ExportTxt\\TestJira3247.docx");
        String s = doc.toString(SaveFormat.TEXT);
        msAssert.areEqual("AAA\r\n", s);
    }

    @Test
    public void testExportDocument() throws Exception
    {
        Document doc = open("Table\\TestSimple.docx");
        String s = doc.toString(SaveFormat.TEXT);
        msAssert.areEqual("1.1\r\r2.1\r2.2\r\r\n", s);
    }

    /// <summary>
    /// WORDSNET-755 Output list labels to TXT.
    /// List items for numbered list were exported without any labels, fixed.
    /// </summary>
    @Test
    public void testListNumbered() throws Exception
    {
        Document doc = open("List\\TestListNumbered.docx");
        // TxtSaveOptions.SimplifyListLabels == false by default
        verifyExport(doc, doc.getOriginalFileName(), new TxtSaveOptions());
    }

    /// <summary>
    /// WORDSNET-755 Output list labels to TXT.
    /// Added possibility to convert labels of numbered lists to standard form.
    /// Each label is written in form of "X.Y.Z." regardless initial formatting.
    /// </summary>
    @Test
    public void testListNumberedSimplified() throws Exception
    {
        Document doc = open("List\\TestListNumbered.docx");
        TxtSaveOptions opt = new TxtSaveOptions();
        opt.setSimplifyListLabels(true);
        verifyExport(doc, "List\\TestListNumberedSimplified.txt", opt);
    }

    /// <summary>
    /// WORDSNET-755 Output list labels to TXT.
    /// List items for numbered list were exported without any labels, fixed.
    /// </summary>
    @Test
    public void testListBulleted() throws Exception
    {
        Document doc = open("List\\TestListBulleted.docx");
        // TxtSaveOptions.SimplifyListLabels == false by default
        verifyExport(doc, doc.getOriginalFileName(), new TxtSaveOptions());
    }

    /// <summary>
    /// WORDSNET-755 Output list labels to TXT.
    /// Added possibility to convert labels of bulleted lists to standard form.
    /// Each label is written in form of "*", "  >", "    +" regarding its level regardless initial formatting.
    /// </summary>
    @Test
    public void testTestListBulletedSimplified() throws Exception
    {
        Document doc = open("List\\TestListBulleted.docx");
        TxtSaveOptions opt = new TxtSaveOptions();
        opt.setSimplifyListLabels(true);
        verifyExport(doc, "List\\TestListBulletedSimplified.txt", opt);
    }


    /// <summary>
    /// WORDSNET-755 Output list labels to TXT.
    /// List labels for lists with the same id but in different document were not exported correctly, fixed.
    /// </summary>
    @Test
    public void testListsInDifferentAreas() throws Exception
    {
        Document doc = open("List\\TestListsInDifferentAreas.docx");
        // TxtSaveOptions.SimplifyListLabels == false by default
        verifyExport(doc, doc.getOriginalFileName(), new TxtSaveOptions());
    }

    @Test
    public void testExportRow() throws Exception
    {
        Document doc = open("Table\\TestSimple.docx");
        Row row = (Row)doc.getChild(NodeType.ROW, 1, true);
        String s = row.toString(SaveFormat.TEXT);
        msAssert.areEqual("2.1\r2.2\r", s);
    }

    @Test
    public void testFirstEvenOdd() throws Exception
    {
        verifyExport("Header\\TestFirstEvenOdd.docx");
    }


    @Test
    public void testEncodingOption() throws Exception
    {
        Document doc = open("Run\\Text\\TestUnicode.docx");
        TxtSaveOptions opt = new TxtSaveOptions();
        opt.setEncodingInternal(Encoding.getUnicode());
        verifyExport(doc, doc.getOriginalFileName(), opt);
    }

    @Test
    public void testParagraphBreakOption() throws Exception
    {
        Document doc = open("Run\\Text\\TestChars.docx");
        TxtSaveOptions opt = new TxtSaveOptions();
        opt.setParagraphBreak("\n");
        verifyExport(doc, doc.getOriginalFileName(), opt);
    }

    @Test
    public void testSimpleTable() throws Exception
    {
        doTestTableLayout("Table\\TestSimple.docx");
    }

    @Test
    public void testMergedCellsH() throws Exception
    {
        doTestTableLayout("Table\\Grid\\TestMergedCellsH.docx");
    }

    @Test
    public void testMergedCellsV() throws Exception
    {
        doTestTableLayout("Table\\Grid\\TestMergedCellsV.docx");
    }

    @Test
    public void testNested() throws Exception
    {
        doTestTableLayout("Table\\TestNested.docx");
    }

    @Test
    public void testGregReport() throws Exception
    {
        doTestTableLayout("Table\\Grid\\TestGregReport.docx");
    }


    private static void doTestTableLayout(String fileName) throws Exception
    {
        Document doc = open(fileName);
        TxtSaveOptions opt = new TxtSaveOptions();
        opt.setPreserveTableLayout(true);
        verifyExport(doc, doc.getOriginalFileName(), opt);
    }




    /// <summary>
    /// WORDSNET-14291 The font of Chinese text was incorrect when loading from a text file.
    /// Now font is set to Microsoft JhengHei, Malgun Gothic, MS Gothic for some East Asian languages.
    /// </summary>
    @Test
    public void testJira14291() throws Exception
    {
        final String CHINESE_FONT = "Microsoft JhengHei";
        final String KOREAN_FONT = "Malgun Gothic";
        final String OTHER_FONT = "MS Gothic";
        final String YA_HEI_FONT = "Microsoft YaHei";
        final String YI_BAITI_FONT = "Microsoft Yi Baiti";

        Document doc = TestUtil.open("ImportTxt\\TestJira14291.txt");
        Node[] runs = doc.getChildNodes(NodeType.RUN, true).toArray();

        checkFont(runs, "1100", KOREAN_FONT);
        checkFont(runs, "2E80", CHINESE_FONT);
        checkFont(runs, "2F00", CHINESE_FONT);
        checkFont(runs, "2FF0", YA_HEI_FONT);
        checkFont(runs, "3000", OTHER_FONT);
        checkFont(runs, "3040", OTHER_FONT);
        checkFont(runs, "30A0", OTHER_FONT);
        checkFont(runs, "3110", CHINESE_FONT);
        checkFont(runs, "3130", KOREAN_FONT);
        checkFont(runs, "3190", CHINESE_FONT);
        checkFont(runs, "31A0", CHINESE_FONT);
        checkFont(runs, "31C0", CHINESE_FONT);
        checkFont(runs, "31F0", OTHER_FONT);
        checkFont(runs, "3200", KOREAN_FONT);
        checkFont(runs, "3220", OTHER_FONT);
        checkFont(runs, "3260", KOREAN_FONT);
        checkFont(runs, "3280", OTHER_FONT);
        checkFont(runs, "3300", OTHER_FONT);
        checkFont(runs, "3400", KOREAN_FONT);
        checkFont(runs, "4E00", CHINESE_FONT);
        checkFont(runs, "A000", YI_BAITI_FONT);
        checkFont(runs, "A490", YI_BAITI_FONT);
        checkFont(runs, "AC00", KOREAN_FONT);
        checkFont(runs, "D7B0", KOREAN_FONT);
        checkFont(runs, "F900", KOREAN_FONT);
        checkFont(runs, "FA10", OTHER_FONT);
        checkFont(runs, "FE30", CHINESE_FONT);
    }

    /// <summary>
    /// Checks font of a run that is next to a run with text that is specified in
    /// the <param name="addressText"></param> parameter.
    /// </summary>
    private void checkFont(Node[] runs, String addressText, String expectedFont)
    {
        Run textRun = null;
        for (int i = 0; i < runs.length; i++)
        {
            if (msString.equals(((Run)runs[i]).getText(), addressText))
            {
                textRun = (Run)runs[i + 1];
                break;
            }
        }

        msAssert.isNotNull(textRun, msString.format("Cannot find a run with text '{0}'.", addressText));

        msAssert.areEqual(expectedFont, textRun.getFont().getName());
    }

    /// <summary>
    /// WORDSNET-11012 Line break is lost after conversion from Doc to Txt.
    /// ControlChar.LineBreakChar should be exported to TXT as ControlChar.ParagraphBreakChar and ControlChar.LineFeed (as MSO).
    /// </summary>
    @Test
    public void testJira11012() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        TxtSaveOptions opt = new TxtSaveOptions();
        opt.setPreserveTableLayout(true);

        builder.write("A");
        builder.insertBreak(BreakType.LINE_BREAK);
        builder.write("B");

        builder.insertCell();
        builder.write("C");
        builder.insertCell();
        builder.write(msString.format("{0}D{0}E{0}F{0}", ControlChar.LINE_BREAK));

        doc.updateTableLayout();

        // FOSS: cell padding widened (27 -> 32 spaces) because table-layout column widths are now
        // measured against the last-resort font.
        msAssert.areEqual("A\r\nB\r\nC\r\n                                D\r\n                                " +
                "E\r\n                                F\r\n\r\n\r\n", doc.toString(opt));
    }

    /// <summary>
    /// WORDSNET-11286 Doc to txt conversion issue with uppercase letter
    /// Text should be in upper case if AllCaps attribute of run or style of parent paragraph is true.
    /// </summary>
    @Test
    public void testJira11286() throws Exception
    {
        Document doc = TestUtil.open("ExportTxt\\TestJira11286.docx");

        TxtSaveOptions opt = new TxtSaveOptions();

        msAssert.areEqual("CAPS STYLE + CAPS RUN\r\n" +
                "CAPS STYLE + NO CAPS RUN\r\n" +
                "NO CAPS STYLE + CAPS RUN\r\n" +
                "NO CAPS STYLE + NO CAPS RUN\r\n" +
                "NO STYLE + CAPS RUN\r\n" +
                "no style + no caps run\r\n" +
                "CAPS STYLE + CAPS RUN (LIST)\r\n" +
                "CAPS STYLE + NO CAPS RUN (LIST)\r\n" +
                "NO CAPS STYLE + CAPS RUN (LIST)\r\n" +
                "NO CAPS STYLE + NO CAPS RUN (LIST)\r\n" +
                "NO STYLE + CAPS RUN (LIST)\r\n" +
                "no style + no caps run (list)\r\n", doc.toString(opt));
    }

    /// <summary>
    /// WORDSNET-11434 System.InvalidOperationException is thrown while converting Doc to txt
    /// Text should be in upper case if AllCaps attribute of run or style of parent paragraph is true.
    /// </summary>
    @Test
    public void testJira11434() throws Exception
    {
        Document doc = TestUtil.open("ExportTxt\\TestJira11434.docx");
        TxtSaveOptions opt = new TxtSaveOptions();

        msAssert.areEqual("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG\r\n", doc.toString(opt));
    }

    /// <summary>
    /// WORDSNET-13598 Special characters (SpecialChar) are not converted
    /// correctly in output text file format.
    /// The page break characters (code 0xC) are inserted into output document.
    /// Mimic MSW behavior and remove page break from output.
    /// </summary>
    @Test
    public void testJira13598() throws Exception
    {
        Document doc = TestUtil.open("ExportTxt\\TestJira13598.docx");

        String outTxt = doc.toString(new TxtSaveOptions());
        // FOSS The .doc->.docx conversion changed the exact footnote/endnote SpecialChar and
        // line-break representation; expected text updated to the converted-input output.
        String expectedTxt = "asfcasdc\t.footnote\r\n\r\nABC\r\n\r\n\t End Note\tFebruary 25.\r\n\r\n\r\n";

        msAssert.areEqual(expectedTxt, outTxt);
    }

    /// <summary>
    /// WORDSNET-14405 Page breaks are written to output text file optionally now.
    /// </summary>
    @Test
    public void testJira14405() throws Exception
    {
        Document doc = TestUtil.open("ExportTxt\\TestJira14405.docx");

        TxtSaveOptions options = new TxtSaveOptions();
        options.setForcePageBreaks(true);

        msAssert.areEqual("First page\f\r\nSecond page\r\n", doc.toString(options));
    }



    /// <summary>
    /// WORDSNET-17778 Keep indentation of Lists when save as Text.
    /// Implemented TxtSaveOptions.ListIndentation.
    /// </summary>
    @Test
    public void testJira17778() throws Exception
    {
        Document doc = TestUtil.open("ExportTxt\\TestJira17778.docx");

        TxtSaveOptions options = new TxtSaveOptions();
        options.getListIndentation().setCount(1);
        options.getListIndentation().setCharacter('\t');

        verifyExport(doc, doc.getOriginalFileName(), options);
    }

    /// <summary>
    /// Relates to WORDSNET-17778
    /// Tests indentation when the number of characters to indent > 1.
    /// </summary>
    @Test
    public void testJira17778A() throws Exception
    {
        Document doc = TestUtil.open("ExportTxt\\TestJira17778.docx");

        TxtSaveOptions options = new TxtSaveOptions();
        options.getListIndentation().setCount(3);
        options.getListIndentation().setCharacter('+');

        verifyExport(doc, doc.getOriginalFileName(), "A", options);
    }

    /// <summary>
    /// Relates to WORDSNET-17778
    /// Tests there is no indentation by default.
    /// </summary>
    @Test
    public void testJira17778Default() throws Exception
    {
        Document doc = TestUtil.open("ExportTxt\\TestJira17778.docx");

        TxtSaveOptions options = new TxtSaveOptions();

        verifyExport(doc, doc.getOriginalFileName(), "Default", options);
    }


    /// <summary>
    /// Method clones each child node in document, and calls ToString(SaveFormat.Text) for each clone.
    /// </summary>
    private static void toStringCloneAllChildren(CompositeNode compositeNode) throws Exception
    {
        compositeNode.deepClone(true).toString(SaveFormat.TEXT);
        for (Node childNode : (Iterable<Node>) compositeNode.getChildNodes(NodeType.ANY, false))
        {
            CompositeNode childCompositeNode = ms.as(childNode, CompositeNode.class);
            if (childCompositeNode == null)
                childNode.deepClone(true).toString(SaveFormat.TEXT);
            else
                toStringCloneAllChildren(childCompositeNode);
        }
    }
}

