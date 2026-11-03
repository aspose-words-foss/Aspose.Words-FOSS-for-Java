// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.IntToIntDictionary;
import com.aspose.IntToObjDictionary;
import com.aspose.ms.System.msMath;
import com.aspose.Debug;
import com.aspose.ConvertUtilCore;
import com.aspose.ArgumentUtil;
import java.awt.Color;
import com.aspose.IntList;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.DateTime;


/// <summary>
/// Represents a table in a Word document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="Table"/> is a block-level node and can be a child of classes derived from <see cref="Story"/> or
/// <see cref="InlineStory"/>.</p>
/// <p><see cref="Table"/> can contain one or more <see cref="Row"/> nodes.</p>
/// <p>A minimal valid table needs to have at least one <see cref="Row"/>.</p>
/// </remarks>
public class Table extends CompositeNode<Row>
{
    /// <summary>
    /// Initializes a new instance of the <see cref="Table"/> class.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="Table"/> is created, it belongs to the specified document, but is not
    /// yet part of the document and <see cref="Node.ParentNode"/> is <c>null</c>.</p>
    /// <p>To append <see cref="Table"/> to the document use <see cref="CompositeNode.InsertAfter{T}(T, Node)"/> or <see cref="CompositeNode.InsertBefore{T}(T, Node)"/>
    /// on the story where you want the table inserted.</p>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    public Table(DocumentBase doc)
    {
    	super(doc);
	
    }

    /// <summary>
    /// Converts cells horizontally merged by width to cells merged by <see cref="CellFormat.HorizontalMerge" />.
    /// </summary>
    /// <remarks>
    /// <p>Table cells can be horizontally merged either using merge flags <see cref="CellFormat.HorizontalMerge" /> or using cell width <see cref="CellFormat.Width" />.</p>
    /// <p>When table cell is merged by width property <see cref="CellFormat.HorizontalMerge" /> is meaningless but sometimes having merge flags is more convenient way.</p>
    /// <p>Use this method to transforms table cells horizontally merged by width to cells merged by merge flags.</p>
    /// </remarks>
    public void convertToHorizontallyMergedCells()
    {
        // Use CellerTable to build table grid.
        CellerTable celler = new CellerTable(this, CellerTableOptions.GOTO_MERGED_CELLS);
        if (celler.getColumnCount() == 0)
            return;

        celler.gotoFirstRow();

        getTablePr().remove(TableAttr.SYS_CALCULATED_TABLE_GRID);
        getTablePr().remove(TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM);

        for(Row row : (Iterable<Row>) getRows())
        {
            ArrayList<Integer>spans = new ArrayList<Integer>();
            ArrayList<Cell>cells = new ArrayList<Cell>();

            // Collect spans to avoid side effect when table is modified.
            for (Cell cell : (Iterable<Cell>) row.getCells())
            {
                int colSpan = 1;

                celler.gotoFirstCell();
                for (int col = 0; col < celler.getColumnCount(); col++)
                {
                    if (msObject.referenceEquals(cell, celler.getCurCell().getCellNode()))
                    {
                        colSpan = celler.getCurCell().getColSpan();
                        break;
                    }

                    celler.gotoNextCell();
                }

                msArrayList.add(cells, cell);
                msArrayList.add(spans, colSpan);
            }

            // Now we have spans for each cells, transform table.
            for (int i = 0; i < cells.size(); i++)
            {
                Cell cell = cells.get(i);
                int span = spans.get(i);

                cell.getCellPr().remove(CellAttr.SYS_CELL_SPAN);

                if (span == 1)
                    continue;

                // First try next cell as it might be already merged by flag.
                // Naive check. We should go deeper and ensure that whole merge chain already exist. Postpone for a while.
                if ((cell.getNextCell() != null) && ((/*CellMerge*/Integer)cell.getNextCell().getCellPr().fetchAttr(CellAttr.HORIZONTAL_MERGE) == CellMerge.PREVIOUS))
                    continue;

                cell.getCellPr().setAttr(CellAttr.HORIZONTAL_MERGE, CellMerge.FIRST);
                for (int j = 0; j < span - 1; j++)
                {
                    Cell mergedCell = (Cell)cell.deepClone(false);
                    mergedCell.getCellPr().setAttr(CellAttr.WIDTH, 0);
                    mergedCell.getCellPr().setAttr(CellAttr.PREFERRED_WIDTH, PreferredWidth.fromPoints(0.0));
                    mergedCell.getCellPr().setAttr(CellAttr.HORIZONTAL_MERGE, CellMerge.PREVIOUS);
                    mergedCell.appendChild(new Paragraph(mergedCell.getDocument()));

                    cell.insertNext(mergedCell);
                }
            }

            celler.gotoNextRow();
        }
    }

    /// <summary>
    /// Returns <see cref="NodeType.Table"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.TABLE; }

    /// <summary>
    /// Returns the first <see cref="Row"/> node in the table.
    /// </summary>
    public Row getFirstRow() { return (Row)getFirstNonMarkupCompositeDescendant(); }

    /// <summary>
    /// Returns the last <see cref="Row"/> node in the table.
    /// </summary>
    public Row getLastRow() { return (Row)getLastNonMarkupCompositeDescendant(); }

    /// <summary>
    /// Provides typed access to the rows of the table.
    /// </summary>
    public RowCollection getRows()
    {
        if (mRowsCache == null)
            mRowsCache = new RowCollection(this);
        return mRowsCache;
    }

    /// <summary>
    /// Returns true if table has any cell in it.
    /// </summary>
    
    boolean hasCells()
    {
        for (Row row : (Iterable<Row>) getRows())
            for (Cell cell : (Iterable<Cell>) row.getCells())
                return true;

        return false;
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        Table lhs = (Table)super.deepClone(isCloneChildren, cloningListener);
        if (mTablePr != null)
        {
            lhs.mTablePr = AttrCollectionExtension.deepClone(mTablePr);
        }
        lhs.mRowsCache = null;
        return lhs;
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitTableStart"/>, then calls <see cref="Node.Accept"/> for all child nodes of the section
    /// and calls <see cref="DocumentVisitor.VisitTableEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }

    /// <summary>
    /// Accepts a visitor for visiting the start of the table.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitTableStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the table.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitTableEnd(this);
    }

    /// <summary>
    /// If the table has no rows, creates and appends one <see cref="Row"/>.
    /// </summary>
    public void ensureMinimum()
    {
        Row row = getFirstRow();
        if (row == null)
            row = (Row)appendChild(new Row(getDocument()));

        row.ensureMinimum();
    }

    /// <summary>
    /// Gets a cell node using the specified column and row indices.
    /// Does NOT take merged cells into account, just works with nodes.
    /// Returns null if the table doesn't contain such a cell.
    /// </summary>
    Cell getCell(int columnIndex, int rowIndex)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if ((rowIndex < 0) || (rowIndex >= getRows().getCount()))
            	return null;

        	CellCollection cells = getRows().get(rowIndex).getCells();

        	if ((columnIndex < 0) || (columnIndex >= cells.getCount()))
            	return null;

        	return cells.get(columnIndex);
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Returns the maximum number of columns spanned by the cells in a table row.
    /// </summary>
    int getColumnCount()
    {
        int columnCount = 0;

        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
            columnCount = Math.max(columnCount, getColumnCount(row));

        return columnCount;
    }

    private static int getColumnCount(Row row)
    {
        int columnCount = 0;
        for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
            columnCount += cell.getCellPr().getGridSpan();

        return columnCount;
    }

    /// <summary>
    /// Updates the widths of the cells in accordance with preferred widths.
    ///
    /// This is DV's implementation of table layout that was done for old PDF and HTML export,
    /// before the layout engine. At the moment it has no relation to the layout engine.
    /// </summary>
    LayoutWidth updateLayout()
    {
        TableLayouter layouter = new TableLayouter(this);
        return layouter.doLayout();
    }

    /// <summary>
    /// Converts horizontally merged cells into normal cells.
    ///
    /// It is perfectly valid to represent horizontally merged cells using the CellFormat.HorizontalMerge.
    /// But MS Word usually represents horizontally merged cells using normal cells.
    /// </summary>
    void normalizeHorizontalMerge()
    {
        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            Cell cell = row.getFirstCell();
            Cell firstMergedCell = null;
            while (cell != null)
            {
                switch (cell.getCellPr().getHorizontalMerge())
                {
                    case CellMerge.FIRST:
                        // The cell that is first in a series of merged cells, becomes a normal cell
                        // and it will now take on the width of all merged cells.
                        firstMergedCell = cell;
                        firstMergedCell.getCellPr().setHorizontalMerge(CellMerge.NONE);
                        break;
                    case CellMerge.PREVIOUS:
                        if (firstMergedCell != null)
                        {
                            firstMergedCell.getCellPr().setWidth(firstMergedCell.getCellPr().getWidth() + cell.getCellPr().getWidth());
                            firstMergedCell.getCellPr().setGridSpan(firstMergedCell.getCellPr().getGridSpan() + cell.getCellPr().getGridSpan());

                            // WORDSNET-7089 Update fixed preferred width.
                            // AM Maybe we should try to add up cell preferred width for percents as well?
                            // andrnosk: WORDSNET-7654 Do not update preferred width if current cell width equals zero.
                            // This case is possible upon HTML import only.
                            // DMatv: In fact, after WORDSNET-14978 preferred width for the merged cells is ignored by docx/wml reader.
                            if (cell.getCellPr().getWidth() != 0 && firstMergedCell.getCellPr().getPreferredWidth().isFixed() &&
                                cell.getCellPr().getPreferredWidth().isFixed())
                            {
                                int twips = firstMergedCell.getCellPr().getPreferredWidth().getValueTwips() +
                                            cell.getCellPr().getPreferredWidth().getValueTwips();
                                firstMergedCell.getCellPr().setPreferredWidth(PreferredWidth.fromTwipsSafe(twips));
                            }

                            // WORDSNET-7644 Move right border of the last cell in a merge to the result merged cell.
                            if ((cell.getNextCell() == null) ||
                                (cell.getNextCell().getCellPr().getHorizontalMerge() != CellMerge.PREVIOUS))
                            {
                                Object border = cell.getCellPr().getDirectAttr(CellAttr.BORDER_RIGHT);
                                if (border != null)
                                {
                                    // Clone is not needed this cell will be removed.
                                    firstMergedCell.getCellPr().setAttr(CellAttr.BORDER_RIGHT, border);
                                }
                                else
                                {
                                    firstMergedCell.getCellPr().remove(CellAttr.BORDER_RIGHT);
                                }
                            }

                            cell.remove();
                            cell = firstMergedCell;
                        }
                        else
                        {
                            // Invalid case. A cell merged to previous while there was no first.
                            // Let's just reset for resiliency.
                            cell.getCellPr().setHorizontalMerge(CellMerge.NONE);
                        }
                        break;
                    case CellMerge.NONE:
                        firstMergedCell = null;
                        break;
                    default:
                        throw new IllegalStateException("Unknown cell merge type.");
                }
                cell = cell.getNextCell();
            }
        }
    }

    /// <summary>
    /// Check if vertically merged cells are merged properly, i.e. merged cells span the same number of grid columns.
    /// If cells are merged improperly, VerticalMerge will be reset.
    ///
    /// Call this method only after normalizing horizontally merged cells because this can change table grid.
    /// </summary>
    void normalizeVerticalMerge()
    {
        normalizeVerticalMerge(RevisionsView.ORIGINAL);
        if (hasRevision())
            normalizeVerticalMerge(RevisionsView.FINAL);
    }

    private void normalizeVerticalMerge(/*RevisionsView*/int view)
    {
        // Check if there are some rows in this table.
        // If there are no rows, this method has nothing to do.
        if(getFirstRow() == null)
            return;

        TableGridFromCellWidth tableGrid = new TableGridFromCellWidth(this, (view == RevisionsView.FINAL));
        // Avoid to normalize tables with invalid table grid.
        if (tableGrid.getColumnWidths().length == 0)
            return;

        // Due to OOXML specification, if a vertically merged group of cells do not span the same set of grid columns,
        // then this vertical merge is invalid. Such invalid merged cells can corrupt a document or cause problems upon rendering.

        // This hashtable is used to store information about vertically merged cells.
        // This is needed to avoid invalid vertical merge. Key is number of grid columns before vertically merged cell,
        // value is number of grid columns spanned by the cell.
        IntToIntDictionary previousRowMergedRegions = new IntToIntDictionary();

        // In this hashtable we will store cells, which are vertically merged.
        // Key is number of grid columns before vertically merged region, value is actually Cell.
        // This is needed to reset VerticalMerge, if it is necessary.
        IntToObjDictionary<Cell> previousRowMergedCells = new IntToObjDictionary<Cell>();

        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            tableGrid.nextRow(row);

            IntToIntDictionary currentRowMergedRegions = new IntToIntDictionary();
            IntToObjDictionary<Cell> currentRowMergedCells = new IntToObjDictionary<Cell>();

            // This value represents current grid position, it is used as a key in MergedRegions and MergedCells tables.
            // Since rows can be indented differently, currentGridPosition not always starts from zero.
            int currentGridPosition = tableGrid.getGridBefore();
            for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
            {
                int gridSpan = tableGrid.nextCell();

                // For further processing we are interested only in vertically merged cells.
                if (getVerticalMerge(cell, view) != CellMerge.NONE)
                {
                    int previousGridSpan = previousRowMergedRegions.get(currentGridPosition);

                    if (IntToIntDictionary.isNullSubstitute(previousGridSpan))
                        previousGridSpan = cell.getCellPr().getGridSpan();

                    // WORDSNET-19247 Use grid span of previous merged cell to compare with current cell "GridSpan" property.
                    int prevMergedGridSpan = previousRowMergedCells.containsKey(currentGridPosition) ?
                        previousRowMergedCells.get(currentGridPosition).getCellPr().getGridSpan() : previousGridSpan;

                    // Merged regions should start at the same grid position and span the same number of grid columns.
                    // Otherwise consider the cell as the first in a vertically merged region.
                    // WORDSNET-16216 gridSpan can be equal 0 if this cell is merged in previous cell,
                    // in this case compare with cells GridSpan property, it has right value.
                    // WORDSNET-23156 The Word allows vertical merge when the grid span of an upper cell greater than current one.
                    if ((previousGridSpan < gridSpan) && (prevMergedGridSpan < cell.getCellPr().getGridSpan()))
                    {
                        cell.getCellPr().setAttrRevisionAware(CellAttr.VERTICAL_MERGE, CellMerge.FIRST, view);
                    }

                    // WORDSNET-8818 If it is a one-column row without RowHeightRule, we should specify RowHeightRule.
                    // WORDSNET-25744 The explicitly set HeightRule.Auto shall also be processed.
                    boolean heightRuleNeedsSpecifying = (row.getTablePr().getHeightRule() == HeightRule.AUTO) && (row.getCells().getCount() == 1);
                    if (heightRuleNeedsSpecifying)
                        specifyRowHeightRule(row);

                    currentRowMergedRegions.set(currentGridPosition, gridSpan);
                    currentRowMergedCells.set(currentGridPosition, cell);
                }
                // There can be not normalized horizontal merge (for example if we save to DOC format).
                // So consider merge flag when advance grid position.
                currentGridPosition += cell.getCellPr().getGridSpan();
            }

            // There could be cells which are vertically merged to previous but there are no cells merged in previous row.
            // Make them first in merge.
            IntToObjDictionary<Cell>.Enumerator enumerator = currentRowMergedCells.iterator();
            while (enumerator.moveNext())
            {
                Cell currentCell = enumerator.getCurrentValue();
                if ((getVerticalMerge(currentCell, view) == CellMerge.PREVIOUS) && (previousRowMergedCells.get(enumerator.getCurrentKey()) == null))
                {
                    currentCell.getCellPr().setAttrRevisionAware(CellAttr.VERTICAL_MERGE, CellMerge.FIRST, view);
                }
            }

            // There could be cells, which are the first cells in the vertically merged region,
            // but there are no cells merged with them in the next row. In such case we can reset VerticalMerge.
            enumerator = previousRowMergedCells.iterator();
            while (enumerator.moveNext())
            {
                Cell previousCell = enumerator.getCurrentValue();
                if ((getVerticalMerge(previousCell, view) == CellMerge.FIRST) && (currentRowMergedCells.get(enumerator.getCurrentKey()) == null))
                {
                    previousCell.getCellPr().setAttrRevisionAware(CellAttr.VERTICAL_MERGE, CellMerge.NONE, view);
                }
            }

            previousRowMergedRegions = currentRowMergedRegions;
            previousRowMergedCells = currentRowMergedCells;
        }

        // Resiliency: Cells in the last row should not be first vertically merged.
        for (Cell cell = getLastRow().getFirstCell(); cell != null; cell = cell.getNextCell())
        {
            if (getVerticalMerge(cell, view) == CellMerge.FIRST)
                cell.getCellPr().setAttrRevisionAware(CellAttr.VERTICAL_MERGE, CellMerge.NONE, view);
        }
    }

    /// <summary>
    /// Fixes nested table layout.
    /// </summary>
    void fixNestedTableLayout() throws Exception
    {
        // Nowhere to set the attribute if the first row is null.
        if (!isNested() || (getFirstRow() == null))
            return;

        fixNestedTableAutoFit();

        // WORDSNET-19655 Nested tables cannot have negative indents.
        fixNestedTableNegativeLefIndent();
    }

    /// <summary>
    /// Sets Autofit value of the nested table to the value of the containing table.
    /// </summary>
    /// <remarks>
    /// MS Word does not seem to allow fixed table layout in autofit tables and vice versa.
    /// </remarks>
    private void fixNestedTableAutoFit() throws Exception
    {
        Table containingTable = (Table)getAncestor(NodeType.TABLE);
        boolean parentAutoFit = containingTable.getAllowAutoFit();
        if (parentAutoFit != getAllowAutoFit())
        {
            // Dmatv: An UpdateLayout() call that was added per 13636 but caused 19798 was removed when working on WORDSNET-23611
            setAllowAutoFit(parentAutoFit);
        }
    }

    /// <summary>
    /// Removes the LefIndent value of the nested table if negative.
    /// </summary>
    /// <remarks>
    /// MS Word overwrites negative nested indents on re-saving.
    /// </remarks>
    private void fixNestedTableNegativeLefIndent()
    {
        if (getLeftIndent() >= 0)
            return;

        getTablePr().remove(TableAttr.LEFT_INDENT);

        for (Row row : (Iterable<Row>) getRows())
            row.getTablePr().remove(TableAttr.LEFT_INDENT);
    }

    /// <summary>
    /// Gets vertical merge value depending on revision view.
    /// </summary>
    private static /*CellMerge*/int getVerticalMerge(Cell cell, /*RevisionsView*/int view)
    {
        return (/*CellMerge*/Integer)cell.getCellPr().fetchAttr(CellAttr.VERTICAL_MERGE, view);
    }

    /// <summary>
    /// Specifies RowHeightRule and RowHeight, according to the last ParagraphBreakFont.Size.
    /// </summary>
    /// <remarks>
    /// Mimic MSW. Used for vertically merged one-column rows.
    /// </remarks>
    private static void specifyRowHeightRule(Row row)
    {
        // MSW uses this value as a RowHeight when cells have no child nodes.
        int rowHeight = 276;

        if (row.getFirstCell().getLastParagraph() != null)
        {
            Paragraph para = row.getFirstCell().getLastParagraph();

            ParaPr expParaPr = para.getExpandedParaPr(ParaPrExpandFlags.LAYOUT);
            RunPr expParaBreakRunPr = para.getExpandedParagraphBreakRunPr(RunPrExpandFlags.LAYOUT);

            int lineSpacing = expParaPr.getLineSpacing();
            int fontSize = expParaBreakRunPr.getSize();

            // AM. This is the best approximation I've got while test this issue.
            //
            // Magic value actually reflects some TTF metric, according to tests it slightly differs
            // depending on font name.
            //
            // Courier New     ~ 21.1
            // Times New Roman ~ 20.8
            //
            // Lets take some average.
            final double MAGIC = 21.0;

            rowHeight = (int)(msMath.round(lineSpacing / MAGIC, 1) * fontSize);
        }

        row.getTablePr().setAttr(TableAttr.ROW_HEIGHT, new Height(HeightRule.AT_LEAST, rowHeight));
    }

    /// <summary>
    /// Gets the number of rows at the top of the table that must be repeated on each page as header rows.
    /// </summary>
    int getHeaderRowCount()
    {
        int result = 0;
        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            if (row.getTablePr().getHeadingFormat())
                result++;
            else
                break;
        }
        return result;
    }

    /// <summary>
    /// Gets the left side of an inline table according to the MS Word algorithm.
    /// </summary>
    int getTableLeft()
    {
        Row row = getFirstRow();
        if (row == null)
            return 0;

        CellPr firstCellPr = row.getFirstCell().getCellPr();
        return row.getTablePr().getTableLeft(firstCellPr, isNested());
    }

    /// <summary>
    /// Allows to insert row-level elements only.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        return NodeUtil.isRowLevelNode(newChild);
    }

    /// <summary>
    /// Returns true if this table is contained inside a table cell and therefore a nested table.
    /// </summary>
    boolean isNested()
    {
        // WORDSNET-9691 If this table was created by node cloning, then it's parent is null
        // and access to its parent is not allowed.
        // Also, if table has no parent, then we should treat it as not nested.
        if (getFirstNonMarkupParentNode() == null)
            return false;

        return (getFirstNonMarkupParentNode().getNodeType() == NodeType.CELL);
    }

    /// <summary>
    /// Finds row with different positioning or style.
    /// </summary>
    Row findDifferentPositioningOrStyle()
    {
        Debug.assertTrue(getParentNode() != null, "The table must already be added to the document.");

        // WORDSNET-12534 Word ignores floating inside textbox.
        if (getAncestor(NodeType.SHAPE) != null)
            return null;

        Row prevRow = null;
        Row curRow = getFirstRow();
        while (curRow != null)
        {
            if (prevRow != null)
                if ((prevRow.getTablePr().getIstd() != curRow.getTablePr().getIstd()) || !prevRow.isSameRowFloatingPositioning(curRow))
                    return curRow;

            prevRow = curRow;
            curRow = curRow.getNextRow();
        }

        return null;
    }

    /// <summary>
    /// Makes the table floating if it is not already so.
    /// </summary>
    private void makeTableFloatingWhenSettingDistance() throws Exception
    {
        if (isFloating())
            return;

        // MS Word sets these properties when setting the DistanceXXX properties in VBA if a table is not floating yet.
        // This makes the table floating.
        setVerticalAnchor(RelativeVerticalPosition.PARAGRAPH);
        setAbsoluteVerticalDistance(0.0);
    }

    /// <summary>
    /// Specifies how an inline table is aligned in the document.
    /// </summary>
    /// <remarks>
    /// <para>The default value is <see cref="TableAlignment.Left"/>.</para>
    /// </remarks>
    public /*TableAlignment*/int getAlignment() { return (/*TableAlignment*/Integer)fetchFirstRowAttr(TableAttr.ALIGNMENT); }
    public void setAlignment(/*TableAlignment*/int value) throws Exception { setAttrOnAllRows(TableAttr.ALIGNMENT, value); }

    /// <summary>
    /// Allows Microsoft Word and Aspose.Words to automatically resize cells in a table to fit their contents.
    /// </summary>
    /// <remarks>
    /// <para>The default value is <c>true</c>.</para>
    ///
    /// <seealso cref="AutoFit"/>
    /// </remarks>
    public boolean getAllowAutoFit() { return (/*boolean*/Boolean)fetchFirstRowAttr(TableAttr.ALLOW_AUTO_FIT); }
    public void setAllowAutoFit(boolean value) throws Exception
    {
        if (value != getAllowAutoFit())
        {
            // WORDSNET-16899 Reset the calculated grid on changing table layout.
            removeCalculatedGrid();
        }

        setAttrOnAllRows(TableAttr.ALLOW_AUTO_FIT, value);
    }


    /// <summary>
    /// Gets or sets the table preferred width.
    /// </summary>
    /// <remarks>
    /// <para>The default value is <see cref="Aspose.Words.Tables.PreferredWidth.Auto"/>.</para>
    /// </remarks>
    public PreferredWidth getPreferredWidth() { return (PreferredWidth)fetchFirstRowAttr(TableAttr.PREFERRED_WIDTH); }
    public void setPreferredWidth(PreferredWidth value) throws Exception { setAttrOnAllRows(TableAttr.PREFERRED_WIDTH, value); }

    /// <summary>
    /// Gets or sets whether this is a right-to-left table.
    /// </summary>
    /// <remarks>
    /// <p>When <c>true</c>, the cells in this row are laid out right to left.</p>
    /// <para>The default value is <c>false</c>.</para>
    /// </remarks>
    public boolean getBidi() { return (/*boolean*/Boolean)fetchFirstRowAttr(TableAttr.BIDI); }
    public void setBidi(boolean value) throws Exception { setAttrOnAllRows(TableAttr.BIDI, value); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add to the left of the contents of cells.
    /// </summary>
    public double getLeftPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.LEFT_PADDING)); }
    public void setLeftPadding(double value) throws Exception { setAttrOnAllRows(TableAttr.LEFT_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add to the right of the contents of cells.
    /// </summary>
    public double getRightPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.RIGHT_PADDING)); }
    public void setRightPadding(double value) throws Exception { setAttrOnAllRows(TableAttr.RIGHT_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add above the contents of cells.
    /// </summary>
    public double getTopPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.TOP_PADDING)); }
    public void setTopPadding(double value) throws Exception { setAttrOnAllRows(TableAttr.TOP_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add below the contents of cells.
    /// </summary>
    public double getBottomPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.BOTTOM_PADDING)); }
    public void setBottomPadding(double value) throws Exception { setAttrOnAllRows(TableAttr.BOTTOM_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) between the cells.
    /// </summary>
    public double getCellSpacing() { return ((PreferredWidth)fetchFirstRowAttr(TableAttr.CELL_SPACING)).getValue(); }
    public void setCellSpacing(double value) throws Exception { setAttrOnAllRows(TableAttr.CELL_SPACING, PreferredWidth.fromPointsSafe(value)); }

    /// <summary>
    /// Gets or sets the "Allow spacing between cells" option.
    /// </summary>
    public boolean getAllowCellSpacing()
    {
        // TODO it is actually not correct to define this at a table level
        // as different rows may have different spacing settings.
        return getFirstRow().hasCellSpacing();
    }
    public void setAllowCellSpacing(boolean value) throws Exception
    {
        if (value)
        {
            // Mimic MSW. Assign initial value if CellSpacing was not defined.
            if (!getAllowCellSpacing())
                setAttrOnAllRows(TableAttr.CELL_SPACING,
                    PreferredWidth.fromTwipsSafe(INITIAL_CELL_SPACING));
        }
        else
        {
            removeAttrFromAllRows(TableAttr.CELL_SPACING);
        }
    }

    /// <summary>
    /// Gets or sets the value that represents the left indent of the table.
    /// </summary>
    public double getLeftIndent() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.LEFT_INDENT)); }
    public void setLeftIndent(double value) throws Exception
    {
        // WORDSNET-19655 Nested tables cannot have negative indents.
        if (isNested() && (value < 0))
            return;

        setAttrOnAllRows(TableAttr.LEFT_INDENT, ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets or sets bit flags that specify how a table style is applied to this table.
    /// </summary>
    public /*TableStyleOptions*/int getStyleOptions() { return (/*TableStyleOptions*/Integer)fetchFirstRowAttr(TableAttr.STYLE_OPTIONS); }
    public void setStyleOptions(/*TableStyleOptions*/int value) throws Exception { setAttrOnAllRows(TableAttr.STYLE_OPTIONS, value); }

    /// <summary>
    /// Gets or sets the table style applied to this table.
    /// </summary>
    public Style getStyle() { return getDocument().getStyles().fetchByIstd(getIstd(), StyleIndex.TABLE_NORMAL); }
    public void setStyle(Style value) throws Exception
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        if (value.getDocument() != this.getDocument())
            throw new IllegalArgumentException("This style belongs to a different document.");

        if (value.getType() != StyleType.TABLE)
            throw new IllegalArgumentException("This style is not a table style.");

        setIstd(value.getIstd());
    }

    /// <summary>
    /// Gets or sets the name of the table style applied to this table.
    /// </summary>
    public String getStyleName() { return getStyle().getName(); }
    public void setStyleName(String value) throws Exception { setStyle(getDocument().getStyles().fetchByName(value)); }

    /// <summary>
    /// Gets or sets the locale independent style identifier of the table style applied to this table.
    /// </summary>
    public /*StyleIdentifier*/int getStyleIdentifier() { return getStyle().getStyleIdentifier(); }
    public void setStyleIdentifier(/*StyleIdentifier*/int value) throws Exception { setStyle(getDocument().getStyles().fetchBySti(value)); }

    /// <summary>
    /// Gets or sets <see cref="TextWrapping" /> for table.
    /// </summary>
    public /*TextWrapping*/int getTextWrapping()
    {
        // AM. I think it enough to just check whether table floating or not.
        return isFloating() ? TextWrapping.AROUND : TextWrapping.NONE;
    }

    public void setTextWrapping(/*TextWrapping*/int value) throws Exception
    {
        if (value == TextWrapping.AROUND)
            setTextWrapping();
        else
            removeTextWrapping();
    }

    /// <summary>
    /// Gets or sets title of this table.
    /// It provides an alternative text representation of the information contained in the table.
    /// </summary>
    /// <remarks>
    /// <para>The default value is an empty string.</para>
    /// <para>This property is meaningful for ISO/IEC 29500 compliant DOCX documents
    /// (<see cref="Saving.OoxmlCompliance"/>).
    /// When saved to pre-ISO/IEC 29500 formats, the property is ignored.</para>
    /// </remarks>
    public String getTitle() { return (String)fetchFirstRowAttr(TableAttr.TITLE); }
    public void setTitle(String value) throws Exception
    {
        ArgumentUtil.checkNotNull(value, "value");

        setAttrOnAllRows(TableAttr.TITLE, value);
    }

    /// <summary>
    /// Gets or sets description of this table.
    /// It provides an alternative text representation of the information contained in the table.
    /// </summary>
    /// <remarks>
    /// <para>The default value is an empty string.</para>
    /// <para>This property is meaningful for ISO/IEC 29500 compliant DOCX documents
    /// (<see cref="Saving.OoxmlCompliance"/>).
    /// When saved to pre-ISO/IEC 29500 formats, the property is ignored.</para>
    /// </remarks>
    public String getDescription() { return (String)fetchFirstRowAttr(TableAttr.DESCRIPTION); }
    public void setDescription(String value) throws Exception
    {
        ArgumentUtil.checkNotNull(value, "value");

        setAttrOnAllRows(TableAttr.DESCRIPTION, value);
    }

    /// <summary>
    /// Gets or sets distance between table left and the surrounding text, in points.
    /// </summary>
    public double getDistanceLeft() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.FRAME_DISTANCE_FROM_LEFT)); }
    public void setDistanceLeft(double value) throws Exception
    {
        ArgumentUtil.checkNonNegative(value, "value");

        setAttrOnAllRows(TableAttr.FRAME_DISTANCE_FROM_LEFT, ConvertUtilCore.pointToTwip(value));

        makeTableFloatingWhenSettingDistance();
    }

    /// <summary>
    /// Gets or sets distance between table right and the surrounding text, in points.
    /// </summary>
    public double getDistanceRight() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.FRAME_DISTANCE_FROM_RIGHT)); }
    public void setDistanceRight(double value) throws Exception
    {
        ArgumentUtil.checkNonNegative(value, "value");

        setAttrOnAllRows(TableAttr.FRAME_DISTANCE_FROM_RIGHT, ConvertUtilCore.pointToTwip(value));

        makeTableFloatingWhenSettingDistance();
    }

    /// <summary>
    /// Gets or sets distance between table top and the surrounding text, in points.
    /// </summary>
    public double getDistanceTop() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.FRAME_DISTANCE_FROM_TOP)); }
    public void setDistanceTop(double value) throws Exception
    {
        ArgumentUtil.checkNonNegative(value, "value");

        setAttrOnAllRows(TableAttr.FRAME_DISTANCE_FROM_TOP, ConvertUtilCore.pointToTwip(value));

        makeTableFloatingWhenSettingDistance();
    }

    /// <summary>
    /// Gets or sets distance between table bottom and the surrounding text, in points.
    /// </summary>
    public double getDistanceBottom() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.FRAME_DISTANCE_FROM_BOTTOM)); }
    public void setDistanceBottom(double value) throws Exception
    {
        ArgumentUtil.checkNonNegative(value, "value");

        setAttrOnAllRows(TableAttr.FRAME_DISTANCE_FROM_BOTTOM, ConvertUtilCore.pointToTwip(value));

        makeTableFloatingWhenSettingDistance();
    }

    /// <summary>
    /// Gets or sets floating table relative horizontal alignment.
    /// </summary>
    public /*HorizontalAlignment*/int getRelativeHorizontalAlignment() { return (/*HorizontalAlignment*/Integer)fetchFirstRowAttr(TableAttr.HORIZONTAL_ALIGNMENT); }
    public void setRelativeHorizontalAlignment(/*HorizontalAlignment*/int value) throws Exception
    {
        removeAttrFromAllRows(TableAttr.FRAME_LEFT);
        setAttrOnAllRows(TableAttr.HORIZONTAL_ALIGNMENT, value);
    }

    /// <summary>
    /// Gets or sets floating table relative vertical alignment.
    /// </summary>
    public /*VerticalAlignment*/int getRelativeVerticalAlignment() { return (/*VerticalAlignment*/Integer)fetchFirstRowAttr(TableAttr.VERTICAL_ALIGNMENT); }
    public void setRelativeVerticalAlignment(/*VerticalAlignment*/int value) throws Exception
    {
        removeAttrFromAllRows(TableAttr.FRAME_TOP);
        setAttrOnAllRows(TableAttr.VERTICAL_ALIGNMENT, value);
    }

    /// <summary>
    /// Gets the base object from which the horizontal positioning of floating table should be calculated.
    /// Default value is <see cref="RelativeHorizontalPosition.Column"/>.
    /// </summary>
    public /*RelativeHorizontalPosition*/int getHorizontalAnchor() { return (/*RelativeHorizontalPosition*/Integer)fetchFirstRowAttr(TableAttr.RELATIVE_HORIZONTAL_POSITION); }
    public void setHorizontalAnchor(/*RelativeHorizontalPosition*/int value) throws Exception
    {
        switch (value)
        {
            case RelativeHorizontalPosition.MARGIN:
            case RelativeHorizontalPosition.PAGE:
            case RelativeHorizontalPosition.COLUMN:
                // Allowed values.
                break;

            default:
                throw new IllegalArgumentException("Parameter value outside of acceptable range." + "\r\nParameter name: " + "HorizontalAnchor");
        }

        setAttrOnAllRows(TableAttr.RELATIVE_HORIZONTAL_POSITION, value);
        // It is interesting that Word sets vertical position to force floating when horizontal anchor set.
        setAttrOnAllRowsNoOverride(TableAttr.FRAME_TOP, 0);
        setAttrOnAllRowsNoOverride(TableAttr.RELATIVE_VERTICAL_POSITION, RelativeVerticalPosition.PARAGRAPH);
        setAttrOnAllRowsNoOverride(TableAttr.FRAME_DISTANCE_FROM_LEFT, 180);
        setAttrOnAllRowsNoOverride(TableAttr.FRAME_DISTANCE_FROM_RIGHT, 180);
    }

    /// <summary>
    /// Gets the base object from which the vertical positioning of floating table should be calculated.
    /// Default value is <see cref="RelativeVerticalPosition.Margin"/>.
    /// </summary>
    public /*RelativeVerticalPosition*/int getVerticalAnchor() { return (/*RelativeVerticalPosition*/Integer)fetchFirstRowAttr(TableAttr.RELATIVE_VERTICAL_POSITION); }
    public void setVerticalAnchor(/*RelativeVerticalPosition*/int value) throws Exception
    {
        switch (value)
        {
            case RelativeVerticalPosition.MARGIN:
            case RelativeVerticalPosition.PAGE:
            case RelativeVerticalPosition.PARAGRAPH:
                // Allowed values.
                break;

            default:
                throw new IllegalArgumentException("Parameter value outside of acceptable range." + "\r\nParameter name: " + "VerticalAnchor");
        }

        setAttrOnAllRows(TableAttr.RELATIVE_VERTICAL_POSITION, value);
        setAttrOnAllRowsNoOverride(TableAttr.FRAME_TOP, 0);
        setAttrOnAllRowsNoOverride(TableAttr.FRAME_DISTANCE_FROM_LEFT, 180);
        setAttrOnAllRowsNoOverride(TableAttr.FRAME_DISTANCE_FROM_RIGHT, 180);
    }

    /// <summary>
    /// Gets or sets absolute horizontal floating table position specified by the table properties, in points.
    /// Default value is 0.
    /// </summary>
    public double getAbsoluteHorizontalDistance() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.FRAME_LEFT)); }
    public void setAbsoluteHorizontalDistance(double value) throws Exception
    {
        removeAttrFromAllRows(TableAttr.HORIZONTAL_ALIGNMENT);
        setAttrOnAllRows(TableAttr.FRAME_LEFT, ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets or sets absolute vertical floating table position specified by the table properties, in points.
    /// Default value is 0.
    /// </summary>
    public double getAbsoluteVerticalDistance() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchFirstRowAttr(TableAttr.FRAME_TOP)); }
    public void setAbsoluteVerticalDistance(double value) throws Exception
    {
        removeAttrFromAllRows(TableAttr.VERTICAL_ALIGNMENT);
        setAttrOnAllRows(TableAttr.FRAME_TOP, ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets whether a floating table shall allow other floating objects in the document
    /// to overlap its extents when displayed.
    /// Default value is <c>true</c>.
    /// </summary>
    public boolean getAllowOverlap() { return (/*boolean*/Boolean)fetchFirstRowAttr(TableAttr.ALLOW_OVERLAP); }

    /// <summary>
    /// Performs no checks on set.
    /// </summary>
    int getIstd() { return (/*int*/Integer)fetchFirstRowAttr(TableAttr.ISTD); }
    void setIstd(int value) throws Exception { setAttrOnAllRows(TableAttr.ISTD, value); }

    /// <summary>
    /// Indicates that the table is floating table.
    /// </summary>
    boolean isFloating() { return (getRows().getCount() > 0) && getRows().get(0).getTablePr().isFloating(); }

    /// <summary>
    /// Indicates if <see cref="TablePr.CalculatedGrid"/> is applied to the table.
    /// </summary>
    boolean isCalculatedGridApplied() { return TableGridAttr.isApplied(getTablePr().getCalculatedGrid()); }

    /// <summary>
    /// Holds table related properties. Currently used only to hold table grid.
    /// </summary>
    /// <remarks>
    /// See ImportDocx\TestJira6362() for design notes.
    /// </remarks>
    TablePr getTablePr()
    {
        // Instantiate at first access.
        if(mTablePr == null)
            mTablePr = new TablePr();

        return mTablePr;
    }

    /// <summary>
    /// Sets all table borders to the specified line style, width and color.
    /// </summary>
    /// <param name="lineStyle">The line style to apply.</param>
    /// <param name="lineWidth">The line width to set (in points).</param>
    /// <param name="color">The color to use for the border.</param>
    public void setBorders(/*LineStyle*/int lineStyle, double lineWidth, Color color) throws Exception
    {
        for (/*BorderType*/int borderType : (Iterable</*BorderType*/Integer>) TablePr.POSSIBLE_BORDER_KEYS.getKeys())
            setBorder(borderType, lineStyle, lineWidth, color, true);
    }

    /// <summary>
    /// Sets the specified table border to the specified line style, width and color.
    /// </summary>
    /// <param name="borderType">The table border to change.</param>
    /// <param name="lineStyle">The line style to apply.</param>
    /// <param name="lineWidth">The line width to set (in points).</param>
    /// <param name="color">The color to use for the border.</param>
    /// <param name="isOverrideCellBorders">When <c>true</c>, causes all existing explicit cell borders to be removed.</param>
    public void setBorder(/*BorderType*/int borderType, /*LineStyle*/int lineStyle, double lineWidth, Color color, boolean isOverrideCellBorders) throws Exception
    {
        // Clear cell borders that are specified on cells explicitly because we want this method to "override" any cell borders.
        if (isOverrideCellBorders)
            removeCellBorders(borderType);

        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            // Now set the table border (we do this on the row level).
            Border border = row.getRowFormat().getBorders().getByBorderType(borderType);
            border.setLineStyle(lineStyle);
            border.setLineWidth(lineWidth);
            border.setColor(color);
        }
    }

    /// <summary>
    /// Removes all table and cell borders on this table.
    /// </summary>
    public void clearBorders()
    {
        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            row.getTablePr().removeBorders();

            for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
                cell.getCellPr().removeBorders();
        }
    }

    private void removeTextWrapping() throws Exception
    {
        TablePr firstRowPr = getFirstRow().getTablePr();

        // Convert HorizontalAlignment to TableAlignment.
        /*HorizontalAlignment*/int alignment = (/*HorizontalAlignment*/Integer)firstRowPr.fetchAttr(TableAttr.HORIZONTAL_ALIGNMENT);

        if (alignment == HorizontalAlignment.RIGHT)
            setAttrOnAllRows(TableAttr.ALIGNMENT, TableAlignment.RIGHT);
        else if (alignment == HorizontalAlignment.CENTER)
            setAttrOnAllRows(TableAttr.ALIGNMENT, TableAlignment.CENTER);

        // FrameLeft doesn't go to LeftIndent.

        removeAttrFromAllRows(TableAttr.FRAME_LEFT);
        removeAttrFromAllRows(TableAttr.RELATIVE_VERTICAL_POSITION);
        removeAttrFromAllRows(TableAttr.RELATIVE_HORIZONTAL_POSITION);
        removeAttrFromAllRows(TableAttr.FRAME_TOP);
        removeAttrFromAllRows(TableAttr.FRAME_DISTANCE_FROM_LEFT);
        removeAttrFromAllRows(TableAttr.FRAME_DISTANCE_FROM_RIGHT);
        removeAttrFromAllRows(TableAttr.ALLOW_OVERLAP);
        removeAttrFromAllRows(TableAttr.HORIZONTAL_ALIGNMENT);
        removeAttrFromAllRows(TableAttr.VERTICAL_ALIGNMENT);
    }

    private void setTextWrapping() throws Exception
    {
        TablePr firstRowPr = getFirstRow().getTablePr();

        // It seems Word always set below floating attributes.
        setAttrOnAllRows(TableAttr.RELATIVE_VERTICAL_POSITION, RelativeVerticalPosition.TEXT_FRAME_DEFAULT);
        setAttrOnAllRows(TableAttr.FRAME_TOP, 0);
        setAttrOnAllRows(TableAttr.FRAME_DISTANCE_FROM_LEFT, 180);
        setAttrOnAllRows(TableAttr.FRAME_DISTANCE_FROM_RIGHT, 180);
        setAttrOnAllRows(TableAttr.ALLOW_OVERLAP, false);

        // Convert TableAligment to HorizontalAlignment.
        /*TableAlignment*/int alignment = (/*TableAlignment*/Integer)firstRowPr.fetchAttr(TableAttr.ALIGNMENT);

        if (alignment == TableAlignment.RIGHT)
            setAttrOnAllRows(TableAttr.HORIZONTAL_ALIGNMENT, HorizontalAlignment.RIGHT);
        else if (alignment == TableAlignment.CENTER)
            setAttrOnAllRows(TableAttr.HORIZONTAL_ALIGNMENT, HorizontalAlignment.CENTER);
        removeAttrFromAllRows(TableAttr.ALIGNMENT);

        // Convert LeftIndent to FrameLeft.
        if (firstRowPr.contains(TableAttr.LEFT_INDENT))
        {
            int leftIndent = (/*int*/Integer)firstRowPr.getDirectAttr(TableAttr.LEFT_INDENT);
            setAttrOnAllRows(TableAttr.FRAME_LEFT, leftIndent - 1); // +1 hack.
        }
        removeAttrFromAllRows(TableAttr.LEFT_INDENT);
    }

    private void removeCellBorders(/*BorderType*/int borderType)
    {
        switch (borderType)
        {
            case BorderType.TOP:
            {
                // Remove top borders from the cells in the first row.
                Row row = getFirstRow();
                if (row != null)
                {
                    for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
                        cell.getCellPr().remove(CellAttr.BORDER_TOP);
                }
                break;
            }
            case BorderType.BOTTOM:
            {
                // Remove bottom borders from the cells in the last row.
                Row row = getLastRow();
                if (row != null)
                {
                    for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
                        cell.getCellPr().remove(CellAttr.BORDER_BOTTOM);
                }
                break;
            }
            case BorderType.LEFT:
            {
                // Remove left border from the first cell in each row.
                for (Row row = getFirstRow(); row != null; row = row.getNextRow())
                {
                    Cell cell = row.getFirstCell();
                    if (cell != null)
                        cell.getCellPr().remove(CellAttr.BORDER_LEFT);
                }
                break;
            }
            case BorderType.RIGHT:
            {
                // Remove right border from the last cell in each row.
                for (Row row = getFirstRow(); row != null; row = row.getNextRow())
                {
                    Cell cell = row.getLastCell();
                    if (cell != null)
                        cell.getCellPr().remove(CellAttr.BORDER_RIGHT);
                }
                break;
            }
            case BorderType.HORIZONTAL:
            {
                // Removing horizontal table border means we need to remove all top and bottom cell borders except cases for the first and last row.
                for (Row row = getFirstRow(); row != null; row = row.getNextRow())
                {
                    boolean isRemoveTop = !row.isFirstRow();
                    boolean isRemoveBottom = !row.isLastRow();

                    for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
                    {
                        if (isRemoveTop)
                            cell.getCellPr().remove(CellAttr.BORDER_TOP);

                        if (isRemoveBottom)
                            cell.getCellPr().remove(CellAttr.BORDER_BOTTOM);
                    }
                }
                break;
            }
            case BorderType.VERTICAL:
            {
                // Removing vertical table border means we need to remove all left and right cell borders except cases for the first and last cell.
                for (Row row = getFirstRow(); row != null; row = row.getNextRow())
                {
                    for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
                    {
                        boolean isRemoveLeft = !cell.isFirstCell();
                        boolean isRemoveRight = !cell.isLastCell();

                        if (isRemoveLeft)
                            cell.getCellPr().remove(CellAttr.BORDER_LEFT);

                        if (isRemoveRight)
                            cell.getCellPr().remove(CellAttr.BORDER_RIGHT);
                    }
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected border type.");
        }
    }

    /// <summary>
    /// Sets shading to the specified values on whole table.
    /// </summary>
    /// <param name="texture">The texture to apply.</param>
    /// <param name="foregroundColor">The color of the texture.</param>
    /// <param name="backgroundColor">The color of the background fill.</param>
    public void setShading(/*TextureIndex*/int texture, Color foregroundColor, Color backgroundColor)
    {
        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            Shading rowShading = row.getTablePr().getShading();
            if (rowShading == null)
            {
                rowShading = new Shading();
                row.getTablePr().setShading(rowShading);
            }

            rowShading.setTexture(texture);
            rowShading.setForegroundPatternColor(foregroundColor);
            rowShading.setBackgroundPatternColor(backgroundColor);

            for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
            {
                Shading shading = cell.getCellFormat().getShading();
                shading.setTexture(texture);
                shading.setForegroundPatternColor(foregroundColor);
                shading.setBackgroundPatternColor(backgroundColor);
            }
        }
    }

    /// <summary>
    /// Removes all shading on the table.
    /// </summary>
    public void clearShading()
    {
        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            row.getTablePr().removeShading();

            for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
                cell.getCellPr().removeShading();
        }
    }

    /// <summary>
    /// Resizes the table and cells according to the specified auto fit behavior.
    /// </summary>
    /// <remarks>
    /// <para>This method mimics the commands available in the Auto Fit menu for a table in Microsoft Word.
    /// The commands available are "Auto Fit to Contents", "Auto Fit to Window" and "Fixed Column Width". In Microsoft Word
    /// these commands set relevant table properties and then update the table layout and Aspose.Words does the same for you.</para>
    /// </remarks>
    /// <param name="behavior">Specifies how to auto fit the table.</param>
    public void autoFit(/*AutoFitBehavior*/int behavior) throws Exception
    {
        switch (behavior)
        {
            case AutoFitBehavior.AUTO_FIT_TO_CONTENTS:
                autoFitToContents();
                break;
            case AutoFitBehavior.AUTO_FIT_TO_WINDOW:
                autoFitToWindow();
                break;
            case AutoFitBehavior.FIXED_COLUMN_WIDTHS:
                autoFitFixedColumnWidths();
                break;
            default:
                throw new IllegalArgumentException("Unknown auto fit behavior.");
        }
    }

    /// <summary>
    /// Calculates sum of cells width.
    /// </summary>
    int getCellsWidth()
    {
        if (getRows().getCount() == 0)
            return 0;

        int maxWidth = 0;
        for (Row row : (Iterable<Row>) getRows())
        {
            int width = 0;
            for (Cell cell : (Iterable<Cell>) row.getCells())
                width += cell.getCellPr().getWidth();

            maxWidth = Math.max(maxWidth, width);
        }

        return maxWidth;
    }

    boolean hasRevision()
    {
        for (Row row : (Iterable<Row>) getRows())
        {
            if (row.getTablePr().hasRevisions())
                return true;

            for(Cell cell : (Iterable<Cell>) row.getCells())
                if (cell.getCellPr().hasRevisions())
                    return true;
        }

        return false;
    }

    void removeAttrFromAllRows(int key)
    {
        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
            row.getTablePr().remove(key);
    }

    void setAttrOnAllRows(int key, Object value) throws Exception
    {
        if (getFirstRow() == null)
            throw new IllegalStateException("Formatting cannot be applied because the table is empty. Add at least one row to the table first.");

        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            if (key == TableAttr.ISTD)
                prepareRowBeforeSetIstd(row);

            row.getTablePr().setAttr(key, value);
        }
    }

    void setAttrOnAllRowsNoOverride(int key, Object value) throws Exception
    {
        if (getFirstRow() == null)
            throw new IllegalStateException("Formatting cannot be applied because the table is empty. Add at least one row to the table first.");

        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            if (key == TableAttr.ISTD)
                prepareRowBeforeSetIstd(row);

            if(!row.getTablePr().containsKey(key))
                row.getTablePr().setAttr(key, value);
        }
    }

    private void autoFitToContents() throws Exception
    {
        setAllowAutoFit(true);
        removeAttrFromAllRows(TableAttr.PREFERRED_WIDTH);
        removeAttrFromAllCells(CellAttr.PREFERRED_WIDTH);
        updateLayout();
    }

    private void autoFitToWindow() throws Exception
    {
        setAllowAutoFit(true);

        // WORDSNET-27499 Don't remove preffered width value from the cells, just convert them to percentages.
        updateCellPreferredWidthToPercentage();
        setAttrOnAllRows(TableAttr.PREFERRED_WIDTH, PreferredWidth.fromPercent(100.0));
        // WORDSNET-18407 Remove left indent attribute when table fits to window.
        removeAttrFromAllRows(TableAttr.LEFT_INDENT);

        updateLayout();
    }

    /// <summary>
    /// Returns True if recalculation of the table width is necessary.
    /// </summary>
    private boolean tableWidthRecalculationNeeded()
    {
        if (!getPreferredWidth().isFixed() || (getPreferredWidth().getValue() == 0))
            return true;

        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
            for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
                if (cell.getCellPr().getWidth() > getPreferredWidth().getValue())
                    return true;

        return false;
    }

    /// <summary>
    /// Updates the preferred width of each cell in the table to a percentage of the table's width.
    /// </summary>
    /// <remarks>
    /// If the cell's preferred width is set to auto and the document is not compatible with Word 2013 or later,
    /// the method will return without making any changes. This is because calculating percentages for old documents
    /// with auto preferred width is complex.
    /// </remarks>
    private void updateCellPreferredWidthToPercentage()
    {
        PreferredWidth firstCellPreferedWidth = getFirstRow().getFirstCell().getCellPr().getPreferredWidth();
        if ((firstCellPreferedWidth.isAuto()) && !(this.getDocument().getDocPr().CompatibilityOptions.isWord2013OrLaterCompatible()))
            return;

        // If the preferred width is fixed, use its value; otherwise, calculate the table width.
        double tableWidth = tableWidthRecalculationNeeded()
            ? calculateTableWidth()
            : getPreferredWidth().getValue();

        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            boolean isRecalculationNeeded = preferredWidthRecalculationNeeded(row);

            for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
            {
                PreferredWidth cellPreferredWidth = cell.getCellPr().getPreferredWidth();
                // Set the preferred width of the cell to a percentage value.
                // If the current preferred width is already a percentage, use its value.
                // Otherwise, calculate the percentage based on the cell and table width.
                cell.getCellPr().setPreferredWidth(PreferredWidth.fromPercent(!isRecalculationNeeded
                    ? cellPreferredWidth.getValue()
                    : calculateCellWidthPercent(cell, tableWidth)));
            }
        }
    }

    /// <summary>
    /// Returns True if PreferredWidth for the cells in the specified row should be recalculated.
    /// </summary>
    private boolean preferredWidthRecalculationNeeded(Row row)
    {
        double totalInPct = 0.0;

        for (Cell cell : (Iterable<Cell>) row.getCells())
        {
            if (!cell.getCellPr().getPreferredWidth().isPercent())
                return true;

            totalInPct += cell.getCellPr().getPreferredWidth().getValue();
        }

        return msMath.round(totalInPct, 2) > 100.1;
    }

    /// <summary>
    /// Convert cell preferred width to a percentage of the table's width.
    /// </summary>
    /// <returns>The width of the table cell in percent.</returns>
    private double calculateCellWidthPercent(Cell cell, double tableWidth)
    {
        // Define a constant value used for truncating the percentage to three decimal places.
        int trancateValue = 1000;

        double cellWidth = ConvertUtilCore.twipToPoint(cell.getCellPr().getWidth());
        double percent = (cellWidth / tableWidth) * 100.0;
        // We need to pass the 3 digits after the dots to MS Word to get the correct result.
        percent = msMath.truncate(percent * trancateValue) / trancateValue;

        return percent;
    }

    /// <summary>
    /// Calculates the total width of a table base on cell widths in the first row.
    /// </summary>
    /// <returns>The total width of the table in points.</returns>
    private double calculateTableWidth()
    {
        double tableWidth = 0.0;
        for (Cell cell = getFirstRow().getFirstCell(); cell != null; cell = cell.getNextCell())
            tableWidth += ConvertUtilCore.twipToPoint(cell.getCellPr().getWidth());

        return tableWidth;
    }

    private void removeAttrFromAllCells(int key)
    {
        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
                cell.getCellPr().remove(key);
        }
    }

    private void autoFitFixedColumnWidths() throws Exception
    {
        // Set fixed table layout.
        setAllowAutoFit(false);

        // In fixed layout, auto table width is determined by cell widths.
        setPreferredWidth(PreferredWidth.AUTO);

        // Remove the grid calculated earlier.
        removeCalculatedGrid();

        // In fixed layout table cells should have preferred width.
        setCellPreferredToCurrentWidth();

        // Calculate grid spans from cell widths.
        cellWidthsToTableGrid();
    }

    /// <summary>
    /// Builds table grid, revised tabled grid if needed and populates table with table grid specific attributes.
    /// </summary>
    void cellWidthsToTableGrid()
    {
        // If the grid re-calculated from table properties is available, use it.
        TableGridAttr calculatedGrid = getTablePr().getCalculatedGrid();

        if (TableGridAttr.isApplied(calculatedGrid) &&
            getTablePr().getDirectAttr(TableAttr.SYS_TABLE_GRID) != null)
        {
            // Currently, the new algorithm does not support tables with revisions,
            // so do not bother with the revisions logic for now.
            // Just use the calculated grid instead of the grid in the document.

            // Do not use the "old" grid calculation logic implemented by TableGrid class if the "new" grid is available.
            return;
        }

        // Build two table grids: original is before-changes, final is after-changes.
        TableGridFromCellWidth tableGridOriginal = new TableGridFromCellWidth(this, false);
        TableGridFromCellWidth tableGridFinal = new TableGridFromCellWidth(this, true);

        getTablePr().setAttr(TableAttr.SYS_TABLE_GRID, new IntList(tableGridOriginal.getColumnWidths()));

        // Update before-rows.
        for (Row row : (Iterable<Row>) getRows())
        {
            tableGridOriginal.nextRow(row);
            updateRowGrid(row.getTablePr(), tableGridOriginal);

            // Update cell span.
            for (Cell cell : (Iterable<Cell>) row.getCells())
                cell.getCellPr().setAttr(CellAttr.SYS_CELL_SPAN, tableGridOriginal.nextCell());
        }

        // If table grid is unchanged do not write changed table grid.
        if (ArrayUtil.isArrayEqual(tableGridOriginal.getColumnWidths(), tableGridFinal.getColumnWidths()))
        {
            // It could be that table had format revision before but now it has not. Remove formatting revision then.
            getTablePr().rejectFormatRevision();
            return;
        }

        // Table grid changes saved into DOCX without revision author/date so this only internally used revision.
        if (getTablePr().getFormatRevision() == null)
            getTablePr().setFormatRevision(new FormatRevision(new TablePr(), "", DateTime.MinValue));

        // Store revised table grid.
        getTablePr().getRevisedPr().setAttr(TableAttr.SYS_TABLE_GRID, new IntList(tableGridFinal.getColumnWidths()));

        for (Row row : (Iterable<Row>) getRows())
        {
            // Regardless of table's revision row can has or has not revision independently.
            TablePr rowPr = row.getTablePr().getRevisedPr();

            tableGridFinal.nextRow(row);
            updateRowGrid(rowPr, tableGridFinal);

            for (Cell cell : (Iterable<Cell>) row.getCells())
                cell.getCellPr().getRevisedPr().setAttr(CellAttr.SYS_CELL_SPAN, tableGridFinal.nextCell());
        }
    }

    /// <summary>
    /// Updates table grid row specific properties from table grid.
    /// </summary>
    /// <remarks>
    /// The method may overwrite width before/after specified in percent units.
    /// It has always worked this way.
    /// The method is only used for tables without a grid calculated by the "new" algorithm.
    /// </remarks>
    private static void updateRowGrid(TablePr rowPr, TableGridFromCellWidth tableGrid)
    {
        rowPr.setAttr(TableAttr.SYS_GRID_BEFORE, tableGrid.getGridBefore());
        rowPr.setAttr(TableAttr.SYS_GRID_AFTER, tableGrid.getGridAfter());
        if (tableGrid.getBefore() > 0)
            rowPr.setWidthBeforeTwips(tableGrid.getBefore());
        else
            rowPr.remove(TableAttr.WIDTH_BEFORE);

        if (tableGrid.getAfter() > 0)
            rowPr.setWidthAfterTwips(tableGrid.getAfter());
        else
            rowPr.remove(TableAttr.WIDTH_AFTER);
    }

    private void removeCalculatedGrid()
    {
        if (getTablePr().getCalculatedGrid() != null)
            getTablePr().remove(TableAttr.SYS_CALCULATED_TABLE_GRID);
    }

    private void setCellPreferredToCurrentWidth()
    {
        for (Row row = getFirstRow(); row != null; row = row.getNextRow())
        {
            Cell firstMergedCell = null;
            for (Cell cell = row.getFirstCell(); cell != null; cell = cell.getNextCell())
            {
                Object width = cell.getCellPr().getDirectAttr(CellAttr.WIDTH);
                int cellWidth = (width == null) ? 0 : (/*int*/Integer) width;

                if (cellWidth <= 0)
                {
                    // Try using preferred width then.
                    PreferredWidth cellPreferred = cell.getCellFormat().getPreferredWidth();
                    // I do not see a sensible way to use percent or auto width.
                    // The absence of computed Width in the model means that table grid was never calculated with those values.
                    // As they are being replaced with fixed preferred width, it does not make sense to use those preferred width values.
                    if (cellPreferred.isFixed())
                    {
                        cellWidth = cellPreferred.getValueTwips();
                        cell.getCellPr().setWidth(cellWidth);
                    }
                }

                if (cellWidth <= 0)
                {
                    // Use the same default MS Word uses when loading a document having cell width not specified in a fixed layout table.
                    cellWidth = TableGridFromCellWidth.DEFAULT_FIXED_COLUMN_WIDTH_TWIPS * cell.getCellPr().getGridSpan();
                    cell.getCellPr().setWidth(cellWidth);
                }

                switch (cell.getCellPr().getHorizontalMerge())
                {
                    case CellMerge.FIRST:
                        // The cell that is first in a series of merged cells, becomes a normal cell
                        // and it will now take on the width of all merged cells.
                        firstMergedCell = cell;
                        cell.getCellPr().setPreferredWidth(PreferredWidth.fromTwipsSafe(cellWidth));
                        break;
                    case CellMerge.PREVIOUS:
                        if (firstMergedCell != null)
                        {
                            int mergedWidth = firstMergedCell.getCellPr().getPreferredWidth().getValueTwips() + cellWidth;
                            firstMergedCell.getCellPr().setPreferredWidth(PreferredWidth.fromTwipsSafe(mergedWidth));
                            // Merged cell preferred is normally ignored.
                            cell.getCellPr().setPreferredWidth(PreferredWidth.AUTO);
                        }
                        else
                        {
                            // Invalid case. A cell merged to previous while there was no first.
                            // Treat it as a regular cell.
                            cell.getCellPr().setPreferredWidth(PreferredWidth.fromTwipsSafe(cellWidth));
                            firstMergedCell = null;
                        }
                        break;
                    case CellMerge.NONE:
                        cell.getCellPr().setPreferredWidth(PreferredWidth.fromTwipsSafe(cellWidth));
                        firstMergedCell = null;
                        break;
                    default:
                        throw new IllegalStateException("Unknown cell merge type.");
                }

                // This may have unexpected results if the current cell width in the model
                // is actually less than minimal possible cell width determined by cell margins.
                // But it is difficult to interpret what the expected result should be in that case,
                // so the code is still using the widths in the model.
            }
        }
    }

    /// <summary>
    /// andrnosk: WORDSNET-7926 Mimic MS Word behavior.
    /// Clear all row attributes except Height, HeightRule, PreferredWidth and AllowAutoFit.
    /// </summary>
    private void prepareRowBeforeSetIstd(Row row) throws Exception
    {
        Object savedHeight = row.getTablePr().get(TableAttr.ROW_HEIGHT);
        Object savedAllowAutoFit = row.getTablePr().get(TableAttr.ALLOW_AUTO_FIT);
        Object savedPreferredWidth = row.getTablePr().get(TableAttr.PREFERRED_WIDTH);

        row.getRowFormat().clearFormatting();

        // MS Word does not preserve cell VerticalAlignment upon style applying.
        removeAttrFromAllCells(CellAttr.VERTICAL_ALIGNMENT);

        if (savedHeight != null)
            row.getTablePr().setAttr(TableAttr.ROW_HEIGHT, savedHeight);
        if (savedAllowAutoFit != null)
            row.getTablePr().setAttr(TableAttr.ALLOW_AUTO_FIT, savedAllowAutoFit);
        if (savedPreferredWidth != null)
            row.getTablePr().setAttr(TableAttr.PREFERRED_WIDTH, savedPreferredWidth);
    }

    /// <summary>
    /// Gets the attribute from the first row. If there is no row, gets the default value.
    /// </summary>
    private Object fetchFirstRowAttr(int key)
    {
        Row row = getFirstRow();

        if (row == null)
            return TablePr.fetchDefaultAttr(key);

        // AM. Since FetchRowAttr needs Istd to get base table style
        // we must not use FetchRowAttr to get Istd value to prevent infinite loop.
        // In fact Istd attribute is not inheritable attribute so we really don't need to resolve it.
        if (key == TableAttr.ISTD)
        {
            // AM. There can be case when row.TablePr is null.
            // For example when we resolve BiDi table alignment row properties is not ready.
            return (row.getTablePr() != null) ? row.getTablePr().getIstd() : TablePr.fetchDefaultAttr(TableAttr.ISTD);
        }

        return ((IRowAttrSource)row).fetchRowAttr(key);
    }

    /// <summary>
    /// Facades, created on demand.
    /// </summary>
    private RowCollection mRowsCache;

    private TablePr mTablePr;

    /// <summary>
    /// That's how many columns in a table MS Word allows maximum.
    /// </summary>
    static final int MAX_COLUMNS = 63;

    /// <summary>
    /// MSW adds this cell spacing when "Allow spacing between cells" checkbox
    /// changes its state from unchecked to checked.
    /// </summary>
    /// <remarks>
    /// The value of this constant depends on operating system property "Measurement system".
    /// (Region -> Formats -> Additional settings -> Numbers -> Measurement system)
    /// It is 7 twips for U.S. measurement system, and it is 11 twips for Metric measurement system.
    /// Here we relies on U.S. measurement system.
    /// In case of customer request, we should take measurement system into consideration
    /// in order to receive correct value.
    /// </remarks>
    static final int INITIAL_CELL_SPACING = 7;
}

