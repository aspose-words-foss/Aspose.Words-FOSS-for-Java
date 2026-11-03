// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/03/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;


/// <summary>
/// Implements interface for object that can have <see cref="GlowFormat"/>.
/// </summary>
interface IGlow
{
    /// <summary>
    /// Removes glow from the parent object;
    /// </summary>
    public void removeGlow();

    /// <summary>
    /// Gets or sets a Color object that represents the color for a glow effect.
    /// </summary>
    public Color getColor(); public void setColor(Color value);

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree
    /// of transparency for the glow effect.
    /// </summary>
    public double getTransparency(); public void setTransparency(double value);

    /// <summary>
    /// Gets or sets the length of the radius for a glow effect.
    /// </summary>
    public double getRadius(); public void setRadius(double value);
}

