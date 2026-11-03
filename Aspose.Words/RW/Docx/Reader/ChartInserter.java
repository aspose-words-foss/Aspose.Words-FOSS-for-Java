// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/02/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;


class ChartInserter implements IChartInserter
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
        DocumentBuilder documentBuilder) throws Exception
    {
        Document document = documentBuilder.getDocument();
        Shape shape = new Shape(document, ShapeMarkupLanguage.DML);

        DmlChartSpace chartSpace = createChartSpace(chartType, document);
        shape.setDmlNode(chartSpace);
        shape.setRunPr(documentBuilder.getRunPrCopy());
        shape.setRelativeHorizontalPosition(horzPos);
        shape.setLeft(left);
        shape.setRelativeVerticalPosition(vertPos);
        shape.setTop(top);
        shape.setWrapType(wrapType);

        CompatibilityOptions compatibility = document.getCompatibilityOptions();
        if (isChartExChart(chartType) ||
            (chartStyle != ChartStyle.NORMAL) ||
            (compatibility.getMswVersion() >= MsWordVersionCore.WORD_2013))
        {
            chartSpace.setColorStyle(generateChartColorStyle());
            chartSpace.setChartStyle(chartStyle, chartType, (chartStyle != ChartStyle.NORMAL));

            // WORDSNET-27030 It is needed to optimize the document to version 2013, otherwise MS Word will not allow
            // to insert Word 2016 charts into the document, even though such a chart is already present in it.
            if (!compatibility.isOptimized() && (compatibility.getMswVersion() < MsWordVersionCore.WORD_2013))
            {
                compatibility.optimizeFor(MsWordVersion.WORD_2013);

                document.warn(
                    WarningType.MINOR_FORMATTING_LOSS,
                    WarningSource.UNKNOWN,
                    WarningStrings.OPTIMIZED_FOR_WORD_2013);
            }
        }

        // WORDSNET-15923 If width or height is negative or zero chart has to be scaled to 100% of its container.
        // Preserve width/height ratio.
        if (MathUtil.isLessOrEqual(width,0.0) || MathUtil.isLessOrEqual(height,0.0))
        {
            SizeD containerSize = ShapeSizeValidationHelper.getContainerSize(shape, documentBuilder.getCurrentParagraph());
            SizeD scaledSize = ShapeSizeValidationHelper.scaleToFitContainer(new SizeD(0.0, 0.0), containerSize, 0.0);

            width = scaledSize.getWidth();
            height = scaledSize.getHeight();
        }

        shape.setWidthSafe(width);
        shape.setHeightSafe(height);

        // We presume we are inserting a top level shape, it has to be inserted into the tree
        // for the shape size validation below to work properly.
        documentBuilder.insertNode(shape);

        return shape;
    }

    /// <summary>
    /// Creates a chart space for the specified chart type.
    /// </summary>
    public DmlChartSpace createChartSpace(/*ChartType*/int chartType, Document document) throws Exception
    {
        boolean isChartEx = isChartExChart(chartType);
        if (isChartEx)
        {
            if (document.getComplianceInfo() == null)
                document.setComplianceInfo(new OoxmlComplianceInfo());

            document.getComplianceInfo().markAsIsoTransitional();
        }

        DmlChartSpace chartSpace = new DmlChartSpace(isChartEx);
        readPresetChart(chartSpace, chartType, document.getBuiltInDocumentProperties().getVersion());

        DmlChart chart = chartSpace.getFirstChart();
        setChartTypedProperties(chartType, chart);

        return chartSpace;
    }

    /// <summary>
    /// Reads chart of specific type from the Aspose.Words.Resources.Charts.ChartTypes.xml
    /// </summary>
    public void readPresetChart(DmlChartSpace chartSpace, /*ChartType*/int chartType, int documentVersion) throws Exception
    {
        PresetChartReader.readPresetChart(chartSpace, chartType, documentVersion);
    }

    /// <summary>
    /// Generates a <see cref="DmlChartColorStyle"/> instance that stores data of a colorsX.xml part of a chart.
    /// </summary>
    private static DmlChartColorStyle generateChartColorStyle()
    {
        DmlChartColorStyle colorStyle = new DmlChartColorStyle();
        colorStyle.setId("10");
        colorStyle.setMethod("cycle");

        colorStyle.setColors(new DmlColor[]
        {
            new DmlSchemeColor(ThemeColor.ACCENT_1),
            new DmlSchemeColor(ThemeColor.ACCENT_2),
            new DmlSchemeColor(ThemeColor.ACCENT_3),
            new DmlSchemeColor(ThemeColor.ACCENT_4),
            new DmlSchemeColor(ThemeColor.ACCENT_5),
            new DmlSchemeColor(ThemeColor.ACCENT_6)
        });

        colorStyle.setVariations(new DmlChartColorStyleVariation[]
        {
            new DmlChartColorStyleVariation(),
            new DmlChartColorStyleVariation(new DmlLuminanceModulation(0.6)),
            new DmlChartColorStyleVariation(new DmlLuminanceModulation(0.8), new DmlLuminanceOffset(0.2)),
            new DmlChartColorStyleVariation(new DmlLuminanceModulation(0.8)),
            new DmlChartColorStyleVariation(new DmlLuminanceModulation(0.6), new DmlLuminanceOffset(0.4)),
            new DmlChartColorStyleVariation(new DmlLuminanceModulation(0.5)),
            new DmlChartColorStyleVariation(new DmlLuminanceModulation(0.7), new DmlLuminanceOffset(0.3)),
            new DmlChartColorStyleVariation(new DmlLuminanceModulation(0.7)),
            new DmlChartColorStyleVariation(new DmlLuminanceModulation(0.5), new DmlLuminanceOffset(0.5))
        });

        return colorStyle;
    }

    /// <summary>
    /// Sets chart typed properties.
    /// </summary>
    private static void setChartTypedProperties(/*ChartType*/int chartType, DmlChart chart)
    {
        DmlChartPr chartPr = chart.getChartPr();
        switch (chartType)
        {
            case ChartType.AREA:
                break;
            case ChartType.AREA_STACKED:
                chartPr.setGrouping(Grouping.STACKED);
                break;
            case ChartType.AREA_PERCENT_STACKED:
                chartPr.setGrouping(Grouping.PERCENT_STACKED);
                break;
            case ChartType.AREA_3_D:
                break;
            case ChartType.AREA_3_D_STACKED:
                chartPr.setGrouping(Grouping.STACKED);
                break;
            case ChartType.AREA_3_D_PERCENT_STACKED:
                chartPr.setGrouping(Grouping.PERCENT_STACKED);
                break;
            case ChartType.BAR:
                break;
            case ChartType.BAR_STACKED:
                chartPr.setGrouping(Grouping.STACKED);
                chartPr.setProperty(DmlChartAttrs.OVERLAP, 100);
                break;
            case ChartType.BAR_PERCENT_STACKED:
                chartPr.setGrouping(Grouping.PERCENT_STACKED);
                chartPr.setProperty(DmlChartAttrs.OVERLAP, 100);
                break;
            case ChartType.BAR_3_D:
                break;
            case ChartType.BAR_3_D_STACKED:
                chartPr.setGrouping(Grouping.STACKED);
                break;
            case ChartType.BAR_3_D_PERCENT_STACKED:
                chartPr.setGrouping(Grouping.PERCENT_STACKED);
                break;
            case ChartType.BUBBLE:
                break;
            case ChartType.BUBBLE_3_D:
                ((DmlBubbleChart)chart).setBubble3D(true);
                for (ChartSeries series : chart.getSeries())
                    series.setBubble3D(true);
                break;
            case ChartType.COLUMN:
                break;
            case ChartType.COLUMN_STACKED:
                chartPr.setGrouping(Grouping.STACKED);
                chartPr.setProperty(DmlChartAttrs.OVERLAP, 100);
                break;
            case ChartType.COLUMN_PERCENT_STACKED:
                chartPr.setGrouping(Grouping.PERCENT_STACKED);
                chartPr.setProperty(DmlChartAttrs.OVERLAP, 100);
                break;
            case ChartType.COLUMN_3_D:
                break;
            case ChartType.COLUMN_3_D_CLUSTERED:
                break;
            case ChartType.COLUMN_3_D_STACKED:
                chartPr.setGrouping(Grouping.STACKED);
                break;
            case ChartType.COLUMN_3_D_PERCENT_STACKED:
                chartPr.setGrouping(Grouping.PERCENT_STACKED);
                break;
            case ChartType.DOUGHNUT:
                break;
            case ChartType.LINE:
                break;
            case ChartType.LINE_STACKED:
                chartPr.setGrouping(Grouping.STACKED);
                break;
            case ChartType.LINE_PERCENT_STACKED:
                chartPr.setGrouping(Grouping.PERCENT_STACKED);
                break;
            case ChartType.LINE_3_D:
                break;
            case ChartType.PIE:
                break;
            case ChartType.PIE_OF_BAR:
                chartPr.setOfPieType(OfPieType.BAR);
                break;
            case ChartType.PIE_OF_PIE:
                chartPr.setOfPieType(OfPieType.PIE);
                break;
            case ChartType.PIE_3_D:
                break;
            case ChartType.RADAR:
                break;
            case ChartType.SCATTER:
                break;
            case ChartType.STOCK:
                break;
            case ChartType.SURFACE:
                break;
            case ChartType.SURFACE_3_D:
                break;
            default:
                break;
        }
    }

    /// <summary>
    /// Gets a flag indicating whether the specified chart type relates to a Word 2016 chart.
    /// </summary>
    private static boolean isChartExChart(/*ChartType*/int chartType)
    {
        switch (chartType)
        {
            case ChartType.TREEMAP:
            case ChartType.SUNBURST:
            case ChartType.HISTOGRAM:
            case ChartType.PARETO:
            case ChartType.BOX_AND_WHISKER:
            case ChartType.WATERFALL:
            case ChartType.FUNNEL:
                return true;
            default:
                return false;
        }
    }
}

