// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2005 by Roman Korchagin
// 2016/06/21 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestFileStream
{
    @Test
    public void testWrite() throws Exception
    {
        // Write to TestOut rather than TestData, the test only needs a scratch file.
        String testFilePath = TestFxUtil.getInTestOutPath("MsTestWrite.doc");
        TestFxUtil.ensureDirectoryForFileExists(testFilePath);

        Stream dst = new FileStream(testFilePath, FileMode.CREATE);
        try /*JAVA: was using*/
        {

            //Test properties
            msAssert.areEqual(0, dst.getPosition());
            msAssert.areEqual(0, dst.getLength());

            //Write data
            byte[] b = { (byte) 1, (byte) 2, (byte) 3, (byte) 4 };
            dst.write(b, 0, b.length);
            msAssert.areEqual(4, dst.getPosition());
            msAssert.areEqual(4, dst.getLength());

            //Check it was written.
            dst.setPosition(0);
            byte[] x = new byte[4];
            dst.read(x, 0, 4);
            msAssert.areEqual(1, x[0]);
            msAssert.areEqual(2, x[1]);
            msAssert.areEqual(3, x[2]);
            msAssert.areEqual(4, x[3]);
        }
        finally { if (dst != null) dst.close(); }
    }
}

