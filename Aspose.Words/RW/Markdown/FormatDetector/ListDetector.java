// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// The markdown List detector.
/// </summary>
/// <remarks>
/// List is a line with special character +, -, * or with digit at the beginning.
/// </remarks>
class ListDetector extends MarkdownDetectorBase
{
    /// <summary>
    /// Detects List block in a specified context.
    /// </summary>
    protected /*override*/ boolean detect(MarkdownDetectorContext context)
    {
        if ((context.getPrevLineDetectedFeatures() == null) || MarkdownDetectorContext.canBreakParagraph(context.getPrevLineDetectedFeatures()))
            mIsListStarted = false;

        Match match = mListRegex.match(context.getLine());
        if (match.getSuccess())
        {
            int prevQuoteLevel = mQuoteLevel;
            mQuoteLevel = QuoteDetector.getQuoteLevel(match.getValue());

            char prevListMarker = mListMarker;
            mListMarker = getListMarker(match.getValue());
            
            // If it is not a continuation of a previous List, then start a new one.
            if (!mIsListStarted)
            {
                setCount(getCount() + 1)/*Property++*/;
                mIsListStarted = true;
                return true;
            }

            // If previous and current Lists are inside Quotes of different levels,
            // then we need to start a new List.
            if (prevQuoteLevel != mQuoteLevel)
            {
                setCount(getCount() + 1)/*Property++*/;
                return true;
            }

            // Changing the bullet or ordered list marker starts a new list.
            if (prevListMarker != mListMarker)
            {
                setCount(getCount() + 1)/*Property++*/;
                return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Returns list marker for a specified list string.
    /// </summary>
    private static char getListMarker(String list)
    {
        Debug.assertTrue(StringUtil.hasChars(list));

        int markerIdx = msString.indexOfAny(list, new char[] {'*', '-', '+'});
        // If none of markers for unordered lists were found, then we have an ordered list that starts from a digit.
        if (markerIdx == -1)
            return '1';

        return list.charAt(markerIdx);
    }

    /// <summary>
    /// Gets markdown feature significance of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureSignificance*/int getSignificance() { return MarkdownFeatureSignificance.MEDIUM; }

    /// <summary>
    /// Gets markdown feature type of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureType*/int getType() { return MarkdownFeatureType.LIST; }

    /// <summary>
    /// Indicates whether a List block is started.
    /// </summary>
    private boolean mIsListStarted;

    /// <summary>
    /// A marker of the current List.
    /// </summary>
    private char mListMarker;

    /// <summary>
    /// A level of the Quote where List block is located (if any).
    /// </summary>
    private int mQuoteLevel;

    /// <summary>
    /// The regex that matches a markdown List block.
    /// </summary>
    private /*final*/ Regex mListRegex = new Regex("(^(\\s{0,3}([>]+\\s{0,4})?)?) ((\\d+\\.|[*+-])$ | (\\d+\\.|[*+-])\\s+)",
        RegexOptions.COMPILED | RegexOptions.IGNORE_PATTERN_WHITESPACE);
}

