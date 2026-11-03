// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Interface for HTML element.
/// </summary>
interface IHtmlElementProvider extends IElementProvider
{
    /// <summary>
    /// Returns a value indicating whether this element contains only one child element optionally surrounded by whitespace.
    /// </summary>
    public boolean hasOnlyOneChildElementWithOptionalWhitespace();

    /// <summary>
    /// Indicates whether the first inner box of this element is an implicit box.
    /// </summary>
    public boolean startsWithImplicitBox();

    /// <summary>
    /// Indicates whether the last inner box of this element is an implicit box.
    /// </summary>
    public boolean endsWithImplicitBox();

    /// <summary>
    /// Indicates whether this element contains non-implicit child boxes.
    /// </summary>
    public boolean containsChildBoxes();

    /// <summary>
    /// Indicates whether this element or any of its child elements contains text that is not whitespace
    /// and is not ignored by the CSS box model.
    /// </summary>
    public boolean isElementContainsText();

    /// <summary>
    /// Indicates whether this element is the last non-implicit child box of its parent element.
    /// </summary>
    public boolean isLastChildBox();
}

