// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/04/2011 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.TTFont;
import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.TTFontFiler;
import com.aspose.EotFontFiler;
import com.aspose.PhysicalFontData;
import com.aspose.MemoryFontData;
import com.aspose.TTFontBuilder;


class EmbeddedFontCollection
{
    /// <summary>
    /// Ctor.
    /// </summary>
    EmbeddedFontCollection()
    {
        mEmbeddedFonts = new EmbeddedFont[FORMATS_QUANTITY][];
        for (int i = 0; i < FORMATS_QUANTITY; i++)
            mEmbeddedFonts[i] = new EmbeddedFont[STYLES_QUANTITY];
    }

    /// <summary>
    /// Need to clone collection of embedded fonts, but not embedded fonts itself. EmbeddedFont is object
    /// immutable in concept and large enough to not want to clone it, so just assign references.
    /// </summary>
    EmbeddedFontCollection deepClone()
    {
        EmbeddedFontCollection lhs = new EmbeddedFontCollection();

        for (int formatIndex = 0; formatIndex < mEmbeddedFonts.length; formatIndex++)
        {
            EmbeddedFont[] source = mEmbeddedFonts[formatIndex];
            EmbeddedFont[] copy = new EmbeddedFont[source.length];
            System.arraycopy(source, 0, copy, 0, source.length);
            lhs.mEmbeddedFonts[formatIndex] = copy;
        }

        lhs.clearParsedFontsCache();

        return lhs;
    }

    void merge(EmbeddedFontCollection srcEmbeddedFontCollection)
    {
        Debug.assertTrue(srcEmbeddedFontCollection != null);

        for (int format = 0; format < FORMATS_QUANTITY; format++)
        {
            EmbeddedFont[] srcEmbeddedFontsOfSpecificFormat = srcEmbeddedFontCollection.mEmbeddedFonts[format];

            for (int style = 0; style < STYLES_QUANTITY; style++)
            {
                EmbeddedFont srcEmbeddedFont = srcEmbeddedFontsOfSpecificFormat[style];

                if (srcEmbeddedFont == null)
                    continue;

                EmbeddedFont myEmbeddedFont = mEmbeddedFonts[format][style];

                if ((myEmbeddedFont == null) ||
                    (myEmbeddedFont.isSubsetted() && !srcEmbeddedFont.isSubsetted()))
                {
                    // AS EmbeddedFont is object immutable in concept and large enough to not want to clone it,
                    // so just assign reference.
                    mEmbeddedFonts[format][style] = srcEmbeddedFont;
                }
            }
        }

        clearParsedFontsCache();
    }

    /// <summary>
    /// <para>Provides access to embedded fonts of specific format to iterate over them.</para>
    /// </summary>
    /// <remarks>
    /// <para>Can not be <c>null</c>. Each of elements of returned array can be <c>null</c>.</para>
    /// </remarks>
    EmbeddedFont[] getFonts(/*EmbeddedFontFormat*/int fontFormat)
    {
        return mEmbeddedFonts[(int)fontFormat];
    }

    /// <summary>
    /// <para>Provides access to particular embedded font file.</para>
    /// <para>Returns <c>null</c> if embedded font is absent.</para>
    /// </summary>
    EmbeddedFont get(/*EmbeddedFontFormat*/int fontFormat, /*EmbeddedFontStyle*/int embeddedFontStyle)
    {
        return mEmbeddedFonts[(int)fontFormat][(int)embeddedFontStyle];
    }

    /// <summary>
    /// Adds new embedded font to the collection. If font with the same <see cref="EmbeddedFontFormat"/>
    /// and <see cref="EmbeddedFontStyle"/> already exists in the collection, replaces it.
    /// </summary>
    /// <param name="font"></param>
    void add(EmbeddedFont font)
    {
        mEmbeddedFonts[(int)font.getFormat()][(int)font.getStyle()] = font;

        clearParsedFontsCache();
    }

    TTFont getTtFont(/*EmbeddedFontStyle*/int style)
    {
        parseEmbeddedData();
        return mParsedFontsCache[(int)style];
    }

    TTFont getTtFontAnyStyle()
    {
        parseEmbeddedData();
        for (TTFont font : mParsedFontsCache)
        {
            if (font != null)
                return font;
        }

        return null;
    }

    private void clearParsedFontsCache()
    {
        mIsEmbeddedDataParsed = false;
        for (int i = 0; i < STYLES_QUANTITY; i++)
            mParsedFontsCache[i] = null;
    }

    private void parseEmbeddedData()
    {
        if (mIsEmbeddedDataParsed)
            return;

        mIsEmbeddedDataParsed = true;

        TTFont[] subsets = new TTFont[STYLES_QUANTITY];
        subsets[(int)EmbeddedFontStyle.REGULAR] = readFont(getOpenTypeData(EmbeddedFontStyle.REGULAR));
        subsets[(int)EmbeddedFontStyle.BOLD] = readFont(getOpenTypeData(EmbeddedFontStyle.BOLD));
        subsets[(int)EmbeddedFontStyle.ITALIC] = readFont(getOpenTypeData(EmbeddedFontStyle.ITALIC));
        subsets[(int)EmbeddedFontStyle.BOLD_ITALIC] = readFont(getOpenTypeData(EmbeddedFontStyle.BOLD_ITALIC));

        // In case of style simulation MW splits font subsets per style for some reason. We should join subsets
        // in order to process them properly.

        // All other styles could be simulated from regular style. Only BoldItalic style could be simulated from Bold or
        // Italic style.
        if (subsets[(int)EmbeddedFontStyle.REGULAR] != null)
        {
            mParsedFontsCache[(int)EmbeddedFontStyle.REGULAR] = subsets[(int)EmbeddedFontStyle.REGULAR];
            joinSubsets(subsets, EmbeddedFontStyle.REGULAR, EmbeddedFontStyle.BOLD, EmbeddedFontStyle.ITALIC,
                EmbeddedFontStyle.BOLD_ITALIC);
        }

        if (mParsedFontsCache[(int)EmbeddedFontStyle.BOLD] == null && subsets[(int)EmbeddedFontStyle.BOLD] != null)
        {
            mParsedFontsCache[(int)EmbeddedFontStyle.BOLD] = subsets[(int)EmbeddedFontStyle.BOLD];
            joinSubsets(subsets, EmbeddedFontStyle.BOLD, EmbeddedFontStyle.BOLD_ITALIC);
        }

        if (mParsedFontsCache[(int)EmbeddedFontStyle.ITALIC] == null && subsets[(int)EmbeddedFontStyle.ITALIC] != null)
        {
            mParsedFontsCache[(int)EmbeddedFontStyle.ITALIC] = subsets[(int)EmbeddedFontStyle.ITALIC];
            joinSubsets(subsets, EmbeddedFontStyle.ITALIC, EmbeddedFontStyle.BOLD_ITALIC);
        }

        if (mParsedFontsCache[(int)EmbeddedFontStyle.BOLD_ITALIC] == null && subsets[(int)EmbeddedFontStyle.BOLD_ITALIC] != null)
            mParsedFontsCache[(int)EmbeddedFontStyle.BOLD_ITALIC] = subsets[(int)EmbeddedFontStyle.BOLD_ITALIC];
    }

    private static boolean canJoinSubsets(TTFont[] subsets, /*EmbeddedFontStyle*/int style1, /*EmbeddedFontStyle*/int style2)
    {
        TTFont sub1 = subsets[(int)style1];
        TTFont sub2 = subsets[(int)style2];

        if (sub1 == null || sub2 == null)
            return false;

        // When splitting subsets MW keeps source FullFontName and Style.
        return StringUtil.equalsIgnoreCase(sub1.getFullFontName(), sub2.getFullFontName()) && sub1.getStyle() == sub2.getStyle();
    }

    private void joinSubsets(TTFont[] subsets, /*EmbeddedFontStyle*/int baseStyle, /*params*/ /*EmbeddedFontStyle*/int... otherStyles)
    {
        ArrayList<Integer> joinStyles = new ArrayList<Integer>();
        msArrayList.add(joinStyles, baseStyle);
        for (/*EmbeddedFontStyle*/int style : otherStyles)
        {
            if (canJoinSubsets(subsets, baseStyle, style))
                msArrayList.add(joinStyles, style);
        }

        if (joinStyles.size() <= 1)
            return;

        ArrayList<TTFont> joinFonts = new ArrayList<TTFont>();
        for (/*EmbeddedFontStyle*/int style : (Iterable</*EmbeddedFontStyle*/Integer>) joinStyles)
            msArrayList.add(joinFonts, subsets[(/*int*/Integer)style]);

        TTFont joinedFont = TTFontFiler.joinEmbeddedSubsets(joinFonts);

        mParsedFontsCache[(int)baseStyle] = joinedFont;
        for (/*EmbeddedFontStyle*/int style : (Iterable</*EmbeddedFontStyle*/Integer>) joinStyles)
            mParsedFontsCache[(/*int*/Integer)style] = joinedFont;
    }

    byte[] getOpenTypeData(/*EmbeddedFontStyle*/int style)
    {
        EmbeddedFont font = get(EmbeddedFontFormat.OPEN_TYPE, style);
        if (font != null && font.getData() != null)
            return font.getData();

        font = get(EmbeddedFontFormat.EMBEDDED_OPEN_TYPE, style);
        return ((font != null) && (font.getData() != null))
            ? EotFontFiler.tryExtractOpenTypeFromEot(font.getData())
            : null;
    }

    private static TTFont readFont(byte[] fontBytes)
    {
        if (fontBytes == null)
            return null;

        try
        {
            PhysicalFontData fontData = new PhysicalFontData(new MemoryFontData(fontBytes, null, true));
            return TTFontBuilder.read(fontData);
        }
        catch(Exception e)
        {
            // If there are exceptions while parsing the font then simply return null.
            return null;
        }
    }

    /// <summary>
    /// <para>First index is (int)<see cref="EmbeddedFontFormat"/>.</para>
    /// <para>Second index is (int)<see cref="EmbeddedFontStyle"/>.</para>
    /// </summary>
    private /*final*/ EmbeddedFont[][] mEmbeddedFonts;

    private static final int FORMATS_QUANTITY = 2;
    private static final int STYLES_QUANTITY = 4;

    private boolean mIsEmbeddedDataParsed;
    private /*final*/ TTFont[] mParsedFontsCache = new TTFont[STYLES_QUANTITY];
}

