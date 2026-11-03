// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2022 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// Generate <see cref="FieldMacroButton"/> and <see cref="FieldGoToButton"/> field's fake results.
/// </summary>
class FieldButtonFakeResultBuilder implements INodeModifier
{
    private FieldButtonFakeResultBuilder(Paragraph fieldParagraph)
    {
        mDocument = fieldParagraph.getDocument();
        Section section = new Section(mDocument);
        mBody = section.appendChild(new Body(mDocument));
        Paragraph paragraph = (Paragraph)mBody.appendChild(fieldParagraph.deepClone(false));
        mRun = paragraph.appendChild(new Run(mDocument));
    }

    static NodeRange buildFieldButtonFakeResult(FieldArgument displayTextArgument) throws Exception
    {
        if (displayTextArgument == null)
            return null;

        FieldButtonFakeResultBuilder builder = new FieldButtonFakeResultBuilder(displayTextArgument.getField().getStart().getParentParagraph());
        builder.copyArgumentRange(displayTextArgument.getRange());
        builder.finalizeResult();
        return builder.getResult();
    }

    private void copyArgumentRange(NodeRange range) throws Exception
    {
        NodeCopier.copy(range, mRun, this);
        mRun.remove();
    }

    private void finalizeResult()
    {
        if (mIsError)
            return;

        for (Node node : (Iterable<Node>) mBody.getChildNodes(NodeType.ANY, false))
        {
            if (mergeNode(node))
                node.remove();
        }

        Debug.assertTrue(mBody.getParagraphs().getCount() == 1);
    }

    private boolean mergeNode(Node node)
    {
        if (node == mBody.getFirstParagraph())
            return false;

        switch (node.getNodeType())
        {
            case NodeType.PARAGRAPH:
                return mergeParagraph((Paragraph)node);
            case NodeType.TABLE:
                return mergeTable((Table)node);
            default:
                return false;
        }
    }

    private boolean mergeParagraph(Paragraph source)
    {
        mBody.getFirstParagraph().insertBefore(source.getFirstChild(), null, null);
        return true;
    }

    private boolean mergeTable(Table source)
    {
        for (Row row : (Iterable<Row>) source.getRows())
        {
            for (Cell cell : (Iterable<Cell>) row.getCells())
            {
                for (Node node : (Iterable<Node>) cell.getChildNodes(NodeType.ANY, false))
                    mergeNode(node);

                mBody.getFirstParagraph().appendChild(new Run(mDocument, ControlChar.CELL));
            }

            if (!row.isLastRow())
                mBody.getFirstParagraph().appendChild(new Run(mDocument, ControlChar.CELL));
        }

        return true;
    }

    private NodeRange getResult()
    {
        if (mIsError)
        {
            Run run = new Run(mDocument, "DisplayText cannot span more than one line!");
            run.getFont().setBold(true);
            return new NodeRange(run, run);
        }

        Paragraph paragraph = mBody.getFirstParagraph();
        return FieldUtil.buildFieldResultNodeRange(paragraph, paragraph);
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener)
    {
        return modifyNode(nodeToModify, modifyChildren);
    }

    private boolean isValidNode(Node node)
    {
        if (mIsError)
            return false;

        Run run = ms.as(node, Run.class);
        if (run == null)
            return true;

        if (!msString.equals(run.getText(), ControlChar.PAGE_BREAK))
            return true;

        mIsError = true;
        return false;
    }

    private Node modifyNode(Node node, boolean modifyChildren)
    {
        if (!isValidNode(node))
            return null;

        switch (node.getNodeType())
        {
            case NodeType.RUN:
                return modifyRun((Run)node);
            case NodeType.SHAPE:
                return modifyShape((Shape)node);
            default:
               break;
        }

        if (modifyChildren && node.isComposite())
        {
            CompositeNode compositeNode = (CompositeNode)node;
            for (Node child : (Iterable<Node>) compositeNode.getChildNodes(NodeType.ANY, false))
            {
                if (modifyNode(child, true) == null)
                    child.remove();
            }
        }

        return node;
    }

    private static Node modifyRun(Run run)
    {
        run.setText(run.getText().replace(ControlChar.LINE_BREAK, ""));
        return run;
    }

    private static Shape modifyShape(Shape shape)
    {
        if (shape.getWrapType() != WrapType.INLINE)
            return null;

        shape.getStroke().setVisible(false);
        shape.getFill().setVisible(false);

        shape.getChildNodes(NodeType.ANY, false).clear();
        return shape;
    }

    private /*final*/ DocumentBase mDocument;
    private /*final*/ Body mBody;
    private /*final*/ Run mRun;

    private boolean mIsError;
}

