// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/04/2024 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.ScRgbUtil;
import com.aspose.MathUtil;


/// <summary>
/// The base class for all color modifiers operating in the scRGB color space.
/// </summary>
abstract class DmlScRgbColorModifier extends DmlPercentageBasedColorModifier
{
    private /*final*/ boolean mGammaShift = false;

    /// <summary>
    /// Default ctor.
    /// </summary>
    protected DmlScRgbColorModifier()
    {
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="value">
    /// Value represented as a fraction.
    /// </param>
    /// <param name="gammaShift">
    /// Specifies that the effect is rendered on the sRGB gamma shift of the input color.
    /// <seealso cref="DmlGamma"/>
    /// </param>
    protected DmlScRgbColorModifier(double value, boolean gammaShift)
    {
        setValue(value);
        mGammaShift = gammaShift;
    }

    /// <remarks>
    /// Do not override this method in child classes.
    /// All the color processing should be implemented in <see cref="ModifyComponent(double, int)"/>
    /// Alpha components is not processed.
    /// </remarks>
    public /*override*/ DrColor modify(DrColor color)
    {
        int argbIn = color.toArgb();

        // Modify R,G,B color components.
        // Alpha is not changed across the color spaces.
        int argbOut = color.getA();
        for (int i = 0; i < 3; i++)
        {
            argbOut <<= 8;

            double normComp = getNormalizedComponent(argbIn, i);
            normComp = preprocess(normComp);

            // Apply the core modification in scRGB space and move back to sRGB afterwards.
            normComp = ScRgbUtil.linearize(normComp);
            normComp = modifyComponent(normComp, i);
            normComp = ScRgbUtil.deLinearize(normComp);

            normComp = postprocess(normComp);
            argbOut |= MathUtil.fitToRange(MathUtil.doubleToInt(normComp * 255.0), 0, 255);
        }

        return new DrColor(argbOut);
    }

    /// <summary>
    /// Gets a normalized color component.
    /// </summary>
    /// <param name="argb">An integer representing for color component.</param>
    /// <param name="i">Index of the component where 0 is red, 1 is green and 2 is blue.</param>
    private static double getNormalizedComponent(int argb, int i)
    {
        // () << 3 is an optimized () * 8
        argb = (argb >> ((2 - i) << 3)) & 0xff;
        return argb / 255.0;
    }

    private double preprocess(double component)
    {
        // At first glance, it seems that calculations should be carried out in the sRGB color space,
        // but in reality MS Word performs unnecessary steps to convert colors between color spaces,
        // leading to calculation errors. So to produce the result similar to MS Word
        // this sequence should be done without color space optimizations.
        return getGammaShift() ? ScRgbUtil.deLinearize(component) : component;
    }

    private double postprocess(double component)
    {
        return getGammaShift() ? ScRgbUtil.linearize(component) : component;
    }

    /// <summary>
    /// Modifies the color component.
    /// </summary>
    /// <param name="normComp">Normalized color component.</param>
    /// <param name="compIndex">Index of the color component in the RGB triple.</param>
    protected abstract double modifyComponent(double normComp, int compIndex);

    /// <summary>
    /// Indicates that the effect is rendered on the sRGB gamma shift of the input color.
    /// </summary>
    protected boolean getGammaShift() { return mGammaShift; }
}

