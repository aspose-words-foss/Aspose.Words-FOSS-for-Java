// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.msString;


/*static*/ class DmlOutlineWriter
{
	/* Simulation of static class by using private constructor */
	private DmlOutlineWriter()
	{}

    static void write(String tagName, DmlOutline outline, IDmlShapeWriterContext writer) throws Exception
    {
        if ((outline != null) && (outline.getDirectPropertiesCount() > 0))
            writeOutlineCore(tagName, outline, writer);

        // If outline DirectPropertiesCount equals zero, this means outline is taken from style and nothing should be written in XML.
    }

    private static void writeOutlineCore(String tagName, DmlOutline outline, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isTextEffect = ("w14:textOutline".equals(tagName));
   
        builder.startElement(tagName);

        if ("a14:hiddenLine".equals(tagName))                     
            builder.writeAttribute("xmlns:a14", DmlExtensionsNamespace.DRAWING_MAIN);
        
        Object widthObj = outline.getDirectProperty(DmlOutlinePropertiesIds.WIDTH_IN_EMUS);
        if (widthObj != null)
            builder.writeAttribute(isTextEffect ? "w14:w" : "w", (/*double*/Double)widthObj);         

        Object lineEndingCapTypeObj = outline.getDirectProperty(DmlOutlinePropertiesIds.LINE_ENDING_CAP_TYPE);
        if (lineEndingCapTypeObj != null)
            builder.writeAttributeIfNotDefault(isTextEffect ? "w14:cap" : "cap",
                DmlEnum.lineEndingCapTypeToDml((/*EndCap*/Integer)lineEndingCapTypeObj), "flat");

        Object compoundLineTypeObj = outline.getDirectProperty(DmlOutlinePropertiesIds.COMPOUND_LINE_TYPE);
        if (compoundLineTypeObj != null)
            builder.writeAttributeIfNotDefault(isTextEffect ? "w14:cmpd" : "cmpd",
                DmlEnum.compoundLineTypeToDml((/*ShapeLineStyle*/Integer)compoundLineTypeObj), "sng");

        Object strokeAlignmentObj = outline.getDirectProperty(DmlOutlinePropertiesIds.STROKE_ALIGNMENT);
        if (strokeAlignmentObj != null)
            builder.writeAttributeIfNotDefault(isTextEffect ? "w14:algn" : "algn",
                ((/*boolean*/Boolean)strokeAlignmentObj ? "in" : "ctr"), "ctr");

        Object fillObj = outline.getDirectProperty(DmlOutlinePropertiesIds.FILL);
        if (fillObj != null)
            DmlFillWriter.write((DmlFill)fillObj, writer, isTextEffect);

        writeDmlDash(outline, builder, isTextEffect, writer.getCompliance());
        writeDmlLineJoinStyle(outline, builder, isTextEffect, writer.getCompliance());

        Object headLineEndStyleObj = outline.getDirectProperty(DmlOutlinePropertiesIds.HEAD_LINE_END_STYLE);
        if (headLineEndStyleObj != null)
            writeDmlLineEnd((DmlLineEndStyle)headLineEndStyleObj, builder, true, isTextEffect);

        Object tailLineEndStyle = outline.getDirectProperty(DmlOutlinePropertiesIds.TAIL_LINE_END_STYLE);
        if (tailLineEndStyle != null)
            writeDmlLineEnd((DmlLineEndStyle)tailLineEndStyle, builder, false, isTextEffect);

        // Write extLst(Extension List) §5.1.2.1.15
        Object extLst = outline.getDirectProperty(DmlOutlinePropertiesIds.EXTENSIONS);
        if (extLst != null)
           DmlExtensionListWriter.write((StringToObjDictionary<DmlExtension>)extLst, writer);

        builder.endElement(tagName);
    }

    private static void writeDmlLineEnd(DmlLineEndStyle lineEndStyle, NrxXmlBuilder builder, boolean isHead, boolean isTextEffect)
    {
        if (lineEndStyle == null)
            return;

        String prefix = isTextEffect ? "w14" : "a";
        String tagName = msString.format((isHead ? "{0}:headEnd" : "{0}:tailEnd"), prefix);
        builder.startElement(tagName);
        builder.writeAttributeIfNotDefault(isTextEffect ? "w14:type" : "type", DmlEnum.arrowTypeToDml(lineEndStyle.getType()), "none");
        builder.writeAttributeIfNotDefault(isTextEffect ? "w14:w" : "w", DmlEnum.arrowWidthToDml(lineEndStyle.getWidth()), "med");
        builder.writeAttributeIfNotDefault(isTextEffect ? "w14:len" : "len", DmlEnum.arrowLengthToDml(lineEndStyle.getLength()), "med");
        builder.endElement(tagName);
    }

    private static void writeDmlDash(DmlOutline outline, NrxXmlBuilder builder, boolean isTextEffect, 
        /*OoxmlComplianceCore*/int compliance)
    {
        Object dmlDashObj = outline.getDirectProperty(DmlOutlinePropertiesIds.DASH);
        if (dmlDashObj != null)
        {
            DmlDash dmlDash = (DmlDash)dmlDashObj;
            switch (dmlDash.getDashType())
            {
                case DmlDashType.PRESET_DASH:
                    writeDmlPresetDash((DmlPresetDash)dmlDash, builder, isTextEffect);
                    break;
                case DmlDashType.CUSTOM_DASH:
                    writeDmlCustomDash((DmlCustomDash)dmlDash, builder, isTextEffect, compliance);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected Dml dash type.");
            }
        }
    }

    private static void writeDmlPresetDash(DmlPresetDash dmlPresetDash, NrxXmlBuilder builder, boolean isTextEffect)
    {
        String tagName = isTextEffect ? "w14:prstDash" : "a:prstDash";
        String attrName = isTextEffect ? "w14:val" : "val";
        builder.writeElementWithAttributes(tagName, attrName, DmlEnum.presetDashTypeToDml(dmlPresetDash.getPreset()));
    }

    private static void writeDmlCustomDash(DmlCustomDash dmlCustomDash, NrxXmlBuilder builder, boolean isTextEffect,
        /*OoxmlComplianceCore*/int compliance)
    {
        boolean isIsoStrict = compliance == OoxmlComplianceCore.ISO_STRICT;
        String rootTagName = isTextEffect ? "w14:custDash" : "a:custDash";
        builder.startElement(rootTagName);

        for (DmlDashStop dashStop : dmlCustomDash.getDashStops())
        {
            builder.writeElementWithAttributes((isTextEffect ? "w14:ds" : "a:ds"),
                (isTextEffect ? "w14:d" : "d"), 
                DmlPercentageUtil.toPercentOrDmlPercent(dashStop.getDashLength(), isIsoStrict),
                (isTextEffect ? "w14:sp" : "sp"), 
                DmlPercentageUtil.toPercentOrDmlPercent(dashStop.getSpaceLength(), isIsoStrict));
        }

        builder.endElement(rootTagName);
    }

    private static void writeDmlLineJoinStyle(DmlOutline outline, NrxXmlBuilder builder, boolean isTextEffect, 
        /*OoxmlComplianceCore*/int compliance)
    {
        boolean isIsoStrict = compliance == OoxmlComplianceCore.ISO_STRICT;
        Object dmlLineJoinStyleObj = outline.getDirectProperty(DmlOutlinePropertiesIds.LINE_JOIN_STYLE);
        if (dmlLineJoinStyleObj != null)
        {
            switch ((/*JoinStyle*/Integer)dmlLineJoinStyleObj)
            {
                case JoinStyle.BEVEL:
                    builder.writeEmptyElement(isTextEffect ? "w14:bevel" : "a:bevel");
                    break;
                case JoinStyle.ROUND:
                    builder.writeEmptyElement(isTextEffect ? "w14:round" : "a:round");
                    break;
                case JoinStyle.MITER:
                    {
                        // WORDSNET-13705 3. MS Word 2013 raises document corruption error if the lim attribute of 
                        // the w14:miter element has percent sign.
                        if (isTextEffect)
                        {
                            builder.writeElementWithAttributes("w14:miter", "w14:lim",
                                DmlPercentageUtil.toDmlPercent(outline.getLineMiterLimit()));
                        }
                        else
                        {
                            builder.writeElementWithAttributes("a:miter", "lim",
                                DmlPercentageUtil.toPercentOrDmlPercent(outline.getLineMiterLimit(), isIsoStrict));
                        }
                        break;
                    }
                default:
                    // Unexpected JoinStyle - do nothing.
                    break;
            }
        }
    }
}

