// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2009 by Konstantin Sidorenko
// 2016/07/21 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Collections.SortedList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.lang.Exception;


@Test
// In .Net generic SortedList do not support getByIndex(int), getKey(int) and clone()
public class TestSortedListGenerics
{
    @Test
    public void testCtor()
    {

        SortedList<String, String> list = new SortedList<String, String>();
        msAssert.isNotNull(list);
        msAssert.areEqual(list.getCount(), 0);

        list.add("cde", null);
        list.add("abc", null);
        list.add("bcd", null);

        msAssert.areEqual(list.getCount(), 3);
        msAssert.areEqual(list.indexOfKey("cde"), 2);
        msAssert.areEqual(list.indexOfKey("abc"), 0);
        msAssert.areEqual(list.indexOfKey("bcd"), 1);
    }

    @Test
    public void testCtor2()
    {
        SortedList<String, String> list = new SortedList<String, String>(100);
        msAssert.isNotNull(list);
        msAssert.areEqual(list.getCount(), 0);

        list = new SortedList<String, String>(0);
        msAssert.isNotNull(list);
        msAssert.areEqual(list.getCount(), 0);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testCtorWrongCapacity()
    {
        SortedList<String, String> list = new SortedList<String, String>(-1);
        msAssert.isNotNull(list); // Just to prevent unused variable hint.
    }

    @Test
    public void testClear()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        msAssert.areEqual(list.getCount(), 0);

        list.clear();
        msAssert.areEqual(list.getCount(), 0);

        list = getPopulatedList();
        msAssert.areEqual(list.getCount(), 100);

        list.clear();
        msAssert.areEqual(list.getCount(), 0);
    }

    @Test
    public void testContainsKey()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        boolean contains = list.containsKey("No_Such_Key");
        msAssert.areEqual(contains, false);

        String s1;
        list = getPopulatedList();
        for (int i = 0; i < list.getCount(); i++)
        {
            s1 = "key_" + i;
            contains = list.containsKey(s1);
            msAssert.areEqual(contains, true);
        }

        s1 = "key_50";
        list.remove(s1);
        contains = list.containsKey(s1);
        msAssert.areEqual(contains, false);
    }

    @Test
    public void testContainsValue()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        boolean contains = list.containsValue("No_Such_Value");
        msAssert.areEqual(contains, false);

        String s1;
        list = getPopulatedList();
        for (int i = 0; i < list.getCount(); i++)
        {
            s1 = "val_" + i;
            contains = list.containsValue(s1);
            msAssert.areEqual(contains, true);
        }

        s1 = "key_50";
        String s2 = "val_50";
        list.remove(s1);
        contains = list.containsValue(s2);
        msAssert.areEqual(contains, false);
    }

    @Test
    public void testGet()
    {
        String s1;
        SortedList<String, String> list = new SortedList<String, String>();
        try
        {
            s1 = list.get("No_Such_Key");
            msAssert.isNull(s1);
        }
        catch (Exception e)
        {
            // In .Net generic SortedList throws KeyNotFoundException.
            // Java throws nothing, just returns null.
        }


        list = getPopulatedList();
        for (int i = 0; i < list.getCount(); i++)
        {
            s1 = "key_" + i;
            String s2 = "val_" + i;
            String s3 = (String)list.get(s1);
            msAssert.areEqual(s2, s3);
        }

        s1 = "key_50";
        list.remove(s1);
        boolean contains = list.containsKey(s1);
        msAssert.areEqual(contains, false);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testIndexOfNullKey()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        list.indexOfKey(null);
    }

    @Test
    public void testIndexOfKey()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        int j = list.indexOfKey("No_Such_Key");
        msAssert.areEqual(j, -1);

        String s1;
        list = getPopulatedList();
        for (int i = 0; i < list.getCount(); i++)
        {
            s1 = "key_" + i;
            String s2 = "val_" + i;

            msAssert.areEqual(list.indexOfKey(s1), list.indexOfValue(s2));
        }

        s1 = "key_50";
        list.remove(s1);
        j = list.indexOfKey(s1);
        msAssert.areEqual(j, -1);
    }

    @Test
    public void testIndexOfValue()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        int j = list.indexOfValue(null);
        msAssert.areEqual(j, -1);
        j = list.indexOfValue("No_Such_Value");
        msAssert.areEqual(j, -1);

        list.add("Key_0", null);
        j = list.indexOfValue(null);
        msAssert.areEqual(j, 0);

        list.add("Key_1", "Val_Same");
        list.add("Key_2", "Val_Same");
        j = list.indexOfValue("Val_Same");
        msAssert.areEqual(j, 1);

        String s1;
        list = getPopulatedList();
        for (int i = 0; i < list.getCount(); i++)
        {
            s1 = "key_" + i;
            String s2 = "val_" + i;

            msAssert.areEqual(list.indexOfKey(s1), list.indexOfValue(s2));
        }

        s1 = "key_50";
        list.remove(s1);
        j = list.indexOfValue(s1);
        msAssert.areEqual(j, -1);
    }

    @Test
    public void testCount()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        msAssert.areEqual(list.getCount(), 0);

        list.set("first key", null);
        msAssert.areEqual(list.getCount(), 1);

        list.set("first key", "first value");
        msAssert.areEqual(list.getCount(), 1);

        list.clear();
        msAssert.areEqual(list.getCount(), 0);

        list = getPopulatedList(50);
        msAssert.areEqual(list.getCount(), 50);

        for (int i = 0; i < 100; i++)
            list.set("key_" + i, "val_" + i);
        msAssert.areEqual(list.getCount(), 100);

        list.clear();
        msAssert.areEqual(list.getCount(), 0);
    }

    @Test
    public void testRemove()
    {
        SortedList<String, String> list = getPopulatedList(100);
        msAssert.areEqual(list.getCount(), 100);

        for (int i = 0; i < 100; i++)
        {
            String s1 = "key_" + i;
            list.remove(s1);
            int j = 100 - (i + 1);
            msAssert.areEqual(list.getCount(), j);
        }

        list.remove("No_Such_Key");//shouldn't throw
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testRemoveNull()
    {
        SortedList<String, String> list = getPopulatedList();
        list.remove(null);
    }

    @Test
    public void testRemoveAt()
    {
        SortedList<String, String> list = getPopulatedList(100);
        msAssert.areEqual(list.getCount(), 100);

        for (int i = 0; i < 100; i++)
        {
            list.removeAt(99 - i);
            int j = 100 - (i + 1);
            msAssert.areEqual(list.getCount(), j);
        }
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveAtEmpty()
    {
        SortedList<String, String> list = getPopulatedList(100);
        msAssert.areEqual(list.getCount(), 100);

        for (int i = 0; i < 100; i++)
        {
            list.removeAt(99 - i);
            int j = 100 - (i + 1);
            msAssert.areEqual(list.getCount(), j);
        }

        list.removeAt(0);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveAtEmpty2()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        list.removeAt(0);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveAtNegative()
    {
        SortedList<String, String> list = getPopulatedList();
        list.removeAt(-1);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveAtMaxValue()
    {
        SortedList<String, String> list = getPopulatedList();
        list.removeAt(Integer.MAX_VALUE);
    }

    @Test
    public void testTrimToSize()
    {
        SortedList<String, String> list = getPopulatedList(32);
        list.add("key_32", "val_32");
        msAssert.areEqual(list.getCount(), 33);

        for (int i = 0; i < 10; i++)
            list.remove("key_" + i);
        msAssert.areEqual(list.getCount(), 23);

        list.trimToSize();
        list.clear();
        list.trimToSize();
    }

    @Test
    public void testSet()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        list.set("first key", null);
        msAssert.areEqual(list.getCount(), 1);

        list.set("0", "first value");
        msAssert.areEqual(list.getCount(), 2);

        msAssert.isTrue(list.containsKey("first key"));

        list.set("first key", "first value");
        msAssert.areEqual(list.get("first key"), "first value");

        list.set("first key", "second value");
        msAssert.areEqual(list.getCount(), 2);
        msAssert.areEqual(list.get("first key"), "second value");

        list.clear();
        for (int i = 0; i < 50; i++)
            list.add("key_" + i, "val_" + i);
        for (int i = 0; i < 50; i++)
            list.set("key_" + i, "new_val_" + i);
        msAssert.areEqual(list.getCount(), 50);
        for (int i = 0; i < 50; i++)
            msAssert.areEqual(list.get("key_" + i), "new_val_" + i);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testSetNullKey()
    {
        SortedList<String, String> list = new SortedList<String, String>();
        list.set(null, "first value");
    }

    @Test
    public void testGetCapacity()
    {
        //JAVA-changed default capacity from 0 to 16. 
        int defaultCapacity =
        16;     
        SortedList<String, String> list = new SortedList<String, String>();
        msAssert.areEqual(list.getCapacity(), defaultCapacity);

        list.setCapacity(3);
        msAssert.areEqual(list.getCapacity(), 3);

        list.setCapacity(0);
        msAssert.areEqual(list.getCapacity(), defaultCapacity);//JAVA-changed since default capacity in java is 16.

        list.setCapacity(5000);
        msAssert.areEqual(list.getCapacity(), 5000);
    }

    private SortedList<String, String> getPopulatedList()
    {
        int size = 100;
        return getPopulatedList(size);
    }

    private static SortedList<String, String> getPopulatedList(int size)
    {
        SortedList<String, String> sl = new SortedList<String, String>();
        for (int i = 0; i < size; i++)
            sl.add("key_" + i, "val_" + i);
        return sl;
    }
}

