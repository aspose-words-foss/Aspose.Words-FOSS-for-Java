//Konstantin Sidorenko 08/08/2018

package com.aspose.ms.NUnit.Framework;

import org.testng.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class msCollectionAssert
{
    public static void areEqual(Iterable<String> expected, Iterable<String> actual)
    {
        Iterator<String> expectedArray = expected.iterator();
        Iterator<String> actualArray = actual.iterator();

        while (expectedArray.hasNext() && actualArray.hasNext())
            Assert.assertEquals(expectedArray.next(),actualArray.next());

        Assert.assertFalse(expectedArray.hasNext());
        Assert.assertFalse(actualArray.hasNext());
    }

    public static void areEqual(Collection expected, Collection actual)
    {
        areEqual(expected, actual, "");
    }

    public static void areEqual(Collection expected, Collection actual, String message)
    {
        Assert.assertEquals(expected, actual, message);
    }

    public static void areEqual(Object[] expected, Object[] actual)
    {
        areEqual(expected, actual, "");
    }

    public static void areEqual(Object[] expected, Object[] actual, String message)
    {
        Assert.assertEquals(expected, actual, message);
    }

    public static void areEqual(Object[] expected, Collection actual)
    {
        areEqual(expected, actual, "");
    }

    public static void areEqual(Object[] expected, Collection actual, String message)
    {
        Assert.assertEquals(Arrays.asList(expected), actual, message);
    }

    public static void areEqual(Collection expected, Object[] actual)
    {
        areEqual(expected, actual, "");
    }

    public static void areEqual(Collection expected, Object[] actual, String message)
    {
        Assert.assertEquals(expected, Arrays.asList(actual), message);
    }

    public static void areEqual(byte[] expected, byte[] actual)
    {
        areEqual(expected, actual, "");
    }

    public static void areEqual(byte[] expected, byte[] actual, String message)
    {
        Assert.assertEquals(expected, actual, message);
    }

    public static void areEqual(int[] expected, int[] actual)
    {
        Assert.assertEquals(expected, actual, "");
    }

    public static void areEqual(int[] expected, Collection actual)
    {
        areEqual(expected, actual, "");
    }

    public static void areEqual(int[] expected, Collection actual, String message)
    {
        Iterator<Integer> actualArray = actual.iterator();
        int index = 0;

        while (index < expected.length && actualArray.hasNext())
        {
            Assert.assertEquals(expected[index], actualArray.next().intValue(), message);
            index++;
        }

        Assert.assertFalse(actualArray.hasNext(), message);
    }

    public static void isEmpty(Collection collection)
    {
        Assert.assertTrue(collection.isEmpty());
    }

    public static void isEmpty(String[] collection)
    {
        Assert.assertTrue(collection.length == 0);
    }
}
