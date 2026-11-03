// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.NullableBool;


/// <summary>
/// Represents a CSS @font-face rule.
/// </summary>
/// <remarks>
/// Please see http://www.w3.org/TR/css-fonts-3/#font-face-rule for more information.
/// </remarks>
class CssFontFaceRule extends CssRule
{
    CssFontFaceRule(CssDeclarationCollection declarations)
    {
    	super(CssRuleType.FONT_FACE);
	
        Debug.assertTrue(declarations != null);

        mDeclarations = declarations;
    }

    boolean declaresFontFamily(String fontFamily)
    {
        CssDeclaration fontFamilyDeclaration = getDeclarations().get("font-family");
        if ((fontFamilyDeclaration != null) &&
            (fontFamilyDeclaration.getValue() instanceof CssFontFamilyPropertyValue))
        {
            for (int i = 0; i < fontFamilyDeclaration.getValue().getCount(); i++)
            {
                String declaredFontFamily;
                CssValue fontFamilyValue = fontFamilyDeclaration.getValue().get(i);
                switch (fontFamilyValue.getValueType())
                {
                    case CssValueType.IDENTIFIER:
                    {
                        declaredFontFamily = ((CssIdentifierValue)fontFamilyValue).getValue();
                        break;
                    }
                    case CssValueType.STRING:
                    {
                        declaredFontFamily = ((CssStringValue)fontFamilyValue).getValue();
                        break;
                    }
                    default:
                    {
                        Debug.fail("Unexpected type of a CSS value.");
                        continue;
                    }
                }

                if (StringUtil.equalsOrdinalIgnoreCase(fontFamily, declaredFontFamily))
                {
                    return true;
                }
            }
        }

        return false;
    }

    ArrayList<CssFontSource> getSources()
    {
        CssDeclaration srcDeclaration = getDeclarations().get("src");
        if ((srcDeclaration != null) && (srcDeclaration.getValue() instanceof CssSrcPropertyValue))
        {
            CssSrcPropertyValue srcPropertyValue = (CssSrcPropertyValue)srcDeclaration.getValue();
            return srcPropertyValue.getFontSources();
        }

        return null;
    }

    /*FontStyle*/int getFontStyle()
    {
        return (/*FontStyle*/int)getFontStyleIntValue();
    }

    /*EmbeddedFontStyle*/int getEmbeddedFontStyle()
    {
        return (/*EmbeddedFontStyle*/int)getFontStyleIntValue();
    }

    /*override*/ String toCss()
    {
        return "@font-face { " + getDeclarations().getShorthandVersion().toCss() + " }";
    }

    CssDeclarationCollection getDeclarations() { return mDeclarations; };

    private CssDeclarationCollection mDeclarations;

    /// <summary>
    /// Converts "bold"/"italic" style combination of this rule to an integer value in range [0..3].
    /// "bold" sets the low bit and "italic" sets the high bit.
    /// </summary>
    private int getFontStyleIntValue()
    {
        // regular       = 0x0 (0b00)
        // bold          = 0x1 (0b01)
        // italic        = 0x2 (0b10)
        // bold + italic = 0x3 (0b11)
        int result = 0;
        result |= (CssUtil.isBoldFont(getDeclarations(), "font-weight") == NullableBool.TRUE)
            ? 0x1
            : 0x0;
        result |= (CssUtil.isItalicFont(getDeclarations(), "font-style") == NullableBool.TRUE)
            ? 0x2
            : 0x0;
        return result;
    }
}

