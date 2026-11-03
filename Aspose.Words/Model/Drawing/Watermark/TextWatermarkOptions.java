// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/22/2020 by Artem Ptitsin.

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.Drawing.msColor;


/// <summary>
/// Contains options that can be specified when adding a watermark with text.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-watermark/">Working with Watermark</a> documentation article.</para>
/// </summary>
public class TextWatermarkOptions
{
    /// <summary>
    /// Gets or sets font family name. The default value is "Calibri".
    /// </summary>
    public String getFontFamily() { return mFontFamily; }
    public void setFontFamily(String value) { mFontFamily = value; }

    /// <summary>
    /// Gets or sets font color. The default value is <see cref="Color.Silver"/>.
    /// </summary>
    public Color getColor() { return mColor; }
    public void setColor(Color value) { mColor = value; }

    /// <summary>
    /// Gets or sets a font size. The default value is 0 - auto.
    /// </summary>
    /// <remarks>
    /// <p>Valid values range from 0 to 65.5 inclusive.</p>
    /// <p> Auto font size means that the watermark will be scaled to its max width and max height relative to
    /// the page margins.</p>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">
    /// Throws when argument was out of the range of valid values.
    /// </exception>
    public float getFontSize() { return mFontSize; }
    public void setFontSize(float value) { setFontSizeInternal(value); }

    /// <summary>
    /// Gets or sets a boolean value which is responsible for opacity of the watermark.
    /// The default value is <c>true</c>.
    /// </summary>
    public boolean isSemitrasparent() { return mIsSemitransparent; }
    public void isSemitrasparent(boolean value) { mIsSemitransparent = value; }

    /// <summary>
    /// Gets or sets layout of the watermark. The default value is <see cref="WatermarkLayout.Diagonal"/>.
    /// </summary>
    public /*WatermarkLayout*/int getLayout() { return mLayout; }
    public void setLayout(/*WatermarkLayout*/int value) { mLayout = value; }

    boolean isAutoSize() { return mFontSize == AUTO_SIZE; }

    private void setFontSizeInternal(double value)
    {
        mFontSize = (float)ArgumentUtil.validateRange(value, AUTO_SIZE, AUTO_SIZE, WordUtil.MAX_FONT_SIZE,
            WordUtil.MAX_FONT_SIZE, true, "Size");
    }

    private String mFontFamily = "Calibri";
    private Color mColor = msColor.getSilver();
    private boolean mIsSemitransparent = true;
    private float mFontSize = AUTO_SIZE;
    private /*WatermarkLayout*/int mLayout = WatermarkLayout.DIAGONAL;
    private static final float AUTO_SIZE = 0f;
}

