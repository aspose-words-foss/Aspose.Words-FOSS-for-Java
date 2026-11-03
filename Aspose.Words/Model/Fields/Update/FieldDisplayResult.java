// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Contains a node range used to display the result of the field. Required by layout engine.
/// </summary>
class FieldDisplayResult
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="range"></param>
    /// <param name="resultType"></param>
    FieldDisplayResult(NodeRange range, /*FieldDisplayRangeType*/int resultType)
    {
        mRange = range;
        mRangeType = resultType;
    }

    /// <summary>
    /// Gets a node range containing nodes to display.
    /// </summary>
    NodeRange getRange() { return mRange; };

    private NodeRange mRange;

    /// <summary>
    /// Gets the meaning of the display range.
    /// </summary>
    /*FieldDisplayRangeType*/int getRangeType() { return mRangeType; };

    private /*FieldDisplayRangeType*/int mRangeType;
}

