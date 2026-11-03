// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Drawing.FontStyle;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Helper class for building <see cref="TTFont"/> instances.
/// </summary>
public class TTFontBuilder
{
    public static TTFont read(PhysicalFontData fontData) throws Exception
    {
        // Register encodings support if required.
        EncodingUtil.registerEncodings();

        Stream stream = fontData.getFileData().openStream();
        try /*JAVA: was using*/
        {
            TTFontBuilder builder = new TTFontBuilder(OpenTypeReader.create(stream, fontData), fontData);
            return builder.readFontCore();
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Reads a TrueType/OpenType font from a file. TTC/Variation fonts are not handled.
    /// Throws if the font is not recognized as a valid true type font or if the font file does not exist.
    /// </summary>
    static TTFont readOpenType(String fileName) throws Exception
    {
        return read(new PhysicalFontData(new FileFontData(fileName)));
    }

    /// <summary>
    /// Reads a TrueType/OpenType font from a file. TTC/Variation fonts are not handled.
    /// Throws if the font is not recognized as a valid true type font or if the font file does not exist.
    /// </summary>
    static TTFont readTtc(String fileName, String fontName) throws Exception
    {
        return read(new TtcPhysicalFontData(new FileFontData(fileName), -1, fontName));
    }

    /// <summary>
    /// Reads a TrueType/OpenType font from a byte array. TTC/Variation fonts are not handled.
    /// Throws if the font is not recognized as a valid true type font.
    /// </summary>
    public static TTFont readOpenType(byte[] fontData) throws Exception
    {
        return read(new PhysicalFontData(new MemoryFontData(fontData)));
    }


    private TTFontBuilder(OpenTypeReader reader, PhysicalFontData fontData)
    {
        mReader = reader;
        mFontData = fontData;
    }

    private TTFont readFontCore() throws Exception
    {
        mReader.readHeader();
        TTFont font = new TTFont();
        // Potentially, there are cases when TTC font data comes without a font index (like when loading a previous
        // version of font search info cache).
        // Update font data to actual one after reading a header.
        PhysicalFontData updatedFontData = mReader.getUpdatedFontData();
        font.setPhysicalData(updatedFontData != null ? updatedFontData : mFontData);
        buildFont(font);
        return font;
    }

    private void buildFont(TTFont font) throws Exception
    {
        font.isCff(mReader.containsTable(OpenTypeTableTag.CFF));

        font.setEmHeight((mReader.getHead().UnitsPerEm & 0xFFFF));
        font.setXMin(mReader.getHead().XMin);
        font.setYMin(mReader.getHead().YMin);
        font.setXMax(mReader.getHead().XMax);
        font.setYMax(mReader.getHead().YMax);

        // Store required names in the object that we are building.
        setFontNames(font);

        // See description in the TTFont remarks.
        font.setTypoLineMeasurements(getTypoLineMeasurements());
        font.setWinLineMeasurements(getWinLineMeasurements());
        font.setUseTypoMetrics(mReader.getOs2().getUseTypoMetrics());
        font.setOfficeLineMeasurements(mReader.getOs2().getUseTypoMetrics()
                                          ? font.getTypoLineMeasurements()
                                          : font.getWinLineMeasurements());

        // Taken this from iTextSharp.
        font.setItalicAngle((float)(-Math.atan2(mReader.getHhea().CaretSlopeRun, mReader.getHhea().CaretSlopeRise) * 180f / Math.PI));

        font.setStrikeoutSize(mReader.getOs2().yStrikeoutSize);
        font.setStrikeoutPosition(mReader.getOs2().yStrikeoutPosition);
        font.setSubscriptSize(mReader.getOs2().ySubscriptYSize);
        font.setSubscriptOffset(mReader.getOs2().ySubscriptYOffset);
        font.setSuperscriptSize(mReader.getOs2().ySuperscriptYSize);
        font.setSuperscriptOffset(mReader.getOs2().ySuperscriptYOffset);
        font.setStyle(selectFontStyle(mReader.getOs2(), mReader.getHead(), font.getFamilyName()));
        font.setWeightClass((mReader.getOs2().usWeightClass & 0xFFFF));
        font.setCapHeight(mReader.getOs2().sCapHeight);
        font.setXHeight(mReader.getOs2().sxHeight);
        font.setAvgCharWidth(mReader.getOs2().xAvgCharWidth);
        font.setUnderlinePosition(mReader.getPost().UnderlinePosition);
        font.setUnderlineThickness(mReader.getPost().UnderlineThickness);
        font.setFsType(mReader.getOs2().fsType);
        font.setUnicodeRanges(mReader.getOs2().ulUnicodeRanges);
        font.setCodepageRanges(mReader.getOs2().ulCodePageRanges);
        font.isMonospaced(FontUtil.isMonospacedFont(font.getFamilyName()));

        // It seems that MS Word uses both these flags when decide to use or not symbol charset.
        font.isSymbolic(mReader.getCmap().isSymbolEncoding() || mReader.getOs2().ulCodePageRanges.isSymbolCharsetUsed());

        font.setGlyphs(mReader.getCmap().buildGlyphs(mReader.getHmtx(), mReader.getVmtx(), (mReader.getMaxp().NumGlyphs & 0xFFFF)));
        font.getGlyphs().setReplacers(getFontCharacterReplacers(font));
        processColoredGlyphs(font);

        font.isCjkMetrics(mReader.getOs2().ulCodePageRanges.isCjkMetrics());
    }

    private void setFontNames(TTFont font) throws Exception
    {
        font.setFamilyName(mReader.getName().getFamilyName());
        font.setSubFamilyName(mReader.getName().getSubFamilyName());
        font.setFullFontName(mReader.getName().getFullFontName());
        font.setVersionString(mReader.getName().getVersionString());

        // WORDSNET-25211 PostScriptName may not be presented in font file but is required for some renderers.
        // In this case use full font name instead of PostScript name.
        font.setPostscriptName(StringUtil.hasChars(mReader.getName().getPostScriptName())
            ? mReader.getName().getPostScriptName()
            : mReader.getName().getFullFontName());
    }

    private FontLineMeasurements getTypoLineMeasurements() throws Exception
    {
        return new FontLineMeasurements(
            mReader.getOs2().sTypoAscender,
            -mReader.getOs2().sTypoDescender,
            mReader.getOs2().sTypoAscender - mReader.getOs2().sTypoDescender + mReader.getOs2().sTypoLineGap);
    }

    private FontLineMeasurements getWinLineMeasurements() throws Exception
    {
        // These calculations are taken from http://www.microsoft.com/typography/otspec/recom.htm
        int externalLeading = Math.max(0,
            mReader.getHhea().LineGap - ((mReader.getOs2().usWinAscent + mReader.getOs2().usWinDescent) -
                                         (mReader.getHhea().Ascender - mReader.getHhea().Descender)));

        return new FontLineMeasurements(
            mReader.getOs2().usWinAscent,
            mReader.getOs2().usWinDescent,
            mReader.getOs2().usWinAscent + mReader.getOs2().usWinDescent + externalLeading);
    }

    static /*FontStyle*/int selectFontStyle(FontMetrics metrics, FontHeader header, String familyName)
    {
        return metrics.fsSelection == 0 ? header.getStyle() : metrics.getStyle();
    }

    private ArrayList<CharacterReplacerBase> getFontCharacterReplacers(TTFont font)
    {
        ArrayList<CharacterReplacerBase> result = new ArrayList<CharacterReplacerBase>();

        if (font.isSymbolic())
        {
            msArrayList.add(result, new SymbolCharacterReplacer());
            msArrayList.add(result, new Win1252PuaCharacterReplacer());
        }

        // Control chars
        msArrayList.add(result, new ControlCharacterReplacer());

        return result;
    }

    private void processColoredGlyphs(TTFont font) throws Exception
    {
        if (!mReader.containsTable(OpenTypeTableTag.COLR) || !mReader.containsTable(OpenTypeTableTag.CPAL))
            return;

        font.isColored(true);

        for (ColrBaseGlyphRecord glyphRecord : mReader.getColr().getGlyphRecords())
        {
            ArrayList<TTGlyphColoredLayer> layers = new ArrayList<TTGlyphColoredLayer>(glyphRecord.NumLayers);
            for (int i = 0; i < glyphRecord.NumLayers; i++)
            {
                ColrLayerRecord layerRecord = mReader.getColr().getLayerRecords()[glyphRecord.FirstLayerIndex + i];
                DrColor color = mReader.getCpal().getPaletteColors()[layerRecord.PaletteIndex];
                msArrayList.add(layers, new TTGlyphColoredLayer(layerRecord.GlyphId, color));
            }

            TTGlyph glyph = font.getGlyphs().getGlyphByIndex(glyphRecord.GlyphId);
            glyph.setColoredInfo(new TTGlyphColoredInfo(layers));
        }
    }

    private static boolean getAreGlyphWidthsEqual(TTFont font, int charCode1, int charCode2)
    {
        TTGlyph glyph1 = font.getGlyphs().getGlyphByCharCode(charCode1);
        TTGlyph glyph2 = font.getGlyphs().getGlyphByCharCode(charCode2);

        return glyph1 != null && glyph2 != null &&
               glyph1.getAdvanceWidth() == glyph2.getAdvanceWidth();
    }

    private /*final*/ OpenTypeReader mReader;
    private /*final*/ PhysicalFontData mFontData;
}

