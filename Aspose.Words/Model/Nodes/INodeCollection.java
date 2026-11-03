// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/10/2012 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Base interface for node collections and composite nodes treated as node collections
/// for use by <see cref="NodeCollectionEnumerator{T}"/>.
/// </summary>
interface INodeCollection
{
    /// <summary>
    /// Uses 2 internal Nodes: Current and Previous.
    /// The next matching node in the collection forward from the specified node
    /// is stored in Current (can be retrieved by <see cref="GetCurrentNode"/>).
    /// Returns a Previous node - node in the collection's container (maybe outside 
    /// of the collection), visited before the found matching (Current) node.
    /// </summary>
    public Node getNextMatchingNode(Node curNode) throws Exception;

    /// <summary>
    /// Returns a node matched by <see cref="GetNextMatchingNode"/> - i.e. "Current" node.
    /// </summary>
    public Node getCurrentNode();

    /// <summary>
    /// Gets a container (the root node) of the collection.
    /// </summary>
    public CompositeNode getContainer();
}

