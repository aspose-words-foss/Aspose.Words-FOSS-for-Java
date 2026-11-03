// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/05/2021 by Artem Tsetkhalin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.words.ref.RefDouble;


class CssAwPaddingPropertyDef extends CssPaddingIndividualPropertyDefBase
{
    CssAwPaddingPropertyDef(String name)
    {
    	super(name);
	
        // Empty constructor.
    }

    /*override*/ void toCellFormat(CssPropertyValue propertyValue, CellFormat cellFormat)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        CssValue cssValue = propertyValue.getFirstValue();
        double padding = 0.0;
        RefDouble referenceToPadding = new RefDouble(padding);
        boolean outRefCondition0 = convertValueToPadding(cssValue, /*out*/ referenceToPadding);
        padding = referenceToPadding.get();
        if (outRefCondition0)
        {
            switch (gStringSwitchMap.of(getName()))
            {
                case/*HtmlConstants.ASPOSE_PADDING_TOP*/0:
                    cellFormat.setTopPadding(padding);
                    break;
                case/*HtmlConstants.ASPOSE_PADDING_RIGHT*/1:
                    cellFormat.setRightPadding(padding);
                    break;
                case/*HtmlConstants.ASPOSE_PADDING_BOTTOM*/2:
                    cellFormat.setBottomPadding(padding);
                    break;
                case/*HtmlConstants.ASPOSE_PADDING_LEFT*/3:
                    cellFormat.setLeftPadding(padding);
                    break;
                default:
                    Debug.assertTrue(false, "Unsupported padding type!");
                    break;
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"-aw-" + "padding-top",
		"-aw-" + "padding-right",
		"-aw-" + "padding-bottom",
		"-aw-" + "padding-left"
	);

}

