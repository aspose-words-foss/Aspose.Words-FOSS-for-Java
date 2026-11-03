// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2011 by Dmitry Matveenko
// The class is created to separate right-left moving logic per issue 24122

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates cells in a one-dimensional range by moving right or left.
/// </summary>
class RightLeftCellRangeEnumerator extends OneDimensionCellRangeEnumerator
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="range"></param>
    RightLeftCellRangeEnumerator(OneDimensionCellRange range)
    {
    	super(range);
	
        final int STEP_LEFT = -1;
        final int STEP_RIGHT = 1;

        switch (range.getDirection())
        {
            case CellEnumerationDirection.LEFT:
                mStep = STEP_LEFT;
                break;
            case CellEnumerationDirection.RIGHT:
                mStep = STEP_RIGHT;
                break;
            default:
                throwOnInvalidDirection();
                break;
        }
    }

    protected /*override*/ boolean moveToNextCell()
    {
        return CurrentPosition.moveRight(mStep);
    }

    /// <summary>
    /// Stores column index step. 1 to move right, -1 to move left.
    /// </summary>
    private /*final*/ int mStep;
}

