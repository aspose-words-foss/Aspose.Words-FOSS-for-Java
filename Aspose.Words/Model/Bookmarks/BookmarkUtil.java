// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/12/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;


/// <summary>
/// Provides methods to work with bookmarks.
/// </summary>
/*static*/ class BookmarkUtil
{
	/* Simulation of static class by using private constructor */
	private BookmarkUtil()
	{}

    /// <summary>
    /// Gets the top left cell of area of the specified column bookmark.
    /// </summary>
    static Cell getColumnBookmarkFirstCell(BookmarkStart start, BookmarkEnd end)
    {
        Table table = getColumnBookmarkTable(start, end);
        if (table == null)
            return null;

        // A column bookmark start is usually located at the beginning of the first paragraph of the first cell of
        // a row. If there is a child table in the first cell, MS Word places a bookmark start to its first cell.
        // If there is a row-level SDT, a bookmark start is located before the top-most SDT (displaced by it).

        Row row = null;
        if (start.getNodeLevel() == NodeLevel.ROW)
        {
            // The bookmark start is before row or row-level SDT.
            row = ms.as(start.getNextNonMarkupCompositeLimited(), Row.class);
        }
        else
        {
            do
            {
                Node node = ((Node)row != null ? (Node)row : (Node)start);
                row = (Row)node.getAncestor(NodeType.ROW);
            }
            while ((row != null) && (row.getParentTable() != table));
        }

        if (row == null)
            return null;

        int column = (start.getLastColumn() >= start.getFirstColumn()) ? start.getFirstColumn() : start.getLastColumn();
        return (column < row.getCells().getCount()) ? row.getCells().get(column) : null;
    }

    /// <summary>
    /// Gets the bottom right cell of area of the specified column bookmark.
    /// </summary>
    static Cell getColumnBookmarkLastCell(BookmarkStart start, BookmarkEnd end)
    {
        Table table = getColumnBookmarkTable(start, end);
        if (table == null)
            return null;

        // A column bookmark end is usually located at the row level after the bookmark last row.
        // Old versions of AW placed such bookmark end to the next paragraph, i.e. to the first paragraph of the
        // first cell of the next row or to the first paragraph after the table. 

        Row row = null;
        if (!end.isAncestorNode(table))
        {
            // The bookmark end is after the table, as the old versions of AW placed it.
            row = table.getLastRow();
        }
        else
        {
            Node node = end;
            do
            {
                Node prevNode = node.getPreviousNonMarkupNodeLimited();
                while ((prevNode != null) && NodeUtil.isCrossStructureAnnotation(prevNode))
                    prevNode = prevNode.getPreviousNonMarkupNodeLimited();

                Row prevRow = ms.as(prevNode, Row.class);
                if ((prevRow != null) && (prevRow.getParentTable() == table))
                {
                    row = prevRow;
                    break;
                }

                node = node.getParentNode();
            }
            while ((node != null) && (node != table));
        }

        if (row == null)
            return null;

        int column = (start.getLastColumn() >= start.getFirstColumn()) ? start.getLastColumn() : start.getFirstColumn();
        return (column < row.getCells().getCount()) ? row.getCells().get(column) : null;
    }

    /// <summary>
    /// Gets the table to which the specified column bookmark relates.
    /// </summary>
    private static Table getColumnBookmarkTable(BookmarkStart start, BookmarkEnd end)
    {
        Node ancestor = Node.getCommonAncestor(start, end);
        if (ancestor.getNodeType() == NodeType.ROW)
            return ((Row)ancestor).getParentTable();

        if (ancestor.getNodeType() == NodeType.TABLE)
            return (Table)ancestor;

        // A bookmark end may be outside of a table. But a bookmark start is always located inside a bookmarked table.
        // Therefore, get the topmost ancestor table of the bookmark start, which is a descendant of the common
        // ancestor. Always take the nearest ancestor table as a fallback for unconventional bookmark nodes positions.

        Table bookmarkTable = (Table)start.getAncestor(NodeType.TABLE);
        if (bookmarkTable == null)
            return null;

        while (true)
        {
            Table table = (Table)bookmarkTable.getAncestor(NodeType.TABLE);
            if ((table != null) && table.isAncestorNode(ancestor))
                bookmarkTable = table;
            else
                break;
        }
        
        return bookmarkTable;
    }
}

