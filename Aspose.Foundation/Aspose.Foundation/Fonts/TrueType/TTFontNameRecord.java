// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/09/2017 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a record in 'name' table.
/// </summary>
class TTFontNameRecord
{
    public static TTFontNameRecord read(BigEndianBinaryReader reader, long tableStart, int stringStorageOffset) throws Exception
    {
        int platformId = (reader.readUInt16() & 0xFFFF);
        int encodingId = (reader.readUInt16() & 0xFFFF);
        int languageId = (reader.readUInt16() & 0xFFFF);
        /*NameString*/int nameId = (/*NameString*/int)(reader.readUInt16() & 0xFFFF);
        // String length (in bytes).
        int length = (reader.readUInt16() & 0xFFFF);
        // String offset from start of storage area (in bytes).
        int stringOffset = (reader.readUInt16() & 0xFFFF);

        TTEncoding encoding = TTEncoding.getEncoding(platformId, encodingId);
        String name = null;
        if (encoding != null)
        {
            int savePos = (int)reader.getBaseStream().getPosition();
            reader.getBaseStream().setPosition(tableStart + stringStorageOffset + stringOffset);
            byte[] bytes = reader.readBytes(length);
            name = encoding.getString(bytes);
            reader.getBaseStream().setPosition(savePos);
        }

        if (!StringUtil.hasChars(name))
            return null;

        TTFontNameRecord result = new TTFontNameRecord();
        result.mPlatformId = platformId;
        result.mEncodingId = encodingId;
        result.mLanguageId = languageId;
        result.mNameId = nameId;
        result.mName = name;
        return result;
    }

    public int getPlatformId() { return mPlatformId; }

    public int getEncodingId() { return mEncodingId; }

    public int getLanguageId() { return mLanguageId; }

    public /*NameString*/int getNameId() { return mNameId; }

    public String getName() { return mName; }

    public boolean isFontSpecificName() { return isFontSpecificNameId((int)getNameId()); }

    public static boolean isFontSpecificNameId(int nameId)
    {
        return nameId >= 256 && nameId <= 32767;
    }

    private int mPlatformId;
    private int mEncodingId;
    private int mLanguageId;
    private /*NameString*/int mNameId;
    private String mName;
}

