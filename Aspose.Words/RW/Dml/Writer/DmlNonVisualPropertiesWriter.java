// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/*static*/ class DmlNonVisualPropertiesWriter
{
	/* Simulation of static class by using private constructor */
	private DmlNonVisualPropertiesWriter()
	{}

    static void writeNvPr(String prefix, DmlNode node, DocxDocumentWriterBase writer) throws Exception
    {
        if (node.getNonVisualPr() == null)
            return;

        if ("wpg".equals(prefix) || "wpc".equals(prefix) || "wps".equals(prefix) || "wp".equals(prefix))
        {
            // This element MUST NOT be present when the Wordprocessing Shape, 
            // Canvas or Group is contained directly by a graphicData.
            // Check this by checking parent.
            if (node.getParent() != null && (node.getParent().getNodeType() == NodeType.GROUP_SHAPE))
                writeNonVisualDrawingProperties(prefix, node.getNonVisualPr().getNvDrawingProperties(), writer);

            writeConnectorProperties(prefix, writer, node);
            return;
        }

        String tagName = msString.format("{0}:{1}", prefix, getNvPrTagName(node.getNonVisualPr().getHolder()));

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(tagName);

        // This is strange behavior of MS Word. According to the spec 'cNvPr' is required,
        // but MS Word treat document with 'cNvPr' inside 'nvContentPartPr' as invalid and does not open it.
        // So simply do not write it for now.
        if (node.getNonVisualPr().getHolder() != DmlNvHolder.CONTENT_PART)
            writeNonVisualDrawingProperties(prefix, node.getNonVisualPr().getNvDrawingProperties(), writer);
        writeConnectorProperties(prefix, writer, node);
        builder.endElement(tagName);
    }

    private static void writeNonVisualDrawingProperties(String prefix, DmlNvDrawingProperties nvPr, DocxDocumentWriterBase writer) throws Exception
    {
        if (nvPr == null)
            return;
        
        DocxBuilder builder = writer.getCurrentBuilder();

        String tagName = msString.format("{0}:cNvPr", prefix);
        builder.startElement(tagName);

        // id and name are required attributes.
        builder.writeAttributeString("id", Integer.toString(nvPr.getId()));
        builder.writeAttributeString("name", nvPr.getName());
        builder.writeAttribute("descr", nvPr.getDescription());
        builder.writeAttributeIfTrue("hidden", nvPr.getHidden());
        DmlHlink hlinkClick = nvPr.getHlinkClick();

        if (hlinkClick != null)
            DmlHlinkWriter.writeHlink("a:hlinkClick", hlinkClick.getId(), hlinkClick.getTargetFrame(), hlinkClick.getTooltip(), hlinkClick.getExtensions(), writer);

        DmlExtensionListWriter.write(nvPr.getExtensions(), writer);
        builder.endElement(tagName);
    }

    private static void writeConnectorProperties(String prefix, DocxDocumentWriterBase writer, DmlNode node) throws Exception
    {
        DmlCnvPrBase cNvPr = node.getNonVisualPr().getCNvProperties();

        switch (cNvPr.getHolder())
        {
            case DmlNvHolder.CONNECTION_SHAPE:
                writeCnvPrConnectionShape(prefix, (DmlCnvPrConnectorShape)cNvPr, writer);
                break;
            case DmlNvHolder.GRAPHIC_FRAME:
            {
                // Non visual properties "cNvFrPr" and "cNvGraphicFramePr" have the same schema definition
                // but using different tag names.
                String tag = "cNvGraphicFramePr";
                if (node.getDmlNodeType() == DmlNodeType.GRAPHIC_FRAME)
                    tag = ((DmlGroupShape)node).isParentLockedCanvas() ? "cNvGraphicFramePr" : "cNvFrPr";

                writeCnvPrGraphicFrame(prefix, tag, (DmlCnvPrGraphicFrame)cNvPr, writer);
                break;
            }
            case DmlNvHolder.GROUP_SHAPE:
                writeCnvPrGroupShape(prefix, (DmlCnvPrGroupShape)cNvPr, writer);
                break;
            case DmlNvHolder.PICTURE:
                writeCnvPrPicture(prefix, (DmlCnvPrPicture)cNvPr, writer);
                break;
            case DmlNvHolder.SHAPE:
                writeCnvPrShape(prefix, (DmlCnvPrShape)cNvPr, writer);
                break;
            case DmlNvHolder.CONTENT_PART:
                writeCnvPrContentPart(prefix, (DmlCnvPrContentPart)cNvPr, writer);
                break;
            default:
                throw new IllegalArgumentException("Unexpected non-visual properties holder.");
        }
    }

    private static void writeCnvPrContentPart(String prefix, DmlCnvPrContentPart cNvPr, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        String tagName = msString.format("{0}:cNvContentPartPr", prefix);

        builder.startElement(tagName);
        writeLocks(cNvPr, writer);
        if (!cNvPr.isComment())
            builder.writeAttribute("isComment", cNvPr.isComment());

        builder.endElement(tagName);
    }

    private static void writeCnvPrConnectionShape(String prefix, DmlCnvPrConnectorShape cNvPr, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        // WORDSNET-26689 When writing the 21.3.2.15 'nvCxnSpPr' (Connector Non Visual Properties) element of the
        // http://purl.oclc.org/ooxml/drawingml/chartDrawing namespace (prefix 'cdr'), the element name is 'cNvCxnSpPr'.
        String connectorTagName = (isIsoStrict && (!"cdr".equals(prefix))) ? "cNvCnPr" : "cNvCxnSpPr";

        String tagName = ("wps".equals(prefix)) ? "wps:cNvCnPr" : msString.format("{0}:{1}", prefix, connectorTagName);

        builder.startElement(tagName);
        writeLocks(cNvPr, writer);

        DmlExtensionListWriter.write(cNvPr.getExtensions(), writer);

        // WORDSNET-17029 Write namespaces to preserve links between shapes.
        if (cNvPr.getConnectionStart() != null)
            writeShapeConnectionsAttributes(builder, "a:stCxn", cNvPr.getConnectionStart());
        if (cNvPr.getConnectionEnd() != null)
            writeShapeConnectionsAttributes(builder, "a:endCxn", cNvPr.getConnectionEnd());
       
        builder.endElement(tagName);
    }

    private static void writeShapeConnectionsAttributes(DocxBuilder builder, String elementName, DmlConnection connection)
    {
        // WriteElementWithAttributes can not be used due to limitations related with "uint" while writing the attribute values.
        builder.startElement(elementName);

        builder.writeAttributeUInt("id", connection.getId());
        builder.writeAttributeUInt("idx", connection.getIndex());

        builder.endElement(elementName);
    }

    private static void writeCnvPrGraphicFrame(String prefix, String tag, DmlCnvPrGraphicFrame cNvPr, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        
        String tagName = msString.format("{0}:{1}", prefix, tag);

        builder.startElement(tagName);
        writeLocks(cNvPr, writer);
        DmlExtensionListWriter.write(cNvPr.getExtensions(), writer);
        builder.endElement(tagName);
    }

    private static void writeCnvPrGroupShape(String prefix, DmlCnvPrGroupShape cNvPr, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        String tagName = msString.format("{0}:cNvGrpSpPr", prefix);

        builder.startElement(tagName);
        writeLocks(cNvPr, writer);
        DmlExtensionListWriter.write(cNvPr.getExtensions(), writer);
        builder.endElement(tagName);
    }

    private static void writeCnvPrPicture(String prefix, DmlCnvPrPicture cNvPr, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        String tagName = msString.format("{0}:cNvPicPr", prefix);

        builder.startElement(tagName);
        writeLocks(cNvPr, writer);
        DmlExtensionListWriter.write(cNvPr.getExtensions(), writer);
        builder.endElement(tagName);
    }

    private static void writeCnvPrShape(String prefix, DmlCnvPrShape cNvPr, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        String tagName = msString.format("{0}:cNvSpPr", prefix);

        builder.startElement(tagName);
        builder.writeAttributeIfTrue("txBox", cNvPr.getTextBox());
        writeLocks(cNvPr, writer);
        DmlExtensionListWriter.write(cNvPr.getExtensions(), writer);
        builder.endElement(tagName);
    }

    private static void writeLocks(DmlCnvPrBase cNvPr, DocxDocumentWriterBase writer) throws Exception
    {
        if (cNvPr.getLocks().getCount() == 0)
            return;

        String prefix = "a";

        DocxBuilder builder = writer.getCurrentBuilder();

        String tagName = msString.format("{0}:{1}", prefix, getLocksTagName(cNvPr.getHolder()));

        builder.startElement(tagName);

        writeLock(DmlLock.NO_GROUP, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_SELECT, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_ROTATION, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_CHANGE_ASPECT, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_MOVE, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_RESIZE, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_EDIT_POINTS, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_ADJUST_HANDLES, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_CHANGE_ARROWHEADS, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_CHANGE_SHAPE_TYPE, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_DRILLDOWN, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_UNGROUP, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_CROP, cNvPr.getLocks(), builder);
        writeLock(DmlLock.NO_TEXT_EDIT, cNvPr.getLocks(), builder);

        DmlExtensionListWriter.write(prefix, cNvPr.getLocks().getExtensions(), writer);

        builder.endElement(tagName);
    }

    private static void writeLock(/*DmlLock*/int dmlLock, DmlLocks locks, DocxBuilder builder)
    {
        Object val = locks.getLock(dmlLock);
        if(val == null)
            return;

        builder.writeAttribute(dmlLockToAttrName(dmlLock), val);
    }

    private static String getNvPrTagName(/*DmlNvHolder*/int holder)
    {
        switch (holder)
        {
            case DmlNvHolder.CONNECTION_SHAPE:
                return "nvCxnSpPr";
            case DmlNvHolder.GRAPHIC_FRAME:
                return "nvGraphicFramePr";
            case DmlNvHolder.GROUP_SHAPE:
                return "nvGrpSpPr";
            case DmlNvHolder.PICTURE:
                return "nvPicPr";
            case DmlNvHolder.SHAPE:
                return "nvSpPr";
            case DmlNvHolder.CONTENT_PART:
                return "nvContentPartPr";
            default:
                throw new IllegalArgumentException("Unexpected non-visual properties holder.");
        }
    }

    private static String getLocksTagName(/*DmlNvHolder*/int holder)
    {
        switch (holder)
        {
            case DmlNvHolder.CONNECTION_SHAPE:
                return "cxnSpLocks";
            case DmlNvHolder.GRAPHIC_FRAME:
                return "graphicFrameLocks";
            case DmlNvHolder.GROUP_SHAPE:
                return "grpSpLocks";
            case DmlNvHolder.PICTURE:
                return "picLocks";
            case DmlNvHolder.SHAPE:
                return "spLocks";
            case DmlNvHolder.CONTENT_PART:
                return "cpLocks";
            default:
                throw new IllegalArgumentException("Unexpected non-visual properties holder.");
        }
    }

    private static String dmlLockToAttrName(/*DmlLock*/int dmlLock)
    {
        switch (dmlLock)
        {
            case DmlLock.NO_GROUP:
                return "noGrp";
            case DmlLock.NO_SELECT:
                return "noSelect";
            case DmlLock.NO_ROTATION:
                return "noRot";
            case DmlLock.NO_CHANGE_ASPECT:
                return "noChangeAspect";
            case DmlLock.NO_MOVE:
                return "noMove";
            case DmlLock.NO_RESIZE:
                return "noResize";
            case DmlLock.NO_EDIT_POINTS:
                return "noEditPoints";
            case DmlLock.NO_ADJUST_HANDLES:
                return "noAdjustHandles";
            case DmlLock.NO_CHANGE_ARROWHEADS:
                return "noChangeArrowheads";
            case DmlLock.NO_CHANGE_SHAPE_TYPE:
                return "noChangeShapeType";
            case DmlLock.NO_DRILLDOWN:
                return "noDrilldown";
            case DmlLock.NO_UNGROUP:
                return "noUngrp";
            case DmlLock.NO_CROP:
                return "noCrop";
            case DmlLock.NO_TEXT_EDIT:
                return "noTextEdit";
            default:
                throw new IllegalArgumentException("Unexpected lock type.");
        }
    }
}

