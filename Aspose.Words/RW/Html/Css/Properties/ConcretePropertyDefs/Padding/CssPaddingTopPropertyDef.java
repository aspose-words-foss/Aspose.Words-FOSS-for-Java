// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.words.ref.RefDouble;


/// <summary>
/// Implements 'padding-top' CSS property. 
/// </summary>
class CssPaddingTopPropertyDef extends CssPaddingIndividualPropertyDefBase

{
    CssPaddingTopPropertyDef()
    {
    	super("padding-top");
	
        // Empty constructor.
    }

    /*override*/ void toTable(CssPropertyValue propertyValue, Table table) throws Exception
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        CssValue cssValue = propertyValue.getFirstValue();
        double padding = 0.0;
        RefDouble referenceToPadding = new RefDouble(padding);
        boolean outRefCondition0 = convertValueToPadding(cssValue, /*out*/ referenceToPadding);
        padding = referenceToPadding.get();
        if (outRefCondition0)
            table.setTopPadding(padding);
    }

    /*override*/ void toCellFormat(CssPropertyValue propertyValue, CellFormat cellFormat)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        CssValue cssValue = propertyValue.getFirstValue();
        double padding = 0.0;
        RefDouble referenceToPadding = new RefDouble(padding);
        boolean outRefCondition1 = convertValueToPadding(cssValue, /*out*/ referenceToPadding);
        padding = referenceToPadding.get();
        if (outRefCondition1)
            cellFormat.setTopPadding(padding);
    }
}

