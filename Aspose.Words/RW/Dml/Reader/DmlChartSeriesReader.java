// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ObjToIntDictionary;
import com.aspose.ms.System.msString;


/// <summary>
/// Class is used to read series of charts.
/// 5.7.2.168 ser (Bubble Chart Series)
/// 5.7.2.169 ser (Line Chart Series)
/// 5.7.2.170 ser (Pie Chart Series)
/// 5.7.2.171 ser (Surface Chart Series)
/// 5.7.2.172 ser (Scatter Chart Series)
/// 5.7.2.173 ser (Radar Chart Series)
/// 5.7.2.174 ser (Area Chart Series)
/// 5.7.2.175 ser (Bar Chart Series)
/// </summary>
class DmlChartSeriesReader extends DmlReaderBase
{
    private DmlChartSeriesReader()
    {
    }

    static ChartSeries build(DocxDocumentReaderBase reader, DmlChart chart, ObjToIntDictionary<ChartSeries> ownerMap) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        ChartSeries series = new ChartSeries(chart);
        String tagName = xmlReader.getLocalName();

        if (msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false)))
        {
            while (xmlReader.moveToNextAttribute())
            {
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"layoutId"*/0:
                        series.setLayoutType(DmlChartsEnum.dmlToSeriesLayout(xmlReader.getValue()));
                        break;
                    case /*"hidden"*/1:
                        series.setHidden(xmlReader.getValueAsBool());
                        break;
                    case /*"ownerIdx"*/2:
                    {
                        int index = xmlReader.getValueAsInt();
                        if (index < chart.getSeries().getCount())
                            series.setOwner(chart.getSeries().get(index));
                        else
                            ownerMap.add(series, index);
                        break;
                    }
                    case /*"uniqueId"*/3:
                        series.setUniqueId(xmlReader.getValue());
                        break;
                    case /*"formatIdx"*/4:
                        series.setFormatIndex(xmlReader.getValueAsInt());
                        break;
                    default:
                        warnUnexpected(xmlReader);
                        break;
                }
            }

            xmlReader.moveToElement();
        }

        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"idx"*/5:
                    series.setIndex(xmlReader.readIntAttribute(0));
                    break;
                case /*"order"*/6:
                    series.setOrder(xmlReader.readIntAttribute(0));
                    break;
                case /*"explosion"*/7:
                    series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.EXPLOSION, xmlReader.readIntAttribute(0));
                    break;
                case /*"invertIfNegative"*/8:
                    series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.INVERT_IF_NEGATIVE, xmlReader.readBoolAttribute(true));
                    break;
                case /*"bubble3D"*/9:
                    series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.BUBBLE_3_D, xmlReader.readBoolAttribute(true));
                    break;
                case /*"smooth"*/10:
                    series.setSmooth(xmlReader.readBoolAttribute(true));
                    break;
                case /*"shape"*/11:
                    series.setShape(DmlChartsEnum.dmlToShape(xmlReader.readAttribute("")));
                    break;
                case /*"cat"*/12:
                case /*"xVal"*/13:
                    DmlChartComplexTypesReader.readChartDataSource(reader, series.getX().getDataSource());
                    break;
                case /*"val"*/14:
                case /*"yVal"*/15:
                    DmlChartComplexTypesReader.readChartDataSource(reader, series.getY().getDataSource());
                    break;
                case /*"bubbleSize"*/16:
                    DmlChartComplexTypesReader.readChartDataSource(reader, series.getSize().getDataSource());
                    break;
                case /*"errBars"*/17:
                    series.addErrorBars(DmlChartComplexTypesReader.readChartErrorBars(reader));
                    break;
                case /*"marker"*/18:
                {
                    ChartMarker marker = new ChartMarker(chart);
                    DmlChartComplexTypesReader.readChartMarker(reader, marker);
                    series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.MARKER, marker);
                    break;
                }
                case /*"dLbls"*/19:
                case /*"dataLabels"*/20: // in the 2.24.3.70 CT_Series type [MS-ODRAWXML]
                    series.setDataLabels(DmlChartComplexTypesReader.readChartDataLabelCollection(reader, chart, series));
                    break;
                case /*"dPt"*/21:
                case /*"dataPt"*/22: // in the 2.24.3.70 CT_Series type [MS-ODRAWXML]
                    series.getDataPoints().addDataPoint(DmlChartComplexTypesReader.readChartDataPoint(reader, chart));
                    break;
                case /*"pictureOptions"*/23:
                    series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.PICTURE_OPTIONS, DmlChartComplexTypesReader.readChartPictureOptions(xmlReader));
                    break;
                case /*"trendline"*/24:
                    series.addTrendline(DmlChartComplexTypesReader.readChartTrendline(reader));
                    break;
                case /*"tx"*/25:
                    series.setTx(DmlChartComplexTypesReader.readChartTx(reader));
                    break;
                case /*"spPr"*/26:
                {
                    DmlChartSpPr spPr = new DmlChartSpPr();
                    DmlChartComplexTypesReader.readChartSpPr(reader, spPr);
                    series.getDefaultDataPoint().getPointPr().setProperty(DmlChartDataPointAttr.SP_PR, spPr);
                    break;
                }
                case /*"valueColors"*/27: // in the 2.24.3.70 CT_Series type [MS-ODRAWXML]
                    series.setValueColors(DmlChartComplexTypesReader.readValueColors(reader));
                    break;
                case /*"dataId"*/28: // in the 2.24.3.70 CT_Series type [MS-ODRAWXML]
                    series.setDataId(xmlReader.readIntAttribute(-1));
                    break;
                case /*"axisId"*/29: // in the 2.24.3.70 CT_Series type [MS-ODRAWXML]
                    series.setAxisId(xmlReader.readIntAttribute(-1));
                    break;
                case /*"layoutPr"*/30: // in the 2.24.3.70 CT_Series type [MS-ODRAWXML]
                    DmlChartComplexTypesReader.readLayoutProperties(reader, series.getLayoutPr());
                    break;
                case /*"extLst"*/31:
                    ((IDmlExtensionListSource)series).setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        normalizeDataPointInvertIfNegative(series);

        series.endReading();

        return series;
    }

    /// <summary>
    /// Normalizes the <see cref="ChartDataPoint.InvertIfNegative"/> property values of materialized data points of
    /// the series.
    /// </summary>
    private static void normalizeDataPointInvertIfNegative(ChartSeries series)
    {
        // 1. The "invertIfNegative" property does not get value from the parent collection (series) in MS Word. Thus,
        // being defined for a data point collection (series), the same value has to be written for all materialized
        // data points that should have the same property value. So, let's collapse the value here so that changing
        // the property in the collection affects such data points too.
        // 2. If the property is not defined in a data point but is defined in the collection (series), and if the
        // value is different than the default value of the property, we need put the default value to the data point.

        DmlChartDataPointPr seriesPointPr = series.getDefaultDataPoint().getPointPr();
        if (!seriesPointPr.isPropertySpecified(DmlChartDataPointAttr.INVERT_IF_NEGATIVE))
            return;

        boolean seriesValue = series.getInvertIfNegative();
        // The default value depends on a document version.
        boolean defaultValue = (/*boolean*/Boolean)seriesPointPr.getInheritedProperty(DmlChartDataPointAttr.INVERT_IF_NEGATIVE);

        for (ChartDataPoint dataPoint : series.getDataPoints().getMaterializedDataPoints())
        {
            if (dataPoint.getPointPr().isPropertySpecified(DmlChartDataPointAttr.INVERT_IF_NEGATIVE))
            {
                if (dataPoint.getInvertIfNegative() == seriesValue)
                    dataPoint.getPointPr().removeProperty(DmlChartDataPointAttr.INVERT_IF_NEGATIVE);
            }
            else
            {
                if (seriesValue != defaultValue)
                    dataPoint.setInvertIfNegative(defaultValue);
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"layoutId",
		"hidden",
		"ownerIdx",
		"uniqueId",
		"formatIdx",
		"idx",
		"order",
		"explosion",
		"invertIfNegative",
		"bubble3D",
		"smooth",
		"shape",
		"cat",
		"xVal",
		"val",
		"yVal",
		"bubbleSize",
		"errBars",
		"marker",
		"dLbls",
		"dataLabels",
		"dPt",
		"dataPt",
		"pictureOptions",
		"trendline",
		"tx",
		"spPr",
		"valueColors",
		"dataId",
		"axisId",
		"layoutPr",
		"extLst"
	);

}

