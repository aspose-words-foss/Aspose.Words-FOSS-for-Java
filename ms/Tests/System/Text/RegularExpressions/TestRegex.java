// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2007 by Konstantin Sidorenko
// 2016/04/28 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import org.testng.annotations.DataProvider;


@Test
public class TestRegex
{
    /// <summary>
    /// Null internal Regex should be returned as null for public api.
    /// </summary>
    @Test
    public void testGetNullRegex()
    {
        Regex regex = null;
        Assert.assertNull(Regex.toJava(regex));
    }

    @Test
    public void testRegexCtor()
    {
        Regex regex = new Regex("regex");
        msAssert.areEqual(regex.toJava().pattern(), "regex");
    }

    @Test
    public void testJava2238()
    {
        String input = "1";

        msAssert.areEqual(1, Regex.match(input, "^(1) (?:(2)(3))?$").getGroups().getCount());
        msAssert.areEqual(4, Regex.match(input, "^(1)(?:(2)(3))?$").getGroups().getCount());
        msAssert.areEqual(3, Regex.match(input, "^(?:1)(?:(2)(3))?$").getGroups().getCount());
    }

    @Test
    public void testRegexEscape()
    {
        String str = "\\,*,+,?,|,{,[,(,),^,$,.,#,and white space ";
        String escaped = "\\\\,\\*,\\+,\\?,\\|,\\{,\\[,\\(,\\),\\^,\\$,\\.,\\#,and\\ white\\ space\\ ";
        msAssert.areEqual(Regex.escape(str), escaped);

        msAssert.areEqual(Regex.escape(""), "");
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testRegexEscapeNull()
    {
        msAssert.areEqual(Regex.escape(null), "");
    }

    @Test
    public void testIsMatch()
    {
        String regex = "\\<html";
        String input = "..<html>aaaa</html>";

        boolean match = Regex.isMatch(input, regex, RegexOptions.IGNORE_CASE);
        Debug.writeLine("match = " + match);
    }

    @Test
    public void testEscapeWorksWithUnicode()
    {
        String regexStr = "\\w+";
        String input = "Лозунг";

        Regex regex = new Regex(regexStr);
        Match match = regex.match(input);
        String value = match.getValue();
        msAssert.areEqual(value, input);
    }

    @Test
    public void testSplit()
    {
        String regexStr = "\\s";
        String[] words;
        Regex regex = new Regex(regexStr);
        words = regex.split(" 2018-08-15 00:00:00", 0);
        msAssert.areEqual(words[0], "");
        msAssert.areEqual(words[1], "2018-08-15");
        msAssert.areEqual(words[2], "00:00:00");

        words = regex.split(" 2018-08-15 00:00:00", 0, 1);
        msAssert.areEqual(words[0], " 2018-08-15");
        msAssert.areEqual(words[1], "00:00:00");

        words = regex.split(" 2018- 08- 15 00:00:00", 0, 1);
        msAssert.areEqual(words[0], " 2018-");
        msAssert.areEqual(words[1], "08-");
        msAssert.areEqual(words[2], "15");
        msAssert.areEqual(words[3], "00:00:00");
    }

    /// <summary>
    /// Complex test how Regex and Match emulate .Net's System.Text.RegularExpressions.Regex and
    /// System.Text.RegularExpressions.Match.
    /// </summary>
    @Test
    public void testMatch()
    {
        Regex regex = new Regex(pattern);

        for (int i = 0; i < fieldcodes.length; i++)
        {
            Match match = regex.match(fieldcodes[i]);

            //0 group: the whole input string for matched regex or "" if regex match failed (java throw)
            String group0 = match.getGroups().get(0).getValue();
            msAssert.areEqual(group0, result[i][0]);

            //optional group: "" for non-matched (in java - null)
            String group1 = match.getGroups().get(1).getValue();
            msAssert.areEqual(group1, result[i][1]);

            //ordinal capturung group: "" for non-matched group (i.e. if regex match failed)
            String group2 = match.getGroups().get(2).getValue();
            msAssert.areEqual(group2, result[i][2]);

            //out of index group (regex have only 2 groups) - in java throws
            String group3 = match.getGroups().get(3).getValue();
            msAssert.areEqual(group3, result[i][3]);

            //negative index - in java throws
            String groupNegative = match.getGroups().get(-33).getValue();
            msAssert.areEqual(groupNegative, result[i][4]);
        }
    }

    /// <summary>
    /// Some greedy patterns, for example <tt>a*</tt> or <tt>[^a]*</tt>, match the empty string.
    /// When used in Regex.matches() them infinitely return this empty group.
    /// </summary>
    @Test
    public void testGreedy()
    {
        //was: matcher's start == end and both equl the end of text string (14).
        String pattern = "([^a]*)";
        String text = " FORMCHECKBOX ";
        Regex regex = new Regex(pattern);
        MatchCollection matchers = regex.matches(text); //infinite loop was here.

        //was: matcher's start == end and both equl the start of text string (0).
        pattern = "a*";
        regex = new Regex(pattern);
        matchers = regex.matches(text); //infinite loop was here.
    }

    /// <summary>
    /// WORDSJAVA-1422
    /// Tests if Java can handle not escaped {} in case they are not repetition expression.
    /// </summary>
    @Test (dataProvider = "testRepetitionDataProvider")
    // Java must escape { when the expression {} is not a repetition one. 
    // It is enought to escape just open curve brace.
    public void testRepetition(String actual, String expected)
    {
        Regex normalized = new Regex(actual);
        msAssert.areEqual(expected, normalized.toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRepetitionDataProvider")
	public static Object[][] testRepetitionDataProvider()
	{
		return new Object[][]
		{
			{"\\{([^}]*.?)}",  "\\{([^}]*.?)}"},
			{"^(([^\\.]+)?\\.)?(\\$?([A-Z]+)\\$?(\\d+)$)",  "^(([^\\.]+)?\\.)?(\\$?([A-Z]+)\\$?(\\d+)$)"},
			{"[0-9]{1,4}",  "[0-9]{1,4}"},
			{"[0-9]{5}",  "[0-9]{5}"},
			{"{[^}]*}",  "\\{[^}]*}"},
			{"\\p{IsL}",  "\\p{IsL}"},
			{"[0-9]{5m}",  "[0-9]\\{5m}"},
		};
	}

    private String pattern =
        "\\S+" + //one or more non spaces HYPERLINK or other word in other languages
        "\\s+" + //one or more spaces
        "(?:\"\"\\s+)?" + //non capturing optional "" and one or more spaces, found in one of the customers files.
        "(\\\\l\\s+)?" + //optional \l flag followed by one or more spaces
        "\"" + //one apostrophe
        "([^\"]+)" + //one or more chars except apostrophe (destination)
        "\""; //one closing apostrophe

    private static String[] fieldcodes =
    {
        "HYPERLINK mailto:a.shearer01@hotmail.com",
        "HYPERLINK \"mailto:a.shearer01@hotmail.com\"",
        "HYPERLINK \"\" \"mailto:a.shearer01@hotmail.com\"",
        "HYPERLINK \\l \"mailto:a.shearer01@hotmail.com\""
    };

    private String[][] result =
    {
        new String[]{"", "", "", "", ""},
        new String[]{fieldcodes[1], "", "mailto:a.shearer01@hotmail.com", "", ""},
        new String[]{fieldcodes[2], "", "mailto:a.shearer01@hotmail.com", "", ""},
        new String[]{fieldcodes[3], "\\l ", "mailto:a.shearer01@hotmail.com", "", ""}
    };
}

