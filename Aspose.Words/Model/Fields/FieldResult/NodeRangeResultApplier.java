// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Applies a field result represented by a node range.
/// </summary>
class NodeRangeResultApplier extends FieldResultApplier
{
    NodeRangeResultApplier(Field field, NodeRange result)
    {
    	super(field);
	
        mResult = result;
    }

    protected /*override*/ void applyResultCore() throws Exception
    {
        // WORDSNET-6625 If a NodeRange is empty or void need just to exit because NodeCopier doesn't validate
        // input parameters and empty NodeRange leads to NullReferenceException.
        if (mResult == null)
            return;

        if (mResult.isVoid() || mResult.isEmpty())
            return;

        /*FieldTokenDecoderOptions*/int decoderOptions = FieldTokenDecoderOptions.NONE;
        decoderOptions = FieldTokenDecoderOptionsUtil.withTrimDoubleQuotes(decoderOptions, !FieldUtil.preserveDoubleQuotesInResult(getField().getType()));
        decoderOptions = FieldTokenDecoderOptionsUtil.withEscapeChars(decoderOptions, !FieldUtil.preserveEscapingBackslashesInResult(getField().getType()));
        CompositeModifier modifier = new CompositeModifier(

            new FieldTokenDecoderNodeModifier(mResult, decoderOptions),
            new CommentIdsRegeneratorNodeModifier(),
            getField().getResultModifier());

        // WORDSNET-15968 There is a contract between Field Engine and Layout: field updating should not affect nodes outside field results to make layout model sync with DOM.
        // Layout considers field end parent paragraph as not a part of field result, even if it holds result nodes.
        // NodeCopier.CopyWithoutFields splits field end paragraph and moves field end into new one. This breaks the contract.
        Paragraph endParagraph = preserveFieldEndParentParagraph();

        // Copy nodes from the source to the destination, decoding nodes.
        final /*NodeCopierOptions*/int OPTIONS = NodeCopierOptions.USE_SOURCE_START_ANCESTOR_PR |
                                          NodeCopierOptions.SKIP_CROSS_STRUCTURE_ANNOTATIONS |
                                          NodeCopierOptions.CLONE_NODE |
                                          NodeCopierOptions.PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS;
        NodeCopier.copyWithoutFields(mResult, getField().getEnd(), modifier, null, false, OPTIONS);

        restoreFieldEndParentParagraph(endParagraph);
    }

    private Paragraph preserveFieldEndParentParagraph()
    {
        if (getField().getStart().getParentNode() == getField().getEnd().getParentNode())
            return null;

        if (getField().getEnd().getParentNode().getNodeType() != NodeType.PARAGRAPH)
            return null;

        Paragraph endParagraph = (Paragraph)getField().getEnd().getParentNode();
        Paragraph endParagraphClone = (Paragraph)endParagraph.deepClone(false);

        endParagraphClone.insertAfter(endParagraph.getFirstChild(), null, null);
        endParagraph.insertPrevious(endParagraphClone);

        return endParagraph;
    }

    private void restoreFieldEndParentParagraph(Paragraph endParagraph)
    {
        if (endParagraph == null)
            return;

        Paragraph endParagraphClone = (Paragraph)getField().getEnd().getParentNode();

        endParagraph.insertAfter(endParagraphClone.getFirstChild(), null, null);
        endParagraph.setParaPr(endParagraphClone.getParaPr());
        endParagraph.setParagraphBreakRunPr(endParagraphClone.getParagraphBreakRunPr());

        endParagraphClone.remove();
    }

    private /*final*/ NodeRange mResult;
}

