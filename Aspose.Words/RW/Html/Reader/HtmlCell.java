// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a table cell during import of an HTML table.
/// </summary>
class HtmlCell
{
    /// <summary>
    /// Ctor.
    /// </summary>
    HtmlCell(HtmlElementNode node, CssBoxBorders cssBorders)
    {
    	this(cssBorders);
	
        Debug.assertTrue(node != null);
        mNode = node;
    }

    /// <summary>
    /// Creates a cell that represents a merged HTML cell.
    /// </summary>
    HtmlCell(CssBoxBorders cssBorders)
    {
        mCssBorders = cssBorders;
        mColSpan = 1;
        mRowSpan = 1;
    }

    /// <summary>
    /// Returns the HTML TH or TD node that this cell represents. Null for merged cells.
    /// </summary>
    HtmlElementNode getNode() { return mNode; }

    /// <summary>
    /// CSS borders of the table cell.
    /// </summary>
    CssBoxBorders getCssBorders() { return mCssBorders; }

    /// <summary>
    /// Gets/sets the merge option that is used to create an imported cell.
    /// </summary>
    /*CellMerge*/int getHorizontalMerge() { return mHorizontalMerge; }
    void setHorizontalMerge(/*CellMerge*/int value) { mHorizontalMerge = value; }

    /// <summary>
    /// Gets/sets the merge option that is used to create an imported cell.
    /// </summary>
    /*CellMerge*/int getVerticalMerge() { return mVerticalMerge; }
    void setVerticalMerge(/*CellMerge*/int value) { mVerticalMerge = value; }

    /// <summary>
    /// Gets/sets the first horizontal merge cell if this cell is a subsequent in the chain or null otherwise.
    /// </summary>
    HtmlCell getHorizontalMergeFirstCell() { return mHorizontalMergeFirstCell; }
    void setHorizontalMergeFirstCell(HtmlCell value) { mHorizontalMergeFirstCell = value; }

    /// <summary>
    /// Gets/sets the first vertical merge cell if this cell is a subsequent in the chain or null otherwise.
    /// </summary>
    HtmlCell getVerticalMergeFirstCell() { return mVerticalMergeFirstCell; }
    void setVerticalMergeFirstCell(HtmlCell value) { mVerticalMergeFirstCell = value; }

    /// <summary>
    /// The number of cols (adjacent cells to the right) this cell is to span. Default is one.
    /// </summary>
    int getColSpan() { return mColSpan; }
    void setColSpan(int value)
    {
        Debug.assertTrue(value >= 1);
        mColSpan = value;
    }

    /// <summary>
    /// The number of COL elements the cell is to span. Default is one.
    /// </summary>
    /// <remarks>
    /// In most cases this value equals <see cref="ColSpan"/> but unlike <see cref="ColSpan"/> this value doesn't get
    /// decreased on last cells in a table row that span extra empty cells.
    /// </remarks>
    int getUnrestrictedColSpan() { return mUnrestrictedColSpan; }; void setUnrestrictedColSpan(int value) { mUnrestrictedColSpan = value; };

    private int mUnrestrictedColSpan;

    /// <summary>
    /// The number of rows the cell is to span. Default is one.
    /// </summary>
    int getRowSpan() { return mRowSpan; }
    void setRowSpan(int value)
    {
        Debug.assertTrue(value >= 1);
        mRowSpan = value;
    }

    /// <summary>
    /// Gets/sets the corresponding model cell.
    /// </summary>
    Cell getModelCell() { return mModelCell; }
    void setModelCell(Cell value) { mModelCell = value; }

    private /*final*/ HtmlElementNode mNode;
    private /*final*/ CssBoxBorders mCssBorders;
    private /*CellMerge*/int mHorizontalMerge;
    private /*CellMerge*/int mVerticalMerge;
    private HtmlCell mHorizontalMergeFirstCell;
    private HtmlCell mVerticalMergeFirstCell;
    private int mColSpan;
    private int mRowSpan;
    private Cell mModelCell;
}

