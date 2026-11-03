package com.aspose;

import java.nio.Buffer;

interface PixelData
{
    void unlockBits();

    int getPixelCount();

    byte getA(int i);

    void setA(int i, byte value);

    byte getR(int i);

    byte getG(int i);

    byte getB(int i);

    void setArgb(int i, byte a, byte r, byte g, byte b);

    Buffer getArgbData();
}
