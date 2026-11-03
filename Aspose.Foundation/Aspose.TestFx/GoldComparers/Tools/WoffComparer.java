// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.words.ref.Ref;


class WoffComparer extends AbstractGoldComparer
{
    public /*override*/ void execute(ComparerParams comparerParams) throws Exception
    {
        String woffFileName = comparerParams.getFileNameOut();

        synchronized (G_IS_WOFF_LOCK_OBJECT)
        {
            String exeName = G_WOFF_VALIDATOR.getExe();
            String output = null;
            String tempDir = TestEnvironment.getLocalAppDataTmp();
            Ref<String> referenceToOutput = new Ref<String>(output);
            int exitCode = TestUtilPal.executeProcess(exeName, msString.format(G_WOFF_VALIDATOR.getCmdLine1(), woffFileName), tempDir, /*out*/ referenceToOutput);
            output = referenceToOutput.get();
            if (exitCode != 0)
            {
                throw new IllegalStateException(msString.format(
                    "Woff file {1} does not pass validation: \n{0}",
                    output, woffFileName));
            }
        }
    }

    private static /*final*/ Object G_IS_WOFF_LOCK_OBJECT = new Object();
    private static /*final*/ Tool G_WOFF_VALIDATOR = new Tool("WoffValidator\\validate.exe", new String[] { "\"{0}\"" });
}

