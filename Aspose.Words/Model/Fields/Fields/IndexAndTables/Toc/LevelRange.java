// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Designates a range of levels, for example 1 to 9.
/// </summary>
class LevelRange
{
    private LevelRange(int min, int max)
    {
        mMin = min;
        mMax = max;
    }

    /// <summary>
    /// Attempts to parse range values from the specified string of type "startLevel-endLevel".
    /// </summary>
    static LevelRange tryParse(String text)
    {
        if (!StringUtil.hasChars(text))
            return null;

        Match match = G_RANGE_REGEX.match(text);

        if (!match.getSuccess())
            return null;

        int min = FormatterPal.parseInt(match.getGroups().get(MIN_LEVEL_GROUP).getValue());
        int max = FormatterPal.parseInt(match.getGroups().get(MAX_LEVEL_GROUP).getValue());

        // Invalid range?
        if (min > max)
            return null;

        // Invalid level?
        if (!isLevelValid(min) || !isLevelValid(max))
            return null;

        return new LevelRange(min, max);
    }

    boolean isInRange(int level)
    {
        return (level >= getMin()) && (level <= getMax());
    }

    static boolean isLevelValid(int level)
    {
        return (level >= MIN_LEVEL) && (level <= MAX_LEVEL);
    }

    int getMin() { return mMin; };

    private int mMin;

    int getMax() { return mMax; };

    private int mMax;

    static final int MIN_LEVEL = 1;
    static final int MAX_LEVEL = 9;

    static /*final*/ LevelRange MAX_RANGE = new LevelRange(MIN_LEVEL, MAX_LEVEL);
    static /*final*/ LevelRange EMPTY_RANGE = new LevelRange(MAX_LEVEL, MIN_LEVEL);

    static final int INVALID_LEVEL = -1;

    //JAVA lacks named groups, let's use numbered ones.
    private static final int MIN_LEVEL_GROUP = 1;
    private static final int MAX_LEVEL_GROUP = 2;
    private static /*final*/ Regex G_RANGE_REGEX = new Regex("\\A\\D*(\\d)\\D+(\\d)\\D*\\z", RegexOptions.COMPILED);
    static final String PARSING_ERROR_MESSAGE = "Error! Not a valid heading level range.";
}

