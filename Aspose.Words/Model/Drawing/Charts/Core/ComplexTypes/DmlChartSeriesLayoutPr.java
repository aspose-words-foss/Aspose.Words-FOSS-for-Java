// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringToObjDictionary;


/// <summary>
/// This class represents the 2.24.3.72 CT_SeriesLayoutProperties complex type [MS-ODRAWXML].
/// It specifies series layout properties.
/// </summary>
class DmlChartSeriesLayoutPr implements Cloneable
{
    DmlChartSeriesLayoutPr()
    {
        setParent(G_DEFAULTS);
    }

    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    DmlChartSeriesLayoutPr deepClone()
    {
        DmlChartSeriesLayoutPr lhs = (DmlChartSeriesLayoutPr)memberwiseClone();
        lhs.mPropertyBag = mPropertyBag.deepClone();

        DmlChartBinningPr binning = (DmlChartBinningPr)getDirectProperty(DmlChartSeriesLayoutAttr.BINNING);
        if (binning != null)
            setProperty(DmlChartSeriesLayoutAttr.BINNING, binning.deepClone());

        ArrayList<Integer> subtotals = (ArrayList<Integer>)getDirectProperty(DmlChartSeriesLayoutAttr.SUBTOTALS);
        if (subtotals != null)
        {
            ArrayList<Integer> clonedSubtotals = new ArrayList<Integer>();
            for (int index : subtotals)
                msArrayList.add(clonedSubtotals, index);

            setProperty(DmlChartSeriesLayoutAttr.SUBTOTALS, clonedSubtotals);
        }

        StringToObjDictionary<DmlExtension> extensions = 
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartSeriesLayoutAttr.EXTENSIONS);
        if (extensions != null)
            setProperty(DmlChartSeriesLayoutAttr.EXTENSIONS, DmlExtensionListSource.cloneExtensions(extensions));

        return lhs;
    }

    /// <summary>
    /// Set parent container of property values.
    /// </summary>
    void setParent(DmlChartSeriesLayoutPr parentBag)
    {
        mPropertyBag.setParentBagProvider(new DmlChartSeriesLayoutPrParentBagProvider(parentBag));
    }

    /// <summary>
    /// Sets property value.
    /// </summary>
    void setProperty(/*DmlChartSeriesLayoutAttr*/int attr, Object value)
    {
        mPropertyBag.setProperty((int)attr, value);
    }

    /// <summary>
    /// Gets property value. If it is not defined, default value is returned.
    /// </summary>
    Object getProperty(/*DmlChartSeriesLayoutAttr*/int attr)
    {
        return mPropertyBag.getProperty((int)attr);
    }

    /// <summary>
    /// Gets property value. If it is not defined, <c>null</c> is returned.
    /// </summary>
    Object getDirectProperty(/*DmlChartSeriesLayoutAttr*/int attr)
    {
        return mPropertyBag.getDirectProperty((int)attr);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="attr">the attr</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlChartSeriesLayoutAttr*/int attr)
    {
        return mPropertyBag.isPropertySpecified((int)attr);
    }

    /// <summary>
    /// Number of defined properties.
    /// </summary>
    int getCount() { return mPropertyBag.getCount(); }

    /// <summary>
    /// Specifies the visibility of a line connecting all mean points.
    /// </summary>
    boolean isMeanLineVisible() { return (/*boolean*/Boolean)getProperty(DmlChartSeriesLayoutAttr.IS_MEAN_LINE_VISIBLE); }
    void isMeanLineVisible(boolean value) { setProperty(DmlChartSeriesLayoutAttr.IS_MEAN_LINE_VISIBLE, value); }

    /// <summary>
    /// Specifies the visibility of markers denoting the mean.
    /// </summary>
    boolean isMeanMarkerVisible() { return (/*boolean*/Boolean)getProperty(DmlChartSeriesLayoutAttr.IS_MEAN_MARKER_VISIBLE); }
    void isMeanMarkerVisible(boolean value) { setProperty(DmlChartSeriesLayoutAttr.IS_MEAN_MARKER_VISIBLE, value); }

    /// <summary>
    /// Specifies the visibility of non-outlier data points.
    /// </summary>
    boolean isNonOutliersVisible() { return (/*boolean*/Boolean)getProperty(DmlChartSeriesLayoutAttr.IS_NON_OUTLIERS_VISIBLE); }
    void isNonOutliersVisible(boolean value) { setProperty(DmlChartSeriesLayoutAttr.IS_NON_OUTLIERS_VISIBLE, value); }

    /// <summary>
    /// Specifies the visibility of outlier data points.
    /// </summary>
    boolean isOutliersVisible() { return (/*boolean*/Boolean)getProperty(DmlChartSeriesLayoutAttr.IS_OUTLIERS_VISIBLE); }
    void isOutliersVisible(boolean value) { setProperty(DmlChartSeriesLayoutAttr.IS_OUTLIERS_VISIBLE, value); }

    /// <summary>
    /// Specifies the quartile calculation method.
    /// </summary>
    /*QuartileMethod*/int getQuartileMethod() { return (/*QuartileMethod*/Integer)getProperty(DmlChartSeriesLayoutAttr.QUARTILE_METHOD); }
    void setQuartileMethod(/*QuartileMethod*/int value) { setProperty(DmlChartSeriesLayoutAttr.QUARTILE_METHOD, value); }

    /// <summary>
    /// Specifies whether data aggregation is performed.
    /// </summary>
    boolean isAggregation() { return (/*boolean*/Boolean)getProperty(DmlChartSeriesLayoutAttr.IS_AGGREGATION); }
    void isAggregation(boolean value) { setProperty(DmlChartSeriesLayoutAttr.IS_AGGREGATION, value); }

    /// <summary>
    /// Specifies the data binning properties for the series.
    /// </summary>
    DmlChartBinningPr getBinning() { return (DmlChartBinningPr)getProperty(DmlChartSeriesLayoutAttr.BINNING); }
    void setBinning(DmlChartBinningPr value) { setProperty(DmlChartSeriesLayoutAttr.BINNING, value); }

    /// <summary>
    /// Specifies a list of indexes of subtotal data points.
    /// </summary>
    ArrayList<Integer> getSubTotals() { return (ArrayList<Integer>)getDirectProperty(DmlChartSeriesLayoutAttr.SUBTOTALS); }
    void setSubTotals(ArrayList<Integer> value) { setProperty(DmlChartSeriesLayoutAttr.SUBTOTALS, value); }

    /// <summary>
    /// Specifies  the visibility of connector lines between data points.
    /// </summary>
    boolean isConnectorLinesVisible() { return (/*boolean*/Boolean)getProperty(DmlChartSeriesLayoutAttr.IS_CONNECTOR_LINES_VISIBLE); }
    void isConnectorLinesVisible(boolean value) { setProperty(DmlChartSeriesLayoutAttr.IS_CONNECTOR_LINES_VISIBLE, value); }

    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();

    private static /*final*/ DmlChartSeriesLayoutPr G_DEFAULTS;

    private static class DmlChartSeriesLayoutPrParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        DmlChartSeriesLayoutPrParentBagProvider(DmlChartSeriesLayoutPr parentPr)
        {
            mParentPr = parentPr;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return (mParentPr != null) ? mParentPr.mPropertyBag : null; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlChartSeriesLayoutPrParentBagProvider clone = 
                (DmlChartSeriesLayoutPrParentBagProvider)memberwiseClone();
            if (mParentPr != null)
                clone.mParentPr = mParentPr.deepClone();
            return clone;
        }

        private DmlChartSeriesLayoutPr mParentPr;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

    /// <summary>
    /// Static ctor that inits default property values.
    /// </summary>
    static/* DmlChartSeriesLayoutPr()*/
    {
        G_DEFAULTS = new DmlChartSeriesLayoutPr();
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.PARENT_LABEL_LAYOUT, ParentLabelLayout.NONE);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.REGION_LABEL_LAYOUT, RegionLabelLayout.NONE);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.IS_CONNECTOR_LINES_VISIBLE, true);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.IS_MEAN_LINE_VISIBLE, false);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.IS_MEAN_MARKER_VISIBLE, true);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.IS_NON_OUTLIERS_VISIBLE, true);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.IS_OUTLIERS_VISIBLE, true);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.IS_AGGREGATION, false);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.BINNING, null);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.GEOGRAPHY, null);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.QUARTILE_METHOD, QuartileMethod.EXCLUSIVE_MEDIAN);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.SUBTOTALS, null);
        G_DEFAULTS.setProperty(DmlChartSeriesLayoutAttr.EXTENSIONS, null);
    }
}

