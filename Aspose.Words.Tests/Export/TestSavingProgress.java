// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import java.util.ArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.msMath;


/// <summary>
/// Tests which checks how client may be notified about document writing progress.
/// </summary>
@Test
public class TestSavingProgress
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
    /// Related with WORDSNET-11575
    /// Checks the case when source document has zero paragraphs.
    /// </summary>
    @Test (groups = "SingleThreadGroup")
    public void test11575NoPara() throws Exception
    {
        final String PATH = "ExportRtf\\Test19533.docx";
        TestSavingProgressCallback callback = openSaveAndCheckProgress(PATH, SaveFormat.FLAT_OPC);

        ArrayList<DocumentSavingArgs> args = callback.getAcceptedArguments();

        msAssert.less(0, args.size());
        msAssert.areEqual(100.0d, DOUBLE_DELTA, args.get(args.size() - 1).getEstimatedProgress());
    }

    private static TestSavingProgressCallback openSaveAndCheckProgress(String srcPath, /*SaveFormat*/int saveFormat) throws Exception
    {
        return openSaveAndCheckProgress(srcPath, saveFormat, null, null, true);
    }

    private static void openSaveAndCheckProgress(String srcPath,
        /*SaveFormat*/int saveFormat, TestSavingProgressCallback callback) throws Exception
    {
        openSaveAndCheckProgress(srcPath, saveFormat, callback, null, true);
    }

    private static TestSavingProgressCallback openSaveAndCheckProgress(
        String srcPath,
        /*SaveFormat*/int saveFormat,
        TestSavingProgressCallback callback,
        TestWarningCallback warningCallback,
        boolean reopenAfterSave) throws Exception
    {
        Document doc = TestUtil.open(srcPath);
        doc.setWarningCallback(warningCallback);

        callback = (callback == null) ? new TestSavingProgressCallback() : callback;
        SaveOptions so = SaveOptions.createSaveOptions(saveFormat);
        so.setProgressCallback(callback);

        String savePath = "Model\\Progress\\" + Path.getFileNameWithoutExtension(srcPath) + FileFormatUtil.saveFormatToExtension(saveFormat);

        try
        {
            if (reopenAfterSave)
                TestUtil.saveOpen(doc, savePath, so, false);
            else
                TestUtil.save(doc, savePath, so, false);
        }
        finally
        {
            checkProgress(callback);
        }

        return callback;
    }

    /// <summary>
    /// Checks progress notifications content.
    /// </summary>
    private static void checkProgress(TestSavingProgressCallback callback)
    {
        // 1. Not more than "100 * 10^ProgressUtils.Precision" items must be in the arguments collection.
        int maxArgsCount = TestLoadingProgress.getMaxArgsCount(SavingProgressCalculator.PRECISION);
        msAssert.lessOrEqual(callback.getAcceptedArguments().size(), maxArgsCount);

        DocumentSavingArgs prevArgs = null;
        for (DocumentSavingArgs args : callback.getAcceptedArguments())
        {
            double progress = args.getEstimatedProgress();

            // 2. Check progress precision.
            msAssert.areEqual(progress, msMath.round(progress, SavingProgressCalculator.PRECISION));

            // 3. Next progress value must be greater than previous value.
            if (prevArgs != null)
                msAssert.less(prevArgs.getEstimatedProgress(), progress);

            prevArgs = args;
        }
    }

    private static final double DOUBLE_DELTA = 0.000001d;
}

