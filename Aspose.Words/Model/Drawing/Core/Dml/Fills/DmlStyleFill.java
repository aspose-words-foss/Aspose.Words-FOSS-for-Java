// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;


/// <summary>
/// Represents a fill from style.
/// </summary>
class DmlStyleFill extends DmlFill
{
    /*override*/ DmlFill deepClone()
    {
        return new DmlStyleFill();
    }

    /// <summary>
    /// Defines the transparency of a fill. Valid range from 0 to 1, where 0 is fully transparent and 1 is fully opaque.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1.</p>
    /// </remarks>
    public /*override*/ double getOpacity() { return super.getOpacity(); }
    public /*override*/ void setOpacity(double value)
    {
        DmlSolidFill solidFill = new DmlSolidFill(getDmlColorInternal());
        getParent().setFill(solidFill);

        solidFill.setOpacity(value);
    }

    /// <summary>
    /// Gets or sets a boolean value indicating whether a parent object is filled.
    /// </summary>
    public /*override*/ boolean getOn()
    {
        ShapeBase shape = ms.as(getParent(), ShapeBase.class);
        if (shape != null)
        {
            DmlShapeStyle style = ((IDmlCommonShapePrSource)shape.getDmlNode()).getStyle();
            // WORDSNET-16732 When fill type is StyleFill, we need to check that style actually exists in a shape.
            if (style == null)
                return false;

            // WORDSNET-25919 Check shapes that have no 'a:noFill' tags and have default style fill.
            // A StyleMatrixIndex value of 0 or 1000 indicates no background for a Shape.
            // See description in ISO29500 20.1.4.2.10 fillRef (Fill Reference).
            int styleMatrixIndex = style.getFillReference().getStyleMatrixIndex();
            return (styleMatrixIndex != 0) && (styleMatrixIndex != 1000);
        }

        // MS Word using the default fill style for a new charts,
        // which always has the value true for the Visible property.
        // We need to mimic this behavior.
        if (getParent() instanceof ChartFormat)
            return true;

        return false;
    }
    public /*override*/ void setOn(boolean value)
    {
        if (getParent() instanceof ShapeBase)
        {
            if (value)
            {
                if (!getOn())
                {
                    // WORDSNET-25919 For a moment we just set a DmlSolidFill for a shape.
                    // It is not clear what value exactly should be set,
                    // as there is no such property analogue in Word and
                    // there is no any default filling in specification.
                    // So, for a moment, lets postpone this issue.
                    getParent().setFill(new DmlSolidFill(getDmlColorInternal()));
                }
            }
            else
            {
                getParent().setFill(new DmlNoFill());
            }
        }
        else
        {
            super.setOn(value);
        }
    }

    /// <summary>
    /// Gets or sets foreground color of the fill.
    /// </summary>
    /*override*/ DmlColor getDmlColorInternal()
    {
        Shape shape = ms.as(getParent(), Shape.class);

        if (shape != null)
        {
            IDmlCommonShapePrSource shapePrSource = (IDmlCommonShapePrSource)shape.getDmlNode();
            if (shapePrSource.getStyle() != null)
                return shapePrSource.getStyle().getFillReference().getColor();
        }


        return null;
    }
    /*override*/ void setDmlColorInternal(DmlColor value)
    {
        DmlSolidFill solidFill = new DmlSolidFill(value);
        getParent().setFill(solidFill);
    }

    /*override*/ /*DmlFillType*/int getDmlFillType() { return DmlFillType.STYLE_FILL; }
}

