// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2011 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;


/// <summary>
/// Modifies TOC entry font attributes for a hyperlink TOC entry (TOC with \\h switch).
/// </summary>
class TocHyperlinkEntryAttributeModifier extends IndexAndTablesEntryAttributeModifier implements INodeModifier
{
    TocHyperlinkEntryAttributeModifier(/*StyleIdentifier*/int tocStyleId, StyleCollection styles)
    {
    	super(tocStyleId, styles);
	
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener) throws Exception
    {
        Node modifiedNode = modify(referenceNode, nodeToModify);

        if (!setHyperlinkStyle(modifiedNode))
        {
            for (Node node : new NodeRange(modifiedNode, modifiedNode))
                setHyperlinkStyle(node);
        }

        return modifiedNode;
    }

    private static boolean setHyperlinkStyle(Node node)
    {
        Font font = extractFont(node);
        if (font == null)
            return false;

        // It's interesting that font attributes defined by Hyperlink style are ignored by MS Word in TOC.
        // "Reveal formatting" shows these attributes, but the text does not get them.
        // This Word behavior breaks if character style other than Hyperlink is used, even if it is just cloned from Hyperlink.
        font.setStyleIdentifier(StyleIdentifier.HYPERLINK);
        return true;
    }

    private static Font extractFont(Node node)
    {
        Inline inline = ms.as(node, Inline.class);
        if (inline != null)
            return inline.getFont();

        ShapeBase shape = ms.as(node, ShapeBase.class);
        if (shape != null && shape.isTopLevel() && shape.getFallbackShape() != null)
            return shape.getFont();

        return null;
    }

    /// <summary>
    /// Implements Toc entry node font attributes modification for a TOC with \\h switch.
    /// </summary>
    protected /*override*/ void modifyInlineNode(Inline sourceInline, Inline modifiedInline)
    {
        stripExtraDirectAttributes(sourceInline, modifiedInline);
    }

    /// <summary>
    /// Checks if a direct font attributes should be stripped when copying node to the TOC with \\h switch.
    /// Adds additional checks to the base implementation.
    /// </summary>
    /// <param name="directAttrKey"></param>
    /// <param name="directAttrValue"></param>
    /// <param name="sourceInline"></param>
    /// <returns></returns>
    protected /*override*/ boolean isExtraDirectAttribute(int directAttrKey, Object directAttrValue, Inline sourceInline)
    {
        return isSpecificHyperlinkAttribute(directAttrKey)
            || super.isExtraDirectAttribute(directAttrKey, directAttrValue, sourceInline)
            || isDefinedViaStyle(directAttrKey, sourceInline)
            || isMinorHAnsiThemeFontAttr(directAttrKey, directAttrValue);
    }

    /// <summary>
    /// Checks if the given attribute type should go to the TOC with \\h switch.
    /// </summary>
    /// <param name="attrKey"></param>
    /// <returns></returns>
    private static boolean isSpecificHyperlinkAttribute(int attrKey)
    {
        // Normally, color and underline are defined by Hyperlink style.
        // Word strips them regardless of the value.
        // And it even does not matter if they are really defined in Hyperlink style.
        switch (attrKey)
        {
            case FontAttr.COLOR:
            case FontAttr.THEME_COLOR:
            case FontAttr.THEME_SHADE:
            case FontAttr.THEME_TINT:
            case FontAttr.UNDERLINE:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Checks if the given is actually defined by TOC or Hyperlink style.
    /// </summary>
    /// <param name="attrKey"></param>
    /// <param name="sourceInline"></param>
    /// <returns></returns>
    private boolean isDefinedViaStyle(int attrKey, Inline sourceInline)
    {
        Style hyperlinkStyle = getStyle(StyleIdentifier.HYPERLINK);

        return styleAttrValueDiffersFromDefault(hyperlinkStyle, attrKey, sourceInline)
            || styleAttrValueDiffersFromDefault(getEntryStyle(), attrKey, sourceInline);
    }

    /// <summary>
    /// Checks if an attribute is defined by style and the definition differs from default and inline.
    /// </summary>
    /// <remarks>
    /// However toggles are considered defined even if they resolve to the default definition.
    /// </remarks>
    /// <param name="style"></param>
    /// <param name="attrKey"></param>
    /// <param name="sourceInline"></param>
    /// <returns></returns>
    private boolean styleAttrValueDiffersFromDefault(Style style, int attrKey, Inline sourceInline)
    {
        if (style == null)
            return false;

        Object attrValue = style.getFontAttr(attrKey, false);
        if (attrValue == null)
        {
            // It's not defined. Hence, does not differ from default.
            return false;
        }
        else if (attrValue instanceof AttrBoolEx)
        {
            // Always clear direct toggle attributes if they are defined in the TOC or HyperLink style.
            return true;
        }
        else
        {
            // Otherwise, compare with the default value and inline value.
            Object defaultAttrValue = getAttributeFromDefaultStyle(attrKey);
            if (attrValue.equals(defaultAttrValue))
            {
                // WORDSNET-10392 Clear direct attribute only if inline attribute equals to style attribute.
                Object inlineAttrValue = sourceInline.getRunPr().fetchAttr(attrKey);
                return attrValue.equals(inlineAttrValue);
            }

            return false;
        }
    }

    /// <summary>
    /// Gets an attribute value from the default style.
    /// </summary>
    /// <param name="attrKey"></param>
    /// <returns></returns>
    private Object getAttributeFromDefaultStyle(int attrKey)
    {
        Style defaultStyle = getStyle(StyleIdentifier.DEFAULT_PARAGRAPH_FONT);

        return (defaultStyle != null)
            ? defaultStyle.getFontAttr(attrKey, hasDefaultAttributeValue(attrKey))
            : null;
    }

    private static boolean hasDefaultAttributeValue(int attrKey)
    {
        switch (attrKey)
        {
            // Revision keys
            case RevisionAttr.FORMAT_REVISION:
            case RevisionAttr.INSERT_REVISION:
            case RevisionAttr.DELETE_REVISION:
            case RevisionAttr.MOVE_FROM_REVISION:
            case RevisionAttr.MOVE_TO_REVISION:

            // Internal keys
            case FontAttr.SYS_SYMBOL:
                return false;

            default:
                return true;
        }
    }

    /// <summary>
    /// Gets a style via Styles collection of TocEntryStyle.
    /// Returns null if TocEntryStyle is null;
    /// </summary>
    /// <param name="styleIdentifier"></param>
    /// <returns></returns>
    private Style getStyle(/*StyleIdentifier*/int styleIdentifier)
    {
        return getEntryStyle() != null
            ? getEntryStyle().getStyles().getBySti(styleIdentifier, true)
            : null;
    }

}

