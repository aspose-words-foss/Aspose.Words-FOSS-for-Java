// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// A map of character codes into TTGlyph objects.
/// </summary>
public class TTGlyphHashtable
{
    public TTGlyphHashtable(int numGlyphs)
    {
        mCharMap = new IntToObjDictionary<TTGlyph>(numGlyphs);
        mGlyphs = new IntToObjDictionary<TTGlyph>(numGlyphs);
    }

    /// <summary>
    /// Gets a glyph by a charCode.
    /// </summary>
    public TTGlyph getGlyphByCharCode(int charCode)
    {
        // First try to find glyph for specified charCode.
        TTGlyph glyph = getCharMap().get(charCode);
        if (glyph != null)
            return glyph;

        // If there is no glyph for the specified charCode then try to find glyph for the charCode replacements.
        IntList characters = getCharacterReplacements(charCode);
        for (int i = 0; i < characters.getCount(); i++)
        {
            glyph = getCharMap().get(characters.get(i));
            if (glyph != null)
                return glyph;
        }

        return null;
    }

    /// <summary>
    /// Returns a glyph for the character code.
    /// If the glyph is not found, returns missing glyph.
    /// </summary>
    public TTGlyph fetchGlyphByCharCode(int charCode)
    {
        TTGlyph glyph = getGlyphByCharCode(charCode);
        if (glyph != null)
            return glyph;

        return getMissingGlyph();
    }

    public int charCodeToGlyphId(int charCode)
    {
        return fetchGlyphByCharCode(charCode).getGlyphIndex();
    }

    /// <summary>
    /// Determines if glyph is available for specified charCode.
    /// </summary>
    public boolean containsCharCode(int charCode)
    {
        return containsCharCode(charCode, true);
    }

    /// <summary>
    /// Determines if glyph is available for specified charCode.
    /// </summary>
    /// <param name="charCode">Character code.</param>
    /// <param name="useCharacterReplacements">
    /// Specifies if character replacements should be used during glyph lookup.
    /// </param>
    public boolean containsCharCode(int charCode, boolean useCharacterReplacements)
    {
        TTGlyph glyph = (useCharacterReplacements)
            ? getGlyphByCharCode(charCode)
            : getCharMap().get(charCode);
        return glyph != null;
    }

    private IntList getCharacterReplacements(int c)
    {
        IntList replacements = new IntList();
        for (CharacterReplacerBase replacer : mReplacers)
            replacer.replace(c, replacements);

        return replacements;
    }

    /// <summary>
    /// Returns a glyph by its index.
    /// </summary>
    public TTGlyph getGlyphByIndex(int index)
    {
        return getGlyphs().get(index);
    }

    /// <summary>
    /// Returns a glyph by its index.
    /// If the glyph is not found, returns missing glyph.
    /// </summary>
    public TTGlyph fetchGlyphByIndex(int index)
    {
        TTGlyph result = getGlyphByIndex(index);
        return result != null ? result : getMissingGlyph();
    }

    /// <summary>
    /// Adds glyph to the table.
    /// </summary>
    public void addGlyph(TTGlyph glyph)
    {
        getGlyphs().set(glyph.getGlyphIndex(), glyph);
    }

    /// <summary>
    /// Adds char code mapping.
    /// </summary>
    public void addCharCodeMapping(int charCode, int glyphIndex)
    {
        TTGlyph glyph = getGlyphs().get(glyphIndex);

        getCharMap().set(charCode, glyph);

        // Use first charcode assigned for the glyph.
        if (glyph.getCharCode() == 0)
            glyph.setCharCode(charCode);
    }

    /// <summary>
    /// This is the glyph that is defined in the font with the glyph index 0.
    /// </summary>
    public TTGlyph getMissingGlyph() { return mMissingGlyph; }; public void setMissingGlyph(TTGlyph value) { mMissingGlyph = value; };

    private TTGlyph mMissingGlyph;

    /// <summary>
    /// Glyphs by index.
    /// </summary>
    public IntToObjDictionary<TTGlyph> getGlyphs() { return mGlyphs; };

    private IntToObjDictionary<TTGlyph> mGlyphs;

    /// <summary>
    /// Character map. Key - character code, value - glyph.
    /// </summary>
    public IntToObjDictionary<TTGlyph> getCharMap() { return mCharMap; };

    private IntToObjDictionary<TTGlyph> mCharMap;

    /// <summary>
    /// List of character replacers to use with this font.
    /// </summary>
    ArrayList<CharacterReplacerBase> getReplacers() { return mReplacers; }
    void setReplacers(ArrayList<CharacterReplacerBase> value) { mReplacers = value; }

    private ArrayList<CharacterReplacerBase> mReplacers = new ArrayList<CharacterReplacerBase>();
}

