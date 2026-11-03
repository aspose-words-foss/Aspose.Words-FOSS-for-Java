// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2008 by Konstantin Sidorenko
// 2016/06/24 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.ms.System.IO.StreamReader;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests adapted from Rotor from files:
/// \Rotor\sscli20\tests\bcl\system\io\streamWriter\:
/// co5563Write_ch.cs, co5564Write_charr_ii.cs, co5565Write_str.cs.
/// </summary>
@Test
public class TestStreamWriterWrite
{
    @Test
    public void testStreamWriterWriteChar1() throws Exception
    {
        MemoryStream ms = new MemoryStream();
        StreamWriter sw = new StreamWriter(ms);
        StreamReader sr;
        for (int i = 0; i < chArr.length; i++)
            sw.write(chArr[i]);
        sw.flush();
        ms.setPosition(0);
        sr = new StreamReader(ms);
        int tmp = 0;
        for (int i = 0; i < chArr.length; i++)
        {
            tmp = sr.read();
            msAssert.areEqual(tmp, chArr[i]);
        }
        sw.close();
        sr.close();
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testStreamWriterWriteChar2() throws Exception
    {
        MemoryStream ms = new MemoryStream();
        StreamWriter sw = new StreamWriter(ms);
        sw.write(null, 0, 0);
        sw.close();//never get
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testStreamWriterWriteChar3() throws Exception
    {
        MemoryStream ms = new MemoryStream();
        StreamWriter sw = new StreamWriter(ms);
        sw.write(chArr, -1, 0);
        sw.close();//never get
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testStreamWriterWriteChar4() throws Exception
    {
        MemoryStream ms = new MemoryStream();
        StreamWriter sw = new StreamWriter(ms);
        sw.write(chArr, 0, -1);
        sw.close();//never get
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testStreamWriterWriteChar5() throws Exception
    {
        MemoryStream ms = new MemoryStream();
        StreamWriter sw = new StreamWriter(ms);
        sw.write(chArr, 1, chArr.length);
        sw.close();//never get
    }

    @Test
    public void testStreamWriterWriteChar6() throws Exception
    {
        MemoryStream ms = new MemoryStream();
        StreamWriter sw = new StreamWriter(ms);
        StreamReader sr;
        sw.write(chArr, 0, chArr.length);
        sw.flush();
        ms.setPosition(0);
        sr = new StreamReader(ms);
        int tmp = 0;
        for (int i = 0; i < chArr.length; i++)
        {
            tmp = sr.read();
            msAssert.areEqual(tmp, chArr[i]);
        }
        sw.close();
        sr.close();
        ms.close();
    }

    @Test (enabled = false)
    public void testStreamWriterWriteChar7() throws Exception
    {
        MemoryStream ms = new MemoryStream();
        StreamWriter sw = new StreamWriter(ms);
        StreamReader sr;
        sw.write(chArr, 2, 5);
        sw.flush();
        ms.setPosition(0);
        sr = new StreamReader(ms);
        int tmp = 0;
        for (int i = 2; i < 7; i++)
        {
            tmp = sr.read();
            msAssert.areEqual(tmp, chArr[i]);
        }
        sw.close();
        sr.close();
        ms.close();
    }

    @Test
    public void testStreamWriterWriteStr1() throws Exception
    {
        MemoryStream ms = new MemoryStream();
        StreamWriter sw = new StreamWriter(ms);
        sw.write((String)null);
        sw.flush();
        msAssert.areEqual(ms.getLength(), 0);
        sw.close();
        ms.close();
    }

    @Test
    public void testStreamWriterWriteStr2() throws Exception
    {
        MemoryStream ms = new MemoryStream();
        StreamWriter sw = new StreamWriter(ms);
        String str = new String(chArr);
        sw.write(str);
        sw.flush();
        ms.setPosition(0);
        StreamReader sr = new StreamReader(ms);
        int tmp = 0;
        for (int i = 0; i < chArr.length; i++)
        {
            tmp = sr.read();
            msAssert.areEqual(tmp, chArr[i]);
        }
        sw.close();
        sr.close();
        ms.close();
    }

    private static char[] chArr = new char[]{
    Character.MIN_VALUE
    ,Character.MAX_VALUE
    ,'\t'
    ,' '
    ,'$'
    ,'@'
    ,'#'
    ,'\u0000'
    ,'\u000B'
    ,'\''
    ,'\u3190'
    ,'\uC3A0'
    ,'A'
    ,'5'
    ,'\uFE70'
    ,'-'
    ,';'
    ,'\u00E6'};

}

