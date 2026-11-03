// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2012 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;


/// <summary>
/// Sets the bookmark value contained in field result.
/// </summary>
class FieldUpdateActionSetBookmarkValue extends FieldUpdateAction
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="field"></param>
    /// <param name="bookmarkName"></param>
    /// <param name="bookmarkValue"></param>
    FieldUpdateActionSetBookmarkValue(Field field, String bookmarkName, String bookmarkValue)
    {
    	this(
            field,
            bookmarkName,
            (bookmarkValue == null) ? null : new FieldResult(new StringConstant(bookmarkValue)));
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="field"></param>
    /// <param name="bookmarkName"></param>
    /// <param name="bookmarkValue"></param>
    FieldUpdateActionSetBookmarkValue(Field field, String bookmarkName, IFieldResult bookmarkValue)
    {
    	super(field);
	
        mBookmarkName = bookmarkName;
        mBookmarkValue = bookmarkValue;
    }

    /*override*/ void perform() throws Exception
    {
        if (!StringUtil.hasChars(mBookmarkName) || (mBookmarkValue == null))
            return;

        // SPEED Get a bookmark from a cache.
        Bookmark bookmark = FieldUtil.getCachedBookmark(getField(), mBookmarkName);
        if (bookmark != null)
        {
            // Remove old bookmark value.
            bookmark.remove();
        }

        NodeRange resultRange = mBookmarkValue.getFieldResultRange();
        if (resultRange == null)
        {
            // If result range is not defined, use text representation of the result.
            String resultString = mBookmarkValue.getFieldResultValue().getValueString();

            // We use TextResultApplier in this case to handle multi-paragraph and RTL text properly.
            // Note that old result removal and separator insurance are performed inside TextResultApplier.ApplyResult().
            TextResultApplier resultApplier = new TextResultApplier(getField(), resultString, false);
            resultApplier.applyResult();
        }
        else
        {
            // Remove old field result.
            getField().removeFieldResult();

            // Forcibly insert field separator here.
            getField().ensureSeparator(true);

            // Copy result range to field result.
            // Note that we do not use NodeRangeResultApplier here as bookmark values contain all child field nodes
            // in contrast to field results, which contain child field results only.
            NodeCopier.copy(resultRange, getField().getEnd(), new FieldTokenDecoderNodeModifier(resultRange), null,
                NodeCopierOptions.USE_SOURCE_START_ANCESTOR_PR | NodeCopierOptions.SKIP_CROSS_STRUCTURE_ANNOTATIONS |
                    NodeCopierOptions.CLONE_NODE);
        }

        // Add bookmark marks to field result.
        getField().getSeparator().insertNext(new BookmarkStart(getDocument(), mBookmarkName));
        getField().getEnd().insertPrevious(new BookmarkEnd(getDocument(), mBookmarkName));
    }

    private /*final*/ String mBookmarkName;
    private /*final*/ IFieldResult mBookmarkValue;
}

