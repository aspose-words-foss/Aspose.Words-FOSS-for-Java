// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2013 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Represents DIB header (bitmap information header).
/// See http://en.wikipedia.org/wiki/BMP_file_format.
/// </summary>
abstract class DibHeader
{
    public static BitmapInfoHeader readAsBitmapInfoHeader(BinaryReader reader) throws Exception
    {
        long headerStartPosition = reader.getBaseStream().getPosition();
        int size = reader.readInt32();

        // Read all header types into BITMAPINFOHEADER for now.
        BitmapInfoHeader result = new BitmapInfoHeader();
        switch (size)
        {
            case OS_21_X_BITMAP_HEADER_SIZE:
                // http://www.fastgraph.com/help/bmp_os2_header_format.html
                result.setWidth((reader.readUInt16() & 0xFFFF));
                // NOTE: If a bitmask is present, this value includes the height of the mask (so often header.height = entry.height * 2)
                result.setHeight((reader.readUInt16() & 0xFFFF));
                result.setPlanes(reader.readUInt16());
                result.setBitCount(reader.readUInt16());
                break;
            case OS_22_X_BITMAP_HEADER_16_SIZE:
            case OS_22_X_BITMAP_HEADER_SIZE:
            case BITMAP_INFO_HEADER_SIZE:
            case BITMAP_V_2_INFO_HEADER_SIZE:
            case BITMAP_V_3_INFO_HEADER_SIZE:
            case BITMAP_V_4_HEADER_SIZE:
            case BITMAP_V_5_HEADER_SIZE:
            default:
                // All these headers have the same first 40 bytes.
                // Also this is the default case for all other values to read silently. Not very safe, but okay.
                // Read only fields from BITMAPINFOHEADER.
                result.setWidth(reader.readInt32());
                // NOTE: If a bitmask is present, this value includes the height of the mask (so often header.height = entry.height * 2)
                result.setHeight(reader.readInt32());
                result.setPlanes(reader.readUInt16());
                result.setBitCount(reader.readUInt16());
                if (size != OS_22_X_BITMAP_HEADER_16_SIZE)
                {
                    result.setCompression((/*BitmapCompression*/int)(reader.readUInt32() & 0xFFFFFFFFL));
                    result.setSizeImage(reader.readUInt32());
                    result.setXPelsPerMeter(reader.readInt32());
                    result.setYPelsPerMeter(reader.readInt32());
                    result.setClrUsed(reader.readUInt32());
                    result.setClrImportant(reader.readUInt32());
                }
                break;
        }

        reader.getBaseStream().setPosition(headerStartPosition + size);

        return result;
    }

    public abstract void write(BinaryWriter writer) throws Exception;

    public abstract int getSize();
    public abstract int getColorTableSize();

    public static final int OS_21_X_BITMAP_HEADER_SIZE = 12;
    public static final int OS_22_X_BITMAP_HEADER_16_SIZE = 16;
    public static final int OS_22_X_BITMAP_HEADER_SIZE = 64;
    public static final int BITMAP_INFO_HEADER_SIZE = 40;
    public static final int BITMAP_V_2_INFO_HEADER_SIZE = 52;
    public static final int BITMAP_V_3_INFO_HEADER_SIZE = 56;
    public static final int BITMAP_V_4_HEADER_SIZE = 108;
    public static final int BITMAP_V_5_HEADER_SIZE = 124;
}

