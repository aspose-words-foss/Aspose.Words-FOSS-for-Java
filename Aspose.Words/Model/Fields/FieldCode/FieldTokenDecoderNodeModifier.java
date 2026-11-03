// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FontUtil;
import com.aspose.ms.System.msString;


class FieldTokenDecoderNodeModifier extends FieldTokenDecoder implements INodeModifier
{
    FieldTokenDecoderNodeModifier(NodeRange tokenRange)
    {
    	this(tokenRange, true);
	
    }

    FieldTokenDecoderNodeModifier(NodeRange tokenRange, boolean trimDoubleQuotes)
    {
    	this(tokenRange, FieldTokenDecoderOptionsUtil.withTrimDoubleQuotes(FieldTokenDecoderOptions.ESCAPE_CHARS, trimDoubleQuotes));
	
    }

    FieldTokenDecoderNodeModifier(NodeRange tokenRange, /*FieldTokenDecoderOptions*/int options)
    {
    	super(options);
	
        mFieldCharCounter = new FieldCharCounter(FieldUtil.endsWithFieldEnd(tokenRange));
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener)
    {
        processNode(nodeToModify);

        // Reject the node if needed, but process it in a common way, as it may influence the internal state of the decoder.
        boolean needRejectNode = !prepareModify(referenceNode, nodeToModify);

        switch (tryDecodeRunText(nodeToModify, false))
        {
            case DecodeRunTextResult.DECODED:
            {
                // Do nothing.
                break;
            }
            case DecodeRunTextResult.EMPTY_TEXT:
            {
                needRejectNode = true;
                break;
            }
            case DecodeRunTextResult.NOT_RUN:
            {
                // WORDSNET-20760 Previously modifyChildren only affected field char calculation and didn't actually modify child nodes.
                if (nodeToModify.isComposite() && modifyChildren)
                    modifyChildNodes(nodeToModify, false);

                // Reset escaping if present.
                isTokenCharEscaped(false);
                onContentProcessed(nodeToModify);

                break;
            }
            default:
            {
                throw new IllegalArgumentException();
            }
        }

        return (needRejectNode ? null : nodeToModify);
    }

    private void modifyChildNodes(Node nodeToModify, boolean processNode)
    {
        if (processNode)
            processNode(nodeToModify);

        if (nodeToModify.isComposite())
        {
            for (Node childNode = ((CompositeNode)nodeToModify).getFirstChild(); childNode != null; childNode = childNode.getNextSibling())
                modifyChildNodes(childNode, true);
        }
        else
        {
            // We don't really care about decode result here.
            tryDecodeRunText(nodeToModify, true);
        }
    }

    private void processNode(Node node)
    {
        mFieldCharCounter.visitNode(node);
        tryProcessCell(node);
    }

    private /*DecodeRunTextResult*/int tryDecodeRunText(Node node, boolean setIfEmpty)
    {
        if (node.getNodeType() != NodeType.RUN)
            return DecodeRunTextResult.NOT_RUN;

        Run run = (Run)node;

        // It looks like decode logic is similar when located in a field or in a table cell.
        String decodedText = decodeTokenPart(
            run.getText(),
            mFieldCharCounter.isInField() || isInCell(),
            FontUtil.isSymbolic(run.getFont().getName()));
        boolean isEmpty = msString.isNullOrEmpty(decodedText);

        if (!isEmpty || setIfEmpty)
            run.setText(decodedText);

        return isEmpty ? DecodeRunTextResult.EMPTY_TEXT : DecodeRunTextResult.DECODED;
    }

    private void onContentProcessed(Node node)
    {
        // This check is redundant, but it allows to avoid cast below in most cases.
        if (hasProcessedContent())
            return;

        if (node instanceof FieldChar)
            return;

        onContentProcessed();
    }

    private void tryProcessCell(Node node)
    {
        // No the best way of detecting if we are still in the same cell. However we don't have ability to track the end of the container
        // like when we use a visitor.
        if ((mLastCell != null) && !node.isAncestorNode(mLastCell))
            mLastCell = null;

        if (node.getNodeType() == NodeType.CELL)
            mLastCell = node;
    }

    protected /*override*/ boolean isHasProcessedContentLocked() { return isInFieldCode(); }

    /// <summary>
    /// Performs initialization of a node modifying process.
    /// </summary>
    /// <remarks>
    /// An implementation of this method in a derived class should return false to reject the specified node.
    /// </remarks>
    protected /*virtual*/ boolean prepareModify(Node referenceNode, Node nodeToModify)
    {
        return true;
    }

    /// <summary>
    /// Gets a value indicating whether the current processed character is inside a field code.
    /// </summary>
    protected boolean isInFieldCode() { return mFieldCharCounter.isInFieldCode(); }

    /// <summary>
    /// Gets a value indicating if the current position is inside a table cell.
    /// </summary>
    private boolean isInCell() { return (mLastCell != null); }

    private /*final*/ FieldCharCounter mFieldCharCounter;
    private Node mLastCell;

    private /*enum*/ final class DecodeRunTextResult
    {
        private DecodeRunTextResult(){}
        
        public static final int DECODED = 0;
        public static final int EMPTY_TEXT = 1;
        public static final int NOT_RUN = 2;

        public static final int length = 3;
    }
}

