// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;
import com.aspose.ImageSizeCore;
import com.aspose.ms.System.Drawing.RectangleF;


/// <summary>
/// 20.1.8.14 blipFill (Picture Fill)
/// This element specifies the type of picture fill that the picture object has.
/// Because a picture has a picture fill already by default, it is possible to
/// have two fills specified for a picture object. An example of this is shown below.
/// </summary>
class DmlBlipFill extends DmlFill
{
    /*override*/ DmlFill deepClone()
    {
        DmlBlipFill result = new DmlBlipFill();
        result.setDotsPerInch(getDotsPerInch());
        result.setRotateWithShape(getRotateWithShape());
        if (mSourceRectangle != null)
            result.mSourceRectangle = mSourceRectangle.deepClone();
        if (mBlipFillMode != null)
            result.mBlipFillMode = mBlipFillMode.deepClone();
        if (mBlip != null)
            result.mBlip = mBlip.deepClone();
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlBlipFill value = (DmlBlipFill)obj;

        return (value.getDotsPerInch() == getDotsPerInch()) &&
               (value.getRotateWithShape() == getRotateWithShape()) &&
               msObject.equals(value.getSourceRectangle(), getSourceRectangle()) &&
               msObject.equals(value.getBlipFillMode(), getBlipFillMode()) &&
               msObject.equals(value.getBlip(), getBlip());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.longHashCode(getDotsPerInch());
        hash ^= msNumber.booleanHashCode(getRotateWithShape());

        if (getSourceRectangle() != null)
            hash ^= getSourceRectangle().hashCode();
        if (getBlipFillMode() != null)
            hash ^= getBlipFillMode().hashCode();
        if (getBlip() != null)
            hash ^= getBlip().hashCode();

        return hash;
    }

    public /*override*/ double getOpacity()
    {
        DmlAlphaModulateFixedEffect alphaFixedEffect = getAlphaFixedEffect();
        return (alphaFixedEffect == null) ? 1.0 : alphaFixedEffect.getAmount();
    }

    public /*override*/ void setOpacity(double value)
    {
        // WORDSNET-16359 Set opacity for appropriate blip instead of fill color.
        DmlAlphaModulateFixedEffect alphaFixedEffect = getAlphaFixedEffect();

        if (alphaFixedEffect == null)
        {
            alphaFixedEffect = new DmlAlphaModulateFixedEffect();
            getBlip().getEffects().add(alphaFixedEffect);
        }

        alphaFixedEffect.setAmount(value);
    }

    /// <summary>
    /// Specifies that the fill should rotate with the shape. That is, when the shape
    /// that has been filled with a picture and the containing shape (say a rectangle)
    /// is transformed with a rotation then the fill is transformed with the same rotation.
    /// </summary>
    public /*override*/ boolean getRotateWithShape() { return mRotateWithShape; }
    public /*override*/ void setRotateWithShape(boolean value) { mRotateWithShape = value; }

    /// <summary>
    /// Gets the raw bytes of the fill texture or pattern.
    /// </summary>
    /// <remarks>
    /// <p>The default value is null.</p>
    /// </remarks>
    public /*override*/ byte[] getImageBytes() throws Exception { return getBlip().getImageBytes(); }

    ImageSizeCore getImageSize() { return mImageSize; }

    /// <summary>
    /// Gets or sets the type of fill.
    /// </summary>
    public /*override*/ /*FillTypeCore*/int getFillType()
    {
        if ((mBlipFillMode == null) || (mBlipFillMode.getFillType() == FillTypeCore.PICTURE))
            return FillTypeCore.PICTURE;

        return FillTypeCore.TEXTURE;
    }

    /*override*/ /*DmlFillType*/int getDmlFillType() { return DmlFillType.BLIP_FILL; }

    RectangleF calculateImageArea()
    {
        RectangleF originalRect = new RectangleF(0f, 0f, mImageSize.getWidth(), mImageSize.getHeight());
        RectangleF rect = getSourceRectangle().adjust(originalRect);
        return rect;
    }

    /// <summary>
    /// Obtains alpha modulate fixed effect from blip fill.
    /// </summary>
    private DmlAlphaModulateFixedEffect getAlphaFixedEffect()
    {
        DmlAlphaModulateFixedEffect alphaFixed = null;

        // AW removes existing effect to replace with new one to prepare arranged collection of DrawingML effects.
        // So, retrieve last alpha fixed effect, which contains actual value.
        for (int i = getBlip().getEffects().size() - 1; i >= 0; i--)
        {
            if (getBlip().getEffects().get(i).getType() == DmlEffectType.ALPHA_MODULATE_FIXED)
            {
                alphaFixed = (DmlAlphaModulateFixedEffect)getBlip().getEffects().get(i);
                break;
            }
        }

        return alphaFixed;
    }

    DmlBlip getBlip()
    {
        if (mBlip == null)
            mBlip = new DmlBlip();

        return mBlip;
    }
    void setBlip(DmlBlip value) { mBlip = value; }

    /// <summary>
    /// Specifies the DPI (dots per inch) used to calculate the size of the blip.
    /// If not present or zero, the DPI in the blip is used.
    /// </summary>
    /// <remarks>
    /// VML does not contain appropriate properties, so value always is 0 while converting VML to DML.
    /// </remarks>
    /*uint*/int getDotsPerInch() { return mDotsPerInch; }
    void setDotsPerInch(/*uint*/int value) { mDotsPerInch = value; }

    /// <summary>
    /// This element specifies the portion of the blip used for the fill.
    /// Each edge of the source rectangle is defined by a percentage offset from the
    /// corresponding edge of the bounding box. A positive percentage specifies an inset,
    /// while a negative percentage specifies an outset.
    /// For example, a left offset of  25% specifies that the left edge of the source
    /// rectangle is located to the right of the bounding box's left edge by an
    /// amount equal to 25% of the bounding box's width.
    /// </summary>
    DmlPercentageOffsetRectangle getSourceRectangle()
    {
        if (mSourceRectangle == null)
            mSourceRectangle = new DmlPercentageOffsetRectangle();

        return mSourceRectangle;
    }
    void setSourceRectangle(DmlPercentageOffsetRectangle value) { mSourceRectangle = value; }

    /// <summary>
    /// Defines the fill mode (tiling or stretching).
    /// </summary>
    IDmlBlipFillMode getBlipFillMode()
    {
        if (mBlipFillMode == null)
            mBlipFillMode = new DmlBlipFillStretch(); // Use stretch mode by default.

        return mBlipFillMode;
    }
    void setBlipFillMode(IDmlBlipFillMode value) { mBlipFillMode = value; }

    private DmlBlip mBlip;
    private IDmlBlipFillMode mBlipFillMode;
    private /*uint*/int mDotsPerInch;
    private ImageSizeCore mImageSize = null;
    private boolean mRotateWithShape = true;
    private DmlPercentageOffsetRectangle mSourceRectangle = new DmlPercentageOffsetRectangle();
}

