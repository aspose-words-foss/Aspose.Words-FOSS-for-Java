// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/03/2021 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents an interface to implement for objects that provide a <see cref="ChartFormat"/>.
/// </summary>
interface IChartFormatSource
{
    /// <summary>
    /// Materializes a <see cref="DmlChartSpPr"/> property that stores fill/outline data in preparation for updating
    /// it. The method is needed for a case when the property refers to a parent <see cref="DmlChartSpPr"/> instance.
    /// </summary>
    public void materializeSpPr();

    /// <summary>
    /// Gets a flag indicating whether fill properties are supported by a chart element.
    /// </summary>
    public boolean isFillSupported();

    /// <summary>
    /// Gets or sets fill properties of a chart element.
    /// </summary>
    public DmlFill getFill(); public void setFill(DmlFill value);

    /// <summary>
    /// Gets or sets outline properties of a chart element.
    /// </summary>
    public DmlOutline getOutline(); public void setOutline(DmlOutline value);

    /// <summary>
    /// Gets or sets the shape type of a chart element.
    /// </summary>
    public /*ChartShapeType*/int getShapeType(); public void setShapeType(/*ChartShapeType*/int value);

    /// <summary>
    /// A theme provider to be able to get the current theme properties.
    /// </summary>
    public IThemeProvider getThemeProvider();

    /// <summary>
    /// Gets a flag indicating whether any format is defined.
    /// </summary>
    public boolean isFormatDefined();
}

