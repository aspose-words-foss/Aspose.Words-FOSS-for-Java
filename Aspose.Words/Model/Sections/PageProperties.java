// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2018 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents document page properties that are required to calculate page margin and content size.
/// </summary>
class PageProperties
{
    /// <summary>
    /// Gets or sets the left margin of the page.
    /// </summary>
    int getLeftMargin() { return mLeftMargin; }
    void setLeftMargin(int value) { mLeftMargin = value; }

    /// <summary>
    /// Gets or sets the right margin of the page.
    /// </summary>
    int getRightMargin() { return mRightMargin; }
    void setRightMargin(int value) { mRightMargin = value; }

    /// <summary>
    /// Gets or sets the top margin of the page.
    /// </summary>
    int getTopMargin() { return mTopMargin; }
    void setTopMargin(int value) { mTopMargin = value; }

    /// <summary>
    /// Gets or sets the bottom margin of the page.
    /// </summary>
    int getBottomMargin() { return mBottomMargin; }
    void setBottomMargin(int value) { mBottomMargin = value; }

    /// <summary>
    /// Gets or sets the amount of extra space added to the margin for document binding.
    /// </summary>
    int getGutter() { return mGutter; }
    void setGutter(int value) { mGutter = value; }

    /// <summary>
    /// Gets or sets a flag indicating whether the document has the <see cref="DocPr.GutterAtTop"/>
    /// property set or not.
    /// </summary>
    boolean isGutterAtTopSet() { return mIsGutterAtTopSet; }
    void isGutterAtTopSet(boolean value) { mIsGutterAtTopSet = value; }

    /// <summary>
    /// Gets or sets whether Microsoft Word uses gutters for the section based on a right-to-left language
    /// or a left-to-right language.
    /// </summary>
    boolean isRtlGutter() { return mIsRtlGutter; }
    void isRtlGutter(boolean value) { mIsRtlGutter = value; }

    /// <summary>
    /// Gets or sets a value indicating what kind of swapping left and right margins on facing pages
    /// for multiple page documents is used.
    /// </summary>
    /*MultiplePagesType*/int getMultiplePages() { return mMultiplePages; }
    void setMultiplePages(/*MultiplePagesType*/int value) { mMultiplePages = value; }

    /// <summary>
    /// Gets or sets the orientation of the page.
    /// </summary>
    /*Orientation*/int getOrientation() { return mOrientation; }
    void setOrientation(/*Orientation*/int value) { mOrientation = value; }

    /// <summary>
    /// Gets or sets a flag indicating whether this page has the same orientation as the first section.
    /// </summary>
    boolean getOrientationMatchesFirstSection() { return mOrientationMatchesFirstSection; }
    void setOrientationMatchesFirstSection(boolean value) { mOrientationMatchesFirstSection = value; }

    /// <summary>
    /// Gets or sets a flag indicating whether odd and even pages have different headers and footers.
    /// </summary>
    boolean getDifferentOddEvenPageHeader() { return mDifferentOddEvenPageHeader; }
    void setDifferentOddEvenPageHeader(boolean value) { mDifferentOddEvenPageHeader = value; }

    private int mLeftMargin;
    private int mRightMargin;
    private int mTopMargin;
    private int mBottomMargin;
    private int mGutter;
    private boolean mIsGutterAtTopSet;
    private boolean mIsRtlGutter;
    private /*MultiplePagesType*/int mMultiplePages;
    private /*Orientation*/int mOrientation = Orientation.PORTRAIT;
    private boolean mOrientationMatchesFirstSection = true;
    private boolean mDifferentOddEvenPageHeader;
}

