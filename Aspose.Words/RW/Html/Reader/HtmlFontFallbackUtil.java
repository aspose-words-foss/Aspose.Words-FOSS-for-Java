// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/11/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IFontProvider;
import com.aspose.Debug;
import com.aspose.IntList;
import com.aspose.StringUtf32Enumerator;
import com.aspose.UnicodeUtil;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Provides methods to get fallback fonts required to render a text.
/// </summary>
class HtmlFontFallbackUtil
{
    HtmlFontFallbackUtil(IFontProvider fontProvider)
    {
        Debug.assertTrue(fontProvider != null);
        mFontProvider = fontProvider;
    }

    /// <summary>
    /// Gets fallback fonts required to render a text.
    /// </summary>
    HtmlFontFallbackRange[] getFontFallbackRanges(String text, Font font)
    {
        Debug.assertTrue(font != null);

        int[] missingCharacterIndexes = getIndexesOfCharactersWithoutGlyphs(text, font);

        return (missingCharacterIndexes.length == 0)
            // The fast path. No font changes are needed.
            ? new HtmlFontFallbackRange[] { new HtmlFontFallbackRange(text) }
            // The slow path. Find fallback fonts for each missing glyph.
            : splitIntoFontFallbackRanges(text, font, missingCharacterIndexes);
    }

    /// <summary>
    /// Gets indexes of characters that have no glyphs in the specified fonts and should be rendered with fallback fonts.
    /// </summary>
    private int[] getIndexesOfCharactersWithoutGlyphs(String text, Font font)
    {
        // We delay instatiation of the resulting list, because it is rarely needed (situations where a font contains
        // no glyph for a character are uncommon).
        IntList result = null;

        // The category here was chosen arbitrarily. We just need something to start with.
        /*CharacterCategory*/int lastCategory = CharacterCategory.ASCII;
        HtmlCachedFont cachedFont = getCachedFont(lastCategory, font);

        // Process the text character-by-character.
        StringUtf32Enumerator enumerator = new StringUtf32Enumerator(text);
        while (enumerator.hasNext())
        {
            int code = enumerator.next();
            /*CharacterCategory*/int category = getCharacterCategory(code);

            // The font changes only when the character category changes, so we can reuse the last font if the category
            // has not changed.
            if (category != lastCategory)
            {
                cachedFont = getCachedFont(category, font);
                lastCategory = category;
            }

            // Check glyph availability.
            if (!cachedFont.hasGlyphFor(code))
            {
                // Delayed instatiation of the resulting list.
                if (result == null)
                {
                    result = new IntList();
                }

                result.add(enumerator.getOffset());
            }
        }

        return (result == null)
            ? G_EMPTY_INDEXES
            : result.toArray();
    }

    /// <summary>
    /// Splits a text into ranges by fallback font required to render them.
    /// </summary>
    private HtmlFontFallbackRange[] splitIntoFontFallbackRanges(String text, Font font, int[] missingCharacterIndexes)
    {
        int lastUnprocessedIndex = 0;
        HtmlFontFallbackRangeBuilder rangeBuilder = new HtmlFontFallbackRangeBuilder();
        for (int missingCharacterIndex : missingCharacterIndexes)
        {
            Debug.assertTrue(missingCharacterIndex >= lastUnprocessedIndex);

            // Append a range of characters that do not require fallback (have glyphs in the font).
            if (missingCharacterIndex > lastUnprocessedIndex)
            {
                String rangeText = text.substring((lastUnprocessedIndex), (lastUnprocessedIndex) + (missingCharacterIndex - lastUnprocessedIndex));
                rangeBuilder.append(rangeText, null);
            }

            // Determine a fallback font required to render the current missing character.
            int missingCharacterCode = UnicodeUtil.convertToUtf32(text, missingCharacterIndex);
            /*CharacterCategory*/int category = getCharacterCategory(missingCharacterCode);
            HtmlCachedFont cachedFont = getCachedFont(category, font);
            // If the fallback font cannot be found, the fallback font name will be null
            // and font fallback will be disabled for the character.
            String fallbackFontName = cachedFont.getFallbackFontName(missingCharacterCode, mFontProvider);
            // Append the current missing character to fallback ranges.
            String missingCharacterText = UnicodeUtil.convertFromUtf32(missingCharacterCode);
            rangeBuilder.append(missingCharacterText, fallbackFontName);
            lastUnprocessedIndex = missingCharacterIndex + missingCharacterText.length();
        }
        // Append the last range of characters that do not require fallback. This range lasts from the last missing character
        // to the end of text.
        if (lastUnprocessedIndex < text.length())
        {
            rangeBuilder.append(text.substring(lastUnprocessedIndex), null);
        }

        return rangeBuilder.getRanges();
    }

    private static /*CharacterCategory*/int getCharacterCategory(int utf32Char)
    {
        // We should probably also consider character category hints here, but this would complicate the code.
        // It is unlikely that we will have a category hint, however, because this code is used mainly when building
        // a document from scratch.
        return (utf32Char <= 0xFFFF)
            ? WordUtil.getCharacterCategory((char)utf32Char)
            : CharacterCategory.OTHER;
    }

    private HtmlCachedFont getCachedFont(/*CharacterCategory*/int category, Font font)
    {
        String familyName;
        switch (category)
        {
            case CharacterCategory.ASCII:
                familyName = font.getNameAscii();
                break;
            case CharacterCategory.COMPLEX_SCRIPT:
                familyName = font.getNameBi();
                break;
            case CharacterCategory.FAR_EAST:
                familyName = font.getNameFarEast();
                break;
            case CharacterCategory.OTHER:
                familyName = font.getNameOther();
                break;
            default:
                Debug.fail("Unknown character category.");
                familyName = font.getNameOther();
                break;
        }

        HtmlCachedFont result = mCachedFonts.get(familyName);
        if (result == null)
        {
            result = new HtmlCachedFont(mFontProvider, familyName);
            mCachedFonts.add(familyName, result);
        }
        return result;
    }

    private /*final*/ IFontProvider mFontProvider;

    /// <summary>
    /// Caches information about availability of glyphs in each font family. Keys are font family names (strings).
    /// Values are instances of <see cref="HtmlCachedFont"/>.
    /// </summary>
    private /*final*/ StringToObjDictionary<HtmlCachedFont> mCachedFonts = new StringToObjDictionary<HtmlCachedFont>(false);

    private static /*final*/ int[] G_EMPTY_INDEXES = new int[0];
}

