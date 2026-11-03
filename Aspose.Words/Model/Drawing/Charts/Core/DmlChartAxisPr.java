// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Class contains chart axis properties.
/// </summary>
class DmlChartAxisPr implements Cloneable
{
    DmlChartAxisPr(DocumentBase document, boolean isChartEx)
    {
    	this(getDefaults(document, isChartEx));
	
    }

    private DmlChartAxisPr()
    {
    	this((DmlChartAxisPr)null);
	
    }

    private DmlChartAxisPr(DmlChartAxisPr defaults)
    {
        if (defaults != null)
            mPrBag.setParentBagProvider(new DmlChartAxisPrParentBagProvider(defaults));

        // Also all complex types must be present in properties directly.
        mPrBag.setProperty((int)DmlChartAxisAttrs.SP_PR, new DmlChartSpPr());
        mPrBag.setProperty((int)DmlChartAxisAttrs.TX_PR, new DmlChartTxPr());
        // MajorUnit and MinorUnit allows only positive values, let's set default values as in renderer.
        mPrBag.setProperty((int)DmlChartAxisAttrs.MAJOR_UNIT, DoubleOrAutomatic.asNull(1.0));
        mPrBag.setProperty((int)DmlChartAxisAttrs.MINOR_UNIT, DoubleOrAutomatic.asNull(0.5));
        mPrBag.setProperty((int)DmlChartAxisAttrs.SCALING, new AxisScaling());
    }

    DmlChartAxisPr deepClone()
    {
        DmlChartAxisPr lhs = (DmlChartAxisPr)memberwiseClone();
        lhs.mPrBag = mPrBag.deepClone();

        DmlChartSpPr spPr = (DmlChartSpPr)getProperty(DmlChartAxisAttrs.SP_PR);
        lhs.setProperty(DmlChartAxisAttrs.SP_PR, spPr.deepClone());

        DmlChartTxPr txPr = (DmlChartTxPr)getProperty(DmlChartAxisAttrs.TX_PR);
        lhs.setProperty(DmlChartAxisAttrs.TX_PR, txPr.deepClone());

        DoubleOrAutomatic majorUnit = (DoubleOrAutomatic)getProperty(DmlChartAxisAttrs.MAJOR_UNIT);
        lhs.setProperty(DmlChartAxisAttrs.MAJOR_UNIT, majorUnit.deepClone());

        DoubleOrAutomatic minorUnit = (DoubleOrAutomatic)getProperty(DmlChartAxisAttrs.MINOR_UNIT);
        lhs.setProperty(DmlChartAxisAttrs.MINOR_UNIT, minorUnit.deepClone());

        AxisScaling scaling = (AxisScaling)getProperty(DmlChartAxisAttrs.SCALING);
        lhs.setProperty(DmlChartAxisAttrs.SCALING, scaling.deepClone());

        DmlChartGridlines majorGridlines = (DmlChartGridlines)getDirectProperty(DmlChartAxisAttrs.MAJOR_GRIDLINES);
        if (majorGridlines != null)
            lhs.setProperty(DmlChartAxisAttrs.MAJOR_GRIDLINES, majorGridlines.deepClone());

        DmlChartGridlines minorGridlines = (DmlChartGridlines)getDirectProperty(DmlChartAxisAttrs.MINOR_GRIDLINES);
        if (minorGridlines != null)
            lhs.setProperty(DmlChartAxisAttrs.MINOR_GRIDLINES, minorGridlines.deepClone());

        cloneExtensionsAttribute(DmlChartAxisAttrs.TICK_LBL_EXTENSIONS, lhs);
        cloneExtensionsAttribute(DmlChartAxisAttrs.MAJOR_TICK_MARK_EXTENSIONS, lhs);
        cloneExtensionsAttribute(DmlChartAxisAttrs.MINOR_TICK_MARK_EXTENSIONS, lhs);

        AxisDisplayUnit displayUnit = (AxisDisplayUnit)getDirectProperty(DmlChartAxisAttrs.DISPLAY_UNIT);
        if (displayUnit != null)
            lhs.setProperty(DmlChartAxisAttrs.DISPLAY_UNIT, displayUnit.deepClone());

        DmlChartNumFormat numFmt = (DmlChartNumFormat)getDirectProperty(DmlChartAxisAttrs.NUM_FMT);
        if (numFmt != null)
            lhs.setProperty(DmlChartAxisAttrs.NUM_FMT, numFmt.deepClone());

        return lhs;
    }

    /// <summary>
    /// Clones the specified extensions attribute.
    /// </summary>
    private void cloneExtensionsAttribute(/*DmlChartAxisAttrs*/int attribute, DmlChartAxisPr destination)
    {
        StringToObjDictionary<DmlExtension> extensions = (StringToObjDictionary<DmlExtension>)getDirectProperty(attribute);
        if (extensions != null)
            destination.setProperty(attribute, DmlExtensionListSource.cloneExtensions(extensions));
    }

    Object getProperty(/*DmlChartAxisAttrs*/int attr)
    {
        return mPrBag.getProperty((int)attr);
    }

    void setProperty(/*DmlChartAxisAttrs*/int attr, Object value)
    {
        mPrBag.setProperty((int)attr, value);
    }

    /// <summary>
    /// Returns null if property is not set explicitly
    /// </summary>
    Object getDirectProperty(/*DmlChartAxisAttrs*/int attr)
    {
        return mPrBag.getDirectProperty((int)attr);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="attr">the attr</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlChartAxisAttrs*/int attr)
    {
        return mPrBag.isPropertySpecified((int)attr);
    }

    private IDmlHierarchicalPropertyBag mPrBag = new DmlHierarchicalPropertyBag();

    /// <summary>
    /// Gets a <seealso cref="DmlChartAxisPr"/> instance that should be used as default properties for
    /// an axis, given the specified document and Word 2016 chart flag.
    /// </summary>
    private static DmlChartAxisPr getDefaults(DocumentBase document, boolean isChartEx)
    {
        if (isChartEx)
            return G_CHART_EX_DEFAULT_PR;

        return DmlChartUtil.isMsWord2007OrLower(document) ? G_DEFAULT_PR : G_DEFAULT_PR_AFTER_2007;
    }

    private static DmlChartAxisPr setDefaults()
    {
        DmlChartAxisPr defaults = new DmlChartAxisPr();
        // No default value.
        defaults.setProperty(DmlChartAxisAttrs.AX_ID, 0);
        defaults.setProperty(DmlChartAxisAttrs.IS_AUTO_CATEGORY_TYPE, false);
        defaults.setProperty(DmlChartAxisAttrs.IS_DATE_CATEGORY_AXIS, false);
        // No default value. Attribute is required and cannot be omitted.
        defaults.setProperty(DmlChartAxisAttrs.AX_POS, AxisPosition.BOTTOM);
        defaults.setProperty(DmlChartAxisAttrs.BASE_TIME_UNIT, AxisTimeUnit.AUTOMATIC);
        // It seems MS Word always uses autoZero (Automatic) value. So lets use it as default. 
        defaults.setProperty(DmlChartAxisAttrs.CROSSES, AxisCrosses.AUTOMATIC);
        // The property is public, so set it to 0 by default.
        defaults.setProperty(DmlChartAxisAttrs.CROSSES_AT, 0d);
        // No default value. Attribute is required.
        defaults.setProperty(DmlChartAxisAttrs.CROSS_AX, 0);
        defaults.setProperty(DmlChartAxisAttrs.CROSS_BETWEEN, CrossBetween.BETWEEN);
        // By default this is false. Is true if 'delete' element is present.
        defaults.setProperty(DmlChartAxisAttrs.LBL_ALGN, LabelAlignment.DEFAULT);
        // Default is 100.
        defaults.setProperty(DmlChartAxisAttrs.TICK_LABEL_OFFSET, 100);
        // Default is days.
        defaults.setProperty(DmlChartAxisAttrs.MAJOR_UNIT_SCALE, AxisTimeUnit.AUTOMATIC);
        // Default is days.
        defaults.setProperty(DmlChartAxisAttrs.MINOR_UNIT_SCALE, AxisTimeUnit.AUTOMATIC);
        // Tick marks are Cross as default in MS Office starting from the 2010 version.
        defaults.setProperty(DmlChartAxisAttrs.MAJOR_TICK_MARK, AxisTickMark.OUTSIDE);
        defaults.setProperty(DmlChartAxisAttrs.MINOR_TICK_MARK, AxisTickMark.NONE);
        defaults.setProperty(DmlChartAxisAttrs.NO_MULTI_LVL_LBL, false);
        defaults.setProperty(DmlChartAxisAttrs.TICK_LBL_POS, AxisTickLabelPosition.DEFAULT);
        defaults.setProperty(DmlChartAxisAttrs.TICK_LABEL_SPACING, 1);
        defaults.setProperty(DmlChartAxisAttrs.TICK_LABEL_SPACING_IS_AUTO, true);
        defaults.setProperty(DmlChartAxisAttrs.TICK_MARK_SPACING, 1);
        defaults.setProperty(DmlChartAxisAttrs.HIDDEN, false);

        return defaults;
    }

    private static DmlChartAxisPr setDefaultsAfter2007()
    {
        DmlChartAxisPr defaults = setDefaults();

        // Tick marks are Cross as default in MS Office starting from the 2010 version.
        defaults.setProperty(DmlChartAxisAttrs.MAJOR_TICK_MARK, AxisTickMark.CROSS);
        defaults.setProperty(DmlChartAxisAttrs.MINOR_TICK_MARK, AxisTickMark.CROSS);

        return defaults;
    }

    private static DmlChartAxisPr setChartExDefaults()
    {
        DmlChartAxisPr defaults = setDefaults();

        // Tick marks are None as default in Word 2016 charts.
        defaults.setProperty(DmlChartAxisAttrs.MAJOR_TICK_MARK, AxisTickMark.NONE);
        defaults.setProperty(DmlChartAxisAttrs.MINOR_TICK_MARK, AxisTickMark.NONE);

        return defaults;
    }

    private static /*final*/ DmlChartAxisPr G_DEFAULT_PR;
    private static /*final*/ DmlChartAxisPr G_DEFAULT_PR_AFTER_2007;
    private static /*final*/ DmlChartAxisPr G_CHART_EX_DEFAULT_PR;

    private static class DmlChartAxisPrParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        DmlChartAxisPrParentBagProvider(DmlChartAxisPr parentPr)
        {
            mParentPr = parentPr;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return (mParentPr != null) ? mParentPr.mPrBag : null; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlChartAxisPrParentBagProvider lhs = (DmlChartAxisPrParentBagProvider)memberwiseClone();
            if (mParentPr != null)
                lhs.mParentPr = mParentPr.deepClone();
            return lhs;
        }

        private DmlChartAxisPr mParentPr;

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

    static/* DmlChartAxisPr()*/
    {
        G_DEFAULT_PR = setDefaults();
        G_DEFAULT_PR_AFTER_2007 = setDefaultsAfter2007();
        G_CHART_EX_DEFAULT_PR = setChartExDefaults();
    }
}

