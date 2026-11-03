// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/datetimeformatinfo.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.DayOfWeek;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;
import com.aspose.words.net.System.Globalization.DateTimeFormatInfo;

import java.text.DateFormatSymbols;
import java.util.Locale;

/**
 * Custom java implementation of http://msdn.microsoft.com/en-us/library/system.globalization.datetimeformatinfo(v=vs.71).aspx.
 * according to its source code.
 * <p>
 * Many .Net public properties and methods set private on Java because not used.
 * Any of the members can be set public again after appropriate tests.
 */
public class msDateTimeFormatInfo implements Cloneable, IFormatProvider
{

    public msDateTimeFormatInfo()
    {
        this(CurrentThread.getCurrentCulture());
    }

    public msDateTimeFormatInfo(msCultureInfo info)
    {
        this(info.getCultureData(), info.getCalendar());
    }

    public msDateTimeFormatInfo(CultureData cultureData, Calendar calendar)
    {
        Locale locale = cultureData.getLocale();

        cultureName = cultureData.getName();
        languageName = locale.getLanguage();
        this.calendar = calendar;

        String localeString = locale.toString();
        isHebrew = "he_IL".equals(localeString) || "iw_IL".equals(localeString);
        isHijri = localeString.startsWith("ar_") || localeString.startsWith("ar-");
        // It is necessary to use special constants for day and month names in case it is a Basque culture.
        // So use boolean flag for that (see WORDSJAVA-2698).
        isBasque = "eu_ES".equals(localeString);

        // In the en-GB locale, the correct abbreviation is "Sept", while in the en-US locale it is "Sep".
        // With Windows 11, the default value for the en-NZ locale, which is our standard testing locale,
        // has changed, and now for en-NZ it follows the British convention as "Sept".
        // So use boolean flag for that
        isEnNz = "en_NZ".equals(localeString);

        // Get some constants from java.
        dateFormatSymbols = new DateFormatSymbols(locale);
        ABBREVIATED_DAY_NAMES = getAbbreviatedDayOfWeekNames();
        SUPER_SHORT_DAY_NAMES = getSuperShortDayOfWeekNames();
        DAY_NAMES = getDayOfWeekNames();
        ABBREVIATED_MONTH_NAMES = getAbbreviatedMonthNames();
        MONTH_NAMES = getMonthNames();
        ERA_NAMES = getEraNames();

        mLocale = locale;

        CalendarData data = cultureData.getCalendarData();
        setLongTimePattern(data.getLongTimePattern());
        setLongDatePattern(data.getLongDatePattern());
        setShortDatePattern(data.getShortDatePattern());
        setShortTimePattern(data.getShortTimePattern());
        setMonthDayPattern(data.getMonthDayPattern());
        setYearMonthPattern(data.getYearMonthPattern());
        setDateSeparator(data.getDateSeparator());
        setTimeSeparator(data.getTimeSeparator());
        setFullDateTimePattern(data.getFullDateTimePattern());
        setPMDesignator(data.getPMDesignator());
        setAMDesignator(data.getAMDesignator());
        setRFC1123Pattern(data.getRfc1123Pattern());
        setSortableDateTimePattern(data.getSortableDateTimePattern());
        setUniversalSortableDateTimePattern(data.getUniversalSortableDateTimePattern());

        clearTokenHashTable(true);
    }

    public static msDateTimeFormatInfo getCurrentInfo()
    {
        // Returns the DateTimeFormat connected to the current Culture (if any) or creates a new one.
        return CurrentThread.getCurrentCulture().getDateTimeFormat();
    }

    public static msDateTimeFormatInfo getInstance(IFormatProvider provider)
    {
        //JAVA-changed: simplified

        // Fast case for a regular CultureInfo
        if (provider instanceof msCultureInfo)
        {
            msCultureInfo cultureProvider = (msCultureInfo) provider;
            return cultureProvider.getDateTimeFormat();
        }

        // Fast case for a DTFI;
        if (provider instanceof msDateTimeFormatInfo)
            return (msDateTimeFormatInfo) provider;

        return getCurrentInfo();
    }

    /**
     * JAVA-added for PublicApiChangeMap.
     */
    public static DateTimeFormatInfo toJava(msDateTimeFormatInfo msFormatInfo)
    {
        return new DateTimeFormatInfo(msFormatInfo);
    }

    /**
     * JAVA-added for PublicApiChangeMap.
     */
    public static msDateTimeFormatInfo fromJava(DateTimeFormatInfo formatInfo)
    {
        if (formatInfo == null)
            return null;

        return formatInfo.getMsFormatInfo();
    }

    public String getCultureName()
    {
        return cultureName;
    }

    public String getLanguageName()
    {
        return languageName;
    }

    boolean tokenize(int tokenMask, DateTimeString.TokenTypeValue token, DateTimeString str)
    {
        token.setType(DateTimeString.TokenType.UnknownToken);
        token.setValue(0);
        char current = str.getCurrent();
        boolean flag = Character.isLetter(current);

        if (flag)
        {
            current = Character.toLowerCase(current);
            if ((isHebrewChar(current) && (tokenMask == DateTimeString.TokenType.RegularTokenMask)) && tryParseHebrewNumber(str, token))
            {
                token.setType(DateTimeString.TokenType.HebrewNumber);
                return true;
            }
        }

        int index = current % '\u00c7';

        int num2 = 1 + (current % '\u00c5');
        int num3 = str.getLen() - str.getIndex();
        int num4 = 0;
        TokenHashValue[] dtfiTokenHash = m_dtfiTokenHash;

        if (dtfiTokenHash == null)
            dtfiTokenHash = createTokenHashTable();

        do
        {
            TokenHashValue value2 = dtfiTokenHash[index];

            if (value2 == null)
                break;

            if (((value2.tokenType & tokenMask) > 0) && (value2.tokenString.length() <= num3))
            {
                if (msString.compare(str.getValue(), str.getIndex(), value2.tokenString, 0, value2.tokenString.length(), true) == 0)
                {
                    int num5;
                    if (flag && ((num5 = str.getIndex() + value2.tokenString.length()) < str.getLen()))
                    {
                        char c = str.getValue().charAt(num5);
                        if (Character.isLetter(c))
                            return false;
                    }
                    token.setType(value2.tokenType & tokenMask);
                    token.setValue(value2.tokenValue);
                    str.advance(value2.tokenString.length());

                    return true;
                }
                if ((value2.tokenType == DateTimeString.TokenType.MonthToken) && hasSpacesInMonthNames)
                {
                    int matchLength = str.matchSpecifiedWords(value2.tokenString, true);
                    if (matchLength != -1)
                    {
                        token.setType(value2.tokenType & tokenMask);
                        token.setValue(value2.tokenValue);
                        str.advance(matchLength);
                        return true;
                    }
                }
                else if ((value2.tokenType == DateTimeString.TokenType.DayOfWeekToken) && hasSpacesInDayNames)
                {
                    int num7 = str.matchSpecifiedWords(value2.tokenString, true);
                    if (num7 != -1)
                    {
                        token.setType(value2.tokenType & tokenMask);
                        token.setValue(value2.tokenValue);
                        str.advance(num7);
                        return true;
                    }
                }
            }
            num4++;
            index += num2;
            if (index >= 0xc7)
                index -= 0xc7;

        }
        while (num4 < 0xc7);

        return false;
    }

    // DTFI properties should call this when the setter are called.
    private void clearTokenHashTable(boolean scanDateWords)
    {
        m_dtfiTokenHash = null;
        m_dateWords = null;
        // The properties in this class have been changed, and we need to re-generate
        // date words & format flags, instead of using the table values.
        m_scanDateWords = scanDateWords;
        // formatFlags = DateTimeFormatFlags.NotInitialized;
    }

    private TokenHashValue[] createTokenHashTable()
    {
        TokenHashValue[] dtfiTokenHash = m_dtfiTokenHash;
        if (dtfiTokenHash == null)
        {
            dtfiTokenHash = new TokenHashValue[0xc7];

            InsertHash(dtfiTokenHash, ",", DateTimeString.TokenType.IgnorableSymbol, 0);
            InsertHash(dtfiTokenHash, ".", DateTimeString.TokenType.IgnorableSymbol, 0);

            InsertHash(dtfiTokenHash, timeSeparator, DateTimeString.TokenType.SEP_Time, 0);
            InsertHash(dtfiTokenHash, AMDesignator, DateTimeString.TokenType.SEP_Am | DateTimeString.TokenType.Am, 0);
            InsertHash(dtfiTokenHash, PMDesignator, DateTimeString.TokenType.SEP_Pm | DateTimeString.TokenType.Pm, 1);

            if ("sq-AL".equals(cultureName))
            {
                InsertHash(dtfiTokenHash, "." + AMDesignator, DateTimeString.TokenType.SEP_Am | DateTimeString.TokenType.Am, 0);
                InsertHash(dtfiTokenHash, "." + PMDesignator, DateTimeString.TokenType.SEP_Pm | DateTimeString.TokenType.Pm, 1);
            }

            // CJK suffix
            InsertHash(dtfiTokenHash, "年", DateTimeString.TokenType.SEP_YearSuff, 0);
            InsertHash(dtfiTokenHash, "년", DateTimeString.TokenType.SEP_YearSuff, 0);
            InsertHash(dtfiTokenHash, "月", DateTimeString.TokenType.SEP_MonthSuff, 0);
            InsertHash(dtfiTokenHash, "월", DateTimeString.TokenType.SEP_MonthSuff, 0);
            InsertHash(dtfiTokenHash, "日", DateTimeString.TokenType.SEP_DaySuff, 0);
            InsertHash(dtfiTokenHash, "일", DateTimeString.TokenType.SEP_DaySuff, 0);

            InsertHash(dtfiTokenHash, "時", DateTimeString.TokenType.SEP_HourSuff, 0);
            InsertHash(dtfiTokenHash, "时", DateTimeString.TokenType.SEP_HourSuff, 0);
            InsertHash(dtfiTokenHash, "分", DateTimeString.TokenType.SEP_MinuteSuff, 0);
            InsertHash(dtfiTokenHash, "秒", DateTimeString.TokenType.SEP_SecondSuff, 0);

            if ("ko".equals(languageName))
            {
                InsertHash(dtfiTokenHash, "시", DateTimeString.TokenType.SEP_HourSuff, 0);
                InsertHash(dtfiTokenHash, "분", DateTimeString.TokenType.SEP_MinuteSuff, 0);
                InsertHash(dtfiTokenHash, "초", DateTimeString.TokenType.SEP_SecondSuff, 0);
            }

            if ("ky-KG".equals(cultureName))
                InsertHash(dtfiTokenHash, "-", DateTimeString.TokenType.IgnorableSymbol, 0);
            else
                InsertHash(dtfiTokenHash, "-", DateTimeString.TokenType.SEP_DateOrOffset, 0);

            String[] dateWords = null;
            DateTimeFormatInfoCore scanner = null;

            // Get the all of the long date pattern.  The getter will check if the default LongDatePattern
            // is in the standard list or not.  If not, m_scanDateWords will be true, and we will
            // need to scan the date words.
            // Note that dateWords is used as a temp buffer here.  It will be reset as the real date words later.

            // FIX WORDSJAVA-654: Field doesn't respect custom format while formatting a Date from Document Custom Properties
            // We shouldn't reset m_scanDateWords here because in this case repeated calls of createTokenHashTable()
            // initialize dateWords by the different values.
            if (m_dateWords == null)
            {
                if (m_scanDateWords)
                {
                    scanner = new DateTimeFormatInfoCore();
                    // Enumerate all LongDatePatterns, and get the DateWords and scan for month postfix.
                    m_dateWords = scanner.getDateWordsOfDTFI(this);
                }
                else
                {
                    m_dateWords = getClonedAllLongDatePatterns();
                }
            }

            dateWords = m_dateWords;

            // For some cultures, the date separator works more like a comma, being allowed before or after any date part.
            // In this cultures, we do not use normal date separator since we disallow date separator after a date terminal state.
            // This is determinted in DateTimeFormatInfoScanner.  Use this flag to determine if we should treat date separator as ignorable symbol.
            boolean useDateSepAsIgnorableSymbol = false;
            String monthPostfix;
            if (dateWords != null)
            {
                // There are DateWords.  It could be a real date word (such as "de"), or a monthPostfix.
                // The monthPostfix starts with '\xfffe' (MonthPostfixChar), followed by the real monthPostfix.
                for (int i = 0; i < dateWords.length; i++)
                {
                    switch (dateWords[i].charAt(0))
                    {
                        // This is a month postfix
                        case DateTimeFormatInfoCore.MonthPostfixChar:
                            // Get the real month postfix.
                            monthPostfix = dateWords[i].substring(1);
                            // Add the month name + postfix into the token.
                            addMonthNames(dtfiTokenHash, monthPostfix);
                            break;
                        case DateTimeFormatInfoCore.IgnorableSymbolChar:
                            String symbol = dateWords[i].substring(1);
                            InsertHash(dtfiTokenHash, symbol, DateTimeString.TokenType.IgnorableSymbol, 0);
                            if (getDateSeparator().trim().equals(symbol))
                            {
                                // The date separator is the same as the ingorable symbol.
                                useDateSepAsIgnorableSymbol = true;
                            }
                            break;
                        default:
                            InsertHash(dtfiTokenHash, dateWords[i], DateTimeString.TokenType.DateWordToken, 0);
                            if ("eu-ES".equals(cultureName))
                            {
                                // Basque has date words with leading dots
                                InsertHash(dtfiTokenHash, "." + dateWords[i], DateTimeString.TokenType.DateWordToken, 0);
                            }
                            break;
                    }
                }
            }

            if (!useDateSepAsIgnorableSymbol)
            {
                // Use the normal date separator.
                InsertHash(dtfiTokenHash, getDateSeparator(), DateTimeString.TokenType.SEP_Date, 0);
            }

            addMonthNames(dtfiTokenHash, null);
            for (int i = 1; i < 13; i++)
                InsertHash(dtfiTokenHash, getAbbreviatedMonthName(i), DateTimeString.TokenType.MonthToken, i);

            if ("gl-ES".equals(cultureName))
            {
                for (int num3 = 1; num3 < 13; num3++)
                {
                    String monthName = getMonthName(num3);
                    if (monthName.length() > 0)
                        InsertHash(dtfiTokenHash, monthName + "de", DateTimeString.TokenType.MonthToken, num3);
                }
            }

            if ((formatFlags & DateTimeFormatFlags.UseGenitiveMonth) != DateTimeFormatFlags.None)
            {
                for (int num4 = 1; num4 < 13; num4++)
                {
                    String str4 = internalGetMonthName(num4, DateTimeFormat.MonthNameStyles.Genitive, false);
                    InsertHash(dtfiTokenHash, str4, DateTimeString.TokenType.MonthToken, num4);
                }
            }
            if ((formatFlags & DateTimeFormatFlags.UseLeapYearMonth) != DateTimeFormatFlags.None)
            {
                for (int num5 = 1; num5 < 13; num5++)
                {
                    String str5 = internalGetMonthName(num5, DateTimeFormat.MonthNameStyles.LeapYear, false);
                    InsertHash(dtfiTokenHash, str5, DateTimeString.TokenType.MonthToken, num5);
                }
            }

            for (int j = 0; j < 7; j++)
            {
                String dayName = getDayName(j);
                InsertHash(dtfiTokenHash, dayName, DateTimeString.TokenType.DayOfWeekToken, j);
                dayName = getAbbreviatedDayName(j);
                InsertHash(dtfiTokenHash, dayName, DateTimeString.TokenType.DayOfWeekToken, j);
            }

            int[] eras = calendar.getEras();
            for (int k = 1; k <= eras.length; k++)
            {
                InsertHash(dtfiTokenHash, getEraName(k), DateTimeString.TokenType.EraToken, k);
                InsertHash(dtfiTokenHash, getAbbreviatedEraName(k), DateTimeString.TokenType.EraToken, k);
            }

            if ("ja".equals(languageName))
            {
                // Japanese allows day of week forms like: "(Tue)"
                for (int num8 = 0; num8 < 7; num8++)
                {
                    String str7 = "(" + getAbbreviatedDayName(num8) + ")";
                    InsertHash(dtfiTokenHash, str7, DateTimeString.TokenType.DayOfWeekToken, num8);
                }
            }

            InsertHash(dtfiTokenHash, getInvariantInfo().AMDesignator, DateTimeString.TokenType.SEP_Am | DateTimeString.TokenType.Am, 0);
            InsertHash(dtfiTokenHash, getInvariantInfo().PMDesignator, DateTimeString.TokenType.SEP_Pm | DateTimeString.TokenType.Pm, 1);

            // Add invariant month names and day names.
            for (int m = 1; m <= 12; m++)
            {
                String str;
                // We have to call public methods here to work with inherited DTFI.
                // Insert the month name first, so that they are at the front of abbrevaited
                // month names.
                str = getInvariantInfo().getMonthName(m);
                InsertHash(dtfiTokenHash, str, DateTimeString.TokenType.MonthToken, m);
                str = getInvariantInfo().getAbbreviatedMonthName(m);
                InsertHash(dtfiTokenHash, str, DateTimeString.TokenType.MonthToken, m);
            }

            for (int n = 0; n < 7; n++)
            {
                // We have to call public methods here to work with inherited DTFI.
                String str = getInvariantInfo().getDayName(/*(DayOfWeek)*/n);
                InsertHash(dtfiTokenHash, str, DateTimeString.TokenType.DayOfWeekToken, n);

                str = getInvariantInfo().getAbbreviatedDayName(/*(DayOfWeek)*/n);
                InsertHash(dtfiTokenHash, str, DateTimeString.TokenType.DayOfWeekToken, n);
            }

            // m_abbrevEnglishEraNames[0] contains the name for era 1, so the token value is i+1.
            for (int i = 0; i < getAbbreviatedEnglishEraNames().length; i++)
                InsertHash(dtfiTokenHash, getAbbreviatedEnglishEraNames()[i], DateTimeString.TokenType.EraToken, i + 1);

            InsertHash(dtfiTokenHash, LocalTimeMark, DateTimeString.TokenType.SEP_LocalTimeMark, 0);
            InsertHash(dtfiTokenHash, DateTimeParse.GMTName, DateTimeString.TokenType.TimeZoneToken, 0);
            InsertHash(dtfiTokenHash, DateTimeParse.ZuluName, DateTimeString.TokenType.TimeZoneToken, 0);
            InsertHash(dtfiTokenHash, gInvariantDateSeparator, DateTimeString.TokenType.SEP_Date, 0);
            InsertHash(dtfiTokenHash, gInvariantTimeSeparator, DateTimeString.TokenType.SEP_Time, 0);
            m_dtfiTokenHash = dtfiTokenHash;
        }

        return dtfiTokenHash;
    }

    private void addMonthNames(TokenHashValue[] temp, String monthPostfix)
    {
        for (int i = 1; i < 13; i++)
        {
            String monthName = getMonthName(i);
            if (monthName.length() > 0)
            {
                if (monthPostfix != null)
                    InsertHash(temp, monthName + monthPostfix, DateTimeString.TokenType.MonthToken, i);
                else
                    InsertHash(temp, monthName, DateTimeString.TokenType.MonthToken, i);
            }
            monthName = getAbbreviatedMonthName(i);
            InsertHash(temp, monthName, DateTimeString.TokenType.MonthToken, i);
        }
    }

    private static void InsertHash(TokenHashValue[] hashTable, String str, int tokenType, int tokenValue)
    {
        TokenHashValue value2;
        if ((str == null) || (str.length() == 0))
            return;

        int pos = 0;
        if (msChar.isWhiteSpace(str.charAt(0)) || msChar.isWhiteSpace(str.charAt(str.length() - 1)))
        {
            str = str.trim();
            if (str.length() == 0)
                return;
        }
        char ch = Character.toLowerCase(str.charAt(0));
        int index = ch % '\u00c7';
        int hashProbe = 1 + (ch % '\u00c5');

        // Label_0068:
        do
        {
            value2 = hashTable[index];
            if (value2 == null)
            {
                hashTable[index] = new TokenHashValue(str, tokenType, tokenValue);
                return;
            }
            else
            {
                if ((str.length() >= value2.tokenString.length()) && (msString.compare(str, 0, value2.tokenString, 0, value2.tokenString.length(), true) == 0))
                {
                    if (str.length() > value2.tokenString.length())
                    {
                        insertAtCurrentHashNode(hashTable, str, ch, tokenType, tokenValue, pos, index, hashProbe);
                        return;
                    }
                    if ((tokenType & DateTimeString.TokenType.SeparatorTokenMask) != (value2.tokenType & DateTimeString.TokenType.SeparatorTokenMask))
                    {
                        value2.tokenType |= tokenType;
                        if (tokenValue != 0)
                            value2.tokenValue = tokenValue;
                    }
                    //TODO:
                    return;
                }
                pos++;
                index += hashProbe;
                if (index >= 0xc7)
                    index -= 0xc7;
            }
        }
        while (pos < 0xc7);
    }

    private static void insertAtCurrentHashNode(TokenHashValue[] hashTable, String str, char ch, int tokenType, int tokenValue, int pos, int hashcode, int hashProbe)
    {
        TokenHashValue value2 = hashTable[hashcode];
        hashTable[hashcode] = new TokenHashValue(str, tokenType, tokenValue);
        while (++pos < 0xc7)
        {
            hashcode += hashProbe;
            if (hashcode >= 0xc7)
                hashcode -= 0xc7;

            TokenHashValue value3 = hashTable[hashcode];
            if ((value3 == null) || (Character.toLowerCase(value3.tokenString.charAt(0)) == ch))
            {
                hashTable[hashcode] = value2;
                if (value3 == null)
                {
                    return;
                }
                value2 = value3;
            }
        }
    }

    private String[] getAbbreviatedDayOfWeekNames()
    {
        if (isHebrew)
            return calendar instanceof HebrewCalendar ? HEBREW_ABBREVIATION_DAYS : HEBREW_SHORT_DAYS;

        if (isHijri)
            return HIJRI_DAYS;

        if(isBasque)
            return BASQUE_ABBREVIATION_DAYS;

        return removeFirst(dateFormatSymbols.getShortWeekdays());
    }

    private String[] getSuperShortDayOfWeekNames()
    {
        return removeFirst(dateFormatSymbols.getShortWeekdays());
    }

    private String[] getDayOfWeekNames()
    {
        if (isHebrew)
            return HEBREW_DAYS;

        if (isHijri)
            return HIJRI_DAYS;

        if(isBasque)
            return BASQUE_DAYS;

        return removeFirst(dateFormatSymbols.getWeekdays());
    }

    // Java starts week day numbers from 1 and the tables with names contain
    // spare member with empty string on index 0. - Let's remove the first array member.
    private String[] removeFirst(String[] longArray)
    {
        String[] shortArray = new String[longArray.length - 1];
        System.arraycopy(longArray, 1, shortArray, 0, shortArray.length);
        return shortArray;
    }

    public String[] getMonthNames()
    {
        /// WORDSJAVA-1503 - dateFormatSymbols returns incorrectly HebrewCalendar months names.
        if (calendar instanceof HebrewCalendar && "he-IL".equals(cultureName))
            return HEBREW_CALENDAR_MONTH_NAMES;
        // WORDSJAVA-1503 - dateFormatSymbols returns incorrectly HijriCalendar months names.
        if ((calendar instanceof HijriCalendar && cultureName.startsWith("ar-")) || isHijri)
            return HIJRI_CALENDAR_MONTH_NAMES;

        if(calendar instanceof GregorianCalendar && isBasque)
            return BASQUE_CALENDAR_MONTH_NAMES;

        return dateFormatSymbols.getMonths();
    }

    public void setMonthNames(String[] monthNames)
    {
        dateFormatSymbols.setMonths(monthNames);
        MONTH_NAMES = monthNames;
    }

    public void setMonthGenitiveNames(String[] monthNames)
    {
    }

    public String[] getAbbreviatedMonthNames()
    {
        /// WORDSJAVA-1503 - dateFormatSymbols returns incorrectly HebrewCalendar months names.
        if (calendar instanceof HebrewCalendar && "he-IL".equals(cultureName))
            return HEBREW_CALENDAR_MONTH_NAMES;
        // WORDSJAVA-1503 - dateFormatSymbols returns incorrectly HijriCalendar months names.
        if ((calendar instanceof HijriCalendar && cultureName.startsWith("ar-")) || isHijri)
            return HIJRI_CALENDAR_MONTH_NAMES;

        if(calendar instanceof GregorianCalendar)
        {
            if(isBasque)
                return BASQUE_ABBREVIATION_CALENDAR_MONTH_NAMES;

            if(isEnNz)
                return EN_NZ_ABBREVIATION_CALENDAR_MONTH_NAMES;
        }

        return dateFormatSymbols.getShortMonths();
    }

    public void setAbbreviatedMonthNames(String[] abbreviatedMonthNames)
    {
        dateFormatSymbols.setShortMonths(abbreviatedMonthNames);
        ABBREVIATED_MONTH_NAMES = abbreviatedMonthNames;
    }

    public void setAbbreviatedMonthGenitiveNames(String[] abbreviatedMonthNames)
    {
    }

    String[] getEraNames()
    {
        if (isHebrew)
            return HEBREW_ERAS;

        if (isHijri)
            return HIJRI_ERAS;

        return dateFormatSymbols.getEras();
    }

    public String getAbbreviatedMonthName(int month)
    {
        if (month < 0 || month > MONTH_NAMES.length)
            throw new IllegalArgumentException("Out of month range" + month);

        return ABBREVIATED_MONTH_NAMES[month - 1];
    }

    String getMonthName(int month)
    {
        if (month < 0 || month > MONTH_NAMES.length)
            throw new IllegalArgumentException("Out of month range" + month);

        return MONTH_NAMES[month - 1];
    }

    String getAbbreviatedEraName(int era)
    {
        if (era < 0 || era > INVARIANT_ERA_NAMES.length)
            throw new IllegalArgumentException("Out of range " + era);

        return INVARIANT_ERA_NAMES[era];
    }

    private String[] getAbbreviatedEnglishEraNames()
    {
        if (ABBREVIATED_ENGLISH_ERA_NAMES == null)
        {
            DateFormatSymbols s = new DateFormatSymbols(Locale.ENGLISH);
            ABBREVIATED_ENGLISH_ERA_NAMES = s.getEras();
        }

        return ABBREVIATED_ENGLISH_ERA_NAMES;
    }

    String getEraName(int era)
    {
        if (era < 0 || era > ERA_NAMES.length)
            throw new IllegalArgumentException("Out of range " + era);

        return ERA_NAMES[era - 1];
    }

    String getAbbreviatedDayName(int dayofweek)
    {
        if (dayofweek < 0 || dayofweek > DAY_NAMES.length - 1)
            throw new IllegalArgumentException("Out of range " + dayofweek);

        return ABBREVIATED_DAY_NAMES[dayofweek];
    }

    String getDayName(int dayofweek)
    {
        if (dayofweek < 0 || dayofweek > DAY_NAMES.length - 1)
            throw new IllegalArgumentException("Out of range " + dayofweek);

        return DAY_NAMES[dayofweek];
    }

    public String[] getAbbreviatedDayNames()
    {
        return ABBREVIATED_DAY_NAMES;
    }

    public void setAbbreviatedDayNames(String[] abbreviatedDayNames)
    {
        ABBREVIATED_DAY_NAMES = abbreviatedDayNames;
    }

    public String[] getShortestDayNames()
    {
        return SUPER_SHORT_DAY_NAMES;
    }

    public void setShortestDayNames(String[] shortestDayNames)
    {
        SUPER_SHORT_DAY_NAMES = shortestDayNames;
    }

    public String[] getDayNames()
    {
        return DAY_NAMES;
    }

    public void setDayNames(String[] dayNames)
    {
        DAY_NAMES = dayNames;
    }

    public String getMonthDayPattern()
    {
        return monthDayPattern;
    }

    public String getYearMonthPattern()
    {
        return yearMonthPattern;
    }

    public String getAMDesignator()
    {
        return AMDesignator;
    }

    public void setAMDesignator(String amDesignator)
    {
        AMDesignator = amDesignator;
        // As in .NET, the parser tokens are built from this value: drop them, or a clone taken after the first parse
        // keeps recognizing the old value.
        m_dtfiTokenHash = null;
    }

    public String getPMDesignator()
    {
        return PMDesignator;
    }

    public void setPMDesignator(String pmDesignator)
    {
        PMDesignator = pmDesignator;
        m_dtfiTokenHash = null;
    }

    public String getTimeSeparator()
    {
        return timeSeparator;
    }

    public void setTimeSeparator(String timeSeparator)
    {
        this.timeSeparator = timeSeparator;
        m_dtfiTokenHash = null;
    }

    public String getDateSeparator()
    {
        return dateSeparator;
    }

    public String getShortDatePattern()
    {
        return shortDatePattern;
    }

    public void setShortDatePattern(String shortDatePattern)
    {
        this.shortDatePattern = shortDatePattern;
    }

    public String getLongDatePattern()
    {
        return longDatePattern;
    }

    public void setLongDatePattern(String longDatePat)
    {
        longDatePattern = longDatePat;
    }

    public int getFirstDayOfWeek()
    {
        return FirstDayOfWeek;
    }

    public void setFirstDayOfWeek(int dayOfWeek)
    {
        FirstDayOfWeek = dayOfWeek;
    }

    public String getLongTimePattern()
    {
        return longTimePattern;
    }

    public void setLongTimePattern(String longTimePattern)
    {
        this.longTimePattern = longTimePattern;
    }

    public String getShortTimePattern()
    {
        return shortTimePattern;
    }

    public void setShortTimePattern(String shortTimePatt)
    {
        shortTimePattern = shortTimePatt;
    }

    public String getRFC1123Pattern()
    {
        return RFC1123Pattern;
    }

    public String getSortableDateTimePattern()
    {
        return sortableDateTimePattern;
    }

    public String getUniversalSortableDateTimePattern()
    {
        return universalSortableDateTimePattern;
    }

    public String getFullDateTimePattern()
    {
        return fullDateTimePattern;
    }

    public String getGeneralShortTimePattern()
    {
        //(short date + short time)
        return getShortDatePattern() + " " + getShortTimePattern();
    }

    String getGeneralLongTimePattern()
    {
        // (short date + long time)
        return getShortDatePattern() + " " + getLongTimePattern();
    }

    void setMonthDayPattern(String monthDayPattern)
    {
        this.monthDayPattern = monthDayPattern;
    }

    void setYearMonthPattern(String yearMonthPattern)
    {
        this.yearMonthPattern = yearMonthPattern;
    }

    void setDateSeparator(String dateSeparator)
    {
        this.dateSeparator = dateSeparator;
        m_dtfiTokenHash = null;
    }

    void setFullDateTimePattern(String fullDateTimePattern)
    {
        this.fullDateTimePattern = fullDateTimePattern;
    }

    void setRFC1123Pattern(String rfc1123Pattern)
    {
        this.RFC1123Pattern = rfc1123Pattern;
    }

    void setSortableDateTimePattern(String sortableDateTimePattern)
    {
        this.sortableDateTimePattern = sortableDateTimePattern;
    }

    void setUniversalSortableDateTimePattern(String universalSortableDateTimePattern)
    {
        this.universalSortableDateTimePattern = universalSortableDateTimePattern;
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    // Note! Simplified code. .Net does numerous checks and wipes some fields before
    // assign the Calendar value.
    public void setCalendar(Calendar value)
    {
        calendar = value;
        // Get some constants from java.
        dateFormatSymbols = new DateFormatSymbols(mLocale);
        ABBREVIATED_DAY_NAMES = getAbbreviatedDayOfWeekNames();
        SUPER_SHORT_DAY_NAMES = getSuperShortDayOfWeekNames();
        DAY_NAMES = getDayOfWeekNames();
        ABBREVIATED_MONTH_NAMES = getAbbreviatedMonthNames();
        MONTH_NAMES = getMonthNames();
        ERA_NAMES = getEraNames();
    }

    boolean hasForceTwoDigitYears()
    {
        switch (calendar.getID())
        {
            // Add a special case for Japanese and ----.
            // If is y/yy, do not get (year % 100). "y" will print
            // year without leading zero.  "yy" will print year with two-digit in leading zero.
            // If pattern is yyy/yyyy/..., print year value with two-digit in leading zero.
            // So year 5 is "05", and year 125 is "125".
            // The reason for not doing (year % 100) is for Taiwan calendar.
            // If year 125, then output 125 and not 25.
            // Note: OS uses "yyyy" for Taiwan calendar by default.
            case (Calendar.CAL_JAPAN):
            case (Calendar.CAL_TAIWAN):
                return true;
        }
        return false;

    }

    public String internalGetMonthName(int month, boolean isGenitive)
    {
        return getMonthName(month);
    }

    String internalGetMonthName(int month, int monthNameStyles, boolean isGenitive)
    {
        return getMonthName(month);
    }

    public static msDateTimeFormatInfo getInvariantInfo()
    {
        // new instance always. because invariant can be changed by code(?)
        return new msDateTimeFormatInfo(msCultureInfo.getInvariantCulture());
    }

    String getDateTimeOffsetPattern()
    {
        if (dateTimeOffsetPattern == null)
            dateTimeOffsetPattern = getShortDatePattern() + " " + getLongDatePattern() + " zzz";

        return dateTimeOffsetPattern;
    }

    String[] getAllDateTimePatterns(char format) throws Exception
    {
        String[] result = null;

        switch (format)
        {
            case 'd':
                result = getClonedAllShortDatePatterns();
                break;
            case 'D':
                result = getClonedAllLongDatePatterns();
                break;
            case 'f':
                result = getCombinedPatterns(getClonedAllLongDatePatterns(), getClonedAllShortTimePatterns(), " ");
                break;
            case 'F':
                result = getCombinedPatterns(getClonedAllLongDatePatterns(), getClonedAllLongTimePatterns(), " ");
                break;
            case 'g':
                result = getCombinedPatterns(getClonedAllShortDatePatterns(), getClonedAllShortTimePatterns(), " ");
                break;
            case 'G':
                result = getCombinedPatterns(getClonedAllShortDatePatterns(), getClonedAllLongTimePatterns(), " ");
                break;
            case 'm':
            case 'M':
                result = new String[]{getMonthDayPattern()};
                break;
            case 'o':
            case 'O':
                result = new String[]{DateTimeFormat.RoundtripFormat};
                break;
            case 'r':
            case 'R':
                result = new String[]{getRFC1123Pattern()};
                break;
            case 's':
                result = new String[]{sortableDateTimePattern};
                break;
            case 't':
                result = getClonedAllShortTimePatterns();
                break;
            case 'T':
                result = getClonedAllLongTimePatterns();
                break;
            case 'u':
                result = new String[]{universalSortableDateTimePattern};
                break;
            case 'U':
                result = getCombinedPatterns(getClonedAllLongDatePatterns(), getClonedAllLongTimePatterns(), " ");
                break;
            case 'y':
            case 'Y':
                result = getClonedAllYearMonthPatterns();
                break;
            default:
                throw new Exception("Argument_BadFormatSpecifier");
        }
        return (result);
    }

    boolean yearMonthAdjustment(int[] yearMonth, boolean parsedMonthName)
    {
        if ((this.formatFlags & DateTimeFormatFlags.UseHebrewRule) != DateTimeFormatFlags.None)
        {
            // Special rules to fix up the Hebrew year/month

            // When formatting, we only format up to the hundred digit of the Hebrew year, although Hebrew year is now over 5000.
            // E.g. if the year is 5763, we only format as 763.
            if (yearMonth[0] < 1000)
                yearMonth[0] += 5000;

            // Because we need to calculate leap year, we should fall out now for an invalid year.
            if (yearMonth[0] < getCalendar().getYear(getCalendar().getMinSupportedDateTime()) ||
                    yearMonth[0] > getCalendar().getYear(getCalendar().getMaxSupportedDateTime()))
            {
                return false;
            }

            // To handle leap months, the set of month names in the symbol table does not always correspond to the numbers.
            // For non-leap years, month 7 (Adar Bet) is not present, so we need to make using this month invalid and
            // shuffle the other months down.
            if (parsedMonthName && !getCalendar().isLeapYear(yearMonth[0]))
            {
                if (yearMonth[1] >= 8)
                    yearMonth[1]--;
                else if (yearMonth[1] == 7)
                    return false;
            }
        }
        return true;
    }

    public String[] getDateWords()
    {
        return m_dateWords;
    }

    private String[] getCombinedPatterns(String[] patterns1, String[] patterns2, String connectString)
    {
        String[] result = new String[patterns1.length * patterns2.length];
        for (int i = 0; i < patterns1.length; i++)
        {
            for (int j = 0; j < patterns2.length; j++)
            {
                result[i * patterns2.length + j] = patterns1[i] + connectString + patterns2[j];
            }
        }
        return (result);
    }

    private String[] getClonedAllShortDatePatterns()
    {
        return new String[]{getShortDatePattern()};
    }

    private String[] getClonedAllLongTimePatterns()
    {
        return new String[]{getLongTimePattern()};
    }


    private String[] getClonedAllYearMonthPatterns()
    {
        return new String[]{getYearMonthPattern()};
    }

    private String[] getClonedAllShortTimePatterns()
    {
        return new String[]{getShortTimePattern()};
    }

    private String[] getClonedAllLongDatePatterns()
    {
        return new String[]{getLongDatePattern()};
    }

    @Override
    public Object getFormat(Class formatType)
    {
        return (formatType == msDateTimeFormatInfo.class) ? this : null;
    }

    private static class TokenHashValue
    {
        private String tokenString;
        private int tokenType;
        private int tokenValue;

        TokenHashValue(String tokenString, int tokenType, int tokenValue)
        {
            this.tokenString = tokenString;
            this.tokenType = tokenType;
            this.tokenValue = tokenValue;
        }
    }

    private static boolean isHebrewChar(char ch)
    {
        return ((ch >= '֐') && (ch <= '׿'));
    }

    private static boolean tryParseHebrewNumber(DateTimeString str, DateTimeString.TokenTypeValue number)
    {
        int /*HebrewNumberParsingState*/ state;
        number.setValue(-1);
        int index = str.getIndex();
        if (!HebrewNumber.IsDigit(str.getValue().charAt(index)))
        {
            return false;
        }
        HebrewNumberParsingContext context = new HebrewNumberParsingContext(0);
        do
        {
            state = HebrewNumber.ParseByChar(str.getValue().charAt(index++), context);
            switch (state)
            {
                case HebrewNumberParsingState.InvalidHebrewNumber:
                case HebrewNumberParsingState.NotHebrewDigit:
                    return false;
            }
        }
        while ((index < str.getValue().length()) && (state != HebrewNumberParsingState.FoundEndOfHebrewNumber));
        if (state != HebrewNumberParsingState.FoundEndOfHebrewNumber)
        {
            return false;
        }
        str.advance(index - str.getIndex());
        number.setValue(context.result);
        return true;
    }

    boolean hasYearMonthAdjustment()
    {
        return ((getFormatFlags() & DateTimeFormatFlags.UseHebrewRule) != DateTimeFormatFlags.None);
    }

    int getFormatFlags()
    {
        if (this.formatFlags == DateTimeFormatFlags.None)
        {
            if (this.m_scanDateWords /* || this.m_cultureTableRecord.IsSynthetic*/)
            {
                this.formatFlags = (DateTimeFormatFlags.None);
                this.formatFlags |= ((DateTimeFormatInfoCore.getFormatFlagGenitiveMonth(getMonthNames(), internalGetGenitiveMonthNames(false), getAbbreviatedMonthNames(), internalGetGenitiveMonthNames(true))));
                this.formatFlags |= ((DateTimeFormatInfoCore.getFormatFlagUseSpaceInMonthNames(getMonthNames(), internalGetGenitiveMonthNames(false), getAbbreviatedMonthNames(), internalGetGenitiveMonthNames(true))));
                this.formatFlags |= ((DateTimeFormatInfoCore.getFormatFlagUseSpaceInDayNames(getDayNames(), getAbbreviatedDayNames())));
                this.formatFlags |= ((DateTimeFormatInfoCore.getFormatFlagUseHebrewCalendar(calendar, cultureName)));
            }
            else /*if (isDefaultCalendar)*/
            {
                // this.formatFlags = this.m_cultureTableRecord.IFORMATFLAGS;
            }
            //else
            //{
            //TODO:
            // this.formatFlags = (DateTimeFormatFlags) CalendarTable.Default.IFORMATFLAGS(this.Calendar.ID);
            //}
        }
        return this.formatFlags;
    }


    private String[] internalGetGenitiveMonthNames(boolean abbreviated)
    {
        return ABBREVIATED_MONTH_NAMES;
    }

    public /*virtual*/ msDateTimeFormatInfo deepClone()
    {
        msDateTimeFormatInfo dtfi = (msDateTimeFormatInfo) memberwiseClone();
        dtfi.setCalendar((Calendar) this.getCalendar().deepClone());
        return dtfi;
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try
        {
            return clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new IllegalStateException(e);
        }
    }

    private static final String LocalTimeMark = "T";

    // each thread may have its own locale instance so we don't want to share this field between the threads.
    private TokenHashValue[] m_dtfiTokenHash;
    private boolean m_scanDateWords = false;
    private String[] m_dateWords = null;

    private static boolean hasSpacesInMonthNames = false;
    private static boolean hasSpacesInDayNames = false;

    private String timeSeparator;
    private String dateSeparator;

    private static final String gInvariantDateSeparator = "/";
    private static final String gInvariantTimeSeparator = ":";

    private String AMDesignator;
    private String PMDesignator;

    private int FirstDayOfWeek = DayOfWeek.SUNDAY;

    private static String[] INVARIANT_ERA_NAMES = {"A.D.", "B.C", "AD", "BC"};
    private static String[] ABBREVIATED_ENGLISH_ERA_NAMES;

    private static String[] HEBREW_DAYS =
            {"יום ראשון", "יום שני", "יום שלישי", "יום רביעי", "יום חמישי", "יום שישי", "שבת"};
    private static String[] HEBREW_SHORT_DAYS =
            {"יום א", "יום ב", "יום ג", "יום ד", "יום ה", "יום ו", "שבת"};
    private static String[] HEBREW_ABBREVIATION_DAYS =
            {"א", "ב", "ג", "ד", "ה", "ו", "ש"};
    private static String[] HEBREW_CALENDAR_MONTH_NAMES =
            {"תשרי", "חשון", "כסלו", "טבת", "שבט", "אדר", "אדר ב", "ניסן", "אייר", "סיון", "תמוז", "אב", "אלול"};
    private static String[] HEBREW_ERAS =
            {"לספירה", "לספירה"};

    private static String[] HIJRI_DAYS =
            {"الاحد", "الاثنين", "الثلاثاء", "الاربعاء", "الخميس", "الجمعة", "السبت"};
    // Non-breaking space (char 160) in month names in .Net's "ar-SA" culture.
    // Other .Net Arabic cultures can use ordinal space (char 32) but also can contain bugs in month names.
    private static String[] HIJRI_CALENDAR_MONTH_NAMES =
            {"محرم", "صفر", "ربيع الأول", "ربيع الثاني", "جمادى الأولى", "جمادى الثانية", "رجب", "شعبان", "رمضان", "شوال", "ذو القعدة", "ذو الحجة"};
    // Usual space (char 32) in era names
    private static String[] HIJRI_ERAS =
            {"بعد الهجرة", "بعد الهجرة"};

    // String constants for Basque culture (see WORDSJAVA-2698)
    private static String[] BASQUE_DAYS =
            {"igandea","astelehena","asteartea","asteazkena","osteguna","ostirala","larunbata"};
    private static String[] BASQUE_ABBREVIATION_DAYS =
            {"ig.","al.","ar.","az.","og.","or.","lr."};
    private static String[] BASQUE_CALENDAR_MONTH_NAMES =
            {"urtarrila","otsaila","martxoa","apirila","maiatza","ekaina","uztaila","abuztu","iraila","urria","azaroa","abendua"};
    private static String[] BASQUE_ABBREVIATION_CALENDAR_MONTH_NAMES =
            {"urt.","ots.","mar.","api.","mai.","eka.","uzt.","abu.","ira.","urr.","aza.","abe."};
    private static String[] EN_NZ_ABBREVIATION_CALENDAR_MONTH_NAMES =
            {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};

    private String[] ABBREVIATED_DAY_NAMES;
    private String[] SUPER_SHORT_DAY_NAMES;
    private String[] DAY_NAMES;
    private String[] ABBREVIATED_MONTH_NAMES;
    private String[] MONTH_NAMES;
    private String[] ERA_NAMES;

    private String languageName;
    private String cultureName;
    private Calendar calendar;
    private DateFormatSymbols dateFormatSymbols;

    private String monthDayPattern;
    private String yearMonthPattern;
    private String shortDatePattern;
    private String longDatePattern;
    private String shortTimePattern;
    private String longTimePattern;

    private Locale mLocale;

    private String RFC1123Pattern;
    private String sortableDateTimePattern;
    private String universalSortableDateTimePattern;
    private String fullDateTimePattern;
    private String dateTimeOffsetPattern;
    int formatFlags = DateTimeFormatFlags.None;
    private boolean isHebrew;
    private boolean isHijri;
    private boolean isBasque;
    private boolean isEnNz;
}
