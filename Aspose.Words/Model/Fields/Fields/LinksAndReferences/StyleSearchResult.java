// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/07/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a result of the search performed by <see cref="StyleFinder"/>.
/// </summary>
class StyleSearchResult
{
    /// <summary>
    /// Creates a <see cref="StyleSearchResult"/> instance if the given arguments correspond to a valid capture,
    /// i.e. a paragraph break and/or a run range are captured because they have the target style applied.
    /// Otherwise, returns <c>null</c>.
    /// </summary>
    static StyleSearchResult createIfNeeded(
        Paragraph paragraph,
        int startIndex,
        int endIndex,
        boolean isForwardDirection,
        boolean isParagraphBreakIncluded,
        boolean isParagraphBreakStyleApplied)
    {
        if (isParagraphBreakStyleApplied || isValidIndexRange(startIndex, endIndex))
            return new StyleSearchResult(paragraph, startIndex, endIndex, isForwardDirection, isParagraphBreakIncluded);

        return null;
    }

    static StyleSearchResult createWholeParagraphResult(Paragraph paragraph, boolean isForwardDirection)
    {
        // The whole paragraph is captured. MS Word does not include a paragraph break in this case.
        NodeCollection inlines = StyleFinder.getParagraphInlines(paragraph);
        boolean hasInlines = inlines.getCount() != 0;
        return new StyleSearchResult(
            paragraph,
            hasInlines ? 0 : -1,
            hasInlines ? inlines.getCount() - 1 : 0,
            isForwardDirection,
            false);
    }

    /// <summary>
    /// Returns a value indicating whether the specified index range is valid.
    /// </summary>
    private static boolean isValidIndexRange(int startIndex, int endIndex)
    {
        return ((startIndex >= 0) && (endIndex >= 0));
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    private StyleSearchResult(
        Paragraph paragraph,
        int startIndex,
        int endIndex,
        boolean isForwardDirection,
        boolean isParagraphBreakIncluded)
    {
        mParagraph = paragraph;
        mStartIndex = startIndex;
        mEndIndex = endIndex;
        mIsForwardDirection = isForwardDirection;
        mIsParagraphBreakIncluded = isParagraphBreakIncluded;
    }

    /// <summary>
    /// Gets the paragraph that is captured by this instance.
    /// </summary>
    Paragraph getParagraph() { return mParagraph; };

    private Paragraph mParagraph;

    /// <summary>
    /// Gets the index of the first run in the paragraph (according to <see cref="IsForwardDirection"/>)
    /// that is captured by this instance.
    /// </summary>
    int getStartIndex() { return mStartIndex; };

    private int mStartIndex;

    /// <summary>
    /// Gets the index of the last run in the paragraph (according to <see cref="IsForwardDirection"/>)
    /// that is captured by this instance.
    /// </summary>
    int getEndIndex() { return mEndIndex; };

    private int mEndIndex;

    /// <summary>
    /// Gets a value indicating whether the direction of the used search is forward.
    /// </summary>
    boolean isForwardDirection() { return mIsForwardDirection; };

    private boolean mIsForwardDirection;

    /// <summary>
    /// Gets a value indicating whether the paragraph's break is captured by this instance.
    /// </summary>
    boolean isParagraphBreakIncluded() { return mIsParagraphBreakIncluded; };

    private boolean mIsParagraphBreakIncluded;

    /// <summary>
    /// Gets a value indicating whether a run range is captured by this instance.
    /// </summary>
    boolean hasIndexRange() { return isValidIndexRange(getStartIndex(), getEndIndex()); }
}

