// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.BinaryReader;
import java.io.EOFException;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.BitConverter;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.Convert;
import com.aspose.ms.System.msMath;


/// <summary>
/// Utility functions to deal with images.
/// </summary>
public /*static*/ class ImageUtil
{
	/* Simulation of static class by using private constructor */
	private ImageUtil()
	{}

    public static /*FileFormat*/int getImageType(byte[] data) throws Exception
    {
        if ((data == null) || (data.length == 0))
            return FileFormat.UNKNOWN;

        Stream stream = new MemoryStream(data);
        try /*JAVA: was using*/
        {
            return getImageType(CompressedData.getStream(stream));
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Gets the image type without invoking GDI+.
    /// Retains the stream position.
    /// </summary>
    public static /*FileFormat*/int getImageType(Stream stream) throws Exception
    {
        if ((stream == null) || (stream.getLength() == 0))
            return FileFormat.UNKNOWN;

        long savePos = stream.getPosition();
        try
        {
            boolean check = isStandardMetafile(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.WMF;

            check = isPlaceableMetafile(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.WMF;

            check = isEmf(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.EMF;

            check = isJpeg(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.JPEG;

            check = isPng(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.PNG;

            check = isBmp(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.BMP;

            check = isPict(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.PICT;

            // alexnosk: PICT can be represented also as a PICT file. check for it too.
            check = isPictFile(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.PICT;

            check = isGif(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.GIF;

            check = isTiff(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.TIFF;

            check = isPdf(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.PDF;

            check = isSvg(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.SVG;

            check = isMov(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.MOV;

            check = isIco(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.ICO;

            check = isWebP(stream);
            stream.setPosition(savePos);
            if (check)
                return FileFormat.WEB_P;
        }
        catch(Exception e)
        {
            // WORDSNET-4996 Just ignore all unexpected troubles recognizing the image type.
        }

        stream.setPosition(savePos);
        return FileFormat.UNKNOWN;
    }

    /// <summary>
    /// Gets the image size and resolution of given image file without invoking GDI+.
    /// </summary>
    public static ImageSizeCore getImageSize(String imageFile) throws Exception
    {
        FileStream imageStream = new FileStream(imageFile, FileMode.OPEN);
        try /*JAVA: was using*/
    	{
            return getImageSize(imageStream);
    	}
        finally { if (imageStream != null) imageStream.close(); }
    }

    /// <summary>
    /// Gets the image size and resolution without invoking GDI+.
    /// </summary>
    public static ImageSizeCore getImageSize(Stream imageStream) throws Exception
    {
        return getImageSize(imageStream, getImageType(imageStream));
    }

    /// <summary>
    /// Gets the image size and resolution without invoking GDI+.
    /// </summary>
    public static ImageSizeCore getImageSize(byte[] imageBytes) throws Exception
    {
        return getImageSize(imageBytes, getImageType(imageBytes));
    }

    /// <summary>
    /// Gets the image size and resolution without invoking GDI+.
    /// </summary>
    public static ImageSizeCore getImageSize(byte[] imageBytes, /*FileFormat*/int imageType) throws Exception
    {
        return getImageSize(new MemoryStream(imageBytes), imageType);
    }

    /// <summary>
    /// Gets the image size and resolution without invoking GDI+.
    /// Retains the stream position.
    /// </summary>
    public static ImageSizeCore getImageSize(Stream imageStream, /*FileFormat*/int imageType)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	long savePos = imageStream.getPosition();
        	ImageSizeCore imageSize;

        	switch (imageType)
        	{
            	case FileFormat.WMF:
            	case FileFormat.EMF:
                	imageSize = getMetafileSize(imageStream);
                	break;
            	case FileFormat.PICT:
                	imageSize = getPictSize(imageStream);
                	break;
            	case FileFormat.BMP:
                	imageSize = getBmpSize(imageStream);
                	break;
            	case FileFormat.PNG:
                	imageSize = getPngSize(imageStream);
                	break;
            	case FileFormat.JPEG:
                	imageSize = getJpegSize(imageStream);
                	break;
            	case FileFormat.GIF:
                	imageSize = getGifSize(imageStream);
                	break;
            	case FileFormat.TIFF: // AN 20/05/2009
                	imageSize = getTiffSize(imageStream);
                	break;
            	case FileFormat.ICO:
                	imageSize = getIcoSize(imageStream, 0);
                	break;
            	case FileFormat.WEB_P:
                	imageSize = getWebPSize(imageStream);
                	break;
            	default:
                	// RK Return at least some usable value for unknown formats.
                	imageSize = ImageSizeCore.createWithResolution(100, 100, ImageConstants.STANDARD_RESOLUTION);
                	break;
        	}

        	imageStream.setPosition(savePos);
        	return imageSize;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    //>>>>>>>> #region  Is<ImageType>

    /// <summary>
    /// Documentation for the format is in Aspose.Words\Doc.
    /// </summary>
    public static boolean isTiff(byte[] data) throws Exception
    {
        return TiffDataReader.isTiff(data);
    }

    /// <summary>
    /// Documentation for the format is in Aspose.Words\Doc.
    /// </summary>
    public static boolean isTiff(Stream stream) throws Exception
    {
        return TiffDataReader.isTiff(stream);
    }

    public static boolean isJpeg(byte[] data) throws Exception
    {
        Debug.assertTrue(data != null);

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isJpeg(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Documentation for the format is in Aspose.Words\Doc.
    /// Will detect Jfif and Exif files as jpeg.
    /// </summary>
    public static boolean isJpeg(Stream stream) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream);
        try
        {
            /*uint*/int soi = (reader.readUInt16() & 0xFFFF);
            if (soi != 0xd8ff)
                return false;

            // For Jfif further bytes will be:
            // 0xe0ff
            // 0x1000
            // 0x4649464a

            // For Exif further bytes will be
            // 0xe1ff
            // 0xef21
            // 0x66697845
        }
        catch (EOFException e)
        {
            // The input stream is too short.
            return false;
        }
        return true;
    }

    public static boolean isPng(byte[] data) throws Exception
    {
        Debug.assertTrue(data != null);

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isPng(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// http://www.libpng.org/pub/png/spec/iso/index-object.html
    /// </summary>
    public static boolean isPng(Stream stream) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream);
        try
        {
            /*uint*/int b1 = reader.readUInt32();
            if (b1 != 0x474e5089)
                return false;

            /*uint*/int b2 = reader.readUInt32();
            if (b2 != 0x0a1a0a0d)
                return false;
        }
        catch (EOFException e)
        {
            // The input stream is too short.
            return false;
        }
        return true;
    }

    public static boolean isBmp(byte[] data) throws Exception
    {
        Debug.assertTrue(data != null);

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isBmp(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Documentation for the format is in Aspose.Words\Doc.
    /// Also here is a good description of OS/2 BMP headers:
    ///   http://www.fileformat.info/format/os2bmp/egff.htm
    /// </summary>
    /// <dev>
    /// Here are several supported headers. (Are they going to invent any more?)
    /// BITMAPINFOHEADER - 0x28
    /// BITMAPV4HEADER - 0x6C
    /// BITMAPV5HEADER - 0x7C
    /// OS/2 1.x BMP header - 0x0C
    /// OS/2 2.x BMP header - 0xF0 (? - saw in our docs)
    /// </dev>
    public static boolean isBmp(Stream stream) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream);
        try
        {
            // "BM" as the first two bytes.
            // We don't recognize other signatures as well as Windows 1.x device dependent bitmaps (DDB).
            /*uint*/int sig = (reader.readUInt16() & 0xFFFF);
            if (sig != 0x4d42)
                return false;

            // Full file size if non-zero. This can be zero in files, means not specified.
            /*uint*/int fileSize = reader.readUInt32();

            // Reserved and should be zero in Windows. In OS/2 there are two hot-spot coordinates.
            // Let's don't check this otherwise we would have to guess what format version it is.
            reader.readUInt32();

            // I saw a file with image failing this condition. WORDSNET-4899.
            // Do we need a more precise test? First guess is no, since we hadn't trouble before WORDSNET-4899.
            /*uint*/int dataOffset = reader.readUInt32();
            if ((fileSize != 0) && ((dataOffset & 0xFFFFFFFFL) > (fileSize & 0xFFFFFFFFL)))
                return false;

            // At this point we have read first 14 bytes which are the BITMAPFILEHEADER.
            // Bitmap info header size.
            // The very minimum it can be is 12 bytes (0x0C) for OS/2 v1.x.
            // If it is greater, then next minimum is 16 (0x10) since the last mandatory field is BPP.
            // Before reading any further field this length should be checked to properly handle OS/2 BMPs.
            final /*uint*/int BMIH_OS_21_X_SIZE = 0x0C;
            final /*uint*/int BMIH_MIN_OTHER_THEN_OS_21_X_SIZE = 0x10;

            /*uint*/int bmihSize = reader.readUInt32();
            boolean isOS21x = (bmihSize == BMIH_OS_21_X_SIZE);

            if (!isOS21x && ((bmihSize & 0xFFFFFFFFL) < (BMIH_MIN_OTHER_THEN_OS_21_X_SIZE & 0xFFFFFFFFL)))
                return false;

            // Width and height.
            // In OS/2 1.x they are unsigned 16 bits long each. In OS/2 2.x - unsigned 32 bits.
            // In Windows always signed 32 bits.
            if (isOS21x)
            {
                reader.readUInt32();
            }
            else
            {
                reader.readInt32();
                reader.readInt32();
            }

            // Planes. Seems to be always 1 in any BMP dialect.
            /*ushort*/short planes = reader.readUInt16();
            if (planes != 1)
                return false;

            // Bits per pixel value is restricted to this subset.
            /*ushort*/short bitsPerPixel = reader.readUInt16();
            ArrayList<Short> allowedbitsPerPixel = msArrayList.ctor(new /*ushort*/short[] { (short) 1, (short) 4, (short) 8, (short) 16, (short) 24, (short) 32 });
            if (allowedbitsPerPixel.contains(bitsPerPixel))
                return true;

            // If you suggest reading and checking more be aware of possible header size variations.
        }
        catch (EOFException e)
        {
            // The input stream is too short.
            return false;
        }
        return false;
    }

    public static boolean isGif(byte[] data) throws Exception
    {
        Debug.assertTrue(data != null);

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isGif(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Documentation for the format is in Aspose.Words\Doc.
    /// </summary>
    public static boolean isGif(Stream stream) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream);

        if (!StreamUtil.hasEnoughBytesToRead(reader, 4 /* Int32 */))
            return false;

        int sig = reader.readInt32() & 0x00FFFFFF;
        return (sig == 0x00464947); // 'GIF'
    }

    public static boolean isPict(byte[] data) throws Exception
    {
        Debug.assertTrue(data != null);

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isPict(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// If PICT is read from file, it contains 512 bytes header (if it is read from document it does not contain it).
    /// Method checks if data contains PICT image read from file.
    /// </summary>
    public static boolean isPictFile(byte[] data) throws Exception
    {
        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isPictFile(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// If PICT is read from file, it contains 512 bytes header (if it is read from document it does not contain it).
    /// Method checks if data contains PICT image read from file.
    /// </summary>
    public static boolean isPictFile(Stream data) throws Exception
    {
        // If data contains less than 512 bytes + 12 bytes header it cannot be pict.
        if (data.getLength() <= (ImageConstants.PICT_HEADER_LENGTH + 12))
            return false;

        data.setPosition(ImageConstants.PICT_HEADER_LENGTH);
        return isPict(data);
    }

    /// <summary>
    /// http://www.fileformat.info/format/macpict/
    /// </summary>
    public static boolean isPict(Stream stream) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream);
        try
        {
            // This is 16 byte size of the image. It is lame.
            // We've seen files longer than 64k. This value is simply a truncation of 32 bit.
            reader.readUInt16();  // SHORT

            // Top, left, bottom, right (big-endian).
            // Documentation says left, top, right, bottom, but stupid Mac stores y first, then x. Perverts.
            reader.readUInt16();  // SHORT
            reader.readUInt16();  // SHORT
            reader.readUInt16();  // SHORT
            reader.readUInt16();  // SHORT

            int versionOperator = (BitUtil.swapUInt16(reader.readUInt16()) & 0xFFFF);
            // This is PICT V1.0
            switch (versionOperator)
            {
                case 0x0111:
                {
                    // WORDSJAVA-1142 Missing ODT chart while saving to Png.
                    // It's not enough to check only version operator and version number for PICT V1.0, because it can
                    // return true for PNG format.
                    // So check picFrame (PICT v1.0) additionally.

                    // Skip picSize records
                    reader.readUInt16(); // WORD     Picture size in bytes
                    reader.readUInt16(); // WORD     Image top
                    reader.readUInt16(); // WORD     Image left
                    reader.readUInt16(); // WORD     Image bottom
                    reader.readUInt16(); // WORD     Image right
                                         // Check picFrame
                    return (reader.readByte() == 0x11 && reader.readByte() == 0x01);
                }
                case 0x0011:
                {
                    int versionNumber = (BitUtil.swapUInt16(reader.readUInt16()) & 0xFFFF);
                    if (versionNumber == 0x02ff)
                        return true;
                    break;
                }
                default:
                    break;
            }
        }
        catch (EOFException e)
        {
            // The input stream is too short.
            return false;
        }
        return false;
    }

    /// <summary>
    /// Returns true if this image is a Dib file.
    /// </summary>
    public static boolean isDib(byte[] data) throws Exception
    {
        Debug.assertTrue(data != null);

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isDib(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Returns true if this image is a Dib file.
    /// </summary>
    public static boolean isDib(Stream imageStream) throws Exception
    {
        long pos = imageStream.getPosition();

        imageStream.setPosition(0);
        if (imageStream.getLength() < 2/*sizeof(ushort)*/)
        {
            // The input buffer is too short.
            return false;
        }

        // Fairly primitive attempt to detect a DIB header.
        byte[] data = new byte[2];
        StreamUtil.read(imageStream, data, 0, 2);

        imageStream.setPosition(pos);

        /*uint*/int size = (BitConverter.toUInt16(data, 0) & 0xFFFF);
        return size == 40;
    }

    /// <summary>
    /// Returns true if this image is an ICO file.
    /// </summary>
    public static boolean isIco(byte[] data) throws Exception
    {
        Debug.assertTrue(data != null);

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isIco(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Returns true if this image is an ICO file.
    /// See https://en.wikipedia.org/wiki/ICO_(file_format)
    /// </summary>
    public static boolean isIco(Stream stream) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream);
        try
        {
            int reserved = (reader.readUInt16() & 0xFFFF);
            if (reserved != 0)
                return false;
            int type = (reader.readUInt16() & 0xFFFF);
            if (type != 1)
                return false;
            int nrEntries = (reader.readUInt16() & 0xFFFF);
            if (nrEntries < 1)
                return false;

            for (int i = 0; i < nrEntries; ++i)
            {
                reader.readByte(); // width
                reader.readByte(); // height
                reader.readByte(); // number of color palettes
                int reservedByte = (reader.readByte() & 0xFF); // reserved byte
                if (reservedByte != 0)
                    return false;
                int nrColorPanes = (reader.readUInt16() & 0xFFFF); // number of color planes
                if (nrColorPanes != 0 && nrColorPanes != 1)
                    return false;
                reader.readUInt16(); // bits per pixel
                reader.readUInt32(); // size in bytes
                reader.readUInt32(); // offset
            }
        }
        catch (EOFException e)
        {
            // The input stream is too short.
            return false;
        }
        return true;
    }

    /// <summary>
    /// Returns true if this image is a non-placeable WML.
    ///
    /// See X:\Aspose\Aspose.Words\Doc\Specs\Wmf\wmf.htm
    /// </summary>
    public static boolean isStandardMetafile(byte[] imageBytes) throws Exception
    {
        MemoryStream imageStream = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
    	{
            return isStandardMetafile(imageStream);
    	}
        finally { if (imageStream != null) imageStream.close(); }
    }

    public static boolean isStandardMetafile(Stream imageStream) throws Exception
    {
        Debug.assertTrue(imageStream != null);

        // WORDSNET-7011 Resilience against too short image bytes.
        final int METAFILE_DETECTION_CHUNK_SIZE = 18;
        if (imageStream.getLength() < METAFILE_DETECTION_CHUNK_SIZE)
            return false;

        BinaryReader reader = new BinaryReader(imageStream);

        // Type of metafile (0=memory, 1=disk)
        int type = reader.readInt16();
        if ((type != 0) && (type != 1))
            return false;

        // Size of header in WORDS (always 9)
        int headerSize = reader.readInt16();
        if (headerSize != 9)
            return false;

        // Version of Microsoft Windows used
        reader.readInt16();

        // Total size of the metafile in WORDs
        reader.readInt32();

        // Number of objects in the file
        reader.readInt16();

        // The size of largest record in WORDs
        reader.readInt32();

        // Not Used (always 0)
        int unused = reader.readInt16();
        if (unused != 0)
            return false;

        return true;
    }

    /// <summary>
    /// Returns true if this image is a placeable WMF.
    /// </summary>
    public static boolean isPlaceableMetafile(byte[] imageBytes) throws Exception
    {
        Debug.assertTrue(imageBytes != null);

        MemoryStream imageStream = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
    	{
            return isPlaceableMetafile(imageStream);
    	}
        finally { if (imageStream != null) imageStream.close(); }
    }

    public static boolean isPlaceableMetafile(Stream imageStream) throws Exception
    {
        BinaryReader reader = new BinaryReader(imageStream);

        // Detection needs at least 6 bytes.
        if (!StreamUtil.hasEnoughBytesToRead(reader, 6))
            return false;

        // Magic number (always 9AC6CDD7h)
        /*uint*/int key = reader.readUInt32();
        if ((key & 0xFFFFFFFFL) != 0x9AC6CDD7L)
            return false;

        // Metafile HANDLE number (always 0)
        int handle = reader.readInt16();
        if (handle != 0)
            return false;

        return true;
    }

    /// <summary>
    /// Returns true if the image is a WMF or EMF metafile.
    /// </summary>
    public static boolean isMetafile(/*FileFormat*/int imageType)
    {
        return (imageType == FileFormat.WMF) || (imageType == FileFormat.EMF);
    }

    public static boolean isEmf(Stream imageStream) throws Exception
    {
        BinaryReader reader = new BinaryReader(imageStream);

        // Detection needs at least 44 bytes.
        if (!StreamUtil.hasEnoughBytesToRead(reader, 44))
            return false;

        // For the EMF header record this value is always 00000001h
        /*uint*/int recordType = reader.readUInt32();
        if (recordType != 1)
            return false;

        // Signature ID (always 0x464D4520)
        reader.getBaseStream().setPosition(40);
        /*uint*/int signature = reader.readUInt32();
        if (signature != 0x464D4520)
            return false;

        return true;
    }

    public static boolean isPdf(Stream stream) throws Exception
    {
        return isPdf(StreamUtil.readAndReturn(stream, 4));
    }

    /// <summary>
    /// Returns true if PDF.
    /// </summary>
    public static boolean isPdf(byte[] data)
    {
        Debug.assertTrue(data != null);

        // First four bytes should be: 0x25 0x50 0x44 0x46 in hex format, in ASCII it's %PDF
        if (data != null)
            return (((data[0] & 0xFF) == '%') && ((data[1] & 0xFF) == 'P') && ((data[2] & 0xFF) == 'D') && ((data[3] & 0xFF) == 'F'));

        return false;
    }

    /// <summary>
    /// Returns true if the specified byte array contains an SVG image.
    /// </summary>
    public static boolean isSvg(byte[] data)
    {
        if (!ArrayUtil.hasData(data))
            return false;

        // Check and convert Java Expected Exception
        try
        {
            MemoryStream dataStream = new MemoryStream(data);
            try /*JAVA: was using*/
            {
                return isSvg(dataStream);
            }
            finally { if (dataStream != null) dataStream.close(); }
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /// <summary>
    /// Returns true if the specified stream contains an SVG image.
    /// </summary>
    public static boolean isSvg(Stream stream) throws Exception
    {
        // WORDSNET-21302 Fast check if the stream looks like an SVG XML. This reduces the number of exceptions we get
        // later when we try to parse the input as XML.
        if (!canBeXml(stream))
            return false;

        // Check if the root node is SVG.
        try
        {
            AnyXmlReader xmlReader = new AnyXmlReader(stream);
            // WORDSNET-21024 We no longer check if the root node of the SVG image has an "xmlns" attribute with
            // the SVG namespace. Standalone SVG images without the namespace declaration are malformed but Aspose.Words
            // supports them.
            return "svg".equals(xmlReader.getLocalName());
        }
        catch (IllegalStateException e)
        {
            return false;
        }
    }

    public static boolean isMov(byte[] data) throws Exception
    {
        MemoryStream dataStream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isMov(dataStream);
    	}
        finally { if (dataStream != null) dataStream.close(); }
    }

    /// <summary>
    /// Returns true if QuickTime MOV file.
    /// </summary>
    public static boolean isMov(Stream stream) throws Exception
    {
        final int HEADER_SIZE = 16;

        // WORDSNET-13374.
        // Check if stream is long enough to have the MOV header.
        if (stream.getPosition() + HEADER_SIZE > stream.getLength())
            return false;

        BinaryReader reader = new BinaryReader(stream);
        String t = Encoding.getASCII().getString(reader.readBytes(HEADER_SIZE), 4, 6);
        // Rewind stream position.
        stream.setPosition(stream.getPosition() - HEADER_SIZE);

        return "ftypqt".equals(t);
    }

    /// <summary>
    /// Returns true if WebP file.
    /// </summary>
    public static boolean isWebP(Stream stream) throws Exception
    {
        final int HEADER_SIZE = 4;

        if (stream.getPosition() + HEADER_SIZE > stream.getLength())
            return false;

        BinaryReader reader = new BinaryReader(stream);
        String t = Encoding.getASCII().getString(reader.readBytes(HEADER_SIZE), 0, 4);
        // Rewind stream position.
        stream.setPosition(stream.getPosition() - HEADER_SIZE);

        return "RIFF".equals(t);
    }
    //<<<<<<<< #endregion 

    /// <summary>
    /// Reads a DIB-bitmap from a reader into a byte array and prepends it with a BMP header.
    /// </summary>
    public static byte[] prependBmpHeader(BinaryReader reader, int dibLength) throws Exception
    {
        long startPos = reader.getBaseStream().getPosition();
        BitmapInfoHeader dibHeader = DibHeader.readAsBitmapInfoHeader(reader);
        byte[] colorTable = getColorTable(reader, dibHeader, dibLength, startPos);
        int headerLength = (int)(reader.getBaseStream().getPosition() - startPos);
        return buildBmp(dibHeader, colorTable, reader, dibLength - headerLength);
    }

    private static byte[] getColorTable(BinaryReader reader, BitmapInfoHeader dibHeader, int dibLength, long startPos) throws Exception
    {
        byte[] colorTable = new byte[0];

        // Read Color Table only if there are some bytes will be remained for an pixels array.
        if (reader.getBaseStream().getPosition() - startPos + dibHeader.getColorTableSize() < dibLength)
            colorTable = reader.readBytes(dibHeader.getColorTableSize());

        return colorTable;
    }

    /// <summary>
    /// Gets palette size.
    /// </summary>
    static int getPaletteSize(/*BitmapCompression*/int compression, int bitCount, int clrUsed)
    {
        final int COLOR_ENTRY_SIZE = 4;

        // See http://msdn.microsoft.com/en-us/library/cc250406%28v=prot.10%29 for more info.

        // If compression is BI_BITFIELDS then color table contains three DWORD color masks.
        // BI_BITFIELDS could be used for 32 and 16 bit colors.
        if (((bitCount == 32) || (bitCount == 16)) && (compression == BitmapCompression.BIT_FIELDS))
            return 12;

        // If BI_BITFIELDS compression is not used then color table is not used for 32, 24 and 16 bit colors.
        if (bitCount > 8)
            return 0;

        // For 1, 2, 4 and 8 bit colors ClrUsed can be explicit number of colors or the full table is present.
        int numColors = (clrUsed != 0) ? clrUsed : (1 << bitCount);

        return numColors * COLOR_ENTRY_SIZE;
    }

    /// <summary>
    /// Reads a DIB-bitmap from a reader into a byte array and prepends it with a BMP header.
    ///
    /// WORDSJAVA-1333 Inline image not displayed.
    /// For some reason image height, specified in ico header is different from image height, specified in dib header.
    /// It seems that ico header contains a correct size 16x16, rather than 16x32 as specified in dib header.
    /// So, for dib rasters extracted from ico files, we should specify image size explicitly.
    /// </summary>
    public static byte[] prependBmpHeader(BinaryReader reader, int dibLength, int width, int height) throws Exception
    {
        long startPos = reader.getBaseStream().getPosition();
        BitmapInfoHeader dibHeader = DibHeader.readAsBitmapInfoHeader(reader);
        dibHeader.setWidth(width);
        dibHeader.setHeight(height);
        byte[] colorTable = getColorTable(reader, dibHeader, dibLength, startPos);
        int headerLength = (int)(reader.getBaseStream().getPosition() - startPos);
        return buildBmp(dibHeader, colorTable, reader, dibLength - headerLength);
    }

    private static byte[] buildColorTableData(DrColor[] colorTable)
    {
        byte[] colorTableData = new byte[colorTable.length * 4];

        for (int i = 0; i < colorTable.length; i++)
        {
            colorTableData[4 * i] = (byte)colorTable[i].getB();
            colorTableData[4 * i + 1] = (byte)colorTable[i].getG();
            colorTableData[4 * i + 2] = (byte)colorTable[i].getR();
            colorTableData[4 * i + 3] = (byte) 0x00;
        }

        return colorTableData;
    }

    /// <summary>
    /// Builds valid BMP.
    /// </summary>
    static byte[] buildBmp(
        DibHeader dibHeader, byte[] colorTable, BinaryReader reader, int bitmapDataLength) throws Exception
    {
        // BMP layout:
        // - BitmapFileHeader
        // - BitmapInfoHeader
        // - Color table
        // - Bitmap data
        int bmpHeaderSize = BitmapFileHeader.STRUCTURE_SIZE + dibHeader.getSize() + colorTable.length;
        byte[] bmp = new byte[bmpHeaderSize + bitmapDataLength];
        MemoryStream stream = new MemoryStream(bmp);
        try /*JAVA: was using*/
        {
            BinaryWriter writer = new BinaryWriter(stream);
            BitmapFileHeader fileHeader = new BitmapFileHeader();
            fileHeader.Size = (/*uint*/int)(bmpHeaderSize + bitmapDataLength);
            fileHeader.OffBits = (/*uint*/int)bmpHeaderSize;
            fileHeader.write(writer);
            dibHeader.write(writer);
            writer.writeBytes(colorTable);
            writer.flush();

            reader.read(bmp, bmpHeaderSize, bitmapDataLength);

            return bmp;
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// DDB - device dependent bitmaps is an old Windows bitmap format.
    /// They sometimes occur in RTF etc. They consist of raw scan data and some fields.
    /// http://msdn2.microsoft.com/en-us/library/ms532298.aspx
    ///
    /// This method converts a DDB into a BMP.
    /// Assumes 1 plane and 1 bit per pixel.
    /// </summary>
    public static byte[] ddbToBmp(
        int width,
        int height,
        int pixelsPerMeterX,
        int pixelsPerMeterY,
        int planes,
        int bitsPerPixel,
        int ddbWidthData,
        byte[] ddbData,
        DrColor[] colorTable) throws Exception
    {
        if (planes != 1)
            throw new IllegalStateException("Expected 1 plane only here.");

        // WORDSNET-22980 Do not throw but ignore non-monochrome images like MS Word does.
        if (bitsPerPixel != 1)
            return null;

        // We can calculate the resulting byte array length upfront.
        final int FORMAT_1_BPP_INDEXED_COLOR_TABLE_SIZE = 8;
        int bmpLength =
            BitmapFileHeader.STRUCTURE_SIZE + // BMP header
            DibHeader.BITMAP_INFO_HEADER_SIZE + // DIB header
            FORMAT_1_BPP_INDEXED_COLOR_TABLE_SIZE + // 1 bit color colortable
            ddbData.length * 2; // DDB scan data is 16 bit aligned, becomes 32 bit aligned.

        // Allocate the resulting byte array and the stream and writer for writing into the array.
        byte[] bmp = new byte[bmpLength];
        MemoryStream stream = new MemoryStream(bmp);
        try /*JAVA: was using*/
        {
            BinaryWriter writer = new BinaryWriter(stream);

            // Write the BMP header.
            BitmapFileHeader bmpHeader = new BitmapFileHeader();
            bmpHeader.Size = (/*uint*/int)bmpLength;
            bmpHeader.OffBits =
                BitmapFileHeader.STRUCTURE_SIZE +
                DibHeader.BITMAP_INFO_HEADER_SIZE +
                FORMAT_1_BPP_INDEXED_COLOR_TABLE_SIZE;
            bmpHeader.write(writer);

            // Write the DIB header.
            BitmapInfoHeader dibHeader = new BitmapInfoHeader();
            dibHeader.setWidth(width);
            dibHeader.setHeight(height);
            dibHeader.setPlanes((/*ushort*/short)planes);
            dibHeader.setBitCount((/*ushort*/short)bitsPerPixel);
            dibHeader.setXPelsPerMeter(pixelsPerMeterX);
            dibHeader.setYPelsPerMeter(pixelsPerMeterY);
            dibHeader.write(writer);

            // Write the color table.
            writer.writeBytes(buildColorTableData(colorTable));

            writer.flush();

            // Convert scanData into DIB bytes and write.

            // DDB scan width is 16 bit aligned, but DIB scan width is 32 bit aligned.
            // This finds the width of the DIB scan line in bytes.
            int dibWidthBytes = MathUtil.roundUp(ddbWidthData, 4);

            // This is how many zero bytes we need to add at the end of each DIB scan line.
            // I guess it could be 2 or 0.
            int padCount = dibWidthBytes - ddbWidthData;

            int dibIdx = (int)(bmpHeader.OffBits & 0xFFFFFFFFL);
            // Go through every scan line in DDB, but in reverse order because DDB is top-down, but DIB is bottom-up.
            for (int ddbY = height - 1; ddbY >= 0; ddbY--)
            {
                // Go through every byte in the DDB scan line and copy to DIB.
                for (int x = 0; x < ddbWidthData; x++)
                {
                    int ddbIdx = ddbY * ddbWidthData + x;
                    bmp[dibIdx] = ddbData[ddbIdx];
                    dibIdx++;
                }

                // Add pad bytes to DIB scan lines.
                for (int pad = 0; pad < padCount; pad++)
                {
                    bmp[dibIdx] = (byte) 0;
                    dibIdx++;
                }
            }
        }
        finally { if (stream != null) stream.close(); }

        return bmp;
    }

    public static ImageSizeCore getBmpSize(Stream imageStream) throws Exception
    {
        imageStream.setPosition(BitmapFileHeader.STRUCTURE_SIZE);
        BinaryReader reader = new BinaryReader(imageStream);
        BitmapInfoHeader infoHeader = DibHeader.readAsBitmapInfoHeader(reader);

        ImageSizeCore size = ImageSizeCore.createWithResolution(
            infoHeader.getWidth(),
            Math.abs(infoHeader.getHeight()),
            ppmToDpi(infoHeader.getXPelsPerMeter()),
            ppmToDpi(infoHeader.getYPelsPerMeter()));

        return size;
    }

    /// <summary>
    /// Returns the size of a GIF image in twips. SPEED This method does not use the Image object to get the size.
    /// </summary>
    public static ImageSizeCore getGifSize(byte[] imageBytes) throws Exception
    {
        Debug.assertTrue(isGif(imageBytes));

        MemoryStream stream = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
    	{
            return getGifSize(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    public static ImageSizeCore getGifSize(Stream imageStream) throws Exception
    {
        imageStream.setPosition(6);
        BinaryReader reader = new BinaryReader(imageStream);
        /*ushort*/short width = reader.readUInt16();
        /*ushort*/short height = reader.readUInt16();

        // RK We need to check the GIF specification, if resolution is specified, the we should read it.
        return ImageSizeCore.createWithResolution((width & 0xFFFF), (height & 0xFFFF), 0.0);
    }

    /// <summary>
    /// Returns the size of a WebP image in twips. SPEED This method does not use the Image object to get the size.
    /// </summary>
    public static ImageSizeCore getWebPSize(Stream imageStream) throws Exception
    {
        final int HEADER_SIZE = 30;

        int width = 0;
        int height = 0;

        BinaryReader reader = new BinaryReader(imageStream);
        byte[] data = reader.readBytes(HEADER_SIZE);
        String imageFormat = Encoding.getASCII().getString(data, 12, 4);

        switch (gStringSwitchMap.of(imageFormat))
        {
            case /*"VP8 "*/0:
            {
                width = get16BitData(data, 26) & 0x3FFF;
                height = get16BitData(data, 28) & 0x3FFF;
                break;
            }
            case /*"VP8X"*/1:
            {
                width = 1 + get24BitData(data, 24);
                height = 1 + get24BitData(data, 27);
                break;
            }
            case /*"VP8L"*/2:
            {
                int firstBytes = get16BitData(data, 21);
                width = 1 + (firstBytes & 0x3FFF);
                int lastTwoDigits = (firstBytes & 0xC000) >> 14;
                height = 1 + ((get16BitData(data, 23) & 0xFFF) << 2 | lastTwoDigits);
                break;
            }
            default:
            {
                // The WebP file is broken and contains no size information or has invalid encoding format.
                return ImageSizeCore.createEmpty();
            }
        }

        imageStream.setPosition(imageStream.getPosition() - HEADER_SIZE);

        return ImageSizeCore.createWithResolution(width, height, ImageConstants.STANDARD_RESOLUTION);
    }

    private static int get16BitData(byte[] data, int index)
    {
        return ((data[index] & 0xFF) & 0xFF) | (((data[index + 1] & 0xFF) & 0xFF) << 8);
    }

    private static int get24BitData(byte[] data, int index)
    {
        return get16BitData(data, index) | (((data[index + 2] & 0xFF) & 0xFF) << 16);
    }

    public static ImageSizeCore getPngSize(Stream stream) throws Exception
    {
        BigEndianBinaryReader reader = new BigEndianBinaryReader(stream);

        final int SIGNATURE_SIZE = 8;
        stream.setPosition(SIGNATURE_SIZE);

        int width = 0;
        int height = 0;
        double hRes = 0.0;
        double vRes = 0.0;

        // RK Need this flag to break the loop from within the switch.
        boolean isExit = false;

        // Indicates that the size of image was already obtained.
        boolean isSizeSet = false;

        while (!isExit && (stream.getPosition() < stream.getLength()))
        {
            /*uint*/int chunkLength = reader.readUInt32();
            String charType = new String(reader.readByteChars(4));

            switch (gStringSwitchMap.of(charType))
            {
                case /*"IHDR"*/3:
                {
                    // Width:              4 bytes
                    // Height:             4 bytes
                    // Bit depth:          1 byte
                    // Color type:         1 byte
                    // Compression method: 1 byte
                    // Filter method:      1 byte
                    // Interlace method:   1 byte

                    width = reader.readInt32();
                    height = reader.readInt32();

                    isSizeSet = true;

                    // Seek back to the beginning of the chunk data.
                    stream.seek(-(4 + 4), SeekOrigin.CURRENT);

                    break;
                }
                case /*"pHYs"*/4:
                {
                    // Pixels per unit, X axis  4 bytes (PNG unsigned integer)
                    // Pixels per unit, Y axis  4 bytes (PNG unsigned integer)
                    // Unit specifier            1 byte

                    // WORDSNET-7590 The problem occurred because the images in the document have
                    // a very large value of pixelsPerUnitX and pixelsPerUnitX in pHYs.
                    // Their values equal maximum value of unsigned int, i.e. FF FF FF FF,
                    // this value is greater than maximum value of signed integer,
                    // that is why upon casting value becomes incorrect and resolution is calculated improperly.
                    long pixelsPerUnitX = (long)(reader.readUInt32() & 0xFFFFFFFFL);
                    long pixelsPerUnitY = (long)(reader.readUInt32() & 0xFFFFFFFFL);
                    short unitSpecifier = (short)(reader.readByte() & 0xFF);

                    // Unit is the metre.
                    if (unitSpecifier == 1)
                    {
                        hRes = ppmToDpi(pixelsPerUnitX);
                        vRes = ppmToDpi(pixelsPerUnitY);
                    }

                    isExit = true;
                    break;
                }
                case /*"IEND"*/5:
                    // RK In one document PNG had extra 4 bytes at the end garbage,
                    // so we have to exit by the end tag, not by reaching the end of the file.
                    isExit = true;
                    break;
                default:
                    // Do nothing.
                    break;
            }

            // Advance by the length of the chunk + CRC.
            /*uint*/int shift = (/*uint*/int)((chunkLength & 0xFFFFFFFFL) + 4);

            // WORDSNET-17482
            // If the minimum data for calculating the size were successfully obtained,
            // the corrupted chunk length may be ignored, since this is not of interest for this method.
            // WORDSNET-18931
            // 8 bytes should be considered as the data containing the chunk length and chunk type
            // for the next step of the loop.
            if (isSizeSet && (stream.getPosition() + (shift & 0xFFFFFFFFL) + 8 >= stream.getLength()))
                break;

            stream.seek((shift & 0xFFFFFFFFL), SeekOrigin.CURRENT);
        }

        return ImageSizeCore.createWithResolution(width, height, hRes, vRes);
    }

    /// <summary>
    /// Gets orientation data from the image bytes.
    /// </summary>
    /// <param name="imageBytes">Image data bytes.</param>
    /// <returns>Obtained tiff orientation data.</returns>
    public static /*ExifOrientation*/int getJpegOrientation(byte[] imageBytes) throws Exception
    {
        Debug.assertTrue(isJpeg(imageBytes));

        MemoryStream stream = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
    	{
            return getJpegOrientation(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Gets orientation data from the image bytes.
    /// </summary>
    /// <param name="imageStream">Image data stream.</param>
    /// <returns>Obtained tiff orientation data.</returns>
    public static /*ExifOrientation*/int getJpegOrientation(Stream imageStream) throws Exception
    {
        Debug.assertTrue(imageStream.canSeek()); // Method logic changes current stream position.
        /*ExifOrientation*/int orientation = ExifOrientation.HORIZONTAL;

        try
        {
            BigEndianBinaryReader reader = new BigEndianBinaryReader(imageStream);
            JpegReader jpegReader = new JpegReader(reader);
            jpegReader.goToNextMarker(); // Skip SOI (Start of Image Marker).

            // Process segments of the JPEG file.
            // DS: About 65% of checked files have no orientation data.
            // So, safe marker reading was introduced to avoid "EndOfStreamException" exception in normal processing.
            while (jpegReader.goToNextMarker())
            {
                if (jpegReader.isEoiMarker())
                    break;

                /*ushort*/short segmentLength = reader.readUInt16();
                long nextMarkerPosition = imageStream.getPosition() + (segmentLength & 0xFFFF) - 2;

                // EXIF segment.
                if ((jpegReader.getCurrentMarker() == JpegReader.MARKER_EXIF) && isExifData(reader))
                {
                    TiffDataReader dataReader = new TiffDataReader(reader);
                    orientation = dataReader.getOrientation();
                    break;
                }

                // Proceed to the next segment.
                imageStream.setPosition(nextMarkerPosition);
            }
        }
        catch (EOFException e)
        {
            // Ignore end of stream i.e. may be invalid file. And return default value.
        }

        return orientation;
    }

    public static boolean isCmykOrYCCK(byte[] imageBytes) throws Exception
    {
        MemoryStream ms = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
    	{
            return isCmykOrYCCK(ms);
    	}
        finally { if (ms != null) ms.close(); }
    }

    /// <summary>
    /// Checks whether the image is CMYK or YCCK image. Works only with JPEG.
    /// </summary>
    public static boolean isCmykOrYCCK(Stream imageStream) throws Exception
    {
        if (getImageType(imageStream) != FileFormat.JPEG)
            return false;

        long savePos = imageStream.getPosition();

        boolean hasApp14Marker = false;
        // ap12 value of APP14 marker must be 0, 1 or 2. 8 means the value was not read.
        byte ap12Value = (byte) 8;
        // Components value of SOF must be 1, 3 or 4. 8 means the value was not read.
        byte components = (byte) 8;

        try
        {
            BigEndianBinaryReader reader = new BigEndianBinaryReader(imageStream);
            JpegReader jpegReader = new JpegReader(reader);

            // Skip SOI.
            jpegReader.goToNextMarker();

            jpegReader.goToNextMarker();
            // Search for SOF.
            // RK Values 0xFFC0 to 0xFFCF are all SOF (Start of Frame Markers) except 0xFFC4 and 0xFFCC.
            while (((jpegReader.getCurrentMarker() & 0xFFFF) & 0xFFF0) != 0xFFC0 ||
                   (jpegReader.getCurrentMarker() & 0xFFFF) == 0xFFC4 ||
                   (jpegReader.getCurrentMarker() & 0xFFFF) == 0xFFCC)
            {
                switch (jpegReader.getCurrentMarker() & 0xFFFF)
                {
                    case 0xFFEE: // APP14 marker.
                        /*ushort*/short app14FieldLength = reader.readUInt16();
                        // The first five bytes must be of the segment coded as X'41', X'64', X'6F', X'62', X'65', X'00'
                        // (the zero - terminated string "Adobe", according to Rec.ITU - T T.50 or ISO / IEC 646 coding)
                        String adobe = Encoding.getUTF8().getString(reader.readBytes(5));

                        // Shift to ap12.
                        reader.readBytes(5);

                        // Read the ap12 value.
                        ap12Value = reader.readByte();
                        hasApp14Marker = "Adobe".equals(adobe);

                        imageStream.seek((app14FieldLength & 0xFFFF) - 13, SeekOrigin.CURRENT);
                        jpegReader.goToNextMarker();
                        continue;
                    default:
                        break;
                }

                /*ushort*/short fieldLength = reader.readUInt16();
                imageStream.seek((fieldLength & 0xFFFF) - 2, SeekOrigin.CURRENT);

                jpegReader.goToNextMarker();
            }

            // WORDSNET-24845 Some images detected as PixelFormat.PixelFormat32bppCMYK in GDI.
            // But this value not exist in System.Drawing Enum and PixelFormat property is defined as 8207.
            // There are no exceptions with such Jpeg images processing in .Net Framework,
            // but such pictures are damaged in SkiaSharp. This method should recognize such pictures as CMYK(marker == 0xFFC2).
            // This is not critical when working with the Net Framework, but must be handled when working with SkiaSharp.
            if ((jpegReader.getCurrentMarker() & 0xFFFF) == 0xFFC0 || (jpegReader.getCurrentMarker() & 0xFFFF) == 0xFFC1 || (jpegReader.getCurrentMarker() & 0xFFFF) == 0xFFC2)
            {
                // Skip field length.
                reader.readUInt16();
                // Skip the sample precision field.
                reader.readByte();
                // Skip image height from a frame segment.
                reader.readUInt16();
                // Skip image width from a frame segment.
                reader.readUInt16();
                // Read number of components.
                components = reader.readByte();
            }
        }
        catch(Exception e)
        {
            // In case of reading problems silent exception and assume the image is not CMYK or YCCK.
            return false;
        }
        finally
        {
            imageStream.setPosition(savePos);
        }

        // If there are 4 components it is likely to be CMYK or YCCK image.
        // Another check. If there is APP14 marker and AP12 value of this marker is 0(CMYK) or 2(YCCK)
        if (((components == 4) && !hasApp14Marker) ||
        ((components == 4) && hasApp14Marker && (ap12Value == 0 || ap12Value == 2)))
            return true;

        // Otherwise assume the image is not CMYK or YCCK and can be used as is in PDF.
        return false;
    }

    public static ImageSizeCore getJpegSize(Stream imageStream) throws Exception
    {
        try
        {
            boolean sizeFound = false;
            int height = 0;
            int width = 0;
            /*PointF*/long resolution = msPointF.Empty;

            BigEndianBinaryReader reader = new BigEndianBinaryReader(imageStream);
            JpegReader jpegReader = new JpegReader(reader);

            // Skip SOI (Start of Image Marker).
            jpegReader.goToNextMarker();

            // Process segments of the JPEG file.
            jpegReader.goToNextMarker();
            while (!sizeFound)
            {
                /*ushort*/short segmentLength = reader.readUInt16();
                long nextMarkerPosition = imageStream.getPosition() + (segmentLength & 0xFFFF) - 2;

                // RK Values 0xFFC0 to 0xFFCF are all SOF (Start of Frame Markers) except 0xFFC4 and 0xFFCC.
                if (((jpegReader.getCurrentMarker() & 0xFFFF) & 0xFFF0) == 0xFFC0 &&
                    (jpegReader.getCurrentMarker() & 0xFFFF) != 0xFFC4 &&
                    (jpegReader.getCurrentMarker() & 0xFFFF) != 0xFFCC)
                {
                    // Skip the sample precision field.
                    reader.readByte();
                    // Read image size from a frame segment.
                    height = (reader.readUInt16() & 0xFFFF);
                    width = (reader.readUInt16() & 0xFFFF);
                    sizeFound = true;
                }
                // APP0 Marker - possible JFIF segment.
                else if ((jpegReader.getCurrentMarker() & 0xFFFF) == 0xFFE0)
                {
                    resolution = readResolutionJfif(reader, resolution);
                }
                // APP1 Marker - possible EXIF segment.
                else if ((jpegReader.getCurrentMarker() & 0xFFFF) == 0xFFE1)
                {
                    resolution = readResolutionExif(reader, resolution);
                }
                // APP13 Photoshop image resource marker segment. (Saved as: IRB, 8BIM, IPTC)
                else if (((jpegReader.getCurrentMarker() & 0xFFFF) == 0xFFED) && (nextMarkerPosition >= 0x30))
                {
                    resolution = PhotoshopMetadataReader.readResolution(reader, resolution);
                }

                // Proceed to the next segment.
                imageStream.setPosition(nextMarkerPosition);
                jpegReader.goToNextMarker();
            }

            return ImageSizeCore.createWithResolution(width, height, msPointF.getX(resolution), msPointF.getY(resolution));
        }
        catch (EOFException e)
        {
            // WORDSNET-10253 If we reach the end of file while looking for its size and resolution, it means that
            // the JPEG file is broken and contains no size information or has invalid segment structure.
            return ImageSizeCore.createEmpty();
        }
    }

    /// <summary>
    /// Converts image data to PNG format.
    /// </summary>
    public static byte[] convertToPng(MemoryStream imageStream) throws Exception
    {
        // alexnosk: in .NET Standard the underlying object of BitmapPal is SKBitmap,
        // when create it from stream the stream is disposed. To avoid ObjectDisposedException,
        // check Is16BppRgbTiff before creating a BitmapPal.
        boolean is16BppRgbTiff = is16BppRgbTiff(imageStream);

        BitmapPal bitmap = new BitmapPal(imageStream);
        try /*JAVA: was using*/
        {
            MemoryStream stream = new MemoryStream();
            try /*JAVA: was using*/
            {
                // WORDSNET-3898 The system library method Image.Save() incorrectly exports images from the
                // 'Tiff' format with 16-bit per sample RGB color model to the 'Png' format.
                // However, it exports correctly such images to 'Bmp' format.
                if (is16BppRgbTiff)
                {
                    BitmapPal bmpBitmap = convertToBmpFormat(bitmap);
                    try /*JAVA: was using*/
                	{
                        bmpBitmap.save(stream, FileFormat.PNG);
                	}
                    finally { if (bmpBitmap != null) bmpBitmap.dispose(); }
                }
                else
                {
                    bitmap.save(stream, FileFormat.PNG);
                }

                return StreamUtil.copyStreamToByteArray(stream);
            }
            finally { if (stream != null) stream.close(); }
        }
        finally { if (bitmap != null) bitmap.dispose(); }
    }

    /// <summary>
    /// Returns image bytes in 'Png' format.
    /// </summary>
    public static byte[] convertToPng(byte[] imageBytes) throws Exception
    {
        MemoryStream stream = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
    	{
            return convertToPng(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Converts specified bitmap to the 'Bmp' format.
    /// </summary>
    private static BitmapPal convertToBmpFormat(BitmapPal bitmap) throws Exception
    {
        // This memory stream will be disposed by the BitmapPal.
        MemoryStream stream = new MemoryStream();
        bitmap.save(stream, FileFormat.BMP);
        stream.setPosition(0);
        return new BitmapPal(stream);
    }

    /// <summary>
    /// Returns true if the specified image stream is 'Tiff' with 16 bit per sample RGB format.
    /// </summary>
    private static boolean is16BppRgbTiff(MemoryStream imageStream) throws Exception
    {
        boolean is16BppRgb = false;
        long pos = imageStream.getPosition();
        if (isTiff(imageStream))
        {
            imageStream.setPosition(pos);
            TiffDataReader reader = new TiffDataReader(imageStream);
            is16BppRgb = reader.is16BppRgb();
        }

        imageStream.setPosition(pos);

        return is16BppRgb;
    }

    /// <summary>
    /// http://www.martinreddy.net/gfx/2d/JPEG.txt
    /// </summary>
    private static /*PointF*/long readResolutionJfif(BigEndianBinaryReader reader, /*PointF*/long resolution) throws Exception
    {
        final int ID_LENGTH = 5;
        final int VERSION_LENGTH = 2;

        reader.getBaseStream().seek(ID_LENGTH + VERSION_LENGTH, SeekOrigin.CURRENT);

        short units = (short)(reader.readByte() & 0xFF);

        /*ushort*/short destinyX = reader.readUInt16();
        /*ushort*/short destinyY = reader.readUInt16();

        switch (units)
        {
            case 0:
                // No uints, aspect ratio, skip?
                break;
            case 1:
                // Dots per inch.
                resolution = msPointF.ctor(destinyX, destinyY);
                break;
            case 2:
                // Dots per cm.
                final double CM_PER_INCH = 2.54;
                resolution = msPointF.ctor((float)((destinyX & 0xFFFF) * CM_PER_INCH), (float)((destinyY & 0xFFFF) * CM_PER_INCH));
                break;
            default:
                // Bad value, skip.
                break;
        }

        return resolution;
    }

    private static boolean isExifData(BigEndianBinaryReader reader) throws Exception
    {
        // Read EXIF signature.
        String sig = new String(reader.readByteChars(6));
        return msString.startsWith(sig, "Exif", StringComparison.ORDINAL);
    }

    /// <summary>
    /// http://park2.wakwak.com/~tsuruzoh/Computer/Digicams/exif-e.html#ExifData
    /// </summary>
    private static /*PointF*/long readResolutionExif(BigEndianBinaryReader reader, /*PointF*/long resolution) throws Exception
    {
        if (isExifData(reader))
        {
            TiffDataReader dataReader = new TiffDataReader(reader);

            // RK This code is probably by VS. In this case I think tag 0x0128 ResolutionUnit should
            // also be parsed it could specify units per inch or per centimeter.
            // alexnosk: WORDSNET-5142 The problem occurred because resolution read from Exif is 0 dpi, there is no
            // actual value of resolution.
            // So default resolution was used. To resolve the problem check whether values read from Exif are not zeros.
            if (dataReader.getImageXResolution() > 0 && dataReader.getImageYResolution() > 0)
            {
                // Resolution are always stored in inches in our model.
                double imageXResolution = dataReader.getImageXResolution();
                double imageYResolution = dataReader.getImageYResolution();

                // WORDSNET-10356 If resolution units read from 'EXIF' are centimeters,
                // then we should convert it to inches for storing in the model.
                // In addition, we should take into account that relationship between
                // image size and resolution is inversed.
                if (dataReader.getImageResolutionUnit() == TiffResolutionUnitCore.CENTIMETER)
                {
                    imageXResolution = ConvertUtilCore.inchToCm(imageXResolution);
                    imageYResolution = ConvertUtilCore.inchToCm(imageYResolution);
                }

                resolution = msPointF.ctor((float)imageXResolution, (float)imageYResolution);
            }
        }

        return resolution;
    }

    public static ImageSizeCore getTiffSize(Stream imageStream) throws Exception
    {
        TiffDataReader dataReader = new TiffDataReader(imageStream);

        return ImageSizeCore.createWithResolution(
            dataReader.getImageWidth(),
            dataReader.getImageHeight(),
            dataReader.getImageXResolution(),
            dataReader.getImageYResolution());
    }

    public static ImageSizeCore getIcoSize(Stream imageStream, int imageIndex) throws Exception
    {
        BinaryReader reader = new BinaryReader(imageStream);

        int reserved = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(reserved == 0);
        int type = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(type == 1);
        int nrEntries = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(nrEntries > 0);

        if (imageIndex < 0 || imageIndex >= nrEntries)
        {
            throw new IllegalArgumentException("Couldn't extract image with index " + imageIndex +
                                        " from .ico file, because it contains only " + nrEntries + " image(s).");
        }

        for (int i = 0; i < nrEntries; ++i)
        {
            int width = (reader.readByte() & 0xFF);
            int height = (reader.readByte() & 0xFF);
            // Ignore nrColorPalette.
            reader.readByte();
            int reservedByte = (reader.readByte() & 0xFF);
            Debug.assertTrue(reservedByte == 0);
            int nrColorPanes = (reader.readUInt16() & 0xFFFF);
            Debug.assertTrue(nrColorPanes == 0 || nrColorPanes == 1);
            // Ignore bitsPerPixel.
            reader.readUInt16();
            // Ignore sizeInBytes.
            reader.readUInt32();
            // Ignore offset.
            reader.readUInt32();
            if (i == imageIndex)
            {
                return ImageSizeCore.createWithResolution(width, height, ImageConstants.STANDARD_RESOLUTION);
            }
        }

        throw new IllegalStateException("Couldn't extract BMP/PNG image from .ico file.");
    }

    public static ImageSizeCore getMetafileSize(byte[] imageBytes) throws Exception
    {
        return getMetafileSize(new MemoryStream(imageBytes));
    }

    public static ImageSizeCore getMetafileSize(Stream imageStream)
    {
        // FOSS simplified
        /*Size*/long defaultScreenSize = msSize.ctor(1280, 1024);
        final float DEFAULT_RESOLUTION = 96f;

        return ImageSizeCore.createWithResolution(
            msSize.getWidth(defaultScreenSize),
            msSize.getHeight(defaultScreenSize),
            DEFAULT_RESOLUTION);
    }

    public static ImageSizeCore getPictSize(byte[] imageBytes) throws Exception
    {
        Debug.assertTrue(isPict(imageBytes));

        MemoryStream imageStream = new MemoryStream(imageBytes);
        try /*JAVA: was using*/
    	{
            return getPictSize(imageStream);
    	}
        finally { if (imageStream != null) imageStream.close(); }
    }

    public static ImageSizeCore getPictSize(Stream imageStream) throws Exception
    {
        BinaryReader reader = new BinaryReader(imageStream);

        // First goes file length to ushort. Skip.
        reader.readUInt16();

        int top = BitUtil.swapInt16(reader.readInt16());
        int left = BitUtil.swapInt16(reader.readInt16());
        int bottom = BitUtil.swapInt16(reader.readInt16());
        int right = BitUtil.swapInt16(reader.readInt16());
        int widthEmus = ConvertUtilCore.pointToEmu((double)(right - left));//JAVA-added casting
        int heightEmus = ConvertUtilCore.pointToEmu((double)(bottom - top));//JAVA-added casting

        return ImageSizeCore.createWithDimensions(left, top, right, bottom, widthEmus, heightEmus);
    }

    /// <summary>
    /// Puts a placeable WMF header before the image bytes.
    /// Use this method if you know the values that must go into the header.
    /// </summary>
    /// <param name="imageBytes">Must be bytes of a WMF file without a placeable header.</param>
    /// <param name="imageSize">Contains image size info. It is used to create the placeable header.</param>
    public static byte[] prependWmfPlaceableHeader(byte[] imageBytes, ImageSizeCore imageSize) throws Exception
    {
        Debug.assertTrue(isStandardMetafile(imageBytes));

        byte[] header = getWmfPlaceableHeader(imageSize);
        byte[] prepended = new byte[header.length + imageBytes.length];

        System.arraycopy((header), 0, (prepended), 0, (header.length));
        msArray.copy(imageBytes, 0, prepended, header.length, imageBytes.length);

        return prepended;
    }

    /// <summary>
    /// Puts a placeable WMF header before the image bytes.
    /// Use this method if you know the values that must go into the header.
    /// </summary>
    /// <param name="stream">Must be stream of a WMF file without a placeable header.</param>
    /// <param name="imageSize">Contains image size info. It is used to create the placeable header.</param>
    public static byte[] prependWmfPlaceableHeader(Stream stream, ImageSizeCore imageSize) throws Exception
    {
        byte[] header = getWmfPlaceableHeader(imageSize);
        byte[] prepended = new byte[(int)(header.length + stream.getLength())];

        System.arraycopy((header), 0, (prepended), 0, (header.length));
        int read = stream.read(prepended, header.length, (int)stream.getLength());

        Debug.assertTrue(read == stream.getLength());

        return prepended;
    }

    private static byte[] getWmfPlaceableHeader(ImageSizeCore imageSize) throws Exception
    {
        MemoryStream result = new MemoryStream();
        try /*JAVA: was using*/
        {
            BinaryWriter writer = new BinaryWriter(result, Encoding.getUnicode());

            int checksum = 0;

            final int MAGIC_LOW = 0xCDD7;
            final int MAGIC_HIGH = 0x9AC6;
            writer.writeUInt16((/*ushort*/short)MAGIC_LOW); //magic number
            checksum ^= MAGIC_LOW;
            writer.writeUInt16((/*ushort*/short)MAGIC_HIGH);
            checksum ^= MAGIC_HIGH;

            writer.writeInt16((short)0); //handle
            checksum ^= 0;

            writer.writeInt16((short)imageSize.getLeft());
            checksum ^= imageSize.getLeft();

            writer.writeInt16((short)imageSize.getTop());
            checksum ^= imageSize.getTop();

            writer.writeInt16((short)imageSize.getRight());
            checksum ^= imageSize.getRight();

            writer.writeInt16((short)imageSize.getBottom());
            checksum ^= imageSize.getBottom();

            // Lets write horizontal resolution.
            writer.writeInt16((short)imageSize.getHorizontalResolution()); //units per inch
            checksum ^= (int)imageSize.getHorizontalResolution();

            writer.writeInt16((short)0); //reserved
            checksum ^= 0;
            writer.writeInt16((short)0); //reserved
            checksum ^= 0;

            writer.writeUInt16((/*ushort*/short)checksum);

            return result.toArray();
        }
        finally { if (result != null) result.close(); }
    }

    /// <summary>
    /// Returns true if creating a bitmap of this size is likely to be too big for the system.
    /// If a bitmap is too big, .NET can fail with argument exception or out of memory exception.
    /// I've seen a problem where .NET fails to create a bitmap of 29Mpixels. I managed .NET to work with 18Mpixels without
    /// failing on my machine. So let's just set a hardcoded limit for approx 20Mpixels. You can use this method to check
    /// before creating or processing bitmaps.
    /// </summary>
    public static boolean isBitmapTooBigForSystem(int width, int height)
    {
        final int MAX_PIXELS = 20 * 1024 * 1024;
        // WORDSNET-25310 Used long to avoid the loss of senior discharges
        long size = (long)width * height;
        return (size > MAX_PIXELS);
    }

    static double ppmToDpi(long pixelsPerMeter)
    {
        return pixelsPerMeter / ImageConstants.INCHES_PER_METER;
    }

    static int dpiToPpm(double dpi)
    {
        return (int)(dpi * ImageConstants.INCHES_PER_METER);
    }


    /// <summary>
    /// Gets the stream that contains the red-cross image to represent missing pictures.
    /// </summary>
    /// <returns>The stream. Don't forget to close it when finished.</returns>
    public static Stream getNoImageStream() throws Exception
    {
        return new MemoryStream(getNoImageBytes());
    }

    /// <summary>
    /// Gets the bytes of the red-cross image to represent missing pictures.
    /// </summary>
    public static byte[] getNoImageBytes() throws Exception
    {
        // alexnosk: WORDSNET-19721 Cache NoImage bytes to decrease number of resource reading calls and redundant
        // streams creation.
        // Size of cached data is about 1kb, I think it is ok to keep it in memory.
        // double-checked locking pattern.
        if (gNoImageCache == null)
        {
            synchronized (G_NO_IMAGE_CACHE_SYNC_ROOT)
            {
                if (gNoImageCache == null)
                {
                    Stream stream = SystemPal.fetchResourceStream("Aspose.Resources.NoImage.png");
                    try /*JAVA: was using*/
                	{
                        gNoImageCache = StreamUtil.copyStreamToByteArray(stream);
                	}
                    finally { if (stream != null) stream.close(); }
                }
            }
        }

        return gNoImageCache;
    }

    /// <summary>
    /// Returns a value that indicates whether the pixel format for this image contains alpha information.
    /// </summary>
    public static boolean hasAlphaChannel(byte[] imageBytes) throws Exception
    {
        // Platform-specific code is moved to BitmapPal.
        // Ideally we should write optimized low-level code here, something like GetImageSize().
        return BitmapPal.imageHasAlphaChannel(imageBytes);
    }

    /// <summary>
    /// Returns True if the FileFormat represents an image.
    /// </summary>
    public static boolean isImage(/*FileFormat*/int fileFormat)
    {
        return fileFormat == FileFormat.BMP
               || fileFormat == FileFormat.EMF
               || fileFormat == FileFormat.GIF
               || fileFormat == FileFormat.JPEG
               || fileFormat == FileFormat.PNG
               || fileFormat == FileFormat.TIFF
               || fileFormat == FileFormat.WMF
               || fileFormat == FileFormat.PICT
               || fileFormat == FileFormat.ICO
               || fileFormat == FileFormat.WEB_P
               || fileFormat == FileFormat.SVG;
    }

    /// <summary>
    /// Converts resolution from pixels per inch (ppi) to pixels per meter (ppm)
    /// </summary>
    /// <param name="resolution">Resolution in pixels per inch</param>
    /// <returns>Resolution in pixels per meter</returns>
    private static /*uint*/int convertPPItoPPM(double resolution)
    {
        // Defines meter to unit conversion coefficient.
        final double METERS_IN_INCH = 0.0254;

        return (/*uint*/int)Convert.toUInt32(msMath.round(resolution / METERS_IN_INCH));
    }

    /// <summary>
    /// WORDSNET-12001 MONO. Fix for PNG codec skips writing image resolution to PNG data.
    /// Add (if not exist) PNG resolution information to PNG data in a stream.
    /// </summary>
    /// <param name="inputStream">Input stream object with the original PNG data</param>
    /// <param name="outputStream">Output stream object where PNG data and pixel resolution will be written to.</param>
    /// <param name="horizontalResolution">Desired horizontal resolution.</param>
    /// <param name="verticalResolution">Desired horizontal resolution.</param>
    static void savePngResolution(
        Stream inputStream, Stream outputStream, double horizontalResolution, double verticalResolution) throws Exception
    {
        inputStream.setPosition(0);

        BigEndianBinaryReader br = new BigEndianBinaryReader(inputStream);
        BigEndianBinaryWriter bw = new BigEndianBinaryWriter(outputStream);

        // Read/write png signature
        byte[] b = br.readBytes(8);
        bw.writeBytes(b, 0, 8);

        // Flags that pHYs chunk already present or written in data
        boolean pHYsWritten = false;

        // Start chunk read/write cycle
        while (true)
        {
            /*uint*/int len = br.readUInt32();    // Read chunk length
            byte[] cname = br.readBytes(4);         // Read chunk name
            byte[] data = br.readBytes((int)(len & 0xFFFFFFFFL));   // Read chunk data
            /*uint*/int crc = br.readUInt32();    // Read CRC

            String sname = Encoding.getASCII().getString(cname);

            // Check if pixel dimensions chunk already stored in file
            if ("pHYs".equals(sname))
                pHYsWritten = true;

            // According to PNG 1.2 specification pHYs chunk should be before IDAT chunk
            // Png file may contain several IDAT chunks.
            // So if IDAT chunk in process and pHYs is not already present/written write them
            if ("IDAT".equals(sname) && !pHYsWritten)
            {
                // always 9 bytes length
                bw.writeUInt32(9);

                MemoryStream memoryStream = new MemoryStream();
                try /*JAVA: was using*/
                {
                    BigEndianBinaryWriter phys_bw = new BigEndianBinaryWriter(memoryStream);

                    // pHYs chunk signature
                    phys_bw.writeBytes(new byte[] { (byte)'p', (byte)'H', (byte)'Y', (byte)'s' }, 0, 4);
                    phys_bw.writeUInt32(convertPPItoPPM(horizontalResolution));
                    phys_bw.writeUInt32(convertPPItoPPM(verticalResolution));
                    // Meter unit
                    phys_bw.writeByte((byte) 1);
                    phys_bw.flush();

                    byte[] buffer = memoryStream.getBuffer();
                    bw.writeBytes(buffer, 0, (int)memoryStream.getLength());

                    // Write CRC
                    bw.writeUInt32(Crc32.computeCRC(buffer, 0, (int)memoryStream.getLength()));
                }
                finally { if (memoryStream != null) memoryStream.close(); }

                pHYsWritten = true;
            }

            // Copy chunk that has been recently read into output stream
            bw.writeUInt32(len);
            bw.writeBytes(cname, 0, 4);
            bw.writeBytes(data, 0, (int)(len & 0xFFFFFFFFL));
            bw.writeUInt32(crc);

            // IEND is strictly last chunk in file
            if ("IEND".equals(sname))
                break;
        }

        bw.flush();
    }

    /// <summary>
    /// Checks if given byte array can be xml.
    /// </summary>
    private static boolean canBeXml(Stream stream) throws Exception
    {
        // Xml can starts with space/tab/linefeed.
        // 200 first bytes should be enough for common xml.
        // Valid xml must have the first significant char '<', no matter '<?' or '<svg_or_anyxmltag'.
        // Another point is that false positive is acceptable, and false negative is not.
        // If check individual chars, not the whole string, we don't care about byte order and UTF-8/16/32.

        final int MAX_LENGTH = 200;
        int dataLength = (stream.getLength() > MAX_LENGTH) ? MAX_LENGTH : (int)stream.getLength();
        byte[] data = new byte[200];
        StreamUtil.read(stream, data, 0, dataLength);
        stream.setPosition(0);

        for (int i = 0; i < dataLength; i++)
        {
            byte tmp = data[i];

            if (isIgnoredValue(tmp))
                continue;

            return ((tmp & 0xFF) == '<');
        }

        return false;
    }

    /// <summary>
    /// Returns true if given value should be ignored in xml checking;
    /// Tab, line feed, space, nil, byte order marks are ignored.
    /// </summary>
    private static boolean isIgnoredValue(byte value)
    {
        byte[] ignored = new byte[10];

        // Different byte order marks.
        ignored[0] = (byte) 0xFE;
        ignored[1] = (byte) 0xFF;
        ignored[2] = (byte) 0xEF;
        ignored[3] = (byte) 0xBB;
        ignored[4] = (byte) 0xBF;

        // Insignificant symbols and nil.
        ignored[5] = (byte) 0x00;
        ignored[6] = (byte)'\r';
        ignored[7] = (byte)'\n';
        ignored[8] = (byte)'\t';
        ignored[9] = (byte)' ';

        for (int i = 0; i < ignored.length; i++)
        {
            if (ignored[i] == value)
                return true;
        }

        return false;
    }

    /// <summary>
    /// Checks whether the specified image is colored.
    /// </summary>
    /// <returns>True if image considered to be colored.</returns>
    static boolean isImageColored(byte[] imageBytes) throws Exception
    {
        BitmapPal bitmap = new BitmapPal(imageBytes);
        try /*JAVA: was using*/
        {
            BitmapPal bitmapArgb = bitmap.convertTo32BppArgb();
            try /*JAVA: was using*/
            {
                BitmapDataPal bitmapData = bitmapArgb.lockBits();
                //Java ArgbData returns Buffer insted of byte[]
                int pixelCount = bitmapData.getArgbDataLength() / 4;

                for (int i = 0; i < pixelCount; i++)
                {
                    byte r = bitmapData.getR(i);
                    byte g = bitmapData.getG(i);
                    byte b = bitmapData.getB(i);

                    int rg = Math.abs((r & 0xFF) - (g & 0xFF));
                    int gb = Math.abs((g & 0xFF) - (b & 0xFF));
                    int br = Math.abs((b & 0xFF) - (r & 0xFF));

                    int colorDiff = Math.max(rg, Math.max(gb, br));
                    if (colorDiff > DrColorUtil.COLOR_DIFFERENCE_THRESHOLD)
                    {
                        bitmapData.unlockBits();
                        return true;
                    }
                }

                bitmapData.unlockBits();
            }
            finally { if (bitmapArgb != null) bitmapArgb.dispose(); }
        }
        finally { if (bitmap != null) bitmap.dispose(); }

        return false;
    }

    private static /*final*/ Object G_NO_IMAGE_CACHE_SYNC_ROOT = new Object();
    private static volatile byte[] gNoImageCache;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"VP8 ",
		"VP8X",
		"VP8L",
		"IHDR",
		"pHYs",
		"IEND"
	);

}

