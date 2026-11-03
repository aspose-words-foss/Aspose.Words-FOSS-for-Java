// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.DrColor;
import com.aspose.DrFont;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.SortedIntegerListGeneric;
import java.util.ArrayList;
import java.util.Set;


/*enum*/ final class CellerTableOptions
{
    private CellerTableOptions(){}
    
    public static final int NONE = 0x00;

    /// <summary>
    /// Option to resolve inherited paddings on cells.
    /// </summary>
    public static final int INHERITED_PADDING = 0x01;

    /// <summary>
    /// Specifies that celler should visit merged cells when go to next cell.
    /// </summary>
    public static final int GOTO_MERGED_CELLS = 0x02;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CellerTableOptions value.
    /// </summary>
    public static String getName(/*CellerTableOptions*/int cellerTableOptions)
    {
    	switch (cellerTableOptions)
    	{
    		case NONE: return "NONE";
    		case INHERITED_PADDING: return "INHERITED_PADDING";
    		case GOTO_MERGED_CELLS: return "GOTO_MERGED_CELLS";
    		default: return "Unknown CellerTableOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer CellerTableOptions value.
    /// </summary>
    public static Set<String> getNames(/*CellerTableOptions*/int cellerTableOptions)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & cellerTableOptions) == NONE) names.add("NONE");
    	if ((INHERITED_PADDING & cellerTableOptions) == INHERITED_PADDING) names.add("INHERITED_PADDING");
    	if ((GOTO_MERGED_CELLS & cellerTableOptions) == GOTO_MERGED_CELLS) names.add("GOTO_MERGED_CELLS");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CellerTableOptions value.
    /// </summary>
    public static String toString(/*CellerTableOptions*/int cellerTableOptions)
    {
    	switch (cellerTableOptions)
    	{
    		case NONE: return "None";
    		case INHERITED_PADDING: return "InheritedPadding";
    		case GOTO_MERGED_CELLS: return "GotoMergedCells";
    		default: return toStringSet(cellerTableOptions);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CellerTableOptions value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by CellerTableOptions name.
    /// </summary>
    public static /*CellerTableOptions*/int fromName(String cellerTableOptionsName)
    {
    	if ("NONE".equals(cellerTableOptionsName)) return NONE;
    	if ("INHERITED_PADDING".equals(cellerTableOptionsName)) return INHERITED_PADDING;
    	if ("GOTO_MERGED_CELLS".equals(cellerTableOptionsName)) return GOTO_MERGED_CELLS;
    	throw new IllegalArgumentException("Unknown CellerTableOptions name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by CellerTableOptions names.
    /// </summary>
    public static /*CellerTableOptions*/int fromNames(Set<String> cellerTableOptionsNames)
    {
    	/*CellerTableOptions*/int result = 0;
    	for (String name : cellerTableOptionsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of CellerTableOptions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		INHERITED_PADDING,
    		GOTO_MERGED_CELLS,
    	};
    }

    public static final int length = 3;
}

/// <summary>
/// Converts a Word table into an intermediate model that is easier to write into PDF and HTML formats.
/// 
/// The main problem when converting DOC to HTML/PDF tables is that columns in different rows
/// in a DOC file do not have to align vertically and do not have to have the same number of cells.
/// This is not possible in HTML/PDf and the solution is to create more columns (for every vertical 
/// position found in the DOC file for all rows) and then create HTML cells that span more than one 
/// column if necessary. I call this algorithm "celler".
/// 
/// So this class cellerizes a document table into a CellerTable consisting of CellerCell objects.
/// Each CellerCell is set to have the correct row and col span and width. Also optionally resolves any
/// default table borders and borders for the pad cells.
/// 
/// Also, this class provides a mechanism to iterate over the cells.
/// Strictly speaking, iterator should be separate, but because we have to support nested tables,
/// it is easier to bundle the iterator right into here.
/// 
/// In the finished CellerTable there could be several types of CellerCells:
/// 
/// 1. Empty pad cells. These can occur at the beginning and end of a row, have IsPad = true, 
/// borders will be resolved to match adjacent cell borders.
///
/// 2. Normal non-merged cells that match cells in the document.
/// These will have merge attributes and their borders set to be the same as in the document cell.
///
/// 3. Normal merged cells that match cells in the document. These cells are merged to a previous 
/// cell vertically or horizontally and will normally be skipped by the output algorithm.
///
/// 4. Extra merged cells introduced by the celler algorithm. These cells do not match cell nodes in the 
/// document, always merged horizontally to the previous cell and are skipped by the output algorithm.
/// </summary>
class CellerTable
{
    /// <summary>
    /// Cellerizes the document table with default options.
    /// </summary>
    CellerTable(Table tableNode) { 	this(tableNode, CellerTableOptions.NONE);
	 }

    /// <summary>
    /// Cellerizes the document table.
    /// </summary>
    CellerTable(Table tableNode, /*CellerTableOptions*/int options)
    {
        mTableNode = tableNode;

        // AM. It seems all callers want to resolve borders and do not populate empty pad borders so I removed these options to simplicity.
        // If later someone need this options again it can be easily reverted.
        mResolveInheritedBorders = true;
        mPopulateEmptyPadBorders = false;

        mResolveInheritedPaddings = (options & CellerTableOptions.INHERITED_PADDING) != 0;
        mGotoMergedCells = (options & CellerTableOptions.GOTO_MERGED_CELLS) != 0;

        calcColumnPositions();
        if (getColumnCount() == 0)
            return;

        createCells();
        calcCellsH();
        calcCellsV();
        processMergedRows();

        if (mHasPadCells)
            updatePadCellsBorders();

        markRowGroups();
    }

    /// <summary>
    /// Selects the first row.
    /// </summary>
    void gotoFirstRow()
    {
        mCurRowIdx = 0;
    }

    /// <summary>
    /// Selects next row.
    /// </summary>
    void gotoNextRow()
    {
        mCurRowIdx++;
    }

    /// <summary>
    /// Select first cell in the row.
    /// </summary>
    void gotoFirstCell()
    {
        mCurColIdx = -1;
        gotoNextCell();
    }

    /// <summary>
    /// Selects next cell that is not horizontally merged to previous cells in the row.
    /// </summary>
    void gotoNextCell()
    {
        if (mGotoMergedCells)
        {
            mCurColIdx++;
        }
        else
        {
            do
            {
                mCurColIdx++;
            }
            while ((getCurCell() != null) && getCurCell().getCellPr().isMergedToPrevious());
        }
    }

    int getColumnWidth(int index)
    {
        return getColumnWidth(index, 1);
    }

    /// <summary>
    /// Gets width of several columns.
    /// </summary>
    private int getColumnWidth(int startIndex, int count)
    {
        Debug.assertTrue(count >= 0);
        return getColumnPosition(startIndex + count) - getColumnPosition(startIndex);
    }

    /// <summary>
    /// Returns an array containing the column widths.
    /// </summary>
    int[] getColumnWidths()
    {
        int[] result = new int[mColumnPositions.getCount() - 1];
        for (int i = 0; i < result.length; i++)
            result[i] = getColumnWidth(i);
        return result;
    }

    int getColumnPosition(int index)
    {
        return mColumnPositions.getKey(index);
    }

    Table getTableNode() { return mTableNode; }

    /// <summary>
    /// Gets total (cellerized) number of columns in the table.
    /// </summary>
    int getColumnCount() { return mColumnPositions.getCount() - 1; }

    int getTableWidth() { return getColumnWidth(0, getColumnCount()); }

    /// <summary>
    /// Returns current row or null if the cursor is beyond the last row in the table.
    /// </summary>
    CellerRow getCurRow() { return (mCurRowIdx < mRows.size()) ? mRows.get(mCurRowIdx) : null; }

    /// <summary>
    /// Returns current cell or null if the cursor is beyond the last cell in the row.
    /// </summary>
    CellerCell getCurCell() { return ((getCurRow() != null) && (mCurColIdx < getColumnCount())) ? getCurRow().getCells()[mCurColIdx] : null; }

    /// <summary>
    /// Creates an array of all vertical column positions for every row in the table.
    /// </summary>
    private void calcColumnPositions()
    {
        for (Row row = mTableNode.getFirstRow(); row != null; row = row.getNextRow())
        {
            // Add all rows' column positions to the sorted array as keys. 
            // I don't use the values in this sorted array (only use keys), so insert nulls.
            int x = getRowLeftOrigin(row);
            mColumnPositions.set(x, null);

            for (Cell cell : (Iterable<Cell>) row.getCells())
            {
                x += getCellWidth(cell);
                mColumnPositions.set(x, null);
            }
        }
    }

    /// <summary>
    /// Creates cells in such a way that allows us to handle columns that are not aligned vertically.
    /// </summary>
    private void createCells()
    {
        RowCollection rowNodes = mTableNode.getRows();
        int rowNodeCount = rowNodes.getCount();

        mHasRowWithoutMisalignedColumns = false;

        for (int rowNodeIdx = 0; rowNodeIdx < rowNodeCount; ++rowNodeIdx)
        {
            CellerRow row = new CellerRow(getColumnCount(), rowNodes.get(rowNodeIdx));
            msArrayList.add(mRows, row);

            createPadCells(row);

            CellCollection cellNodes = row.getRowNode().getCells();
            int cellNodeCount = cellNodes.getCount();

            boolean hasMisalignedColumns = false;

            // This loop is for the normal (non pad) cells.
            for (int cellNodeIdx = 0; cellNodeIdx < cellNodeCount; ++cellNodeIdx)
            {
                Cell curCellNode = cellNodes.get(cellNodeIdx);
                CellPr curCellNodePr = curCellNode.getCellPr().getFinalPr();
                int curCellWidth = getCellWidth(curCellNode);

                // WORDSNET-1058 Document fails with 'Index was outside the bounds of the array' exception when saved in AsposePdf xml.
                // Cells that are merged to previous and have zero width seem to be
                // handled in the loop for extra merged cells below, so skip them here.
                if (curCellNodePr.isMergedToPrevious() && (curCellWidth == 0))
                    continue;

                // This creates a celler cell with a copy of cell properties from the model cell.
                CellerCell cell = CellerCell.createNormalCell(curCellNodePr, curCellNode);

                int leftOfCell = getCellLeftOrigin(row.getRowNode(), cellNodeIdx);
                int cellIdx = mColumnPositions.indexOfKey(leftOfCell);
                int rightOfCell = leftOfCell + getCellWidth(curCellNode);
                int nextCellIdx = mColumnPositions.indexOfKey(rightOfCell);
                row.getCells()[cellIdx] = cell;

                // WORDSNET-1572 Borders are output incorrectly for the table with many merged cells.
                // For merged cells right and bottom borders should be taken from the respective _last_ cells
                // in horizontal and vertical chain. Since borders are taken from first cells when exporting anywhere
                // make this trick only for them
                Cell cellNodeRightBorderSource = curCellNode;
                Cell cellNodeBottomBorderSource = curCellNode;
                if (curCellNodePr.getHorizontalMerge() == CellMerge.FIRST)
                {
                    for (int cellNodeIdx2 = cellNodeIdx + 1; cellNodeIdx2 < cellNodeCount; ++cellNodeIdx2)
                    {
                        Cell rightNeighbourCellNode = cellNodes.get(cellNodeIdx2);
                        if (rightNeighbourCellNode.getCellPr().getFinalPr().getHorizontalMerge() != CellMerge.PREVIOUS)
                            break;

                        cellNodeRightBorderSource = rightNeighbourCellNode;
                    }
                }
                if (curCellNodePr.getVerticalMerge() == CellMerge.FIRST)
                {
                    for (int rowNodeIdx2 = rowNodeIdx + 1; rowNodeIdx2 < rowNodeCount; ++rowNodeIdx2)
                    {
                        Cell bottomNeighbourCellNode = findCellMatchingLeft(rowNodes.get(rowNodeIdx2), leftOfCell);
                        if ((bottomNeighbourCellNode == null) ||
                            (bottomNeighbourCellNode.getCellPr().getFinalPr().getVerticalMerge() != CellMerge.PREVIOUS))
                        {
                            break;
                        }

                        cellNodeBottomBorderSource = bottomNeighbourCellNode;
                    }
                }

                // Resolve any default cell borders if requested.
                // SPEED optimized.
                CellPr cellPr = cell.getCellPr();
                getCellBorderForExport(cellPr, curCellNode, CellAttr.BORDER_TOP);
                getCellBorderForExport(cellPr, curCellNode, CellAttr.BORDER_LEFT);
                getCellBorderForExport(cellPr, cellNodeBottomBorderSource, CellAttr.BORDER_BOTTOM);
                getCellBorderForExport(cellPr, cellNodeRightBorderSource, CellAttr.BORDER_RIGHT);
                getCellBorderForExport(cellPr, curCellNode, CellAttr.BORDER_DIAGONAL_DOWN);
                getCellBorderForExport(cellPr, curCellNode, CellAttr.BORDER_DIAGONAL_UP);

                // The same for paddings.
                getCellPaddingForExport(cellPr, curCellNode, CellAttr.TOP_PADDING);
                getCellPaddingForExport(cellPr, curCellNode, CellAttr.LEFT_PADDING);
                getCellPaddingForExport(cellPr, curCellNode, CellAttr.BOTTOM_PADDING);
                getCellPaddingForExport(cellPr, curCellNode, CellAttr.RIGHT_PADDING);

                // Create extra merged cells (due to cellerization) if necessary.
                for (int extraCellIdx = cellIdx + 1; extraCellIdx < nextCellIdx; ++extraCellIdx)
                    row.getCells()[extraCellIdx] = CellerCell.createHorizontallyMergedCell(cellPr, curCellNode);

                if (nextCellIdx - cellIdx > 1)
                    hasMisalignedColumns = true;
            }

            if (!hasMisalignedColumns)
                mHasRowWithoutMisalignedColumns = true;
        }
    }

    /// <summary>
    /// See <see cref="GetCellAttrsForExport" />.
    /// </summary>
    private void getCellBorderForExport(CellPr destPr, Cell srcNode, int key)
    {
        getCellAttrsForExport(mResolveInheritedBorders, destPr, srcNode, key);
    }

    /// <summary>
    /// See <see cref="GetCellAttrsForExport" />.
    /// </summary>
    private void getCellPaddingForExport(CellPr destPr, Cell srcNode, int key)
    {
        getCellAttrsForExport(mResolveInheritedPaddings, destPr, srcNode, key);
    }

    /// <summary>
    /// There are two possibilities for attributes being copied: either direct or inherited.
    /// </summary>
    private static void getCellAttrsForExport(boolean resolveInherited, CellPr destPr, Cell srcNode, int key)
    {
        ICellAttrSource cellAttrSource = srcNode;

        Object value = resolveInherited
            ? cellAttrSource.fetchCellAttr(key)
            : cellAttrSource.getDirectCellAttr(key);

        // WORDSNET-24112 Inherited borders of edge cells in tables with non-zero cell spacing are not the borders
        // that are actually rendered by MS Word. Inherited borders are copied from edge borders of the containing row
        // (left, right, top, or bottom) but rendered borders are copied from inner borders of the row (vertical
        // or horizontal). Here we copy rendered borders directly to cells.
        if (resolveInherited &&
            srcNode.getParentTable().getAllowCellSpacing() &&
            (cellAttrSource.getDirectCellAttr(key) == null))
        {
            switch (key)
            {
                case CellAttr.BORDER_TOP:
                case CellAttr.BORDER_BOTTOM:
                    value = ((IRowAttrSource)srcNode.getParentRow()).fetchRowAttr(TableAttr.BORDER_HORIZONTAL);
                    break;
                case CellAttr.BORDER_LEFT:
                case CellAttr.BORDER_RIGHT:
                    value = ((IRowAttrSource)srcNode.getParentRow()).fetchRowAttr(TableAttr.BORDER_VERTICAL);
                    break;
                default:
                    // Keep other values unchanged.
                    break;
            }
        }

        if (value != null)
            destPr.setAttr(key, value);
    }

    /// <summary>
    /// Create pad cells:
    ///   - on the left of the row if this row is indented to the right;
    ///   - on the right of the row if the end of the row is indented to the left.
    /// </summary>
    private void createPadCells(CellerRow row)
    {
        int leftOfFirstCell = getRowLeftOrigin(row.getRowNode());
        int firstCellIdx = mColumnPositions.indexOfKey(leftOfFirstCell);
        for (int i = 0; i < firstCellIdx; ++i)
            row.getCells()[i] = CellerCell.createPadCell();

        int rightOfLastCell = getCellLeftOrigin(row.getRowNode(), row.getRowNode().getCells().getCount());
        int beyondLastCellIdx = mColumnPositions.indexOfKey(rightOfLastCell);
        for (int i = beyondLastCellIdx; i < getColumnCount(); ++i)
            row.getCells()[i] = CellerCell.createPadCell();

        // Mark as having both misaligned cells and pad cells if at least one of loops executed at least once.
        if ((firstCellIdx > 0) || (beyondLastCellIdx < getColumnCount()))
            mHasPadCells = true;
    }

    /// <summary>
    /// This loop updates col spans and widths of the cells.
    /// </summary>
    private void calcCellsH()
    {
        for (int rowIdx = 0; rowIdx < mRows.size(); rowIdx++)
        {
            int colSpan = 1;
            //This is a loop from the last column to the first.
            for (int colIdx = getColumnCount() - 1; colIdx >= 0; colIdx--)
            {
                CellerCell cell = mRows.get(rowIdx).getCells()[colIdx];
                switch (cell.getCellPr().getHorizontalMerge())
                {
                    case CellMerge.FIRST:
                    case CellMerge.NONE:
                        cell.getCellPr().setWidth(getColumnWidth(colIdx, colSpan));
                        cell.setColSpan(colSpan);
                        colSpan = 1;
                        break;
                    case CellMerge.PREVIOUS:
                        colSpan++;
                        break;
                    default:
                        throw new IllegalStateException("Unknown CellMerge type.");
                }
            }
        }
    }

    /// <summary>
    /// Updates row spans so the cells will come out vertically merged.
    /// </summary>
    private void calcCellsV()
    {
        for (int colIdx = 0; colIdx < getColumnCount(); colIdx++)
        {
            int rowSpan = 1;
            for (int rowIdx = mRows.size() - 1; rowIdx >= 0; rowIdx--)
            {
                CellerCell cell = mRows.get(rowIdx).getCells()[colIdx];
                switch (cell.getCellPr().getVerticalMerge())
                {
                    case CellMerge.FIRST:
                    case CellMerge.NONE:
                        cell.setRowSpan(rowSpan);
                        rowSpan = 1;
                        break;
                    case CellMerge.PREVIOUS:
                        rowSpan++;
                        break;
                    default:
                        throw new IllegalStateException("Unknown CellMerge type.");
                }
            }
        }
    }

    /// <summary>
    /// Updates borders of the empty pad cells at the beginning and end of the rows.
    /// This code assumes that at least one non-padding cell exists in every row.
    /// Here we should determine whether we need empty borders in inheritance mode.
    /// Using assumption the same as in PDF: taking properties from the first row.
    /// </summary>
    private void updatePadCellsBorders()
    {
        Debug.assertTrue(mHasPadCells);
        Debug.assertTrue(getColumnCount() > 1);

        if (mPopulateEmptyPadBorders)
        {
            TablePr tablePr = mTableNode.getFirstRow().getTablePr().getFinalPr();
            mTableLeftVisible = tablePr.containsKey(TableAttr.BORDER_LEFT);
            mTableRightVisible = tablePr.containsKey(TableAttr.BORDER_RIGHT);
            mTableTopVisible = tablePr.containsKey(TableAttr.BORDER_TOP);
            mTableBottomVisible = tablePr.containsKey(TableAttr.BORDER_BOTTOM);
            mTableHorizontalVisible = tablePr.containsKey(TableAttr.BORDER_HORIZONTAL);
            mTableVerticalVisible = tablePr.containsKey(TableAttr.BORDER_VERTICAL);

            boolean tableAnyVisible = mTableLeftVisible || mTableRightVisible || mTableTopVisible || mTableBottomVisible ||
                mTableHorizontalVisible || mTableVerticalVisible;
            mEmptyBorder = tableAnyVisible ? new Border(LineStyle.NONE, 0, DrColor.EMPTY) : null;
        }

        for (int rowIdx = 0; rowIdx < mRows.size(); ++rowIdx)
        {
            CellerRow row = mRows.get(rowIdx);
            for (int colIdx = 0; updateOnePadCellBorder(row.getCells()[colIdx], rowIdx, colIdx); ++colIdx)
            {
                // Nothing to do, empty block.
            }
            for (int colIdx = getColumnCount() - 1; updateOnePadCellBorder(row.getCells()[colIdx], rowIdx, colIdx); --colIdx)
            {
                // Nothing to do, empty block.
            }
        }
    }

    private boolean updateOnePadCellBorder(CellerCell cell, int rowIdx, int colIdx)
    {
        boolean result = cell.isPad();
        if (result)
        {
            CellPr cellPr = cell.getCellPr();

            boolean topCell = (rowIdx == 0);
            boolean bottomCell = (rowIdx == mRows.size() - 1);

            // The top border of the pad cell becomes the bottom border of the top cell unless at the top row already.
            boolean topAssigned = !topCell && assignCellAttr(cellPr, CellAttr.BORDER_TOP, mRows.get(rowIdx - 1).getCells()[colIdx].getCellPr(), CellAttr.BORDER_BOTTOM);
            // The bottom border of the pad cell becomes the top border of the bottom row.
            boolean bottomAssigned = !bottomCell && assignCellAttr(cellPr, CellAttr.BORDER_BOTTOM, mRows.get(rowIdx + 1).getCells()[colIdx].getCellPr(), CellAttr.BORDER_TOP);
            // Left and right borders of the pad cells look ok in the browser so don't worry about them.
            // (Unless we add empty borders.)

            // Need empty borders? Add them. At this point mEmptyBorder is initialized if they are needed.
            if (mEmptyBorder != null)
            {
                Debug.assertTrue(mPopulateEmptyPadBorders);

                if ((topCell && mTableTopVisible) || (!topCell && mTableHorizontalVisible && !topAssigned))
                    cellPr.setBorderTop(mEmptyBorder);

                if ((bottomCell && mTableBottomVisible) || (!bottomCell && mTableHorizontalVisible && !bottomAssigned))
                    cellPr.setBorderBottom(mEmptyBorder);

                // Here we have to do everything analogically to horizontal borders.
                boolean leftCell = (colIdx == 0);
                boolean rightCell = (colIdx == getColumnCount() - 1);

                boolean leftAssigned = !leftCell && assignCellAttr(cellPr, CellAttr.BORDER_LEFT, mRows.get(rowIdx).getCells()[colIdx - 1].getCellPr(), CellAttr.BORDER_RIGHT);
                boolean rightAssigned = !rightCell && assignCellAttr(cellPr, CellAttr.BORDER_RIGHT, mRows.get(rowIdx).getCells()[colIdx + 1].getCellPr(), CellAttr.BORDER_LEFT);

                if ((leftCell && mTableLeftVisible) || (!leftCell && mTableVerticalVisible && !leftAssigned))
                    cellPr.setBorderLeft(mEmptyBorder);

                if ((rightCell && mTableRightVisible) || (!rightCell && mTableVerticalVisible && !rightAssigned))
                    cellPr.setBorderRight(mEmptyBorder);
            }
        }
        return result;
    }

    /// <summary>
    /// Assigns one attribute on source to another attribute on destination. Returns whether attribute is not null.
    /// </summary>
    private static boolean assignCellAttr(CellPr dest, int destKey, CellPr src, int srcKey)
    {
        Object value = src.getDirectAttr(srcKey);
        boolean result = (value != null);
        if (result)
            dest.setAttr(destKey, value);
        return result;
    }

    /// <summary>
    /// Returns true if the table has pad cells.
    /// </summary>
    boolean hasPadCells() { return mHasPadCells; }

    /// <summary>
    /// Returns true if the table has at least one row without misaligned columns.
    /// </summary>
    boolean hasRowWithoutMisalignedColumns() { return mHasRowWithoutMisalignedColumns; }

    /// <summary>
    /// Finds out merged rows and calculates related values.
    /// </summary>
    private void processMergedRows()
    {
        findMergedRows();
        calculateMergedRowHeight();
        calculateUnmergedRowSpans();
    }

    /// <summary>
    /// Marks merged rows and calculates the running total of merged row count.
    /// </summary>
    private void findMergedRows()
    {
        // Indicates how many merged rows are located above the current row (including the current row itself).
        int mergedRowsAbove = 0;

        for (CellerRow row : mRows)
        {
            boolean isMerged = true;

            // A row is merged if all its cells are vertically merged.
            for (CellerCell cell : row.getCells())
            {
                if (cell.getCellPr().getVerticalMerge() != CellMerge.PREVIOUS)
                {
                    isMerged = false;
                    break;
                }
            }

            if (isMerged)
            {
                ++mergedRowsAbove;
            }

            row.isMerged(isMerged);
            row.setMergedRowsAbove(mergedRowsAbove);
        }
    }

    /// <summary>
    /// Calculates sum of heights for all adjacent merged rows and the last unmerged row before them.
    /// </summary>
    private void calculateMergedRowHeight()
    {
        double sumMergedRowHeight = 0.0;
        CellerRow lastUnmergedRow = mRows.get(0);
        for (CellerRow row : mRows)
        {
            if (row.isMerged())
            {
                // Calculate sum of height of adjacent merged rows.
                sumMergedRowHeight += getRowHeightAproximate(row.getRowNode());
            }
            else
            {
                // End of a merged row range. Update unmerged row range info.
                if (sumMergedRowHeight > 0)
                {
                    lastUnmergedRow.setMergedRowsHeight(sumMergedRowHeight + getRowHeightAproximate(lastUnmergedRow.getRowNode()));
                }
                // Start a new merged row range.
                lastUnmergedRow = row;
                sumMergedRowHeight = 0.0;
            }
        }
        // A merged row range also ends the end of the table. Update unmerged row range info.
        if (sumMergedRowHeight > 0)
        {
            lastUnmergedRow.setMergedRowsHeight(sumMergedRowHeight + getRowHeightAproximate(lastUnmergedRow.getRowNode()));
        }
    }

    /// <summary>
    /// Calculates cell's row spans taking merged rows below them into account.
    /// </summary>
    private void calculateUnmergedRowSpans()
    {
        for (int rowIndex = 0; rowIndex < mRows.size(); rowIndex++)
        {
            CellerRow row = mRows.get(rowIndex);
            for (CellerCell cell : row.getCells())
            {
                if (cell.getRowSpan() > 1)
                {
                    // Row span value of each cell is reduced by the number of merged rows that are inside the row span.
                    CellerRow lastRowInRowSpan = mRows.get(rowIndex + cell.getRowSpan() - 1);
                    cell.setUnmergedRowSpan(cell.getRowSpan() - (lastRowInRowSpan.getMergedRowsAbove() - row.getMergedRowsAbove()));
                }
            }
        }
    }

    private static double getRowHeightAproximate(Row row)
    {
        double rowHeight = 0.0;
        // Row height depends on the first cell's font size.
        if ((row.getFirstCell() != null) && (row.getFirstCell().getFirstParagraph() != null))
        {
            Font font = row.getFirstCell().getFirstParagraph().getParagraphBreakFont();
            DrFont drFont = row.getDocument().getFontProvider().fetchDrFont(
                font.getName(),
                (float)font.getSize(),
                FontStyle.REGULAR);

            rowHeight = drFont.getLineSpacingPoints();
        }
        // Takes larger of the table row's height and aproximate row height.
        return Math.max(row.getRowFormat().getHeight(), rowHeight);
    }

    /// <summary>
    /// Calculates left coordinate of the row.
    /// The same result as GetCellLeftOrigin(rowNode, 0)
    /// </summary>
    private static int getRowLeftOrigin(Row rowNode)
    {
        TablePr tablePr = rowNode.getTablePr().getFinalPr();
        return rowNode.getParentTable().getTableLeft() + tablePr.getWidthBeforeTwips();
    }

    /// <summary>
    /// Calculates left coordinate of the cell in the row with given index.
    /// Moved from Row class. Was marked as "Temporary hack used by CellerTable."
    /// </summary>
    private static int getCellLeftOrigin(Row rowNode, int cellIndex)
    {
        int result = getRowLeftOrigin(rowNode);

        CellCollection cells = rowNode.getCells();
        for (int i = 0; i < cellIndex; ++i)
            result += getCellWidth(cells.get(i));

        return result;
    }

    /// <summary>
    /// Finds a cell in the neibour row which can be merged.
    /// Let's find a cell exactly matching at left without care of right.
    /// For instance we can merge these four cells in MS Word:
    /// [ narrow ] [               wide               ]
    /// [               wide               ] [ narrow ]
    /// So checking left is enough to find the proper border I guess.
    /// </summary>
    private static Cell findCellMatchingLeft(Row neighbourRowNode, int left)
    {
        int neighbourLeft = getRowLeftOrigin(neighbourRowNode);
        if (neighbourLeft <= left)
        {
            CellCollection neighbourCells = neighbourRowNode.getCells();
            for (Cell cell : (Iterable<Cell>) neighbourCells)
            {
                if (neighbourLeft == left)
                    return cell;

                neighbourLeft += getCellWidth(cell);
            }
        }

        return null;
    }

    private static int getCellWidth(Cell cell)
    {
        CellPr cellPr = cell.getCellPr().getFinalPr();
        int width = cellPr.getWidth();

        // WORDSNET-28434 It is possible in certain edge cases that an unmerged model cell will have zero width.
        // It is a problem for the celler algorithm, because zero width cells don't generate celler columns and,
        // as a result, no celler cell is generated for the model cell node, which leads to an exception.
        // In order to work around the problem, it was decided to process zero-width unmerged cells as min width cells.
        if ((width == 0) && !cellPr.isMergedToPrevious())
        {
            width = 1;
        }

        return width;
    }

    /// <summary>
    /// Sets flags that indicate the position of each row in its row group (header or body).
    /// </summary>
    private void markRowGroups()
    {
        boolean tableHasHeader = false;
        CellerRow previousCellerRow = null;
        for (CellerRow cellerRow : mRows)
        {
            // WORDSNET-20327 Write only one <thead> per table and make sure <thead> always precedes <tbody>.
            // Heading rows in the document model can come after normal (body) rows but in HTML this is not allowed.
            // That's why if we meet a normal row, we consider all rows below it as normal too.
            cellerRow.isHeading(((previousCellerRow == null) || previousCellerRow.isHeading()) &&
                isHeadingRow(cellerRow.getRowNode()));

            if (cellerRow.isHeading())
            {
                tableHasHeader = true;
            }

            // Please notice that we don't mark rows if the table has no other row groups besides body. As a result,
            // simple tables are exported without explicit row group elements. For example, in HTML most tables will
            // contain <tr> elements directly, without enclosing them into <tbody>.
            if (tableHasHeader &&
                ((previousCellerRow == null) || (previousCellerRow.isHeading() != cellerRow.isHeading())))
            {
                cellerRow.isFirstInGroup(true);
            }

            previousCellerRow = cellerRow;
        }
    }

    /// <summary>
    /// Indicates whether a table row is heading.
    /// </summary>
    private static boolean isHeadingRow(Row row)
    {
        // Table row is heading if it has any vertical merged cell with previous heading row.
        boolean isHeading = row.getTablePr().getFinalPr().getHeadingFormat();
        if (!isHeading && (row.getPreviousRow() != null) && row.getPreviousRow().getTablePr().getFinalPr().getHeadingFormat())
        {
            Cell cell = row.getFirstCell();
            while (cell != null)
            {
                if (cell.getCellPr().getVerticalMerge() == CellMerge.PREVIOUS)
                {
                    isHeading = true;
                    break;
                }
                cell = cell.getNextCell();
            }
        }
        return isHeading;
    }

    /// <summary>
    /// Table node being cellerized
    /// </summary>
    private /*final*/ Table mTableNode;

    /// <summary>
    /// Option to resolve inherited borders on cells.
    /// </summary>
    private /*final*/ boolean mResolveInheritedBorders;

    /// <summary>
    /// Option to populate empty borders to pad cells.
    /// Needed when table borders are specified.
    /// </summary>
    private /*final*/ boolean mPopulateEmptyPadBorders;

    /// <summary>
    /// Option to resolve inherited paddings on cells.
    /// </summary>
    private /*final*/ boolean mResolveInheritedPaddings;

    /// <summary>
    /// Specifies if celler should visit merged cells when move to next cell.
    /// </summary>
    private /*final*/ boolean mGotoMergedCells;

    /// <summary>
    /// These several indidates helps populate empty pad borders only where they are needed.
    /// </summary>
    private boolean mTableLeftVisible;
    private boolean mTableRightVisible;
    private boolean mTableTopVisible;
    private boolean mTableBottomVisible;
    private boolean mTableHorizontalVisible;
    private boolean mTableVerticalVisible;
    private Border mEmptyBorder;
    /// <summary>
    /// Keys are sorted column positions for all rows of the table.
    /// </summary>
    private /*final*/ SortedIntegerListGeneric<Object> mColumnPositions = new SortedIntegerListGeneric<Object>();
    private /*final*/ ArrayList<CellerRow> mRows = new ArrayList<CellerRow>();
    private int mCurRowIdx;
    private int mCurColIdx;
    private boolean mHasRowWithoutMisalignedColumns;
    private boolean mHasPadCells;
}

