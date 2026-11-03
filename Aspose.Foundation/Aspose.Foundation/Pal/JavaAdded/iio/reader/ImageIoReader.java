package com.aspose;

import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.NetInputStream;
import com.aspose.ms.java.ImageIO.gif.GifDecoder;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageIoReader extends ImageReader
{
    @Override
    protected BufferedImage doRead(Stream stream, ImageReaderBuilder.Options options) throws Exception
    {
        long startInputStreamPos = stream.getPosition();

        ImageInputStream ios = ImageIO.createImageInputStream(new NetInputStream(stream));
        javax.imageio.ImageReader reader = options.inFormat() != null
                ? getSuitableImageReaderByFileExt(FileFormatCore.toExt(options.inFormat()))
                : getSuitableImageReader(ios);

        if (reader == null)
            throw new IllegalArgumentException("Couldn't find appropriate ImageIO reader for " + FileFormat.getName(options.inFormat()));

        reader.setInput(ios);
        int imageFormat = FileFormatCore.fromExt(reader.getFormatName());

        BufferedImage result = null;
        IIOMetadata metadata = null;

        List<String> errors = new ArrayList<String>();

        if (imageFormat == FileFormat.GIF)
        {
            try
            {
                metadata = reader.getImageMetadata(0);
            }
            catch (Throwable t)
            {
                // it is ok, metadata could be corrupted sometimes.
                errors.add(t.getMessage());
            }

            // WORDSJAVA-1847. ImageIO and JAI returns wrong image for animated GIF.
            // Try to detect animated gif and use integrated open source code for it.
            // WORDSJAVA-1907. Similar issue occurs in non-animated images, but
            // for performance and back compatibility we'll solve it separately. Issues with
            // compatibility are caused by the internal Gif engine which always returns ARGB image,
            // but our code workd differently with INDEXED and ARBG images, see AdobeImage.java (preblendRgb).
            // However, in the future this Gif engine can be used by default
            if (isGifAnimated(metadata))
            {
                stream.setPosition(startInputStreamPos);
                result = loadUsingInternalGifEngine(stream);
            }
        }
        else if (imageFormat == FileFormat.JPEG && reader.canReadRaster())
        {
            try
            {
                boolean[] wasConvertedFromCMYK = new boolean[]{options.isCmyk()};
                result = new JpegImageReader(ios).read(wasConvertedFromCMYK);
                options.withIsCmyk(wasConvertedFromCMYK[0]);
            }
            catch (Throwable any)
            {
                // fallback to default with/without metadata read
                errors.add(any.getMessage());
            }
        }
        else if (imageFormat == FileFormat.BMP)
        {
            try
            {
                // BMPs positioned incorrectly when reader reads with metadata
                reader.setInput(ios, true, true);
                result = reader.read(0);
            }
            catch (Throwable any)
            {
                // fallback to default with/without metadata read
                errors.add(any.getMessage());
            }
        }

        if (result == null)
        {
            // FIX WORDSJAVA-2458. Read the stream from the beginning regardless of the presence of metadata
            try
            {
                // Try to read from the beginning without metadata.
                stream.setPosition(startInputStreamPos);
                ios = ImageIO.createImageInputStream(new NetInputStream(stream));
                reader.setInput(ios, true, true);
                result = reader.read(0);
            }
            catch (Throwable t)
            {
                errors.add(t.getMessage());
                // WORDSJAVA-1907. It's a known ImageIO issue (https://bugs.openjdk.java.net/browse/JDK-7132728),
                // which still hasn't fixed. It's caused by "too strong LZW compression" and can
                // be occurred in any GIF image, that's why we use external GIF library instead
                if (imageFormat == FileFormat.GIF)
                {
                    stream.setPosition(startInputStreamPos);
                    result = loadUsingInternalGifEngine(stream);
                }
            }
        }

        if (result == null)
        {
            StringBuilder errorStr = new StringBuilder();
            for (String error : errors)
                errorStr.append(error).append("\n");

            throw new RuntimeException("Image cannot be read by found reader " + reader + ". Reasons:\n" + errorStr);
        }

        // @nixspirit
        // Some issues occur because of TYPE_CUSTOM.
        // Also some of the types are not handled properly by Java
        // and they look different while using KEY_QUALITY_RENDERING options.
        // See WORDSJAVA-569.
        // So we intentionally convert it to ARGB.
        switch (result.getType())
        {
            case BufferedImage.TYPE_CUSTOM:
            case BufferedImage.TYPE_USHORT_555_RGB:
            case BufferedImage.TYPE_USHORT_565_RGB:
            {
                Rectangle bounds = new Rectangle(0, 0, result.getWidth(), result.getHeight());
                result = ImageConverter.convertTo(result, bounds, BufferedImage.TYPE_INT_ARGB);
            }
        }

        // The Java documentation recommends disposing image readers and writers asap.
        reader.dispose();
        // The second attempt (FIX 713)
        if (ios != null)
            ios.close();

        return result;
    }

    private static BufferedImage loadUsingInternalGifEngine(Stream stream)
    {
        BufferedImage result = null;

        try
        {
            GifDecoder decoder = new GifDecoder();
            decoder.read(new NetInputStream(stream));
            result = decoder.getImage();
        }
        catch (Throwable any)
        {
            // fallback to default with/without metadata read
        }

        return result;
    }

    //JAVA-added
    private static javax.imageio.ImageReader getSuitableImageReaderByFileExt(String fileExt)
    {
        // Original ImageIO JPEG reader better match .NET golds than a more recent JAI one.
        Iterator readers = ImageIO.getImageReadersByFormatName(fileExt);
        return findReader(readers, preferredReadersPackage);
    }

    private static javax.imageio.ImageReader getSuitableImageReader(ImageInputStream imageInputStream)
    {
        Iterator readers = ImageIO.getImageReaders(imageInputStream);
        return findReader(readers, preferredReadersPackage);
    }

    private static javax.imageio.ImageReader findReader(Iterator<javax.imageio.ImageReader> readers, String preferred)
    {
        javax.imageio.ImageReader reader = null;
        while (readers.hasNext())
        {
            reader = readers.next();
            if (reader.getOriginatingProvider().getPluginClassName().startsWith(preferred))
                break;
        }

        return reader;
    }

    private static boolean isGifAnimated(IIOMetadata metadata)
    {
        return metadata != null && readGifDelayTime(metadata) > gDefaultDelayTime;
    }

    /***
     * WORDSJAVA-1853
     * The package "com.sun.imageio.plugins.gif.GIFImageMetadata" is hidden since Java9+.
     * The cross-platform solution would be to retrieve the value from the Metadata tree.
     *
     * @param metadata
     * @return delayTime
     */
    private static int readGifDelayTime(IIOMetadata metadata)
    {
        if (metadata == null)
            return gDefaultDelayTime;

        String metaFormatName = metadata.getNativeMetadataFormatName();
        if (!gGifMetadataFormatName.equals(metaFormatName))
            return gDefaultDelayTime;

        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

        if (root == null)
            return gDefaultDelayTime;

        NodeList nodeList = root.getElementsByTagName(gGifGraphicControlExtensionNodeName);
        Node graphicsControlExtensionNode = null;
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node item = nodeList.item(i);
            if (item != null)
            {
                graphicsControlExtensionNode = item;
                break;
            }
        }

        if (graphicsControlExtensionNode == null)
            return gDefaultDelayTime;

        NamedNodeMap attributes = graphicsControlExtensionNode.getAttributes();
        if (attributes == null || attributes.getLength() == 0)
            return gDefaultDelayTime;

        Node delayTimeNode = attributes.getNamedItem(gDelayTimeAttributeName);
        if (delayTimeNode == null)
            return gDefaultDelayTime;

        String delayTime = delayTimeNode.getNodeValue();
        return delayTime != null ? Integer.valueOf(delayTime) : gDefaultDelayTime;
    }

    private static final String preferredReadersPackage = "com.sun.imageio.plugins.jpeg"; // .JPEGImageReader
    private static final int gDefaultDelayTime = 0;
    private static final String gGifGraphicControlExtensionNodeName = "GraphicControlExtension";
    private static final String gDelayTimeAttributeName = "delayTime";
    private static final String gGifMetadataFormatName = "javax_imageio_gif_image_1.0";

    static
    {
        ImageIO.setUseCache(false);
    }
}
