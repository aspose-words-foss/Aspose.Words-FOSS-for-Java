// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents the 'attr' CSS function, which is a souce of generated content for pseudo-elements.
/// </summary>
class PseudoElementContentAttr extends PseudoElementContentPart
{
    PseudoElementContentAttr(String attributeName)
    {
        Debug.assertTrue(StringUtil.hasChars(attributeName));
        mAttributeName = attributeName;
    }

    /// <summary>
    /// Parses parameters of the 'attr' CSS function.
    /// </summary>
    /// <returns>
    /// Parsed content or <c>null</c> if parameters are malformed.
    /// </returns>
    static PseudoElementContentAttr parse(CssFunctionValue value)
    {
        if ((!"attr".equals(value.getName())) || (value.getArguments().getCount() != 1))
        {
            // Error. Either function name or function syntax is invalid.
            return null;
        }

        CssValue firstParameter = value.getArguments().get(0);
        if (firstParameter.getValueType() != CssValueType.IDENTIFIER)
        {
            // Error. Attribute name must be an identifier.
            return null;
        }

        String attributeName = ((CssIdentifierValue)firstParameter).getValue();
        Debug.assertTrue(StringUtil.hasChars(attributeName));

        // In HTML, attribute names are compared in a case-insensitive manner. Note, however, that the specifications
        // doesn't define an algorithm for comparison of attribute names. Seems that both Crome and Firefox convert 
        // parameters of attr() functions to lowercase upon reading, and we stick to this behavior. However, this may lead
        // to unexpected results, because the HTML converts case of arguments in a different manner. It converts all ASCII
        // letters to lowercase and leave all other characters intact. For example, attribute name 'DÂTÄ' will be converted
        // to 'dÂtÄ' in HTML and it will be converted to 'dâtä' in CSS. As a result, attr(DÂTÄ) will not match the attribute
        // DÂTÄ="..." both in Chrome and Firefox but it will match the attribute in IE, which uses different casing rules
        // for attributes in CSS.
        attributeName = msString.toLowerInvariant(attributeName);

        return new PseudoElementContentAttr(attributeName);
    }

    /*override*/ void accept(IPseudoElementContentPartVisitor visitor)
    {
        visitor.visitAttr(mAttributeName);
    }


    private /*final*/ String mAttributeName;
}

