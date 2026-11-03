// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/05/2025 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import com.aspose.DrColor;


/// <summary>
/// Defines a layout for rendering multiple pages into a single output.
/// </summary>
/// <remarks>
/// Use one of the static factory methods to create a layout configuration.
/// </remarks>
public final class MultiPageLayout
{
    /// <summary>
    /// Private ctor.
    /// </summary>
    private MultiPageLayout(/*MultiPageLayoutMode*/int mode, int columns, float horizontalGap, float verticalGap)
    {
        if (horizontalGap < 0)
            throw new IllegalArgumentException("Horizontal gap must be non-negative." + "\r\nParameter name: " + "horizontalGap");

        if (verticalGap < 0)
            throw new IllegalArgumentException("Vertical gap must be non-negative." + "\r\nParameter name: " + "verticalGap");

        mMode = mode;
        mColumns = columns;
        mHorizontalGap = horizontalGap;
        mVerticalGap = verticalGap;

        // Set the default values.
        setBorderWidth(0f);
        setBorderColor(msColor.Empty);
        setBackColor(msColor.Empty);
    }

    /// <summary>
    /// Creates a layout where all specified pages are rendered vertically one below the other in a single output.
    /// </summary>
    /// <param name="verticalGap">The vertical gap between pages in points.</param>
    public static MultiPageLayout vertical(float verticalGap)
    {
        return new MultiPageLayout(MultiPageLayoutMode.VERTICAL, 1, 0f, verticalGap);
    }

    /// <summary>
    /// Creates a layout in which all specified pages are rendered horizontally side by side,
    /// left to right, in a single output.
    /// </summary>
    /// <param name="horizontalGap">The horizontal gap between pages in points.</param>
    public static MultiPageLayout horizontal(float horizontalGap)
    {
        return new MultiPageLayout(MultiPageLayoutMode.HORIZONTAL, Integer.MAX_VALUE, horizontalGap, 0f);
    }

    /// <summary>
    /// Creates a layout in which pages are rendered left-to-right, top-to-bottom, in a grid with the
    /// specified number of columns.
    /// </summary>
    /// <param name="columns">The number of columns in the layout. Must be greater than zero.</param>
    /// <param name="horizontalGap">The horizontal gap between columns in points.</param>
    /// <param name="verticalGap">The vertical gap between rows in points.</param>
    /// <exception cref="ArgumentOutOfRangeException">Thrown if <paramref name="columns"/> is less than or equal to zero.</exception>
    public static MultiPageLayout grid(int columns, float horizontalGap, float verticalGap)
    {
        if (columns <= 0)
            throw new IllegalArgumentException("Columns must be greater than zero." + "\r\nParameter name: " + "columns");

        return new MultiPageLayout(MultiPageLayoutMode.GRID, columns, horizontalGap, verticalGap);
    }

    /// <summary>
    /// Creates a layout where each page is rendered as a separate frame in a multi-frame TIFF image.
    /// Applicable only to TIFF image formats.
    /// </summary>
    public static MultiPageLayout tiffFrames()
    {
        return new MultiPageLayout(MultiPageLayoutMode.TIFF_FRAMES, 0, 0f, 0f);
    }

    /// <summary>
    /// Creates a layout that renders only the first of specified pages.
    /// </summary>
    public static MultiPageLayout singlePage()
    {
        return new MultiPageLayout(MultiPageLayoutMode.SINGLE_PAGE, 0, 0f, 0f);
    }

    /// <summary>
    /// Gets or sets the background color of the output.
    /// The default is <see cref="Color.Empty"/>.
    /// </summary>
    public Color getBackColor() { return mBackColor.toNativeColor(); }
    public void setBackColor(Color value) { mBackColor = DrColor.fromNativeColor(value); }

    /// <summary>
    /// Gets or sets the color of the pages border.
    /// The default is <see cref="Color.Empty"/>.
    /// </summary>
    public Color getBorderColor() { return mBorderColor.toNativeColor(); }
    public void setBorderColor(Color value) { mBorderColor = DrColor.fromNativeColor(value); }

    /// <summary>
    /// Gets or sets the width of the pages border.
    /// The default is 0.
    /// </summary>
    public float getBorderWidth() { return mBorderWidth; }
    public void setBorderWidth(float value)
    {
        if (value < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "Border width must be non-negative.");

        mBorderWidth = value;
    }

    /// <summary>
    /// Gets a layout mode for rendering multiple pages into a single output.
    /// </summary>
    /*MultiPageLayoutMode*/int getMode() { return mMode; }

    /// <summary>
    /// Gets the number of columns in a grid layout.
    /// </summary>
    /// <remarks>
    /// This property is used only if the layout mode is <c>Grid</c>.
    /// </remarks>
    int getColumns() { return mColumns; }

    /// <summary>
    /// Gets the horizontal gap between pages in points.
    /// </summary>
    float getHorizontalGap() { return mHorizontalGap; }

    /// <summary>
    /// Gets the vertical gap between pages in points.
    /// </summary>
    float getVerticalGap() { return mVerticalGap; }

    DrColor getBackColorCore() { return mBackColor; }

    DrColor getBorderColorCore() { return mBorderColor; }

    boolean hasBorder() { return (!DrColor.equals(mBorderColor, DrColor.EMPTY)) && (mBorderWidth > 0); }

    private float mBorderWidth;
    private DrColor mBorderColor;
    private DrColor mBackColor;
    private /*final*/ float mVerticalGap;
    private /*final*/ float mHorizontalGap;
    private /*final*/ int mColumns;
    private /*final*/ /*MultiPageLayoutMode*/int mMode;
}

