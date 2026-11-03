// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2009 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.System.msObject;


/// <summary>
/// Provides methods to deal with MS Word-like image cropping (when crop is specified in fractions of image width).
/// </summary>
public class ImageCrop
{
    public ImageCrop(double cropLeft, double cropRight, double cropTop, double cropBottom)
    {
        mCropLeft = cropLeft;
        mCropRight = cropRight;
        mCropTop = cropTop;
        mCropBottom = cropBottom;
    }

    /// <summary>
    /// Calculates the full "source" crop rectangle.
    /// It is a rectangle that is supposed to be in pixels and includes both positive and negative cropping.
    /// </summary>
    public RectangleF getSrcFullCropRect(RectangleF srcRect)
    {
        double srcCroppedLeft = srcRect.getLeft() + srcRect.getWidth() * mCropLeft;
        double srcCroppedRight = srcRect.getLeft() + srcRect.getWidth() * (1.0 - mCropRight);
        double srcCroppedTop = srcRect.getTop() + srcRect.getHeight() * mCropTop;
        double srcCroppedBottom = srcRect.getTop() + srcRect.getHeight() * (1.0 - mCropBottom);

        return RectangleF.fromLTRB((float)srcCroppedLeft, (float)srcCroppedTop, (float)srcCroppedRight, (float)srcCroppedBottom);
    }

    /// <summary>
    /// Calculates the rectangle in pixels that you must take from the source image.
    /// The maximum is the whole source rectangle (image).
    /// </summary>
    public Rectangle getSrcPositiveCropRect(Rectangle srcRect)
    {
        double srcCroppedLeft = srcRect.getLeft() + srcRect.getWidth() * Math.max(0.0, mCropLeft);
        double srcCroppedRight = srcRect.getLeft() + srcRect.getWidth() * (1.0 - Math.max(0.0, mCropRight));
        double srcCroppedTop = srcRect.getTop() + srcRect.getHeight() * Math.max(0.0, mCropTop);
        double srcCroppedBottom = srcRect.getTop() + srcRect.getHeight() * (1.0 - Math.max(0.0, mCropBottom));

        return Rectangle.fromLTRB(
            MathUtil.doubleToInt(srcCroppedLeft),
            MathUtil.doubleToInt(srcCroppedTop),
            MathUtil.doubleToInt(srcCroppedRight),
            MathUtil.doubleToInt(srcCroppedBottom));
    }

    /// <summary>
    /// Calculates the rectangle in world units that you must output the cropped image to.
    /// The maximum is the whole destination rectangle (shape).
    /// </summary>
    public RectangleF getDstNegativeCropRect(RectangleF dstRect)
    {
        // Original code by VS.
        double basePictureCoeffX = 1.0 / (1.0 - Math.min(0.0, mCropLeft + mCropRight));
        double basePictureCoeffY = 1.0 / (1.0 - Math.min(0.0, mCropTop + mCropBottom));

        double absNegCropLeft = -Math.min(0.0, mCropLeft);
        double absNegCropRight = -Math.min(0.0, mCropRight);
        double absNegCropTop = -Math.min(0.0, mCropTop);
        double absNegCropBottom = -Math.min(0.0, mCropBottom);

        double destLeft = dstRect.getLeft() + dstRect.getWidth() * absNegCropLeft * basePictureCoeffX;
        double destRight = dstRect.getLeft() + dstRect.getWidth() * (1.0 - absNegCropRight * basePictureCoeffX);
        double destTop = dstRect.getTop() + dstRect.getHeight() * absNegCropTop * basePictureCoeffY;
        double destBottom = dstRect.getTop() + dstRect.getHeight() * (1.0 - absNegCropBottom * basePictureCoeffY);

        return RectangleF.fromLTRB((float)destLeft, (float)destTop, (float)destRight, (float)destBottom);
    }

    /// <summary>
    /// Chops the image inside according to the crop.
    /// Do not call this if the crop has no positive cropping as it might be a waste loading the image into memory.
    /// Do not call this for metafiles.
    /// The caller is responsible for destroying the returned image.
    /// </summary>
    public BitmapPal positivelyCrop(byte[] imageBytes) throws Exception
    {
        Debug.assertTrue(hasPositiveCrop());

        BitmapPal srcImage = new BitmapPal(imageBytes);
        try /*JAVA: was using*/
        {
            Rectangle srcRect = getSrcPositiveCropRect(new Rectangle(0, 0, srcImage.getWidth(), srcImage.getHeight()));
            return srcImage.positivelyCrop(srcRect);
        }
        finally { if (srcImage != null) srcImage.dispose(); }
    }

    public /*override*/ int hashCode()
    {
        // RK This seems to work well, but it was tricky to come up with. I tried some published algorithms and they failed,
        // resulted in duplicate values when left/right or top/bottom had opposite (positive and negative) values.
        return
            (DoublePal.hashCode(mCropLeft) >> 1) ^
            (DoublePal.hashCode(mCropRight) << 3) ^
            (DoublePal.hashCode(mCropTop) << 1) ^
            (DoublePal.hashCode(mCropBottom) >> 3);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != getClass())
            return false;

        ImageCrop other = (ImageCrop)obj;
        return MathUtil.areEqual(mCropLeft, other.mCropLeft) &&
               MathUtil.areEqual(mCropRight, other.mCropRight) &&
               MathUtil.areEqual(mCropTop, other.mCropTop) &&
               MathUtil.areEqual(mCropBottom, other.mCropBottom);
    }

    public boolean isEmpty() { return (mCropLeft == 0) && (mCropRight == 0) && (mCropTop == 0) && (mCropBottom == 0); }

    /// <summary>
    /// Positive cropping is when the crop boundary is moved inside of the image.
    /// </summary>
    public boolean hasPositiveCrop() { return ((mCropLeft > 0) || (mCropRight > 0) || (mCropTop > 0) || (mCropBottom > 0)); }

    /// <summary>
    /// Negative cropping is when the crop boundary is moved outside of the image.
    /// </summary>
    public boolean hasNegativeCrop() { return ((mCropLeft < 0) || (mCropRight < 0) || (mCropTop < 0) || (mCropBottom < 0)); }

    public double getCropLeft() { return mCropLeft; }

    public double getCropRight() { return mCropRight; }

    public double getCropTop() { return mCropTop; }

    public double getCropBottom() { return mCropBottom; }

    public static boolean isNullOrEmpty(ImageCrop crop)
    {
        return (crop == null) || crop.isEmpty();
    }

    private /*final*/ double mCropLeft;
    private /*final*/ double mCropRight;
    private /*final*/ double mCropTop;
    private /*final*/ double mCropBottom;
}

