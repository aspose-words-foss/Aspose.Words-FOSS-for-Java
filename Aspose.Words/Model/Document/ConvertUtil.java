// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;

/// <summary>
/// Provides helper functions to convert between various measurement units.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/convert-between-measurement-units/">Convert Between Measurement Units</a> documentation article.</para>
/// </summary>
/// <dev>
/// This is only a public wrapper that makes functions available in the public Aspose.Words namespace.
/// The actual implementation is in <see cref="ConvertUtilCore"/>.
/// </dev>
public /*static*/ class ConvertUtil
{
	/* Simulation of static class by using private constructor */
	private ConvertUtil()
	{}

    /// <overloads>Converts points to pixels.</overloads>
    /// <summary>
    /// Converts points to pixels at 96 dpi.
    /// </summary>
    /// <param name="points">The value to convert.</param>
    /// <remarks>
    /// 1 inch equals 72 points.
    /// </remarks>
    public static double pointToPixel(double points)
    {
        return ConvertUtilCore.pointToPixel(points);
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
        return ConvertUtilCore.pointToPixel(points, resolution);
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
        return ConvertUtilCore.pixelToPoint(pixels);
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
        return ConvertUtilCore.pixelToPoint(pixels, resolution);
    }

    /// <summary>
    /// Converts pixels from one resolution to another.
    /// </summary>
    /// <param name="pixels">The value to convert.</param>
    /// <param name="oldDpi">The current dpi (dots per inch) resolution.</param>
    /// <param name="newDpi">The new dpi (dots per inch) resolution.</param>
    public static int pixelToNewDpi(double pixels, double oldDpi, double newDpi)
    {
        return ConvertUtilCore.pixelToNewDpi(pixels, oldDpi, newDpi);
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
        return ConvertUtilCore.inchToPoint(inches);
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
        return ConvertUtilCore.pointToInch(points);
    }

    /// <summary>
    /// Converts millimeters to points.
    /// </summary>
    /// <param name="millimeters">The value to convert.</param>
    /// <remarks>
    /// 1 inch equals 25.4 millimeters. 1 inch equals 72 points.
    /// </remarks>
    public static double millimeterToPoint(double millimeters)
    {
        return ConvertUtilCore.mmToPoint(millimeters);
    }
}

