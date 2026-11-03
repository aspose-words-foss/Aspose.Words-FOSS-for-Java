// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/02/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.SortedIntegerListGeneric;


/// <summary>
/// Implements collection for HtmlBlock items.
/// </summary>
/// <remarks>
/// AM. This collection MUST be written sorted by HtmlBlock.Id for DOC and RTF formats. 
/// That why it's a sorted list.
/// </remarks>
class HtmlBlockCollection
{
    void add(HtmlBlock htmlBlock)
    {
        // WORDSNET-11139 Ignore items with duplicate ids.
        if (mBlocks.get(htmlBlock.getId()) != null)
            return;

        mBlocks.add(htmlBlock.getId(), htmlBlock);
        htmlBlock.setHtmlBlockCollection(this);
    }

    void add(HtmlBlockCollection blocks)
    {
        for (int i = 0; i < blocks.getCount(); i++)
        {
            HtmlBlock block = blocks.getHtmlBlockByIndex(i);
            add(block);
        }
    }

    int indexOfKey(int id)
    {
        return mBlocks.indexOfKey(id);
    }

    /// <summary>
    /// Expands HTML related formatting specified in the source paragraph properties into the destination paragraph properties.
    /// </summary>
    void expand(ParaPr srcParaPr, ParaPr dstParaPr, boolean expandParent)
    {
        Object htmlBlockId = srcParaPr.getDirectAttr(ParaAttr.HTML_BLOCK_ID);

        if (htmlBlockId != null)
            getExpandedParaPr((/*int*/Integer)htmlBlockId, expandParent).expandTo(dstParaPr);
    }

    /// <summary>
    /// Expands HTML related formatting specified in the source table row properties into the destination row properties.
    /// </summary>
    void expand(TablePr dstTablePr, int htmlBlockId, boolean expandParent)
    {
        ParaPr dstParaPr = new ParaPr();
        getExpandedParaPr(htmlBlockId, expandParent).expandTo(dstParaPr);

        // Take in attention only margin attributes for a while.
        int startIndex = ParaAttr.HTML_MARGIN_LEFT;
        for (int i = startIndex; i <= ParaAttr.HTML_MARGIN_BOTTOM; ++i)
        {
            if (dstParaPr.get(i) != null)
                dstTablePr.set(i - startIndex + TableAttr.HTML_MARGIN_LEFT, dstParaPr.get(i));
        }
    }

    /// <summary>
    /// Removes HTML related formatting specified in the table row properties.
    /// </summary>
    void removeFormatting(TablePr tablePr)
    {
        // Take in attention only margin attributes for a while.
        for (int i = TableAttr.HTML_MARGIN_BOTTOM; i >= TableAttr.HTML_MARGIN_LEFT; --i)
        {
            if (tablePr.contains(i))
                tablePr.remove(i);
        }
    }

    /// <summary>
    /// Creates a deep copy of this collection.
    /// </summary>
    HtmlBlockCollection deepClone()
    {
        HtmlBlockCollection lhs = new HtmlBlockCollection();

        for (int i = 0; i < mBlocks.getCount(); i++)
            lhs.add(this.getHtmlBlockByIndex(i).deepClone());

        return lhs;
    }

    HtmlBlock getHtmlBlockById(int id)
    {
        return mBlocks.get(id);
    }

    HtmlBlock getHtmlBlockByIndex(int index)
    {
        return mBlocks.getByIndex(index);
    }

    private ParaPr getExpandedParaPr(int htmlBlockId, boolean expandParent)
    {
        HtmlBlock htmlBlock = getHtmlBlockById(htmlBlockId);

        if(htmlBlock == null)
            return new ParaPr();

        if (expandParent)
        {
            ParaPr expParaPr = getExpandedParaPr(htmlBlock.getParentId(), true);

            // Expand formatting of this block.
            htmlBlock.getParaPr().expandTo(expParaPr);
            return expParaPr;
        }
        else
        {
            return htmlBlock.getParaPr();
        }
    }

    int getCount() { return mBlocks.getCount(); }

    private /*final*/ SortedIntegerListGeneric<HtmlBlock> mBlocks = new SortedIntegerListGeneric<HtmlBlock>();
}

