// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/08/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Collect new subsidiary content parts are generated during export. 
/// After export will contain the array of <see cref="SubsidiaryContentPart" /> objects.
/// </summary>
class SubsidiaryContentPartCollector
{
    SubsidiaryContentPartCollector()
    {
        mParts = new ArrayList<SubsidiaryContentPart>();
    }

    /// <summary>
    /// Adds new item to the list of subsidiary content parts.
    /// </summary>
    void addPart(SubsidiaryContentPart part)
    {
        msArrayList.add(mParts, part);
    }

    ArrayList<SubsidiaryContentPart> getParts() { return mParts; }

    private /*final*/ ArrayList<SubsidiaryContentPart> mParts;
}

