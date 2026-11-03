// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2021 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.UriUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Represents an individual font source information from a @font-face CSS rule.
/// </summary>
/// <remarks>
/// Note that each @font-face rule may specify more than one font source.
/// See https://www.w3.org/TR/css-fonts-3/#src-desc
/// </remarks>
class CssFontSource
{
    private CssFontSource(boolean isLocal, String nameOrUri, /*CssFontFaceFormat*/int format)
    {
        Debug.assertTrue(StringUtil.hasChars(nameOrUri));

        mIsLocal = isLocal;
        mNameOrUri = nameOrUri;
        mFormat = detectFormat(format);
    }

    static CssFontSource createLocal(String familyName)
    {
        return new CssFontSource(true, familyName, CssFontFaceFormat.UNKNOWN);
    }

    static CssFontSource createExternal(String uri, /*CssFontFaceFormat*/int format)
    {
        return new CssFontSource(false, uri, format);
    }

    boolean isLocal() { return mIsLocal; };

    private boolean mIsLocal;

    /*CssFontFaceFormat*/int getFormat() { return mFormat; };

    private /*CssFontFaceFormat*/int mFormat;

    String getNameOrUri() { return mNameOrUri; };

    private String mNameOrUri;

    boolean isSupportedFormat()
    {
        switch (getFormat())
        {
            case CssFontFaceFormat.WOFF:
            case CssFontFaceFormat.EMBEDDED_OPENTYPE:
            case CssFontFaceFormat.TRUE_TYPE:
                return true;
            default:
                return false;
        }
    }

    private boolean hasExtension(String extension)
    {
        String fileExtension = UriUtil.getExtension("", getNameOrUri());
        return msString.equals(extension, fileExtension, StringComparison.ORDINAL_IGNORE_CASE);
    }

    private boolean hasMediaType(String mediaType)
    {
        DataUrl dataUrl = DataUrl.parse(getNameOrUri());
        return (dataUrl != null) && isFontWithType(dataUrl.getMediaType(), mediaType);
    }

    private static boolean isFontWithType(String mediaType, String fontType)
    {
        return msString.equals(mediaType, "font/" + fontType, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(mediaType, "application/x-font-" + fontType, StringComparison.ORDINAL_IGNORE_CASE);
    }

    private /*CssFontFaceFormat*/int detectFormat(/*CssFontFaceFormat*/int format)
    {
        if ((format == CssFontFaceFormat.EMBEDDED_OPENTYPE) || hasExtension("eot") || hasMediaType("eot"))
            return CssFontFaceFormat.EMBEDDED_OPENTYPE;
        if ((format == CssFontFaceFormat.WOFF) || hasExtension("woff") || hasMediaType("woff"))
            return CssFontFaceFormat.WOFF;
        if ((format == CssFontFaceFormat.TRUE_TYPE) || hasExtension("ttf") || hasMediaType("ttf"))
            return CssFontFaceFormat.TRUE_TYPE;
        return CssFontFaceFormat.UNKNOWN;
    }
}

