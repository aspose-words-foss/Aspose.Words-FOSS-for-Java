// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import java.util.Collection;
import com.aspose.ms.System.msObject;


/// <summary>
/// Keeps together all methods related to accept/reject revision procedures.
/// Knows how to handle revisions of each node type.
/// </summary>
/// <remarks>
/// RK: MS Word seems to block some "nasty" delete scenarios so
/// it simplifies our task. Here is what I've noticed MS Word does:
///
/// 1. Does not allow to delete a paragraph or section break before a table.
/// 2. Does not allow to delete last paragraph break of a non main text story (such as header or footer).
/// 3. Does not allow to delete cell end mark (last paragraph in a cell).
/// 4. Does not mark complete cell delete as a revision.
///</remarks>
/*static*/ class RevisionUtil
{
	/* Simulation of static class by using private constructor */
	private RevisionUtil()
	{}

    /// <summary>
    /// Perform accept/reject revision procedures on a node. Delay deletion of nodes during mass accept/reject scenarios.
    /// </summary>
    static void handleNodeRevision(Node node, RevisionHandlingContext context) throws Exception
    {
        context.setCurrentNode(node);

        // Nodes should be actually inserted/deleted here so suspend tracking during this operation.
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(node.getDocument());
        try /*JAVA: was using*/
        {
            if (node instanceof IInline)
                processInlineRevision(node, context);
            else if (node instanceof Paragraph)
                processParagraphRevision(node, context);
            else if (node instanceof Section)
                processSectionRevision(node, context);
            else if (node instanceof Row)
                processRowRevision(node, context);
            else if (node instanceof Cell)
                processCellRevision(node, context);
            else if (node instanceof StructuredDocumentTag)
                processSdtRevision(node, context);
            else
                Debug.fail(msString.format("Unknown node type {0}", node.getNodeType()));

            if (context.isSingleRevision())
                processDelayedNodes(context, node.getDocument());
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
    }

    /// <summary>
    /// Deletes all inline/para/row/cell/sdt nodes that were collected during the rejection/acceptance of revision(s).
    /// </summary>
    static void processDelayedNodes(RevisionHandlingContext context, DocumentBase document) throws Exception
    {
        ArrayList<Node> deferredDeletionParagraphs = new ArrayList<Node>();

        delayedDeleteSdts(context.getDelayedSdts());
        delayedDeleteInlines(context.getDelayedInlines(), context);
        delayedDeleteParas(context.getDelayedParagraphs(), context, deferredDeletionParagraphs);
        delayedDeleteRows(context.getDelayedRows());
        delayedDeleteCells(context.getDelayedCells());
        delayedDeleteParas(deferredDeletionParagraphs, context, null);

        processSdtNesting(context);

        processInlinedSdts(context.getInlinedSdts());

        if (context.isSingleRevision())
        {
            MoveRange range = context.getCurrentMoveRange();
            if ((range != null) && range.isEmpty())
                range.remove();
        }
        else
        {
            deleteMoveRanges(document);
        }
    }

    /// <summary>
    /// Returns collection of formatting attributes as generic <see cref="WordAttrCollection"/>.
    /// </summary>
    /// <remarks>
    /// 1) This is limited implementation implemented for Revision API purposes. It does not deal with
    /// other nodes having properties e.g. ShapeBase et al.
    /// 2) Some nodes have more than one formatting collection, e.g. <see cref="Paragraph"/> contains both
    /// <see cref="ParaPr"/> (which is returned by this method) and
    /// <see cref="Paragraph.ParagraphBreakRunPr"/> (which is not returned).
    /// </remarks>
    static WordAttrCollection getNodeFormatting(Node node)
    {
        return (((((tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) != null ? (tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) : tryGetSectionAttrs(node)) != null ? ((tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) != null ? (tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) : tryGetSectionAttrs(node)) : tryGetRowAttrs(node)) != null ? (((tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) != null ? (tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) : tryGetSectionAttrs(node)) != null ? ((tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) != null ? (tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) : tryGetSectionAttrs(node)) : tryGetRowAttrs(node)) : tryGetCellAttrs(node)) != null ? ((((tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) != null ? (tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) : tryGetSectionAttrs(node)) != null ? ((tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) != null ? (tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) : tryGetSectionAttrs(node)) : tryGetRowAttrs(node)) != null ? (((tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) != null ? (tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) : tryGetSectionAttrs(node)) != null ? ((tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) != null ? (tryGetInlineAttrs(node) != null ? tryGetInlineAttrs(node) : tryGetParagraphAttrs(node)) : tryGetSectionAttrs(node)) : tryGetRowAttrs(node)) : tryGetCellAttrs(node)) : tryGetSdtAttrs(node));
    }

    /// <summary>
    /// Returns True if the specified node has revision.
    /// </summary>
    static boolean hasRevision(Node node)
    {
        boolean result = false;
        WordAttrCollection formatting = getNodeFormatting(node);
        if (formatting != null)
            result = formatting.hasRevisions() || formatting.hasEmptyFormatRevision();

        // Paragraph has extra formatting that is checked for revisions separately.
        if (!result && (node instanceof Paragraph))
        {
            Paragraph par = (Paragraph)node;
            result = par.hasRevisions();
            result |= par.getParagraphBreakRunPr().hasEmptyFormatRevision();
        }

        if (!result && (node instanceof FieldEnd))
        {
            FieldEnd fieldEnd = ((FieldEnd)node);
            result = fieldEnd.getRunPr().hasNumberRevision();
        }

        if (!result && (node instanceof StructuredDocumentTag))
        {
            StructuredDocumentTag sdt = (StructuredDocumentTag)node;
            result = sdt.getContentsRunPr().hasRevisions() || sdt.getEndCharacterRunPr().hasRevisions();
            result |= sdt.getEndCharacterRunPr().hasEmptyFormatRevision();
        }

        return result;
    }

    private static WordAttrCollection tryGetInlineAttrs(Node node)
    {
        IInline inline = ms.as(node, IInline.class);
        return inline != null
            ? inline.getRunPr_IInline()
            : null;
    }

    private static WordAttrCollection tryGetParagraphAttrs(Node node)
    {
        Paragraph para = ms.as(node, Paragraph.class);
        return para != null
            ? para.getParaPr()
            : null;
    }

    private static WordAttrCollection tryGetSectionAttrs(Node node)
    {
        Section section = ms.as(node, Section.class);
        return section != null
            ? section.getSectPr()
            : null;
    }

    private static WordAttrCollection tryGetRowAttrs(Node node)
    {
        Row row = ms.as(node, Row.class);
        return row != null
            ? row.getTablePr()
            : null;
    }

    private static WordAttrCollection tryGetCellAttrs(Node node)
    {
        Cell cell = ms.as(node, Cell.class);
        return cell != null
            ? cell.getCellPr()
            : null;
    }

    private static WordAttrCollection tryGetSdtAttrs(Node node)
    {
        StructuredDocumentTag sdt = ms.as(node, StructuredDocumentTag.class);
        return sdt != null
            ? sdt.getContentsRunPr()
            : null;
    }

    /// <summary>
    /// Returns a flag indicating if the specified node or any of its children have revisions.
    /// </summary>
    static boolean hasRevisions(Node node) throws Exception
    {
        return NodeUtil.findChildOrSelf(node, new RevisionNodeMatcher()) != null;
    }

    /// <summary>
    /// Processes SDT revision.
    /// </summary>
    private static void processSdtRevision(Node node, RevisionHandlingContext context)
    {
        StructuredDocumentTag sdt = (StructuredDocumentTag)node;
        if (context.isAcceptance())
        {
            sdt.getEndCharacterRunPr().acceptFormatRevision();
            acceptRevision(sdt, sdt.getContentsRunPr(), context);
        }
        else
        {
            sdt.getEndCharacterRunPr().rejectFormatRevision();
            rejectRevision(sdt, sdt.getContentsRunPr(), context);
        }
    }

    private static void processCellRevision(Node node, RevisionHandlingContext context)
    {
        Cell cell = (Cell)node;
        if (context.isAcceptance())
            acceptRevision(cell, cell.getCellPr(), context);
        else
            rejectRevision(cell, cell.getCellPr(), context);
    }

    private static void processSectionRevision(Node node, RevisionHandlingContext context)
    {
        SectPr sectPr = ((Section)node).getSectPr();
        if (context.isAcceptance())
            sectPr.acceptFormatRevision();
        else
            sectPr.rejectFormatRevision();
    }

    private static void processParagraphRevision(Node node, RevisionHandlingContext context)
    {
        Paragraph para = (Paragraph)node;
        if (context.isAcceptance())
            acceptParagraphRevision(para, context);
        else
            rejectParagraphRevision(para, context);
    }

    private static void processInlineRevision(Node node, RevisionHandlingContext context)
    {
        if (node.getParentNode() == null)
            return;

        if (context.isAcceptance())
            acceptInlineRevision(node, context);
        else
            rejectInlineRevision(node, context);
    }

    private static void processRowRevision(Node node, RevisionHandlingContext context)
    {
        Row row = (Row)node;
        if (context.isAcceptance())
            acceptRevision(row, row.getTablePr(), context);
        else
            rejectRevision(row, row.getTablePr(), context);

        // In order to get typed access to row cells it is required to use row.Cells collection.
        for (Cell cell : (Iterable<Cell>) row.getCells())
        {
            CellPr cellPr = cell.getCellPr();
            if (isFakeCellFormatRevision(cellPr))
                cellPr.remove(RevisionAttr.FORMAT_REVISION);
        }
    }

    /// <summary>
    /// Accepts revision defined in the specified run properties.
    /// </summary>
    private static void acceptRevision(Node node, WordAttrCollection properties, RevisionHandlingContext context)
    {
        properties.acceptFormatRevision();

        // To accept an inserted/movedTo revision simply remove the revision object.
        properties.remove(RevisionAttr.INSERT_REVISION);
        properties.remove(RevisionAttr.MOVE_TO_REVISION);

        if (needDeleteNodeOnAccepting(properties, context))
        {
            context.addDelayedNode(node);
            if (properties.hasMoveFromRevision())
                processNextUnmovedNodes(context);
        }
    }

    /// <summary>
    /// Rejects revision defined in the specified run properties.
    /// </summary>
    private static void rejectRevision(Node node, WordAttrCollection properties, RevisionHandlingContext context)
    {
        properties.rejectFormatRevision();

        // To reject an deleted/movedFrom revision simply remove the revision object.
        properties.remove(RevisionAttr.DELETE_REVISION);
        properties.remove(RevisionAttr.MOVE_FROM_REVISION);

        if (needDeleteNodeOnRejecting(properties, context))
        {
            context.addDelayedNode(node);
            if (properties.hasMoveToRevision())
                processNextUnmovedNodes(context);
        }
    }

    /// <summary>
    /// When writing row format revisions MS Word adds fake format revisions of cells.
    /// They not visible in Word DOM and should be removed together with Row, otherwise row is messed up upon accepting/rejecting changes.
    /// </summary>
    private static boolean isFakeCellFormatRevision(CellPr cellPr)
    {
        return !cellPr.hasFormatRevision() && (cellPr.getFormatRevision() != null);
    }

    private static void acceptParagraphRevision(Paragraph para, RevisionHandlingContext context)
    {
        // To accept paragraph formatting, apply the revision attributes.
        para.getParaPr().acceptFormatRevision();

        // To accept bullet and numbered revision simply remove the revision object.
        para.getParaPr().remove(RevisionAttr.NUMBER_REVISION);

        RunPr pBreakRunPr = para.getParagraphBreakRunPr();

        // To accept run formatting on the paragraph, apply the revision attributes.
        pBreakRunPr.acceptFormatRevision();

        // WORDSNET-22956 Do not process other than formatting revisions
        // when requested to accept only formatting single revision.
        if (!context.isSingleRevision() || (context.getRevisionType() != RevisionType.FORMAT_CHANGE))
        {
            // To accept an inserted/movedTo paragraph simply remove the revision object.
            pBreakRunPr.remove(RevisionAttr.INSERT_REVISION);
            pBreakRunPr.remove(RevisionAttr.MOVE_TO_REVISION);

            // To accept a deleted paragraph, cannot delete the paragraph now, do it later.
            if (needDeleteNodeOnAccepting(pBreakRunPr, context))
                processDeletingParagraph(para, context, pBreakRunPr.hasMoveFromRevision());
        }
    }

    private static void rejectParagraphRevision(Paragraph para, RevisionHandlingContext context)
    {
        // To reject paragraph formatting, remove the revision attributes.
        para.getParaPr().rejectFormatRevision();

        // To reject bullet and numbered revision simply remove the revision object.
        para.getParaPr().remove(RevisionAttr.NUMBER_REVISION);

        RunPr pBreakRunPr = para.getParagraphBreakRunPr();

        // To reject run formatting on the paragraph, remove the revision attributes.
        pBreakRunPr.rejectFormatRevision();

        // WORDSNET-22956 Do not process other than formatting revisions
        // when requested to reject only formatting single revision.
        if (!context.isSingleRevision() || (context.getRevisionType() != RevisionType.FORMAT_CHANGE))
        {
            // To reject a deleted/moved paragraph simply remove the revision object.
            pBreakRunPr.remove(RevisionAttr.DELETE_REVISION);
            pBreakRunPr.remove(RevisionAttr.MOVE_FROM_REVISION);

            // To reject an inserted paragraph, cannot delete the paragraph now, do it later.
            if (needDeleteNodeOnRejecting(pBreakRunPr, context))
                processDeletingParagraph(para, context, pBreakRunPr.hasMoveToRevision());
        }
    }

    /// <summary>
    /// Processes a revised paragraph, which is about to be deleted during handling revision.
    /// A move-revised paragraph may be preserved in some cases.
    /// </summary>
    private static void processDeletingParagraph(Paragraph para, RevisionHandlingContext context, boolean isMove)
    {
        MoveRange moveRange = context.getCurrentMoveRange();
        if (isMove && moveRange.isDefined())
        {
            MoveRangeRevisionHelper helper = new MoveRangeRevisionHelper(moveRange);
            boolean delayDeletion = !helper.isNonRevisedNodePreserved() ||
                         !hasUnmovedTrackables(para, moveRange.isMoveTo()) ||
                         areAllNextParagraphsDeleted(para, context);
            if (delayDeletion)
            {
                context.addDelayedNode(para);
                processNextUnmovedNodes(context);
            }
            else
            {
                // Looks like Word removes the original paragraph and creates a new one with keeping
                // some formatting at this case.
                int revisionAttr = moveRange.isMoveTo() ? RevisionAttr.MOVE_TO_REVISION : RevisionAttr.MOVE_FROM_REVISION;
                para.getParagraphBreakRunPr().remove(revisionAttr);

                // maybe it will be changed to removal of some particular attributes
                para.getParagraphBreakRunPr().clear();
            }
        }
        else
        {
            context.addDelayedNode(para);
        }
    }

    /// <summary>
    /// Returns <c>true</c> if all the next paragraphs in the move range have move revision.
    /// </summary>
    private static boolean areAllNextParagraphsDeleted(Paragraph para, RevisionHandlingContext context)
    {
        MoveRange moveRange = context.getCurrentMoveRange();

        Node node = para.getNextNonMarkupCompositeLimited();
        while ((node != null) && node.isAbove(moveRange.getEnd()))
        {
            if (hasNoMoveRevision(node, moveRange.isMoveTo()))
                return false;

            node = node.getNextNonMarkupCompositeLimited();
        }

        return true;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified node is a trackable node and has a move revision.
    /// </summary>
    static boolean hasMoveRevision(Node node, boolean checkForMoveTo)
    {
        IMoveTrackableNode trackable = ms.as(node, IMoveTrackableNode.class);
        if (trackable == null)
            return false;

        return checkForMoveTo
            ? trackable.getMoveToRevision() != null
            : trackable.getMoveFromRevision() != null;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified node is a trackable node and has no move revision.
    /// </summary>
    private static boolean hasNoMoveRevision(Node node, boolean checkForMoveTo)
    {
        IMoveTrackableNode trackable = ms.as(node, IMoveTrackableNode.class);
        if (trackable == null)
            return false;

        return checkForMoveTo
            ? trackable.getMoveToRevision() == null
            : trackable.getMoveFromRevision() == null;
    }

    /// <summary>
    /// Checks whether the node contains trackable children without move revision.
    /// </summary>
    private static boolean hasUnmovedTrackables(CompositeNode composite, boolean isMoveTo)
    {
        for (Node node : (Iterable<Node>) composite)
        {
            if (hasNoMoveRevision(node, isMoveTo))
                return true;

            if (node.isComposite() && hasUnmovedTrackables((CompositeNode)node, isMoveTo))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Accepts revisions for inline nodes.
    /// </summary>
    /// <param name="node">node with revisions to be accepted.</param>
    /// <param name="context">Revision acceptance/rejection context.</param>
    private static void acceptInlineRevision(Node node, RevisionHandlingContext context)
    {
        RunPr runPr = ((IInline)node).getRunPr_IInline();

        //To accept run formatting, apply the revision attributes.
        runPr.acceptRunSpecificFormatRevision(Run.isSymbolicCharacter(node.getText()));

        runPr.remove(RevisionAttr.NUMBER_REVISION);

        // WORDSNET-22956 Do not process other than formatting revisions
        // when requested to accept only formatting single revision.
        if (!context.isSingleRevision() || (context.getRevisionType() != RevisionType.FORMAT_CHANGE))
        {
            //To accept an inserted/movedTo run simply remove the revision object.
            runPr.remove(RevisionAttr.INSERT_REVISION);
            runPr.remove(RevisionAttr.MOVE_TO_REVISION);

            //To accept a deleted/movedFrom inline, delete it. If cannot delete the inline, do it later.
            if (needDeleteNodeOnAccepting(runPr, context))
            {
                // WORDSNET-11175 The customer document contains mail-merge field, in which field start and
                // field end nodes are marked as deleted, but field run is not. It should be deleted too.
                // WORDSNET-23209 Mark for deletion whole Field when accepting delete revision on any of FieldChar.
                FieldChar fieldChar = ms.as(node, FieldChar.class);
                if (fieldChar != null)
                    addDelayedField(fieldChar, context);
                else
                    context.addDelayedNode(node);

                if (runPr.hasMoveFromRevision())
                    processNextUnmovedNodes(context);
            }
        }
    }

    /// <summary>
    /// Rejects revisions for inline nodes.
    /// </summary>
    /// <param name="node">node with revisions to be deleted.</param>
    /// <param name="context">Revision acceptance/rejection context.</param>
    private static void rejectInlineRevision(Node node, RevisionHandlingContext context)
    {
        RunPr runPr = ((IInline)node).getRunPr_IInline();

        //To reject run formatting, apply the revision attributes.
        runPr.rejectFormatRevision();

        runPr.remove(RevisionAttr.NUMBER_REVISION);

        // WORDSNET-22956 Do not process other than formatting revisions
        // when requested to reject only formatting single revision.
        if (!context.isSingleRevision() || (context.getRevisionType() != RevisionType.FORMAT_CHANGE))
        {
            //To reject a deleted/moved run simply remove the revision object.
            runPr.remove(RevisionAttr.DELETE_REVISION);
            runPr.remove(RevisionAttr.MOVE_FROM_REVISION);

            //To reject an inserted/moved inline delete it. if cannot delete the inline, do it later.
            if (needDeleteNodeOnRejecting(runPr, context))
            {
                // WORDSNET-23209 Mark for deletion whole Field when rejecting insert revision on any of FieldChar.
                FieldChar fieldChar = ms.as(node, FieldChar.class);
                if (fieldChar != null)
                    addDelayedField(fieldChar, context);
                else
                    context.addDelayedNode(node);

                if (runPr.hasMoveToRevision())
                    processNextUnmovedNodes(context);
            }
        }
    }

    /// <summary>
    /// Returns a flag indicating whether the node containing the specified attribute collection should be deleted
    /// in the current operation of accepting revisions.
    /// </summary>
    private static boolean needDeleteNodeOnAccepting(WordAttrCollection attrCollection, RevisionHandlingContext context)
    {
        if (context.isSingleRevision())
        {
            // Delete only for the processing revision.
            if ((context.getRevisionType() == RevisionType.DELETION) && attrCollection.hasDeleteRevision())
                return true;

            if ((context.getRevisionType() == RevisionType.MOVING) && attrCollection.hasMoveFromRevision())
                return true;

            return false;
        }

        return attrCollection.hasDeleteRevision() || attrCollection.hasMoveFromRevision();
    }

    /// <summary>
    /// Returns a flag indicating whether the node containing the specified attribute collection should be deleted
    /// in the current operation of rejecting revisions.
    /// </summary>
    private static boolean needDeleteNodeOnRejecting(WordAttrCollection attrCollection, RevisionHandlingContext context)
    {
        if (context.isSingleRevision())
        {
            // Delete only for the processing revision.
            if ((context.getRevisionType() == RevisionType.INSERTION) && attrCollection.hasInsertRevision())
                return true;

            if ((context.getRevisionType() == RevisionType.MOVING) && attrCollection.hasMoveToRevision())
                return true;

            return false;
        }

        return attrCollection.hasInsertRevision() || attrCollection.hasMoveToRevision();
    }

    /// <summary>
    /// Adds all nodes of the Field to which the specified FieldChar node belongs
    /// to a corresponding delayed deletion list.
    /// </summary>
    private static void addDelayedField(FieldChar fieldChar, RevisionHandlingContext context)
    {
        // Here "FieldEnd" marked as deleted. If there is inline content which has not revision or marked as "inserted",
        // then MSW removes whole field with the content, although other content is not marked as deleted.

        // WORDSNET-16242 Remove all inline content include nested fields, when "FieldEnd" element
        // was added to the "Delete" revision.
        FieldBundle bundle = FieldBundle.getFieldBundle(fieldChar);
        Field field = new Field(bundle);

        for (Node fieldNode : field.getFieldRange())
        {
            IInline curInline = ms.as(fieldNode, IInline.class);
            RunPr curRunPr = curInline != null
                ? curInline.getRunPr_IInline()
                : null;

            if (curRunPr == null)
                continue;

            context.addDelayedNode(fieldNode);
        }
    }

    private static void delayedDeleteInlines(Collection<Node> deletedInlines, RevisionHandlingContext context)
    {
        for (Node inlineNode : deletedInlines)
            deleteInline(inlineNode, context);

        deletedInlines.clear();
    }

    private static void delayedDeleteParas(ArrayList<Node> deletedParas, RevisionHandlingContext context,
        ArrayList<Node> deferredDeletionParagraphs)
    {
        Paragraph pendingParagraph = null;
        for (Node node : deletedParas)
        {
            Paragraph para = (Paragraph) node;
            if (!deleteRevisedParagraph(para, context, deferredDeletionParagraphs))
            {
                Debug.assertTrue(pendingParagraph == null);
                pendingParagraph = para;
                continue;
            }

            if (pendingParagraph != null)
            {
                if (deleteRevisedParagraph(pendingParagraph, context, deferredDeletionParagraphs))
                    pendingParagraph = null;
            }
        }

        deletedParas.clear();
    }

    /// <summary>
    /// Deletes rows that are contained in the specified list.
    /// </summary>
    private static void delayedDeleteRows(ArrayList<Node> deletedRows)
    {
        for (Node row : deletedRows)
            deleteNode((Row)row);

        deletedRows.clear();
    }

    /// <summary>
    /// Deletes rows that are contained in the specified list.
    /// </summary>
    private static void delayedDeleteCells(ArrayList<Node> deletedCells)
    {
        for (Node cell : deletedCells)
            deleteNode(cell);

        deletedCells.clear();
    }

    /// <summary>
    /// Deletes SDTs that are contained in the specified list.
    /// </summary>
    private static void delayedDeleteSdts(ArrayList<Node> deletedSdts) throws Exception
    {
        for (Node sdt : deletedSdts)
            deleteSdt((StructuredDocumentTag)sdt);

        deletedSdts.clear();
    }

    /// <summary>
    /// Deletes node marked with deleted or moved revision.
    /// </summary>
    private static void deleteNode(Node node)
    {
        if (node.getParentNode() != null)
            node.remove();
    }

    /// <summary>
    /// Deletes inline node marked with deleted or moved revision.
    /// </summary>
    private static void deleteInline(Node inline, RevisionHandlingContext context)
    {
        CompositeNode parent = inline.getParentNode();

        // Remove deleted/moved node.
        inline.remove();

        // WORDSNET-9525 It seems like if the content of smart tag has been deleted, the smart tag should be deleted too.
        if (parent.getNodeType() == NodeType.SMART_TAG || parent.getNodeType() == NodeType.COMMENT)
            removeEmptyParent(parent, parent.getNodeType(), context);
    }

    /// <summary>
    /// Deletes SDT node marked with deleted or moved revision.
    /// </summary>
    private static void deleteSdt(StructuredDocumentTag sdt) throws Exception
    {
        // IN. It seems MS Word does not update content when accepts deleted SDTs.
        // Also, in general case I think when user removes SDT in MS Word GUI, it should
        // remove all SDT's content as well, so there is no need to update it.
        sdt.removeSelfOnly(false);
    }

    private static void removeEmptyParent(CompositeNode compositeNode, /*NodeType*/int nodeType,
        RevisionHandlingContext context)
    {
        if (context.hasPreservedTrackables(compositeNode, false))
            return;

        if (compositeNode.getParentNode() == null)
            return;

        if (compositeNode.getParentNode().getNodeType() == nodeType)
            removeEmptyParent(compositeNode.getParentNode(), nodeType, context);

        compositeNode.remove();
    }

    /// <summary>
    /// Usually MS Word removes unrevised trackables in move-from range on acceptance of revisions, but may
    /// preserve them in some cases.
    /// It is implemented that unrevised nodes are processed only till the next revised node to remove nodes little
    /// by little on accepting single revisions and for compatibility with the old code.
    /// </summary>
    private static void processNextUnmovedNodes(RevisionHandlingContext context)
    {
        MoveRange moveRange = context.getCurrentMoveRange();
        if (!moveRange.isDefined())
            return;

        MoveRangeRevisionHelper helper = new MoveRangeRevisionHelper(moveRange);
        if (helper.isNonRevisedNodePreserved())
            return;

        Node node = getNextNodeOfMoveRange(context.getCurrentNode(), helper);
        while (node != null)
        {
            if (node instanceof IMoveTrackableNode)
            {
                // Process nodes till the next move revision only.
                if (hasMoveRevision(node, moveRange.isMoveTo()))
                    break;

                if (isNodeForRemoval(node, helper))
                {
                    context.addDelayedNode(node);
                }
                // Check preconditions whether the current node (SDT) may be nested in the first SDT of the move
                // range and store it in the context. Final decision about the nesting will be made after removing
                // child nodes of the SDTs. See the cases 7, 8 (nested) and 2 (not nested) of WORDSNET-15250
                else if (isToNestInFirstSdt(node, helper))
                {
                    context.addSdtNesting(
                        (StructuredDocumentTag)helper.getTopStartNode(),
                        (StructuredDocumentTag)helper.getTopEndNode(),
                        helper.canNestInlineSdts());
                }
            }

            node = getNextNodeOfMoveRange(node, helper);

            if (node == context.getCurrentNode())
                break;
        }
    }

    /// <summary>
    /// Returns <c>true</c> if this unrevised node is about to be deleted during handling revisions.
    /// </summary>
    private static boolean isNodeForRemoval(Node node, MoveRangeRevisionHelper helper)
    {
        if (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
        {
            StructuredDocumentTag sdt = (StructuredDocumentTag)node;
            return
                !helper.isMiddleSdtPreserved() &&
                !(helper.isFirstSdtPreserved() && helper.isFirstSdt(sdt)) &&
                !(helper.isLastSdtPreserved() && helper.isLastSdt(sdt));
        }

        if ((node.getNodeType() == NodeType.PARAGRAPH) || (node instanceof IInline))
        {
            // If move range end is at this paragraph, then the paragraph is after move range, preserve it.
            if ((node.getNodeType() == NodeType.PARAGRAPH) && helper.getEndNode().isAncestorNode(node))
                return false;

            // Looks like unrevised inlines are preserved by MS Word with the same rules as "middle" paragraphs.
            if (helper.isMiddleParagraphPreserved())
                return false;

            if (node.getNodeType() != NodeType.PARAGRAPH)
                return true;

            if (!helper.isLastParagraphPreserved())
                return true;

            if (!helper.isLastParagraph((Paragraph)node))
                return true;

            return false;
        }

        // Cells/rows/tables are processed on paragraph removal.
        return false;
    }

    /// <summary>
    /// Returns <c>true</c> if this unrevised node is last SDT of the move range, which, if it has no children,
    /// is intended to move into the first SDT of the range.
    /// </summary>
    private static boolean isToNestInFirstSdt(Node node, MoveRangeRevisionHelper helper)
    {
        if (node.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return false;

        if (!helper.isLastSdtNestedInFirst())
            return false;

        if (!helper.isLastSdt((StructuredDocumentTag)node))
            return false;

        if (helper.getTopStartNode().getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return false;

        return true;
    }

    /// <summary>
    /// Get next node of the specified node with returning deepest child at first.
    /// </summary>
    private static Node getNextNodeOfMoveRange(Node node, MoveRangeRevisionHelper helper)
    {
        // WORDSNET-18520 Do not search inside empty MoveRange.
        if (helper.getRange().isEmpty())
            return null;

        if (!NodeUtil.isAncestorOrSelf(helper.getEndNode(), node) && (node.getNextSibling() != null))
            return getDeepestFirstChild(node.getNextSibling());

        Node parentNode = node.getParentNode();
        return parentNode == helper.getRangeParent()
            ? getDeepestFirstChild(helper.getStartNode())
            : parentNode;
    }

    /// <summary>
    /// Gets deepest first descendant of the specified node.
    /// </summary>
    private static Node getDeepestFirstChild(Node node)
    {
        while (node.isComposite() && ((CompositeNode)node).hasChildNodes())
            node = ((CompositeNode)node).getFirstChild();
        return node;
    }

    /// <summary>
    /// Deletes paragraph marked with revision.
    /// </summary>
    private static boolean deleteRevisedParagraph(Paragraph para, RevisionHandlingContext context,
        ArrayList<Node> deferredDeletionParagraphs)
    {
        if (para.isEndOfCell())
        {
            deleteLastParaFromCell(para, context);
            return true;
        }

        if (para.isEndOfSection())
        {
            deleteLastParaFromSection(para, context);
            return true;
        }

        return deleteParagraphCore(para, context, deferredDeletionParagraphs);
    }

    /// <summary>
    /// Deleting last paragraph from a cell in a revision means the whole cell is deleted.
    /// I also delete the row and the table if they become empty.
    /// </summary>
    private static void deleteLastParaFromCell(Paragraph para, RevisionHandlingContext context)
    {
        Cell cell = para.getParentCell();
        Row row = cell.getParentRow();
        Table table = row.getParentTable();

        cell.remove();

        if (!row.isRemoved())
        {
            // AllRevisions was passed only on acceptance of all revisions, keep the old behaviour for now.
            ArrayList<Revision> revisions = context.isAcceptance() ? context.getAllRevisions() : null;
            if (isRowEmpty(row, revisions))
                row.remove();
        }

        if ((table != null) && (table.getFirstRow() == null))
            table.remove();
    }

    /// <summary>
    /// Checks that row is empty considering conflicts.
    /// </summary>
    /// <remarks>
    /// AM. WORDSNET-8664 Column is inserted and row is deleted.
    /// We have some kind of conflicts here i.e cell is inserted into deleted row.
    /// This method checks this case.
    /// </remarks>
    private static boolean isRowEmpty(Row row, ArrayList<Revision> allRevisions)
    {
        if (row.getFirstCell() == null)
            return true;

        // andrnosk: WORDSNET-9656 If row contains deleted cell, consider this row is not empty.
        for (Cell cell : (Iterable<Cell>) row.getCells())
        {
            if (!isInsertedCell(cell, allRevisions) || isDeletedCell(cell, allRevisions))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Indicates that given cell is insertion revision.
    /// </summary>
    private static boolean isInsertedCell(Cell cell, ArrayList<Revision> allRevisions)
    {
        return checkCellRevision(cell, allRevisions, RevisionType.INSERTION);
    }

    /// <summary>
    /// Indicates that given cell is deletion revision.
    /// </summary>
    private static boolean isDeletedCell(Cell cell, ArrayList<Revision> allRevisions)
    {
        return checkCellRevision(cell, allRevisions, RevisionType.DELETION);
    }

    private static boolean checkCellRevision(Cell cell, ArrayList<Revision> allRevisions, /*RevisionType*/int revisionType)
    {
        if (allRevisions == null)
            return false;

        for (Revision revision : allRevisions)
        {
            if (revision.getRevisionType() != revisionType)
                continue;

            if (revision.getParentNode() == null)
                continue;

            if (cell.getLastParagraph() != revision.getParentNode())
                continue;

            return true;
        }

        return false;
    }

    /// <summary>
    /// If the last run in the last paragraph of the main story was deleted,
    /// move the contents to the next section and delete this section.
    /// </summary>
    private static void deleteLastParaFromSection(Paragraph para, RevisionHandlingContext context)
    {
        Body srcBody = (Body)para.getFirstNonMarkupParentNode();
        Section srcSection = srcBody.getParentSection();

        Section dstSection = (Section)srcSection.getNextSibling();
        if (dstSection != null)
        {
            Body dstBody = dstSection.getBody();
            Paragraph dstPara = dstBody.getFirstParagraph();

            //Move all inline nodes from the paragraph to the beginning of the first paragraph in the next section.
            if (para.hasChildNodes())
                dstPara.insertAfter(para.getFirstChild(), null, null);

            if (!msObject.referenceEquals(para.getParentNode(), srcBody))
            {
                // WORDSNET-15042 End of section paragraph is a child of SDT.
                dstBody.insertAfter(srcBody.getFirstChild(), null, null);
                para.getParagraphBreakRunPr().remove(RevisionAttr.DELETE_REVISION);
            }
            else
            {
                //Move all block-level nodes from the body to the beginning of the next section body.
                dstBody.insertAfter(srcBody.getFirstChild(), para, null);
            }

            // Headers/footers are moved only if destination section has no headers/footers.
            if (dstSection.getHeadersFooters().getCount() == 0)
            {
                // Move headers and footers.
                while (srcSection.getHeadersFooters().getCount() > 0)
                {
                    HeaderFooter srcHeaderFooter = srcSection.getHeadersFooters().get(0);

                    // Move whole header/footer to destination section.
                    dstSection.insertBefore(srcHeaderFooter, dstSection.getBody());
                }
            }

            srcSection.remove();
        }
        else
        {
            if (!deleteParagraphCore(para, context, null))
                removeEditRevisions(para);
        }
    }

    /// <summary>
    /// The paragraph mark was deleted, delete the paragraph and join with next.
    /// If the method returns <c>false</c>, behaviour depends on the next deleting paragraph: the next paragraph
    /// should be processed at first.
    /// </summary>
    private static boolean deleteParagraphCore(
        Paragraph para,
        RevisionHandlingContext context,
        ArrayList<Node> deferredDeletionParagraphs)
    {
        Node nextNode = para.getNextNonMarkupCompositeLimited();
        Paragraph nextPara = ms.as(nextNode, Paragraph.class);
        if (nextPara != null)
        {
            if (isIndividualStdChild(para, nextPara))
            {
                CompositeNode ancestor = (CompositeNode)Node.getCommonAncestor(para, nextPara);
                CompositeNode paraAncestor = para.getParentNode() == ancestor
                    ? para
                    : getSdtAncestorAsChildOf(para, ancestor);
                CompositeNode nextParaAncestor = nextPara.getParentNode() == ancestor
                    ? nextPara
                    : getSdtAncestorAsChildOf(nextPara, ancestor);

                boolean processDeletingParagraph = needProcessDeletingParagraphWithInliningSdts(
                    paraAncestor,
                    nextParaAncestor,
                    para,
                    nextPara,
                    context);

                if (processDeletingParagraph)
                {
                    return processDeletingParagraphWithInliningSdts(
                        para,
                        nextPara,
                        paraAncestor,
                        nextParaAncestor,
                        context);
                }
            }
        }
        else if (nextNode instanceof Table)
        {
            // If the next node is a table, delete the paragraph after rows/cells/paragraphs of the table will be
            // processed, since the table or its rows/cells may be deleted and contents of the currently deleting
            // paragraph will be lost.
            if (deferredDeletionParagraphs != null)
            {
                deferredDeletionParagraphs.add(para);
                return true;
            }

            nextPara = NodeUtil.findNextParagraph(para);
        }

        moveNextNonSdtAnnotations(para, nextPara);
        moveChildrenAndRemove(para, nextPara);

        return true;
    }

    private static boolean isIndividualStdChild(Node first, Node second)
    {
        CompositeNode firstParent = first.getParentNode();
        CompositeNode secondParent = second.getParentNode();

        if (firstParent == secondParent)
            return false;

        if (firstParent.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            return true;

        if (secondParent.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            return true;

        return false;
    }

    private static boolean needProcessDeletingParagraphWithInliningSdts(
        CompositeNode paraAncestor,
        CompositeNode nextParaAncestor,
        Paragraph para,
        Paragraph nextPara,
        RevisionHandlingContext context)
    {
        if (paraAncestor == null)
            return false;

        if (nextParaAncestor == null)
            return false;

        if ((paraAncestor == para) && (para.getFirstNonAnnotationChild() == null))
            return false;

        if (isAnyAncestorSdtDestinationToNestBlockSdts(para, paraAncestor, context))
            return false;

        if (isAnyAncestorSdtDestinationToNestBlockSdts(nextPara, nextParaAncestor, context))
            return false;

        return true;
    }

    /// <summary>
    /// Moves annotations except SDT range nodes located after the reference node to the beginning of
    /// the destination node.
    /// </summary>
    private static void moveNextNonSdtAnnotations(Node referenceNode, CompositeNode destinationNode)
    {
        if (destinationNode == null)
            return;

        Node nextNode = referenceNode.getNextSibling();
        Node destinationRefNode = null;

        while ((nextNode != null) && NodeUtil.isCrossStructureAnnotation(nextNode))
        {
            Node movingNode = nextNode;
            nextNode = nextNode.getNextSibling();

            if (NodeUtil.isStructuredDocumentTagNode(movingNode))
                continue;

            destinationNode.insertAfter(movingNode, destinationRefNode);
            destinationRefNode = movingNode;
        }
    }

    /// <summary>
    /// Moves all children of the deleting paragraph into the specified destination paragraph and deletes it.
    /// If there is no destination paragraph and the deleting paragraph has child nodes, it is preserved,
    /// edit revisions are removed.
    /// </summary>
    private static void moveChildrenAndRemove(Paragraph deletingPara, Paragraph destinationPara)
    {
        // Move all from the deleted paragraph into the next paragraph.
        if (deletingPara.hasChildNodes() && (destinationPara != null))
            destinationPara.insertAfter(deletingPara.getFirstChild(), null, null);

        // If there is no suitable paragraph to place content in, leave this paragraph and just remove deletion mark.
        if (deletingPara.hasChildNodes())
        {
            removeEditRevisions(deletingPara);
        }
        else
        {
            if (deletingPara.getParentNode() != null)
                deletingPara.remove();
        }
    }

    private static boolean isAnyAncestorSdtDestinationToNestBlockSdts(
        Node node,
        CompositeNode endAncestorSdt,
        RevisionHandlingContext context)
    {
        if (endAncestorSdt.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return false;

        do
        {
            node = node.getParentNode();
            if ((node == null) || (node.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG))
                return false;

            for (StructuredDocumentTag child : context.getMovingNestedSdts((StructuredDocumentTag)node))
            {
                for (Node para : (Iterable<Node>) child.getChildNodes(NodeType.PARAGRAPH, true))
                {
                    if (!context.isDeletingNode(para))
                        return true;
                }
            }
        }
        while (node != endAncestorSdt);

        return false;
    }

    /// <summary>
    /// Gets ancestor SDT of the specified node as child of the specified parent node.
    /// </summary>
    /// <param name="node">A node to go through parents to get a result SDT node.</param>
    /// <param name="parent">
    /// An ancestor of a node defined by the parameter above which direct child is searched for.
    /// </param>
    /// <returns>An achieved SDT node or null if any ancestor is not an SDT.</returns>
    private static CompositeNode getSdtAncestorAsChildOf(Node node, CompositeNode parent)
    {
        if (parent == null)
            return null;

        do
        {
            node = node.getParentNode();
            if ((node == null) || (node.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG))
                return null;
        }
        while (node.getParentNode() != parent);

        return (CompositeNode)node;
    }

    /// <summary>
    /// Clears all edit revisions in the specified run properties.
    /// </summary>
    static void removeEditRevisions(RunPr runPr)
    {
        runPr.remove(RevisionAttr.DELETE_REVISION);
        runPr.remove(RevisionAttr.INSERT_REVISION);
        runPr.remove(RevisionAttr.MOVE_FROM_REVISION);
        runPr.remove(RevisionAttr.MOVE_TO_REVISION);
    }

    /// <summary>
    /// Clears all revisions in the specified run properties.
    /// </summary>
    static void removeRevisions(RunPr runPr)
    {
        removeEditRevisions(runPr);
        runPr.remove(RevisionAttr.FORMAT_REVISION);
    }

    /// <summary>
    /// Clears all edit revisions of the specified paragraph.
    /// </summary>
    private static void removeEditRevisions(Paragraph para)
    {
        removeEditRevisions(para.getParagraphBreakRunPr());
    }

    /// <summary>
    /// Clears all edit revisions of the paragraph and copies formatting from the specified source paragraph.
    /// </summary>
    private static void removeEditRevisions(Paragraph para, Paragraph paraToCopyFormattingFrom)
    {
        para.setParagraphBreakRunPr(AttrCollectionExtension.deepClone(paraToCopyFormattingFrom.getParagraphBreakRunPr()));
        para.setParaPr(AttrCollectionExtension.deepClone(paraToCopyFormattingFrom.getParaPr()));

        removeEditRevisions(para);
    }

    /// <summary>
    /// Processes special cases with paragraphs inside SDTs to mimic MS Word behaviour on accepting revisions.
    /// </summary>
    /// <remarks>
    /// A parent block SDT of the deleting paragraph if present is converted to inline one to place into the
    /// destination paragraph. And a parent SDT of the destination paragraph if present is converted to inline
    /// one if any inlines are moved from the deleting paragraph into the destination one.
    /// Returns <c>false</c> if it is not possible to process the deleting paragraph because the next deleting
    /// paragraph should be processed at first. It happen if a parent SDT of the destination has several paragraphs
    /// and cannot be converted to inline yet.
    /// </remarks>
    private static boolean processDeletingParagraphWithInliningSdts(Paragraph deletingPara, Paragraph destinationPara,
        CompositeNode ancestorOfDeletingPara, CompositeNode ancestorOfDestinationPara, RevisionHandlingContext context)
    {
        // Converts <sdt><p> node structure of the deleting paragraph and/or the destination paragraph into <p><sdt>
        // and moves contents of the deleting paragraph into the destination one.

        // If parent SDT either of the deleting paragraph or of the destination one is not only paragraph in SDT,
        // MS Word preserves deleting paragraph except special case on moving revisions.
        if ((ancestorOfDeletingPara != deletingPara) &&
                !isOnlyNonMarkupCompositeInAncestor(deletingPara, ancestorOfDeletingPara))
        {
            removeEditRevisions(deletingPara);
            return true;
        }

        // A deleting paragraph may have no revisions at all if it is in a move range: check for delete and insert.
        boolean isMoveRevision =
            !deletingPara.getParagraphBreakRunPr().hasInsertRevision() &&
            !deletingPara.getParagraphBreakRunPr().hasDeleteRevision();
        if (isMoveRevision && (ancestorOfDestinationPara != destinationPara))
        {
            // If available place to move an empty SDT to is in an SDT after a move range, MS Word doesn't move
            // the SDT, just keeps it empty. After resaving in MS Word, a new paragraph is created with formatting
            // of the next paragraph and the empty SDT is placed into it. Emulate the same.

            // Move range may be entirely in the deleting paragraph now after deletion of other nodes: start
            // searching from a child of the paragraph (from the last child since MoveRangeFinder searches backward).
            Node searchStart = deletingPara.hasChildNodes() ? deletingPara.getLastChild() : deletingPara;
            MoveRange range = MoveRangeFinder.findMoveRange(searchStart, !context.isAcceptance());
            if ((range != null) &&
                (range.getEnd() != null) &&
                (range.getEnd().isAncestorNode(destinationPara) || range.getEnd().isAbove(destinationPara)))
            {
                removeEditRevisions(deletingPara, destinationPara);
                return true;
            }
        }

        if ((ancestorOfDestinationPara != destinationPara) &&
            !isOnlyNonMarkupCompositeInAncestor(destinationPara, ancestorOfDestinationPara))
        {
            if (isMoveRevision)
            {
                // The paragraph is not preserved if it is in a move revision/range: need to delete paragraphs of
                // the destination SDT at first.
                for (Node para : (Iterable<Node>) ancestorOfDestinationPara.getChildNodes(NodeType.PARAGRAPH, true))
                {
                    if (context.isDeletingNode(para))
                        return false;
                }
            }

            removeEditRevisions(deletingPara);
            return true;
        }

        // Check nodes between ancestors of the deleting and destination paragraphs.
        for (Node node = ancestorOfDeletingPara.getNextSibling();
             node != ancestorOfDestinationPara;
             node = node.getNextSibling())
        {
            if (NodeUtil.isCrossStructureAnnotation(node))
                continue;

            if ((node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG) &&
                ((CompositeNode)node).getFirstNonMarkupCompositeDescendant() == null)
                continue;

            removeEditRevisions(deletingPara);
            return true;
        }

        if (ancestorOfDeletingPara != deletingPara)
            exchangeParagraphWithAncestorSdt(deletingPara, (StructuredDocumentTag)ancestorOfDeletingPara, context);
        if (ancestorOfDestinationPara != destinationPara)
            exchangeParagraphWithAncestorSdt(destinationPara, (StructuredDocumentTag)ancestorOfDestinationPara, context);

        // Move SDTs and annotations between the paragraphs into the destination paragraph to preserve original order.
        Node currentNode = destinationPara.getPreviousSibling();
        while (currentNode != deletingPara)
        {
            Node previousNode = currentNode.getPreviousSibling();

            if (currentNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            {
                currentNode.remove();
                ((StructuredDocumentTag)currentNode).setLevel(MarkupLevel.INLINE);
            }

            destinationPara.insertAfter(currentNode, null);
            currentNode = previousNode;
        }

        destinationPara.insertAfter(deletingPara.getFirstChild(), null, null);
        deletingPara.remove();

        return true;
    }

    /// <summary>
    /// Exchanges the specified paragraph and its ancestor SDT in the way that the paragraph becomes a parent
    /// of the SDT.
    /// </summary>
    private static void exchangeParagraphWithAncestorSdt(Paragraph descendant, StructuredDocumentTag ancestor,
        RevisionHandlingContext context)
    {
        Debug.assertTrue(descendant.isAncestorNode(ancestor));

        StructuredDocumentTag parent = (StructuredDocumentTag)descendant.getParentNode();
        Node nextNode = descendant.getNextSibling();

        // Make the ancestor SDT of the destination paragraph to be of the inline level and move contents of
        // the paragraph into it. Place the SDT into the paragraph.
        ancestor.insertNext(descendant);

        ancestor.remove();
        ancestor.setLevel(MarkupLevel.INLINE);

        parent.insertBefore(descendant.getFirstChild(), null, nextNode);

        descendant.appendChild(ancestor);

        context.getInlinedSdts().add(parent);
    }

    /// <summary>
    /// Returns <c>true</c> if the specified node is the only non-markup composite node of the specified ancestor node.
    /// </summary>
    private static boolean isOnlyNonMarkupCompositeInAncestor(CompositeNode node, CompositeNode ancestor)
    {
        return
            (ancestor.getFirstNonMarkupCompositeDescendant() == node) &&
            (ancestor.getLastNonMarkupCompositeDescendant() == node);
    }

    /// <summary>
    /// Makes the specified empty SDT to be inline level and moves it to appropriate place.
    /// </summary>
    private static void processSdtNesting(RevisionHandlingContext context)
    {
        for (StructuredDocumentTag sdt : context.getParentsForNestedSdts())
        {
            if (sdt.getFirstNonMarkupCompositeDescendant() != null)
                continue;

            // If a destination is several nested SDTs, insert into the deepest child SDT.
            StructuredDocumentTag destination = getDeepestNestedSdt(sdt);

            for (StructuredDocumentTag child : context.getMovingNestedSdts(sdt))
            {
                if (canNestSdt(child, sdt, context))
                    destination.appendChild(child);
                destination = child;
            }

            destination.insertDefaultContentIfEmpty();
        }
    }

    /// <summary>
    /// Returns the deepest SDT of possible nested SDT chain started at the specified SDT node.
    /// </summary>
    private static StructuredDocumentTag getDeepestNestedSdt(StructuredDocumentTag sdt)
    {
        // Expect simple SDT chains only for now and take the last SDT.
        StructuredDocumentTag result = (StructuredDocumentTag)sdt.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, -1, true);
        return result != null ? result : sdt;
    }

    /// <summary>
    /// Additional checks for necessity to nest one SDT into another. Mimics behaviour of MS Word.
    /// </summary>
    /// <remarks>
    /// See the cases 7, 8 (nested) and 2 (not nested) of WORDSNET-15250 for more information.
    /// Some conditions have already been checked in <see cref="ProcessNextUnmovedNodes"/>.
    /// </remarks>
    private static boolean canNestSdt(StructuredDocumentTag childSdt, StructuredDocumentTag parentSdt,
        RevisionHandlingContext context)
    {
        // Checks whether there are preserved nodes between the SDTs.
        Node node = childSdt.previousPreOrder(parentSdt.getDocument());
        while ((node != null) && !NodeUtil.isAncestorOrSelf(node, parentSdt))
        {
            if ((node instanceof ITrackableNode) && !context.isDeletingNode(node))
                return false;

            node = node.previousPreOrder(parentSdt.getDocument());
        }

        if (childSdt.getLevel() != MarkupLevel.INLINE)  // case 7 of WORDSNET-15250
            return true;
        if (!context.canNestInlineSdts(parentSdt)) // case 8 of WORDSNET-15250
            return false;

        MoveRange range = MoveRangeFinder.findMoveRange(childSdt, !context.isAcceptance());

        // Variations of case 2 of WORDSNET-15250
        if (!range.isDefined())
            return true;

        // Check presence of an opposite move range.
        /*MoveRevisionType*/int moveRevisionType = context.isAcceptance()
            ? MoveRevisionType.MOVE_TO
            : MoveRevisionType.MOVE_FROM;
        if (MoveRangeFinder.findMoveRangeStartByName(childSdt.getDocument(), range.getStart().getName(), moveRevisionType) == null)
            return true;

        // Checks whether the destination paragraph has runs.
        Node destination = childSdt.getNextNonMarkupCompositeLimited();
        while ((destination != null) &&
               ((destination.getNodeType() != NodeType.PARAGRAPH) || context.isDeletingNode(destination)))
        {
            destination = destination.getNextNonMarkupCompositeLimited();
        }

        return (destination == null) || !context.hasPreservedTrackables(destination, true);
    }

    /// <summary>
    /// Inserts default contents into structured document tags that have become empty and inline-level during
    /// handling revisions. Mimics to MS Word.
    /// </summary>
    private static void processInlinedSdts(ArrayList<StructuredDocumentTag> inlinedSdts)
    {
        for (StructuredDocumentTag sdt : inlinedSdts)
        {
            StructuredDocumentTag destination = getDeepestNestedSdt(sdt);
            destination.insertDefaultContentIfEmpty();
        }
    }

    /// <summary>
    /// Deletes all move range nodes of empty ranges from the document.
    /// </summary>
    private static void deleteMoveRanges(DocumentBase document)
    {
        removeEmptyMoveRanges(document, NodeType.MOVE_FROM_RANGE_START);
        removeEmptyMoveRanges(document, NodeType.MOVE_TO_RANGE_START);
    }

    /// <summary>
    /// Searches for move range start nodes of the specified type and removes move ranges if they are empty.
    /// </summary>
    private static void removeEmptyMoveRanges(DocumentBase document, /*NodeType*/int rangeStartNodeType)
    {
        ArrayList<Node> rangeStarts = document.getChildNodes(rangeStartNodeType, true).toNodeList();
        for (Node start : rangeStarts)
        {
            MoveRange range = new MoveRange((MoveRangeStart)start);
            if (range.isEmpty())
                range.remove();
        }
    }
}

