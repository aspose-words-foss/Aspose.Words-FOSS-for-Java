// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/05/2023 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.W3CNamespaces;


/// <summary>
/// Determines whether the input HTML document has fixed-page format with positioned elements.
/// </summary>
class HtmlFixedPageDetector
{
    void processHtmlElementNode(
        String name,
        String ns,
        CssDeclarationCollection declarations)
    {
        // If we're already sure that the document is fixed-page HTML, we skip all futher steps.
        if (isFixedPageHtml())
        {
            return;
        }

        // Skip SVG.
        if (W3CNamespaces.SVG.equals(ns))
        {
            return;
        }

        // Skip all insignificant elements.
        if ((!"div".equals(name)) &&
            (!"span".equals(name)) &&
            (!"input".equals(name)) &&
            (!"select".equals(name)))
        {
            return;
        }

        // Each significant element in a fixed-page document must be absolutely positioned.
        if (declarations.containsIdentifier("position", "absolute"))
        {
            if ((declarations.get("top") != null) ||
                (declarations.get("right") != null) ||
                (declarations.get("bottom") != null) ||
                (declarations.get("left") != null))
            {
                isFixedPageHtml(true);
            }
        }
    }

    boolean isFixedPageHtml() { return mIsFixedPageHtml; }; private void isFixedPageHtml(boolean value) { mIsFixedPageHtml = value; };

    private boolean mIsFixedPageHtml;
}

