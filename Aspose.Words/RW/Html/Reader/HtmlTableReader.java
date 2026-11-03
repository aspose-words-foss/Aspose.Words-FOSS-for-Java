// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.MathUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Responsible for importing HTML table.
/// </summary>
class HtmlTableReader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="builder">Current builder</param>
    /// <param name="cssStyleTracker">CSS style tracker.</param>
    /// <param name="tableFormatter">Table formatter.</param>
    /// <param name="commonBorderResolver">Common border resolver</param>
    /// <param name="nodeProcessor">Callback interface for node processing</param>
    /// <param name="applyFormattingAsMsWord">
    /// Controls whether the reader should mimic MS Word when formatting imported tables.
    /// </param>
    /// <param name="htmlBlockReader">
    /// Creates <see cref="HtmlBlock"/> from 'div', 'body' and 'blockquote' HTML elements.
    /// </param>
    /// <param name="hyperlinkResolver">
    /// Adds bookmarks for imported HTML elements with 'id' attributes.
    /// </param>
    HtmlTableReader(
        DocumentBuilder builder,
        CssStyleTracker cssStyleTracker,
        ITableFormatter tableFormatter,
        HtmlCommonBorderResolver commonBorderResolver,
        IHtmlNodeProcessor nodeProcessor,
        boolean applyFormattingAsMsWord,
        HtmlBlockReader htmlBlockReader,
        HtmlHyperlinkResolver hyperlinkResolver)
    {
        Debug.assertTrue(builder != null);
        Debug.assertTrue(cssStyleTracker != null);
        Debug.assertTrue(tableFormatter != null);
        Debug.assertTrue(commonBorderResolver != null);
        Debug.assertTrue(nodeProcessor != null);
        Debug.assertTrue(htmlBlockReader != null);

        mBuilder = builder;
        mCssStyleTracker = cssStyleTracker;
        mTableFormatter = tableFormatter;
        mCommonBorderResolver = commonBorderResolver;
        mNodeProcessor = nodeProcessor;
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
        mHtmlBlockReader = htmlBlockReader;
        mHyperlinkResolver = hyperlinkResolver;
    }

    /// <summary>
    /// Imports an HTML table into the document.
    /// </summary>
    /// <param name="htmlTable">HTML table.</param>
    /// <param name="isTopmostTable">
    /// Indicates whether the table is topmost (is not nested in another table) in this HTML reading session.
    /// </param>
    /// <param name="tableNode">HTML table node.</param>
    Table processTable(
        HtmlTable htmlTable,
        boolean isTopmostTable,
        HtmlElementNode tableNode) throws Exception
    {
        Debug.assertTrue(htmlTable != null);

        mHtmlTable = htmlTable;
        if ((mHtmlTable.getRowCount() == 0) || (mHtmlTable.getColumnCount() == 0))
            return null;

        Table table = mBuilder.startTable();

        if (mHyperlinkResolver != null)
        {
            mHyperlinkResolver.addPendingBookmarkForTableIfNeeded(tableNode, table);
        }

        // In HtmlReader nested tables are handled first so collect tables here in HtmlTableReader.
        mCommonBorderResolver.collectTable(table, mCssStyleTracker.getElementDeclarations());
        mIsRtlTable = mCssStyleTracker.isBlockRtl();

        // WORDSNET-954 InsertHtml sometimes does not stop marking paragraphs as list members.
        // Table always starts with a new paragraph in MS Word, reset paragraph and list formatting.
        mBuilder.getParagraphFormat().clearFormatting();

        // Top captions.
        int topCaptionCount = 0;
        for (int i = 0; i < mHtmlTable.getCaptionCount(); i++)
        {
            HtmlTableCaption tableCaption = mHtmlTable.getCaption(i);
            if (tableCaption.isTopCaption())
            {
                topCaptionCount++;
                processCaption(tableCaption);
            }
        }

        // WORDSNET-21609 Creates inside horizontal and vertical borders from a table CSS declaration.
        CssDeclarationCollection declarations = mCssStyleTracker.getElementDeclarations();
        mInsideHorizontalCssBorder = CssBorder.createInsideBorder(declarations, BorderType.HORIZONTAL);
        mInsideVerticalCssBorder = CssBorder.createInsideBorder(declarations, BorderType.VERTICAL);
        // Table rows.
        mCurrentRowGroup = null;
        mCurrentHtmlRowCanBeTableHeader = true;
        for (int rowIdx = 0; rowIdx < mHtmlTable.getRowCount(); rowIdx++)
        {
            processRow(rowIdx);
            // WORDSNET-21609 Applies inside horizontal and vertical borders to row TablePr attributes.
            Row curRow = mBuilder.getCurrentParagraph().getParentRow();
            if (curRow != null)
            {
                setInsideBorders(curRow);
            }
        }
        if (mCurrentRowGroup != null)
        {
            Debug.assertTrue(mCssStyleTracker.getCurrentElement() == mCurrentRowGroup.getNode());
            mCssStyleTracker.popElement();
            mCurrentRowGroup = null;
        }

        // Bottom captions.
        int bottomCaptionCount = 0;
        for (int i = 0; i < mHtmlTable.getCaptionCount(); i++)
        {
            HtmlTableCaption tableCaption = mHtmlTable.getCaption(i);
            if (!tableCaption.isTopCaption())
            {
                bottomCaptionCount++;
                processCaption(tableCaption);
            }
        }
        // Set table attributes after all rows have been processed because they will update all rows.
        // Word turns this option on when importing.
        table.setAllowAutoFit(true);

        mTableFormatter.toTable(
            table,
            topCaptionCount,
            bottomCaptionCount,
            mHtmlTable.getFirstRowFirstCellLeftBorder(),
            mHtmlTable.getFirstRowLastCellRightBorder());
        applyTableBorders(table, topCaptionCount, bottomCaptionCount);
        applyIndents(table);

        mHtmlBlockReader.updateTable(table);

        // WORDSNET-14898 Table borders were applied to table and possibly to some cells.
        // Now we should remove table borders from cells, if applying them only to table is enough.
        if (mHtmlTable.isBorderCollapsed())
        {
            removeCellBordersDuplicatingTableBorders(topCaptionCount == 0, bottomCaptionCount == 0);
        }

        mBuilder.endTable();

        // WORDSNET-996 Invalid table width when inserting it into a table cell.
        // WORDSNET-19685 Update widths for the nested table if it was inserted into another table using InsertHtml.
        // We should update layout for topmost tables only to reduce performance impact. UpdateLayout method updates
        // the current table and all nested tables.
        if (!table.isNested() || isTopmostTable)
            table.updateLayout();

        return table;
    }

    private void setInsideBorders(Row row)
    {
        Debug.assertTrue(row != null);
        TablePr rowTablePr = row.getTablePr();
        if ((mInsideHorizontalCssBorder != null) &&
            (mInsideHorizontalCssBorder.isVisible() || mInsideHorizontalCssBorder.isAwBorder()))
        {
            rowTablePr.setAttr(TableAttr.BORDER_HORIZONTAL, new Border());
            mInsideHorizontalCssBorder.toModelBorder(rowTablePr.getBorderHorizontal());
        }
        if ((mInsideVerticalCssBorder != null) &&
            (mInsideVerticalCssBorder.isVisible() || mInsideVerticalCssBorder.isAwBorder()))
        {
            rowTablePr.setAttr(TableAttr.BORDER_VERTICAL, new Border());
            mInsideVerticalCssBorder.toModelBorder(rowTablePr.getBorderVertical());
        }
    }

    private void processRow(int rowIndex) throws Exception
    {
        mCurrentHtmlRow = mHtmlTable.get(rowIndex);

        // We push the row group only when it is changed to ensure each row group is pushed only once. Repeated pushing
        // of elements is blocked because it affects CSS counter values.
        if (mCurrentHtmlRow.getRowGroup() != mCurrentRowGroup)
        {
            if (mCurrentRowGroup != null)
            {
                mCssStyleTracker.popElement();
            }
            mCurrentRowGroup = mCurrentHtmlRow.getRowGroup();
            Debug.assertTrue(mCurrentRowGroup != null);
            mCssStyleTracker.pushElement(mCurrentRowGroup.getNode(), true);
        }

        mCssStyleTracker.pushElement(mCurrentHtmlRow.getNode(), true);

        if (mCssStyleTracker.getElementDisplayState() == HtmlElementDisplayState.VISIBLE)
        {
            // Note that the row formatting will be cleared when the first cell of the row is created.
            for (int colIdx = 0; colIdx < mCurrentHtmlRow.getCellCount(); colIdx++)
                processCell(mCurrentHtmlRow.get(colIdx), rowIndex, colIdx);

            // Set row properties. Some of them in HTML are table properties,
            // but in Aspose.Words table properties are stored per row at the moment.
            Row row = mBuilder.getCurrentParagraph().getParentRow();
            cssStylesToRow(row);
            if (!MathUtil.isMinValue(mCurrentHtmlRow.getMaxCellHeightInRow()) && (mCurrentHtmlRow.getMaxCellHeightInRow() > row.getRowFormat().getHeight()))
            {
                row.getRowFormat().setHeight(mCurrentHtmlRow.getMaxCellHeightInRow());
                row.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
            }

            // WORDSNET-14812 Read row's HeightRule.Exactly value.
            String heightRuleValue = mCssStyleTracker.getElementDeclarations().getIdentifier(HtmlConstants.HEIGHT_RULE);
            if (HtmlConstants.HEIGHT_RULE_EXACTLY.equals(heightRuleValue))
                row.getRowFormat().setHeightRule(HeightRule.EXACTLY);

            // WORDSNET-22050 Read `AllowBreakAcrossPages` row property value.
            String allowBreakAcrossPagesValue = mCssStyleTracker.getElementDeclarations().getIdentifier("page-break-inside");
            if (msString.equals(allowBreakAcrossPagesValue, "avoid", StringComparison.ORDINAL_IGNORE_CASE))
                row.getRowFormat().setAllowBreakAcrossPages(false);

            // WORDSNET-10504 Enable "Repeat as the header row at the top of each page" option when importing HTML thead rows.
            if (mCurrentHtmlRowCanBeTableHeader)
            {
                if (mCurrentHtmlRow.getRowGroup().isThead())
                    row.getRowFormat().setHeadingFormat(true);
                else
                    mCurrentHtmlRowCanBeTableHeader = false;
            }

            if (mHyperlinkResolver != null)
            {
                mHyperlinkResolver.endInlineBookmarkIfNeeded(mCurrentHtmlRow.getNode());
            }
            mBuilder.endRow();
        }

        mCssStyleTracker.popElement();
        mCurrentHtmlRow = null;
    }

    /// <summary>
    /// Processes HTML cell.
    /// </summary>
    /// <param name="htmlCell">HTML cell for processing.</param>
    /// <param name="rowIndex">Table row index of the processed cell. Should be <c>-1</c> for caption rows.</param>
    /// <param name="colIndex">Table column index of the processed cell. Should be <c>-1</c> for caption rows.</param>
    private void processCell(HtmlCell htmlCell, int rowIndex, int colIndex) throws Exception
    {
        mCurrentHtmlCell = htmlCell;

        if (htmlCell.getNode() != null)
            mCssStyleTracker.pushElement(htmlCell.getNode(), true);

        // WORDSNET-19889 There is a limit on the number of table columns that MS Word supports. If the number of
        // columns in the source table exceeds that limit, all content from extra columns is imported into the last column.
        Cell modelCell;
        if (colIndex < Table.MAX_COLUMNS)
        {
            modelCell = mBuilder.insertCell();

            // WORDSNET-28462 If the cell that we've just inserted starts a new table row, we need to clear its formatting.
            // Otherwise, it will inherit default table row formatting specified in the document builder, which is not what
            // we want. Instead, we want to load the formatting completely from HTML.
            if (modelCell.isFirstCell())
            {
                modelCell.getParentRow().getTablePr().clear();
            }

            htmlCell.setModelCell(modelCell);

            // WORDSNET-12113 Incorrect image size after importing html.
            // AW downsizes the image to fit the cell (this behavior was implemented in WORDSNET-10174),
            // because the table has AllowAutoFit=false style at the moment when the image is being inserted.
            // But it is not allowed to set AllowAutoFit=true immediately after creating the table because
            // it does not contain any rows.
            if (!modelCell.getParentTable().getAllowAutoFit())
                modelCell.getParentTable().setAllowAutoFit(true);

            // Need to clear formatting first because it might be changed by a previous cell.
            mBuilder.getCellFormat().clearAllFormatting();

            // WORDSNET-16021 By default, MS Word sets HideMark to true for all cells.
            // Note that this flag may be cleared later, after content of the cell is fully loaded.
            modelCell.getCellPr().setHideMark(true);

            // Html table cell has padding equal 1px by default, but element that has been
            // turned into table cells via 'display:table-cell' has 0px padding.
            // In case a table is defined using the 'display: table' or 'display:inline-table'
            // property the padding is not set and should be set to 0px.
            if (mHtmlTable.isDisplayTable())
            {
                mBuilder.getCellFormat().setLeftPadding(0.0);
                mBuilder.getCellFormat().setRightPadding(0.0);
            }

            mBuilder.getCellFormat().setHorizontalMerge(htmlCell.getHorizontalMerge());
            mBuilder.getCellFormat().setVerticalMerge(htmlCell.getVerticalMerge());
            mBuilder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);

            if (htmlCell.getNode() != null)
            {
                boolean isCellEmpty = !htmlCell.getNode().containsText();
                cssStylesToCell(
                    modelCell,
                    rowIndex,
                    htmlCell.getColSpan(),
                    htmlCell.getUnrestrictedColSpan(),
                    htmlCell.getRowSpan(),
                    isCellEmpty);
            }

            // Some attributes should be also taken from the first nodes in corresponding chains.
            HtmlCell mergeFirstHtmlCell = htmlCell.getHorizontalMergeFirstCell();
            if (mergeFirstHtmlCell == null)
                mergeFirstHtmlCell = htmlCell.getVerticalMergeFirstCell();
            if (mergeFirstHtmlCell != null)
            {
                Cell mergeFirstCell = mergeFirstHtmlCell.getModelCell();
                Debug.assertTrue(mergeFirstCell != null);

                if (mergeFirstHtmlCell.getVerticalMerge() != CellMerge.NONE)
                {
                    // WORDSNET-21609 CopyBorders method should take into account bottom and top border
                    // properties declared in a declaration block for the TD element selector declared in a <style>
                    // element, if the block exists. This is from MS Word behavior. If there is a declaration block
                    // for the TD element selector that contains border properties then these properties are applied
                    // to top and bottom borders of merged cells during import from HTML, or otherwise if there is
                    // no the TD selector with top and border border properties then the inner borders of vertical
                    // merged cells are not set.
                    // To get border properties from TD element selector the next block of code adds a TD element to
                    // the style tracker stack and obtains the default element declaration for the processed table
                    // that contains border properties from the TD element selector declaration block if they exist
                    // there.
                    mCssStyleTracker.pushElement(new HtmlElementNode("td"), false);
                    Border defaultTopBorder = new Border();
                    CssBorder.createBorder(mCssStyleTracker.getElementDeclarations(), BorderType.TOP, true)
                        .toModelBorder(defaultTopBorder);
                    Border defaultBottomBorder = new Border();
                    CssBorder.createBorder(mCssStyleTracker.getElementDeclarations(), BorderType.BOTTOM, true)
                        .toModelBorder(defaultBottomBorder);
                    mCssStyleTracker.popElement();

                    copyBorders(
                        mergeFirstCell,
                        modelCell,
                        mergeFirstHtmlCell.getRowSpan(),
                        defaultTopBorder,
                        defaultBottomBorder,
                        mInsideHorizontalCssBorder.isVisible());
                }
                else
                {
                    copyBorders(mergeFirstCell, modelCell);
                }
            }
        }
        else
        {
            modelCell = mCurrentHtmlRow.get(Table.MAX_COLUMNS - 1).getModelCell();
        }

        // Start of bookmark for an empty row.
        if ((colIndex == 0) && (mHyperlinkResolver != null))
        {
            mHyperlinkResolver.startInlineBookmarkIfNeeded(mCurrentHtmlRow.getNode());
        }

        if (htmlCell.getNode() != null)
        {
            if (mHyperlinkResolver != null)
            {
                mHyperlinkResolver.startInlineBookmarkIfNeeded(htmlCell.getNode());
            }
            // Delegate to the node processor.
            mNodeProcessor.processCell(htmlCell.getNode());
            if (mHyperlinkResolver != null)
            {
                mHyperlinkResolver.endInlineBookmarkIfNeeded(htmlCell.getNode());
            }
            mCssStyleTracker.popElement();
        }
        else if (colIndex < Table.MAX_COLUMNS)
        {
            // WORDSNET-9232 Clear paragraph formatting for empty cells.
            // WORDSNET-19889 It shouldn't clear the formatting when empty cells from ignored columns are
            // processed.
            mBuilder.getParagraphFormat().clearFormatting();
        }

        // WORDSNET-11248 and 11618 - Bookmark position is changed after conversion from Doc to Html.
        // In DocumentBuilder paragraph containing only bookmarks is considered empty.
        // In DocumentBuilder when current paragraph is empty new table is inserted before this paragraph.
        // So when bookmarks are located just before a table in HTML document they appear after the table in the model.
        // Here we use MS Word approach and import such bookmarks at the begining of the paragraph of the first table's cell.
        if ((rowIndex == 0) && (colIndex == 0) && (modelCell.getParentTable().getNextSibling() instanceof Paragraph))
        {
            Paragraph nextPara = (Paragraph)modelCell.getParentTable().getNextSibling();
            while (nextPara.getLastChild() != null)
            {
                if ((nextPara.getLastChild().getNodeType() != NodeType.BOOKMARK_START) &&
                    (nextPara.getLastChild().getNodeType() != NodeType.BOOKMARK_END))
                {
                    break;
                }

                String bookmarkName;
                switch (nextPara.getLastChild().getNodeType())
                {
                    case NodeType.BOOKMARK_START:
                    {
                        BookmarkStart bookmarkStart = (BookmarkStart)nextPara.getLastChild();
                        bookmarkName = bookmarkStart.getName();
                        break;
                    }
                    case NodeType.BOOKMARK_END:
                    {
                        BookmarkEnd bookmarkEnd = (BookmarkEnd)nextPara.getLastChild();
                        bookmarkName = bookmarkEnd.getName();
                        break;
                    }
                    default:
                    {
                        bookmarkName = "";
                        break;
                    }
                }

                if ((mHyperlinkResolver != null) && !mHyperlinkResolver.containsStartedAnchorBookmark(bookmarkName))
                {
                    break;
                }

                Node bookmarkNode = nextPara.getLastChild();
                bookmarkNode.remove();
                modelCell.getFirstParagraph().insert(bookmarkNode, null, true);
            }
        }
    }

    private void processCaption(HtmlTableCaption tableCaption) throws Exception
    {
        HtmlCell firstCell = new HtmlCell(tableCaption.getNode(), null);
        if (mHtmlTable.getColumnCount() > 1)
        {
            firstCell.setHorizontalMerge(CellMerge.FIRST);
        }
        processCell(firstCell, -1, -1);

        // First cell has just been processed.
        for (int i = 1; i < mHtmlTable.getColumnCount(); i++)
        {
            HtmlCell mergedCell = new HtmlCell(null);
            mergedCell.setHorizontalMerge(CellMerge.PREVIOUS);
            mergedCell.setHorizontalMergeFirstCell(firstCell);
            processCell(mergedCell, -1, -1);
        }

        mBuilder.endRow();
    }

    private static void copyBorders(Cell source, Cell destination)
    {
        for (int borderKey : CellPr.POSSIBLE_BORDER_KEYS.getValues())
        {
            destination.getCellPr().remove(borderKey);
            Border sourceBorder = ms.as(source.getCellPr().get(borderKey), Border.class);
            if ((sourceBorder != null) && (!sourceBorder.isInherited()))
            {
                destination.getCellPr().setAttr(borderKey, sourceBorder.deepClone());
            }
        }
    }

    private static void copyBorders(
        Cell source,
        Cell destination,
        int rowSpan,
        Border defaultTopBorder,
        Border defaultBottomBorder,
        boolean setNilForLast)
    {
        boolean isLast = (destination.getRowIndex() - source.getRowIndex() + 1) == rowSpan;
        for (/*BorderType*/int borderType : (Iterable</*BorderType*/Integer>) CellPr.POSSIBLE_BORDER_KEYS.getKeys())
        {
            int borderKey = CellPr.POSSIBLE_BORDER_KEYS.get(borderType);
            destination.getCellPr().remove(borderKey);

            Border sourceBorder = ms.as(source.getCellPr().get(borderKey), Border.class);

            // WORDSNET-28238 - 'borders: none' is not always processed correctly in table cells,
            // so we should skip empty borders here.
            if (sourceBorder == null)
                continue;

            if (!sourceBorder.isInherited() &&
                sourceBorder.isVisible() &&
                (borderType != BorderType.TOP) &&
                ((borderType != BorderType.BOTTOM) || isLast))
            {
                destination.getCellPr().setAttr(borderKey, sourceBorder.deepClone());
            }
            else if ((borderType == BorderType.TOP) || (borderType == BorderType.BOTTOM))
            {
                Border defaultBorder = (borderType == BorderType.TOP)
                    ? defaultTopBorder
                    : defaultBottomBorder;
                if (defaultBorder.isVisible() && !defaultBorder.isNil())
                {
                    Border newBorder = new Border(
                        defaultBorder.getLineStyle(),
                        defaultBorder.getRawLineWidth(),
                        defaultBorder.getColorInternal());
                    destination.getCellPr().setAttr(borderKey, newBorder);
                }
            }
        }
        // The bottom border of the top vertical merged cell should be replaced by a nil border if there are inner
        // horizontal borders in the table, or otherwise it should be an empty border.
        if (isLast)
        {
            int borderKey = CellPr.POSSIBLE_BORDER_KEYS.get(BorderType.BOTTOM);
            Border oldBorder = ms.as(source.getCellPr().get(borderKey), Border.class);
            if (oldBorder != null)
            {
                Border newBorder = setNilForLast
                    ? Border.createNilBorder()
                    : new Border();
                source.getCellPr().setAttr(borderKey, newBorder);
            }
        }
    }

    /// <summary>
    /// Applies CSS styles to a model row.
    /// </summary>
    private void cssStylesToRow(Row row) throws Exception
    {
        Debug.assertTrue(row != null);
        for (CssDeclaration declaration : mCssStyleTracker.getElementDeclarations())
            ((CssComputedDeclaration)declaration).toRow(row);
    }

    /// <summary>
    /// Applies CSS styles to a model cell.
    /// </summary>
    private void cssStylesToCell(
        Cell cell,
        int rowIndex,
        int colSpan,
        int unrestrictedColSpan,
        int rowSpan,
        boolean isCellEmpty) throws Exception
    {
        Debug.assertTrue(cell != null);

        CssBoxBorders appliedBorders = mHtmlTable.isBorderCollapsed()
            ? applyCollapsedBordersToCell(cell, cell.getColumnIndex(), rowIndex, colSpan, rowSpan)
            : applySeparateBordersToCell(cell.getCellFormat().getBorders());

        for (CssDeclaration declaration : mCssStyleTracker.getElementDeclarations())
        {
            ((CssComputedDeclaration)declaration).toCellFormat(cell.getCellFormat());
        }

        applyWidthStyleToCell(cell, unrestrictedColSpan, isCellEmpty, appliedBorders);
        applyRowAndColumnBackgroundColorToCell(cell);

        // WORDSNET-19526 CSS propery "writing-mode" doesn't support text
        // rotated 90 degrees counterclockwise (upward). We use the "transform" property to emulate this.
        applyRotateTransformation(cell);
    }

    private void applyRotateTransformation(Cell cell)
    {
        // The "writing-mode" CSS property with value 'tb-rl' always parses as TextOrientation.VerticalFarEast.
        if (cell.getCellFormat().getOrientation() == TextOrientation.VERTICAL_FAR_EAST)
        {
            CssDeclaration transformDeclaration = mCssStyleTracker.getElementDeclarations().get("transform");

            if ((transformDeclaration == null) ||
                (transformDeclaration.getValue().getFirstValue().getValueType() != CssValueType.FUNCTION))
            {
                return;
            }

            CssFunctionValue functionValue = (CssFunctionValue)transformDeclaration.getValue().getFirstValue();
            if (("rotate".equals(functionValue.getName())) &&
                (functionValue.getArguments().getCount() == 1) &&
                (functionValue.getArguments().get(0).getValueType() == CssValueType.DEGREE) &&
                MathUtil.areEqual(180d, functionValue.getArguments().get(0).getDoubleValue()))
            {
                cell.getCellFormat().setOrientation(TextOrientation.UPWARD);
            }
        }
    }

    private void applyRowAndColumnBackgroundColorToCell(Cell cell) throws Exception
    {
        // This method applies only background color that is specified indirectly: on table columns and rows.
        // Background color specified directly on table cells is applied somewhere else.
        if (mCssStyleTracker.getElementDeclarations().get("background-color") != null)
            return;

        if (mCurrentHtmlRow != null)
        {
            CssComputedDeclaration rowBackgroundDeclaration =
                (CssComputedDeclaration)mCurrentHtmlRow.getDeclarations().get("background-color");
            if (rowBackgroundDeclaration != null)
            {
                rowBackgroundDeclaration.toCellFormat(cell.getCellFormat());
                return;
            }

            if (mCurrentHtmlRow.getRowGroup() != null)
            {
                CssComputedDeclaration rowGroupBackgroundDeclaration =
                    (CssComputedDeclaration)mCurrentHtmlRow.getRowGroup().getDeclarations().get("background-color");
                if (rowGroupBackgroundDeclaration != null)
                {
                    rowGroupBackgroundDeclaration.toCellFormat(cell.getCellFormat());
                    return;
                }
            }
        }

        if (mHtmlTable.getColElementCount() > cell.getColumnIndex())
        {
            HtmlCol col = mHtmlTable.getCol(cell.getColumnIndex());
            CssDeclarationCollection colDeclarations = col.getDeclarations();

            CssComputedDeclaration colBackgroundDeclaration = (CssComputedDeclaration)colDeclarations.get("background-color");
            if (colBackgroundDeclaration != null)
            {
                colBackgroundDeclaration.toCellFormat(cell.getCellFormat());
                return;
            }

            if (col.getColGroup() != null)
            {
                CssComputedDeclaration groupBackgroundDeclaration =
                    (CssComputedDeclaration)col.getColGroup().getDeclarations().get("background-color");
                if (groupBackgroundDeclaration != null)
                    groupBackgroundDeclaration.toCellFormat(cell.getCellFormat());
            }
        }
    }

    private void applyWidthStyleToCell(Cell cell, int colSpan, boolean isCellEmpty, CssBoxBorders appliedBorders) throws Exception
    {
        CssDeclarationCollection colDeclarations = CssDeclarationCollection.EMPTY;
        CssDeclarationCollection[] spannedColDeclarations = null;
        if (cell.getColumnIndex() < mHtmlTable.getColElementCount())
        {
            HtmlCol col = mHtmlTable.getCol(cell.getColumnIndex());
            colDeclarations = col.getDeclarations();

            // If the cell spans more than one column, get declarations of additional COL elements.
            // Note that we create the corresponding array only when needed, because it's a rare case where a table
            // contains both COL elements and cells that span multiple columns.
            if (colSpan >= 2)
            {
                int lastColumnIndex = Math.min(mHtmlTable.getColElementCount(), cell.getColumnIndex() + colSpan) - 1;
                spannedColDeclarations = new CssDeclarationCollection[lastColumnIndex - cell.getColumnIndex()];
                for (int colIndex = cell.getColumnIndex() + 1; colIndex <= lastColumnIndex; colIndex++)
                {
                    HtmlCol spannedCol = mHtmlTable.getCol(colIndex);
                    spannedColDeclarations[colIndex - cell.getColumnIndex() - 1] = spannedCol.getDeclarations();
                }
            }
        }

        CssWidthStyleConverter.toCellFormat(
            cell.getCellFormat(),
            mCssStyleTracker.getElementDeclarations(),
            colDeclarations,
            spannedColDeclarations,
            appliedBorders,
            !mApplyFormattingAsMsWord,
            mHtmlTable.isBorderCollapsed(),
            isCellEmpty);

        if (cell.isFirstCell() && cell.getParentRow().isFirstRow())
            mHtmlTable.setFirstRowFirstCellLeftBorder(appliedBorders.getLeft());

        if (cell.isLastCell() && cell.getParentRow().isFirstRow())
            mHtmlTable.setFirstRowLastCellRightBorder(appliedBorders.getRight());
    }

    /// <summary>
    /// Applies CSS separate border styles to model cell borders.
    /// </summary>
    private CssBoxBorders applySeparateBordersToCell(BorderCollection cellBorders) throws Exception
    {
        CssBoxBorders cssBorders = (mCurrentHtmlCell.getCssBorders() != null)
            ? mCurrentHtmlCell.getCssBorders()
            : CssBoxBorders.createBorders(mCssStyleTracker.getElementDeclarations(), true);

        if (cssBorders.getTop().isVisible() || cssBorders.getTop().isAwBorder())
            cssBorders.getTop().toModelBorder(cellBorders.getTop());
        if (cssBorders.getBottom().isVisible() || cssBorders.getBottom().isAwBorder())
            cssBorders.getBottom().toModelBorder(cellBorders.getBottom());

        // When importing Rtl direction table into the AW model the left and the right
        // cell borders are switched.
        if (cssBorders.getLeft().isVisible() || cssBorders.getLeft().isAwBorder())
        {
            cssBorders.getLeft().toModelBorder(mIsRtlTable ? cellBorders.getRight() : cellBorders.getLeft());
        }

        if (cssBorders.getRight().isVisible() || cssBorders.getRight().isAwBorder())
        {
            cssBorders.getRight().toModelBorder(mIsRtlTable ? cellBorders.getLeft() : cellBorders.getRight());
        }

        return cssBorders;
    }

    /// <summary>
    /// Applies CSS collapsed borders to a model cell.
    /// </summary>
    private CssBoxBorders applyCollapsedBordersToCell(Cell cell, int cellIndex, int rowIndex, int colSpan, int rowSpan) throws Exception
    {
        CssBoxBorders cellBorders = (mCurrentHtmlCell.getCssBorders() != null)
            ? mCurrentHtmlCell.getCssBorders()
            : CssBoxBorders.createBorders(mCssStyleTracker.getElementDeclarations(), true);

        // WORDSNET-10395 Cells with negative row indexes are caption cells
        // and they should not participate in the border conflict resolution.
        if (rowIndex < 0)
        {
            cellBorders.toModelBorders(cell.getCellFormat().getBorders());
        }
        else
        {
            /*BorderType*/int[] borderTypes = new /*BorderType*/int[]
            {
                BorderType.TOP,
                BorderType.RIGHT,
                BorderType.BOTTOM,
                BorderType.LEFT
            };
            CssBorder[] appliedBorders = new CssBorder[4];
            int appliedBorderIndex = 0;
            for (/*BorderType*/int borderType : borderTypes)
            {
                CssBorder winnerBorder = getWinnerBorder(borderType, cellIndex, rowIndex, colSpan, rowSpan);

                // When importing Rtl direction table into the AW model the left and the right
                // cell borders are switched.
                /*BorderType*/int rtlAwareBorderType = borderType;
                if (mIsRtlTable)
                {
                    if (borderType == BorderType.LEFT)
                    {
                        rtlAwareBorderType = BorderType.RIGHT;
                    }
                    else if (borderType == BorderType.RIGHT)
                    {
                        rtlAwareBorderType = BorderType.LEFT;
                    }
                }

                Border modelBorder = cell.getCellFormat().getBorders().getByBorderType(rtlAwareBorderType);

                if (winnerBorder.isVisible() || winnerBorder.isAwBorder())
                {
                    winnerBorder.toModelBorder(modelBorder);
                    appliedBorders[appliedBorderIndex] = winnerBorder;
                }
                else
                {
                    // WORDSNET-21609 Adds nil-borders instead of empty ones for inner table borders.
                    if (((((rtlAwareBorderType == BorderType.LEFT) || (rtlAwareBorderType == BorderType.RIGHT)) && mInsideVerticalCssBorder.isVisible()) ||
                         (((rtlAwareBorderType == BorderType.TOP) || (rtlAwareBorderType == BorderType.BOTTOM)) && mInsideHorizontalCssBorder.isVisible())) &&
                            ((cellIndex != 0) || (rtlAwareBorderType != BorderType.LEFT)) &&
                            ((cellIndex != (mCurrentHtmlRow.getCellCount() - 1)) || (rtlAwareBorderType != BorderType.RIGHT)) &&
                            ((rowIndex != 0) || (rtlAwareBorderType != BorderType.TOP)) &&
                            ((rowIndex != (mHtmlTable.getRowCount() - 1)) || (rtlAwareBorderType != BorderType.BOTTOM)))
                    {

                        winnerBorder.toModelBorder(modelBorder);
                        modelBorder.isNil(true);
                    }
                }

                ++appliedBorderIndex;
            }
            cellBorders = new CssBoxBorders(
                appliedBorders[0],
                appliedBorders[1],
                appliedBorders[2],
                appliedBorders[3]);
        }
        return cellBorders;
    }

    /// <summary>
    /// Returns cell border specified directly on cell.
    /// </summary>
    /// <param name="borderType">The type of the border to retrieve.</param>
    private CssBorder getCellBorder(/*BorderType*/int borderType)
    {
        if ((mCurrentHtmlCell == null) || (mCurrentHtmlCell.getCssBorders() == null))
            return CssBorder.getEmpty();

        return mCurrentHtmlCell.getCssBorders().getByBorderType(borderType);
    }

    /// <summary>
    /// Returns cell border inherited from a row element.
    /// </summary>
    /// <param name="borderType">The type of the border to retrieve.</param>
    /// <param name="cellIndex">Cell index in the row.</param>
    /// <param name="colSpan">The number of cols the cell is to span.</param>
    private CssBorder getRowBorder(/*BorderType*/int borderType, int cellIndex, int colSpan)
    {
        if ((mCurrentHtmlRow == null) ||
            ((borderType == BorderType.LEFT) && (cellIndex != 0)) ||
            ((borderType == BorderType.RIGHT) && ((cellIndex + colSpan) != mCurrentHtmlRow.getCellCount())))
        {
            return CssBorder.getEmpty();
        }

        return mCurrentHtmlRow.getCssBorders().getByBorderType(borderType);
    }

    /// <summary>
    /// Returns cell border inherited from a row group element.
    /// </summary>
    /// <param name="borderType">The type of the border to retrieve.</param>
    /// <param name="cellIndex">Cell index in the row.</param>
    /// <param name="rowIndex">The current row index.</param>
    /// <param name="colSpan">The number of cols the cell is to span.</param>
    private CssBorder getRowGroupBorder(/*BorderType*/int borderType, int cellIndex, int rowIndex, int colSpan)
    {
        if ((mHtmlTable == null) || (mCurrentHtmlRow == null) || (mCurrentHtmlRow.getRowGroup() == null))
            return CssBorder.getEmpty();

        boolean isEdgeCell;
        switch (borderType)
        {
            case BorderType.TOP:
                isEdgeCell = (rowIndex == 0) ||
                    (mHtmlTable.get(rowIndex - 1).getRowGroup() != mCurrentHtmlRow.getRowGroup());
                break;
            case BorderType.RIGHT:
                isEdgeCell = (cellIndex + colSpan) == mCurrentHtmlRow.getCellCount();
                break;
            case BorderType.BOTTOM:
                isEdgeCell = (rowIndex == (mHtmlTable.getRowCount() - 1)) ||
                    (mHtmlTable.get(rowIndex + 1).getRowGroup() != mCurrentHtmlRow.getRowGroup());
                break;
            case BorderType.LEFT:
                isEdgeCell = cellIndex == 0;
                break;
            default:
                throw new IllegalStateException("Unknown border type");
        }

        return isEdgeCell
            ? mCurrentHtmlRow.getRowGroup().getCssBorders().getByBorderType(borderType)
            : CssBorder.getEmpty();
    }

    /// <summary>
    /// Returns cell border inherited from a col element.
    /// </summary>
    /// <param name="borderType">The type of the border to retrieve.</param>
    /// <param name="cellIndex">Cell index in the row.</param>
    /// <param name="rowIndex">The current row index.</param>
    private CssBorder getColBorder(/*BorderType*/int borderType, int cellIndex, int rowIndex)
    {
        if ((mHtmlTable == null) ||
            (cellIndex >= mHtmlTable.getColElementCount()) ||
            ((borderType == BorderType.TOP) && (rowIndex != 0)) ||
            (borderType == BorderType.BOTTOM) && (rowIndex != (mHtmlTable.getRowCount() - 1)))
        {
            return CssBorder.getEmpty();
        }

        return mHtmlTable.getCol(cellIndex).getCssBorders().getByBorderType(borderType);
    }

    /// <summary>
    /// Returns cell border inherited from a colgroup element.
    /// </summary>
    /// <param name="borderType">The type of the border to retrieve.</param>
    /// <param name="cellIndex">Cell index in the row.</param>
    /// <param name="rowIndex">The current row index.</param>
    private CssBorder getColGroupBorder(/*BorderType*/int borderType, int cellIndex, int rowIndex)
    {
        if ((mHtmlTable == null) || (mHtmlTable.getColElementCount() <= cellIndex))
        {
            return CssBorder.getEmpty();
        }

        switch (borderType)
        {
            case BorderType.TOP:
                return (rowIndex == 0)
                    ? mHtmlTable.getCol(cellIndex).getColGroup().getCssBorders().getTop()
                    : CssBorder.getEmpty();
            case BorderType.RIGHT:
                return getColGroupRightBorder(cellIndex);
            case BorderType.BOTTOM:
                return (rowIndex == mHtmlTable.getRowCount() - 1)
                    ? mHtmlTable.getCol(cellIndex).getColGroup().getCssBorders().getBottom()
                    : CssBorder.getEmpty();
            case BorderType.LEFT:
                return getColGroupLeftBorder(cellIndex);
            default:
                throw new IllegalStateException("Unknown border type");
        }
    }

    private CssBorder getColGroupRightBorder(int cellIndex)
    {
        HtmlColGroup colGroup = mHtmlTable.getCol(cellIndex).getColGroup();
        int nextCellIndex = cellIndex + 1;
        HtmlColGroup nextColGroup = ((nextCellIndex < mCurrentHtmlRow.getCellCount()) && (nextCellIndex < mHtmlTable.getColElementCount()))
            ? mHtmlTable.getCol(nextCellIndex).getColGroup()
            : null;
        return (nextColGroup != colGroup)
            ? colGroup.getCssBorders().getRight()
            : CssBorder.getEmpty();
    }

    private CssBorder getColGroupLeftBorder(int cellIndex)
    {
        HtmlColGroup colGroup = mHtmlTable.getCol(cellIndex).getColGroup();
        HtmlColGroup prevColGroup = (cellIndex > 0)
            ? mHtmlTable.getCol(cellIndex - 1).getColGroup()
            : null;
        return (prevColGroup != colGroup)
            ? colGroup.getCssBorders().getLeft()
            : CssBorder.getEmpty();
    }

    /// <summary>
    /// Returns cell border inherited from table element.
    /// </summary>
    /// <param name="borderType">The type of the border to retrieve.</param>
    /// <param name="cellIndex">Cell index in the row.</param>
    /// <param name="rowIndex">The current row index.</param>
    /// <param name="colSpan">The number of cols the cell is to span.</param>
    /// <param name="rowSpan">The number of rows the cell is to span.</param>
    private CssBorder getTableBorder(/*BorderType*/int borderType, int cellIndex, int rowIndex, int colSpan, int rowSpan)
    {
        if (mHtmlTable == null)
            return CssBorder.getEmpty();

        boolean isEdgeCell;
        switch (borderType)
        {
            case BorderType.TOP:
                isEdgeCell = rowIndex == 0;
                break;
            case BorderType.RIGHT:
                isEdgeCell = (cellIndex + colSpan) == mCurrentHtmlRow.getCellCount();
                break;
            case BorderType.BOTTOM:
                isEdgeCell = (rowIndex + rowSpan) == mHtmlTable.getRowCount();
                break;
            case BorderType.LEFT:
                isEdgeCell = cellIndex == 0;
                break;
            default:
                throw new IllegalStateException("Unknown border type");
        }

        return isEdgeCell
            ? mHtmlTable.getCssBorders().getByBorderType(borderType)
            : CssBorder.getEmpty();
    }

    /// <summary>
    /// Applies CSS separate border styles to model table.
    /// </summary>
    /// <param name="table"></param>
    /// <param name="topCaptionCount">Number of caption rows at the top of the table.</param>
    /// <param name="bottomCaptionCount">Number of caption rows at the bottom of the table.</param>
    /// <remarks>Table captions are imported as table rows. Top captions become leading table rows and bottom captions
    /// become trailing rows. To distinguish 'normal' table rows from caption rows we need to know how many top and bottom
    /// caption rows the table contains.</remarks>
    private void applyTableBorders(Table table, int topCaptionCount, int bottomCaptionCount) throws Exception
    {
        // Table borders are applied at row level, but only to 'normal' table rows, not to captions.
        int firstTableRowIndex = topCaptionCount;
        int lastTableRowIndex = table.getRows().getCount() - bottomCaptionCount - 1;
        for (int i = firstTableRowIndex; i <= lastTableRowIndex; ++i)
        {
            BorderCollection rowBorders = table.getRows().get(i).getRowFormat().getBorders();
            CssBoxBorders htmlTableBorders = mHtmlTable.getCssBorders();
            if (htmlTableBorders.getTop().isVisible() || htmlTableBorders.getTop().isAwBorder())
                htmlTableBorders.getTop().toModelBorder(rowBorders.getTop());
            if (htmlTableBorders.getRight().isVisible() || htmlTableBorders.getRight().isAwBorder())
                htmlTableBorders.getRight().toModelBorder(rowBorders.getRight());
            if (htmlTableBorders.getBottom().isVisible() || htmlTableBorders.getBottom().isAwBorder())
                htmlTableBorders.getBottom().toModelBorder(rowBorders.getBottom());
            if (htmlTableBorders.getLeft().isVisible() || htmlTableBorders.getLeft().isAwBorder())
                htmlTableBorders.getLeft().toModelBorder(rowBorders.getLeft());
        }
    }

    /// <summary>
    /// Removes borders edge cell borders in case they are equal to table border. Needed to remove duplication in case
    /// table borders are collapsed.
    /// </summary>
    private void removeCellBordersDuplicatingTableBorders(boolean removeTopBorder, boolean removeBottomBorder)
    {
        if (removeTopBorder)
        {
            removeHorizontalBorderDuplicatingTableBorder(BorderType.TOP, CellAttr.BORDER_TOP, 0);
        }
        if (removeBottomBorder)
        {
            removeHorizontalBorderDuplicatingTableBorder(BorderType.BOTTOM, CellAttr.BORDER_BOTTOM, mHtmlTable.getRowCount() - 1);
        }
        for (int rowIndex = 0; rowIndex < mHtmlTable.getRowCount(); rowIndex++)
        {
            removeVerticalBorderDuplicatingTableBorder(BorderType.LEFT, CellAttr.BORDER_LEFT, rowIndex, 0);
            removeVerticalBorderDuplicatingTableBorder(BorderType.RIGHT, CellAttr.BORDER_RIGHT, rowIndex,
                mHtmlTable.get(rowIndex).getModelCellCount() - 1);
        }
    }

    private void removeVerticalBorderDuplicatingTableBorder(
        /*BorderType*/int borderType,
        int borderCellAttr,
        int rowIndex,
        int cellIndex)
    {
        mCurrentHtmlRow = mHtmlTable.get(rowIndex);
        mCurrentHtmlCell = mCurrentHtmlRow.get(cellIndex);

        CssBorder winnerBorder = getWinnerBorder(
            borderType,
            cellIndex,
            rowIndex,
            mCurrentHtmlCell.getColSpan(),
            mCurrentHtmlCell.getRowSpan());

        if (winnerBorder == mHtmlTable.getCssBorders().getByBorderType(borderType))
        {
            Cell modelCell = mCurrentHtmlCell.getModelCell();
            modelCell.getCellPr().remove(borderCellAttr);

            // In case the current cell is merged with other cells, we should remove the border from them as well.
            int mergedCellIndex;
            // Find the first merged cell.
            for (mergedCellIndex = cellIndex; mergedCellIndex >= 0; mergedCellIndex--)
            {
                modelCell = mCurrentHtmlRow.get(mergedCellIndex).getModelCell();
                if (modelCell.getCellPr().getHorizontalMerge() != CellMerge.PREVIOUS)
                {
                    break;
                }
            }
            // Remove the border from all cells that are merged with the first one.
            for (; mergedCellIndex < mCurrentHtmlRow.getModelCellCount(); mergedCellIndex++)
            {
                modelCell = mCurrentHtmlRow.get(mergedCellIndex).getModelCell();
                if (modelCell.getCellPr().getHorizontalMerge() == CellMerge.NONE)
                {
                    break;
                }
                modelCell.getCellPr().remove(borderCellAttr);
            }
        }

        mCurrentHtmlRow = null;
        mCurrentHtmlCell = null;
    }

    private void removeHorizontalBorderDuplicatingTableBorder(
        /*BorderType*/int borderType,
        int borderCellAttr,
        int rowIndex)
    {
        mCurrentHtmlRow = mHtmlTable.get(rowIndex);
        for (int cellIndex = 0; cellIndex < mCurrentHtmlRow.getModelCellCount(); cellIndex++)
        {
            mCurrentHtmlCell = mCurrentHtmlRow.get(cellIndex);

            CssBorder winnerBorder = getWinnerBorder(
                borderType,
                cellIndex,
                rowIndex,
                mCurrentHtmlCell.getColSpan(),
                mCurrentHtmlCell.getRowSpan());

            if (winnerBorder == mHtmlTable.getCssBorders().getByBorderType(borderType))
            {
                Cell modelCell = mCurrentHtmlCell.getModelCell();
                modelCell.getCellPr().remove(borderCellAttr);

                // In case the current cell is merged with other cells, we should remove the border from them as well.
                int mergedRowIndex;
                // Find the first merged cell.
                for (mergedRowIndex = rowIndex; mergedRowIndex >= 0; mergedRowIndex--)
                {
                    modelCell = mHtmlTable.get(mergedRowIndex).get(cellIndex).getModelCell();
                    if (modelCell.getCellPr().getVerticalMerge() != CellMerge.PREVIOUS)
                    {
                        break;
                    }
                }
                // Remove the border from all cells that are merged with the first one.
                for (; mergedRowIndex < mHtmlTable.getRowCount(); mergedRowIndex++)
                {
                    modelCell = mHtmlTable.get(mergedRowIndex).get(cellIndex).getModelCell();
                    if (modelCell.getCellPr().getVerticalMerge() == CellMerge.NONE)
                    {
                        break;
                    }
                    modelCell.getCellPr().remove(borderCellAttr);
                }
            }
        }
    }

    /// <summary>
    /// In the collapsing border model returns winner border according to Border Conflict Resolution specification
    /// (http://www.w3.org/TR/CSS21/tables.html#border-conflict-resolution).
    /// </summary>
    /// <param name="borderType">The type of the border to retrieve.</param>
    /// <param name="cellIndex">Cell index in the row.</param>
    /// <param name="rowIndex">The current row index.</param>
    /// <param name="colSpan">The number of cols the cell is to span.</param>
    /// <param name="rowSpan">The number of rows the cell is to span.</param>
    private CssBorder getWinnerBorder(/*BorderType*/int borderType, int cellIndex, int rowIndex, int colSpan, int rowSpan)
    {
        // WORDSNET-28188 Border with 'nil' style should not participate in border conflict resolution.
        CssBorder cellBorder = getCellBorder(borderType);
        if (cellBorder.isAwBorder())
            return cellBorder;

        // Rule 4: If border styles differ only in color, then a style set on a cell wins over one on a row, which wins
        // over a row group, column, column group and, lastly, table.
        // Note #1: Elements in this array are ordered as defined in rule 4 of Border Conflict Resolution specification.
        // Note #2: The specification says: When two elements of the same type conflict, then the one further to the left
        // (if the table's 'direction' is 'ltr'; right, if it is 'rtl') and further to the top wins. We ignore this rule
        // in our algorithm because MS Word uses a very similar behavior. We can implement this rule later.
        CssBorder[] elementsBorders = new CssBorder[]
        {
            getRowBorder(borderType, cellIndex, colSpan),
            getRowGroupBorder(borderType, cellIndex, rowIndex, colSpan),
            getColBorder(borderType, cellIndex, rowIndex),
            getColGroupBorder(borderType, cellIndex, rowIndex),
            getTableBorder(borderType, cellIndex, rowIndex, colSpan, rowSpan)
        };
        CssBorder winnerBorder = cellBorder;
        for (int i = 0; i < elementsBorders.length; i++)
        {
            if (winnerBorder.wins(elementsBorders[i]) < 0)
                winnerBorder = elementsBorders[i];
        }

        return winnerBorder;
    }

    /// <summary>
    /// Applies indents (distances from text) from the CSS box model to a table.
    /// </summary>
    /// <remarks>
    /// The <see cref="Table.Bidi"/> property must be set before calling this method.
    /// </remarks>
    private void applyIndents(Table table) throws Exception
    {
        // Margins of floating tables are translated into tables' distances from text.
        if (table.isFloating())
        {
            if (table.getFirstRow() != null)
            {
                TablePr tablePr = table.getFirstRow().getTablePr();
                // Our CSS box model can return negative horizontal margins, but distances from text
                // are not allowed to be negative.
                int left = Math.max(ConvertUtilCore.pointToTwip(mCssStyleTracker.getBoxModel().getLeft().getValue()), 0);
                tablePr.setAttr(TableAttr.FRAME_DISTANCE_FROM_LEFT, left);

                int right = Math.max(ConvertUtilCore.pointToTwip(mCssStyleTracker.getBoxModel().getRight().getValue()), 0);
                tablePr.setAttr(TableAttr.FRAME_DISTANCE_FROM_RIGHT, right);

                // Our CSS box model guarantee that vertical margins are always non-negative.
                int top = ConvertUtilCore.pointToTwip(mCssStyleTracker.getBoxModel().getTop().getValue());
                Debug.assertTrue(top >= 0);
                tablePr.setAttr(TableAttr.FRAME_DISTANCE_FROM_TOP, top);

                int bottom = ConvertUtilCore.pointToTwip(mCssStyleTracker.getBoxModel().getBottom().getValue());
                Debug.assertTrue(bottom >= 0);
                tablePr.setAttr(TableAttr.FRAME_DISTANCE_FROM_BOTTOM, bottom);
            }
        }
        else
        {
            double leftIndent;
            // Now, we use HtmlBlock nodes during import altChunk elements to store borders and margins of block-level HTML elements,
            // we don't need the box model and we apply only the current element margins directly to the current table indent.
            if (mApplyFormattingAsMsWord)
            {
                leftIndent = table.getBidi()
                    ? getMargin(mCssStyleTracker.getElementDeclarations(), "margin-right")
                    : getMargin(mCssStyleTracker.getElementDeclarations(), "margin-left");
            }
            else
            {
                leftIndent = table.getBidi()
                    ? mCssStyleTracker.getBoxModel().getRight().getValue()
                    : mCssStyleTracker.getBoxModel().getLeft().getValue();
            }

            // WORDSNET-20364 Adjust table left margin in documents intended for older versions of MS Word.
            // Older MS Word versions used a table aligment algorithm that is different from the one used in HTML, and
            // we need to adjust table margin in order to compensate this difference.
            // Please note that this adjustment is not performed by MS Word when it loads HTML documents.
            // WORDSNET-7392 for an inner HTML table MS Word converts its 'margin-left' CSS property value
            // directly to LeftIndent value, so no additional correction is needed for LeftIndent of inner tables.
            if ((mBuilder.getDocument().getCompatibilityOptions().getMswVersion() < MsWordVersionCore.WORD_2013) &&
                !table.isNested() &&
                (table.getFirstRow() != null) &&
                !mApplyFormattingAsMsWord)
            {
                CellPr firstCellPr = table.getFirstRow().getFirstCell().getCellPr();
                leftIndent += ConvertUtilCore.twipToPoint(firstCellPr.getLeftPadding());
                if ((firstCellPr.getBorderLeft() != null) && (firstCellPr.getBorderLeft().getLineStyle() != LineStyle.NONE))
                {
                    leftIndent += firstCellPr.getBorderLeft().getLineWidth() / 2.0;
                }
            }

            if (!MathUtil.areEqual(table.getLeftIndent(), leftIndent))
            {
                table.setLeftIndent(leftIndent);
            }
        }
    }

    private static double getMargin(
        CssDeclarationCollection declarations,
        String marginPropertyName)
    {
        CssValue cssValue = getSingleValue(declarations, marginPropertyName);
        if (cssValue == null)
            return 0.0;

        return CssUtil.lengthToPoint(cssValue);
    }

    protected static CssValue getSingleValue(CssDeclarationCollection declarations, String propertyName)
    {
        CssDeclaration declaration = declarations.get(propertyName);
        if (declaration == null)
        {
            return null;
        }

        Debug.assertTrue(declaration.getValue().getCount() == 1);
        return declaration.getValue().getFirstValue();
    }

    private /*final*/ DocumentBuilder mBuilder;
    private /*final*/ CssStyleTracker mCssStyleTracker;
    private /*final*/ ITableFormatter mTableFormatter;
    private /*final*/ HtmlCommonBorderResolver mCommonBorderResolver;
    private /*final*/ IHtmlNodeProcessor mNodeProcessor;
    private HtmlTable mHtmlTable;

    /// <summary>
    /// An HTML row group that is currently being processed.
    /// </summary>
    private HtmlRowGroup mCurrentRowGroup;

    /// <summary>
    /// HTML table row that is currently being processed.
    /// </summary>
    private HtmlRow mCurrentHtmlRow;

    /// <summary>
    /// HTML table cell that is currently being processed.
    /// </summary>
    private HtmlCell mCurrentHtmlCell;

    /// <summary>
    /// Indicate whether the currently processed row can be repeated as the header row at the top of each page.
    /// </summary>
    private boolean mCurrentHtmlRowCanBeTableHeader;

    private boolean mIsRtlTable;

    /// <summary>
    /// Instructs the table reader to stick to MS Word's behavior when applying certain formatting.
    /// </summary>
    private /*final*/ boolean mApplyFormattingAsMsWord;

    private CssBorder mInsideHorizontalCssBorder;
    private CssBorder mInsideVerticalCssBorder;

    private /*final*/ HtmlBlockReader mHtmlBlockReader;
    private /*final*/ HtmlHyperlinkResolver mHyperlinkResolver;
}

