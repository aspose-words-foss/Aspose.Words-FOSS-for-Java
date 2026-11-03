// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.DrColor;
import java.util.ArrayList;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.MathUtil;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.StringSplitOptions;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import com.aspose.ms.System.Convert;
import com.aspose.UnicodeUtil;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ConvertUtilCore;


/*static*/ class DmlChartRenderingUtil
{
	/* Simulation of static class by using private constructor */
	private DmlChartRenderingUtil()
	{}

    /// <summary>
    /// Indicates whether the specified <see cref="DmlChart"/> has filled series.
    /// </summary>
    static boolean isChartFilled(DmlChart chart)
    {
        if (chart == null)
            return false;

         if (chart.isBarChart() || (chart.getChartType() == DmlChartType.AREA_CHART) || chart.isPieChart())
            return true;

        if (chart.isRadarChart())
        {
            DmlRadarChart radarChart = ms.as(chart, DmlRadarChart.class);
            return (radarChart.getRadarStyle() == RadarStyle.FILLED);
        }

        return false;
    }

    /// <summary>
    /// Returns the <see cref="DmlChartSpPr"/> that should be used to render negative bars.
    /// </summary>
    static DmlChartSpPr getIfNegativSpPr(DmlExtension extension)
    {
        if (extension != null)
            return extension.getDmlChartSpPr();

        // WORDSNET-27537 If extension is not specified, MS Word uses white color for the fill and black for border
        // to render the negative bar.
        DmlChartSpPr spPr = new DmlChartSpPr();
        DmlColor white = DmlColor.createFromDrColor(DrColor.WHITE);
        DmlColor black = DmlColor.createFromDrColor(DrColor.BLACK);
        DmlSolidFill whiteFill = new DmlSolidFill(white);
        spPr.setFill(whiteFill);
        spPr.getOutline().setFill(new DmlSolidFill(black));
        return spPr;
    }

     /// <summary>
    /// Indicates whether the outline is not rendered.
    /// </summary>
    static boolean isOutlineEmpty(DmlOutline outline)
    {
         return (outline == null) || (outline.getFill() == null) || (outline.getFill().getDmlFillType() == DmlFillType.NO_FILL);
    }

    /// <summary>
    /// Splits the specified array of points into segments if array contains points that must be skipped.
    /// </summary>
    static ArrayList</*PointF*/long[]> splitToSegments(/*PointF*/long[] points, int[] segmentsIndices)
    {
        if (segmentsIndices.length % 2 > 0)
            throw new IllegalArgumentException("Length of segment indexes array must be even.");

        ArrayList</*PointF*/long[]> segments = new ArrayList</*PointF*/long[]>();

        for (int i = 0; i < segmentsIndices.length; i += 2)
        {
            int startIndex = segmentsIndices[i];
            int segmentLength = segmentsIndices[i + 1];

            /*PointF*/long[] segment = new /*PointF*/long[segmentLength];
            msArray.copy(points, startIndex, segment, 0, segmentLength);
            msArrayList.add(segments, segment);
        }

        return segments;
    }

    /// <summary>
    /// Returns array of float values, each value equals a sum of Y values for the corresponding X value.
    /// The array is used to render stacked charts.
    /// </summary>
    static double[] getStackedMaximums(DmlChart chart)
    {
        double[] maxValues = new double[chart.getMaxPointsCount()];
        for (ChartSeries series : chart.getSeries())
        {
            for (int i = 0; i < maxValues.length; i++)
            {
                // WORDSNET-13069 Stacked maximum value is a range between minimum and maximum stacked values,
                // that is why calculate it as sum of absolute values.
                DmlChartValue value = series.getY().getData().getOriginalValue(i);
                maxValues[i] += Math.abs(DmlChartValue.isNullOrNaN(value)? 0d : value.getValue());
            }
        }

        // Validate stacked maximum values to prevent dividing by zero.
        for (int i = 0; i < maxValues.length; i++)
        {
            // If maximum value is zero, reset it to 1 so that upon dividing,
            // the original value stay unchanged.
            if (MathUtil.isZero(maxValues[i]))
                maxValues[i] = 1.0f;
        }

        return maxValues;
    }

    /// <summary>
    /// Returns increment of tick index where labels should be rendered.
    /// If tick size is greater than default label font size, returns 1, that means
    /// label at each tick must be rendered.
    /// </summary>
    static int getTickIndexIncrement(float tickSize, float labelSize)
    {
        if (MathUtil.isGreaterOrEqual(tickSize,labelSize))
            return 1;

        return (int)(labelSize / tickSize + 1f);
    }

    /// <summary>
    /// Gets the string representation of the specified <see cref="DateTime"/>.
    /// </summary>
    /// <param name="date">The specified <see cref="DateTime"/></param>
    /// <param name="originalFormat">The specified format code</param>
    /// <returns>The string representation of the date</returns>
    static String getStringDate(DateTime date, String originalFormat)
    {
        msCultureInfo ci = DmlChartFormatCodeValidator.getCultureInfo(originalFormat);
        String correctedFormat = DmlChartFormatCodeValidator.correctDefaultDateFormat(originalFormat);
        correctedFormat = DmlChartFormatCodeValidator.correctDateFormatCode(correctedFormat);

        // WORDSNET-24366 "MMMMM" in format code should be replaced with the first letter of the month name (3.8.31).
        if (G_MONTH_FIRST_LETTER_FORMAT.isMatch(correctedFormat))
        {
            // The current culture is read-only. Clone to make changes.
            ci = ms.as(ci.deepClone(), msCultureInfo.class);
            String[] firstLetterMonthNames = getMonthFirstLettersArray(ci.getDateTimeFormat().getAbbreviatedMonthNames());
            msDateTimeFormatInfo dtfi = new msDateTimeFormatInfo();
            // Replace the abbreviated name of the month with the first letter of month.
            dtfi.setAbbreviatedMonthNames(firstLetterMonthNames);
            ci.setDateTimeFormat(dtfi);
            // Replace the first letter of month format to the abbreviated name of the month.
            correctedFormat = G_MONTH_FIRST_LETTER_FORMAT.replace(correctedFormat, "MMM");
        }

        return date.toString(correctedFormat, ci);
    }

    /// <summary>
    /// Gets the array of the first letters of the month names.
    /// </summary>
    private static String[]  getMonthFirstLettersArray(String[] monthNames)
    {
        String[] firstLettersOfMonthNames = new String[13];

        for (int i = 0; i < monthNames.length; i++)
            firstLettersOfMonthNames[i] = msString.isNullOrEmpty(monthNames[i]) ? monthNames[i] : monthNames[i].substring((0), (0) + (1));

        return firstLettersOfMonthNames;
    }

    /// <summary>
    /// Gets the string representation of the specified double value.
    /// </summary>
    /// <remarks>
    /// A semicolon in the format code defines sections with separate format strings for positive, negative, and zero
    /// numbers. If the decimal point is defined in the format code, .Net applies the decimal point formatting, and then
    /// the positive/negative/zero formatting. I.e. the format code is "0.0; (0.0); zero" and the value is "-0.03" the
    /// result of value.ToString("0.0; (0.0); zero") is "zero". But MS Word first applies positive/negative/zero
    /// formatting and then the decimal point formatting. So, we have to emulate the MS Word behavior and manually
    /// repeat the sequence of conversions.
    /// </remarks>
    static String doubleToString(double value, String formatCode)
    {
        msCultureInfo ci = DmlChartFormatCodeValidator.getCultureInfo(formatCode);
        String format = DmlChartFormatCodeValidator.removeAdditionalConditions(formatCode);
        String[] formats = msString.split(format, SECTION_SEPARATOR);

        // WORDSNET-26910  According to OOXML spec, text between double quotes or chars escaped with backslash
        // in formatCode string should appear without changes in the resulting string.
        String[] additionalTextElements = DmlChartFormatCodeValidator.getFormatCodeParts(formatCode);
        boolean containsQuotedText = (formats.length <= 1 && additionalTextElements.length > 1);
        format = getFormatDependingOnValue(value, containsQuotedText, formats);
        format = DmlChartFormatCodeValidator.validateFormatCode(format);

        value = (value < 0) && (formats.length > 1) && !DmlChartFormatCodeValidator.isGeneralFormatCode(format)
            ? Math.abs(value)
            : value;

        // WORDSNET-22540 The value should be scaled by thousands, if the "comma" specified in the format code.
        String result = applyThousandsSeparator(value, format, ci);

        return containsQuotedText
            ? msString.format("{0}{1}{2}", additionalTextElements[0], result, additionalTextElements[1])
            : result;
    }

    /// <summary>
    /// Returns the format applied to the value.
    /// </summary>
    private static String getFormatDependingOnValue(double value, boolean containsQuotedText, String[] formats)
    {
        if (containsQuotedText)
            return DmlChartFormatCodeValidator.GENERAL_FORMAT;

        int positiveFormatIndex = 0;
        int negativeFormatIndex = 1;
        int zeroFormatIndex = 2;
        int formatsCount = formats.length;
        int index;

        if (value > 0)
            index = positiveFormatIndex;
        else if (value < 0)
            index = (formatsCount > 1) ? negativeFormatIndex : 0;
        else
            index = (formatsCount > 2) ? zeroFormatIndex : 0;

        return formats[index];
    }

    /// <summary>
    /// Applies the thousands separator if present in the format code, and returns the string representation of the value.
    /// </summary>
    private static String applyThousandsSeparator(double value, String format, msCultureInfo ci)
    {
        Match match = G_THOUSANDS_REGEX.match(format);

        if (StringUtil.hasChars(match.getValue()))
        {
            String[] commaSplitted = msString.split(match.getValue(), THOUSANDS_SEPARATOR);
            int commaCount = commaSplitted.length - 1;
            double divider = Math.pow(1000.0, commaCount);
            value /= divider;
        }

        format = G_THOUSANDS_REGEX.replace(format, "0");
        String result;

        if (StringUtil.hasChars(format) && DmlChartFormatCodeValidator.isGeneralFormatCode(format))
            // WORDSNET-24367 The general format should be applied if the section format is "general".
            result = getGeneralFormatStringValue(value);
        else
            // WORDSNET-20483 If one of the section is empty, the value corresponding to the section is not rendered.
            result = msString.isNullOrEmpty(format) ? "" : FormatterPal.doubleToStr(value, format, ci);

        return result;
    }

    /// <summary>
    /// Get string representation of the double value if format code is "General".
    /// </summary>
    static String getGeneralFormatStringValue(double value)
    {
        return (((Math.abs(value) < 1E-7) || (Math.abs(value) > 9.9999999999E8)) && !MathUtil.isZero(value))
                ? FormatterPal.doubleToExponential(value)
                : FormatterPal.doubleToStr9Decimals(value);
    }

    /// <summary>
    /// Returns the first day of a month (year) of the specified date according to the specified <see cref="AxisTimeUnit"/>.
    /// </summary>
    /// <param name="date">The specified <see cref="DateTime"/></param>
    /// <param name="timeUnit">The specified <see cref="AxisTimeUnit"/></param>
    /// <returns>The first day of the month or of the year</returns>
    static DateTime getFirstDay(DateTime date, /*AxisTimeUnit*/int timeUnit)
    {
        switch (timeUnit)
        {
            case AxisTimeUnit.MONTHS:
                // Return the first day of the month;
                return date.addDays(-date.getDay() + 1);
            case AxisTimeUnit.YEARS:
                // Return the first day of the year.
                return new DateTime(date.getYear(), 1, 1);
            case AxisTimeUnit.DAYS:
            default:
                return date;
        }
    }

    /// <summary>
    /// Creates new <see cref="DmlChartTx"/> from the specified text string.
    /// </summary>
    static DmlChartTx createTx(String text)
    {
        DmlChartTx tx = createTxCore();

        if (msString.isNullOrEmpty(text))
            return tx;

        // Split text into paragraphs.
        String[] paragraphs = msString.split(text, new String[] { ControlChar.CR_LF }, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        for (String paragraph : paragraphs)
            addTxParagraph(tx, paragraph);

        return tx;
    }

    /// <summary>
    /// Creates new <see cref="DmlChartTx"/> from the specified texts strings.
    /// For each item in the enumeration a separate paragraph is created.
    /// </summary>
    static DmlChartTx createTx(Iterable<String> texts)
    {
        DmlChartTx tx = createTxCore();
        for (String text : texts)
            addTxParagraph(tx, text);

        return tx;
    }

    static DmlChartTx createTxCore()
    {
        DmlChartTx tx = new DmlChartTx();
        tx.setRichText(new DmlTextBody());
        return tx;
    }

    static void addTxParagraph(DmlChartTx tx, String text)
    {
        addTxParagraph(tx, new String[] { text}, new double[1]);
    }

    /// <summary>
    /// Adds <see cref="DmlParagraph"/> with specified text into <see cref="DmlChartTx"/> take into account the baseline value
    /// </summary>
    /// <remarks>
    /// The base line is used when subscript or superscript text should be rendered.
    /// </remarks>
    static void addTxParagraph(DmlChartTx tx, String[] stringValues, double[] baseLineValues)
    {
        DmlParagraph paragraph = new DmlParagraph();
        tx.getRichText().addParagraph(paragraph);

        for (int i = 0; i < stringValues.length; i++)
        {
            String text = stringValues[i];

            if (msString.isNullOrEmpty(text))
                continue;

            DmlRun run = new DmlRun();
            run.setText(replaceUtf16Chars(text));

            // Set baseline for subscript or superscript rendering.
            if ((i < baseLineValues.length) && !Double.isNaN(baseLineValues[i]))
                run.getRunProperties().setBaseline(baseLineValues[i]);

            paragraph.addElement(run);
        }
    }

    /// <summary>
    /// Replaces UTF16 character codes with a string representation.
    /// </summary>
    /// <param name="text">Specified string</param>
    /// <returns>String with replaced UTF16 character codes</returns>
    static String replaceUtf16Chars(String text)
    {
        if (!G_UTF_16_CODE_REGEX.isMatch(text))
            return text;

        MatchCollection matches = G_UTF_16_CODE_REGEX.matches(text);

        for (Match mat : (Iterable<Match>) matches)
        {
            // Get the string representation of the Unicode code point.
            String utf16Code = "0" + mat.getValue().substring((1), (1) + (mat.getValue().length() - 2));
            // Converts string representation to integer.
            int utfChar = Convert.toInt32(utf16Code, 16);
            // Get char and convert to string.
            String utf16String = UnicodeUtil.convertFromUtf32(utfChar);
            // Replace the char symbols.
            text = Regex.replace(text, mat.getValue(), utf16String);
        }

        return text;
    }

    /// <summary>
    /// Converts the specified value to string that can be used as label for axis, trims the value if it is too long.
    /// </summary>
    static String getTrimmedString(DmlChartValue value, int maxStringLength)
    {
        // Sometimes value might be null, in this case simply return an empty string.
        if (DmlChartValue.isNullOrNone(value) || msString.isNullOrEmpty(value.getStringValue()))
            return "";


        return getTrimmedString(value.getStringValue(), maxStringLength);
    }

    /// <summary>
    /// Trims the specified string if it is too long.
    /// </summary>
    static String getTrimmedString(String stringVale, int maxStringLength)
    {

        if (msString.isNullOrEmpty(stringVale))
            return stringVale;

        String result = replaceUtf16Chars(stringVale);

        if (MathUtil.isLessOrEqual(maxStringLength, 0f) || MathUtil.isLessOrEqual(result.length(), maxStringLength))
            return result;

        // Text should not be split at the first char if the first char is a white space.
        String[] words = SPACE_REGEX.split(result, 0, 1);
        int maxWordLength = 0;

        for (String word : words)
            maxWordLength = Math.max(maxWordLength, word.length());

        int maxLength = Math.min(maxStringLength, maxWordLength);
        int firstWordLength = Math.min(maxLength, words[0].length());
        return (maxWordLength < maxStringLength) ? result : getSrtingWithHorizontalEllipsis(result, firstWordLength);
    }

    /// <summary>
    /// Gets the specified number of letters form the string and appends horizontal ellipsis to the end.
    /// </summary>
    /// <param name="text">The specified text</param>
    /// <param name="numberOfLetters">The number of letters</param>
    /// <returns>The trimmed text with horizontal ellipsis</returns>
    static String getSrtingWithHorizontalEllipsis(String text, int numberOfLetters)
    {
        return msString.format("{0}...", text.substring((0), (0) + (numberOfLetters)));
    }

    /// <summary>
    /// Adds "space" after "hyphen".
    /// </summary>
    /// <param name="value">The specified string</param>
    /// <returns>String with replacement</returns>
    static String addSpaceAfterHyphen(String value)
    {
        return G_HYPHEN_REGEX.replace(value, "- ");
    }

    static float calculateDepth(DmlChart chart, float baseTickWidth, int depthPercent)
    {
        if (!chart.getIs3D())
            return 0.0f;

        int barsCount = getBarCount(chart);

        // The values are required to calculate size of base and depth.
        int gapWidth = (/*int*/Integer)chart.getChartPr().getProperty(DmlChartAttrs.GAP_WIDTH);
        int gapDepth = (/*int*/Integer)chart.getChartPr().getProperty(DmlChartAttrs.GAP_DEPTH);

        // It seems size of base equals width of bar.
        float baseSize = baseTickWidth / (barsCount + gapWidth / 100.0f);
        float depth = baseSize * ((float)depthPercent / 100f) * (1f + (float)gapDepth / 100f);

        return depth;
    }

    private static int getBarCount(DmlChart chart)
    {
        /*Grouping*/int grouping = chart.getChartPr().getGrouping();

        if((chart.getChartType() == DmlChartType.BAR_3_D_CHART) && (grouping == Grouping.CLUSTERED))
            return chart.getSeries().getCount();

        boolean isDistributedSeries = ((chart.getChartType() == DmlChartType.BAR_3_D_CHART) && (grouping == Grouping.STANDARD));
        isDistributedSeries |= (chart.getChartType() == DmlChartType.LINE_3_D_CHART);
        isDistributedSeries |= ((chart.getChartType() == DmlChartType.AREA_3_D_CHART) && !chart.isStacked());

        return isDistributedSeries ? 2 : 1;
    }

    /// <summary>
    /// Returns point on the line defined by two given points with specified X coordinate.
    /// </summary>
    static /*PointF*/long getLinePoint(/*PointF*/long p0, /*PointF*/long p1, double x)
    {
        // Special case - vertical line.
        if (MathUtil.areEqual(msPointF.getX(p0), msPointF.getX(p1)))
            return msPointF.ctor((float)x, msPointF.getY(p0) + (msPointF.getY(p1) - msPointF.getY(p0)));

        double slope = (msPointF.getY(p1) - msPointF.getY(p0)) / (msPointF.getX(p1) - msPointF.getX(p0));
        double y = slope * (x - msPointF.getX(p0)) + msPointF.getY(p0);
        return msPointF.ctor((float)x, (float)y);
    }
    /// <summary>
    /// Indicates whether the specified axis is logarithmic scaled and the value is less or equal to zero.
    /// </summary>
    /// <returns>"True" if the axis is logarithmic scaled and the value is less or equal to zero, otherwise - "false"</returns>
    static boolean isWrongLogScaledValue(ChartAxis axis, double value)
    {
        return axis.isLogScaled() && ((value < 0) || MathUtil.isZero(value));
    }

    /// <summary>
    /// Indicates whether the specified angle is orthogonal.
    /// </summary>
    /// <param name="angle">The specified angle</param>
    /// <returns>"True" if angle in degrees is 90 or -90, otherwise "false"</returns>
    static boolean isOrthogonal(double angle)
    {
        double normalizeAngle = MathUtil.normalizeAngle(angle);
        return MathUtil.areEqual(normalizeAngle, 90.0d) || MathUtil.areEqual(normalizeAngle, 270.0d);
    }

    /// <summary>
    /// Converts the specified value to twips, gets the smallest integral value that is less than or equal
    /// to the converted value, and converts it to emus.
    /// </summary>
    static double floorTwipsToEmus(double emus)
    {
        return Math.floor(emus / ConvertUtilCore.EMUS_PER_TWIP) * ConvertUtilCore.EMUS_PER_TWIP;
    }

    /// <summary>
    /// Regex matches if string contains whitespace characters.
    /// </summary>
    static /*final*/ Regex SPACE_REGEX = new Regex("\\s");

    /// <summary>
    /// Separator used to split format code to the several formats for negative, positive and zero values.
    /// </summary>
    static final char SECTION_SEPARATOR = ';';

    /// <summary>
    /// Separator used to scale the number by one thousand.
    /// </summary>
    private static final char THOUSANDS_SEPARATOR = ',';

    /// <summary>
    /// Regex matches if string contains text like '_x...._' representing the UTF16 char code.
    /// </summary>
    private static /*final*/ Regex G_UTF_16_CODE_REGEX = new Regex("_x\\w{4}_");

    /// <summary>
    /// Regex matches if string contains text like '0,' representing thousands separator.
    /// </summary>
    private static /*final*/ Regex G_THOUSANDS_REGEX = new Regex("0,+");

    /// <summary>
    /// Regex matches if string contains "hyphen".
    /// </summary>
    private static /*final*/ Regex G_HYPHEN_REGEX = new Regex("[\\-]");

    /// <summary>
    /// Regex matches if string contains "MMMMM".
    /// </summary>
    /// <remarks>
    /// A part of the format code that is used to convert datetime. The first letter of the month should be rendered
    /// (3.8.31 Dates and times).
    /// </remarks>
    private static /*final*/ Regex G_MONTH_FIRST_LETTER_FORMAT = new Regex("MMMMM");
}

