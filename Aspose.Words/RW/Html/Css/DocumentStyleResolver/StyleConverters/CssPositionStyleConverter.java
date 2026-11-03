// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/12/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.DoublePal;


/// <summary>
/// Helps to apply CSS position style to a model.
/// </summary>
/*static*/ class CssPositionStyleConverter
{
	/* Simulation of static class by using private constructor */
	private CssPositionStyleConverter()
	{}

    /// <summary>
    /// Applies CSS width style to a shape.
    /// </summary>
    static void toShape(CssDeclarationCollection declarations, Shape shape, CssBoxModel boxModel)
    {
        CssDeclaration positionDeclaration = declarations.get("position");
        if (positionDeclaration == null)
            return;

        if (positionDeclaration.getValue().equals(CssValue.Absolute))
        {
            // WORDSNET-9459 Image is placed incorrectly when imported from HTML.
            // To understand the following code please see how absolute positioned shapes are saved to HTML
            // in HtmlStyleWriter.AddFloatingStyle method.
            shape.setWrapType(WrapType.NONE);
            double zIndexValue = declarations.getNumber("z-index");
            if (!MathUtil.isMinValue(zIndexValue))
            {
                // The "z-index" value must be clamped to int32 limits in order to avoid integer overflow that would change
                // the sign and thus the position of the image relative to text (behind or in front of text).
                // The values that are close to the limits are also not supported for the same reason.
                zIndexValue = MathUtil.fitToRange(zIndexValue, Integer.MIN_VALUE + 2, Integer.MAX_VALUE - 1);
                int zOrder = DoublePal.toInt(zIndexValue);

                // In CSS, the "z-index" of text is zero. Consequently, all objects with "z-index" values less than zero
                // are "behind text", and all objects with other "z-index" values are "in front of text".
                shape.setBehindText(zOrder < 0);

                // Since we have already correctly set the "BehindText" flag, we can now convert the "z-index" value
                // into a positive "ZOrder" value.
                if (zOrder < 0)
                {
                    zOrder += Integer.MAX_VALUE;
                }
                shape.setZOrder(zOrder);
            }

            // WORDSNET-10125 CSS 2.1 specification says that if the element has 'position: absolute', the containing block
            // is established by the nearest ancestor with a 'position' of 'absolute', 'relative' or 'fixed'.
            // If there is no such ancestor, the containing block is the initial containing block.
            // Here (for simplicity now) we act like the only ancestor that meets the criteria is the initial containing block.
            double left = declarations.getLength("left");
            double shapeLeft = !MathUtil.isMinValue(left) ? left : boxModel.getLeft().getValue();

            double marginLeft = declarations.getLength("margin-left");
            if (!MathUtil.isMinValue(marginLeft))
                shapeLeft += marginLeft;
            shape.setLeft(shapeLeft);

            double shapeTop = boxModel.getTop().getValue();
            double marginTop = declarations.getLength("margin-top");
            if (!MathUtil.isMinValue(marginTop))
                shapeTop += marginTop;
            shape.setTop(shapeTop);
        }
    }
}

