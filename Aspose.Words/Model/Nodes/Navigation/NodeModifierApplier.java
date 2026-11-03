// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/05/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Applies an <see cref="INodeModifier"/> instance to a node range in the same manner as <see cref="NodeCopier"/>.
/// The difference is that source nodes are not copied before being modified.
/// </summary>
/// <remarks>
/// If the corresponding node range starts/ends inside a run, then this run is cloned before being modified.
/// </remarks>
class NodeModifierApplier extends NodeTraverser
{
    /// <summary>
    /// Ctor.
    /// </summary>
    NodeModifierApplier(NodeRange range, INodeModifier modifier)
    {
    	super(range);
	
        mModifier = modifier;
    }

    /// <summary>
    /// Applies the modifier to the node range.
    /// </summary>
    void applyModifier() throws Exception
    {
        traverse();
    }

    protected /*override*/ void onNonCompositeNode() throws Exception
    {
        processCurrentNode();
    }

    protected /*override*/ void onMiddleNodeAncestor() throws Exception
    {
        processCurrentNode();
    }

    private void processCurrentNode() throws Exception
    {
        // Apply the modifier to the range through NodeEnumerator.ExtractCurrentNode()
        // as it correctly cuts runs and clones them if needed.
        extractRangeNode(getCurrentNode(), mModifier, null, false, true);
    }

    private /*final*/ INodeModifier mModifier;
}

