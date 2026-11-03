// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Document node specific tests.
/// </summary>
@Test
public class TestDocumentNode
{
    @Test
    public void testImportNode() throws Exception
    {
        Document doc1 = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect1 = doc1.appendChild(new Section(doc1));
        Body body1 = sect1.appendChild(new Body(doc1));

        Document doc2 = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect2 = (Section)doc2.importNode(sect1, true);

        //It is a clone, not the same section now.
        msAssert.isTrue(sect1 != sect2);

        //When the node is imported, it is not yet part of the tree, but the owner document is set.
        msAssert.areEqual(null, sect2.getParentNode());
        msAssert.areEqual(doc2, sect2.getDocument());

        //Check the child of the section
        Node body2 = sect2.getFirstChild();
        msAssert.isNotNull(body2);
        msAssert.isTrue(body1 != body2);
        msAssert.areEqual(doc2, body2.getDocument());

        //Now can successfully append the imported node.
        doc2.appendChild(sect2);

        msAssert.areEqual(doc2, sect2.getParentNode());
    }

    @Test
    public void testCloneDocument() throws Exception
    {
        Document doc1 = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect = doc1.appendChild(new Section(doc1));
        Body body = new Body(doc1);
        sect.appendChild(body);

        Document doc2 = (Document)doc1.deepClone(true);
        msAssert.areEqual(null, doc2.getParentNode());
        msAssert.areEqual(doc2, doc2.getDocument());
        msAssert.areEqual(NodeType.SECTION, doc2.getFirstChild().getNodeType());
    }
}

