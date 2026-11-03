// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/12/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// A base class used to find a paragraph required to update a particular field.
/// </summary>
abstract class FieldParagraphFinder
{
    /// <summary>
    /// Seeks for the first paragraph which is confirmed by <see cref="ConfirmParagraphNode(Aspose.Words.Node)"/>
    /// for a field located in header/footer story.
    /// </summary>
    protected Paragraph findParagraphHeaderFooter(Field field) throws Exception
    {
        // Ensure that the field's update is initiated by layout. Otherwise, we could not use the display context.
        Debug.assertTrue(field.getUpdater().isUpdateInitiatedByLayout());

        // 1. Scan pages backward from the field's page to the first page.
        Paragraph paragraph = field.getUpdater().getDisplayContext().findParagraph(field.getStart(), this);

        if (paragraph == null)
        {
            onAllPagesScanned();

            // 2. Nothing has been found before the field, so we need to search for a paragraph after it.
            //    Scan the document forward starting from the field's section not to bother layout once again.
            Node parentSection = field.getStart().getAncestor(NodeType.SECTION);
            paragraph = findParagraph(parentSection, true, true);
        }

        return paragraph;
    }

    /// <summary>
    /// Notifies the finder that a whole page has been scanned.
    /// </summary>
    void notifyPageScanned()
    {
        onPageScanned();
    }

    /// <summary>
    /// Called when a whole page has been scanned.
    /// </summary>
    protected /*virtual*/ void onPageScanned()
    {
        // Do nothing.
    }

    /// <summary>
    /// Called when all of the interesting pages have been scanned.
    /// </summary>
    protected /*virtual*/ void onAllPagesScanned()
    {
        // Do nothing.
    }

    /// <summary>
    /// Seeks for the first paragraph which is confirmed by <see cref="ConfirmParagraphNode(Aspose.Words.Node)"/>
    /// starting from the specified node in forward or backward direction. Optionally includes the start node
    /// and its children (if any) to the search.
    /// </summary>
    protected Paragraph findParagraph(Node startNode, boolean isForward, boolean includeStart) throws Exception
    {
        DocumentPosition position = (isForward ^ includeStart)
            ? DocumentPosition.createPositionAfter(startNode)
            : DocumentPosition.createPositionBefore(startNode);

        // We should skip the first move if the start node should be included to the search.
        boolean skipMove = includeStart;

        while (skipMove || position.move(null, isForward, true, true, false, true))
        {
            Paragraph paragraph = confirmParagraphNode(position.getNode());
            if (paragraph != null)
                return paragraph;

            skipMove = false;
        }

        return null;
    }

    /// <summary>
    /// Checks whether the specified candidate node is a paragraph satisfying some conditions defined by
    /// the derived class and if it is, returns it as a paragraph, otherwise returns <c>null</c>.
    /// </summary>
    Paragraph confirmParagraphNode(Node candidateNode) throws Exception
    {
        if ((candidateNode == null) || (candidateNode.getNodeType() != NodeType.PARAGRAPH))
            return null;

        return confirmParagraph((Paragraph)candidateNode);
    }

    /// <summary>
    /// When implemented in a derived class, checks whether the specified candidate paragraph satisfies some
    /// conditions defined by the derived class and if it is, returns it, otherwise returns <c>null</c>.
    /// </summary>
    abstract Paragraph confirmParagraph(Paragraph candidateParagraph) throws Exception;

    /// <summary>
    /// When implemented in a derived class, gets a value indicating whether a page should be scanned in
    /// forward direction while searching for a paragraph.
    /// </summary>
    abstract boolean isForwardPageScan();

    /*virtual*/ boolean needCachePageScanResult(Paragraph paragraph)
    {
        return false;
    }
}

