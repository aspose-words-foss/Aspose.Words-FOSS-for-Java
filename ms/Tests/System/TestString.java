// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2007 by Konstantin Sidorenko
// 2016/01/15 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.msString;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.StringSplitOptions;


@Test
public class TestString
{
    @Test
    public void testCompareOrdinal()
    {
        msAssert.areEqual(0, msString.compareOrdinal(".2.", 1, "2", 0, 1));
        msAssert.areEqual(1, msString.compareOrdinal(".2.", ".2"));
        msAssert.areEqual(0, msString.compare(".2.", 1, "2", 0, 1, StringComparison.ORDINAL));
        msAssert.areEqual(1, msString.compare(".2.", ".2", StringComparison.ORDINAL));
        //ignore case
        msAssert.areEqual(0, msString.compare(".a.", 1, "A", 0, 1, StringComparison.ORDINAL_IGNORE_CASE));
        msAssert.areEqual(0, msString.compare(".a.", ".A.", StringComparison.ORDINAL_IGNORE_CASE));
    }

    @Test
    public void testCompare()
    {
        msAssert.areEqual(0, msString.compare(".2.", 1, "2", 0, 1));
        msAssert.areEqual(1, msString.compare(".2.", ".2"));
        msAssert.areEqual(0, msString.compare(".2.", 1, "2", 0, 1, StringComparison.CURRENT_CULTURE));
        msAssert.areEqual(1, msString.compare(".2.", ".2", StringComparison.CURRENT_CULTURE));
        msAssert.areEqual(0, msString.compare(".2.", 1, "2", 0, 1, StringComparison.INVARIANT_CULTURE));
        msAssert.areEqual(1, msString.compare(".2.", ".2", StringComparison.INVARIANT_CULTURE));
        //ignore case
        msAssert.areEqual(0, msString.compare(".a.", 1, "A", 0, 1, true));
        msAssert.areEqual(0, msString.compare(".a.", ".A.", true));
        msAssert.areEqual(0, msString.compare(".a.", 1, "A", 0, 1, StringComparison.CURRENT_CULTURE_IGNORE_CASE));
        msAssert.areEqual(0, msString.compare(".a.", ".A.", StringComparison.CURRENT_CULTURE_IGNORE_CASE));
        msAssert.areEqual(0, msString.compare(".a.", 1, "A", 0, 1, StringComparison.INVARIANT_CULTURE_IGNORE_CASE));
        msAssert.areEqual(0, msString.compare(".a.", ".A.", StringComparison.INVARIANT_CULTURE_IGNORE_CASE));
    }

    @Test
    public void testCompare2()
    {
        msAssert.isTrue(msString.compare("a", "a", StringComparison.ORDINAL) == 0);
        msAssert.isTrue(msString.compare("a", "A", StringComparison.ORDINAL) > 0);
        msAssert.isTrue(msString.compare("a", "b", StringComparison.ORDINAL) < 0);
        msAssert.isTrue(msString.compare("a", "ä", StringComparison.ORDINAL) < 0);

        msAssert.isTrue(msString.compare("a", "a", StringComparison.ORDINAL_IGNORE_CASE) == 0);
        msAssert.isTrue(msString.compare("a", "A", StringComparison.ORDINAL_IGNORE_CASE) == 0);
        msAssert.isTrue(msString.compare("a", "b", StringComparison.ORDINAL_IGNORE_CASE) < 0);
        msAssert.isTrue(msString.compare("a", "ä", StringComparison.ORDINAL_IGNORE_CASE) < 0);

        msAssert.isTrue(msString.compare("a", "a", StringComparison.CURRENT_CULTURE) == 0);
        msAssert.isTrue(msString.compare("a", "A", StringComparison.CURRENT_CULTURE) < 0);
        msAssert.isTrue(msString.compare("a", "b", StringComparison.CURRENT_CULTURE) < 0);
        msAssert.isTrue(msString.compare("a", "ä", StringComparison.CURRENT_CULTURE) < 0);

        msAssert.isTrue(msString.compare("a", "a", StringComparison.CURRENT_CULTURE_IGNORE_CASE) == 0);
        msAssert.isTrue(msString.compare("a", "A", StringComparison.CURRENT_CULTURE_IGNORE_CASE) == 0);
        msAssert.isTrue(msString.compare("a", "b", StringComparison.CURRENT_CULTURE_IGNORE_CASE) < 0);
        msAssert.isTrue(msString.compare("a", "ä", StringComparison.CURRENT_CULTURE_IGNORE_CASE) < 0);
    }

    @Test
    public void testStringCtors()
    {
        msAssert.areEqual("", "");

        msAssert.areEqual(msString.newString('X', 3), "XXX");
        msAssert.areEqual(msString.newString('Y', 0), "");
    }

    @Test
    public void testStringRemoveInsert()
    {
        msAssert.areEqual(msString.remove("0123456789", 3, 3), "0126789");

        msAssert.areEqual(msString.insert("0123456789", 3, "XXX"), "012XXX3456789");
    }

    @Test (enabled = false, description = "For a while.")
    public void testStringIndexOf()
    {
        String s1 = "ani\u00ADmal";
        String s2 = "animal";
        String empty = "";

        // Empty string
        msAssert.areEqual(msString.indexOf(s1, empty, StringComparison.CURRENT_CULTURE), 0);
        msAssert.areEqual(msString.indexOf(s1, empty, StringComparison.INVARIANT_CULTURE), 0);
        msAssert.areEqual(msString.indexOf(s1, empty, StringComparison.ORDINAL), 0);
        msAssert.areEqual(msString.indexOf(empty, empty, StringComparison.ORDINAL), 0);

        // Use culture-sensitive comparison to find the soft hyphen.
        msAssert.areEqual(msString.indexOf(s1, "\u00AD", StringComparison.CURRENT_CULTURE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00AD", StringComparison.CURRENT_CULTURE), -1);

        // Use culture-sensitive comparison to find the soft hyphen followed by "n".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADn", StringComparison.CURRENT_CULTURE), -1);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADn", StringComparison.CURRENT_CULTURE), -1);

        // Use culture-sensitive comparison to find the soft hyphen followed by "m".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADm", StringComparison.CURRENT_CULTURE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADm", StringComparison.CURRENT_CULTURE), -1);

        // Use InvariantCulture-sensitive comparison to find the soft hyphen.
        msAssert.areEqual(msString.indexOf(s1, "\u00AD", StringComparison.INVARIANT_CULTURE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00AD", StringComparison.INVARIANT_CULTURE), -1);

        // Use InvariantCulture-sensitive comparison to find the soft hyphen followed by "n".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADn", StringComparison.INVARIANT_CULTURE), -1);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADn", StringComparison.INVARIANT_CULTURE), -1);

        // Use InvariantCulture-sensitive comparison to find the soft hyphen followed by "m".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADm", StringComparison.INVARIANT_CULTURE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADm", StringComparison.INVARIANT_CULTURE), -1);

        // Use ordinal comparison to find the soft hyphen.
        msAssert.areEqual(msString.indexOf(s1, "\u00AD", StringComparison.ORDINAL), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00AD", StringComparison.ORDINAL), -1);

        // Use ordinal comparison to find the soft hyphen followed by "n".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADn", StringComparison.ORDINAL), -1);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADn", StringComparison.ORDINAL), -1);

        // Use ordinal comparison to find the soft hyphen followed by "m".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADm", StringComparison.ORDINAL), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADm", StringComparison.ORDINAL), -1);
    }

    @Test (enabled = false, description = "For a while.")
    public void testStringIndexOfIgnoreCase()
    {
        String s1 = "ani\u00ADmal";
        String s2 = "animal";
        String empty = "";

        // Empty string
        msAssert.areEqual(msString.indexOf(s1, empty, StringComparison.CURRENT_CULTURE_IGNORE_CASE), 0);
        msAssert.areEqual(msString.indexOf(s1, empty, StringComparison.INVARIANT_CULTURE_IGNORE_CASE), 0);
        msAssert.areEqual(msString.indexOf(s1, empty, StringComparison.ORDINAL_IGNORE_CASE), 0);
        msAssert.areEqual(msString.indexOf(empty, empty, StringComparison.ORDINAL_IGNORE_CASE), 0);

        // Use culture-sensitive comparison to find the soft hyphen.
        msAssert.areEqual(msString.indexOf(s1, "\u00AD", StringComparison.CURRENT_CULTURE_IGNORE_CASE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00AD", StringComparison.CURRENT_CULTURE_IGNORE_CASE), -1);

        // Use culture-sensitive comparison to find the soft hyphen followed by "n".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADN", StringComparison.CURRENT_CULTURE_IGNORE_CASE), -1);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADN", StringComparison.CURRENT_CULTURE_IGNORE_CASE), -1);

        // Use culture-sensitive comparison to find the soft hyphen followed by "m".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADM", StringComparison.CURRENT_CULTURE_IGNORE_CASE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADM", StringComparison.CURRENT_CULTURE_IGNORE_CASE), -1);

        // Use InvariantCulture-sensitive comparison to find the soft hyphen.
        msAssert.areEqual(msString.indexOf(s1, "\u00AD", StringComparison.INVARIANT_CULTURE_IGNORE_CASE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00AD", StringComparison.INVARIANT_CULTURE_IGNORE_CASE), -1);

        // Use InvariantCulture-sensitive comparison to find the soft hyphen followed by "n".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADN", StringComparison.INVARIANT_CULTURE_IGNORE_CASE), -1);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADN", StringComparison.INVARIANT_CULTURE_IGNORE_CASE), -1);

        // Use InvariantCulture-sensitive comparison to find the soft hyphen followed by "m".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADM", StringComparison.INVARIANT_CULTURE_IGNORE_CASE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADM", StringComparison.INVARIANT_CULTURE_IGNORE_CASE), -1);

        // Use ordinal comparison to find the soft hyphen.
        msAssert.areEqual(msString.indexOf(s1, "\u00AD", StringComparison.ORDINAL_IGNORE_CASE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00AD", StringComparison.ORDINAL_IGNORE_CASE), -1);

        // Use ordinal comparison to find the soft hyphen followed by "n".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADN", StringComparison.ORDINAL_IGNORE_CASE), -1);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADN", StringComparison.ORDINAL_IGNORE_CASE), -1);

        // Use ordinal comparison to find the soft hyphen followed by "m".
        msAssert.areEqual(msString.indexOf(s1, "\u00ADM", StringComparison.ORDINAL_IGNORE_CASE), 3);
        msAssert.areEqual(msString.indexOf(s2, "\u00ADM", StringComparison.ORDINAL_IGNORE_CASE), -1);
    }

    @Test
    public void testStringIndexOfAny()
    {
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '5' }), 3);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '4', '5', '3' }), 3);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '5', '9', '0' }), 0);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { 'x', '9', 'v' }), 9);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { 'x', 'y', 'z' }), -1);

        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '5' }, 3), 3);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '5' }, 4), 4);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '5' }, 5), 5);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '9' }, 9), 9);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '5' }, 6), -1);

        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '5' }, 3, 1), 3);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '5' }, 4, 3), 4);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '5' }, 1, 4), 3);
        msAssert.areEqual(msString.indexOfAny("0123456789", new char[] { '3', '4', '5' }, 6, 3), -1);
    }

    @Test
    public void testStringTrim()
    {
        msAssert.areEqual(msString.trimStart("XXX0123456789XXX", 'X'), "0123456789XXX");
        msAssert.areEqual(msString.trimStart("XXX0123456789XXX", 'Y'), "XXX0123456789XXX");

        msAssert.areEqual(msString.trimEnd("XXX0123456789XXX", 'X'), "XXX0123456789");
        msAssert.areEqual(msString.trimEnd("XXX0123456789XXX", 'Y'), "XXX0123456789XXX");

        msAssert.areEqual(msString.trim("XXX0123456789XXX", 'X'), "0123456789");
        msAssert.areEqual(msString.trim("XXX0123456789", 'X'), "0123456789");
        msAssert.areEqual(msString.trim("0123456789XXX", 'X'), "0123456789");
        msAssert.areEqual(msString.trim("XXX0123456789XXX", 'Y'), "XXX0123456789XXX");

        msAssert.areEqual(msString.trim("XXYYYZZZ0123456789YYYXXXZZZ", 
                new char[] { 'X', 'Y', 'Z' }), "0123456789");
        msAssert.areEqual(msString.trim("XXYYYZZZ0123456789YYYXXXZZZ", 
                new char[] { 'X', 'Y' }), "ZZZ0123456789YYYXXXZZZ");
        msAssert.areEqual(msString.trim("XXYYYZZZ0123456789YYYXXXZZZ", 
                new char[] { 'a', 'b', 'c' }), "XXYYYZZZ0123456789YYYXXXZZZ");
    }

    @Test
    public void testStringJoin()
    {
        String[] strings = new String[] { "one", "two", "three" };
        msAssert.areEqual(msString.join("XYZ", strings), "oneXYZtwoXYZthree");
        msAssert.areEqual(msString.join("", strings), "onetwothree");
        msAssert.areEqual(msString.join(null, strings), "onetwothree");
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> string.split(char[])

    @Test
    public void testStringSplitByChar()
    {
        String[] splitStrings = msString.split("one/two/three", '/');
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one/two/three/", '/');
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three", "" });

        splitStrings = msString.split("/one/two/three/", '/');
        msAssert.areEqual(splitStrings, new String[] { "", "one", "two", "three", "" });

        splitStrings = msString.split("/one//two/three/", '/');
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        splitStrings = msString.split("one/two/three", ';');
        msAssert.areEqual(splitStrings, new String[] { "one/two/three" });
    }

    @Test
    public void testStringSplitSeparatorsArray()
    {
        char[] separators = { ',', ';' };
        String[] splitStrings = msString.split("one,two;three", separators);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one,two,three", separators);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one;two;three", separators);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one,two;three;", separators);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three", "" });

        splitStrings = msString.split(",one;two;three,", separators);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "two", "three", "" });

        splitStrings = msString.split(";one,,two;three,", separators);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        splitStrings = msString.split("one/two/three", separators);
        msAssert.areEqual(splitStrings, new String[] { "one/two/three" });

        //if it get null or empty separators array - it will find whitespaces.
        splitStrings = msString.split("one two three", null);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one two three", new char[0]);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });
    }

    @Test
    public void testStringSplitBySingleChar()
    {
        String[] splitStrings = msString.split("one/two/three", '/');
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one/two/three/", '/');
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three", "" });

        splitStrings = msString.split("/one/two/three/", '/');
        msAssert.areEqual(splitStrings, new String[] { "", "one", "two", "three", "" });

        splitStrings = msString.split("/one//two/three/", '/');
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        splitStrings = msString.split("/one//two/three/", '-');
        msAssert.areEqual(splitStrings, new String[] { "/one//two/three/" });
    }

    @Test
    public void testStringSplitByCharArray()
    {
        char[] separators = { '/', '-' };
        String[] splitStrings = msString.split("one/two/three", separators);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one-two-three", separators);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one/two-three/", separators);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three", "" });

        splitStrings = msString.split("-one/two/three-", separators);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "two", "three", "" });

        splitStrings = msString.split("/one--two/three/", separators);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        // non-found separators.
        separators = new char[] { ';', ':' };
        splitStrings = msString.split("/one/-two-three/", separators);
        msAssert.areEqual(splitStrings, new String[] { "/one/-two-three/" });

        //if it get null or empty separators array - it will find whitespaces.
        splitStrings = msString.split("/one/-two-three/", null);
        msAssert.areEqual(splitStrings, new String[] { "/one/-two-three/" });

        splitStrings = msString.split("one two three", null);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one two three", new char[0]);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });
    }

    @Test
    public void testStringSplitByCharArrayCount()
    {
        // Count 0 and 1 are special cases.
        // If count is less than splitted strings - the last string is concatenaded without split.

        char[] separators = { '/', '-' };
        String[] splitStrings = msString.split("one/two/three", separators, 0);
        msAssert.areEqual(splitStrings, new String[0]);

        splitStrings = msString.split("one/two/three", separators, 1);
        msAssert.areEqual(splitStrings, new String[] { "one/two/three" });

        splitStrings = msString.split("one-two-three", separators, 2);
        msAssert.areEqual(splitStrings, new String[] { "one", "two-three" });

        splitStrings = msString.split("one/two-three/", separators, 3);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three/" });

        splitStrings = msString.split("-one/two/three-", separators, 4);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "two", "three-" });

        splitStrings = msString.split("/one--two/three/", separators, 5);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three/" });

        splitStrings = msString.split("/one--two/three/", separators, 6);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        splitStrings = msString.split("/one--two/three/", separators, 7);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        splitStrings = msString.split("/one--two/three/", separators, 999);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        separators = new char[] { ';', ':' };
        splitStrings = msString.split("/one/-two-three/", separators, 1);
        msAssert.areEqual(splitStrings, new String[] { "/one/-two-three/" });

        splitStrings = msString.split("/one/-two-three/", separators, 999);
        msAssert.areEqual(splitStrings, new String[] { "/one/-two-three/" });
    }

    @Test
    public void testStringSplitByCharArrayCountDontRemoveEmpty()
    {
        // Count 0 and 1 are special cases.
        // If count is less than splitted strings - the last string is concatenaded without split.

        char[] separators = { '/', '-' };
        String[] splitStrings = msString.split("one/two/three", separators, 0, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[0]);

        splitStrings = msString.split("one/two/three", separators, 1, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "one/two/three" });

        splitStrings = msString.split("one-two-three", separators, 2, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "one", "two-three" });

        splitStrings = msString.split("one/two-three/", separators, 3, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three/" });

        splitStrings = msString.split("-one/two/three-", separators, 4, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "two", "three-" });

        splitStrings = msString.split("/one--two/three/", separators, 5, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three/" });

        splitStrings = msString.split("/one--two/three/", separators, 6, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        splitStrings = msString.split("/one--two/three/", separators, 7, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        splitStrings = msString.split("/one--two/three/", separators, 999, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        separators = new char[] { ';', ':' };
        splitStrings = msString.split("/one/-two-three/", separators, 1, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "/one/-two-three/" });

        splitStrings = msString.split("/one/-two-three/", separators, 999, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "/one/-two-three/" });
    }

    @Test
    public void testStringSplitByCharArrayRemoveEmpty()
    {
        char[] separators = { '/', '-' };
        String[] splitStrings = msString.split("one/two/three", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one-two-three", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one/two-three/", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("-one/two/three-", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("/one--two/three/", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        separators = new char[] { ';', ':' };
        splitStrings = msString.split("/one/-two-three/", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "/one/-two-three/" });
    }

    @Test
    public void testStringSplitByCharArrayRemoveEmptyCount()
    {
        char[] separators = { '/', '-' };
        String[] splitStrings = msString.split("one/two/three", separators, 0, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[0]);

        splitStrings = msString.split("one/two/three", separators, 1, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one/two/three" });

        splitStrings = msString.split("one-two-three", separators, 2, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two-three" });

        splitStrings = msString.split("one/two-three/", separators, 3, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three/" });

        splitStrings = msString.split("-one/two/three-", separators, 4, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("/one--two/three/", separators, 5, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("/one--two/three/", separators, 999, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        separators = new char[] { ';', ':' };
        splitStrings = msString.split("/one/-two-three/", separators, 1, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "/one/-two-three/" });

        splitStrings = msString.split("/one/-two-three/", separators, 7, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "/one/-two-three/" });
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< string.split(char[])

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> string.split(string[])

    @Test
    public void testStringSplitByStringDontRemoveEmpty()
    {
        String[] separators = { "/r" };
        String[] splitStrings = msString.split("one/rtwo/rthree", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one/rtwo/rthree/r", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three", "" });

        splitStrings = msString.split("/rone/rtwo/rthree/r", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "two", "three", "" });

        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        separators[0] = "/f";
        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "/rone/r/rtwo/rthree/r" });
    }

    @Test
    public void testStringSplitByStringArrayDontRemoveEmpty()
    {
        String[] separators = { "/r/n", "/r", "/n" };
        String[] splitStrings = msString.split("one/rtwo/r/nthree", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one/ntwo/rthree/r/n", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three", "" });

        splitStrings = msString.split("/r/none/rtwo/nthree/r/n", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "two", "three", "" });

        splitStrings = msString.split("/r/none/r/n/rtwo/rthree/r/n", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        separators = new String[] { "/f", "/t", "/ab" };
        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "/rone/r/rtwo/rthree/r" });
    }

    @Test
    public void testStringSplitByStringArrayDontRemoveEmptyCount()
    {
        String[] separators = { "/r/n", "/r", "/n" };
        String[] splitStrings = msString.split("one/rtwo/r/nthree", separators, 0, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[0]);

        splitStrings = msString.split("one/rtwo/r/nthree", separators, 1, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "one/rtwo/r/nthree" });

        splitStrings = msString.split("one/ntwo/rthree/r/n", separators, 2, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "one", "two/rthree/r/n" });

        splitStrings = msString.split("/r/none/rtwo/nthree/r/n", separators, 3, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "two/nthree/r/n" });

        splitStrings = msString.split("/r/none/r/n/rtwo/rthree/r/n", separators, 4, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two/rthree/r/n" });

        splitStrings = msString.split("/r/none/r/n/rtwo/rthree/r/n", separators, 5, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three/r/n" });

        splitStrings = msString.split("/r/none/r/n/rtwo/rthree/r/n", separators, 6, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        splitStrings = msString.split("/r/none/r/n/rtwo/rthree/r/n", separators, 7, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        splitStrings = msString.split("/r/none/r/n/rtwo/rthree/r/n", separators, 999, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "", "one", "", "two", "three", "" });

        separators = new String[] { "/f", "/t", "/ab" };
        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, 1, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "/rone/r/rtwo/rthree/r" });

        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, 3, StringSplitOptions.NONE);
        msAssert.areEqual(splitStrings, new String[] { "/rone/r/rtwo/rthree/r" });
    }

    @Test
    public void testStringSplitByStringRemoveEmpty()
    {
        String[] separators = { "/r" };
        String[] splitStrings = msString.split("one/rtwo/rthree", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one/rtwo/rthree/r", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("/rone/rtwo/rthree/r", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        separators[0] = "/f";
        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "/rone/r/rtwo/rthree/r" });
    }

    @Test
    public void testStringSplitByStringArrayRemoveEmpty()
    {
        String[] separators = { "/r/n", "/r", "/n" };
        String[] splitStrings = msString.split("one/rtwo/r/nthree", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("one/ntwo/rthree/r/n", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("/r/none/rtwo/nthree/r/n", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        splitStrings = msString.split("/r/none/r/n/rtwo/rthree/r/n", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        separators = new String[] { "/f", "/t", "/ab" };
        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "/rone/r/rtwo/rthree/r" });
    }


    @Test
    public void testStringSplitByStringArrayRemoveEmptyCount()
    {
        // Count 0 and 1 are special cases.
        // If count is less than splitted strings - the last string is concatenaded without split.

        String[] separators = { "/r/n", "/r", "/n" };
        String[] splitStrings = msString.split("one/rtwo/r/nthree", separators, 0, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[0]);

        splitStrings = msString.split("one/rtwo/r/nthree", separators, 1, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one/rtwo/r/nthree" });

        splitStrings = msString.split("one/ntwo/rthree/r/n", separators, 2, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two/rthree/r/n" });

        splitStrings = msString.split("/r/none/rtwo/nthree/r/n", separators, 3, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three/r/n" });

        splitStrings = msString.split("/r/none/r/n/rtwo/rthree/r/n", separators, 4, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "one", "two", "three" });

        separators = new String[] { "/f", "/t", "/ab" };
        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, 1, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "/rone/r/rtwo/rthree/r" });

        splitStrings = msString.split("/rone/r/rtwo/rthree/r", separators, 4, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        msAssert.areEqual(splitStrings, new String[] { "/rone/r/rtwo/rthree/r" });
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< string.split(string[])

    @Test
    public void testIsNullOrEmpty()
    {
        String nullStr = null;
        String emptyStr = "";

        msAssert.isTrue(msString.isNullOrEmpty(nullStr));
        msAssert.isTrue(msString.isNullOrEmpty(emptyStr));
    }

    @Test
    public void testToUpperInvariant()
    {
        msAssert.areEqual("HELLO" + MICRO_SIGN, msString.toUpperInvariant(("Hello" + MICRO_SIGN)));
    }

    @Test
    public void testToLowerInvariant()
    {
        msAssert.areEqual("hello" + MICRO_SIGN, msString.toLowerInvariant(("HELLO" + MICRO_SIGN)));
    }

    @Test
    public void testCompareInvariant()
    {
        for (int i = (int)'a'; i <= (int)'z'; i++)
        {
            // normal rules: a < A and a'b < a'B
            msAssert.areEqual(msString.compare("" + (char)i, "" + Character.toUpperCase((char)i), StringComparison.INVARIANT_CULTURE), -1);
            msAssert.areEqual(msString.compare("a'" + (char)i, "a'" + Character.toUpperCase((char)i), StringComparison.INVARIANT_CULTURE), -1);

            // weird rules:
            //  ab < a'b    , but
            //  ab > a' b
            msAssert.areEqual(msString.compare("a" + (char)i, "a'" + (char)i, StringComparison.INVARIANT_CULTURE), -1);
            msAssert.areEqual(msString.compare("a" + (char)i, "a' " + (char)i, StringComparison.INVARIANT_CULTURE), 1);

            // uppercase weird rules:
            //  aB < a'B    , but
            //  aB > a' B
            msAssert.areEqual(msString.compare("a" + Character.toUpperCase((char)i), "a'" + Character.toUpperCase((char)i), StringComparison.INVARIANT_CULTURE), -1);
            msAssert.areEqual(msString.compare("a" + Character.toUpperCase((char)i), "a' " + Character.toUpperCase((char)i), StringComparison.INVARIANT_CULTURE), 1);
        }
    }

    @Test
    public void testCompareInvariantIgnoreCase()
    {
        for (int i = (int)'a'; i <= (int)'z'; i++)
        {
            // normal rules: a = A and a'b = a'B
            msAssert.areEqual(msString.compare("" + (char)i, "" + Character.toUpperCase((char)i), StringComparison.INVARIANT_CULTURE_IGNORE_CASE), 0);
            msAssert.areEqual(msString.compare("a'" + (char)i, "a'" + Character.toUpperCase((char)i), StringComparison.INVARIANT_CULTURE_IGNORE_CASE), 0);

            // weird rules:
            //  ab < a'b    , but
            //  ab > a' b
            msAssert.areEqual(msString.compare("a" + (char)i, "a'" + (char)i, StringComparison.INVARIANT_CULTURE_IGNORE_CASE), -1);
            msAssert.areEqual(msString.compare("a" + (char)i, "a' " + (char)i, StringComparison.INVARIANT_CULTURE_IGNORE_CASE), 1);

            // uppercase weird rules:
            //  aB < a'B    , but
            //  aB > a' B
            msAssert.areEqual(msString.compare("a" + Character.toUpperCase((char)i), "a'" + Character.toUpperCase((char)i), StringComparison.INVARIANT_CULTURE_IGNORE_CASE), -1);
            msAssert.areEqual(msString.compare("a" + Character.toUpperCase((char)i), "a' " + Character.toUpperCase((char)i), StringComparison.INVARIANT_CULTURE_IGNORE_CASE), 1);
        }
    }

    // J1678: micro sign char stays the same while converting to upper or to lower invariant in .Net.
    private static final String MICRO_SIGN = "\u00b5";
}

