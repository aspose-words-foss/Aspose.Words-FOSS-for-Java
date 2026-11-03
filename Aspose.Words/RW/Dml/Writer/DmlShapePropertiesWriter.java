// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Instance class for writing DrawingML shape properties.
/// </summary>
/*static*/ class DmlShapePropertiesWriter
{
	/* Simulation of static class by using private constructor */
	private DmlShapePropertiesWriter()
	{}

    static void write(DmlShapeBase dmlShapeBase, IDmlShapeWriterContext writer) throws Exception
    {
        String prefix;
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        // Use the same writer for writing legacy grpSp and wgp.
        // Determine root tag name by group shape type.
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
                prefix = isIsoStrict ? "wp" : "wps";
                break;
            default:
                throw new IllegalArgumentException("Unexpected Dml node type.");
        }

        write(prefix, dmlShapeBase, writer);
    }

    static void write(String prefix, IDmlShapePrSource prSource, IDmlShapeWriterContext writer) throws Exception
    {
        write(prefix, prSource, writer, false);
    }

    /// <summary>
    /// Writes the shape properties with the option to specify whether placeholder colors that occur in chart style
    /// parts can be written to the current document part. Such a color has no particular color definition, and
    /// colors without a definition are not written to the document by default.
    /// </summary>
    static void write(String prefix, IDmlShapePrSource prSource, IDmlShapeWriterContext writer,
        boolean writePlaceholderColor) throws Exception
    {
        if (prSource == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        String rootTagName = msString.format("{0}:spPr", prefix);

        builder.startElement(rootTagName);

        builder.writeAttribute("bwMode", DmlEnum.bWModeToDml(prSource.getBWMode()));

        DmlXfrmWriter.write(prSource.getTransform(), writer);
        DmlGeomertyWriter.write(prSource.getGeometry(), writer);
        DmlFillWriter.write(((IDmlCommonShapePrSource)prSource).getFill(), writer, false, writePlaceholderColor);
        DmlOutlineWriter.write("a:ln", ((IDmlCommonShapePrSource)prSource).getOutline(), writer);
        DmlShapeEffectsWriter.write(prSource.getEffects(), writer, false);
        Dml3DPropertiesWriter.writeScene3D(prSource.getScene3DProperties(), writer, false);
        Dml3DPropertiesWriter.writeShape3D(prSource.getShape3DProperties(), writer, false);
        DmlExtensionListWriter.write(prSource.getSpPrExtensions(), writer);

        builder.endElement(rootTagName);
    }
}

