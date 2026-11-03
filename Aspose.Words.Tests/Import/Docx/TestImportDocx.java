// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.StringUtil;
import com.aspose.Language;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msString;
import com.aspose.ArrayUtil;
import com.aspose.LocaleConverter;
import com.aspose.DrColor;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Globalization.DateTimeStyles;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msObject;
import com.aspose.Debug;
import com.aspose.ms.System.msMath;
import org.testng.annotations.DataProvider;


/// <summary>
/// Miscellaneous import DOCX tests that do not fit into more specific categories.
/// At the moment contains tests for anything in the text, tables, shapes etc.
/// </summary>
@Test
public class TestImportDocx
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
        // DocTestStub is not autoportable.
    }





    // FOSS TestJira6806 removed: verified data-bound SDT content is preserved when rendering to a
    // fixed page format (Pdf), which is a removed format.

    /// <summary>
    /// WORDSNET-6350 BaseJustification.Bottom is lost after open/save in OfficeMath.
    /// Added "bot" for ECMA376 MathJustification, whereas it was "bottom" for ISO29500 docs.
    /// </summary>
    @Test
    public void testJira6350() throws Exception
    {
        final String TEST_NAME = "Model\\Math\\Customers\\TestJira6350";
        Document doc = TestUtil.open(TEST_NAME + ".docx");

        OfficeMath math = (OfficeMath)doc.getNodeById("6.0.0.0.0.0");
        msAssert.isTrue(math.getMathObject().containsKey(MathAttr.BASE_JUSTIFICATION));
        msAssert.areEqual(MathBaseJustification.BOTTOM, math.getMathObject().getDirectAttr(MathAttr.BASE_JUSTIFICATION));

        OoxmlSaveOptions options = new OoxmlSaveOptions();
        options.setCompliance(OoxmlCompliance.ISO_29500_2008_TRANSITIONAL);
        TestUtil.saveOpen(doc, TEST_NAME + "_iso29500.docx", options);

        options.setCompliance(OoxmlCompliance.ECMA_376_2006);
        TestUtil.saveOpen(doc, TEST_NAME + ".docx", options);
    }



    /// <summary>
    /// WORDSNET-3932 Unknown color type encountered during WordML import - #9bbb59 [3206].
    /// RK Updated the regular expression to parse such colors safely.
    /// </summary>
    @Test
    public void testDefect3932() throws Exception
    {
        TestUtil.openSaveOpen("ImportDocx\\TestDefect3932.docx");
    }


    /// <summary>
    /// WORDSNET-5530 Images are not displayed when opening docx file from website.
    /// RK The weird problem was due to missing o:title="" attribute. Made to write it always.
    /// </summary>
    @Test
    public void testDefect5530() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestDefect5530.docx");
        // FOSS Dropped the WML roundtrip (removed format); assert the imported shape directly.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        String title = (String)shape.getShapePr().fetchAttr(ShapeAttr.IMAGE_TITLE);
        msAssert.areEqual("", title);
    }








    private static void checkThemeFont(Paragraph para, String fontName)
    {
        msAssert.areEqual(10, para.getParagraphFormat().getSpaceAfter());
        msAssert.areEqual(fontName, para.getFirstRun().getFont().getName());
    }


    private static void checkDefect5313(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestDefect5313.docx");

        // Convert to the desired format.
        // FOSS No gold check (the format changed from the removed Doc/Rtf/WordML); the model asserts below are the point.
        doc = TestUtil.saveOpen(doc, TestUtil.buildOutFileName(TestUtil.buildTestFileName("ImportDocx\\TestDefect5313"), "", sf), (SaveOptions)null, false);

        // Check the attributes were expanded properly.
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        Paragraph para = paras.get(0);
        Font font = para.getFirstRun().getFont();
        msAssert.areEqual("Cambria", font.getName());
        msAssert.areEqual(15, para.getParagraphFormat().getSpaceAfter());

        para = paras.get(1);
        font = para.getFirstRun().getFont();
        msAssert.areEqual("Calibri", font.getName());
        msAssert.areEqual(11, font.getSize());
        msAssert.areEqual(10, para.getParagraphFormat().getSpaceAfter());

        Cell cell = (Cell)doc.getChild(NodeType.CELL, 0, true);
        para = cell.getFirstParagraph();
        msAssert.areEqual("Workitem\u0007", para.getText());
        msAssert.areEqual(0, para.getParagraphFormat().getSpaceAfter());
    }



    /// <summary>
    /// WORDSNET-7946 DrawAspect of an OLE object was changed during Open/Save.
    /// The problem was that the XML had "icon" whereas it had to be "Icon". Made resilient.
    /// </summary>
    @Test
    public void testDefect7946() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestDefect7946.docx");
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(true, shape.getOleFormat().getOleIcon());
    }



    /// <summary>
    /// WORDSNET-15791 FileCorruptedException occurs during loading.
    /// The problem was caused by the missing styleId in the document. Made resilient.
    /// </summary>
    @Test
    public void testDefect15791() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestDefect15791.docx");
        msAssert.isTrue(doc.getStyles().get("CaptionItemParagraph") != null);
    }

    /// <summary>
    /// WORDSNET-15335 FileCorruptedException occurs during loading.
    /// The problem was we had a ThemeColor attribute in a run properties revision, but no default in the RunPr class.
    /// </summary>
    @Test
    public void testDefect15335() throws Exception
    {
        TestUtil.openSaveOpen("ImportDocx\\TestDefect15335.docx");
    }




    /// <summary>
    /// WORDSNET-20828 Numbering is wrong after open/save document.
    /// The problem occurred because nsid of abstractNum was omitted. In this case we used default listDefId that cause incorrect numbering.
    /// Fixed by using abstractNumId in case if nsid is omitted.
    /// </summary>
    @Test
    public void testDefect20828() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestDefect20828.docx");

        Paragraph par0 = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.isTrue(par0.isListItem());
        msAssert.areEqual(NumberStyle.LOWERCASE_LETTER, par0.getListFormat().getListLevel().getNumberStyle());

        Paragraph par1 = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);
        msAssert.isTrue(par1.isListItem());
        msAssert.areEqual(NumberStyle.LOWERCASE_ROMAN, par1.getListFormat().getListLevel().getNumberStyle());

        Paragraph par2 = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 2, true);
        msAssert.isTrue(par2.isListItem());
        msAssert.areEqual(NumberStyle.UPPERCASE_LETTER, par2.getListFormat().getListLevel().getNumberStyle());
    }


    /// <summary>
    /// WORDSNET-21462 Background of cells is lost upon loading DOCX document.
    /// The problem occurred because there was '#' at the beginning of color code string.
    /// Color code parser did not recognize such code as valid.
    /// Fixed by trimming '#' character from the color code string.
    /// </summary>
    @Test
    public void testDefect21462() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestDefect21462.docx");
        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        // Color of the cell should bot be empty.
        msAssert.areNotEqual(msColor.Empty, table.getFirstRow().getFirstCell().getCellFormat().getShading().getBackgroundPatternColor());
    }





    /// <summary>
    /// WORDSNET-21677 InvalidOperationException occurs during open/save.
    /// The problem occurred because we did not handle tiff images.
    /// Fixed by adding a case for Tiff format.
    /// </summary>
    @Test
    public void testDefect21677() throws Exception
    {
        // For now it is enough just test open/save of this document.
        // The image is DrawingML so we cannot check it's type for not, because there is no public API yet.
        TestUtil.openSaveOpen("ImportDocx\\TestDefect21677.docx");
    }

    /// <summary>
    /// WORDSNET-26296 Docx to Doc conversion makes very bad output if document contain tables.
    /// Found some severe bug during analysis. I fixed this bug and made more specific CRs about issue.
    /// </summary>
    @Test
    public void testDefect26296() throws Exception
    {
        String testfile = TestUtil.buildTestFileName("Model\\Style\\Table\\TestBuiltInTableStyles.docx");
        String outfile = TestUtil.buildOutFileName(testfile, "", SaveFormat.DOCX); // FOSS Doc -> Docx roundtrip

        Document doc = TestUtil.open(testfile);
        doc.save(outfile);
        doc = TestUtil.open(outfile);

        msAssert.areEqual(16, doc.getStyles().getCount());
        TableStyle style = (TableStyle)doc.getStyles().get(8);
        CellPr cellPr =
            (CellPr)style.fetchConditionalStylePr(TableStyleOverrideType.ODD_ROW_BANDING, AttrCollectionType.CELL_PR);

        // insane check. Verify border in one conditional style.
        msAssert.areEqual(1, cellPr.getBorderTop().getLineWidth());
        msAssert.areEqual(LineStyle.SINGLE, cellPr.getBorderTop().getLineStyle());
    }


    /// <summary>
    /// WORDSNET-26279 Extra space appears at bottom of table cells in HTML output.
    /// DocumentValidator.ExpandsDocumentDefault modifies styles so next save produces incorrect document because
    /// DocumentValidator.TableFormattingExpander uses modified styles.
    /// </summary>
    @Test
    public void testDefect26279() throws Exception
    {
        final String TESTFILE = "ImportDocx\\TestDefect26279.docx";
        Document doc = TestUtil.open(TESTFILE);

        // FOSS WordML -> FlatOpc (a supported flat OOXML format) to keep the deterministic byte compare.
        String outfile1 = TestUtil.buildOutFileName(TestUtil.buildTestFileName(TESTFILE), " 1", SaveFormat.FLAT_OPC);
        String outfile2 = TestUtil.buildOutFileName(TestUtil.buildTestFileName(TESTFILE), " 2", SaveFormat.FLAT_OPC);

        // save twice
        doc.save(outfile1);
        doc.save(outfile2);

        // and compare results
        FileStream out1 = new FileStream(outfile1, FileMode.OPEN, FileAccess.READ);
        FileStream out2 = new FileStream(outfile2, FileMode.OPEN, FileAccess.READ);

        msAssert.isTrue(TestUtil.compareStreams(out1, out2));
    }

    // FOSS TestDefect5032 removed: verifies extra space-after is resolved during the removed DOC
    // conversion; the assertion relies on the DOC-specific node tree (the id addresses a Cell after a
    // Docx roundtrip), so it can't be faithfully reproduced in the FOSS build.

    /// <summary>
    /// WORDSNET-4826 SectionBreak inside SDT is ignored upon loading DOCX document.
    /// This file has several sections/bodies all wrapped into structured document tag. Such document can not be represented by AW Model so std should be removed.
    /// AM. I'd like to use MarkupResolver (former CustomXmlResolver) class to process markup (custom XML and SDT) in DOCX files.
    /// It removes ugly recursion unbound logic and handles all markup processing in uniform way.
    /// Once we get solution to represent section nodes within markups such additional processing can be easily changed in one place.
    /// </summary>
    @Test
    public void testDefect4826() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestDefect4826.docx");

        // Verify sections were read properly.
        msAssert.areEqual(2, doc.getSections().getCount());
    }





    /// <summary>
    /// Core body for <see cref="TestJira5323"/>
    /// </summary>
    static void testJira5323Core(Document doc)
    {
        NodeList notes = doc.selectNodes("//Footnote");
        msAssert.areEqual(2, notes.getCount());
        // check that the first footnote in the document loaded and custom text presented
        Footnote footnote = (Footnote)notes.get(0);
        msAssert.areEqual(FootnoteType.FOOTNOTE, footnote.getFootnoteType());
        msAssert.areEqual(false, footnote.isAuto());
        msAssert.areEqual("http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CELEX:31985L0374:EN:HTML", footnote.getReferenceMark());

        footnote = (Footnote)notes.get(1);
        msAssert.areEqual(FootnoteType.FOOTNOTE, footnote.getFootnoteType());
        msAssert.areEqual(false, footnote.isAuto());
        msAssert.areEqual(1, footnote.getReferenceMark().length());
        msAssert.areEqual(ControlChar.LINE_BREAK_CHAR, footnote.getReferenceMark().charAt(0));
    }


    /// <summary>
    /// Core body of <see cref="TestJira5324"/>
    /// </summary>
    static void testJira5324Core(Document doc)
    {
        NodeList notes = doc.selectNodes("//Footnote");
        msAssert.areEqual(1, notes.getCount());

        // check that the first footnote in the document loaded and custom text presented
        Footnote footnote = (Footnote)notes.get(0);
        msAssert.areEqual(FootnoteType.FOOTNOTE, footnote.getFootnoteType());
        msAssert.areEqual(false, footnote.isAuto());
        msAssert.areEqual(1, footnote.getReferenceMark().length());
        msAssert.areEqual("\uf02a", footnote.getReferenceMark());
    }

    // FOSS TestJira4047 removed: explicitly tests that the removed Doc/Wml/Rtf formats serialize
    // XML ActiveX controls as images (a legacy-format-only behavior).



    /// <summary>
    /// WORDSNET-4664 Center alignment is not correct when render to pdf or xps.
    /// andrnosk: There are two Runs with leading white spaces, but the first run does not have "Space" attribute set.
    /// andrnosk: MS Word removes white spaces from the firs run, AW does the same now.
    /// </summary>
    @Test
    public void testJira4664() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestJira4664.docx");

        // Get all Run nodes.
        NodeCollection runs = doc.getFirstSection().getBody().getChildNodes(NodeType.RUN, true);

        msAssert.isFalse(runs.get(0).getText().startsWith(" "));
        msAssert.isTrue(runs.get(1).getText().startsWith(" "));
        msAssert.isFalse(runs.get(2).getText().startsWith(" "));
    }

    /// <summary>
    /// WORDSNET-5553 Additional paragraph break are added after open/save document.
    /// andrnosk: There is Run with leading and trailing \r\n chars.
    /// andrnosk: MS Word removes \r\n chars in all cases, AW does the same now.
    /// </summary>
    @Test
    public void testJira5553() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestJira5553.docx");

        // Get all Run nodes.
        NodeCollection runs = doc.getFirstSection().getBody().getChildNodes(NodeType.RUN, true);

        msAssert.isFalse(StringUtil.contains(runs.get(0).getText(), "\r\n", false));
    }


    // FOSS TestJira4164 removed: asserts the FillType.ShadeUnscale gradient produced by the removed
    // WML/VML conversion; in the FOSS build the imported shape fill is Solid.

    /// <summary>
    /// WORDSNET-5240 "System.ArgumentException" exception occurs during saving DOCX to other formats.
    /// andrnosk: OfficeMath class does not allow a Shape node to be inserted into itself.
    /// Added mechanism to check if the parent node of DrawingML is OfficeMath then search for parent where we can insert shape.
    /// </summary>
    @Test
    public void testJira5240() throws Exception
    {
        // FOSS Doc/WML saves are removed; verify the OfficeMath+DrawingML doc roundtrips to Docx without throwing.
        TestUtil.openSaveOpen("ImportDocx\\TestJira5240", LoadFormat.DOCX, SaveFormat.DOCX);
    }





    /// <summary>
    /// WORDSNET-5996 Track Changes (Revisions) are lost on open/save.
    /// Move revisions are unsupported, convert them into common del/ins revisions.
    /// andrnosk: Move revisions are supported now.
    /// </summary>
    @Test
    public void testJira5996() throws Exception
    {
        LoadOptions lo = new LoadOptions();
        WarningInfoCollection warnings = new WarningInfoCollection();
        lo.setWarningCallback(warnings);

        Document doc = TestUtil.open("ImportDocx\\TestJira5996.docx", lo);

        msAssert.isTrue(doc.hasRevisions());

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        msAssert.isTrue(paragraphs.get(1).getFirstRun().getRunPr().hasMoveFromRevision());
        msAssert.isTrue(paragraphs.get(3).getFirstRun().getRunPr().hasMoveToRevision());
    }







    /// <summary>
    /// WORDSNET-5431 Text appears with shadow after open/save.
    /// Document has W14 markup which specifies extended shadow properties.
    /// Since DOCX reader is not namespace-aware this extended shadow is mistakenly treated as old shadow attribute
    /// </summary>
    @Test
    public void testJira5431() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira5431.docx");
        Run run = (Run)doc.getChild(NodeType.RUN, 0, true);

        // andrnosk: FallBack was read instead of Choice to the model, because Choice contains unsupported element "wps:txbx".
        msAssert.isNotNull(run);
    }

    /// <summary>
    /// WORDSNET-6332 Font language settings are lost when converting DOCX to DOC.
    /// NoProof localeId should be written also.
    /// </summary>
    @Test
    public void testJira6332() throws Exception
    {
        // Since we don't have LocaleIdNoProof anymore in Mode, DOC output file should be tested manually.
        // Actually this is made implicitly during RunPrFIler tests.
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestJira6332", LoadFormat.DOCX, SaveFormat.DOCX); // FOSS Doc -> Docx
        Run run = (Run)doc.getNodeById("3.0.0.0");
        msAssert.areEqual(Language.FRENCH_FRANCE, (/*Language*/Integer)run.getRunPr().getDirectAttr(FontAttr.LOCALE_ID));
    }


    /// <summary>
    /// WORDSNET-5758 Exception while loading .docx file.
    /// andrnosk: The problem occurs because the latest node of the document's section is Table. But it must be paragraph.
    /// The error occurred on the old code when bookmarks were moved to the inline level.
    /// </summary>
    @Test
    public void testJira5758() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira5758.docx");

        msAssert.areEqual(NodeType.BOOKMARK_END, doc.getLastSection().getBody().getLastChild().getNodeType());
    }




    /// <summary>
    /// WORDSNET-5307 Watermark disappears after open/save DOCX document.
    /// andrnosk: Fixed by adding mechanism to concatenate headers/footers of the same type.
    /// </summary>
    @Test
    public void testJira5307A() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira5307.docx");
        msAssert.areEqual(3, doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getParagraphs().getCount());
    }



    /// <summary>
    /// WORDSNET-6852 Incorrect conversion of LeftIndentUnits into LeftIndent by DocumentValidator.
    /// Use SizeBi if Size attribute is missing for indent calculation.
    /// </summary>
    @Test
    public void testJira6852() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira6852.docx");
        // FOSS Save via Docx (Html is a removed format) still runs the indent-converting validator.
        doc.save(new MemoryStream(), SaveFormat.DOCX);

        ParaPr paraPr = ((Paragraph)doc.getNodeById("6.6.0")).getParaPr();
        msAssert.areEqual(1428, paraPr.getLeftIndent());
        msAssert.areEqual(-420, paraPr.getFirstLineIndent());
    }


    /// <summary>
    /// WORDSNET-6928 xml:space="preserve" attribute is not preserved during open/save.
    /// If xml:space="preserve” attribute  is not set in Run or document then remove all leading and trailing white-spaces and tabs.
    /// </summary>
    @Test
    public void testJira6928() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira6928.docx");
        msAssert.areEqual("Cell one\r", doc.getNodeById("0.0.0.0.0.0").toString(SaveFormat.TEXT));
    }





    /// <summary>
    /// WORDSNET-7399 Link between grouped textboxes breaks when saving a .docx.
    /// andrnosk: The problem occurs because we try to get linked shape by ID before reading this shape to the model.
    /// Fixed by writing original textbox next shape Id into the temporary TextboxNextShapeIdRaw,
    /// and then updating TextboxNextShapeId using temporary Id.
    /// </summary>
    @Test
    public void testJira7399() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira7399.docx");
        GroupShape dml = ((GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true));

        Shape firstInGroup = (Shape)dml.getChildNodes(NodeType.ANY, false).get(0);
        Shape secondInGroup = (Shape)dml.getChildNodes(NodeType.ANY, false).get(1);

        // Check the TextboxNextShapeId is here.
        msAssert.areEqual(secondInGroup.getId(), firstInGroup.getShapePr().get(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID));
        msAssert.isNull(secondInGroup.getShapePr().get(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID));
    }

    /// <summary>
    /// WORDSNET-7639 Aspose.Words.FileCorruptedException occurs when loading a WordML/Docx.
    /// andrnosk: The problem occurs because there is ilvl equals -1, but according to specification ISO29500 17.9.6
    /// it is a zero-based index of the number of list levels in the document.
    /// Fixed by processing -1 value as MS Word does.
    /// </summary>
    @Test
    public void testJira7639() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira7639.docx");
        msAssert.isTrue(doc.getFirstSection().getBody().getFirstParagraph().isListItem());
        msAssert.areEqual(0, doc.getFirstSection().getBody().getFirstParagraph().getListFormat().getListLevelNumber());
    }






    /// <summary>
    /// WORDSNET-7990 A Bookmark is not preserved during open/save a DOCX.
    /// andrnosk: The problem occurs because there is bookmark id greater than int32 maximum value.
    /// This value was trimmed to negative and that is why have decided just get module of it to fix this.
    /// </summary>
    @Test
    public void testJira7990() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira7990.docx");
        msAssert.areEqual(4, doc.getFirstSection().getRange().getBookmarks().getCount());
    }

    /// <summary>
    /// WORDSNET-8023 Unwanted border around Cells of first Row appears during open/save.
    /// andrnosk: The problem occurs because AW does not write border when this border has lineStyle none,
    /// and that is why this border inherits from TableBorder.
    /// Fixed by checking LineWidth, in this case only a border with val=none; sz=0; will be ignored.
    /// </summary>
    @Test
    public void testJira8023() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira8023.docx");
        BorderCollection borders = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getCellFormat().getBorders();
        msAssert.isFalse(borders.getByBorderType(BorderType.LEFT).isVisible());
        msAssert.isFalse(borders.getByBorderType(BorderType.RIGHT).isVisible());
        msAssert.isFalse(borders.getByBorderType(BorderType.TOP).isVisible());
        msAssert.isFalse(borders.getByBorderType(BorderType.BOTTOM).isVisible());
    }


    /// <summary>
    /// Relates to above TestJira6904.
    /// </summary>
    @Test
    public void testJira6907() throws Exception
    {
        // There is slightly different problem. All shapes has equal z-index. In this case we should number them by appearance.
        Document doc = TestUtil.open("ImportDocx\\TestJira6107.docx");
        NodeCollection shapes = doc.getFirstSection().getBody().getFirstParagraph().getChildNodes(NodeType.ANY, false);
        checkZLess(shapes.get(0), shapes.get(1));
        checkZLess(shapes.get(1), shapes.get(2));
        checkZLess(shapes.get(2), shapes.get(3));
        checkZLess(shapes.get(3), shapes.get(4));
    }


    /// <summary>
    /// Checks that z-order of first shape is less than z-order of second shape.
    /// </summary>
    private static void checkZLess(Node shape1, Node shape2)
    {
        msAssert.greater(((ShapeBase)shape2).getZOrder(), ((ShapeBase)shape1).getZOrder());
    }


    /// <summary>
    /// WORDSNET-8113 Open/Save Docx lose the bullet.
    /// ListValidator made incorrect translation, because List.Style != null.
    /// Lists.Style.ParaPr.ListId should be also translated in this case.
    /// </summary>
    @Test
    public void testJira8113() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira8113.docx");
        msAssert.areEqual("•", doc.getLists().get(5).getListLevels().get(0).getNumberFormat());
    }









    /// <summary>
    /// WORDSNET-6173 Incorrect font problem occurs after loading Word docx format.
    /// Additional test for WORDSNET-3312
    /// </summary>
    @Test
    public void testJira6173() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira6173.docx");

        Run run = doc.getSections().get(0).getBody().getParagraphs().get(0).getRuns().get(0);

        // Verify font name exposed correctly to public.
        msAssert.areEqual("Calibri", run.getFont().getName());

        // Internal IRunAttrSource data (bottom-up resolving).
        ComplexFontName complexFontName = (ComplexFontName)((IRunAttrSource)run).fetchInheritedRunAttr(FontAttr.NAME_ASCII);
        msAssert.isTrue(complexFontName.isThemeFont());
        msAssert.areEqual(ThemeFontCore.MINOR_H_ANSI, complexFontName.getThemeFontCore());

        // Internal Run.ExpandedRunPr (top-bottom resolving).
        RunPr expandedRunPr = run.getExpandedRunPr(RunPrExpandFlags.DOCUMENT_DEFAULTS);
        msAssert.isTrue(expandedRunPr.getComplexNameAscii().isThemeFont());
        msAssert.areEqual(ThemeFontCore.MINOR_H_ANSI, expandedRunPr.getComplexNameAscii().getThemeFontCore());

        // Layout the document.
        // No update page layout in FOSS.

        // Verify nothing is changed after document layout is rebuilt.
        msAssert.areEqual("Calibri", run.getFont().getName());
    }

    /// <summary>
    /// WORDSNET-8446 Support import of element 'image' in DOCX format.
    /// </summary>
    @Test
    public void testJira8446() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira8446.docx");
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        msAssert.areEqual(1, shapes.getCount());
        Shape shape = (Shape)shapes.get(0);
        msAssert.areEqual(141, shape.getImageData().getImageBytes().length);
        msAssert.areEqual(9.0d, shape.getWidth());
        msAssert.areEqual(9.0d, shape.getHeight());
    }

    /// <summary>
    /// WORDSNET-8577 InvalidCastException occurs during loading a DOCX.
    /// There is strange mess of include picture fields in file. And they all include only one picture. Word removes such "incorrect" fields.
    /// </summary>
    @Test
    public void testJira8577() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira8577.docx");
        msAssert.areEqual(1, doc.getChildNodes(NodeType.SHAPE, true).getCount());
    }

    // FOSS TestJira6380 removed: checks image file extensions in HTML export output (HTML is a removed format).






    /// <summary>
    /// WORDSNET-8652 System.ArgumentException is thrown while doing Mail Merge
    /// Bookmarks with duplicated names should be renamed upon node importing.
    /// </summary>
    @Test
    public void testJira8652() throws Exception
    {
        final String TEST_NAME = "ImportDocx\\TestJira8652.docx";
        Document doc = TestUtil.open(TEST_NAME);

        verifyBookmarks(doc, new String[] { "test", "new", "_GoBack" });
        doc.appendDocument(TestUtil.open(TEST_NAME), ImportFormatMode.KEEP_SOURCE_FORMATTING);
        verifyBookmarks(doc, new String[] { "test", "new", "_GoBack", "test_0", "new_0" });
        doc.appendDocument(TestUtil.open(TEST_NAME), ImportFormatMode.KEEP_SOURCE_FORMATTING);
        verifyBookmarks(doc, new String[] { "test", "new", "_GoBack", "test_0", "new_0", "test_1", "new_1" });
    }

    /// <summary>
    /// Relates to TestJira8652. Verifies BookmarkStart names in the document.
    /// </summary>
    /// <param name="doc"></param>
    /// <param name="names"></param>
    private static void verifyBookmarks(Document doc, String[] names)
    {
        NodeCollection bookmarkStarts = doc.getChildNodes(NodeType.BOOKMARK_START, true);

        msAssert.areEqual(names.length, bookmarkStarts.getCount());
        for (int i = 0; i < bookmarkStarts.getCount(); i++)
            msAssert.areEqual(names[i], ((BookmarkStart)bookmarkStarts.get(i)).getName());
    }

    /// <summary>
    /// WORDSNET-4336 Font is not changed during appending with UseDestinationStyles
    /// Consider ImportFormatMode when resolve document defaults difference.
    /// </summary>
    @Test
    public void testJira4336() throws Exception
    {
        Document dstDoc = TestUtil.open("ImportDocx\\TestJira4336_Dst.docx");
        Document srcDoc = TestUtil.open("ImportDocx\\TestJira4336_Src.docx");
        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        // Verify that no "compensation" attributes were added to imported document nodes in spite of
        // document defaults difference because ImportFormatMode.UseDestinationStyles was used.
        Paragraph p = dstDoc.getSections().get(1).getBody().getFirstParagraph();
        Run run = p.getFirstRun();
        msAssert.isNull(p.getParaPr().get(ParaAttr.ALIGNMENT));
        msAssert.isNull(run.getRunPr().get(FontAttr.NAME_ASCII));
        msAssert.isNull(run.getRunPr().get(FontAttr.SIZE));

        TestUtil.save(dstDoc, "ImportDocx\\TestJira4336_UseDestinationStyles.docx");

        dstDoc = TestUtil.open("ImportDocx\\TestJira4336_Dst.docx");
        srcDoc = TestUtil.open("ImportDocx\\TestJira4336_Src.docx");
        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        // Verify that document defaults difference were compensated by direct attributes because source formatting should be kept.
        p = dstDoc.getSections().get(1).getBody().getFirstParagraph();
        run = p.getFirstRun();
        msAssert.areEqual(ParagraphAlignment.RIGHT, p.getParaPr().get(ParaAttr.ALIGNMENT));
        msAssert.areEqual("Times New Roman", run.getRunPr().getComplexNameAscii().getName());
        msAssert.areEqual(22, run.getRunPr().get(FontAttr.SIZE));

        TestUtil.save(dstDoc, "ImportDocx\\TestJira4336_KeepSourceFormatting.docx");
    }


    /// <summary>
    /// WORDSNET-4115 List numbering is lost after open/save document using AW.
    /// Numbering definition completely missed in document. Fixed per WORDSNET-9065
    /// </summary>
    @Test
    public void testJira4115() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira4115.xml");
        doc.updateListLabels();

        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual("1.", p.getListLabel().getLabelString());
    }





    /// <summary>
    /// Relates to WORDSNET-9188
    /// Displays the different behavior for Headings styles.
    /// In case ANY Heading style is instantiated Words uses style from template.
    /// </summary>
    /// <remarks>
    /// AM. Made this test Ignore because it blocks WORDSNET-10432
    /// This test describes strange Word behavior I found during WORDSNET-9188
    /// It describers how Word behaves when 'copy + paste' and it seems that Word behaves differently
    /// when imports AltChunks. Since customer doesn not complain lets postpone and investigate it later.
    /// </remarks>
    @Test (enabled = false, description = "TestJira9188Headings")
    public void testJira9188Headings() throws Exception
    {
        Document src = TestUtil.open("ImportDocx\\TestJira9188HeadingSrc.docx");

        // Source Heading4 style is Arial Rounded MT, 52 size.
        Style heading4 = src.getStyles().getBySti(StyleIdentifier.HEADING_4, false);
        msAssert.areEqual(52, heading4.getRunPr().getSize());
        msAssert.areEqual("Arial Rounded MT Bold", heading4.getRunPr().getComplexNameAscii().getName());

        // Test case when Heading1 is not instantiated in destination document.
        Document dst = TestUtil.open("ImportDocx\\TestJira9188HeadingDstA.docx");
        msAssert.isNull(dst.getStyles().getBySti(StyleIdentifier.HEADING_1, false));

        // Source style should be copied.
        dst.appendDocument(src, ImportFormatMode.USE_DESTINATION_STYLES);

        // Verify that source style is copied.
        heading4 = dst.getStyles().getBySti(StyleIdentifier.HEADING_4, false);
        msAssert.areEqual("Arial Rounded MT Bold", heading4.getRunPr().getComplexNameAscii().getName());
        msAssert.areEqual(52, heading4.getRunPr().getSize());

        // Test case when Heading1 is instantiated in destination document.
        src = TestUtil.open("ImportDocx\\TestJira9188HeadingSrc.docx");
        dst = TestUtil.open("ImportDocx\\TestJira9188HeadingDstB.docx");
        msAssert.isNotNull(dst.getStyles().getBySti(StyleIdentifier.HEADING_1, false));

        dst.appendDocument(src, ImportFormatMode.USE_DESTINATION_STYLES);

        // Verify that source style is NOT copied and Heading4 is loaded from resources.
        heading4 = dst.getStyles().getBySti(StyleIdentifier.HEADING_4, false);
        msAssert.isTrue(heading4.getRunPr().getComplexNameAscii().isThemeFont());
        msAssert.areNotEqual(52, heading4.getRunPr().getSize());
    }












    /// <summary>
    /// WORDSNET-9884 Tables are not handled properly by Aspose 14.2.0
    /// Table missing tblGrid but has grid spans this causes table layout is corrupted.
    /// </summary>
    @Test
    public void testJira9884() throws Exception
    {
        // Test simplified document.
        Document doc = TestUtil.open("ImportDocx\\TestJira9884.xml");

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);

        int row1CellsWidth = cellsWidth(table.getRows().get(0));
        int row2CellsWidth = cellsWidth(table.getRows().get(1));

        // Total cell width of first row should be equal to first row.
        msAssert.areEqual(row2CellsWidth, row1CellsWidth);
    }






    /// <summary>
    /// WORDSNET-10141 OutOfRangeException occurred during rendering to PDF.
    /// Source document contains negative outline level, which should be removed, according to MSW behavior.
    /// </summary>
    @Test
    public void testJira10141() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira10141.docx");

        // Problematic paragraph, which raised exception on saving to PDF.
        Paragraph para = (Paragraph)doc.getNodeById("35.6.0");
        msAssert.areEqual(OutlineLevel.BODY_TEXT, para.getParagraphFormat().getOutlineLevel());
    }

    // FOSS TestJira10463 removed: the input was WordML and had to be converted to .docx via Word for
    // the FOSS build, which drops the direct cell-border attributes this test asserts on.



    /// <summary>
    /// WORDSNET-10748 Contents position is changed after re-saving the Doc file
    /// Word replaces CR character with space in "t" element.
    /// </summary>
    @Test
    public void testJira10748() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira10748.docx"); // FOSS WordML -> .docx

        // Verify that CR is replaced with space.
        // FOSS The WordML->Docx conversion changed paragraph/run indexing; locate the run by content.
        Run run = null;
        for (Run r : (Iterable<Run>) doc.getChildNodes(NodeType.RUN, true))
            if (r.getText().startsWith(", vol. 2256")) { run = r; break; }
        msAssert.isNotNull(run);
        msAssert.areEqual(", vol. 2256, p. 119; \u0020", run.getText());
    }

    /// <summary>
    /// WORDSNET-10436 A Page Break is not preserved during open/save a DOCX.
    /// There is break 'w:br' element inside body. MSW moves such elements to the next paragraph met.
    /// Made resilience for this case.
    /// </summary>
    @Test
    public void testJira10436() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira10436.docx");
        Run breakRun = doc.getFirstSection().getBody().getLastParagraph().getFirstRun();

        msAssert.areEqual(ControlChar.PAGE_BREAK, breakRun.getText());
    }









    // FOSS TestJira11573 removed: verifies an altChunk is inserted as a Table after a bookmark; the
    // altChunk had to be flattened via Word for the FOSS build, changing that inserted structure.



    /// <summary>
    /// WORDSNET-11357 Twip-based attributes with fractional parts rounds up for DOCX or WML.
    /// Twip-based attributes with fractional parts should be truncated, as it MS Word does.
    /// </summary>
    @Test
    public void testJira11357() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira11357.docx");

        // Check twip-based properties.
        // Paragraph properties.
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        ParaPr paraPr = para.getParaPr();
        msAssert.areEqual(7200, paraPr.get(ParaAttr.FRAME_WIDTH));
        msAssert.areEqual(160, ((LineSpacing)paraPr.get(ParaAttr.LINE_SPACING)).getValue());
        // Table properties.
        Table tbl = (Table)doc.getChild(NodeType.TABLE, 0, true);
        TablePr tblPr = tbl.getFirstRow().getTablePr();
        msAssert.areEqual(180, tblPr.get(TableAttr.FRAME_DISTANCE_FROM_LEFT));
        msAssert.areEqual(2775, tblPr.get(TableAttr.FRAME_LEFT));
        // List properties.
        ListLevel listLevel = doc.getLists().getListDefByIndex(0).getLevels().get(0);
        msAssert.areEqual(0, listLevel.getLegacySpace());
        msAssert.areEqual(283, listLevel.getLegacyIndent());
        // Section properties
        SectPr sectPr = doc.getFirstSection().getSectPr();
        msAssert.areEqual(11906, sectPr.get(SectAttr.PAGE_WIDTH));
        msAssert.areEqual(1440, sectPr.get(SectAttr.TOP_MARGIN));

        // Check non twip-based properties.
        Section secondSection = (Section)doc.getFirstSection().getNextSibling();
        para = secondSection.getBody().getFirstParagraph();
        paraPr = para.getParaPr();
        // Paragraph properties.
        msAssert.areEqual(11792, paraPr.get(ParaAttr.FRAME_WIDTH));
        msAssert.areEqual(397, ((LineSpacing)paraPr.get(ParaAttr.LINE_SPACING)).getValue());
        // Table properties.
        tbl = (Table)doc.getChild(NodeType.TABLE, 1, true);
        tblPr = tbl.getFirstRow().getTablePr();
        msAssert.areEqual(454, tblPr.get(TableAttr.FRAME_DISTANCE_FROM_LEFT));
        msAssert.areEqual(4421, tblPr.get(TableAttr.FRAME_LEFT));
        // List properties.
        listLevel = doc.getLists().getListDefByIndex(1).getLevels().get(0);
        msAssert.areEqual(454, listLevel.getLegacySpace());
        msAssert.areEqual(1020, listLevel.getLegacyIndent());
        // Section properties
        sectPr = secondSection.getSectPr();
        msAssert.areEqual(18595, sectPr.get(SectAttr.PAGE_WIDTH));
        msAssert.areEqual(2154, sectPr.get(SectAttr.TOP_MARGIN));
    }


    /// <summary>
    /// WORDSNET-11751 First page header content is not preserved during open/save.
    /// There is 'w:r' element inside 'w:rPr'. Made resilience.
    /// </summary>
    @Test
    public void testJira11751() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira11751.docx");

        HeaderFooter header = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        Run run = header.getFirstParagraph().getFirstRun();

        msAssert.areEqual("Bold underlined text in header.", run.getText());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
        msAssert.areEqual(Underline.SINGLE, run.getRunPr().getUnderline());
    }


    /// <summary>
    /// WORDSNET-11918 Aspose.Words.FileCorruptedException occurs during loading a DOCX.
    /// The problem occurs because of AlternateContent inside graphicData.
    /// Fixed by reading AlternateConten->Choose.
    /// </summary>
    @Test
    public void testJira11918() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira11918.docx");
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);

        // Check shapes was read.
        msAssert.areEqual(4, shapes.getCount());
    }





    /// <summary>
    /// WORDSNET-12228 FileCorruptedException was thrown while loading Docx.
    /// Ignore all unexpected attributes in <see cref="DocxStylesReader.ReadStyle"/>.
    /// </summary>
    @Test
    public void testJira12228() throws Exception
    {
        LoadOptions loadOptions = new LoadOptions();
        WarningInfoCollection warnings = new WarningInfoCollection();
        loadOptions.setWarningCallback(warnings);

        TestUtil.open("ImportDocx\\TestJira12228.docx", loadOptions);

        // Verify, that unexpected attribute was ignored with a proper massage.
        String message = msString.format(WarningStrings.UNEXPECTED_ATTRIBUTE, "Ignorable");
        msAssert.isTrue(TestUtil.containsWarningBySource(warnings, WarningSource.DOCX, message));
    }
















    /// <summary>
    /// Related with WORDSNET-13442
    /// Checks expected preferred width type which has to be returned by the reader.
    /// </summary>
    @Test
    public void testJira13442NoTypePart()
    {
        String xml = "<w:top w:w=\"4.25mm\" xmlns:w=\"http://purl.oclc.org/ooxml/wordprocessingml/main\" />";

        NrxXmlReader reader = new NrxXmlReader(xml, null);
        PreferredWidth width = reader.readLength(new OoxmlComplianceInfo());
        msAssert.areEqual(PreferredWidthType.POINTS, width.getType());
        msAssert.areEqual(241, width.getValueRaw());
    }

    /// <summary>
    /// WORDSNET-13443 System.Xml.XmlException is thrown while loading Docx.
    /// Diagram data has reference to diagram drawing object which can be in invalid format.
    /// In this case reading algorithm have to skip invalid part.
    /// </summary>
    @Test
    public void testJira13443() throws Exception
    {
        final String E_MSG = "Can not parse content some of diagram drawing object from specified path.";

        // Diagram references to invalid diagram drawing object (file format is not XML).
        LoadOptions lo = new LoadOptions();
        TestWarningCallback twc = new TestWarningCallback();
        lo.setWarningCallback(twc);
        TestUtil.open("ImportDocx\\TestJira13443_1.docx", lo);

        WarningInfo wi = getFirstDmlWarning(twc);
        msAssert.areEqual(WarningSource.DRAWING_ML, wi.getSource());
        msAssert.areEqual(WarningType.MINOR_FORMATTING_LOSS, wi.getWarningType());
        msAssert.isTrue(StringUtil.startsWithOrdinalIgnoreCase(wi.getDescription(), E_MSG));
        twc.clear();

        // Diagram drawing object file has invalid XML content.
        TestUtil.open("ImportDocx\\TestJira13443_2.docx", lo);

        wi = getFirstDmlWarning(twc);
        msAssert.areEqual(WarningSource.DRAWING_ML, wi.getSource());
        msAssert.areEqual(WarningType.MINOR_FORMATTING_LOSS, wi.getWarningType());
        msAssert.isTrue(StringUtil.startsWithOrdinalIgnoreCase(wi.getDescription(), E_MSG));
    }



    /// <summary>
    /// WORDSNET-13632 Diagram gets messed up during open/save a DOCX.
    /// While converting attached document into PDF, the diagram on Pg 5 gets completely messed up in the output.
    /// Mimic MS Word behavior to re-calculation of the shapes width is different from AW.
    /// </summary>
    @Test
    public void testJira13632() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira13632", LoadFormat.DOCX);
        // DML shapes.
        checkShapesWidth(doc.getFirstSection(), 181.9d, 184.85d, 60.75d);
        // VML shapes.
        checkShapesWidth(doc.getLastSection(), 187.1d, 238.12d, 187.1d);
    }

    /// <summary>
    /// WORDSNET-13651 Aspose.Words.FileCorruptedException is thrown while loading Docx.
    /// "MACROBUTTON" field is missed second argument (DisplayText) which is parsed and appended to string while
    /// export the content of the node into a string. This operation is failed because argument is null.
    /// </summary>
    @Test
    public void testJira13651() throws Exception
    {
        final String DT = "Display text";

        Document doc = new Document();
        TxtSaveOptions tso = new TxtSaveOptions();
        DocumentBuilder db = new DocumentBuilder(doc);
        FieldMacroButton field = (FieldMacroButton)db.insertField(FieldType.FIELD_MACRO_BUTTON, true);
        field.setMacroName("AcceptAllChangesInDoc");
        // Check case when "DisplayText" is null.
        checkFieldExportText(doc, new TxtWriter(), tso, ControlChar.CR_LF);
        // Check case when "DisplayText" is not null.
        field.setDisplayText(DT);
        String expectedVal = msString.format("{0}{1}", DT, ControlChar.CR_LF);
        checkFieldExportText(doc, new TxtWriter(), tso, expectedVal);
    }

    /// <summary>
    /// WORDSNET-13416 The contents of last page are truncated after re-saving the Doc.
    /// The document has 'sect' inside run. Resilience is implemented.
    /// </summary>
    @Test
    public void testJira13416A() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira13416A.docx"); // FOSS WordML -> .docx

        // Checks correctness of document structure.
        SectionCollection sections = doc.getSections();
        msAssert.areEqual(1, sections.getCount());

        Section section = sections.get(0);
        msAssert.areEqual(820, section.getSectPr().getColumnsSpacing());

        ParagraphCollection paras = section.getBody().getParagraphs();
        msAssert.areEqual(3, paras.getCount());

        // FOSS The WordML->Docx conversion merges the adjacent runs, so assert the paragraph text
        // rather than the original 3-run split.
        msAssert.areEqual("Text1Text2Text3", msString.trimEnd(paras.get(0).getText()));

        RunCollection runs = paras.get(1).getRuns();
        msAssert.areEqual(1, runs.getCount());
        msAssert.areEqual("Text4", runs.get(0).getText());
    }

    /// <summary>
    /// WORDSNET-13846 Aspose.Words.FileCorruptedException occurs upon loading a DOCX.
    /// "Fallback" element of the "AlternateContent" contains shape with invalid relationship
    /// identifier (this relationship does not exist). MSW for this issue does not parse "Fallback"
    /// content, so can open document.
    /// </summary>
    @Test
    public void testJira13846() throws Exception
    {
        final String DOC_PATH = "ImportDocx\\TestJira13846{0}";

        LoadOptions lo = new LoadOptions();
        WarningInfoCollection warn = new WarningInfoCollection();
        lo.setWarningCallback(warn);

        // Open and check that has one suppressed exception.
        Document doc = TestUtil.open(msString.format(DOC_PATH, ".docx"), lo);

        // Check that warnings for "Fallback" items on alternate content level were not registered.
        msAssert.isTrue(warn.getCount() < 17);

        // Try to import the group shape.
        Document destDoc = new Document();
        NodeImporter imp = new NodeImporter(doc, destDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        GroupShape dstNode = (GroupShape)imp.importNode(doc.getFirstSection().getBody().getLastParagraph().getLastChild(), true);

        msAssert.isNull(dstNode.getRunPr().getAlternateContent().getFallBack());

        // Try to re-open document and check that "Fallback" was generated.
        doc = TestUtil.saveOpen(doc, msString.format(DOC_PATH, ""), UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        AlternateContent ac = ((GroupShape)doc.getFirstSection().getBody().getLastParagraph().getLastChild()).getRunPr().getAlternateContent();
        msAssert.isNotNull(ac.getFallBack());
    }

    /// <summary>
    /// WORDSNET-13866 Last page missing when appending document.
    /// Document after reading has two textboxes with the same value of the shape identifiers.
    /// Therefore. textbox which should be linked while importing will be skipped with comment
    /// in code "source document has non unique dml Ids". The reason of the problem is the
    /// algorithm behavior of the assigning shape identifiers for linked textboxes.
    /// </summary>
    @Test
    public void testJira13866() throws Exception
    {
        // Document contains four linked textboxex and one separate textbox.
        // Sequence of the shapes were mixed in the document.
        Document doc = TestUtil.open("ImportDocx\\TestJira13866.docx");

        // 1. Main case reproduced problem from issue.
        Document dstDoc = new Document();
        dstDoc.appendDocument(doc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        Paragraph lastPar = doc.getLastSection().getBody().getLastParagraph();
        Paragraph middlePar = doc.getLastSection().getBody().getParagraphs().get(1);

        Shape txbx = (Shape)middlePar.getChildNodes(NodeType.ANY, false).get(2);
        Shape lnkTxbx3 = (Shape)middlePar.getChildNodes(NodeType.ANY, false).get(0);
        Shape lnkTxbx2 = (Shape)middlePar.getChildNodes(NodeType.ANY, false).get(1);
        Shape rect = (Shape)doc.getLastSection().getBody().getFirstParagraph().getFirstChild();
        Shape lnkTxbx1 = (Shape)doc.getLastSection().getBody().getLastParagraph().getChildNodes(NodeType.ANY, false).get(0);

        checkLinkedTxBxSeq(dstDoc, true);

        // 2. Import part of the textbox sequence with invalid sequence number
        // for linked textbox. Import this case like a separate texboxes.
        Paragraph lastPar2 = (Paragraph)importNode(middlePar);

        txbx = (Shape)lastPar2.getChildNodes(NodeType.ANY, false).get(2);
        lnkTxbx1 = (Shape)lastPar2.getChildNodes(NodeType.ANY, false).get(1);
        lnkTxbx2 = (Shape)lastPar2.getChildNodes(NodeType.ANY, false).get(0);

        msAssert.isNotNull(txbx.getTextBox());
        msAssert.areEqual(32768, txbx.getTextboxId());
        msAssert.areEqual(0, txbx.getLinkedTextboxId());
        msAssert.areEqual(0, txbx.getLinkedTextboxSeq());
        msAssert.areEqual(0, txbx.getTextboxNextShapeId());

        msAssert.isNotNull(lnkTxbx1.getTextBox());
        msAssert.areEqual(0, lnkTxbx1.getTextboxId());
        msAssert.areEqual(0, lnkTxbx1.getLinkedTextboxId());
        msAssert.areEqual(0, lnkTxbx1.getLinkedTextboxSeq());
        msAssert.areEqual(0, lnkTxbx1.getTextboxNextShapeId());

        msAssert.isNotNull(lnkTxbx2.getTextBox());
        msAssert.areEqual(0, lnkTxbx2.getTextboxId());
        msAssert.areEqual(0, lnkTxbx2.getLinkedTextboxId());
        msAssert.areEqual(0, lnkTxbx2.getLinkedTextboxSeq());
        msAssert.areEqual(0, lnkTxbx2.getTextboxNextShapeId());

        // 3. Import only linked textboxes. Import this case like a separate texboxes.
        lnkTxbx1 = (Shape)((Paragraph)importNode(lastPar)).getFirstChild();

        msAssert.isNotNull(lnkTxbx1.getTextBox());
        msAssert.areEqual(0, lnkTxbx1.getTextboxId());
        msAssert.areEqual(0, lnkTxbx1.getLinkedTextboxId());
        msAssert.areEqual(0, lnkTxbx1.getLinkedTextboxSeq());
        msAssert.areEqual(0, lnkTxbx1.getTextboxNextShapeId());

        // 4. Import only textbox without linked textboxes.
        txbx = (Shape)doc.getLastSection().getBody().getParagraphs().get(1).getChildNodes(NodeType.ANY, false).get(2); // Fix for C++. Get txbx from the original document.
        Shape importedTxbx = (Shape)importNode(txbx);

        msAssert.isNotNull(importedTxbx.getTextBox());
        msAssert.areEqual(0, importedTxbx.getTextboxId());
        msAssert.areEqual(0, importedTxbx.getLinkedTextboxId());
        msAssert.areEqual(0, importedTxbx.getLinkedTextboxSeq());
        msAssert.areEqual(0, importedTxbx.getTextboxNextShapeId());
    }

    /// <summary>
    /// This test checks functionality which was added while solving the issue WORDSNET-13866
    /// Linked textbox can hold identifier of the source textbox or index of the shape
    /// in the textboxes sequence inside document. MSW try to relates textboxes by index if
    /// can not find related textbox by textbox identifier.
    /// </summary>
    @Test
    public void testJira13866LinkByIndex() throws Exception
    {
        checkLinkedTxBxSeq(TestUtil.open("ImportDocx\\TestJira13866ByIndex.docx"), true);
    }

    /// <summary>
    /// WORDSNET-13961 Few words show as bold incorrectly in the PDF output
    /// Document has a problem, 'r' element is inside another 'r'. We resiliently read it but formatting is broken.
    /// </summary>
    @Test
    public void testJira13961() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira13961.docx");

        Run run = doc.getFirstSection().getBody().getParagraphs().get(1).getFirstRun();
        msAssert.isNull(run.getRunPr().get(FontAttr.BOLD));
        msAssert.isNull(run.getRunPr().get(FontAttr.BOLD_BI));
    }

    // FOSS TestJira13866MapId removed: tests shape-id range remapping specifically through WML (Docx2Wml) saves, a removed format.


    /// <summary>
    /// WORDSNET-14100 Support of reading/writing the 'collapsed' element (§2.5.1.3 of [MS-DOCX]).
    /// </summary>
    @Test
    public void testCollapsedParagraph() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestCollapsedParagraph", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.isTrue(doc.getFirstSection().getBody().getParagraphs().get(2).getParaPr().getCollapsed());
        // FOSS Dropped the Doc-format validator-warning check (removed format); the import assertion above is the point.
    }

    // FOSS TestJira14421 removed: the only check is that the loaded doc saves to PDF (a removed format).

    /// <summary>
    /// WORDSNET-14107 Support of the footnoteColumns element (§2.5.1.8 of [MS-DOCX]).
    /// </summary>
    @Test
    public void testFootnoteColumns() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestFootnoteColumns", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(3, doc.getFirstSection().getSectPr().getFootnoteColumns());
        // FOSS Dropped the Doc-format validator-warning check (removed format); the import assertion above is the point.
    }


    /// <summary>
    /// WORDSNET-14426 Aspose.Words.FileCorruptedException occurs upon loading a DOCX.
    /// The reason of the problem is that VML shape attribute “z-index” has non-numeric value “auto”.
    /// </summary>
    @Test
    public void testJira14636() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestJira14636", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        Body body = doc.getFirstSection().getBody();

        GroupShape group = (GroupShape)body.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(0, group.getZOrder());

        // For shapes inside group "z-index" is skipped.
        msAssert.isNull(((Shape)group.getChildNodes(NodeType.ANY, false).get(0)).getDirectShapeAttrInternal(ShapeAttr.Z_ORDER));
        msAssert.isNull(((Shape)group.getChildNodes(NodeType.ANY, false).get(1)).getDirectShapeAttrInternal(ShapeAttr.Z_ORDER));
        msAssert.isNull(((Shape)group.getChildNodes(NodeType.ANY, false).get(2)).getDirectShapeAttrInternal(ShapeAttr.Z_ORDER));

        // Check order of the shapes (First 3 shapes placed in the group, so skip it).
        NodeCollection shapes = (NodeCollection)body.getFirstParagraph().getChildNodes(NodeType.SHAPE, true);
        msAssert.areEqual(1, ((Shape)shapes.get(3)).getZOrder());
        msAssert.areEqual(4, ((Shape)shapes.get(4)).getZOrder());
        msAssert.areEqual(2, ((Shape)shapes.get(5)).getZOrder());
        msAssert.areEqual(3, ((Shape)shapes.get(6)).getZOrder());

        // For in-line shape "z-index" is skipped.
        shapes = (NodeCollection)body.getLastParagraph().getChildNodes(NodeType.SHAPE, true);
        msAssert.isNull(((Shape)shapes.get(0)).getDirectShapeAttrInternal(ShapeAttr.Z_ORDER));
    }
























    /// <summary>






    /// <summary>
    /// Relates to WORDSNET-15583
    /// Shows that we need to collapse attributes (at least direct formatting) in case of parent paragraph Istd revision.
    /// </summary>
    @Test
    public void testJira15583A() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira15583A.xml");
        Run run = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun();
        RunPr revPr = (RunPr)run.getRunPr().getFormatRevision().getRevPr();

        msAssert.areEqual(AttrBoolEx.FALSE, revPr.get(FontAttr.BOLD));
        msAssert.areEqual(AttrBoolEx.FALSE, revPr.get(FontAttr.ITALIC));
    }


    /// <summary>
    /// WORDSNET-15758 System.InvalidCastException is thrown while loading DOC file.
    /// One OLE package may be referred more than once in document. Added resilience.
    /// </summary>
    /// <remarks>
    /// Warning! File ImportDocx\TestJira15758.docWithVirus is infected, be careful.
    /// </remarks>
    @Test
    public void testJira15758() throws Exception
    {
        Document doc = TestUtil.openFromZip("ImportDocx\\TestJira15758.docx.zip", "virus");
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        String fileName1 = shapes.get(1).getOleFormat().getOlePackage().getFileName();
        String fileName2 = shapes.get(2).getOleFormat().getOlePackage().getFileName();
        msAssert.isTrue(StringUtil.equalsIgnoreCase(fileName1, fileName2));
    }

    /// <summary>
    /// WORDSNET-15727 ListFormat.ListLevel.Font does not report bold attribute properly.
    /// Bold or Italic attributes have to be get from Paragraph if ListLevel is not bullet. Added resilience.
    /// </summary>
    @Test
    public void testJira15727() throws Exception
    {
        Document document = TestUtil.open("ImportDocx\\TestJira15727.docx");
        document.updateListLabels();
        NodeCollection paras = document.getChildNodes(NodeType.PARAGRAPH, true);
        String[] boldTexts = new String[] { "One again\r", "Two again\r", "Three again\r" };
        for (Node para : (Iterable<Node>) paras)
        {
            Paragraph p = (Paragraph)para;
            if (p.isListItem())
            {
                msAssert.areEqual(ArrayUtil.contains(boldTexts, p.getRange().getText()), p.getListFormat().getListLevel().getFont().getBold());
            }
        }
    }



    /// <summary>
    /// WORDSNET-15960 Do not read LineSpacing when its Value part is not specified.
    /// We should not read LineSpacing when its Value part is missed.
    /// </summary>
    @Test
    public void testJira15960() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira15960.docx");

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(45, para.getParagraphFormat().getLineSpacing());
        msAssert.isFalse(para.getParagraphStyle().getParaPr().contains(ParaAttr.LINE_SPACING));
    }




    /// <summary>
    /// WORDSNET-16557 Aspose.Words.UnsupportedFileFormatException is thrown for DOTX.
    /// Word generates "file corrupted error" if source file is zip but it has corrupted content.
    /// </summary>
    @Test (expectedExceptions = FileCorruptedException.class)
    public void testJira16557() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira16557.dotx");
    }

    /// <summary>
    /// WORDSNET-16557 Aspose.Words.UnsupportedFileFormatException is thrown for DOTX.
    /// Check for DetectFileFormat generates valid exception.
    /// </summary>
    @Test (expectedExceptions = FileCorruptedException.class)
    public void testJira16557DetectFileFormat() throws Exception
    {
        String filename = TestUtil.getInTestDataPath("ImportDocx\\TestJira16557.dotx");
        FileFormatInfo ffi = FileFormatUtil.detectFileFormat(filename);
    }








    /// <summary>
    /// WORDSNET-17627 Header not rendered.
    /// SectPr element is outside of Body. The resilience is implemented.
    /// </summary>
    @Test
    public void testJira17627() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira17627.docx");
        HeaderFooter header = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        // Check some header content.
        msAssert.areEqual("Header Test 1", header.getTables().get(0).getFirstRow().getFirstCell().getFirstParagraph().getFirstRun().getText());
    }


    /// <summary>
    /// WORDSNET-17739 BuiltInDocumentProperties.Bytes always does not return a valid number of bytes.
    /// OOXML format doesn’t have the "Bytes" property, so take the bytes from stream.
    /// </summary>
    @Test
    public void testJira17739() throws Exception
    {
        final int EXPECTED_BYTES = 11960;
        final String TEST_FILE = "ImportDocx\\TestJira17739.docx";

        Document doc = TestUtil.open(TEST_FILE);
        int actualBytes = doc.getBuiltInDocumentProperties().getBytes();

        msAssert.areEqual(EXPECTED_BYTES, actualBytes);
    }

    /// <summary>
    /// Relates to WORDSNET-17723.
    /// Checks the case where bytes were set for the "Bytes" property and
    /// exported to the format which supports the "Bytes" property.
    /// </summary>
    @Test
    public void testJira17739Set() throws Exception
    {
        final int EXPECTED_REAL_BYTES = 11960;
        final int EXPECTED_SET_BYTES = 100;
        final String TEST_FILE = "ImportDocx\\TestJira17739.docx";

        Document doc = TestUtil.open(TEST_FILE);
        msAssert.areEqual(EXPECTED_REAL_BYTES, doc.getBuiltInDocumentProperties().getBytes());

        doc.getBuiltInDocumentProperties().setBytes(EXPECTED_SET_BYTES);
        // Check that overridden value was reset.
        msAssert.isNull(doc.getBuiltInDocumentProperties().get(PropertyName.BYTES).getDefaultValueInternal());
        // FOSS Dropped the WML roundtrip persistence check: Docx recomputes Bytes on save, so the
        // set-override does not survive a Docx roundtrip (it did through the removed flat WML format).
        msAssert.areEqual(EXPECTED_SET_BYTES, doc.getBuiltInDocumentProperties().getBytes());
    }

    /// <summary>
    /// Relates to WORDSNET-17723.
    /// Checks that the index and getter return an equal value of bytes.
    /// </summary>
    @Test
    public void testJira17739Indexer() throws Exception
    {
        final int EXPECTED_BYTES = 11960;
        final String TEST_FILE = "ImportDocx\\TestJira17739.docx";

        Document doc = TestUtil.open(TEST_FILE);

        msAssert.areEqual(EXPECTED_BYTES, doc.getBuiltInDocumentProperties().getBytes());
        msAssert.areEqual(EXPECTED_BYTES, doc.getBuiltInDocumentProperties().get(PropertyName.BYTES).toInt());
    }

    /// <summary>
    /// Relates to WORDSNET-17723.
    /// Checks the case when "Bytes" property is set to zero and can be read correctly.
    /// </summary>
    @Test
    public void testJira17739SetZero() throws Exception
    {
        final int EXPECTED_BYTES = 11960;
        final int EXPECTED_SET_BYTES = 0;
        final String TEST_FILE = "ImportDocx\\TestJira17739.docx";

        Document doc = TestUtil.open(TEST_FILE);
        msAssert.areEqual(EXPECTED_BYTES, doc.getBuiltInDocumentProperties().getBytes());

        doc.getBuiltInDocumentProperties().setBytes(EXPECTED_SET_BYTES);
        msAssert.areEqual(EXPECTED_SET_BYTES, doc.getBuiltInDocumentProperties().getBytes());
        // Check that overridden value was reset.
        msAssert.isNull(doc.getBuiltInDocumentProperties().get(PropertyName.BYTES).getDefaultValueInternal());
    }

    /// <summary>
    /// Relates to WORDSNET-17723.
    /// Checks that the writer does not changed "Bytes" property after set bytes.
    /// </summary>
    @Test
    public void testJira17739WriterDoesNotChangeProp() throws Exception
    {
        final int EXPECTED_BYTES = 11960;
        final String TEST_FILE = "ImportDocx\\TestJira17739.docx";

        Document doc = TestUtil.open(TEST_FILE);
        msAssert.areEqual(EXPECTED_BYTES, doc.getBuiltInDocumentProperties().getBytes());

        doc.getBuiltInDocumentProperties().setBytes(100);
        TestUtil.save(doc, "ImportDocx\\TestJira17739.docx");
        msAssert.areEqual(100, doc.getBuiltInDocumentProperties().getBytes());
    }


    /// <summary>
    /// WORDSNET-14953 Footnote.ReferenceMark returns incorrect value for custom reference mark.
    /// Word interprets content as reference mark text when it follows after the non-auto numbered footnote and formatted
    /// with "FootnoteReference" style. The issue can be resolved by appending such content to footnote reference mark.
    /// </summary>
    @Test
    public void testJira14953() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira14953.docx");

        NodeCollection footnotes = doc.getChildNodes(NodeType.FOOTNOTE, true);
        // "0xF031" - has to be appended.
        msAssert.areEqual(Character.toString(((char)0xF041)) + Character.toString(((char)0xF031)), ((Footnote)footnotes.get(0)).getReferenceMark());
        // "0xF031" was not appended because of style is not applied.
        msAssert.areEqual("A", ((Footnote)footnotes.get(1)).getReferenceMark());

        // Check footnotes parent structure.
        NodeCollection nodes = footnotes.get(0).getParentNode().getChildNodes(NodeType.ANY, false);
        msAssert.areEqual(NodeType.RUN, nodes.get(0).getNodeType());
        msAssert.areEqual(NodeType.FOOTNOTE, nodes.get(1).getNodeType());
        msAssert.areEqual(NodeType.RUN, nodes.get(2).getNodeType());
        msAssert.areEqual(NodeType.RUN, nodes.get(3).getNodeType());
        msAssert.areEqual(NodeType.FOOTNOTE, nodes.get(4).getNodeType());
        msAssert.areEqual(NodeType.BOOKMARK_START, nodes.get(5).getNodeType());
        msAssert.areEqual(NodeType.BOOKMARK_END, nodes.get(6).getNodeType());
        msAssert.areEqual(Character.toString(((char)0xF031)), ((Run)nodes.get(7)).getText());
        msAssert.areEqual(8, nodes.getCount());
    }

    /// <summary>
    /// Related to WORDSNET-14953
    /// Checks condition related to length of the footnote reference mark.
    /// </summary>
    @Test
    public void testJira14953Length() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira14953Length.docx");
        NodeCollection footnotes = doc.getChildNodes(NodeType.FOOTNOTE, true);

        // 1. The case with reference mark length.
        msAssert.areEqual("reference" + Character.toString(((char)0xF041)), ((Footnote)footnotes.get(0)).getReferenceMark());
        // This character was not appended due to length of the reference mark.
        msAssert.areEqual(Character.toString(((char)0xF031)), ((Run)footnotes.get(0).getNextSibling()).getText());

        // 2. Check that different formatted text can not be appended to reference mark. Actually Word does it.
        // However, AW model unable to store different font properties for reference mark characters.
        msAssert.areEqual("A", ((Footnote)footnotes.get(1)).getReferenceMark());
        msAssert.areEqual(Character.toString(((char)0xF031)), ((Run)footnotes.get(1).getParentNode().getLastChild()).getText());
    }

    /// <summary>
    /// Related to WORDSNET-14953
    /// Checks that symbols are not appended to reference mark when formatted with style differ from "FootnoteReference".
    /// </summary>
    @Test
    public void testJira14953Style() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira14953Style.docx");
        Footnote footnote = (Footnote)doc.getChild(NodeType.FOOTNOTE, 0, true);

        msAssert.areEqual(Character.toString(((char)0xF041)), footnote.getReferenceMark());
        msAssert.areEqual(Character.toString(((char)0xF031)), ((Run)footnote.getNextSibling()).getText());
    }


    /// <summary>
    /// Related to WORDSNET-18265
    /// Checks whether the SysDirectAttrsBackup attribute is ignored when it is collapsed.
    /// </summary>
    /// <remarks>
    /// <see cref="WordAttrCollection.SysDirectAttrsBackup"/> is temporary.
    /// This test must be deleted when the attribute is deleted.
    /// </remarks>
    @Test
    public void testJira18265IgnoreSysDirectAttrsBackup()
    {
        RunPr runAttrs = new RunPr();
        RunPr collapseAttr = new RunPr();
        collapseAttr.setAttr(WordAttrCollection.SYS_DIRECT_ATTRS_BACKUP, "1");
        runAttrs.collapse(collapseAttr);

        msAssert.areEqual(0, runAttrs.getCount());
    }



    /// <summary>
    /// Related to WORDSNET-18361
    /// Checks that child nodes of an unsupported element in the run also are read by the AW.
    /// </summary>
    @Test
    public void test18361() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira18361UnsupportedTag.docx");
        msAssert.areEqual("Text in run", doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getText());
    }

    /// <summary>
    /// WORDSNET-18712 Aspose.Words.FileCorruptedException occurs upon loading a DOCX.
    /// There is an empty tag 'w:w /' that is read as null string. It causes an exception while
    /// converting into a percentage value. Should be ignored.
    /// </summary>
    @Test
    public void test18712() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test18712.docx");

        // The problematic run with empty <w:w /> tag.
        Run run = doc.getFirstSection().getBody().getParagraphs().get(1).getFirstRun();

        msAssert.areEqual("This placeholder is ", run.getText());
        msAssert.isNull(run.getRunPr().get(FontAttr.SCALING));
    }






    // FOSS Test18984 removed: the input embedded an altChunk and had to be flattened via Word for the
    // FOSS build, which drops the wrong-footer resilience structure this test asserts on.

    /// <summary>
    /// WORDSNET-19151 DOCX to PDF/Print issue with text position.
    /// The MS Word's behaviour was implemented to ignore incorrect tabs.
    /// </summary>
    /// <remarks>
    /// The problematic paragraph in the test file has got the 'w:pPr' element with many 'w:tab' tags nested inside
    /// the 'w:r' element for some reason. MS Word ignores these tabs. The same behaviour was implemented.
    /// </remarks>
    @Test
    public void test19151() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test19151.docx");
        msAssert.areEqual("From inserted document", doc.getFirstSection().getBody().getParagraphs().get(1).getRuns().get(2).getText());
    }



    /// <summary>
    /// WORDSNET-19296 Aspose.Words.FileCorruptedException is thrown while loading DOCX
    /// Invalid INCLUDEPICTURE field, added resilience.
    /// </summary>
    @Test
    public void test19296() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test19296.docx");

        Shape shape = (Shape)doc.getFirstSection().getBody().getTables().get(0).getRows().get(1).getCells().get(1).getParagraphs().get(1).getFirstChild();
        msAssert.areEqual(1.0, shape.getShapePr().get(ShapeAttr.WIDTH));
        msAssert.areEqual(1.0, shape.getShapePr().get(ShapeAttr.HEIGHT));
        msAssert.isTrue(shape.getImageData().getSourceFullName().startsWith("C:\\Users\\Administrator.SKY-20180423UBO"));
    }


    // FOSS Test19346 removed: input embeds an HTML altChunk (the style-copy-from-altChunk behavior is
    // the point); altChunk / HTML load is unsupported in the FOSS build.



    /// <summary>
    /// WORDSNET-19392 System.OverflowException is thrown while loading DOCX
    /// Invalid w:line element values. Added validation.
    /// </summary>
    @Test
    public void test19392A() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test19392A.xml");

        // w:line="-2147483648"
        checkLineSpacing(doc, 0, 0, LineSpacingRule.MULTIPLE);

        // w:line="-2147483646"
        checkLineSpacing(doc, 1, 2, LineSpacingRule.MULTIPLE);

        // w:line="2147483648"
        checkLineSpacing(doc, 2, 0, LineSpacingRule.MULTIPLE);

        // w:line="2147483646"
        checkLineSpacing(doc, 3, 1, LineSpacingRule.EXACTLY);
    }





    /// <summary>
    /// Related to WORDSNET-19542
    /// Word updates outline level specified in a "Heading" style to a corresponding value between 1 and 9.
    /// </summary>
    @Test
    public void test19542WrongStyleOutline() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test19542WrongStyleOutline.docx");
        Body body = doc.getFirstSection().getBody();

        checkHeadingParaOutlineLevel(body.getFirstParagraph(), OutlineLevel.LEVEL_2);
        checkHeadingParaOutlineLevel(body.getShapes().get(0).getFirstParagraph(), OutlineLevel.LEVEL_2);
        checkHeadingParaOutlineLevel(body.getTables().get(0).getLastRow().getCells().get(1).getFirstParagraph(), OutlineLevel.LEVEL_2);
    }

    /// <summary>
    /// WORDSNET-19547 Bookmark.Text returns empty value that is incorrect
    /// Run content should be flushed before bookmark resilient read.
    /// </summary>
    @Test
    public void test19547() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test19547.xml");
        msAssert.areEqual("____", doc.getRange().getBookmarks().get(0).getText());
    }


    /// <summary>
    /// WORDSNET-19577 Converting DOCX -> PDF Mirror Margins doesn't translate.
    /// SectPr contains the "mirrorMargins" property. Word writes this property for DocPr. Read property added.
    /// </summary>
    @Test (dataProvider = "test19577DataProvider")
    // The main document doesn't contain types in SectPrs, and the Alt chunks contains types in settings.xml.
    public void test19577(String fileName, /*MultiplePagesType*/int type) throws Exception
    {
        Document doc = TestUtil.open(fileName);
        msAssert.areEqual(type, doc.getDocPr().MultiplePages);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19577DataProvider")
	public static Object[][] test19577DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"ImportDocx\\Test19577A.docx",  MultiplePagesType.MIRROR_MARGINS},
			{"ImportDocx\\Test19577B.docx",  MultiplePagesType.NORMAL},
		};
	}


    /// <summary>
    /// WORDSNET-3980 rotation angle attribute is imported incorrectly.
    /// Improved import of fixed degrees values with the postfix "f" and "fd".
    /// </summary>
    @Test
    public void test3980() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test3980.docx");
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        msAssert.areEqual(-1638402, shape.getShapePr().get(ShapeAttr.TD_ROTATION_ANGLE_Y));
    }



    /// <summary>
    /// Relates to WORDSNET-19768
    /// Tests that OOXML format updated similar to WML format.
    /// </summary>
    @Test
    public void test19768() throws Exception
    {
        Table table = TestUtil.open("ImportDocx\\Test19768.xml").getFirstSection().getBody().getTables().get(0);
        msAssert.areEqual(0.5, table.getLeftPadding());
        msAssert.areEqual(0.5, table.getRightPadding());
    }





    /// <summary>
    /// WORDSNET-20928 Aspose.Words.FileCorruptedException occurs upon loading a DOCX.
    /// DocumentProtection.PasswordHash reading has been changed to skip corrupted base 64 encoded strings.
    /// </summary>
    @Test
    public void test20928() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test20928.docx");

        msAssert.isNull(doc.getDocumentProtection().getPasswordHash().Hash);
        msAssert.isNull(doc.getDocumentProtection().getPasswordHash().Salt);
    }




    /// <summary>
    /// WORDSNET-20722 Aspose.Words.FileCorruptedException occurs upon loading a DOCX
    /// Changed style limitations in DOM.
    /// </summary>
    @Test
    public void test20722() throws Exception
    {
        final String TEST_FILE = "ImportDocx\\Test20722.docx";

        Document doc = TestUtil.open(TEST_FILE);
        msAssert.areEqual(30, doc.getStyles().getCount());
        msAssert.areEqual(4130, doc.getGlossaryDocument().getStyles().getCount());

        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.DOCX);

        // There is some issue with Custom XML and full roundtrip failed.
        String outFile = TestUtil.save(doc, TEST_FILE, so, true, GoldLevel.EXPORT_ONLY);

        doc = TestUtil.open(outFile);
        msAssert.areEqual(30, doc.getStyles().getCount());
        msAssert.areEqual(4082, doc.getGlossaryDocument().getStyles().getCount());
    }


    /// <summary>
    /// WORDSJAVA-2470 java.lang.RuntimeException is thrown while loading DOCX
    /// Exception in thread "main" java.lang.RuntimeException: Format_BadBase64Char
    /// </summary>
    @Test
    public void testJava2407() throws Exception
    {
        // Verify file can be loaded successfully.
        TestUtil.open("ImportDocx\\TestJava2470.docx");
    }



    /// <summary>
    /// WORDSNET-21597 Add "Decorative" flag for DML shapes.
    /// New public property added.
    /// </summary>
    @Test (dataProvider = "test21597DataProvider")
    public void test21597(/*LoadFormat*/int loadFormat) throws Exception
    {
        final String FILE_NAME = "Model\\DrawingML\\Test21597";
        Document doc = TestUtil.open(FILE_NAME, loadFormat);

        Shape shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        Shape shape2 = doc.getFirstSection().getBody().getShapes().get(1);

        msAssert.isTrue(shape1.isDecorative());
        if (shape1.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            DmlExtension ext = shape1.getDmlNode().getDocPrExtensions().get(DmlExtensionUri.DECORATIVE);
            msAssert.isTrue(ext.getDecorative());
        }

        msAssert.isFalse(shape2.isDecorative());
        if (shape2.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            DmlExtension ext = shape2.getDmlNode().getDocPrExtensions().get(DmlExtensionUri.DECORATIVE);
            msAssert.isFalse(ext.getDecorative());
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21597DataProvider")
	public static Object[][] test21597DataProvider() throws Exception
	{
		return new Object[][]
		{
			{LoadFormat.DOCX},
		};
	}

    /// <summary>
    /// WORDSNET-22087 Range.Fields throws System.InvalidOperationException.
    /// <see cref="Aspose.Xml.AnyXmlReader.ReadChild"/> does not support reading child nodes with the same name as parent.
    /// Reading of the parent will be stopped at this case. Continue reading to fix the problem when name of a child node
    /// equal to parent name.
    /// </summary>
    @Test (enabled = false)
    public void test22087(String fileName) throws Exception
    {
        Document doc = TestUtil.open(fileName);
        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(1);

        msAssert.areEqual(15, para.getChildNodes(NodeType.ANY, false).getCount());
        NodeCollection nodes = para.getChildNodes(NodeType.ANY, false);

        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldStart)nodes.get(1)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldSeparator)nodes.get(3)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_INCLUDE_PICTURE, ((FieldStart)nodes.get(4)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_INCLUDE_PICTURE, ((FieldSeparator)nodes.get(6)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldStart)nodes.get(7)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldSeparator)nodes.get(9)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldEnd)nodes.get(10)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_INCLUDE_PICTURE, ((FieldEnd)nodes.get(11)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldEnd)nodes.get(14)).getFieldType());

        // The client case.
        for (Field field : doc.getRange().getFields())
        {
            // Empty loop. Check that no exception occurs when fields are being enumerated.
        }
    }

    /// <summary>
    /// Related with WORDSNET-22087
    /// Checks the case when hyperlink content ends with nested hyperlink.
    /// </summary>
    /// <param name="fileName"></param>
    @Test (enabled = false)
    public void test22087B(String fileName) throws Exception
    {
        Document doc = TestUtil.open(fileName);
        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(1);

        msAssert.areEqual(9, para.getChildNodes(NodeType.ANY, false).getCount());
        NodeCollection nodes = para.getChildNodes(NodeType.ANY, false);

        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldStart)nodes.get(1)).getFieldType());
        msAssert.isTrue(((Run)nodes.get(2)).getText().endsWith("Middelweerd.php\" "));
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldSeparator)nodes.get(3)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldStart)nodes.get(4)).getFieldType());
        msAssert.isTrue(((Run)nodes.get(5)).getText().endsWith("VanHemert.php\" "));
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldSeparator)nodes.get(6)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldEnd)nodes.get(7)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldEnd)nodes.get(8)).getFieldType());
    }

    /// <summary>
    /// Related with WORDSNET-22087
    /// Checks the case when hyperlink contains two nested hyperlinks.
    /// </summary>
    /// <param name="fileName"></param>
    @Test (enabled = false)
    public void test22087C(String fileName) throws Exception
    {
        Document doc = TestUtil.open(fileName);
        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(1);

        msAssert.areEqual(13, para.getChildNodes(NodeType.ANY, false).getCount());
        NodeCollection nodes = para.getChildNodes(NodeType.ANY, false);

        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldStart)nodes.get(1)).getFieldType());
        msAssert.isTrue(((Run)nodes.get(2)).getText().endsWith("Middelweerd.php\" "));
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldSeparator)nodes.get(3)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldStart)nodes.get(4)).getFieldType());
        msAssert.isTrue(((Run)nodes.get(5)).getText().endsWith("VanHemert.php\" "));
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldSeparator)nodes.get(6)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldEnd)nodes.get(7)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldStart)nodes.get(8)).getFieldType());
        msAssert.isTrue(((Run)nodes.get(9)).getText().endsWith("Middelweerd3.php\" "));
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldSeparator)nodes.get(10)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldEnd)nodes.get(11)).getFieldType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldEnd)nodes.get(12)).getFieldType());
    }


    /// <summary>
    /// WORDSNET-22410 Language of text changes after re-saving a DOCX document.
    /// Encodyng the locale detection algorithm in a case insensitive manner.
    /// </summary>
    @Test
    public void test22410() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Test22410", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        RunPr runPr = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getRunPr();
        msAssert.areEqual("en-NZ", LocaleConverter.localeToDocxTag((/*int*/Integer)runPr.get(FontAttr.LOCALE_ID)));
    }

    /// <summary>
    /// WORDSNET-22434 "Russian (Moldova)" and "Romanian (Moldova)" languages are not correctly loaded and saved from/to DOCX documents.
    /// Fixed typos in the names of the "ru-MD" and "ro-MD" locales.
    /// </summary>
    @Test
    public void test22434() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Test22434", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        RunPr runPr = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getRunPr();
        msAssert.areEqual("ru-MD", LocaleConverter.localeToDocxTag((/*int*/Integer)runPr.get(FontAttr.LOCALE_ID)));
        runPr = doc.getFirstSection().getBody().getParagraphs().get(1).getFirstRun().getRunPr();
        msAssert.areEqual("ro-MD", LocaleConverter.localeToDocxTag((/*int*/Integer)runPr.get(FontAttr.LOCALE_ID)));
    }

    /// <summary>
    /// Relates to WORDSNET-22587
    /// Tests how default Table style is applied.
    /// </summary>
    @Test
    public void test22587TableStyle() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test22587TableStyle.docx");
        Table table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.areEqual("Style1", table.getStyle().getName());
        msAssert.isTrue(table.getFirstRow().getFirstCell().getFirstParagraph().getFirstRun().getFont().getBold());
    }









    /// <summary>
    /// WORDSNET-22949 Supports docLocation and history hyperlink attributes.
    /// </summary>
    @Test
    public void test22949() throws Exception
    {
        Document document = TestUtil.open("ImportDocx\\Test22949.docx");

        FieldCollection fields = document.getRange().getFields();

        msAssert.areEqual(3, fields.getCount());
        assertFieldHyperlink(fields.get(0), false, null);
        assertFieldHyperlink(fields.get(1), true, null);
        assertFieldHyperlink(fields.get(2), false, "location");
    }

    private static void assertFieldHyperlink(Field field, boolean noHistory, String docLocation)
    {
        msAssert.isTrue(field instanceof FieldHyperlink);
        FieldHyperlink fieldHyperlink = (FieldHyperlink)field;
        msAssert.areEqual(noHistory, fieldHyperlink.getNoHistory());
        msAssert.areEqual(docLocation, fieldHyperlink.getDocLocation());
    }







    /// <summary>
    /// WORDSNET-22982 Table cell preferred does not match MS Word in AW docx output.
    /// Preferred width needs to be converted to short int.
    /// </summary>
    @Test
    public void test22982() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test22982.docx");

        // Check some key values.
        CellCollection tbl1Cells = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getCells();
        // Value in XML 0x03 0570.
        msAssert.areEqual(0x0570, tbl1Cells.get(0).getCellPr().getPreferredWidth().getValueRaw());
        // Value in XML 0x03 0d40.
        msAssert.areEqual(0x0d40, tbl1Cells.get(1).getCellPr().getPreferredWidth().getValueRaw());
        // Value in XML 0x03 18f8.
        msAssert.areEqual(0x18f8, tbl1Cells.get(2).getCellPr().getPreferredWidth().getValueRaw());

        CellCollection tbl4Cells = doc.getFirstSection().getBody().getTables().get(3).getFirstRow().getCells();
        // Value in XML 0x07 ffff.
        msAssert.areEqual(Short.MAX_VALUE, tbl4Cells.get(0).getCellPr().getPreferredWidth().getValueRaw());
        // Value in XML 0x08 000.
        msAssert.areEqual(Short.MIN_VALUE, tbl4Cells.get(1).getCellPr().getPreferredWidth().getValueRaw());

        CellCollection tbl5Cells = doc.getFirstSection().getBody().getTables().get(4).getFirstRow().getCells();
        // Value in XML 0x00 ffff.
        msAssert.areEqual(-1, tbl5Cells.get(0).getCellPr().getPreferredWidth().getValueRaw());
        // Value in XML 0x01 0000.
        msAssert.areEqual(0, tbl5Cells.get(1).getCellPr().getPreferredWidth().getValueRaw());
    }

    /// <summary>
    /// Related with WORDSNET-22982
    /// Checks how preferred width of tables converted to short int.
    /// </summary>
    @Test
    public void test23228() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test23228.docx");

        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(-1, tables.get(0).getPreferredWidth().getValueTwips());
        msAssert.areEqual(4096, tables.get(1).getPreferredWidth().getValueTwips());
        msAssert.areEqual(32000, tables.get(2).getPreferredWidth().getValueTwips());
        msAssert.areEqual(4096, tables.get(3).getPreferredWidth().getValueTwips());

        // Check that values are the same after the export.
        doc = TestUtil.saveOpen(doc, "ImportDocx\\Test23228", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(-1, tables.get(0).getPreferredWidth().getValueTwips());
        msAssert.areEqual(4096, tables.get(1).getPreferredWidth().getValueTwips());
        msAssert.areEqual(32000, tables.get(2).getPreferredWidth().getValueTwips());
        msAssert.areEqual(4096, tables.get(3).getPreferredWidth().getValueTwips());
    }

    /// <summary>
    /// WORDSNET-23566 Text becomes white after open/save DOCX document.
    /// The 'w:color' element is defined multiple times. We should take into account only one is last occured.
    /// </summary>
    @Test
    public void test23566() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Test23566", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        Run run = TestUtil.getRunWithText(doc.getFirstSection().getBody().getFirstParagraph(), "...text that should appear...");

        msAssert.areEqual(DrColor.BLACK, run.getRunPr().getColor());
    }

    // FOSS Test23604 removed: tests list-definition numbering from HTML altChunks specifically;
    // altChunk / HTML load is unsupported in the FOSS build.




    /// <summary>
    /// WORDSNET-23812 Timestamp data of the inserted/deleted text gets modified.
    /// Word ignores TimeZone offset in revisions timestamp.
    /// </summary>
    @Test
    public void test23812() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test23812.docx");
        // The problematic revision has timestamp "2022-04-21T08:49:00-4".
        msAssert.areEqual(new DateTime(2022, 4, 21, 8, 49, 0), doc.getRevisions().get(0).getDateTimeInternal());
    }

    /// <summary>
    /// WORDSNET-23885 Shape becomes visible after open/save DOCX document.
    /// AlternateContent occurs on the paragraph level. Need to implement resilience and read the content to fix the issue.
    /// </summary>
    @Test
    public void test23885() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test23885.docx");
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        msAssert.areEqual(1, shapes.getCount());
        msAssert.areEqual(ShapeType.RECTANGLE, shapes.get(0).getShapeType());
        msAssert.areEqual(ShapeType.RECTANGLE, shapes.get(0).getFallbackShape().getShapeType());
    }

    private static void verifySpacings(String testFile, LoadOptions lo, int spaceAfter, int lineSpacing) throws Exception
    {
        ParaPr defaultParaPr = TestUtil.open(testFile, lo).getStyles().getDefaultParaPr();
        msAssert.areEqual(spaceAfter, defaultParaPr.getSpaceAfter());
        msAssert.areEqual(lineSpacing, defaultParaPr.getLineSpacing());
        msAssert.areEqual(LineSpacingRule.MULTIPLE, defaultParaPr.getLineSpacingRule());
    }

    /// <summary>
    /// WORDSNET-23778 Revision on list item is rendered incorrectly.
    /// Looks like the Word does not support nested "ins" revisions. Ignore such revisions on loading to fix the issue.
    /// </summary>
    @Test
    public void test23778() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test23778.docx");
        msAssert.isFalse(doc.getFirstSection().getBody().getParagraphs().get(1).getParagraphBreakRunPr().hasInsertRevision());
    }

    // FOSS Test23913 removed: the input is a deliberately-invalid table that also embeds an altChunk,
    // so it cannot be loaded in the FOSS build (altChunk unsupported) and can't be Word-repaired without
    // fixing the very invalidity the resilience test relies on.

    /// <summary>
    /// WORDSNET-23886 Style applied to text is changed after open/save DOCX document.
    /// The problem was we always read 'w:default' element as 'true', but it can have a On/Off value.
    /// </summary>
    @Test
    public void test23886() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test23886.docx");

        // Check problematic paragraph in Footer.
        HeaderFooter footer = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);
        Paragraph para = footer.getFirstParagraph();
        msAssert.areEqual("Normal", para.getParagraphFormat().getStyleName());
        Run run = para.getFirstRun();
        msAssert.isFalse(run.getFont().getBold());

        // Check problematic paragraph in Table.
        Table table = doc.getLastSection().getBody().getTables().get(0);
        para = table.getFirstRow().getFirstCell().getFirstParagraph();
        msAssert.areEqual("Normal", para.getParagraphFormat().getStyleName());
    }







    // System.Link still not portable to Java
    private static GroupShape getFirstGroupShapeWithName(NodeCollection nodes, String name)
    {
        GroupShape shape = null;
        for (Node node : (Iterable<Node>) nodes)
        {
            shape = (GroupShape)node;
            if (msString.equals(shape.getName(), name))
                break;
        }
        return shape;
    }



    /// <summary>
    /// WORDSNET-24514 FileCorruptedException is thrown upon loading DOCX document.
    /// The document contains duplicated "CommentExtensible" and "CommentEx" elements. Looks like the Word uses last
    /// occurrence from the sequence at this case. Mimic the Word to fix the issue.
    /// </summary>
    @Test (dataProvider = "test24514DataProvider")
    public void test24514(String filePath, int commentsCount, String commentDateStr, boolean done) throws Exception
    {
        Document doc = TestUtil.open(filePath);

        NodeCollection comments = doc.getChildNodes(NodeType.COMMENT, true);
        msAssert.areEqual(commentsCount, comments.getCount());

        if (commentsCount == 0)
            return;

        DateTime commentDate = DateTime.parseExact(commentDateStr,
            "yyyy-MM-ddTHH:mm:ssZ", msCultureInfo.getInvariantCulture(), DateTimeStyles.ADJUST_TO_UNIVERSAL);

        Comment comment = (Comment)comments.get(0);
        msAssert.areEqual(done, comment.getDone());
        msAssert.areEqual(StringUtil.hasChars(commentDateStr), comment.isDateTimeUtcDefined());
        if (comment.isDateTimeUtcDefined())
            msAssert.areEqual(commentDate, comment.getDateTimeUtcInternal());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test24514DataProvider")
	public static Object[][] test24514DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"ImportDocx\\Test24514.docx",  1,  "2022-10-26T06:07:00Z",  false},
		};
	}


    /// <summary>
    /// Tests RegEx which helps to convert EQ field to Ruby formatting.
    /// </summary>
    @Test (dataProvider = "testConvertFieldEqCodeToRubyADataProvider")
    public void testConvertFieldEqCodeToRubyA(
        String fieldCode,
        int alignment,
        int topSize,
        int distance,
        String topText,
        String baseText) throws Exception
    {
        Field field = new DocumentBuilder().insertField(fieldCode, null);

        Ruby ruby = RubyConverter.convertToRuby(field);

        msAssert.isNotNull(ruby);
        msAssert.areEqual((/*RubyAlignment*/int)alignment, ruby.Alignment);
        msAssert.areEqual(topSize, ruby.TopSize);
        msAssert.areEqual(distance * 2, ruby.Distance);
        msAssert.areEqual(topText, ruby.getTop().getText());
        msAssert.areEqual(baseText, ruby.getBase().getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testConvertFieldEqCodeToRubyADataProvider")
	public static Object[][] testConvertFieldEqCodeToRubyADataProvider() throws Exception
	{
		return new Object[][]
		{
			{"EQ \\*jc0 \\* \"Font:宋体\" \\* hps14 \\o \\ad(\\s \\up 13(nao),b)",  0,  14,  13,  "nao",  "b"},
			{" EQ \\*jc0 \\* \"Font:宋体\" \\*hps14 \\o \\ad(\\s \\up 13(nao),b)",  0,  14,  13,  "nao",  "b"},
			{"EQ \\*jc0 \\* hps14 \\o \\ad (\\s\\up 13 (nao) , b)",  0,  14,  13,  "nao",  "b"},
		};
	}

    /// <summary>
    /// Tests RegEx which helps to convert EQ field to Ruby formatting when
    /// EQ field is not a Ruby due to subscript (\do). See WORDSNET 25920.
    /// </summary>
    @Test (dataProvider = "testConvertFieldEqCodeToRubyBDataProvider")
    public void testConvertFieldEqCodeToRubyB(String fieldCode) throws Exception
    {
        Field field = new DocumentBuilder().insertField(fieldCode, null);

        Ruby ruby = RubyConverter.convertToRuby(field);
        msAssert.isNull(ruby);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testConvertFieldEqCodeToRubyBDataProvider")
	public static Object[][] testConvertFieldEqCodeToRubyBDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"EQ \\o(\\s\\up 5(25),\\s\\do 2(D))"},
			{"EQ \\* jc3 \\* hps9 \\o(\\s\\up 5(25),\\s\\do 2(D))"},
		};
	}

    /// <summary>
    /// WORDSNET-24579 FileCorruptedException is thrown upon loading DOCX document.
    /// </summary>
    @Test
    public void test24579() throws Exception
    {
        Document document = TestUtil.open("ImportDocx\\Test24579.docx");

        FieldCollection fields = document.getRange().getFields();
        msAssert.areEqual(1, fields.getCount());

        msAssert.areEqual(FieldType.FIELD_EQUATION, fields.get(0).getType());

        Run run = document.getFirstSection().getBody().getFirstParagraph().getFirstRun();
        msAssert.isNull(run.getRunPr().get(FontAttr.RUBY));
    }

    /// <summary>
    /// Additional test for WORDSNET-23747
    /// Checks the case with "DOCX" input file.
    /// </summary>
    @Test
    public void test23747Docx() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test23747.docx");
        TableCollection tables = doc.getFirstSection().getBody().getTables();

        final double PRECISION = 0.0001;
        msAssert.areEqual(ConvertUtilCore.twipToPoint(108), PRECISION, tables.get(0).getLeftIndent());
        msAssert.areEqual(ConvertUtilCore.twipToPoint(158), PRECISION, tables.get(1).getLeftIndent());
    }


    /// <summary>
    /// WORDSNET-24477 High peak memory usage when working with the document containing a large number of images.
    /// Allow to disable loading of OLE embedded data to reduce memory consumption.
    /// </summary>
    @Test (dataProvider = "test24477DataProvider")
    public void test24477(boolean ignoreEmbedded) throws Exception
    {
        TestWarningCallback wc = new TestWarningCallback();
        LoadOptions lo = new LoadOptions();
        {
            lo.setWarningCallback(wc);
            lo.setIgnoreOleData(ignoreEmbedded);
        }

        Document doc = TestUtil.open("ImportDocx\\Test24477.docx", lo);

        Shape oleShape = doc.getFirstSection().getBody().getShapes().get(0);
        if (ignoreEmbedded)
            msAssert.isNull(oleShape.getOleFormat());
        else
            msAssert.isNotNull(oleShape.getOleFormat());

        msAssert.isFalse(oleShape.isOleControl());
        msAssert.areEqual(!ignoreEmbedded, oleShape.isOle());
        msAssert.areEqual(!ignoreEmbedded, oleShape.isOleObject());
        msAssert.areEqual(ignoreEmbedded ? OleObjectType.NONE : OleObjectType.EMBEDDED, oleShape.getOleObjectType());

        // On post loading stage OLE without embedded data will be converted to image.
        msAssert.areEqual(ignoreEmbedded ? ShapeType.IMAGE : ShapeType.OLE_OBJECT, oleShape.getShapeType());

        msAssert.areEqual(ignoreEmbedded, wc.contains(WarningSource.UNKNOWN,
                WarningType.UNEXPECTED_CONTENT, WarningStrings.EMBEDDED_OLE_WITHOUT_DATA));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test24477DataProvider")
	public static Object[][] test24477DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// WORDSNET-24791 Hanging indentation is lost after open/save DOCX document.
    /// Preserve expanded list related attributes in case of ListId revision.
    /// </summary>
    @Test
    public void test24791() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test24791.docx");

        Paragraph para1 = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(-360, para1.getParaPr().getFormatRevision().getRevPr().get(ParaAttr.FIRST_LINE_INDENT));

        Paragraph para2 = doc.getFirstSection().getBody().getParagraphs().get(2);
        msAssert.areEqual(-360, para2.getParaPr().getFormatRevision().getRevPr().get(ParaAttr.FIRST_LINE_INDENT));
    }



    /// <summary>
    /// WORDSNET-24680 FileCorruptedException is thrown upon loading encrypted DOCX document.
    /// Added default value for FontAttr.FitText attribute.
    /// </summary>
    @Test
    public void test24680() throws Exception
    {
        LoadOptions options = new LoadOptions(); { options.setPassword("gamma2018"); }
        Document doc = TestUtil.open("ImportDocx\\Test24680.docx", options);
        msAssert.areEqual(11, doc.getFirstSection().getBody().getParagraphs().getCount());
    }



    /// <summary>
    /// WORDSNET-24761 Field value is not read when field is defined as fldSimple.
    /// </summary>
    @Test
    public void test24761() throws Exception
    {
        Document document = TestUtil.open("ImportDocx\\Test24761.docx");

        FieldCollection fields = document.getRange().getFields();

        RunPr runPr = new RunPr();
        assertFieldResult(fields.get(0), "w/o outer rPr, w/o inner r, w/o inner rPr", runPr);

        runPr.setBold(AttrBoolEx.TRUE);
        runPr.setColor(DrColor.BLUE);
        runPr.setSize(28);
        assertFieldResult(fields.get(1), "w/ outer rPr, w/o inner r, w/o inner rPr", runPr);
        assertFieldResult(fields.get(2), "w/ outer rPr, w/ inner r, w/o inner rPr", runPr);

        runPr.setItalic(AttrBoolEx.TRUE);
        runPr.setColor(DrColor.RED);
        runPr.setSize(14);
        assertFieldResult(fields.get(3), "w/ outer rPr, w/ inner r, w/ inner rPr", runPr);
    }












    /// <summary>
    /// WORDSNET-25714 Image is lost after converting document to HTML
    /// Ignore hidden attribute for inline DML shapes.
    /// </summary>
    @Test
    public void test25714() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test25714.docx");
        msAssert.isFalse(doc.getFirstSection().getBody().getShapes().get(0).getHidden());
    }

    /// <summary>
    /// WORDSNET-26245 Comparison displays wrong deletion
    /// Create new attribute collection for runs after symbolic run.
    /// </summary>
    @Test
    public void test26245() throws Exception
    {
        Document docA = TestUtil.open("ImportDocx\\Test26245.docx");
        Run run5 = docA.getFirstSection().getBody().getFirstParagraph().getRuns().get(5);
        Run run7 = docA.getFirstSection().getBody().getFirstParagraph().getRuns().get(7);

        // Check problematic runs don't share attribute collection.
        msAssert.isFalse(msObject.referenceEquals(run5.getRunPr(), run7.getRunPr()));
    }

    /// <summary>
    /// WORDSNET-25123 Shape position is changed after open/save document.
    /// Checks the correct WrapType setting proceeding from the shape position,
    /// not from direct setting of WrapType from VML extended markup.
    /// </summary>
    @Test
    public void test25123() throws Exception
    {
        final String TEST_NAME = "ImportDocx\\Test25123";
        Document doc = TestUtil.open(TEST_NAME, LoadFormat.DOCX);

        msAssert.areEqual(WrapType.INLINE, doc.getFirstSection().getBody().getShapes().get(0).getWrapType());

        // We check the gold for the absence of "position:absolute" and the explicit attribute "w10:wrap type".
        doc = TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX);
        msAssert.areEqual(WrapType.INLINE, doc.getFirstSection().getBody().getShapes().get(0).getWrapType());
    }






    /// <summary>
    /// WORDSNET-26249 Incorrect handling of cell preferred tcw element in pct units
    /// Floor value specified by percentage character.
    /// </summary>
    @Test
    public void test26249() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test26249.docx");
        Cell cell = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getCells().get(1);
        msAssert.areEqual(PreferredWidth.fromPercent(50.0), cell.getCellPr().getPreferredWidth());
    }

    /// <summary>
    /// WORDSNET-26806 Aspose.Words hangs upon loading damaged document.
    /// The code was hanging in the <see cref="ZipEntry"/> class due to reading 0 bytes. Now an exception is generated.
    /// </summary>
    @Test (expectedExceptions = FileCorruptedException.class)
    // FOSS: the corrupt-package load surfaces FileCorruptedException; the point (it throws instead of
    // hanging) still holds.
    public void test26806() throws Exception
    {
        TestUtil.open("ImportDocx\\Test26806Corrupted.docx");
    }

    /// <summary>
    /// WORDSNET-26597 StackOverflowException is thrown upon loading DOCX document.
    /// Limit maximum allowed group nesting level.
    /// </summary>
    @Test
    public void test26597() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test26597.docx");

        msAssert.areEqual(2721, doc.getChildNodes(NodeType.SHAPE, true).getCount());
        msAssert.areEqual(2755, doc.getChildNodes(NodeType.GROUP_SHAPE, true).getCount());
    }

    /// <summary>
    /// WORDSNET-26228 Aspose.Words does not throw exception upon loading corrupted document.
    /// Decrease confidence on bad words length.
    /// </summary>
    @Test (expectedExceptions = UnsupportedFileFormatException.class)
    public void test26228() throws Exception
    {
        TestUtil.open("ImportTxt\\FormatDetector\\Test26228.docx");
    }


    /// <summary>
    /// WORDSNET-27021 Font is changed after open/save document.
    /// Handle AlternateContent for run properties.
    /// </summary>
    @Test
    public void test27021() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test27021.docx");

        Run run1 = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun();
        msAssert.areEqual("Segoe UI Emoji", run1.getRunPr().getNameAscii());
        msAssert.areEqual("Segoe UI Emoji", run1.getRunPr().getNameFarEast());
        msAssert.areEqual("Segoe UI Emoji", run1.getRunPr().getNameOther());

        Run run2 = doc.getFirstSection().getBody().getParagraphs().get(8).getFirstRun();
        msAssert.areEqual("ＭＳ 明朝", run2.getRunPr().getNameAscii());
        msAssert.areEqual("ＭＳ 明朝", run2.getRunPr().getNameBi());
        msAssert.areEqual("ＭＳ 明朝", run2.getRunPr().getNameOther());
    }

    /// <summary>
    /// Additional test for WORDSNET-26269.
    /// Checks that RunPr is not shared between shape and following run.
    /// </summary>
    @Test
    public void test26269A() throws Exception
    {
        Document docA = TestUtil.open("Model\\Comparer\\Runs\\Test26269(A).docx");

        Paragraph para = docA.getFirstSection().getBody().getParagraphs().get(1);
        RunPr runPr1 = ((Shape)para.getFirstChild()).getRunPr();
        RunPr runPr2 = ((Run)para.getChild(NodeType.RUN, 0, true)).getRunPr();

        msAssert.isFalse(msObject.referenceEquals(runPr1, runPr2));
    }


    /// <summary>
    /// WORDSNET-27097 FileCorruptedException is thrown upon loading FlatOPC document.
    /// Invalid Base64 string, added resilience.
    /// </summary>
    /// <remarks>
    /// Problematic image is read as invalid JPG image but MS Word also reads it from source as invalid JPG.
    /// </remarks>
    @Test
    public void test27097() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test27097.xml");
        msAssert.areEqual(4, doc.getChildNodes(NodeType.SHAPE, true).getCount());

        Shape shape = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getShapes().get(1);
        msAssert.areEqual(149184, shape.getImageData().getImageBytes().length);
    }

    /// <summary>
    /// WORDSNET-26696 GIF is converted to PNG after updating fields.
    /// </summary>
    @Test
    public void test26696() throws Exception
    {
        Document document = TestUtil.open("ImportDocx\\Test26696.docx");

        Shape shape = document.getFirstSection().getBody().getShapes().get(0);
        msAssert.areEqual(ImageType.GIF, shape.getImageData().getImageType());

        TestUtil.saveCheckGold(document, "ImportDocx\\Test26696.docx");
    }


    /// <summary>
    /// WORDSNET-27191 Hyperlink is changed after converting DOCX to DOC.
    /// MS Words splits hyperlink target into address and subaddress at '#' character.
    /// </summary>
    @Test
    public void test27191() throws Exception
    {
        Document document = TestUtil.open("ImportDocx\\Test27191.docx");

        FieldHyperlink field = (FieldHyperlink)document.getRange().getFields().get(0);

        msAssert.areEqual("https://www.votiro.com/portal/", field.getAddress());
        msAssert.areEqual("/d154e0b8cef6?url=http%3A%2F%2Fwww.google.com", field.getSubAddress());
    }


    /// <summary>
    /// Relates to WORDSNET-23187.
    /// Raises a warning if the decoding of protection password fails.
    /// </summary>
    @Test
    public void test27395Warning() throws Exception
    {
        WarningInfoCollection warnings = new WarningInfoCollection();
        LoadOptions loadOptions = new LoadOptions();
        loadOptions.setWarningCallback(warnings);

        Document doc = TestUtil.open("ImportDocx\\Test27395Warning.docx", loadOptions);

        msAssert.isTrue(TestUtil.containsWarning(warnings,
                WarningType.UNEXPECTED_CONTENT,
                WarningSource.DOCX,
                WarningStrings.UNEXPECTED_BASE_64));
    }

    /// <summary>
    /// Footnote with missing custom reference mark brakes outer table.
    /// </summary>
    @Test
    public void testImportMissingCustomFootnoteMark() throws Exception
    {
        Document document = TestUtil.open("ImportDocx\\TestImportMissingCustomFootnoteMark.docx");

        Table table = document.getFirstSection().getBody().getTables().get(0);

        msAssert.areEqual(2, table.getRows().getCount());

        msAssert.areEqual(2, table.getRows().get(0).getCount());
        msAssert.areEqual(2, table.getRows().get(1).getCount());

        msAssert.areEqual("\u0002\rTop left\u0007", table.getRows().get(0).getCells().get(0).getText());
        msAssert.areEqual("Top right\u0007", table.getRows().get(0).getCells().get(1).getText());
        msAssert.areEqual("Bottom left\u0007", table.getRows().get(1).getCells().get(0).getText());
        msAssert.areEqual("Bottom right\u0007", table.getRows().get(1).getCells().get(1).getText());
    }




    /// <summary>
    /// WORDSNET-27641 Redundant borders are shown after rendering.
    /// TableNormal style should contain predefined attributes only.
    /// </summary>
    @Test
    public void test27641() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test27641.dotx");
        TableStyle tableNormal = (TableStyle)doc.getStyles().getBySti(StyleIdentifier.TABLE_NORMAL, false);

        msAssert.isFalse(tableNormal.hasConditionalFormatting());

        for (int key : TablePr.POSSIBLE_BORDER_KEYS.getValues())
            msAssert.isNull(tableNormal.getTablePr().get(key));
    }

    /// <summary>
    /// WORDSNET-27690 FileCorruptedException is thrown upon loading DOCX document.
    /// Extended document properties are written as int rather than as bool. Added resilience.
    /// </summary>
    @Test (dataProvider = "test27690DataProvider")
    public void test27690(String fileName) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        msAssert.isFalse(doc.getBuiltInDocumentProperties().getScaleCrop());
        msAssert.isFalse(doc.getBuiltInDocumentProperties().getLinksUpToDate());
        msAssert.isFalse(doc.getBuiltInDocumentProperties().getSharedDocument());
        msAssert.isFalse(doc.getBuiltInDocumentProperties().getHyperlinksChanged());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27690DataProvider")
	public static Object[][] test27690DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"ImportDocx\\Test27690.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-27754 Paragraph borders are lost after open/save using Aspose.Words.
    /// Handle \box RTF keyword.
    /// </summary>
    /// <remarks>
    /// Placed test here since original issue relates to DOCX.
    /// </remarks>
    @Test (enabled = false)
    public void test27754(String testName) throws Exception
    {
        Document doc = TestUtil.open(testName);

        Paragraph para = TestUtil.getParagraphWithText(doc.getFirstSection().getBody(), "TEST\t1 Tab in Rahmen\r");
        int[] borderKeys = new int[]
        {
            ParaAttr.BORDER_TOP, ParaAttr.BORDER_BOTTOM, ParaAttr.BORDER_LEFT, ParaAttr.BORDER_RIGHT
        };

        for (int key : borderKeys)
            msAssert.areEqual(LineStyle.SINGLE, ((Border)para.getParaPr().get(key)).getLineStyle());
    }


    /// <summary>
    /// WORDSNET-28064 FileCorruptedException is thrown upon loading DOCX document.
    /// Invalid bool attribute value, added resilience.
    /// </summary>
    @Test
    public void test28064() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test28064.docx");
        Run run = doc.getFirstSection().getBody().getFirstParagraph().getRuns().get(2);

        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().get(FontAttr.HIDDEN));
    }



    /// <summary>
    /// WORDSNET-28435 SimHei font replaced with SimSun and became Regular instead of Bold upon DOCX to PDF.
    /// Do not override font that already set.
    /// </summary>
    @Test (dataProvider = "test28435DataProvider")
    public void test28435(String testName) throws Exception
    {
        Document doc = TestUtil.open(testName);

        ComplexFontName fontSimHei = ComplexFontName.fromName("黑体");
        msAssert.areEqual(fontSimHei, doc.getStyles().getDefaultRunPr().get(FontAttr.NAME_FAR_EAST));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28435DataProvider")
	public static Object[][] test28435DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"ImportDocx\\Test28435.docx"},
		};
	}

    /// <summary>
    /// Additional test for WORDSNET-28498.
    /// Checks that nodes have no shared RunPr collections.
    /// </summary>
    @Test (enabled = false)
    public void test28498A(String fileName) throws Exception
    {
        Assert.assertTrue(!hasSharedRunPr(TestUtil.open(fileName)));
    }

    /// <summary>
    /// Tests how the field numbering revision is imported from DOCX.
    /// </summary>
    @Test
    public void testFieldNumberingRevision() throws Exception
    {
        Document document = TestUtil.open("Model\\Revision\\TestFieldNumberingRevision.docx");

        for (FieldEnd node : (Iterable<FieldEnd>) document.getChildNodes(NodeType.FIELD_END, true))
            msAssert.isNotNull(node.getRunPr().get(RevisionAttr.NUMBER_REVISION));
    }

    /// <summary>
    /// WORDSNET-28365 Document.ExtractPages throws an exception with the message "Value cannot be null. (Parameter 'key')"
    /// Unexpected 'pic' element inside DML fallback caused bad document tree. Test simplified document.
    /// </summary>
    @Test
    public void test28365A() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test28365A.docx");
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();

        NodeCollection childNodes = para.getChildNodes(NodeType.ANY, false);

        msAssert.areEqual(2, childNodes.getCount());
        msAssert.areEqual(NodeType.RUN, childNodes.get(0).getNodeType());
        msAssert.areEqual(NodeType.SHAPE, childNodes.get(1).getNodeType());
    }

    /// <summary>
    /// WORDSNET-28848 InvalidCastException is thrown upon reading DOCX document.
    /// Invalid oMath caused DOCX reader out of sync.
    /// </summary>
    @Test
    public void test28848() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Test28848.docx");
        msAssert.areEqual(6, doc.getFirstSection().getHeadersFooters().getCount());
    }


    /// <summary>
    /// Checks that document nodes have no shared run properties collection.
    /// </summary>
    private static boolean hasSharedRunPr(Document doc)
    {
        for (Node thisNode : (Iterable<Node>) doc.getChildNodes(NodeType.ANY, true))
        {
            IInline thisInline = ms.as(thisNode, IInline.class);

            if (thisInline == null)
                continue;

            for (Node otherNode : (Iterable<Node>) doc.getChildNodes(NodeType.ANY, true))
            {
                IInline otherInline = ms.as(otherNode, IInline.class);

                if (otherInline == null)
                    continue;

                if (msObject.referenceEquals(thisInline, otherInline))
                    continue;

                // Check to RunPr sharing.
                if (msObject.referenceEquals(thisInline.getRunPr_IInline(), otherInline.getRunPr_IInline()))
                {
                    Debug.writeLine(msString.format("{0} <=> {1}", thisInline, otherInline));
                    return true;
                }
            }
        }

        return false;
    }

    private static void assertFieldResult(Field field, String text, RunPr runPr)
    {
        Run run = (Run)field.getSeparator().getNextSibling();

        msAssert.areEqual(text, run.getText());
        msAssert.isTrue(runPr.equals(run.getRunPr()), msString.format("{0} != {1}", runPr, run.getRunPr()));
    }

    /// <summary>
    /// Checks that outline level for specified paragraph equals to expected value.
    /// </summary>
    private static void checkHeadingParaOutlineLevel(Paragraph para, /*OutlineLevel*/int expectedLevel)
    {
        msAssert.areEqual(expectedLevel, (/*OutlineLevel*/Integer)para.fetchParaAttr(ParaAttr.OUTLINE_LEVEL, RevisionsView.FINAL));
        msAssert.areEqual(expectedLevel, para.getParagraphFormat().getOutlineLevel());
        // Word does not remove outline level for paragraphs in cells. Just fixes value according to applied style.
        msAssert.areEqual(para.isInCell(), para.getParaPr().contains(ParaAttr.OUTLINE_LEVEL));
    }

    private static void checkLineSpacing(Document doc, int index, int value, /*LineSpacingRule*/int rule)
    {
        ParaPr paraPr = doc.getFirstSection().getBody().getParagraphs().get(index).getParaPr();

        msAssert.areEqual(value, paraPr.getLineSpacing());
        msAssert.areEqual(rule, paraPr.getLineSpacingRule());
    }

    private static void checkFormattingAfterImport(Paragraph para, Document dstDoc, ImportFormatOptions options, Font font)
    {
        NodeImporter importer = new NodeImporter(para.getDocument(), dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING,
            options);
        Paragraph importPara = (Paragraph)importer.importNode(para, true);
        Run importRun = ((Shape)importPara.getFirstChild()).getFirstParagraph().getFirstRun();

        msAssert.areEqual(font.getSize(), importRun.getFont().getSize());
        msAssert.areEqual(font.getName(), importRun.getFont().getName());
    }

    /// <summary>
    /// Checks cells of the specified row are merged.
    /// </summary>
    private static void checkCellsMerged(Row row)
    {
        for (Cell cell : (Iterable<Cell>) row.getCells())
        {
            /*CellMerge*/int expectedHMerge = cell.isFirstCell() ? CellMerge.FIRST : CellMerge.PREVIOUS;
            msAssert.areEqual(expectedHMerge, cell.getCellPr().get(CellAttr.HORIZONTAL_MERGE));
        }
    }

    /// <summary>
    /// Retrieves first inline SDT and checks child nodes count.
    /// </summary>
    private static StructuredDocumentTag getFirstBlockLevelSdt(Document doc, int expChildCount)
    {
        StructuredDocumentTag sdtBlock =
            (StructuredDocumentTag)doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(MarkupLevel.BLOCK, sdtBlock.getLevel());
        msAssert.areEqual(expChildCount, sdtBlock.getCount());

        return sdtBlock;
    }

    /// <summary>
    /// Checks content of the cell.
    /// </summary>
    /// <param name="cell">Table cell.</param>
    /// <param name="expChildCount">Expected child of cell count.</param>
    /// <param name="expFirstParaChildCount">Expected child of first paragraph of the cell.</param>
    /// <param name="expText">Expected text of the first run of the specified cell,</param>
    private static void checkCellContent(Cell cell, int expChildCount, int expFirstParaChildCount, String expText)
    {
        msAssert.areEqual(expChildCount, cell.getCount());
        msAssert.areEqual(expFirstParaChildCount, cell.getFirstParagraph().getCount());
        msAssert.areEqual(expText, ((Run)cell.getFirstParagraph().getFirstChild()).getText());
    }

    /// <summary>
    /// Checks text in the table of the specified document.
    /// </summary>
    /// <param name="doc">Document for check.</param>
    /// <param name="firstCellText">Expected text in the first cell.</param>
    /// <param name="lastCellText">Expected text in the second cell.</param>
    private static void checkTableFirstRowText(Document doc, String firstCellText, String lastCellText)
    {
        Table tbl = (Table)doc.getFirstSection().getBody().getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual(firstCellText, tbl.getFirstRow().getFirstCell().getFirstParagraph().getFirstRun().getText());
        msAssert.areEqual(lastCellText, tbl.getFirstRow().getLastCell().getFirstParagraph().getFirstRun().getText());
    }

    /// <summary>
    /// Check that textboxes are related correct.
    /// </summary>
    /// <param name="doc">Document for check.</param>
    /// <param name="isDml">Shows that need to check "DrawingML" linked parameters.</param>
    private static void checkLinkedTxBxSeq(Document doc, boolean isDml)
    {
        // Get textboxes from document for check.
        Paragraph middlePar = doc.getLastSection().getBody().getParagraphs().get(1);

        Shape txbx = (Shape)middlePar.getChildNodes(NodeType.ANY, false).get(2);
        Shape lnkTxbx3 = (Shape)middlePar.getChildNodes(NodeType.ANY, false).get(0);
        Shape lnkTxbx2 = (Shape)middlePar.getChildNodes(NodeType.ANY, false).get(1);
        Shape rect = (Shape)doc.getLastSection().getBody().getFirstParagraph().getFirstChild();
        Shape lnkTxbx1 = (Shape)doc.getLastSection().getBody().getLastParagraph().getChildNodes(NodeType.ANY, false).get(0);

        checkNextShapeIds(rect, txbx, lnkTxbx1, lnkTxbx2, lnkTxbx3);

        if (!isDml)
            return;

        msAssert.isNotNull(rect.getTextBox());
        msAssert.areEqual(0, rect.getTextboxId());
        msAssert.areEqual(0, rect.getLinkedTextboxId());
        msAssert.areEqual(0, rect.getLinkedTextboxSeq());

        msAssert.isNotNull(txbx.getTextBox());
        msAssert.areEqual(0, txbx.getLinkedTextboxId());
        msAssert.areEqual(0, txbx.getLinkedTextboxSeq());

        msAssert.isNotNull(lnkTxbx1.getTextBox());
        msAssert.areEqual(0, lnkTxbx1.getTextboxId());
        msAssert.areEqual(1, lnkTxbx1.getLinkedTextboxSeq());

        msAssert.isNotNull(lnkTxbx2.getTextBox());
        msAssert.areEqual(0, lnkTxbx2.getTextboxId());
        msAssert.areEqual(2, lnkTxbx2.getLinkedTextboxSeq());

        msAssert.isNotNull(lnkTxbx3.getTextBox());
        msAssert.areEqual(0, lnkTxbx3.getTextboxId());
        msAssert.areEqual(3, lnkTxbx3.getLinkedTextboxSeq());

        // Check that textboxes are linked.
        msAssert.areEqual(lnkTxbx1.getLinkedTextboxId(), txbx.getTextboxId());
        msAssert.areEqual(lnkTxbx2.getLinkedTextboxId(), lnkTxbx1.getLinkedTextboxId());
        msAssert.areEqual(lnkTxbx3.getLinkedTextboxId(), lnkTxbx2.getLinkedTextboxId());
    }

    /// <summary>
    /// Checks that shapes are linked correctly.
    /// </summary>
    /// <param name="rect">Shape without text.</param>
    /// <param name="txbx">First textbox in the sequence.</param>
    /// <param name="lnkTxbx1">First linked textbox in the sequence.</param>
    /// <param name="lnkTxbx2">Second linked textbox in the sequence.</param>
    /// <param name="lnkTxbx3">Third linked textbox in the sequence.</param>
    private static void checkNextShapeIds(
        Shape rect,
        Shape txbx,
        Shape lnkTxbx1,
        Shape lnkTxbx2,
        Shape lnkTxbx3)
    {
        msAssert.areEqual(0, rect.getTextboxNextShapeId());
        msAssert.areEqual(lnkTxbx1.getId(), txbx.getTextboxNextShapeId());
        msAssert.areEqual(lnkTxbx2.getId(), lnkTxbx1.getTextboxNextShapeId());
        msAssert.areEqual(lnkTxbx3.getId(), lnkTxbx2.getTextboxNextShapeId());
        msAssert.areEqual(0, lnkTxbx3.getTextboxNextShapeId());
    }

    /// <summary>
    /// Import node to a new document.
    /// </summary>
    /// <param name="node">Node to import.</param>
    /// <returns>Imported node.</returns>
    private static Node importNode(Node node) throws Exception
    {
        Document doc = new Document();
        NodeImporter importer = new NodeImporter(node.getDocument(), doc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        return importer.importNode(node, true);
    }

    /// <summary>
    /// Save fragment of a document in text and check result.
    /// </summary>
    /// <param name="doc">AW document.</param>
    /// <param name="tw">Object for saving fragment of a document in text.</param>
    /// <param name="tso">Options for saving fragment of a document in text.</param>
    /// <param name="val">Expected result after saving.</param>
    private static void checkFieldExportText(Document doc, TxtWriter tw, TxtSaveOptions tso, String val) throws Exception
    {
        Paragraph fp = doc.getFirstSection().getBody().getFirstParagraph();
        // Here should not be throw the "ArgumentNullException".
        msAssert.areEqual(val, tw.saveToString(fp, tso));
    }

    /// <summary>
    /// Check width of the shapes according to expected values.
    /// </summary>
    /// <param name="sec">Section in document with shapes.</param>
    /// <param name="lineWidth">Expected line width.</param>
    /// <param name="notInlineGrW">Expected width of the floating group shape.</param>
    /// <param name="inlineGrW">Expected width of the in-line group shape.</param>
    private static void checkShapesWidth(Section sec, double lineWidth, double notInlineGrW, double inlineGrW)
    {
        // See comment in <see cref="TestShapePosition.CheckSizeRelative"/> method.
        final double DELTA = 0.05;

        Body b = sec.getBody();

        Paragraph fp = b.getFirstParagraph();
        // Check width of the line with arrow.
        msAssert.areEqual(lineWidth, DELTA, ((Shape)fp.getChild(NodeType.SHAPE, 0, false)).getWidth());

        NodeCollection cn = b.getLastParagraph().getChildNodes(NodeType.ANY, false);
        // Check width of the in-line star shape.
        msAssert.areEqual(65.76d, DELTA, ((Shape)cn.get(0)).getWidth());
        // Check width of the floating group shape.
        msAssert.areEqual(notInlineGrW, DELTA, ((GroupShape)cn.get(1)).getWidth());
        // Check width of the text box shape.
        msAssert.areEqual(65.92d, DELTA, ((Shape)cn.get(2)).getWidth());
        // Check width of the rectangle shape.
        msAssert.areEqual(68.83d, DELTA, ((Shape)cn.get(3)).getWidth());
        // Check width of the in-line group shape.
        // For VML in MSW 2013 in-line group width re-calculated according to percent width.
        msAssert.areEqual(inlineGrW, DELTA, ((GroupShape)cn.get(4)).getWidth());
    }

    /// <summary>
    /// Validate document run content.
    /// </summary>
    /// <param name="doc">Document for processing.</param>
    private static void checkWrappedHyperlink(Document doc)
    {
        final String FR_TEXT = "Run text";
        final String HPL_TEXT = "[2011 MR 679]";
        final String STD_TEXT = "\rsdtContent text";
        final String HPL_REF = " HYPERLINK \"http://svt-pc-142:45903/\" \\t \"_blank\" ";

        // Case when hyper-link placed among another content inside "Run" element.
        Paragraph fp = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(FR_TEXT, fp.getRuns().get(0).getText());
        msAssert.areEqual(HPL_REF, fp.getRuns().get(1).getText());
        msAssert.areEqual(HPL_TEXT, fp.getRuns().get(2).getText());
        // Check picture existence.
        msAssert.areEqual(NodeType.SHAPE, fp.getLastChild().getNodeType());

        // Case when hyper-link is multiple nesting into "Run" and another element.
        CompositeNode cn = doc.getFirstSection().getBody().getLastParagraph();

        if (doc.getOriginalLoadFormat() == LoadFormat.DOCX)
            cn = (StructuredDocumentTag)cn.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, false);

        NodeCollection childs = cn.getChildNodes(NodeType.ANY, false);
        msAssert.areEqual(HPL_REF, ((Run)childs.get(1)).getText());
        msAssert.areEqual(HPL_TEXT, ((Run)childs.get(3)).getText());
        msAssert.areEqual(STD_TEXT, ((Run)childs.get(5)).getText());
    }

    /// <summary>
    /// Get first warning with "DrawingML" source.
    /// </summary>
    /// <param name="twc">Source with warning info elements.</param>
    /// <returns>Warning with "DrawingML" source.</returns>
    private static WarningInfo getFirstDmlWarning(TestWarningCallback twc)
    {
        WarningInfo dmlWarning = null;

        for (int i = 0; i < twc.getCount(); ++i)
        {
            WarningInfo wi = twc.get(i);
            if (wi.getSource() == WarningSource.DRAWING_ML)
            {
                dmlWarning = wi;
                break;
            }
        }

        return dmlWarning;
    }

    /// <summary>
    /// Checks position and size of the specified shape.
    /// </summary>
    private static void checkShapePosAndSize(Shape shape, double expectedLeft, double expectedTop, double expectedWidth,
        double expectedHeight)
    {
        msAssert.areEqual(msMath.round(expectedLeft, 2), msMath.round(shape.getLeft(), 2), "The left margin is wrong.");
        msAssert.areEqual(msMath.round(expectedTop, 2), msMath.round(shape.getTop(), 2), "The top margin is wrong.");
        msAssert.areEqual(msMath.round(expectedWidth, 2), msMath.round(shape.getWidth(), 2), "The width is wrong.");
        msAssert.areEqual(msMath.round(expectedHeight, 2), msMath.round(shape.getHeight(), 2), "The height is wrong.");
    }

    /// <summary>
    /// Calculates total cells width for given row.
    /// </summary>
    private static int cellsWidth(Row row)
    {
        int cellsWidth = 0;
        for (Cell cell : (Iterable<Cell>) row)
            cellsWidth += cell.getCellPr().getWidth();

        return cellsWidth;
    }

    /// <summary>
    /// Utility method, checks that border is resolved correctly for given cell.
    /// </summary>
    private static void verifyBorderStyle(Cell cell, int borderKey, /*LineStyle*/int lineStyle)
    {
        Border border = (Border)cell.getInheritedCellAttr(borderKey);
        msAssert.areEqual(lineStyle, border.getLineStyle());
    }

    /// <summary>
    /// Implements checking margins values for table cells.
    /// </summary>
    /// <param name="tbl">Table for check.</param>
    /// <param name="l">Left margin value.</param>
    /// <param name="r">Right margin value.</param>
    /// <param name="u">Upper margin value.</param>
    /// <param name="b">Bottom margin value.</param>
    /// <param name="cellAttrSkipped">True when margin attributes on cell level should be skipped.</param>
    private static void checkTableCellsMargin(
        Table tbl,
        double l,
        double r,
        double u,
        double b,
        boolean cellAttrSkipped)
    {
        for (Row row : (Iterable<Row>) tbl.getRows())
        {
            for (Cell c : (Iterable<Cell>) row.getCells())
            {
                CellFormat cf = c.getCellFormat();
                msAssert.areEqual(u, cf.getTopPadding());
                msAssert.areEqual(l, cf.getLeftPadding());
                msAssert.areEqual(r, cf.getRightPadding());
                msAssert.areEqual(b, cf.getBottomPadding());
                for (int pt : G_PADDING_TYPES)
                {
                    if (cellAttrSkipped)
                        msAssert.isNull(c.getCellPr().get(pt));
                    else
                        msAssert.isNotNull(c.getCellPr().get(pt));
                }
            }
        }
    }

    /// <summary>
    /// Get table in first section of the document body by index.
    /// </summary>
    /// <param name="doc"> Document for search.</param>
    /// <param name="index"> Table index.</param>
    /// <returns> Table, if it is found.</returns>
    private static Table getTableByIndex(Document doc, int index)
    {
        return (Table)doc.getFirstSection().getBody().getChild(NodeType.TABLE, index, false);
    }

    /// <summary>
    /// Get count of immediate child nodes with the specified type.
    /// </summary>
    /// <remarks>
    /// Actually <see cref="NodeCollection"/> can be used to calculate child nodes count
    /// with specified type. However, for SDT container can be returned invalid count of
    /// child nodes. See, WORDSNET-15251
    /// </remarks>
    /// <param name="container">Ancestor node.</param>
    /// <param name="nodeType">Type of child nodes.</param>
    /// <returns>Child nodes count.</returns>
    private static int getChildNodesCount(CompositeNode container, /*NodeType*/int nodeType)
    {
        int count = 0;
        for (Node node = container.getFirstChild(); node != null; node = node.getNextSibling())
            count = (nodeType == node.getNodeType()) ? ++count : count;
        return count;
    }

    /// <summary>
    /// Cell margin attributes keys.
    /// </summary>
    private static /*final*/ int[] G_PADDING_TYPES = new int[]
    {
        CellAttr.TOP_PADDING,
        CellAttr.LEFT_PADDING,
        CellAttr.RIGHT_PADDING,
        CellAttr.BOTTOM_PADDING
    };
}

