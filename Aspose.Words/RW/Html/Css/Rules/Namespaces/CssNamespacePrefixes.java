// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;
import com.aspose.words.ref.Ref;


/// <summary>
/// Maps prefixes of declared CSS namespaces to corresponding namespace selectors.
/// </summary>
class CssNamespacePrefixes
{
    /// <summary>
    /// Gets a namespace selector for the specified prefix.
    /// </summary>
    /// <param name="prefix">A namespace prefix. Must not be <c>null</c> or empty.</param>
    /// <returns>
    /// The CSS namespace selector that selects the namespace with the specified prefix.
    /// If the namespace prefix is not declared, the method returns <c>null</c>.
    /// </returns>
    CssNamespace getNamespace(String prefix)
    {
        Debug.assertTrue(StringUtil.hasChars(prefix));

        return DictionaryExtension.getValueOrNull(mPrefixToNamespaceMap, prefix);
    }

    /// <summary>
    /// Gets the current default namespace for elements that have no namespace declaration.
    /// </summary>
    /// <returns>
    /// The default namespace, if it has been set. Otherwise, <see cref="CssNamespace.Any"/> is returned.
    /// </returns>
    CssNamespace getDefaultNamespaceForElements()
    {
        CssNamespace result = null;
        Ref<CssNamespace> referenceToResult = new Ref<CssNamespace>(result);
        boolean outRefCondition0 = msDictionary.tryGetValue(mPrefixToNamespaceMap, "", /*out*/ referenceToResult);
        result = referenceToResult.get();
        if (outRefCondition0)
        {
            return result;
        }
        return CssNamespace.ANY;
    }

    /// <summary>
    /// Remembers a CSS namespace declaration.
    /// </summary>
    /// <param name="prefix">
    /// The prefix of the declared CSS namespace. Must not be <c>null</c>. If the prefix is empty, the default namespace
    /// will be set. This prefix is local to rules of the stylesheet that is being parsed.
    /// </param>
    /// <param name="ns">A specific namespace that corresponds to the prefix. The prefix stored in the namespace
    /// is shared among rules of all stylesheets that are parsed by the same instance of the CSS parser.</param>
    void setNamespace(String prefix, CssNamespace ns)
    {
        Debug.assertTrue(prefix != null);
        Debug.assertTrue(ns != null);
        Debug.assertTrue(ns.isSpecific());

        mPrefixToNamespaceMap.put(prefix, ns);
    }

    /// <summary>
    /// Maps declared CSS namespace prefixes to namespace names. Keys are not empty.
    /// </summary>
    private /*final*/ HashMap<String, CssNamespace> mPrefixToNamespaceMap = new HashMap<String, CssNamespace>();
}

