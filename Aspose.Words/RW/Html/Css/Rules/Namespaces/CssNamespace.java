// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

/// <summary>
/// CSS namespace selector that selects CSS elements and attributes by their namespaces.
/// </summary>
class CssNamespace
{
    CssNamespace(String prefix, String namespaceName)
    {
        mPrefix = prefix;
        mName = namespaceName;
    }

    boolean matches(String namespaceName)
    {
        // Any namespace.
        if (getName() == null)
        {
            return true;
        }

        // Use ordinal comparison (case-sensitive). This also covers the empty namespace case.
        return msString.equals(getName(), namespaceName);
    }

    String getPrefixedName(String localName, String defaultNamespaceName)
    {
        if (msString.equals(getName(), defaultNamespaceName))
        {
            return localName;
        }

        if (getName() == null)
        {
            return "*|" + localName;
        }

        if (getName().length() == 0)
        {
            return "|" + localName;
        }

        return getPrefix() + "|" + localName;
    }

    String getPrefix() { return mPrefix; };

    private String mPrefix;

    String getName() { return mName; };

    private String mName;

    /// <summary>
    /// Gets a value indicating whether this namespace selector matches a specific namespace (not any namespace).
    /// </summary>
    boolean isSpecific()
    {
        // Note that the empty namespace, whose name is an empty string, is considered specific too.
        return getName() != null;
    }

    /// <summary>
    /// Gets a value indicating whether this namespace selector matches no namespace.
    /// </summary>
    boolean isEmpty() { return "".equals(getName()); }

    static /*final*/ CssNamespace ANY = new CssNamespace(null, null);

    static /*final*/ CssNamespace EMPTY = new CssNamespace(null, "");
}

