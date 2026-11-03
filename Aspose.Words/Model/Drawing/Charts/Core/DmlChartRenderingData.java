// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/12/2021 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.Iterator;
import com.aspose.Debug;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.TimeSpan;
import com.aspose.MathUtil;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;
import com.aspose.DoublePal;
import com.aspose.ms.System.msArray;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;


/// <summary>
/// Represents chart series data to render.
/// </summary>
class DmlChartRenderingData implements Iterable<DmlChartValue>, Cloneable
{
    DmlChartRenderingData(DmlChartValueCollection values)
    {
        mOriginalValues = values;
        mValues = null;
    }

    void addValue(DmlChartValue value)
    {
        prepareForModification();
        getValues().add(value);
    }

    DmlChartRenderingData deepClone()
    {
        return deepClone(mOriginalValues.deepClone());
    }

    DmlChartRenderingData deepClone(DmlChartValueCollection originalData)
    {
        DmlChartRenderingData lhs = (DmlChartRenderingData)memberwiseClone();

        lhs.mOriginalValues = originalData;

        if (mValues != null)
            lhs.mValues = mValues.deepClone();

        return lhs;
    }

    /// <summary>
    /// If data is empty or incomplete, fills it with dummy data.
    /// </summary>
    void addDummyData(int valueCount)
    {
        if (isDate() || hasNonEmptyValues())
            return;

        setValueCount(Math.max(valueCount, 1));
        fillWithDummyValues();
    }

    /// <summary>
    /// Gets a value at the specified index.
    /// </summary>
    DmlChartValue getValue(int index)
    {
        return getValue(getValues(), index);
    }

    /// <summary>
    /// Gets a value at the specified index.
    /// </summary>
    static DmlChartValue getValue(DmlChartValueCollection data, int index)
    {
        // If there is no data, we should generate data.
        // No data seems to be allowed only for Category axis, that is why return str value.
        if (data == null)
            return new DmlChartStrValue(index, Integer.toString((index + 1)));

        DmlChartValue value = data.get(index);

        // If there is no entry with specified index, and this is str data, return a dummy value.
        if ((value == null) &&
            ((data.getValueType() == DmlChartValueType.STRING) || (data.getValueType() == DmlChartValueType.MULTI_LVL_STRING)))
            return new DmlChartDummyValue(index, index + 1);

        return value;
    }

    /// <summary>
    /// Gets a value, originally loaded from the document, at the specified index.
    /// </summary>
    DmlChartValue getOriginalValue(int index)
    {
        return getValue(mOriginalValues, index);
    }

    /// <summary>
    /// Changes value type of the data collection. Method also converts values to the corresponding string values.
    /// Has effect only when original value type is numeric.
    /// </summary>
    void resetTypeToString(String axisFormatCode, boolean isSourceLinked)
    {
        if (getValueType() != DmlChartValueType.NUMERIC && getValueType() != DmlChartValueType.MULTI_LVL_NUMERIC)
            return;

        changeCollectionValues(getValues(), DmlChartValueType.STRING, axisFormatCode, isSourceLinked);
    }

    /// <summary>
    /// Returns array of values that represents this data source.
    /// Used for rendering pie charts.
    /// </summary>
    DmlChartValue[] toArray()
    {
        DmlChartValue[] values = new DmlChartValue[getValueCount()];
        int notNullSize = 0;

        for (int i = 0; i < getValueCount(); i++)
        {
            DmlChartValue val = getValue(i);

            if (val == null)
                continue;

            values[notNullSize++] = val;
        }

        // Strip out null values.
        DmlChartValue[] notNullValues = new DmlChartValue[notNullSize];
        System.arraycopy((values), 0, (notNullValues), 0, (notNullSize));

        return notNullValues;
    }

    /// <summary>
    /// Returns sum of all values in the data source.
    /// </summary>
    double getSum()
    {
        // Had to make the sum variable of double type, because different order of iterated values
        // led to different results because of float type's low precision.
        double sum = 0.0;

        // Sum is used for calculation of percentage values. It seems the sum should be calculated for absolute values.
        for (DmlChartValue val : getValues())
            sum += Math.abs(DmlChartValue.isNullOrNaN(val) ? 0.0 : val.getValue());

        return sum;
    }

    /// <summary>
    /// Returns first not null value from the collection, if there are not null values, returns null.
    /// </summary>
    DmlChartValue getFirstNotNullValue()
    {
        int firstIndex = getFirstNotNullIndex();
        return (firstIndex < 0) ? null : getValues().get(firstIndex);
    }

    /// <summary>
    /// Returns dummy value for date data. Value of dummy value is calculated using other not null dates.
    /// </summary>
    DmlChartValue getDummyDate(int index)
    {
        int firstIndex = getFirstNotNullIndex();
        int lastIndex = getLastNotNullIndex();

        // If we have no not null values, we cannot generate dummy value, return null.
        if ((firstIndex < 0) || (lastIndex < 0) || (firstIndex == lastIndex))
            return null;

        DmlChartValue firstValue = getValues().get(firstIndex);
        DmlChartValue lastValue = getValues().get(lastIndex);

        // Otherwise try to calculate value using approximate step.
        double step = (lastValue.getValue() - firstValue.getValue()) / (lastIndex - firstIndex);
        double val = firstValue.getValue() + (index - firstIndex) * step;

        return new DmlChartDummyValue(index, val);
    }

    /// <summary>
    /// Returns percentage value for data label.
    /// </summary>
    double getPercentValue(int index, String format)
    {
        if (mPercentages == null)
            initPercentages(format);

        return (index >= mPercentages.length) ? 0.0d : mPercentages[index];
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<DmlChartValue> iterator()
    {
        return getValues().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Fills the specified <see cref="DmlChartRenderingData"/> with "dummy" values. The collection is expected
    /// to be empty with defined <see cref="ValueCount"/> property.
    /// </summary>
    private void fillWithDummyValues()
    {
        Debug.assertTrue(!hasNonEmptyValues());
        prepareForModification();
        getValues().fillWithDummyValues();
    }

    /// <summary>
    /// Returns index of the first not null entry. Return negative value if there is no not null values.
    /// </summary>
    private int getFirstNotNullIndex()
    {
        for (int i = 0; i <= getLastNonEmptyValueIndex(); i++)
        {
            if (!DmlChartValue.isNullOrNaN(getValues().get(i)))
                return i;
        }

        return -1;
    }

    /// <summary>
    /// Returns index of the last not null entry. Return negative value if there is no not null values.
    /// </summary>
    private int getLastNotNullIndex()
    {
        for (int i = getLastNonEmptyValueIndex(); i >= 0; i--)
        {
            if (!DmlChartValue.isNullOrNaN(getValues().get(i)))
                return i;
        }

        return -1;
    }

    /// <summary>
    /// Changes values into current collection.
    /// </summary>
    /// <param name="sourceValues">The values collection that is used as sample</param>
    /// <param name="valueType"><see cref="DmlChartValueType"/> of the result collection</param>
    /// <param name="formatCode">The format code is used to convert values of the source collection</param>
    /// <param name="isSourceLinked">Indicates whether the source collection is linked with source</param>
    private void changeCollectionValues(
        DmlChartValueCollection sourceValues,
        /*DmlChartValueType*/int valueType,
        String formatCode,
        boolean isSourceLinked)
    {
        DmlChartValueCollection newData = new DmlChartValueCollection(valueType);

        for (DmlChartValue value : sourceValues)
        {
            DmlChartNumValue numValue = ms.as(value, DmlChartNumValue.class);

            if (numValue == null)
                continue;

            newData.add((valueType == DmlChartValueType.STRING)
                ? (DmlChartValue)getStringValue(numValue, formatCode, isSourceLinked)
                : (DmlChartValue)new DmlChartNumValue(numValue.getIndex(), numValue.getValue(), numValue.getFormatCode()));
        }

        mValues = newData;
    }

    /// <summary>
    /// Gets <see cref="DmlChartStrValue"/> based on input <see cref="DmlChartNumValue"/> and format code.
    /// </summary>
    /// <param name="value">The input <see cref="DmlChartNumValue"/> from source collection</param>
    /// <param name="formatCode">Format that is used to convert value to string</param>
    /// <param name="isSourceLinked">Indicates whether the source collection is linked with source</param>
    /// <returns><see cref="DmlChartStrValue"/></returns>
    private DmlChartStrValue getStringValue(DmlChartNumValue value, String formatCode, boolean isSourceLinked)
    {
        String strValue;
        String actualFormat = DmlChartFormatCodeValidator.getFormatCode(formatCode, isSourceLinked,
            value.getFormatCode(), mFormatCode);

        // WORDSNET-17258 The date format can be used for category values. In this case, MS Word renders dates.
        // WORDSNET-23818 The value type should be checked.
        // WORDSNET-25379 The value must not exceed the .NET maximum date value.
        boolean isDateFormat =
            isCorrectMsWordDate(value.getValue()) &&
            (value.isDate() || DmlChartFormatCodeValidator.isDateFormat(actualFormat)) &&
            !DmlChartFormatCodeValidator.isGeneralFormatCode(actualFormat);

        if (isDateFormat && (value.getValueType() != DmlChartValueType.NONE) && !value.isNaN())
            strValue = getStringDate(value.getValue(), actualFormat);
        // WORDSNET-22826 If a format code is specified, it should be used when converting the value to a string.
        // WORDSNET-28787 Used DoubleToString from DmlChartRenderingUtil when converting the value to a string.
        else if (!DmlChartFormatCodeValidator.isGeneralFormatCode(actualFormat) && !Double.isNaN(value.getValue()))
            strValue = DmlChartRenderingUtil.doubleToString(value.getValue(), actualFormat);
        else
            strValue = value.getStringValue();

        return new DmlChartStrValue(value.getIndex(), strValue);
    }


    /// <summary>
    /// Gets a flag indicating whether the specified numeric MS Word date value is correct.
    /// </summary>
    private static boolean isCorrectMsWordDate(double value)
    {
        final double MIN_DATE_VALUE = 0.0; // 00.01.1900, the minimum date in MS Word.
        final double MAX_DATE_VALUE = 2958466.0; // Corresponds to DateTime.MaxDate.
        return (value >= MIN_DATE_VALUE) && (value < MAX_DATE_VALUE);
    }

    /// <summary>
    /// Gets a string representation of a date based on the specified format code.
    /// </summary>
    /// <param name="value">The specified value</param>
    /// <param name="formatCode">The specified format code</param>
    /// <returns>The string representation of the date</returns>
    private String getStringDate(double value, String formatCode)
    {
        // The axis label is not rendered if value is less than 0 and the axis is "DateAxis".
        if (value < 0)
            return "";

        boolean isTime = DmlChartFormatCodeValidator.isTimeFormat(formatCode);
        DateTime date = DmlChartUtil.getDateFromDouble(value);

        // If values are dates MS Word rounds them, do the same.
        // WORDSNET-21384 MS Word does not round values if they are "time".
        long roundDateStep = isTime ? 0 : getRoundDateStep();

        DateTime roundDate = (roundDateStep != 0)
            ? new DateTime(midPointRound(date.getTicks(), roundDateStep) * roundDateStep)
            : date;

        return DmlChartRenderingUtil.getStringDate(roundDate, formatCode);
    }

    private long getRoundDateStep()
    {
        long step = getTicksStep();
        if (midPointRound(step, TimeSpan.TICKS_PER_DAY) > 0)
            return TimeSpan.TICKS_PER_DAY;
        if (midPointRound(step, TimeSpan.TICKS_PER_HOUR) > 0)
            return TimeSpan.TICKS_PER_HOUR;
        if (midPointRound(step, TimeSpan.TICKS_PER_MINUTE) > 0)
            return TimeSpan.TICKS_PER_MINUTE;
        if (midPointRound(step, TimeSpan.TICKS_PER_SECOND) > 0)
            return TimeSpan.TICKS_PER_SECOND;
        return step;
    }

    private long getTicksStep()
    {
        double minValue = Double.MAX_VALUE;
        double maxValue = -Double.MAX_VALUE;
        for (DmlChartValue value : getValues())
        {
            if (value.isNaN())
                continue;

            minValue = Math.min(minValue, value.getValue());
            maxValue = Math.max(maxValue, value.getValue());
        }

        double step = (maxValue - minValue) / (getLastNonEmptyValueIndex() + 1);

        DateTime start = DateTime.fromOADate(0.0);
        DateTime end = DateTime.fromOADate(step);

        TimeSpan span = (DateTime.subtract(end, start));
        return span.getTicks();
    }

    private static long midPointRound(long src, long round)
    {
        return (src + (round / 2) + 1) / round;
    }

    private void initPercentages(String format)
    {
        mPercentages = new double[getValueCount()];
        DmlChartDummyValue[] positiveDiffs = new DmlChartDummyValue[getValueCount()];
        DmlChartDummyValue[] negativeDiffs = new DmlChartDummyValue[getValueCount()];
        double sum = getSum();
        double percentageSum = 0.0;
        int precision = getPrecision(format);

        // Initialize percentages with initial values. MS word rounds percentage values to whole numbers
        // if format is not specified and sometimes it is required to adjust values to get 100% as the sum.
        for (int i = 0; i < getValueCount(); i++)
        {
            DmlChartValue val = getValue(i);
            // If sum is 1 we have no need to divide the original value.
            double factor = MathUtil.areEqual(1.0d, sum) ? 1.0 : (1.0 / sum);
            double fractionValue = DmlChartValue.isNullOrNaN(val) ? 0.0d : (val.getValue() * factor);
            mPercentages[i] = fractionValue;

            // Mid point value must be rounded to greater value.
            double percentValue = msMath.round(fractionValue, precision, MidpointRounding.AWAY_FROM_ZERO);
            double diff = percentValue - fractionValue;
            percentageSum += Math.abs(percentValue);

            if (MathUtil.isZero(diff))
                continue;

            // Ms Word double rounding seems to work in different way than .NET. It seems simply trimming value to 15
            // digits after the dot do the trick.
            DmlChartDummyValue[] usedDiffs = (diff > 0) ? positiveDiffs : negativeDiffs;
            usedDiffs[i] = new DmlChartDummyValue(i, DoublePal.trim(Math.abs(diff), 15));
        }

        // The desired value of percentage sum is 100, calculate difference between desired and actual values.
        double delta = 1.0 - percentageSum;

        // Absolute value of delta is the number of values to be adjusted.
        double deltaAbs = Math.abs(delta);

        // If delta is zero, percentage values should not be adjusted.
        if (MathUtil.isZero(delta))
            return;

        // If deltaAbs * 100 < 1 or the percent symbol is not used, percentage values should not be adjusted.
        if (deltaAbs * 100.0 < 1 || !DmlChartFormatCodeValidator.isPercentFormat(format))
            return;

        // MS Word in most of case adds 1%. There are cases when it adds 2% maybe there are other cases,
        // but I cannot figure out how the addition is calculated. Though it seems to be rare case, so use 1% as an
        // addition.
        double addition = (1.0 / Math.pow(10.0, precision)) * (delta / deltaAbs);

        // Sort differences array, it seems MS Word adjusts values that have the biggest difference between
        // rounded value and actual value. After sorting values that should be adjusted become at the end of array.
        // WORDSNET-22693 If addition is negative, MS Words corrects values with a maximum positive difference.
        DmlChartDummyValue[] diffs = (addition > 0) ? negativeDiffs : positiveDiffs;
        int numberOfNulls = moveNullElements(diffs);
        msArray.sort(diffs, numberOfNulls, diffs.length - numberOfNulls);
        int interations = DoublePal.roundToIntUp(deltaAbs * 100.0);
        for (int i = 0; i < interations; i++)
        {
            int index = diffs.length - (i + 1);
            DmlChartDummyValue diffVal = diffs[index];
            mPercentages[diffVal.getIndex()] += addition;
        }
    }

    private static int moveNullElements(DmlChartDummyValue[] array)
    {
        int nullCount = 0;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == null)
            {
                array[i] = array[nullCount];
                array[nullCount] = null;
                nullCount++;
            }
        }
        return nullCount;
    }

    /// <summary>
    /// Gets the number of digits after decimal separator from the specified format.
    /// </summary>
    /// <param name="format">The specified format</param>
    /// <returns>The precision</returns>
    private static int getPrecision(String format)
    {
        int countPercentSymbol = 0;
        char separator = FormatterPal.getDecimalSeparatorCurrent();
        // Take a number with the maximum number of decimal places.
        double baseValueForPecisionCalculation = 1d / 3.0;
        String formattedValue = FormatterPal.doubleToStr(baseValueForPecisionCalculation, format);
        StringBuilder doubleValue = new StringBuilder();

        for (int i = 0; i < formattedValue.length(); i++)
        {
        	char chr = formattedValue.charAt(i);
            // In the case of a "custom" format the percent symbol can be used several times.
            // The precision must be increased by 2 each time when the percent symbol is used.
            if (chr == '%')
                countPercentSymbol++;

            // In the case of a "custom" format a non-digit chars can be used. Remove this chars.
            if (Character.isDigit(chr) || chr == separator)
                doubleValue.append(chr);
        }

        formattedValue = doubleValue.toString();
        int precision = countPercentSymbol * 2;
        int separatorIndex = formattedValue.indexOf(separator);

        if (separatorIndex > 0)
            precision += formattedValue.length() - (separatorIndex + 1);

        // 15 is the maximum value for Math.Round method if a double type is used.
        return Math.min(precision, 15);
    }

    /// <summary>
    /// Creates a copy of the original chart data value collection, if one has not already been created, so that the
    /// original data loaded from the document or defined by a customer will not be affected during a modification.
    /// </summary>
    private void prepareForModification()
    {
        if (mValues == null)
            mValues = mOriginalValues.deepClone();
    }

    /// <summary>
    /// Returns a data value by an index.
    /// </summary>
    DmlChartValue get(int index) { return getValues().get(index); }

    /// <summary>
    /// Returns number of data values of a chart dimension including empty values.
    /// </summary>
    int getValueCount() { return getValues().getValueCount(); }
    void setValueCount(int value)
    {
        prepareForModification();
        getValues().setValueCount(value);
    }

    /// <summary>
    /// Returns number of data values of a chart dimension including empty values.
    /// </summary>
    /// <remarks>
    /// WORDSNET-21034 The series of radar chart use the number of non-empty values. The original document contains
    /// 11 non-empty x-values and the index of the last non-empty value is 20. So, <see cref="LastNonEmptyValueIndex"/> can
    /// not be used.
    /// </remarks>
    int getNonEmptyValueCount() { return getValues().getNonEmptyValueCount(); }

    /// <summary>
    /// Gets index of the last non-empty value in this collection.
    /// </summary>
    int getLastNonEmptyValueIndex() { return getValues().getLastNonEmptyValueIndex(); }

    /// <summary>
    /// Indicates whether the collection contains non-empty values.
    /// </summary>
    boolean hasNonEmptyValues() { return getValues().hasNonEmptyValues(); }

    /// <summary>
    /// Gets a format code used to convert data values to their string representation.
    /// </summary>
    String getFormatCode()
    {
        if (!StringUtil.hasChars(mFormatCode))
            mFormatCode = DmlChartFormatCodeValidator.validateFormatCode(mOriginalValues.getFormatCode());

        return mFormatCode;
    }

    /// <summary>
    /// Gets value type of items of this collection.
    /// </summary>
    /*DmlChartValueType*/int getValueType() { return getValues().getValueType(); }

    /// <summary>
    /// Returns <c>true</c> if values in the collection are dates.
    /// </summary>
    boolean isDate()
    {
        DmlChartValue val = getFirstNotNullValue();
        return (val != null) && val.isDate();
    }

    /// <summary>
    /// Indicates, whether the values were replaced by date.
    /// </summary>
    /// <remarks>
    /// When values were replaced by dates the data labels should use values with original indexes.
    /// </remarks>
    boolean isReplacedByDate() { return mIsReplacedByDate; }; void isReplacedByDate(boolean value) { mIsReplacedByDate = value; };

    private boolean mIsReplacedByDate;

    /// <summary>
    /// Gets actual data of this instance.
    /// </summary>
    private DmlChartValueCollection getValues() { return (mValues != null) ? mValues : mOriginalValues; }

    private String mFormatCode;
    private DmlChartValueCollection mOriginalValues;
    private DmlChartValueCollection mValues;
    private double[] mPercentages;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

