// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/09/2011 by Dmitry Matveenko
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msString;
import com.aspose.JapaneseNumber;
import com.aspose.ms.System.DayOfWeek;


/// <summary>
/// Implements date and time formatting specific to Japanese language.
/// </summary>
class JapaneseDateTimeFormat extends DateTimeFormat
{
    /// <summary>
    /// Creates a new instance of JapaneseDateTimeFormat class for a given date.
    /// </summary>
    JapaneseDateTimeFormat(DateTime dateTime)
    {
    	super(dateTime);
	
    }

    /// <summary>
    /// Gets Japanese era name for a given date.
    /// </summary>
    /// <param name="date"></param>
    /// <returns></returns>
    static String getJapaneseEraName(DateTime date)
    {
        Era era = getJapaneseEra(date);
        return era != null
            ? era.getName()
            : "";
    }

    /// <summary>
    /// Gets an abbreviated Japanese era name for a given date.
    /// </summary>
    static String getJapaneseEraAbbreviated(DateTime date)
    {
        Era era = getJapaneseEra(date);
        return era != null
            ? era.getAbbreviation()
            : "";
    }

    /// <summary>
    /// Gets an abbreviated Japanese era name in English for a given date.
    /// </summary>
    static String getJapaneseEraAbbreviatedEnglish(DateTime date)
    {
        Era era = getJapaneseEra(date);
        return era != null
            ? era.getEnglishAbbreviation()
            : "";
    }

    /// <summary>
    /// Gets Japanese era year in Arabic digits.
    /// </summary>
    static String getJapaneseYearInArabicDigits(int pictureElementLength, DateTime date, String wordFormat, int charIdx)
    {
        if (DateTime.greater(G_MIN_SUPPORTED_DATE, date))
            return "";

        int intYear = getJapaneseEraYear(date);

        // Gannen (the first year of an era) case
        if ((intYear == 1) && wordFormat.substring(charIdx).contains("年"))
            return "元";

        boolean addLeadingZero = (pictureElementLength > 1) && (intYear < 10);
        return addLeadingZero
            ? msString.format("0{0}", intYear)
            : Integer.toString(intYear);
    }

    /// <summary>
    /// Gets Japanese era year in Arabic digits.
    /// </summary>
    protected /*override*/ String getYearInArabicDigits(int pictureElementLength, String wordFormat, int charIdx)
    {
        return getJapaneseYearInArabicDigits(pictureElementLength, DateToFormat, wordFormat, charIdx);
    }

    /// <summary>
    /// Gets Japanese era year as Japanese numeral or Gregorian year in Japanese digits.
    /// </summary>
    /// <param name="pictureElementLength"></param>
    protected /*override*/ String getYearInEastAsianDigits(int pictureElementLength)
    {
        String japaneseYear;
        if(pictureElementLength > 1)
        {
            // Gregorian year in Japanese digits.
            japaneseYear = super.getYearInEastAsianDigits(pictureElementLength);
        }
        else if (DateTime.lessOrEqual(G_MIN_SUPPORTED_DATE, DateToFormat))
        {
            // Japanese year as Japanese numeral.
            int intYear = getJapaneseEraYear(DateToFormat);
            japaneseYear = JapaneseNumber.toJapaneseCounting(intYear);
        }
        else
        {
            japaneseYear = "";
        }

        return japaneseYear;
    }

    /// <summary>
    /// Gets Japanese week day.
    /// </summary>
    protected /*override*/ String getEastAsianDayName()
    {
        return getJapaneseDayNameAbbreviated(DateToFormat);
    }

    /// <summary>
    /// Gets Japanese week day.
    /// </summary>
    protected /*override*/ String getEastAsianDayNameOrNumber()
    {
        return getJapaneseDayNameAbbreviated(DateToFormat);
    }

    /// <summary>
    /// Gets Japanese weed day for 'aaa' picture element.
    /// </summary>
    /// <param name="pictureElementLength"></param>
    protected /*override*/ String getEastAsianDayNameAaa(int pictureElementLength)
    {
        // Only aaa is a valid pattern, everything else is handled by the default implementation.
        final int REQUIRED_CHAR_REPETIONS = 3;
        return REQUIRED_CHAR_REPETIONS == pictureElementLength
            ? getEastAsianDayName()
            : super.getEastAsianDayNameAaa(pictureElementLength);
    }

    /// <summary>
    /// Gets AM/PM designator for Japanese AM/PM picture elements.
    /// </summary>
    protected /*override*/ String getEastAsianAmpmDesignator()
    {
        return DateToFormat.getHour() < 12
            ? "午前"
            : "午後";
    }

    protected /*override*/ int getMaxCharEastAsianYearPictureElementRepetitions() { return 2; }

    /// <summary>
    /// Gets Japanese era.
    /// </summary>
    /// <returns>An object representing Japanese era for a given day or null if Japanese era is not defined for this date.</returns>
    private static Era getJapaneseEra(DateTime date)
    {
        for(Era era : G_ERAS)
        {
            if (DateTime.greaterOrEqual(date, era.getBeginning()))
                return era;
        }

        return null;
    }

    /// <summary>
    /// Gets Japanese era year as integer.
    /// </summary>
    private static int getJapaneseEraYear(DateTime date)
    {
        Era era = getJapaneseEra(date);

        if (era != null)
            return date.getYear() - era.getBeginning().getYear() + 1;

        throw new IllegalArgumentException("Japanese Era is not defined for the given date." + "\r\nParameter name: " + "date" + "\r\nParameter value: " + date);
    }

    /// <summary>
    /// Gets an abbreviated Japanese week day for a given date.
    /// </summary>
    private static String getJapaneseDayNameAbbreviated(DateTime date)
    {
        switch(date.getDayOfWeek())
        {
            case DayOfWeek.SUNDAY:
                return "日";
            case DayOfWeek.MONDAY:
                return "月";
            case DayOfWeek.TUESDAY:
                return "火";
            case DayOfWeek.WEDNESDAY:
                return "水";
            case DayOfWeek.THURSDAY:
                return "木";
            case DayOfWeek.FRIDAY:
                return "金";
            case DayOfWeek.SATURDAY:
                return "土";
            default:
                // It should not happen.
                return "";
        }
    }

    /// <summary>
    /// Stores the Japanese eras.
    /// </summary>
    /// <remarks>
    /// The eras should go in descending order.
    /// </remarks>
    private static /*final*/ Era[] G_ERAS =
    {
        new Era(new DateTime(2019, 5, 1), "令和", "令", "R"), // Reiwa
        new Era(new DateTime(1989, 1, 8), "平成", "平", "H"), // Heisei
        new Era(new DateTime(1926, 12, 25), "昭和", "昭", "S"), // Showa
        new Era(new DateTime(1912, 7, 30), "大正", "大", "T"), // Taisho
        new Era(new DateTime(1868, 1, 1), "明治", "明", "M")  // Meiji
    };

    /// <summary>
    /// Gets the minimal for which Japanese era and years are defined.
    /// </summary>
    private static /*final*/ DateTime G_MIN_SUPPORTED_DATE = G_ERAS[G_ERAS.length - 1].getBeginning();

    private static class Era
    {
        Era(DateTime beginning, String name, String abbreviation, String englishAbbreviation)
        {
            mBeginning = beginning;
            mName = name;
            mAbbreviation = abbreviation;
            mEnglishAbbreviation = englishAbbreviation;
        }

        DateTime getBeginning() { return mBeginning; };

        private DateTime mBeginning;
        String getName() { return mName; };

        private String mName;
        String getAbbreviation() { return mAbbreviation; };

        private String mAbbreviation;
        String getEnglishAbbreviation() { return mEnglishAbbreviation; };

        private String mEnglishAbbreviation;
    }
}

