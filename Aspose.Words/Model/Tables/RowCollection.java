// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Provides typed access to a collection of <see cref="Row"/> nodes.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
public class RowCollection extends NodeCollection<Row>
{
    RowCollection(CompositeNode parent)
    {
    	super(parent, NodeType.ROW, false);
	
    }

    /// <summary>
    /// Retrieves a <see cref="Row"/> at the given index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public /*new*/ Row get(int index) { return (Row)super.get(index); }

    /// <summary>
    /// Copies all rows from the collection to a new array of rows.
    /// </summary>
    /// <returns>An array of rows.</returns>
    public /*new*/ Row[] toArray()
    {
        return msArrayList.toArray(this.<Row>toList(), new Row[0]);
    }
}

