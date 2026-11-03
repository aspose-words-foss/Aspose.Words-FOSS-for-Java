// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2018 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Text.msStringBuilder;
import org.testng.Assert;


@Test
public class TestSortedIntegerListGeneric
{
    @Test
    public void testAdd()
    {
        SortedIntegerListGeneric<String> list = new SortedIntegerListGeneric<String>();
        addAll(list);
        checkSorting(list, mOrderedValues1);
    }

    @Test
    public void testSet()
    {
        SortedIntegerListGeneric<String> list = new SortedIntegerListGeneric<String>();
        setAll(list);
        checkSorting(list, mOrderedValues2);
    }

    @Test
    public void testAddSet()
    {
        SortedIntegerListGeneric<String> list = new SortedIntegerListGeneric<String>();
        addAll(list);
        setAll(list);
        checkSorting(list, mOrderedValues2);
    }

    @Test
    public void testAddSetRemove()
    {
        SortedIntegerListGeneric<String> list = new SortedIntegerListGeneric<String>();
        addAll(list);
        setAll(list);
        removeAll(list);
        checkSorting(list, mOrderedValues3);
    }

    private void addAll(SortedIntegerListGeneric<String> list)
    {
        for (int i = 0; i < mKeysToAdd.length; i++)
            list.add(mKeysToAdd[i], mValuesToAdd[i]);
    }

    private void setAll(SortedIntegerListGeneric<String> list)
    {
        for (int i = 0; i < mKeysToSet.length; i++)
            list.set(mKeysToSet[i], mValuesToSet[i]);
    }

    private void removeAll(SortedIntegerListGeneric<String> list)
    {
        for (int i = 0; i < mKeysToRemove.length; i++)
            list.remove(mKeysToRemove[i]);
    }

    private static String[] getArrayOfValues(SortedIntegerListGeneric<String> list)
    {
        String[] values = new String[list.getCount()];
        for (int i = 0; i < list.getCount(); i++)
            values[i] = list.getByIndex(i);
        return values;
    }

    private static void checkSorting(SortedIntegerListGeneric<String> list, String[] expectedValues)
    {
        String[] sortedValues = getArrayOfValues(list);
        if (!ArrayUtil.isArrayEqual(sortedValues, expectedValues))
        {
            StringBuilder sb = new StringBuilder();
            msStringBuilder.append(sb, "\r\nsorted\t: expected\r\n");
            msStringBuilder.append(sb, "----------:---------\r\n");
            for (int i = 0; i < expectedValues.length; i++)
                msStringBuilder.appendFormat(sb, "{0} -> {1} : {2}\r\n", list.getKey(i), sortedValues[i], expectedValues[i]);

            Assert.fail(sb.toString());
        }
    }

    private /*final*/ int[] mKeysToAdd =
    {
        0, 20, 10, 200, 100, 2000, 1000, 20000, 10000, 200000, 100000,
        2000000, 1000000, 20000000, 10000000, 200000000, 100000000, 2000000000, 1000000000
    };

    private /*final*/ String[] mValuesToAdd =
    {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18"
    };

    private /*final*/ String[] mOrderedValues1 =
    {
        "0", "2", "1", "4", "3", "6", "5", "8", "7", "10", "9", "12", "11", "14", "13", "16", "15", "18", "17"
    };

    private /*final*/ int[] mKeysToSet =
    {
        999, 0, 20, 10, 200, 100, 2000, 1000, 20000, 10000, 200000, 100000,
        999999999, 2000000, 1000000, 20000000, 10000000, 200000000, 100000000, 2000000000, 1000000000
    };

    private /*final*/ String[] mValuesToSet =
    {
        "19", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "20", "11", "12", "13", "14", "15", "16", "17", "18"
    };

    private /*final*/ String[] mOrderedValues2 =
    {
        "0", "2", "1", "4", "3", "19", "6", "5", "8", "7", "10", "9", "12", "11", "14", "13", "16", "15", "20", "18", "17"
    };

    private /*final*/ int[] mKeysToRemove =
    {
        20, 10, 200, 100, 2000, 1000, 20000, 10000, 200000, 100000,
        2000000, 1000000, 20000000, 10000000, 200000000, 100000000, 2000000000, 1000000000
    };

    private /*final*/ String[] mOrderedValues3 = { "0", "19", "20" };
}

