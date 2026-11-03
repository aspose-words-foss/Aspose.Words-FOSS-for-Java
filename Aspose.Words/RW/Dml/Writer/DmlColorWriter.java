// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/16/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.MathUtil;


/*static*/ class DmlColorWriter
{
	/* Simulation of static class by using private constructor */
	private DmlColorWriter()
	{}

    static void write(DmlColor color, IDmlShapeWriterContext writer)
    {
        write("a", color, writer);
    }

    static void write(String prefix, DmlColor color, IDmlShapeWriterContext writer)
    {
        if (color == null)
            return;

        switch (color.getColorType())
        {
            case DmlColorType.HEX_RGB_COLOR:
                writeHexRgbColor(prefix, (DmlHexRgbColor)color, writer);
                break;
            case DmlColorType.HSL_COLOR:
                writeHslColor(prefix, (DmlHslColor)color, writer);
                break;
            case DmlColorType.PERCENTAGE_RGB_COLOR:
                writePercentageRgbColor(prefix, (DmlPercentageRgbColor)color, writer);
                break;
            case DmlColorType.PLACEHOLDER_COLOR:
                writePlaceholderColor(prefix, (DmlPlaceholderColor)color, writer);
                break;
            case DmlColorType.PRESET_COLOR:
                writePresetColor(prefix, (DmlPresetColor)color, writer);
                break;
            case DmlColorType.SCHEME_COLOR:
                writeSchemeColor(prefix, (DmlSchemeColor)color, writer);
                break;
            case DmlColorType.SYSTEM_COLOR:
                writeSystemColor(prefix, (DmlSystemColor)color, writer);
                break;
            default:
                throw new IllegalArgumentException("Unexpected Dml color type.");
        }
    }

    private static void writeHexRgbColor(String prefix, DmlHexRgbColor hexRgbColor, IDmlShapeWriterContext writer)
    {
        String tagName = msString.format("{0}:srgbClr", prefix);
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);

        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "val"), hexRgbColor.getValue());
        writeHexRgbColorIndex(hexRgbColor, writer);
        DmlColorModifiersWriter.write(prefix, hexRgbColor.getColorModifiers(), writer);

        builder.endElement(tagName);
    }

    /// <summary>
    /// Writes color index and related attributes.
    /// </summary>
    /// <param name="hexRgbColor">Color.</param>
    /// <param name="writer">Document writer.</param>
    private static void writeHexRgbColorIndex(DmlHexRgbColor hexRgbColor, IDmlShapeWriterContext writer)
    {
        Debug.assertTrue((hexRgbColor != null) && (writer != null));

        if (hexRgbColor.getColorIndex() == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        final String CLR_INDEX_PREFIX = "a14";
        String clrIndexVal = msString.format("{0}:legacySpreadsheetColorIndex", CLR_INDEX_PREFIX);

        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        String nameSpace = DocxNamespaces.getNamespace(DocxNamespace.MARKUP_COMPATIBILITY, isIsoStrict);

        builder.writeAttribute(clrIndexVal, hexRgbColor.getColorIndex());
        builder.writeAttribute("mc:Ignorable", CLR_INDEX_PREFIX);

        builder.writeAttribute("xmlns:a14", DmlExtensionsNamespace.DRAWING_MAIN);

        builder.writeAttribute("xmlns:mc", nameSpace);
    }

    private static void writeHslColor(String prefix, DmlHslColor hslColor, IDmlShapeWriterContext writer)
    {
        String tagName = msString.format("{0}:hslClr", prefix);
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "hue"), 
            hslColor.getHue().getValue());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "sat"),
            DmlPercentageUtil.toPercentOrDmlPercent(hslColor.getSaturation(), isIsoStrict));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "lum"),
            DmlPercentageUtil.toPercentOrDmlPercent(hslColor.getLuminance(), isIsoStrict));

        DmlColorModifiersWriter.write(prefix, hslColor.getColorModifiers(), writer);

        builder.endElement(tagName);
    }

    private static void writePercentageRgbColor(String prefix, DmlPercentageRgbColor percentageRgbColor,
        IDmlShapeWriterContext writer)
    {
        String tagName = msString.format("{0}:scrgbClr", prefix);
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);

        writePercentWithZeroCheck(writer, prefix, "r", percentageRgbColor.getR());
        writePercentWithZeroCheck(writer, prefix, "g", percentageRgbColor.getG());
        writePercentWithZeroCheck(writer, prefix, "b", percentageRgbColor.getB());

        DmlColorModifiersWriter.write(prefix, percentageRgbColor.getColorModifiers(), writer);
        builder.endElement(tagName);
    }

    /// <summary>
    /// Writes percentage attribute to current element. Interprets values that are less than precision of
    /// a double value as zero.
    /// </summary>
    private static void writePercentWithZeroCheck(IDmlShapeWriterContext writer, String prefix, 
        String attribute, double value)
    {
        String strValue = DmlPercentageUtil.toPercentOrDmlPercent(!MathUtil.isZero(value) ? value : 0d,
            writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        writer.getBuilder().writeAttribute(DmlNamespaceUtil.getAttrName(prefix, attribute), strValue);
    }

    private static void writePlaceholderColor(String prefix, DmlPlaceholderColor placeholderColor,
        IDmlShapeWriterContext writer)
    {
        String tagName = msString.format("{0}:schemeClr", prefix);
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "val"), "phClr");

        DmlColorModifiersWriter.write(prefix, placeholderColor.getColorModifiers(), writer);

        builder.endElement(tagName);
    }

    private static void writePresetColor(String prefix, DmlPresetColor presetColor, IDmlShapeWriterContext writer)
    {
        String tagName = msString.format("{0}:prstClr", prefix);
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "val"), presetColor.getValue());

        DmlColorModifiersWriter.write(prefix, presetColor.getColorModifiers(), writer);

        builder.endElement(tagName);
    }

    private static void writeSchemeColor(String prefix, DmlSchemeColor schemeColor, IDmlShapeWriterContext writer)
    {
        String tagName = msString.format("{0}:schemeClr", prefix);
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "val"), ThemeColorConverter.toString(schemeColor.getValue()));

        DmlColorModifiersWriter.write(prefix, schemeColor.getColorModifiers(), writer);

        builder.endElement(tagName);
    }

    private static void writeSystemColor(String prefix, DmlSystemColor systemColor, IDmlShapeWriterContext writer)
    {
        String tagName = msString.format("{0}:sysClr", prefix);
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "val"), systemColor.getValue());
        builder.writeAttributeIfNotDefault(DmlNamespaceUtil.getAttrName(prefix, "lastClr"), systemColor.getLastColor(), "");

        DmlColorModifiersWriter.write(prefix, systemColor.getColorModifiers(), writer);

        builder.endElement(tagName);
    }
}

