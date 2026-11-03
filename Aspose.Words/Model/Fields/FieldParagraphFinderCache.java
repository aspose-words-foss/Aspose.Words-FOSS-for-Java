// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2018 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DictionaryExtension;
import java.util.HashMap;


/// <summary>
/// Contains info about last paragraph search for fields with <see cref="FieldParagraphFinder"/>.
/// </summary>
class FieldParagraphFinderCache
{
    /// <summary>
    /// Retrieves or creates a new search result cache object for a field.
    /// </summary>
    LastParagraphSearchResult ensureLastFieldParagraphResult(FieldStart fieldStart)
    {
        LastParagraphSearchResult results = DictionaryExtension.getValueOrNull(mCache, fieldStart);
        if (results == null)
        {
            results = new LastParagraphSearchResult();
            mCache.put(fieldStart, results);
        }

        return results;
    }

    private /*final*/ HashMap<FieldStart, LastParagraphSearchResult> mCache =
        new HashMap<FieldStart, LastParagraphSearchResult>();
}

