// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2011 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;

/// <summary>
/// Modifies TOC entry font attributes for a not-hyperlink TOC entry (TOC without \\h switch).
/// </summary>
class TocNormalEntryAttributeModifier extends IndexAndTablesEntryAttributeModifier implements INodeModifier
{
    TocNormalEntryAttributeModifier(
        /*StyleIdentifier*/int tocStyleId,
        StyleCollection styles,
        boolean copyCharStyleAttributes)
    {
    	super(tocStyleId, styles);
	
        mCopyCharStyleAttributes = copyCharStyleAttributes;
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener) throws Exception
    {
        return modify(referenceNode, nodeToModify);
    }

    /// <summary>
    /// Implements Toc entry node font attributes modification for a TOC without \\h switch.
    /// </summary>
    protected /*override*/ void modifyInlineNode(Inline sourceInline, Inline modifiedInline)
    {
        stripExtraDirectAttributes(sourceInline, modifiedInline);
        addDirectAttributesForCharStyle(sourceInline, modifiedInline);
    }

    /// <summary>
    /// Currently, it is used for testing only.
    /// Strips extra direct formatting attributes from the current node (if it is inline) and all inline children.
    /// </summary>
    static void stripInlineFontAttrsForToc(Node node)
    {
        NodeRange nodeRange = new NodeRange(node, node);

        TocNormalEntryAttributeModifier stripper = new TocNormalEntryAttributeModifier(
            StyleIdentifier.DEFAULT_PARAGRAPH_FONT,
            node.getDocument().getStyles(),
            true);

        stripper.stripInlineFontAttrsForToc(nodeRange);
    }

    /// <summary>
    /// Currently, it is used for testing only.
    /// Strips extra direct formatting from all inline nodes in the given range.
    /// </summary>
    private void stripInlineFontAttrsForToc(NodeRange strippingRange)
    {
        NodeEnumerator strippingEnumerator = new NodeEnumerator(strippingRange);
        try /*JAVA: was using*/
        {
            while (strippingEnumerator.moveToNextNode())
            {
                Inline currentNode = ms.as(strippingEnumerator.getCurrentNode(), Inline.class);
                if (currentNode != null)
                {
                    stripExtraDirectAttributes(currentNode, currentNode);
                }
            }
        }
        finally { if (strippingEnumerator != null) strippingEnumerator.dispose(); }
    }

    /// <summary>
    /// Adds direct attributes to the copied entry in TOC.
    /// This is needed when an attribute is set via character style and no direct formatting is present.
    /// As the character style is not copied to the TOC, to make TOC entry look the same as the source,
    /// direct formatting must be added.
    /// </summary>
    private void addDirectAttributesForCharStyle(Inline sourceInline, Inline modifiedInline)
    {
        if (!mCopyCharStyleAttributes)
            return;

        if (getEntryStyle() == null)
            return;

        RunPr charStylePr = sourceInline.getCharacterStyle().getExpandedRunPr(RunPrExpandFlags.NORMAL);
        RunPr sourceRunPr = sourceInline.getRunPr();
        RunPr modifiedRunPr = modifiedInline.getRunPr();

        for (int attrIdx = 0; attrIdx < charStylePr.getCount(); ++attrIdx)
        {
            int charStyleAttrKey = charStylePr.getKey(attrIdx);
            Object charStyleAttrValue = charStylePr.get(charStyleAttrKey);

            if (isStrippedUncoditionally(charStyleAttrKey))
                continue;

            boolean isHandledByKeepingDirectFormatting = (sourceRunPr.get(charStyleAttrKey) != null);
            if (isHandledByKeepingDirectFormatting)
                continue;

            // Add a direct formatting so that entry formatting in TOC
            // matches source entry formatting via character style.
            if (charStyleAttrValue instanceof AttrBoolEx)
            {
                // If the toggle is turned off via character style it does not differ from the case when it is not set at all.
                // Ignore toggled off AttrBoolEx.
                // It is assumed that all AttrBoolEx'es are False by default.
                boolean attrToggledOn = (charStyleAttrValue == AttrBoolEx.TRUE);
                if (attrToggledOn && !resolvedAttrValueMatchesParagraphStyle(charStyleAttrKey, sourceInline))
                {
                    // For toggle attributes, add a toggle to TOC paragraph style value.
                    // Resolved value from the source won't work.
                    modifiedRunPr.add(charStyleAttrKey, AttrBoolEx.TOGGLE);
                }
            }
            else if (!resolvedAttrValueMatchesParagraphStyle(charStyleAttrKey, sourceInline))
            {
                // For normal attributes, just add the attribute value to keep formatting set by character style in TOC.
                modifiedRunPr.add(charStyleAttrKey, charStyleAttrValue);
            }
        }
    }

    private /*final*/ boolean mCopyCharStyleAttributes;
}

