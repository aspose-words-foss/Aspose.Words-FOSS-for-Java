// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2017 by Tengiz Sharafiev

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests CaseInsensitiveHashSet class' functionality.
/// </summary>
/// <remarks>
/// Every individual test of this class corresponds to a ByteList member.
/// See the corresponding CaseInsensitiveHashSet members' descriptions for any additional information.
/// </remarks>
@Test
public class TestCaseInsensitiveHashSet
{
    @Test
    public void testCtor()
    {
        CaseInsensitiveHashSet hashSet = new CaseInsensitiveHashSet();
        msAssert.areEqual(0, hashSet.getCount());
    }

    @Test
    public void testAdd()
    {
        CaseInsensitiveHashSet hashSet = new CaseInsensitiveHashSet();
        msAssert.isTrue(hashSet.add("i"));
        msAssert.isTrue(hashSet.add("b"));
        msAssert.isFalse(hashSet.add("i"));
        msAssert.isFalse(hashSet.add("I"));
        msAssert.isFalse(hashSet.add("B"));
    }

    @Test
    public void testClear()
    {
        CaseInsensitiveHashSet hashSet = new CaseInsensitiveHashSet();
        hashSet.add("a");
        hashSet.add("b");
        msAssert.areEqual(2, hashSet.getCount());
        hashSet.clear();
        msAssert.areEqual(0, hashSet.getCount());
    }

    @Test
    public void testContains()
    {
        CaseInsensitiveHashSet hashSet = new CaseInsensitiveHashSet();
        hashSet.add("a");
        msAssert.isFalse(hashSet.contains("1"));
        msAssert.isTrue(hashSet.contains("a"));
        msAssert.isTrue(hashSet.contains("A"));
    }

    @Test
    public void testRemove()
    {
        CaseInsensitiveHashSet hashSet = new CaseInsensitiveHashSet();
        hashSet.add("a");
        hashSet.add("b");
        hashSet.add("c");
        msAssert.areEqual(3, hashSet.getCount());
        msAssert.isFalse(hashSet.remove("1"));
        msAssert.isTrue(hashSet.remove("a"));
        msAssert.isTrue(hashSet.remove("B"));
        msAssert.areEqual(1, hashSet.getCount());
    }
}

