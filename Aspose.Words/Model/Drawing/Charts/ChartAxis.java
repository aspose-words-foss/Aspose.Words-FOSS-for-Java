// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ArgumentUtil;
import com.aspose.StringToObjDictionary;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Drawing.RectangleF;


/// <summary>
/// Represents the axis options of the chart.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
public class ChartAxis implements IDmlChartTitleHolder, IDmlExtensionListSource, INumberFormatProvider, IChartFormatSource, Cloneable
{
    ChartAxis(/*ChartAxisType*/int axisType, DmlChart chart, DocumentBase document, boolean isChartEx)
    {
        mAxisType = axisType;
        mChart = chart;
        setChartAxisPr(new DmlChartAxisPr(document, isChartEx));
    }

    //>>>>>>>> #region  Public API.

    /// <summary>
    /// Returns type of the axis.
    /// </summary>
    public /*ChartAxisType*/int getType() { return mAxisType; }

    /// <summary>
    /// Gets or sets type of the category axis.
    /// </summary>
    /// <remarks>
    /// Only text categories (<see cref="AxisCategoryType.Category"/>) are allowed in MS Office 2016 new charts.
    /// </remarks>
    /// <dev>
    /// The property is internally represented as two bool attributes since the <see cref="AxisCategoryType"/> type
    /// provides no enough information that is needed to store the property in document file.
    /// </dev>
    public /*AxisCategoryType*/int getCategoryType()
    {
        if ((/*boolean*/Boolean)getChartAxisPr().getProperty(DmlChartAxisAttrs.IS_AUTO_CATEGORY_TYPE))
            return AxisCategoryType.AUTOMATIC;
        else if ((/*boolean*/Boolean)getChartAxisPr().getProperty(DmlChartAxisAttrs.IS_DATE_CATEGORY_AXIS))
            return AxisCategoryType.TIME;
        else
            return AxisCategoryType.CATEGORY;
    }
    public void setCategoryType(/*AxisCategoryType*/int value)
    {
        switch (value)
        {
            case AxisCategoryType.AUTOMATIC:
                getChartAxisPr().setProperty(DmlChartAxisAttrs.IS_AUTO_CATEGORY_TYPE, true);
                break;
            case AxisCategoryType.TIME:
            {
                getChartAxisPr().setProperty(DmlChartAxisAttrs.IS_AUTO_CATEGORY_TYPE, false);
                getChartAxisPr().setProperty(DmlChartAxisAttrs.IS_DATE_CATEGORY_AXIS, true);
                break;
            }
            case AxisCategoryType.CATEGORY:
            {
                getChartAxisPr().setProperty(DmlChartAxisAttrs.IS_AUTO_CATEGORY_TYPE, false);
                getChartAxisPr().setProperty(DmlChartAxisAttrs.IS_DATE_CATEGORY_AXIS, false);
                break;
            }
            default:
                throw new IllegalArgumentException(msString.format("Wrong value is specified: {0}.", value));
        }
    }

    /// <summary>
    /// Specifies how this axis crosses the perpendicular axis.
    /// </summary>
    /// <remarks>
    /// <para>Default value is <see cref="AxisCrosses.Automatic"/>.</para>
    /// <para>The property is not supported by MS Office 2016 new charts.</para>
    /// </remarks>
    public /*AxisCrosses*/int getCrosses() { return (/*AxisCrosses*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.CROSSES); }
    public void setCrosses(/*AxisCrosses*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.CROSSES, value); }

    /// <summary>
    /// Specifies where on the perpendicular axis the axis crosses.
    /// </summary>
    /// <remarks>
    /// <para>The property has effect only if <see cref="Crosses"/> are set to <see cref="AxisCrosses.Custom"/>.
    /// It is not supported by MS Office 2016 new charts.</para>
    /// <para>The units are determined by the type of axis. When the axis is a value axis, the value of the property
    /// is a decimal number on the value axis. When the axis is a time category axis, the value is defined as
    /// an integer number of days relative to the base date (30/12/1899). For a text category axis, the value is
    /// an integer category number, starting with 1 as the first category.</para>
    /// </remarks>
    public double getCrossesAt() { return (/*double*/Double)getChartAxisPr().getProperty(DmlChartAxisAttrs.CROSSES_AT); }
    public void setCrossesAt(double value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.CROSSES_AT, value); }

    /// <summary>
    /// Returns or sets a flag indicating whether values of axis should be displayed in reverse order, i.e.
    /// from max to min.
    /// </summary>
    /// <remarks>
    /// The property is not supported by MS Office 2016 new charts. Default value is <c>false</c>.
    /// </remarks>
    public boolean getReverseOrder() { return (getScaling().getOrientation() == AxisOrientation.MAX_MIN); }
    public void setReverseOrder(boolean value) { getScaling().setOrientation((value ? AxisOrientation.MAX_MIN : AxisOrientation.MIN_MAX)); }

    /// <summary>
    /// Returns or sets the major tick marks.
    /// </summary>
    public /*AxisTickMark*/int getMajorTickMark() { return (/*AxisTickMark*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.MAJOR_TICK_MARK); }
    public void setMajorTickMark(/*AxisTickMark*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.MAJOR_TICK_MARK, value); }

    /// <summary>
    /// Returns or sets the minor tick marks for the axis.
    /// </summary>
    public /*AxisTickMark*/int getMinorTickMark() { return (/*AxisTickMark*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.MINOR_TICK_MARK); }
    public void setMinorTickMark(/*AxisTickMark*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.MINOR_TICK_MARK, value); }

    /// <summary>
    /// Returns or sets the distance between major tick marks.
    /// </summary>
    /// <remarks>
    /// <para>Valid range of a value is greater than zero. The property has effect for time category and
    /// value axes.</para>
    /// <para>Setting this property sets the <see cref="MajorUnitIsAuto"/> property to <c>false</c>.</para>
    /// </remarks>
    public double getMajorUnit() { return getMajorUnitPr().getValue(); }
    public void setMajorUnit(double value)
    {
        ArgumentUtil.checkPositive(value, "value");
        getMajorUnitPr().setValue(value);
    }

    /// <summary>
    /// Gets or sets a flag indicating whether default distance between major tick marks shall be used.
    /// </summary>
    /// <remarks>
    /// The property has effect for time category and value axes.
    /// </remarks>
    public boolean getMajorUnitIsAuto() { return getMajorUnitPr().isNullOrAuto(); }
    public void setMajorUnitIsAuto(boolean value) { getMajorUnitPr().isAuto(value); }

    /// <summary>
    /// Returns or sets the scale value for major tick marks on the time category axis.
    /// </summary>
    /// <remarks>
    /// The property has effect only for time category axes.
    /// </remarks>
    public /*AxisTimeUnit*/int getMajorUnitScale() { return (/*AxisTimeUnit*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.MAJOR_UNIT_SCALE); }
    public void setMajorUnitScale(/*AxisTimeUnit*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.MAJOR_UNIT_SCALE, value); }

    /// <summary>
    /// Returns or sets the distance between minor tick marks.
    /// </summary>
    /// <remarks>
    /// <para>Valid range of a value is greater than zero. The property has effect for time category and
    /// value axes.</para>
    /// <para>Setting this property sets the <see cref="MinorUnitIsAuto"/> property to <c>false</c>.</para>
    /// </remarks>
    public double getMinorUnit() { return getMinorUnitPr().getValue(); }
    public void setMinorUnit(double value)
    {
        ArgumentUtil.checkPositive(value, "value");
        getMinorUnitPr().setValue(value);
    }

    /// <summary>
    /// Gets or sets a flag indicating whether default distance between minor tick marks shall be used.
    /// </summary>
    /// <remarks>
    /// The property has effect for time category and value axes.
    /// </remarks>
    public boolean getMinorUnitIsAuto() { return getMinorUnitPr().isNullOrAuto(); }
    public void setMinorUnitIsAuto(boolean value) { getMinorUnitPr().isAuto(value); }

    /// <summary>
    /// Returns or sets the scale value for minor tick marks on the time category axis.
    /// </summary>
    /// <remarks>
    /// The property has effect only for time category axes.
    /// </remarks>
    public /*AxisTimeUnit*/int getMinorUnitScale() { return (/*AxisTimeUnit*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.MINOR_UNIT_SCALE); }
    public void setMinorUnitScale(/*AxisTimeUnit*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.MINOR_UNIT_SCALE, value); }

    /// <summary>
    /// Returns or sets the smallest time unit that is represented on the time category axis.
    /// </summary>
    /// <remarks>
    /// The property has effect only for time category axes.
    /// </remarks>
    public /*AxisTimeUnit*/int getBaseTimeUnit() { return (/*AxisTimeUnit*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.BASE_TIME_UNIT); }
    public void setBaseTimeUnit(/*AxisTimeUnit*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.BASE_TIME_UNIT, value); }

    /// <summary>
    /// Returns a <see cref="ChartNumberFormat"/> object that allows defining number formats for the axis.
    /// </summary>
    public ChartNumberFormat getNumberFormat()
    {
        if (mNumberFormat == null)
            mNumberFormat = new ChartNumberFormat(this, mChart);

        return mNumberFormat;
    }

    /// <summary>
    /// Specifies the scaling value of the display units for the value axis.
    /// </summary>
    /// <remarks>
    /// The property has effect only for value axes.
    /// </remarks>
    public AxisDisplayUnit getDisplayUnit()
    {
        AxisDisplayUnit dispUnitObj = (AxisDisplayUnit)getChartAxisPr().getProperty(DmlChartAxisAttrs.DISPLAY_UNIT);
        if (dispUnitObj == null)
        {
            dispUnitObj = new AxisDisplayUnit();
            dispUnitObj.setAxis(this);
            getChartAxisPr().setProperty(DmlChartAxisAttrs.DISPLAY_UNIT, dispUnitObj);
        }
        return dispUnitObj;
    }

    /// <summary>
    /// Gets or sets a flag indicating whether the value axis crosses the category axis between categories.
    /// </summary>
    /// <remarks>
    /// The property has effect only for value axes. It is not supported by MS Office 2016 new charts.
    /// </remarks>
    public boolean getAxisBetweenCategories() { return getCrossBetween() == CrossBetween.BETWEEN; }
    public void setAxisBetweenCategories(boolean value) { setCrossBetween(value ? CrossBetween.BETWEEN : CrossBetween.MIDPOINT_OF_CATEGORY); }

    /// <summary>
    /// Provides access to the scaling options of the axis.
    /// </summary>
    public AxisScaling getScaling() { return (AxisScaling)getChartAxisPr().getProperty(DmlChartAxisAttrs.SCALING); }

    /// <summary>
    /// Gets or sets the interval, at which tick marks are drawn.
    /// </summary>
    /// <remarks>
    /// <para>The property has effect for text category and series axes. It is not supported by MS Office 2016
    /// new charts.</para>
    /// <para>Valid range of a value is greater than or equal to 1.</para>
    /// </remarks>
    /// <dev>
    /// The ISO standard states that this element specifies how many tick marks shall be skipped before the next
    /// one shall be drawn.
    /// In MS Office, this element specifies the interval at which tick marks are drawn.
    /// </dev>
    public int getTickMarkSpacing() { return (/*int*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.TICK_MARK_SPACING); }
    public void setTickMarkSpacing(int value)
    {
        ArgumentUtil.checkPositive(value, "value");
        getChartAxisPr().setProperty(DmlChartAxisAttrs.TICK_MARK_SPACING, value);
    }

    /// <summary>
    /// Gets or sets a flag indicating whether this axis is hidden or not.
    /// </summary>
    /// <remarks>
    /// Default value is <c>false</c>.
    /// </remarks>
    /// <dev>
    /// Corresponds to the 21.2.2.40 delete element [ISO/IEC 29500] and to the hidden attribute of the 2.24.3.3
    /// CT_Axis complex type [MS-ODRAWXML].
    /// </dev>
    public boolean getHidden() { return (/*boolean*/Boolean)getChartAxisPr().getProperty(DmlChartAxisAttrs.HIDDEN); }
    public void setHidden(boolean value)
    {
        boolean isChanged = (getHidden() != value);
        setHiddenInternal(value);
        if (isChanged && !value)
            mPlotArea.getParentChartFormat().getDmlChartSpace().applyChartStyle(this);
    }

    /// <summary>
    /// Gets or sets a flag indicating whether the axis has major gridlines.
    /// </summary>
    public boolean hasMajorGridlines() { return (getMajorGridlines() != null); }
    public void hasMajorGridlines(boolean value)
    {
        if (hasMajorGridlines() == value)
            return;

        setMajorGridlines(value ? getDefaultGridLines(GridlineType.MAJOR) : null);
    }

    /// <summary>
    /// Gets or sets a flag indicating whether the axis has minor gridlines.
    /// </summary>
    public boolean hasMinorGridlines() { return (getMinorGridlines() != null); }
    public void hasMinorGridlines(boolean value)
    {
        if (hasMinorGridlines() == value)
            return;

        setMinorGridlines(value ? getDefaultGridLines(GridlineType.MINOR) : null);
    }

    /// <summary>
    /// Provides access to the axis title properties.
    /// </summary>
    public ChartAxisTitle getTitle()
    {
        if (mTitle == null)
            mTitle = new ChartAxisTitle(this);

        return mTitle;
    }

    /// <summary>
    /// Provides access to the properties of the axis tick mark labels.
    /// </summary>
    public AxisTickLabels getTickLabels()
    {
        if (mTickLabels == null)
            mTickLabels = new AxisTickLabels(this);

        return mTickLabels;
    }

    /// <summary>
    /// Provides access to line formatting of the axis and fill of the tick labels.
    /// </summary>
    /// <remarks>
    /// Fill of chart tick marks can be changed only for pre Word 2016 charts. Word 2016 charts do not support this.
    /// </remarks>
    public ChartFormat getFormat()
    {
        if (mFormat == null)
            mFormat = new ChartFormat(this);

        return mFormat;
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Raw Axis Properties (values corresponds the appropriate values in the source XML).

    /// <summary>
    /// Returns a flag indicating whether this axis is a datetime category axis.
    /// </summary>
    boolean isDateCategoryAxis() { return isCategory() && (/*boolean*/Boolean)getChartAxisPr().getProperty(DmlChartAxisAttrs.IS_DATE_CATEGORY_AXIS); }

    /// <summary>
    /// Returns properties of this axis.
    /// </summary>
    DmlChartAxisPr getChartAxisPr() { return mChartAxisPr; }; void setChartAxisPr(DmlChartAxisPr value) { mChartAxisPr = value; };

    private DmlChartAxisPr mChartAxisPr;

    /// <summary>
    /// Specifies the text alignment for the tick labels on the axis.
    /// </summary>
    /// <remarks>
    /// The property has effect only for text category axes.
    /// </remarks>
    /*LabelAlignment*/int getLblAlgn() { return (/*LabelAlignment*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.LBL_ALGN); }
    void setLblAlgn(/*LabelAlignment*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.LBL_ALGN, value); }

    /// <summary>
    /// Specifies the identifier for the axis.
    /// </summary>
    int getAxId() { return (/*int*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.AX_ID); }
    void setAxId(int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.AX_ID, value); }

    /// <summary>
    /// Specifies the position of the axis on the chart.
    /// </summary>
    /*AxisPosition*/int getAxPos() { return (/*AxisPosition*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.AX_POS); }
    void setAxPos(/*AxisPosition*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.AX_POS, value); }

    /// <summary>
    /// Specifies the ID of axis that this axis crosses.
    /// </summary>
    int getCrossAx() { return (/*int*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.CROSS_AX); }
    void setCrossAx(int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.CROSS_AX, value); }

    /// <summary>
    /// Specifies whether the value axis crosses the category axis between categories.
    /// </summary>
    /// <remarks>
    /// The property has effect only for value axes.
    /// </remarks>
    /*CrossBetween*/int getCrossBetween() { return (/*CrossBetween*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.CROSS_BETWEEN); }
    void setCrossBetween(/*CrossBetween*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.CROSS_BETWEEN, value); }

    /// <summary>
    /// Specifies major gridlines.
    /// </summary>
    DmlChartGridlines getMajorGridlines()
    {
        Object majorGridlinesObj = getChartAxisPr().getProperty(DmlChartAxisAttrs.MAJOR_GRIDLINES);
        if (majorGridlinesObj != null)
            return (DmlChartGridlines)majorGridlinesObj;

        return null;
    }
    void setMajorGridlines(DmlChartGridlines value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.MAJOR_GRIDLINES, value); }

    /// <summary>
    /// Specifies the minor gridlines.
    /// </summary>
    DmlChartGridlines getMinorGridlines()
    {
        Object minorGridlinesObj = getChartAxisPr().getProperty(DmlChartAxisAttrs.MINOR_GRIDLINES);
        if (minorGridlinesObj != null)
            return (DmlChartGridlines)minorGridlinesObj;

        return null;
    }
    void setMinorGridlines(DmlChartGridlines value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.MINOR_GRIDLINES, value); }

    /// <summary>
    /// Specifies the labels shall be shown as flat text.
    /// </summary>
    /// <remarks>
    /// The property has effect only for text category axes.
    /// </remarks>
    boolean getNoMultiLvlLbl() { return (/*boolean*/Boolean)getChartAxisPr().getProperty(DmlChartAxisAttrs.NO_MULTI_LVL_LBL); }
    void setNoMultiLvlLbl(boolean value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.NO_MULTI_LVL_LBL, value); }

    /// <summary>
    /// Specifies the formatting for the parent chart element.
    /// </summary>
    DmlChartSpPr getSpPr() { return (DmlChartSpPr)getChartAxisPr().getProperty(DmlChartAxisAttrs.SP_PR); }

    /// <summary>
    /// Allows defining the distance between major ticks.
    /// </summary>
    DoubleOrAutomatic getMajorUnitPr() { return (DoubleOrAutomatic)getChartAxisPr().getProperty(DmlChartAxisAttrs.MAJOR_UNIT); }

    /// <summary>
    /// Allows defining the distance between minor ticks.
    /// </summary>
    DoubleOrAutomatic getMinorUnitPr() { return (DoubleOrAutomatic)getChartAxisPr().getProperty(DmlChartAxisAttrs.MINOR_UNIT); }

    /// <summary>
    /// Gets or sets a flag indicating whether automatic interval of drawing tick marks shall be used.
    /// </summary>
    /// <remarks>
    /// The property has effect for text category and series axes.
    /// </remarks>
    /// <dev>
    /// VBA has no TickMarkSpacingIsAuto public property.
    /// </dev>
    boolean getTickMarkSpacingIsAuto() { return getChartAxisPr().getDirectProperty(DmlChartAxisAttrs.TICK_MARK_SPACING) == null; }

    /// <summary>
    /// Specifies number formatting for the parent element.
    /// </summary>
    DmlChartNumFormat getNumFmt()
    {
        Object numFmtObj = getChartAxisPr().getProperty(DmlChartAxisAttrs.NUM_FMT);
        if (numFmtObj != null)
            return (DmlChartNumFormat)numFmtObj;

        return null;
    }
    void setNumFmt(DmlChartNumFormat value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.NUM_FMT, value); }

    /// <summary>
    /// Represents a format code that is used to render axis values.
    /// </summary>
    String getFormatCode()
    {
        if (mFormatCode == null)
            mFormatCode =(getNumFmt() != null) ? getNumFmt().getFormatCode() : "";

        return mFormatCode;
    }

    void setFormatCode(String value) { mFormatCode = value; }

    /// <summary>
    /// Specifies text formatting.
    /// </summary>
    DmlChartTxPr getTxPr() { return (DmlChartTxPr)getChartAxisPr().getProperty(DmlChartAxisAttrs.TX_PR); }

    /// <summary>
    /// Gets or sets a flag indicating whether tick labels are shown.
    /// Default value is <c>true</c>.
    /// </summary>
    /// <dev>
    /// There is no tick labels position property in charts of the schema
    /// http://schemas.microsoft.com/office/drawing/2014/chartex, but only a flag indicating if they are visible.
    /// </dev>
    boolean getAreTickLabelsVisible() { return getTickLabels().getPosition() != AxisTickLabelPosition.NONE; }
    void setAreTickLabelsVisible(boolean value) { getTickLabels().setPosition(value ? AxisTickLabelPosition.DEFAULT : AxisTickLabelPosition.NONE); }

    /// <summary>
    /// Gets or sets tick labels extensions.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    StringToObjDictionary<DmlExtension> getTickLabelExtensions() { return (StringToObjDictionary<DmlExtension>)getChartAxisPr().getProperty(DmlChartAxisAttrs.TICK_LBL_EXTENSIONS); }
    void setTickLabelExtensions(StringToObjDictionary<DmlExtension> value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.TICK_LBL_EXTENSIONS, value); }

    /// <summary>
    /// Gets or sets major tick mark extensions.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    StringToObjDictionary<DmlExtension> getMajorTickMarkExtensions() { return (StringToObjDictionary<DmlExtension>)getChartAxisPr().getProperty(DmlChartAxisAttrs.MAJOR_TICK_MARK_EXTENSIONS); }
    void setMajorTickMarkExtensions(StringToObjDictionary<DmlExtension> value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.MAJOR_TICK_MARK_EXTENSIONS, value); }

    /// <summary>
    /// Gets or sets minor tick mark extensions.
    /// </summary>
    /// <dev>
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </dev>
    StringToObjDictionary<DmlExtension> getMinorTickMarkExtensions() { return (StringToObjDictionary<DmlExtension>)getChartAxisPr().getProperty(DmlChartAxisAttrs.MINOR_TICK_MARK_EXTENSIONS); }
    void setMinorTickMarkExtensions(StringToObjDictionary<DmlExtension> value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.MINOR_TICK_MARK_EXTENSIONS, value); }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IDmlChartTitleHolder members

    @ReservedForInternalUse @Deprecated
    public int /*IDmlChartTitleHolder.*/getRelativeFontSize(int chartFontSize)
    {
        return chartFontSize; // An axis title uses font size defined on the chart without changes.
    }

    /// <summary>
    /// Gets or sets title.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DmlChartTitle /*IDmlChartTitleHolder.*/getDCTitle() { return getTitleInternal(); }
    @ReservedForInternalUse @Deprecated
    public void /*IDmlChartTitleHolder.*/setDCTitle(DmlChartTitle value) { setTitleInternal(value); }

    /// <summary>
    /// Returns the position of the title.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*TitlePosition*/int /*IDmlChartTitleHolder.*/getTitlePosition()
    {
        switch (getActualAxisPosition())
        {
            case AxisPosition.TOP:
                return TitlePosition.TOP;
            case AxisPosition.BOTTOM:
                return TitlePosition.BOTTOM;
            case AxisPosition.LEFT:
                return TitlePosition.LEFT;
            case AxisPosition.RIGHT:
                return TitlePosition.RIGHT;
            default:
                return TitlePosition.TOP;
        }
    }

    /// <summary>
    /// Returns the document containing the parent chart.
    /// </summary>
    public DocumentBase getDocument() { return getChart().getChartSpace().getDml().getDocument(); }

    /// <summary>
    /// Returns <c>true</c> if tile should not be displayed even if it exists.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IDmlChartTitleHolder.*/getTitleDeleted() { return !mHasTitle || (getTitleInternal() == null); }
    @ReservedForInternalUse @Deprecated
    public void /*IDmlChartTitleHolder.*/setTitleDeleted(boolean value)
    {
        if (mHasTitle == !value)
            return;

        mHasTitle = !value;
        if (mHasTitle)
            mPlotArea.getParentChartFormat().getDmlChartSpace().applyChartStyleToAxisTitle(this);
    }

    /// <summary>
    /// Gets the default title text.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public String /*IDmlChartTitleHolder.*/getDefaultTitleText() { return DefaultAxisTitles.getTitle(); }

    /// <summary>
    /// Gets the default font size in points.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IDmlChartTitleHolder.*/getDefaultFontSize() { return getDefaultFontSizeInternal(); }

    /// <summary>
    /// Gets the title font size in points that MS Word sets for created charts.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IDmlChartTitleHolder.*/getDefaultDisplayedFontSize() { return getDefaultFontSizeInternal(); }

    @ReservedForInternalUse @Deprecated
    public /*DmlChartStyleItem*/int /*IDmlChartTitleHolder.*/getStyleItem() { return DmlChartStyleItem.AXIS_TITLE; }

    /// <summary>
    /// Gets the parent chart space.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DmlChartSpace /*IDmlChartTitleHolder.*/getChartSpace() { return getChart().getChartSpace(); }

    /// <summary>
    /// Indicates whether the axis title is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IDmlChartTitleHolder.*/isVisible() { return !((IDmlChartTitleHolder)this).getTitleDeleted(); }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  INumberFormatProvider members

    @ReservedForInternalUse @Deprecated
    public DmlChartNumFormat /*INumberFormatProvider.*/getNumFmt_INumberFormatProvider() { return getNumFmt(); }
    @ReservedForInternalUse @Deprecated
    public void /*INumberFormatProvider.*/setNumFmt_INumberFormatProvider(DmlChartNumFormat value) { setNumFmt(value); }

    @ReservedForInternalUse @Deprecated
    public boolean /*INumberFormatProvider.*/isInherited() { return false; }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IChartFormatSource implementation

    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/materializeSpPr()
    {
        if (getChartAxisPr().isPropertySpecified(DmlChartAxisAttrs.SP_PR))
            return;

        DmlChartSpPr spPr = new DmlChartSpPr();
        getChartAxisPr().setProperty(DmlChartAxisAttrs.SP_PR, spPr);
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFillSupported() { return !getChart().getChartSpace().isChartEx(); }

    @ReservedForInternalUse @Deprecated
    public DmlFill /*IChartFormatSource.*/getFill() { return getSpPr().getFill(); }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setFill(DmlFill value) { getSpPr().setFill(value); }

    @ReservedForInternalUse @Deprecated
    public DmlOutline /*IChartFormatSource.*/getOutline() { return getSpPr().getOutline(); }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setOutline(DmlOutline value) { getSpPr().setOutline(value); }

    @ReservedForInternalUse @Deprecated
    public /*ChartShapeType*/int /*IChartFormatSource.*/getShapeType()
    {
        // Not supported by axis.
        return ChartShapeType.DEFAULT;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setShapeType(/*ChartShapeType*/int value)
    {
        // Not supported by axis.
    }

    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IChartFormatSource.*/getThemeProvider() { return getDocument().getThemeInternal(); }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFormatDefined() { return getChartAxisPr().isPropertySpecified(DmlChartAxisAttrs.SP_PR) && !getSpPr().isEmpty(); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets title.
    /// </summary>
    DmlChartTitle getTitleInternal() { return mTitleInternal; }
    void setTitleInternal(DmlChartTitle value) { mTitleInternal = value; }

    ChartAxis deepClone()
    {
        ChartAxis lhs = (ChartAxis)memberwiseClone();

        // FOSS

        // FOSS

        if (getChartAxisPr() != null)
        {
            lhs.setChartAxisPr(getChartAxisPr().deepClone());

            AxisDisplayUnit displayUnit =
                (AxisDisplayUnit)lhs.getChartAxisPr().getDirectProperty(DmlChartAxisAttrs.DISPLAY_UNIT);

            if (displayUnit != null)
                displayUnit.setAxis(lhs);
        }

        if (mTitleInternal != null)
        {
            lhs.mTitleInternal = mTitleInternal.deepClone();
            lhs.mTitleInternal.setTitleHolder(lhs);
        }

        // FOSS

        lhs.mCrossAxis = null;
        lhs.mNumberFormat = null;
        lhs.mTickLabels = null;
        lhs.mFormat = null;

        if (mExtensions != null)
            lhs.mExtensions = DmlExtensionListSource.cloneExtensions(mExtensions);

        return lhs;
    }

    /// <summary>
    /// Sets axis type.
    /// </summary>
    /// <dev>
    /// It is separate from the <see cref="Type"/> property, since the property is intended to be public.
    /// </dev>
    void setType(/*ChartAxisType*/int value)
    {
        mAxisType = value;
    }

    /// <summary>
    /// Sets display unit.
    /// </summary>
    void setDisplayUnit(AxisDisplayUnit value)
    {
        if (value != null)
            value.setAxis(this);
        getChartAxisPr().setProperty(DmlChartAxisAttrs.DISPLAY_UNIT, value);
    }

    /// <summary>
    /// Sets the parent chart of this axis.
    /// </summary>
    void setChart(DmlChart chart)
    {
        mChart = chart;

        for (ChartSeries series : chart.getSeries())
            getSeries().add(series);
    }

    /// <summary>
    /// Sets a flag indicating whether the axis is hidden without applying the chart style.
    /// </summary>
    void setHiddenInternal(boolean value)
    {
        getChartAxisPr().setProperty(DmlChartAxisAttrs.HIDDEN, value);
    }

    /// <summary>
    /// Gets default chart gridlines.
    /// </summary>
    private DmlChartGridlines getDefaultGridLines(/*GridlineType*/int type)
    {
        DmlOutline outline = new DmlOutline();
        outline.setWidthInEmus(9525.0);
        outline.setEndCap(EndCap.FLAT);
        outline.setCompoundLineType(ShapeLineStyle.SINGLE);
        outline.setStrokeAlignment(false);

        ArrayList<IDmlColorModifier> modifiers = new ArrayList<IDmlColorModifier>();
        DmlLuminanceModulation luminanceModulation = new DmlLuminanceModulation();
        luminanceModulation.setValue((type == GridlineType.MAJOR) ? 0.15d : 0.05d);
        msArrayList.add(modifiers, luminanceModulation);
        DmlLuminanceOffset luminanceOffset = new DmlLuminanceOffset();
        luminanceOffset.setValue((type == GridlineType.MAJOR) ? 0.85d : 0.95d);
        msArrayList.add(modifiers, luminanceOffset);

        DmlSchemeColor color = new DmlSchemeColor(ThemeColor.TEXT_1);
        color.setColorModifiers(modifiers);

        DmlSolidFill fill = new DmlSolidFill();
        fill.setColor(color);
        outline.setFill(fill);

        DmlChartGridlines gridlines = new DmlChartGridlines();
        gridlines.getSpPr().setOutline(outline);

        return gridlines;
    }

    /// <summary>
    /// Gets the default font size of axis title and tick labels.
    /// </summary>
    int getDefaultFontSizeInternal()
    {
        return getChart().getChartSpace().isChartEx() ? 9 : 10;
    }

    /// <summary>
    /// Gets or sets the plot area this axis belongs to.
    /// </summary>
    DmlChartPlotArea getPlotArea() { return mPlotArea; }
    void setPlotArea(DmlChartPlotArea value) { mPlotArea = value; }

    /// <summary>
    /// Gets or sets target rectangle where axis will be rendered.
    /// </summary>
    RectangleF getTargetRectangle() { return mPlotAreaTargetRect; }
    void setTargetRectangle(RectangleF value) { mPlotAreaTargetRect = value; }

    /// <summary>
    /// Returns <c>true</c> if the axis is vertical.
    /// </summary>
    boolean isVertical()
    {
        return ((getAxPos() == AxisPosition.LEFT) || (getAxPos() == AxisPosition.RIGHT) ||
           (mChart != null && mChart.isChartEx() && !isCategory() && mChart.getSeriesType() != ChartSeriesType.FUNNEL));
    }

    /// <summary>
    /// Indicates whether this axis is a category axis.
    /// </summary>
    boolean isCategory() { return (getType() == ChartAxisType.CATEGORY) ; }

    /// <summary>
    /// Returns axis that crosses the current axis.
    /// </summary>
    ChartAxis getCrossAxis()
    {
        if (mCrossAxis == null)
            mCrossAxis = mPlotArea.getAxis(getCrossAx());
        return mCrossAxis;
    }
    void setCrossAxis(ChartAxis value) { mCrossAxis = value; }

    /// <summary>
    /// Returns <c>true</c> if labels for this axis should be rendered before axis line,
    /// i.e. if it is vertical axis - labels on the left side of the axis line,
    /// if it is horizontal axis, labels are below the axis line.
    /// </summary>
    boolean getAreLabelsBefore()
    {
        boolean isAxisAtLowerLeftCorner = (getActualAxisPosition() == AxisPosition.LEFT) ||
            (getActualAxisPosition() == AxisPosition.BOTTOM);

        boolean areLabelsVeryAfter = getCrossAxis().getReverseOrder()
            ? (getTickLabels().getPosition() == AxisTickLabelPosition.LOW)
            : (getTickLabels().getPosition() == AxisTickLabelPosition.HIGH);

        boolean areLabelsVeryBefore = getCrossAxis().getReverseOrder()
            ? (getTickLabels().getPosition() == AxisTickLabelPosition.HIGH)
            : (getTickLabels().getPosition() == AxisTickLabelPosition.LOW);

        return ((isAxisAtLowerLeftCorner && !areLabelsVeryAfter) || areLabelsVeryBefore);
    }

    /// <summary>
    /// Returns actual axis position taking in account where axis crosses the perpendicular axis.
    /// </summary>
    /*AxisPosition*/int getActualAxisPosition()
    {
        // WORDSNET-28193 If ChartEx axis is not "Category" the position should be "left".
        if (mChart != null && mChart.isChartEx() && !isCategory())
            return AxisPosition.LEFT;

        // If axis crosses perpendicular axis at maximum value and the perpendicular axis' orientation is MinMax,
        // axis position is right or top whatever value of AxPos is set.
        // The same if axis crosses perpendicular axis at minimum value and the perpendicular axis' orientation is MaxMin.
        if ((getCrosses() == AxisCrosses.MAXIMUM) && (getCrossAxis().getScaling().getOrientation() == AxisOrientation.MIN_MAX) ||
            (getCrosses() == AxisCrosses.MINIMUM) && (getCrossAxis().getScaling().getOrientation() == AxisOrientation.MAX_MIN))
        {
            if (getAxPos() == AxisPosition.LEFT)
                return AxisPosition.RIGHT;
            if (getAxPos() == AxisPosition.BOTTOM)
                return AxisPosition.TOP;
        }

        // And vise versa if axis crosses perpendicular axis at min value and the perpendicular axis' orientation is MinMax,
        // axis position is left or bottom whatever value of AxPos is set.
        // The same if axis crosses perpendicular axis at max value and the perpendicular axis' orientation is MaxMin.
        if ((getCrosses() == AxisCrosses.MINIMUM) && (getCrossAxis().getScaling().getOrientation() == AxisOrientation.MIN_MAX) ||
            (getCrosses() == AxisCrosses.MAXIMUM) && (getCrossAxis().getScaling().getOrientation() == AxisOrientation.MAX_MIN))
        {
            if (getAxPos() == AxisPosition.RIGHT)
                return AxisPosition.LEFT;
            if (getAxPos() == AxisPosition.TOP)
                return AxisPosition.BOTTOM;
        }

        // WORDSNET-12797 In case of AutoZero position and MinMax orientation of cross axis,
        // right position of axis must be switched to left.
        if ((getCrosses() == AxisCrosses.AUTOMATIC) && (getAxPos() == AxisPosition.RIGHT) &&
            (getCrossAxis().getScaling().getOrientation() == AxisOrientation.MIN_MAX))
        {
            return AxisPosition.LEFT;
        }

        return getAxPos();
    }

    /// <summary>
    /// Returns <c>true</c> if plot area can overlap axis labels.
    /// </summary>
    boolean canPlotAreaOverlapLabels()
    {
        Object tickLblPos = getChartAxisPr().getDirectProperty(DmlChartAxisAttrs.TICK_LBL_POS);
        return (tickLblPos != null) && ((/*AxisTickLabelPosition*/Integer)tickLblPos == AxisTickLabelPosition.NEXT_TO_AXIS);
    }

    /// <summary>
    /// Gets or sets flag that indicates whether consider values of this axis as percent staked.
    /// </summary>
    boolean isPercentStacked() { return mIsPercentStacked; }
    void isPercentStacked(boolean value) { mIsPercentStacked = value; }

    /// <summary>
    /// Gets or sets flag that indicates whether this Axis is one of 3D coordinate plane Axis.
    /// Flag is used upon calculating auto max, min and step values.
    /// MS Word uses special rules for 3D charts.
    /// </summary>
    boolean getIs3D() { return mIs3D; }
    void setIs3D(boolean value) { mIs3D = value; }

    boolean getIs2DSurface() { return mIs2DSurface; }
    void setIs2DSurface(boolean value) { mIs2DSurface = value; }

    /// <summary>
    /// Returns the labels angle in degrees.
    /// </summary>
    double getLabelsRotation() { return getTxPr().getBodyPr().getRotation().getValueInDegrees(); }

    double getUnit() { return getDisplayUnit().getActualUnit(); }

    /// <summary>
    /// Returns <c>true</c> if axis is logarithmic scaled.
    /// </summary>
    boolean isLogScaled() { return getScaling().getType() == AxisScaleType.LOGARITHMIC; }

    boolean isStaked() { return mIsStacked; }
    void isStaked(boolean value) { mIsStacked = value; }

    /// <summary>
    /// Returns the underlying chart object for internal use.
    /// </summary>
    DmlChart getChart() { return mChart; }

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
    /// Returns the series that use this axis.
    /// </summary>
    ArrayList<ChartSeries> getSeries() { return mSeries; }

    /// <summary>
    /// Gets or sets the axis direction (X, Y, or Z). The property can also return the
    /// <see cref="AxisDirection.Unspecified"/> value.
    /// </summary>
    /*AxisDirection*/int getDirection() { return mDirection; }; void setDirection(/*AxisDirection*/int value) { mDirection = value; };

    private /*AxisDirection*/int mDirection;

    private StringToObjDictionary<DmlExtension> mExtensions;
    private DmlChart mChart;
    private ChartAxis mCrossAxis;
    private /*ChartAxisType*/int mAxisType;
    private DmlChartPlotArea mPlotArea;
    private ArrayList<ChartSeries> mSeries = new ArrayList<ChartSeries>();
    private RectangleF mPlotAreaTargetRect;
    private boolean mIsPercentStacked;
    private boolean mIsStacked;
    private boolean mIs3D;
    private boolean mIs2DSurface;

    /// <summary>
    /// Represents the format code that is used to render axis values.
    /// </summary>
    private String mFormatCode;

    private DmlChartTitle mTitleInternal;
    private ChartAxisTitle mTitle;
    private boolean mHasTitle = true;
    private ChartNumberFormat mNumberFormat;
    private AxisTickLabels mTickLabels;
    private ChartFormat mFormat;

    /// <summary>
    /// Specifies a type of chart gridlines: minor or major.
    /// </summary>
    private /*enum*/ final class GridlineType
    {
        private GridlineType(){}
        
        public static final int MINOR = 0;
        public static final int MAJOR = 1;

        public static final int length = 2;
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

