// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/07/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



class JpegReader
{
    public JpegReader(BigEndianBinaryReader reader)
    {
        mReader = reader;
    }

    public boolean goToNextMarker() throws Exception
    {
        // All JPEG markers are 2 bytes length. The leading byte is always 0xFF, and the trailing byte is not equal
        // to 0x00 or 0xFF. Areas before markers may be filled with 0xFF bytes.

        // WORDSNET-6573 Normally, at the moment the reader is positioned right before the next marker.
        // But if JPEG file is broken and contains invalid segment sizes, we can find ourselves anywhere in the middle
        // of a segment. In this case, we search for the nearest following marker to recover image processing.

        short leadingByte = 0;
        short trailingByte = 0;
        boolean markerFound = false;

        while (!markerFound)
        {
            // Find the next possible leading byte.
            while (leadingByte != 0xFF)
            {
                if (isEndOfStream())
                    break;

                leadingByte = (short)(mReader.readByte() & 0xFF);
            }

            if (isEndOfStream())
                break;

            // A possible leading byte found. Read and check the trailing byte.
            trailingByte = (short)(mReader.readByte() & 0xFF);
            if ((trailingByte == 0) || (trailingByte == 0xFF))
            {
                // The trailing byte is invalid, and the 2-byte sequence is not a marker. Let's advance one byte
                // and continue searching.
                leadingByte = trailingByte;
                trailingByte = 0;
            }
            else
            {
                // The trailing byte is valid. We found a marker.
                markerFound = true;
            }
        }

        setCurrentMarker((/*ushort*/short)(markerFound ? (0xFF00 | (trailingByte & 0xFF)) : (NO_MARKER & 0xFFFF)));
        return !isEndOfStream();
    }

    public /*ushort*/short getCurrentMarker() { return mCurrentMarker; }; private void setCurrentMarker(/*ushort*/short value) { mCurrentMarker = value; };

    private /*ushort*/short mCurrentMarker;

    public boolean getMarkerFound() { return getCurrentMarker() != NO_MARKER; }

    public boolean isEoiMarker() { return getCurrentMarker() == MARKER_EOI; }

    public boolean isEndOfStream() throws Exception { return mReader.isEndOfStream(); }

    private /*final*/ BigEndianBinaryReader mReader;

    public static final /*ushort*/short MARKER_SOI = (short) 0xFFD8;
    public static final /*ushort*/short MARKER_EOI = (short) 0xFFD9;
    public static final /*ushort*/short MARKER_EXIF = (short) 0xFFE1;
    public static final /*ushort*/short NO_MARKER = (short) 0;
}

