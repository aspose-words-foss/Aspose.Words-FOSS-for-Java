// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/08/2008 by Konstantin Sidorenko
// 2016/06/22 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.IO.Directory;
import org.testng.annotations.AfterClass;
import com.aspose.ms.System.IO.StreamWriter;
import java.io.FileNotFoundException;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.StreamReader;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import java.lang.Class;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Threading.CurrentThread;


/// <summary>
/// Rotor's tests adapted for TestNG.
/// 
/// Tests taken form  Rotor\sscli20\tests\bcl\system\io\streamwriter directory.
/// In Rotor each tests sits in its own file named something like co5560ctor_str.cs.
/// Each the test code extracted from surounding support code and placed in its own
/// test methods named like Test_ctor_str1(), Test_ctor_str2(), etc.
/// //@todo 2 sk test network streams
/// </summary>
@Test
public class TestStreamWriterCtor
{
    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception
    {
        if (!Directory.exists(getTestDir(TestStreamWriterCtor.class)))
            Directory.createDirectory(getTestDir(TestStreamWriterCtor.class));
    }

    @AfterClass (alwaysRun = true)
    public void tearDown() throws Exception
    {
        Directory.delete(getTestDir(TestStreamWriterCtor.class), true);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void test_ctor_str1() throws Exception
    {
        StreamWriter sw = new StreamWriter((String)null);
    }

    @Test (expectedExceptions = FileNotFoundException.class)
    public void test_ctor_str2() throws Exception
    {
        StreamWriter sw = new StreamWriter("..");
    }

    @Test
    public void test_ctor_str3() throws Exception
    {
        String testPath = Path.combine(getTestDir(getClass()), "TestFile.tmp");

        StreamWriter sw = new StreamWriter(testPath);
        try /*JAVA: was using*/
        {
            sw.write(5);
            sw.flush();
            sw.close();
            StreamReader sr = new StreamReader(testPath);
            int tmp = sr.read();
            msAssert.areEqual(tmp, '5');
            sr.close();
            File.delete(testPath);
        }
        finally { if (sw != null) sw.close(); }
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void test_ctor_stream_enc1() throws Exception
    {
        StreamReader sr = new StreamReader((Stream)null, Encoding.getUTF8());
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void test_ctor_stream_enc2() throws Exception
    {
        StreamReader sr = new StreamReader(new MemoryStream(), null);
    }

    @Test
    public void test_ctor_stream_enc3() throws Exception
    {
        String fileName = getTestFileName(getClass());
        FileStream fs2 = new FileStream(fileName, FileMode.CREATE);
        StreamWriter sw2 = new StreamWriter(fs2, Encoding.getASCII());
        sw2.write("HelloThere\u00FF");
        sw2.close();

        fs2 = new FileStream(fileName, FileMode.OPEN);
        StreamReader sr2 = new StreamReader(fs2, Encoding.getASCII());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "HelloThere?");
        sr2.close();
    }

    @Test
    public void test_ctor_stream_enc4() throws Exception
    {
        MemoryStream ms2 = new MemoryStream();
        StreamWriter sw2 = new StreamWriter(ms2, Encoding.getASCII());
        sw2.write("HelloThere\u00FF");
        sw2.flush();
        ms2.setPosition(0);
        StreamReader sr2 = new StreamReader(ms2, Encoding.getASCII());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "HelloThere?");
        sr2.close();
    }

    @Test
    public void test_ctor_stream_enc5() throws Exception
    {
        String fileName = getTestFileName(getClass());
        FileStream fs2 = new FileStream(fileName, FileMode.CREATE);
        StreamWriter sw2 = new StreamWriter(fs2, Encoding.getUTF8());
        sw2.write("This is UTF8\u00FF");
        sw2.close();

        fs2 = new FileStream(fileName, FileMode.OPEN);
        StreamReader sr2 = new StreamReader(fs2, Encoding.getUTF8());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is UTF8\u00FF");
        sr2.close();
    }

    @Test
    public void test_ctor_stream_enc6() throws Exception
    {
        MemoryStream ms2 = new MemoryStream();
        StreamWriter sw2 = new StreamWriter(ms2, Encoding.getUTF8());
        sw2.write("This is UTF8\u00FF");
        sw2.flush();
        ms2.setPosition(0);
        StreamReader sr2 = new StreamReader(ms2, Encoding.getUTF8());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is UTF8\u00FF");

        //check bom
        ms2.setPosition(0);
        byte[] preamble = Encoding.getUTF8().getPreamble();
        byte[] bom = new byte[preamble.length];
        ms2.read(bom, 0, bom.length);
        msAssert.areEqual(preamble, bom);

        sr2.close();
    }

    @Test
    public void test_ctor_stream_enc7() throws Exception
    {
        String fileName = getTestFileName(getClass());
        FileStream fs2 = new FileStream(fileName, FileMode.CREATE);
        StreamWriter sw2 = new StreamWriter(fs2, Encoding.getUTF7());
        sw2.write("This is UTF7\u00FF");
        sw2.close();

        fs2 = new FileStream(fileName, FileMode.OPEN);
        StreamReader sr2 = new StreamReader(fs2, Encoding.getUTF7());
        String str1 = "This is UTF7\u00FF";
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, str1);
        sr2.close();
    }

    @Test
    public void test_ctor_stream_enc8() throws Exception
    {
        MemoryStream ms2 = new MemoryStream();
        StreamWriter sw2 = new StreamWriter(ms2, Encoding.getUTF7());
        sw2.write("This is UTF7\u00FF");
        sw2.flush();
        ms2.setPosition(0);

        //check bom - utf7 doesn't Write the bom as in .net
        ms2.setPosition(0);
        byte[] preamble = Encoding.getUTF7().getPreamble();
        byte[] bom = new byte[preamble.length];
        ms2.read(bom, 0, bom.length);
        msAssert.areEqual(preamble, bom);

        ms2.setPosition(0);
        StreamReader sr2 = new StreamReader(ms2, Encoding.getUTF7());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is UTF7\u00FF");
        sr2.close();
    }

    @Test
    public void testStreamWriterEncodingUTF7() throws Exception
    {
        Encoding encoding = Encoding.getUTF7();
        MemoryStream stream = new MemoryStream(128);
        StreamWriter sw2 = new StreamWriter(stream, encoding);

        sw2.write("111aAБ");
        sw2.flush();

        stream.setPosition(0);
        byte[] preamble = encoding.getPreamble();
        byte[] bom = new byte[preamble.length];
        stream.setPosition(0);
        stream.read(bom, 0, bom.length);
        msAssert.areEqual(preamble, bom);

        stream.setPosition(0);
        StreamReader Reader = new StreamReader(stream, encoding);
        String declaration = Reader.readToEnd();
        msAssert.areEqual(declaration, "111aAБ");

        stream.setPosition(0);
        Reader = new StreamReader(stream, Encoding.getASCII());
        declaration = Reader.readToEnd();
        msAssert.areEqual(declaration, "111aA+BBE-");
        Reader.close();
    }

    @Test
    public void test_ctor_stream_enc9() throws Exception
    {
        String fileName = getTestFileName(getClass());
        String strValue = "This is BigEndianUnicode\u00FF";
        FileStream fs2 = new FileStream(fileName, FileMode.CREATE);
        StreamWriter sw2 = new StreamWriter(fs2, Encoding.getBigEndianUnicode());
        sw2.write(strValue);
        sw2.close();

        fs2 = new FileStream(fileName, FileMode.OPEN);
        StreamReader sr2 = new StreamReader(fs2, Encoding.getBigEndianUnicode());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, strValue);
        sr2.close();


        MemoryStream ms2 = new MemoryStream();
        sw2 = new StreamWriter(ms2, Encoding.getBigEndianUnicode());
        sw2.write(strValue);
        sw2.flush();
        ms2.setPosition(0);
        sr2 = new StreamReader(ms2, Encoding.getBigEndianUnicode());
        str2 = sr2.readToEnd();
        msAssert.areEqual(str2, strValue);

        //check bom
        ms2.setPosition(0);
        byte[] preamble = Encoding.getBigEndianUnicode().getPreamble();
        byte[] bom = new byte[preamble.length];
        ms2.read(bom, 0, bom.length);
        msAssert.areEqual(preamble, bom);

        sr2.close();
    }

    @Test
    public void test_ctor_stream_enc10() throws Exception
    {
        String fileName = getTestFileName(getClass());
        String strValue = "This is Unicode\u00FF";
        FileStream fs2 = new FileStream(fileName, FileMode.CREATE);
        StreamWriter sw2 = new StreamWriter(fs2, Encoding.getUnicode());
        sw2.write(strValue);
        sw2.close();

        fs2 = new FileStream(fileName, FileMode.OPEN);
        StreamReader sr2 = new StreamReader(fs2, Encoding.getUnicode());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, strValue);
        sr2.close();

        MemoryStream ms2 = new MemoryStream();
        sw2 = new StreamWriter(ms2, Encoding.getUnicode());
        sw2.write(strValue);
        sw2.flush();
        ms2.setPosition(0);
        sr2 = new StreamReader(ms2, Encoding.getUnicode());
        str2 = sr2.readToEnd();
        msAssert.areEqual(str2, strValue);

        //check bom
        ms2.setPosition(0);
        byte[] preamble = Encoding.getUnicode().getPreamble();
        byte[] bom = new byte[preamble.length];
        ms2.read(bom, 0, bom.length);
        msAssert.areEqual(preamble, bom);

        sr2.close();
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void test_ctor_str_b_enc1() throws Exception
    {
        new StreamWriter(null, false, Encoding.getUTF8());
    }

    @Test
    public void test_ctor_str_b_enc2() throws Exception
    {
        String fileName = getTestFileName(getClass());
        StreamWriter sw2 = new StreamWriter(fileName, false, Encoding.getASCII());
        sw2.write("HelloThere\u00FF");
        sw2.close();
        StreamReader sr2 = new StreamReader(fileName, Encoding.getASCII());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "HelloThere?");
        sr2.close();

        sw2 = new StreamWriter(fileName, true, Encoding.getASCII());
        msAssert.areEqual(sw2.getBaseStream().getLength(), 11);
        sw2.close();
    }

    @Test
    public void test_ctor_str_b_enc3() throws Exception
    {
        String fileName = getTestFileName(getClass());
        StreamWriter sw2 = new StreamWriter(fileName, false, Encoding.getASCII());
        msAssert.areEqual(sw2.getBaseStream().getLength(), 0);
        sw2.close();
    }

    @Test
    public void test_ctor_str_b_enc4() throws Exception
    {
        String fileName = getTestFileName(getClass());
        StreamWriter sw2 = new StreamWriter(fileName, false, Encoding.getUTF8());
        sw2.write("This is UTF8\u00FF");
        sw2.close();
        StreamReader sr2 = new StreamReader(fileName, Encoding.getUTF8());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is UTF8\u00FF");
        sr2.close();
    }

    @Test
    public void test_ctor_str_b_enc5() throws Exception
    {
        String fileName = getTestFileName(getClass());
        StreamWriter sw2 = new StreamWriter(fileName, false, Encoding.getUTF7());
        sw2.write("This is UTF7\u00FF");
        sw2.close();

        StreamReader sr2 = new StreamReader(fileName, Encoding.getUTF7());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is UTF7\u00FF");
        sr2.close();
    }

    @Test
    public void test_ctor_str_b_enc6() throws Exception
    {
        String fileName = getTestFileName(getClass());
        StreamWriter sw2 = new StreamWriter(fileName, false, Encoding.getBigEndianUnicode());
        sw2.write("This is BigEndianUnicode\u00FF");
        sw2.close();
        StreamReader sr2 = new StreamReader(fileName, Encoding.getBigEndianUnicode());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is BigEndianUnicode\u00FF");
        sr2.close();
    }

    @Test
    public void test_ctor_str_b_enc7() throws Exception
    {
        String fileName = getTestFileName(getClass());
        StreamWriter sw2 = new StreamWriter(fileName, false, Encoding.getUnicode());
        sw2.write("This is Unicode\u00FF");
        sw2.close();
        StreamReader sr2 = new StreamReader(fileName, Encoding.getUnicode());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is Unicode\u00FF");
        sr2.close();
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void test_ctor_str_b_enc_i1() throws Exception
    {
        new StreamWriter(null, false, Encoding.getUTF8(), 1);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void test_ctor_str_b_enc_i2() throws Exception
    {
        new StreamWriter(getTestFileName(getClass()), false, null, 1);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void test_ctor_str_b_enc_i3() throws Exception
    {
        new StreamWriter(getTestFileName(getClass()), false, Encoding.getUTF8(), -2);
    }

    @Test
    public void test_ctor_str_b_enc_i4() throws Exception
    {
        String fileName = getTestFileName(getClass());
        StreamWriter sw2 = new StreamWriter(fileName, false, Encoding.getASCII(), 9);
        sw2.write("HelloThere\u00FF");
        sw2.close();

        StreamReader sr2 = new StreamReader(fileName, Encoding.getASCII());
        String str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "HelloThere?");
        sr2.close();

        sw2 = new StreamWriter(fileName, false, Encoding.getUTF8(), 8888);
        sw2.write("This is UTF8\u00FF");
        sw2.close();

        sr2 = new StreamReader(fileName, Encoding.getUTF8());
        str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is UTF8\u00FF");
        sr2.close();

        sw2 = new StreamWriter(fileName, false, Encoding.getUTF7(), 11);
        sw2.write("This is UTF7\u00FF");
        sw2.close();

        sr2 = new StreamReader(fileName, Encoding.getUTF7());
        str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is UTF7\u00FF");
        sr2.close();

        sw2 = new StreamWriter(fileName, false, Encoding.getBigEndianUnicode(), 20);
        sw2.write("This is BigEndianUnicode\u00FF");
        sw2.close();

        sr2 = new StreamReader(fileName, Encoding.getBigEndianUnicode());
        str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is BigEndianUnicode\u00FF");
        sr2.close();

        sw2 = new StreamWriter(fileName, false, Encoding.getUnicode(), 50000);
        sw2.write("This is Unicode\u00FF");
        sw2.close();

        sr2 = new StreamReader(fileName, Encoding.getUnicode());
        str2 = sr2.readToEnd();
        msAssert.areEqual(str2, "This is Unicode\u00FF");
        sr2.close();
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void test_ctor_str_b1() throws Exception
    {
        new StreamWriter(null, false);
    }

    @Test
    public void test_ctor_str_b2() throws Exception
    {
        String fileName = getTestFileName(getClass());
        StreamWriter sw2 = new StreamWriter(fileName, false);
        String strValue = "123456";
        sw2.write(strValue);
        sw2.flush();
        msAssert.areEqual(sw2.getBaseStream().getLength(), strValue.length());
        sw2.close();

        sw2 = new StreamWriter(fileName, false);
        strValue = "1234";
        sw2.write(strValue);
        sw2.flush();
        msAssert.areEqual(sw2.getBaseStream().getLength(), strValue.length());
        sw2.close();

        sw2 = new StreamWriter(fileName, true);
        strValue = "ABCDE";
        sw2.write(strValue);
        sw2.flush();
        msAssert.areEqual(sw2.getBaseStream().getLength(), (strValue.length() + "ABCD".length()));
        sw2.close();

        StreamReader sr2 = new StreamReader(fileName);
        String tmp;
        tmp = sr2.readToEnd();
        sr2.close();
        msAssert.areEqual(tmp, "1234ABCDE");
    }

    @Test (enabled = false)
    public static String getTestFileName(Class testClass) throws Exception
    {
        return getTestDir(testClass) + Path.DirectorySeparatorChar + testClass.getName() + "." +
               DateTime.getNow().getMillisecond() + "." + CurrentThread.getId() + ".tmp";
    }

    @Test (enabled = false)
    public static String getTestDir(Class testClass) throws Exception
    {
        // Use TestOut directory for creating test directories to avoid UnautorizedAccessException when run with NUnit3.
        return TestFxUtil.buildOutFileName(testClass.getName(), "", "");
    }
}

