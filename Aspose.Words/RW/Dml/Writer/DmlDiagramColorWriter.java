// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/*static*/ class DmlDiagramColorWriter
{
	/* Simulation of static class by using private constructor */
	private DmlDiagramColorWriter()
	{}

    static void write(DmlDiagramColorTransform colorDef, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startDocument("dgm:colorsDef");
        builder.writeAttribute("xmlns:dgm", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM, isIsoStrict));
        builder.writeAttribute("xmlns:a", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));

        builder.writeAttributeIfNotDefault("uniqueId", colorDef.getUniqueId(), "");
        builder.writeAttributeIfNotDefault("minVer", colorDef.getMinVersion(), "");

        DmlDiagramComplexTypesWriter.writeStrings("dgm:title", colorDef.getTitles(), writer);
        DmlDiagramComplexTypesWriter.writeStrings("dgm:desc", colorDef.getDescriptions(), writer);
        DmlDiagramComplexTypesWriter.writeCategoryList(colorDef.getCategories(), writer);
        writeStyleLabels(colorDef.getStyleLabels(), writer);

        DmlExtensionListWriter.write("dgm", colorDef.getExtensions(), writer);

        builder.endDocument();
    }

    private static void writeStyleLabels(DmlColorTransformationStyleLabel[] styleLabels, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (DmlColorTransformationStyleLabel styleLabel : styleLabels)
        {
            builder.startElement("dgm:styleLbl");
            builder.writeAttribute("name", styleLabel.getName());

            writeDiagramColors("dgm:fillClrLst", styleLabel.getFillColorList(), writer);
            writeDiagramColors("dgm:linClrLst", styleLabel.getLineColorList(), writer);
            writeDiagramColors("dgm:effectClrLst", styleLabel.getEffectColorList(), writer);
            writeDiagramColors("dgm:txLinClrLst", styleLabel.getTextLineColorList(), writer);
            writeDiagramColors("dgm:txFillClrLst", styleLabel.getTextFillColorList(), writer);
            writeDiagramColors("dgm:txEffectClrLst", styleLabel.getTextEffectColorList(), writer);
            DmlExtensionListWriter.write("dgm", styleLabel.getExtensions(), writer);

            builder.endElement("dgm:styleLbl");
        }
    }

    private static void writeDiagramColors(String tagName, DmlDiagramColors fillColorList, DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(tagName);

        builder.writeAttributeIfNotDefault("meth", DmlDiagramEnum.colorApplicationMethodToDml(fillColorList.getColorApplicationMethod()), "span");
        builder.writeAttributeIfNotDefault("hueDir", DmlDiagramEnum.hueDirectionToDml(fillColorList.getHueDirection()), "cw");

        for (DmlColor color : fillColorList.getColors())
            DmlColorWriter.write(color, writer);
        
        builder.endElement(tagName);
    }
}

