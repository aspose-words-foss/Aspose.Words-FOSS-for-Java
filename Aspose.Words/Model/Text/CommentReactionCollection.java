// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Represents the 'reactions' element of the CT_CommentReactions complex type defined in §2.1.1.1 of [MS-OREACTXML]
/// that specifies information for the reactions to a comment. It is the root element in an extension within the
/// extension list of a CommentsExtensible part.
/// </summary>
class CommentReactionCollection implements Iterable<CommentReaction>
{
    /// <summary>
    /// Adds the specified comment reaction to this collection.
    /// </summary>
    void add(CommentReaction reaction)
    {
        msArrayList.add(mReactions, reaction);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<CommentReaction> iterator()
    {
        return mReactions.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Gets the number of items in the collection.
    /// </summary>
    int getCount() { return mReactions.size(); }

    /// <summary>
    /// Returns a comment reaction by an index.
    /// </summary>
    CommentReaction get(int index) { return mReactions.get(index); }

    private /*final*/ ArrayList<CommentReaction> mReactions = new ArrayList<CommentReaction>();
}

