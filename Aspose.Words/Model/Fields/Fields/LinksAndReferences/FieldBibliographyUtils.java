// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableInt32;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.SystemPal;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msUShort;
import com.aspose.NullableExtension;


/// <summary>
/// Common utils for bibliography fields.
/// </summary>
/*static*/ class FieldBibliographyUtils
{
	/* Simulation of static class by using private constructor */
	private FieldBibliographyUtils()
	{}

    /// <summary>
    /// Parses string representation of LCID.
    /// </summary>
    static int getEffectiveLcid(String value)
    {
        NullableInt32 lcid = tryParseLcid(value);
        if (lcid.hasValue())
            return lcid.getValue();

        msCultureInfo culture = SystemPal.tryGetCulture(value);
        if (culture != null)
            return culture.getLCID();

        return getCurrentCultureLcid();
    }

    private static NullableInt32 tryParseLcid(String value)
    {
        if (msString.isNullOrEmpty(value))
            return NullableInt32.NULL;

        NullableInt32 result = FormatterPal.parseNullableInt(value);
        if (!result.hasValue())
            return NullableInt32.NULL;

        if (result.getValue() < 0)
            return NullableInt32.NULL;

        // same as unchecked((ushort)result.Value)
        int lcid = result.getValue() % ((msUShort.MAX_VALUE & 0xFFFF) + 1);
        if (lcid == 0)
            return NullableInt32.NULL;

        return NullableExtension.asNullable(lcid);
    }

    /// <summary>
    /// Returns current culture LCID.
    /// </summary>
    /// <returns></returns>
    static int getCurrentCultureLcid()
    {
        return SystemPal.getCurrentCulture().getLCID();
    }
}

