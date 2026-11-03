// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/07/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.IntList;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Indexes nodes for find/replace operations.
/// </summary>
/// <remarks>
/// AM. Currently ignores few node types to simplify replace operations.
/// </remarks>
class FindReplaceIndexer extends NodeIndexer
{
    FindReplaceIndexer(Node rootNode, FindReplaceOptions options) throws Exception
    {
    	super(rootNode);
	
        mOptions = options;
        update();
    }

    FindReplaceIndexer(NodeRange nodeRange, Node rootNode, FindReplaceOptions options) throws Exception
    {
    	super(nodeRange, rootNode);
	
        mOptions = options;
        update();
    }

    /*override*/ /*IndexerAction*/int onNode(Node node)
    {
        // WORDSNET-18986 Removed SmartTag from ignore.
        switch (node.getNodeType())
        {
            case NodeType.TABLE:
            case NodeType.ROW:
                return IndexerAction.SKIP;

            case NodeType.OFFICE_MATH:
            {
                // WORDSNET-28249 Added option to consider text in OfficeMath.
                return ((mOptions == null) || mOptions.getIgnoreOfficeMath()) ? IndexerAction.IGNORE : IndexerAction.NONE;
            }

            case NodeType.SPECIAL_CHAR:
            case NodeType.FORM_FIELD:
                return IndexerAction.IGNORE;

            case NodeType.COMMENT:
            case NodeType.HEADER_FOOTER:
            {
                if (node != getRootNode())
                {
                    msArrayList.add(getPendingList(), node);
                    return IndexerAction.IGNORE;
                }
                break;
            }

            case NodeType.FIELD_START:
            case NodeType.FIELD_SEPARATOR:
            {
                mFieldNodes.push(node);
                return IndexerAction.IGNORE;
            }

            case NodeType.FIELD_END:
            {
                // WORDSNET-23658 There can be situation when customer wants to replace inside some isolated Paragraph
                // and this paragraph contains only FieldEnd, and its FieldStart is located inside another Paragraph.
                // In this case we are not actually inside a Field and should check it for resilience.
                if (isInField())
                {
                    Node fieldNode = mFieldNodes.pop();
                    if ((mFieldNodes.getCount() > 0) && (fieldNode.getNodeType() == NodeType.FIELD_SEPARATOR))
                        mFieldNodes.pop();
                }

                return IndexerAction.IGNORE;
            }

            case NodeType.SHAPE:
                // In the legacy Find/Replace behavior, text boxes should be processed
                // in the context of the document content, and not separately from it.
                if ((node != getRootNode()) && !(mOptions.getUseLegacyOrder() && ((Shape)node).hasTextbox()))
                {
                    msArrayList.add(getPendingList(), node);
                    // WORDSNET-24701 Do not ignore shapes to mimic Word behavior.
                    // WORDSNET-25115 Introduced an option to control over the behavior.
                    return mOptions.getIgnoreShapes() ? IndexerAction.IGNORE : IndexerAction.COLLAPSE;
                }

                break;

            case NodeType.STRUCTURED_DOCUMENT_TAG:
            {
                StructuredDocumentTag sdt = (StructuredDocumentTag)node;
                if(!isSupportedSdtLevel(sdt.getLevel()))
                    return IndexerAction.SKIP;

                // WORDSNET-24241 Added new option to control the behavior.
                if ((!mOptions.getIgnoreStructuredDocumentTags()) && (node != getRootNode()))
                {
                    msArrayList.add(getPendingList(), node);
                    return IndexerAction.IGNORE;
                }

                break;
            }

            case NodeType.FOOTNOTE:
                return mOptions.getIgnoreFootnotes()
                    ? IndexerAction.IGNORE
                    : IndexerAction.NONE;

            default:
                break;
        }

        // WORDSNET-20184 Check options after all node types are processed.
        // Otherwise, some data may be never collected (for example, mFieldCharsCount will be never adjusted).
        return verifyOptions(node);
    }

    /// <summary>
    /// Returns text for a specified node.
    /// </summary>
    protected /*override*/ String getText(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.SHAPE:
            case NodeType.GROUP_SHAPE:
                return G_SHAPE;

            default:
                return super.getText(node);
        }
    }

    /// <summary>
    /// Verifies underlying FindReplace options for a specified node.
    /// </summary>
    private /*IndexerAction*/int verifyOptions(Node node)
    {
        if (mOptions != null)
        {
            // WORDSNET-19912 New option IgnoreFields is introduced.
            // WORDSNET-22955 Changed action from 'Ignore' to 'Skip' to allow indexer find 'FieldEnd' correctly.
            if (mOptions.getIgnoreFields() && isInField())
                return IndexerAction.SKIP;

            // WORDSNET-22686 New option IgnoreFieldCodes is introduced.
            if (mOptions.getIgnoreFieldCodes() && isInFieldCode())
                return IndexerAction.SKIP;

            // WORDSNET-19912 New option IgnoreDeleted is introduced.
            // WORDSNET-22632 Lets do 'Skip' instead of 'Ignore' for this option
            // to process child nodes of deleted composite nodes properly.
            if (mOptions.getIgnoreDeleted() && (node instanceof ITrackableNode) && (((ITrackableNode)node).getDeleteRevision() != null))
                return IndexerAction.SKIP;

            // WORDSNET-19912 New option IgnoreInserted is introduced.
            // WORDSNET-22632 Lets do 'Skip' instead of 'Ignore' for this option
            // to process child nodes of inserted composite nodes properly.
            if (mOptions.getIgnoreInserted() && (node instanceof ITrackableNode) && (((ITrackableNode)node).getInsertRevision() != null))
                return IndexerAction.SKIP;
        }

        return IndexerAction.NONE;
    }

    /// <summary>
    /// Indicates that given markup level is supported for find/replace operation.
    /// </summary>
    private static boolean isSupportedSdtLevel(/*MarkupLevel*/int level)
    {
        return (level == MarkupLevel.INLINE) || (level == MarkupLevel.BLOCK);
    }

    protected /*override*/ String getEndText(CompositeNode node)
    {
        switch (node.getNodeType())
        {
            case NodeType.PARAGRAPH:
            {
                Paragraph para = (Paragraph)node;

                if (para.isEndOfSection())
                {
                    // Make section break for sections and eof for the last section.
                    return para.getParentSection().isLastChild() ? G_PARAGRAPH_BREAK : G_SECTION_BREAK;
                }

                if (para.isEndOfCell())
                {
                    return G_CELL_BREAK;
                }

                return G_PARAGRAPH_BREAK;
            }

            case NodeType.SHAPE:
            case NodeType.GROUP_SHAPE:
            {
                return G_SHAPE;
            }

            default:
                return node.getEndText();
        }
    }

    /// <summary>
    /// Adds index for a specified node.
    /// </summary>
    protected /*override*/ void add(int position, Node node, String text)
    {
        super.add(position, node, text);

        // WORDSNET-22814 We now also collect positions of LineBreak('\v') and PageBreak('\f') characters.
        int specialIndex = msString.indexOfAny(text, G_SPECIAL_CHARS, 0);
        while (specialIndex != -1)
        {
            getSpecialPositions().add(specialIndex + position);
            specialIndex = msString.indexOfAny(text, G_SPECIAL_CHARS, specialIndex + 1);
        }
    }

    /// <summary>
    /// List of nodes that ignored and pended for second stage of find/replace operation.
    /// </summary>
    ArrayList<Node> getPendingList() { return mPendingList; }

    /// <summary>
    /// Gets a boolean value indicating that current node is inside a field.
    /// </summary>
    private boolean isInField() { return (mFieldNodes.getCount() > 0); }

    /// <summary>
    /// Gets a boolean value indicating that current node is inside a field's Code.
    /// </summary>
    private boolean isInFieldCode() { return (isInField() && (mFieldNodes.peek().getNodeType() == NodeType.FIELD_START)); }

    /// <summary>
    /// A list of positions of special characters within indexed text.
    /// </summary>
    IntList getSpecialPositions() { return mSpecialPositions; }

    /// <summary>
    /// Substitute char for paragraph break.
    /// </summary>
    static final char PARAGRAPH_BREAK_CHAR = (char)0xe00d;
    private static /*final*/ String G_PARAGRAPH_BREAK = Character.toString(PARAGRAPH_BREAK_CHAR);

    /// <summary>
    /// Substitute char for section break.
    /// </summary>
    static final char SECTION_BREAK_CHAR = (char)0xe00f;
    private static /*final*/ String G_SECTION_BREAK = Character.toString(SECTION_BREAK_CHAR);

    /// <summary>
    /// Substitute char for cell break.
    /// </summary>
    static final char CELL_BREAK_CHAR = (char)0xe010;
    private static /*final*/ String G_CELL_BREAK = Character.toString(CELL_BREAK_CHAR);

    /// <summary>
    /// Substitute char for Shape node.
    /// </summary>
    static final char SHAPE_CHAR = (char)0xe019;
    private static /*final*/ String G_SHAPE = Character.toString(SHAPE_CHAR);

    private /*final*/ ArrayList<Node> mPendingList = new ArrayList<Node>();
    private /*final*/ FindReplaceOptions mOptions;
    private /*final*/ msStack<Node> mFieldNodes = new msStack<Node>();
    private /*final*/ IntList mSpecialPositions = new IntList();

    /// <summary>
    /// The array of all special characters.
    /// </summary>
    private static /*final*/ char[] G_SPECIAL_CHARS = new char[]
    {
        PARAGRAPH_BREAK_CHAR, SECTION_BREAK_CHAR, CELL_BREAK_CHAR, ControlChar.LINE_BREAK_CHAR, ControlChar.PAGE_BREAK_CHAR
    };
}

