// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="DmlChartSeriesReader"/>.
/// </summary>
@Test
public class TestDmlChartSeriesReader
{
    @Test
    public void testLineChartSeries() throws Exception
    {
        String xml =
            "<c:ser xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:idx val=\"1\" />" +
                "<c:order val=\"1\" />" +
                "<c:tx>" +
                    "<c:strRef>" +
                        "<c:f>Sheet1!$C$1</c:f>" +
                        "<c:strCache><c:ptCount val=\"1\" /><c:pt idx=\"0\"><c:v>Series 2</c:v></c:pt></c:strCache>" +
                    "</c:strRef>" +
                "</c:tx>" +
                "<c:marker>" +
                    "<c:symbol val=\"square\" />" +
                    "<c:size val=\"42\" />" +
                    "<c:spPr>" +
                        "<a:blipFill dpi=\"0\" rotWithShape=\"1\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                            "<a:blip xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" r:embed=\"rId1\" />" +
                            "<a:srcRect />" +
                            "<a:tile tx=\"0\" ty=\"0\" sx=\"10000\" sy=\"10000\" flip=\"none\" algn=\"tl\" />" +
                        "</a:blipFill>" +
                    "</c:spPr>" +
                "</c:marker>" +
                "<c:dPt>" +
                    "<c:idx val=\"1\" />" +
                    "<c:marker>" +
                        "<c:symbol val=\"diamond\" />" +
                        "<c:size val=\"15\" />" +
                        "<c:spPr>" +
                            "<a:solidFill xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                                "<a:srgbClr val=\"1F497D\"><a:alpha val=\"50000\" /></a:srgbClr>" +
                            "</a:solidFill>" +
                            "<a:scene3d xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                                "<a:camera prst=\"orthographicFront\" />" +
                                "<a:lightRig rig=\"threePt\" dir=\"t\" />" +
                            "</a:scene3d>" +
                            "<a:sp3d xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"><a:bevelT /></a:sp3d>" +
                        "</c:spPr>" +
                    "</c:marker>" +
                    "<c:spPr>" +
                        "<a:ln cmpd=\"dbl\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                            "<a:solidFill><a:srgbClr val=\"FF0000\" /></a:solidFill>" +
                            "<a:prstDash val=\"sysDash\" />" +
                        "</a:ln>" +
                    "</c:spPr>" +
                "</c:dPt>" +
                "<c:cat>" +
                    "<c:strRef>" +
                        "<c:f>Sheet1!$A$2:$A$5</c:f>" +
                        "<c:strCache>" +
                            "<c:ptCount val=\"4\" />" +
                            "<c:pt idx=\"0\"><c:v>Category 1</c:v></c:pt>" +
                            "<c:pt idx=\"1\"><c:v>Category 2</c:v></c:pt>" +
                            "<c:pt idx=\"2\"><c:v>Category 3</c:v></c:pt>" +
                            "<c:pt idx=\"3\"><c:v>Category 4</c:v></c:pt>" +
                        "</c:strCache>" +
                    "</c:strRef>" +
                "</c:cat>" +
                "<c:val>" +
                    "<c:numRef>" +
                        "<c:f>Sheet1!$C$2:$C$5</c:f>" +
                        "<c:numCache>" +
                            "<c:formatCode>General</c:formatCode>" +
                            "<c:ptCount val=\"4\" />" +
                            "<c:pt idx=\"0\"><c:v>2.4</c:v></c:pt>" +
                            "<c:pt idx=\"1\"><c:v>4.4000000000000004</c:v></c:pt>" +
                            "<c:pt idx=\"2\"><c:v>1.8</c:v></c:pt>" +
                            "<c:pt idx=\"3\"><c:v>2.8</c:v></c:pt>" +
                        "</c:numCache>" +
                    "</c:numRef>" +
                "</c:val>" +
                "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</c:extLst> " +
            "</c:ser>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        // C++ workaround extend chart object lifetime
        DmlAreaChart chart = new DmlAreaChart();
        chart.setDocument(reader.getDocument());

        ChartSeries series = DmlChartSeriesReader.build(reader, chart, null);

        msAssert.isNotNull(series);

        msAssert.areEqual(1, series.getIndex());
        msAssert.areEqual(1, series.getOrder());
        msAssert.areEqual(-1, series.getDefaultDataPoint().getExplosion());
        msAssert.areEqual(false, series.getDefaultDataPoint().getInvertIfNegative());
        msAssert.areEqual(false, series.getDefaultDataPoint().getBubble3D());
        msAssert.areEqual(false, series.getSmooth());
        msAssert.areEqual(BarShape.NONE, series.getShape());
        msAssert.isNotNull(series.getSize());
        msAssert.isNull(series.getXErrorBars());
        msAssert.isNull(series.getYErrorBars());
        msAssert.isNotNull(series.getDefaultDataPoint().getMarker());
        msAssert.isFalse(series.hasDataLabels());
        msAssert.areEqual(0, series.getDataLabels().getCount());
        msAssert.isNotNull(series.getDataPoints().get(1));
        msAssert.isNull(series.getDefaultDataPoint().getPictureOptions());
        msAssert.isNotNull(series.getTrendlines());
        msAssert.areEqual(0, series.getTrendlines().size());
        msAssert.isNotNull(series.getTx());
        msAssert.isNotNull(series.getDefaultDataPoint().getSpPr());

        msAssert.isNotNull(series.getX());
        msAssert.isNotNull(series.getX().getValues());
        msAssert.areEqual(DmlChartValueType.STRING, series.getX().getValueType());
        verifyDataSourceValues(new Object[] { "Category 1", "Category 2", "Category 3", "Category 4" }, series.getX());

        msAssert.isNotNull(series.getY());
        msAssert.isNotNull(series.getY().getValues());
        msAssert.areEqual(DmlChartValueType.NUMERIC, series.getY().getValueType());
        verifyDataSourceValues(new Object[] { 2.4d, 4.4000000000000004d, 1.8d, 2.8d }, series.getY());

        msAssert.areEqual(1, ((IDmlExtensionListSource)series).getExtensions().getCount());
    }

    @Test
    public void testBarChartSeries() throws Exception
    {
        String xml =
            "<c:ser xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:idx val=\"2\" />" +
                "<c:order val=\"2\" />" +
                "<c:tx>" +
                    "<c:strRef>" +
                        "<c:f>Sheet1!$B$1</c:f>" +
                        "<c:strCache><c:ptCount val=\"1\" /><c:pt idx=\"0\"><c:v>Series 1</c:v></c:pt></c:strCache>" +
                     "</c:strRef>" +
                "</c:tx>" +
                "<c:dLbls>" +
                    "<c:txPr>" +
                        "<a:bodyPr rot=\"0\" vert=\"eaVert\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                        "<a:lstStyle xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                        "<a:p xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                            "<a:pPr><a:defRPr /></a:pPr><a:endParaRPr lang=\"en-US\" />" +
                        "</a:p>" +
                    "</c:txPr>" +
                    "<c:dLblPos val=\"outEnd\" />" +
                    "<c:showLegendKey val=\"1\" />" +
                    "<c:showSerName val=\"1\" />" +
                    "<c:separator>. </c:separator>" +
                "</c:dLbls>" +
                "<c:errBars>" +
                    "<c:errBarType val=\"both\" />" +
                    "<c:errValType val=\"stdErr\" />" +
                "</c:errBars>" +
                "<c:cat>" +
                    "<c:strRef>" +
                        "<c:f>Sheet1!$A$2:$A$5</c:f>" +
                        "<c:strCache>" +
                            "<c:ptCount val=\"4\" />" +
                            "<c:pt idx=\"0\"><c:v>Category 1</c:v></c:pt>" +
                            "<c:pt idx=\"1\"><c:v>Category 2</c:v></c:pt>" +
                            "<c:pt idx=\"2\"><c:v>Category 3</c:v></c:pt>" +
                            "<c:pt idx=\"3\"><c:v>Category 4</c:v></c:pt>" +
                        "</c:strCache>" +
                    "</c:strRef>" +
                "</c:cat>" +
                "<c:val>" +
                    "<c:numRef>" +
                        "<c:f>Sheet1!$B$2:$B$5</c:f>" +
                        "<c:numCache>" +
                            "<c:formatCode>General</c:formatCode>" +
                            "<c:ptCount val=\"4\" />" +
                            "<c:pt idx=\"0\"><c:v>4.3</c:v></c:pt>" +
                            "<c:pt idx=\"1\"><c:v>2.5</c:v></c:pt>" +
                            "<c:pt idx=\"2\"><c:v>3.5</c:v></c:pt>" +
                            "<c:pt idx=\"3\"><c:v>4.5</c:v></c:pt>" +
                        "</c:numCache>" +
                    "</c:numRef>" +
                "</c:val>" +
            "</c:ser>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        // C++ workaround extend chart object lifetime
        DmlAreaChart chart = new DmlAreaChart();
        chart.setDocument(reader.getDocument());

        ChartSeries series = DmlChartSeriesReader.build(reader, chart, null);

        msAssert.isNotNull(series);

        msAssert.areEqual(2, series.getIndex());
        msAssert.areEqual(2, series.getOrder());
        msAssert.areEqual(-1, series.getDefaultDataPoint().getExplosion());
        msAssert.areEqual(false, series.getDefaultDataPoint().getInvertIfNegative());
        msAssert.areEqual(false, series.getDefaultDataPoint().getBubble3D());
        msAssert.areEqual(false, series.getSmooth());
        msAssert.areEqual(BarShape.NONE, series.getShape());
        msAssert.isNotNull(series.getSize());
        msAssert.isNotNull(series.getYErrorBars());
        msAssert.isNotNull(series.getDefaultDataPoint().getMarker());
        msAssert.isTrue(series.hasDataLabels());
        msAssert.areEqual(4, series.getDataLabels().getCount());
        msAssert.isFalse(series.getDataPoints().hasCustomDataPoints());
        msAssert.isNull(series.getDefaultDataPoint().getPictureOptions());
        msAssert.isNotNull(series.getTrendlines());
        msAssert.areEqual(0, series.getTrendlines().size());
        msAssert.isNotNull(series.getTx());
        msAssert.isNotNull(series.getDefaultDataPoint().getSpPr());

        msAssert.isNotNull(series.getX());
        msAssert.isNotNull(series.getX().getValues());
        msAssert.areEqual(DmlChartValueType.STRING, series.getX().getValueType());
        verifyDataSourceValues(new Object[] { "Category 1", "Category 2", "Category 3", "Category 4" }, series.getX());

        msAssert.isNotNull(series.getY());
        msAssert.isNotNull(series.getY().getValues());
        msAssert.areEqual(DmlChartValueType.NUMERIC, series.getY().getValueType());
        verifyDataSourceValues(new Object[] { 4.3d, 2.5d, 3.5d, 4.5d }, series.getY());
    }

    private static void verifyDataSourceValues(Object[] expectedValues, DmlChartDimensionData data)
    {
        msAssert.areEqual(expectedValues.length, data.getValues().getValueCount());

        for (int i = 0; i < expectedValues.length; i++)
        {
            DmlChartValue val = data.getValue(i);
            if (val.getValueType() == DmlChartValueType.STRING)
                msAssert.areEqual(expectedValues[i], val.getStringValue());
            else
                msAssert.areEqual(expectedValues[i], ((DmlChartNumValue)val).getValue());
        }
    }
}

