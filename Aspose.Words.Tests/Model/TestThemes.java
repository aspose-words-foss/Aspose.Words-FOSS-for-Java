// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/06/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msObject;
import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.msString;
import java.awt.Color;
import com.aspose.Language;
import com.aspose.DrColor;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.annotations.DataProvider;


@Test
public class TestThemes
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests how theme fonts are accessed public.
    /// </summary>
    @Test
    public void testThemeFonts() throws Exception
    {
        final String TEST_NAME = "Model\\Theme\\TestThemeFonts.docx";
        Document doc = TestUtil.open(TEST_NAME);

        // Check few fonts.
        Theme theme = doc.getTheme();
        msAssert.areEqual("Cooper Black", theme.getMajorFonts().getLatin());
        msAssert.areEqual("Consolas", theme.getMinorFonts().getLatin());
        msAssert.areEqual("", theme.getMinorFonts().getEastAsian());

        // Set to new values.
        theme.getMajorFonts().setLatin("Times New Roman");
        theme.getMajorFonts().setEastAsian(null);
        theme.getMinorFonts().setLatin("Arial");
        theme.getMinorFonts().setEastAsian("MS Mincho");

        doc = TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        theme = doc.getTheme();
        msAssert.areEqual("Times New Roman", theme.getMajorFonts().getLatin());
        msAssert.areEqual("", theme.getMajorFonts().getEastAsian());
        msAssert.areEqual("Arial", theme.getMinorFonts().getLatin());
        msAssert.areEqual("MS Mincho", theme.getMinorFonts().getEastAsian());
    }

    /// <summary>
    /// Tests how theme fonts are accessed public.
    /// </summary>
    @Test
    public void testThemeSupplementalFonts() throws Exception
    {
        final String TEST_NAME = "Model\\Theme\\TestThemeSupplimentalFonts.docx";
        Document doc = TestUtil.open(TEST_NAME);

        Theme theme = doc.getTheme();
        msAssert.areEqual(30, doc.getTheme().getMajorFonts().getSupplementalFonts().size());
        msAssert.areEqual(30, doc.getTheme().getMinorFonts().getSupplementalFonts().size());

        // Modify theme.
        theme.getMajorFonts().setLatin("Courier");

        doc = TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        // Check that supplemental font collection is preserved.
        theme = doc.getTheme();
        msAssert.areEqual(30, theme.getMajorFonts().getSupplementalFonts().size());
        msAssert.areEqual(30, doc.getTheme().getMinorFonts().getSupplementalFonts().size());
    }


    /// <summary>
    /// Tests how theme colors are accessed public.
    /// </summary>
    @Test
    public void testThemeColors() throws Exception
    {
        final String TEST_NAME = "Model\\Theme\\TestThemeColors.docx";
        Document doc = TestUtil.open(TEST_NAME);

        setAllColors(doc.getTheme());
        checkAllColors(doc.getTheme());

        doc = TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        checkAllColors(doc.getTheme());
    }

    /// <summary>
    /// Tests how theme accessed when document has no theme initially.
    /// </summary>
    @Test
    public void testNoTheme() throws Exception
    {
        // FOSS: the Doc reader is removed, so the original themeless TestNoTheme.doc can no longer
        // be loaded. Reproduce the same "document with no internal theme" state directly instead;
        // the tested behavior (theme is lazily created from the built-in resource) is unchanged.
        Document doc = new Document();
        doc.setThemeInternal(null);

        // Although we have no theme internally it is created on first public access.
        msAssert.isNull(doc.getThemeInternal());
        msAssert.isNotNull(doc.getTheme());

        Theme theme = doc.getTheme();
        // Check that theme is created only once.
        msAssert.isTrue(msObject.referenceEquals(theme, doc.getTheme()));

        msAssert.areEqual("Cambria", theme.getMajorFonts().getLatin());
        msAssert.areEqual("Calibri", theme.getMinorFonts().getLatin());

        setAllColors(theme);
        checkAllColors(theme);
    }


    /// <summary>
    /// Tests that extension properties are read/written correctly.
    /// </summary>
    @Test
    public void testExtension() throws Exception
    {
        final String DOCUMENT_NAME = "Model\\Theme\\TestThemeColors.docx";
        Document doc = TestUtil.open(DOCUMENT_NAME);

        checkThemeFamilyExtension(((IDmlExtensionListSource)doc.getTheme()).getExtensions(), false);

        // We need to specify ISO Strict compliance here to regenerate theme stream on writing.
        doc = TestUtil.saveOpen(doc, DOCUMENT_NAME,
            OoxmlSaveOptions.docxWithCompliance(OoxmlComplianceCore.ISO_STRICT), false);

        checkThemeFamilyExtension(((IDmlExtensionListSource)doc.getTheme()).getExtensions(), true);
    }

    /// <summary>
    /// Checks properties of the theme family extension in the specified extension list.
    /// </summary>
    private static void checkThemeFamilyExtension(StringToObjDictionary<DmlExtension> extensionList, boolean expectedIsoStrictUrl)
    {
        msAssert.isNotNull(extensionList);

        msAssert.areEqual("{05A4C25C-085E-4340-85A3-A5531E510DB2}", extensionList.get("{05A4C25C-085E-4340-85A3-A5531E510DB2}").getUri());

        final String EXTENSION_XML =
            "<a:ext uri=\"{{05A4C25C-085E-4340-85A3-A5531E510DB2}}\" xmlns:a=\"{0}\">" +
                "<thm15:themeFamily id=\"{{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}}\" name=\"Office Theme\" " +
                    "vid=\"{{4A3C46E8-61CC-4603-A589-7422A47A8E4A}}\" " +
                    "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" />" +
            "</a:ext>";
        msAssert.areEqual(msString.format(EXTENSION_XML,
                    DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, expectedIsoStrictUrl)), extensionList.get("{05A4C25C-085E-4340-85A3-A5531E510DB2}").getXmlDoc());
    }

    /// <summary>
    /// Tests that several extensions are read/written correctly.
    /// </summary>
    @Test
    public void testReadWriteTwoExtensions() throws Exception
    {
        final String DOCUMENT_NAME = "Model\\Theme\\TestTwoExtensions_ManuallyGenerated.docx";
        Document doc = TestUtil.open(DOCUMENT_NAME);

        checkExtExtension(((IDmlExtensionListSource)doc.getTheme()).getExtensions());

        // We need to specify ISO Strict compliance here to regenerate theme stream on writing.
        doc = TestUtil.saveOpen(doc, DOCUMENT_NAME,
            OoxmlSaveOptions.docxWithCompliance(OoxmlComplianceCore.ISO_STRICT), false);

        checkExtExtension(((IDmlExtensionListSource)doc.getTheme()).getExtensions());
    }

    // FOSS: VerifyRtfThemeExport removed — the RTF reader/writer are removed, so an RTF
    // theme import/export round-trip cannot be performed.

    /// <summary>
    /// Checks additional extension in theme extension list. The extension is not real; it is added for test only.
    /// </summary>
    private static void checkExtExtension(StringToObjDictionary<DmlExtension> extensionList)
    {
        msAssert.isNotNull(extensionList);
        msAssert.areEqual(2, extensionList.getCount());
        // The extension is not real; it is added for test only.
        msAssert.areEqual("{05A4C25C-085E-4340-85A3-A5531E510DB3}", extensionList.get("{05A4C25C-085E-4340-85A3-A5531E510DB3}").getUri());
    }






    // FOSS: TestBadThemeFontDescription and TestRtfTheme removed — both load .rtf inputs to
    // verify RTF-specific theme import (RTF carries no theme data, so MSW resolves fonts/colors
    // from the default theme). The RTF reader is removed, so these scenarios cannot exist.


    /// <summary>
    /// Additional test for WORDSNET-15143
    /// </summary>
    @Test
    public void testJira15146() throws Exception
    {
        final String TEST_NAME = "Model\\Theme\\TestJira15146";

        Document doc = TestUtil.open(TEST_NAME, LoadFormat.DOCX);
        Table table = doc.getFirstSection().getBody().getTables().get(0);

        table.getFirstRow().getFirstCell().getCellFormat().getBorders().getBottom().setColor(Color.RED);

        doc = TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.areEqual(Color.RED.getRGB(), table.getFirstRow().getFirstCell().getCellFormat().getBorders().getBottom().getColor().getRGB());
    }


    /// <summary>
    /// WORDSNET-15818 Traditional Chinese fonts should be used for certain Chinese locales instead of simplified fonts.
    /// </summary>
    @Test (dataProvider = "testJira15818DataProvider")
    public void testJira15818(/*Language*/int chineseLanguage, String expectedFontName) throws Exception
    {
        Document doc = new Document();
        doc.getTheme().getThemeFontLanguages().EastAsia = chineseLanguage;
        msAssert.areEqual(expectedFontName, ((IThemeProvider)doc.getTheme()).getFontName(ThemeFontCore.MAJOR_EAST_ASIA));
        msAssert.areEqual(expectedFontName, ((IThemeProvider)doc.getTheme()).getFontName(ThemeFontCore.MINOR_EAST_ASIA));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira15818DataProvider")
	public static Object[][] testJira15818DataProvider() throws Exception
	{
		return new Object[][]
		{
			{Language.CHINESE_CHINA,  "宋体"},
			{Language.CHINESE_SINGAPORE,  "宋体"},
			{Language.CHINESE_HONG_KONG,  "新細明體"},
			{Language.CHINESE_MACAO,  "新細明體"},
			{Language.CHINESE_TAIWAN,  "新細明體"},
		};
	}


    // FOSS: TestJira6840 (MHTML) and TestJira16563 (HTML) removed — both verify that the
    // MHTML/HTML writer emits the *actual* system-color value rather than the theme's cached
    // LastColor. That behavior lives in the removed MHTML/HTML export path; a DOCX round-trip
    // preserves the sysClr/lastClr as-is (LastColor stays the source value, not the resolved
    // one), so the invariant cannot be reproduced without those writers.


    /// <summary>
    /// WORDSNET-17840 Border color not modified when changing Accent color.
    /// Theme should be applied to the paragraph attributes of the style.
    /// </summary>
    @Test
    public void testJira17840() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\TestJira17840.docx");
        doc.getTheme().getColors().setAccent1(Color.YELLOW);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\TestJira17840.docx", null, false);

        Style style = doc.getStyles().get("Heading 1");
        DrColor color = style.getParaPr().getBorderBottom().getColorInternal();
        msAssert.areEqual(DrColor.YELLOW, color);
    }

    /// <summary>
    /// WORDSNET-21183 Set custom font style as “linked” to the Theme’s font does not work.
    /// Implemented new public properties that allow to work with themed fonts.
    /// </summary>
    /// <remarks>This test checks customer's scenario.</remarks>
    @Test
    public void test21183() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test21183.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();
        builder.writeln();

        // Create some style with theme font properties.
        Style style = doc.getStyles().add(StyleType.PARAGRAPH, "From Code");
        style.getFont().setThemeFont(ThemeFont.MAJOR);
        style.getFont().setThemeColor(ThemeColor.ACCENT_5);
        builder.getParagraphFormat().setStyleName("From Code");
        builder.writeln("From Code: This is in \"From Code\" style created");

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test21183.docx");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // Check Theme's font properties of the run with built-in style created using Word.
        Run run = paras.get(0).getFirstRun();
        msAssert.areEqual("This is in “Heading 1” style", run.getText());
        msAssert.areEqual(new Color((0x2F), (0x54), (0x96)), run.getFont().getColor());
        msAssert.areEqual(ThemeColor.ACCENT_1, run.getFont().getThemeColor());
        msAssert.areEqual(-0.25, 0.01, run.getFont().getTintAndShade());
        msAssert.areEqual(ThemeFont.MAJOR, run.getFont().getThemeFont());
        checkThemeFontNames(run.getFont(), ThemeFont.MAJOR);

        // Check Theme's font properties of the run with custom style created using Word.
        run = paras.get(1).getFirstRun();
        msAssert.areEqual("This is in “Custom Style” style created in MS Word", run.getText());
        msAssert.areEqual(doc.getTheme().getColors().getAccent1(), run.getFont().getColor());
        msAssert.areEqual(ThemeColor.ACCENT_1, run.getFont().getThemeColor());
        checkThemeFontNames(run.getFont(), ThemeFont.MINOR);

        // Check Theme's font properties of the run with style created from the scratch in code.
        run = paras.get(2).getFirstRun();
        msAssert.areEqual("From Code: This is in \"From Code\" style created", run.getText());
        msAssert.areEqual(doc.getTheme().getColors().getAccent5(), run.getFont().getColor());
        msAssert.areEqual(ThemeColor.ACCENT_5, run.getFont().getThemeColor());
        checkThemeFontNames(run.getFont(), ThemeFont.MAJOR);
    }

    /// <summary>
    /// Relates to  WORDSNET-21183
    /// Tests changing non-theme font name to the themed one and vice versa by applying theme font 'None'.
    /// </summary>
    @Test
    public void testThemeFontNameA() throws Exception
    {
        Document doc = createNewDocumentWithThemes();
        Font font = doc.getStyles().get("Normal").getFont();

        // Set non-theme font name.
        font.setThemeFont(ThemeFont.NONE);
        doc = TestUtil.saveOpen(doc, "Model\\Theme\\TestThemeFontNameA.docx", new OoxmlSaveOptions(), false);

        font = doc.getStyles().get("Normal").getFont();
        checkThemeFontNames(font, ThemeFont.NONE);
        checkResolvedFontNames(font, "Algerian", "Aharoni", "Algerian", "Andalus");
    }

    /// <summary>
    /// Relates to  WORDSNET-21183
    /// Checks changing non-theme font name to themed one and vice versa by applying some simple font name.
    /// </summary>
    @Test
    public void testThemeFontNameB() throws Exception
    {
        Document doc = createNewDocumentWithThemes();
        Font font = doc.getStyles().get("Normal").getFont();

        // Set non-theme font name.
        font.setName("Arial");
        doc = TestUtil.saveOpen(doc, "Model\\Theme\\TestThemeFontNameB.docx", new OoxmlSaveOptions(), false);

        font = doc.getStyles().get("Normal").getFont();
        checkThemeFontNames(font, ThemeFont.NONE);
        checkResolvedFontNames(font, "Arial", "Arial", "Arial", "Arial");
    }

    /// <summary>
    /// Relates to  WORDSNET-21183
    /// Tests changing non-theme color to the theme color and vice versa by applying theme font color 'None'.
    /// </summary>
    @Test
    public void testThemeFontColorA() throws Exception
    {
        Document doc = new Document();

        // Check a simple RGB color.
        Font font = doc.getStyles().get("Normal").getFont();
        msAssert.areEqual(ThemeColor.NONE, font.getThemeColor());
        msAssert.areEqual(msColor.Empty, font.getColor());

        // Apply theme color.
        font.setThemeColor(ThemeColor.ACCENT_2);
        doc = TestUtil.saveOpen(doc, "Model\\Theme\\TestThemeFontColorA_Accent2.docx", new OoxmlSaveOptions(), false);
        font = doc.getStyles().get("Normal").getFont();
        msAssert.areEqual(ThemeColor.ACCENT_2, font.getThemeColor());
        msAssert.areEqual(msColor.Empty, font.getColor());

        // Set theme color back to 'None'.
        font.setThemeColor(ThemeColor.NONE);
        doc = TestUtil.saveOpen(doc, "Model\\Theme\\TestThemeFontColorA_None.docx", new OoxmlSaveOptions(), false);
        font = doc.getStyles().get("Normal").getFont();
        msAssert.areEqual(ThemeColor.NONE, font.getThemeColor());
        msAssert.areEqual(msColor.Empty, font.getColor());
    }

    /// <summary>
    /// Relates to  WORDSNET-21183
    /// Tests changing non-theme color to the theme color and vice versa by applying some simple color.
    /// </summary>
    @Test
    public void testThemeFontColorB() throws Exception
    {
        Document doc = new Document();

        // Check a simple RGB color.
        Font font = doc.getStyles().get("Normal").getFont();
        msAssert.areEqual(ThemeColor.NONE, font.getThemeColor());
        msAssert.areEqual(msColor.Empty, font.getColor());

        // Apply theme color.
        font.setThemeColor(ThemeColor.ACCENT_2);
        doc = TestUtil.saveOpen(doc, "Model\\Theme\\TestThemeFontColorB_Accent2.docx", new OoxmlSaveOptions(), false);
        font = doc.getStyles().get("Normal").getFont();
        msAssert.areEqual(ThemeColor.ACCENT_2, font.getThemeColor());
        msAssert.areEqual(msColor.Empty, font.getColor());

        // Set simple RGB color.
        font.setColor(Color.BLUE);
        doc = TestUtil.saveOpen(doc, "Model\\Theme\\TestThemeFontColorB_RGB_Blue.docx", new OoxmlSaveOptions(), false);
        font = doc.getStyles().get("Normal").getFont();
        msAssert.areEqual(ThemeColor.NONE, font.getThemeColor());
        msAssert.areEqual(Color.BLUE.getRGB(), font.getColor().getRGB());
    }

    /// <summary>
    /// Relates to  WORDSNET-21183
    /// Tests various combinations of TintAndShade.
    /// </summary>
    @Test (dataProvider = "testTintAndShadeDataProvider")
    public void testTintAndShade(double tintAndShade) throws Exception
    {
        final /*ThemeColor*/int DESIRED_THEME_COLOR = ThemeColor.ACCENT_6;

        Document doc = new Document();
        Font font = doc.getStyles().get("Normal").getFont();

        font.setThemeColor(DESIRED_THEME_COLOR);
        font.setTintAndShade(tintAndShade);

        String outFileName = msString.format("Model\\Theme\\TestTintAndShade_{0}.docx", tintAndShade);
        doc = TestUtil.saveOpen(doc, outFileName, new OoxmlSaveOptions(), false);

        font = doc.getStyles().get("Normal").getFont();
        msAssert.areEqual(tintAndShade, 0.01, font.getTintAndShade());
        msAssert.areEqual(DESIRED_THEME_COLOR, font.getThemeColor());
        msAssert.areEqual(msColor.Empty, font.getColor());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTintAndShadeDataProvider")
	public static Object[][] testTintAndShadeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{0.4},
			{-0.25},
			{1.0},
			{-1.0},
			{0.0},
		};
	}

    /// <summary>
    /// Relates to  WORDSNET-21183
    /// Tests that exception is thrown when applying value to TintAndShade out of [-1, 1] range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testTintAndShadeOutOfRange() throws Exception
    {
        Document doc = new Document();
        Font font = doc.getStyles().get("Normal").getFont();
        font.setThemeColor(ThemeColor.ACCENT_2);
        // Apply TintAndShade value out of the allowed range [-1, 1].
        font.setTintAndShade(2.0);
    }

    /// <summary>
    /// Relates to  WORDSNET-21183
    /// Tests that exception is thrown when applying value to TintAndShade for non-theme font color.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testTintAndShadeNonThemeColor() throws Exception
    {
        Document doc = new Document();
        Font font = doc.getStyles().get("Normal").getFont();
        // Apply TintAndShade to the non-theme font color.
        font.setTintAndShade(0.5);
    }

    /// <summary>
    /// WORDSNET-24441 Add Border.ThemeColor.
    /// Tests the public API of ThemeColor and TintAndShade properties in Border.
    /// Style border case.
    /// </summary>
    @Test
    public void test24441_Style() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test24441.docx");

        BorderCollection heading1Borders = doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1).getParagraphFormat().getBorders();
        checkBorderColors(heading1Borders.getBottom(), ThemeColor.ACCENT_1, msColor.Empty);
        checkBorderColors(heading1Borders.getTop(), ThemeColor.NONE, new Color((0xFF), (0x00), (0x00), (0xFF)));
        heading1Borders.getBottom().setThemeColor(ThemeColor.ACCENT_2);
        heading1Borders.getTop().setThemeColor(ThemeColor.ACCENT_1);
        heading1Borders.getTop().setTintAndShade(0.5);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test24441_Style.docx", new OoxmlSaveOptions(), false);
        heading1Borders = doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_1).getParagraphFormat().getBorders();

        checkBorderColors(heading1Borders.getBottom(), ThemeColor.ACCENT_2, msColor.Empty);
        checkBorderColors(heading1Borders.getTop(), ThemeColor.ACCENT_1, msColor.Empty);
        msAssert.areEqual(0.5, 0.01, heading1Borders.getTop().getTintAndShade());
    }

    /// <summary>
    /// Relates to WORDSNET-24441
    /// Tests the public API of ThemeColor and TintAndShade properties in Border.
    /// Paragraph border case.
    /// </summary>
    @Test
    public void test24441_Paragraph() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test24441.docx");

        BorderCollection paraBorders = doc.getFirstSection().getBody().getParagraphs().get(1).getParagraphFormat().getBorders();
        checkBorderColors(paraBorders.getBottom(), ThemeColor.ACCENT_5, new Color((0x5B), (0x9B), (0xD5), (0xFF)));
        checkBorderColors(paraBorders.getTop(), ThemeColor.ACCENT_5, new Color((0x5B), (0x9B), (0xD5), (0xFF)));
        paraBorders.getBottom().setThemeColor(ThemeColor.ACCENT_1);
        paraBorders.getTop().setColor(Color.RED);
        paraBorders.getBottom().setTintAndShade(-0.5);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test24441_Paragraph.docx", new OoxmlSaveOptions(), false);
        paraBorders = doc.getFirstSection().getBody().getParagraphs().get(1).getParagraphFormat().getBorders();

        checkBorderColors(paraBorders.getBottom(), ThemeColor.ACCENT_1, msColor.Empty);
        checkBorderColors(paraBorders.getTop(), ThemeColor.NONE, new Color((0xFF), (0x00), (0x00), (0xFF)));
        msAssert.areEqual(-0.5, 0.01, paraBorders.getBottom().getTintAndShade());
    }

    /// <summary>
    /// Relates to WORDSNET-24441
    /// Tests the public API of ThemeColor and TintAndShade properties in Border.
    /// Text border case.
    /// </summary>
    @Test
    public void test24441_Text() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test24441.docx");

        Border textBorder = doc.getFirstSection().getBody().getParagraphs().get(3).getFirstRun().getFont().getBorder();
        checkBorderColors(textBorder, ThemeColor.ACCENT_3, msColor.Empty);
        textBorder.setThemeColor(ThemeColor.ACCENT_1);
        textBorder.setTintAndShade(-0.25);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test24441_Text.docx", new OoxmlSaveOptions(), false);
        textBorder = doc.getFirstSection().getBody().getParagraphs().get(3).getFirstRun().getFont().getBorder();

        checkBorderColors(textBorder, ThemeColor.ACCENT_1, msColor.Empty);
        msAssert.areEqual(-0.25, 0.01, textBorder.getTintAndShade());
    }

    /// <summary>
    /// Relates to WORDSNET-24441
    /// Tests the public API of ThemeColor and TintAndShade properties in Border.
    /// Table border case.
    /// </summary>
    @Test
    public void test24441_Table() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test24441.docx");

        BorderCollection tableBorders = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getRowFormat().getBorders();
        checkBorderColors(tableBorders.getBottom(), ThemeColor.ACCENT_6, new Color((0x70), (0xAD), (0x47), (0xFF)));
        checkBorderColors(tableBorders.getTop(), ThemeColor.ACCENT_6, new Color((0x70), (0xAD), (0x47), (0xFF)));
        tableBorders.getBottom().setThemeColor(ThemeColor.ACCENT_3);
        tableBorders.getTop().setColor(Color.RED);
        tableBorders.getBottom().setTintAndShade(0.25);
        // The setter resets the border color value.
        checkBorderColors(tableBorders.getBottom(), ThemeColor.ACCENT_3, msColor.Empty);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test24441_Table.docx", new OoxmlSaveOptions(), false);
        tableBorders = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getRowFormat().getBorders();
        // The writer restores the border color by the appropriate theme color.
        checkBorderColors(tableBorders.getBottom(), ThemeColor.ACCENT_3, new Color((0xBB), (0xBB), (0xBB), (0xFF)));
        checkBorderColors(tableBorders.getTop(), ThemeColor.NONE, new Color((0xFF), (0x00), (0x00), (0xFF)));
        msAssert.areEqual(0.25, 0.01, tableBorders.getBottom().getTintAndShade());
    }

    /// <summary>
    /// Relates to  WORDSNET-24441
    /// Tests the public API of ThemeColor and TintAndShade properties in Border.
    /// Cell border case.
    /// </summary>
    @Test
    public void test24441_Cell() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test24441.docx");

        BorderCollection cellBorders = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getCellFormat().getBorders();
        checkBorderColors(cellBorders.getBottom(), ThemeColor.ACCENT_4, msColor.Empty);
        checkBorderColors(cellBorders.getRight(), ThemeColor.ACCENT_4, msColor.Empty);
        cellBorders.getBottom().setThemeColor(ThemeColor.ACCENT_1);
        cellBorders.getTop().setColor(Color.RED);
        cellBorders.getBottom().setTintAndShade(0.125);
        // Setter resets the border color value.
        checkBorderColors(cellBorders.getBottom(), ThemeColor.ACCENT_1, msColor.Empty);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test24441_Cell.docx", new OoxmlSaveOptions(), false);
        cellBorders = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getCellFormat().getBorders();
        // Writer restores the border color by the appropriate theme color.
        checkBorderColors(cellBorders.getBottom(), ThemeColor.ACCENT_1, new Color((0x5B), (0x83), (0xCB), (0xFF)));
        checkBorderColors(cellBorders.getTop(), ThemeColor.NONE, new Color((0xFF), (0x00), (0x00), (0xFF)));
        msAssert.areEqual(0.125, 0.01, cellBorders.getBottom().getTintAndShade());
    }

    /// <summary>
    /// Relates to  WORDSNET-24441
    /// Tests that exception is thrown when applying value to TintAndShade out of [-1, 1] range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testBorderTintAndShadeOutOfRange() throws Exception
    {
        Document doc = new Document();
        Border border = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getBorders().getVertical();
        border.setThemeColor(ThemeColor.ACCENT_2);
        // Apply TintAndShade value out of the allowed range [-1, 1].
        border.setTintAndShade(2.0);
    }

    /// <summary>
    /// Relates to  WORDSNET-24441
    /// Tests that exception is thrown when applying value to TintAndShade for non-theme font color.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testBorderTintAndShadeNonThemeColor() throws Exception
    {
        Document doc = new Document();
        Border border = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getBorders().getVertical();
        // Apply TintAndShade to the non-theme font color.
        border.setTintAndShade(0.5);
    }

    /// <summary>
    /// WORDSNET-24456 Add Shading.ThemeColor.
    /// Tests the public API of ForegroundThemeColor, BackgroundThemeColor, ForegroundTintAndShade
    /// and BackgroundTintAndShade properties in Shading.
    /// Paragraph shading case.
    /// </summary>
    @Test
    public void test24456_Paragraph() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test24456_Para.docx");

        Shading shadingPara0 = doc.getFirstSection().getBody().getParagraphs().get(0).getParagraphFormat().getShading();
        Shading shadingPara1 = doc.getFirstSection().getBody().getParagraphs().get(1).getParagraphFormat().getShading();
        checkShadingColors(shadingPara0, ThemeColor.NONE, ThemeColor.NONE,
            0.0, 0.0, new Color((0x00), (0x00), (0xFF), (0xFF)),
            new Color((0x00), (0xFF), (0xFF), (0xFF)));
        checkShadingColors(shadingPara1, ThemeColor.NONE, ThemeColor.NONE,
            0.0, 0.0, new Color((0x00), (0x80), (0x00), (0xFF)),
            new Color((0xFF), (0x00), (0x00), (0xFF)));

        shadingPara0.setForegroundPatternThemeColor(ThemeColor.ACCENT_1);
        shadingPara0.setBackgroundPatternThemeColor(ThemeColor.ACCENT_2);
        shadingPara1.setForegroundPatternThemeColor(ThemeColor.DARK_1);
        shadingPara1.setBackgroundPatternThemeColor(ThemeColor.DARK_2);
        shadingPara1.setForegroundTintAndShade(0.5);
        shadingPara1.setBackgroundTintAndShade(-0.5);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test24456_Para.docx", new OoxmlSaveOptions(), false);
        shadingPara0 = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getShading();
        shadingPara1 = doc.getFirstSection().getBody().getParagraphs().get(1).getParagraphFormat().getShading();

        checkShadingColors(shadingPara0, ThemeColor.ACCENT_1, ThemeColor.ACCENT_2,
            0.0, 0.0, msColor.Empty, msColor.Empty);
        checkShadingColors(shadingPara1, ThemeColor.DARK_1, ThemeColor.DARK_2,
            0.5, -0.5, msColor.Empty, msColor.Empty);
    }

    /// <summary>
    /// Relates to WORDSNET-24456
    /// Tests the public API of ForegroundThemeColor, BackgroundThemeColor, ForegroundTintAndShade
    /// and BackgroundTintAndShade properties in Shading.
    /// Text shading case.
    /// </summary>
    @Test
    public void test24456_Text() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test24456_Text.docx");

        Shading shadingFont = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getFont().getShading();
        checkShadingColors(shadingFont, ThemeColor.NONE, ThemeColor.NONE, 0.0,
            0.0, new Color((0x00), (0x00), (0xFF), (0xFF)),
            new Color((0xFF), (0x00), (0x00), (0xFF)));

        shadingFont.setForegroundPatternThemeColor(ThemeColor.ACCENT_1);
        shadingFont.setBackgroundPatternThemeColor(ThemeColor.ACCENT_2);
        shadingFont.setForegroundTintAndShade(0.5);
        shadingFont.setBackgroundTintAndShade(-0.5);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test24456_Tara.docx", new OoxmlSaveOptions(), false);
        shadingFont = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getFont().getShading();

        checkShadingColors(shadingFont, ThemeColor.ACCENT_1, ThemeColor.ACCENT_2,
            0.5, -0.5, msColor.Empty, msColor.Empty);
    }

    /// <summary>
    /// Relates to WORDSNET-24456
    /// Tests the public API of ForegroundThemeColor, BackgroundThemeColor, ForegroundTintAndShade
    /// and BackgroundTintAndShade properties in Shading.
    /// Table style shading case.
    /// </summary>
    @Test
    public void test24456_TableStyle() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test24456_TableStyle.docx");

        Shading shadingTable0 = ((TableStyle)doc.getFirstSection().getBody().getTables().get(0).getStyle()).getShading();
        Shading shadingTable1 = ((TableStyle)doc.getFirstSection().getBody().getTables().get(1).getStyle()).getShading();
        checkShadingColors(shadingTable0, ThemeColor.NONE, ThemeColor.NONE,
            0.0, 0.0, new Color((0x00), (0x00), (0xFF), (0xFF)),
            new Color((0x00), (0xFF), (0xFF), (0xFF)));
        checkShadingColors(shadingTable1, ThemeColor.NONE, ThemeColor.NONE,
            0.0, 0.0, new Color((0x00), (0x80), (0x00), (0xFF)),
            new Color((0xFF), (0x00), (0x00), (0xFF)));

        shadingTable0.setForegroundPatternThemeColor(ThemeColor.ACCENT_1);
        shadingTable0.setBackgroundPatternThemeColor(ThemeColor.ACCENT_2);
        shadingTable1.setForegroundPatternThemeColor(ThemeColor.DARK_1);
        shadingTable1.setBackgroundPatternThemeColor(ThemeColor.DARK_2);
        shadingTable1.setForegroundTintAndShade(0.5);
        shadingTable1.setBackgroundTintAndShade(-0.5);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test24456_TableStyle.docx", new OoxmlSaveOptions(), false);
        shadingTable0 = ((TableStyle)doc.getFirstSection().getBody().getTables().get(0).getStyle()).getShading();
        shadingTable1 = ((TableStyle)doc.getFirstSection().getBody().getTables().get(1).getStyle()).getShading();

        checkShadingColors(shadingTable0, ThemeColor.ACCENT_1, ThemeColor.ACCENT_2,
            0.0, 0.0, msColor.Empty, msColor.Empty);
        checkShadingColors(shadingTable1, ThemeColor.DARK_1, ThemeColor.DARK_2,
            0.5, -0.5, msColor.Empty, msColor.Empty);
    }

    /// <summary>
    /// Relates to WORDSNET-24456
    /// Tests the public API of ForegroundThemeColor, BackgroundThemeColor, ForegroundTintAndShade
    /// and BackgroundTintAndShade properties in Shading.
    /// Table cell shading case.
    /// </summary>
    @Test
    public void test24456_Cell() throws Exception
    {
        Document doc = TestUtil.open("Model\\Theme\\Test24456_Cell.docx");

        Shading shadingCell = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getCellFormat().getShading();
        checkShadingColors(shadingCell, ThemeColor.NONE, ThemeColor.NONE,
            0.0, 0.0, new Color((0xFF), (0x00), (0x00), (0xFF)),
            new Color((0x00), (0xFF), (0xFF), (0xFF)));

        shadingCell.setForegroundPatternThemeColor(ThemeColor.DARK_1);
        shadingCell.setBackgroundPatternThemeColor(ThemeColor.DARK_2);
        shadingCell.setForegroundTintAndShade(0.5);
        shadingCell.setBackgroundTintAndShade(-0.5);

        doc = TestUtil.saveOpen(doc, "Model\\Theme\\Test24456_Cell.docx", new OoxmlSaveOptions(), false);
        shadingCell = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getCellFormat().getShading();
        // Writer restores the border color by the appropriate theme color.
        checkShadingColors(shadingCell, ThemeColor.DARK_1, ThemeColor.DARK_2, 0.5,
            -0.5, new Color((0x80), (0x80), (0x80), (0xFF)),
            new Color((0x21), (0x29), (0x34), (0xFF)));
    }

    /// <summary>
    /// Relates to WORDSNET-24456
    /// Tests that exception is thrown when applying value to ForegroundTintAndShade or BackgroundTintAndShade out of [-1, 1] range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "testShadingTintAndShadeOutOfRangeDataProvider")
    public void testShadingTintAndShadeOutOfRange(boolean testForeground) throws Exception
    {
        Document doc = new Document();
        Shading shading = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getShading();
        shading.setBackgroundPatternThemeColor(ThemeColor.ACCENT_1);
        shading.setForegroundPatternThemeColor(ThemeColor.ACCENT_1);
        // Apply ForegroundTintAndShade or BackgroundTintAndShade value out of the allowed range [-1, 1].
        if (testForeground)
            shading.setForegroundTintAndShade(2.0);
        else
            shading.setBackgroundTintAndShade(2.0);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShadingTintAndShadeOutOfRangeDataProvider")
	public static Object[][] testShadingTintAndShadeOutOfRangeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-24456
    /// Tests that exception is thrown when applying value to ForegroundTintAndShade or BackgroundTintAndShade for non-theme color.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, dataProvider = "testShadingTintAndShadeNonThemeColorDataProvider")
    public void testShadingTintAndShadeNonThemeColor(boolean testForeground) throws Exception
    {
        Document doc = new Document();
        Shading shading = doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getShading();
        // Apply TintAndShade to the non-theme color.
        if (testForeground)
            shading.setForegroundTintAndShade(0.5);
        else
            shading.setBackgroundTintAndShade(0.5);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShadingTintAndShadeNonThemeColorDataProvider")
	public static Object[][] testShadingTintAndShadeNonThemeColorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}


    /// <summary>
    /// Checks border colors.
    /// </summary>
    private static void checkBorderColors(Border border, /*ThemeColor*/int expectedThemeColor, Color expectedColor)
    {
        msAssert.areEqual(expectedThemeColor, border.getThemeColor());
        msAssert.areEqual(expectedColor, border.getColor());
    }

    /// <summary>
    /// Checks shading colors.
    /// </summary>
    private static void checkShadingColors(Shading shading, /*ThemeColor*/int expectedForegroundThemeColor, /*ThemeColor*/int expectedBackgroundThemeColor,
        double expectedForegroundTintAndShade, double expectedBackgroundTintAndShade,
        Color expectedForegroundPatternColor, Color expectedBackgroundPatternColor)
    {
        msAssert.areEqual(expectedForegroundThemeColor, shading.getForegroundPatternThemeColor());
        msAssert.areEqual(expectedBackgroundThemeColor, shading.getBackgroundPatternThemeColor());
        msAssert.areEqual(expectedForegroundTintAndShade, 0.01, shading.getForegroundTintAndShade());
        msAssert.areEqual(expectedBackgroundTintAndShade, 0.01, shading.getBackgroundTintAndShade());
        msAssert.areEqual(expectedForegroundPatternColor, shading.getForegroundPatternColor());
        msAssert.areEqual(expectedBackgroundPatternColor, shading.getBackgroundPatternColor());
    }

    /// <summary>
    /// Sets all theme color to certain values.
    /// </summary>
    private static void setAllColors(Theme theme)
    {
        ThemeColors colors = theme.getColors();

        colors.setAccent1(Color.RED);
        colors.setAccent2(msColor.getGreen());
        colors.setAccent3(Color.BLUE);
        colors.setAccent4(Color.YELLOW);
        colors.setAccent5(msColor.getGold());
        colors.setAccent6(msColor.getDeepPink());
        colors.setDark1(msColor.getDarkOrchid());
        colors.setDark2(msColor.getDarkOrange());
        colors.setLight1(msColor.getLightCoral());
        colors.setLight2(msColor.getLightSkyBlue());
        colors.setFollowedHyperlink(msColor.getDarkRed());
        colors.setHyperlink(msColor.getDarkMagenta());
    }

    /// <summary>
    /// Checks that all theme colors equal to certain values.
    /// </summary>
    /// <param name="theme"></param>
    private static void checkAllColors(Theme theme)
    {
        ThemeColors colors = theme.getColors();

        msAssert.areEqual(Color.RED.getRGB(), colors.getAccent1().getRGB());
        msAssert.areEqual(msColor.getGreen().getRGB(), colors.getAccent2().getRGB());
        msAssert.areEqual(Color.BLUE.getRGB(), colors.getAccent3().getRGB());
        msAssert.areEqual(Color.YELLOW.getRGB(), colors.getAccent4().getRGB());
        msAssert.areEqual(msColor.getGold().getRGB(), colors.getAccent5().getRGB());
        msAssert.areEqual(msColor.getDeepPink().getRGB(), colors.getAccent6().getRGB());
        msAssert.areEqual(msColor.getDarkOrchid().getRGB(), colors.getDark1().getRGB());
        msAssert.areEqual(msColor.getDarkOrange().getRGB(), colors.getDark2().getRGB());
        msAssert.areEqual(msColor.getLightCoral().getRGB(), colors.getLight1().getRGB());
        msAssert.areEqual(msColor.getLightSkyBlue().getRGB(), colors.getLight2().getRGB());
        msAssert.areEqual(msColor.getDarkRed().getRGB(), colors.getFollowedHyperlink().getRGB());
        msAssert.areEqual(msColor.getDarkMagenta().getRGB(), colors.getHyperlink().getRGB());
    }

    /// <summary>
    /// Creates a new document and applies theme font names to the Normal style.
    /// </summary>
    private static Document createNewDocumentWithThemes() throws Exception
    {
        Document doc = new Document();
        doc.getTheme().getMinorFonts().setLatin("Algerian");
        doc.getTheme().getMinorFonts().setEastAsian("Aharoni");
        doc.getTheme().getMinorFonts().setComplexScript("Andalus");

        // Normal style is not themed originally. Check it.
        Font font = doc.getStyles().get("Normal").getFont();
        checkThemeFontNames(font, ThemeFont.NONE, ThemeFont.NONE, ThemeFont.NONE, ThemeFont.NONE);
        checkResolvedFontNames(font, "Times New Roman", "Times New Roman", "Times New Roman", "Times New Roman");

        // Apply theme font names to the Normal style.
        setThemeFontNames(font, ThemeFont.MINOR, ThemeFont.MINOR, ThemeFont.MINOR, ThemeFont.MINOR);

        // Round-trip the document.
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            doc.save(stream, SaveFormat.DOCX);
            doc = new Document(stream);
        }
        finally { if (stream != null) stream.close(); }

        font = doc.getStyles().get("Normal").getFont();

        checkThemeFontNames(font, ThemeFont.MINOR);
        checkResolvedFontNames(font, "Algerian", "Aharoni", "Algerian", "Andalus");

        return doc;
    }

    /// <summary>
    /// Checks theme's font names.
    /// </summary>
    private static void checkThemeFontNames(Font font, /*ThemeFont*/int expectedFont)
    {
        msAssert.areEqual(expectedFont, font.getThemeFontAscii());
        msAssert.areEqual(expectedFont, font.getThemeFontFarEast());
        msAssert.areEqual(expectedFont, font.getThemeFontOther());
        msAssert.areEqual(expectedFont, font.getThemeFontBi());
    }

    /// <summary>
    /// Checks theme's font names.
    /// </summary>
    private static void checkThemeFontNames(
        Font font,
        /*ThemeFont*/int expectedAscii, /*ThemeFont*/int expectedFarEast, /*ThemeFont*/int expectedOther, /*ThemeFont*/int expectedBi)
    {
        msAssert.areEqual(expectedAscii, font.getThemeFontAscii());
        msAssert.areEqual(expectedFarEast, font.getThemeFontFarEast());
        msAssert.areEqual(expectedOther, font.getThemeFontOther());
        msAssert.areEqual(expectedBi, font.getThemeFontBi());
    }

    /// <summary>
    /// Checks theme's font names.
    /// </summary>
    private static void setThemeFontNames(
        Font font,
        /*ThemeFont*/int themeAscii, /*ThemeFont*/int themeFarEast, /*ThemeFont*/int themeOther, /*ThemeFont*/int themeBi)
    {
        font.setThemeFontAscii(themeAscii);
        font.setThemeFontFarEast(themeFarEast);
        font.setThemeFontOther(themeOther);
        font.setThemeFontBi(themeBi);
    }

    /// <summary>
    /// Checks resolved font names.
    /// </summary>
    private static void checkResolvedFontNames(
        Font font,
        String expectedAscii, String expectedFarEast, String expectedOther, String expectedBi)
    {
        msAssert.areEqual(expectedAscii, font.getNameAscii());
        msAssert.areEqual(expectedFarEast, font.getNameFarEast());
        msAssert.areEqual(expectedOther, font.getNameOther());
        msAssert.areEqual(expectedBi, font.getNameBi());
    }
}

