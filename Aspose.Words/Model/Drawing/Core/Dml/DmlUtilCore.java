// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.MathUtil;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ms.System.msMath;


/// <summary>
/// Helper class containing methods used for a DrawingML conversion.
/// </summary>
/*static*/ class DmlUtilCore
{
	/* Simulation of static class by using private constructor */
	private DmlUtilCore()
	{}

    static Object convertDmlNullEffect(Object value)
    {
        return (value instanceof DmlNullEffect ? null : value);
    }

    /// <summary>
    /// Converts <see cref="ShapeTextOrientation"/> to <see cref="LayoutFlow"/> value.
    /// </summary>
    static /*LayoutFlow*/int dmlToTextVerticalType(/*ShapeTextOrientation*/int textOrientation, boolean normalEastAsianFlow)
    {
        // According to specification attribute that specifies whether the text flow of the text contents of the shape 
        // ignores the text flow value specified by the vert attribute of the bodyPr element.
        // MS Word seams uses LayoutFlow.HorizontalIdeographic do the same.
        if (normalEastAsianFlow)
            return LayoutFlow.HORIZONTAL_IDEOGRAPHIC;

        switch (textOrientation)
        {
            case ShapeTextOrientation.HORIZONTAL:
                return LayoutFlow.HORIZONTAL;
            case ShapeTextOrientation.VERTICAL_ROTATED_FAR_EAST:
            case ShapeTextOrientation.DOWNWARD:
                return LayoutFlow.VERTICAL;
            case ShapeTextOrientation.VERTICAL_FAR_EAST:
            case ShapeTextOrientation.WORD_ART_VERTICAL:
            case ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT:
                return LayoutFlow.TOP_TO_BOTTOM_IDEOGRAPHIC;
            case ShapeTextOrientation.UPWARD:
                return LayoutFlow.BOTTOM_TO_TOP;
            default:
                return LayoutFlow.HORIZONTAL;
        }
    }

    /// <summary>
    /// Converts <see cref="LayoutFlow"/> to <see cref="ShapeTextOrientation"/> value.
    /// </summary>
    static /*ShapeTextOrientation*/int textVerticalTypeToDml(/*LayoutFlow*/int layoutFlow)
    {
        switch (layoutFlow)
        {
            case LayoutFlow.HORIZONTAL:
                return ShapeTextOrientation.HORIZONTAL;
            case LayoutFlow.VERTICAL:
            case LayoutFlow.TOP_TO_BOTTOM:
                return ShapeTextOrientation.DOWNWARD;
            case LayoutFlow.TOP_TO_BOTTOM_IDEOGRAPHIC:
                return ShapeTextOrientation.VERTICAL_FAR_EAST;
            case LayoutFlow.BOTTOM_TO_TOP:
                return ShapeTextOrientation.UPWARD;
            case LayoutFlow.HORIZONTAL_IDEOGRAPHIC:
            default:
                return ShapeTextOrientation.HORIZONTAL;
        }
    }

    /// <summary>
    /// Converts <see cref="DmlTextAnchoringType"/> to <see cref="TextBoxAnchor"/> value.
    /// </summary>
    static /*TextBoxAnchor*/int dmlToTextboxAnchor(/*DmlTextAnchoringType*/int dmlTextAnchoringType)
    {
        switch (dmlTextAnchoringType)
        {
            case DmlTextAnchoringType.BOTTOM:
                return TextBoxAnchor.BOTTOM;
            case DmlTextAnchoringType.TOP:
                return TextBoxAnchor.TOP;
            case DmlTextAnchoringType.CENTER:
                return TextBoxAnchor.MIDDLE;
            case DmlTextAnchoringType.DISTRIBUTED:
            case DmlTextAnchoringType.JUSTIFIED:
            default:
                return TextBoxAnchor.TOP;
        }
    }

    /// <summary>
    /// Converts <see cref="TextBoxAnchor"/> to <see cref="DmlTextAnchoringType"/> value.
    /// </summary>
    static /*DmlTextAnchoringType*/int textboxAnchorToDml(/*TextBoxAnchor*/int textBoxAnchor)
    {
        switch (textBoxAnchor)
        {
            case TextBoxAnchor.BOTTOM:
                return DmlTextAnchoringType.BOTTOM;
            case TextBoxAnchor.TOP:
                return DmlTextAnchoringType.TOP;
            case TextBoxAnchor.MIDDLE:
                return DmlTextAnchoringType.CENTER;
            case TextBoxAnchor.BOTTOM_BASELINE:
            case TextBoxAnchor.BOTTOM_CENTERED:
            case TextBoxAnchor.BOTTOM_CENTERED_BASELINE:
            case TextBoxAnchor.TOP_BASELINE:
            case TextBoxAnchor.TOP_CENTERED:
            case TextBoxAnchor.TOP_CENTERED_BASELINE:
            case TextBoxAnchor.MIDDLE_CENTERED:
            default:
                return DmlTextAnchoringType.TOP;
        }
    }

    /// <summary>
    /// Parses DmlGradientFill.GradientStops array.
    /// </summary>
    static GradientColor[] dmlToGradientStops(ArrayList<DmlGradientStop> list, IThemeProvider themeProvider)
    {
        GradientColor[] result = new GradientColor[list.size()];

        for (int i = 0; i < list.size(); i++)
        {
            DmlGradientStop dmlGradientStop = list.get(i);
            GradientColor gradientColor = new GradientColor();
            gradientColor.Color = dmlGradientStop.getColor().createDrColor(themeProvider, null);
            gradientColor.Start = MathUtil.doubleToInt(DmlPercentageUtil.toDmlPercent(dmlGradientStop.getPosition()) / 100000.0 * (double) 0x10000);

            result[i] = gradientColor;
        }
        return result;
    }

    /// <summary>
    /// Converts alignment to origin point.
    /// </summary>
    static /*PointF*/long getOrigin(/*DmlRectangleAlignment*/int alignment)
    {
        switch (alignment)
        {
            case DmlRectangleAlignment.TOP_LEFT:
                return msPointF.ctor(-0.5f, -0.5f);
            case DmlRectangleAlignment.TOP:
                return msPointF.ctor(0f, -0.5f);
            case DmlRectangleAlignment.TOP_RIGHT:
                return msPointF.ctor(0.5f, -0.5f);
            case DmlRectangleAlignment.LEFT:
                return msPointF.ctor(-0.5f, 0f);
            case DmlRectangleAlignment.CENTER:
            case DmlRectangleAlignment.NONE: // for Dml Text Effects.
                return msPointF.ctor(0f, 0f);
            case DmlRectangleAlignment.RIGHT:
                return msPointF.ctor(0.5f, 0f);
            case DmlRectangleAlignment.BOTTOM_LEFT:
                return msPointF.ctor(-0.5f, 0.5f);
            case DmlRectangleAlignment.BOTTOM:
                return msPointF.ctor(0f, 0.5f);
            case DmlRectangleAlignment.BOTTOM_RIGHT:
                return msPointF.ctor(0.5f, 0.5f);
            default:
                return msPointF.Empty;
        }
    }

    /// <summary>
    /// Calculates value in the range between "-spread" and "spread". Result depends from 
    /// ratio of the shape width and height, when width greater then height, then value is positive,
    /// otherwise negative.
    /// </summary>
    static int getScaledSpread(double width, double height, int spread)
    {
        int delta = 0;
        if (MathUtil.areEqual(width, height))
            return delta;

        if (width > height)
            delta = (int)msMath.round((1.0 - height / width) * spread);
        else
            delta = (int)msMath.round((1.0 - width / height) * -spread);

        return delta;
    }
}

