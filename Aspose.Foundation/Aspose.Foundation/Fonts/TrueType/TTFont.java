// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Contains information about a true type font.
/// </summary>
/// <remarks>
/// Each OpenType font contains two sets of line measurements (metrics) relevant to MW: typographic and windows. Spec says that
/// typographic metrics should be preferred over the windows metrics. But MW relies on the UseTypeMetrics flag for TrueType fonts.
/// For OpenType(CFF) font MW13 and older used only win metrics. MW16 starts to use UseTypeMetrics flag when laying-out text in GUI.
/// But when printing or saving to PDF windows metrics are still used.
///
/// <see cref="WinLineMeasurements"/> represents windows metrics. <see cref="TypoLineMeasurements"/> represents typographic metrics.
/// <see cref="OfficeLineMeasurements"/> represents metrics used by Office. For OpenType(CFF) font it is always windows metrics for now.
/// </remarks>
public class TTFont
{
    /// <summary>
    /// Up to four fonts can share the Font Family name, forming a font style linking group
    /// (regular, italic, bold, bold italic - as defined by OS/2.fsSelection bit settings).
    /// </summary>
    public String getFamilyName() { return mFamilyName; }; public void setFamilyName(String value) { mFamilyName = value; };

    private String mFamilyName;

    /// <summary>
    /// The Font Subfamily name distinguishes the font in a group with the same Font Family name (name ID 1).
    /// This is assumed to address style (italic, oblique) and weight (light, bold, black, etc.).
    /// A font with no particular differences in weight or style (e.g. medium weight, not italic and fsSelection bit 6 set) should have the string "Regular" stored in this position.
    /// </summary>
    public String getSubFamilyName() { return mSubFamilyName; }; public void setSubFamilyName(String value) { mSubFamilyName = value; };

    private String mSubFamilyName;

    /// <summary>
    /// This should be a combination of strings 1 and 2. Exception: if the font is "Regular" as indicated
    /// in string 2, then use only the family name contained in string 1.
    /// An exception to the above definition of Full font name is for Microsoft platform strings
    /// for CFF OpenType fonts: in this case, the Full font name string must be identical to the PostScript
    /// FontName in the CFF Name INDEX.
    /// </summary>
    public String getFullFontName() { return mFullFontName; }; public void setFullFontName(String value) { mFullFontName = value; };

    private String mFullFontName;

    /// <summary>
    /// Specifies a string which is used to invoke a PostScript language font that corresponds to this
    /// OpenType font.
    /// </summary>
    public String getPostscriptName() { return mPostscriptName; }; public void setPostscriptName(String value) { mPostscriptName = value; };

    private String mPostscriptName;

    public String getVersionString() { return mVersionString; }; public void setVersionString(String value) { mVersionString = value; };

    private String mVersionString;

    /// <summary>
    /// Gets the full font file name if the font was read from a file.
    /// Gets a generated font file name if the font was read from memory.
    /// </summary>
    public String getFileName()
    {
        if (getPhysicalData() == null)
            return null;

        IFileFontData fileFontData = ms.as(getPhysicalData().getFileData(), IFileFontData.class);
        return (fileFontData != null)
            ? fileFontData.getFileName()
            : createFileNameForEmbeddedFont();
    }

    /// <summary>
    /// Map of char codes to TTGlyph objects.
    /// </summary>
    public TTGlyphHashtable getGlyphs() { return mGlyphs; }; public void setGlyphs(TTGlyphHashtable value) { mGlyphs = value; };

    private TTGlyphHashtable mGlyphs;

    /// <summary>
    /// Gets the font style.
    /// </summary>
    public /*FontStyle*/int getStyle() { return mStyle; }; public void setStyle(/*FontStyle*/int value) { mStyle = value; };

    private /*FontStyle*/int mStyle;

    public boolean isBold() { return ((getStyle() & FontStyle.BOLD) != 0); }

    public boolean isItalic() { return ((getStyle() & FontStyle.ITALIC) != 0); }

    /// <summary>
    /// Font weight class.
    /// </summary>
    /// <remarks>
    /// https://learn.microsoft.com/en-us/typography/opentype/spec/os2#wtc
    /// </remarks>
    public int getWeightClass() { return mWeightClass; }; public void setWeightClass(int value) { mWeightClass = value; };

    private int mWeightClass;

    /// <summary>
    /// Gets the height, in font design units, of the em square for the specified style.
    /// Corresponds to FontFamily.GetEmHeight.
    /// </summary>
    public int getEmHeight() { return mEmHeight; }; public void setEmHeight(int value) { mEmHeight = value; };

    private int mEmHeight;

    /// <summary>
    /// Returns the ascent, in design units. Corresponds to FontFamily.GetCellAscent.
    /// </summary>
    public int getAscent() { return getOfficeLineMeasurements().getAscent(); }

    /// <summary>
    /// Returns the descent, in design units. Corresponds to FontFamily.GetCellDescent.
    /// </summary>
    public int getDescent() { return getOfficeLineMeasurements().getDescent(); }

    /// <summary>
    /// Gets Ascent + Descent in design units.
    /// </summary>
    public int getCellHeight() { return getAscent() + getDescent(); }

    /// <summary>
    /// Returns the line spacing in design units. Corresponds to FontFamily.GetLineSpacing.
    /// The line spacing is the vertical distance between the base lines of two consecutive lines of text.
    /// </summary>
    public int getLineSpacing() { return getOfficeLineMeasurements().getLineSpacing(); }

    /// <summary>
    /// Width of the strikeout stroke in font design units.
    /// </summary>
    public int getStrikeoutSize() { return mStrikeoutSize; }; public void setStrikeoutSize(int value) { mStrikeoutSize = value; };

    private int mStrikeoutSize;

    /// <summary>
    /// The recommended horizontal size in font design units for superscripts for this font.
    /// </summary>
    public int getSuperscriptSize() { return mSuperscriptSize; }; public void setSuperscriptSize(int value) { mSuperscriptSize = value; };

    private int mSuperscriptSize;

    /// <summary>
    /// The recommended vertical offset in font design units from the baseline for superscripts for this font.
    /// </summary>
    public int getSuperscriptOffset() { return mSuperscriptOffset; }; public void setSuperscriptOffset(int value) { mSuperscriptOffset = value; };

    private int mSuperscriptOffset;

    /// <summary>
    /// The recommended horizontal size in font design units for subscripts for this font.
    /// </summary>
    public int getSubscriptSize() { return mSubscriptSize; }; public void setSubscriptSize(int value) { mSubscriptSize = value; };

    private int mSubscriptSize;

    /// <summary>
    /// The recommended vertical offset in font design units from the baseline for subscripts for this font.
    /// </summary>
    public int getSubscriptOffset() { return mSubscriptOffset; }; public void setSubscriptOffset(int value) { mSubscriptOffset = value; };

    private int mSubscriptOffset;

    /// <summary>
    /// The position of the top of the strikeout stroke relative to the baseline in font design units.
    /// </summary>
    public int getStrikeoutPosition() { return mStrikeoutPosition; }; public void setStrikeoutPosition(int value) { mStrikeoutPosition = value; };

    private int mStrikeoutPosition;

    /// <summary>
    /// The distance between the baseline and the approximate height of
    /// non-ascending lowercase letters measured in font design units.
    /// </summary>
    public int getXHeight() { return mXHeight; }; public void setXHeight(int value) { mXHeight = value; };

    private int mXHeight;

    /// <summary>
    /// Bounding box in design units.
    /// </summary>
    public int getXMin() { return mXMin; }; public void setXMin(int value) { mXMin = value; };

    private int mXMin;

    /// <summary>
    /// Bounding box in design units.
    /// </summary>
    public int getYMin() { return mYMin; }; public void setYMin(int value) { mYMin = value; };

    private int mYMin;

    /// <summary>
    /// Bounding box in design units.
    /// </summary>
    public int getXMax() { return mXMax; }; public void setXMax(int value) { mXMax = value; };

    private int mXMax;

    /// <summary>
    /// Bounding box in design units.
    /// </summary>
    public int getYMax() { return mYMax; }; public void setYMax(int value) { mYMax = value; };

    private int mYMax;

    public int getCapHeight() { return mCapHeight; }; public void setCapHeight(int value) { mCapHeight = value; };

    private int mCapHeight;

    public int getAvgCharWidth() { return mAvgCharWidth; }; public void setAvgCharWidth(int value) { mAvgCharWidth = value; };

    private int mAvgCharWidth;

    /// <summary>
    /// In degrees I think. Needed to pass to PDF document.
    /// </summary>
    public float getItalicAngle() { return mItalicAngle; }; public void setItalicAngle(float value) { mItalicAngle = value; };

    private float mItalicAngle;

    /// <summary>
    /// Returns char advance width from the font.
    /// </summary>
    /// <remarks>
    /// This method should not be used to get the real char advance. DrFont may apply
    /// some modifications to these values. DrFont.GetCharWidthPoints() method should be used instead.
    /// </remarks>
    int getCharAdvanceWidthDesignUnits(int c)
    {
        return getGlyphs().fetchGlyphByCharCode(c).getAdvanceWidth();
    }

    /// <summary>
    /// Returns char advance height from the font.
    /// </summary>
    /// <remarks>
    /// This method should not be used to get the real char advance. DrFont may apply
    /// some modifications to these values. DrFont.GetCharWidthPoints() method should be used instead.
    /// </remarks>
    int getCharAdvanceHeightDesignUnits(int c)
    {
        return getGlyphs().fetchGlyphByCharCode(c).getAdvanceHeight();
    }

    /// <summary>
    /// Converts the value in design units into points, given the font size in points.
    /// </summary>
    public float designUnitsToPoints(float designUnits, float fontSize)
    {
        float factor = getEmHeight() / fontSize;
        return designUnits / factor;
    }

    /// <summary>
    /// Gets or set  Preview and Print embedding restriction for a font usage.
    /// TODO AN. There are few levels of restriction in OTF fonts usage described e.g. in XPS 1.0, 2.1.7.2.
    /// Right now the only Preview and Print embedding is supported. The support for all other levels should be
    /// implemented later.
    /// </summary>
    public boolean isPrintPreview() { return getFsType().getPermissions() == FontFsTypePermissions.PRINT_AND_PREVIEW; }

    /// <summary>
    /// Gets fsType value of "OS/2" table (font embedding licensing rights).
    /// </summary>
    public FontFsType getFsType() { return mFsType; }; public void setFsType(FontFsType value) { mFsType = value; };

    private FontFsType mFsType;

    /// <summary>
    /// True if the font file contains CFF table.
    /// When the font has a CFF table, it means it is an OpenType font based on PostScript glyph outlines.
    /// When this is false, then it is either a TrueType font or an OpenType font based on TrueType glyph outlines.
    /// </summary>
    public boolean isCff() { return mIsCff; }; public void isCff(boolean value) { mIsCff = value; };

    private boolean mIsCff;

    /// <summary>
    /// Font data.
    /// </summary>
    public IFontData getData() { return getPhysicalData().getFileData(); }

    /// <summary>
    /// Font data.
    /// </summary>
    public PhysicalFontData getPhysicalData() { return mPhysicalData; }; public void setPhysicalData(PhysicalFontData value) { mPhysicalData = value; };

    private PhysicalFontData mPhysicalData;

    /// <summary>
    /// True if font is symbolic.
    /// </summary>
    public boolean isSymbolic() { return mIsSymbolic; }; public void isSymbolic(boolean value) { mIsSymbolic = value; };

    private boolean mIsSymbolic;

    /// <summary>
    /// True if font is monospaced.
    /// </summary>
    public boolean isMonospaced() { return mIsMonospaced; }; public void isMonospaced(boolean value) { mIsMonospaced = value; };

    private boolean mIsMonospaced;

    /// <summary>
    /// True if font data is in TTC format.
    /// </summary>
    boolean isTtc() { return getPhysicalData().isTtc(); }

    /// <summary>
    /// Returns a key for the font cache table.
    /// Uses <see cref="FullFontName"/>, <see cref="FamilyName"/> and <see cref="Style"/>
    /// as parameters for key generation. If it is needed to use more parameters,
    /// use <see cref="BuildFontKey(string[])"/> method.
    /// </summary>
    public String getTtFontKey()
    {
        if (!StringUtil.hasChars(mTtFontKey))
            mTtFontKey = buildFontKey(new String[] { getFullFontName(), getFamilyName(), FormatterPal.intToStr((int)getStyle()) });

        return mTtFontKey;
    }

    /// <summary>
    /// True if this font should adjust metrics according to MW CJK handling.
    /// </summary>
    public boolean isCjkMetrics() { return mIsCjkMetrics; }; public void isCjkMetrics(boolean value) { mIsCjkMetrics = value; };

    private boolean mIsCjkMetrics;


    /// <summary>
    /// True if the font is embedded.
    /// </summary>
    public boolean isEmbedded() { return getData().isEmbedded(); }

    /// <summary>
    /// Windows line measurements.
    /// </summary>
    public FontLineMeasurements getWinLineMeasurements() { return mWinLineMeasurements; }; public void setWinLineMeasurements(FontLineMeasurements value) { mWinLineMeasurements = value; };

    private FontLineMeasurements mWinLineMeasurements;

    /// <summary>
    /// Typographic line measurements.
    /// </summary>
    public FontLineMeasurements getTypoLineMeasurements() { return mTypoLineMeasurements; }; public void setTypoLineMeasurements(FontLineMeasurements value) { mTypoLineMeasurements = value; };

    private FontLineMeasurements mTypoLineMeasurements;

    /// <summary>
    /// Line measurements used by Office.
    /// </summary>
    public FontLineMeasurements getOfficeLineMeasurements() { return mOfficeLineMeasurements; }; public void setOfficeLineMeasurements(FontLineMeasurements value) { mOfficeLineMeasurements = value; };

    private FontLineMeasurements mOfficeLineMeasurements;

    /// <summary>
    /// Flag from the font data.
    /// </summary>
    /// <remarks>
    /// See class remarks for more info.
    /// </remarks>
    public boolean getUseTypoMetrics() { return mUseTypoMetrics; }; public void setUseTypoMetrics(boolean value) { mUseTypoMetrics = value; };

    private boolean mUseTypoMetrics;

    /// <summary>
    /// Supported unicode ranges.
    /// </summary>
    public FontUnicodeRanges getUnicodeRanges() { return mUnicodeRanges; }; public void setUnicodeRanges(FontUnicodeRanges value) { mUnicodeRanges = value; };

    private FontUnicodeRanges mUnicodeRanges;

    /// <summary>
    /// Supported codepage ranges.
    /// </summary>
    public FontCodepageRanges getCodepageRanges() { return mCodepageRanges; }; public void setCodepageRanges(FontCodepageRanges value) { mCodepageRanges = value; };

    private FontCodepageRanges mCodepageRanges;

    /// <summary>
    /// Gets or sets the underline position.
    /// </summary>
    public short getUnderlinePosition() { return mUnderlinePosition; }; public void setUnderlinePosition(short value) { mUnderlinePosition = value; };

    private short mUnderlinePosition;

    /// <summary>
    /// Gets or sets the underline thickness.
    /// </summary>
    public short getUnderlineThickness() { return mUnderlineThickness; }; public void setUnderlineThickness(short value) { mUnderlineThickness = value; };

    private short mUnderlineThickness;

    /// <summary>
    /// True if font contains colored glyphs.
    /// </summary>
    public boolean isColored() { return mIsColored; }; public void isColored(boolean value) { mIsColored = value; };

    private boolean mIsColored;

    /// <summary>
    /// Generates file name for embedded fonts.
    /// </summary>
    private String createFileNameForEmbeddedFont()
    {
        // This code copied from XpsContextBase.GetFontResourceName method.
        // FullFontName or combination of FamilyName and Style could be used,
        // but there is no verification that these fields may act as valid file name.
        byte[] guidBytes = new byte[16];
        ArrayUtil.writeUInt32ToByteArray(getFamilyName().hashCode(), guidBytes, 0);
        ArrayUtil.writeUInt32ToByteArray((int)getStyle(), guidBytes, 4);
        ArrayUtil.writeUInt32ToByteArray(getFullFontName().hashCode(), guidBytes, 8);
        Guid guid = new Guid(guidBytes);
        String guidName = guid.toString("D");
        String ext = isCff() ? "otf" : "ttf";
        return msString.format("{0}.{1}", guidName, ext);
    }

    /// <summary>
    /// Builds font key from specified string parameters.
    /// Use this method if you need to build custom font key, if not use <see cref="TtFontKey"/> property instead.
    /// </summary>
    public static String buildFontKey(String[] keyParams)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keyParams.length; i++)
        {
            msStringBuilder.append(builder, keyParams[i]);
            if (i < keyParams.length - 1)
                builder.append(SEPARATOR);
        }
        return builder.toString();
    }

    /// <summary>
    /// Builds font key from specified string parameters.
    /// Use this method if you need to build custom font key, if not use <see cref="TtFontKey"/> property instead.
    /// This method is optimized version of <see cref="BuildFontKey(string[])"/> to reach better performance.
    /// </summary>
    public static String buildFontKey(String keyParam1, String keyParam2)
    {
        return keyParam1 + SEPARATOR + keyParam2;
    }

    private String mTtFontKey;

    private static final char SEPARATOR = '-';
}

