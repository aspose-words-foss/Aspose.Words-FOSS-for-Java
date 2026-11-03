// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.FontStyle;


/// <summary>
/// The OS/2 table consists of a set of metrics that are required in OpenType fonts.
/// See http://www.microsoft.com/typography/otspec/os2.htm for more info.
/// Here is info about older versions:
/// http://www.microsoft.com/typography/otspec/os2ver3.htm
/// http://www.microsoft.com/typography/otspec/os2ver2.htm
/// http://www.microsoft.com/typography/otspec/os2ver1.htm
/// http://www.microsoft.com/typography/otspec/os2ver0.htm
/// </summary>
class FontMetrics extends TTTable
{
    /// <summary>
    /// The version number for this OS/2 table.
    /// </summary>
    /*ushort*/short version;
    /// <summary>
    /// The Average Character Width parameter specifies the arithmetic average of the escapement (width) of
    /// all non-zero width glyphs in the font.
    /// Pels / em units.
    /// </summary>
    short xAvgCharWidth;
    /// <summary>
    /// Indicates the visual weight (degree of blackness or thickness of strokes) of the characters in the font.
    /// </summary>
    /*ushort*/short usWeightClass;
    /// <summary>
    /// Indicates a relative change from the normal aspect ratio (width to height ratio) as specified by a font
    /// designer for the glyphs in a font.
    /// </summary>
    /*ushort*/short usWidthClass;

    /// <summary>
    /// Indicates font embedding licensing rights for the font.
    /// </summary>
    FontFsType fsType;
    /// <summary>
    /// The recommended horizontal size in font design units for subscripts for this font.
    /// </summary>
    short ySubscriptXSize;
    /// <summary>
    /// The recommended vertical size in font design units for subscripts for this font.
    /// </summary>
    short ySubscriptYSize;
    /// <summary>
    /// The recommended horizontal offset in font design untis for subscripts for this font.
    /// </summary>
    short ySubscriptXOffset;
    /// <summary>
    /// The recommended vertical offset in font design units from the baseline for subscripts for this font.
    /// </summary>
    short ySubscriptYOffset;
    /// <summary>
    /// The recommended horizontal size in font design units for superscripts for this font.
    /// </summary>
    short ySuperscriptXSize;
    /// <summary>
    /// The recommended vertical size in font design units for superscripts for this font.
    /// </summary>
    short ySuperscriptYSize;
    /// <summary>
    /// The recommended horizontal offset in font design units for superscripts for this font.
    /// </summary>
    short ySuperscriptXOffset;
    /// <summary>
    /// The recommended vertical offset in font design units from the baseline for superscripts for this font.
    /// </summary>
    short ySuperscriptYOffset;
    /// <summary>
    /// Width of the strikeout stroke in font design units.
    /// </summary>
    short yStrikeoutSize;
    /// <summary>
    /// The position of the top of the strikeout stroke relative to the baseline in font design units.
    /// </summary>
    short yStrikeoutPosition;
    /// <summary>
    /// This parameter is a classification of font-family design.
    /// The high byte of this field contains the family class, while the low byte contains the family subclass.
    /// </summary>
    short sFamilyClass;
    /// <summary>
    /// This 10 byte series of numbers is used to describe the visual characteristics of a given typeface.
    /// </summary>
    FontPanose panose;
    /// <summary>
    /// Unicode Character Range.
    /// 32-bit unsigned long(4 copies) totaling 128 bits.
    /// This field is used to specify the Unicode blocks or ranges encompassed by the font file in the
    /// 'cmap' subtable for platform 3, encoding ID 1 (Microsoft platform).
    /// If the bit is set (1) then the Unicode range is considered functional. If the bit is clear (0)
    /// then the range is not considered functional.
    /// </summary>
    FontUnicodeRanges ulUnicodeRanges;

    /// <summary>
    /// The four character identifier for the vendor of the given type face.
    /// </summary>
    byte[] achVendID;
    /// <summary>
    /// Contains information concerning the nature of the font patterns.
    /// </summary>
    /*ushort*/short fsSelection;
    /// <summary>
    /// If set, it is strongly recommended to use OS/2.sTypoAscender - OS/2.sTypoDescender+ OS/2.sTypoLineGap
    /// as a value for default line spacing for this font.
    /// </summary>
    boolean getUseTypoMetrics() { return BitUtil.isSetUInt16(fsSelection, FS_SELECTION_USE_TYPO_METRICS_MASK); }

    /// <summary>
    /// The minimum Unicode index (character code) in this font, according to the cmap subtable for
    /// platform ID 3 and platform- specific encoding ID 0 or 1.
    /// </summary>
    /*ushort*/short usFirstCharIndex;
    /// <summary>
    /// The maximum Unicode index (character code) in this font, according to the cmap subtable for
    /// platform ID 3 and encoding ID 0 or 1.
    /// </summary>
    /*ushort*/short usLastCharIndex;
    /// <summary>
    /// The typographic ascender for this font.
    /// </summary>
    short sTypoAscender;
    /// <summary>
    /// The typographic descender for this font
    /// </summary>
    short sTypoDescender;
    /// <summary>
    /// The typographic line gap for this font.
    /// </summary>
    short sTypoLineGap;
    /// <summary>
    /// The ascender metric for Windows.
    /// </summary>
    short usWinAscent;
    /// <summary>
    /// The descender metric for Windows.
    /// </summary>
    short usWinDescent;
    /// <summary>
    /// 32-bit unsigned long(2 copies) totaling 64 bits.
    /// Code Page Character Range
    /// This field is used to specify the code pages encompassed by the font file in the 'cmap'
    /// subtable for platform 3, encoding ID 1 (Microsoft platform).
    /// </summary>
    FontCodepageRanges ulCodePageRanges = FontCodepageRanges.EMPTY;

    /// <summary>
    /// This metric specifies the distance between the baseline and the approximate height of
    /// non-ascending lowercase letters measured in FUnits.
    /// </summary>
    short sxHeight;
    /// <summary>
    /// This metric specifies the distance between the baseline and the approximate height of
    /// uppercase letters measured in FUnits.
    /// </summary>
    short sCapHeight;
    /// <summary>
    /// Whenever a request is made for a character that is not in the font, Windows provides
    /// this default character.
    /// </summary>
    /*ushort*/short usDefaultChar;
    /// <summary>
    /// This is the Unicode encoding of the glyph that Windows uses as the break character.
    /// </summary>
    /*ushort*/short usBreakChar;
    /// <summary>
    /// The maximum length of a target glyph context for any feature in this font.
    /// </summary>
    /*ushort*/short usMaxContext;

    /// <summary>
    /// Returns the font style.
    /// </summary>
    /*FontStyle*/int getStyle()
    {
        /*FontStyle*/int style = 0;
        style |= (((fsSelection & 0xFFFF) & 0x01) != 0) ? FontStyle.ITALIC : 0;
        style |= (((fsSelection & 0xFFFF) & 0x02) != 0) ? FontStyle.UNDERLINE : 0;
        // 0x04 - Negative
        // 0x08 - Outlined
        style |= (((fsSelection & 0xFFFF) & 0x10) != 0) ? FontStyle.STRIKEOUT : 0;
        style |= (((fsSelection & 0xFFFF) & 0x20) != 0) ? FontStyle.BOLD : 0;
        // 0x40 - Regular
        return style;
    }

    /// <summary>
    /// Ctor. Reads font metrics from the stream.
    /// </summary>
    FontMetrics(BigEndianBinaryReader reader) throws Exception
    {
        this.version = reader.readUInt16();
        this.xAvgCharWidth = reader.readInt16();
        this.usWeightClass = reader.readUInt16();
        this.usWidthClass = reader.readUInt16();
        this.fsType = new FontFsType(reader.readUInt16());

        this.ySubscriptXSize = reader.readInt16();
        this.ySubscriptYSize = reader.readInt16();
        this.ySubscriptXOffset = reader.readInt16();
        this.ySubscriptYOffset = reader.readInt16();

        this.ySuperscriptXSize = reader.readInt16();
        this.ySuperscriptYSize = reader.readInt16();
        this.ySuperscriptXOffset = reader.readInt16();
        this.ySuperscriptYOffset = reader.readInt16();

        this.yStrikeoutSize = reader.readInt16();
        this.yStrikeoutPosition = reader.readInt16();
        this.sFamilyClass = reader.readInt16();

        this.panose = new FontPanose(reader.readBytes(10));

        /*uint*/int ulUnicodeRange1 = reader.readUInt32();
        /*uint*/int ulUnicodeRange2 = reader.readUInt32();
        /*uint*/int ulUnicodeRange3 = reader.readUInt32();
        /*uint*/int ulUnicodeRange4 = reader.readUInt32();
        ulUnicodeRanges =
            new FontUnicodeRanges(ulUnicodeRange1, ulUnicodeRange2, ulUnicodeRange3, ulUnicodeRange4);

        this.achVendID = reader.readBytes(4);

        this.fsSelection = reader.readUInt16();
        this.usFirstCharIndex = reader.readUInt16();
        this.usLastCharIndex = reader.readUInt16();

        this.sTypoAscender = reader.readInt16();
        this.sTypoDescender = reader.readInt16();
        this.sTypoLineGap = reader.readInt16();

        // WORDSNET-9896 Spec says that these values are unsigned and GDI+ returns them as unsigned too.
        // Despite that MW seems to read them as signed. Do the same for now.
        this.usWinAscent = reader.readInt16();
        this.usWinDescent = reader.readInt16();

        // WORDSNET-15710 Seems that MW and Windows consider the font invalid when usWinAscent + usWinDescent = 0.
        if (usWinAscent + usWinDescent == 0)
            throw new IllegalStateException("Invalid font metrics.");

        // The following attributes are available only in True Type Font Version greater than 0.
        if ((this.version & 0xFFFF) <= 0)
            return;

        /*uint*/int ulCodePageRange1 = reader.readUInt32();
        /*uint*/int ulCodePageRange2 = reader.readUInt32();
        ulCodePageRanges = new FontCodepageRanges(ulCodePageRange1, ulCodePageRange2);

        // The following attributes are available only in True Type Font Version greater than 1.
        if ((this.version & 0xFFFF) <= 1)
            return;

        this.sxHeight = reader.readInt16();
        this.sCapHeight = reader.readInt16();

        this.usDefaultChar = reader.readUInt16();
        this.usBreakChar = reader.readUInt16();
        this.usMaxContext = reader.readUInt16();
    }

    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt16((version & 0xFFFF));
        writer.writeInt16(xAvgCharWidth);
        writer.writeUInt16((usWeightClass & 0xFFFF));
        writer.writeUInt16((usWidthClass & 0xFFFF));
        writer.writeUInt16((fsType.getValue() & 0xFFFF));

        writer.writeInt16(ySubscriptXSize);
        writer.writeInt16(ySubscriptYSize);
        writer.writeInt16(ySubscriptXOffset);
        writer.writeInt16(ySubscriptYOffset);

        writer.writeInt16(ySuperscriptXSize);
        writer.writeInt16(ySuperscriptYSize);
        writer.writeInt16(ySuperscriptXOffset);
        writer.writeInt16(ySuperscriptYOffset);

        writer.writeInt16(yStrikeoutSize);
        writer.writeInt16(yStrikeoutPosition);
        writer.writeInt16(sFamilyClass);

        writer.writeBytes(panose.getValues());

        writer.writeUInt32(ulUnicodeRanges.getRange1());
        writer.writeUInt32(ulUnicodeRanges.getRange2());
        writer.writeUInt32(ulUnicodeRanges.getRange3());
        writer.writeUInt32(ulUnicodeRanges.getRange4());

        writer.writeBytes(achVendID);

        writer.writeUInt16((fsSelection & 0xFFFF));
        writer.writeUInt16((usFirstCharIndex & 0xFFFF));
        writer.writeUInt16((usLastCharIndex & 0xFFFF));

        writer.writeInt16(sTypoAscender);
        writer.writeInt16(sTypoDescender);
        writer.writeInt16(sTypoLineGap);

        writer.writeInt16(usWinAscent);
        writer.writeInt16(usWinDescent);

        // The following attributes are available only in True Type Font Version greater than 0.
        if ((version & 0xFFFF) <= 0)
            return;

        writer.writeUInt32(ulCodePageRanges.getRange1());
        writer.writeUInt32(ulCodePageRanges.getRange2());

        // The following attributes are available only in True Type Font Version greater than 1.
        if ((version & 0xFFFF) <= 1)
            return;

        writer.writeInt16(sxHeight);
        writer.writeInt16(sCapHeight);

        writer.writeUInt16((usDefaultChar & 0xFFFF));
        writer.writeUInt16((usBreakChar & 0xFFFF));
        writer.writeUInt16((usMaxContext & 0xFFFF));
    }

    void fixVersionCompatibility()
    {
        // WORDSNET-10817 fsSelection bits 7-9 are allowed only in version 4.
        if (((fsSelection & 0xFFFF) & ~(FS_SELECTION_VERSION_0_MASK & 0xFFFF)) != 0)
        {
            if ((version & 0xFFFF) <= 1)
                // Version 0 and 1 has different layout than version 4. So remove prohibited bits to fix the problem.
                fsSelection = (/*ushort*/short)((fsSelection & 0xFFFF) & (FS_SELECTION_VERSION_0_MASK & 0xFFFF));
            else
                // Version 2, 3 and 4 have the same layout. So we could simply update the version to 4 in this case.
                // Moreover experiments shows that MW considers bit 7 (USE_TYPO_METRICS) in version 3.
                version = (short) 4;
        }
    }

    private static final /*ushort*/short FS_SELECTION_USE_TYPO_METRICS_MASK = (short) 0x0080;
    private static final /*ushort*/short FS_SELECTION_VERSION_0_MASK = (short) 0x003F;

    public static final int SEMI_BOLD_WEIGHT_VALUE = 600;
    public static final int BOLD_WEIGHT_VALUE = 700;
    public static final int REGULAR_WEIGHT_VALUE = 400;
}

