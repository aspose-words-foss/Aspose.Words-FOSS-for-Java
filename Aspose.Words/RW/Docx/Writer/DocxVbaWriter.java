// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackagePart;
import com.aspose.FileSystem;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.AnyXmlBuilder;
import com.aspose.words.ref.Ref;


/// <summary>
/// Writes the VBA project and its related parts (digital signature and vba data).
/// </summary>
class DocxVbaWriter
{
    static void write(DocxDocumentWriter writer) throws Exception
    {
        if (!writer.getDocument().hasMacros())
            return;

        OpcPackagePart vbaPart = writeVbaProject(writer);
        writeVbaSignature(writer, vbaPart);
        writeVbaData(writer, vbaPart);
    }

    private static OpcPackagePart writeVbaProject(DocxDocumentWriter writer) throws Exception
    {
        // Add the vba project part.
        OpcPackagePart vbaPart = writer.getPackage().createChildPart(
            writer.getDocumentPart(), "vbaProject.bin", DocxContentType.VBA, writer.getRelTypes().getVba());

        // Save the vba project to the part.
        FileSystem vbaFs = new FileSystem(writer.getDocument().getVbaProject().getStorage());
        vbaFs.save(vbaPart.getStream());

        return vbaPart;
    }

    /// <summary>
    /// Writes the VBA digital signature if present.
    /// </summary>
    private static void writeVbaSignature(DocxDocumentWriter writer, OpcPackagePart vbaPart) throws Exception
    {
        Debug.assertTrue(vbaPart != null);

        Document doc = writer.getDocument();

        if (doc.getVbaProject() == null)
            return;

        byte[] vbaSignature = doc.getVbaProject().getSignature();
        if (vbaSignature == null)
            return;


        // Add the vba signature part.
        OpcPackagePart vbaSignaturePart = writer.getPackage().createChildPart(
            vbaPart, "vbaProjectSignature.bin", DocxContentType.VBA_PROJECT_SIGNATURE, writer.getRelTypes().getVbaProjectSignature());

        // Save the vba signature to the part, stripping away the DOC prefix.
        vbaSignaturePart.getStream().write(
            vbaSignature,
            Document.VBA_SIGNATURE_PREFIX_LENGTH,
            vbaSignature.length - Document.VBA_SIGNATURE_PREFIX_LENGTH);
    }

    /// <summary>
    /// Although the SPEC says the VBA project must always have a relationship to the VBA data part,
    /// I see that many documents have VBA project, but no VBA part. So let's do the same and write only when needed.
    /// </summary>
    private static void writeVbaData(DocxDocumentWriter writer, OpcPackagePart vbaPart) throws Exception
    {
        Debug.assertTrue(vbaPart != null);

        Document doc = writer.getDocument();
        if (!hasDocEvents(doc) && !hasMacroNames(doc))
            return;

        String dummyRelId = null;
        Ref<String> referenceToDummyRelId = new Ref<String>(dummyRelId);
        DocxBuilder builder = writer.createChildPartAndBuilder(
            vbaPart, "vbaData.xml", DocxContentType.VBA_DATA, writer.getRelTypes().getVbaData(), /*out*/ referenceToDummyRelId);
        dummyRelId = referenceToDummyRelId.get();

        builder.startDocumentWithStandardNamespaces("wne:vbaSuppData");
        writeDocEvents(writer.getDocument(), builder);
        writeMcds(writer.getDocument(), builder);
        builder.endDocument(); 
    }

    private static boolean hasMacroNames(Document doc)
    {
        if (doc.getVbaProject() == null)
            return false;

        ArrayList<String> macroNames = doc.getVbaProject().getMacroNames();
        return (macroNames != null) && (macroNames.size() > 0);
    }

    private static boolean hasDocEvents(Document doc)
    {
        return (doc.getVbaDocumentEvents() != 0);
    }

    private static void writeDocEvents(Document doc, AnyXmlBuilder builder)
    {
        if (!hasDocEvents(doc))
            return;

        builder.startElement("wne:docEvents");
        /*VbaDocumentEvents*/int events = doc.getVbaDocumentEvents();
        writeDocEvent(events, VbaDocumentEvents.NEW, "wne:eventDocNew", builder);
        writeDocEvent(events, VbaDocumentEvents.OPEN, "wne:eventDocOpen", builder);
        writeDocEvent(events, VbaDocumentEvents.CLOSE, "wne:eventDocClose", builder);
        writeDocEvent(events, VbaDocumentEvents.SYNC, "wne:eventDocSync", builder);
        writeDocEvent(events, VbaDocumentEvents.XML_AFTER_INSERT, "wne:eventDocXmlAfterInsert", builder);
        writeDocEvent(events, VbaDocumentEvents.XML_BEFORE_DELETE, "wne:eventDocXmlBeforeDelete", builder);
        writeDocEvent(events, VbaDocumentEvents.CONTENT_CONTROL_AFTER_INSERT, "wne:eventDocContentControlAfterInsert", builder);
        writeDocEvent(events, VbaDocumentEvents.CONTENT_CONTROL_BEFORE_DELETE, "wne:eventDocContentControlBeforeDelete", builder);
        writeDocEvent(events, VbaDocumentEvents.CONTENT_CONTROL_ON_EXIT, "wne:eventDocContentControlOnExit", builder);
        writeDocEvent(events, VbaDocumentEvents.CONTENT_CONTROL_ON_ENTER, "wne:eventDocContentControlOnEnter", builder);
        writeDocEvent(events, VbaDocumentEvents.STORE_UPDATE, "wne:eventDocStoreUpdate", builder);
        writeDocEvent(events, VbaDocumentEvents.CONTENT_CONTROL_CONTENT_UPDATE, "wne:eventDocContentControlContentUpdate", builder);
        writeDocEvent(events, VbaDocumentEvents.BUILDING_BLOCK_AFTER_INSERT, "wne:eventDocBuildingBlockAfterInsert", builder);
        builder.endElement();   // docEvents
    }

    private static void writeDocEvent(/*VbaDocumentEvents*/int events, /*VbaDocumentEvents*/int mask, String elemName, AnyXmlBuilder builder)
    {
        if ((events & mask) != 0)
            builder.writeEmptyElement(elemName);
    }

    private static void writeMcds(Document doc, NrxXmlBuilder builder)
    {
        if (!hasMacroNames(doc))
            return;

        ArrayList<String> macroNames = doc.getVbaProject().getMacroNames();
        builder.startElement("wne:mcds");
        for (String macroName : macroNames)
        {
            builder.startElement("wne:mcd");
            builder.writeAttribute("wne:macroName", macroName.toUpperCase());   // Must always be uppercase of name.
            builder.writeAttribute("wne:name", macroName);
            builder.writeAttribute("wne:bEncrypt", "00");   // Must always be this value.
            builder.writeAttribute("wne:cmg", "56");        // Must always be this value.
            builder.endElement();   // mcd
        }
        builder.endElement();   // mcds
    }
}

