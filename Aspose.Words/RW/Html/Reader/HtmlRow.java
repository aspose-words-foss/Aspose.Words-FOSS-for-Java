// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Collection of cells during HTML table import.
/// </summary>
class HtmlRow
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="node">Table row element.</param>
    /// <param name="rowDeclarations">Row element CSS declarations.</param>
    /// <param name="rowGroup">HTML row group of the row.</param>
    HtmlRow(HtmlElementNode node, CssDeclarationCollection rowDeclarations, HtmlRowGroup rowGroup)
    {
        Debug.assertTrue(node != null);
        Debug.assertTrue(rowDeclarations != null);
        Debug.assertTrue(rowGroup != null);

        mNode = node;
        mRowGroup = rowGroup;

        mCssBorders = CssBoxBorders.createBorders(rowDeclarations, false);
        mMaxCellHeightInRow = -Double.MAX_VALUE;
        mDeclarations = rowDeclarations;
        mCells = new ArrayList<HtmlCell>();
    }

    void add(HtmlCell cell)
    {
        msArrayList.add(mCells, cell);
    }

    void removeLastCell()
    {
        int index = mCells.size() - 1;
        if (index >= 0)
        {
            mCells.remove(index);
        }
    }

    int getColumnCountWithoutTrailingEmptyExtraCells()
    {
        int trailingEmptyCellCount = 0;
        for (int i = mCells.size() - 1; (i >= 0) && isEmpty(mCells.get(i)); i--)
        {
            ++trailingEmptyCellCount;
        }

        return mCells.size() - trailingEmptyCellCount;
    }

    HtmlCell get(int index) { return mCells.get(index); }
    void set(int index, HtmlCell value) { mCells.set(index, value); }

    HtmlElementNode getNode() { return mNode; }

    /// <summary>
    /// Maximum cell height in the row; <c>double.MinValue</c> if there are no height specified for the row's cells.
    /// </summary>
    double getMaxCellHeightInRow() { return mMaxCellHeightInRow; }
    void setMaxCellHeightInRow(double value) { mMaxCellHeightInRow = value; }

    /// <summary>
    /// CSS borders of the table row.
    /// </summary>
    CssBoxBorders getCssBorders() { return mCssBorders; }

    /// <summary>
    /// Gets HTML row group of the row.
    /// </summary>
    HtmlRowGroup getRowGroup() { return mRowGroup; }

    /// <summary>
    /// CSS declarations of the table row.
    /// </summary>
    CssDeclarationCollection getDeclarations() { return mDeclarations; }

    /// <summary>
    /// Gets the number of cells in this row, as declared in the HTML document.
    /// </summary>
    int getCellCount() { return mCells.size(); }

    /// <summary>
    /// Gets the number of cells in this row, as declared in the HTML document, but no greater than the max number
    /// of columns allowed by the document model.
    /// </summary>
    int getModelCellCount() { return Math.min(getCellCount(), Table.MAX_COLUMNS); }

    private static boolean isEmpty(HtmlCell cell)
    {
        // Vertically merged cells are also treated as non-empty and are not affected by the fix applied in WORDSNET-20250
        // We've added this restriction in order to preserve behavior that had been covered by tests.
        return (cell == null) ||
            ((cell.getNode() == null) && (cell.getVerticalMerge() == CellMerge.NONE));
    }

    private double mMaxCellHeightInRow;
    private /*final*/ HtmlElementNode mNode;
    private /*final*/ CssBoxBorders mCssBorders;
    private /*final*/ HtmlRowGroup mRowGroup;
    private /*final*/ CssDeclarationCollection mDeclarations;
    private /*final*/ ArrayList<HtmlCell> mCells;
}

