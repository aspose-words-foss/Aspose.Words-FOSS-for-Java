// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Class contains chart properties.
/// </summary>
class DmlChartPr implements Cloneable
{
    DmlChartPr()
    {
    	this(null);
	
    }

    DmlChartPr(DocumentBase doc)
    {
        // WORDSNET-20872 The defaults depend on the document version (7.2.2.2 AppVersion (Application Version)).
        // The version in CompatibilityOptions does not affect the defaults.
        DmlChartPr defaults = (DmlChartUtil.isMsWord2007OrLower(doc)) ? G_DEFAULT_PR : G_DEFAULT_PR_AFTER_2007;
        mPrBag.setParentBagProvider(new DmlChartPrParentBagProvider(defaults));
        // Collection of series must be present in properties directly.
        mPrBag.setProperty((int)DmlChartAttrs.SERIES, new ArrayList<ChartSeries>());
        // Also all complex types must be present in properties directly.
        mPrBag.setProperty((int)DmlChartAttrs.DROP_LINES, new DmlChartSpPr());
        mPrBag.setProperty((int)DmlChartAttrs.HI_LOW_LINES, new DmlChartSpPr());
        mPrBag.setProperty((int)DmlChartAttrs.SER_LINES, new DmlChartSpPr());
        mPrBag.setProperty((int)DmlChartAttrs.BAND_FMTS, new DmlChartBandFormats());
    }

    DmlChartPr deepClone()
    {
        DmlChartPr lhs = (DmlChartPr)memberwiseClone();
        lhs.mPrBag = mPrBag.deepClone();

        ArrayList<ChartSeries> cloningSeries = (ArrayList<ChartSeries>)getDirectProperty(DmlChartAttrs.SERIES);
        if (cloningSeries != null)
        {
            ArrayList<ChartSeries> clonedSeries = new ArrayList<ChartSeries>();
            for (ChartSeries series : cloningSeries)
            {
                ChartSeries cloned = series.deepClone();
                clonedSeries.add(cloned);
            }
            lhs.setProperty(DmlChartAttrs.SERIES, clonedSeries);

            // Assign owner
            for (int i = 0; i < cloningSeries.size(); i++)
            {
                ChartSeries series = cloningSeries.get(i);
                if (series.getOwner() != null)
                {
                    int ownerIndex = cloningSeries.indexOf(series.getOwner());
                    clonedSeries.get(i).setOwner(clonedSeries.get(ownerIndex));
                }
            }
        }

        ChartDataLabelCollection labels = (ChartDataLabelCollection)getDirectProperty(DmlChartAttrs.D_LBLS);
        if (labels != null)
            lhs.setProperty(DmlChartAttrs.D_LBLS, labels.deepClone());

        DmlChartSpPr dropLines = (DmlChartSpPr)getDirectProperty(DmlChartAttrs.DROP_LINES);
        if (dropLines != null)
            lhs.setProperty(DmlChartAttrs.DROP_LINES, dropLines.deepClone());

        DmlChartSpPr hiLowLines = (DmlChartSpPr)getDirectProperty(DmlChartAttrs.HI_LOW_LINES);
        if (hiLowLines != null)
            lhs.setProperty(DmlChartAttrs.HI_LOW_LINES, hiLowLines.deepClone());

        DmlChartSpPr serLines = (DmlChartSpPr)getDirectProperty(DmlChartAttrs.SER_LINES);
        if (serLines != null)
            lhs.setProperty(DmlChartAttrs.SER_LINES, serLines.deepClone());

        DmlChartUpDownBars upDownBars = (DmlChartUpDownBars)getDirectProperty(DmlChartAttrs.UP_DOWN_BARS);
        if (upDownBars != null)
            lhs.setProperty(DmlChartAttrs.UP_DOWN_BARS, upDownBars.deepClone());

        int[] split = (int[])getDirectProperty(DmlChartAttrs.CUST_SPLIT);
        if (split != null)
            lhs.setProperty(DmlChartAttrs.CUST_SPLIT, split.clone());

        DmlChartBandFormats bandFormats = (DmlChartBandFormats)getDirectProperty(DmlChartAttrs.BAND_FMTS);
        if (bandFormats != null)
            lhs.setProperty(DmlChartAttrs.BAND_FMTS, bandFormats.deepClone());

        StringToObjDictionary<DmlExtension> extensions =
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartAttrs.EXTENSIONS);
        if (extensions != null)
            lhs.setProperty(DmlChartAttrs.EXTENSIONS, DmlExtensionListSource.cloneExtensions(extensions));

        return lhs;
    }

    Object getProperty(/*DmlChartAttrs*/int attr)
    {
        return mPrBag.getProperty((int)attr);
    }

    void setProperty(/*DmlChartAttrs*/int attr, Object value)
    {
        mPrBag.setProperty((int)attr, value);
    }

    /// <summary>
    /// Returns null if property is not set explicitly
    /// </summary>
    Object getDirectProperty(/*DmlChartAttrs*/int attr)
    {
        return mPrBag.getDirectProperty((int) attr);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="attr">the attr</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlChartAttrs*/int attr)
    {
        return mPrBag.isPropertySpecified((int)attr);
    }

    private IDmlHierarchicalPropertyBag mPrBag = new DmlHierarchicalPropertyBag();

    private static DmlChartPr getDefaults()
    {
        DmlChartPr defaults = new DmlChartPr();
        defaults.setProperty(DmlChartAttrs.AX_ID_X, 0);
        defaults.setProperty(DmlChartAttrs.AX_ID_Y, 1);
        defaults.setProperty(DmlChartAttrs.AX_ID_Z, 2);
        defaults.setProperty(DmlChartAttrs.D_LBLS, null);
        defaults.setProperty(DmlChartAttrs.IS_DROP_LINES_VISIBLE, false);
        defaults.setProperty(DmlChartAttrs.IS_HI_LOW_LINES_VISIBLE, false);
        defaults.setProperty(DmlChartAttrs.GAP_DEPTH, 150);
        defaults.setProperty(DmlChartAttrs.GAP_WIDTH, 150);
        defaults.setProperty(DmlChartAttrs.GROUPING, Grouping.STANDARD);
        defaults.setProperty(DmlChartAttrs.VARY_COLORS, false);
        defaults.setProperty(DmlChartAttrs.BAR_DIR, BarDirection.BAR);
        defaults.setProperty(DmlChartAttrs.SHAPE, BarShape.BOX);
        defaults.setProperty(DmlChartAttrs.OVERLAP, 0);
        defaults.setProperty(DmlChartAttrs.BUBBLE_SCALE, 0);
        defaults.setProperty(DmlChartAttrs.SHOW_NEG_BUBBLES, false);
        defaults.setProperty(DmlChartAttrs.SHOW_SER_LINE, false);
        defaults.setProperty(DmlChartAttrs.SIZE_REPRESENTS, SizeRepresents.AREA);
        defaults.setProperty(DmlChartAttrs.FIRST_SLICE_ANG, 0);
        defaults.setProperty(DmlChartAttrs.HOLE_SIZE, 10);
        defaults.setProperty(DmlChartAttrs.SHOW_MARKER, false);
        defaults.setProperty(DmlChartAttrs.SMOOTH, false);
        defaults.setProperty(DmlChartAttrs.CUST_SPLIT, new int[0]);
        defaults.setProperty(DmlChartAttrs.OF_PIE_TYPE, OfPieType.PIE);
        defaults.setProperty(DmlChartAttrs.SECOND_PIE_SIZE, 75);
        defaults.setProperty(DmlChartAttrs.SPLIT_POS, Double.NaN);
        defaults.setProperty(DmlChartAttrs.SPLIT_TYPE, SplitType.AUTO);
        defaults.setProperty(DmlChartAttrs.RADAR_STYLE, RadarStyle.STANDARD);
        defaults.setProperty(DmlChartAttrs.SCATTER_STYLE, ScatterStyle.NONE);
        defaults.setProperty(DmlChartAttrs.WIREFRAME, false);
        defaults.setProperty(DmlChartAttrs.EXTENSIONS, null);

        return defaults;
    }

    private static DmlChartPr getDefaultsAfter2007()
    {
        DmlChartPr defaults = getDefaults();
        defaults.setProperty(DmlChartAttrs.VARY_COLORS, true);

        return defaults;
    }

    /// <summary>
    /// Gets or sets the grouping of the chart.
    /// </summary>
    /*Grouping*/int getGrouping() { return (/*Grouping*/Integer)getProperty(DmlChartAttrs.GROUPING); }
    void setGrouping(/*Grouping*/int value) { setProperty(DmlChartAttrs.GROUPING, value); }

    /// <summary>
    /// Gets or sets the type of a combo Pie chart: Pie of Pie or Bar of Pie.
    /// </summary>
    /*OfPieType*/int getOfPieType() { return (/*OfPieType*/Integer)getProperty(DmlChartAttrs.OF_PIE_TYPE); }
    void setOfPieType(/*OfPieType*/int value) { setProperty(DmlChartAttrs.OF_PIE_TYPE, value); }

    /// <summary>
    /// Gets or sets the bar direction of the chart.
    /// </summary>
    /*BarDirection*/int getBarDirection() { return (/*BarDirection*/Integer)getProperty(DmlChartAttrs.BAR_DIR); }
    void setBarDirection(/*BarDirection*/int value) { setProperty(DmlChartAttrs.BAR_DIR, value); }

    private static /*final*/ DmlChartPr G_DEFAULT_PR;
    private static /*final*/ DmlChartPr G_DEFAULT_PR_AFTER_2007;

    private static class DmlChartPrParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        DmlChartPrParentBagProvider(DmlChartPr parentPr)
        {
            mParentPr = parentPr;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return (mParentPr != null) ? mParentPr.mPrBag : null; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlChartPrParentBagProvider lhs = (DmlChartPrParentBagProvider)memberwiseClone();
            if (mParentPr != null)
                lhs.mParentPr = mParentPr.deepClone();
            return lhs;
        }

        private DmlChartPr mParentPr;

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

    static/* DmlChartPr()*/
    {
        G_DEFAULT_PR = getDefaults();
        G_DEFAULT_PR_AFTER_2007 = getDefaultsAfter2007();
    }
}

