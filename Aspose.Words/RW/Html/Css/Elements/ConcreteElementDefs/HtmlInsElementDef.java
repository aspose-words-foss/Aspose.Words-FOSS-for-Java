// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class HtmlInsElementDef extends HtmlElementDef
{
    protected /*override*/ void applyStyles(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // User agent (default) style.

        // WORDSNET-13034 Browsers apply 'text-decoration: underline' to <ins> elements but MS Word ignores this
        // default style. Insertion revisions appear underlined in MS Word without additional formatting. We mimic
        // MS Word's behavior and don't apply the default 'text-decoration' on <ins> elements.
    }
}

