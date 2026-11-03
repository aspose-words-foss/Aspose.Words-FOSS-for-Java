// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/03/2021 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.MathUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.DrColor;
import com.aspose.ms.System.msString;
import com.aspose.HashUtil;
import com.aspose.Debug;
import java.awt.Color;


/// <summary>
/// Represents the formatting of a chart element.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
public class ChartFormat implements IFillable, IStrokable
{
    ChartFormat(IChartFormatSource parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Resets the fill of the chart element to have the default value.
    /// </summary>
    public void setDefaultFill()
    {
        if (!isDefined())
            return;

        ensureFillCanBeUpdated();

        // It is expected that setting to 'null' resets the fill, and then mParent.Fill will return a value
        // representing the default fill.
        mParent.setFill(null);
    }

    /// <summary>
    /// Changes type of the fill to an one-color gradient.
    /// </summary>
    private void oneColorGradient(DmlColor color, /*GradientStyle*/int style, /*GradientVariant*/int variant, double degree)
    {
        DmlColor color2 = color.deepClone();

        if (!MathUtil.areEqual(degree, 0.5))
        {
            // Add either a Tint or Shade modifier to the second color.
            IDmlColorModifier modifier = MathUtil.isLessOrEqual(degree, 0.5)
                ? (IDmlColorModifier)new DmlShade(degree * 2.0)
                : (IDmlColorModifier)new DmlTint((1.0 - degree) * 2.0);

            msArrayList.add(color2.getColorModifiers(), modifier);
        }

        twoColorGradient(color, color2, style, variant);
    }

    /// <summary>
    /// Changes type of the fill to a two-color gradient.
    /// </summary>
    private void twoColorGradient(DmlColor color1, DmlColor color2, /*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        ensureFillCanBeUpdated();

        IThemeProvider themeProvider = ((IFillable)this).getFillableThemeProvider();
        setFillCore(new DmlGradientFill(color1, color2, style, variant, themeProvider));

        // Word VBA sets this flag, do the same.
        getFillCore().setRotateWithShape(true);
    }

    /// <summary>
    /// Ensures that fill of the chart element can be updated. Throws an exception if this is not so.
    /// </summary>
    private void ensureFillCanBeUpdated()
    {
        if (!mParent.isFillSupported())
            throw new IllegalStateException(NOT_SUPPORTED_FILL);

        mParent.materializeSpPr();
    }

    //>>>>>>>> #region  IFillable implementation

    /// <summary>
    /// Changes type of the fill to Solid.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/solid()
    {
        ensureFillCanBeUpdated();

        if (getFillCore().getDmlFillType() == DmlFillType.SOLID_FILL)
            return;

        setFillCore(new DmlSolidFill());
        // Word VBA resets back color to default in the new Solid fill.
        getFillCore().setColor2Internal(DrColor.WHITE);
    }

    /// <summary>
    /// Changes type of the fill to preset texture.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/presetTextured(/*PresetTexture*/int presetTexture)
    {
        String textureName = FillUtil.getTextureName(presetTexture);
        if (msString.equals(textureName, ""))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "The specified texture is out of range.");

        ensureFillCanBeUpdated();

        DmlBlipFill blipFill = new DmlBlipFill();
        blipFill.setBlipFillMode(new DmlBlipFillTile());
        blipFill.getBlip().setEmbedImage(FillUtil.getTextureBytes(textureName));

        setFillCore(blipFill);
    }

    /// <summary>
    /// Changes type of the fill to a pattern.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/patterned(/*PatternType*/int patternType)
    {
        ensureFillCanBeUpdated();

        DmlPatternFill patternFill = new DmlPatternFill();
        patternFill.setFillPresetPattern(patternType);

        patternFill.setForegroundColor((getFillCore().getDmlColorInternal() != null)
            ? getFillCore().getDmlColorInternal().deepClone()
            : ((IFillable)this).getFillableThemeProvider().getThemeColor(ThemeColor.ACCENT_1));

        patternFill.setBackgroundColor((getFillCore().getDmlColor2Internal() != null)
            ? getFillCore().getDmlColor2Internal().deepClone()
            : ((IFillable)this).getFillableThemeProvider().getThemeColor(ThemeColor.BACKGROUND_1));

        setFillCore(patternFill);
    }

    /// <summary>
    /// Gets a <see cref="PresetTexture"/> for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*PresetTexture*/int /*IFillable.*/getPresetTexture() throws Exception
    {
        int textureHash = HashUtil.getSHA512Hash(getFillCore().getImageBytes()).hashCode();
        return FillUtil.getTextureByHash(textureHash);
    }

    /// <summary>
    /// Gets a <see cref="PatternType"/> of the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*PatternType*/int /*IFillable.*/getPatternType()
    {
        return (getFillCore().getFillType() == FillTypeCore.PATTERN)
            ? ((DmlPatternFill)getFillCore()).getFillPresetPattern()
            : PatternType.NONE;
    }

    /// <summary>
    /// Changes type of the fill to gradient.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/oneColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant, double degree)
    {
        DmlColor color = (getFillCore().getDmlColorInternal() != null)
            ? getFillCore().getDmlColorInternal().deepClone()
            : ((IFillable)this).getFillableThemeProvider().getThemeColor(ThemeColor.ACCENT_1);

        oneColorGradient(color, style, variant, degree);
    }

    /// <summary>
    /// Changes type of the fill to two-color gradient.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/twoColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        IThemeProvider themeProvider = ((IFillable)this).getFillableThemeProvider();

        DmlColor color1 = (getFillCore().getDmlColorInternal() != null)
            ? getFillCore().getDmlColorInternal().deepClone()
            : themeProvider.getThemeColor(ThemeColor.ACCENT_1);

        DmlColor color2 = (getFillCore().getDmlColor2Internal() != null)
            ? getFillCore().getDmlColor2Internal().deepClone()
            : themeProvider.getThemeColor(ThemeColor.BACKGROUND_1);

        twoColorGradient(color1, color2, style, variant);
    }

    /// <summary>
    /// Changes the fill type to single image.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setImage(byte[] imageBytes)
    {
        ensureFillCanBeUpdated();

        setFillCore(new DmlBlipFill());
        getFillCore().setImageBytes(imageBytes);
    }

    /// <summary>
    /// Sets the specified fill to the parent chart element.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFill(IFill fill)
    {
        Debug.assertTrue(fill != null);

        DmlFill dmlFill = ms.as(fill, DmlFill.class);
        if (dmlFill == null)
            throw new IllegalStateException("Invalid fill type for this object.");

        setFillCore(dmlFill);
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

    //>>>>>>>> #region  The properties of the old Fill API for compatibility.

    /// <summary>
    /// Gets or sets a <see cref="Color"/> object that represents a color of the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFilledColor() { return ((IFillable)this).getFillableForeColor(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFilledColor(Color value) { ((IFillable)this).setFillableForeColor(value); }

    /// <summary>
    /// Gets or sets a flag indicating whether a fill applied to the parent chart element is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getOldOn() { return ((IFillable)this).getFillableVisible(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setOldOn(boolean value) { ((IFillable)this).setFillableVisible(value); }

    /// <summary>
    /// Gets or sets a double value between 0.0 (clear) and 1.0 (opaque) representing the degree of opacity
    /// of the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getOldOpacity() { return 1.0 - ((IFillable)this).getFillableTransparency(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setOldOpacity(double value) { ((IFillable)this).setFillableTransparency(1.0 - value); }

    /// <summary>
    /// Gets the raw bytes of the fill texture or pattern.
    /// </summary>
    /// <remarks>
    /// <p>The default value is null.</p>
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public byte[] /*IFillable.*/getFillableImageBytes() { return (getFillCore().getDmlFillType() == DmlFillType.BLIP_FILL) ? ((DmlBlipFill)getFillCore()).getBlip().getEmbedImage() : null; }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets a <see cref="Color"/> object that represents a color of the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableForeColor()
    {
        // Transparency/Opacity properties are stored as color alpha: return color with 0xff alpha here.
        return getFillCore().getColorInternal().toColorFixAlpha().toNativeColor();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeColor(Color value)
    {
        ensureFillCanBeUpdated();

        if (getFillCore().getDmlFillType() == DmlFillType.NO_FILL)
            setFillCore(new DmlSolidFill());

        getFillCore().setColorInternal(DrColor.fromNativeColor(value));
    }

    /// <summary>
    /// Gets or sets a <see cref="Color"/> object that represents a base color of the fill without modifiers.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableBaseForeColor() { return getFillCore().getColorInternalUnmodified().toColorFixAlpha().toNativeColor(); }

    /// <summary>
    /// Gets or sets a <see cref="Color"/> object that represents an additional color of the fill.
    /// </summary>
    /// <remarks>
    /// This is an end color of a gradient fill or a background color of a pattern fill.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableBackColor() { return getFillCore().getColor2Internal().toColorFixAlpha().toNativeColor(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackColor(Color value)
    {
        ensureFillCanBeUpdated();

        if (getFillCore().getDmlFillType() != DmlFillType.NO_FILL)
            getFillCore().setColor2Internal(DrColor.fromNativeColor(value));
    }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the foreground color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IFillable.*/getFillableForeThemeColor() { return ThemeColor.NONE; }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeThemeColor(/*ThemeColor*/int value) { throw new UnsupportedOperationException(ERROR_NOT_IMPLEMENTED); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the background color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IFillable.*/getFillableBackThemeColor() { return ThemeColor.NONE; }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackThemeColor(/*ThemeColor*/int value) { throw new UnsupportedOperationException(ERROR_NOT_IMPLEMENTED); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the foreground color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableForeTintAndShade() { return 0.0; }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeTintAndShade(double value) { throw new UnsupportedOperationException(ERROR_NOT_IMPLEMENTED); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the background color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableBackTintAndShade() { return 0.0; }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackTintAndShade(double value) { throw new UnsupportedOperationException(ERROR_NOT_IMPLEMENTED); }

    /// <summary>
    /// Gets or sets a flag indicating whether a fill applied to the parent chart element is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getFillableVisible() { return getFillCore().getOn(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableVisible(boolean value)
    {
        ensureFillCanBeUpdated();
        getFillCore().setOn(value);
    }

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree of transparency
    /// of the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableTransparency() { return 1.0 - getFillCore().getOpacity(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableTransparency(double value)
    {
        ensureFillCanBeUpdated();

        // Word VBA allows to set up opacity (transparency) for NoFill and changes the type of fill
        // to Solid implicitly.
        if (getFillCore().getDmlFillType() == DmlFillType.NO_FILL)
            setFillCore(new DmlSolidFill());

        getFillCore().setOpacity(1.0 - value);
    }

    /// <summary>
    /// Gets or sets a flag indicating whether the fill rotates with the specified object.
    /// </summary>
    /// <dev>
    /// Couldn't find a way to change this option in MS Word, it is always disabled. And cannot find a way to rotate
    /// a chart in MS Word, the corresponding button is always disabled too.
    /// </dev>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getRotateWithObject() { return getFillCore().getRotateWithShape(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setRotateWithObject(boolean value)
    {
        ensureFillCanBeUpdated();
        getFillCore().setRotateWithShape(value);
    }

    /// <summary>
    /// Gets fill type.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*FillTypeCore*/int /*IFillable.*/getFillType() { return getFillCore().getFillType(); }

    /// <summary>
    /// Gets or sets the alignment for tile texture fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*TextureAlignment*/int /*IFillable.*/getTextureAlignment()
    {
        DmlBlipFill blipFill = ms.as(getFillCore(), DmlBlipFill.class);
        if (blipFill == null)
            return TextureAlignment.NONE;

        DmlBlipFillTile blipFillTile = ms.as(blipFill.getBlipFillMode(), DmlBlipFillTile.class);
        if (blipFillTile == null)
            return TextureAlignment.NONE;

        return FillUtil.dmlRectangleToTextureAlignment(blipFillTile.getAlignment());
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setTextureAlignment(/*TextureAlignment*/int value)
    {
        if (value == TextureAlignment.NONE)
            throw new IllegalStateException("TextureAlignment.None cannot be applied directly.");

        DmlBlipFill blipFill = ms.as(getFillCore(), DmlBlipFill.class);
        if (blipFill == null)
            throw new IllegalStateException("Texture alignment can be applied to a texture fill only.");

        DmlBlipFillTile blipFillTile = ms.as(blipFill.getBlipFillMode(), DmlBlipFillTile.class);
        if (blipFillTile == null)
            throw new IllegalStateException("Texture alignment can be applied to a tile texture fill only.");

        blipFillTile.setAlignment(FillUtil.textureToDmlRectangleAlignment(value));
    }

    /// <summary>
    /// Gets or sets the angle of the gradient fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getGradientAngle() { return getFillCore().getAngle(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setGradientAngle(double value) { getFillCore().setAngle(value); }

    /// <summary>
    /// Gets the gradient variant for the fill as an integer value from 1 to 4 for most gradient fills.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*GradientVariant*/int /*IFillable.*/getGradientVariant() { return getFillCore().getGradientVariant(); }

    /// <summary>
    /// Gets the gradient style for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*GradientStyle*/int /*IFillable.*/getGradientStyle() { return getFillCore().getGradientStyle(); }

    /// <summary>
    /// Gets the gradient stops collection for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public GradientStopCollection /*IFillable.*/getGradientStops()
    {
        DmlGradientFill gradientFill = ms.as(getFillCore(), DmlGradientFill.class);
        if (gradientFill == null)
            throw new IllegalStateException(INVALID_ACTION);

        // Let's materialize SpPr here so that changing gradient stops will not affect format of the parent object.
        // This makes format of this object unlinked from format of the parent object, which is not good for a get
        // operation, but let's use this simple solution until customers complain.
        mParent.materializeSpPr();

        return gradientFill.getGradientStopCollection();
    }

    /// <summary>
    /// Gets a <see cref="IThemeProvider"/> object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IFillable.*/getFillableThemeProvider() { return (mParent.getThemeProvider() != null) ? mParent.getThemeProvider() : Theme.getBuiltInTheme(); }
    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IStrokable implementation

    /// <summary>
    /// Gets or sets the foreground color for the stroke.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DrColor /*IStrokable.*/getStrokeForeColor()
    {
        // Transparency/Opacity properties are stored as color alpha: return color with 0xff alpha here.
        return getStrokeCore().getColorInternal().toColorFixAlpha();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeForeColor(DrColor value)
    {
        mParent.materializeSpPr();

        // DmlNoFill doesn't store color: let's convert to a solid fill to not lost the value. The same is done
        // in IFillable.ForeColor implementation. This makes the line visible.
        if (getStrokeCore().getFill().getDmlFillType() == DmlFillType.NO_FILL)
            getStrokeCore().setFill(new DmlSolidFill());

        getStrokeCore().setColorInternal(value);
    }

    /// <summary>
    /// Gets a base foreground color for the stroke without modifiers.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DrColor /*IStrokable.*/getStrokeBaseForeColor() { return getStrokeCore().getColorInternalUnmodified().toColorFixAlpha(); }

    /// <summary>
    /// Gets or sets the background color for the stroke.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DrColor /*IStrokable.*/getStrokeBackColor() { return getStrokeCore().getColor2Internal().toColorFixAlpha(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeBackColor(DrColor value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setColor2Internal(value);
    }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the foreground color for the stroke.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IStrokable.*/getStrokeForeThemeColor() { return ThemeColor.NONE; }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeForeThemeColor(/*ThemeColor*/int value) { throw new UnsupportedOperationException(ERROR_NOT_IMPLEMENTED); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the background color for the stroke.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IStrokable.*/getStrokeBackThemeColor() { return ThemeColor.NONE; }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeBackThemeColor(/*ThemeColor*/int value) { throw new UnsupportedOperationException(ERROR_NOT_IMPLEMENTED); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the foreground color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IStrokable.*/getStrokeForeTintAndShade() { return 0.0; }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeForeTintAndShade(double value) { throw new UnsupportedOperationException(ERROR_NOT_IMPLEMENTED); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the background color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IStrokable.*/getStrokeBackTintAndShade() { return 0.0; }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeBackTintAndShade(double value) { throw new UnsupportedOperationException(ERROR_NOT_IMPLEMENTED); }

    /// <summary>
    /// Gets or sets a flag indicating whether the stroke is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IStrokable.*/getStrokeVisible() { return getStrokeCore().getOn(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeVisible(boolean value)
    {
        mParent.materializeSpPr();

        getStrokeCore().setOn(value);
        if (value && getStrokeCore().getFill().getDmlFillType() == DmlFillType.NO_FILL)
            getStrokeCore().setFill(new DmlSolidFill());
    }

    /// <summary>
    /// Gets or sets a value between 0.0 (opaque) and 1.0 (clear) representing the degree of transparency
    /// of the stroke.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IStrokable.*/getStrokeTransparency() { return 1.0 - getStrokeCore().getOpacity(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeTransparency(double value)
    {
        mParent.materializeSpPr();

        // Opacity/transparency is stored in DmlOutline.Fill.Opacity, but DmlNoFill cannot store it: let's
        // convert to a solid fill.
        if (getStrokeCore().getFill().getDmlFillType() == DmlFillType.NO_FILL)
            getStrokeCore().setFill(new DmlSolidFill());

        getStrokeCore().setOpacity(1.0 - value);
    }

    /// <summary>
    /// Defines the brush thickness that strokes the path of a chart element in points.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IStrokable.*/getWeight()
    {
        if (getStrokeCore().getOn() && !getStrokeCore().isWeightSpecified())
        {
            // Actually weight default value depends on a chart element type. For example, it is different for
            // a line chart series. Weight is explicitly defined in chart definitions, so skip this discrepancy
            // for now.
            return DEFAULT_WEIGHT;
        }

        return getStrokeCore().getWeight();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setWeight(double value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setWeight(value);
    }

    /// <summary>
    /// Specifies the dot and dash pattern for a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="DashStyle.Solid"/>.</p>
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*DashStyle*/int /*IStrokable.*/getDashStyle() { return getStrokeCore().getDashStyle(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setDashStyle(/*DashStyle*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setDashStyle(value);
    }

    /// <summary>
    /// Defines the join style of a polyline.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="JoinStyle.Round"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*JoinStyle*/int /*IStrokable.*/getJoinStyle() { return getStrokeCore().getJoinStyle(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setJoinStyle(/*JoinStyle*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setJoinStyle(value);
    }

    /// <summary>
    /// Defines the cap style for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="EndCap.Flat"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*EndCap*/int /*IStrokable.*/getEndCap() { return getStrokeCore().getEndCap(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setEndCap(/*EndCap*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setEndCap(value);
    }

    /// <summary>
    /// Defines the line style of the stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ShapeLineStyle.Single"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ShapeLineStyle*/int /*IStrokable.*/getLineStyle() { return getStrokeCore().getLineStyle(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setLineStyle(/*ShapeLineStyle*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setLineStyle(value);
    }

    /// <summary>
    /// Defines the arrowhead for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowType.None"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowType*/int /*IStrokable.*/getStartArrowType() { return getStrokeCore().getStartArrowType(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStartArrowType(/*ArrowType*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setStartArrowType(value);
    }

    /// <summary>
    /// Defines the arrowhead for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowType.None"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowType*/int /*IStrokable.*/getEndArrowType() { return getStrokeCore().getEndArrowType(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setEndArrowType(/*ArrowType*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setEndArrowType(value);
    }

    /// <summary>
    /// Defines the arrowhead width for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowWidth.Medium"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowWidth*/int /*IStrokable.*/getStartArrowWidth() { return getStrokeCore().getStartArrowWidth(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStartArrowWidth(/*ArrowWidth*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setStartArrowWidth(value);
    }

    /// <summary>
    /// Defines the arrowhead length for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowLength.Medium"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowLength*/int /*IStrokable.*/getStartArrowLength() { return getStrokeCore().getStartArrowLength(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStartArrowLength(/*ArrowLength*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setStartArrowLength(value);
    }

    /// <summary>
    /// Defines the arrowhead width for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowWidth.Medium"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowWidth*/int /*IStrokable.*/getEndArrowWidth() { return getStrokeCore().getEndArrowWidth(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setEndArrowWidth(/*ArrowWidth*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setEndArrowWidth(value);
    }

    /// <summary>
    /// Defines the arrowhead length for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowLength.Medium"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowLength*/int /*IStrokable.*/getEndArrowLength() { return getStrokeCore().getEndArrowLength(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setEndArrowLength(/*ArrowLength*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setEndArrowLength(value);
    }

    /// <summary>
    /// Defines the type of fill used for the background of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="LineFillType.Solid"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*LineFillType*/int /*IStrokable.*/getLineFillType() { return getStrokeCore().getLineFillType(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setLineFillType(/*LineFillType*/int value)
    {
        mParent.materializeSpPr();
        getStrokeCore().setLineFillType(value);
    }

    /// <summary>
    /// Gets the image for a stroke image or pattern fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public byte[] /*IStrokable.*/getStrokeImageBytes() throws Exception { return getStrokeCore().getImageBytes(); }

    /// <summary>
    /// Gets a <see cref="IThemeProvider"/> object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IStrokable.*/getStrokeThemeProvider() { return mParent.getThemeProvider(); }

    /// <summary>
    /// Gets or sets a <see cref="DmlFill"/> object of the stroke fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DmlFill /*IStrokable.*/getStrokeFill() { return getStrokeCore().getFill(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeFill(DmlFill value) { getStrokeCore().setFill(value); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets fill formatting for the parent chart element.
    /// </summary>
    public Fill getFill()
    {
        if (mFill == null)
            mFill = new Fill(this);

        return mFill;
    }

    /// <summary>
    /// Gets line formatting for the parent chart element.
    /// </summary>
    public Stroke getStroke()
    {
        if (mLine == null)
            mLine = new Stroke(this);

        return mLine;
    }

    /// <summary>
    /// Gets or sets the shape type of the parent chart element.
    /// </summary>
    /// <remarks>
    /// Currently, the property can only be used for data labels.
    /// </remarks>
    public /*ChartShapeType*/int getShapeType() { return mParent.getShapeType(); }
    public void setShapeType(/*ChartShapeType*/int value) { mParent.setShapeType(value); }

    /// <summary>
    /// Gets a flag indicating whether any format is defined.
    /// </summary>
    public boolean isDefined() { return mParent.isFormatDefined(); }

    /// <summary>
    /// Gets a <see cref="DmlFill"/> containing fill properties of the parent chart element.
    /// </summary>
    private DmlFill getFillCore()
    {
        DmlFill fillCore = mParent.getFill();
        fillCore.setParent(this);
        return fillCore;
    }
    private void setFillCore(DmlFill value)
    {
        mParent.setFill(value);
        if (value != null)
            value.setParent(this);
    }

    /// <summary>
    /// Gets a <see cref="DmlOutline"/> containing line formatting properties of the parent chart element.
    /// </summary>
    private DmlOutline getStrokeCore()
    {
        DmlOutline strokeCore = mParent.getOutline();
        strokeCore.getFill().setParent(this);
        return strokeCore;
    }
    private void setStrokeCore(DmlOutline value)
    {
        mParent.setOutline(value);
        if (value != null)
            value.getFill().setParent(this);
    }

    private /*final*/ IChartFormatSource mParent;
    private Fill mFill;
    private Stroke mLine;

    /// <summary>
    /// Default weight.
    /// </summary>
    /// <remarks>
    /// Actually weight default value depends on a chart element type. For example, it is different for a line
    /// chart series. Weight is explicitly defined in chart definitions, so skip this discrepancy for now.
    /// </remarks>
    private static final double DEFAULT_WEIGHT = 0.75;

    private static final String ERROR_NOT_IMPLEMENTED = "Method or property is not implemented for ChartFormat.";
    private static final String INVALID_ACTION = "Object doesn't support this action.";
    private static final String NOT_SUPPORTED_FILL = "Fill is not supported by this chart element.";
}

