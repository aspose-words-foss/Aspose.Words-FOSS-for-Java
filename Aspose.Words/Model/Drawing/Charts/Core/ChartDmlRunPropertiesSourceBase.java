// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.DrColor;
import com.aspose.HSLColor;


/// <summary>
/// Represents a base for <see cref="IRunAttrSource"/> implementations to provide <see cref="Font"/> property in
/// chart objects.
/// </summary>
abstract class ChartDmlRunPropertiesSourceBase implements IRunAttrSource
{
    /// <summary>
    /// Ctor to create an instance of this class.
    /// </summary>
    ChartDmlRunPropertiesSourceBase(DmlChartSpace chartSpace)
    {
        mChartSpace = chartSpace;
    }

    /// <summary>
    /// Gets the property value specified directly on the source or <b>null</b> by the key.
    /// </summary>
    public Object getDirectRunAttr(int key)
    {
        return getDirectRunAttr(key, RevisionsView.ORIGINAL);
    }

    /// <summary>
    /// Gets the property value specified directly on the source or <b>null</b> by the key.
    /// </summary>
    public Object getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        if ((getGeneralRunProperties() != null) && RunPrConverter.isPropertySpecified(getGeneralRunProperties(), key))
            return getPropertyValue(key, getGeneralRunProperties());

        // A Font object doesn't try to get inherited Fill attribute: do it here.
        if (key == FontAttr.EFFECT_FILL)
            return fetchInheritedRunPropertyValue(key);

        return null;
    }

    /// <summary>
    /// Gets the property value from one of the parents or from defaults.
    /// </summary>
    public Object fetchInheritedRunAttr(int key)
    {
        Object value = fetchInheritedRunPropertyValue(key);
        if (value != null)
            return value;

        return fetchDefaultRunPropertyValue(key);
    }

    /// <summary>
    /// Sets the property value.
    /// </summary>
    public void setRunAttr(int key, Object value)
    {
        if (needThrowExceptionWhenSettingRunAttr(key))
            throw new IllegalStateException("The property is not supported on a chart object.");

        ensureGeneralRunPropertiesExist(true);

        // Set the property for all runs/fields and paragraphs of the text.
        for (DmlRunProperties runProperties : getRunPropertiesEnumerable())
            RunPrConverter.setProperty(runProperties, key, value, getTheme());

        // Set the flag indicating that run properties of the paragraphs are not empty.
        for (DmlParagraph paragraph : getParagraphEnumerable())
            paragraph.getProperties().hasDefaultRunProperties(true);
    }

    /// <summary>
    /// Removes the direct property value if it exists.
    /// </summary>
    public void removeRunAttr(int key)
    {
        // Clear the property in the all runs/fields and paragraphs of the text.
        for (DmlRunProperties runProperties : getRunPropertiesEnumerable())
            RunPrConverter.clearProperty(runProperties, key);
    }

    /// <summary>
    /// Resets properties of a chart object so that properties of the parent object will be used.
    /// </summary>
    public void clearRunAttrs()
    {
        clearAllRunProperties();
    }

    /// <summary>
    /// Clears all properties of all instances of <see cref="DmlRunProperties"/> related to the current chart object.
    /// </summary>
    protected /*virtual*/ void clearAllRunProperties()
    {
        for (DmlRunProperties runProperties : getRunPropertiesEnumerable())
            runProperties.removeAll();
    }

    /// <summary>
    /// Fetches the specified property from the parent paragraph, collection or chart space. Run defaults are not
    /// returned.
    /// </summary>
    protected abstract Object fetchInheritedRunPropertyValue(int key);

    /// <summary>
    /// Returns the default property value of a chart run.
    /// </summary>
    protected /*virtual*/ Object fetchDefaultRunPropertyValue(int key)
    {
        // See DmlChartFontDefaults.GetDefaultRunPr for more information.
        switch (key)
        {
            case FontAttr.NAME_ASCII:
            {
                String fontName = (getTheme() != null)
                    ? ((IThemeProvider)getTheme()).getFontName(ThemeFontCore.MINOR_ASCII)
                    : null;

                return ComplexFontName.fromName(
                    (fontName != null)
                        ? fontName
                        : DmlChartFontDefaults.DEFAULT_FONT_NAME);
            }
            case FontAttr.SIZE:
                return ConvertUtilCore.pointToHalfPoint(
                    isChartEx() ? 9 : DmlChartFontDefaults.DEFAULT_FONT_SIZE_IN_POINTS);
            case FontAttr.KERNING:
                return isChartEx() ? 0 : 24; // half points
            case FontAttr.COLOR:
                return getDefaultTextColor();
            default:
                return RunPr.fetchDefaultAttr(key);
        }
    }

    /// <summary>
    /// Gets an enumerable over all instances of <see cref="DmlRunProperties"/> related to the current chart object.
    /// </summary>
    protected abstract Iterable<DmlRunProperties> getRunPropertiesEnumerable();

    /// <summary>
    /// Gets an enumerable over all instances of <see cref="DmlParagraph"/> related to the current chart object.
    /// </summary>
    protected abstract Iterable<DmlParagraph> getParagraphEnumerable();

    /// <summary>
    /// Generates <see cref="GeneralRunProperties"/> if it is <b>null</b>.
    /// </summary>
    protected abstract void ensureGeneralRunPropertiesExist(boolean toSetProperty);

    /// <summary>
    /// Gets a value from the specified parent <see cref="DmlRunProperties"/> and, if necessary, clones it and puts
    /// to <see cref="GeneralRunProperties"/>.
    /// </summary>
    /// <remarks>
    /// If The property value is an object instance, state of which can be modified through API, we need to make
    /// a clone and place to the object's run properties so that the parent will not be affected.
    /// </remarks>
    protected Object getValueFromParentRunProperties(int key, DmlRunProperties parentRunProperties)
    {
        Object value = getPropertyValue(key, parentRunProperties);
        if ((value != null) && needCloneInheritedRunPropertyValue(key))
        {
            // The 'value' is a mutable object instance: we need to make a clone and put to GeneralRunProperties.
            value = cloneRunPropertyValue(value);
            ensureGeneralRunPropertiesExist(false);
            RunPrConverter.setProperty(getGeneralRunProperties(), key, value, getTheme());
        }

        return value;
    }

    /// <summary>
    /// Gets a property value specified by the font attribute key.
    /// </summary>
    protected Object getPropertyValue(int key, DmlRunProperties runProperties)
    {
        Theme theme = getTheme();
        // Let's resolve theme font names like "+mn-cs".
        if ((theme == null) && isFontNameKey(key))
            theme = Theme.getBuiltInTheme();

        return RunPrConverter.getProperty(runProperties, key, theme, true);
    }

    /// <summary>
    /// Gets a flag indicating whether the specified font attribute key is a font name key.
    /// </summary>
    private static boolean isFontNameKey(int key)
    {
        switch (key)
        {
            case FontAttr.NAME_ASCII:
            case FontAttr.NAME_BI:
            case FontAttr.NAME_FAR_EAST:
            case FontAttr.NAME_OTHER:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a flag indicating whether the specified run attribute is not supported, and if an exception should
    /// be generated when trying to set it.
    /// </summary>
    private static boolean needThrowExceptionWhenSettingRunAttr(int key)
    {
        switch (key)
        {
            // These Font properties are currently not supported by chart objects. Font.Border.get and
            // Font.Shading.get have a behavior that if the property value is null, a new instance is
            // created and set to IRunAttrSource: do not generate the exception at this case.
            case FontAttr.BORDER:
            case FontAttr.SHADING:
                return false;
            default:
                return !RunPrConverter.isPropertySupported(key);
        }
    }

    /// <summary>
    /// Returns a flag indicating whether the specified run property value need to be cloned when inherited value
    /// is used. This method should return <b>true</b> for all non-immutable objects.
    /// </summary>
    private static boolean needCloneInheritedRunPropertyValue(int key)
    {
        switch (key)
        {
            case FontAttr.EFFECT_FILL:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Clones the specified run property value if required.
    /// </summary>
    private static Object cloneRunPropertyValue(Object value)
    {
        if (value instanceof DmlFill)
            return ((DmlFill)value).deepClone();

        return value;
    }

    /// <summary>
    /// Gets a color that is used by default to display the text.
    /// </summary>
    private DrColor getDefaultTextColor()
    {
        if (!isChartEx())
            return DrColor.EMPTY;

        HSLColor hslColor = new HSLColor(DrColor.fromNativeColor(getTheme().getColors().getDark1()));
        // It seems MS Word calculates the color in this way.
        hslColor.setLum(1.0 - (1.0 - hslColor.getLum()) * 0.65);
        return hslColor.toDrColor();
    }

    /// <summary>
    /// Gets text properties of the parent chart space.
    /// </summary>
    protected DmlChartTxPr getChartSpaceTxPr() { return mChartSpace.getTxPr(); }

    /// <summary>
    /// Gets a theme of the document.
    /// </summary>
    protected Theme getTheme() { return mChartSpace.getDml().getDocument().getThemeInternal(); }

    /// <summary>
    /// Gets a flag indicating whether the current chart is a Word 2016 chart.
    /// </summary>
    protected boolean isChartEx() { return mChartSpace.isChartEx(); }

    /// <summary>
    /// Gets DML run properties that is used to get direct property value.
    /// </summary>
    abstract DmlRunProperties getGeneralRunProperties();

    private /*final*/ DmlChartSpace mChartSpace;
}

