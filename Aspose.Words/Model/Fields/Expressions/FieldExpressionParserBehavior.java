// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;


/// <summary>
/// Performs behavior of expression parser related to field.
/// </summary>
class FieldExpressionParserBehavior implements IExpressionParserBehavior
{
    FieldExpressionParserBehavior()
    {
    	this(false);
	
    }

    FieldExpressionParserBehavior(boolean buildBookmarkReference)
    {
    	this(buildBookmarkReference, true, true);
	
    }

    FieldExpressionParserBehavior(boolean buildBookmarkReference, boolean resolveBookmarkReference, boolean resolveCellReference)
    {
        mBuildBookmarkReference = buildBookmarkReference;
        mResolveBookmarkReference = resolveBookmarkReference;
        mResolveCellReference = resolveCellReference;
    }

    public ArrayList<Object> /*IExpressionParserBehavior.*/normalizeExpression(ArrayList<Object> expression, FieldContext context) throws Exception
    {
        if (expression.size() == 0)
            return expression;

        if (!mResolveBookmarkReference && !mResolveCellReference)
            return expression;

        ArrayList<Object> result = new ArrayList<Object>(expression.size());

        for (Object item : expression)
        {
            StringConstant stringConstant = ms.as(item, StringConstant.class);
            if (stringConstant != null)
            {
                IExecutionItem reference = parseReference(stringConstant.getValueString(), context);
                if (reference != null)
                {
                    msArrayList.add(result, reference);
                    continue;
                }
            }

            msArrayList.add(result, item);
        }

        return result;
    }

    public ExecutionQueue /*IExpressionParserBehavior.*/normalizeExecutionQueue(ExecutionQueue queue, FieldContext context, String expression)
    {
        if (queue.getCount() == 1)
        {
            IExecutionItem item = queue.dequeue();

            StringConstant stringConstant = ms.as(item, StringConstant.class);
            if (stringConstant != null)
            {
                item = mBuildBookmarkReference
                    ? (IExecutionItem)new BookmarkReference(context, stringConstant.getValueString())
                    : new StringConstant(expression);
            }

            queue.enqueue(item);
        }

        return queue;
    }

    public boolean /*IExpressionParserBehavior.*/isDelimiter(char c, String expression, int position, StringBuilder builder)
    {
        return false;
    }

    /// <summary>
    /// Tries to parse the specified operand string to a reference.
    /// </summary>
    /// <param name="operand">The operand to parse.</param>
    /// <param name="context"></param>
    /// <returns>The parsed reference or null if the operand is not a reference.</returns>
    private IExecutionItem parseReference(String operand, FieldContext context) throws Exception
    {
        IExecutionItem cellReference = mResolveCellReference
            ? CellReference.tryParseAsRectangularRange(context, operand, false)
            : null;

        // WORDSNET-5569 Need to check the input parameter, it might be an empty string.
        // Bookmark name may not start with a digit, and should it happen, return the cell reference.
        if (StringUtil.hasChars(operand) && Character.isDigit(operand.charAt(0)))
        {
            return cellReference;
        }

        // That is the way Word works, 12812 shows that well. If a formula references "C2" from within a table cell
        // and there are both cell C2 and bookmark "C2" in the table, then bookmark reference has a higher priority.
        if (mResolveBookmarkReference && context.documentContainsBookmark(operand))
        {
            return new BookmarkReference(context, operand);
        }

        return cellReference;
    }

    private /*final*/ boolean mBuildBookmarkReference;
    private /*final*/ boolean mResolveBookmarkReference;
    private /*final*/ boolean mResolveCellReference;
}

