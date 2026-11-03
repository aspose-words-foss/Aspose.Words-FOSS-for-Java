// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2016 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.Debug;


/// <summary>
/// Implements deferred removing of bookmarks from TOC field results, when they are used by PAGEREF fields in another TOC field results.
/// </summary>
class TocEntriesBookmarkDeferredRemover
{
    void registerBookmark(String bookmarkName)
    {
        msArrayList.add(mBookmarkNames, bookmarkName);
    }

    void freezeBookmarks(Field field)
    {
        mCurrentFieldBookmarks = null;
        if (field.getType() != FieldType.FIELD_TOC)
            return;

        NodeRange fieldResultRange = new NodeRange(field.getSeparator(), field.getEnd());
        mCurrentFieldBookmarks = BookmarkFinder.find(fieldResultRange, mBookmarkNames);
    }

    void restoreFrozenBookmarks(Field field)
    {
        if (mCurrentFieldBookmarks == null || mCurrentFieldBookmarks.length == 0)
            return;

        field.ensureSeparator(false);

        Paragraph firstParagraph = (Paragraph)field.getSeparator().getAncestor(NodeType.PARAGRAPH);

        for (int i = mCurrentFieldBookmarks.length - 1; i >= 0; i--)
        {
            BookmarkWithParagraphOffset bookmark = mCurrentFieldBookmarks[i];

            Paragraph paragraph = getParagraph(firstParagraph, bookmark.getParagraphOffset());

            Node bookmarkNode = bookmark.getNode();
            paragraph.insertAfter(bookmarkNode, (paragraph == firstParagraph) ? field.getSeparator() : null);

            msArrayList.add(mBookmarkNodes, bookmarkNode);
        }
    }

    void removeFrozenBookmarks()
    {
        for (Node node : mBookmarkNodes)
        {
            if (node.getParentNode() != null)
                node.remove();
        }
    }

    private static Paragraph getParagraph(Paragraph paragraph, int offset)
    {
        Paragraph result = paragraph;

        for (int i = 0; i < offset; i++)
        {
            if (result.getNextNonAnnotationSibling() == null)
                break;

            result = (Paragraph)result.getNextNonAnnotationSibling();
        }

        return result;
    }

    private BookmarkWithParagraphOffset[] mCurrentFieldBookmarks;

    private /*final*/ ArrayList<String> mBookmarkNames = new ArrayList<String>();
    private /*final*/ ArrayList<Node> mBookmarkNodes = new ArrayList<Node>();

    /// <summary>
    /// Finds bookmarks with particular names in specified range.
    /// </summary>
    private static class BookmarkFinder extends NodeFinder
    {
        static BookmarkWithParagraphOffset[] find(NodeRange range, ArrayList<String> expectedBookmarkNames)
        {
            BookmarkFinder finder = new BookmarkFinder(range, expectedBookmarkNames);
            try /*JAVA: was using*/
            {
                ArrayList<Node> nodes = finder.find();
                Debug.assertTrue(nodes.size() == finder.mOffsets.size());

                BookmarkWithParagraphOffset[] result = new BookmarkWithParagraphOffset[nodes.size()];

                for (int i = 0; i < result.length; i++)
                {
                    Node node = nodes.get(i);
                    int offset = finder.mOffsets.get(i);

                    result[i] = new BookmarkWithParagraphOffset(node, offset);
                }

                return result;
            }
            finally { if (finder != null) finder.dispose(); }
        }

        private BookmarkFinder(NodeRange range, ArrayList<String> expectedBookmarkNames)
        {
        	super(range, NodeType.BOOKMARK_START, NodeType.BOOKMARK_END);
	
            mExpectedBookmarkNames = expectedBookmarkNames;
            mFirstParagraph = (Paragraph)range.getStart().getNode().getAncestor(NodeType.PARAGRAPH);
        }

        protected /*override*/ boolean onNodeFinding()
        {
            IBookmarkNode bookmarkNode = (IBookmarkNode)getCurrentNode();

            if (mExpectedBookmarkNames.indexOf(bookmarkNode.getName()) == -1)
                return false;

            Paragraph bookmarkParagraph = (Paragraph)getCurrentNode().getAncestor(NodeType.PARAGRAPH);
            Debug.assertTrue(mFirstParagraph.getParentNode() == bookmarkParagraph.getParentNode());

            int offset = 0;
            Paragraph paragraph = bookmarkParagraph;
            while (paragraph != mFirstParagraph)
            {
                paragraph = (Paragraph)paragraph.previousSiblingOfType(NodeType.PARAGRAPH);
                offset++;
            }

            msArrayList.add(mOffsets, offset);

            return true;
        }

        private /*final*/ ArrayList<String> mExpectedBookmarkNames;
        private /*final*/ Paragraph mFirstParagraph;
        private /*final*/ ArrayList<Integer> mOffsets = new ArrayList<Integer>();
    }

    private static class BookmarkWithParagraphOffset
    {
        public BookmarkWithParagraphOffset(Node node, int paragraphOffset)
        {
            mNode = node;
            mParagraphOffset = paragraphOffset;
        }

        Node getNode() { return mNode; };

        private Node mNode;

        int getParagraphOffset() { return mParagraphOffset; };

        private int mParagraphOffset;
    }
}

