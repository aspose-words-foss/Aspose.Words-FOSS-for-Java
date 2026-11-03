// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/05/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.CssEscape;


/// <summary>
/// Base class for all selectors that select attribute name or value.
/// </summary>
abstract class CssAttributeSelector extends CssSimpleSelector
{
    protected CssAttributeSelector(
        CssNamespace attributeNamespace,
        String attributeLocalName,
        String selectorOperator,
        String value)
    {
        Debug.assertTrue(attributeNamespace != null);
        Debug.assertTrue(StringUtil.hasChars(attributeLocalName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(attributeLocalName));
        // If the "operator" parameter is specified, the "value" parameter must must be non-null but it may be an empty
        // string. If "operator" is empty, "value" must be null.
        Debug.assertTrue(StringUtil.hasChars(selectorOperator) == (value != null));

        setAttributeNamespace(attributeNamespace);
        setAttributeLocalName(attributeLocalName);
        mSelectorOperator = selectorOperator;
        setValue(value);
    }

    /*override*/ CssSelectorSpecificity getSpecificity() { return G_SPECIFICITY; }

    /*override*/ String toCss()
    {
        String escapedName = CssEscape.escapeIdentifier(getAttributeLocalName());
        String prefixedName = getAttributeNamespace().getPrefixedName(escapedName, CssNamespace.EMPTY.getName());

        if (StringUtil.hasChars(mSelectorOperator))
        {
            String escapedValue = CssEscape.escapeDoubleQuotedString(getValue());
            return "[" + prefixedName + mSelectorOperator + "\"" + escapedValue + "\"]";
        }
        else
        {
            return "[" + prefixedName + "]";
        }
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return StringUtil.hasChars(mSelectorOperator)
            ? getAttributeLocalName() + mSelectorOperator + getValue()
            : getAttributeLocalName();
    }

    protected CssNamespace getAttributeNamespace() { return mAttributeNamespace; }; private void setAttributeNamespace(CssNamespace value) { mAttributeNamespace = value; };

    private CssNamespace mAttributeNamespace;

    protected String getAttributeLocalName() { return mAttributeLocalName; }; private void setAttributeLocalName(String value) { mAttributeLocalName = value; };

    private String mAttributeLocalName;

    protected String getValue() { return mValue; }; private void setValue(String value) { mValue = value; };

    private String mValue;

    private static /*final*/ CssSelectorSpecificity G_SPECIFICITY = new CssSelectorSpecificity(0, 1, 0);

    private /*final*/ String mSelectorOperator;
}

