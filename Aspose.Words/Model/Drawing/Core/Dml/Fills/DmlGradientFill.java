// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msObject;
import com.aspose.ListUtil;
import com.aspose.ms.System.msNumber;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Collections;
import com.aspose.MathUtil;


/// <summary>
/// 20.1.8.33 gradFill (Gradient Fill)
/// This element defines a gradient fill.
/// A gradient fill is a fill which is characterized by a smooth gradual
/// transition from one color to the next. At its simplest, it is a fill
/// which transitions between two colors; or more generally, it can be a
/// transition of any number of colors.
/// The desired transition colors and locations are defined in the gradient
/// stop list (gsLst) child element.
/// </summary>
class DmlGradientFill extends DmlFill
{
    /// <summary>
    /// Creates instance of a <see cref="DmlGradientFill"/> class by default.
    /// </summary>
    DmlGradientFill()
    {
    }

    /// <summary>
    /// Creates instance of a <see cref="DmlGradientFill"/> class with specified gradient colors, style and variant.
    /// </summary>
    /// <remarks>
    /// Note, there can be 2 or 3 gradient colors depending on gradient style and variant.
    /// If the gradient is 3-colored, then third color is the same as one of the specified color1 or color2.
    /// </remarks>
    DmlGradientFill(DmlColor color1, DmlColor color2, /*GradientStyle*/int style, /*GradientVariant*/int variant,
        IThemeProvider themeProvider)
    {
        switch (style)
        {
            case GradientStyle.HORIZONTAL:
            case GradientStyle.VERTICAL:
            case GradientStyle.DIAGONAL_DOWN:
            case GradientStyle.DIAGONAL_UP:
            {
                setGradient(new DmlLinearGradient(style, variant, themeProvider));
                setGradientStops(DmlLinearGradient.createGradientStops(color1, color2, variant));
                break;
            }
            case GradientStyle.FROM_CORNER:
            {
                setGradient(new DmlPathGradient(style, variant, themeProvider));
                setGradientStops(DmlPathGradient.createFromCornerGradientStops(color1, color2));
                setTileRectangle(DmlPathGradient.createFromCornerTileRect(variant));
                break;
            }
            case GradientStyle.FROM_CENTER:
                setGradient(new DmlPathGradient(style, variant, themeProvider));
                setGradientStops(DmlPathGradient.createFromCenterGradientStops(color1, color2, variant));
                break;
            default:
                throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "style" + "\r\nParameter value: " + style);
        }
    }

    /// <summary>
    /// Set style color to any color placeholders used in the fill.
    /// </summary>
    public /*override*/ void applyStyleColor(DmlColor styleColor)
    {
        for (DmlGradientStop gradientStop : getGradientStops())
        {
            gradientStop.getColor().applyStyleColor(styleColor);
        }
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlGradientFill value = (DmlGradientFill)obj;

        return (value.getRotateWithShape() == getRotateWithShape()) &&
               (value.getTileFlipMode() == getTileFlipMode()) &&
               msObject.equals(value.getTileRectangle(), getTileRectangle()) &&
               msObject.equals(value.getGradient(), getGradient()) &&
               ListUtil.checkAreEquals(value.getGradientStops(), getGradientStops());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getTileRectangle().hashCode();
        hash ^= /*enum.hashCode()=(int)value*/getTileFlipMode();
        hash ^= msNumber.booleanHashCode(getRotateWithShape());
        hash ^= getGradient().hashCode();
        for (DmlGradientStop stop : getGradientStops())
            hash ^= stop.hashCode();
        return hash;
    }

    /*override*/ DmlFill deepClone()
    {
        DmlGradientFill fill = new DmlGradientFill();
        fill.setRotateWithShape(getRotateWithShape());
        fill.setTileFlipMode(getTileFlipMode());
        for (DmlGradientStop gradientStop : getGradientStops())
        {
            fill.getGradientStops().add(gradientStop.deepClone());
        }

        fill.setGradient(getGradient().deepClone());
        fill.setTileRectangle(getTileRectangle().deepClone());
        return fill;
    }

    /// <summary>
    /// Resolves theme colors to a concrete RGB colors using a specified theme provider.
    /// </summary>
    /*override*/ void resolveThemeColors(IThemeProvider themeProvider)
    {
        if (mGradientStops != null)
        {
            for (DmlGradientStop gradientStop : mGradientStops)
            {
                if (gradientStop.getColor().getColorType() == DmlColorType.SCHEME_COLOR)
                    gradientStop.setColor(((DmlSchemeColor)gradientStop.getColor()).resolve(themeProvider));
            }
        }
    }

    /// <summary>Gets the array of custom gradient colors.</summary>
    /// <remarks>
    /// <p>The default value is null.</p>
    /// </remarks>
    public /*override*/ GradientColor[] getGradientColors() { return DmlUtilCore.dmlToGradientStops(getGradientStopsSortedByPosition(), getThemeProvider()); }

    /// <summary>
    /// Gets or sets fill color opacity.
    /// </summary>
    public /*override*/ double getOpacity()
    {
        // IN. When colors in gradient stops have different transparencies, Word VBA returns value 0x8000000
        // scaled to [0, 1] range. Looks like this is denoting that transparency is mixed. Not sure we need this,
        // so lets return transparency of the first gradient stop, for a while.
        return super.getOpacity();
    }
    public /*override*/ void setOpacity(double value)
    {
        // Setting transparency in VBA updates colors in all gradient stops.
        for (DmlGradientStop gradientStop : getGradientStops())
        {
            if (gradientStop.getColor() != null)
                gradientStop.getColor().updateAlpha(value);
        }
    }

    /// <summary>
    /// Gets or sets a boolean value indicating the fill should be rotated with parent object.
    /// </summary>
    public /*override*/ boolean getRotateWithShape() { return mRotateWithShape; }
    public /*override*/ void setRotateWithShape(boolean value) { mRotateWithShape = value; }

    /// <summary>
    /// Gets or sets the type of fill.
    /// </summary>
    public /*override*/ /*FillTypeCore*/int getFillType()
    {
        DmlPathGradient pathGradient = ms.as(getGradient(), DmlPathGradient.class);
        if (pathGradient != null)
            return pathGradient.getPathFillType();

        DmlLinearGradient linearGradient = ms.as(getGradient(), DmlLinearGradient.class);
        if (linearGradient != null)
            return (linearGradient.isScaled()) ? FillTypeCore.SHADE_SCALE : FillTypeCore.SHADE_UNSCALE;

        return FillTypeCore.SHADE;
    }

    public /*override*/ double getAngle()
    {
        DmlLinearGradient linearGradient = ms.as(getGradient(), DmlLinearGradient.class);
        return (linearGradient != null)
            ? linearGradient.getAngle().getValueInDegrees()
            : 0.0;
    }
    public /*override*/ void setAngle(double value)
    {
        DmlLinearGradient linearGradient = ms.as(getGradient(), DmlLinearGradient.class);
        if (linearGradient != null)
            linearGradient.setAngle(DmlAngle.fromDegrees(value));
    }

    /// <summary>
    /// Gets the gradient style for the fill.
    /// https://docs.microsoft.com/en-us/office/vba/api/word.fillformat.gradientstyle
    /// </summary>
    public /*override*/ /*GradientStyle*/int getGradientStyle()
    {
        if (mGradient == null)
            return GradientStyle.NONE;

        return mGradient.getGradientStyle();
    }

    /// <summary>
    /// Gets the gradient variant for the fill as an integer value from 1 to 4 for most gradient fills or 0 if not defined.
    /// https://docs.microsoft.com/en-us/office/vba/api/word.fillformat.gradientvariant
    /// </summary>
    public /*override*/ /*GradientVariant*/int getGradientVariant()
    {
        if ((mGradient == null) || (mGradientStops == null))
            return GradientVariant.NONE;

        return mGradient.getGradientVariant(mGradientStops);
    }

    /// <summary>
    /// Gets or sets foreground color of the fill.
    /// </summary>
    /*override*/ DmlColor getDmlColorInternal()
    {
        return (getFirstGradientStop() == null) ? null : getFirstGradientStop().getColor();
    }
    /*override*/ void setDmlColorInternal(DmlColor value)
    {
        if (getFirstGradientStop() != null)
            getFirstGradientStop().setColor(value);
    }

    /// <summary>
    /// Gets or sets background color of the fill.
    /// </summary>
    /*override*/ DmlColor getDmlColor2Internal()
    {
        return (getLastGradientStop() == null) ? null : getLastGradientStop().getColor();
    }
    /*override*/ void setDmlColor2Internal(DmlColor value)
    {
        if (getLastGradientStop() != null)
            getLastGradientStop().setColor(value);
    }

    /*override*/ /*DmlFillType*/int getDmlFillType() { return DmlFillType.GRADIENT_FILL; }

    /*DmlTileFlipMode*/int getTileFlipMode() { return mTileFlipMode; }
    void setTileFlipMode(/*DmlTileFlipMode*/int value) { mTileFlipMode = value; }

    ArrayList<DmlGradientStop> getGradientStops()
    {
        if (mGradientStops == null)
            mGradientStops = new ArrayList<DmlGradientStop>();
        return mGradientStops;
    }
    void setGradientStops(ArrayList<DmlGradientStop> value) { mGradientStops = value; }

    IDmlGradient getGradient()
    {
        if (mGradient == null)
            mGradient = new DmlLinearGradient();
        return mGradient;
    }
    void setGradient(IDmlGradient value)
    {
        mGradient = value;
    }

    /// <summary>
    /// 20.1.8.59 tileRect (Tile Rectangle)
    /// This element specifies a rectangular region of the shape to which
    /// the gradient is applied. This region is then tiled across the remaining
    /// area of the shape to complete the fill. The tile rectangle is defined by
    /// percentage offsets from the sides of the shape's bounding box.
    /// Each edge of the tile rectangle is defined by a percentage offset from the
    /// corresponding edge of the bounding box. A positive percentage specifies an inset,
    /// while a negative percentage specifies an outset. [Note: For example, a left
    /// offset of 25% specifies that the left edge of the tile rectangle is located
    /// to the right of the bounding box's left edge by an amount equal to 25%
    /// of the bounding box's width.
    /// </summary>
    DmlPercentageOffsetRectangle getTileRectangle()
    {
        if (mTileRectangle == null)
            mTileRectangle = new DmlPercentageOffsetRectangle();
        return mTileRectangle;
    }
    void setTileRectangle(DmlPercentageOffsetRectangle value) { mTileRectangle = value; }

    /// <summary>
    /// Gets gradient stop collection.
    /// </summary>
    /// <remarks>
    /// Used as facade in <see cref="Fill"/> object for internal <see cref="GradientStops"/> collection.
    /// </remarks>
    GradientStopCollection getGradientStopCollection()
    {
        if (mGradientStopCollection == null)
            mGradientStopCollection = new GradientStopCollection(this, getThemeProvider());

        return mGradientStopCollection;
    }

    private DmlGradientStop getFirstGradientStop()
    {
        if (getGradientStops().size() == 0)
            return null;
        return getGradientStops().get(0);
    }

    private DmlGradientStop getLastGradientStop()
    {
        if (getGradientStops().size() == 0)
            return null;
        return getGradientStops().get(getGradientStops().size() - 1);
    }

    /// <summary>
    /// Gets gradient stops sorted by position.
    /// </summary>
    /// <remarks>
    /// The gradient stops sorted by position are needed for Rendering. They were introduced per WORDSNET-9594
    /// Initially, after the fix, all gradient stops were sorted. However, during implementation of WORDSNET-22003
    /// it was discovered that Word VBA uses original order to determine gradient styles and variants and as consequence
    /// the ForeColor and BackColor. In addition, Word seems always inserts new gradient stops at the very start of
    /// gradient stop collection (before any existing gradient stops). And manipulating a sorted collection is tougher
    /// than manipulating an unsorted one. As the result, it is more convenient and natural to keep in model gradient
    /// stops unsorted and use this sorted version only for Rendering.
    /// </remarks>
    private ArrayList<DmlGradientStop> getGradientStopsSortedByPosition()
    {
        if (mGradientStopsSortedByPosition == null)
        {
            mGradientStopsSortedByPosition = msArrayList.ctor(getGradientStops());
            Collections.sort(mGradientStopsSortedByPosition);
        }

        return mGradientStopsSortedByPosition;
    }

    /// <summary>
    /// Gets first gradient stop of gradient fill sorted by position.
    /// </summary>
    private DmlGradientStop getFirstGradientStopSortedByPosition()
    {
        if (getGradientStopsSortedByPosition().size() == 0)
            return null;

        return getGradientStopsSortedByPosition().get(0);
    }

    /// <summary>
    /// Gets last gradient stop of gradient fill sorted by position.
    /// </summary>
    private DmlGradientStop getLastGradientStopSortedByPosition()
    {
        if (getGradientStopsSortedByPosition().size() == 0)
            return null;

        return getGradientStopsSortedByPosition().get(getGradientStopsSortedByPosition().size() - 1);
    }

    /// <summary>
    /// Gets a boolean value indicating either <see cref="FirstGradientStopSortedByPosition"/> is shifted.
    /// </summary>
    private boolean isFirstGradientStopShifted()
    {
        return (getFirstGradientStopSortedByPosition() != null) &&
               !MathUtil.isZero(getFirstGradientStopSortedByPosition().getPosition());
    }

    /// <summary>
    /// Gets a boolean value indicating either <see cref="LastGradientStopSortedByPosition"/> is shifted.
    /// </summary>
    private boolean isLastGradientStopShifted()
    {
        return (getLastGradientStopSortedByPosition() != null) &&
               !MathUtil.areEqual(getLastGradientStopSortedByPosition().getPosition(), 1.0);
    }

    private IDmlGradient mGradient;
    private ArrayList<DmlGradientStop> mGradientStops;
    private boolean mRotateWithShape;
    private /*DmlTileFlipMode*/int mTileFlipMode;
    private DmlPercentageOffsetRectangle mTileRectangle;
    private ArrayList<DmlGradientStop> mGradientStopsSortedByPosition;
    private GradientStopCollection mGradientStopCollection;
    private static final String VALUE_OUT_OF_RANGE = "The specified value is out of range.";
}

