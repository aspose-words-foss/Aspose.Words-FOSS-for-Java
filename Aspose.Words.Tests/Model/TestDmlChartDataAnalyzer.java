// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/12/2019 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests methods of the <see cref="DmlChartDataAnalyzer"/> class.
/// </summary>
@Test
public class TestDmlChartDataAnalyzer
{
    /// <summary>
    /// Tests the <see cref="DmlChartDataAnalyzer.GetValueRange"/> method.
    /// </summary>
    @Test
    public void testValueRange()
    {
        DmlChartValueCollection collection = new DmlChartValueCollection(DmlChartValueType.MULTI_LVL_NUMERIC);
        fillMultiLvlNumValues(collection, 10.0, -6, 1.0, 34.0, 22.0, -12, -28, -3);

        DmlChartDataAnalyzer analyzer = new DmlChartDataAnalyzer();
        DmlChartDataAnalyzer.ValueRange range = analyzer.getValueRange(collection);

        msAssert.areEqual(-28, range.getStart());
        msAssert.areEqual(34, range.getEnd());
    }

    /// <summary>
    /// Tests the <see cref="DmlChartDataAnalyzer.GetBoxAndWhiskerPlots"/> method.
    /// </summary>
    @Test (dataProvider = "testBoxAndWhiskerDataProvider")
    public void testBoxAndWhisker(double[] data, boolean isMedianInclusive, double expectedLowerExtreme, 
        double expectedLowerQuartile, double expectedMedian, double expectedUpperQuartile, double expectedUpperExtreme,
        double expectedMean, int expectedLowerOutlierCount, int expectedUpperOutlierCount)
    {
        DmlChartValueCollection categories = new DmlChartValueCollection(DmlChartValueType.MULTI_LVL_STRING);
        DmlChartValueCollection values = new DmlChartValueCollection(DmlChartValueType.MULTI_LVL_NUMERIC);

        for (int i = 0; i < data.length; i++)
        {
            DmlChartMultiLvlStrValue category = new DmlChartMultiLvlStrValue(i);
            category.addLevelValue("Category", 0);
            categories.add(category);

            DmlChartMultiLvlNumValue value = new DmlChartMultiLvlNumValue(i, null);
            value.addLevelValue(data[i], 0);
            values.add(value);
        }

        DmlChartDataAnalyzer analyzer = new DmlChartDataAnalyzer();
        /*QuartileMethod*/int method = isMedianInclusive ? QuartileMethod.INCLUSIVE_MEDIAN : QuartileMethod.EXCLUSIVE_MEDIAN;
        ArrayList<BoxAndWhiskerPlot> list = analyzer.getBoxAndWhiskerPlots(categories, values, method);
        BoxAndWhiskerPlot result = list.get(0);

        msAssert.areEqual(expectedLowerExtreme, result.getLowerExtreme());
        msAssert.areEqual(expectedLowerQuartile, result.getLowerQuartile());
        msAssert.areEqual(expectedMedian, result.getMedian());
        msAssert.areEqual(expectedUpperQuartile, result.getUpperQuartile());
        msAssert.areEqual(expectedUpperExtreme, result.getUpperExtreme());
        msAssert.areEqual(expectedMean, 0.0001, result.getMean());

        msAssert.areEqual(expectedLowerOutlierCount, result.getLowerOutlierCount());
        msAssert.areEqual(expectedUpperOutlierCount, result.getUpperOutlierCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBoxAndWhiskerDataProvider")
	public static Object[][] testBoxAndWhiskerDataProvider()
	{
		return new Object[][]
		{
			{new double[] { 34.0 },  false,  34,  34,  34,  34,  34,  34,  0,  0},
			{new double[] { 34.0, 10.0 },  false,  10,  10,  22,  34,  34,  22,  0,  0},
			{new double[] { 34.0, 10.0, -6 },  false,  -6,  -6,  10,  34,  34,  12.6667,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0 },  false,  -6,  -4.25,  5.5,  28,  34,  9.75,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0 },  false,  -6,  -2.5,  10,  28,  34,  12.2,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12 },  false,  -12,  -7.5,  5.5,  25,  34,  8.1667,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3 },  false,  -12,  -6,  1,  22,  34,  6.5714,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28 },  false,  -28,  -10.5,  -1,  19,  34,  2.25,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0 },  false,  -28,  -9,  1,  28,  48,  7.3333,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0, 6.0 },  false,  -28,  -7.5,  3.5,  25,  48,  7.2,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0, 6.0, 31.0 },  false,  -28,  -6,  6,  31,  48,  9.3636,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0, 6.0, 31.0, 3.0 },  false,  -28,  -5.25,  4.5,  28.75,  48, 
        8.8333,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0, 6.0, 31.0, 3.0, 12.0 },  false,  -28,  -4.5,  6,  26.5,  48, 
        9.0769,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0, 6.0, 31.0, 3.0, 12.0, -12 },  false,  -28,  -7.5,  4.5, 
        24.25,  48,  7.5714,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0, 6.0, 31.0, 3.0, 12.0, -12, -13 },  false,  -28,  -12,  3, 
        22,  48,  6.2,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0, 6.0, 31.0, 3.0, 12.0, -12, -13, 6.0 },  false,  -28,  -10.5,  4.5, 
        19.5,  48,  6.1875,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0, 6.0, 31.0, 3.0, 12.0, -12, -13, 6.0, 15.0 },  false,  
        -28,  -9,  6,  18.5,  48,  6.7059,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 128.0 },  false,  -28,  -9,  1,  28,  34,  16.2222,  0,  1},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, -120 },  false,  -28,  -20,  -3,  16,  34,  -11.3333,  1,  0},
			{new double[] { 34.0 },  true,  34,  34,  34,  34,  34,  34,  0,  0},
			{new double[] { 34.0, 10.0 },  true,  10,  16,  22,  28,  34,  22,  0,  0},
			{new double[] { 34.0, 10.0, -6 },  true,  -6,  2,  10,  22,  34,  12.6667,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0 },  true,  -6,  -0.75,  5.5,  16,  34,  9.75,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0 },  true,  -6,  1,  10,  22,  34,  12.2,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12 },  true,  -12,  -4.25,  5.5,  19,  34,  8.1667,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3 },  true,  -12,  -4.5,  1,  16,  34,  6.5714,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28 },  true,  -28,  -7.5,  -1,  13,  34,  2.25,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0 },  true,  -28,  -6,  1,  22,  48,  7.3333,  0,  0},
			{new double[] { 34.0, 10.0, -6, 1.0, 22.0, -12, -3, -28, 48.0, 6.0 },  true,  -28,  -5.25,  3.5,  19,  48,  7.2,  0,  0},
		};
	}

    /// <summary>
    /// Tests the <see cref="DmlChartDataAnalyzer.GetUniqueCategoryCount"/> method.
    /// </summary>
    @Test
    public void testUniqueCategoryCount()
    {
        DmlChartValueCollection collection = new DmlChartValueCollection(DmlChartValueType.MULTI_LVL_STRING);
        fillMultiLvlStrValues(collection, new String[] { "category1", "category2", "Category1", "CATEGORY1" }, null);

        DmlChartDataAnalyzer analyzer = new DmlChartDataAnalyzer();
        int count = analyzer.getUniqueCategoryCount(collection, false);

        msAssert.areEqual(2, count);
    }

    /// <summary>
    /// Tests the <see cref="DmlChartDataAnalyzer.GetCategories"/> method.
    /// </summary>
    @Test
    public void testCategories()
    {
        DmlChartValueCollection collection = new DmlChartValueCollection(DmlChartValueType.MULTI_LVL_STRING);
        fillMultiLvlStrValues(collection,
            new String[] { "branch1", "stem1", "category1", "category2", "Category1", "CATEGORY1", "stem2" },
            new int[] { 0, 1, 2, 2, 2, 2, 1 });

        DmlChartDataAnalyzer analyzer = new DmlChartDataAnalyzer();
        ArrayList<DmlChartDataAnalyzer.Category> list = analyzer.getCategories(collection);

        msAssert.areEqual(7, list.size());
        checkCategory(list.get(0), "branch1", 0);
        checkCategory(list.get(1), "stem1", 1);
        checkCategory(list.get(2), "category1", 2);
        checkCategory(list.get(3), "category2", 2);
        checkCategory(list.get(4), "Category1", 2);
        checkCategory(list.get(5), "CATEGORY1", 2);
        checkCategory(list.get(6), "stem2", 1);
    }

    /// <summary>
    /// Checks properties of the <see cref="DmlChartDataAnalyzer.Category"/> object.
    /// </summary>
    private void checkCategory(DmlChartDataAnalyzer.Category category, String expectedName, int expectedLevel)
    {
        msAssert.areEqual(expectedName, category.getName());
        msAssert.areEqual(expectedLevel, category.getLevelIndex());
    }

    /// <summary>
    /// Fills a data value collection of <see cref="DmlChartMultiLvlNumValue"/> type with test data.
    /// </summary>
    private static void fillMultiLvlNumValues(DmlChartValueCollection collection, /*params*/ double... data)
    {
        for (int i = 0; i < data.length; i++)
        {
            DmlChartMultiLvlNumValue value = new DmlChartMultiLvlNumValue(i, null);
            value.addLevelValue(data[i], 0);
            collection.add(value);
        }
    }

    /// <summary>
    /// Fills a data value collection of <see cref="DmlChartMultiLvlStrValue"/> type with test data.
    /// </summary>
    private static void fillMultiLvlStrValues(DmlChartValueCollection collection, String[] data, int[] levels)
    {
        for (int i = 0; i < data.length; i++)
        {
            DmlChartMultiLvlStrValue value = new DmlChartMultiLvlStrValue(i);
            value.addLevelValue(data[i], (levels != null) ? levels[i] : 0);
            collection.add(value);
        }
    }
}

