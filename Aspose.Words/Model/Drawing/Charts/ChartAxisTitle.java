// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/06/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;


/// <summary>
/// Provides access to the axis title properties.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with
/// Charts</a> documentation article.</para>
/// </summary>
public class ChartAxisTitle
{
    /// <summary>
    /// Customers cannot create instances of this class.
    /// </summary>
    ChartAxisTitle(IDmlChartTitleHolder chartTitleHolder)
    {
        mTitleHolder = chartTitleHolder;
    }

    /// <summary>
    /// Gets or sets the text of the axis title.
    /// If <c>null</c> or empty value is specified, auto generated title will be shown.
    /// </summary>
    /// <remarks>Use <see cref="Show"/> option if you need to show the title.</remarks>
    public String getText() { return getDmlTitle().getText(); }
    public void setText(String value) { getDmlTitle().setText(value); }

    /// <summary>
    /// Determines whether other chart elements shall be allowed to overlap the title.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getOverlay() { return getDmlTitle().getOverlay(); }
    public void setOverlay(boolean value) { getDmlTitle().setOverlay(value); }

    /// <summary>
    /// Determines whether the title shall be shown for the axis.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getShow() { return !mTitleHolder.getTitleDeleted(); }
    public void setShow(boolean value)
    {
        if (getShow() == value)
            return;

        // It is needed to create a title instance when showing the title, otherwise it will become invisible when
        // calling DmlTitle.get.
        if (value && (mTitleHolder.getDCTitle() == null))
            mTitleHolder.setDCTitle(new DmlChartTitle(mTitleHolder));

        // Sets default text format for the showing title.
        if (value)
            getDmlTitle().ensureInitialized();

        mTitleHolder.setTitleDeleted(!value);
    }

    /// <summary>
    /// Provides access to the font formatting of the axis title.
    /// </summary>
    public Font getFont() { return getDmlTitle().getFont(); }

    /// <summary>
    /// Provides access to fill and line formatting of the axis title.
    /// </summary>
    public ChartFormat getFormat() { return getDmlTitle().getFormat(); }

    /// <summary>
    /// Gets or sets the orientation of the axis title text.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ShapeTextOrientation.Horizontal"/>.
    /// </remarks>
    public /*ShapeTextOrientation*/int getOrientation() { return getDmlTitle().getOrientation(); }
    public void setOrientation(/*ShapeTextOrientation*/int value)
    {
        if (isChartEx())
            throw new IllegalStateException(DmlChartUtil.CHART_EX_UNSUPPORTED_PROPERTY);

        getDmlTitle().setOrientation(value);
    }

    /// <summary>
    /// Gets or sets the rotation of the axis title in degrees.
    /// </summary>
    /// <remarks>
    /// The range of acceptable values is from -180 to 180 inclusive.
    /// </remarks>
    public int getRotation() { return getDmlTitle().getRotation(); }
    public void setRotation(int value)
    {
        if (isChartEx())
            throw new IllegalStateException(DmlChartUtil.CHART_EX_UNSUPPORTED_PROPERTY);

        ArgumentUtil.checkRangeInclusive(value, -180, 180, "value");
        getDmlTitle().setRotation(value);
    }

    /// <summary>
    /// Gets or creates the internal <see cref="DmlChartTitle"/> instance to get/set property values.
    /// </summary>
    private DmlChartTitle getDmlTitle()
    {
        if (mTitleHolder.getDCTitle() == null)
        {
            mTitleHolder.setDCTitle(new DmlChartTitle(mTitleHolder));
            // The title is hidden by default.
            setShow(false);
        }

        return mTitleHolder.getDCTitle();
    }

    /// <summary>
    /// Indicates that this chart is an extension of the ISO/IEC 29000 format that is defined by
    /// the http://schemas.microsoft.com/office/drawing/2015/9/8/chartex schema of [MS-ODRAWXML].
    /// The extension is introduced in MS Word 2016.
    /// </summary>
    private boolean isChartEx() { return mTitleHolder.getChartSpace().isChartEx(); }

    private /*final*/ IDmlChartTitleHolder mTitleHolder;
}

