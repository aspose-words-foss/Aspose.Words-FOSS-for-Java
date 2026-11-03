// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2025 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;


class CssHashValue extends CssValue
{
    CssHashValue(String text)
    {
    	super(CssValueType.HASH, text);
	
        mText = text;
    }

    static CssHashValue fromColor(DrColor color)
    {
        return fromColor(color.toArgb());
    }

    static CssHashValue fromColor(int argb)
    {
        int b = argb & 0xFF;
        argb >>= 8;
        int g = argb & 0xFF;
        argb >>= 8;
        int r = argb & 0xFF;
        return fromColor(r, g, b);
    }

    static CssHashValue fromColor(int r, int g, int b)
    {
        return new CssHashValue(
            FormatterPal.intToStrX2Lower(r & 0xFF) +
            FormatterPal.intToStrX2Lower(g & 0xFF) +
            FormatterPal.intToStrX2Lower(b & 0xFF));
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        sb.append('#');
        msStringBuilder.append(sb, getText());
    }

    /*override*/ DrColor parseAsColor()
    {
        return CssColorParser.parseHexColor(getText());
    }

    String getText() { return mText; };

    private String mText;

    protected /*override*/ boolean doEquals(CssValue other)
    {
        return msString.equals(getText(), ((CssHashValue)other).getText());
    }
}

