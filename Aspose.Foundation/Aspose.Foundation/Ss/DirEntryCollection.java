// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Text.Encoding;
import java.util.ArrayList;


/// <summary>
/// Typed collection of structured storage directory entries.
/// </summary>
/// <remarks>
/// There is no load method here because reading directory is done sector by sector.
/// </remarks>
class DirEntryCollection
{
    DirEntry get(/*uint*/int index) { return (DirEntry)mItems.get((int)(index & 0xFFFFFFFFL)); }
    void set(/*uint*/int index, DirEntry value) { mItems.set((int)(index & 0xFFFFFFFFL), value); }

    int getCount() { return mItems.size(); }

    void add(DirEntry dirEntry)
    {
        msArrayList.add(mItems, dirEntry);
    }

    /// <summary>
    /// Finds an entry with the specified name among the direct children of the root storage.
    /// </summary>
    DirEntry getEntryInRoot(String name)
    {
        DirEntry child = getSafe(this.get(0), this.get(0).Child);
        return findEntryInSiblings(child, name, new SortedIntegerListGeneric<Boolean>());
    }

    private DirEntry findEntryInSiblings(DirEntry entry, String name, SortedIntegerListGeneric<Boolean> visitedEntries)
    {
        if (msString.equals(entry.Name, name))
            return entry;

        DirEntry left = getSafe(entry, entry.LeftSib);
        if ((left != null) && !visitedEntries.containsKey((int)(entry.LeftSib & 0xFFFFFFFFL)))
        {
            visitedEntries.add((int)(entry.LeftSib & 0xFFFFFFFFL), false);
            DirEntry result = findEntryInSiblings(left, name, visitedEntries);
            if (result != null)
                return result;
        }

        DirEntry right = getSafe(entry, entry.RightSib);
        if ((right != null) && !visitedEntries.containsKey((int)(entry.RightSib & 0xFFFFFFFFL)))
        {
            visitedEntries.add((int)(entry.RightSib & 0xFFFFFFFFL), false);
            DirEntry result = findEntryInSiblings(right, name, visitedEntries);
            if (result != null)
                return result;
        }

        return null;
    }

    /// <summary>
    /// Gets a dir entry if index is not a NoStream value and if index is within the range.
    /// Otherwise returns null.
    /// WORDSNET-15829 added checking that item's child is not item itself, so preventing endless looping.
    /// </summary>
    DirEntry getSafe(DirEntry parentEntry, /*uint*/int childIndex)
    {
        if ((childIndex != DirEntry.NO_STREAM) && ((childIndex & 0xFFFFFFFFL) < mItems.size()) && 
            (parentEntry != this.get(childIndex)))
            return this.get(childIndex);
        else
            return null;
    }

    /// <summary>
    /// Saves directory into a stream, pads it with empty directory entries to a sector boundary.
    /// </summary>
    /// <returns></returns>
    MemoryStream toMemoryStream() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        BinaryWriter writer = new BinaryWriter(stream, Encoding.getUnicode());

        for (/*uint*/int i = 0; (i & 0xFFFFFFFFL) < mItems.size(); i++)
            this.get(i).write(writer);

        //Pad with empty dir entries to the sector boundary.
        DirEntry emptyDirEntry = new DirEntry();
        while (stream.getLength() % Sector.SECTOR_SIZE != 0)
            emptyDirEntry.write(writer);

        return stream;
    }

    private /*final*/ ArrayList<DirEntry> mItems = new ArrayList<DirEntry>();
}

