// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.ms.System.msString;
import com.aspose.DrColor;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ms.System.IO.Path;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Tests related to importing of styles between different documents.
/// </summary>
@Test
public class TestStyleImport extends UnifiedTestsBase
{
    /// <summary>
    /// Test importing of paragraphs and runs that creates new styles in the destination document.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImportStyle_UseDestinationStyles(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document dst = doTestImportStyle(ImportFormatMode.USE_DESTINATION_STYLES, lf, sf);

        //These styles were not created because destination styles were used instead.
        msAssert.isNull(dst.getStyles().get("Normal_0"));
        msAssert.isNull(dst.getStyles().get("Default Paragraph Format_0"));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImportStyle_UseDestinationStylesSourceDataProvider")
	public void unifiedTestImportStyle_UseDestinationStylesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImportStyle_UseDestinationStyles(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImportStyle_UseDestinationStylesSourceDataProvider")
	Object[][] unifiedTestImportStyle_UseDestinationStylesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImportStyle_KeepSourceFormatting(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document dst = doTestImportStyle(ImportFormatMode.KEEP_SOURCE_FORMATTING, lf, sf);

        // Make sure Normal style is not copied.
        msAssert.isNull(dst.getStyles().get("Normal_0"));

        // Make sure DefaultParagraphFont style is not copied.
        // There is no need to because it cannot be modified.
        // Also, if we copy it and try to modify in Microsoft Word, it crashes.
        msAssert.isNull(dst.getStyles().get("Default Paragraph Font_0"));

        //This built in style was copied.
        Style style = dst.getStyles().get("Heading 4");
        msAssert.isNotNull(style);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImportStyle_KeepSourceFormattingSourceDataProvider")
	public void unifiedTestImportStyle_KeepSourceFormattingSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImportStyle_KeepSourceFormatting(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImportStyle_KeepSourceFormattingSourceDataProvider")
	Object[][] unifiedTestImportStyle_KeepSourceFormattingSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static Document doTestImportStyle(/*ImportFormatMode*/int importFormatMode, /*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document dst = new Document();
        Document src = TestUtil.openSaveOpen("Model\\Style\\TestImportStyle", lf, sf);

        Paragraph srcPara = src.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(Color.RED.getRGB(), srcPara.getParagraphBreakFont().getColor().getRGB());

        dst.getSections().clear();
        dst.getSections().add(dst.importNode(src.getSections().get(0), true, importFormatMode));
        TestUtil.saveOpen(dst, msString.format("Model\\Style\\TestImportStyle_{0}", importFormatModeToStr(importFormatMode)), lf, sf);
        ParagraphCollection paras = dst.getFirstSection().getBody().getParagraphs();
        if (importFormatMode == ImportFormatMode.KEEP_SOURCE_FORMATTING)
        {
            // Heading 4 source style should be expanded into the direct attributes and removed from the paragraph,
            // when 'KeepSourceFormatting' mode is used.
            msAssert.isNull(paras.get(0).getParaPr().get(ParaAttr.ISTD));

            // Importing this node expands Title and Hyperlink properties into the direct formatting,
            // when 'KeepSourceFormatting' mode is used.
            msAssert.isNull(paras.get(1).getParaPr().get(ParaAttr.ISTD));
            msAssert.isNull(paras.get(1).getFirstRun().getRunPr().get(FontAttr.ISTD));

            // Importing this node expands MyStyle, MyCharStyle, Signature and Heading 9 properties into direct formatting.
            msAssert.isNull(paras.get(2).getParaPr().get(ParaAttr.ISTD));
            msAssert.isNull(paras.get(2).getRuns().get(1).getRunPr().get(FontAttr.ISTD));
        }
        else
        {
            msAssert.areEqual(dst.getStyles().get("Heading 4").getIstd(), paras.get(0).getParaPr().get(ParaAttr.ISTD));

            msAssert.areEqual(dst.getStyles().get("Title").getIstd(), paras.get(1).getParaPr().get(ParaAttr.ISTD));
            msAssert.areEqual(dst.getStyles().get("Hyperlink").getIstd(), paras.get(1).getFirstRun().getRunPr().get(FontAttr.ISTD));

            msAssert.areEqual(dst.getStyles().get("MyStyle").getIstd(), paras.get(2).getParaPr().get(ParaAttr.ISTD));
            msAssert.areEqual(dst.getStyles().get("MyCharStyle").getIstd(), paras.get(2).getRuns().get(1).getRunPr().get(FontAttr.ISTD));
        }

        msAssert.areEqual(DrColor.RED, paras.get(0).getParagraphBreakFont().getColorInternal());

        msAssert.areEqual(DrColor.BLUE, paras.get(1).getParagraphBreakFont().getColorInternal());
        msAssert.areEqual(Underline.DOTTED_HEAVY, paras.get(1).getFirstRun().getFont().getUnderline());

        msAssert.areEqual(DrColor.GREEN, paras.get(2).getParagraphBreakFont().getColorInternal());
        msAssert.isTrue(paras.get(2).getRuns().get(1).getFont().getBold());

        return dst;
    }

    /// <summary>
    /// Must do this instead of enum.ToString because on Java this is just an integer.
    /// </summary>
    private static String importFormatModeToStr(/*ImportFormatMode*/int ifm)
    {
        switch (ifm)
        {
            case ImportFormatMode.KEEP_SOURCE_FORMATTING:
                return "KeepSourceFormatting";
            case ImportFormatMode.USE_DESTINATION_STYLES:
                return "UseDestinationStyles";
            default:
                throw new IllegalStateException("Unknown import format mode.");
        }
    }

    /// <summary>
    /// Test importing some nodes when the style already exists. The references to the
    /// style must be correctly updated, but the style in the destination document is not modified.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestImportStyleExists_UseDestinationStyles(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document dst = TestUtil.openSaveOpen("Model\\Style\\TestImportStyleExistsDst", lf, sf);
        Document src = TestUtil.openSaveOpen("Model\\Style\\TestImportStyleExistsSrc", lf, sf);

        //This is a simple case where translation of istd is not required.
        //Heading 1 is black in the destination
        msAssert.areEqual(0, dst.getStyles().get("Heading 1").getFont().getColor().getRGB());

        //Paragraph is red Heading 1 in the source.
        Paragraph srcPara = src.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual("Heading 1", srcPara.getParagraphFormat().getStyleName());
        msAssert.areEqual(Color.RED.getRGB(), srcPara.getParagraphBreakFont().getColor().getRGB());

        //When imported, Heading 1 is not changed (black in the destination).
        Paragraph dstPara = (Paragraph)dst.importNode(srcPara, true);
        msAssert.areEqual("Heading 1", dstPara.getParagraphFormat().getStyleName());
        msAssert.areEqual(0, dstPara.getParagraphBreakFont().getColor().getRGB());
        dst.getFirstSection().getBody().appendChild(dstPara);


        //Another case when a built in paragraph and character styles already exists in the destination,
        //but istds must be translated.
        //Verify istds are initially different.
        msAssert.areEqual(15, dst.getStyles().get("Title").getIstd());
        msAssert.areEqual(17, dst.getStyles().get("Hyperlink").getIstd());
        msAssert.areEqual(20, src.getStyles().get("Title").getIstd());
        msAssert.areEqual(22, src.getStyles().get("Hyperlink").getIstd());

        //Check Title and Hyperlink in the source
        srcPara = src.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual("Title", srcPara.getParagraphFormat().getStyleName());
        msAssert.areEqual(Color.BLUE.getRGB(), srcPara.getParagraphBreakFont().getColor().getRGB());
        msAssert.areEqual("Hyperlink", srcPara.getRuns().get(0).getFont().getStyleName());
        msAssert.areEqual(Underline.SINGLE, srcPara.getRuns().get(0).getFont().getUnderline());

        //When imported, it remains Title and Hyperlink and the styles are not changed
        //in the destination, this however required istd of the paragraph and run properties translated.
        dstPara = (Paragraph)dst.importNode(srcPara, true);
        msAssert.areEqual("Title", dstPara.getParagraphFormat().getStyleName());
        msAssert.areEqual(0, dstPara.getParagraphBreakFont().getColor().getRGB());
        msAssert.areEqual("Hyperlink", dstPara.getRuns().get(0).getFont().getStyleName());
        msAssert.areEqual(Underline.DOUBLE, dstPara.getRuns().get(0).getFont().getUnderline());
        dst.getFirstSection().getBody().appendChild(dstPara);


        //Test of a user defined paragraph style. It's green in the source document.
        srcPara = src.getFirstSection().getBody().getParagraphs().get(2);
        msAssert.areEqual("MyStyle", srcPara.getParagraphFormat().getStyleName());
        msAssert.areEqual(DrColor.GREEN, srcPara.getParagraphBreakFont().getColorInternal());

        //When imported, it remains MyStyle and the style is not changed in the destination.
        dstPara = (Paragraph)dst.importNode(srcPara, true);
        msAssert.areEqual("MyStyle", dstPara.getParagraphFormat().getStyleName());
        msAssert.areEqual(Color.RED.getRGB(), dstPara.getParagraphBreakFont().getColor().getRGB());
        dst.getFirstSection().getBody().appendChild(dstPara);

        TestUtil.saveOpen(dst, "Model\\Style\\TestImportStyleExists", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestImportStyleExists_UseDestinationStylesSourceDataProvider")
	public void unifiedTestImportStyleExists_UseDestinationStylesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestImportStyleExists_UseDestinationStyles(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestImportStyleExists_UseDestinationStylesSourceDataProvider")
	Object[][] unifiedTestImportStyleExists_UseDestinationStylesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    private static void checkLinkedHeading1(Document doc)
    {
        msAssert.areEqual(1, doc.getStyles().get("Heading 1").getIstd());
        msAssert.areEqual(15, doc.getStyles().get("Heading 1 Char").getIstd());
        msAssert.areEqual(1, doc.getStyles().get("Heading 1 Char").getLinkedIstd());
    }

    /// <summary>
    /// WORDSNET-3658 "Cannot add a style because a style with the same name already exists”
    /// exception occurs during inserting one document into another.
    /// RK The problem occurs when destination document contains a user defined style with a name
    /// of a built in style.
    /// </summary>
    @Test
    public void testDefect3658() throws Exception
    {
        // Destination document with a user defined style with a name of a built in style.
        Document dstDoc = new Document();
        dstDoc.getStyles().add(StyleType.PARAGRAPH, "Balloon Text");
        dstDoc.removeAllChildren();

        // Source document with a built in style.
        Document srcDoc = TestUtil.open("Model\\Style\\TestDefect3658.docx");

        // There was an exception during import.
        for (Section srcSection : (Iterable<Section>) srcDoc.getSections())
        {
            Node dstSection = dstDoc.importNode(srcSection, true, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            dstDoc.getSections().add(dstSection);
        }

        Paragraph para = (Paragraph)dstDoc.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual("Balloon Text\f", para.getText());
        // A new user defined style is created, because the destination document already contains a style with this name.
        // WORDSNET-14587 All properties are expanded into direct paragraph formatting
        // and style of paragraph is changed to Normal.
        msAssert.areEqual("Normal", para.getParagraphFormat().getStyleName());
        msAssert.areEqual(StyleIdentifier.NORMAL, para.getParagraphFormat().getStyleIdentifier());

        TestUtil.save(dstDoc, "Model\\Style\\TestDefect3658.docx");
    }





    /// <summary>
    /// WORDSNET-5736 InvalidOperationException: occurs when appending two documents
    /// </summary>
    @Test
    public void testDefect5736() throws Exception
    {
        Document doc = new Document();
        Document doc2 = TestUtil.open("Model\\Style\\TestDefect5736.docx");

        doc.appendDocument(doc2, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        TestUtil.saveOpen(doc, "Model\\Style\\TestDefect5736.docx");
    }


    /// <summary>
    /// WORDSNET-6842 Style is imported incorrectly in destination document during appending documents.
    /// andrnosk: Test how rPr defaults resolved upon import style with KeepSourceFormatting.
    /// </summary>
    @Test
    public void testJira6842B() throws Exception
    {
        Document dst = TestUtil.open("Model\\Style\\TestJira6842dstB.docx");
        Document src = TestUtil.open("Model\\Style\\TestJira6842srcB.docx");

        // Check rPr defaults.
        msAssert.areEqual("Times New Roman", dst.getStyles().getDefaultRunPr().getNameAscii());
        msAssert.areEqual("Arial", src.getStyles().getDefaultRunPr().getNameAscii());

        Style dstStyle = dst.getStyles().get("Normal");
        msAssert.areEqual("Times New Roman", dstStyle.getRunPr().getNameAscii());

        dst.appendDocument(src, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        RunPr runPr = dst.getSections().get(1).getBody().getFirstParagraph().getFirstRun().getRunPr();
        msAssert.areEqual(36, runPr.get(FontAttr.SIZE));
        msAssert.isNull(runPr.get(FontAttr.SIZE_BI));
        msAssert.areEqual(DrColor.fromArgb(0xFF, 0xFF, 0x00, 0x00), runPr.get(FontAttr.COLOR));
        msAssert.areEqual(ComplexFontName.fromName("Arial"), runPr.get(FontAttr.NAME_ASCII));

        TestUtil.saveOpen(dst, "Model\\Style\\TestJira6842B.docx");
    }


    /// <summary>
    /// Tests how content with themes is imported if themes are different in source and destination.
    /// </summary>
    @Test
    public void testJira3312_ImportDifferentThemes() throws Exception
    {
        Document dst = TestUtil.open("Model\\Style\\TestThemedStyles1.docx");
        Document src = TestUtil.open("Model\\Style\\TestThemedStyles2.docx");
        dst.appendDocument(src, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        dst.joinRunsWithSameFormatting();

        Body importedBody = dst.getSections().get(1).getBody();

        // [0]
        Paragraph para = importedBody.getParagraphs().get(0);

        // This run has no attributes. So "Book Antiqua (Body)" font should applied to this run.
        // Because themes are different this difference should be applied to imported paragraph style as normal font name.
        // WORDSNET-14587 Actually, Word expands all properties into direct paragraph/run formatting and removes style from them.
        Run run = para.getRuns().get(0);
        Style style = dst.getStyles().getByIstd(para.getParaPr().getIstd(), false);
        msAssert.areEqual("Normal", style.getName());

        msAssert.areEqual(ComplexFontName.fromName("Book Antiqua"), run.getRunPr().get(FontAttr.NAME_ASCII));

        run = para.getRuns().get(1);
        msAssert.areEqual(ComplexFontName.fromName("Arial Narrow"), run.getRunPr().get(FontAttr.NAME_ASCII));

        // [1]
        para = importedBody.getParagraphs().get(1);
        style = dst.getStyles().getByIstd(para.getParaPr().getIstd(), false);
        msAssert.areEqual("Normal", style.getName());

        // This run has "Heading 2" style which has "Lucida Sans (Headings)" font.
        // This also should be resolved to simple font name in direct run properties.
        ComplexFontName fontNameLucidaSans = ComplexFontName.fromName("Lucida Sans");
        run = para.getRuns().get(0);
        msAssert.areEqual(fontNameLucidaSans, run.getRunPr().get(FontAttr.NAME_ASCII));
        // ThemeColor attribute had to be expanded into direct run properties.
        msAssert.areEqual(DrColor.fromArgb(0xFF, 0xCE, 0xB9, 0x66), run.getRunPr().get(FontAttr.COLOR));

        // [3]
        // This run had directly applied theme font so it has to be converted to simple font.
        para = importedBody.getParagraphs().get(3);
        run = para.getRuns().get(1);
        msAssert.areEqual(fontNameLucidaSans, run.getRunPr().get(FontAttr.NAME_ASCII));

        // Visual check.
        TestUtil.save(dst, "Model\\Style\\TestThemedStyles.docx");
    }

    /// <summary>
    /// WORDSNET-8863 Style inheritance does not work when copying style to another document
    /// Consider Normal style for every style imported.
    /// </summary>
    @Test
    public void testJira8863() throws Exception
    {
        Document dst = new Document();
        Document src = TestUtil.open("Model\\Style\\TestJira8863.docx");
        dst.appendDocument(src, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        // WORDSNET-14587 Style properties had to be expanded into direct run properties.
        Paragraph para = dst.getSections().get(1).getBody().getLastParagraph();
        // The problematic run with the text "Intense".
        // The customer complains about the font is changed from 'Calibri' to 'Georgia'.
        Run run = para.getRuns().get(1);
        RunPr runPr = run.getRunPr();
        msAssert.isNull(runPr.get(FontAttr.ISTD));
        msAssert.areEqual("Calibri", runPr.getComplexNameAscii().getName());
        msAssert.areEqual("Calibri", runPr.getComplexNameOther().getName());
        msAssert.areEqual("Calibri", runPr.getComplexNameFarEast().getName());
        msAssert.isTrue(runPr.getComplexNameBi().isThemeFont());
        msAssert.areEqual(ThemeFontCore.MINOR_BIDI, runPr.getComplexNameBi().getThemeFontCore());
    }





    /// <summary>
    /// WORDSNET-9441 (fixed by WORDSNET-8638) - Font size in table cells is not preserved during saving DOCX to PDF/HTML.
    /// Test with default FontSize = 10.
    /// </summary>
    @Test
    public void testJira9441() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira9441.docx");

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        RunPr dstRunPr = new RunPr();
        table.getRows().get(1).getCells().get(1).getFirstParagraph().getFirstRun().expandRunPr(dstRunPr, RunPrExpandFlags.NORMAL);

        msAssert.areEqual(20, dstRunPr.get(FontAttr.SIZE));
        msAssert.areEqual(24, table.getStyle().getRunPr().get(FontAttr.SIZE));
        msAssert.areEqual(22, doc.getStyles().getDefaultRunPr().get(FontAttr.SIZE));
    }

    /// <summary>
    /// WORDSNET-10697 Test that special base styles are handled differently during style import.
    /// e.g. DefaultParagraphFont.
    /// </summary>
    @Test
    public void testJira11294_1() throws Exception
    {
        Document srcDoc = new Document();
        srcDoc.getStyles().getBySti(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, true).getFont().setAllCaps(true);

        Style source = Style.create(StyleType.CHARACTER, 100, StyleIdentifier.USER, "myStyle1");
        source.setBasedOnIstd(StyleIndex.DEFAULT_PARAGRAPH_FONT);
        srcDoc.getStyles().add(source);

        Document dstDoc = new Document();
        dstDoc.getStyles().getBySti(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, true).getFont().setColor(msColor.getAqua());
        Style destination = Style.create(StyleType.CHARACTER, 101, StyleIdentifier.USER, "myStyle1");
        destination.setBasedOnIstd(StyleIndex.DEFAULT_PARAGRAPH_FONT);
        dstDoc.getStyles().add(destination);

        msAssert.isTrue(source.equals(destination));

        Style importedStyle = dstDoc.getStyles().importStyle(
            new ImportContext(srcDoc, dstDoc, ImportFormatMode.KEEP_DIFFERENT_STYLES),
            source);

        msAssert.isNull(dstDoc.getStyles().getByName("myStyle1_0", false));
        msAssert.areSame(destination, importedStyle);

    }

    /// <summary>
    /// WORDSNET-10697 Test that styles with same original names from multiple document get reused.
    /// </summary>
    @Test
    public void testJira11294_2() throws Exception
    {

        Document doc1 = new Document();
        Style style1 = populateTestDocument(doc1, "Line1");
        style1.getFont().setBold(true);

        Document doc2 = new Document();
        Style style2 = populateTestDocument(doc2, "Line2");
        style2.getFont().setItalic(true);

        Document doc3 = new Document();
        Style style3 = populateTestDocument(doc3, "Line3");
        style3.getFont().setBold(true);

        doc1.appendDocument(doc2, ImportFormatMode.KEEP_DIFFERENT_STYLES);
        doc1.appendDocument(doc3, ImportFormatMode.KEEP_DIFFERENT_STYLES);
        doc1.appendDocument(doc2, ImportFormatMode.KEEP_DIFFERENT_STYLES);

        msAssert.isNotNull(doc1.getStyles().getByName("myStyle1_0", false));
        msAssert.isNull(doc1.getStyles().getByName("myStyle1_1", false));
    }









    /// <summary>
    /// WORDSNET-21218 Left Indent lost after Appending Document.
    /// This issue duplicates a part of WORDSNET-20704
    /// </summary>
    @Test
    public void test21218() throws Exception
    {
        Document dstDoc = new Document();
        Document srcDoc = TestUtil.open("Model\\Style\\Test21218.docx");

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_DIFFERENT_STYLES);

        // The problematic style.
        Style style = dstDoc.getStyles().get("PIB-Modulüberschrift");
        msAssert.areEqual(0, style.getParaPr().get(ParaAttr.LEFT_INDENT));
        msAssert.areEqual(-227, style.getParaPr().get(ParaAttr.FIRST_LINE_INDENT));
    }

    /// <summary>
    /// WORDSNET-21615 ImportNode() function causes Exception: "Index was out of range".
    /// The reference style of the numbered style is not yet set properly, but we try to get list properties from it.
    /// Solved by using formatting properties from the source list in StyleCollection.CopyParaPr()
    /// instead of using destination list.
    /// </summary>
    @Test
    public void test21615() throws Exception
    {
        Document dstDoc = new Document();
        Document srcDoc = TestUtil.open("Model\\Style\\Test21615.docx");

        dstDoc.removeAllChildren();
        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        dstDoc.updateListLabels();

        // The problematic paragraphs are inside sdt.
        StructuredDocumentTag sdt = (StructuredDocumentTag)dstDoc.getFirstSection().getBody().getFirstChild();
        Paragraph para = (Paragraph)sdt.getFirstChild();
        msAssert.areEqual("1.", para.getListLabel().getLabelString());
        para = (Paragraph)para.getNextSibling();
        msAssert.areEqual("a)", para.getListLabel().getLabelString());
    }


    private static Style populateTestDocument(Document doc, String text)
    {
        Style result = doc.getStyles().add(StyleType.CHARACTER, "myStyle1");
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.getFont().setStyle(result);
        builder.writeln(text);
        return result;
    }

    /// <summary>
    /// Imports tables into the new document specified number times, using ImportFormatMode.KeepDifferentStyles.
    /// </summary>
    private static Document importDocument(Document srcDoc, int count) throws Exception
    {
        Document dstDoc = new Document();

        NodeCollection srcTables = srcDoc.getFirstSection().getBody().getChildNodes(NodeType.TABLE, true);

        for (int i = 0; i < count; i++)
            for (Table table : (Iterable<Table>) srcTables)
            {
                Node importedNode = dstDoc.importNode(table, true, ImportFormatMode.KEEP_DIFFERENT_STYLES);
                dstDoc.getFirstSection().getBody().appendChild(importedNode);
            }

        return dstDoc;
    }

    /// <summary>
    /// Checks equality of the following styles paragraph format properties:
    /// FirstLineIndent, LeftIndent.
    /// </summary>
    private static void checkStylesParaFormatsEqual(Document srcDoc, Document dstDoc, String styleName)
    {
        ParagraphFormat paraFormatA = srcDoc.getStyles().get(styleName).getParagraphFormat();
        ParagraphFormat paraFormatB = dstDoc.getStyles().get(styleName).getParagraphFormat();

        msAssert.areEqual(paraFormatA.getFirstLineIndent(), paraFormatB.getFirstLineIndent());
        msAssert.areEqual(paraFormatA.getLeftIndent(), paraFormatB.getLeftIndent());


    }

    /// <summary>
    /// Checks equality of the following styles list format properties:
    /// NumberFormat, NumberPosition, TabPosition, TextPosition.
    /// </summary>
    private static void checkStylesListFormatsEqual(Document srcDoc, Document dstDoc, String styleName)
    {
        ListLevel listLevelA = srcDoc.getStyles().get(styleName).getListFormat().getListLevel();
        ListLevel listLevelB = dstDoc.getStyles().get(styleName).getListFormat().getListLevel();

        msAssert.areEqual(listLevelA.getNumberFormat(), listLevelB.getNumberFormat());
        msAssert.areEqual(listLevelA.getNumberPosition(), listLevelB.getNumberPosition());
        msAssert.areEqual(listLevelA.getTabPosition(), listLevelB.getTabPosition());
        msAssert.areEqual(listLevelA.getTextPosition(), listLevelB.getTextPosition());
    }

    /// <summary>
    /// Checks font size after <see cref="TableFormattingExpander.ExpandRunPr"/> executed.
    /// </summary>
    private static Document checkExpandedFontSize(String fileName, int runIdxToCheck, int expectedFontSize) throws Exception
    {
        Document doc = TestUtil.open(Path.combine("Model\\Style\\", fileName));

        // We are using DocumentValidator here instead of Run.ExpandRunPr() because it may
        // additionally change 'overrideTableStyleFontSizeAndJustification' option and also makes to expand document defaults.
        TestUtil.executeValidator(doc, SaveFormat.MARKDOWN);

        Run run = (Run)doc.getChild(NodeType.RUN, runIdxToCheck, true);
        msAssert.areEqual(expectedFontSize, run.getRunPr().getSize());

        return doc;
    }
}

