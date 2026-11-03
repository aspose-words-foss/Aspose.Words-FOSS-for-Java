// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2016 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ConvertUtilCore;


/// <summary>
/// A simple class that handles DML/VML specifics when reading some properties from a shape.
/// </summary>
/// <remarks>
/// It is used for text box width calculation by the new table grid algorithm.
/// </remarks>
class ShapeAdaptor
{
    ShapeAdaptor(Shape shape)
    {
        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            DmlShape dmlShape = ms.as(shape.getDmlNode(), DmlShape.class);
            if ((dmlShape == null) || (dmlShape.getTextShape() == null))
            {
                Debug.fail("Unexpected shape type.");
                mWrapMode = TextBoxWrapMode.NONE;
                mMargins = 0d;
            }
            else
            {
                // Get from text body properties.
                DmlTextBodyProperties bodyPr = dmlShape.getTextShape().getTextBody().getProperties();
                
                mWrapMode = bodyPr.getTextWrappingType();
                int marginsEmu = bodyPr.getLeftInset() + bodyPr.getRightInset();
                mMargins = ConvertUtilCore.emuToPoint(marginsEmu);
            }
        }
        else
        {
            // TextBox should work for a VML shape.
            TextBox textBox = shape.getTextBox();
            
            mWrapMode = textBox.getTextBoxWrapMode();
            mMargins = textBox.getInternalMarginLeft() + textBox.getInternalMarginRight();
        }

        // I had an idea to use shape content rectangle computed in the layout here.
        // There is experimental code to access it from here via PageLayout and ShapeInfo @dmitry.matveenko/tbl_27620_textBox.
        // But eventually I decided against it as it is simpler just to compute the grid in the layout as well.
        mContentWidth = getContentWidthFromProperties(shape);
        // A condition not to re-calculate fixed layout grids inside text boxes was introduced
        // to preserve grids computed during layout stage.
    }

    /// <summary>
    /// Gets the wrapping mode of the underlying shapes taking DML specifics into account.
    /// </summary>
    /*TextBoxWrapMode*/int getWrapMode() { return mWrapMode; };

    private /*TextBoxWrapMode*/int mWrapMode;

    /// <summary>
    /// Gets the combined left and right margins of the underlying shape in points,
    /// taking DML specifics into account.
    /// </summary>
    double getMargins() { return mMargins; };

    private double mMargins;

    /// <summary>
    /// Gets content width computed from shape width, margins and outlines.
    /// </summary>
    /// <remarks>
    /// This is the original simplistic implementation that is used outside layout.
    /// It may be incorrect, especially for complex shapes.
    /// Correct computation is supposed to be done by Rendering.
    /// </remarks>
    double getContentWidth() { return mContentWidth; };

    private double mContentWidth;

    private double getContentWidthFromProperties(Shape shape)
    {
        double contentWidth = shape.getWidth() - getMargins();
        Stroke stroke = shape.getStroke();
        if (stroke.getOn())
            contentWidth -= stroke.getWeight();  // half-sum of the two border sides

        return contentWidth;
    }
}

