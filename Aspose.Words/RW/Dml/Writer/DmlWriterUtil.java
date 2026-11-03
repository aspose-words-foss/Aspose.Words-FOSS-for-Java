// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2025 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.MathUtil;


/// <summary>
/// Contains various methods used when writing DML shapes.
/// </summary>
/*static*/ class DmlWriterUtil
{
	/* Simulation of static class by using private constructor */
	private DmlWriterUtil()
	{}

    /// <summary>
    /// Writes DML adjustable point.
    /// </summary>
    static void writeDmlAdjustablePoint(DmlAdjustablePoint point, NrxXmlBuilder builder)
    {
        builder.writeElementWithAttributes("a:pt", "x", point.getX().getString(), "y", point.getY().getString());
    }

    /// <summary>
    /// Writes percentage offset rectangle.
    /// </summary>
    static void writePercentageOffsetRectangle(DmlPercentageOffsetRectangle rect, String prefix,
        String tagName, boolean writeEmpty, NrxXmlBuilder builder, boolean isIsoStrict)
    {
        if (rect.isEmpty() && !writeEmpty)
            return;

        String tagNameWithPrefix = msString.format("{0}:{1}", prefix, tagName);

        builder.startElement(tagNameWithPrefix);
        if (!rect.isEmpty())
        {
            writePercentAttributeIfNotZero(prefix, "l", rect.getLeftOffset(), builder, isIsoStrict);
            writePercentAttributeIfNotZero(prefix, "t", rect.getTopOffset(), builder, isIsoStrict);
            writePercentAttributeIfNotZero(prefix, "r", rect.getRightOffset(), builder, isIsoStrict);
            writePercentAttributeIfNotZero(prefix, "b", rect.getBottomOffset(), builder, isIsoStrict);
        }
        builder.endElement(tagNameWithPrefix);
    }

    /// <summary>
    /// Writes a percentage type attribute if the value is not zero.
    /// </summary>
    private static void writePercentAttributeIfNotZero(String prefix, String attributeName,
        double value, NrxXmlBuilder builder, boolean isIsoStrict)
    {
        if (MathUtil.doubleToInt(DmlPercentageUtil.toDmlPercent(value)) != 0)
        {
            builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, attributeName),
                DmlPercentageUtil.toPercentOrDmlPercent(value, isIsoStrict));
        }
    }
}

