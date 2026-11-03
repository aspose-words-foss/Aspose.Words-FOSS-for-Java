// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.SystemPal;
import org.testng.annotations.AfterMethod;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.TimeSpan;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.HashSetGeneric;
import com.aspose.Debug;
import com.aspose.FormatterPal;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests Chart Data API: <see cref="ChartSeries.XValues"/>, <see cref="ChartSeries.YValues"/>,
/// <see cref="ChartSeries.BubbleSizes"/> and other members and classes.
/// </summary>
@Test
public class TestChartDataApi
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
        // Set default culture for tests to make tests independent on the current culture.
        // Required because most of chart tests uses string formatting, which depends on culture. 
        SystemPal.saveCulture();
        SystemPal.setStandardCulture();
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown()
    {
        SystemPal.restoreCulture();
    }

    /// <summary>
    /// Tests filling data to a <see cref="ChartSeries"/> using X values of the <see cref="ChartXValueType.Double"/>
    /// type.
    /// </summary>
    @Test (dataProvider = "testFillDataWithNumericXDataProvider")
    public void testFillDataWithNumericX(/*ChartType*/int chartType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);
        Chart chart = shape.getChart();

        for (int i = 0; i < chart.getSeries().getCount(); i++)
        {
            ChartSeries series = chart.getSeries().get(i);
            series.clear();

            for (int j = 1; j <= 5; j++)
                series.add(ChartXValue.fromDouble(j), ChartYValue.fromDouble(j + (chart.getSeries().getCount() - 1 - i)), j + 1);
        }

        String fileName = msString.format(NUMERIC_X_TEST_FILE_NAME_TEMPLATE, ChartType.toString(chartType));
        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFillDataWithNumericXDataProvider")
	public static Object[][] testFillDataWithNumericXDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.AREA},
			{ChartType.AREA_STACKED},
			{ChartType.AREA_PERCENT_STACKED},
			{ChartType.AREA_3_D},
			{ChartType.AREA_3_D_STACKED},
			{ChartType.AREA_3_D_PERCENT_STACKED},
			{ChartType.BAR},
			{ChartType.BAR_STACKED},
			{ChartType.BAR_PERCENT_STACKED},
			{ChartType.BAR_3_D},
			{ChartType.BAR_3_D_STACKED},
			{ChartType.BAR_3_D_PERCENT_STACKED},
			{ChartType.BUBBLE},
			{ChartType.BUBBLE_3_D},
			{ChartType.COLUMN},
			{ChartType.COLUMN_STACKED},
			{ChartType.COLUMN_PERCENT_STACKED},
			{ChartType.COLUMN_3_D},
			{ChartType.COLUMN_3_D_STACKED},
			{ChartType.COLUMN_3_D_PERCENT_STACKED},
			{ChartType.COLUMN_3_D_CLUSTERED},
			{ChartType.DOUGHNUT},
			{ChartType.LINE},
			{ChartType.LINE_STACKED},
			{ChartType.LINE_PERCENT_STACKED},
			{ChartType.LINE_3_D},
			{ChartType.PIE},
			{ChartType.PIE_3_D},
			{ChartType.PIE_OF_BAR},
			{ChartType.PIE_OF_PIE},
			{ChartType.RADAR},
			{ChartType.SCATTER},
			{ChartType.STOCK},
			{ChartType.SURFACE},
			{ChartType.SURFACE_3_D},
		};
	}

    /// <summary>
    /// Tests filling data to a <see cref="ChartSeries"/> using X values of the <see cref="ChartXValueType.String"/>
    /// type.
    /// </summary>
    @Test (dataProvider = "testFillDataWithStringXDataProvider")
    public void testFillDataWithStringX(/*ChartType*/int chartType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);
        Chart chart = shape.getChart();

        for (int i = 0; i < chart.getSeries().getCount(); i++)
        {
            ChartSeries series = chart.getSeries().get(i);
            series.clear();

            for (int j = 1; j <= 5; j++)
            {
                int yValue = j + (chart.getSeries().getCount() - 1 - i);
                series.add(ChartXValue.fromString("Category " + j), ChartYValue.fromDouble(yValue), j + 1);
            }
        }

        String fileName = msString.format("Model\\Charts\\DataApi\\TestFillData_{0}_StringX.docx", ChartType.toString(chartType));
        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFillDataWithStringXDataProvider")
	public static Object[][] testFillDataWithStringXDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.AREA},
			{ChartType.AREA_STACKED},
			{ChartType.AREA_PERCENT_STACKED},
			{ChartType.AREA_3_D},
			{ChartType.AREA_3_D_STACKED},
			{ChartType.AREA_3_D_PERCENT_STACKED},
			{ChartType.BAR},
			{ChartType.BAR_STACKED},
			{ChartType.BAR_PERCENT_STACKED},
			{ChartType.BAR_3_D},
			{ChartType.BAR_3_D_STACKED},
			{ChartType.BAR_3_D_PERCENT_STACKED},
			{ChartType.BUBBLE},
			{ChartType.BUBBLE_3_D},
			{ChartType.COLUMN},
			{ChartType.COLUMN_STACKED},
			{ChartType.COLUMN_PERCENT_STACKED},
			{ChartType.COLUMN_3_D},
			{ChartType.COLUMN_3_D_STACKED},
			{ChartType.COLUMN_3_D_PERCENT_STACKED},
			{ChartType.COLUMN_3_D_CLUSTERED},
			{ChartType.DOUGHNUT},
			{ChartType.LINE},
			{ChartType.LINE_STACKED},
			{ChartType.LINE_PERCENT_STACKED},
			{ChartType.LINE_3_D},
			{ChartType.PIE},
			{ChartType.PIE_3_D},
			{ChartType.PIE_OF_BAR},
			{ChartType.PIE_OF_PIE},
			{ChartType.RADAR},
			{ChartType.SCATTER},
			{ChartType.STOCK},
			{ChartType.SURFACE},
			{ChartType.SURFACE_3_D},
		};
	}

    /// <summary>
    /// Tests filling data to a <see cref="ChartSeries"/> using X values of the <see cref="ChartXValueType.DateTime"/>
    /// type.
    /// </summary>
    @Test (dataProvider = "testFillDataWithDateTimeXDataProvider")
    public void testFillDataWithDateTimeX(/*ChartType*/int chartType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);
        Chart chart = shape.getChart();

        for (int i = 0; i < chart.getSeries().getCount(); i++)
        {
            ChartSeries series = chart.getSeries().get(i);
            series.clear();

            for (int j = 1; j <= 5; j++)
            {
                int yValue = j + (chart.getSeries().getCount() - 1 - i);
                series.add(ChartXValue.fromDateTimeInternal(new DateTime(2022, j, 1)), ChartYValue.fromDouble(yValue), j + 1);
            }
        }

        String fileName = msString.format("Model\\Charts\\DataApi\\TestFillData_{0}_DateTimeX.docx", ChartType.toString(chartType));
        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFillDataWithDateTimeXDataProvider")
	public static Object[][] testFillDataWithDateTimeXDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.AREA},
			{ChartType.AREA_STACKED},
			{ChartType.AREA_PERCENT_STACKED},
			{ChartType.AREA_3_D},
			{ChartType.AREA_3_D_STACKED},
			{ChartType.AREA_3_D_PERCENT_STACKED},
			{ChartType.BAR},
			{ChartType.BAR_STACKED},
			{ChartType.BAR_PERCENT_STACKED},
			{ChartType.BAR_3_D},
			{ChartType.BAR_3_D_STACKED},
			{ChartType.BAR_3_D_PERCENT_STACKED},
			{ChartType.BUBBLE},
			{ChartType.BUBBLE_3_D},
			{ChartType.COLUMN},
			{ChartType.COLUMN_STACKED},
			{ChartType.COLUMN_PERCENT_STACKED},
			{ChartType.COLUMN_3_D},
			{ChartType.COLUMN_3_D_STACKED},
			{ChartType.COLUMN_3_D_PERCENT_STACKED},
			{ChartType.COLUMN_3_D_CLUSTERED},
			{ChartType.DOUGHNUT},
			{ChartType.LINE},
			{ChartType.LINE_STACKED},
			{ChartType.LINE_PERCENT_STACKED},
			{ChartType.LINE_3_D},
			{ChartType.PIE},
			{ChartType.PIE_3_D},
			{ChartType.PIE_OF_BAR},
			{ChartType.PIE_OF_PIE},
			{ChartType.RADAR},
			{ChartType.SCATTER},
			{ChartType.STOCK},
			{ChartType.SURFACE},
			{ChartType.SURFACE_3_D},
		};
	}

    /// <summary>
    /// Tests specifying datetime X and Y values containing milliseconds.
    /// </summary>
    @Test
    public void testDateTimeWithMilliseconds() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.SCATTER, 432.0, 252.0);
        Chart chart = shape.getChart();

        for (ChartSeries series : chart.getSeries())
        {
            series.clear();

            for (int j = 1; j <= 5; j++)
            {
                series.add(
                    ChartXValue.fromDateTimeInternal(new DateTime(2023, 1, j, 0, 0, 0, 700)),
                    ChartYValue.fromDateTimeInternal(new DateTime(2023, 1, j, 0, 0, 0, 700)));
            }
        }

        builder.writeln();
        shape = builder.insertChart(ChartType.SCATTER, 432.0, 252.0);
        chart = shape.getChart();

        for (ChartSeries series : chart.getSeries())
        {
            series.clear();

            for (int j = 1; j <= 5; j++)
            {
                series.add(
                    ChartXValue.fromTimeSpanInternal(new TimeSpan(0, 0, 0, j, 400)),
                    ChartYValue.fromTimeSpanInternal(new TimeSpan(0, 0, 0, j, 600)));
            }
        }

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\DataApi\\TestDateTimeWithMilliseconds.docx");
    }

    /// <summary>
    /// Tests filling data to a <see cref="ChartSeries"/> using X values of the <see cref="ChartXValueType.Time"/>
    /// type.
    /// </summary>
    @Test (dataProvider = "testFillDataWithTimeXDataProvider")
    public void testFillDataWithTimeX(/*ChartType*/int chartType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);
        Chart chart = shape.getChart();

        for (int i = 0; i < chart.getSeries().getCount(); i++)
        {
            ChartSeries series = chart.getSeries().get(i);
            series.clear();

            for (int j = 1; j <= 5; j++)
            {
                int yValue = j + (chart.getSeries().getCount() - 1 - i);
                series.add(ChartXValue.fromTimeSpanInternal(new TimeSpan(9 + j, 0, 0)), ChartYValue.fromDouble(yValue), j + 1);
            }
        }

        String fileName = msString.format("Model\\Charts\\DataApi\\TestFillData_{0}_TimeX.docx", ChartType.toString(chartType));
        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFillDataWithTimeXDataProvider")
	public static Object[][] testFillDataWithTimeXDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.AREA},
			{ChartType.AREA_STACKED},
			{ChartType.AREA_PERCENT_STACKED},
			{ChartType.AREA_3_D},
			{ChartType.AREA_3_D_STACKED},
			{ChartType.AREA_3_D_PERCENT_STACKED},
			{ChartType.BAR},
			{ChartType.BAR_STACKED},
			{ChartType.BAR_PERCENT_STACKED},
			{ChartType.BAR_3_D},
			{ChartType.BAR_3_D_STACKED},
			{ChartType.BAR_3_D_PERCENT_STACKED},
			{ChartType.BUBBLE},
			{ChartType.BUBBLE_3_D},
			{ChartType.COLUMN},
			{ChartType.COLUMN_STACKED},
			{ChartType.COLUMN_PERCENT_STACKED},
			{ChartType.COLUMN_3_D},
			{ChartType.COLUMN_3_D_STACKED},
			{ChartType.COLUMN_3_D_PERCENT_STACKED},
			{ChartType.COLUMN_3_D_CLUSTERED},
			{ChartType.DOUGHNUT},
			{ChartType.LINE},
			{ChartType.LINE_STACKED},
			{ChartType.LINE_PERCENT_STACKED},
			{ChartType.LINE_3_D},
			{ChartType.PIE},
			{ChartType.PIE_3_D},
			{ChartType.PIE_OF_BAR},
			{ChartType.PIE_OF_PIE},
			{ChartType.RADAR},
			{ChartType.SCATTER},
			{ChartType.STOCK},
			{ChartType.SURFACE},
			{ChartType.SURFACE_3_D},
		};
	}

    /// <summary>
    /// Tests filling data to a <see cref="ChartSeries"/> using X values of the <see cref="ChartXValueType.Multilevel"/>
    /// type.
    /// </summary>
    @Test (dataProvider = "testFillDataWithMultilevelXDataProvider")
    public void testFillDataWithMultilevelX(/*ChartType*/int chartType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);
        Chart chart = shape.getChart();

        for (int i = 0; i < chart.getSeries().getCount(); i++)
        {
            ChartSeries series = chart.getSeries().get(i);
            series.clear();

            for (int j = 1; j <= 5; j++)
            {
                int yValue = j + (chart.getSeries().getCount() - 1 - i);
                series.add(
                    ChartXValue.fromMultilevelValue(new ChartMultilevelValue(
                        (j <= 3) ? "First Part" : "Last Part",
                        ((j - 1 & 2) == 0) ? "Group 1" : "Group 2",
                        "Category " + j)),
                    ChartYValue.fromDouble(yValue),
                    j + 1);
            }
        }

        String fileName = msString.format("Model\\Charts\\DataApi\\TestFillData_{0}_MultilevelX.docx", ChartType.toString(chartType));
        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFillDataWithMultilevelXDataProvider")
	public static Object[][] testFillDataWithMultilevelXDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.AREA},
			{ChartType.AREA_STACKED},
			{ChartType.AREA_PERCENT_STACKED},
			{ChartType.AREA_3_D},
			{ChartType.AREA_3_D_STACKED},
			{ChartType.AREA_3_D_PERCENT_STACKED},
			{ChartType.BAR},
			{ChartType.BAR_STACKED},
			{ChartType.BAR_PERCENT_STACKED},
			{ChartType.BAR_3_D},
			{ChartType.BAR_3_D_STACKED},
			{ChartType.BAR_3_D_PERCENT_STACKED},
			{ChartType.BUBBLE},
			{ChartType.BUBBLE_3_D},
			{ChartType.COLUMN},
			{ChartType.COLUMN_STACKED},
			{ChartType.COLUMN_PERCENT_STACKED},
			{ChartType.COLUMN_3_D},
			{ChartType.COLUMN_3_D_STACKED},
			{ChartType.COLUMN_3_D_PERCENT_STACKED},
			{ChartType.COLUMN_3_D_CLUSTERED},
			{ChartType.DOUGHNUT},
			{ChartType.LINE},
			{ChartType.LINE_STACKED},
			{ChartType.LINE_PERCENT_STACKED},
			{ChartType.LINE_3_D},
			{ChartType.PIE},
			{ChartType.PIE_3_D},
			{ChartType.PIE_OF_BAR},
			{ChartType.PIE_OF_PIE},
			{ChartType.RADAR},
			{ChartType.SCATTER},
			{ChartType.STOCK},
			{ChartType.SURFACE},
			{ChartType.SURFACE_3_D},
		};
	}

    /// <summary>
    /// Tests that a warning is generated when adding chart values including bubble sizes if a series does not
    /// support bubble sizes. And that there is no warning if the series supports them.
    /// </summary>
    @Test (dataProvider = "testBubbleSizeWarningsDataProvider")
    public void testBubbleSizeWarnings(/*ChartType*/int chartType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);
        Chart chart = shape.getChart();

        TestWarningCallback warningCallback = new TestWarningCallback();
        doc.setWarningCallback(warningCallback);

        final int VALUE_COUNT = 5;

        for (int i = 0; i < chart.getSeries().getCount(); i++)
        {
            ChartSeries series = chart.getSeries().get(i);
            series.clear();

            for (int j = 1; j <= VALUE_COUNT; j++)
                series.add(ChartXValue.fromDouble(j), ChartYValue.fromDouble(j + (chart.getSeries().getCount() - 1 - i)), j + 1);
        }

        if (chart.getChartSpace().getFirstChart().getChartType() != DmlChartType.BUBBLE_CHART)
        {
            msAssert.areEqual(VALUE_COUNT * chart.getSeries().getCount(), warningCallback.getCount());
            for (int i = 0; i < warningCallback.getCount(); i++)
                msAssert.areEqual(WarningStrings.CANNOT_SET_BUBBLE_SIZE, warningCallback.get(i).getDescription());
        }
        else
        {
            msAssert.areEqual(0, warningCallback.getCount());
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBubbleSizeWarningsDataProvider")
	public static Object[][] testBubbleSizeWarningsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.AREA},
			{ChartType.AREA_STACKED},
			{ChartType.AREA_PERCENT_STACKED},
			{ChartType.AREA_3_D},
			{ChartType.AREA_3_D_STACKED},
			{ChartType.AREA_3_D_PERCENT_STACKED},
			{ChartType.BAR},
			{ChartType.BAR_STACKED},
			{ChartType.BAR_PERCENT_STACKED},
			{ChartType.BAR_3_D},
			{ChartType.BAR_3_D_STACKED},
			{ChartType.BAR_3_D_PERCENT_STACKED},
			{ChartType.BUBBLE},
			{ChartType.BUBBLE_3_D},
			{ChartType.COLUMN},
			{ChartType.COLUMN_STACKED},
			{ChartType.COLUMN_PERCENT_STACKED},
			{ChartType.COLUMN_3_D},
			{ChartType.COLUMN_3_D_STACKED},
			{ChartType.COLUMN_3_D_PERCENT_STACKED},
			{ChartType.COLUMN_3_D_CLUSTERED},
			{ChartType.DOUGHNUT},
			{ChartType.LINE},
			{ChartType.LINE_STACKED},
			{ChartType.LINE_PERCENT_STACKED},
			{ChartType.LINE_3_D},
			{ChartType.PIE},
			{ChartType.PIE_3_D},
			{ChartType.PIE_OF_BAR},
			{ChartType.PIE_OF_PIE},
			{ChartType.RADAR},
			{ChartType.SCATTER},
			{ChartType.STOCK},
			{ChartType.SURFACE},
			{ChartType.SURFACE_3_D},
		};
	}

    /// <summary>
    /// Tests filling data to a <see cref="ChartSeries"/> using Y values of the <see cref="ChartYValueType.DateTime"/>
    /// type.
    /// </summary>
    @Test (dataProvider = "testFillDataWithDateTimeYDataProvider")
    public void testFillDataWithDateTimeY(/*ChartType*/int chartType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);
        Chart chart = shape.getChart();

        for (int i = 0; i < chart.getSeries().getCount(); i++)
        {
            ChartSeries series = chart.getSeries().get(i);
            series.clear();
            series.hasDataLabels(true);
            series.getDataLabels().setShowValue(true);

            for (int j = 1; j <= 5; j++)
            {
                int month = j + (chart.getSeries().getCount() - 1 - i);
                series.add(
                    ChartXValue.fromString("Category " + j),
                    ChartYValue.fromDateTimeInternal(new DateTime(2022, month, 1)),
                    j + 1);
            }
        }

        // When opening the output file in MS Word and select Edit Data, datetime values are displayed as numeric:
        // Looks like MS Word doesn't use format code when generates Y data in internal XLSX.

        String fileName = msString.format("Model\\Charts\\DataApi\\TestFillData_{0}_DateTimeY.docx", ChartType.toString(chartType));
        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFillDataWithDateTimeYDataProvider")
	public static Object[][] testFillDataWithDateTimeYDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.AREA},
			{ChartType.AREA_STACKED},
			{ChartType.AREA_PERCENT_STACKED},
			{ChartType.AREA_3_D},
			{ChartType.AREA_3_D_STACKED},
			{ChartType.AREA_3_D_PERCENT_STACKED},
			{ChartType.BAR},
			{ChartType.BAR_STACKED},
			{ChartType.BAR_PERCENT_STACKED},
			{ChartType.BAR_3_D},
			{ChartType.BAR_3_D_STACKED},
			{ChartType.BAR_3_D_PERCENT_STACKED},
			{ChartType.BUBBLE},
			{ChartType.BUBBLE_3_D},
			{ChartType.COLUMN},
			{ChartType.COLUMN_STACKED},
			{ChartType.COLUMN_PERCENT_STACKED},
			{ChartType.COLUMN_3_D},
			{ChartType.COLUMN_3_D_STACKED},
			{ChartType.COLUMN_3_D_PERCENT_STACKED},
			{ChartType.COLUMN_3_D_CLUSTERED},
			{ChartType.DOUGHNUT},
			{ChartType.LINE},
			{ChartType.LINE_STACKED},
			{ChartType.LINE_PERCENT_STACKED},
			{ChartType.LINE_3_D},
			{ChartType.PIE},
			{ChartType.PIE_3_D},
			{ChartType.PIE_OF_BAR},
			{ChartType.PIE_OF_PIE},
			{ChartType.RADAR},
			{ChartType.SCATTER},
			{ChartType.STOCK},
			{ChartType.SURFACE},
			{ChartType.SURFACE_3_D},
		};
	}

    /// <summary>
    /// Tests filling data to a <see cref="ChartSeries"/> using Y values of the <see cref="ChartYValueType.Time"/>
    /// type.
    /// </summary>
    @Test (dataProvider = "testFillDataWithTimeYDataProvider")
    public void testFillDataWithTimeY(/*ChartType*/int chartType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);
        Chart chart = shape.getChart();

        for (int i = 0; i < chart.getSeries().getCount(); i++)
        {
            ChartSeries series = chart.getSeries().get(i);
            series.clear();
            series.hasDataLabels(true);
            series.getDataLabels().setShowValue(true);

            for (int j = 1; j <= 5; j++)
            {
                int hours = j + (chart.getSeries().getCount() - 1 - i);
                series.add(
                    ChartXValue.fromString("Category " + j),
                    ChartYValue.fromTimeSpanInternal(new TimeSpan(hours, 0, 0)),
                    j + 1);
            }
        }

        // When opening the output file in MS Word and select Edit Data, time values are displayed as numeric:
        // Looks like MS Word doesn't use format code when generates Y data in internal XLSX.

        String fileName = msString.format("Model\\Charts\\DataApi\\TestFillData_{0}_TimeY.docx", ChartType.toString(chartType));
        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFillDataWithTimeYDataProvider")
	public static Object[][] testFillDataWithTimeYDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.AREA},
			{ChartType.AREA_STACKED},
			{ChartType.AREA_PERCENT_STACKED},
			{ChartType.AREA_3_D},
			{ChartType.AREA_3_D_STACKED},
			{ChartType.AREA_3_D_PERCENT_STACKED},
			{ChartType.BAR},
			{ChartType.BAR_STACKED},
			{ChartType.BAR_PERCENT_STACKED},
			{ChartType.BAR_3_D},
			{ChartType.BAR_3_D_STACKED},
			{ChartType.BAR_3_D_PERCENT_STACKED},
			{ChartType.BUBBLE},
			{ChartType.BUBBLE_3_D},
			{ChartType.COLUMN},
			{ChartType.COLUMN_STACKED},
			{ChartType.COLUMN_PERCENT_STACKED},
			{ChartType.COLUMN_3_D},
			{ChartType.COLUMN_3_D_STACKED},
			{ChartType.COLUMN_3_D_PERCENT_STACKED},
			{ChartType.COLUMN_3_D_CLUSTERED},
			{ChartType.DOUGHNUT},
			{ChartType.LINE},
			{ChartType.LINE_STACKED},
			{ChartType.LINE_PERCENT_STACKED},
			{ChartType.LINE_3_D},
			{ChartType.PIE},
			{ChartType.PIE_3_D},
			{ChartType.PIE_OF_BAR},
			{ChartType.PIE_OF_PIE},
			{ChartType.RADAR},
			{ChartType.SCATTER},
			{ChartType.STOCK},
			{ChartType.SURFACE},
			{ChartType.SURFACE_3_D},
		};
	}



    /// <summary>
    /// Tests getting/setting empty (null) X and Y values.
    /// </summary>
    @Test
    public void testEmptyValues() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\DataApi\\TestEmptyValues.docx";
        Document doc = TestUtil.open(FILE_NAME);

        // Test data of column chart.

        Shape columnChartShape = doc.getFirstSection().getBody().getShapes().get(0);
        ChartSeriesCollection columnChartSeries = columnChartShape.getChart().getSeries();
        msAssert.areEqual(3, columnChartSeries.getCount());
        ChartSeries columnChartSeries1 = columnChartSeries.get(0);
        ChartSeries columnChartSeries2 = columnChartSeries.get(1);
        ChartSeries columnChartSeries3 = columnChartSeries.get(2);

        ChartXValueCollection xValues1 = columnChartSeries1.getXValues();
        msAssert.areEqual(4, xValues1.getCount());
        msAssert.isNotNull(xValues1.get(0));
        msAssert.areEqual(ChartXValueType.STRING, xValues1.get(0).getValueType());
        msAssert.areEqual("Category 1", xValues1.get(0).getStringValue());
        msAssert.isNull(xValues1.get(1));
        msAssert.isNull(xValues1.get(3));

        ChartYValueCollection yValues1 = columnChartSeries1.getYValues();
        msAssert.areEqual(4, yValues1.getCount());
        msAssert.isNull(yValues1.get(0));
        msAssert.isNull(yValues1.get(1));
        msAssert.isNotNull(yValues1.get(2));
        msAssert.areEqual(ChartYValueType.DOUBLE, yValues1.get(2).getValueType());
        msAssert.isNotNull(yValues1.get(3));
        msAssert.areEqual(3.5d, yValues1.get(2).getDoubleValue());

        msAssert.isNull(columnChartSeries2.getYValues().get(1));
        msAssert.isNull(columnChartSeries3.getYValues().get(1));

        // Update data.
        xValues1.set(0, null);
        xValues1.set(1, ChartXValue.fromString("Updated Category 2"));
        yValues1.set(1, ChartYValue.fromDouble(6.0));
        yValues1.set(3, null);

        columnChartSeries1.insert(0, ChartXValue.fromString("Inserted Category"), ChartYValue.fromDouble(0.5));
        columnChartSeries1.remove(1);
        columnChartSeries1.add(ChartXValue.fromString("Last Category"), null);
        // Insert empty values.
        columnChartSeries1.insert(4, null, null);
        msAssert.areEqual(6, xValues1.getCount());
        msAssert.areEqual(6, yValues1.getCount());

        msAssert.areEqual(4, columnChartSeries2.getXValues().getCount());
        msAssert.areEqual(4, columnChartSeries2.getYValues().getCount());
        // Add empty values.
        columnChartSeries2.add(null, null);
        // X categories of the first series are displayed on the chart. This X value will not be displayed.
        columnChartSeries2.insert(4, ChartXValue.fromString("Non-displayed value 1"), null);
        msAssert.areEqual(6, columnChartSeries2.getXValues().getCount());
        msAssert.areEqual(6, columnChartSeries2.getYValues().getCount());

        msAssert.areEqual(4, columnChartSeries3.getXValues().getCount());
        msAssert.areEqual(4, columnChartSeries3.getYValues().getCount());
        columnChartSeries3.add(ChartXValue.fromString("Non-displayed value 2"), ChartYValue.fromDouble(5.0));
        columnChartSeries3.insert(4, ChartXValue.fromString("Non-displayed value 3"), ChartYValue.fromDouble(4.5));
        msAssert.areEqual(6, columnChartSeries3.getXValues().getCount());
        msAssert.areEqual(6, columnChartSeries3.getYValues().getCount());

        // Test data of bubble chart.

        Shape bubbleChartShape = doc.getFirstSection().getBody().getShapes().get(1);
        ChartSeries series = bubbleChartShape.getChart().getSeries().get(0);

        ChartXValueCollection xValues = series.getXValues();
        msAssert.areEqual(3, xValues.getCount());
        msAssert.isNotNull(xValues.get(0));
        msAssert.areEqual(0.7, 0.01, xValues.get(0).getDoubleValue());
        msAssert.isNotNull(xValues.get(1));
        msAssert.isNull(xValues.get(2));

        ChartYValueCollection yValues = series.getYValues();
        msAssert.areEqual(3, yValues.getCount());
        msAssert.isNotNull(yValues.get(0));
        msAssert.areEqual(2.7, 0.01, yValues.get(0).getDoubleValue());
        msAssert.isNotNull(yValues.get(1));
        msAssert.areEqual(3.2, 0.01, yValues.get(1).getDoubleValue());
        msAssert.isNull(yValues.get(2));

        BubbleSizeCollection bubbleSizes = series.getBubbleSizes();
        msAssert.areEqual(3, bubbleSizes.getCount());
        msAssert.areEqual(10, 0.01, bubbleSizes.get(0));
        // Empty values in BubbleSizeCollection are represented as NaN.
        msAssert.isTrue(Double.isNaN(bubbleSizes.get(1)));
        msAssert.areEqual(8, 0.01, bubbleSizes.get(2));

        // Update data.
        bubbleSizes.set(0, Double.NaN);
        bubbleSizes.set(1, (double)4.0);
        xValues.set(2, ChartXValue.fromDouble(2.5));
        yValues.set(2, ChartYValue.fromDouble(1.5));

        series.insert(0, ChartXValue.fromDouble(0.1), ChartYValue.fromDouble(0.5), 1.0);
        series.remove(1);
        // Check adding empty values.
        series.add(ChartXValue.fromDouble(1.0), null, Double.NaN);
        series.add(null, ChartYValue.fromDouble(2.0), 2.0);
        // Check inserting empty values.
        series.insert(4, ChartXValue.fromDouble(3.0), null, 3.0);
        series.insert(5, null, ChartYValue.fromDouble(4.0), Double.NaN);
        msAssert.areEqual(7, xValues.getCount());
        msAssert.areEqual(7, yValues.getCount());
        msAssert.areEqual(7, bubbleSizes.getCount());

        TestUtil.saveCheckGoldExportOnly(doc, FILE_NAME);
    }

    /// <summary>
    /// Tests inserting and removing values to/from a chart series.
    /// </summary>
    @Test
    public void testInsertingAndRemovingValues() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.SCATTER, 432.0, 252.0);
        shape.getChart().getSeries().clear();
        ChartSeries series = shape.getChart().getSeries().add(
            "Series 1",
            new double[] { 0.0, 2.0, 3.0, 3.3, 3.7, 4.0, 5.0 },
            new double[] { 0.0, -2, 0.5, -1, -1, 4.0, 3.0 });

        series.hasDataLabels(true);
        series.getDefaultDataPoint().getMarker().setSymbol(MarkerSymbol.TRIANGLE);
        series.getDefaultDataPoint().getMarker().setSize(12);
        series.getDefaultDataPoint().getMarker().getFormat().getFill().solid(Color.BLUE);
        series.getDefaultDataPoint().getFormat().getStroke().setColor(msColor.getLightBlue());

        series.getDataPoints().get(1).getMarker().getFormat().getFill().solid(Color.RED);
        series.getDataLabels().get(1).setShowValue(true);
        series.getDataPoints().get(5).getMarker().getFormat().getFill().solid(msColor.getGreen());
        series.getDataLabels().get(5).setShowValue(true);
        series.getDataLabels().get(5).setShowLegendKey(true);
        series.setSmooth(true);

        series.remove(3);
        series.insert(1, ChartXValue.fromDouble(1.0), ChartYValue.fromDouble(-1.5));
        series.remove(4);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\DataApi\\TestInsertingAndRemovingValues.docx");
    }


    /// <summary>
    /// Tests chart data manipulations in a Pareto Word 2016 chart.
    /// </summary>
    @Test
    public void testPareto() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        Body body = doc.getFirstSection().getBody();
        Shape shape = body.getShapes().get(0);
        msAssert.areEqual(3, shape.getChart().getSeries().getCount());

        ChartSeries series1 = shape.getChart().getSeries().get(0);
        ChartSeries series2 = shape.getChart().getSeries().get(1);
        ChartSeries series3 = shape.getChart().getSeries().get(2);
        msAssert.areEqual(SeriesLayout.PARETO_LINE, series1.getLayoutType());
        msAssert.areEqual(SeriesLayout.CLUSTERED_COLUMN, series2.getLayoutType());
        msAssert.areEqual(SeriesLayout.PARETO_LINE, series3.getLayoutType());

        msAssert.areEqual(0, series1.getXValues().getCount());
        msAssert.areEqual(0, series1.getYValues().getCount());
        msAssert.areEqual(0, series1.getBubbleSizes().getCount());
        msAssert.areEqual(0, series3.getXValues().getCount());
        msAssert.areEqual(0, series3.getYValues().getCount());
        msAssert.areEqual(0, series3.getBubbleSizes().getCount());

        // Check that no exception occurs in RemoveAll when called for a Pareto line series.
        series1.clear();

        ChartXValueCollection xValues = series2.getXValues();
        ChartYValueCollection yValues = series2.getYValues();

        msAssert.areEqual(50, xValues.getCount());
        msAssert.areEqual(50, yValues.getCount());
        msAssert.areEqual(0, series2.getBubbleSizes().getCount());
        msAssert.areEqual(ChartXValueType.STRING, xValues.get(0).getValueType());
        msAssert.areEqual("Category 1", xValues.get(0).getStringValue());
        msAssert.areEqual("Category 3", xValues.get(1).getStringValue());
        msAssert.areEqual("Category 4", xValues.get(49).getStringValue());
        msAssert.areEqual(ChartYValueType.DOUBLE, yValues.get(0).getValueType());
        msAssert.areEqual(1, 0.01, yValues.get(0).getDoubleValue());
        msAssert.areEqual(1, 0.01, yValues.get(1).getDoubleValue());
        msAssert.areEqual(1, 0.01, yValues.get(49).getDoubleValue());

        int count = 0;
        for (int i = xValues.getCount() - 1; i >= 0; i--)
        {
            if ("Category 3".equals(xValues.get(i).getStringValue()))
            {
                series2.remove(i);
                count++;
            }
        }

        msAssert.areEqual(3, count);

        series2.getYValues().set(2, ChartYValue.fromDouble(2.0));
        series2.insert(0, ChartXValue.fromString("New Category"), ChartYValue.fromDouble(5.0));
        series2.add(ChartXValue.fromString("Last Category"), ChartYValue.fromDouble(4.0));

        Shape shape2 = (Shape)shape.deepClone(true);
        body.appendChild(new Paragraph(doc));
        body.getLastParagraph().appendChild(shape2);

        shape2.getChart().getTitle().setText("New Chart 1");

        ChartSeries newChartSeries2 = shape2.getChart().getSeries().get(1);
        newChartSeries2.clear();
        newChartSeries2.hasDataLabels(false);

        for (int i = 1; i <= 12; i++)
        {
            newChartSeries2.add(
                ChartXValue.fromString((new DateTime(2022, i, 1)).toString("yyyy-M-d")),
                ChartYValue.fromDouble(i * 100));
        }

        Shape shape3 = (Shape)shape.deepClone(true);
        body.appendChild(new Paragraph(doc));
        body.appendChild(new Paragraph(doc));
        body.getLastParagraph().appendChild(shape3);

        shape3.getChart().getTitle().setText("New Chart 2");

        ChartSeries newChartSeries3 = shape3.getChart().getSeries().get(1);
        newChartSeries3.clear();

        for (int i = 1; i <= 12; i++)
        {
            newChartSeries3.add(
                ChartXValue.fromString((new DateTime(2022, i, 1)).toString("yyyy-M-d")),
                ChartYValue.fromTimeSpanInternal(new TimeSpan(i, 0, 0)));
        }

        // Data and axis labels aren't displayed correctly in Russian locale. It can be resolved in this way:
        //shape3.Chart.AxisY.NumberFormat.FormatCode = "[$-x-systime]ч:мм";
        //newChartSeries3.DataLabels.NumberFormat.FormatCode = "[$-x-systime]ч:мм";

        Shape shape4 = (Shape)shape.deepClone(true);
        body.appendChild(new Paragraph(doc));
        body.appendChild(new Paragraph(doc));
        body.getLastParagraph().appendChild(shape4);

        shape4.getChart().getTitle().setText("New Chart 3");

        ChartSeries newChartSeries4 = shape4.getChart().getSeries().get(1);
        newChartSeries4.clear();

        for (int i = 1; i <= 4; i++)
        {
            newChartSeries4.add(
                ChartXValue.fromString(msString.format("Category {0}", i)),
                ChartYValue.fromDateTimeInternal(new DateTime(2000 + i * 5, 1, 1)));
        }

        // Data and axis labels aren't displayed correctly in Russian locale. It can be resolved in this way:
        //shape4.Chart.AxisY.NumberFormat.FormatCode = "ДД.ММ.ГГГГ";
        //newChartSeries4.DataLabels.NumberFormat.FormatCode = "ДД.ММ.ГГГГ";

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\DataApi\\TestPareto.docx");
    }

    /// <summary>
    /// Tests that an exception is thrown when trying to add a value to a pareto line series using the
    /// <see cref="ChartSeries.Add(ChartXValue)"/> method.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis series type cannot have data.\\E")
    public void testAddingValueToParetoLine1() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        msAssert.areEqual(SeriesLayout.PARETO_LINE, series.getLayoutType());

        series.add(ChartXValue.fromString("Category"));
    }

    /// <summary>
    /// Tests that an exception is thrown when trying to add a value to a pareto line series using the
    /// <see cref="ChartSeries.Add(ChartXValue, ChartYValue)"/> method.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis series type cannot have data.\\E")
    public void testAddingValueToParetoLine2() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        msAssert.areEqual(SeriesLayout.PARETO_LINE, series.getLayoutType());

        series.add(ChartXValue.fromString("Category"), ChartYValue.fromDouble(1.0));
    }

    /// <summary>
    /// Tests that an exception is thrown when trying to add a value to a pareto line series using the
    /// <see cref="ChartSeries.Add(ChartXValue, ChartYValue, double)"/> method.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis series type cannot have data.\\E")
    public void testAddingValueToParetoLine3() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        msAssert.areEqual(SeriesLayout.PARETO_LINE, series.getLayoutType());

        series.add(ChartXValue.fromString("Category"), ChartYValue.fromDouble(1.0), Double.NaN);
    }

    /// <summary>
    /// Tests that an exception is thrown when trying to insert a value to a pareto line series using the
    /// <see cref="ChartSeries.Insert(int, ChartXValue)"/> method.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis series type cannot have data.\\E")
    public void testInsertingValueToParetoLine1() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        msAssert.areEqual(SeriesLayout.PARETO_LINE, series.getLayoutType());

        series.insert(0, ChartXValue.fromString("Category"));
    }

    /// <summary>
    /// Tests that an exception is thrown when trying to insert a value to a pareto line series using the
    /// <see cref="ChartSeries.Insert(int, ChartXValue, ChartYValue)"/> method.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis series type cannot have data.\\E")
    public void testInsertingValueToParetoLine2() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        msAssert.areEqual(SeriesLayout.PARETO_LINE, series.getLayoutType());

        series.insert(0, ChartXValue.fromString("Category"), ChartYValue.fromDouble(1.0));
    }

    /// <summary>
    /// Tests that an exception is thrown when trying to insert a value to a pareto line series using the
    /// <see cref="ChartSeries.Insert(int, ChartXValue, ChartYValue, double)"/> method.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis series type cannot have data.\\E")
    public void testInsertingValueToParetoLine3() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        msAssert.areEqual(SeriesLayout.PARETO_LINE, series.getLayoutType());

        series.insert(0, ChartXValue.fromString("Category"), ChartYValue.fromDouble(1.0), Double.NaN);
    }


    /// <summary>
    /// Tests chart data manipulations in a Waterfall Word 2016 chart.
    /// </summary>
    @Test
    public void testWaterfall() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Waterfall.docx");
        Body body = doc.getFirstSection().getBody();
        Shape shape = body.getShapes().get(0);
        msAssert.areEqual(1, shape.getChart().getSeries().getCount());

        ChartSeries series = shape.getChart().getSeries().get(0);
        msAssert.areEqual(SeriesLayout.WATERFALL, series.getLayoutType());

        ChartXValueCollection xValues = series.getXValues();
        ChartYValueCollection yValues = series.getYValues();

        msAssert.areEqual(8, xValues.getCount());
        msAssert.areEqual(8, yValues.getCount());
        msAssert.areEqual(0, series.getBubbleSizes().getCount());
        msAssert.areEqual(ChartXValueType.STRING, xValues.get(0).getValueType());
        msAssert.areEqual("Category 1", xValues.get(0).getStringValue());
        msAssert.areEqual("Category 2", xValues.get(1).getStringValue());
        msAssert.areEqual("Category 8", xValues.get(7).getStringValue());
        msAssert.areEqual(ChartYValueType.DOUBLE, yValues.get(0).getValueType());
        msAssert.areEqual(10000, 0.01, yValues.get(0).getDoubleValue());
        msAssert.areEqual(2000, 0.01, yValues.get(1).getDoubleValue());
        msAssert.areEqual(14000, 0.01, yValues.get(7).getDoubleValue());

        xValues.set(7, ChartXValue.fromString("Changed Category"));
        yValues.set(7, ChartYValue.fromDouble(-1000));
        series.remove(1);
        series.insert(2, ChartXValue.fromString("New Category"), ChartYValue.fromDouble(2700.0));
        series.add(ChartXValue.fromString("Last Category"), ChartYValue.fromDouble(1500.0));

        Shape shape2 = (Shape)shape.deepClone(true);
        body.appendChild(new Paragraph(doc));
        body.appendChild(new Paragraph(doc));
        body.getLastParagraph().appendChild(shape2);

        shape2.getChart().getTitle().setText("New Chart 1");

        ChartSeries newChartSeries2 = shape2.getChart().getSeries().get(0);
        newChartSeries2.clear();
        // Reset label properties.
        newChartSeries2.getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.SP_PR, null);
        newChartSeries2.getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.TX_PR, null);
        // Reset axis settings.
        shape2.getChart().getAxisY().setChartAxisPr(new DmlChartAxisPr(doc, shape2.getChart().getChartSpace().isChartEx()));
        shape2.getChart().getAxisY().setAxId(1);

        for (int i = 1; i <= 12; i++)
        {
            newChartSeries2.add(
                ChartXValue.fromString((new DateTime(2022, i, 1)).toString("yyyy-M-d")),
                ChartYValue.fromTimeSpanInternal(new TimeSpan(i, 0, 0)));
        }

        // Data and axis labels aren't displayed correctly in Russian locale. It can be resolved in this way:
        //shape2.Chart.AxisY.NumberFormat.FormatCode = "[$-x-systime]ч:мм";
        //newChartSeries2.DataLabels.NumberFormat.FormatCode = "[$-x-systime]ч:мм";

        Shape shape3 = (Shape)shape2.deepClone(true);
        body.appendChild(new Paragraph(doc));
        body.appendChild(new Paragraph(doc));
        body.getLastParagraph().appendChild(shape3);

        shape3.getChart().getTitle().setText("New Chart 2");

        ChartSeries newChartSeries3 = shape3.getChart().getSeries().get(0);
        newChartSeries3.clear();
        // Hide axis to not show axis labels, which are sums of dates in this chart type.
        shape3.getChart().getAxisY().setHidden(true);

        for (int i = 1; i <= 4; i++)
        {
            newChartSeries3.add(
                ChartXValue.fromString(msString.format("Category {0}", i)),
                ChartYValue.fromDateTimeInternal(new DateTime(2000 + i * 5, 1, 1)));
        }

        // Data labels aren't displayed correctly in Russian locale. It can be resolved in this way:
        //newChartSeries3.DataLabels.NumberFormat.FormatCode = "ДД.ММ.ГГГГ";

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\DataApi\\TestWaterfall.docx");
    }

    /// <summary>
    /// Tests chart data manipulations in a Box & Whisker Word 2016 chart.
    /// </summary>
    @Test
    public void testBoxWhisker() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\BoxWhisker.docx");
        Body body = doc.getFirstSection().getBody();
        Shape shape = body.getShapes().get(0);
        msAssert.areEqual(3, shape.getChart().getSeries().getCount());

        ChartSeries series1 = shape.getChart().getSeries().get(0);
        ChartXValueCollection xValues1 = series1.getXValues();
        ChartYValueCollection yValues1 = series1.getYValues();
        msAssert.areEqual(SeriesLayout.BOX_WHISKER, series1.getLayoutType());

        msAssert.areEqual(22, xValues1.getCount());
        msAssert.areEqual(22, yValues1.getCount());
        msAssert.areEqual(0, series1.getBubbleSizes().getCount());
        msAssert.areEqual(ChartXValueType.STRING, xValues1.get(0).getValueType());
        msAssert.areEqual("Category 1", xValues1.get(0).getStringValue());
        msAssert.areEqual("Category 1", xValues1.get(1).getStringValue());
        msAssert.areEqual("Category 3", xValues1.get(21).getStringValue());
        msAssert.areEqual(ChartYValueType.DOUBLE, yValues1.get(0).getValueType());
        msAssert.areEqual(-7, 0.01, yValues1.get(0).getDoubleValue());
        msAssert.areEqual(-10, 0.01, yValues1.get(1).getDoubleValue());
        msAssert.areEqual(-20, 0.01, yValues1.get(21).getDoubleValue());

        ChartSeries series2 = shape.getChart().getSeries().get(1);
        ChartXValueCollection xValues2 = series2.getXValues();
        ChartYValueCollection yValues2 = series2.getYValues();

        msAssert.areEqual(22, xValues2.getCount());
        msAssert.areEqual(22, yValues2.getCount());
        msAssert.areEqual(0, series2.getBubbleSizes().getCount());
        msAssert.areEqual(ChartXValueType.STRING, xValues2.get(0).getValueType());
        msAssert.areEqual("Category 1", xValues2.get(0).getStringValue());
        msAssert.areEqual("Category 1", xValues2.get(1).getStringValue());
        msAssert.areEqual("Category 3", xValues2.get(21).getStringValue());
        msAssert.areEqual(ChartYValueType.DOUBLE, yValues2.get(0).getValueType());
        msAssert.areEqual(-3, 0.01, yValues2.get(0).getDoubleValue());
        msAssert.areEqual(1, 0.01, yValues2.get(1).getDoubleValue());
        msAssert.areEqual(16, 0.01, yValues2.get(21).getDoubleValue());

        ChartSeries series3 = shape.getChart().getSeries().get(2);
        ChartXValueCollection xValues3 = series3.getXValues();
        ChartYValueCollection yValues3 = series3.getYValues();

        msAssert.areEqual(22, xValues3.getCount());
        msAssert.areEqual(22, yValues3.getCount());
        msAssert.areEqual(0, series3.getBubbleSizes().getCount());
        msAssert.areEqual(ChartXValueType.STRING, xValues3.get(0).getValueType());
        msAssert.areEqual("Category 1", xValues3.get(0).getStringValue());
        msAssert.areEqual("Category 1", xValues3.get(1).getStringValue());
        msAssert.areEqual("Category 3", xValues3.get(21).getStringValue());
        msAssert.areEqual(ChartYValueType.DOUBLE, yValues3.get(0).getValueType());
        msAssert.areEqual(-24, 0.01, yValues3.get(0).getDoubleValue());
        msAssert.areEqual(11, 0.01, yValues3.get(1).getDoubleValue());
        msAssert.areEqual(-18, 0.01, yValues3.get(21).getDoubleValue());

        // Update data.
        // When updating, it is needed to specify the same X values in all series otherwise there are issues with
        // the chart in MS Word.

        ChartXValue category1 = ChartXValue.fromString("Category 1");
        xValues1.set(9, category1);
        xValues2.set(9, category1);
        xValues3.set(9, category1);

        yValues1.set(3, ChartYValue.fromDouble(18.0));
        yValues2.set(5, ChartYValue.fromDouble(-50));
        yValues3.set(0, ChartYValue.fromDouble(-50));

        final int REMOVED_VALUE_INDEX = 20;
        series1.remove(REMOVED_VALUE_INDEX);
        series2.remove(REMOVED_VALUE_INDEX);
        series3.remove(REMOVED_VALUE_INDEX);

        ChartXValue category3 = ChartXValue.fromString("Category 3");
        final int NEW_VALUE_INDEX = 16;
        series1.insert(NEW_VALUE_INDEX, category3, ChartYValue.fromDouble(48.0));
        series2.insert(NEW_VALUE_INDEX, category3, ChartYValue.fromDouble(0.0));
        series3.insert(NEW_VALUE_INDEX, category3, ChartYValue.fromDouble(100.0));

        ChartXValue newCategory = ChartXValue.fromString("New Category");
        series1.add(newCategory, ChartYValue.fromDouble(55.0));
        series1.add(newCategory, ChartYValue.fromDouble(-10));
        series1.add(newCategory, ChartYValue.fromDouble(14.0));
        series1.add(newCategory, ChartYValue.fromDouble(7.0));
        series2.add(newCategory, ChartYValue.fromDouble(6.0));
        series2.add(newCategory, ChartYValue.fromDouble(7.0));
        series2.add(newCategory, ChartYValue.fromDouble(-1));
        series2.add(newCategory, ChartYValue.fromDouble(11.0));
        series3.add(newCategory, ChartYValue.fromDouble(0.0));
        series3.add(newCategory, ChartYValue.fromDouble(5.0));
        series3.add(newCategory, ChartYValue.fromDouble(10.0));
        series3.add(newCategory, ChartYValue.fromDouble(15.0));

        Shape shape2 = (Shape)shape.deepClone(true);
        body.appendChild(new Paragraph(doc));
        body.getLastParagraph().appendChild(shape2);

        shape2.getChart().getTitle().setText("New Chart 1");

        ChartSeries newChartSeries2 = shape2.getChart().getSeries().get(0);
        newChartSeries2.clear();

        while (shape2.getChart().getSeries().getCount() > 1)
            shape2.getChart().getSeries().removeAt(1);

        for (int i = 1; i <= 20; i++)
        {
            newChartSeries2.add(
                ChartXValue.fromString((new DateTime(2020 + i / 11, 1, 1)).toString("yyyy-M-d")),
                ChartYValue.fromTimeSpanInternal(new TimeSpan(i, 0, 0)));
        }

        // Data and axis labels aren't displayed correctly in Russian locale. It can be resolved in this way:
        //shape2.Chart.AxisY.NumberFormat.FormatCode = "[$-x-systime]ч:мм";
        //newChartSeries2.DataLabels.NumberFormat.FormatCode = "[$-x-systime]ч:мм";

        Shape shape3 = (Shape)shape2.deepClone(true);
        body.appendChild(new Paragraph(doc));
        body.getLastParagraph().appendChild(shape3);

        shape3.getChart().getTitle().setText("New Chart 2");

        ChartSeries newChartSeries3 = shape3.getChart().getSeries().get(0);
        newChartSeries3.clear();

        while (shape3.getChart().getSeries().getCount() > 1)
            shape3.getChart().getSeries().removeAt(1);

        for (int i = 1; i <= 20; i++)
        {
            newChartSeries3.add(
                ChartXValue.fromString(msString.format("Category {0}", i / 11 + 1)),
                ChartYValue.fromDateTimeInternal(new DateTime(2000 + i, 1, 1)));
        }

        // Data labels aren't displayed correctly in Russian locale. It can be resolved in this way:
        //shape3.Chart.AxisY.NumberFormat.FormatCode = "ДД.ММ.ГГГГ";
        //newChartSeries3.DataLabels.NumberFormat.FormatCode = "ДД.ММ.ГГГГ";

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\DataApi\\TestBoxWhisker.docx");
    }

    /// <summary>
    /// Tests chart data manipulations in a Treemap Word 2016 chart.
    /// </summary>
    @Test
    public void testTreemap() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Treemap.docx");
        Body body = doc.getFirstSection().getBody();
        Shape shape1 = body.getShapes().get(0);
        msAssert.areEqual(1, shape1.getChart().getSeries().getCount());

        ChartSeries series1 = shape1.getChart().getSeries().get(0);
        ChartXValueCollection xValues1 = series1.getXValues();
        ChartYValueCollection yValues1 = series1.getYValues();
        msAssert.areEqual(SeriesLayout.TREEMAP, series1.getLayoutType());

        msAssert.areEqual(16, xValues1.getCount());
        msAssert.areEqual(16, yValues1.getCount());
        msAssert.areEqual(0, series1.getBubbleSizes().getCount());
        msAssert.areEqual(ChartXValueType.MULTILEVEL, xValues1.get(0).getValueType());
        ChartMultilevelValue xValue1Levels = xValues1.get(0).getMultilevelValue();
        msAssert.areEqual("Branch 1", xValue1Levels.getLevel1());
        msAssert.areEqual("Stem 1", xValue1Levels.getLevel2());
        msAssert.areEqual("Leaf 1", xValue1Levels.getLevel3());
        ChartMultilevelValue xValue2Levels = xValues1.get(1).getMultilevelValue();
        msAssert.areEqual("Branch 1", xValue2Levels.getLevel1());
        msAssert.areEqual("Stem 1", xValue2Levels.getLevel2());
        msAssert.areEqual("Leaf 2", xValue2Levels.getLevel3());
        ChartMultilevelValue lastXValueLevels = xValues1.get(15).getMultilevelValue();
        msAssert.areEqual("Branch 3", lastXValueLevels.getLevel1());
        msAssert.areEqual("Stem 6", lastXValueLevels.getLevel2());
        msAssert.areEqual("Leaf 16", lastXValueLevels.getLevel3());

        msAssert.areEqual(ChartYValueType.DOUBLE, yValues1.get(0).getValueType());
        msAssert.areEqual(22, 0.01, yValues1.get(0).getDoubleValue());
        msAssert.areEqual(12, 0.01, yValues1.get(1).getDoubleValue());
        msAssert.areEqual(11, 0.01, yValues1.get(15).getDoubleValue());

        // Update data.

        xValues1.set(7, ChartXValue.fromMultilevelValue(new ChartMultilevelValue(
            xValues1.get(6).getMultilevelValue().getLevel1(),
            "New Stem 1",
            xValues1.get(6).getMultilevelValue().getLevel3())));

        // Set a value that already exists.
        xValues1.set(8, xValues1.get(9));

        yValues1.set(1, ChartYValue.fromDouble(50.0));
        yValues1.set(10, ChartYValue.fromDouble(9.0));

        series1.remove(6);

        final String NEW_BRANCH = "New Branch";

        xValues1.set(13, ChartXValue.fromMultilevelValue(new ChartMultilevelValue(
            NEW_BRANCH,
            "New Stem 1",
            "Leaf 2")));

        series1.insert(13,
            ChartXValue.fromMultilevelValue(new ChartMultilevelValue(NEW_BRANCH, "New Stem 1", "Leaf 1")),
            ChartYValue.fromDouble(100.0));

        series1.add(
            ChartXValue.fromMultilevelValue(new ChartMultilevelValue(NEW_BRANCH, "New Stem 2", "Leaf 1")),
            ChartYValue.fromDouble(7.0));
        series1.add(
            ChartXValue.fromMultilevelValue(new ChartMultilevelValue(NEW_BRANCH, "New Stem 2", "Leaf 2")),
            ChartYValue.fromDouble(17.0));

        Shape shape2 = body.getShapes().get(1);
        msAssert.areEqual(1, shape2.getChart().getSeries().getCount());

        ChartSeries series2 = shape2.getChart().getSeries().get(0);
        series2.getDataLabels().setShowCategoryName(true);
        series2.getDataLabels().setShowValue(true);
        series2.clear();

        for (int i = 1; i <= 5; i++)
        {
            series2.add(
                ChartXValue.fromMultilevelValue(new ChartMultilevelValue("Group 1", msString.format("Item {0}", i))),
                ChartYValue.fromTimeSpanInternal(new TimeSpan(i, 0, 0)));
        }

        for (int i = 1; i <= 3; i++)
        {
            series2.add(
                ChartXValue.fromMultilevelValue(new ChartMultilevelValue("Group 2", msString.format("Item {0}", i))),
                ChartYValue.fromTimeSpanInternal(new TimeSpan(i * 2, 0, 0)));
        }

        // Data labels aren't displayed correctly in Russian locale. It can be resolved in this way:
        //series2.DataLabels.NumberFormat.FormatCode = "[$-x-systime]ч:мм";

        Shape shape3 = (Shape)shape1.deepClone(true);
        body.appendChild(new Paragraph(doc));
        body.getLastParagraph().appendChild(shape3);

        shape3.getChart().getTitle().setText("Single-Level Date Chart");

        ChartSeries series3 = shape3.getChart().getSeries().get(0);
        series3.clear();

        series3.getDataLabels().setShowCategoryName(true);
        series3.getDataLabels().setShowValue(true);
        series3.clear();

        for (int i = 1; i <= 10; i++)
        {
            series3.add(
                ChartXValue.fromMultilevelValue(new ChartMultilevelValue(msString.format("Item {0}", i))),
                ChartYValue.fromDateTimeInternal(new DateTime(2000 + i - 1, 1, 1)));
        }

        // Data labels aren't displayed correctly in Russian locale. It can be resolved in this way:
        //series3.DataLabels.NumberFormat.FormatCode = "ДД.ММ.ГГГГ";

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\DataApi\\TestTreemap.docx");
    }



    /// <summary>
    /// Tests the correspondence between chart data values and data points/labels in a Treemap chart series.
    /// </summary>
    @Test
    public void testTreemapDataLabelIndexes() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Treemap.docx");
        Document clonedDocument = doc.deepClone();
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        ChartSeries series = shapes.get(0).getChart().getSeries().get(0);

        msAssert.areEqual(16, series.getXValues().getCount());

        // Remove data values starting from the end.
        // Not all labels of the series are materialized, they have string.Empty text in the method calls below.
        checkDataLabelRemoval(series, 15, 24, "Leaf 16, 11");
        checkDataLabelRemoval(series, 14, 23, "Leaf 15, 10");
        checkDataLabelRemoval(series, 13, 21, ""/*Stem 6*/, "Leaf 14, 86");
        checkDataLabelRemoval(series, 12, 20, "Leaf 13, 19");
        checkDataLabelRemoval(series, 11, 17, "Branch 3", ""/*Stem 5*/, "Leaf 12, 16");
        checkDataLabelRemoval(series, 10, 16, "Leaf 11, 89");
        checkDataLabelRemoval(series, 9, 14, ""/*Stem 4*/, "Leaf 10, 24");
        checkDataLabelRemoval(series, 8, 13, "Leaf 9, 23");
        checkDataLabelRemoval(series, 7, 10, "Branch 2", ""/*Stem 3*/, "Leaf 8, 25");
        checkDataLabelRemoval(series, 6, 9, "Leaf 7, 9");
        checkDataLabelRemoval(series, 5, 8, "Leaf 6, 17");
        checkDataLabelRemoval(series, 4, 7, "Leaf 5, 88");
        checkDataLabelRemoval(series, 3, 5, ""/*Stem 2*/, "Leaf 4, 87");
        checkDataLabelRemoval(series, 2, 4, "Leaf 3, 18");
        checkDataLabelRemoval(series, 1, 3, "Leaf 2, 12");
        checkDataLabelRemoval(series, 0, 0, "Branch 1", ""/*Stem 1*/, "Leaf 1, 22");

        ensureNoDataPointsAndLabelsExist(series);

        doc = clonedDocument.deepClone();
        shapes = doc.getFirstSection().getBody().getShapes();
        series = shapes.get(0).getChart().getSeries().get(0);

        msAssert.areEqual(16, series.getXValues().getCount());

        // Remove data values starting from the beginning.
        // Not all labels of the series are materialized, they have string.Empty text in the method calls below.
        checkDataLabelRemoval(series, 0, 2, "Leaf 1, 22");
        checkDataLabelRemoval(series, 0, 2, "Leaf 2, 12");
        checkDataLabelRemoval(series, 0, 1, ""/*Stem 1*/, "Leaf 3, 18");
        checkDataLabelRemoval(series, 0, 2, "Leaf 4, 87");
        checkDataLabelRemoval(series, 0, 2, "Leaf 5, 88");
        checkDataLabelRemoval(series, 0, 2, "Leaf 6, 17");
        checkDataLabelRemoval(series, 0, 0, "Branch 1", ""/*Stem 2*/, "Leaf 7, 9");
        checkDataLabelRemoval(series, 0, 2, "Leaf 8, 25");
        checkDataLabelRemoval(series, 0, 1, ""/*Stem 3*/, "Leaf 9, 23");
        checkDataLabelRemoval(series, 0, 2, "Leaf 10, 24");
        checkDataLabelRemoval(series, 0, 0, "Branch 2", ""/*Stem 4*/, "Leaf 11, 89");
        checkDataLabelRemoval(series, 0, 2, "Leaf 12, 16");
        checkDataLabelRemoval(series, 0, 1, ""/*Stem 5*/, "Leaf 13, 19");
        checkDataLabelRemoval(series, 0, 2, "Leaf 14, 86");
        checkDataLabelRemoval(series, 0, 2, "Leaf 15, 10");
        checkDataLabelRemoval(series, 0, 0, "Branch 3", ""/*Stem 6*/, "Leaf 16, 11");

        ensureNoDataPointsAndLabelsExist(series);

        doc = clonedDocument;
        shapes = doc.getFirstSection().getBody().getShapes();
        series = shapes.get(0).getChart().getSeries().get(0);

        msAssert.areEqual(16, series.getXValues().getCount());

        ChartXValue xValue =
            ChartXValue.fromMultilevelValue(new ChartMultilevelValue("Branch 1", "Stem 1", "New Leaf"));
        ChartYValue yValue = ChartYValue.fromDouble(50.0);
        checkDataLabelInsertion(series, 3, xValue, yValue, 5, 1);
        xValue = ChartXValue.fromMultilevelValue(new ChartMultilevelValue("Branch 1", "Stem 3", "New Leaf"));
        checkDataLabelInsertion(series, 8, xValue, yValue, 11, 2);
        xValue = ChartXValue.fromMultilevelValue(new ChartMultilevelValue("New Branch", "Stem 1", "Leaf 1"));
        checkDataLabelInsertion(series, 9, xValue, yValue, 13, 3);
        checkDataLabelInsertion(series, 17, xValue, yValue, 29, 5);

        xValue = ChartXValue.fromMultilevelValue(new ChartMultilevelValue("Branch 3", "Stem 6", "Leaf 1"));
        checkDataLabelChange(series, 17, xValue, 29, 5, 1);
        xValue = ChartXValue.fromMultilevelValue(new ChartMultilevelValue("New Branch", "Stem 1", "Leaf 1"));
        checkDataLabelChange(series, 14, xValue, 26, 0, 2);
        xValue = ChartXValue.fromMultilevelValue(new ChartMultilevelValue("Branch 1", "Stem 2a", "Leaf 5"));
        checkDataLabelChange(series, 5, xValue, 8, 1, 3);
    }


    /// <summary>
    /// Tests the correspondence between chart data values and data points/labels in a Pareto chart series.
    /// </summary>
    @Test
    public void testParetoDataLabelIndexes() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        Document clonedDocument = doc.deepClone();
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        ChartSeries series = shapes.get(0).getChart().getSeries().get(1);

        msAssert.areEqual(50, series.getXValues().getCount());

        // Remove all duplicate values of X category.
        // Remove the first item. After it, the first 4 X values has different value.
        checkDataLabelRemoval(series, 0, -1);
        // Remove all values except the first 4 ones.
        while (series.getXValues().getCount() > 4)
            checkDataLabelRemoval(series, 4, -1);

        // Now removing any value will cause removing the last label. This is the same behavior as MS Word has.
        checkDataLabelRemoval(series, 3, 3, "300%");
        checkDataLabelRemoval(series, 2, 2, "700%");
        checkDataLabelRemoval(series, 1, 1, "1700%");
        checkDataLabelRemoval(series, 0, 0, "2300%");

        ensureNoDataPointsAndLabelsExist(series);

        doc = clonedDocument;
        shapes = doc.getFirstSection().getBody().getShapes();
        series = shapes.get(0).getChart().getSeries().get(1);

        msAssert.areEqual(50, series.getXValues().getCount());

        ChartXValue xValue = ChartXValue.fromString("New Category");
        ChartYValue yValue = ChartYValue.fromDouble(50.0);
        checkDataLabelInsertion(series, 5, xValue, yValue, 4, 1);
        yValue = ChartYValue.fromDouble(1.0);
        checkDataLabelInsertion(series, 10, xValue, yValue, -1, 0);

        checkDataLabelChange(series, 0, xValue, -1, 0, 0);
        xValue = ChartXValue.fromString("New Category 2");
        checkDataLabelChange(series, 1, xValue, 5, 0, 1);
        xValue = ChartXValue.fromString("Category 2");
        checkDataLabelChange(series, 1, xValue, 5, 1, 0);
    }



    /// <summary>
    /// Tests the correspondence between chart data values and data points/labels in a Waterfall chart series.
    /// </summary>
    @Test
    public void testWaterfallDataLabelIndexes() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Waterfall.docx");
        Document clonedDocument = doc.deepClone();
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        ChartSeries series = shapes.get(0).getChart().getSeries().get(0);

        msAssert.areEqual(8, series.getXValues().getCount());

        // Not all labels of the series are materialized, they have string.Empty text in the method calls below.
        checkDataLabelRemoval(series, 0, 0, "");
        checkDataLabelRemoval(series, 0, 0, "");
        checkDataLabelRemoval(series, 0, 0, "");
        checkDataLabelRemoval(series, 0, 0, "Series1\rCategory 4\r-40");
        checkDataLabelRemoval(series, 0, 0, "Series1\rCategory 5\r130");
        checkDataLabelRemoval(series, 0, 0, "");
        checkDataLabelRemoval(series, 0, 0, "");
        checkDataLabelRemoval(series, 0, 0, "");

        ensureNoDataPointsAndLabelsExist(series);

        doc = clonedDocument;
        shapes = doc.getFirstSection().getBody().getShapes();
        series = shapes.get(0).getChart().getSeries().get(0);

        msAssert.areEqual(8, series.getXValues().getCount());

        ChartXValue xValue = ChartXValue.fromString("New Category");
        ChartYValue yValue = ChartYValue.fromDouble(10.0);
        checkDataLabelInsertion(series, 2, xValue, yValue, 2, 1);
        xValue = ChartXValue.fromString("New Category 2");
        yValue = ChartYValue.fromDouble(20.0);
        checkDataLabelInsertion(series, 5, xValue, yValue, 5, 1);

        xValue = ChartXValue.fromString("Category 9");
        checkDataLabelChange(series, 5, xValue, -1, 0, 0);
    }



    /// <summary>
    /// Tests the <see cref="ChartXValue.Equals(object)"/> and <see cref="ChartXValue.GetHashCode"/> methods.
    /// </summary>
    @Test
    public void testChartXValueEquality()
    {
        ChartXValue xValue1 = ChartXValue.fromString("123");
        ChartXValue xValue2 = ChartXValue.fromString("123");
        ChartXValue xValue3 = ChartXValue.fromString("1234");
        msAssert.isTrue(xValue1.equals(xValue2));
        msAssert.isFalse(xValue1.equals(xValue3));

        ChartXValue xValue4 = ChartXValue.fromDouble(10.0);
        ChartXValue xValue5 = ChartXValue.fromDouble(10.0);
        ChartXValue xValue6 = ChartXValue.fromDouble(10.01);
        msAssert.isTrue(xValue4.equals(xValue5));
        msAssert.isFalse(xValue4.equals(xValue6));

        ChartXValue xValue7 = ChartXValue.fromDateTimeInternal(new DateTime(2022, 10, 12, 11, 39, 0));
        ChartXValue xValue8 = ChartXValue.fromDateTimeInternal(new DateTime(2022, 10, 12, 11, 39, 0));
        ChartXValue xValue9 = ChartXValue.fromDateTimeInternal(new DateTime(2022, 10, 12, 11, 39, 1));
        msAssert.isTrue(xValue7.equals(xValue8));
        msAssert.isFalse(xValue7.equals(xValue9));

        ChartXValue xValue10 = ChartXValue.fromTimeSpanInternal(new TimeSpan(2, 30, 0));
        ChartXValue xValue11 = ChartXValue.fromTimeSpanInternal(new TimeSpan(2, 30, 0));
        ChartXValue xValue12 = ChartXValue.fromTimeSpanInternal(new TimeSpan(2, 30, 1));
        msAssert.isTrue(xValue10.equals(xValue11));
        msAssert.isFalse(xValue10.equals(xValue12));

        HashSetGeneric<ChartXValue> hashSet = new HashSetGeneric<ChartXValue>();
        hashSet.add(xValue1);
        hashSet.add(xValue4);
        hashSet.add(xValue7);
        hashSet.add(xValue10);
        msAssert.isTrue(hashSet.contains(xValue2));
        msAssert.isFalse(hashSet.contains(xValue3));
        msAssert.isTrue(hashSet.contains(xValue5));
        msAssert.isFalse(hashSet.contains(xValue6));
        msAssert.isTrue(hashSet.contains(xValue8));
        msAssert.isFalse(hashSet.contains(xValue9));
        msAssert.isTrue(hashSet.contains(xValue11));
        msAssert.isFalse(hashSet.contains(xValue12));
    }

    /// <summary>
    /// Tests the <see cref="ChartYValue.Equals(object)"/> and <see cref="ChartYValue.GetHashCode"/> methods.
    /// </summary>
    @Test
    public void testChartYValueEquality()
    {
        ChartYValue yValue1 = ChartYValue.fromDouble(10.0);
        ChartYValue yValue2 = ChartYValue.fromDouble(10.0);
        ChartYValue yValue3 = ChartYValue.fromDouble(10.01);
        msAssert.isTrue(yValue1.equals(yValue2));
        msAssert.isFalse(yValue1.equals(yValue3));

        ChartYValue yValue4 = ChartYValue.fromDateTimeInternal(new DateTime(2022, 10, 12, 11, 39, 0));
        ChartYValue yValue5 = ChartYValue.fromDateTimeInternal(new DateTime(2022, 10, 12, 11, 39, 0));
        ChartYValue yValue6 = ChartYValue.fromDateTimeInternal(new DateTime(2022, 10, 12, 11, 39, 1));
        msAssert.isTrue(yValue4.equals(yValue5));
        msAssert.isFalse(yValue4.equals(yValue6));

        ChartYValue yValue7 = ChartYValue.fromTimeSpanInternal(new TimeSpan(2, 30, 0));
        ChartYValue yValue8 = ChartYValue.fromTimeSpanInternal(new TimeSpan(2, 30, 0));
        ChartYValue yValue9 = ChartYValue.fromTimeSpanInternal(new TimeSpan(2, 30, 1));
        msAssert.isTrue(yValue7.equals(yValue8));
        msAssert.isFalse(yValue7.equals(yValue9));

        HashSetGeneric<ChartYValue> hashSet = new HashSetGeneric<ChartYValue>();
        hashSet.add(yValue1);
        hashSet.add(yValue4);
        hashSet.add(yValue7);
        msAssert.isTrue(hashSet.contains(yValue2));
        msAssert.isFalse(hashSet.contains(yValue3));
        msAssert.isTrue(hashSet.contains(yValue5));
        msAssert.isFalse(hashSet.contains(yValue6));
        msAssert.isTrue(hashSet.contains(yValue8));
        msAssert.isFalse(hashSet.contains(yValue9));
    }

    /// <summary>
    /// Tests that an exception is thrown when trying to add a chart X value of a non-supported type
    /// (<see cref="ChartXValueType"/>) to an empty series of a Word 2016 chart.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe series cannot contain an X value of this type.\\E", dataProvider = "testAddingWrongXValueType2016DataProvider")
    public void testAddingWrongXValueType2016(String fileName, /*ChartXValueType*/int valueType) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        // Find a series that can be updated.
        ChartSeries series = null;
        for (ChartSeries currentSeries : doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries())
        {
            // ParetoLine series cannot have data.
            if (currentSeries.getLayoutType() != SeriesLayout.PARETO_LINE)
            {
                series = currentSeries;
                break;
            }
        }

        Debug.assertTrue(series != null);

        series.clear();
        series.add(createXValue(valueType));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testAddingWrongXValueType2016DataProvider")
	public static Object[][] testAddingWrongXValueType2016DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Charts\\Word2016Charts\\Pareto.docx",  ChartXValueType.DOUBLE},
			{"Model\\Charts\\Word2016Charts\\Pareto.docx",  ChartXValueType.DATE_TIME},
			{"Model\\Charts\\Word2016Charts\\Pareto.docx",  ChartXValueType.TIME},
			{"Model\\Charts\\Word2016Charts\\Pareto.docx",  ChartXValueType.MULTILEVEL},
			{"Model\\Charts\\Word2016Charts\\Waterfall.docx",  ChartXValueType.DOUBLE},
			{"Model\\Charts\\Word2016Charts\\Waterfall.docx",  ChartXValueType.DATE_TIME},
			{"Model\\Charts\\Word2016Charts\\Waterfall.docx",  ChartXValueType.TIME},
			{"Model\\Charts\\Word2016Charts\\Waterfall.docx",  ChartXValueType.MULTILEVEL},
			{"Model\\Charts\\Word2016Charts\\BoxWhisker.docx",  ChartXValueType.DOUBLE},
			{"Model\\Charts\\Word2016Charts\\BoxWhisker.docx",  ChartXValueType.DATE_TIME},
			{"Model\\Charts\\Word2016Charts\\BoxWhisker.docx",  ChartXValueType.TIME},
			{"Model\\Charts\\Word2016Charts\\BoxWhisker.docx",  ChartXValueType.MULTILEVEL},
		};
	}

    /// <summary>
    /// Tests that an exception is thrown when trying to add a chart value of a different type
    /// (<see cref="ChartXValueType"/>/<see cref="ChartYValueType"/>) than a series already contains.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe value must be of the same type as the existing items in the collection.\\E", dataProvider = "testAddingWrongTypeValueDataProvider")
    public void testAddingWrongTypeValue(/*ChartXValueType*/int initialXValueType, /*ChartYValueType*/int initialYValueType,
        /*ChartXValueType*/int addingXValueType, /*ChartYValueType*/int addingYValueType) throws Exception
    {
        ChartSeries series = createAreaChartWithEmptySeries();

        // Add the first data point to the series. This specifies data types of X/Y values stored in the series.
        // Do in a try/catch block to ensure that the exception being checked in the test is not thrown.
        try
        {
            series.add(createXValue(initialXValueType), createYValue(initialYValueType));
        }
        catch(Exception e)
        {
            Assert.fail("This exception is not expected.");
        }

        series.add(createXValue(addingXValueType), createYValue(addingYValueType));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testAddingWrongTypeValueDataProvider")
	public static Object[][] testAddingWrongTypeValueDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.TIME,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE},
			{ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE,  ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE},
			{ChartXValueType.TIME,  ChartYValueType.DOUBLE,  ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE,  ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.TIME,  ChartYValueType.DOUBLE,  ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE,  ChartXValueType.TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE,  ChartXValueType.TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.DATE_TIME},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.TIME},
			{ChartXValueType.STRING,  ChartYValueType.DATE_TIME,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DATE_TIME,  ChartXValueType.STRING,  ChartYValueType.TIME},
			{ChartXValueType.STRING,  ChartYValueType.TIME,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.TIME,  ChartXValueType.STRING,  ChartYValueType.DATE_TIME},
		};
	}

    /// <summary>
    /// Tests that an exception is thrown when trying to insert a chart value of a different type
    /// (<see cref="ChartXValueType"/>/<see cref="ChartYValueType"/>) than a series already contains.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe value must be of the same type as the existing items in the collection.\\E", dataProvider = "testInsertingWrongTypeValueDataProvider")
    public void testInsertingWrongTypeValue(/*ChartXValueType*/int initialXValueType, /*ChartYValueType*/int initialYValueType,
        /*ChartXValueType*/int addingXValueType, /*ChartYValueType*/int addingYValueType) throws Exception
    {
        ChartSeries series = createAreaChartWithEmptySeries();

        // Add the first data point to the series. This specifies data types of X/Y values stored in the series.
        // Do in a try/catch block to ensure that the exception being checked in the test is not thrown.
        try
        {
            series.add(createXValue(initialXValueType), createYValue(initialYValueType));
        }
        catch(Exception e)
        {
            Assert.fail("This exception is not expected.");
        }

        series.insert(1, createXValue(addingXValueType), createYValue(addingYValueType));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testInsertingWrongTypeValueDataProvider")
	public static Object[][] testInsertingWrongTypeValueDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.TIME,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE},
			{ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE,  ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE},
			{ChartXValueType.TIME,  ChartYValueType.DOUBLE,  ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE,  ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.TIME,  ChartYValueType.DOUBLE,  ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.DOUBLE,  ChartYValueType.DOUBLE,  ChartXValueType.TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.DATE_TIME,  ChartYValueType.DOUBLE,  ChartXValueType.TIME,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.DATE_TIME},
			{ChartXValueType.STRING,  ChartYValueType.DOUBLE,  ChartXValueType.STRING,  ChartYValueType.TIME},
			{ChartXValueType.STRING,  ChartYValueType.DATE_TIME,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.DATE_TIME,  ChartXValueType.STRING,  ChartYValueType.TIME},
			{ChartXValueType.STRING,  ChartYValueType.TIME,  ChartXValueType.STRING,  ChartYValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartYValueType.TIME,  ChartXValueType.STRING,  ChartYValueType.DATE_TIME},
		};
	}

    /// <summary>
    /// Tests that an exception is thrown when trying to set a chart X value of a different type
    /// (<see cref="ChartXValueType"/>) than a series already contains.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe value must be of the same type as the existing items in the collection.\\E", dataProvider = "testSettingWrongTypeXValueDataProvider")
    public void testSettingWrongTypeXValue(/*ChartXValueType*/int initialXValueType, /*ChartXValueType*/int settingXValueType) throws Exception
    {
        ChartSeries series = createAreaChartWithEmptySeries();

        // Add the first data point to the series. This specifies data types of X/Y values stored in the series.
        // Do in a try/catch block to ensure that the exception being checked in the test is not thrown.
        try
        {
            series.add(createXValue(initialXValueType), ChartYValue.fromDouble(1.0));
        }
        catch(Exception e)
        {
            Assert.fail("This exception is not expected.");
        }

        series.getXValues().set(0, createXValue(settingXValueType));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSettingWrongTypeXValueDataProvider")
	public static Object[][] testSettingWrongTypeXValueDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartXValueType.DOUBLE,  ChartXValueType.STRING},
			{ChartXValueType.DATE_TIME,  ChartXValueType.STRING},
			{ChartXValueType.TIME,  ChartXValueType.STRING},
			{ChartXValueType.STRING,  ChartXValueType.DOUBLE},
			{ChartXValueType.DATE_TIME,  ChartXValueType.DOUBLE},
			{ChartXValueType.TIME,  ChartXValueType.DOUBLE},
			{ChartXValueType.STRING,  ChartXValueType.DATE_TIME},
			{ChartXValueType.DOUBLE,  ChartXValueType.DATE_TIME},
			{ChartXValueType.TIME,  ChartXValueType.DATE_TIME},
			{ChartXValueType.STRING,  ChartXValueType.TIME},
			{ChartXValueType.DOUBLE,  ChartXValueType.TIME},
			{ChartXValueType.DATE_TIME,  ChartXValueType.TIME},
		};
	}

    /// <summary>
    /// Tests that an exception is thrown when trying to set a chart Y value of a different type
    /// (<see cref="ChartYValueType"/>) than a series already contains.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe value must be of the same type as the existing items in the collection.\\E", dataProvider = "testSettingWrongTypeYValueDataProvider")
    public void testSettingWrongTypeYValue(/*ChartYValueType*/int initialYValueType, /*ChartYValueType*/int settingYValueType) throws Exception
    {
        ChartSeries series = createAreaChartWithEmptySeries();

        // Add the first data point to the series. This specifies data types of X/Y values stored in the series.
        // Do in a try/catch block to ensure that the exception being checked in the test is not thrown.
        try
        {
            series.add(ChartXValue.fromString("Category"), createYValue(initialYValueType));
        }
        catch(Exception e)
        {
            Assert.fail("This exception is not expected.");
        }

        series.getYValues().set(0, createYValue(settingYValueType));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSettingWrongTypeYValueDataProvider")
	public static Object[][] testSettingWrongTypeYValueDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartYValueType.DATE_TIME,  ChartYValueType.DOUBLE},
			{ChartYValueType.TIME,  ChartYValueType.DOUBLE},
			{ChartYValueType.DOUBLE,  ChartYValueType.DATE_TIME},
			{ChartYValueType.TIME,  ChartYValueType.DATE_TIME},
			{ChartYValueType.DOUBLE,  ChartYValueType.TIME},
			{ChartYValueType.DATE_TIME,  ChartYValueType.TIME},
		};
	}

    /// <summary>
    /// Tests the <see cref="ChartSeries.ClearValues"/> method.
    /// </summary>
    @Test
    public void testClearValues() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        ChartSeries series = shape.getChart().getSeries().get(0);

        series.hasDataLabels(true);
        // Set some label formatting to be checked after clearing values.
        final String NUMBER_FORMAT = "#,##0.00";
        series.getDataLabels().get(1).getNumberFormat().setFormatCode(NUMBER_FORMAT);

        series.clearValues();
        series.add(ChartXValue.fromString("Category 1"), ChartYValue.fromDouble(10.0));
        series.add(ChartXValue.fromString("Category 2"), ChartYValue.fromDouble(3.5));

        // Checks that series.ClearValues has not deleted the labels.
        msAssert.areEqual(NUMBER_FORMAT, series.getDataLabels().get(1).getNumberFormat().getFormatCode());
        msAssert.areNotEqual(NUMBER_FORMAT, series.getDataLabels().get(0).getNumberFormat().getFormatCode());

        series.clear();
        series.add(ChartXValue.fromString("Category 1"), ChartYValue.fromDouble(7.0));
        series.add(ChartXValue.fromString("Category 2"), ChartYValue.fromDouble(5.2));

        // Checks that series.Clear has deleted the labels.
        msAssert.areNotEqual(NUMBER_FORMAT, series.getDataLabels().get(1).getNumberFormat().getFormatCode());
        msAssert.areNotEqual(NUMBER_FORMAT, series.getDataLabels().get(0).getNumberFormat().getFormatCode());
    }

    /// <summary>
    /// WORDSNET-26164 Errors with dates of 1900 year in charts
    /// When a series is being added using the <see cref="ChartSeriesCollection.Add(string,DateTime[],double[])"/>
    /// method, the <see cref="DmlChartUtil.GetDoubleFromDate"/> method is used now instead of
    /// <see cref="DateTime.ToOADate"/> to get numeric representation of datetime values. And the method
    /// <see cref="DmlChartUtil.GetDoubleFromDate "/> returned wrong value when converting the 01/03/1900 date.
    /// </summary>
    @Test
    public void test26164() throws Exception
    {
        // Generate dates for X values of charts.

        DateTime[] dates = new DateTime[65];
        double[] values = new double[dates.length];

        DateTime startValue = new DateTime(1899, 12, 31);
        for (int i = 0; i < dates.length; i++)
        {
            dates[i] = startValue.addDays(i);
            values[i] = i + 5;
        }

        Document doc = new Document();
        doc.getFirstSection().getPageSetup().setOrientation(Orientation.LANDSCAPE);

        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add a chart and fill data using ChartSeriesCollection.Add.

        Shape shape1 = builder.insertChart(ChartType.COLUMN, 640.0, 240.0);
        Chart chart1 = shape1.getChart();
        chart1.getTitle().setText("ChartSeriesCollection.Add");
        chart1.getSeries().clear();

        ChartSeries series1 = chart1.getSeries().addInternal("Series 1", dates, values);

        // Check dates.
        for (int i = 0; i < dates.length; i++)
            msAssert.areEqual(dates[i], series1.getXValues().get(i).getDateTimeValueInternal());

        // Add a chart and fill data using ChartSeries.Add.

        CompositeNode paragraph = shape1.getAncestor(NodeType.PARAGRAPH);
        CompositeNode newParagraph = (CompositeNode)paragraph.deepClone(true);
        paragraph.insertNext(newParagraph);
        Shape shape2 = (Shape)newParagraph.getChild(NodeType.SHAPE, 0, true);
        Chart chart2 = shape2.getChart();
        chart2.getTitle().setText("ChartSeries.Add");
        ChartSeries series2 = chart2.getSeries().get(0);

        series2.clearValues();
        for (int i = 0; i < dates.length; i++)
            series2.add(ChartXValue.fromDateTimeInternal(dates[i]), ChartYValue.fromDouble(values[i]));

        // Check dates.
        for (int i = 0; i < dates.length; i++)
            msAssert.areEqual(dates[i], series2.getXValues().get(i).getDateTimeValueInternal());

        // The charts in the output document contain the '31/12/1899' date without label, which is correct, because
        // MS Word does not support dates before 01/01/1900. And there are empty chart values that relate to the dates
        // 00/01/1900 and 29/02/1900 that are existing dates in MS Word but not in .NET.

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\DataApi\\Test26164.docx");
    }

    /// <summary>
    /// Tests the <see cref="DmlChartUtil.GetDoubleFromDate"/> and <see cref="DmlChartUtil.GetDateFromDouble"/>
    /// methods that are used in charts.
    /// </summary>
    @Test
    public void testDateTimeConversion()
    {
        msAssert.areEqual(59, 0.001, DmlChartUtil.getDoubleFromDate(new DateTime(1900, 2, 28)));
        msAssert.areEqual(61, 0.001, DmlChartUtil.getDoubleFromDate(new DateTime(1900, 3, 1)));

        final double ONE_MINUTE = 1d / 24.0 / 60.0;
        msAssert.areEqual(59.0 + ONE_MINUTE, 0.001, DmlChartUtil.getDoubleFromDate(new DateTime(1900, 2, 28, 0, 1, 0)));
        msAssert.areEqual(61.0 + ONE_MINUTE, 0.001, DmlChartUtil.getDoubleFromDate(new DateTime(1900, 3, 1, 0, 1, 0)));

        msAssert.areEqual(new DateTime(1900, 2, 28), DmlChartUtil.getDateFromDouble(59.0));
        msAssert.areEqual(new DateTime(1900, 3, 1), DmlChartUtil.getDateFromDouble(61.0));

        msAssert.areEqual(new DateTime(1900, 2, 28, 3, 0, 0), DmlChartUtil.getDateFromDouble(59.125));
        msAssert.areEqual(new DateTime(1900, 3, 1, 3, 0, 0), DmlChartUtil.getDateFromDouble(61.125));
    }

    /// <summary>
    /// WORDSNET-27533 Support for displaying numbers as decimals in Aspose.Words Charts
    /// An ability to set format code for X, Y, Buble Size data collections has been implemented.
    /// </summary>
    @Test
    public void test27533() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape1 = builder.insertChart(ChartType.BUBBLE, 432.0, 252.0);
        ChartSeriesCollection seriesCollection1 = shape1.getChart().getSeries();
        seriesCollection1.clear();

        ChartSeries series1 = seriesCollection1.add(
            "Series1",
            new double[] { 1.0, 1.9, 2.45, 3.0 },
            new double[] { 4.0, 5.9, 1.82, 4.0 },
            new double[] { 2.0, 1.1, 2.95, 2.0 });

        series1.hasDataLabels(true);
        series1.getDataLabels().setShowCategoryName(true);
        series1.getDataLabels().setShowValue(true);
        series1.getDataLabels().setShowBubbleSize(true);

        series1.getXValues().setFormatCode("#,##0.0#");
        series1.getYValues().setFormatCode("#,##0.0#");
        series1.getBubbleSizes().setFormatCode("#,##0.0#");

        Shape shape2 = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        ChartSeriesCollection seriesCollection2 = shape2.getChart().getSeries();
        seriesCollection2.clear();

        ChartSeries series2_1 = seriesCollection2.add(
            "Series1",
            new String[] { "Category1", "Category2", "Category3" },
            new double[] { 4.0, 5.9, 1.82 });

        series2_1.hasDataLabels(true);
        series2_1.getDataLabels().setShowValue(true);

        series2_1.getYValues().setFormatCode("#,##0.00");

        ChartSeries series2_2 = seriesCollection2.add(
            "Series1",
            new String[] { "Category1", "Category2", "Category3" },
            new double[] { 1.0, 1.9, 2.45 });

        series2_2.hasDataLabels(true);
        series2_2.getDataLabels().setShowValue(true);

        // Test setting empty values.
        series2_2.getYValues().setFormatCode(null);

        // Test time format.
        Shape shape3 = builder.insertChart(ChartType.SCATTER, 432.0, 252.0);
        ChartSeriesCollection seriesCollection3 = shape3.getChart().getSeries();
        seriesCollection3.clear();

        ChartSeries series3 = seriesCollection3.add("Series1", new double[] { 0.0 }, new double[] { 0.0 });
        series3.clearValues();
        series3.add(ChartXValue.fromTimeSpanInternal(new TimeSpan(8, 0, 0)), ChartYValue.fromTimeSpanInternal(new TimeSpan(1, 0, 0)));
        series3.add(ChartXValue.fromTimeSpanInternal(new TimeSpan(11, 45, 0)), ChartYValue.fromTimeSpanInternal(new TimeSpan(2, 0, 0)));
        series3.add(ChartXValue.fromTimeSpanInternal(new TimeSpan(14, 30, 0)), ChartYValue.fromTimeSpanInternal(new TimeSpan(3, 0, 0)));
        series3.add(ChartXValue.fromTimeSpanInternal(new TimeSpan(18, 0, 0)), ChartYValue.fromTimeSpanInternal(new TimeSpan(4, 0, 0)));

        series3.hasDataLabels(true);
        series3.getDataLabels().setShowCategoryName(true);
        series3.getDataLabels().setShowValue(true);

        series3.getXValues().setFormatCode("h:mm\\ am/pm");
        series3.getYValues().setFormatCode("h:mm\\ am/pm");

        // Test date format.
        Shape shape4 = builder.insertChart(ChartType.SCATTER, 432.0, 252.0);
        ChartSeriesCollection seriesCollection4 = shape4.getChart().getSeries();
        seriesCollection4.clear();

        ChartSeries series4 = seriesCollection4.add("Series1", new double[] { 0.0 }, new double[] { 0.0 });
        series4.clearValues();
        series4.add(ChartXValue.fromDateTimeInternal(new DateTime(2024, 1, 1)),
            ChartYValue.fromDateTimeInternal(new DateTime(2024, 1, 1)));
        series4.add(ChartXValue.fromDateTimeInternal(new DateTime(2024, 1, 2)),
            ChartYValue.fromDateTimeInternal(new DateTime(2024, 2, 1)));
        series4.add(ChartXValue.fromDateTimeInternal(new DateTime(2024, 1, 3)),
            ChartYValue.fromDateTimeInternal(new DateTime(2024, 3, 1)));
        series4.add(ChartXValue.fromDateTimeInternal(new DateTime(2024, 1, 4)),
            ChartYValue.fromDateTimeInternal(new DateTime(2024, 4, 1)));

        series4.hasDataLabels(true);
        series4.getDataLabels().setShowCategoryName(true);
        series4.getDataLabels().setShowValue(true);

        series4.getXValues().setFormatCode("yyyy-mm-dd");
        series4.getYValues().setFormatCode("yyyy-mm-dd");

        // Test Word 2016 charts.

        Shape shape5 = builder.insertChart(ChartType.FUNNEL, 432.0, 252.0);
        ChartSeriesCollection seriesCollection5 = shape5.getChart().getSeries();
        seriesCollection5.clear();

        ChartSeries series5 = seriesCollection5.add(
            "Series1",
            new String[] { "Category1", "Category2", "Category3" },
            new double[] { 5.0, 3.1, 1.95 });

        series5.hasDataLabels(true);
        series5.getDataLabels().setShowValue(true);

        series5.getYValues().setFormatCode("#,##0.00"); // This should be "# ##0,00" in ru locale.

        // Test time format.
        Shape shape6 = builder.insertChart(ChartType.FUNNEL, 432.0, 252.0);
        ChartSeriesCollection seriesCollection6 = shape6.getChart().getSeries();
        seriesCollection6.clear();

        ChartSeries series6 = seriesCollection6.add("Series1", new String[] { "" }, new double[] { 0.0 });
        series6.clearValues();
        series6.add(ChartXValue.fromString("Category1"), ChartYValue.fromTimeSpanInternal(new TimeSpan(8, 0, 0)));
        series6.add(ChartXValue.fromString("Category2"), ChartYValue.fromTimeSpanInternal(new TimeSpan(12, 0, 0)));
        series6.add(ChartXValue.fromString("Category3"), ChartYValue.fromTimeSpanInternal(new TimeSpan(16, 0, 0)));

        series6.hasDataLabels(true);
        series6.getDataLabels().setShowValue(true);

        series6.getYValues().setFormatCode("h:mm\\ am/pm"); // This should be "ч:мм\\ AM/PM" in ru locale.

        // Test date format.
        Shape shape7 = builder.insertChart(ChartType.FUNNEL, 432.0, 252.0);
        ChartSeriesCollection seriesCollection7 = shape7.getChart().getSeries();
        seriesCollection7.clear();

        ChartSeries series7 = seriesCollection7.add("Series1", new String[] { "" }, new double[] { 0.0 });
        series7.clearValues();
        series7.add(ChartXValue.fromString("Category1"), ChartYValue.fromDateTimeInternal(new DateTime(2024, 1, 1)));
        series7.add(ChartXValue.fromString("Category2"), ChartYValue.fromDateTimeInternal(new DateTime(2024, 2, 1)));
        series7.add(ChartXValue.fromString("Category3"), ChartYValue.fromDateTimeInternal(new DateTime(2024, 3, 1)));

        series7.hasDataLabels(true);
        series7.getDataLabels().setShowValue(true);

        series7.getYValues().setFormatCode("yyyy-mm-dd"); // This should be "ГГГГ\\-ММ\\-ДД" in ru locale.

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\DataApi\\Test27533.docx");
    }

    /// <summary>
    /// Creates a test X value of the specified type.
    /// </summary>
    private static ChartXValue createXValue(/*ChartXValueType*/int valueType)
    {
        switch (valueType)
        {
            case ChartXValueType.STRING:
                return ChartXValue.fromString("Category");
            case ChartXValueType.DOUBLE:
                return ChartXValue.fromDouble(1.0);
            case ChartXValueType.DATE_TIME:
                return ChartXValue.fromDateTimeInternal(DateTime.getToday());
            case ChartXValueType.TIME:
                return ChartXValue.fromTimeSpanInternal(TimeSpan.ZERO);
            case ChartXValueType.MULTILEVEL:
                return ChartXValue.fromMultilevelValue(new ChartMultilevelValue("Level1", "Level2", "Level3"));
            default:
                throw new IllegalStateException();
        }
    }

    /// <summary>
    /// Creates a test Y value of the specified type.
    /// </summary>
    private static ChartYValue createYValue(/*ChartYValueType*/int valueType)
    {
        switch (valueType)
        {
            case ChartYValueType.DOUBLE:
                return ChartYValue.fromDouble(1.0);
            case ChartYValueType.DATE_TIME:
                return ChartYValue.fromDateTimeInternal(DateTime.getToday());
            case ChartYValueType.TIME:
                return ChartYValue.fromTimeSpanInternal(TimeSpan.ZERO);
            default:
                throw new IllegalStateException();
        }
    }

    /// <summary>
    /// Creates a test chart where the first series is empty.
    /// </summary>
    private static ChartSeries createAreaChartWithEmptySeries() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.AREA, 432.0, 252.0);
        ChartSeries series = shape.getChart().getSeries().get(0);
        series.clear();

        return series;
    }

    /// <summary>
    /// Checks that labels are removed after removing data value with the specified index.
    /// </summary>
    /// <remarks>
    /// There may be zero, one or several labels that correspond to some chart data value.
    /// </remarks>
    /// <param name="series">The series whose label removal is being checked.</param>
    /// <param name="valueIndex">The index of the data value to remove.</param>
    /// <param name="firstRemovedLabelIndex">The index of the first label that is expected to be removed after the
    /// data value is removed. When a value is removed, there can only be a contiguous range of labels that match
    /// the value and should be removed.</param>
    /// <param name="removedLabelTexts">The array of text of the labels that are expected to be removed. Text should
    /// be <see cref="string.Empty"/> for non-materialized labels.</param>
    private static void checkDataLabelRemoval(ChartSeries series, int valueIndex, int firstRemovedLabelIndex,
        /*params*/ String... removedLabelTexts)
    {
        Debug.assertTrue((firstRemovedLabelIndex >= 0) || (removedLabelTexts.length == 0));

        ChartDataLabelCollection labels = series.getDataLabels();
        // Use data point count to exclude possible not used labels left after data removal and extra label of
        // an OfPieChart chart.
        int pointCount = series.getDataPoints().getCount();
        ChartDataLabel[] oldLabels = new ChartDataLabel[pointCount];
        for (int i = 0; i < pointCount; i++)
        {
            if (labels.hasNonDefaultItemFormatting(i))
                oldLabels[i] = labels.get(i);
        }

        boolean isLabelRemovalExpected = (firstRemovedLabelIndex >= 0);
        int removedLabelCount = removedLabelTexts.length;

        if (removedLabelTexts.length > 0)
        {
            // Check that the label text is the same as the value itself to ensure that previous removals worked
            // correctly.

            boolean isTreemapOrSunburst =
                (series.getLayoutType() == SeriesLayout.TREEMAP) || (series.getLayoutType() == SeriesLayout.SUNBURST);

            int valueLabelTextIndex = isTreemapOrSunburst ? removedLabelCount - 1 : 0;
            String valueLabelText = removedLabelTexts[valueLabelTextIndex];
            if ((!msString.equals(valueLabelText, "")) && (series.getLayoutType() != SeriesLayout.CLUSTERED_COLUMN))
            {
                String valueText = getLabelValueText(series, valueIndex);
                // A label may contain other information divided by the separator.
                msAssert.isTrue(valueLabelText.startsWith(valueText) || valueLabelText.endsWith(valueText));
            }
        }

        // Remove the value.
        series.remove(valueIndex);

        msAssert.areEqual(pointCount - removedLabelCount, series.getDataPoints().getCount());

        // When a value is removed, there can only be a contiguous range of labels that match the value and should be
        // removed: check that labels from firstRemovedLabelIndex to (firstRemovedLabelIndex + removedLabelCount - 1)
        // have been removed.
        int lastRemovedLabelIndex = isLabelRemovalExpected ? firstRemovedLabelIndex + removedLabelCount - 1 : -1;
        for (int i = 0; i < oldLabels.length; i++)
        {
            ChartDataLabel label = oldLabels[i];
            if (label == null)
            {
                if (isLabelRemovalExpected && (i >= firstRemovedLabelIndex) && (i <= lastRemovedLabelIndex))
                {
                    // Check that it is expected that the label is not materialized.
                    msAssert.areEqual("", removedLabelTexts[i - firstRemovedLabelIndex]);
                }

                continue;
            }

            if (!isLabelRemovalExpected || (i < firstRemovedLabelIndex))
            {
                msAssert.areSame(label, labels.get(i));
                msAssert.areEqual(i, label.getIndex());
            }
            else if (i <= lastRemovedLabelIndex)
            {
                msAssert.isFalse(labels.contains(label));
                String actualLabelText = label.getTxPr().getRunText();
                msAssert.areEqual(removedLabelTexts[i - firstRemovedLabelIndex], actualLabelText);
            }
            else
            {
                msAssert.areSame(label, labels.get(i - removedLabelCount));
                msAssert.areEqual(i - removedLabelCount, label.getIndex());
            }
        }
    }

    /// <summary>
    /// Checks that labels are moved in the data label collection after inserting data value with the specified index.
    /// </summary>
    /// <remarks>
    /// There may be zero, one or several labels that correspond to some chart data value.
    /// </remarks>
    /// <param name="series">The series whose label removal is being checked.</param>
    /// <param name="valueIndex">The index to insert the data value.</param>
    /// <param name="xValue">The inserting X value.</param>
    /// <param name="yValue">The inserting Y value.</param>
    /// <param name="expectedLabelInsertionIndex">The index of the first label that is expected to be inserted after
    /// the data value insertion.</param>
    /// <param name="expectedInsertedLabelCount">The number of the labels that is expected to be inserted after the
    /// data value insertion.</param>
    private static void checkDataLabelInsertion(ChartSeries series, int valueIndex, ChartXValue xValue,
        ChartYValue yValue, int expectedLabelInsertionIndex, int expectedInsertedLabelCount)
    {
        ChartDataLabelCollection labels = series.getDataLabels();
        // Use data point count to exclude possible not used labels left after data removal and extra label of
        // an OfPieChart chart.
        int pointCount = series.getDataPoints().getCount();
        ChartDataLabel[] oldLabels = new ChartDataLabel[pointCount];
        for (int i = 0; i < pointCount; i++)
        {
            if (labels.hasNonDefaultItemFormatting(i))
                oldLabels[i] = labels.get(i);
        }

        boolean isLabelInsertionExpected = (expectedLabelInsertionIndex >= 0);

        // Insert the data values.
        series.insert(valueIndex, xValue, yValue);

        msAssert.areEqual(pointCount + expectedInsertedLabelCount, series.getDataPoints().getCount());

        int lastInsertedLabelIndex = isLabelInsertionExpected
            ? expectedLabelInsertionIndex + expectedInsertedLabelCount - 1
            : -1;

        for (ChartDataLabel label : labels.getMaterializedDataLabels())
        {
            int index = label.getIndex();
            if (!isLabelInsertionExpected || (index < expectedLabelInsertionIndex))
            {
                msAssert.areSame(oldLabels[index], label);
                msAssert.areEqual(index, oldLabels[index].getIndex());
            }
            else if (index <= lastInsertedLabelIndex)
            {
                Assert.fail("No materialized labels expected in this label range.");
            }
            else
            {
                msAssert.areSame(oldLabels[index - expectedInsertedLabelCount], label);
                msAssert.areEqual(index, oldLabels[index - expectedInsertedLabelCount].getIndex());
            }
        }
    }

    /// <summary>
    /// Checks that labels are updated if necessary in the data label collection after changing data value with
    /// the specified index.
    /// </summary>
    /// <remarks>
    /// There may be zero, one or several labels that correspond to some chart data value.
    /// </remarks>
    /// <param name="series">The series whose label removal is being checked.</param>
    /// <param name="valueIndex">The index of the data value to change.</param>
    /// <param name="newXValue">The X value to set at the specified index.</param>
    /// <param name="expectedAffectedLabelIndex">The index of the start of the label range that is affected by the
    /// data value change.</param>
    /// <param name="expectedDeletedLabelCount">The number of the labels that is expected to be removed after the
    /// data value change.</param>
    /// <param name="expectedInsertedLabelCount">The number of the labels that is expected to be inserted after the
    /// data value change.</param>
    private static void checkDataLabelChange(ChartSeries series, int valueIndex, ChartXValue newXValue,
        int expectedAffectedLabelIndex, int expectedDeletedLabelCount, int expectedInsertedLabelCount)
    {
        ChartDataLabelCollection labels = series.getDataLabels();
        // Use data point count to exclude possible not used labels left after data removal and extra label of
        // an OfPieChart chart.
        int pointCount = series.getDataPoints().getCount();
        ChartDataLabel[] oldLabels = new ChartDataLabel[pointCount];
        for (int i = 0; i < pointCount; i++)
        {
            if (labels.hasNonDefaultItemFormatting(i))
                oldLabels[i] = labels.get(i);
        }

        int labelCountChange = expectedInsertedLabelCount - expectedDeletedLabelCount;

        // Change the data values.
        series.getXValues().set(valueIndex, newXValue);

        msAssert.areEqual(pointCount + labelCountChange, series.getDataPoints().getCount());

        int lastDeletedLabelIndex = (expectedDeletedLabelCount > 0)
            ? expectedAffectedLabelIndex + expectedDeletedLabelCount - 1
            : -1;

        for (int i = 0; i < oldLabels.length; i++)
        {
            ChartDataLabel label = oldLabels[i];
            if (label == null)
                continue;

            if (i < expectedAffectedLabelIndex)
            {
                msAssert.areSame(label, labels.get(i));
                msAssert.areEqual(i, label.getIndex());
            }
            else if (i <= lastDeletedLabelIndex)
            {
                msAssert.isFalse(labels.contains(label));
            }
            else
            {
                msAssert.areSame(label, labels.get(i + labelCountChange));
                msAssert.areEqual(i + labelCountChange, label.getIndex());
            }
        }
    }

    /// <summary>
    /// Gets text representation of the specified chart series value to display in a data label.
    /// </summary>
    private static String getLabelValueText(ChartSeries series, int valueIndex)
    {
        if ((series.getLayoutType() == SeriesLayout.TREEMAP) || (series.getLayoutType() == SeriesLayout.SUNBURST))
        {
            DmlChartMultiLvlStrValue value = (DmlChartMultiLvlStrValue)series.getX().getValues().get(valueIndex);
            for (int level = 0; level < value.getLevelsCount(); level++)
            {
                if (value.getLevels().get(level) != null)
                    return (String)value.getLevels().get(level);
            }

            return "";
        }
        else
        {
            double unit = ((IDmlChart2D)series.getChart()).getAxY().getDisplayUnit().getActualUnit();
            double value = series.getY().getValues().get(valueIndex).getValue() / unit;
            return FormatterPal.doubleToStr(value).replace(".", ",");
        }
    }

    /// <summary>
    /// Checks that no materialized data labels and data points exist in the series.
    /// </summary>
    private static void ensureNoDataPointsAndLabelsExist(ChartSeries series)
    {
        // Check that there are no labels left in the collection.
        msAssert.isFalse(series.getDataLabels().getMaterializedDataLabels().iterator().hasNext());
        // Check that there are no data points left in the collection.
        msAssert.isFalse(series.getDataPoints().getMaterializedDataPoints().iterator().hasNext());
    }

    private static final String NUMERIC_X_TEST_FILE_NAME_TEMPLATE = "Model\\Charts\\DataApi\\TestFillData_{0}_NumericX.docx";
}

