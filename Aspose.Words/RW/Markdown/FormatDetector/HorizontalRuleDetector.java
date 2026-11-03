// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/04/2019 by Denis Panov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// The markdown HorizontalRule detector.
/// </summary>
class HorizontalRuleDetector extends MarkdownDetectorBase
{
    /// <summary>
    /// Detects HorizontalRule block in a specified context.
    /// </summary>
    protected /*override*/ boolean detect(MarkdownDetectorContext context)
    {
        // There cannot be a HorizontalRule inside a Heading.
        if (context.getCurLineDetectedFeatures().contains(MarkdownFeatureType.HEADING))
            return false;

        if (mHorizontalRuleRegex.isMatch(context.getLine()))
        {
            setCount(getCount() + 1)/*Property++*/;
            return true;
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
    protected /*override*/ /*MarkdownFeatureType*/int getType() { return MarkdownFeatureType.HORIZONTAL_RULE; }

    /// <summary>
    /// The regex for matching HorizontalRule blocks.
    /// </summary>
    private /*final*/ Regex mHorizontalRuleRegex = new Regex(
        "^[ ]{0,3}" +       // Leading spaces at the beginning
            "([-*_])" +     // $1: First marker
            "(?>" +         // Repeated marker group
               "[ ]{0,2}" + // Zero, one, or two spaces.
                "\\1" +     // Marker character
            "){2,}" +       // Group repeated at least twice
            "[ ]*" +        // Trailing spaces
        "$"                 // End of line.
        , RegexOptions.MULTILINE | RegexOptions.COMPILED);
}

