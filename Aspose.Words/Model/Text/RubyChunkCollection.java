// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.ArrayList;


/// <summary>
/// Represents collection of <see cref="RubyChunk"/> items.
/// </summary>
class RubyChunkCollection implements Iterable<RubyChunk>
{
    RubyChunk get(int index) { return mItems.get(index); }

    void add(RubyChunk item)
    {
        msArrayList.add(mItems, item);
    }

    RubyChunkCollection deepClone()
    {
        RubyChunkCollection clonedCollection = new RubyChunkCollection();
        for (RubyChunk chunk : mItems)
        {
            RubyChunk clonedChunk = new RubyChunk();
            clonedChunk.Text = chunk.Text;
            clonedChunk.RunPr = AttrCollectionExtension.deepClone(chunk.RunPr);

            clonedCollection.add(clonedChunk);
        }

        return clonedCollection;
    }

    void clear()
    {
        mItems.clear();
    }

    public Iterator<RubyChunk> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Text of all collected chunks.
    /// </summary>
    String getText()
    {
        StringBuilder sb = new StringBuilder();
        for (RubyChunk rubyChunk : mItems)
            msStringBuilder.append(sb, rubyChunk.Text);

        return sb.toString();
    }

    int getCount() { return mItems.size(); }

    private /*final*/ ArrayList<RubyChunk> mItems = new ArrayList<RubyChunk>();
}

