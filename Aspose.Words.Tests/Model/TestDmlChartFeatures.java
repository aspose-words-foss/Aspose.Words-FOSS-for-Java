// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/25/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.annotations.DataProvider;


/// <summary>
/// Simple open/save/compare tests for DOCX document with charts.
/// Used to make sure charts are written properly from the model.
/// Use test documents from charts features rendering tests.
/// </summary>
@Test
public class TestDmlChartFeatures
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }

    @Test
    public void testNegativeValues() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestNegativeValues.docx");
    }


    @Test
    public void testAxisPosition() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisPosition.docx");
    }

    @Test
    public void testAxisDirection() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisDirection.docx");
    }

    @Test
    public void testAxisDirection2() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisDirection2.docx");
    }

    @Test
    public void testLabelRotationA() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestLabelRotationA.docx");
    }

    @Test
    public void testLabelRotationB() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestLabelRotationB.docx");
    }


    @Test
    public void testOffsets() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestOffsets.docx");
    }



    @Test
    public void testStackedCharts() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestStackedCharts.docx");
    }

    @Test
    public void testStackedBarCharts() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestStackedBarCharts.docx");
    }


    @Test
    public void testLegendPosition() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestLegendPosition.docx");
    }


    /// <summary>
    /// WORDSNET-6935 Support Data Point formatting.
    /// </summary>
    @Test
    public void testDataPointFormattingBarChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDataPointFormattingBarChart.docx");
    }

    /// <summary>
    /// WORDSNET-6935 Support Data Point formatting.
    /// </summary>
    @Test
    public void testDataPointFormattingLineChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDataPointFormattingLineChart.docx");
    }




    /// <summary>
    /// WORDSNET-6935 Support Data Point formatting.
    /// </summary>
    @Test
    public void testDataPointFormattingStockChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDataPointFormattingStockChart.docx");
    }

    /// <summary>
    /// WORDSNET-6961 Support gap width upon rendering bar charts.
    /// </summary>
    @Test
    public void testBarChartGapWidth() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestBarChartGapWidth.docx");
    }

    /// <summary>
    /// WORDSNET-6961 Support gap width upon rendering bar charts.
    /// </summary>
    @Test
    public void testStockChartGapWidth() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestStockChartGapWidth.docx");
    }

    /// <summary>
    /// WORDSNET-6959 Support explosion upon rendering Pie charts.
    /// </summary>
    @Test
    public void testPieChartExplosion() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestPieChartExplosion.docx");
    }

    /// <summary>
    /// WORDSNET-6959 Support explosion upon rendering Pie charts.
    /// </summary>
    @Test
    public void testDoughnutChartExplosion() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDoughnutChartExplosion.docx");
    }

    /// <summary>
    /// WORDSNET-6939 Support custom axis formatting.
    /// </summary>
    @Test
    public void testAxisFormatting() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisFormatting.docx");
    }

    /// <summary>
    /// WORDSNET-6938 Support elements overlapping on chart area.
    /// </summary>
    @Test
    public void testOverlapping() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestOverlapping.docx");
    }

    /// <summary>
    /// WORDSNET-6937 Support manual layout of chart elements.
    /// </summary>
    @Test
    public void testManualLayout() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestManualLayout.docx");
    }

    /// <summary>
    /// WORDSNET-6940 Support custom legend formatting.
    /// </summary>
    @Test
    public void testLegendFormatting() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestLegendFormatting.docx");
    }

    /// <summary>
    /// WORDSNET-6936 Support custom font formatting.
    /// </summary>
    @Test
    public void testFontFormatting() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestFontFormatting.docx");
    }

    @Test
    public void testLegendEntry() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestLegendEntry.docx");
    }

    /// <summary>
    /// WORDSNET-6942 Render data labels
    /// </summary>
    @Test
    public void testDataLabels() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDataLabels.docx");
    }

    /// <summary>
    /// WORDSNET-6942 Render data labels
    /// </summary>
    @Test
    public void testDataLabelsBarChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDataLabelsBarChart.docx");
    }

    /// <summary>
    /// WORDSNET-6942 Render data labels
    /// </summary>
    @Test
    public void testDataLabelsPieChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDataLabelsPieChart.docx");
    }

    /// <summary>
    /// WORDSNET-6941 Render axis title.
    /// </summary>
    @Test
    public void testAxisTitle() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisTitle.docx");
    }

    /// <summary>
    /// WORDSNET-6776 Support fixed maximum and minimum values of axis.
    /// Tests scaling of value axis.
    /// </summary>
    @Test
    public void testAxisScalingA() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisScalingA.docx");
    }

    /// <summary>
    /// WORDSNET-6776 Support fixed maximum and minimum values of axis.
    /// Tests scaling of date axis.
    /// </summary>
    @Test
    public void testAxisScalingB() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisScalingB.docx");
    }

    /// <summary>
    /// WORDSNET-6776 Support fixed maximum and minimum values of axis.
    /// Tests scaling of radar chart value axis.
    /// </summary>
    @Test
    public void testAxisScalingC() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisScalingC.docx");
    }

    /// <summary>
    /// WORDSNET-6776 Support fixed maximum and minimum values of axis.
    /// Tests scaling of stacked charts.
    /// </summary>
    @Test
    public void testAxisScalingD() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisScalingD.docx");
    }

    /// <summary>
    /// Test auto axis scaling when minimum and maximum values of axis are close to each other.
    /// </summary>
    @Test
    public void testAxisScalingE() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisScalingE.docx");
    }

    /// <summary>
    /// WORDSNET-6946 Support rendering Pictures, Shapes and Textboxes on Chart Area
    /// </summary>
    @Test
    public void testChartShapes() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestChartShapes.docx");
    }

    /// <summary>
    /// WORDSNET-6945 Render Error Bars.
    /// </summary>
    @Test
    public void testErrorBarsLineChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestErrorBarsLineChart.docx");
    }

    /// <summary>
    /// WORDSNET-6945 Render Error Bars.
    /// </summary>
    @Test
    public void testErrorBarsBarChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestErrorBarsBarChart.docx");
    }

    /// <summary>
    /// WORDSNET-6945 Render Error Bars.
    /// </summary>
    @Test
    public void testErrorBarsScatterChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestErrorBarsScatterChart.docx");
    }

    /// <summary>
    /// WORDSNET-6945 Render Error Bars.
    /// </summary>
    @Test
    public void testErrorBarsAreaChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestErrorBarsAreaChart.docx");
    }

    /// <summary>
    /// WORDSNET-6945 Render Error Bars.
    /// </summary>
    @Test
    public void testErrorBarsBubbleChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestErrorBarsBubbleChart.docx");
    }

    /// <summary>
    /// WORDSNET-6945 Render Error Bars.
    /// </summary>
    @Test
    public void testErrorBarsStockChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestErrorBarsStockChart.docx");
    }

    /// <summary>
    /// WORDSNET-7262 Support rendering of minor grid lines.
    /// </summary>
    @Test
    public void testMinorGridLines() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestMinorGridLines.docx");
    }

    /// <summary>
    /// WORDSNET-7365 Support rendering of up-down bars.
    /// </summary>
    @Test
    public void testUpDownBars() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestUpDownBars.docx");
    }

    /// <summary>
    /// WORDSNET-7243 Support render legend markers in data labels.
    /// </summary>
    @Test
    public void testLegendMarkers() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestLegendMarkers.docx");
    }

    /// <summary>
    /// WORDSNET-7366 Support rendering high-low lines.
    /// </summary>
    @Test
    public void testHighLowLines() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestHighLowLines.docx");
    }

    /// <summary>
    /// WORDSNET-7366 Support rendering high-low lines.
    /// </summary>
    @Test
    public void testDropLines() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDropLines.docx");
    }

    /// <summary>
    /// WORDSNET-7504 Improve bubble size calculation algorithm.
    /// </summary>
    @Test
    public void testBubbleSize() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestBubbleSize.docx");
    }

    /// <summary>
    /// WORDSNET-7407 The Chart's axis values are different after conversion from Docx to PDF
    /// </summary>
    @Test
    public void testVerticalAxisLabels() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestVerticalAxisLabels.docx");
    }

    /// <summary>
    /// WORDSNET-7407 The Chart's axis values are different after conversion from Docx to PDF
    /// </summary>
    @Test
    public void testVerticalAxisLabelsNegative() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestVerticalAxisLabelsNegative.docx");
    }

    /// <summary>
    /// WORDSNET-7457 Improve axis labels auto rotation algorithm
    /// </summary>
    @Test
    public void testAxisLabelRotation() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisLabelRotation.docx");
    }

    /// <summary>
    /// WORDSNET-7457 Improve axis labels auto rotation algorithm
    /// </summary>
    @Test
    public void testAxisLabelRotationStandardAngles() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisLabelRotationStandardAngles.docx");
    }

    /// <summary>
    /// WORDSNET-7457 Improve axis labels auto rotation algorithm
    /// </summary>
    @Test
    public void testAxisLabelRotationCustomAngles() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisLabelRotationCustomAngles.docx");
    }

    /// <summary>
    /// WORDSNET-7244 Support rendering of leader lines upon rendering data labels.
    /// WORDSNET-13916 Support rendering of leader lines for all chart types.
    /// </summary>
    @Test (dataProvider = "testLeaderLinesDataProvider")
    public void testLeaderLines(String testFileName) throws Exception
    {
        TestUtil.openSaveOpen(testFileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testLeaderLinesDataProvider")
	public static Object[][] testLeaderLinesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Charts\\TestLeaderLines.docx"},
			{"Model\\Charts\\TestLeaderLinesAreaChart.docx"},
			{"Model\\Charts\\TestLeaderLinesBarChart.docx"},
			{"Model\\Charts\\TestLeaderLinesLineChart.docx"},
			{"Model\\Charts\\TestLeaderLinesRadarChart.docx"},
			{"Model\\Charts\\TestLeaderLinesScatterChart.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-6954 Improve work with brushes upon rendering charts.
    /// </summary>
    @Test
    public void testLinearGradient() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestLinearGradient.docx");
    }

    /// <summary>
    /// WORDSNET-8105 Support rendering of Linear trendlines.
    /// </summary>
    @Test
    public void testTrendlinesLinear() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestTrendlinesLinear.docx");
    }

    /// <summary>
    /// WORDSNET-8107 Support rendering of Moving Average trendlines.
    /// </summary>
    @Test
    public void testTrendlinesMovingAverage() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestTrendlinesMovingAverage.docx");
    }

    /// <summary>
    /// WORDSNET-8108 Support rendering of Polynomial trendlines.
    /// </summary>
    @Test
    public void testTrendlinesPolynomial() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestTrendlinesPolynomial.docx");
    }

    /// <summary>
    /// WORDSNET-8104 Support rendering of Exponential trendlines.
    /// </summary>
    @Test
    public void testTrendlinesExponential() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestTrendlinesExponential.docx");
    }

    /// <summary>
    /// WORDSNET-8106 Support rendering of Logarithmic trendlines.
    /// </summary>
    @Test
    public void testTrendlinesLogarithmic() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestTrendlinesLogarithmic.docx");
    }

    /// <summary>
    /// WORDSNET-8109 Support rendering of Power trendlines.
    /// </summary>
    @Test
    public void testTrendlinesPower() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestTrendlinesPower.docx");
    }

    /// <summary>
    /// WORDSNET-8191 Support Overlap option upon rendering bar charts.
    /// </summary>
    @Test
    public void testBarChartOverlap() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestBarChartOverlap.docx");
    }

    /// <summary>
    /// WORDSNET-6943 Render data table.
    /// </summary>
    @Test
    public void testDataTable() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDataTable.docx");
    }

    /// <summary>
    /// WORDSNET-8867 Support "Display Units" upon rendering axis labels.
    /// </summary>
    @Test
    public void testDisplayUnits() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDisplayUnits.docx");
    }

    /// <summary>
    /// WORDSNET-8123 Support trendlines intercept.
    /// </summary>
    @Test
    public void testTrendlineIntercept() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestTrendlineIntercept.docx");
    }

    /// <summary>
    /// WORDSNET-8110 Support trendlines forecast.
    /// </summary>
    @Test
    public void testTrendlinesForecast() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestTrendlinesForecast.docx");
    }

    /// <summary>
    /// WORDSNET-8974 Investigate rendering charts with random date value step.
    /// </summary>
    @Test
    public void testDateAxisRandomStep() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDateAxisRandomStep.docx");
    }

    /// <summary>
    /// WORDSNET-8999 Support tick mark type.
    /// </summary>
    @Test
    public void testTickMarkType() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestTickMarkType.docx");
    }

    /// <summary>
    /// WORDSNET-9178 Improve auto color mechanism for data points.
    /// </summary>
    @Test
    public void testDataPointAutoColor() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDataPointAutoColor.docx");
    }

    /// <summary>
    /// WORDSNET-7245 Support BestFit position of Pie chart's data labels.
    /// </summary>
    @Test
    public void testDataLabelBestFitPosition() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestDataLabelBestFitPosition.docx");
    }
}

