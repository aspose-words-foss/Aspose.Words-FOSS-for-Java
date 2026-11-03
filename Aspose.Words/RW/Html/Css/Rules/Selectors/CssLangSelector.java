// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.CssEscape;


/// <summary>
/// Represents the CSS :lang() pseudo-class selector. For example, ':lang("en")'.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#lang-pseudo
/// </remarks>
class CssLangSelector extends CssPseudoClassSelector
{
    CssLangSelector(String language)
    {
        Debug.assertTrue(StringUtil.hasChars(language));
        mLanguage = language;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        String elementLanguage = element.getLanguage();
        Debug.assertTrue(elementLanguage != null);

        if (StringUtil.equalsIgnoreCase(elementLanguage, mLanguage))
        {
            return true;
        }

        String languagePrefix = mLanguage + '-';
        if (elementLanguage.length() < languagePrefix.length())
        {
            return false;
        }
        String elementLanguagePrefix = elementLanguage.substring((0), (0) + (languagePrefix.length()));

        return StringUtil.equalsIgnoreCase(elementLanguagePrefix, languagePrefix);
    }

    /*override*/ String toCss()
    {
        return ":lang(" + CssEscape.escapeIdentifier(mLanguage) + ")";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "lang(" + mLanguage + ")";
    }

    private /*final*/ String mLanguage;
}

