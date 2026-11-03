// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.Iterator;
import com.aspose.EnumeratorWrapperPalGeneric;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Test that node lists and enumeration works.
/// </summary>
@Test
public class TestNodeCollection
{
    @Test
    public void testGetChildNode() throws Exception
    {
        Document doc = createTestDoc();

        Paragraph para = (Paragraph)doc.getFirstSection().getBody().getChildNodes(NodeType.ANY, false).get(0);

        //Test access from the start
        msAssert.areEqual("Body1", para.getChild(NodeType.RUN, 0, false).getText());
        msAssert.areEqual("Body2", para.getChild(NodeType.RUN, 1, false).getText());
        msAssert.areEqual(null, para.getChild(NodeType.RUN, 2, false));

        //Test access from the end
        msAssert.areEqual("Body2", para.getChild(NodeType.RUN, -1, false).getText());
        msAssert.areEqual("Body1", para.getChild(NodeType.RUN, -2, false).getText());
        msAssert.areEqual(null, para.getChild(NodeType.RUN, -3, false));
    }

    /// <summary>
    /// Tests the flat child nodes collection.
    /// </summary>
    @Test
    public void testFlatNodes() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChild(new Section(doc));
        Node sect2 = doc.appendChild(new Section(doc));

        NodeCollection nodes = doc.getChildNodes(NodeType.ANY, false);
        msAssert.areEqual(2, nodes.getCount());
        msAssert.areEqual(sect1, nodes.get(0));
        msAssert.areEqual(sect2, nodes.get(1));

        //Also test access from the back.
        msAssert.areEqual(sect2, nodes.get(-1));
        msAssert.areEqual(sect1, nodes.get(-2));

        //It is a live collection, check it is updated when the doc is modified.
        Node sect0 = doc.prependChild(new Section(doc));
        msAssert.areEqual(3, nodes.getCount());
        msAssert.areEqual(sect0, nodes.get(0));
        msAssert.areEqual(sect1, nodes.get(1));
        msAssert.areEqual(sect2, nodes.get(2));

        //Also test access from the back.
        msAssert.areEqual(sect2, nodes.get(-1));
        msAssert.areEqual(sect1, nodes.get(-2));
        msAssert.areEqual(sect0, nodes.get(-3));
    }

    /// <summary>
    /// Out of range access to flat NodeCollection returns nulls.
    /// </summary>
    @Test
    public void testFlatNodesOutOfRange() throws Exception
    {
        Document doc = createTestDoc();
        
        NodeCollection sections = doc.getChildNodes(NodeType.ANY, false);
        msAssert.areEqual(1, sections.getCount());
        msAssert.isNull(sections.get(-2));    //-1 means the last element, so have to go one further
        msAssert.isNotNull(sections.get(0));
        msAssert.isNull(sections.get(1));

        doc.removeAllChildren();
        msAssert.areEqual(0, sections.getCount());
        msAssert.isNull(sections.get(0));
        msAssert.isNull(sections.get(-1));
    }

    @Test
    public void testFlatNodeEnumerator() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChild(new Section(doc));
        Node sect2 = doc.appendChild(new Section(doc));
        Node sect3 = doc.appendChild(new Section(doc));

        // I have to work with the explicit enumerator type directly here because I am testing IEnumerator methods,
        // but in Java it is autoported as Iterator and the methods are not available on an Iterator.
        NodeCollectionEnumerator<Node> e = (NodeCollectionEnumerator<Node>)doc.getChildNodes(NodeType.ANY, false).iterator();

        //Run over an unmodified collection
        e.reset();
        msAssert.areEqual(true, e.hasNext());
        msAssert.areEqual(sect1, e.next());
        msAssert.areEqual(true, e.hasNext());
        msAssert.areEqual(sect2, e.next());
        msAssert.areEqual(true, e.hasNext());
        msAssert.areEqual(sect3, e.next());
        msAssert.areEqual(false, e.hasNext());

        //Run over a modified collection
        e.reset();
        msAssert.areEqual(true, e.hasNext());
        msAssert.areEqual(sect1, e.next());
        doc.removeChild(sect2);
        msAssert.areEqual(true, e.hasNext());
        msAssert.areEqual(sect3, e.next());
        msAssert.areEqual(false, e.hasNext());
    }

    /// <summary>
    /// Check that node collection enumerators do not share any state and can work independently.
    /// </summary>
    @Test
    public void testTwoNodeEnumerators() throws Exception
    {
        // Check both flat and deep collections.
        checkTwoNodeEnumerators(false);
        checkTwoNodeEnumerators(true);
    }

    /// <summary>
    /// Check that can retrieve nodes by type, that they appear in the correct order 
    /// and the live collection is updated when more nodes are inserted.
    /// </summary>
    @Test
    public void testDeepNodes() throws Exception
    {
        Document doc = createTestDoc();

        //This is a live collection of all runs in the document.
        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        //Test before modifying the document.
        msAssert.areEqual(3, runs.getCount());
        msAssert.areEqual("Body1", runs.get(0).getText());
        msAssert.areEqual("Body2", runs.get(1).getText());
        msAssert.areEqual("Header1", runs.get(2).getText());

        //Also test access from the back of the collection
        msAssert.areEqual("Header1", runs.get(-1).getText());
        msAssert.areEqual("Body2", runs.get(-2).getText());
        msAssert.areEqual("Body1", runs.get(-3).getText());

        //Create one more paragraph and run
        Body body = doc.getFirstSection().getBody();
        msAssert.areEqual(body.getNodeType(), NodeType.BODY);
        Paragraph para = body.appendChild(new Paragraph(doc));
        para.appendChild(new Run(doc, "Body3"));

        //Test the collection was updated
        msAssert.areEqual(4, runs.getCount());
        msAssert.areEqual("Body1", runs.get(0).getText());
        msAssert.areEqual("Body2", runs.get(1).getText());
        msAssert.areEqual("Body3", runs.get(2).getText());
        msAssert.areEqual("Header1", runs.get(3).getText());

        //Also test access from the back of the collection
        msAssert.areEqual("Header1", runs.get(-1).getText());
        msAssert.areEqual("Body3", runs.get(-2).getText());
        msAssert.areEqual("Body2", runs.get(-3).getText());
        msAssert.areEqual("Body1", runs.get(-4).getText());
    }

    /// <summary>
    /// Out of range access to deep NodeCollection returns nulls.
    /// </summary>
    @Test
    public void testDeepNodesOutOfRange() throws Exception
    {
        Document doc = createTestDoc();
        
        NodeCollection sections = doc.getChildNodes(NodeType.SECTION, true);
        msAssert.areEqual(1, sections.getCount());
        msAssert.isNull(sections.get(-2));    //-1 means the last element, so have to go one further
        msAssert.isNotNull(sections.get(0));
        msAssert.isNull(sections.get(1));

        doc.removeAllChildren();
        msAssert.areEqual(0, sections.getCount());
        msAssert.isNull(sections.get(0));
        msAssert.isNull(sections.get(-1));    
    }

    @Test
    public void testDeepNodesEnumerator() throws Exception
    {
        Document doc = createTestDoc();

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);
        Iterator<Node> e = runs.iterator();

        //Test iteration over existing elements works.
        e.hasNext();
        msAssert.areEqual("Body1", ((Node)e.next()).getText());
        e.hasNext();
        msAssert.areEqual("Body2", ((Node)e.next()).getText());

        //Insert a node that will not invalidate iteration.
        doc.getLastSection().getBody().prependChild(new Paragraph(doc));

        //Test iteration can continue after inserting a node of different type.
        e.hasNext();
        msAssert.areEqual("Header1", ((Node)e.next()).getText());
        e.hasNext();
    }

    /// <summary>
    /// Test forward and backward preorder iteration over the tree.
    /// </summary>
    @Test
    public void testPreOrderTraversal() throws Exception
    {
        Document doc = createTestDoc();

        //Try iterating over all nodes of a node
        Section section = doc.getFirstSection();
        NodeCollection all = section.getChildNodes(NodeType.ANY, true);

        msAssert.areEqual(9, all.getCount());

        //This will excercise forward preforder
        msAssert.areEqual(NodeType.BODY, all.get(0).getNodeType());
        msAssert.areEqual(NodeType.PARAGRAPH, all.get(1).getNodeType());
        msAssert.areEqual(NodeType.BOOKMARK_START, all.get(2).getNodeType());
        msAssert.areEqual(NodeType.RUN, all.get(3).getNodeType());
        msAssert.areEqual(NodeType.RUN, all.get(4).getNodeType());
        msAssert.areEqual(NodeType.PARAGRAPH, all.get(5).getNodeType());

        //This will excercise backward preorder
        msAssert.areEqual(NodeType.RUN, all.get(8).getNodeType());
        msAssert.areEqual(NodeType.PARAGRAPH, all.get(7).getNodeType());
        msAssert.areEqual(NodeType.HEADER_FOOTER, all.get(6).getNodeType());
    }

    /// <summary>
    /// Tests that XPathNodeCollection works.
    /// </summary>
    @Test
    public void testXPathNodeCollection() throws Exception
    {
        Document doc = createTestDoc();
        NodeList runs = doc.selectNodes("//Run");
        msAssert.areEqual(3, runs.getCount());

        //Test forward indexing
        msAssert.areEqual("Body1", runs.get(0).getText());
        msAssert.areEqual("Body2", runs.get(1).getText());
        msAssert.areEqual("Header1", runs.get(2).getText());
        msAssert.areEqual(null, runs.get(3));    //Out of range

        //Test backward indexing
        msAssert.areEqual("Header1", runs.get(-1).getText());
        msAssert.areEqual("Body2", runs.get(-2).getText());
        msAssert.areEqual("Body1", runs.get(-3).getText());
        msAssert.areEqual(null, runs.get(-4));    //Out of range
    }

    @Test
    public void testXPathNodeCollectionFromNode() throws Exception
    {
        Document doc = createTestDoc();
        Paragraph para = (Paragraph)doc.selectSingleNode("//Paragraph[1]");

        //Select not from document, but from 
        NodeList runs = para.selectNodes("Run");
        msAssert.areEqual(2, runs.getCount());
        msAssert.areEqual("Body1", runs.get(0).getText());
        msAssert.areEqual("Body2", runs.get(1).getText());
    }

    /// <summary>
    /// Test that can enumerate over XPathNodeCollection.
    /// </summary>
    @Test
    public void testXPathNodeEnumerator() throws Exception
    {
        Document doc = createTestDoc();
        NodeList runs = doc.selectNodes("//Run");
        EnumeratorWrapperPalGeneric<Node> e = new EnumeratorWrapperPalGeneric<Node>(runs.iterator());
        
        msAssert.areEqual(null, e.getCurrent());
        e.moveNext();
        msAssert.areEqual("Body1", ((Run)e.getCurrent()).getText());
        e.moveNext();
        msAssert.areEqual("Body2", ((Run)e.getCurrent()).getText());
        e.moveNext();
        msAssert.areEqual("Header1", ((Run)e.getCurrent()).getText());
        e.moveNext();
        msAssert.areEqual(null, e.getCurrent());
    }

    /// <summary>
    /// Just test some XPath paths to see if it works.
    /// </summary>
    @Test
    public void testLastRunInFirstSection() throws Exception
    {
        Document doc = createTestDoc();
        Node run = doc.selectSingleNode("Section[1]/Body/Paragraph/Run[last()]");
        msAssert.areEqual("Body2", run.getText());

        //Just test it can also return nulls
        run = doc.selectSingleNode("Section[1]/Body/Paragraph/Run[3]");
        msAssert.areEqual(null, run);
    }

    @Test
    public void testToArray() throws Exception
    {
        Document doc = createTestDoc();

        NodeCollection paras = doc.getChildNodes(NodeType.PARAGRAPH, true);
        msAssert.areEqual(3, paras.getCount());
        
        // Check fixed size node array was created.
        Node[] nodes = paras.toArray();
        msAssert.areEqual(3, nodes.length);
        msAssert.areEqual(nodes[0], paras.get(0));
        msAssert.areEqual(nodes[1], paras.get(1));

        // It is now easy to add/remove nodes while iterating over the fixed size array.
        nodes[0].remove();
        // See how live collection is updated at the same time.
        msAssert.areEqual(2, paras.getCount());
    }


    /// <summary>
    /// WORDSNET-6825 Check removing the current node inside 'foreach' over NodeCollection.
    /// </summary>
    @Test
    public void testRemoveCurNodeInsideForeach() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.writeln("The first paragraph");
        builder.writeln("The second paragraph");
        builder.writeln("The third paragraph");

        for (Paragraph para : (Iterable<Paragraph>) builder.getDocument().getFirstSection().getBody().getParagraphs())
            if (StringUtil.contains(para.toString(SaveFormat.TEXT), "second", false))
                para.remove();

        msAssert.areEqual("The first paragraph\r\nThe third paragraph", msString.trim(builder.getDocument().toString(SaveFormat.TEXT)));
    }

    /// <summary>
    /// WORDSNET-6825 Check removing the previous node inside 'foreach' over NodeCollection.
    /// </summary>
    @Test
    public void testRemovePrevNodeInsideForeach() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.writeln("The first paragraph");
        builder.writeln("The second paragraph");
        builder.writeln("The third paragraph");

        for (Paragraph para : (Iterable<Paragraph>) builder.getDocument().getFirstSection().getBody().getParagraphs())
            if (StringUtil.contains(para.toString(SaveFormat.TEXT), "third", false))
                para.getPreviousSibling().remove();

        msAssert.areEqual("The first paragraph\r\nThe third paragraph", msString.trim(builder.getDocument().toString(SaveFormat.TEXT)));
    }

    /// <summary>
    /// WORDSNET-6825 Check removing the next node inside 'foreach' over NodeCollection.
    /// </summary>
    @Test
    public void testRemoveNextNodeInsideForeach() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.writeln("The first paragraph");
        builder.writeln("The second paragraph");
        builder.writeln("The third paragraph");

        for (Paragraph para : (Iterable<Paragraph>) builder.getDocument().getFirstSection().getBody().getParagraphs())
            if (StringUtil.contains(para.toString(SaveFormat.TEXT), "first", false))
                para.getNextSibling().remove();

        msAssert.areEqual("The first paragraph\r\nThe third paragraph", msString.trim(builder.getDocument().toString(SaveFormat.TEXT)));
    }

    /// <summary>
    /// WORDSNET-6825 Check direct access to methods of <see cref="NodeCollectionEnumerator{T}"/>.
    /// </summary>
    @Test
    public void testReadFromEnumerator() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.writeln("The first paragraph");
        builder.write("The second paragraph");

        Iterator<Paragraph> e = builder.getDocument().getFirstSection().getBody().getParagraphs().iterator();

        //In .Net MoveNext() moves cursor, Current - just returns current.
        //In Java MoveNext (hasNext()) - just returns boolean, Current (next()) - moves cursor and returns current.
        boolean isSuccess = e.hasNext();
        Node next = e.next();
        msAssert.areEqual(true, isSuccess);
        
        isSuccess = e.hasNext();
        next = e.next();
        msAssert.areEqual(true, isSuccess);
        
        isSuccess = e.hasNext();
        msAssert.areEqual(false, isSuccess);
    }

    /// <summary>
    /// WORDSNET-6825 Check reading beyond the end of an enumerator.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testReadBeyondEnumeratorEnd() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.writeln("The first paragraph");
        builder.write("The second paragraph");

        Iterator<Paragraph> e = builder.getDocument().getFirstSection().getBody().getParagraphs().iterator();

        //In java MoveNext (hasNext) doesn't move cursor. Current (next() in java) does move cursor.
        e.next();
        e.next();
        e.next();
        Object beyoundCollectionEnd = e.next();
        msAssert.isNotNull(beyoundCollectionEnd);
    }

    /// <summary>
    /// Create the following test document:
    /// 
    /// doc
    ///        section
    ///            body
    ///                para
    ///                    bookmark
    ///                    run "Body1"
    ///                    run "Body2"
    ///                para
    ///            header
    ///                para
    ///                    run    "Header1"
    /// </summary>
    private static Document createTestDoc() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect = doc.appendChild(new Section(doc));

        Body body = sect.appendChild(new Body(doc));
        Paragraph para1 = body.appendChild(new Paragraph(doc));
        para1.appendChild(new BookmarkStart(doc, "xxx"));
        para1.appendChild(new Run(doc, "Body1"));
        para1.appendChild(new Run(doc, "Body2"));

        body.appendChild(new Paragraph(doc));
        
        HeaderFooter header = sect.appendChild(new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY));
        Paragraph para3 = header.appendChild(new Paragraph(doc));
        para3.appendChild(new Run(doc, "Header1"));

        //Check that we've built what we want: basically some runs in different paragraphs and in different stories.
        final String RESULT = "Body1Body2\r\fHeader1\r";
        msAssert.areEqual(RESULT, doc.getText());

        return doc;
    }

    private static void checkTwoNodeEnumerators(boolean isDeep) throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Node sect1 = doc.appendChild(new Section(doc));
        Node sect2 = doc.appendChild(new Section(doc));
        Node sect3 = doc.appendChild(new Section(doc));

        NodeCollection nodes = doc.getChildNodes(NodeType.ANY, isDeep);
        Iterator<Node> e1 = nodes.iterator();
        Iterator<Node> e2 = nodes.iterator();

        e1.hasNext();
        msAssert.areEqual(sect1, e1.next());

        e2.hasNext();
        msAssert.areEqual(sect1, e2.next());

        e1.hasNext();
        msAssert.areEqual(sect2, e1.next());
        e1.hasNext();
        msAssert.areEqual(sect3, e1.next());

        // This "advances" the inner state inside the collection. Just to make sure the state of the collection
        // does not affect the work of iterators.
        msAssert.areEqual(sect1, nodes.get(0));

        e2.hasNext();
        msAssert.areEqual(sect2, e2.next());
        e2.hasNext();
        msAssert.areEqual(sect3, e2.next());
    }
}

