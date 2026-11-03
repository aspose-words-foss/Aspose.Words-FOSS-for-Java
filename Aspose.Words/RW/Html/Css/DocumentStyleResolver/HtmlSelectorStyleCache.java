// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/06/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;


/// <summary>
/// Caches results of CSS selector to document style resolution.
/// </summary>
/// <remarks>
/// This class is used to speed up selector to style resolution.
/// </remarks>
class HtmlSelectorStyleCache
{
    /// <summary>
    /// Gets a document style that corresponds to the specified selector and style.
    /// </summary>
    /// <param name="selector">A CSS selector.</param>
    /// <param name="styleType">A requested style type.</param>
    /// <returns>
    /// A document style that corresponds to the selector. Can be <c>null</c> if the selector hasn't been resolved
    /// to a style yet. Can be the null substitute style if there is no document style that corresponds to the selector.
    /// </returns>
    Style getStyle(CssSelector selector, /*StyleType*/int styleType)
    {
        Debug.assertTrue(selector != null);

        HtmlSelectorStyleCacheKey key = new HtmlSelectorStyleCacheKey(selector, styleType);
        return DictionaryExtension.getValueOrNull(mCachedStyles, key);
    }

    /// <summary>
    /// Adds a resolution result (a selector and a style) to the cache.
    /// </summary>
    /// <param name="selector">A CSS selector.</param>
    /// <param name="styleType">A requested style type.</param>
    /// <param name="style">A document style that corresponds to the selector. Can be <c>null</c>.</param>
    void addStyle(CssSelector selector, /*StyleType*/int styleType, Style style)
    {
        Debug.assertTrue(selector != null);
        Debug.assertTrue(!isNullSubstitute(style));

        HtmlSelectorStyleCacheKey key = new HtmlSelectorStyleCacheKey(selector, styleType);
        if (style == null)
        {
            style = G_NULL_STYLE;
        }
        msDictionary.add(mCachedStyles, key, style);
    }

    /// <summary>
    /// Checks whether the specified style is a special instance that substitutes for <c>null</c> values in this cache.
    /// </summary>
    boolean isNullSubstitute(Style style)
    {
        return style == G_NULL_STYLE;
    }

    /// <summary>
    /// A special style instance that substitutes for <c>null</c> styles in this cache.
    /// </summary>
    /// <remarks>
    /// This instance is used to tell apart the situations "a selector hasn't been resolved to a style yet" and "a selector
    /// has been resolved to no style".
    /// </remarks>
    private static /*final*/ Style G_NULL_STYLE = Style.create(StyleType.CHARACTER);

    private /*final*/ HashMap<HtmlSelectorStyleCacheKey, Style> mCachedStyles =
        new HashMap<HtmlSelectorStyleCacheKey, Style>();
}

