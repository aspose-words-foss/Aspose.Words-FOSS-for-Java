package com.aspose;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.MemoryToJavaOutputStream;
import com.aspose.ms.java.ImageIO.tiff.decoder.BaselineTIFFTags;
import com.aspose.ms.java.ImageIO.tiff.decoder.TiffMetadata;
import com.aspose.ms.java.ImageIO.tiff.encoder.TIFFImageWriter;
import com.aspose.ms.java.logging.Log;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleJaiTiffWriterPal extends BaseTiffWriterPal
{
    @Override
    public void saveFirstFrame(BitmapPal frame, OutputStream stream, int compression, int binarizationMethod, byte threshold, boolean isMultiframe) throws Exception
    {
        mThreshold = threshold;
        mStream = new MemoryToJavaOutputStream(stream);
        mCompression = compression;
        mBinarizationMethod = binarizationMethod;
        mFirstFrame = convertTo1BppIfRequired(frame, mCompression, binarizationMethod, mThreshold);

        mWriter = new TIFFImageWriter();
        mWriter.setOutput(mStream);

        TiffMetadata frameMetadata = saveMetadata(frame);
        mWriter.write(frameMetadata, mFirstFrame.getNativeImage());
        mImageIdx++;

        //FIX WORDSJAVA-2381. mStream.flush() only if it is not multiframe TIFF
        if(!isMultiframe)
            mStream.flush();
    }

    @Override
    public void saveIntermediateFrame(BitmapPal frame) throws Exception
    {
        BitmapPal frameToSave = convertTo1BppIfRequired(frame, mCompression, mBinarizationMethod, mThreshold);
        mWriter.writeInsert(mImageIdx++, frameToSave.getNativeImage());
    }

    @Override
    public void flushMultiframe() throws Exception
    {
        //FIX WORDSJAVA-2381. mStream.flush() must be here
        mStream.flush();
    }

    @Override
    public void close() throws IOException
    {
        super.close();
        try
        {
            mStream.flush();
        }
        catch (Exception e)
        {
            Log.error(e);
        }
    }

    private TiffMetadata saveMetadata(BitmapPal frame)
    {
        TiffMetadata metadata = new TiffMetadata();
        metadata.setXResolution(frame.getHorizontalResolution());
        metadata.setYResolution(frame.getVerticalResolution());
        // The resolutions above are in DPI. Say so explicitly, some viewers do not assume the default unit (TestImageFormat.testJiraJ1744).
        metadata.setResolutionUnit(BaselineTIFFTags.RESOLUTION_UNIT_INCH);
        // metadata.setPhotometricInterpretation(BaselineTIFFTags.PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO);
        metadata.setCompression(getEncoderValueForTiffCompression(mCompression));
        return metadata;
    }

    private static /*EncoderValue*/int getEncoderValueForTiffCompression(/*TiffCompressionCore*/int compression)
    {
        switch (compression)
        {
            case TiffCompressionCore.NONE:
                return BaselineTIFFTags.COMPRESSION_NONE;
            case TiffCompressionCore.RLE:
                return BaselineTIFFTags.COMPRESSION_CCITT_RLE;
            case TiffCompressionCore.LZW:
                return BaselineTIFFTags.COMPRESSION_LZW;
            case TiffCompressionCore.CCITT_3:
                return BaselineTIFFTags.COMPRESSION_CCITT_T_4;
            case TiffCompressionCore.CCITT_4:
                return BaselineTIFFTags.COMPRESSION_CCITT_T_6;
            case TiffCompressionCore.JPEG:
                return BaselineTIFFTags.COMPRESSION_JPEG;
            default:
                throw new IllegalArgumentException("compression: " + compression);
        }
    }

    private Stream mStream;
    private TIFFImageWriter mWriter;
    private int mImageIdx;

    private BitmapPal mFirstFrame;
}
