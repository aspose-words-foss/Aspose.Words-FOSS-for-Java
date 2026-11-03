// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/09/2015 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a font shorthand CSS property value.
/// </summary>
class CssFontPropertyValue extends CssPropertyValue
{
    CssFontPropertyValue(
        CssValue fontStyle,
        CssValue fontVariant,
        CssValue fontWeight,
        CssValue fontSize,
        CssValue lineHeight,
        CssValue fontFamily)
    {
    	super(createValues(fontStyle, fontVariant, fontWeight, fontSize, lineHeight, fontFamily));
	
        Debug.assertTrue(fontSize != null);
        Debug.assertTrue(fontFamily != null);

        mFontStyle = fontStyle;
        mFontVariant = fontVariant;
        mFontWeight = fontWeight;
        mFontSize = fontSize;
        mLineHeight = lineHeight;
        mFontFamily = fontFamily;
    }

    CssFontPropertyValue(
        CssValue fontSize,
        CssValue fontFamily)
    {
    	this(null, null, null, fontSize, null, fontFamily);
	
        // Empty constructor.
    }

    protected /*override*/ void valueToCss(StringBuilder sb)
    {
        final char DELIMITER = ' ';
        boolean delimiterNeeded = false;

        if (mFontStyle != null)
        {
            mFontStyle.toCss(sb);
            delimiterNeeded = true;
        }

        if (mFontVariant != null)
        {
            if (delimiterNeeded)
                sb.append(DELIMITER);
            mFontVariant.toCss(sb);
            delimiterNeeded = true;
        }

        if (mFontWeight != null)
        {
            if (delimiterNeeded)
                sb.append(DELIMITER);
            mFontWeight.toCss(sb);
            delimiterNeeded = true;
        }

        if (delimiterNeeded)
            sb.append(DELIMITER);
        mFontSize.toCss(sb);

        if (mLineHeight != null)
        {
            sb.append('/');
            mLineHeight.toCss(sb);
        }

        sb.append(DELIMITER);
        mFontFamily.toCss(sb);
    }

    private static CssValueList createValues(
        CssValue fontStyle,
        CssValue fontVariant,
        CssValue fontWeight,
        CssValue fontSize,
        CssValue lineHeight,
        CssValue fontFamily)
    {
        Debug.assertTrue(fontSize != null);
        Debug.assertTrue(fontFamily != null);

        CssValueList values = new CssValueList();
        if (fontStyle != null)
            values.add(fontStyle);
        if (fontVariant != null)
            values.add(fontVariant);
        if (fontWeight != null)
            values.add(fontWeight);
        values.add(fontSize);
        if (lineHeight != null)
            values.add(lineHeight);
        values.add(fontFamily);
        return values;
    }

    private /*final*/ CssValue mFontStyle;
    private /*final*/ CssValue mFontVariant;
    private /*final*/ CssValue mFontWeight;
    private /*final*/ CssValue mFontSize;
    private /*final*/ CssValue mLineHeight;
    private /*final*/ CssValue mFontFamily;
}

