// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.DrColor;


/*static*/ class DmlFillWriter
{
	/* Simulation of static class by using private constructor */
	private DmlFillWriter()
	{}

    static void write(DmlFill fill, IDmlShapeWriterContext writer, boolean isTextEffect) throws Exception
    {
        write(fill, writer, isTextEffect, false);
    }

    /// <summary>
    /// Writes the DML fill with the option to specify whether placeholder colors that occur in chart style parts
    /// can be written to the current document part. Such a color has no particular color definition, and colors
    /// without a definition are not written to the document by default.
    /// </summary>
    static void write(DmlFill fill, IDmlShapeWriterContext writer, boolean isTextEffect,
        boolean writePlaceholderColor) throws Exception
    {
        if (fill == null)
            return;

        switch (fill.getDmlFillType())
        {
            case DmlFillType.BLIP_FILL:
                writeDmlBlipFill("a", (DmlBlipFill)fill, writer);
                break;
            case DmlFillType.GRADIENT_FILL:
                writeDmlGradientFill((DmlGradientFill)fill, writer, isTextEffect);
                break;
            case DmlFillType.GROUP_FILL:
                writeGroupFill(writer);
                break;
            case DmlFillType.NO_FILL:
                writeDmlNoFill(writer, isTextEffect);
                break;
            case DmlFillType.PATTERN_FILL:
                writeDmlPatternFill((DmlPatternFill)fill, writer, writePlaceholderColor);
                break;
            case DmlFillType.SOLID_FILL:
                writeDmlSolidFill((DmlSolidFill)fill, writer, isTextEffect);
                break;
            case DmlFillType.STYLE_FILL:
                // If fill isn't specified then use the fill provided by the shape's style.
                // So we have nothing to write here.
                break;
            default:
                throw new IllegalArgumentException("Unexpected Dml fill type.");
        }
    }

    static void writeDmlBlipFill(String prefix, DmlBlipFill blipFill, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        String tagName = msString.format("{0}:blipFill", prefix);

        builder.startElement(tagName);

        // If not present or zero, the DPI in the blip is used.
        if ((blipFill.getDotsPerInch() & 0xFFFFFFFFL) > 0)
            builder.writeAttributeUInt("dpi", blipFill.getDotsPerInch());

        final String ROT_ATTR_NAME = "rotWithShape";

        // WORDSNET-14807 Mimic MSW and write "rotWithShape" explicitly, when
        // shape has a tile fill. MSW uses "1" as a default value for this attribute.
        if ((blipFill.getBlipFillMode() instanceof DmlBlipFillTile) || !blipFill.getRotateWithShape())
            builder.writeAttribute(ROT_ATTR_NAME, blipFill.getRotateWithShape());

        writeBlip(blipFill.getBlip(), writer, "a:blip");
        // For converted VML shapes currently skip writing empty "srcRect".
        // Although mimic MSW behavior does it.
        DmlWriterUtil.writePercentageOffsetRectangle(blipFill.getSourceRectangle(), "a", "srcRect", false,
            builder, isIsoStrict);
        blipFill.getBlipFillMode().write(writer);

        builder.endElement(tagName);
    }

    /// <summary>
    /// Writes Blip.
    /// </summary>
    static void writeBlip(DmlBlip blip, IDmlShapeWriterContext writer, String name) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startElement(name);

        writeBlipImage(blip, writer);

        if (blip.getCompressionState() != DmlCompressionState.EMAIL)
            builder.writeAttribute("cstate", DmlEnum.compressionStateToDml(blip.getCompressionState()));

        builder.writeAttribute("xmlns:r", DocxNamespaces.getNamespace(DocxNamespace.RELATIONSHIPS, isIsoStrict));

        DmlEffectsWriter.write(blip.getEffects(), writer);
        DmlExtensionListWriter.write(blip.getExtensions(), writer);

        builder.endElement(name);
    }

    /// <summary>
    /// Writes Blip image data (image binary data or/and image link).
    /// </summary>
    static void writeBlipImage(DmlBlip blip, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        // WORDSNET-17960 If the length of the embedded image is zero, then there is nothing to write.
        if (blip.hasEmbedImage())
        {
            String imageId = writer.writeImageBinData(blip.getEmbedImage());
            builder.writeAttribute("r:embed", imageId);
        }
        if (StringUtil.hasChars(blip.getImageLink()))
        {
            String imageLinkId = writer.writeImageLink(blip.getImageLink());
            builder.writeAttribute("r:link", imageLinkId);
        }
    }

    /// <summary>
    /// Writes patterned fill.
    /// </summary>
    private static void writeDmlPatternFill(DmlPatternFill patternFill, IDmlShapeWriterContext writer,
        boolean writePlaceholderColor)
    {
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement("a:pattFill");
        builder.writeAttribute("prst", DmlEnum.patternTypeToDml(patternFill.getFillPresetPattern()));

        // The document becomes corrupted if the color is not specified (except chart style part).
        DmlColor foreColor = patternFill.getForegroundColor();
        if (!foreColor.isEmpty() || (writePlaceholderColor && (foreColor.getColorType() == DmlColorType.PLACEHOLDER_COLOR)))
        {
            builder.startElement("a:fgClr");
            DmlColorWriter.write(patternFill.getForegroundColor(), writer);
            builder.endElement("a:fgClr");
        }

        // The document becomes corrupted if the color is not specified (except chart style part).
        DmlColor backColor = patternFill.getBackgroundColor();
        if (!backColor.isEmpty() || (writePlaceholderColor && (backColor.getColorType() == DmlColorType.PLACEHOLDER_COLOR)))
        {
            builder.startElement("a:bgClr");
            DmlColorWriter.write(patternFill.getBackgroundColor(), writer);
            builder.endElement("a:bgClr");
        }

        builder.endElement("a:pattFill");
    }

    /// <summary>
    /// Writes gradient fill.
    /// </summary>
    private static void writeDmlGradientFill(DmlGradientFill gradientFill, IDmlShapeWriterContext writer,
        boolean isTextEffect)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);

        String prefix = isTextEffect ? "w14" : "a";
        String tagName = msString.format("{0}:gradFill", prefix);
        builder.startElement(tagName);

        builder.writeAttributeIfNotDefault("flip", DmlEnum.tileFlipModeToDml(gradientFill.getTileFlipMode()), "none");
        builder.writeAttribute("rotWithShape", gradientFill.getRotateWithShape());

        writeDmlGradientStops(gradientFill, writer, isTextEffect);
        gradientFill.getGradient().write(writer, isTextEffect);
        DmlWriterUtil.writePercentageOffsetRectangle(gradientFill.getTileRectangle(), prefix, "tileRect", false,
            builder, isIsoStrict);

        builder.endElement(tagName);
    }

    /// <summary>
    /// Writes gradient stops.
    /// </summary>
    private static void writeDmlGradientStops(DmlGradientFill gradientFill, IDmlShapeWriterContext writer,
        boolean isTextEffect)
    {
        NrxXmlBuilder builder = writer.getBuilder();

        String tagName = isTextEffect ? "w14:gsLst" : "a:gsLst";
        builder.startElement(tagName);
        for (DmlGradientStop stop : gradientFill.getGradientStops())
            writeDmlGradientStop(stop, writer, isTextEffect);
        builder.endElement(tagName);
    }

    /// <summary>
    /// Writes gradient stop.
    /// </summary>
    private static void writeDmlGradientStop(DmlGradientStop stop, IDmlShapeWriterContext writer, boolean isTextEffect)
    {
        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        NrxXmlBuilder builder = writer.getBuilder();
        String prefix = isTextEffect ? "w14" : "a";
        String tagName = msString.format("{0}:gs", prefix);
        builder.startElement(tagName);

        if (isIsoStrict)
            stop.setPosition(DmlPercentageUtil.toDmlPercentPrecision(stop.getPosition()));

        builder.writeAttribute((isTextEffect ? "w14:pos" : "pos"),
            DmlPercentageUtil.toPercentOrDmlPercent(stop.getPosition(), isIsoStrict));

        DmlColorWriter.write(prefix, stop.getColor(), writer);
        builder.endElement(tagName);
    }

    /// <summary>
    /// Writes solid fill.
    /// </summary>
    private static void writeDmlSolidFill(DmlSolidFill solidFill, IDmlShapeWriterContext writer, boolean isTextEffect)
    {
        NrxXmlBuilder builder = writer.getBuilder();

        String prefix = (isTextEffect) ? "w14" : "a";
        String tagName = msString.format("{0}:solidFill", prefix);

        DmlColor color;
        // WORDSNET-12980 If color was not initialized yet, we should initialize it.
        // Otherwise we will write empty string for the color and
        // this causes MS Word to crash.
        // WORDSNET-13497 Except placeholder color.
        if (solidFill.isColorEmpty() && (solidFill.getColor().getColorType() != DmlColorType.PLACEHOLDER_COLOR))
        {
            color = DmlColor.createFromDrColor(DrColor.EMPTY);
            color.setColorModifiers(solidFill.getColor().getColorModifiers());
        }
        else
        {
            color = solidFill.getColor();
        }

        builder.startElement(tagName);
        DmlColorWriter.write(prefix, color, writer);
        builder.endElement(tagName);
    }

    /// <summary>
    /// Writes NoFill fill.
    /// </summary>
    private static void writeDmlNoFill(IDmlShapeWriterContext writer, boolean isTextEffect)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.writeEmptyElement(isTextEffect ? "w14:noFill" : "a:noFill");
    }

    /// <summary>
    /// Writes group fill.
    /// </summary>
    private static void writeGroupFill(IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.writeEmptyElement("a:grpFill");
    }



}

