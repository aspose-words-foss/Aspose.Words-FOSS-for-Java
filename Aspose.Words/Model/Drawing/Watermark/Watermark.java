// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/22/2020 by Artem Ptitsin.

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.image.BufferedImage;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.IO.Stream;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;
import com.aspose.DrFont;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents class to work with document watermark.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-watermark/">Working with Watermark</a> documentation article.</para>
/// </summary>
/// <dev>
/// WORDSNET-4879 This class has been added to working with a watermark.
/// </dev>
public final class Watermark
{
    Watermark(Document doc, IWatermarkProvider watermarkProvider)
    {
        mDoc = doc;
        mWatermarkProvider = watermarkProvider;
    }

    /// <summary>
    /// Adds Text watermark into the document.
    /// </summary>
    /// <param name="text">Text that is displayed as a watermark.</param>
    /// <remarks>
    /// The text length must be in the range from 1 to 200 inclusive.
    /// The text cannot be <c>null</c> or contain only whitespaces.
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">
    /// Throws when the text length is out of range or the text contains only whitespaces.
    /// </exception>
    /// <exception cref="ArgumentNullException">
    /// Throws when the text is <c>null</c>.
    /// </exception>
    public void setText(String text) throws Exception
    {
        setText(text, new TextWatermarkOptions());
    }

    /// <summary>
    /// Adds Text watermark into the document.
    /// </summary>
    /// <param name="text">Text that is displayed as a watermark.</param>
    /// <param name="options">Defines additional options for the text watermark.</param>
    /// <remarks>
    /// The text length must be in the range from 1 to 200 inclusive.
    /// The text cannot be <c>null</c> or contain only whitespaces.
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">
    /// Throws when the text length is out of range or the text contain only whitespaces.
    /// </exception>
    /// <exception cref="ArgumentNullException">
    /// Throws when the text is <c>null</c>.
    /// </exception>
    /// <remarks>If <see cref="TextWatermarkOptions"/> is <c>null</c>, the watermark will be set with default options.</remarks>
    public void setText(String text, TextWatermarkOptions options) throws Exception
    {
        validateText(text);
        TextWatermarkOptions localOptions = options == null ? new TextWatermarkOptions() : options;

        Shape watermark = createWatermark(text, localOptions, mDoc);
        add(watermark);
    }

    /// <summary>
    /// Adds Image watermark into the document.
    /// </summary>
    /// <param name="image">Image that is displayed as a watermark.</param>
    /// <exception cref="ArgumentNullException">
    /// Throws when the image is <c>null</c>.
    /// </exception>
    public void setImage(BufferedImage image) throws Exception
    {
        setImage(image, new ImageWatermarkOptions());
    }

    /// <summary>
    /// Adds Image watermark into the document.
    /// </summary>
    /// <param name="image">Image that is displayed as a watermark.</param>
    /// <param name="options">Defines additional options for the image watermark.</param>
    /// <exception cref="ArgumentNullException">
    /// Throws when the image is <c>null</c>.
    /// </exception>
    /// <remarks>If <see cref="ImageWatermarkOptions"/> is <c>null</c>, the watermark will be set with default options.</remarks>
    public void setImage(BufferedImage image, ImageWatermarkOptions options) throws Exception
    {
        ArgumentUtil.checkNotNull(image, "image");

        Shape watermark = new Shape(mDoc, ShapeType.IMAGE);
        watermark.getImageData().setImage(image);

        addWatermark(watermark, options, mDoc);
    }

    /// <summary>
    /// Adds Image watermark into the document.
    /// </summary>
    /// <param name="imagePath">Path to the image file that is displayed as a watermark.</param>
    /// <param name="options">Defines additional options for the image watermark.</param>
    /// <exception cref="ArgumentNullException">
    /// Throws when the path is <c>null</c>.
    /// </exception>
    /// <remarks>If <see cref="ImageWatermarkOptions"/> is <c>null</c>, the watermark will be set with default options.</remarks>
    public void setImage(String imagePath, ImageWatermarkOptions options) throws Exception
    {
        ArgumentUtil.checkNotNull(imagePath, "imagePath");

        Shape watermark = new Shape(mDoc, ShapeType.IMAGE);
        watermark.getImageData().setImage(imagePath);

        addWatermark(watermark, options, mDoc);
    }

    /// <summary>
    /// Adds Image watermark into the document.
    /// </summary>
    /// <param name="imageStream">The stream containing the image data that is displayed as a watermark.</param>
    /// <param name="options">Defines additional options for the image watermark.</param>
    /// <exception cref="ArgumentNullException">
    /// Throws when the path is <c>null</c>.
    /// </exception>
    /// <remarks>If <see cref="ImageWatermarkOptions"/> is <c>null</c>, the watermark will be set with default options.</remarks>
    //JAVA: method is internalized
	void setImageInternal(Stream imageStream, ImageWatermarkOptions options) throws Exception
    {
        ArgumentUtil.checkNotNull(imageStream, "imageStream");

        Shape watermark = new Shape(mDoc, ShapeType.IMAGE);
        watermark.getImageData().setImage(imageStream);

        addWatermark(watermark, options, mDoc);
    }

	//JAVA-added public wrapper for internalized method
	public void setImage(java.io.InputStream imageStream,ImageWatermarkOptions options) throws Exception
	{
		setImageInternal(Stream.fromJavaInputStream(imageStream), options);
	}

    /// <summary>
    /// Gets the watermark type.
    /// </summary>
    public /*WatermarkType*/int getType()
    {
        Shape watermark = mWatermarkProvider.get();

        if (watermark == null)
            return WatermarkType.NONE;

        // IWatermarkProvider.Get should return the watermark or null.
        Debug.assertTrue(watermark.isWatermark());

        if (watermark.canBeTextWatermark())
            return WatermarkType.TEXT;

        if (watermark.canBeImageWatermark())
            return WatermarkType.IMAGE;

        return WatermarkType.NONE;
    }

    private void add(Shape shape) throws Exception
    {
        remove();
        mWatermarkProvider.add(shape);
    }

    /// <summary>
    /// Removes the watermark.
    /// </summary>
    public void remove()
    {
        mWatermarkProvider.remove();
    }

    private void validateText(String text)
    {
        if (text == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "text");

        if (StringUtil.containsOnlyWhitespaces(text))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "text");

        ArgumentUtil.validateRange(text.length(), 0.0, 0.0, 200.0, 200.0, true, "text");
    }

    private void addWatermark(Shape watermark, ImageWatermarkOptions options, Document doc) throws Exception
    {
        if (options == null)
            options = new ImageWatermarkOptions();

        setImageSize(watermark, options, doc);

        if (options.isWashout())
        {
            watermark.getImageData().setContrast(0.15d);
            watermark.getImageData().setBrightness(0.85d);
        }

        setGeneralAttributes(watermark, IMAGE_NAME_PREFIX);
        add(watermark);
    }

    private void setImageSize(Shape watermark, ImageWatermarkOptions options, Document doc) throws Exception
    {
        double scale = options.getScale();
        double initialWidth = watermark.getImageData().getImageSize().getWidthPoints();
        double initialHeight = watermark.getImageData().getImageSize().getHeightPoints();

        if (options.isScaleAuto())
        {
            PageSetup pageSetup = doc.getFirstSection().getPageSetup();
            float maxWidth = pageSetup.getContentWidth();
            float maxHeight = pageSetup.getContentHeight();

            scale = maxWidth / initialWidth;
            if ((initialHeight * scale) > maxHeight)
                scale = maxHeight / initialHeight;
        }

        watermark.setWidthSafe(msMath.round(initialWidth * scale, 2, MidpointRounding.AWAY_FROM_ZERO));
        watermark.setHeightSafe(msMath.round(initialHeight * scale, 2, MidpointRounding.AWAY_FROM_ZERO));
    }

    private Shape createWatermark(String text, TextWatermarkOptions options, Document doc)
    {
        Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);

        watermark.getTextPath().setText(text);
        watermark.getTextPath().setFontFamily(options.getFontFamily());
        watermark.setRotation((int)options.getLayout());

        if (options.isSemitrasparent())
            watermark.getFill().setOpacity(0.5);

        setTextSize(watermark, options, doc);
        watermark.getFill().setForeColor(options.getColor());
        watermark.setStrokeColor(options.getColor());
        watermark.getFont().setSize(1.0);

        setGeneralAttributes(watermark, TEXT_NAME_PREFIX);

        return watermark;
    }

    private void setTextSize(Shape watermark, TextWatermarkOptions options, Document doc)
    {
        float fontSize = options.isAutoSize() ? 1f : options.getFontSize();
        DrFont drFont = doc.getFontProvider().fetchDrFont(watermark.getTextPath().getFontFamily(), fontSize, FontStyle.REGULAR);
        /*SizeF*/long size = drFont.getTextSizePoints(watermark.getTextPath().getText());

        if (options.isAutoSize())
            size = getTextAutoSize(size, options, doc);

        watermark.setWidthSafe(msMath.round(msSizeF.getWidth(size), 2, MidpointRounding.AWAY_FROM_ZERO));
        watermark.setHeightSafe(msMath.round(msSizeF.getHeight(size), 2, MidpointRounding.AWAY_FROM_ZERO));
    }

    private /*SizeF*/long getTextAutoSize(/*SizeF*/long textSize, TextWatermarkOptions options, Document doc)
    {
        PageSetup pageSetup = doc.getFirstSection().getPageSetup();
        float maxWidth = pageSetup.getContentWidth();
        float maxHeight = pageSetup.getContentHeight();
        float ratio = msSizeF.getHeight(textSize) / msSizeF.getWidth(textSize);

        if (options.getLayout() == WatermarkLayout.DIAGONAL)
        {
            // This calculation will only work for angles of 45, 135, 225, 315 degrees.
            float minSide = Math.min(maxHeight, maxWidth);
            float width = (minSide * (float)Math.sqrt(2.0)) / (1f + ratio);

            return msSizeF.ctor(width, width * ratio);
        }

        return (msSizeF.getHeight(textSize) >= msSizeF.getWidth(textSize))
            ? msSizeF.ctor(maxHeight / ratio, maxHeight)
            : msSizeF.ctor(maxWidth, maxWidth * ratio);
    }

    private void setGeneralAttributes(Shape watermark, String name)
    {
        watermark.setName(msString.format("{0}{1}", name, watermark.getId()));

        // Place the watermark in the page center.
        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
        watermark.setWrapType(WrapType.NONE);
        watermark.setVerticalAlignment(VerticalAlignment.CENTER);
        watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // WORDSNET-24431 MS Word sets this property for a watermark, otherwise the watermark is drawn over
        // header/footer text.
        watermark.setBehindText(true);
    }

    static final String TEXT_NAME_PREFIX = "PowerPlusWaterMarkObject";
    static final String IMAGE_NAME_PREFIX = "WordPictureWatermark";

    private /*final*/ Document mDoc;
    private /*final*/ IWatermarkProvider mWatermarkProvider;
}

