// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2024 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msStack;
import java.lang.Class;


/// <summary>
/// Extensions methods for the <see cref="Stack{T}"/> class.
/// </summary>
public /*static*/ class StackExtension
{
	/* Simulation of static class by using private constructor */
	private StackExtension()
	{}

    /// <summary>
    /// Returns the object at the top of the <paramref name="stack"/> if it exists. Otherwise <c>null</c>.
    /// </summary>
    public static <T extends Object> T top(/*this*/ msStack<T> stack)
    {
        return stack.getCount() != 0
            ? stack.peek()
            : null;
    }

    /// <summary>
    /// Removes and returns the object at the top of the <paramref name="stack"/>
    /// if it exists and can be assigned to <typeparamref name="TSub"/> type. Otherwise <c>null</c>.
    /// </summary>
    public static <TBase extends Object> TBase popIfInstanceOf(/*this*/ msStack<TBase> stack, Class tSub)
    {
        TBase top = StackExtension.top(stack);
        return (top != null && top.getClass() == tSub)
            ? stack.pop()
            : null;
    }
}

