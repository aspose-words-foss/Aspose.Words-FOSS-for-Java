// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2021 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.TTFont;
import com.aspose.StringUtil;
import com.aspose.WoffConverter;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msString;
import com.aspose.CaseInsensitiveHashSet;


/// <summary>
/// Imports fonts from @font-face rule to document.
/// </summary>
class CssFontFaceProvider
{
    CssFontFaceProvider(
        ArrayList<CssFontFaceRule> fontFaceRules,
        String baseUri,
        HtmlResourceLoader resourceLoader,
        Document document)
    {
        mFontFaceRules = fontFaceRules;
        mBaseUri = baseUri;
        mResourceLoader = resourceLoader;
        mDocument = document;
    }

    boolean importFontFaceToDocument(String fontFamily) throws Exception
    {
        if (mProcessedFontFamilies.contains(fontFamily))
        {
            return true;
        }

        for (CssFontFaceRule fontFaceRule : mFontFaceRules)
        {
            if (!fontFaceRule.declaresFontFamily(fontFamily))
            {
                continue;
            }

            ArrayList<CssFontSource> sources = fontFaceRule.getSources();
            if (sources == null)
            {
                continue;
            }

            for (CssFontSource source : sources)
            {
                byte[] fontData = null;

                if (source.isLocal())
                {
                    TTFont font = mDocument.getFontProvider().getTTFont(source.getNameOrUri(), fontFaceRule.getFontStyle());
                    if (font != null)
                    {
                        if (StringUtil.equalsIgnoreCase(fontFamily, source.getNameOrUri()))
                        {
                            return true;
                        }
                        fontData = font.getData().getFontBytes();
                    }
                }
                else
                {
                    if (!source.isSupportedFormat())
                    {
                        continue;
                    }

                    fontData = mResourceLoader.loadFont(mBaseUri, source.getNameOrUri());
                    if ((source.getFormat() == CssFontFaceFormat.WOFF) && (fontData != null))
                    {
                        fontData = WoffConverter.convert(new MemoryStream(fontData));
                    }
                }

                if (fontData != null)
                {
                    embedFont(
                        fontFamily,
                        source.getFormat() == CssFontFaceFormat.EMBEDDED_OPENTYPE,
                        fontData,
                        fontFaceRule);
                    mProcessedFontFamilies.add(fontFamily);
                    return true;
                }
            }
        }

        return false;
    }

    private void embedFont(
        String fontFamily,
        boolean isEot,
        byte[] fontData,
        CssFontFaceRule fontFaceRule)
    {
        FontInfo fontInfo = new FontInfo(fontFamily);

        /*EmbeddedFontFormat*/int embeddedFontFormat = isEot
            ? EmbeddedFontFormat.EMBEDDED_OPEN_TYPE
            : EmbeddedFontFormat.OPEN_TYPE;

        fontInfo.addEmbeddedFont(
            fontData,
            embeddedFontFormat,
            fontFaceRule.getEmbeddedFontStyle(),
            false);
        TTFont font = fontInfo.getEmbeddedFontParsedAnyStyle();
        if ((font != null) && font.isPrintPreview())
        {
            warn(WarningType.FONT_EMBEDDING,
                msString.format("The loaded font has license restrictions on embedding. '{0}' only has `Print & Preview` permissions.", fontFamily));
        }

        mDocument.getFontInfos().merge(fontInfo);
    }

    private void warn(/*WarningType*/int warningType, String description)
    {
        if (mDocument.getWarningCallback() != null)
            mDocument.getWarningCallback().warning(new WarningInfo(warningType, WarningSource.HTML, description));
    }

    private /*final*/ ArrayList<CssFontFaceRule> mFontFaceRules;
    private /*final*/ String mBaseUri;
    private /*final*/ HtmlResourceLoader mResourceLoader;
    private /*final*/ Document mDocument;
    private /*final*/ CaseInsensitiveHashSet mProcessedFontFamilies = new CaseInsensitiveHashSet();
}

