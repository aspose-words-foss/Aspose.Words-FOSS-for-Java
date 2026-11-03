// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Text.msStringBuilder;
import java.lang.Class;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Base class for all nodes of a Word document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A document is represented as a tree of nodes, similar to DOM or XmlDocument.</p>
/// <p>For more info see the Composite design pattern.</p>
/// <p>The <see cref="Node"/> class:</p>
/// <list type="bullet">
/// <item>Defines the child node interface.</item>
/// <item>Defines the interface for visiting nodes.</item>
/// <item>Provides default cloning capability.</item>
/// <item>Implements parent node and owner document mechanisms.</item>
/// <item>Implements access to sibling nodes.</item>
/// </list>
/// </remarks>
public abstract class Node implements Cloneable
{
    /// <summary>
    /// Ctor for creating special nodes that never need to know the document.
    /// </summary>
    protected Node()
    {
    }

    /// <summary>
    /// Ctor. This is the normal ctor to use.
    /// </summary>
    /// <param name="doc"></param>
    protected Node(DocumentBase doc)
    {
    	this();
	
        setDocument(doc);
    }


    /// <summary>
    /// Gets the type of this node.
    /// </summary>
    public abstract /*NodeType*/int getNodeType();

    /// <summary>
    /// Gets the immediate parent of this node.
    /// </summary>
    /// <remarks>
    /// <p>If a node has just been created and not yet added to the tree,
    /// or if it has been removed from the tree, the parent is <c>null</c>.</p>
    /// </remarks>
    public CompositeNode getParentNode()
    {
        //Parent can be null for the document node for example.
        if (mParentNode == null)
            return null;

        // When a node is part of the tree, it knows its parent node.
        // But a node can exist outside of a tree as well, in this case the parent is null.
        // Even if the node has no parent, it always belongs to a document (knows its owner document).
        // This is implemented using  the NullNode approach stolen from XmlDocument.

        //If the parent is the special null node, return simple null to the user.
        //As a performance optimization I don't use Document.NullNode anymore.
        if (mParentNode.getNodeType() == NodeType.NULL)
            return null;

        return (CompositeNode)mParentNode;
    }

    /// <summary>
    /// Returns first non-Markup parent of this node.
    /// </summary>
    CompositeNode getFirstNonMarkupParentNode()
    {
        // Recurse until find a non markup.
        return NodeUtil.isMarkupNode(getParentNode()) ? getParentNode().getFirstNonMarkupParentNode() : getParentNode();
    }

    /// <summary>
    /// Returns first non-Markup parent and non-Office Math parent of this node.
    /// </summary>
    /// <remarks>
    /// DV Initial name I chose was FirstNonMarkupNonOfficeMathParentNode, but then I thought that we might potentially have other node types
    /// that should be skipped during traverse operations.
    /// </remarks>
    CompositeNode getFirstMeaningfulParentNode()
    {
        // Recurse until find a meaningful node.
        return NodeUtil.isMarkupNode(getParentNode()) || NodeUtil.isOfficeMath(getParentNode()) ?
            getParentNode().getFirstMeaningfulParentNode() :
            getParentNode();
    }

    /// <summary>
    /// Gets the document to which this node belongs.
    /// </summary>
    /// <remarks>
    /// <p>The node always belongs to a document even if it has just been created
    /// and not yet added to the tree, or if it has been removed from the tree.</p>
    /// </remarks>
    public /*virtual*/ DocumentBase getDocument()
    {
        return (mParentNode != null)
            ? mParentNode.getDocument()
            : null;
    }

    /// <summary>
    /// Returns the main (not glossary) owner document.
    /// </summary>
    Document fetchDocument()
    {
        DocumentBase doc = getDocument();
        if (doc instanceof Document)
            return (Document)doc;

        throw new IllegalStateException("This operation requires the node to be inside the main document.");
    }

    /// <summary>
    /// Returns the main (not glossary) owner document.
    /// in case of GlossaryDocument returns its main document.
    /// </summary>
    Document fetchDocumentOrGlossaryMain()
    {
        DocumentBase doc = getDocument();
        if (doc instanceof Document)
            return (Document) doc;

        return ((GlossaryDocument)doc).getMainDocument();
    }

    /// <summary>
    /// Gets the node immediately preceding this node.
    /// </summary>
    /// <remarks>
    /// If there is no preceding node, a <c>null</c> is returned.
    /// </remarks>
    public Node getPreviousSibling() { return getPrevNode(); }

    /// <summary>
    /// Gets the non-cross structure annotation node immediately preceding this node.
    /// </summary>
    /// <remarks>
    /// If there is no such node, a <c>null</c> is returned.
    /// </remarks>
    Node getPreviousNonAnnotationSibling()
    {
        Node node = this;
        do
        {
            node = node.getPreviousSibling();
        }
        while ((node != null) && NodeUtil.isCrossStructureAnnotation(node));

        return node;
    }

    /// <summary>
    /// Gets the last non-markup node <see cref="NodeUtil.IsMarkupNode(Words.NodeType)"/>
    /// which goes before the current node in a depth-first traverse order. Can go up the tree.
    /// </summary>
    /// <remarks>
    /// The current node and its descendants are not considered.
    /// The siblings before the current node and their descendants are searched for non-markups.
    /// If nothing found among siblings and below, it is assumed that the current node may be already deep inside a tree consisting of markup composites.
    /// So the search continues from the current node's parent while it remains a markup node.
    ///
    /// The search is limited in two ways:
    /// It does not go inside the current node. So if it contains non-markup nodes, they will not be returned.
    /// It goes to the parent node only if the parent is a markup.
    /// So if the current node is inside a non-markup node, it is similar to PreviousSibling, but it looks inside previous siblings if they are markups.
    /// </remarks>
    Node getPreviousNonMarkupNodeLimited()
    {
        Node previousNonMarkup = NodeUtil.getLastNonMarkupNodeInDepthFirstTraverse(getPreviousSibling(), false);

        if (previousNonMarkup == null)
        {
            // No non-markups nodes found among siblings before the current node and below.
            if (NodeUtil.isMarkupNode(getParentNode()))
                previousNonMarkup = getParentNode().getPreviousNonMarkupNodeLimited();
        }

        return previousNonMarkup;
    }

    /// <summary>
    /// Gets the next non-markup node <see cref="NodeUtil.IsMarkupNode(Words.NodeType)"/>
    /// which goes after the current node in a depth-first traverse order. Can go up the tree.
    /// </summary>
    /// <remarks>
    /// The current node and its descendants are not considered.
    /// The siblings after the current node and their descendants are searched for non-markups.
    /// If nothing found among siblings and below, it is assumed that the current node may be already deep inside a tree consisting of markup composites.
    /// So the search continues from the current node's parent while it remains a markup node.
    ///
    /// The search is limited in two ways:
    /// It does not go inside the current node. So if it contains non-markup nodes, they will not be returned.
    /// It goes to the parent node only if the parent is a markup.
    /// So if the current node is inside a non-markup node, it is similar to NextSibling, but it looks inside if next siblings if they are markups.
    /// </remarks>
    Node getNextNonMarkupNodeLimited()
    {
        Node nextNonMarkup = NodeUtil.getFirstNonMarkupNodeInDepthFirstTraverse(getNextSibling(), false);

        if(nextNonMarkup == null)
        {
            // No non-markups nodes found among siblings after the current node and below.
            if (NodeUtil.isMarkupNode(getParentNode()))
                nextNonMarkup = getParentNode().getNextNonMarkupNodeLimited();
        }

        return nextNonMarkup;
    }

    /// <summary>
    /// Gets the last non-markup composite node (<see cref="NodeUtil.IsMarkupNode(Words.NodeType)"/>)
    /// which goes before the current node in a depth-first traverse order. Can go up the tree.
    /// </summary>
    /// <remarks>
    /// The current node and its descendants are not considered.
    /// The siblings before the current node and their descendants are searched for non-markups and composite.
    /// If nothing found among siblings and below, it is assumed that the current node may be already deep
    /// inside a tree consisting of markup composites.
    /// So the search continues from the current node's parent while it remains a markup node.
    ///
    /// The search is limited in two ways:
    /// It does not go inside the current node. So if it contains non-markup nodes, they will not be returned.
    /// It goes to the parent node only if the parent is a markup.
    /// So if the current node is inside a non-markup node, it is similar to PreviousSibling with additional checking
    /// for composite, but it looks inside previous siblings if they are markups.
    /// </remarks>
    CompositeNode getPreviousNonMarkupCompositeLimited()
    {
        Node node = this;
        do
        {
            node = node.getPreviousNonMarkupNodeLimited();
        }
        while ((node != null) && !node.isComposite());

        return (CompositeNode)node;
    }

    /// <summary>
    /// Gets the next non-markup composite node (<see cref="NodeUtil.IsMarkupNode(Words.NodeType)"/>)
    /// which goes after the current node in a depth-first traverse order. Can go up the tree.
    /// </summary>
    /// <remarks>
    /// The current node and its descendants are not considered.
    /// The siblings after the current node and their descendants are searched for non-markups and composite.
    /// If nothing found among siblings and below, it is assumed that the current node may be already deep
    /// inside a tree consisting of markup composites.
    /// So the search continues from the current node's parent while it remains a markup node.
    ///
    /// The search is limited in two ways:
    /// It does not go inside the current node. So if it contains non-markup nodes, they will not be returned.
    /// It goes to the parent node only if the parent is a markup.
    /// So if the current node is inside a non-markup node, it is similar to NextSibling with additional checking
    /// for composite, but it looks inside if next siblings if they are markups.
    /// </remarks>
    CompositeNode getNextNonMarkupCompositeLimited()
    {
        Node node = this;
        do
        {
            node = node.getNextNonMarkupNodeLimited();
        }
        while ((node != null) && !node.isComposite());

        return (CompositeNode)node;
    }

    /// <summary>
    /// Gets the node immediately following this node.
    /// </summary>
    /// <remarks>
    /// If there is no next node, a <c>null</c> is returned.
    /// </remarks>
    public Node getNextSibling() { return getNextNode(); }

    /// <summary>
    /// Gets the non-cross structure annotation node immediately following this node.
    /// </summary>
    /// <remarks>
    /// If there is no such node, a <c>null</c> is returned.
    /// </remarks>
    Node getNextNonAnnotationSibling()
    {
        Node node = this;
        do
        {
            node = node.getNextSibling();
        }
        while ((node != null) && NodeUtil.isCrossStructureAnnotation(node));

        return node;
    }

    /// <summary>
    /// Returns <c>true</c> if this node can contain other nodes.
    /// </summary>
    /// <value>This method returns <c>false</c> as <see cref="Node"/> cannot have child nodes.</value>
    public /*virtual*/ boolean isComposite() { return false; }

    /// <summary>
    /// Returns a <see cref="Aspose.Words.Range"/> object that represents the portion of a document that is contained in this node.
    /// </summary>
    public Range getRange() { return new Range(this); }

    boolean isLastChild() { return (getParentNode() == null) || (this == getParentNode().getLastChild()); }

    boolean isFirstChild() { return (getParentNode() == null) || (this == getParentNode().getFirstChild()); }

    boolean isFirstNonZeroLengthChild()
    {
        Node node = getPreviousSibling();
        while (node != null)
        {
            if (!NodeUtil.isZln(node))
                return false;

            node = node.getPreviousSibling();
        }

        return true;
    }

    boolean isLastNonZeroLengthChild()
    {
        Node node = getNextSibling();
        while (node != null)
        {
            if (!NodeUtil.isZln(node))
                return false;

            node = node.getNextSibling();
        }

        return true;
    }

    /// <summary>
    /// Next node in the linked chain of nodes. The same as the <see cref="NextSibling"/> but has setter.
    /// </summary>
    
    Node getNextNode() { return mNextNode; }
    
    void setNextNode(Node value) { mNextNode = value; }

    /// <summary>
    /// Previous node in the linked chain of nodes. The same as the <see cref="PreviousSibling"/> but has setter.
    /// </summary>
    
    Node getPrevNode() { return mPrevNode; }
    
    void setPrevNode(Node value) { mPrevNode = value; }

    /// <summary>
    /// Returns the next node or parent if it does not exist.
    /// </summary>
    Node getNextOrParent() { return getNextNode() != null ? getNextNode() : getParentNode(); }

    /// <summary>
    /// Gets a level of the node (inline, block etc).
    /// </summary>
    /*NodeLevel*/int getNodeLevel()
    {
        switch (getNodeType())
        {
            case NodeType.DOCUMENT:
                return NodeLevel.DOCUMENT;
            case NodeType.SECTION:
                return NodeLevel.SECTION;
            case NodeType.BODY:
            case NodeType.HEADER_FOOTER:
                return NodeLevel.SECTION_STORY;
            default:
                boolean isInlineLevel = NodeUtil.isInlineLevelNode(this);
                boolean isBlockLevel = NodeUtil.isBlockLevelNode(this);
                boolean isCellLevel = NodeUtil.isCellLevelNode(this);
                boolean isRowLevel = NodeUtil.isRowLevelNode(this);
                Node parent = getParentNode();
                /*NodeLevel*/int sdtParentLevel = (parent != null) && (parent.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                    ? getParentNode().getNodeLevel()
                    : NodeLevel.OTHER;
                /*NodeType*/int parentNodeType = (parent != null) ? parent.getNodeType() : NodeType.NULL;

                if (isRowLevel &&
                    (
                        !(isInlineLevel || isBlockLevel || isCellLevel) ||
                        (sdtParentLevel == NodeLevel.ROW) ||
                        (parentNodeType == NodeType.TABLE)
                    ))
                    return NodeLevel.ROW;

                if (isCellLevel &&
                    (
                        !(isInlineLevel || isBlockLevel) ||
                        (sdtParentLevel == NodeLevel.CELL) ||
                        (parentNodeType == NodeType.ROW)
                    ))
                    return NodeLevel.CELL;

                if (isBlockLevel &&
                    (
                        !isInlineLevel ||
                        (sdtParentLevel == NodeLevel.BLOCK) ||
                        ((parent != null) && (parent.getNodeLevel() != NodeLevel.BLOCK) &&
                            (sdtParentLevel != NodeLevel.INLINE) && (parentNodeType != NodeType.SMART_TAG) &&
                            (parentNodeType != NodeType.OFFICE_MATH))
                    ))
                    return NodeLevel.BLOCK;

                if (isInlineLevel)
                    return NodeLevel.INLINE;

                return NodeLevel.OTHER;
        }
    }

    /// <summary>
    /// Returns the start linear position of this node.
    /// This is not public because it is global in the tree and therefore not really
    /// meaningful to the user. For example, position in body can depend on whether
    /// header node is before the body or node or after. So its internal.
    /// </summary>
    int getStart()
    {
        if (getParentNode() == null)
            return 0;

        int localStart = 0;
        for (Node node = getParentNode().getFirstChild(); node != this; node = node.getNextSibling())
            localStart += node.getTextLength();

        return getParentNode().getStart() + localStart;
    }

    /// <summary>
    /// Returns the end linear position of this node.
    /// </summary>
    int getEnd()
    {
        return getStart() + getTextLength();
    }

    /// <summary>
    /// Returns <c>true</c> if the position is greater than equal start and less than end.
    /// </summary>
    boolean contains(int position)
    {
        int start = getStart();
        int end = start + getTextLength();
        return ((position >= start) && (position < end));
    }

    /// <summary>
    /// Returns this node if it contains the position, otherwise <c>null</c>.
    /// </summary>
    /*virtual*/ Node getNodeFromPos(int position) throws Exception
    {
        return (contains(position)) ? this : null;
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Clone(bool)"]/*'/>
    /// <dev>
    /// You should use the <see cref="Clone(bool, INodeCloningListener)"/> overload when
    /// the node cloning is a part of another (usually composite) node cloning process.
    /// And you should pass down the <see cref="INodeCloningListener"/> parameter in this case.
    /// </dev>
    public Node deepClone(boolean isCloneChildren)
    {
        return deepClone(isCloneChildren, new CommentIdGenerator(this));
    }

    /*virtual*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        // This creates a node of the correct class and bitwise copies all fields.
        Node lhs = (Node)memberwiseClone();

        // Make the node parentless, but belonging to the document by referencing the document's special null node.
        // The if condition is required for a Document object to work as it has null parent.
        if (mParentNode != null)
            lhs.mParentNode = getDocument().getNullNode();

        //And will have no siblings.
        lhs.mNextNode = null;
        lhs.mPrevNode = null;

        if (cloningListener != null)
            cloningListener.notifyNodeCloned(this, lhs);

        return lhs;
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    public abstract boolean accept(DocumentVisitor visitor) throws Exception;

    /// <summary>
    /// A utility function that all derived classes can call to convert a visitor action
    /// into a boolean value when visiting the end of the node.
    /// </summary>
    protected static boolean visitorActionToBool(/*VisitorAction*/int action)
    {
        switch (action)
        {
            case VisitorAction.CONTINUE:
            case VisitorAction.SKIP_THIS_NODE:
                return true;
            case VisitorAction.STOP:
                return false;
            default:
                throw new IllegalStateException("Unknown visitor action.");
        }
    }

    /// <summary>
    /// Gets the text of this node and of all its children.
    /// </summary>
    ///<remarks>
    ///<p>The returned string includes all control and special characters as described in <see cref="ControlChar"/>.</p>
    ///</remarks>
    public /*virtual*/ String getText()
    {
        return "";
    }

    /// <summary>
    /// Puts the text of this node and of all its children to the specified string builder.
    /// </summary>
    /*virtual*/ void getTextToBuilder(StringBuilder builder)
    {
        msStringBuilder.append(builder, getText());
    }

    /// <summary>
    /// Gets the first ancestor of the specified object type.
    /// </summary>
    /// <param name="ancestorType">The object type of the ancestor to retrieve.</param>
    /// <returns>The ancestor of the specified type or <c>null</c> if no ancestor of this type was found.</returns>
    /// <remarks>
    /// <p>The ancestor type matches if it is equal to <paramref name="ancestorType"/> or derived from <paramref name="ancestorType"/>.</p>
    /// </remarks>
    public CompositeNode getAncestor(Class ancestorType)
    {
        CompositeNode curParent = getParentNode();
        while (curParent != null)
        {
            if (ancestorType.isInstance(curParent))
                return curParent;

            curParent = curParent.getParentNode();
        }

        return null;
    }

    IStory getAncestorIStory()
    {
        Node curParent = getParentNode();

        while (curParent != null)
        {
            IStory parent = ms.as(curParent, IStory.class);
            if (parent != null)
                return parent;

            curParent = curParent.getParentNode();
        }

        return null;
    }

    /// <summary>
    /// Gets the first ancestor of the specified <see cref="Aspose.Words.NodeType"/>.
    /// </summary>
    /// <param name="ancestorType">The node type of the ancestor to retrieve.</param>
    /// <returns>The ancestor of the specified type or <c>null</c> if no ancestor of this type was found.</returns>
    public CompositeNode getAncestor(/*NodeType*/int ancestorType)
    {
        CompositeNode curParent = getParentNode();
        while (curParent != null)
        {
            if (curParent.getNodeType() == ancestorType)
                return curParent;

            curParent = curParent.getParentNode();
        }
        return null;
    }

    /// <summary>
    /// Removes itself from the parent.
    /// </summary>
    public void remove()
    {
        if (getParentNode() == null)
            throw new IllegalStateException("Cannot remove because there is no parent.");
        getParentNode().removeChild(this);
    }

    /// <summary>
    /// Setting parent node to null actually sets it to reference the <c>null</c>
    /// node of the document so we still know the document.
    /// </summary>
    void setParent(Node parentNode)
    {
        mParentNode = (parentNode != null) ? parentNode : getDocument().getNullNode();
    }

    /// <summary>
    /// Makes the node belong to a different document and have no parent. Use with care.
    /// </summary>
    void setDocument(DocumentBase doc)
    {
        // RK I guess doc can be null when creating a document node itself.
        mParentNode = (doc != null) ? doc.getNullNode() : null;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified node is an ancestor of this node.
    /// </summary>
    boolean isAncestorNode(Node node)
    {
        if (node == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "node");

        Node n = getParentNode();
        while (n != null && n != this)
        {
            if (n == node)
                return true;
            n = n.getParentNode();
        }
        return false;
    }

    /// <summary>
    /// Returns top most ancestor of the node.
    /// </summary>
    Node getTopmostAncestor()
    {
        // Return Document if node was deleted.
        if (getParentNode() == null)
            return getDocument();

        Node node = this;
        while (node.getParentNode() != null)
            node = node.getParentNode();

        return node;
    }

    /// <summary>
    /// Gets the length of the text of this node.
    /// Override in descendant classes if you can make it faster than using GetText().Length.
    /// </summary>
    /*virtual*/ int getTextLength()
    {
        return getText().length();
    }

    /// <summary>
    /// Gets next node according to the pre-order tree traversal algorithm.
    /// </summary>
    /// <param name="rootNode">The top node (limit) of traversal.</param>
    /// <returns>Next node in pre-order order. Null if reached the <paramref name="rootNode"/>.</returns>
    public Node nextPreOrder(Node rootNode)
    {
        //For preorder walking, first try its child
        Node retNode = (isComposite()) ? ((CompositeNode)this).getFirstChild() : null;

        if (retNode == null)
        {
            //If no child, the next node forward will the be the NextSibling of this node
            //or of the first ancestor which has NextSibling so, first loop to find out such
            //an ancestor until no more ancestor or the ancestor is the rootNode.
            retNode = this;
            while ((retNode != null) &&
                (retNode != rootNode) &&
                (retNode.getNextSibling() == null))
            {
                retNode = retNode.getParentNode();
            }

            //Then if such an ancestor exists, set the retNode to its NextSibling.
            if ((retNode != null) && (retNode != rootNode))
                retNode = retNode.getNextSibling();
        }

        //If reached the rootNode, consider having walked through the whole tree.
        if (retNode == rootNode)
            retNode = null;

        return retNode;
    }

    /// <summary>
    /// Gets the previous node according to the pre-order tree traversal algorithm.
    /// </summary>
    /// <param name="rootNode">The top node (limit) of traversal.</param>
    /// <returns>Previous node in pre-order order. Null if reached the <paramref name="rootNode"/>.</returns>
    public Node previousPreOrder(Node rootNode)
    {
        //For preorder walking, the previous node will be the right-most node
        //in the tree of PreviousSibling of the curNode.

        Node retNode = getPreviousSibling();
        //So if the previous sibling is not null, go through the tree down to find the right-most node.
        while (retNode != null)
        {
            Node lastChild = (retNode.isComposite()) ? ((CompositeNode)retNode).getLastChild() : null;

            if (lastChild == null)
                break;

            retNode = lastChild;
        }

        //If no previous sibling, the previous node will be the curNode's parentNode
        if (retNode == null)
            retNode = getParentNode();

        //If the final retNode is rootNode, consider having walked through the tree.
        if (retNode == rootNode)
            retNode = null;

        return retNode;
    }

    /// <summary>
    /// Returns first sibling node of the specified type after this node.
    /// If there is no such node returns <c>null</c>.
    /// </summary>
    Node nextSiblingOfType(/*NodeType*/int nodeType)
    {
        Node node = getNextSibling();
        while (null != node && nodeType != node.getNodeType())
            node = node.getNextSibling();
        return node;
    }

    /// <summary>
    /// Returns first sibling node of the specified type before this node.
    /// If there is no such node returns <c>null</c>.
    /// </summary>
    Node previousSiblingOfType(/*NodeType*/int nodeType)
    {
        Node node = getPreviousSibling();
        while (null != node && nodeType != node.getNodeType())
            node = node.getPreviousSibling();
        return node;
    }

    /// <summary>
    /// Gets the next node specified type according to the pre-order tree traversal algorithm.
    /// </summary>
    /// <param name="rootNode">The top node (limit) of traversal.</param>
    /// <param name="nodeType">Type of node to search.</param>
    /// <returns>Next node in pre-order order. Null if reached the rootNode.</returns>
    Node nextPreOrderOfType(Node rootNode, /*NodeType*/int nodeType)
    {
        Node nextNode = nextPreOrder(rootNode);
        while (nextNode != null)
        {
            if (nextNode.getNodeType() == nodeType)
                return nextNode;

            nextNode = nextNode.nextPreOrder(rootNode);
        }

        return null;
    }

    /// <summary>
    /// Gets the previous node specified type according to the pre-order tree traversal algorithm.
    /// </summary>
    /// <param name="rootNode">The top node (limit) of traversal.</param>
    /// <param name="nodeType">Type of node to search.</param>
    /// <returns>Previous node in pre-order order. Null if reached the <paramref name="rootNode"/>.</returns>
    Node previousPreOrderOfType(Node rootNode, /*NodeType*/int nodeType)
    {
        Node node = previousPreOrder(rootNode);
        while ((node != null) && (node.getNodeType() != nodeType))
        {
            node = node.previousPreOrder(rootNode);
        }
        return node;
    }

    /// <summary>
    /// Returns the next or previous sibling of this node.
    /// </summary>
    Node getNearestSibling(boolean isNext)
    {
        return isNext ? getNextSibling() : getPreviousSibling();
    }

    /// <summary>
    /// Inserts the specified node immediately after this node.
    /// </summary>
    /// <param name="node">The <see cref="Node"/> to insert.</param>
    Node insertNext(Node node)
    {
        return getParentNode().insertAfter(node, this);
    }

    /// <summary>
    /// Inserts nodes starting from the start node up to, but not including the end node after this node.
    /// </summary>
    /// <param name="start">Node to start moving from.</param>
    /// <param name="end">Can be <c>null</c> to indicate move all from start to the end.</param>
    void insertNext(Node start, Node end)
    {
        getParentNode().insertAfter(start, end, this);
    }

    /// <summary>
    /// Inserts the specified node immediately before this node.
    /// </summary>
    /// <param name="node">The <see cref="Node"/> to insert.</param>
    Node insertPrevious(Node node)
    {
        return getParentNode().insertBefore(node, this);
    }

    /// <summary>
    /// Inserts nodes starting from the start node up to, but not including the end node before this node.
    /// </summary>
    /// <param name="start">Node to start moving from.</param>
    /// <param name="end">Can be <c>null</c> to indicate move all from start to the end.</param>
    void insertPrevious(Node start, Node end)
    {
        getParentNode().insertBefore(start, end, this);
    }

    /// <summary>
    /// Exports the content of the node into a string in the specified format.
    /// </summary>
    /// <returns>The content of the node in the specified format.</returns>
    public String toString(/*SaveFormat*/int saveFormat) throws Exception
    {
        IDocumentFragmentWriter fragmentWriter = FragmentWriterFactory.createFragmentWriter(saveFormat);
        return fragmentWriter.saveToString(this);
    }

    /// <summary>
    /// Exports the content of the node into a string using the specified save options.
    /// </summary>
    /// <param name="saveOptions">Specifies the options that control how the node is saved.</param>
    /// <returns>The content of the node in the specified format.</returns>
    public String toString(SaveOptions saveOptions) throws Exception
    {
        IDocumentFragmentWriter fragmentWriter = FragmentWriterFactory.createFragmentWriter(saveOptions);
        return fragmentWriter.saveToString(this);
    }

    private Node mParentNode;
    private Node mNextNode;
    private Node mPrevNode;

    /// <summary>
    /// Returns ancestor of the node of the specified type or story ancestor.
    /// This method doesn't ascend story masters.
    /// </summary>
    /// <param name="nodeType">Ancestor node type. If <see cref="Words.NodeType.Any"/> then stops at story node,
    /// <seealso cref="NodeUtil.IsStoryNodeType"/>.</param>
    /// <returns>Ancestor node of the specified type, or story ancestor (if requested), or Null if not found.</returns>
    Node getStoryAncestor(/*NodeType*/int nodeType)
    {
        for (Node node = getParentNode(); null != node; node = node.getParentNode())
        {
            if (node.getNodeType() == nodeType)
                return node;
            if (NodeUtil.isStoryNodeType(node))
            {
                if (nodeType != NodeType.ANY)
                    break;
                return node;
            }
        }
        return null;
    }

    /// <summary>
    /// Returns a <see cref="DocumentPosition"/> that is based on the previous sibling of this node or,
    /// if the sibling is null, on the parent of this node.
    /// </summary>
    /// <returns></returns>
    DocumentPosition getBeforeDocumentPosition()
    {
        Node previousSibling = getPreviousSibling();

        return (previousSibling != null) ?
            DocumentPosition.createPositionAfter(previousSibling) :
            DocumentPosition.createPositionBefore(getParentNode());
    }

    /// <summary>
    /// Returns a <see cref="DocumentPosition"/> that is based on the next sibling of this node or,
    /// if the sibling is null, on the parent of this node.
    /// </summary>
    /// <returns></returns>
    DocumentPosition getAfterDocumentPosition()
    {
        Node nextSibling = getNextSibling();

        return (nextSibling != null) ?
            DocumentPosition.createPositionBefore(nextSibling) :
            DocumentPosition.createPositionAfter(getParentNode());
    }

    /// <summary>
    /// Calculates if this node is above given node in a document tree flow.
    /// </summary>
    /// <remarks>
    /// The result is computed by finding a common ancestor and looking whose ancestor comes first.
    /// </remarks>
    boolean isAbove(Node node)
    {
        return nodeAIsAboveNodeB(this, node);
    }

    /// <summary>
    /// Determines whether this node is removed from a <see cref="Document"/>
    /// </summary>
    boolean isRemoved() { return getAncestor(NodeType.DOCUMENT) == null; }

    /// <summary>
    /// Gets a common ancestor of the specified nodes.
    /// </summary>
    static Node getCommonAncestor(Node nodeA, Node nodeB)
    {
        if (nodeA == nodeB)
            return nodeA;
        if ((nodeA == null) || (nodeB == null))
            return null;

        msStack<Node> aAncestors = getAncestors(nodeA);
        msStack<Node> bAncestors = getAncestors(nodeB);

        return findTheLastMatchingObject(aAncestors, bAncestors);
    }

    /// <summary>
    /// Calculates if the first node is above the second node in a document tree flow.
    /// </summary>
    /// <remarks>
    /// The result is computed by finding a common ancestor and looking whose ancestor comes first.
    /// </remarks>
    static boolean nodeAIsAboveNodeB(Node a, Node b)
    {
        if ((a == null) || (b == null))
            return false;

        msStack<Node> aAncestors = getAncestors(a);
        msStack<Node> bAncestors = getAncestors(b);

        Node lastCommonAncestor = findTheLastMatchingObject(aAncestors, bAncestors);

        if (lastCommonAncestor == null)
        {
            // The nodes are from different trees.
            return false;
        }
        else if ((aAncestors.getCount() == 0) || (bAncestors.getCount() == 0))
        {
            // a is an ancestor of b or vice versa.
            boolean aIsAboveB = (bAncestors.getCount() > 0);
            return aIsAboveB;
        }
        else
        {
            // Whose ancestor is the first in the last common ancestor's children, is above.
            Object aAncestor = aAncestors.peek();
            Object bAncestor = bAncestors.peek();

            for (Node possibleAncestor : (Iterable<Node>) ((CompositeNode)lastCommonAncestor).getChildNodes(NodeType.ANY, false))
            {
                if (possibleAncestor == aAncestor)
                    return true;
                if (possibleAncestor == bAncestor)
                    return false;
            }
            // Actually, we should never get here, but the compiler does not know about it.
            return false;
        }
    }

    /// <summary>
    /// Gets a stack of ancestors of the given node with the root on top.
    /// </summary>
    private static msStack<Node> getAncestors(Node node)
    {
        msStack<Node> ancestorStack = new msStack<Node>();

        Node currentNode = node;
        while (currentNode != null)
        {
            ancestorStack.push(currentNode);
            currentNode = currentNode.getParentNode();
        }
        return ancestorStack;
    }

    /// <summary>
    /// Going from top of the stacks, finds the last object that is the same in two stacks.
    /// </summary>
    /// <param name="a"></param>
    /// <param name="b"></param>
    /// <returns></returns>
    private static Node findTheLastMatchingObject(msStack<Node> a, msStack<Node> b)
    {
        Node lastMatchingObject = null;
        while (a.peek() == b.peek())
        {
            lastMatchingObject = a.pop();
            b.pop();

            if ((a.getCount() == 0) || (b.getCount() == 0))
                break;
        }
        return lastMatchingObject;
    }

    /// <summary>
    /// A utility method that converts a node type enum value into a user friendly string.
    /// </summary>
    public static String nodeTypeToString(/*NodeType*/int nodeType)
    {
        switch (nodeType)
        {
            case NodeType.ANY: return "Any";
            case NodeType.DOCUMENT: return "Document";
            case NodeType.SECTION: return "Section";
            case NodeType.BODY: return "Body";
            case NodeType.HEADER_FOOTER: return "HeaderFooter";
            case NodeType.TABLE: return "Table";
            case NodeType.ROW: return "Row";
            case NodeType.CELL: return "Cell";
            case NodeType.PARAGRAPH: return "Paragraph";
            case NodeType.BOOKMARK_START: return "BookmarkStart";
            case NodeType.BOOKMARK_END: return "BookmarkEnd";
            case NodeType.GROUP_SHAPE: return "GroupShape";
            case NodeType.SHAPE: return "Shape";
            case NodeType.COMMENT: return "Comment";
            case NodeType.FOOTNOTE: return "Footnote";
            case NodeType.RUN: return "Run";
            case NodeType.FIELD_START: return "FieldStart";
            case NodeType.FIELD_SEPARATOR: return "FieldSeparator";
            case NodeType.FIELD_END: return "FieldEnd";
            case NodeType.FORM_FIELD: return "FormField";
            case NodeType.SPECIAL_CHAR: return "SpecialChar";
            case NodeType.SMART_TAG: return "SmartTag";
            case NodeType.STRUCTURED_DOCUMENT_TAG: return "StructuredDocumentTag";
            case NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START: return "StructuredDocumentTagRangeStart";
            case NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END: return "StructuredDocumentTagRangeEnd";
            case NodeType.GLOSSARY_DOCUMENT: return "GlossaryDocument";
            case NodeType.BUILDING_BLOCK: return "BuildingBlock";
            case NodeType.COMMENT_RANGE_START: return "CommentRangeStart";
            case NodeType.COMMENT_RANGE_END: return "CommentRangeEnd";
            case NodeType.MOVE_FROM_RANGE_START: return "MoveFromRangeStart";
            case NodeType.MOVE_FROM_RANGE_END: return "MoveFromRangeEnd";
            case NodeType.MOVE_TO_RANGE_START: return "MoveToRangeStart";
            case NodeType.MOVE_TO_RANGE_END: return "MoveToRangeEnd";
            case NodeType.EDITABLE_RANGE_START: return "EditableRangeStart";
            case NodeType.EDITABLE_RANGE_END: return "EditableRangeEnd";
            case NodeType.OFFICE_MATH: return "OfficeMath";
            case NodeType.SUB_DOCUMENT: return "SubDocument";
            case NodeType.SYSTEM: return "System";
            case NodeType.NULL: return "Null";
            default: return "Unknown node type.";
        }
    }

    /// <summary>
    /// Specifies custom node identifier.
    /// </summary>
    /// <remarks>
    /// <p>Default is zero.</p>
    /// <p>This identifier can be set and used arbitrarily. For example, as a key to get external data.</p>
    /// <p>Important note, specified value is not saved to an output file and exists only during the node lifetime.</p>
    /// </remarks>
    public int getCustomNodeId() { return mCustomNodeId; }; public void setCustomNodeId(int value) { mCustomNodeId = value; };

    private int mCustomNodeId;

    /// <summary>
    /// Returns Node Id, used for debugging and testing.
    /// </summary>
    String getNodeId()
    {
        StringBuilder sb = new StringBuilder();

        for (Node node = this; node != null && node.getParentNode() != null; node = node.getParentNode())
        {
            int index = 0;
            for (Node n = node.getParentNode().getFirstChild(); n != null && n != node; n = n.getNextSibling())
                index++;
            msStringBuilder.appendFormat(sb, "{0}{1}", (node == this ? "" : "."), index);
        }

        return msString.trim(sb.toString());
    }

    public /*override*/ String toString()
    {
        // WORDSJAVA-2613 return implementation of toString method
        return msString.trim(msString.format("{0} {1}", nodeTypeToString(getNodeType()), getNodeId()));
    }

    /// <summary>
    /// Returns first sibling node of the specified types after this node.
    /// If there is no such node returns <c>null</c>.
    /// </summary>
    Node nextSiblingOfTypes(/*params*/ /*NodeType*/int... nodeTypes)
    {
        Debug.assertTrue(nodeTypes.length > 0);

        Node node = getNextSibling();
        while ((node != null) && !node.hasType(nodeTypes))
            node = node.getNextSibling();
        return node;
    }

    /// <summary>
    /// Returns first sibling node of the specified types before this node.
    /// If there is no such node returns <c>null</c>.
    /// </summary>
    Node previousSiblingOfTypes(/*params*/ /*NodeType*/int... nodeTypes)
    {
        Debug.assertTrue(nodeTypes.length > 0);

        Node node = getPreviousSibling();
        while ((node != null) && !node.hasType(nodeTypes))
            node = node.getPreviousSibling();
        return node;
    }

    /// <summary>
    /// Returns <c>true</c>, if node has one of the specified types.
    /// </summary>
    private boolean hasType(/*params*/ /*NodeType*/int... nodeType)
    {
        for (/*NodeType*/int type : nodeType)
        {
            if (getNodeType() == type)
                return true;
        }
        return false;
    }

    /// <summary>
    /// Represents a class that generates IDs for cloned comment nodes.
    /// </summary>
    private static class CommentIdGenerator implements INodeCloningListener
    {
        CommentIdGenerator(Node cloningNode)
        {
            mCloningNode = cloningNode;
        }

        public void notifyNodeCloned(Node source, Node clone)
        {
            // Do nothing if Clone is called for a comment or comment range node. Can replace Id if all comment
            // nodes are children of the cloning node.
            if (source == mCloningNode)
                return;

            if ((clone.getNodeType() != NodeType.COMMENT) &&
                (clone.getNodeType() != NodeType.COMMENT_RANGE_START) &&
                (clone.getNodeType() != NodeType.COMMENT_RANGE_END))
            {
                return;
            }

            INodeWithAnnotationId annotation = (INodeWithAnnotationId)clone;

            if (mCommentIdMap == null)
                mCommentIdMap = new HashMap<Integer, Integer>();

            int newId = 0;
            RefInt referenceToNewId = new RefInt(newId);
            boolean outRefCondition0 = !msDictionary.tryGetValue(mCommentIdMap, annotation.getIdInternal(), /*out*/ referenceToNewId);
            newId = referenceToNewId.get();
            if (outRefCondition0)
            {
                newId = clone.getDocument().getNextAnnotationId();
                msDictionary.add(mCommentIdMap, annotation.getIdInternal(), newId);
            }

            annotation.setIdInternal(newId);

            int parentNewId = 0;
            RefInt referenceToParentNewId = new RefInt(parentNewId);
            boolean outRefCondition1 = msDictionary.tryGetValue(mCommentIdMap, annotation.getParentIdInternal(), /*out*/ referenceToParentNewId);
            parentNewId = referenceToParentNewId.get();
            if (outRefCondition1)
                annotation.setParentIdInternal(parentNewId);
        }

        private /*final*/ Node mCloningNode;
        private HashMap<Integer, Integer> mCommentIdMap;
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

