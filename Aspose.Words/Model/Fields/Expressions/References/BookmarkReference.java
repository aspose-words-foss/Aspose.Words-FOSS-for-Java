// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a reference to a bookmark.
/// </summary>
class BookmarkReference implements IExecutionItem
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="context">The field context object.</param>
    /// <param name="bookmarkName">The name of a bookmark to refer to.</param>
    BookmarkReference(FieldContext context, String bookmarkName)
    {
        mContext = context;
        mBookmarkName = bookmarkName;
    }

    public Constant /*IExecutionItem.*/evaluate(ConstantStack calculationStack) throws Exception
    {
        if (getBookmark() == null)
            return ErrorConstant.createBookmarkError(mBookmarkName);

        String bookmarkText = getBookmark().getText(true);

        DoubleConstant result = ExpressionEvaluator.evaluateReferenceExpression(mContext, bookmarkText);
        if (result != null)
            return result;

        if (mContext.getField().getType() == FieldType.FIELD_FORMULA)
            return new DoubleConstant(0.0);

        return new StringConstant(bookmarkText);
    }

    private Bookmark getBookmark() throws Exception { return FieldUtil.getCachedBookmark(mContext.getField(), mBookmarkName); }

    private /*final*/ FieldContext mContext;
    private /*final*/ String mBookmarkName;
}

