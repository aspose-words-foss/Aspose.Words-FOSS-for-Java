// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of <see cref="CommentReactionInfo"/> instances.
/// </summary>
class CommentReactionInfoCollection implements Iterable<CommentReactionInfo>
{
    /// <summary>
    /// Adds the specified comment reaction info to this collection.
    /// </summary>
    void add(CommentReactionInfo reactionInfo)
    {
        msArrayList.add(mReactionInfos, reactionInfo);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<CommentReactionInfo> iterator()
    {
        return mReactionInfos.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Gets the number of items in the collection.
    /// </summary>
    int getCount() { return mReactionInfos.size(); }

    /// <summary>
    /// Returns a comment reaction info by an index.
    /// </summary>
    CommentReactionInfo get(int index) { return mReactionInfos.get(index); }

    private /*final*/ ArrayList<CommentReactionInfo> mReactionInfos = new ArrayList<CommentReactionInfo>();
}

