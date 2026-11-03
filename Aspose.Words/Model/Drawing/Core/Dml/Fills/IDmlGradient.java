// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Represents a interface for gradient types.
/// </summary>
interface IDmlGradient
{
    public IDmlGradient deepClone();

    public void write(IDmlShapeWriterContext writer, boolean isTextEffect);

    /// <summary>
    /// Returns the gradient variant for the specified fill as an integer value from 1 to 4, or 0 if not defined.
    /// </summary>
    public /*GradientVariant*/int getGradientVariant(ArrayList<DmlGradientStop> gradientStops);

    public boolean getAreColorsInReverseOrder();

    /// <summary>
    /// Gets <see cref="GradientStyle"/> of the gradient.
    /// </summary>
    public /*GradientStyle*/int getGradientStyle();
}

