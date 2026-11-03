// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/05/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;


/// <summary>
/// Implements interface for object that can have <see cref="ShadowFormat"/>.
/// </summary>
interface IShadow
{
    /// <summary>
    /// Removes shadow from the parent object;
    /// </summary>
    public void removeShadow();

    /// <summary>
    /// Gets or sets a <see cref="ShadowType"/> value for the shadow effect.
    /// </summary>
    public /*ShadowType*/int getShadowType(); public void setShadowType(/*ShadowType*/int value);

    /// <summary>
    /// Gets a boolean value indicating whether shadow effect is visible.
    /// </summary>
    public boolean getVisible();

    /// <summary>
    /// Gets or sets <see cref="System.Drawing.Color"/> object that represents the color for a shadow effect.
    /// </summary>
    public Color getShadowColors(); public void setShadowColors(Color value);

    /// <summary>
    /// Gets or sets the degree of transparency for a shadow effect.
    /// </summary>
    public double getShadowTransparency(); public void setShadowTransparency(double value);
}

