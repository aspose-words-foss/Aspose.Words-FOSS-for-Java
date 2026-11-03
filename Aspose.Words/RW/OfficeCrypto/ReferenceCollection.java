// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/03/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Provides collection for <see cref="Reference"/> objects and <see cref="IReferenceResolver"/> used to resolve references.
/// </summary>
class ReferenceCollection implements Iterable<Reference>
{
    ReferenceCollection(IReferenceResolver resolver)
    {
        mReferenceResolver = resolver;
    }

    void add(Reference reference)
    {
        msArrayList.add(mItems, reference);
        reference.setReferenceResolver(mReferenceResolver);
    }

    int getCount() { return mItems.size(); }

    public Iterator<Reference> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ IReferenceResolver mReferenceResolver;
    private /*final*/ ArrayList<Reference> mItems = new ArrayList<Reference>();
}

