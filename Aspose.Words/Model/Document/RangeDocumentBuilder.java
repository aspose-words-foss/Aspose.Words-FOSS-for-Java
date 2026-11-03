// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/09/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.HashSetGeneric;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Provides methods to build new documents based on a specified <see cref="Range" />s.
/// </summary>
class RangeDocumentBuilder
{
    /// <summary>
    /// Builds a new document that contains a specified range.
    /// Clones and inserts source range's nodes to a new document. Doesn’t change the source document.
    /// </summary>
    /// <returns>New document that contains a specified range.</returns>
    static Document build(Range range) throws Exception
    {
        Debug.assertTrue(range != null);

        Node node = range.getNode();
        Document doc;
        switch (node.getNodeType())
        {
            case NodeType.DOCUMENT:
                doc = (Document) node.deepClone(true);
                break;
            case NodeType.GLOSSARY_DOCUMENT:
                doc = createDocument((GlossaryDocument) node, true);
                break;
            case NodeType.ANY:
            case NodeType.SECTION:
            case NodeType.BODY:
            case NodeType.HEADER_FOOTER:
            case NodeType.TABLE:
            case NodeType.ROW:
            case NodeType.CELL:
            case NodeType.PARAGRAPH:
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.GROUP_SHAPE:
            case NodeType.SHAPE:
            case NodeType.COMMENT:
            case NodeType.FOOTNOTE:
            case NodeType.RUN:
            case NodeType.FIELD_START:
            case NodeType.FIELD_SEPARATOR:
            case NodeType.FIELD_END:
            case NodeType.FORM_FIELD:
            case NodeType.SPECIAL_CHAR:
            case NodeType.SMART_TAG:
            case NodeType.STRUCTURED_DOCUMENT_TAG:
            case NodeType.BUILDING_BLOCK:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.MOVE_FROM_RANGE_START:
            case NodeType.MOVE_FROM_RANGE_END:
            case NodeType.MOVE_TO_RANGE_START:
            case NodeType.MOVE_TO_RANGE_END:
            case NodeType.OFFICE_MATH:
            case NodeType.SUB_DOCUMENT:
            case NodeType.SYSTEM:
            case NodeType.NULL:
                doc = createDocumentFromNode(node);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return doc;
    }

    /// <summary>
    /// Creates a new document from a GlossaryDocument object.
    /// </summary>
    /// <param name="glossaryDocument">Source GlossaryDocument object.</param>
    /// <param name="importGlossaryChildren">true if you need to import children nodes of a GlossaryDocument object to a destination document; otherwise, false.</param>
    /// <returns></returns>
    private static Document createDocument(GlossaryDocument glossaryDocument, boolean importGlossaryChildren) throws Exception
    {
        Debug.assertTrue(glossaryDocument != null);
        Document doc = new Document(DocumentCtorMode.BLANK_DOCUMENT_NODE);
        doc.setGlossaryDocument((GlossaryDocument) glossaryDocument.deepClone(importGlossaryChildren));
        return doc;
    }

    /// <summary>
    /// Creates a new document from a Node object.
    /// </summary>
    /// <param name="node">Source node.</param>
    /// <returns>New document.</returns>
    private static Document createDocumentFromNode(Node node) throws Exception
    {
        Debug.assertTrue(node != null);
        Debug.assertTrue(node.getDocument() != null);

        Document doc;
        switch (node.getDocument().getNodeType())
        {
            case NodeType.DOCUMENT:
            {
                doc = (Document)node.getDocument().deepClone(false);
                importSourceNode(doc, node);
                break;
            }
            case NodeType.GLOSSARY_DOCUMENT:
            {
                doc = createDocument((GlossaryDocument)node.getDocument(), false);
                importSourceNode(doc.getGlossaryDocument(), node);
                break;
            }
            default:
            {
                Debug.assertTrue(false, "Unsupported document type!");
                doc = null;
                break;
            }
        }
        return doc;
    }

    /// <summary>
    /// Imports a source document node to a destination document.
    /// </summary>
    private static void importSourceNode(DocumentBase destDoc, Node sourceNode) throws Exception
    {
        Debug.assertTrue(destDoc != null);
        Debug.assertTrue(sourceNode != null);
        Debug.assertTrue(sourceNode.getDocument() != null);

        HashSetGeneric<Integer> referNodeTypes = new HashSetGeneric<Integer>();
        referNodeTypes.add(NodeType.HEADER_FOOTER);
        msStack<Node> srcNodesHierarchy = getNodesHierarchyStack(sourceNode, referNodeTypes);
        CompositeNode destParentNode = destDoc;
        while (srcNodesHierarchy.getCount() != 0)
        {
            Node srcNode = srcNodesHierarchy.pop();
            Node destNode = importNodeToDocument(destDoc, srcNode, srcNode == sourceNode, destParentNode);
            destParentNode = (destNode.isComposite()) ? (CompositeNode) destNode : null;
        }
    }

    /// <summary>
    /// Imports a source node to a destination document.
    /// </summary>
    /// <param name="destDoc">Destination document for importing node to.</param>
    /// <param name="srcNode">Source node to import.</param>
    /// <param name="initialSrcNode">true if a source node is an initial node for import; otherwise, false.</param>
    /// <param name="parentDestNode">Destination document parent node. A new node is added to children nodes of this node.</param>
    /// <returns>Imported node.</returns>
    private static Node importNodeToDocument(DocumentBase destDoc, Node srcNode, boolean initialSrcNode, CompositeNode parentDestNode) throws Exception
    {
        Debug.assertTrue(parentDestNode != null);
        Node destNode;
        if (srcNode.getNodeType() == NodeType.FORM_FIELD)
        {
            // Form field is a complex node. Import it in particular manner.
            destNode = importFormFieldNodeToDocument(destDoc, (FormField)srcNode, parentDestNode);
        }
        else
        {
            // Imports child nodes of the initial source node only.
            destNode = destDoc.importNode(srcNode, initialSrcNode, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            parentDestNode.appendChild(destNode);
        }

        return destNode;
    }

    /// <summary>
    /// Imports a source form field to a destination document.
    /// </summary>
    /// <param name="destDoc">Destination document.</param>
    /// <param name="srcFieldNode">Source form field node.</param>
    /// <param name="parentDestNode">Destination document parent node. A new node is added to children nodes of this node.</param>
    /// <returns></returns>
    private static Node importFormFieldNodeToDocument(DocumentBase destDoc, FormField srcFieldNode, CompositeNode parentDestNode) throws Exception
    {
        // FormField consists of FieldStart, FieldEnd, FieldSeparator, FormField, BoormarkStart, BoormarkEnd and Run nodes.
        Node srcStartNode = srcFieldNode.getField().getStart();
        BookmarkStart bookmarkStart = srcFieldNode.getBookmarkStart();
        BookmarkEnd bookmarkEnd = null;
        if (bookmarkStart != null)
        {
            Bookmark bookmark = new Bookmark(bookmarkStart);
            bookmarkEnd = bookmark.getBookmarkEnd();
        }

        boolean isBookmarkStartCopied = false;
        boolean isBookmarkEndCopied = false;
        Node destFieldNode = null;
        Node srcNode = srcStartNode;

        while ((srcNode != null) && (srcNode.getNodeType() != NodeType.FIELD_END))
        {
            Node destNode = destDoc.importNode(srcNode, false);
            parentDestNode.appendChild(destNode);

            if (srcNode == srcFieldNode)
                destFieldNode = destNode;

            isBookmarkStartCopied = isBookmarkStartCopied || (srcNode == bookmarkStart);
            isBookmarkEndCopied = isBookmarkEndCopied || (srcNode == bookmarkEnd);

            srcNode = srcNode.getNextSibling();
        }

        if (srcNode != null)
            parentDestNode.appendChild(destDoc.importNode(srcNode, true));

        if ((bookmarkStart != null) && !isBookmarkStartCopied)
        {
            Node destBookmarkStart = destDoc.importNode(bookmarkStart, false);
            // Mimic MS Word in placing bookmark nodes located before a field.
            if (parentDestNode.getParentNode().canInsert(destBookmarkStart))
                parentDestNode.insertPrevious(destBookmarkStart);
            else
                parentDestNode.insertBefore(destBookmarkStart, destFieldNode);
        }

        if ((bookmarkEnd != null) && !isBookmarkEndCopied)
            parentDestNode.appendChild(destDoc.importNode(bookmarkEnd, false));

        Debug.assertTrue(destFieldNode != null);
        return destFieldNode;
    }

    /// <summary>
    /// Gets source document nodes hierarchy for import. Skips all unnecessary nodes.
    /// Can process Document and GlossaryDocument child nodes.
    /// </summary>
    /// <remarks>
    /// To build a correct document we use a minimal nodes hierarchy.
    /// The minimal hierarchy consist of:
    /// [BuildingBlock]-Section-{Body|HeaderFooter}-FirstBlockLevelNode-[....]-SourceNode
    ///   FirstBlockLevelNode - first source node's ancestor of a block level type.
    /// All intermediate nodes are skipped between {Body|HeaderFooter} and FirstBlockLevelNode nodes.
    /// </remarks>
    private static msStack<Node> getNodesHierarchyStack(Node sourceNode, HashSetGeneric<Integer> referNodeTypes)
    {
        Debug.assertTrue(sourceNode != null);

        msStack<Node> nodesHierarchy = new msStack<Node>();
        Node node = sourceNode;
        referNodeTypes.add(NodeType.BODY);
        while ((node != null) && (node.getNodeType() != NodeType.DOCUMENT) && (node.getNodeType() != NodeType.GLOSSARY_DOCUMENT))
        {
            nodesHierarchy.push(node);
            if (node.getNodeLevel() == NodeLevel.BLOCK)
            {
                // Skip all intermediate nodes between the found block-level node and nodes with the specified NodeType.
                do
                    node = node.getParentNode();
                while ((node != null) && (!referNodeTypes.contains(node.getNodeType())));
            }
            else
            {
                node = node.getParentNode();
            }
        }

        return nodesHierarchy;
    }
}

