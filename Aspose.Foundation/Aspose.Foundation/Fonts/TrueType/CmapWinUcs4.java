// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 'cmap' table for Windows platform and Unicode UCS-4 encoding.
/// </summary>
class CmapWinUcs4 extends Cmap
{
    public CmapWinUcs4(SortedIntegerListGeneric<Integer> charMap, int language)
    {
    	super(charMap, language);
	
    }

    protected /*override*/ CmapSubtable[] buildSubtables()
    {
        // Specification says that format 4 subtable should be included along with format 12 subtable
        // for backward compatibility.
        // Subtables should be sorted by platform id and then by encoding id.
        return new CmapSubtable[]
            {
                new CmapSubtableFormat4(PLATFORM_ID_MICROSOFT, MICROSOFT_ENCODING_ID_UNICODE_UCS_2, CharMap, Language),
                new CmapSubtableFormat12(PLATFORM_ID_MICROSOFT, MICROSOFT_ENCODING_ID_UNICODE_UCS_4, CharMap, Language)
            };
    }

    /// <summary>
    /// True if MicrosoftSymbolEncoding is specified.
    /// </summary>
    public /*override*/ boolean isSymbolEncoding() { return false; }
}

