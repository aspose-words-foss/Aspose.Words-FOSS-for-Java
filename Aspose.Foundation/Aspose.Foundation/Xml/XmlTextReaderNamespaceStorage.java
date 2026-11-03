// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2017 by Dmitry Sokolov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;


/// <summary>
/// Implements storage for XML namespaces.
/// </summary>
public class XmlTextReaderNamespaceStorage
{
    public XmlTextReaderNamespaceStorage(XmlTextReaderNamespaceStorage parent)
    {
        mParent = parent;
    }

    public Map<String, String> convertTo()
    {
        Map<String, String> namesapcesMap = new HashMap<String, String>();

        for (Map.Entry<String, String> item : (Iterable<Map.Entry<String, String>>) mNamespaces)
        {
            msDictionary.add(namesapcesMap, item.getKey(), item.getValue());
        }

        return namesapcesMap;
    }

    boolean isPrefixDefined(String prefix)
    {
        XmlTextReaderNamespaceStorage storage = this;
        while (storage != null)
        {
            if (storage.mNamespaces.containsKey(prefix))
                return true;

            storage = storage.mParent;
        }
        return false;
    }

    SortedStringListGeneric<String> getNamespaces() { return mNamespaces; }

    private /*final*/ SortedStringListGeneric<String> mNamespaces = new SortedStringListGeneric<String>();
    private /*final*/ XmlTextReaderNamespaceStorage mParent;
}

