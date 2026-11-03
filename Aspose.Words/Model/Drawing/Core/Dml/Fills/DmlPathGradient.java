// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;
import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.DrColor;


/// <summary>
/// 20.1.8.46 path (Path Gradient)
/// This element defines that a gradient fill follows a path vs. a linear line.
/// </summary>
class DmlPathGradient implements IDmlGradient
{
    /// <summary>
    /// Initializes a new instance of the <see cref="DmlPathGradient" /> class.
    /// </summary>
    DmlPathGradient()
    {
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="DmlPathGradient" /> class.
    /// </summary>
    DmlPathGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant, IThemeProvider themeProvider)
    {
        mFillToRectangle = new DmlPercentageOffsetRectangle(getFillToRectangle(style, variant));
        mPath = DmlPathShadeType.RECTANGLE;
        mThemeProvider = themeProvider;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlPathGradient value = (DmlPathGradient)obj;

        return (value.getPath() == getPath()) && msObject.equals(value.getFillToRectangle(), getFillToRectangle());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= /*enum.hashCode()=(int)value*/getPath();
        hash ^= getFillToRectangle().hashCode();
        return hash;
    }

    /// <summary>
    /// Gets model FillType for the shape of the path.
    /// </summary>
    /*FillTypeCore*/int getPathFillType()
    {
        switch (mPath)
        {
            case DmlPathShadeType.SHAPE:
            case DmlPathShadeType.CIRCLE:
                return FillTypeCore.SHADE_SHAPE;
            case DmlPathShadeType.RECTANGLE:
                return FillTypeCore.SHADE_CENTER;
            default:
                return FillTypeCore.SHADE;
        }
    }

    /// <summary>
    /// Creates collection of gradient stops for <see cref="Aspose.Words.Drawing.GradientStyle.FromCenter"/> gradient.
    /// </summary>
    /// <dev>
    /// The order of gradient stops is meaningful. In VBA Word determines gradient variant and
    /// style depending on this order. And as the result the ForeColor and BackColor,
    /// for example, also depends on it.
    /// </dev>
    static ArrayList<DmlGradientStop> createFromCenterGradientStops(DmlColor color1, DmlColor color2,
        /*GradientVariant*/int variant)
    {
        ArrayList<DmlGradientStop> gradientStops = new ArrayList<DmlGradientStop>();
        switch (variant)
        {
            case GradientVariant.VARIANT_1:
                gradientStops.add(new DmlGradientStop(0.0, color1));
                gradientStops.add(new DmlGradientStop(1.0, color2));
                break;
            case GradientVariant.VARIANT_2:
                gradientStops.add(new DmlGradientStop(1.0, color1));
                gradientStops.add(new DmlGradientStop(0.0, color2));
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
    /// Creates collection of gradient stops for <see cref="Aspose.Words.Drawing.GradientStyle.FromCorner"/> gradient.
    /// </summary>
    /// <dev>
    /// The order of gradient stops is meaningful. In VBA Word determines gradient variant and
    /// style depending on this order. And as the result the ForeColor and BackColor,
    /// for example, also depends on it.
    /// </dev>
    static ArrayList<DmlGradientStop> createFromCornerGradientStops(DmlColor color1, DmlColor color2)
    {
        ArrayList<DmlGradientStop> gradientStops = new ArrayList<DmlGradientStop>();
        gradientStops.add(new DmlGradientStop(0.0, color1));
        gradientStops.add(new DmlGradientStop(1.0, color2));

        return gradientStops;
    }

    /// <summary>
    /// Creates tile rectangle for <see cref="Aspose.Words.Drawing.GradientStyle.FromCorner"/> gradient.
    /// </summary>
    static DmlPercentageOffsetRectangle createFromCornerTileRect(/*GradientVariant*/int variant)
    {
        switch (variant)
        {
            case GradientVariant.VARIANT_1:
                return new DmlPercentageOffsetRectangle(-1, -1, 0.0, 0.0);
            case GradientVariant.VARIANT_2:
                return new DmlPercentageOffsetRectangle(0.0, -1, -1, 0.0);
            case GradientVariant.VARIANT_3:
                return new DmlPercentageOffsetRectangle(-1, 0.0, 0.0, -1);
            case GradientVariant.VARIANT_4:
                return new DmlPercentageOffsetRectangle(0.0, 0.0, -1, -1);
            default:
                throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "variant" + "\r\nParameter value: " + variant);
        }
    }

    /// <summary>
    /// Gets gradient variant for 'FromCorner' gradient.
    /// </summary>
    private /*GradientVariant*/int getFromCornerGradientVariant()
    {
        if (mFillToRectangle.equals(G_CORNER_RECT_1))
            return GradientVariant.VARIANT_1;

        if (mFillToRectangle.equals(G_CORNER_RECT_2))
            return GradientVariant.VARIANT_2;

        if (mFillToRectangle.equals(G_CORNER_RECT_3))
            return GradientVariant.VARIANT_3;

        if (mFillToRectangle.equals(G_CORNER_RECT_4))
            return GradientVariant.VARIANT_4;

        return GradientVariant.NONE;
    }

    /// <summary>
    /// Gets gradient variant for 'FromCenter' gradient.
    /// </summary>
    /// <remarks>
    /// Word determines only variants 1 and 3 in VBA for 'FromCenter' gradient.
    /// The variant 3 is when gradient stops count is either equal to 3 and first and last colors are equal,
    /// or gradient stops more than 3 and they are all equal.
    /// </remarks>
    private /*GradientVariant*/int getFromCenterGradientVariant(ArrayList<DmlGradientStop> gradientStops)
    {
        Debug.assertTrue(mFillToRectangle.equals(G_CENTER_RECT));

        if ((gradientStops == null) || (gradientStops.size() < 3))
            return GradientVariant.VARIANT_1;

        if (gradientStops.size() == 3)
        {
            // If there are three gradient stops, then this can be variant 3 or 4.
            // First check if this is variant 3.
            DmlGradientStop stopA = gradientStops.get(0);
            DmlGradientStop stopB = gradientStops.get(2);
            if (ArgumentUtil.bothAreNotNull(stopA, stopB))
            {
                DrColor colorA = stopA.getColor().createDrColor(mThemeProvider, null);
                DrColor colorB = stopB.getColor().createDrColor(mThemeProvider, null);
                if (colorA.equals(colorB))
                    return GradientVariant.VARIANT_3;

                // If we here, then this is actually can be variant 4.
                // However, Word returns 3 and we should mimic such behavior.
                stopA = gradientStops.get(1);
                if (stopA != null)
                {
                    colorA = stopA.getColor().createDrColor(mThemeProvider, null);
                    if (colorA.equals(colorB))
                        return GradientVariant.VARIANT_3;
                }
            }

            // There are no equal colors, so this is variant 1.
            return GradientVariant.VARIANT_1;
        }

        // If we here, then gradient stops count greater 3.
        // If all colors are equal, then this is variant 3, otherwise this is variant 1.
        DrColor prevColor = gradientStops.get(0).getColor().createDrColor(mThemeProvider, null);
        for (int i = 1; i < gradientStops.size() - 1; i++)
        {
            DrColor curColor = gradientStops.get(i).getColor().createDrColor(mThemeProvider, null);
            if (!prevColor.equals(curColor))
                return GradientVariant.VARIANT_1;

            prevColor = curColor;
        }

        return GradientVariant.VARIANT_3;
    }

    /// <summary>
    /// Returns one of the predefined static <see cref="DmlPercentageOffsetRectangle"/> corresponded
    /// to a particular <see cref="GradientStyle"/> and Variant.
    /// </summary>
    /// <remarks>
    /// Note, this should be immutable.
    /// </remarks>
    private static DmlPercentageOffsetRectangle getFillToRectangle(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        switch (style)
        {
            case GradientStyle.FROM_CORNER:
            {
                switch (variant)
                {
                    case GradientVariant.VARIANT_1:
                        return G_CORNER_RECT_1;
                    case GradientVariant.VARIANT_2:
                        return G_CORNER_RECT_2;
                    case GradientVariant.VARIANT_3:
                        return G_CORNER_RECT_3;
                    case GradientVariant.VARIANT_4:
                        return G_CORNER_RECT_4;
                    default:
                        throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "variant" + "\r\nParameter value: " + variant);
                }
            }
            case GradientStyle.FROM_CENTER:
                return G_CENTER_RECT;
            default:
                throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "style" + "\r\nParameter value: " + style);
        }
    }

    //>>>>>>>> #region  IDmlGradient implementation
    public IDmlGradient /*IDmlGradient.*/deepClone()
    {
        DmlPathGradient result = new DmlPathGradient();
        result.setPath(getPath());
        result.setFillToRectangle(getFillToRectangle().deepClone());
        return result;
    }

    public void /*IDmlGradient.*/write(IDmlShapeWriterContext writer, boolean isTextEffect)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        String tagName = isTextEffect ? "w14:path" : "a:path";
        builder.startElement(tagName);
        builder.writeAttribute((isTextEffect ? "w14:path" : "path"), DmlEnum.pathShadeTypeToDml(getPath()));
        DmlWriterUtil.writePercentageOffsetRectangle(getFillToRectangle(), (isTextEffect ? "w14" : "a"), "fillToRect",
            false, builder, isIsoStrict);
        builder.endElement(tagName);
    }

    /// <summary>
    /// Returns the gradient variant for the specified fill as an integer value from 1 to 4 or 0 if not defined.
    /// </summary>
    public /*GradientVariant*/int /*IDmlGradient.*/getGradientVariant(ArrayList<DmlGradientStop> gradientStops)
    {
        if (mFillToRectangle != null)
        {
            return mFillToRectangle.equals(G_CENTER_RECT)
                ? getFromCenterGradientVariant(gradientStops)
                : getFromCornerGradientVariant();
        }

        return GradientVariant.NONE;
    }

    public boolean /*IDmlGradient.*/getAreColorsInReverseOrder() { return true; }

    /// <summary>
    /// Gets <see cref="GradientStyle"/> of the gradient.
    /// </summary>
    public /*GradientStyle*/int /*IDmlGradient.*/getGradientStyle()
    {
        if (mFillToRectangle != null)
        {
            if (mFillToRectangle.equals(G_CENTER_RECT))
                return GradientStyle.FROM_CENTER;

            if (mFillToRectangle.equals(G_CORNER_RECT_1) ||
                mFillToRectangle.equals(G_CORNER_RECT_2) ||
                mFillToRectangle.equals(G_CORNER_RECT_3) ||
                mFillToRectangle.equals(G_CORNER_RECT_4))
                return GradientStyle.FROM_CORNER;
        }

        return GradientStyle.NONE;
    }
    //<<<<<<<< #endregion 

    /// <summary>
    /// 20.1.8.31 fillToRect (Fill To Rectangle)
    /// This element defines the "focus" rectangle for the center shade,
    /// specified relative to the fill tile rectangle. The center shade fills
    /// the entire tile except the margins specified by each attribute.
    /// Each edge of the center shade rectangle is defined by a percentage offset
    /// from the corresponding edge of the tile rectangle. A positive percentage
    /// specifies an inset, while a negative percentage specifies an outset.
    /// [Note: For example, a left offset of 25% specifies that the left edge
    /// of the center shade rectangle is located to the right of the tile rectangle's left
    /// edge by an amount equal to 25% of the tile rectangle's width. end note]
    /// </summary>
    DmlPercentageOffsetRectangle getFillToRectangle()
    {
        if (mFillToRectangle == null)
            mFillToRectangle = new DmlPercentageOffsetRectangle();
        return mFillToRectangle;
    }
    void setFillToRectangle(DmlPercentageOffsetRectangle value) { mFillToRectangle = value; }

    /// <summary>
    /// Specifies the shape of the path to follow.
    /// </summary>
    /*DmlPathShadeType*/int getPath() { return mPath; }
    void setPath(/*DmlPathShadeType*/int value) { mPath = value; }

    private DmlPercentageOffsetRectangle mFillToRectangle;
    private /*DmlPathShadeType*/int mPath;
    private /*final*/ IThemeProvider mThemeProvider;
    private static final String VALUE_OUT_OF_RANGE = "The specified value is out of range.";

    /// <summary>
    /// Predefined <see cref="FillToRectangle"/> corresponded to a particular <see cref="GradientStyle"/> and Variant.
    /// </summary>
    private static /*final*/ DmlPercentageOffsetRectangle G_CORNER_RECT_1 = new DmlPercentageOffsetRectangle(0.0, 0.0, 1.0, 1.0);
    private static /*final*/ DmlPercentageOffsetRectangle G_CORNER_RECT_2 = new DmlPercentageOffsetRectangle(1.0, 0.0, 0.0, 1.0);
    private static /*final*/ DmlPercentageOffsetRectangle G_CORNER_RECT_3 = new DmlPercentageOffsetRectangle(0.0, 1.0, 1.0, 0.0);
    private static /*final*/ DmlPercentageOffsetRectangle G_CORNER_RECT_4 = new DmlPercentageOffsetRectangle(1.0, 1.0, 0.0, 0.0);
    private static /*final*/ DmlPercentageOffsetRectangle G_CENTER_RECT = new DmlPercentageOffsetRectangle(0.5, 0.5, 0.5, 0.5);
}

