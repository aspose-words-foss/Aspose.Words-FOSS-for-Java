// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/04/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Sequentially counts encountered field chars indicating whether the current position is inside a field.
/// </summary>
class FieldCharCounter
{
    /// <summary>
    /// Ctor to create a counter that does not consider a parent field if any.
    /// </summary>
    FieldCharCounter()
    {
    	this(FieldCharCounterOptions.DEFAULT);
	
    }

    /// <summary>
    /// Ctor to create a counter that can be started within a parent field's result.
    /// </summary>
    /// <remarks>
    /// Only direct parent field can be considered but other ancestor fields can not.
    /// </remarks>
    FieldCharCounter(boolean isInFieldResult)
    {
    	this (isInFieldResult ? FieldCharCounterOptions.IN_FIELD_RESULT : FieldCharCounterOptions.DEFAULT);
	
    }

    FieldCharCounter(/*FieldCharCounterOptions*/int options)
    {
        if ((options & FieldCharCounterOptions.IN_FIELD_RESULT) == FieldCharCounterOptions.IN_FIELD_RESULT)
            mFieldStarts.push(null);

        mSafeMode = (options & FieldCharCounterOptions.SAFE_MODE) == FieldCharCounterOptions.SAFE_MODE;
    }

    /// <summary>
    /// If the specified node is of a field char type then counts it. Otherwise, does nothing.
    /// </summary>
    void visitNode(Node node)
    {
        if (!isValidFieldChar(node))
            return;

        switch (node.getNodeType())
        {
            case NodeType.FIELD_START:
            {
                mEnteredFieldCodeCount++;
                mFieldStarts.push((FieldStart)node);
                break;
            }
            case NodeType.FIELD_SEPARATOR:
            {
                reduceEnteredFieldCodeCount();
                break;
            }
            case NodeType.FIELD_END:
            {
                FieldEnd fieldEnd = (FieldEnd)node;
                if (!fieldEnd.hasSeparator())
                    reduceEnteredFieldCodeCount();

                reduceEnteredFieldCount();
                break;
            }
            default:
                break;
        }
    }

    static boolean isValidFieldChar(Node node)
    {
        FieldChar fieldChar = ms.as(node, FieldChar.class);

        if (fieldChar == null)
            return false;

        return !fieldChar.isDeleteRevision();
    }

    private void reduceEnteredFieldCodeCount()
    {
        mEnteredFieldCodeCount--;

        if (mEnteredFieldCodeCount < 0)
        {
            if (mSafeMode)
            {
                mEnteredFieldCodeCount = 0;
            }
            else
            {
                Debug.fail("mEnteredFieldCodeCount >= 0");
            }
        }
    }

    private void reduceEnteredFieldCount()
    {
        if (getFieldsDepth() > 0 || !mSafeMode)
            mFieldStarts.pop();
    }

    /// <summary>
    /// Gets a value indicating if the current position is inside a field code.
    /// </summary>
    boolean isInFieldCode() { return mEnteredFieldCodeCount != 0; }

    /// <summary>
    /// Gets a value indicating if the current position is inside a field result.
    /// </summary>
    boolean isInFieldResult() { return isInField() && !isInFieldCode(); }

    /// <summary>
    /// Gets a value indicating if the current position is inside a field.
    /// </summary>
    boolean isInField() { return getFieldsDepth() != 0; }

    FieldStart getCurrentField() { return StackExtension.top(mFieldStarts); }

    int getFieldsDepth() { return mFieldStarts.getCount(); }

    private int mEnteredFieldCodeCount;

    private /*final*/ msStack<FieldStart> mFieldStarts = new msStack<FieldStart>();
    private /*final*/ boolean mSafeMode;
}

