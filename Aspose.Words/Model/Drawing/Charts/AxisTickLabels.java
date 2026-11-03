// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msMath;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Represents properties of axis tick mark labels.
/// </summary>
public class AxisTickLabels implements IChartItemTextProperties
{
    AxisTickLabels(ChartAxis parentAxis)
    {
        mAxis = parentAxis;
    }

    /// <summary>
    /// Gets or sets the position of the tick labels on the axis.
    /// </summary>
    /// <remarks>
    /// The property is not supported by MS Office 2016 new charts.
    /// </remarks>
    public /*AxisTickLabelPosition*/int getPosition() { return (/*AxisTickLabelPosition*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.TICK_LBL_POS); }
    public void setPosition(/*AxisTickLabelPosition*/int value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.TICK_LBL_POS, value); }

    /// <summary>
    /// Gets or sets the distance of the tick labels from the axis.
    /// </summary>
    /// <remarks>
    /// <para>The property represents a percentage of the default label offset.</para>
    /// <para>Valid range is from 0 to 1000 percent inclusive. The default value is 100%.</para>
    /// <para>The property has effect only for category axes. It is not supported by MS Office 2016 new charts.</para>
    /// </remarks>
    public int getOffset() { return (/*int*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.TICK_LABEL_OFFSET); }
    public void setOffset(int value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0, 1000, "value");
        getChartAxisPr().setProperty(DmlChartAxisAttrs.TICK_LABEL_OFFSET, value);
    }

    /// <summary>
    /// Gets or sets the interval at which the tick labels are drawn.
    /// </summary>
    /// <remarks>
    /// <para>The property has effect for text category and series axes. It is not supported by MS Office 2016
    /// new charts. Valid range of a value is greater than or equal to 1.</para>
    /// <para>Setting this property sets the <see cref="IsAutoSpacing"/> property to <c>false</c>.</para>
    /// </remarks>
    /// <dev>
    /// The ISO standard states that this element specifies how many tick labels to skip between label that is drawn.
    /// In MS Office, this element specifies the interval at which tick labels are drawn.
    /// </dev>
    public int getSpacing() { return (/*int*/Integer)getChartAxisPr().getProperty(DmlChartAxisAttrs.TICK_LABEL_SPACING); }
    public void setSpacing(int value)
    {
        ArgumentUtil.checkPositive(value, "value");
        getChartAxisPr().setProperty(DmlChartAxisAttrs.TICK_LABEL_SPACING, value);
        isAutoSpacing(false);
    }

    /// <summary>
    /// Gets or sets a flag indicating whether to use automatic interval for drawing the tick labels.
    /// </summary>
    /// <remarks>
    /// <para>The default value is <c>true</c>.</para>
    /// <para>The property has effect for text category and series axes. It is not supported by MS Office 2016
    /// new charts.</para>
    /// </remarks>
    public boolean isAutoSpacing() { return (/*boolean*/Boolean)getChartAxisPr().getProperty(DmlChartAxisAttrs.TICK_LABEL_SPACING_IS_AUTO); }
    public void isAutoSpacing(boolean value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.TICK_LABEL_SPACING_IS_AUTO, value); }

    /// <summary>
    /// Gets or sets text alignment of the axis tick labels.
    /// </summary>
    /// <remarks>
    /// <para>This property has effect only for multi-line labels.</para>
    /// <para>The default value is <see cref="ParagraphAlignment.Center"/>.</para>.
    /// </remarks>
    public /*ParagraphAlignment*/int getAlignment() { return getTxPr().getFirstParagraph().getProperties().getAlignment(); }
    public void setAlignment(/*ParagraphAlignment*/int value) { getTxPr().getFirstParagraph().getProperties().setAlignment(value); }

    /// <summary>
    /// Gets or sets the orientation of the tick label text.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ShapeTextOrientation.Horizontal"/>.</p>
    /// <p>Note that some <see cref="ShapeTextOrientation"/> values do not affect the orientation of tick label text
    /// in value axes.</p>
    /// </remarks>
    public /*ShapeTextOrientation*/int getOrientation() { return getBodyPr().getTextOrientation(); }
    public void setOrientation(/*ShapeTextOrientation*/int value) { getBodyPrForUpdate().setTextOrientation(value); }

    /// <summary>
    /// Gets or sets the rotation of the tick labels in degrees.
    /// </summary>
    /// <remarks>
    /// The range of acceptable values is from -180 to 180 inclusive. The default value is 0.
    /// </remarks>
    public int getRotation() { return (int)msMath.round(getBodyPr().getRotation().getValueInDegrees()); }
    public void setRotation(int value)
    {
        ArgumentUtil.checkRangeInclusive(value, -180, 180, "value");
        getBodyPrForUpdate().setRotation(DmlAngle.fromDegrees(value));
    }

    /// <summary>
    /// Provides access to font formatting of the tick labels.
    /// </summary>
    public Font getFont()
    {
        if (mFont == null)
            mFont = new Font(getFontRunPropertiesSource(), mAxis.getDocument());

        return mFont;
    }

    /// <summary>
    /// Gets a <see cref="ChartItemDmlRunPropertiesSource"/> instance that is the source of the tick labels
    /// font properties.
    /// </summary>
    private ChartItemDmlRunPropertiesSource getFontRunPropertiesSource()
    {
        if (mFontRunPropertiesSource == null)
            mFontRunPropertiesSource = new ChartItemDmlRunPropertiesSource(this, mAxis.getChart().getChartSpace());

        return mFontRunPropertiesSource;
    }

    /// <summary>
    /// Specifies text formatting.
    /// </summary>
    private DmlChartTxPr getTxPr() { return (DmlChartTxPr)getChartAxisPr().getProperty(DmlChartAxisAttrs.TX_PR); }
    private void setTxPr(DmlChartTxPr value) { getChartAxisPr().setProperty(DmlChartAxisAttrs.TX_PR, value); }

    /// <summary>
    /// Gets a <see cref="DmlTextBodyProperties"/> instance to get text properties of the tick labels.
    /// </summary>
    private DmlTextBodyProperties getBodyPr() { return getTxPr().getBodyPr(); }

    /// <summary>
    /// Gets a <see cref="DmlTextBodyProperties"/> instance to set text properties of the tick labels.
    /// </summary>
    private DmlTextBodyProperties getBodyPrForUpdate()
    {
        getTxPr().ensureBodyPrExists();
        return getTxPr().getBodyPr();
    }

    /// <summary>
    /// Specifies the formatting for the parent chart element.
    /// </summary>
    private DmlChartSpPr getSpPr() { return (DmlChartSpPr)getChartAxisPr().getProperty(DmlChartAxisAttrs.SP_PR); }

    /// <summary>
    /// Gets the axis properties collection.
    /// </summary>
    private DmlChartAxisPr getChartAxisPr() { return mAxis.getChartAxisPr(); }

    //>>>>>>>> #region  IChartItemTextProperties members

    @ReservedForInternalUse @Deprecated
    public String /*IChartItemTextProperties.*/generateItemText()
    {
        return null; // Not used for tick labels.
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IChartItemTextProperties.*/fetchSpecialDefaultRunPropertyValue(int key)
    {
        if (key == FontAttr.SIZE)
            return ConvertUtilCore.pointToHalfPoint(mAxis.getDefaultFontSizeInternal());

        return null;
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IChartItemTextProperties.*/getRelativePropertyValue(int key, Object value)
    {
        return value;
    }

    @ReservedForInternalUse @Deprecated
    public DmlChartTx /*IChartItemTextProperties.*/getItemTx() { return null; }

    @ReservedForInternalUse @Deprecated
    public DmlChartTxPr /*IChartItemTextProperties.*/getItemTxPr() { return getTxPr(); }
    @ReservedForInternalUse @Deprecated
    public void /*IChartItemTextProperties.*/setItemTxPr(DmlChartTxPr value) { setTxPr(value); }

    @ReservedForInternalUse @Deprecated
    public DmlChartSpPr /*IChartItemTextProperties.*/getItemSpPr() { return getSpPr(); }

    @ReservedForInternalUse @Deprecated
    public DmlChartTxPr /*IChartItemTextProperties.*/getCollectionTxPr() { return null; }

    //<<<<<<<< #endregion 

    private /*final*/ ChartAxis mAxis;
    private Font mFont;
    private ChartItemDmlRunPropertiesSource mFontRunPropertiesSource;
}

