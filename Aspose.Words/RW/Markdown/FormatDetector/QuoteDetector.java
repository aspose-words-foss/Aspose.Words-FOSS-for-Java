// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// The markdown Quote detector.
/// </summary>
class QuoteDetector extends MarkdownDetectorBase
{
    /// <summary>
    /// Returns Quote level from a specified line of text.
    /// </summary>
    static int getQuoteLevel(String txtLine)
    {
        int startIdx = txtLine.indexOf('>');
        if (startIdx == -1)
            return -1;

        int level = 0;
        while (((startIdx + level) < txtLine.length()) && txtLine.charAt(startIdx + level) == '>')
            level++;

        return level;
    }

    /// <summary>
    /// Detects a Quote block in a specified context.
    /// </summary>
    protected /*override*/ boolean detect(MarkdownDetectorContext context)
    {
        if (context.isPrevParaBroken())
            mQuoteLevel = -1;

        Match match = mQuoteRegex.match(context.getLine());
        if (match.getSuccess())
        {
            int prevQuoteLevel = mQuoteLevel;
            mQuoteLevel = getQuoteLevel(match.getValue());

            if (mQuoteLevel > prevQuoteLevel)
            {
                setCount(getCount() + 1)/*Property++*/;
                return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Gets markdown feature significance of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureSignificance*/int getSignificance() { return MarkdownFeatureSignificance.MEDIUM; }

    /// <summary>
    /// Gets markdown feature type of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureType*/int getType() { return MarkdownFeatureType.QUOTE; }

    /// <summary>
    /// The regex that matches > with up to three whitespace characters before it.
    /// </summary>
    private /*final*/ Regex mQuoteRegex = new Regex("^[ ]{0,3}>", RegexOptions.COMPILED);

    /// <summary>
    /// The last processed Quote level.
    /// </summary>
    private int mQuoteLevel;
}

