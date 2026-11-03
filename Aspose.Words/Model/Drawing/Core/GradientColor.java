// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


/// <summary>
/// Defines a band of color in a gradient with multiple colors.
///
/// Made into a class to simplify autporting to Java. Do not make this a struct.
/// </summary>
class GradientColor
{
    /// <summary>
    /// Initializes a new instance of the <see cref="GradientColor" /> class.
    /// </summary>
    GradientColor()
    {
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="GradientColor" /> class.
    /// </summary>
    GradientColor(GradientColor other)
    {
        Color = new DrColor(other.Color.toArgb());
        Start = other.Start;
    }

    /// <summary>
    /// Determines whether the specified object is equal to the current object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if ((obj == null) || (obj.getClass() != GradientColor.class))
            return false;

        GradientColor other = (GradientColor)obj;
        return ((Start == other.Start) && Color.equals(other.Color));
    }

    /// <summary>
    /// The index of this instance in collection to which it belongs.
    /// </summary>
    /// <remarks>
    /// This used in sorting to preserve indexes of equal gradient colors within collection being sorted.
    /// </remarks>
    int getIndex() { return mIndex; }; void setIndex(int value) { mIndex = value; };

    private int mIndex;

    /// <summary>
    /// The color of the band.
    /// </summary>
    DrColor Color;
    /// <summary>
    /// The starting position of the band in the whole gradient. This is Fixed data type.
    /// </summary>
    int Start;
}

