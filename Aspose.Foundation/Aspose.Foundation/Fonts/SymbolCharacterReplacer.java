// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Performs character replacement according to Symbol codepage.
/// </summary>
/// <remarks>
/// Experiments shows that MS Word replaces characters from [0x20;0xFF] range with characters from 
/// Unicode Private Use Area ([0xF020;0xF0FF] range) and vice versa for symbolic fonts.
/// </remarks>
class SymbolCharacterReplacer extends TableCharacterReplacer
{
    public SymbolCharacterReplacer()
    {
    	super(G_REPLACEMENTS);
	
    }

    private static /*final*/ IntToIntDictionary G_REPLACEMENTS;

    static/* SymbolCharacterReplacer()*/
    {
        G_REPLACEMENTS = new IntToIntDictionary();

        for (int i = 0x20; i <= 0xFF; i++)
        {
            G_REPLACEMENTS.add(i, i + 0xF000);
            G_REPLACEMENTS.add(i + 0xF000, i);
        }
    }
}

