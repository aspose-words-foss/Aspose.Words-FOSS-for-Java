// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;
import com.aspose.MathUtil;
import com.aspose.ms.System.DateTime;
import com.aspose.DrColor;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msMath;
import com.aspose.ArgumentUtil;


/// <summary>
/// Represents data label on a chart point or trendline.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
/// <remarks>
/// On a series, the <see cref="ChartDataLabel"/> object is a member of the <see cref="ChartDataLabelCollection"/>.
/// The <see cref="ChartDataLabelCollection"/> contains a <see cref="ChartDataLabel"/> object for each point.
/// </remarks>
public class ChartDataLabel implements INumberFormatProvider, IChartItemTextProperties, IChartFormatSource, Cloneable
{
    ChartDataLabel(DmlChartDataLabelPr parentPr, ChartSeries series)
    {
        mLabelPr = new DmlChartDataLabelPr(parentPr, (series != null) ? series.getChart() : null);
        mSeries = series;
    }

    /// <summary>
    /// Clears format of this data label. The properties are set to the default values defined in the parent data
    /// label collection.
    /// </summary>
    public void clearFormat()
    {
        int index = getIndex();
        mLabelPr.clear();
        setIndex(index);
    }

    ChartDataLabel deepClone()
    {
        ChartDataLabel lhs = (ChartDataLabel)memberwiseClone();
        if (mLabelPr != null)
            lhs.mLabelPr = mLabelPr.deepClone();

        if (mTx != null)
            lhs.mTx = mTx.deepClone();

        return lhs;
    }

    void warn(/*WarningType*/int warningType, String message)
    {
        // For data labels of PivotFormat, chart is null. So added check.
        // This must be revised when PivotFormat will be moved to public API.
        if (getChart() != null)
            getChart().warn(warningType, message);
    }

    /// <summary>
    /// Sets the parent chart of this data label.
    /// </summary>
    void setSeries(ChartSeries series)
    {
        mSeries = series;
    }

    /// <summary>
    /// Generates <see cref="Tx"/> if current data label does not have explicitly set text.
    /// </summary>
    void updateText(ChartSeries series)
    {
        // Generate Tx if current data label does not have it.
        if (getTx() == null)
            throw new UnsupportedOperationException("FOSS");
        else
            updateFields(getTx(), series);
    }

    /// <summary>
    /// Update fields within data labels tx.
    /// </summary>
    private void updateFields(DmlChartTx tx, ChartSeries series)
    {
        if ((tx == null) || (tx.getRichText() == null) || (series == null))
            return;

        for (DmlParagraph paragraph : tx.getRichText().getParagraphs())
        {
            for (DmlParagraphTextElementBase element : paragraph.getElements())
            {
                DmlTextField field = ms.as(element, DmlTextField.class);
                if (field == null)
                    continue;

                switch (gStringSwitchMap.of(field.getType()))
                {
                    case/*ChartDataLabelFieldType.CELL_RANGE*/0:
                        field.setText(series.getDataLabelsRangeData().getValue(getSeriesElementIndex()).getStringValue());
                        break;
                    // WORDSNET-15672 Update [VALUE] field.
                    case/*ChartDataLabelFieldType.VALUE*/1:
                    // WORDSNET-23267 Update [Y VALUE] field.
                    case/*ChartDataLabelFieldType.Y_VALUE*/2:
                        field.setText(getValue(series));
                        break;
                    // WORDSNET-16609 Update [CATEGORYNAME], [SERIESNAME], [PERCENTAGE] fields.
                    case/*ChartDataLabelFieldType.CATEGORY_NAME*/3:
                    // WORDSNET-25342 Update [XVALUE] field. Rendering x-value of data point.
                    case/*ChartDataLabelFieldType.X_VALUE*/4:
                        field.setText(getCategoryName(series));
                        break;
                    case/*ChartDataLabelFieldType.SERIES_NAME*/5:
                        field.setText(series.getName());
                        break;
                    case/*ChartDataLabelFieldType.PERCENTAGE*/6:
                        field.setText(getPercentage(series));
                        break;
                    // WORDSNET-23851 Update [BUBBLE SIZE] field.
                    case
        /*ChartDataLabelFieldType.BUBBLE_SIZE*/7:
                        field.setText(getValue(series.getSize().getValue(getSeriesElementIndex())));
                        break;
                    default:
                        // Do nothing for default.
                        break;
                }
            }
        }
    }

    /// <summary>
    /// Gets actual layout.
    /// </summary>
    /// <remarks>
    /// If the extension layout and direct layout are specified, then the actual layout must be composed of the parameters
    /// of the both layouts. X and Y values must be taken from the direct layout, and other parameters -
    /// from the extension layout.
    /// </remarks>
    private DmlChartManualLayout getActualLayout()
    {
        DmlChartManualLayout actualLayout;
        DmlChartManualLayout extManual = (DmlChartManualLayout)mLabelPr.getExtensionProperty(DmlChartDataLabelAttrs.LAYOUT);
        DmlChartManualLayout directLayout = (DmlChartManualLayout)mLabelPr.getDirectProperty(DmlChartDataLabelAttrs.LAYOUT);

        // WORDSNET-16640 The X and Y values from the direct layout have a higher priority than the values
        // from the extension layout, but the width and the hight must be taken from the extension layout.
        if ((extManual != null) && (directLayout != null))
        {
            actualLayout = extManual.deepClone();
            actualLayout.setTop(directLayout.getTop());
            actualLayout.setLeft(directLayout.getLeft());
        }
        else
        {
            actualLayout = (DmlChartManualLayout)mLabelPr.getProperty(DmlChartDataLabelAttrs.LAYOUT);
        }

        return actualLayout;
    }

    /// <summary>
    /// Generates text string for the current data label.
    /// </summary>
    /// <remarks>
    /// Order matters.
    /// </remarks>
    private String generateDataLabelText(ChartSeries series)
    {
        StringBuilder labelBuilder = new StringBuilder();

        // WORDSNET-23624 Data labels range rendered first.
        if (getShowDataLabelsRange())
            addTextToDataLabel(labelBuilder, series.getDataLabelsRangeData().getValue(getSeriesElementIndex()).getStringValue());

        if (getShowSeriesName())
            addTextToDataLabel(labelBuilder, series.getName());

        boolean useCurrentSeries = getChart().isPieChart() || getChart().isScatter();

        // The first series is used to load category data.
        // WORDSNET-21748 For a scatter chart use the current series (not the first series) to get correct data.
        if (getShowCategoryName())
            addTextToDataLabel(labelBuilder, getCategoryName(useCurrentSeries ? series : getChart().getFirstSeries()));

        if (getShowValue())
            addTextToDataLabel(labelBuilder, getValue(series));

        // Percents are rendered only for pie charts.
        if (getShowPercentage() && getChart().isPieChart())
            addTextToDataLabel(labelBuilder, getPercentage(series));

        // Bubble size are rendered only for bubble charts.
        // WORDSNET-23851 The bubble size is rendered at the end of the label text.
        if (getShowBubbleSize() && getChart().isBubbleChart())
            addTextToDataLabel(labelBuilder, getValue(series.getSize().getValue(getSeriesElementIndex())));

        return labelBuilder.toString();
    }

    /// <summary>
    /// Appends the data label separator and the specified text to the specified label builder.
    /// </summary>
    /// <param name="labelBuilder">The specified <see cref="StringBuilder"/></param>
    /// <param name="addedText">The specified text</param>
    private void addTextToDataLabel(StringBuilder labelBuilder, String addedText)
    {
        appendSeparator(labelBuilder);
        msStringBuilder.append(labelBuilder, addedText);
    }

    /// <summary>
    /// Returns a string with the percentage value.
    /// </summary>
    /// <param name="series">The specified <see cref="ChartSeries"/></param>
    /// <returns>String with the percentage value</returns>
    private String getPercentage(ChartSeries series)
    {
        // MS Word shows whole numbers of percents, if a format code is not specified, so use "0%" format.
        String formatCode = (getNumFmt() != null) && !DmlChartFormatCodeValidator.isGeneralFormatCode(getNumFmt().getFormatCode())
            ? getNumFmt().getFormatCode()
            : "0%";

        double val = series.getY().getData().getPercentValue(getSeriesElementIndex(), formatCode);
        return FormatterPal.doubleToStr(val, formatCode);
    }

    /// <summary>
    /// Returns a string with the category value.
    /// </summary>
    /// <param name="series">The specified <see cref="ChartSeries"/></param>
    /// <returns>String with category value</returns>
    private String getCategoryName(ChartSeries series)
    {
        if (series == null)
            return "";

        DmlChartDimensionData xValues = series.getX();

        // WORDSNET-21690 The source x-values may be not specified. In this case return an empty string.
        if (xValues.isEmpty() || MathUtil.isZero(xValues.getValues().getValueCount()))
            return "";

        DmlChartValue xValue = xValues.getValues().get(getSeriesElementIndex());

        if (DmlChartValue.isNullOrNaN(xValue) || (DmlChartValue.NAN_STRING_VALUE.equals(xValue.getStringValue())))
            return "";

        DmlChartNumValue numVal = ms.as(xValue, DmlChartNumValue.class);

        // If NumFmt is specified and not linked to the source - use the format code from the data label NumFmt,
        // otherwise, if format code is specified for the value - use the it, else - use the series format code.
        String formatCode = isNumFmtUsed()
            ? getNumFmt().getFormatCode()
            : ((numVal == null) || (numVal.getFormatCode() == null)) ? series.getX().getData().getFormatCode() : numVal.getFormatCode();

        if (numVal == null)
            return xValue.getStringValue();

        if (xValue.isDate())
        {
            DateTime dateTime = getDate(xValue);
            throw new UnsupportedOperationException("FOSS");
        }

        return getStringValue(xValue, formatCode);

    }

    /// <summary>
    /// Sets the text color, if it is specified in the format code.
    /// </summary>
    /// <param name="seriesFormatCode">The source format code of the specified <see cref="ChartSeries"/> </param>
    /// <param name="dmlVal">The specified <see cref="DmlChartValue"/></param>
    private void setLabelTextColor(String seriesFormatCode, DmlChartValue dmlVal)
    {
        DmlChartNumValue numVal = ms.as(dmlVal, DmlChartNumValue.class);

        if (numVal == null)
            return;

        String sourceFormatCode = isNumFmtUsed()
            ? getNumFmt().getSourceFormatCode()
            : ((numVal.getSourceFormatCode() == null)) ? seriesFormatCode : numVal.getFormatCode();

        if (DmlChartFormatCodeValidator.isGeneralFormatCode(sourceFormatCode))
            return;

        DrColor color = DmlChartFormatCodeColors.getValueColor(dmlVal.getValue(), sourceFormatCode);

        if (color.isEmpty())
            return;

        // mLabelPr should not be changed. Clone TxPr.
        if (isTxPrSpecified())
            mTxPr = getTxPr().deepClone();

        getTxPr().getRunPr().setFill(new DmlSolidFill(DmlColor.createFromDrColor(color)));
    }

    /// <summary>
    /// Gets a string representation of <see cref="DmlChartValue"/> linked with the current data label and sets the color.
    /// </summary>
    /// <param name="series">The specified <see cref="ChartSeries"/></param>
    /// <returns>The string  representation of <see cref="DmlChartValue"/></returns>
    private String getValue(ChartSeries series)
    {
        DmlChartValue dmlVal = series.getY().getData().getOriginalValue(getSeriesElementIndex());
        String seriesFormatCode = series.getY().getValues().getFormatCode();
        setLabelTextColor(seriesFormatCode, dmlVal);

        return getValue(dmlVal);
    }

    /// <summary>
    /// Gets a string representation of the specified <see cref="DmlChartValue"/>.
    /// </summary>
    /// <param name="dmlVal">The specified <see cref="DmlChartValue"/></param>
    /// <returns>The string  representation of the specified <see cref="DmlChartValue"/></returns>
    private String getValue(DmlChartValue dmlVal)
    {
        // It seems when percents and values are shown in data label and format code is percents format code,
        // it is not used for values. See TestJira9191.
        if (DmlChartValue.isNullOrNaN(dmlVal))
            return (dmlVal.isNaN()) ? dmlVal.getStringValue() : "";

        String formatCode = isNumFmtUsed() ? getNumFmt().getFormatCode() : "";

        // WORDSNET-24288 The "ShowPercentage" is enough to ignore the specified format code.
        if (getShowPercentage())
            formatCode = null;

        return getStringValue(dmlVal, formatCode);
    }

    /// <summary>
    /// Returns a date, taking into account axis BaseTimeUnit.
    /// </summary>
    /// <param name="dmlVal">The specified <see cref="DmlChartValue"/></param>
    /// <returns>The date</returns>
    private DateTime getDate(DmlChartValue dmlVal)
    {
        DateTime baseDate = DmlChartUtil.getDateFromDouble(dmlVal.getValue());

        IDmlChart2D chart2D = ms.as(getChart(), IDmlChart2D.class);

        if (chart2D == null)
            return baseDate;

        throw new UnsupportedOperationException("FOSS");
    }

    private static String getStringValue(DmlChartValue dmlVal, String formatCode)
    {
        String result;

        if (DmlChartFormatCodeValidator.isGeneralFormatCode(formatCode))
        {
            result = (dmlVal == null) ? "" : dmlVal.getStringValue();
        }
        else
        {
            throw new UnsupportedOperationException("FOSS");
        }

        // WORDSNET-24130 MS Word trims the spaces for data labels.
        result = msString.trim(result);
        return result;
    }

    private static double getDoubleValue(DmlChartValue dmlVal)
    {
        if (dmlVal == null)
            return 0.0d;

        DmlChartNumValue numValue = ms.as(dmlVal, DmlChartNumValue.class);
        // WORDSNET-22431 The display unit should be taken into account if the value type is "DmlChartNumValue".
        return (numValue != null) ? numValue.getDisplayUnitValue() : dmlVal.getValue();
    }

    /// <summary>
    /// Appends data label separator to label builder if required.
    /// </summary>
    private void appendSeparator(StringBuilder labelBuilder)
    {
        if (labelBuilder.length() != 0)
        {
            msStringBuilder.append(labelBuilder, getSeparator());
            msStringBuilder.append(labelBuilder, " ");
        }
    }

    /// <summary>
    /// Converts the specified <see cref="ChartDataLabelLocationMode"/> value to <see cref="LayoutMode"/>.
    /// </summary>
    private static /*LayoutMode*/int locationModeToLayoutMode(/*ChartDataLabelLocationMode*/int value)
    {
        switch (value)
        {
            case ChartDataLabelLocationMode.ABSOLUTE:
                return LayoutMode.EDGE;
            case ChartDataLabelLocationMode.OFFSET:
            default:
                return LayoutMode.FACTOR;
        }
    }

    /// <summary>
    /// Converts the specified <see cref="LayoutMode"/> value to <see cref="ChartDataLabelLocationMode"/>.
    /// </summary>
    private static /*ChartDataLabelLocationMode*/int layoutModeToLocationMode(/*LayoutMode*/int value)
    {
        switch (value)
        {
            case LayoutMode.EDGE:
                return ChartDataLabelLocationMode.ABSOLUTE;
            case LayoutMode.FACTOR:
            default:
                return ChartDataLabelLocationMode.OFFSET;
        }
    }

    //>>>>>>>> #region  IChartFormatSource implementation

    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/materializeSpPr()
    {
        // This un-links data label SpPr from parent SpPr. Do it only on changing properties, not on reading, for
        // the behavior to not depend on a fact whether a customer has read ChartFormat properties or hasn't.

        if (mLabelPr.isPropertySpecified(DmlChartDataLabelAttrs.SP_PR))
            return;

        // Copy formatting of the parent collection.
        DmlChartSpPr parentSpPr = (DmlChartSpPr)mLabelPr.getProperty(DmlChartDataLabelAttrs.SP_PR);
        DmlChartSpPr spPr = (parentSpPr != null) ? parentSpPr.deepClone() : getSpPr();
        mLabelPr.setProperty(DmlChartDataLabelAttrs.SP_PR, spPr);
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFillSupported() { return true; }

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
        DmlChartShapeProperties shapePr =
            (DmlChartShapeProperties)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHAPE_PR);

        return ((shapePr != null) && (shapePr.getGeometry() != null))
            ? ChartShapeTypeUtil.presetGeometryToChartShapeType(shapePr.getGeometry().getPresetName())
            : ChartShapeType.DEFAULT;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setShapeType(/*ChartShapeType*/int value)
    {
        if (((IChartFormatSource)this).getShapeType() == value)
            return;

        if (((IChartFormatSource)mSeries.getDataLabels()).getShapeType() == value)
        {
            mLabelPr.removeExtensionProperty(DmlChartDataLabelAttrs.SHAPE_PR);
            return;
        }

        DmlChartShapeProperties shapePr =
            (DmlChartShapeProperties)mLabelPr.getExtensionProperty(DmlChartDataLabelAttrs.SHAPE_PR);
        if (shapePr == null)
        {
            shapePr = new DmlChartShapeProperties();
            mLabelPr.setExtensionProperty(DmlChartDataLabelAttrs.SHAPE_PR, shapePr);
        }

        if (value == ChartShapeType.DEFAULT)
        {
            shapePr.setGeometry(null);
        }
        else
        {
            if (shapePr.getGeometry() == null)
                shapePr.setGeometry(new DmlGeometry());

            shapePr.getGeometry().setPresetName(ChartShapeTypeUtil.chartShapeTypeToDmlPresetGeom(value));
        }
    }

    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IChartFormatSource.*/getThemeProvider() { return getChart().getDocument().getThemeInternal(); }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFormatDefined() { return mLabelPr.isPropertySpecified(DmlChartDataLabelAttrs.SP_PR) && !getSpPr().isEmpty(); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Specifies the index of the containing element.
    /// This index shall determine which of the parent's children collection this element applies to.
    /// Default value is 0.
    /// </summary>
    public int getIndex() { return (/*int*/Integer)mLabelPr.getProperty(DmlChartDataLabelAttrs.IDX); }
    void setIndex(int value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.IDX, value); }

    /// <summary>
    /// Allows to specify if category name is to be displayed for the data labels on a chart.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getShowCategoryName() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME); }
    public void setShowCategoryName(boolean value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME, value); }

    /// <summary>
    /// Allows to specify if bubble size is to be displayed for the data labels on a chart.
    /// Applies only to Bubble charts.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getShowBubbleSize() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE); }
    public void setShowBubbleSize(boolean value)
    {
        if (getChart().isBubbleChart())
            mLabelPr.setProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE, value);
        else
            getChart().warn(WarningType.MINOR_FORMATTING_LOSS,
                "ShowBubbleSize is not supported by this type of chart, value will not be set.");
    }

    /// <summary>
    /// Allows to specify if legend key is to be displayed for the data labels on a chart.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getShowLegendKey() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_LEGEND_KEY); }
    public void setShowLegendKey(boolean value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.SHOW_LEGEND_KEY, value); }

    /// <summary>
    /// Allows to specify if percentage value is to be displayed for the data labels on a chart.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getShowPercentage() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_PERCENT); }
    public void setShowPercentage(boolean value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.SHOW_PERCENT, value); }

    /// <summary>
    /// Returns or sets a Boolean to indicate the series name display behavior for the data labels on a chart.
    /// <c>true</c> to show the series name; <c>false</c> to hide. By default <c>false</c>.
    /// </summary>
    public boolean getShowSeriesName() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME); }
    public void setShowSeriesName(boolean value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME, value); }

    /// <summary>
    /// Allows to specify if values are to be displayed in the data labels.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getShowValue() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_VAL); }
    public void setShowValue(boolean value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.SHOW_VAL, value); }

    /// <summary>
    /// Allows to specify if data label leader lines need be shown.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Applies to Pie charts only.
    /// Leader lines create a visual connection between a data label and its corresponding data point.
    /// </remarks>
    public boolean getShowLeaderLines() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_LEADER_LINES); }
    public void setShowLeaderLines(boolean value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.SHOW_LEADER_LINES, value); }

    /// <summary>
    /// Allows to specify if values from data labels range to be displayed in the data labels.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getShowDataLabelsRange() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE); }
    public void setShowDataLabelsRange(boolean value)
    {
        mLabelPr.setExtensionProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE, value);
        // When the property is defined, UniqueId extension is required.
        mLabelPr.createUniqueIdExtensionIfNone();
    }

    /// <summary>
    /// Gets or sets string separator used for the data labels on a chart.
    /// The default is a comma, except for pie charts showing only category name and percentage, when a line break
    /// shall be used instead.
    /// </summary>
    public String getSeparator() { return mLabelPr.getSeparator(getChart().isPieChart()); }
    public void setSeparator(String value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.SEPARATOR, value); }

    /// <summary>
    /// Gets or sets the orientation of the label text.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ShapeTextOrientation.Horizontal"/>.
    /// </remarks>
    public /*ShapeTextOrientation*/int getOrientation() { return getBodyPr().getTextOrientation(); }
    public void setOrientation(/*ShapeTextOrientation*/int value) { getBodyPrForUpdate().setTextOrientation(value); }

    /// <summary>
    /// Gets or sets the rotation of the label in degrees.
    /// </summary>
    /// <remarks>
    /// <para>The range of acceptable values is from -180 to 180 inclusive. The default value is 0.</para>
    /// <para>If the <see cref="Orientation"/> value is <see cref="ShapeTextOrientation.Horizontal"/>, the
    /// label shape, if it exists, is rotated along with the label text. Otherwise, only the label text is rotated.
    /// </para>
    /// </remarks>
    public int getRotation() { return (int)msMath.round(getBodyPr().getRotation().getValueInDegrees()); }
    public void setRotation(int value)
    {
        ArgumentUtil.checkRangeInclusive(value, -180, 180, "value");
        getBodyPrForUpdate().setRotation(DmlAngle.fromDegrees(value));
    }

    /// <summary>
    /// Returns <c>true</c> if this data label has something to display.
    /// </summary>
    public boolean isVisible()
    {
        return getShowBubbleSize() || getShowCategoryName() || getShowLegendKey()|| getShowPercentage() || getShowSeriesName() || getShowValue() ||
            getShowDataLabelsRange();
    }

    /// <summary>
    /// Returns number format of the parent element.
    /// </summary>
    public ChartNumberFormat getNumberFormat()
    {
        if (mNumberFormat == null)
            mNumberFormat = new ChartNumberFormat(this, getChart());

        return mNumberFormat;
    }

    /// <summary>
    /// Gets/sets a flag indicating whether this label is hidden.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean isHidden()
    {
        if (hasNonDefaultFormatting())
        {
            // If properties specified use direct 'Delete' properly since it is not inheritable.
            Object val = mLabelPr.getDirectProperty(DmlChartDataLabelAttrs.DELETE);
            return (val != null) && (/*boolean*/Boolean)val;
        }
        else
        {
            return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.DELETE);
        }
    }

    public void isHidden(boolean value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.DELETE, value); }

    /// <summary>
    /// Provides access to the font formatting of this data label.
    /// </summary>
    public Font getFont()
    {
        if (mFont == null)
        {
            IRunAttrSource runAttrSource = new ChartItemDmlRunPropertiesSource(this, getChart().getChartSpace());
            mFont = Font.makeFont(runAttrSource, getChart().getDocument());
        }

        return mFont;
    }

    /// <summary>
    /// Provides access to fill and line formatting of the data label.
    /// </summary>
    public ChartFormat getFormat()
    {
        if (mFormat == null)
            mFormat = new ChartFormat(this);

        return mFormat;
    }

    /// <summary>
    /// Gets or sets the distance of the data label in points from the left edge of the chart or from the position
    /// specified by its <see cref="Position"/> property, depending on the value of the <see cref="LeftMode"/>
    /// property.
    /// </summary>
    /// <remarks>
    /// <para>The value of the property changes proportionally if the chart shape is resized.</para>
    /// <para>The property cannot be set in a Word 2016 chart.</para>
    /// </remarks>
    public double getLeft() { return getLayoutSafe().isXSet() ? getLayoutSafe().getLeft() * getShapeWidth() : 0.0; }
    public void setLeft(double value)
    {
        if (isChartEx())
            throw new IllegalStateException(DmlChartUtil.CHART_EX_UNSUPPORTED_PROPERTY);

        if (Double.isNaN(value))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        final double DEFAULT_WIDTH = 100.0;
        double shapeWidth = !MathUtil.isZero(getShapeWidth()) ? getShapeWidth() : DEFAULT_WIDTH;
        getLayoutSafe().setLeft(value / shapeWidth);
    }

    /// <summary>
    /// Gets or sets the interpretation mode of the <see cref="Left"/> property value: whether it sets the location
    /// of the data label from the left edge of the chart of from the position specified by its <see cref="Position"/>
    /// property.
    /// </summary>
    /// <remarks>
    /// The property cannot be set in a Word 2016 chart.
    /// </remarks>
    public /*ChartDataLabelLocationMode*/int getLeftMode() { return layoutModeToLocationMode(getLayoutSafe().getLeftMode()); }
    public void setLeftMode(/*ChartDataLabelLocationMode*/int value)
    {
        if (isChartEx())
            throw new IllegalStateException(DmlChartUtil.CHART_EX_UNSUPPORTED_PROPERTY);

        getLayoutSafe().setLeftMode(locationModeToLayoutMode(value));
    }

    /// <summary>
    /// Gets or sets the distance of the data label in points from the top edge of the chart or from the position
    /// specified by its <see cref="Position"/> property, depending on the value of the <see cref="TopMode"/>
    /// property.
    /// </summary>
    /// <remarks>
    /// <para>The value of the property changes proportionally if the chart shape is resized.</para>
    /// <para>The property cannot be set in a Word 2016 chart.</para>
    /// </remarks>
    public double getTop() { return getLayoutSafe().isYSet() ? getLayoutSafe().getTop() * getShapeHeight() : 0.0; }
    public void setTop(double value)
    {
        if (isChartEx())
            throw new IllegalStateException(DmlChartUtil.CHART_EX_UNSUPPORTED_PROPERTY);

        if (Double.isNaN(value))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        final double DEFAULT_HEIGHT = 100.0;
        double shapeHeight = !MathUtil.isZero(getShapeHeight()) ? getShapeHeight() : DEFAULT_HEIGHT;
        getLayoutSafe().setTop(value / shapeHeight);
    }

    /// <summary>
    /// Gets or sets the interpretation mode of the <see cref="Top"/> property value: whether it sets the location
    /// of the data label from the top edge of the chart of from the position specified by its <see cref="Position"/>
    /// property.
    /// </summary>
    /// <remarks>
    /// The property cannot be set in a Word 2016 chart.
    /// </remarks>
    public /*ChartDataLabelLocationMode*/int getTopMode() { return layoutModeToLocationMode(getLayoutSafe().getTopMode()); }
    public void setTopMode(/*ChartDataLabelLocationMode*/int value)
    {
        if (isChartEx())
            throw new IllegalStateException(DmlChartUtil.CHART_EX_UNSUPPORTED_PROPERTY);

        getLayoutSafe().setTopMode(locationModeToLayoutMode(value));
    }

    /// <summary>
    /// Gets or sets the position of the data label.
    /// </summary>
    /// <remarks>
    /// <para>The position can be set for data labels of the following chart series types:</para>
    /// <para>- <see cref="ChartSeriesType.Bar"/>, <see cref="ChartSeriesType.Column"/>,
    /// <see cref="ChartSeriesType.Histogram"/>, <see cref="ChartSeriesType.Pareto"/>,
    /// <see cref="ChartSeriesType.Waterfall"/>; allowed values: <see cref="ChartDataLabelPosition.Center"/>,
    /// <see cref="ChartDataLabelPosition.InsideBase"/>, <see cref="ChartDataLabelPosition.InsideEnd"/> and
    /// <see cref="ChartDataLabelPosition.OutsideEnd"/>;</para>
    /// <para>- <see cref="ChartSeriesType.BarStacked"/>, <see cref="ChartSeriesType.BarPercentStacked"/>,
    /// <see cref="ChartSeriesType.ColumnStacked"/>, <see cref="ChartSeriesType.ColumnPercentStacked"/>; allowed
    /// values: <see cref="ChartDataLabelPosition.Center"/>, <see cref="ChartDataLabelPosition.InsideBase"/>
    /// and <see cref="ChartDataLabelPosition.InsideEnd"/>;</para>
    /// <para>- <see cref="ChartSeriesType.Bubble"/>, <see cref="ChartSeriesType.Bubble3D"/>,
    /// <see cref="ChartSeriesType.Line"/>, <see cref="ChartSeriesType.LineStacked"/>,
    /// <see cref="ChartSeriesType.LinePercentStacked"/>, <see cref="ChartSeriesType.Scatter"/>,
    /// <see cref="ChartSeriesType.Stock"/>; allowed values: <see cref="ChartDataLabelPosition.Center"/>,
    /// <see cref="ChartDataLabelPosition.Left"/>, <see cref="ChartDataLabelPosition.Right"/>,
    /// <see cref="ChartDataLabelPosition.Above"/> and <see cref="ChartDataLabelPosition.Below"/>;</para>
    /// <para>- <see cref="ChartSeriesType.Pie"/>, <see cref="ChartSeriesType.Pie3D"/>,
    /// <see cref="ChartSeriesType.PieOfBar"/>, <see cref="ChartSeriesType.PieOfPie"/>; allowed values:
    /// <see cref="ChartDataLabelPosition.Center"/>, <see cref="ChartDataLabelPosition.InsideEnd"/>,
    /// <see cref="ChartDataLabelPosition.OutsideEnd"/> and <see cref="ChartDataLabelPosition.BestFit"/>;</para>
    /// <para>- <see cref="ChartSeriesType.BoxAndWhisker"/>; allowed values:
    /// <see cref="ChartDataLabelPosition.Left"/>, <see cref="ChartDataLabelPosition.Right"/>,
    /// <see cref="ChartDataLabelPosition.Above"/> and <see cref="ChartDataLabelPosition.Below"/>.</para>
    /// </remarks>
    public /*ChartDataLabelPosition*/int getPosition()
    {
        /*ChartDataLabelPosition*/int position =
            (/*ChartDataLabelPosition*/Integer)mLabelPr.getProperty(DmlChartDataLabelAttrs.D_LBL_POS);

        return (position != DmlChartDataLabelPr.DEFAULT_POSITION)
            ? position
            : DmlChartUtil.getDataLabelDefaultPosition(mSeries.getSeriesType());
    }
    public void setPosition(/*ChartDataLabelPosition*/int value)
    {
        if (value == (/*ChartDataLabelPosition*/Integer)mLabelPr.getProperty(DmlChartDataLabelAttrs.D_LBL_POS))
            return;

        if (!DmlChartUtil.isDataLabelPositionSupported(mSeries.getSeriesType(), value))
        {
            throw new IllegalStateException(
                "This series type doesn't support setting the position of a data label.");
        }

        mLabelPr.setProperty(DmlChartDataLabelAttrs.D_LBL_POS, value);
    }

    /// <summary>
    /// Specifies the position of the data label.
    /// <see cref="ChartDataLabelPosition"/>.
    /// </summary>
    /*ChartDataLabelPosition*/int getDLblPos() { return (/*ChartDataLabelPosition*/Integer)mLabelPr.getProperty(DmlChartDataLabelAttrs.D_LBL_POS); }

    /// <summary>
    /// Specifies how the chart element is placed on the chart.
    /// <see cref="DmlChartManualLayout"/>.
    /// </summary>
    DmlChartManualLayout getLayout()
    {
        if (mLayout == null)
            mLayout = getActualLayout();

        return mLayout;
    }
    void setLayout(DmlChartManualLayout value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.LAYOUT, value); }

    /// <summary>
    /// Indicates whether the data label has layout.
    /// </summary>
    boolean hasLayout() { return (getLayout() != null); }

    /// <summary>
    /// Indicates whether the data label position should be changed.
    /// </summary>
    boolean isPositionChanged() { return hasLayout() && (getLayout().isXSet() || getLayout().isYSet()); }

    /// <summary>
    /// Indicates whether size of label should be changed.
    /// </summary>
    boolean isSizeChanged() { return hasLayout() && (getLayout().isWidthSet() || getLayout().isHeightSet()); }

    /// <summary>
    /// Specifies the formatting for the parent chart element.
    /// </summary>
    DmlChartSpPr getSpPr()
    {
        DmlChartSpPr spPr = (DmlChartSpPr)mLabelPr.getProperty(DmlChartDataLabelAttrs.SP_PR);
        if (spPr != null)
            return spPr;

        if (mSpPr == null)
            mSpPr = new DmlChartSpPr();

        return mSpPr;
    }

    /// <summary>
    /// Specifies text to use on a chart, including rich text formatting.
    /// <see cref="DmlChartTx"/>.
    /// </summary>
    DmlChartTx getTx()
    {
        // WORDSNET-12553 Do not use label property to store generated tx used for rendering.
        // Instead store it in private field.
        if (mTx == null)
            mTx = (DmlChartTx)mLabelPr.getProperty(DmlChartDataLabelAttrs.TX);

        return mTx;
    }

    /// <summary>
    /// Checks the combination SpPr and Tx properties.
    /// </summary>
    /// <returns>
    /// "True" if SpPr is not set directly or is empty or Tx is not set directly or TX is string reference, otherwise - "false"
    /// </returns>
    boolean isParentRunPrUsed()
    {
        return !mLabelPr.isPropertySpecified(DmlChartDataLabelAttrs.SP_PR)
               || getSpPr().isEmpty() || !mLabelPr.isPropertySpecified(DmlChartDataLabelAttrs.TX) || (getTx().getStrRef() != null);
    }

    private boolean isTxPrSpecified()
    {
        return (mLabelPr.getProperty(DmlChartDataLabelAttrs.TX_PR) != null) && isParentRunPrUsed();
    }

    /// <summary>
    /// Specifies text formatting.
    /// <see cref="DmlChartTxPr"/>.
    /// </summary>
    DmlChartTxPr getTxPr()
    {
        // WORDSNET-16609 Strange behavior of MS Word. If SpPr is not set directly or is empty,
        // or Tx is not set directly, the TxPr must be set by default (must not be inherited).
        if (mTxPr == null)
        {
            mTxPr = isTxPrSpecified()
                ? (DmlChartTxPr)mLabelPr.getProperty(DmlChartDataLabelAttrs.TX_PR)
                : new DmlChartTxPr();
        }

        return mTxPr;
    }

    DmlChartSpPr getLeaderLines()
    {
        if (mLabelPr.getProperty(DmlChartDataLabelAttrs.LEADER_LINES) == null)
            mLabelPr.setProperty(DmlChartDataLabelAttrs.LEADER_LINES, new DmlChartSpPr());

        return (DmlChartSpPr)mLabelPr.getProperty(DmlChartDataLabelAttrs.LEADER_LINES);
    }

    DmlChartDataLabelPr getLabelPr() { return mLabelPr; }

    /// <summary>
    /// Specifies number formatting for the parent element.
    /// </summary>
    DmlChartNumFormat getNumFmt() { return (DmlChartNumFormat)mLabelPr.getProperty(DmlChartDataLabelAttrs.NUM_FMT); }
    void setNumFmt(DmlChartNumFormat value) { mLabelPr.setProperty(DmlChartDataLabelAttrs.NUM_FMT, value); }

    /// <summary>
    /// Returns a flag indicating whether the label has default formatting and it was saved to document markup by
    /// MS Word for its internal purposes only.
    /// </summary>
    /// <remarks>
    /// Represents a xForSave element of [MS-ODRAWXML] section 2.6.1.26 that specifies whether this data label was
    /// created solely for the purpose of saving. If it is <c>true</c>, it means that when the document is loaded,
    /// this data label can be removed from the parent collection as having default formatting.
    /// </remarks>
    boolean getXForSave()
    {
        Object xForSave = mLabelPr.getExtensionProperty(DmlChartDataLabelAttrs.X_FOR_SAVE);
        return ((xForSave instanceof /*boolean*/Boolean) && (/*boolean*/Boolean)xForSave);
    }

    /// <summary>
    /// Gets the parent chart of this label.
    /// </summary>
    DmlChart getChart() { return (mSeries != null) ? mSeries.getChart() : null; }

    /// <summary>
    /// The index of the series element that is linked with the current data label.
    /// </summary>
    /// <remarks>
    /// In normal cases, this series element index is equal to the data label index. But sometimes not (see WORDSNET-17879)
    /// </remarks>
    int getSeriesElementIndex() { return mSeriesElementIndex; }; void setSeriesElementIndex(int value) { mSeriesElementIndex = value; };

    private int mSeriesElementIndex;

    /// <summary>
    /// Returns a flag indicating whether some properties of this data label have different values than the corresponding
    /// properties of the parent collection.
    /// </summary>
    boolean hasNonDefaultFormatting() { return mLabelPr.hasNonDefaultFormatting(); }

    /// <summary>
    /// Indicates that <see cref="DmlChartNumFormat"/> is not null and is not linked to the source.
    /// </summary>
    private boolean isNumFmtUsed()
    {
        return (getNumFmt() != null) && !getNumFmt().getSourceLinked();
    }

    /// <summary>
    /// Gets a <see cref="DmlTextBodyProperties"/> instance to get text properties of the label.
    /// </summary>
    private DmlTextBodyProperties getBodyPr()
    {
        if ((getTx() != null) && (getTx().getRichText() != null))
            return getTx().getRichText().getProperties();

        return getTxPr().getBodyPr();
    }

    /// <summary>
    /// Gets a <see cref="DmlTextBodyProperties"/> instance to set text properties of the label.
    /// </summary>
    private DmlTextBodyProperties getBodyPrForUpdate()
    {
        // Tx cannot be inherited from collection: return as is.
        if ((getTx() != null) && (getTx().getRichText() != null))
            return getTx().getRichText().getProperties();

        // Materialize TxPr if it is taken from the parent collection.
        if (mLabelPr.getDirectProperty(DmlChartDataLabelAttrs.TX_PR) == null)
        {
            mTxPr = getTxPr().deepClone();
            mTxPr.ensureBodyPrExists();
            mLabelPr.setProperty(DmlChartDataLabelAttrs.TX_PR, mTxPr);
        }

        return getTxPr().getBodyPr();
    }

    /// <summary>
    /// Gets the value of the <see cref="Layout"/> property of the data label. Creates and sets the property if
    /// it is <b>null</b>.
    /// </summary>
    private DmlChartManualLayout getLayoutSafe()
    {
        if (getLayout() == null)
        {
            mLayout = new DmlChartManualLayout();
            mLabelPr.setProperty(DmlChartDataLabelAttrs.LAYOUT, mLayout);
        }

        return mLayout;
    }

    /// <summary>
    /// Gets the width of the parent chart in points.
    /// </summary>
    private double getShapeWidth() { return mSeries.getChart().getChartSpace().getDml().getWidth(); }

    /// <summary>
    /// Gets the height of the parent chart in points.
    /// </summary>
    private double getShapeHeight() { return mSeries.getChart().getChartSpace().getDml().getHeight(); }

    /// <summary>
    /// Gets a value indicating whether this data label belongs to a MS Word 2016 chart.
    /// </summary>
    private boolean isChartEx() { return mSeries.getChart().getChartSpace().isChartEx(); }

    //>>>>>>>> #region  INumberFormatProvider members

    @ReservedForInternalUse @Deprecated
    public DmlChartNumFormat /*INumberFormatProvider.*/getNumFmt_INumberFormatProvider() { return getNumFmt(); }
    @ReservedForInternalUse @Deprecated
    public void /*INumberFormatProvider.*/setNumFmt_INumberFormatProvider(DmlChartNumFormat value) { setNumFmt(value); }

    @ReservedForInternalUse @Deprecated
    public boolean /*INumberFormatProvider.*/isInherited() { return mLabelPr.getDirectProperty(DmlChartDataLabelAttrs.NUM_FMT) == null; }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IChartItemTextProperties members

    @ReservedForInternalUse @Deprecated
    public String /*IChartItemTextProperties.*/generateItemText()
    {
        // TODO: Implement a label index to value index translator so that this method can be used for Word 2016 charts.
        if (getChart().getChartType() == DmlChartType.CHART_EX_CHART)
            return null;

        setSeriesElementIndex(getIndex());
        return generateDataLabelText(mSeries);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IChartItemTextProperties.*/fetchSpecialDefaultRunPropertyValue(int key)
    {
        return null; // No special default values.
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IChartItemTextProperties.*/getRelativePropertyValue(int key, Object value)
    {
        return value;
    }

    @ReservedForInternalUse @Deprecated
    public DmlChartTx /*IChartItemTextProperties.*/getItemTx() { return (DmlChartTx)mLabelPr.getDirectProperty(DmlChartDataLabelAttrs.TX); }

    @ReservedForInternalUse @Deprecated
    public DmlChartTxPr /*IChartItemTextProperties.*/getItemTxPr() { return (DmlChartTxPr)mLabelPr.getDirectProperty(DmlChartDataLabelAttrs.TX_PR); }
    @ReservedForInternalUse @Deprecated
    public void /*IChartItemTextProperties.*/setItemTxPr(DmlChartTxPr value)
    {
        mTxPr = value;
        mLabelPr.setProperty(DmlChartDataLabelAttrs.TX_PR, value);
    }

    @ReservedForInternalUse @Deprecated
    public DmlChartSpPr /*IChartItemTextProperties.*/getItemSpPr() { return (DmlChartSpPr)mLabelPr.getDirectProperty(DmlChartDataLabelAttrs.SP_PR); }

    @ReservedForInternalUse @Deprecated
    public DmlChartTxPr /*IChartItemTextProperties.*/getCollectionTxPr() { return (mSeries != null) ? mSeries.getDataLabels().getTxPr() : null; }

    //<<<<<<<< #endregion 

    private DmlChartManualLayout mLayout;
    private DmlChartDataLabelPr mLabelPr;
    private DmlChartTx mTx;
    private DmlChartTxPr mTxPr;
    private DmlChartSpPr mSpPr;
    private ChartSeries mSeries;
    private ChartNumberFormat mNumberFormat;
    private Font mFont;
    private ChartFormat mFormat;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"CELLRANGE",
		"VALUE",
		"YVALUE",
		"CATEGORYNAME",
		"XVALUE",
		"SERIESNAME",
		"PERCENTAGE",
		"BUBBLESIZE"
	);

}

