// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Dmitry Burov
// 14/05/2011 by Alexey Titov

// Ignore Spelling: bezier

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ms.System.Drawing.msSizeF;


public /*static*/ class GeometryUtil
{
	/* Simulation of static class by using private constructor */
	private GeometryUtil()
	{}

    /// <summary>
    /// Calculates the center point of the rectangle.
    /// </summary>
    public static /*PointF*/long getCenterPoint(RectangleF rect)
    {
        return msPointF.ctor(rect.getX() + (float)(rect.getWidth() / 2.0f), rect.getY() + (float)(rect.getHeight() / 2.0f)); // Casting for Java.
    }

    /// <summary>
    /// Gets the specified rectangle points in array.
    /// </summary>
    public static /*PointF*/long[] getRectPoints(RectangleF rect)
    {
        return new /*PointF*/long[] {
            msPointF.ctor(rect.getLeft(), rect.getTop()),
            msPointF.ctor(rect.getRight(), rect.getTop()),
            msPointF.ctor(rect.getRight(), rect.getBottom()),
            msPointF.ctor(rect.getLeft(), rect.getBottom())
        };
    }

    /// <summary>
    /// Calculates the bounding rectangle for the specified rectangle rotated by specified angle in degrees.
    /// </summary>
    public static RectangleF getRotatedRectangleBounds(RectangleF rect, float angle)
    {
        return getBounds(getRotatedRectanglePoints(rect, angle));
    }

    /// <summary>
    /// Gets the bounding rectangle for specified array of points.
    /// </summary>
    public static RectangleF getBounds(/*PointF*/long[] rectPoints)
    {
        return getBounds(null, rectPoints);
    }

    private static RectangleF getBounds(PointFList list, /*PointF*/long[] array)
    {
        // WORDSNET-7424.
        // Return RectangleF.Empty as bound for empty rectangle.
        if ((list == null) && (array == null))
            return RectangleF.Empty;

        int pointCount = (list != null) ? list.getCount() : array.length;
        if (pointCount == 0)
            return RectangleF.Empty;

        /*PointF*/long point0 = (list != null) ? list.get(0) : array[0];
        float maxX = msPointF.getX(point0);
        float minX = maxX;
        float maxY = msPointF.getY(point0);
        float minY = maxY;

        for (int i = 1; i < pointCount; i++)
        {
            /*PointF*/long point = (list != null) ? list.get(i) : array[i];

            if (msPointF.getX(point) > maxX)
                maxX = msPointF.getX(point);
            else if (msPointF.getX(point) < minX)
                minX = msPointF.getX(point);

            if (msPointF.getY(point) > maxY)
                maxY = msPointF.getY(point);
            else if (msPointF.getY(point) < minY)
                minY = msPointF.getY(point);
        }

        return new RectangleF(minX, minY, maxX - minX, maxY - minY);
    }

    /// <summary>
    /// Gets the array of points of the specified rectangle rotated by the specified angle in degrees.
    /// </summary>
    /// <remarks>
    /// The rectangle is rotated about its center; with the y axis pointing down, a positive angle turns it clockwise.
    /// Multiples of 90 degrees use exact sines and cosines, so that such rotations only swap and mirror coordinates
    /// relative to the center (and a full turn returns the points unchanged).
    /// </remarks>
    public static /*PointF*/long[] getRotatedRectanglePoints(RectangleF rect, float angle)
    {
        double sin;
        double cos;
        double quadrantAngle = angle % 90.0;
        if (quadrantAngle == 0)
        {
            int quadrant = (int)(angle / 90.0) % 4;
            if (quadrant < 0)
                quadrant += 4;

            sin = (quadrant == 1) ? 1 : ((quadrant == 3) ? -1 : 0);
            cos = (quadrant == 0) ? 1 : ((quadrant == 2) ? -1 : 0);
        }
        else
        {
            double radians = angle * Math.PI / 180.0;
            sin = Math.sin(radians);
            cos = Math.cos(radians);
        }

        /*PointF*/long center = getCenterPoint(rect);
        /*PointF*/long[] rectPoints = getRectPoints(rect);
        for (int i = 0; i < rectPoints.length; i++)
        {
            // Subtract in double: there the difference of two floats of comparable magnitude is exact.
            double dx = (double)msPointF.getX(rectPoints[i]) - msPointF.getX(center);
            double dy = (double)msPointF.getY(rectPoints[i]) - msPointF.getY(center);
            rectPoints[i] = msPointF.ctor((float)(msPointF.getX(center) + dx * cos - dy * sin), (float)(msPointF.getY(center) + dx * sin + dy * cos));
        }

        return rectPoints;
    }

    /// <summary>
    /// Returns distance between points.
    /// </summary>
    /// <param name="point1">Point 1.</param>
    /// <param name="point2">Point 2.</param>
    public static float getDistanceBetweenPoints(/*PointF*/long point1, /*PointF*/long point2)
    {
        float dX = msPointF.getX(point1) - msPointF.getX(point2);
        float dY = msPointF.getY(point1) - msPointF.getY(point2);
        return (float)Math.sqrt((float)(dX * dX) + (float)(dY * dY)); // Casting for Java.
    }

    /// <summary>
    /// Value of tolerance that is used in geometric computations.
    /// </summary>
    public static final float TOLERANCE = 0.001f;

    /// <summary>
    /// Returns true if the two sizes are equal within the given tolerance.
    /// </summary>
    public static boolean equalsPaperSize(/*SizeF*/long size1, /*SizeF*/long size2, boolean isLandscape, double tolerance)
    {
        if (isLandscape)
        {
            if ((Math.abs(msSizeF.getWidth(size1) - msSizeF.getHeight(size2)) <= tolerance) &&
                (Math.abs(msSizeF.getHeight(size1) - msSizeF.getWidth(size2)) <= tolerance))
                return true;
        }
        else
        {
            if ((Math.abs(msSizeF.getWidth(size1) - msSizeF.getWidth(size2)) <= tolerance) &&
                (Math.abs(msSizeF.getHeight(size1) - msSizeF.getHeight(size2)) <= tolerance))
                return true;
        }

        return false;
    }
}

