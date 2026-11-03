// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.msString;
import com.aspose.MathUtil;


/// <summary>
/// Represents an HTML table during import.
/// </summary>
class HtmlTable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="cssStyleTracker">CSS style tracker.</param>
    /// <param name="isXhtml">
    /// Indicates whether the input document is XHTML and has been parsed according to XHTML rules.
    /// </param>
    private HtmlTable(
        CssStyleTracker cssStyleTracker,
        boolean isXhtml)
    {
        Debug.assertTrue(cssStyleTracker != null);

        mCssStyleTracker = cssStyleTracker;
        mRows = new ArrayList<HtmlRow>();
        mCaptions = new ArrayList<HtmlTableCaption>();
        mHtmlCols = new ArrayList<HtmlCol>();

        CssDeclarationCollection tableDeclarations = mCssStyleTracker.getElementDeclarations();
        mCssBorders = CssBoxBorders.createBorders(tableDeclarations, false);

        CssDeclaration borderCollapseDeclaration = tableDeclarations.get("border-collapse");
        mIsBorderCollapsed = (borderCollapseDeclaration != null) &&
                             borderCollapseDeclaration.getValue().equals(CssValue.Collapse);

        mIsXhtml = isXhtml;
    }

    /// <summary>
    /// Fills and returns an HtmlTable object from an HtmlNode that represents a table.
    /// </summary>
    /// <param name="tableNode">Should be a table element.</param>
    /// <param name="cssStyleTracker">CSS style tracker.</param>
    /// <param name="isXhtml">Indicates whether the input document is XHTML.</param>
    static HtmlTable create(
        HtmlElementNode tableNode,
        CssStyleTracker cssStyleTracker,
        boolean isXhtml)
    {
        Debug.assertTrue(tableNode != null);
        Debug.assertTrue(cssStyleTracker != null);
        Debug.assertTrue(tableNode == cssStyleTracker.getCurrentElement());

        // Need to check that the node name is 'table' because this tag does not depend on "display" style
        // and is always processed as table.
        Debug.assertTrue(("table".equals(tableNode.getName())) || (cssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE) ||
                     (cssStyleTracker.getElementDisplayType() == CssDisplayType.INLINE_TABLE));

        HtmlTable htmlTable = new HtmlTable(cssStyleTracker, isXhtml);
        htmlTable.fill(tableNode);
        return htmlTable;
    }

    /// <summary>
    /// Gets <see cref="HtmlTableCaption"/> object specified by index.
    /// </summary>
    HtmlTableCaption getCaption(int index)
    {
        return mCaptions.get(index);
    }

    /// <summary>
    /// Gets <see cref="HtmlCol"/> object by the specified column index.
    /// </summary>
    /// <param name="colIndex">Column index.</param>
    HtmlCol getCol(int colIndex)
    {
        return mHtmlCols.get(colIndex);
    }

    /// <summary>
    /// Fills the HtmlTable object from an HtmlNode that represents a table.
    /// </summary>
    /// <param name="tableNode">TABLE element or element with 'table' or 'inline-table' css 'display' type.</param>
    private void fill(HtmlElementNode tableNode)
    {
        isDisplayTable(!"table".equals(tableNode.getName()));

        processNode(tableNode);

        verticalMerge();

        // WORDSNET-20250 Remove extra empty cells that are added because of "colspan" attributes.
        removeExtraEmptyCells();

        makeRowsSameLength();

        setColumnCount(getMaxColumnCount());
    }

    private void processNode(HtmlElementNode node)
    {
        // Counters should be updated only once per element and they will be updated when this element will be processed
        // by the HTML table reader.
        mCssStyleTracker.pushElement(node, false);

        if (mCssStyleTracker.getElementDisplayState() == HtmlElementDisplayState.VISIBLE)
        {
            if (handleNodeStart(node))
            {
                for (HtmlNode child : node.getChildren())
                {
                    if (child instanceof HtmlElementNode)
                    {
                        processNode((HtmlElementNode)child);
                    }
                }
                handleNodeEnd(node);
            }
        }

        mCssStyleTracker.popElement();
    }

    /// <summary>
    /// Called for every node in the table when the node processing is started.
    /// </summary>
    /// <returns>Returns false if don't need to process children of this node and don't need to process end of this node.</returns>
    private boolean handleNodeStart(HtmlElementNode node)
    {
        // WORDSNET-21365 TR and TD elements should be processed before a check for 'display: table' CSS
        // property.
        if ((mIsCssTable && (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_ROW)) ||
            ("tr".equals(node.getName())))
        {
            // In case the table consists of non-table tags (display: table-cell, table-row, etc ),
            // sometimes it is required to create missing elements if they are not explicitly defined.
            if (!"tr".equals(node.getName()))
                handleGroupNodeStart(node);

            return handleRowNodeStart(node);
        }

        if ((mIsCssTable && (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_CELL)) ||
            ("td".equals(node.getName())) || ("th".equals(node.getName())))
        {
            // In case the table consists of non-table tags (display: table-cell, table-row, etc ),
            // sometimes it is required to create missing elements if they are not explicitly defined.
            if (mIsCssTable && (mRows.size() == 0) && ((!"td".equals(node.getName())) || (!"th".equals(node.getName()))))
            {
                handleGroupNodeStart(node);
                handleRowNodeStart(node);
            }

            handleCellNodeStart(node);
            return true;
        }

        if ((mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE) ||
            (mCssStyleTracker.getElementDisplayType() == CssDisplayType.INLINE_TABLE) ||
            ("table".equals(node.getName())))
        {
            if (mIsInTable)
                return false; // Do not process nested table here. HtmlTable represents one table.
            mIsInTable = true;
            mIsCssTable = !"table".equals(node.getName());
            return true;
        }

        if ((mIsCssTable && (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_CAPTION)) ||
            ("caption".equals(node.getName())))
        {
            msArrayList.add(mCaptions, new HtmlTableCaption(node, mCssStyleTracker.getElementDeclarations()));
            // Do not search for table elements inside captions.
            return false;
        }

        if ((mIsCssTable &&
             ((mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_HEADER_GROUP) ||
              (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_FOOTER_GROUP) ||
              (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_ROW_GROUP))) ||
            ("thead".equals(node.getName())) || ("tbody".equals(node.getName())) || ("tfoot".equals(node.getName())))
        {
            handleGroupNodeStart(node);
            return true;
        }

        if ((mIsCssTable && (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_COLUMN_GROUP)) ||
            ("colgroup".equals(node.getName())))
        {
            handleColumnGroupNodeStart();
            return true;
        }

        if ((mIsCssTable && (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_COLUMN)) ||
            ("col".equals(node.getName())))
        {
            if (mCurrentHtmlColGroup == null)
                mCurrentHtmlColGroup = new HtmlColGroup();

            // WORDSNET-10157 If 'col' element has 'span' attribute, then 'col' attributes should be applied a 'span'
            // number times.
            // WORDSNET-19917 The "span" value of a "col" attribute must be greater than 0 and less
            // than or equal to 1000, and its default value is 1.
            // See https://html.spec.whatwg.org/multipage/tables.html#attr-col-span
            int span = node.getAttributes().getAttributeValue("span", 1, 1000);
            for (int i = 0; i < span; i++)
                msArrayList.add(mHtmlCols, new HtmlCol(mCssStyleTracker.getElementDeclarations(), mCurrentHtmlColGroup));

            // Do not search for table elements inside col.
            return false;
        }

        return false;
    }

    private void handleColumnGroupNodeStart()
    {
        mCurrentHtmlColGroup = new HtmlColGroup(mCssStyleTracker.getElementDeclarations());
    }

    private void handleGroupNodeStart(HtmlElementNode node)
    {
        mCurrentHtmlRowGroup = new HtmlRowGroup(node, mCssStyleTracker.getElementDeclarations());
    }

    private void handleCellNodeStart(HtmlElementNode node)
    {
        // WORDSNET-8117 In order to provide export/import roundtrip
        // here we should ignore unnecessary cells previously added on HTML import.
        String style = node.getAttributes().getAttributeValue("style", "");
        Regex styleRegex = new Regex("width:0pt; height:\\d+(\\.\\d+)?pt; border:none");
        if ((msString.equals(node.getInnerText(), "")) && styleRegex.isMatch(style))
            return;

        // If have no rows but got a TD, it is invalid table, ignore TD.
        if (mRows.size() == 0)
            return;

        // WORDSNET-19917 The "colspan" value of a "td" element must be greater than 0 and less
        // than or equal to 1000.
        // See https://html.spec.whatwg.org/multipage/tables.html#attr-tdth-colspan
        int colSpan = node.getAttributes().getAttributeValue("colspan", 1, 1000);

        // WORDSNET-19917 The "rowspan" value of a "td" element must be greater than 0 and less
        // than or equal to 65534.
        // See https://html.spec.whatwg.org/multipage/tables.html#attr-tdth-rowspan
        int rowSpan = node.getAttributes().getAttributeValue("rowspan", 1, 65534);

        HtmlCell firstCell = new HtmlCell(node, CssBoxBorders.createBorders(mCssStyleTracker.getElementDeclarations(), true));
        firstCell.setColSpan(colSpan);
        firstCell.setUnrestrictedColSpan(colSpan);
        firstCell.setRowSpan(rowSpan);
        getCurRow().add(firstCell);

        if (colSpan > 1)
        {
            //Process horizontal merge by adding more cells to the row.
            firstCell.setHorizontalMerge(CellMerge.FIRST);

            for (int remainingColSpan = colSpan - 1; remainingColSpan > 0; --remainingColSpan)
            {
                HtmlCell cell = new HtmlCell(firstCell.getCssBorders());
                cell.setHorizontalMerge(CellMerge.PREVIOUS);
                cell.setHorizontalMergeFirstCell(firstCell);

                // Store 'rowspan' value in generated empty cells in case the original cell has both
                // 'colspan' and 'rowspan' attributes specified. 'rowspan' will be processed later, after
                // all rows are loaded.
                cell.setColSpan(remainingColSpan);
                cell.setUnrestrictedColSpan(remainingColSpan);
                cell.setRowSpan(rowSpan);
                getCurRow().add(cell);
            }
        }

        // WORDSNET-9219 Consider only cells which span one row.
        if (rowSpan == 1)
        {
            CssDeclaration heightDeclaration = mCssStyleTracker.getElementDeclarations().get("height");
            if (heightDeclaration != null)
            {
                double cellHeight = CssUtil.lengthToPoint(heightDeclaration.getValue());
                if (!MathUtil.isMinValue(cellHeight) && (cellHeight > getCurRow().getMaxCellHeightInRow()))
                    getCurRow().setMaxCellHeightInRow(cellHeight);
            }
        }
    }

    private boolean handleRowNodeStart(HtmlElementNode node)
    {
        // WORDSNET-968 Remove the zero height extra row appended by AW to the HTML table during export.
        // The last table row with empty cells can be used to define column widths. It would be correct not to delete it
        // but sometimes it's enclosed with <!--[if !supportMisalignedColumns]--> - <!--[endif]--> brackets.
        // MS Word skips such row but we don't support this syntax and so it's better to just remove empty row.
        if (isColumnAlignmentRow(node, mCssStyleTracker.getElementDeclarations()))
        {
            return false;
        }

        // WORDSNET-8838 If a table has no row group elements, the HTML 5 parser creates a TBODY element enclosing all
        // table rows. The XHTML parser, however, returns such tables without any correction. As a result, tables in XHTML
        // documents may have no row group elements.
        // In order to work around that issue, here we create a new fake TBODY element without any style declarations.
        HtmlRowGroup rowGroup = mCurrentHtmlRowGroup;
        if ((rowGroup == null) && mIsXhtml)
        {
            rowGroup = new HtmlRowGroup(new HtmlElementNode("tbody", false), new CssDeclarationCollection());
        }

        msArrayList.add(mRows, new HtmlRow(node, mCssStyleTracker.getElementDeclarations(), rowGroup));

        return true;
    }

    /// <summary>
    /// Called for every node in the table when the node processing is finished.
    /// </summary>
    private void handleNodeEnd(HtmlElementNode node)
    {
        if ((mIsCssTable &&
                ((mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_HEADER_GROUP) ||
                 (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_FOOTER_GROUP) ||
                 (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_ROW_GROUP))) ||
            ("thead".equals(node.getName())) || ("tbody".equals(node.getName())) || ("tfoot".equals(node.getName())))
        {
            mCurrentHtmlRowGroup = null;
            return;
        }

        if ((mIsCssTable && (mCssStyleTracker.getElementDisplayType() == CssDisplayType.TABLE_COLUMN_GROUP)) ||
            ("colgroup".equals(node.getName())))
        {
            Debug.assertTrue(mCurrentHtmlColGroup != null);

            // "Colgroup" attributes are applied if the column group doesn't contain "col" elements.
            if ((mHtmlCols.size() == 0) || (mHtmlCols.get(mHtmlCols.size() - 1).getColGroup() != mCurrentHtmlColGroup))
            {
                // A <colgroup> element can cover multiple columns if the "span" attribute is specified.
                // WORDSNET-19917 The "span" value of a "colgroup" attribute must be greater than 0 and less
                // than or equal to 1000, and its default value is 1.
                // See https://html.spec.whatwg.org/multipage/tables.html#attr-colgroup-span
                int span = node.getAttributes().getAttributeValue("span", 1, 1000);
                for (int i = 0; i < span; i++)
                {
                    msArrayList.add(mHtmlCols, new HtmlCol(mCurrentHtmlColGroup.getDeclarations(), mCurrentHtmlColGroup));
                }
            }

            mCurrentHtmlColGroup = null;
            return;
        }
    }

    /// <summary>
    /// Inserts a cell to the specified row. Shifts the remaining cells in such a way that vertically merged cells
    /// do not change their positions. This is necessary to properly build a table when colspan and rowspan
    /// are used together.
    /// </summary>
    /// <param name="rowIndex"></param>
    /// <param name="cellIndex"></param>
    /// <param name="cell"></param>
    private void insertCell(int rowIndex, int cellIndex, HtmlCell cell)
    {
        HtmlRow row = this.get(rowIndex);

        // Add a placeholder cell.
        row.add(null);

        int dstIndex = row.getCellCount() - 1;
        int srcIndex = dstIndex - 1;

        while (srcIndex >= cellIndex)
        {
            // Decrement the source index until it points to a non-merged cell.
            while (row.get(srcIndex).getVerticalMerge() != CellMerge.NONE)
            {
                srcIndex--;

                // WORDSNET-20270 If "rowspan" of a HTML cell is greater than the number of available rows,
                // don't try to insert a cell to a non-existent row.
                if (srcIndex < 0)
                {
                    // Remove the cell that we've added at the beginning of this method.
                    row.removeLastCell();
                    return;
                }
            }

            // Shift the cell.
            row.set(dstIndex, row.get(srcIndex));

            dstIndex = srcIndex;
            srcIndex = dstIndex - 1;
        }

        // Insert the target cell.
        row.set(cellIndex, cell);
    }

    private void addCellsToRow(int rowIndex, int cellCount, CssBoxBorders cellBorders)
    {
        for (int i = 0; i < cellCount; i++)
            this.get(rowIndex).add(new HtmlCell(cellBorders));
    }

    private int getMaxColumnCount()
    {
        int result = 0;
        for (HtmlRow row : mRows)
        {
            result = Math.max(result, row.getCellCount());
        }
        return result;
    }

    /// <summary>
    /// Adds empty cells to rows that are shorter than the maximum column count.
    /// Some rows could be shorter due to merged cells or just could be because it is so in the HTML.
    /// Our algorithms require all rows to have same number of cells to work properly.
    ///
    /// Note neither Word nor IE require that.
    /// </summary>
    private void makeRowsSameLength()
    {
        int columnCount = getMaxColumnCount();

        for (HtmlRow row : mRows)
        {
            while (row.getCellCount() < columnCount)
            {
                row.add(new HtmlCell(CssBoxBorders.createEmpty()));
            }
        }
    }

    private void removeExtraEmptyCells()
    {
        int maxColumnCountWithoutExtraEmptyCells = 0;
        for (HtmlRow row : mRows)
        {
            maxColumnCountWithoutExtraEmptyCells = Math.max(
                maxColumnCountWithoutExtraEmptyCells,
                row.getColumnCountWithoutTrailingEmptyExtraCells());
        }

        // Remove all empty extra cells at the end of each row.
        for (HtmlRow row : mRows)
        {
            while (row.getCellCount() > maxColumnCountWithoutExtraEmptyCells)
            {
                HtmlCell cell = row.get(row.getCellCount() - 1);
                HtmlCell firstMergedCell = cell.getHorizontalMergeFirstCell();
                if ((firstMergedCell != null) && (firstMergedCell.getColSpan() > 1))
                {
                    firstMergedCell.setColSpan(firstMergedCell.getColSpan() - 1)/*Property--*/;
                }

                row.removeLastCell();
            }
        }
    }

    /// <summary>
    /// Creates a required amount of empty row span cells and marks them with vertical merge
    /// flag as specified by row span.
    ///
    /// <see cref="MakeRowsSameLength"/> simply adds empty cells to the end of the rows and it seems to
    /// be not enough when processing rowspans. To correctly build a table, we should add
    /// (rowspan - 1) empty cells below a cell with rowspan attribute.
    /// </summary>
    private void verticalMerge()
    {
        int columnCount = getMaxColumnCount();

        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++)
        {
            for (int rowIndex = 0; rowIndex < mRows.size(); rowIndex++)
            {
                HtmlRow row = this.get(rowIndex);

                if (row.getCellCount() <= columnIndex)
                    continue;

                HtmlCell firstCell = row.get(columnIndex);

                // Limit rowspan by the number of remaining rows below.
                int indexAfterRowSpan = Math.min(rowIndex + firstCell.getRowSpan(), mRows.size());

                // Start with 1 because this is for the second and consecutive cells.
                for (int spanRowIndex = rowIndex + 1; spanRowIndex < indexAfterRowSpan; spanRowIndex++)
                {
                    // WORDSNET-15611 Rowspans cannot cross row group borders.
                    if (this.get(spanRowIndex).getRowGroup() != row.getRowGroup())
                    {
                        break;
                    }

                    // The first (0 index) cell in the span gets this attribute, but only if there are rows to span.
                    firstCell.setVerticalMerge(CellMerge.FIRST);

                    if (columnIndex + 1 > this.get(spanRowIndex).getCellCount())
                    {
                        // The next row is shorter than that with the rowspan cell, so append empty cells.
                        addCellsToRow(spanRowIndex, columnIndex - this.get(spanRowIndex).getCellCount() + 1, firstCell.getCssBorders());
                    }
                    else
                    {
                        // Otherwise insert an empty cell at cellIndex.
                        if (this.get(spanRowIndex).get(columnIndex).getHorizontalMerge() != CellMerge.PREVIOUS)
                            insertCell(spanRowIndex, columnIndex, new HtmlCell(firstCell.getCssBorders()));
                    }

                    HtmlCell curCell = this.get(spanRowIndex).get(columnIndex);
                    curCell.setVerticalMerge(CellMerge.PREVIOUS);
                    curCell.setVerticalMergeFirstCell(firstCell);

                    // WORDSNET-12435 Don't break existing horizontal merge.
                    if (curCell.getHorizontalMerge() != CellMerge.PREVIOUS)
                    {
                        // Copy the horizontal merge attribute from the top cell to all vertically merged
                        // cells below. This covers the situation when a cell has both colspan and rowspan.
                        curCell.setHorizontalMerge(firstCell.getHorizontalMerge());
                        curCell.setHorizontalMergeFirstCell(firstCell.getHorizontalMergeFirstCell());
                    }
                }
            }
        }
    }

    /// <summary>
    /// Checks whether an HTML node is a special row (tr) added by MS Word or Aspose.Words during export because
    /// the table contains misaligned cells.
    /// </summary>
    private static boolean isColumnAlignmentRow(HtmlElementNode node, CssDeclarationCollection declarations)
    {
        // The special row is always a <tr> element.
        if (!"tr".equals(node.getName()))
        {
            return false;
        }

        // The special row always has zero length.
        CssDeclaration heightDeclaration = declarations.get("height");
        if ((heightDeclaration == null) || !MathUtil.isZero(heightDeclaration.getValue().getFirstValue().getDoubleValue()))
        {
            return false;
        }

        // The special row is always last in a table.
        if (node.getNextSibling() != null)
        {
            if (!(node.getNextSibling() instanceof HtmlTextNode)
                || node.getNextSibling().containsText()
                || (node.getNextSibling().getNextSibling() != null))
            {
                // There is an element or meaninful text after this row.
                return false;
            }
        }

        // The special row contains one or more empty cells that are always <td> elements. Each cell can contain nothing
        // but a single whitespace child.
        int cellCount = 0;
        for (HtmlNode child : node.getChildren())
        {
            if (child instanceof HtmlElementNode)
            {
                HtmlElementNode childElement = (HtmlElementNode)child;
                if ((!"td".equals(childElement.getName())) || (childElement.getChildren().getCount() > 1))
                {
                    return false;
                }
                if (childElement.getChildren().getCount() == 1)
                {
                    if (!(childElement.getChildren().get(0) instanceof HtmlTextNode) || childElement.getChildren().get(0).containsText())
                    {
                        return false;
                    }
                }
                ++cellCount;
            }
            else if (child.containsText())
            {
                return false;
            }
        }

        return cellCount > 0;
    }

    HtmlRow get(int index) { return mRows.get(index); }

    int getRowCount() { return mRows.size(); }

    /// <summary>
    /// Count of table captions.
    /// </summary>
    int getCaptionCount() { return mCaptions.size(); }

    /// <summary>
    /// This is the maximum number of columns, all rows are padded with cells to match this.
    /// </summary>
    int getColumnCount() { return mColumnCount; }; private void setColumnCount(int value) { mColumnCount = value; };

    private int mColumnCount;

    /// <summary>
    /// Gets count of 'col' elements declared in the table.
    /// </summary>
    int getColElementCount() { return mHtmlCols.size(); }

    /// <summary>
    /// CSS borders of the table.
    /// </summary>
    CssBoxBorders getCssBorders() { return mCssBorders; };

    private CssBoxBorders mCssBorders;

    /// <summary>
    /// Indicates whether the table borders are collapsed into a single border ('border-collapse:collapse').
    /// </summary>
    boolean isBorderCollapsed() { return mIsBorderCollapsed; };

    private boolean mIsBorderCollapsed;

    /// <summary>
    /// Indicates whether the table is defined not with 'table' tag,
    /// but with "display: table" and "display:inline-table" property.
    /// </summary>
    boolean isDisplayTable() { return mIsDisplayTable; }; private void isDisplayTable(boolean value) { mIsDisplayTable = value; };

    private boolean mIsDisplayTable;

    CssBorder getFirstRowFirstCellLeftBorder() { return mFirstRowFirstCellLeftBorder; }; void setFirstRowFirstCellLeftBorder(CssBorder value) { mFirstRowFirstCellLeftBorder = value; };

    private CssBorder mFirstRowFirstCellLeftBorder;

    CssBorder getFirstRowLastCellRightBorder() { return mFirstRowLastCellRightBorder; }; void setFirstRowLastCellRightBorder(CssBorder value) { mFirstRowLastCellRightBorder = value; };

    private CssBorder mFirstRowLastCellRightBorder;

    /// <summary>
    /// Gets the current HTML row.
    /// </summary>
    private HtmlRow getCurRow() { return mRows.get(mRows.size() - 1); }

    private /*final*/ ArrayList<HtmlRow> mRows;
    private /*final*/ CssStyleTracker mCssStyleTracker;
    private /*final*/ ArrayList<HtmlTableCaption> mCaptions;
    private /*final*/ ArrayList<HtmlCol> mHtmlCols;
    private HtmlRowGroup mCurrentHtmlRowGroup;
    private HtmlColGroup mCurrentHtmlColGroup;

    /// <summary>
    /// This helps to stop processing nested tables.
    /// </summary>
    private boolean mIsInTable;

    /// <summary>
    /// WORDSNET-16075, WORDSNET-13896 Indicates, if table is CSS table (not HTML table).
    /// Looks like that browsers don't mix HTML tables with CSS tables, and we do the same.
    /// </summary>
    private boolean mIsCssTable;
    private /*final*/ boolean mIsXhtml;
}

