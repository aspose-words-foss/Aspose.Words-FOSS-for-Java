// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/datetimeformatinfoscanner.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.msChar;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;

import java.util.*;

class DateTimeFormatInfoCore
{
    // The collection of date words & postfix.
    private static List<String> m_dateWords = new ArrayList<String>(100);

    // Add ignorable symbol in a DateWord array.

    // hu-HU has:
    //      shrot date pattern: yyyy. MM. dd.;yyyy-MM-dd;yy-MM-dd
    //      long date pattern: yyyy. MMMM d.
    // Here, "." is the date separator (derived from short date pattern). However,
    // "." also appear at the end of long date pattern.  In this case, we just
    // "." as ignorable symbol so that the DateTime.Parse() state machine will not
    // treat the additional date separator at the end of y,m,d pattern as an error
    // condition.
    public static final char IgnorableSymbolChar = '\ue001';
    public static final char MonthPostfixChar = '\ue000';

    // Known CJK suffix
    private static String CJKYearSuff = "\u5e74";
    private static String CJKMonthSuff = "\u6708";
    private static String CJKDaySuff = "\u65e5";

    private static String KoreanYearSuff = "\ub144";
    private static String KoreanMonthSuff = "\uc6d4";
    private static String KoreanDaySuff = "\uc77c";

    private static String KoreanHourSuff = "\uc2dc";
    private static String KoreanMinuteSuff = "\ubd84";
    private static String KoreanSecondSuff = "\ucd08";

    private static String CJKHourSuff = "\u6642";
    private static String ChineseHourSuff = "\u65f6";

    private static String CJKMinuteSuff = "\u5206";
    private static String CJKSecondSuff = "\u79d2";

    //
    // Flag used to trace the date patterns (yy/yyyyy/M/MM/MMM/MMM/d/dd) that we have seen.
    //
    private static class FoundDatePattern
    {
        static int None = 0x0000,
                FoundYearPatternFlag = 0x0001,
                FoundMonthPatternFlag = 0x0002,
                FoundDayPatternFlag = 0x0004,
                FoundYMDPatternFlag = 0x0007; // FoundYearPatternFlag | FoundMonthPatternFlag | FoundDayPatternFlag;
    }

    // Check if we have found all of the year/month/day pattern.
    private static int m_ymdFlags = FoundDatePattern.None;


    private static String EMPTY = "";
    private static Map<String, String> m_knownWords = new HashMap<String, String>();

    static
    {
        Map temp = new HashMap();
        // Add known words into the hash table.

        // Skip these special symbols.
        temp.put("/", EMPTY);
        temp.put("-", EMPTY);
        temp.put(".", EMPTY);
        // Skip known CJK suffixes.
        temp.put(CJKYearSuff, EMPTY);
        temp.put(CJKMonthSuff, EMPTY);
        temp.put(CJKDaySuff, EMPTY);
        temp.put(KoreanYearSuff, EMPTY);
        temp.put(KoreanMonthSuff, EMPTY);
        temp.put(KoreanDaySuff, EMPTY);
        temp.put(KoreanHourSuff, EMPTY);
        temp.put(KoreanMinuteSuff, EMPTY);
        temp.put(KoreanSecondSuff, EMPTY);
        temp.put(CJKHourSuff, EMPTY);
        temp.put(ChineseHourSuff, EMPTY);
        temp.put(CJKMinuteSuff, EMPTY);
        temp.put(CJKSecondSuff, EMPTY);

        m_knownWords = temp;
    }


    ////////////////////////////////////////////////////////////////////////////
    //
    // Given a DTFI, get all of the date words from date patterns and time patterns.
    //
    ////////////////////////////////////////////////////////////////////////////

    public static String[] getDateWordsOfDTFI(msDateTimeFormatInfo dtfi)
    {
        // Enumarate all LongDatePatterns, and get the DateWords and scan for month postfix.
        try
        {
            String[] datePatterns = dtfi.getAllDateTimePatterns('D');
            int i;

            // Scan the long date patterns
            for (i = 0; i < datePatterns.length; i++)
            {
                scanDateWord(datePatterns[i]);
            }

            // Scan the short date patterns
            datePatterns = dtfi.getAllDateTimePatterns('d');
            for (i = 0; i < datePatterns.length; i++)
            {
                scanDateWord(datePatterns[i]);
            }
            // Scan the YearMonth patterns.
            datePatterns = dtfi.getAllDateTimePatterns('y');
            for (i = 0; i < datePatterns.length; i++)
            {
                scanDateWord(datePatterns[i]);
            }

            // Scan the month/day pattern
            scanDateWord(dtfi.getMonthDayPattern());

            // Scan the long time patterns.
            datePatterns = dtfi.getAllDateTimePatterns('T');
            for (i = 0; i < datePatterns.length; i++)
            {
                scanDateWord(datePatterns[i]);
            }

            // Scan the short time patterns.
            datePatterns = dtfi.getAllDateTimePatterns('t');
            for (i = 0; i < datePatterns.length; i++)
            {
                scanDateWord(datePatterns[i]);
            }

            String[] result = null;
            if (m_dateWords != null && m_dateWords.size() > 0)
            {
                result = new String[m_dateWords.size()];
                for (i = 0; i < m_dateWords.size(); i++)
                {
                    result[i] = (String) m_dateWords.get(i);
                }
            }
            return (result);

        }
        catch (Exception e)
        {
            Log.warn(e);
        }

        return null;
    }

    private static void scanDateWord(String pattern)
    {
        // Check if we have found all of the year/month/day pattern.
        m_ymdFlags = FoundDatePattern.None;

        int i = 0;
        while (i < pattern.length())
        {
            char ch = pattern.charAt(i);
            int[] chCount = new int[1];

            switch (ch)
            {
                case '\'':
                    // Find a beginning quote.  Search until the end quote.
                    i = addDateWords(pattern, i + 1, null);
                    break;
                case 'M':
                    i = scanRepeatChar(pattern, 'M', i, chCount);
                    if (chCount[0] >= 4)
                    {
                        if (i < pattern.length() && pattern.charAt(i) == '\'')
                        {
                            i = addDateWords(pattern, i + 1, "MMMM");
                        }
                    }
                    m_ymdFlags |= FoundDatePattern.FoundMonthPatternFlag;
                    break;
                case 'y':
                    i = scanRepeatChar(pattern, 'y', i, chCount);
                    m_ymdFlags |= FoundDatePattern.FoundYearPatternFlag;
                    break;
                case 'd':
                    i = scanRepeatChar(pattern, 'd', i, chCount);
                    if (chCount[0] <= 2)
                    {
                        // Only count "d" & "dd".
                        // ddd, dddd are day names.  Do not count them.
                        m_ymdFlags |= FoundDatePattern.FoundDayPatternFlag;
                    }
                    break;
                case '\\':
                    // Found a escaped char not in a quoted string.  Skip the current backslash
                    // and its next character.
                    i += 2;
                    break;
                case '.':
                    if (m_ymdFlags == FoundDatePattern.FoundYMDPatternFlag)
                    {
                        // If we find a dot immediately after the we have seen all of the y, m, d pattern.
                        // treat it as a ignroable symbol.  Check for comments in AddIgnorableSymbols for
                        // more details.
                        addIgnorableSymbols(".");
                        m_ymdFlags = FoundDatePattern.None;
                    }
                    i++;
                    break;
                default:
                    if (m_ymdFlags == FoundDatePattern.FoundYMDPatternFlag && !msChar.isWhiteSpace(ch))
                    {
                        // We are not seeing "." after YMD. Clear the flag.
                        m_ymdFlags = FoundDatePattern.None;
                    }
                    // We are not in quote.  Skip the current character.
                    i++;
                    break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // A simple helper to find the repeat count for a specified char.
    //
    ////////////////////////////////////////////////////////////////////////////
    private static int scanRepeatChar(String pattern, char ch, int index, int[] count)
    {
        count[0] = 1;
        while (++index < pattern.length() && pattern.charAt(index) == ch)
        {
            count[0]++;
        }
        // Return the updated position.
        return (index);
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // Scan the pattern from the specified index and add the date word/postfix
    // when appropriate.
    //
    //  Parameters:
    //      pattern: The pattern to be scanned.
    //      index: The starting index to be scanned.
    //      formatPostfix: The kind of postfix to be scanned.
    //          Possible values:
    //              null: This is a regular date word
    //              "MMMM": month postfix
    //
    //
    ////////////////////////////////////////////////////////////////////////////
    private static int addDateWords(String pattern, int index, String formatPostfix)
    {
        // Skip any whitespaces so we will start from a letter.
        int newIndex = skipWhiteSpacesAndNonLetter(pattern, index);
        if (newIndex != index && formatPostfix != null)
        {
            // There are whitespaces. This will not be a postfix.
            formatPostfix = null;
        }
        index = newIndex;

        // This is the first char added into dateWord.
        // Skip all non-letter character.  We will add the first letter into DateWord.
        StringBuilder dateWord = new StringBuilder();
        // We assume that date words should start with a letter.
        // Skip anything until we see a letter.

        while (index < pattern.length())
        {
            char ch = pattern.charAt(index);
            if (ch == '\'')
            {
                // We have seen the end of quote.  Add the word if we do not see it before,
                // and break the while loop.
                addDateWordOrPostfix(formatPostfix, dateWord.toString());
                index++;
                break;
            }
            else if (ch == '\\')
            {
                //
                // Escaped character.  Look ahead one character
                //

                // Skip escaped backslash.
                index++;
                if (index < pattern.length())
                {
                    dateWord.append(pattern.charAt(index));
                    index++;
                }
            }
            else if (msChar.isWhiteSpace(ch))
            {
                // Found a whitespace.  We have to add the current date word/postfix.
                addDateWordOrPostfix(formatPostfix, dateWord.toString());
                if (formatPostfix != null)
                {
                    // Done with postfix.  The rest will be regular date word.
                    formatPostfix = null;
                }
                // Reset the dateWord.
                dateWord.delete(0, dateWord.length()); // dateWord.length = 0;
                index++;
            }
            else
            {
                dateWord.append(ch);
                index++;
            }
        }
        return (index);
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    //  Parameters:
    //      pattern: The pattern to be scanned.
    //      currentIndex: the current index to start the scan.
    //
    //  Returns:
    //      Return the index with the first character that is a letter, which will
    //      be the start of a date word.
    //      Note that the index can be pattern.Length if we reach the end of the string.
    //
    ////////////////////////////////////////////////////////////////////////////
    private static int skipWhiteSpacesAndNonLetter(String pattern, int currentIndex)
    {
        while (currentIndex < pattern.length())
        {
            char ch = pattern.charAt(currentIndex);
            if (ch == '\\')
            {
                // Escaped character. Look ahead one character.
                currentIndex++;
                if (currentIndex < pattern.length())
                {
                    ch = pattern.charAt(currentIndex);
                    if (ch == '\'')
                    {
                        // Skip the leading single quote.  We will
                        // stop at the first letter.
                        continue;
                    }
                    // Fall thru to check if this is a letter.
                }
                else
                {
                    // End of string
                    break;
                }
            }
            if (Character.isLetter(ch) || ch == '\'' || ch == '.')
            {
                break;
            }
            // Skip the current char since it is not a letter.
            currentIndex++;
        }
        return (currentIndex);
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // A helper to add the found date word or month postfix into ArrayList for date words.
    //
    // Parameters:
    //      formatPostfix: What kind of postfix this is.
    //          Possible values:
    //              null: This is a regular date word
    //              "MMMM": month postfix
    //      word: The date word or postfix to be added.
    //
    ////////////////////////////////////////////////////////////////////////////
    private static void addDateWordOrPostfix(String formatPostfix, String str)
    {
        if (str.length() > 0)
        {
            // Some cultures use . like an abbreviation
            if (".".equals(str))
            {
                addIgnorableSymbols(".");
                return;
            }
            if (m_knownWords.get(str) == null)
            {
                if (m_dateWords == null)
                {
                    m_dateWords = new ArrayList();
                }
                if ("MMMM".equals(formatPostfix))
                {
                    // Add the word into the ArrayList as "\xfffe" + real month postfix.
                    String temp = MonthPostfixChar + str;
                    if (!m_dateWords.contains(temp))
                    {
                        m_dateWords.add(temp);
                    }
                }
                else
                {
                    if (!m_dateWords.contains(str))
                    {
                        m_dateWords.add(str);
                    }
                    if (str.charAt(str.length() - 1) == '.')
                    {
                        // Old version ignore the trialing dot in the date words. Support this as well.
                        String strWithoutDot = str.substring(0, str.length() - 1);
                        if (!m_dateWords.contains(strWithoutDot))
                        {
                            m_dateWords.add(strWithoutDot);
                        }

                    }
                }
            }
        }
    }

    private static void addIgnorableSymbols(String text)
    {
        if (m_dateWords == null)
        {
            // Create the date word array.
            m_dateWords = new ArrayList();
        }
        // Add the ingorable symbol into the ArrayList.
        String temp = IgnorableSymbolChar + text;
        if (!m_dateWords.contains(temp))
        {
            m_dateWords.add(temp);
        }
    }

    static int getFormatFlagGenitiveMonth(String[] monthNames, String[] genitveMonthNames, String[] abbrevMonthNames, String[] genetiveAbbrevMonthNames)
    {
        if (EqualStringArrays(monthNames, genitveMonthNames) && EqualStringArrays(abbrevMonthNames, genetiveAbbrevMonthNames))
        {
            return FORMATFLAGS.None;
        }
        return FORMATFLAGS.UseGenitiveMonth;
    }

    static int getFormatFlagUseHebrewCalendar(Calendar calendar, String cultureName)
    {
        //if (calID != 8)
        if (!"he-IL".equals(cultureName))
        {
            return FORMATFLAGS.None;
        }
        return ((FORMATFLAGS.UseHebrewParsing | FORMATFLAGS.UseLeapYearMonth));
    }


    static int getFormatFlagUseSpaceInDayNames(String[] dayNames, String[] abbrevDayNames)
    {
        if (!ArrayElementsHaveSpace(dayNames) && !ArrayElementsHaveSpace(abbrevDayNames))
        {
            return FORMATFLAGS.None;
        }
        return FORMATFLAGS.UseSpacesInDayNames;
    }


    static int getFormatFlagUseSpaceInMonthNames(String[] monthNames, String[] genitveMonthNames, String[] abbrevMonthNames, String[] genetiveAbbrevMonthNames)
    {
        int none = FORMATFLAGS.None;
        none |= ((ArrayElementsBeginWithDigit(monthNames) || ArrayElementsBeginWithDigit(genitveMonthNames)) || (ArrayElementsBeginWithDigit(abbrevMonthNames) || ArrayElementsBeginWithDigit(genetiveAbbrevMonthNames))) ? FORMATFLAGS.UseDigitPrefixInTokens : FORMATFLAGS.None;
        return (none | (((ArrayElementsHaveSpace(monthNames) || ArrayElementsHaveSpace(genitveMonthNames)) || (ArrayElementsHaveSpace(abbrevMonthNames) || ArrayElementsHaveSpace(genetiveAbbrevMonthNames))) ? FORMATFLAGS.UseSpacesInMonthNames : FORMATFLAGS.None));
    }


    private static boolean ArrayElementsHaveSpace(String[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            if(array[i] != null)
            {
                for (int j = 0; j < array[i].length(); j++)
                {
                    if (msChar.isWhiteSpace(array[i].charAt(j)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean EqualStringArrays(String[] array1, String[] array2)
    {
        if (array1.length != array2.length)
        {
            return false;
        }
        for (int i = 0; i < array1.length; i++)
        {
            if (!array1[i].equals(array2[i]))
            {
                return false;
            }
        }
        return true;
    }


    private static boolean ArrayElementsBeginWithDigit(String[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (((array[i] != null && array[i].length() > 0) && (array[i].charAt(0) >= '0')) && (array[i].charAt(0) <= '9'))
            {
                int num2 = 1;
                while (((num2 < array[i].length()) && (array[i].charAt(num2) >= '0')) && (array[i].charAt(num2) <= '9'))
                {
                    num2++;
                }
                if (num2 == array[i].length())
                {
                    return false;
                }
                if (num2 == (array[i].length() - 1))
                {
                    switch (array[i].charAt(num2))
                    {
                        case '月':
                        case 0xc6d4:
                            return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
