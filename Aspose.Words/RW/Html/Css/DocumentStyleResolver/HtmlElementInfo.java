// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Helps to store an element information in <see cref="DocumentFormatter"/>.
/// Stores intermediate values used for performance optimization.
/// </summary>
abstract class HtmlElementInfo
{
    protected HtmlElementInfo(IHtmlElementProvider element, CssDeclarationCollection declarations)
    {
        Debug.assertTrue(element != null);
        Debug.assertTrue(declarations != null);

        Element = element;
        mDeclarations = declarations;
    }

    /// <summary>
    /// Element declarations. All declarations are computed.
    /// </summary>
    CssDeclarationCollection getDeclarations() { return getDeclarations(HtmlElementPart.ELEMENT); }

    /// <summary>
    /// Gets the part of an HTML element that these properties relate to. 
    /// </summary>
    abstract /*HtmlElementPart*/int getPart();

    /// <summary>
    /// Gets generated content of this element. Only pseudo-elements can have generated content.
    /// </summary>
    /// <returns>
    /// Generated content of this element or <c>null</c> if the element has no generated content.
    /// </returns>
    abstract PseudoElementContent getGeneratedContent();

    /// <summary>
    /// Gets CSS declarations applied to the ::before pseudo-element of this element.
    /// </summary>
    /// <returns>
    /// A collection of CSS declarations. The result is never <c>null</c> but it may be empty.
    /// </returns>
    abstract CssDeclarationCollection getBeforePseudoElementDeclarations();

    /// <summary>
    /// Gets CSS declarations applied to the ::after pseudo-element of this element.
    /// </summary>
    /// <returns>
    /// A collection of CSS declarations. The result is never <c>null</c> but it may be empty.
    /// </returns>
    abstract CssDeclarationCollection getAfterPseudoElementDeclarations();

    /// <summary>
    /// Gets CSS declarations from the specified part of the element. All declarations are computed.
    /// </summary>
    /// <returns>
    /// CSS declarations from the specified part of the element. The result is never <c>null</c>.
    /// </returns>
    CssDeclarationCollection getDeclarations(/*HtmlElementPart*/int part)
    {
        switch (part)
        {
            case HtmlElementPart.ELEMENT:
                return mDeclarations;
            case HtmlElementPart.BEFORE:
                return getBeforePseudoElementDeclarations();
            case HtmlElementPart.AFTER:
                return getAfterPseudoElementDeclarations();
            default:
                Debug.assertTrue(false);
                return CssDeclarationCollection.EMPTY;
        }
    }

    /// <summary>
    /// HTML Element.
    /// </summary>
    IHtmlElementProvider Element;

    /// <summary>
    /// Parent block-level element info; null if parent element not found.
    /// </summary>
    HtmlElementInfo ParentBlockElement;

    /// <summary>
    /// Determines whether the element is a block-level element according to its CSS declarations.
    /// </summary>
    boolean IsBlockLevelElement;

    /// <summary>
    /// Text direction of the block-level element currently being processed.
    /// </summary>
    /*CssDirection*/int BlockLevelDirection;

    /// <summary>
    /// Text direction of the inline-level element currently being processed.
    /// </summary>
    /*CssDirection*/int InlineLevelDirection;

    /// <summary>
    /// Element's display state based on CSS display:none property.
    /// </summary>
    /*HtmlElementDisplayState*/int DisplayState;

    /// <summary>
    /// Element display type based on CSS "display" property.
    /// </summary>
    /*CssDisplayType*/int DisplayType;

    /// <summary>
    /// A value indicating whether this HTML node contain any child nodes: either real or pseudo-elements.
    /// </summary>
    boolean HasChildren;

    /// <summary>
    /// Absolute and effective size in points of this element or <c>null</c> if no sizes are specified.
    /// </summary>
    /// <remarks>
    /// Effective size is the element's absolute "width" and "height" values restricted by its "min-width" and "min-height"
    /// declarations.
    /// </remarks>
    SizeD EffectiveSize;

    private /*final*/ CssDeclarationCollection mDeclarations;
}

