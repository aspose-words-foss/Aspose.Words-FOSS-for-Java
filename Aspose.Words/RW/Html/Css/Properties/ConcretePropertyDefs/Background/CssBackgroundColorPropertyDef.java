// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.DrColor;


/// <summary>
/// Implements 'background-color' CSS property.
/// </summary>
class CssBackgroundColorPropertyDef extends CssIndividualSimplePropertyDef
{
    CssBackgroundColorPropertyDef()
    {
    	super(
            "background-color",
            false,
            CssValue.Transparent,
            // <color> | transparent
            CssValueFilter.anyOf(
                CssValueFilter.COLOR,
                CssValueFilter.value(CssValue.Transparent)));
	
        // Empty constructor.
    }

    /*override*/ void toTable(CssPropertyValue propertyValue, Table table)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        CssValue cssValue = propertyValue.getFirstValue();
        Shading shading = new Shading();
        toShading(cssValue, shading);
        if (shading.isVisible())
        {
            // Word applies shading to every row and every cell.
            for (Row row : (Iterable<Row>) table.getRows())
            {
                row.getTablePr().setShading(shading.deepClone());

                for (Cell cell : (Iterable<Cell>) row.getCells())
                {
                    // Preserve cell shading processed before.
                    if (!cell.getCellPr().contains(CellAttr.SHADING))
                        cell.getCellPr().setShading(shading.deepClone());
                }
            }
        }
    }

    /*override*/ void toCellFormat(CssPropertyValue propertyValue, CellFormat cellFormat)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        CssValue cssValue = propertyValue.getFirstValue();
        toShading(cssValue, cellFormat.getShading());
    }

    /*override*/ void toHorizontalRule(CssPropertyValue propertyValue, Shape horizontalRuleShape)
    {
        DrColor color = propertyValue.parseAsColor();
        if (color != null)
        {
            horizontalRuleShape.getFillCore().setColorInternal(color);
        }
    }

    /*override*/ void toDocument(CssPropertyValue propertyValue, Document document)
    {
        DrColor color = propertyValue.parseAsColor();
        if (color != null)
        {
            document.setPageColor(color.toNativeColor());
        }
    }

    private static void toShading(CssValue cssValue, Shading shading)
    {
        DrColor color = cssValue.parseAsColor();
        if (color != null)
        {
            // Plain background color corresponds to BackgroundPatternColor and TextureNone in the model.
            shading.setTexture(TextureIndex.TEXTURE_NONE);
            shading.setBackgroundPatternColorInternal(color);
        }
    }
}

