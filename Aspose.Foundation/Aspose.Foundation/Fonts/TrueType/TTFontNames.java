// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/09/2009 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Collection of names of true type font stored in true type font Naming Table.
/// Collection contains only FamilyName, SubFamilyName, FullFontName and PostSciptName.
/// </summary>
class TTFontNames
{
    public static TTFontNames read(BigEndianBinaryReader reader) throws Exception
    {
        long tableStart = reader.getBaseStream().getPosition();
        /*ushort*/short format = reader.readUInt16();
        if (format != 0)
            throw new IllegalStateException("Unsupported Name table format.");

        /*ushort*/short count = reader.readUInt16();
        // Offset to start of string storage (from start of table).
        /*ushort*/short stringStorageOffset = reader.readUInt16();

        // Read NameRecords.
        TTFontNames result = new TTFontNames();
        ArrayList<TTFontNameRecord> familyNames = new ArrayList<TTFontNameRecord>();
        ArrayList<TTFontNameRecord> subfamilyNames = new ArrayList<TTFontNameRecord>();
        ArrayList<TTFontNameRecord> fullNames = new ArrayList<TTFontNameRecord>();
        ArrayList<TTFontNameRecord> postScriptNames = new ArrayList<TTFontNameRecord>();
        ArrayList<TTFontNameRecord> versions = new ArrayList<TTFontNameRecord>();
        result.mFontSpecificNames = new ArrayList<TTFontNameRecord>();

        for (int i = 0; i < (count & 0xFFFF); i++)
        {
            TTFontNameRecord record = TTFontNameRecord.read(reader, tableStart, (stringStorageOffset & 0xFFFF));
            if (record == null)
                continue;

            switch (record.getNameId())
            {
                case NameString.FAMILY_NAME:
                    msArrayList.add(familyNames, record);
                    break;
                case NameString.SUB_FAMILY_NAME:
                    msArrayList.add(subfamilyNames, record);
                    break;
                case NameString.FULL_FONT_NAME:
                    msArrayList.add(fullNames, record);
                    break;
                case NameString.POST_SCIPT_NAME:
                    msArrayList.add(postScriptNames, record);
                    break;
                case NameString.VERSION:
                    msArrayList.add(versions, record);
                    break;
                default:
                    break;
            }

            if (record.isFontSpecificName())
                msArrayList.add(result.mFontSpecificNames, record);
        }

        result.setFamilyName(selectSingleName(familyNames));
        result.setSubFamilyName(selectSingleName(subfamilyNames));
        result.setFullFontName(selectSingleName(fullNames));
        result.setPostScriptName(selectSingleName(postScriptNames));
        result.setVersionString(selectSingleName(versions));
        result.mFamilyNamesAllLanguages = selectNamesList(familyNames);
        result.mFullFontNamesAllLanguages = selectNamesList(fullNames);

        if (result.getFamilyName() == null || result.getSubFamilyName() == null || result.getFullFontName() == null)
            throw new IllegalStateException("Font do not have required names.");

        return result;
    }

    private static ArrayList<String> selectNamesList(ArrayList<TTFontNameRecord> names)
    {
        // WORDSNET-10040 Experiments shows that MW prefers Windows platform and ignores other platform entries if it is available.
        // Follow the MW logic and use only single platform names in following order: MS, Mac, Unicode.
        ArrayList<String> result = getPlatformNames(names, Cmap.PLATFORM_ID_MICROSOFT);
        if (result.size() == 0)
            result = getPlatformNames(names, Cmap.PLATFORM_ID_MACINTOSH);
        if (result.size() == 0)
            result = getPlatformNames(names, Cmap.PLATFORM_ID_UNICODE);

        return result;
    }

    private static ArrayList<String> getPlatformNames(ArrayList<TTFontNameRecord> names, int platform)
    {
        ArrayList<String> result = new ArrayList<String>();
        for (TTFontNameRecord record : names)
            if (record.getPlatformId() == platform)
                msArrayList.add(result, record.getName());

        return result;
    }

    private static String selectSingleName(ArrayList<TTFontNameRecord> names)
    {
        // First priority is platform: MS->Mac->Unicode.
        String result = selectSingleNamePlatform(names, Cmap.PLATFORM_ID_MICROSOFT);
        result = (result != null ? result : selectSingleNamePlatform(names, Cmap.PLATFORM_ID_MACINTOSH));
        result = (result != null ? result : selectSingleNamePlatform(names, Cmap.PLATFORM_ID_UNICODE));
        return result;
    }

    private static String selectSingleNamePlatform(ArrayList<TTFontNameRecord> names, int platform)
    {
        ArrayList<String> platformName = getPlatformNames(names, platform);
        if (platformName.size() == 0)
            return null;

        // Second priority is encoding. First try filer partially supported encodings.
        ArrayList<TTFontNameRecord> filteredNames = filterPartiallySupportedEncodings(names);

        // If there are no fully supported encoding, use all other.
        if (filteredNames.size() == 0)
            filteredNames = names;

        return selectSingleNameLanguage(filteredNames, platform);
    }

    private static ArrayList<TTFontNameRecord> filterPartiallySupportedEncodings(ArrayList<TTFontNameRecord> names)
    {
        ArrayList<TTFontNameRecord> result = new ArrayList<TTFontNameRecord>();
        for (TTFontNameRecord rec : names)
            if (isEncodingFullySupported(rec.getPlatformId(), rec.getEncodingId()))
                msArrayList.add(result, rec);

        return result;
    }

    private static boolean isEncodingFullySupported(int platform, int encoding)
    {
        // Currently only MS PRC encoding may return unreliable result. Other encoding are properly supported.
        return !(platform == Cmap.PLATFORM_ID_MICROSOFT && encoding == Cmap.MICROSOFT_ENCODING_ID_PRC);
    }

    private static String selectSingleNameLanguage(ArrayList<TTFontNameRecord> names, int platform)
    {
        if (names.size() == 0)
            return null;

        // Third priority is language. First try to get English name.
        String result = null;
        if (platform == Cmap.PLATFORM_ID_MICROSOFT)
            result = getNameSpecificLanguage(names, Cmap.MICROSOFT_LANGUAGE_ID_ENGLISH_US);
        else if (platform == Cmap.PLATFORM_ID_MACINTOSH)
            result = getNameSpecificLanguage(names, Cmap.MACINTOSH_LANGUAGE_ID_ENGLISH);

        // If there are no English name then use any name.
        if (result == null)
            result = names.get(0).getName();

        return result;
    }

    private static String getNameSpecificLanguage(ArrayList<TTFontNameRecord> names, int language)
    {
        for (TTFontNameRecord rec : names)
            if (rec.getLanguageId() == language)
                return rec.getName();

        return null;
    }

    public String getFontSpecificName(int nameId)
    {
        if (mFontSpecificNames.size() == 0 || !TTFontNameRecord.isFontSpecificNameId(nameId))
            return null;

        TTFontNameRecord record = selectFontSpecificRecord(
            nameId,
            Cmap.PLATFORM_ID_MICROSOFT,
            Cmap.MICROSOFT_LANGUAGE_ID_ENGLISH_US);
        if (record != null)
            return record.getName();
        record = selectFontSpecificRecord(
            nameId,
            Cmap.PLATFORM_ID_MACINTOSH,
            Cmap.MACINTOSH_LANGUAGE_ID_ENGLISH);
        if (record != null)
            return record.getName();
        record = selectFontSpecificRecord(nameId);
        return record != null
            ? record.getName()
            : null;
    }

    private TTFontNameRecord selectFontSpecificRecord(int nameId, int platform, int language)
    {
        for (TTFontNameRecord record : getFontSpecificNames())
            if ((int)record.getNameId() == nameId && record.getPlatformId() == platform && record.getLanguageId() == language)
                return record;
        return null;
    }

    private TTFontNameRecord selectFontSpecificRecord(int nameId)
    {
        for (TTFontNameRecord record : getFontSpecificNames())
            if ((int)record.getNameId() == nameId)
                return record;
        return null;
    }

    /// <summary>
    /// Font version string.
    /// </summary>
    public String getVersionString() { return mVersionString; }; private void setVersionString(String value) { mVersionString = value; };

    private String mVersionString;

    public String getFamilyName() { return mFamilyName; }; private void setFamilyName(String value) { mFamilyName = value; };

    private String mFamilyName;

    public String getSubFamilyName() { return mSubFamilyName; }; private void setSubFamilyName(String value) { mSubFamilyName = value; };

    private String mSubFamilyName;

    public String getFullFontName() { return mFullFontName; }; private void setFullFontName(String value) { mFullFontName = value; };

    private String mFullFontName;

    public String getPostScriptName() { return mPostScriptName; }; private void setPostScriptName(String value) { mPostScriptName = value; };

    private String mPostScriptName;

    public ArrayList<String> getFamilyNamesAllLanguages() { return mFamilyNamesAllLanguages; }

    public ArrayList<String> getFullFontNamesAllLanguages() { return mFullFontNamesAllLanguages; }

    public ArrayList<TTFontNameRecord> getFontSpecificNames() { return mFontSpecificNames; }

    private ArrayList<String> mFamilyNamesAllLanguages;
    private ArrayList<String> mFullFontNamesAllLanguages;
    private ArrayList<TTFontNameRecord> mFontSpecificNames;
}

