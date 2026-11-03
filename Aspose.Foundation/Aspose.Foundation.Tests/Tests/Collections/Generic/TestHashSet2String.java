// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2017 by Tengiz Sharafiev

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Tests HashSetGeneric class' functionality.
/// </summary>
/// <remarks>
/// Every individual test of this class corresponds to a ByteList member.
/// See the corresponding HashSetGeneric members' descriptions for any additional information.
/// </remarks>
@Test
public class TestHashSet2String
{
    @Test
    public void testDefaultCtor()
    {
        HashSetGeneric<String> hashSet = new HashSetGeneric<String>();
        msAssert.areEqual(0, hashSet.getCount());
    }

    @Test
    public void testIEnumerableCtor()
    {
        ArrayList<String> list = new ArrayList<String>();
        msArrayList.add(list, "1");
        msArrayList.add(list, "2");
        msArrayList.add(list, "3");
        HashSetGeneric<String> hashSet = new HashSetGeneric<String>(list);
        msAssert.areEqual(3, hashSet.getCount());
    }

    @Test
    public void testAdd()
    {
        HashSetGeneric<String> hashSet = new HashSetGeneric<String>();
        msAssert.isTrue(hashSet.add("1"));
        msAssert.isFalse(hashSet.add("1"));
        msAssert.isTrue(hashSet.add("2"));
        msAssert.areEqual(2, hashSet.getCount());
    }

    @Test
    public void testClear()
    {
        HashSetGeneric<String> hashSet = new HashSetGeneric<String>();
        hashSet.add("1");
        msAssert.areEqual(1, hashSet.getCount());
        hashSet.clear();
        msAssert.areEqual(0, hashSet.getCount());
    }

    @Test
    public void testContains()
    {
        ArrayList<String> list = new ArrayList<String>();
        msArrayList.add(list, "1");
        msArrayList.add(list, "2");
        msArrayList.add(list, "3");
        HashSetGeneric<String> hashSet = new HashSetGeneric<String>(list);
        msAssert.isTrue(hashSet.contains("1"));
        msAssert.isFalse(hashSet.contains("4"));
    }

    @Test
    public void testRemove()
    {
        ArrayList<String> list = new ArrayList<String>();
        msArrayList.add(list, "1");
        msArrayList.add(list, "2");
        msArrayList.add(list, "3");
        HashSetGeneric<String> hashSet = new HashSetGeneric<String>(list);
        msAssert.areEqual(3, hashSet.getCount());
        msAssert.isFalse(hashSet.remove("4"));
        msAssert.isTrue(hashSet.remove("1"));
        msAssert.areEqual(2, hashSet.getCount());
    }
}

