// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/01/2018 by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.Iterator;


@Test
public class TestGenericCollection
{
    @Test
    public void testEnumerator()
    {
        String[] array = new String[] { "abc", "bcd", "cde" };
        TestCollection coll = new TestCollection(array);
        msAssert.isNotNull(coll);
        msAssert.areEqual(coll.getCount(), 3);

        Iterator<String> enumerator = coll.iterator();
        for (int index = 0; enumerator.hasNext(); index++)
        {
            msAssert.areEqual(enumerator.next(), array[index]);
        }
        msAssert.areEqual(enumerator.hasNext(), false);
    }

    @Test
    public void testCollectionEnumerator()
    {
        String[] array = new String[] { "abc", "bcd", "cde" };
        TestCollection coll = new TestCollection(array);
        for (int index = 0; coll.hasNext(); index++)
        {
            msAssert.areEqual(coll.next(), array[index]);
        }
        msAssert.areEqual(coll.hasNext(), false);
        coll.reset();
        msAssert.areEqual(coll.hasNext(), true);
    }

    @Test
    public void testForeach()
    {
        String[] array = new String[] { "abc", "bcd", "cde" };
        TestCollection coll = new TestCollection(array);

        int index = 0;
        for (String name : coll)
        {
            msAssert.areEqual(name, array[index]);
            index++;
        }
    }

    @Test
    public void testForeachEnumerable()
    {
        String[] array = new String[] { "abc", "bcd", "cde" };
        TestCollection coll = new TestCollection(array);

        Iterable<String> enumerable = coll;
        int index = 0;
        for (String name : enumerable)
        {
            msAssert.areEqual(name, array[index]);
            index++;
        }
    }

    private static class TestCollection implements Iterator<String>, Iterable<String>
    {
        public TestCollection(String[] collection)
        {
            mArray = collection;
        }

        public int getCount() { return mArray.length; }

        //>>>>>>>> #region  Implementation of IEnumerator

        public boolean hasNext()
        {
            if (mIndex >= mArray.length - 1)
                return false;

            mIndex++;
            return true;
        }

        public void reset()
        {
            mIndex = -1;
        }

        public String next() { return mArray[mIndex]; }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        public void /*IDisposable.*/dispose()
        {
            // Do nothing.
        }

        //<<<<<<<< #endregion 

        //>>>>>>>> #region  Implementation of IEnumerable

        public Iterator<String> iterator()
        {
            return this;
        }

        //JAVA-deleted non-generic namesake:
    //Iterator iterator()

        //<<<<<<<< #endregion 

        private String[] mArray;
        private int mIndex = -1;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }
}

