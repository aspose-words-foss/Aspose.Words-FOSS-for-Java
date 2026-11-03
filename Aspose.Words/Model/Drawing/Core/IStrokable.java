// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/04/2021 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


/// <summary>
/// Represents an interface to implement for object that can be stroked.
/// </summary>
interface IStrokable
{
    /// <summary>
    /// Gets or sets the foreground color of the stroke.
    /// </summary>
    public DrColor getStrokeForeColor(); public void setStrokeForeColor(DrColor value);

    /// <summary>
    /// Gets the base foreground color without modifiers of the stroke.
    /// </summary>
    public DrColor getStrokeBaseForeColor();

    /// <summary>
    /// Gets or sets the background color of the stroke.
    /// </summary>
    public DrColor getStrokeBackColor(); public void setStrokeBackColor(DrColor value);

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the stroke foreground color.
    /// </summary>
    public 
    /*ThemeColor*/int getStrokeForeThemeColor() throws Exception; public void setStrokeForeThemeColor(/*ThemeColor*/int value) throws Exception;

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the stroke background color.
    /// </summary>
    public 
    /*ThemeColor*/int getStrokeBackThemeColor() throws Exception; public void setStrokeBackThemeColor(/*ThemeColor*/int value) throws Exception;

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the stroke foreground color.
    /// </summary>
    public double getStrokeForeTintAndShade(); public void setStrokeForeTintAndShade(double value);

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the stroke background color.
    /// </summary>
    public double getStrokeBackTintAndShade(); public void setStrokeBackTintAndShade(double value);

    /// <summary>
    /// Gets or sets a flag indicating whether the stroke is visible.
    /// </summary>
    public boolean getStrokeVisible(); public void setStrokeVisible(boolean value);

    /// <summary>
    /// Gets or sets a value between 0.0 (opaque) and 1.0 (clear) representing the degree of transparency
    /// of the stroke.
    /// </summary>
    public double getStrokeTransparency(); public void setStrokeTransparency(double value);

    /// <summary>
    /// Defines the brush thickness that strokes the path of a visual element in points.
    /// </summary>
    public double getWeight(); public void setWeight(double value);

    /// <summary>
    /// Specifies the dot and dash pattern for a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.DashStyle.Solid"/>.</p>
    /// </remarks>
    public /*DashStyle*/int getDashStyle(); public void setDashStyle(/*DashStyle*/int value);

    /// <summary>
    /// Defines the join style of a polyline.
    /// </summary>
    public /*JoinStyle*/int getJoinStyle(); public void setJoinStyle(/*JoinStyle*/int value);

    /// <summary>
    /// Defines the cap style for the end of a stroke.
    /// </summary>
    public /*EndCap*/int getEndCap(); public void setEndCap(/*EndCap*/int value);

    /// <summary>
    /// Defines the line style of the stroke.
    /// </summary>
    public /*ShapeLineStyle*/int getLineStyle(); public void setLineStyle(/*ShapeLineStyle*/int value);

    /// <summary>
    /// Defines the arrowhead for the start of a stroke.
    /// </summary>
    public /*ArrowType*/int getStartArrowType(); public void setStartArrowType(/*ArrowType*/int value);

    /// <summary>
    /// Defines the arrowhead for the end of a stroke.
    /// </summary>
    public /*ArrowType*/int getEndArrowType(); public void setEndArrowType(/*ArrowType*/int value);

    /// <summary>
    /// Defines the arrowhead width for the start of a stroke.
    /// </summary>
    public /*ArrowWidth*/int getStartArrowWidth(); public void setStartArrowWidth(/*ArrowWidth*/int value);

    /// <summary>
    /// Defines the arrowhead length for the start of a stroke.
    /// </summary>
    public /*ArrowLength*/int getStartArrowLength(); public void setStartArrowLength(/*ArrowLength*/int value);

    /// <summary>
    /// Defines the arrowhead width for the end of a stroke.
    /// </summary>
    public /*ArrowWidth*/int getEndArrowWidth(); public void setEndArrowWidth(/*ArrowWidth*/int value);

    /// <summary>
    /// Defines the arrowhead length for the end of a stroke.
    /// </summary>
    public /*ArrowLength*/int getEndArrowLength(); public void setEndArrowLength(/*ArrowLength*/int value);

    /// <summary>
    /// Gets the image for a stroke image or pattern fill.
    /// </summary>
    public 
    byte[] getStrokeImageBytes() throws Exception;

    /// <summary>
    /// Defines the type of fill used for the background of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="Core.LineFillType.Solid"/>.
    /// </remarks>
    public /*LineFillType*/int getLineFillType(); public void setLineFillType(/*LineFillType*/int value);

    /// <summary>
    /// Gets a <see cref="IThemeProvider"/> object.
    /// </summary>
    public IThemeProvider getStrokeThemeProvider();

    /// <summary>
    /// Gets or sets a <see cref="DmlFill"/> object of the stroke fill.
    /// </summary>
    public DmlFill getStrokeFill(); public void setStrokeFill(DmlFill value);
}

