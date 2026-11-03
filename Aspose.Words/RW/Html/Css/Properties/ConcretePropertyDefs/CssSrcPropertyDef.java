// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2021 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Represents the "src" property of a @font-face rule.
/// </summary>
/// <remarks>
/// See https://www.w3.org/TR/css-fonts-3/#src-desc
/// </remarks>
class CssSrcPropertyDef extends CssIndividualPropertyDef
{
    CssSrcPropertyDef()
    {
    	super("src", false, null);
	
        // Empty constructor.
    }

    /*override*/ CssDeclaration createIndividualDeclaration(
        CssValueList cssValues,
        int startIndex,
        boolean important,
        boolean isInQuirksMode,
        /*out*/RefInt affectedValues)
    {
        if (startIndex == 0)
        {
            CssPropertyValue propertyValue = createPropertyValue(cssValues, isInQuirksMode);
            if (propertyValue != null)
            {
                affectedValues.set(cssValues.getCount());
                return new CssSpecifiedDeclaration(getName(), propertyValue, important);
            }
        }

        affectedValues.set(0);
        return null;
    }

    protected /*override*/ CssPropertyValue createPropertyValue(
        CssValueList cssValues,
        boolean isInQuirksMode)
    {
        ArrayList<CssFontSource> fontSources = new ArrayList<CssFontSource>();
        int first = 0;
        // Note that we step past the end of list in order to simplify the loop body. The last part is also processed
        // inside the loop.
        for (int last = 0; last < (cssValues.getCount() + 1); last++)
        {
            boolean isDelimeter = (last >= cssValues.getCount()) || cssValues.get(last).equals(CssValue.Comma);
            if (isDelimeter)
            {
                int count = last - first;
                if (count > 0)
                {
                    CssFontSource fontSource = parseFontSource(cssValues.getRange(first, count));
                    if (fontSource != null)
                    {
                        msArrayList.add(fontSources, fontSource);
                    }
                }
                first = last + 1;
            }
        }

        return (fontSources.size() > 0)
            ? new CssSrcPropertyValue(fontSources)
            : null;
    }

    private static CssFontSource parseFontSource(CssValueList cssValues)
    {
        if (cssValues.getCount() == 0)
        {
            return null;
        }

        CssValue value = cssValues.get(0);

        if (value.getValueType() == CssValueType.FUNCTION)
        {
            if (cssValues.getCount() > 1)
                return null;

            CssFunctionValue cssFunction = (CssFunctionValue)value;

            if ((!"local".equals(cssFunction.getName())) || (cssFunction.getArguments().getCount() < 1))
                return null;

            String fontName = parseLocalFontName(cssFunction.getArguments());
            return StringUtil.hasChars(fontName)
                ? CssFontSource.createLocal(fontName)
                : null;
        }

        if (value.getValueType() == CssValueType.URI)
        {
            /*CssFontFaceFormat*/int format = CssFontFaceFormat.UNKNOWN;
            String fontUri = ((CssUriValue)value).getValue();

            if (!StringUtil.hasChars(fontUri))
                return null;

            if (cssValues.getCount() == 2)
            {
                CssFunctionValue cssFunction = ms.as(cssValues.get(1), CssFunctionValue.class);
                if ((cssFunction == null) ||
                    (!"format".equals(cssFunction.getName())) ||
                    (cssFunction.getArguments().getCount() != 1))
                {
                    return null;
                }

                String formatArgument = ms.as(cssFunction.getArguments().get(0).getValue(), String.class);
                if (!StringUtil.hasChars(formatArgument))
                {
                    return null;
                }
                format = parseFormatArgument(formatArgument);
            }

            return CssFontSource.createExternal(fontUri, format);
        }

        return null;
    }

    private static String parseLocalFontName(CssValueList cssValues)
    {
        /*CssValueType*/int firstValueType = cssValues.get(0).getValueType();

        // A single string or identifier.
        if ((cssValues.getCount() == 1) &&
            ((firstValueType == CssValueType.STRING) || (firstValueType == CssValueType.IDENTIFIER)))
        {
            return (String)cssValues.get(0).getValue();
        }

        // A series of identifiers separated by whitespace. For example, "src: local(Times New Roman)"
        if (firstValueType == CssValueType.IDENTIFIER)
        {
            StringBuilder fontName = new StringBuilder();
            for (CssValue cssValue : cssValues)
            {
                if (cssValue.getValueType() != CssValueType.IDENTIFIER)
                {
                    return null;
                }

                if (fontName.length() > 0)
                {
                    fontName.append(' ');
                }
                msStringBuilder.append(fontName, (String)cssValue.getValue());
            }
            return fontName.toString();
        }

        // Nothing of the above. This is an error.
        return null;
    }

    private static /*CssFontFaceFormat*/int parseFormatArgument(String value)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(msString.trim(value))))
        {
            case /*"truetype"*/0:
                return CssFontFaceFormat.TRUE_TYPE;
            case /*"woff"*/1:
                return CssFontFaceFormat.WOFF;
            case /*"embedded-opentype"*/2:
                return CssFontFaceFormat.EMBEDDED_OPENTYPE;
            case /*"woff2"*/3:
                return CssFontFaceFormat.WOFF_2;
            case /*"collection"*/4:
                return CssFontFaceFormat.COLLECTION;
            case /*"svg"*/5:
                return CssFontFaceFormat.SVG;
            case /*"opentype"*/6:
                return CssFontFaceFormat.OPEN_TYPE;
            default:
                return CssFontFaceFormat.UNKNOWN;
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"truetype",
		"woff",
		"embedded-opentype",
		"woff2",
		"collection",
		"svg",
		"opentype"
	);

}

