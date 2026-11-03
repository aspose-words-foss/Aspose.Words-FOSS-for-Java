// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2005 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Collections;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Calculates and updates table cell widths accordingly to the preferred widths.
/// 
/// This is DV's implementation of table layout that was done for old PDF and HTML export,
/// before the layout engine. At the moment it has no relation to the layout engine.
/// It should be deprecated some day, because it cannot calculate table widths exactly 
/// like MS Word in some cases and this is by design. E.g. to calculate exactly like MS Word 
/// we must perform proper table layout in the layout engine.
/// </summary>
class TableLayouter
{
    /// <summary>
    /// Ctor.
    /// </summary> 
    TableLayouter(Table table)
    {
        if (table.getParentNode() == null)
            throw new IllegalStateException("The table must be in the document tree before it can be updated.");

        mTable = table;
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="table"></param>
    /// <param name="suppressNestedTablesUpdates">Controls whether to suppress layout update of nested tables at the end of layout process.</param>
    private TableLayouter(Table table, boolean suppressNestedTablesUpdates)
    {
    	this(table);
	
        mSuppressNestedTablesUpdates = suppressNestedTablesUpdates;
    }

    /// <summary>
    /// Launches the layout process.
    /// </summary>
    LayoutWidth doLayout()
    {
        // We should not update cell widths if the table AutoFit is disabled.
        // But we must do this, because it collects nested tables and nested tables might have AutoFit enabled and they need to be resized.
        calculateLayoutWidths();

        // Do not resize (and do not have to perform further calculations) for tables that have AutoFit off.
        if (mTable.getAllowAutoFit())
        {
            calculateTableContainerWidth();
            calculateTableSpecifiedWidth();
            calculateResultingWidths();

            setCellWidths();
        }

        // WORDSNET-6423 The problem occurs because there was a lot of useless layout updates for each nested table.
        // To fix this issue we suppress the useless layout updates for the nested tables.
        // There is one top-level cascade layout update for the nested tables now.
        if (!mSuppressNestedTablesUpdates)
        {
            // We must do this because nested tables might have AutoFit enabled and need to be resized.
            processNestedTables();
        }

        return mTableLayoutWidth;
    }

    /// <summary>
    /// Calculates widths required for further processing.
    /// </summary>
    private void calculateLayoutWidths()
    {
        if (!mAreLayoutWidthsDirty)
            return;

        initializeLayout();
        calculateColumnLayoutWidths();
        calculateEffectiveWidth();
        // Dmatv: per WORDSNET-4794 I made an experiment to update this legacy table layouter logic to handle cell spacing.
        // Adding cell spacing (halfSpacing * 2) to EffectiveLayoutWidth.Min and .Max for all columns appears to work in most cases.
        // However, the logic was disabled in order to preserve the original behavior
        // for tables not handled by the new grid construction logic.
        // The reason is that those tables are often not handled correctly by this legacy table layouter either,
        // and replacing one incorrect layout (without spacing) with a different incorrect layout (with spacing)
        // can be perceived as breakage.
        // The correct way to handle spacing is to support a table via the new table grid calculation logic.
        calculateTableLayoutWidth();

        mAreLayoutWidthsDirty = false;
    }

    /// <summary>
    /// Initializes the layout object.
    /// </summary>
    private void initializeLayout()
    {
        mParagraphMeasurer = new ParagraphMeasurer();

        mSpanCells = new ArrayList<SpanCell>();
        mNestedTables = new ArrayList<Table>();

        mFixedContributor = null;
        mMaxContributor = null;

        mHasPercent = false;

        mTableLayoutWidth = new LayoutWidth();

        mAutoCount = 0;
        mFixedCount = 0;
        mPercentCount = 0;

        // Create an array of columns.
        mColumns = new Column[mTable.getColumnCount()];
        // Initialize the array.
        for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
            mColumns[columnIndex] = new Column();
    }

    /// <summary>
    /// Calculates layout and preferred widths for each column.
    /// </summary>
    private void calculateColumnLayoutWidths()
    {
        for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
        {
            Column column = mColumns[columnIndex];

            for (int rowIndex = 0; rowIndex < mTable.getRows().getCount(); rowIndex++)
            {
                Cell cell = getCellUsingGridSpans(mTable, columnIndex, rowIndex);

                if (cell == null)
                    continue;

                CellFormat cellFormat = cell.getCellFormat();

                // Don't waste time.
                if (cellFormat.getVerticalMerge() == CellMerge.PREVIOUS)
                    continue;

                switch (cellFormat.getHorizontalMerge())
                {
                    case CellMerge.NONE:
                    case CellMerge.FIRST:
                        column.ensureColumnHasLayoutWidth();

                        if (Extensions.getHorizontalMergeSpan(cell) > 1)
                            contributeMergedCell(cell, columnIndex);
                        else
                            contributeNonMergedCell(cell, column);

                        break;
                    case CellMerge.PREVIOUS:
                        // Skip these cells.
                        continue;
                    default:
                        throw new IllegalStateException("Unknown cell merge.");
                }
            }

            if (column.getPreferredWidth().isFixed() && (column.getLayoutWidth().getMax() > column.getPreferredWidth().getValue()) && (mFixedContributor != mMaxContributor))
            {
                column.setPreferredWidth(PreferredWidth.AUTO);
                mFixedContributor = null;
            }

            column.getLayoutWidth().setMax(Math.max(column.getLayoutWidth().getMin(), column.getLayoutWidth().getMax()));
        }
    }

    private void contributeMergedCell(Cell cell, int columnIndex)
    {
        msArrayList.add(mSpanCells, new SpanCell(cell, columnIndex));
    }

    private void contributeNonMergedCell(Cell cell, Column column)
    {
        LayoutWidth cellLayoutWidth = calculateCellLayoutWidth(cell);

        if (cellLayoutWidth.getMax() > column.getLayoutWidth().getMax())
            mMaxContributor = cell;

        column.getLayoutWidth().update(cellLayoutWidth);

        PreferredWidth cellPreferredWidth = cell.getCellFormat().getPreferredWidth();
        switch (cellPreferredWidth.getType())
        {
            case PreferredWidthType.POINTS:
            {
                if ((cellPreferredWidth.isPositive()) && !column.getPreferredWidth().isPercent())
                {
                    double totalCellWidth = cellPreferredWidth.getValue() + cell.getCellFormat().getLeftPadding() + cell.getCellFormat().getRightPadding();

                    if (column.getPreferredWidth().isFixed())
                    {
                        if ((totalCellWidth > column.getPreferredWidth().getValue()) || ((totalCellWidth > column.getPreferredWidth().getValue()) && (cell == mMaxContributor)))
                        {
                            column.setPreferredWidth(PreferredWidth.fromPointsSafe(totalCellWidth));
                            mFixedContributor = cell;
                        }
                    }
                    else
                    {
                        column.setPreferredWidth(PreferredWidth.fromPointsSafe(totalCellWidth));
                        mFixedContributor = cell;
                    }
                }

                break;
            }
            case PreferredWidthType.PERCENT:
            {
                mHasPercent = true;

                if ((cellPreferredWidth.isPositive()) &&
                    (!column.getPreferredWidth().isPercent() || (cellPreferredWidth.getValue() > column.getPreferredWidth().getValue())))
                {
                    column.setPreferredWidth(PreferredWidth.fromPercentSafe(cellPreferredWidth.getValue()));
                }

                break;
            }
            default:
                break;
        }
    }

    private void calculateEffectiveWidth()
    {
        prepareToCalculateEffectiveWidth();

        for (int cellIndex = 0; cellIndex < mSpanCells.size(); cellIndex++)
        {
            SpanContext context = new SpanContext();

            SpanCell spanCell = mSpanCells.get(cellIndex);
            Cell cell = spanCell.getCell();

            context.setSpan(spanCell.getSpan());
            context.setSpanIndex(spanCell.getColumnIndex());
            context.setLastColumnIndex(context.getSpanIndex());

            context.setCellPreferredWidth(cell.getCellFormat().getPreferredWidth());

            if (!context.getCellPreferredWidth().isPositive())
                context.setCellPreferredWidth(PreferredWidth.AUTO);

            context.setCellLayoutWidth(calculateCellLayoutWidth(cell));

            // These 2 should be disabled when spacing is handled by the Extensions method correctly:
            context.getCellLayoutWidth().setMin(context.getCellLayoutWidth().getMin() + mTable.getCellSpacing());
            context.getCellLayoutWidth().setMax(context.getCellLayoutWidth().getMax() + mTable.getCellSpacing());

            context.setSpanLayoutWidth(new LayoutWidth());

            collectSpanInformation(context);
            adjustTableMaxWidth(context);
            satisfySpanCellLayoutWidth(context);
            adjustColumnMaxWidth(context);
        }
    }

    private void prepareToCalculateEffectiveWidth()
    {
        mSpanMaxWidth = 0.0;

        Collections.sort(mSpanCells, new SpanComparer());

        for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
            mColumns[columnIndex].initializeEffectiveWidth();
    }

    private void collectSpanInformation(SpanContext context)
    {
        while ((context.getLastColumnIndex() < mColumns.length) && (context.getSpan() > 0))
        {
            Column lastColumn = mColumns[context.getLastColumnIndex()];

            switch (lastColumn.getPreferredWidth().getType())
            {
                case PreferredWidthType.AUTO:
                {
                    context.hasAuto(true);
                    context.setAllColumnsAreFixed(false);
                    context.setAllColumnsArePercent(false);

                    lastColumn.setEffectivePreferredWidth(PreferredWidth.AUTO);
                    break;
                }
                case PreferredWidthType.POINTS:
                {
                    if (lastColumn.getPreferredWidth().isPositive())
                    {
                        context.setTotalFixed(context.getTotalFixed() + lastColumn.getPreferredWidth().getValue());
                        context.setAllColumnsArePercent(false);
                    }
                    else
                    {
                        context.hasAuto(true);
                        context.setAllColumnsAreFixed(false);
                        context.setAllColumnsArePercent(false);

                        lastColumn.setEffectivePreferredWidth(PreferredWidth.AUTO);
                    }
                    break;
                }
                case PreferredWidthType.PERCENT:
                {
                    context.setTotalPercent(context.getTotalPercent() + lastColumn.getPreferredWidth().getValue());
                    context.setAllColumnsAreFixed(false);

                    break;
                }
                default:
                    throw new IllegalStateException("Unknown length type.");
            }

            context.getSpanLayoutWidth().setMin(context.getSpanLayoutWidth().getMin() + lastColumn.getEffectiveLayoutWidth().getMin());
            context.getSpanLayoutWidth().setMax(context.getSpanLayoutWidth().getMax() + lastColumn.getEffectiveLayoutWidth().getMax());

            // These 2 should be disabled if Extensions class method returning the correct spacing are used :
            context.getCellLayoutWidth().setMin(context.getCellLayoutWidth().getMin() - mTable.getCellSpacing());
            context.getCellLayoutWidth().setMax(context.getCellLayoutWidth().getMax() - mTable.getCellSpacing());

            context.setSpan(context.getSpan() - 1)/*Property--*/;

            context.setLastColumnIndex(context.getLastColumnIndex() + 1)/*Property++*/;
        }
    }

    private void adjustTableMaxWidth(SpanContext context)
    {
        if (context.getCellPreferredWidth().isPercent())
        {
            if ((context.getTotalPercent() > context.getCellPreferredWidth().getValue()) || (context.getAllColumnsArePercent()))
            {
                // Can't satisfy this condition, treat as auto.
                context.setCellPreferredWidth(PreferredWidth.AUTO);
            }
            else
            {
                double spanMaxWidth = Math.max(context.getSpanLayoutWidth().getMax(), context.getCellLayoutWidth().getMax());
                mSpanMaxWidth = Math.max(mSpanMaxWidth, spanMaxWidth * 100.0 / context.getCellPreferredWidth().getValue());

                // All non percent columns in the span get percent values to sum up correctly.

                double percentMissing = context.getCellPreferredWidth().getValue() - context.getTotalPercent();
                double totalWidth = 0.0;

                for (int columnIndex = context.getSpanIndex(); columnIndex < context.getLastColumnIndex(); columnIndex++)
                {
                    Column column = mColumns[columnIndex];

                    if (!column.getPreferredWidth().isPercent())
                        totalWidth += column.getEffectiveLayoutWidth().getMax();
                }

                for (int columnIndex = context.getSpanIndex(); (columnIndex < context.getLastColumnIndex()) && (totalWidth > 0); columnIndex++)
                {
                    Column column = mColumns[columnIndex];

                    if (!column.getPreferredWidth().isPercent())
                    {
                        int percent = (int)(percentMissing * column.getEffectiveLayoutWidth().getMax() / totalWidth);

                        totalWidth -= column.getEffectiveLayoutWidth().getMax();
                        percentMissing -= percent;

                        if (percent > 0)
                            column.setEffectivePreferredWidth(PreferredWidth.fromPercentSafe(percent));
                        else
                            column.setEffectivePreferredWidth(PreferredWidth.AUTO);
                    }
                }
            }
        }
    }

    private void satisfySpanCellLayoutWidth(SpanContext context)
    {
        if (context.getCellLayoutWidth().getMin() > context.getSpanLayoutWidth().getMin())
        {
            if (context.getAllColumnsAreFixed())
            {
                for (int columnIndex = context.getSpanIndex(); (columnIndex < context.getLastColumnIndex()) && (context.getTotalFixed() > 0); columnIndex++)
                {
                    Column column = mColumns[columnIndex];

                    double newWidth = Math.max(
                        column.getEffectiveLayoutWidth().getMin(),
                        context.getCellLayoutWidth().getMin() * column.getPreferredWidth().getValue() / context.getTotalFixed());

                    context.setTotalFixed(context.getTotalFixed() - column.getPreferredWidth().getValue());
                    context.getCellLayoutWidth().setMin(context.getCellLayoutWidth().getMin() - newWidth);

                    column.getEffectiveLayoutWidth().setMin(newWidth);
                }
            }
            else
            {
                double minWidth = context.getSpanLayoutWidth().getMin();
                double maxWidth = context.getSpanLayoutWidth().getMax();

                for (
                    int columnIndex = context.getSpanIndex();
                    (columnIndex < context.getLastColumnIndex()) && (maxWidth > 0);
                    columnIndex++)
                {
                    Column column = mColumns[columnIndex];

                    if (column.getPreferredWidth().isFixed() && context.hasAuto() && (context.getTotalFixed() <= context.getCellLayoutWidth().getMin()))
                    {
                        double newWidth = Math.max(column.getEffectiveLayoutWidth().getMin(), column.getPreferredWidth().getValue());

                        context.setTotalFixed(context.getTotalFixed() - column.getPreferredWidth().getValue());
                        minWidth -= column.getEffectiveLayoutWidth().getMin();
                        maxWidth -= column.getEffectiveLayoutWidth().getMax();
                        context.getCellLayoutWidth().setMin(context.getCellLayoutWidth().getMin() - newWidth);

                        column.getEffectiveLayoutWidth().setMin(newWidth);
                    }
                }

                for (
                    int columnIndex = context.getSpanIndex();
                    (columnIndex < context.getLastColumnIndex()) && (minWidth < context.getCellLayoutWidth().getMin()) && (maxWidth > 0);
                    columnIndex++)
                {
                    Column column = mColumns[columnIndex];

                    if (!(column.getPreferredWidth().isFixed() && context.hasAuto() && (context.getTotalFixed() <= context.getCellLayoutWidth().getMin())))
                    {
                        double newWidth = Math.max(
                            column.getEffectiveLayoutWidth().getMin(),
                            context.getCellLayoutWidth().getMin() * column.getEffectiveLayoutWidth().getMax() / maxWidth);

                        newWidth = Math.min(newWidth, column.getEffectiveLayoutWidth().getMin() + (context.getCellLayoutWidth().getMin() - minWidth));

                        minWidth -= column.getEffectiveLayoutWidth().getMin();
                        maxWidth -= column.getEffectiveLayoutWidth().getMax();
                        context.getCellLayoutWidth().setMin(context.getCellLayoutWidth().getMin() - newWidth);

                        column.getEffectiveLayoutWidth().setMin(newWidth);
                    }
                }
            }
        }
    }

    private void adjustColumnMaxWidth(SpanContext context)
    {
        if (!context.getCellPreferredWidth().isPercent())
        {
            if (context.getCellLayoutWidth().getMax() > context.getSpanLayoutWidth().getMax())
            {
                for (int columnIndex = context.getSpanIndex(); (columnIndex < context.getLastColumnIndex()) && (context.getSpanLayoutWidth().getMax() > 0); columnIndex++)
                {
                    Column column = mColumns[columnIndex];

                    double newWidth = Math.max(
                        column.getEffectiveLayoutWidth().getMax(),
                        context.getCellLayoutWidth().getMax() * column.getEffectiveLayoutWidth().getMax() / context.getSpanLayoutWidth().getMax());

                    context.getSpanLayoutWidth().setMax(context.getSpanLayoutWidth().getMax() - column.getEffectiveLayoutWidth().getMax());
                    context.getCellLayoutWidth().setMax(context.getCellLayoutWidth().getMax() - newWidth);

                    column.getEffectiveLayoutWidth().setMax(newWidth);
                }
            }
        }
        else
        {
            for (int columnIndex = context.getSpanIndex(); columnIndex < context.getLastColumnIndex(); columnIndex++)
            {
                Column column = mColumns[columnIndex];

                column.getLayoutWidth().setMax(Math.max(column.getLayoutWidth().getMin(), column.getLayoutWidth().getMax()));
            }
        }
    }

    /// <summary>
    /// Calculates table layout width.
    /// </summary>
    private void calculateTableLayoutWidth()
    {
        double maxPercent = 0.0;
        double maxNonPercent = 0.0;

        double remainingPercent = 100.0;

        for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
        {
            Column column = mColumns[columnIndex];

            mTableLayoutWidth.setMin(mTableLayoutWidth.getMin() + column.getEffectiveLayoutWidth().getMin());
            mTableLayoutWidth.setMax(mTableLayoutWidth.getMax() + column.getEffectiveLayoutWidth().getMax());

            if (column.getEffectivePreferredWidth().isPercent())
            {
                double percent = Math.min(column.getEffectivePreferredWidth().getValue(), remainingPercent);
                double pw = column.getEffectiveLayoutWidth().getMax() * 100.0 / Math.max(percent, 1.0);

                remainingPercent -= percent;
                maxPercent = Math.max(maxPercent, pw);
            }
            else
            {
                maxNonPercent += column.getEffectiveLayoutWidth().getMax();
            }
        }

        if (shouldScaleColumns())
        {
            maxNonPercent = (maxNonPercent * 100.0 + 50.0) / Math.max(remainingPercent, 1.0);
            mTableLayoutWidth.setMax(Math.max(mTableLayoutWidth.getMax(), maxNonPercent));
            mTableLayoutWidth.setMax(Math.max(mTableLayoutWidth.getMax(), maxPercent));
        }

        mTableLayoutWidth.setMax(Math.max(mTableLayoutWidth.getMax(), mSpanMaxWidth));

        double bs = Extensions.getTotalBorderAndSpacingWidth(mTable);
        mTableLayoutWidth.setMin(mTableLayoutWidth.getMin() + bs);
        mTableLayoutWidth.setMax(mTableLayoutWidth.getMax() + bs);

        if (mTable.getPreferredWidth().isFixed() && mTable.getPreferredWidth().isPositive())
        {
            mTableLayoutWidth.setMin(Math.max(mTableLayoutWidth.getMin(), mTable.getPreferredWidth().getValue()));
            mTableLayoutWidth.setMax(mTableLayoutWidth.getMin());
        }

        // Limit table width to the value allowed by Word.
        mTableLayoutWidth.setMin(Math.min(mTableLayoutWidth.getMin(), MAX_TABLE_WIDTH));
        mTableLayoutWidth.setMax(Math.min(mTableLayoutWidth.getMax(), MAX_TABLE_WIDTH));
    }

    private boolean shouldScaleColumns()
    {
        boolean result = true;

        Table table = mTable;

        while (table != null)
        {
            PreferredWidth tableWidth = table.getPreferredWidth();

            if (!tableWidth.isFixed())
            {
                Node container = mTable.getParentNode();

                while ((container != null) && (container.getNodeType() != NodeType.CELL))
                    container = container.getParentNode();

                table = null;

                if ((container != null) && (container.getNodeType() == NodeType.CELL))
                {
                    Cell cell = (Cell)container;
                    PreferredWidth cellWidth = cell.getCellFormat().getPreferredWidth();

                    if (!cellWidth.isFixed())
                    {
                        if (tableWidth.isPercent())
                        {
                            result = false;
                        }
                        else
                        {
                            if ((Extensions.getHorizontalMergeSpan(cell) > 1) || cell.getParentTable().getPreferredWidth().isAuto())
                                result = false;
                            else
                                table = cell.getParentTable();
                        }
                    }
                }
            }
            else
            {
                table = null;
            }
        }

        return result;
    }

    static double getContainerWidth(CompositeNode tableContainer)
    {
        double containerWidth;

        Section section = (Section)tableContainer.getAncestor(NodeType.SECTION);

        switch (tableContainer.getNodeType())
        {
            case NodeType.BODY:
            {
                containerWidth = getMinimalColumnWidth(section);
                break;
            }
            case NodeType.CELL:
            {
                Cell cell = (Cell)tableContainer;
                containerWidth = cell.getCellFormat().getWidth();
                break;
            }
            case NodeType.HEADER_FOOTER:
            case NodeType.COMMENT:
            {
                containerWidth = section.getPageSetup().getContentWidth();
                break;
            }
            case NodeType.FOOTNOTE:
            {
                Footnote note = (Footnote)tableContainer;
                boolean useLastSectionWidth = (note.getFootnoteType() == FootnoteType.ENDNOTE) &&
                    (section.getPageSetup().getEndnoteOptions().getPosition() == EndnotePosition.END_OF_DOCUMENT);
                if (useLastSectionWidth)
                    section = section.fetchDocument().getLastSection();

                containerWidth = section.getPageSetup().getContentWidth();
                break;
            }
            case NodeType.SHAPE:
            {
                Shape shape = (Shape)tableContainer;
                ShapeAdaptor shapeAdaptor = new ShapeAdaptor(shape);
                CompositeNode parentStory = null;
                if (shapeAdaptor.getWrapMode() == TextBoxWrapMode.NONE)
                {
                    // Word wrap OFF, the text box may grow to accommodate the contents.
                    // Use the container story width. This should be section body or header/footer.
                    // The other containers (footnotes, comments, text boxes) must not have text boxes inside.
                    parentStory = getBodyOrHeaderAncestor(shape);
                    // It should not be null, but it is when fall-back shapes are validated (TestLayoutTableGridFixed2013.TestTblwPctInTextBoxNested).
                    // The code below will use the text box width instead.
                }

                if (parentStory != null)
                {
                    containerWidth = getContainerWidth(parentStory);
                }
                else
                {
                    // Word wrap ON, the text box does not grow to accommodate the contents.
                    // Use the text box width.
                    containerWidth = shapeAdaptor.getContentWidth();
                }

                break;
            }
            case NodeType.SYSTEM:
                if (tableContainer instanceof FootnoteSeparator)
                {
                    // WORDSNET-13165 tables can be present in separators.

                    // Actually, the same separator may be used in different sections.
                    // But it seems MS Word always uses the first section width,
                    // even if there are no footnotes in the first section (TestJira13165TableInSeparator).
                    section = tableContainer.fetchDocument().getFirstSection();
                    containerWidth = section.getPageSetup().getContentWidth();
                    // The value is currently not used by the layout anyway as separators are not traversed by the validator,
                    // so the fixed grid is never built and because of that fall-back grid is always used by the layout.
                }
                else
                {
                    throw new IllegalStateException("Unexpected table container.");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected table container.");
        }

        return containerWidth;
    }

    private static CompositeNode getBodyOrHeaderAncestor(Node node)
    {
        CompositeNode ancestor = node.getParentNode();
        while ((ancestor != null) && (ancestor.getNodeType() != NodeType.BODY) && (ancestor.getNodeType() != NodeType.HEADER_FOOTER))
        {
            ancestor = ancestor.getParentNode();
        }

        return ancestor;
    }

    /// <summary>
    /// Gets minimal column width for the given section, in points.
    /// </summary>
    private static double getMinimalColumnWidth(Section section)
    {
        PageSetup pageSetup = section.getPageSetup();

        Document doc = section.fetchDocumentOrGlossaryMain();
        boolean hasSideGutter = PageMarginCalculator.getPageHasSideGutter(section.getSectPr(), doc.getDocPr(), doc.getFirstSection().getSectPr().getOrientation());

        // Width available for columns on the page.
        int availableWidthTwips = section.getSectPr().getContentWidthTwips(hasSideGutter);
        double minimalColumnWidth = ConvertUtilCore.twipToPoint(availableWidthTwips);

        TextColumnCollection columns = pageSetup.getTextColumns();
        // WORDSNET-22176 Even if there is only one column,
        // it may still have a width specified which is different from the available width between margins and gutter.
        if (columns == null || columns.getCount() < 1)
            return minimalColumnWidth;

        if (section.getSectPr().getColumnsEvenlySpaced())
        {
            // Width available for columns on the page.
            availableWidthTwips = section.getSectPr().getTotalEvenlySpacedColumnSizeTwips(hasSideGutter);
            minimalColumnWidth = ConvertUtilCore.twipToPoint(availableWidthTwips / columns.getCount());
        }
        else
        {
            for (int i = 0; i < columns.getCount(); ++i)
            {
                double currentColumnWidth = columns.get(i).getWidth();
                if (currentColumnWidth < minimalColumnWidth)
                {
                    minimalColumnWidth = currentColumnWidth;
                }
            }
        }

        return minimalColumnWidth;
    }

    /// <summary>
    /// Calculates available content width of the table's container.
    /// </summary>
    private void calculateTableContainerWidth()
    {
        mTableContainerWidth = getContainerWidth(mTable.getFirstNonMarkupParentNode()) - mTable.getLeftIndent();
        // Add magical 15 twips for tables with percent tblw in shapes.
        mTableContainerWidth += ConvertUtilCore.twipToPoint(Extensions.getShapeContainerWidthAdjustmentTwips(mTable));
        // It is no longer included in GetContainerWidth() as it does not apply to tables with auto width.

        mTableContainerWidth = Math.min(mTableContainerWidth, ConvertUtilCore.twipToPoint(Extensions.MAX_TABLE_WIDTH_TWIPS));
    }

    /// <summary>
    /// Calculates the absolute width of the table in points.
    /// </summary>
    private void calculateTableSpecifiedWidth()
    {
        PreferredWidth preferredWidth = mTable.getPreferredWidth();

        switch (preferredWidth.getType())
        {
            case PreferredWidthType.AUTO:
            {
                mMarginsWidth = Math.min(mTableContainerWidth, mTableLayoutWidth.getMax());
                break;
            }
            case PreferredWidthType.POINTS:
            {
                if (preferredWidth.isPositive())
                    mMarginsWidth = preferredWidth.getValue();
                else
                    mMarginsWidth = Math.min(mTableContainerWidth, mTableLayoutWidth.getMax());

                break;
            }
            case PreferredWidthType.PERCENT:
            {
                if (preferredWidth.isPositive())
                    mMarginsWidth = mTableContainerWidth * preferredWidth.getValue() / 100.0;
                else
                    mMarginsWidth = Math.min(mTableContainerWidth, mTableLayoutWidth.getMax());

                break;
            }
            default:
            {
                throw new IllegalStateException("Unknown length type.");
            }
        }

        mTableSpecifiedWidth = Math.max(mMarginsWidth, mTableLayoutWidth.getMin());
    }

    /// <summary>
    /// The essential method which calculates the resulting column width which are then used
    /// for setting width of the cells.
    /// </summary>
    private void calculateResultingWidths()
    {
        // Compute several widths required by further calculations.

        prepareWidths();

        // Allocate the desired widths to the columns.

        allocateToPercent();
        allocateToFixed();
        allocateToAuto();

        // If we have an extra space, distribute it among the columns.

        spreadOverPercent();
        spreadOverFixed();
        spreadOverRemainder();

        // If we have over allocated, reduce every cell according to the difference between desired width
        // and min width.
        if (mAvailableWidth < 0)
        {
            reduceAuto();
            reduceFixed();
            reducePercent();
        }

        // WORDSNET-7805 Round widths of columns to whole numbers of twips. Further processing of columns is done
        // in whole twips, and so refusing to round the widths leads to misaligned columns and unstable layout due to 
        // accumulation of fractional width parts.
        roundWidthsToWholeTwips();
    }

    private void prepareWidths()
    {
        mAvailableWidth = mTableSpecifiedWidth - Extensions.getTotalBorderAndSpacingWidth(mTable);

        for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
        {
            Column column = mColumns[columnIndex];

            column.getEffectiveLayoutWidth().setMax(Math.max(column.getEffectiveLayoutWidth().getMin(), column.getEffectiveLayoutWidth().getMax()));

            // Fill up every cell with its min width.
            column.setCalculatedWidth(column.getEffectiveLayoutWidth().getMin());
            mAvailableWidth -= column.getEffectiveLayoutWidth().getMin();

            // Count widths necessary for further allocations.
            switch (column.getEffectivePreferredWidth().getType())
            {
                case PreferredWidthType.AUTO:
                    mAutoCount++;
                    mTotalAutoMin += column.getEffectiveLayoutWidth().getMin();
                    mTotalAutoMax += column.getEffectiveLayoutWidth().getMax();
                    break;
                case PreferredWidthType.POINTS:
                    mFixedCount++;
                    mTotalFixedMax += column.getEffectiveLayoutWidth().getMax();
                    break;
                case PreferredWidthType.PERCENT:
                    mPercentCount++;
                    mTotalPercent += column.getEffectivePreferredWidth().getValue();
                    break;
                default:
                    break;
            }
        }
    }

    private void allocateToPercent()
    {
        if ((mAvailableWidth) > 0 && (mPercentCount > 0))
        {
            double tableWidth = mTableSpecifiedWidth - Extensions.getTotalBorderAndSpacingWidth(mTable);

            for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
            {
                Column column = mColumns[columnIndex];
                PreferredWidth preferredWidth = column.getEffectivePreferredWidth();

                if (preferredWidth.isPercent())
                {
                    double newWidth = Math.max(column.getEffectiveLayoutWidth().getMin(), preferredWidth.getValue() * tableWidth / 100.0);
                    mAvailableWidth += column.getCalculatedWidth() - newWidth;
                    column.setCalculatedWidth(newWidth);
                }
            }
        }
    }

    private void allocateToFixed()
    {
        if (mAvailableWidth > 0)
        {
            for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
            {
                Column column = mColumns[columnIndex];
                PreferredWidth preferredWidth = column.getEffectivePreferredWidth();

                if ((preferredWidth.isFixed()) && (preferredWidth.getValue() > column.getCalculatedWidth()))
                {
                    mAvailableWidth += column.getCalculatedWidth() - preferredWidth.getValue();
                    column.setCalculatedWidth(preferredWidth.getValue());
                }
            }
        }
    }

    private void allocateToAuto()
    {
        if ((mAvailableWidth) > 0 && (mAutoCount > 0))
        {
            mAvailableWidth += mTotalAutoMin;

            for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
            {
                Column column = mColumns[columnIndex];
                PreferredWidth preferredWidth = column.getEffectivePreferredWidth();

                if ((preferredWidth.isAuto()) && (mTotalAutoMax != 0))
                {
                    double newWidth = Math.max(
                        column.getCalculatedWidth(),
                        mAvailableWidth * column.getEffectiveLayoutWidth().getMax() / mTotalAutoMax);

                    mAvailableWidth -= newWidth;
                    mTotalAutoMax -= column.getEffectiveLayoutWidth().getMax();
                    column.setCalculatedWidth(newWidth);
                }
            }
        }
    }

    private void spreadOverPercent()
    {
        if ((mAvailableWidth > 0) && (mHasPercent) && (mTotalPercent < 100))
        {
            for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
            {
                Column column = mColumns[columnIndex];
                PreferredWidth preferredWidth = column.getEffectivePreferredWidth();

                if (preferredWidth.isPercent())
                {
                    double newWidth = mAvailableWidth * preferredWidth.getValue() / mTotalPercent;

                    mAvailableWidth -= newWidth;
                    mTotalPercent -= preferredWidth.getValue();
                    column.setCalculatedWidth(column.getCalculatedWidth() + newWidth);

                    if ((mAvailableWidth == 0) || (mTotalPercent == 0))
                        break;
                }
            }
        }
    }

    private void spreadOverFixed()
    {
        if ((mAvailableWidth > 0) && (mFixedCount > 0))
        {
            for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
            {
                Column column = mColumns[columnIndex];
                PreferredWidth preferredWidth = column.getEffectivePreferredWidth();

                if (preferredWidth.isFixed())
                {
                    double newWidth = mAvailableWidth * column.getEffectiveLayoutWidth().getMax() / mTotalFixedMax;

                    mAvailableWidth -= newWidth;
                    mTotalFixedMax -= column.getEffectiveLayoutWidth().getMax();
                    column.setCalculatedWidth(column.getCalculatedWidth() + newWidth);
                }
            }
        }
    }

    private void spreadOverRemainder()
    {
        if (mAvailableWidth > 0)
        {
            int columnsTotal = mColumns.length;
            int columnIndex = columnsTotal;

            while (columnIndex-- > 0)
            {
                double newWidth = mAvailableWidth / columnsTotal;
                mAvailableWidth -= newWidth;
                columnsTotal--;

                mColumns[columnIndex].setCalculatedWidth(mColumns[columnIndex].getCalculatedWidth() + newWidth);
            }
        }
    }

    private void reduceAuto()
    {
        if (mAvailableWidth < 0)
        {
            double widthToReduce = 0.0;

            for (int columnIndex = mColumns.length - 1; columnIndex >= 0; columnIndex--)
            {
                Column column = mColumns[columnIndex];

                if (column.getEffectivePreferredWidth().isAuto())
                    widthToReduce += column.getCalculatedWidth() - column.getEffectiveLayoutWidth().getMin();
            }

            for (int columnIndex = mColumns.length - 1; columnIndex >= 0 && widthToReduce > 0; columnIndex--)
            {
                Column column = mColumns[columnIndex];

                if (column.getEffectivePreferredWidth().isAuto())
                {
                    double minMaxDiff = column.getCalculatedWidth() - column.getEffectiveLayoutWidth().getMin();
                    double reduce = mAvailableWidth * minMaxDiff / widthToReduce;

                    column.setCalculatedWidth(column.getCalculatedWidth() + reduce);
                    mAvailableWidth -= reduce;
                    widthToReduce -= minMaxDiff;

                    if (mAvailableWidth >= 0)
                        break;
                }
            }
        }
    }

    private void reduceFixed()
    {
        if (mAvailableWidth < 0)
        {
            double widthToReduce = 0.0;

            for (int columnIndex = mColumns.length - 1; columnIndex >= 0; columnIndex--)
            {
                Column column = mColumns[columnIndex];

                if (column.getEffectivePreferredWidth().isFixed())
                    widthToReduce += column.getCalculatedWidth() - column.getEffectiveLayoutWidth().getMin();
            }

            for (int columnIndex = mColumns.length - 1; columnIndex >= 0 && widthToReduce > 0; columnIndex--)
            {
                Column column = mColumns[columnIndex];

                if (column.getEffectivePreferredWidth().isFixed())
                {
                    double minMaxDiff = column.getCalculatedWidth() - column.getEffectiveLayoutWidth().getMin();
                    double reduce = mAvailableWidth * minMaxDiff / widthToReduce;

                    column.setCalculatedWidth(column.getCalculatedWidth() + reduce);
                    mAvailableWidth -= reduce;
                    widthToReduce -= minMaxDiff;

                    if (mAvailableWidth >= 0)
                        break;
                }
            }
        }
    }

    private void reducePercent()
    {
        if (mAvailableWidth < 0)
        {
            double widthToReduce = 0.0;

            for (int columnIndex = mColumns.length - 1; columnIndex >= 0; columnIndex--)
            {
                Column column = mColumns[columnIndex];

                if (column.getEffectivePreferredWidth().isPercent())
                    widthToReduce += column.getCalculatedWidth() - column.getEffectiveLayoutWidth().getMin();
            }

            for (int columnIndex = mColumns.length - 1; columnIndex >= 0 && widthToReduce > 0; columnIndex--)
            {
                Column column = mColumns[columnIndex];

                if (column.getEffectivePreferredWidth().isPercent())
                {
                    double minMaxDiff = column.getCalculatedWidth() - column.getEffectiveLayoutWidth().getMin();
                    double reduce = mAvailableWidth * minMaxDiff / widthToReduce;

                    column.setCalculatedWidth(column.getCalculatedWidth() + reduce);
                    mAvailableWidth -= reduce;
                    widthToReduce -= minMaxDiff;

                    if (mAvailableWidth >= 0)
                        break;
                }
            }
        }
    }

    /// <summary>
    /// Rounds calculated column widths to whole numbers of twips.
    /// </summary>
    private void roundWidthsToWholeTwips()
    {
        for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
        {
            Column column = mColumns[columnIndex];

            // Conversion from points to twips implies rounding.
            double roundedWidthInTwips = ConvertUtilCore.pointToTwip(column.getCalculatedWidth());

            column.setCalculatedWidth(ConvertUtilCore.twipToPoint(roundedWidthInTwips));
        }
    }

    /// <summary>
    /// Calculates the cell layout width.
    /// </summary>
    private LayoutWidth calculateCellLayoutWidth(Cell cell)
    {
        LayoutWidth layoutWidth = new LayoutWidth();

        for (Node childNode : (Iterable<Node>) cell.getChildNodes(NodeType.ANY, false))
            calculateChildNode(childNode, layoutWidth);

        layoutWidth.setMax(Math.max(layoutWidth.getMin(), layoutWidth.getMax()));

        PreferredWidth cellPreferredWidth = cell.getCellFormat().getPreferredWidth();

        if (cellPreferredWidth.isFixed() && cellPreferredWidth.isPositive())
            layoutWidth.setMax(Math.max(layoutWidth.getMin(), cellPreferredWidth.getValue()));

        layoutWidth.add(Extensions.getPaddingAndBorderWidth(cell));

        return layoutWidth;
    }

    private void calculateChildNode(Node childNode, LayoutWidth layoutWidth)
    {
        switch (childNode.getNodeType())
        {
            case NodeType.PARAGRAPH:
            {
                Paragraph paragraph = (Paragraph)childNode;
                layoutWidth.update(mParagraphMeasurer.getLayoutWidth(paragraph));
                break;
            }
            case NodeType.TABLE:
            {
                Table table = (Table)childNode;
                TableLayouter layouter = new TableLayouter(table, true);
                LayoutWidth newLayoutWidth = layouter.doLayout();
                layoutWidth.update(newLayoutWidth);
                msArrayList.add(mNestedTables, table);
                break;
            }
            case NodeType.STRUCTURED_DOCUMENT_TAG:
            {
                CompositeNode sdt = (CompositeNode)childNode;
                // NextNonMarkupNodeLimited may go out of parent node if the parent is SDT.
                for (Node node = sdt.getFirstNonMarkupDescendant(); node != null && node.isAncestorNode(sdt);
                        node = node.getNextNonMarkupNodeLimited())
                    calculateChildNode(node, layoutWidth);
                break;
            }
            default:
            {
                // Skip other types of nodes (should not actually exist).
                break;
            }
        }
    }

    private void setCellWidths()
    {
        for (int rowIndex = 0; rowIndex < mTable.getRows().getCount(); rowIndex++)
        {
            for (int columnIndex = 0; columnIndex < mColumns.length; columnIndex++)
            {
                Cell cell = getCellUsingGridSpans(mTable, columnIndex, rowIndex);

                // Skip grid columns in the middle of the cell.
                if (cell == null)
                    continue;

                double width = getCellWidth(cell, columnIndex);
                width = Math.min(width, MAX_TABLE_WIDTH);

                // Do not use CellFormat.Width, it sets the preferred width as well.
                cell.getCellPr().setWidth(ConvertUtilCore.pointToTwip(width));
            }
        }
    }

    private double getCellWidth(Cell cell, int columnIndex)
    {
        switch (cell.getCellFormat().getHorizontalMerge())
        {
            case CellMerge.NONE:
            case CellMerge.FIRST:
            {
                int gridSpan = Extensions.getHorizontalMergeSpan(cell);
                return getGridRangeWidth(columnIndex, gridSpan);
            }
            case CellMerge.PREVIOUS:
            {
                return 0.0;
            }
            default:
            {
                throw new IllegalStateException("Unknown cell merge.");
            }
        }
    }

    /// <summary>
    /// Lays out the immediate nested tables.
    /// </summary>
    private void processNestedTables()
    {
        for (int tableIndex = 0; tableIndex < mNestedTables.size(); tableIndex++)
        {
            Table table = mNestedTables.get(tableIndex);
            table.updateLayout();
        }
    }

    /// <summary>
    /// Gets the sum of grid column widths in the given range.
    /// </summary>
    private double getGridRangeWidth(int firstColumn, int columnCount)
    {
        int rangeStart = Math.max(firstColumn, 0);
        int rangeEnd = Math.min(rangeStart + columnCount, mColumns.length);

        double width = 0d;
        for (int column = rangeStart; column < rangeEnd; column++)
            width += mColumns[column].getCalculatedWidth();

        return width;
    }

    /// <summary>
    /// Gets a cell node from the given table using the specified column and row indices.
    /// </summary>
    /// <remarks>
    /// Returns null if the table doesn't contain such a cell.
    /// Unlike <see cref="Table.GetCell"/>, the method takes grid spans into account.
    /// Grid span handling was introduced per WORDSNET-13177
    /// </remarks>
    private static Cell getCellUsingGridSpans(Table table, int columnIndex, int rowIndex)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if ((rowIndex < 0) || (rowIndex >= table.getRows().getCount()))
            	return null;

        	if (columnIndex < 0)
            	return null;

        	CellCollection cells = table.getRows().get(rowIndex).getCells();

        	// DM It seems, grid before is not taken into account. 
        	// Let it be for now, the method is used by the "old layouter" only.
        	int cellColumn = 0;
        	Cell cellAtIndex = null;
        	for (Cell cell : (Iterable<Cell>) cells)
        	{
            	if (cellColumn == columnIndex)
            	{
                	// Cell found.
                	cellAtIndex = cell;
                	break;
            	}
            	else if (cellColumn < columnIndex)
            	{
                	// Next cell column:
                	cellColumn += cell.getCellPr().getGridSpan();
            	}
            	else
            	{
                	// Column index does not match a cell start in the given row. 
                	// This is normal when cells spanning several columns are present, this method just returns null in this case.
                	break;
            	}
        	}

        	return cellAtIndex;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private Cell mFixedContributor;
    private Cell mMaxContributor;
    private boolean mHasPercent;
    private double mSpanMaxWidth;

    /// <summary>
    /// The table to lay out.
    /// </summary>
    private /*final*/ Table mTable;
    /// <summary>
    /// An array of columns used to lay out the table.
    /// </summary>
    private Column[] mColumns;
    /// <summary>
    /// True if need to recalculate the layout widths.
    /// </summary>
    private boolean mAreLayoutWidthsDirty = true;
    /// <summary>
    /// The width of the table container's client area.
    /// </summary>
    private double mTableContainerWidth;
    /// <summary>
    /// The absolute calculated width of the table.
    /// </summary>
    private double mTableSpecifiedWidth;
    /// <summary>
    /// The min and max possible widths of the table.
    /// </summary>
    private LayoutWidth mTableLayoutWidth;
    /// <summary>
    /// An object which performs the measurement of a paragraph contents.
    /// </summary>
    private ParagraphMeasurer mParagraphMeasurer;
    /// <summary>
    /// A list of cells whose span is > 1.
    /// </summary>
    private ArrayList<SpanCell> mSpanCells;
    /// <summary>
    /// This is used in CalculateResultingWidths to determine the extra or overallocated width.
    /// </summary>
    private double mAvailableWidth;
    /// <summary>
    /// The total number of "auto" (not specified) width columns.
    /// </summary>
    private int mAutoCount;
    /// <summary>
    /// The total number of fixed width columns.
    /// </summary>
    private int mFixedCount;
    /// <summary>
    /// The total number of percent width columns.
    /// </summary>
    private int mPercentCount;
    /// <summary>
    /// The total minimum width occupied by the contents of the "auto" width columns.
    /// </summary>
    private double mTotalAutoMin;
    /// <summary>
    /// The total maximum width occupied by the contents of the "auto" width columns.
    /// </summary>
    private double mTotalAutoMax;
    /// <summary>
    /// The total maximum width occupied by the contents of the fixed width columns.
    /// </summary>
    private double mTotalFixedMax;
    /// <summary>
    /// The total value of the percent columns.
    /// </summary>
    private double mTotalPercent;
    /// <summary>
    /// Width of table container. If GrowAutofit option is not set table should be scaled to this margins.
    /// </summary>
    private double mMarginsWidth;
    /// <summary>
    /// A list of immediate nested tables.
    /// </summary>
    private ArrayList<Table> mNestedTables;
    /// <summary>
    /// Controls whether to suppress layout update of nested tables at the end of layout process.
    /// </summary>
    private /*final*/ boolean mSuppressNestedTablesUpdates;
    /// <summary>
    /// This is the maximum table width allowed in Word.
    /// </summary>
    private static final double MAX_TABLE_WIDTH = ConvertUtilCore.MAX_SIZE_POINT;  // 55.87 cm.
}

