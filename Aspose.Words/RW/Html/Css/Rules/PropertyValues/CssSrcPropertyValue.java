// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2021 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.CssEscape;


/// <summary>
/// Represents a value of the "src" property in the @font-face rule.
/// </summary>
/// <remarks>
/// See https://www.w3.org/TR/css-fonts-3/#src-desc
/// </remarks>
class CssSrcPropertyValue extends CssPropertyValue
{
    CssSrcPropertyValue(ArrayList<CssFontSource> fontSources)
    {
    	super(convertSourcesToCssValues(fontSources));
	
        mFontSources = fontSources;
    }

    ArrayList<CssFontSource> getFontSources() { return mFontSources; };

    private ArrayList<CssFontSource> mFontSources;

    protected /*override*/ void valueToCss(StringBuilder sb)
    {
        final String DELIMITER = ", ";
        for (int i = 0; i < getFontSources().size(); i++)
        {
            if (i != 0)
                msStringBuilder.append(sb, DELIMITER);
            CssFontSource fontSource = getFontSources().get(i);
            if (fontSource.isLocal())
            {
                msStringBuilder.append(sb, "local('");
                msStringBuilder.append(sb, CssEscape.escapeSingleQuotedString(fontSource.getNameOrUri()));
                msStringBuilder.append(sb, "')");
            }
            else
            {
                msStringBuilder.append(sb, "url('");
                msStringBuilder.append(sb, CssEscape.escapeSingleQuotedString(fontSource.getNameOrUri()));
                msStringBuilder.append(sb, "')");

                if (fontSource.isSupportedFormat())
                {
                    sb.append(' ');
                    msStringBuilder.append(sb, "format(");
                    msStringBuilder.append(sb, formatToString(fontSource.getFormat()));
                    sb.append(')');
                }
            }
        }
    }

    private static CssValueList convertSourcesToCssValues(ArrayList<CssFontSource> fontSources)
    {
        CssValueList cssValues = new CssValueList();
        for (CssFontSource fontSource : fontSources)
        {
            if (fontSource.isLocal())
            {
                cssValues.add(new CssFunctionValue("local", new CssStringValue(fontSource.getNameOrUri())));
            }
            else
            {
                cssValues.add(new CssUriValue(fontSource.getNameOrUri()));
                if (fontSource.isSupportedFormat())
                {
                    cssValues.add(new CssFunctionValue("format",
                        new CssStringValue(formatToString(fontSource.getFormat()))));
                }
            }
        }
        return cssValues;
    }

    private static String formatToString(/*CssFontFaceFormat*/int format)
    {
        switch (format)
        {
            case CssFontFaceFormat.COLLECTION:
                return "collection";
            case CssFontFaceFormat.EMBEDDED_OPENTYPE:
                return "embedded-opentype";
            case CssFontFaceFormat.OPEN_TYPE:
                return "opentype";
            case CssFontFaceFormat.SVG:
                return "svg";
            case CssFontFaceFormat.TRUE_TYPE:
                return "truetype";
            case CssFontFaceFormat.WOFF:
                return "woff";
            case CssFontFaceFormat.WOFF_2:
                return "woff2";
            default:
                return "";
        }
    }
}

