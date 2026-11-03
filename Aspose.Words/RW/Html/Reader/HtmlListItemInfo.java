// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/05/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Superclass for storing and calculating information is required for correct indentation of list items.
/// </summary>
abstract class HtmlListItemInfo
{
    protected HtmlListItemInfo(
        Paragraph paragraph,
        CssLength wholeListLeftIndent,
        CssLength leftIndent,
        CssLength padding,
        CssLength markerToTextDistance,
        boolean isMarkerPositionInside,
        boolean isListItemNoneListStyle)
    {
        Debug.assertTrue(paragraph != null);
        Debug.assertTrue(wholeListLeftIndent != null);
        Debug.assertTrue(leftIndent != null);
        Debug.assertTrue(padding != null);
        Debug.assertTrue(markerToTextDistance != null);

        mParagraph = paragraph;
        mWholeListLeftIndent = wholeListLeftIndent;
        mLeftIndent = leftIndent;
        mPadding = padding;
        mMarkerToTextDistance = markerToTextDistance;

        mIsMarkerPositionInside = isMarkerPositionInside;
        mIsListItemNoneListStyle = isListItemNoneListStyle;
    }

    /// <summary>
    /// Calculates first line indent of list item.
    /// </summary>
    CssLength getFirstLineIndent(CssLength leftIndent, CssLength textIndent)
    {
        return (isMarkerPositionInside())
            ? textIndent
            : getFirstLineIndentForOutsidePosition(leftIndent);
    }

    /// <summary>
    /// Calculates left indent of list item.
    /// </summary>
    /*virtual*/ CssLength getLeftIndent(CssLength leftIndent, CssLength textIndent)
    {
        return CssLength.op_Addition(leftIndent, getWholeListLeftIndent());
    }

    /// <summary>
    /// Calculates first line indent for outside postion of list item marker.
    /// </summary>
    protected CssLength getFirstLineIndentForOutsidePosition(CssLength leftIndent)
    {
        return CssLength.op_UnaryNegation((CssLength.op_Addition(CssLength.op_Addition(getMarkerToTextDistance(), getPadding()), (CssLength.op_Subtraction(leftIndent, getLeftIndent())))));
    }
    
    Paragraph getParagraph() { return mParagraph; };

    private Paragraph mParagraph;

    CssLength getLeftIndent() { return mLeftIndent; };

    private CssLength mLeftIndent;

    boolean isListItemNoneListStyle() { return mIsListItemNoneListStyle; };

    private boolean mIsListItemNoneListStyle;

    protected CssLength getWholeListLeftIndent() { return mWholeListLeftIndent; };

    private CssLength mWholeListLeftIndent;

    protected CssLength getPadding() { return mPadding; };

    private CssLength mPadding;

    protected CssLength getMarkerToTextDistance() { return mMarkerToTextDistance; };

    private CssLength mMarkerToTextDistance;

    protected boolean isMarkerPositionInside() { return mIsMarkerPositionInside; };

    private  boolean mIsMarkerPositionInside;

    /// <summary>
    /// Calculates size of tab stop between list item marker and text.
    /// </summary>
    /// <returns>
    /// Returns tab stop size as <see cref="CssLength"/> or <c>null</c> if tab stop is not needed.
    /// </returns>
    abstract CssLength getTabStop(CssLength leftIndent, CssLength textIndent, ListLevel listLevel);
   
}

