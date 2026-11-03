// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2020 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.DateTime;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests behavior of the <see cref="ChartDataPoint"/> and <see cref="ChartDataPointCollection"/> classes.
/// </summary>
@Test
public class TestChartDataPoint
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testInheritedPropertyValues() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();

        chart.getSeries().clear();
        ChartSeries series = chart.getSeries().addInternal("AW Series 1",
            new DateTime[] { new DateTime(2002, 1, 1), new DateTime(2002, 6, 1), new DateTime(2002, 7, 1),
                new DateTime(2002, 8, 1), new DateTime(2002, 9, 1) },
            new double[] { 640.0, 120.0, 280.0, 120.0, 150.0 });

        msAssert.areEqual(MarkerSymbol.NONE, ((IChartDataPoint)series).getMarker().getSymbol());

        ChartDataPoint point1 = series.getDataPoints().get(1);
        point1.setInvertIfNegative(true);
        point1.getMarker().setSize(12);

        msAssert.areEqual(MarkerSymbol.NONE, point1.getMarker().getSymbol());

        ((IChartDataPoint)series).getMarker().setSymbol(MarkerSymbol.DIAMOND);
        ((IChartDataPoint)series).getMarker().setSize(24);

        msAssert.areEqual(MarkerSymbol.DIAMOND, point1.getMarker().getSymbol());
        msAssert.isNull(point1.getMarker().getMarkerPr().getDirectProperty(DmlChartMarkerAttr.SYMBOL));

        ChartDataPoint point2 = series.getDataPoints().get(2);
        point2.setInvertIfNegative(true);
        msAssert.areEqual(MarkerSymbol.DIAMOND, point2.getMarker().getSymbol());

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestInheritedPropertyValues.docx", null, false);

        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        series = chart.getSeries().get(0);
        point1 = series.getDataPoints().get(1);

        msAssert.areEqual(MarkerSymbol.DIAMOND, point1.getMarker().getSymbol());
        msAssert.isNull(point1.getMarker().getMarkerPr().getDirectProperty(DmlChartMarkerAttr.SYMBOL));
        msAssert.areEqual(12, point1.getMarker().getSize());
        msAssert.isTrue(point1.getInvertIfNegative());

        point2 = series.getDataPoints().get(2);
        msAssert.isTrue(point2.getInvertIfNegative());
        msAssert.areEqual(MarkerSymbol.DIAMOND, point2.getMarker().getSymbol());
    }


    /// <summary>
    /// WORDSNET-18874 Tests the <see cref="ChartDataPoint.ClearFormat"/> and
    /// <see cref="ChartDataPointCollection.ClearFormat"/> public methods.
    /// </summary>
    @Test
    public void testClearFormat() throws Exception
    {
        Document doc = createDocumentWithChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        ChartSeries series = chart.getSeries().get(0);
        ChartDataPointCollection points = series.getDataPoints();

        ChartDataPoint point1 = points.get(0);
        ChartDataPoint point2 = points.get(1);

        point1.setInvertIfNegative(true);
        point1.getMarker().setSymbol(MarkerSymbol.SQUARE);
        point2.setInvertIfNegative(true);
        point2.getMarker().setSymbol(MarkerSymbol.DIAMOND);

        point1.clearFormat();

        msAssert.isFalse(point1.getInvertIfNegative());
        msAssert.areEqual(series.getDefaultDataPoint().getMarker().getSymbol(), point1.getMarker().getSymbol());

        points.clearFormat();
        msAssert.isFalse(point2.getInvertIfNegative());
        msAssert.areEqual(series.getDefaultDataPoint().getMarker().getSymbol(), point2.getMarker().getSymbol());
    }

    /// <summary>
    /// WORDSNET-18874 Tests data point count if a collection contains a point with index larger than
    /// the index of the last point that can be displayed.
    /// </summary>
    @Test
    public void testPointCountWhenContainingOutsidePoint() throws Exception
    {
        Document doc = createDocumentWithChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartSeries series = chart.getSeries().get(0);

        checkPointCount(series, 3);

        ChartDataPoint point = series.getDataPoints().get(5);
        checkPointCount(series, 3);

        point.getMarker().setSymbol(MarkerSymbol.PLUS);
        // Now the data point #5 has non-default value and is included in Count value.
        checkPointCount(series, 4);

        series.getDataPoints().get(5000).setInvertIfNegative(true);
        checkPointCount(series, 5);
    }

    /// <summary>
    /// WORDSNET-24940 Wrong value of ChartDataPoint.InvertIfNegative property
    /// The 'invertIfNegative' attribute is processed in a different way in MS Word than the other ones: its
    /// default value depends on a document version and when resolving a value of the attribute for a data point,
    /// the parent collection (series) is not taken into account. The necessary fixes has been implement in
    /// Aspose.Word to bypass these differences.
    /// </summary>
    @Test (dataProvider = "testInvertIfNegativeDataProvider")
    public void testInvertIfNegative(int documentVersion) throws Exception
    {
        Document doc = new Document();
        doc.getBuiltInDocumentProperties().setVersion((doc.getBuiltInDocumentProperties().getVersion() & 0xff) + (documentVersion << 16));

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertChart(ChartType.BAR, 432.0, 252.0);

        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        ChartSeries series1 = chart.getSeries().get(0);
        // Define some formatting.
        series1.getDataPoints().get(0).getFormat().getFill().solid(msColor.getGreen());
        series1.getDataPoints().get(1).getFormat().getFill().solid(Color.CYAN);

        series1.getDefaultDataPoint().getPointPr().removeProperty(DmlChartDataPointAttr.INVERT_IF_NEGATIVE);

        /*MsWordVersionCore*/int version = (/*MsWordVersionCore*/int)documentVersion;
        boolean expectedDefaultValue = (version > MsWordVersionCore.WORD_2007);
        checkInvertIfNegative(series1, expectedDefaultValue, expectedDefaultValue, expectedDefaultValue);

        series1.setInvertIfNegative(true);
        checkInvertIfNegative(series1, true, true, true);

        series1.getDataPoints().get(1).setInvertIfNegative(false);
        checkInvertIfNegative(series1, true, true, false);

        ChartSeries series2 = chart.getSeries().get(1);
        // Define some formatting.
        series2.getDataPoints().get(0).getFormat().getFill().solid(msColor.getGreen());
        series2.getDataPoints().get(1).getFormat().getFill().solid(Color.CYAN);

        series2.setInvertIfNegative(false);
        checkInvertIfNegative(series2, false, false, false);

        series2.getDataPoints().get(1).setInvertIfNegative(true);
        checkInvertIfNegative(series2, false, false, true);

        doc = TestUtil.saveOpen(doc, msString.format("Model\\Charts\\TestInvertIfNegative{0}", MsWordVersionCore.toString(version)),
            UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);

        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        series1 = chart.getSeries().get(0);
        checkInvertIfNegative(series1, true, true, false);

        series2 = chart.getSeries().get(1);
        checkInvertIfNegative(series2, false, false, true);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testInvertIfNegativeDataProvider")
	public static Object[][] testInvertIfNegativeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{(int)MsWordVersionCore.WORD_2007},
			{(int)MsWordVersionCore.WORD_2010},
		};
	}

    /// <summary>
    /// Tests the public <see cref="ChartDataPointCollection.CopyFormat"/> and <see cref="ChartSeries.CopyFormatFrom"/>
    /// methods.
    /// </summary>
    @Test
    public void testCopyingFormat() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestCopyingDataPointFormat.docx");
        ChartSeries series1 = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        ChartDataPointCollection dataPoints1 = series1.getDataPoints();
        ChartSeries series2 = doc.getFirstSection().getBody().getShapes().get(1).getChart().getSeries().get(0);
        ChartDataPointCollection dataPoints2 = series2.getDataPoints();

        msAssert.isFalse(dataPoints1.hasDefaultFormat(1));
        msAssert.isTrue(dataPoints1.hasDefaultFormat(2));
        msAssert.isFalse(dataPoints2.hasDefaultFormat(1));
        msAssert.isTrue(dataPoints2.hasDefaultFormat(2));

        dataPoints1.copyFormat(1, 2);
        dataPoints2.copyFormat(1, 2);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestCopyingDataPointFormatForItem.docx");

        series1.copyFormatFrom(1);
        series2.copyFormatFrom(1);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestCopyingDataPointFormatForCollection.docx");
    }

    /// <summary>
    /// Checks the <see cref="ChartSeries.InvertIfNegative"/> property of the specified series and the
    /// <see cref="ChartDataPoint.InvertIfNegative"/> property of the two first data points.
    /// </summary>
    private static void checkInvertIfNegative(ChartSeries series, boolean expectedSeriesValue,
        boolean expectedDataPoint1Value, boolean expectedDataPoint2Value)
    {
        msAssert.areEqual(expectedSeriesValue, series.getInvertIfNegative());
        msAssert.areEqual(expectedDataPoint1Value, series.getDataPoints().get(0).getInvertIfNegative());
        msAssert.areEqual(expectedDataPoint2Value, series.getDataPoints().get(1).getInvertIfNegative());
    }

    /// <summary>
    /// Checks point count of the series.
    /// </summary>
    private static void checkPointCount(ChartSeries series, int expectedCount)
    {
        msAssert.areEqual(expectedCount, series.getDataPoints().getCount());

        // Check enumerator too.
        int count = 0;
        for (ChartDataPoint point : series.getDataPoints())
            count++;
        msAssert.areEqual(expectedCount, count);
    }

    /// <summary>
    /// Creates a document containing a chart.
    /// </summary>
    private static Document createDocumentWithChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();

        // Delete default generated series.
        chart.getSeries().clear();

        String[] categories = new String[] { "AW Category 1", "AW Category 2", "AW Category 3" };
        chart.getSeries().add("AW Series 1", categories, new double[] { 4.3, 2.5, 3.5 });

        return doc;
    }
}

