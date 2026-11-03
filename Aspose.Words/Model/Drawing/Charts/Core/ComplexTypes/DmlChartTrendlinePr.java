// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents set of <see cref="DmlChartTrendline"/> properties.
/// </summary>
class DmlChartTrendlinePr implements Cloneable
{
    DmlChartTrendlinePr()
    {
    	this(G_DEFAULTS);
	
    }

    private DmlChartTrendlinePr(DmlChartTrendlinePr parentBag)
    {
        mPropertyBag.setParentBagProvider(new DmlChartTrendlinePrParentBagProvider((parentBag != null) ? parentBag : G_DEFAULTS));
    }

    DmlChartTrendlinePr deepClone()
    {
        DmlChartTrendlinePr lhs = (DmlChartTrendlinePr)memberwiseClone();
        lhs.mPropertyBag = mPropertyBag.deepClone();

        DmlChartSpPr spPr = (DmlChartSpPr)getDirectProperty(DmlChartTrendlineAttr.SP_PR);
        if (spPr != null)
            lhs.setProperty(DmlChartTrendlineAttr.SP_PR, spPr.deepClone());

        DmlChartTrendlineLabel label = (DmlChartTrendlineLabel)getDirectProperty(DmlChartTrendlineAttr.TRENDLINE_LBL);
        if (label != null)
            lhs.setProperty(DmlChartTrendlineAttr.TRENDLINE_LBL, label.deepClone());

        StringToObjDictionary<DmlExtension> extensions = 
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartTrendlineAttr.EXTENSIONS);
        if (extensions != null)
            lhs.setProperty(DmlChartTrendlineAttr.EXTENSIONS, DmlExtensionListSource.cloneExtensions(extensions));

        return lhs;
    }

    void setProperty(/*DmlChartTrendlineAttr*/int attr, Object value)
    {
        mPropertyBag.setProperty((int)attr, value);
    }

    Object getProperty(/*DmlChartTrendlineAttr*/int attr)
    {
        return mPropertyBag.getProperty((int)attr);
    }

    Object getDirectProperty(/*DmlChartTrendlineAttr*/int attr)
    {
        return mPropertyBag.getDirectProperty((int)attr);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="attr">the attr</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlChartTrendlineAttr*/int attr)
    {
        return mPropertyBag.isPropertySpecified((int)attr);
    }

    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();

    private static /*final*/ DmlChartTrendlinePr G_DEFAULTS;

    private static class DmlChartTrendlinePrParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        public DmlChartTrendlinePrParentBagProvider(DmlChartTrendlinePr parentPr)
        {
            mParentPr = parentPr;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return (mParentPr != null) ? mParentPr.mPropertyBag : null; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlChartTrendlinePrParentBagProvider clone = (DmlChartTrendlinePrParentBagProvider)memberwiseClone();
            if (mParentPr != null)
                clone.mParentPr = mParentPr.deepClone();
            return clone;
        }

        private DmlChartTrendlinePr mParentPr;

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

    static/* DmlChartTrendlinePr()*/
    {
        G_DEFAULTS = new DmlChartTrendlinePr();
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.BACKWARD, 0.0d);
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.DISP_EQ, false);
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.DISP_R_SQR, false);
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.FORWARD, 0.0d);
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.INTERCEPT, Double.NaN);
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.NAME, null);
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.ORDER, 2);
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.PERIOD, 2);
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.TRENDLINE_LBL, null);
        G_DEFAULTS.setProperty(DmlChartTrendlineAttr.TRENDLINE_TYPE, TrendlineType.LINEAR);
    }

}

