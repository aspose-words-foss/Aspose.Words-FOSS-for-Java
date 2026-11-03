// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2010 by Konstantin Sidorenko
// 12/01/2016 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;


@Test
public class TestMs
{
    @Test
    public void testAs()
    {
        Object obj = "aaa";
        String s;

        s = ms.as(obj, String.class);
        msAssert.areEqual(s, obj);

        s = ms.as("str", String.class);
        msAssert.areEqual(s, "str");

        obj = new ArrayList();
        s = ms.as(obj, String.class);
        msAssert.isNull(s);

        // Java's ms.as() can do 'int as String' and similar without syntax error (returns null usually).
        // .Net can't.
    }

    @Test
    public void testAsWithArrays()
    {
        Object obj = new int[10];
        int[] iArray;

        iArray = ms.as(obj, int[].class);
        msAssert.areEqual(iArray, obj);

        int[] array = { 1, 2, 3 };
        iArray = ms.as(array, int[].class);
        msAssert.areEqual(iArray, array);

        obj = array;
        iArray = ms.as(obj, int[].class);
        msAssert.areEqual(iArray, array);

        obj = new Object[10];
        iArray = ms.as(obj, int[].class);
        msAssert.isNull(iArray);
    }

    @Test
    public void testSizeOfValueTypes()
    {
        // 1 byte
        msAssert.areEqual(1, ms.sizeofByte());
        msAssert.areEqual(1, ms.sizeofSByte());

        // 2 bytes
        msAssert.areEqual(2, ms.sizeofChar());
        msAssert.areEqual(2, ms.sizeofInt16());
        msAssert.areEqual(2, ms.sizeofUInt16());

        // 4 bytes
        msAssert.areEqual(4, ms.sizeofInt32());
        msAssert.areEqual(4, ms.sizeofUInt32());
        msAssert.areEqual(4, ms.sizeofSingle());

        // 8 bytes
        msAssert.areEqual(8, ms.sizeofInt64());
        msAssert.areEqual(8, ms.sizeofUInt64());
        msAssert.areEqual(8, ms.sizeofDouble());
    }
}

