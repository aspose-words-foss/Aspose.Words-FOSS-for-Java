// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/22/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;


/// <summary>
/// Class used to write DrawingML chart axis.
/// </summary>
/*static*/ class DmlChartAxisWriter
{
	/* Simulation of static class by using private constructor */
	private DmlChartAxisWriter()
	{}

    static void write(ChartAxis axis, DocxDocumentWriterBase writer) throws Exception
    {
        switch (axis.getType())
        {
            case ChartAxisType.CATEGORY:
            {
                if (axis.isDateCategoryAxis())
                    writeDateAxis(axis, writer);
                else
                    writeCategoryAxis(axis, writer);
                break;
            }
            case ChartAxisType.SERIES:
                writeSeriesAxis(axis, writer);
                break;
            case ChartAxisType.VALUE:
                writeValueAxis(axis, writer);
                break;
            default:
                throw new IllegalArgumentException("Unexpected axis type.");
        }
    }

    static void writeChartExAxis(ChartAxis axis, DocxDocumentWriterBase writer) throws Exception
    {
        DmlChartExChart chartEx = (DmlChartExChart)axis.getChart();

        if (chartEx.isStubAxis(axis.getAxId()))
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getTagName("axis", true));

        builder.writeAttribute("id", axis.getAxId());
        if (axis.getHidden())
            builder.writeAttribute("hidden", axis.getHidden());

        if (axis.isCategory())
            writeCategoryScaling(axis, builder);
        else
            writeValueScaling(axis, builder);

        DmlChartCommonWriter.writeDisplayUnits(axis.getDisplayUnit(), writer, true);

        writeAxisCommon(axis, writer, true);

        DmlExtensionListWriter.write(getTagPrefix(true), ((IDmlExtensionListSource)axis).getExtensions(), writer);

        builder.endElement(getTagName("axis", true));
    }

    /// <summary>
    /// Writes category axis scaling properties (the catScaling element of the 2.24.3.8 CT_CategoryAxisScaling
    /// complex type [MS-ODRAWXML]).
    /// </summary>
    private static void writeCategoryScaling(ChartAxis axis, DocxBuilder builder)
    {
        builder.startElement(getTagName("catScaling", true));

        DmlChartCommonWriter.writeDoubleOrAutomaticAttribute("gapWidth", axis.getScaling().getGapWidth(), builder);

        builder.endElement(getTagName("catScaling", true));
    }

    /// <summary>
    /// Writes category axis scaling properties (the valScaling element of the 2.24.3.82 CT_ValueAxisScaling
    /// complex type [MS-ODRAWXML]).
    /// </summary>
    private static void writeValueScaling(ChartAxis axis, DocxBuilder builder)
    {
        builder.startElement(getTagName("valScaling", true));

        if (axis.getScaling().getMaximumIsDefined())
            DmlChartCommonWriter.writeAxisBound("max", axis.getScaling().getMaximum(), builder);
        if (axis.getScaling().getMinimumIsDefined())
            DmlChartCommonWriter.writeAxisBound("min", axis.getScaling().getMinimum(), builder);
        DmlChartCommonWriter.writeDoubleOrAutomaticAttribute("majorUnit", axis.getMajorUnitPr(), builder);
        DmlChartCommonWriter.writeDoubleOrAutomaticAttribute("minorUnit", axis.getMinorUnitPr(), builder);

        builder.endElement(getTagName("valScaling", true));
    }

    private static void writeCategoryAxis(ChartAxis axis, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getTagName("catAx", false));

        writeAxisCommon(axis, writer, false);

        builder.writeElementWithAttributes(getTagName("auto", false), "val",
            axis.getCategoryType() == AxisCategoryType.AUTOMATIC);
        if (axis.getLblAlgn() != LabelAlignment.DEFAULT)
            builder.writeElementWithAttributes(getTagName("lblAlgn", false), "val", 
                DmlChartsEnum.labelAlignmentToDml(axis.getLblAlgn()));
        builder.writeElementWithAttributes(getTagName("lblOffset", false), "val", axis.getTickLabels().getOffset());
        if (!axis.getTickLabels().isAutoSpacing())
            builder.writeElementWithAttributes(getTagName("tickLblSkip", false), "val", axis.getTickLabels().getSpacing());
        if (!axis.getTickMarkSpacingIsAuto())
            builder.writeElementWithAttributes(getTagName("tickMarkSkip", false), "val", axis.getTickMarkSpacing());
        builder.writeElementWithAttributes(getTagName("noMultiLvlLbl", false), "val", axis.getNoMultiLvlLbl());

        DmlExtensionListWriter.write(getTagPrefix(false), ((IDmlExtensionListSource)axis).getExtensions(), writer);

        builder.endElement(getTagName("catAx", false));
    }

    private static void writeDateAxis(ChartAxis axis, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getTagName("dateAx", false));

        writeAxisCommon(axis, writer, false);

        DmlChartCommonWriter.writeWrappedBooleanIfTrue(getTagName("auto", false), 
            axis.getCategoryType() == AxisCategoryType.AUTOMATIC, builder);
        builder.writeElementWithAttributes(getTagName("lblOffset", false), "val", axis.getTickLabels().getOffset());
        builder.writeElementWithAttributes(getTagName("baseTimeUnit", false), "val", 
            DmlChartsEnum.timeUnitToDml(axis.getBaseTimeUnit()));
        if (!axis.getMajorUnitIsAuto())
            builder.writeElementWithAttributes(getTagName("majorUnit", false), "val", 
                FormatterPal.doubleToStrRoundtrip(axis.getMajorUnit()));
        builder.writeElementWithAttributes(getTagName("majorTimeUnit", false), "val", 
            DmlChartsEnum.timeUnitToDml(axis.getMajorUnitScale()));
        if (!axis.getMinorUnitIsAuto())
            builder.writeElementWithAttributes(getTagName("minorUnit", false), "val", 
                FormatterPal.doubleToStrRoundtrip(axis.getMinorUnit()));
        builder.writeElementWithAttributes(getTagName("minorTimeUnit", false), "val", 
            DmlChartsEnum.timeUnitToDml(axis.getMinorUnitScale()));

        DmlExtensionListWriter.write(getTagPrefix(false), ((IDmlExtensionListSource)axis).getExtensions(), writer);

        builder.endElement(getTagName("dateAx", false));
    }

    private static void writeSeriesAxis(ChartAxis axis, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getTagName("serAx", false));

        writeAxisCommon(axis, writer, false);

        if (!axis.getTickLabels().isAutoSpacing())
            builder.writeElementWithAttributes(getTagName("tickLblSkip", false), "val", axis.getTickLabels().getSpacing());
        if (!axis.getTickMarkSpacingIsAuto())
            builder.writeElementWithAttributes(getTagName("tickMarkSkip", false), "val", axis.getTickMarkSpacing());

        DmlExtensionListWriter.write(getTagPrefix(false), ((IDmlExtensionListSource)axis).getExtensions(), writer);

        builder.endElement(getTagName("serAx", false));
    }

    private static void writeValueAxis(ChartAxis axis, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getTagName("valAx", false));

        writeAxisCommon(axis, writer, false);

        builder.writeElementWithAttributes(getTagName("crossBetween", false), "val", 
            DmlChartsEnum.crossBetweenToDml(axis.getCrossBetween()));
       
        if (!axis.getMajorUnitIsAuto())
            builder.writeElementWithAttributes(getTagName("majorUnit", false), "val", 
                FormatterPal.doubleToStrRoundtrip(axis.getMajorUnit()));

        if (!axis.getMinorUnitIsAuto())
            builder.writeElementWithAttributes(getTagName("minorUnit", false), "val", 
                FormatterPal.doubleToStrRoundtrip(axis.getMinorUnit()));

        DmlChartCommonWriter.writeDisplayUnits(axis.getDisplayUnit(), writer, false);

        DmlExtensionListWriter.write(getTagPrefix(false), ((IDmlExtensionListSource)axis).getExtensions(), writer);

        builder.endElement(getTagName("valAx", false));
    }

    private static void writeAxisCommon(ChartAxis axis, DocxDocumentWriterBase writer, boolean isChartEx) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        if (!isChartEx)
        {
            builder.writeElementWithAttributes(getTagName("axId", isChartEx), "val", axis.getAxId());
            DmlChartCommonWriter.writeScaling(axis.getScaling(), writer, isChartEx);
            builder.writeElementWithAttributes(getTagName("delete", isChartEx), "val", axis.getHidden());
            builder.writeElementWithAttributes(getTagName("axPos", isChartEx), "val", DmlChartsEnum.axisPositionToDml(axis.getAxPos()));
        }
        DmlChartCommonWriter.writeGridlines("majorGridlines", axis.getMajorGridlines(), writer, isChartEx);
        DmlChartCommonWriter.writeGridlines("minorGridlines", axis.getMinorGridlines(), writer, isChartEx);
        DmlChartCommonWriter.writeTitle(axis.getTitleInternal(), writer, ((IDmlChartTitleHolder)axis).getTitleDeleted(), isChartEx, true);
        DmlChartCommonWriter.writeNumberFormat(axis.getNumFmt(), builder, isChartEx);

        DmlChartCommonWriter.writeTickMarks(isChartEx ? "majorTickMarks" : "majorTickMark", 
            axis.getMajorTickMark(), axis.getMajorTickMarkExtensions(), writer, isChartEx);
        DmlChartCommonWriter.writeTickMarks(isChartEx ? "minorTickMarks" : "minorTickMark", 
            axis.getMinorTickMark(), axis.getMinorTickMarkExtensions(), writer, isChartEx);

        if (isChartEx)
        {
            if (axis.getAreTickLabelsVisible() || 
                (axis.getTickLabelExtensions() != null && axis.getTickLabelExtensions().getCount() > 0))
            {
                builder.startElement(getTagName("tickLabels", isChartEx));
                DmlExtensionListWriter.write(getTagPrefix(isChartEx), axis.getTickLabelExtensions(), writer);
                builder.endElement(getTagName("tickLabels", isChartEx));
            }
        }
        else
        {
            Object tickLblPos = axis.getChartAxisPr().getDirectProperty(DmlChartAxisAttrs.TICK_LBL_POS);
            if (tickLblPos != null)
                builder.writeElementWithAttributes(getTagName("tickLblPos", isChartEx), "val",
                    DmlChartsEnum.tickLabelPositionToDml((/*AxisTickLabelPosition*/Integer)tickLblPos));
        }
      
        DmlChartCommonWriter.writeShapeProperties(axis.getSpPr(), writer, isChartEx);
        DmlChartTxPr axisTxPr = (DmlChartTxPr)axis.getChartAxisPr().getProperty(DmlChartAxisAttrs.TX_PR);
        DmlChartCommonWriter.writeTextProperties(axisTxPr, writer, isChartEx);

        if (!isChartEx)
        {
            builder.writeElementWithAttributes(getTagName("crossAx", isChartEx), "val", axis.getCrossAx());

            if (axis.getCrosses() == AxisCrosses.CUSTOM)
            {
                builder.writeElementWithAttributes(getTagName("crossesAt", isChartEx), "val",
                    FormatterPal.doubleToStr(axis.getCrossesAt()));
            }
            else
            {
                builder.writeElementWithAttributes(getTagName("crosses", isChartEx), "val",
                    DmlChartsEnum.crossesToDml(axis.getCrosses()));
            }
        }
    }

    /// <summary>
    /// Generates a tag name with a prefix depending on chart type.
    /// </summary>
    /// <param name="localName">Name without prefix.</param>
    /// <param name="isChartEx">A flag indicating that the chart is of the chartEx schema.</param>
    /// <returns>Tag name with prefix.</returns>
    private static String getTagName(String localName, boolean isChartEx)
    {
        return DmlChartCommonWriter.getTagName(localName, isChartEx);
    }

    /// <summary>
    /// Gets a tag prefix depending on chart type.
    /// </summary>
    private static String getTagPrefix(boolean isChartEx)
    {
        return DmlChartCommonWriter.getTagPrefix(isChartEx);
    }
}

