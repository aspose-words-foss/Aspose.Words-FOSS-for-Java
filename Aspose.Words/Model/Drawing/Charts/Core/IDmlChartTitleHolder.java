// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



interface IDmlChartTitleHolder
{
    /// <summary>
    /// If font size is not specified for a chart title, MS Word uses 1.2 * [font size of chart space]. This method
    /// is used to get such calculated font size.
    /// </summary>
    public int getRelativeFontSize(int chartFontSize);

    /// <summary>
    /// Gets or sets title.
    /// </summary>
    public DmlChartTitle getDCTitle(); public void setDCTitle(DmlChartTitle value);

    /// <summary>
    /// Returns the position of the title.
    /// </summary>
    public /*TitlePosition*/int getTitlePosition();

    /// <summary>
    /// Indicates whether the title is visible.
    /// </summary>
    public boolean isVisible();

    /// <summary>
    /// Returns true if title should not be displayed even if it exists.
    /// </summary>
    public boolean getTitleDeleted(); public void setTitleDeleted(boolean value);

    /// <summary>
    /// Gets the default title text.
    /// </summary>
    public String getDefaultTitleText();

    /// <summary>
    /// Gets the title font size in points that MS Word sets for created charts.
    /// </summary>
    public double getDefaultDisplayedFontSize();

    /// <summary>
    /// Gets the title default font size in points.
    /// </summary>
    public double getDefaultFontSize();

    /// <summary>
    /// Gets a style item that is applied to the title.
    /// </summary>
    public /*DmlChartStyleItem*/int getStyleItem();

    /// <summary>
    /// Gets the parent chart space.
    /// </summary>
    public DmlChartSpace getChartSpace();
}

