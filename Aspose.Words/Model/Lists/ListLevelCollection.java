// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/05/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;


/// <summary>
/// A collection of list formatting for each level in a list.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-lists/">Working with Lists</a> documentation article.</para>
/// </summary>
public class ListLevelCollection implements Iterable<ListLevel>, Cloneable
{
    /// <summary>
    /// Ctor. Used when loading a document from RTF.
    /// </summary>
    ListLevelCollection()
    {
        mItems = new ArrayList<ListLevel>();
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="document">List levels require an owner document.</param>
    /// <param name="listType">Specifies how many list levels to create.</param>
    ListLevelCollection(DocumentBase document, /*ListType*/int listType)
    {
        int levelCount = getExpectedLevelCount(listType);
        mItems = new ArrayList<ListLevel>(levelCount);

        for (int levelIndex = 0; levelIndex < levelCount; levelIndex++)
            msArrayList.add(mItems, new ListLevel(document, levelIndex));
    }

    /// <summary>
    /// Adds a list level to the document. Used when loading a document from RTF.
    /// </summary>
    /// <param name="listLevel"></param>
    void add(ListLevel listLevel)
    {
        msArrayList.add(mItems, listLevel);
    }
    
    /// <summary>
    /// WORDSNET-14420 Corrects (adds or removes) the number of levels in this collection to match the specified list type.
    /// </summary>
    void fixUpLevelCount(/*ListType*/int listType, DocumentBase doc)
    {
        int expectedLevelCount = getExpectedLevelCount(listType);

        // Remove excess levels if we have any.
        while (mItems.size() > expectedLevelCount)
            mItems.remove(mItems.size() - 1);

        // Add more levels if required.
        while (mItems.size() < expectedLevelCount)
            add(new ListLevel(doc, mItems.size()));
    }

    /// <summary>
    /// Gets the number of levels for the specified list type.
    /// </summary>
    private static int getExpectedLevelCount(/*ListType*/int listType)
    {
        switch (listType)
        {
            case ListType.SINGLE_LEVEL:
                return 1;
            case ListType.HYBRID_MULTI_LEVEL:
            case ListType.MULTI_LEVEL:
                return ListLevel.MAX_LEVELS;
            default:
                throw new IllegalStateException("Unknown list type.");
        }
    }

    /// <summary>
    /// Gets the enumerator object that will enumerate levels in this list.
    /// </summary>
    public Iterator<ListLevel> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// In some probably corrupted documents index can be greater than the number of levels,
    /// I silently default to the last available level.
    /// </summary>
    ListLevel fetchListLevel(int level)
    {
        // WORDSNET-23784 Should check the collection is not empty for resilience. 
        return (getCount() == 0) ? null : this.get(correctLevel(level));
    }

    /// <summary>
    /// In some probably corrupted documents index can be greater than the number of levels,
    /// I silently default to the last available level.
    /// </summary>
    int correctLevel(int level)
    {
        return Math.min(level, getCount() - 1);
    }

    /// <summary>
    /// Makes a deep copy of the list levels.
    /// Note that the paragraph styles that might be linked to the levels are not copied.
    /// </summary>
    ListLevelCollection deepClone(DocumentBase dstDocument)
    {
        ListLevelCollection lhs = (ListLevelCollection)memberwiseClone();

        lhs.mItems = new ArrayList<ListLevel>(mItems.size());
        for (ListLevel item : mItems)
            msArrayList.add(lhs.mItems, item.deepClone(dstDocument));

        return lhs;
    }

    /// <summary>
    /// Gets a list level by index.
    /// </summary>
    public ListLevel get(int index) { return mItems.get(index); }
    public void set(int index, ListLevel value) { mItems.set(index, value); }

    /// <summary>
    /// Gets the number of levels in this list.
    /// </summary>
    /// <remarks>
    /// <p>There could be 1 or 9 levels in a list.</p>
    /// </remarks>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Clears ListLevels collection.
    /// </summary>
    void clear()
    {
        mItems.clear();
    }

    private ArrayList<ListLevel> mItems;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

