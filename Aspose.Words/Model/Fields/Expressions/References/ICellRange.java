// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// When implemented, designates a one-dimension or rectangular area of table cells.
/// </summary>
interface ICellRange extends Iterable<Cell>
{
    /// <summary>
    /// Gets whether the range is rectangular.
    /// </summary>
    public boolean isRectangular();

    /// <summary>
    /// Gets whether the range contains one cell.
    /// </summary>
    public boolean isOneCell();

    /// <summary>
    /// Gets the upper left position of the range.
    /// </summary>
    public CellPosition getStart();

    /// <summary>
    /// Gets the lower right position of the range.
    /// </summary>
    public CellPosition getEnd();

    /// <summary>
    /// Gets the value for empty cell.
    /// </summary>
    public Constant getEmptyCellValue();

    /// <summary>
    /// Gets whether the text in cells should be always evaluated or not.
    /// </summary>
    public boolean getAlwaysEvaluateCellText();
}

