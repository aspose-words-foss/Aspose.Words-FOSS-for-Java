// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2022 by Ilya Egorov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Convert;
import com.aspose.Language;
import com.aspose.LocaleClassifier;
import com.aspose.StringUtil;
import com.aspose.SystemPal;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Implements methods for preparing format code (3.8.31 numFmts (Number Formats) ) used to display chart values.
/// </summary>
/*static*/ class DmlChartFormatCodeValidator
{
	/* Simulation of static class by using private constructor */
	private DmlChartFormatCodeValidator()
	{}

    /// <summary>
    /// Resolves lower case and upper case 'm' in the format code.
    /// In MS Word format codes there is no difference between m and M, both of them can work as minutes and months. 
    /// If "m" or "mm" code is used immediately after the "h" or "hh" code (for h 1 ours) or immediately before the "ss"
    /// code (for seconds), the application shall display minutes instead of the month.
    /// </summary>
    static String correctDateFormatCode(String originalFormat)
    {
        // Replace all lowercase 'm' with uppercase 'M'.
        String result = msString.replace(originalFormat, 'm', 'M');

        // Replace sequence of 'M' immediately after 'h' with lower case 'm'.
        result = resolveMinutesVsMonths(result, true);

        // Replace sequence of 'M' immediately before 's' with lower case 'm'.
        result = resolveMinutesVsMonths(result, false);

        // WORDSNET-10975
        // '[$-409]m/d/yy h:mm AM/PM;@' is standard format code that contains condition, alternative format code and AM/PM.
        // Replace 'AM/PM' with 'tt', which is supported by .NET.
        result = result.replace("AM/PM", "tt");
        // If [$-F800] is contained in the format code, use the long date format.
        if (G_SYS_DATE_CODE_REGEX.isMatch(result))
            return "D";
        // If [$-F400] is contained in the format code, use the long time format.
        if (G_SYS_TIME_CODE_REGEX.isMatch(result))
            return "T";

        // remove 'g' from format code, in .NET it means the period or era. MS Word seems to ignore it (see TestJira13957)
        result = result.replace("g", "");

        // Replace lower case 'h' with uppercase 'H', .NET behavior with lower case 'h' is different from upper case.
        if (!result.contains("tt"))
            result = msString.replace(result, 'h', 'H');

        result = removeAdditionalConditions(result);
        return result;
    }

    /// <summary>
    /// Removes from the format code additional conditions.
    /// </summary>
    /// <param name="originalFormat">The specified string with the format code.</param>
    /// <returns>The format code without additional conditions</returns>
    static String removeAdditionalConditions(String originalFormat)
    {
        // Remove condition at the beginning of format code and alternative format code at the end.
        // [$-409] - This seem to be locale condition
        // WORDSNET-18649 Format code like [$-407] defines language to use. Remove this text from format code.
        String result = G_LANGUAGE_CODE_REGEX.replace(originalFormat, "");
        result = result.replace("[$]", "");
        // WORDSNET-22540 Text with conditions, contained in the format code, should be removed.
        result = G_SQUARE_BRACKET_REGEX.replace(result, "");
        // ;@ - is alternative format code.
        result = result.replace(";@", "");
        return result;
    }

    /// <summary>
    /// Gets the actual format code based on the axis format code, series format code and value format code.
    /// </summary>
    /// <param name="axisFormatCode">The specified axis format code</param>
    /// <param name="isSourceLinked">Indicates, whether series values are linked with the source</param>
    /// <param name="valueFormatCode">The specified value format code</param>
    /// <param name="seriesFormatCode">The specified series format code</param>
    /// <returns>The actual format code</returns>
    static String getFormatCode(String axisFormatCode, boolean isSourceLinked, String valueFormatCode,
        String seriesFormatCode)
    {
        return isGeneralFormatCode(axisFormatCode) || isSourceLinked
            ? (valueFormatCode == null) ? seriesFormatCode : valueFormatCode
            : axisFormatCode;
    }

    /// <summary>
    /// Gets <see cref="CultureInfo"/> taking into account language settings, if they are specified in format code.
    /// </summary>
    /// <param name="originalFormat">the specified format code</param>
    /// <returns><see cref="CultureInfo"/></returns>
    static msCultureInfo getCultureInfo(String originalFormat)
    {
        msCultureInfo culture = msCultureInfo.getCurrentCulture();
        Match match = G_LANGUAGE_CODE_REGEX.match(originalFormat);

        if (match.getLength() != 0)
        {
            String result = Regex.replace(match.getValue(), "\\[\\$(\\w*)-", "0x");
            result = result.replace("]", "");
            int languageValue = Convert.toInt32(result, 16);
            /*Language*/int language = (/*Language*/int)languageValue;
            // Check if language value is defined,
            if (LocaleClassifier.isDefined(language))
                culture = msCultureInfo.getCultureInfo(languageValue);
        }

        return culture;
    }

    /// <summary>
    /// When date format 'm/d/yyyy' is specified, MS Word uses current culture date format: change the format
    /// in the same way.
    /// </summary>
    static String correctDefaultDateFormat(String formatCode)
    {
        return StringUtil.equalsIgnoreCase(formatCode, "m/d/yyyy")
            ? SystemPal.getCurrentCulture().getDateTimeFormat().getShortDatePattern()
            : formatCode;
    }

    static boolean isGeneralFormatCode(String formatCode)
    {
        return !StringUtil.hasChars(formatCode) || StringUtil.equalsIgnoreCase(formatCode, GENERAL_FORMAT);
    }

    /// <summary>
    /// Gets format code with text in double quotes and chars escaped with backslash.
    /// </summary>
    static String[] getFormatCodeParts(String format)
    {
        if (isGeneralFormatCode(format))
            return new String[0];

        format = validateFormatCode(format);
        int indexOfGeneral = msString.indexOf(format, GENERAL_FORMAT, StringComparison.ORDINAL_IGNORE_CASE);

        if (indexOfGeneral < 0)
            return new String[0];
       
        String firstPart = removeQuotesAndSlashes(format.substring((0), (0) + (indexOfGeneral)));
        int indexOfGeneralEnd = indexOfGeneral + GENERAL_FORMAT.length();
        int lenght = format.length() - indexOfGeneralEnd;
        String secondPart = removeQuotesAndSlashes(format.substring((indexOfGeneralEnd), (indexOfGeneralEnd) + (lenght)));

        return new String[] { firstPart, secondPart }; 
    }

    static String validateFormatCode(String formatCode)
    {
        // If source format code is empty string or null, return it as is.
        if (!StringUtil.hasChars(formatCode))
            return formatCode;

        // If format code is "general" or "standard" return "general" that means normal number format should be used.
        if (StringUtil.equalsIgnoreCase(formatCode, GENERAL_FORMAT) ||
            StringUtil.equalsIgnoreCase(formatCode, "standard") ||
            StringUtil.equalsIgnoreCase(formatCode, "estandar") || // WORDSNET-15534 One more variation of general format.
            StringUtil.equalsIgnoreCase(formatCode, "standaard")) // WORDSNET-10606 "Standaard" by mistake is treated as date format.
            return GENERAL_FORMAT;

        // Number format documentation http://exceldesignsolutions.com/a-comprehensive-guide-number-formats-excel
        // WORDSNET-11416 There might be colors in format code. We currently do not support this feature.
        // So simply remove colors. Colors are specified within [] brackets.
        // Color can be set using color name.
        formatCode = Regex.replace(formatCode, "\\[([Bb]lack|[Ww]hite|[Rr]ed|[Gg]reen|[Bb]lue|[Yy]ellow|[Mm]agenta|[Cc]yan)\\]", "");
        // Color index
        formatCode = Regex.replace(formatCode, "\\[([Cc]olor\\s*\\d+)\\]", "");
        // And color code.
        formatCode = Regex.replace(formatCode, "\\[(\\#\\d+)\\]", "");

        // WORDSNET-13446 Replace format code like [$€-813] with currency character.
        // Currency character is the first matching group.
        formatCode = Regex.replace(formatCode, "\\[\\$(\\S{1})-\\d+\\]", "$1");

        // According to the documentation '?' digit placeholder follows the same rules as the 0 (zero). 
        // However it seems it behaves as '#', so replace '?' with '#'.
        formatCode = formatCode.replace("?", "#");

        // * Displays the proceeding [single] character as many times is required to fill the width of the cell. 
        // This proceeding character will always be taken as a literal, regardless of what character is actually used.
        // Remove "*." sequence.
        // WORDSNET - 28617 Text inside double quotes should not be modified.
        formatCode = Regex.replace(formatCode, "(\"[^\"]*\")|\\*.", "$1");

        // WORDSNET-15237 If format code is '@', use general format. '@' is special character in excel and means 
        // Displays the text entered in the cell. 
        if ("@".equals(formatCode))
            formatCode = "";

        // According to 3.8.21 of specification the pattern "_)" should be replaced by a space character. This pattern is
        // used to line up the positive and negative values in axis labels. But the space must be trimmed when rendering
        // data labels (See ChartDataLabel.GetStringValue).
        return Regex.replace(formatCode, "_.", " ");
    }

    /// <summary>
    /// Replaces 'M' or 'MM' after 'h' or 'hh' or before 's' or 'ss' with lower case 'm' or 'mm'.
    /// afterHours flag indicates whether method should replace 'M' after 'h' or before 's'. 
    /// </summary>
    private static String resolveMinutesVsMonths(String input, boolean afterHours)
    {
        Regex regex = afterHours ? G_RIGHT_M : G_LEFT_M;
        int mmIndex = afterHours ? RIGHT_M_GROUP : LEFT_M_GROUP;

        Match match = regex.match(input);
        if (StringUtil.hasChars(match.getValue()))
        {
            String mm = match.getGroups().get(mmIndex).getValue();
            return input.replace(match.getValue(), match.getValue().replace(mm, mm.toLowerCase()));
        }

        return input;
    }

    /// <summary>
    /// Return true if string is date format code.
    /// Note: method uses assumption that if string contains date format specifies it is date format string.
    /// </summary>
    static boolean isDateFormat(String formatCode)
    {
        if (isGeneralFormatCode(formatCode))
            return false;

        // First remove from the input string all text between double quotes and character with a backslash.
        // According to OOXML spec, text between double quotes or chars escaped with backslash
        // in formatCode string should appear without changes in the resulting string.
        String testString = G_QUOTED_TEXT_REGEX.replace(formatCode, "");

        // The second regex checks whether the resulting string contains date format specifies.
        return G_DATE_FORMAT_REGEX.isMatch(testString);
    }

    /// <summary>
    /// Indicates whether the format code is "Time Format" (hours, minutes or seconds and not "days", "months" and "years").
    /// </summary>
    static boolean isTimeFormat(String formatCode)
    {
        return isDateFormat(formatCode) && !formatCode.contains("d") && !formatCode.contains("y") &&
           ((msString.indexOf(formatCode, "h", StringComparison.ORDINAL_IGNORE_CASE) >= 0) ||
            (msString.indexOf(formatCode, "s", StringComparison.ORDINAL_IGNORE_CASE) >= 0));
    }

    /// <summary>
    /// Return true if string is percent format code.
    /// Note: method uses assumption that if string contains '%' char it is percent format string.
    /// </summary>
    static boolean isPercentFormat(String formatCode)
    {
        if (isGeneralFormatCode(formatCode))
            return false;

        // First remove from the input string all text between double quotes 
        // and character with a backslash.
        // According to OOXML spec, text between double quotes or chars escaped with backslash
        // in formatCode string should appear without changes in the resulting string.
        String testString = G_QUOTED_TEXT_REGEX.replace(formatCode, "");

        // The second regex checks whether the resulting string contains 
        // date format specifies.
        return G_PERCENTE_FORMAT_REGEX.isMatch(testString);
    }

    private static String removeQuotesAndSlashes(String input)
    {
        if (msString.isNullOrEmpty(input))
            return "";

        int last = input.length() - 1;
        char slash = '\\';
        char quote = '\"';
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            boolean isNextSlash = (i < last) && (input.charAt(i + 1) == slash);
            boolean isQutesWithSlash = (c == quote) && isNextSlash;
            boolean isTripleSlash = (c == slash) && isNextSlash  && (i < (last - 1)) && (input.charAt(i + 2) == slash);

            if ((c != quote) && (c != slash))
            {
                sb.append(c);
            }
            // Quotes and slashes should be removed from result.
            else
            {
                // Combination of double quotes and backslash should be rendered as backslash.
                // The triple backslash should be rendered as backslash.
                if (isQutesWithSlash || isTripleSlash)
                {
                    sb.append(slash);
                    i += isQutesWithSlash ? 1 : 2;
                }

                continue;
            }
        }

        return sb.toString();
    }

    /// <summary>
    /// Regex matches if string contains any of date format specifiers.
    /// </summary>
    private static /*final*/ Regex G_DATE_FORMAT_REGEX =
        new Regex("K|[ghHmst]{1,2}|z{1,3}|[dM]{1,4}|y{1,5}|[fF]{1,7}");

    /// <summary>
    /// Regex matches if string contains '%' char.
    /// </summary>
    private static /*final*/ Regex G_PERCENTE_FORMAT_REGEX = new Regex("%");

    /// <summary>
    /// Regex matches 'M' or 'MM' immediately after 'h' or 'hh'. There might be delimiters between 'm' and 'h'.
    /// </summary>
    private static /*final*/ Regex G_RIGHT_M =
        new Regex("(h{1,2}([-\\$\\+\\(:\\^'\\{<=/\\)!&~}>\\s]|(\".*\")|(\\\\\\S))*)(M{1,2})", RegexOptions.COMPILED);

    /// <summary>
    /// Regex matches 'M' or 'MM' immediately before 's' or 'ss'. There might be delimiters between 'm' and 's'.
    /// </summary>
    private static /*final*/ Regex G_LEFT_M =
        new Regex("(M{1,2})(([-\\$\\+\\(:\\^'\\{<=/\\)!&~}>\\s]|(\".*\")|(\\\\\\S))*s{1,2})");
   
    /// <summary>
    /// Regex matches text between double quotes and chars escaped with backslash.
    /// </summary>
    private static /*final*/ Regex G_QUOTED_TEXT_REGEX = new Regex("(\".*\")|(\\\\\\S)", RegexOptions.COMPILED);

    /// <summary>
    /// Regex matches if string contains text like '[$-....]' representing language settings.
    /// </summary>
    private static /*final*/ Regex G_LANGUAGE_CODE_REGEX = new Regex("\\[\\$-\\w*\\]");

    /// <summary>
    /// Regex matches if string contains text like '[*]' representing additional conditions.
    /// </summary>
    private static /*final*/ Regex G_SQUARE_BRACKET_REGEX = new Regex("\\[\\W*\\w*\\]");
    
    /// <summary>
    /// Regex matches if string contains text like '[$-F800]' representing system long date format.
    /// </summary>
    private static /*final*/ Regex G_SYS_DATE_CODE_REGEX = new Regex("\\[\\$-F800]");

    /// <summary>
    /// Regex matches if string contains text like '[$-....]' representing system time format.
    /// </summary>
    private static /*final*/ Regex G_SYS_TIME_CODE_REGEX = new Regex("\\[\\$-F400]");

    static final String GENERAL_FORMAT = "general";

    /// <summary>
    /// Index of M or MM group.
    /// </summary>
    private static final int RIGHT_M_GROUP = 5;

    /// <summary>
    /// Index of M or MM group.
    /// </summary>
    private static final int LEFT_M_GROUP = 1;
}

