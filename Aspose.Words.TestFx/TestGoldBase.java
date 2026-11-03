// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/11/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import com.aspose.Debug;


/// <summary>
/// Base class for exports into human-readable formats that we verify against gold files.
/// </summary>
public class TestGoldBase
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
    }

    /// <summary>
    /// This is a standard test for export and import with compare against a text gold file.
    /// First it test export: saves a document in the specified format and verifies against a gold file.
    /// Then it tests import: opens the file that was just created, saves again and verifies against gold.
    ///
    /// This method allows you to test various save settings. You need to open
    /// the document yourself, set the settings and then call this method to verify against gold.
    /// </summary>
    /// <param name="doc">The document to save.</param>
    /// <param name="relSrcFileName">Name of the original file, relative to the TestData\Model folder.
    /// The file name will be used to derive the name for the Out and Gold.</param>
    /// <param name="suffix">Additional suffix is added to file name, both out and gold
    /// to form separate files for any options we need to test (i. e. embedded styles).</param>
    /// <param name="goldLevel">Specifies whether to verify export only or both export and import.</param>
    /// <param name="saveOptions"></param>
    /// <returns>The name of the Out file.</returns>
    protected static String verifyTextGold(
        Document doc,
        String relSrcFileName,
        String suffix,
        /*GoldLevel*/int goldLevel,
        SaveOptions saveOptions) throws Exception
    {
        Debug.assertTrue(saveOptions != null);

        String srcFileName = getSrcFileName(relSrcFileName);
        String outFileName = getOutFileName(relSrcFileName, suffix, saveOptions.getSaveFormat());
        String goldFileName = getGoldFileName(relSrcFileName, suffix, saveOptions.getSaveFormat());

        TestUtil.ensureDirectoryForFileExists(outFileName);
        doc.save(outFileName, saveOptions);

        TestUtil.verifyGoldText(srcFileName, outFileName, goldFileName, null, goldLevel, saveOptions);
        return outFileName;
    }

    protected static Document open(String relSrcFileName) throws Exception
    {
        return open(relSrcFileName, null);
    }

    protected static Document open(String relSrcFileName, LoadOptions lo) throws Exception
    {
        return new Document(getSrcFileName(relSrcFileName), lo);
    }

    protected static String getSrcFileName(String relSrcFileName)
    {
        return TestUtil.getInModelPath(relSrcFileName);
    }

    protected static String getOutFileName(String relSrcFileName, String userSuffix, /*SaveFormat*/int sf)
    {
        return TestUtil.buildOutFileName(TestUtil.getInModelPath(relSrcFileName), userSuffix, sf);
    }

    protected static String getGoldFileName(String relSrcFileName, String userSuffix, /*SaveFormat*/int sf)
    {
        return TestUtil.buildGoldFileName(TestUtil.getInModelPath(relSrcFileName), userSuffix, sf);
    }
}

