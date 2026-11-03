// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.words.ref.Ref;


class XpsComparer extends AbstractGoldComparer
{
    public /*override*/ void execute(ComparerParams comparerParams) throws Exception
    {
        execute(comparerParams.getTitle(), comparerParams.getFileNameOut(), comparerParams.getFileNameMs(),
            TestSettings.getRoundXpsPoints() ? RoundingXpsPagePartComparer.INSTANCE : null);
    }

    public /*override*/ void verifyConformance(ComparerParams comparerParams) throws Exception
    {
        verifyConformance(comparerParams.getFileNameOut());
    }

    public static void execute(String testName, String outFileName, String originalFileName) throws Exception
	{
		execute(testName, outFileName, originalFileName, null);
	}

    public static void execute(String testName, String outFileName, String originalFileName, IPackagePartComparer comparer) throws Exception
    {
        verifyConformance(outFileName);

        String goldFileName = TestFxUtil.buildGoldFileName(testName, "", ".xps");
        new ZipFileComparer().execute(
            "EXPORT XPS",
            originalFileName,
            outFileName,
            goldFileName,
            null,
            comparer);
    }

    public static void verifyConformance(String xpsFileName) throws Exception
    {
        // RK To properly launch the right isXps.exe I need to know the bitness of the OS, not the bitness of the current process.
        String exeName = G_XPS.getExe();

        synchronized (G_IS_XPS_LOCK_OBJECT)
        {
            String output = null;
            // WORDSNET-7570 Change TEMP folder for isXps.exe to avoid conflict with TeamCity. We can't use Path.GetTempPath() because it returns TC temp folder.
            String tempDir = TestEnvironment.getLocalAppDataTmp();
            Ref<String> referenceToOutput = new Ref<String>(output);
            int exitCode = TestUtilPal.executeProcess(exeName, msString.format(G_XPS.getCmdLine1(), xpsFileName), tempDir, /*out*/ referenceToOutput);
            output = referenceToOutput.get();
            if (exitCode != 0)
            {
                throw new IllegalStateException(msString.format(
                    "XPS file {1} does not conform: \n{0}",
                    output, xpsFileName));
            }
        }
    }

    private static /*final*/ Object G_IS_XPS_LOCK_OBJECT = new Object();
    private static /*final*/ Tool G_XPS;

    static/* XpsComparer()*/
    {
        G_XPS = TestUtilPal.is64BitWindows()
            ? new Tool("xps-tools\\amd64\\isXps.exe", new String[] { "-f=\"{0}\"" })
            : new Tool("xps-tools\\x86\\isXps.exe", new String[] { "-f=\"{0}\"" });
    }
}

