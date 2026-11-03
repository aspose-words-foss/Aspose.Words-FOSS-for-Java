// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Base class for export to TXT tests.
/// </summary>
public class TestGoldTxtBase extends TestGoldBase
{
    /// <summary>
    /// See base class for more info.
    /// </summary>
    static void verifyExport(String relSrcFileName) throws Exception
    {
        relSrcFileName = com.aspose.TestFxUtil.correctPath(relSrcFileName);
        verifyExport(open(relSrcFileName), relSrcFileName, null);
    }

    /// <summary>
    /// See base class for more info.
    /// </summary>
    static void verifyExport(Document doc, String relSrcFileName, TxtSaveOptions saveOptions) throws Exception
    {
        verifyExport(doc, relSrcFileName, "", saveOptions);
    }

    /// <summary>
    /// Exports to TXT format and verifies against gold file.
    /// </summary>
    /// <remarks>
    /// See base class for more info.
    /// </remarks>
    static void verifyExport(Document doc, String relSrcFileName, String suffix, TxtSaveOptions saveOptions) throws Exception
    {
        relSrcFileName = com.aspose.TestFxUtil.correctPath(relSrcFileName);

        if (saveOptions == null)
            saveOptions = new TxtSaveOptions();

        verifyTextGold(doc, relSrcFileName, suffix, GoldLevel.EXPORT_ONLY, saveOptions);
    }
}

