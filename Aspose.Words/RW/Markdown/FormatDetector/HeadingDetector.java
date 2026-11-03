// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// The markdown Heading detector.
/// </summary>
class HeadingDetector extends MarkdownDetectorBase
{
    /// <summary>
    /// Detects Heading block in a specified context.
    /// </summary>
    protected /*override*/ boolean detect(MarkdownDetectorContext context)
    {
        // Check for AtxHeading.
        if (mAtxHeadingRegex.isMatch(context.getLine()))
        {
            setCount(getCount() + 1)/*Property++*/;
            return true;
        }

        // Check for SetextHeading.
        // If previous line contains features that breaks paragraph, then current line cannot be a SetextHeading.
        if (!context.isPrevParaBroken() && mSetextHeadingRegex.isMatch(context.getLine()))
        {
            setCount(getCount() + 1)/*Property++*/;
            return true;
        }

        return false;
    }

    /// <summary>
    /// Gets markdown feature significance of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureSignificance*/int getSignificance() { return MarkdownFeatureSignificance.LOW; }

    /// <summary>
    /// Gets markdown feature type of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureType*/int getType() { return MarkdownFeatureType.HEADING; }

    /// <summary>
    /// The regex for matching SetextHeading blocks.
    /// </summary>
    private /*final*/ Regex mSetextHeadingRegex = new Regex("^[ >]*(=+|-+)\\s*$", RegexOptions.COMPILED);
    
    /// <summary>
    /// The regex for matching AtxHeading blocks.
    /// </summary>
    private /*final*/ Regex mAtxHeadingRegex = new Regex("^[ >]*\\#{1,6}\\s.*$", RegexOptions.COMPILED);
}

