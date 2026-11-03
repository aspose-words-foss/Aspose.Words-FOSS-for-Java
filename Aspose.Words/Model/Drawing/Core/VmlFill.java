// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ConvertUtilCore;
import com.aspose.MathUtil;
import com.aspose.ArrayUtil;
import com.aspose.Debug;
import com.aspose.DrColor;


class VmlFill implements IFill
{
    /// <summary>
    /// Sets one of the predefined static focus positions corresponded to a particular <see cref="GradientStyle"/>
    /// and Variant to a specified shape attributes.
    /// </summary>
    static void setFocusPositions(ShapePr shapePr, /*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        double[] focusPositions = getFocusPositions(style, variant);
        shapePr.setAttrIfNotDefault(ShapeAttr.FILL_TO_LEFT, ConvertUtilCore.doubleToFixed(focusPositions[0]));
        shapePr.setAttrIfNotDefault(ShapeAttr.FILL_TO_TOP, ConvertUtilCore.doubleToFixed(focusPositions[1]));
        shapePr.setAttrIfNotDefault(ShapeAttr.FILL_TO_RIGHT, ConvertUtilCore.doubleToFixed(focusPositions[2]));
        shapePr.setAttrIfNotDefault(ShapeAttr.FILL_TO_BOTTOM, ConvertUtilCore.doubleToFixed(focusPositions[3]));
    }

    /// <summary>
    /// Gets gradient focus corresponded to a specified <see cref="GradientStyle"/> and Variant.
    /// </summary>
    static int getGradientFocus(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        switch (style)
        {
            case GradientStyle.HORIZONTAL:
            case GradientStyle.VERTICAL:
            case GradientStyle.DIAGONAL_UP:
            case GradientStyle.DIAGONAL_DOWN:
                return getLinearGradientFocus(style, variant);
            case GradientStyle.FROM_CORNER:
                return getFromCornerGradientFocus(variant);
            case GradientStyle.FROM_CENTER:
                return getFromCenterGradientFocus(variant);
            default:
                throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "style" + "\r\nParameter value: " + style);
        }
    }

    /// <summary>
    /// Returns angle corresponding to a specified gradient style.
    /// </summary>
    static double getLinearGradientAngle(/*GradientStyle*/int style)
    {
        switch (style)
        {
            case GradientStyle.HORIZONTAL:
                return 0.0;
            case GradientStyle.VERTICAL:
                return -90;
            case GradientStyle.DIAGONAL_UP:
                return -135;
            case GradientStyle.DIAGONAL_DOWN:
                return -45;
            default:
                return 0.0;
        }
    }

    /// <summary>
    /// Returns style of a linear gradient by specified angle.
    /// </summary>
    private static /*GradientStyle*/int getLinearGradientStyle(double angle)
    {
        // Fit value to diapason from -360 to 360 degrees.
        if (Math.abs(angle) > 360)
            angle %= 360.0;

        if (MathUtil.isZero(angle))
            return GradientStyle.HORIZONTAL;

        if (MathUtil.areEqual(angle, -90.0))
            return GradientStyle.VERTICAL;

        if (MathUtil.areEqual(angle, -135.0))
            return GradientStyle.DIAGONAL_UP;

        if (MathUtil.areEqual(angle, -45.0))
            return GradientStyle.DIAGONAL_DOWN;

        return GradientStyle.NONE;
    }

    /// <summary>
    /// Gets the gradient variant for the 'FromCorner' fills.
    /// </summary>
    private /*GradientVariant*/int getFromCornerGradientVariant()
    {
        double[] focusPositios = new double[] { getFocusLeft(), getFocusTop(), getFocusRight(), getFocusBottom() };
        if (ArrayUtil.isArrayEqual(focusPositios, G_CORNER_RECT_1))
            return GradientVariant.VARIANT_1;

        if (ArrayUtil.isArrayEqual(focusPositios, G_CORNER_RECT_2))
            return GradientVariant.VARIANT_2;

        if (ArrayUtil.isArrayEqual(focusPositios, G_CORNER_RECT_3))
            return GradientVariant.VARIANT_3;

        if (ArrayUtil.isArrayEqual(focusPositios, G_CORNER_RECT_4))
            return GradientVariant.VARIANT_4;

        return GradientVariant.NONE;
    }

    /// <summary>
    /// Gets the gradient variant for the 'FromCenter' fills.
    /// </summary>
    private /*GradientVariant*/int getFromCenterGradientVariant()
    {
        switch (getFocus())
        {
            case 0:
                return GradientVariant.VARIANT_2;
            case 100:
                return GradientVariant.VARIANT_1;
            default:
                return GradientVariant.NONE;
        }
    }

    /// <summary>
    /// Gets the gradient variant for the linear gradient fills.
    /// </summary>
    private /*GradientVariant*/int getLinearGradientVariant(/*GradientStyle*/int style)
    {
        Debug.assertTrue(
            (style == GradientStyle.HORIZONTAL) ||
            (style == GradientStyle.VERTICAL) ||
            (style == GradientStyle.DIAGONAL_DOWN) ||
            (style == GradientStyle.DIAGONAL_UP));

        switch (getFocus())
        {
            case 50:
                return (style == GradientStyle.HORIZONTAL) ? GradientVariant.VARIANT_3 : GradientVariant.VARIANT_4;
            case -50:
                return (style == GradientStyle.HORIZONTAL) ? GradientVariant.VARIANT_4 : GradientVariant.VARIANT_3;
            case 100:
                return (style == GradientStyle.DIAGONAL_DOWN) ? GradientVariant.VARIANT_2 : GradientVariant.VARIANT_1;
            case 0:
                return (style == GradientStyle.DIAGONAL_DOWN) ? GradientVariant.VARIANT_1 : GradientVariant.VARIANT_2;
            default:
                return GradientVariant.NONE;
        }
    }

    /// <summary>
    /// Gets the gradient focus for the linear gradient fills.
    /// </summary>
    private static int getLinearGradientFocus(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        Debug.assertTrue(
            (style == GradientStyle.HORIZONTAL) ||
            (style == GradientStyle.VERTICAL) ||
            (style == GradientStyle.DIAGONAL_DOWN) ||
            (style == GradientStyle.DIAGONAL_UP));

        switch (variant)
        {
            case GradientVariant.VARIANT_4:
                return (style == GradientStyle.HORIZONTAL) ? -50 : 50;
            case GradientVariant.VARIANT_3:
                return (style == GradientStyle.HORIZONTAL) ? 50 : -50;
            case GradientVariant.VARIANT_2:
                return (style == GradientStyle.DIAGONAL_DOWN) ? 100 : 0;
            case GradientVariant.VARIANT_1:
                return (style == GradientStyle.DIAGONAL_DOWN) ? 0 : 100;
            default:
                throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "variant" + "\r\nParameter value: " + variant);
        }
    }

    /// <summary>
    /// Gets the gradient focus for the 'FromCenter' fills.
    /// </summary>
    private static int getFromCenterGradientFocus(/*GradientVariant*/int variant)
    {
        switch (variant)
        {
            case GradientVariant.VARIANT_1:
                return 100;
            case GradientVariant.VARIANT_2:
                return 0;
            default:
                throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "variant" + "\r\nParameter value: " + variant);
        }
    }

    /// <summary>
    /// Gets the gradient focus for the 'FromCorner' fills.
    /// </summary>
    private static int getFromCornerGradientFocus(/*GradientVariant*/int variant)
    {
        switch (variant)
        {
            case GradientVariant.VARIANT_1:
            case GradientVariant.VARIANT_2:
            case GradientVariant.VARIANT_3:
            case GradientVariant.VARIANT_4:
                return 100;
            default:
                throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "variant" + "\r\nParameter value: " + variant);
        }
    }

    /// <summary>
    /// Returns one of the predefined static focus positions corresponded
    /// to a particular <see cref="GradientStyle"/> and Variant.
    /// </summary>
    /// <remarks>
    /// Note, this should be immutable.
    /// </remarks>
    private static double[] getFocusPositions(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        switch (style)
        {
            case GradientStyle.FROM_CORNER:
            {
                switch (variant)
                {
                    case GradientVariant.VARIANT_1:
                        return G_CORNER_RECT_1;
                    case GradientVariant.VARIANT_2:
                        return G_CORNER_RECT_2;
                    case GradientVariant.VARIANT_3:
                        return G_CORNER_RECT_3;
                    case GradientVariant.VARIANT_4:
                        return G_CORNER_RECT_4;
                    default:
                        throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "variant" + "\r\nParameter value: " + variant);
                }
            }
            case GradientStyle.FROM_CENTER:
                return G_CENTER_RECT;
            default:
                throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "style" + "\r\nParameter value: " + style);
        }
    }

    private void setOpacityAttrCore(double value, int key)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        setAttr(key, ConvertUtilCore.doubleToFixed(value));
    }

    private Object fetchAttr(int key)
    {
        return getShape().fetchShapeAttrInternal(key);
    }

    private void setAttr(int key, Object value)
    {
        getShape().setShapeAttrInternal(key, value);
    }

    private void removeAttr(int key)
    {
        getShape().removeShapeAttrInternal(key);
    }

    //>>>>>>>> #region  IFill interface implementation.
    public void setImageBytes(byte[] imageBytes)
    {
        setFillType(FillTypeCore.PICTURE);

        setAttr(ShapeAttr.FILL_IMAGE_BYTES, imageBytes);
        setAttr(ShapeAttr.FILLED, true);

        // WORDSNET-16357 Remove possible existing texture.
        removeAttr(ShapeAttr.FILL_PRESET_TEXTURE);
    }

    /// <summary>
    /// Gets or sets a color.
    /// </summary>
    public DrColor getColorInternal() { return (DrColor)fetchAttr(ShapeAttr.FILL_COLOR); }
    public void setColorInternal(DrColor value) { setAttr(ShapeAttr.FILL_COLOR, value); }

    /// <summary>
    /// Gets a base color without modifiers.
    /// </summary>
    public DrColor getColorInternalUnmodified() { return getColorInternal(); }

    /// <summary>
    /// Gets or sets a background color.
    /// </summary>
    public DrColor getColor2Internal() { return (DrColor)fetchAttr(ShapeAttr.FILL_BACK_COLOR); }
    public void setColor2Internal(DrColor value) { setAttr(ShapeAttr.FILL_BACK_COLOR, value); }

    /// <summary>Gets the array of custom gradient colors.</summary>
    /// <remarks>
    /// <p>The default value is <c>null</c>.</p>
    /// </remarks>
    public GradientColor[] getGradientColors() { return (GradientColor[])fetchAttr(ShapeAttr.FILL_SHADE_COLORS); }

    public double getOpacity() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.FILL_OPACITY)); }
    public void setOpacity(double value) { setOpacityAttrCore(value, ShapeAttr.FILL_OPACITY); }

    public double getOpacity2() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.FILL_BACK_OPACITY)); }
    public void setOpacity2(double value) { setOpacityAttrCore(value, ShapeAttr.FILL_BACK_OPACITY); }

    public boolean /*IFill.*/getOn() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.FILLED); }
    public void /*IFill.*/setOn(boolean value) { setAttr(ShapeAttr.FILLED, value); }

    public byte[] getImageBytes() { return (byte[])fetchAttr(ShapeAttr.FILL_IMAGE_BYTES); }

    public double getAngle() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.FILL_ANGLE)); }
    public void setAngle(double value) { setAttr(ShapeAttr.FILL_ANGLE, ConvertUtilCore.doubleToFixed(value)); }

    /// <summary>
    /// Gets the gradient style for the fill.
    /// https://docs.microsoft.com/en-us/office/vba/api/word.fillformat.gradientstyle
    /// </summary>
    public /*GradientStyle*/int /*IFill.*/getGradientStyle()
    {
        if (getShape() == null)
            return GradientStyle.NONE;

        /*FillTypeCore*/int fillType = (/*FillTypeCore*/Integer)fetchAttr(ShapeAttr.FILL_TYPE);
        switch (fillType)
        {
            // Path gradient.
            case FillTypeCore.SHADE_CENTER:
                return GradientStyle.FROM_CORNER;
            case FillTypeCore.SHADE_SHAPE:
                return GradientStyle.FROM_CENTER;
            // Linear gradient.
            case FillTypeCore.SHADE:
            case FillTypeCore.SHADE_TITLE:
            case FillTypeCore.SHADE_UNSCALE:
            case FillTypeCore.SHADE_SCALE:
                return getLinearGradientStyle(getAngle());
            default:
                return GradientStyle.NONE;
        }
    }

    /// <summary>
    /// Gets the gradient variant for the fill as an integer value from 1 to 4 for most gradient fills or 0 if not defined.
    /// https://docs.microsoft.com/en-us/office/vba/api/word.fillformat.gradientvariant
    /// </summary>
    public /*GradientVariant*/int /*IFill.*/getGradientVariant()
    {
        /*GradientStyle*/int style = ((IFill)this).getGradientStyle();
        switch (style)
        {
            case GradientStyle.HORIZONTAL:
            case GradientStyle.VERTICAL:
            case GradientStyle.DIAGONAL_UP:
            case GradientStyle.DIAGONAL_DOWN:
                return getLinearGradientVariant(style);
            case GradientStyle.FROM_CORNER:
                return getFromCornerGradientVariant();
            case GradientStyle.FROM_CENTER:
                return getFromCenterGradientVariant();
            default:
                return GradientVariant.NONE;
        }
    }

    /// <summary>
    /// Determines whether the fill rotates with the specified shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>true</c>.</p>
    /// </remarks>
    /// <dev>
    /// IN. Word VBA does not allow access to this property even in getter. But GUI allows full access
    /// for the all fills except of Solid and Pattern. So, I think we should allow this property as well.
    /// </dev>
    public boolean getRotateWithShape() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.FILL_USE_SHAPE_ANCHOR); }
    public void setRotateWithShape(boolean value) { setAttr(ShapeAttr.FILL_USE_SHAPE_ANCHOR, value); }

    public boolean getLockAspectRatio() { return (/*FillDimensionType*/Integer)fetchAttr(ShapeAttr.FILL_DIMENSION_TYPE) != FillDimensionType.DEFAULT; }
    public void setLockAspectRatio(boolean value) { setAttr(ShapeAttr.FILL_DIMENSION_TYPE, value ? FillDimensionType.FIXED_ASPECT : FillDimensionType.DEFAULT); }

    /// <summary>
    /// Defines the left position of the center of a radial gradient.
    /// </summary>
    /// <remarks>
    /// <p>The value is a fraction of the width of the shape.</p>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public double getFocusLeft() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.FILL_TO_LEFT)); }
    public void setFocusLeft(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        setAttr(ShapeAttr.FILL_TO_LEFT, ConvertUtilCore.doubleToFixed(value));
    }

    /// <summary>
    /// Defines the top position of the center of a radial gradient.
    /// </summary>
    /// <remarks>
    /// <p>The value is a fraction of the height of the shape.</p>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public double getFocusTop() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.FILL_TO_TOP)); }
    public void setFocusTop(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        setAttr(ShapeAttr.FILL_TO_TOP, ConvertUtilCore.doubleToFixed(value));
    }

    /// <summary>
    /// Defines the right position of the center of a radial gradient.
    /// </summary>
    /// <remarks>
    /// <p>The value is a fraction of the width of the shape.</p>
    /// <p>The default value is 0.</p>
    /// </remarks>
    private double getFocusRight() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.FILL_TO_RIGHT)); }
    private void setFocusRight(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        setAttr(ShapeAttr.FILL_TO_RIGHT, ConvertUtilCore.doubleToFixed(value));
    }

    /// <summary>
    /// Defines the bottom position of the center of a radial gradient.
    /// </summary>
    /// <remarks>
    /// <p>The value is a fraction of the height of the shape.</p>
    /// <p>The default value is 0.</p>
    /// </remarks>
    private double getFocusBottom() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.FILL_TO_BOTTOM)); }
    private void setFocusBottom(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        setAttr(ShapeAttr.FILL_TO_BOTTOM, ConvertUtilCore.doubleToFixed(value));
    }

    /// <summary>
    /// Defines the center of a linear gradient fill.
    /// </summary>
    /// <remarks>
    /// <p>Defines the center starting position of the blend. Values range from -100 to 100.
    /// The default value is 0.</p>
    ///
    /// <p>A value of 100 or -100 will shift the focus so that the direction of the blend will reverse
    /// (in effect reversing Color and Color2). A value of 50 will change the blend so that Color is at
    /// both ends and Color2 is in the middle. A value of -50 will change the blend so that Color2 is at
    /// both ends and Color is in the middle.</p>
    /// </remarks>
    public int getFocus() { return (/*int*/Integer)fetchAttr(ShapeAttr.FILL_FOCUS); }
    public void setFocus(int value)
    {
        if ((value < -100) || (value > 100))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        setAttr(ShapeAttr.FILL_FOCUS, value);
    }

    /// <summary>
    /// Defines the type of fill.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="FillTypeCore.Solid"/>.</p>
    /// </remarks>
    public /*FillTypeCore*/int getFillType() { return (/*FillTypeCore*/Integer)fetchAttr(ShapeAttr.FILL_TYPE); }
    public void setFillType(/*FillTypeCore*/int value) { setAttr(ShapeAttr.FILL_TYPE, value); }

    /// <summary>
    /// Gets or sets parent object.
    /// </summary>
    public IFillable /*IFill.*/getParent() { return mParent; }
    public void /*IFill.*/setParent(IFillable value) { mParent = value; }
    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets parent Shape object.
    /// </summary>
    private ShapeBase getShape() { return ms.as(mParent, ShapeBase.class); }

    private IFillable mParent;

    /// <summary>
    /// Predefined focus positions corresponded to a particular <see cref="GradientStyle"/> and Variant.
    /// </summary>
    private static /*final*/ double[] G_CORNER_RECT_1 = new double[] {0.0, 0.0, 0.0, 0.0};
    private static /*final*/ double[] G_CORNER_RECT_2 = new double[] { 1.0, 0.0, 1.0, 0.0 };
    private static /*final*/ double[] G_CORNER_RECT_3 = new double[] { 0.0, 1.0, 0.0, 1.0 };
    private static /*final*/ double[] G_CORNER_RECT_4 = new double[] { 1.0, 1.0, 1.0, 1.0 };
    private static /*final*/ double[] G_CENTER_RECT = new double[] {0.5 , 0.5, 0.5, 0.5 };

    private static final String VALUE_OUT_OF_RANGE = "The specified value is out of range.";
}

