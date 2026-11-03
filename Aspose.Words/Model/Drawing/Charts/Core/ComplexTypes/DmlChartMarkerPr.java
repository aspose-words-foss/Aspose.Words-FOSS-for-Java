// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;
import com.aspose.IntList;


/// <summary>
/// Represents set of <see cref="ChartMarker"/> properties.
/// </summary>
class DmlChartMarkerPr implements Cloneable
{
    DmlChartMarkerPr()
    {
        setParent(G_DEFAULTS);
    }

    void setParent(DmlChartMarkerPr parentBag)
    {
        mPropertyBag.setParentBagProvider(new DmlChartMarkerPrParentBagProvider(parentBag));
    }

    DmlChartMarkerPr deepClone()
    {
        DmlChartMarkerPr lhs = (DmlChartMarkerPr)memberwiseClone();
        lhs.mPropertyBag = mPropertyBag.deepClone();

        DmlChartSpPr spPr = (DmlChartSpPr)getDirectProperty(DmlChartMarkerAttr.SP_PR);
        if (spPr != null)
            lhs.setProperty(DmlChartMarkerAttr.SP_PR, spPr.deepClone());

        StringToObjDictionary<DmlExtension> extensions = 
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartMarkerAttr.EXTENSIONS);
        if (extensions != null)
            lhs.setProperty(DmlChartMarkerAttr.EXTENSIONS, DmlExtensionListSource.cloneExtensions(extensions));

        return lhs;
    }

    void setProperty(/*DmlChartMarkerAttr*/int attr, Object value)
    {
        mPropertyBag.setProperty((int)attr, value);
    }

    Object getProperty(/*DmlChartMarkerAttr*/int attr)
    {
        return mPropertyBag.getProperty((int)attr);
    }

    Object getDirectProperty(/*DmlChartMarkerAttr*/int attr)
    {
        return mPropertyBag.getDirectProperty((int)attr);
    }

    /// <summary>
    /// Removes the specified property.
    /// </summary>
    void removeProperty(/*DmlChartMarkerAttr*/int attr)
    {
        mPropertyBag.remove((int)attr);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="attr">the attr</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlChartMarkerAttr*/int attr)
    {
        return mPropertyBag.isPropertySpecified((int)attr);
    }

    /// <summary>
    /// Clears contents of this property collection.
    /// </summary>
    void clear()
    {
        mPropertyBag.removeAll();
        mPropertyBag.setExtensionProperties(null);
    }

    int getCount() { return mPropertyBag.getCount(); }

    /// <summary>
    /// Returns <c>true</c> if this property collection redefines any properties of the parent collection.
    /// </summary>
    boolean hasNonDefaultFormatting()
    {
        IntList attributesToIgnore = new IntList();

        // Ignore empty SpPr properties.
        DmlChartSpPr spPr = (DmlChartSpPr)getDirectProperty(DmlChartMarkerAttr.SP_PR);
        if ((spPr == null) || spPr.isEmpty())
            attributesToIgnore.add((int)DmlChartDataLabelAttrs.SP_PR);

        return mPropertyBag.hasNonDefaultFormatting(attributesToIgnore.toArray(), null);
    }

    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();

    private static /*final*/ DmlChartMarkerPr G_DEFAULTS;

    private static class DmlChartMarkerPrParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        DmlChartMarkerPrParentBagProvider(DmlChartMarkerPr parentPr)
        {
            mParentPr = parentPr;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return (mParentPr != null) ? mParentPr.mPropertyBag : null; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlChartMarkerPrParentBagProvider clone = (DmlChartMarkerPrParentBagProvider)memberwiseClone();
            if (mParentPr != null)
                clone.mParentPr = mParentPr.deepClone();
            return clone;
        }

        private DmlChartMarkerPr mParentPr;

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

    static/* DmlChartMarkerPr()*/
    {
        G_DEFAULTS = new DmlChartMarkerPr();
        G_DEFAULTS.setProperty(DmlChartMarkerAttr.SYMBOL, MarkerSymbol.DEFAULT);
        G_DEFAULTS.setProperty(DmlChartMarkerAttr.SIZE, 7);
        G_DEFAULTS.setProperty(DmlChartMarkerAttr.SP_PR, new DmlChartSpPr());
        G_DEFAULTS.setProperty(DmlChartMarkerAttr.EXTENSIONS, null);
    }
}

