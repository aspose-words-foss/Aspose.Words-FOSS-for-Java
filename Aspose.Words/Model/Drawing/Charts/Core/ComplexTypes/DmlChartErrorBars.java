// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;


/// <summary>
/// Represents 5.7.2.55 errBars (Error Bars) element that specifies error bars.
/// </summary>
class DmlChartErrorBars extends DmlExtensionListSource implements Cloneable
{
    /// <summary>
    /// Sets the parent series of this error bars. 
    /// Series is required to calculate error values in units of series values.
    /// </summary>
    void setSeries(ChartSeries series)
    {
        mSeries = series;
    }

    DmlChartErrorBars deepClone()
    {
        DmlChartErrorBars lhs = (DmlChartErrorBars)memberwiseClone();
        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        if (mPlusValues != null)
            lhs.mPlusValues = mPlusValues.deepClone();

        if (mMinusValues != null)
            lhs.mMinusValues = mMinusValues.deepClone();

        if (mAverageValue != null)
            lhs.mAverageValue = mAverageValue.deepClone();

        lhs.setExtensions(cloneExtensions());

        if (mMinus != null)
            lhs.mMinus = mMinus.deepClone();

        if (mPlus != null)
            lhs.mPlus = mPlus.deepClone();

        return lhs;
    }

    private void calculateErrorValues()
    {
        // Depending on the error bar direction, use collection of X or Y values as a source collection
        // for calculating absolute error values in units of the corresponding series values.
        DmlChartDimensionData sourceCollection = getChartDimensionData();

        // Stacked maximums are used to calculate errors for percent staked charts.
        double[] stackedMaximums = DmlChartRenderingUtil.getStackedMaximums(mSeries.getChart());

        mPlusValues = createErrorChartDimensionData(sourceCollection);
        mMinusValues = createErrorChartDimensionData(sourceCollection);

        double error = getFixedError(sourceCollection);

        // Calculate values of plus and minus errors.
        // WORDSNET-22213 Error bars should be rendered only for non-empty points.
        for (int i = 0; i <= mSeries.getLastNonEmptyValueIndex(); i++)
        {
            DmlChartValue sourceValue = sourceCollection.getValue(i);

            // No error bar should be applied to null value, so simply do nothing.
            if (sourceValue == null)
                continue;

            double plusAbsoluteValue = sourceValue.getValue();
            double minusAbsoluteValue = sourceValue.getValue();

            switch (getErrValType())
            {
                case ErrorValueType.PERCENTAGE:
                {
                    // It seems, MS Word always uses original value (not staked) for calculating percent error.
                    // So do the same.
                    error = calculatePercentageError(sourceCollection.getData().getOriginalValue(i), (float)getVal());
                    double stackedError = getStackedErrorValue(error, stackedMaximums[i]);
                    plusAbsoluteValue += stackedError;
                    minusAbsoluteValue -= stackedError;
                    break;
                }
                case ErrorValueType.CUSTOM_ERROR_BARS:
                {
                    DmlChartValue plusError = getPlus().getValue(sourceValue.getIndex());
                    DmlChartValue minusError = getMinus().getValue(sourceValue.getIndex());

                    double plusErrorValue = (plusError == null) ? 0.0d : plusError.getValue();
                    double minusErrorValue = (minusError == null) ? 0.0d : minusError.getValue();
                    plusAbsoluteValue += getStackedErrorValue(plusErrorValue, stackedMaximums[i]);
                    minusAbsoluteValue -= getStackedErrorValue(minusErrorValue, stackedMaximums[i]);
                    break;
                }
                case ErrorValueType.STANDARD_DEVIATION:
                {
                    plusAbsoluteValue = getAverageValue().getValue() + error;
                    minusAbsoluteValue = getAverageValue().getValue() - error;
                    break;
                }
                case ErrorValueType.FIXED_VALUE:
                {
                    double stackedError = getStackedErrorValue(error, stackedMaximums[i]);
                    plusAbsoluteValue += stackedError;
                    minusAbsoluteValue -= stackedError;
                    break;
                }
                case ErrorValueType.STANDARD_ERROR:
                default:
                {
                    plusAbsoluteValue += error;
                    minusAbsoluteValue -= error;
                    break;
                }
            }

            mPlusValues.getData().addValue(new DmlChartNumValue(sourceValue.getIndex(), plusAbsoluteValue));
            mMinusValues.getData().addValue(new DmlChartNumValue(sourceValue.getIndex(), minusAbsoluteValue));
        }
    }

    /// <summary>
    /// Gets the <see cref="DmlChartDimensionData"/> based on error bar direction.
    /// </summary>
    /// <returns><see cref="DmlChartDimensionData"/></returns>
    private DmlChartDimensionData getChartDimensionData()
    {
        return (getErrDir() == ErrorBarDirection.X)
            ? getXValuesSource()
            : mSeries.getY();
    }

    /// <summary>
    /// Gets the <see cref="DmlChartDimensionData"/> if value of the error bar direction is "X".
    /// </summary>
    /// <returns><see cref="DmlChartDimensionData"/></returns>
    private DmlChartDimensionData getXValuesSource()
    {
        return mSeries.getX().isEmpty()
            // WORDSNET-21470 If XValues is empty, MS Words uses the source with "dummy" values.
            ? DmlChartDimensionData.getDummyDimensionData(mSeries.getLastNonEmptyValueIndex() + 1)
            : mSeries.getX();
    }

    /// <summary>
    /// Gets the error value.
    /// </summary>
    /// <param name="error">The specified error</param>
    /// <param name="stackedMaximum">The specified stacked maximum</param>
    /// <returns>The calculated error</returns>
    private double getStackedErrorValue(double error, double stackedMaximum)
    {
        return isPercentStacked() ? (error / stackedMaximum) : error;
    }

    /// <summary>
    /// Calculates fixed error value, i.e. common error value for all data points.
    /// If error value depends on data point value, returns zero.
    /// </summary>
    private double getFixedError(DmlChartDimensionData sourceCollection)
    {
        double error = 0.0;
        switch (getErrValType())
        {
            case ErrorValueType.STANDARD_DEVIATION:
                error = calculateStandardDeviation(sourceCollection, getAverageValue(), getVal());
                break;
            case ErrorValueType.STANDARD_ERROR:
                error = calculateStandardError(sourceCollection, getAverageValue(), getVal());
                break;
            case ErrorValueType.FIXED_VALUE:
                error = (float)getVal();
                break;
            case ErrorValueType.PERCENTAGE:
            case ErrorValueType.CUSTOM_ERROR_BARS:
            default:
                // Percentage and custom errors are specific for each concrete point, so they will be calculated later.
                break;
        }
        return error;
    }

    /// <summary>
    /// Calculates average value of the given data source.
    /// </summary>
    private DmlChartValue calculateAverageValue(DmlChartDimensionData sourceCollection)
    {
        double sum = 0.0;
        int notNullAndNotNanValuesCount = 0;

        for (int i = 0; i < sourceCollection.getData().getValueCount(); i++)
        {
            DmlChartValue value = sourceCollection.getValue(i);
            DmlChartValue x = mSeries.getX().getValue(i);
            DmlChartValue y = mSeries.getY().getValue(i);
            
            //Calculate number of valid values.
            if (!DmlChartValue.isNullOrNaN(x) && !DmlChartValue.isNullOrNaN(y))
                notNullAndNotNanValuesCount++;

            if (!DmlChartValue.isNullOrNaN(value))
                sum += value.getValue();
        }

        return new DmlChartNumValue(0, (notNullAndNotNanValuesCount == 0) ? 0.0 : (sum / notNullAndNotNanValuesCount), 
            sourceCollection.getData().getFormatCode());
    }

    /// <summary>
    /// Calculates standard deviation.
    /// http://en.wikipedia.org/wiki/Standard_deviation
    /// </summary>
    private static double calculateStandardDeviation(
        DmlChartDimensionData sourceCollection, DmlChartValue avgValue, double value)
    {
        // Calculate sum of the population standard deviation, 
        // first compute the difference of each data point from the mean (avgPoint), 
        // and square the result of each, then sum.
        int n = sourceCollection.getData().getValueCount();
        double population = 0.0;
        for (int i = 0; i < n; i++)
        {
            DmlChartValue currentValue = sourceCollection.getValue(i);
            if (!DmlChartValue.isNullOrNaN(currentValue))
                population += (float)Math.pow((currentValue.getValue() - avgValue.getValue()), 2.0);
        }

        // Calculate a standard deviation as a sqrt of avg value of population.
        double deviation = Math.sqrt(population / n);
        deviation *= (MathUtil.isZero(value)) ? 1.0 : value;
        return deviation;
    }

    /// <summary>
    /// Calculates standard error.
    /// http://en.wikipedia.org/wiki/Standard_error
    /// </summary>
    private static double calculateStandardError(DmlChartDimensionData sourceCollection, DmlChartValue avgValue,
        double value)
    {
        int n = sourceCollection.getData().getValueCount();
        double deviation = calculateStandardDeviation(sourceCollection, avgValue, value);
        return deviation / Math.sqrt(n);
    }

    /// <summary>
    /// Calculates percentage error.
    /// </summary>
    private static double calculatePercentageError(DmlChartValue value, float errValue)
    {
        return (DmlChartValue.isNullOrNaN(value)) ? 0.0d : (value.getValue() * errValue / 100.0);
    }

    /// <summary>
    /// Creates an empty copy of source.
    /// </summary>
    private static DmlChartDimensionData createErrorChartDimensionData(DmlChartDimensionData source)
    {
        DmlChartDataSource errorSource = new DmlChartDataSource();
        errorSource.setValues(new DmlChartValueCollection(DmlChartValueType.NUMERIC));
        errorSource.getValues().setValueCount(source.getData().getValueCount());
        errorSource.getValues().setFormatCode(source.getData().getFormatCode());

        return new DmlChartDimensionData(errorSource);
    }

    /*ErrorBarType*/int getErrBarType() { return mErrBarType; }
    void setErrBarType(/*ErrorBarType*/int value) { mErrBarType = value; }

    /// <summary>
    /// By default returns Y.
    /// </summary>
    /*ErrorBarDirection*/int getErrDir() { return mErrDir; }
    void setErrDir(/*ErrorBarDirection*/int value) { mErrDir = value; }

    /*ErrorValueType*/int getErrValType() { return mErrValType; }
    void setErrValType(/*ErrorValueType*/int value) { mErrValType = value; }

    DmlChartDimensionData getMinus()
    {
        if (mMinus == null)
            mMinus = new DmlChartDimensionData(new DmlChartDataSource());

        return mMinus;
    }

    DmlChartDimensionData getPlus()
    {
        if (mPlus == null)
            mPlus = new DmlChartDimensionData(new DmlChartDataSource());
     
        return mPlus;
    }

    boolean getNoEndCap() { return mNoEndCap; }
    void setNoEndCap(boolean value) { mNoEndCap = value; }

    DmlChartSpPr getSpPr()
    {
        if (mSpPr == null)
            mSpPr = new DmlChartSpPr();

        return mSpPr;
    }

    double getVal() { return mVal; }
    void setVal(double value) { mVal = value; }

    /// <summary>
    /// Returns absolute values of positive errors.
    /// Values are calculated.
    /// </summary>
    DmlChartDimensionData getPlusValues()
    {
        if (mPlusValues == null)
            calculateErrorValues();

        return mPlusValues;
    }

    /// <summary>
    /// Returns absolute values of negative errors.
    /// Values are calculated.
    /// </summary>
    DmlChartDimensionData getMinusValues()
    {
        if (mMinusValues == null)
            calculateErrorValues();

        return mMinusValues;
    }

    /// <summary>
    /// Returns average value of the source values.
    /// </summary>
    DmlChartValue getAverageValue()
    {
        if (mAverageValue == null)
            mAverageValue = calculateAverageValue((getErrDir() == ErrorBarDirection.X) ? mSeries.getX() : mSeries.getY());

        return mAverageValue;
    }

    /// <summary>
    /// Returns true if plus error bar should be rendered.
    /// </summary>
    boolean getRenderPlus() { return ((getErrBarType() == ErrorBarType.BOTH) || (getErrBarType() == ErrorBarType.PLUS)); }

    /// <summary>
    /// Returns true if minus error bar should be rendered.
    /// </summary>
    boolean getRenderMinus() { return ((getErrBarType() == ErrorBarType.BOTH) || (getErrBarType() == ErrorBarType.MINUS)); }

    /// <summary>
    /// Returns true if error bars value type is standard deviation. 
    /// In this case error bar must start at the average point.
    /// </summary>
    boolean isStandardDeviation() { return (getErrValType() == ErrorValueType.STANDARD_DEVIATION); }

    /// <summary>
    /// Gets the series to which these bars belongs.
    /// </summary>
    /// <remarks>Property exposed for test purposes.</remarks>
    ChartSeries getSeries() { return mSeries; }

    /// <summary>
    /// Returns true if parent chart is percent stacked.
    /// </summary>
    private boolean isPercentStacked() { return (mSeries.getChart().getChartPr().getGrouping() == Grouping.PERCENT_STACKED); }

    private /*ErrorBarType*/int mErrBarType;
    private /*ErrorBarDirection*/int mErrDir = ErrorBarDirection.Y;
    private /*ErrorValueType*/int mErrValType;
    private DmlChartDimensionData mMinus;
    private DmlChartDimensionData mPlus;
    private boolean mNoEndCap;
    private DmlChartSpPr mSpPr;
    private double mVal;

    private ChartSeries mSeries;
    private DmlChartDimensionData mMinusValues;
    private DmlChartDimensionData mPlusValues;
    private DmlChartValue mAverageValue;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

