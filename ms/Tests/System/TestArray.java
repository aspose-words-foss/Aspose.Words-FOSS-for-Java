// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2007 by Konstantin Sidorenko
// 21/12/2015 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.StringComparer;
import com.aspose.ms.System.msString;


@Test
public class TestArray
{
    //Array.IndexOf(int[], ...)

    @Test
    public void testIndexOfIntArray()
    {
        msAssert.areEqual(msArray.indexOf(mIntArray, 0, 0, 10), 0);
        msAssert.areEqual(msArray.indexOf(mIntArray, 0, 1, 9), 7);
        msAssert.areEqual(msArray.indexOf(mIntArray, 5, 0, 10), 5);
        msAssert.areEqual(msArray.indexOf(mIntArray, -1, 0, 10), 6);
        msAssert.areEqual(msArray.indexOf(mIntArray, Integer.MIN_VALUE, 0, 10), 8);
        msAssert.areEqual(msArray.indexOf(mIntArray, Integer.MAX_VALUE, 0, 10), 9);
        msAssert.areEqual(msArray.indexOf(mIntArray, 999, 0, 10), -1);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testIndexOfIntArrayNull()
    {
        int[] nullArray = null;
        msArray.indexOf(nullArray, 0, 0, 10);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testIndexOfIntArrayNegativeStartIndex()
    {
        msArray.indexOf(mIntArray, 0, -1, 10);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testIndexOfIntArrayTooBigStartIndex()
    {
        msArray.indexOf(mIntArray, 0, 10, 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testIndexOfIntArrayNegativeCount()
    {
        msArray.indexOf(mIntArray, 0, 0, -1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testIndexOfIntArrayTooBigCount()
    {
        msArray.indexOf(mIntArray, 0, 0, 11);
    }

    //Array.IndexOf(object[], ...)

    @Test
    public void testIndexOfobjectArray()
    {
        msAssert.areEqual(msArray.indexOf(mObjArray, 0, 0, 10), 0);
        msAssert.areEqual(msArray.indexOf(mObjArray, 0, 1, 9), 7);
        msAssert.areEqual(msArray.indexOf(mObjArray, 5, 0, 10), 5);
        msAssert.areEqual(msArray.indexOf(mObjArray, -1, 0, 10), 6);
        msAssert.areEqual(msArray.indexOf(mObjArray, null, 0, 10), 8);
        msAssert.areEqual(msArray.indexOf(mObjArray, Integer.MAX_VALUE, 0, 10), 9);
        msAssert.areEqual(msArray.indexOf(mObjArray, 999, 0, 10), -1);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testIndexOfobjectArrayNull()
    {
        Object[] nullArray = null;
        msArray.indexOf(nullArray, 0, 0, 10);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testIndexOfobjectArrayNegativeStartIndex()
    {
        msArray.indexOf(mObjArray, 0, -1, 10);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testIndexOfobjectArrayTooBigStartIndex()
    {
        msArray.indexOf(mObjArray, 0, 10, 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testIndexOfobjectArrayNegativeCount()
    {
        msArray.indexOf(mObjArray, 0, 0, -1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testIndexOfobjectArrayTooBigCount()
    {
        msArray.indexOf(mObjArray, 0, 0, 11);
    }

    //Array.LastIndexOf(int[], ...)

    @Test
    public void testLastIndexOfIntArray()
    {
        msAssert.areEqual(msArray.lastIndexOf(mIntArray, 0, 9, 10), 7);
        msAssert.areEqual(msArray.lastIndexOf(mIntArray, 0, 5, 6), 0);
        msAssert.areEqual(msArray.lastIndexOf(mIntArray, 5, 9, 10), 5);
        msAssert.areEqual(msArray.lastIndexOf(mIntArray, -1, 9, 10), 6);
        msAssert.areEqual(msArray.lastIndexOf(mIntArray, Integer.MIN_VALUE, 9, 10), 8);
        msAssert.areEqual(msArray.lastIndexOf(mIntArray, Integer.MAX_VALUE, 9, 10), 9);
        msAssert.areEqual(msArray.lastIndexOf(mIntArray, 999, 9, 10), -1);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testLastIndexOfIntArrayNull()
    {
        int[] nullArray = null;
        msArray.lastIndexOf(nullArray, 0, 9, 10);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testLastIndexOfIntArrayNegativeStartIndex()
    {
        msArray.lastIndexOf(mIntArray, 0, -1, 10);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testLastIndexOfIntArrayTooBigStartIndex()
    {
        msArray.lastIndexOf(mIntArray, 0, 10, 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testLastIndexOfIntArrayNegativeCount()
    {
        msArray.lastIndexOf(mIntArray, 0, 2, -1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testLastIndexOfIntArrayTooBigCount()
    {
        msArray.lastIndexOf(mIntArray, 0, 9, 11);
    }

    //Array.Reverse(int[], ...)

    @Test
    public void testReverceIntArray()
    {
        int[] firstArray = { 0, 1, 2, 3, 4, 5 };
        int[] reversedArray = { 5, 4, 3, 2, 1, 0 };
        int[] reversedArray2 = { 5, 1, 2, 3, 4, 0 };
        int[] reversedArray3 = { 5, 1, 2, 3, 0, 4 };

        msArray.reverse(firstArray, 0, firstArray.length);
        msAssert.isTrue(ArrayUtil.isArrayEqual(reversedArray, firstArray));

        msArray.reverse(firstArray, 1, 4);
        msAssert.isTrue(ArrayUtil.isArrayEqual(reversedArray2, firstArray));

        msArray.reverse(firstArray, 4, 2);
        msAssert.isTrue(ArrayUtil.isArrayEqual(reversedArray3, firstArray));
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testReverceIntArrayNull()
    {
        int[] nullArray = null;
        msArray.reverse(nullArray, 0, 10);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testReverceIntArrayNegativeStartIndex()
    {
        msArray.reverse(mIntArray2, -1, 9);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testReverceIntArrayTooBigStartIndex()
    {
        msArray.reverse(mIntArray2, 10, 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testReverceIntArrayNegativeCount()
    {
        msArray.reverse(mIntArray2, 2, -1);
    }

    @Test
    public void testArrayUtilContains()
    {
        byte[] src = { (byte) 0x1, (byte) 0x2, (byte) 0x3, (byte) 0x4, (byte) 0x5 };
        byte[] value1 = { (byte) 0x2, (byte) 0x3 };
        byte[] value2 = { (byte) 0x1 };
        byte[] value3 = { (byte) 0x5 };
        byte[] vrong = { (byte) 0x6, (byte) 0x7 };

        msAssert.isTrue(ArrayUtil.contains(src, value1));
        msAssert.isTrue(ArrayUtil.contains(src, value2));
        msAssert.isTrue(ArrayUtil.contains(src, value3));
        msAssert.isTrue(ArrayUtil.contains(src, src));
        msAssert.isTrue(ArrayUtil.contains(value1, value1));

        msAssert.isFalse(ArrayUtil.contains(value1, src));
        msAssert.isFalse(ArrayUtil.contains(src, vrong));
    }

    @Test
    // TODO: strange test...
    public void testArrayUtilToString()
    {
        int count = 160;
        byte[] src = new byte[count];
        for (int i = 0; i < count; i++)
            src[i] = (byte)i;

        String aStr = ArrayUtil.arrayToStringCombined(src, 0, count);
        Debug.write("aStr = " + aStr);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testReverceIntArrayTooBigCount()
    {
        msArray.reverse(mIntArray2, 0, 11);
    }

    /// <summary>
    /// Check that Java's msArray sorts strings using .Net's "string-sort" algorithm.
    /// </summary>
    @Test
    public void testJiraJ1734()
    {
        String[] list = new String[]
        {
            "a",
            "TOC",
            "A",
            "_Toc256000000"
        };

        msArray.sort(list);

        msAssert.areEqual(list[0], "_Toc256000000");
        msAssert.areEqual(list[1], "a");
        msAssert.areEqual(list[2], "A");
        msAssert.areEqual(list[3], "TOC");
    }

    @Test
    public void testStringArraySort()
    {
        String[] data1 = getData();
        msArray.sort(data1, StringComparer.getOrdinal());
        msAssert.areEqual(msString.join(",", data1), "2016,PersonName,Visual Studio,Windows,able,apple,la Secretaria,product,Æble,ångström,Привет,嗨");

        String[] data2 = getData();
        msArray.sort(data2, StringComparer.getOrdinalIgnoreCase());
        msAssert.areEqual(msString.join(",", data2), "2016,able,apple,la Secretaria,PersonName,product,Visual Studio,Windows,ångström,Æble,Привет,嗨");

        String[] data3 = getData();
        msArray.sort(data3, StringComparer.getCurrentCulture());
        msAssert.areEqual(msString.join(",", data3), "2016,able,Æble,ångström,apple,la Secretaria,PersonName,product,Visual Studio,Windows,Привет,嗨");

        String[] data4 = getData();
        msArray.sort(data4, StringComparer.getCurrentCultureIgnoreCase());
        msAssert.areEqual(msString.join(",", data4), "2016,able,Æble,ångström,apple,la Secretaria,PersonName,product,Visual Studio,Windows,Привет,嗨");

        String[] data5 = getData();
        msArray.sort(data5, StringComparer.getInvariantCulture());
        msAssert.areEqual(msString.join(",", data5), "2016,able,Æble,ångström,apple,la Secretaria,PersonName,product,Visual Studio,Windows,Привет,嗨");

        String[] data6 = getData();
        msArray.sort(data6, StringComparer.getInvariantCultureIgnoreCase());
        msAssert.areEqual(msString.join(",", data6), "2016,able,Æble,ångström,apple,la Secretaria,PersonName,product,Visual Studio,Windows,Привет,嗨");
    }

    @Test
    public void testArrayUtilConcat()
    {
        int[] result = ArrayUtil.concat(mIntArray, mIntArray2);
        msAssert.areEqual(result.length, mIntArray.length + mIntArray2.length);
        msAssert.areEqual(result[5], mIntArray[5]);
        msAssert.areEqual(result[mIntArray.length + 5], mIntArray2[5]);
    }

    @Test
    public void testArrayUtilConcatNull()
    {
        int[] resultInt = ArrayUtil.concat(mIntArray, null);
        msAssert.areEqual(resultInt, mIntArray);
        int length = ArrayUtil.concatLength(null, mObjArray);
        Object[] resultObj = ArrayUtil.concat(null, mObjArray, new Object[length]);
        msAssert.areEqual(resultObj, mObjArray);

        Object[] resultNull = ArrayUtil.<Object>concat(null, null, null);
        msAssert.isNull(resultNull);
    }

    @Test
    public void testArrayUtilConcatEmpty()
    {
        int[] result = ArrayUtil.concat(mIntArray, new int[0]);
        msAssert.areEqual(result.length, mIntArray.length);
        msAssert.isTrue(ArrayUtil.isArrayEqual(result, mIntArray));
    }

    private static String[] getData()
    {
        return new String[]
        {
            "PersonName",
            "嗨",
            "2016",
            "Привет",
            "la Secretaria",
            "product",
            "able",
            "ångström",
            "apple",
            "Æble",
            "Windows",
            "Visual Studio"
        };
    }

    private int[] mIntArray = new int[] { 0, 1, 2, 3, 4, 5, -1, 0, Integer.MIN_VALUE, Integer.MAX_VALUE };//10 elements
    private int[] mIntArray2 = { 0, 1, 2, 3, 4, 5, -1, 0, Integer.MIN_VALUE, Integer.MAX_VALUE };//10 elements
    private Object[] mObjArray = { 0, 1, 2, 3, 4, 5, -1, 0, null, Integer.MAX_VALUE };//10 elements
}

