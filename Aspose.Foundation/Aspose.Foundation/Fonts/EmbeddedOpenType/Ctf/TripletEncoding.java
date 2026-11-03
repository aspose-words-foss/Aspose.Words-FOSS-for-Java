// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Helper class for Triplet Encoding.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#TripletEncoding for more info.
/// </remarks>
class TripletEncoding
{
    private TripletEncoding(int byteCount, int xBits, int yBits, int deltaX, int deltaY, int xSign, int ySign)
    {
        mByteCount = byteCount;
        mXBits = xBits;
        mYBits = yBits;
        mDeltaX = deltaX;
        mDeltaY = deltaY;
        mXSign = xSign;
        mYSign = ySign;
    }

    /// <summary>
    /// Read triplet from binary reader and fills X, Y and IsOnCurve fields of <paramref name="point"/>.
    /// </summary>
    /// <param name="reader">Binary reader.</param>
    /// <param name="flag">Previously red flag.</param>
    /// <param name="point">Point to write decoded values.</param>
    static void readTriplet(BigEndianBinaryReader reader, byte flag, CtfGlyphPoint point) throws Exception
    {
        // First bit of flag means IsOnCurve.
        point.IsOnCurve = (((flag & 0xFF) & 0x80) == 0);

        // Rest flag data represents index for Triplet Encoding.
        int tripletEncodingIndex = ((flag & 0xFF) & 0x7F);
        TripletEncoding encoding = G_INDEX[tripletEncodingIndex];

        // Read data according to triplet encoding. ByteCount counts flag byte too.
        byte[] data = reader.readBytes(encoding.mByteCount - 1);
        BitBinaryReader bitReader = new BitBinaryReader(new MemoryStream(data), true);
        try /*JAVA: was using*/
        {
            long xValue = bitReader.readUnsignedValue(encoding.mXBits);
            long yValue = bitReader.readUnsignedValue(encoding.mYBits);
            point.X = (short)(encoding.mXSign * (xValue + encoding.mDeltaX));
            point.Y = (short)(encoding.mYSign * (yValue + encoding.mDeltaY));
        }
        finally { if (bitReader != null) bitReader.dispose(); }
    }

    /// <summary>
    /// Write triplet for <paramref name="point"/> to bit binary writer, using specified triplet encoding flag.
    /// </summary>
    static void writeTriplet(BitBinaryWriter writer, byte flag, SimpleGlyfPoint point) throws Exception
    {
        int tripletEncodingIndex = ((flag & 0xFF) & 0x7F);
        TripletEncoding encoding = G_INDEX[tripletEncodingIndex];

        // Write data according to triplet encoding.
        writer.writeValue((Math.abs(point.getDX()) - encoding.mDeltaX), encoding.mXBits);
        writer.writeValue((Math.abs(point.getDY()) - encoding.mDeltaY), encoding.mYBits);
    }

    /// <summary>
    /// Returns index of <see cref="TripletEncoding"/> in <see cref="gIndex"/> by specified x and y values.
    /// </summary>
    /// <remarks> See table encoding in https://www.w3.org/Submission/MTX/#TripletEncoding </remarks>
    static byte getTripletEncodingIndex(int xVal, int yVal)
    {
        int index;

        int x = Math.abs(xVal);
        int y = Math.abs(yVal);

        if ((x == 0) && (y < 0x500))
        {
            int yRange = (y & 0xFF00) >> 8;
            index = 1 + 2 * yRange;
            // Do this because we subtract 2 below, but in this case should subtract 1.
            if (yVal < 0)
                index++;
        }
        else if ((x < 0x500) && (y == 0))
        {
            int xRange = (x & 0xFF00) >> 8;
            index = 11 + 2 * xRange;
        }
        else if ((x > 0xFFF) || (y > 0xFFF))
        {
            index = 127;
        }
        else if ((x > 768) || (y > 768))
        {
            index = 123;
        }
        else if ((x > 64) || (y > 64))
        {
            int xRange = ((x - 1) & 0xF00) >> 8;
            int yRange = ((y - 1) & 0xF00) >> 8;

            index = 87 + (xRange * 12) + (yRange * 4);
        }
        else
        {
            int xRange = ((x - 1) & 0xF0) >> 4;
            int yRange = ((y - 1) & 0xF0) >> 4;

            index = 23 + (xRange * 16) + (yRange * 4);
        }

        if (xVal < 0)
            index--;
        if (yVal < 0)
            index -= 2;

        return (byte)index;
    }

    private /*final*/ int mByteCount;
    private /*final*/ int mXBits;
    private /*final*/ int mYBits;
    private /*final*/ int mDeltaX;
    private /*final*/ int mDeltaY;
    private /*final*/ int mXSign;
    private /*final*/ int mYSign;

    /// <summary>
    /// Table of predefined encoding values.
    /// </summary>
    private static /*final*/ TripletEncoding[] G_INDEX =
        new TripletEncoding[]
            {
                new TripletEncoding(2, 0, 8, 0,     0,      0, -1),
                new TripletEncoding(2, 0, 8, 0,     0,      0, +1),
                new TripletEncoding(2, 0, 8, 0,     256,    0, -1),
                new TripletEncoding(2, 0, 8, 0,     256,    0, +1),
                new TripletEncoding(2, 0, 8, 0,     512,    0, -1),
                new TripletEncoding(2, 0, 8, 0,     512,    0, +1),
                new TripletEncoding(2, 0, 8, 0,     768,    0, -1),
                new TripletEncoding(2, 0, 8, 0,     768,    0, +1),
                new TripletEncoding(2, 0, 8, 0,     1024,   0, -1),
                new TripletEncoding(2, 0, 8, 0,     1024,   0, +1),
                new TripletEncoding(2, 8, 0, 0,     0,      -1, 0),
                new TripletEncoding(2, 8, 0, 0,     0,      +1, 0),
                new TripletEncoding(2, 8, 0, 256,   0,      -1, 0),
                new TripletEncoding(2, 8, 0, 256,   0,      +1, 0),
                new TripletEncoding(2, 8, 0, 512,   0,      -1, 0),
                new TripletEncoding(2, 8, 0, 512,   0,      +1, 0),
                new TripletEncoding(2, 8, 0, 768,   0,      -1, 0),
                new TripletEncoding(2, 8, 0, 768,   0,      +1, 0),
                new TripletEncoding(2, 8, 0, 1024,  0,      -1, 0),
                new TripletEncoding(2, 8, 0, 1024,  0,      +1, 0),
                new TripletEncoding(2, 4, 4, 1,     1,      -1, -1),
                new TripletEncoding(2, 4, 4, 1,     1,      +1, -1),
                new TripletEncoding(2, 4, 4, 1,     1,      -1, +1),
                new TripletEncoding(2, 4, 4, 1,     1,      +1, +1),
                new TripletEncoding(2, 4, 4, 1,     17,     -1, -1),
                new TripletEncoding(2, 4, 4, 1,     17,     +1, -1),
                new TripletEncoding(2, 4, 4, 1,     17,     -1, +1),
                new TripletEncoding(2, 4, 4, 1,     17,     +1, +1),
                new TripletEncoding(2, 4, 4, 1,     33,     -1, -1),
                new TripletEncoding(2, 4, 4, 1,     33,     +1, -1),
                new TripletEncoding(2, 4, 4, 1,     33,     -1, +1),
                new TripletEncoding(2, 4, 4, 1,     33,     +1, +1),
                new TripletEncoding(2, 4, 4, 1,     49,     -1, -1),
                new TripletEncoding(2, 4, 4, 1,     49,     +1, -1),
                new TripletEncoding(2, 4, 4, 1,     49,     -1, +1),
                new TripletEncoding(2, 4, 4, 1,     49,     +1, +1),
                new TripletEncoding(2, 4, 4, 17,    1,      -1, -1),
                new TripletEncoding(2, 4, 4, 17,    1,      +1, -1),
                new TripletEncoding(2, 4, 4, 17,    1,      -1, +1),
                new TripletEncoding(2, 4, 4, 17,    1,      +1, +1),
                new TripletEncoding(2, 4, 4, 17,    17,     -1, -1),
                new TripletEncoding(2, 4, 4, 17,    17,     +1, -1),
                new TripletEncoding(2, 4, 4, 17,    17,     -1, +1),
                new TripletEncoding(2, 4, 4, 17,    17,     +1, +1),
                new TripletEncoding(2, 4, 4, 17,    33,     -1, -1),
                new TripletEncoding(2, 4, 4, 17,    33,     +1, -1),
                new TripletEncoding(2, 4, 4, 17,    33,     -1, +1),
                new TripletEncoding(2, 4, 4, 17,    33,     +1, +1),
                new TripletEncoding(2, 4, 4, 17,    49,     -1, -1),
                new TripletEncoding(2, 4, 4, 17,    49,     +1, -1),
                new TripletEncoding(2, 4, 4, 17,    49,     -1, +1),
                new TripletEncoding(2, 4, 4, 17,    49,     +1, +1),
                new TripletEncoding(2, 4, 4, 33,    1,      -1, -1),
                new TripletEncoding(2, 4, 4, 33,    1,      +1, -1),
                new TripletEncoding(2, 4, 4, 33,    1,      -1, +1),
                new TripletEncoding(2, 4, 4, 33,    1,      +1, +1),
                new TripletEncoding(2, 4, 4, 33,    17,     -1, -1),
                new TripletEncoding(2, 4, 4, 33,    17,     +1, -1),
                new TripletEncoding(2, 4, 4, 33,    17,     -1, +1),
                new TripletEncoding(2, 4, 4, 33,    17,     +1, +1),
                new TripletEncoding(2, 4, 4, 33,    33,     -1, -1),
                new TripletEncoding(2, 4, 4, 33,    33,     +1, -1),
                new TripletEncoding(2, 4, 4, 33,    33,     -1, +1),
                new TripletEncoding(2, 4, 4, 33,    33,     +1, +1),
                new TripletEncoding(2, 4, 4, 33,    49,     -1, -1),
                new TripletEncoding(2, 4, 4, 33,    49,     +1, -1),
                new TripletEncoding(2, 4, 4, 33,    49,     -1, +1),
                new TripletEncoding(2, 4, 4, 33,    49,     +1, +1),
                new TripletEncoding(2, 4, 4, 49,    1,      -1, -1),
                new TripletEncoding(2, 4, 4, 49,    1,      +1, -1),
                new TripletEncoding(2, 4, 4, 49,    1,      -1, +1),
                new TripletEncoding(2, 4, 4, 49,    1,      +1, +1),
                new TripletEncoding(2, 4, 4, 49,    17,     -1, -1),
                new TripletEncoding(2, 4, 4, 49,    17,     +1, -1),
                new TripletEncoding(2, 4, 4, 49,    17,     -1, +1),
                new TripletEncoding(2, 4, 4, 49,    17,     +1, +1),
                new TripletEncoding(2, 4, 4, 49,    33,     -1, -1),
                new TripletEncoding(2, 4, 4, 49,    33,     +1, -1),
                new TripletEncoding(2, 4, 4, 49,    33,     -1, +1),
                new TripletEncoding(2, 4, 4, 49,    33,     +1, +1),
                new TripletEncoding(2, 4, 4, 49,    49,     -1, -1),
                new TripletEncoding(2, 4, 4, 49,    49,     +1, -1),
                new TripletEncoding(2, 4, 4, 49,    49,     -1, +1),
                new TripletEncoding(2, 4, 4, 49,    49,     +1, +1),
                new TripletEncoding(3, 8, 8, 1,     1,      -1, -1),
                new TripletEncoding(3, 8, 8, 1,     1,      +1, -1),
                new TripletEncoding(3, 8, 8, 1,     1,      -1, +1),
                new TripletEncoding(3, 8, 8, 1,     1,      +1, +1),
                new TripletEncoding(3, 8, 8, 1,     257,    -1, -1),
                new TripletEncoding(3, 8, 8, 1,     257,    +1, -1),
                new TripletEncoding(3, 8, 8, 1,     257,    -1, +1),
                new TripletEncoding(3, 8, 8, 1,     257,    +1, +1),
                new TripletEncoding(3, 8, 8, 1,     513,    -1, -1),
                new TripletEncoding(3, 8, 8, 1,     513,    +1, -1),
                new TripletEncoding(3, 8, 8, 1,     513,    -1, +1),
                new TripletEncoding(3, 8, 8, 1,     513,    +1, +1),
                new TripletEncoding(3, 8, 8, 257,   1,      -1, -1),
                new TripletEncoding(3, 8, 8, 257,   1,      +1, -1),
                new TripletEncoding(3, 8, 8, 257,   1,      -1, +1),
                new TripletEncoding(3, 8, 8, 257,   1,      +1, +1),
                new TripletEncoding(3, 8, 8, 257,   257,    -1, -1),
                new TripletEncoding(3, 8, 8, 257,   257,    +1, -1),
                new TripletEncoding(3, 8, 8, 257,   257,    -1, +1),
                new TripletEncoding(3, 8, 8, 257,   257,    +1, +1),
                new TripletEncoding(3, 8, 8, 257,   513,    -1, -1),
                new TripletEncoding(3, 8, 8, 257,   513,    +1, -1),
                new TripletEncoding(3, 8, 8, 257,   513,    -1, +1),
                new TripletEncoding(3, 8, 8, 257,   513,    +1, +1),
                new TripletEncoding(3, 8, 8, 513,   1,      -1, -1),
                new TripletEncoding(3, 8, 8, 513,   1,      +1, -1),
                new TripletEncoding(3, 8, 8, 513,   1,      -1, +1),
                new TripletEncoding(3, 8, 8, 513,   1,      +1, +1),
                new TripletEncoding(3, 8, 8, 513,   257,    -1, -1),
                new TripletEncoding(3, 8, 8, 513,   257,    +1, -1),
                new TripletEncoding(3, 8, 8, 513,   257,    -1, +1),
                new TripletEncoding(3, 8, 8, 513,   257,    +1, +1),
                new TripletEncoding(3, 8, 8, 513,   513,    -1, -1),
                new TripletEncoding(3, 8, 8, 513,   513,    +1, -1),
                new TripletEncoding(3, 8, 8, 513,   513,    -1, +1),
                new TripletEncoding(3, 8, 8, 513,   513,    +1, +1),
                new TripletEncoding(4, 12, 12, 0,   0,      -1, -1),
                new TripletEncoding(4, 12, 12, 0,   0,      +1, -1),
                new TripletEncoding(4, 12, 12, 0,   0,      -1, +1),
                new TripletEncoding(4, 12, 12, 0,   0,      +1, +1),
                new TripletEncoding(5, 16, 16, 0,   0,      -1, -1),
                new TripletEncoding(5, 16, 16, 0,   0,      +1, -1),
                new TripletEncoding(5, 16, 16, 0,   0,      -1, +1),
                new TripletEncoding(5, 16, 16, 0,   0,      +1, +1)
            };
}

