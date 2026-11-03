// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/17/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;
import com.aspose.MathUtil;


/*static*/ class DmlEffectsWriter
{
	/* Simulation of static class by using private constructor */
	private DmlEffectsWriter()
	{}

    static void write(ArrayList<DmlEffect> effects, IDmlShapeWriterContext writer) throws Exception
    {
        for (DmlEffect effect : effects)
        {
            switch (effect.getType())
            {
                case DmlEffectType.ALPHA_BI_LEVEL:
                    writeAlphaBiLevel((DmlAlphaBiLevelEffect)effect, writer);
                    break;
                case DmlEffectType.ALPHA_CEILING:
                    writeAlphaCeiling(writer);
                    break;
                case DmlEffectType.ALPHA_FLOOR:
                    writeAlphaFloor(writer);
                    break;
                case DmlEffectType.ALPHA_INVERSE:
                    writeAlphaInverse((DmlAlphaInverseEffect)effect, writer);
                    break;
                case DmlEffectType.ALPHA_MODULATE:
                    writeAlphaModulate((DmlAlphaModulateEffect)effect, writer);
                    break;
                case DmlEffectType.ALPHA_MODULATE_FIXED:
                    writeAlphaModulateFixed((DmlAlphaModulateFixedEffect)effect, writer);
                    break;
                case DmlEffectType.ALPHA_REPLACE:
                    writeAlphaReplace((DmlAlphaReplaceEffect)effect, writer);
                    break;
                case DmlEffectType.BI_LEVEL:
                    writeBiLevel((DmlBiLevelEffect)effect, writer);
                    break;
                case DmlEffectType.BLUR:
                    writeBlur((DmlBlurEffect)effect, writer);
                    break;
                case DmlEffectType.COLOR_CHANGE:
                    writeColorChange((DmlColorChangeEffect)effect, writer);
                    break;
                case DmlEffectType.COLOR_REPLACE:
                    writeColorReplace((DmlColorReplaceEffect)effect, writer);
                    break;
                case DmlEffectType.DUOTONE:
                    writeDuotone((DmlDuotoneEffect)effect, writer);
                    break;
                case DmlEffectType.FILL_OVERLAY:
                    writeFillOverlay((DmlFillOverlayEffect)effect, writer);
                    break;
                case DmlEffectType.GRAY_SCALE:
                    writeGrayScale(writer);
                    break;
                case DmlEffectType.HUE_SATURATION_LUMINANCE:
                    writeHueSaturationLuminance((DmlHueSaturationLuminanceEffect)effect, writer);
                    break;
                case DmlEffectType.LUMINANCE:
                    writeLuminance((DmlLuminanceEffect)effect, writer);
                    break;
                case DmlEffectType.TINT:
                    writeTint((DmlTintEffect)effect, writer);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected Dml effect type.");
            }
        }
    }

    private static void writeAlphaBiLevel(DmlAlphaBiLevelEffect effect, IDmlShapeWriterContext writer)
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:alphaBiLevel");
        builder.writeAttribute("thresh", DmlPercentageUtil.toPercentOrDmlPercent(effect.getThreshold(), isIsoStrict));
        builder.endElement("a:alphaBiLevel");
    }

    private static void writeAlphaCeiling(IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.writeEmptyElement("a:alphaCeiling");
    }

    private static void writeAlphaFloor(IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.writeEmptyElement("a:alphaFloor");
    }

    private static void writeAlphaInverse(DmlAlphaInverseEffect effect, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:alphaInv");
        DmlColorWriter.write(effect.getColor(), writer);
        builder.endElement("a:alphaInv");
    }

    private static void writeAlphaModulate(DmlAlphaModulateEffect effect, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:alphaMod");
        builder.startElement("a:cont");
        write(effect.getEffectsContainer(), writer);
        builder.endElement("a:cont");
        builder.endElement("a:alphaMod");
    }

    private static void writeAlphaModulateFixed(DmlAlphaModulateFixedEffect effect, IDmlShapeWriterContext writer)
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        double amount = effect.getAmount();

        if (isIsoStrict)
        {
            // According to spec the type of the alpha value is "ST_PositivePercentage", and
            // value have to be matched for mask "[0-9]+(\.[0-9]+)?%". Mimic MSW behavior
            // and take only integer part of the value.
            amount = msMath.round(amount, 2, MidpointRounding.AWAY_FROM_ZERO);
        }

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:alphaModFix");
        builder.writeAttribute("amt", DmlPercentageUtil.toPercentOrDmlPercent(amount, isIsoStrict));
        builder.endElement("a:alphaModFix");
    }

    private static void writeAlphaReplace(DmlAlphaReplaceEffect effect, IDmlShapeWriterContext writer)
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:alphaRepl");
        builder.writeAttribute("a", DmlPercentageUtil.toPercentOrDmlPercent(effect.getAlpha(), isIsoStrict));
        builder.endElement("a:alphaRepl");
    }

    private static void writeBiLevel(DmlBiLevelEffect effect, IDmlShapeWriterContext writer)
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:biLevel");
        builder.writeAttribute("thresh", DmlPercentageUtil.toPercentOrDmlPercent(effect.getThreshold(), isIsoStrict));
        builder.endElement("a:biLevel");
    }

    static void writeBlur(DmlBlurEffect effect, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:blur");
        builder.writeAttribute("rad", effect.getRadius());
        builder.writeAttribute("grow", effect.getGrow());
        builder.endElement("a:blur");
    }

    private static void writeColorChange(DmlColorChangeEffect effect, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:clrChange");

        if (!effect.getConsiderAlphaValues())
            builder.writeAttribute("useA", effect.getConsiderAlphaValues());

        builder.startElement("a:clrFrom");
        DmlColorWriter.write(effect.getSourceColor(), writer);
        builder.endElement("a:clrFrom");

        builder.startElement("a:clrTo");
        DmlColorWriter.write(effect.getDestinationColor(), writer);
        builder.endElement("a:clrTo");

        builder.endElement("a:clrChange");
    }

    private static void writeColorReplace(DmlColorReplaceEffect effect, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:clrRepl");
        DmlColorWriter.write(effect.getColor(), writer);
        builder.endElement("a:clrRepl");
    }

    private static void writeDuotone(DmlDuotoneEffect effect, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:duotone");
        DmlColorWriter.write(effect.getColor1(), writer);
        DmlColorWriter.write(effect.getColor2(), writer);
        builder.endElement("a:duotone");
    }

    static void writeFillOverlay(DmlFillOverlayEffect effect, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:fillOverlay");
        builder.writeAttribute("blend", DmlEnum.effectBlendModeToDml(effect.getBlend()));
        DmlFillWriter.write(effect.getFill(), writer, false);
        builder.endElement("a:fillOverlay");
    }

    private static void writeGrayScale(IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        builder.writeEmptyElement("a:grayscl");
    }

    private static void writeHueSaturationLuminance(DmlHueSaturationLuminanceEffect effect,
        IDmlShapeWriterContext writer)
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:hsl");
        builder.writeAttribute("hue", effect.getHue().getValue());
        builder.writeAttribute("lum", DmlPercentageUtil.toPercentOrDmlPercent(effect.getLuminance(), isIsoStrict));
        builder.writeAttribute("sat", DmlPercentageUtil.toPercentOrDmlPercent(effect.getSaturation(), isIsoStrict));
        builder.endElement("a:hsl");
    }

    private static void writeLuminance(DmlLuminanceEffect effect, IDmlShapeWriterContext writer)
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:lum");

        // Change contrast/bright ranges from (0, 1) to (-1, 1).
        double brightness = (effect.getBrightness() * 2.0) - 1.0;
        if (MathUtil.doubleToInt(DmlPercentageUtil.toDmlPercent(brightness)) != 0)
            builder.writeAttribute("bright", DmlPercentageUtil.toPercentOrDmlPercent(brightness, isIsoStrict));
        double contrast = (effect.getContrast() * 2.0) - 1.0;
        if (MathUtil.doubleToInt(DmlPercentageUtil.toDmlPercent(contrast)) != 0)
            builder.writeAttribute("contrast", DmlPercentageUtil.toPercentOrDmlPercent(contrast, isIsoStrict));

        builder.endElement("a:lum");
    }

    private static void writeTint(DmlTintEffect effect, IDmlShapeWriterContext writer)
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:tint");
        builder.writeAttribute("amt", DmlPercentageUtil.toPercentOrDmlPercent(effect.getAmount(), isIsoStrict));
        builder.writeAttribute("hue", DmlPercentageUtil.toPercentOrDmlPercent(effect.getHue(), isIsoStrict));
        builder.endElement("a:tint");
    }
}

