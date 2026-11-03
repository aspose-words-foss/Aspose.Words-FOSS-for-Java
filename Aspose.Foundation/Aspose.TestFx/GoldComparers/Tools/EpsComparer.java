// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/05/2023 by Denis Panov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.words.ref.Ref;


class EpsComparer extends AbstractGoldComparer
{
    public /*override*/ void execute(ComparerParams comparerParams) throws Exception
    {
        String srcFileName = comparerParams.getFileNameSrc();
        String outFileName = comparerParams.getFileNameOut();
        String goldFileName = comparerParams.getFileNameGold();
        ;

        rasterizePostScript(srcFileName, outFileName);
        ImageFileComparer.execute(outFileName, goldFileName, null);
    }


    private static void rasterizePostScript(String epsFileName, String outFileName) throws Exception
    {
        String exeName = G_GSWIN.getExe();

        String cmdLine =
            msString.format(
                G_GSWIN.getCmdLine2(),
                outFileName,
                epsFileName);

        synchronized (G_IS_EPS_LOCK_OBJECT)
        {
            String output = null;
            Ref<String> referenceToOutput = new Ref<String>(output);
            int exitCode = TestUtilPal.executeProcess(exeName, cmdLine, /*out*/ referenceToOutput);
            output = referenceToOutput.get();

            if (exitCode != 0)
            {
                throw new IllegalStateException(
                    msString.format(
                        "gswin64c.exe failed, exit code: {0}. \r\nFile: {1} \r\nMessage: {2}", exitCode, epsFileName, output));
            }
        }
    }

    public /*override*/ void verifyConformance(ComparerParams comparerParams) throws Exception
    {
        String epsFileName = comparerParams.getFileNameOut();
        String exeName = G_GSWIN.getExe();

        synchronized (G_IS_EPS_LOCK_OBJECT)
        {
            String output = null;
            Ref<String> referenceToOutput = new Ref<String>(output);
            int exitCode = TestUtilPal.executeProcess(exeName, msString.format(G_GSWIN.getCmdLine1(), epsFileName), /*out*/ referenceToOutput);
            output = referenceToOutput.get();
            if (exitCode != 0)
            {
                throw new IllegalStateException(msString.format(
                    "Encapsulated PostScript file {0} is not valid: \n{1}",
                    epsFileName, output));
            }
        }
    }

    private static /*final*/ Object G_IS_EPS_LOCK_OBJECT = new Object();
    private static /*final*/ Tool G_GSWIN;

    static/* EpsComparer()*/
    {
        G_GSWIN = TestUtilPal.is64BitWindows()
            ? new Tool("PostScript-tools\\gs64\\gswin64c.exe",
                new String[]
                {
                    "-sDEVICE=nullpage -dNOPAUSE -dBATCH \"{0}\"",
                    "-dSAFER -dBATCH -dNOPAUSE -dEPSCrop -sDEVICE=png16m -dGraphicsAlphaBits=4 -r96 -sOutputFile=\"{0}\" \"{1}\""
                })
            : new Tool("PostScript-tools\\gs32\\gswin32c.exe",
                new String[]
                {
                    "-sDEVICE=nullpage -dNOPAUSE -dBATCH \"{0}\"",
                    "-dSAFER -dBATCH -dNOPAUSE -dEPSCrop -sDEVICE=png16m -dGraphicsAlphaBits=4 -r96 -sOutputFile=\"{0}\" \"{1}\""
                });
    }
}

