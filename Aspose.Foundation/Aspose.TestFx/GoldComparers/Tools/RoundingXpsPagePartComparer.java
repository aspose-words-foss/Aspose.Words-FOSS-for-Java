// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/12/2018 by Michael Morozoff

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msMath;


/// <summary>
/// Implements comparing of Xps page parts with rounded points
/// </summary>
public class RoundingXpsPagePartComparer implements IPackagePartComparer
{
    public static /*final*/ RoundingXpsPagePartComparer INSTANCE = new RoundingXpsPagePartComparer();

    public boolean compareBuffers(String partName, byte[] buffer1, byte[] buffer2, boolean isPreferXmlDiff) throws Exception
    {
        if (StringUtil.hasChars(partName) && partName.endsWith(".fpage") && buffer1 != null && buffer2 != null)
        {
            // Since we do not accept rounded golds always I'd rather round them all the time
        }

        return DefaultPackagePartComparer.INSTANCE.compareBuffers(partName, buffer1, buffer2, isPreferXmlDiff);
    }

    private static void roundAttribute(org.w3c.dom.Attr a)
    {
        if (a != null)
        {
            String v = roundDouble(a.getNodeValue());
            if (v != null)
                a.setNodeValue(v);
        }
    }

    private static String roundDouble(String v)
    {
        if (!StringUtil.hasChars(v))
            return v;

        // Number of decimal digits after rounding, 3 should be enough, thats 1/10000th of a point, ot 1/10th of Li in layout.
        final int NUM_DIGITS = 4;
        double d = FormatterPal.parseDouble(v);
        return FormatterPal.doubleToStr(msMath.round(d, NUM_DIGITS));
    }
}

