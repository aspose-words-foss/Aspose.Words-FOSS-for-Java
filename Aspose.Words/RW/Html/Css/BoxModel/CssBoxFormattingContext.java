// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a CSS box formatting context. If boxes belong to the same formatting context, their adjacent vertical margins
/// can collapse.
/// </summary>
/// <remarks>
/// We have to manually collapse adjacent vertical margins of boxes in cases where MS Word do not conform to CSS rules.
/// For example, in CSS floating tables do not affect collapsing of margins of elements around them, but MS Word do not
/// collapse the margins. Currently, our CSS box model creates additional formatting contexts only for floating tables.
/// These contexts can be nested.
/// </remarks>
class CssBoxFormattingContext
{
    CssBoxFormattingContext(CssBox rootBox, boolean isFullWidthBox)
    {
        Debug.assertTrue(rootBox != null);
        mRootBox = rootBox;
        mIsFullWidthBox = isFullWidthBox;
    }

    CssBox getRootBox() { return mRootBox; }

    /// <summary>
    /// Gets a value indicating whether the enclosing box of this formatting context has 100% width (is full-width).
    /// </summary>
    /// <remarks>
    /// A full-width floating box cannot have other boxes next to its left and right sides. Instead, adjacent boxes
    /// are pushed below the full-width box.
    /// </remarks>
    boolean isFullWidthBox() { return mIsFullWidthBox; }

    /*CssBoxMarginManualCollapsing*/int getCurrentBoxTopMarginManualCollapsing() { return mCurrentBoxTopMarginManualCollapsing; }
    void setCurrentBoxTopMarginManualCollapsing(/*CssBoxMarginManualCollapsing*/int value) { mCurrentBoxTopMarginManualCollapsing = value; }

    double getPreviousBoxBottomMargin() { return mPreviousBoxBottomMargin; }
    void setPreviousBoxBottomMargin(double value) { mPreviousBoxBottomMargin = value; }

    double getCurrentBoxBottomMargin() { return mCurrentBoxBottomMargin; }
    void setCurrentBoxBottomMargin(double value) { mCurrentBoxBottomMargin = value; }

    CssLength getEmptyBlockMarginBefore() { return mEmptyBlockMarginBefore; }; void setEmptyBlockMarginBefore(CssLength value) { mEmptyBlockMarginBefore = value; };

    private CssLength mEmptyBlockMarginBefore;

    private /*final*/ CssBox mRootBox;

    private /*final*/ boolean mIsFullWidthBox;

    private /*CssBoxMarginManualCollapsing*/int mCurrentBoxTopMarginManualCollapsing;

    private double mPreviousBoxBottomMargin;

    private double mCurrentBoxBottomMargin;
}

