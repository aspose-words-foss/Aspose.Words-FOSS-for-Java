// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2009 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;
import java.io.EOFException;


/// <summary>
/// Class is used to read data from TIFF image file directory (IFD).
/// It helps to read TIFF resolution and check TIFF conformance.
/// </summary>
public class TiffDataReader
{
    public TiffDataReader(byte[] imageBytes) throws Exception
    {
    	this(new MemoryStream(imageBytes));
	
    }

    public TiffDataReader(Stream imageStream) throws Exception
    {
    	this(new BigEndianBinaryReader(imageStream));
	
    }

    public TiffDataReader(BigEndianBinaryReader reader) throws Exception
    {
        mTiffDataOffset = reader.getBaseStream().getPosition();
        readTiffFields(reader);
    }

    private void readTiffFields(BigEndianBinaryReader reader) throws Exception
    {
        // First two bytes of tiff file indicates byte order that is used within a file.
        short byteOrder = reader.readInt16();

        // If it is 0x4949 little-endian is used, if 0x4d4d - big-endian.
        boolean isBigEndian = (byteOrder == 0x4d4d);

        // Next two bytes always = 42, that further identifies the file as a TIFF file.
        /*ushort*/short tiffIdentifier = readTiffInt16(reader, isBigEndian);

        // If this value is not 42, then file is invalid and we will stop parsing.
        if (tiffIdentifier != 42)
            return;

        // Next 4 bytes are offset of the first image file directory (IFD).
        /*uint*/int ifdOffset = readTiffInt32(reader, isBigEndian);

        // Move reader to the first IFD.
        reader.getBaseStream().setPosition(mTiffDataOffset + (ifdOffset & 0xFFFFFFFFL));

        // Get number of TIFF tags within IFD.
        // This is the first 2 bytes in IFD.
        /*ushort*/short tagsCount = readTiffInt16(reader, isBigEndian);

        // Every tag takes up exactly 12 bytes and looks like this.
        //  Offset   |  Datatype          |  Value 
        //  0        |  Word              |  Tag identifying code 
        //  2        |  Word              |  Datatype of tag data 
        //  4        |  Unsigned Long     |  Number of values 
        //  8        |  x * Tag data      |  Tag data
        //           |  datatype          |
        //           |  or Unsigned Long  |
        //           |  offset            | 
        // http://www.awaresystems.be/imaging/tiff/faq.html

        long currentOffset = reader.getBaseStream().getPosition();
        // Read tag by tag.
        for (int tagIdx = 0; tagIdx < (tagsCount & 0xFFFF); tagIdx++)
        {
            // Move to the next tag.
            reader.getBaseStream().setPosition(currentOffset);
            currentOffset += 12;

            // Read tag identifier.
            /*ushort*/short tagType = readTiffInt16(reader, isBigEndian);

            // Read type of tag value.
            /*ushort*/short tagDataType = readTiffInt16(reader, isBigEndian);

            // Next 4 bytes is number of values.
            /*uint*/int numberOfValues = readTiffInt32(reader, isBigEndian);

            // WORDSNET-20914 Skip empty tag reading.
            if (numberOfValues == 0)
                continue;

            // Determine if value of the field fits 4 bytes.
            // If not, value of the field is offset to the position where the actual value is stored.
            if (((tagDataType == 1 || tagDataType == 2) && (numberOfValues & 0xFFFFFFFFL) > 4) ||
                ((tagDataType == 3) && (numberOfValues & 0xFFFFFFFFL) > 2) ||
                ((tagDataType == 4) && (numberOfValues & 0xFFFFFFFFL) > 1) ||
                (tagDataType == 5))
            {
                long offset = mTiffDataOffset + (readTiffInt32(reader, isBigEndian) & 0xFFFFFFFFL);

                // WORDSNET-26291
                // In case of invalid offset just stop reading without the exception.
                // It'd be better to give a warning here: "Bad TIFF image file directory found."
                // But currently there's no warning service available at this level.
                if (offset >= reader.getBaseStream().getLength())
                    return;

                reader.getBaseStream().setPosition(offset);
            }

            readTag(reader, isBigEndian, tagType, tagDataType, numberOfValues);
        }
    }

    private void readTag(BigEndianBinaryReader reader, boolean isBigEndian, /*ushort*/short tagType, /*ushort*/short tagDataType, /*uint*/int numberOfValues) throws Exception
    {
        switch (tagType & 0xFFFF)
        {
            // Read tags, which are needed to determine size and resolution of the TIFF image.
            case 0x0100:
                mWidth = tagDataType == 3 ? readTiffInt16(reader, isBigEndian) : readTiffInt32(reader, isBigEndian);
                break;
            case 0x0101:
                mHeight = tagDataType == 3 ? readTiffInt16(reader, isBigEndian) : readTiffInt32(reader, isBigEndian);
                break;
            case 0x011A:
                mXResolution = readTiffRational(reader, isBigEndian);
                break;
            case 0x011B:
                mYResolution = readTiffRational(reader, isBigEndian);
                break;
            // Read tags, which are needed to check TIFF image conformance.
            case 0x0106:
                mPhotometricInterpretation = (/*TiffPhotometricInterpretationCore*/int)(readTiffInt16(reader, isBigEndian) & 0xFFFF);
                break;
            case 0x0128:
                mResolutionUnit = (/*TiffResolutionUnitCore*/int)(readTiffInt16(reader, isBigEndian) & 0xFFFF);
                break;
            case 0x0103:
                mCompression = (/*TiffCompressionCore*/int)(readTiffInt16(reader, isBigEndian) & 0xFFFF);
                break;
            case 0x0102:
                mBitsPerSample = readTiffInt16Array(reader, isBigEndian, numberOfValues);
                break;
            case 0x011C:
                mPlanarConfiguration = (/*TiffPlanarConfigurationCore*/int)(readTiffInt16(reader, isBigEndian) & 0xFFFF);
                break;
            case 0x0115:
                mSamplesPerPixel = readTiffInt16(reader, isBigEndian);
                break;
            case 0x014C:
                mInkSet = (/*TiffInkSetCore*/int)(readTiffInt16(reader, isBigEndian) & 0xFFFF);
                break;
            case 0x014E:
                mNumberOfInks = readTiffInt16(reader, isBigEndian);
                break;
            // Orientation.
            case 0x0112:
                mOrientation = readTiffInt16(reader, isBigEndian);
                break;
            default:
                // Ignore unknown tags.
                break;
        }
    }

    public static boolean isTiff(byte[] data) throws Exception
    {
        Debug.assertTrue(data != null);

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
    	{
            return isTiff(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Documentation for the format is in Aspose.Words\Doc.
    /// </summary>
    public static boolean isTiff(Stream stream) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream);
        try
        {
            // The byte order used within the file. We can use it to detect if this is TIFF or not.
            int byteOrder = reader.readInt16();
            return (byteOrder == 0x4949) || (byteOrder == 0x4d4d);
        }
        catch (EOFException e)
        {
            // The input stream is too short.
            return false;
        }
    }

    /// <summary>
    /// Returns true, if Tiff image format is 16-bit per sample RGB. 
    /// </summary>
    public boolean is16BppRgb()
    {
        return (mPhotometricInterpretation == TiffPhotometricInterpretationCore.RGB) &&
               (mBitsPerSample.length == 3) &&
               (mBitsPerSample[0] == 16) &&
               (mBitsPerSample[1] == 16) &&
               (mBitsPerSample[2] == 16);
    }

    private static /*ushort*/short readTiffInt16(BigEndianBinaryReader reader, boolean isBigEndian) throws Exception
    {
        /*ushort*/short value = reader.readUInt16();
        if (!isBigEndian)
            value = BitUtil.swapUInt16(value);

        return value;
    }

    private static /*uint*/int readTiffInt32(BigEndianBinaryReader reader, boolean isBigEndian) throws Exception
    {
        /*uint*/int value = reader.readUInt32();
        if (!isBigEndian)
            value = BitUtil.swapUInt32(value);

        return value;
    }

    private static /*ushort*/short[] readTiffInt16Array(BigEndianBinaryReader reader, boolean isBigEndian, /*uint*/int numberOfItems) throws Exception
    {
        /*ushort*/short[] values = new /*ushort*/short[(int)(numberOfItems & 0xFFFFFFFFL)];   // Casting to int to allow autoporting to Java.
        for (int i = 0; i < values.length; i++)
            values[i] = readTiffInt16(reader, isBigEndian);
        return values;
    }

    private static double readTiffRational(BigEndianBinaryReader reader, boolean isBigEndian) throws Exception
    {
        // Two LONGs: the first represents the numerator of a fraction; the second, the denominator.
        /*uint*/int numerator = readTiffInt32(reader, isBigEndian);
        /*uint*/int denominator = readTiffInt32(reader, isBigEndian);
        // WORDSNET-11932 The problem occurred because denominator in the image is zero, so DivideByZeroException exception occurs.
        // Fixed by adding a condition to avoid dividing by zero.
        return denominator != 0 ? (numerator & 0xFFFFFFFFL) / (denominator & 0xFFFFFFFFL) : 0;
    }

    /// <summary>
    /// Returns Tiff image width. If width is 0, returns default value (100).
    /// </summary>
    public int getImageWidth() { return mWidth != 0 ? (int)(mWidth & 0xFFFFFFFFL) : 100; }

    /// <summary>
    /// Returns Tiff image height. If height is 0, returns default value (100).
    /// </summary>
    public int getImageHeight() { return mHeight != 0 ? (int)(mHeight & 0xFFFFFFFFL) : 100; }

    /// <summary>
    /// Returns Tiff image orientation. If value is missing, returns default value (Horizontal).
    /// </summary>
    public /*ExifOrientation*/int getOrientation() { return mOrientation != 0 ? (/*ExifOrientation*/int)(mOrientation & 0xFFFF) : ExifOrientation.HORIZONTAL; }

    /// <summary>
    /// Returns Tiff image X resolution.
    /// </summary>
    public double getImageXResolution() { return (getImageResolutionUnit() == TiffResolutionUnitCore.NONE) ? ImageConstants.STANDARD_RESOLUTION : mXResolution; }

    /// <summary>
    /// Returns Tiff image Y resolution.
    /// </summary>
    public double getImageYResolution() { return (getImageResolutionUnit() == TiffResolutionUnitCore.NONE) ? ImageConstants.STANDARD_RESOLUTION : mYResolution; }

    /// <summary>
    /// Returns Tiff photometric tag value
    /// </summary>
    public /*TiffPhotometricInterpretationCore*/int getPhotometricInterpretation() { return mPhotometricInterpretation; }

    /// <summary>
    /// Returns Tiff compression tag value
    /// </summary>
    public /*TiffCompressionCore*/int getCompression() { return mCompression; }

    /// <summary>
    /// Returns true if TIFF image conforms XPS specification and can be inserted into XPS document as is.
    /// </summary>
    public boolean isConformXpsSpecification()
    {
        if (mResolutionUnit != TiffResolutionUnitCore.NONE &&
            mResolutionUnit != TiffResolutionUnitCore.INCH &&
            mResolutionUnit != TiffResolutionUnitCore.CENTIMETER)
            return false;

        switch (getPhotometricInterpretation())
        {
            case TiffPhotometricInterpretationCore.BLACK_IS_ZERO:
            case TiffPhotometricInterpretationCore.WHITE_IS_ZERO:
                return (isValidBilevelTiff() || isValidGrayscaleTiff());
            case TiffPhotometricInterpretationCore.RGB_PALETTE:
                return isValidPaletteColorTiff();
            case TiffPhotometricInterpretationCore.RGB:
                return isValidRgbTiff();
            case TiffPhotometricInterpretationCore.CMYK:
                return isValidCmykTiff();
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns Tiff image resolution unit.
    /// </summary>
    /*TiffResolutionUnitCore*/int getImageResolutionUnit() { return mResolutionUnit; }

    /// <summary>
    /// Returns true if image is valid Bilevel Tiff
    /// </summary>
    private boolean isValidBilevelTiff()
    {
        // If BitsPerSample is not defined, i.e is 0, this is Bilevel image.
        return mBitsPerSample[0] == 0 && isCompressionAllowBiLevel();
    }

    /// <summary>
    /// Returns true if image is valid Grayscale Tiff
    /// </summary>
    private boolean isValidGrayscaleTiff()
    {
        // In case of Grayscale image, BitsPerSample (258) should be 4, 8 or 16.
        boolean isValidBitsPerSample = mBitsPerSample[0] == 4 || mBitsPerSample[0] == 8 || mBitsPerSample[0] == 16;
        return isValidBitsPerSample && isCompressionAllowGrayscale();
    }

    /// <summary>
    /// Returns true if image is valid Palette Color Tiff
    /// </summary>
    private boolean isValidPaletteColorTiff()
    {
        // In case of Palette color image, BitsPerSample (258) should be 1, 4 or 8.
        boolean isValidBitsPerSample = mBitsPerSample[0] == 1 || mBitsPerSample[0] == 4 || mBitsPerSample[0] == 8;
        return isValidBitsPerSample && isCompressionAllowPalette();
    }

    /// <summary>
    /// Returns true if image is valid Rgb Tiff
    /// </summary>
    private boolean isValidRgbTiff()
    {
        // PlanarConfiguration (284) should be 1(Chunky).
        if (mPlanarConfiguration != TiffPlanarConfigurationCore.CHUNKY)
            return false;

        if (!isCompressionAllowRgb())
            return false;

        // In case of RGB image, BitsPerSample (258) should be an array with length 3 or 4.
        // If ExtraSamples (338) is 0 - BitsPerSample should be [8,8,8] or [16,16,16], SamplesPerPixel (277) should be 3.
        // if ExtraSamples is 1 or 2 - BitsPerSample should be [8,8,8,8] or [16,16,16,16], SamplesPerPixel (277) should be 4.
        return isValidRgbCmykBitsPerSample();
    }

    /// <summary>
    /// Returns true if image is valid CMYK Tiff
    /// </summary>
    private boolean isValidCmykTiff()
    {
        // PlanarConfiguration (284) should be 1(Chunky).
        if (mPlanarConfiguration != TiffPlanarConfigurationCore.CHUNKY)
            return false;

        if (!isCompressionAllowCmyk())
            return false;

        if (!isValidCmykInks())
            return false;

        // If ExtraSamples (338) is 0 - BitsPerSample should be [8,8,8,8] or [16,16,16,16], SamplesPerPixel (277) should be 4.
        // if ExtraSamples is 1 or 2 - BitsPerSample should be [8,8,8,8,8] or [16,16,16,16,16], SamplesPerPixel (277) should be 5.
        return isValidRgbCmykBitsPerSample();
    }

    private boolean isValidCmykInks()
    {
        // InkSet (332) should be 1(Cmyk).
        // NumberOfInks (334) should be 4.
        return mInkSet == TiffInkSetCore.CMYK &&
               mNumberOfInks == DEFAULT_NUMBER_OF_INKS;
    }

    /// <summary>
    /// Returns true if all items in bitsPerSample array are equal and is 8 or 16. 
    /// This option has no matter when there is only one item in the BitsPerSample array.
    /// It is used only to check Rgb and Cmyk TIFFs.
    /// </summary>
    private boolean isValidRgbCmykBitsPerSample()
    {
        if (mBitsPerSample.length != (mSamplesPerPixel & 0xFFFF))
            return false;

        // Check if all items in bitsPerSample array are equal and is 8 or 16.
        // This option has no matter when there is only one item in the BitsPerSample array.
        boolean isBitsPerSampleValid = mBitsPerSample[0] == 8 || mBitsPerSample[0] == 16;
        for (int i = 0; i < mBitsPerSample.length; i++)
            isBitsPerSampleValid = isBitsPerSampleValid && mBitsPerSample[i] == mBitsPerSample[0];

        return isBitsPerSampleValid;
    }

    private boolean isCompressionAllowBiLevel()
    {
        // In case of Bilevel, compression can be 1(None), 2(Rle), 3(Ccitt3), 4(Ccitt4), 5(Lzw) or 32773(PackBits).
        switch (mCompression)
        {
            case TiffCompressionCore.NONE:
            case TiffCompressionCore.RLE:
            case TiffCompressionCore.CCITT_3:
            case TiffCompressionCore.CCITT_4:
            case TiffCompressionCore.LZW:
            case TiffCompressionCore.PACK_BITS:
                return true;
            default:
                return false;
        }
    }

    private boolean isCompressionAllowGrayscale()
    {

        // In case of Grayscale image, compression can be 1(None), 2(Rle) or 32773(PackBits).
        switch (mCompression)
        {
            case TiffCompressionCore.NONE:
            case TiffCompressionCore.RLE:
            case TiffCompressionCore.PACK_BITS:
                return true;
            default:
                return false;
        }
    }

    private boolean isCompressionAllowPalette()
    {
        // For RgbPalette Compression should be 1(None), 5(Lzw) or 32773(PackBits).
        switch (mCompression)
        {
            case TiffCompressionCore.NONE:
            case TiffCompressionCore.LZW:
            case TiffCompressionCore.PACK_BITS:
                return true;
            default:
                return false;
        }
    }

    private boolean isCompressionAllowRgb()
    {
        // For RGB compression should be 1(None), 5(Lzw), 6(Jpeg) or 32773(PackBits).
        switch (mCompression)
        {
            case TiffCompressionCore.NONE:
            case TiffCompressionCore.LZW:
            case TiffCompressionCore.JPEG:
            case TiffCompressionCore.PACK_BITS:
                return true;
            default:
                return false;
        }
    }

    private boolean isCompressionAllowCmyk()
    {
        // For CMYK compression should be 1(None), 5(Lzw), 6(Jpeg) or 32773(PackBits).
        switch (mCompression)
        {
            case TiffCompressionCore.NONE:
            case TiffCompressionCore.LZW:
            case TiffCompressionCore.JPEG:
            case TiffCompressionCore.PACK_BITS:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// TiffDataReader is also used to read information from EXIF data of JPEG image. 
    /// In this case, Tiff data can be placed not at the beginning of the stream.
    /// </summary>
    private /*final*/ long mTiffDataOffset;

    // Tags, which values are size and resolution of TIFF.
    private /*uint*/int mWidth; // Tag 0x0100 (decimal 256).
    private /*uint*/int mHeight; // Tag 0x0101 (decimal 257).
    private /*ushort*/short mOrientation; // Tag 0x0112 (decimal 274).
    private double mXResolution; // Tag 0x011A (decimal 282).
    private double mYResolution; // Tag 0x011B (decimal 283).

    // Tags, which are needed to check TIFF conformance.
    // If such tags are missed, the default values should be used.
    private /*TiffPhotometricInterpretationCore*/int mPhotometricInterpretation = TiffPhotometricInterpretationCore.UNSPECIFIED; // Tag 0x0106 (decimal 262).
    private /*TiffResolutionUnitCore*/int mResolutionUnit = TiffResolutionUnitCore.INCH; // Tag 0x0128 (decimal 296).
    private /*TiffCompressionCore*/int mCompression = TiffCompressionCore.NONE; // Tag 0x0103 (decimal 259).
    private /*TiffPlanarConfigurationCore*/int mPlanarConfiguration = TiffPlanarConfigurationCore.CHUNKY; // Tag 0x011C (decimal 284).
    private /*TiffInkSetCore*/int mInkSet = TiffInkSetCore.CMYK; // Tag 0x014C (decimal 332).
    private /*ushort*/short mSamplesPerPixel = DEFAULT_SAMPLES_PER_PIXEL; // Tag 0x0115 (decimal 277).
    private /*ushort*/short mNumberOfInks = DEFAULT_NUMBER_OF_INKS; // Tag 0x014E (decimal 334).
    private /*ushort*/short[] mBitsPerSample = new /*ushort*/short[] { (short) 0 }; // Tag 0x0102 (decimal 258).

    // Values used by default.
    private static final /*ushort*/short DEFAULT_SAMPLES_PER_PIXEL = (short) 1;
    private static final /*ushort*/short DEFAULT_NUMBER_OF_INKS = (short) 4;
}

