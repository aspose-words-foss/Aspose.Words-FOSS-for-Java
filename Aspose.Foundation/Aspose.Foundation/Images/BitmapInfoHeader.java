// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/01/2006 by Roman Korchagin
//08/04/08 by Alexey Linnik
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Represents BITMAPINFOHEADER object.
/// BITMAPINFOHEADER contains information about the dimensions and color format of a DIB. 
/// 
/// See http://msdn.microsoft.com/en-us/library/aa930622.aspx for details.
/// </summary>
class BitmapInfoHeader extends DibHeader
{
    public /*override*/ void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt32(getSize());
        writer.writeInt32(mWidth);
        writer.writeInt32(mHeight);
        writer.writeInt16((short)(mPlanes & 0xFFFF));
        writer.writeInt16((short)(mBitCount & 0xFFFF));
        writer.writeInt32((int)mCompression);
        writer.writeInt32((int)(mSizeImage & 0xFFFFFFFFL));
        writer.writeInt32(mXPelsPerMeter);
        writer.writeInt32(mYPelsPerMeter);
        writer.writeInt32((int)(mClrUsed & 0xFFFFFFFFL));
        writer.writeInt32((int)(mClrImportant & 0xFFFFFFFFL));
    }

    /// <summary>
    /// Gets the size of the color table that follows this header in the DIB.
    /// </summary>
    public /*override*/ int getColorTableSize() { return ImageUtil.getPaletteSize(mCompression, (mBitCount & 0xFFFF), (int)(mClrUsed & 0xFFFFFFFFL)); }

    /// <summary>
    /// Specifies the number of bytes required by the structure.
    /// </summary>
    public /*override*/ int getSize() { return BITMAP_INFO_HEADER_SIZE; }

    /// <summary>
    /// Specifies the width of the bitmap, in pixels. 
    /// </summary>
    public int getWidth() { return mWidth; }
    public void setWidth(int value) { mWidth = value; }

    /// <summary>
    /// Specifies the height of the bitmap, in pixels. 
    /// 
    /// CAN BE NEGATIVE. 
    /// If negative, is negative, the bitmap is a top-down DIB and its origin is the upper left corner. 
    /// </summary>
    public int getHeight() { return mHeight; }
    public void setHeight(int value) { mHeight = value; }


    /// <summary>
    /// Specifies the number of planes for the target device. 
    /// This value must be set to 1.
    /// </summary>
    public /*ushort*/short getPlanes() { return mPlanes; }
    public void setPlanes(/*ushort*/short value) { mPlanes = value; }

    /// <summary>
    /// Specifies the number of bits-per-pixel. 
    /// </summary>
    public /*ushort*/short getBitCount() { return mBitCount; }
    public void setBitCount(/*ushort*/short value) { mBitCount = value; }

    /// <summary>
    /// Specifies the type of compression for a compressed bottom-up bitmap.
    /// </summary>
    public /*BitmapCompression*/int getCompression() { return mCompression; }
    public void setCompression(/*BitmapCompression*/int value) { mCompression = value; }

    /// <summary>
    /// Specifies the size, in bytes, of the image. This may be set to zero for BI_RGB bitmaps. 
    /// </summary>
    public /*uint*/int getSizeImage() { return mSizeImage; }
    public void setSizeImage(/*uint*/int value) { mSizeImage = value; }

    /// <summary>
    /// Specifies the horizontal resolution, in pixels-per-meter.
    /// </summary>
    public int getXPelsPerMeter() { return mXPelsPerMeter; }
    public void setXPelsPerMeter(int value) { mXPelsPerMeter = value; }

    /// <summary>
    /// Specifies the vertical resolution, in pixels-per-meter.
    /// </summary>
    public int getYPelsPerMeter() { return mYPelsPerMeter; }
    public void setYPelsPerMeter(int value) { mYPelsPerMeter = value; }

    /// <summary>
    /// Specifies the number of color indexes in the color table that are actually used by the bitmap. 
    /// If this value is zero, the bitmap uses the maximum number of colors corresponding to the value
    /// of the biBitCount member for the compression mode specified by biCompression. 
    /// </summary>
    public /*uint*/int getClrUsed() { return mClrUsed; }
    public void setClrUsed(/*uint*/int value) { mClrUsed = value; }

    /// <summary>
    /// Specifies the number of color indexes that are required for displaying the bitmap. 
    /// If this value is zero, all colors are required.
    /// </summary>
    public /*uint*/int getClrImportant() { return mClrImportant; }
    public void setClrImportant(/*uint*/int value) { mClrImportant = value; }

    private int mWidth;
    private int mHeight;
    private /*ushort*/short mPlanes;
    private /*ushort*/short mBitCount;
    private /*BitmapCompression*/int mCompression;
    private /*uint*/int mSizeImage;
    private int mXPelsPerMeter;
    private int mYPelsPerMeter;
    private /*uint*/int mClrUsed;
    private /*uint*/int mClrImportant;
}

