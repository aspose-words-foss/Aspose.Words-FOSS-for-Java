// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.IntList;
import java.util.ArrayList;


/// <summary>
/// The class responsible for writing Tables into markdown.
/// </summary>
class MarkdownTableWriter
{
    /// <summary>
    /// Creates instance for a specified table.
    /// </summary>
    MarkdownTableWriter(Table table, MarkdownWriter writer)
    {
        Debug.assertTrue(writer != null);
        Debug.assertTrue(table != null);

        mWriter = writer;
        mTable = table;
    }

    /// <summary>
    /// Processes start of the table.
    /// </summary>
    void onTableStart() throws Exception
    {
        // WORDSNET-27382 Handle a new implemented option.
        if (isExportAsHtml())
        {
            String tableAsRawHtml = mTable.toString(mWriter.RawHtmlSaveOptions);
            msStringBuilder.append(getBuilder(), tableAsRawHtml);
            msStringBuilder.appendLine(getBuilder());
            return;
        }

        // Add one blank line before the table.
        if (!mTable.isFirstChild())
        {
            // WORDSNET-26064 We have allowed blank paragraphs in Document,
            // and they are natural separators in Markdown. So, no need to
            // add another one blank line if it has been existed there already.
            Paragraph prevPara = ms.as(mTable.getPreviousSibling(), Paragraph.class);
            if ((prevPara != null) && prevPara.hasChildNodes())
                msStringBuilder.append(getBuilder(), getParagraphBreak());
        }

        if (getUseHtmlSyntax())
            msStringBuilder.append(getBuilder(), "<table>");

        // WORDSNET-26757 Allow table to fall under a previous list item,
        // if it has the same left indent as this list item and thus make
        // appropriate left indent for the table.
        Paragraph listItemSource = MarkdownParagraphWriter.getListItemSource(mTable);
        MarkdownParagraphWriter listItemSourceWriter = mWriter.getParagraphWriterByNode(listItemSource);
        if (listItemSourceWriter != null)
        {
            int indentLength = 0;
            for (String listLabel : listItemSourceWriter.getListLabels())
            {
                if (listLabel != null)
                    indentLength += listLabel.length();
            }
            mIndent = msString.newString(' ', indentLength);
        }
    }

    /// <summary>
    /// Processes end of the table.
    /// </summary>
    void onTableEnd()
    {
        // When this option is enabled, whole table is processed in 'OnTableStart()', so just exit.
        if (isExportAsHtml())
            return;

        if (getUseHtmlSyntax())
        {
            msStringBuilder.append(getBuilder(), "</table>");
            msStringBuilder.append(getBuilder(), getParagraphBreak());
        }
    }

    /// <summary>
    /// Starts nested table.
    /// </summary>
    MarkdownTableWriter startNestedTable(Table table) throws Exception
    {
        // When this option is enabled, nested tables are processed in 'OnTableStart()' of parent table.
        if (!isExportAsHtml())
        {
            // Write collected paragraphs into current cell.
            flushParagraphs();

            // Fill with empty cells to the end and finish split row.
            // WORDSNET-25339 Resilience when number of columns in table cannot be determined properly.
            msStringBuilder.append(getBuilder(), CellBlock.SEPARATOR_CHAR, Math.max(0, getColsCount() - getCurCell().getColumnIndex()));
            msStringBuilder.append(getBuilder(), getParagraphBreak());

            // Write delimiter row for split part of the table, if it is not written yet.
            if (getCurCell().getParentRow() == getFirstRow())
            {
                writeDelimiterRow(getFirstRow());
                msStringBuilder.append(getBuilder(), getParagraphBreak());
            }

            // Add one blank line before nested table.
            msStringBuilder.append(getBuilder(), getParagraphBreak());

            // Remember cell where nested table is inserted.
            mSplitCell = getCurCell();
        }

        return new MarkdownTableWriter(table, mWriter);
    }

    /// <summary>
    /// Processes end of a nested table.
    /// </summary>
    void onNestedTableEnd() throws Exception
    {
        // When this option is enabled, whole table is processed in 'OnTableStart()', so just exit.
        if (isExportAsHtml())
            return;

        // Add one blank line after nested table.
        msStringBuilder.append(getBuilder(), getParagraphBreak());

        // Fill current row with empty cells to the split cell.
        int cellsCountBefore = mSplitCell.getColumnIndex() + 1;
        msStringBuilder.append(getBuilder(), CellBlock.SEPARATOR_CHAR, cellsCountBefore);

        // Write collected paragraphs into the current cell.
        flushParagraphs();
    }

    /// <summary>
    /// Processes end of a row.
    /// </summary>
    void onRowStart(Row row)
    {
        // When this option is enabled, whole table is processed in 'OnTableStart()', so just exit.
        if (isExportAsHtml())
            return;

        // WORDSNET-26757 Add left indent to the table if it falls into a list.
        msStringBuilder.append(getBuilder(), mIndent);

        if (getUseHtmlSyntax())
            msStringBuilder.append(getBuilder(), "<tr>");
    }

    /// <summary>
    /// Processes end of a row.
    /// </summary>
    void onRowEnd(Row row)
    {
        // When this option is enabled, whole table is processed in 'OnTableStart()', so just exit.
        if (isExportAsHtml())
            return;

        if (getUseHtmlSyntax())
        {
            msStringBuilder.append(getBuilder(), "</tr>");
            msStringBuilder.append(getBuilder(), getParagraphBreak());
        }
        else
        {
            // Close last cell.
            // WORDSNET-25339 Resilience when number of columns in table cannot be determined properly.
            msStringBuilder.append(getBuilder(), CellBlock.SEPARATOR_CHAR, Math.max(1, getColsCount() - getCurCell().getColumnIndex()));
            msStringBuilder.append(getBuilder(), getParagraphBreak());

            if (row == getFirstRow())
            {
                writeDelimiterRow(row);
                msStringBuilder.append(getBuilder(), getParagraphBreak());
            }
        }
    }

    /// <summary>
    /// Processes start of a cell.
    /// </summary>
    void onCellStart(Cell cell)
    {
        // When this option is enabled, whole table is processed in 'OnTableStart()', so just exit.
        if (isExportAsHtml())
            return;

        mCurCell = cell;

        if (getUseHtmlSyntax())
        {
            if (cell.getCellPr().isMergedToPrevious())
                return;

            String cellTag = (cell.getParentRow() == getFirstRow()) ? "th" : "td";
            String rowSpan = "";
            String colSpan = "";

            if ((cell.getCellPr().getHorizontalMerge() == CellMerge.FIRST) || (cell.getCellPr().get(CellAttr.SYS_CELL_SPAN) != null))
                colSpan = msString.format(" colspan=\"{0}\"", getHorizontalMergedCellCount(cell));
            if (cell.getCellPr().getVerticalMerge() == CellMerge.FIRST)
                rowSpan = msString.format(" rowspan=\"{0}\"", getVerticalMergedCellCount(cell));

            msStringBuilder.append(getBuilder(), msString.format("<{0}{1}{2}{3}>", cellTag, colSpan, rowSpan, getVerticalAlign()));
        }
        else
            getBuilder().append(CellBlock.SEPARATOR_CHAR);
    }

    /// <summary>
    /// Processes end of a cell.
    /// </summary>
    void onCellEnd(Cell cell) throws Exception
    {
        // When this option is enabled, whole table is processed in 'OnTableStart()', so just exit.
        if (isExportAsHtml())
            return;

        flushParagraphs();
        if (getUseHtmlSyntax() && !cell.getCellPr().isMergedToPrevious())
        {
            String cellTag = (cell.getParentRow() == getFirstRow()) ? "th" : "td";
            msStringBuilder.appendFormat(getBuilder(), "</{0}>", cellTag);
        }
    }

    /// <summary>
    /// Adds a specified paragraph to the list of paragraphs pending for writing into current cell.
    /// </summary>
    /// <remarks>
    /// We don`t write paragraph inside cell immediately as there can be multiple paragraphs in the cell.
    /// In such case they should be wrapped into HTML elements &lt;p&gt; and &lt;/p&gt;.
    /// </remarks>
    void addParagraph(MarkdownParagraphWriter paragraphWriter)
    {
        msArrayList.add(mParagraphs, paragraphWriter);
    }

    /// <summary>
    /// Flushes paragraphs to the writer.
    /// </summary>
    private void flushParagraphs() throws Exception
    {
        for (MarkdownParagraphWriter paragraph : mParagraphs)
        {
            if ((mWriter.getSaveOptions().getEmptyParagraphExportMode() == MarkdownEmptyParagraphExportMode.NONE) && paragraph.isEmpty())
                continue;

            // Write HTML paragraph opening element, if this is multi-paragraph cell.
            if (mParagraphs.size() > 1)
                msStringBuilder.append(getBuilder(), HTML_PARA_OPEN);

            paragraph.write();

            // Write HTML paragraph closing element, if this is multi-paragraph cell.
            if (mParagraphs.size() > 1)
                msStringBuilder.append(getBuilder(), HTML_PARA_CLOSE);
        }

        mParagraphs.clear();
    }

    /// <summary>
    /// Writes delimiter row based on a specified row.
    /// </summary>
    private void writeDelimiterRow(Row row)
    {
        // WORDSNET-26757 Add left indent to the table if it falls into a list.
        msStringBuilder.append(getBuilder(), mIndent);

        getBuilder().append(CellBlock.SEPARATOR_CHAR);

        // WORDSNET-25339 Loop through the all columns of table
        // instead of all cells of the current row.
        for (int i = 0; i < getColsCount(); i++)
        {
            // It is not necessary, but lets add one indentation space for better looking.
            getBuilder().append(' ');

            // WORDSNET-20425 New option is introduced to control over alignment of the tables content.
            /*ParagraphAlignment*/int alignment = ParagraphAlignment.LEFT;
            if ((i < row.getCells().getCount()) && row.getCells().get(i).getFirstParagraph() != null)
                alignment = getParagraphAlignment(row.getCells().get(i).getFirstParagraph());

            if ((alignment == ParagraphAlignment.LEFT) || (alignment == ParagraphAlignment.CENTER))
                getBuilder().append(CellBlock.ALIGNMENT_CHAR);

            getBuilder().append(CellBlock.CONTENT_CHAR);

            if ((alignment == ParagraphAlignment.RIGHT) || (alignment == ParagraphAlignment.CENTER))
                getBuilder().append(CellBlock.ALIGNMENT_CHAR);

            // It is not necessary, but lets add one indentation space for better looking.
            getBuilder().append(' ');
            getBuilder().append(CellBlock.SEPARATOR_CHAR);
        }
    }

    /// <summary>
    /// Returns paragraph alignment for a specified paragraph in respect of
    /// <see cref="MarkdownSaveOptions.TableContentAlignment"/> saving option.
    /// </summary>
    private /*ParagraphAlignment*/int getParagraphAlignment(Paragraph paragraph)
    {
        switch (mWriter.getSaveOptions().getTableContentAlignment())
        {
            case TableContentAlignment.LEFT:
                return ParagraphAlignment.LEFT;
            case TableContentAlignment.CENTER:
                return ParagraphAlignment.CENTER;
            case TableContentAlignment.RIGHT:
                return ParagraphAlignment.RIGHT;
            default:
                return paragraph.getParaPr().getAlignment();
        }
    }

    /// <summary>
    /// Gets the format string for the vertical alignment of a table cell.
    /// If the vertical alignment value is equal to the default value, an empty string is returned.
    /// </summary>
    private String getVerticalAlign()
    {
        if (mCurCell.getCellPr().getVerticalAlignment() == CellVerticalAlignment.CENTER)
            return "";

        return msString.format(" valign=\"{0}\"", (mCurCell.getCellPr().getVerticalAlignment() == CellVerticalAlignment.TOP)
                    ? "top"
                    : "bottom");
    }

    /// <summary>
    /// Gets the number of horizontally merged cells adjacent to the specified cell.
    /// </summary>
    private static int getHorizontalMergedCellCount(Cell cell)
    {
        int result = 1;

        // If the Sys_CellSpan attribute is set, we prefer to use it.
        if (cell.getCellPr().get(CellAttr.SYS_CELL_SPAN) != null)
            return (/*int*/Integer)cell.getCellPr().get(CellAttr.SYS_CELL_SPAN);

        Cell curCell = cell.getNextCell();
        while ((curCell != null) && (curCell.getCellPr().getHorizontalMerge() == CellMerge.PREVIOUS))
        {
            result++;
            curCell = curCell.getNextCell();
        }

        return result;
    }

    /// <summary>
    /// Gets the number of vertically merged cells adjacent to the specified cell.
    /// </summary>
    private static int getVerticalMergedCellCount(Cell cell)
    {
        int result = 1;
        int colIndex = cell.getColumnIndex();

        Row row = cell.getParentRow().getNextRow();
        // WORDSNET-25335 Get rid of row.NextRow in clause to avoid null reference exception.
        while ((row != null) && (row.getCells().get(colIndex) != null) && (row.getCells().get(colIndex).getCellPr().getVerticalMerge() == CellMerge.PREVIOUS))
        {
            result++;
            row = row.getNextRow();
        }

        return result;
    }

    /// <summary>
    /// Returns True if the current table has merged cells.
    /// </summary>
    private boolean hasMergedCells()
    {
        for (Row row : (Iterable<Row>) mTable.getRows())
        for (Cell cell : (Iterable<Cell>) row.getCells())
            if (cell.getCellPr().isMergedToPrevious())
                return true;

        return false;
    }

    /// <summary>
    /// Gets the number of columns in the current table.
    /// </summary>
    private int getColsCount()
    {
        if (mColsCount == -1)
        {
            // First try to get from TableGrid.
            IntList colsWidths = (IntList)mTable.getTablePr().get(TableAttr.SYS_TABLE_GRID);
            if (colsWidths != null)
            {
                mColsCount = colsWidths.getCount();
            }
            // Then try to get from First row cells.
            else if (getFirstRow() != null)
            {
                mColsCount = getFirstRow().getCells().getCount();
            }

            if (mColsCount < 0)
                mColsCount = 0;
        }

        return mColsCount;
    }

    /// <summary>
    /// Returns True if Html syntax shall be used to correctly write the attributes of the current table.
    /// </summary>
    boolean getHtmlSyntaxRequired()
    {
        // WORDSNET-28289 Added a new flag into MarkdownExportAsHtml.
        if (mWriter.getSaveOptions() != null &&
            (mWriter.getSaveOptions().getExportAsHtml() & MarkdownExportAsHtml.NON_COMPATIBLE_TABLES) == 0)
            return false;

        // Markdown does not support nested table in Html syntax.
        boolean hasNestedTable = mTable.getChild(NodeType.TABLE, 0, true) != null;

        return hasMergedCells() && !hasNestedTable;
    }

    /// <summary>
    /// Returns true, if the current writer is in Html syntax mode.
    /// </summary>
    private boolean getUseHtmlSyntax() { return mWriter.getUseHtmlSyntax(); }

    /// <summary>
    /// The first logical row.
    /// </summary>
    /// <remarks>
    /// Note, this can be different from the actual first row of the table due to nested tables,
    /// which split the table and moves first logical row to the place where it is inserted.
    /// </remarks>
    private Row getFirstRow() { return (mSplitCell == null) ? mTable.getFirstRow() : mSplitCell.getParentRow(); }

    /// <summary>
    /// The current cell.
    /// </summary>
    private Cell getCurCell() { return (mCurCell == null) ? getFirstRow().getFirstCell() : mCurCell; }

    /// <summary>
    /// Gets current builder.
    /// </summary>
    private StringBuilder getBuilder() { return mWriter.getBuilder(); }

    /// <summary>
    /// Gets string representing current paragraph break value to use in the writer.
    /// </summary>
    private String getParagraphBreak() { return mWriter.getSaveOptions().getParagraphBreak(); }

    /// <summary>
    /// Gets a boolean value indicating either table should be exported as raw HTML
    /// in accordance with specified <see cref="MarkdownSaveOptions.ExportAsHtml"/> option.
    /// </summary>
    private boolean isExportAsHtml()
    {
        return (mWriter.getSaveOptions() != null) && ((mWriter.getSaveOptions().getExportAsHtml() & MarkdownExportAsHtml.TABLES) != 0);
    }

    /// <summary>
    /// The table being written.
    /// </summary>
    private /*final*/ Table mTable;

    /// <summary>
    /// The underlying writer.
    /// </summary>
    private /*final*/ MarkdownWriter mWriter;

    /// <summary>
    /// The list of paragraphs pending for writing.
    /// </summary>
    /// <remarks>
    /// We don't write paragraphs immediately inside a cell, because we need to know
    /// either that cell has multiple paragraphs or not (as this should be written using inline HTML blocks).
    /// </remarks>
    private /*final*/ ArrayList<MarkdownParagraphWriter> mParagraphs = new ArrayList<MarkdownParagraphWriter>();

    /// <summary>
    /// The currently processing cell.
    /// </summary>
    private Cell mCurCell;

    /// <summary>
    /// The cell where nested table is inserted, if any.
    /// </summary>
    private Cell mSplitCell;

    private int mColsCount = -1;

    private static final String HTML_PARA_OPEN = "<p>";
    private static final String HTML_PARA_CLOSE = "</p>";

    /// <summary>
    /// Specifies an indent of the table.
    /// </summary>
    private String mIndent = "";
}

