// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/01/2018 by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import org.testng.annotations.Test;
import java.util.LinkedList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestQueueGenerics
{
    @Test
    public void testCtor()
    {
        LinkedList<String> queue = new LinkedList<String>();
        msAssert.isNotNull(queue);
        msAssert.areEqual(queue.size(), 0);

        queue.addLast("cde");
        queue.addLast("abc");
        queue.addLast("bcd");

        msAssert.areEqual(queue.size(), 3);

        msAssert.areEqual(queue.removeFirst(), "cde");
        msAssert.areEqual(queue.removeFirst(), "abc");
        msAssert.areEqual(queue.removeFirst(), "bcd");

        msAssert.areEqual(queue.size(), 0);
    }

    @Test
    public void testCtor2()
    {
        LinkedList<String> queue = new LinkedList<String>();
        msAssert.isNotNull(queue);
        msAssert.areEqual(queue.size(), 0);

        queue = new LinkedList<String>();
        msAssert.isNotNull(queue);
        msAssert.areEqual(queue.size(), 0);
    }

    @Test
    public void testCount()
    {
        LinkedList<String> queue = new LinkedList<String>();
        msAssert.areEqual(queue.size(), 0);

        queue.addLast("cde");
        msAssert.areEqual(queue.size(), 1);

        queue.addLast("abc");
        msAssert.areEqual(queue.size(), 2);

        queue.addLast("bcd");
        msAssert.areEqual(queue.size(), 3);

        queue = getPopulatedQueue(50);
        msAssert.areEqual(queue.size(), 50);
    }


    @Test
    public void testForeach()
    {
        LinkedList<String> queue = getPopulatedQueue(3);

        String result = "";
        for (String name : queue)
        {
            result = msString.plusEqOperator(result, name);
        }

        msAssert.areEqual(result, " val_0 val_1 val_2");
        msAssert.areEqual(queue.size(), 3);
    }

    private static LinkedList<String> getPopulatedQueue(int size)
    {
        LinkedList<String> qu = new LinkedList<String>();
        for (int i = 0; i < size; i++)
            qu.addLast(" val_" + i);
        return qu;
    }
}

