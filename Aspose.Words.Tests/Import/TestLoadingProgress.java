// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.System.msString;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.msMath;


/// <summary>
/// Tests which checks how client may be notified about document loading progress.
/// </summary>
@Test
public class TestLoadingProgress
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


    // FOSS Test14472NoProgress removed: it loads an MHTML file to check the "progress unsupported"
    // warning, but MHTML loading is removed (throws) in the FOSS build.



    /// <summary>
    /// Related with WORDSNET-14472
    /// Checks that content was read and may be saved when progress callback is set.
    /// </summary>
    @Test
    public void test14472Simple() throws Exception
    {
        TestWarningCallback wc = new TestWarningCallback();

        TestLoadingProgressCallback testCallback = loadAndCheckProgress(
            "Model\\Progress\\Test14472Simple.fopc",
            wc,
            null,
            true,
            LoadFormat.AUTO);

        msAssert.isFalse(wc.contains(
                WarningUtil.toLoadWarningSource(LoadFormat.FLAT_OPC),
                WarningType.HINT,
                msString.format(WarningStrings.LOADING_PROGRESS_UNSUPPORTED, LoadFormat.FLAT_OPC)));

        ArrayList<DocumentLoadingArgs> args = testCallback.getAcceptedArguments();
        msAssert.less(0, args.size());
        msAssert.areEqual(100.0d, DOUBLE_DELTA, args.get(args.size() - 1).getEstimatedProgress());
    }


    /// <summary>
    /// Related with WORDSNET-14472
    /// Checks how loading errors process.
    /// </summary>
    @Test (expectedExceptions = FileCorruptedException.class)
    public void test14472LoadError() throws Exception
    {
        String path = "ImportDocx\\Package\\TestJira13791.docx";
        loadAndCheckProgress(path);
    }

    /// <summary>
    /// Related with WORDSNET-14472
    /// Checks the case when source document has zero paragraphs.
    /// </summary>
    @Test (groups = "SingleThreadGroup")
    public void test14472NoPara() throws Exception
    {
        String path = "ExportRtf\\Test19533.docx";
        TestLoadingProgressCallback testCallback = loadAndCheckProgress(path);

        ArrayList<DocumentLoadingArgs> args = testCallback.getAcceptedArguments();

        msAssert.less(0, args.size());
        msAssert.areEqual(100.0d, DOUBLE_DELTA, args.get(args.size() - 1).getEstimatedProgress());
    }

    /// <summary>
    /// Related with WORDSNET-14472
    /// Checks the case when post loading stage progress may complete with value less than 100%.
    /// I.e. we should notify about 100% progress at the end of post loading stage.
    /// </summary>
    @Test (groups = "SingleThreadGroup")
    public void test14472PostLoadingProgressComplete() throws Exception
    {
        String path = "Model\\Markup\\Test18729.docx";
        TestLoadingProgressCallback testCallback = loadAndCheckProgress(path);

        ArrayList<DocumentLoadingArgs> args = testCallback.getAcceptedArguments();

        msAssert.less(0, args.size());
        msAssert.areEqual(100.0d, DOUBLE_DELTA, args.get(args.size() - 1).getEstimatedProgress());
    }

    // FOSS TestStageBorders removed: its only cases load WML and ODT, both removed load formats.

    /// <summary>
    /// WORDSNET-23836 Progress does not work while loading blank file.
    /// AW loads the blank document from resources when input stream is empty.
    /// Use client callback for loading the blank to fix the issue.
    /// </summary>
    @Test
    public void test23836() throws Exception
    {
        TestLoadingProgressCallback testCallback = loadAndCheckProgress("Model\\Progress\\Test23836.docx");

        ArrayList<DocumentLoadingArgs> args = testCallback.getAcceptedArguments();
        msAssert.areEqual(2, args.size());

        msAssert.areEqual(70.0d, DOUBLE_DELTA, args.get(0).getEstimatedProgress());
        msAssert.areEqual(100.0d, DOUBLE_DELTA, args.get(1).getEstimatedProgress());
    }

    @Test (enabled = false)
    public static int getMaxArgsCount(int precision)
    {
        // Not more than "100 * 10^ProgressUtils.Precision" items must be in the arguments collection.
        // I.e. for two digits it will be 100*100 = 10000.
        final int DECIMAL_BASE = 10;
        int maxArgsCount = (int)msMath.round(Math.pow(DECIMAL_BASE, precision));

        // Take into account digits before point.
        maxArgsCount *= 100;

        return maxArgsCount;
    }

    private TestLoadingProgressCallback loadAndCheckProgress(String path) throws Exception
    {
        return loadAndCheckProgress(path, null, new TestLoadingProgressCallback(), false, LoadFormat.AUTO);
    }

    private TestLoadingProgressCallback loadAndCheckProgress(String path, TestWarningCallback warningCallback) throws Exception
    {
        return loadAndCheckProgress(path, warningCallback, new TestLoadingProgressCallback(), false, LoadFormat.AUTO);
    }

    private TestLoadingProgressCallback loadAndCheckProgress(
        String path,
        TestWarningCallback warningCallback,
        TestLoadingProgressCallback testCallback,
        boolean verifyExport,
        /*LoadFormat*/int loadFormat) throws Exception
    {
        testCallback = (testCallback == null) ? new TestLoadingProgressCallback() : testCallback;

        LoadOptions lo = new LoadOptions();
        lo.setProgressCallback(testCallback);
        lo.setLoadFormat(loadFormat);

        if (warningCallback != null)
            lo.setWarningCallback(warningCallback);

        Document doc = null;
        try
        {
            doc = TestUtil.open(path, lo);
        }
        finally
        {
            checkProgress(testCallback);
        }

        if (verifyExport)
            TestUtil.saveOpen(doc, path, UnifiedScenario.DOCX_2_DOCX);

        return testCallback;
    }

    private void checkProgress(TestLoadingProgressCallback testCallback)
    {
        // 1. Not more than "100 * 10^ProgressUtils.Precision" items must be in the arguments collection.
        int maxArgsCount = getMaxArgsCount(LoadingProgressCalculator.PRECISION);
        msAssert.lessOrEqual(testCallback.getAcceptedArguments().size(), maxArgsCount);

        DocumentLoadingArgs prevArgs = null;
        for (DocumentLoadingArgs args : testCallback.getAcceptedArguments())
        {
            double progress = args.getEstimatedProgress();

            // 2. Check progress precision.
            msAssert.areEqual(progress, msMath.round(progress, LoadingProgressCalculator.PRECISION));

            // 3. Next progress value must be greater than previous value.
            // DS: AW does two attempts of loading when input format specified explicitly.
            // So, generally it is possible to obtain situation when a next progress value will be less than previous.
            // However, i do not see a way to reproduce it in the scope of DOCX format.
            if (prevArgs != null)
                msAssert.less(prevArgs.getEstimatedProgress(), progress);

            prevArgs = args;
        }
    }

    private static String loadFormatToExtension(/*LoadFormat*/int lf)
    {
        String extension;
        switch (lf)
        {
            case LoadFormat.WORD_ML:
                extension = "wml";
                break;
            case LoadFormat.MHTML:
                extension = "mht";
                break;
            case LoadFormat.TEXT:
                extension = "txt";
                break;
            default:
                extension = LoadFormat.toString(lf);
                break;
        }

        return extension;
    }

    private static final double DOUBLE_DELTA = 0.000001d;
}

