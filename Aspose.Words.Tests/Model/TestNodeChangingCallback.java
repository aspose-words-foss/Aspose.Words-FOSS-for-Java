// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2017 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Test implementation of <see cref="INodeChangingCallback"/>.
/// </summary>
public class TestNodeChangingCallback implements INodeChangingCallback
{
    /// <summary>
    /// Ctor.
    /// </summary>
    TestNodeChangingCallback()
    {
    	this(null);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="documentToUnscribe">
    /// If specified, then the created instance is removed from a chain of the given document
    /// on the first listened node insertion.
    /// </param>
    TestNodeChangingCallback(Document documentToUnscribe)
    {
        mDocumentToUnscribe = documentToUnscribe;
    }

    public void /*INodeChangingCallback.*/nodeInserting(NodeChangingArgs args)
    {
        // Do nothing.
    }

    public void /*INodeChangingCallback.*/nodeInserted(NodeChangingArgs args)
    {
        mInsertedCount++;

        // Unscribe from events if the document is specified.
        if (mDocumentToUnscribe != null)
        {
            if (mDocumentToUnscribe.getNodeChangingCallback() == this)
            {
                mDocumentToUnscribe.setNodeChangingCallback(null);
            }
            else
            {
                mDocumentToUnscribe.removeInternalNodeChangingCallback(this);
            }
        }
    }

    public void /*INodeChangingCallback.*/nodeRemoving(NodeChangingArgs args)
    {
        // Do nothing.
    }

    public void /*INodeChangingCallback.*/nodeRemoved(NodeChangingArgs args)
    {
        mRemovedCount++;
    }

    /// <summary>
    /// Gets the number of nodes which have been inserted while listening.
    /// </summary>
    int getInsertedCount() { return mInsertedCount; }

    /// <summary>
    /// Gets the number of nodes which have been removed while listening.
    /// </summary>
    int getRemovedCount() { return mRemovedCount; }

    private /*final*/ Document mDocumentToUnscribe;
    private int mInsertedCount;
    private int mRemovedCount;
}

