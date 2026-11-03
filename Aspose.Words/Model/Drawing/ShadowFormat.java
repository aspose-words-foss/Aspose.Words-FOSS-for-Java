// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/05/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;


/// <summary>
/// Represents shadow formatting for an object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-graphic-elements/">Working with Graphic Elements</a> documentation article.</para>
/// </summary>
public class ShadowFormat
{
    /// <summary>
    /// Creates ShadowFormat object with a specified parent.
    /// </summary>
    ShadowFormat(IShadow parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Clears shadow format.
    /// </summary>
    public void clear()
    {
        mParent.removeShadow();
    }

    /// <summary>
    /// Gets or sets the specified <see cref="ShadowType"/> for ShadowFormat.
    /// </summary>
    /// <remarks>
    /// Setting a new shadow type will reset Color and Transparency values to their default ones.
    /// Therefore, it makes sense to first set the desired shadow type and only then Color and Transparency values.
    /// </remarks>
    public /*ShadowType*/int getType() { return mParent.getShadowType(); }
    public void setType(/*ShadowType*/int value)
    {
        if (mParent.getShadowType() != value )
            mParent.setShadowType(value);
    }

    /// <summary>
    /// Returns <c>true</c> if the formatting applied to this instance is visible.
    /// </summary>
    /// <remarks>
    /// Unlike <see cref="Clear()"/>, assigning <c>false</c> to Visible does not clear the formatting,
    /// it only hides the shape effect.
    /// </remarks>
    public boolean getVisible() { return mParent.getVisible(); }
    void setVisible(boolean value) { throw new UnsupportedOperationException(); }

    /// <summary>
    /// Gets or sets a <see cref="System.Drawing.Color"/> object that represents the color for the shadow.
    /// The default value is <see cref="System.Drawing.Color.Black"/>.
    /// </summary>
    public Color getColor() { return mParent.getShadowColors(); }
    public void setColor(Color value) { mParent.setShadowColors(value); }

    /// <summary>
    /// Gets or sets the degree of transparency for the shadow effect as a value between 0.0 (opaque) and 1.0 (clear).
    /// The default value is 0.0.
    /// </summary>
    public double getTransparency() { return mParent.getShadowTransparency(); }
    public void setTransparency(double value) { mParent.setShadowTransparency(value); }

    /// <summary>
    /// The parent object.
    /// </summary>
    private /*final*/ IShadow mParent;
}

