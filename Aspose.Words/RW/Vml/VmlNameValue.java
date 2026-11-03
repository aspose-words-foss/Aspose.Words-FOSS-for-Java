// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/08/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.StringUtil;


class VmlNameValue
{
    private VmlNameValue(String name, String value)
    {
        mName = name;
        mValue = value;
    }

    static VmlNameValue fromString(String s)
    {
        String[] nameValue = msString.split(s, ':');

        if (nameValue.length < 2)
            return null;

        // WORDSNET-25533 Property names are case-insensitive and we convert them all to lower case for convenience.
        // Property values are case-insensitive too, except for ID values (used by "mso-next-textbox") and the "font-family"
        // property value (font family name).
        // Another property with a case sensitive value is "font" but MS Word doesn't load font family from that property
        // and our code doesn't support it either. As a result, it doesn't matter if we convert that property's value
        // to lower case.
        String name = msString.toLowerInvariant(msString.trim(nameValue[0]));
        String value = msString.trim(nameValue[1]);
        if ((!"font-family".equals(name)) && !msString.startsWith(value, "#", StringComparison.ORDINAL))
        {
            value = msString.toLowerInvariant(value);
        }

        return (StringUtil.hasChars(name) && StringUtil.hasChars(value))
            ? new VmlNameValue(name, value)
            : null;
    }

    String getName() { return mName; };

    private String mName;

    String getValue() { return mValue; };

    private String mValue;
}

