// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Represents a stack of operators.
/// </summary>
class OperatorStack
{
    /// <summary>
    /// Pushes a operator into the stack.
    /// </summary>
    /// <param name="op">The operator to push.</param>
    void push(Operator op)
    {
        Debug.assertTrue(op != null);
        mStack.push(op);
    }

    /// <summary>
    /// Pops a operator from the stack.
    /// </summary>
    /// <returns>The retrieved operator.</returns>
    Operator pop()
    {
        return mStack.pop();
    }

    /// <summary>
    /// Returns the operator from the top of the stack without removing it.
    /// </summary>
    /// <returns>The retrieved operator.</returns>
    Operator peek()
    {
        return mStack.peek();
    }

    /// <summary>
    /// Gets the number of operators contained in this stack.
    /// </summary>
    int getCount() { return mStack.getCount(); }

    private /*final*/ msStack<Operator> mStack = new msStack<Operator>();
}

