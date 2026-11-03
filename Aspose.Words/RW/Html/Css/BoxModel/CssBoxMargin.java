// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// A margin of a box in the CSS box model.
/// </summary>
/// <remarks>
/// This class is immutable (read-only).
/// </remarks>
class CssBoxMargin
{
    private CssBoxMargin(
        CssLength length,
        /*CssBoxMarginCollapsing*/int collapsingWithParentMargin,
        boolean zeroDefaultCollapsedMargins)
    {
        mLength = length;
        mCollapsingWithParentMargin = collapsingWithParentMargin;
        mZeroDefaultCollapsedMargins = zeroDefaultCollapsedMargins;
    }

    static CssBoxMargin create(
        CssLength length,
        /*CssBoxMarginCollapsing*/int collapsingWithParentMargin,
        boolean zeroDefaultCollapsedMargins)
    {
        if ((length == CssLength.ZeroDefault) && !zeroDefaultCollapsedMargins)
        {
            // Memory optimization: use instanced objects to reduce memory allocations.
            switch (collapsingWithParentMargin)
            {
                case CssBoxMarginCollapsing.ADD:
                    return G_ZERO_ADD;
                case CssBoxMarginCollapsing.COLLAPSE:
                    return G_ZERO_COLLAPSE;
                case CssBoxMarginCollapsing.SEPARATE:
                    return G_ZERO_SEPARATE;
                default:
                    Debug.assertTrue(false);
                    return G_ZERO_SEPARATE;
            }
        }

        return new CssBoxMargin(length, collapsingWithParentMargin, zeroDefaultCollapsedMargins);
    }

    /// <summary>
    /// Margin size.
    /// </summary>
    CssLength getLength() { return mLength; }

    /// <summary>
    /// Defines how this margin is collapsed with the margin of the parent box.
    /// </summary>
    /*CssBoxMarginCollapsing*/int getCollapsingWithParentMargin() { return mCollapsingWithParentMargin; }

    /// <summary>
    /// Indicates whether all default margins are zeroed when collapsed with this margin.
    /// </summary>
    boolean getZeroDefaultCollapsedMargins() { return mZeroDefaultCollapsedMargins; }

    private /*final*/ CssLength mLength;

    private /*final*/ /*CssBoxMarginCollapsing*/int mCollapsingWithParentMargin;

    private /*final*/ boolean mZeroDefaultCollapsedMargins;

    private static /*final*/ CssBoxMargin G_ZERO_ADD = new CssBoxMargin(CssLength.ZeroDefault, CssBoxMarginCollapsing.ADD, false);
    private static /*final*/ CssBoxMargin G_ZERO_COLLAPSE = new CssBoxMargin(CssLength.ZeroDefault, CssBoxMarginCollapsing.COLLAPSE, false);
    private static /*final*/ CssBoxMargin G_ZERO_SEPARATE = new CssBoxMargin(CssLength.ZeroDefault, CssBoxMarginCollapsing.SEPARATE, false);
}

