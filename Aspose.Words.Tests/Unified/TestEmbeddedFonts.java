// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.msString;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// <para>Tests for fonts embedded in documents.</para>
/// </summary>
/// <remarks>
/// FOSS: DOCX font-embedding OUTPUT was removed — DocxFontTableWriter strips all embedded fonts
/// on save (FontInfos.CloneWithoutEmbeddedFonts), and DOC/RTF/HTML load+save are gone. The many
/// tests that asserted embedded fonts are preserved / created / subsetted (incl. all DOC/RTF/HTML
/// and font-merge cases) were removed accordingly. What remains verifies the surviving behavior:
/// fonts are NOT embedded (embedding disabled or removed) and long font-name handling.
/// </remarks>
@Test
public class TestEmbeddedFonts extends UnifiedTestsBase
{

    /// <summary>
    /// Relates to WORDSNET-21120
    /// Checks when <see cref="SaveOptions.AllowEmbeddingPostScriptFonts"/> option is on, but font embedding is disabled.
    /// </summary>
    // FOSS: reduced to Docx — Doc/Rtf save were removed.
    @Test (dataProvider = "test21120BDataProvider")
    public void test21120B(/*SaveFormat*/int saveFormat) throws Exception
    {
        Document doc = createDocumentTest21120();

        // Disable fonts embedding.
        doc.getFontInfos().setEmbedTrueTypeFonts(false);
        doc.getFontInfos().setEmbedSystemFonts(false);

        // Enable embedding PostScript fonts.
        SaveOptions saveOptions = SaveOptions.createSaveOptions(saveFormat);
        saveOptions.setAllowEmbeddingPostScriptFonts(true);

        // Roundtrip the document with embedding problematic fonts.
        String outFileName = msString.format("Model\\FontEmbedding\\Test21120\\Test21120_NoEmbedding{0}",
            FileFormatUtil.saveFormatToExtension(saveFormat));
        doc = TestUtil.saveOpen(doc, outFileName, saveOptions, false);

        // Check PostScript fonts are not embedded, as embedding of fonts is completely disabled in FontInfos.
        FontInfo fontInfo = doc.getFontInfos().get("AllensHand");
        msAssert.isTrue((fontInfo == null) || !fontInfo.hasEmbeddedFonts());

        fontInfo = doc.getFontInfos().get("Allura");
        msAssert.isTrue((fontInfo == null) || !fontInfo.hasEmbeddedFonts());

        // Check TrueType fonts are not embedded.
        fontInfo = doc.getFontInfos().get("Long Cang");
        msAssert.isTrue((fontInfo == null) || !fontInfo.hasEmbeddedFonts());

        fontInfo = doc.getFontInfos().get("Pacifico");
        msAssert.isTrue((fontInfo == null) || !fontInfo.hasEmbeddedFonts());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21120BDataProvider")
	public static Object[][] test21120BDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}


    /// <summary>
    /// Tests embedded fonts removal.
    /// </summary>
    // FOSS: reduced to Docx2Docx — Doc/Rtf load+save were removed.
    @Test (dataProvider = "unifiedTestRemoveEmbeddedFontsDataProvider")
    public void unifiedTestRemoveEmbeddedFonts(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String SRC_FILE_NAME = "Model\\FontEmbedding\\TestEmbeddedAllStyles";
        Document doc = TestUtil.open(SRC_FILE_NAME, lf);

        doc.getFontInfos().setEmbedTrueTypeFonts(false);

        String outFileName = TestUtil.buildOutFileName(SRC_FILE_NAME, "", sf);
        doc = TestUtil.saveOpen(doc, outFileName, SaveOptions.createSaveOptions(sf), false);

        msAssert.isFalse(doc.getFontInfos().hasEmbeddedFonts());
        msAssert.isFalse(doc.getFontInfos().getEmbedTrueTypeFonts());
        msAssert.isFalse(doc.getDocPr().EmbedTrueTypeFonts);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "unifiedTestRemoveEmbeddedFontsDataProvider")
	public static Object[][] unifiedTestRemoveEmbeddedFontsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{LoadFormat.DOCX,  SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Creates test document for WORDSNET-21120
    /// </summary>
    private static Document createDocumentTest21120() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create content with problematic fonts.
        builder.getFont().setName("AllensHand");
        builder.writeln("AllensHand font.");

        builder.getFont().setName("Long Cang");
        builder.writeln("Long Cang font.");

        builder.getFont().setName("Pacifico");
        builder.writeln("Pacifico font.");

        builder.getFont().setName("Allura");
        builder.write("Allura font.");

        // FOSS: font sources / substitution were removed; the test only asserts that fonts are
        // NOT embedded, which holds regardless of whether the source fonts are loaded.
        return doc;
    }
}

