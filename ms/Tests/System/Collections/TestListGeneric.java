// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/02/2018 by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msArray;
import java.util.Collections;
import com.aspose.ms.System.msString;
import java.util.Comparator;


@Test
public class TestListGeneric
{
    @Test
    public void testAdd()
    {
        ArrayList<String> list = new ArrayList<String>();
        msArrayList.add(list, getFirstItem());
        msAssert.areEqual(1, list.size());
        msAssert.areEqual(getFirstItem(), list.get(0));
    }

    @Test
    public void testAddRange()
    {
        ArrayList<String> list1 = new ArrayList<String>();
        msArrayList.addRange(list1, G_ARRAY);
        msAssert.areEqual(G_ARRAY.length, list1.size());
        msAssert.areEqual(G_ARRAY, msArrayList.toArray(list1, new String[0]));

        ArrayList<String> list2 = new ArrayList<String>();
        msArrayList.addRange(list2, list1);
        msAssert.areEqual(G_ARRAY.length, list2.size());
        msAssert.areEqual(G_ARRAY, msArrayList.toArray(list2, new String[0]));
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testAddRangeNullList()
    {
        ArrayList<String> list = new ArrayList<String>();
        msArrayList.addRange(list, (ArrayList<String>)null);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testAddRangeNullArray()
    {
        ArrayList<String> list = new ArrayList<String>();
        msArrayList.addRange(list, (String[])null);
    }

    @Test
    public void testSortBinarySearchFull()
    {
        String[] sortedArray = new String[G_ARRAY.length];
        System.arraycopy((G_ARRAY), 0, (sortedArray), 0, (G_ARRAY.length));
        msArray.sort(sortedArray);

        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        Collections.sort(list);
        msAssert.areEqual(sortedArray, msArrayList.toArray(list, new String[0]));
        msAssert.areEqual(ArrayUtil.binarySearch(sortedArray, 0, sortedArray.length, getFirstItem()), msArrayList.binarySearch(list, getFirstItem(), StringOrdinalComparer.DEFAULT));
        msAssert.less(msArrayList.binarySearch(list, G_MISSING_ITEM, StringOrdinalComparer.DEFAULT), 0);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testSortNegativeIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        Collections.sort(list.subList(-1, -1 + list.size()), StringOrdinalComparer.DEFAULT);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testSortNegativeCount()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        Collections.sort(list.subList(0, 0 + -1), StringOrdinalComparer.DEFAULT);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testSortInvalidRange()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        Collections.sort(list.subList(0, 0 + list.size() + 1), StringOrdinalComparer.DEFAULT);
    }

    @Test
    public void testClear()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.add(list, getFirstItem());
        list.clear();
        msAssert.areEqual(0, list.size());
    }

    @Test
    public void testContains()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msAssert.isTrue(list.contains(getFirstItem()));
        msAssert.isFalse(list.contains(G_MISSING_ITEM));
    }

    @Test
    public void testCopyTo()
    {
        String[] expectedArray = new String[G_ARRAY.length * 2 + 1];
        System.arraycopy((G_ARRAY), 0, (expectedArray), 0, (G_ARRAY.length));
        msArray.copy(G_ARRAY, 0, expectedArray, G_ARRAY.length, G_ARRAY.length);
        expectedArray[expectedArray.length - 1] = G_ARRAY[G_ARRAY.length - 1];

        String[] actualArray = new String[G_ARRAY.length * 2 + 1];
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.copyTo(list, actualArray);
        msArrayList.copyTo(list, actualArray, list.size());
        msArrayList.copyTo(list, list.size() - 1, actualArray, list.size() * 2, 1);

        msAssert.areEqual(expectedArray, actualArray);
    }

    @Test (expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testCopyToNegativeIndex()
    {
        String[] array = new String[G_ARRAY.length];
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.copyTo(list, -1, array, 0, list.size());
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testCopyToNegativeCount()
    {
        String[] array = new String[G_ARRAY.length];
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.copyTo(list, 0, array, 0, -1);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testCopyToInvalidRange()
    {
        String[] array = new String[G_ARRAY.length];
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.copyTo(list, 0, array, 0, list.size() + 1);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testCopyToNullArray()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.copyTo(list, 0, null, 0, list.size());
    }

    @Test (expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testCopyToNegativeArrayIndex()
    {
        String[] array = new String[G_ARRAY.length];
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.copyTo(list, 0, array, -1, list.size());
    }

    @Test (expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testCopyToInvalidArrayRange()
    {
        String[] array = new String[G_ARRAY.length - 1];
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.copyTo(list, 0, array, 0, list.size());
    }

    @Test
    public void testGetRange()
    {
        ArrayList<String> list1 = msArrayList.ctor(G_ARRAY);
        ArrayList<String> list2 = msArrayList.getRange(list1, 1, list1.size() - 1);
        msAssert.areEqual(list1.size() - 1, list2.size());

        String[] expectedArray = new String[G_ARRAY.length - 1];
        msArray.copy(G_ARRAY, 1, expectedArray, 0, G_ARRAY.length - 1);
        msAssert.areEqual(expectedArray, msArrayList.toArray(list2, new String[0]));
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetRangeNegativeIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.getRange(list, -1, list.size());
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testGetRangeNegativeCount()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.getRange(list, 0, -1);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetRangeInvalidRange()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.getRange(list, 0, list.size() + 1);
    }

    @Test
    public void testIndexOf()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msAssert.areEqual(0, list.indexOf(getFirstItem()));
        msAssert.areEqual(-1, list.indexOf(G_MISSING_ITEM));
    }

    @Test
    public void testInsert()
    {
        String[] expectedArray = new String[G_ARRAY.length + 3];
        expectedArray[0] = G_MISSING_ITEM;
        expectedArray[1] = getFirstItem();
        expectedArray[2] = G_MISSING_ITEM;
        expectedArray[expectedArray.length - 1] = G_MISSING_ITEM;
        msArray.copy(G_ARRAY, 1, expectedArray, 3, G_ARRAY.length - 1);

        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        list.add(1, G_MISSING_ITEM);
        list.add(0, G_MISSING_ITEM);
        list.add(list.size(), G_MISSING_ITEM);
        msAssert.areEqual(G_ARRAY.length + 3, list.size());
        msAssert.areEqual(expectedArray, msArrayList.toArray(list, new String[0]));
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testInsertNegativeIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        list.add(-1, G_MISSING_ITEM);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testInsertExceedingIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        list.add(list.size() + 1, G_MISSING_ITEM);
    }

    @Test
    public void testInsertRange()
    {
        String[] expectedArray = new String[G_ARRAY.length * 4];
        System.arraycopy((G_ARRAY), 0, (expectedArray), 0, (G_ARRAY.length));
        expectedArray[G_ARRAY.length] = getFirstItem();
        msArray.copy(G_ARRAY, 0, expectedArray, G_ARRAY.length + 1, G_ARRAY.length);
        msArray.copy(G_ARRAY, 1, expectedArray, G_ARRAY.length * 2 + 1, G_ARRAY.length - 1);
        msArray.copy(G_ARRAY, 0, expectedArray, G_ARRAY.length * 3, G_ARRAY.length);

        ArrayList<String> list1 = msArrayList.ctor(G_ARRAY);
        ArrayList<String> list2 = msArrayList.ctor(G_ARRAY);
        msArrayList.insertRange(list1, 1, list2);
        msArrayList.insertRange(list1, 0, list2);
        msArrayList.insertRange(list1, list1.size(), list2);
        msArrayList.insertRange(list2, 1, G_ARRAY);
        msArrayList.insertRange(list2, 0, G_ARRAY);
        msArrayList.insertRange(list2, list2.size(), G_ARRAY);

        msAssert.areEqual(expectedArray.length, list1.size());
        msAssert.areEqual(expectedArray, msArrayList.toArray(list1, new String[0]));
        msAssert.areEqual(expectedArray.length, list2.size());
        msAssert.areEqual(expectedArray, msArrayList.toArray(list2, new String[0]));
    }

    @Test
    public void testInsertRangeSelf()
    {
        String[] expectedArray = new String[G_ARRAY.length * 2];
        expectedArray[0] = getFirstItem();
        msArray.copy(G_ARRAY, 0, expectedArray, 1, G_ARRAY.length);
        msArray.copy(G_ARRAY, 1, expectedArray, G_ARRAY.length + 1, G_ARRAY.length - 1);

        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.insertRange(list, 1, list);
        msAssert.areEqual(expectedArray.length, list.size());
        msAssert.areEqual(expectedArray, msArrayList.toArray(list, new String[0]));
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testInsertRangeNullArray()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.insertRange(list, 1, (String[])null);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testInsertRangeArrayNegativeIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.insertRange(list, -1, G_ARRAY);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testInsertRangeArrayExceedingIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.insertRange(list, list.size() + 1, G_ARRAY);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testInsertRangeNullList()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.insertRange(list, 1, (ArrayList<String>)null);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testInsertRangeListNegativeIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.insertRange(list, -1, list);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testInsertRangeListExceedingIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.insertRange(list, list.size() + 1, list);
    }

    @Test
    public void testLastIndexOf()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        int lastItemIndex = list.size() - 1;
        String lastItem = list.get(lastItemIndex);

        msAssert.areEqual(lastItemIndex, list.lastIndexOf(lastItem));
        msAssert.areEqual(-1, list.lastIndexOf(G_MISSING_ITEM));
    }

    @Test
    public void testRemove()
    {
        String[] expectedArray = new String[G_ARRAY.length - 1];
        expectedArray[0] = getFirstItem();
        msArray.copy(G_ARRAY, 2, expectedArray, 1, G_ARRAY.length - 2);

        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        boolean isRemoved = list.remove(G_ARRAY[1]);
        msAssert.isTrue(isRemoved);
        msAssert.areEqual(expectedArray.length, list.size());
        msAssert.areEqual(expectedArray, msArrayList.toArray(list, new String[0]));

        isRemoved = list.remove(G_MISSING_ITEM);
        msAssert.isFalse(isRemoved);
        msAssert.areEqual(expectedArray.length, list.size());
        msAssert.areEqual(expectedArray, msArrayList.toArray(list, new String[0]));
    }

    @Test
    public void testRemoveAt()
    {
        String[] expectedArray = new String[G_ARRAY.length - 1];
        expectedArray[0] = getFirstItem();
        msArray.copy(G_ARRAY, 2, expectedArray, 1, G_ARRAY.length - 2);

        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        list.remove(1);
        msAssert.areEqual(expectedArray.length, list.size());
        msAssert.areEqual(expectedArray, msArrayList.toArray(list, new String[0]));
    }

    @Test (expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testRemoveAtNegativeIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        list.remove(-1);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveAtExceedingIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        list.remove(list.size());
    }

    @Test
    public void testRemoveRange()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.removeRange(list, 1, list.size() - 2);
        msAssert.areEqual(2, list.size());
        msAssert.areEqual(getFirstItem(), list.get(0));
        msAssert.areEqual(G_ARRAY[G_ARRAY.length - 1], list.get(1));
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveRangeNegativeIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.removeRange(list, -1, list.size());
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testRemoveRangeNegativeCount()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.removeRange(list, 0, -1);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveRangeInvalidRange()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.removeRange(list, 0, list.size() + 1);
    }

    @Test
    public void testReverseFull()
    {
        String[] expectedArray = new String[G_ARRAY.length];
        System.arraycopy((G_ARRAY), 0, (expectedArray), 0, (G_ARRAY.length));
        msArray.reverse(expectedArray);

        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        Collections.reverse(list);
        msAssert.areEqual(expectedArray, msArrayList.toArray(list, new String[0]));
    }

    @Test
    public void testReversePart()
    {
        String[] expectedArray = new String[G_ARRAY.length];
        System.arraycopy((G_ARRAY), 0, (expectedArray), 0, (G_ARRAY.length));
        msArray.reverse(expectedArray, 1, expectedArray.length - 2);

        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.reverse(list, 1, list.size() - 2);
        msAssert.areEqual(expectedArray, msArrayList.toArray(list, new String[0]));
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testReverseNegativeIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.reverse(list, -1, list.size());
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testReverseNegativeCount()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.reverse(list, 0, -1);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testReverseInvalidRange()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        msArrayList.reverse(list, 0, list.size() + 1);
    }

    @Test
    public void testToArray()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        String[] array1 = msArrayList.toArray(list, new String[0]);
        String[] array2 = msArrayList.toArray(list, new String[0]);
        msAssert.areNotSame(array1, array2);
        msAssert.areEqual(G_ARRAY, array1);
        msAssert.areEqual(G_ARRAY, array2);

        String[] expectedArray = new String[G_ARRAY.length - 1];
        expectedArray[0] = getFirstItem();
        msArray.copy(G_ARRAY, 2, expectedArray, 1, G_ARRAY.length - 2);

        list.remove(1);
        msAssert.areEqual(expectedArray, msArrayList.toArray(list, new String[0]));

        list.clear();
        msAssert.areEqual(new int[0], msArrayList.toArray(list, new String[0]));
    }

    @Test
    public void testCount()
    {
        ArrayList<String> list = new ArrayList<String>();
        msAssert.areEqual(0, list.size());

        msArrayList.add(list, G_MISSING_ITEM);
        msAssert.areEqual(1, list.size());

        msArrayList.add(list, G_MISSING_ITEM);
        msAssert.areEqual(2, list.size());

        list.remove(0);
        msAssert.areEqual(1, list.size());

        list.clear();
        msAssert.areEqual(0, list.size());
    }

    @Test
    public void testGetSet()
    {
        ArrayList<String> list = new ArrayList<String>();
        msArrayList.add(list, G_MISSING_ITEM);
        msAssert.areEqual(G_MISSING_ITEM, list.get(0));

        list.set(0, getFirstItem());
        msAssert.areEqual(getFirstItem(), list.get(0));
    }

    @Test (expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testGetNegativeIndex()
    {
        ArrayList<String> list = new ArrayList<String>();
        String item = list.get(-1);
        Assert.fail(msString.format("", item)); // Force the compiler not to skip the previous instruction.
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetExceedingIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        String item = list.get(list.size());
        Assert.fail(msString.format("", item)); // Force the compiler not to skip the previous instruction.
    }

    @Test (expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testSetNegativeIndex()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.set(-1, G_MISSING_ITEM);
    }

    @Test (expectedExceptions = IndexOutOfBoundsException.class)
    public void testSetExceedingIndex()
    {
        ArrayList<String> list = msArrayList.ctor(G_ARRAY);
        list.set(list.size(), G_MISSING_ITEM);
    }

    @Test
    public void testCopyCtors()
    {
        ArrayList<String> list1 = msArrayList.ctor(G_ARRAY);
        msAssert.areEqual(G_ARRAY, msArrayList.toArray(list1, new String[0]));

        ArrayList<String> list2 = msArrayList.ctor(list1);
        msAssert.areEqual(G_ARRAY, msArrayList.toArray(list2, new String[0]));
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testCopyCtorNullArray()
    {
        ArrayList<String> list = msArrayList.ctor((String[])null);
        Assert.fail(msString.format("", list)); // Force the compiler not to skip the previous instruction.
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testCopyCtorNullList()
    {
        ArrayList<String> list = msArrayList.ctor((ArrayList<String>)null);
        Assert.fail(msString.format("", list)); // Force the compiler not to skip the previous instruction.
    }

    /// <summary>
    /// Returns an array extensively used in this test.
    /// </summary>
    private static String[] getArray()
    {
        return new String[] { "6", "4", "1", "2", "5", "3" };
    }

    /// <summary>
    /// Returns an instance of string type missing in an array returned by <see cref="GetArray"/>.
    /// </summary>
    private static String getMissingItem()
    {
        return "7";
    }

    /// <summary>
    /// Gets the first item of the test array.
    /// </summary>
    private static String getFirstItem() { return G_ARRAY[0]; }

    private static class StringOrdinalComparer implements Comparator<String>
    {
        /// <summary>
        /// Use the singleton instance instead.
        /// </summary>
        private StringOrdinalComparer()
        {
        }

        public int compare(String x, String y)
        {
            return msString.compareOrdinal(x, y);
        }

        /// <summary>
        /// Do not create instances of this class. Use this instance instead.
        /// </summary>
        public static /*final*/ Comparator<String> DEFAULT = new StringOrdinalComparer();
    }

    // Calculate once, use multiple times.
    private static /*final*/ String[] G_ARRAY = getArray();
    private static /*final*/ String G_MISSING_ITEM = getMissingItem();
}


