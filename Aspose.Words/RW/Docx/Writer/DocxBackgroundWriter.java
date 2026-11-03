// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/06/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


/// <summary>
/// This class allows writing the 17.2.1 background (Document Background) element.
/// </summary>
/*static*/ class DocxBackgroundWriter
{
	/* Simulation of static class by using private constructor */
	private DocxBackgroundWriter()
	{}

    /// <summary>
    /// Writes the w:background element with using the specified writer.
    /// </summary>
    static void write(DocxDocumentWriterBase writer) throws Exception
    {
        Shape shape = writer.getSaveInfo().Document.getBackgroundShape();
        if (!Shape.isVisibleAsBackground(shape))
            return;

        DocxBuilder builder = writer.getCurrentBuilder();
        builder.startElement("w:background");

        DrColor color = (DrColor)shape.getDirectShapeAttrInternal(ShapeAttr.FILL_COLOR);
        if (color == null)
            color = shape.getFillCore().getColorInternal();
        builder.writeAttributeString("w:color", DocxBuilder.getColorString(color));

        if ((shape.getFallbackShape() != null) && (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT))
            writeAlternateContent(shape, writer);
        else
            writeShape(shape, writer);

        builder.endElement(); //w:background
    }

    /// <summary>
    /// Writes a background shape as an alternate content block with VML shape (v:background) in a choice part
    /// and with DML shape in fallback. In this way a background shape is written in Strict OOXML format.
    /// </summary>
    private static void writeAlternateContent(Shape shape, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("mc:AlternateContent");
        builder.startElement("mc:Choice");
        builder.writeAttribute("Requires", DocxDocumentWriterBase.getChoiceRequires(shape));

        writeShape(shape, writer);

        builder.endElement("mc:Choice");
        builder.startElement("mc:Fallback");

        writeShape(shape.getFallbackShape(), writer);

        builder.endElement("mc:Fallback");
        builder.endElement("mc:AlternateContent");
    }

    /// <summary>
    /// Writes a background shape.
    /// </summary>
    private static void writeShape(ShapeBase shape, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            VmlShapeWriter.write(shape, true, builder, writer);
            builder.endElement(); //v:background
        }
        else
        {
            DmlDrawingWriter.writeStart(shape, writer);
            DmlDrawingWriter.writeEnd(shape, writer);
        }
    }
}

