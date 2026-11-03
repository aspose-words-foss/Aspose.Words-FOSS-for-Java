// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/04/2017 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.Map;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;


/// <summary>
/// Implements ability to resolve unexpected nodes which occurred into inline SDT.
/// </summary>
class InlineMarkupResolver
{
    /// <summary>
    /// Ctr.
    /// </summary>
    /// <param name="markupStartsTable">Dictionary of nodes where IMarkupNode of block level is started.</param>
    InlineMarkupResolver(Map<IMarkupNode, Node> markupStartsTable)
    {
        mMarkupStartsTable = markupStartsTable;
    }

    /// <summary>
    /// Adds new root to sequence for resolve.
    /// </summary>
    /// <param name="sdt">Node to append.</param>
    void addToSequence(StructuredDocumentTag sdt)
    {
        if (mTopLevelInline != null)
            return;

        mTopLevelInline = sdt;
        msArrayList.add(mInlineSequence, mTopLevelInline);
    }

    /// <summary>
    /// Initializes addition of the node with specified type to collection for resolve.
    /// </summary>
    /// <param name="sdt">SDT which contains node with specified type.</param>
    /// <param name="nodeType">Type of the node which will be read.</param>
    /// <returns>Index of the reserved place in the collection or -1 when reservation does not required.</returns>
    int startPendingInline(StructuredDocumentTag sdt, /*NodeType*/int nodeType)
    {
        Debug.assertTrue((sdt != null));
        final int EMPTY_INDEX = -1;

        if (sdt.getLevel() != MarkupLevel.INLINE)
            return EMPTY_INDEX;

        ArrayList<Node> pendingNodes = null;

        // MSW builds path from top level inline SDT and goes deeper while is not occurred
        // last or valid inline nested node. So, collect nodes which is invalid and have to
        // be resolved on this path.
        if (mTopLevelInline != null)
        {
            // Create appropriate collection for nodes to resolve if it is not exist.
            if (!mInlinePendingTable.containsKey(mTopLevelInline))
            {
                // This is first element inside SDT, so append parent paragraph for resolve.
                pendingNodes = new ArrayList<Node>();
                Paragraph para = (Paragraph)sdt.getAncestor(NodeType.PARAGRAPH);

                // When parent paragraph already exist among nodes for resolve then skip it.
                if (!checkFirstParagraphIsUsed(para))
                    pendingNodes.add(para);

                // Set actual parent.
                setBlockLevelParent(mTopLevelInline, para);
                // Set collection with nodes for resolve.
                mInlinePendingTable.put(mTopLevelInline, pendingNodes);
            }

            // Reserve place for element which will be read.
            pendingNodes = mInlinePendingTable.get(mTopLevelInline);
            pendingNodes.add(null);
        }

        // Reset to process cell level correctly.
        if (nodeType == NodeType.TABLE)
            resetInlineContext();

        return (pendingNodes != null) ? pendingNodes.size() - 1 : EMPTY_INDEX;
    }

    /// <summary>
    /// Completes addition of the node which was initialized with 
    /// <see cref="InlineMarkupResolver.StartPendingInline"/>.
    /// </summary>
    /// <param name="sdt">SDT which contains read node.</param>
    /// <param name="node">Node which was read.</param>
    /// <param name="index">Index in the collection which was returned by <see cref="InlineMarkupResolver.StartPendingInline"/>.</param>
    void endPendingInline(StructuredDocumentTag sdt, Node node, int index)
    {
        Debug.assertTrue(node != null);

        if ((sdt != null) && (sdt.getLevel() != MarkupLevel.INLINE))
            return;

        // Inline SDT (root) was extracted, current node have to be added to block level.
        // When "index" < 0, then "sdr" equals to null.
        if (sdt == null)
        {
            setBlockLevelParent(node, node);
            msArrayList.add(mInlineSequence, node);
            return;
        }

        // Set value into reserved place. 
        mInlinePendingTable.get(sdt).set(index, node);
        // Get actual count of the pending elements.
        int pendingAfter = getPendingInlineCount();

        // If node which was read contains another SDT wrapped into inline then
        // after reading this node a pending elements count will be increased.
        // Check that passed node contains only inline elements, so current collection with nodes
        // to extract is complete.
        if ((index + 1) == pendingAfter)
            resetInlineContext();
    }

    /// <summary>
    /// Resolves accumulated nodes.
    /// </summary>
    void processInline(StructuredDocumentTag sdt)
    {
        Debug.assertTrue(sdt != null);

        if (sdt.getLevel() != MarkupLevel.INLINE)
            return;

        // When nothing to resolve SDT or passed markup node is not top level among
        // nested inline SDT, then skip the processing.
        if ((mInlineSequence.size() == 0) || (mInlineSequence.get(0) != sdt))
        {
            if (mTopLevelInline == sdt)
                resetInlineContext();

            return;
        }

        ArrayList<Node> pendingNodes = null;
        CompositeNode inlineAncestor;
        Node resolveRoot;

        for (int i = 0; i < mInlineSequence.size(); ++i)
        {
            resolveRoot = mInlineSequence.get(i);
            pendingNodes = getPendingInlineNodes(i);
            inlineAncestor = DictionaryExtension.getValueOrNull(mBlockLevelParents, resolveRoot);

            // It can be inline SDT without block level nodes, so is nothing to process.
            if (inlineAncestor == null)
                break;

            // WORDSNET-15210 Implement reading of block level nodes inside inline SDT.
            processPendingNodes(pendingNodes, i);
        }

        mParasToAppend.clear();
        mInlineSequence.clear();
        mBlockLevelParents.clear();
        mInlinePendingTable.clear();
        mParasToAppendParents.clear();
        // Reset top level inline SDT.
        resetInlineContext();
    }

    /// <summary>
    ///Process specified pending nodes collection.
    /// </summary>
    private void processPendingNodes(ArrayList<Node> pendingNodes, int seqIndex)
    {
        Node curNode;

        for (int j = 0; j < pendingNodes.size(); j++)
        {
            curNode = pendingNodes.get(j);

            switch (curNode.getNodeType())
            {
                case NodeType.TABLE:
                    processNestedInlineTable((Table)curNode, seqIndex);
                    break;
                case NodeType.PARAGRAPH:
                    processNestedInlinePara((Paragraph)curNode, seqIndex, j);
                    break;
                default:
                    break;
            }

            // Flush paragraphs which have to be moved.
            flushParagraphs(curNode);
        }
    }

    /// <summary>
    /// Moves paragraphs which have to be moved after specified node.
    /// </summary>
    private void flushParagraphs(Node node)
    {
        Debug.assertTrue(node != null);

        if (!mParasToAppend.containsKey(node))
            return;

        ArrayList<Node> nodesToAppend = mParasToAppend.get(node);

        for (Node para : nodesToAppend)
        {
            if (!mParasToAppendParents.containsKey(para))
                continue;

            CompositeNode inlineAncestor = mParasToAppendParents.get(para);

            if ((inlineAncestor != null) && (para != inlineAncestor.getLastChild()))
                inlineAncestor.insertBefore(para, inlineAncestor.getLastChild());
        }
    }

    /// <summary>
    /// Retrieves nodes count which pending to be resolved for current root.
    /// </summary>
    private int getPendingInlineCount()
    {
        if ((mTopLevelInline == null) || !mInlinePendingTable.containsKey(mTopLevelInline))
            return 0;

        return mInlinePendingTable.get(mTopLevelInline).size();
    }

    /// <summary>
    /// Sets current parent for specified node into map using the key.
    /// </summary>
    private void setBlockLevelParent(Node key, Node curNode)
    {
        // Re-calculate block level parent when it have to be changed. For example,
        // it can happened when table occurred in inline level.
        if (mBlockLevelParent == null)
            mBlockLevelParent = getBlockLevelParent(curNode);

        // Save block level parent for specified node.
        mBlockLevelParents.put(key, mBlockLevelParent);
    }

    /// <summary>
    /// Checks that specified paragraph exist among first nodes in the collections for resolve.
    /// </summary>
    private boolean checkFirstParagraphIsUsed(Paragraph para)
    {
        NodeCollection sdts = para.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, false);

        for (Node key : (Iterable<Node>) sdts)
        {
            if (!mInlinePendingTable.containsKey(key))
                continue;

            ArrayList<Node> usedNodes = mInlinePendingTable.get(key);
            // Looks like it can be just first element in the sequence.
            if ((usedNodes.size() > 0) && (usedNodes.get(0) == para))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Determines actual parent for specified node.
    /// </summary>
    private static CompositeNode getBlockLevelParent(Node curNode)
    {
        CompositeNode parent = curNode.getParentNode();

        while (parent != null)
        {
            if (parent.canInsert(curNode) && ((parent.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG) ||
                (((IMarkupNode)parent).getLevel_IMarkupNode() != MarkupLevel.INLINE)))
            {
                return parent;
            }

            parent = parent.getParentNode();
        }

        return curNode.getParentNode();
    }

    /// <summary>
    /// Clears current context which determines how to accumulate nodes for resolve.
    /// </summary>
    private void resetInlineContext()
    {
        mTopLevelInline = null;
        mBlockLevelParent = null;
    }

    /// <summary>
    /// Changes start position of the block level SDT to new position.
    /// </summary>
    private void moveMarkupStartToNode(Node newStartNode, Node prevStartNode)
    {
        ArrayList<IMarkupNode> keys = new ArrayList<IMarkupNode>(mMarkupStartsTable.keySet().size());

        // Retrieve all start positions for previous node and move it to a new position.
        for (IMarkupNode key : mMarkupStartsTable.keySet())
        {
            if (mMarkupStartsTable.get(key) == prevStartNode)
                msArrayList.add(keys, key);
        }

        for (IMarkupNode key : keys)
            mMarkupStartsTable.put(key, newStartNode);
    }

    /// <summary>
    /// Checks that current root has pending nodes. Returns "null" and inserts
    /// node before last child of the ancestor when does not have.
    /// </summary>
    private Node checkResolveRoot(int seqIndex)
    {
        Node resolveRoot = mInlineSequence.get(seqIndex);
        // Existence of the value for specified key was checked on the stack above.
        ArrayList<Node> pendingNodes = DictionaryExtension.getValueOrNull(mInlinePendingTable, resolveRoot);
        CompositeNode inlineAncestor = DictionaryExtension.getValueOrNull(mBlockLevelParents, resolveRoot);

        boolean emptyRoot = (pendingNodes == null);

        if (emptyRoot)
            inlineAncestor.insertBefore(resolveRoot, inlineAncestor.getLastChild());

        return emptyRoot ? null : resolveRoot;
    }

    /// <summary>
    /// Resolves table.
    /// </summary>
    private void processNestedInlineTable(Table tbl, int seqIndex)
    {
        Debug.assertTrue(tbl != null);

        Node resolveRoot = checkResolveRoot(seqIndex);

        // Node without pending child nodes.
        if (resolveRoot == null)
            return;

        // Currently table which was detected in inline SDT is in resolve.
        // Container for text of the first table paragraph have to be saved.
        CompositeNode textHolder = tbl.getParentNode();
        CompositeNode inlineAncestor = DictionaryExtension.getValueOrNull(mBlockLevelParents, resolveRoot);

        // MSW requires that cell has to contain a paragraph.
        // In general this code for resiliency purposes.
        tbl.getFirstRow().getFirstCell().ensureMinimum();

        // Move block level structured document tag start position to the beginning of the table.
        Paragraph firstTblPara = tbl.getFirstRow().getFirstCell().getFirstParagraph();
        moveMarkupStartToNode(firstTblPara, inlineAncestor.getLastChild());

        // Copy cell content of the first paragraph to inline SDT (mimic MSW).
        while (firstTblPara.getFirstChild() != null)
            textHolder.appendChild(firstTblPara.getFirstChild());

        flushFirstParagraph(firstTblPara, seqIndex);

        // Insert table to block level.
        inlineAncestor.insertBefore(tbl, inlineAncestor.getLastChild());

        // Move current inline SDT to the table.
        firstTblPara.appendChild(resolveRoot);

        ArrayList<Node> pendingNodes;
        // Content of the paragraph was moved to inline SDT, however it can be used
        // in another element sequences (as first paragraph).
        for (int i = seqIndex + 1; i < mInlineSequence.size(); ++i)
        {
            pendingNodes = getPendingInlineNodes(i);

            if ((pendingNodes.size() > 0) && (pendingNodes.get(0) == firstTblPara))
            {
                Node rootBefore = mInlineSequence.get(i);

                mInlineSequence.set(i, resolveRoot);
                mInlinePendingTable.put(resolveRoot, pendingNodes);
                CompositeNode node = DictionaryExtension.getValueOrNull(mBlockLevelParents, rootBefore);
                mBlockLevelParents.put(resolveRoot, node);

                break;
            }
        }
    }

    /// <summary>
    /// Resolves paragraph.
    /// </summary>
    private void processNestedInlinePara(Paragraph para, int seqIndex, int nodeIndex)
    {
        Debug.assertTrue(para != null);

        // Existence of the value for specified key was checked on the stack above.
        Node resolveRoot = checkResolveRoot(seqIndex);

        // Node without pending nodes.
        if (resolveRoot == null)
            return;

        CompositeNode inlineAncestor = DictionaryExtension.getValueOrNull(mBlockLevelParents, resolveRoot);
        boolean isParaMoved = nodeIndex == mInlinePendingTable.get(resolveRoot).size() - 1;
        Node lastBlockChild = detectLastChild(para, seqIndex, nodeIndex);

        if ((lastBlockChild != null) && !isParaMoved)
        {
            if (!mParasToAppend.containsKey(lastBlockChild))
                msDictionary.add(mParasToAppend, lastBlockChild, new ArrayList<Node>());

            mParasToAppend.get(lastBlockChild).add(para);
            mParasToAppendParents.put(para, inlineAncestor);
        }

        // Copy content of the paragraph to inline SDT (mimic MSW).
        if ((nodeIndex != 0) || (para.getParentNode() == resolveRoot))
        {
            while (para.getFirstChild() != null)
                para.getParentNode().appendChild(para.getFirstChild());
        }

        // Move paragraph to block level when current node is the last node for resolve.
        if (isParaMoved)
        {
            // Move child of the first paragraph to extracted node.
            flushFirstParagraph(para, seqIndex);
            // Move current inline SDT to the paragraph.
            para.remove();
            para.appendChild(resolveRoot);

            // Insert paragraph to block level.
            inlineAncestor.insertBefore(para, inlineAncestor.getLastChild());
            moveMarkupStartToNode(para, inlineAncestor.getLastChild());
        }
    }

    /// <summary>
    /// Copies content of the first paragraph of the specified sequence to 
    /// destination container.
    /// </summary>
    private void flushFirstParagraph(CompositeNode destContainer, int seqIndex)
    {
        Debug.assertTrue(destContainer != null);

        ArrayList<Node> pendingNodes = getPendingInlineNodes(seqIndex);

        if (pendingNodes.size() == 0)
            return;

        Paragraph src = ms.as(pendingNodes.get(0), Paragraph.class);
        if ((src == null) || (src.getParentNode() == mInlineSequence.get(seqIndex)))
            return;

        // Copy content to destination container.
        while ((src.getFirstChild() != null) && checkFirstParaContent(src.getFirstChild(), seqIndex))
            destContainer.appendChild(src.getFirstChild());
    }

    /// <summary>
    /// Detects that specified child has to copied as inline content. 
    /// </summary>
    private boolean checkFirstParaContent(Node child, int seqIndex)
    {
        Debug.assertTrue(child != null);

        if (!child.isComposite())
            return true;

        if (child.isComposite() && (child.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG))
            return false;

        // It is can be inline SDT without block level nodes within child nodes.
        ArrayList<Node> nodes = getPendingInlineNodes(seqIndex);
        // Take the node for current root and check that child is not ancestor for it.
        // We have at least two nodes at this place in the collection (first paragraph and
        // one more table or paragraph).
        return !nodes.get(nodes.size() - 1).isAncestorNode(child);
    }

    /// <summary>
    /// Retrieves pending nodes for specified sequence.
    /// </summary>
    private ArrayList<Node> getPendingInlineNodes(int seqIndex)
    {
        Node rootNode = mInlineSequence.get(seqIndex);

        if (!mInlinePendingTable.containsKey(rootNode))
        {
            ArrayList<Node> returnInitializer = new ArrayList<Node>(); { returnInitializer.add(rootNode); }
            return returnInitializer;
        }

        return mInlinePendingTable.get(rootNode);
    }

    /// <summary>
    /// Finds last pending node which placed inside specified container.
    /// </summary>
    private Node detectLastChild(CompositeNode container, int seqIndex, int nodeIndex)
    {
        ArrayList<Node> pendingNodes = null;
        Node lastBlockChild = null;

        for (int i = seqIndex; i < mInlineSequence.size(); ++i)
        {
            pendingNodes = getPendingInlineNodes(i);

            for (int j = nodeIndex + 1; j < pendingNodes.size(); ++j)
            {
                boolean isAncestor = pendingNodes.get(j).isAncestorNode(container);
                lastBlockChild = isAncestor ? pendingNodes.get(j) : lastBlockChild;

                if (!isAncestor)
                    break;
            }

            nodeIndex = -1;
        }

        return lastBlockChild;
    }

    /// <summary>
    /// Stores top level inline SDT for current context.
    /// </summary>
    StructuredDocumentTag getTopLevelInline() { return mTopLevelInline; }

    /// <summary>
    /// Stores top level inline SDT for current context.
    /// </summary>
    /// <remarks>
    /// It can be reset and changed after the root is completed and can be started the next one.
    /// <see cref="InlineMarkupResolver.ResetInlineContext"/>.
    /// </remarks>
    private StructuredDocumentTag mTopLevelInline;

    /// <summary>
    /// Stores current block level parent for inline SDT.
    /// </summary>
    /// <remarks>
    /// It used to reduce call count of <see cref="SetBlockLevelParent"/>.
    /// </remarks>
    private CompositeNode mBlockLevelParent;

    /// <summary>
    /// The collection of items which preserve sequence of nodes occurrence while reading.
    /// </summary>
    /// <remarks> 
    /// In current implementation this sequence can be changed, while
    /// <see cref="ProcessInline"/> is in process. However, in the
    /// beginning of this method the sequence is preserved.
    /// </remarks>
    private /*final*/ ArrayList<Node> mInlineSequence = new ArrayList<Node>();

    /// <summary>
    /// Stores nodes where IMarkupNode of block level is started.
    /// </summary>
    /// <remarks> 
    /// It is reference to object which declared into <see cref="MarkupResolver"/>.
    /// </remarks>
    private /*final*/ Map<IMarkupNode, Node> mMarkupStartsTable;

    /// <summary>
    /// The map of the nodes and paragraphs which have to be moved after these nodes.
    /// </summary>
    private /*final*/ HashMap<Node, ArrayList<Node>> mParasToAppend = new HashMap<Node, ArrayList<Node>>();

    /// <summary>
    /// The map of paragraphs which have to be moved to a new positions and parents.
    /// </summary>
    private /*final*/ HashMap<Node, CompositeNode> mParasToAppendParents = new HashMap<Node, CompositeNode>();

    /// <summary>
    /// Stores unresolved child nodes of inline SDT's. Keys are inline SDT's and values are 
    /// arrays of nodes which pending to be resolved.
    /// </summary>
    /// <remarks>
    /// Keys of inline SDT's retrieves while iterating through <see cref="mInlineSequence"/>.
    /// </remarks>
    private /*final*/ HashMap<Node, ArrayList<Node>> mInlinePendingTable =
        new HashMap<Node, ArrayList<Node>>();

    /// <summary>
    /// Stores composite nodes from  <see cref="mInlineSequence"/> and parents map. 
    /// </summary>
    private /*final*/ HashMap<Node, CompositeNode> mBlockLevelParents =
        new HashMap<Node, CompositeNode>();
}

