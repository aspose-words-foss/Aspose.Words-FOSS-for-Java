// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2009 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents 'cmap' table in a OpenType font.
/// http://www.microsoft.com/typography/otspec/cmap.htm
/// </summary>
abstract class Cmap extends TTTable
{
    protected Cmap(SortedIntegerListGeneric<Integer> charMap, int language)
    {
        CharMap = charMap;
        Language = language;
    }

    /// <summary>
    /// Reads 'cmap' table from binary reader.
    /// </summary>
    public static Cmap read(BigEndianBinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        if (version != 0)
            throw new IllegalStateException("Unexpected cmap table version.");
        int numTables = (reader.readUInt16() & 0xFFFF);

        CmapEncodingRecord[] records = readEncodingRecords(reader, numTables);
        return selectCmapEncoding(reader, records);
    }

    private static CmapEncodingRecord[] readEncodingRecords(BigEndianBinaryReader reader, int numTables) throws Exception
    {
        long tableStart = reader.getBaseStream().getPosition() - 4;
        CmapEncodingRecord[] result = new CmapEncodingRecord[numTables];

        for (int i = 0; i < numTables; i++)
        {
            int platformId = (reader.readUInt16() & 0xFFFF);
            int encodingId = (reader.readUInt16() & 0xFFFF);
            int offset = reader.readInt32();

            long currentPosition = reader.getBaseStream().getPosition();
            long subtablePosition = tableStart + offset;
            reader.getBaseStream().setPosition(subtablePosition);
            int format = (reader.readUInt16() & 0xFFFF);
            reader.getBaseStream().setPosition(currentPosition);

            result[i] = new CmapEncodingRecord(platformId, encodingId, format, subtablePosition);
        }

        return result;
    }

    private static Cmap selectCmapEncoding(BigEndianBinaryReader reader, CmapEncodingRecord[] encodingRecords) throws Exception
    {
        // First try Windows UCS-4 encoding.
        CmapEncodingRecord record =
            tryGetEncodingRecord(encodingRecords, PLATFORM_ID_MICROSOFT, MICROSOFT_ENCODING_ID_UNICODE_UCS_4, CMAP_FORMAT_12);
        if (record != null)
        {
            CmapSubtableFormat12 subtable = CmapSubtableFormat12.read(reader, record);
            return new CmapWinUcs4(subtable.getCharMap(), subtable.getLanguage());
        }

        // Then try Windows Symbol encoding.
        record = tryGetEncodingRecord(encodingRecords, PLATFORM_ID_MICROSOFT, MICROSOFT_ENCODING_ID_SYMBOL, CMAP_FORMAT_4);
        if (record != null)
        {
            CmapSubtableFormat4 subtable = CmapSubtableFormat4.read(reader, record);
            return new CmapUcs2(subtable.getCharMap(), subtable.getLanguage(), record.getPlatformId(), record.getEncodingId());
        }

        // Then try Windows UCS-2 encoding.
        record = tryGetEncodingRecord(encodingRecords, PLATFORM_ID_MICROSOFT, MICROSOFT_ENCODING_ID_UNICODE_UCS_2, CMAP_FORMAT_4);
        if (record != null)
        {
            CmapSubtableFormat4 subtable = CmapSubtableFormat4.read(reader, record);
            return new CmapUcs2(subtable.getCharMap(), subtable.getLanguage(), record.getPlatformId(), record.getEncodingId());
        }

        // WORDSNET-6283 Mac fonts in most cases contains encoding records for Mac and Unicode platforms.
        // Mac platform uses specific encodings which should be translated to Unicode. For example MacRomanEncoding.
        // Don't use it for now. Try to use Unicode platform.
        // kvk: Unicode encoding id = 1 should have special treatment for some Unicode ranges. Ignore it for now.
        // See https://developer.apple.com/fonts/ttrefman/rm06/Chap6name.html#ID.
        record = tryGetEncodingRecord(encodingRecords, PLATFORM_ID_UNICODE, CMAP_FORMAT_4);
        if (record != null)
        {
            // PDF validator do not like Unicode platform. Replace it with regular MS Unicode.
            CmapSubtableFormat4 subtable = CmapSubtableFormat4.read(reader, record);
            return new CmapUcs2(subtable.getCharMap(), subtable.getLanguage(), PLATFORM_ID_MICROSOFT, MICROSOFT_ENCODING_ID_UNICODE_UCS_2);
        }

        // WORDSNET-12916 Customer is using fonts (probably obsolete) with only format 2 cmap and PRC encoding.
        record = tryGetEncodingRecord(encodingRecords, PLATFORM_ID_MICROSOFT, 2);
        if (record != null)
        {
            CmapSubtableFormat2 subtable = CmapSubtableFormat2.read(reader, record);
            // Do not write format 2 into subset. Replace it with regular format 4 subtable with Unicode encoding.
            return new CmapUcs2(subtable.getCharMap(), subtable.getLanguage(), PLATFORM_ID_MICROSOFT, MICROSOFT_ENCODING_ID_UNICODE_UCS_2);
        }

        throw new IllegalStateException("Cannot find a required cmap encoding record.");
    }

    private static CmapEncodingRecord tryGetEncodingRecord(
        CmapEncodingRecord[] records, int platformId, int encodingId, int format)
    {
        for (CmapEncodingRecord record : records)
            if (record.getPlatformId() == platformId && record.getEncodingId() == encodingId && record.getFormat() == format)
                return record;

        return null;
    }

    private static CmapEncodingRecord tryGetEncodingRecord(CmapEncodingRecord[] records, int platformId, int format)
    {
        for (CmapEncodingRecord record : records)
            if (record.getPlatformId() == platformId && record.getFormat() == format)
                return record;

        return null;
    }

    /// <summary>
    /// Builds a <see cref="TTGlyphHashtable"/> from character map.
    /// </summary>
    public TTGlyphHashtable buildGlyphs(HorizontalMetrics hMetrics, VerticalMetrics vMetrics, int numGlyphs)
    {
        TTGlyphHashtable result = new TTGlyphHashtable(numGlyphs);

        for (int i = 0; i < numGlyphs; i++)
        {
            HorizontalMetric hMetric = hMetrics.getHMetric(i);

            TTGlyph glyph;
            if (vMetrics != null)
            {
                VerticalMetric vMetric = vMetrics.getVMetric(i);
                glyph = new TTGlyph(i, hMetric.getAdvanceWidth(), hMetric.getLeftSideBearing(), vMetric.AdvanceHeight, vMetric.TopSideBearing);
            }
            else
            {
                // Use advance width and left side bearing as advance height and top side bearing when the font contains no vertical metrics.
                glyph = new TTGlyph(i, hMetric.getAdvanceWidth(), hMetric.getLeftSideBearing(), hMetric.getAdvanceWidth(), hMetric.getLeftSideBearing());
            }
            result.addGlyph(glyph);
        }

        result.setMissingGlyph(result.getGlyphByIndex(0));
        Debug.assertTrue(result.getMissingGlyph() != null);

        // Most of the font has a mapping from 0xFFFF to missing glyph. Add it explicitly to uniformly process the
        // missing glyph for all fonts.
        result.addCharCodeMapping(0xFFFF, 0);

        for (int i = 0; i < CharMap.getCount(); i++)
        {
            int charCode = CharMap.getKey(i);
            int glyphIndex = CharMap.getByIndex(i);
            if (glyphIndex >= 0 && glyphIndex < numGlyphs)
                result.addCharCodeMapping(charCode, glyphIndex);
        }

        return result;
    }

    /// <summary>
    /// Creates a cmap that contains info for the specified characters and glyphs only.
    /// </summary>
    /// <param name="usedChars">
    /// Map of used characters. Key - character code, value - new glyph index.
    /// </param>
    public void subset(SortedIntegerListGeneric<Integer> usedChars)
    {
        CharMap = usedChars;
    }

    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        CmapSubtable[] subtables = buildSubtables();

        // Spec says:
        // The header entries must be sorted first by platform ID, then by platform-specific encoding ID, and then by
        // the language field in the corresponding subtable.
        //
        // Currently we write only one or two subtables which are sorted in BuildSubtables method.
        ArrayList<byte[]> subtablesData = new ArrayList<byte[]>();

        for (int i = 0; i < subtables.length; i++)
            msArrayList.add(subtablesData, subtables[i].writeToBytes());

        writer.writeInt16(0);  // version
        writer.writeInt16(subtables.length);  // tableCount

        int currentSubtabelOffset = 4 + 8 * subtables.length;
        for (int i = 0; i < subtables.length; i++)
        {
            // Write encoding table record.
            writer.writeInt16(subtables[i].getPlatformId());
            writer.writeInt16(subtables[i].getEncodingId());
            writer.writeUInt32((/*uint*/int)currentSubtabelOffset);
            currentSubtabelOffset += subtablesData.get(i).length;
        }

        for (int i = 0; i < subtables.length; i++)
            writer.writeBytes(subtablesData.get(i));
    }

    protected abstract CmapSubtable[] buildSubtables();

    /// <summary>
    /// True if MicrosoftSymbolEncoding is specified.
    /// </summary>
    public abstract boolean isSymbolEncoding();


    /// <summary>
    /// Reads and returns all encoding records used in the 'cmap'.
    /// Used for unit testing.
    /// </summary>
    static CmapEncodingRecord[] getEncodingRecords(BigEndianBinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        if (version != 0)
            throw new IllegalStateException("Unexpected cmap table version.");
        int numTables = (reader.readUInt16() & 0xFFFF);

        return readEncodingRecords(reader, numTables);
    }

    /// <summary>
    /// Sorted list of characters - glyph ids pairs.
    /// Key - int char code. Value - int glyph id.
    /// </summary>
    public SortedIntegerListGeneric<Integer> CharMap;
    protected /*final*/ int Language;

    public static final int CMAP_FORMAT_4 = 4;
    public static final int CMAP_FORMAT_12 = 12;

    static final int PLATFORM_ID_UNICODE = 0;
    static final int PLATFORM_ID_MACINTOSH = 1;
    static final int PLATFORM_ID_MICROSOFT = 3;

    static final int MICROSOFT_ENCODING_ID_SYMBOL = 0;
    static final int MICROSOFT_ENCODING_ID_UNICODE_UCS_2 = 1;
    static final int MICROSOFT_ENCODING_ID_PRC = 3;
    static final int MICROSOFT_ENCODING_ID_BIG_5 = 4;
    static final int MICROSOFT_ENCODING_ID_UNICODE_UCS_4 = 10;

    static final int MACINTOSH_ENCODING_ID_ROMAN = 0;
    static final int MACINTOSH_ENCODING_ID_JAPANESE = 1;
    static final int MACINTOSH_ENCODING_ID_TRADITIONAL_CHINESE = 2;
    static final int MACINTOSH_ENCODING_ID_KOREAN = 3;
    static final int MACINTOSH_ENCODING_ID_SIMPLIFIED_CHINESE = 25;

    static final int MICROSOFT_LANGUAGE_ID_ENGLISH_US = 0x0409;

    static final int MACINTOSH_LANGUAGE_ID_ENGLISH = 0;
}

