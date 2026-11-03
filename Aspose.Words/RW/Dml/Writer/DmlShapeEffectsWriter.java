// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.msMath;


/// <summary>
/// Instance class for writing DrawingML shape effects.
/// </summary>
/*static*/ class DmlShapeEffectsWriter
{
	/* Simulation of static class by using private constructor */
	private DmlShapeEffectsWriter()
	{}

    static void write(DmlShapeEffectsCollection shapeEffectsCollection, IDmlShapeWriterContext writer,
        boolean isThemeWriting) throws Exception
    {
        if (!isThemeWriting)
        {
            // For theme effectLst is required element.
            if (shapeEffectsCollection == null)
                return;

            // Do not write effects that are inherited from theme for shapes. 
            if (shapeEffectsCollection.isTheme())
                return;
        }

        NrxXmlBuilder builder = writer.getBuilder();

        String tagName = "a:effectLst";
        if ((shapeEffectsCollection != null) && shapeEffectsCollection.isEffectDag())
            tagName = "a:effectDag";

        builder.startElement(tagName);

        if ((shapeEffectsCollection != null) && shapeEffectsCollection.isEffectDag())
        {
            builder.writeAttribute("name", shapeEffectsCollection.getDagName());
            builder.writeAttributeIfNotDefault("type", DmlEnum.effectDagTypeToDml(shapeEffectsCollection.getDagType()), "sib");
        }

        if (shapeEffectsCollection != null) 
            for (DmlShapeEffect dmlShapeEffect : shapeEffectsCollection)
                writeEffect(dmlShapeEffect, writer);

        builder.endElement(tagName);
    }

    private static void writeEffect(DmlShapeEffect dmlShapeEffect, IDmlShapeWriterContext writer) throws Exception
    {
        switch (dmlShapeEffect.getEffectType())
        {
            case DmlShapeEffectType.BLUR:
                writeBlur((DmlShapeBlurEffect)dmlShapeEffect, writer);
                break;
            case DmlShapeEffectType.FILL_OVERLAY:
                writeFillOverlay((DmlShapeFillOverlayEffect)dmlShapeEffect, writer);
                break;
            case DmlShapeEffectType.GLOW:
                writeGlow((DmlShapeGlowEffect)dmlShapeEffect, writer, false);
                break;
            case DmlShapeEffectType.INNER_SHADOW:
                writeInnerShadow((DmlShapeInnerShadowEffect)dmlShapeEffect, writer);
                break;
            case DmlShapeEffectType.OUTER_SHADOW:
                writeOuterShadow((DmlShapeOuterShadowEffect)dmlShapeEffect, writer, false);
                break;
            case DmlShapeEffectType.PRESET_SHADOW:
                writePresetShadow((DmlShapePresetShadowEffect)dmlShapeEffect, writer);
                break;
            case DmlShapeEffectType.REFLECTION:
                writeReflection((DmlShapeReflectionEffect)dmlShapeEffect, writer, false);
                break;
            case DmlShapeEffectType.SOFT_EDGES:
                writeSoftEdges((DmlShapeSoftEdgeEffect)dmlShapeEffect, writer);
                break;
            default:
               throw new IllegalArgumentException("Unexpected Dml shape effect type.");
        }
    }

    private static void writeSoftEdges(DmlShapeSoftEdgeEffect shapeSoftEdge, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.writeElementWithAttributes("a:softEdge", "rad", shapeSoftEdge.getRadius());
    }

    static void writeReflection(DmlShapeReflectionEffect shapeReflection, IDmlShapeWriterContext writer,
        boolean isTextEffect)
    {
        String prefix = isTextEffect ? "w14" : "a";
        String tagName = msString.format("{0}:reflection", prefix);
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        // Note: optional attributes are written because 
        // MS Word does not process text effects with omitted default attributes properly.
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "blurRad"), 
            shapeReflection.getBlurRadius());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "stA"), 
            DmlPercentageUtil.toPercentOrDmlPercent(shapeReflection.getStartAlpha(), isIsoStrict));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "stPos"), 
            DmlPercentageUtil.toPercentOrDmlPercent(shapeReflection.getStartPosition(), isIsoStrict));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "endA"), 
            DmlPercentageUtil.toPercentOrDmlPercent(shapeReflection.getEndAlpha(), isIsoStrict));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "endPos"), 
            DmlPercentageUtil.toPercentOrDmlPercent(shapeReflection.getEndPosition(), isIsoStrict));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "dist"), 
            shapeReflection.getDistance());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "dir"), 
            shapeReflection.getDirection().getValue());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "fadeDir"), 
            shapeReflection.getFadeDirection().getValue());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "sx"), 
            DmlPercentageUtil.toPercentOrDmlPercent(shapeReflection.getHorizontalScale(), isIsoStrict));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "sy"), 
            DmlPercentageUtil.toPercentOrDmlPercent(shapeReflection.getVerticalScale(), isIsoStrict));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "kx"), 
            shapeReflection.getHorizontalSkew().getValue());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "ky"), 
            shapeReflection.getVerticalSkew().getValue());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "algn"), 
            DmlEnum.rectangleAlignmentToDml(shapeReflection.getAlignment()));
        if (!shapeReflection.getRotateWithShape())
            builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "rotWithShape"), shapeReflection.getRotateWithShape());
        builder.endElement(tagName);
    }

    private static void writePresetShadow(DmlShapePresetShadowEffect shapePresetShadow, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:prstShdw");
        builder.writeAttribute("prst", DmlEnum.presetShadowToDml(shapePresetShadow.getPresetShadow()));
        builder.writeAttribute("dist", shapePresetShadow.getDistance());
        builder.writeAttribute("dir", msMath.round(shapePresetShadow.getDirection().getValue(), 0));
        DmlColorWriter.write(shapePresetShadow.getColor(), writer);
        builder.endElement("a:prstShdw");
    }

    static void writeOuterShadow(DmlShapeOuterShadowEffect shapeOuterShadow, IDmlShapeWriterContext writer,
                                          boolean isTextEffect)
    {
        String prefix = isTextEffect ? "w14" : "a";
        String tagName = isTextEffect ? "w14:shadow" : "a:outerShdw";
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        // Note: optional attributes are written because 
        // MS Word does not process text effects with omitted default attributes properly.
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "blurRad"), 
            shapeOuterShadow.getBlurRadius());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "dist"), 
            shapeOuterShadow.getDistance());

        // Direction value have to be written as integer.
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "dir"),
            msMath.round(shapeOuterShadow.getDirection().getValue(), 0));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "sx"), 
            DmlPercentageUtil.toPercentOrDmlPercent(shapeOuterShadow.getHorizontalScale(), isIsoStrict));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "sy"), 
            DmlPercentageUtil.toPercentOrDmlPercent(shapeOuterShadow.getVerticalScale(), isIsoStrict));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "kx"), 
            msMath.round(shapeOuterShadow.getHorizontalSkew().getValue(), 0));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "ky"),
             msMath.round(shapeOuterShadow.getVerticalSkew().getValue(), 0));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "algn"),
            DmlEnum.rectangleAlignmentToDml(shapeOuterShadow.getAlignment()));
        if (!shapeOuterShadow.getRotateWithShape())
            builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "rotWithShape"), shapeOuterShadow.getRotateWithShape());

        DmlColorWriter.write(prefix, shapeOuterShadow.getColor(), writer);
        builder.endElement(tagName);
    }

    private static void writeFillOverlay(DmlShapeFillOverlayEffect shapeFillOverlay, IDmlShapeWriterContext writer) throws Exception
    {
        DmlEffectsWriter.writeFillOverlay(shapeFillOverlay.getFillOverlay(), writer);
    }

    private static void writeInnerShadow(DmlShapeInnerShadowEffect shapeInnerShadow, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:innerShdw");
        builder.writeAttribute("blurRad", shapeInnerShadow.getBlurRadius());
        builder.writeAttribute("dist", shapeInnerShadow.getDistance());
        builder.writeAttribute("dir", shapeInnerShadow.getDirection().getValue());
        DmlColorWriter.write(shapeInnerShadow.getColor(), writer);
        builder.endElement("a:innerShdw");
    }

    static void writeGlow(DmlShapeGlowEffect shapeGlow, IDmlShapeWriterContext writer, boolean isTextEffect)
    {
        String prefix = isTextEffect ? "w14" : "a";
        String tagName = msString.format("{0}:glow", prefix);

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "rad"), shapeGlow.getRadius());
        DmlColorWriter.write(prefix, shapeGlow.getColor(), writer);
        builder.endElement(tagName);
    }

    private static void writeBlur(DmlShapeBlurEffect shapeBlur, IDmlShapeWriterContext writer)
    {
        DmlEffectsWriter.writeBlur(shapeBlur.getBlur(), writer);
    }
}

