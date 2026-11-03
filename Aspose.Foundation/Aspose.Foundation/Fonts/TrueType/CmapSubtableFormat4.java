// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 'cmap' format 4 subtable.
/// </summary>
class CmapSubtableFormat4 extends CmapSubtable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public CmapSubtableFormat4(int platformId, int encodingId, SortedIntegerListGeneric<Integer> charMap, int language)
    {
    	super(platformId, encodingId, language, filterSupplementaryPlanesChars(charMap));
	
    }

    private static SortedIntegerListGeneric<Integer> filterSupplementaryPlanesChars(SortedIntegerListGeneric<Integer> charMap)
    {
        SortedIntegerListGeneric<Integer> result = new SortedIntegerListGeneric<Integer>();
        for (int i = 0; i < charMap.getCount(); i++)
        {
            int charCode = charMap.getKey(i);

            // charMap is sorted so we could stop when find first supplementary plane char.
            if (UnicodeUtil.isSupplementaryPlanesCharacter(charCode))
                break;

            result.add(charCode, charMap.getByIndex(i));
        }
        return result;
    }

    /// <summary>
    /// Reads subtable from binary reader.
    /// </summary>
    public static CmapSubtableFormat4 read(BigEndianBinaryReader reader, CmapEncodingRecord encodingRecord) throws Exception
    {
        SortedIntegerListGeneric<Integer> charMap = new SortedIntegerListGeneric<Integer>();

        reader.getBaseStream().setPosition(encodingRecord.getPosition());
        int format = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(format == Cmap.CMAP_FORMAT_4);

        int length = (reader.readUInt16() & 0xFFFF);
        int language = (reader.readUInt16() & 0xFFFF);

        int segCountX2 = (reader.readUInt16() & 0xFFFF);
        reader.readUInt16(); // searchRange
        reader.readUInt16(); // entrySelector
        reader.readUInt16(); // rangeShift

        int segCount = segCountX2 / 2;

        int[] endCodes = readUInt16Array(reader, segCount);       //End characterCode for each segment, last=0xFFFF.
        reader.readUInt16();                                //Pad
        int[] startCodes = readUInt16Array(reader, segCount);     //Start character code for each segment.
        int[] idDeltas = readInt16Array(reader, segCount);        //Delta for all character codes in segment.
        int[] idRangeOffsets = readUInt16Array(reader, segCount); //Offsets into glyphIdArray or 0

        int tableEnd = (int)encodingRecord.getPosition() + length;
        int remainingBytes = tableEnd - (int)reader.getBaseStream().getPosition();
        // Glyph IDs array is located in the end of the subtable. Up to this moment the
        // position of pointer in the stream points to the beginning of this array.
        // Thus glyphIdCount can be calculated as (number of unread bytes in subtable)/(2 bytes per ID)
        // I'm not 100% sure this count is correct, but looks okay.
        int glyphIdCount = remainingBytes / 2;

        // WORDSNET-13043 Resilience. MingLiu font contains glyph ID indexes 22103 and 22104 while the glyphIdCount = 22103.
        // Meanwhile the font contains both glyph IDs and glyphs itself, so the most probably subtable's length value 
        // is incorrect.
        final int EXTRA_BYTES_NUMBER = 4;
        if (tableEnd + EXTRA_BYTES_NUMBER <= reader.getBaseStream().getLength())
            glyphIdCount += EXTRA_BYTES_NUMBER / 2;

        int[] glyphIds = readUInt16Array(reader, glyphIdCount);

        for (int segIdx = 0; segIdx < segCount; segIdx++)
        {
            // Loop through all character codes in the segment.
            for (int charCode = startCodes[segIdx]; charCode <= endCodes[segIdx]; charCode++)
            {
                int glyphIndex;

                // WORDSNET-9205, 12852 - The problem occurred because fonts cmap has invalid mapping for the latest charCode.
                // Due to TTF specification - For the search to terminate, the final endCode value must be 0xFFFF.
                // This segment need not contain any valid mappings. 
                // (It can just map the single character code 0xFFFF to missingGlyph).
                // To fix the problem just map 65535 char to the missing glyph.
                if (charCode == 65535)
                {
                    glyphIndex = 0;
                }
                else
                {
                    int idRangeOffset = idRangeOffsets[segIdx];
                    switch (idRangeOffset)
                    {
                        case 0:
                        {
                            // If the idRangeOffset is 0, the idDelta value is added directly to the
                            // character code offset (i.e. idDelta[i] + c) to get the corresponding glyph index.
                            // The idDelta arithmetic is modulo 65536.
                            glyphIndex = (charCode + idDeltas[segIdx]) & 0xFFFF;

                            // This is to fix the problem similar to 7531 (see comment below)
                            if (glyphIndex == 65535)
                            {
                                glyphIndex = 0;
                            }
                            break;
                        }
                        case 65535:
                        {
                            // WORDSNET-7531 RESILIENCY There seems to be this value in the file.
                            // It is for the last char and I think it is for the "missing glyph"
                            glyphIndex = 0;
                            break;
                        }
                        default:
                        {
                            // If the idRangeOffset value for the segment is not 0, the mapping of character codes
                            // relies on glyphIdArray.
                            // The character code offset from startCode is added to the idRangeOffset value.
                            // This sum is used as an offset from the current location within
                            // idRangeOffset itself to index out the correct glyphIdArray value.

                            // original C expression: 
                            // *(idRangeOffset[segIdx] / 2 +            this is idRangeOffset value, why divided?
                            //  (charCode - startCount[segIdx]) +       this is character code offset
                            //  &idRangeOffset[segIdx])                 current location within isRangeOffset itself

                            int charCodeOffset = charCode - startCodes[segIdx];

                            // Calculate index into the glyphId array.
                            int glyphIdIdx =
                                idRangeOffsets[segIdx] / 2          // the idRangeOffsetValue
                                + charCodeOffset                    // character code offset
                                - segCount                          // probably helps to come down from pointer arithmetic
                                + segIdx;                           // just use index instead of pointer arithmetic

                            // If the value obtained from the indexing operation is not 0 (which indicates missingGlyph),
                            // idDelta[i] is added to it to get the glyph index. The idDelta arithmetic is modulo 65536.
                            glyphIndex = glyphIds[glyphIdIdx];
                            if (glyphIndex != 0)
                                glyphIndex = (glyphIndex + idDeltas[segIdx]) & 0xFFFF;

                            break;
                        }
                    }
                }

                charMap.set(charCode, glyphIndex);
            }
        }

        return new CmapSubtableFormat4(encodingRecord.getPlatformId(), encodingRecord.getEncodingId(), charMap, language);
    }

    private static int[] readUInt16Array(BigEndianBinaryReader reader, int count) throws Exception
    {
        int[] result = new int[count];
        for (int i = 0; i < count; i++)
            result[i] = (reader.readUInt16() & 0xFFFF);
        return result;
    }

    private static int[] readInt16Array(BigEndianBinaryReader reader, int count) throws Exception
    {
        int[] result = new int[count];
        for (int i = 0; i < count; i++)
            result[i] = reader.readInt16();
        return result;
    }

    /// <summary>
    /// Writes subtable to binary writer.
    /// </summary>
    public /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        long tableStart = writer.getBaseStream().getPosition();

        writer.writeInt16(Cmap.CMAP_FORMAT_4);

        // Last segment should always map 0xFFFF char to 0 index.
        final int LAST_CHAR_CODE = 0xFFFF;
        final int LAST_GLYPH_INDEX = 0;

        // From the SPEC: The final endCode value must be 0xFFFF. This segment need not contain
        // any valid mappings. (It can just map the single character code 0xFFFF to missingGlyph).
        // However, the segment must be present.
        // So add this segment even if character 0xFFFF is not in usedChars map.
        boolean additionalSegmentNeeded = !getCharMap().containsKey(LAST_CHAR_CODE);

        // These calculations are copied from the TTF spec. I have no idea what they are really for.
        int segCount = getCharMap().getCount() + (additionalSegmentNeeded ? 1 : 0);
        int segCountX2 = segCount * 2;
        int searchRange = 2 << (int)Math.floor(Math.log(segCount) / Math.log(2.0));  // 2 ^ largest power of 2 <= segCount
        int entrySelector = (int)(Math.log((double)searchRange / 2.0) / Math.log(2.0));
        int rangeShift = 2 * segCount - searchRange;

        // This writes the table in a very simplistic way: one segment for each character.
        // We are using the following formula: If the idRangeOffset is 0, the idDelta value
        // is added directly to the character code offset (i.e. idDelta[i] + c) to get the 3
        // corresponding glyph index.
        int[] endCodes = new int[segCount];
        int[] startCodes = new int[segCount];
        int[] idDeltas = new int[segCount];
        int[] idRangeOffsets = new int[segCount];
        for (int i = 0; i < getCharMap().getCount(); i++)
        {
            int charCode = getCharMap().getKey(i);
            int newGlyphIndex = getCharMap().getByIndex(i);

            endCodes[i] = charCode;
            startCodes[i] = charCode;
            idDeltas[i] = newGlyphIndex - charCode;
        }

        // Last segment should always map 0xFFFF char to 0 index.
        if (additionalSegmentNeeded)
        {
            endCodes[segCount - 1] = LAST_CHAR_CODE;
            startCodes[segCount - 1] = LAST_CHAR_CODE;
            idDeltas[segCount - 1] = LAST_GLYPH_INDEX - LAST_CHAR_CODE;
        }

        int length =
            8 * 2 +
            segCountX2 * 4;

        writer.writeInt16(length);
        writer.writeInt16(getLanguage());

        writer.writeInt16(segCountX2);
        writer.writeInt16(searchRange);
        writer.writeInt16(entrySelector);
        writer.writeInt16(rangeShift);

        writeInt16Array(endCodes, writer);
        writer.writeInt16(0);
        writeInt16Array(startCodes, writer);
        writeInt16Array(idDeltas, writer);
        writeInt16Array(idRangeOffsets, writer);

        Debug.assertTrue((writer.getBaseStream().getPosition() - tableStart) == length);
    }

    private static void writeInt16Array(int[] data, BigEndianBinaryWriter writer) throws Exception
    {
        for (int v : data)
            writer.writeInt16(v);
    }
}

