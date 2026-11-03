// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/*static*/ class DmlDiagramStyleWriter
{
	/* Simulation of static class by using private constructor */
	private DmlDiagramStyleWriter()
	{}

    static void write(DmlDiagramStyleDefinition styleDef, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startDocument("dgm:styleDef");
        builder.writeAttribute("xmlns:dgm", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM, isIsoStrict));
        builder.writeAttribute("xmlns:a", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));

        builder.writeAttribute("uniqueId", styleDef.getUniqueId());
        builder.writeAttribute("minVer", styleDef.getMinVersion());

        DmlDiagramComplexTypesWriter.writeStrings("dgm:title", styleDef.getTitles(), writer);
        DmlDiagramComplexTypesWriter.writeStrings("dgm:desc", styleDef.getDescriptions(), writer);
        DmlDiagramComplexTypesWriter.writeCategoryList(styleDef.getCategories(), writer);
        Dml3DPropertiesWriter.writeScene3D("dgm", styleDef.getScene3D(), writer, false);
        writeStyleLabels(styleDef.getStyleLabels(), writer);

        DmlExtensionListWriter.write("dgm", styleDef.getExtensions(), writer);

        builder.endDocument();
    }

    private static void writeStyleLabels(DmlStyleLabel[] dmlStyleLabel, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (DmlStyleLabel style : dmlStyleLabel)
        {
            builder.startElement("dgm:styleLbl");
            builder.writeAttributeString("name", style.getName());

            Dml3DPropertiesWriter.writeScene3D("dgm", style.getScene3D(), writer, false);
            Dml3DPropertiesWriter.writeShape3D("dgm", style.getShape3D(), writer, false);
            writeTxPr(style.getTextProperties(), writer);
            DmlShapeStyleWriter.write("dgm", style.getStyle(), writer);

            DmlExtensionListWriter.write("dgm", style.getExtensions(), writer);

            builder.endElement("dgm:styleLbl");
        }
    }

    private static void writeTxPr(DmlDiagramTextPr txPr, DocxDocumentWriterBase writer) throws Exception
    {
        if (txPr == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:txPr");

        if (txPr.getFlatText() != null)
            builder.writeElementWithAttributes("dgm:flatTx", "z", txPr.getFlatText().getZCoordinate());

        Dml3DPropertiesWriter.writeShape3D("a", txPr.getShape3DProperties(), writer, false);

        builder.endElement("dgm:txPr");
    }
}

