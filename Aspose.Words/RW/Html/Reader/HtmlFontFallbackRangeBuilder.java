// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/12/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Helps to group text fragments into an array <see cref="HtmlFontFallbackRange"/> so that no adjacent ranges in that array
/// have the same fallback font name.
/// </summary>
class HtmlFontFallbackRangeBuilder
{
    /// <summary>
    /// Appends a text fragment to font fallback ranges.
    /// </summary>
    /// <param name="text">A text fragment.</param>
    /// <param name="fallbackFontName">
    /// Name of the fallback font that should be used to render the text. May be <c>null</c> if no font fallback is required.
    /// </param>
    /// <remarks>
    /// Adjacent text fragments are concatenated if they have same fallback font name.
    /// </remarks>
    void append(String text, String fallbackFontName)
    {
        if (!msString.equals(mCurrentRangeFallbackFontName, fallbackFontName))
        {
            closeCurrentRange();
        }
        msStringBuilder.append(mCurrentRangeText, text);
        mCurrentRangeFallbackFontName = fallbackFontName;
    }

    /// <summary>
    /// Gets font fallback ranges that were accumulated by the builder.
    /// </summary>
    HtmlFontFallbackRange[] getRanges()
    {
        closeCurrentRange();
        return msArrayList.toArray(mRanges, new HtmlFontFallbackRange[0]);
    }

    private void closeCurrentRange()
    {
        if (mCurrentRangeText.length() > 0)
        {
            msArrayList.add(mRanges, new HtmlFontFallbackRange(mCurrentRangeText.toString(), mCurrentRangeFallbackFontName));
            mCurrentRangeText.setLength(0);
        }
    }

    private /*final*/ ArrayList<HtmlFontFallbackRange> mRanges = new ArrayList<HtmlFontFallbackRange>();
    private /*final*/ StringBuilder mCurrentRangeText = new StringBuilder();
    private String mCurrentRangeFallbackFontName;
}

