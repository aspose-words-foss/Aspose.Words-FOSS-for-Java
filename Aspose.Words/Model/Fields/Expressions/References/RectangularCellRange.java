// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.Group;
import com.aspose.ms.System.msString;
import java.util.Iterator;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.words.ref.Ref;


/// <summary>
/// Designates a rectangular area of table cells.
/// </summary>
class RectangularCellRange implements ICellRange
{
    private RectangularCellRange(CellPosition start, CellPosition end)
    {
        mStart = start;
        mEnd = end;
    }

    /// <summary>
    /// Attempts to parse the specified string to a <see cref="RectangularCellRange"/> object.
    /// Returns an erroneous constant if there was a problem during parse.
    /// </summary>
    static Constant tryParse(
        FieldContext context,
        String name,
        boolean areMultipleCellsAllowed,
        /*out*/Ref<ICellRange> range) throws Exception
    {
        range.set(null);

        Match match = G_CELL_RANGE_REGEX.match(name);
        if (!match.getSuccess())
            return null;

        Table rangeTable = null;
        Table bookmarkedTable = null;

        Group tableNameGroup = match.getGroups().get(TABLE_GROUP);
        if (tableNameGroup.getSuccess())
        {
            Ref<Table> referenceToBookmarkedTable = new Ref<Table>(bookmarkedTable);
            Constant result = tryGetBookmarkedTable(context, tableNameGroup.getValue(), /*out*/ referenceToBookmarkedTable);
            bookmarkedTable = referenceToBookmarkedTable.get();
            if (result != null)
            {
                // Something wrong with finding the bookmark.
                return result;
            }
        }

        if (bookmarkedTable != null)
        {
            rangeTable = bookmarkedTable;
        }
        else if (context.getParentTable() != null)
        {
            rangeTable = context.getParentTable();
        }

        Group oneCellGroup = match.getGroups().get(ONE_CELL_GROUP);
        if (oneCellGroup.getSuccess())
        {
            // One cell/one row/one column range.
            range.set(parse(rangeTable, oneCellGroup.getValue(), oneCellGroup.getValue()));
        }
        else if (match.getGroups().get(RANGE_GROUP).getSuccess())
        {
            // Rectangular range.
            range.set(parse(
                rangeTable,
                match.getGroups().get(START_GROUP).getValue(),
                match.getGroups().get(END_GROUP).getValue()));
        }

        if (range.get() == null)
        {
            return bookmarkedTable != null
                ? ErrorConstant.createSyntaxError()
                : null;
        }

        if (rangeTable == null)
            return new ErrorConstant("!The Formula Not In Table");

        if (!areMultipleCellsAllowed && !range.get().isOneCell())
            return ErrorConstant.createSyntaxError();

        return null;
    }

    private static Constant tryGetBookmarkedTable(FieldContext context, String bookmarkName, /*out*/Ref<Table> table) throws Exception
    {
        table.set(null);

        // SPEED Get a bookmark from a cache.
        Bookmark bookmark = FieldUtil.getCachedBookmark(context.getField(), bookmarkName);
        if (bookmark == null)
        {
            // No such bookmark in the document.
            return new ErrorConstant(msString.format("!Undefined Bookmark, {0}", bookmarkName.toUpperCase()));
        }

        Table bookmarkStartTable = (Table)bookmark.getBookmarkStart().getAncestor(NodeType.TABLE);
        Table bookmarkEndTable = (Table)bookmark.getBookmarkEnd().getAncestor(NodeType.TABLE);

        boolean isBookmarkStartValid = (bookmarkStartTable != null);
        // Word treats okay a situation when a bookmark end is right after a table.
        boolean isBookmarkEndValid = (bookmarkEndTable != null) ||
            (bookmark.getBookmarkEnd().getPreviousNonAnnotationSibling() == bookmarkStartTable) ||
            (bookmark.getBookmarkEnd().getFirstNonMarkupParentNode().getPreviousNonAnnotationSibling() == bookmarkStartTable);

        if (!isBookmarkEndValid)
            return new ErrorConstant(msString.format("!{0} Is Not In Table", bookmarkName));

        if (!isBookmarkStartValid)
            return new DoubleConstant(0d);

        table.set(bookmarkStartTable);
        return null;
    }

    private static ICellRange parse(Table table, String startName, String endName)
    {
        CellPosition startCell = CellPosition.tryParse(table, startName, 0);
        CellPosition endCell = CellPosition.tryParse(table, endName, Integer.MAX_VALUE);
        return startCell != null && endCell != null
            ? new RectangularCellRange(startCell, endCell)
            : null;
    }

    public Iterator<Cell> iterator()
    {
        return new RectangularCellRangeEnumerator(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public boolean /*ICellRange.*/isRectangular() { return true; }

    public boolean /*ICellRange.*/isOneCell() { return CellPosition.areSame(mStart, mEnd); }

    public CellPosition /*ICellRange.*/getStart() { return mStart; }

    public CellPosition /*ICellRange.*/getEnd() { return mEnd; }

    public Constant /*ICellRange.*/getEmptyCellValue() { return new DoubleConstant(0d); }

    public boolean /*ICellRange.*/getAlwaysEvaluateCellText() { return true; }

    /// <summary>
    /// Enumerates cells in a range.
    /// </summary>
    private final static class RectangularCellRangeEnumerator implements Iterator<Cell>
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="range"></param>
        RectangularCellRangeEnumerator(ICellRange range)
        {
            mRange = range;
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        public boolean hasNext()
        {
        	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
        	try
        	{
            	final int ONE_CELL_FORWARD = 1;

            	boolean isNewPositionValid;
            	if (mIsStarted)
            	{
                	isNewPositionValid = mCurrentPosition.moveRight(ONE_CELL_FORWARD);
            	}
            	else
            	{
                	mIsStarted = true;
                	mCurrentPosition = mRange.getStart().deepClone();
                	isNewPositionValid = mCurrentPosition.isValid();
            	}

            	// Check the range.
            	if (isNewPositionValid && (mCurrentPosition.getColumnIndex() <= mRange.getEnd().getColumnIndex()))
                	return true;

            	// End of range row. Move down until the first leftmost cell of the range is valid.
            	mCurrentPosition.setColumnIndex(0);
            	while (mCurrentPosition.moveDown(ONE_CELL_FORWARD) && (mCurrentPosition.getRowIndex() <= mRange.getEnd().getRowIndex()))
            	{
                	// Move to leftmost range column.
                	if (mCurrentPosition.moveRight(mRange.getStart().getColumnIndex()))
                    	return true;
            	}

            	// No more rows.
            	return false;
        	}
        	catch (Exception e)
        	{
        		throw new RuntimeException(e);
        	}
        }

        public void reset()
        {
            mIsStarted = false;
        }

        public Cell next() { return mIsStarted ? mCurrentPosition.getCell() : null; }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        private /*final*/ ICellRange mRange;
        private CellPosition mCurrentPosition;
        private boolean mIsStarted;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    private /*final*/ CellPosition mStart;
    private /*final*/ CellPosition mEnd;

    // Group indexes instead of names are required for auto-porting to Java.
    private static final int TABLE_GROUP = 2;
    private static final int ONE_CELL_GROUP = 4;
    private static final int RANGE_GROUP = 7;
    private static final int START_GROUP = 8;
    private static final int END_GROUP = 9;

    private static /*final*/ Regex G_CELL_RANGE_REGEX = new Regex(
        "^((\\w+)\\s+)?" + // Table bookmark name (optional).
        "((([A-Z]+\\d+)|(R\\d+C\\d+))|" + // One cell range (A2 or R2C3).
        "(([A-Z]+|\\d+|[A-Z]+\\d+):([A-Z]+|\\d+|[A-Z]+\\d+)))$", // Multiple cells range (A2:D2, B:B, 3:4).
        RegexOptions.IGNORE_CASE | RegexOptions.COMPILED);
}

