// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackagePart;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.words.ref.Ref;


/// <summary>
/// Exports web extension to appropriate package part.
/// </summary>
class DocxWebExtensionWriter
{
    /// <summary>
    /// This class is static. Instance creation is not allowed.
    /// </summary>
    private DocxWebExtensionWriter(DocxDocumentWriter writer)
    {
        mWriter = writer;
    }

    /// <summary>
    /// Writes web extensions document part.
    /// </summary>
    static String write(DocxDocumentWriter writer, OpcPackagePart taskPanesPart, WebExtension extension) throws Exception
    {
        Debug.assertTrue(extension != null);

        DocxWebExtensionWriter extensionWriter = new DocxWebExtensionWriter(writer);
        return extensionWriter.writeCore(taskPanesPart, extension);
    }

    private String writeCore(OpcPackagePart taskPanesPart, WebExtension extension) throws Exception
    {
        String partName = msString.format("webextension{0}.xml", 
            mWriter.getNextEmbeddedPartNumber(mWriter.getRelTypes().getWebExtension()));

        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder builder = mWriter.createChildPartAndBuilder(taskPanesPart, partName,
            DocxContentType.WEB_EXTENSION, mWriter.getRelTypes().getWebExtension(), /*out*/ referenceToRelId);
        relId = referenceToRelId.get();
        mWriter.pushBuilder(builder);

        writeWebExtensionCore(builder, extension);

        mWriter.popBuilder();
        return relId;
    }

    /// <summary>
    /// Writes "2.2.7 CT_OsfWebExtension" element.
    /// </summary>
    private void writeWebExtensionCore(DocxBuilder builder, WebExtension extension) throws Exception
    {
        final String WEB_EXTENSION_ELEMENT = "we:webextension";
        builder.startDocument(WEB_EXTENSION_ELEMENT);

        builder.writeAttributeString("xmlns:we", "http://schemas.microsoft.com/office/webextensions/webextension/2010/11");
        builder.writeAttributeString("id", extension.getId());
        builder.writeAttributeIfTrue("frozen", extension.isFrozen());

        writeReference(builder, extension.getReference());
        writeAlternateReferences(builder, extension.getAlternateReferences());
        writeProperties(builder, extension.getProperties());
        writeBindings(builder, extension.getBindings());

        final String SNAPSHOT_NAME = "we:snapshot";
        DmlBlip shanShot = extension.getSnapshot() == null ? new DmlBlip() : extension.getSnapshot();
        DmlFillWriter.writeBlip(shanShot, mWriter, SNAPSHOT_NAME);

        builder.endDocument();
    }

    /// <summary>
    /// Writes "2.2.5 CT_OsfWebExtensionReference" element.
    /// </summary>
    private static void writeReference(DocxBuilder builder, WebExtensionReference reference)
    {
        final String WEB_EXTENSION_REFERENCE_ELEMENT = "we:reference";
        builder.startElement(WEB_EXTENSION_REFERENCE_ELEMENT);

        builder.writeAttributeString("id", reference.getId());
        builder.writeAttributeString("version", reference.getVersion());
        builder.writeAttributeString("store", reference.getStore());
        builder.writeAttributeString("storeType", DocxEnum.webExtensionStoreTypeToDocx(reference.getStoreType()));

        builder.endElement(WEB_EXTENSION_REFERENCE_ELEMENT);
    }

    /// <summary>
    /// Writes "2.2.6 CT_OsfWebExtensionReferenceList" element.
    /// </summary>
    private static void writeAlternateReferences(DocxBuilder builder, WebExtensionReferenceCollection references)
    {
        final String WEB_EXTENSION_ALTERNATE_REFERENCES_ELEMENT = "we:alternateReferences";
        if (checkEmptyCollection(references, builder, WEB_EXTENSION_ALTERNATE_REFERENCES_ELEMENT))
            return;

        builder.startElement(WEB_EXTENSION_ALTERNATE_REFERENCES_ELEMENT);

        for (WebExtensionReference reference : (Iterable<WebExtensionReference>) references)
            writeReference(builder, reference);

        builder.endElement(WEB_EXTENSION_ALTERNATE_REFERENCES_ELEMENT);
    }

    /// <summary>
    /// Write "2.2.2 CT_OsfWebExtensionPropertyBag" element.
    /// </summary>
    private static void writeProperties(DocxBuilder builder, WebExtensionPropertyCollection properties)
    {
        final String WEB_EXTENSION_PROPERTIES_ELEMENT = "we:properties";
        if (checkEmptyCollection(properties, builder, WEB_EXTENSION_PROPERTIES_ELEMENT))
            return;

        builder.startElement(WEB_EXTENSION_PROPERTIES_ELEMENT);

        for (WebExtensionProperty property : (Iterable<WebExtensionProperty>) properties)
            builder.writeElementWithAttributes("we:property", "name", property.getName(), "value", property.getValue());

        builder.endElement(WEB_EXTENSION_PROPERTIES_ELEMENT);
    }

    /// <summary>
    /// Writes "2.2.4 CT_OsfWebExtensionBindingList" element.
    /// </summary>
    private static void writeBindings(DocxBuilder builder, WebExtensionBindingCollection bindings)
    {
        final String WEB_EXTENSION_BINDINGS_ELEMENT = "we:bindings";
        if (checkEmptyCollection(bindings, builder, WEB_EXTENSION_BINDINGS_ELEMENT))
            return;

        builder.startElement(WEB_EXTENSION_BINDINGS_ELEMENT);

        for (WebExtensionBinding binding : (Iterable<WebExtensionBinding>) bindings)
        {
            builder.writeElementWithAttributes("we:binding", "id", binding.getId(), "type",
                DocxEnum.webExtensionBindingTypeToDocx(binding.getBindingType()), "appref", binding.getAppRef());
        }

        builder.endElement(WEB_EXTENSION_BINDINGS_ELEMENT);
    }

    /// <summary>
    /// Returns true when specified collection is empty. And writes empty element at this case.
    /// </summary>
    private static <T extends Object> boolean checkEmptyCollection(BaseWebExtensionCollection<T> items, DocxBuilder builder, String elementName)
    {
        boolean isEmpty = items.getCount() == 0;

        if (isEmpty)
            builder.writeEmptyElement(elementName);

        return isEmpty;
    }

    /// <summary>
    /// Document writer.
    /// </summary>
    private /*final*/ DocxDocumentWriter mWriter;
}

