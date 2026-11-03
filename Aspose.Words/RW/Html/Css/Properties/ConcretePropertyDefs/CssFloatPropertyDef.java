// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Implements 'float' CSS property.
/// </summary>
class CssFloatPropertyDef extends CssIndividualSimplePropertyDef
{
    CssFloatPropertyDef()
    {
    	super(
            "float",
            false,
            CssValue.None,
            // left | right | none
            CssValueFilter.values(CssValue.Left, CssValue.Right, CssValue.None));
	
        // Empty constructor.
    }

    /*override*/ void toTable(CssPropertyValue propertyValue, Table table) throws Exception
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        CssValue cssValue = propertyValue.getFirstValue();
        if (cssValue.equals(CssValue.Right))
            table.setAlignment(TableAlignment.RIGHT);
        else if (cssValue.equals(CssValue.Left))
            table.setAlignment(TableAlignment.LEFT);

        // WORDSNET-8410 Tables that have non-default 'float' property value should be imported as floating tables.
        table.setTextWrapping(TextWrapping.AROUND);
    }

    /*override*/ void toShape(CssPropertyValue propertyValue, Shape shape)
    {
        if (shape.isHorizontalRule())
            return;

        if (propertyValue.equals(CssValue.Left))
        {
            shape.setWrapType(WrapType.SQUARE);
            shape.setShapeAttrInternal(ShapeAttr.HORIZONTAL_ALIGNMENT, HorizontalAlignment.LEFT);
            shape.setRelativeVerticalPosition(RelativeVerticalPosition.LINE);
            shape.setAllowOverlap(false);
        }
        else if (propertyValue.equals(CssValue.Right))
        {
            shape.setWrapType(WrapType.SQUARE);
            shape.setShapeAttrInternal(ShapeAttr.HORIZONTAL_ALIGNMENT, HorizontalAlignment.RIGHT);
            shape.setRelativeVerticalPosition(RelativeVerticalPosition.LINE);
            shape.setAllowOverlap(false);
        }
    }
}

