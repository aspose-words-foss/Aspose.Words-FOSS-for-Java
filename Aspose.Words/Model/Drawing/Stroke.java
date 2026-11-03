// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.ArgumentUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Drawing.msColor;


/// <summary>
/// Defines a stroke for a shape.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-shapes/">Working with Shapes</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Use the <see cref="Shape.Stroke"/> property to access stroke properties of a shape.
/// You do not create instances of the <see cref="Stroke"/> class directly.</p>
///
/// <seealso cref="Shape.Stroke"/>
/// </remarks>
public class Stroke implements IFillable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    Stroke(IStrokable parent)
    {
        mParent = parent;
    }

    //>>>>>>>> #region  Properties of the old Stroke API for compatibility.

    /// <summary>
    /// Defines whether the path will be stroked.
    /// </summary>
    /// <remarks>
    /// <p>The default value for a <see cref="Shape"/> is <c>true</c>.</p>
    /// </remarks>
    public boolean getOn() { return getVisible(); }
    public void setOn(boolean value) { setVisible(value); }

    /// <summary>
    /// Defines the color of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value for a <see cref="Shape"/> is
    /// <see cref="System.Drawing.Color.Black"/>.
    /// </p>
    /// </remarks>
    public Color getColor() { return getForeColor(); }
    public void setColor(Color value) { setForeColor(value); }

    /// <summary>
    /// Defines a second color for a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value for a <see cref="Shape"/> is
    /// <see cref="System.Drawing.Color.White"/>.
    /// </p>
    /// </remarks>
    public Color getColor2() { return getBackColor(); }
    public void setColor2(Color value) { setBackColor(value); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets the foreground color of the stroke.
    /// </summary>
    /// <remarks>
    /// The default value for a <see cref="Shape"/> is
    /// <see cref="System.Drawing.Color.Black"/>.
    /// </remarks>
    public Color getForeColor() { return getColorInternal().toNativeColor(); }
    public void setForeColor(Color value) { setColorInternal(DrColor.fromNativeColor(value)); }

    /// <summary>
    /// Gets the base foreground color of the stroke without any modifiers.
    /// </summary>
    /// <remarks>
    /// The default value for a <see cref="Shape"/> is
    /// <see cref="System.Drawing.Color.Black"/>.
    /// </remarks>
    public Color getBaseForeColor() { return mParent.getStrokeBaseForeColor().toNativeColor(); }

    /// <summary>
    /// Gets or sets the background color of the stroke.
    /// </summary>
    /// <remarks>
    /// The default value for a <see cref="Shape"/> is
    /// <see cref="System.Drawing.Color.White"/>.
    /// </remarks>
    public Color getBackColor() { return getColor2Internal().toNativeColor(); }
    public void setBackColor(Color value) { setColor2Internal(DrColor.fromNativeColor(value)); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the stroke foreground color.
    /// </summary>
    public /*ThemeColor*/int getForeThemeColor() throws Exception { return ((IFillable)this).getFillableForeThemeColor(); }
    public void setForeThemeColor(/*ThemeColor*/int value) throws Exception { ((IFillable)this).setFillableForeThemeColor(value); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the stroke background color.
    /// </summary>
    public /*ThemeColor*/int getBackThemeColor() throws Exception { return ((IFillable)this).getFillableBackThemeColor(); }
    public void setBackThemeColor(/*ThemeColor*/int value) throws Exception { ((IFillable)this).setFillableBackThemeColor(value); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the stroke foreground color.
    /// </summary>
    /// <remarks>
    /// <para> The allowed values are within the range from -1 (the darkest) to 1 (the lightest) for this property.
    /// Zero (0) is neutral. Attempting to set this property to a value less than -1 or more than 1
    /// results in <see cref="ArgumentOutOfRangeException"/>.</para>
    /// </remarks>
    public double getForeTintAndShade() { return ((IFillable)this).getFillableForeTintAndShade(); }
    public void setForeTintAndShade(double value) { ((IFillable)this).setFillableForeTintAndShade(value); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the stroke background color.
    /// </summary>
    /// <remarks>
    /// <para> The allowed values are within the range from -1 (the darkest) to 1 (the lightest) for this property.
    /// Zero (0) is neutral. Attempting to set this property to a value less than -1 or more than 1
    /// results in <see cref="ArgumentOutOfRangeException"/>.</para>
    /// </remarks>
    public double getBackTintAndShade() { return ((IFillable)this).getFillableBackTintAndShade(); }
    public void setBackTintAndShade(double value) { ((IFillable)this).setFillableBackTintAndShade(value); }

    /// <summary>
    /// Gets or sets a flag indicating whether the stroke is visible.
    /// </summary>
    /// <remarks>
    /// The default value for a <see cref="Shape"/> is <c>true</c>.
    /// </remarks>
    public boolean getVisible() { return mParent.getStrokeVisible(); }
    public void setVisible(boolean value) { mParent.setStrokeVisible(value); }

    /// <summary>
    /// Gets or sets a value between 0.0 (opaque) and 1.0 (clear) representing the degree of transparency
    /// of the stroke.
    /// </summary>
    /// <remarks>
    /// The default value is 0.
    /// </remarks>
    public double getTransparency() { return mParent.getStrokeTransparency(); }
    public void setTransparency(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Transparency");
        mParent.setStrokeTransparency(value);
    }

    /// <summary>
    /// Defines the brush thickness that strokes the path of a shape in points.
    /// </summary>
    /// <remarks>
    /// <p>The default value for a <see cref="Shape"/> is 0.75.</p>
    /// </remarks>
    public double getWeight() { return mParent.getWeight(); }
    public void setWeight(double value) { mParent.setWeight(value); }

    /// <summary>
    /// Specifies the dot and dash pattern for a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.DashStyle.Solid"/>.</p>
    /// </remarks>
    public /*DashStyle*/int getDashStyle() { return mParent.getDashStyle(); }
    public void setDashStyle(/*DashStyle*/int value) { mParent.setDashStyle(value); }

    /// <summary>
    /// Defines the join style of a polyline.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.JoinStyle.Round"/>.</p>
    /// </remarks>
    public /*JoinStyle*/int getJoinStyle() { return mParent.getJoinStyle(); }
    public void setJoinStyle(/*JoinStyle*/int value) { mParent.setJoinStyle(value); }

    /// <summary>
    /// Defines the cap style for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.EndCap.Flat"/>.</p>
    /// </remarks>
    public /*EndCap*/int getEndCap() { return mParent.getEndCap(); }
    public void setEndCap(/*EndCap*/int value) { mParent.setEndCap(value); }

    /// <summary>
    /// Defines the line style of the stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ShapeLineStyle.Single"/>.</p>
    /// </remarks>
    public /*ShapeLineStyle*/int getLineStyle() { return mParent.getLineStyle(); }
    public void setLineStyle(/*ShapeLineStyle*/int value) { mParent.setLineStyle(value); }

    /// <summary>
    /// Defines the arrowhead for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowType.None"/>.</p>
    /// </remarks>
    public /*ArrowType*/int getStartArrowType() { return mParent.getStartArrowType(); }
    public void setStartArrowType(/*ArrowType*/int value) { mParent.setStartArrowType(value); }

    /// <summary>
    /// Defines the arrowhead for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowType.None"/>.</p>
    /// </remarks>
    public /*ArrowType*/int getEndArrowType() { return mParent.getEndArrowType(); }
    public void setEndArrowType(/*ArrowType*/int value) { mParent.setEndArrowType(value); }

    /// <summary>
    /// Defines the arrowhead width for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowWidth.Medium"/>.</p>
    /// </remarks>
    public /*ArrowWidth*/int getStartArrowWidth() { return mParent.getStartArrowWidth(); }
    public void setStartArrowWidth(/*ArrowWidth*/int value) { mParent.setStartArrowWidth(value); }

    /// <summary>
    /// Defines the arrowhead length for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowLength.Medium"/>.</p>
    /// </remarks>
    public /*ArrowLength*/int getStartArrowLength() { return mParent.getStartArrowLength(); }
    public void setStartArrowLength(/*ArrowLength*/int value) { mParent.setStartArrowLength(value); }

    /// <summary>
    /// Defines the arrowhead width for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowWidth.Medium"/>.</p>
    /// </remarks>
    public /*ArrowWidth*/int getEndArrowWidth() { return mParent.getEndArrowWidth(); }
    public void setEndArrowWidth(/*ArrowWidth*/int value) { mParent.setEndArrowWidth(value); }

    /// <summary>
    /// Defines the arrowhead length for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowLength.Medium"/>.</p>
    /// </remarks>
    public /*ArrowLength*/int getEndArrowLength() { return mParent.getEndArrowLength(); }
    public void setEndArrowLength(/*ArrowLength*/int value) { mParent.setEndArrowLength(value); }

    /// <summary>
    /// Defines the amount of transparency of a stroke. Valid range is from 0 to 1.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1.</p>
    /// </remarks>
    public double getOpacity() { return 1.0 - getTransparency(); }
    public void setOpacity(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Opacity");
        setTransparency(1.0 - value);
    }

    /// <summary>
    /// Defines the image for a stroke image or pattern fill.
    /// </summary>
    
    public byte[] getImageBytes() throws Exception { return mParent.getStrokeImageBytes(); }

    /// <summary>
    /// Gets fill formatting for the <see cref="Stroke"/>.
    /// </summary>
    public Fill getFill()
    {
        if (mFill == null)
            mFill = new Fill(this);

        return mFill;
    }

    //>>>>>>>> #region  IFillable implementation

    /// <summary>
    /// Changes type of the fill to Solid.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/solid()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
        {
            // Most probably, the fill is VmlOutline. Just set it visible.
            setVisible(true);
            return;
        }

        if (fill.getDmlFillType() == DmlFillType.SOLID_FILL)
            return;

        createSolidFill();
    }

    /// <summary>
    /// Gets a PresetTexture for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*PresetTexture*/int /*IFillable.*/getPresetTexture()
    {
        throw new IllegalStateException(ERROR_PRESET_TEXTURED);
    }

    /// <summary>
    /// Gets a PatternType for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*PatternType*/int /*IFillable.*/getPatternType()
    {
        throw new IllegalStateException(ERROR_PATTERNED);
    }

    /// <summary>
    /// Changes type of the fill to preset texture.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/presetTextured(/*PresetTexture*/int presetTexture)
    {
        throw new IllegalStateException(ERROR_PRESET_TEXTURED);
    }

    /// <summary>
    /// Changes type of the fill to a pattern.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/patterned(/*PatternType*/int patternType)
    {
        throw new IllegalStateException(ERROR_PATTERNED);
    }

    /// <summary>
    /// Changes type of the fill to gradient.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/twoColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        DmlFill curFill = getFill(false);
        DmlColor color1 = ((curFill != null) && (curFill.getDmlColorInternal() != null))
            ? curFill.getDmlColorInternal().deepClone()
            : DmlColor.createFromDrColor(DrColor.BLACK);

        DmlColor color2 = ((curFill != null) && (curFill.getDmlColor2Internal() != null))
            ? curFill.getDmlColor2Internal().deepClone()
            : DmlColor.createFromDrColor(DrColor.BLACK);

        DmlFill gradientFill = new DmlGradientFill(color1, color2, style, variant, mParent.getStrokeThemeProvider());
        ((IFillable)this).setFill(gradientFill);
    }

    /// <summary>
    /// Changes type of the fill to gradient.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/oneColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant, double degree)
    {
        DmlFill curFill = getFill(false);
        DmlColor color1 = ((curFill != null) && (curFill.getDmlColorInternal() != null))
            ? curFill.getDmlColorInternal().deepClone()
            : DmlColor.createFromDrColor(DrColor.BLACK);

        DmlColor color2 = color1.deepClone();

        // Check either we need luminance modifier.
        if (!MathUtil.areEqual(degree, 0.5))
        {
            DmlLuminanceModulation lumMod = new DmlLuminanceModulation();
            DmlLuminanceOffset lumOff = new DmlLuminanceOffset();
            if (MathUtil.isLessOrEqual(degree, 0.5))
            {
                lumMod.setValue(degree * 2.0);
                lumOff.setValue(0.0);
            }
            else
            {
                lumMod.setValue((1.0 - degree) * 2.0);
                lumOff.setValue(1.0 - lumMod.getValue());
            }

            msArrayList.add(color2.getColorModifiers(), lumMod);
            msArrayList.add(color2.getColorModifiers(), lumOff);
        }

        DmlFill gradientFill = new DmlGradientFill(color1, color2, style, variant, mParent.getStrokeThemeProvider());
        ((IFillable)this).setFill(gradientFill);
    }

    /// <summary>
    /// Changes the fill type to single image.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setImage(byte[] imageBytes)
    {
        throw new IllegalStateException("SetImage cannot be applied to a fill of the Stroke.");
    }

    /// <summary>
    /// Sets specified fill to this object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFill(IFill fill)
    {
        DmlFill dmlFill = ms.as(fill, DmlFill.class);
        if ((dmlFill == null) ||
            ((dmlFill.getDmlFillType() != DmlFillType.SOLID_FILL) &&
             (dmlFill.getDmlFillType() != DmlFillType.GRADIENT_FILL) &&
             (dmlFill.getDmlFillType() != DmlFillType.NO_FILL)))
            throw new IllegalStateException("Invalid fill type for this object.");

        dmlFill.setParent(this);
        mParent.setStrokeFill(dmlFill);
    }

    /// <summary>
    /// Returns a double value representing transparency of a specified color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getTransparency(DmlColor color)
    {
        if (color.getAlpha() == null)
            return 0.0;

        return (1.0 - color.getAlpha().getValue());
    }

    /// <summary>
    /// Sets a specified value to a transparency of specified color .
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setTransparency(DmlColor color, double value)
    {
        color.updateAlpha(1.0 - value);
    }

    //>>>>>>>> #region  The public properties of old Fill object for compatibility.

    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFilledColor() { return ((IFillable)this).getFillableForeColor(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFilledColor(Color value) { ((IFillable)this).setFillableForeColor(value); }

    /// <summary>
    /// Gets or sets a boolean value indicating whether a Fill applied to an object is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getOldOn() { return ((IFillable)this).getFillableVisible(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setOldOn(boolean value) { ((IFillable)this).setFillableVisible(value); }

    /// <summary>
    /// Gets or sets a double value between 0.0 (clear) and 1.0 (opaque) representing the degree
    /// of opacity of the specified fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getOldOpacity() { return 1.0 - ((IFillable)this).getFillableTransparency(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setOldOpacity(double value) { ((IFillable)this).setFillableTransparency(1.0 - value); }

    /// <summary>
    /// Gets the raw bytes of the fill texture or pattern.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>null</c>.</p>
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public byte[] /*IFillable.*/getFillableImageBytes() { return null; }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets the alignment for tile texture fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*TextureAlignment*/int /*IFillable.*/getTextureAlignment() { return TextureAlignment.NONE; }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setTextureAlignment(/*TextureAlignment*/int value) { throw new IllegalStateException(ERROR_TEXTURE_ALIGNMENT); }

    /// <summary>
    /// Gets or sets the angle of the gradient fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getGradientAngle()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return 0.0;

        return fill.getAngle();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setGradientAngle(double value)
    {
        DmlFill fill = getFill(false);
        if (fill != null)
            fill.setAngle(value);
    }

    /// <summary>
    /// Gets the gradient variant for the fill as an integer value from 1 to 4 for most gradient fills.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*GradientVariant*/int /*IFillable.*/getGradientVariant()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return GradientVariant.NONE;

        return fill.getGradientVariant();
    }

    /// <summary>
    /// Gets the gradient style for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*GradientStyle*/int /*IFillable.*/getGradientStyle()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return GradientStyle.NONE;

        return fill.getGradientStyle();
    }

    /// <summary>
    /// Gets the gradient stops collection for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public GradientStopCollection /*IFillable.*/getGradientStops()
    {
        DmlGradientFill fill = ms.as(getFill(false), DmlGradientFill.class);
        if (fill == null)
            throw new IllegalStateException(INVALID_ACTION);

        return fill.getGradientStopCollection();
    }

    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableForeColor()
    {
        return ((IFillable)this).getFillableVisible()
            ? mParent.getStrokeForeColor().toColorFixAlpha().toNativeColor()
            : msColor.Empty;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeColor(Color value) { mParent.setStrokeForeColor(DrColor.fromNativeColor(value)); }

    /// <summary>
    /// Gets a Color object that represents the base foreground color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableBaseForeColor()
    {
        return ((IFillable)this).getFillableVisible()
            ? mParent.getStrokeBaseForeColor().toColorFixAlpha().toNativeColor()
            : msColor.Empty;
    }

    /// <summary>
    /// Gets or sets a Color object that represents the background color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableBackColor()
    {
        return ((IFillable)this).getFillableVisible()
            ? mParent.getStrokeBackColor().toColorFixAlpha().toNativeColor()
            : msColor.Empty;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackColor(Color value) { mParent.setStrokeBackColor(DrColor.fromNativeColor(value)); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the foreground color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IFillable.*/getFillableForeThemeColor() throws Exception
    {
        return ((IFillable)this).getFillableVisible()
            ? mParent.getStrokeForeThemeColor()
            : ThemeColor.NONE;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeThemeColor(/*ThemeColor*/int value) throws Exception { mParent.setStrokeForeThemeColor(value); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the background color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IFillable.*/getFillableBackThemeColor() throws Exception
    {
        return ((IFillable)this).getFillableVisible()
            ? mParent.getStrokeBackThemeColor()
            : ThemeColor.NONE;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackThemeColor(/*ThemeColor*/int value) throws Exception { mParent.setStrokeBackThemeColor(value); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the foreground color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableForeTintAndShade()
    {
        return ((IFillable)this).getFillableVisible()
            ? mParent.getStrokeForeTintAndShade()
            : 0.0;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeTintAndShade(double value) { mParent.setStrokeForeTintAndShade(value); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the background color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableBackTintAndShade()
    {
        return ((IFillable)this).getFillableVisible()
            ? mParent.getStrokeBackTintAndShade()
            : 0.0;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackTintAndShade(double value) { mParent.setStrokeBackTintAndShade(value); }

    /// <summary>
    /// Gets or sets a boolean value indicating whether a Fill applied to an object is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getFillableVisible() { return mParent.getStrokeVisible(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableVisible(boolean value) { mParent.setStrokeVisible(value); }

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree
    /// of transparency of the specified fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableTransparency() { return mParent.getStrokeTransparency(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableTransparency(double value) { mParent.setStrokeTransparency(value); }

    /// <summary>
    /// Gets or sets a boolean value indicating whether the fill rotates with the specified object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getRotateWithObject() { throw new IllegalStateException(DmlFill.MSG_INVALID_ACTION); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setRotateWithObject(boolean value) { throw new IllegalStateException(DmlFill.MSG_INVALID_ACTION); }

    /// <summary>
    /// Gets fill type.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*FillTypeCore*/int /*IFillable.*/getFillType()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return FillTypeCore.SOLID;

        return fill.getFillType();
    }

    /// <summary>
    /// Gets IThemeProvider object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IFillable.*/getFillableThemeProvider() { return mParent.getStrokeThemeProvider(); }

//<<<<<<<< #endregion 

    /// <summary>
    /// Gets fill of this <see cref="Stroke"/> object.
    /// </summary>
    /// <param name="isAllowAutoCreate"> Indicates whether it is allowed to create
    /// default Solid fill when there is no fill set. </param>
    private DmlFill getFill(boolean isAllowAutoCreate)
    {
        DmlFill value = mParent.getStrokeFill();
        if (value == null)
            return (isAllowAutoCreate) ? createSolidFill() : null;

        value.setParent(this);
        return value;
    }

    /// <summary>
    /// Creates a new Solid fill in this <see cref="Stroke"/> object.
    /// </summary>
    private DmlFill createSolidFill()
    {
        DmlFill solidFill = new DmlSolidFill();
        mParent.setStrokeFill(solidFill);

        solidFill.setParent(this);

        return solidFill;
    }

    DrColor getColorInternal() { return mParent.getStrokeForeColor(); }
    void setColorInternal(DrColor value) { mParent.setStrokeForeColor(value); }

    DrColor getColor2Internal() { return mParent.getStrokeBackColor(); }
    void setColor2Internal(DrColor value) { mParent.setStrokeBackColor(value); }

    /// <summary>
    /// Defines the type of fill used for the background of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.Core.LineFillType.Solid"/>.</p>
    /// </remarks>
    /*LineFillType*/int getLineFillType() { return mParent.getLineFillType(); }
    void setLineFillType(/*LineFillType*/int value) { mParent.setLineFillType(value); }

    private /*final*/ IStrokable mParent;

    private Fill mFill;

    private static final String ERROR_PRESET_TEXTURED = "Preset texture cannot be applied to Stroke.";
    private static final String ERROR_TEXTURE_ALIGNMENT = "TextureAlignment cannot be applied to Stroke.";
    private static final String ERROR_PATTERNED = "Pattern cannot be applied to Stroke.";
    private static final String INVALID_ACTION = "Object doesn't support this action.";
}

