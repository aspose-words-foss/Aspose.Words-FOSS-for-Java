// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/11/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.Stream;
import com.aspose.StreamUtil;
import com.aspose.ArgumentUtil;


/// <summary>
/// Represents fill formatting for an object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-graphic-elements/">Working with Graphic Elements</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Use the <see cref="ShapeBase.Fill"/> or <see cref="Font.Fill"/> property to access fill properties of an object.
/// You do not create instances of the <see cref="Fill"/> class directly.</p>
/// </remarks>
/// <dev>
/// This class is proposed public facade for <see cref="IFillable"/>.
/// https://docs.microsoft.com/en-us/office/vba/api/word.fillformat
/// </dev>
public class Fill
{
    /// <summary>
    /// Creates FillFormat object with a specified parent.
    /// </summary>
    Fill(IFillable parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Sets the fill to a uniform color.
    /// </summary>
    /// <remarks>
    /// Use this method to convert any of the fills back to solid fill.
    /// </remarks>
    public void solid()
    {
        mParent.solid();
    }

    /// <summary>
    /// Sets the fill to a specified uniform color.
    /// </summary>
    /// <remarks>
    /// Use this method to convert any of the fills back to solid fill.
    /// </remarks>
    public void solid(Color color)
    {
        mParent.solid();
        mParent.setFillableForeColor(color);
    }

    /// <summary>
    /// Sets the fill to a preset texture.
    /// </summary>
    /// <param name="presetTexture"><see cref="Drawing.PresetTexture"/></param>
    public void presetTextured(/*PresetTexture*/int presetTexture)
    {
        if (presetTexture == PresetTexture.NONE)
            throw new IllegalArgumentException("PresetTexture.None cannot be applied directly.");
        mParent.presetTextured(presetTexture);
    }

    /// <summary>
    /// Sets the specified fill to a pattern.
    /// </summary>
    /// <param name="patternType"><see cref="Drawing.PatternType"/></param>
    public void patterned(/*PatternType*/int patternType)
    {
        if (patternType == PatternType.NONE)
            throw new IllegalArgumentException(ERROR_PATTERNED);

        mParent.patterned(patternType);
    }

    /// <summary>
    /// Sets the specified fill to a pattern.
    /// </summary>
    /// <param name="patternType"><see cref="Drawing.PatternType"/></param>
    /// <param name="foreColor">The color of the foreground fill.</param>
    /// <param name="backColor">The color of the background fill.</param>
    public void patterned(/*PatternType*/int patternType, Color foreColor, Color backColor)
    {
        patterned(patternType);
        mParent.setFillableForeColor(foreColor);
        mParent.setFillableBackColor(backColor);
    }

    /// <summary>
    /// Sets the specified fill to a one-color gradient.
    /// </summary>
    /// <param name="style">The gradient style <see cref="Drawing.GradientStyle"/></param>
    /// <param name="variant">The gradient variant <see cref="Drawing.GradientVariant"/></param>
    /// <param name="degree">The gradient degree. Can be a value from 0.0 (dark) to 1.0 (light).</param>
    public void oneColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant, double degree)
    {
        mParent.oneColorGradient(style, variant, degree);
    }

    /// <summary>
    /// Sets the specified fill to a one-color gradient using the specified color.
    /// </summary>
    /// <param name="color">The color to build the gradient.</param>
    /// <param name="style">The gradient style <see cref="Drawing.GradientStyle"/></param>
    /// <param name="variant">The gradient variant <see cref="Drawing.GradientVariant"/></param>
    /// <param name="degree">The gradient degree. Can be a value from 0.0 (dark) to 1.0 (light).</param>
    public void oneColorGradient(Color color, /*GradientStyle*/int style, /*GradientVariant*/int variant, double degree)
    {
        // Generate a simple gradient so that the mParent.FillableForeColor property can be set. Otherwise its value
        // may be not stored.
        mParent.oneColorGradient(style, GradientVariant.VARIANT_1, degree);

        mParent.setFillableForeColor(color);

        // Generate the final gradient.
        mParent.oneColorGradient(style, variant, degree);
    }

    /// <summary>
    /// Sets the specified fill to a two-color gradient.
    /// </summary>
    /// <param name="style">The gradient style <see cref="Drawing.GradientStyle"/>.</param>
    /// <param name="variant">The gradient variant <see cref="Drawing.GradientVariant"/></param>
    public void twoColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        mParent.twoColorGradient(style, variant);
    }

    /// <summary>
    /// Sets the specified fill to a two-color gradient.
    /// </summary>
    /// <param name="color1">The first color to build the gradient.</param>
    /// <param name="color2">The second color to build the gradient.</param>
    /// <param name="style">The gradient style <see cref="Drawing.GradientStyle"/>.</param>
    /// <param name="variant">The gradient variant <see cref="Drawing.GradientVariant"/></param>
    public void twoColorGradient(Color color1, Color color2, /*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        // Generate a simple gradient so that the mParent.FillableForeColor and mParent.FillableBackColor properties
        // can be set. Otherwise their values may be not stored.
        mParent.twoColorGradient(style, GradientVariant.VARIANT_1);

        mParent.setFillableForeColor(color1);
        mParent.setFillableBackColor(color2);

        // Generate the final gradient.
        mParent.twoColorGradient(style, variant);
    }

    /// <summary>
    /// Changes the fill type to single image.
    /// </summary>
    /// <param name="fileName">The path to the image file.</param>
    public void setImage(String fileName) throws Exception
    {
        byte[] imageBytes = File.readAllBytes(fileName);
        mParent.setImage(imageBytes);
    }

    /// <summary>
    /// Changes the fill type to single image.
    /// </summary>
    /// <param name="stream">The stream that contains the image bytes.</param>
    /// <javaName>void setImage(java.io.InputStream stream)</javaName>
    //JAVA: method is internalized
	void setImageInternal(Stream stream) throws Exception
    {
        byte[] imageBytes = new byte[(int)stream.getLength()];
        StreamUtil.read(stream, imageBytes, 0, (int)stream.getLength());
        mParent.setImage(imageBytes);
    }

	//JAVA-added public wrapper for internalized method
	public void setImage(java.io.InputStream stream) throws Exception
	{
		setImageInternal(Stream.fromJavaInputStream(stream));
	}

    /// <summary>
    /// Changes the fill type to single image.
    /// </summary>
    /// <param name="imageBytes">The image bytes array.</param>
    public void setImage(byte[] imageBytes)
    {
        mParent.setImage(imageBytes);
    }

    /// <summary>
    /// Gets a <see cref="Drawing.PresetTexture"/> for the fill.
    /// </summary>
    public /*PresetTexture*/int getPresetTexture() throws Exception { return mParent.getPresetTexture(); }

    /// <summary>
    /// Gets a <see cref="Drawing.PatternType"/> for the fill.
    /// </summary>
    public /*PatternType*/int getPattern() throws Exception { return mParent.getPatternType(); }

    /// <summary>
    /// Gets or sets the alignment for tile texture fill.
    /// </summary>
    public /*TextureAlignment*/int getTextureAlignment() { return mParent.getTextureAlignment(); }
    public void setTextureAlignment(/*TextureAlignment*/int value) { mParent.setTextureAlignment(value); }

    //>>>>>>>> #region  The public properties of old Fill object for compatibility.
    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    /// <remarks>
    /// This property preserves the alpha component of the <see cref="System.Drawing.Color"/>,
    /// unlike the <see cref="ForeColor"/> property, which resets it to fully opaque color.
    /// </remarks>
    /// <dev> Word VBA gets and sets RGB color. </dev>
    public Color getColor() { return mParent.getFilledColor(); }
    public void setColor(Color value) { mParent.setFilledColor(value); }

    /// <summary>
    /// Gets or sets the degree of opacity of the specified fill as a value between 0.0 (clear) and 1.0 (opaque).
    /// </summary>
    /// <remarks>This property is the opposite of property <see cref="Transparency"/>.</remarks>
    public double getOpacity() { return mParent.getOldOpacity(); }
    public void setOpacity(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Opacity");
        mParent.setOldOpacity(value);
    }

    /// <summary>
    /// Gets the raw bytes of the fill texture or pattern.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>null</c>.</p>
    /// </remarks>
    public byte[] getImageBytes() throws Exception { return mParent.getFillableImageBytes(); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    /// <remarks> This property resets the alpha component of the <see cref="System.Drawing.Color"/>
    /// to fully opaque color unlike the <see cref="Color"/> property, which preserves it.
    /// </remarks>
    /// <dev> Word VBA gets and sets RGB color. </dev>
    public Color getForeColor() { return mParent.getFillableForeColor(); }
    public void setForeColor(Color value) { mParent.setFillableForeColor(value); }

    /// <summary>
    /// Gets a Color object that represents the base foreground color for the fill
    /// without any modifiers.
    /// </summary>
    public Color getBaseForeColor() { return mParent.getFillableBaseForeColor(); }

    /// <summary>
    /// Gets or sets a Color object that represents the background color for the fill.
    /// </summary>
    /// <dev> Word VBA gets and sets RGB color. </dev>
    public Color getBackColor() { return mParent.getFillableBackColor(); }
    public void setBackColor(Color value) { mParent.setFillableBackColor(value); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the foreground color for the fill.
    /// </summary>
    public /*ThemeColor*/int getForeThemeColor() throws Exception { return mParent.getFillableForeThemeColor(); }
    public void setForeThemeColor(/*ThemeColor*/int value) throws Exception { mParent.setFillableForeThemeColor(value); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the background color for the fill.
    /// </summary>
    public /*ThemeColor*/int getBackThemeColor() throws Exception { return mParent.getFillableBackThemeColor(); }
    public void setBackThemeColor(/*ThemeColor*/int value) throws Exception { mParent.setFillableBackThemeColor(value); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the foreground color.
    /// </summary>
    /// <remarks>
    /// <para>The allowed values are in the range from -1 (the darkest) to 1 (the lightest) for this property.</para>
    /// <para>Zero (0) is neutral.</para>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">Throw if set this property to a value less than -1 or more than 1.</exception>
    public double getForeTintAndShade() { return mParent.getFillableForeTintAndShade(); }
    public void setForeTintAndShade(double value) { mParent.setFillableForeTintAndShade(value); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the background color.
    /// </summary>
    /// <remarks>
    /// <para>The allowed values are in the range from -1 (the darkest) to 1 (the lightest) for this property.</para>
    /// <para>Zero (0) is neutral.</para>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">Throw if set this property to a value less than -1 or more than 1.</exception>
    public double getBackTintAndShade() { return mParent.getFillableBackTintAndShade(); }
    public void setBackTintAndShade(double value) { mParent.setFillableBackTintAndShade(value); }

    /// <summary>
    /// Gets or sets value that is <c>true</c> if the formatting applied to this instance, is visible.
    /// </summary>
    public boolean getVisible() { return mParent.getFillableVisible(); }
    public void setVisible(boolean value) { mParent.setFillableVisible(value); }

    /// <summary>
    /// Gets or sets the degree of transparency of the specified fill as a value between 0.0 (opaque) and 1.0 (clear).
    /// </summary>
    /// <remarks>This property is the opposite of property <see cref="Opacity"/>.</remarks>
    /// <dev>See https://docs.microsoft.com/en-us/office/vba/api/word.fillformat for details.</dev>
    public double getTransparency() { return mParent.getFillableTransparency(); }
    public void setTransparency(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Transparency");
        mParent.setFillableTransparency(value);
    }

    /// <summary>
    /// Gets or sets whether the fill rotates with the specified object.
    /// </summary>
    /// <dev>
    /// This property is applicable to shape objects only.
    /// When accessing this property in VBA for text an exception is thrown.
    /// </dev>
    public boolean getRotateWithObject() { return mParent.getRotateWithObject(); }
    public void setRotateWithObject(boolean value) { mParent.setRotateWithObject(value); }

    /// <summary>
    /// Gets a fill type.
    /// </summary>
    public /*FillType*/int getFillType()
    {
        if (!mParent.getFillableVisible())
            return FillType.SOLID;

        switch (mParent.getFillType())
        {
            case FillTypeCore.SOLID:
                return FillType.SOLID;
            case FillTypeCore.PATTERN:
                return FillType.PATTERNED;
            case FillTypeCore.TEXTURE:
                return FillType.TEXTURED;
            case FillTypeCore.PICTURE:
                return FillType.PICTURE;
            case FillTypeCore.SHADE:
            case FillTypeCore.SHADE_CENTER:
            case FillTypeCore.SHADE_SHAPE:
            case FillTypeCore.SHADE_SCALE:
            case FillTypeCore.SHADE_TITLE:
            case FillTypeCore.SHADE_UNSCALE:
                return FillType.GRADIENT;
            case FillTypeCore.BACKGROUND:
                return FillType.BACKGROUND;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "FillType");
        }
    }

    /// <summary>
    /// Gets or sets the angle of the gradient fill.
    /// </summary>
    public double getGradientAngle()
    { return mParent.getGradientAngle(); }
    public void setGradientAngle(double value) { mParent.setGradientAngle(value); }

    /// <summary>
    /// Gets the gradient variant <see cref="Drawing.GradientVariant"/> for the fill.
    /// </summary>
    public /*GradientVariant*/int getGradientVariant() { return mParent.getGradientVariant(); }

    /// <summary>
    /// Gets the gradient style <see cref="Drawing.GradientStyle"/> for the fill.
    /// </summary>
    public /*GradientStyle*/int getGradientStyle() { return mParent.getGradientStyle(); }

    /// <summary>
    /// Gets a collection of <see cref="GradientStop"/> objects for the fill.
    /// </summary>
    public GradientStopCollection getGradientStops() { return mParent.getGradientStops(); }

    /// <summary>
    /// The parent object.
    /// </summary>
    private /*final*/ IFillable mParent;

    private static final String ERROR_PATTERNED = "PatternType.None cannot be applied directly.";
}

