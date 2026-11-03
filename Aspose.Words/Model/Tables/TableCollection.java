// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Provides typed access to a collection of <see cref="Table"/> nodes.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
public class TableCollection extends NodeCollection<Table>
{
    TableCollection(CompositeNode parent)
    {
    	super(parent, NodeType.TABLE, false);
	
    }

    /// <summary>
    /// Retrieves a <see cref="Table"/> at the given index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public /*new*/ Table get(int index) { return (Table)super.get(index); }

    /// <summary>
    /// Copies all tables from the collection to a new array of tables.
    /// </summary>
    /// <returns>An array of tables.</returns>
    public /*new*/ Table[] toArray()
    {
        return msArrayList.toArray(this.<Table>toList(), new Table[0]);
    }
}

