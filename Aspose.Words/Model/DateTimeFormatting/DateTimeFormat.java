// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2011 by Dmitry Matveenko
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.LanguageOnly;
import com.aspose.JapaneseNumber;
import com.aspose.ms.System.DayOfWeek;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringUtil;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Implements date and time formatting according to MS Word format strings (\\@ field switch argument).
/// </summary>
class DateTimeFormat
{
    /// <summary>
    /// Creates a new instance of DateTimeFormat class for a given date.
    /// </summary>
    protected DateTimeFormat(DateTime dateTime)
    {
    	this (dateTime, new GregorianCalendarFormatter());
	
    }

    /// <summary>
    /// Creates a new instance of DateTimeFormat class for a given date.
    /// </summary>
    protected DateTimeFormat(DateTime dateTime, CalendarFormatter calendarFormatter)
    {
        DateToFormat = dateTime;
        mCalendarFormatter = calendarFormatter;
    }

    /// <summary>
    /// Formats a DateTime value according to the given MS Word format string (\\@ switch argument)
    /// and East Asian language ID.
    /// </summary>
    static String formatDateTime(String wordFormat, DateTime d, int eastAsianLanguageId, /*CalendarType*/int calendarType)
    {
        CalendarFormatter calendarFormatter;
        switch (calendarType)
        {
            case CalendarType.GREGORIAN:
                calendarFormatter = new GregorianCalendarFormatter();
                break;
            case CalendarType.HIJRI:
                calendarFormatter = new HijriCalendarFormatter();
                break;
            case CalendarType.HEBREW:
                calendarFormatter = new HebrewCalendarFormatter();
                break;
            case CalendarType.SAKA_ERA:
            case CalendarType.UM_AL_QURA:
                calendarFormatter = new GregorianCalendarFormatter();
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "calendarType");
        }

        DateTimeFormat format = LanguageOnly.compare(eastAsianLanguageId, LanguageOnly.JAPANESE)
            ? new JapaneseDateTimeFormat(d)
            : new DateTimeFormat(d, calendarFormatter);

        return format.formatDateTimeCore(wordFormat);
    }

    /// <summary>
    /// Gets year from date, in Arabic digits.
    /// This is a default implementation that uses Gregorian year.
    /// When overridden in a derived class may use other calendars.
    /// </summary>
    protected /*virtual*/ String getYearInArabicDigits(int pictureElementLength, String wordFormat, int charIdx)
    {
        int intYear = DateToFormat.getYear();
        String strYear = Integer.toString(intYear);

        boolean addLeadingZero = (pictureElementLength > 1) && (intYear < 10);

        return (addLeadingZero)
            ? "0" + strYear
            : strYear;
    }

    /// <summary>
    /// Gets year from date, in East Asian digits.
    /// This is a default implementation that uses Gregorian year.
    /// When overridden in a derived class may use other calendars.
    /// </summary>
    protected /*virtual*/ String getYearInEastAsianDigits(int pictureElementLength)
    {
        return getGregorianYearInJapaneseDigits(DateToFormat);
    }

    private static String getGregorianYearInJapaneseDigits(DateTime date)
    {
        final int DIGITS_IN_YEAR = 4;
        return JapaneseNumber.toJapaneseDigital(date.getYear(), DIGITS_IN_YEAR);
    }

    /// <summary>
    /// Gets a day name in East Asian language.
    /// This is the default implementation for European languages that returns picture element as literal text.
    /// </summary>
    protected /*virtual*/ String getEastAsianDayName()
    {
        return "w";
    }

    /// <summary>
    /// Gets a day name in East Asian language.
    /// This is the default implementation for European languages that returns a week day number as East Asian numeral.
    /// </summary>
    protected /*virtual*/ String getEastAsianDayNameOrNumber()
    {
        int dayNumber = (int)DateToFormat.getDayOfWeek();
        // Mimic Word. Strangely enough, it returns a numeral for all days except Sunday.
        return DateToFormat.getDayOfWeek() == DayOfWeek.SUNDAY
            ? "日"
            : JapaneseNumber.toJapaneseCounting(dayNumber);
    }

    /// <summary>
    /// Gets a day name in East Asian language, for 'aaa' picture element.
    /// This is the default implementation for European languages that returns picture element as literal text.
    /// </summary>
    protected /*virtual*/ String getEastAsianDayNameAaa(int pictureElementLength)
    {
        // Just repeat 'a' the given number of times.
        return msString.newString('a', pictureElementLength);
    }

    /// <summary>
    /// Gets an EastAsian AM/PM designator.
    /// This is the default implementation European languages.
    /// </summary>
    /// <returns></returns>
    protected /*virtual*/ String getEastAsianAmpmDesignator()
    {
        return getJapaneseAmpmHourDesignator(DateToFormat);
    }

    /// <summary>
    /// Formats a given DateTime value according to MS Word format string.
    /// </summary>
    private String formatDateTimeCore(String wordFormat)
    {
        boolean inQuotes = false;
        mFormattedValue.setLength(0);

        int charIdx = 0;
        while (charIdx < wordFormat.length())
        {
            char currentChar = wordFormat.charAt(charIdx);
            int pictureElementLength = 1;

            if(currentChar == '\'')
            {
                inQuotes = !inQuotes;
            }
            else if (inQuotes)
            {
                mFormattedValue.append(currentChar);
            }
            else
            {
                pictureElementLength = processAmpmPictureElement(wordFormat, charIdx);
                boolean ampmPictureElementFound = (pictureElementLength > 0);

                if(!ampmPictureElementFound)
                {
                    // Process the rest of the picture elements.
                    pictureElementLength = processPictureElements(wordFormat, charIdx);
                }
            }

            charIdx += pictureElementLength;
        }

        return mFormattedValue.toString();
    }

    /// <summary>
    /// Gets a Japanese Emperor era name for g, gg, ggg patterns.
    /// </summary>
    private String getJapaneseEra(int pictureElementLength)
    {
        String eraName;
        switch (pictureElementLength)
        {
            case 1:
                eraName = JapaneseDateTimeFormat.getJapaneseEraAbbreviatedEnglish(DateToFormat);
                break;
            case 2:
                eraName = JapaneseDateTimeFormat.getJapaneseEraAbbreviated(DateToFormat);
                break;
            case 3:
                eraName = JapaneseDateTimeFormat.getJapaneseEraName(DateToFormat);
                break;
            default:
                Debug.fail("Unexpected character count value in era pattern");
                eraName = "";
                break;
        }
        return eraName;
    }

    /// <summary>
    /// Gets a Japanese Emperor era name for G, GG patterns.
    /// </summary>
    private String getJapaneseEraGG(int pictureElementLength)
    {
        int gggAdjustedPictureElementLength = pictureElementLength + 1;
        return getJapaneseEra(gggAdjustedPictureElementLength);
    }

    /// <summary>
    /// Appends a date formatted via pattern that is supported by ToString() to the output.
    /// </summary>
    /// <returns>The length of the processed picture element.</returns>
    private int appendStandardPattern(String formatString, int tokenPosition, int maxCharRepetions)
    {
        int pictureElementLength = countSameCharacters(formatString, tokenPosition, maxCharRepetions);

        String formattedFragment = mCalendarFormatter.format(DateToFormat, formatString.charAt(tokenPosition), pictureElementLength, formatString, tokenPosition);
        msStringBuilder.append(mFormattedValue, formattedFragment);

        return pictureElementLength;
    }

    /// <summary>
    /// Looks for different forms of AM/PM pattern at the given position of the format string.
    /// If AM/PM pattern is found, appends the formatted date to the output.
    /// </summary>
    /// <returns>The length of the AM/PM pattern found, or 0.</returns>
    private int processAmpmPictureElement(String wordFormat, int charIdx)
    {
        String currentFormatSubstring = wordFormat.substring(charIdx).toUpperCase();

        // WORDSNET-7025 Added "tt" pattern for backward compatibility.
        String[] languageSpecificPictureElements = { "AM/PM", "PM/AM", "A/P", "P/A", "TT" };

        int pictureElementLength = tryAmpmPictureElements(currentFormatSubstring, languageSpecificPictureElements, false);

        boolean patternFound = (pictureElementLength > 0);
        if (!patternFound)
        {
            String[] japanesePictureElements = {"JAM/JPM", "JPM/JAM", "AMPM", "PMAM"};
            pictureElementLength = tryAmpmPictureElements(currentFormatSubstring, japanesePictureElements, true);
        }

        return pictureElementLength;
    }

    /// <summary>
    /// Tries AM/PM picture elements from the given array.
    /// If AM/PM pattern is found, appends the formatted date to the output.
    /// </summary>
    private int tryAmpmPictureElements(String currentFormatSubstring, String[] pictureElements, boolean isJapanesePattern)
    {
        int pictureElementLength = getPrefixLength(currentFormatSubstring, pictureElements);
        boolean patternFound = (pictureElementLength > 0);
        if (patternFound)
        {
            final String DOT_NET_AMPM_PATTERN = "tt";
            String ampmValue = isJapanesePattern
                ? getEastAsianAmpmDesignator()
                : DateToFormat.toString(DOT_NET_AMPM_PATTERN);

            msStringBuilder.append(mFormattedValue, ampmValue);
        }
        return pictureElementLength;
    }

    /// <summary>
    /// Gets the length of the first prefix from a given array that starts the given string,
    /// or 0 if such prefix is not found.
    /// </summary>
    private static int getPrefixLength(String hostString, String[] possiblePrefixes)
    {
        for(String prefix : possiblePrefixes)
        {
            Debug.assertTrue(StringUtil.hasChars(prefix));

            if (msString.startsWith(hostString, prefix, StringComparison.ORDINAL))
                return prefix.length();
        }

        return 0;
    }

    /// <summary>
    /// Takes a picture element from the given position of the format string
    /// and appends the date formatted according to the picture element to the output.
    /// </summary>
    private int processPictureElements(String wordFormat, int charIdx)
    {
        int pictureElementLength = 1;
        char currentChar = wordFormat.charAt(charIdx);
        int maxCharRepetions;
        String formattedFragment;

        switch (currentChar)
        {
            case 'Y':
            case 'y':
            case 'M':
            case 'D':
            case 'd':
                maxCharRepetions = 4;
                pictureElementLength = appendStandardPattern(wordFormat, charIdx, maxCharRepetions);
                break;
            case 'H':
            case 'h':
            case 'm':
            case 'S':
            case 's':
                maxCharRepetions = 2;
                pictureElementLength = appendStandardPattern(wordFormat, charIdx, maxCharRepetions);
                break;
            case 'g':
                maxCharRepetions = 3;
                pictureElementLength = countSameCharacters(wordFormat, charIdx, maxCharRepetions);
                formattedFragment = getJapaneseEra(pictureElementLength);
                msStringBuilder.append(mFormattedValue, formattedFragment);
                break;
            case 'G':
                maxCharRepetions = 2;
                pictureElementLength = countSameCharacters(wordFormat, charIdx, maxCharRepetions);
                formattedFragment = getJapaneseEraGG(pictureElementLength);
                msStringBuilder.append(mFormattedValue, formattedFragment);
                break;
            case 'n':
                maxCharRepetions = 2;
                pictureElementLength = countSameCharacters(wordFormat, charIdx, maxCharRepetions);
                formattedFragment = JapaneseDateTimeFormat.getJapaneseYearInArabicDigits(pictureElementLength, DateToFormat, wordFormat, charIdx);
                msStringBuilder.append(mFormattedValue, formattedFragment);
                break;
            case 'e':
                maxCharRepetions = 2;
                pictureElementLength = countSameCharacters(wordFormat, charIdx, maxCharRepetions);
                formattedFragment = getYearInArabicDigits(pictureElementLength, wordFormat, charIdx);
                msStringBuilder.append(mFormattedValue, formattedFragment);
                break;
            case 'E':
                maxCharRepetions = getMaxCharEastAsianYearPictureElementRepetitions();
                pictureElementLength = countSameCharacters(wordFormat, charIdx, maxCharRepetions);
                formattedFragment = getYearInEastAsianDigits(pictureElementLength);
                msStringBuilder.append(mFormattedValue, formattedFragment);
                break;
            case 'w':
                formattedFragment = getEastAsianDayName();
                msStringBuilder.append(mFormattedValue, formattedFragment);
                break;
            case 'W':
                formattedFragment = getEastAsianDayNameOrNumber();
                msStringBuilder.append(mFormattedValue, formattedFragment);
                break;
            case 'a':
                maxCharRepetions = 3;
                pictureElementLength = countSameCharacters(wordFormat, charIdx, maxCharRepetions);
                formattedFragment = getEastAsianDayNameAaa(pictureElementLength);
                msStringBuilder.append(mFormattedValue, formattedFragment);
                break;
            case 'O':
                appendJapaneseNumeral(DateToFormat.getMonth());
                break;
            case 'A':
                appendJapaneseNumeral(DateToFormat.getDay());
                break;
            case 'r':
                maxCharRepetions = 2;
                pictureElementLength = countSameCharacters(wordFormat, charIdx, maxCharRepetions);
                appendJapaneseAmpmHourDesignator(pictureElementLength);
                appendJapaneseHour();
                break;
            case 'R':
                maxCharRepetions = 2;
                pictureElementLength = countSameCharacters(wordFormat, charIdx, maxCharRepetions);
                appendJapaneseAmpmHourDesignator(pictureElementLength);
                appendJapaneseHourR(pictureElementLength);
                break;
            case 'I':
                appendJapaneseNumeral(DateToFormat.getMinute());
                break;
            case 'C':
                appendJapaneseNumeral(DateToFormat.getSecond());
                break;
            default:
                mFormattedValue.append(currentChar);
                break;
        }

        return pictureElementLength;
    }

    protected /*virtual*/ int getMaxCharEastAsianYearPictureElementRepetitions() { return 4; }

    /// <summary>
    /// Counts up to maxRepetions occurrences of the character specified by the startPosition in the input string.
    /// </summary>
    private static int countSameCharacters(String input, int startPosition, int maxRepetitions)
    {
        char startChar = input.charAt(startPosition);
        int repetitions = 1;
        int currentPosition = startPosition + 1;
        while ((repetitions < maxRepetitions) && (currentPosition < input.length()) && (input.charAt(currentPosition) == startChar))
        {
            repetitions++;
            currentPosition++;
        }
        return repetitions;
    }

    private void appendJapaneseNumeral(int number)
    {
        msStringBuilder.append(mFormattedValue, JapaneseNumber.toJapaneseCounting(number));
    }

    /// <summary>
    /// Appends hours formatted according to r, rr patterns.
    /// </summary>
    private void appendJapaneseHour()
    {
        int hour = to12Hour(DateToFormat.getHour());
        appendJapaneseNumeral(hour);
    }

    private static int to12Hour(int hour24)
    {
        final int TWELVE = 12;
        int hour12 = hour24 % TWELVE;
        return (hour12 != 0)
            ? hour12
            : TWELVE;
    }

    /// <summary>
    /// Appends hours formatted according to R, RR patterns.
    /// </summary>
    private void appendJapaneseHourR(int pictureElementLength)
    {
        String formattedFragment;
        int hour = DateToFormat.getHour();

        if (pictureElementLength == 1)
        {
            // 24-based hour as Japanese numeral.
            formattedFragment = JapaneseNumber.toJapaneseCounting(hour);
        }
        else
        {
            // 12-based our in Arabic digits.
            hour = to12Hour(hour);
            formattedFragment = Integer.toString(hour);
        }

        msStringBuilder.append(mFormattedValue, formattedFragment);
    }

    /// <summary>
    /// Appends Japanese AM/PM hour designator if needed.
    /// </summary>
    private void appendJapaneseAmpmHourDesignator(int pictureElementLength)
    {
        boolean appendAmpm = pictureElementLength > 1;
        if (appendAmpm)
            msStringBuilder.append(mFormattedValue, getJapaneseAmpmHourDesignator(DateToFormat));
    }

    private static String getJapaneseAmpmHourDesignator(DateTime date)
    {
        return (date.getHour() < 12)
            ? "上午"
            : "下午";
    }

    /// <summary>
    /// Stores the date being formatted.
    /// </summary>
    protected /*final*/ DateTime DateToFormat;

    private /*final*/ CalendarFormatter mCalendarFormatter;

    /// <summary>
    /// Stores the formatted output.
    /// </summary>
    private /*final*/ StringBuilder mFormattedValue = new StringBuilder();
}

