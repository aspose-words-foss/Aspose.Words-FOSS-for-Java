// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlBubbleChart extends DmlCartesianPlaneChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.BUBBLE_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType() { return getBubble3D() ? ChartSeriesType.BUBBLE_3_D : ChartSeriesType.BUBBLE; }

    /// <dev>
    /// This property is not written to a document, but is taken from a series.
    /// It is used to assign to a corresponding property of a new chart series.
    /// </dev>
    boolean getBubble3D() { return mBubble3D; }; void setBubble3D(boolean value) { mBubble3D = value; };

    private boolean mBubble3D;

    int getBubbleScale() { return (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.BUBBLE_SCALE); }
    void setBubbleScale(int value) { getChartPr().setProperty(DmlChartAttrs.BUBBLE_SCALE, value); }

    boolean getShowNegBubbles() { return (/*boolean*/Boolean)getChartPr().getProperty(DmlChartAttrs.SHOW_NEG_BUBBLES); }
    void setShowNegBubbles(boolean value) { getChartPr().setProperty(DmlChartAttrs.SHOW_NEG_BUBBLES, value); }

    /*SizeRepresents*/int getSizeRepresents() { return (/*SizeRepresents*/Integer)getChartPr().getProperty(DmlChartAttrs.SIZE_REPRESENTS); }
    void setSizeRepresents(/*SizeRepresents*/int value) { getChartPr().setProperty(DmlChartAttrs.SIZE_REPRESENTS, value); }

    /// <summary>
    /// Loop through all bubbles sizes and selects the maximum one to use it as <see cref="MaxBubbleSize"/>.
    /// </summary>
    private void recalculateMaxBubbleSize()
    {
        mMaxBubbleSize = 0.0f;

        for (ChartSeries series : getSeries())
        {
            for (int i = 0; i < series.getValueCount(); i++)
            {
                DmlChartValue s = series.getSize().getValue(i);
                if (!DmlChartValue.isNullOrNaN(s))
                    mMaxBubbleSize = Math.max(mMaxBubbleSize, s.getFloatValue());
            }
        }
    }

    /// <summary>
    /// Returns the maximum bubble size of the current chart.
    /// </summary>
    float getMaxBubbleSize()
    {
        if (Float.isNaN(mMaxBubbleSize))
            recalculateMaxBubbleSize();

        return mMaxBubbleSize;
    }

    private float mMaxBubbleSize = Float.NaN;
}

