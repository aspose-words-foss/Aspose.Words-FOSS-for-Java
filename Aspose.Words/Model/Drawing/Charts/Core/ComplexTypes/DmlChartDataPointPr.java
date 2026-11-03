// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;
import com.aspose.StringUtil;
import com.aspose.IntList;


/// <summary>
/// Represents set of <see cref="ChartDataPoint"/> properties.
/// </summary>
class DmlChartDataPointPr implements Cloneable
{
    private DmlChartDataPointPr(boolean isDefaultTag)
    {
        // intentionally left blank
        // WORDSNET-12521
        // Because unexpected behaviour while in multi-threading mode
        // We have to create DEFAULTS every time when DmlChartDataPointPr is created.
        // It helps to avoid changing of DEFAULT tags.
        // It will be removed after the issue is fixed.
    }

    DmlChartDataPointPr(DocumentBase doc)
    {
        setParent(getDefaults(doc));
    }

    void setParent(DmlChartDataPointPr parentBag)
    {
        mPropertyBag.setParentBagProvider(new DmlChartDataPointPrParentBagProvider(parentBag));
        mParentPr = parentBag;

        setMarkerParent((ChartMarker)getDirectProperty(DmlChartDataPointAttr.MARKER));
    }

    DmlChartDataPointPr deepClone()
    {
        DmlChartDataPointPr lhs = (DmlChartDataPointPr)memberwiseClone();
        lhs.mPropertyBag = mPropertyBag.deepClone();

        ChartMarker marker = (ChartMarker)getDirectProperty(DmlChartDataPointAttr.MARKER);
        if (marker != null)
            lhs.setProperty(DmlChartDataPointAttr.MARKER, marker.deepClone());

        DmlChartSpPr spPr = (DmlChartSpPr)getDirectProperty(DmlChartDataPointAttr.SP_PR);
        if (spPr != null)
            lhs.setProperty(DmlChartDataPointAttr.SP_PR, spPr.deepClone());

        DmlChartPictureOptions options = 
            (DmlChartPictureOptions)getDirectProperty(DmlChartDataPointAttr.PICTURE_OPTIONS);
        if (options != null)
            lhs.setProperty(DmlChartDataPointAttr.PICTURE_OPTIONS, options.deepClone());

        StringToObjDictionary<DmlExtension> extensions = 
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartDataPointAttr.EXTENSIONS);
        if (extensions != null)
            lhs.setProperty(DmlChartDataPointAttr.EXTENSIONS, DmlExtensionListSource.cloneExtensions(extensions));

        return lhs;
    }

    void setProperty(/*DmlChartDataPointAttr*/int attr, Object value)
    {
        mPropertyBag.setProperty((int)attr, value);

        if (attr == DmlChartDataPointAttr.MARKER)
            setMarkerParent((ChartMarker)value);
    }

    /// <summary>
    /// Removes the specified property.
    /// </summary>
    void removeProperty(/*DmlChartDataPointAttr*/int attr)
    {
        mPropertyBag.remove((int)attr);
    }

    Object getProperty(/*DmlChartDataPointAttr*/int attr)
    {
        return mPropertyBag.getProperty((int)attr);
    }

    Object getDirectProperty(/*DmlChartDataPointAttr*/int attr)
    {
        return mPropertyBag.getDirectProperty((int)attr);
    }

    /// <summary>
    /// Gets the property value from the parent bag provider.
    /// </summary>
    Object getInheritedProperty(/*DmlChartDataPointAttr*/int attr)
    {
        return mPropertyBag.getParentBagProvider().getParentBag().getProperty((int)attr);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="attr">the attr</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlChartDataPointAttr*/int attr)
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

    /// <summary>
    /// Copies properties set directly from the specified data point property collection.
    /// The <see cref="DmlChartDataPointAttr.Index"/> property is not copied.
    /// </summary>
    void copyDirectPropertiesFrom(DmlChartDataPointPr pointPr)
    {
        for (/*DmlChartDataPointAttr*/int attribute : DmlChartDataPointAttr.getValues())
        {
            if (!pointPr.isPropertySpecified(attribute) || (attribute == DmlChartDataPointAttr.INDEX))
                continue;

            Object value = pointPr.getDirectProperty(attribute);

            switch (attribute)
            {
                case DmlChartDataPointAttr.MARKER:
                {
                    if (value != null)
                        value = ((ChartMarker)value).deepClone();

                    break;
                }
                case DmlChartDataPointAttr.SP_PR:
                {
                    if (value != null)
                        value = ((DmlChartSpPr)value).deepClone();

                    break;
                }
                case DmlChartDataPointAttr.PICTURE_OPTIONS:
                {
                    if (value != null)
                        value = ((DmlChartPictureOptions)value).deepClone();

                    break;
                }
                case DmlChartDataPointAttr.EXTENSIONS:
                    copyExtensionsFrom((StringToObjDictionary<DmlExtension>)value);
                    continue;
            }

            setProperty(attribute, value);
        }
    }

    private void copyExtensionsFrom(StringToObjDictionary<DmlExtension> source)
    {
        if (source == null)
            return;

        StringToObjDictionary<DmlExtension> extensions =
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartDataPointAttr.EXTENSIONS);
        if (extensions == null)
            extensions = new StringToObjDictionary<DmlExtension>(false);

        boolean areExtensionsCopied = false;

        for (DmlExtension ext : source.getValues())
        {
            // Let's skip copying unique ID.
            if (StringUtil.equalsOrdinalIgnoreCase(ext.getUri(), DmlExtensionUri.UNIQUE_ID))
                continue;

            extensions.set(ext.getUri(), ext.deepClone());
            areExtensionsCopied = true;
        }

        if (areExtensionsCopied)
            setProperty(DmlChartDataPointAttr.EXTENSIONS, extensions);
    }

    /// <summary>
    /// Returns <c>true</c> if this property collection redefines any properties of the parent collection.
    /// </summary>
    boolean hasNonDefaultFormatting()
    {
        ChartMarker marker = (ChartMarker)getDirectProperty(DmlChartDataPointAttr.MARKER);
        if ((marker != null) && marker.hasNonDefaultFormatting())
            return true;

        IntList attributesToIgnore = new IntList();

        // Ignore since each point has its own index.
        attributesToIgnore.add((int)DmlChartDataPointAttr.INDEX);
        attributesToIgnore.add((int)DmlChartDataPointAttr.MARKER); // Already checked.
        attributesToIgnore.add((int)DmlChartDataPointAttr.EXTENSIONS); // Checked separately below.

        // Ignore empty SpPr properties.
        DmlChartSpPr spPr = (DmlChartSpPr)getDirectProperty(DmlChartDataPointAttr.SP_PR);
        if ((spPr == null) || spPr.isEmpty())
            attributesToIgnore.add((int)DmlChartDataPointAttr.SP_PR);

        if (mPropertyBag.hasNonDefaultFormatting(attributesToIgnore.toArray(), null))
            return true;

        StringToObjDictionary<DmlExtension> extensions =
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartDataPointAttr.EXTENSIONS);

        if (extensions != null)
        {
            for (String uri : extensions.getKeys())
            {
                // Ignore UniqueId since it seems it has no any useful information.
                // If another extension exists, just consider that the extensions are different (no way to
                // compare them now).
                if (!StringUtil.equalsOrdinalIgnoreCase(uri, DmlExtensionUri.UNIQUE_ID))
                    return true;
            }
        }

        return false;
    }

    private void setMarkerParent(ChartMarker marker)
    {
        if (marker == null)
            return;

        ChartMarker parentMarker = (ChartMarker)mParentPr.getDirectProperty(DmlChartDataPointAttr.MARKER);
        if (parentMarker != null)
            marker.getMarkerPr().setParent(parentMarker.getMarkerPr());
    }

    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();
    private DmlChartDataPointPr mParentPr;

    // WORDSNET-12521
    private static DmlChartDataPointPr getDefaults(DocumentBase doc)
    {
        // WORDSNET-24940 The defaults depend on the document version (7.2.2.2 AppVersion (Application Version)).
        boolean is2007OrLower = DmlChartUtil.isMsWord2007OrLower(doc);

        DmlChartDataPointPr defaults = new DmlChartDataPointPr(true);
        defaults.setProperty(DmlChartDataPointAttr.INDEX, 0);
        defaults.setProperty(DmlChartDataPointAttr.EXPLOSION, -1);
        defaults.setProperty(DmlChartDataPointAttr.INVERT_IF_NEGATIVE, !is2007OrLower);
        defaults.setProperty(DmlChartDataPointAttr.BUBBLE_3_D, false);
        defaults.setProperty(DmlChartDataPointAttr.MARKER, null);
        defaults.setProperty(DmlChartDataPointAttr.SP_PR, null);
        defaults.setProperty(DmlChartDataPointAttr.PICTURE_OPTIONS, null);
        defaults.setProperty(DmlChartDataPointAttr.EXTENSIONS, null);
        return defaults;
    }

    private static class DmlChartDataPointPrParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        public DmlChartDataPointPrParentBagProvider(DmlChartDataPointPr parentPr)
        {
            mParentPr = parentPr;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return (mParentPr != null) ? mParentPr.mPropertyBag : null; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlChartDataPointPrParentBagProvider clone = (DmlChartDataPointPrParentBagProvider)memberwiseClone();
            if (mParentPr != null)
                clone.mParentPr = mParentPr.deepClone();
            return clone;
        }

        private DmlChartDataPointPr mParentPr;

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
}

