// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Test that node inserting and removing works.
/// </summary>
@Test
public class TestNodeInsertRemove
{
    /// <summary>
    /// Test the fast method of appending children.
    /// </summary>
    @Test
    public void testAppendChildForLoad() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        //Appends when there are no children
        Node sect1 = doc.appendChildForLoad(new Section(doc));
        //Appends when there are some children
        Node sect2 = doc.appendChildForLoad(new Section(doc));

        //Check document knows children
        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect2, doc.getLastChild());

        //Check sections know the document
        msAssert.areEqual(doc, sect1.getParentNode());
        msAssert.areEqual(doc, sect1.getDocument());
        msAssert.areEqual(doc, sect2.getParentNode());
        msAssert.areEqual(doc, sect2.getDocument());

        //Check sections know each other
        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(null, sect2.getNextSibling());
    }

    @Test
    public void testAppendSingleChildForLoad() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        //Appends when there are no children
        Node sect1 = doc.appendChildForLoad(new Section(doc));

        //Check document knows children
        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect1, doc.getLastChild());

        //Check sections know the document
        msAssert.areEqual(doc, sect1.getParentNode());
        msAssert.areEqual(doc, sect1.getDocument());

        //Check sections know each other
        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(null, sect1.getNextSibling());
    }

    @Test
    public void testAppendManyChildsForLoad() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        //Appends when there are no children
        Node sect1 = doc.appendChildForLoad(new Section(doc));
        //Appends when there are some children
        Node sect2 = doc.appendChildForLoad(new Section(doc));
        Node sect3 = doc.appendChildForLoad(new Section(doc));

        //Check document knows children
        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect3, doc.getLastChild());

        //Check sections know the document
        msAssert.areEqual(doc, sect1.getParentNode());
        msAssert.areEqual(doc, sect1.getDocument());

        //Check sections know each other
        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(sect3, sect2.getNextSibling());
        msAssert.areEqual(sect2, sect3.getPreviousSibling());
        msAssert.areEqual(null, sect3.getNextSibling());
    }

    /// <summary>
    /// Test insert after last child.
    /// </summary>
    @Test
    public void testAppendChild() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);

        //Appends when there are no children
        Node sect1 = doc.appendChild(new Section(doc));

        //Check document is valid after insert
        msAssert.areEqual(true, doc.hasChildNodes());
        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect1, doc.getLastChild());

        //Check section is valid after insert
        msAssert.areEqual(doc, sect1.getParentNode());
        msAssert.areEqual(doc, sect1.getDocument());
        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(null, sect1.getNextSibling());

        //Appends when there are some children
        Node sect2 = doc.appendChild(new Section(doc));

        //Check both sections siblings are working
        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(null, sect2.getNextSibling());

        //Appends third child
        Node sect3 = doc.appendChild(new Section(doc));

        //Check sections siblings are working
        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(sect3, sect2.getNextSibling());
        msAssert.areEqual(sect2, sect3.getPreviousSibling());
        msAssert.areEqual(null, sect3.getNextSibling());
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testAppendInvalidNodeType() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect = doc.appendChild(new Section(doc));
        sect.appendChild(new Section(doc));
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QCannot add a node to self.\\E")
    public void testAppendSelf() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        doc.appendChild(doc);
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe newChild is an ancestor of this node.\\E")
    public void testAppendAncestor() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect = doc.appendChild(new Section(doc));
        sect.appendChild(doc);
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe newChild was created from a different document than the one that created this node.\\E")
    public void testAppendFromDifferentDoc() throws Exception
    {
        Document doc1 = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect = new Section(doc1);

        Document doc2 = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        doc2.appendChild(sect);
    }

    @Test
    public void testAppendEvents() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        AppendEventsContext context = new AppendEventsContext();
        context.setDoc(doc);
        context.getDoc().setNodeChangingCallback(new HandleTestAppendEvents(context));

        context.setInsertedNode(new Section(doc));
        context.getDoc().appendChild(context.getInsertedNode());

        msAssert.areEqual(true, context.isInsertingFired());
        msAssert.areEqual(true, context.isInsertedFired());
    }

    private static class AppendEventsContext
    {
        public boolean isInsertingFired() { return mIsInsertingFired; }; public void isInsertingFired(boolean value) { mIsInsertingFired = value; };

        private boolean mIsInsertingFired;
        public boolean isInsertedFired() { return mIsInsertedFired; }; public void isInsertedFired(boolean value) { mIsInsertedFired = value; };

        private boolean mIsInsertedFired;
        public Node getInsertedNode() { return mInsertedNode; }; public void setInsertedNode(Node value) { mInsertedNode = value; };

        private Node mInsertedNode;
        
        public Document getDoc() { return mDoc; }; 
        public void setDoc(Document value) { mDoc = value; };

        private Document mDoc;
    }

    private static class HandleTestAppendEvents implements INodeChangingCallback
    {
        public HandleTestAppendEvents(AppendEventsContext context)
        {
            mContext = context;
        }

        public void /*INodeChangingCallback.*/nodeInserting(NodeChangingArgs e)
        {
            msAssert.areEqual(NodeChangingAction.INSERT, e.getAction());
            msAssert.areEqual(null, e.getOldParent());
            msAssert.areEqual(mContext.getDoc(), e.getNewParent());
            msAssert.areEqual(mContext.getInsertedNode(), e.getNode());
            mContext.isInsertingFired(true);
        }

        public void /*INodeChangingCallback.*/nodeInserted(NodeChangingArgs e)
        {
            msAssert.areEqual(NodeChangingAction.INSERT, e.getAction());
            msAssert.areEqual(null, e.getOldParent());
            msAssert.areEqual(mContext.getDoc(), e.getNewParent());
            msAssert.areEqual(mContext.getInsertedNode(), e.getNode());
            mContext.isInsertedFired(true);
        }

        public void /*INodeChangingCallback.*/nodeRemoving(NodeChangingArgs e)
        {
            // Do nothing.
        }

        public void /*INodeChangingCallback.*/nodeRemoved(NodeChangingArgs e)
        {
            // Do nothing.
        }

        private AppendEventsContext mContext;
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThis node is not a parent of the oldChild node.\\E")
    public void testRemoveNonChild() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        doc.removeChild(doc);
    }

    @Test
    public void testRemoveSingleChild() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect = doc.appendChild(new Section(doc));

        msAssert.areEqual(sect, doc.removeChild(sect));

        //Check the document no longer has the child
        msAssert.areEqual(null, doc.getFirstChild());
        msAssert.areEqual(null, doc.getLastChild());

        //Check the removed element is detached
        msAssert.areEqual(null, sect.getParentNode());
        msAssert.areEqual(doc, sect.getDocument());
    }

    @Test
    public void testRemoveLastChild() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChild(new Section(doc));
        Node sect2 = doc.appendChild(new Section(doc));

        doc.removeChild(sect2);

        //Check the parent
        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect1, doc.getLastChild());

        //Check the remaining element
        msAssert.areEqual(null, sect1.getNextSibling());
        //Single element is looped to self in the list.
        msAssert.areEqual(null, sect1.getNextNode());
        msAssert.areEqual(null, sect1.getPrevNode());

        //Check the deleted element
        msAssert.areEqual(null, sect2.getNextSibling());
        //But a single element that is not in a tree is not a list.
        msAssert.areEqual(null, sect2.getNextNode());
        msAssert.areEqual(null, sect2.getPrevNode());
    }

    @Test
    public void testRemoveFirstChild() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChild(new Section(doc));
        Node sect2 = doc.appendChild(new Section(doc));

        doc.removeChild(sect1);

        //Check the parent
        msAssert.areEqual(sect2, doc.getFirstChild());
        msAssert.areEqual(sect2, doc.getLastChild());

        //Check the remaining element
        msAssert.areEqual(null, sect2.getNextSibling());
        msAssert.areEqual(null, sect2.getNextNode());
        msAssert.areEqual(null, sect2.getPrevNode());

        //Check the deleted element
        msAssert.areEqual(null, sect1.getNextSibling());
        msAssert.areEqual(null, sect1.getNextNode());
        msAssert.areEqual(null, sect1.getPrevNode());
    }

    @Test
    public void testRemoveMiddleChild() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChild(new Section(doc));
        Node sect2 = doc.appendChild(new Section(doc));
        Node sect3 = doc.appendChild(new Section(doc));

        doc.removeChild(sect2);

        //Check the remaining elements
        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect3, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect3.getPreviousSibling());
        msAssert.areEqual(null, sect3.getNextSibling());
    }

    /// <summary>
    /// WORDSNET-7087 Null reference exception occurs when adding a new run to paragraph.
    /// Added appropriate exception to CompositeNode.Insert.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testInsertSameNodeTwice() throws Exception
    {
        Document doc = new Document();
        // Get the paragraph from the document, we will be adding runs of text to it.
        Paragraph para = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);

        // Add a run of normal text.
        Run run = new Run(doc, "Normal text");
        para.appendChild(run);
        para.appendChild(run); // Exception occurs here
    }

    @Test
    public void testInsertAfterInMiddle() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChildForLoad(new Section(doc));
        Node sect2 = doc.appendChildForLoad(new Section(doc));
        Node sect4 = doc.appendChildForLoad(new Section(doc));

        Node sect3 = new Section(doc);
        msAssert.areEqual(sect3, doc.insertAfter(sect3, sect2));

        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(sect3, sect2.getNextSibling());
        msAssert.areEqual(sect2, sect3.getPreviousSibling());
        msAssert.areEqual(sect4, sect3.getNextSibling());
        msAssert.areEqual(sect3, sect4.getPreviousSibling());
        msAssert.areEqual(null, sect4.getNextSibling());

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect4, doc.getLastChild());
    }

    @Test
    public void testInsertAfterFirst() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChildForLoad(new Section(doc));
        Node sect3 = doc.appendChildForLoad(new Section(doc));

        Node sect2 = new Section(doc);
        msAssert.areEqual(sect2, doc.insertAfter(sect2, sect1));

        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(sect3, sect2.getNextSibling());
        msAssert.areEqual(sect2, sect3.getPreviousSibling());
        msAssert.areEqual(null, sect3.getNextSibling());

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect3, doc.getLastChild());
    }

    @Test
    public void testInsertAfterLast() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChildForLoad(new Section(doc));
        Node sect2 = doc.appendChildForLoad(new Section(doc));

        Node sect3 = new Section(doc);
        msAssert.areEqual(sect3, doc.insertAfter(sect3, sect2));

        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(sect3, sect2.getNextSibling());
        msAssert.areEqual(sect2, sect3.getPreviousSibling());
        msAssert.areEqual(null, sect3.getNextSibling());

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect3, doc.getLastChild());
    }

    @Test
    public void testInsertAfterSingle() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChildForLoad(new Section(doc));

        Node sect2 = new Section(doc);
        msAssert.areEqual(sect2, doc.insertAfter(sect2, sect1));

        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(null, sect2.getNextSibling());

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect2, doc.getLastChild());
    }

    @Test
    public void testInsertAfterNull() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect2 = doc.insertAfter(new Section(doc), null);

        //Inserting after null actually places the child in front of the list.
        Node sect1 = doc.insertAfter(new Section(doc), null);

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect2, doc.getLastChild());

        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(null, sect2.getNextSibling());
    }

    @Test
    public void testInsertBefore() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect3 = doc.appendChildForLoad(new Section(doc));

        //This tests insert before first child
        Node sect1 = doc.insertBefore(new Section(doc), sect3);
        //This tests insert before some other child
        Node sect2 = doc.insertBefore(new Section(doc), sect3);

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect3, doc.getLastChild());

        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect3, sect2.getNextSibling());
        msAssert.areEqual(null, sect3.getNextSibling());
    }

    @Test
    public void testInsertBeforeSingle() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect2 = doc.appendChildForLoad(new Section(doc));

        // Single node is looped to null.
        msAssert.areEqual(sect2, doc.getFirstChild());
        msAssert.areEqual(sect2, doc.getLastChild());
        msAssert.areEqual(null, sect2.getPrevNode());
        msAssert.areEqual(null, sect2.getNextNode());

        // This tests insert before single child.
        Node sect1 = doc.insertBefore(new Section(doc), sect2);

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect2, doc.getLastChild());

        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(null, sect2.getNextSibling());
    }

    @Test
    public void testInsertBeforeFirst() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect2 = doc.appendChildForLoad(new Section(doc));
        Node sect3 = doc.appendChildForLoad(new Section(doc));

        msAssert.areEqual(sect2, doc.getFirstChild());
        msAssert.areEqual(sect3, doc.getLastChild());

        Node sect1 = doc.insertBefore(new Section(doc), sect2);

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect3, doc.getLastChild());

        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(sect3, sect2.getNextSibling());
        msAssert.areEqual(sect2, sect3.getPreviousSibling());
        msAssert.areEqual(null, sect3.getNextSibling());
    }

    @Test
    public void testInsertBeforeLast() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChildForLoad(new Section(doc));
        Node sect3 = doc.appendChildForLoad(new Section(doc));

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect3, doc.getLastChild());

        Node sect2 = doc.insertBefore(new Section(doc), sect3);

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect3, doc.getLastChild());

        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(sect3, sect2.getNextSibling());
        msAssert.areEqual(sect2, sect3.getPreviousSibling());
        msAssert.areEqual(null, sect3.getNextSibling());
    }

    @Test
    public void testInsertBeforeInMiddle() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChildForLoad(new Section(doc));
        Node sect2 = doc.appendChildForLoad(new Section(doc));
        Node sect4 = doc.appendChildForLoad(new Section(doc));

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect4, doc.getLastChild());

        Node sect3 = doc.insertBefore(new Section(doc), sect4);

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect4, doc.getLastChild());

        msAssert.areEqual(null, sect1.getPreviousSibling());
        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(sect1, sect2.getPreviousSibling());
        msAssert.areEqual(sect3, sect2.getNextSibling());
        msAssert.areEqual(sect2, sect3.getPreviousSibling());
        msAssert.areEqual(sect4, sect3.getNextSibling());
        msAssert.areEqual(sect3, sect4.getPreviousSibling());
        msAssert.areEqual(null, sect4.getNextSibling());
    }

    @Test
    public void testInsertBeforeNull() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.insertBefore(new Section(doc), null);

        //Inserting before null actually places the child at the end.
        Node sect2 = doc.insertBefore(new Section(doc), null);

        msAssert.areEqual(sect1, doc.getFirstChild());
        msAssert.areEqual(sect2, doc.getLastChild());

        msAssert.areEqual(sect2, sect1.getNextSibling());
        msAssert.areEqual(null, sect2.getNextSibling());
    }


}

