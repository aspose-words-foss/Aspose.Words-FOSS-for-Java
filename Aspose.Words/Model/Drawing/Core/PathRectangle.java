// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;

/// <summary>
/// Represents a rectangle whose coordinates can contain references to shape formulas.
/// </summary>
class PathRectangle
{
    /// <summary>
    /// Ctor.
    /// </summary>
    PathRectangle()
    {
    }

    /// <summary>
    /// Ctor for creating <see cref="PathRectangle"/> with non formula <see cref="PathValue"/>s.
    /// </summary>
    private PathRectangle(int left, int top, int right, int bottom)
    {
        Left = new PathValue(left);
        Top = new PathValue(top);
        Right = new PathValue(right);
        Bottom = new PathValue(bottom);
    }

    /// <summary>
    /// Returns true if this <see cref="PathRectangle"/> equals to <paramref name="other"/>.
    /// </summary>
    public boolean equals(PathRectangle other)
    {
        if (msObject.referenceEquals(null, other))
            return false;

        if (msObject.referenceEquals(this, other))
            return true;

        return Left.equals(other.Left) &&
            Top.equals(other.Top) &&
            Right.equals(other.Right) &&
            Bottom.equals(other.Bottom);
    }

    /// <summary>
    /// Clones this <see cref="PathRectangle"/>.
    /// </summary>
    PathRectangle deepClone()
    {
        PathRectangle lhs = new PathRectangle();

        lhs.Left = Left.deepClone();
        lhs.Top = Top.deepClone();
        lhs.Right = Right.deepClone();
        lhs.Bottom = Bottom.deepClone();

        return lhs;
    }

    PathValue Left = new PathValue();
    PathValue Top = new PathValue();
    PathValue Right = new PathValue();
    PathValue Bottom = new PathValue();

    /// <summary>
    /// MS Word uses this as a default non-formula path rectangle.
    /// </summary>
    static /*final*/ PathRectangle DEFAULT = new PathRectangle(3163, 3163, 18437, 18437);
}

