// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/06/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msObject;

/// <summary>
/// Represents a key for <see cref="HtmlSelectorStyleCache"/>.
/// </summary>
class HtmlSelectorStyleCacheKey
{
    HtmlSelectorStyleCacheKey(CssSelector selector, /*StyleType*/int styleType)
    {
        Debug.assertTrue(selector != null);
        mSelector = selector;
        mStyleType = styleType;
    }

    CssSelector getSelector() { return mSelector; }

    /*StyleType*/int getStyleType() { return mStyleType; }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != getClass())
            return false;
        return equals((HtmlSelectorStyleCacheKey)obj);
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            return (mSelector.hashCode() * 397) ^ (int)mStyleType;
        }
    }

    protected boolean equals(HtmlSelectorStyleCacheKey other)
    {
        return mSelector.equals(other.mSelector) && (mStyleType == other.mStyleType);
    }

    private /*final*/ CssSelector mSelector;
    private /*final*/ /*StyleType*/int mStyleType;
}

