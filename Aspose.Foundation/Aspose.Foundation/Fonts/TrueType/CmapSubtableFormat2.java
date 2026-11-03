// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/09/2017 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 'cmap' format 2 subtable.
/// </summary>
class CmapSubtableFormat2 extends CmapSubtable
{
    public CmapSubtableFormat2(int platformId, int encodingId, int language, SortedIntegerListGeneric<Integer> charMap)
    {
    	super(platformId, encodingId, language, charMap);
	
    }

    public /*override*/ void write(BigEndianBinaryWriter writer)
    {
        // Currently this format is replaced with format 4 on writing. 
        throw new UnsupportedOperationException();
    }

    public static CmapSubtableFormat2 read(BigEndianBinaryReader reader, CmapEncodingRecord encodingRecord) throws Exception
    {
        SortedIntegerListGeneric<Integer> charMap = new SortedIntegerListGeneric<Integer>();
        TTEncoding encoding = TTEncoding.getEncoding(encodingRecord.getPlatformId(), encodingRecord.getEncodingId());

        reader.getBaseStream().setPosition(encodingRecord.getPosition());
        int format = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(format == 2);

        reader.readUInt16(); // length
        int language = (reader.readUInt16() & 0xFFFF);

        int[] subheaderKeys = new int[256];
        for (int i = 0; i < 256; i++)
            subheaderKeys[i] = (reader.readUInt16() & 0xFFFF);

        long subheaderStartPosition = reader.getBaseStream().getPosition();

        reader.readUInt16(); // subheader0FirstCode
        int subheader0EntryCount = (reader.readUInt16() & 0xFFFF);
        int subheader0IdDelta = reader.readInt16();
        int subheader0IdRangeOffset = (reader.readUInt16() & 0xFFFF);

        reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + (subheader0IdRangeOffset - 2));
        int[] subheader0Array = new int[subheader0EntryCount];
        for (int i = 0; i < subheader0EntryCount; i++)
            subheader0Array[i] = (reader.readUInt16() & 0xFFFF);

        for (int firstByte = 0; firstByte < 256; firstByte++)
        {
            int subheaderKey = subheaderKeys[firstByte];
            if (subheaderKey == 0)
            {
                int glyphId = subheader0Array[firstByte] + subheader0IdDelta;
                addGlyph(new byte[] { (byte)firstByte }, glyphId, charMap, encoding);
                continue;
            }

            reader.getBaseStream().setPosition(subheaderStartPosition + subheaderKey);
            int firstCode = (reader.readUInt16() & 0xFFFF);
            int entryCount = (reader.readUInt16() & 0xFFFF);
            int idDelta = reader.readInt16();
            int idRangeOffset = (reader.readUInt16() & 0xFFFF);

            if (entryCount == 0)
                continue;

            reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + (idRangeOffset - 2));
            for (int secondByte = firstCode; secondByte < firstCode + entryCount; secondByte++)
            {
                int id = (reader.readUInt16() & 0xFFFF);
                if (id == 0)
                    continue;

                int glyphId = id + idDelta;
                addGlyph(new byte[] { (byte)firstByte, (byte)secondByte }, glyphId, charMap, encoding);
            }
        }

        return new CmapSubtableFormat2(encodingRecord.getPlatformId(), encodingRecord.getEncodingId(), language, charMap);
    }

    private static void addGlyph(byte[] bytes, int glyphId, SortedIntegerListGeneric<Integer> charMap, TTEncoding encoding)
    {
        String uniStr = encoding.getString(bytes);
        int charCode = UnicodeUtil.convertToUtf32(uniStr, 0);

        if (!charMap.containsKey(charCode))
            charMap.add(charCode, glyphId);
    }
}

