// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/05/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;


/// <summary>
/// Applies CHARFORMAT to a field result.
/// </summary>
class CharFormatApplier extends DefaultFormatApplier
{
    CharFormatApplier(Inline formattingSource)
    {
    	super(formattingSource.getExpandedRunPr(RunPrExpandFlags.NORMAL), new int[] { FontAttr.BIDI });
	
    }

    protected /*override*/ void applyFormatCore(NodeRange result)
    {
        super.applyFormatCore(result);

        Paragraph start = ms.as(NodeUtil.getAncestorOrSelf(result.getStart().getNode(), NodeType.PARAGRAPH), Paragraph.class);
        if (start == null)
            return;

        Paragraph end = ms.as(NodeUtil.getAncestorOrSelf(result.getEnd().getNode(), NodeType.PARAGRAPH), Paragraph.class);
        if (start == end)
            return;

        for (Node node : result)
        {
            if (node.getNodeType() != NodeType.PARAGRAPH)
                continue;

            Paragraph paragraph = (Paragraph)node;
            Paragraph previousParagraph = (Paragraph)paragraph.previousSiblingOfType(NodeType.PARAGRAPH);
            if (previousParagraph != null)
            {
                previousParagraph.setParagraphBreakRunPr(adjustParagraphBreakRunPr(
                    previousParagraph.getParagraphBreakRunPr(),
                    previousParagraph));
            }
        }
    }

    protected /*override*/ RunPr adjustRunPr(RunPr original, Node owner)
    {
        RunPr result = super.adjustRunPr(original, owner);

        // Since the source formatting contains expanded values we may not need some of them
        // if the result node inherits the same values. Remove duplicated ones.
        RunPr inheritedAttrs = getInheritedRunPrAttrs(owner);
        result.removeEquals(inheritedAttrs);

        return result;
    }

    private static RunPr getInheritedRunPrAttrs(Node source)
    {
        final /*RunPrExpandFlags*/int INHERITED_FORMATTING_FLAG = RunPrExpandFlags.NO_DIRECT_FORMATTING;

        RunPr inheritedAttrs;

        switch (source.getNodeType())
        {
            case NodeType.PARAGRAPH:
                Paragraph paragraph = (Paragraph)source;
                inheritedAttrs = paragraph.getExpandedParagraphBreakRunPr(INHERITED_FORMATTING_FLAG);
                break;
            case NodeType.RUN:
            case NodeType.SHAPE:
            case NodeType.SPECIAL_CHAR:
                IInline inline = (IInline)source;
                inheritedAttrs = inline.getExpandedRunPr_IInline(INHERITED_FORMATTING_FLAG);
                break;
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                StructuredDocumentTag sdt = (StructuredDocumentTag)source;
                inheritedAttrs = new RunPr();
                InlineHelper.expandRunPr(
                    sdt.getDocument(),
                    (Paragraph)sdt.getAncestor(NodeType.PARAGRAPH),
                    sdt.getContentsRunPr(),
                    inheritedAttrs,
                    INHERITED_FORMATTING_FLAG);
                break;
            default:
                throw new IllegalArgumentException();
        }

        // The style identifier itself is not an inherited attribute.
        inheritedAttrs.remove(FontAttr.ISTD);

        return inheritedAttrs;
    }

    private RunPr adjustParagraphBreakRunPr(RunPr original, Node owner)
    {
        RunPr result = adjustRunPr(original, owner);
        original.mirrorTo(result, FontAttr.RSID_R, FontAttr.RSID_R_PR);
        return result;
    }
}

