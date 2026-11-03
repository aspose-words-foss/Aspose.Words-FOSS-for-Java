// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ms.System.Drawing.msPoint;


/// <summary>
/// Provides helper functions to convert between various measurement units.
/// </summary>
public /*static*/ class ConvertUtilCore
{
	/* Simulation of static class by using private constructor */
	private ConvertUtilCore()
	{}

    /// <summary>
    /// Converts points to pixels at 96 dpi.
    /// </summary>
    /// <param name="points">The value to convert.</param>
    /// <remarks>
    /// 1 inch equals 72 points.
    /// </remarks>
    public static double pointToPixel(double points)
    {
        return pointToPixel(points, ImageConstants.STANDARD_RESOLUTION);
    }

    /// <summary>
    /// Converts points to pixels at the specified pixel resolution.
    /// </summary>
    /// <param name="points">The value to convert.</param>
    /// <param name="resolution">The dpi (dots per inch) resolution.</param>
    /// <remarks>
    /// 1 inch equals 72 points.
    /// </remarks>
    public static double pointToPixel(double points, double resolution)
    {
        return (points / POINTS_PER_INCH) * resolution;
    }

    /// <summary>
    /// Converts points to pixels at 96 dpi and rounds to int.
    /// </summary>
    public static int pointToPixelInt(double points)
    {
        return MathUtil.doubleToInt(pointToPixel(points));
    }

    /// <summary>
    /// Converts points to pixels at the specified pixel resolution and rounds to int.
    /// </summary>
    public static int pointToPixelInt(double points, double resolution)
    {
        return MathUtil.doubleToInt(pointToPixel(points, resolution));
    }

    /// <summary>
    /// Converts points to pixels at the specified pixel resolution and casts the result to int.
    /// </summary>
    public static int pointToPixelIntTruncate(double points, double resolution)
    {
        return MathUtil.truncate(pointToPixel(points, resolution));
    }

    /// <summary>
    /// Converts points to pixels at the specified pixel resolution and rounds to int.
    /// Makes the value not less than 1.
    /// </summary>
    public static int pointToPixelIntCeiling(double points, double resolution)
    {
        return Math.max((int)Math.ceil(pointToPixel(points, resolution)), 1);
    }

    /// <summary>
    /// Converts points to pixels at the standard pixel resolution and rounds to int.
    /// Makes the value not less than 1.
    /// </summary>
    public static int pointToPixelIntCeiling(double points)
    {
        return pointToPixelIntCeiling(points, ImageConstants.STANDARD_RESOLUTION);
    }

    /// <summary>
    /// Converts points to pixels at the specified pixel resolution and rounds to int.
    /// Makes the value not less than 1.
    /// </summary>
    public static /*Size*/long pointToPixelInt(/*SizeF*/long sizePoints, float scale, double horizontalDpi, double verticalDpi)
    {
        if (scale <= 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "scale");
        if (horizontalDpi <= 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "dpi");
        if (verticalDpi <= 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "dpi");

        return msSize.ctor(
            Math.max(pointToPixelInt(msSizeF.getWidth(sizePoints) * scale, horizontalDpi), 1),
            Math.max(pointToPixelInt(msSizeF.getHeight(sizePoints) * scale, verticalDpi), 1));
    }

    /// <summary>
    /// Converts points to pixels at the specified pixel resolution and rounds up to int.
    /// Makes the value not less than 1.
    /// </summary>
    public static /*Size*/long pointToPixelIntCeiling(/*SizeF*/long sizePoints, float scale, double horizontalDpi, double verticalDpi)
    {
        return msSize.ctor(pointToPixelIntCeiling(msSizeF.getWidth(sizePoints), scale, horizontalDpi),
                        pointToPixelIntCeiling(msSizeF.getHeight(sizePoints), scale, verticalDpi));
    }

    /// <summary>
    /// Converts rectangle in points to rectangle in pixels at the specified pixel resolution and rounds up to int.
    /// Makes the values not less than 0.
    /// </summary>
    public static Rectangle pointToPixelIntCeiling(RectangleF rectanglePoints, float scale, double horizontalDpi, double verticalDpi)
    {
        return Rectangle.fromLTRB(
            pointToPixelIntCeiling(rectanglePoints.getLeft(), scale, horizontalDpi, 0),
            pointToPixelIntCeiling(rectanglePoints.getTop(), scale, verticalDpi, 0),
            pointToPixelIntCeiling(rectanglePoints.getRight(), scale, horizontalDpi, 0),
            pointToPixelIntCeiling(rectanglePoints.getBottom(), scale, verticalDpi, 0));
    }

    /// <summary>
    /// Converts points to pixels at the specified pixel resolution and rounds up to int.
    /// Makes the value not less than 1.
    /// </summary>
    public static int pointToPixelIntCeiling(float points, float scale, double dpi)
    {
        return pointToPixelIntCeiling(points, scale, dpi, 1);
    }

    /// <summary>
    /// Converts points to pixels at the specified pixel resolution and rounds up to int.
    /// Makes the value not less than parameter minValue.
    /// </summary>
    public static int pointToPixelIntCeiling(float points, float scale, double dpi, int minValue)
    {
        if (scale <= 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "scale");
        if (dpi <= 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "dpi");

        return Math.max((int)Math.ceil(pointToPixel(points * scale, dpi)), minValue);
    }

    /// <summary>
    /// Converts points to microns (1 thousandth of mm).
    /// </summary>
    public static double pointToMicron(double points)
    {
        return points / POINTS_PER_THOUSANDTH_MM;
    }

    /// <summary>
    /// Converts points to microns and rounds to int (1 thousandth of mm).
    /// </summary>
    public static int pointToMicronInt(double points)
    {
        return MathUtil.doubleToInt(pointToMicron(points));
    }

    /// <summary>
    /// Converts points to HIMETRIC (1 hundreds of mm).
    /// </summary>
    public static double pointToHimetric(double points)
    {
        return points / POINTS_PER_HUNDREDTH_MM;
    }

    /// <summary>
    /// Converts points to HIMETRIC (1 hundreds of mm) and rounds to int.
    /// </summary>
    public static int pointToHimetricInt(double points)
    {
        return MathUtil.doubleToInt(pointToHimetric(points));
    }

    /// <summary>
    /// Converts HIMETRIC (1 hundreds of mm) to points.
    /// </summary>
    public static double himetricToPoint(double himetrics)
    {
        return himetrics * POINTS_PER_HUNDREDTH_MM;
    }

    /// <overloads>Converts pixels to points.</overloads>
    /// <summary>
    /// Converts pixels to points at 96 dpi.
    /// </summary>
    /// <param name="pixels">The value to convert.</param>
    /// <remarks>
    /// 1 inch equals 72 points.
    /// </remarks>
    public static double pixelToPoint(double pixels)
    {
        return pixelToPoint(pixels, ImageConstants.STANDARD_RESOLUTION);
    }

    /// <summary>
    /// Converts pixels to points at 96 dpi.
    /// </summary>
    /// <remarks>
    /// 1 inch equals 72 points.
    /// </remarks>
    public static RectangleF pixelToPoint(RectangleF rectInPixel)
    {
        return RectangleF.fromLTRB((float)pixelToPoint(rectInPixel.getLeft()), (float)pixelToPoint(rectInPixel.getTop()),
                                   (float)pixelToPoint(rectInPixel.getRight()), (float)pixelToPoint(rectInPixel.getBottom()));
    }

    /// <summary>
    /// Converts pixels to points at the specified pixel resolution.
    /// </summary>
    /// <param name="pixels">The value to convert.</param>
    /// <param name="resolution">The dpi (dots per inch) resolution.</param>
    /// <remarks>
    /// 1 inch equals 72 points.
    /// </remarks>
    public static double pixelToPoint(double pixels, double resolution)
    {
        return ((pixels / resolution) * POINTS_PER_INCH);
    }

    public static int pixelToTwip(double pixels, double resolution)
    {
        return MathUtil.doubleToInt((pixels / resolution) * TWIPS_PER_INCH);
    }

    public static int pixelToLi(double pixels, double resolution)
    {
        return MathUtil.doubleToInt((pixels / resolution) * LIS_PER_INCH);
    }

    /// <summary>
    /// Converts pixels from one resolution to another.
    /// </summary>
    /// <param name="pixels">The value to convert.</param>
    /// <param name="oldDpi">The current dpi (dots per inch) resolution.</param>
    /// <param name="newDpi">The new dpi (dots per inch) resolution.</param>
    public static int pixelToNewDpi(double pixels, double oldDpi, double newDpi)
    {
        return MathUtil.doubleToInt(pixels * newDpi / oldDpi);
    }

    /// <summary>
    /// Converts inches to points.
    /// </summary>
    /// <param name="inches">The value to convert.</param>
    /// <remarks>
    /// 1 inch equals 72 points.
    /// </remarks>
    public static double inchToPoint(double inches)
    {
        return inches * POINTS_PER_INCH;
    }

    /// <summary>
    /// Converts points to inches.
    /// </summary>
    /// <param name="points">The value to convert.</param>
    /// <remarks>
    /// 1 inch equals 72 points.
    /// </remarks>
    public static double pointToInch(double points)
    {
        return points / POINTS_PER_INCH;
    }

    /// <summary>
    /// Converts millimeters to points.
    /// </summary>
    /// <param name="mm">The value to convert.</param>
    /// <remarks>
    /// 1 inch equals 25.4 millimeters. 1 inch equals 72 points.
    /// </remarks>
    public static double mmToPoint(double mm)
    {
        return mm * POINTS_PER_MM;
    }

    public static int mmToTwip(double mm)
    {
        return MathUtil.doubleToInt(mm * TWIPS_PER_MM);
    }

    public static /*Size*/long mmToTwip(double x, double y)
    {
        return msSize.ctor(mmToTwip(x), mmToTwip(y));
    }

    /// <summary>
    /// Converts mm to pixels at 96 dpi and rounds to int.
    /// </summary>
    public static int mmToPixelInt(double mm)
    {
        return MathUtil.doubleToInt(mmToPixel(mm));
    }

    public static double mmToPixel(double mm)
    {
        return mmToPixel(mm, ImageConstants.STANDARD_RESOLUTION);
    }

    public static double mmToPixel(double mm, double resolution)
    {
        return mm * resolution / MM_PER_INCH;
    }

    public static double inchToPixel(double inches)
    {
        return inchToPixel(inches, ImageConstants.STANDARD_RESOLUTION);
    }

    public static double inchToPixel(double inches, double resolution)
    {
        return inches * resolution;
    }

    /// <summary>
    /// Converts centimeters to points.
    /// </summary>
    public static double cmToPoint(double cm)
    {
        return cm * POINTS_PER_CM;
    }

    /// <summary>
    /// Converts centimetres to twips.
    /// </summary>
    public static int cmToTwip(double cm)
    {
        return MathUtil.doubleToInt(cm * TWIPS_PER_CM);
    }

    public static int cmToEmu(double cmValue)
    {
        return twipToEmu(cmToTwip(cmValue));
    }

    public static double cmToInch(double cm)
    {
        return cm / CM_PER_INCH;
    }

    /// <summary>
    /// 1 line is 12 points.
    /// </summary>
    public static double lineToPoint(double lines)
    {
        return lines * POINTS_PER_LINE;
    }

    /// <summary>
    /// 1 line is 12 points.
    /// </summary>
    public static int lineHundredthToTwip(int lineHundredths)
    {
        return MathUtil.doubleToInt(lineHundredths * TWIPS_PER_LINE / 100.0);
    }

    public static double pointToLine(double points)
    {
        return points / POINTS_PER_LINE;
    }

    public static int pointToHalfPoint(double points)
    {
        // This function is used to convert points to half points for storing font's size.
        // Previously we used DoubleToInt function here (with rounding to nearest even int) but
        // in order to copy MS Word behavior we should use DoublePal.RoundToIntUp() function instead.
        // This was requested in WORDSNET-8388 And as no existing tests failed i decided to change
        // this function's behavior and not to create one with similar behavior but with rounding
        // to nearest int as i have done for DoubleToInt.
        return DoublePal.roundToIntUp(points * 2.0);
    }

    public static double halfPointToPoint(int halfPoints)
    {
        return halfPoints / 2.0;
    }

    public static double halfPointToTwip(int halfPoints)
    {
        return halfPoints * TWIPS_PER_HALF_POINT;
    }

    public static int pointToEightsPoint(double points)
    {
        return MathUtil.doubleToInt(points * 8.0);
    }

    public static double eightsPointToPoint(int eightsPoints)
    {
        return eightsPoints / 8.0;
    }

    public static int eightsPointToTwip(int eightsPoint)
    {
        return MathUtil.doubleToInt(eightsPoint * 2.5);
    }

    public static int pointToTwip(double points)
    {
        return MathUtil.doubleToInt(points * TWIPS_PER_POINT);
    }

    public static double twipToPoint(int twips)
    {
        return twips / TWIPS_PER_POINT;
    }

    public static double twipToInch(int twips)
    {
        return twips / TWIPS_PER_INCH;
    }

    public static double twipToPoint(double twips)
    {
        return twips / TWIPS_PER_POINT;
    }

    public static double twipToPicas(double twips)
    {
        return twips / TWIPS_PER_PICAS;
    }

    public static double twipToMm(int twips)
    {
        return twips / TWIPS_PER_MM;
    }

    public static double twipToCm(int twips)
    {
        return twips / TWIPS_PER_CM;
    }

    /// <summary>
    /// 1 line is 12 points.
    /// </summary>
    public static double twipToLine(int twips)
    {
        return twips / 240.0;
    }

    /// <summary>
    /// Converts twips to pixels at 96 dpi.
    /// </summary>
    public static int twipToPixel(int twips)
    {
        return pointToPixelInt(twipToPoint(twips));
    }

    public static int inchToTwip(double inches)
    {
        return MathUtil.doubleToInt(inches * TWIPS_PER_INCH);
    }

    public static /*Size*/long inchToTwip(double x, double y)
    {
        return msSize.ctor(inchToTwip(x), inchToTwip(y));
    }

    public static int emuToTwip(int emus)
    {
        return MathUtil.doubleToInt(emus / EMUS_PER_TWIP);
    }

    public static int twipToEmu(int twips)
    {
        return MathUtil.doubleToInt(twips * EMUS_PER_TWIP);
    }

    /// <summary>
    /// Additional overloaded method is needed in Java because <see cref="T:SizeF PointToEmu(SizeF sizeInPoints)"/>
    /// is converted to /*SizeF*/long pointToEmu(/*SizeF*/long sizeInPoints) and this method is invoked instead of
    /// <see cref="T:int PointToEmu(double points)"/> in Java.
    /// </summary>
    public static int pointToEmu(int points)
    {
        return MathUtil.doubleToInt(points * EMUS_PER_POINT);
    }

    public static int pointToEmu(double points)
    {
        return MathUtil.doubleToInt(points * EMUS_PER_POINT);
    }

    public static RectangleF pointToEmu(RectangleF rectInPoints)
    {
        return RectangleF.fromLTRB(pointToEmu(rectInPoints.getLeft()), pointToEmu(rectInPoints.getTop()),
            pointToEmu(rectInPoints.getRight()), pointToEmu(rectInPoints.getBottom()));
    }

    public static /*SizeF*/long pointToEmu(/*SizeF*/long sizeInPoints)
    {
        return msSizeF.ctor(pointToEmu(msSizeF.getWidth(sizeInPoints)), pointToEmu(msSizeF.getHeight(sizeInPoints)));
    }

    public static double emuToPoint(int emus)
    {
        return emus / EMUS_PER_POINT;
    }

    public static double emuToPoint(double emus)
    {
        return emus / EMUS_PER_POINT;
    }

    public static RectangleF emuToPoint(RectangleF rectInEmu)
    {
        return new RectangleF(
            (float)emuToPoint(rectInEmu.getLeft()),
            (float)emuToPoint(rectInEmu.getTop()),
            (float)emuToPoint(rectInEmu.getWidth()),
            (float)emuToPoint(rectInEmu.getHeight()));
    }

    public static double pixelToEmu(int pixels)
    {
        return pointToEmu(pixelToPoint(pixels));
    }

    public static double pixelToEmu(int pixels, double resolution)
    {
        return pointToEmu(pixelToPoint(pixels, resolution));
    }
    public static RectangleF pixelToEmu(RectangleF rectInPixels, double resolution)
    {
        return RectangleF.fromLTRB(
            (float)pixelToEmu((int)rectInPixels.getLeft(), resolution),
            (float)pixelToEmu((int)rectInPixels.getTop(), resolution),
            (float)pixelToEmu((int)rectInPixels.getRight(), resolution),
            (float)pixelToEmu((int)rectInPixels.getBottom(), resolution));
    }

    public static RectangleF pixelToEmu(RectangleF rectInPixels)
    {
        return new RectangleF(
            (float)pixelToEmu((int)rectInPixels.getLeft()),
            (float)pixelToEmu((int)rectInPixels.getTop()),
            (float)pixelToEmu((int)rectInPixels.getWidth()),
            (float)pixelToEmu((int)rectInPixels.getHeight()));
    }

    public static double emuToInch(int emus)
    {
        return emus / EMUS_PER_INCH;
    }

    public static float emuToPixel(int emu)
    {
        return (float)pointToPixel(emu / EMUS_PER_POINT);
    }

    public static RectangleF emuToPixel(RectangleF rectInEmu)
    {
        return RectangleF.fromLTRB(emuToPixel((int)rectInEmu.getLeft()), emuToPixel((int)rectInEmu.getTop()),
            emuToPixel((int)rectInEmu.getRight()), emuToPixel((int)rectInEmu.getBottom()));
    }

    public static float emuToMillimeter(float emus)
    {
        return emus / (float)EMUS_PER_MM;
    }

    public static double emuToMillimeter(int emus)
    {
        return emus / EMUS_PER_MM;
    }

    public static int mmToEmu(double mm)
    {
        return MathUtil.doubleToInt(mm * EMUS_PER_MM);
    }

    public static RectangleF mmToEmu(RectangleF mm)
    {
        return RectangleF.fromLTRB(mmToEmu(mm.getLeft()), mmToEmu(mm.getTop()), mmToEmu(mm.getRight()), mmToEmu(mm.getBottom()));
    }

    public static int liToPixel(double lis, double resolution)
    {
        // Casting to float in the code below may lead to inexact calculation for small li values.
        // This inexactness allows to get results close to Word's while laying out documents with
        // the printer metrics compatibility option. Use this method with caution for
        // other than printer metrics-related calculations.
        return MathUtil.doubleToInt((float)(lis / LIS_PER_POINT) / POINTS_PER_INCH * resolution);
    }

    /// <summary>
    /// Converts lis to pixels at 96 dpi.
    /// </summary>
    public static int liToPixel(int width)
    {
        return liToPixel(width, 96.0);
    }

    /// <summary>
    /// <para>Converts points to layout integers (custom scale value).</para>
    /// <para>
    /// This method accepts a double because it is mostly used for conversion
    /// from model public values into layout and in the model such values are doubles.
    /// </para>
    /// </summary>
    public static int pointToLi(double points)
    {
        return MathUtil.doubleToInt(points * LIS_PER_POINT);
    }

    /// <summary>
    /// The same as above. Needed only to autoport .Net strong autoboxing to java.
    /// </summary>
    public static int pointToLi(int points)
    {
        return MathUtil.doubleToInt(points * LIS_PER_POINT);
    }

    /// <summary>
    /// Converts layout integers (custom scale value) to points.
    ///
    /// The result is float because the result is used for the APS model where values are floats.
    /// </summary>
    public static float liToPoint(int lis)
    {
        return (float)lis / LIS_PER_POINT;
    }

    /// <summary>
    /// Converts twips to layout integers (custom scale value).
    /// </summary>
    public static int twipToLi(int twips)
    {
        return twips * LIS_PER_TWIP;
    }

    /// <summary>
    /// Converts layout integers (custom scale value) to twips.
    /// </summary>
    public static int liToTwip(int lis)
    {
        return lis / LIS_PER_TWIP;
    }

    /// <summary>
    /// Converts list to twips, rounding the result up.
    /// </summary>
    /// <remarks>
    /// So it answers the question "how many twips do you need to accommodate the given number of lis".
    /// It is used for table cell contents width calculation, because MS Word works with cell widths in twips.
    /// </remarks>
    public static int lisToTwipRoundingUp(int lis)
    {
        int twips = lis / LIS_PER_TWIP;
        int remainder = lis % LIS_PER_TWIP;
        int rounding = (remainder == 0) ? 0 : 1;
        return twips + rounding;
    }

    /// <summary>
    /// Converts half points to layout integers (custom scale value).
    /// </summary>
    public static int halfPointToLi(double halfPoints)
    {
        return MathUtil.doubleToInt(halfPoints * LIS_PER_HALF_POINT);
    }

    /// <summary>
    /// Converts layout integers (custom scale value) to half points.
    /// </summary>
    public static float liToHalfPoint(int lis)
    {
        return (float)lis / LIS_PER_HALF_POINT;
    }

    public static RectangleF liToPoint(Rectangle li)
    {
        return new RectangleF(liToPoint(li.getX()), liToPoint(li.getY()), liToPoint(li.getWidth()), liToPoint(li.getHeight()));
    }

    public static /*PointF*/long liToPoint(/*Point*/long li)
    {
        return msPointF.ctor(liToPoint(msPoint.getX(li)), liToPoint(msPoint.getY(li)));
    }

    public static /*SizeF*/long liToPointFromSize(/*Size*/long li)
    {
        return msSizeF.ctor(liToPoint(msSize.getWidth(li)), liToPoint(msSize.getHeight(li)));
    }

    public static Rectangle pointToLi(RectangleF pt)
    {
        return new Rectangle(pointToLi(pt.getX()),
        pointToLi(pt.getY()), pointToLi(pt.getWidth()),
        pointToLi(pt.getHeight()));
    }

    /// <summary>
    /// </summary>
    public static /*Point*/long pointToLi(/*PointF*/long pt)
    {
        return msPoint.ctor(pointToLi(msPointF.getX(pt)), pointToLi(msPointF.getY(pt)));
    }

    /// <summary>
    /// Converts layout integers (custom scale value) to lines,
    /// each line is 12 points.
    /// </summary>
    public static float liToLine(int lis)
    {
        return (float)((float)lis / LIS_PER_POINT) / 12.0f; // Casting for Java.
    }

    /// <summary>
    /// Converts 16.16 fixed point into floating point number.
    /// </summary>
    public static double fixedToDouble(int value)
    {
        return value / 65536.0;
    }

    /// <summary>
    /// Converts a floating point number into 16.16 fixed point number.
    /// </summary>
    public static int doubleToFixed(double value)
    {
        // TODO 1 Maybe I should throw instead?
        if (value > MAX_FIXED_VALUE)
            value = MAX_FIXED_VALUE;
        else if (value < MIN_FIXED_VALUE)
            value = MIN_FIXED_VALUE;

        return MathUtil.doubleToInt(value * 65536.0);
    }

    public static double dmlAnglesToRadians(double dmlAngle)
    {
        return MathUtil.degreesToRadians(dmlAngle / DML_ANGLES_PER_DEGREE);
    }

    public static double radiansToDmlAngles(double value)
    {
        return MathUtil.radiansToDegrees(value) * DML_ANGLES_PER_DEGREE;
    }

    public static double degreesToDmlAngles(double value)
    {
        return value * DML_ANGLES_PER_DEGREE;
    }

    public static double dmlAnglesToDegrees(double value)
    {
        return value / DML_ANGLES_PER_DEGREE;
    }

    /// <summary>
    /// Converts lis to pixels, rounds to int and converts back to lis.
    /// </summary>
    public static int liToLiRoundedByPixels(int value, float resolution)
    {
        int pixels = liToPixel(value, resolution);
        return pixelToLi(pixels, resolution);
    }

    /// <summary>
    /// Converts inches to centimeters.
    /// </summary>
    /// <param name="inches">The value to convert.</param>
    /// <remarks>
    /// 1 inch equals 2.54 centimeters.
    /// </remarks>
    public static double inchToCm(double inches)
    {
        return inches * CM_PER_INCH;
    }

    public static double pixelToMm(double pixels, double resolution)
    {
        return pixelToPoint(pixels, resolution) / POINTS_PER_MM;
    }

    public static double pixelToMicroMeter(double pixels, double resolution)
    {
        return pixelToPoint(pixels, resolution) / POINTS_PER_THOUSANDTH_MM;
    }

    public static double pointToMm(double points)
    {
        return points / POINTS_PER_MM;
    }

    public static double pointToCm(double points)
    {
        return (points * CM_PER_INCH) / POINTS_PER_INCH;
    }

    /// <summary>
    /// Converts the specified value to twips, gets the smallest integral value that is greater than or equal
    /// to the converted value, and converts it to emus.
    /// </summary>
    /// <param name="emus">The specified values</param>
    /// <returns>The calculated value</returns>
    public static double ceilingTwipsToEmus(double emus)
    {
        return Math.ceil(emus / EMUS_PER_TWIP) * EMUS_PER_TWIP;
    }

    /// <summary>
    /// http://trac.bookofhook.com/bookofhook/trac.cgi/wiki/IntroductionToFixedPointMath
    /// </summary>
    public static final double MAX_FIXED_VALUE = 32767.0 + 65535.0 / 65536.0;  // 0x7fffffff
    public static final double MIN_FIXED_VALUE = -32768.0 - 65535.0 / 65536.0; // 0x8000ffff

    public static final double MM_PER_INCH = 25.4;
    public static final double CM_PER_INCH = 2.54;

    public static final double POINTS_PER_INCH = 72.0;
    public static final float POINTS_PER_HUNDREDTH_INCH = (float)POINTS_PER_INCH * 0.01f;
    public static final float POINTS_PER_THOUSANDTH_INCH = (float)POINTS_PER_INCH * 0.001f;

    public static final float PIXELS_PER_POINT_STANDARD_RESOLUTION = (float)(ImageConstants.STANDARD_RESOLUTION / POINTS_PER_INCH);
    public static final float POINTS_PER_PIXEL_STANDARD_RESOLUTION = (float)(1.0 / PIXELS_PER_POINT_STANDARD_RESOLUTION);

    public static final double POINTS_PER_MM = POINTS_PER_INCH / MM_PER_INCH;
    public static final float POINTS_PER_TENTH_MM = (float)POINTS_PER_MM * 0.1f;
    public static final float POINTS_PER_HUNDREDTH_MM = (float)POINTS_PER_MM * 0.01f;
    public static final float POINTS_PER_THOUSANDTH_MM = (float)POINTS_PER_MM * 0.001f;
    public static final double POINTS_PER_CM = POINTS_PER_MM * 10.0;

    private static final double POINTS_PER_LINE = 12.0;

    public static final double TWIPS_PER_POINT = 20.0;
    public static final double TWIPS_PER_HALF_POINT = TWIPS_PER_POINT / 2.0;
    public static final double TWIPS_PER_INCH = TWIPS_PER_POINT * POINTS_PER_INCH;
    public static final double TWIPS_PER_MM = TWIPS_PER_POINT * POINTS_PER_MM;
    public static final double TWIPS_PER_CM = TWIPS_PER_MM * 10.0;
    public static final double TWIPS_PER_PICAS = TWIPS_PER_POINT * 12.0;
    private static final double TWIPS_PER_LINE = TWIPS_PER_POINT * POINTS_PER_LINE;

    public static final double POINTS_PER_TWIP = 1.0 / TWIPS_PER_POINT;

    public static final double EMUS_PER_POINT = 12700.0;
    private static final double EMUS_PER_INCH = EMUS_PER_POINT * POINTS_PER_INCH;
    private static final double EMUS_PER_MM = EMUS_PER_POINT * POINTS_PER_MM;
    public static final double EMUS_PER_TWIP = EMUS_PER_POINT * POINTS_PER_TWIP;

    public static final int LIS_PER_POINT = 1000;
    private static final int LIS_PER_INCH = (int)(LIS_PER_POINT * POINTS_PER_INCH);
    public static final int LIS_PER_TWIP = (int)(LIS_PER_POINT / TWIPS_PER_POINT);
    private static final int LIS_PER_HALF_POINT = LIS_PER_POINT / 2;

    /// <summary>
    /// WordLi is a measure similar to AW's Li.
    /// </summary>
    /// <remarks>
    /// Eventually, Lis should be replaced by WordLis to achieve higher fidelity and fix rounding issues, such as WORDSNET-19657
    /// </remarks>
    public static final int WORD_LIS_PER_POINT = 0x1000;

    /// <summary>
    /// This is widely used in Word as maximum size in points for an object.
    /// </summary>
    public static final double MAX_SIZE_POINT = 1584.0;

    /// <summary>
    /// This is widely used in Word as maximum size in emus for an object.
    /// </summary>
    public static final double MAX_SIZE_EMUS = 1584.0 * EMUS_PER_POINT;

    /// <summary>
    /// This is widely used in Word as minimum size in points for an object.
    /// </summary>
    public static final double MIN_SIZE_POINT = 0.75d;

    public static final int MAX_SIZE_TWIP = (int)(MAX_SIZE_POINT * TWIPS_PER_POINT);

    public static final int MIN_SIZE_TWIP = (int)(MIN_SIZE_POINT * TWIPS_PER_POINT);

    public static final int MAX_SIZE_LIS = 1584 * LIS_PER_POINT;

    public static final double DML_ANGLES_PER_DEGREE = 60000.0;
}

