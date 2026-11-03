// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import com.aspose.MathUtil;
import com.aspose.FormatterPal;


/*static*/ class DmlDiagramLayoutWriter
{
	/* Simulation of static class by using private constructor */
	private DmlDiagramLayoutWriter()
	{}

    static void write(DmlDiagramLayout layout, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startDocument("dgm:layoutDef");
        builder.writeAttribute("xmlns:dgm", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM, isIsoStrict));
        builder.writeAttribute("xmlns:a", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));

        builder.writeAttribute("uniqueId", layout.getUniqueId());
        builder.writeAttribute("minVer", layout.getMinVersion());
        builder.writeAttribute("defStyle", layout.getDefaultStyle());

        DmlDiagramComplexTypesWriter.writeStrings("dgm:title", layout.getTitles(), writer);
        DmlDiagramComplexTypesWriter.writeStrings("dgm:desc", layout.getDescriptions(), writer);
        DmlDiagramComplexTypesWriter.writeCategoryList(layout.getCategories(), writer);
        DmlDiagramDataWriter.writeSampleData("dgm:sampData", layout.getSampleData(), writer);
        DmlDiagramDataWriter.writeSampleData("dgm:styleData", layout.getStyleData(), writer);
        DmlDiagramDataWriter.writeSampleData("dgm:clrData", layout.getColorTransformSampleData(), writer);

        writeLayoutNode(layout.getLayoutNode(), writer);
        DmlExtensionListWriter.write("dgm", layout.getExtensions(), writer);

        builder.endDocument();
    }

    private static void writeLayoutNode(DmlDiagramLayoutNode layoutNode, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:layoutNode");
        builder.writeAttribute("name", layoutNode.getName());
        builder.writeAttribute("styleLbl", layoutNode.getStyleLabel());
        if (layoutNode.getChildOrder() != DmlChildOrder.BOTTOM)
            builder.writeAttribute("chOrder", DmlDiagramEnum.childOrderToDml(layoutNode.getChildOrder()));
        builder.writeAttribute("moveWith", layoutNode.getMoveWith());

        for (DmlDiagramLayoutNodeContentItem item : layoutNode.getContent())
            writeLayoutNodeContentItem(item, writer);

        DmlExtensionListWriter.write("dgm", layoutNode.getExtensions(), writer);

        builder.endElement("dgm:layoutNode");
    }

    private static void writeLayoutNodeContentItem(DmlDiagramLayoutNodeContentItem item, DocxDocumentWriterBase writer) throws Exception
    {
        switch (item.getContentItemType())
        {
            case DmlDiagramLayoutNodeContentItemType.ALGORITHM:
                writeAlgorithm((DmlAlgorithm)item, writer);
                break;
            case DmlDiagramLayoutNodeContentItemType.CHOOSE:
                writeChoose((DmlChoose)item, writer);
                break;
            case DmlDiagramLayoutNodeContentItemType.CONSTRAINT_LIST:
                writeConstraintList((DmlConstraintList)item, writer);
                break;
            case DmlDiagramLayoutNodeContentItemType.LAYOUT_NODE:
                writeLayoutNode((DmlDiagramLayoutNode)item, writer);
                break;
            case DmlDiagramLayoutNodeContentItemType.SHAPE:
                writeShape((DmlDiagramShape)item, writer);
                break;
            case DmlDiagramLayoutNodeContentItemType.FOR_EACH:
                writeForEach((DmlForEach)item, writer);
                break;
            case DmlDiagramLayoutNodeContentItemType.LAYOUT_VARIABLE_PROPERTY_SET:
                DmlDiagramComplexTypesWriter.writeLayoutVariables("dgm:varLst", (DmlLayoutVariablePropertySet)item, writer);
                break;
            case DmlDiagramLayoutNodeContentItemType.NUMERIC_RULE_LIST:
                writeNumericRuleList((DmlNumericRuleList)item, writer);
                break;
            case DmlDiagramLayoutNodeContentItemType.PRESENTATION_OF:
                writePresentationOf((DmlPresentationOf)item, writer);
                break;
            default:
                throw new IllegalArgumentException("Unexpected Diagram Layout Node Content Item Type");
        }
    }

    private static void writeAlgorithm(DmlAlgorithm alg, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:alg");
        builder.writeAttribute("type", DmlDiagramEnum.algorithmTypeToDml(alg.getType()));
        builder.writeAttributeIfNotZero("rev", alg.getRevision());

        for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>) alg.getParams())
            builder.writeElementWithAttributes("dgm:param", "type", entry.getKey(), "val", entry.getValue());

        DmlExtensionListWriter.write("dgm", alg.getExtensions(), writer);

        builder.endElement("dgm:alg");
    }

    private static void writeChoose(DmlChoose choose, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:choose");
        builder.writeAttribute("name", choose.getName());

        writeWhen(choose.getIf(), writer);
        writeOtherwise(choose.getElse(), writer);

        builder.endElement("dgm:choose");
    }

    private static void writeWhen(Iterable<DmlWhen> whens, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        for (DmlWhen when : whens)
        {
            builder.startElement("dgm:if");
            builder.writeAttribute("name", when.getName());
            writeIteratorAttributes(when.getIteratorAttributes(), builder);
            builder.writeAttribute("func", DmlDiagramEnum.functionTypeToDml(when.getFunction()));
            builder.writeAttribute("arg", DmlDiagramEnum.variableTypeToDml(when.getArgument()));
            builder.writeAttribute("op", DmlDiagramEnum.functionOperatorToDml(when.getOperator()));
            builder.writeAttribute("val", when.getValue());

            for (DmlDiagramLayoutNodeContentItem item : when.getContent())
                writeLayoutNodeContentItem(item, writer);

            DmlExtensionListWriter.write("dgm", when.getExtensions(), writer);

            builder.endElement("dgm:if");
        }
    }

    private static void writeOtherwise(DmlOtherwise otherwise, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        builder.startElement("dgm:else");
        builder.writeAttribute("name", otherwise.getName());

        for (DmlDiagramLayoutNodeContentItem item : otherwise.getContent())
            writeLayoutNodeContentItem(item, writer);

        DmlExtensionListWriter.write("dgm", otherwise.getExtensions(), writer);

        builder.endElement("dgm:else");
    }

    private static void writeConstraintList(DmlConstraintList constrLst, DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:constrLst");

        for (DmlConstraint value : constrLst.getValues())
        {
            builder.startElement("dgm:constr");
            writeConstraintAttributes(value.getConstraintAttributes(), builder);
            writeConstraintRefAttributess(value.getConstraintReferenceAttributes(), builder);
            builder.writeAttributeIfNotDefault("op", DmlDiagramEnum.booleanOperatorToDml(value.getOperator()), "none");
            if (!MathUtil.areEqual(value.getValue(), 0d))
                builder.writeAttribute("val", getDoubleValue(value.getValue()));
            if (!MathUtil.areEqual(value.getFactor(), 1.0d))
                builder.writeAttribute("fact", getDoubleValue(value.getFactor()));
            builder.endElement("dgm:constr");
        }

        builder.endElement("dgm:constrLst");
    }

    private static void writeShape(DmlDiagramShape shape, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:shape");
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        
        // It seems MS Word writes this value in degrease. So do the same.
        builder.writeAttributeIfNotZero("rot", shape.getRotation().getValueInDegrees());
        builder.writeAttributeIfNotDefault("type", shape.getType(), "none");
        builder.writeAttribute("xmlns:r", DocxNamespaces.getNamespace(DocxNamespace.RELATIONSHIPS, isIsoStrict));
        builder.writeAttributeString("r:blip", shape.getBlipReference());
        builder.writeAttributeIfNotZero("zOrderOff", shape.getZOrder());
        builder.writeAttributeIfTrue("hideGeom", shape.getHideGeometry());
        builder.writeAttributeIfTrue("lkTxEntry", shape.getPreventTextEditing());
        builder.writeAttributeIfTrue("blipPhldr", shape.getImagePlaceholder());

        if (shape.getAdjustList() != null)
        {
            builder.startElement("dgm:adjLst");

            for (DmlShapeAdjust adjust : shape.getAdjustList())
                builder.writeElementWithAttributes("dgm:adj", "idx", adjust.getHandleIndex(), "val", adjust.getValue());

            builder.endElement("dgm:adjLst");
        }

        DmlExtensionListWriter.write("dgm", shape.getExtensions(), writer);

        builder.endElement("dgm:shape");
    }

    private static void writeForEach(DmlForEach forEach, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:forEach");
        builder.writeAttribute("name", forEach.getName());
        builder.writeAttribute("ref", forEach.getReference());
        writeIteratorAttributes(forEach.getIteratorAttributes(), builder);

        for (DmlDiagramLayoutNodeContentItem item : forEach.getContent())
            writeLayoutNodeContentItem(item, writer);

        DmlExtensionListWriter.write("dgm", forEach.getExtensions(), writer);

        builder.endElement("dgm:forEach");
    }

    private static void writeNumericRuleList(DmlNumericRuleList ruleLst, DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:ruleLst");

        for (DmlNumericRule rule : ruleLst.getValues())
        {
            builder.startElement("dgm:rule");
            writeConstraintAttributes(rule.getConstraintAttributes(), builder);
            builder.writeAttribute("val", getDoubleValue(rule.getValue()));
            builder.writeAttribute("fact", getDoubleValue(rule.getFactor()));
            builder.writeAttribute("max", getDoubleValue(rule.getMax()));
            builder.endElement("dgm:rule");
        }

        builder.endElement("dgm:ruleLst");
    }

    private static String getDoubleValue(double val)
    {
        if (((val) == Double.POSITIVE_INFINITY))
            return "INF";
        if (((val) == Double.NEGATIVE_INFINITY))
            return "-INF";
        if (Double.isNaN(val))
            return "NaN";

        return FormatterPal.doubleToStr9Decimals(val);
    }

    private static void writePresentationOf(DmlPresentationOf presOf, DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        builder.startElement("dgm:presOf");
        writeIteratorAttributes(presOf.getIteratorAttributes(), builder);
        builder.endElement("dgm:presOf");
    }

    private static void writeIteratorAttributes(DmlIteratorAttributes attrs, DocxBuilder builder)
    {
        builder.writeAttributeIfNotDefault("axis", DmlDiagramEnum.axisTypeListToDml(attrs.getAxisType()), "none");
        builder.writeAttributeIfNotDefault("ptType", DmlDiagramEnum.elementTypeListToDml(attrs.getPointType()), "all");
        builder.writeAttributeIfNotDefault("hideLastTrans", DmlDiagramEnum.boolListToDml(attrs.getHideLastTransition()), "true");
        builder.writeAttributeIfNotDefault("st", DmlDiagramEnum.intListToDml(attrs.getStart()), "1");
        builder.writeAttributeIfNotDefault("cnt", DmlDiagramEnum.intListToDml(attrs.getCount()), "0");
        builder.writeAttributeIfNotDefault("step", DmlDiagramEnum.intListToDml(attrs.getStep()), "1");
    }

    private static void writeConstraintAttributes(DmlConstraintAttributes attrs, DocxBuilder builder)
    {
        builder.writeAttribute("type", DmlDiagramEnum.constraintTypeToDml(attrs.getType()));
        builder.writeAttributeIfNotDefault("for", DmlDiagramEnum.constraintRelationshipToDml(attrs.getFor()), "self");
        builder.writeAttribute("forName", attrs.getForName());
        builder.writeAttributeIfNotDefault("ptType", DmlDiagramEnum.elementTypeToDml(attrs.getPointType()), "all");
    }

    private static void writeConstraintRefAttributess(DmlConstraintAttributes attrs, DocxBuilder builder)
    {
        builder.writeAttributeIfNotDefault("refType", DmlDiagramEnum.constraintTypeToDml(attrs.getType()), "none");
        builder.writeAttributeIfNotDefault("refFor", DmlDiagramEnum.constraintRelationshipToDml(attrs.getFor()), "self");
        builder.writeAttribute("refForName", attrs.getForName());
        builder.writeAttributeIfNotDefault("refPtType", DmlDiagramEnum.elementTypeToDml(attrs.getPointType()), "all");
    }
}

