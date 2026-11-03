// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a run attribute source for a chart collection whose items contain text.
/// </summary>
class ChartCollectionDmlRunPropertiesSource extends ChartDmlRunPropertiesSourceBase
{
    /// <summary>
    /// Ctor to create an instance of this class.
    /// </summary>
    ChartCollectionDmlRunPropertiesSource(DmlChartTxPr txPr, DmlChartSpace chartSpace)
    {
    	super(chartSpace);
	
        mTxPr = txPr;
    }

    /// <summary>
    /// Fetches the specified property from the parent paragraph or chart space. Run defaults are not returned.
    /// </summary>
    protected /*override*/ Object fetchInheritedRunPropertyValue(int key)
    {
        if (isChartEx())
        {
            // Get the value from the parent paragraph.
            // For pre-Word 2016 charts, GetDirectRunAttr uses default run properties of the parent paragraph
            // itself to get the value. So it's omitted for pre-Word 2016 charts.
            if (RunPrConverter.isPropertySpecified(mTxPr.getRunPr(), key))
                return getValueFromParentRunProperties(key, mTxPr.getRunPr());
        }
        else
        {
            // Get the value from the chart space. A chart space is not used in Word 2016 charts.
            if (RunPrConverter.isPropertySpecified(getChartSpaceTxPr().getRunPr(), key))
                return getValueFromParentRunProperties(key, getChartSpaceTxPr().getRunPr());
        }

        return null;
    }

    /// <summary>
    /// Gets an enumerable over all instances of <see cref="DmlRunProperties"/> within <see cref="mTxPr"/>.
    /// </summary>
    protected /*override*/ Iterable<DmlRunProperties> getRunPropertiesEnumerable()
    {
        // Text properties of Word 2016 chart collection are located in paragraph break properties: enumerate them too.
        return new DmlRunPropertiesEnumerable(mTxPr.getParagraphs(), isChartEx());
    }

    /// <summary>
    /// Gets an enumerable over all instances of <see cref="DmlParagraph"/> within <see cref="mTxPr"/>.
    /// </summary>
    protected /*override*/ Iterable<DmlParagraph> getParagraphEnumerable()
    {
        return mTxPr.getParagraphs();
    }

    /// <summary>
    /// Generates <see cref="GeneralRunProperties"/> if it is <b>null</b>.
    /// </summary>
    protected /*override*/ void ensureGeneralRunPropertiesExist(boolean toSetProperty)
    {
        mTxPr.ensureParagraphExists();
        Debug.assertTrue(getGeneralRunProperties() != null);
    }

    /// <summary>
    /// Gets DML run properties that is used to get direct property value.
    /// </summary>
    /*override*/ DmlRunProperties getGeneralRunProperties()
    {
        // In Word 2016 charts, text properties are taken from paragraph break properties, but from paragraph
        // default run properties in the older chart types.
        return isChartEx()
            ? mTxPr.getFirstParagraph().getEndParagraphRunProperties()
            : mTxPr.getRunPr();
    }

    private /*final*/ DmlChartTxPr mTxPr;
}

