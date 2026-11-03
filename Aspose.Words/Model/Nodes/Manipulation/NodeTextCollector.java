// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Collects text between two nodes. May skip or include field codes.
/// </summary>
class NodeTextCollector extends NodeEnumerator
{
    private NodeTextCollector(NodeRange range, NodeTextCollectorOptions options)
    {
    	super(range);
	
        mOptions = options;
    }

    /// <summary>
    /// Collects and returns text of the specified range.
    /// </summary>
    /// <param name="range"></param>
    /// <returns></returns>
    static String getText(NodeRange range)
    {
        return getText(range, new NodeTextCollectorOptions());
    }

    /// <summary>
    /// Collects and returns text of the specified range.
    /// </summary>
    /// <param name="range"></param>
    /// <param name="isFieldResultMode"></param>
    /// <returns></returns>
    static String getText(NodeRange range, boolean isFieldResultMode)
    {
        NodeTextCollectorOptions options = new NodeTextCollectorOptions();
        options.isFieldResultMode(isFieldResultMode);
        return getText(range, options);
    }

    /// <summary>
    /// Collects and returns text between the two specified nodes inclusively.
    /// </summary>
    /// <param name="start"></param>
    /// <param name="end"></param>
    /// <returns></returns>
    static String getText(Node start, Node end)
    {
        return getText(start, true, end, true);
    }

    /// <summary>
    /// Collects and returns text between the two specified nodes inclusively or exclusively.
    /// </summary>
    /// <param name="start"></param>
    /// <param name="isIncludeStart"></param>
    /// <param name="end"></param>
    /// <param name="isIncludeEnd"></param>
    /// <returns></returns>
    static String getText(Node start, boolean isIncludeStart, Node end, boolean isIncludeEnd)
    {
        return getText(start, isIncludeStart, end, isIncludeEnd, new NodeTextCollectorOptions());
    }

    /// <summary>
    /// Collects and returns text between the two specified nodes inclusively or exclusively, also allows
    /// to control field results.
    /// </summary>
    /// <param name="start"></param>
    /// <param name="isIncludeStart"></param>
    /// <param name="end"></param>
    /// <param name="isIncludeEnd"></param>
    /// <param name="isFieldResultMode">
    /// True to include field results only, false to include all field parts that includes field code and field result
    /// separated by field characters.
    /// </param>
    /// <returns></returns>
    static String getText(Node start, boolean isIncludeStart, Node end, boolean isIncludeEnd, boolean isFieldResultMode)
    {
        NodeTextCollectorOptions options = new NodeTextCollectorOptions();
        options.isFieldResultMode(isFieldResultMode);
        return getText(start, isIncludeStart, end, isIncludeEnd, options);
    }

    /// <summary>
    /// Collects and returns text between the two specified nodes inclusively or exclusively.
    /// </summary>
    /// <param name="start"></param>
    /// <param name="isIncludeStart"></param>
    /// <param name="end"></param>
    /// <param name="isIncludeEnd"></param>
    /// <param name="options"></param>
    /// <returns></returns>
    static String getText(Node start, boolean isIncludeStart, Node end, boolean isIncludeEnd, NodeTextCollectorOptions options)
    {
        return getText(new NodeRange(start, isIncludeStart, end, isIncludeEnd), options);
    }

    /// <summary>
    /// Collects and returns text of the specified range.
    /// </summary>
    /// <param name="range"></param>
    /// <param name="options"></param>
    /// <returns></returns>
    static String getText(NodeRange range, NodeTextCollectorOptions options)
    {
        NodeTextCollector collector = new NodeTextCollector(range, options);
        try /*JAVA: was using*/
    	{
            return collector.getText();
    	}
        finally { if (collector != null) collector.dispose(); }
    }

    private String getText()
    {
        while (moveToNextNode(!skipCurrentNode()))
        {
            processPossibleFieldChar();

            if (skipCurrentNode())
                continue;

            if (!isLocked())
            {
                if (getCurrentNode().isComposite())
                {
                    CompositeNode node = (CompositeNode)getCurrentNode();
                    if (!node.hasChildNodes() && !isSkippedStoryEnd(node))
                        msStringBuilder.append(mBuilder, node.getEndText());
                }
                else
                {
                    if (needAppendCurrentNodeText())
                        msStringBuilder.append(mBuilder, getCurrentNode().getText());
                }
            }
        }

        return mBuilder.toString();
    }

    private boolean needAppendCurrentNodeText()
    {
        Inline inlineNode = ms.as(getCurrentNode(), Inline.class);
        if (inlineNode == null)
            return true;

        if (inlineNode.getFont().getHidden() && !mOptions.getAllowHiddenText())
            return false;

        if (inlineNode.isDeleteRevision() && !mOptions.getAllowDeletedText())
            return false;

        if (inlineNode.isInsertRevision() && !mOptions.getAllowInsertedText())
            return false;

        return true;
    }

    private boolean skipCurrentNode()
    {
        if (getCurrentNode() instanceof Comment)
            return mOptions.getSkipCommentText();
        if (getCurrentNode() instanceof Footnote)
            return mOptions.getSkipFootnoteText();

        return false;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified node is a story end and its story break char should not be included
    /// into result text.
    /// </summary>
    /// <remarks>
    /// When cross-structure annotations are allowed at block level, the range end node (annotation) may be
    /// in the same story but after the final paragraph. At this case story end control char returned by
    /// <see cref="Paragraph.GetEndText"/> should not be included into the result text.
    /// The same behaviour is with inline annotations: at this case the annotation is moved into the previous
    /// paragraph and the paragraph end is outside of the range.
    /// </remarks>
    private boolean isSkippedStoryEnd(Node node)
    {
        Node endNode = getRange().getEnd().getNode();
        if ((node.getNodeType() != NodeType.PARAGRAPH) ||
            !NodeUtil.isCrossStructureAnnotation(endNode) ||
            (endNode.getPreviousNonMarkupCompositeLimited() != node))
            return false;

        Paragraph para = (Paragraph)node;
        return para.isEndOfCell() || para.isEndOfTextbox() || para.isEndOfNoteSeparator() || para.isEndOfFootnote() ||
            para.isEndOfComment() || para.isEndOfSection() || para.isEndOfHeaderFooter();
    }

    protected /*override*/ void onMoved(/*DocumentPositionMovement*/int movement)
    {
        if ((movement == DocumentPositionMovement.ABOVE) && !isSkippedStoryEnd(getCurrentNode()))
            msStringBuilder.append(mBuilder, ((CompositeNode)getCurrentNode()).getEndText());
    }

    private void processPossibleFieldChar()
    {
        if (!mOptions.isFieldResultMode() && !isHiddenField())
            return;

        switch (getCurrentNode().getNodeType())
        {
            case NodeType.FIELD_START:
                processFieldCodeStart();
                break;
            case NodeType.FIELD_SEPARATOR:
                // WORDSNET-8937 Do not unlock an invisible field result.
                if (!FieldUtil.isFieldResultInvisible((FieldSeparator)getCurrentNode()))
                    processFieldCodeEnd();
                break;
            case NodeType.FIELD_END:
                // If the field has separator, the output was already unlocked.
                // WORDSNET-8937 Unlock an invisible field result here.
                FieldEnd fieldEnd = (FieldEnd)getCurrentNode();
                if (!fieldEnd.hasSeparator() || FieldUtil.isFieldResultInvisible(fieldEnd))
                    processFieldCodeEnd();
                break;
            default:
                // Do nothing.
                break;
        }
    }

    private void processFieldCodeStart()
    {
        mLockCount++;
    }

    private void processFieldCodeEnd()
    {
        if (mLockCount > 0)
        {
            // Do not unlock if nothing is locked.
            mLockCount--;
        }
        else
        {
            // HACK We may deal with a bookmark node range, which starts inside a field code,
            // so we need not to collect the field code's text in this case.
            mBuilder.setLength(0);
        }
    }

    private boolean isLocked()
    {
        if (mLockCount > 0)
            return true;

        switch (getCurrentNode().getNodeType())
        {
            case NodeType.FIELD_START:
            case NodeType.FIELD_SEPARATOR:
            case NodeType.FIELD_END:
                // When in field result mode, do not include field control characters.
                return mOptions.isFieldResultMode() || isHiddenField();
            default:
                return false;
        }
    }

    private boolean isHiddenField()
    {
        FieldChar fieldChar = ms.as(getCurrentNode(), FieldChar.class);
        if (fieldChar == null)
            return false;

        return FieldUtil.isDead(fieldChar.getFieldType());
    }

    private /*final*/ NodeTextCollectorOptions mOptions;
    private /*final*/ StringBuilder mBuilder = new StringBuilder();
    /// <summary>
    /// Greater than zero indicates the document content is being skipped.
    /// </summary>
    private int mLockCount;
}

