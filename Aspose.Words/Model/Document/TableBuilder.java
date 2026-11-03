// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/11/2004 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies state of processing a table.
/// </summary>
/*enum*/ final class TableState
{
    private TableState(){}
    
    /// <summary>
    /// Not initialized.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Inside Table
    /// </summary>
    public static final int IN_TABLE = 1;
    /// <summary>
    /// Inside Row
    /// </summary>
    public static final int IN_ROW = 2;
    /// <summary>
    /// Inside Cell
    /// </summary>
    public static final int IN_CELL = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TableState value.
    /// </summary>
    public static String getName(/*TableState*/int tableState)
    {
    	switch (tableState)
    	{
    		case NONE: return "NONE";
    		case IN_TABLE: return "IN_TABLE";
    		case IN_ROW: return "IN_ROW";
    		case IN_CELL: return "IN_CELL";
    		default: return "Unknown TableState value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TableState value.
    /// </summary>
    public static String toString(/*TableState*/int tableState)
    {
    	switch (tableState)
    	{
    		case NONE: return "None";
    		case IN_TABLE: return "InTable";
    		case IN_ROW: return "InRow";
    		case IN_CELL: return "InCell";
    		default: return "Unknown TableState value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TableState name.
    /// </summary>
    public static /*TableState*/int fromName(String tableStateName)
    {
    	if ("NONE".equals(tableStateName)) return NONE;
    	if ("IN_TABLE".equals(tableStateName)) return IN_TABLE;
    	if ("IN_ROW".equals(tableStateName)) return IN_ROW;
    	if ("IN_CELL".equals(tableStateName)) return IN_CELL;
    	throw new IllegalArgumentException("Unknown TableState name.");
    }
    
    /// <summary>
    /// Returns array of TableState values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		IN_TABLE,
    		IN_ROW,
    		IN_CELL,
    	};
    }

    public static final int length = 4;
}

/// <summary>
/// Works together with DocumentBuilder to build tables in a document.
/// </summary>
class TableBuilder
{
    TableBuilder(DocumentBuilder builder, boolean contextFormatting)
    {
        if (builder == null)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "builder");

        mBuilder = builder;
        mContextFormatting = contextFormatting;
    }

    Table startTable()
    {
        if (mTableState != TableState.NONE)
            throw new IllegalStateException("Cannot start a table in this state.");

        // If you try to start a table in the middle of a paragraph, start it from a new paragraph.
        if (!mBuilder.isAtStartOfParagraph())
        {
            // WORDSNET-27500 Ensure that cursor is moved to SDT end.
            mBuilder.ensureAtStructuredDocumentTagEnd();
            mBuilder.insertParagraph();
        }

        // Insert the table node.
        mCurTable = new Table(mBuilder.getDocument());
        mBuilder.insertBeforeCurPara(mCurTable);

        mTableState = TableState.IN_TABLE;
        return mCurTable;
    }

    Table endTable()
    {
        //Smart table building API method, end previous cell and row implicitly.
        if (mTableState == TableState.IN_CELL)
            endCell();

        if (mTableState == TableState.IN_ROW)
            endRow();

        if (mTableState != TableState.IN_TABLE)
            throw new IllegalStateException("Cannot end a table in this state.");

        //Move the cursor outside of the table and this will detach table attributes from the model.
        Paragraph nextPara = (Paragraph)mCurTable.getNextNonMarkupCompositeLimited();
        if (!mContextFormatting)
            nextPara.setParagraphBreakRunPr(mBuilder.getRunPrCopy());

        mBuilder.moveTo(nextPara, 0);

        // WORDSNET-21602 To avoid unwanted cell merging in the next table (if any) reset merge.
        mBuilder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        mBuilder.getCellFormat().setHorizontalMerge(CellMerge.NONE);

        mTableState = TableState.NONE;
        Table result = mCurTable;
        mCurTable = null;
        return result;
    }

    /// <summary>
    /// Creates a new table row node and adds to the current table.
    /// </summary>
    Row startRow()
    {
        if (mTableState != TableState.IN_TABLE)
            throw new IllegalStateException("Cannot start a row in this state.");

        TablePr tablePr;

        // If we are adding rows to a table, I want all rows to have same attributes, hence clone from the previous row.
        Row prevRow = mCurTable.getLastRow();
        if (prevRow != null)
        {
            tablePr = AttrCollectionExtension.deepClone(prevRow.getTablePr());

            // Some formatting can be set before row is created, so copy them into newly created row.
            mBuilder.getTablePrCopy().expandTo(tablePr);
        }
        else
        {
            tablePr = mBuilder.getTablePrCopy();
        }

        // Create a table row and insert it into the model before the current paragraph.
        mCurRow = new Row(mBuilder.getDocument(), tablePr);
        mCurTable.appendChild(mCurRow);

        mTableState = TableState.IN_ROW;
        return mCurRow;
    }

    Row endRow()
    {
        // Smart table building API method, end previous cell implicitly.
        if (mTableState == TableState.IN_CELL)
            endCell();

        if (mTableState != TableState.IN_ROW)
            throw new IllegalStateException("Cannot end a row in this state.");

        mTableState = TableState.IN_TABLE;
        Row result = mCurRow;
        mCurRow = null;
        return result;
    }

    /// <summary>
    /// Creates a new table cell node with a cell mark, adds to the current table row
    /// and moves the cursor to it.
    /// </summary>
    Cell startCell()
    {
        if (mTableState != TableState.IN_ROW)
            throw new IllegalStateException("Cannot start a cell in this state.");

        // Create a new cell and insert it into the current table row.
        Cell cell = new Cell(mBuilder.getDocument(), mBuilder.getCellPrCopy());
        mCurRow.appendChild(cell);

        // Inside the cell, create a paragraph.
        Paragraph cellEndPara = new Paragraph(mBuilder.getDocument(), mBuilder.getParaPrCopy(), mBuilder.getRunPrCopy());
        cell.appendChild(cellEndPara);

        // Moving the cursor makes sure CellFormat and RowFormat get attached to the cell.
        mBuilder.moveTo(cellEndPara, 0);

        mTableState = TableState.IN_CELL;
        return cell;
    }

    void endCell()
    {
        if (mTableState != TableState.IN_CELL)
            throw new IllegalStateException("Cannot end a cell in this state.");

        mTableState = TableState.IN_ROW;

        // We want all cells have the same formatting. Hence we clone current cell formatting to the builder.
        // When new cell is started, we copy formatting from the builder to this new cell again.
        mBuilder.saveCurCellFormatting();

        // Does not do anything special regarding attaching/detaching table formatting
        // so it is still possible to modify cell attributes after it was added and before
        // the new cell is started. Not very clean, but okay since EndCell is normally
        // followed by StartCell or EndRow.

        /*
             * TODO 3 I might try a different approach for attaching/detaching table formatting
             * that could be a little easier to understand:
             *
             * StartTable - DetachAll because no row and no cell yet and changing row and cell format now should not change previous table format.
             * StartRow - AttachRow so modifying it will change the current row.
             * StartCell - Move into the cell attaches both Cell and Row since the cursor is now inside a cell.
             * EndCell - DetachCell so modifying cell properties has no effect on the finished cell.
             * EndRow -    DetachRow so modifying row will not change the finished row.
             * EndTable - Moves the cursor out of the table so another complete detach is made (skipped because already detached).
             *
             */
    }

    /*TableState*/int getTableState() { return mTableState; }

    private /*final*/ DocumentBuilder mBuilder;
    private /*final*/ boolean mContextFormatting;
    private /*TableState*/int mTableState;
    private Table mCurTable;
    private Row mCurRow;
}

