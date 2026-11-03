// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Gets and formats appropriate model styles when CSS formatting is applied to the document model.
/// </summary>
abstract class StyleFormatter
{
    protected StyleFormatter(
        Document document,
        CssStyleTracker cssStyleTracker,
        ParagraphFormatter paragraphFormatter,
        FontFormatter fontFormatter)
    {
        Debug.assertTrue(document != null);
        Debug.assertTrue(cssStyleTracker != null);
        Debug.assertTrue(paragraphFormatter != null);
        Debug.assertTrue(fontFormatter != null);

        Document = document;
        CssStyleTracker = cssStyleTracker;
        ParagraphFormatter = paragraphFormatter;
        FontFormatter = fontFormatter;
    }

    abstract Style getModelStyle(/*StyleType*/int styleType) throws Exception;

    abstract void updatePredefinedStyles() throws Exception;

    /*virtual*/ void postUpdateStyles()
    {
        // Do nothing by default.
    }

    void handleSpanStart() throws Exception
    {
        Style style = getModelStyle(StyleType.CHARACTER);
        if (style == null)
        {
            style = getParentCharacterStyle();
        }
        mCharacterStyles.push(style);
    }

    void handleSpanEnd()
    {
        Debug.assertTrue(mCharacterStyles.getCount() > 0);
        mCharacterStyles.pop();
    }

    /*virtual*/ void handleParagraphStart() throws Exception
    {
        // Do nothing.
    }

    abstract Style getStyleWithoutDeclarations();

    /// <summary>
    /// Gets the character style from the parent element. May be <c>null</c>.
    /// </summary>
    /// <remarks>
    /// In MS Word, character styles are propagated from parent inline elements to their children.
    /// </remarks>
    protected Style getParentCharacterStyle()
    {
        return StackExtension.top(mCharacterStyles);
    }

    protected /*final*/ Document Document;
    protected /*final*/ CssStyleTracker CssStyleTracker;
    protected /*final*/ ParagraphFormatter ParagraphFormatter;
    protected /*final*/ FontFormatter FontFormatter;

    /// <summary>
    /// A stack of character styles selected for parent inline-level HTML elements.
    /// Elements are instances of <see cref="Style"/>.
    /// </summary>
    private /*final*/ msStack<Style> mCharacterStyles = new msStack<Style>();
}

