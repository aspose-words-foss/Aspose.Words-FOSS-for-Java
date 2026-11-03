// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import java.awt.image.BufferedImage;
import com.aspose.ms.System.IO.Stream;
import com.aspose.MathUtil;
import com.aspose.ArgumentUtil;
import com.aspose.ImageCrop;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.FileFormat;
import com.aspose.ms.System.Collections.SortedList;



/// <summary>
/// Defines an image for a shape.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-images/">Working with Images</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Use the <see cref="Shape.ImageData"/> property to access and modify the image inside a shape.
/// You do not create instances of the <see cref="ImageData"/> class directly.</p>
///
/// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="IImageData.Ctor"]/*'/>
/// </remarks>
public class ImageData implements IBorderAttrSource
{
    /// <summary>
    /// Ctor.
    /// Document is needed to resolve relative image URIs by some of the methods.
    /// Document might be null if in glossary, let's think what to do with them.
    /// </summary>
    ImageData(ShapeBase parent, Document doc)
    {
        Debug.assertTrue(parent != null);
        mParent = parent;

        mImageDataCore = (parent.getMarkupLanguage() == ShapeMarkupLanguage.VML)
            ? new ImageDataCore(doc, new ImageDataSource(parent))
            : new ImageDataCore(doc, new DrawingMLImageDataSource(parent));
    }

    /// <summary>
    /// Sets the image that the shape displays.
    /// </summary>
    /// <param name="image">The image object.</param>
    public void setImage(BufferedImage image) throws Exception
    {
        mImageDataCore.setImage(image);
    }

    /// <summary>
    /// Sets the image that the shape displays.
    /// </summary>
    /// <param name="stream">The stream that contains the image.
    /// <java> The stream will be read from the current position, so one should be careful about stream position.</java></param>
    /// <javaName>void setImage(java.io.InputStream stream)</javaName>
    //JAVA-added public wrapper for internalized member
    public void setImage(java.io.InputStream stream) throws Exception
    {
        setImage(com.aspose.ms.java.IO.JavaOnlyStreamUtil.copyToMemoryStream(stream));
    }
    void setImage( Stream stream) throws Exception
    {
        mImageDataCore.setImage(stream);
    }

    /// <summary>
    /// Sets the image that the shape displays.
    /// </summary>
    /// <param name="fileName">The image file. Can be a file name or a URL.</param>
    public void setImage(String fileName) throws Exception
    {
        mImageDataCore.setImage(fileName);
    }

    /// <summary>
    /// Gets the image stored in the shape as a <ms><see cref="Image"/></ms><java>java <tt>BufferedImage</tt></java><cpp><see cref="Image"/></cpp> object.
    /// </summary>
    /// <returns></returns>
    /// <remarks>
    ///     <ms><p>A new <see cref="Image"/> object is created every time this method is called.</p></ms>
    ///     <cpp><p>A new <see cref="Image"/> object is created every time this method is called.</p></cpp>
    ///     <java>
    ///         <p>Tries to create a new <tt>java.awt.image.BufferedImage</tt> object from image bytes every time this method is called.
    /// If <tt>javax.imageio.ImageReader</tt> can't read image bytes (emf, wmf, tiff, etc.) the method returns <c>null</c>.</p>
    ///     </java>
    ///     <p>It is the responsibility of the caller to dispose the image object.</p>
    /// </remarks>
    public BufferedImage toImage() throws Exception
    {
        return mImageDataCore.toImage();
    }

    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="IImageData.ToStream"]/*'/>
    //JAVA: method is internalized
	Stream toStreamInternal() throws Exception
    {
        return mImageDataCore.toStream();
    }

	//JAVA-added public wrapper for internalized method
	public java.io.InputStream toStream() throws Exception
	{
		return Stream.toJavaInputStream(toStreamInternal());
	}

    /// <summary>
    /// Returns image bytes for any image regardless whether the image is stored or linked.
    /// </summary>
    /// <returns></returns>
    /// <remarks>
    ///     <para>If the image is linked, downloads the image every time it is called.</para>
    ///     <seealso cref="ImageBytes"/>
    /// </remarks>
    public byte[] toByteArray() throws Exception
    {
        return mImageDataCore.toByteArray();
    }

    /// <summary>
    /// Saves the image into the specified stream.
    /// </summary>
    /// <param name="stream">The stream where to save the image to.</param>
    /// <overloads>Saves the image of the shape.</overloads>
    /// <remarks>
    ///     <p>Is it the responsibility of the caller to dispose the stream object.</p>
    /// </remarks>
    /// <javaName>void save(java.io.OutputStream stream)</javaName>
    //JAVA-added public wrapper for internalized member
    public void save(java.io.OutputStream stream) throws Exception
    {
        com.aspose.ms.System.IO.MemoryStream tempStream = new com.aspose.ms.System.IO.MemoryStream();
        save(tempStream);
        tempStream.setPosition(0);
        com.aspose.ms.java.IO.JavaOnlyStreamUtil.copyStream(tempStream, stream);
    }
    void save( Stream stream) throws Exception
    {
        mImageDataCore.save(stream);
    }

    /// <summary>
    /// Saves the image into a file.
    /// </summary>
    /// <param name="fileName">The file name where to save the image.</param>
    public void save(String fileName) throws Exception
    {
        mImageDataCore.save(fileName);
    }

    /// <summary>
    /// Fits the image data to Shape frame so that the aspect ratio of the image data matches the aspect ratio of Shape frame.
    /// </summary>
    public void fitImageToShape() throws Exception
    {
        if (mParent.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            DmlBlipFill blipFill;
            DmlNode dmlNode = mParent.getDmlNode();
            if (dmlNode == null)
                return;

            switch (dmlNode.getDmlNodeType())
            {
                case DmlNodeType.PICTURE:
                    DmlPicture dmlPicture = (DmlPicture)dmlNode;
                    blipFill = dmlPicture.getBlipFill();
                    break;
                case DmlNodeType.WORDPROCESSING_SHAPE:
                    DmlShape dmlShape = (DmlShape)dmlNode;
                    blipFill = ms.as(dmlShape.getFill(), DmlBlipFill.class);
                    break;
                default:
                    return;
            }

            if (blipFill == null)
                return;

            ImageSize imageSize = getImageSize();
            if (MathUtil.areEqual(mParent.getWidth(), 0.0) || MathUtil.areEqual(mParent.getHeight(), 0.0) || !imageSize.isValid())
                return;

            double shapeRatio = mParent.getWidth() / mParent.getHeight();
            double imageRatio = (double)imageSize.getWidthPixels() / imageSize.getHeightPixels();
            if (MathUtil.areEqual(shapeRatio, imageRatio))
                return;

            DmlBlipFillStretch stretch = new DmlBlipFillStretch();
            if (shapeRatio > imageRatio)
            {
                // Offsets are at left and right.
                double imageRelativeWidth = imageRatio / shapeRatio;
                double offset = (1.0 - imageRelativeWidth) / 2.0;
                stretch.setFillRectangle(new DmlPercentageOffsetRectangle(offset, 0.0, offset, 0.0));
            }
            else
            {
                // Offsets are at top and bottom.
                double imageRelativeHeight = shapeRatio / imageRatio;
                double offset = (1.0 - imageRelativeHeight) / 2.0;
                stretch.setFillRectangle(new DmlPercentageOffsetRectangle(0.0, offset, 0.0, offset));
            }

            blipFill.setBlipFillMode(stretch);
        }
        else
        {
            // A VML shape can have two defined images: a shape main image and an image specified as Fill. A fill
            // image can be displayed with keeping aspect ratio; it is drawn behind a shape image.
            // So, let's convert the shape main image into a fill image.
            // (Although we can simply remove the fill image, i.e. remove value of the ShapeAttr.FillImageBytes
            // attribute here, and Shape.ReplaceVmlImageDataWithFill will be automatically run on validation,
            // explicit calling the method looks more descriptive.)

            Shape shape = (Shape)mParent;
            if ((getImageBytes() != null) && (getImageBytes().length > 0))
                shape.replaceVmlImageDataWithFill();
            shape.getFillCore().setLockAspectRatio(true);
        }
    }

    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="IImageData.ImageBytes"]/*'/>
    
    public byte[] getImageBytes() throws Exception { return mImageDataCore.getImageBytes(); }
    
    public void setImageBytes(byte[] value) throws Exception { mImageDataCore.setImageBytes(value); }

    /// <summary>
    /// Returns <c>true</c> if the shape has image bytes or links an image.
    /// </summary>
    /// <value></value>
    public boolean hasImage() throws Exception { return mImageDataCore.hasImage(); }

    /// <summary>
    /// Gets the information about image size and resolution.
    /// </summary>
    /// <value></value>
    /// <remarks>
    ///     <p>If the image is linked only and not stored in the document, returns zero size.</p>
    /// </remarks>
    public ImageSize getImageSize() throws Exception { return mImageDataCore.getImageSize(); }

    /// <summary>
    /// Gets the type of the image.
    /// </summary>
    /// <value></value>
    public /*ImageType*/int getImageType() throws Exception { return mImageDataCore.getImageType(); }

    /// <summary>
    /// Sets the image data and silences exceptions if any.
    /// </summary>
    /// <param name="bytes">Bytes of the image.</param>
    /// <returns>True, if setting was successful, otherwise - False.</returns>
    boolean setImageSafe(byte[] bytes) throws Exception
    {
        return mImageDataCore.setImageSafe(bytes);
    }

    /// <summary>
    /// Returns <c>true</c> if the image is linked to the shape (when <see cref="SourceFullName"/> is specified).
    /// </summary>
    /// <value></value>
    public boolean isLink() throws Exception { return mImageDataCore.isLink(); }

    /// <summary>
    /// Returns <c>true</c> if the image is linked and not stored in the document.
    /// </summary>
    /// <value></value>
    public boolean isLinkOnly() throws Exception { return mImageDataCore.isLinkOnly(); }

    /// <summary>
    /// Gets or sets the path and name of the source file for the linked image.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    /// <p>If <see cref="SourceFullName"/> is not an empty string, the image is linked.</p>
    /// </remarks>
    public String getSourceFullName() throws Exception { return mImageDataCore.getSourceFullName(); }
    public void setSourceFullName(String value) throws Exception { mImageDataCore.setSourceFullName(value); }

    /// <summary>
    /// Defines the title of an image.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    /// </remarks>
    public String getTitle()
    {
        return (String)fetchAttr(ShapeAttr.IMAGE_TITLE);
    }
    public void setTitle(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(ShapeAttr.IMAGE_TITLE, value);
    }

    /// <summary>
    /// Defines the fraction of picture removal from the top side.
    /// </summary>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="Cropping"]/*'/>
    public double getCropTop() { return mParent.getGraphicData().getCropTop(); }
    public void setCropTop(double value) { mParent.getGraphicData().setCropTop(value); }

    /// <summary>
    /// Defines the fraction of picture removal from the bottom side.
    /// </summary>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="Cropping"]/*'/>
    public double getCropBottom() { return mParent.getGraphicData().getCropBottom(); }
    public void setCropBottom(double value) { mParent.getGraphicData().setCropBottom(value); }

    /// <summary>
    /// Defines the fraction of picture removal from the left side.
    /// </summary>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="Cropping"]/*'/>
    public double getCropLeft() { return mParent.getGraphicData().getCropLeft(); }
    public void setCropLeft(double value) { mParent.getGraphicData().setCropLeft(value); }

    /// <summary>
    /// Defines the fraction of picture removal from the right side.
    /// </summary>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="Cropping"]/*'/>
    public double getCropRight() { return mParent.getGraphicData().getCropRight(); }
    public void setCropRight(double value) { mParent.getGraphicData().setCropRight(value); }

    /// <summary>
    /// Gets all crop info in one call.
    /// </summary>
    ImageCrop getCrop()
    {
        return new ImageCrop(getCropLeft(), getCropRight(), getCropTop(), getCropBottom());
    }

    /// <summary>
    /// Gets the collection of borders of the image. Borders only have effect for inline images.
    /// </summary>
    public BorderCollection getBorders()
    {
        if (mBordersCache == null)
            mBordersCache = new BorderCollection(this);
        return mBordersCache;
    }

    /// <summary>
    /// Defines the color value of the image that will be treated as transparent.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public Color getChromaKey() { return getChromaKeyInternal().toNativeColor(); }
    public void setChromaKey(Color value) { setChromaKeyInternal(DrColor.fromNativeColor(value)); }

    DrColor getChromaKeyInternal() { return (DrColor)fetchAttr(ShapeAttr.IMAGE_TRANSPARENT); }
    void setChromaKeyInternal(DrColor value) { setAttr(ShapeAttr.IMAGE_TRANSPARENT, value); }

    /// <summary>
    /// Gets or sets the brightness of the picture.
    /// The value for this property must be a number from 0.0 (dimmest) to 1.0 (brightest).
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.5.</p>
    /// </remarks>
    public double getBrightness() { return mParent.getGraphicData().getBrightness(); }
    public void setBrightness(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mParent.getGraphicData().setBrightness(value);
    }

    /// <summary>
    /// Gets or sets the contrast for the specified picture. The value
    /// for this property must be a number from 0.0 (the least contrast) to 1.0 (the greatest contrast).
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.5.</p>
    /// </remarks>
    public double getContrast() { return mParent.getGraphicData().getContrast(); }
    public void setContrast(double value)
    {
        if ((value < 0.0) || (value > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mParent.getGraphicData().setContrast(value);
    }

    /// <summary>
    /// Determines whether an image will be displayed in black and white.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getBiLevel() { return mParent.getGraphicData().getBiLevel(); }
    public void setBiLevel(boolean value) { mParent.getGraphicData().setBiLevel(value); }

    /// <summary>
    /// Determines whether a picture will display in grayscale mode.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getGrayScale() { return mParent.getGraphicData().getGrayScale(); }
    public void setGrayScale(boolean value) { mParent.getGraphicData().setGrayScale(value); }

    /// <summary>
    /// Converts escher brightness to percent brightness in -0.5 to 0.5 range to 0.0 to 1.0 range.
    /// </summary>
    static double brightnessToPercent(double escherBrightness)
    {
        if ((escherBrightness < BRIGHTNESS_MIN) || (escherBrightness > BRIGHTNESS_MAX))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "escherBrightness");

        return escherBrightness + 0.5;
    }

    /// <summary>
    /// Converts percent brightness to escher brightness from 0.0 to 1.0 range to -0.5 to 0.5 range.
    /// </summary>
    static double percentToBrightness(double percent)
    {
        if ((percent < 0.0) || (percent > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "percent");

        return percent - 0.5;
    }

    /// <summary>
    /// Uses somewhat complicated formula that I came up myself while observing values stored by escher.
    /// </summary>
    static double contrastToPercent(double escherContrast)
    {
        // WORDSNET-18766 Although MS Word doesn't allow to enter contrast values less then 0% and greater then 100% via GUI,
        // it still processes them normally if they're already specified in the document, and it doesn't skip such
        // out-of-range contrast values while rendering, so we shouldn't check for ContrastMin value here either.

        if (escherContrast <= 1.0)
            return escherContrast / 2.0;
        else
            return (escherContrast - 0.5) / escherContrast;
    }

    /// <summary>
    /// Converts percent contrast to escher contrast.
    /// Uses somewhat complicated formula that I came up myself while observing values stored by escher.
    /// </summary>
    static double percentToContrast(double percent)
    {
        if ((percent < 0.0) || (percent > 1.0))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "percent");

        if (percent <= 0.5)
        {
            return percent * 2.0;
        }
        else
        {
            if (percent < 1.0)
                return 0.5 / (1.0 - percent);
            else
                return Double.MAX_VALUE;
        }
    }

    byte[] loadImageBytes() throws Exception
    {
        return mImageDataCore.loadImageBytes();
    }

    /// <summary>
    /// True if <see cref="ChromaKey"/> is specified.
    /// </summary>
    boolean isChromaKeySpecified() { return mParent.getDirectShapeAttrInternal(ShapeAttr.IMAGE_TRANSPARENT) != null; }

    boolean hasImageBytes() throws Exception { return mImageDataCore.hasImageBytes(); }

    /// <summary>
    /// Gets the type of the image.
    /// </summary>
    /*FileFormat*/int getFileFormat() throws Exception { return mImageDataCore.getFileFormat(); }

    private Object fetchAttr(int key)
    {
        return mParent.fetchShapeAttrInternal(key);
    }

    private void setAttr(int key, Object value)
    {
        mParent.setShapeAttrInternal(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/getDirectBorderAttr(int key)
    {
        return mParent.getDirectShapeAttrInternal(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/fetchInheritedBorderAttr(int key)
    {
        return mParent.fetchInheritedShapeAttrInternal(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IBorderAttrSource.*/setBorderAttr(int key, Object value)
    {
        setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public SortedList<Integer, Integer> /*IBorderAttrSource.*/getPossibleBorderKeys() { return G_IMAGE_BORDERS; }

    /// <summary>
    /// Parent attribute source.
    /// Typical implementer of <see cref="IShapeAttrSource" /> is <see cref="Shape" />.
    /// </summary>
    private /*final*/ ShapeBase mParent;

    /// <summary>
    /// Associated border collection.
    /// </summary>
    private BorderCollection mBordersCache;

    private static /*final*/ SortedList<Integer, Integer> G_IMAGE_BORDERS = new SortedList<Integer, Integer>();
    private /*final*/ ImageDataCore mImageDataCore;

    static final double CONTRAST_MIN = 0.0;
    static final double CONTRAST_MAX = 1.0;
    static final double BRIGHTNESS_MIN = -0.5;
    static final double BRIGHTNESS_MAX = 0.5;

    /// <summary>
    /// Static ctor.
    /// </summary>
    static/* ImageData()*/
    {
        G_IMAGE_BORDERS.add(BorderType.TOP, ShapeAttr.BORDER_TOP);
        G_IMAGE_BORDERS.add(BorderType.LEFT, ShapeAttr.BORDER_LEFT);
        G_IMAGE_BORDERS.add(BorderType.BOTTOM, ShapeAttr.BORDER_BOTTOM);
        G_IMAGE_BORDERS.add(BorderType.RIGHT, ShapeAttr.BORDER_RIGHT);
    }
}

