// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.StringToIntDictionary;
import com.aspose.Debug;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.HashSetGeneric;


/// <summary>
/// The markdown Link detector. 
/// </summary>
class LinkDetector extends MarkdownDetectorBase
{
    /// <summary>
    /// Detects a Link block in a specified context.
    /// </summary>
    protected /*override*/ boolean detect(MarkdownDetectorContext context)
    {
        int linksCount = 0;
        for (Match match : (Iterable<Match>) mLinkRegex.matches(context.getLine()))
        {
            if (msString.endsWith(match.getValue(), ")", StringComparison.ORDINAL))
            {
                // This is Inline Link, so just count it.
                linksCount++;
            }
            else
            {
                String linkDestination = getLinkDestination(match.getValue());
                
                int count = mLinkLabels.get(linkDestination);
                
                if (msString.endsWith(match.getValue(), ">", StringComparison.ORDINAL))
                {
                    // If we encounter a link definition, then add it and check accumulated link labels.
                    mLinkDefs.add(linkDestination);

                    if (!StringToIntDictionary.isNullSubstitute(count))
                    {
                        // Now we have a corresponding link definition.
                        // So, if there are link labels with such destination, then count them.
                        linksCount += count;
                        mLinkLabels.set(linkDestination, 0);
                    }
                }
                else
                {
                    // If we encounter a link label, then count it if we have a corresponding link definition.
                    // Otherwise accumulate it for further proceeding.
                    if (mLinkDefs.contains(linkDestination))
                        linksCount++;
                    else
                        mLinkLabels.set(linkDestination, StringToIntDictionary.isNullSubstitute(count) ? 1 : (count + 1));
                }
            }
        }

        setCount(getCount() + linksCount);

        return (linksCount > 0);
    }

    /// <summary>
    /// Gets a link destination substring from a specified link.
    /// </summary>
    private static String getLinkDestination(String link)
    {
        // Link destination should be inside a last [].
        int startIdx = link.lastIndexOf('[');
        int endIdx = link.lastIndexOf(']');

        Debug.assertTrue((startIdx != -1) && (endIdx != -1));

        // Strip '[' and ']' characters.
        String linkDestination = link.substring((startIdx + 1), (startIdx + 1) + (endIdx - startIdx - 1));

        // Normalize by trimming whitespace characters and converting it to a lower case.
        return msString.toLowerInvariant(msString.trim(linkDestination));
    }

    /// <summary>
    /// Gets markdown feature significance of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureSignificance*/int getSignificance() { return MarkdownFeatureSignificance.BIG; }

    /// <summary>
    /// Gets markdown feature type of the detector.
    /// </summary>
    protected /*override*/ /*MarkdownFeatureType*/int getType() { return  MarkdownFeatureType.LINK; }

    /// <summary>
    /// The regex for all types of links (inline, full reference, shortcut).
    /// </summary>
    /// <remarks>
    /// It matches the following:
    /// []() - Inline links
    /// [][] - Full links | [] - Shortcut links 
    /// []:&lt;&gt; - Link destination.
    /// </remarks>
    private /*final*/ Regex mLinkRegex = new Regex(
        "\\[[^\\[\\]]+\\]\\([^\\(\\)]+\\) |  \r\n              (\\[[^\\[\\]]+\\]){1,2}(?!:) |\r\n              (\\[[^\\[\\]]+\\]):\\<.*\\>", 
        RegexOptions.COMPILED | RegexOptions.IGNORE_PATTERN_WHITESPACE);

    /// <summary>
    /// The dictionary with link labels and their counts.
    /// </summary>
    private /*final*/ StringToIntDictionary mLinkLabels = new StringToIntDictionary();

    /// <summary>
    /// The hashset with link definitions.
    /// </summary>
    private /*final*/ HashSetGeneric<String> mLinkDefs = new HashSetGeneric<String>();

}

