package com.aspose;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.NetOutputStream;
import org.w3c.dom.Element;

import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadataNode;
import java.awt.image.BufferedImage;

public class ImageIoWriter extends ImageWriter
{

    @Override
    protected void doWriteGif(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        // simplified code for the first time: no preferred writer name, no metadata tweaks.
        // WORDSJAVA-1668: specify default plugin
        //      Default (null) is com.sun.media.imageioimpl.plugins.gif.GIFImageWriter
        //      But it's better to change it to com.sun.imageio.plugins.gif.GIFImageWriter. In this case,
        //      it's necessary to regenerate all gif gold files
        ImageWriterHelper helper = new ImageWriterHelper(image, "gif", "com.sun.media.imageioimpl.plugins.gif.GIFImageWriter");
        helper.write(new NetOutputStream(stream));
    }

    @Override
    protected void doWriteBmp(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        //JAVA-changed from:
        // image.Save(stream, ImageCodecInfoRegistry.GetEncoderInfo(ImageFormat.Bmp), null);

        ImageWriterHelper helper = new ImageWriterHelper(image, "bmp", "com.sun.imageio.plugins.bmp.BMPImageWriter");

        // Java's BMP codec is very picky and refuses to save images in many types and color models
        // so we query if it can save this particular image. If it cannot save this color model / type
        // we are converting this image into a format that I found BMP codec can save, it is TYPE_INT_BGR.
        // This still sometimes can result in an ugly image, but better than nothing.
        // WORDSJAVA-1650: added convertion for alpha channel

        if (!helper.getWriter().getOriginatingProvider().canEncodeImage(image)
                || ImageConverter.hasAlphaChannel(image.getType())
                || image.getColorModel().hasAlpha())
        {
            image = ImageConverter.convertTo(image, options.bounds(), BufferedImage.TYPE_INT_BGR);
            helper = new ImageWriterHelper(image, "bmp", "com.sun.imageio.plugins.bmp.BMPImageWriter");
        }

        // Ideally, we need to set the resolution here.
        // SK But we should not use implementation classes like BMPMetadata, since in different
        // platforms there can be different implementations (in different jars and _packages_).
        // Only abstract IIOMetadata should be used here. To get or set non-standard ("native") fields
        // we should use "metadata XML DOM tree". But the code to change metadata using DOM tree is
        // complicated too: first of all, to get a tree we have to enumerate all plugins by name
        // and repeat all tree-code for each plugin tree.
        //
        // RK For BMP I have seen metadata in these two formats. They appear identical schemas at least for
        // the resolution setting purposes. I tried coding this, but Java throws strange "missing attribute sizeOfPallette"
        // when setting tree into the metadata so I just gave up. They users will be okay without the resolution.
        // final String bmpMetadataFormat0 = "com_sun_media_imageio_plugins_bmp_image_1.0";
        // final String bmpMetadataFormat1 = "javax_imageio_bmp_1.0";

        helper.write(new NetOutputStream(stream));
        options.withOutImage(image);
    }

    @Override
    protected void doWriteJpeg(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        // JAVA-added
        // 1. FIX 23947 MS Word seems to convert CMYK bitmaps to RGB when writing to PDF so we do the same.
        // 2. FIX 24426 - TestImageCompression Jpeg compression - some pictures garbled.
        // The JPEG codec seems to be pretty weak at encoding TYPE_CUSTOM images,
        // so we force them to RGB for better results.
        // 3. Any image that is 32bit ARGB seems to appear with pink colours if saved by Java as is.
        // 4. WORDSJAVA-1033. Also we should convert images which have alpha channel and have not been preblended (premultiplied with alpha)
        // Therefore we force it to 24bit RGB before save.
        // 5. WORDSJAVA-1627. Problems with converting ARGB bitmap to JPEG
        // 6. WORDSJAVA-2166. Standard JDK com.sun.imageio.plugins.jpeg.JPEGImageWriter fails to save some indexed images:
        //    - input image is TYPE_BYTE_BINARY or TYPE_BYTE_INDEXED AND has 4 color components
        //    - target JPEG assumes 3 color components specified by metadata
        int imageType = image.getType();
        boolean colorComponentsMismatch =
                imageType == BufferedImage.TYPE_BYTE_BINARY && // no need to check TYPE_BYTE_INDEXED, handled below
                        image.getColorModel().getNumComponents() == 4;
        if (options.isCmyk()
                || imageType == BufferedImage.TYPE_CUSTOM
                || imageType == BufferedImage.TYPE_INT_ARGB
                || imageType == BufferedImage.TYPE_BYTE_GRAY
                || imageType == BufferedImage.TYPE_4BYTE_ABGR
                || imageType == BufferedImage.TYPE_4BYTE_ABGR_PRE
                || imageType == BufferedImage.TYPE_BYTE_INDEXED
                || imageType == BufferedImage.TYPE_INT_ARGB_PRE
                || colorComponentsMismatch)
            image = ImageConverter.convertTo24BppRgb(image, options.bounds());

        // We select this JPEG writer if possible because it seems to support 555 and 565 RGB BMPs.
        ImageWriterHelper helper = new ImageWriterHelper(image, "jpeg", "com.sun.imageio.plugins.jpeg.JPEGImageWriter");

        // Compression
        helper.getParam().setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        helper.getParam().setCompressionQuality(options.jpegQuality() / 100.0f);

        // Resolution
        // http://download.oracle.com/javase/1.4.2/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
        //
        // SK There could be a problem here when we pick up a different codec and it does not match this metadata format.
        // Will address later if we really come across it.
        final String metadataFormat = "javax_imageio_jpeg_image_1.0";
        Element tree = (Element) helper.getMetadata().getAsTree(metadataFormat);

        Element jpegVariety = (Element) tree.getFirstChild();
        Element jfif = (Element) jpegVariety.getFirstChild();   // This is "JPEGvariety/app0JFIF"
        if (jfif == null)
        {
            jfif = new IIOMetadataNode("app0JFIF");
            jfif.setAttribute("majorVersion", "1");
            jfif.setAttribute("minorVersion", "2");
            jfif.setAttribute("thumbWidth", "0");
            jfif.setAttribute("thumbHeight", "0");
            jpegVariety.appendChild(jfif);
        }
        // Use 'Math.max()' to avoid 'Xdensity attribute out of range' exception.
        jfif.setAttribute("Xdensity", FormatterPal.intToStr(Math.max((int) options.horizontalResolution(), 1)));
        jfif.setAttribute("Ydensity", FormatterPal.intToStr(Math.max((int) options.verticalResolution(), 1)));
        jfif.setAttribute("resUnits", "1"); // density is dots per inch

        helper.getMetadata().setFromTree(metadataFormat, tree);

        helper.write(new NetOutputStream(stream));

        options.withOutImage(image);
    }

    @Override
    protected void doWritePng(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        ImageWriterHelper helper = new ImageWriterHelper(image, "png", "com.sun.imageio.plugins.png.PNGImageWriter");

        // Set Resolution
        // http://download.oracle.com/javase/6/docs/api/javax/imageio/metadata/doc-files/png_metadata.html
        // http://jcs.mobile-utopia.com/jcs/84296_PNGMetadata.html
        //
        // SK There could be a problem here when we pick up a different codec and it does not match this metadata format.
        // Will address later if we really come across it.
        final String metadataFormat = "javax_imageio_png_1.0";
        Element tree = (Element) helper.getMetadata().getAsTree(metadataFormat);

        IIOMetadataNode pHYs = new IIOMetadataNode("pHYs");
        pHYs.setAttribute("pixelsPerUnitXAxis", FormatterPal.intToStr(ImageUtil.dpiToPpm(options.horizontalResolution())));
        pHYs.setAttribute("pixelsPerUnitYAxis", FormatterPal.intToStr(ImageUtil.dpiToPpm(options.horizontalResolution())));
        pHYs.setAttribute("unitSpecifier", "meter");
        tree.appendChild(pHYs);

        helper.getMetadata().setFromTree(metadataFormat, tree);

        helper.write(new NetOutputStream(stream));
        options.withOutImage(image);
    }

    @Override
    protected void doWriteTiff(BufferedImage image, Stream stream, ImageWriterBuilder.Options options) throws Exception
    {
        throw new UnsupportedOperationException("ImageIoWriter throws: JAI will be used if available");
    }

}
