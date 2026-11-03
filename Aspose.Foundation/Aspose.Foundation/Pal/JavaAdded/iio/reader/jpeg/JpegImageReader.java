// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//16/01/13 by Vyacheslav Durin

package com.aspose;

import com.aspose.ms.java.logging.Log;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RescaleOp;
import java.io.*;
import java.util.Iterator;

/**
 * This class is an extended analogue of Java's ImageReader.
 * It reads the same types of images as standard Java's does(e.g. JPEG, PNG).
 * Also it supports images which have CMYK, YCCK, sRGB colorspaces along with the RGB, ARGB.
 * Use: <code>new JpegImageReader(ios).read(arrayToStoreResult);</code>
 */
public class JpegImageReader
{
    /**
     * Stream to keep the picture in.
     */
    private ImageInputStream inputStream;
    private boolean isYCCKInverted = true;

    /**
     * Default ctor.
     */
    public JpegImageReader(ImageInputStream image)
    {
        inputStream = image;
    }

    /**
     * The main method to read an image.
     */
    public BufferedImage read() throws IOException
    {
        return read(null);
    }

    /**
     * The main method to read an image.
     *
     * @param isFromCMYKYCCKresult - it is a 1-length array which its first element will keep <code>true</code>
     *                             if the given image was converted from CMYK|YCCK|SRGB colorspace.
     */
    public BufferedImage read(boolean[] isFromCMYKYCCKresult) throws IOException
    {
        BufferedImage img = null;
        try
        {
            // First of all. We try to recognize what exactly colorspace this image has.
            // Read ICC_Profile from it and convert it to RGB without loss.
            // Usually images which have sRGB,CMYK,YCCK colorspaces will be read with this method.
            img = read(inputStream, isYCCKInverted, isFromCMYKYCCKresult);

            // If it is pretty usual image then we read it with standard java ImageReader.
           // if (img == null)
           //     return readWithStandardReader();
        }
        catch (Exception e)
        {
            Log.warn(e);

            // Last resort.
         //   img = readWithStandardReader();
        }
        return img;
    }

    /**
     * Choose an appropriate reader from the Java's list and read.
     *
     * @return new buffered image or null if image cannot be read.
     */
    private BufferedImage readWithStandardReader() throws IOException
    {
        BufferedImage img = null;

        inputStream.seek(0);
        for (Iterator<ImageReader> i = ImageIO.getImageReaders(inputStream); img == null && i.hasNext(); )
        {
            ImageReader r = i.next();
            try
            {
                inputStream.seek(0);
                r.setInput(inputStream);
                img = r.read(0);
                break;
            }
            catch (IOException ioe)
            {
                Log.warn(ioe);
            }
            finally
            {
                if (r != null)
                    r.dispose();
            }
        }

        // One more attempt by using Toolkit.createImage + ImageIcon to take into account ICC_Profile and other Java magic.
        if (img == null)
        {
            // FIX WORDSJAVA-1122: Losing some pictures while converting from Word (docx) to PDF
            // img = readSRGB(inputStream, 1, 1);  ->  this strange code produces image 1x1
            Image toolkitImage = getToolkitImage(inputStream);
            img = isWrongImage(toolkitImage) ? null : createARGBImage(toolkitImage);
        }

        return img;
    }

    /**
     * Read image from the stream and convert it to sRGB colorspace.
     *
     * @param in                is image's stream.
     * @param inverseYCCKColors is a flag which indicates if CMYK|YCCK colorspace is inverted.
     * @param wasConverted      is an array to store result in as its first item.
     */
    private static BufferedImage read(ImageInputStream in, boolean inverseYCCKColors, boolean[] wasConverted) throws Exception
    {
        // Seek to start of input stream
        in.seek(0);

        // Extract metadata from the JFIF stream.
        int numberOfComponentsInFrame = 0;
        int app14AdobeColorTransform = 0;
        ByteArrayOutputStream app2ICCProfile = new ByteArrayOutputStream();

        JpegSegmentInputStream jsis = new JpegSegmentInputStream(new InputStreamAdapter(in));
        try
        {
            // read segments
            for (JpegSegment seg = jsis.getNextSegment(); seg != null; seg = jsis.getNextSegment())
            {
                if (seg.isStartOfFrameHeader())
                {
                    // Start of Frame Header marker segment
                    DataInputStream dis = new DataInputStream(jsis);
                    dis.readUnsignedByte();
                    dis.readUnsignedShort();
                    dis.readUnsignedShort();
                    numberOfComponentsInFrame = dis.readUnsignedByte();
                    dis.close();
                    break;
                }
                else if (seg.isApp2())
                {
                    // APP2: Application-specific marker segment
                    if (seg.length >= 26)
                    {
                        DataInputStream dis = new DataInputStream(jsis);
                        // Check for Adobe ICC_PROFILE containing the
                        // null-terminated string: "ICC_PROFILE".
                        if (dis.readLong() == 0x4943435f50524f46L && dis.readInt() == 0x494c4500)
                        {
                            // Skip 2 bytes
                            dis.skipBytes(2);

                            // Read Adobe ICC_PROFILE in buffer.
                            byte[] b = new byte[512];
                            for (int count = dis.read(b); count != -1; count = dis.read(b))
                                app2ICCProfile.write(b, 0, count);

                            dis.close();
                        }
                    }
                }
                else if (seg.isApp14())
                {
                    // APP14: Application-specific marker segment
                    // Fixed WORDSJAVA-883: Color of CMYK JPEG background image is changed after conversion from Docx to Pdf.
                    // For some reason APP14 segment has length more than 12 bytes. The excess bytes will be skipped
                    // at the next iteration. The source JPEG image has YCCK color transformation rather than CMYK.
                    // Value of app14AdobeColorTransform must be 2.
                    if (seg.length >= 12)
                    {
                        DataInputStream dis = new DataInputStream(jsis);
                        // Check for 6-bytes containing the null-terminated string: "Adobe".
                        if (dis.readInt() == 0x41646f62L && dis.readUnsignedShort() == 0x6500)
                        {
                            dis.readUnsignedByte();
                            dis.readUnsignedShort();
                            dis.readUnsignedShort();
                            app14AdobeColorTransform = dis.readUnsignedByte();
                            dis.close();
                        }
                    }
                }
            }

            // Read the image data
            BufferedImage img = null;

            // Try to instantiate an ICC_Profile from the app2ICCProfile
            ICC_Profile profile = getICC_profile(app2ICCProfile);

            // mostly sRGB
            // AMAS: FIX WORDSJAVA-2471 - use readDiscrepantSRGB() not only if profile.getColorSpaceType() == ColorSpace.TYPE_GRAY
            if (numberOfComponentsInFrame == 3)
            {
                if (profile != null)
                {
                    img = readDiscrepantSRGB(in);
                }
                else
                {
                    // Do nothing, let's left the job for default ImageIO reader.
                }
            }
            // CMYK|YCCK
            else if (numberOfComponentsInFrame == 4)
            {
                // In case of failure, use a Generic CMYK profile
                if (profile == null)
                    profile = CmykIccProfile.getInstance();

                switch (app14AdobeColorTransform)
                {
                    case 0:
                    default:
                        in.seek(0);
                        img = ImageReaderHelper.createImage(getRaster(in), profile, CmykIccProfile.getInstance(), inverseYCCKColors, false);// from CMYK
                        break;
                    case 1:
                        throw new IOException("YCbCr not supported");
                    case 2:
                        in.seek(0);
                        img = ImageReaderHelper.createImage(getRaster(in), profile, CmykIccProfile.getInstance(), inverseYCCKColors, true);// from YCCK
                        break;
                }

                // intentionally make the result picture brighter
                // otherwise "White" becomes "Light Blue"
                new RescaleOp(1.02f, 0, null).filter(img, img);
            }

            // store the result for further use out of this class.
            if (wasConverted != null && wasConverted.length > 0 && (numberOfComponentsInFrame == 4 || app2ICCProfile.size() > 0))
                wasConverted[0] = true;

            return img;
        }
        finally
        {
            jsis.close();
        }
    }

    private static ICC_Profile getICC_profile(ByteArrayOutputStream app2ICCProfile)
    {
        if (app2ICCProfile.size() > 0)
        {
            byte[] byteArray = app2ICCProfile.toByteArray();
            try (ByteArrayInputStream byteStream = new ByteArrayInputStream(byteArray))
            {
                return ICC_Profile.getInstance(byteStream);
            }
            catch (Throwable ex)
            {
                // icc profile is corrupt
                Log.warn(ex);
            }
        }
        return null;
    }

    /**
     * Reads an image and converts it to RGB regardless of actual number of components and color profile.
     * <p>
     * This method should not be used normally (e.g. when metadata and actual content of an image are consistent). But
     * it can be helpful in case of "broken" images. However, this method is not all-powerful. For instance, it doesn't
     * protect from JPEG-bomb that leads to out-of-memory error (when too huge dimensions of an image are specified in
     * its metadata).
     */
    private static BufferedImage readDiscrepantSRGB(ImageInputStream input) throws IOException
    {
        Image toolkitImage = getToolkitImage(input);

        if (isWrongImage(toolkitImage))
            return null;

        return createRGBImage(
                toolkitImage,
                toolkitImage.getWidth(null),
                toolkitImage.getHeight(null));
    }

    private static Image getToolkitImage(ImageInputStream input) throws IOException
    {
        input.seek(0);
        byte[] picture = readAllBytes(input);
        return new ImageIcon(picture).
                getImage();
    }

    private static boolean isWrongImage(Image img)
    {
        return img.getWidth(null) < 0 || img.getHeight(null) < 0;
    }

    private static BufferedImage createRGBImage(Image image, int desiredWidth, int desiredHeight)
    {
        return createBufferedImage(image, desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
    }

    private static BufferedImage createARGBImage(Image image)
    {
        return createARGBImage(image, image.getWidth(null), image.getHeight(null));
    }

    private static BufferedImage createARGBImage(Image image, int desiredWidth, int desiredHeight)
    {
        return createBufferedImage(image, desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
    }

    private static BufferedImage createBufferedImage(Image image, int desiredWidth, int desiredHeight, int imageType)
    {
        BufferedImage result = new BufferedImage(desiredWidth, desiredHeight, imageType);
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return result;
    }

    private static byte[] readAllBytes(ImageInputStream input) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] b = new byte[1024 * 10];
        int l = 0;
        while ((l = input.read(b)) >= 0)
            output.write(b, 0, l);

        byte[] result = output.toByteArray();
        output.close();
        return result;
    }

    /**
     * Extract raster from the given image's stream.
     */
    private static Raster getRaster(ImageInputStream in) throws IOException
    {
        ImageReader reader = PreferredJpegReader.get();
        if (reader == null)
            return null;

        ImageInputStream inputStream = (in instanceof ImageInputStream) ? in : ImageIO.createImageInputStream(in);
        reader.setInput(inputStream);
        Raster raster = reader.readRaster(0, null);
        reader.dispose();
        return raster;
    }

    /**
     * PreferredJpegReader.get() should be used to get new jpeg reader using cached jpeg ImageReaderSpi.
     * <p>
     * Initialization On Demand Holder idiom is used to implement lazy loading of jpeg ImageReaderSpi.
     */
    private static class PreferredJpegReader
    {
        private static final String gPreferredPackage = "com.sun.imageio.plugins.jpeg"; // .JPEGImageReader
        private static ImageReaderSpi gProvider;

        public static ImageReader get() throws IOException
        {
            if (gProvider != null)
                return gProvider.createReaderInstance();

            return null;
        }

        static
        {
            try
            {
                Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
                while (readers.hasNext())
                {
                    ImageReader candidateReader = (ImageReader) readers.next();
                    if (gProvider == null ||
                            candidateReader.getOriginatingProvider().getPluginClassName().startsWith(gPreferredPackage))
                        gProvider = candidateReader.getOriginatingProvider();
                }
            }
            catch (Exception ex)
            {
                Log.warn(ex);
            }
        }
    }

    public static class InputStreamAdapter extends InputStream
    {
        public InputStreamAdapter(ImageInputStream stream)
        {
            super();
            this.mStream = stream;
        }

        public int read() throws IOException
        {
            return mStream.read();
        }

        public int read(byte b[], int off, int len) throws IOException
        {
            return mStream.read(b, off, len);
        }

        ImageInputStream mStream;
    }
}
