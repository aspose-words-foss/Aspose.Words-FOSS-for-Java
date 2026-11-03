// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackagePart;
import com.aspose.ms.System.Guid;


/// <summary>
/// Writes the glossary document to an OOXML part.
/// </summary>
class DocxGlossaryWriter extends DocxDocumentWriterBase
{
    DocxGlossaryWriter(DocxWriter writer)
    {
    	super(writer, writer.getDocument().getGlossaryDocument(), writer.getWarningCallback());
	
    }

    protected /*override*/ OpcPackagePart doCreateDocumentPart() throws Exception
    {
        OpcPackagePart mainDocPart = getPackage().fetchPartByRelationshipType(null, getRelTypes().getOfficeDocument());
        return getPackage().createChildPart(mainDocPart, "glossary/document.xml", DocxContentType.GLOSSARY_DOCUMENT, getRelTypes().getGlossaryDocument());
    }

    protected /*override*/ void doWrite() throws Exception
    {
        DocxBuilder builder = getCurrentBuilder();

        builder.startDocumentWithStandardNamespaces("w:glossaryDocument");
        builder.startElement("w:docParts");

        getDocument().accept(this);

        builder.endElement();   // docParts
        builder.endDocument();
    }

    public /*override*/ /*VisitorAction*/int visitBuildingBlockStart(BuildingBlock block) throws Exception
    {
        getCurrentBuilder().startElement("w:docPart");
        writeDocPartPr(block);

        getCurrentBuilder().startElement("w:docPartBody");
        writeSections(block);
        getCurrentBuilder().endElement();   // docPartBody
        
        getCurrentBuilder().endElement();    // docPart
        return VisitorAction.SKIP_THIS_NODE;
    }

    private void writeDocPartPr(BuildingBlock block)
    {
        DocxBuilder builder = getCurrentBuilder();
        builder.startElement("w:docPartPr");

        builder.startElement("w:name");
        builder.writeAttributeIfTrue("w:decorated", block.getDecorated());
        builder.writeAttribute("w:val", block.getName());
        builder.endElement();

        builder.writeVal("w:style", block.getStyle());

        builder.startElement("w:category");
        builder.writeVal("w:name", block.getCategory());
        builder.writeVal("w:gallery", DocxEnum.docPartGalleryToDocx(block.getGallery()));
        builder.endElement();

        if (block.getType() != BuildingBlockType.NONE)
        {
            builder.startElement("w:types");
            if (block.getType() == BuildingBlockType.ALL)
                builder.writeAttribute("all", true);
            else
                builder.writeVal("w:type", DocxEnum.docPartTypeToDocx(block.getType()));
            builder.endElement();
        }

        builder.startElement("w:behaviors");
        builder.writeVal("w:behavior", DocxEnum.docPartBehaviorToDocx(block.getBehavior()));
        builder.endElement();

        builder.writeVal("w:description", block.getDescription());

        if (!block.getGuidInternal().equals(Guid.Empty))
            builder.writeVal("w:guid", block.getGuidInternal().toString("B").toUpperCase());

        builder.endElement();   // docPartPr
    }
}

