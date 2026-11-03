// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/04/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Modifies INDEX entry font attributes.
/// </summary>
class IndexEntryAttributeModifier extends IndexAndTablesEntryAttributeModifier implements INodeModifier
{
    /// <summary>
    /// Ctor.
    /// </summary>
    IndexEntryAttributeModifier(/*StyleIdentifier*/int styleId, StyleCollection styles)
    {
    	super(styleId, styles);
	
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener) throws Exception
    {
        return modify(referenceNode, nodeToModify);
    }

    protected /*override*/ void modifyInlineNode(Inline sourceInline, Inline modifiedInline)
    {
        stripExtraDirectAttributes(sourceInline, modifiedInline);
    }

    protected /*override*/ boolean isExtraDirectAttribute(int directAttrKey, Object directAttrValue, Inline sourceInline)
    {
        return isIndexExtraDirectAttribute(directAttrKey)
            || super.isExtraDirectAttribute(directAttrKey, directAttrValue, sourceInline)
            || isMinorHAnsiThemeFontAttr(directAttrKey, directAttrValue);
    }

    /// <summary>
    /// Returns a value indicating whether the font attribute with the specified key is an INDEX-specific extra attr.
    /// </summary>
    private static boolean isIndexExtraDirectAttribute(int key)
    {
        return (key == FontAttr.HIDDEN);
    }
}

