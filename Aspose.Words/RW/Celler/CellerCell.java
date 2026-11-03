// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a table cell in the celler table model. See CellerTable for more info.
/// </summary>
class CellerCell
{
    /// <summary>
    /// Redesigned so the caller is to deal with meaningful function names, not constructors with different intentions.
    /// </summary>
    private CellerCell(CellPr cellPr, Cell cellNode)
    {
        mCellPr = cellPr;
        mCellNode = cellNode;
    }

    /// <summary>
    /// Creates a cell from a specified TC.
    /// Use this creator for cells that correspond to cells in the document.
    /// </summary>
    static CellerCell createNormalCell(CellPr cellPr, Cell cellNode)
    {
        // TODO 2 I don't like this. Clone because we will resolve default borders and don't want to modify original document.
        return new CellerCell(AttrCollectionExtension.deepClone(cellPr), cellNode);
    }

    /// <summary>
    /// Creates a horizontally merged cell from the specified TC.
    /// Use this creator for "extra" cells that resulted from the celler algorithm.
    /// </summary>
    static CellerCell createHorizontallyMergedCell(CellPr cellPr, Cell cellNode)
    {
        CellerCell cell = createNormalCell(cellPr, cellNode);

        CellPr cellPrDest = cell.mCellPr;
        cellPrDest.setHorizontalMerge(CellMerge.PREVIOUS);

        // Borders not cloned in the above constructor if they are in the inherited mode.
        // Another thing that looks like a hack and hard to explain why.
        cellPrDest.setBorderTop(cellPr.getBorderTop());
        cellPrDest.setBorderLeft(cellPr.getBorderLeft());
        cellPrDest.setBorderBottom(cellPr.getBorderBottom());
        cellPrDest.setBorderRight(cellPr.getBorderRight());
        cellPrDest.setBorderDiagonalDown(cellPr.getBorderDiagonalDown());
        cellPrDest.setBorderDiagonalUp(cellPr.getBorderDiagonalUp());

        return cell;
    }

    /// <summary>
    /// Creates an empty pad cell.
    /// Use this creator for cells that are used on the left and right of a row as padding.
    /// </summary>
    static CellerCell createPadCell()
    {
        return new CellerCell(new CellPr(), null);
    }

    CellPr getCellPr() { return mCellPr; }

    Cell getCellNode() { return mCellNode; }

    /// <summary>
    /// We can have some pad cells at the beginning and end of a row so we can align rows of the table vertically.
    /// </summary>
    boolean isPad() { return mCellNode == null; }

    int getColSpan() { return mColSpan; }
    void setColSpan(int value) { mColSpan = value; }

    int getRowSpan() { return mRowSpan; }
    void setRowSpan(int value) { mRowSpan = value; }

    /// <summary>
    /// Row span value excluding merged rows.
    /// </summary>
    int getUnmergedRowSpan() { return mUnmergedRowSpan; }
    void setUnmergedRowSpan(int value) { mUnmergedRowSpan = value; }

    private /*final*/ CellPr mCellPr;
    private int mColSpan = 1;
    private int mRowSpan = 1;
    private int mUnmergedRowSpan = 1;
    private /*final*/ Cell mCellNode;
}

