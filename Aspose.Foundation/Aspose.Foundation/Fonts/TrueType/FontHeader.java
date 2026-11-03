// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.FontStyle;


/// <summary>
/// head - Font Header
///
/// This table gives global information about the font.
/// </summary>
class FontHeader extends TTTable
{
    /// <summary>
    /// Read head table from the binary reader.
    /// </summary>
    static FontHeader read(BigEndianBinaryReader reader) throws Exception
    {
        FontHeader header = new FontHeader();

        header.Version = reader.readUInt32();
        // WORDSNET-Test27393 Version 2 is not defined in OTF spec but in this case there is legacy Taiwan font
        // with version 2 which is handled well by MW and Windows.
        if (header.Version != 0x00010000 && header.Version != 0x00020000)
            throw new UnsupportedOperationException("Unsupported font head version.");
        header.Revision = reader.readUInt32();
        header.CheckSumAdjustment = reader.readUInt32();
        header.MagicNumber = reader.readUInt32();
        if (header.MagicNumber != 0x5F0F3CF5)
            throw new UnsupportedOperationException("Unsupported font head magic number.");
        header.Flags = reader.readUInt16();
        header.UnitsPerEm = reader.readUInt16();
        header.CreatedTime = reader.readInt64();
        header.ModifiedTime = reader.readInt64();
        header.XMin = reader.readInt16();
        header.YMin = reader.readInt16();
        header.XMax = reader.readInt16();
        header.YMax = reader.readInt16();
        header.MacStyle = reader.readUInt16();
        header.LowestRecPpem = reader.readUInt16();
        header.FontDirectionHint = reader.readInt16();
        header.IndexToLocFormat = reader.readInt16();
        header.GlyphDataFormat = reader.readInt16();

        return header;
    }

    /// <summary>
    /// Writes head table to binary writer.
    /// </summary>
    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt32(Version);
        writer.writeUInt32(Revision);
        writer.writeUInt32(CheckSumAdjustment);
        writer.writeUInt32(MagicNumber);
        writer.writeUInt16((Flags & 0xFFFF));
        writer.writeUInt16((UnitsPerEm & 0xFFFF));
        writer.writeInt64(CreatedTime);
        writer.writeInt64(ModifiedTime);
        writer.writeInt16(XMin);
        writer.writeInt16(YMin);
        writer.writeInt16(XMax);
        writer.writeInt16(YMax);
        writer.writeUInt16((MacStyle & 0xFFFF));
        writer.writeUInt16((LowestRecPpem & 0xFFFF));
        writer.writeInt16(FontDirectionHint);
        writer.writeInt16(IndexToLocFormat);
        writer.writeInt16(GlyphDataFormat);
    }

    /// <summary>
    /// 0x00010000 for version 1.0.
    /// </summary>
    /*uint*/int Version;

    /// <summary>
    /// Set by font manufacturer.
    /// </summary>
    /*uint*/int Revision;

    /// <summary>
    /// To compute: set it to 0, sum the entire font as ULONG, then store 0xB1B0AFBA - sum.
    /// </summary>
    /*uint*/int CheckSumAdjustment;

    /// <summary>
    /// Set to 0x5F0F3CF5.
    /// </summary>
    /*uint*/int MagicNumber;

    /// <summary>
    /// Bit 0: Baseline for font at y=0;
    /// Bit 1: Left sidebearing point at x=0;
    /// Bit 2: Instructions may depend on point size;
    /// Bit 3: Force ppem to integer values for all internal scaler math; may use fractional ppem sizes if this bit is clear;
    /// Bit 4: Instructions may alter advance width (the advance widths might not scale linearly);
    /// Bits 5-10: These should be set according to Apple's specification . However, they are not implemented in OpenType.
    /// Bit 11: Font data is 'lossless,' as a result of having been compressed and decompressed with the Agfa MicroType Express engine.
    /// Bit 12: Font converted (produce compatible metrics)
    /// Bit 13: Font optimized for ClearType
    /// Bit 14: Reserved, set to 0
    /// Bit 15: Reserved, set to 0
    /// </summary>
    /*ushort*/short Flags;

    /// <summary>
    /// Valid range is from 16 to 16384. This value should be a power of 2 for fonts that have TrueType outlines.
    /// </summary>
    /*ushort*/short UnitsPerEm;

    /// <summary>
    /// Number of seconds since 12:00 midnight, January 1, 1904. 64-bit integer.
    /// </summary>
    long CreatedTime;

    /// <summary>
    /// Number of seconds since 12:00 midnight, January 1, 1904. 64-bit integer.
    /// </summary>
    long ModifiedTime;

    /// <summary>
    /// For all glyph bounding boxes.
    /// </summary>
    short XMin;

    /// <summary>
    /// For all glyph bounding boxes.
    /// </summary>
    short YMin;

    /// <summary>
    /// For all glyph bounding boxes.
    /// </summary>
    short XMax;

    /// <summary>
    /// For all glyph bounding boxes.
    /// </summary>
    short YMax;

    /// <summary>
    /// Bit 0: Bold (if set to 1);
    /// Bit 1: Italic (if set to 1)
    /// Bit 2: Underline (if set to 1)
    /// Bit 3: Outline (if set to 1)
    /// Bit 4: Shadow (if set to 1)
    /// Bit 5: Condensed (if set to 1)
    /// Bit 6: Extended (if set to 1)
    /// Bits 7-15: Reserved (set to 0).
    /// </summary>
    /*ushort*/short MacStyle;

    /// <summary>
    /// Smallest readable size in pixels.
    /// </summary>
    /*ushort*/short LowestRecPpem;

    /// <summary>
    /// 0: Fully mixed directional glyphs;
    /// 1: Only strongly left to right;
    /// 2: Like 1 but also contains neutrals;
    /// -1: Only strongly right to left;
    /// -2: Like -1 but also contains neutrals.
    /// </summary>
    short FontDirectionHint;

    /// <summary>
    /// 0 for short offsets, 1 for long.
    /// </summary>
    short IndexToLocFormat;

    /// <summary>
    /// True if short glyph offsets are used.
    /// </summary>
    boolean isLocaShort() { return IndexToLocFormat == 0; }
    void isLocaShort(boolean value) { IndexToLocFormat = (short)(value ? 0 : 1); }

    /// <summary>
    /// 0 for current format
    /// </summary>
    short GlyphDataFormat;

    /*FontStyle*/int getStyle()
    {
        /*FontStyle*/int style = 0;
        style |= (((MacStyle & 0xFFFF) & 0x01) != 0) ? FontStyle.BOLD : 0;
        style |= (((MacStyle & 0xFFFF) & 0x02) != 0) ? FontStyle.ITALIC : 0;
        style |= (((MacStyle & 0xFFFF) & 0x04) != 0) ? FontStyle.UNDERLINE : 0;
        return style;
    }
}

