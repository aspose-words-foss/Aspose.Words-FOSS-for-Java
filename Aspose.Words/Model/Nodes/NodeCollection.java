// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import com.aspose.Debug;


/// <summary>
/// Represents a collection of nodes of a specific type.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="NodeCollection"/> does not own the nodes it contains, rather, is just a selection of nodes
/// of the specified type, but the nodes are stored in the tree under their respective parent nodes.</p>
///
/// <p><see cref="NodeCollection"/> supports indexed access, iteration and provides add and remove methods.</p>
///
/// <p>The <see cref="NodeCollection"/> collection is "live", i.e. changes to the children of the node object
/// that it was created from are immediately reflected in the nodes returned by the <see cref="NodeCollection"/>
/// properties and methods.</p>
///
/// <p><see cref="NodeCollection"/> is returned by <see cref="CompositeNode.GetChildNodes(NodeType, bool)"/>
/// and also serves as a base class for typed node collections such as <see cref="SectionCollection"/>,
/// <see cref="ParagraphCollection"/> etc.</p>
///
/// <p><see cref="NodeCollection"/> can be "flat" and contain only immediate children of the node it was created
/// from, or it can be "deep" and contain all descendant children.</p>
/// </remarks>
public class NodeCollection<T extends Node> implements INodeCollection, Iterable<T>
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="container">The topmost node covered by the collection. Not included in the collection itself.</param>
    /// <param name="nodeType">Type of nodes that this collection selects.</param>
    /// <param name="isDeep">Specifies whether the collection selects all nested children or only immediate children of the container.</param>
    NodeCollection(CompositeNode container, /*NodeType*/int nodeType, boolean isDeep)
    {
    	this(container, NodeTypeMatcher.getMatcher(nodeType), isDeep);
	
    }

    NodeCollection(CompositeNode container, /*NodeType*/int[] nodeTypes, boolean isDeep)
    {
    	this(container, new NodeTypeMatcher(nodeTypes), isDeep);
	
    }

    NodeCollection(CompositeNode container, NodeMatcher matcher, boolean isDeep)
    {
        if (container == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "container");

        if (matcher == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "matcher");

        mContainer = container;

        // SPEED Get the document only once.
        mDocument = container.getDocument();

        mMatcher = matcher;
        mIsDeep = isDeep;

        invalidate();
    }

    /// <summary>
    /// Retrieves a node at the given index.
    /// </summary>
    /// <param name="index">An index into the collection of nodes.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    
    public Node get(int index)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	checkInvalidate();

        	if (index < 0)
        	{
            	//Access from the end, convert the index into positive index.
            	index = getCount() + index;

            	//If it is still negative, this means out of range.
            	if (index < 0)
                	return null;
        	}

        	//Optimization - direct hit - access of the most recently accessed element.
        	if (mCurIndex == index)
            	return mCurNode;

        	//Optimization - no direct hit, but most of the time we expect the user will
        	//access nodes in close proximity, therefore navigate only the difference
        	//between last and new indexes.
        	//
        	//Note the way it works for index zero: mCurIndex is reset to -1 when optimization is reset,
        	//therefore 0 - (-1) results in 1, which is the correct index to retrieve the first child.
        	//
        	//This optimization is not my idea - I copied it from XmlElement.
        	int diff = index - mCurIndex;
        	Node node = getNthMatchingNode(mCurNode, diff);

        	//Save results for next optimization.
        	if (node != null)
        	{
            	mCurIndex = index;
            	mCurNode = node;
        	}

        	return node;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Adds a node to the end of the collection.
    /// </summary>
    /// <remarks>
    /// <p>The node is inserted as a child into the node object from which the collection was created.</p>
    /// </remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.InsertCommon"]/*'/>
    /// <param name="node">The node to be added to the end of the collection.</param>
    /// <exception cref="NotSupportedException">The <see cref="NodeCollection"/> is a "deep" collection.</exception>
    public void add(Node node)
    {
        if (mIsDeep)
            throw new UnsupportedOperationException("Adding nodes is not yet supported for deep node collections.");

        mContainer.appendChild(node);
    }

    /// <summary>
    /// Inserts a node into the collection at the specified index.
    /// </summary>
    /// <remarks>
    /// <p>The node is inserted as a child into the node object from which the collection was created.</p>
    /// <p>If the index is equal to or greater than <see cref="Count"/>, the node is added at the end of the collection.</p>
    /// <p>If the index is negative and its absolute value is greater than <see cref="Count"/>, the node is added at the end of the collection.</p>
    /// </remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.InsertCommon"]/*'/>
    /// <param name="index">The zero-based index of the node.
    /// Negative indexes are allowed and indicate access from the back of the list.
    /// For example -1 means the last node, -2 means the second before last and so on.</param>
    /// <param name="node">The node to insert.</param>
    /// <exception cref="NotSupportedException">The <see cref="NodeCollection"/> is a "deep" collection.</exception>
    public void insert(int index, Node node)
    {
        if (mIsDeep)
            throw new UnsupportedOperationException("Inserting nodes is not yet supported for deep node collections.");

        mContainer.insertBefore(node, this.get(index));
    }

    /// <summary>
    /// Removes the node from the collection and from the document.
    /// </summary>
    /// <param name="node">The node to remove.</param>
    public void remove(Node node)
    {
        node.remove();
    }

    /// <summary>
    /// Removes the node at the specified index from the collection and from the document.
    /// </summary>
    /// <param name="index">The zero-based index of the node.
    /// Negative indexes are allowed and indicate access from the back of the list.
    /// For example -1 means the last node, -2 means the second before last and so on.</param>
    public void removeAt(int index)
    {
        this.get(index).remove();
    }

    /// <summary>
    /// Removes all nodes from this collection and from the document.
    /// </summary>
    public void clear()
    {
        // When a node is removed, NodeCollectionEnumerator remembers the previous node visited in the collection's
        // container and so continues iterating from that node, not from the beginning of the container.
        for (Node node : (Iterable<Node>) this)
            node.remove();
    }

    /// <summary>
    /// Determines whether a node is in the collection.
    /// </summary>
    /// <remarks>
    /// <p>This method performs a linear search; therefore, the average execution time is proportional to <see cref="Count"/>.</p>
    /// </remarks>
    /// <param name="node">The node to locate.</param>
    /// <returns><c>true</c> if item is found in the collection; otherwise, <c>false</c>.</returns>
    public boolean contains(Node node)
    {
        return (indexOf(node) != -1);
    }

    /// <summary>
    /// Returns the zero-based index of the specified node.
    /// </summary>
    /// <param name="node">The node to locate.</param>
    /// <returns>The zero-based index of the node within the collection, if found; otherwise, -1.</returns>
    /// <remarks>
    /// <p>This method performs a linear search; therefore, the average execution time is proportional to <see cref="Count"/>.</p>
    /// </remarks>
    public int indexOf(Node node)
    {
        int i = 0;
        for (Node curNode : (Iterable<Node>) this)
        {
            if (node == curNode)
                return i;
            i++;
        }
        return -1;
    }

    /// <summary>
    /// Copies all nodes from the collection to a new array of nodes.
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.ToArrayCommon"]/*'/>
    /// </remarks>
    /// <returns>An array of nodes.</returns>
    public Node[] toArray()
    {
        return msArrayList.toArray(this.<Node>toList(), new Node[0]);
    }

    /// <summary>
    /// Provides a simple "foreach" style iteration over the collection of nodes.
    /// </summary>
    /// <returns>An <ms>IEnumerator</ms><java>Iterator</java><cpp>IEnumerator</cpp>.</returns>
    public Iterator<T> iterator()
    {
        return new NodeCollectionEnumerator<T>(this);
    }

    <TNode extends Node>NodeCollectionEnumerator<TNode> getNodeEnumerator()
    {
        return new NodeCollectionEnumerator<TNode>(this);
    }

    /// <summary>
    /// Provides a simple "foreach" style iteration over the collection of nodes.
    /// </summary>
    /// <returns><ms>An IEnumerator</ms><java><tt>Iterator&lt;Node&gt;</tt></java><cpp>An IEnumerator</cpp>.</returns>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Copies all nodes from the collection to a new List{Node} of nodes.
    /// Used as core of <see cref="ToArray"/> method.
    /// </summary>
    ArrayList<Node> toNodeList()
    {
        return this.<Node>toList();
    }

    /// <summary>
    /// Copies all nodes from the collection to a new List{T} of nodes.
    /// </summary>
    <TValue extends Node>ArrayList<TValue> toList()
    {
        ArrayList<TValue> result = new ArrayList<TValue>();
        for (Node node : (Iterable<Node>) this)
            msArrayList.add(result, (TValue)node);
        return result;
    }

    /// <summary>
    /// Gets the next matching node count times. If count is positive traverses forward.
    /// If negative traverses backward.
    /// </summary>
    /// <remarks>
    /// Exposed for unit-testing.
    /// </remarks>
    Node getNthMatchingNode(Node startNode, int count) throws Exception
    {
        //Decide whether we traverse forward of backward
        boolean isForward = (count > 0);

        //Can normalize count to positive now.
        if (count < 0)
            count = -count;

        Node node = startNode;
        for (int i = 0; i < count; i++)
        {
            node = findMatchingNode(node, isForward);
            if (node == null)
                return null;
        }
        return node;
    }

    /// <summary>
    /// <para>Gets the next matching node in the collection forward from the specified node,
    /// see <see cref="GetMatchingNode(Node, bool)"/>.</para>
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Node /*INodeCollection.*/getNextMatchingNode(Node curNode) throws Exception
    {
        return getMatchingNode(curNode, true);
    }

    /// <summary>
    /// Gets the next matching node in the collection forward or backward from the specified node,
    /// stores it in <see cref="mCurrent"/> - can be get by <see cref="INodeCollection.GetCurrentNode"/> . 
    /// Returns a node in the collection's container
    /// (maybe outside of the collection), visited before the found matching one.
    /// </summary>
    /// <param name="curNode">A starting node in the container (maybe outside of the collection).
    /// <c>node == this.Container</c> means search from the beginning of the collection.</param>
    /// <param name="isForward">Whether step is forward.</param>
    private Node getMatchingNode(Node curNode, boolean isForward) throws Exception
    {
        Debug.assertTrue(curNode != null);
        mCurrent = curNode;
        Node prevVisited;

        do
        {
            prevVisited = mCurrent;
            // WORDSNET-27401 We shall not skip the markup nodes in case of the subobjects of the parent markup node.
            boolean isSkipMarkupNodes = NodeUtil.isSkipMarkupNodesInFlatEnumeration(mContainer.getNodeType()) &&
                                     mMatcher.isSkipMarkupNodes();

            mCurrent =
                (mIsDeep) ? traverseDeep(isForward, mCurrent) :
                (isSkipMarkupNodes) ? traverseFlatSkipMarkupNodes(isForward, mCurrent) :
                traverseFlat(isForward, mCurrent);
        }
        while ((mCurrent != null) && !mMatcher.isMatch(mCurrent));

        return prevVisited;
    }

    /// <summary>
    /// This is a copycat of the <see cref="GetMatchingNode(Node, bool)"/> method but without ref keyword.
    /// It is created to prevent creating of too many Ref objects on Java.
    /// See WORDSJAVA-2279.
    /// </summary>
    private Node findMatchingNode(Node curNode, boolean isForward) throws Exception
    {
        Debug.assertTrue(curNode != null);

        do
        {
            curNode =
                (mIsDeep) ? traverseDeep(isForward, curNode) :
                (mMatcher.isSkipMarkupNodes()) ? traverseFlatSkipMarkupNodes(isForward, curNode) :
                traverseFlat(isForward, curNode);
        }
        while ((curNode != null) && !mMatcher.isMatch(curNode));

        return curNode;
    }

    /// <summary>
    /// Deep (preorder) tree traversal.
    /// </summary>
    private Node traverseDeep(boolean isForward, Node node)
    {
        return (isForward) ? node.nextPreOrder(mContainer) : node.previousPreOrder(mContainer);
    }

    /// <summary>
    /// Simple flat sibling traversal.
    /// </summary>
    private Node traverseFlat(boolean isForward, Node node)
    {
        if (isForward)
            return (node == mContainer) ? mContainer.getFirstChild() : node.getNextSibling();
        else
            return (node == mContainer) ? mContainer.getLastChild() : node.getPreviousSibling();
    }

    /// <summary>
    /// Flat sibling traversal.
    /// Takes markup nodes into the account by reading their content and skipping them. 
    /// </summary>
    private Node traverseFlatSkipMarkupNodes(boolean isForward, Node node)
    {
        Node resultNode;

        if (isForward)
        {
            resultNode = (node == mContainer) ? mContainer.getFirstNonMarkupDescendant() : node.getNextNonMarkupNodeLimited();
        }
        else
        {
            resultNode = (node == mContainer) ? mContainer.getLastNonMarkupDescendant() : node.getPreviousNonMarkupNodeLimited();
        }

        // WORDSNET-15251 It's a sibling traversal, so we should stay on a descendant's line.
        if ((resultNode == mContainer.getNextSibling()) || (resultNode == mContainer.getPreviousSibling()))
            resultNode = null;

        return resultNode;
    }

    /// <summary>
    /// Invalidates a collection if the document has changed since last time the collection was accessed.
    /// </summary>
    private void checkInvalidate()
    {
        if (mInitialChangeCount != getDocumentTreeChangeCount())
            invalidate();
    }

    /// <summary>
    /// Invalidates a collection so getting item by index next time will start without cache.
    /// </summary>
    private void invalidate()
    {
        mInitialChangeCount = getDocumentTreeChangeCount();
        mCurIndex = -1;
        mCurNode = mContainer;
        mCount = -1;
    }

    private int getDocumentTreeChangeCount()
    {
        // mDocument is null in case when mContainer is EmptyCompositeNode so let's handle this properly.
        return (mDocument != null) ? mDocument.getTreeChangeCount() : 0;
    }

    /// <summary>
    /// Gets the number of nodes in the collection.
    /// </summary>
    
    public int getCount()
    {
        checkInvalidate();

        if (mCount == -1)
            mCount = NodeCollectionEnumerator.getCount(this);

        return mCount;
    }

    @ReservedForInternalUse @Deprecated
    
    public Node /*INodeCollection.*/getCurrentNode()
    {
        return mCurrent;
    }

    /// <summary>
    /// Gets a container (the root node) of the collection.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public 
    CompositeNode /*INodeCollection.*/getContainer() { return mContainer; }

    private Node mCurrent;
    private /*final*/ CompositeNode mContainer;
    private /*final*/ DocumentBase mDocument;
    private /*final*/ boolean mIsDeep;

    /// <summary>
    /// Caches the initial "version" of the DOM tree. Used to invalidate the collection.
    /// </summary>
    private int mInitialChangeCount;
    /// <summary>
    /// Part of speed optimization.
    /// -1 means the starting point for a new search round
    /// </summary>
    private int mCurIndex;
    /// <summary>
    /// Part of speed optimization.
    /// if set to rootNode, means the starting point for a new search round
    /// </summary>
    private Node mCurNode;
    /// <summary>
    /// Part of speed optimization.
    /// -1 means count needs to be calculated
    /// </summary>
    private int mCount;
    /// <summary>
    /// Matcher providing the select pattern for nodes.
    /// </summary>
    private /*final*/ NodeMatcher mMatcher;
}

