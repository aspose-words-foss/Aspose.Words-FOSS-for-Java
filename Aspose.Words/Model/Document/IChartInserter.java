// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This interface is used to break the direct dependency Aspose.Words.Model from RW.Docx.
/// We use a stub implementation of IChartInserter in C++ branches until RW.Docx isn't ported to C++.
/// </summary>
interface IChartInserter
{
    /// <summary>
    /// Chart insertion with options.
    /// </summary>
    public Shape insertChart(
        /*ChartType*/int chartType,
        /*ChartStyle*/int chartStyle,
        /*RelativeHorizontalPosition*/int horzPos,
        double left,
        /*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,
        /*WrapType*/int wrapType,
        DocumentBuilder documentBuilder) throws Exception;

    /// <summary>
    /// Creates a chart space for the specified chart type.
    /// </summary>
    public DmlChartSpace createChartSpace(/*ChartType*/int chartType, Document document) throws Exception;

    /// <summary>
    /// Reads chart of specific type from the Aspose.Words.Resources.Charts.ChartTypes.xml
    /// </summary>
    public void readPresetChart(DmlChartSpace chartSpace, /*ChartType*/int chartType, int documentVersion) throws Exception;
}

