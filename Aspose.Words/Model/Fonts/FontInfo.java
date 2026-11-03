// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/05/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.TTFont;
import com.aspose.ms.System.IO.Stream;
import com.aspose.StreamUtil;
import java.util.ArrayList;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.FontUtil;
import com.aspose.FontUnicodeRanges;


/// <summary>
/// Specifies information about a font used in the document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fonts/">Working with Fonts</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You do not create instances of this class directly.
/// Use the <see cref="DocumentBase.FontInfos"/> property to access the collection of fonts
/// defined in a document.</para>
///
/// <seealso cref="FontInfoCollection"/>
/// <seealso cref="DocumentBase.FontInfos"/>
/// </remarks>
public class FontInfo implements Cloneable
{
    /// <summary>
    /// Don't need public ctor.
    /// </summary>
    FontInfo()
    {
    }

    /// <summary>
    /// Don't need public ctor.
    /// </summary>
    FontInfo(String name)
    {
        setName(name);
    }

    /// <summary>
    /// Use only when loading font infos. Must call before adding font info to the font infos collection.
    /// </summary>
    void setName(String name)
    {
        if (name == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "name");

        Debug.assertTrue(msString.isNullOrEmpty(mName), "Name is already set, but I expect name to be set only once during loading.");

        mName = name;
    }

    /// <summary>
    /// Clones this font info, including embedded fonts.
    /// </summary>
    FontInfo deepClone()
    {
        //Don't think I need to clone other fields.
        FontInfo lhs = (FontInfo)memberwiseClone();

        if (mEmbeddedFonts != null)
            lhs.mEmbeddedFonts = mEmbeddedFonts.deepClone();

        if (mPanose != null)
            lhs.mPanose = (byte[])mPanose.clone();

        if (mSig != null)
            lhs.mSig = (byte[])mSig.clone();

        return lhs;
    }

    /// <summary>
    /// <para>Merges new <see cref="FontInfo"/> to this one.</para>
    /// <para>It's acceptable for <see cref="FontInfo"/> not to contain some of possible attributes. Also,
    /// <see cref="FontInfo"/> doesn't often contain one or all embedded fonts in its <see cref="FontInfoCollection"/>.</para>
    /// <para>Merge operation adds all missing attributes and embedded fonts from <paramref name="srcFontInfo"/>
    /// concerning the same font. Different <paramref name="srcFontInfo"/> can be obtained from other file or other
    /// font entry in the same file.</para>
    /// <para>Merge operations replaces subsetted embedded fonts with full versions.</para>
    /// </summary>
    void merge(FontInfo srcFontInfo)
    {
        mergeAttributes(srcFontInfo);

        if (srcFontInfo.mEmbeddedFonts == null)
        {
            return;
        }
        else if (mEmbeddedFonts == null)
        {
            mEmbeddedFonts = new EmbeddedFontCollection();
        }

        mEmbeddedFonts.merge(srcFontInfo.mEmbeddedFonts);
    }

    /// <summary>
    /// <para>Provides access to embedded fonts list to iterate over it.</para>
    /// <para>Don't use to add records to embedded fonts list,
    /// use <see cref="FontInfo.AddEmbeddedFont(byte[], EmbeddedFontFormat, EmbeddedFontStyle, bool)"/> instead.</para>
    /// <para>Use <see cref="FontInfo.GetEmbeddedFont"/> to get safe access to particular element.</para>
    /// <para>Each <see cref="FontInfo"/> can contain font files of different formats (see <see cref="EmbeddedFontFormat"/>)
    /// and up to four font files for each font format (see <see cref="EmbeddedFontStyle"/>).</para>
    /// </summary>
    /// <remarks>
    /// <para>Can be <c>null</c>.</para>
    /// </remarks>
    EmbeddedFont[] getEmbeddedFonts(/*EmbeddedFontFormat*/int format)
    {
        return (mEmbeddedFonts == null) ? null : mEmbeddedFonts.getFonts(format);
    }

    /// <summary>
    /// Gets a specific embedded font file.
    /// </summary>
    /// <param name="format">Specifies the font format to retrieve.</param>
    /// <param name="style">Specifies the font style to retrieve.</param>
    /// <returns>Returns <c>null</c> if the specified font is not embedded.</returns>
    public byte[] getEmbeddedFont(/*EmbeddedFontFormat*/int format, /*EmbeddedFontStyle*/int style)
    {
        if (mEmbeddedFonts == null)
            return null;

        EmbeddedFont embeddedFont = mEmbeddedFonts.get(format, style);

        return (embeddedFont == null) ? null : embeddedFont.getData();
    }

    /// <summary>
    /// Gets an embedded font file in OpenType format. Fonts in Embedded OpenType format are converted to OpenType.
    /// </summary>
    /// <param name="style">Specifies the font style to retrieve.</param>
    /// <returns>Returns <c>null</c> if the specified font is not embedded.</returns>
    public byte[] getEmbeddedFontAsOpenType(/*EmbeddedFontStyle*/int style)
    {
        if (mEmbeddedFonts == null)
            return null;

        return mEmbeddedFonts.getOpenTypeData(style);
    }

    /// <summary>
    /// Gets embedded font of specified style with not empty data.
    /// </summary>
    EmbeddedFont getEmbeddedFontWithData(/*FontStyle*/int style)
    {
        if (mEmbeddedFonts == null)
            return null;

        /*EmbeddedFontStyle*/int embeddedStyle = fontStyleToEmbeddedFontStyle(style);

        EmbeddedFont font = mEmbeddedFonts.get(EmbeddedFontFormat.OPEN_TYPE, embeddedStyle);
        if (font != null && font.getData() != null)
            return font;

        font = mEmbeddedFonts.get(EmbeddedFontFormat.EMBEDDED_OPEN_TYPE, embeddedStyle);
        if (font != null && font.getData() != null)
            return font;

        return null;
    }

    /// <summary>
    /// Returns <see cref="EmbeddedFontStyle"/> corresponded to the specified <paramref name="style"/>.
    /// </summary>
    static /*EmbeddedFontStyle*/int fontStyleToEmbeddedFontStyle(/*FontStyle*/int style)
    {
        if (((style & FontStyle.ITALIC) != 0) && ((style & FontStyle.BOLD) != 0))
            return EmbeddedFontStyle.BOLD_ITALIC;
        if ((style & FontStyle.ITALIC) != 0)
            return EmbeddedFontStyle.ITALIC;
        if ((style & FontStyle.BOLD) != 0)
            return EmbeddedFontStyle.BOLD;
        return EmbeddedFontStyle.REGULAR;
    }

    TTFont getEmbeddedFontParsed(/*FontStyle*/int style)
    {
        return mEmbeddedFonts == null
            ? null
            : mEmbeddedFonts.getTtFont(fontStyleToEmbeddedFontStyle(style));
    }

    TTFont getEmbeddedFontParsedAnyStyle()
    {
        return mEmbeddedFonts == null
            ? null
            : mEmbeddedFonts.getTtFontAnyStyle();
    }

    /// <summary>
    /// <para>Adds new font to embedded fonts collection or replaces existing font with the same format and style.</para>
    /// </summary>
    void addEmbeddedFont(byte[] fontData, /*EmbeddedFontFormat*/int fontFormat, /*EmbeddedFontStyle*/int fontStyle,
        boolean isSubsetted)
    {
        if (mEmbeddedFonts == null)
            mEmbeddedFonts = new EmbeddedFontCollection();

        mEmbeddedFonts.add(new EmbeddedFont(fontData, fontFormat, fontStyle, isSubsetted));
    }

    /// <summary>
    /// <para>Adds new font of <see cref="EmbeddedFontFormat.OpenType"/> format to embedded fonts collection
    /// or replaces existing font with the same format and style.</para>
    /// </summary>
    void addEmbeddedFont(TTFont ttFont) throws Exception
    {
        Stream fontDataStream = ttFont.getData().openStream();
        try /*JAVA: was using*/
        {
            byte[] fontData = StreamUtil.copyStreamToByteArray(fontDataStream);
            /*EmbeddedFontStyle*/int style = fontStyleToEmbeddedFontStyle(ttFont.getStyle());

            addEmbeddedFont(fontData, EmbeddedFontFormat.OPEN_TYPE, style, false);
        }
        finally { if (fontDataStream != null) fontDataStream.close(); }
    }

    /// <summary>
    /// <para>Gets list of all alternative names (except main) for the font.</para>
    /// <para><seealso cref="AltName"/>.</para>
    /// </summary>
    /// <remarks>Implemented logic is from DOCX spec.</remarks>
    ArrayList<String> getAltNameList()
    {
        ArrayList<String> answer = new ArrayList<String>();

        for (String fontName : msString.split(mAltName, ','))
        {
            String fontNameTrimmed = msString.trim(fontName, ' ');
            if (StringUtil.hasChars(fontNameTrimmed))
            {
                msArrayList.add(answer, fontNameTrimmed);
            }
        }

        return answer;
    }

    /// <summary>
    /// Returns array of embedded fonts of those of <see cref="EmbeddedFontFormat"/>
    /// that has at least one not null embedded font.
    /// </summary>
    EmbeddedFont[] getEmbeddedFonts()
    {

        if (hasEmbeddedFontsByFormat(EmbeddedFontFormat.OPEN_TYPE))
            return getEmbeddedFonts(EmbeddedFontFormat.OPEN_TYPE);

        if (hasEmbeddedFontsByFormat(EmbeddedFontFormat.EMBEDDED_OPEN_TYPE))
            return getEmbeddedFonts(EmbeddedFontFormat.EMBEDDED_OPEN_TYPE);

        return null;
    }

    /// <summary>
    /// Returns true if this font info has not null embedded font of any embedded format.
    /// </summary>
    boolean hasEmbeddedFonts()
    {
        return hasEmbeddedFontsByFormat(EmbeddedFontFormat.OPEN_TYPE) ||
               hasEmbeddedFontsByFormat(EmbeddedFontFormat.EMBEDDED_OPEN_TYPE);
    }

    /// <summary>
    /// Returns true if this font info has embedded fonts
    /// of the specified format with not empty data.
    /// </summary>
    boolean hasEmbeddedFontsByFormat(/*EmbeddedFontFormat*/int fontFormat)
    {
        return (getEmbeddedFontsCount(fontFormat) != 0);
    }

    /// <summary>
    /// Returns true if this font info has embedded fonts
    /// of the specified style with not empty data.
    /// </summary>
    boolean hasEmbeddedFontsByStyle(/*FontStyle*/int fontStyle)
    {
        return (getEmbeddedFontWithData(fontStyle) != null);
    }

    /// <summary>
    /// Removes embedded fonts.
    /// </summary>
    void removeEmbeddedFonts()
    {
        mEmbeddedFonts = null;
    }

    /// <summary>
    /// Returns number of not null embedded fonts of the specified format.
    /// </summary>
    int getEmbeddedFontsCount(/*EmbeddedFontFormat*/int fontFormat)
    {
        int embeddedFontsCount = 0;

        EmbeddedFont[] embeddedFonts = getEmbeddedFonts(fontFormat);

        if (embeddedFonts != null)
        {
            for (EmbeddedFont embeddedFont : embeddedFonts)
                if (embeddedFont != null)
                    embeddedFontsCount++;
        }

        return embeddedFontsCount;
    }

    /// <summary>
    /// <para>It's acceptable for <see cref="FontInfo"/> not to contain some of possible attributes.</para>
    /// <para>MergeAttributes operation adds all missing attributes from <paramref name="srcFontInfo"/>
    /// concerning the same font. <paramref name="srcFontInfo"/> can be obtained from other file or other
    /// font entry in the same file.</para>
    /// <para>In general, these attributes should be filled from installed font or be kept unchanged if there is no
    /// installed font with specified name. But currently attributes of the font cannot be got from font file.</para>
    /// <para>The algorithm below successfully fills missing attributes if they exist in another <see cref="FontInfo"/>
    /// but it cannot correctly handle the case when fonts with the same name are essentially different fonts.</para>
    /// <para><see cref = "FontSignature" /> is updated in accordance with the charsets, which must be supported
    /// by the font.</para>
    /// </summary>
    /// <remarks>
    /// For handling of merge of essentially different fonts with the same name requesting of attributes from
    /// installed fonts and embedded fonts is needed.
    /// </remarks>
    private void mergeAttributes(FontInfo srcFontInfo)
    {
        Debug.assertTrue(StringUtil.equalsIgnoreCase(mName, srcFontInfo.mName));

        if (mPitch == FontPitch.DEFAULT)
            mPitch = srcFontInfo.mPitch;

        if ("".equals(mAltName))
            mAltName = srcFontInfo.mAltName;

        if (mFamily == FontFamily.AUTO)
            mFamily = srcFontInfo.mFamily;

        if (mWeight == 0)
            mWeight = srcFontInfo.mWeight;

        if (mIsTrueType)
            mIsTrueType = srcFontInfo.mIsTrueType;

        if ((!isCharsetDefined() || (mCharset == FontUtil.ANSI_CHARSET)) && !StringUtil.hasChars(mIanaCharset))
        {
            mCharset = srcFontInfo.mCharset;
            mIanaCharset = srcFontInfo.mIanaCharset;
        }

        // AS It's safer to make clones of arrays here but
        // I make the behavour consistent with that in Clone() operation.
        if (mPanose == null)
            mPanose = srcFontInfo.mPanose;

        if (mSig == null)
            mSig = srcFontInfo.mSig;

        // If signature was not taken from merged font lets build it.
        FontSignature signature = new FontSignature(mSig);
        signature.addCharset(srcFontInfo.getCharset());
        signature.addCharset(mCharset);
        mSig = signature.getData();
    }

    /// <summary>
    /// The pitch indicates if the font is fixed pitch, proportionally spaced, or relies on a default setting.
    /// </summary>
    public /*FontPitch*/int getPitch() { return mPitch; }
    public void setPitch(/*FontPitch*/int value) { mPitch = value; }

    /// <summary>
    /// Indicates that this font is a TrueType or OpenType font as opposed to a raster or vector font.
    /// Default is <c>true</c>.
    /// </summary>
    public boolean isTrueType() { return mIsTrueType; }
    public void isTrueType(boolean value) { mIsTrueType = value; }

    /// <summary>
    /// Gets or sets the font family this font belongs to.
    /// </summary>
    public /*FontFamily*/int getFamily() { return mFamily; }
    public void setFamily(/*FontFamily*/int value) { mFamily = value; }

    /// <summary>
    /// Base weight of font.
    /// </summary>
    int getWeight() { return mWeight; }
    void setWeight(int value) { mWeight = value; }

    /// <summary>
    /// Gets or sets the character set for the font.
    /// </summary>
    /// <dev>
    /// Such charset code is replaced by IANA name of character set in ISO/IEC 29500.
    /// We may not know charset codes for some IANA charset names. Also there may be unknown charset codes.
    /// For example AW test documents contain the undocumented 0x4E and 0x50 codes.
    /// So, now we store the both: charset codes and charset IANA names to be able to write charset as it was read.
    /// </dev>
    public int getCharset()
    {
        if (isCharsetDefined())
            return mCharset;

        int charset = FontUtil.resolveCharset(mIanaCharset, mName);
        return (charset != FontUtil.UNDEFINED_CHARSET) ? charset : FontUtil.ANSI_CHARSET;
    }
    public void setCharset(int value)
    {
        mCharset = value;
        if (isCharsetDefined())
            mIanaCharset = null;
    }

    /// <summary>
    /// Gets or sets the IANA character set name for the font.
    /// </summary>
    /// <dev>
    /// IANA name is used instead of character set code in ISO/IEC 29500.
    /// We may not know charset codes for some IANA charset names. Also there may be unknown charset codes.
    /// For example AW test documents contain the undocumented 0x4E and 0x50 codes.
    /// So, now we store the both: charset codes and charset IANA names to be able to write charset as it was read.
    /// </dev>
    String getIanaCharset()
    {
        if (StringUtil.hasChars(mIanaCharset))
            return mIanaCharset;

        if (isCharsetDefined())
            return FontUtil.ianaNameByCharset(mCharset);
        else
            return "";
    }
    void setIanaCharset(String value)
    {
        mIanaCharset = value;
        if (StringUtil.hasChars(mIanaCharset))
            mCharset = FontUtil.UNDEFINED_CHARSET;
    }

    /// <summary>
    /// Gets or sets the PANOSE typeface classification number.
    /// </summary>
    /// <remarks>
    /// <para>PANOSE is a compact 10-byte description of a fonts critical visual characteristics,
    /// such as contrast, weight, and serif style. The digits represent Family Kind, Serif Style,
    /// Weight, Proportion, Contrast, Stroke Variation, Arm Style, Letterform, Midline, and X-Height.</para>
    ///
    /// <para>Can be <c>null</c>.</para>
    /// </remarks>
    
    public byte[] getPanose() { return mPanose; }
    
    public void setPanose(byte[] value)
    {
        if ((value != null) && (value.length != PANOSE_LENGTH))
            throw new IllegalArgumentException("Incorrect array length.");

        mPanose = value;
    }

    /// <summary>
    /// Contains information identifying the code pages and Unicode subranges for which the font provides glyphs.
    /// </summary>
    /// <remarks>
    /// <para>Can be <c>null</c>.</para>
    /// </remarks>
    byte[] getSig() { return mSig; }
    void setSig(byte[] value)
    {
        if ((value != null) && (value.length != SIG_LENGTH))
            throw new IllegalArgumentException("Incorrect array length.");

        mSig = value;
    }

    /// <summary>
    /// Gets the name of the font.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>. Can be an empty string.</para>
    /// </remarks>
    public String getName() { return mName; }

    /// <summary>
    /// Gets or sets the alternate name for the font.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>. Can be an empty string.</para>
    /// </remarks>
    public String getAltName() { return mAltName; }
    public void setAltName(String value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        mAltName = value;
    }

    /// <summary>
    /// Gets the embedded font licensing rights.
    /// </summary>
    /// <remarks>
    /// <para>The value may be null if font is not embedded.</para>
    /// </remarks>
    public FontEmbeddingLicensingRights getEmbeddingLicensingRights()
    {
        // Note: In theory there may be different fsType values on different embedded fonts.
        // Do not consider this case for now because it is a rare case. The font styles from the same family
        // should have the same fsType values. The difference may occur in case when font styles are not really
        // from the same family (e.g. from different font producers or different font versions).
        TTFont font = getEmbeddedFontParsedAnyStyle();
        return font != null ? FontEmbeddingLicensingRights.fromFsType(font.getFsType()) : null;
    }

    /// <summary>
    /// Returns true if character set of font is defined.
    /// </summary>
    boolean isCharsetDefined() { return mCharset != FontUtil.UNDEFINED_CHARSET; }

    /// <summary>
    /// Returns UnicodeRanges. Returns <see cref="FontUnicodeRanges.Empty"/> if <see cref="Sig"/> is null
    /// </summary>
    FontUnicodeRanges getUnicodeRanges()
    {
        FontUnicodeRanges ranges = (mSig == null) ? FontUnicodeRanges.EMPTY : new FontUnicodeRanges(mSig, 0);
        return ranges;
    }

    private /*FontPitch*/int mPitch;
    private boolean mIsTrueType = true;
    private /*FontFamily*/int mFamily;
    private int mWeight;
    private int mCharset = FontUtil.UNDEFINED_CHARSET;
    private String mIanaCharset;
    private byte[] mPanose;
    private byte[] mSig;
    private String mName = "";
    private String mAltName = "";
    private EmbeddedFontCollection mEmbeddedFonts;

    static final int PANOSE_LENGTH = 10;
    static final int SIG_LENGTH = 24;

    /// <summary>
    /// Seems that font name has maximum allowed length.
    /// </summary>
    static final int MAX_FONT_NAME_LENGTH = 31;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

