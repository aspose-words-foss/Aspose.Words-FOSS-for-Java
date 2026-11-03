// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


class EmfComparer extends AbstractGoldComparer
{
    public /*override*/ void execute(ComparerParams comparerParams) throws Exception
    {
        String testName = comparerParams.getTitle();
        String outFileName = comparerParams.getFileNameOut();
        String originalImageFileName = comparerParams.getFileNameSrc();

        String goldFileName = TestFxUtil.buildGoldFileName(testName, "", ".emf");
        ImageFileComparer.execute(outFileName, goldFileName, originalImageFileName);
    }
}

