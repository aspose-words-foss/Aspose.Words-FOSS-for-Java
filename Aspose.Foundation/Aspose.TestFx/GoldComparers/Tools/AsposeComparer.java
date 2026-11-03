// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/*static*/ class AsposeComparer
{
	/* Simulation of static class by using private constructor */
	private AsposeComparer()
	{}

    public static Comparables convert(
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        IToComparableConverter toComparableConverter) throws Exception
    {
        String[] comparableOuts = toComparableConverter.convert(fileNameOut);
        String[] comparableGolds = toComparableConverter.convert(fileNameGold);
        String[] comparableOriginalGolds = null;
        if (fileNameOriginalGold != null)
            comparableOriginalGolds = toComparableConverter.convert(fileNameOriginalGold);

        Comparables comparables = new Comparables(comparableOuts, comparableGolds, comparableOriginalGolds);
        return comparables;
    }

    private static /*ComparerFormResult*/int executeTestUI(
       String testName,
       String fileNameOut,
       String fileNameGold,
       String fileNameOriginalGold,
       String fileNameSrc,
       String fileNameMS,
       String message,
       /*ComparableFileType*/int comparableFileType,
       String comparableOut,
       String comparableGold,
       String comparableOriginalGold) throws Exception
	{
		return executeTestUI(testName, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS, message, comparableFileType, comparableOut, comparableGold, comparableOriginalGold, false);
	}

    private static /*ComparerFormResult*/int executeTestUI(
       String testName,
       String fileNameOut,
       String fileNameGold,
       String fileNameOriginalGold,
       String fileNameSrc,
       String fileNameMS,
       String message,/*ComparableFileType*/int comparableFileType,
       String comparableOut,
       String comparableGold,
       String comparableOriginalGold,
       boolean topmost) throws Exception
	{
		return executeTestUI(testName, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS, message, comparableFileType, comparableOut, comparableGold, comparableOriginalGold, topmost, false);
	}

    private static /*ComparerFormResult*/int executeTestUI(
       String testName,
       String fileNameOut,
       String fileNameGold,
       String fileNameOriginalGold,
       String fileNameSrc,
       String fileNameMS,
       String message,/*ComparableFileType*/int comparableFileType,
       String comparableOut,
       String comparableGold,
       String comparableOriginalGold,
       boolean topmost,
       boolean restorePosition) throws Exception
    {
        if (msString.equals(fileNameOriginalGold, fileNameGold))
            fileNameOriginalGold = null;
        if (fileNameOriginalGold == null)
            comparableOriginalGold = null;

        String cmdLine;
        String difflabels = TestSettings.getLearning() ? msString.join(",", TestSettings.getLabels()) : "";
        switch (comparableFileType)
        {
            case ComparableFileType.IMAGE:
                cmdLine = msString.format(CMD_COMPARE_IMAGE, comparableOut, comparableGold, comparableOriginalGold, fileNameSrc, message, difflabels);
                break;
            case ComparableFileType.TEXT:
                String filesToShow = (msString.isNullOrEmpty(fileNameOriginalGold))
                                         ? msString.format("{0},{1}", fileNameOut, fileNameGold)
                                         : msString.format("{0},{1},{2}", fileNameOut, fileNameGold, fileNameOriginalGold);
                cmdLine = msString.format(CMD_COMPARE_TEXT, comparableOut, comparableGold, comparableOriginalGold, fileNameSrc, testName,
                    filesToShow);
                break;
            case ComparableFileType.ZIP:
                cmdLine = msString.format(CMD_COMPARE_ZIP, fileNameSrc, comparableOut, comparableGold, comparableOriginalGold, fileNameMS, testName, difflabels);
                break;
            case ComparableFileType.MOBI:
                cmdLine = msString.format(CMD_COMPARE_MOBI, fileNameSrc, comparableOut, comparableGold, comparableOriginalGold, fileNameMS, testName);
                break;
            case ComparableFileType.AZW_3:
                cmdLine = msString.format(CMD_COMPARE_AZW_3, fileNameSrc, comparableOut, comparableGold, comparableOriginalGold, fileNameMS, testName);
                break;
            default:
                throw new IllegalArgumentException("Unexpected comparable file type.");
        }
        if (topmost)
            cmdLine = msString.plusEqOperator(cmdLine, " /topmost:");
        if (restorePosition)
            cmdLine = msString.plusEqOperator(cmdLine, " /restoreposition:");
        synchronized (G_SYNC_OBJ)
        {
            return (/*ComparerFormResult*/int)TestUtilPal.executeProcess(G_ASPOSE_TEST_UI.getExe(), cmdLine);
        }
    }

    private static /*ComparerFormResult*/int executeTestUI(
        String testName,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String fileNameMS,
        String message,
        /*ComparableFileType*/int comparableFileType) throws Exception
	{
		return executeTestUI(testName, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS, message, comparableFileType, false);
	}

    private static /*ComparerFormResult*/int executeTestUI(
        String testName,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String fileNameMS,
        String message,/*ComparableFileType*/int comparableFileType,
        boolean topMost) throws Exception
	{
		return executeTestUI(testName, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS, message, comparableFileType, topMost, false);
	}

    private static /*ComparerFormResult*/int executeTestUI(
        String testName,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String fileNameMS,
        String message,/*ComparableFileType*/int comparableFileType,
        boolean topMost,
        boolean restorePostion) throws Exception
    {
        return executeTestUI(testName, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS,
            message, comparableFileType, fileNameOut, fileNameGold, fileNameOriginalGold, topMost, restorePostion);
    }

    public static /*ComparerFormResult*/int executeImageTestUI(String fileNameOut, String fileNameGold, String fileNameOriginalGold, String fileNameSrc, String message) throws Exception
    {
        return executeTestUI(null, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, null, message,
            ComparableFileType.IMAGE);
    }

    public static /*ComparerFormResult*/int executeTextTestUI(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String fileNameMS,
        boolean topmost,
        boolean restorePosition) throws Exception
    {
        return executeTestUI(title, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc,
            fileNameMS, null, ComparableFileType.TEXT, topmost, restorePosition);
    }

    public static /*ComparerFormResult*/int executeZipTestUI(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String fileNameMS) throws Exception
    {
        return executeTestUI(title, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS, null,
            ComparableFileType.ZIP);
    }

    public static /*ComparerFormResult*/int executeMobiTestUI(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String fileNameMS) throws Exception
    {
        return executeTestUI(title, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS, null,
            ComparableFileType.MOBI);
    }

    public static /*ComparerFormResult*/int executeAzw3TestUI(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String fileNameMS) throws Exception
    {
        return executeTestUI(title, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS, null,
            ComparableFileType.AZW_3);
    }

    public static /*ComparerFormResult*/int executeComparableTestUI(
        String testName,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String message,
        /*ComparableFileType*/int comparableFileType,
        String comparableOut,
        String comparableGold,
        String comparableOriginalGold) throws Exception
    {
        return executeTestUI(testName, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, null, message,
            comparableFileType, comparableOut, comparableGold, comparableOriginalGold);
    }

    private static final String CMD_COMPARE_IMAGE = "/task:CompareImage /out:\"{0}\" /gold:\"{1}\" /originalGold:\"{2}\" /src:\"{3}\" /message:\"{4}\" /difflabels:\"{5}\"";
    private static final String CMD_COMPARE_TEXT = "/task:CompareText /out:\"{0}\" /gold:\"{1}\" /originalGold:\"{2}\" /src:\"{3}\" /title:\"{4}\" /filesToShow:\"{5}\"";
    private static final String CMD_COMPARE_ZIP = "/task:CompareZip /src:\"{0}\" /out:\"{1}\" /gold:\"{2}\" /originalGold:\"{3}\" /ms:\"{4}\" /title:\"{5}\" /difflabels:\"{6}\"";
    private static final String CMD_COMPARE_MOBI = "/task:CompareMobi /src:\"{0}\" /out:\"{1}\" /gold:\"{2}\" /originalGold:\"{3}\" /ms:\"{4}\" /title:\"{5}\"";
    private static final String CMD_COMPARE_AZW_3 = "/task:CompareAzw3 /src:\"{0}\" /out:\"{1}\" /gold:\"{2}\" /originalGold:\"{3}\" /ms:\"{4}\" /title:\"{5}\"";

    private static /*final*/ AbstractGoldComparer.Tool G_ASPOSE_TEST_UI = new AbstractGoldComparer.Tool("Aspose.TestUI\\bin\\Aspose.TestUI.exe");
    private static /*final*/ Object G_SYNC_OBJ = new Object();
}

