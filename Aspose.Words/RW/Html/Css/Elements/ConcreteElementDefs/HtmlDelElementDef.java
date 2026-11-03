// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlDelElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.

        // WORDSNET-13034 Browsers apply 'text-decoration: line-through' to <del> elements but MS Word ignores this
        // default style. Deletion revisions appear striked out in MS Word without additional formatting. We mimic
        // MS Word's behavior and don't apply the default 'text-decoration' on <del> elements.
    }
}

