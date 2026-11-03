// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Helps to apply CSS border line style, width and color styles to a model border.
/// </summary>
/*static*/ class CssBorderStyleConverter
{
	/* Simulation of static class by using private constructor */
	private CssBorderStyleConverter()
	{}

    /// <summary>
    /// Applies CSS border line style, width and color styles to a model border.
    /// </summary>
    static void toModelBorder(CssDeclarationCollection declarations, /*BorderType*/int borderType, Border border)
    {
        Debug.assertTrue(border != null);

        CssBorder cssBorder = CssBorder.createBorder(declarations, borderType, false);
        if (!cssBorder.isUndefined())
        {
            cssBorder.toModelBorder(border);
        }
    }

    /// <summary>
    /// Applies CSS border line style, width and color styles to a model paragraph.
    /// </summary>
    static void toParagraphFormat(CssDeclarationCollection declarations, ParagraphFormat paragraphFormat) throws Exception
    {
        CssBorder leftBorder = CssBorder.createBorder(declarations, BorderType.LEFT, false);
        if (!leftBorder.isUndefined())
        {
            leftBorder.toModelBorder(paragraphFormat.getBorders().getLeft());
        }

        CssBorder rightBorder = CssBorder.createBorder(declarations, BorderType.RIGHT, false);
        if (!rightBorder.isUndefined())
        {
            rightBorder.toModelBorder(paragraphFormat.getBorders().getRight());
        }

        CssBorder topBorder = CssBorder.createBorder(declarations, BorderType.TOP, false);
        if (!topBorder.isUndefined())
        {
            topBorder.toModelBorder(paragraphFormat.getBorders().getTop());
        }

        CssBorder bottomBorder = CssBorder.createBorder(declarations, BorderType.BOTTOM, false);
        if (!bottomBorder.isUndefined())
        {
            bottomBorder.toModelBorder(paragraphFormat.getBorders().getBottom());
        }
    }

    /// <summary>
    /// Applies CSS border line style, width and color styles to a model shape.
    /// </summary>
    static void toShape(CssDeclarationCollection declarations, Shape shape)
    {
        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            toVmlShape(declarations, shape);
        }
        else
        {
            toDmlShape(declarations, shape);
        }
    }

    /// <summary>
    /// Applies CSS border styles to a VML shape.
    /// </summary>
    private static void toVmlShape(CssDeclarationCollection declarations, Shape shape)
    {
        Debug.assertTrue(shape != null);
        Debug.assertTrue(shape.getMarkupLanguage() == ShapeMarkupLanguage.VML);

        toVmlShapeBorder(declarations, shape, BorderType.TOP, ShapeAttr.BORDER_TOP);
        toVmlShapeBorder(declarations, shape, BorderType.RIGHT, ShapeAttr.BORDER_RIGHT);
        toVmlShapeBorder(declarations, shape, BorderType.BOTTOM, ShapeAttr.BORDER_BOTTOM);
        toVmlShapeBorder(declarations, shape, BorderType.LEFT, ShapeAttr.BORDER_LEFT);
    }

    /// <summary>
    /// Applies CSS border styles to a VML shape border.
    /// </summary>
    private static void toVmlShapeBorder(
        CssDeclarationCollection declarations,
        Shape shape,
        /*BorderType*/int borderType,
        int shapeBorderAttr)
    {
        CssBorder cssBorder = CssBorder.createBorder(declarations, borderType, false);
        if (!cssBorder.isUndefined())
        {
            Border modelBorder = new Border();
            cssBorder.toModelBorder(modelBorder);
            
            shape.setShapeAttrInternal(shapeBorderAttr, modelBorder);
        }
    }

    /// <summary>
    /// Applies CSS border styles to a DML shape.
    /// </summary>
    private static void toDmlShape(CssDeclarationCollection declarations, Shape shape)
    {
        Debug.assertTrue(shape != null);
        Debug.assertTrue(shape.getMarkupLanguage() == ShapeMarkupLanguage.DML);

        // Border properties have no effect on DML shapes, so we apply CSS borders to the shape's stroke.
        // CSS allows to define style of each of four borders individually, but stroke defines same line style for all sides
        // of a shape. We need to somehow collapse styles of four borders and apply them to single stroke. MS Word doesn't
        // apply CSS borders to DML images and we cannot use its behavior as a reference in this case.
        // We decided to implement here the same behavior that MS Word demonstrates when it applies CSS borders to run
        // properties. Same problem arises there, because four CSS borders need to be somehow converted to single run border.
        // In that scenario, MS Word imports run border properties from the corresponding top CSS border properties.
        CssBorder topBorder = CssBorder.createBorder(declarations, BorderType.TOP, false);
        if (!topBorder.isUndefined())
        {
            topBorder.toStroke(shape.getStroke());

            // Need to correct effect extent in order to make all borders visible.
            if (shape.getStroke().getOn())
            {
                ShapePr shapePr = shape.getShapePr();

                int currentExtentLeft = (/*int*/Integer)shapePr.fetchAttr(ShapeAttr.DML_EFFECT_EXTENT_LEFT);
                int currentExtentRight = (/*int*/Integer)shapePr.fetchAttr(ShapeAttr.DML_EFFECT_EXTENT_RIGHT);
                int currentExtentTop = (/*int*/Integer)shapePr.fetchAttr(ShapeAttr.DML_EFFECT_EXTENT_TOP);
                int currentExtentBottom = (/*int*/Integer)shapePr.fetchAttr(ShapeAttr.DML_EFFECT_EXTENT_BOTTOM);

                int strokeExtent = ConvertUtilCore.pointToEmu(shape.getStroke().getWeight());
                shapePr.setAttr(ShapeAttr.DML_EFFECT_EXTENT_LEFT, currentExtentLeft + strokeExtent);
                shapePr.setAttr(ShapeAttr.DML_EFFECT_EXTENT_RIGHT, currentExtentRight + strokeExtent);
                shapePr.setAttr(ShapeAttr.DML_EFFECT_EXTENT_TOP, currentExtentTop + strokeExtent);
                shapePr.setAttr(ShapeAttr.DML_EFFECT_EXTENT_BOTTOM, currentExtentBottom + strokeExtent);
            }
        }
    }
}

