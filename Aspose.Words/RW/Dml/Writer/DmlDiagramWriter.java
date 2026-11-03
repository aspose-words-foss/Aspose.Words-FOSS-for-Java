// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/15/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.words.ref.Ref;


/*static*/ class DmlDiagramWriter
{
	/* Simulation of static class by using private constructor */
	private DmlDiagramWriter()
	{}

    static void write(DmlNode dml, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startElement("dgm:relIds");
        builder.writeAttribute("xmlns:dgm", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM, isIsoStrict));
        builder.writeAttribute("xmlns:r", 
            DocxNamespaces.getNamespace(DocxNamespace.RELATIONSHIPS, isIsoStrict));

        DmlDiagram diagram = (DmlDiagram)dml;

        String dmId = writeDiagramData(diagram.getData(), writer);
        String loId = writeDiagramLayout(diagram.getLayout(), writer);
        String qsId = writeDiagramStyle(diagram.getStyleDefinition(), writer);
        String csId = writeDiagramColors(diagram.getColorTransformDefinition(), writer);

        builder.writeAttribute("r:dm", dmId);
        builder.writeAttribute("r:lo", loId);
        builder.writeAttribute("r:qs", qsId);
        builder.writeAttribute("r:cs", csId);

        builder.endElement("dgm:relIds");
    }

    private static String writeDiagramData(DmlDiagramDataModel data, DocxDocumentWriterBase writer) throws Exception
    {
        String drawingId = writeDiagramDrawing(data.getDrawing(), writer);

        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder chartBuilder = createChildPart(
            writer,
            DocxRelationshipTypes.getType(DocxRelationshipType.DIAGRAM_DATA, isIsoStrict),
            DocxContentType.DIAGRAM_DATA,
            "/word/diagrams/data{0}.xml",
            /*out*/ referenceToRelId);
        relId = referenceToRelId.get();

        writer.pushBuilder(chartBuilder);
        DmlDiagramDataWriter.write(data, drawingId, writer);
        writer.popBuilder();

        return relId;
    }

    private static String writeDiagramDrawing(ShapeBase drawing, DocxDocumentWriterBase writer) throws Exception
    {
        if (drawing == null)
            return null;

        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder partBuilder = createChildPart(
            writer,
            DocxRelationshipTypes.getType(DocxRelationshipType.DIAGRAM_DRAWING, isIsoStrict),
            DocxContentType.DIAGRAM_DRAWING,
            "/word/diagrams/drawing{0}.xml",
            /*out*/ referenceToRelId);
        relId = referenceToRelId.get();

        writer.pushBuilder(partBuilder);
        DmlDiagramDrawingWriter.write(drawing, writer);
        writer.popBuilder();

        return relId;
    }

    private static String writeDiagramLayout(DmlDiagramLayout layout, DocxDocumentWriterBase writer) throws Exception
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder partBuilder = createChildPart(
            writer,
            DocxRelationshipTypes.getType(DocxRelationshipType.DIAGRAM_LAYOUT, isIsoStrict),
            DocxContentType.DIAGRAM_LAYOUT,
            "/word/diagrams/layout{0}.xml",
            /*out*/ referenceToRelId);
        relId = referenceToRelId.get();

        writer.pushBuilder(partBuilder);
        DmlDiagramLayoutWriter.write(layout, writer);
        writer.popBuilder();

        return relId;
    }

    private static String writeDiagramStyle(DmlDiagramStyleDefinition styleDefinition, DocxDocumentWriterBase writer) throws Exception
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder partBuilder = createChildPart(
            writer,
            DocxRelationshipTypes.getType(DocxRelationshipType.DIAGRAM_STYLE, isIsoStrict),
            DocxContentType.DIAGRAM_STYLE,
            "/word/diagrams/quickStyle{0}.xml",
            /*out*/ referenceToRelId);
        relId = referenceToRelId.get();

        writer.pushBuilder(partBuilder);
        DmlDiagramStyleWriter.write(styleDefinition, writer);
        writer.popBuilder();

        return relId;
    }

    private static String writeDiagramColors(DmlDiagramColorTransform colorTransformDefinition, DocxDocumentWriterBase writer) throws Exception
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder partBuilder = createChildPart(
            writer,
            DocxRelationshipTypes.getType(DocxRelationshipType.DIAGRAM_COLORS, isIsoStrict),
            DocxContentType.DIAGRAM_COLORS,
            "/word/diagrams/colors{0}.xml",
            /*out*/ referenceToRelId);
        relId = referenceToRelId.get();

        writer.pushBuilder(partBuilder);
        DmlDiagramColorWriter.write(colorTransformDefinition, writer);
        writer.popBuilder();

        return relId;
    }

    private static DocxBuilder createChildPart(
        DocxDocumentWriterBase writer,
        String relType,
        String contentType,
        String partNameTemplate,
        /*out*/Ref<String> relId) throws Exception
    {
        int partNumber = writer.getNextEmbeddedPartNumber(relType);
        String partName = msString.format(partNameTemplate, partNumber);

        return writer.createChildPartAndBuilder(writer.getCurrentBuilder().getPart(), partName, contentType, relType, /*out*/ relId);
    }
}

