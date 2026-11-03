// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.IntToObjDictionary;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import java.util.Iterator;
import com.aspose.ms.System.msString;


class DmlChartValueCollection extends DmlExtensionListSource implements Iterable<DmlChartValue>, Cloneable
{
    DmlChartValueCollection(/*DmlChartValueType*/int valueType)
    {
        mValueType = valueType;
    }

    /// <summary>
    /// Adds the specified chart data value to the collection.
    /// </summary>
    void add(DmlChartValue value)
    {
        if (value == null)
            return;
        
        mValues.set(value.getIndex(), value);

        if (value.getIndex() > getLastNonEmptyValueIndex())
            setLastNonEmptyValueIndex(value.getIndex());

        markAsChanged();
        value.setCollection(this);
    }

    /// <summary>
    /// Adds the specified chart data value to the collection.
    /// </summary>
    /// <remarks>
    /// If the value is <b>null</b>, the existing value with the specified index is removed from the collection.
    /// If the value is a multi-level value, missing levels are added to the value or to all existing values in
    /// the collection.
    /// </remarks>
    void addNullAware(DmlChartValue value, int index)
    {
        if (value != null)
        {
            addMissingLevelsToCollectionOrValue(value);
            add(value);
        }
        else
        {
            mValues.remove(index);

            if (mLastNonEmptyValueIndex == index)
                calculateLastNonEmptyValueIndex();

            markAsChanged();
        }
    }

    /// <summary>
    /// Inserts the specified chart data value to the collection at the specified index.
    /// </summary>
    /// <remarks>
    /// The indexes of existing values that have an index equal to or greater than the specified one are
    /// incremented by 1.
    /// If a multi-level value is inserted, missing levels are added to the value or to all existing values in
    /// the collection.
    /// The value can be <b>null</b>.
    /// </remarks>
    void insert(int index, DmlChartValue value)
    {
        IntToObjDictionary<DmlChartValue> values = new IntToObjDictionary<DmlChartValue>(mValues.getCount() + 1);

        for (DmlChartValue currentValue : mValues.getValues())
        {
            if (currentValue.getIndex() >= index)
                currentValue.setIndex(currentValue.getIndex() + 1)/*Property++*/;

            values.set(currentValue.getIndex(), currentValue);
        }

        mValues = values;

        if (value != null)
        {
            addMissingLevelsToCollectionOrValue(value);
            values.add(index, value);
            value.setCollection(this);
            mLastNonEmptyValueIndex = Math.max(mLastNonEmptyValueIndex + 1, index);
        }
        else
        {
            if (index < mLastNonEmptyValueIndex)
                mLastNonEmptyValueIndex++;
        }

        setValueCount(getValueCount() + 1)/*Property++*/;

        markAsChanged();
    }

    /// <summary>
    /// Removes the specified chart data value from the collection at the specified index.
    /// </summary>
    /// <remarks>
    /// The indexes of existing values after the specified one are decremented by 1.
    /// </remarks>
    void remove(int index)
    {
        if (getValueCount() > index)
            setValueCount(getValueCount() - 1)/*Property--*/;

        if (index > mLastNonEmptyValueIndex)
            return;

        int lastIndex = -1;
        IntToObjDictionary<DmlChartValue> values = new IntToObjDictionary<DmlChartValue>(mValues.getCount());

        for (DmlChartValue value : mValues.getValues())
        {
            if (value.getIndex() == index)
                continue;

            if (value.getIndex() > index)
                value.setIndex(value.getIndex() - 1)/*Property--*/;

            values.set(value.getIndex(), value);

            if (lastIndex < value.getIndex())
                lastIndex = value.getIndex();
        }

        mValues = values;
        mLastNonEmptyValueIndex = lastIndex;

        markAsChanged();
    }

    /// <summary>
    /// Removes the all values from the collection.
    /// </summary>
    void clear()
    {
        // Remove the all level properties except the last one.
        while ((mLevelProperties != null) && (mLevelProperties.size() > 1))
            mLevelProperties.remove(0);

        mLastNonEmptyValueIndex = -1;
        setValueCount(0);
        mValues.clear();

        markAsChanged();
    }

    DmlChartValueCollection deepClone()
    {
        DmlChartValueCollection lhs = (DmlChartValueCollection)memberwiseClone();
        lhs.mValues = new IntToObjDictionary<DmlChartValue>();

        if (mLevelProperties != null)
        {
            lhs.mLevelProperties = new ArrayList<DmlChartDataLevelProperties>(mLevelProperties.size());
            for (DmlChartDataLevelProperties properties : mLevelProperties)
                msArrayList.add(lhs.mLevelProperties, properties.deepClone());
        }

        for (DmlChartValue value : mValues.getValues())
        {
            DmlChartValue clonedValue = value.deepClone();
            clonedValue.setCollection(lhs);

            DmlChartMultiLvlNumValue numValue = ms.as(clonedValue, DmlChartMultiLvlNumValue.class);
            if (numValue != null)
                numValue.setLevelProperties(lhs.mLevelProperties);

            lhs.add(clonedValue);
        }

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Adds the specified properties to the level properties list.
    /// </summary>
    void addLevelProperties(DmlChartDataLevelProperties levelProperties)
    {
        if (mLevelProperties == null)
            mLevelProperties = new ArrayList<DmlChartDataLevelProperties>();

        msArrayList.add(mLevelProperties, levelProperties);
    }

    /// <summary>
    /// Sets the <see cref="DmlChartDataLevelProperties.ValueCount"/> level property to the specified value for all
    /// levels of the collection.
    /// </summary>
    void setValueCountForAllLevels(int valueCount, int levelCount)
    {
        for (int i = 0; i < levelCount; i++)
        {
            if (i >= mLevelProperties.size())
                msArrayList.add(mLevelProperties, new DmlChartDataLevelProperties());

            mLevelProperties.get(i).setValueCount(valueCount);
        }
    }

    /// <summary>
    /// Fills this <see cref="DmlChartValueCollection"/> with "dummy" values. The collection is expected to be
    /// empty with defined <see cref="ValueCount"/> property.
    /// </summary>
    void fillWithDummyValues()
    {
        Debug.assertTrue(mValues.getCount() == 0);

        for (int i = 0; i < getValueCount(); i++)
            add(new DmlChartDummyValue(i, (i + 1)));
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<DmlChartValue> iterator()
    {
        return mValues.getValues().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Marks the collection as modified by incrementing <see cref="ValueChangeCount"/>.
    /// </summary>
    void markAsChanged()
    {
        mValueChangeCount++;
    }

    /// <summary>
    /// Returns the first non-empty value in this collection.
    /// </summary>
    DmlChartValue getFirstNotNullValue()
    {
        for (int i = 0; i <= getLastNonEmptyValueIndex(); i++)
        {
            if (!DmlChartValue.isNullOrNaN(this.get(i)))
                return this.get(i);
        }

        return null;
    }

    /// <summary>
    /// If the collection contains multilevel values, and the number of levels in the existing values differs from
    /// the number of levels in the specified value, the missing levels are added to either the existing values or
    /// the specified value.
    /// </summary>
    private void addMissingLevelsToCollectionOrValue(DmlChartValue value)
    {
        DmlChartMultiLvlValue multiLevelValue = ms.as(value, DmlChartMultiLvlValue.class);
        if (multiLevelValue == null)
            return;

        while (mLevelProperties.size() > multiLevelValue.getLevelsCount())
            multiLevelValue.getLevels().add(0, null);

        // Make number of levels the same in the inserting and existing values.
        int levelCount = -1;
        for (DmlChartValue currentValue : mValues.getValues())
        {
            DmlChartMultiLvlValue currentMultilevelValue = ms.as(currentValue, DmlChartMultiLvlValue.class);
            if (currentMultilevelValue == null)
                continue;

            if (levelCount < 0)
                levelCount = currentMultilevelValue.getLevelsCount();
            else
                Debug.assertTrue(currentMultilevelValue.getLevelsCount() == levelCount);

            while (currentMultilevelValue.getLevelsCount() < multiLevelValue.getLevelsCount())
                currentMultilevelValue.getLevels().add(0, null);
        }

        while (multiLevelValue.getLevelsCount() < levelCount)
            multiLevelValue.getLevels().add(0, null);

        // Create LastLevelProperties if it does not exist yet.
        DmlChartDataLevelProperties lastLevelProperties = getLastLevelProperties();
        while (mLevelProperties.size() < multiLevelValue.getLevelsCount())
            msArrayList.add(mLevelProperties, lastLevelProperties.deepClone());
    }

    /// <summary>
    /// Calculates and sets the value of the <see cref="LastNonEmptyValueIndex"/> property.
    /// </summary>
    private void calculateLastNonEmptyValueIndex()
    {
        int lastIndex = -1;
        for (DmlChartValue currentValue : mValues.getValues())
        {
            if (lastIndex < currentValue.getIndex())
                lastIndex = currentValue.getIndex();
        }

        setLastNonEmptyValueIndex(lastIndex);
    }

    /// <summary>
    /// Returns a data value by an index.
    /// </summary>
    DmlChartValue get(int index) { return mValues.get(index); }

    /// <summary>
    /// Returns number of data values of a chart dimension including empty data values.
    /// </summary>
    int getValueCount()
    {
        // It seems the last level always contains real data. The other levels may have some intermediate or
        // calculated data, for example, the 'size' dimension of Model\Charts\Word2016Charts\BoxWhisker.docx
        return getLastLevelProperties().getValueCount();
    }
    void setValueCount(int value)
    {
        if (getValueCount() == value)
            return;
        
        markAsChanged();

        // It seems the last level always contains real data. The other levels may have some intermediate or
        // calculated data, for example, the 'size' dimension of Model\Charts\Word2016Charts\BoxWhisker.docx
        // Let's keep those unknown levels for now, just update their value count.
        for (DmlChartDataLevelProperties levelProperties : mLevelProperties)
        {
            if (levelProperties.getValueCount() >= getLastLevelProperties().getValueCount())
                levelProperties.setValueCount(value);
        }
    }

    /// <summary>
    /// Returns number of non-empty values of a chart dimension.
    /// </summary>
    int getNonEmptyValueCount() { return mValues.getCount(); }

    /// <summary>
    /// Indicates whether the collection contains non-empty values.
    /// </summary>
    boolean hasNonEmptyValues() { return getNonEmptyValueCount() > 0; }

    /// <summary>
    /// Gets the index of the last non-empty value in this collection.
    /// </summary>
    int getLastNonEmptyValueIndex() { return mLastNonEmptyValueIndex; }
    private void setLastNonEmptyValueIndex(int value)
    {
        mLastNonEmptyValueIndex = value;

        if (getValueCount() <= mLastNonEmptyValueIndex)
            setValueCount(mLastNonEmptyValueIndex + 1);
    }

    /// <summary>
    /// Gets or sets a format code used to convert data values to their string representation.
    /// </summary>
    String getFormatCode() { return getLastLevelProperties().getFormatCode(); }
    void setFormatCode(String value)
    {
        if (msString.equals(getFormatCode(), value))
            return;
        
        markAsChanged();

        // All levels have the same data type, let's use the same format.
        for (DmlChartDataLevelProperties levelProperties : mLevelProperties)
            levelProperties.setFormatCode(value);
    }

    /// <summary>
    /// Gets value type of items of this collection.
    /// </summary>
    /*DmlChartValueType*/int getValueType() { return mValueType; }
    void setValueType(/*DmlChartValueType*/int value)
    {
        if (mValueType == value)
            return;
        
        mValueType = value;
        markAsChanged();
    }

    /// <summary>
    /// Allows to check whether values are changed since the last access.
    /// </summary>
    int getValueChangeCount() { return mValueChangeCount; }

    /// <summary>
    /// Indicates whether the collection stores date or time values.
    /// </summary>
    boolean isDate() { return DmlChartFormatCodeValidator.isDateFormat(getFormatCode()); }

    /// <summary>
    /// Gets list of dimension level properties.
    /// </summary>
    ArrayList<DmlChartDataLevelProperties> getLevelProperties() { return mLevelProperties; }

    /// <summary>
    /// Gets the properties of the last level.
    /// </summary>
    private DmlChartDataLevelProperties getLastLevelProperties()
    {
        if (mLevelProperties.size() == 0)
            msArrayList.add(mLevelProperties, new DmlChartDataLevelProperties());

        return mLevelProperties.get(mLevelProperties.size() - 1);
    }

    private int mLastNonEmptyValueIndex = -1;
    private IntToObjDictionary<DmlChartValue> mValues = new IntToObjDictionary<DmlChartValue>();
    private /*DmlChartValueType*/int mValueType;
    private ArrayList<DmlChartDataLevelProperties> mLevelProperties =
        new ArrayList<DmlChartDataLevelProperties>(MAX_CHART_EX_LEVEL_COUNT);
    private int mValueChangeCount;

    private static final int MAX_CHART_EX_LEVEL_COUNT = 3;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

