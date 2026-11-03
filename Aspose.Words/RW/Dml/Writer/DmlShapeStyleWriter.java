// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Represents class writing DrawingML shape style.
/// </summary>
/*static*/ class DmlShapeStyleWriter
{
	/* Simulation of static class by using private constructor */
	private DmlShapeStyleWriter()
	{}

    static void write(DmlShapeBase dmlShapeBase, DocxDocumentWriterBase writer)
    {
        String prefix;
        switch (dmlShapeBase.getDmlNodeType())
        {
            case DmlNodeType.PICTURE:
                prefix = "pic";
                break;
            case DmlNodeType.SHAPE:
            case DmlNodeType.CONNECTOR_SHAPE:
                prefix = "a";
                break;
            case DmlNodeType.WORDPROCESSING_SHAPE:
                prefix = "wps";
                break;
            default:
                throw new IllegalArgumentException("Unexpected Dml node type.");
        }

        write(prefix, dmlShapeBase.getStyle(), writer);
    }

    static void write(String prefix, DmlShapeStyle shapeStyle, DocxDocumentWriterBase writer)
    {
        if (shapeStyle == null || shapeStyle.isEmpty())
            return;

        NrxXmlBuilder builder = writer.getCurrentBuilder();

        String rootTagName = msString.format("{0}:style", prefix);

        builder.startElement(rootTagName);

        writeDmlLineReference(shapeStyle.getLineReference(), writer);
        writeDmlFillReference(shapeStyle.getFillReference(), writer);
        writeDmlEffectRef(shapeStyle.getEffectReference(), writer);
        writeDmlFontReference(shapeStyle.getFontReference(), writer);

        builder.endElement(rootTagName);
    }

    private static void writeDmlFontReference(DmlFontReference fontRef, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();

        builder.startElement("a:fontRef");
        builder.writeAttribute("idx", DmlEnum.fontCollectionIndexToDml(fontRef.getFontCollectionIndex()), 0);
        DmlColorWriter.write(fontRef.getColor(), writer);
        builder.endElement("a:fontRef");
    }

    private static void writeDmlFillReference(DmlFillReference fillRef, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();

        builder.startElement("a:fillRef");
        builder.writeAttribute("idx", fillRef.getStyleMatrixIndex(), 0);
        DmlColorWriter.write(fillRef.getColor(), writer);
        builder.endElement("a:fillRef");
    }

    private static void writeDmlLineReference(DmlLineReference lineRef, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();

        builder.startElement("a:lnRef");
        builder.writeAttribute("idx", lineRef.getStyleMatrixIndex(), 0);
        DmlColorWriter.write(lineRef.getColor(), writer);
        builder.endElement("a:lnRef");
    }

    private static void writeDmlEffectRef(DmlEffectReference effectRef, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();

        builder.startElement("a:effectRef");
        builder.writeAttribute("idx", effectRef.getStyleMatrixIndex(), 0);
        DmlColorWriter.write(effectRef.getColor(), writer);
        builder.endElement("a:effectRef");
    }
}

