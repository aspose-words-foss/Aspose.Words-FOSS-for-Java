// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2010 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

class HandlePoint
{
    /// <summary>
    /// Describes the positioning of adjust handle.
    /// </summary>
    HandlePoint(HandlePositionValue x, HandlePositionValue y)
    {
        mX = x;
        mY = y;
    }

    /// <summary>
    /// The X value of the adjust handle position.
    /// </summary>
    HandlePositionValue getX() { return mX; }
    void setX(HandlePositionValue value) { mX = value; }

    /// <summary>
    /// The Y value of the adjust handle position.
    /// </summary>
    HandlePositionValue getY() { return mY; }
    void setY(HandlePositionValue value) { mY = value; }

    public /*override*/ String toString()
    {
        return msString.format("X:{0}, Y:{1}", getX().toString(), getY().toString());
    }

    private HandlePositionValue mY;
    private HandlePositionValue mX;
}

