// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/09/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Incapsulates information used to resolve computed CSS values during the cascade.
/// </summary>
class CssCascadeContext
{
    /// <summary>
    /// Creates a new instance of the class.
    /// </summary>
    /// <param name="rootElementDeclarations">
    /// Computed CSS declarations of the root HTML element; null if the element has no root.
    /// </param>
    /// <param name="parentElementDeclarations">
    /// Computed CSS declarations of the parent HTML element; null if the element has no parent.
    /// </param>
    /// <param name="elementFontSize">
    /// Computed 'font-size' value of this element. Relative values of some properties refer to this value.
    /// If this value is zero, default font size is used.
    /// </param>
    /// <param name="isPseudoElement">
    /// Indicates whether the current HTML element is a pseudo-element.
    /// </param>
    CssCascadeContext(
        CssDeclarationCollection rootElementDeclarations, 
        CssDeclarationCollection parentElementDeclarations,
        double elementFontSize,
        boolean isPseudoElement)
    {
        if (rootElementDeclarations != null)
        if (parentElementDeclarations != null)
        Debug.assertTrue(elementFontSize >= 0);

        mRootElementDeclarations = rootElementDeclarations;
        mParentElementDeclarations = parentElementDeclarations;
        mElementFontSize = elementFontSize;
        mIsPseudoElement = isPseudoElement;
    }

    /// <summary>
    /// Computed CSS declarations of the parent HTML element; null if the element has no parent.
    /// </summary>
    CssDeclarationCollection getParentElementDeclarations() { return mParentElementDeclarations; }

    /// <summary>
    /// Computed CSS declarations of the root HTML element; null if the element has no root.
    /// </summary>
    CssDeclarationCollection getRootElementDeclarations() { return mRootElementDeclarations; }

    /// <summary>
    /// Computed 'font-size' value of this element. Relative values of some properties (for instance, 'line-height')
    /// refer to this value.
    /// </summary>
    double getElementFontSize() { return mElementFontSize; }

    /// <summary>
    /// Gets a value indicating whether this HTML element is a pseudo-element.
    /// </summary>
    boolean isPseudoElement() { return mIsPseudoElement; }

    private /*final*/ CssDeclarationCollection mParentElementDeclarations;
    private /*final*/ CssDeclarationCollection mRootElementDeclarations;
    private /*final*/ double mElementFontSize;
    private /*final*/ boolean mIsPseudoElement;
}

