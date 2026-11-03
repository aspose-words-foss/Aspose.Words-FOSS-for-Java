// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies a single row of a matrix.
/// </summary>
class MathObjectMatrixRow extends MathObject
{
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.MATRIX_ROW; }

    /*override*/ boolean canBeArgument() { return false; }

    /// <summary>
    /// Flag is used for rendering EQ field overstrike token only.
    /// </summary>
    boolean isOverstrikeRow() { return mIsOverstrikeRow; }
    void isOverstrikeRow(boolean value) { mIsOverstrikeRow = value; }

    private boolean mIsOverstrikeRow;
}

