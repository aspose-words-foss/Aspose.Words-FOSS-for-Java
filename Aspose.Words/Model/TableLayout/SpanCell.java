// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/08/2006 by Dmitry Vorobyev
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Encapsulates a spanned cell information.
/// </summary>
/*struct*/ final class SpanCell
{
	public SpanCell(){}
	
    SpanCell(Cell cell, int columnIndex)
    {
        Debug.assertTrue(cell != null);
        mCell = cell;
        mColumnIndex = columnIndex;
        
        mSpan = Extensions.getHorizontalMergeSpan(cell);
        // The object should be created for multi-column cells only.
        Debug.assertTrue(mSpan > 1);
    }

    Cell getCell() { return mCell; }

    int getSpan() { return mSpan; }

    int getColumnIndex() { return mColumnIndex; }

    private /*final*/ Cell mCell;
    private /*final*/ int mSpan;
    private /*final*/ int mColumnIndex;
}

