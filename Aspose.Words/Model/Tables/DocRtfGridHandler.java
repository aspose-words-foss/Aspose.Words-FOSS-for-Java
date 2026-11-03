// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2016 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.IntList;
import com.aspose.Debug;


/// <summary>
/// Implements methods for getting table column width information from cell widths specified in the document.
/// </summary>
/// <remarks>
/// It seems binary doc and rtf formats do not have tblGrid analogue stored in the document.
/// Instead, cell widths are stored for each table row.
/// The older TableGrid class generates the grid by intersecting all the stored cell widths.
/// The result is used similar to tblGrid in docx file.
/// It is used as a preferred width for cells with auto width in fixed table layout.
/// And the actual cell width may be different if a table is resized in order to meet table width,
/// and for other reasons (just like the width in tblGrid in docx is not final).
/// </remarks>
/*static*/ class DocRtfGridHandler
{
	/* Simulation of static class by using private constructor */
	private DocRtfGridHandler()
	{}

    /// <summary>
    /// Initializes grid and cell spans taking binary doc format specifics into account.
    /// </summary>
    /// <remarks>
    /// The method does nothing if load format is not doc or rtf.
    /// The method is to be used after loading the document before any model changes.
    /// Changing the model can make the widths stored in the model inaccurate.
    /// </remarks>
    static void getGridAndSpansFromCellWidths(Table table)
    {
        if (isGridStoredInCellWidths(table) && table.getFirstRow()!= null)
        {
            TableGridFromCellWidth gridFromDoc = TableGridFromCellWidth.getDocRtfGridFromAttrs(table);

            // Treat it the same way as the original tblGrid is treated for docx files.
            IntList grid = gridFromDoc.toIntList();

            TableGridColumnsAttr gridColumns = new TableGridColumnsAttr(grid);
            table.getTablePr().setAttr(TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM, gridColumns);

            // Update the grid spans in the table so that they match the cell widths and the generated grid.
            updateGridSpans(table, gridFromDoc);
            // Cells having zero width can be removed in the process,
            // but it requires updating some tests that check the model built with zero-width cells present.
            // I think it is better to do it as a separate change, there is a large delta for the table merge already.
        }
    }

    /// <summary>
    /// Gets a boolean value indicating if the Document of the given table node
    /// was loaded from doc or rtf formats which requires grid span calculation from the cell widths in the document.
    /// </summary>
    static boolean isGridStoredInCellWidths(Node table)
    {
        Document doc = ms.as(table.getDocument(), Document.class);

        // Return false for documents other than main document.
        if (doc == null)
            return false;

        switch (doc.getOriginalLoadFormat())
        {
            case LoadFormat.RTF:
            case LoadFormat.DOC:
            case LoadFormat.DOC_PRE_WORD_60:
            case LoadFormat.DOT:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Updates grid spans for the table cells so that they match cell width and the constructed grid.
    /// </summary>
    private static void updateGridSpans(Table table, TableGridFromCellWidth gridFromDoc)
    {
        // Go through cells and check if gridSpan needs updating.
        // In doc files, grid span may not be stored explicitly, just derived from cell widths.
        for (Row row : (Iterable<Row>) table.getRows())
        {
            gridFromDoc.nextRow(row);

            // Row grid before calculated from the grid may be different from the attribute value in some cases.
            // Do not overwrite width before in percent units.
            if (!row.getTablePr().getWidthBefore().isPercent() && gridFromDoc.getBefore() != row.getTablePr().getWidthBeforeTwips())
            {
                if (gridFromDoc.getBefore() != 0)
                    row.getTablePr().setWidthBeforeTwips(gridFromDoc.getBefore());
                else
                    row.getTablePr().remove(TableAttr.WIDTH_BEFORE);
            }

            if (gridFromDoc.getGridBefore() != row.getTablePr().getGridBefore())
            {
                // It seems, in rtf files gridBefore can be missing when widthBefore is specified.
                Debug.assertTrue(row.getTablePr().getGridBefore() == 0);

                // Set it to the calculated value.
                row.getTablePr().setAttr(TableAttr.SYS_GRID_BEFORE, gridFromDoc.getGridBefore());
            }

            for (Cell cell : (Iterable<Cell>) row.getCells())
            {
                Debug.assertTrue(!gridFromDoc.isLastCell());
                int spanCountFromGrid = gridFromDoc.nextCell();

                if (spanCountFromGrid == 0)
                {
                    // Zero span count should only happen for a cell with a width within the tolerance.
                    Debug.assertTrue(cell.getCellPr().getWidth() <= TableGridFromCellWidth.getWidthTolerance(),
                        "Unexpected span count value for a grid generated from cell widths in doc/rtf document.");
                }
                cell.getCellPr().setGridSpan(spanCountFromGrid);
            }

            setRowAfter(row, gridFromDoc.getAfter(), gridFromDoc.getGridAfter());
        }
    }

    /// <summary>
    /// Updates row grid/width after with the given values (calculated from cell widths in the document).
    /// </summary>
    private static void setRowAfter(Row row, int widthAfterFromDoc, int gridAfterFromDoc)
    {
        TablePr rowPr = row.getTablePr();
        if (widthAfterFromDoc > 0)
        {
            Debug.assertTrue(gridAfterFromDoc > 0);
            int gridAfter = Math.max(1, gridAfterFromDoc);
            rowPr.setAttr(TableAttr.SYS_GRID_AFTER, gridAfter);

            if (rowPr.getWidthAfterTwips() != widthAfterFromDoc)
            {
                Debug.writeLine("WidthAfter calculated from cell widths does not match the value read from the document. Document value is used.");
                // This happens with a document from TestJira6793.
                // The original document is inconsistent: similar rows have different widthAfter/gridAfter.
                // Also the document has revisions. But even if the source docx is re-saved via MS Word with revisions accepted,
                // widthAfter in the re-saved document does not match the stored grid.
                // So even when the document is saved to .doc and re-opened, preserved widthAfter values
                // may not match the actual column widths. I removed the assertion that used to be here.
                // Also happens with TestJira9653FormatRevision, but the difference is minor there.
            }

            // It should not happen, but do not overwrite widthAfter in pct units, just in case.
            if (rowPr.getWidthAfter().getType() != PreferredWidthType.PERCENT)
                rowPr.setWidthAfterTwips(widthAfterFromDoc);
            else
                Debug.fail("Unexpected: trying to overwrite with after in pct units with twips.");
        }
        else
        {
            // Zero grid after should be in the model.
            if (rowPr.getGridAfter() != 0)
            {
                rowPr.setAttr(TableAttr.SYS_GRID_AFTER, 0);
                Debug.writeLine("GridAfter calculated from cell widths is zero, but a not zero value is specified in the model. Replaced with 0.");
            }
            if (rowPr.getWidthAfterTwips() != 0)
            {
                rowPr.setWidthAfterTwips(0);
                Debug.writeLine("WidthAfter calculated from cell widths is zero, but a not zero value is specified in the model. Replaced with 0.");
                // The issue happens with TestJira9656, TestJira9653FormatRevision (both with revisions).
                // TODO investigate merging of tables with revisions.
            }
        }
    }
}

