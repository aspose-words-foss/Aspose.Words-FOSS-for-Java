// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/15/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.words.ref.Ref;


/*static*/ class DmlChartWriter
{
	/* Simulation of static class by using private constructor */
	private DmlChartWriter()
	{}

    static void write(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        boolean isChartEx = dml.getDmlNode().getDmlNodeType() == DmlNodeType.CHART_EX;
        String prefix = isChartEx ? "cx" : "c";

        builder.startElement(prefix + ":chart");
        builder.writeAttribute("xmlns:" + prefix, DocxNamespaces.getNamespace(
            isChartEx ? DocxNamespace.DRAWING_ML_CHART_EX : DocxNamespace.DRAWING_ML_CHART, isIsoStrict));
        builder.writeAttribute("xmlns:r", DocxNamespaces.getNamespace(DocxNamespace.RELATIONSHIPS, isIsoStrict));

        String chartRelType = DocxRelationshipTypes.getType(
            isChartEx ? DocxRelationshipType.CHART_EX : DocxRelationshipType.CHART, isIsoStrict);
        int chartPartNumber = writer.getNextEmbeddedPartNumber(chartRelType);
        String chartPartName = msString.format("/word/charts/{0}{1}.xml", 
            isChartEx ? "chartEx" : "chart", chartPartNumber);

        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder chartBuilder = writer.createChildPartAndBuilder(
            writer.getCurrentBuilder().getPart(), 
            chartPartName,
            isChartEx ? DocxContentType.CHART_EX : DocxContentType.CHART, 
            chartRelType, 
            /*out*/ referenceToRelId);
        relId = referenceToRelId.get();

        builder.writeAttribute("r:id", relId);

        writer.pushBuilder(chartBuilder);
        DmlChartSpaceWriter.write(dml, writer);
        writer.popBuilder();

        builder.endElement(prefix + ":chart");
    }
}

