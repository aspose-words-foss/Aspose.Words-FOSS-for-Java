// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents dml font.
/// 21.1.2.3.1 cs (Complex Script Font)
/// 21.1.2.3.3 ea (East Asian Font)
/// 21.1.2.3.7 latin (Latin Font)
/// 21.1.2.3.10 sym (Symbol Font)
/// </summary>
class DmlFont implements Cloneable
{
    /// <summary>
    /// Default ctor.
    /// </summary>
    DmlFont()
    {
    }

    /// <summary>
    /// Ctor with specifying font typeface.
    /// </summary>
    DmlFont(String typeface)
    {
        mTextTypeface = typeface;
    }

    /// <summary>
    /// Creates a copy of this object instance.
    /// </summary>
    DmlFont deepClone()
    {
        return (DmlFont)memberwiseClone();
    }

    /// <summary>
    /// Specifies the typeface, or name of the font that is to be used.
    /// The typeface is a string name of the specific font that should be
    /// used in rendering the presentation. If this font is not available
    /// within the font list of the generating application than font substitution
    /// logic should be utilized in order to select an alternate font.
    /// </summary>
    String getTextTypeface()
    {
        if (mTextTypeface == null)
            mTextTypeface = "";
        return mTextTypeface;
    }
    void setTextTypeface(String value) { mTextTypeface = value; }

    /// <summary>
    /// Specifies the font pitch as well as the font family for the corresponding font.
    /// </summary>
    int getSimilarFontFamily() { return mSimilarFontFamily; }
    void setSimilarFontFamily(int value) { mSimilarFontFamily = value; }

    /// <summary>
    /// Specifies the Panose-1 classification number for the current font
    /// using the mechanism defined in 4.2.7.17 of ISO/IEC 14496-22:2007.
    /// </summary>
    String getPanoseSetting()
    {
        if (mPanoseSetting == null)
            mPanoseSetting = "";

        return mPanoseSetting;
    }
    void setPanoseSetting(String value) { mPanoseSetting = value; }

    /// <summary>
    /// Specifies the character set which is supported by the parent font.
    /// This information can be used in font substitution logic to locate
    /// an appropriate substitute font when this font is not available.
    /// This information is determined by querying the font when present
    /// and shall not be modified when the font is not available.
    /// Default is 1.
    /// </summary>
    int getSimilarCharacterSet() { return mSimilarCharacterSet; }
    void setSimilarCharacterSet(int value) { mSimilarCharacterSet = value; }

    private String mPanoseSetting = "";
    private int mSimilarCharacterSet = 1;
    private int mSimilarFontFamily;
    private String mTextTypeface = "";

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

