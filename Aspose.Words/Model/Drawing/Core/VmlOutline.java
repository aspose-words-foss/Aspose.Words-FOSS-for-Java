// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.DrColor;


class VmlOutline implements IStroke
{
    VmlOutline(Shape shape)
    {
        mShape = shape;
    }

    //>>>>>>>> #region  IStroke interface implementation.

    public boolean getOn() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LINE_ON) && (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEOMETRY_LINE_OK); }
    public void setOn(boolean value)
    {
        // Setting both attributes with the same value ensures the correct visual output.
        setAttr(ShapeAttr.LINE_ON, value);
        setAttr(ShapeAttr.GEOMETRY_LINE_OK, value);
    }

    public double getWeight() { return ConvertUtilCore.emuToPoint((/*int*/Integer)fetchAttr(ShapeAttr.LINE_WIDTH)); }
    public void setWeight(double value)
    {
        if (value < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        setAttr(ShapeAttr.LINE_WIDTH, ConvertUtilCore.pointToEmu(value));
    }

    /// <summary>
    /// Gets or sets a color of a stroke.
    /// </summary>
    public DrColor getColorInternal() { return (DrColor)fetchAttr(ShapeAttr.LINE_COLOR); }
    public void setColorInternal(DrColor value) { setAttr(ShapeAttr.LINE_COLOR, value); }

    /// <summary>
    /// Gets an unmodified base color of a stroke.
    /// </summary>
    public DrColor getColorInternalUnmodified() { return getColorInternal(); }

    public DrColor getColor2Internal() { return (DrColor)fetchAttr(ShapeAttr.LINE_BACK_COLOR); }
    public void setColor2Internal(DrColor value) { setAttr(ShapeAttr.LINE_BACK_COLOR, value); }

    public /*DashStyle*/int getDashStyle() { return (/*DashStyle*/Integer)fetchAttr(ShapeAttr.LINE_DASH_STYLE); }
    public void setDashStyle(/*DashStyle*/int value) { setAttr(ShapeAttr.LINE_DASH_STYLE, value); }

    public /*JoinStyle*/int getJoinStyle() { return (/*JoinStyle*/Integer)fetchAttr(ShapeAttr.LINE_JOIN_STYLE); }
    public void setJoinStyle(/*JoinStyle*/int value) { setAttr(ShapeAttr.LINE_JOIN_STYLE, value); }

    public /*EndCap*/int getEndCap() { return (/*EndCap*/Integer)fetchAttr(ShapeAttr.LINE_END_CAP_STYLE); }
    public void setEndCap(/*EndCap*/int value) { setAttr(ShapeAttr.LINE_END_CAP_STYLE, value); }

    public /*ShapeLineStyle*/int getLineStyle() { return (/*ShapeLineStyle*/Integer)fetchAttr(ShapeAttr.LINE_STYLE); }
    public void setLineStyle(/*ShapeLineStyle*/int value) { setAttr(ShapeAttr.LINE_STYLE, value); }

    public /*ArrowType*/int getStartArrowType() { return (/*ArrowType*/Integer)fetchAttr(ShapeAttr.LINE_START_ARROW); }
    public void setStartArrowType(/*ArrowType*/int value) { setAttr(ShapeAttr.LINE_START_ARROW, value); }

    public /*ArrowType*/int getEndArrowType() { return (/*ArrowType*/Integer)fetchAttr(ShapeAttr.LINE_END_ARROW); }
    public void setEndArrowType(/*ArrowType*/int value) { setAttr(ShapeAttr.LINE_END_ARROW, value); }

    public /*ArrowWidth*/int getStartArrowWidth() { return (/*ArrowWidth*/Integer)fetchAttr(ShapeAttr.LINE_START_ARROW_WIDTH); }
    public void setStartArrowWidth(/*ArrowWidth*/int value) { setAttr(ShapeAttr.LINE_START_ARROW_WIDTH, value); }

    public /*ArrowLength*/int getStartArrowLength() { return (/*ArrowLength*/Integer)fetchAttr(ShapeAttr.LINE_START_ARROW_LENGTH); }
    public void setStartArrowLength(/*ArrowLength*/int value) { setAttr(ShapeAttr.LINE_START_ARROW_LENGTH, value); }

    public /*ArrowWidth*/int getEndArrowWidth() { return (/*ArrowWidth*/Integer)fetchAttr(ShapeAttr.LINE_END_ARROW_WIDTH); }
    public void setEndArrowWidth(/*ArrowWidth*/int value) { setAttr(ShapeAttr.LINE_END_ARROW_WIDTH, value); }

    public /*ArrowLength*/int getEndArrowLength() { return (/*ArrowLength*/Integer)fetchAttr(ShapeAttr.LINE_END_ARROW_LENGTH); }
    public void setEndArrowLength(/*ArrowLength*/int value) { setAttr(ShapeAttr.LINE_END_ARROW_LENGTH, value); }

    public double getOpacity() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.LINE_OPACITY)); }
    public void setOpacity(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        setAttr(ShapeAttr.LINE_OPACITY, ConvertUtilCore.doubleToFixed(value));
    }

    public byte[] getImageBytes() { return (byte[])fetchAttr(ShapeAttr.LINE_IMAGE_BYTES); }

    public /*LineFillType*/int getLineFillType() { return (/*LineFillType*/Integer)fetchAttr(ShapeAttr.LINE_FILL_TYPE); }
    public void setLineFillType(/*LineFillType*/int value) { setAttr(ShapeAttr.LINE_FILL_TYPE, value); }

    /// <summary>
    /// Gets or sets a <see cref="DmlFill"/> object of the stroke fill.
    /// </summary>
    public DmlFill /*IStroke.*/getStrokeFill() { return null; }
    public void /*IStroke.*/setStrokeFill(DmlFill value) { throw new IllegalStateException("Object doesn't support this action."); }

    //<<<<<<<< #endregion 

    private Object fetchAttr(int key)
    {
        return mShape.fetchShapeAttrInternal(key);
    }

    private void setAttr(int key, Object value)
    {
        mShape.setShapeAttrInternal(key, value);
    }

    private /*final*/ Shape mShape;
}

