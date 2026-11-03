// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/09/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DictionaryExtension;
import java.util.HashMap;
import java.util.Map;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Iterator;


/// <summary>
/// Provides typed access to <see cref="FootnoteSeparator"/> nodes of a document.
/// </summary>
/// <dev>
/// Map of special footnote/endnote separators defined in a document.
/// Key is <see cref="FootnoteSeparatorType"/>, Value is <see cref="FootnoteSeparator"/>.
/// </dev>
public class FootnoteSeparatorCollection implements Iterable<FootnoteSeparator>, Cloneable
{
    /// <summary>
    /// Retrieves a <see cref="FootnoteSeparator"/> of the specified type.
    /// </summary>
    /// <remarks>
    /// Returns <c>null</c> if the footnote/endnote separator of the specified type is not found.
    /// </remarks>
    public FootnoteSeparator getByFootnoteSeparatorType(/*FootnoteSeparatorType*/int separatorType) { return DictionaryExtension.getValueOrNull(mItems, separatorType); }

    void add(FootnoteSeparator footnoteSeparator)
    {
        mItems.put(footnoteSeparator.getSeparatorType(), footnoteSeparator);
    }

    /// <summary>
    /// Makes a deep clone of the collection.
    /// Suitable only when copying a complete document.
    /// </summary>
    FootnoteSeparatorCollection deepClone(DocumentBase dstDoc, INodeCloningListener nodeCloningListener)
    {
        FootnoteSeparatorCollection lhs = (FootnoteSeparatorCollection)this.memberwiseClone();
        lhs.mItems = new HashMap<Integer, FootnoteSeparator>();
        for (Map.Entry<Integer, FootnoteSeparator> pair : mItems.entrySet())
        {
            FootnoteSeparator separator = pair.getValue();

            FootnoteSeparator dstSeparator = (FootnoteSeparator)separator.deepClone(true, nodeCloningListener);
            dstSeparator.setDocument(dstDoc);

            msDictionary.add(lhs.mItems, pair.getKey(), dstSeparator);
        }

        return lhs;
    }

    int getCount() { return mItems.size(); }

    /// <summary>
    /// Indicates that all separators in collection have default formatting.
    /// </summary>
    boolean isDefault()
    {
        for (FootnoteSeparator footnoteSeparator : this)
            if (!footnoteSeparator.isDefault())
                return false;

        return true;
    }

    public Iterator<FootnoteSeparator> iterator()
    {
        return mItems.values().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private HashMap<Integer, FootnoteSeparator> mItems = new HashMap<Integer, FootnoteSeparator>();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

