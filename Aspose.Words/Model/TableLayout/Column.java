// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;


/// <summary>
/// Represents a table column. Holds necessary width values that are required for layout calculations.
/// </summary>
class Column
{
    /// <summary>
    /// Ctor.
    /// </summary>
    Column()
    {
    }

    void initializeEffectiveWidth()
    {
        mEffectivePreferredWidth = mPreferredWidth;
        mEffectiveLayoutWidth = mLayoutWidth.deepClone();
    }

    PreferredWidth getPreferredWidth() { return mPreferredWidth; }
    void setPreferredWidth(PreferredWidth value) { mPreferredWidth = value; }

    LayoutWidth getLayoutWidth() { return mLayoutWidth; }
    void setLayoutWidth(LayoutWidth value) { mLayoutWidth = value; }

    /// <summary>
    /// Gets or sets the preferred width that is calculated based on preferred widths of the cells which compose the column.
    /// </summary>
    PreferredWidth getEffectivePreferredWidth() { return mEffectivePreferredWidth; }
    void setEffectivePreferredWidth(PreferredWidth value) { mEffectivePreferredWidth = value; }

    /// <summary>
    /// Gets or sets the layout (min and max) width of the column.
    /// </summary>
    LayoutWidth getEffectiveLayoutWidth() { return mEffectiveLayoutWidth; }
    void setEffectiveLayoutWidth(LayoutWidth value) { mEffectiveLayoutWidth = value; }

    /// <summary>
    /// Gets or sets the resulting width used for setting cell widths.
    /// </summary>
    double getCalculatedWidth() { return mCalculatedWidth; }
    void setCalculatedWidth(double value) { mCalculatedWidth = value; }

    /// <summary>
    /// Ensures that the column has a minimum layout width.
    /// </summary>
    void ensureColumnHasLayoutWidth()
    {
        getLayoutWidth().setMin(Math.max(getLayoutWidth().getMin(), ConvertUtilCore.MIN_SIZE_POINT));   // 1 px.
        getLayoutWidth().setMax(Math.max(getLayoutWidth().getMax(), ConvertUtilCore.MIN_SIZE_POINT));   // 1 px.
    }

    private PreferredWidth mPreferredWidth = PreferredWidth.AUTO;
    private LayoutWidth mLayoutWidth = new LayoutWidth();
    private PreferredWidth mEffectivePreferredWidth;
    private LayoutWidth mEffectiveLayoutWidth;
    private double mCalculatedWidth;
}

