// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Modifies TOA entry font attributes.
/// </summary>
class ToaEntryAttributeModifier extends IndexAndTablesEntryAttributeModifier implements INodeModifier
{
    public ToaEntryAttributeModifier(StyleCollection styles, boolean clearDirectFormatting)
    {
    	super(StyleIdentifier.TABLE_OF_AUTHORITIES, styles);
	
        mClearDirectFormatting = clearDirectFormatting;
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
        return mClearDirectFormatting
            || super.isExtraDirectAttribute(directAttrKey, directAttrValue, sourceInline)
            || isMinorHAnsiThemeFontAttr(directAttrKey, directAttrValue);
    }

    private /*final*/ boolean mClearDirectFormatting;
}

