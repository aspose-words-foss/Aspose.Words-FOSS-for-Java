// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// Stores and restores bookmark nodes in field result range during field update.
/// </summary>
class FieldResultBookmarsPreserver
{
    FieldResultBookmarsPreserver(Field field)
    {
        mField = field;

        store();
    }

    private void store()
    {
        if (!mField.hasSeparator())
            return;

        NodeRange oldFieldResultRange = mField.getFieldResultRange();
        NodeEnumerator enumerator = new NodeEnumerator(oldFieldResultRange);
        try /*JAVA: was using*/
        {
            while (enumerator.moveToNextNode())
            {
                markLeadingBookmarkNodes(enumerator.getCurrentNode());
                markMiddleBookmarkNodes(enumerator.getCurrentNode());

                storeBookmarkNode(enumerator.getCurrentNode());
            }

            markTrailingBookmarkNodes();
        }
        finally { if (enumerator != null) enumerator.dispose(); }

        sanitize();
    }

    void restore()
    {
        HashSetGeneric<String> skipBookmarkNames = extractFieldResultBookmarkNames();

        for (int index = 0; index < mNodes.size(); index++)
        {
            FieldResultBookmarkNode node = mNodes.get(index);

            if (skipBookmarkNames.contains(node.getBookmarkName()))
                continue;

            if ((node.getPosition() == Position.MIDDLE) || (node.getPosition() == Position.TRAILING))
                mField.getEnd().insertPrevious(node.getNode());
        }

        for (int index = mNodes.size() - 1; index >= 0; index--)
        {
            FieldResultBookmarkNode node = mNodes.get(index);

            if (skipBookmarkNames.contains(node.getBookmarkName()))
                continue;

            if (node.getPosition() == Position.LEADING)
                mField.getSeparator().insertNext(node.getNode());
        }
    }

    private void storeBookmarkNode(Node node)
    {
        if (!NodeUtil.isBookmarkNode(node))
            return;

        if (Bookmark.GO_BACK_BOOKMARK_NAME.equals(((IBookmarkNode)node).getName()))
            return;

        mNodes.add(new FieldResultBookmarkNode(node));
    }

    private void markLeadingBookmarkNodes(Node node)
    {
        if (!mIsLeading)
            return;

        if (NodeUtil.isCrossStructureAnnotation(node))
            return;

        mIsLeading = false;

        markUnknownAs(Position.LEADING);
    }

    private void markMiddleBookmarkNodes(Node node)
    {
        if (mIsLeading)
            return;

        if (NodeUtil.isCrossStructureAnnotation(node))
            return;

        markUnknownAs(Position.MIDDLE);
    }

    private void markTrailingBookmarkNodes()
    {
        markUnknownAs(Position.TRAILING);
    }

    private void markUnknownAs(/*Position*/int position)
    {
        for (FieldResultBookmarkNode node : (Iterable<FieldResultBookmarkNode>) mNodes)
        {
            if (node.getPosition() != Position.UNKNOWN)
                continue;

            node.setPosition(position);
        }
    }

    private void sanitize()
    {
        ArrayList<FieldResultBookmarsPreserver.FieldResultBookmarkNode> unpairedNodes = new ArrayList<FieldResultBookmarsPreserver.FieldResultBookmarkNode>();
        for (int index = mNodes.size() - 1; index >= 0; index--)
        {
            FieldResultBookmarkNode fieldResultBookmarkNode = mNodes.get(index);
            if (fieldResultBookmarkNode.getPosition() != Position.MIDDLE)
                continue;

            FieldResultBookmarkNode pair = findByBookmarkName(fieldResultBookmarkNode.getBookmarkName(), unpairedNodes);
            if (pair == null)
            {
                msArrayList.add(unpairedNodes, fieldResultBookmarkNode);
            }
            else
            {
                unpairedNodes.remove(pair);
                mNodes.remove(pair);
                mNodes.remove(fieldResultBookmarkNode);
            }
        }
    }

    private HashSetGeneric<String> extractFieldResultBookmarkNames()
    {
        NodeRange newFieldResultRange = mField.getFieldResultRange();
        ArrayList<Node> bookmarkNodes = NodeFinder.findNodes(newFieldResultRange, NodeType.BOOKMARK_START, NodeType.BOOKMARK_END);

        HashSetGeneric<String> unpairedBookmarks = new HashSetGeneric<String>();
        HashSetGeneric<String> completeBookmarks = new HashSetGeneric<String>();

        for (Node node : bookmarkNodes)
        {
            String bookmarkName = ((IBookmarkNode)node).getName();
            String nameLowerInvariant = msString.toLowerInvariant(bookmarkName);
            if (unpairedBookmarks.contains(nameLowerInvariant))
            {
                unpairedBookmarks.remove(nameLowerInvariant);
                completeBookmarks.add(bookmarkName);
            }
            else
            {
                unpairedBookmarks.add(nameLowerInvariant);
            }
        }

        return completeBookmarks;
    }

    private static FieldResultBookmarkNode findByBookmarkName(String name, Iterable<FieldResultBookmarsPreserver.FieldResultBookmarkNode> nodes)
    {
        for (FieldResultBookmarkNode node : (Iterable<FieldResultBookmarkNode>) nodes)
        {
            if (msString.equals(node.getBookmarkName(), name))
                return node;
        }

        return null;
    }

    private /*final*/ ArrayList<FieldResultBookmarsPreserver.FieldResultBookmarkNode> mNodes = new ArrayList<FieldResultBookmarsPreserver.FieldResultBookmarkNode>();
    private boolean mIsLeading = true;
    private /*final*/ Field mField;

    private /*enum*/ final class Position
    {
        private Position(){}
        
        public static final int UNKNOWN = 0;
        public static final int LEADING = 1;
        public static final int MIDDLE = 2;
        public static final int TRAILING = 3;

        public static final int length = 4;
    }

    private static class FieldResultBookmarkNode
    {
        FieldResultBookmarkNode(Node node)
        {
            mNode = node;
            setPosition(Position.UNKNOWN);
            mBookmarkName = ((IBookmarkNode)node).getName();
        }

        Node getNode() { return mNode; };

        private Node mNode;
        String getBookmarkName() { return mBookmarkName; };

        private String mBookmarkName;
        /*Position*/int getPosition() { return mPosition; }; void setPosition(/*Position*/int value) { mPosition = value; };

        private /*Position*/int mPosition;
    }
}

