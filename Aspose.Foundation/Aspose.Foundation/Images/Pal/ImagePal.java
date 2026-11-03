// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2015 by Alexander Zhiltsov
package com.aspose;

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;

import java.awt.image.BufferedImage;
import java.io.Closeable;


/// <summary>
/// Base class for wrappers of native image objects.
/// </summary>
public class ImagePal implements Closeable
{
    /// <summary>
    /// It is not possible to create objects of this type.
    /// </summary>
    protected ImagePal()
    {
    }

    /// <summary>
    /// This implementation is a streamlined version of the FxCop rule
    /// http://msdn.microsoft.com/en-us/library/ms244737(VS.80).aspx
    /// </summary>
    public void dispose()
    {
        dispose(true);
        // The method below does nothig for this class because this class doesn't have a destructor, 
        // but a derived class can have this one.
        //GC.SuppressFinalize(this); //JAVA-deleted: not applicable to java.;
    }

    /// <summary>
    /// Sets empty image and releases all resources associated with the object.
    /// </summary>
    public void close()
    {
         dispose(true);
    }

    protected /*virtual*/ void dispose(boolean disposing)
    {
        if (disposing && (mNativeImage != null))
        {
            // Dispose managed resources that implement IDisposable here. 
            mNativeImage.flush();
            mNativeImage = null;
        }

        mImageSize = null;
    }

    // JAVA: not used for bitmap initialization in java.
    // Used only for mask operations like flip(), applyChromaKey(), applyImageAttributes() etc.
    /// <summary>
    /// Sets the native image object that this wrapper encapsulates.
    /// </summary>
    protected void setNativeImage(BufferedImage value)
    {
        mNativeImage = value;
    }

    //JAVA-added. The main setter in java used for image initialization.
    protected void setNativeImage(BufferedImage value, /*FileFormat*/int imageType, ImageSizeCore imageSize)
    {
        mNativeImage = value;
        mImageType = imageType;
        mImageSize = imageSize;
    }

    /// <summary>
    /// Gets the native image object that this wrapper encapsulates.
    /// </summary>
    public BufferedImage getNativeImage() { return mNativeImage; }

    /// <summary>
    /// Returns the original format of the image.
    /// </summary>
    public /*FileFormat*/int getImageType() { return mImageType; }
    protected void setImageType(/*FileFormat*/int value) { mImageType = value; }

    /// <summary>
    /// Returns width of the image.
    /// </summary>
    public int getWidth() { return mNativeImage.getWidth(); }

    /// <summary>
    /// Returns height of the image.
    /// </summary>
    public int getHeight() { return mNativeImage.getHeight(); }

    /// <summary>
    /// Returns the resolution that is resilient to Windows desktop resolution.
    /// </summary>
    public float getHorizontalResolution()
    {
        return mIsOriginalResolutionZero ? ImageConstants.STANDARD_RESOLUTION : (float) getImageSize().getHorizontalResolution();
    }

    /// <summary>
    /// Returns the resolution that is resilient to Windows desktop resolution.
    /// </summary>
    public float getVerticalResolution()
    {
        return mIsOriginalResolutionZero ? ImageConstants.STANDARD_RESOLUTION : (float) getImageSize().getVerticalResolution();
    }

    /// <summary>
    /// Used during initialization.
    /// </summary>
    protected void setIsOriginalResolutionZero(byte[] imageBytes) throws Exception
    {
        // Normally, this class handles bitmap images only, but a textured brush can have a metafile
        // as its texture and when converting such brush to a native image, .NET plays its dirty trick again and
        // sets the bitmap resolution to the current desktop resolution. So we need to treat metafiles as having
        // zero resolution.
        MemoryStream stream = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
        {
            setIsOriginalResolutionZero(stream);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Used during initialization.
    /// </summary>
    protected void setIsOriginalResolutionZero(Stream imageStream) throws Exception
    {
        /*FileFormat*/int imageType = ImageUtil.getImageType(imageStream);
        ImageSizeCore imageSize = ImageUtil.getImageSize(imageStream, imageType);

        setIsOriginalResolutionZero(imageSize, imageType);
    }

    /// <summary>
    /// Used during initialization.
    /// </summary>
    //JAVA-changed: we already have imageSize and imageType - no need to calculate them from imageBytes.
    protected void setIsOriginalResolutionZero(ImageSizeCore imageSize, /*FileFormat*/int imageType) throws Exception
    {
        // Normally, this class handles bitmap images only, but a textured brush can have a metafile
        // as its texture and when converting such brush to a native image, .NET plays its dirty trick again and
        // sets the bitmap resolution to the current desktop resolution. So we need to treat metafiles as having
        // zero resolution.
        mIsOriginalResolutionZero =
                ImageUtil.isMetafile(imageType) ||
                imageSize.isOriginalResolutionZero();
    }

    /// <summary>
    /// RK It can happen when the resolution returned from the original image bytes is different
    /// from the resolution returned by the GDI+ for an image that was loaded from those bytes.
    /// 
    /// This could happen for example, when the resolution was not specified in the original image bytes 
    /// and we default to 96dpi. But creating a GDI+ image from such bytes will set image resolution to 
    /// the current desktop resolution and it could be 120dpi for example. This will cause XPS and possibly
    /// other tests to fail because the output will be dependent on the desktop resolution.
    /// 
    /// So here we detect that situation and change the bitmap resolution to what we expect it to be,
    /// but to properly set the bitmap resolution we actually have to create a new bitmap.
    /// This method disposes the original bitmap.
    /// </summary>
    protected void setStandardResolutionIfOriginalResolutionWasZero()
    {
        if (mIsOriginalResolutionZero)
        {
            //JAVA-changed: specific logic to save resolution
            // BufferedImage newBitmap = new BufferedImage(mNativeImage);
            // newBitmap.SetResolution(ImageConstants.STANDARD_RESOLUTION, ImageConstants.STANDARD_RESOLUTION);
            // mNativeImage.flush();
            // mNativeImage = newBitmap;
            ImageSizeCore imageSize = ImageSizeCore.createWithResolution(
                    getWidth(), getHeight(), ImageConstants.STANDARD_RESOLUTION, ImageConstants.STANDARD_RESOLUTION);
            setImageSize(imageSize);
            mIsOriginalResolutionZero = false;
        }
    }

    //JAVA-added:
    public ImageSizeCore getImageSize() { return mImageSize; }

    //JAVA-added:
    public void setImageSize(ImageSizeCore value) { mImageSize = value; }

    private BufferedImage mNativeImage;
    private /*FileFormat*/ int mImageType;
    private boolean mIsOriginalResolutionZero;
    //JAVA-added:
    private ImageSizeCore mImageSize;
}

