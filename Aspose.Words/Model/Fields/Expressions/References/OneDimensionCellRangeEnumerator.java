// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2011 by Dmitry Matveenko
// The class is moved to a separate module to separate up-down and right-left moving logic per issue 24122

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;


/// <summary>
/// Enumerates cells in the range.
/// </summary>
abstract class OneDimensionCellRangeEnumerator implements Iterator<Cell>
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="range"></param>
    protected OneDimensionCellRangeEnumerator(OneDimensionCellRange range)
    {
        Range = range;
        CurrentPosition = Range.getReferencePosition().deepClone();
    }

    public static OneDimensionCellRangeEnumerator createInstance(OneDimensionCellRange range)
    {
        OneDimensionCellRangeEnumerator instance = null;
        switch (range.getDirection())
        {
            case CellEnumerationDirection.UP:
            case CellEnumerationDirection.DOWN:
                instance = new UpDownCellRangeEnumerator(range);
                break;
            case CellEnumerationDirection.LEFT:
            case CellEnumerationDirection.RIGHT:
                instance = new RightLeftCellRangeEnumerator(range);
                break;
            default:
                throwOnInvalidDirection();
                break;
        }
        return instance;
    }

    protected static void throwOnInvalidDirection()
    {
        throw new IllegalStateException("Unknown cell enumeration direction.");
    }

    public boolean hasNext()
    {
        boolean isMoveSuccessfull;
        // WORDSNET-25839 Ignore merged cells.
        do
        {
            isMoveSuccessfull = moveToNextCell();

            setCurrent(isMoveSuccessfull ? CurrentPosition.getCell() : null);

            // WORDSNET-25839 Ignore merged cells. Move on if the cell is merged.
        } while (isMoveSuccessfull && isCellMerged(next()));

        return isMoveSuccessfull;
    }

    /// <summary>
    /// Checks if the cell is merged with a previous cell either vertically or horizontally.
    /// </summary>
    /// <param name="cell"></param>
    /// <returns></returns>
    private static boolean isCellMerged(Cell cell)
    {
        return (cell.getCellPr().getVerticalMerge() == CellMerge.PREVIOUS)
            || (cell.getCellPr().getHorizontalMerge() == CellMerge.PREVIOUS);
    }

    protected abstract boolean moveToNextCell();

    public void reset()
    {
        CurrentPosition = Range.getReferencePosition().deepClone();
        setCurrent(null);
    }

    public void dispose()
    {
        // Nothing to dispose.
    }

    public Cell next() { return mCurrent; }; private void setCurrent(Cell value) { mCurrent = value; };

    private Cell mCurrent;

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //Object IEnumerator.Current

    protected /*final*/ OneDimensionCellRange Range;
    protected CellPosition CurrentPosition;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
}

