// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;
import com.aspose.ConvertUtilCore;
import com.aspose.Debug;
import com.aspose.DrColor;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents 20.1.2.2.24 ln (Outline)
/// This element specifies an outline style that can be applied to
/// a number of different objects such as shapes and text.
/// The line allows for the specifying of many different
/// types of outlines including even line dashes and bevels.
/// </summary>
class DmlOutline extends DmlExtensionListSource implements IDmlColorable, IStroke
{
    DmlOutline()
    {
        mPropertyBag.setParentBagProvider(G_DEFAULT_PARENT_BAG_PROVIDER);
    }

    public /*virtual*/ DmlOutline deepClone()
    {
        DmlOutline lhs = new DmlOutline();

        if (mPropertyBag != null)
            lhs.mPropertyBag = mPropertyBag.deepClone();

        if (isPropertySpecified(DmlOutlinePropertiesIds.FILL))
            lhs.setFill(getFill().deepClone());

        if (isPropertySpecified(DmlOutlinePropertiesIds.DASH))
            lhs.setDash(getDash().deepClone());

        if (isPropertySpecified(DmlOutlinePropertiesIds.HEAD_LINE_END_STYLE))
            lhs.setHeadLineEndStyle((DmlHeadLineEndStyle)getHeadLineEndStyle().deepClone());

        if (isPropertySpecified(DmlOutlinePropertiesIds.TAIL_LINE_END_STYLE))
            lhs.setTailLineEndStyle((DmlTailLineEndStyle)getTailLineEndStyle().deepClone());

        if (isPropertySpecified(DmlOutlinePropertiesIds.EXTENSIONS))
            lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlOutline value = (DmlOutline)obj;

        boolean equals = true;

        /*DmlOutlinePropertiesIds*/int[] values = (/*DmlOutlinePropertiesIds*/int[])DmlOutlinePropertiesIds.getValues();
        for (/*DmlOutlinePropertiesIds*/int id : values)
        {
            Object val1 = getDirectProperty(id);
            Object val2 = value.getDirectProperty(id);
            equals &= msObject.equals(val1, val2);
            if(!equals)
                break;
        }

        return equals;
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;

        /*DmlOutlinePropertiesIds*/int[] values = (/*DmlOutlinePropertiesIds*/int[])DmlOutlinePropertiesIds.getValues();
        for (/*DmlOutlinePropertiesIds*/int id : values)
        {
            Object value = getDirectProperty(id);
            if (value == null)
                continue;

            hash ^= value.hashCode();
        }

        return hash;
    }

    /// <summary>
    /// Set style color in color placeholders used
    /// in the outline.
    /// </summary>
    public void applyStyleColor(DmlColor styleColor)
    {
        if (getFill() != null)
            getFill().applyStyleColor(styleColor);
    }

    Object getDirectProperty(/*DmlOutlinePropertiesIds*/int propertyId)
    {
        return mPropertyBag.getDirectProperty((int)propertyId);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="propertyId">the property id</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlOutlinePropertiesIds*/int propertyId)
    {
        return mPropertyBag.isPropertySpecified((int)propertyId);
    }

    /// <summary>
    /// Sets parent properties collection for current outline.
    /// </summary>
    /// <remarks>
    /// Internal access level for test purposes.
    /// </remarks>
    void setParentProperties(DmlOutline dmlParentOutline)
    {
        // WORDSNET-25330 Release parent in the Fill before it is replaced with a new Fill
        // in this parent properties bag to prevent possible memory leak.
        IDmlHierarchicalPropertyBag parentBag = mPropertyBag.getParentBagProvider().getParentBag();
        if (parentBag != null)
        {
            DmlFill fill = ms.as(parentBag.getProperty((int)DmlOutlinePropertiesIds.FILL), DmlFill.class);
            if (fill != null)
                fill.setParent(null);
        }

        mPropertyBag.setParentBagProvider(new DmlHierarchicalPropertyBagParentContainer(dmlParentOutline.mPropertyBag));
    }

    private void setProperty(/*DmlOutlinePropertiesIds*/int propertyId, Object value)
    {
        mPropertyBag.setProperty((int)propertyId, value);
    }

    private Object getProperty(/*DmlOutlinePropertiesIds*/int propertyId)
    {
        return mPropertyBag.getProperty((int)propertyId);
    }

    /// <summary>
    /// Removes the specified property.
    /// </summary>
    void remove(/*DmlOutlinePropertiesIds*/int propertyId)
    {
        mPropertyBag.remove((int)propertyId);
    }

    /// <summary>
    /// Creates and sets new DmlFill using style fill if direct fill is not specified.
    /// </summary>
    private void createDirectFill()
    {
        Object fillObj = getDirectProperty(DmlOutlinePropertiesIds.FILL);

        if (fillObj == null)
        {
            // If direct fill is not specified we have to create new DmlFill using style fill type.
            DmlFill newFill;

            switch (getFill().getDmlFillType())
            {
                case DmlFillType.SOLID_FILL:
                    newFill = new DmlSolidFill();
                    break;
                case DmlFillType.PATTERN_FILL:
                    newFill = new DmlPatternFill();
                    break;
                case DmlFillType.BLIP_FILL:
                    newFill = new DmlBlipFill();
                    break;
                case DmlFillType.GRADIENT_FILL:
                    newFill = new DmlGradientFill();
                    break;
                default:
                    newFill = new DmlSolidFill();
                    break;
            }

            updateEffectExtent(0.0, ConvertUtilCore.emuToPoint(getWidthInEmus()));

            // Set Shape for this new fill, we need this to get color from
            // Document Themes and to reset fill for current shape.
            newFill.setParent(getFill().getParent());
            setFill(newFill);
        }
    }

    //>>>>>>>> #region  IStroke interface implementation.

    public boolean getOn()
    {
        // Return false only when the FillType equals DmlFillType.NoFill, in all other cases return true even
        // if the fill is not specified (in this case the style fill will be used).
        if ((getFill() != null) && (getFill().getDmlFillType() == DmlFillType.NO_FILL))
            return false;

        return true;
    }
    public void setOn(boolean value)
    {
        IFillable parent = (getFill() != null) ? getFill().getParent() : null;

        if (value)
        {
            if ((getFill() != null) && (getFill().getDmlFillType() == DmlFillType.NO_FILL))
            {
                mPropertyBag.remove((int)DmlOutlinePropertiesIds.FILL);
                if (getFill() != null)
                    getFill().setParent(parent);
            }
        }
        else
        {
            // Reduce effect extent according to outline weight.
            if ((getFill() != null) && (getFill().getDmlFillType() != DmlFillType.NO_FILL))
                updateEffectExtent(ConvertUtilCore.emuToPoint(getWidthInEmus()), 0.0);

            setFill(new DmlNoFill());
            getFill().setParent(parent);
        }
    }

    public double getWeight() { return ConvertUtilCore.emuToPoint(getWidthInEmus()); }
    public void setWeight(double value)
    {
        if (value < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        // WORDSNET-14095 Add required effect extent to enable display outline of the image.
        if (getOn())
            updateEffectExtent(ConvertUtilCore.emuToPoint(getWidthInEmus()), value);

        setWidthInEmus(ConvertUtilCore.pointToEmu(value));
    }

    /// <summary>
    /// Updates effect extents according to changes of the outline weight.
    /// </summary>
    void updateEffectExtent(double curWeight, double newWeight)
    {
        if (getFill() == null)
            return;

        Shape parentShape = ms.as(getFill().getParent(), Shape.class);
        if (parentShape == null)
            return;

        DmlNoFill defaultOutlineFill =
            (DmlNoFill)DmlOutlinePropertiesDefaults.getInstance().getDirectProperty((int)DmlOutlinePropertiesIds.FILL);

        boolean isOutlineFillFromShapeStyle = (getDirectProperty(DmlOutlinePropertiesIds.FILL) == null) &&
            !msObject.referenceEquals(getFill(), defaultOutlineFill);

        // There are not examples of pictures with shape styles to implement calculation of effect extents.
        // So, this condition was added to mark this limitation.
        if (!isOutlineFillFromShapeStyle || !parentShape.isImage())
        {
            DmlOutlineEffectExtentCalculator.updateWeightRelatedEffectExtentPart(parentShape, curWeight, newWeight);
        }
        else
        {
            Debug.fail("Unsupported scenario");
        }
    }

    /// <summary>
    /// Gets or sets a color.
    /// </summary>
    public DrColor getColorInternal() { return getFill().getColorInternal(); }
    public void setColorInternal(DrColor value)
    {
        createDirectFill();
        getFill().setColorInternal(value);
    }

    /// <summary>
    /// Gets a base color without modifiers.
    /// </summary>
    public DrColor getColorInternalUnmodified() { return getFill().getColorInternalUnmodified(); }

    public DrColor getColor2Internal() { return getFill().getColor2Internal(); }
    public void setColor2Internal(DrColor value)
    {
        createDirectFill();

        // Color2 works only for PatternFill and GradientFill types.
        if ((getFill().getDmlFillType() == DmlFillType.PATTERN_FILL) || (getFill().getDmlFillType() == DmlFillType.GRADIENT_FILL))
            getFill().setColor2Internal(value);
    }

    public /*DashStyle*/int getDashStyle()
    {
        // In case of DmlDashType.CustomDash return DashStyle.Default.
        if (getDash().getDashType() == DmlDashType.PRESET_DASH)
            return ((DmlPresetDash)getDash()).getPreset();

        return DashStyle.DEFAULT;
    }
    public void setDashStyle(/*DashStyle*/int value)
    {
        if (isPropertySpecified(DmlOutlinePropertiesIds.DASH) && (getDash().getDashType() == DmlDashType.PRESET_DASH))
            ((DmlPresetDash)getDash()).setPreset(value);

        // Create and set new DmlPresetDash with specified Preset value.
        DmlPresetDash dmlPreset = new DmlPresetDash();
        dmlPreset.setPreset(value);
        setDash(dmlPreset);
    }

    public /*JoinStyle*/int getJoinStyle() { return getLineJoinStyle(); }
    public void setJoinStyle(/*JoinStyle*/int value) { setLineJoinStyle(value); }

    public /*EndCap*/int getEndCap() { return getLineEndingCapType(); }
    public void setEndCap(/*EndCap*/int value) { setLineEndingCapType(value); }

    public /*ShapeLineStyle*/int getLineStyle() { return getCompoundLineType(); }
    public void setLineStyle(/*ShapeLineStyle*/int value) { setCompoundLineType(value); }

    public /*ArrowType*/int getStartArrowType() { return getHeadLineEndStyle().getType(); }
    public void setStartArrowType(/*ArrowType*/int value)
    {
        cloneIfRequired(DmlOutlinePropertiesIds.HEAD_LINE_END_STYLE);
        getHeadLineEndStyle().setType(value);
    }

    public /*ArrowWidth*/int getStartArrowWidth() { return getHeadLineEndStyle().getWidth(); }
    public void setStartArrowWidth(/*ArrowWidth*/int value)
    {
        cloneIfRequired(DmlOutlinePropertiesIds.HEAD_LINE_END_STYLE);
        getHeadLineEndStyle().setWidth(value);
    }

    public /*ArrowLength*/int getStartArrowLength() { return getHeadLineEndStyle().getLength(); }
    public void setStartArrowLength(/*ArrowLength*/int value)
    {
        cloneIfRequired(DmlOutlinePropertiesIds.HEAD_LINE_END_STYLE);
        getHeadLineEndStyle().setLength(value);
    }

    public /*ArrowType*/int getEndArrowType() { return getTailLineEndStyle().getType(); }
    public void setEndArrowType(/*ArrowType*/int value)
    {
        cloneIfRequired(DmlOutlinePropertiesIds.TAIL_LINE_END_STYLE);
        getTailLineEndStyle().setType(value);
    }

    public /*ArrowWidth*/int getEndArrowWidth() { return getTailLineEndStyle().getWidth(); }
    public void setEndArrowWidth(/*ArrowWidth*/int value)
    {
        cloneIfRequired(DmlOutlinePropertiesIds.TAIL_LINE_END_STYLE);
        getTailLineEndStyle().setWidth(value);
    }

    public /*ArrowLength*/int getEndArrowLength() { return getTailLineEndStyle().getLength(); }
    public void setEndArrowLength(/*ArrowLength*/int value)
    {
        cloneIfRequired(DmlOutlinePropertiesIds.TAIL_LINE_END_STYLE);
        getTailLineEndStyle().setLength(value);
    }

    /// <summary>
    /// If direct DmlHeadLineEndStyle/DmlTailLineEndStyle is not set it is need to clone these objects
    /// from defaults before set internal attributes.
    /// </summary>
    private void cloneIfRequired(/*DmlOutlinePropertiesIds*/int propertiesId)
    {
        if (!isPropertySpecified(propertiesId) )
        {
            switch (propertiesId)
            {
                case DmlOutlinePropertiesIds.HEAD_LINE_END_STYLE:
                    setHeadLineEndStyle((DmlHeadLineEndStyle)getHeadLineEndStyle().deepClone());
                    break;
                case DmlOutlinePropertiesIds.TAIL_LINE_END_STYLE:
                    setTailLineEndStyle((DmlTailLineEndStyle)getTailLineEndStyle().deepClone());
                    break;
                default:
                    break;
            }
        }
    }

    public double getOpacity() { return getFill().getOpacity(); }
    public void setOpacity(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        createDirectFill();

        // If color is empty we have to get and set color from style, and then apply opacity to this color.
        if (DrColor.equals(getFill().getColorInternal(), DrColor.EMPTY))
        {
            Shape shape = ms.as(getFill().getParent(), Shape.class);
            if (shape != null)
            {
                DmlShapeBase shapeBase = (DmlShapeBase)shape.getDmlNode();
                if (shapeBase.getStyle() != null)
                    getFill().setDmlColorInternal(shapeBase.getStyle().getLineReference().getColor());
            }
        }

        getFill().setOpacity(value);
    }

    public byte[] getImageBytes() throws Exception { return getFill().getImageBytes(); }

    public /*LineFillType*/int getLineFillType()
    {
        switch (getFill().getDmlFillType())
        {
            case DmlFillType.SOLID_FILL:
                return LineFillType.SOLID;
            case DmlFillType.PATTERN_FILL:
                return LineFillType.PATTERN;
            case DmlFillType.BLIP_FILL:
                {
                    if (((DmlBlipFill)getFill()).getBlipFillMode() instanceof DmlBlipFillTile)
                        return LineFillType.TEXTURE;

                    return LineFillType.PICTURE;
                }
            default:
                return LineFillType.SOLID;
        }
    }
    public void setLineFillType(/*LineFillType*/int value)
    {
        DmlFill newFill;
        switch (value)
        {
            case LineFillType.PATTERN:
                newFill = new DmlPatternFill();
                break;
            case LineFillType.PICTURE:
                {
                    newFill = new DmlBlipFill();
                    ((DmlBlipFill)newFill).setBlipFillMode(new DmlBlipFillStretch());
                    break;
                }
            case LineFillType.SOLID:
                newFill = new DmlSolidFill();
                break;
            case LineFillType.TEXTURE:
                {
                    newFill = new DmlBlipFill();
                    ((DmlBlipFill)newFill).setBlipFillMode(new DmlBlipFillTile());
                    break;
                }
            default:
                newFill = new DmlSolidFill();
                break;
        }

        setFill(newFill);
    }

    /// <summary>
    /// Gets or sets a <see cref="DmlFill"/> object of the stroke fill.
    /// </summary>
    public 
    DmlFill /*IStroke.*/getStrokeFill() { return getFill(); }
    public void /*IStroke.*/setStrokeFill(DmlFill value) { setFill(value); }

    //<<<<<<<< #endregion 

    public /*override*/ StringToObjDictionary<DmlExtension> getExtensions() { return (StringToObjDictionary<DmlExtension>)getProperty(DmlOutlinePropertiesIds.EXTENSIONS); }
    public /*override*/ void setExtensions(StringToObjDictionary<DmlExtension> value) { setProperty(DmlOutlinePropertiesIds.EXTENSIONS, value); }

    /// <summary>
    /// Specifies the line fill properties to be used for the underline stroke.
    /// </summary>
    DmlFill getFill() { return (DmlFill)getProperty(DmlOutlinePropertiesIds.FILL); }
    void setFill(DmlFill value) { setProperty(DmlOutlinePropertiesIds.FILL, value); }

    /// <summary>
    /// Specifies the width in EMUs to be used for the underline stroke.
    /// </summary>
    double getWidthInEmus() { return (/*double*/Double)getProperty(DmlOutlinePropertiesIds.WIDTH_IN_EMUS); }
    void setWidthInEmus(double value) { setProperty(DmlOutlinePropertiesIds.WIDTH_IN_EMUS, value); }

    /// <summary>
    /// Specifies that a line join shall be mitered.
    /// </summary>
    double getLineMiterLimit() { return (/*double*/Double)getProperty(DmlOutlinePropertiesIds.LINE_MITER_LIMIT); }
    void setLineMiterLimit(double value) { setProperty(DmlOutlinePropertiesIds.LINE_MITER_LIMIT, value); }

    /// <summary>
    /// Specifies the compound line type to be used for the underline stroke.
    /// The default value is SingleLine.
    /// </summary>
    /*ShapeLineStyle*/int getCompoundLineType() { return (/*ShapeLineStyle*/Integer)getProperty(DmlOutlinePropertiesIds.COMPOUND_LINE_TYPE); }
    void setCompoundLineType(/*ShapeLineStyle*/int value) { setProperty(DmlOutlinePropertiesIds.COMPOUND_LINE_TYPE, value); }

    /// <summary>
    /// Specifies the ending caps that should be used for this line.
    /// The default value is Square.
    /// </summary>
    /*EndCap*/int getLineEndingCapType() { return (/*EndCap*/Integer)getProperty(DmlOutlinePropertiesIds.LINE_ENDING_CAP_TYPE); }
    void setLineEndingCapType(/*EndCap*/int value) { setProperty(DmlOutlinePropertiesIds.LINE_ENDING_CAP_TYPE, value); }

    /// <summary>
    /// Specifies the alignment to be used for the underline stroke.
    /// </summary>
    boolean getStrokeAlignment() { return (/*boolean*/Boolean)getProperty(DmlOutlinePropertiesIds.STROKE_ALIGNMENT); }
    void setStrokeAlignment(boolean value) { setProperty(DmlOutlinePropertiesIds.STROKE_ALIGNMENT, value); }

    /// <summary>
    /// Specifies the dash properties to be used for the underline stroke.
    /// </summary>
    DmlDash getDash() { return (DmlDash)getProperty(DmlOutlinePropertiesIds.DASH); }
    void setDash(DmlDash value) { setProperty(DmlOutlinePropertiesIds.DASH, value); }

    /*JoinStyle*/int getLineJoinStyle() { return (/*JoinStyle*/Integer)getProperty(DmlOutlinePropertiesIds.LINE_JOIN_STYLE); }
    void setLineJoinStyle(/*JoinStyle*/int value) { setProperty(DmlOutlinePropertiesIds.LINE_JOIN_STYLE, value); }

    DmlHeadLineEndStyle getHeadLineEndStyle() { return (DmlHeadLineEndStyle)getProperty(DmlOutlinePropertiesIds.HEAD_LINE_END_STYLE); }
    void setHeadLineEndStyle(DmlHeadLineEndStyle value) { setProperty(DmlOutlinePropertiesIds.HEAD_LINE_END_STYLE, value); }

    DmlTailLineEndStyle getTailLineEndStyle() { return (DmlTailLineEndStyle)getProperty(DmlOutlinePropertiesIds.TAIL_LINE_END_STYLE); }
    void setTailLineEndStyle(DmlTailLineEndStyle value) { setProperty(DmlOutlinePropertiesIds.TAIL_LINE_END_STYLE, value); }

    int getDirectPropertiesCount() { return mPropertyBag.getCount(); }

    boolean isWeightSpecified() { return isPropertySpecified(DmlOutlinePropertiesIds.WIDTH_IN_EMUS); }

    boolean isLineJoinStyleSpecified() { return isPropertySpecified(DmlOutlinePropertiesIds.LINE_JOIN_STYLE); }

    /// <summary>
    /// Resolves theme colors to a concrete RGB colors using a specified theme provider.
    /// </summary>
    void resolveThemeColors(IThemeProvider themeProvider, DmlShapeStyle dmlShapeStyle)
    {
        // So far, we only resolve theme color when Outline fill is missing.
        if (isPropertySpecified(DmlOutlinePropertiesIds.FILL))
            return;

        // In this case, Word takes color from a corresponding DmlShapeStyle.
        if (dmlShapeStyle == null)
            return;

        DmlSchemeColor schemeColor = ms.as(dmlShapeStyle.getLineReference().getColor(), DmlSchemeColor.class);
        if (schemeColor == null)
            return;

        setFill(new DmlSolidFill(schemeColor.resolve(themeProvider)));
    }

    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();

    private static /*final*/ IDmlHierarchicalPropertyBagParentProvider G_DEFAULT_PARENT_BAG_PROVIDER =
        new DmlHierarchicalPropertyBagParentContainer(DmlOutlinePropertiesDefaults.getInstance());
}

