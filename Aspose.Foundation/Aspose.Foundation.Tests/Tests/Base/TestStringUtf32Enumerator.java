// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestStringUtf32Enumerator
{
    @Test
    public void testEnumerateUtf32EmptyString()
    {
        StringUtf32Enumerator enumerator = new StringUtf32Enumerator("");
        msAssert.isFalse(enumerator.hasNext());
        msAssert.isFalse(enumerator.hasNext());
    }

    @Test
    public void testEnumerateUtf32NullString()
    {
        StringUtf32Enumerator enumerator = new StringUtf32Enumerator(null);
        msAssert.isFalse(enumerator.hasNext());
        msAssert.isFalse(enumerator.hasNext());
    }

    @Test
    public void testEnumerateUtf32SimpleString()
    {
        testEnumerateUtf32('a', 'b', 'c');
    }

    @Test
    public void testEnumerateUtf32SupplementaryString()
    {
        final int CHAR_1 = 0x1F680; // U+1F680 ROCKET.
        final int CHAR_2 = 0x1F681; // U+1F681 HELICOPTER.
        final int CHAR_3 = 0x1F682; // U+1F682 STEAM LOCOMOTIVE.

        testEnumerateUtf32(CHAR_1, CHAR_2, CHAR_3);
    }

    @Test
    public void testEnumerateUtf32MixedString()
    {
        final int CHAR_1 = 0x1F680; // U+1F680 ROCKET.
        final int CHAR_2 = 0x1F681; // U+1F681 HELICOPTER.
        final int CHAR_3 = 0x1F682; // U+1F682 STEAM LOCOMOTIVE.

        testEnumerateUtf32('a', CHAR_1, 'b', CHAR_2, 'c', CHAR_3, 'd');
    }

    @Test
    public void testInvalidSurrogates()
    {
        final int SUPPLEMENTARY_CHAR = 0x1F680; // U+1F680 ROCKET.
        char leadSurrogate = UnicodeUtil.convertFromUtf32(SUPPLEMENTARY_CHAR).charAt(0);
        char tailSurrogate = UnicodeUtil.convertFromUtf32(SUPPLEMENTARY_CHAR).charAt(1);
        final char NORMAL_CHAR = 'A';

        testEnumerateUtf32(leadSurrogate);
        testEnumerateUtf32(tailSurrogate);
        testEnumerateUtf32(leadSurrogate, leadSurrogate);
        testEnumerateUtf32(tailSurrogate, tailSurrogate);
        testEnumerateUtf32(tailSurrogate, leadSurrogate);
        testEnumerateUtf32(tailSurrogate, NORMAL_CHAR, leadSurrogate);
        testEnumerateUtf32(leadSurrogate, NORMAL_CHAR, tailSurrogate);
        testEnumerateUtf32(NORMAL_CHAR, leadSurrogate, leadSurrogate, NORMAL_CHAR);
        testEnumerateUtf32(NORMAL_CHAR, SUPPLEMENTARY_CHAR, NORMAL_CHAR);
        testEnumerateUtf32(tailSurrogate, leadSurrogate, NORMAL_CHAR);
        testEnumerateUtf32(leadSurrogate, SUPPLEMENTARY_CHAR, tailSurrogate);
    }

    private static void testEnumerateUtf32(/*params*/ int... utf32Characters)
    {
        // Build a test string from the provided characters.
        StringBuilder testStringBuilder = new StringBuilder();
        IntList offsets = new IntList();
        for (int utf32Character : utf32Characters)
        {
            offsets.add(testStringBuilder.length());
            UnicodeUtil.appendUtf32(testStringBuilder, utf32Character);
        }

        // Test character enumeration.
        StringUtf32Enumerator enumerator = new StringUtf32Enumerator(testStringBuilder.toString());
        for (int i = 0; i < utf32Characters.length; i++)
        {
            msAssert.isTrue(enumerator.hasNext());
            msAssert.areEqual(utf32Characters[i], enumerator.next());
            msAssert.areEqual(utf32Characters[i], enumerator.next());
            msAssert.areEqual(offsets.get(i), enumerator.getOffset());
        }

        // The enumerator has reached the end of the sequence and it cannot return any more characters.
        // Double check is intentional here.
        msAssert.isFalse(enumerator.hasNext());
        msAssert.isFalse(enumerator.hasNext());
    }
}

