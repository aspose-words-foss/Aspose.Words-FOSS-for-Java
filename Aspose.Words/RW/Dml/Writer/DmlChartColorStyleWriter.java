// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This class allows writing a color style part of a chart.
/// </summary>
/*static*/ class DmlChartColorStyleWriter
{
	/* Simulation of static class by using private constructor */
	private DmlChartColorStyleWriter()
	{}

    /// <summary>
    /// Writes a <see cref="DmlChartColorStyle"/> object into the element 2.8.1.2 colorStyle [MS-ODRAWXML] that is
    /// a root element of the word/charts/colorsX.xml part.
    /// </summary>
    static void write(DmlChartColorStyle colorStyle, DocxDocumentWriterBase writer) throws Exception
    {
        if (colorStyle == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("cs:colorStyle");
        builder.writeAttribute("xmlns:cs", DmlExtensionsNamespace.CHART_STYLE);
        builder.writeAttribute("xmlns:a", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN,
            writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT));

        builder.writeAttribute("meth", colorStyle.getMethod());
        builder.writeAttribute("id", colorStyle.getId());

        if (colorStyle.getColors() != null)
        {
            for (DmlColor color : colorStyle.getColors()) 
                DmlColorWriter.write(color, writer);
        }

        if (colorStyle.getVariations() != null)
        {
            for (DmlChartColorStyleVariation variation : colorStyle.getVariations())
                writeVariation(variation, writer);
        }

        DmlExtensionListWriter.write("cs", colorStyle.getExtensions(), writer);

        builder.endElement("cs:colorStyle");
        builder.flush();
    }

    /// <summary>
    /// Writes the variation element of the 2.8.3.3 CT_ColorStyleVariation type.
    /// </summary>
    private static void writeVariation(DmlChartColorStyleVariation variation, DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("cs:variation");
        DmlColorModifiersWriter.write("a", variation.getColorModifiers(), writer);
        builder.endElement("cs:variation");
    }
}

