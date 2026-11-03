// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2013 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Drawing.msPoint;
import org.testng.annotations.DataProvider;


@Test
public class TestMathUtil
{
    @Test (dataProvider = "testNormalizeZeroDoubleDataProvider")
    public void testNormalizeZeroDouble(double input, double expected, String formatted)
    {
        msAssert.areEqual(expected, MathUtil.normalizeZero(input));
        msAssert.areEqual(formatted, msNumber.formatDouble(MathUtil.normalizeZero(input), "F1", msCultureInfo.getInvariantCulture()));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNormalizeZeroDoubleDataProvider")
	public static Object[][] testNormalizeZeroDoubleDataProvider()
	{
		return new Object[][]
		{
			{0.0,  0.0,  "0.0"},
			{-0.0,  0.0,  "0.0"},
			{5.0,  5.0,  "5.0"},
			{-5.0,  -5.0,  "-5.0"},
			{-9.99,  -9.99,  "-10.0"},
			{5.97,  5.97,  "6.0"},
			{9.99,  9.99,  "10.0"},
			{12345.6789,  12345.6789,  "12345.7"},
		};
	}

    @Test (dataProvider = "testNormalizeZeroFloatDataProvider")
    public void testNormalizeZeroFloat(float input, float expected, String formatted)
    {
        msAssert.areEqual(expected, MathUtil.normalizeZero(input));
        msAssert.areEqual(formatted, msNumber.formatFloat(MathUtil.normalizeZero(input), "F1", msCultureInfo.getInvariantCulture()));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNormalizeZeroFloatDataProvider")
	public static Object[][] testNormalizeZeroFloatDataProvider()
	{
		return new Object[][]
		{
			{0.0f,  0.0f,  "0.0"},
			{-0.0f,  0.0f,  "0.0"},
			{5.0f,  5.0f,  "5.0"},
			{-5.0f,  -5.0f,  "-5.0"},
			{-9.99f,  -9.99f,  "-10.0"},
			{5.97f,  5.97f,  "6.0"},
			{9.99f,  9.99f,  "10.0"},
			{12345.6789f,  12345.6789f,  "12345.7"},
		};
	}


    /// <summary>
    /// Tests <see cref="MathUtil.IsZero(double)"/> method.
    /// </summary>
    @Test
    public void testIsZeroDouble()
    {
        msAssert.isTrue(MathUtil.isZero((double)0.0)); // casting for C++
        msAssert.isTrue(MathUtil.isZero(0.0d));
        msAssert.isTrue(MathUtil.isZero(Double.MIN_VALUE / 2.0));
        msAssert.isTrue(MathUtil.isZero(-Double.MIN_VALUE / 2f));
        msAssert.isFalse(MathUtil.isZero((double)-1));  // casting for C++
        msAssert.isFalse(MathUtil.isZero(Double.MIN_VALUE * 2.0));
    }

    /// <summary>
    /// Tests <see cref="MathUtil.AreEqual(double,double)"/> method.
    /// </summary>
    @Test
    public void testDoubleAreEqual()
    {
        // This is tolerance used by default in this method. 
        final double TOLERANCE = 1e-10;

        testDoubleAreEqualCore(123456.789, TOLERANCE);
        testDoubleAreEqualCore(-123456.789, TOLERANCE);
        testDoubleAreEqualCore(Double.MIN_VALUE, TOLERANCE);
        testDoubleAreEqualCore(0.0d, TOLERANCE);
    }

    private static void testDoubleAreEqualCore(double testValue1, double tolerance)
    {
        msAssert.isTrue(MathUtil.areEqual(testValue1, testValue1));
        msAssert.isTrue(MathUtil.areEqual(testValue1, (testValue1 - tolerance / 2.0)));
        msAssert.isTrue(MathUtil.areEqual(testValue1, (testValue1 + tolerance / 2.0)));
        msAssert.isFalse(MathUtil.areEqual(testValue1, (testValue1 - tolerance)));
        msAssert.isFalse(MathUtil.areEqual(testValue1, (testValue1 + tolerance)));
    }

    @Test
    public void testPolyline()
    {
        /*Point*/long[] points = new /*Point*/long[] { msPoint.ctor(100, 50), msPoint.ctor(200, 70), msPoint.ctor(300, 150), msPoint.ctor(400, 120) };

        int y = MathUtil.getPolylineY(30, points);
        msAssert.areEqual(0, y);

        y = MathUtil.getPolylineY(450, points);
        msAssert.areEqual(105, y);

        y = MathUtil.getPolylineY(100, points);
        msAssert.areEqual(50, y);

        y = MathUtil.getPolylineY(300, points);
        msAssert.areEqual(150, y);

        y = MathUtil.getPolylineY(400, points);
        msAssert.areEqual(120, y);

        y = MathUtil.getPolylineY(150, points);
        msAssert.areEqual(60, y);

        y = MathUtil.getPolylineY(350, points);
        msAssert.areEqual(135, y);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testPolyline2()
    {
        /*Point*/long[] points = new /*Point*/long[] { msPoint.ctor(100, 50), msPoint.ctor(200, 70), msPoint.ctor(200, 150), msPoint.ctor(300, 120) };

        int y = MathUtil.getPolylineY(300, points);
        msAssert.areEqual(0, y);
    }

    @Test
    public void testRoundingDivision()
    {
        msAssert.areEqual(2, MathUtil.divide(4, 2));
        msAssert.areEqual(2, MathUtil.divide(3, 2));
        msAssert.areEqual(3, MathUtil.divide(5, 2));
        msAssert.areEqual(0, MathUtil.divide(499, 1000));
        msAssert.areEqual(0, MathUtil.divide(499, 999));
        msAssert.areEqual(1, MathUtil.divide(500, 1000));
    }

    @Test (dataProvider = "testFectorialDataProvider")
    public void testFectorial(int n, int result)
    {
        msAssert.areEqual(result, MathUtil.factorial(n));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFectorialDataProvider")
	public static Object[][] testFectorialDataProvider()
	{
		return new Object[][]
		{
			{3,  6},
			{4,  24},
			{5,  120},
			{11,  39916800},
			{12,  479001600},
		};
	}

    /// <summary>
    /// WORDSJAVA-2352 '(int)double.PositiveInfinity' returns int.MinValue on .Net. 
    /// '(int)double.MaxValue', '(int)double.NaN' - the same.
    /// </summary>
    @Test
    public void testDoubleToInt()
    {
        msAssert.areEqual(Integer.MIN_VALUE, MathUtil.doubleToInt(Double.POSITIVE_INFINITY));
        msAssert.areEqual(Integer.MIN_VALUE, MathUtil.doubleToInt(Double.NEGATIVE_INFINITY));

        msAssert.areEqual(Integer.MIN_VALUE, MathUtil.doubleToInt(Double.MAX_VALUE));
        msAssert.areEqual(Integer.MIN_VALUE, MathUtil.doubleToInt(-Double.MAX_VALUE));

        msAssert.areEqual(Integer.MIN_VALUE, MathUtil.doubleToInt(Double.NaN));
    }
}

