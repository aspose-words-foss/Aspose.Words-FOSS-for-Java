// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Performs character replacement according to Windows 1252 codepage.
/// </summary>
/// <remarks>
/// MW seems supports some obsolete non-Unicode fonts by mapping Unicode codepoint to Win1252+PUA.
/// </remarks>
class Win1252PuaCharacterReplacer extends TableCharacterReplacer
{
    public Win1252PuaCharacterReplacer()
    {
    	super(G_REPLACEMENTS);
	
    }

    private static /*final*/ IntToIntDictionary G_REPLACEMENTS;

    static/* Win1252PuaCharacterReplacer()*/
    {
        G_REPLACEMENTS = new IntToIntDictionary();

        // See http://en.wikipedia.org/wiki/Windows-1252 for codepage to Unicode table.
        // Character code and Unicode value differs only for [0x80;0x9f] range.
        G_REPLACEMENTS.add(0x20AC, 0xF080);
        G_REPLACEMENTS.add(0x201A, 0xF082);
        G_REPLACEMENTS.add(0x0192, 0xF083);
        G_REPLACEMENTS.add(0x201E, 0xF084);
        G_REPLACEMENTS.add(0x2026, 0xF085);
        G_REPLACEMENTS.add(0x2020, 0xF086);
        G_REPLACEMENTS.add(0x2021, 0xF087);
        G_REPLACEMENTS.add(0x02C6, 0xF088);
        G_REPLACEMENTS.add(0x2030, 0xF089);
        G_REPLACEMENTS.add(0x0160, 0xF08A);
        G_REPLACEMENTS.add(0x2039, 0xF08B);
        G_REPLACEMENTS.add(0x0152, 0xF08C);
        G_REPLACEMENTS.add(0x017D, 0xF08E);
        G_REPLACEMENTS.add(0X2018, 0xF091);
        G_REPLACEMENTS.add(0x2019, 0xF092);
        G_REPLACEMENTS.add(0x201C, 0xF093);
        G_REPLACEMENTS.add(0x201D, 0xF094);
        G_REPLACEMENTS.add(0x2022, 0xF095);
        G_REPLACEMENTS.add(0x2013, 0xF096);
        G_REPLACEMENTS.add(0x2014, 0xF097);
        G_REPLACEMENTS.add(0x02DC, 0xF098);
        G_REPLACEMENTS.add(0x2122, 0xF099);
        G_REPLACEMENTS.add(0x0161, 0xF09A);
        G_REPLACEMENTS.add(0x203A, 0xF09B);
        G_REPLACEMENTS.add(0x0153, 0xF09C);
        G_REPLACEMENTS.add(0x017E, 0xF09E);
        G_REPLACEMENTS.add(0x0178, 0xF09F);
    }
}

