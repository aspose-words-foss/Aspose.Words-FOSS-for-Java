// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.Group;
import com.aspose.Debug;
import com.aspose.NullableInt32;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Designates a position of a cell in a table.
/// </summary>
class CellPosition implements Cloneable
{
    CellPosition(Cell cell)
    {
    	this(cell.getParentTable(), cell.getColumnIndex(), cell.getRowIndex());
	
    }

    private CellPosition(Table table, int columnIndex, int rowIndex)
    {
        mTable = table;
        setColumnIndex(columnIndex);
        setRowIndex(rowIndex);
    }

    /// <summary>
    /// Parse the specified text to a cell position.
    /// </summary>
    static CellPosition tryParse(Table table, String name, int cellIndex)
    {
        return (tryParseA1Notation(table, name, cellIndex) != null ? tryParseA1Notation(table, name, cellIndex) : tryParseR1C1Notation(table, name));
    }

    private static CellPosition tryParseA1Notation(Table table, String name, int cellIndex)
    {
        Match match = G_CELL_POSITION_A_1_NOTATION_REGEX.match(name);

        if (!match.getSuccess())
            return null;

        Group startGroup = match.getGroups().get(1);
        Group endGroup = match.getGroups().get(2);

        Debug.assertTrue(startGroup.getSuccess() || endGroup.getSuccess());

        if (startGroup.getSuccess() && endGroup.getSuccess())
        {
            // One cell.
            // WORDSNET-9786 The name matches to regex pattern, but can no be converted into cell position, because contains row index more than max int.
            // Return null, if row index can not be parsed from the name.
            NullableInt32 rowIndex = FormatterPal.parseNullableInt(endGroup.getValue());
            return rowIndex.hasValue()
                ? new CellPosition(
                    table,
                    NumberConverter.parseExcelColumnName(startGroup.getValue()),
                    rowIndex.getValue() - 1)
                : null;
        }
        else if (startGroup.getSuccess())
        {
            // One column.
            return new CellPosition(table, NumberConverter.parseExcelColumnName(startGroup.getValue()), cellIndex);
        }
        else
        {
            // One row.
            NullableInt32 rowIndex = FormatterPal.parseNullableInt(endGroup.getValue());
            return rowIndex.hasValue()
                ? new CellPosition(table, cellIndex, rowIndex.getValue() - 1)
                : null;
        }
    }

    private static CellPosition tryParseR1C1Notation(Table table, String name)
    {
        Match match = G_CELL_POSITION_R_1_C_1_NOTATION_REGEX.match(name);

        if (!match.getSuccess())
            return null;

        Group rowGroup = match.getGroups().get(1);
        Group columnGroup = match.getGroups().get(2);

        NullableInt32 rowIndex = FormatterPal.parseNullableInt(rowGroup.getValue());
        NullableInt32 columnIndex = FormatterPal.parseNullableInt(columnGroup.getValue());

        if (!rowIndex.hasValue() || !columnIndex.hasValue())
            return null;

        if ((rowIndex.getValue() == 0) || (columnIndex.getValue() == 0))
            return null;

        return new CellPosition(table, columnIndex.getValue() - 1, rowIndex.getValue() - 1);
    }

    static boolean areSame(CellPosition position1, CellPosition position2)
    {
        return (position1.getColumnIndex() == position2.getColumnIndex()) && (position1.getRowIndex() == position2.getRowIndex());
    }

    CellPosition deepClone()
    {
        return (CellPosition)memberwiseClone();
    }

    public /*override*/ String toString()
    {
        String columnName = (NumberConverter.toExcelColumnName(getColumnIndex()) != null ? NumberConverter.toExcelColumnName(getColumnIndex()) : INVALID_COLUMN_NAME);

        return msString.format("{0}{1}", columnName, getRowIndex() + 1);
    }

    /// <summary>
    /// Moves the current position right or left.
    /// </summary>
    /// <param name="step">The number of columns to move. A negative value moves left.</param>
    /// <returns>A boolean value indicating if the new position is valid (true) or not (false)</returns>
    boolean moveRight(int step)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	// WORDSNET-6456 Because mColumnIndex might get a negative value in the constructor
        	// (after parsing an invalid column name), we need to check it before using.
        	if (getColumnIndex() < 0)
            	return false;

        	int newColumnIndex = getColumnIndex() + step;
        	boolean isNewIndexValid = isColumnValid(getRowIndex(), newColumnIndex);
        	if(isNewIndexValid)
            	setColumnIndex(newColumnIndex);
        	return isNewIndexValid;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Moves the current position up or down.
    /// </summary>
    /// <param name="step">The number of rows to move. A negative value moves up.</param>
    /// <returns>A boolean value indicating if the new position is valid (true) or not (false)</returns>
    boolean moveDown(int step)
    {
        // WORDSNET-6456 Since mColumnIndex might get a negative value in the constructor
        // (after parsing an invalid column name), we have to prohibit any operation with invalid cell position.
        if (getColumnIndex() < 0)
            return false;

        int newRowIndex = getRowIndex() + step;
        boolean isNewIndexValid = isRowValid(newRowIndex);
        if (isNewIndexValid)
            setRowIndex(newRowIndex);
        return isNewIndexValid;
    }

    /// <summary>
    /// Gets a cell located at the position.
    /// </summary>
    Cell getCell() { return mTable.getCell(getColumnIndex(), getRowIndex()); }

    boolean isValid() { return isColumnValid(getRowIndex(), getColumnIndex()); }

    private boolean isRowValid(int rowIndex)
    {
        return (0 <= rowIndex) && (rowIndex < mTable.getRows().getCount());
    }

    private boolean isColumnValid(int rowIndex, int columnIndex)
    {
        return isRowValid(rowIndex) &&
               (0 <= columnIndex) &&
               (columnIndex < mTable.getRows().get(rowIndex).getCells().getCount());
    }

    boolean isAtFirstColumn() { return getColumnIndex() == 0; }

    boolean isAtFirstRow() { return getRowIndex() == 0; }

    boolean isAtLastColumn() { return getColumnIndex() == mTable.getRows().get(getRowIndex()).getCells().getCount() - 1; }

    boolean isAtLastRow() { return getRowIndex() == mTable.getRows().getCount() - 1; }

    /// <summary>
    /// Gets or sets the index of the cell's column.
    /// </summary>
    int getColumnIndex() { return mColumnIndex; }; void setColumnIndex(int value) { mColumnIndex = value; };

    private int mColumnIndex;

    /// <summary>
    /// Gets or sets the index of the cell's row.
    /// </summary>
    int getRowIndex() { return mRowIndex; }; void setRowIndex(int value) { mRowIndex = value; };

    private int mRowIndex;

    private /*final*/ Table mTable;

    private static /*final*/ Regex G_CELL_POSITION_A_1_NOTATION_REGEX = new Regex(
        "^([A-Z]{1,2})?(\\d{1,5})?$",
        RegexOptions.IGNORE_CASE | RegexOptions.COMPILED);

    private static /*final*/ Regex G_CELL_POSITION_R_1_C_1_NOTATION_REGEX = new Regex(
        "^R(\\d{1,5})C(\\d{1,5})$",
        RegexOptions.IGNORE_CASE | RegexOptions.COMPILED);

    private static final String INVALID_COLUMN_NAME = "!Invalid column name.";

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

