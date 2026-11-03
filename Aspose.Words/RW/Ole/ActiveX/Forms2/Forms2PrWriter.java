// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/10/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.BitConverter;
import com.aspose.ArrayUtil;
import java.util.ArrayList;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.Debug;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Implements writing of a Forms 2.0 control properties.
/// </summary>
class Forms2PrWriter
{
    /// <summary>
    /// Ctor.
    /// </summary>
    Forms2PrWriter(BinaryWriter writer, Forms2Pr pr) throws Exception
    {
        mStart = (int)writer.getBaseStream().getPosition();
        mWriter = writer;
        mPr = pr;
    }

    /// <summary>
    /// Writes padding.
    /// </summary>
    void writePadding(int size) throws Exception
    {
        if (size == 1)
            return;

        // Padding size cannot be greater 4 by spec.
        int padSize = Math.min(4, size);

        int pos = (int)mWriter.getBaseStream().getPosition() - mStart;

        int div = pos % padSize;
        if (div == 0)
            return;

        mWriter.writeBytes(G_PADDING, 0, padSize - div);
    }

    /// <summary>
    /// Writes unsigned integer with padding.
    /// </summary>
    void write(/*uint*/int value) throws Exception
    {
        byte[] raw = BitConverter.getBytes(value);

        writePadding(4);
        mWriter.writeBytes(raw, 0, 4);
    }

    /// <summary>
    /// Writes property specified by a key.
    /// </summary>
    boolean write(int key, int valueSize) throws Exception
    {
        byte[] raw = getRawValue(key, valueSize);
        if (raw == null)
            return false;

        writeBytes(raw, valueSize);

        return true;
    }

    /// <summary>
    /// Writes byte array with a specified padding size.
    /// </summary>
    boolean writeBytes(byte[] raw, int paddingSize) throws Exception
    {
        if (!ArrayUtil.hasData(raw))
            return false;

        writePadding(paddingSize);
        mWriter.writeBytes(raw);

        return true;
    }

    /// <summary>
    /// Writes array of byte array.
    /// </summary>
    boolean writeBytes(byte[][] raw) throws Exception
    {
        if ((raw == null) || (raw.length == 0))
            return false;

        for (byte[] bytes : raw)
            writeBytes(bytes);

        return true;
    }

    /// <summary>
    /// Returns array of byte arrays representing input list of strings
    /// as specified in [MS-OFORMS], 2.4.14.1 ArrayString.
    /// </summary>
    static byte[][] getBytes(ArrayList<String> list, /*out*/RefInt size)
    {
        byte[][] bytes = new byte[list.size()][];

        size.set(0);
        for (int i = 0; i < list.size(); i++)
        {
            bytes[i] = getBytes(list.get(i));
            size.set(size.get() + (/*uint*/int)bytes[i].length);
        }

        return bytes;
    }

    /// <summary>
    /// Writes count of bytes with compression flag for a specified string.
    /// </summary>
    /// <returns>Byte array representing input string.</returns>
    byte[] writeCountOfBytesWithCompressionFlag(String value) throws Exception
    {
        if (!StringUtil.hasChars(value))
            return null;

        Encoding encoding = StringUtil.containsOnlyAscii(value) ? Encoding.getASCII() : Encoding.getUnicode();

        byte[] raw = encoding.getBytes(value);

        /*uint*/int countOfBytes = (/*uint*/int)((((/*uint*/int)raw.length) & 0xFFFFFFFFL) & 0x0fffffff);
        /*uint*/int compressionFlag = (encoding.equals(Encoding.getASCII()) && ((countOfBytes & 0xFFFFFFFFL) > 0)) ? (int) 0x80000000L : 0x0;

        write((/*uint*/int)((compressionFlag & 0xFFFFFFFFL) | (countOfBytes & 0xFFFFFFFFL)));

        return raw;
    }

    /// <summary>
    /// Writes GuidAndPicture as specified in [MS-OFORMS], 2.4.8 GuidAndPicture.
    /// </summary>
    void writeGuidAndPicture(byte[] imageBytes) throws Exception
    {
        Debug.assertTrue(ArrayUtil.hasData(imageBytes));

        // Write CLSID_StdPicture.
        writeBytes(Forms2OleControl.CLSID_STD_PICTURE.toByteArray());

        // Write StdPicture.
        writeWithoutPadding(Forms2OleControl.STD_PICTURE_PREAMBLE);
        writeWithoutPadding((/*uint*/int)imageBytes.length);
        writeBytes(imageBytes);
    }

    /// <summary>
    /// Writes GuidAndFont as specified in [MS-OFORMS], 2.4.7 GuidAndFont.
    /// </summary>
    void writeGuidAndFont() throws Exception
    {
        // The font must be either StdFont or TextProps.
        Debug.assertTrue(!StdFont.hasDefaultValue(mPr) || !Forms2TextProps.hasAllDefaultValues(mPr));

        if (!StdFont.hasDefaultValue(mPr))
        {
            // Write GUID and StdFont.
            Guid guid = new Guid(StdFont.GUID);
            mWriter.writeBytes(guid.toByteArray());

            StdFont font = (StdFont)mPr.get(Forms2Attr.FONT);
            font.write(mWriter);
        }
        else
        {
            // Write GUID and TextProps.
            Guid guid = new Guid(Forms2TextProps.GUID);
            mWriter.writeBytes(guid.toByteArray());

            Forms2TextProps.write(mWriter, mPr);
        }
    }

    /// <summary>
    /// Writes the sum of sizes of PropMask, DataBlock, and ExtraDataBlock and a specified PropMask.
    /// </summary>
    void writeDataSizeAndPropMask(int startPos, /*uint*/int propMask) throws Exception
    {
        writeDataSizeAndPropMasks(startPos, propMask, (int) 0xFFFFFFFFL);
    }

    /// <summary>
    /// Writes the sum of sizes of PropMasks, DataBlock, and ExtraDataBlock and a specified PropMasks.
    /// </summary>
    void writeDataSizeAndPropMasks(int startPos, /*uint*/int propMask1, /*uint*/int propMask2) throws Exception
    {
        int sizeOffset = startPos + 2;
        short size = (short)((mWriter.getBaseStream().getPosition() - 2) - sizeOffset);
        mWriter.seek(sizeOffset, SeekOrigin.BEGIN);

        mWriter.writeInt16(size);
        mWriter.writeUInt32(propMask1);
        if ((propMask2 & 0xFFFFFFFFL) != 0xFFFFFFFFL)
            mWriter.writeUInt32(propMask2);

        mWriter.seek(0, SeekOrigin.END);
    }

    /// <summary>
    /// Gets property specified by a key as raw bytes.
    /// </summary>
    private byte[] getRawValue(int key, int valueSize)
    {
        // Special check for Font as it can be of two types: StdFont and TextProps.
        // In case of TextProps it is located in a bunch of corresponding Forms2Attrs,
        // and not in just a single Forms2Attr.Font.
        if (key == Forms2Attr.FONT)
        {
            if (!hasFont())
                return null;
        }
        else
        {
            // We should not write default values.
            if (mPr.isDefaultValue(key))
                return null;
        }

        switch (key)
        {
            case Forms2Attr.FOREGROUND_COLOR:
            case Forms2Attr.BACKGROUND_COLOR:
            case Forms2Attr.BORDER_COLOR:
                return BitConverter.getBytes(((OleColor)mPr.get(key)).toRaw());

            case Forms2Attr.SIZE:
            case Forms2Attr.LOGICAL_SIZE:
                return ((OleSize)mPr.get(key)).toRaw();

            case Forms2Attr.SCROLL_POSITION:
            case Forms2Attr.SITE_POSITION:
                return ((OlePosition)mPr.get(key)).toRaw();

            case Forms2Attr.VALUE:
            case Forms2Attr.CAPTION:
            case Forms2Attr.GROUP_NAME:
            {
                String val = (String)mPr.get(key);
                Encoding encoding = StringUtil.containsOnlyAscii(val)
                    ? Encoding.getASCII()
                    : Encoding.getUnicode();
                return encoding.getBytes(val);
            }

            case Forms2Attr.MOUSE_ICON:
            case Forms2Attr.PICTURE:
            case Forms2Attr.FONT:
                return BitConverter.getBytes((/*ushort*/short)0xFFFF);

            case Forms2Attr.MATCH_ENTRY:
            case Forms2Attr.SHOW_DROP_BUTTON_WHEN:
            case Forms2Attr.DROP_BUTTON_STYLE:
            case Forms2Attr.BORDER_STYLE:
            case Forms2Attr.DISPLAY_STYLE:
            case Forms2Attr.SCROLL_BARS:
            case Forms2Attr.PICTURE_POSITION:
            case Forms2Attr.PICTURE_SIZE_MODE:
            case Forms2Attr.PICTURE_ALIGNMENT:
            case Forms2Attr.PICTURE_TILING:
            case Forms2Attr.SPECIAL_EFFECT:
            case Forms2Attr.MOUSE_POINTER:
            case Forms2Attr.PARAGRAPH_ALIGN:
            case Forms2Attr.CLSID_CACHE_INDEX:
            case Forms2Attr.MIN:
            case Forms2Attr.MAX:
            case Forms2Attr.POSITION:
            case Forms2Attr.PREV_ENABLED:
            case Forms2Attr.NEXT_ENABLED:
            case Forms2Attr.SMALL_CHANGE:
            case Forms2Attr.DELAY:
            case Forms2Attr.CYCLE:
            case Forms2Attr.ZOOM:
            case Forms2Attr.ID:
            case Forms2Attr.HELP_CONTEXT_ID:
            case Forms2Attr.TAB_INDEX:
            case Forms2Attr.LIST_INDEX:
            case Forms2Attr.TAB_ORIENATION:
            case Forms2Attr.TAB_STYLE:
            case Forms2Attr.TAB_DATA:
            case Forms2Attr.PAGE_COUNT:
            case Forms2Attr.GRID_X:
            case Forms2Attr.GRID_Y:
            case Forms2Attr.CLICK_CONTROL_MODE:
            case Forms2Attr.DBL_CLICK_CONTROL_MODE:
            case Forms2Attr.MULTI_SELECT:
                return getBytes((/*int*/Integer)mPr.get(key), valueSize);

            case Forms2Attr.ACCELERATOR:
                return getBytes((/*char*/Character)mPr.get(key), valueSize);

            case Forms2Attr.PROPORTIONAL_THUMB:
                return getBytes((((/*ushort*/Short)mPr.get(key)) & 0xFFFF), valueSize);

            default:
                return getBytesUint((/*uint*/Integer)mPr.get(key), valueSize);
        }
    }

    /// <summary>
    /// Returns raw bytes from a unsigned integer padded to the needed size from the end with zeroes.
    /// </summary>
    private static byte[] getBytesUint(/*uint*/int value, int size)
    {
        byte[] raw = BitConverter.getBytes(value);
        if (size == 4)
            return raw;

        byte[] val = new byte[size];
        System.arraycopy((raw), 0, (val), 0, (size));
        return val;
    }

    /// <summary>
    /// Returns bytes array from a specified string as specified [MS-OFORMS], 2.4.14.1 ArrayString.
    /// </summary>
    private static byte[] getBytes(String value)
    {
        Encoding encoding = StringUtil.containsOnlyAscii(value) ? Encoding.getASCII() : Encoding.getUnicode();
        byte[] itemBytes = encoding.getBytes(value);

        /*uint*/int countOfBytes = (/*uint*/int)((((/*uint*/int)itemBytes.length) & 0xFFFFFFFFL) & 0x0fffffff);
        /*uint*/int compressionFlag = (encoding.equals(Encoding.getASCII()) && ((countOfBytes & 0xFFFFFFFFL) > 0)) ? (int) 0x80000000L : 0x0;
        byte[] countOfBytesWithCompressionFlag = BitConverter.getBytes((/*uint*/int)((compressionFlag & 0xFFFFFFFFL) | (countOfBytes & 0xFFFFFFFFL)));

        int len = countOfBytesWithCompressionFlag.length + itemBytes.length;
        int pad = ((len % 4) == 0) ? 0 : 4 - (len % 4);

        byte[] bytes = new byte[len + pad];
        System.arraycopy(countOfBytesWithCompressionFlag, 0, bytes, 0, countOfBytesWithCompressionFlag.length);
        System.arraycopy(itemBytes, 0, bytes, 4, itemBytes.length);

        return bytes;
    }

    /// <summary>
    /// Returns raw bytes from a integer padded to the needed size from the end with zeroes.
    /// </summary>
    private static byte[] getBytes(int value, int size)
    {
        return getBytesUint((/*uint*/int)value, size);
    }

    /// <summary>
    /// Writes byte array without paddings.
    /// </summary>
    private void writeBytes(byte[] raw) throws Exception
    {
        writeBytes(raw, 1);
    }

    /// <summary>
    /// Writes unsigned integer without padding.
    /// </summary>
    private void writeWithoutPadding(/*uint*/int value) throws Exception
    {
        byte[] raw = BitConverter.getBytes(value);
        mWriter.writeBytes(raw, 0, 4);
    }

    /// <summary>
    /// Returns true, if there is at least one non-empty font property.
    /// </summary>
    private boolean hasFont()
    {
        return !StdFont.hasDefaultValue(mPr) || !Forms2TextProps.hasAllDefaultValues(mPr);
    }

    private /*final*/ BinaryWriter mWriter;
    private /*final*/ int mStart;
    private /*final*/ Forms2Pr mPr;
    private static /*final*/ byte[] G_PADDING = new byte[4];
}

