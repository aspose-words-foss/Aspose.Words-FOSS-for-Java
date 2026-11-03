package com.aspose;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * This class works with ARGB data stored in DataBufferInt.
 */
class PixelDataInt implements PixelData
{
    PixelDataInt(BufferedImage bitmap)
    {
        mArgbData = ((DataBufferInt) bitmap.getRaster().getDataBuffer()).getData();
    }

    @Override
    public void unlockBits() { /* empty */ }

    @Override
    public int getPixelCount() { return mArgbData.length; }

    @Override
    public byte getA(int i) { return (byte) (mArgbData[i] >>> 24); }

    @Override
    public void setA(int i, byte value) { mArgbData[i] = (mArgbData[i] & 0x00ffffff) | (value << 24); }

    @Override
    public byte getR(int i) { return (byte) ((mArgbData[i] >>> 16) & 0xff); }

    @Override
    public byte getG(int i) { return (byte) ((mArgbData[i] >>> 8) & 0xff); }

    @Override
    public byte getB(int i) { return (byte) (mArgbData[i] & 0xff); }

    @Override
    public void setArgb(int i, byte a, byte r, byte g, byte b)
    {
        mArgbData[i] = a << 24 | (r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff);
    }

    public Buffer getArgbData()
    {
        return IntBuffer.wrap(mArgbData);
    }

    private int[] mArgbData;
}
