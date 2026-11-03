// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 24/05/2006 by Dmitry Vorobyev

package com.aspose;

import java.io.OutputStream;

/// <summary>
/// This class is used to store a temporary TIFF data between method calls when saving a multipage TIFF.
/// </summary>
public final class TiffWriterPal extends BaseTiffWriterPal
{
    @Override
    public void saveFirstFrame(BitmapPal frame, OutputStream stream, int compression, int binarizationMethod, byte threshold, boolean isMultiframe) throws Exception
    {
        mTiffWriter.saveFirstFrame(frame, stream, compression, binarizationMethod, threshold, isMultiframe);
    }

    @Override
    public void saveIntermediateFrame(BitmapPal frame) throws Exception
    {
        mTiffWriter.saveIntermediateFrame(frame);
    }

    @Override
    public void flushMultiframe() throws Exception
    {
        mTiffWriter.flushMultiframe();
    }

    public TiffWriterPal()
    {
        mTiffWriter = new SimpleJaiTiffWriterPal();
    }

    private final BaseTiffWriterPal mTiffWriter;
}
