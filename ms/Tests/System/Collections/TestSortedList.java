// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/03/2009 by Konstantin Sidorenko
// 2016/07/15 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Collections.SortedList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestSortedList
{
    @Test
    public void testCtor()
    {
        SortedList list = new SortedList();
        msAssert.isNotNull(list);
        msAssert.areEqual(list.getCount(), 0);

        list.add("cde", null);
        list.add("abc", null);
        list.add("bcd", null);

        msAssert.areEqual(list.getCount(), 3);
        msAssert.areEqual(list.indexOfKey("cde"), 2);
        msAssert.areEqual(list.indexOfKey("abc"), 0);
        msAssert.areEqual(list.indexOfKey("bcd"), 1);
        msAssert.areEqual(list.getKey(0), "abc");
        msAssert.areEqual(list.getKey(1), "bcd");
        msAssert.areEqual(list.getKey(2), "cde");
    }

    @Test
    public void testCtor2()
    {
        SortedList list = new SortedList(100);
        msAssert.isNotNull(list);
        msAssert.areEqual(list.getCount(), 0);

        list = new SortedList(0);
        msAssert.isNotNull(list);
        msAssert.areEqual(list.getCount(), 0);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testCtorWrongCapacity()
    {
        SortedList list = new SortedList(-1);
        msAssert.isNotNull(list); // Just a stub to prevent an unused variable hint.
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testCtorWrongIndex()
    {
        SortedList list = new SortedList();
        Object item = list.getByIndex(-1);
        msAssert.isNull(item);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testCtorWrongIndex2()
    {
        SortedList list = new SortedList();
        list.getByIndex(0);
    }

    @Test
    public void testGetByIndex()
    {
        SortedList list = getPopulatedList();

        String s1;
        for (int i = 0; i < list.getCount(); i++)
        {
            s1 = "key_" + i;
            String s2 = "val_" + i;
            String s3 = (String)list.getByIndex(list.indexOfKey(s1));
            String s4 = (String)list.getByIndex(list.indexOfValue(s2));

            msAssert.areEqual(s3, s4);
            msAssert.areEqual(s3, s2);
        }

        s1 = "key_50";
        list.remove(s1);
        int j = list.indexOfKey(s1);
        msAssert.areEqual(j, -1);

    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetByWrongIndex1()
    {
        SortedList list = getPopulatedList();
        list.getByIndex(list.getCount());
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetByWrongIndex2()
    {
        SortedList list = getPopulatedList();
        list.getByIndex(list.getCount() + 1);
    }

    @Test
    public void testClear()
    {
        SortedList list = new SortedList();
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
        SortedList list = new SortedList();
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
        SortedList list = new SortedList();
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
        SortedList list = new SortedList();
        String s1 = (String)list.get("No_Such_Key");
        msAssert.isNull(s1);

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
        SortedList list = new SortedList();
        list.indexOfKey(null);
    }

    @Test
    public void testIndexOfKey()
    {
        SortedList list = new SortedList();
        int j = list.indexOfKey("No_Such_Key");
        msAssert.areEqual(j, -1);

        String s1;
        list = getPopulatedList();
        for (int i = 0; i < list.getCount(); i++)
        {
            s1 = "key_" + i;
            String s2 = "val_" + i;
            String s3 = (String)list.getByIndex(list.indexOfKey(s1));
            String s4 = (String)list.getByIndex(list.indexOfValue(s2));

            msAssert.areEqual(s3, s4);
            msAssert.areEqual(s3, s2);
        }

        s1 = "key_50";
        list.remove(s1);
        j = list.indexOfKey(s1);
        msAssert.areEqual(j, -1);
    }

    @Test
    public void testIndexOfValue()
    {
        SortedList list = new SortedList();
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
            String s3 = (String)list.getByIndex(list.indexOfKey(s1));
            String s4 = (String)list.getByIndex(list.indexOfValue(s2));

            msAssert.areEqual(s3, s4);
            msAssert.areEqual(s3, s2);
        }

        s1 = "key_50";
        list.remove(s1);
        j = list.indexOfValue(s1);
        msAssert.areEqual(j, -1);
    }

    @Test
    public void testCount()
    {
        SortedList list = new SortedList();
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
    public void testClone()
    {
        SortedList list = new SortedList();
        for (int i = 0; i < 10; i++)
            list.add("Key_" + i, "string_" + i);

        SortedList list2 = (SortedList)list.deepClone();
        for (int i = 0; i < 10; i++)
        {
            String s1 = "string_" + i;
            String s2 = "Key_" + i;
            String s3 = (String)list2.get(s2);
            msAssert.areEqual(s1, s3);
        }

        list.remove("Key_9");
        msAssert.areEqual(list.get("Key_9"), null);
        msAssert.areEqual(list2.get("Key_9"), "string_9");

        list = new SortedList(1000);
        list2 = (SortedList)list.deepClone();
        msAssert.areEqual(list.getCount(), list2.getCount());
    }

    @Test
    public void testRemove()
    {
        SortedList list = getPopulatedList(100);
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
        SortedList list = getPopulatedList();
        list.remove(null);
    }

    @Test
    public void testRemoveAt()
    {
        SortedList list = getPopulatedList(100);
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
        SortedList list = getPopulatedList(100);
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
        SortedList list = new SortedList();
        list.removeAt(0);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveAtNegative()
    {
        SortedList list = getPopulatedList();
        list.removeAt(-1);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveAtMaxValue()
    {
        SortedList list = getPopulatedList();
        list.removeAt(Integer.MAX_VALUE);
    }

    @Test
    public void testTrimToSize()
    {
        SortedList list = getPopulatedList(32);
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
        SortedList list = new SortedList();
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
        SortedList list = new SortedList();
        list.set(null, "first value");
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetKeyAbsent()
    {
        SortedList list = new SortedList();
        list.getKey(0);
    }

    @Test
    public void testGetKey()
    {
        SortedList list = new SortedList();
        list.set("first key", null);
        msAssert.areEqual(list.getCount(), 1);

        list.clear();
        for (int i = 0; i < 50; i++)
            list.add("key_" + i, "val_" + i);
        for (int i = 0; i < 50; i++)
        {
            String s1 = "key_" + i;
            String s2 = (String)list.getKey(list.indexOfKey(s1));
            msAssert.areEqual(s1, s2);
        }
        msAssert.areEqual(list.getCount(), 50);
    }

    @Test
    public void testGetCapacity()
    {
        //JAVA-changed default capacity from 0 to 16. 
        int defaultCapacity =
        16;     
        SortedList list = new SortedList();
        msAssert.areEqual(list.getCapacity(), defaultCapacity);

        list.setCapacity(3);
        msAssert.areEqual(list.getCapacity(), 3);

        list.setCapacity(0);
        msAssert.areEqual(list.getCapacity(), defaultCapacity);

        list.setCapacity(5000);
        msAssert.areEqual(list.getCapacity(), 5000);
    }

    private SortedList getPopulatedList()
    {
        int size = 100;
        return getPopulatedList(size);
    }

    private SortedList getPopulatedList(int size)
    {
        SortedList sl = new SortedList();
        for (int i = 0; i < size; i++)
            sl.add("key_" + i, "val_" + i);

        return sl;
    }
}

