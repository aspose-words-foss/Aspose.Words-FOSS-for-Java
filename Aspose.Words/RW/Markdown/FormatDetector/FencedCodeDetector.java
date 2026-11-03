// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// The markdown FencedCode block detector.
/// </summary>
/// <remarks>
/// The FencedCode block starts and ends with ``` or ~~~.
/// </remarks>
class FencedCodeDetector extends MarkdownDetectorBase
{
    /// <summary>
    /// Detects a FencedCode block in a specified context.
    /// </summary>
    protected /*override*/ boolean detect(MarkdownDetectorContext context) throws Exception
    {
        if (mIsFencedBlockStarted)
        {
            // Search for the FencedCode end.
            if (mFencedCodeEndRegex.isMatch(context.getLine()) || context.getEndOfFile())
            {
                setCount(getCount() + 1)/*Property++*/;
                mIsFencedBlockStarted = false;
                return true;
            }
        }
        else
        {
            mIsFencedBlockStarted = mFencedCodeStartRegex.isMatch(context.getLine());

            // Check if FencedCode block is closed by EOF.
            if (mIsFencedBlockStarted && context.getEndOfFile())
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
    protected /*override*/ /*MarkdownFeatureSignificance*/int getSignificance() { return MarkdownFeatureSignificance.BIG; }

    /// <summary>
    /// Gets markdown feature type of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureType*/int getType() { return MarkdownFeatureType.FENCED_CODE; }

    /// <summary>
    /// Indicates whether FencedCode block has been started.
    /// </summary>
    private boolean mIsFencedBlockStarted;

    /// <summary>
    /// The regex for matching FencedCode block start.
    /// </summary>
    private /*final*/ Regex mFencedCodeStartRegex = new Regex("(^\\s{0,3}(>\\s{0,4})?(([`]{3,}(?!`))|[~]{3,}))",
        RegexOptions.COMPILED);

    /// <summary>
    /// The regex for matching FencedCode block end.
    /// </summary>
    private /*final*/ Regex mFencedCodeEndRegex = new Regex("(^\\s{0,3}(>\\s{0,4})?([`]{3,}|[~]{3,})$)",
        RegexOptions.COMPILED);
}

