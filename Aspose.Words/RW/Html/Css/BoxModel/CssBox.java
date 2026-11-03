// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/10/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// A box of the CSS box model.
/// </summary>
class CssBox
{
    CssBox()
    {
    	this(G_ZERO_MARGIN, G_ZERO_MARGIN, G_ZERO_MARGIN, G_ZERO_MARGIN, false, false, true, false, true);
	
        // Empty constructor.
    }

    CssBox(CssBoxMargin left,
        CssBoxMargin right,
        CssBoxMargin top,
        CssBoxMargin bottom,
        boolean startsWithImplicitBox,
        boolean endsWithImplicitBox,
        boolean bottomMarginIsCollapsible,
        boolean isInsideTableCell,
        boolean canContainImplicitTextBoxes)
    {
        mLeftMargin = left;
        mRightMargin = right;
        mTopMargin = top;
        mBottomMargin = bottom;
        mChildTopMarginCollapsing = CssBoxMarginCollapsing.COLLAPSE;
        mChildBottomMarginCollapsing = CssBoxMarginCollapsing.COLLAPSE;
        mStartsWithImplicitBox = startsWithImplicitBox;
        mEndsWithImplicitBox = endsWithImplicitBox;
        mBottomMarginIsCollapsible = bottomMarginIsCollapsible;
        mIsInsideTableCell = isInsideTableCell;
        mCanContainImplicitTextBoxes = canContainImplicitTextBoxes;
    }

    /*CssBoxMarginCollapsing*/int getChildTopMarginCollapsing() { return mChildTopMarginCollapsing; }
    void setChildTopMarginCollapsing(/*CssBoxMarginCollapsing*/int value) { mChildTopMarginCollapsing = value; }

    /*CssBoxMarginCollapsing*/int getChildBottomMarginCollapsing() { return mChildBottomMarginCollapsing; }
    void setChildBottomMarginCollapsing(/*CssBoxMarginCollapsing*/int value) { mChildBottomMarginCollapsing = value; }

    boolean getBottomMarginIsCollapsible() { return mBottomMarginIsCollapsible; }

    boolean startsWithImplicitBox() { return mStartsWithImplicitBox; }

    boolean endsWithImplicitBox() { return mEndsWithImplicitBox; }

    boolean isInsideTableCell() { return mIsInsideTableCell; }

    boolean canContainImplicitTextBoxes() { return mCanContainImplicitTextBoxes; }

    boolean isEmptyBlock() { return mIsEmptyBlock; }; void isEmptyBlock(boolean value) { mIsEmptyBlock = value; };

    private boolean mIsEmptyBlock;

    CssBoxMargin getMargin(/*CssBoxMarginSide*/int side)
    {
        switch (side)
        {
            case CssBoxMarginSide.LEFT:
                return mLeftMargin;
            case CssBoxMarginSide.RIGHT:
                return mRightMargin;
            case CssBoxMarginSide.TOP:
                return mTopMargin;
            case CssBoxMarginSide.BOTTOM:
                return mBottomMargin;
            default:
                Debug.assertTrue(false);
                return G_ZERO_MARGIN;
        }
    }

    private static /*final*/ CssBoxMargin G_ZERO_MARGIN = CssBoxMargin.create(
        CssLength.ZeroDefault,
        CssBoxMarginCollapsing.COLLAPSE,
        true);

    private /*CssBoxMarginCollapsing*/int mChildTopMarginCollapsing;

    private /*CssBoxMarginCollapsing*/int mChildBottomMarginCollapsing;

    private /*final*/ boolean mStartsWithImplicitBox;

    private /*final*/ boolean mEndsWithImplicitBox;

    private /*final*/ boolean mBottomMarginIsCollapsible;

    private /*final*/ CssBoxMargin mLeftMargin;

    private /*final*/ CssBoxMargin mRightMargin;

    private /*final*/ CssBoxMargin mTopMargin;

    private /*final*/ CssBoxMargin mBottomMargin;

    private /*final*/ boolean mIsInsideTableCell;

    private /*final*/ boolean mCanContainImplicitTextBoxes;

}

