// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


/// <summary>
/// Interface to define a common public data for <see cref="DmlOutline"/> and <see cref="Stroke"/>.
/// </summary>
interface IStroke
{
    /// <summary>
    /// Defines whether the path will be stroked.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>true</c>.</p>
    /// </remarks>
    public boolean getOn(); public void setOn(boolean value);

    /// <summary>
    /// Defines the brush thickness that strokes the path of a shape in points.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.75.</p>
    /// </remarks>
    public double getWeight(); public void setWeight(double value);

    /// <summary>
    /// Defines the color of a stroke.
    /// </summary>
    public DrColor getColorInternal(); public void setColorInternal(DrColor value);

    /// <summary>
    /// Represents an unmodified base color of a stroke.
    /// </summary>
    public DrColor getColorInternalUnmodified();

    /// <summary>
    /// Defines the color of a stroke.
    /// </summary>
    /// <remarks>
    /// It seems in MS Word there is no way to set PatternFill for outline
    /// if we work with Dml shape (it is possible for VML Shapes only).
    /// </remarks>
    public DrColor getColor2Internal(); public void setColor2Internal(DrColor value);

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
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.JoinStyle.Round"/>.</p>
    /// </remarks>
    public /*JoinStyle*/int getJoinStyle(); public void setJoinStyle(/*JoinStyle*/int value);

    /// <summary>
    /// Defines the cap style for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.EndCap.Flat"/>.</p>
    /// </remarks>
    public /*EndCap*/int getEndCap(); public void setEndCap(/*EndCap*/int value);

    /// <summary>
    /// Defines the line style of the stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ShapeLineStyle.Single"/>.</p>
    /// </remarks>
    public /*ShapeLineStyle*/int getLineStyle(); public void setLineStyle(/*ShapeLineStyle*/int value);

    /// <summary>
    /// Defines the arrowhead for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowType.None"/>.</p>
    /// </remarks>
    public /*ArrowType*/int getStartArrowType(); public void setStartArrowType(/*ArrowType*/int value);

    /// <summary>
    /// Defines the arrowhead for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowType.None"/>.</p>
    /// </remarks>
    public /*ArrowType*/int getEndArrowType(); public void setEndArrowType(/*ArrowType*/int value);

    /// <summary>
    /// Defines the arrowhead width for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowWidth.Medium"/>.</p>
    /// </remarks>
    public /*ArrowWidth*/int getStartArrowWidth(); public void setStartArrowWidth(/*ArrowWidth*/int value);

    /// <summary>
    /// Defines the arrowhead length for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowLength.Medium"/>.</p>
    /// </remarks>
    public /*ArrowLength*/int getStartArrowLength(); public void setStartArrowLength(/*ArrowLength*/int value);

    /// <summary>
    /// Defines the arrowhead width for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowWidth.Medium"/>.</p>
    /// </remarks>
    public /*ArrowWidth*/int getEndArrowWidth(); public void setEndArrowWidth(/*ArrowWidth*/int value);

    /// <summary>
    /// Defines the arrowhead length for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ArrowLength.Medium"/>.</p>
    /// </remarks>
    public /*ArrowLength*/int getEndArrowLength(); public void setEndArrowLength(/*ArrowLength*/int value);

    /// <summary>
    /// Defines the amount of transparency of a stroke. Valid range is from 0 to 1.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1.</p>
    /// </remarks>
    public double getOpacity(); public void setOpacity(double value);

    /// <summary>
    /// Defines the image for a stroke image or pattern fill.
    /// </summary>
    public 
    byte[] getImageBytes() throws Exception;

    /// <summary>
    /// Defines the type of fill used for the background of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Core.LineFillType.Solid"/>.</p>
    /// </remarks>
    public /*LineFillType*/int getLineFillType(); public void setLineFillType(/*LineFillType*/int value);

    /// <summary>
    /// Gets or sets a <see cref="DmlFill"/> object of the stroke fill.
    /// </summary>
    public DmlFill getStrokeFill(); public void setStrokeFill(DmlFill value);
}

