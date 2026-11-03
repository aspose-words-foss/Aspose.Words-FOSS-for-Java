// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2016 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Performs character replacement for control characters.
/// </summary>
/// <remarks>
/// For now only different space chars are handled here. They should have special treatment in layout and probably 
/// should be replaced there. But sometimes they are passed to rendering as is. This replacer make sure that
/// spaces are rendered properly.
/// </remarks>
class ControlCharacterReplacer extends TableCharacterReplacer
{
    public ControlCharacterReplacer()
    {
    	super(G_REPLACEMENTS);
	
    }

    private static /*final*/ IntToIntDictionary G_REPLACEMENTS;

    static/* ControlCharacterReplacer()*/
    {
        G_REPLACEMENTS = new IntToIntDictionary();

        // Spaces
        G_REPLACEMENTS.add(0x2000, 0x0020); // EN QUAD
        G_REPLACEMENTS.add(0x2001, 0x0020); // EM QUAD
        G_REPLACEMENTS.add(0x2002, 0x0020); // EN SPACE
        G_REPLACEMENTS.add(0x2003, 0x0020); // EM SPACE
        G_REPLACEMENTS.add(0x2004, 0x0020); // THREE-PER-EM SPACE
        G_REPLACEMENTS.add(0x2005, 0x0020); // FOUR-PER-EM SPACE
        G_REPLACEMENTS.add(0x2006, 0x0020); // SIX-PER-EM SPACE
        G_REPLACEMENTS.add(0x2007, 0x0020); // FIGURE SPACE
        G_REPLACEMENTS.add(0x2008, 0x0020); // PUNCTUATION SPACE
        G_REPLACEMENTS.add(0x2009, 0x0020); // THIN SPACE
        G_REPLACEMENTS.add(0x200A, 0x0020); // HAIR SPACE
    }
}

