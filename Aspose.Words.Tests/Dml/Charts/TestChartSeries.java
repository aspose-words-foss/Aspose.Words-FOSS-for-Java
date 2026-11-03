// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.SystemPal;
import com.aspose.ms.System.DateTime;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests behavior of the <see cref="ChartSeries"/> class.
/// </summary>
@Test
public class TestChartSeries
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void testSetUp()
    {
        RandomUtil.reset();
    }

    @Test (dataProvider = "testNonWord2016ChartSeriesTypeDataProvider")
    public void testNonWord2016ChartSeriesType(/*ChartType*/int chartType, /*ChartSeriesType*/int expectedSeriesType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);

        for (ChartSeries series : shape.getChart().getSeries())
            msAssert.areEqual(expectedSeriesType, series.getSeriesType());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNonWord2016ChartSeriesTypeDataProvider")
	public static Object[][] testNonWord2016ChartSeriesTypeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.AREA,  ChartSeriesType.AREA},
			{ChartType.AREA_STACKED,  ChartSeriesType.AREA_STACKED},
			{ChartType.AREA_PERCENT_STACKED,  ChartSeriesType.AREA_PERCENT_STACKED},
			{ChartType.AREA_3_D,  ChartSeriesType.AREA_3_D},
			{ChartType.AREA_3_D_STACKED,  ChartSeriesType.AREA_3_D_STACKED},
			{ChartType.AREA_3_D_PERCENT_STACKED,  ChartSeriesType.AREA_3_D_PERCENT_STACKED},
			{ChartType.BAR,  ChartSeriesType.BAR},
			{ChartType.BAR_STACKED,  ChartSeriesType.BAR_STACKED},
			{ChartType.BAR_PERCENT_STACKED,  ChartSeriesType.BAR_PERCENT_STACKED},
			{ChartType.BAR_3_D,  ChartSeriesType.BAR_3_D},
			{ChartType.BAR_3_D_STACKED,  ChartSeriesType.BAR_3_D_STACKED},
			{ChartType.BAR_3_D_PERCENT_STACKED,  ChartSeriesType.BAR_3_D_PERCENT_STACKED},
			{ChartType.BUBBLE,  ChartSeriesType.BUBBLE},
			{ChartType.BUBBLE_3_D,  ChartSeriesType.BUBBLE_3_D},
			{ChartType.COLUMN,  ChartSeriesType.COLUMN},
			{ChartType.COLUMN_STACKED,  ChartSeriesType.COLUMN_STACKED},
			{ChartType.COLUMN_PERCENT_STACKED,  ChartSeriesType.COLUMN_PERCENT_STACKED},
			{ChartType.COLUMN_3_D,  ChartSeriesType.COLUMN_3_D},
			{ChartType.COLUMN_3_D_STACKED,  ChartSeriesType.COLUMN_3_D_STACKED},
			{ChartType.COLUMN_3_D_PERCENT_STACKED,  ChartSeriesType.COLUMN_3_D_PERCENT_STACKED},
			{ChartType.COLUMN_3_D_CLUSTERED,  ChartSeriesType.COLUMN_3_D_CLUSTERED},
			{ChartType.DOUGHNUT,  ChartSeriesType.DOUGHNUT},
			{ChartType.LINE_STACKED,  ChartSeriesType.LINE_STACKED},
			{ChartType.LINE_PERCENT_STACKED,  ChartSeriesType.LINE_PERCENT_STACKED},
			{ChartType.LINE_3_D,  ChartSeriesType.LINE_3_D},
			{ChartType.PIE,  ChartSeriesType.PIE},
			{ChartType.PIE_3_D,  ChartSeriesType.PIE_3_D},
			{ChartType.PIE_OF_BAR,  ChartSeriesType.PIE_OF_BAR},
			{ChartType.PIE_OF_PIE,  ChartSeriesType.PIE_OF_PIE},
			{ChartType.RADAR,  ChartSeriesType.RADAR},
			{ChartType.SCATTER,  ChartSeriesType.SCATTER},
			{ChartType.STOCK,  ChartSeriesType.STOCK},
			{ChartType.SURFACE,  ChartSeriesType.SURFACE},
			{ChartType.SURFACE_3_D,  ChartSeriesType.SURFACE_3_D},
		};
	}


    @Test
    public void testParetoChartSeriesType() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        ChartSeriesCollection seriesCollection = shape.getChart().getSeries();

        msAssert.areEqual(3, seriesCollection.getCount());
        msAssert.areEqual(ChartSeriesType.PARETO_LINE, seriesCollection.get(0).getSeriesType());
        msAssert.areEqual(ChartSeriesType.PARETO, seriesCollection.get(1).getSeriesType());
        msAssert.areEqual(ChartSeriesType.PARETO_LINE, seriesCollection.get(2).getSeriesType());
    }


    /// <summary>
    /// Tests the method <see cref="ChartSeriesCollection.Add(string, string[], double[])"/> for all chart types
    /// with ensuring that no exception is generated and the document can be open in MS Word.
    /// </summary>
    @Test
    public void testAddingSeriesWithStringX() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            Shape shape = builder.insertChart(chartType, 432.0, 252.0);
            Chart chart = shape.getChart();

            chart.getTitle().setText(ChartType.toString(chartType));
            chart.getSeries().clear();

            chart.getSeries().add(
                "Series 1",
                new String[] { "Category 1", "Category 2", "Category 3", "Category 4" },
                new double[] { 10.0, 7.0, 12.0, 15.0 });
        }

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestAddingSeriesWithStringX.docx");
    }

    /// <summary>
    /// Tests the method <see cref="ChartSeriesCollection.Add(string, double[], double[])"/> for all chart types
    /// with ensuring that no exception is generated and the document can be open in MS Word.
    /// </summary>
    @Test
    public void testAddingSeriesWithDoubleX() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();

                for (/*ChartType*/int chartType : ChartType.getValues())
            {
                Shape shape = builder.insertChart(chartType, 432.0, 252.0);
                Chart chart = shape.getChart();

                chart.getTitle().setText(ChartType.toString(chartType));
                chart.getSeries().clear();

                chart.getSeries().add(
                    "Series 1",
                    new double[] { 1.0, 2.5, 4.0, 5.5 },
                    new double[] { 10.0, 7.0, 12.0, 15.0 });
            }

          TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestAddingSeriesWithDoubleX.docx");
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// Tests the method <see cref="ChartSeriesCollection.Add(string, DateTime[], double[])"/> for all chart types
    /// with ensuring that no exception is generated and the document can be open in MS Word.
    /// </summary>
    @Test
    public void testAddingSeriesWithDateTimeX() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        SystemPal.saveCulture();

        try
        {
            SystemPal.setStandardCulture();

            for (/*ChartType*/int chartType : ChartType.getValues())
            {
                Shape shape = builder.insertChart(chartType, 432.0, 252.0);
                Chart chart = shape.getChart();

                chart.getTitle().setText(ChartType.toString(chartType));
                chart.getSeries().clear();

                chart.getSeries().addInternal(
                    "Series 1",
                    new DateTime[] { new DateTime(2020, 1, 1), new DateTime(2020, 3, 1),
                        new DateTime(2020, 6, 1), new DateTime(2020, 12, 1) },
                    new double[] { 10.0, 7.0, 12.0, 15.0 });
            }

            TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestAddingSeriesWithDateTimeX.docx");
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// Tests the method <see cref="ChartSeriesCollection.Add(string, double[], double[], double[])"/> for all chart
    /// types with ensuring that no exception is generated and the document can be open in MS Word.
    /// </summary>
    @Test
    public void testAddingSeriesWithBubbleSize() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            Shape shape = builder.insertChart(chartType, 432.0, 252.0);
            Chart chart = shape.getChart();

            chart.getTitle().setText(ChartType.toString(chartType));
            chart.getSeries().clear();

            chart.getSeries().add(
                "Series 1",
                new double[] { 1.0, 2.5, 3.5, 5.0 },
                new double[] { 10.0, 7.0, 12.0, 15.0 },
                new double[] { 1.0, 0.75, 1.25, 2.0 });
        }

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestAddingSeriesWithBubbleSize.docx");
    }

    /// <summary>
    /// Tests the method <see cref="ChartSeriesCollection.Add(string, ChartMultilevelValue[], double[])"/> for all
    /// chart types with ensuring that no exception is generated and the document can be open in MS Word.
    /// </summary>
    @Test
    public void testAddingSeriesWithMultilevelX() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            Shape shape = builder.insertChart(chartType, 432.0, 252.0);
            Chart chart = shape.getChart();

            chart.getTitle().setText(ChartType.toString(chartType));
            chart.getSeries().clear();

            ChartSeries series = chart.getSeries().add(
                "Series 1",
                new ChartMultilevelValue[]
                {
                    new ChartMultilevelValue("Branch 1", "Stem 1", "Leaf 1"),
                    new ChartMultilevelValue("Branch 1", "Stem 1", "Leaf 2"),
                    new ChartMultilevelValue("Branch 1", "Stem 1", "Leaf 3"),
                    new ChartMultilevelValue("Branch 1", "Stem 2", "Leaf 4"),
                    new ChartMultilevelValue("Branch 2", "Stem 3", "Leaf 5"),
                    new ChartMultilevelValue("Branch 2", "Stem 3", "Leaf 6"),
                },
                new double[] { 10.0, 7.0, 12.0, 15.0, 14.0, 11.0 });

            series.hasDataLabels(true);
            series.getDataLabels().setShowValue(true);
            if ((chartType == ChartType.TREEMAP) || (chartType == ChartType.SUNBURST))
                series.getDataLabels().setShowCategoryName(true);
        }

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestAddingSeriesWithMultilevelX.docx");
    }

    /// <summary>
    /// Tests the method <see cref="ChartSeriesCollection.Add(string, double[])"/> for all chart types with
    /// ensuring that no exception is generated and the document can be open in MS Word.
    /// </summary>
    @Test
    public void testAddingSeriesWithoutY() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            Shape shape = builder.insertChart(chartType, 432.0, 252.0);
            Chart chart = shape.getChart();

            chart.getTitle().setText(ChartType.toString(chartType));
            chart.getSeries().clear();

            chart.getSeries().add("Series 1", new double[] { 10.0, 7.0, 12.0, 15.0, 14.0, 11.0 });
        }

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestAddingSeriesWithoutY.docx");
    }

    /// <summary>
    /// Tests the method <see cref="ChartSeriesCollection.Add(string, string[], double[], bool[])"/> for all chart
    /// types with ensuring that no exception is generated and the document can be open in MS Word.
    /// </summary>
    @Test
    public void testAddingSeriesWithIsSubtotal() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            Shape shape = builder.insertChart(chartType, 432.0, 252.0);
            Chart chart = shape.getChart();

            chart.getTitle().setText(ChartType.toString(chartType));
            chart.getSeries().clear();

            chart.getSeries().add(
                "Series 1",
                new String[] { "Start Total", "Category 1", "Category 2", "Category 3", "End Total" },
                new double[] { 100.0, 7.0, -12, 15.0, 110.0 },
                new boolean[] { true, false, false, false, true });
        }

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestAddingSeriesWithIsSubtotal.docx");
    }
}

