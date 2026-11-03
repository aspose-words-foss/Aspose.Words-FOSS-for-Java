// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
package com.aspose;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/// <summary>
/// Represents a one-dimensional array of pixel data in the 32bppArgb-bitmap.
/// This class is a proxy for PixelData implementations.
/// </summary>
public class BitmapDataPal implements PixelData
{
    public Buffer getArgbData()
    {
       return mArgbData.getArgbData();
    }

    public int getArgbDataLength()
    {
       return mArgbData.getArgbData().capacity();
    }

    BitmapDataPal(BufferedImage bitmap)
    {
        if (bitmap == null)
            throw new IllegalArgumentException("Bitmap cannot be null.");

        final int dataType = bitmap.getRaster().getDataBuffer().getDataType();
        switch (dataType)
        {
            case DataBuffer.TYPE_BYTE:
                mArgbData = new PixelDataByte(bitmap);
                break;

            case DataBuffer.TYPE_INT:
                mArgbData = new PixelDataInt(bitmap);
                break;

            case DataBuffer.TYPE_USHORT:
            case DataBuffer.TYPE_SHORT:
            case DataBuffer.TYPE_FLOAT:
            case DataBuffer.TYPE_DOUBLE:
            case DataBuffer.TYPE_UNDEFINED:
                throw new IllegalArgumentException("BitmapDataPal doesn't support this kind of DataBuffers yet. DataType=" + dataType);
        }
    }

    public void unlockBits() { mArgbData.unlockBits();}

    public int getPixelCount() { return mArgbData.getPixelCount(); }

    public byte getA(int i) { return mArgbData.getA(i); }

    public void setA(int i, byte value) { mArgbData.setA(i, value);}

    public byte getR(int i) { return mArgbData.getR(i); }

    public byte getG(int i) { return mArgbData.getG(i); }

    public byte getB(int i) { return mArgbData.getB(i); }

    public void setArgb(int i, byte a, byte r, byte g, byte b) { mArgbData.setArgb(i, a, r, g, b); }

    private /*final*/ PixelData mArgbData;
}
