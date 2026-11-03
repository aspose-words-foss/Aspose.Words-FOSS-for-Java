// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.CssEscape;


/// <summary>
/// Represents the CSS ID selector. For example, '#id'.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#id-selectors
/// </remarks>
class CssIdSelector extends CssSimpleSelector
{
    CssIdSelector(String id)
    {
        Debug.assertTrue(StringUtil.hasChars(id));
        mId = id;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        String idAttributeValue = element.getAttributeValue("id");
        if (idAttributeValue == null)
        {
            return false;
        }

        if (documentMode == CssDocumentMode.QUIRKS)
        {
            return StringUtil.equalsIgnoreCase(mId, idAttributeValue);
        }
        else
        {
            // Case-sensitive ordinal comparison.
            return msString.equals(mId, idAttributeValue);
        }
    }

    /*override*/ CssSelectorSpecificity getSpecificity() { return G_SPECIFICITY; }

    /*override*/ String toCss()
    {
        return "#" + CssEscape.escapeIdentifier(mId);
    }

    String getId() { return mId; }

    protected /*override*/ String makePreferableStyleName()
    {
        return mId;
    }

    private /*final*/ String mId;

    private static /*final*/ CssSelectorSpecificity G_SPECIFICITY = new CssSelectorSpecificity(1, 0, 0);
}

