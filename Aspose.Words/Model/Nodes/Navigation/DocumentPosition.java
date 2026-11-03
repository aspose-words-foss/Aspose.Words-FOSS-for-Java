// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Michael Morozoff

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;


/// <summary>
/// Represents a position in the document tree.
/// </summary>
class DocumentPosition implements Cloneable
{
    /// <summary>
    /// Initializes new instance of this class.
    /// </summary>
    DocumentPosition(Node node)
    {
    	this(node, 0);
	
    }

    /// <summary>
    /// Initializes new instance of this class.
    /// </summary>
    DocumentPosition(Node node, int offset)
    {
    	this(node, offset, new DefaultAccessor());
	
    }

    /// <summary>
    /// Initializes new instance of this class.
    /// </summary>
    DocumentPosition(Node node, int offset, IDocumentTreeAccessor accessor)
    {
        Debug.assertTrue(accessor != null);
        Debug.assertTrue(offset >= 0);

        mNode = node;
        mAccessor = accessor;
        setOffset(offset);
        resetLength();
    }

    /// <summary>
    /// Returns a position before the specified node.
    /// </summary>
    /// <param name="node"></param>
    /// <returns></returns>
    static DocumentPosition createPositionBefore(Node node)
    {
        DocumentPosition position = new DocumentPosition(node);
        position.moveNodeStart();
        return position;
    }

    /// <summary>
    /// Returns a position after the specified node.
    /// </summary>
    /// <param name="node"></param>
    /// <returns></returns>
    static DocumentPosition createPositionAfter(Node node)
    {
        DocumentPosition position = new DocumentPosition(node);
        position.moveNodeEnd();
        return position;
    }

    /// <summary>
    /// Gets or sets node.
    /// </summary>
    Node getNode() { return mNode; }
    void setNode(Node value)
    {
        mNode = value;

        // Reset cached length of the node.
        resetLength();
    }

    /// <summary>
    /// Gets or sets offset into the node.
    /// </summary>
    /// <remarks>If node is composite valid values are 0 (before the node) and 1 (after the node).</remarks>
    /// <remarks>Cannot normalize value in setter because mNode may be null.</remarks>
    int getOffset() { return mOffset; }; void setOffset(int value) { mOffset = value; };

    private int mOffset;

    /// <summary>
    /// Returns true if the position is before the node.
    /// </summary>
    boolean isStart() { return (0 >= getOffset()); }

    /// <summary>
    /// Returns true if the position is after the node.
    /// </summary>
    boolean isEnd() { return getLength() <= getOffset(); }

    /// <summary>
    /// Returns true if node is null.
    /// </summary>
    boolean isVoid() { return (null == getNode()); }

    /// <summary>
    /// Moves to the start of the <see cref="Node"/>.
    /// </summary>
    void moveNodeStart()
    {
        setOffset(0);
    }

    /// <summary>
    /// Moves to the end of the <see cref="Node"/>.
    /// </summary>
    void moveNodeEnd()
    {
        setOffset(getLength());
    }

    /// <summary>
    /// Moves to the next position.
    /// </summary>
    /// <param name="root">Root of the scope subtree. Position will not go above the root node.</param>
    /// <param name="isForward">True if position moves to the next node (to the right); False if moves to the previous node (to the left).</param>
    /// <param name="isDeep">True if position stops at child nodes of composites; False if it doesn't descend composites.</param>
    /// <param name="isGlobal">True if position moves between stories; False if position moves within the current story.</param>
    /// <param name="isInside">True if position stops at inner offsets of the runs.</param>
    /// <param name="isAround">True if position stops before and after the node; False if position stops before the node.</param>
    /// <returns>True if moved successfully; False otherwise.</returns>
    boolean move(
        Node root,
        boolean isForward,
        boolean isDeep,
        boolean isGlobal,
        boolean isInside,
        boolean isAround)
    {
        return move(root, isForward, isDeep, isGlobal, isInside, isAround, null);
    }

    /// <summary>
    /// Moves to the next position.
    /// </summary>
    /// <param name="root">Root of the scope subtree. Position will not go above the root node.</param>
    /// <param name="isForward">True if position moves to the next node (to the right); False if moves to the previous node (to the left).</param>
    /// <param name="isDeep">True if position stops at child nodes of composites; False if it doesn't descend composites.</param>
    /// <param name="isGlobal">True if position moves between stories; False if position moves within the current story.</param>
    /// <param name="isInside">True if position stops at inner offsets of the runs.</param>
    /// <param name="isAround">True if position stops before and after the node; False if position stops before the node.</param>
    /// <param name="listener">An object that receives notifications about movements.</param>
    /// <returns>True if moved successfully; False otherwise.</returns>
    boolean move(
        Node root,
        boolean isForward,
        boolean isDeep,
        boolean isGlobal,
        boolean isInside,
        boolean isAround,
        IDocumentPositionListener listener)
    {
        // First move.
        if (isVoid())
        {
            // If node is not specified and we have no root then we cannot move.
            if (null == root)
                throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "root");

            setNode(root);
            setOffset((isForward ? 0 : getLength()));
            return true;
        }

        // Normalize offset.
        setOffset(Math.min(getLength(), Math.max(0, getOffset())));

        /*DocumentPositionMovement*/int movement = DocumentPositionMovement.NONE;

        if (moveInside(isForward, isInside, isAround))
            movement = DocumentPositionMovement.INSIDE;
        else if (moveBelow(root, isForward, isDeep, isGlobal))
            movement = DocumentPositionMovement.BELOW;
        else if (moveStartEnd(isForward, isAround))
            movement = DocumentPositionMovement.START_END;
        else if (moveSibling(root, isForward))
            movement = DocumentPositionMovement.SIBLING;
        else if (moveAbove(root, isForward, isDeep, isGlobal, isInside, isAround))
            movement = DocumentPositionMovement.ABOVE;

        if (movement != DocumentPositionMovement.NONE)
        {
            if (listener != null)
                listener.notifyMoved(movement);
            return true;
        }
        else
        {
            return false;
        }
    }

    /// <summary>
    /// </summary>
    /// <param name="isByNode">If True then doesn't stop inside runs.</param>
    boolean moveNext(boolean isByNode)
    {
        boolean result = move(null, true, true, true, !isByNode, true);

        if (result && !isEndOfNonComposite())
        {
            // Move over a non-composite end.
            result = move(null, true, true, true, !isByNode, true);
        }

        return result;
    }

    /// <summary>
    /// Gets a boolean value indicating if the current position is at the end of a non-composite node.
    /// </summary>
    /// <remarks>
    /// Empty runs have both end and start at offset 0.
    /// The method will return true for 0 offset of empty runs.
    /// </remarks>
    boolean isEndOfNonComposite() { return !getNode().isComposite() && isEnd(); }

    /// <summary>
    /// Returns true if this instance is logically equal to the specified one.
    /// </summary>
    boolean isEqual(DocumentPosition position)
    {
        if (position == null)
            return false;

        return (isSameNode(position)) && (getOffset() == position.getOffset());
    }

    /// <summary>
    /// Returns true if this position references the same node as the specified one.
    /// </summary>
    boolean isSameNode(DocumentPosition position)
    {
        if (position == null)
            return false;

        return (getNode() == position.getNode());
    }

    /// <summary>
    /// Returns a shallow copy of this <see cref="DocumentPosition"/>.
    /// </summary>
    /// <returns></returns>
    DocumentPosition deepClone()
    {
        return (DocumentPosition)memberwiseClone();
    }

    /// <summary>
    /// Checks and moves to either next or previous offset inside the node.
    /// </summary>
    private boolean moveInside(boolean isForward, boolean isInside, boolean isAround)
    {
        if (getNode() instanceof CompositeNode)
            return false;

        if (!isInside)
            return false;

        if (isForward)
        {

            if (getOffset() + (isAround ? 1 : 2) > getLength())
                return false;

            setOffset(getOffset() + 1)/*Property++*/;
        }
        else
        {
            if (getOffset() - (isAround ? 1 : 2) < 0)
                return false;

            setOffset(getOffset() - 1)/*Property--*/;
        }

        return true;
    }

    /// <summary>
    /// Checks and moves to either first or last child of the node.
    /// </summary>
    private boolean moveBelow(Node root, boolean isForward, boolean isDeep, boolean isGlobal)
    {
        if (!isDeep)
            return false;

        if (!(isForward && isStart() || !isForward && isEnd()))
            return false;

        // Don't descend into other story, except if it is root node.
        if (!isGlobal && getNode() != root && NodeUtil.isStoryNodeType(getNode()))
            return false;

        if (!getNode().isComposite())
            return false;

        CompositeNode oldNode = (CompositeNode)getNode();

        if (mAccessor.firstChild(oldNode) == null)
            return false;

        setNode((isForward ? mAccessor.firstChild(oldNode) : mAccessor.lastChild(oldNode)));
        setOffset((isForward ? 0 : getLength()));

        return true;
    }

    /// <summary>
    /// Checks and moves to either start of end of the node.
    /// </summary>
    private boolean moveStartEnd(boolean isForward, boolean isAround)
    {
        if (!isAround)
            return false;

        if (isForward)
        {
            if (isEnd())
                return false;

            moveNodeEnd();
        }
        else
        {
            if (isStart())
                return false;

            moveNodeStart();
        }

        return true;
    }

    /// <summary>
    /// Checks and moves to either next or previous sibling node.
    /// </summary>
    private boolean moveSibling(Node root, boolean isForward)
    {
        if (root == getNode())
            return false;

        if (isForward && null != mAccessor.nextSibling(getNode()))
        {
            setNode(mAccessor.nextSibling(getNode()));
            setOffset(0);
            return true;
        }

        if (!isForward && null != mAccessor.previousSibling(getNode()))
        {
            setNode(mAccessor.previousSibling(getNode()));
            setOffset(getLength());
            return true;
        }

        return false;
    }

    /// <summary>
    /// Checks and moves above.
    /// </summary>
    private boolean moveAbove(Node root, boolean isForward, boolean isDeep, boolean isGlobal, boolean isInside, boolean isAround)
    {
        if (getNode() == root)
            return false;

        Node parent = mAccessor.parentNode(getNode());

        if (null == parent)
            return false;

        if (!isGlobal && root != parent && NodeUtil.isStoryNodeType(parent))
            return false;

        setNode(parent);
        setOffset((isForward ? getLength() : 0));

        return (isAround || move(root, isForward, isDeep, isGlobal, isInside, false));
    }

    /// <summary>
    /// Gets length of the current node.
    /// </summary>
    private int getLength()
    {
        // This will always calculate zero length for Zlns since in structs cannot initialize mLength to -1.
        if (mLength == NOT_INITIALIZED)
        {
            // WORDSNET-15827 Set non-zero length for run with “Ruby" to avoid wrong positioning in the document.
            if ((NodeType.RUN == getNode().getNodeType()) && !((Run)getNode()).getRunPr().contains(FontAttr.RUBY))
                mLength = getNode().getTextLength();
            else
                mLength = 1;
        }
        return mLength;
    }

    void resetLength()
    {
        mLength = NOT_INITIALIZED;
    }

    /// <summary>
    /// Interface for document tree access.
    /// </summary>
    interface IDocumentTreeAccessor
    {
        public CompositeNode parentNode(Node node);
        public Node nextSibling(Node node);
        public Node previousSibling(Node node);
        public Node firstChild(Node node);
        public Node lastChild(Node node);
    }

    private static class DefaultAccessor implements IDocumentTreeAccessor
    {
        public CompositeNode parentNode(Node node) { return node.getParentNode(); }

        public Node firstChild(Node node)
        {
            CompositeNode composite = ms.as(node, CompositeNode.class);
            return composite != null? composite.getFirstChild() : null;
        }

        public Node lastChild(Node node)
        {
            CompositeNode composite = ms.as(node, CompositeNode.class);
            return composite != null? composite.getLastChild() : null;
        }

        public Node nextSibling(Node node) { return node.getNextSibling(); }

        public Node previousSibling(Node node) { return node.getPreviousSibling(); }
    }

    static /*final*/ DocumentPosition VOID = new DocumentPosition(null);
    private /*final*/ IDocumentTreeAccessor mAccessor;
    private static final int NOT_INITIALIZED = -1;
    private Node mNode;
    private int mLength;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

}

