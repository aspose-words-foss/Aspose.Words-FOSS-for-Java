// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ConvertUtilCore;
import com.aspose.MathUtil;
import java.util.ArrayList;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.GeometryUtil;
import com.aspose.ArgumentUtil;
import com.aspose.FormatterPal;
import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.msString;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.ms.System.Drawing.msColor;


/// <summary>
/// Base class for concrete Dml shape object.
/// </summary>
abstract class DmlNode extends Graphic implements IDmlExtensionListSource, Cloneable
{
    /// <summary>
    /// Call this method immediately when set DmlNode property of DrawingML.
    /// </summary>
    void setDrawingML(ShapeBase dml)
    {
        mShape = dml;
    }

    protected void setDmlTransform(DmlTransform transform)
    {
        // If DrawingML is not top level we must set its dimensions from transform.
        // This is required to be able render nested dmls.
        if ((mShape != null) && !mShape.isTopLevel())
        {
            ShapePr shapePr = mShape.getShapePr();
            shapePr.setAttr(ShapeAttr.LEFT, transform.getX());
            shapePr.setAttr(ShapeAttr.TOP, transform.getY());
            shapePr.setAttr(ShapeAttr.WIDTH, transform.getWidth());
            shapePr.setAttr(ShapeAttr.HEIGHT, transform.getHeight());
        }
    }

    /*override*/ void setWidthCore(double value, boolean isThrow)
    {
        double validWidth = ShapeSizeValidationHelper.validateDimension(mShape, value, isThrow, "width");
        mShape.setShapeAttrInternal(ShapeAttr.WIDTH, validWidth);
        mShape.setShapeAttrInternal(ShapeAttr.GRAPHIC_FRAME_EXT_WIDTH, (double)ConvertUtilCore.pointToEmu(validWidth));

        // WORDSNET-16239 Convert to EMUs only if shape is not in group.
        if (getDml().isTopLevel())
            validWidth = ConvertUtilCore.pointToEmu(validWidth);

        getTransform().setWidth(MathUtil.doubleToInt(validWidth));
    }

    /*override*/ void setHeightCore(double value, boolean isThrow)
    {
        double validHeight = ShapeSizeValidationHelper.validateDimension(mShape, value, isThrow, "height");
        mShape.setShapeAttrInternal(ShapeAttr.HEIGHT, validHeight);
        mShape.setShapeAttrInternal(ShapeAttr.GRAPHIC_FRAME_EXT_HEIGHT, (double)ConvertUtilCore.pointToEmu(validHeight));

        // WORDSNET-16239 Convert to EMUs only if shape is not in group.
        if (getDml().isTopLevel())
            validHeight = ConvertUtilCore.pointToEmu(validHeight);

        getTransform().setHeight(MathUtil.doubleToInt(validHeight));
    }

    /*override*/ void setCoordSizeWidthSafe(int width)
    {
        DmlGroupTransform groupTransform = ms.as(getTransform(), DmlGroupTransform.class);
        if (groupTransform != null)
            groupTransform.setChildWidth(width);
    }

    /*override*/ void setCoordSizeHeightSafe(int height)
    {
        DmlGroupTransform groupTransform = ms.as(getTransform(), DmlGroupTransform.class);
        if (groupTransform != null)
            groupTransform.setChildHeight(height);
    }

    /// <summary>
    /// Removes shadow effect from a shape.
    /// </summary>
    /*override*/ void removeShadow()
    {
        DmlShapeBase dmlShapeBase = ms.as(this, DmlShapeBase.class);
        if ((dmlShapeBase != null) && (dmlShapeBase.getEffects() != null))
        {
            dmlShapeBase.getEffects().removeEffect(DmlShapeEffectType.PRESET_SHADOW);
            dmlShapeBase.getEffects().removeEffect(DmlShapeEffectType.INNER_SHADOW);
            dmlShapeBase.getEffects().removeEffect(DmlShapeEffectType.OUTER_SHADOW);

            if (dmlShapeBase.getEffects().getCount() == 0)
                dmlShapeBase.setEffects(null);
        }
    }

    protected /*virtual*/ void initializeTransform()
    {
        mTransform = new DmlTransform();
    }

    /// <summary>
    /// Returns false if dimensions of the shape should not be updated from relative values.
    /// </summary>
    /*virtual*/ boolean isUpdateDimensionsFromRelative() { return true; }

    /*virtual*/ DmlNode deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        // This creates a node of the correct class and bitwise copies all fields.
        DmlNode lhs = (DmlNode)memberwiseClone();

        if (mNonVisualPr != null)
            lhs.setNonVisualPr(mNonVisualPr.deepClone());

        if (mTransform != null)
            lhs.mTransform = mTransform.deepClone();

        lhs.mExtensions = DmlExtensionListSource.cloneExtensions(mExtensions);

        // The mModelId field is skipped since it is immutable.

        return lhs;
    }

    abstract /*DmlNodeType*/int getDmlNodeType();

    private double getInternalMargin(int key)
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
        {
            DmlTextBodyProperties bodyPr = ((DmlShape)this).getTextBodyPr();

            switch (key)
            {
                case ShapeAttr.TEXTBOX_LEFT:
                    return ConvertUtilCore.emuToPoint(bodyPr.getLeftInset());
                case ShapeAttr.TEXTBOX_TOP:
                    return ConvertUtilCore.emuToPoint(bodyPr.getTopInset());
                case ShapeAttr.TEXTBOX_RIGHT:
                    return ConvertUtilCore.emuToPoint(bodyPr.getRightInset());
                case ShapeAttr.TEXTBOX_BOTTOM:
                    return ConvertUtilCore.emuToPoint(bodyPr.getBottomInset());
                default:
                    break;
            }
        }

        return 0.0;
    }

    private void setInternalMargin(int key, double value)
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
        {
            int intValue = ConvertUtilCore.pointToEmu(value);

            DmlShape dmlShape = (DmlShape)this;

            if (dmlShape.getTextShape() == null)
                dmlShape.setTextShape(new DmlTextShape());

            switch (key)
            {
                case ShapeAttr.TEXTBOX_LEFT:
                    dmlShape.getTextBodyPr().setLeftInset(intValue);
                    break;
                case ShapeAttr.TEXTBOX_TOP:
                    dmlShape.getTextBodyPr().setTopInset(intValue);
                    break;
                case ShapeAttr.TEXTBOX_RIGHT:
                    dmlShape.getTextBodyPr().setRightInset(intValue);
                    break;
                case ShapeAttr.TEXTBOX_BOTTOM:
                    dmlShape.getTextBodyPr().setBottomInset(intValue);
                    break;
                default:
                    break;
            }
        }
    }

    private double getDmlLuminanceEffectValue(int key)
    {
        if (getDmlNodeType() == DmlNodeType.PICTURE)
        {
            DmlBlip dmlBlip = ((DmlPicture)this).getBlipFill().getBlip();

            if (dmlBlip.getEffects().size() > 0)
            {
                for (DmlEffect effect : dmlBlip.getEffects())
                {
                    if (effect.getType() == DmlEffectType.LUMINANCE)
                    {
                        switch (key)
                        {
                            case ShapeAttr.IMAGE_BRIGHTNESS:
                                return ((DmlLuminanceEffect)effect).getBrightness();
                            case ShapeAttr.IMAGE_CONTRAST:
                                return ((DmlLuminanceEffect)effect).getContrast();
                            default:
                                break;
                        }
                    }
                }
            }
        }

        //The default value is 0.5.
        return 0.5;
    }

    private void setDmlLuminanceEffect(int key, double value)
    {
        if (getDmlNodeType() == DmlNodeType.PICTURE)
        {
            DmlBlipFill fill = ((DmlPicture)this).getBlipFill();
            if (fill != null)
            {
                DmlBlip blip = fill.getBlip();
                DmlLuminanceEffect effect = null;

                // Check if this effect is already applied. If yes, then just set new brightness/contrast.
                for (int i = 0; i < blip.getEffects().size(); i++)
                {
                    DmlEffect dmlEffect = blip.getEffects().get(i);
                    if (dmlEffect.getType() == DmlEffectType.LUMINANCE)
                    {
                        effect = (DmlLuminanceEffect)blip.getEffects().get(i);
                        break;
                    }
                }

                if (effect == null)
                {
                    // Create new DmlLuminanceEffect add to effects collection, and then set new brightness/contrast.
                    effect = new DmlLuminanceEffect();
                    blip.getEffects().add(effect);
                }

                // Set brightness/contrast.
                switch (key)
                {
                    case ShapeAttr.IMAGE_BRIGHTNESS:
                        effect.setBrightness(value);
                        break;
                    case ShapeAttr.IMAGE_CONTRAST:
                        effect.setContrast(value);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private boolean getDmlImageEffectValue(/*DmlEffectType*/int type)
    {
        if (getDmlNodeType() == DmlNodeType.PICTURE)
        {
            DmlBlip dmlBlip = ((DmlPicture)this).getBlipFill().getBlip();

            if (dmlBlip.getEffects().size() > 0)
            {
                for (DmlEffect effect : dmlBlip.getEffects())
                {
                    if (effect.getType() == type)
                        return true;
                }
            }
        }

        return false;
    }

    private void setDmlImageEffects(/*DmlEffectType*/int type, Object value)
    {
        // andrnosk: WORDSNET-12025 Apply or remove DmlGrayScaleEffect.
        if (getDmlNodeType() == DmlNodeType.PICTURE)
        {
            DmlBlipFill fill = ((DmlPicture)this).getBlipFill();
            if (fill != null)
            {
                ArrayList<DmlEffect> dmlBlipEffects = fill.getBlip().getEffects();

                if ((/*boolean*/Boolean)value)
                {
                    DmlEffect dmlEffect = null;

                    switch (type)
                    {
                        case DmlEffectType.GRAY_SCALE:
                            dmlEffect = new DmlGrayScaleEffect();
                            break;
                        case DmlEffectType.BI_LEVEL:
                            {
                                dmlEffect = new DmlBiLevelEffect();

                                // In MS Word you can set 25, 50, 75 percent, so i think 50 looks good as default value.
                                ((DmlBiLevelEffect)dmlEffect).setThreshold(0.5);
                                break;
                            }
                        default:
                            break;
                    }

                    if (dmlEffect != null)
                        dmlBlipEffects.add(dmlEffect);
                }
                else
                {
                    // If false, remove this effect.
                    for (int i = 0; i < dmlBlipEffects.size(); i++)
                    {
                        DmlEffect dmlEffect = dmlBlipEffects.get(i);
                        if (dmlEffect.getType() == type)
                        {
                            dmlBlipEffects.remove(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    private double getCrop(int key)
    {
        if (getDmlNodeType() == DmlNodeType.PICTURE)
        {
            DmlBlipFill dmlBlipFill = ((DmlPicture)this).getBlipFill();
            DmlPercentageOffsetRectangle sRec = dmlBlipFill.getSourceRectangle();

            switch (key)
            {
                case ShapeAttr.IMAGE_CROP_TOP:
                    return validateValue(sRec.getTopOffset());
                case ShapeAttr.IMAGE_CROP_BOTTOM:
                    return validateValue(sRec.getBottomOffset());
                case ShapeAttr.IMAGE_CROP_LEFT:
                    return validateValue(sRec.getLeftOffset());
                case ShapeAttr.IMAGE_CROP_RIGHT:
                    return validateValue(sRec.getRightOffset());
                default:
                    return 0.0;
            }
        }

        return 0.0;
    }

    private void setCropAttr(int key, double value)
    {
        DmlPercentageOffsetRectangle srOffsetRectangle = getOffsetRectangle();
        if (srOffsetRectangle == null)
            return;

        switch (key)
        {
            case ShapeAttr.IMAGE_CROP_TOP:
                srOffsetRectangle.setTopOffset(validateValue(value));
                break;
            case ShapeAttr.IMAGE_CROP_BOTTOM:
                srOffsetRectangle.setBottomOffset(validateValue(value));
                break;
            case ShapeAttr.IMAGE_CROP_LEFT:
                srOffsetRectangle.setLeftOffset(validateValue(value));
                break;
            case ShapeAttr.IMAGE_CROP_RIGHT:
                srOffsetRectangle.setRightOffset(validateValue(value));
                break;
            default:
                break;
        }
    }

    private DmlPercentageOffsetRectangle getOffsetRectangle()
    {
        if (getDmlNodeType() != DmlNodeType.PICTURE)
            return null;

        DmlBlipFill fill = ((DmlPicture)this).getBlipFill();
        return fill.getSourceRectangle();
    }

    private static double validateValue(double value)
    {
        // Fix of issue WORDSNET-13552 uses crop instead of clipping canvas,
        // and rounding to 2 digits produces incorrect output. So increases number of digits after dot to 5 for proper output.
        return msMath.round(value, 5, MidpointRounding.AWAY_FROM_ZERO);
    }

    /// <summary>
    /// Update EffectExtent upon rotation. Taking existing effect extents in account.
    /// </summary>
    private void updateEffectExtent(double value)
    {
        // The code below is not really calculating EffectExtent.
        // It's just a stub to avoid issues for some rotation changing, but a lot of cases are broken.
        double normAngle = MathUtil.normalizeAngle(value);
        RectangleF sizeBox = mShape.getsSizeBoxForBounds(normAngle);

        // Bounds should be rotated by an angle equal to the difference between
        // the previous shape's rotation angle and new value.
        float rotate = (float)normAngle - (float)mShape.getRotation();
        RectangleF rotatedBounds = GeometryUtil.getRotatedRectangleBounds(mShape.getBoundsWithEffectsInternal(), rotate);

        int left = ConvertUtilCore.pointToEmu(Math.max((int)(sizeBox.getLeft() - rotatedBounds.getLeft()), 0));
        int top = ConvertUtilCore.pointToEmu(Math.max((int)(sizeBox.getTop() - rotatedBounds.getTop()), 0));
        int right = ConvertUtilCore.pointToEmu(Math.max((int)(rotatedBounds.getRight() - sizeBox.getRight()), 0));
        int bottom = ConvertUtilCore.pointToEmu(Math.max((int)(rotatedBounds.getBottom() - sizeBox.getBottom()), 0));

        mShape.setShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_LEFT, left);
        mShape.setShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_TOP, top);
        mShape.setShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_RIGHT, right);
        mShape.setShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_BOTTOM, bottom);
    }

    /// <summary>
    /// Gets the collection of adjustment values that are applied to the shape.
    /// </summary>
    private AdjustmentCollection getAdjustmentCollection()
    {
        AdjustmentCollection adjustments = new AdjustmentCollection();

        DmlGuides dmlGuides = getDml().getDmlShape().getGeometry().getGuides();
        for (DmlGuide guide : dmlGuides.getAdjustableValues())
        {
            DmlOneArgumentFormula formula = ms.as(guide.getFormula(), DmlOneArgumentFormula.class);
            ArgumentUtil.checkNotNull(formula, "formula");

            String name = guide.getName();
            int value = FormatterPal.tryParseInt(formula.getX());

            // If there are no custom values available, we will use preset values to work
            // with in order to prevent any updates to the collection during the work.
            // However, if custom values are available, we will use those instead.
            if (!dmlGuides.hasCustomAdjustableValues())
                adjustments.add(new Adjustment(name, value, formula, mShape));
            else
            {
                if (!guide.isPreset())
                    adjustments.add(new Adjustment(name, value, formula, mShape));
            }
        }

        return adjustments;
    }

    /// <summary>
    /// Tries set specified shadow type to a specified effect.
    /// </summary>
    private boolean trySetShadowType(/*DmlShapeEffectType*/int effectType, /*ShadowType*/int shadowType)
    {
        DmlShapeBase dmlShapeBase = ms.as(this, DmlShapeBase.class);
        if (dmlShapeBase == null)
            return false;

        boolean hasEffects = (dmlShapeBase.getEffects() != null);

        DmlShapeEffect effect = null;
        if (hasEffects)
            effect = dmlShapeBase.getEffects().getByDmlShapeEffectType(effectType);

        boolean hasEffect = (effect != null);
        if (!hasEffect)
            effect = DmlShapeEffect.createEffect(effectType);

        if (!effect.trySetShadowType(shadowType))
            return false;

        if (!hasEffects)
            dmlShapeBase.setEffects(new DmlShapeEffectsCollection());

        if (!hasEffect)
            dmlShapeBase.getEffects().addEffect(effect);

        return true;
    }

    /// <summary>
    /// Represents extLst: a CT_OfficeArtExtensionList([ISO/IEC29500-1:2012] section A.4.1) element that specifies
    /// the extension list in which all future extensions of element type ext is defined.
    /// </summary>
    public StringToObjDictionary<DmlExtension> getExtensions() { return mExtensions; }
    public void setExtensions(StringToObjDictionary<DmlExtension> value) { mExtensions = value; }

    public StringToObjDictionary<DmlExtension> getDocPrExtensions() { return mDocPrExtensions; }
    public void setDocPrExtensions(StringToObjDictionary<DmlExtension> value) { mDocPrExtensions = value; }

    public /*override*/ String getName()
    {
        if (!mShape.isTopLevel())
        {
            if (getNonVisualPr().getNvDrawingProperties() != null)
                return getNonVisualPr().getNvDrawingProperties().getName();

            return "";
        }

        return (String)mShape.fetchShapeAttrInternal(ShapeAttr.SHAPE_NAME);
    }
    public /*override*/ void setName(String value)
    {
        if (!mShape.isTopLevel())
        {
            if (getNonVisualPr().getNvDrawingProperties() == null)
                getNonVisualPr().setNvDrawingProperties(new DmlNvDrawingProperties());

            getNonVisualPr().getNvDrawingProperties().setName(value);
        }

        mShape.setShapeAttrInternal(ShapeAttr.SHAPE_NAME, value);
    }

    public /*override*/ String getAlternativeText()
    {
        if (!mShape.isTopLevel())
        {
            if (getNonVisualPr().getNvDrawingProperties() != null)
                return getNonVisualPr().getNvDrawingProperties().getDescription();

            return "";
        }

        return (String)mShape.fetchShapeAttrInternal(ShapeAttr.SHAPE_DESCRIPTION);
    }
    public /*override*/ void setAlternativeText(String value)
    {
        if (!mShape.isTopLevel())
        {
            if (getNonVisualPr().getNvDrawingProperties() == null)
                getNonVisualPr().setNvDrawingProperties(new DmlNvDrawingProperties());

            getNonVisualPr().getNvDrawingProperties().setDescription(value);
        }

        mShape.setShapeAttrInternal(ShapeAttr.SHAPE_DESCRIPTION, value);
    }

    public /*override*/ boolean getDecorative()
    {
        if (!msString.equals((String)getDml().fetchShapeAttrInternal(ShapeAttr.SHAPE_DESCRIPTION), ""))
            return false;

        return (getDocPrExtensions() != null) &&
               (getDocPrExtensions().get(DmlExtensionUri.DECORATIVE) != null) &&
               getDocPrExtensions().get(DmlExtensionUri.DECORATIVE).getDecorative();
    }

    public /*override*/ void setDecorative(boolean value)
    {
        if (getDocPrExtensions() == null)
            setDocPrExtensions(new StringToObjDictionary<DmlExtension>());
        DmlExtension ext = DmlExtension.getOrCreateExtension(getDocPrExtensions(), DmlExtensionUri.DECORATIVE);
        ext.setDecorative(value);
    }

    public /*override*/ String getTitle()
    {
        if (!mShape.isTopLevel())
        {
            if (getNonVisualPr().getNvDrawingProperties() != null)
                return getNonVisualPr().getNvDrawingProperties().getTitle();

            return "";
        }

        return (String)mShape.fetchShapeAttrInternal(ShapeAttr.SHAPE_TITLE);
    }
    public /*override*/ void setTitle(String value)
    {
        if (!mShape.isTopLevel())
        {
            if (getNonVisualPr().getNvDrawingProperties() == null)
                getNonVisualPr().setNvDrawingProperties(new DmlNvDrawingProperties());

            getNonVisualPr().getNvDrawingProperties().setTitle(value);
        }

        mShape.setShapeAttrInternal(ShapeAttr.SHAPE_TITLE, value);
    }

    /*override*/ boolean getHidden()
    {
        // WORDSNET-16976 Take "Hidden" value from non-visual properties for nested shapes.
        if (!mShape.isTopLevel() && (getNonVisualPr() != null) && (getNonVisualPr().getNvDrawingProperties() != null))
                return getNonVisualPr().getNvDrawingProperties().getHidden();

        return (/*boolean*/Boolean)mShape.fetchShapeAttrInternal(ShapeAttr.HIDDEN);
    }

    /*override*/ void setHidden(boolean value)
    {
        if (!mShape.isTopLevel())
        {
            if (getNonVisualPr().getNvDrawingProperties() == null)
                getNonVisualPr().setNvDrawingProperties(new DmlNvDrawingProperties());

            getNonVisualPr().getNvDrawingProperties().setHidden(value);
        }
        // This attributes has to be set for nested and top level shapes, due to currently DML->VML
        // conversion does not take in attention the "Hidden" property of the non-visual properties,
        // it just copy attributes. Probably, after fixing this conversion, it will be possible to avoid
        // set of the attribute below.
        mShape.setShapeAttrInternal(ShapeAttr.HIDDEN, value);
    }

    /// <summary>
    /// Vml shape related only.
    /// </summary>
    public /*override*/ boolean isHorizontalRule() { return false; }

    /// <summary>
    /// Vml shape related only.
    /// </summary>
    public /*override*/ boolean isWordArt() { return false; }

    /// <summary>
    /// Vml shape related only.
    /// </summary>
    public /*override*/ OleFormat getOleFormat() { return null; }

    /// <summary>
    /// Switches the orientation of a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.FlipOrientation.None"/>.</p>
    /// </remarks>
    public /*override*/ /*FlipOrientation*/int getFlipOrientation() { return getTransform().getFlipOrientation(); }
    public /*override*/ void setFlipOrientation(/*FlipOrientation*/int value) { getTransform().setFlipOrientation(value); }

    /// <summary>
    /// Defines the angle (in degrees) that a shape is rotated.
    /// Positive value corresponds to clockwise rotation angle.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public /*override*/ double getRotation() { return getTransform().getRotation().getValueInDegrees(); }
    public /*override*/ void setRotation(double value)
    {
        // Previous rotation angle should be taken into account.
        updateEffectExtent(value);

        getTransform().setRotation(DmlAngle.fromDegrees(value));
    }

    /// <summary>
    /// Represents the avLst element that specifies a list of shape adjust values ([ISO/IEC29500-1:2012] section 20.1.9.5).
    /// </summary>
    public /*override*/ AdjustmentCollection getAdjustments()
    {
        if (mAdjustments == null)
            mAdjustments = getAdjustmentCollection();

        return mAdjustments;
    }

    /// <summary>
    /// Vml shape related only.
    /// </summary>
    public /*override*/ TextPath getTextPath()
    {
        // Dml shape cannot be WordArt, but to avoid exception we return
        // the new TextPath (the same we do for example for all other VML shapes).
        return new TextPath(mShape);
    }

    /// <summary>
    /// Returns true if an extrusion effect is enabled.
    /// </summary>
    public /*override*/ boolean getExtrusionEnabled()
    {
        IDmlShapePrSource source = ms.as(this, IDmlShapePrSource.class);
        if (source != null)
        {
            DmlShape3DProperties shape3DProperties = source.getShape3DProperties();
            DmlScene3DProperties scene3DProperties = source.getScene3DProperties();

            return ((shape3DProperties != null) || (scene3DProperties != null));
        }

        return false;
    }

    /// <summary>
    /// Returns true if a shadow effect is enabled.
    /// </summary>
    public /*override*/ boolean getShadowEnabled()
    {
        DmlFillableNode fillableNode = ms.as(this, DmlFillableNode.class);
        if ((fillableNode != null) && (fillableNode.getEffects() != null))
        {
            for (DmlShapeEffect dmlShapeEffect : fillableNode.getEffects())
            {
                if ((dmlShapeEffect.getEffectType() == DmlShapeEffectType.INNER_SHADOW) ||
                    (dmlShapeEffect.getEffectType() == DmlShapeEffectType.OUTER_SHADOW) ||
                    (dmlShapeEffect.getEffectType() == DmlShapeEffectType.PRESET_SHADOW))
                    return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Gets the shape type.
    /// </summary>
    public /*override*/ /*ShapeType*/int getShapeType()
    {
        /*ShapeType*/int shapeType = ShapeType.NON_PRIMITIVE;

        if ((getDmlNodeType() == DmlNodeType.GROUP_SHAPE) ||
            (getDmlNodeType() == DmlNodeType.WORDPROCESSING_GROUP_SHAPE) ||
            (getDmlNodeType() == DmlNodeType.WORDPROCESSING_CANVAS) ||
            (getDmlNodeType() == DmlNodeType.SP_TREE) ||
            (getDmlNodeType() == DmlNodeType.LOCKED_CANVAS) ||
            (getDmlNodeType() == DmlNodeType.GRAPHIC_FRAME))
            return ShapeType.GROUP;

        if (getDmlNodeType() == DmlNodeType.PICTURE)
            return ShapeType.IMAGE;

        if ((getDmlNodeType() == DmlNodeType.SHAPE) ||
            (getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) ||
            (getDmlNodeType() == DmlNodeType.CONNECTOR_SHAPE))
        {
            shapeType = DmlEnum.dmlPresetGeomToShapeType(((DmlShape)this).getGeometry().getPresetName());

            if (mShape.hasTextbox() && (shapeType == ShapeType.RECTANGLE))
            {
                DmlCnvPrShape nvPr = (mNonVisualPr != null) && (mNonVisualPr.getCNvProperties() != null) ?
                    ms.as(getNonVisualPr().getCNvProperties(), DmlCnvPrShape.class) : null;

                // WORDSNET-17530 Take in account “txBox” attribute of the "cNvSpPr", while determining DML textbox.
                shapeType = (nvPr != null) && nvPr.getTextBox() ? ShapeType.TEXT_BOX : shapeType;
            }

        }

        return shapeType;
    }

    
    public DmlTransform getTransform()
    {
        if (mTransform == null)
            initializeTransform();

        return mTransform;
    }
    
    public void setTransform(DmlTransform value)
    {
        mTransform = value;
        setDmlTransform(mTransform);
    }

    //>>>>>>>> #region  ImageData properties.
    /// <summary>
    /// Gets or sets the brightness of the Dml picture.
    /// The value for this property must be a number from 0.0 (dimmest) to 1.0 (brightest).
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.5.</p>
    /// </remarks>
    public /*override*/ double getBrightness() { return getDmlLuminanceEffectValue(ShapeAttr.IMAGE_BRIGHTNESS); }
    public /*override*/ void setBrightness(double value) { setDmlLuminanceEffect(ShapeAttr.IMAGE_BRIGHTNESS, value); }

    /// <summary>
    /// Gets or sets the contrast for the specified Dml picture. The value
    /// for this property must be a number from 0.0 (the least contrast) to 1.0 (the greatest contrast).
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.5.</p>
    /// </remarks>
    public /*override*/ double getContrast() { return getDmlLuminanceEffectValue(ShapeAttr.IMAGE_CONTRAST); }
    public /*override*/ void setContrast(double value) { setDmlLuminanceEffect(ShapeAttr.IMAGE_CONTRAST, value); }

    /// <summary>
    /// Determines whether a Dml picture image will be displayed in black and white.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public /*override*/ boolean getBiLevel() { return getDmlImageEffectValue(DmlEffectType.BI_LEVEL); }
    public /*override*/ void setBiLevel(boolean value) { setDmlImageEffects(DmlEffectType.BI_LEVEL, value); }

    /// <summary>
    /// Determines whether a Dml picture will display in grayscale mode.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public /*override*/ boolean getGrayScale() { return getDmlImageEffectValue(DmlEffectType.GRAY_SCALE); }
    public /*override*/ void setGrayScale(boolean value) { setDmlImageEffects(DmlEffectType.GRAY_SCALE, value); }

    /// <summary>
    /// Defines the fraction of Dml picture removal from the top side.
    /// </summary>
    public /*override*/ double getCropTop() { return getCrop(ShapeAttr.IMAGE_CROP_TOP); }
    public /*override*/ void setCropTop(double value) { setCropAttr(ShapeAttr.IMAGE_CROP_TOP, value); }

    /// <summary>
    /// Defines the fraction of Dml picture removal from the bottom side.
    /// </summary>
    public /*override*/ double getCropBottom() { return getCrop(ShapeAttr.IMAGE_CROP_BOTTOM); }
    public /*override*/ void setCropBottom(double value) { setCropAttr(ShapeAttr.IMAGE_CROP_BOTTOM, value); }

    /// <summary>
    /// Defines the fraction of Dml picture removal from the left side.
    /// </summary>
    public /*override*/ double getCropLeft() { return getCrop(ShapeAttr.IMAGE_CROP_LEFT); }
    public /*override*/ void setCropLeft(double value) { setCropAttr(ShapeAttr.IMAGE_CROP_LEFT, value); }

    /// <summary>
    /// Defines the fraction of Dml picture removal from the right side.
    /// </summary>
    public /*override*/ double getCropRight() { return getCrop(ShapeAttr.IMAGE_CROP_RIGHT); }
    public /*override*/ void setCropRight(double value) { setCropAttr(ShapeAttr.IMAGE_CROP_RIGHT, value); }
    //<<<<<<<< #endregion 

    //>>>>>>>> #region  TextBox properties.
    /// <summary>
    /// Specifies the inner left margin in points for a TextShape of Shape or WordprocessingShape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/10 inch.</p>
    /// </remarks>
    public /*override*/ double getInternalMarginLeft() { return getInternalMargin(ShapeAttr.TEXTBOX_LEFT); }
    public /*override*/ void setInternalMarginLeft(double value) { setInternalMargin(ShapeAttr.TEXTBOX_LEFT, value); }

    /// <summary>
    /// Specifies the inner right margin in points for a TextShape of Shape or WordprocessingShape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/10 inch.</p>
    /// </remarks>
    public /*override*/ double getInternalMarginRight() { return getInternalMargin(ShapeAttr.TEXTBOX_RIGHT); }
    public /*override*/ void setInternalMarginRight(double value) { setInternalMargin(ShapeAttr.TEXTBOX_RIGHT, value); }

    /// <summary>
    /// Specifies the inner top margin in points for a TextShape of Shape or WordprocessingShape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/20 inch.</p>
    /// </remarks>
    public /*override*/ double getInternalMarginTop() { return getInternalMargin(ShapeAttr.TEXTBOX_TOP); }
    public /*override*/ void setInternalMarginTop(double value) { setInternalMargin(ShapeAttr.TEXTBOX_TOP, value); }

    /// <summary>
    /// Specifies the inner bottom margin in points for a TextShape of Shape or WordprocessingShape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/20 inch.</p>
    /// </remarks>
    public /*override*/ double getInternalMarginBottom() { return getInternalMargin(ShapeAttr.TEXTBOX_BOTTOM); }
    public /*override*/ void setInternalMarginBottom(double value) { setInternalMargin(ShapeAttr.TEXTBOX_BOTTOM, value); }

    /// <summary>
    /// Determines whether Microsoft Word will grow the TextShape to fit text.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public /*override*/ boolean getFitShapeToText()
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
        {
            DmlShapeAutoFitMode dmlShapeAutoFitMode = ms.as(((DmlShape)this).getTextBodyPr().getAutoFitMode(), DmlShapeAutoFitMode.class);
            if (dmlShapeAutoFitMode != null)
                return true;

        }

        return false;
    }
    public /*override*/ void setFitShapeToText(boolean value)
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
        {
            DmlShape dmlShape = (DmlShape)this;

            if (dmlShape.getTextShape() == null)
                dmlShape.setTextShape(new DmlTextShape());

            DmlTextBodyProperties textBodyPr = dmlShape.getTextBodyPr();
            if (value)
                textBodyPr.setAutoFitMode(new DmlShapeAutoFitMode());
            else
                textBodyPr.setAutoFitMode(new DmlNoAutoFitMode());
        }

    }

    /// <summary>
    /// Determines the flow of the text layout in a TextShape of Shape or WordprocessingShape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.LayoutFlow.Horizontal"/>.</p>
    /// </remarks>
    public /*override*/ /*LayoutFlow*/int getLayoutFlow()
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
            return DmlUtilCore.dmlToTextVerticalType(((DmlShape)this).getTextBodyPr().getTextOrientation(),
                ((DmlShape)this).getNormalEastAsianFlow());

        return LayoutFlow.HORIZONTAL;
    }
    public /*override*/ void setLayoutFlow(/*LayoutFlow*/int value)
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
        {
            DmlShape dmlShape = (DmlShape)this;

            if (dmlShape.getTextShape() == null)
                dmlShape.setTextShape(new DmlTextShape());

            dmlShape.getTextBodyPr().setTextOrientation(DmlUtilCore.textVerticalTypeToDml(value));
        }
    }

    /// <summary>
    /// Determines how text wraps inside a TextShape of Shape or WordprocessingShape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Words.Drawing.TextBoxWrapMode.Square"/>.</p>
    /// </remarks>
    public /*override*/ /*TextBoxWrapMode*/int getTextBoxWrapMode()
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
            return ((DmlShape)this).getTextBodyPr().getTextWrappingType();

        return TextBoxWrapMode.NONE;
    }
    public /*override*/ void setTextBoxWrapMode(/*TextBoxWrapMode*/int value)
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
        {
            DmlShape dmlShape = (DmlShape)this;

            if (dmlShape.getTextShape() == null)
                dmlShape.setTextShape(new DmlTextShape());

            dmlShape.getTextBodyPr().setTextWrappingType(value);
        }
    }

    /// <summary>
    /// Gets or sets a boolean value indicating either text of the TextBox should not rotate when the shape is rotated.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c></p>
    /// </remarks>
    /*override*/ boolean getTextBoxNoTextRotation()
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
            return ((DmlShape)this).getTextBodyPr().isTextUpright();

        return false;
    }
    /*override*/ void setTextBoxNoTextRotation(boolean value)
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
        {
            DmlShape dmlShape = (DmlShape)this;

            if (dmlShape.getTextShape() == null)
                dmlShape.setTextShape(new DmlTextShape());

            dmlShape.getTextBodyPr().isTextUpright(value);
        }
    }

    /// <summary>
    /// Defines the vertical anchoring of text in a TextShape. Word 2007 only.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Words.Drawing.TextBoxAnchor.Top"/>.</p>
    /// </remarks>
    /*override*/ /*TextBoxAnchor*/int getTextBoxAnchor()
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
            return DmlUtilCore.dmlToTextboxAnchor(((DmlShape)this).getTextBodyPr().getAnchor());

        return TextBoxAnchor.TOP;
    }
    /*override*/ void setTextBoxAnchor(/*TextBoxAnchor*/int value)
    {
        if ((getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) || (getDmlNodeType() == DmlNodeType.SHAPE))
        {
            DmlShape dmlShape = (DmlShape)this;

            if (dmlShape.getTextShape() == null)
                dmlShape.setTextShape(new DmlTextShape());

            dmlShape.getTextBodyPr().setAnchor(DmlUtilCore.textboxAnchorToDml(value));
        }
    }
    //<<<<<<<< #endregion 

    /*override*/ int getCoordSizeWidth()
    {
        DmlGroupTransform groupTransform = ms.as(getTransform(), DmlGroupTransform.class);
        if (groupTransform != null)
        {
            // andrnosk: WORDSNET-12565 WordprocessingCanvas does not have CoordSize,
            // CoordSize is the same as original shape size.
            if (getDmlNodeType() == DmlNodeType.WORDPROCESSING_CANVAS)
                return ConvertUtilCore.pointToEmu(mShape.getWidth());

            return !MathUtil.isZero(groupTransform.getChildWidth())
                ? (int)groupTransform.getChildWidth()
                : (int)groupTransform.getWidth();
        }

        return getWidth();
    }

    /*override*/ int getCoordSizeHeight()
    {
        DmlGroupTransform groupTransform = ms.as(getTransform(), DmlGroupTransform.class);
        if (groupTransform != null)
        {
            if (getDmlNodeType() == DmlNodeType.WORDPROCESSING_CANVAS)
                return ConvertUtilCore.pointToEmu(mShape.getHeight());

            return !MathUtil.isZero(groupTransform.getChildHeight())
                ? (int)groupTransform.getChildHeight()
                : (int)groupTransform.getHeight();
        }

        return getHeight();
    }

    /// <summary>
    /// Returns the width of the extents rectangle in EMUs.
    /// </summary>
    int getWidth()
    {
        DmlTransform dmlTransform = getTransform();
        return (int)dmlTransform.getWidth();
    }

    /// <summary>
    /// Returns the Height of the extents rectangle in EMUs.
    /// </summary>
    int getHeight()
    {
        DmlTransform dmlTransform = getTransform();
        return (int)dmlTransform.getHeight();
    }

    /*override*/ int getCoordOriginX()
    {
        if (getTransform() instanceof DmlGroupTransform)
            return (int)((DmlGroupTransform)getTransform()).getChildX();

        return (int)getTransform().getX();
    }
    /*override*/ void setCoordOriginX(int value)
    {
        if (getTransform() instanceof DmlGroupTransform)
            ((DmlGroupTransform)getTransform()).setChildX(value);
        else
            getTransform().setX(value);
    }

    /*override*/ int getCoordOriginY()
    {
        if (getTransform() instanceof DmlGroupTransform)
            return (int)((DmlGroupTransform)getTransform()).getChildY();

        return (int)getTransform().getY();
    }
    /*override*/ void setCoordOriginY(int value)
    {
        if (getTransform() instanceof DmlGroupTransform)
            ((DmlGroupTransform)getTransform()).setChildY(value);
        else
            getTransform().setY(value);
    }

    /*override*/ boolean getAspectRatioLocked()
    {
        if (mShape.isTopLevel())
        {
            // For top level shapes we have to use LockAspectRatio read from cNvGraphicFramePr->graphicFrameLocks
            // and stored inside ShapePr.
            if (mShape.getShapePr().contains(ShapeAttr.LOCK_ASPECT_RATIO))
                return (/*boolean*/Boolean)mShape.getShapePr().getDirectAttr(ShapeAttr.LOCK_ASPECT_RATIO);
        }
        else
        {
            // For child shapes we have to use LockAspectRatio read from cNvSpPr->spLocks
            // and stored inside CNvProperties.Locks.
            Object dmlLock = mShape.getDmlNode().getNonVisualPr().getCNvProperties().getLocks().getLock(DmlLock.NO_CHANGE_ASPECT);
            if (dmlLock != null)
                return (/*boolean*/Boolean)dmlLock;
        }

        return false;
    }
    /*override*/ void setAspectRatioLocked(boolean value)
    {
        if (mShape.isTopLevel())
            mShape.getShapePr().setAttr(ShapeAttr.LOCK_ASPECT_RATIO, value);

        mShape.getDmlNode().getNonVisualPr().getCNvProperties().getLocks().addLock(DmlLock.NO_CHANGE_ASPECT, value);
    }

    /// <summary>
    /// Vml shape related only.
    /// </summary>
    /*override*/ HorizontalRule getHorizontalRule() { return null; }

    /// <summary>
    /// Gets or sets <see cref="Aspose.Words.Drawing.ShadowType"/> object for a shape.
    /// </summary>
    /*override*/ /*ShadowType*/int getShadowType()
    {
        DmlShapeEffectsCollection effects = (this instanceof DmlShapeBase) ? ((DmlShapeBase)this).getEffects() : null;
        if (effects == null)
            return ShadowType.SHADOW_MIXED;

        for (DmlShapeEffect effect : effects)
        {
            if (effect.isShadowEffect())
                return effect.getShadowType();
        }

        return ShadowType.SHADOW_MIXED;
    }
    /*override*/ void setShadowType(/*ShadowType*/int value)
    {
        if (trySetShadowType(DmlShapeEffectType.PRESET_SHADOW, value))
            return;

        if (trySetShadowType(DmlShapeEffectType.INNER_SHADOW, value))
            return;

        trySetShadowType(DmlShapeEffectType.OUTER_SHADOW, value);
    }

    /// <summary>
    /// Gets or sets color of a shadow effect.
    /// </summary>
    /*override*/ Color getShadowColor()
    {
        DmlShapeEffect shadowShapeEffect = getShadowShapeEffect(false);
        if ((shadowShapeEffect == null) || (shadowShapeEffect.getColor() == null))
            return Color.BLACK;

        return shadowShapeEffect.getColor().createDrColor(mShape.getDocumentTheme(), null).toNativeColor();               
    }
    /*override*/ void setShadowColor(Color value)
    {
        DmlShapeEffect shadowShapeEffect = getShadowShapeEffect(true);
        shadowShapeEffect.setColor(DmlHexRgbColor.fromDrColor(DrColor.fromNativeColor(value)));
        if (msColor.isEmpty(value))
            shadowShapeEffect.getColor().updateAlpha(0.0);
    }

    /// <summary>
    /// Gets or sets transparency of a shadow effect.
    /// </summary>
    /*override*/ double getShadowTransparency()
    {
        DmlShapeEffect shadowShapeEffect = getShadowShapeEffect(false);
        if ((shadowShapeEffect == null) || (shadowShapeEffect.getColor() == null))
            return 0.0;

        return 1.0 - shadowShapeEffect.getColor().getAlpha().getValue();
    }
    /*override*/ void setShadowTransparency(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Shadow.Transparency");

        DmlShapeEffect shadowShapeEffect = getShadowShapeEffect(true);
        shadowShapeEffect.getColor().updateAlpha(1.0 - value);
    }

    Node getParent() { return mShape.getParentNode(); }

    /// <summary>
    /// Specifies whether the text flow of the text contents of the shape ignores the text flow value specified by the
    /// "vert" attribute of the "bodyPr" element.
    /// </summary>
    boolean getNormalEastAsianFlow() { return mNormalEastAsianFlow; }
    void setNormalEastAsianFlow(boolean value) { mNormalEastAsianFlow = value; }

    /// <summary>
    /// Model Id is used in diagram drawing.
    /// </summary>
    DmlModelId getModelId() { return mModelId; }
    void setModelId(DmlModelId value) { mModelId = value; }

    DmlNvPrBase getNonVisualPr() { return mNonVisualPr; }
    void setNonVisualPr(DmlNvPrBase value) { mNonVisualPr = value; }

    ShapeBase getDml() { return mShape; }

    /// <summary>
    /// Returns shadow shape effect for current DmlShape.
    /// </summary>
    /// <remarks>
    /// If DmlShape does not have a shadow shape effect and isAllowAutoCreate value is True,
    /// then creation of a new shadow shape effect corresponding to ShadowType.Shadow1 template
    /// is initiated. This is MS Word behavior.
    /// </remarks>
    private DmlShapeEffect getShadowShapeEffect(boolean isAllowAutoCreate)
    {
        DmlShapeBase dmlShapeBase = ms.as(this, DmlShapeBase.class);

        if (isAllowAutoCreate)
        {
            ArgumentUtil.checkNotNull(dmlShapeBase, "Shadow.ShapeEffect");
            ArgumentUtil.checkNotNull(dmlShapeBase.getEffects(), "Shadow.ShapeEffect");
        }

        if ((dmlShapeBase != null) && (dmlShapeBase.getEffects() != null))
        {
            for (DmlShapeEffect effect : dmlShapeBase.getEffects())
                if (effect.isShadowEffect())
                    return effect;
        }

        if (isAllowAutoCreate)
        {
            trySetShadowType(DmlShapeEffectType.PRESET_SHADOW, ShadowType.SHADOW_1);
            return getShadowShapeEffect(false);
        }

        return null;
    }

    private DmlModelId mModelId;
    private DmlNvPrBase mNonVisualPr;
    private boolean mNormalEastAsianFlow;
    protected DmlTransform mTransform;
    private StringToObjDictionary<DmlExtension> mExtensions;
    private StringToObjDictionary<DmlExtension> mDocPrExtensions;
    private AdjustmentCollection mAdjustments;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

