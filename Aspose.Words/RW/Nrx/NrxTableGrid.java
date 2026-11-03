// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/07/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.IntList;
import java.util.ArrayList;
import com.aspose.ms.System.DateTime;



/// <summary>
/// Implements reading/writing of the tblGrid element.
/// </summary>
/*static*/ class NrxTableGrid
{
	/* Simulation of static class by using private constructor */
	private NrxTableGrid()
	{}

    /// <summary>
    /// Writes 'w:tblGrid' element for the current table and 'w:tblGridChange' element if table has formatting revision.
    /// The table grid is a definition of the set of grid columns which define all of the shared
    /// vertical edges of the table, as well as default widths for each of these grid columns.
    /// </summary>
    static void writeTblGrid(NrxXmlBuilder builder, Table table)
    {
        TablePr tablePr = table.getTablePr();

        IntList tableGrid = getTableGrid(tablePr.getRevisedPr());
        IntList tableGridChange = tablePr.hasFormatRevision() ? getTableGrid(tablePr) : null;

        builder.startElement("w:tblGrid");
        for (int i = 0; i < tableGrid.getCount(); i++)
            builder.writeElementWithAttributes("w:gridCol", "w:w", tableGrid.get(i));

        if(tableGridChange != null)
        {
            builder.startElement("w:tblGridChange");
            builder.startElement("w:tblGrid");

            for (int i = 0; i < tableGridChange.getCount(); i++)
                builder.writeElementWithAttributes("w:gridCol", "w:w", tableGridChange.get(i)); 
            
            builder.endElement();
            builder.endElement();
        }

        builder.endElement(); //w:tblGrid
    }

    /// <summary>
    /// Reads 'tblGrid' element which specifies table grid for current table.
    /// </summary>
    static void readTblGrid(NrxXmlReader partReader, TablePr tablePr, OoxmlComplianceInfo complianceInfo)
    {
        IntList tableGrid = new IntList();
        tablePr.setAttr(TableAttr.SYS_TABLE_GRID, tableGrid);

        while (partReader.readChild("tblGrid"))
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"gridCol"*/0:
                    // DD: this might be a good piece to make a function, but it is used only once so far. So leave as is.
                    int width = 0;
                    while (partReader.moveToNextAttribute())
                    {
                        if ("w".equals(partReader.getLocalName()))
                            width = partReader.getValueAsTwips(complianceInfo);
                    }
                    tableGrid.add(width);
                    break;

                case /*"tblGridChange"*/1:
                    readTblGridChange(partReader, tablePr, complianceInfo);
                    break;

                default:
                    partReader.ignoreElement();
                    break;
            }
        }

        // Save a copy of the grid as stored in the document, before the grid calculation tweaked it in any way.
        // The copy is used when adjacent tables are merged.
        backupTableGrid(tablePr);
    }

    /// <summary>
    /// Updates cell widths from table grid.
    /// </summary>
    static void tableGridToCellWidths(Row row, ArrayList<Table> tablesWithMissedTableGrid)
    {
        Table table = row.getParentTable();
        
        // There is one file with TableGrid missed, build it from first row.
        if (!hasValidTableGrid(table.getTablePr()))
        {
            buildTableGridFromFirstRow(table);
            tablesWithMissedTableGrid.add(table);
        }

        if (table.getTablePr().hasFormatRevision())
        {
            // In case table has table grid changed we have to additionally update before-changes cell widths.
            tableGridToCellWidths(row, RevisionsView.ORIGINAL);
        }
        
        tableGridToCellWidths(row, RevisionsView.FINAL);
    }

    private static boolean hasValidTableGrid(TablePr tablePr)
    {
        IntList tableGrid = (IntList)tablePr.get(TableAttr.SYS_TABLE_GRID);

        if ((tableGrid == null) || (tableGrid.getCount() == 0))
            return false;

        int totalWidth = 0;
        for (int i = 0; i < tableGrid.getCount(); i++)
            totalWidth += tableGrid.get(i);

        return totalWidth > 0;
    }

    /// <summary>
    /// Updates either cell before-widths or after-widths from table grid depending on isRevised parameter.
    /// </summary>
    private static void tableGridToCellWidths(Row row, /*RevisionsView*/int view)
    {
        IntList tableGrid = (IntList)row.getParentTable().getTablePr().fetchAttr(TableAttr.SYS_TABLE_GRID, view);

        if (row.isFirstRow())
            validateTableGrid(tableGrid, row, view);

        int gridBefore = (/*int*/Integer)row.getTablePr().fetchAttr(TableAttr.SYS_GRID_BEFORE, view);
        updateWidthBefore(tableGrid, row, view);

        if (tableGrid.getCount() > 0)
        {
            int gridIndex = gridBefore;

            // RK Set fixed cell width in the model from the grid.
            for (Cell cell : (Iterable<Cell>) row.getCells())
            {
                CellPr cellPr = cell.getCellPr();
                int gridSpan = (/*int*/Integer)cellPr.fetchAttr(CellAttr.SYS_CELL_SPAN, view);
                int width = 0;

                int maxGridIndex = Math.min(gridIndex + gridSpan, tableGrid.getCount());
                while (gridIndex < maxGridIndex)
                    width += tableGrid.get(gridIndex++);

                // Here we have two options. Cell has format revision or not.
                // If case of cell has no format revision cell widths should be updated from revised table grid otherwise
                // cell before-widths should be updated from before-grid and after-widths from after-grid.
                // Proper table grid is chosen depending isAccepted value.
                //
                // Note we process with accepted properties but it's just clone of real node attributes.
                // So we must to update real node attributes.
                if (view == RevisionsView.FINAL)
                {
                    // After-grid is in process. Update after-width if cell has revision or cell width otherwise.
                    cellPr.getRevisedPr().setWidth(width);
                }
                else
                {
                    // Before-grid is in process. Update only before-width and only if cell has revision.
                    if (cellPr.hasFormatRevision())
                        cellPr.setWidth(width);
                }
            }
        }
    }

    /// <summary>
    /// Validates table grid. 
    /// Currently checks that number of columns corresponds to number of table grid elements and 
    /// rebuild table grid if needed.
    /// </summary>
    private static void validateTableGrid(IntList tableGrid, Row row, /*RevisionsView*/int view)
    {
        int columnCount = (/*int*/Integer)row.getTablePr().fetchAttr(TableAttr.SYS_GRID_BEFORE, view) + (/*int*/Integer)row.getTablePr().fetchAttr(TableAttr.SYS_GRID_AFTER, view);
        for (Cell cell : (Iterable<Cell>) row.getCells())
            columnCount += (/*int*/Integer) cell.getCellPr().fetchAttr(CellAttr.SYS_CELL_SPAN, view);

        if (columnCount > tableGrid.getCount())
        {
            // Table grid has insufficient count of elements. Rebuild it.
            if (row.getTablePr().getAllowAutoFit())
            {
                int totalWidth = 0;
                for (int i = 0; i < tableGrid.getCount(); i++)
                    totalWidth += tableGrid.get(i);

                tableGrid.clear();

                for (int i = 0; i < columnCount; i++)
                    tableGrid.add(totalWidth / columnCount);
            }
            // WORDSNET-11185 If table with insufficient grid cols has layout type 'fixed',
            // then MS Word adds an extra grid cols with fixed width.
            else
            {
                while (columnCount > tableGrid.getCount())
                    tableGrid.add(DEFAULT_GRID_COL_WIDTH_TWIPS);
            }
        }
    }

    /// <summary>
    /// This is a part of an older logic implemented by TableGridToCellWidths.
    /// </summary>
    /// <remarks>
    /// The logic only works correctly in case the table has correct widths stored in tblGrid element.
    /// Even with the correct tblGrid, replacing widthBefore values stored in the document with values calculated from the grid
    /// may change the table layout.
    /// The logic is still needed to preserve AW behavior in scenarios not handled by the new table layout algorithm.
    /// </remarks>
    private static void updateWidthBefore(IntList tableGrid, Row row, /*RevisionsView*/int view)
    {
        int gridBefore = (/*int*/Integer)row.getTablePr().fetchAttr(TableAttr.SYS_GRID_BEFORE, view);

        int widthBefore = 0;
        for (int i = 0; i < gridBefore; i++)
        {
            // andrnosk Resilience WORDSNET-8211 
            if (tableGrid.getCount() > i)
                widthBefore += tableGrid.get(i);
        }

        // Update either original or final properties.
        TablePr rowPr = (view == RevisionsView.FINAL && row.getTablePr().hasFormatRevision()) 
            ? (TablePr)row.getTablePr().getFormatRevision().getRevPr() 
            : row.getTablePr();

        // This will certainly overwrite width before specified in pct units, but that what AW always did.
        rowPr.setWidthBeforeTwips(widthBefore);
    }
    
    /// <summary>
    /// Builds table grid from first row of table. Used if only table grid is missed.
    /// </summary>
    private static void buildTableGridFromFirstRow(Table table)
    {
        IntList tableGrid = new IntList();
        table.getTablePr().setAttr(TableAttr.SYS_TABLE_GRID, tableGrid);

        // RK The grid is not defined or empty. Let's build a grid using cells of (the first) row.
        for (Cell cell : (Iterable<Cell>) table.getFirstRow().getCells())
        {
            cell.getCellPr().validateWidth();
            tableGrid.add(cell.getCellPr().getWidth());
        }
    }

    /// <summary>
    /// Reads 'tblGridChange' element which specifies original table grid in revision.
    /// </summary>
    private static void readTblGridChange(NrxXmlReader partReader, TablePr tablePr, OoxmlComplianceInfo complianceInfo)
    {
        while (partReader.readChild("tblGridChange"))
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"tblGrid"*/2:
                {
                    // if we are reading 'Change' table grid it means that table grid that was read before is actually after-changes table grid and
                    // this one is before-changes table grid.
                    // 1. Move already read grid to format revision.
                    IntList tableGrid = (IntList)tablePr.get(TableAttr.SYS_TABLE_GRID);
                    if (tablePr.getFormatRevision() == null)
                        tablePr.setFormatRevision(new FormatRevision(new TablePr(), "", DateTime.MinValue));
                    tablePr.getFormatRevision().getRevPr().setAttr(TableAttr.SYS_TABLE_GRID, tableGrid);

                    // 2. Create and read new table grid.
                    readTblGrid(partReader, tablePr, complianceInfo);
                    break;
                }

                default:
                    partReader.ignoreElement();
                    break;
            }
        }

        backupTableGrid(tablePr);
    }

    /// <summary>
    /// Helper method returns TableGrid from given table attrs.
    /// </summary>
    private static IntList getTableGrid(WordAttrCollection tablePr)
    {
        return (IntList)tablePr.getDirectAttr(TableAttr.SYS_TABLE_GRID);
    }

    private static void backupTableGrid(TablePr tablePr)
    {
        IntList grid = (IntList)tablePr.get(TableAttr.SYS_TABLE_GRID);
        if (grid == null)
            return;

        IntList gridClone = new IntList(grid.getCount());
        gridClone.addRange(grid);
        tablePr.setAttr(TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM, new TableGridColumnsAttr(gridClone));
    }

    /// <summary>
    /// Default grid col width 0,25" to be used in case when extra grid cols should be added.
    /// </summary>
    private static final int DEFAULT_GRID_COL_WIDTH_TWIPS = 360;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"gridCol",
		"tblGridChange",
		"tblGrid"
	);

}

