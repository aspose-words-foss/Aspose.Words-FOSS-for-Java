// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2007 by Konstantin Sidorenko
// 2015/12/30 by Anatoliy Sidorenko


package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.File;


@Test
public class TestFile
{
    @Test
    public void testFileExist() throws Exception
    {
        msAssert.areEqual(File.exists(""), false);
        msAssert.areEqual(File.exists(null), false);

        msAssert.areEqual(File.exists(TestEnvironment.getRawRoot() + "\\words-net\\words-net"), false);
        msAssert.areEqual(File.exists(TestEnvironment.getRawRoot() + "\\words-net\\words-net\\Aspose.Words.sln"), true);
        msAssert.areEqual(File.exists(TestEnvironment.getRawRoot() + "/words-net/words-net/Aspose.Words.sln"), true);
    }
}

