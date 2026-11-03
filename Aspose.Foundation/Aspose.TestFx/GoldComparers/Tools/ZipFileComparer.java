// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/05/2024 by Anton Savko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public class ZipFileComparer extends PackageFileComparer
{
    protected /*override*/ PackageComparisonInfo createPackageComparisonInfo(
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold)
    {
        return new ZipComparisonInfo(fileNameSrc, fileNameOut, fileNameGold, null);
    }

    protected /*override*/ /*ComparerFormResult*/int executePackageTestUI(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String fileNameMS) throws Exception
    {
        return AsposeComparer.executeZipTestUI(title, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS);
    }

    protected /*override*/ void saveGoldTestInfoIfNeeded(
        String title,
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS) throws Exception
    {
        FailedGoldTestCollector.saveZipGoldTestInfoIfNeeded(title, fileNameSrc, fileNameOut, fileNameGold, fileNameMS);
    }
}

