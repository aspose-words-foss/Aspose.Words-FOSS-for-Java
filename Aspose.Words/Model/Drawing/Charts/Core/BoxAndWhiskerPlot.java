// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/12/2019 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents data for an item of Box and Whisker chart.
/// </summary>
class BoxAndWhiskerPlot
{
    BoxAndWhiskerPlot(String category, double lowerExtreme, double lowerQuartile, double median,
        double upperQuartile, double upperExtreme, double mean, int lowerOutlierCount, int upperOutlierCount,
        double[] values)
    {
        setCategory(category);
        setLowerExtreme(lowerExtreme);
        setLowerQuartile(lowerQuartile);
        setMedian(median);
        setUpperQuartile(upperQuartile);
        setUpperExtreme(upperExtreme);
        setMean(mean);
        setLowerOutlierCount(lowerOutlierCount);
        setUpperOutlierCount(upperOutlierCount);
        setValues(values);
    }

    /// <summary>
    /// Bound of the lower whisker.
    /// </summary>
    double getLowerExtreme() { return mLowerExtreme; }; private void setLowerExtreme(double value) { mLowerExtreme = value; };

    private double mLowerExtreme;

    /// <summary>
    /// Position of the lower quartile.
    /// </summary>
    double getLowerQuartile() { return mLowerQuartile; }; private void setLowerQuartile(double value) { mLowerQuartile = value; };

    private double mLowerQuartile;

    /// <summary>
    /// Median of input numerical data.
    /// </summary>
    double getMedian() { return mMedian; }; private void setMedian(double value) { mMedian = value; };

    private double mMedian;

    /// <summary>
    /// Position of the upper quartile.
    /// </summary>
    double getUpperQuartile() { return mUpperQuartile; }; private void setUpperQuartile(double value) { mUpperQuartile = value; };

    private double mUpperQuartile;

    /// <summary>
    /// Bound of the upper whisker.
    /// </summary>
    double getUpperExtreme() { return mUpperExtreme; }; private void setUpperExtreme(double value) { mUpperExtreme = value; };

    private double mUpperExtreme;

    /// <summary>
    /// Arithmetical mean of input numerical data.
    /// </summary>
    double getMean() { return mMean; }; private void setMean(double value) { mMean = value; };

    private double mMean;

    /// <summary>
    /// Number of data values that have value less than <see cref="LowerExtreme"/>. This values are plotted as an
    /// outliers in the chart.
    /// </summary>
    int getLowerOutlierCount() { return mLowerOutlierCount; }; private void setLowerOutlierCount(int value) { mLowerOutlierCount = value; };

    private int mLowerOutlierCount;

    /// <summary>
    /// Number of data values that have value larger than <see cref="UpperExtreme"/>. This values are plotted as an
    /// outliers in the chart.
    /// </summary>
    int getUpperOutlierCount() { return mUpperOutlierCount; }; private void setUpperOutlierCount(int value) { mUpperOutlierCount = value; };

    private int mUpperOutlierCount;

    /// <summary>
    /// Category name of this plot.
    /// </summary>
    String getCategory() { return mCategory; }; private void setCategory(String value) { mCategory = value; };

    private String mCategory;

    /// <summary>
    /// Data values sorted in ascending order.
    /// </summary>
    double[] getValues() { return mValues; }; private void setValues(double[] value) { mValues = value; };

    private double[] mValues;
}

