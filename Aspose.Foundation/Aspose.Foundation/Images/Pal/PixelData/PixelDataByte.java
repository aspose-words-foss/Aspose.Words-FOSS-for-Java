package com.aspose;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * This class works with ARGB data stored in DataBufferByte.
 */
class PixelDataByte implements PixelData
{
    PixelDataByte(BufferedImage bitmap)
    {
        final WritableRaster raster = bitmap.getRaster();
        mNumBands = raster.getNumBands();
        mArgbData = ((DataBufferByte) raster.getDataBuffer()).getData();
        mPixelCount = mArgbData.length / mNumBands;
    }

    @Override
    public void unlockBits() { /* empty */ }

    @Override
    public int getPixelCount() { return mPixelCount;}

    @Override
    public byte getA(int i) { return mArgbData[i * mNumBands]; }

    @Override
    public void setA(int i, byte value) { mArgbData[i * mNumBands] = value; }

    @Override
    public byte getR(int i) { return (mArgbData[i * mNumBands + 1]); }

    @Override
    public byte getG(int i) { return (mArgbData[i * mNumBands + 2]); }

    @Override
    public byte getB(int i) { return (mArgbData[i * mNumBands + 3]); }

    @Override
    public void setArgb(int i, byte a, byte r, byte g, byte b)
    {
        final int position = i * mNumBands;
        mArgbData[position] = a;
        mArgbData[position + 1] = r;
        mArgbData[position + 2] = g;
        mArgbData[position + 3] = b;
    }

    public Buffer getArgbData()
    {
        return ByteBuffer.wrap(mArgbData);
    }

    private final byte[] mArgbData;
    private final int mNumBands;
    private final int mPixelCount;
}
