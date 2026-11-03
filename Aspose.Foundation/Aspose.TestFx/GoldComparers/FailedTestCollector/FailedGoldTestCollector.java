// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/12/2012 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Utility methods for postprocessing of failed gold tests.
/// </summary>
/// <remarks>
/// We need the gold tests postprocessing feature for CI (Jenkins). Please see WORDSNET-7572
/// It's a part of the mechanism which allows CI users to see CI gold test problems.
/// </remarks>
/*static*/ class FailedGoldTestCollector
{
	/* Simulation of static class by using private constructor */
	private FailedGoldTestCollector()
	{}

    /// <summary>
    /// Saves information about failed gold tests with zip test files to a specified folder.
    /// </summary>
    static void saveZipGoldTestInfoIfNeeded(
        String title,
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS) throws Exception
    {
        FailedGoldTestInfo testInfo = new FailedGoldTestInfo();
        testInfo.setGoldFileType(GoldTestFileType.ZIP);
        testInfo.setTitle(title);
        testInfo.setFileNameSrc(fileNameSrc);
        testInfo.setFileNameOut(fileNameOut);
        testInfo.setFileNameGold(fileNameGold);
        testInfo.setFileNameMS(fileNameMS);

        saveGoldTestInfoIfNeeded(testInfo);
    }

    /// <summary>
    /// Saves information about failed gold tests with MOBI test files to a specified folder.
    /// </summary>
    static void saveMobiGoldTestInfoIfNeeded(
        String title,
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS) throws Exception
    {
        FailedGoldTestInfo testInfo = new FailedGoldTestInfo();
        testInfo.setGoldFileType(GoldTestFileType.MOBI);
        testInfo.setTitle(title);
        testInfo.setFileNameSrc(fileNameSrc);
        testInfo.setFileNameOut(fileNameOut);
        testInfo.setFileNameGold(fileNameGold);
        testInfo.setFileNameMS(fileNameMS);

        saveGoldTestInfoIfNeeded(testInfo);
    }

    /// <summary>
    /// Saves information about failed gold tests with AZW3 test files to a specified folder.
    /// </summary>
    static void saveAzw3GoldTestInfoIfNeeded(
        String title,
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS) throws Exception
    {
        FailedGoldTestInfo testInfo = new FailedGoldTestInfo();
        testInfo.setGoldFileType(GoldTestFileType.AZW_3);
        testInfo.setTitle(title);
        testInfo.setFileNameSrc(fileNameSrc);
        testInfo.setFileNameOut(fileNameOut);
        testInfo.setFileNameGold(fileNameGold);
        testInfo.setFileNameMS(fileNameMS);

        saveGoldTestInfoIfNeeded(testInfo);
    }

    /// <summary>
    /// Saves information about failed gold tests with text test files to a specified folder.
    /// </summary>
    static void saveTxtGoldTestInfoIfNeeded(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS,
        String fileNameSource) throws Exception
    {
        FailedGoldTestInfo testInfo = new FailedGoldTestInfo();
        testInfo.setGoldFileType(GoldTestFileType.TEXT);
        testInfo.setTitle(title);
        testInfo.setFileNameOut(fileNameOut);
        testInfo.setFileNameGold(fileNameGold);
        testInfo.setFileNameMS(fileNameMS);
        testInfo.setFileNameSrc(fileNameSource);

        saveGoldTestInfoIfNeeded(testInfo);
    }

    /// <summary>
    /// Saves information about failed gold tests with image test files to a specified folder.
    /// </summary>
    static void saveImgGoldTestInfoIfNeeded(
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold) throws Exception
    {
        FailedGoldTestInfo testInfo = new FailedGoldTestInfo();
        testInfo.setGoldFileType(GoldTestFileType.IMAGE);
        testInfo.setFileNameSrc(fileNameSrc);
        testInfo.setFileNameOut(fileNameOut);
        testInfo.setFileNameGold(fileNameGold);

        saveGoldTestInfoIfNeeded(testInfo);
    }

    /// <summary>
    /// Saves information about failed gold tests to a specified folder.
    /// </summary>
    private static void saveGoldTestInfoIfNeeded(FailedGoldTestInfo testInfo) throws Exception
    {
        FailedGoldTestStorageEngine engine = new FailedGoldTestStorageEngine();
        engine.saveFailedTest(testInfo);
    }
}

