// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2011 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.EnumUtilPal;


/// <summary>
/// A base class for modifying INDEX, TOC and TOA entry font attributes when copying to the INDEX, TOC or TOA.
/// </summary>
abstract class IndexAndTablesEntryAttributeModifier
{
    /// <summary>
    /// Initializes base class members.
    /// </summary>
    /// <param name="styleId"></param>
    /// <param name="styles"></param>
    protected IndexAndTablesEntryAttributeModifier(/*StyleIdentifier*/int styleId, StyleCollection styles)
    {
        mEntryStyle = styles.getBySti(styleId, true);
    }

    /// <summary>
    /// When implemented in a derived class, modifies the attributes of inline node to be placed in the INDEX, TOC or TOA.
    /// </summary>
    /// <param name="sourceInline"></param>
    /// <param name="modifiedInline"></param>
    protected abstract void modifyInlineNode(Inline sourceInline, Inline modifiedInline) throws Exception;

    /// <summary>
    /// Implements common parameter checks to perform before node modification.
    /// </summary>
    /// <param name="referenceNode"></param>
    /// <param name="nodeToModify"></param>
    /// <returns></returns>
    protected Node modify(Node referenceNode, Node nodeToModify) throws Exception
    {
        Debug.assertTrue(referenceNode != null);
        Debug.assertTrue(nodeToModify != null);

        if (!modifyInlineNode(referenceNode, nodeToModify))
        {
            // WORDSNET-18798 modify SDT nested inline nodes.
            if (referenceNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            {
                NodeEnumerator referenceRange = (NodeEnumerator)new NodeRange(referenceNode, referenceNode).iterator();
                NodeEnumerator modifiedRange = (NodeEnumerator)new NodeRange(nodeToModify, nodeToModify).iterator();

                while (referenceRange.moveToNextNode() && modifiedRange.moveToNextNode())
                    modifyInlineNode(referenceRange.getCurrentNode(), modifiedRange.getCurrentNode());
            }
        }

        return nodeToModify;
    }

    private boolean modifyInlineNode(Node referenceNode, Node nodeToModify) throws Exception
    {
        Inline referenceInline = ms.as(referenceNode, Inline.class);
        Inline modifiedInline = ms.as(nodeToModify, Inline.class);

        if (referenceInline == null || modifiedInline == null)
            return false;

        // Derived classes process nodes differently for different field types.
        acceptRevisions(modifiedInline);
        modifyInlineNode(referenceInline, modifiedInline);

        return true;
    }

    private static void acceptRevisions(Inline node)
    {
        node.getRunPr().acceptFormatRevision();
        node.getRunPr().remove(RevisionAttr.INSERT_REVISION);
    }

    /// <summary>
    /// Removes extra direct formatting attributes that should not be placed in the INDEX, TOC or TOA.
    /// </summary>
    /// <param name="sourceInline">The node on which duplicated attributes are detected.</param>
    /// <param name="modifiedInline">The node from which the detected attributes are stripped. Can be other than sourceInline.</param>
    protected void stripExtraDirectAttributes(Inline sourceInline, Inline modifiedInline)
    {
        int[] extraDirectAttributes = listExtraDirectAttributes(sourceInline);
        removeListOfAttrs(modifiedInline.getRunPr(), extraDirectAttributes);
    }

    /// <summary>
    /// Builds a collection of direct formatting attributes
    /// that must be removed when copying the node to the INDEX, TOC or TOA.
    /// </summary>
    /// <param name="inline"></param>
    protected int[] listExtraDirectAttributes(Inline inline)
    {
        RunPr runPr = inline.getRunPr();

        // Something like List would be cleaner, but I don't like boxing/un-boxing in this case.
        // It's a pity we can't use generics.
        int[] directAttributes = new int[runPr.getCount()];

        for (int attrIdx = 0; attrIdx < runPr.getCount(); ++attrIdx)
        {
            int directAttrKey = runPr.getKey(attrIdx);
            Object directAttrValue = runPr.getDirectAttr(directAttrKey);

            directAttributes[attrIdx] = isExtraDirectAttribute(directAttrKey, directAttrValue, inline)
                ? directAttrKey
                // We could place all attributes to delete in the beginning of the array without gaps.
                // But I don't think it is worth complicating the logic.
                : KEEP_THIS_ATTRIBUTE;
        }

        return directAttributes;
    }

    /// <summary>
    /// Implements the logic of deciding if the given attribute should be stripped when copying to the INDEX, TOC or TOA.
    /// </summary>
    /// <param name="directAttrKey"></param>
    /// <param name="directAttrValue"></param>
    /// <param name="sourceInline"></param>
    /// <returns></returns>
    protected /*virtual*/ boolean isExtraDirectAttribute(int directAttrKey, Object directAttrValue, Inline sourceInline)
    {
        return isStrippedUncoditionally(directAttrKey)
            || directAttrDuplicatesInheritedValue(directAttrKey, directAttrValue, sourceInline)
            || isExtraToggleAttribute(directAttrKey, directAttrValue, sourceInline);
    }

    protected static boolean isMinorHAnsiThemeFontAttr(int attrKey, Object attrValue)
    {
        switch (attrKey)
        {
            case FontAttr.NAME_ASCII:
            case FontAttr.NAME_OTHER:
                return isMinorHAnsiThemeFontAttr(attrValue);
            case FontAttr.NAME_BI:
            case FontAttr.NAME_FAR_EAST:
                // MS Word doesn't strip `cstheme` and `eastAsiaTheme` attrs. Let's declare it explicitly.
                return false;
            default:
                return false;
        }
    }

    private static boolean isMinorHAnsiThemeFontAttr(Object attrValue)
    {
        ComplexFontName fontName = ms.as(attrValue, ComplexFontName.class);
        if (fontName == null)
            return false;

        if (!fontName.isThemeFont())
            return false;

        return fontName.getThemeFontCore() == ThemeFontCore.MINOR_H_ANSI;
    }

    /// <summary>
    /// Checks if a certain attribute type should go to INDEX, TOC or TOA.
    /// </summary>
    /// <param name="attrKey"></param>
    /// <returns></returns>
    protected static boolean isStrippedUncoditionally(int attrKey)
    {
        // It seems like font size specified directly is always ignored when building an INDEX, TOC or TOA.
        return (attrKey == FontAttr.SIZE) || (attrKey == FontAttr.SIZE_BI)
            // WORDSNET-25674 TOC items inherit style formatting after update fields.
            || (attrKey == FontAttr.ISTD);
    }

    /// <summary>
    /// Checks if the given attribute has the same value as inherited from the styles or defaults.
    /// </summary>
    /// <remarks>
    /// Normally, such attributes should not be present in a document.
    /// They just duplicate the value obtained from the styles or defaults.
    /// However, when the node is copied to the INDEX, TOC or TOA, styles change.
    /// So in a new context such attribute may result in a different formatting.
    /// </remarks>
    protected static boolean directAttrDuplicatesInheritedValue(int directAttrKey, Object directAttribute, Inline sourceInline)
    {
        Object inheritedAttribute = InlineHelper.fetchInheritedAttr(sourceInline, directAttrKey);
        return EnumUtilPal.enumOrIntEquals(directAttribute, inheritedAttribute);
    }

    /// <summary>
    /// Checks if a toggle attribute should go to INDEX, TOC or TOA.
    /// </summary>
    /// <param name="directAttrKey"></param>
    /// <param name="directAttrValue"></param>
    /// <param name="sourceInline"></param>
    /// <returns></returns>
    protected boolean isExtraToggleAttribute(int directAttrKey, Object directAttrValue, Inline sourceInline)
    {
        // Check toggle attributes only.
        if (!(directAttrValue instanceof AttrBoolEx))
            return false;

        // DM: Oddly enough, Word seems to clear a direct toggle attribute when copying to TOC...
        // ...if the same attribute is set via character style.
        // It looks like this happens regardless of the actual resolved attribute value for the source or destination run.
        // I cannot explain it logically. Just trying to reproduce it.
        AttrBoolEx resolvedCharStyleAttr = (AttrBoolEx)sourceInline.getCharacterStyle().getFontAttr(directAttrKey, false);
        boolean isToggledOnViaCharStyle = (resolvedCharStyleAttr == AttrBoolEx.TRUE);

        return isToggledOnViaCharStyle
            || resolvedAttrValueMatchesParagraphStyle(directAttrKey, sourceInline);

    }

    /// <summary>
    /// Checks if the resolved attribute of the source node has the same value
    /// that is derived from the INDEX, TOC or TOA paragraph style.
    /// </summary>
    /// <param name="attrKey"></param>
    /// <param name="sourceInline"></param>
    /// <returns></returns>
    protected boolean resolvedAttrValueMatchesParagraphStyle(int attrKey, Inline sourceInline)
    {
        if (getEntryStyle() == null)
            return false;

        // That will be the attribute value in the INDEX, TOC or TOA in the absence of direct formatting.
        Object resolvedParagraphStyleAttr = getEntryStyle().getFontAttr(attrKey, true);

        // This is the resolved attribute value of the source node.
        Object resolvedSourceAttr = sourceInline.getFont().fetchAttr(attrKey);

        // Do not add direct formatting in INDEX, TOC or TOA if the resolved attributes match without it.
        return resolvedSourceAttr.equals(resolvedParagraphStyleAttr);
    }

    /// <summary>
    /// Removes a list of attributes from a collection.
    /// </summary>
    /// <param name="attrCollection"></param>
    /// <param name="attrsToRemove"></param>
    protected static void removeListOfAttrs(AttrCollection attrCollection, int[] attrsToRemove)
    {
        // Remove duplicated attributes.
        for (int attr : attrsToRemove)
        {
            if (attr != KEEP_THIS_ATTRIBUTE)
                attrCollection.remove(attr);
        }
    }

    /// <summary>
    /// A placeholder for the attributes that should not be removed from the modified node.
    /// </summary>
    private static final int KEEP_THIS_ATTRIBUTE = -1;

    protected Style getEntryStyle() { return mEntryStyle; };

    private Style mEntryStyle;
}

