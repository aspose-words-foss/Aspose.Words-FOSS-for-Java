// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/09/2019 by Artem Ptitsin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import java.awt.Color;


/// <summary>
/// Represents horizontal rule formatting.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-shapes/">Working with Shapes</a> documentation article.</para>
/// </summary>
/// <dev>
/// WORDSNET-18182 This class has been added to provide public horizontal rule properties.
/// </dev>
public class HorizontalRuleFormat
{
    HorizontalRuleFormat(Shape shape)
    {
        mShape = shape;
    }

    /// <summary>
    /// Sets width percent value to the horizontal rule.
    /// </summary>
    private void setWidthPercentInternal(double value)
    {
        value = ArgumentUtil.validateRange(value, 1.0, 1.0, 100.0, 100.0, true, "WidthPercent");
        mShape.getHorizontalRule().setPercent(value);
    }

    /// <summary>
    /// Sets height value to the horizontal rule.
    /// </summary>
    private void setHeightInternal(double value) throws Exception
    {
        value = ArgumentUtil.validateRange(value, 0.0, 0.0, 1584.0, 1584.0, true, "Height");
        mShape.setHeight(value);
    }

    /// <summary>
    /// Gets or sets the length of the specified horizontal rule expressed as a percentage of the window width.
    /// </summary>
    /// <remarks>
    /// <p>Valid values ​​range from 1 to 100 inclusive.</p>
    /// <p>The default value is 100.</p>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">
    /// Throws when argument was out of the range of valid values.
    /// </exception>
    public double getWidthPercent() { return mShape.getHorizontalRule().getPercent(); }
    public void setWidthPercent(double value) { setWidthPercentInternal(value); }

    /// <summary>
    /// Gets or sets the height of the horizontal rule.
    /// </summary>
    /// <remarks>
    /// <p>This is a shortcut to the <see cref="Aspose.Words.Drawing.ShapeBase.Height"/> property.</p>
    /// <p>Valid values ​​range from 0 to 1584 inclusive.</p>
    /// <p>The default value is 1.5.</p>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">
    /// Throws when argument was out of the range of valid values.
    /// </exception>
    public double getHeight() { return mShape.getHeight(); }
    public void setHeight(double value) throws Exception { setHeightInternal(value); }

    /// <summary>
    /// Indicates the presence of 3D shading for the horizontal rule.
    /// If <c>true</c>, then the horizontal rule is without 3D shading and solid color is used.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getNoShade() { return mShape.getHorizontalRule().getNoShade(); }
    public void setNoShade(boolean value) { mShape.getHorizontalRule().setNoShade(value); }

    /// <summary>
    /// Gets or sets the brush color that fills the horizontal rule.
    /// </summary>
    /// <remarks>
    /// <p>This is a shortcut to the <see cref="Fill.Color"/> property.</p>
    /// <p>The default value is
    /// <see cref="System.Drawing.Color.Gray"/>.
    /// </p>
    /// </remarks>
    public Color getColor() { return mShape.getFillColor(); }
    public void setColor(Color value) { mShape.setFillColor(value); }

    /// <summary>
    /// Gets or sets the alignment of the horizontal rule.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="HorizontalRuleAlignment.Left"/>.</p>
    /// </remarks>
    public /*HorizontalRuleAlignment*/int getAlignment() { return mShape.getHorizontalRule().getAlign(); }
    public void setAlignment(/*HorizontalRuleAlignment*/int value) { mShape.getHorizontalRule().setAlign(value); }

    private /*final*/ Shape mShape;
}

