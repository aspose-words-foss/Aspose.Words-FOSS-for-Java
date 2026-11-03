// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 'cmap' table with platform-encoding which should contain format 4 subtable (Unicode UCS-2).
/// </summary>
class CmapUcs2 extends Cmap
{
    public CmapUcs2(SortedIntegerListGeneric<Integer> charMap, int language, int platformId, int encodingId)
    {
    	super(charMap, language);
	
        mPlatformId = platformId;
        mEncodingId = encodingId;
    }

    protected /*override*/ CmapSubtable[] buildSubtables()
    {
        return new CmapSubtable[]
            {
                new CmapSubtableFormat4(mPlatformId, mEncodingId, CharMap, Language)
            };
    }

    /// <summary>
    /// True if MicrosoftSymbolEncoding is specified.
    /// </summary>
    public /*override*/ boolean isSymbolEncoding() { return ((mPlatformId == PLATFORM_ID_MICROSOFT) && (mEncodingId == MICROSOFT_ENCODING_ID_SYMBOL)); }

    private /*final*/ int mPlatformId;
    private /*final*/ int mEncodingId;
}

