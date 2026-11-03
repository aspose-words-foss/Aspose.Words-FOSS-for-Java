// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This class represents the 2.24.3.7 CT_Binning complex type [MS-ODRAWXML].
/// It specifies data binning properties.
/// </summary>
class DmlChartBinningPr implements Cloneable
{
    DmlChartBinningPr()
    {
        setParent(G_DEFAULTS);
        setProperty(DmlChartBinningAttr.UNDERFLOW, DoubleOrAutomatic.asNull(0.0));
        setProperty(DmlChartBinningAttr.OVERFLOW, DoubleOrAutomatic.asNull(0.0));
    }

    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    DmlChartBinningPr deepClone()
    {
        DmlChartBinningPr lhs = (DmlChartBinningPr)memberwiseClone();
        lhs.mPropertyBag = mPropertyBag.deepClone();

        DoubleOrAutomatic underflow = (DoubleOrAutomatic)getProperty(DmlChartBinningAttr.UNDERFLOW);
        lhs.setProperty(DmlChartBinningAttr.UNDERFLOW, underflow.deepClone());
        DoubleOrAutomatic overflow = (DoubleOrAutomatic)getProperty(DmlChartBinningAttr.OVERFLOW);
        lhs.setProperty(DmlChartBinningAttr.OVERFLOW, overflow.deepClone());

        return lhs;
    }

    /// <summary>
    /// Set parent container of property values.
    /// </summary>
    void setParent(DmlChartBinningPr parentBag)
    {
        mPropertyBag.setParentBagProvider(new DmlChartBinningPrParentBagProvider(parentBag));
    }

    /// <summary>
    /// Sets property value.
    /// </summary>
    void setProperty(/*DmlChartBinningAttr*/int attr, Object value)
    {
        mPropertyBag.setProperty((int)attr, value);
    }

    /// <summary>
    /// Gets property value. If it is not defined, default value is returned.
    /// </summary>
    Object getProperty(/*DmlChartBinningAttr*/int attr)
    {
        return mPropertyBag.getProperty((int)attr);
    }

    /// <summary>
    /// Gets property value. If it is not defined, <c>null</c> is returned.
    /// </summary>
    Object getDirectProperty(/*DmlChartBinningAttr*/int attr)
    {
        return mPropertyBag.getDirectProperty((int)attr);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="attr">the attr</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlChartBinningAttr*/int attr)
    {
        return mPropertyBag.isPropertySpecified((int)attr);
    }

    /// <summary>
    /// Number of defined properties.
    /// </summary>
    int getCount() { return mPropertyBag.getCount(); }

    /// <summary>
    /// Specifies the bin count. If it is <b>-1</b>, bins are defined by bin size or are calculated automatically.
    /// </summary>
    int getBinCount() { return (/*int*/Integer)getProperty(DmlChartBinningAttr.BIN_COUNT); }
    void setBinCount(int value) { setProperty(DmlChartBinningAttr.BIN_COUNT, value); }

    /// <summary>
    /// Specifies the bin size. If it is <see cref="double.NaN"/>, explicit bin count is defined, or bins are
    /// calculated automatically.
    /// </summary>
    double getBinSize() { return (/*double*/Double)getProperty(DmlChartBinningAttr.BIN_SIZE); }
    void setBinSize(double value) { setProperty(DmlChartBinningAttr.BIN_SIZE, value); }

    /// <summary>
    /// Allows to specify the custom value for underflow bin.
    /// </summary>
    DoubleOrAutomatic getUnderflow() { return (DoubleOrAutomatic)getProperty(DmlChartBinningAttr.UNDERFLOW); }
    void setUnderflow(DoubleOrAutomatic value) { setProperty(DmlChartBinningAttr.UNDERFLOW, value); }

    /// <summary>
    /// Allows to specify the custom value for overflow bin.
    /// </summary>
    DoubleOrAutomatic getOverflow() { return (DoubleOrAutomatic)getProperty(DmlChartBinningAttr.OVERFLOW); }
    void setOverflow(DoubleOrAutomatic value) { setProperty(DmlChartBinningAttr.OVERFLOW, value); }

    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();

    private static /*final*/ DmlChartBinningPr G_DEFAULTS;

    private static class DmlChartBinningPrParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        DmlChartBinningPrParentBagProvider(DmlChartBinningPr parentPr)
        {
            mParentPr = parentPr;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return (mParentPr != null) ? mParentPr.mPropertyBag : null; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlChartBinningPrParentBagProvider clone = 
                (DmlChartBinningPrParentBagProvider)memberwiseClone();
            if (mParentPr != null)
                clone.mParentPr = mParentPr.deepClone();
            return clone;
        }

        private DmlChartBinningPr mParentPr;

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
    static/* DmlChartBinningPr()*/
    {
        G_DEFAULTS = new DmlChartBinningPr();
        G_DEFAULTS.setProperty(DmlChartBinningAttr.BIN_SIZE, Double.NaN);
        G_DEFAULTS.setProperty(DmlChartBinningAttr.BIN_COUNT, -1);
        G_DEFAULTS.setProperty(DmlChartBinningAttr.INTERVAL_CLOSED, null);
    }
}

