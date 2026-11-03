// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Applies MERGEFORMAT to a field result.
/// </summary>
/// <remarks>
/// MERGEFORMAT applies run and paragraph formatting taken from the "old" result to the "new" result. It splits
/// the results to tokens, such as words, whitespace, and separators, and attempts to apply formatting on
/// token to tokem basis. Given we have both results represented by a jagged range of nodes, correct merge
/// of formatting requires a pretty sophisticated algorithm.
/// </remarks>
class MergeFormatApplier implements IFieldResultFormatApplier
{
    /// <summary>
    /// Ctor.
    /// </summary>
    MergeFormatApplier(Iterable<Node> oldResultNodes,
        Paragraph oldStartParagraph, Paragraph oldEndParagraph)
    {
        mOldResultNodes = oldResultNodes;
        mOldStartParagraph = oldStartParagraph;
        mOldEndParagraph = oldEndParagraph;
    }

    public void /*IFieldResultFormatApplier.*/applyFormat(NodeRange result) throws Exception
    {
        OldResultEnumerator oldResultEnumerator = new OldResultEnumerator(mOldResultNodes, mOldStartParagraph);

        Paragraph newResultStartParagraph = (Paragraph)result.getStart().getNode().getAncestor(NodeType.PARAGRAPH);
        Paragraph newResultEndParagraph = (Paragraph)result.getEnd().getNode().getAncestor(NodeType.PARAGRAPH);

        NewResultEnumerator.mergeFormat(
            result,
            newResultStartParagraph,
            newResultEndParagraph,
            oldResultEnumerator,
            mOldEndParagraph);
    }

    /// <summary>
    /// An collection containing runs and paragraphs that comprised old result.
    /// </summary>
    private /*final*/ Iterable<Node> mOldResultNodes;
    /// <summary>
    /// The paragraph that contained field's start.
    /// </summary>
    private /*final*/ Paragraph mOldStartParagraph;
    /// <summary>
    /// The paragraph that contained field's end.
    /// </summary>
    private /*final*/ Paragraph mOldEndParagraph;
}

