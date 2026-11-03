// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.CssEscape;


/// <summary>
/// Represents the CSS type selector. For example, 'div'.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#type-selectors
/// </remarks>
class CssTypeSelector extends CssSimpleSelector
{
    CssTypeSelector(CssNamespace elementNamespace, String elementLocalName)
    {
        Debug.assertTrue(elementNamespace != null);
        Debug.assertTrue(StringUtil.hasChars(elementLocalName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(elementLocalName));

        mNamespace = elementNamespace;
        mLocalName = elementLocalName;
    }

    /// <summary>
    /// Creates a selector that matches elements that are both matched by this selector and have the specified class.
    /// In other words, this method appends '.className' to this selector.
    /// </summary>
    CssCompoundSelector withClass(String className)
    {
        CssClassSelector classSelector = classSelector(className);
        return new CssCompoundSelector(this, classSelector);
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // Element names are lowercase.  
        return (msString.equals(element.getElementName(), mLocalName)) && mNamespace.matches(element.getElementNamespace());
    }

    /*override*/ CssSelectorSpecificity getSpecificity() { return G_SPECIFICITY; }

    /*override*/ String toCss()
    {
        String escapedElementName = CssEscape.escapeIdentifier(mLocalName);
        return mNamespace.getPrefixedName(escapedElementName, CssNamespace.ANY.getName());
    }

    CssNamespace getNamespace() { return mNamespace; }

    String getElementName() { return mLocalName; }

    protected /*override*/ String makePreferableStyleName()
    {
        return mLocalName;
    }

    private /*final*/ CssNamespace mNamespace;

    private /*final*/ String mLocalName;

    private static /*final*/ CssSelectorSpecificity G_SPECIFICITY = new CssSelectorSpecificity(0, 0, 1);
}

