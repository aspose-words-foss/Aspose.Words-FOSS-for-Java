// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/01/2018 by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msArray;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.Collections;
import java.util.Comparator;
import com.aspose.ms.System.msInt;


@Test
public class TestComparableGenerics
{
    @Test
    public void testArraySort()
    {
        ArrayList<TestComparableGenerics.CompareObject> compareArrayList = getCompareArrayList();
        CompareObject[] compareArray = msArrayList.toArray(compareArrayList, new TestComparableGenerics.CompareObject[0]);
        msArray.sort(compareArray);
        String result = "";
        for (int i = 0; i < compareArray.length; i++)
            result = result + compareArray[i].getCode();

        msAssert.areEqual(result, "7 6 4 3 2 ");

        msArray.sort(compareArray, new ReverseObjectComparer());
        result = "";
        for (int i = 0; i < compareArray.length; i++)
            result = result + compareArray[i].getCode();

        msAssert.areEqual(result, "2 3 4 6 7 ");
    }

    @Test
    public void testListSort()
    {
        ArrayList<TestComparableGenerics.CompareObject> compareList = getCompareList();
        Collections.sort(compareList);
        String result = "";
        for (int i = 0; i < compareList.size(); i++)
            result = result + compareList.get(i).getCode();

        msAssert.areEqual(result, "7 6 4 3 2 ");

        Collections.sort(compareList, new ReverseObjectComparer());
        result = "";
        for (int i = 0; i < compareList.size(); i++)
            result = result + compareList.get(i).getCode();

        msAssert.areEqual(result, "2 3 4 6 7 ");
    }

    private ArrayList<TestComparableGenerics.CompareObject> getCompareArrayList()
    {
        ArrayList<TestComparableGenerics.CompareObject> newArray = new ArrayList<TestComparableGenerics.CompareObject>();
        for (int index : mIndexes)
            msArrayList.add(newArray, new CompareObject(index));

        return newArray;
    }

    private ArrayList<TestComparableGenerics.CompareObject> getCompareList()
    {
        ArrayList<TestComparableGenerics.CompareObject> newList = new ArrayList<TestComparableGenerics.CompareObject>();
        for (int index : mIndexes)
            msArrayList.add(newList, new CompareObject(index));

        return newList;
    }


    private int[] mIndexes = { 3, 4, 2, 6, 7 };

    private static class ReverseObjectComparer implements Comparator<TestComparableGenerics.CompareObject>
    {
        public int compare(CompareObject x, CompareObject y)
        {
            return -x.compareTo(y);
        }
    }

    private static class CompareObject implements Comparable<TestComparableGenerics.CompareObject>
    {
        CompareObject(int index)
        {
            mCode = index + " ";
            mIndex = index;
        }

        public int compareTo(CompareObject obj)
        {
            if (obj == null)
                return 1;

            return -msInt.compareTo(getIndex(), obj.getIndex());
        }

        String getCode() { return mCode; }

        int getIndex() { return mIndex; }

        private /*final*/ String mCode;
        private /*final*/ int mIndex;
    }
}

