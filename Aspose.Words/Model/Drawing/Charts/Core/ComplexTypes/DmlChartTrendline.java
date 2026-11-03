// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.MathUtil;
import com.aspose.PointFList;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ms.System.msArray;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents 5.7.2.212 trendline (Trendlines) element.
/// This element specifies a trendline.
/// </summary>
class DmlChartTrendline extends DmlExtensionListSource implements Cloneable
{
   
    DmlChartTrendline deepClone()
    {
        // It is not required to clone x and y values and points, they all are filled on demand using series values.
        DmlChartTrendline lhs = (DmlChartTrendline)memberwiseClone();

        lhs.mTrendlinePr = mTrendlinePr.deepClone();

        lhs.mXValues = null;
        lhs.mYValues = null;
        lhs.mPoints = null;

        return lhs;
    }

    /// <summary>
    /// Sets the series the trendline belongs to.
    /// </summary>
    void setSeries(ChartSeries series)
    {
        mSeries = series;
    }

    /// <summary>
    /// Method calculates <see cref="XValues"/> and <see cref="YValues"/>.
    /// </summary>
    private void prepare()
    {
        prepareXValues();

        switch (getTrendlineType())
        {
            case TrendlineType.EXPONENTIAL:
                prepareExponential();
                break;
            case TrendlineType.LOGARITHMIC:
                prepareLogarithmic();
                break;
            case TrendlineType.MOVING_AVERAGE:
                prepareMovingAverage();
                break;
            case TrendlineType.LINEAR:
            case TrendlineType.POLYNOMIAL:
                preparePolynomial();
                break;
            case TrendlineType.POWER:
                preparePower();
                break;
            default:
                throw new IllegalArgumentException(UNEXPECTED_TRENDLINE_TYPE_MESSAGE);
        }
    }

    /// <summary>
    /// Checks if x-values are the same.
    /// </summary>
    /// <remarks>
    /// WORDSNET-19634 It makes no sense to create a trend line if all X-values are the same.
    /// </remarks>
    /// <returns>"True" if float value of all X-values is the same, otherwise "false"</returns>
    private boolean areXValuesSame()
    {
        DmlChartValue prev = null;

        for (DmlChartValue value : mSeries.getOrderedXValuesArray())
        {
            if ((prev != null) && !MathUtil.areEqual(value.getValue(), prev.getValue()))
                return false;

            prev = value;
        }

        return true;
    }

    /// <summary>
    /// Method fills <see cref="XValues"/> with x values taking forecast in account.
    /// </summary>
    private void prepareXValues()
    {
        if (mXValues != null)
            return;

        mXValues = new DmlChartDimensionData(new DmlChartDataSource());
        mXValues.setValues(new DmlChartValueCollection(getXValueType()));

        if (getXValueType() == DmlChartValueType.STRING || getXValueType() == DmlChartValueType.MULTI_LVL_STRING)
            prepareStringXValues();
        else if (mSeries.getX().isDate())
            prepareDateXValues();
        else
            prepareNumXValues();
    }

    /// <summary>
    /// Prepare x-values for numeric and date trendlines and returns the index of the last value.
    /// </summary>
    private int prepareXValuesGetLastIndex(int startIndex)
    {
        int index = startIndex;
        String formatCode = mSeries.getX().getData().getFormatCode();

        for (int i = 0; i < getOrderedSourceXValues().length; i++)
        {
            DmlChartValue x = getOrderedSourceXValues()[i];

            if (DmlChartValue.isNullOrNaN(x))
                continue;

            DmlChartNumValue srcNum = ms.as(getOrderedSourceXValues()[i], DmlChartNumValue.class);
            String actualFormatCode = (srcNum == null) ? formatCode : srcNum.getFormatCode();
            mXValues.getData().addValue(new DmlChartNumValue(index, x.getValue(), actualFormatCode));
            index++;
        }

        return index;
    }

    /// <summary>
    /// Method fills <see cref="XValues"/> with numeric values.
    /// </summary>
    private void prepareNumXValues()
    {
        // First calculate minimum and maximum series x values. 
        setXValuesExtremums();

        // Calculate approximate step between x values.
        double step = (mMaxValueX - mMinValueX) / mSeries.getValueCount();

        // Subtract backward from actual minimum and add forward to actual maximum.
        // This way we get forecast minimum and maximum values.
        double forecastMin = mMinValueX - getBackward();
        double forecastMax = mMaxValueX + getForward();

        // If trendline is Power or Logarithmic, minimum value cannot be less than zero.
        // If such situation occurs, add minimum absolute value to maximum value and reset minimum to zero.
        // It seems MS Word does the same.
        if ((getTrendlineType() == TrendlineType.POWER || getTrendlineType() == TrendlineType.LOGARITHMIC) && (forecastMin < 0))
        {
            forecastMax += Math.abs(forecastMin);
            forecastMin = 0.0;
        }

        // Set start index to zero and current value to forecast minimum.
        int index = 0;
        double current = forecastMin;

        // Add backward forecast values if required.
        // WORDSNET-13836 If X step is zero (vertical line in scatter chart), 
        // it is impossible to process Forward and Backward. So simply ignore them.
        if (!MathUtil.isZero(step))
        {
            while (current < mMinValueX)
            {
                mXValues.getData().addValue(new DmlChartDummyValue(index, (float)current));
                index++;
                current += step;
            }
        }

        index = prepareXValuesGetLastIndex(index);

        // Add forward forecast values if required.
        // WORDSNET-13836 If X step is zero (vertical line in scatter chart), 
        // it is impossible to process Forward and Backward. So simply ignore them.
        if (!MathUtil.isZero(step))
        {
            current = mMaxValueX;
            while ((current < forecastMax) || (!MathUtil.isZero(getForward()) && MathUtil.areEqual(current, forecastMax)))
            {
                mXValues.getData().addValue(new DmlChartDummyValue(index, (float)current));
                index++;
                current += step;
            }
        }

        mXValues.getData().setValueCount(index);
    }

    /// <summary>
    /// Sets the maximum and minimum of the X-values.
    /// </summary>
    private void setXValuesExtremums()
    {
        for (int i = 0; i < mSeries.getValueCount(); i++)
        {
            DmlChartValue x = mSeries.getX().getValue(i);

            if (DmlChartValue.isNullOrNaN(x))
                continue;

            mMinValueX = Math.min(mMinValueX, x.getValue());
            mMaxValueX = Math.max(mMaxValueX, x.getValue());
        }
    }

    /// <summary>
    /// Method fills <see cref="XValues"/> with numeric values, which represent dates.
    /// </summary>
    private void prepareDateXValues()
    {
        if (!mSeries.getX().isDate())
            throw new IllegalArgumentException("Date values are expected.");

        setXValuesExtremums();
        // WORDSNET-21968 Use the number of non-empty points.
        int index = prepareXValuesGetLastIndex(0);
        String formatCode = mSeries.getX().getData().getFormatCode();

        // Only forward is taken in account, MS Word does not allow to specify backward for dates.
        mXValues.getData().setValueCount((index + MathUtil.doubleToInt(getForward())));

        double step = (mMaxValueX - mMinValueX) / index;

        for (int i = index; i < mXValues.getData().getValueCount(); i++)
        {
            mMaxValueX += step;
            mXValues.getData().addValue(new DmlChartNumValue(i, mMaxValueX, formatCode));
        }
    }

    /// <summary>
    /// Method fills <see cref="XValues"/> with string values.
    /// </summary>
    private void prepareStringXValues()
    {
        if(getXValueType() != DmlChartValueType.STRING && getXValueType() != DmlChartValueType.MULTI_LVL_STRING)
            throw new IllegalArgumentException("String value type is expected.");

        // Copy original values into x values collection.
        for (int i = 0; i < mSeries.getValueCount(); i++)
            mXValues.getData().addValue(mSeries.getX().getValue(i).deepClone());

        // Increase number of points if forecast is specified.
        // In this case if there is no value for specified index, data source will return dummy value.
        // Backward is not taken in account here (Ms Word does not allow to specify backward for string values axis).
        mXValues.getData().setValueCount(mSeries.getValueCount() + MathUtil.doubleToInt(getForward()));
    }
    
    /// <summary>
    /// Gets points that are used in trendline calculation.
    /// </summary>
    private void preparePoints()
    {
        PointFList points = new PointFList();
        float prevX = Float.NaN;
        float prevY = Float.NaN;
        int countIdenticalX = 1;

        for (int i = 0; i < getOrderedSourceXValues().length; i++)
        {
            // In case of date X, we use index as a value. It seems MS Word does the same.
            // This will allow us to get correct equation of trendline.
            DmlChartValue x = getOrderedSourceXValues()[i];

            if (DmlChartValue.isNullOrNaN(x))
                continue;

            DmlChartValue y = mSeries.getY().getValue(x.getIndex());

            if (DmlChartValue.isNullOrNaN(y))
                continue;

            float xValue = getXValues().isDate() ? (i + 1) : x.getFloatValue();
            float yValue = y.getFloatValue();
            /*PointF*/long point = msPointF.Empty;

            switch (getTrendlineType())
            {
                case TrendlineType.EXPONENTIAL:
                    // We linearize y=a*e^(b*x) equation and get ln(y)=ln(a) + b*x equation.
                    // Then find ln(a) and b coefficients the same way as we do for linear trendline.
                    point = msPointF.ctor(xValue, (float)Math.log(yValue));
                    break;
                case TrendlineType.LOGARITHMIC:
                    // We can consider equation y=a*ln(x)+b as linear function y=f(ln(x)).
                    point = msPointF.ctor((float)Math.log(xValue), yValue);
                    break;
                case TrendlineType.LINEAR:
                case TrendlineType.POLYNOMIAL:
                case TrendlineType.MOVING_AVERAGE:
                    if (prevX == xValue)
                    {
                        prevY += yValue;
                        countIdenticalX++;
                    }
                    else
                    {
                        if (countIdenticalX > 1)
                            setAverageYForIdenticalXLinear(points, prevX, (prevY / countIdenticalX));

                        countIdenticalX = 1;
                        prevY = yValue;
                    }

                    point = msPointF.ctor(xValue, yValue);
                    break;
                case TrendlineType.POWER:
                    // Power trendline is represented by y=a*x^b equation. It can be linearized as ln(y)=ln(a)+b*ln(x)
                    point = msPointF.ctor((float)Math.log(xValue), (float)Math.log(yValue));
                    break;
                default:
                    throw new IllegalArgumentException(UNEXPECTED_TRENDLINE_TYPE_MESSAGE);
            }

            prevX = xValue;
            points.add(point);
        }

        mPreparedPoints = points;
    }

    /// <summary>
    /// Sets average y-values for identical x-values.
    /// </summary>
    /// <remarks>
    /// WORDSNET-24626 If series contains several identical x-values MS Word uses average y-value for all such x.
    /// </remarks>
    private static void setAverageYForIdenticalXLinear(PointFList points, float x, float averageY)
    {
        for (int i = 0; i < points.getCount(); i++)
            if (MathUtil.areEqual(msPointF.getX(points.get(i)), x))
                 points.set(i, msPointF.ctor(x, averageY));
    }

    /// <summary>
    /// Calculates trendline's control points and <see cref="YValues"/> of Polynomial or Linear trendline.
    /// </summary>
    private void preparePolynomial()
    {
        int order = getOrder() < getPreparedPoints().getCount() ? getOrder() : Math.max(2, getPreparedPoints().getCount() - 1);

        // Order of linear trendline is 1.
        if (getTrendlineType() == TrendlineType.LINEAR)
            order = 1;

        mCoefficients = calculateCoeff(getPreparedPoints(), order + 1, getIntercept());
        prepareTrendlineCore(order);
    }

    /// <summary>
    /// Calculates trendline's control points and <see cref="YValues"/> of Power trendline.
    /// </summary>
    private void preparePower()
    {
        prepareLogarithmic();
    }

    /// <summary>
    /// Calculates trendline's control points and <see cref="YValues"/> of Logarithmic trendline.
    /// </summary>
    private void prepareLogarithmic()
    {
        mCoefficients = calculateCoeff(getPreparedPoints(), 2);
        prepareTrendlineCore(0);
    }

    /// <summary>
    /// Calculates trendline's control points and <see cref="YValues"/> of Exponential trendline.
    /// </summary>
    private void prepareExponential()
    {
        mCoefficients = emptyMatrix(2, 1);

        // If intercept is set to zero, we do not calculate coefficients, because they should be set to zero.
        // We process this situation later in PrepareTrendlineCore method.
        if (!MathUtil.isZero(getIntercept()))
        {
            // Use logarithm of intercept for further calculation.
            double intercept = Double.isNaN(getIntercept()) ? Double.NaN : Math.log(getIntercept());
            mCoefficients = calculateCoeff(getPreparedPoints(), 2, intercept);
        }

        prepareTrendlineCore(0);
    }

    /// <summary>
    /// Prepares Moving Average trendline. Since this type of trendline does not support forecast and
    /// its values cannot exit real series value limits, we do nothing here except setting actual series values as 
    /// trendline's X and Y values. 
    /// </summary>
    private void prepareMovingAverage()
    {
        // WORDSNET-13140 Do not reset calculated X values with values from series.
        // Values from series might be empty, that causes the exception.
        mYValues = mSeries.getY();
        mPoints = getPreparedPoints();
    }

    /// <summary>
    /// Core method for calculation trendline's control points and <see cref="YValues"/>.
    /// </summary>
    private void prepareTrendlineCore(int order)
    {
        // Lets take one more X point between known X points.
        int pointCount = mXValues.getData().getValueCount() * 2 - 1;

        // Init y values source. These values will be used for adjusting axis.
        mYValues = new DmlChartDimensionData(new DmlChartDataSource());
        mYValues.setValues(new DmlChartValueCollection(DmlChartValueType.NUMERIC));
        mYValues.getValues().setValueCount(pointCount);

        boolean isDate = mXValues.isDate();
        mPoints = new PointFList(pointCount);

        // WORDSNET-17004 The values of the trend line can be unsorted. But for rendering of the trend line, the values 
        // should be sorted in ascending order.
        DmlChartValue[] sortedXvalues = mXValues.getData().toArray();
        msArray.sort(sortedXvalues);

        for (int i = 0; i < sortedXvalues.length; i++)
        {
            DmlChartValue x1 = sortedXvalues[i];
   
            if (DmlChartValue.isNullOrNaN(x1))
                continue;
            
            // If value is date, we use index instead of actual float value.
            float x = isDate ? (i + 1) : x1.getFloatValue();
            addPoint(x, mCoefficients, order);

            if (i < sortedXvalues.length - 1)
            {
                DmlChartValue x2 = sortedXvalues[i + 1];
                // If value is date, we use index instead of actual float value.
                x = isDate ? (i + 3.0f / 2.0f) : (x1.getFloatValue() + x2.getFloatValue()) / 2f;
                addPoint(x, mCoefficients, order);
            }
        }
    }

    private void addPoint(float x, double[][] coeffs, int order)
    {
        float y = calculateYValue(x, coeffs, order);
        mYValues.getData().addValue(new DmlChartNumValue(mPoints.getCount(), y));
        mPoints.add(msPointF.ctor(x, y));
    }

    /// <summary>
    /// Method calculates Y value by given X, coefficients and order values.
    /// </summary>
    private float calculateYValue(float x, double[][] coeffs, int order)
    {
        switch (getTrendlineType())
        {
            case TrendlineType.LINEAR:
            case TrendlineType.POLYNOMIAL:
            {
                float y = 0f;
                for (int degree = 0; degree < order + 1; degree++)
                    y += (float)(coeffs[degree][0] * Math.pow(x, degree));
                return y;
            }
            case TrendlineType.LOGARITHMIC:
            {
                double a = coeffs[1][0];
                double b = coeffs[0][0];
                // If X is zero Log(0)->-Infinity, we cannot use this value for further calculation.
                // 0.9b is an experimental value, which gives the result close to MS.
                return MathUtil.isZero(x) ? (float)b * 0.9f : (float)(a * Math.log(x) + b);
            }
            case TrendlineType.EXPONENTIAL:
            {
                // If intercept is set to zero, set both coefficients to zero,
                // because we cannot get logarithm of such intercept for further calculation. MS Word does the same.
                double a = MathUtil.isZero(getIntercept()) ? 0.0f : Math.exp(coeffs[0][0]);
                double b = MathUtil.isZero(getIntercept()) ? 0.0f : coeffs[1][0];

                return (float)(a * Math.exp(b * x));
            }
            case TrendlineType.POWER:
            {
                double a = Math.exp(coeffs[0][0]);
                double b = coeffs[1][0];
                // If X is zero, x^b = 0, but MS seems to use 1.2a value. so do the same.
                return MathUtil.isZero(x) ? (float)a * 1.2f : (float)(a * Math.pow(x, b));
            }
            default:
                throw new IllegalArgumentException(UNEXPECTED_TRENDLINE_TYPE_MESSAGE);
        }
    }

    /// <summary>
    /// Calculates coefficients for polynomial trendline of the specified degree taking intercept value into account.
    /// Intercept value should be taken into account only upon rendering Linear, Polynomial or Exponential trendlines.
    /// For other trendlines use <see cref="CalculateCoeff(PointFList, int)"/>
    /// </summary>
    private static double[][] calculateCoeff(PointFList points, int degree, double intercept)
    {
        double[][] coeffs = calculateCoeff(points, degree);

        if (Double.isNaN(intercept))
            return coeffs;

        double b = coeffs[0][0];

        // We cannot calculate coefficients with 100% precision. Use 0.001% of y values range as tolerance.
        double range = calculateRangeY(points);
        double precision = 1e+5d;
        double tolerance = calculateRangeY(points) / precision;

        // WORDSNET-24353 Provide the epsilon as minimum precision (if series is a horizontal line - delta y is zero).
        tolerance = Math.max(Double.MIN_VALUE, tolerance);

        // Add intercept point.
        points.add(msPointF.ctor(0f, (float)intercept));
        int iterationsCount = 0;

        // WORDSNET-26954 In some cases the coefficients do not change. To avoid hanging set the maximum number of
        // iterations.
        while (!MathUtil.areEqual(b, intercept, tolerance) && (iterationsCount <= precision))
        {
            // Do not include intercept point.
            for (int i = 0; i < (points.getCount() - 1); i++)
            {
                float x = msPointF.getX(points.get(i));
                float y = 0f;

                for (int d = 0; d < degree; d++)
                    y += (float)(coeffs[d][0] * Math.pow(x, d));

                points.set(i, msPointF.ctor(x, y));
            }

            // Use trendline points plus (0, intercept) point for the next iteration.
            coeffs = calculateCoeff(points, degree);
            b = coeffs[0][0];
            iterationsCount++;
        }

        // Reset first coefficient to intercept value. This is not required actually, 
        // but this will make trendline's formula closer to MS.
        coeffs[0][0] = intercept;

        return coeffs;
    }

    /// <summary>
    /// Calculates y range.
    /// </summary>
    private static double calculateRangeY(PointFList points)
    {
        double maxY = 0.0;
        double minY = 0.0;
        for (int i = 0; i < points.getCount(); i++)
        {
            /*PointF*/long point = points.get(i);
            maxY = Math.max(msPointF.getY(point), maxY);
            minY = Math.min(msPointF.getY(point), minY);
        }

        double deltaY = maxY - minY;
        return deltaY;
    }

    /// <summary>
    /// Calculates coefficients for polynomial trendline of the specified degree.
    /// Uses algorithm described here http://charlway.blogspot.com/2012/08/polynomial-regression-in-python.html.
    /// </summary>
    private static double[][] calculateCoeff(PointFList points, int degree)
    {
        double[][] x = makeXmatrix(points, degree);
        double[][] y = makeYmatrix(points);
        double[][] xt = transposeMatrix(x); //Transpose X Matrix
        double[][] xtx = multiplyMatrices(xt, x); //Multiply X' by X
        double[][] xtInvert = gausJ(xtx); //Invert
        double[][] xty = multiplyMatrices(xt, y); //Times together to get X' x Y
        double[][] coeffMatrix = multiplyMatrices(xtInvert, xty); //Here we get the coefficients of the polynomial
        return coeffMatrix;
    }

    /// <summary>
    /// Fill up the Xmatrix
    /// </summary>
    private static double[][] makeXmatrix(PointFList points, int degree)
    {
        double[][] xMatrix = emptyMatrix(points.getCount(), degree);
        for (int row = 0; row < points.getCount(); row++)
        {
            // The new Xmatrix elements are the X point to the power of the column value.
            // powers = 0,1,2, ... ,amount of x points - 1
            for (int col = 0; col < degree; col++)
                xMatrix[row][col] = Math.pow(msPointF.getX(points.get(row)), col);
        }

        return xMatrix;
    }

    /// <summary>
    /// Making the Y column matrix
    /// </summary>
    private static double[][] makeYmatrix(PointFList points)
    {
        double[][] yMatrix = emptyMatrix(points.getCount(), 1);
        for (int row = 0; row < points.getCount(); row++)
            yMatrix[row][0] = msPointF.getY(points.get(row));

        return yMatrix;
    }

    /// <summary>
    /// Create a matrix of zeros This is Rows by Columns
    /// </summary>
    private static double[][] emptyMatrix(int m, int n)
    {
        double[][] array = new double[m][];
        for (int i = 0; i < m; i++)
            array[i] = new double[n];

        return array;
    }

    /// <summary>
    /// Creates a square identity matrix of size m x m
    /// </summary>
    private static double[][] identityMatrix(int m)
    {
        int n = m;
        double[][] result = emptyMatrix(m, n);

        for (int i = 0; i < m; i++)
            result[i][i] = 1.0;

        return result;
    }

    /// <summary>
    /// Transposes source matrix.
    /// </summary>
    private static double[][] transposeMatrix(double[][] sourceMatrix)
    {
        double[][] result = emptyMatrix(sourceMatrix[0].length, sourceMatrix.length);

        for (int i = 0; i < sourceMatrix.length; i++)
        {
            for (int j = 0; j < sourceMatrix[0].length; j++)
                result[j][i] = sourceMatrix[i][j];
        }

        return result;
    }

    /// <summary>
    /// Multiplies matrices.
    /// </summary>
    private static double[][] multiplyMatrices(double[][] a, double[][] b)
    {
        double[][] result = emptyMatrix(a.length, b[0].length);

        for (int i = 0; i < result.length; i++)
            for (int j = 0; j < result[0].length; j++)
                for (int k = 0; k < b.length; k++)
                    result[i][j] += a[i][k] * b[k][j];

        return result;
    }

    private static double[][] gausJ(double[][] matrix)
    {
        // Build Identity matrix to do row operations on, this will become inverted matrix 
        double[][] result = identityMatrix(matrix.length);

        for (int r = 0; r < matrix.length; r++)
        {
            // Divide each element on the diagonal by itself, 
            // making the new diagonal elements all = 1, so we never divide by 0.
            double divider = 0.0;

            // While our divider is 0, cant divide by 0.
            while (MathUtil.isZero(divider))
            {
                // Divider is now equals to element r,r in original matrix 
                divider = matrix[r][r];
                // In case the element r is 0, so we don't divide by 0
                if (MathUtil.isZero(divider))
                {
                    for (int t = 0; t < matrix.length; t++)
                    {
                        if (MathUtil.isZero(matrix[r][r]))
                        {
                            // We add rows till we don't have 0 elements
                            for (int elem = 0; elem < matrix.length; elem++)
                                matrix[r][elem] = matrix[r][elem] + matrix[t][elem];
                        }
                    }
                }
            }

            for (int n = 0; n < matrix.length; n++)
            {
                // Divide element by divider
                matrix[r][n] = (matrix[r][n]) / divider;
                // Do the same for the identity matrix that's getting transformed, output matrix
                result[r][n] = (result[r][n]) / divider;
            }

            for (int row = 0; row < matrix.length; row++)
            {
                if (row != r)
                {
                    double multi = matrix[row][r] / matrix[r][r];
                    for (int col = 0; col < matrix[0].length; col++)
                    {
                        // Subtract columns
                        matrix[row][col] = matrix[row][col] - (multi * matrix[r][col]);
                        //Subtract columns for new matrix as well
                        result[row][col] = result[row][col] - (multi * result[r][col]);
                    }
                }
            }
        }

        return result;
    }

    public /*override*/ StringToObjDictionary<DmlExtension> getExtensions() { return (StringToObjDictionary<DmlExtension>)mTrendlinePr.getProperty(DmlChartTrendlineAttr.EXTENSIONS); }
    public /*override*/ void setExtensions(StringToObjDictionary<DmlExtension> value) { mTrendlinePr.setProperty(DmlChartTrendlineAttr.EXTENSIONS, value); }

    double getBackward() { return (/*double*/Double)mTrendlinePr.getProperty(DmlChartTrendlineAttr.BACKWARD); }
    void setBackward(double value) { mTrendlinePr.setProperty(DmlChartTrendlineAttr.BACKWARD, value); }

    boolean getDispEq() { return (/*boolean*/Boolean)mTrendlinePr.getProperty(DmlChartTrendlineAttr.DISP_EQ); }
    void setDispEq(boolean value) { mTrendlinePr.setProperty(DmlChartTrendlineAttr.DISP_EQ, value); }

    boolean getDispRSqr() { return (/*boolean*/Boolean)mTrendlinePr.getProperty(DmlChartTrendlineAttr.DISP_R_SQR); }
    void setDispRSqr(boolean value) { mTrendlinePr.setProperty(DmlChartTrendlineAttr.DISP_R_SQR, value); }

    double getForward() { return (/*double*/Double)mTrendlinePr.getProperty(DmlChartTrendlineAttr.FORWARD); }
    void setForward(double value) { mTrendlinePr.setProperty(DmlChartTrendlineAttr.FORWARD, value); }

    /// <summary>
    /// Returns NaN if intercept is not set.
    /// </summary>
    double getIntercept() { return (/*double*/Double)mTrendlinePr.getProperty(DmlChartTrendlineAttr.INTERCEPT); }
    void setIntercept(double value) { mTrendlinePr.setProperty(DmlChartTrendlineAttr.INTERCEPT, value); }

    String getName() { return (String)mTrendlinePr.getProperty(DmlChartTrendlineAttr.NAME); }
    void setName(String value) { mTrendlinePr.setProperty(DmlChartTrendlineAttr.NAME, value); }

    /// <summary>
    /// Allowed values are [2;6], zero means that property is not set.
    /// </summary>
    int getOrder() { return (/*int*/Integer)mTrendlinePr.getProperty(DmlChartTrendlineAttr.ORDER); }
    void setOrder(int value)
    {
        if (value >= 2 && value <= 6)
            mTrendlinePr.setProperty(DmlChartTrendlineAttr.ORDER, value);
    }

    /// <summary>
    /// Allowed values are [2;255], zero means that property is not set.
    /// </summary>
    int getPeriod() { return (/*int*/Integer)mTrendlinePr.getProperty(DmlChartTrendlineAttr.PERIOD); }
    void setPeriod(int value)
    {
        if (value >= 2 && value <= 255)
            mTrendlinePr.setProperty(DmlChartTrendlineAttr.PERIOD, value);
    }

    DmlChartSpPr getSpPr()
    {
        if (mTrendlinePr.getProperty(DmlChartTrendlineAttr.SP_PR) == null)
            mTrendlinePr.setProperty(DmlChartTrendlineAttr.SP_PR, new DmlChartSpPr());

        return (DmlChartSpPr)mTrendlinePr.getProperty(DmlChartTrendlineAttr.SP_PR);
    }

    DmlChartTrendlineLabel getTrendlineLbl() { return (DmlChartTrendlineLabel)mTrendlinePr.getProperty(DmlChartTrendlineAttr.TRENDLINE_LBL); }
    void setTrendlineLbl(DmlChartTrendlineLabel value) { mTrendlinePr.setProperty(DmlChartTrendlineAttr.TRENDLINE_LBL, value); }

    /*TrendlineType*/int getTrendlineType() { return (/*TrendlineType*/Integer)mTrendlinePr.getProperty(DmlChartTrendlineAttr.TRENDLINE_TYPE); }
    void setTrendlineType(/*TrendlineType*/int value) { mTrendlinePr.setProperty(DmlChartTrendlineAttr.TRENDLINE_TYPE, value); }

    DmlChartTrendlinePr getTrendlinePr() { return mTrendlinePr; }

    /// <summary>
    /// Returns prepared points based on the type of trendline.
    /// </summary>
    private PointFList getPreparedPoints()
    {
        if (mPreparedPoints == null)
            preparePoints();

        return mPreparedPoints;
    }

    /// <summary>
    /// Returns X values of the trendline. Use these values to recalculate axis limits.
    /// </summary>
    DmlChartDimensionData getXValues()
    {
        // Init x values on demand.
        if (mXValues == null)
            prepare();

        return mXValues;
    }

    /// <summary>
    /// Returns Y values of the trendline. Use these values to recalculate axis limits.
    /// </summary>
    DmlChartDimensionData getYValues()
    {
        // Init y values on demand.
        if (mYValues == null)
            prepare();

        return mYValues;
    }

    /// <summary>
    /// Returns array of trendline control points in axis units.
    /// </summary>
    PointFList getPoints()
    {
        if (mPoints == null)
            prepare();
        
        return mPoints;
    }

    /// <summary>
    /// Returns true if X values are valid.
    /// </summary>
    boolean isValid()
    {
        // MS Word does not render Power and Logarithmic trendlines for values less than or equals zero (invalid values.)
        // It makes no sense to create a trend line if all X-values are the same or the number of values is less than 2
        // or if Y-values are empty.
        return (getOrderedSourceXValues().length > 1) && (mSeries.getY().getData().getLastNonEmptyValueIndex() > 0) && 
               !areXValuesSame() && 
               ((getTrendlineType() != TrendlineType.POWER) && (getTrendlineType() != TrendlineType.LOGARITHMIC) || 
               (getOrderedSourceXValues()[0].getFloatValue() > 0));
    }

    /// <summary>
    /// Returns an array of coefficients used to calculate trendline values.
    /// </summary>
    double[][] getCoefficients() { return mCoefficients; }

    /// <summary>
    /// Returns <see cref="ChartSeries"/> for which the trendline is rendered.
    /// </summary>
    ChartSeries getSeries() { return mSeries; }

    /// <summary>
    /// Returns value type of series' x values.
    /// </summary>
    private /*DmlChartValueType*/int getXValueType() { return (mSeries.getX().getData() == null) ? DmlChartValueType.STRING : mSeries.getX().getData().getValueType(); }

    /// <summary>
    /// Returns sorted X values. 
    /// </summary>
    private DmlChartValue[] getOrderedSourceXValues() { return  mSeries.getOrderedXValuesArray(); }

    private double[][] mCoefficients;
    private DmlChartTrendlinePr mTrendlinePr = new DmlChartTrendlinePr();
    private ChartSeries mSeries;
    private DmlChartDimensionData mXValues;
    private DmlChartDimensionData mYValues;
    private PointFList mPreparedPoints;
    private PointFList mPoints;
    private double mMinValueX = Double.MAX_VALUE;
    private double mMaxValueX = -Double.MAX_VALUE;
    private static final String UNEXPECTED_TRENDLINE_TYPE_MESSAGE = "Unexpected TrendlineType";

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

