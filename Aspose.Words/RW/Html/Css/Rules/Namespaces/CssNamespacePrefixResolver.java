// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/04/2020 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import java.util.HashMap;
import com.aspose.HashSetGeneric;
import com.aspose.words.ref.Ref;


/// <summary>
/// Generates unique prefixes for namespaces imported from different stylesheets.
/// </summary>
class CssNamespacePrefixResolver
{
    String getPrefix(String namespaceName, String suggestedPrefix)
    {
        // Note that the namespace name can be empty.
        Debug.assertTrue(namespaceName != null);

        String knownPrefix = null;
        Ref<String> referenceToKnownPrefix = new Ref<String>(knownPrefix);
        boolean outRefCondition0 = msDictionary.tryGetValue(mNamespaceToPrefixMap, namespaceName, /*out*/ referenceToKnownPrefix);
        knownPrefix = referenceToKnownPrefix.get();
        if (outRefCondition0)
        {
            return knownPrefix;
        }

        if (!StringUtil.hasChars(suggestedPrefix))
        {
            suggestedPrefix = "ns";
        }

        String uniquePrefix = suggestedPrefix;
        int counter = 1;
        while (mUsedPrefixes.contains(uniquePrefix))
        {
            uniquePrefix = suggestedPrefix + FormatterPal.intToStr(counter);
            ++counter;
        }

        msDictionary.add(mNamespaceToPrefixMap, namespaceName, uniquePrefix);
        mUsedPrefixes.add(uniquePrefix);

        return uniquePrefix;
    }

    String redefinePrefix(String oldNamespaceName, String newNamespaceName)
    {
        String prefix = mNamespaceToPrefixMap.get(oldNamespaceName);
        msDictionary.remove(mNamespaceToPrefixMap, oldNamespaceName);
        msDictionary.add(mNamespaceToPrefixMap, newNamespaceName, prefix);
        return prefix;
    }

    boolean isRegistered(String namespaceName, String prefix)
    {
        String usedPrefix = null;
        Ref<String> referenceToUsedPrefix = new Ref<String>(usedPrefix);
        boolean outRefCondition1 = !msDictionary.tryGetValue(mNamespaceToPrefixMap, namespaceName, /*out*/ referenceToUsedPrefix);
        usedPrefix = referenceToUsedPrefix.get();
        if (outRefCondition1)
        {
            return false;
        }
        return msString.equals(usedPrefix, prefix);
    }

    private /*final*/ HashMap<String, String> mNamespaceToPrefixMap = new HashMap<String, String>();

    private /*final*/ HashSetGeneric<String> mUsedPrefixes = new HashSetGeneric<String>();
}

