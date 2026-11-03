// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;

/// <summary>
/// Modifies HYPERLINK field results inside IF field result.
/// </summary>
class FieldIfHyperlinkFormattingModifier implements INodeModifier
{
    FieldIfHyperlinkFormattingModifier(Document document)
    {
        mDocument = document;
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener)
    {
        modify(nodeToModify, modifyChildren);
        return nodeToModify;
    }

    private void modify(Node node, boolean modifyChildren)
    {
        mFieldCharCounter.visitNode(node);

        modifyHyperlinkFieldResult(node);

        if (!modifyChildren || !node.isComposite())
            return;

        for (Node child : (Iterable<Node>) ((CompositeNode)node))
            modify(child, true);
    }

    private void modifyHyperlinkFieldResult(Node node)
    {
        Inline inline = ms.as(node, Inline.class);
        if (inline == null)
            return;

        if (!isHyperlinkFieldResult())
            return;

        removeHyperlinkSpecificAttributes(inline.getRunPr());
        addNotHyperlinkSpecificAttributes(inline.getRunPr());

        inline.getRunPr().remove(FontAttr.ISTD);
    }

    private boolean isHyperlinkFieldResult()
    {
        return mFieldCharCounter.isInFieldResult() &&
               mFieldCharCounter.getCurrentField().getFieldType() == FieldType.FIELD_HYPERLINK;
    }

    private static void removeHyperlinkSpecificAttributes(RunPr runPr)
    {
        for (int key : runPr.getKeys())
        {
            if (isSpecificHyperlinkAttribute(key))
                runPr.remove(key);
        }
    }

    private void addNotHyperlinkSpecificAttributes(RunPr runPr)
    {
        for (int key : getNotHyperlinkSpecificAttributes().getKeys())
        {
            if (runPr.containsKey(key))
            {
                runPr.remove(key);
            }
            else
            {
                runPr.add(key, getNotHyperlinkSpecificAttributes().get(key));
            }
        }
    }

    private RunPr getNotHyperlinkSpecificAttributes()
    {
        if (mNotHyperlinkSpecificAttributes == null)
        {
            Style hyperlinkStyle = mDocument.getStyles().getBySti(StyleIdentifier.HYPERLINK, false);
            if (hyperlinkStyle != null)
            {
                mNotHyperlinkSpecificAttributes = hyperlinkStyle.getExpandedRunPr(RunPrExpandFlags.NORMAL);
                for (int key : mNotHyperlinkSpecificAttributes.getKeys())
                {
                    if (isSpecificHyperlinkAttribute(key))
                        mNotHyperlinkSpecificAttributes.remove(key);
                }
            }
            else
            {
                mNotHyperlinkSpecificAttributes = new RunPr();
            }
        }

        return mNotHyperlinkSpecificAttributes;
    }

    private static boolean isSpecificHyperlinkAttribute(int attrKey)
    {
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

    private /*final*/ FieldCharCounter mFieldCharCounter = new FieldCharCounter();
    private /*final*/ Document mDocument;
    private RunPr mNotHyperlinkSpecificAttributes;
}

