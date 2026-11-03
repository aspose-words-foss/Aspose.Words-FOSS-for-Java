// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/11/2016 by Hasanov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestImageDataCore
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-14428 The ImageDataCore.GetIcoImageBytes(Stream stream, int imageIndex)
    /// incorrectly positions the stream reader before calling ImageUtil.PrependBmpHeader.
    /// ihasanov: The problem occurred because of not setting to offset when ico-file contains multiple entries.
    /// </summary>
    @Test
    public void testJira14428() throws Exception
    {
        assertIcoFile(TestUtil.buildTestFileName("Model\\Drawing\\Core\\icon_01.ico"), 2);
        assertIcoFile(TestUtil.buildTestFileName("Model\\Drawing\\Core\\icon_02.ico"), 1);
        assertIcoFile(TestUtil.buildTestFileName("Model\\Drawing\\Core\\icon_03.ico"), 2);
        assertIcoFile(TestUtil.buildTestFileName("Model\\Drawing\\Core\\icon_04.ico"), 2);
        assertIcoFile(TestUtil.buildTestFileName("Model\\Drawing\\Core\\icon_05.ico"), 4);
    }

    /// <summary>
    /// WORDSNET-18086 An exception occurred because AW tried to load PNG images stored in .ICO files as BMP images.
    @Test
    public void testJira18086() throws Exception
    {
        assertIcoFile(TestUtil.buildTestFileName("Model\\Drawing\\Core\\pngIcon.ico"), 1);
    }

    private static void assertIcoFile(String icoFile, int entries) throws Exception
    {
        for (int entryIdx = 0; entryIdx < entries; entryIdx++)
        {
            FileStream fileStream = new FileStream(icoFile, FileMode.OPEN, FileAccess.READ);
            try /*JAVA: was using*/
            {
                msAssert.isNotNull(TestUtil.readAllBytes(icoFile)); // check if ICO file has been read ok
                msAssert.isNotNull(ImageDataCore.getIcoImageBytes(fileStream, entryIdx)); // check each ICO's image entry
            }
            finally { if (fileStream != null) fileStream.close(); }
        }
    }
}

