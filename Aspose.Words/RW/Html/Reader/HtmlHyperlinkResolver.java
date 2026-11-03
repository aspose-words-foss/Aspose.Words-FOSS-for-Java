// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/10/2022 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;
import com.aspose.StringToObjDictionary;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.UriUtil;
import java.util.ArrayList;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.words.ref.Ref;


/// <summary>
/// Adds bookmarks for imported HTML elements with 'id' attributes
/// so that they can be referenced by hyperlinks in the same document.
/// </summary>
/// <remarks>
/// Inline, anchor and block bookmarks are processed in different ways.
/// Inline and anchor bookmarks are inserted into the document model immediately.
/// Block bookmarks are remembered and inserted after
/// the whole HTML tree has been processed. This allows to take the structure of block-level elements into account.
/// </remarks>
class HtmlHyperlinkResolver
{
    HtmlHyperlinkResolver(IHyperlinkProcessor hyperlinkProcessor, HashSetGeneric<String> bookmarkNames)
    {
        mHyperlinkProcessor = hyperlinkProcessor;
        mReferencedElementIds = new HashSetGeneric<String>();
        mBookmarkInfos = new BookmarkInfos();
        mRememberedBookmarks = bookmarkNames;
        mStartedAnchorBookmarks = new HashSetGeneric<String>();
        mProcessedAnchorBookmarks = new HashSetGeneric<String>();
        mBookmarkEnds = new StringToObjDictionary<BookmarkEnd>();
    }

    /// <summary>
    /// Initializes the HTML hyperlink resolver.
    /// </summary>
    void init(
        DocumentBuilder documentBuilder,
        HtmlBidiTextArranger bidiTextArranger,
        boolean rememberExistingBookmarks)
    {
        mBuilder = documentBuilder;
        mBidiTextArranger = bidiTextArranger;

        // If the calling code doesn't want this class to remember existing bookmarks, it shouldn't provide a bookmark
        // name cache to the constructor of this class.
        Debug.assertTrue(!rememberExistingBookmarks || (mRememberedBookmarks == null));

        // If the bookmark cache is non-null, collection of existing bookmarks in this class is turned off.
        // This happens in two cases:
        //   either the calling code collects and provides remembered bookmarks on its own;
        //   or the calling code doesn't want this class to collect and check existing bookmarks at all.
        if (!rememberExistingBookmarks && (mRememberedBookmarks == null))
        {
            mRememberedBookmarks = new HashSetGeneric<String>();
        }
    }

    /// <summary>
    ///  Starts a bookmark from anchor or bookmark span in model.
    /// </summary>
    void startAnchorBookmarkIfNeeded(String bookmarkName, boolean parentNodeIsParagraph, boolean isBookmarkSpan) throws Exception
    {
        if (!StringUtil.hasChars(bookmarkName))
            return;

        String bookmarkNameLower = msString.toLowerInvariant(bookmarkName);
        if (containsBookmark(bookmarkNameLower))
            return;

        if (isBookmarkSpan)
        {
            // Bookmark boundaries are hard to preserve during reordering by the Unicode bidirectional algorithm (UBA),
            // so at the moment bookmark contents are isolated from the UBA to make sure it is reordered as a whole.
            mBidiTextArranger.rearrangeAndWriteText();
        }

        // WORDSNET-20553 If start of a bookmark is located just before SDT
        // and it is not in a paragraph then it should be moved to the end of the SDT.
        if (!parentNodeIsParagraph &&
            (mBuilder.getCurrentParagraph().getPrevNode() != null) &&
            !mBuilder.getCurrentParagraph().hasChildNodes() &&
            (mBuilder.getCurrentParagraph().getPrevNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG))
        {
            BookmarkStart start = new BookmarkStart(mBuilder.getDocument(), bookmarkName);
            StructuredDocumentTag sdt = (StructuredDocumentTag)mBuilder.getCurrentParagraph().getPrevNode();
            sdt.insertAfter(start, sdt.getLastChild());
        }
        else
        {
            mBuilder.startBookmark(bookmarkName);
        }

        mStartedAnchorBookmarks.add(bookmarkNameLower);
    }

    /// <summary>
    ///  Ends a bookmark from anchor or bookmark span in model.
    /// </summary>
    void endAnchorBookmarkIfNeeded(String bookmarkName, boolean parentNodeIsParagraph, boolean isBookmarkSpan) throws Exception
    {
        if (!StringUtil.hasChars(bookmarkName))
            return;

        String bookmarkNameLower = msString.toLowerInvariant(bookmarkName);
        if (mProcessedAnchorBookmarks.contains(bookmarkNameLower))
            return;

        if (!mStartedAnchorBookmarks.contains(bookmarkNameLower))
            return;

        if (isBookmarkSpan)
        {
            // Bookmark boundaries are hard to preserve during reordering by the Unicode bidirectional algorithm (UBA),
            // so at the moment bookmark contents are isolated from the UBA to make sure it is reordered as a whole.
            mBidiTextArranger.rearrangeAndWriteText();
        }

        if (getBookmarkEnds().containsKey(bookmarkName))
        {
            BookmarkEnd bookmarkEnd = getBookmarkEnds().get(bookmarkName);
            bookmarkEnd.remove();
            getBookmarkEnds().remove(bookmarkName);
        }
        BookmarkEnd newBookmarkEnd;

        // WORDSNET-20553 If start of a bookmark is located just before SDT
        // and it is not in a paragraph then it should be moved to the end of the SDT.
        if (!parentNodeIsParagraph &&
            (mBuilder.getCurrentParagraph().getPrevNode() != null) &&
            !mBuilder.getCurrentParagraph().hasChildNodes() &&
            (mBuilder.getCurrentParagraph().getPrevNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG))
        {
            newBookmarkEnd = new BookmarkEnd(mBuilder.getDocument(), bookmarkName);
            StructuredDocumentTag sdt = (StructuredDocumentTag)mBuilder.getCurrentParagraph().getPrevNode();
            sdt.insertAfter(newBookmarkEnd, sdt.getLastChild());
        }
        else
        {
            newBookmarkEnd = mBuilder.endBookmark(bookmarkName);
        }
        getBookmarkEnds().add(bookmarkName, newBookmarkEnd);

        if (isBookmarkSpan)
            mProcessedAnchorBookmarks.add(bookmarkNameLower);
    }

    /// <summary>
    /// Start a new inline bookmark.
    /// </summary>
    BookmarkStart startInlineBookmarkIfNeeded(IHtmlElementProvider element) throws Exception
    {
        String elementId = getElementId(element);
        if (!StringUtil.hasChars(elementId))
            return null;

        if (containsBookmark(elementId))
            return null;

        // Create an inline bookmark inside the current paragraph.
        BookmarkStart bookmarkStart = mBuilder.startBookmark(elementId);
        BookmarkEnd bookmarkEnd = new BookmarkEnd(mBuilder.getDocument());
        bookmarkEnd.setName(elementId);
        Bookmark bookmark = new Bookmark(bookmarkStart, bookmarkEnd);
        mBookmarkInfos.addInlineBookmark(elementId, bookmark);
        return bookmarkStart;
    }

    /// <summary>
    /// Ends an inline bookmark.
    /// </summary>
    BookmarkEnd endInlineBookmarkIfNeeded(IHtmlElementProvider element) throws Exception
    {
        String elementId = getElementId(element);
        if (!StringUtil.hasChars(elementId))
            return null;

        Bookmark bookmark = mBookmarkInfos.getInlineBookmark(elementId);
        if (bookmark == null)
            return null;

        // It is a duplicated end of bookmark.
        if (bookmark.getBookmarkEnd().getParentNode() != null)
            return null;

        mBuilder.insertNode(bookmark.getBookmarkEnd());
        getBookmarkEnds().add(elementId, bookmark.getBookmarkEnd());
        return bookmark.getBookmarkEnd();
    }

    /// <summary>
    /// Marks current paragraph as a node after which bookmark will be added.
    /// </summary>
    void addPendingBookmarkIfNeeded(HtmlElementNode elementNode) throws Exception
    {
        String elementId = getElementId(elementNode);
        if (!StringUtil.hasChars(elementId))
            return;

        if (containsBookmark(elementId))
            return;

        NodePair nodePair = new NodePair();
        nodePair.setStartBookmarkNode(mBuilder.getCurrentParagraph());
        mBookmarkInfos.addPendingBookmark(elementId, mBuilder.getCurrentParagraph(), nodePair);
    }

    /// <summary>
    /// Marks the table as a node after which bookmark will be added.
    /// </summary>
    void addPendingBookmarkForTableIfNeeded(HtmlElementNode tableNode, Table table) throws Exception
    {
        // The started table is inserted before current paragraph.
        // We should update the node before which the bookmarks will be inserted.
        mBookmarkInfos.updatePendingStartBookmark(mBuilder.getCurrentParagraph(), table);

        String elementId = getElementId(tableNode);
        if (!StringUtil.hasChars(elementId))
            return;

        if (containsBookmark(elementId))
            return;

        NodePair nodePair = new NodePair();
        nodePair.setStartBookmarkNode(table);
        nodePair.setEndBookmarkNode(table);
        mBookmarkInfos.addPendingBookmark(elementId, mBuilder.getCurrentParagraph(), nodePair);
    }

    /// <summary>
    /// Marks current paragraph as a node after which a start of bookmark will be added.
    /// </summary>
    void addPendingStartBookmarkIfNeeded(HtmlElementNode elementNode) throws Exception
    {
        String elementId = getElementId(elementNode);
        if (!StringUtil.hasChars(elementId))
            return;

        if (containsBookmark(elementId))
            return;

        NodePair nodePair = new NodePair();
        nodePair.setStartBookmarkNode(mBuilder.getCurrentParagraph());
        mBookmarkInfos.addPendingBookmark(elementId, mBuilder.getCurrentParagraph(), nodePair);
    }

    /// <summary>
    /// Marks current paragraph as a node after which an end of bookmark will be added.
    /// </summary>
    void addPendingEndBookmarkIfNeeded(HtmlElementNode elementNode)
    {
        String elementId = getElementId(elementNode);
        if (!StringUtil.hasChars(elementId))
            return;

        NodePair nodePair = mBookmarkInfos.getPendingBookmark(elementId);
        if (nodePair == null)
            return;

        // If the end of bookmark is not null then the element node has duplicated bookmark.
        if (nodePair.getEndBookmarkNode() == null)
            nodePair.setEndBookmarkNode(mBuilder.getCurrentParagraph());
    }

    /// <summary>
    /// Inserts pending bookmarks and optionally removes unreferenced bookmarks.
    /// </summary>
    /// <remarks>
    /// A bookmark is unreferenced if it is created from an element with the 'id' attribute but is not referenced
    /// by any hyperlink in the document.
    /// </remarks>
    void processBookmarksAndHyperlinks(boolean keepUnreferencedIdBookmarks) throws Exception
    {
        for (String hyperlink : mBookmarkInfos.getPendingBookmarks())
        {
            // We create unreferenced pending bookmarks only if we're asked to keep them. We don't want to create bookmarks
            // that we'll have to remove right away.
            if (keepUnreferencedIdBookmarks || mReferencedElementIds.contains(hyperlink))
            {
                NodePair nodePair = mBookmarkInfos.getPendingBookmark(hyperlink);
                if (nodePair == null)
                    continue;

                CompositeNode startBookmarkParentNode = nodePair.getStartBookmarkNode().getParentNode();
                BookmarkStart bookmarkStart = new BookmarkStart(mBuilder.getDocument(), hyperlink);
                BookmarkEnd bookmarkEnd = new BookmarkEnd(mBuilder.getDocument(), hyperlink);
                if (nodePair.getEndBookmarkNode() != null)
                {
                    startBookmarkParentNode.insertBefore(bookmarkStart, nodePair.getStartBookmarkNode());
                    nodePair.getEndBookmarkNode().insertNext(bookmarkEnd);
                }
                else
                {
                    // Insert a bookmark after the corresponding node.
                    startBookmarkParentNode.insertAfter(bookmarkStart, nodePair.getStartBookmarkNode());
                    startBookmarkParentNode.insertAfter(bookmarkEnd, bookmarkStart);
                }
                getBookmarkEnds().add(hyperlink, bookmarkEnd);
            }
        }

        // Remove unused inline bookmarks if asked to.
        if (!keepUnreferencedIdBookmarks)
        {
            StringToObjDictionary<Bookmark>.Enumerator enumerator = mBookmarkInfos.getInlineBookmarksEnumerator();
            while (enumerator.moveNext())
            {
                if (mReferencedElementIds.contains(enumerator.getCurrentKey()))
                    continue;

                if (enumerator.getCurrentValue() != null)
                {
                    enumerator.getCurrentValue().remove();
                    getBookmarkEnds().remove(enumerator.getCurrentValue().getName());
                }
            }
        }
    }

    /// <summary>
    /// Adds a hyperlink if it starts from '#'.
    /// </summary>
    void addHyperlink(String hyperlink)
    {
        if (UriUtil.isSubAddressOnly(hyperlink))
        {
            String hyperlinkName = msString.toLowerInvariant(UriUtil.getSubAddress(hyperlink));
            if (!mReferencedElementIds.contains(hyperlinkName))
                mReferencedElementIds.add(hyperlinkName);
        }
    }

    /// <summary>
    /// Indicates whether the corresponding anchor bookmark was created or not.
    /// </summary>
    boolean containsStartedAnchorBookmark(String bookmarkName)
    {
        return mStartedAnchorBookmarks.contains(msString.toLowerInvariant(bookmarkName));
    }

    /// <summary>
    /// Holds the ends of bookmarks created during import HTML document.
    /// Keys - bookmark names. Values - ends of bookmarks.
    /// </summary>
    StringToObjDictionary<BookmarkEnd> getBookmarkEnds() { return mBookmarkEnds; };

    private StringToObjDictionary<BookmarkEnd> mBookmarkEnds;

    /// <summary>
    /// Indicates whether the corresponding bookmark (the start and the end) was created before reading the HTML document or
    /// during reading the HTML document.
    /// </summary>
    private boolean containsBookmark(String bookmarkName) throws Exception
    {
        String bookmarkNameLower = msString.toLowerInvariant(bookmarkName);
        if (mBookmarkInfos.containsBookmark(bookmarkNameLower) ||
            mStartedAnchorBookmarks.contains(bookmarkNameLower))
        {
            return true;
        }

        // Check if a bookmark with the same name had been created in the document before we started to import HTML.
        // WORDSNET-28606 This check implies traversing the whole document tree in order to find all bookmarks, which may
        // be quite slow for big documents. Since not all HTML documents create bookmarks, it is a good idea to delay
        // tree traversal until we really need to insert a bookmark.
        if (mRememberedBookmarks == null)
        {
            mRememberedBookmarks = new HashSetGeneric<String>();
            // WORDSNET-28606 We used to use Document.GetChildNodes(NodeType.BookmarkStart, true) but it was noticeably
            // slower than the approach with DocumentVisitor.
            mBuilder.getDocument().accept(new BookmarkStartCollector(mRememberedBookmarks));
        }
        return mRememberedBookmarks.contains(bookmarkNameLower);
    }

    /// <summary>
    /// Returns normalized element Id if the corresponding HTML element node contains 'id' attribute
    /// or an empty string otherwise.
    /// </summary>
    private String getElementId(IHtmlElementProvider element)
    {
        String idValue = element.getAttributeValue("id");
        if (idValue == null)
        {
            return "";
        }

        // Element IDs are used as bookmark names, which are case-insensitive.
        idValue = msString.toLowerInvariant(idValue);

        // We need to perform format-specific processing of element IDs, because they will be used as bookmark names.
        if (mHyperlinkProcessor != null)
        {
            idValue = mHyperlinkProcessor.mapBookmarkName(idValue);
        }

        return idValue;
    }

    /// <summary>
    /// If specified, this processor is used to map bookmark names imported from HTML into unique bookmark names that
    /// can be used in the resulting document without conflicts.
    /// </summary>
    private /*final*/ IHyperlinkProcessor mHyperlinkProcessor;

    /// <summary>
    /// Holds IDs of HTML elements that are referenced by hyperlinks. Items are in lower case.
    /// </summary>
    private /*final*/ HashSetGeneric<String> mReferencedElementIds;

    private /*final*/ BookmarkInfos mBookmarkInfos;

    /// <summary>
    /// Holds names of started anchor bookmarks.
    /// </summary>
    private /*final*/ HashSetGeneric<String> mStartedAnchorBookmarks;

    /// <summary>
    /// Holds names of processed anchor bookmarks.
    /// </summary>
    private /*final*/ HashSetGeneric<String> mProcessedAnchorBookmarks;

    private DocumentBuilder mBuilder;

    private HtmlBidiTextArranger mBidiTextArranger;

    /// <summary>
    /// Holds names of bookmarks that already were in the model before loading of HTML started. Items are in lower case.
    /// </summary>
    private HashSetGeneric<String> mRememberedBookmarks;

    /// <summary>
    /// Holds the nodes after or before which starts or ends of bookmark will be inserted
    /// during processing bookmarks and hyperlinks.
    /// </summary>
    private static class NodePair
    {
        Node getStartBookmarkNode() { return mStartBookmarkNode; }; void setStartBookmarkNode(Node value) { mStartBookmarkNode = value; };

        private Node mStartBookmarkNode;

        Node getEndBookmarkNode() { return mEndBookmarkNode; }; void setEndBookmarkNode(Node value) { mEndBookmarkNode = value; };

        private Node mEndBookmarkNode;
    }

    /// <summary>
    /// Holds information about bookmarks created from the HTML elements with 'id' attribute.
    /// </summary>
    private static class BookmarkInfos
    {
        BookmarkInfos()
        {
            mInlineBookmarksById = new StringToObjDictionary<Bookmark>();
            mPendingBookmarkNodes = new StringToObjDictionary<HtmlHyperlinkResolver.NodePair>();
            mPendingBookmarkOrder = new ArrayList<String>();
            mPendingNodes = new HashMap<Node, ArrayList<String>>();
        }

        void addInlineBookmark(String elementId, Bookmark bookmark)
        {
            Debug.assertTrue(msString.equals(elementId, msString.toLowerInvariant(elementId)));
            Debug.assertTrue(!mInlineBookmarksById.containsKey(elementId));
            mInlineBookmarksById.add(elementId, bookmark);
        }

        Bookmark getInlineBookmark(String elementId)
        {
            return mInlineBookmarksById.get(elementId);
        }

        void addPendingBookmark(String elementId, Node pendingNode, NodePair nodePair)
        {
            Debug.assertTrue(msString.equals(elementId, msString.toLowerInvariant(elementId)));
            Debug.assertTrue(!mPendingBookmarkNodes.containsKey(elementId));
            mPendingBookmarkNodes.add(elementId, nodePair);
            msArrayList.add(mPendingBookmarkOrder, elementId);
            ArrayList<String> bookmarks = null;
            Ref<ArrayList<String>> referenceToBookmarks = new Ref<ArrayList<String>>(bookmarks);
            boolean outRefCondition0 = !msDictionary.tryGetValue(mPendingNodes, pendingNode, /*out*/ referenceToBookmarks);
            bookmarks = referenceToBookmarks.get();
            if (outRefCondition0)
            {
                bookmarks = new ArrayList<String>();
                msDictionary.add(mPendingNodes, pendingNode, bookmarks);
            }
            msArrayList.add(bookmarks, elementId);
        }

        NodePair getPendingBookmark(String elementId)
        {
            return mPendingBookmarkNodes.get(elementId);
        }

        ArrayList<String> getPendingBookmarks()
        {
            return mPendingBookmarkOrder;
        }

        StringToObjDictionary<Bookmark>.Enumerator getInlineBookmarksEnumerator()
        {
            return mInlineBookmarksById.iterator();
        }

        boolean containsBookmark(String bookmarkName)
        {
            Debug.assertTrue(msString.equals(bookmarkName, msString.toLowerInvariant(bookmarkName)));
            return mInlineBookmarksById.containsKey(bookmarkName) || mPendingBookmarkNodes.containsKey(bookmarkName);
        }

        /// <summary>
        /// Replaces the old node with new one for the pending start of bookmark.
        /// </summary>
        void updatePendingStartBookmark(Node oldNode, Node newNode)
        {
            ArrayList<String> bookmarkNames = null;
            Ref<ArrayList<String>> referenceToBookmarkNames = new Ref<ArrayList<String>>(bookmarkNames);
            boolean outRefCondition1 = msDictionary.tryGetValue(mPendingNodes, oldNode, /*out*/ referenceToBookmarkNames);
            bookmarkNames = referenceToBookmarkNames.get();
            if (outRefCondition1)
            {
                msDictionary.remove(mPendingNodes, oldNode);
                mPendingNodes.put(newNode, bookmarkNames);
                for (String bookmarkName : bookmarkNames)
                {
                    NodePair nodePair = getPendingBookmark(bookmarkName);
                    if (nodePair != null)
                    {
                        nodePair.setStartBookmarkNode(newNode);
                    }
                }
            }
        }

        /// <summary>
        /// Holds the bookmarks created from HTML elements with 'id' attribute.
        /// Keys - values of 'id' attribute. Values - bookmarks.
        /// </summary>
        private /*final*/ StringToObjDictionary<Bookmark> mInlineBookmarksById;

        /// <summary>
        /// Holds nodes after which a start and an end of bookmark will be inserted during processing bookmarks and hyperlinks.
        /// Keys - bookmark names. Values - pairs of nodes.
        /// </summary>
        private /*final*/ StringToObjDictionary<HtmlHyperlinkResolver.NodePair> mPendingBookmarkNodes;

        /// <summary>
        /// Holds bookmarks in the order of their creation.
        /// </summary>
        private /*final*/ ArrayList<String> mPendingBookmarkOrder;

        /// <summary>
        /// Holds nodes for which bookmarks will be inserted during processing bookmarks and hyperlinks.
        /// Keys - Nodes. Values - bookmark names.
        /// </summary>
        private /*final*/ HashMap<Node, ArrayList<String>> mPendingNodes;
    }

    /// <summary>
    /// Collects lowercase names of all bookmark start nodes in a document.
    /// </summary>
    private static class BookmarkStartCollector extends DocumentVisitor
    {
        BookmarkStartCollector(HashSetGeneric<String> bookmarkNames)
        {
            mBookmarkNames = bookmarkNames;
        }

        public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart)
        {
            mBookmarkNames.add(msString.toLowerInvariant(bookmarkStart.getName()));
            return VisitorAction.CONTINUE;
        }

        private /*final*/ HashSetGeneric<String> mBookmarkNames;
    }
}

