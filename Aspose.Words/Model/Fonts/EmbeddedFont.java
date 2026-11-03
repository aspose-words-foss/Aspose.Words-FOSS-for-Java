// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/01/2011 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.EotFontFiler;


/// <summary>
/// <para>Contains embedded font data, its format, style and flag whether font data subsetted
/// (i.e. contains incomplete set of characters).</para>
/// </summary>
class EmbeddedFont
{
    /// <summary>
    /// Builds <see cref="EmbeddedFont"/> object from its components.
    /// </summary>
    /// <param name="fontData"></param>
    /// <param name="format"></param>
    /// <param name="style"></param>
    /// <param name="isSubsetted">Specifies whether font data is complete or contains only subset of characters.</param>
    EmbeddedFont(byte[] fontData, /*EmbeddedFontFormat*/int format, /*EmbeddedFontStyle*/int style, boolean isSubsetted)
    {
        if (fontData == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "fontData");

        mData = fontData;
        mFormat = format;
        mStyle = style;
        mIsSubsetted = isSubsetted;
    }

    /// <summary>
    /// Creates embedded font of type <see cref="EmbeddedFontFormat.OpenType"/> from this embedded font.
    /// If it has this type already, then returns this instance.
    /// On error returns null.
    /// </summary>
    /// <remarks>
    /// We do not create new instance of this object or clone it
    /// if it has type <see cref="EmbeddedFontFormat.OpenType"/> already,
    /// because this object is immutable in concept and it can be very large.
    /// </remarks>
    EmbeddedFont getAsOpenType()
    {
        if (mData == null)
            return null;

        if (mFormat == EmbeddedFontFormat.OPEN_TYPE)
            return this;

        byte[] openTypeData = EotFontFiler.tryExtractOpenTypeFromEot(mData);

        return (openTypeData == null) ? null : new EmbeddedFont(openTypeData, EmbeddedFontFormat.OPEN_TYPE, getStyle(), isSubsetted());
    }

    /// <summary>
    /// Creates embedded font of type <see cref="EmbeddedFontFormat.EmbeddedOpenType"/> from this embedded font.
    /// If it has this type already, then returns this instance.
    /// On error returns null.
    /// </summary>
    /// <remarks>
    /// We do not create new instance of this object or clone it
    /// if it has type <see cref="EmbeddedFontFormat.EmbeddedOpenType"/> already,
    /// because this object is immutable in concept and it can be very large.
    /// </remarks>
    EmbeddedFont getAsEot(String fontName) throws Exception
    {
        if (mData == null)
            return null;

        if (mFormat == EmbeddedFontFormat.EMBEDDED_OPEN_TYPE)
            return this;

        byte[] eotData = null;
        try
        {
            eotData = EotFontFiler.compressOpenTypeToEot(mData, fontName, isSubsetted());
        }
        catch(Exception e)
        {
            // Ignore any errors.
        }

        if (eotData == null)
            return null;

        return new EmbeddedFont(eotData, EmbeddedFontFormat.EMBEDDED_OPEN_TYPE, getStyle(), isSubsetted());
    }

    /// <summary>
    /// Gets embedded font data.
    /// </summary>
    byte[] getData() { return mData; }

    /// <summary>
    /// Indicates format of embedded font data. See <see cref="EmbeddedFontFormat"/>.
    /// </summary>
    /*EmbeddedFontFormat*/int getFormat() { return mFormat; }

    /// <summary>
    /// Indicates style of embedded font data. See <see cref="EmbeddedFontStyle"/>.
    /// </summary>
    /*EmbeddedFontStyle*/int getStyle() { return mStyle; }

    /// <summary>
    /// Indicates whether font data is complete or contains only subset of characters.
    /// </summary>
    boolean isSubsetted() { return mIsSubsetted; }

    private /*final*/ byte[] mData;
    private /*final*/ /*EmbeddedFontFormat*/int mFormat;
    private /*final*/ /*EmbeddedFontStyle*/int mStyle;
    private /*final*/ boolean mIsSubsetted;
}

