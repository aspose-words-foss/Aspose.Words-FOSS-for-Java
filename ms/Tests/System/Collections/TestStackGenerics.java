// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/01/2018 by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import org.testng.annotations.Test;
import com.aspose.ms.System.Collections.msStack;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestStackGenerics
{
    @Test
    public void testCtor()
    {
        msStack<String> stack = new msStack<String>();
        msAssert.isNotNull(stack);
        msAssert.areEqual(stack.getCount(), 0);

        stack.push("cde");
        stack.push("abc");
        stack.push("bcd");

        msAssert.areEqual(stack.getCount(), 3);

        msAssert.areEqual(stack.peek(), "bcd");
        msAssert.areEqual(stack.pop(), "bcd");
        msAssert.areEqual(stack.peek(), "abc");
        msAssert.areEqual(stack.pop(), "abc");
        msAssert.areEqual(stack.peek(), "cde");
        msAssert.areEqual(stack.pop(), "cde");

        msAssert.areEqual(stack.getCount(), 0);
    }

    @Test
    public void testCtor2()
    {
        msStack<String> stack = new msStack<String>();
        msAssert.isNotNull(stack);
        msAssert.areEqual(stack.getCount(), 0);

        stack = new msStack<String>();
        msAssert.isNotNull(stack);
        msAssert.areEqual(stack.getCount(), 0);
    }

    @Test
    public void testCount()
    {
        msStack<String> stack = new msStack<String>();
        msAssert.areEqual(stack.getCount(), 0);

        stack.push("cde");
        msAssert.areEqual(stack.getCount(), 1);

        stack.push("abc");
        msAssert.areEqual(stack.getCount(), 2);

        stack.push("bcd");
        msAssert.areEqual(stack.getCount(), 3);

        stack = getPopulatedStack(50);
        msAssert.areEqual(stack.getCount(), 50);
    }


    @Test
    public void testForeach()
    {
        msStack<String> stack = getPopulatedStack(3);

        String result = "";
        for (String name : stack)
        {
            result = msString.plusEqOperator(result, name);
        }

        msAssert.areEqual(result, " val_2 val_1 val_0");
        msAssert.areEqual(stack.getCount(), 3);
    }

    private static msStack<String> getPopulatedStack(int size)
    {
        msStack<String> st = new msStack<String>();
        for (int i = 0; i < size; i++)
            st.push(" val_" + i);
        return st;
    }
}

