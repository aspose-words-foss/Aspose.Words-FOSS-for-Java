// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/11/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.MathUtil;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Utility methods for 5.1.12.22 ST_FixedPercentage (Fixed Percentage) amd ST_Percentage.
/// </summary>
/*static*/ class DmlPercentageUtil
{
	/* Simulation of static class by using private constructor */
	private DmlPercentageUtil()
	{}

    /// <summary>
    /// Converts from 0..100% to 0.0..1.0 fraction.
    /// </summary>
    private static double fromPercent(String value, double defaultValue)
    {
        if (!isPercentWithSign(value))
            return defaultValue;

        // Trim % from the end.
        String num = value.substring((0), (0) + (value.length() - 1));

        return FormatterPal.parseDouble(num)/100.0;
    }

    /// <summary>
    /// Converts from 0..100% to 0.0..1.0 fraction.
    /// </summary>
    static double fromPercent(String value)
    {
        return fromPercent(value, 0.0);
    }

    /// <summary>
    /// Checks if the specified value is a number with the percent sign.
    /// </summary>
    private static boolean isPercentWithSign(String value)
    {
        return StringUtil.hasChars(value) && G_CONTENT_REGEX.isMatch(value);
    }

    /// <summary>
    /// Converts from 0..100000 (1000th percent) or 0.0..100.0% to 0.0..1.0 fraction.
    /// </summary>
    /// <param name="value">Input string to convert.</param>
    /// <param name="defaultValue">Default value to return, if input string is wrong percent value.
    /// Should be 0.0..1.0 fraction.</param>
    /// <param name="complianceInfo">Depending on input value this argument is used to specify which OOXML
    /// specification the document conforms.</param>
    /// <returns>Conversion result as 0.0..1.0 fraction.</returns>
    static double fromPercentOrDmlPercent(String value, double defaultValue, 
        OoxmlComplianceInfo complianceInfo)
    {
        if (isPercentWithSign(value))
        {
            if (complianceInfo != null)
                complianceInfo.markAsIsoTransitional();

            return fromPercent(value, defaultValue);
        }
        else
            return fromDmlPercent(value, defaultValue);
    }

    /// <summary>
    /// Converts from 0..100000 (1000th percent) or 0.0..100.0% to 0.0..1.0 fraction.
    /// </summary>
    static double fromPercentOrDmlPercent(String value, OoxmlComplianceInfo complianceInfo)
    {
        return fromPercentOrDmlPercent(value, 0.0, complianceInfo);
    }

    /// <summary>
    /// Converts from 0.0..1.0 to 0%..100%.
    /// </summary>
    private static String toPercent(double fraction)
    {
        return FormatterPal.doubleToStr9Decimals(fraction * 100.0) + "%";
    }

    /// <summary>
    /// Converts from 0.0..1.0 to 0..100000 (1000th percent) or to 0.00%..100.00% depending on isIsoStrict parameter.
    /// </summary>
    /// <param name="fraction">A fraction value to convert.</param>
    /// <param name="isIsoStrict">The format of ISO 29500 Strict specification should be used.</param>
    /// <returns>Conversion result.</returns>
    static String toPercentOrDmlPercent(double fraction, boolean isIsoStrict)
    {
        return isIsoStrict ?
            toPercent(fraction) :
            FormatterPal.doubleToStrNDecimals(toDmlPercent(fraction), 0);
    }

    /// <summary>
    /// Converts from 0..100000 to 0.0..1.0 (1000th percent to fraction).
    /// </summary>
    static double fromDmlPercent(double value)
    {
        return value / FACTOR;
    }

    /// <summary>
    /// Converts from 0..100000 to 0.0..1.0 (1000th percent to fraction).
    /// </summary>
    static double fromDmlPercent(String value, double defaultValue)
    {
        double number = FormatterPal.tryParseDoubleInvariant(value);
        return !Double.isNaN(number) ? fromDmlPercent(number) : defaultValue;
    }

    /// <summary>
    /// Converts from 0.0..1.0 to 0..100000 (fraction to 1000th percent).
    /// </summary>
    static double toDmlPercent(double fraction)
    {
        return fraction * FACTOR;
    }

    /// <summary>
    /// Converts from int 0..100000 to 0.0 to 1.0 fixed 16.16.
    /// </summary>
    static int dmlToPercentFixed(int value)
    {
        return ConvertUtilCore.doubleToFixed(fromDmlPercent(value));
    }

    /// <summary>
    /// Converts from 0.0..1.0 fixed 16.6 to int 0..100000.
    /// </summary>
    static int percentFixedToDml(int value)
    {
        return MathUtil.doubleToInt(toDmlPercent(ConvertUtilCore.fixedToDouble(value)));
    }

    /// <summary>
    /// Converts from int -100000..100000 to fixed 16.16 brightness value.
    /// </summary>
    static int dmlToBrightnessFixed(int value)
    {
        return ConvertUtilCore.doubleToFixed(value / DML_TO_BRIGHTNESS_RATIO);
    }

    /// <summary>
    /// Converts from fixed 16.16 brightness value to int -100000..100000.
    /// </summary>
    static int brightnessFixedToDml(int value)
    {
        return MathUtil.doubleToInt(ConvertUtilCore.fixedToDouble(value) * DML_TO_BRIGHTNESS_RATIO);
    }

    /// <summary>
    /// Converts from int -100000..100000 to fixed 16.16 contrast value.
    /// </summary>
    static int dmlToContrastFixed(int value)
    {
        // Transpose the value to make positive and then scale down to the 0.0 to 1.0 range.
        double percent = (value + (-DML_FIXED_PERCENT_MIN)) / DML_TO_CONTRAST_RATIO;
        // Now convert to model contrast value.
        return ConvertUtilCore.doubleToFixed(ImageData.percentToContrast(percent));
    }

    /// <summary>
    /// Converts from fixed 16.16 contrast value to int -100000..100000.
    /// </summary>
    static int contrastFixedToDml(int value)
    {
        // Convert model contrast value into percent 0.0 to 1.0.
        double percent = ImageData.contrastToPercent(ConvertUtilCore.fixedToDouble(value));
        // Scale back to OOXML range and transpose.
        return MathUtil.doubleToInt((percent * DML_TO_CONTRAST_RATIO) - (-DML_FIXED_PERCENT_MIN));
    }

    /// <summary>
    /// Converts from DML percent to VML percent value.
    /// </summary>
    static int dmlToVmlPercent(double value)
    {
        // In VML value is integer 1/10th percent. In DML 1/1000th percent.
        return MathUtil.doubleToInt(value*VML_PER_PERCENT/DML_PER_PERCENT);
    }

    /// <summary>
    /// Converts from VML percent to DML percent value.
    /// </summary>
    static double vmlToDmlPercent(double value)
    {
        return value * DML_PER_PERCENT / VML_PER_PERCENT;
    }

    /// <summary>
    /// Rounds specified value according to precision which used to store
    /// percent value in DrawingML.
    /// </summary>
    static double toDmlPercentPrecision(double value)
    {
        // The definition of the percent type described at "22.9.2.11 ST_PositivePercentage"
        // has the following restriction: "This simple type's contents shall
        // match the following regular expression pattern: [0-9]+(\.[0-9]+)?%". Mimic MSW behavior
        // and take only first 3 digits from fraction part.
        return msMath.round(value, 5, MidpointRounding.AWAY_FROM_ZERO);
    }

    private static final double FACTOR = 100000.0;

    private static final double DML_PER_PERCENT = 1000.0;
    private static final double VML_PER_PERCENT = 10.0;

    private static final double CONTRAST_RANGE = ImageData.CONTRAST_MAX - ImageData.CONTRAST_MIN;
    private static final double BRIGHTNESS_RANGE = ImageData.BRIGHTNESS_MAX - ImageData.BRIGHTNESS_MIN;

    private static final int DML_FIXED_PERCENT_MAX = 100000;
    private static final int DML_FIXED_PERCENT_MIN = -100000;
    private static final int DML_FIXED_PERCEN_RANGE = DML_FIXED_PERCENT_MAX - DML_FIXED_PERCENT_MIN;

    private static final double DML_TO_BRIGHTNESS_RATIO = DML_FIXED_PERCEN_RANGE / BRIGHTNESS_RANGE;
    private static final double DML_TO_CONTRAST_RATIO = DML_FIXED_PERCEN_RANGE / CONTRAST_RANGE;

    private static /*final*/ Regex G_CONTENT_REGEX = new Regex("-?[0-9]+(\\.[0-9]+)?%", RegexOptions.COMPILED);
}

