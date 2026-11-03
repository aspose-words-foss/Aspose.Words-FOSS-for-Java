// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


class TtcOpenTypeReader extends OpenTypeReader
{
    public TtcOpenTypeReader(Stream stream, TtcPhysicalFontData data)
    {
    	super(stream);
	
        mData = data;
    }

    public /*override*/ boolean tryReadHeader() throws Exception
    {
        TtcReader ttcReader = new TtcReader(getBinaryReader());
        if (!ttcReader.tryReadHeader())
            return false;
        mFaceIndex =
            mData.getTtcIndexPresent()
                ? mData.getTtcIndex()
                : findTtcFontIndex(ttcReader, mData.getFullFontName());
        if (mFaceIndex < 0 || mFaceIndex >= ttcReader.getNumberOfFonts())
            return false;

        ttcReader.seekToFont(mFaceIndex);
        return super.tryReadHeader();
    }

    private int findTtcFontIndex(TtcReader reader, String fullFontName) throws Exception
    {
        for (int i = 0; i < reader.getNumberOfFonts(); i++)
        {
            reader.seekToFont(i);
            if (checkFontName(fullFontName))
                return i;
        }

        return -1;
    }

    private boolean checkFontName(String fontName) throws Exception
    {
        SfntReader reader = new SfntReader(getBaseStream());
        if (!reader.tryReadHeader())
            return false;
        if (!reader.getTableRecordEntries().containsKey(OpenTypeTableTag.NAME))
            return false;

        reader.seekToTable(OpenTypeTableTag.NAME);
        TTFontNames names = TTFontNames.read(getBinaryReader());

        for (String name : names.getFullFontNamesAllLanguages())
            if (StringUtil.equalsOrdinalIgnoreCase(fontName, name))
                return true;

        return false;
    }

    public /*override*/ PhysicalFontData getUpdatedFontData()
    {
        return new TtcPhysicalFontData(mData.getFileData(), mFaceIndex, mData.getFullFontName());
    }

    protected /*override*/ String getInvalidDataMessage() { return "The TTC font is invalid."; }

    private /*final*/ TtcPhysicalFontData mData;
    private int mFaceIndex;
}

