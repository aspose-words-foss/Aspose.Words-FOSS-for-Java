// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


/// <summary>
/// Interface to define a common data for <see cref="Aspose.Words.Drawing.Core.Dml.Fills.DmlFill"/> and <see cref="Aspose.Words.Drawing.Core.VmlFill"/>.
/// </summary>
interface IFill
{
    public void setImageBytes(byte[] imageBytes);

    /// <summary>
    /// Gets or sets a color.
    /// </summary>
    public DrColor getColorInternal(); public void setColorInternal(DrColor value);

    /// <summary>
    /// Gets a base color without modifiers.
    /// </summary>
    public DrColor getColorInternalUnmodified();

    /// <summary>
    /// Gets or sets a background color.
    /// </summary>
    public DrColor getColor2Internal(); public void setColor2Internal(DrColor value);

    /// <summary>
    /// Gets the array of custom gradient colors.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>null</c>.</p>
    /// </remarks>
    public GradientColor[] getGradientColors();

    /// <summary>
    /// Defines the transparency of a fill. Valid range from 0 to 1, where 0 is fully transparent and 1 is fully opaque.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1.</p>
    /// </remarks>
    public double getOpacity(); public void setOpacity(double value);

    /// <summary>
    /// Defines the transparency of the second fill color. Valid range from 0 to 1, where 0 is fully transparent and 1 is fully opaque.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1.</p>
    /// </remarks>
    public double getOpacity2(); public void setOpacity2(double value);

    /// <summary>
    /// Determines whether the shape will be filled.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>true</c>.</p>
    /// </remarks>
    public boolean getOn(); public void setOn(boolean value);

    /// <summary>
    /// Determines whether the fill rotates with the specified shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    /// <dev>
    /// IN. Seems this property has not very good name as fill can be defined for also non-Shape objects.
    /// The analogue in VBA has name RotateWithObject. However, in GUI the name is RotateWithShape regardless of
    /// parent object. Also, as I can see, the default value for this property is <c>true</c> in Word (at least Word2016).
    /// </dev>
    public boolean getRotateWithShape(); public void setRotateWithShape(boolean value);

    /// <summary>
    /// Gets the raw bytes of the fill texture or pattern.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>null</c>.</p>
    /// </remarks>
    public 
    byte[] getImageBytes() throws Exception;

    /// <summary>
    /// Defines the angle of a gradient fill in degrees.
    /// </summary>
    /// <remarks>
    /// <p>The vector of a gradient is perpendicular to the vector of the blend direction
    /// from one color to another. The default value is 0 (zero) degrees, which is a horizontal
    /// vector from left to right. Positive angles rotate the gradient in a counter-clockwise direction.</p>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public double getAngle(); public void setAngle(double value);

    /// <summary>
    /// Gets the gradient style for the fill.
    /// https://docs.microsoft.com/en-us/office/vba/api/word.fillformat.gradientstyle
    /// </summary>
    public /*GradientStyle*/int getGradientStyle();

    /// <summary>
    /// Gets the gradient variant for the fill as an integer value from 1 to 4 for most gradient fills or 0 if not defined.
    /// https://docs.microsoft.com/en-us/office/vba/api/word.fillformat.gradientvariant
    /// </summary>
    public /*GradientVariant*/int getGradientVariant();

    // /// <summary>
    // /// Gets or sets a parent object.
    // /// </summary>
    public IFillable getParent(); public void setParent(IFillable value);

    /// <summary>
    /// Locks or unlocks the aspect ration of a fill picture if exists.
    /// </summary>
    public boolean getLockAspectRatio(); public void setLockAspectRatio(boolean value);

    /// <summary>
    /// Defines the left position of the center of a radial gradient.
    /// </summary>
    public double getFocusLeft(); public void setFocusLeft(double value);

    /// <summary>
    /// Defines the top position of the center of a radial gradient.
    /// </summary>
    public double getFocusTop(); public void setFocusTop(double value);

    /// <summary>
    /// Defines the center of a linear gradient fill.
    /// </summary>
    public int getFocus(); public void setFocus(int value);

    /// <summary>
    /// Defines the type of fill.
    /// </summary>
    public /*FillTypeCore*/int getFillType(); public void setFillType(/*FillTypeCore*/int value);
}

