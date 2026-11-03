// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/06/2015 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.Assert;
import com.aspose.ms.System.msString;


/// <summary>
/// Collects all warnings and asserts if number of warnings is more than MaxCount.
/// </summary>
public class TestMaxWarningCountCallback extends TestWarningCallback
{
    public TestMaxWarningCountCallback(int maxWarningCount)
    {
        mMaxCount = maxWarningCount;
    }

    public int getMaxCount() { return mMaxCount; }

    protected /*override*/ void warningAdded()
    {
        if (getMaxCount() >= 0 && getCount() > getMaxCount())
            Assert.fail(msString.format("Too many warnings (expected {0}).", getMaxCount()));
    }

    private int mMaxCount;
}

