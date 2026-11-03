// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/01/2007 by Konstantin Sidorenko
// 2015/12/31 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Guid;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Random;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msByte;
import com.aspose.ms.System.msUInt;
import com.aspose.ms.System.msUShort;
import java.lang.NumberFormatException;


@Test
public class TestGuid
{
    @Test
    public void testNewGuid()
    {
        Guid guid;
        Guid guid1;
        Guid guid2;

        guid1 = Guid.newGuid();
        guid2 = Guid.newGuid();
        msAssert.isFalse(guid1.equals(guid2));

        ArrayList<Guid> table = new ArrayList<Guid>();
        for (int i = 0; i < 1000; i++)
        {
            guid = Guid.newGuid();
            msArrayList.add(table, guid);
        }

        for (int i = 0; i < 100; i++)
        {
            guid1 = Guid.newGuid();
            guid2 = new Guid(guid1.toByteArray());
            msAssert.isTrue(guid1.equals(guid2));
        }
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testCtorByteArr1()
    {
        byte[] bytes = null;
        Guid guid = new Guid(bytes);
        msAssert.isNotNull(guid); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testCtorByteArr2()
    {
        byte[] bytes = new byte[0];
        Guid guid = new Guid(bytes);
        msAssert.isNotNull(guid); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testCtorByteArr3()
    {
        byte[] bytes = new byte[177];
        Guid guid = new Guid(bytes);
        msAssert.isNotNull(guid); // Just a stub to prevent an unused variable hint.
    }

    @Test
    public void testCtorByteArr4()
    {
        byte[] bytes = new byte[16];
        Guid guid = new Guid(bytes);
        msAssert.areEqual(guid.toString(), "00000000-0000-0000-0000-000000000000");
    }

    @Test
    public void testCtorByteArr5()
    {
        byte[] bytes = new byte[16];
        Random random = new Random(DateTime.getNow().getMillisecond());
        random.nextBytes(bytes);

        Guid guid = new Guid(bytes);
        byte[] bytResultArr = guid.toByteArray();

        msAssert.areEqual(bytes, bytResultArr);
    }

    @Test
    public void testCtorArr1()
    {
        int int1 = 1;
        short short2 = 2;
        short short3 = 3;

        byte[] bytes = new byte[8];
        bytes[0] = (byte)0;
        bytes[1] = (byte)1;
        bytes[2] = (byte)2;
        bytes[3] = (byte)3;
        bytes[4] = (byte)4;
        bytes[5] = (byte)5;
        bytes[6] = (byte)6;
        bytes[7] = (byte)7;

        Guid guid = new Guid(int1, short2, short3, bytes);
        String str = guid.toString();
        msAssert.areEqual(str, "00000001-0002-0003-0001-020304050607");

        int1 = -1;
        short2 = -2;
        short3 = -3;
        bytes[0] = (byte)-0;
        bytes[1] = (byte)255;
        bytes[2] = (byte)254;
        bytes[3] = (byte)253;
        bytes[4] = (byte)252;
        bytes[5] = (byte)251;
        bytes[6] = (byte)250;
        bytes[7] = (byte)249;
        guid = new Guid(int1, short2, short3, bytes);
        str = guid.toString();
        msAssert.areEqual(str, "ffffffff-fffe-fffd-00ff-fefdfcfbfaf9");
    }

    @Test
    public void testCtorDegaultDupl()
    {
        final String STR_GUID_2 = "123456Ab-1629-11d2-8879-00c04fb990b0";

        Guid guid2 = Guid.newGuid();
        Guid guid3 = new Guid(guid2.toString());
        msAssert.areEqual(guid2, guid3);
        msAssert.areEqual(guid2.hashCode(), guid3.hashCode());

        guid2 = new Guid(STR_GUID_2);
        guid3 = new Guid(STR_GUID_2);
        msAssert.areEqual(guid2, guid3);
        msAssert.isTrue(StringUtil.equalsIgnoreCase(STR_GUID_2, guid2.toString()));
    }

    @Test
    public void testCtorISSUUU()
    {
        Random rand = new Random(new DateTime().getMillisecond());

        //the "main" ctor: (int, short, short, unsigned byte, unsigned byte, ...)
        Guid guid = new Guid(10, (short)11, (short)12, (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7);
        msAssert.areEqual(guid.toString(), "0000000a-000b-000c-0001-020304050607");
        //convinient ctor without castings.

        guid = new Guid(10, 11, 12, (int)0, (int)1, (int)2, (int)3, (int)4, (int)5, (int)6, (int)7);
        msAssert.areEqual(guid.toString(), "0000000a-000b-000c-0001-020304050607");

        guid = new Guid(0, 0, 0, (int)0, (int)0, (int)0, (int)0, (int)0, (int)0, (int)0, (int)0);
        msAssert.areEqual(guid.toString(), "00000000-0000-0000-0000-000000000000");

        int a;
        short b, c;
        byte d, e, f, g, h, i, j, k;
        for (int ii = 0; ii < 8000; ii++)
        {
            a = rand.next(Integer.MAX_VALUE);
            b = (short)rand.next(Integer.MAX_VALUE);
            c = (short)rand.next(Integer.MAX_VALUE);
            d = (byte)rand.next((msByte.MAX_VALUE & 0xFF));
            e = (byte)rand.next((msByte.MAX_VALUE & 0xFF));
            f = (byte)rand.next((msByte.MAX_VALUE & 0xFF));
            g = (byte)rand.next((msByte.MAX_VALUE & 0xFF));
            h = (byte)rand.next((msByte.MAX_VALUE & 0xFF));
            i = (byte)rand.next((msByte.MAX_VALUE & 0xFF));
            j = (byte)rand.next((msByte.MAX_VALUE & 0xFF));
            k = (byte)rand.next((msByte.MAX_VALUE & 0xFF));
            guid = new Guid(a, b, c, d, e, f, g, h, i, j, k);
        }
    }

    @Test
    public void testCtorUInt32UInt16Etc()
    {
        Random rand = new Random(new DateTime().getMillisecond());
        Guid gTest;

        gTest = new Guid((/*uint*/int)0xa, (/*ushort*/short)0xb, (/*ushort*/short)0xc, (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7);
        msAssert.areEqual(gTest.toString(), "0000000a-000b-000c-0001-020304050607");

        gTest = new Guid((/*uint*/int)0, (/*ushort*/short)0, (/*ushort*/short)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0);
        msAssert.areEqual(gTest.toString(), "00000000-0000-0000-0000-000000000000");

        byte byteMaxValue = (byte) 0xff;// hardcoded byte.MaxValue for java.
        gTest = new Guid(msUInt.MAX_VALUE, msUShort.MAX_VALUE, msUShort.MAX_VALUE, byteMaxValue, byteMaxValue, byteMaxValue, byteMaxValue, byteMaxValue, byteMaxValue, byteMaxValue, byteMaxValue);
        msAssert.areEqual(gTest.toString(), "ffffffff-ffff-ffff-ffff-ffffffffffff");

        long a;
        int b, c;
        short d, e, f, g, h, i, j, k;
        for (int count = 0; count < 1000; count++)
        {
            a = ((long)(rand.next(Integer.MAX_VALUE)) * 2);
            b = (int)rand.next(Integer.MAX_VALUE);
            c = (int)rand.next(Integer.MAX_VALUE);
            d = (short)rand.next((byteMaxValue & 0xFF));
            e = (short)rand.next((byteMaxValue & 0xFF));
            f = (short)rand.next((byteMaxValue & 0xFF));
            g = (short)rand.next((byteMaxValue & 0xFF));
            h = (short)rand.next((byteMaxValue & 0xFF));
            i = (short)rand.next((byteMaxValue & 0xFF));
            j = (short)rand.next((byteMaxValue & 0xFF));
            k = (short)rand.next((byteMaxValue & 0xFF));
            gTest = new Guid((/*uint*/int)a, (/*ushort*/short)b, (/*ushort*/short)c, (byte)d, (byte)e, (byte)f, (byte)g, (byte)h, (byte)i, (byte)j, (byte)k);
            String expected = FormatterPal.int64ToStrX8(a) + "-" +
                              FormatterPal.intToStrX8(b).substring(4) + "-" +
                              FormatterPal.intToStrX8(c).substring(4) + "-" +
                              FormatterPal.intToStrX8(d).substring(6) +
                              FormatterPal.intToStrX8(e).substring(6) + "-" +
                              FormatterPal.intToStrX8(f).substring(6) +
                              FormatterPal.intToStrX8(g).substring(6) +
                              FormatterPal.intToStrX8(h).substring(6) +
                              FormatterPal.intToStrX8(i).substring(6) +
                              FormatterPal.intToStrX8(j).substring(6) +
                              FormatterPal.intToStrX8(k).substring(6);
            msAssert.areEqual(gTest.toString(), expected.toLowerCase());
        }
    }

    @Test
    public void testCtorString()
    {
        Guid guid2 = Guid.newGuid();
        Guid guid3 = new Guid(guid2.toString());
        msAssert.areEqual(guid2, guid3);
        msAssert.areEqual(guid2.hashCode(), guid3.hashCode());

        final String STR_GUID_2 = "123456Ab-1629-11d2-8879-00c04fb990b0";
        guid2 = new Guid(STR_GUID_2);
        guid3 = new Guid(STR_GUID_2);
        msAssert.areEqual(guid2, guid3);
        msAssert.areEqual(guid2.hashCode(), guid3.hashCode());
        msAssert.areEqual(guid2.toString(), STR_GUID_2.toLowerCase());
        msAssert.areEqual(guid2.hashCode(), 0x41D47C9);
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString2()
    {
        Guid guid2 = new Guid("123456Ab 1629-11d2-8879-00c04fb990b0");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString3()
    {
        Guid guid2 = new Guid("123456A-1629-11d2-8879-00c04fb990b0");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString4()
    {
        Guid guid2 = new Guid("123456Az-1629-11d2-8879-00c04fb990b0");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString5()
    {
        Guid guid2 = new Guid("123456123445");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString6()
    {
        Guid guid2 = new Guid("{00000000-0000-0000-0000-000000000000");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString7()
    {
        Guid guid2 = new Guid("{00000000-0000-0000-0000-000000000000}0");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString8()
    {
        Guid guid2 = new Guid("00000000-0000-0000-0000-0000000000000");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString9()
    {
        Guid guid2 = new Guid("0000000-00000-0000-0000-000000000000");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString10()
    {
        Guid guid2 = new Guid("00000000-00000-000-0000-000000000000");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString11()
    {
        Guid guid2 = new Guid("00000000-0000-00000-000-000000000000");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString12()
    {
        Guid guid2 = new Guid("00000000-0000-0000-0000-00L000000000");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    //JAVA: other string formats do not supported yet:
    @Test (expectedExceptions = NumberFormatException.class)
    public void testCtorString13()
    {
        Guid guid2 = new Guid("{0x11111111,0x0000,0x0000,{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x11}}");
        msAssert.isNotNull(guid2); // Just a stub to prevent an unused variable hint.
    }

    @Test
    // TODO: What does this test do?
    public void testCtorStringStat()
    {
        Guid guid2;
        Guid guid3;

        for (int i = 0; i < 100; i++)
        {
            guid2 = Guid.newGuid();
            guid3 = new Guid(guid2.toString());
        }
    }

    @Test
    public void testToString1()
    {
        Guid guid = new Guid("a4434021-80db-4b65-aadd-f138e53e0e07");
        String[] formats = { "d", "D", "n", "N", "p", "P", "b", "B" };

        for (String format : formats)
        {
            String value = guid.toString(format);
            switch (format.charAt(0))
            {
                case 'B':
                    msAssert.areEqual(value, "{a4434021-80db-4b65-aadd-f138e53e0e07}");
                    break;
                case 'b':
                    msAssert.areEqual(value, "{a4434021-80db-4b65-aadd-f138e53e0e07}");
                    break;
                case 'D':
                    msAssert.areEqual(value, "a4434021-80db-4b65-aadd-f138e53e0e07");
                    break;
                case 'd':
                    msAssert.areEqual(value, "a4434021-80db-4b65-aadd-f138e53e0e07");
                    break;
                case 'N':
                    msAssert.areEqual(value, "a443402180db4b65aaddf138e53e0e07");
                    break;
                case 'n':
                    msAssert.areEqual(value, "a443402180db4b65aaddf138e53e0e07");
                    break;
                case 'P':
                    msAssert.areEqual(value, "(a4434021-80db-4b65-aadd-f138e53e0e07)");
                    break;
                case 'p':
                    msAssert.areEqual(value, "(a4434021-80db-4b65-aadd-f138e53e0e07)");
                    break;
                default:
                    break;
            }
        }
    }

    @Test
    public void testToString2()
    {
        Guid emptyGuid = Guid.Empty;
        String[] formats = { "d", "D", "n", "N", "p", "P", "b", "B" };

        for (String format : formats)
        {
            String guidStr = emptyGuid.toString(format);
            switch (format.charAt(0))
            {
                case 'B':
                    msAssert.areEqual(guidStr, "{00000000-0000-0000-0000-000000000000}");
                    break;
                case 'b':
                    msAssert.areEqual(guidStr, "{00000000-0000-0000-0000-000000000000}");
                    break;
                case 'D':
                    msAssert.areEqual(guidStr, "00000000-0000-0000-0000-000000000000");
                    break;
                case 'd':
                    msAssert.areEqual(guidStr, "00000000-0000-0000-0000-000000000000");
                    break;
                case 'N':
                    msAssert.areEqual(guidStr, "00000000000000000000000000000000");
                    break;
                case 'n':
                    msAssert.areEqual(guidStr, "00000000000000000000000000000000");
                    break;
                case 'P':
                    msAssert.areEqual(guidStr, "(00000000-0000-0000-0000-000000000000)");
                    break;
                case 'p':
                    msAssert.areEqual(guidStr, "(00000000-0000-0000-0000-000000000000)");
                    break;
                default:
                    break;
            }
        }
    }

    @Test
    public void testToString3()
    {
        Guid guid1 = Guid.Empty;
        String retValue;
        String[] formats = { "d", "D", "n", "N", "p", "P", "b", "B" };

        for (String format : formats)
        {
            retValue = guid1.toString(format);
            switch (format.charAt(0))
            {
                case 'B':
                    msAssert.areEqual(retValue, "{00000000-0000-0000-0000-000000000000}");
                    break;
                case 'b':
                    msAssert.areEqual(retValue, "{00000000-0000-0000-0000-000000000000}");
                    break;
                case 'D':
                    msAssert.areEqual(retValue, "00000000-0000-0000-0000-000000000000");
                    break;
                case 'd':
                    msAssert.areEqual(retValue, "00000000-0000-0000-0000-000000000000");
                    break;
                case 'N':
                    msAssert.areEqual(retValue, "00000000000000000000000000000000");
                    break;
                case 'n':
                    msAssert.areEqual(retValue, "00000000000000000000000000000000");
                    break;
                case 'P':
                    msAssert.areEqual(retValue, "(00000000-0000-0000-0000-000000000000)");
                    break;
                case 'p':
                    msAssert.areEqual(retValue, "(00000000-0000-0000-0000-000000000000)");
                    break;
                default:
                    break;
            }
        }
    }

    @Test
    public void testToString4()
    {
        Guid guid1 = Guid.newGuid();
        String[] exceptionFormats = { "g", "G", "c", "C", "e", "E", "#", "0", "####.####", "000.000", "%###", "##E+0", "{{" };

        for (String exp : exceptionFormats)
        {
            try
            {
                String retValue = guid1.toString(exp);
                Assert.fail("exp= " + exp + "\r\nretval= " + retValue);
            }
            catch (NumberFormatException e)
            {
                //catched
            }

        }
    }

    @Test
    public void testCompareTo2()
    {
        byte[] bytes = new byte[16];
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = (byte)i;

        Guid guid1 = new Guid(bytes);
        Guid guid2 = new Guid(bytes);
        msAssert.areEqual(guid1.compareTo(guid2), 0);
        msAssert.areEqual(guid2.compareTo(guid1), 0);
        msAssert.areEqual(guid1.compareTo(guid1), 0);

        bytes = new byte[16];
        for (int i = 0, j = 16; i < bytes.length; i++, j--)
            bytes[i] = (byte)j;

        guid2 = new Guid(bytes);
        msAssert.isTrue(guid1.compareTo(guid2) < 0);
        msAssert.isTrue(guid2.compareTo(guid1) > 0);
        msAssert.isTrue(guid2.compareTo(null) > 0);
    }
}

