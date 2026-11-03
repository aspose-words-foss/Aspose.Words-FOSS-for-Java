// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/09/2014 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests our implementation of the Unicode bidirectional algorithm.
/// </summary>
@Test
public class TestBidiAlgorithm
{
    @Test
    public void testIsRtlParagraphEmpty()
    {
        msAssert.areEqual(false, BidiAlgorithm.isRtlParagraph(""));
    }

    @Test
    public void testIsRtlParagraphLtr()
    {
        msAssert.areEqual(false, BidiAlgorithm.isRtlParagraph("z\u05D0"));
    }

    @Test
    public void testIsRtlParagraphRtl()
    {
        msAssert.areEqual(true, BidiAlgorithm.isRtlParagraph("\u05D0Az"));
    }

    @Test
    public void testIsRtlParagraphRtlArabicLetter()
    {
        msAssert.areEqual(true, BidiAlgorithm.isRtlParagraph("\u0621z\u05D0A"));
    }

    @Test
    public void testIsRtlParagraphNoStrongCharacter()
    {
        msAssert.areEqual(false, BidiAlgorithm.isRtlParagraph("123"));
    }

    @Test
    public void testBoundaryNeutralCharacterLtr()
    {
        BidiRun[] runs = BidiAlgorithm.apply("abc\u00ADdef", false);

        msAssert.areEqual(1, runs.length);
        checkBidiRun("abc\u00ADdef", false, 0, runs[0]);
    }

    @Test
    public void testBoundaryNeutralCharacterRtl()
    {
        BidiRun[] runs = BidiAlgorithm.apply("\u0645\u06CC\u200C\u062E\u0648\u0627\u0647\u0645", false);

        msAssert.areEqual(1, runs.length);
        checkBidiRun("\u0645\u06CC\u200C\u062E\u0648\u0627\u0647\u0645", true, 0, runs[0]);
    }

    @Test
    public void test1()
    {
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("User "),
            new BidiSourceRun("שנב"),
            new BidiSourceRun(" - 3.5 posts")
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, false, false, true);

        msAssert.areEqual(4, runs.length);
        checkBidiRun("User ", false, 0, runs[0]);
        checkBidiRun("שנב", true, 1, runs[1]);
        checkBidiRun(" - 3.5", true, 2, runs[2]);
        checkBidiRun(" posts", false, 2, runs[3]);
    }

    @Test
    public void test2()
    {
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("1. שנב 123 456 גקכ.", BidiLevel.EMBED_RTL)
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, false, false, true);

        msAssert.areEqual(1, runs.length);
        checkBidiRun("1. שנב 123 456 גקכ.", true, 0, runs[0]);
    }

    @Test
    public void test3()
    {
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("\u05D0\u05D1\u05D2  \uD840\uDC00\uD840\uDC01\uD840\uDC02 \u05D3\u05D4\u05D5")
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, true, false, true);

        msAssert.areEqual(3, runs.length);
        checkBidiRun("\u05D0\u05D1\u05D2  ", true, 0, runs[0]);
        checkBidiRun("\uD840\uDC00\uD840\uDC01\uD840\uDC02", false, 0, runs[1]);
        checkBidiRun(" \u05D3\u05D4\u05D5", true, 0, runs[2]);
    }

    @Test
    public void test4()
    {
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("5/11/2011", BidiLevel.EMBED_LTR),
            new BidiSourceRun(" יעיבר םוי - ןורחא ןוכדע")
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, true, true, true);

        msAssert.areEqual(2, runs.length);
        checkBidiRunHebrewRtl("5/11/2011", 0, runs[0]);
        checkBidiRun("עדכון אחרון - יום רביעי ", true, 1, runs[1]);
    }

    @Test
    public void test5()
    {
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("123:456")
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, true, true, true);

        msAssert.areEqual(1, runs.length);
        checkBidiRun("123:456", false, 0, runs[0]);
    }

    @Test
    public void test6()
    {
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun(":" + "ךיראת"),
            new BidiSourceRun("23/04/2014", BidiLevel.EMBED_LTR)
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, true, true, true);

        msAssert.areEqual(2, runs.length);
        checkBidiRun("תאריך" + ":", true, 0, runs[0]);
        checkBidiRunHebrewRtl("23/04/2014", 1, runs[1]);
    }

    @Test
    public void test7()
    {
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("LTR Najib said \"السلام عليكم\" "),
            new BidiSourceRun("(as-salaam alaykum] to me.", BidiLevel.EMBED_RTL)
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, false, false, true);

        msAssert.areEqual(5, runs.length);
        checkBidiRun("LTR Najib said \"", false, 0, runs[0]);
        checkBidiRun(".", true, 1, runs[1]);
        checkBidiRun("as-salaam alaykum] to me", false, 1, runs[2]);
        checkBidiRun("السلام عليكم\" ", true, 0, runs[3]);
        checkBidiRun("(", true, 1, runs[4]);
    }

    @Test
    public void test8()
    {
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("1[First].", BidiLevel.EMBED_RTL),
            new BidiSourceRun("2{Second}!"),
            new BidiSourceRun("3(Third)?", BidiLevel.EMBED_RTL)
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, false, false, true);

        msAssert.areEqual(8, runs.length);
        checkBidiRun("].", true, 0, runs[0]);
        checkBidiRun("2", true, 1, runs[1]);
        checkBidiRun("First", false, 0, runs[2]);
        checkBidiRun("1[", true, 0, runs[3]);
        checkBidiRun("{Second}!", false, 1, runs[4]);
        checkBidiRun(")?", true, 2, runs[5]);
        checkBidiRun("Third", false, 2, runs[6]);
        checkBidiRun("3(", true, 2, runs[7]);
    }

    /// <summary>
    /// WORDSNET-12119 Adjacent runs having same direction were reordered during conversion to MS Word order.
    /// </summary>
    @Test
    public void testJira12119()
    {
        // The source runs correspond to the following HTML code:
        // <p dir='rtl'><span dir='ltr'>server</span><span dir='rtl'>A</span><span> 8</span></p>
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("server", BidiLevel.EMBED_LTR),
            new BidiSourceRun("A", BidiLevel.EMBED_RTL),
            new BidiSourceRun(" 8")
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, true, false, true);

        // The order of runs 'server' and 'A' shouldn't change.
        msAssert.areEqual(3, runs.length);
        checkBidiRun("server", false, 0, runs[0]);
        checkBidiRun("A", false, 1, runs[1]);
        checkBidiRun(" 8", true, 2, runs[2]);
    }

    /// <summary>
    /// WORDSNET-13787 Numbers containing certain characters ('+', '-', and '/') get reordered if imported
    /// into an RTL run. In order to prevent this behavior we have to import such runs with 'he-IL' locale.
    /// This test checks a number in Hebrew RTL text.
    /// </summary>
    @Test
    public void testJira13787Hebrew()
    {
        // Because of Hebrew text, numbers are classified as European numbers (AN) and European separators (ES) between them
        // become parts of that numbers and receive the LTR direction.
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("שנב 1+2-3/4:5.6,7 גקכ")
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, true, false, true);

        msAssert.areEqual(3, runs.length);
        checkBidiRun("שנב" + " ", true, 0, runs[0]);
        // This part is RTL and it contains '+', '-', and '/' characters between european number (EN) characters
        // so it requires 'he-IL' locale to be rendered properly.
        checkBidiRunHebrewRtl("1+2-3/4:5.6,7", 0, runs[1]);
        checkBidiRun(" " + "גקכ", true, 0, runs[2]);
    }

    /// <summary>
    /// WORDSNET-13787 Numbers containing certain characters ('+', '-', and '/') get reordered if imported
    /// into an RTL run. In order to prevent this behavior we have to import such runs with 'he-IL' locale.
    /// This test checks a number in English LTR text.
    /// </summary>
    @Test
    public void testJira13787English()
    {
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("abc 1+2-3/4:5.6,7 def")
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, true, false, true);

        msAssert.areEqual(1, runs.length);
        // The whole text is LTR. No need to offset the number in any way.
        checkBidiRun("abc 1+2-3/4:5.6,7 def", false, 0, runs[0]);
    }

    /// <summary>
    /// WORDSNET-13787 Numbers containing certain characters ('+', '-', and '/') get reordered if imported
    /// into an RTL run. In order to prevent this behavior we have to import such runs with 'he-IL' locale.
    /// This test checks a number in Arabic RTL text.
    /// </summary>
    @Test
    public void testJira13787Arabic()
    {
        // Because of Arabic text, numbers are classified as Arabic numbers (AN) and European separators (ES) between them
        // are treated as neutral characters (ON), which receive the RTL direction.
        BidiSourceRun[] sourceRuns = new BidiSourceRun[]
        {
            new BidiSourceRun("شلاؤ 1+2-3/4:5.6,7 يثب")
        };
        BidiRun[] runs = BidiAlgorithm.apply(sourceRuns, true, false, true);

        msAssert.areEqual(3, runs.length);
        checkBidiRun("شلاؤ" + " 1+2-", true, 0, runs[0]);
        // This part is RTL and it contains a slash character so it requires 'he-IL' locale to be rendered properly.
        checkBidiRunHebrewRtl("3/4:5.6,7", 0, runs[1]);
        checkBidiRun(" " + "يثب", true, 0, runs[2]);
    }

    /// <summary>
    /// WORDSNET-18763 The W4 rule of the Unicode Bidi Algorithm was not applied to ES and CS characters in certain cases
    /// because of a bug.
    /// </summary>
    @Test
    public void test18763()
    {
        // U+202C "POP DIRECTIONAL FORMATTING" characters around the last separator are added to check how the W4 rule
        // of the algorithm works with BN characters.
        BidiRun[] runs = BidiAlgorithm.apply("\u05D0:\u00A01/2\u202C\u202C+\u202C\u202C3", true);

        msAssert.areEqual(2, runs.length);
        // These two separators must be left unchanged.
        checkBidiRun("\u05D0:\u00A0", true, 0, runs[0]);
        // These separators must become a part of the number. The PDF characters are removed by the algorithm.
        checkBidiRun("1/2+3", false, 0, runs[1]);
    }

    /// <summary>
    /// Text inserted into the document model keeps its logical order.
    /// </summary>
    @Test
    public void testInsertedTextLtrParagraph()
    {
        BidiRun[] runs = BidiAlgorithm.applyToInsertedText("abc \u05E9\u05DC\u05D5\u05DD def", false);

        msAssert.areEqual(3, runs.length);
        checkBidiRun("abc ", false, 0, runs[0]);
        checkBidiRun("\u05E9\u05DC\u05D5\u05DD", true, 0, runs[1]);
        checkBidiRun(" def", false, 0, runs[2]);
    }

    /// <summary>
    /// A right-to-left number with '+', '-' or '/' needs the Hebrew bidi locale, as in <see cref="TestJira13787Hebrew"/>.
    /// </summary>
    @Test
    public void testInsertedTextHebrewNumberSeparators()
    {
        BidiRun[] runs = BidiAlgorithm.applyToInsertedText("\u05E9\u05E0\u05D1 1+2-3/4 \u05D2\u05E7\u05DB", true);

        msAssert.areEqual(3, runs.length);
        checkBidiRun("\u05E9\u05E0\u05D1 ", true, 0, runs[0]);
        checkBidiRunHebrewRtl("1+2-3/4", 0, runs[1]);
        checkBidiRun(" \u05D2\u05E7\u05DB", true, 0, runs[2]);
    }

    /// <summary>
    /// Runs of text whose direction differs from the paragraph direction are inserted in reverse order.
    /// </summary>
    @Test
    public void testInsertedTextReversedInOppositeParagraph()
    {
        // The lowest level of the text is 1 (right-to-left) in a left-to-right paragraph.
        BidiRun[] runs = BidiAlgorithm.applyToInsertedText("\u05D1$1) 3.14%", false);

        msAssert.areEqual(4, runs.length);
        checkBidiRun("3.14%", false, 0, runs[0]);
        checkBidiRun(") ", true, 0, runs[1]);
        checkBidiRun("$1", false, 0, runs[2]);
        checkBidiRun("\u05D1", true, 0, runs[3]);

        // The same text in a right-to-left paragraph keeps its order.
        runs = BidiAlgorithm.applyToInsertedText("\u05D1$1) 3.14%", true);

        msAssert.areEqual(4, runs.length);
        checkBidiRun("\u05D1", true, 0, runs[0]);
        checkBidiRun("$1", false, 0, runs[1]);
        checkBidiRun(") ", true, 0, runs[2]);
        checkBidiRun("3.14%", false, 0, runs[3]);
    }

    /// <summary>
    /// In a field result numbers get the level of the text before them.
    /// </summary>
    @Test
    public void testFieldResultNumbers()
    {
        BidiRun[] runs = BidiAlgorithm.applyToFieldResult("\u05E9\u05E0\u05D1 1+2-3/4 \u05D2\u05E7\u05DB", true);

        msAssert.areEqual(1, runs.length);
        checkBidiRun("\u05E9\u05E0\u05D1 1+2-3/4 \u05D2\u05E7\u05DB", true, 0, runs[0]);

        runs = BidiAlgorithm.applyToFieldResult("\u05D1$1) 3.14%", false);

        msAssert.areEqual(1, runs.length);
        checkBidiRun("\u05D1$1) 3.14%", true, 0, runs[0]);
    }

    /// <summary>
    /// Text of embedding characters only gives no runs.
    /// </summary>
    @Test
    public void testEmbeddingCharactersOnly()
    {
        msAssert.areEqual(0, BidiAlgorithm.applyToInsertedText("\u202E", false).length);
        msAssert.areEqual(0, BidiAlgorithm.applyToFieldResult("\u202B\u202C", true).length);
        msAssert.areEqual(0, BidiAlgorithm.apply(new BidiSourceRun[] { new BidiSourceRun("\u202D") }, false, false, true).length);
    }

    private static void checkBidiRun(String expectedText, boolean expectedRtl, int expectedSourceRunIndex, BidiRun run)
    {
        msAssert.areEqual(expectedText, run.getText());
        msAssert.areEqual(expectedRtl, run.getRtl());
        msAssert.areEqual(expectedSourceRunIndex, run.getSourceRunIndex());
        msAssert.areEqual(false, run.getRequiresHebrewLocaleBi());
    }

    private static void checkBidiRunHebrewRtl(String expectedText, int expectedSourceRunIndex, BidiRun run)
    {
        msAssert.areEqual(expectedText, run.getText());
        msAssert.areEqual(true, run.getRtl());
        msAssert.areEqual(expectedSourceRunIndex, run.getSourceRunIndex());
        msAssert.areEqual(true, run.getRequiresHebrewLocaleBi());
    }
}

