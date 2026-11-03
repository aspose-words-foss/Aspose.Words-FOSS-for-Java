// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/11/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import com.aspose.Debug;
import java.util.Iterator;


/// <summary>
/// A base class representing a node of a field update tree.
/// </summary>
/// <remarks>
/// A field update tree is composed of a <see cref="FieldUpdater"/> instance as its root node and
/// <see cref="FieldUpdateContext"/> instances as its branch and leaf nodes.
///
/// A <see cref="FieldUpdater"/> corresponds to a single field update session. A <see cref="FieldUpdateContext"/>
/// instance corresponds to a single updated field contained in a document.
///
/// That is a <see cref="FieldUpdater"/> instance contains <see cref="FieldUpdateContext"/> instances corresponding to
/// the topmost fields in the document. <see cref="FieldUpdateContext"/> instances in turn are nested to each other
/// exactly the same way as the corresponding fields are nested to each other's codes in the document.
/// </remarks>
abstract class FieldUpdateContextContainer implements Iterable<FieldUpdateContext>
{
    /// <summary>
    /// Inserts the specified child context to the given parent container before or after its specified child.
    /// This does not break any parent container enumeration if any.
    /// </summary>
    protected static void insertChild(
        FieldUpdateContextContainer parent,
        FieldUpdateContext refChild,
        FieldUpdateContext newChild,
        boolean isAfter)
    {
        // Ensure parent container.
        Debug.assertTrue((refChild == null) || (refChild.getParent() == parent));

        // Ensure that the child context is not/was not inserted to another container.
        Debug.assertTrue(newChild.getParent() == null);
        Debug.assertTrue(newChild.getNextSibling() == null);
        Debug.assertTrue(newChild.getPreviousSibling() == null);

        // Use the first or the last child if the reference child is missing.
        if (refChild == null)
            refChild = parent.getEdgeChild(isAfter);

        if (refChild == null)
        {
            // This is the first child.
            parent.setFirstChild(newChild);
            parent.mLastChild = newChild;
        }
        else
        {
            // The parent container already have children.
            refChild.relinkSibling(isAfter, newChild);

            newChild.setSibling(isAfter, refChild.getSibling(isAfter));
            newChild.setSibling(!isAfter, refChild);

            refChild.setSibling(isAfter, newChild);
        }

        newChild.setParent(parent);
    }

    /// <summary>
    /// Removes the given child context from its parent container. This does not break any parent container
    /// enumeration if any.
    /// </summary>
    protected static void removeChild(FieldUpdateContext child)
    {
        // Ensure the child context is belongs to a container.
        if (child.getParent() == null)
            return;

        child.notifyUpdateProgressCurrentFieldIsUpdated();

        // Change sibling references of the remained child contexts, but do not change sibling references of
        // the context being removed not to break any existing child context enumeration.
        child.relinkSibling(false, child.getNextSibling());
        child.relinkSibling(true, child.getPreviousSibling());

        // Reset the parent container of the removed context, so it could not be removed multiple times
        // (see the guard at the top).
        child.setParent(null);
    }

    /// <summary>
    /// Makes the next or the previous sibling of this context reference another context instead of this one.
    /// </summary>
    void relinkSibling(boolean isNext, FieldUpdateContext newSibling)
    {
        if (this == getParent().getEdgeChild(isNext))
        {
            getParent().setEdgeChild(isNext, newSibling);
        }
        else
        {
            getSibling(isNext).setSibling(!isNext, newSibling);
        }
    }

    /// <summary>
    /// Returns the next or the previous sibling of this context.
    /// </summary>
    FieldUpdateContext getSibling(boolean isNext)
    {
        return isNext ? getNextSibling() : getPreviousSibling();
    }

    /// <summary>
    /// Sets the next or the previous sibling of this context.
    /// </summary>
    void setSibling(boolean isNext, FieldUpdateContext newSibling)
    {
        if (isNext)
        {
            setNextSibling(newSibling);
        }
        else
        {
            setPreviousSibling(newSibling);
        }
    }

    /// <summary>
    /// Returns the first or the last child context of this container.
    /// </summary>
    private FieldUpdateContext getEdgeChild(boolean isLast)
    {
        return isLast ? mLastChild : getFirstChild();
    }

    /// <summary>
    /// Sets the first or the last child context of this container.
    /// </summary>
    private void setEdgeChild(boolean isLast, FieldUpdateContext newChild)
    {
        if (isLast)
        {
            mLastChild = newChild;
        }
        else
        {
            setFirstChild(newChild);
        }
    }

    /// <summary>
    /// Returns <see cref="IEnumerator"/> instance to iterate over child contexts within this container.
    /// Any enumeration is flexible for changes, i.e. you can remove the current context being enumerated
    /// or insert a new one right after it without any harm to the enumeration.
    /// </summary>
    public Iterator<FieldUpdateContext> iterator()
    {
        return new ChildContextEnumerator(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    void notifyUpdateProgressChildrenFieldsAreUpdated()
    {
        for (FieldUpdateContext child : this)
        {
            child.notifyUpdateProgressCurrentFieldIsUpdated();
            child.notifyUpdateProgressChildrenFieldsAreUpdated();
        }
    }

    /// <summary>
    /// Gets the parent container for this container.
    /// </summary>
    
    FieldUpdateContextContainer getParent() { return mParent; }; 
    void setParent(FieldUpdateContextContainer value) { mParent = value; };

    private FieldUpdateContextContainer mParent;

    
    FieldUpdateContext getPreviousSibling() { return mPreviousSibling; }; 
    void setPreviousSibling(FieldUpdateContext value) { mPreviousSibling = value; };

    private FieldUpdateContext mPreviousSibling;

    FieldUpdateContext getNextSibling() { return mNextSibling; }; void setNextSibling(FieldUpdateContext value) { mNextSibling = value; };

    private FieldUpdateContext mNextSibling;

    /// <summary>
    /// Gets a value indicating whether this container has any children.
    /// </summary>
    boolean hasChildren() { return getFirstChild() != null; }

    FieldUpdateContext getFirstChild() { return mFirstChild; }; private void setFirstChild(FieldUpdateContext value) { mFirstChild = value; };

    private FieldUpdateContext mFirstChild;

    /// <summary>
    /// Implements <see cref="IEnumerator"/> to iterate over child contexts within the specified container.
    /// </summary>
    private final static class ChildContextEnumerator implements Iterator<FieldUpdateContext>
    {
        ChildContextEnumerator(FieldUpdateContextContainer parent)
        {
            mParent = parent;
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        public boolean /*IEnumerator.*/hasNext()
        {
            mCurrentChild = (mCurrentChild == null)
                ? mParent.getFirstChild()
                : mCurrentChild.getNextSibling();
            return (mCurrentChild != null);
        }

        public void /*IEnumerator.*/reset()
        {
            mCurrentChild = null;
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        public FieldUpdateContext next()
        {
            if (mCurrentChild == null)
                throw new IllegalStateException();

            return mCurrentChild;
        }

        private /*final*/ FieldUpdateContextContainer mParent;
        private FieldUpdateContext mCurrentChild;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    private FieldUpdateContext mLastChild;

}

