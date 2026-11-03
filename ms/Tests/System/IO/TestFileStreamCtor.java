// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2008 by Konstantin Sidorenko
// 2016/06/21 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.IO.Directory;
import org.testng.annotations.AfterClass;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import java.io.FileNotFoundException;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.ms.System.IO.StreamReader;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.DateTime;
import java.io.IOException;
import java.lang.Class;
import com.aspose.ms.System.Threading.CurrentThread;


/// <summary>
/// Tests adapted from Rotor from files:
/// \Rotor\sscli20\tests\bcl\system\io\filestream\Co5575ctor_str_fm.cs
/// </summary>
@Test
public class TestFileStreamCtor
{
    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception
    {
        if (!Directory.exists(getTestDir(TestFileStreamCtor.class)))
            Directory.createDirectory(getTestDir(TestFileStreamCtor.class));
    }

    @AfterClass (alwaysRun = true)
    public void tearDown() throws Exception
    {
        Directory.delete(getTestDir(TestFileStreamCtor.class), true);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testFileStreamCtor1() throws Exception
    {
        new FileStream(null, FileMode.OPEN);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testFileStreamCtor2() throws Exception
    {
        new FileStream("", FileMode.OPEN);
    }

    @Test (expectedExceptions = FileNotFoundException.class)
    public void testFileStreamCtor3() throws Exception
    {
        new FileStream(".", FileMode.OPEN);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testFileStreamCtor4() throws Exception
    {
        new FileStream(getTestFileName(getClass()), ~FileMode.OPEN);
    }

    @Test (expectedExceptions = FileNotFoundException.class)
    public void testFileStreamCtor5() throws Exception
    {
        String filName = getTestFileName(getClass());
        File.delete(filName);
        new FileStream(filName, FileMode.OPEN);
    }

    @Test
    public void testFileStreamCtor6() throws Exception
    {
        String filName = getTestFileName(getClass());
        FileStream fs2 = new FileStream(filName, FileMode.CREATE);
        try /*JAVA: was using*/
        {
            StreamWriter sw2 = new StreamWriter(fs2);
            sw2.setAutoFlush(true);
            for (char c = 'A'; c <= 'Z'; c++)
                sw2.write(c);
            fs2.setPosition(0);
            StreamReader sr2 = new StreamReader(fs2);
            int tmp;
            for (int c = 'A'; c <= 'Z'; c++)
            {
                tmp = sr2.read();
                msAssert.areEqual(tmp, c);
            }
        }
        finally { if (fs2 != null) fs2.close(); }

        FileStream fs21 = new FileStream(filName, FileMode.CREATE);
        try /*JAVA: was using*/
        {
            StreamWriter sw2 = new StreamWriter(fs21);
            sw2.setAutoFlush(true);

            StreamReader sr2 = new StreamReader(fs21);
            for (int i = 0; i < 10; i++)
                sw2.write(i);
            fs21.setPosition(0);
            int tmp;
            int i32 = '0';
            for (int i = 0; i < 10; i++)
            {
                tmp = sr2.read();
                msAssert.areEqual(tmp, i32++);
            }
        }
        finally { if (fs21 != null) fs21.close(); }

        FileStream fs22 = new FileStream(filName, FileMode.TRUNCATE);
        try /*JAVA: was using*/
        {
            msAssert.areEqual(fs22.getLength(), 0);

            StreamReader sr2 = new StreamReader(fs22);
            msAssert.areEqual(sr2.read(), -1);

            StreamWriter sw2 = new StreamWriter(fs22);
            sw2.setAutoFlush(true);
            for (char c = 'A'; c < 'K'; c++)
                sw2.write(c);
        }
        finally { if (fs22 != null) fs22.close(); }
    }

    @Test
    public void testFileStreamCtor7() throws Exception
    {
        String strFileName = Path.combine(getTestDir(getClass()), "ETE_LOGFILE.txt");
        StreamWriter writer = new StreamWriter(strFileName);
        try /*JAVA: was using*/
        {
            writer.writeLine("This is a test file");
            writer.close();

            FileStream fs = new FileStream(strFileName, FileMode.APPEND);

            StreamWriter logStream = new StreamWriter(fs);
            String s = "User logged out at " + DateTime.getNow();
            logStream.writeLine(s);
            logStream.close();

            StreamReader reader = new StreamReader(strFileName);
            String strFileContent = reader.readToEnd();
            msAssert.isTrue(strFileContent.contains(s));
            reader.close();

            File.delete(strFileName);
        }
        finally { if (writer != null) writer.close(); }
    }

    @Test (expectedExceptions = IOException.class)
    public void testFileStreamCtor8() throws Exception
    {
        String filName = getTestFileName(getClass());
        FileStream fs2 = new FileStream(filName, FileMode.CREATE);
        fs2.close();
        new FileStream(filName, FileMode.CREATE_NEW);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testFileStreamCtor9() throws Exception
    {
        new FileStream(getTestFileName(getClass()), FileMode.CREATE | FileMode.TRUNCATE);
    }

    @Test (enabled = false)
    public static String getTestFileName(Class testClass) throws Exception
    {
        return getTestDir(testClass) + Path.DirectorySeparatorChar + testClass.getName() + "." + DateTime.getNow().getMillisecond() + "." + CurrentThread.getId() + ".tmp";
    }

    @Test (enabled = false)
    public static String getTestDir(Class testClass) throws Exception
    {
        // Use TestOut directory for creating test directories to avoid UnautorizedAccessException when run with NUnit3.
        return TestFxUtil.buildOutFileName(testClass.getName(), "", "");
    }
}

