// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.CssEscape;


/// <summary>
/// Represents the CSS class selector. For example, '.class'.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#class-html
/// </remarks>
class CssClassSelector extends CssSimpleSelector
{
    CssClassSelector(String className)
    {
        Debug.assertTrue(StringUtil.hasChars(className));
        mClassName = className;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // For performance reasons, we use a separate method to get 'class' attribute value 
        // instead of fetching it from the attribute collection of the element.
        String[] classes = element.getClasses();
        if (classes.length == 0)
        {
            return false;
        }

        for (String className : classes)
        {
            if (documentMode == CssDocumentMode.QUIRKS)
            {
                if (StringUtil.equalsIgnoreCase(className, mClassName))
                {
                    return true;
                }
            }
            else
            {
                // Ordinal case-sensitive comparison.
                if (msString.equals(className, mClassName))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /*override*/ CssSelectorSpecificity getSpecificity() { return G_SPECIFICITY; }

    /*override*/ String toCss()
    {
        // WORDSNET-12814 We use class names to store style names in HTML format. However, style names (even processed
        // with our style id generator) are not always valid CSS identifiers (for example, style names can start with
        // a digit) so we must properly escape such style names before exporting them as class names.
        return "." + CssEscape.escapeIdentifier(mClassName);
    }

    String getClassName() { return mClassName; }

    protected /*override*/ String makePreferableStyleName()
    {
        return mClassName;
    }

    private /*final*/ String mClassName;

    private static /*final*/ CssSelectorSpecificity G_SPECIFICITY = new CssSelectorSpecificity(0, 1, 0);
}

