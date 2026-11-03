// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.DictionaryExtension;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msMath;
import com.aspose.ArgumentUtil;
import com.aspose.IntList;


/// <summary>
/// Represents a collection of <see cref="ChartDataLabel"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
public class ChartDataLabelCollection implements Iterable<ChartDataLabel>, INumberFormatProvider, IChartFormatSource
{
    // Represents 5.7.2.49 dLbls (Data Labels) element
    // that specifies the settings for the data labels for an entire series or the entire chart.

    ChartDataLabelCollection(DmlChart chart, ChartSeries series)
    {
        mChart = chart;
        mSeries = series;
        mLabelPr = new DmlChartDataLabelPr(chart);
    }

    ChartDataLabelCollection deepClone()
    {
        ChartDataLabelCollection lhs = new ChartDataLabelCollection(mChart, mSeries);

        if (getLabelPr() != null)
            lhs.setLabelPr(getLabelPr().deepClone());

        for (ChartDataLabel label : getMaterializedDataLabels())
        {
            if (label.hasNonDefaultFormatting())
            {
                ChartDataLabel cloned = label.deepClone();
                lhs.addLabel(cloned);
            }
        }

        return lhs;
    }

    /// <summary>
    /// Sets the parent chart of this collection.
    /// </summary>
    void setChart(DmlChart chart, ChartSeries series)
    {
        mChart = chart;
        mSeries = series;

        for (ChartDataLabel label : getMaterializedDataLabels())
            label.setSeries(series);
    }

    /// <summary>
    /// Returns <see cref="ChartDataLabel"/> for the specified index.
    /// </summary>
    public ChartDataLabel get(int index) { return materializeItem(index); }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<ChartDataLabel> iterator()
    {
        return new Enumerator(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Clears format of all <see cref="ChartDataLabel"/> in this collection.
    /// </summary>
    public void clearFormat()
    {
        // Let's keep currently stored label objects.
        for (ChartDataLabel label : getMaterializedDataLabels())
            label.clearFormat();
    }

    void addLabel(ChartDataLabel label)
    {
        mDataLabels.add(label.getIndex(), label);
    }

    /// <summary>
    /// Inserts a data label with default formatting at the specified index. The indexes of the labels following
    /// the inserted one are incremented by 1.
    /// </summary>
    void insert(int index)
    {
        if (mDataLabels.getCount() == 0)
            return;

        SortedList<Integer, ChartDataLabel> dataLabels = new SortedList<Integer, ChartDataLabel>(mDataLabels.getCount());

        for (ChartDataLabel label : mDataLabels.getValues())
        {
            if (label.getIndex() >= index)
                label.setIndex(label.getIndex() + 1);

            dataLabels.add(label.getIndex(), label);
        }

        mDataLabels = dataLabels;
    }

    /// <summary>
    /// Removes the data label at the specified index. The indexes of the labels following the removed one are
    /// decremented by 1.
    /// </summary>
    void remove(int index)
    {
        if (mDataLabels.getCount() == 0)
            return;

        SortedList<Integer, ChartDataLabel> dataLabels = new SortedList<Integer, ChartDataLabel>(mDataLabels.getCount());

        for (ChartDataLabel label : mDataLabels.getValues())
        {
            if (label.getIndex() == index)
                continue;

            if (label.getIndex() > index)
                label.setIndex(label.getIndex() - 1);

            dataLabels.add(label.getIndex(), label);
        }

        mDataLabels = dataLabels;
    }

    /// <summary>
    /// Returns <c>true</c> if the label at the specified index has non-default formatting.
    /// </summary>
    boolean hasNonDefaultItemFormatting(int index)
    {
        ChartDataLabel label = DictionaryExtension.getValueOrNull(mDataLabels, index);
        return (label != null) && label.hasNonDefaultFormatting();
    }

    /// <summary>
    /// Gets data labels with necessary formatting to write in form required by MS Word.
    /// </summary>
    /// <remarks>
    /// When displaying a data label with non-default formatting, MS Word does not take inherited values of ShowXXX
    /// and DLblPos properties from a data label collection. So, these properties should be always written to data
    /// label markup.
    /// And when the property <see cref="ShowDataLabelsRange"/> is set, MS Word writes all data labels with ShowXXX
    /// and Tx properties included into output XML file even if the labels have default formatting only.
    /// </remarks>
    ArrayList<ChartDataLabel> getLabelsToWrite()
    {
        ArrayList<ChartDataLabel> labels = new ArrayList<ChartDataLabel>();

        boolean needAllLabelsExplicitly = getShowDataLabelsRange();

        // Default values of ShowXXX properties depend on document version, so, they may be different from AW default
        // values: need to write the properties. And MS Word does the same.
        getLabelPr().expandParentProperties(G_PROPERTIES_WITH_VERSION_DEPENDENT_DEFAULTS);

        if (needAllLabelsExplicitly)
        {
            // Mimic MS Word in generating output XML.

            for (ChartDataLabel label : this)
            {
                ChartDataLabel clonedLabel = label.deepClone();

                if (clonedLabel.getShowDataLabelsRange())
                {
                    // Make the ShowDataLabelsRange property to be explicitly defined.
                    clonedLabel.setShowDataLabelsRange(true);

                    if (mSeries.hasDataLabelsRangeData())
                        generateTx(clonedLabel);
                }

                expandNonInheritedParentProperties(clonedLabel.getLabelPr());

                if (!label.hasNonDefaultFormatting())
                    clonedLabel.getLabelPr().setExtensionProperty(DmlChartDataLabelAttrs.X_FOR_SAVE, true);

                msArrayList.add(labels, clonedLabel);
            }
        }
        else
        {
            for (ChartDataLabel label : getMaterializedDataLabels())
            {
                if (!label.hasNonDefaultFormatting())
                    continue;

                // Mimic MS Word in generating output XML.

                ChartDataLabel clonedLabel = label.deepClone();

                if (mSeries.hasDataLabelsRangeData())
                    generateTx(clonedLabel);

                expandNonInheritedParentProperties(clonedLabel.getLabelPr());

                msArrayList.add(labels, clonedLabel);
            }
        }

        return labels;
    }

    /// <summary>
    /// Expand data label properties that do not inherit their value from the parent collection.
    /// </summary>
    /// <param name="labelPr">Properties of the individual data label.</param>
    private void expandNonInheritedParentProperties(DmlChartDataLabelPr labelPr)
    {
        // These properties are always not inherited.
        labelPr.expandParentProperties(G_NON_INHERITED_PROPERTIES);

        DmlChartNumFormat numFmt = (DmlChartNumFormat)labelPr.getDirectProperty(DmlChartDataLabelAttrs.NUM_FMT);
        Object txPr = labelPr.getDirectProperty(DmlChartDataLabelAttrs.TX_PR);
        Object spPr = labelPr.getDirectProperty(DmlChartDataLabelAttrs.SP_PR);
        DmlChartShapeProperties shapePr =
            (DmlChartShapeProperties)labelPr.getExtensionProperty(DmlChartDataLabelAttrs.SHAPE_PR);

        // If any of these properties are defined: txPr, spPr, numFmt, they are not taken from the parent collection,
        // need to expand missing ones.
        // If ShapePr is defined but all the properties mentioned above are null, MS Word ignores it: need to
        // expand the properties.
        if ((numFmt != null) || (txPr != null) || (spPr != null) || (shapePr != null))
        {
            labelPr.expandParentProperties(new /*DmlChartDataLabelAttrs*/int[] { DmlChartDataLabelAttrs.NUM_FMT,
                DmlChartDataLabelAttrs.TX_PR, DmlChartDataLabelAttrs.SP_PR });
            labelPr.expandParentExtensionProperties(new /*DmlChartDataLabelAttrs*/int[] { DmlChartDataLabelAttrs.SHAPE_PR });

            if (numFmt != null)
            {
                boolean hasSpPrOrTxPr = (txPr != null) || (spPr != null);

                // If a data label has either spPr or txPr element, and value is not defined, global default
                // (source-linked) is used: no need to write this value.
                // If a label has neither spPr nor txPr, it inherits number format from the parent collection:
                // can skip writing at this case too.
                if ((hasSpPrOrTxPr && numFmt.getSourceLinked()) ||
                    (!hasSpPrOrTxPr && numFmt.equals(getNumFmt())))
                {
                    labelPr.removeProperty(DmlChartDataLabelAttrs.NUM_FMT);
                }
            }
        }
    }

    /// <summary>
    /// Generates default value for the <see cref="DmlChartDataLabelAttrs.Tx"/> property, if it is not defined yet,
    /// to display information specified by the ShowXXX properties. MS Word needs this property written when the
    /// <see cref="ShowDataLabelsRange"/> property is set to On.
    /// </summary>
    private void generateTx(ChartDataLabel label)
    {
        /*DmlChartValueType*/int pointType = mSeries.getX().getValueType();
        boolean isCategory = (pointType == DmlChartValueType.STRING) || (pointType == DmlChartValueType.MULTI_LVL_STRING);
        label.getLabelPr().generateTx(isCategory);
    }

    /// <summary>
    /// Calculates real values of <see cref="NumberFormat"/> and ShowXXX properties of child labels. Collapses
    /// values of the properties if they have the same values as this parent collection. Removes labels that have
    /// default formatting.
    /// </summary>
    /// <remarks>
    /// The method is executed after loading the chart from the document file.
    /// Real values of the <see cref="NumberFormat"/> and ShowXXX properties of labels depend on the other properties
    /// and document version. This method calculates them.
    /// And we need to normalize label objects after loading, otherwise setting properties of the entire collection
    /// will not have effect for such non-normalized labels.
    /// </remarks>
    void normalizeAfterLoad()
    {
        // When ShowDataLabelRange property is set, all attributes from gNonInheritedProperties are explicitly
        // written, we need collapse them.
        // Also XForSave attribute indicates that a label has default formatting and its properties were explicitly
        // filled for writing purposes only: need to clear format at this case.

        for (ChartDataLabel label : getMaterializedDataLabels())
        {
            // When xForSave flag is set, it means that a label has default formatting, and it was saved to document
            // markup by MS Word for internal purposes only.
            if (label.getXForSave())
            {
                label.clearFormat();
                continue;
            }

            fixPropertyInheritance(label.getLabelPr());

            collapsePropertiesExpandedForSaving(label);
        }
    }

    /// <summary>
    /// Returns <b>true</b> if the specified data label is contained in this collection.
    /// </summary>
    boolean contains(ChartDataLabel label)
    {
        return mDataLabels.containsValue(label);
    }

    /// <summary>
    /// If any of the following attributes are defined in a data label: <see cref="DmlChartDataLabelAttrs.TxPr"/>,
    /// <see cref="DmlChartDataLabelAttrs.SpPr"/>, <see cref="DmlChartDataLabelAttrs.NumFmt"/>, and some of them is
    /// missing, it is not taken from the parent data label collection, but the default value is used. This method
    /// sets the missing properties to the default values.
    /// </summary>
    private void fixPropertyInheritance(DmlChartDataLabelPr labelPr)
    {
        Object labelTxPr = labelPr.getDirectProperty(DmlChartDataLabelAttrs.TX_PR);
        Object labelSpPr = labelPr.getDirectProperty(DmlChartDataLabelAttrs.SP_PR);
        Object labelNumFmt = labelPr.getDirectProperty(DmlChartDataLabelAttrs.NUM_FMT);

        // Return if none of the properties are defined.
        if ((labelTxPr == null) && (labelSpPr == null) && (labelNumFmt == null))
        {
            // MS Word ignores ShapePr if no of these elements are defined.
            labelPr.removeExtensionProperty(DmlChartDataLabelAttrs.SHAPE_PR);
            return;
        }

        // Set the default values to the missing properties.

        if ((labelTxPr == null) && (getLabelPr().getDirectProperty(DmlChartDataLabelAttrs.TX_PR) != null))
            labelPr.setProperty(DmlChartDataLabelAttrs.TX_PR, new DmlChartTxPr());

        if ((labelSpPr == null) && (getLabelPr().getDirectProperty(DmlChartDataLabelAttrs.SP_PR) != null))
            labelPr.setProperty(DmlChartDataLabelAttrs.SP_PR, new DmlChartSpPr());

        if ((labelNumFmt == null) && (getLabelPr().getDirectProperty(DmlChartDataLabelAttrs.NUM_FMT) != null))
        {
            DmlChartNumFormat numberFormat = new DmlChartNumFormat();
            numberFormat.setSourceLinked(true);
            labelPr.setProperty(DmlChartDataLabelAttrs.NUM_FMT, numberFormat);
        }

        // The similar is with ShapePr, but in difference, it does not affect the other properties.
        if ((((IChartFormatSource)this).getShapeType() != ChartShapeType.DEFAULT) &&
            (labelPr.getExtensionProperty(DmlChartDataLabelAttrs.SHAPE_PR) == null))
        {
            labelPr.setExtensionProperty(DmlChartDataLabelAttrs.SHAPE_PR, new DmlChartShapeProperties());
        }
    }

    /// <summary>
    /// Collapses label properties that MS Word and AW expand for saving.
    /// </summary>
    private void collapsePropertiesExpandedForSaving(ChartDataLabel label)
    {
        if (!label.hasNonDefaultFormatting())
            return;

        // When the ShowDataLabelsRange property is set in a label or in a collection, MS Word writes
        // label(s) with ShowXXX and Tx properties included in output XML file even if the properties
        // have default values only.
        boolean needCollapse = getShowDataLabelsRange() || label.getShowDataLabelsRange();
        if (!needCollapse)
            return;

        label.getLabelPr().collapseParentProperties(G_NON_INHERITED_PROPERTIES);
        if (label.getShowDataLabelsRange() == getShowDataLabelsRange())
            label.getLabelPr().removeExtensionProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE);
    }

    /// <summary>
    /// If the collection does not contain a data label at the specified index yet, this method creates it and puts
    /// into the collection.
    /// </summary>
    private ChartDataLabel materializeItem(int index)
    {
        // The index is not compared with Count now since we cannot calculate Count exactly for some chart types
        // of MS Word 2016.
        if ((index < 0) ||
            ((mSeries != null) && !mSeries.hasDataLabels()))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        ChartDataLabel label = DictionaryExtension.getValueOrNull(mDataLabels, index);
        if (label == null)
        {
            label = new ChartDataLabel(mLabelPr, mSeries);
            label.setIndex(index);

            addLabel(label);
        }

        return label;
    }

    /// <summary>
    /// Counts labels that have non-default formatting starting with the specified label index.
    /// </summary>
    private int countLabelsFrom(int labelIndex)
    {
        int count = 0;

        for (int i = mDataLabels.getCount() - 1; i >= 0; i--)
        {
            if (mDataLabels.getKeys().get(i) < labelIndex)
                break;

            if (mDataLabels.getValues().get(i).hasNonDefaultFormatting())
                count++;
        }

        return count;
    }

    /// <summary>
    /// Sets the data label defaults property. If data labels are not visible yet, they are displayed.
    /// </summary>
    private void setProperty(/*DmlChartDataLabelAttrs*/int attr, Object value)
    {
        mLabelPr.setProperty(attr, value);

        if (mSeries != null)
            mSeries.setHasDataLabels(true);
    }

    //>>>>>>>> #region  IChartFormatSource implementation

    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/materializeSpPr()
    {
        // Do nothing.
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

        if (value == ChartShapeType.DEFAULT)
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

        if (shapePr.getGeometry() == null)
            shapePr.setGeometry(new DmlGeometry());

        shapePr.getGeometry().setPresetName(ChartShapeTypeUtil.chartShapeTypeToDmlPresetGeom(value));
    }

    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IChartFormatSource.*/getThemeProvider() { return mChart.getDocument().getThemeInternal(); }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFormatDefined() { return mLabelPr.isPropertySpecified(DmlChartDataLabelAttrs.SP_PR) && !getSpPr().isEmpty(); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Returns the number of <see cref="ChartDataLabel"/> in this collection.
    /// </summary>
    public int getCount()
    {
        ChartSeriesHelper helper = new ChartSeriesHelper(mSeries);
        ChartSeriesHelper.ItemCountInfo countInfo = helper.getDataLabelCount();

        return
            countInfo.getCount() +
            // Count labels with indexes larger than the last label index possible to display if such labels
            // have non-default formatting.
            countLabelsFrom(countInfo.getLastUsedIndex() + 1);
    }

    /// <summary>
    /// Allows to specify whether category name is to be displayed for the data labels of the entire series.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.ShowCategoryName"/> property.
    /// </remarks>
    public boolean getShowCategoryName() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME); }
    public void setShowCategoryName(boolean value) { setProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME, value); }

    /// <summary>
    /// Allows to specify whether bubble size is to be displayed for the data labels of the entire series.
    /// Applies only to Bubble charts.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.ShowBubbleSize"/> property.
    /// </remarks>
    public boolean getShowBubbleSize() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE); }
    public void setShowBubbleSize(boolean value)
    {
        if (getChart().isBubbleChart())
            setProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE, value);
        else
            mChart.warn(WarningType.MINOR_FORMATTING_LOSS,
                "ShowBubbleSize is not supported by this type of chart, value will not be set.");
    }

    /// <summary>
    /// Allows to specify whether legend key is to be displayed for the data labels of the entire series.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.ShowLegendKey"/> property.
    /// </remarks>
    public boolean getShowLegendKey() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_LEGEND_KEY); }
    public void setShowLegendKey(boolean value) { setProperty(DmlChartDataLabelAttrs.SHOW_LEGEND_KEY, value); }

    /// <summary>
    /// Allows to specify whether percentage value is to be displayed for the data labels of the entire series.
    /// Default value is <c>false</c>. Applies only to Pie charts.
    /// </summary>
    /// <remarks>
    /// Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.ShowPercentage"/> property.
    /// </remarks>
    public boolean getShowPercentage() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_PERCENT); }
    public void setShowPercentage(boolean value) { setProperty(DmlChartDataLabelAttrs.SHOW_PERCENT, value); }

    /// <summary>
    /// Returns or sets a Boolean to indicate the series name display behavior for the data labels of the entire series.
    /// <c>true</c> to show the series name; <c>false</c> to hide. By default <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.ShowSeriesName"/> property.
    /// </remarks>
    public boolean getShowSeriesName() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME); }
    public void setShowSeriesName(boolean value) { setProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME, value); }

    /// <summary>
    /// Allows to specify whether values are to be displayed in the data labels of the entire series.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.ShowValue"/> property.
    /// </remarks>
    public boolean getShowValue() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_VAL); }
    public void setShowValue(boolean value) { setProperty(DmlChartDataLabelAttrs.SHOW_VAL, value); }

    /// <summary>
    /// Allows to specify whether data label leader lines need be shown for the data labels of the entire series.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>Applies to Pie charts only.
    /// Leader lines create a visual connection between a data label and its corresponding data point.</para>
    /// <para>Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.ShowLeaderLines"/> property.</para>
    /// </remarks>
    public boolean getShowLeaderLines() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_LEADER_LINES); }
    public void setShowLeaderLines(boolean value) { setProperty(DmlChartDataLabelAttrs.SHOW_LEADER_LINES, value); }

    /// <summary>
    /// Allows to specify whether values from data labels range to be displayed in the data labels of the entire series.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.ShowDataLabelsRange"/> property.
    /// </remarks>
    public boolean getShowDataLabelsRange() { return (/*boolean*/Boolean)mLabelPr.getProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE); }
    public void setShowDataLabelsRange(boolean value) { mLabelPr.setExtensionProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE, value); }

    /// <summary>
    /// Gets or sets string separator used for the data labels of the entire series.
    /// The default is a comma, except for pie charts showing only category name and percentage, when a line break
    /// shall be used instead.
    /// </summary>
    /// <remarks>
    /// Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.Separator"/> property.
    /// </remarks>
    public String getSeparator() { return mLabelPr.getSeparator(getChart().isPieChart()); }
    public void setSeparator(String value) { setProperty(DmlChartDataLabelAttrs.SEPARATOR, value); }

    /// <summary>
    /// Gets or sets the text orientation of the data labels of the entire series.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ShapeTextOrientation.Horizontal"/>.
    /// </remarks>
    public /*ShapeTextOrientation*/int getOrientation() { return getTxPr().getBodyPr().getTextOrientation(); }
    public void setOrientation(/*ShapeTextOrientation*/int value)
    {
        getTxPr().ensureBodyPrExists();
        getTxPr().getBodyPr().setTextOrientation(value);
    }

    /// <summary>
    /// Gets or sets the rotation of the data labels of the entire series in degrees.
    /// </summary>
    /// <remarks>
    /// <para>The range of acceptable values is from -180 to 180 inclusive. The default value is 0.</para>
    /// <para>If the <see cref="Orientation"/> value is <see cref="ShapeTextOrientation.Horizontal"/>, label shapes,
    /// if they exist, are rotated along with the label text. Otherwise, only the label text is rotated.</para>
    /// </remarks>
    public int getRotation() { return (int)msMath.round(getTxPr().getBodyPr().getRotation().getValueInDegrees()); }
    public void setRotation(int value)
    {
        ArgumentUtil.checkRangeInclusive(value, -180, 180, "value");
        getTxPr().ensureBodyPrExists();
        getTxPr().getBodyPr().setRotation(DmlAngle.fromDegrees(value));
    }

    /// <summary>
    /// Gets an <see cref="ChartNumberFormat"/> instance allowing to set number format for the data labels of the
    /// entire series.
    /// </summary>
    public ChartNumberFormat getNumberFormat()
    {
        if (mNumberFormat == null)
            mNumberFormat = new ChartNumberFormat(this, mChart);

        return mNumberFormat;
    }

    /// <summary>
    /// Provides access to the font formatting of the data labels of the entire series.
    /// </summary>
    /// <remarks>
    /// Value defined for this property can be overridden for an individual data label with using the
    /// <see cref="ChartDataLabel.Font"/> property.
    /// </remarks>
    public Font getFont()
    {
        if (mFont == null)
        {
            IRunAttrSource attrSource = new ChartCollectionDmlRunPropertiesSource(getTxPr(), getChart().getChartSpace());
            mFont = Font.makeFont(attrSource, getChart().getDocument());
        }

        return mFont;
    }

    /// <summary>
    /// Provides access to fill and line formatting of the data labels.
    /// </summary>
    public ChartFormat getFormat()
    {
        if (mFormat == null)
            mFormat = new ChartFormat(this);

        return mFormat;
    }

    /// <summary>
    /// Gets or sets the position of the data labels.
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
                "This series type doesn't support setting the position of data labels.");
        }

        mLabelPr.setProperty(DmlChartDataLabelAttrs.D_LBL_POS, value);
    }

    DmlChartDataLabelPr getLabelPr() { return mLabelPr; }
    void setLabelPr(DmlChartDataLabelPr value) { mLabelPr = value; }

    /// <summary>
    /// Gets the parent chart of this data label collection.
    /// </summary>
    DmlChart getChart() { return mChart; }

    /// <summary>
    /// Specifies number formatting for the parent element.
    /// </summary>
    DmlChartNumFormat getNumFmt() { return (DmlChartNumFormat)mLabelPr.getProperty(DmlChartDataLabelAttrs.NUM_FMT); }
    void setNumFmt(DmlChartNumFormat value) { setProperty(DmlChartDataLabelAttrs.NUM_FMT, value); }

    /// <summary>
    /// Returns <c>true</c> if this collection contains at least one explicitly-defined label.
    /// </summary>
    boolean hasItems() { return mDataLabels.getCount() > 0; }

    /// <summary>
    /// Returns <c>true</c> if any properties of this collection have non-default values.
    /// </summary>
    boolean hasNonDefaultFormatting()
    {
        return mLabelPr.hasNonDefaultFormatting() || (mSeries != null && mSeries.hasDataLabels()) ||
            (mSeries == null && mChart != null && mChart.getChartPr().isPropertySpecified(DmlChartAttrs.D_LBLS));
    }

    //>>>>>>>> #region  INumberFormatProvider members

    @ReservedForInternalUse @Deprecated
    public DmlChartNumFormat /*INumberFormatProvider.*/getNumFmt_INumberFormatProvider() { return getNumFmt(); }
    @ReservedForInternalUse @Deprecated
    public void /*INumberFormatProvider.*/setNumFmt_INumberFormatProvider(DmlChartNumFormat value) { setNumFmt(value); }

    @ReservedForInternalUse @Deprecated
    public boolean /*INumberFormatProvider.*/isInherited() { return false; }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets an enumerable over all materialized data labels.
    /// </summary>
    Iterable<ChartDataLabel> getMaterializedDataLabels() { return mDataLabels.getValues(); }

    /// <summary>
    /// Specifies text formatting.
    /// </summary>
    DmlChartTxPr getTxPr()
    {
        DmlChartTxPr txPr = (DmlChartTxPr)mLabelPr.getProperty(DmlChartDataLabelAttrs.TX_PR);
        if (txPr == null)
        {
            txPr = new DmlChartTxPr();
            mLabelPr.setProperty(DmlChartDataLabelAttrs.TX_PR, txPr);
        }

        return txPr;
    }

    /// <summary>
    /// Specifies the formatting for the data labels.
    /// </summary>
    DmlChartSpPr getSpPr()
    {
        if (mSpPr == null)
        {
            mSpPr = (DmlChartSpPr)mLabelPr.getProperty(DmlChartDataLabelAttrs.SP_PR);
            if (mSpPr == null)
            {
                mSpPr = new DmlChartSpPr();
                mLabelPr.setProperty(DmlChartDataLabelAttrs.SP_PR, mSpPr);
            }
        }

        return mSpPr;
    }

    /// <summary>
    /// Gets the parent series of the data labels.
    /// </summary>
    ChartSeries getSeries() { return mSeries; }

    private DmlChartDataLabelPr mLabelPr;
    private SortedList<Integer, ChartDataLabel> mDataLabels = new SortedList<Integer, ChartDataLabel>();

    private DmlChart mChart;
    private ChartSeries mSeries;
    private ChartNumberFormat mNumberFormat;
    private Font mFont;
    private ChartFormat mFormat;
    private DmlChartSpPr mSpPr;

    /// <summary>
    /// For these properties, when displaying a data label with non-default formatting, MS Word does not take
    /// inherited value from a data label collection. So, these properties should be always written to data label
    /// markup.
    /// </summary>
    private static /*final*/ /*DmlChartDataLabelAttrs*/int[] G_NON_INHERITED_PROPERTIES =
        { DmlChartDataLabelAttrs.SHOW_SER_NAME, DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE, DmlChartDataLabelAttrs.SHOW_CAT_NAME,
            DmlChartDataLabelAttrs.SHOW_LEGEND_KEY, DmlChartDataLabelAttrs.SHOW_PERCENT, DmlChartDataLabelAttrs.SHOW_VAL,
            DmlChartDataLabelAttrs.D_LBL_POS };

    private static /*final*/ /*DmlChartDataLabelAttrs*/int[] G_PROPERTIES_WITH_VERSION_DEPENDENT_DEFAULTS =
    { DmlChartDataLabelAttrs.SHOW_SER_NAME, DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE, DmlChartDataLabelAttrs.SHOW_CAT_NAME,
        DmlChartDataLabelAttrs.SHOW_LEGEND_KEY, DmlChartDataLabelAttrs.SHOW_PERCENT, DmlChartDataLabelAttrs.SHOW_VAL };

    /// <summary>
    /// Enumerator over data labels that are included in a value returned by the <see cref="Count"/> property.
    /// </summary>
    /// <remarks>
    /// The following are included in the enumeration: 1) labels that are displayed by default by MS Word for the
    /// current series data and series options even if they have the same formatting as this collection has;
    /// hidden/deleted labels are included too; 2) labels that are not displayed with the current data, but which have
    /// non-default formatting.
    /// </remarks>
    private final static class Enumerator implements Iterator<ChartDataLabel>
    {
        Enumerator(ChartDataLabelCollection collection)
        {
            ChartSeriesHelper helper = new ChartSeriesHelper(collection.mSeries);
            mCollection = collection;
            mLabelIndexes = helper.getLabelIndexesDeterminedByData();
            mLastUsedIndex = (mLabelIndexes != null)
                ? mLabelIndexes.get(mLabelIndexes.getCount() - 1)
                : helper.getDataLabelCount().getCount() - 1;
        }

        public boolean hasNext()
        {
            if (mCollection.mSeries == null)
                return false;

            if (!mCollection.mSeries.hasDataLabels())
                return false;

            mCurrentIndex++;

            if (mLabelIndexes != null)
            {
                // All labels with indexes in mLabelIndexes and labels with non-default formatting, which exists in
                // the collection, are enumerated.
                while ((mCurrentIndex <= mLastUsedIndex) &&
                       (mLabelIndexes.binarySearch(mCurrentIndex) < 0) &&
                       !mCollection.hasNonDefaultItemFormatting(mCurrentIndex))
                {
                    mCurrentIndex++;
                }
            }

            if (mCurrentIndex <= mLastUsedIndex)
                return true;

            SortedList<Integer, ChartDataLabel> dataLabels = mCollection.mDataLabels;

            // Check for labels after the last visible label. If the collection contains such invisible labels with
            // non-default formatting, include them into enumeration too.
            int maxExistingIndex = (dataLabels.getCount() > 0) ? dataLabels.getKeys().get(dataLabels.getCount() - 1) : -1;
            while (mCurrentIndex <= maxExistingIndex)
            {
                if (mCollection.hasNonDefaultItemFormatting(mCurrentIndex))
                    return true;

                mCurrentIndex++;
            }

            return false;
        }

        public void reset()
        {
            mCurrentIndex = -1;
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        public ChartDataLabel next() { return mCollection.get(mCurrentIndex); }

        private /*final*/ ChartDataLabelCollection mCollection;
        private /*final*/ IntList mLabelIndexes;
        private /*final*/ int mLastUsedIndex;
        private int mCurrentIndex = -1;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }
}

