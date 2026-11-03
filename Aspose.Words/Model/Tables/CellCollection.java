// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Provides typed access to a collection of <see cref="Cell"/> nodes.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
public class CellCollection extends NodeCollection<Cell>
{
    CellCollection(CompositeNode parent)
    {
    	super(parent, NodeType.CELL, false);
	
    }

    /// <summary>
    /// Retrieves a <see cref="Cell"/> at the given index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public /*new*/ Cell get(int index) { return (Cell)super.get(index); }

    /// <summary>
    /// Copies all cells from the collection to a new array of cells.
    /// </summary>
    /// <returns>An array of cells.</returns>
    public /*new*/ Cell[] toArray()
    {
        return msArrayList.toArray(this.<Cell>toList(), new Cell[0]);
    }

    /// <summary>
    /// Returns true if any cell has format revision.
    /// </summary>
    boolean hasFormatRevision()
    {
        for (Cell cell : (Iterable<Cell>) this)
            if(cell.getCellPr().hasFormatRevision())
                return true;

        return false;
    }
}

