// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/04/2022 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import com.aspose.ms.NUnit.Framework.TestCaseData;


@Test
public abstract class UnifiedTestsBase
{
    @BeforeClass (alwaysRun = true)
    public void testFixtureSetUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void testSetUp()
    {
        RandomUtil.reset();
    }

    public static /*final*/ TestCaseData[] DEFAULT_TEST_SCENARIOS =
    {
        // Reduced to FOSS supported formats only.
        new TestCaseData(LoadFormat.DOCX, SaveFormat.DOCX)
    };
}

