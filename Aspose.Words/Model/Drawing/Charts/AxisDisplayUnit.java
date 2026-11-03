// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.StringToObjDictionary;
import com.aspose.FormatterPal;


/// <summary>
/// Provides access to the scaling options of the display units for the value axis.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
/// <dev>
/// Corresponds to the dispUnits element (5.7.2.45, 21.2.2.45).
/// </dev>
public class AxisDisplayUnit implements IDmlChartTitleHolder, IDmlExtensionListSource, Cloneable
{
    /// <summary>
    /// Gets or sets the scaling value of the display units as one of the predefined values.
    /// </summary>
    /// <remarks>
    /// Default value is <see cref="AxisBuiltInUnit.None"/>. The <see cref="AxisBuiltInUnit.Custom"/> and
    /// <see cref="AxisBuiltInUnit.Percentage"/> values are not available in some chart types; see
    /// <see cref="AxisBuiltInUnit"/> for more information.
    /// </remarks>
    public /*AxisBuiltInUnit*/int getUnit() { return mUnit; }
    public void setUnit(/*AxisBuiltInUnit*/int value)
    {
        if (mAxis != null && mAxis.getChart() != null)
        {
            /*DmlChartType*/int chartType = mAxis.getChart().getChartType();
            if ((value == AxisBuiltInUnit.CUSTOM) && (chartType == DmlChartType.CHART_EX_CHART))
                throw new IllegalArgumentException(
                    "The AxisBuiltInUnit.Custom value is not allowed in MS Office 2016 new charts.");
            if ((value == AxisBuiltInUnit.PERCENTAGE) && (chartType != DmlChartType.CHART_EX_CHART))
                throw new IllegalArgumentException(msString.format(
                    "The AxisBuiltInUnit.Percentage value is not supported by the {0} chart type.", chartType));
        }
        mUnit = value;
    }

    /// <summary>
    /// Gets or sets a user-defined divisor to scale display units on the value axis.
    /// </summary>
    /// <remarks>
    /// <para>The property is not supported by MS Office 2016 new charts. Default value is 1.</para>
    /// <para>Setting this property sets the <see cref="Unit"/> property to
    /// <see cref="AxisBuiltInUnit.Custom"/>.</para>
    /// </remarks>
    public double getCustomUnit() { return mCustomUnit; }
    public void setCustomUnit(double value)
    {
        mUnit = AxisBuiltInUnit.CUSTOM;
        mCustomUnit = value;
    }

    /// <summary>
    /// Provides access to properties of the display unit label for the value axis.
    /// </summary>
    /// <dev>
    /// The property is intended for public interface in future.
    /// </dev>
    DmlChartTitle getLabel() { return mLabel; }

    /// <summary>
    /// Gets a flag indicating whether display units are defined.
    /// </summary>
    boolean isDefined()
    {
        return (mUnit != AxisBuiltInUnit.NONE) || (mLabel != null) ||
            ((mExtensions != null) && (mExtensions.getCount() > 0));
    }

    /// <summary>
    /// Represents extLst: a CT_OfficeArtExtensionList ([ISO/IEC29500-1:2012] section A.4.1) element that specifies
    /// the extension list in which all future extensions of element type ext is defined.
    ///  </summary>
    /// <remarks>
    /// Explicit implementation hides this from public.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public StringToObjDictionary<DmlExtension> /*IDmlExtensionListSource.*/getExtensions() { return mExtensions; }
    @ReservedForInternalUse @Deprecated
    public void /*IDmlExtensionListSource.*/setExtensions(StringToObjDictionary<DmlExtension> value) { mExtensions = value; }

    /// <summary>
    /// Sets axis, to which this object belongs.
    /// </summary>
    void setAxis(ChartAxis axis)
    {
        mAxis = axis;
    }

    /// <summary>
    /// Sets display unit label.
    /// </summary>
    void setLabel(DmlChartDisplayUnitsLabel value)
    {
        mLabel = value;
    }

    //>>>>>>>> #region  IDmlChartTitleHolder members

    @ReservedForInternalUse @Deprecated
    public int /*IDmlChartTitleHolder.*/getRelativeFontSize(int chartFontSize)
    {
        return chartFontSize; // An axis display unit uses font size defined on the chart without changes.
    }

    @ReservedForInternalUse @Deprecated
    public DmlChartTitle /*IDmlChartTitleHolder.*/getDCTitle() { return getLabel(); }
    @ReservedForInternalUse @Deprecated
    public void /*IDmlChartTitleHolder.*/setDCTitle(DmlChartTitle value)
    {
        // Do nothing here, DmlChartDisplayUnits actually does not have title,
        // DmlChartDisplayUnitsLabel is rendered instead of title.
    }

    @ReservedForInternalUse @Deprecated
    public /*TitlePosition*/int /*IDmlChartTitleHolder.*/getTitlePosition()
    {
        boolean crossesMax = (mAxis.getCrosses() == AxisCrosses.MAXIMUM);
        boolean labelsLowAndCrossesMax = (mAxis.getTickLabels().getPosition() == AxisTickLabelPosition.LOW) && crossesMax;
        boolean labelsHighAndNotCrossesMax = (mAxis.getTickLabels().getPosition() == AxisTickLabelPosition.HIGH) && !crossesMax;

        switch (mAxis.getActualAxisPosition())
        {
            case AxisPosition.TOP:
                return labelsLowAndCrossesMax? TitlePosition.BOTTOM_RIGHT : TitlePosition.TOP_RIGHT;
            case AxisPosition.BOTTOM:
                return labelsHighAndNotCrossesMax ? TitlePosition.TOP_RIGHT : TitlePosition.BOTTOM_RIGHT;
            case AxisPosition.LEFT:
                return labelsHighAndNotCrossesMax ? TitlePosition.RIGHT_TOP : TitlePosition.LEFT_TOP;
            case AxisPosition.RIGHT:
                return labelsLowAndCrossesMax ? TitlePosition.LEFT_TOP : TitlePosition.RIGHT_TOP;
            default:
                return TitlePosition.TOP_RIGHT;
        }
    }

    /// <summary>
    /// Indicates whether the title of display unit extends beyond the chart space.
    /// </summary>
    /// <remarks>
    /// WORDSNET-24266 MS Words in some cases renders display unit title outside the chart space.
    /// </remarks>
    boolean getDoesGoBeyondChart()
    {
        /*TitlePosition*/int basedOnAxisPosition;

        switch (mAxis.getActualAxisPosition())
        {
            case AxisPosition.TOP:
                basedOnAxisPosition = TitlePosition.TOP_RIGHT;
                break;
            case AxisPosition.BOTTOM:
                basedOnAxisPosition = TitlePosition.BOTTOM_RIGHT;
                break;
            case AxisPosition.LEFT:
                basedOnAxisPosition = TitlePosition.LEFT_TOP;
                break;
            case AxisPosition.RIGHT:
                basedOnAxisPosition = TitlePosition.RIGHT_TOP;
                break;
            default:
                basedOnAxisPosition = TitlePosition.TOP_RIGHT;
                break;
        }

        return ((IDmlChartTitleHolder)this).getTitlePosition() != basedOnAxisPosition;
    }

    /// <summary>
    /// Returns the document containing the parent chart.
    /// </summary>
    public DocumentBase getDocument() { return mAxis.getDocument(); }

    @ReservedForInternalUse @Deprecated
    public boolean /*IDmlChartTitleHolder.*/getTitleDeleted() { return false;}
    @ReservedForInternalUse @Deprecated
    public void /*IDmlChartTitleHolder.*/setTitleDeleted(boolean value)
    {
        // Do nothing, because DmlChartDisplayUnits actually does not have title.
        // DmlChartDisplayUnitsLabel is rendered instead of title.
    }

    /// <summary>
    /// Indicates whether the display unit is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IDmlChartTitleHolder.*/isVisible() { return (getLabel() != null); }

    /// <summary>
    /// Gets the default title text.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public String /*IDmlChartTitleHolder.*/getDefaultTitleText() { return null; }

    /// <summary>
    /// Gets the default font size in points.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IDmlChartTitleHolder.*/getDefaultFontSize() { return mAxis.getChart().getChartSpace().isChartEx() ? 9 : 10; }

    /// <summary>
    /// Gets the title font size in points that MS Word sets for created charts.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IDmlChartTitleHolder.*/getDefaultDisplayedFontSize() { return ((IDmlChartTitleHolder)this).getDefaultFontSize(); }

    @ReservedForInternalUse @Deprecated
    public /*DmlChartStyleItem*/int /*IDmlChartTitleHolder.*/getStyleItem() { return DmlChartStyleItem.VALUE_AXIS; }

    /// <summary>
    /// Gets the parent chart space.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DmlChartSpace /*IDmlChartTitleHolder.*/getChartSpace() { return mAxis.getChart().getChartSpace(); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Clones this instance of display units.
    /// </summary>
    AxisDisplayUnit deepClone()
    {
        AxisDisplayUnit lhs = (AxisDisplayUnit)memberwiseClone();

        if (mLabel != null)
        {
            lhs.mLabel = (DmlChartDisplayUnitsLabel)mLabel.deepClone();
            lhs.mLabel.setTitleHolder(lhs);
        }

        if (mExtensions != null)
            lhs.mExtensions = DmlExtensionListSource.cloneExtensions(mExtensions);

        return lhs;
    }

    /// <summary>
    /// Returns text that should be rendered as <see cref="Label"/> if its text is not set explicitly.
    /// </summary>
    /// <returns></returns>
    String getDisplayUnitsLabel()
    {
        switch (mUnit)
        {
            case AxisBuiltInUnit.HUNDREDS:
                return "Hundreds";
            case AxisBuiltInUnit.THOUSANDS:
                return "Thousands";
            case AxisBuiltInUnit.MILLIONS:
                return "Millions";
            case AxisBuiltInUnit.BILLIONS:
                return "Billions";
            case AxisBuiltInUnit.TRILLIONS:
                return "Trillions";
            default:
                return msString.format("x {0}", getNumberLabel());
        }
    }

    /// <summary>
    /// Returns number text that should be rendered as <see cref="Label"/> if its text is not set explicitly.
    /// </summary>
    String getNumberLabel()
    {
        double unit = getActualUnit();

        return ((unit < 1e-9) || (unit > 9.9999999999e10))
            ? FormatterPal.doubleToExponential(unit)
            : FormatterPal.doubleToStr9Decimals(unit);
    }

    /// <summary>
    /// Returns double value that should be used as divider of actual value before displaying.
    /// </summary>
    double getActualUnit()
    {
        switch (mUnit)
        {
            case AxisBuiltInUnit.HUNDREDS:
                return 100.0d;
            case AxisBuiltInUnit.THOUSANDS:
                return 1000.0d;
            case AxisBuiltInUnit.TEN_THOUSANDS:
                return 10000.0d;
            case AxisBuiltInUnit.HUNDRED_THOUSANDS:
                return 100000.0d;
            case AxisBuiltInUnit.MILLIONS:
                return 1000000.0d;
            case AxisBuiltInUnit.TEN_MILLIONS:
                return 10000000.0d;
            case AxisBuiltInUnit.HUNDRED_MILLIONS:
                return 100000000.0d;
            case AxisBuiltInUnit.BILLIONS:
                return 1000000000.0d;
            case AxisBuiltInUnit.TRILLIONS:
                return 1000000000000.0d;
            case AxisBuiltInUnit.CUSTOM:
                return mCustomUnit;
            default:
                return 1.0d;
        }
    }

    private /*AxisBuiltInUnit*/int mUnit = AxisBuiltInUnit.NONE;
    private double mCustomUnit = 1.0;
    private DmlChartDisplayUnitsLabel mLabel;
    private ChartAxis mAxis;
    private StringToObjDictionary<DmlExtension> mExtensions;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

