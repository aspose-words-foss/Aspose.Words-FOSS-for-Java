// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/05/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for all CSS pseudo-class selectors.
/// </summary>
abstract class CssPseudoClassSelector extends CssSimpleSelector
{
    /*override*/ CssSelectorSpecificity getSpecificity() { return G_SPECIFICITY; }

    private static /*final*/ CssSelectorSpecificity G_SPECIFICITY = new CssSelectorSpecificity(0, 1, 0);
}
