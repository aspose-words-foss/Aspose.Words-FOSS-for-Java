// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/05/2022 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.BeforeTest;


public class SetUpFixture
{
    @BeforeTest
    public void oneTimeSetUp()
    {
        // Fix Resharper and Rider empty Test Session Output
        ensureConsoleTraceListenerUsesConsoleOutWriter();

        // Fix failed tests in Resharper and Rider caused by difference in Trace.Fail behavior.
        doNotThrowOnTraceFail();
    }

    private static void ensureConsoleTraceListenerUsesConsoleOutWriter()
    {
    }

    private static void doNotThrowOnTraceFail()
    {
    }
}

