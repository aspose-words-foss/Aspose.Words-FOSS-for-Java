// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/09/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;
import com.aspose.ArgumentUtil;
import com.aspose.DrColor;


/// <summary>
/// Represents one gradient stop.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-graphic-elements/">Working with Graphic Elements</a> documentation article.</para>
/// </summary>
public class GradientStop
{
    /// <summary>
    /// Initializes a new instance of the <see cref="GradientStop" /> class.
    /// </summary>
    /// <param name="color">Represents the color of the gradient stop.</param>
    /// <param name="position">Represents the position of a stop within
    /// the gradient expressed as a percent in range 0.0 to 1.0.</param>
    public GradientStop(Color color, double position)
    {
        ArgumentUtil.checkNotNull(color, "Color");
        ArgumentUtil.checkRangeInclusive(position, 0.0, 1.0, "Position");

        DmlColor dmlColor =  DmlColor.createFromDrColor(DrColor.fromNativeColor(color));
        mDmlGradientStop = new DmlGradientStop(position, dmlColor);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="GradientStop" /> class.
    /// </summary>
    /// <param name="color">Represents the color of the gradient stop.</param>
    /// <param name="position">Represents the position of a stop within
    /// the gradient expressed as a percent in range 0.0 to 1.0.</param>
    /// <param name="transparency">Represents the transparency of a stop within
    /// the gradient expressed as a percent in range 0.0 to 1.0.</param>
    public GradientStop(Color color, double position, double transparency)
    {
    	this(color, position);
	
        setTransparency(transparency);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="GradientStop" /> class.
    /// </summary>
    GradientStop(DmlGradientStop dmlGradientStop, IThemeProvider themeProvider,
        GradientStopCollection parentCollection)
    {
        ArgumentUtil.checkNotNull(dmlGradientStop, "dmlGradientStop");

        mDmlGradientStop = dmlGradientStop;
        mThemeProvider = themeProvider;
        mParentCollection = parentCollection;
    }

    /// <summary>
    /// Removes the gradient stop from the parent <see cref="GradientStopCollection"/>.
    /// </summary>
    public void remove()
    {
        if (getParentCollection() == null)
            throw new IllegalStateException("The gradient stop doesn't belong to any collection.");

        getParentCollection().remove(this);
    }


    /// <summary>
    /// Gets or sets a value representing the color of the gradient stop.
    /// </summary>
    public Color getColor()
    {
        return getDrColor().toColorFixAlpha().toNativeColor();
    }
    public void setColor(Color value)
    {
        ArgumentUtil.checkNotNull(value, "Color");
        getDmlGradientStop().setColor(DmlColor.createFromDrColor(DrColor.fromNativeColor(value)));
    }

    /// <summary>
    /// Gets a value representing the color of the gradient stop without any modifiers.
    /// </summary>
    public Color getBaseColor() { return getDmlColor().createUnmodifiedDrColor(mThemeProvider).toColorFixAlpha().toNativeColor(); }

    /// <summary>
    /// Gets or sets a value representing the position of a stop within the gradient
    /// expressed as a percent in range 0.0 to 1.0.
    /// </summary>
    public double getPosition() { return getDmlGradientStop().getPosition(); }
    public void setPosition(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Position");
        getDmlGradientStop().setPosition(value);
    }

    /// <summary>
    /// Gets or sets a value representing the transparency of the gradient fill
    /// expressed as a percent in range 0.0 to 1.0.
    /// </summary>
    public double getTransparency()
    {
        if (getParentCollection() != null)
            return getParentCollection().getParentFill().getParent().getTransparency(getDmlColor());

        return (getDmlColor().getAlpha() == null) ? 0.0 : getDmlColor().getAlpha().getValue();
    }
    public void setTransparency(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Transparency");

        if (getParentCollection() == null)
            getDmlColor().updateAlpha(value);
        else
            getParentCollection().getParentFill().getParent().setTransparency(getDmlColor(), value);
    }

    /// <summary>
    /// Gets or sets the parent <see cref="GradientStopCollection"/>.
    /// </summary>
    GradientStopCollection getParentCollection() { return mParentCollection; }
    void setParentCollection(GradientStopCollection value)
    {
        mParentCollection = value;

        // After the parent is set, we should recalculate transparency,
        // as it should be stored inverted for non-font fills.
        if ((mParentCollection != null) && (getDmlColor().getAlpha() != null) && !(mParentCollection.getParentFill().getParent() instanceof Font))
            getDmlColor().updateAlpha(1.0 - getDmlColor().getAlpha().getValue());
    }

    /// <summary>
    /// The internal representation of this facade gradient stop.
    /// </summary>
    /// <remarks>
    /// This reference equals to a corresponding stop in internal <see cref="DmlGradientFill.GradientStops"/>
    /// of the parent collection when the parent collection is set up.
    /// </remarks>
    DmlGradientStop getDmlGradientStop() { return mDmlGradientStop; };

    private DmlGradientStop mDmlGradientStop;

    /// <summary>
    /// Gets color of the gradient stop.
    /// </summary>
    private DmlColor getDmlColor() { return getDmlGradientStop().getColor(); }

    /// <summary>
    /// Gets color of the gradient stop.
    /// </summary>
    private DrColor getDrColor() { return getDmlColor().createDrColor(mThemeProvider, null); }

    private GradientStopCollection mParentCollection;
    private /*final*/ IThemeProvider mThemeProvider;
}

