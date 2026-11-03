// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/11/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Provides typed access to a collection of <see cref="Shape"/> nodes.
/// </summary>
class ShapeCollection extends NodeCollection<Shape>
{
    ShapeCollection(CompositeNode parent)
    {
    	super(parent, NodeType.SHAPE, true);
	
    }

    /// <summary>
    /// Retrieves a <b>Shape</b> at the given index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public /*new*/ Shape get(int index) { return (Shape)super.get(index); }

    /// <summary>
    /// Copies all shapes from the collection to a new array of shapes.
    /// </summary>
    /// <returns>An array of shapes.</returns>
    public /*new*/ Shape[] toArray()
    {
        return msArrayList.toArray(this.<Shape>toList(), new Shape[0]);
    }
}

