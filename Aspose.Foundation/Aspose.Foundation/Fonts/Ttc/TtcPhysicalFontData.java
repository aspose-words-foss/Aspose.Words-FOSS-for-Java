// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Physical font data for a font in TrueType Collection.
/// </summary>
public class TtcPhysicalFontData extends PhysicalFontData
{
    public TtcPhysicalFontData(IFontData fileData, int ttcIndex, String fullFontName)
    {
    	super(fileData);
	
        mTtcIndex = ttcIndex;
        mFullFontName = fullFontName;
    }

    public int getTtcIndex() { return mTtcIndex; };

    private  int mTtcIndex;

    public boolean getTtcIndexPresent() { return getTtcIndex() >= 0; }

    public String getFullFontName() { return mFullFontName; };

    private  String mFullFontName;

    public /*override*/ boolean isTtc() { return true; }
}

