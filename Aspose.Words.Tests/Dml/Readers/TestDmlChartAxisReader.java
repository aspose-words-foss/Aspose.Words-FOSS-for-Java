// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTime;


/// <summary>
/// Tests for <see cref="DmlChartAxisReader"/>.
/// </summary>
@Test
public class TestDmlChartAxisReader
{
    @Test
    public void testReadCatAx() throws Exception
    {
        String xml =
          "<c:catAx xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
            "<c:axId val=\"122199040\" />" +
            "<c:scaling>" +
              "<c:orientation val=\"minMax\" />" +
            "</c:scaling>" +
            "<c:axPos val=\"t\" />" +
            "<c:majorGridlines />" +
            "<c:title>" +
              "<c:tx>" +
                "<c:rich>" +
                  "<a:bodyPr xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                  "<a:lstStyle xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                  "<a:p xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                    "<a:pPr>" +
                      "<a:defRPr />" +
                    "</a:pPr>" +
                    "<a:r>" +
                      "<a:rPr lang=\"en-US\" i=\"1\" strike=\"sngStrike\" />" +
                      "<a:t>Axis Title</a:t>" +
                    "</a:r>" +
                  "</a:p>" +
                "</c:rich>" +
              "</c:tx>" +
            "</c:title>" +
            "<c:tickLblPos val=\"nextTo\" />" +
            "<c:crossAx val=\"122958976\" />" +
            "<c:crosses val=\"autoZero\" />" +
            "<c:auto val=\"1\" />" +
            "<c:lblAlgn val=\"ctr\" />" +
            "<c:lblOffset val=\"10\" />" +
            "<c:extLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                    "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                        "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                        "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                "</a:ext> " +
            "</c:extLst> " +
          "</c:catAx>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        ChartAxis axis = DmlChartAxisReader.read(reader, null, false);

        msAssert.isNotNull(axis);
        msAssert.areEqual(ChartAxisType.CATEGORY, axis.getType());

        // Check simple properties.
        msAssert.areEqual(AxisCategoryType.AUTOMATIC, axis.getCategoryType());
        msAssert.isFalse(axis.isDateCategoryAxis());
        msAssert.areEqual(122199040, axis.getAxId());
        msAssert.areEqual(AxisPosition.TOP, axis.getAxPos());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getBaseTimeUnit());
        msAssert.areEqual(122958976, axis.getCrossAx());
        msAssert.areEqual(CrossBetween.BETWEEN, axis.getCrossBetween());
        msAssert.areEqual(AxisCrosses.AUTOMATIC, axis.getCrosses());
        msAssert.areEqual(false, axis.getHidden());
        msAssert.areEqual(LabelAlignment.CENTER, axis.getLblAlgn());
        msAssert.areEqual(10, axis.getTickLabels().getOffset());
        msAssert.areEqual(AxisTickMark.OUTSIDE, axis.getMajorTickMark());
        msAssert.areEqual(AxisTickMark.NONE, axis.getMinorTickMark());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getMajorUnitScale());
        msAssert.isTrue(axis.getMajorUnitIsAuto());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getMinorUnitScale());
        msAssert.isTrue(axis.getMinorUnitIsAuto());
        msAssert.areEqual(false, axis.getNoMultiLvlLbl());
        msAssert.areEqual(AxisTickLabelPosition.NEXT_TO_AXIS, axis.getTickLabels().getPosition());
        msAssert.isTrue(axis.getTickLabels().isAutoSpacing());
        msAssert.isTrue(axis.getTickMarkSpacingIsAuto());

        // Check complex properties.
        msAssert.areEqual(AxisBuiltInUnit.NONE, axis.getDisplayUnit().getUnit());
        msAssert.isNotNull(axis.getMajorGridlines());
        msAssert.isNull(axis.getMinorGridlines());
        msAssert.isNull(axis.getNumFmt());
        msAssert.isNotNull(axis.getScaling());
        msAssert.areEqual(AxisOrientation.MIN_MAX, axis.getScaling().getOrientation());
        msAssert.isNotNull(axis.getSpPr());
        msAssert.isNotNull(axis.getTitleInternal());
        msAssert.isNotNull(axis.getTitleInternal().getTx());
        msAssert.areEqual(DmlChartTxType.CHART_TEXT, axis.getTitleInternal().getTx().getTxType());
        msAssert.isNotNull(axis.getTxPr());

        msAssert.areEqual(1, ((IDmlExtensionListSource)axis).getExtensions().getCount());
    }

    @Test
    public void testReadDateAx() throws Exception
    {
        String xml =
            "<c:dateAx xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:axId val=\"122579200\" />" +
                "<c:scaling><c:orientation val=\"minMax\" /></c:scaling>" +
                "<c:axPos val=\"b\" />" +
                "<c:numFmt formatCode=\"dd/mm/yyyy\" sourceLinked=\"1\" />" +
                "<c:tickLblPos val=\"nextTo\" />" +
                "<c:crossAx val=\"124649472\" />" +
                "<c:crosses val=\"autoZero\" />" +
                "<c:auto val=\"1\" />" +
                "<c:lblOffset val=\"100\" />" +
            "</c:dateAx>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        ChartAxis axis = DmlChartAxisReader.read(reader, null, false);

        msAssert.isNotNull(axis);
        msAssert.areEqual(ChartAxisType.CATEGORY, axis.getType());

        // Check simple properties.
        msAssert.areEqual(AxisCategoryType.AUTOMATIC, axis.getCategoryType());
        msAssert.isTrue(axis.isDateCategoryAxis());
        msAssert.areEqual(122579200, axis.getAxId());
        msAssert.areEqual(AxisPosition.BOTTOM, axis.getAxPos());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getBaseTimeUnit());
        msAssert.areEqual(124649472, axis.getCrossAx());
        msAssert.areEqual(CrossBetween.BETWEEN, axis.getCrossBetween());
        msAssert.areEqual(AxisCrosses.AUTOMATIC, axis.getCrosses());
        msAssert.areEqual(false, axis.getHidden());
        msAssert.areEqual(LabelAlignment.DEFAULT, axis.getLblAlgn());
        msAssert.areEqual(100, axis.getTickLabels().getOffset());
        msAssert.areEqual(AxisTickMark.OUTSIDE, axis.getMajorTickMark());
        msAssert.areEqual(AxisTickMark.NONE, axis.getMinorTickMark());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getMajorUnitScale());
        msAssert.isTrue(axis.getMajorUnitIsAuto());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getMinorUnitScale());
        msAssert.isTrue(axis.getMinorUnitIsAuto());
        msAssert.areEqual(false, axis.getNoMultiLvlLbl());
        msAssert.areEqual(AxisTickLabelPosition.NEXT_TO_AXIS, axis.getTickLabels().getPosition());
        msAssert.isTrue(axis.getTickLabels().isAutoSpacing());
        msAssert.isTrue(axis.getTickMarkSpacingIsAuto());

        // Check complex properties.
        msAssert.areEqual(AxisBuiltInUnit.NONE, axis.getDisplayUnit().getUnit());
        msAssert.isNull(axis.getMajorGridlines());
        msAssert.isNull(axis.getMinorGridlines());
        msAssert.isNotNull(axis.getNumFmt());
        msAssert.areEqual("dd/mm/yyyy", axis.getNumFmt().getFormatCode());
        msAssert.isTrue(axis.getNumFmt().getSourceLinked());
        msAssert.isNotNull(axis.getScaling());
        msAssert.areEqual(AxisOrientation.MIN_MAX, axis.getScaling().getOrientation());
        msAssert.isNotNull(axis.getSpPr());
        msAssert.isNull(axis.getTitleInternal());
        msAssert.isNotNull(axis.getTxPr());
    }

    @Test
    public void testReadSerAx() throws Exception
    {
        String xml =
            "<c:serAx xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:axId val=\"124911616\" />" +
                "<c:scaling><c:orientation val=\"minMax\" /></c:scaling>" +
                "<c:axPos val=\"b\" />" +
                "<c:tickLblPos val=\"nextTo\" />" +
                "<c:crossAx val=\"124909440\" />" +
                "<c:crosses val=\"autoZero\" />" +
            "</c:serAx>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        ChartAxis axis = DmlChartAxisReader.read(reader, null, false);

        msAssert.isNotNull(axis);
        msAssert.areEqual(ChartAxisType.SERIES, axis.getType());

        // Check simple properties.
        msAssert.areEqual(124911616, axis.getAxId());
        msAssert.areEqual(AxisPosition.BOTTOM, axis.getAxPos());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getBaseTimeUnit());
        msAssert.areEqual(124909440, axis.getCrossAx());
        msAssert.areEqual(CrossBetween.BETWEEN, axis.getCrossBetween());
        msAssert.areEqual(AxisCrosses.AUTOMATIC, axis.getCrosses());
        msAssert.areEqual(false, axis.getHidden());
        msAssert.areEqual(LabelAlignment.DEFAULT, axis.getLblAlgn());
        msAssert.areEqual(100, axis.getTickLabels().getOffset());
        msAssert.areEqual(AxisTickMark.OUTSIDE, axis.getMajorTickMark());
        msAssert.areEqual(AxisTickMark.NONE, axis.getMinorTickMark());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getMajorUnitScale());
        msAssert.isTrue(axis.getMajorUnitIsAuto());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getMinorUnitScale());
        msAssert.isTrue(axis.getMinorUnitIsAuto());
        msAssert.areEqual(false, axis.getNoMultiLvlLbl());
        msAssert.areEqual(AxisTickLabelPosition.NEXT_TO_AXIS, axis.getTickLabels().getPosition());
        msAssert.isTrue(axis.getTickLabels().isAutoSpacing());
        msAssert.isTrue(axis.getTickMarkSpacingIsAuto());

        // Check complex properties.
        msAssert.areEqual(AxisBuiltInUnit.NONE, axis.getDisplayUnit().getUnit());
        msAssert.isNull(axis.getMajorGridlines());
        msAssert.isNull(axis.getMinorGridlines());
        msAssert.isNull(axis.getNumFmt());
        msAssert.isNotNull(axis.getScaling());
        msAssert.areEqual(AxisOrientation.MIN_MAX, axis.getScaling().getOrientation());
        msAssert.isNotNull(axis.getSpPr());
        msAssert.isNull(axis.getTitleInternal());
        msAssert.isNotNull(axis.getTxPr());
    }

    @Test
    public void testReadValAx() throws Exception
    {
        String xml =
            "<c:valAx xmlns:c=\"http://schemas.openxmlformats.org/drawingml/2006/chart\">" +
                "<c:axId val=\"122958976\" />" +
                "<c:scaling>" +
                    "<c:logBase val=\"2\" />" +
                    "<c:orientation val=\"maxMin\" />" +
                    "<c:max val=\"6\" />" +
                    "<c:min val=\"1\" />" +
                "</c:scaling>" +
                "<c:axPos val=\"l\" />" +
                "<c:majorGridlines />" +
                "<c:minorGridlines />" +
                "<c:title>" +
                    "<c:tx>" +
                        "<c:rich>" +
                            "<a:bodyPr rot=\"-5400000\" vert=\"horz\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                            "<a:lstStyle xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                            "<a:p xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                                "<a:pPr><a:defRPr /></a:pPr>" +
                                "<a:r><a:rPr lang=\"en-US\" i=\"1\" /><a:t>Axis Title</a:t></a:r>" +
                            "</a:p>" +
                        "</c:rich>" +
                    "</c:tx>" +
                    "<c:layout>" +
                        "<c:manualLayout>" +
                            "<c:xMode val=\"edge\" />" +
                            "<c:yMode val=\"edge\" />" +
                            "<c:x val=\"6.9444444444444489E-2\" />" +
                            "<c:y val=\"0.51687664041994752\" />" +
                        "</c:manualLayout>" +
                    "</c:layout>" +
                "</c:title>" +
                "<c:numFmt formatCode=\"#,##0.00[$$-C09]\" sourceLinked=\"0\" />" +
                "<c:majorTickMark val=\"in\" />" +
                "<c:tickLblPos val=\"low\" />" +
                "<c:txPr>" +
                    "<a:bodyPr xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                    "<a:lstStyle xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" />" +
                    "<a:p xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                        "<a:pPr>" +
                            "<a:defRPr sz=\"1400\" b=\"1\" i=\"1\" baseline=\"0\">" +
                                "<a:solidFill><a:schemeClr val=\"accent6\" /></a:solidFill>" +
                                "<a:latin typeface=\"Arial\" pitchFamily=\"34\" charset=\"0\" />" +
                            "</a:defRPr>" +
                        "</a:pPr>" +
                        "<a:endParaRPr lang=\"en-US\" />" +
                    "</a:p>" +
                "</c:txPr>" +
                "<c:crossAx val=\"122199040\" />" +
                "<c:crosses val=\"autoZero\" />" +
                "<c:crossBetween val=\"between\" />" +
                "<c:majorUnit val=\"1\" />" +
                "<c:dispUnits>" +
                    "<c:builtInUnit val=\"hundreds\" />" +
                    "<c:dispUnitsLbl />" +
                "</c:dispUnits>" +
            "</c:valAx>";

        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        ChartAxis axis = DmlChartAxisReader.read(reader, null, false);

        msAssert.isNotNull(axis);
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());

        // Check simple properties.
        msAssert.areEqual(122958976, axis.getAxId());
        msAssert.areEqual(AxisPosition.LEFT, axis.getAxPos());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getBaseTimeUnit());
        msAssert.areEqual(122199040, axis.getCrossAx());
        msAssert.areEqual(CrossBetween.BETWEEN, axis.getCrossBetween());
        msAssert.areEqual(AxisCrosses.AUTOMATIC, axis.getCrosses());
        msAssert.areEqual(false, axis.getHidden());
        msAssert.areEqual(LabelAlignment.DEFAULT, axis.getLblAlgn());
        msAssert.areEqual(100, axis.getTickLabels().getOffset());
        msAssert.areEqual(AxisTickMark.INSIDE, axis.getMajorTickMark());
        msAssert.areEqual(AxisTickMark.NONE, axis.getMinorTickMark());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getMajorUnitScale());
        msAssert.areEqual(1, axis.getMajorUnit());
        msAssert.areEqual(AxisTimeUnit.AUTOMATIC, axis.getMinorUnitScale());
        msAssert.isTrue(axis.getMinorUnitIsAuto());
        msAssert.areEqual(false, axis.getNoMultiLvlLbl());
        msAssert.areEqual(AxisTickLabelPosition.LOW, axis.getTickLabels().getPosition());
        msAssert.isTrue(axis.getTickLabels().isAutoSpacing());
        msAssert.isTrue(axis.getTickMarkSpacingIsAuto());

        // Check complex properties.
        msAssert.areEqual(AxisBuiltInUnit.HUNDREDS, axis.getDisplayUnit().getUnit());
        msAssert.isNotNull(axis.getMajorGridlines());
        msAssert.isNotNull(axis.getMinorGridlines());
        msAssert.isNotNull(axis.getNumFmt());
        msAssert.areEqual("#,##0.00[$$-C09]", axis.getNumFmt().getFormatCode());
        msAssert.isFalse(axis.getNumFmt().getSourceLinked());
        msAssert.isNotNull(axis.getScaling());
        msAssert.areEqual(AxisOrientation.MAX_MIN, axis.getScaling().getOrientation());
        msAssert.areEqual(AxisScaleType.LOGARITHMIC, axis.getScaling().getType());
        msAssert.areEqual(2, axis.getScaling().getLogBase());
        msAssert.areEqual(new AxisBound(1.0), axis.getScaling().getMinimum());
        msAssert.areEqual(new AxisBound(6.0), axis.getScaling().getMaximum());
        msAssert.isNotNull(axis.getSpPr());
        msAssert.isNotNull(axis.getTitleInternal());
        msAssert.isNotNull(axis.getTitleInternal().getTx());
        msAssert.areEqual(DmlChartTxType.CHART_TEXT, axis.getTitleInternal().getTx().getTxType());
        msAssert.isNotNull(axis.getTxPr());
    }

    /// <summary>
    /// Tests that axis public properties have the same values as in Word interface.
    /// </summary>
    @Test
    public void testPublicProperties() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestAxisPublicProperties.docx");

        // Chart 0
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        msAssert.areNotEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        ChartAxis axis = chart.getAxisX();
        msAssert.areEqual(ChartAxisType.CATEGORY, axis.getType());
        msAssert.areEqual(AxisCategoryType.TIME, axis.getCategoryType());
        msAssert.areEqual(AxisCrosses.CUSTOM, axis.getCrosses());
        msAssert.areEqual(100, axis.getCrossesAt());
        msAssert.isFalse(axis.getReverseOrder());
        msAssert.isTrue(axis.getMajorUnitIsAuto());
        msAssert.isTrue(axis.getMinorUnitIsAuto());
        msAssert.areEqual("[$-1409]dddd\\,\\ d\\ mmmm\\ yyyy;@", axis.getNumberFormat().getFormatCode());
        AxisScaling scaling = axis.getScaling();
        msAssert.areEqual(AxisScaleType.LINEAR, scaling.getType());
        msAssert.isTrue(scaling.getMinimum().isAuto());
        msAssert.isTrue(scaling.getMaximum().isAuto());

        axis = chart.getAxisY();
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());
        msAssert.areEqual(AxisCrosses.CUSTOM, axis.getCrosses());
        DateTime baseDate = new DateTime(1899, 12, 30);
        msAssert.areEqual(DateTime.subtract((new DateTime(2002, 6, 1)),baseDate).getDays(), axis.getCrossesAt());
        msAssert.isTrue(axis.getAxisBetweenCategories());
        scaling = axis.getScaling();
        msAssert.areEqual(AxisScaleType.LINEAR, scaling.getType());
        msAssert.isFalse(scaling.getMinimum().isAuto());
        msAssert.areEqual(new AxisBound(-180), scaling.getMinimum());
        msAssert.isFalse(scaling.getMaximum().isAuto());
        msAssert.areEqual(new AxisBound(240.0), scaling.getMaximum());

        msAssert.isNull(chart.getAxisZ());

        // Chart 1 (ChartEx)
        chart = doc.getFirstSection().getBody().getShapes().get(1).getChart();
        msAssert.areEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        axis = chart.getAxisX();
        msAssert.areEqual(ChartAxisType.CATEGORY, axis.getType());
        msAssert.areEqual(AxisCategoryType.CATEGORY, axis.getCategoryType());
        msAssert.areEqual(AxisCrosses.AUTOMATIC, axis.getCrosses());
        msAssert.areEqual(AxisTickMark.OUTSIDE, axis.getMajorTickMark());
        msAssert.areEqual(AxisTickMark.INSIDE, axis.getMinorTickMark());

        axis = chart.getAxisY();
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());
        msAssert.areEqual("#,##0.0", axis.getNumberFormat().getFormatCode());

        // Chart 2
        chart = doc.getFirstSection().getBody().getShapes().get(2).getChart();
        msAssert.areNotEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        axis = chart.getAxisX();
        msAssert.areEqual(ChartAxisType.CATEGORY, axis.getType());
        msAssert.areEqual(500, axis.getTickLabels().getOffset());

        axis = chart.getAxisY();
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());
        msAssert.areEqual(2.5, axis.getMajorUnit());
        msAssert.areEqual(1.0, axis.getMinorUnit());

        axis = chart.getAxisZ();
        msAssert.areEqual(ChartAxisType.SERIES, axis.getType());
        msAssert.areEqual(AxisCrosses.AUTOMATIC, axis.getCrosses());
        msAssert.areEqual(AxisTickLabelPosition.HIGH, axis.getTickLabels().getPosition());

        // Chart 3
        chart = doc.getFirstSection().getBody().getShapes().get(3).getChart();
        msAssert.areNotEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        axis = chart.getAxisX();
        msAssert.areEqual(ChartAxisType.CATEGORY, axis.getType());
        msAssert.areEqual(AxisCrosses.MAXIMUM, axis.getCrosses());
        msAssert.areEqual(AxisTickLabelPosition.LOW, axis.getTickLabels().getPosition());

        axis = chart.getAxisY();
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());
        msAssert.areEqual(AxisCrosses.MAXIMUM, axis.getCrosses());

        // Chart 4
        chart = doc.getFirstSection().getBody().getShapes().get(4).getChart();
        msAssert.areNotEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        msAssert.isNull(chart.getAxisX());
        msAssert.isNull(chart.getAxisY());
        msAssert.isNull(chart.getAxisZ());

        // Chart 5
        chart = doc.getFirstSection().getBody().getShapes().get(5).getChart();
        msAssert.areNotEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        axis = chart.getAxisX();
        msAssert.areEqual(ChartAxisType.CATEGORY, axis.getType());
        msAssert.isTrue(axis.getReverseOrder());

        axis = chart.getAxisY();
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());
        msAssert.areEqual(AxisCrosses.AUTOMATIC, axis.getCrosses());
        msAssert.areEqual(AxisScaleType.LOGARITHMIC, axis.getScaling().getType());
        msAssert.areEqual(4, axis.getScaling().getLogBase());

        // Chart 6
        chart = doc.getFirstSection().getBody().getShapes().get(6).getChart();
        msAssert.areNotEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        axis = chart.getAxisX();
        msAssert.areEqual(ChartAxisType.CATEGORY, axis.getType());
        msAssert.areEqual(AxisCategoryType.AUTOMATIC, axis.getCategoryType());
        msAssert.isTrue(axis.isDateCategoryAxis());
        msAssert.areEqual(2, axis.getMajorUnit());
        msAssert.areEqual(AxisTimeUnit.MONTHS, axis.getMajorUnitScale());
        msAssert.areEqual(1, axis.getMinorUnit());
        msAssert.areEqual(AxisTimeUnit.MONTHS, axis.getMinorUnitScale());
        msAssert.areEqual(AxisTimeUnit.MONTHS, axis.getBaseTimeUnit());

        axis = chart.getAxisY();
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());
        msAssert.areEqual(AxisTickMark.CROSS, axis.getMajorTickMark());
        msAssert.areEqual(AxisTickMark.OUTSIDE, axis.getMinorTickMark());
        msAssert.isFalse(axis.getAxisBetweenCategories());

        // Chart 7
        chart = doc.getFirstSection().getBody().getShapes().get(7).getChart();
        msAssert.areNotEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        axis = chart.getAxisX();
        msAssert.areEqual(ChartAxisType.CATEGORY, axis.getType());
        msAssert.isFalse(axis.getTickLabels().isAutoSpacing());
        msAssert.areEqual(2, axis.getTickLabels().getSpacing());
        msAssert.areEqual(2, axis.getTickMarkSpacing());

        axis = chart.getAxisY();
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());

        axis = chart.getAxisZ();
        msAssert.areEqual(ChartAxisType.SERIES, axis.getType());
        msAssert.isTrue(axis.getTickLabels().isAutoSpacing());
        msAssert.areEqual(1, axis.getTickMarkSpacing());

        // Chart 8
        chart = doc.getFirstSection().getBody().getShapes().get(8).getChart();
        msAssert.areNotEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        axis = chart.getAxisY();
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());
        msAssert.areEqual(AxisBuiltInUnit.CUSTOM, axis.getDisplayUnit().getUnit());
        msAssert.areEqual(10, axis.getDisplayUnit().getCustomUnit());
        msAssert.isNotNull(axis.getDisplayUnit().getLabel());

        // Chart 9 (ChartEx)
        chart = doc.getFirstSection().getBody().getShapes().get(9).getChart();
        msAssert.areEqual(DmlChartType.CHART_EX_CHART, chart.getChartSpace().getFirstChart().getChartType());
        axis = chart.getAxisY();
        msAssert.areEqual(ChartAxisType.VALUE, axis.getType());
        msAssert.areEqual(AxisBuiltInUnit.HUNDREDS, axis.getDisplayUnit().getUnit());
        msAssert.isNotNull(axis.getDisplayUnit().getLabel());
        msAssert.areEqual(new AxisBound(-200), axis.getScaling().getMinimum());
        msAssert.areEqual(new AxisBound(200.0), axis.getScaling().getMaximum());
    }

    /// <summary>
    /// Tests that the 'delete' flag of an axis is read/written/set correctly for ISO 29500 charts.
    /// </summary>
    @Test
    public void testDeletedAxis() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\TestDeletedAxis";
        Document doc = TestUtil.openSaveOpen(FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        msAssert.isTrue(chart.getAxisY().getHidden());

        chart.getAxisY().setHidden(false);
        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        msAssert.isFalse(chart.getAxisY().getHidden());
    }
}

