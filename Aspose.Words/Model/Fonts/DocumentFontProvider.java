// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2012 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IDrFontProvider;
import com.aspose.DrFont;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.TTFont;
import com.aspose.StringUtil;
import com.aspose.StringToIntDictionary;


/// <summary>
/// Helps to resolve fonts. Uses fonts embedded into a document plus fonts from specified font sources.
/// </summary>
class DocumentFontProvider implements IDrFontProvider, Cloneable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    DocumentFontProvider(Document document)
    {
        mDocument = document;
    }

    /// <summary>
    /// Fetches the drawing font and tries to perform a simple font substitution.
    /// </summary>
    public DrFont fetchDrFont(String familyName, float sizePoints, /*FontStyle*/int style)
    {
        return fetchDrFont(familyName, sizePoints, style, style, false);
    }

    /// <summary>
    /// Fetches the drawing font and tries to perform a simple font substitution.
    /// </summary>
    public DrFont fetchDrFont(String familyName, float sizePoints, /*FontStyle*/int actualStyle, /*FontStyle*/int fontFaceStyle, boolean isVertical)
    {
        return new DrFont(sizePoints, actualStyle, fetchTTFont(familyName, fontFaceStyle), isVertical);
    }

    /// <summary>
    /// Fetches the drawing font and tries to perform a simple font substitution.
    /// </summary>
    public DrFont fetchDrFont(String familyName,
        float sizePoints,
        /*FontStyle*/int actualStyle,
        /*FontStyle*/int fontFaceStyle,
        boolean isVertical,
        boolean isSmallCaps,
        boolean isSuperscript,
        boolean isSubscript)
    {
        return new DrFont(sizePoints, actualStyle, fetchTTFont(familyName, fontFaceStyle), isVertical, isSmallCaps, isSuperscript, isSubscript);
    }

    /// <summary>
    /// Gets the specified font. Returns null if font is not available.
    /// </summary>
    public TTFont getTTFont(String familyName, /*FontStyle*/int style)
    {
        // FOSS: font substitution and external/system font resolution were removed.
        // Only fonts embedded into the document are resolved to real font data; every other
        // font name is left unresolved (callers fall back to the last-resort font via FetchTTFont).
        if (!StringUtil.hasChars(familyName))
            return null;

        registerRequestedFont(familyName, style);

        return getEmbeddedFont(getFontInfo(familyName), style);
    }

    /// <summary>
    /// Fetches the TrueType font and tries to perform a simple font substitution.
    /// </summary>
    public TTFont fetchTTFont(String familyName, /*FontStyle*/int style)
    {
        // FOSS: substitution rules were removed. Use the embedded font if present, otherwise
        // the last-resort font so callers always get non-null font data.
        return (getTTFont(familyName, style) != null ? getTTFont(familyName, style) : getSettings().getAnyFont());
    }

    /// <summary>
    /// Returns fallback font for the specified font and character code.
    /// Returns null if fallback font is not found.
    /// </summary>
    /// <param name="font">Font for which fallback font is searched.</param>
    /// <param name="charCode">Character code.</param>
    /// <param name="useCharacterReplacements">
    /// Specifies if character replacements should be used when checking fallback font for glyph availability.
    /// </param>
    public TTFont getFallbackFont(TTFont font, int charCode, boolean useCharacterReplacements)
    {
        // FOSS: font fallback resolution was removed along with the substitution engine.
        return null;
    }

    /// <summary>
    /// Returns <see cref="FontInfo"/> from the document's font info
    /// collection by its name <see cref="FontInfo.Name"/>.
    /// </summary>
    FontInfo getFontInfo(String familyName)
    {
        for (FontInfo info : mDocument.getFontInfos())
            if (StringUtil.equalsOrdinalIgnoreCase(info.getName(), familyName))
                return info;

        return null;
    }

    /// <summary>
    /// Returns resolved font name with checking part of font substitution rules required for HTML reader/writer.
    /// </summary>
    /// <remarks>
    /// If font name can be resolved as a font from document font infos or from external cache or embedded font
    /// then return resolved font family name.
    /// If font name can be resolved via substitution rules then return original family name.
    /// Else return null.
    /// </remarks>
    String getFontNameForHtml(String fontFamily)
    {
        // FOSS: substitution was removed. Only report a resolved name for fonts that are actually
        // embedded into the document; otherwise return null so the caller keeps the originally
        // requested font family name.
        final /*FontStyle*/int FAMILY_FONT_STYLE = FontStyle.REGULAR;

        TTFont resolvedFont = getTTFont(fontFamily, FAMILY_FONT_STYLE);
        if (resolvedFont == null)
            return null;

        // We should use name with which the font was imported from @font-face rule.
        if (resolvedFont.getData().isEmbedded())
            return fontFamily;

        // Note that by returning "FamilyName" instead of "fontFamily" we "normalize" the font name.
        // For example, both "arial" and "ARIAL" will be resolved to "Arial".
        return resolvedFont.getFamilyName();
    }

    /// <summary>
    /// Returns embedded <see cref="TTFont"/> from the specified <paramref name="info"/>
    /// with the specified <paramref name="style"/>. If there is no exactly font match found,
    /// it tries to get font with other styles (not italic, then not bold and regular at last).
    /// </summary>
    static TTFont getEmbeddedFont(FontInfo info, /*FontStyle*/int style)
    {
        // There may be no font info at all.
        if (info == null)
            return null;

        // First try exact style.
        TTFont font = info.getEmbeddedFontParsed(style);
        if (font != null)
            return font;

        // Then try non-italic style.
        /*FontStyle*/int tryStyle = (style & (~FontStyle.ITALIC));
        font = info.getEmbeddedFontParsed(tryStyle);
        if (font != null)
            return font;

        // Then try non-bold style.
        tryStyle = (style & (~FontStyle.BOLD));
        font = info.getEmbeddedFontParsed(tryStyle);
        if (font != null)
            return font;

        // Then try regular style.
        font = info.getEmbeddedFontParsed(FontStyle.REGULAR);
        if (font != null)
            return font;

        // At last try to get any style.
        return info.getEmbeddedFontParsedAnyStyle();
    }

    /// <summary>
    /// Stores all requested fonts (font familyName and style) into the requestedFontsCache collection.
    /// </summary>
    private void registerRequestedFont(String familyName, /*FontStyle*/int style)
    {
        // Only for debug purposes.
    }

    DocumentFontProvider deepClone()
    {
        return (DocumentFontProvider)memberwiseClone();
    }

    /// <summary>
    /// This collection contains requested fonts family names and styles.
    /// </summary>
    StringToIntDictionary getRequestedFontsCache() { return mRequestedFontsCache; }
    void setRequestedFontsCache(StringToIntDictionary value) { mRequestedFontsCache = value; }

    private FontSettings getSettings() { return mDocument.getEffectiveFontSettings(); }

    private /*final*/ Document mDocument;
    private StringToIntDictionary mRequestedFontsCache = new StringToIntDictionary();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

