// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/09/2014 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.ArrayList;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Tests our implementation of the Unicode bidirectional algorithm (the class <see cref="BidiAlgorithm"/>
/// for conformance to the specification, as described here: http://www.unicode.org/reports/tr9/#Bidi_Conformance_Testing
/// At the moment, only BidiTest.txt file is used.
/// </summary>
@Test
public class TestBidiAlgorithmConformance
{

    @Test (groups = "CppSlowTest")
    public void testAllCases() throws Exception
    {
        BidiUcdTestCase[] testCases = loadTestCases();
        int failedCount = 0;
        for (int i = 0; i < testCases.length; i++)
        {
            boolean passed = testOneCase(testCases[i], i, true);
            if (!passed)
            {
                ++failedCount;
            }
        }
        if (failedCount > 0)
        {
            int totalCount = testCases.length;
            double failedPercent = (totalCount > 0)
                ? (double)failedCount / totalCount * 100.0
                : 0.0;
            Assert.fail(msString.format("{0} of {1} ({2:N2}%) tests failed.", failedCount, totalCount, failedPercent));
        }
    }

    @Test (enabled = false, description = "When debugging, this test is used to manually run just one specific test case.")
    public void testOneCaseByIndex() throws Exception
    {
        testOneCaseByIndex(162871);
    }

    private void testOneCaseByIndex(int caseIndex) throws Exception
    {
        BidiUcdTestCase[] testCases = loadTestCases();
        boolean passed = testOneCase(testCases[caseIndex], caseIndex, true);
        if (!passed)
        {
            Assert.fail("Test failed.");
        }
    }

    private boolean testOneCase(BidiUcdTestCase testCase, int testCaseIndex, boolean outputDebugInfo)
    {
        BidiRun[] expectedRuns = testCase.getExpectedRuns();
        BidiRun[] actualRuns = BidiAlgorithm.apply(testCase.getText(), testCase.isRtlParagraph());

        String expectedText = runsToString(expectedRuns);
        String actualText = runsToString(actualRuns);

        boolean testPassed = msString.equals(actualText, expectedText);
        if ((!testPassed) && outputDebugInfo)
        {
            String paragraphDirection = (testCase.isRtlParagraph())
                ? "RTL"
                : "LTR";
            Debug.writeLine(msString.format("Expected <{0}> but was <{1}> at line {2} ({3}). Case index {4}.", expectedText, actualText, testCase.getLineNumber(), paragraphDirection, testCaseIndex));
        }
        return testPassed;
    }

    private String runsToString(BidiRun[] runs)
    {
        StringBuilder result = new StringBuilder();
        boolean firstRun = true;
        for (BidiRun run : runs)
        {
            String filteredRunText = filterOutBidiControlChars(run.getText());

            if (!StringUtil.hasChars(filteredRunText))
            {
                continue;
            }

            if (!firstRun)
            {
                result.append(' ');
            }
            firstRun = false;

            msStringBuilder.append(result, msString.format("{0}:(", run.getLevel()));

            boolean firstCodePoint = true;
            for (int c : new StringUtf32Enumerator(filteredRunText))
            {
                if (!firstCodePoint)
                {
                    result.append(' ');
                }
                firstCodePoint = false;

                msStringBuilder.append(result, FormatterPal.intToStrX4(c));
            }

            msStringBuilder.append(result, ")");
        }

        return result.toString();
    }

    private String filterOutBidiControlChars(String text)
    {
        StringBuilder result = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            // Our implementation of the Unicode Bidi Algorithm does not filter the boundary neutral (BN) characters,
            // while the original UBA does.
            boolean filtered = BidiAlgorithm.isControlChar(c) ||
                (UnicodeCharacterDataResolver.getBidiCharacterType(c) == BidiCharacterType.BN);
            if (!filtered)
            {
                result.append(c);
            }
        }
        return result.toString();
    }

    private BidiUcdTestCase[] loadTestCases() throws Exception
    {
        ArrayList<BidiUcdTestCase> result = new ArrayList<BidiUcdTestCase>();

        StreamReader testFile = new StreamReader(TestFxUtil.buildTestFileName("Bidi\\BidiTest.txt"));
        try /*JAVA: was using*/
        {
            int[] levels = new int[0];

            int lineNumber = 0;
            while (true)
            {
                String line = testFile.readLine();
                if (line == null)
                    break;

                ++lineNumber;

                if ((line.length() == 0) || msString.startsWith(line, "#", StringComparison.ORDINAL))
                {
                    // Skip comments and empty lines.
                    continue;
                }

                if (msString.startsWith(line, "@", StringComparison.ORDINAL))
                {
                    // Unknown labels are skipped for compatibility with different versions of the BidiTests.txt file.
                    // For example, older versions of the file contained "@Type" labels, which are removed now.
                    final String LEVELS_LABEL = "@Levels:\t";
                    if (line.startsWith(LEVELS_LABEL))
                    {
                        levels = parseLevels(line.substring(LEVELS_LABEL.length()));
                    }
                }
                else
                {
                    BidiUcdTestCase[] testCases = parseTestCases(line, levels, lineNumber);
                    for (BidiUcdTestCase testCase : testCases)
                    {
                        msArrayList.add(result, testCase);
                    }
                }
            }
        }
        finally { if (testFile != null) testFile.close(); }

        return msArrayList.toArray(result, new BidiUcdTestCase[0]);
    }

    private static int[] parseLevels(String levels)
    {
        if (!StringUtil.hasChars(levels))
        {
            return new int[0];
        }

        String[] parts = msString.split(levels, ' ');
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++)
        {
            if ("x".equals(parts[i]))
            {
                result[i] = -1;
            }
            else
            {
                result[i] = FormatterPal.parseInt(parts[i]);
            }
        }
        return result;
    }

    private BidiUcdTestCase[] parseTestCases(String testCases, int[] expectedLevels, int lineNumber)
    {
        ArrayList<BidiUcdTestCase> result = new ArrayList<BidiUcdTestCase>();

        int semicolonPosition = testCases.indexOf(';');
        if (semicolonPosition >= 0)
        {
            String text = parseTestCaseText(testCases.substring((0), (0) + (semicolonPosition)));
            int directions = FormatterPal.parseInt(testCases.substring(semicolonPosition + 1));
            if ((directions & 2) != 0)
            {
                // Left-to-Right.
                msArrayList.add(result, new BidiUcdTestCase(text, expectedLevels, false, lineNumber));
            }
            if ((directions & 4) != 0)
            {
                // Right-to-Left.
                msArrayList.add(result, new BidiUcdTestCase(text, expectedLevels, true, lineNumber));
            }
        }

        return msArrayList.toArray(result, new BidiUcdTestCase[0]);
    }

    private String parseTestCaseText(String text)
    {
        String[] bidiTypes = msString.split(text, ' ');
        StringBuilder result = new StringBuilder();
        for (String bidiType : bidiTypes)
        {
            result.append(getCharByBidiType(bidiType));
        }
        return result.toString();
    }

    private static char getCharByBidiType(String bidiType)
    {
        char result = G_CHARACTERS.get(bidiType);
        if (StringToCharDictionary.isNullSubstitute(result))
        {
            Assert.fail("Unknown bidi type: " + bidiType);
        }
        return result;
    }

    private static /*final*/ StringToCharDictionary G_CHARACTERS = new StringToCharDictionary();
    static/* TestBidiAlgorithmConformance()*/
    {
        G_CHARACTERS = new StringToCharDictionary();
        G_CHARACTERS.add("LRE", BidiChars.LRE);
        G_CHARACTERS.add("RLE", BidiChars.RLE);
        G_CHARACTERS.add("PDF", BidiChars.PDF);
        G_CHARACTERS.add("LRO", BidiChars.LRO);
        G_CHARACTERS.add("RLO", BidiChars.RLO);
        G_CHARACTERS.add("BN", '\u00AD'); // SOFT HYPHEN
        G_CHARACTERS.add("L", '\u0041'); // LATIN CAPITAL LETTER A
        G_CHARACTERS.add("R", '\u05D0'); // HEBREW LETTER ALEF
        G_CHARACTERS.add("EN", '\u0033'); // DIGIT THREE
        G_CHARACTERS.add("ES", '\u002B'); // PLUS SIGN
        G_CHARACTERS.add("ET", '\u0024'); // DOLLAR SIGN
        G_CHARACTERS.add("CS", '\u002C'); // COMMA
        G_CHARACTERS.add("NSM", '\u0302'); // COMBINING CIRCUMFLEX ACCENT
        G_CHARACTERS.add("B", '\n'); // LINE FEED (LF)
        G_CHARACTERS.add("S", '\u0009'); // CHARACTER TABULATION
        G_CHARACTERS.add("WS", '\u0020'); // SPACE
        G_CHARACTERS.add("ON", '\u0021'); // EXCLAMATION MARK
        G_CHARACTERS.add("AL", '\u062D'); // ARABIC LETTER HAA
        G_CHARACTERS.add("AN", '\u0663'); // ARABIC-INDIC DIGIT THREE
    }
}

class BidiUcdTestCase
{
    BidiUcdTestCase(String text, int[] expectedLevels, boolean isInRtlParagraph, int lineNumber)
    {
        Debug.assertTrue(StringUtf32Enumerator.getStringLength(text) == expectedLevels.length);

        mText = text;
        mExpectedLevels = expectedLevels;
        mIsRtlParagraph = isInRtlParagraph;
        mLineNumber = lineNumber;
    }

    String getText() { return mText; }

    boolean isRtlParagraph() { return mIsRtlParagraph; }

    int getLineNumber() { return mLineNumber; }

    BidiRun[] getExpectedRuns()
    {
        IntList resultCharacters = new IntList();
        IntList resultLevels = new IntList();

        int charIndex = 0;
        for (int utf32Char : new StringUtf32Enumerator(mText))
        {
            int expectedCharLevel = mExpectedLevels[charIndex];
            ++charIndex;

            // If the character is not removed by the Bidi Algorithm, append it to the result text.
            if (expectedCharLevel >= 0)
            {
                resultCharacters.add(utf32Char);
                resultLevels.add(expectedCharLevel);
            }
        }
        Debug.assertTrue(resultCharacters.getCount() == resultLevels.getCount());

        ArrayList<BidiRun> result = new ArrayList<BidiRun>();

        // Group characters by embedding level.
        int runLevel = -1;
        StringBuilder runText = new StringBuilder();
        for (int i = 0; i < (resultLevels.getCount() + 1); i++)
        {
            // An implicit end-of-text character with an invalid embedding level is used to terminate the last group.
            // This simplifies the processing loop and removes the code duplication (otherwise the last group
            // would be processed after the loop).
            int currentLevel = (i < resultLevels.getCount())
                ? resultLevels.get(i)
                : -1;

            if (currentLevel != runLevel)
            {
                if (runText.length() > 0)
                {
                    msArrayList.add(result, new BidiRun(runText.toString(), runLevel));
                }
                runLevel = currentLevel;
                runText.setLength(0);
            }

            if (currentLevel >= 0)
            {
                UnicodeUtil.appendUtf32(runText, resultCharacters.get(i));
            }
        }

        return msArrayList.toArray(result, new BidiRun[0]);
    }

    private /*final*/ String mText;
    private /*final*/ int[] mExpectedLevels;
    private /*final*/ boolean mIsRtlParagraph;
    private /*final*/ int mLineNumber;
}

