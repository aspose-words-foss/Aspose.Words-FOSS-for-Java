// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/12/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents an interface to work with text properties of a chart collection item that contains text.
/// </summary>
interface IChartItemTextProperties
{
    /// <summary>
    /// Generates text to put to <see cref="ItemTxPr"/>.
    /// </summary>
    public String generateItemText();

    /// <summary>
    /// Allows to fetch a default value of chart run properties whose default values differ from the default values
    /// of other chart elements.
    /// </summary>
    public Object fetchSpecialDefaultRunPropertyValue(int key);

    /// <summary>
    /// If font size is not specified for a chart title, MS Word uses 1.2 * [font size of chart space] value. This
    /// method is intended to perform such property value calculation.
    /// </summary>
    public Object getRelativePropertyValue(int key, Object value);

    /// <summary>
    /// Gets text element data of the chart collection item.
    /// </summary>
    /// <remarks>
    /// This is always <b>null</b> in Word 2016 charts.
    /// </remarks>
    public DmlChartTx getItemTx();

    /// <summary>
    /// Gets or sets text properties of the chart collection item.
    /// </summary>
    public DmlChartTxPr getItemTxPr(); public void setItemTxPr(DmlChartTxPr value);

    /// <summary>
    /// Gets shape properties of the chart collection item.
    /// </summary>
    public DmlChartSpPr getItemSpPr();

    /// <summary>
    /// Gets text properties of the parent chart collection.
    /// </summary>
    public DmlChartTxPr getCollectionTxPr();
}

