// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2022 by Dmitry Burov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.msPointF;


/*static*/ class PhotoshopMetadataReader
{
	/* Simulation of static class by using private constructor */
	private PhotoshopMetadataReader()
	{}

    static /*PointF*/long readResolution(BigEndianBinaryReader reader, /*PointF*/long resolution) throws Exception
    {
        boolean bimMarkerFound = searchFor8BimMarker(reader);
        if (!bimMarkerFound)
            return resolution;

        // Check the image resource ID
        /*uint*/int imageResourceId = (reader.readUInt16() & 0xFFFF);
        if (imageResourceId != 0x03ED) //  ResolutionInfo structure. See Appendix A in Photoshop API Guide.pdf.
            return resolution;

        // Read ResolutionInfo structure.

        if (!skipPascalString(reader))
            return resolution;

        // Read the actual size of resource data that follows.
        /*uint*/int resourceDataSize = reader.readUInt32();

        // Read the resolution structure.

        // X resolution fixed point format.
        int xRresolutionValue = reader.readInt16();
        int xRresolutionFraction = reader.readInt16();
        float xResolution = getFloatFromFixed(xRresolutionValue, xRresolutionFraction);

        // Skip horizontal resolution display unit.
        reader.readInt16();

        // Skip width display unit.
        reader.readInt16();

        // Y resolution fixed point format.
        int yRresolutionValue = reader.readInt16();
        int yRresolutionFraction = reader.readInt16();
        float yResolution = getFloatFromFixed(yRresolutionValue, yRresolutionFraction);

        // Skip vertical resolution display unit.
        reader.readInt16();

        // Skip height display unit.
        reader.readInt16();

        return msPointF.ctor(xResolution, yResolution);
    }

    private static float getFloatFromFixed(int value, int fraction)
    {
        return value + (float)fraction / 65536f;
    }

    private static boolean skipPascalString(BigEndianBinaryReader reader) throws Exception
    {
        // Skip the first byte of the string.
        reader.readByte();

        // Skip the pascal string.            
        while (!reader.isEndOfStream())
        {
            if (reader.readByte() == 0)
                return true;
        }

        // Seems like an invalid structure.
        return false;
    }

    private static boolean searchFor8BimMarker(BigEndianBinaryReader reader) throws Exception
    {
        // "8BIM" marker as 32-bit integer.
        final int BIM_MARKER = 0x3842494d;
        QuadByteQueue byteQueue = new QuadByteQueue();
        boolean bimMarkerFound = false;

        while (!reader.isEndOfStream())
        {
            byte b = reader.readByte();
            byteQueue.enqueue(b);

            // Check for the end of "8BIM" marker, i.e. 'M'. first
            // and then check the full "8BIM" marker.
            if ((b == 0x4d) && (byteQueue.getIntValue() == BIM_MARKER))
            {
                bimMarkerFound = true;
                break;
            }
        }

        return bimMarkerFound;
    }
}

