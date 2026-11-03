// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/09/2014 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Stores element background information.
/// This information allows specifying background colors taken from html element for their counterpart of the AW model.
/// </summary>
/// /// <remarks>
/// This class is immutable (read-only).
/// </remarks>
class ElementBackgroundInfo
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="blockBackgroundDeclaration"><see cref="CssDeclaration"/> for block element.</param>
    /// <param name="inlineBackgroundDeclaration"><see cref="CssDeclaration"/> for inline element.</param>
    /// <param name="isTopLevelContainer"><c>true</c> if it's top level element as a 'html' or 'body'.</param>
    /// <param name="parent">Parent background.</param>
    /// <param name="isContainer">True if the element may contain another element.</param>
    ElementBackgroundInfo(
        CssComputedDeclaration blockBackgroundDeclaration,
        CssComputedDeclaration inlineBackgroundDeclaration,
        boolean isTopLevelContainer,
        ElementBackgroundInfo parent,
        boolean isContainer)
    {
        mBlockBackgroundDeclaration = blockBackgroundDeclaration;
        mIsTopLevelContainer = isTopLevelContainer;
        mParent = parent;
        mIsContainer = isContainer;
        mInlineBackgroundDeclaration = inlineBackgroundDeclaration;
    }

    CssDeclaration getParagraphBackgroundColor()
    {
        return ((!isBlockBackgroundEmpty()) && (!isBlockBackgroundSameAsContainer()))
            ? getBlockBackgroundDeclaration()
            : null;
    }

    CssDeclaration getFontBackgroundColor()
    {
        return ((!isInlineBackgroundEmpty()) && (!isBlockBackgroundSameAsInlineBackground()))
            ? getInlineBackgroundDeclaration()
            : null;
    }

    CssDeclaration getTableBackgroundColor()
    {
        return ((!isBlockBackgroundEmpty()) && (!isBlockBackgroundSameAsContainer()))
            ? getBlockBackgroundDeclaration()
            : null;
    }

    private CssComputedDeclaration getContainerBackgroundColor()
    {
        ElementBackgroundInfo item = mParent;
        while (item != null)
        {
            if (item.mIsContainer)
                return item.getBlockBackgroundDeclaration();
            item = item.mParent;
        }
        return null;
    }

    boolean isBlockBackgroundEmpty()
    {
        return getBlockBackgroundDeclaration() == null;
    }

    /// <summary>
    /// Determines if the block background for this item is the same as the container.
    /// </summary>
    boolean isBlockBackgroundSameAsContainer()
    {
        CssComputedDeclaration containerColor = getContainerBackgroundColor();
        return ((getBlockBackgroundDeclaration() == null) && (containerColor == null)) ||
            ((getBlockBackgroundDeclaration() != null) && (containerColor != null) &&
            getBlockBackgroundDeclaration().getValue().equals(containerColor.getValue()));
    }

    boolean isInlineBackgroundEmpty()
    {
        return getInlineBackgroundDeclaration() == null;
    }

    boolean isBlockBackgroundSameAsInlineBackground()
    {
        return !isInlineBackgroundEmpty() &&
           !isBlockBackgroundEmpty() &&
           getBlockBackgroundDeclaration().getValue().equals(getInlineBackgroundDeclaration().getValue());
    }

    /// <summary>
    /// <see cref="CssDeclaration"/> of background declaration for block element
    /// </summary>
    CssComputedDeclaration getBlockBackgroundDeclaration() { return mBlockBackgroundDeclaration; }
    /// <summary>
    /// <see cref="CssDeclaration"/> of background declaration for inline element
    /// </summary>
    CssComputedDeclaration getInlineBackgroundDeclaration() { return mInlineBackgroundDeclaration; }
    /// <summary>
    /// Returns <c>true</c> if this top level element as a 'html' or 'body'
    /// </summary>
    boolean isTopLevelContainer() { return mIsTopLevelContainer; }

    /// <summary>
    /// Represents an empty top level container element.
    /// </summary>
    /// <remarks>
    /// Used to reduce memory allocations.
    /// </remarks>
    static ElementBackgroundInfo getEmptyTopLevelContainer() { return G_EMPTY_TOP_LEVEL_CONTAINER; }

    private /*final*/ CssComputedDeclaration mBlockBackgroundDeclaration;
    private /*final*/ CssComputedDeclaration mInlineBackgroundDeclaration;
    private /*final*/ boolean mIsTopLevelContainer;
    private /*final*/ ElementBackgroundInfo mParent;
    private /*final*/ boolean mIsContainer;

    private static /*final*/ ElementBackgroundInfo G_EMPTY_TOP_LEVEL_CONTAINER = new ElementBackgroundInfo(null, null, true, null, true);
}

