// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/09/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of <see cref="VbaReference"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-vba-macros/">Working with VBA Macros</a> documentation article.</para>
/// </summary>
public final class VbaReferenceCollection implements Iterable<VbaReference>
{
    /// <summary>
    /// Initializes a new instance of the VbaReferenceCollection class for a specified VbaProject.
    /// </summary>
    VbaReferenceCollection(VbaProject vbaProject)
    {
        mVbaProject = vbaProject;
    }

    /// <summary>
    /// Removes the first occurrence of a specified <see cref="VbaReference"/> item from the collection. 
    /// </summary>
    public void remove(VbaReference item)
    {
        mReferences.remove(item);
        mVbaProject.markModified();
    }

    /// <summary>
    /// Removes the <see cref="VbaReference"/> element at the specified index of the collection.
    /// </summary>
    public void removeAt(int index)
    {
        mReferences.remove(index);
        mVbaProject.markModified();
    }

    /// <summary>
    /// Adds a specified VbaReference object to a collection.
    /// </summary>
    /// <param name="vbaReference"></param>
    void add(VbaReference vbaReference)
    {
        msArrayList.add(mReferences, vbaReference);
    }

    /// <summary>
    /// Makes a copy of the object.
    /// </summary>
    VbaReferenceCollection deepClone()
    {
        VbaReferenceCollection lhs = new VbaReferenceCollection(mVbaProject);

        for (VbaReference reference : mReferences)
            lhs.add(reference.deepClone());

        return lhs;
    }

    @ReservedForInternalUse @Deprecated
    public Iterator<VbaReference> /*IEnumerable<VbaReference>.*/iterator()
    {
        return mReferences.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Returns the number of VBA references in the collection.
    /// </summary>
    public int getCount() { return mReferences.size(); }

    /// <summary>
    /// Gets <see cref="VbaReference"/> object at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index of the reference to get.</param>
    public VbaReference get(int index) { return mReferences.get(index); }

    private /*final*/ ArrayList<VbaReference> mReferences = new ArrayList<VbaReference>();
    private /*final*/ VbaProject mVbaProject;
}

