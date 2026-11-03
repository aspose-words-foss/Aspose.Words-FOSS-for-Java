// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/11/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.FormatterPal;
import com.aspose.Debug;


/// <summary>
/// Writes DrawingML shapes to DOCX.
/// </summary>
/*static*/ class DmlDrawingWriter
{
	/* Simulation of static class by using private constructor */
	private DmlDrawingWriter()
	{}

    static void writeStart(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        if (dml.isTopLevel())
            writeDrawingStart(dml, writer);

        DmlGraphicWriter.writeStart(dml, writer);
    }

    static void writeEnd(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DmlGraphicWriter.writeEnd(dml, writer);

        if (dml.isTopLevel())
            writeDrawingEnd(writer, dml);
    }

    private static void writeDrawingStart(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        //<complexType name="CT_Drawing">
        //  <choice minOccurs="1" maxOccurs="unbounded">
        //      <element ref="wp:anchor" minOccurs="0"/>
        //      <element ref="wp:inline" minOccurs="0"/>
        //  </choice>
        //</complexType>
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        builder.startElement("w:drawing");

        // SPEED We convert attribute collection into a flat property bag for speed to avoid
        // lookup of every defined attribute.
        ShapePrBag shapePr = new ShapePrBag();

        shapePr.fill(dml.getShapePr());
        if (dml.getDmlNode().getDocPrExtensions() != null)
            shapePr.DocPrExtensions = dml.getDmlNode().getDocPrExtensions();

        //<complexType name="CT_Anchor">
        //  <sequence>
        //      <element name="simplePos" type="a:CT_Point2D"/>
        //      <element name="positionH" type="CT_PosH"/>
        //      <element name="positionV" type="CT_PosV"/>
        //      <element name="extent" type="a:CT_PositiveSize2D"/>
        //      <element name="effectExtent" type="CT_EffectExtent" minOccurs="0"/>
        //      <group ref="EG_WrapType"/>
        //      <element name="docPr" type="a:CT_NonVisualDrawingProps" minOccurs="1" maxOccurs="1"/>
        //      <element name="cNvGraphicFramePr" type="a:CT_NonVisualGraphicFrameProperties" minOccurs="0" maxOccurs="1"/>
        //      <element ref="a:graphic" minOccurs="1" maxOccurs="1"/>
        //  </sequence>
        //  <attribute name="distT" type="ST_WrapDistance" use="optional"/>
        //  <attribute name="distB" type="ST_WrapDistance" use="optional"/>
        //  <attribute name="distL" type="ST_WrapDistance" use="optional"/>
        //  <attribute name="distR" type="ST_WrapDistance" use="optional"/>
        //  <attribute name="simplePos" type="xsd:boolean"/>
        //  <attribute name="relativeHeight" type="xsd:unsignedInt" use="required"/>
        //  <attribute name="behindDoc" type="xsd:boolean" use="required"/>
        //  <attribute name="locked" type="xsd:boolean" use="required"/>
        //  <attribute name="layoutInCell" type="xsd:boolean" use="required"/>
        //  <attribute name="hidden" type="xsd:boolean" use="optional"/>
        //  <attribute name="allowOverlap" type="xsd:boolean" use="required"/>
        //</complexType>
        builder.startElement(dml.getShapePr().isInline() ? "wp:inline" : "wp:anchor");

        builder.writeAttribute("distT", shapePr.DistanceTop);
        builder.writeAttribute("distB", shapePr.DistanceBottom);
        builder.writeAttribute("distL", shapePr.DistanceLeft);
        builder.writeAttribute("distR", shapePr.DistanceRight);

        if (!dml.getShapePr().isInline())
        {
            // The 'hidden' attribute is not read/written because MS Word ignores it, see the chapter 2.1.1345 of
            // [MS-OI29500].

            builder.writeAttribute("simplePos", false);
            builder.writeAttribute("relativeHeight", writer.getSaveInfo().getShapeZIndex(dml));
            builder.writeAttribute("behindDoc", shapePr.BehindText, ShapePr.BEHIND_TEXT_DEFAULT);
            builder.writeAttribute("locked", shapePr.AnchorLocked, ShapePr.ANCHOR_LOCKED_DEFAULT);
            builder.writeAttribute("layoutInCell", shapePr.AllowInCell, ShapePr.ALLOW_IN_CELL_DEFAULT);
            builder.writeAttribute("allowOverlap", shapePr.AllowOverlap, ShapePr.ALLOW_OVERLAP_DEFAULT);

            DmlBasicsWriter.writeXY("wp:simplePos", 0, 0, builder);
            writePositionH(shapePr, writer);
            writePositionV(shapePr, writer);
        }

        writeExtent(shapePr, writer);
        writeEffectExtent(shapePr, writer);
        writeWrap(shapePr, writer);
        writeDocPr(shapePr, writer);
        writeCNvGraphicFramePr(shapePr, writer);
    }

    private static void writeDrawingEnd(DocxDocumentWriterBase writer, ShapeBase dml)
    {
        writeSizeRelH(dml.getShapePr(), writer);
        writeSizeRelV(dml.getShapePr(), writer);

        writer.getCurrentBuilder().endElement();   // wp:inline or anchor
        writer.getCurrentBuilder().endElement("w:drawing");
    }

    private static void writeSizeRelH(ShapePr shapePr, DocxDocumentWriterBase writer)
    {
        // DD: Todo this can be a good candidate for a complex attr: when two attrs are used together often we can treat them as a complex attr and 
        // incapsulate logic (WORDSNET-13478).
        if (shapePr.contains(ShapeAttr.RELATIVE_WIDTH) &&
            shapePr.contains(ShapeAttr.WIDTH_PERCENT)) // MS Word 2013 raises error if sizeRelH has no pctWidth
        {
            NrxXmlBuilder builder = writer.getCurrentBuilder();
            builder.startElement("wp14:sizeRelH");
            builder.writeAttribute("relativeFrom", DmlEnum.relativeWidthToDml(shapePr.get(ShapeAttr.RELATIVE_WIDTH)));

            writePercentage("wp14:pctWidth", (/*int*/Integer)shapePr.get(ShapeAttr.WIDTH_PERCENT), builder,
                writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);

            builder.endElement(); // wp14:sizeRelH
        }
    }

    /// <summary>
    /// Writes element of percentage type.
    /// </summary>
    private static void writePercentage(String element, int vmlPercent, NrxXmlBuilder builder, boolean isIsoStrict)
    {
        double asFraction = DmlPercentageUtil.fromDmlPercent(DmlPercentageUtil.vmlToDmlPercent(vmlPercent));
        builder.writeElement(element, DmlPercentageUtil.toPercentOrDmlPercent(asFraction, isIsoStrict));
    }

    private static void writeSizeRelV(ShapePr shapePr, DocxDocumentWriterBase writer)
    {
        // DD: Todo this can be a good candidate for a complex attr: when two attrs are used together often we can treat them as a complex attr and 
        // incapsulate logic (WORDSNET-13478).
        if (shapePr.contains(ShapeAttr.RELATIVE_HEIGHT) &&
            shapePr.contains(ShapeAttr.HEIGHT_PERCENT)) // MS Word 2013 raises error if sizeRelV has no pctHeight
        {
            NrxXmlBuilder builder = writer.getCurrentBuilder();
            builder.startElement("wp14:sizeRelV");
            builder.writeAttribute("relativeFrom", DmlEnum.relativeHeightToDml(shapePr.get(ShapeAttr.RELATIVE_HEIGHT)));

            writePercentage("wp14:pctHeight", (/*int*/Integer)shapePr.get(ShapeAttr.HEIGHT_PERCENT), builder,
                writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);

            builder.endElement(); // wp14:sizeRelV
        }
    }
    
    private static void writePositionH(ShapePrBag shapePr, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        // LeftPercent is not null, it means Choice was read, so we have to write AlternateContent.  
        if (shapePr.LeftPercent != null)
            writePositionAlternateContent(shapePr, builder, true, isIsoStrict);
        else
            writePositionHCore(shapePr, builder, true, isIsoStrict);
    }

    private static void writePositionV(ShapePrBag shapePr, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        // TopPercent is not null, it means Choice was read, so we have to write AlternateContent.  
        if (shapePr.TopPercent != null)
            writePositionAlternateContent(shapePr, builder, false, isIsoStrict);
        else
            writePositionVCore(shapePr, builder, true, isIsoStrict);
    }

    /// <summary>
    /// Writes positionV/positionH elements covered in AlternateContent.
    /// </summary>
    private static void writePositionAlternateContent(ShapePrBag shapePr, NrxXmlBuilder builder, boolean isPositionH,
        boolean isIsoStrict)
    {
        builder.startElement("mc:AlternateContent");
        builder.writeAttribute("xmlns:mc", 
            DocxNamespaces.getNamespace(DocxNamespace.MARKUP_COMPATIBILITY, isIsoStrict));

        builder.startElement("mc:Choice");
        builder.writeAttribute("Requires", "c14");
        builder.writeAttribute("xmlns:c14", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_2007, isIsoStrict));
        if (isPositionH)
            writePositionHCore(shapePr, builder, false, isIsoStrict);
        else
            writePositionVCore(shapePr, builder, false, isIsoStrict);
        builder.endElement("mc:Choice");

        builder.startElement("mc:Fallback");
        if (isPositionH)
            writePositionHCore(shapePr, builder, true, isIsoStrict);
        else
            writePositionVCore(shapePr, builder, true, isIsoStrict);
        builder.endElement("mc:Fallback");

        builder.endElement("mc:AlternateContent");
    }

    private static void writePositionVCore(ShapePrBag shapePr, NrxXmlBuilder builder, 
        boolean isFallback, boolean isIsoStrict)
    {
        builder.startElement("wp:positionV");
        builder.writeAttribute("relativeFrom", DmlEnum.relativeVerticalPositionToDml(shapePr.RelativeVerticalPosition));

        if ((shapePr.VerticalAlignment != null) &&
            ((/*VerticalAlignment*/Integer)shapePr.VerticalAlignment != VerticalAlignment.NONE) &&
            ((/*VerticalAlignment*/Integer)shapePr.VerticalAlignment != VerticalAlignment.INLINE))
        {
            builder.writeElement("wp:align", DmlEnum.verticalAlignmentToDml(shapePr.VerticalAlignment));
        }
        else
        {
            if (isFallback)
            {
                int top = (shapePr.Top != null) ? ConvertUtilCore.pointToEmu((/*double*/Double)shapePr.Top) : 0;
                builder.writeElement("wp:posOffset", FormatterPal.intToXml(top));
            }
            else
            {
                writePercentage("wp14:pctPosVOffset", (/*int*/Integer)shapePr.TopPercent, builder, isIsoStrict);
            }
        }

        builder.endElement("wp:positionV");
    }

    private static void writePositionHCore(ShapePrBag shapePr, NrxXmlBuilder builder,
        boolean isFallback, boolean isIsoStrict)
    {
        builder.startElement("wp:positionH");
        builder.writeAttribute("relativeFrom", DmlEnum.relativeHorizontalPositionToDml(shapePr.RelativeHorizontalPosition));

        if ((shapePr.HorizontalAlignment != null) &&
            ((/*HorizontalAlignment*/Integer)shapePr.HorizontalAlignment != HorizontalAlignment.NONE))
        {
            builder.writeElement("wp:align", DmlEnum.horizontalAlignmentToDml(shapePr.HorizontalAlignment));
        }
        else
        {
            if (isFallback)
            {
                int left = (shapePr.Left != null) ? ConvertUtilCore.pointToEmu((/*double*/Double)shapePr.Left) : 0;
                builder.writeElement("wp:posOffset", FormatterPal.intToXml(left));
            }
            else
            {
                writePercentage("wp14:pctPosHOffset", (/*int*/Integer)shapePr.LeftPercent, builder, isIsoStrict);
            }
        }

        builder.endElement("wp:positionH");
    }

    private static void writeWrap(ShapePrBag shapePr, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        /*WrapType*/int wrapType = (/*WrapType*/Integer)((shapePr.WrapType != null) ? shapePr.WrapType : ShapePr.WRAP_TYPE_DEFAULT);
        switch (wrapType)
        {
            case WrapType.INLINE:
                // Do nothing.
                break;
            case WrapType.NONE:
                builder.writeEmptyElement("wp:wrapNone");
                break;
            case WrapType.SQUARE:
                builder.startElement("wp:wrapSquare");
                // Have not yet seen distL, distT, distR, distB.
                writeWrapText(shapePr, writer);
                builder.endElement();
                break;
            case WrapType.THROUGH:
                builder.startElement("wp:wrapThrough");
                // Have not yet seen distL, distR.
                writeWrapText(shapePr, writer);
                writeWrapPolygon(shapePr, writer);
                builder.endElement();
                break;
            case WrapType.TIGHT:
                builder.startElement("wp:wrapTight");
                // Have not yet seen distL, distR.
                writeWrapText(shapePr, writer);
                writeWrapPolygon(shapePr, writer);
                builder.endElement();
                break;
            case WrapType.TOP_BOTTOM:
                builder.startElement("wp:wrapTopAndBottom");
                // Have not yet seen distT, distB.
                builder.endElement();
                break;
            default:
                Debug.fail("Write wrap type.");
                break;
        }
    }

    private static void writeWrapText(ShapePrBag shapePr, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        /*WrapSide*/int wrapSide = (shapePr.WrapSide != null) ? (/*WrapSide*/Integer)shapePr.WrapSide : WrapSide.DEFAULT;
        builder.writeAttribute("wrapText", DmlEnum.wrapSideToDml(wrapSide));
    }

    private static void writeWrapPolygon(ShapePrBag shapePr, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        builder.startElement("wp:wrapPolygon");
        builder.writeAttribute("edited", shapePr.EditedWrap);

        if (shapePr.WrapPolygonVertices != null)
        {
            PathPoint[] wrapPolygon = (PathPoint[])shapePr.WrapPolygonVertices;
            writePolygonStart(wrapPolygon[0], builder);
            for (int i = 1; i < wrapPolygon.length; i++)
                writePolygonLineTo(wrapPolygon[i], builder);
            writePolygonLineTo(wrapPolygon[0], builder);
        }
        else
        {
            // Wrap polygon does not exist, but we actually have to write it to OOXML, 
            // it is required by the schema. Let's just write default.
            DmlBasicsWriter.writeXY("wp:start", -389, 0, builder);
            DmlBasicsWriter.writeXY("wp:lineTo", -389, 21340, builder);
            DmlBasicsWriter.writeXY("wp:lineTo", 21795, 21340, builder);
            DmlBasicsWriter.writeXY("wp:lineTo", 21795, 0, builder);
            DmlBasicsWriter.writeXY("wp:lineTo", -389, 0, builder);
        }

        builder.endElement();
    }

    private static void writePolygonStart(PathPoint pt, NrxXmlBuilder builder)
    {
        DmlBasicsWriter.writeXY("wp:start", pt.getX().getValue(), pt.getY().getValue(), builder);
    }

    private static void writePolygonLineTo(PathPoint pt, NrxXmlBuilder builder)
    {
        DmlBasicsWriter.writeXY("wp:lineTo", pt.getX().getValue(), pt.getY().getValue(), builder);
    }

    private static void writeDocPr(ShapePrBag shapePr, DocxDocumentWriterBase writer) throws Exception
    {
        //<complexType name="CT_NonVisualDrawingProps">
        //  <sequence>
        //      <element name="hlinkClick" type="CT_Hyperlink" minOccurs="0" maxOccurs="1"/>
        //      <element name="hlinkHover" type="CT_Hyperlink" minOccurs="0" maxOccurs="1"/>
        //      <element name="extLst" type="CT_OfficeArtExtensionList" minOccurs="0" maxOccurs="1"/>
        //  </sequence>
        //  <attribute name="id" type="ST_DrawingElementId" use="required"/>
        //  <attribute name="name" type="xsd:string" use="required"/>
        //  <attribute name="descr" type="xsd:string" use="optional" default=""/>
        //  <attribute name="hidden" type="xsd:boolean" use="optional" default="false"/>
        //</complexType>
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startElement("wp:docPr");
        builder.writeAttribute("id", shapePr.ShapeId, ShapePr.SHAPE_ID_DEFAULT);
        // WORDSNET-21316 We must always write the name attribute. This is the method that always writes a string attribute.
        builder.writeAttributeString("name", (shapePr.ShapeName != null) ? (String)shapePr.ShapeName : ShapePr.SHAPE_NAME_DEFAULT);
        builder.writeAttribute("descr", shapePr.ShapeDescription);
        builder.writeAttribute("title", shapePr.ShapeTitle);

        if (shapePr.Hidden != null)
            builder.writeAttributeIfTrue("hidden", (/*boolean*/Boolean)shapePr.Hidden);

        DmlHlinkWriter.writeHlink("a:hlinkClick", (String)shapePr.HyperlinkAddress, (String)shapePr.HyperlinkTarget,
            (String)shapePr.ScreenTip, null, writer);

        if (shapePr.DocPrExtensions != null)
            DmlExtensionListWriter.write("a", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict),
                shapePr.DocPrExtensions, writer);

        builder.endElement();
    }

    private static void writeExtent(ShapePrBag shapePr, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        DmlBasicsWriter.writeCxCy(
            "wp:extent",
            ConvertUtilCore.pointToEmu((/*double*/Double)shapePr.Width),
            ConvertUtilCore.pointToEmu((/*double*/Double)shapePr.Height),
            builder);
    }

    private static void writeEffectExtent(ShapePrBag shapePr, DocxDocumentWriterBase writer)
    {
        if ((shapePr.DmlEffectExtentLeft == null) &&
            (shapePr.DmlEffectExtentTop == null) &&
            (shapePr.DmlEffectExtentRight == null) &&
            (shapePr.DmlEffectExtentBottom == null))
            return;

        NrxXmlBuilder builder = writer.getCurrentBuilder();
        builder.startElement("wp:effectExtent");
        builder.writeAttribute("l", shapePr.DmlEffectExtentLeft, ShapePr.DML_EFFECT_EXTENT_LEFT_DEFAULT);
        builder.writeAttribute("t", shapePr.DmlEffectExtentTop, ShapePr.DML_EFFECT_EXTENT_TOP_DEFAULT);
        builder.writeAttribute("r", shapePr.DmlEffectExtentRight, ShapePr.DML_EFFECT_EXTENT_RIGHT_DEFAULT);
        builder.writeAttribute("b", shapePr.DmlEffectExtentBottom, ShapePr.DML_EFFECT_EXTENT_BOTTOM_DEFAULT);
        builder.endElement();
    }

    private static void writeCNvGraphicFramePr(ShapePrBag shapePr, DocxDocumentWriterBase writer)
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        builder.startElement("wp:cNvGraphicFramePr");
        writeGraphicFrameLocks(shapePr, writer);
        builder.endElement();
    }

    private static void writeGraphicFrameLocks(ShapePrBag shapePr, DocxDocumentWriterBase writer)
    {
        if ((shapePr.LockAspectRatio == null) &&
            (shapePr.LockAgainstGrouping == null) &&
            (shapePr.LockPosition == null) &&
            (shapePr.LockAgainstSelect == null) &&
            (shapePr.DmlLockDrillDown == null) &&
            (shapePr.DmlLockResize == null))
            return;

        NrxXmlBuilder builder = writer.getCurrentBuilder();
        builder.startElement("a:graphicFrameLocks");
        builder.writeAttribute("xmlns:a", writer.getDocxNamespaces().getDrawingMLMain());
        builder.writeAttribute("noChangeAspect", shapePr.LockAspectRatio);
        builder.writeAttribute("noGrp", shapePr.LockAgainstGrouping);
        builder.writeAttribute("noMove", shapePr.LockPosition);
        builder.writeAttribute("noSelect", shapePr.LockAgainstSelect);
        builder.writeAttribute("noDrilldown", shapePr.DmlLockDrillDown);
        builder.writeAttribute("noResize", shapePr.DmlLockResize);
        builder.endElement();
    }
}

