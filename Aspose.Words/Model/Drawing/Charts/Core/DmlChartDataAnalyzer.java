// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/12/2019 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.IntToObjDictionary;
import com.aspose.StringList;
import com.aspose.ms.System.msString;
import com.aspose.MathUtil;
import java.util.Collections;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.StringToObjDictionary;
import com.aspose.DoubleList;
import com.aspose.DictionaryExtension;
import java.util.Comparator;


/// <summary>
/// Contains tools to obtain specific information from chart data stored in <see cref="DmlChartValueCollection"/>.
/// </summary>
/// <dev>
/// For performance reasons, collected information is cached in instances of this class and returned without
/// processing data on the next call if the data has not been changed. The fact that the data is changed is checked
/// using the <see cref="DmlChartValueCollection.ValueChangeCount"/> property.
/// </dev>
class DmlChartDataAnalyzer
{
    /// <summary>
    /// Calculates range of numeric values stored in the collection.
    /// </summary>
    ValueRange getValueRange(DmlChartValueCollection collection)
    {
        Debug.assertTrue((collection.getValueType() == DmlChartValueType.NUMERIC) ||
            (collection.getValueType() == DmlChartValueType.MULTI_LVL_NUMERIC));

        if (!collection.hasNonEmptyValues())
            return null;

        if ((mCachedValueRange != null) && mCachedValueRange.isActual(collection))
            return mCachedValueRange.getRange();

        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        for (DmlChartValue value : collection)
        {
            if (collection.getValueType() == DmlChartValueType.MULTI_LVL_NUMERIC)
            {
                DmlChartMultiLvlValue multiLevelValue = (DmlChartMultiLvlValue)value;

                for (Object levelValue : multiLevelValue.getLevels())
                {
                    if (levelValue == null)
                        continue;

                    double numericValue = (/*double*/Double)levelValue;

                    if (numericValue < min)
                        min = numericValue;

                    if (numericValue > max)
                        max = numericValue;
                }
            }
            else
            {
                double numericValue = value.getValue();

                if (numericValue < min)
                    min = numericValue;

                if (numericValue > max)
                    max = numericValue;
            }
        }

        ValueRange range = new ValueRange(min, max);

        mCachedValueRange = new CachedValueRange(range, collection);

        return range;
    }

    /// <summary>
    /// Calculates number of unique categories in the collection.
    /// </summary>
    int getUniqueCategoryCount(DmlChartValueCollection collection, boolean calcEmptyCategories)
    {
        Debug.assertTrue(collection.getValueType() == DmlChartValueType.MULTI_LVL_STRING,
            "The method is used for this point type only");

        if ((mCachedUniqueCategoryCount != null) &&
            mCachedUniqueCategoryCount.isActual(collection, calcEmptyCategories))
        {
            return mCachedUniqueCategoryCount.getCount();
        }

        // Sort values by index.
        ArrayList<DmlChartMultiLvlValue> values = getSortedMultiLevelValues(collection);

        IntToObjDictionary<StringList> uniqueValuesByLevels = new IntToObjDictionary<StringList>();
        int lastIndex = -1;
        boolean addEmptyCategories = false;

        for (DmlChartMultiLvlValue multiLevelValue : values)
        {
            // If there is a missing point, it is intended that it has an empty category.
            addEmptyCategories = addEmptyCategories ||
                (calcEmptyCategories && (multiLevelValue.getIndex() != lastIndex + 1));
            lastIndex = multiLevelValue.getIndex();

            for (int levelIndex = 0; levelIndex < multiLevelValue.getLevelsCount(); levelIndex++)
            {
                String level = nullToEmptyString((String)multiLevelValue.getLevels().get(levelIndex));

                if (msString.isNullOrEmpty(level) && !calcEmptyCategories)
                    continue;

                StringList list = uniqueValuesByLevels.get(levelIndex);
                if (list == null)
                {
                    list = new StringList();
                    uniqueValuesByLevels.add(levelIndex, list);
                }

                addIfNotExists(list, level.toUpperCase());
            }
        }

        if (addEmptyCategories)
        {
            for (StringList list : uniqueValuesByLevels.getValues())
                addIfNotExists(list, "");
        }

        int count = getTotalItemCount(uniqueValuesByLevels);
        mCachedUniqueCategoryCount = new CachedUniqueCategoryCount(count, collection, calcEmptyCategories);

        return count;
    }

    /// <summary>
    /// Gets aggregated category info of the Box and Whisker chart data value of the specified X multi-level
    /// string data collections.
    /// </summary>
    AggregatedCategory getBoxWhiskerCategoryInfo(DmlChartValueCollection xValues,
        DmlChartValueCollection yValues, int valueIndex)
    {
        // The method is used for the multi-level point type only.
        Debug.assertTrue(xValues.getValueType() == DmlChartValueType.MULTI_LVL_STRING);

        DmlChartMultiLvlStrValue xValue = (DmlChartMultiLvlStrValue)xValues.get(valueIndex);
        String targetCategoryName = (xValue != null)
            ? nullToEmptyString((String)xValue.getLevels().get(0))
            : "";

        ArrayList<DmlChartDataAnalyzer.Category> categories = new ArrayList<DmlChartDataAnalyzer.Category>();
        // A label for an empty Y value in a Box&Whisker chart has the largest index: use double.MaxValue here.
        DmlChartValue yValue = yValues.get(valueIndex);
        double valueY = (yValue != null) ? yValue.getValue() : Double.MAX_VALUE;

        AggregatedCategory targetCategory = new AggregatedCategory();
        targetCategory.setCategoryIndex(Integer.MAX_VALUE);
        targetCategory.isSingleValueCategory(true);
        targetCategory.setLastOrderedValueIndex(-1);

        // Calculate the category index and chart data value index assuming that the chart values are grouped
        // into categories by their X value with sorting chart data values within a category by their Y value.
        // The categories are arranged in the order they appear in the data.

        for (int i = 0; i <= Math.max(valueIndex, xValues.getLastNonEmptyValueIndex()); i++)
        {
            DmlChartMultiLvlStrValue value = (DmlChartMultiLvlStrValue)xValues.get(i);

            // If there is a missing point, it is intended that it has an empty category.
            String categoryName = (value != null) ? nullToEmptyString((String)value.getLevels().get(0)) : "";

            double y = (yValues.get(i) != null) ? yValues.get(i).getValue() : Double.MAX_VALUE;
            int categoryIndex = findOrCreateCategory(categories, categoryName, 0, i);

            if (!msString.equals(categoryName, targetCategoryName))
            {
                if (categoryIndex < targetCategory.getCategoryIndex())
                {
                    // The processing value has a point/label before than a point/label of the data value with
                    // index valueIndex.
                    targetCategory.setOrderedValueIndex(targetCategory.getOrderedValueIndex() + 1)/*Property++*/;
                    targetCategory.setLastOrderedValueIndex(targetCategory.getLastOrderedValueIndex() + 1)/*Property++*/;
                }

                continue;
            }

            // If the processing value has the same category, and its Y value is less than Y value of the data value
            // with the index valueIndex, its point/label is located before the value valueIndex.
            if ((y < valueY) ||
                ((i < valueIndex) && MathUtil.areEqual(y, valueY)))
            {
                targetCategory.setOrderedValueIndex(targetCategory.getOrderedValueIndex() + 1)/*Property++*/;
            }

            targetCategory.setLastOrderedValueIndex(targetCategory.getLastOrderedValueIndex() + 1)/*Property++*/;

            targetCategory.setCategoryIndex(categoryIndex);
            if (i != valueIndex)
                targetCategory.isSingleValueCategory(false);
        }

        targetCategory.setCategoryCount(categories.size());

        return targetCategory;
    }

    /// <summary>
    /// Gets aggregated category info of the Pareto chart data value of the specified X multi-level
    /// string data collections.
    /// </summary>
    AggregatedCategory getParetoCategoryInfo(DmlChartValueCollection xValues,
        DmlChartValueCollection yValues, int valueIndex)
    {
        // The method is used for the multi-level point type only.
        Debug.assertTrue(xValues.getValueType() == DmlChartValueType.MULTI_LVL_STRING);

        int xValueLevel = xValues.getLevelProperties().size() - 1;

        DmlChartMultiLvlStrValue xValue = (DmlChartMultiLvlStrValue)xValues.get(valueIndex);
        if (xValue == null)
            return null; // No point for this value.

        String targetCategoryName = nullToEmptyString((String)xValue.getLevels().get(xValueLevel));
        if (msString.equals(targetCategoryName, ""))
            return null; // No point for this value.

        ArrayList<DmlChartDataAnalyzer.Category> categories = new ArrayList<DmlChartDataAnalyzer.Category>();

        AggregatedCategory targetCategory = new AggregatedCategory();
        targetCategory.isSingleValueCategory(true);

        // Calculate the category index assuming that the chart values are grouped into categories by their X value
        // with sorting the categories by total Y value.

        for (int i = 0; i <= Math.max(valueIndex, xValues.getLastNonEmptyValueIndex()); i++)
        {
            DmlChartMultiLvlStrValue value = (DmlChartMultiLvlStrValue)xValues.get(i);
            if (value == null)
                continue;

            String categoryName = nullToEmptyString((String)value.getLevels().get(xValueLevel));
            if (msString.equals(categoryName, ""))
                continue;

            int categoryIndex = findOrCreateCategory(categories, categoryName, xValueLevel, i);
            if (yValues.get(i) != null)
                categories.get(categoryIndex).setTotalY(categories.get(categoryIndex).getTotalY() + yValues.get(i).getValue());

            if ((i != valueIndex) && (msString.equals(categoryName, targetCategoryName)))
                targetCategory.isSingleValueCategory(false);
        }

        Collections.sort(categories, new CategoryComparerByTotalYDesc());
        targetCategory.setCategoryIndex(findCategory(categories, targetCategoryName));
        targetCategory.setCategoryCount(categories.size());

        return targetCategory;
    }

    /// <summary>
    /// Get category infos of multi-level data of Sunburst or Treemap chart series.
    /// </summary>
    /// <remarks>
    /// Here, a category means a branch, stem, or leaf of a Sunburst or Treemap chart. A branch is the highest level
    /// of data, a stem is a middle level and a leaf is the lowest level.
    /// A level has its own category if the level or any of the higher levels is different than the corresponding
    /// level of the previous chart data value. And all lowest levels have own categories.
    /// In the difference with behaviour of the <see cref="GetUniqueCategoryCount"/> method, order of category values
    /// is important. The 'category1' items in the example below are treated as two different categories by this method:
    /// 'category1', 'category2', 'category1'.
    /// </remarks>
    ArrayList<DmlChartDataAnalyzer.Category> getCategories(DmlChartValueCollection collection)
    {
        Debug.assertTrue(collection.getValueType() == DmlChartValueType.MULTI_LVL_STRING,
            "The method is intended for this point type only");

        ArrayList<DmlChartDataAnalyzer.Category> categories = new ArrayList<DmlChartDataAnalyzer.Category>();
        if ((mCachedCategories != null) && mCachedCategories.isActual(collection))
        {
            msArrayList.addRange(categories, mCachedCategories.getCategories());
            return categories;
        }

        // Sort values by index.
        ArrayList<DmlChartMultiLvlValue> values = getSortedMultiLevelValues(collection);

        IntToObjDictionary<String> previousValueLevels = new IntToObjDictionary<String>();

        for (DmlChartMultiLvlValue multiLevelValue : values)
        {
            boolean isHigherLevelDifferent = false;
            // Need return categories from higher level to lower, levels of DmlChartMultiLvlValue are in reverse order.
            for (int i = multiLevelValue.getLevelsCount() - 1; i >= 0; i--)
            {
                String level = (String)multiLevelValue.getLevels().get(i);
                if (msString.isNullOrEmpty(level))
                    continue;

                // A level has its own category if the level or any of the higher levels is different than the
                // corresponding level of the previous chart data value. And all lowest levels have own categories.
                if (isHigherLevelDifferent || (!msString.equals(level, previousValueLevels.get(i))) || (i == 0))
                {
                    msArrayList.add(categories, new Category(level, categories.size(), i, multiLevelValue.getIndex()));
                    previousValueLevels.set(i, level);
                    isHigherLevelDifferent = true;
                }
            }
        }

        mCachedCategories = new CachedCategories(msArrayList.toArray(categories, new DmlChartDataAnalyzer.Category[0]), collection);

        return categories;
    }

    /// <summary>
    /// Gets plot infos of a Box and Whisker chart.
    /// </summary>
    ArrayList<BoxAndWhiskerPlot> getBoxAndWhiskerPlots(DmlChartValueCollection categories,
        DmlChartValueCollection values, /*QuartileMethod*/int quartileMethod)
    {
        Debug.assertTrue(
            (categories.getValueType() == DmlChartValueType.MULTI_LVL_STRING) &&
                (values.getValueType() == DmlChartValueType.MULTI_LVL_NUMERIC),
            "The method is intended for this point type only");

        ArrayList<BoxAndWhiskerPlot> list = new ArrayList<BoxAndWhiskerPlot>();
        if ((mCachedBoxAndWhiskerPlots != null) &&
            mCachedBoxAndWhiskerPlots.isActual(categories, values, quartileMethod))
        {
            msArrayList.addRange(list, mCachedBoxAndWhiskerPlots.getPlots());
            return list;
        }

        HashMap<Integer, String> categoryByIndex = new HashMap<Integer, String>();
        for (DmlChartValue value : categories)
        {
            DmlChartMultiLvlValue multiLevelValue = (DmlChartMultiLvlValue)value;
            Debug.assertTrue(multiLevelValue.getLevelsCount() == 1);
            msDictionary.add(categoryByIndex, multiLevelValue.getIndex(), multiLevelValue.getStringValue());
        }

        StringToObjDictionary<DoubleList> valuesByCategory = new StringToObjDictionary<DoubleList>();

        for (DmlChartValue value : values)
        {
            DmlChartMultiLvlValue multiLevelValue = (DmlChartMultiLvlValue)value;
            Debug.assertTrue(multiLevelValue.getLevelsCount() == 1);

            // Categories are case sensitive. It seems nulls are possible.
            String category = nullToEmptyString(DictionaryExtension.getValueOrNull(categoryByIndex, multiLevelValue.getIndex()));

            DoubleList categoryValues = valuesByCategory.get(category);
            if (categoryValues == null)
            {
                categoryValues = new DoubleList();
                valuesByCategory.add(category, categoryValues);
            }

            categoryValues.add(multiLevelValue.getValue());
        }

        StringToObjDictionary<DoubleList>.Enumerator enumerator = valuesByCategory.iterator();
        while (enumerator.moveNext())
            msArrayList.add(list, calculateBoxAndWhiskerPlot(enumerator.getCurrentKey(), enumerator.getCurrentValue(), quartileMethod));

        mCachedBoxAndWhiskerPlots = new CachedBoxAndWhiskerPlots(msArrayList.toArray(list, new BoxAndWhiskerPlot[0]), categories, values, quartileMethod);

        return list;
    }

    /// <summary>
    /// Searches for a category with the specified name in the list. If the category is not found, creates it and
    /// adds to the list. Returns index of the category.
    /// </summary>
    private static int findOrCreateCategory(ArrayList<DmlChartDataAnalyzer.Category> categories, String name, int levelIndex, int valueIndex)
    {
        int index = findCategory(categories, name);
        if (index >= 0)
            return index;

        Category category = new Category(name, categories.size(), levelIndex, valueIndex);
        msArrayList.add(categories, category);
        return category.getIndex();
    }

    private static int findCategory(ArrayList<DmlChartDataAnalyzer.Category> categories, String name)
    {
        for (int i = 0; i < categories.size(); i++)
        {
            if (msString.equals(categories.get(i).getName(), name))
                return i;
        }

        return -1;
    }

    /// <summary>
    /// Gets the total number of items in the string lists contained in the specified dictionary.
    /// </summary>
    private static int getTotalItemCount(IntToObjDictionary<StringList> uniqueValuesByLevels)
    {
        int count = 0;

        for (StringList list : uniqueValuesByLevels.getValues())
            count += list.getCount();

        return count;
    }

    /// <summary>
    /// Adds the value to the list if it is not already in it.
    /// </summary>
    private static void addIfNotExists(StringList sortedList, String value)
    {
        int index = sortedList.binarySearch(value);
        if (index < 0)
            sortedList.insert(~index, value);
    }

    private static String nullToEmptyString(String value)
    {
        return (value != null) ? value : "";
    }

    /// <summary>
    /// Calculates plot parameters of Box and Whisker category. Mimics MS Word behavior.
    /// </summary>
    private static BoxAndWhiskerPlot calculateBoxAndWhiskerPlot(String category, DoubleList values,
        /*QuartileMethod*/int quartileMethod)
    {
        Debug.assertTrue(values.getCount() > 0);

        if (values.getCount() == 1)
        {
            double value = values.get(0);
            return new BoxAndWhiskerPlot(category, value, value, value, value, value, value, 0, 0, values.toArray());
        }

        boolean includeMedian = quartileMethod == QuartileMethod.INCLUSIVE_MEDIAN;

        values.sort();
        int midpoint = values.getCount() / 2;
        double median = (values.getCount() % 2 == 0)
            ? (values.get(midpoint) + values.get(midpoint - 1)) / 2.0
            : values.get(midpoint);

        double sum = 0.0;
        for (int i = 0; i < values.getCount(); i++)
            sum += values.get(i);
        double mean = sum / values.getCount();

        if (values.getCount() <= 3)
        {
            int lastIndex = values.getCount() - 1;
            double lowerQuartile = includeMedian ? (values.get(0) + median) / 2.0 : values.get(0);
            double upperQuartile = includeMedian ? (values.get(lastIndex) + median) / 2.0 : values.get(lastIndex);

            return new BoxAndWhiskerPlot(category, values.get(0), lowerQuartile, median, upperQuartile,
                values.get(lastIndex), mean, 0, 0, values.toArray());
        }

        int quarter = (midpoint + (includeMedian ? 1 : 0)) / 2;
        int quarter3 = values.getCount() - 1 - quarter;
        double q1;
        double q3;

        switch ((values.getCount() + (includeMedian ? 2 : 0)) % 4)
        {
            case 0:
                q1 = (values.get(quarter) + 3.0 * values.get(quarter - 1)) / 4.0;
                q3 = (values.get(quarter3) + 3.0 * values.get(quarter3 + 1)) / 4.0;
                break;
            case 1:
                q1 = (values.get(quarter) + values.get(quarter - 1)) / 2.0;
                q3 = (values.get(quarter3) + values.get(quarter3 + 1)) / 2.0;
                break;
            case 2:
                q1 = (3.0 * values.get(quarter) + values.get(quarter - 1)) / 4.0;
                q3 = (3.0 * values.get(quarter3) + values.get(quarter3 + 1)) / 4.0;
                break;
            case 3:
            default:
                q1 = values.get(quarter);
                q3 = values.get(quarter3);
                break;
        }

        double lowerExtreme = q1 - 1.5 * (q3 - q1);
        int lowerOutlierCount = 0;
        while (values.get(lowerOutlierCount) < lowerExtreme)
            lowerOutlierCount++;
        lowerExtreme = values.get(lowerOutlierCount);

        double upperExtreme = q3 + 1.5 * (q3 - q1);
        int index = values.getCount() - 1;
        while (values.get(index) > upperExtreme)
            index--;
        upperExtreme = values.get(index);
        int upperOutlierCount = values.getCount() - index - 1;

        return new BoxAndWhiskerPlot(category, lowerExtreme, q1, median, q3, upperExtreme, mean,
            lowerOutlierCount, upperOutlierCount, values.toArray());
    }

    /// <summary>
    /// Returns a list of chart data values of the specified collection sorted by the index.
    /// </summary>
    private static ArrayList<DmlChartMultiLvlValue> getSortedMultiLevelValues(DmlChartValueCollection collection)
    {
        Debug.assertTrue((collection.getValueType() == DmlChartValueType.MULTI_LVL_STRING) ||
            (collection.getValueType() == DmlChartValueType.MULTI_LVL_NUMERIC));

        ArrayList<DmlChartMultiLvlValue> values = new ArrayList<DmlChartMultiLvlValue>(collection.getNonEmptyValueCount());

        for (DmlChartValue multiLevelValue : collection)
            msArrayList.add(values, (DmlChartMultiLvlValue)multiLevelValue);

        Collections.sort(values, new DmlChartMultiLvlValueComparer());

        return values;
    }

    private static class DmlChartMultiLvlValueComparer implements Comparator<DmlChartMultiLvlValue>
    {
        public int /*IComparer<DmlChartMultiLvlValue>.*/compare(DmlChartMultiLvlValue value1, DmlChartMultiLvlValue value2)
        {
            return value1.getIndex() - value2.getIndex();
        }
    }

    private CachedValueRange mCachedValueRange;
    private CachedUniqueCategoryCount mCachedUniqueCategoryCount;
    private CachedCategories mCachedCategories;
    private CachedBoxAndWhiskerPlots mCachedBoxAndWhiskerPlots;

    static class ValueRange
    {
        ValueRange(double start, double end)
        {
            setStart(start);
            setEnd(end);
        }

        double getStart() { return mStart; }; private void setStart(double value) { mStart = value; };

        private double mStart;
        double getEnd() { return mEnd; }; private void setEnd(double value) { mEnd = value; };

        private double mEnd;
    }

    static class Category
    {
        Category(String name, int index, int levelIndex, int firstRelatedValueIndex)
        {
            setName(name);
            setIndex(index);
            setLevelIndex(levelIndex);
            setFirstRelatedValueIndex(firstRelatedValueIndex);
        }

        String getName() { return mName; }; private void setName(String value) { mName = value; };

        private String mName;
        int getIndex() { return mIndex; }; private void setIndex(int value) { mIndex = value; };

        private int mIndex;
        int getLevelIndex() { return mLevelIndex; }; private void setLevelIndex(int value) { mLevelIndex = value; };

        private int mLevelIndex;
        int getFirstRelatedValueIndex() { return mFirstRelatedValueIndex; }; private void setFirstRelatedValueIndex(int value) { mFirstRelatedValueIndex = value; };

        private int mFirstRelatedValueIndex;
        double getTotalY() { return mTotalY; }; void setTotalY(double value) { mTotalY = value; };

        private double mTotalY;
    }

    private static class CategoryComparerByTotalYDesc implements Comparator<DmlChartDataAnalyzer.Category>
    {
        public int /*IComparer<Category>.*/compare(Category value1, Category value2)
        {
            if (value1.getTotalY() > value2.getTotalY())
                return -1;

            if (value1.getTotalY() < value2.getTotalY())
                return 1;

            return 0;
        }
    }

    /// <summary>
    /// Represents an info of a displayed X value related to a particular chart data value in charts, in which
    /// chart data values are grouped to categories by string X value. Used for Box and Whisker and Pareto charts.
    /// </summary>
    static class AggregatedCategory
    {
        /// <summary>
        /// The index of the category in the order in which the categories appear in the chart.
        /// </summary>
        int getCategoryIndex() { return mCategoryIndex; }; void setCategoryIndex(int value) { mCategoryIndex = value; };

        private int mCategoryIndex;

        /// <summary>
        /// Total number of categories in the series.
        /// </summary>
        int getCategoryCount() { return mCategoryCount; }; void setCategoryCount(int value) { mCategoryCount = value; };

        private int mCategoryCount;

        /// <summary>
        /// The index of the chart data value, for which this category info is retrieved, when the values are sorted
        /// by category in the category appearance order, and then by Y. This is not <see cref="DmlChartValue.Index"/>.
        /// </summary>
        int getOrderedValueIndex() { return mOrderedValueIndex; }; void setOrderedValueIndex(int value) { mOrderedValueIndex = value; };

        private int mOrderedValueIndex;

        /// <summary>
        /// The index of the last chart data value of the category when the values are sorted by category in the
        /// category appearance order, and then by Y.
        /// </summary>
        int getLastOrderedValueIndex() { return mLastOrderedValueIndex; }; void setLastOrderedValueIndex(int value) { mLastOrderedValueIndex = value; };

        private int mLastOrderedValueIndex;

        /// <summary>
        /// A flag indicating whether the category consists of only one chart data value.
        /// </summary>
        boolean isSingleValueCategory() { return mIsSingleValueCategory; }; void isSingleValueCategory(boolean value) { mIsSingleValueCategory = value; };

        private boolean mIsSingleValueCategory;
    }

    private static class CachedValueBase
    {
        CachedValueBase(DmlChartValueCollection collection)
        {
            mCollection = collection;
            mValueChangeCount = collection.getValueChangeCount();
        }

        /// <summary>
        /// Returns <c>true</c> if cached value is actual for the specified data collection.
        /// </summary>
        boolean isActual(DmlChartValueCollection collection)
        {
            return (mCollection == collection) && (mValueChangeCount == collection.getValueChangeCount());
        }

        private /*final*/ DmlChartValueCollection mCollection;
        private /*final*/ int mValueChangeCount;
    }

    private static class CachedValueRange extends CachedValueBase
    {
        CachedValueRange(ValueRange range, DmlChartValueCollection collection)
        {
        	super (collection);
	
            setRange(range);
        }

        ValueRange getRange() { return mRange; }; private void setRange(ValueRange value) { mRange = value; };

        private ValueRange mRange;
    }

    private static class CachedUniqueCategoryCount extends CachedValueBase
    {
        CachedUniqueCategoryCount(int count, DmlChartValueCollection collection,
            boolean isEmptyCategoriesIncluded)
        {
        	super(collection);
	
            setCount(count);
            mIsEmptyCategoriesIncluded = isEmptyCategoriesIncluded;
        }

        /// <summary>
        /// Returns <c>true</c> if cached value is actual for the specified data collections.
        /// </summary>
        boolean isActual(DmlChartValueCollection collection, boolean isEmptyCategoriesIncluded)
        {
            return super.isActual(collection) && mIsEmptyCategoriesIncluded == isEmptyCategoriesIncluded;
        }

        int getCount() { return mCount; }; private void setCount(int value) { mCount = value; };

        private int mCount;

        private /*final*/ boolean mIsEmptyCategoriesIncluded;
    }

    private static class CachedCategories extends CachedValueBase
    {
        CachedCategories(Category[] categories, DmlChartValueCollection collection)
        {
        	super(collection);
	
            setCategories(categories);
        }

        Category[] getCategories() { return mCategories; }; private void setCategories(Category[] value) { mCategories = value; };

        private Category[] mCategories;
    }

    private static class CachedBoxAndWhiskerPlots
    {
        CachedBoxAndWhiskerPlots(BoxAndWhiskerPlot[] plots, DmlChartValueCollection dataCategories,
            DmlChartValueCollection dataValues, /*QuartileMethod*/int quartileMethod)
        {
            setPlots(plots);
            mDataCategories = dataCategories;
            mCategoryChangeCount = dataCategories.getValueChangeCount();
            mDataValues = dataValues;
            mValueChangeCount = dataValues.getValueChangeCount();
            mQuartileMethod = quartileMethod;
        }

        /// <summary>
        /// Returns <c>true</c> if cached value is actual for the specified data collections.
        /// </summary>
        boolean isActual(DmlChartValueCollection dataCategories, DmlChartValueCollection dataValues,
            /*QuartileMethod*/int quartileMethod)
        {
            return
                (mDataCategories == dataCategories) && (mCategoryChangeCount == dataCategories.getValueChangeCount()) &&
                (mDataValues == dataValues) && (mValueChangeCount == dataValues.getValueChangeCount()) &&
                (mQuartileMethod == quartileMethod);
        }

        BoxAndWhiskerPlot[] getPlots() { return mPlots; }; private void setPlots(BoxAndWhiskerPlot[] value) { mPlots = value; };

        private BoxAndWhiskerPlot[] mPlots;

        private /*final*/ DmlChartValueCollection mDataCategories;
        private /*final*/ int mCategoryChangeCount;
        private /*final*/ DmlChartValueCollection mDataValues;
        private /*final*/ int mValueChangeCount;
        private /*final*/ /*QuartileMethod*/int mQuartileMethod;
    }
}

