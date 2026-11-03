// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/01/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToIntBidirectionalMap;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Collects imported styles istds from particular sources.
/// </summary>
class ImportedStylesIstdsCollector
{
    IntToIntBidirectionalMap getImportedStylesIstds(String sourceFileName)
    {
        IntToIntBidirectionalMap importedIstds = mCache.get(sourceFileName);
        if (importedIstds == null)
        {
            importedIstds = new IntToIntBidirectionalMap();
            mCache.set(sourceFileName, importedIstds);
        }

        return importedIstds;
    }

    private /*final*/ StringToObjDictionary<IntToIntBidirectionalMap> mCache = new StringToObjDictionary<IntToIntBidirectionalMap>();
}
