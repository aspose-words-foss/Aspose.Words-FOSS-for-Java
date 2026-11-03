// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;
import java.util.ArrayList;
import com.aspose.MathUtil;
import com.aspose.DrColor;


/// <summary>
/// 20.1.8.41 lin (Linear Gradient Fill)
/// This element specifies a linear gradient.
/// </summary>
class DmlLinearGradient implements IDmlGradient
{
    /// <summary>
    /// Initializes a new instance of the <see cref="DmlLinearGradient" /> class.
    /// </summary>
    DmlLinearGradient()
    {
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="DmlLinearGradient" /> class.
    /// </summary>
    DmlLinearGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant, IThemeProvider themeProvider)
    {
        setAngle(DmlAngle.fromDegrees(getDegrees(style, variant)));
        mIsScaled = true;
        mThemeProvider = themeProvider;
    }

    public IDmlGradient deepClone()
    {
        DmlLinearGradient result = new DmlLinearGradient();
        result.setAngle(getAngle());
        result.isScaled(isScaled());
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlLinearGradient value = (DmlLinearGradient)obj;

        return (value.isScaled() == isScaled()) && msObject.equals(value.getAngle(), getAngle());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= msNumber.doubleHashCode(getAngle().getValue());
        hash ^= msNumber.booleanHashCode(isScaled());
        return hash;
    }

    /// <summary>
    /// Creates collection of gradient stops for any linear style gradient (i.e. Horizontal,
    /// Vertical, DiagonalDown or DiagonalUp).
    /// </summary>
    /// <dev>
    /// The behavior mimics Word VBA.
    /// Note, all linear style gradient stops depend on variant of the gradient only.
    /// Also the order of gradient stops is meaningful. In VBA Word determines gradient variant
    /// and style depending on this order. And as the result the ForeColor and BackColor,
    /// for example, also depends on it.
    /// </dev>
    static ArrayList<DmlGradientStop> createGradientStops(DmlColor color1, DmlColor color2, /*GradientVariant*/int variant)
    {
        ArrayList<DmlGradientStop> gradientStops = new ArrayList<DmlGradientStop>();
        switch (variant)
        {
            // Variants 1-2 are two-colored, and 3-4 are three-colored in VBA.
            case GradientVariant.VARIANT_1:
            case GradientVariant.VARIANT_2:
                gradientStops.add(new DmlGradientStop(0.0, color1));
                gradientStops.add(new DmlGradientStop(1.0, color2));
                break;
            case GradientVariant.VARIANT_3:
                gradientStops.add(new DmlGradientStop(0.0, color1));
                gradientStops.add(new DmlGradientStop(0.5, color2));
                gradientStops.add(new DmlGradientStop(1.0, color1));
                break;
            case GradientVariant.VARIANT_4:
                gradientStops.add(new DmlGradientStop(0.5, color1));
                gradientStops.add(new DmlGradientStop(0.0, color2));
                gradientStops.add(new DmlGradientStop(1.0, color2));
                break;
            default:
                throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "variant" + "\r\nParameter value: " + variant);
        }

        return gradientStops;
    }

    /// <summary>
    /// Returns gradient degree for a specified gradient style and variant.
    /// </summary>
    /// <remarks>
    /// The values are taken from the Word "Format Shape" -> "Fill" dialog.
    /// </remarks>
    private static double getDegrees(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        double degrees;
        switch (style)
        {
            case GradientStyle.VERTICAL:
                degrees = 0.0;
                break;
            case GradientStyle.DIAGONAL_UP:
                degrees = 45.0;
                break;
            case GradientStyle.HORIZONTAL:
                degrees = 90.0;
                break;
            case GradientStyle.DIAGONAL_DOWN:
                degrees = 135.0;
                break;
            default:
                degrees = 0.0;
                break;
        }

        if ((variant == GradientVariant.VARIANT_2) || (variant == GradientVariant.VARIANT_4))
            degrees += 180.0;

        return degrees;
    }


    //>>>>>>>> #region  IDmlGradient implementation
    public void /*IDmlGradient.*/write(IDmlShapeWriterContext writer, boolean isTextEffect)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        String tagName = isTextEffect ? "w14:lin" : "a:lin";
        builder.startElement(tagName);
        builder.writeAttribute((isTextEffect ? "w14:ang" : "ang"), getAngle().getValue());
        builder.writeAttribute((isTextEffect ? "w14:scaled" : "scaled"), isScaled());
        builder.endElement(tagName);
    }

    /// <summary>
    /// Returns the gradient variant for the specified fill as an integer value from 1 to 4, or 0 if not defined.
    /// </summary>
    /// <remarks>
    /// See <see cref="GetDegrees"/> for details.
    /// </remarks>
    public /*GradientVariant*/int /*IDmlGradient.*/getGradientVariant(ArrayList<DmlGradientStop> gradientStops)
    {
        /*GradientVariant*/int variant;

        double degrees = getAngle().getValueInDegrees();
        if (MathUtil.areEqual(degrees, 0.0) ||
            MathUtil.areEqual(degrees, 45.0) ||
            MathUtil.areEqual(degrees, 90.0) ||
            MathUtil.areEqual(degrees, 135.0))
            variant = GradientVariant.VARIANT_1;
        else if (MathUtil.areEqual(degrees, 180.0) ||
                 MathUtil.areEqual(degrees, 225.0) ||
                 MathUtil.areEqual(degrees, 270.0) ||
                 MathUtil.areEqual(degrees, 315.0))
            variant = GradientVariant.VARIANT_2;
        else
            return GradientVariant.NONE;

        if (gradientStops != null)
        {
            // If gradientStops count is greater 2, then there can be variants 3 or 4.
            if (gradientStops.size() == 3)
            {
                // If gradient stops count equals to 3, then VBA checks also corresponding colors(see CreateGradientStop()).
                DmlGradientStop stopA = gradientStops.get((int)variant - 1);
                DmlGradientStop stopB = gradientStops.get(2);
                if (ArgumentUtil.bothAreNotNull(stopA, stopB))
                {
                    DrColor colorA = stopA.getColor().createDrColor(mThemeProvider, null);
                    DrColor colorB = stopB.getColor().createDrColor(mThemeProvider, null);
                    if (colorA.equals(colorB))
                    {
                        // C++ workaround
                        variant = (/*GradientVariant*/int)((int)variant + 2);
                    }

                }
            }
            else if (gradientStops.size() > 3)
            {
                // If gradient stops count is more than 3, then VBA returns variant 3 or 4 only if all colors are equal.
                DrColor prevColor = gradientStops.get(0).getColor().createDrColor(mThemeProvider, null);
                for (int i = 1; i < gradientStops.size() - 1; i++)
                {
                    DrColor curColor = gradientStops.get(i).getColor().createDrColor(mThemeProvider, null);
                    if (!prevColor.equals(curColor))
                        return variant;

                    prevColor = curColor;
                }

                // C++ workaround
                variant = (/*GradientVariant*/int)((int)variant + 2);
            }
        }

        return variant;
    }

    public boolean /*IDmlGradient.*/getAreColorsInReverseOrder() { return false; }

    /// <summary>
    /// Gets <see cref="GradientStyle"/> of the gradient.
    /// </summary>
    public /*GradientStyle*/int /*IDmlGradient.*/getGradientStyle()
    {
        double degrees = getAngle().getValueInDegrees();
        if (MathUtil.areEqual(degrees, 0.0) || MathUtil.areEqual(degrees, 180.0))
            return GradientStyle.VERTICAL;

        if (MathUtil.areEqual(degrees, 45.0) || MathUtil.areEqual(degrees, 225.0))
            return GradientStyle.DIAGONAL_UP;

        if (MathUtil.areEqual(degrees, 90.0) || MathUtil.areEqual(degrees, 270.0))
            return GradientStyle.HORIZONTAL;

        if (MathUtil.areEqual(degrees, 135.0) || MathUtil.areEqual(degrees, 315.0))
            return GradientStyle.DIAGONAL_DOWN;

        return GradientStyle.NONE;
    }
    //<<<<<<<< #endregion 

    /// <summary>
    /// Specifies the direction of color change for the gradient.
    /// To define this angle, let its value be x measured clockwise.
    /// Then ( -sin x, cos x ) is a vector parallel to the line of
    /// constant color in the gradient fill.
    /// </summary>
    DmlAngle getAngle() { return mAngle; }
    void setAngle(DmlAngle value) { mAngle = value; }

    /// <summary>
    /// Whether the gradient angle scales with the fill region.
    /// Mathematically, if this flag is true, then the gradient vector ( cos x , sin x )
    /// is scaled by the width (w) and height (h) of the fill region, so that the vector
    /// becomes ( w cos x, h sin x ) (before normalization). Observe that now if the
    /// gradient angle is 45 degrees, the gradient vector is ( w, h ), which goes
    /// from top-left to bottom-right of the fill region. If this flag is false,
    /// the gradient angle is independent of the fill region and is not scaled
    /// using the manipulation described above. So a 45-degree gradient angle always
    /// give a gradient band whose line of constant color is parallel to the vector (1, -1).
    /// </summary>
    boolean isScaled() { return mIsScaled; }
    void isScaled(boolean value) { mIsScaled = value; }

    private DmlAngle mAngle = new DmlAngle(); // For Java
    private boolean mIsScaled;
    private /*final*/ IThemeProvider mThemeProvider;
    private static final String VALUE_OUT_OF_RANGE = "The specified value is out of range.";
}

