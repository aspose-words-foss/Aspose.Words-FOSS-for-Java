// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;


/// <summary>
/// Implements interface for object that can be filled.
/// </summary>
interface IFillable
{
    /// <summary>
    /// Changes type of the fill to Solid.
    /// </summary>
    public void solid();

    /// <summary>
    /// Changes type of the fill to preset texture.
    /// </summary>
    public void presetTextured(/*PresetTexture*/int presetTexture);

    /// <summary>
    /// Changes type of the fill to a pattern.
    /// </summary>
    public void patterned(/*PatternType*/int patternType);

    /// <summary>
    /// Returns a PresetTexture for the fill.
    /// </summary>
    public /*PresetTexture*/int getPresetTexture() throws Exception;

    /// <summary>
    /// Returns a PatternType for the fill.
    /// </summary>
    public /*PatternType*/int getPatternType() throws Exception;

    /// <summary>
    /// Changes type of the fill to gradient.
    /// </summary>
    public void twoColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant);

    /// <summary>
    /// Changes type of the fill to gradient.
    /// </summary>
    public void oneColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant, double degree);

    /// <summary>
    /// Changes the fill type to single image.
    /// </summary>
    public void setImage(byte[] imageBytes);

    /// <summary>
    /// Sets specified fill to this object.
    /// </summary>
    public void setFill(IFill fill);

    /// <summary>
    /// Returns a double value representing transparency of a specified color.
    /// </summary>
    public double getTransparency(DmlColor color);

    /// <summary>
    /// Sets a specified value to a transparency of specified color .
    /// </summary>
    public void setTransparency(DmlColor color, double value);

    //>>>>>>>> #region  The public properties of the old Fill object for compatibility.

    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    public Color getFilledColor(); public void setFilledColor(Color value);

    /// <summary>
    /// Gets or sets a boolean value indicating whether a Fill applied to an object is visible.
    /// </summary>
    public boolean getOldOn(); public void setOldOn(boolean value);

    /// <summary>
    /// Gets or sets a double value between 0.0 (clear) and 1.0 (opaque) representing the degree
    /// of opacity of the specified fill.
    /// </summary>
    public double getOldOpacity(); public void setOldOpacity(double value);

    /// <summary>
    /// Gets the raw bytes of the fill texture or pattern.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>null</c>.</p>
    /// </remarks>
    public 
    byte[] getFillableImageBytes() throws Exception;

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    public Color getFillableForeColor(); public void setFillableForeColor(Color value);

    /// <summary>
    /// Gets a Color object that represents the base foreground color for the fill without modifiers.
    /// </summary>
    public Color getFillableBaseForeColor();

    /// <summary>
    /// Gets or sets a Color object that represents the background color for the fill.
    /// </summary>
    public Color getFillableBackColor(); public void setFillableBackColor(Color value);

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the foreground color for the fill.
    /// </summary>
    public 
    /*ThemeColor*/int getFillableForeThemeColor() throws Exception; public void setFillableForeThemeColor(/*ThemeColor*/int value) throws Exception;

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the background color for the fill.
    /// </summary>
    public 
    /*ThemeColor*/int getFillableBackThemeColor() throws Exception; public void setFillableBackThemeColor(/*ThemeColor*/int value) throws Exception;

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the foreground color.
    /// </summary>
    public double getFillableForeTintAndShade(); public void setFillableForeTintAndShade(double value);

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the background color.
    /// </summary>
    public double getFillableBackTintAndShade(); public void setFillableBackTintAndShade(double value);

    /// <summary>
    /// Gets or sets a boolean value indicating whether a Fill applied to an object is visible.
    /// </summary>
    public boolean getFillableVisible(); public void setFillableVisible(boolean value);

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree
    /// of transparency of the specified fill.
    /// </summary>
    public double getFillableTransparency(); public void setFillableTransparency(double value);

    /// <summary>
    /// Gets or sets a boolean value indicating whether the fill rotates with the specified object.
    /// </summary>
    public boolean getRotateWithObject(); public void setRotateWithObject(boolean value);

    /// <summary>
    /// Gets fill type.
    /// </summary>
    public /*FillTypeCore*/int getFillType();

    /// <summary>
    /// Gets or sets the alignment for tile texture fill.
    /// </summary>
    public /*TextureAlignment*/int getTextureAlignment(); public void setTextureAlignment(/*TextureAlignment*/int value);

    /// <summary>
    /// Gets or sets the angle of the gradient fill.
    /// </summary>
    public double getGradientAngle(); public void setGradientAngle(double value);

    /// <summary>
    /// Gets the gradient variant for the fill as an integer value from 1 to 4 for most gradient fills.
    /// </summary>
    public /*GradientVariant*/int getGradientVariant();

    /// <summary>
    /// Gets the gradient style for the fill.
    /// </summary>
    public /*GradientStyle*/int getGradientStyle();

    /// <summary>
    /// Gets a collection of <see cref="GradientStop"/> objects for the fill.
    /// </summary>
    /// <remarks>This is facade for <see cref="DmlGradientFill.GradientStops"/> collection.</remarks>
    public GradientStopCollection getGradientStops();

    /// <summary>
    /// Gets IThemeProvider object.
    /// </summary>
    public IThemeProvider getFillableThemeProvider();
}

