// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;

import java.util.*;
import java.util.List;

//This class is to be MANUALLY ported to Java.
//Java: java NodeList so differ from C# one because jaxen XPath selectNodes() returns ordinary java.util.List
//and this List used as backing for the NodeList.

/// <summary>
/// Represents a collection of nodes matching an XPath query executed using the <see cref="CompositeNode.SelectNodes"/> method.
/// </summary>
/// <remarks>
/// <p><b>NodeList</b> is returned by <see cref="CompositeNode.SelectNodes"/> and contains a collection
/// of nodes matching the XPath query.</p>
/// <p><b>NodeList</b> supports indexed access and iteration.</p>
/// <note>Treat the <b>NodeList</b> collection as a "snapshot" collection. <b>NodeList</b> starts
/// as a "live" collection because the nodes are not actually retrieved when the XPath query is run.
/// The nodes are only retrieved upon access and at this time the node and all nodes that precede
/// it are cached forming a "snapshot" collection.</note>
/// </remarks>
public class NodeList<T extends Node> implements Iterable<T>
{
	/// <summary>
	/// Ctor.
	/// </summary>
	protected NodeList(java.util.List<Node> nodeList)
	{
		this.nodeList = nodeList;
	}

	/// <summary>
	/// Retrieves a node at the given index.
	/// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
	/// <param name="index">An index into the list of nodes.</param>
	public Node get(int index)
	{
		if (index < 0)
		{
			//Access from the end, convert the index into positive index.
			index = getCount() + index;

			//If still out of range, return null as per interface requirements.
			if (index < 0)
				return null;
		}
		if (index >= nodeList.size())
			return null;

		return nodeList.get(index);
	}

	/// <summary>
	/// Gets the number of nodes in the list.
	/// </summary>
	public int getCount()
	{
		return nodeList.size();
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
		int count = getCount();
		Node[] result = new Node[count];
		for (int i = 0; i < count; i++)
			result[i] = this.get(i);

		return result;
	}

	/// <summary>
	/// Provides a simple "foreach" style iteration over the collection of nodes.
	/// </summary>
	/// <returns>An <ms>IEnumerator</ms><java>Iterator</java>.</returns>
	public Iterator iterator()
	{
		return nodeList.iterator();
	}

	private List<Node> nodeList;
}
