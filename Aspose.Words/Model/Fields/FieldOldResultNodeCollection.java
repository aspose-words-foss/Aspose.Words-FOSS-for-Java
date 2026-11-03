// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/11/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ISetGeneric;
import com.aspose.HashSetGeneric;


/// <summary>
/// Represents a collection of old result nodes for a single field.
/// </summary>
class FieldOldResultNodeCollection
{

    /// <summary>
    /// Creates an instance of <see cref="FieldOldResultNodeCollection"/> for the specified field if its old result
    /// nodes will be used while its update.
    /// </summary>
    static FieldOldResultNodeCollection create(Field field)
    {
        return new FieldOldResultNodeCollection(field);
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    private FieldOldResultNodeCollection(Field field)
    {
        NodeRange range = field.getFieldResultRange();
        if (range.isVoid())
        {
            mRange = NodeRange.VOID;
            return;
        }

        Node start = range.getStart().getNode();
        Node end = range.getEnd().getNode();

        EdgeCloningListener listener = new EdgeCloningListener(start, end);

        mClonedRoot = cloneSubTree(start, end, listener);

        mRange = new NodeRange(listener.getStartClone(), false, listener.getEndClone(), false);
    }

    private static Node cloneSubTree(Node start, Node end, INodeCloningListener listener)
    {
        Node root = Node.getCommonAncestor(start, end);

        ISetGeneric<Node> startAncestors = getAncestors(start, root);
        ISetGeneric<Node> endAncestors = getAncestors(end, root);

        return cloneSubTree(root, startAncestors, endAncestors, listener);
    }

    private static ISetGeneric<Node> getAncestors(Node node, Node root)
    {
        HashSetGeneric<Node> ancestors = new HashSetGeneric<Node>();

        Node currentNode = node;
        while (currentNode != null)
        {
            ancestors.add(currentNode);

            if (currentNode == root)
                break;

            currentNode = currentNode.getParentNode();
        }

        return ancestors;
    }

    private static Node cloneSubTree(Node node, ISetGeneric<Node> startAncestors, ISetGeneric<Node> endAncestors, INodeCloningListener listener)
    {
        Node clone = node.deepClone(false, listener);

        if (node.isComposite())
        {
            CompositeNode compositeNode = (CompositeNode)node;
            CompositeNode compositeClone = (CompositeNode)clone;

            boolean isSrartBranchFound = startAncestors == null;
            for (Node child = compositeNode.getFirstChild(); child != null; child = child.getNextSibling())
            {
                if (isSrartBranchFound)
                {
                    if (endAncestors != null && endAncestors.contains(child))
                    {
                        compositeClone.appendChildForLoad(cloneSubTree(child, null, endAncestors, listener));
                        break;
                    }

                    compositeClone.appendChildForLoad(child.deepClone(true));
                }
                else
                {
                    if (startAncestors.contains(child))
                    {
                        compositeClone.appendChildForLoad(cloneSubTree(child, startAncestors, null, listener));
                        isSrartBranchFound = true;
                    }
                }
            }
        }

        return clone;
    }

    NodeRange getRange() { return mRange; };

    private NodeRange mRange;

    // ReSharper disable once NotAccessedField.Local : C++ workaround, extends lifetime of cloned nodes
    private /*final*/ Node mClonedRoot;

    private static class EdgeCloningListener implements INodeCloningListener
    {
        public EdgeCloningListener(Node start, Node end)
        {
            mStartSource = start;
            mEndSource = end;
        }

        public void notifyNodeCloned(Node source, Node clone)
        {
            if (source == mStartSource)
                setStartClone(clone);

            if (source == mEndSource)
                setEndClone(clone);
        }

        Node getStartClone() { return mStartClone; }; private void setStartClone(Node value) { mStartClone = value; };

        private Node mStartClone;

        Node getEndClone() { return mEndClone; }; private void setEndClone(Node value) { mEndClone = value; };

        private Node mEndClone;

        private /*final*/ Node mStartSource;
        private /*final*/ Node mEndSource;
    }
}

