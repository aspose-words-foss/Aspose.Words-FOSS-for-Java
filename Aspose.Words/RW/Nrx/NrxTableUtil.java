// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/07/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/*static*/ class NrxTableUtil
{
	/* Simulation of static class by using private constructor */
	private NrxTableUtil()
	{}

    /// <summary>
    /// Returns true if the chain of table styles referenced by this table properties defines the specified attribute.
    /// </summary>
    static boolean hasAttrInTableStyles(DocumentBase doc, TablePr tablePr, int key)
    {
        TableStyle style = (TableStyle)doc.getStyles().getByIstd(tablePr.getIstd(), false);
        while (style != null)
        {
            if (style.getRowPr().containsKey(key))
                return true;

            if (style.getTablePr().containsKey(key))
                return true;

            style = (TableStyle)style.getBaseStyle();
        }
        return false;
    }

    /// <summary>
    /// Inverts RowBands and ColumnBands because in the model they are inverted.
    /// </summary>
    static int tableStyleOptionsToNrx(/*TableStyleOptions*/int options)
    {
        return (int)TablePr.invertTableBands(options);
    }

    /// <summary>
    /// Inverts RowBands and ColumnBands because in the model they are inverted.
    /// </summary>
    static /*TableStyleOptions*/int nrxToTableStyleOptions(int value)
    {
        return TablePr.invertTableBands((/*TableStyleOptions*/int)value);
    }

    /// <summary>
    /// Saves an AW equivalent of the given tblpX/tblpY to table properties.
    /// </summary>
    /// <remarks>
    /// MS IMPL notes say that we have to decrement tblpY and tblpX by one upon loading docx/wml.
    /// MS Word also disregards 0 values for those attributes. Do not write 0 from xml to the model.
    /// 0 in the model corresponds to 1 in xml.
    /// 0 in the model is different from no value in the model for those attributes.
    /// </remarks>
    static void setPositionAttribute(TablePr tablePr, int key, int xmlValue)
    {
        Debug.assertTrue(key == TableAttr.FRAME_LEFT || key == TableAttr.FRAME_TOP);

        // Do not write 0 values from oxml to the model. MS Word disregards them and removes them on re-saving.
        if (xmlValue != 0)
            // Write the decremented value.
            tablePr.set(key, --xmlValue);
    }
}

