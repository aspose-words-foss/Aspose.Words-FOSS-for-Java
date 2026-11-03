// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2025 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestUnicodeUtil
{
    @Test
    public void testReverseString_Null()
    {
        final String SOURCE = null;
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areSame(SOURCE, reversed);
    }

    @Test
    public void testReverseString_Empty()
    {
        final String SOURCE = "";
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areSame(SOURCE, reversed);
    }

    @Test
    public void testReverseString_SingleCharacter()
    {
        final String SOURCE = "A";
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areSame(SOURCE, reversed);
    }

    @Test
    public void testReverseString_NoSurrogatePairs()
    {
        final String SOURCE = "ABCDEF";
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areEqual("FEDCBA", reversed);
    }

    @Test
    public void testReverseString_OneSurrogatePair()
    {
        final String SOURCE = ROCKET;
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areEqual(ROCKET, reversed);
    }

    @Test
    public void testReverseString_TwoSurrogatePairs()
    {
        final String SOURCE = ROCKET + ACE_OF_SPADES;
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areEqual(ACE_OF_SPADES + ROCKET, reversed);
    }

    @Test
    public void testReverseString_Mixed()
    {
        final String SOURCE = ROCKET + "ABCD";
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areEqual("DCBA" + ROCKET, reversed);
    }

    @Test
    public void testReverseString_MalformedPairs_1()
    {
        final String SOURCE = LEAD_1 + LEAD_2 + TAIL_2;
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areEqual(LEAD_2 + TAIL_2 + LEAD_1, reversed);
    }

    @Test
    public void testReverseString_MalformedPairs_2()
    {
        final String SOURCE = LEAD_1 + TAIL_1 + TAIL_2;
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areEqual(TAIL_2 + LEAD_1 + TAIL_1, reversed);
    }

    @Test
    public void testReverseString_MalformedPairs_3()
    {
        final String SOURCE = LEAD_1 + LEAD_2 + TAIL_2 + TAIL_1;
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areEqual(TAIL_1 + LEAD_2 + TAIL_2 + LEAD_1, reversed);
    }

    @Test
    public void testReverseString_MalformedPairs_4()
    {
        final String SOURCE = TAIL_1 + LEAD_1;
        String reversed = UnicodeUtil.reverseString(SOURCE);
        msAssert.areEqual(LEAD_1 + TAIL_1, reversed);
    }

    @Test
    public void testReverseString_Emojis()
    {
        final String MONKEY = "\ud83d\ude48"; // U+1F648 SEE-NO-EVIL MONKEY;

        final String SHRUG = "\ud83e\udd37"; // U+1F937 SHRUG
        final String DARK_SKIN = "\ud83c\udffd"; // U+1F3FD EMOJI MODIFIER FITZPATRICK TYPE-4
        final String ZWJ = "\u200D"; // U+200D ZERO WIDTH JOINER (ZWJ)
        final String FEMALE = "\u2640"; // U+2640 FEMALE SIGN
        final String EMOJI = "\uFE0F"; // U+FE0F VARIATION SELECTOR-16 (VS16) -- emoji variation selector
        final String WOMAN_SHRUG_DARK_SKIN = SHRUG + DARK_SKIN + ZWJ + FEMALE + EMOJI;

        final String SOURCE = MONKEY + WOMAN_SHRUG_DARK_SKIN;
        String reversed = UnicodeUtil.reverseString(SOURCE);

        // Note that the components of the second emoji are reversed. The method is not intended to correctly preserve
        // the emoji sequences.
        msAssert.areEqual(EMOJI + FEMALE + ZWJ + DARK_SKIN + SHRUG + MONKEY, reversed);
    }

    private static final String ROCKET = "\ud83d\ude80"; // U+1F680 ROCKET
    private static final String ACE_OF_SPADES = "\ud83c\udca1"; // U+1F0A1 PLAYING CARD ACE OF SPADES

    // UTF-16 for U+1F680 ROCKET
    private static final String LEAD_1 = "\uD83D";
    private static final String TAIL_1 = "\uDE80";

    // UTF-16 for U+1F0A1 PLAYING CARD ACE OF SPADES
    private static final String LEAD_2 = "\uD83C";
    private static final String TAIL_2 = "\uDCA1";
}

