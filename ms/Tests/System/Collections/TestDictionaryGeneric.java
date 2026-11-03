// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/02/2018 by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.Iterator;
import java.util.Map;
import com.aspose.ms.System.msArray;


@Test
public class TestDictionaryGeneric
{
    /// <summary>
    /// Tests how items are added to a dictionary.
    /// </summary>
    @Test
    public void testAdd()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        msDictionary.add(dictionary, G_FIRST_KEY, G_FIRST_VALUE);
        msDictionary.add(dictionary, G_SECOND_KEY, G_SECOND_VALUE);
        msAssert.areEqual(2, dictionary.size());
        msAssert.areEqual(G_FIRST_VALUE, dictionary.get(G_FIRST_KEY));
        msAssert.areEqual(G_SECOND_VALUE, dictionary.get(G_SECOND_KEY));
    }

    /// <summary>
    /// Tests that duplicate keys can not be added to a dictionary.
    /// </summary>
    @Test
    public void testAddDuplicate()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        msDictionary.add(dictionary, G_FIRST_KEY, G_FIRST_VALUE);
        testAddDuplicate(dictionary, G_FIRST_KEY);
    }

    /// <summary>
    /// Tests the addition of a duplicate key to a dictionary.
    /// </summary>
    private static void testAddDuplicate(HashMap<String, String> dictionary, String duplicateKey)
    {
        try
        {
            msDictionary.add(dictionary, duplicateKey, G_FIRST_VALUE);
        }
        catch (IllegalArgumentException e)
        {
            return;
        }

        Assert.fail("An exception throw is expected.");
    }

    /// <summary>
    /// Tests removal of all items from a dictionary.
    /// </summary>
    @Test
    public void testClear()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        msDictionary.add(dictionary, G_FIRST_KEY, G_FIRST_VALUE);
        msDictionary.add(dictionary, G_SECOND_KEY, G_SECOND_VALUE);
        dictionary.clear();
        msAssert.areEqual(0, dictionary.size());
    }

    /// <summary>
    /// Tests how dictionary key and value existence is checked.
    /// </summary>
    @Test
    public void testContains()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        msDictionary.add(dictionary, G_FIRST_KEY, G_FIRST_VALUE);
        msAssert.isTrue(dictionary.containsKey(G_FIRST_KEY));
        msAssert.isTrue(dictionary.containsValue(G_FIRST_VALUE));
        msAssert.isFalse(dictionary.containsKey(G_SECOND_KEY));
        msAssert.isFalse(dictionary.containsValue(G_SECOND_VALUE));
    }

    /// <summary>
    /// Tests how dictionary enumeration works.
    /// </summary>
    @Test
    public void testEnumerator()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        msDictionary.add(dictionary, G_FIRST_KEY, G_FIRST_VALUE);
        msDictionary.add(dictionary, G_SECOND_KEY, G_SECOND_VALUE);

        Iterator<Map.Entry<String, String>> enumerator = dictionary.entrySet().iterator();
        testEnumerator(dictionary, enumerator);
    }

    /// <summary>
    /// Tests a single pass of a dictionary enumerator.
    /// </summary>
    private static void testEnumerator(HashMap<String, String> dictionary, Iterator<Map.Entry<String, String>> enumerator)
    {
        String[] keys = new String[dictionary.size()];
        String[] values = new String[dictionary.size()];
        int index = 0;

        while (enumerator.hasNext())
        {
            Map.Entry<String, String> current = enumerator.next();
            keys[index] = current.getKey();
            values[index] = current.getValue();
            index++;
        }

        msAssert.areEqual(2, index);

        int firstKeyIndex = msArray.indexOf((keys), (G_FIRST_KEY), 0, (keys).length);
        msAssert.lessOrEqual(0, firstKeyIndex);

        int secondKeyIndex = msArray.indexOf((keys), (G_SECOND_KEY), 0, (keys).length);
        msAssert.lessOrEqual(0, secondKeyIndex);
        msAssert.areNotEqual(firstKeyIndex, secondKeyIndex);

        int firstValueIndex = msArray.indexOf((values), (G_FIRST_VALUE), 0, (values).length);
        msAssert.lessOrEqual(0, firstValueIndex);
        msAssert.areEqual(firstKeyIndex, firstValueIndex);

        int secondValueIndex = msArray.indexOf((values), (G_SECOND_VALUE), 0, (values).length);
        msAssert.lessOrEqual(0, secondValueIndex);
        msAssert.areNotEqual(firstValueIndex, secondValueIndex);
        msAssert.areEqual(secondKeyIndex, secondValueIndex);
    }


    /// <summary>
    /// Tests how dictionary item removal works.
    /// </summary>
    @Test
    public void testRemove()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        msDictionary.add(dictionary, G_FIRST_KEY, G_FIRST_VALUE);
        boolean isRemoved = msDictionary.remove(dictionary, G_SECOND_KEY);
        msAssert.isFalse(isRemoved);
        msAssert.areEqual(1, dictionary.size());

        isRemoved = msDictionary.remove(dictionary, G_FIRST_KEY);
        msAssert.isTrue(isRemoved);
        msAssert.areEqual(0, dictionary.size());

        isRemoved = msDictionary.remove(dictionary, G_FIRST_KEY);
        msAssert.isFalse(isRemoved);
        msAssert.areEqual(0, dictionary.size());
    }

    /// <summary>
    /// Tests how dictionary item count calculation works.
    /// </summary>
    @Test
    public void testCount()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        msDictionary.add(dictionary, G_FIRST_KEY, G_FIRST_VALUE);
        msAssert.areEqual(1, dictionary.size());

        msDictionary.add(dictionary, G_SECOND_KEY, G_SECOND_VALUE);
        msAssert.areEqual(2, dictionary.size());

        msDictionary.remove(dictionary, G_SECOND_KEY);
        msAssert.areEqual(1, dictionary.size());

        dictionary.clear();
        msAssert.areEqual(0, dictionary.size());
    }

    /// <summary>
    /// Tests how dictionary values are set and retrieved according to their keys.
    /// </summary>
    @Test
    public void testGetSet()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        dictionary.put(G_FIRST_KEY, G_FIRST_VALUE);
        dictionary.put(G_FIRST_KEY, G_SECOND_VALUE);
        msAssert.areEqual(1, dictionary.size());
        msAssert.areEqual(G_SECOND_VALUE, dictionary.get(G_FIRST_KEY));

        msAssert.areEqual(true, dictionary.containsKey(G_FIRST_KEY));
        msAssert.areEqual(G_SECOND_VALUE, DictionaryExtension.getValueOrNull(dictionary, G_FIRST_KEY));

        msAssert.areEqual(false, dictionary.containsKey(G_SECOND_KEY));
    }

    /// <summary>
    /// Tests how a dictionary contents are copied from another dictionary.
    /// </summary>
    @Test
    public void testCopyCtor()
    {
        HashMap<String, String> dictionary1 = new HashMap<String, String>();
        dictionary1.put(G_FIRST_KEY, G_FIRST_VALUE);
        dictionary1.put(G_SECOND_KEY, G_SECOND_VALUE);

        HashMap<String, String> dictionary2 = new HashMap<String, String>(dictionary1);
        msAssert.areEqual(2, dictionary2.size());
        msAssert.areEqual(G_FIRST_VALUE, dictionary2.get(G_FIRST_KEY));
        msAssert.areEqual(G_SECOND_VALUE, dictionary2.get(G_SECOND_KEY));

        // Ensure that the dictionaries are independent.
        dictionary2.put(G_FIRST_KEY, G_SECOND_VALUE);
        msAssert.areEqual(G_SECOND_VALUE, dictionary2.get(G_FIRST_KEY));
        msAssert.areEqual(G_FIRST_VALUE, dictionary1.get(G_FIRST_KEY));
    }

    /// <summary>
    /// Tests how a dictionary with case-sensitive string keys works.
    /// </summary>
    @Test
    public void testCaseSensitiveDictionary()
    {
        testCaseSensitiveDictionary(new HashMap<String, String>());
    }

    private static void testCaseSensitiveDictionary(HashMap<String, String> dictionary)
    {
        // Add two values to a dictionary with keys which differ only by character case and
        // ensure that they are considered to be separate dictionary entries.
        dictionary.put("Iя", G_FIRST_VALUE);
        dictionary.put("iя", G_SECOND_VALUE);
        msAssert.areEqual(2, dictionary.size());
        msAssert.areEqual(G_FIRST_VALUE, dictionary.get("Iя"));
        msAssert.areEqual(G_SECOND_VALUE, dictionary.get("iя"));
        msAssert.isTrue(dictionary.containsKey("Iя"));
        msAssert.isTrue(dictionary.containsKey("iя"));
        msAssert.isFalse(dictionary.containsKey("IЯ"));
        msAssert.isFalse(dictionary.containsKey("iЯ"));

        // Remove one of the values and ensure that it is removed whereas the other one is kept.
        boolean isRemoved = msDictionary.remove(dictionary, "Iя");
        msAssert.isTrue(isRemoved);
        msAssert.areEqual(1, dictionary.size());
        msAssert.isFalse(dictionary.containsKey("Iя"));
        msAssert.isTrue(dictionary.containsKey("iя"));
        msAssert.areEqual(G_SECOND_VALUE, dictionary.get("iя"));

        // Ensure that the removed dictionary entry is not considered to be a duplicate while repeated adding.
        msDictionary.add(dictionary, "Iя", G_FIRST_VALUE);
    }

    /// <summary>
    /// Tests how dictionary key enumeration works.
    /// </summary>
    @Test
    public void testKeyEnumeration()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        dictionary.put(G_FIRST_KEY, G_FIRST_VALUE);
        dictionary.put(G_SECOND_KEY, G_SECOND_VALUE);

        int index = 0;
        for (String key : (Iterable<String>) dictionary.keySet())
        {
            switch (index++)
            {
                case 0:
                    msAssert.areEqual(G_FIRST_KEY, key);
                    break;
                case 1:
                    msAssert.areEqual(G_SECOND_KEY, key);
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        msAssert.areEqual(2, index);
    }

    /// <summary>
    /// Tests how dictionary value enumeration works.
    /// </summary>
    @Test
    public void testValueEnumeration()
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        dictionary.put(G_FIRST_KEY, G_FIRST_VALUE);
        dictionary.put(G_SECOND_KEY, G_SECOND_VALUE);

        int index = 0;
        for (String value : (Iterable<String>) dictionary.values())
        {
            switch (index++)
            {
                case 0:
                    msAssert.areEqual(G_FIRST_VALUE, value);
                    break;
                case 1:
                    msAssert.areEqual(G_SECOND_VALUE, value);
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        msAssert.areEqual(2, index);
    }

    /// <summary>
    /// Returns the first dictionary key to test.
    /// </summary>
    private static String getFirstKey()
    {
        return "a";
    }

    /// <summary>
    /// Returns the second dictionary key to test.
    /// </summary>
    private static String getSecondKey()
    {
        return "b";
    }

    /// <summary>
    /// Returns the first dictionary value to test.
    /// </summary>
    private static String getFirstValue()
    {
        return "A";
    }

    /// <summary>
    /// Returns the second dictionary value to test.
    /// </summary>
    private static String getSecondValue()
    {
        return "B";
    }

    // Calculate once, use multiple times.
    private static /*final*/ String G_FIRST_KEY = getFirstKey();
    private static /*final*/ String G_SECOND_KEY = getSecondKey();
    private static /*final*/ String G_FIRST_VALUE = getFirstValue();
    private static /*final*/ String G_SECOND_VALUE = getSecondValue();
}


