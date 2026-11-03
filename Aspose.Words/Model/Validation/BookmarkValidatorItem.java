// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/01/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// A data holder. Keeps info about both bookmark start and bookmark end nodes during document validation.
/// Not a struct because stored in a hashtable as a value, it requires a by reference object.
/// </summary>
class BookmarkValidatorItem
{
    BookmarkValidatorItem(BookmarkStart start, /*StoryType*/int storyType)
    {
        Debug.assertTrue(start != null);

        mBookmarkStart = start;
        mBookmarkStoryType = storyType;
    }

    /// <summary>
    /// Returns true, if there is only BookmarkStart in this instance.
    /// </summary>
    boolean hasStartOnly()
    {
        if (getBookmarkEnd() != null)
            return false;

        // WORDSNET-16397 Check BookmarkStart is not removed.
        if (getBookmarkStart().getParentNode() == null)
            return false;

        return true;
    }

    /// <summary>
    /// Obtains bookmark validation key.
    /// </summary>
    static String getKey(IBookmarkNode bookmarkNode)
    {
        Debug.assertTrue(bookmarkNode != null);

        Node bookmark = (Node)bookmarkNode;
        GlossaryDocument glossary = ms.as(bookmark.getDocument(), GlossaryDocument.class);

        if (glossary == null)
            return bookmarkNode.getName();

        CompositeNode buildingBlock = bookmark.getAncestor(NodeType.BUILDING_BLOCK);
        String buildingBlockId = buildingBlock != null
            ? Integer.toString(buildingBlock.hashCode())
            : "";

        final String KEY_PATTERN = "{0};{1}";

        return StringUtil.hasChars(buildingBlockId)
            ? msString.format(KEY_PATTERN, buildingBlockId, bookmarkNode.getName())
            : bookmarkNode.getName();
    }

    BookmarkStart getBookmarkStart() { return mBookmarkStart; };

    private BookmarkStart mBookmarkStart;
    BookmarkEnd getBookmarkEnd() { return mBookmarkEnd; }; void setBookmarkEnd(BookmarkEnd value) { mBookmarkEnd = value; };

    private BookmarkEnd mBookmarkEnd;
    /*StoryType*/int getBookmarkStoryType() { return mBookmarkStoryType; };

    private /*StoryType*/int mBookmarkStoryType;
}

