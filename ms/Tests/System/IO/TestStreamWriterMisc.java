// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2008 by Konstantin Sidorenko
// 2016/06/23 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.IO.Directory;
import org.testng.annotations.AfterClass;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.StreamReader;
import java.lang.Class;


/// <summary>
/// Tests adapted from Rotor from files:
/// \Rotor\sscli20\tests\bcl\system\io\streamWriter\:
/// co5591set_autoFlush.cs.
/// </summary>
@Test
public class TestStreamWriterMisc
{
    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception
    {
        if (!Directory.exists(getTestDir(TestStreamWriterMisc.class)))
            Directory.createDirectory(getTestDir(TestStreamWriterMisc.class));
    }

    @AfterClass (alwaysRun = true)
    public void tearDown() throws Exception
    {
        Directory.delete(getTestDir(TestStreamWriterMisc.class), true);
    }

    @Test
    public void testStreamWriterAutoFlush() throws Exception
    {
        StreamWriter sw2 = new StreamWriter(new MemoryStream());
        sw2.setAutoFlush(true);
        msAssert.areEqual(sw2.getAutoFlush(), true);
        sw2.close();

        sw2 = new StreamWriter(new MemoryStream());
        sw2.setAutoFlush(false);
        msAssert.areEqual(sw2.getAutoFlush(), false);
        sw2.close();
    }

    //JAVA: .net throws ObjectDisposedException here, but i think IllegalStateException is more appropriate.
    @Test (expectedExceptions = IllegalStateException.class)
    public void testStreamWriterClose1() throws Exception
    {
        StreamWriter sw2;
        sw2 = new StreamWriter(new MemoryStream());
        sw2.close();
        sw2.write('A');
    }

    //JAVA: .net throws ObjectDisposedException here, but i think IllegalStateException is more appropriate.
    @Test (expectedExceptions = IllegalStateException.class)
    public void testStreamWriterClose2() throws Exception
    {
        StreamWriter sw2;
        sw2 = new StreamWriter(new MemoryStream());
        sw2.close();
        sw2.writeLine("hello");
    }

    //JAVA: .net throws ObjectDisposedException here, but i think IllegalStateException is more appropriate.
    @Test (expectedExceptions = IllegalStateException.class)
    public void testStreamWriterClose3() throws Exception
    {
        StreamWriter sw2;
        sw2 = new StreamWriter(new MemoryStream());
        sw2.close();
        sw2.flush();
    }

    @Test
    public void testStreamWriterClose4() throws Exception
    {
        StreamWriter sw2;
        sw2 = new StreamWriter(new MemoryStream());
        sw2.close();
        msAssert.isNull(sw2.getBaseStream());
    }

    //JAVA: .net throws ObjectDisposedException here, but i think IllegalStateException is more appropriate.
    @Test (expectedExceptions = IllegalStateException.class)
    public void testStreamWriterClose5() throws Exception
    {
        StreamWriter sw2;
        sw2 = new StreamWriter(new MemoryStream());
        sw2.close();
        sw2.setAutoFlush(true);
    }

    @Test
    public void testStreamWriterClose6() throws Exception
    {
        // Use TestOut directory for creating test directories to avoid UnautorizedAccessException when run with NUnit3.
        String strFileName = Path.combine(getTestDir(getClass()), "Co5595Test.tmp");
        File.delete(strFileName);
        StreamWriter sw2 = new StreamWriter(strFileName);
        sw2.write("hello");
        sw2.close();
        StreamReader sr2 = new StreamReader(strFileName);
        String tmp = sr2.readToEnd();
        msAssert.areEqual(tmp, "hello");
        sr2.close();
        File.delete(strFileName);
    }

    @Test
    public void testStreamWriterFlush() throws Exception
    {
        StreamWriter sw2;
        MemoryStream memstr2 = new MemoryStream();
        sw2 = new StreamWriter(memstr2);
        String strTemp = "HelloWorld";
        sw2.write(strTemp);
        msAssert.areEqual(memstr2.getLength(), 0);

        sw2.flush();
        msAssert.areEqual(memstr2.getLength(), strTemp.length());
        sw2.close();
    }

    @Test (enabled = false)
    public static String getTestDir(Class testClass) throws Exception
    {
        // Use TestOut directory for creating test directories to avoid UnautorizedAccessException when run with NUnit3.
        return TestFxUtil.buildOutFileName(testClass.getName(), "", "");
    }
}

