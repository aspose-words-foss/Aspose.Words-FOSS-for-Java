// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.Iterator;
import com.aspose.Debug;


/// <summary>
/// Base class for nodes that can contain other nodes.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A document is represented as a tree of nodes, similar to DOM or XmlDocument.</p>
/// <p>For more info see the Composite design pattern.</p>
/// <p>The <see cref="CompositeNode"/> class:</p>
/// <list type="bullet">
/// <item>Provides access to the child nodes.</item>
/// <item>Implements Composite operations such as insert and remove children.</item>
/// <item>Provides methods for XPath navigation.</item>
/// </list>
/// </remarks>
/// <dev>
/// Maintains a pointer to the list of children.
///
/// Provides helper methods to insert or remove a whole range of nodes.
///
/// Inserting and removing children are the most important features here.
///
/// Public API methods for insert and remove verify the arguments well and raise the insert/delete
/// node events to the owner document.
///
/// The child nodes are stored in a doubly linked list and the composite node knows its first and last children.
/// This approach was taken from XmlElement (with addition of doubly linking). Inserting or removing a node just
/// means updating the PrevSibling, NextSibling and ParentNode of the appropriate nodes.
/// </dev>
public abstract class CompositeNode<V extends Node> extends Node implements Iterable<V>, INodeCollection
{
    /// <summary>
    /// Ctor for creating special nodes that never need to know the document.
    /// </summary>
    protected CompositeNode()
    {
    }

    /// <summary>
    /// Ctor to use.
    /// </summary>
    protected CompositeNode(DocumentBase doc)
    {
    	super(doc);
	
    }

            
    /// <summary>
    /// Removes this node from the parent document, but keeps the children inside the tree,
    /// by attaching them to direct parent of this node into the place previously occupied by this node.
    /// </summary>
    /// <remarks>
    /// This node largely makes sense for <see cref="NodeType.StructuredDocumentTag"/> and <see cref="NodeType.SmartTag"/>,
    /// because they contain children of the same level as they are <see cref="MarkupLevel"/>.
    /// </remarks>
    protected void coreRemoveSelfOnly()
    {
        if (getParentNode() == null)
            throw new IllegalStateException("This node is not attached to any document");

        SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(getDocument());
        try /*JAVA: was using*/
        {
            while (hasChildNodes())
            {
                // Move all children on the same level to preserve them
                // in the document when their parent is deleted.
                getParentNode().insertAfter(getLastChild(), this);
            }

            getParentNode().removeChildCore(this, true);
        }
        finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
    }

    /// <summary>
    /// Returns <c>true</c> as this node can have child nodes.
    /// </summary>
    public /*override*/ boolean isComposite() { return true; }

    /// <summary>
    /// Returns <c>true</c> if this node has any child nodes.
    /// </summary>
    public boolean hasChildNodes() { return (getLastChild() != null); }

    /// <summary>
    /// Returns <c>true</c> if this node has any descendants other than markup nodes.
    /// </summary>
    /// <remarks>
    /// Sometimes a CompositeNode that is only container for empty Markup nodes is a signal of invalid node.
    /// This was the case with Defect 25952
    /// </remarks>
    boolean hasNonMarkupDescendants() { return (getFirstNonMarkupDescendant() != null); }

    /// <summary>
    /// Returns <c>true</c> if this node has only one child node.
    /// </summary>
    boolean hasOneChildOnly() { return hasChildNodes() && (getFirstChild() == getLastChild()); }

    /// <summary>
    /// Recursively checks if any of this node's children implement <see cref="IInline"/>.
    /// </summary>
    boolean hasInlineNodes()
    {
        for (Node childNode = getFirstChild(); childNode != null; childNode = childNode.getNextSibling())
        {
            if (childNode instanceof IInline)
                return true;

            CompositeNode childNodeAsComposite = ms.as(childNode, CompositeNode.class);
            if ((childNodeAsComposite != null) && childNodeAsComposite.hasInlineNodes())
                return true;
        }
        return false;
    }

    /// <summary>
    /// Gets the first child of the node.
    /// </summary>
    /// <remarks>
    /// If there is no first child node, a <c>null</c> is returned.
    /// </remarks>
    public Node getFirstChild() { return mFirstChild; }

    /// <summary>
    /// Gets the last child of the node.
    /// </summary>
    /// <remarks>
    /// If there is no last child node, a <c>null</c> is returned.
    /// </remarks>
    public Node getLastChild() { return mLastChild; }

    /// <summary>
    /// Gets the first non-markup <see cref="NodeUtil.IsMarkupNode(Words.NodeType)"/> descendant of the node in a depth-first traverse.
    /// </summary>
    Node getFirstNonMarkupDescendant() { return NodeUtil.getFirstNonMarkupNodeInDepthFirstTraverse(getFirstChild(), false); }

    /// <summary>
    /// Gets the last non-markup <see cref="NodeUtil.IsMarkupNode(Words.NodeType)"/> descendant of the node in a depth-first traverse.
    /// </summary>
    Node getLastNonMarkupDescendant() { return NodeUtil.getLastNonMarkupNodeInDepthFirstTraverse(getLastChild(), false); }

    /// <summary>
    /// Gets the first non-markup (<see cref="NodeUtil.IsMarkupNode(Words.NodeType)"/>) composite descendant of
    /// the node in a depth-first traverse.
    /// </summary>
    CompositeNode getFirstNonMarkupCompositeDescendant() { return (CompositeNode)NodeUtil.getFirstNonMarkupNodeInDepthFirstTraverse(getFirstChild(), true); }

    /// <summary>
    /// Gets the last non-markup (<see cref="NodeUtil.IsMarkupNode(Words.NodeType)"/>) composite descendant of
    /// the node in a depth-first traverse.
    /// </summary>
    CompositeNode getLastNonMarkupCompositeDescendant() { return (CompositeNode)NodeUtil.getLastNonMarkupNodeInDepthFirstTraverse(getLastChild(), true); }

    /// <summary>
    /// Gets the first non-annotation (<see cref="NodeUtil.IsCrossStructureAnnotation(Node)"/>) child of the node.
    /// </summary>
    Node getFirstNonAnnotationChild()
    {
        Node result = getFirstChild();
        while ((result != null) && NodeUtil.isCrossStructureAnnotation(result))
            result = result.getNextSibling();
        return result;
    }

    /// <summary>
    /// Gets the last non-annotation (<see cref="NodeUtil.IsCrossStructureAnnotation(Node)"/>) child of the node.
    /// </summary>
    Node getLastNonAnnotationChild()
    {
        Node result = getLastChild();
        while ((result != null) && NodeUtil.isCrossStructureAnnotation(result))
            result = result.getPreviousSibling();
        return result;
    }

    /// <summary>
    /// Gets the number of immediate children of this node.
    /// </summary>
    public int getCount()
    {
        int count = 0;
        for (Node node = getFirstChild(); node != null; node = node.getNextSibling())
            count++;
        return count;
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        CompositeNode lhs = (CompositeNode)super.deepClone(isCloneChildren, cloningListener);

        lhs.mLastChild = null;
        lhs.mFirstChild = null;

        if (isCloneChildren)
        {
            for (Node srcChild = getFirstChild(); srcChild != null; srcChild = srcChild.getNextSibling())
                lhs.appendChildForLoad(srcChild.deepClone(true, cloningListener));
        }

        return lhs;
    }

    /// <summary>
    /// Gets the text of this node and of all its children.
    /// </summary>
    ///<remarks>
    ///<p>The returned string includes all control and special characters as described in <see cref="ControlChar"/>.</p>
    ///</remarks>
    public /*override*/ String getText()
    {
        StringBuilder builder = new StringBuilder();
        getTextToBuilder(builder);
        return builder.toString();
    }

    /// <summary>
    /// Puts the text of this node and of all its children to the specified string builder.
    /// </summary>
    /*override*/ void getTextToBuilder(StringBuilder builder)
    {
        getChildrenTextInternal(builder);
        msStringBuilder.append(builder, getEndText());
    }

    /// <overloads>Returns a collection of child nodes that match the specified type.</overloads>
    /// <summary>
    /// Returns a live collection of child nodes that match the specified type.
    /// </summary>
    /// <remarks>
    /// <p>The collection of nodes returned by this method is always live.</p>
    /// </remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="CompositeNode.GetChildNodesLive"]/*'/>
    /// <param name="nodeType">Specifies the type of nodes to select.</param>
    /// <param name="isDeep"><c>true</c> to select from all child nodes recursively;
    /// <c>false</c> to select only among immediate children. </param>
    /// <returns>A live collection of child nodes of the specified type.</returns>
    public NodeCollection getChildNodes(/*NodeType*/int nodeType, boolean isDeep)
    {
        return new NodeCollection(this, nodeType, isDeep);
    }

    /// <summary>
    /// Returns a live collection of child nodes that match the specified types.
    /// </summary>
    NodeCollection getChildNodes(/*NodeType*/int[] nodeTypes, boolean isDeep)
    {
        return new NodeCollection(this, nodeTypes, isDeep);
    }

    /// <summary>
    /// Returns an Nth child node that matches the specified type.
    /// </summary>
    /// <remarks>
    /// <p>If index is out of range, a <c>null</c> is returned.</p>
    /// </remarks>
    /// <param name="nodeType">Specifies the type of the child node.</param>
    /// <param name="index">Zero based index of the child node to select.
    /// Negative indexes are also allowed and indicate access from the end,
    /// that is -1 means the last node.</param>
    /// <param name="isDeep"><c>true</c> to select from all child nodes recursively;
    /// <c>false</c> to select only among immediate children. See remarks for more info.</param>
    /// <returns>The child node that matches the criteria or <c>null</c> if no matching node is found.</returns>
    /// <remarks>Note that markup nodes (<see cref="NodeType.StructuredDocumentTag"/> and <see cref="NodeType.SmartTag "/>)
    /// are traversed even when <paramref name="isDeep"/> = <c>false</c> and <see cref="GetChild"/> is invoked for non-markup node type. For example if the first run in a para
    /// is wrapped in a <see cref="StructuredDocumentTag"/>, it will still be returned by <see cref="GetChild"/>(<see cref="NodeType.Run"/>, 0, <c>false</c>).</remarks>
    public Node getChild(/*NodeType*/int nodeType, int index, boolean isDeep)
    {
        if ((index >= 0) && !isDeep)
        {
            // Getting Nth flat child from the start could be used quite often, so this
            // is a simple inline performance optimization.

            // Need to repeat count times, for example 1 time for the first element (zero index).
            int count = index + 1;

            // This is the same technique as in NodeCollection when performing a flat enumeration.
            // We need to ignore sdt nodes if the user wants nodes of a specific type.
            boolean isSkipMarkupNodes = NodeUtil.isSkipMarkupNodesInFlatEnumeration(nodeType);
            Node node = (isSkipMarkupNodes) ? getFirstNonMarkupDescendant() : getFirstChild();
            while (node != null)
            {
                if ((node.getNodeType() == nodeType) || (nodeType == NodeType.ANY))
                    count--;

                if (count == 0)
                    return node;

                node = (isSkipMarkupNodes) ? node.getNextNonMarkupNodeLimited() : node.getNextSibling();
            }
        }
        else
        {
            NodeCollection nodes = getChildNodes(nodeType, isDeep);
            return nodes.get(index);
        }

        return null;
    }

    /// <summary>
    /// Selects a list of nodes matching the XPath expression.
    /// </summary>
    /// <remarks>
    /// <p>Only expressions with element names are supported at the moment. Expressions
    /// that use attribute names are not supported.</p>
    /// </remarks>
    /// <param name="xpath">The XPath expression.</param>
    /// <returns>A list of nodes matching the XPath query.</returns>
    public NodeList selectNodes(String xpath)
    {
        return DocumentXPathNavigator.selectNodes(this, xpath);
    }

    /// <summary>
    /// Selects the first <see cref="Node"/> that matches the XPath expression.
    /// </summary>
    /// <remarks>
    /// <p>Only expressions with element names are supported at the moment. Expressions
    /// that use attribute names are not supported.</p>
    /// </remarks>
    /// <param name="xpath">The XPath expression.</param>
    /// <returns>The first <see cref="Node"/> that matches the XPath query or <c>null</c> if no matching node is found.</returns>
    public Node selectSingleNode(String xpath)
    {
        return DocumentXPathNavigator.selectSingleNode(this, xpath);
    }

    /// <summary>
    /// Provides support for the for each style iteration over the child nodes of this node.
    /// </summary>
    public Iterator<V> iterator()
    {
        return new NodeCollectionEnumerator<V>(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()


    /// <summary>
    /// Adds the specified node to the end of the list of child nodes for this node.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="CompositeNode.InsertCommon"]/*'/>
    /// <param name="newChild">The node to add.</param>
    /// <returns>The node added.</returns>
    /// <javaName>appendChild(com.aspose.words.Node)</javaName>
    public <T extends Node> T appendChild(T newChild)
    {
        return insertAfter(newChild, getLastChild());
    }

    /// <summary>
    /// Adds the specified node to the beginning of the list of child nodes for this node.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="CompositeNode.InsertCommon"]/*'/>
    /// <param name="newChild">The node to add.</param>
    /// <returns>The node added.</returns>
    /// <javaName>prependChild(com.aspose.words.Node)</javaName>
    public <T extends Node> T prependChild(T newChild)
    {
        return insertBefore(newChild, getFirstChild());
    }

    /// <summary>
    /// Inserts the specified node immediately after the specified reference node.
    /// </summary>
    /// <remarks>
    /// <p>If <paramref name="refChild"/> is <c>null</c>, inserts <paramref name="newChild"/> at the beginning of the list of child nodes.</p>
    /// </remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="CompositeNode.InsertCommon"]/*'/>
    /// <param name="newChild">The <see cref="Node"/> to insert.</param>
    /// <param name="refChild">The <see cref="Node"/> that is the reference node. The <paramref name="newChild"/> is placed after the <paramref name="refChild"/>.</param>
    /// <returns>The inserted node.</returns>
    /// <javaName>insertAfter(com.aspose.words.Node, com.aspose.words.Node)</javaName>
    public <T extends Node> T insertAfter(T newChild, Node refChild)
    {
        return insert(newChild, refChild, true);
    }

    /// <summary>
    /// Inserts the specified node immediately before the specified reference node.
    /// </summary>
    /// <remarks>
    /// <p>If <paramref name="refChild"/> is <c>null</c>, inserts <paramref name="newChild"/> at the end of the list of child nodes.</p>
    /// </remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="CompositeNode.InsertCommon"]/*'/>
    /// <param name="newChild">The <see cref="Node"/> to insert.</param>
    /// <param name="refChild">The <see cref="Node"/> that is the reference node. The <paramref name="newChild"/> is placed before this node.</param>
    /// <returns>The inserted node.</returns>
    /// <javaName>insertBefore(com.aspose.words.Node, com.aspose.words.Node)</javaName>
    public <T extends Node> T insertBefore(T newChild, Node refChild)
    {
        return insert(newChild, refChild, false);
    }

    /// <summary>
    /// Removes the specified child node.
    /// </summary>
    /// <remarks>
    /// <p>The parent of <paramref name="oldChild"/> is set to <c>null</c> after the node is removed.</p>
    /// </remarks>
    /// <param name="oldChild">The node to remove.</param>
    /// <returns>The removed node.</returns>
    /// <javaName>removeChild(com.aspose.words.Node)</javaName>
    public <T extends Node> T removeChild(T oldChild)
    {
        return removeChildCore(oldChild, false);
    }

    /// <summary>
    /// Removes the specified child node.
    /// </summary>
    private <T extends Node> T removeChildCore(T oldChild, boolean skipUpdatingRelatedNodes)
    {
        if (oldChild == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "oldChild");

        if (oldChild.getParentNode() != this)
            throw new IllegalArgumentException("This node is not a parent of the oldChild node.");

        DocumentBase doc = getDocument();

        NodeChangingArgs args = CompositeNodeHelper.documentBaseInternalEvent(doc, oldChild, this, null, NodeChangingAction.REMOVE);

        if (CompositeNodeHelper.documentBaseIsTrackRevisionsEnabled(doc))
        {
            if (RevisionTrackingUtil.trackDeletion(oldChild))
                return oldChild;
        }

        CompositeNodeHelper.documentBaseBeforeEvent(doc, args);

        if (!skipUpdatingRelatedNodes && NodeUtil.isStructuredDocumentTagNode(oldChild))
            NodeUtil.resetDisplacedAnnotationReferences(oldChild, false);

        T removedNode = removeNode(oldChild);

        CompositeNodeHelper.documentBaseAfterEvent(doc, args);

        return removedNode;
    }

    /// <summary>
    /// Removes all the child nodes of the current node.
    /// </summary>
    public void removeAllChildren()
    {
        NodeRemover.removeSameParent(getFirstChild(), null);
    }

    /// <summary>
    /// Removes all <see cref="SmartTag"/> descendant nodes of the current node.
    /// </summary>
    /// <remarks>This method does not remove the content of the smart tags.</remarks>
    public void removeSmartTags()
    {
        for (SmartTag smartTag : (Iterable<SmartTag>) getChildNodes(NodeType.SMART_TAG, true))
            smartTag.coreRemoveSelfOnly();
    }

    /// <summary>
    /// Just appends node without performing any checks.
    /// Use only when you can be sure the tree will be valid.
    ///
    /// This does not even mark the document tree as modified so any node collections
    /// that you already have might fail to show new nodes added using this method.
    /// You should avoid using node collections if you are using this method.
    /// </summary>
    Node appendChildForLoad(Node newChild)
    {
        Debug.assertTrue(newChild.getParentNode() == null, "The node is already a child of another node.");
        // WORDSNET-4319 RTF importer creates two Body or two HeaderFooter of the same type for
        // some documents. It is not too bad because it actually deletes the dummy HeaderFooter
        // after processing and extra Body is created for an invalid document.
        // RK But I can't improve RTF importer right now so I have to comment out this check.
        // Return it back when the defect is fixed.
        // Debug.Assert(CanInsert(newChild, mLastChild, true))

        if (mLastChild == null)
        {
            // First child node to be added to the parent, it is linked to nothing.
            newChild.setPrevNode(null);
            newChild.setNextNode(null);
            mFirstChild = newChild;
        }
        else
        {
            newChild.setPrevNode(mLastChild);
            newChild.setNextNode(null);
            mLastChild.setNextNode(newChild);
        }

        mLastChild = newChild;
        newChild.setParent(this);

        return newChild;
    }

    /// <summary>
    /// Inserts nodes starting from the start node up to, but not including the end node
    /// into this node before the reference node.
    /// </summary>
    /// <param name="start">Node to start moving from.</param>
    /// <param name="end">Can be <c>null</c> to indicate move all from start to the end.</param>
    /// <param name="refNode">The nodes will be inserted before this node.</param>
    void insertBefore(Node start, Node end, Node refNode)
    {
        insertBefore(start, end, refNode, false);
    }

    /// <summary>
    /// Inserts nodes starting from the start node up to, but not including the end node
    /// into this node before the reference node.
    /// </summary>
    /// <param name="start">Node to start moving from.</param>
    /// <param name="end">Can be <c>null</c> to indicate move all from start to the end.</param>
    /// <param name="refNode">The nodes will be inserted before this node.</param>
    /// <param name="safe">Prevent insertion errors when <c>true</c>.</param>
    void insertBefore(Node start, Node end, Node refNode, boolean safe)
    {
        if ((end != null) && (end.getParentNode() != start.getParentNode()))
            throw new IllegalArgumentException("The start and end nodes should have the same parent.");

        Node curNode = start;
        while (curNode != end)
        {
            Node nextNode = curNode.getNextSibling();

            if (!safe || canInsert(curNode))
                insertBefore(curNode, refNode);

            curNode = nextNode;
        }
    }

    /// <summary>
    /// Inserts nodes starting from the start node up to, but not including the end node
    /// into this node after the reference node.
    /// </summary>
    /// <param name="start">Node to start moving from.</param>
    /// <param name="end">Can be <c>null</c> to indicate move all from start to the end.</param>
    /// <param name="refNode">The nodes will be inserted after this node.</param>
    void insertAfter(Node start, Node end, Node refNode)
    {
        if ((end != null) && (end.getParentNode() != start.getParentNode()))
            throw new IllegalArgumentException("The start and end nodes should have the same parent.");

        Node curNode = start;
        while (curNode != end)
        {
            Node nextNode = curNode.getNextSibling();
            refNode = insertAfter(curNode, refNode);
            curNode = nextNode;
        }
    }

    /// <summary>
    /// Returns the index of the specified child node in the child node array.
    /// </summary>
    /// <remarks>
    /// Returns -1 if the node is not found in the child nodes.
    /// </remarks>
    public int indexOf(Node child)
    {
        int index = 0;
        for (Node curChild = getFirstChild(); curChild != null; curChild = curChild.getNextSibling())
        {
            if (curChild == child)
                return index;
            else
                index++;
        }
        return -1;
    }

    /// <summary>
    /// Returns the index of a child node which contains the specified descendant node.
    /// </summary>
    /// <remarks>
    /// Returns -1 if the node is not found in the child nodes.
    /// </remarks>
    int indexOfChildByDescendant(Node descendant, boolean skipAnnotations)
    {
        int index = 0;
        for (Node child = getFirstChild(); child != null; child = child.getNextSibling())
        {
            if (!skipAnnotations || !NodeUtil.isCrossStructureAnnotation(child))
            {
                if (NodeUtil.isAncestorOrSelf(descendant, child))
                    return index;

                index++;
            }
        }

        return -1;
    }

    /// <summary>
    /// Recursively tries to find the childmost node that contains the position.
    /// Returns <c>null</c> if no node that contains this position was found.
    /// </summary>
    /*override*/ Node getNodeFromPos(int position) throws Exception
    {
        //This algorithm is adopted from the .NET BinarySearch function.
        int lo = 0;
        int hi = indexOf(getLastChild());

        while (lo <= hi)
        {
            int i = (lo + hi) >> 1;

            Node curNode = getChild(NodeType.ANY, i, false);
            int start = curNode.getStart();
            int end = start + curNode.getTextLength();

            if (position < start)
            {
                //Position is before this node, make next iteration to look closer to the beginning.
                hi = i - 1;
            }
            else if (position >= end)
            {
                //Position is after this node, make next iteration to look close to the end.
                lo = i + 1;
            }
            else
            {
                //Position is between start and end, recurse into the children.
                return curNode.getNodeFromPos(position);
            }
        }

        return null;
    }

    /// <summary>
    /// Gets the length of the text of the child nodes. Faster than using GetText().Length.
    /// </summary>
    /*override*/ int getTextLength()
    {
        int result = 0;
        for (Node srcChild = getFirstChild(); srcChild != null; srcChild = srcChild.getNextSibling())
            result += srcChild.getTextLength();

        result += getEndText().length();
        return result;
    }

    /// <summary>
    /// Override in nodes that need to add some text after the children text, for example paragraph mark.
    /// </summary>
    /*virtual*/ String getEndText()
    {
        return "";
    }

    /// <summary>
    /// Gets text of all children nodes without appending "end text" that some composite nodes might have.
    /// </summary>
    String getChildrenText()
    {
        StringBuilder builder = new StringBuilder();
        getChildrenTextInternal(builder);
        return builder.toString();
    }

    /// <summary>
    /// Puts text of all children nodes into the specified string builder without appending "end text"
    /// that some composite nodes might have.
    /// </summary>
    private void getChildrenTextInternal(StringBuilder builder)
    {
        for (Node child = getFirstChild(); child != null; child = child.getNextSibling())
            child.getTextToBuilder(builder);
    }

    /// <summary>
    /// Calls AcceptStart, then AcceptChildren, then AcceptEnd.
    /// </summary>
    /// <param name="visitor"></param>
    /// <returns></returns>
    protected boolean acceptCore(DocumentVisitor visitor) throws Exception
    {
        switch (acceptStart(visitor))
        {
            case VisitorAction.CONTINUE:
                break;
            case VisitorAction.SKIP_THIS_NODE:
                return true;
            case VisitorAction.STOP:
                return false;
            default:
                throw new IllegalStateException("Unknown visitor action.");
        }

        if (!acceptChildren(visitor))
            return false;

        return visitorActionToBool(acceptEnd(visitor));
    }

    /// <summary>
    /// When implemented in a derived class, calls the VisitXXXStart method of the specified document visitor.
    /// </summary>
    public abstract /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception;

    /// <summary>
    /// When implemented in a derived class, calls the VisitXXXEnd method of the specified document visitor.
    /// </summary>
    public abstract /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception;

    /// <summary>
    /// Invokes Accept(visitor) for every child node. Returns <c>true</c> to continue enumeration.
    /// </summary>
    protected boolean acceptChildren(DocumentVisitor visitor) throws Exception
    {
        // Use while "loop" instead of the usual "for" to allow the child to remove itself during visiting.
        Node child = getFirstChild();
        while (child != null)
        {
            Node nextChild = child.getNextSibling();

            if (!child.accept(visitor))
                return false;

            child = nextChild;
        }

        return true;
    }

    /// <summary>
    /// Implement in derived classes and return <c>true</c> if the node can be inserted into this node.
    /// </summary>
    /*virtual*/ boolean canInsert(Node newChild)
    {
        return true;
    }

    /// <summary>
    /// Inserts a child before or after the reference node.
    /// Reference node can be null (like in XmlNode).
    /// If inserting after a null reference node, inserts at the beginning.
    /// If inserting before a null reference node, inserts at the end.
    /// Note that inserting before could be a bit slower than inserting after.
    /// </summary>
    <T extends Node>T insert(T newChild, Node refChild, boolean isAfter)
    {
        if ((refChild != null) && (refChild.getParentNode() != this))
            throw new IllegalArgumentException("The reference node is not a child of this node.");

        if (newChild == null)
            throw new IllegalArgumentException("Cannot insert a null node.");

        if (newChild == this)
            throw new IllegalArgumentException("Cannot add a node to self.");

        if (isAncestorNode(newChild))
            throw new IllegalArgumentException("The newChild is an ancestor of this node.");

        if (newChild == refChild)
            throw new IllegalArgumentException("Cannot add a node before/after itself.");

        DocumentBase thisDoc = getDocument();
        DocumentBase childDoc = newChild.getDocument();
        CompositeNodeHelper.documentBaseCompareException(thisDoc, childDoc);

        //System nodes (such as mail merge marker) are allowed to be inserted anywhere.
        if ((newChild.getNodeType() != NodeType.SYSTEM) && !canInsert(newChild))
            throw new IllegalArgumentException("Cannot insert a node of this type at this location.");

        CompositeNode oldParent = newChild.getParentNode();
        Node oldNextSibling = newChild.getNextSibling();

        //Remove the child from its current parent.
        if (oldParent != null)
        {
            // Disable revision tracking to not generate Deletion revision for this node.
            // RevisionTrackingUtil.TrackInsertion is used below to generate necessary revisions.
            SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(thisDoc);
            try /*JAVA: was using*/
            {
                oldParent.removeChildCore(newChild, true);
            }
            finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
        }

        NodeChangingArgs args = CompositeNodeHelper.documentBaseInternalEvent(thisDoc, newChild, null, this, NodeChangingAction.INSERT);
        CompositeNodeHelper.documentBaseBeforeEvent(thisDoc, args);

        // Insert the node into the linked list.
        if (mLastChild == null)
        {
            // This is a first child node to be added to the parent, link it to nothing.
            newChild.setPrevNode(null);
            newChild.setNextNode(null);
            mFirstChild = newChild;
            mLastChild = newChild;
        }
        else
        {
            if (isAfter)
            {
                if (refChild != null)
                {
                    // Normal insert after some node.
                    insertAfterCore(newChild, refChild);
                }
                else
                {
                    // Inserting after null means inserting before the first child.
                    insertBeforeCore(newChild, mFirstChild);
                }
            }
            else
            {
                if (refChild != null)
                {
                    insertBeforeCore(newChild, refChild);
                }
                else
                {
                    // Inserting before null means insert after the last child.
                    insertAfterCore(newChild, mLastChild);
                }
            }
        }

        // Make the child aware it has a parent now.
        newChild.setParent(this);

        CompositeNodeHelper.documentBaseAfterEvent(thisDoc, args);

        if (CompositeNodeHelper.documentBaseIsTrackRevisionsEnabled(thisDoc))
            RevisionTrackingUtil.trackInsertion(newChild, oldParent, oldNextSibling);

        return newChild;
    }

    /// <summary>
    /// Modifies the links to insert newChild after prevChild.
    /// </summary>
    private void insertAfterCore(Node newChild, Node prevChild)
    {
        Node nextChild = prevChild.getNextNode();

        newChild.setPrevNode(prevChild);
        newChild.setNextNode(nextChild);
        prevChild.setNextNode(newChild);
        // If added to the end, change the new last child node.
        if (nextChild == null)
            mLastChild = newChild;
        else
            nextChild.setPrevNode(newChild);
    }

    /// <summary>
    /// Modifies the links to insert newChild before nextChild.
    /// </summary>
    private void insertBeforeCore(Node newChild, Node nextChild)
    {
        Node prevChild = nextChild.getPrevNode();

        newChild.setPrevNode(prevChild);
        newChild.setNextNode(nextChild);
        nextChild.setPrevNode(newChild);

        // In inserted to the first position, change the first child node.
        if (prevChild == null)
            mFirstChild = newChild;
        else
            prevChild.setNextNode(newChild);
    }

    /// <summary>
    /// Performs real node removing by arranging all necessary node links.
    /// </summary>
    private <T extends Node> T removeNode(T oldChild)
    {
        if (oldChild == mFirstChild)
        {
            if (mFirstChild == mLastChild)
            {
                // The first and the only child is being deleted, set it to no child nodes at all.
                mFirstChild = null;
                mLastChild = null;
            }
            else
            {
                // The first child is being deleted.
                mFirstChild = oldChild.getNextNode();
                mFirstChild.setPrevNode(null);
            }
        }
        else
        {
            // Not a first node is being deleted, set the previous node to point to next sibling of the deleted node.
            Node prevNode = oldChild.getPrevNode();
            Node nextNode = oldChild.getNextNode();
            prevNode.setNextNode(nextNode);
            // If removing the last node, update the last node pointer.
            if (nextNode == null)
                mLastChild = prevNode;
            else
                nextNode.setPrevNode(prevNode);
        }

        // Disconnect the removed node.
        oldChild.setNextNode(null);
        oldChild.setPrevNode(null);
        oldChild.setParent(null);

        return oldChild;
    }

    //>>>>>>>> #region  INodeCollection

    /// <summary>
    /// <para>Represents this composite node as node collection for <see cref="NodeCollectionEnumerator{T}"/>.</para>
    /// <para>Gets the next matching node in the collection forward from the specified node.</para>
    /// </summary>
    @ReservedForInternalUse @Deprecated
    
    public Node /*INodeCollection.*/getNextMatchingNode(Node curNode)
    {
        Node prevNode = curNode;
        mCurrent = (curNode == this) ? getFirstChild() : curNode.getNextSibling();
        return prevNode;
    }

    @ReservedForInternalUse @Deprecated
    
    public Node /*INodeCollection.*/getCurrentNode()
    {
        return mCurrent;
    }

    /// <summary>
    /// <para>Represents this composite node as node collection for <see cref="NodeCollectionEnumerator{T}"/>.</para>
    /// <para>Returns <c>this</c>.</para>
    /// </summary>
    @ReservedForInternalUse @Deprecated
    
public 
    CompositeNode /*INodeCollection.*/getContainer() { return this; }

//<<<<<<<< #endregion 

    private Node mFirstChild;
    private Node mLastChild;
    private Node mCurrent;

}

