// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/09/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.MathUtil;
import com.aspose.ms.System.msMath;
import com.aspose.HashSetGeneric;


/// <summary>
/// Validates tables, rows and cells during document save.
/// </summary>
class TableValidator
{
    TableValidator(/*DocumentValidatorActions*/int saveActions, SaveInfo saveInfo)
    {
        mWordTableLimits = (saveActions & DocumentValidatorActions.WORD_TABLE_LIMITS) != 0;

        mIsNormalizeHorizontalMerge = (saveActions & DocumentValidatorActions.NORMALIZE_HORIZONTAL_MERGE) != 0;
        mExplicitAutoPreferredWidth = (saveActions & DocumentValidatorActions.EXPLICIT_AUTO_PREFERRED_WIDTH) != 0;
        mSaveInfo = saveInfo;
    }

    /*VisitorAction*/int visitTableStart(Table table)
    {
        if (table.hasCells())
        {
            // RK This is for rendering at the moment. Ideally, should do for all formats as it will allow to simplify writers.
            if (mIsNormalizeHorizontalMerge)
            {
                boolean anyModified = false;
                for (Row row : (Iterable<Row>) table.getRows())
                    for (Cell cell : (Iterable<Cell>) row.getCells())
                        anyModified |= cell.getCellPr().validateWidth();

                if(anyModified)
                    updateTableLayoutOnce(table);

                table.normalizeHorizontalMerge();
            }
            else
            {
                TableGridFromCellWidth tableGrid = new TableGridFromCellWidth(table);
                if (tableGrid.getColumnWidths().length == 0)
                    updateTableLayoutOnce(table);
            }

            table.normalizeVerticalMerge();

            validateTableWidth(table);
            validateTablePr(table);
        }

        return VisitorAction.CONTINUE;
    }

    private boolean updateTableLayoutOnce(Table table)
    {
        if (mUpdatedTables.contains(table))
            return false;

        table.updateLayout();
        mUpdatedTables.add(table);
                    return true;
    }

    private static void validateTablePr(Table table)
    {
        if (table.getFirstRow() == null)
            return;

        // Dmatv: WORDSNET-25085: The logic below seems to match MS Word behavior at the moment of 25085 fix.
        // Conflicting table x/y and alignment attributes are resolved by removing tblpX, tblpY.
        // Note: for frames in case of similar conflicts, MS Word behaves differently.
        // The logic below supersedes the earlier fixes:
        //      andrnosk: WORDSNET-7084 Table vertically positioned relatively to a paragraph, and in the same time vertically centered.
        //      17370 forcing negative table position for "outside" alignment.

        TablePr tablePr = table.getFirstRow().getTablePr();
        /*HorizontalAlignment*/int ha = tablePr.getHorizontalAlignment();
        boolean isHorizontalAlignmentSpecified = (ha != HorizontalAlignment.NONE);
        if (isHorizontalAlignmentSpecified)
            table.removeAttrFromAllRows(TableAttr.FRAME_LEFT);

        /*VerticalAlignment*/int va = tablePr.getVerticalAlignment();
        boolean isVerticalAlignmentSpecified = (va != VerticalAlignment.NONE) && (va != VerticalAlignment.INLINE);
        if (isVerticalAlignmentSpecified)
            table.removeAttrFromAllRows(TableAttr.FRAME_TOP);

        // No model changes are needed for an invalid combination of paragraph-relative vertical position and vertical alignment.
        // MS Word does not change the model on re-saving in this case.
        // The invalid combination is handled by the layout code.

        // The above logic was not tested for cases when conflicting attributes are defined via styles.
        // Removing direct table attributes will not work if conflicting x or y came from a style.
    }

    /*VisitorAction*/int visitRowStart(Row row)
    {
        if (mWordTableLimits)
            if (row.getCells().getCount() > Table.MAX_COLUMNS)
                throw new IllegalStateException("More than 63 cells per row is not supported for this file format.");

        if (row.getTablePr().getHeight() > ConvertUtilCore.MAX_SIZE_TWIP)
        {
            row.getTablePr().setHeight(ConvertUtilCore.MAX_SIZE_TWIP);
            WarningUtil.warnUnexpected(getWarningCallback(), WarningStrings.ROW_HEIGHT_EXCEED_LIMIT);
        }

        if (row.getCells().getCount() >= Table.MAX_COLUMNS)
            WarningUtil.warnUnexpected(getWarningCallback(), WarningStrings.TABLE_VALIDATOR_WORD_CELL_LIMIT_EXCEED);

        ThemeColorUpdater.update(row);

        removeInvalidAttributes(row);
        row.validateAlignedIndent();

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Checks the values of some attributes and removes the if they are not valid.
    /// </summary>
    /// <remarks>
    /// Some docs have negative gridAfter (TestJira8211).
    /// This method is called from DocumentPostLoader as well, so that grid calculation deals with correct attributes.
    /// </remarks>
    static void removeInvalidAttributes(Row row)
    {
        removeNegativeIntAttribute(row.getTablePr(), TableAttr.SYS_GRID_BEFORE);
        removeNegativeIntAttribute(row.getTablePr(), TableAttr.SYS_GRID_AFTER);
    }

    private static void removeNegativeIntAttribute(TablePr rowPr, int attrKey)
    {
        Object attrValue = rowPr.getDirectAttr(attrKey);

        if ((attrValue != null) && ((/*int*/Integer)attrValue < 0))
        {
            Debug.writeLine(msString.format("Invalid value {0} of attribute {1} removed from a row.", attrValue, attrKey));
            rowPr.remove(attrKey);
        }
    }

    /*VisitorAction*/int visitTableEnd(Table table)
    {
        // Resiliency: Don't write empty table. In fact, delete a completely empty table.
        // RK We do this in the table end handler because the table could become empty
        // during validation if all rows are empty and deleted from it.
        //
        // After this no further checks should be made because we deleted the table.
        if (table.getFirstRow() == null)
        {
            warn(WarningType.MINOR_FORMATTING_LOSS, WarningStrings.TABLE_VALIDATOR_EMPTY_TABLE);
            table.remove();
        }

        return VisitorAction.CONTINUE;
    }

    /*VisitorAction*/int visitRowEnd(Row row)
    {
        if (row.getFirstCell() == null)
        {
            // Resiliency: Don't write empty rows. In fact, delete a completely empty row.
            warn(WarningType.MINOR_FORMATTING_LOSS, WarningStrings.TABLE_VALIDATOR_EMPTY_ROW);
            row.remove();
            return VisitorAction.CONTINUE;
        }

        // WORDSNET-905 MS Word 2000 crashes if the first cell is merged to previous.
        Cell firstCell = row.getFirstCell();
        if (firstCell.getCellPr().getHorizontalMerge() == CellMerge.PREVIOUS)
            firstCell.getCellPr().setHorizontalMerge(CellMerge.FIRST);

        // Last cell should not be first merged.
        Cell lastCell = row.getLastCell();

        if (lastCell.getCellPr().getHorizontalMerge() == CellMerge.FIRST)
            lastCell.getCellPr().setHorizontalMerge(CellMerge.NONE);

        validateEditRevision(row);

        // Dmatv: originally the logic was called for docx/wml only from the readers, but I moved it
        // to avoid applying to intermediate TablePr state before tblPrEx etc are processed.
        // Also it seems that to emulate MS Word the logic should be applied to other formats as well.
        removeDisregardedProperties(row.getTablePr());

        if (expandHtmlBlockAttrs(row))
            mSaveInfo.HasHtmlBlockReferences = true;

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Reverts attributes modified by expanding procedures.
    /// </summary>
    void revert()
    {
        if (mHtmlBlockRows.getCount() == 0)
            return;

        for(Row row : mHtmlBlockRows)
            row.getDocument().getHtmlBlockCollection().removeFormatting(row.getTablePr());
    }

    /// <summary>
    /// Expands <see cref="HtmlBlock"/> attributes to the table row properties.
    /// </summary>
    /// <remarks>
    /// These attributes will be reverted after saving. See <see cref="Revert"/>
    /// </remarks>
    private boolean expandHtmlBlockAttrs(Row row)
    {
        if (!row.getTablePr().contains(TableAttr.HTML_BLOCK_ID))
            return false;

        int htmlBlockId = (/*int*/Integer)row.getTablePr().get(TableAttr.HTML_BLOCK_ID);
        // Do not place to the formatting expander because a document may be without table styles.
        row.getDocument().getHtmlBlockCollection()
            .expand(row.getTablePr(), htmlBlockId, !(row.getParentTable().getAncestor(NodeType.TABLE) instanceof Table));

        mHtmlBlockRows.add(row);

        return true;
    }

    /// <summary>
    /// Removes table properties related to floating position and text wrapping if they should be disregarded.
    /// </summary>
    /// <remarks>
    /// It seems that MS Word does not take into account and removes the following table properties from the documents:
    /// - tblpXSpec = left;
    /// - vertAnchor = page, when the table is not floating;
    /// - all "distance from text" attributes when the table is not floating.
    /// <see cref="TablePr.IsFloating"/> for the logic that determines if the table is floating.
    /// </remarks>
    private static void removeDisregardedProperties(TablePr tablePr)
    {
        // Remove tblpXSpec attribute if its value is Left.
        if (tablePr.getHorizontalAlignment() == HorizontalAlignment.LEFT)
            tablePr.remove(TableAttr.HORIZONTAL_ALIGNMENT);
        // Dmatv it even works correctly with Page alignment
        // because of a special condition in layout positioning logic that substitutes Left as needed.
        // Leave it as is as MS Word does remove Left alignment on re-saving.

        if (!tablePr.isFloating())
        {
            // Remove relative vertical position Page if other floating-position-related properties are default (checked by IsFloating).
            tablePr.remove(TableAttr.RELATIVE_VERTICAL_POSITION);

            // Remove FrameDistanceFrom* attributes if the table is not floating.
            tablePr.remove(TableAttr.FRAME_DISTANCE_FROM_LEFT);
            tablePr.remove(TableAttr.FRAME_DISTANCE_FROM_TOP);
            tablePr.remove(TableAttr.FRAME_DISTANCE_FROM_RIGHT);
            tablePr.remove(TableAttr.FRAME_DISTANCE_FROM_BOTTOM);
        }
    }

    /// <summary>
    /// If all paragraph in row have InsertRevision/DeleteRevisions we can guess that entire row was inserted/deleted.
    /// This is how insert/delete revision for rows works in DOC format.
    /// In DOCX we read insert and delete revisions directly from document.
    /// </summary>
    private static void validateEditRevision(Row row)
    {
        boolean isInsertedRow = true;
        boolean isDeletedRow = true;
        for (Cell cell : (Iterable<Cell>) row.getCells())
        {
            isInsertedRow &= cell.getLastParagraph().getParagraphBreakRunPr().hasInsertRevision();
            isDeletedRow &= cell.getLastParagraph().getParagraphBreakRunPr().hasDeleteRevision();
        }

        if (isInsertedRow && !row.getTablePr().hasInsertRevision())
            row.getTablePr().setInsertRevision(new EditRevision(EditRevisionType.INSERTION, row.getFirstCell().getLastParagraph().getParagraphBreakRunPr().getInsertRevision().getAuthor(),
                                                          row.getFirstCell().getLastParagraph().getParagraphBreakRunPr().getInsertRevision().getDateTime()));

        if (isDeletedRow && !row.getTablePr().hasDeleteRevision())
            row.getTablePr().setDeleteRevision(new EditRevision(EditRevisionType.DELETION, row.getFirstCell().getLastParagraph().getParagraphBreakRunPr().getDeleteRevision().getAuthor(),
                                                          row.getFirstCell().getLastParagraph().getParagraphBreakRunPr().getDeleteRevision().getDateTime()));
    }

    /*VisitorAction*/int visitCellStart(Cell cell)
    {
        if (mExplicitAutoPreferredWidth)
            if(!cell.getCellPr().contains(CellAttr.PREFERRED_WIDTH))
                cell.getCellPr().setPreferredWidth(PreferredWidth.AUTO);

        // WORDSNET-956 A cell needs at least one paragraph to be valid.
        cell.ensureMinimum();

        // WORDSNET-5164 RK At first we want to validate width to make sure we don't write zero width to the file.
        // But also, if the width had to be corrected because it was zero it is a good update to recalculate the table to update width.
        // Recalculation of the table will produce better results because validation performs only a basic correction.
        if (cell.getCellPr().validateWidth())
        {
            warn(WarningType.MINOR_FORMATTING_LOSS, WarningStrings.TABLE_VALIDATOR_INVALID_CELL_WIDTH);
            // WORDSJAVA-898 Table.setAllowAutoFit(false) takes much time as compared to Table.setAllowAutoFit(true)
            // If the table contains 1000 cells (e.g. 1000 rows and 1 column) then UpdateLayout() is invoked 1000 times.
            // UpdateLayout() calculates width of each cell by invoking TableLayouter.CalculateColumnLayoutWidths().
            // Thus the inner loop in TableLayouter.CalculateColumnLayoutWidths() may be executed up to 1000 * 1000 times.
            // We can update layout only once instead of doing it for each cell.
            if (updateTableLayoutOnce(cell.getParentTable()))
            {
                // Once we update layout we need to validate table width again.
                validateTableWidth(cell.getParentTable());
            }
        }

        // WORDSNET-17118 Cells padding values have to be casted to the two bytes integers, according to the Word behavior.
        validateCellPadding(cell);

        ThemeColorUpdater.update(cell);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Validates padding attributes of the cell.
    /// </summary>
    private static void validateCellPadding(Cell cell)
    {
        CellPr cellPr = cell.getCellPr();

        validatePaddingAttr(cellPr, CellAttr.LEFT_PADDING);
        validatePaddingAttr(cellPr, CellAttr.RIGHT_PADDING);
        validatePaddingAttr(cellPr, CellAttr.TOP_PADDING);
        validatePaddingAttr(cellPr, CellAttr.BOTTOM_PADDING);
    }

    /// <summary>
    /// Validates passed padding attribute in the specified collection of cell properties.
    /// </summary>
    private static void validatePaddingAttr(CellPr cellPr, int key)
    {
        if (!cellPr.contains(key))
            return;

        cellPr.set(key, MathUtil.castIntToShort((/*int*/Integer)cellPr.get(key)));
    }

    /// <summary>
    /// Indicates that table width is affected by container width.
    /// </summary>
    private static boolean isContainerAffected(Table table)
    {
        // Skip empty tables.
        if (table.getRows().getCount() == 0)
            return false;

        // Floating tables are not affected by container width.
        if (table.isFloating())
            return false;

        if (table.isNested())
        {
            // Nested table is affected by container if it has percent preferred width.
            return table.getPreferredWidth().isPercent() || table.getPreferredWidth().isAuto();
        }
        else
        {
            if (table.getAllowAutoFit())
            {
                // Auto fit table is affected by container if not GrowAutoFit compatibility options set.
                return !table.getDocument().getDocPr().CompatibilityOptions.getGrowAutofit();
            }
            else
            {
                // Top-level table is affected by container if it has percent preferred width.
                return table.getPreferredWidth().isPercent();
            }
        }
    }

    /// <summary>
    /// Validates that table grid is valid and shrinks table to fit into container margins is needed.
    /// </summary>
    /// <remarks>
    /// AM. Actually Word doesn't update cells width at all and
    /// only cares that total cells width not exceed 31680 (widely used Word maximum).
    ///
    /// But layout engine completely relies on cell width so we have to update them and
    /// mimic Word rendering behavior. So below comments refer to Word renderer behavior
    /// rather than Word export to file algorithm.
    /// </remarks>
    private void validateTableWidth(Table table)
    {
        // Total cell width must not exceed 31680 otherwise Word is not able to render table properly.
        int cellsWidth = table.getCellsWidth();

        Section parentSection = (Section)table.getAncestor(NodeType.SECTION);
        int parentSectionWidth = (parentSection != null) ? parentSection.getSectPr().getPageWidth() : 0;

        // WORDSNET-12477 Allow table goes beyond MaxSizeTwip if target is not MS Word native document.
        // After some discussion we decided to allow table goes beyond MaxSizeTwip only if parent page is wider than MaxSizeTwip
        // to avoid possible regression.
        if ((mWordTableLimits || (parentSectionWidth <= ConvertUtilCore.MAX_SIZE_TWIP)) && (cellsWidth > ConvertUtilCore.MAX_SIZE_TWIP))
        {
            shrinkCells(table, ConvertUtilCore.MAX_SIZE_TWIP);
            // Get updated table cell width.
            cellsWidth = table.getCellsWidth();
        }

        if (!isContainerAffected(table))
            return;

        int newWidth = cellsWidth;
        // Do not try resizing fixed layout table to fit container, unless the table width is in percents.
        if (table.getAllowAutoFit() || table.getPreferredWidth().getType() == PreferredWidthType.PERCENT)
            newWidth = Extensions.getMaxAllowedWidthResolveTblwPct(table);

        // Word processes fixed width table is quite strange way.
        if (table.getPreferredWidth().isFixed())
        {
            // If table preferred width is greater than container Word renders table as is (cells are not shrink).
            if (table.getPreferredWidth().getValueTwips() >= newWidth)
            {
                // Verify that total cells width is less than Word maximum value.
                newWidth = (cellsWidth < ConvertUtilCore.MAX_SIZE_TWIP) ? cellsWidth : ConvertUtilCore.MAX_SIZE_TWIP;
            }
        }

        // AM. Accept 1mm extent as valid, trying to update as few tables as possible.
        if (cellsWidth > (newWidth + G_MAX_ALLOWED_EXTENT))
            shrinkCells(table, newWidth);
    }

    /// <summary>
    /// Shrink table cells to get total table cells width to be equal to given width.
    /// </summary>
    private void shrinkCells(Table table, int newWidth)
    {
        int cellsWidth = table.getCellsWidth();

        double scale = (double)(newWidth) / cellsWidth;
        if (scale < 1)
        {
            // AM. Last check. I put it here in order to do not check table revisions often.
            // Insert cell revision is poorly implemented in Word
            // (See UnifiedTestInsertCellRevision remarks for example) and moreover
            // it seems that table has inconsistent attribute values when in format revision
            // so I decided t exclude such table from validation at all till users complain.
            if (table.hasRevision())
                return;

            warn(WarningType.MINOR_FORMATTING_LOSS, WarningStrings.TABLE_VALIDATOR_TABLE_RESIZED);

            // Scale factor less than 1 if margin width is less than cells width. Scale table in this case.
            for (Row row : (Iterable<Row>) table.getRows())
            {
                int totalWidthInRow = 0;

                for (Cell cell : (Iterable<Cell>) row.getCells())
                {
                    cell.getCellPr().setWidth((int)msMath.round(scale * cell.getCellPr().getWidth()));
                    totalWidthInRow += cell.getCellPr().getWidth();
                }

                // Adjust last cell if needed to get total cell width the same for all rows.
                int lastCellWidth = row.getLastCell().getCellPr().getWidth() + (newWidth - totalWidthInRow);

                if (lastCellWidth >= 0)
                    row.getLastCell().getCellPr().setWidth(lastCellWidth);
            }
        }
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int warningType, String description)
    {
        if (getWarningCallback() != null)
            getWarningCallback().warning(new WarningInfo(warningType, WarningSource.VALIDATOR, description));
    }

    private /*final*/ HashSetGeneric<Table> mUpdatedTables = new HashSetGeneric<Table>();
    private /*final*/ HashSetGeneric<Row> mHtmlBlockRows = new HashSetGeneric<Row>();

    private /*final*/ boolean mIsNormalizeHorizontalMerge;

    /// <summary>
    /// True if auto width should be explicitly set.
    /// </summary>
    private /*final*/ boolean mExplicitAutoPreferredWidth;

    /// <summary>
    /// Validator will throw an exception if cell count is exceed allowed limit for current save format and
    /// will shrink table width to maximum allowed size.
    /// </summary>
    private /*final*/ boolean mWordTableLimits;

    /// <summary>
    /// Maximum allowed table extent into container margins. 1mm in twips.
    /// </summary>
    private static /*final*/ int G_MAX_ALLOWED_EXTENT = ConvertUtilCore.pointToTwip(ConvertUtil.millimeterToPoint(1.0));

    private IWarningCallback getWarningCallback() { return mSaveInfo.Document.getWarningCallback(); }
    private /*final*/ SaveInfo mSaveInfo;
}

