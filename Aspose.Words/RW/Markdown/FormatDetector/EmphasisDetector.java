// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msChar;
import com.aspose.StringUtil;

/// <summary>
/// The markdown Emphasis detector.
/// </summary>
class EmphasisDetector extends MarkdownDetectorBase
{
    /// <summary>
    /// Detects markdown inline blocks, such as emphases and code spans.
    /// </summary>
    protected /*override*/ boolean detect(MarkdownDetectorContext context)
    {
        if (context.isPrevParaBroken())
            reset();

        char prevChar = '\u0000';
        for (int i = 0; i < context.getLine().length(); i++)
        {
            char curChar = context.getLine().charAt(i);
            if (curChar == prevChar)
                continue;

            switch (curChar)
            {
                case '`':
                {
                    // If it was opened already, then now we encounter a closing sequence
                    // and should count it as completed code span.
                    if (mIsBacktick)
                        setCount(getCount() + 1)/*Property++*/;

                    mIsBacktick = !mIsBacktick;
                    break;
                }
                case '~':
                {
                    // If it was opened already, then now we encounter a closing sequence
                    // and should count it as completed strike-through.
                    if (mIsStrikethrough)
                        setCount(getCount() + 1)/*Property++*/;

                    mIsStrikethrough = !mIsStrikethrough;
                    break;
                }
                case '*':
                {
                    // Exclude emphasis that is more likely is a wildcard in a path.
                    if (((prevChar == '\\') || (prevChar == '/')) &&
                        ((i + 1) < context.getLine().length()) && (context.getLine().charAt(i + 1) == '.'))
                        break;

                    // If it was opened already, then now we encounter a closing sequence
                    // and should count it as completed emphasis.
                    if (mIsAsterisk)
                    {
                        // WORDSNET-26035 To be a closing sequence of emphasis there at least should not
                        // be any whitespace characters just before this sequence.
                        if (!msChar.isWhiteSpace(prevChar))
                        {
                            // If length of closing sequence is greater than 2,
                            // then we treat it as two different emphases (bold + italic).
                            int end = i;
                            while ((end < context.getLine().length()) && (context.getLine().charAt(end) == '*'))
                                end++;
                            int emphasesCount = ((end - i) > 2) ? 2 : 1;
                            setCount(getCount() + emphasesCount);

                            mIsAsterisk = false;
                        }

                    }
                    else
                    {
                        // WORDSNET-26035 To be an opening sequence of emphasis there at least should not
                        // be any whitespace characters just after this sequence.
                        if ((i + 1) < context.getLine().length())
                        {
                            char nextChar = context.getLine().charAt(i + 1);
                            if (!msChar.isWhiteSpace(nextChar))
                                mIsAsterisk = true;
                        }
                    }

                    break;
                }
                case '_':
                {
                    // A sequence of '_' is not allowed inside words to be emphasis by spec.
                    int nextCharIdx = StringUtil.indexOfNotEqualTo(context.getLine(), '_', (i + 1));
                    char nextChar = (nextCharIdx == -1) ? '\u0000' : context.getLine().charAt(nextCharIdx);
                    if(MarkdownUtil.isIntraword(prevChar, nextChar))
                        break;

                    // If it was opened already, then now we encounter a closing sequence
                    // and should count it as completed emphasis.
                    if (mIsUnderscore)
                    {
                        // If length of closing sequence is greater than 2,
                        // then we treat it as two different emphases (bold + italic).
                        if (nextCharIdx == -1)
                            nextCharIdx = context.getLine().length();
                        int emphasesCount = ((nextCharIdx - i) > 2) ? 2 : 1;
                        setCount(getCount() + emphasesCount);
                    }

                    mIsUnderscore = !mIsUnderscore;
                    break;
                }
                default:
                    // Do nothing by default.
                    break;
            }

            prevChar = curChar;
        }

        return (getCount() > 0);
    }

    /// <summary>
    /// Gets markdown feature significance of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureSignificance*/int getSignificance() { return MarkdownFeatureSignificance.LOW; }

    /// <summary>
    /// Gets markdown feature type of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureType*/int getType() { return MarkdownFeatureType.EMPHASIS; }

    /// <summary>
    /// Resets accumulated states of all found features.
    /// </summary>
    private void reset()
    {
        mIsAsterisk = false;
        mIsUnderscore = false;
        mIsBacktick = false;
        mIsStrikethrough = false;
    }

    /// <summary>
    /// Keeps state of found inline feature.
    /// </summary>
    private boolean mIsAsterisk;
    private boolean mIsUnderscore;
    private boolean mIsBacktick;
    private boolean mIsStrikethrough;
}

