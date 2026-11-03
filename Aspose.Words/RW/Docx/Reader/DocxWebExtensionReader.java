// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Implements reading of the "2.2.7 CT_OsfWebExtension" element.
/// </summary>
class DocxWebExtensionReader
{
    /// <summary>
    /// Reads "2.2.7 CT_OsfWebExtension" element.
    /// </summary>
    /// <param name="relId">Relation identifier of the package part with web extension.</param>
    /// <param name="reader">Document reader object.</param>
    /// <param name="extension">Extension which receives read data.</param>
    static void readWebExtension(String relId, DocxDocumentReader reader, WebExtension extension) throws Exception
    {
        Debug.assertTrue(extension != null);

        DocxXmlReader webExtensionReader = reader.switchToPartReaderByRelId(relId);
 
        while (webExtensionReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(webExtensionReader.getLocalName()))
            {
                case /*"id"*/0:
                    extension.setId(webExtensionReader.getValue());
                    break;
                case /*"frozen"*/1:
                    extension.isFrozen(webExtensionReader.getValueAsBool());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        while (webExtensionReader.readChild("webextension")) // we:webextension
        {
            switch (gStringSwitchMap.of(webExtensionReader.getLocalName()))
            {
                case /*"reference"*/2:
                    readWebExtensionReference(webExtensionReader, extension.getReference());
                    break;
                case /*"alternateReferences"*/3:
                    readAlternateReferences(webExtensionReader, extension.getAlternateReferences());
                    break;
                case /*"properties"*/4:
                    readWebExtensionPropertyBag(webExtensionReader, extension.getProperties());
                    break;
                case /*"bindings"*/5:
                    readWebExtensionBindingList(webExtensionReader, extension.getBindings());
                    break;
                case /*"snapshot"*/6:
                    extension.setSnapshot(DmlFillReader.readBlip(reader));
                    break;
                default:
                    // For example "extLst", which may be ignored according to MS-OWEXML spec.
                    webExtensionReader.ignoreElement();
                    break;
            }
        }

        reader.restorePartReader();
    }

    /// <summary>
    /// Read "2.2.5 CT_OsfWebExtensionReference" element.
    /// </summary>
    private static void readWebExtensionReference(DocxXmlReader xmlReader, WebExtensionReference reference)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"id"*/0:
                    reference.setId(xmlReader.getValue());
                    break;
                case /*"store"*/7:
                    reference.setStore(xmlReader.getValue());
                    break;
                case /*"version"*/8:
                    reference.setVersion(xmlReader.getValue());
                    break;
                case /*"storeType"*/9:
                    reference.setStoreType(DocxEnum.docxToWebExtensionStoreType(xmlReader.getValue().toLowerCase()));
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        // "WebExtensionReference" has one child node "extLst" which may be ignored according to MS-OWEXML spec.
    }

    /// <summary>
    /// Reads "2.2.6 CT_OsfWebExtensionReferenceList" element.
    /// </summary>
    private static void readAlternateReferences(DocxXmlReader xmlReader, WebExtensionReferenceCollection references)
    {
        while (xmlReader.readChild("alternateReferences")) // we:webextension
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"reference"*/2:
                {
                    WebExtensionReference reference = new WebExtensionReference();
                    references.add(reference);

                    readWebExtensionReference(xmlReader, reference);
                    break;
                }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads "2.2.2 CT_OsfWebExtensionPropertyBag" element.
    /// </summary>
    private static void readWebExtensionPropertyBag(DocxXmlReader xmlReader, WebExtensionPropertyCollection properties)
    {
        while (xmlReader.readChild("properties")) // we:properties
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"property"*/10:
                    WebExtensionProperty property = readWebExtensionProperty(xmlReader);
                    properties.add(property);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads "2.2.1 CT_OsfWebExtensionProperty" element.
    /// </summary>
    private static WebExtensionProperty readWebExtensionProperty(DocxXmlReader xmlReader)
    {
        WebExtensionProperty property = new WebExtensionProperty();

        while (xmlReader.moveToNextAttribute()) // we:property
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"name"*/11:
                    property.setName(xmlReader.getValue());
                    break;
                case /*"value"*/12:
                    property.setValue(xmlReader.getValue());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        return property;
    }

    /// <summary>
    /// Reads "2.2.4 CT_OsfWebExtensionBindingList" element.
    /// </summary>
    private static void readWebExtensionBindingList(DocxXmlReader xmlReader, WebExtensionBindingCollection bindings)
    {
        while (xmlReader.readChild("bindings")) // we:bindings
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"binding"*/13:
                    WebExtensionBinding binding = readWebExtensionBinding(xmlReader);
                    bindings.add(binding);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads "2.2.3 CT_OsfWebExtensionBinding" element.
    /// </summary>
    private static WebExtensionBinding readWebExtensionBinding(DocxXmlReader xmlReader)
    {
        WebExtensionBinding binding = new WebExtensionBinding();

        while (xmlReader.moveToNextAttribute()) // we:binding
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"id"*/0:
                    binding.setId(xmlReader.getValue());
                    break;
                case /*"appref"*/14:
                    binding.setAppRef(xmlReader.getValue());
                    break;
                case /*"type"*/15:
                    binding.setBindingType(DocxEnum.docxToWebExtensionBindingType(xmlReader.getValue().toLowerCase()));
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        // "WebExtensionBinding" has one child node "extLst" which may be ignored according to MS-OWEXML spec.

        return binding;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"id",
		"frozen",
		"reference",
		"alternateReferences",
		"properties",
		"bindings",
		"snapshot",
		"store",
		"version",
		"storeType",
		"property",
		"name",
		"value",
		"binding",
		"appref",
		"type"
	);

}

