// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Drawing.RectangleF;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msPointF;
import org.testng.annotations.DataProvider;


@Test
public class TestGeometryUtil
{
    /// <summary>
    /// Rotations by multiples of 90 degrees only swap and mirror coordinates, so the results are exact.
    /// </summary>
    @Test (dataProvider = "testRotatedBoundsQuadrantsDataProvider")
    public void testRotatedBoundsQuadrants(double angle, double x, double y, double width, double height)
    {
        RectangleF bounds = GeometryUtil.getRotatedRectangleBounds(new RectangleF(0f, 0f, 100f, 50f), (float)angle);

        msAssert.areEqual((float)x, bounds.getX());
        msAssert.areEqual((float)y, bounds.getY());
        msAssert.areEqual((float)width, bounds.getWidth());
        msAssert.areEqual((float)height, bounds.getHeight());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRotatedBoundsQuadrantsDataProvider")
	public static Object[][] testRotatedBoundsQuadrantsDataProvider()
	{
		return new Object[][]
		{
			{0.0,  0.0,  0.0,  100.0,  50.0},
			{90.0,  25.0,  -25.0,  50.0,  100.0},
			{180.0,  0.0,  0.0,  100.0,  50.0},
			{270.0,  25.0,  -25.0,  50.0,  100.0},
			{-90.0,  25.0,  -25.0,  50.0,  100.0},
			{450.0,  25.0,  -25.0,  50.0,  100.0},
			{-720.0,  0.0,  0.0,  100.0,  50.0},
		};
	}

    @Test (dataProvider = "testRotatedBoundsDataProvider")
    public void testRotatedBounds(double angle, double x, double y, double width, double height)
    {
        RectangleF bounds = GeometryUtil.getRotatedRectangleBounds(new RectangleF(0f, 0f, 100f, 50f), (float)angle);

        msAssert.areEqual(x, 1e-4, bounds.getX());
        msAssert.areEqual(y, 1e-4, bounds.getY());
        msAssert.areEqual(width, 1e-4, bounds.getWidth());
        msAssert.areEqual(height, 1e-4, bounds.getHeight());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRotatedBoundsDataProvider")
	public static Object[][] testRotatedBoundsDataProvider()
	{
		return new Object[][]
		{
			{45.0,  -3.0330086,  -28.033009,  106.06601,  106.06601},
			{30.0,  -5.8012695,  -21.650635,  111.60254,  93.30127},
			{359.9,  -0.043560028,  -0.08723271,  100.08713,  50.17447},
			{-123.4,  1.6047668,  -30.504417,  96.79047,  111.00883},
		};
	}

    /// <summary>
    /// With the y axis pointing down, a positive angle turns the rectangle clockwise about its center.
    /// </summary>
    @Test
    public void testRotatedPointsDirection()
    {
        /*PointF*/long[] points = GeometryUtil.getRotatedRectanglePoints(new RectangleF(0f, 0f, 100f, 50f), 90f);

        // The top left corner goes to the top right of the rotated rectangle.
        msAssert.areEqual(75f, msPointF.getX(points[0]));
        msAssert.areEqual(-25f, msPointF.getY(points[0]));
        msAssert.areEqual(75f, msPointF.getX(points[1]));
        msAssert.areEqual(75f, msPointF.getY(points[1]));
    }
}

