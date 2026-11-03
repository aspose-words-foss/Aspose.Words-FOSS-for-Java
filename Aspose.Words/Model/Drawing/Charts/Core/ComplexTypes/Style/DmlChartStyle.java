// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;
import com.aspose.FormatterPal;


/// <summary>
/// Represents 2.8.1.1 chartStyle, 2.8.3.1 CT_ChartStyle [MS-ODRAWXML]
/// A complex type that specifies visual and text properties for all elements present on a chart.
/// </summary>
class DmlChartStyle extends DmlExtensionListSource implements Cloneable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    DmlChartStyle()
    {
        mStyleEntries = new IntToObjDictionary<DmlChartStyleEntry>();
    }

    /// <summary>
    /// Clones this <see cref="DmlChartStyle"/> object.
    /// </summary>
    DmlChartStyle deepClone()
    {
        DmlChartStyle lhs = (DmlChartStyle)memberwiseClone();

        lhs.mStyleEntries = new IntToObjDictionary<DmlChartStyleEntry>();
        for (/*DmlChartStyleItem*/int index : getAllStyleItems())
        {
            if (mStyleEntries.get((int)index) != null)
                lhs.setByDmlChartStyleItem(index, this.getByDmlChartStyleItem(index).deepClone());
        }

        if (mDataPointMarkerLayout != null)
            lhs.mDataPointMarkerLayout = mDataPointMarkerLayout.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified item is a required element of chart style.
    /// </summary>
    private static boolean isRequiredStyleItem(/*DmlChartStyleItem*/int item)
    {
        return (item != DmlChartStyleItem.DATA_LABEL_CALLOUT);
    }

    /// <summary>
    /// Returns a style entry item for the specified index.
    /// </summary>
    DmlChartStyleEntry getByDmlChartStyleItem(/*DmlChartStyleItem*/int index)
    {
        DmlChartStyleEntry result = mStyleEntries.get((int)index);
        if ((result == null) && isRequiredStyleItem(index))
        {
            result = new DmlChartStyleEntry();
            mStyleEntries.set((int)index, result);
        }
        return result;
    }
    void setByDmlChartStyleItem(/*DmlChartStyleItem*/int index, DmlChartStyleEntry value) { mStyleEntries.set((int)index, value); }

    /// <summary>
    /// Represents dataPointMarkerLayout: a CT_MarkerLayout element that specifies additional marker properties
    /// not present in dataPointMarker. It is not required element of the CT_ChartStyle type.
    /// </summary>
    DmlChartMarkerLayout getDataPointMarkerLayout() { return mDataPointMarkerLayout; }
    void setDataPointMarkerLayout(DmlChartMarkerLayout value) { mDataPointMarkerLayout = value; }

    /// <summary>
    /// Represents id: an unsignedInt ([XMLSCHEMA2] section 3.3.22) attribute that specifies the identifier
    /// for this CT_ChartStyle.
    /// </summary>
    String getId() { return mId; }
    void setId(String value) { mId = value; }

    /// <summary>
    /// Gets the identifier of the style.
    /// </summary>
    int getIntId() { return FormatterPal.tryParseInt(mId); }

    /// <summary>
    /// Returns all elements of the <see cref="DmlChartStyleItem"/> enum to be used in the foreach operator.
    /// </summary>
    static /*DmlChartStyleItem*/int[] getAllStyleItems()
    {
        return (/*DmlChartStyleItem*/int[])DmlChartStyleItem.getValues();
    }

    private IntToObjDictionary<DmlChartStyleEntry> mStyleEntries;
    private DmlChartMarkerLayout mDataPointMarkerLayout;
    private String mId;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

