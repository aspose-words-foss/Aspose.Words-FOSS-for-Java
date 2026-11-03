// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/06/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.DateTime;
import com.aspose.DateTimeUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Implements the DOCPROPERTY field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the indicated document information.
/// </remarks>
public class FieldDocProperty extends Field
{
    /*override*/ FieldUpdateAction updateCore()
    {
        if (!StringUtil.hasChars(getPropertyName()))
            return new FieldUpdateActionInsertErrorMessage(this, "Error! No property name supplied.");

        Document doc = fetchDocument();

        // WORDSNET-10809 First try to find the property in custom, then in built in document properties.
        DocumentProperty property = doc.getCustomDocumentProperties().get(getPropertyName());
        boolean isCustomProperty = (property != null);
        if (!isCustomProperty)
            property = doc.getBuiltInDocumentProperties().get(getPropertyName());

        if (property == null)
            return new FieldUpdateActionInsertErrorMessage(this, "Error! Unknown document property name.");

        return new FieldUpdateActionApplyResult(this, getUnformattedResult(property, isCustomProperty));
    }

    private Constant getUnformattedResult(DocumentProperty property, boolean isCustomProperty)
    {
        switch (property.getType())
        {
            case PropertyType.DATE_TIME:
                return new StringConstant(getUnformattedDateTimeResult(property, isCustomProperty));
            case PropertyType.DOUBLE:
                return new DoubleConstant(property.toDouble());
            default:
                return new StringConstant(property.toString());
        }
    }

    /// <summary>
    /// Returns field result without formatting to be applied.
    /// </summary>
    /// <param name="property">document property</param>
    /// <param name="isCustomProperty">true if <paramref name="property"/> is a custom document property</param>
    /// <returns>field result without formatting to be applied</returns>
    private String getUnformattedDateTimeResult(DocumentProperty property, boolean isCustomProperty)
    {
        // WORDSNET-4290 Value of date/time document property is truncated to date during DocumentProperty.ToString call.
        // Also it is not converted to local time value. So we need to handle it separately.
        Debug.assertTrue(property.getType() == PropertyType.DATE_TIME);

        msCultureInfo preProcessCulture = fetchDocument().getFieldOptions().getPreProcessCultureInternal();

        // MS Word always stores date/time document property value in UTC (truncated to minutes)
        // and converts it to local time at this point.
        DateTime value = DateTimeUtil.toLocalTime(property.toDateTimeInternal());

        // MS Word uses current locale short date pattern to represent date part of date/time
        // document property value in field result by default (when no picture switch is provided).
        // But it converts year format in its own fasion. As it is too complex to take in account all
        // cases of this convertation, we will do it only for most popular formats as follows:
        // "yyyy" -> "yyyy", "yy" -> "yyyy", "y" -> "yy".
        String dateFormat = FormatterPal.getShortDatePatternForCulture(preProcessCulture);
        dateFormat = G_YEAR_FORMAT_REGEX.replace(dateFormat, YEAR_FORMAT_REPLACE_PATTERN);

        // MS Word truncates custom date/time document property value to date and displays only date part by default.
        if (isCustomProperty)
            return value.toString(dateFormat);

        // MS Word uses first occurance of hour format in current locale long time pattern.
        // If it's not present then "H" is the default.
        String timeFormat = FormatterPal.getLongTimePatternForCulture(preProcessCulture);
        Match match = G_HOUR_FORMAT_REGEX.match(timeFormat);
        String hourFormat = match.getSuccess() ? match.getGroups().get(HOUR_FORMAT_GROUP_INDEX).getValue() : DEFAULT_HOUR_FORMAT;

        // MS Word uses first occurance of time separator in current locale long time pattern.
        // But if it's not present or its length is not equal to one then ":" is used instead.
        String timeSeparator = FormatterPal.getTimeSeparatorForCulture(preProcessCulture);
        if ((timeSeparator == null) || (timeSeparator.length() != DEFAULT_TIME_SEPARATOR.length()))
            timeSeparator = DEFAULT_TIME_SEPARATOR;

        // MS Word uses pattern "<ShortDate> <Hour><TimeSeparator>mm" relative to current locale to represent
        // built-in date/time document property value by default (when no picture switch is provided).
        String dateTimeFormat = msString.format(DATE_TIME_FORMAT_FORMAT, dateFormat, hourFormat, timeSeparator);

        return value.toString(dateTimeFormat);
    }

    /// <summary>
    /// Gets or sets the name of the document property to retrieve.
    /// </summary>
    String getPropertyName() { return getFieldCodeCache().getArgumentAsString(PROPERTY_NAME_ARGUMENT_INDEX); }
    void setPropertyName(String value) throws Exception { getFieldCodeCache().setArgument(PROPERTY_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Format of built-in date/time document property value default format. Single quotes are used to escape
    /// <see cref="DefaultTimeSeparator"/> which value has special meaning in .Net date/time formatting routines.
    /// Note that "\" can't be escaped that way, but we have no other options because of Java compatibility.
    /// MS Word always formats minutes with "mm" format in that case.
    /// </summary>
    private static final String DATE_TIME_FORMAT_FORMAT = "{0} {1}'{2}'mm";

    /// <summary>
    /// Default MS Word time separator.
    /// </summary>
    private static final String DEFAULT_TIME_SEPARATOR = ":";

    /// <summary>
    /// Default MS Word hour format.
    /// </summary>
    private static final String DEFAULT_HOUR_FORMAT = "H";

    /// <summary>
    /// Index of hour format group in <see cref="gHourFormatRegex"/>.
    /// </summary>
    private static final int HOUR_FORMAT_GROUP_INDEX = 2;

    /// <summary>
    /// Regular expression to extract hour format from time pattern.
    /// It bypasses hour format characters enclosed by single quotes which means they are not relative to hour format.
    /// </summary>
    private static /*final*/ Regex G_HOUR_FORMAT_REGEX = new Regex("\\A([^']*'[^']*')*?[^']*?((h|H)\\3?)", RegexOptions.COMPILED);

    /// <summary>
    /// Regular expression to convert year format according to MS Word.
    /// It bypasses year format characters enclosed by single quotes which means they are not relative to year format.
    /// </summary>
    private static /*final*/ Regex G_YEAR_FORMAT_REGEX = new Regex("\\G(([^']*'[^']*')*?[^']*?)y(y?)y*", RegexOptions.COMPILED);

    /// <summary>
    /// Replace pattern for <see cref="gYearFormatRegex"/> regex.
    /// If year format is "y" then it is replaced by "yy", otherwise it is replaced by "yyyy".
    /// </summary>
    private static final String YEAR_FORMAT_REPLACE_PATTERN = "$1yy$3$3";

    private static final int PROPERTY_NAME_ARGUMENT_INDEX = 0;
}

