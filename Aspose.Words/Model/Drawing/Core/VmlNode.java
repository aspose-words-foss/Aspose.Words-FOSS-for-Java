// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/10/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.Collections.msDictionary;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.ms.System.msMath;
import com.aspose.ArgumentUtil;
import java.util.HashMap;
import com.aspose.words.ref.Ref;


class VmlNode extends Graphic
{
    VmlNode(ShapeBase shape)
    {
        mShape = shape;
    }

    /*override*/ void setCoordSizeWidthSafe(int width)
    {
        setCoordSizeSafeCore(ShapeAttr.COORD_SIZE_WIDTH, width);
    }

    /*override*/ void setCoordSizeHeightSafe(int height)
    {
        setCoordSizeSafeCore(ShapeAttr.COORD_SIZE_HEIGHT, height);
    }

    /*override*/ void setWidthCore(double value, boolean isThrow)
    {
        mShape.setShapeAttrInternal(ShapeAttr.WIDTH,
            ShapeSizeValidationHelper.validateDimension(mShape, value, isThrow, "width"));
    }

    /*override*/ void setHeightCore(double value, boolean isThrow)
    {
        mShape.setShapeAttrInternal(ShapeAttr.HEIGHT,
            ShapeSizeValidationHelper.validateDimension(mShape, value, isThrow, "height"));
    }

    /// <summary>
    /// Removes shadow effect from a shape.
    /// </summary>
    /*override*/ void removeShadow()
    {
        mShape.getShapePr().remove(ShapeAttr.SHADOW_ON);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_TYPE);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_COLOR);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_HIGHLIGHT);
        mShape.getShapePr().remove(ShapeAttr.IMAGE_DBL_CR_MOD);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_OPACITY);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_OFFSET_X);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_OFFSET_Y);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_ORIGIN_X);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_ORIGIN_Y);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_SCALE_XTO_X);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_SCALE_XTO_Y);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_SCALE_YTO_X);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_SCALE_YTO_Y);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_SECOND_OFFSET_X);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_SECOND_OFFSET_Y);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_PERSPECTIVE_X);
        mShape.getShapePr().remove(ShapeAttr.SHADOW_PERSPECTIVE_Y);
    }

    private void setCoordSizeSafeCore(int key, int value)
    {
        if (value < 0)
            value = (/*int*/Integer)mShape.fetchInheritedShapeAttrInternal(key);

        mShape.setShapeAttrInternal(key, value);
    }

    /// <summary>
    /// Gets the collection of adjustment values that are applied to the shape.
    /// </summary>
    private AdjustmentCollection getAdjustmentCollection()
    {
        ShapePr parentPr = ShapeTypeLibrary.getShapeTypePr(getShapeType());

        AdjustmentCollection adjustments = new AdjustmentCollection();
        for (int adjustKey = ShapeAttr.GEOMETRY_ADJUST_1; adjustKey <= ShapeAttr.GEOMETRY_ADJUST_10; ++adjustKey)
        {
            Object adjustObj = mShape.getShapePr().get(adjustKey);
            if ((adjustObj == null) && (parentPr != null))
                adjustObj = parentPr.get(adjustKey);

            if (adjustObj == null)
                continue;

            int index = adjustKey - ShapeAttr.GEOMETRY_ADJUST_1 + 1;
            int adjustValue = (/*int*/Integer)adjustObj;
            adjustments.add(new Adjustment(index, adjustValue, mShape));
        }

        return adjustments;
    }

    /// <summary>
    /// Sets value to the <see cref="ShapePr"/> collection if value is not null,
    /// or remove it from the collection otherwise.
    /// </summary>
    private void setAttrSafe(int key, Object value)
    {
        if (value == null)
            mShape.getShapePr().remove(key);
        else
            mShape.getShapePr().set(key, value);
    }

    private void setCropCoefficient(int key, double value)
    {
        if ((value < -1.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mShape.setShapeAttrInternal(key, ConvertUtilCore.doubleToFixed(value));
    }

    public /*override*/ boolean isHorizontalRule() { return (/*boolean*/Boolean)mShape.fetchShapeAttrInternal(ShapeAttr.HR_ON); }

    public /*override*/ boolean isWordArt()
    {
        // WORDSNET-5500 It appears that not any shape can be treated as a WordArt object,
        // but only which have a special ShapeType, because of a WordArt requirements to the paths.
        return VmlUtil.isWordArt(getShapeType(), (/*boolean*/Boolean)mShape.fetchShapeAttrInternal(ShapeAttr.GEO_TEXT_ON));
    }

    public /*override*/ String getName() { return (String) mShape.getShapePr().fetchAttr(ShapeAttr.SHAPE_NAME); }
    public /*override*/ void setName(String value) { mShape.getShapePr().setAttr(ShapeAttr.SHAPE_NAME, value); }

    public /*override*/ String getAlternativeText() { return (String) mShape.getShapePr().fetchAttr(ShapeAttr.SHAPE_DESCRIPTION); }
    public /*override*/ void setAlternativeText(String value) { mShape.getShapePr().setAttr(ShapeAttr.SHAPE_DESCRIPTION, value); }

    public /*override*/ boolean getDecorative()
    {
        Object value = mShape.getShapePr().fetchAttr(ShapeAttr.SHAPE_DESCRIPTION);
        return ("\"\"".equals((String)value));
    }

    public /*override*/ void setDecorative(boolean value)
    {
        if (value)
            mShape.getShapePr().setAttr(ShapeAttr.SHAPE_DESCRIPTION, "\"\"");
        else if ("\"\"".equals((String)mShape.getShapePr().fetchAttr(ShapeAttr.SHAPE_DESCRIPTION)))
            mShape.getShapePr().remove(ShapeAttr.SHAPE_DESCRIPTION);
    }

    public /*override*/ String getTitle() { return (String)mShape.getShapePr().fetchAttr(ShapeAttr.SHAPE_TITLE); }
    public /*override*/ void setTitle(String value) { mShape.getShapePr().setAttr(ShapeAttr.SHAPE_TITLE, value); }

    /*override*/ boolean getHidden() { return (/*boolean*/Boolean)mShape.getShapePr().fetchAttr(ShapeAttr.HIDDEN); }
    /*override*/ void setHidden(boolean value) { mShape.getShapePr().setAttr(ShapeAttr.HIDDEN, value); }

    /// <summary>
    /// Gets the shape type.
    /// </summary>
    public /*override*/ /*ShapeType*/int getShapeType()
    {
        // Use shape properties directly to avoid endless recursion.
        return (/*ShapeType*/Integer)mShape.getShapePr().fetchAttr(ShapeAttr.SHAPE_TYPE);
    }

    /// <summary>
    /// Provides access to the OLE data of a shape. For a shape that is not an OLE object or ActiveX control, returns <c>null</c>.
    /// </summary>
    public /*override*/ OleFormat getOleFormat()
    {
        if (!mShape.isOle())
            return null;

        if (mOleFormatCache == null)
            mOleFormatCache = new OleFormat(mShape);

        return mOleFormatCache;
    }

    /// <summary>
    /// Switches the orientation of a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.FlipOrientation.None"/>.</p>
    /// </remarks>
    public /*override*/ /*FlipOrientation*/int getFlipOrientation() { return (/*FlipOrientation*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.FLIP); }
    public /*override*/ void setFlipOrientation(/*FlipOrientation*/int value) { mShape.setShapeAttrInternal(ShapeAttr.FLIP, value); }

    /// <summary>
    /// Defines the angle (in degrees) that a shape is rotated.
    /// Positive value corresponds to clockwise rotation angle.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public /*override*/ double getRotation() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.TRANSFORM_ROTATION)); }
    public /*override*/ void setRotation(double value) { mShape.setShapeAttrInternal(ShapeAttr.TRANSFORM_ROTATION, ConvertUtilCore.doubleToFixed(value)); }

    /// <summary>
    /// Represents adjustment values that correspond to the positions of the adjust handles of the shape.
    /// </summary>
    public /*override*/ AdjustmentCollection getAdjustments()
    {
        if (mAdjustments == null)
            mAdjustments = getAdjustmentCollection();

        return mAdjustments;
    }

    /// <summary>
    /// Defines the text of the text path (of a WordArt object).
    /// </summary>
    public /*override*/ TextPath getTextPath()
    {
        if (mTextPathCache == null)
            mTextPathCache = new TextPath(mShape);
        return mTextPathCache;
    }

    /// <summary>
    /// Returns true if an extrusion effect is enabled.
    /// </summary>
    public /*override*/ boolean getExtrusionEnabled() { return (/*boolean*/Boolean)mShape.fetchShapeAttrInternal(ShapeAttr.TD_ON); }

    /// <summary>
    /// Returns true if a shadow effect is enabled.
    /// </summary>
    public /*override*/ boolean getShadowEnabled() { return (/*boolean*/Boolean)mShape.fetchShapeAttrInternal(ShapeAttr.SHADOW_ON); }

    //>>>>>>>> #region  ImageData properties.
    public /*override*/ double getBrightness()
    {
        return ImageData.brightnessToPercent(
            ConvertUtilCore.fixedToDouble((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.IMAGE_BRIGHTNESS)));
    }
    public /*override*/ void setBrightness(double value)
    {
        mShape.setShapeAttrInternal(ShapeAttr.IMAGE_BRIGHTNESS,
            ConvertUtilCore.doubleToFixed(ImageData.percentToBrightness(value)));
    }

    public /*override*/ double getContrast()
    {
        return ImageData.contrastToPercent(
                ConvertUtilCore.fixedToDouble((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.IMAGE_CONTRAST)));
    }
    public /*override*/ void setContrast(double value)
    {
        mShape.setShapeAttrInternal(ShapeAttr.IMAGE_CONTRAST, ConvertUtilCore.doubleToFixed(ImageData.percentToContrast(value)));
    }

    public /*override*/ boolean getBiLevel() { return (/*boolean*/Boolean)mShape.fetchShapeAttrInternal(ShapeAttr.IMAGE_BI_LEVEL); }
    public /*override*/ void setBiLevel(boolean value) { mShape.setShapeAttrInternal(ShapeAttr.IMAGE_BI_LEVEL, value); }

    public /*override*/ boolean getGrayScale() { return (/*boolean*/Boolean)mShape.fetchShapeAttrInternal(ShapeAttr.IMAGE_GRAY_SCALE); }
    public /*override*/ void setGrayScale(boolean value) { mShape.setShapeAttrInternal(ShapeAttr.IMAGE_GRAY_SCALE, value); }

    public /*override*/ double getCropTop() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.IMAGE_CROP_TOP)); }
    public /*override*/ void setCropTop(double value) { setCropCoefficient(ShapeAttr.IMAGE_CROP_TOP, value); }

    public /*override*/ double getCropBottom() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.IMAGE_CROP_BOTTOM)); }
    public /*override*/ void setCropBottom(double value) { setCropCoefficient(ShapeAttr.IMAGE_CROP_BOTTOM, value); }

    public /*override*/ double getCropLeft() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.IMAGE_CROP_LEFT)); }
    public /*override*/ void setCropLeft(double value) { setCropCoefficient(ShapeAttr.IMAGE_CROP_LEFT, value); }

    public /*override*/ double getCropRight() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.IMAGE_CROP_RIGHT)); }
    public /*override*/ void setCropRight(double value) { setCropCoefficient(ShapeAttr.IMAGE_CROP_RIGHT, value); }
    //<<<<<<<< #endregion 

    //>>>>>>>> #region  TextBox properties.
    public /*override*/ double getInternalMarginLeft() { return ConvertUtilCore.emuToPoint((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.TEXTBOX_LEFT)); }
    public /*override*/ void setInternalMarginLeft(double value) { mShape.setShapeAttrInternal(ShapeAttr.TEXTBOX_LEFT, ConvertUtilCore.pointToEmu(value)); }

    public /*override*/ double getInternalMarginRight() { return ConvertUtilCore.emuToPoint((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.TEXTBOX_RIGHT)); }
    public /*override*/ void setInternalMarginRight(double value) { mShape.setShapeAttrInternal(ShapeAttr.TEXTBOX_RIGHT, ConvertUtilCore.pointToEmu(value)); }

    public /*override*/ double getInternalMarginTop() { return ConvertUtilCore.emuToPoint((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.TEXTBOX_TOP)); }
    public /*override*/ void setInternalMarginTop(double value) { mShape.setShapeAttrInternal(ShapeAttr.TEXTBOX_TOP, ConvertUtilCore.pointToEmu(value)); }

    public /*override*/ double getInternalMarginBottom() { return ConvertUtilCore.emuToPoint((/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.TEXTBOX_BOTTOM)); }
    public /*override*/ void setInternalMarginBottom(double value) { mShape.setShapeAttrInternal(ShapeAttr.TEXTBOX_BOTTOM, ConvertUtilCore.pointToEmu(value)); }

    public /*override*/ boolean getFitShapeToText() { return (/*boolean*/Boolean)mShape.fetchShapeAttrInternal(ShapeAttr.TEXTBOX_FIT_SHAPE_TO_TEXT); }
    public /*override*/ void setFitShapeToText(boolean value) { mShape.setShapeAttrInternal(ShapeAttr.TEXTBOX_FIT_SHAPE_TO_TEXT, value); }

    public /*override*/ /*LayoutFlow*/int getLayoutFlow() { return (/*LayoutFlow*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.TEXTBOX_LAYOUT_FLOW); }
    public /*override*/ void setLayoutFlow(/*LayoutFlow*/int value) { mShape.setShapeAttrInternal(ShapeAttr.TEXTBOX_LAYOUT_FLOW, value); }

    public /*override*/ /*TextBoxWrapMode*/int getTextBoxWrapMode() { return (/*TextBoxWrapMode*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.TEXTBOX_WRAP_MODE); }
    public /*override*/ void setTextBoxWrapMode(/*TextBoxWrapMode*/int value) { mShape.setShapeAttrInternal(ShapeAttr.TEXTBOX_WRAP_MODE, value); }

    /// <summary>
    /// Gets or sets a boolean value indicating either text of the TextBox should not rotate when the shape is rotated.
    /// </summary>
    /*override*/ boolean getTextBoxNoTextRotation() { return (/*boolean*/Boolean)mShape.fetchShapeAttrInternal(ShapeAttr.TEXTBOX_ROTATE_TEXT); }
    /*override*/ void setTextBoxNoTextRotation(boolean value) { mShape.setShapeAttrInternal(ShapeAttr.TEXTBOX_ROTATE_TEXT, value); }

    /*override*/ /*TextBoxAnchor*/int getTextBoxAnchor() { return (/*TextBoxAnchor*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.TEXTBOX_ANCHOR); }
    /*override*/ void setTextBoxAnchor(/*TextBoxAnchor*/int value) { mShape.setShapeAttrInternal(ShapeAttr.TEXTBOX_ANCHOR, value); }
    //<<<<<<<< #endregion 

    /*override*/ int getCoordSizeWidth() { return (/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.COORD_SIZE_WIDTH); }

    /*override*/ int getCoordSizeHeight() { return (/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.COORD_SIZE_HEIGHT); }

    /*override*/ int getCoordOriginX() { return (/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.COORD_ORIGIN_X); }
    /*override*/ void setCoordOriginX(int value) { mShape.setShapeAttrInternal(ShapeAttr.COORD_ORIGIN_X, value); }

    /*override*/ int getCoordOriginY() { return (/*int*/Integer)mShape.fetchShapeAttrInternal(ShapeAttr.COORD_ORIGIN_Y); }
    /*override*/ void setCoordOriginY(int value) { mShape.setShapeAttrInternal(ShapeAttr.COORD_ORIGIN_Y, value); }

    /*override*/ boolean getAspectRatioLocked()
    {
        ShapeBase topLevelShape = mShape.getTopLevelParentShape();

        // WORDSNET-13812 Returns direct or inherited from shape type LockAspectRatio value.
        return (/*boolean*/Boolean)topLevelShape.fetchShapeAttrInternal(ShapeAttr.LOCK_ASPECT_RATIO);
    }
    /*override*/ void setAspectRatioLocked(boolean value) { mShape.setShapeAttrInternal(ShapeAttr.LOCK_ASPECT_RATIO, value); }

    /// <summary>
    /// Provides access to the properties of the horizontal rule shape.
    /// </summary>
    /*override*/ HorizontalRule getHorizontalRule()
    {
        if (mHorizontalRuleCache == null)
            mHorizontalRuleCache = new HorizontalRule(mShape);
        return mHorizontalRuleCache;
    }

    /// <summary>
    /// Gets or sets <see cref="Aspose.Words.Drawing.ShadowType"/> object for a shape.
    /// </summary>
    /*override*/ /*ShadowType*/int getShadowType()
    {
        ShapePr shapePr = mShape.getShapePr();
        if ((shapePr.get(ShapeAttr.SHADOW_ON) == null) || !(/*boolean*/Boolean)shapePr.get(ShapeAttr.SHADOW_ON) ||
            (shapePr.get(ShapeAttr.SHADOW_SCALE_XTO_Y) != null) || (shapePr.get(ShapeAttr.SHADOW_PERSPECTIVE_X) != null))
            return ShadowType.SHADOW_MIXED;

        for (/*ShadowType*/int type : (Iterable</*ShadowType*/Integer>) getShapeTypeVmlDefaults().keySet())
        {
            Object[] shapeTypeVmlDefaults = getShapeTypeVmlDefaults().get(type);
            if (msObject.equals(shapePr.get(ShapeAttr.SHADOW_TYPE), shapeTypeVmlDefaults[0]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_HIGHLIGHT), shapeTypeVmlDefaults[1]) &&
                msObject.equals(shapePr.get(ShapeAttr.IMAGE_DBL_CR_MOD), shapeTypeVmlDefaults[2]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_OFFSET_X), shapeTypeVmlDefaults[4]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_OFFSET_Y), shapeTypeVmlDefaults[5]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_SCALE_XTO_X), shapeTypeVmlDefaults[6]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_SECOND_OFFSET_X), shapeTypeVmlDefaults[7]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_SECOND_OFFSET_Y), shapeTypeVmlDefaults[8]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_SCALE_YTO_X), shapeTypeVmlDefaults[9]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_SCALE_YTO_Y), shapeTypeVmlDefaults[10]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_ORIGIN_X), shapeTypeVmlDefaults[11]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_ORIGIN_Y), shapeTypeVmlDefaults[12]) &&
                msObject.equals(shapePr.get(ShapeAttr.SHADOW_PERSPECTIVE_Y), shapeTypeVmlDefaults[13]))
                return type;
        }

        return ShadowType.SHADOW_MIXED;
    }
    /*override*/ void setShadowType(/*ShadowType*/int value)
    {
        Object[] shapeTypeVmlDefaults = null;
        Ref<Object[]> referenceToShapeTypeVmlDefaults = new Ref<Object[]>(shapeTypeVmlDefaults);
        boolean outRefCondition0 = !msDictionary.tryGetValue(getShapeTypeVmlDefaults(), value, /*out*/ referenceToShapeTypeVmlDefaults);
        shapeTypeVmlDefaults = referenceToShapeTypeVmlDefaults.get();
        if (outRefCondition0)
            return;

        mShape.getShapePr().set(ShapeAttr.SHADOW_ON, true);
        setAttrSafe(ShapeAttr.SHADOW_TYPE, shapeTypeVmlDefaults[0]);
        setAttrSafe(ShapeAttr.SHADOW_HIGHLIGHT, shapeTypeVmlDefaults[1]);
        setAttrSafe(ShapeAttr.IMAGE_DBL_CR_MOD, shapeTypeVmlDefaults[2]);
        setAttrSafe(ShapeAttr.SHADOW_OPACITY, shapeTypeVmlDefaults[3]);
        setAttrSafe(ShapeAttr.SHADOW_OFFSET_X, shapeTypeVmlDefaults[4]);
        setAttrSafe(ShapeAttr.SHADOW_OFFSET_Y, shapeTypeVmlDefaults[5]);
        setAttrSafe(ShapeAttr.SHADOW_SCALE_XTO_X, shapeTypeVmlDefaults[6]);
        setAttrSafe(ShapeAttr.SHADOW_SECOND_OFFSET_X, shapeTypeVmlDefaults[7]);
        setAttrSafe(ShapeAttr.SHADOW_SECOND_OFFSET_Y, shapeTypeVmlDefaults[8]);
        setAttrSafe(ShapeAttr.SHADOW_SCALE_YTO_X, shapeTypeVmlDefaults[9]);
        setAttrSafe(ShapeAttr.SHADOW_SCALE_YTO_Y, shapeTypeVmlDefaults[10]);
        setAttrSafe(ShapeAttr.SHADOW_ORIGIN_X, shapeTypeVmlDefaults[11]);
        setAttrSafe(ShapeAttr.SHADOW_ORIGIN_Y, shapeTypeVmlDefaults[12]);
        setAttrSafe(ShapeAttr.SHADOW_PERSPECTIVE_Y, shapeTypeVmlDefaults[13]);
    }

    /// <summary>
    /// Gets or sets color of a shadow effect.
    /// </summary>
    /*override*/ Color getShadowColor()
    {
        Object color = mShape.getShapePr().get(ShapeAttr.SHADOW_COLOR);
        if (color == null)
            return Color.BLACK;

        DrColor drColor = (DrColor)color;
        Object opacity = mShape.getShapePr().get(ShapeAttr.SHADOW_OPACITY);
        if (opacity != null)
        {
            int alpha = (int)msMath.round(((/*int*/Integer)opacity * 255 / 65535.0));
            drColor = DrColor.fromArgb(alpha, drColor.getR(), drColor.getG(), drColor.getB());
        }

        return drColor.toNativeColor();
    }
    /*override*/ void setShadowColor(Color value)
    {
        mShape.setShapeAttrInternal(ShapeAttr.SHADOW_COLOR, DrColor.fromNativeColor(value));
    }

    /// <summary>
    /// Gets or sets transparency of a shadow effect.
    /// </summary>
    /*override*/ double getShadowTransparency()
    {
        Object opacity = mShape.getShapePr().get(ShapeAttr.SHADOW_OPACITY);
        if (opacity == null)
            return 0.0;

        return 1.0 - (/*int*/Integer)opacity / 65535.0;
    }
    /*override*/ void setShadowTransparency(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Shadow.Transparency");

        int opacity = (int)msMath.round((1.0 - value) * 65535.0);
        mShape.setShapeAttrInternal(ShapeAttr.SHADOW_OPACITY, opacity);         
    }

    /// <summary>
    /// Returns VML attribute default values for the corresponding shadow types.
    /// </summary>
    private static HashMap<Integer, Object[]> getShapeTypeVmlDefaults()
    {
        if (gShapeTypeVmlPresets == null)
        {
            gShapeTypeVmlPresets = new HashMap<Integer, Object[]>();

            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_1, new Object[]
                { null, null, null, 32768, -76200, -76200, null, null, null, null, null, null, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_2, new Object[]
                { null, null, null, 32768, 76200, -76200, null, null, null, null, null, null, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_3, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, 56756, 32768, null, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_4, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, -56756, 32768, null, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_5, new Object[]
                { null, null, null, 32768, -76200, 76200, null, null, null, null, null, null, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_6, new Object[]
                { null, null, null, 32768, 76200, 76200, null, null, null, null, null, null, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_7, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, 56756, -32768, null, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_8, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, -56756, -32768, null, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_9, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, -76200, -76200, 49152, null, null, null, 49152, -32768, -32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_10, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, -76200, -76200, 81920, null, null, null, 81920, 32768, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_11, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, 92680, null, -32768, 32768, -16 });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_12, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, -92680, null, 32768, 32768, -16 });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_13, new Object[] { ShadowTypeCore.DOUBLE,
                DrColor.fromArgb(0xEF, 0xF3, 0x03, 0x66), null, 32768, -38100, -38100, null, -76200, -76200, null,
                null, null, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_14, new Object[]
                { null, null, null, 32768, null, null, null, null, null, null, null, null, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_15, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, 92680, -65536, -32768, 32768, -16 });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_16, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, -92680, -65536, 32768, 32768, -16 });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_17, new Object[] { ShadowTypeCore.EMBOSS_OR_ENGRAVE, DrColor.fromArgb(0xEF,
                    0xF3, 0x03, 0x66), DrColor.fromArgb(0xEF, 0xF3, 0x03, 0x33), 32768, 12700, 12700, null,
                -12700, -12700, null, null, null, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_18, new Object[] { ShadowTypeCore.EMBOSS_OR_ENGRAVE, DrColor.fromArgb(0xEF,
                    0xF3, 0x03, 0x66), DrColor.fromArgb(0xEF, 0xF3, 0x03, 0x33), 32768, -12700, -12700, null, 12700,
                12700, null, null, null, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_19, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, null, 32768, null, 32768, -8 });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_20, new Object[]
                { ShadowTypeCore.RICH, null, null, 32768, 0, 0, null, null, null, null, -65536, null, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_21, new Object[]
                { null, null, null, 26214, 26941, 26941, null, null, null, null, null, -32768, -32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_22, new Object[]
                { null, null, null, 26214, 0, 38100, null, null, null, null, null, null, -32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_23, new Object[]
                { null, null, null, 26214, -26941, 26941, null, null, null, null, null, 32768, -32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_24, new Object[]
                { null, null, null, 26214, 38100, 0, null, null, null, null, null, -32768, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_25, new Object[]
                { null, null, null, 26214, 0, 0, 66847, null, null, null, 66847, null, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_26, new Object[]
                { null, null, null, 26214, -38100, 0, null, null, null, null, null, 32768, null, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_27, new Object[]
                { null, null, null, 26214, 26941, -26941, null, null, null, null, null, -32768, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_28, new Object[]
                { null, null, null, 26214, 0, -38100, null, null, null, null, null, null, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_29, new Object[]
                { null, null, null, 26214, -26941, -26941, null, null, null, null, null, 32768, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_39, new Object[]
                { ShadowTypeCore.RICH, null, null, 13107, 0, 0, null, null, null, 23853, 15073, 32768, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_40, new Object[]
                { ShadowTypeCore.RICH, null, null, 13107, 0, 0, null, null, null, -23853, 15073, -32768, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_41, new Object[]
                { ShadowTypeCore.RICH, null, null, 9830, 0, 317500, 58982, null, null, null, -12452, null, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_42, new Object[]
                { ShadowTypeCore.RICH, null, null, 13107, -8980, 8980, null, null, null, 15540, -15073, 32768, 32768, null });
            msDictionary.add(gShapeTypeVmlPresets, ShadowType.SHADOW_43, new Object[]
                { ShadowTypeCore.RICH, null, null, 13107, 8980, 8980, null, null, null, -15540, -15073, -32768, 32768, null });
        }

        return gShapeTypeVmlPresets;
    }

    /// <summary>
    /// Default values for VML attributes for the respective shadow types.
    /// </summary>
    private static HashMap<Integer, Object[]> gShapeTypeVmlPresets;

    private OleFormat mOleFormatCache;
    private HorizontalRule mHorizontalRuleCache;
    private TextPath mTextPathCache;
    private AdjustmentCollection mAdjustments;
}

