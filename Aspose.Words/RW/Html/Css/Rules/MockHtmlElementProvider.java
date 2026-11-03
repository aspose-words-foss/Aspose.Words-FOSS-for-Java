// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.SortedStringListGeneric;
import com.aspose.DictionaryExtension;
import com.aspose.W3CNamespaces;


/// <summary>
/// Implementation of IHtmlElementProvider for mocking and test purpose.
/// </summary>
class MockHtmlElementProvider implements IHtmlElementProvider
{
    MockHtmlElementProvider(String name)
    {
    	this(name, null);
	
    }

    MockHtmlElementProvider(String name, MockHtmlElementProvider parent)
    {
        Debug.assertTrue(StringUtil.hasChars(name));
        mName = name;
        mAttributes = new SortedStringListGeneric<String>(false);
        mChildren = new HtmlElementProviderCollection();

        if (parent != null)
        {
            mElementIndex = parent.mChildren.add(this);
            mParent = parent;
        }
        else
        {
            mElementIndex = -1;
        }
    }

    static MockHtmlElementProvider create(String name)
    {
        return new MockHtmlElementProvider(name);
    }

    static MockHtmlElementProvider create(String name, MockHtmlElementProvider parent)
    {
        return new MockHtmlElementProvider(name, parent);
    }

    public String getInnerText()
    {
        return "";
    }

    public String getAttributeValue(String attribName)
    {
        return DictionaryExtension.getValueOrNull(mAttributes, attribName);
    }

    public String getAttributeValue(String name, String def)
    {
        return mAttributes.containsKey(name)
                   ? mAttributes.get(name)
                   : def;
    }

    public String[] getClasses()
    {
        String classValue = getAttributeValue("class", "");
        return CssUtil.splitClassAttributeValue(classValue);
    }

    public String getLanguage()
    {
        // This method does not implement the full procedure of getting the element's language, as described
        // in http://www.w3.org/TR/html5/dom.html#language, but other sources of the language information are either
        // non-conforming (the element <meta http-equiv="content-language">) or unavailable (HTTP headers).
        String langAttribute = DictionaryExtension.getValueOrNull(mAttributes, "lang");
        if (langAttribute != null)
            return langAttribute;

        return (mParent != null)
                   ? mParent.getLanguage()
                   : "";
    }

    public IElementProvider getFirstChildElement()
    {
        return (mChildren.getCount() != 0) ? mChildren.get(0) : null;
    }

    public IElementProvider getPreviousSiblingElement()
    {
        if (mParent == null)
            return null;

        return (mElementIndex > 0)
                   ? mParent.mChildren.get(mElementIndex - 1)
                   : null;
    }

    public IElementProvider getNextSiblingElement()
    {
        if (mParent == null)
            return null;

        return (mElementIndex < mParent.mChildren.getCount() - 1)
                   ? mParent.mChildren.get(mElementIndex + 1)
                   : null;
    }

    public IElementProvider[] getChildElements()
    {
        return mChildren.toArray();
    }

    public boolean hasOnlyOneChildElementWithOptionalWhitespace()
    {
        return mChildren.getCount() == 1;
    }

    public boolean startsWithImplicitBox()
    {
        // Implicit elements do not participate in our CSS box model, so the return value does not matter.
        return false;
    }

    public boolean endsWithImplicitBox()
    {
        // Implicit elements do not participate in our CSS box model, so the return value does not matter.
        return false;
    }

    public boolean containsChildBoxes()
    {
        // Implicit elements do not participate in our CSS box model, so the return value does not matter.
        return false;
    }

    public boolean isElementContainsText()
    {
        // Implicit elements do not participate in our CSS box model, so the return value does not matter.
        return false;
    }

    public boolean isLastChildBox()
    {
        // Implicit elements do not participate in our CSS box model, so the return value does not matter.
        return false;
    }

    public IElementProvider getParentElement()
    {
        return mParent;
    }

    MockHtmlElementProvider addAttribute(String attributeName, String attributeValue)
    {
        mAttributes.add(attributeName, attributeValue);
        return this;
    }

    public String getElementNamespace() { return W3CNamespaces.XHTML; }

    public String getElementName() { return mName; }
    public void setElementName(String value) { mName = value; }

    public boolean isImplicit() { return false; }

    private String mName;
    private /*final*/ int mElementIndex;
    private /*final*/ SortedStringListGeneric<String> mAttributes;

    private /*final*/ MockHtmlElementProvider mParent;
    private /*final*/ HtmlElementProviderCollection mChildren;
}

