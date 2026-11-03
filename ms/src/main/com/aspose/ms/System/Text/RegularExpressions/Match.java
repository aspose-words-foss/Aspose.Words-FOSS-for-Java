package com.aspose.ms.System.Text.RegularExpressions;

/**
 * Copyright Aspose.com, 2006
 * Helper Java code to port Aspose.Word C# source, Jan 10, 2007
 *
 * @author Konstantin Sidorenko
 */

import com.aspose.ms.System.msString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simplified copycat of .Net System.Text.RegularExpressions.Match.
 * Backed by java.util.regex.Matcher.
 * <p>
 * Only methods used by Aspose.Words are implemented.
 * <p>
 * Note:
 */
public class Match
{
    /**
     * Do not use this ctor publicly.
     * <p>
     * Used solely by com.aspose.ms.System.Text.RegularExpressions.Regex.match(String) to emulate .Net Regex.Match(string).
     */
    Match(Pattern pattern)
    {
        mMatcher = pattern.matcher(msString.Empty);
    }

    /**
     * Do not use this ctor publicly.
     * <p>
     * Used solely by com.aspose.ms.System.Text.RegularExpressions.Regex.match(String, int) to emulate .Net Regex.Match(string, int).
     */
    Match(Pattern pattern, String input, int startAt)
    {
        mMatcher = pattern.matcher(input);
        mMatcher.region(startAt, input.length());
        mSuccess = mMatcher.find();
    }

    public Match(Matcher javaMatcher)
    {
        mMatcher = javaMatcher;
        mSuccess = mMatcher.find();
    }

    public Match find(String input)
    {
        mMatcher.reset(input);
        mSuccess = mMatcher.find();
        return this;
    }

    public Match find(String input, int startAt)
    {
        mMatcher.reset(input);
        mMatcher.region(startAt, input.length());
        mSuccess = mMatcher.find();
        return this;
    }

    public Match find(String input, int start, int length)
    {
        mMatcher.reset(input);
        mMatcher.region(start, start + length);
        mSuccess = mMatcher.find();
        return this;
    }

    public Match nextMatch()
    {
        mSuccess = mMatcher.find();
        return this;
    }

    public Match lookAt(String input, int startAt)
    {
        mMatcher.reset(input);
        mMatcher.region(startAt, input.length());
        // The HACK. if we use here mMatcher.find() as in above ctor,
        // the TestHtml.testCssTokenizerPerformance() runs too long.
        mSuccess = mMatcher.lookingAt();
        return this;
    }

    public String replaceAll(String input, String replacement)
    {
        find(input);
        return mMatcher.replaceAll(replacement);
    }

    public String replace(String input, String replacement, int count)
    {
        find(input);
        while (count > 0)
        {
            find(input);
            input = mMatcher.replaceFirst(replacement);
            count--;
        }
        return input;
    }

    /**
     * Emulates Match.Groups[n] indexer.
     */
    public GroupCollection getGroups()
    {
        if (mGroups == null)
            mGroups = new GroupCollection(this);

        return mGroups;
    }

    /**
     * Emulates Match.Success property
     * Gets a value indicating whether the match is successful.
     */
    public boolean getSuccess()
    {
        return mSuccess;
    }

    /**
     * Emulates Match.Index property
     * Returns the position in the original string where the first character of the captured substring was found.
     */
    public int getIndex()
    {
        return mMatcher.start();
    }

    /**
     * Emulates Match.Length property
     * Returns the length of the captured substring.
     */
    public int getLength()
    {
        return mSuccess ? (mMatcher.end() - mMatcher.start()) : 0;
    }

    public String getValue()
    {
        return mSuccess ? mMatcher.group() : "";
    }

    public String result(String replaceWith)
    {
        //FIX WORDSJAVA-2546 - use custom replace() method here
        return mSuccess ? replace(replaceWith) : "";
    }

    @Override
    public String toString()
    {
        // .NET returns captured substring. Let's do the same.
        return getValue();
    }

    public Matcher toJava()
    {
        return mMatcher;
    }

    //JAVA-added

    /**
     * This static method should be used instead of match.toJava() to avoid NullPointerException
     * if match == null. For instance, the method used by ApiChangeMap.
     */
    public static Matcher toJava(Match match)
    {
        return match == null ? null : match.toJava();
    }

    public static Match fromJava(Matcher javaMatcher)
    {
        return new Match(javaMatcher);
    }

    //FIX WORDSJAVA-2546 - custom replace method. This is a copy of java.util.regex.Matcher.replaceFirst()
    //without appendTail() method because appendTail() adds additional excess symbols to the result.
    private String replace(String replacement)
    {
        if (replacement == null)
            throw new NullPointerException("replacement");
        //FIX WORDSJAVA-2455 - remember the start and end positions and use them to better work with multiline input strings
        int startAt = mMatcher.start();
        int endAt = mMatcher.end();
        mMatcher.reset();
        mMatcher.region(startAt, endAt);
        if (!mMatcher.find())
            return mMatcher.toString();
        StringBuffer sb = new StringBuffer();
        mMatcher.appendReplacement(sb, replacement);
        return sb.substring(startAt);
    }

    private Matcher mMatcher;
    private boolean mSuccess;
    private GroupCollection mGroups;
}
