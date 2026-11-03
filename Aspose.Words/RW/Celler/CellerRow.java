// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a table row in the celler table model, see CellerTable for more info.
/// </summary>
class CellerRow
{
    CellerRow(int columnCount, Row rowNode)
    {
        mCells = new CellerCell[columnCount];
        mRowNode = rowNode;
    }

    Row getRowNode() { return mRowNode; };

    private Row mRowNode;

    CellerCell[] getCells() { return mCells; };

    private CellerCell[] mCells;

    /// <summary>
    /// Indicates whether all cells of this row are vertically merged with cells of the previous row.
    /// </summary>
    boolean isMerged() { return mIsMerged; }; void isMerged(boolean value) { mIsMerged = value; };

    private boolean mIsMerged;

    /// <summary>
    /// Indicates how many merged rows are located above this row (including this row itself).
    /// </summary>
    int getMergedRowsAbove() { return mMergedRowsAbove; }; void setMergedRowsAbove(int value) { mMergedRowsAbove = value; };

    private int mMergedRowsAbove;

    /// <summary>
    /// The total height of this row and all adjacent merged rows right below it.
    /// </summary>
    double getMergedRowsHeight() { return mMergedRowsHeight; }; void setMergedRowsHeight(double value) { mMergedRowsHeight = value; };

    private double mMergedRowsHeight;

    /// <summary>
    /// Gets or sets a value indicating whether this row belongs to the table header.
    /// </summary>
    boolean isHeading() { return mIsHeading; }; void isHeading(boolean value) { mIsHeading = value; };

    private boolean mIsHeading;

    /// <summary>
    /// Gets or sets a value indicating whether this row is the first row in its row group (header or body).
    /// </summary>
    boolean isFirstInGroup() { return mIsFirstInGroup; }; void isFirstInGroup(boolean value) { mIsFirstInGroup = value; };

    private boolean mIsFirstInGroup;
}

