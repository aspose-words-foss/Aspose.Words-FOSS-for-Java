// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2016 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msArray;
import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.SortedIntegerListGeneric;
import com.aspose.IntList;
import com.aspose.MathUtil;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Implements methods for merging tables adjacent in the document model.
/// </summary>
class TableMerger
{

    /// <summary>
    /// Checks if there are adjacent table to be combined with the given table after it,
    /// and joins the table with adjacent tables before it if there are none.
    /// </summary>
    /// <returns>The combined table or null if there are combinable tables after.</returns>
    /// <remarks>
    /// <para>
    /// The method is supposed to be called from DocumentVisitor.
    /// It actually joins the table only when last table that can be merged is found.
    /// This allows the visitor to do whatever it does on cell and row level before the tables are joined.
    /// </para>
    /// <para>
    /// The method emulates MS Word behavior by combining table grids stored in the document for the merged tables.
    /// It is to be called from DocumentPostLoader.
    /// </para>
    /// </remarks>
    Table joinAdjacentTablesCombineGrids(Table table)
    {
        // Set "missing default table style condition" only when combining grids (in postLoader).
        setIsTableNormalMissingOnLoading(table);
        return joinAdjacentTables(table, true);
    }

    /// <summary>
    /// Checks if there are adjacent table to be combined with the given table after it,
    /// and joins the table with adjacent tables before it if there are none.
    /// </summary>
    /// <returns>The combined table or null if there are combinable tables after.</returns>
    /// <remarks>
    /// <para>
    /// The method is supposed to be called from DocumentVisitor.
    /// It actually joins the table only when last table that can be merged is found.
    /// This allows the visitor to do whatever it does on cell and row level before the tables are joined.
    /// </para>
    /// <para>
    /// The methods just moves table rows to the first table.
    /// It discards table grids for the removed tables.
    /// It does not update cell grid spans.
    /// This is how table merge was originally implemented in document validator.
    /// </para>
    /// </remarks>
    Table joinAdjacentTablesDiscardGrids(Table table)
    {
        return joinAdjacentTables(table, false);
    }

    /// <summary>
    /// Implements adjacent table merge.
    /// </summary>
    private Table joinAdjacentTables(Table table, boolean combineGrids)
    {
        if (isNextTableMergeNeeded(table, combineGrids))
        {
            // Just remember the table, it will be merged when the last table to be merged is found.
            rememberTable(table);
            return null;
        }

        if (!hasMergeableTablesBefore(table))
        {
            // The table does not need merging, just return the table.
            return table;
        }

        ArrayList<Table> adjacentTables = mAdjacentTableLists.pop();
        Debug.assertTrue(adjacentTables.size() > 0);

        Table firstTable = adjacentTables.get(0);
        msArrayList.add(adjacentTables, table);

        boolean allGridsApplied = true;
        for (Table tbl : adjacentTables)
            allGridsApplied = allGridsApplied && tbl.isCalculatedGridApplied();

        // Use the new logic that combines grids:
        // - on loading, always;
        // - on validation (before saving), if the new grids are applied.
        if (combineGrids || allGridsApplied)
        {
            SortedIntegerListGeneric<Object> combinedGrid = getCombinedGrid(adjacentTables);
            updateSpanCountsForCombinedGrid(adjacentTables, combinedGrid);

            // Set the combined grid.
            TableGridColumnsAttr combinedWidths = new TableGridColumnsAttr(toIntList(combinedGrid));
            firstTable.getTablePr().setAttr(TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM, combinedWidths);

            // I've tried removing unneeded indents in the merged tables, but it broke a couple of tests.
            // TestJira14246 and TestDefect5221 do save-open, checking the opened and re-saved document against the same gold.
            // On the first save, some tables are merged, and if indents are removed, a gold without indents must be accepted.
            // But on opening the saved document, AW adds indents all the same, so the new gold does not work for the second save.
            // It appears that unneeded indents should be handled at the model level. Leave them be for now.
        }
        else
        {
            // Use the older logic that just moves all rows into a single table
            // not trying to re-calculate grid spans or taking grids into account.

            // WORDSNET-13599 Allow the re-calculation of the grid and spans of the cells,
            // while document writing.
            // DM: The calculation will actually fall back to using cell widths.
            // If cell widths are not correct, the result will be incorrect as well.
            TableGridAttr calculatedGrid = firstTable.getTablePr().getCalculatedGrid();
            if (calculatedGrid != null)
                calculatedGrid.setState(GridState.CALCULATION_FAILED);
        }

        // Move all rows to the first merged table.
        mergeTables(adjacentTables);

        return firstTable;
    }

    /// <summary>
    /// Registers the given table in a list of tables that should be merged together.
    /// </summary>
    private void rememberTable(Table table)
    {
        if (!hasMergeableTablesBefore(table))
        {
            // The table is on a different level (can happen for nested tables).
            // Add a new list.
            mAdjacentTableLists.push(new ArrayList<Table>());
        }

        ArrayList<Table> topList = mAdjacentTableLists.peek();
        msArrayList.add(topList, table);
    }

    /// <summary>
    /// Gets a boolean value indicating if tables that should be merged with the given table were already registered.
    /// </summary>
    private boolean hasMergeableTablesBefore(Table table)
    {
        if (mAdjacentTableLists.getCount() == 0)
            return false;

        ArrayList<Table> topList = mAdjacentTableLists.peek();
        Debug.assertTrue(topList.size() > 0);

        Table firstInMerge = topList.get(0);
        return (firstInMerge.getParentNode() == table.getParentNode());
    }

    /// <summary>
    /// Checks if the two adjacent tables should be merged into one.
    /// </summary>
    private static boolean isTableMergeNeeded(Table table1, Table table2, boolean combineGrids)
    {
        // WORDSNET-10543 If at least one of the tables has no cells, then we do not compare their formatting at all.
        if (!table1.hasCells() || !table2.hasCells())
            return false;

        validateTableStyle(table1);
        validateTableStyle(table2);

        Row row1 = table1.getFirstRow();
        Row row2 = table2.getFirstRow();

        // TestJira6025HideMarkCustomer shows that different styles prevent merging.
        // One exception is missing style vs default style. Compare resolved Istds.
        if (row1.getTablePr().getIstd() != row2.getTablePr().getIstd())
            return false;

        boolean bothGridsApplied = table1.isCalculatedGridApplied() && table2.isCalculatedGridApplied();
        int[] ignoredAttributes = bothGridsApplied || combineGrids
            ? G_ATTRIBUTES_IGNORED_COMBINED
            : G_ATTRIBUTES_IGNORED;
        // Compare table formatting excluding row exclusive attributes.
        if (!row1.getTablePr().equals(row2.getTablePr(), ignoredAttributes))
            return false;

        // Finally do deep comparison including inner paragraphs.
        if (!row1.isSameRowFloatingPositioning(row2))
            return false;

        return true;
    }

    /// <summary>
    /// Checks that table has valid table style, assigns TableNormal otherwise.
    /// </summary>
    /// <remarks>
    /// This validation is actually performed later but
    /// we need it before we decide whether we need to merge tables.
    /// </remarks>
    private static void validateTableStyle(Table table)
    {
        TablePr tablePr = table.getFirstRow().getTablePr();

        if (tablePr.contains(TableAttr.ISTD))
        {
            Style tableStyle = table.getDocument().getStyles().getByIstd(tablePr.getIstd(), false);
            if(tableStyle != null && tableStyle.getType() != StyleType.TABLE)
                tablePr.setAttr(TableAttr.ISTD, StyleIndex.TABLE_NORMAL);
        }
    }

    /// <summary>
    /// Gets a boolean value indicating if the next sibling of the given table is a table that should be merged with it.
    /// </summary>
    private static boolean isNextTableMergeNeeded(Table table, boolean combineGrids)
    {
        // Merging tables when one or both of them are in SDTs is not implemented.
        // MS Word itself does not handle the case very well.
        // TestTableMergeSdt demonstrates different cases when an error occurs in MS Word during saving
        // if editing the document results in adjacent tables wrapped in SDT.

        // So the code below just checks siblings, it does not ascend/descend SDTs.
        Node nextNode = table.getNextNonAnnotationSibling();
        boolean isNextSiblingTable = (nextNode != null) && (nextNode.getNodeType() == NodeType.TABLE);
        boolean isNextTableMergeNeeded = isNextSiblingTable &&
                              isTableMergeNeeded(table, (Table)nextNode, combineGrids);

        return isNextTableMergeNeeded;
    }

    /// <summary>
    /// Joins the tables in the specified list.
    /// </summary>
    private static void mergeTables(ArrayList<Table> adjacentTables)
    {
        Table firstTable = adjacentTables.get(0);
        Debug.assertTrue(firstTable != null);

        // Start from the next table.
        for (int tableIndex = 1; tableIndex < adjacentTables.size(); ++tableIndex)
        {
            Table table = adjacentTables.get(tableIndex);

            // WORDSNET-10636 Table in glossary may not have parent.
            // Also table can be removed by TableValidator.
            if (table.getParentNode() == null)
                continue;

            Table prevTable = ms.as(table.getPreviousNonAnnotationSibling(), Table.class);
            Debug.assertTrue(firstTable == prevTable);
            if (prevTable == null)
                continue;

            // Move annotation nodes between tables to row level.
            prevTable.insertAfter(prevTable.getNextSibling(), table, prevTable.getLastChild());

            // Move rows.
            while (table.getFirstChild() != null)
                prevTable.appendChild(table.getFirstChild());

            table.remove();
        }
    }

    /// <summary>
    /// Converts a hashset of column boundaries to a list of column widths.
    /// </summary>
    private static IntList toIntList(SortedIntegerListGeneric<Object> columns)
    {
        IntList grid = new IntList(Math.max(columns.getCount() - 1, 0));
        for (int columnIndex = 1; columnIndex < columns.getCount(); columnIndex++)
        {
            int columnWidth = columns.getKey(columnIndex) - columns.getKey(columnIndex - 1);
            grid.add(columnWidth);
        }

        return grid;
    }

    /// <summary>
    /// Gets a combined list of column boundaries for the given table list according to table grids.
    /// </summary>
    /// <returns>A sorted list that stores relative boundaries of all columns in a grid
    /// made from overlapping of table grids for tables in the list.</returns>
    /// <remarks>
    /// List keys represent the boundaries, the values are null.
    /// </remarks>
    private SortedIntegerListGeneric<Object> getCombinedGrid(ArrayList<Table> tables)
    {
        Debug.assertTrue(tables.size() > 0);

        Table leftmostTable = getLeftmostTable(tables);

        // Test26496(): a very special behavior under a weird condition.
        setWriteWidthBeforeTableX(leftmostTable);

        SortedIntegerListGeneric<Object> combinedGrid = new SortedIntegerListGeneric<Object>();
        int minTableX = getTableX(leftmostTable);
        addToGrid(combinedGrid, minTableX);

        for (Table table : tables)
        {
            // Insert column boundaries for this table into combined grid.
            int tableX = getTableX(table);

            // Test26496(): insert the left table boundary into the grid under a special condition.
            if (getWriteWidthBeforeTableX())
                addToGrid(combinedGrid, tableX);

            int columnRight = tableX;
            IntList tableGrid = getTableGrid(table);
            // Column count may be greater than the grid count in case grid columns are missing.
            int columnCount = getColumnCount(table);
            // The right table boundary is treated specially.
            for (int col = 0; col < columnCount; ++col)
            {
                // Default width is returned for columns missing in the grid.
                int columnWidth = getGridColumnWidth(tableGrid, col);
                columnRight += columnWidth;
                addToGrid(combinedGrid, columnRight);
            }
        }

        return combinedGrid;
    }

    /// <summary>
    /// Gets the table with the leftmost position from the given list.
    /// </summary>
    /// <remarks>
    /// If several tables have the same position, the first one is returned.
    /// </remarks>
    private static Table getLeftmostTable(ArrayList<Table> tables)
    {
        Debug.assertTrue(tables.size() > 0);

        Table leftmostTable = null;
        int minTableLeft = Integer.MAX_VALUE;
        for (Table table : tables)
        {
            int tableLeft = getTableX(table);
            if (tableLeft < minTableLeft)
            {
                minTableLeft = tableLeft;
                leftmostTable = table;
            }
        }

        return leftmostTable;
    }

    /// <summary>
    /// Sets a special condition about TableNormal style missing.
    /// </summary>
    private void setIsTableNormalMissingOnLoading(Table table)
    {
        // If already set, do not reset.
        if (isTableNormalMissingOnLoading())
            return;

        if (table.getDocument() == null || table.getDocument().getStyles() == null)
            return;

        isTableNormalMissingOnLoading(table.getDocument().getStyles().getBySti(StyleIdentifier.TABLE_NORMAL, false) == null);
    }

    /// <summary>
    /// Stores a special condition about TableNormal style missing.
    /// </summary>
    /// <remarks>
    /// This is a rare condition that affects merging tables on loading the document.
    /// See usages for details.
    /// </remarks>
    private boolean isTableNormalMissingOnLoading() { return mIsTableNormalMissingOnLoading; }; private void isTableNormalMissingOnLoading(boolean value) { mIsTableNormalMissingOnLoading = value; };

    private boolean mIsTableNormalMissingOnLoading;

    /// <summary>
    /// Calculates a condition to imitate some special MS Word logic.
    /// </summary>
    /// <remarks>
    /// Test26496(): MS Word has a very special behavior under a weird condition.
    /// The behavior is imitated by storing widthBefore for the rows of the indented merged tables.
    /// </remarks>
    private void setWriteWidthBeforeTableX(Table leftmostTable)
    {
        // A weird condition that fixes Test26946.
        // Actually, MS Word behavior seems to depend on presence of a table style with w:default="1".
        // But since we do not read this style attribute, this is what I came up with.
        // The case of missing default table styles with adjacent tables in the document present should be rare.
        setWriteWidthBeforeTableX(isTableNormalMissingOnLoading() && (leftmostTable.getAlignment() != TableAlignment.LEFT));
        // TODO: what happens if there is a mix of differently aligned tables at 0 position?
    }

    /// <summary>
    /// Indicates if special logic should be used for setting widthBefore values.
    /// </summary>
    /// <remarks>
    /// The logic affects Test26496() and TestMergeAndSaveToPdf().
    /// The case is rare.
    /// </remarks>
    private boolean getWriteWidthBeforeTableX() { return mWriteWidthBeforeTableX; }; private void setWriteWidthBeforeTableX(boolean value) { mWriteWidthBeforeTableX = value; };

    private boolean mWriteWidthBeforeTableX;

    private static void addToGrid(SortedIntegerListGeneric<Object> grid, int column)
    {
        if (!grid.containsKey(column))
            grid.set(column, null);
    }

    private static int getGridColumnWidth(IntList grid, int column)
    {
        return (column < grid.getCount())
            ? grid.get(column)
            : TableGridFromCellWidth.DEFAULT_FIXED_COLUMN_WIDTH_TWIPS;
    }

    /// <summary>
    /// Truncates the grid starting from the first invalid column.
    /// </summary>
    /// <remarks>
    /// Sometimes the grid is present in the document, but with zero values.
    /// It seems, MS Word disregards such columns.
    /// </remarks>
    private static void removeInvalidGridColumns(IntList grid)
    {
        if (grid == null)
            return;

        for (int i = 0; i < grid.getCount(); ++i)
        {
            if (grid.get(i) <= 0)
            {
                int allTheRest = grid.getCount() - i;
                grid.removeRange(i, allTheRest);
                break;
            }
        }
    }

    /// <summary>
    /// Gets the column widths (grid) to use with the new logic that relies on table grids.
    /// </summary>
    private static IntList getTableGrid(Table table)
    {
        IntList grid;
        if (table.isCalculatedGridApplied())
        {
            // Use the calculated grid if there is any.
            grid = table.getTablePr().getCalculatedGrid().getColumns();
        }
        else
        {
            // Use the original grid stored in the document.
            TableGridColumnsAttr gridFromDoc = (TableGridColumnsAttr)table.getTablePr().getDirectAttr(TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM);

            // Return an empty list if there is no grid in the document.
            grid = (gridFromDoc == null)
                ? new IntList()
                : gridFromDoc.GridColumns;
        }

        removeInvalidGridColumns(grid);
        return grid;
    }

    private static int getColumnCount(Table table)
    {
        int maxColumnCount = 0;
        for (Row row : (Iterable<Row>) table.getRows())
        {
            // Only spanCounts are used here. The widths are not used.
            int columnCount = row.getTablePr().getGridBefore();
            for (Cell cell : (Iterable<Cell>) row.getCells())
                columnCount += cell.getCellPr().getGridSpan();

            if (maxColumnCount < columnCount)
                maxColumnCount = columnCount;
        }

        return maxColumnCount;
    }

    /// <summary>
    /// Updates cell span counts in a combined table to match the combined grid.
    /// </summary>
    /// <remarks>
    /// The cell spans are calculated from table grids, even if the grids are not correct.
    /// Extra grid columns should not affect anything.
    /// They may only cause span count increase in other tables, but the increased span will match the same grid width.
    /// Missing grid columns will cause cell spans to be re-computed from the default width.
    /// Cell spans may change when the combined table grid is re-calculated:
    /// grid columns matching wide cell intersections may (or may not) be removed.
    /// It seems, the same logic works for auto-fit tables.
    /// </remarks>
    private void updateSpanCountsForCombinedGrid(ArrayList<Table> tables, SortedIntegerListGeneric<Object> combinedGrid)
    {
        // The left of the first column (table.X) is present in the combined grid, decrease 1 to get column count.
        int combinedGridColumnCount = combinedGrid.getCount() - 1;
        int combinedGridRight = combinedGrid.getKey(combinedGridColumnCount);
        int combinedGridX = combinedGrid.getKey(0);

        for (Table table : tables)
        {
            IntList tableGrid = getTableGrid(table);

            int tableX = getTableX(table);
            // tableX is (normally) not written to the combined grid, so there may not be a matching grid column boundary.
            // Look for maximum combined grid column that is before (or equals) this tableX.
            int tableXGrid = 0;
            for (int colIdx = 0; colIdx < combinedGrid.getCount(); ++colIdx)
            {
                int nextColumn = combinedGrid.getKey(colIdx);
                if (nextColumn <= tableX)
                    tableXGrid = nextColumn;
                else
                    break;
            }
            // This will effectively widen the first cell (or grid before)
            // in tables indented relatively to the leftmost tables,
            // unless they happen to align with a combined grid column boundary.
            // See tests like TestTableMergeIndentE().

            for (Row row : (Iterable<Row>) table.getRows())
            {
                // Only spanCounts are used here. The widths are not used.
                int firstCellColumn = 0;
                int cellLeft = 0;
                int cellRight = 0;
                int combinedColumnRight = tableXGrid;

                int cellSpanCount = row.getTablePr().getGridBefore();
                if (cellSpanCount > 0)
                {
                    cellRight = getColumnRightFromTableGrid(tableGrid, firstCellColumn, cellLeft, cellSpanCount);
                    combinedColumnRight = tableX + cellRight;
                }
                int combinedColumns = getColumnCountFromCombinedGrid(combinedGrid, combinedColumnRight);

                // WidthBefore without gridBefore is ignored by the above code.
                // It seems, MS Word currently works this way, see the document in TestTableMergWidthBeforeWtGridBefore().

                row.getTablePr().setAttr(TableAttr.SYS_GRID_BEFORE, combinedColumns);
                // Only write widthBefore under a very special and rare condition.
                if (combinedColumns > 0 && getWriteWidthBeforeTableX())
                {
                    int widthBeforeTwips = combinedColumnRight - combinedGridX;
                    PreferredWidth widthBefore = PreferredWidth.fromTwipsSafe(widthBeforeTwips);
                    row.getTablePr().setAttr(TableAttr.WIDTH_BEFORE, widthBefore);
                    // Set the original width before as it is no longer valid for the combined table. Test26496().
                    row.getTablePr().setAttr(TableAttr.WIDTH_BEFORE_ORIGINAL, widthBefore);
                }

                firstCellColumn += cellSpanCount;
                cellLeft = cellRight;
                int combinedColumnsBeforeCell = combinedColumns;

                for (Cell cell : (Iterable<Cell>) row.getCells())
                {
                    // For cells with horizontal merge it will produce a strange mix of cells
                    // where both merge and grid spans are set.
                    // It does not affect grid calculation.
                    cellSpanCount = cell.getCellPr().getGridSpan();
                    cellRight = getColumnRightFromTableGrid(tableGrid, firstCellColumn, cellLeft, cellSpanCount);

                    combinedColumnRight = tableX + cellRight;
                    combinedColumns = getColumnCountFromCombinedGrid(combinedGrid, combinedColumnRight);
                    int combinedColumnsCount = combinedColumns - combinedColumnsBeforeCell;

                    // WORDSJAVA-2144 - Can't load RTF with 0-columns merged table inside.
                    Debug.assertTrue(combinedColumnsCount >= 0);

                    cell.getCellPr().setGridSpan(combinedColumnsCount);
                    // Test19871autoB: do not set twip width to zero pct preferred.
                    PreferredWidth cellPreferred = cell.getCellPr().getPreferredWidth();
                    if (!table.getAllowAutoFit() && cellPreferred.isAuto() && cellPreferred.getType() != PreferredWidthType.PERCENT)
                    {
                        // Without this setting, "auto" fixed width may be wrong in a combined table.
                        cell.getCellPr().setPreferredWidth(PreferredWidth.fromTwipsSafe(cellRight - cellLeft));
                        // Actually, MS Word seems to always assign preferred width to cells in fixed layout tables if it is missing.
                        // But I do it only here and during fixed grid calculation.
                        // TODO Consider doing it in PostLoader for all fixed layout tables.
                        // Cells with auto width in auto-fit tables should remain at zero as TestFixedVsAutoDifferentSpansA shows.
                    }

                    firstCellColumn += cellSpanCount;
                    cellLeft = cellRight;
                    combinedColumnsBeforeCell = combinedColumns;
                }

                // Update widthAfter.
                int widthAfter = combinedGridRight - combinedColumnRight;
                if (widthAfter > 0)
                {
                    row.getTablePr().setWidthAfterTwips(widthAfter);
                    int gridAfter = combinedGridColumnCount - combinedColumns;
                    Debug.assertTrue(gridAfter > 0);
                    row.getTablePr().setAttr(TableAttr.SYS_GRID_AFTER, gridAfter);
                }
            }
        }
    }

    private static int getTableX(Table table)
    {
        // Disregard negative indents.
        int indent = Math.max(0, table.getFirstRow().getTablePr().getLeftIndent());

        // The code somewhat parallels the code in layout, because it calculates similar things.
        // See RowMetrics.GetXForWord2010OrPriorCompatible() and CellMetrics.GetSpaceLeft().
        // I see no easy way to extract the common logic. Let it be for now.
        int borderAndPaddingShift = 0;
        Cell firstCell = table.getFirstRow().getFirstCell();
        Debug.assertTrue(firstCell != null);
        if (firstCell != null)
        {
            borderAndPaddingShift = table.getDocument().getDocPr().CompatibilityOptions.isWord2013OrLaterCompatible()
                ? getBorderShift(firstCell)
                : getBorderAndPaddingShiftCompatibility(firstCell);
        }

        // TestTableMergeIndentI confirms that border and padding must be accounted for.
        return indent + borderAndPaddingShift;
    }

    private static int getBorderAndPaddingShiftCompatibility(Cell firstCell)
    {
        int borderAndPaddingShift;
        if (firstCell.getParentTable().isNested())
        {
            borderAndPaddingShift = getBorderShift(firstCell);
        }
        else
        {
            // In compatibility mode, tables are shifted to the left of the column margins because of borders and cell padding.
            // It affects how they are merged.
            int padding = Extensions.resolvedLeftPadding(firstCell);
            int borderShift = getBorderShift(firstCell);

            borderAndPaddingShift = -Math.max(padding, borderShift);
        }

        return borderAndPaddingShift;
    }

    private static int getBorderShift(Cell firstCell)
    {
        // Even if there is widthBefore the first cell in the first row, the table position is still determined by its left border width.
        int borderWidth = Extensions.getLeftBorderTwips(firstCell);

        // In 2013 mode, the border of the first cell is aligned to the container margin.
        // So the grid actually starts half a border later.
        // The behavior is the same for tables nested in shapes, tables etc.
        // In compatibility mode, only nested tables behave this way.
        return MathUtil.divide(borderWidth, 2);
    }

    private static int getColumnRightFromTableGrid(IntList tableGrid, int firstColumn, int columnLeft,
                                                   int columnCount)
    {
        int columnRight = columnLeft;
        for (int i = 0; i < columnCount; ++i)
        {
            int column = firstColumn + i;
            boolean isTableGridColumnPresent = (column < tableGrid.getCount());
            columnRight += isTableGridColumnPresent
                ? tableGrid.get(column)
                : TableGridFromCellWidth.DEFAULT_FIXED_COLUMN_WIDTH_TWIPS;
        }
        // An array with cumulative column widths sums could be more effective.
        // Leave it be for now, this way there is less code , there is no need for additional arrays
        // and it is not a performance problem.

        return columnRight;
    }

    private static int getColumnCountFromCombinedGrid(SortedIntegerListGeneric<Object> combinedGrid, int columnRight)
    {
        int columnIndex = combinedGrid.indexOfKey(columnRight);

        // All cell boundaries should be present in the combined grid.
        Debug.assertTrue(columnIndex >= 0);
        return columnIndex;
    }

    /// <summary>
    /// Stack of lists of adjacent tables to be merged before table grid calculation.
    /// </summary>
    private /*final*/ msStack<ArrayList<Table>> mAdjacentTableLists = new msStack<ArrayList<Table>>();

    /// <summary>
    /// Attributes that do not prevent merging adjacent tables, both in the "old" and the "new" logic.
    /// </summary>
    private static /*final*/ int[] G_ATTRIBUTES_IGNORED = new int[]
    {
        // TableAttr.AllowAutoFit:
        // It appears that after some update that happened around Mar or Apr' 25, MS Word demonstrates a new behavior:
        // consecutive tables with different tblLayout values are no longer merged.
        // The new behavior is more logical, especially in the case when there are nested tables in merged tables,
        // as nested table layouts should be updated to match the container table.
        // TestTableMergeLayout*() demonstrate the new behavior.
        // Several customers requested the new behavior.
        // So TableAttr.AllowAutoFit was excluded from this list per WORDSNET-28521.
        // It broke several tests, and in some of those the layout differed significantly
        // and the customer had specifically wanted the old behavior (Test13434, Test20814).
        // But as the breaking change was actually in MS Word first, I believe that is OK.
        // AW logic now matches the current MS Word behavior.

        TableAttr.LEFT_PADDING, //4020
        TableAttr.HEADING_FORMAT, //4040
        TableAttr.BORDER_TOP, //4050
        TableAttr.BORDER_LEFT, //4060
        TableAttr.BORDER_BOTTOM, //4070
        TableAttr.BORDER_RIGHT, //4080
        TableAttr.BORDER_HORIZONTAL, //4090
        TableAttr.BORDER_VERTICAL, //4100
        TableAttr.ROW_HEIGHT, //4120
        TableAttr.PREFERRED_WIDTH, //4230
        TableAttr.WIDTH_BEFORE, //4250
        TableAttr.WIDTH_AFTER, //4260
        TableAttr.RIGHT_PADDING, //4320
        TableAttr.ALLOW_BREAK_ACROSS_PAGES, //4360
        TableAttr.RSID_TR, //4400
        TableAttr.SYS_GRID_AFTER, //5105
        TableAttr.SYS_CALCULATED_TABLE_GRID, //5106
        TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM, //5107
    };

    /// <summary>
    /// Attributes that do not prevent merging adjacent tables, in the "new" logic only.
    /// </summary>
    private static /*final*/ int[] G_ATTRIBUTES_IGNORED_NEW = new int[]
    {
        TableAttr.ISTD, // 4005
        TableAttr.ALIGNMENT, //4010
        TableAttr.STYLE_OPTIONS, //4140
        TableAttr.WIDTH_BEFORE_ORIGINAL, //4251
        TableAttr.WIDTH_AFTER_ORIGINAL, //4261
        TableAttr.SHADING, // 4330
        // Table with different indents are combined, but there is some special logic to handle indents.
        TableAttr.LEFT_INDENT, // 4340
        TableAttr.SYS_GRID_BEFORE, //5104
    };

    /// <summary>
    /// Attributes that do not prevent merging adjacent tables, in the "new" logic.
    /// </summary>
    /// <remarks>
    /// This is filled in the static constructor.
    /// </remarks>
    private static /*final*/ int[] G_ATTRIBUTES_IGNORED_COMBINED;
    static/* TableMerger()*/
    {
        // Sort the attributes array, to be on the safe side if someone changes the numbers.
        msArray.sort(G_ATTRIBUTES_IGNORED);

        // Merge old and new attributes to use with the new logic.
        G_ATTRIBUTES_IGNORED_COMBINED = new int[G_ATTRIBUTES_IGNORED.length + G_ATTRIBUTES_IGNORED_NEW.length];
        System.arraycopy((G_ATTRIBUTES_IGNORED), 0, (G_ATTRIBUTES_IGNORED_COMBINED), 0, (G_ATTRIBUTES_IGNORED.length));
        msArray.copy(G_ATTRIBUTES_IGNORED_NEW, 0, G_ATTRIBUTES_IGNORED_COMBINED,
            G_ATTRIBUTES_IGNORED.length, G_ATTRIBUTES_IGNORED_NEW.length);
        msArray.sort(G_ATTRIBUTES_IGNORED_COMBINED);
    }
}

