// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Represents a stack of constants.
/// </summary>
class ConstantStack
{
    /// <summary>
    /// Pushes a constant into the stack.
    /// </summary>
    /// <param name="constant">The constant to push.</param>
    void push(Constant constant)
    {
        Debug.assertTrue(constant != null);
        mStack.push(constant);
    }

    /// <summary>
    /// Pops a constant from the stack.
    /// </summary>
    /// <returns>The retrieved constant.</returns>
    Constant pop()
    {
        if (mStack.getCount() == 0)
            return ErrorConstant.createSyntaxError();
        return mStack.pop();
    }

    /// <summary>
    /// Gets the number of constants contained in this stack.
    /// </summary>
    int getCount() { return mStack.getCount(); }

    private /*final*/ msStack<Constant> mStack = new msStack<Constant>();
}

