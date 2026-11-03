// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/05/2024 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.Assert;
import java.util.Map;


@Test
//[JavaManual("replase new Integer[] with new Integer[]")]
public class TestStringListGeneric
{
    /// <summary>
    /// Tests creation of the list.
    /// </summary>
    @Test
    public void testCreate()
    {
        StringListGeneric<Integer> listA = new StringListGeneric<Integer>(new String[] { "a", "c", "b" }, new Integer[] { 1, 3, 2 });
        msAssert.areEqual(3, listA.getCount());
        msAssert.areEqual(1, listA.get("a"));
        msAssert.areEqual(3, listA.get("c"));
        msAssert.areEqual(2, listA.get("b"));

        StringListGeneric<Integer> listB = new StringListGeneric<Integer>(listA);
        msAssert.areEqual(3, listB.getCount());
        msAssert.areEqual(1, listB.get("a"));
        msAssert.areEqual(3, listB.get("c"));
        msAssert.areEqual(2, listB.get("b"));
    }

    /// <summary>
    /// Tests get value at a specified index.
    /// </summary>
    @Test
    public void testGetValueAt()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>(new String[] { "a", "c", "b" }, new Integer[] { 1, 3, 2 });
        msAssert.areEqual(1, list.getByIndex(0));
        msAssert.areEqual(3, list.getByIndex(1));
        msAssert.areEqual(2, list.getByIndex(2));
    }

    /// <summary>
    /// Tests remove element at a specified index.
    /// </summary>
    @Test
    public void testRemoveAt()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>(new String[] { "a", "c", "b" }, new Integer[] { 1, 3, 2 });
        msAssert.areEqual(3, list.getCount());

        list.removeAt(1);
        msAssert.areEqual(2, list.getCount());
        msAssert.areEqual(1, list.getByIndex(0));
        msAssert.areEqual(2, list.getByIndex(1));

        list.removeAt(0);
        msAssert.areEqual(1, list.getCount());
        msAssert.areEqual(2, list.getByIndex(0));

        list.removeAt(0);
        msAssert.areEqual(0, list.getCount());
    }

    /// <summary>
    /// Tests get index of a key in the list.
    /// </summary>
    @Test
    public void testIndexOfKey()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>(new String[] { "a", "a", "b" }, new Integer[] { 1, 3, 2 });
        msAssert.areEqual(0, list.indexOfKey("a"));
        msAssert.areEqual(2, list.indexOfKey("b"));
    }

    /// <summary>
    /// Tests whether a specified key is contained in the List.
    /// </summary>
    @Test
    public void testContainsKey()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>(new String[] { "a", "c", "b" }, new Integer[] { 1, 3, 2 });
        Assert.assertTrue(list.containsKey("a"));
        Assert.assertTrue(list.containsKey("b"));
        Assert.assertTrue(list.containsKey("c"));
        Assert.assertFalse(list.containsKey("d"));
    }

    /// <summary>
    /// Tests adding element to the List.
    /// </summary>
    @Test
    public void testAdd()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>();
        msAssert.areEqual(0, list.getCount());

        list.add("a", 1);
        msAssert.areEqual(1, list.getCount());
        msAssert.areEqual(1, list.get("a"));

        list.add("b", 2);
        msAssert.areEqual(2, list.getCount());
        msAssert.areEqual(1, list.get("a"));
        msAssert.areEqual(2, list.get("b"));

        list.add("a", 3);
        msAssert.areEqual(3, list.getCount());
        msAssert.areEqual(3, list.get("a"));
        msAssert.areEqual(2, list.get("b"));
        msAssert.areEqual("a", list.getKey(0));
        msAssert.areEqual(1, list.getByIndex(0));
    }

    /// <summary>
    /// Tests remove element with a specified key from the List.
    /// </summary>
    @Test
    public void testRemove()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>(new String[] { "a", "b" }, new Integer[] { 1, 2 });
        list.remove("b");
        msAssert.areEqual(1, list.getCount());
        msAssert.areEqual(1, list.get("a"));

        // Add duplicated key.
        list.add("a", 5);
        msAssert.areEqual(2, list.getCount());
        msAssert.areEqual(5, list.get("a"));
        msAssert.areEqual("a", list.getKey(0));
        msAssert.areEqual(1, list.getByIndex(0));

        list.remove("a");
        msAssert.areEqual(1, list.getCount());
        msAssert.areEqual(1, list.get("a"));

        list.remove("a");
        msAssert.areEqual(0, list.getCount());
    }

    /// <summary>
    /// Tests get value safely.
    /// </summary>
    @Test
    public void testGetValueSafely()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>(new String[] { "a", "c", "b" }, new Integer[] { 1, 3, 2 });

        // Get existing key.
        msAssert.areEqual(3, list.getValueSafe("c", 5));

        // Get non-existing key.
        msAssert.areEqual(-1, list.getValueSafe("d", -1));

        // Add duplicated key.
        list.add("a", 5);

        msAssert.areEqual(5, list.getValueSafe("a", -1));

        list.remove("a");
        msAssert.areEqual(1, list.getValueSafe("a", -1));

        list.remove("a");
        msAssert.areEqual(-1, list.getValueSafe("a", -1));
    }

    /// <summary>
    /// Tests get key at a specified index.
    /// </summary>
    @Test
    public void testGetKeyAt()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>(new String[] { "a", "c", "b" }, new Integer[] { 1, 3, 2 });
        msAssert.areEqual(3, list.getCount());
        msAssert.areEqual("a", list.getKey(0));
        msAssert.areEqual("c", list.getKey(1));
        msAssert.areEqual("b", list.getKey(2));
    }

    /// <summary>
    /// Tests sort by key.
    /// </summary>
    @Test
    public void testSort()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>(new String[] { "d", "b", "a", "c", "b" }, new Integer[] { 5, 2, 1, 4, 3 });

        list.sort();

        msAssert.areEqual("a", list.getKey(0));
        msAssert.areEqual("b", list.getKey(1));
        msAssert.areEqual("b", list.getKey(2));
        msAssert.areEqual("c", list.getKey(3));
        msAssert.areEqual("d", list.getKey(4));

        msAssert.areEqual(1, list.get("a"));
        msAssert.areEqual(3, list.get("b"));
        msAssert.areEqual(4, list.get("c"));
        msAssert.areEqual(5, list.get("d"));
    }

    /// <summary>
    /// Tests Enumerator.
    /// </summary>
    @Test
    public void testEnumerator()
    {
        String[] keys = new String[] { "d", "b", "a", "c" };
        Integer[] values = new Integer[] { 4, 2, 1, 3 };
        StringListGeneric<Integer> list = new StringListGeneric<Integer>(keys, values);

        int i = 0;
        for (Map.Entry<String, Integer> keyValuePair : (Iterable<Map.Entry<String, Integer>>) list)
        {
            msAssert.areEqual(keys[i], keyValuePair.getKey());
            msAssert.areEqual(values[i], keyValuePair.getValue());
            i++;
        }
    }

    /// <summary>
    /// Tests indexer.
    /// </summary>
    @Test
    public void testIndexer()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>();

        list.set("a", 1);
        msAssert.areEqual(1, list.get("a"));

        list.set("b", 2);
        msAssert.areEqual(2, list.get("b"));

        list.set("a", 2);
        msAssert.areEqual(2, list.get("a"));

        list.set("b", 1);
        msAssert.areEqual(1, list.get("b"));
    }

    /// <summary>
    /// Tests <see cref="StringListGeneric{TValue}.Count"/> property.
    /// </summary>
    @Test
    public void testCount()
    {
        StringListGeneric<Integer> list = new StringListGeneric<Integer>();

        msAssert.areEqual(0, list.getCount());

        list.set("a", 1);
        msAssert.areEqual(1, list.getCount());

        list.set("a", 2);
        msAssert.areEqual(1, list.getCount());

        list.set("b", 3);
        msAssert.areEqual(2, list.getCount());

        list.add("b", 4);
        msAssert.areEqual(3, list.getCount());

        list.remove("a");
        msAssert.areEqual(2, list.getCount());
    }
}

