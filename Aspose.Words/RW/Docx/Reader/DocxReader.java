// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2007 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.Stream;
import com.aspose.Debug;
import com.aspose.TempFiler;
import com.aspose.OpcFlatPackage;
import com.aspose.IWarningCallbackCore;
import com.aspose.OpcPackage;
import com.aspose.ms.System.msString;
import com.aspose.OpcPackagePart;
import com.aspose.OpcRelationshipType;
import com.aspose.OpcRelationship;
import com.aspose.AnyXmlReader;
import com.aspose.OpcPackageBase;


/// <summary>
/// The main starter class for reading DOCX documents.
/// </summary>
class DocxReader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    DocxReader(Stream stream, Document doc, LoadOptions loadOptions) throws Exception
    {
        Debug.assertTrue(loadOptions != null);
        mTempFiler = new TempFiler(loadOptions.getTempFolder());

        switch (doc.getOriginalLoadFormat())
        {
            case LoadFormat.FLAT_OPC:
            case LoadFormat.FLAT_OPC_TEMPLATE:
            case LoadFormat.FLAT_OPC_MACRO_ENABLED:
            case LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                mPackage = new OpcFlatPackage(stream);
                break;
            case LoadFormat.DOCX:
            case LoadFormat.DOCM:
            case LoadFormat.DOTX:
            case LoadFormat.DOTM:
                {
                    IWarningCallbackCore callback = null;

                    if (doc.getWarningCallback() != null)
                        callback = new WarningCallbackCoreAdapter(doc.getWarningCallback());

                    mPackage = new OpcPackage(stream, loadOptions.getSkipFormatting(), callback, mTempFiler);
                    break;
                }
            default:
                throw new IllegalStateException(msString.format("Unexpected LoadFormat value {0}.", doc.getOriginalLoadFormat()));
        }

        mDoc = doc;
        mLoadOptions = loadOptions;

        // WORDSNET-17739 Mimic Word and use original document size, when retrieving "Bytes" property from the model.
        // Max size of file MSW can open 512 Mb.
        mDoc.getBuiltInDocumentProperties().get(PropertyName.BYTES).setDefaultValueInternal((int)stream.getLength());

        readSignatures();
    }

    /// <summary>
    /// Read digital signatures if they exists.
    /// </summary>
    void readSignatures() throws Exception
    {
        OpcPackagePart originPart = mPackage.getPartByRelationshipType(null, OpcRelationshipType.DIGITAL_SIGNATURE_ORIGIN);

        // there is no signature data.
        if (originPart == null)
            return;

        for (OpcRelationship rel : originPart.getRels())
        {
            String signaturePartName = originPart.getRelatedPartName(rel);

            OpcPackagePart signaturePart = mPackage.fetchPartByName(signaturePartName);
            XmlDsigReader.read(signaturePart.getStream(), new OpcReferenceResolver(mPackage),
                mDoc.getDigitalSignatures(), mLoadOptions.getWarningCallback());
        }
    }

    /// <summary>
    /// Imports a DOCX document.
    /// </summary>
    void read() throws Exception
    {
        try
        {
            // We have to check if this Docx document is ISO/IEC 29500 Strict.
            checkIsoStrict();
            DocxCorePropertiesReader.read(this);
            DocxExtentedPropertiesReader.read(this);
            DocxCustomPropertiesReader.read(this);

            DocxDocumentReader documentReader = readDocument();
            DocxTaskPaneAddinsReader.read(documentReader);
            DocxCustomPartReader.read(mPackage.getRels(), mPackage, mDoc.getPackageCustomParts());
        }
        finally
        {
            if (mTempFiler != null)
                mTempFiler.cleanup();
        }
    }

    private void checkIsoStrict() throws Exception
    {
        if (mPackage.exists(getRelTypes().getOfficeDocument()))
        {
            // Get document.xml part by transition namespace.
            mPackage.fetchPartByRelationshipType(null, getRelTypes().getOfficeDocument());
        }
        else
        {
            mRelTypes = null;

            // Actually we can stop here, and just call MarkAsIsoStrict(), but according to specification
            // ISO29500-1 p17.2.3 document has attribute w:conformance,
            // which we can also check to make sure it is strict.
            // Suppose we read strict OOXML.
            mDoc.getComplianceInfo().markAsIsoStrict();

            // Try to get document.xml part by strict namespace.
            OpcPackagePart documentPart = mPackage.fetchPartByRelationshipType(null, getRelTypes().getOfficeDocument());
            if (documentPart != null)
            {
                DocxDocumentReader documentReader = DocxReaderFactory.createDocumentReader(
                    mPackage, documentPart, mDoc, mLoadOptions, mDoc.getComplianceInfo());

                // If we successfully read OOXML document as strict, set the appropriate ComplianceInfo.
                // According to specification ISO29500-1, if this attribute is omitted, its default value is transitional.
                if (!"strict".equals(documentReader.getXmlReader().readAttribute("conformance", "")))
                    mDoc.getComplianceInfo().setCompliance(OoxmlComplianceCore.ISO_TRANSITIONAL);
            }
        }
    }

    private DocxDocumentReader readDocument() throws Exception
    {
        OpcPackagePart documentPart = mPackage.fetchPartByRelationshipType(null, getRelTypes().getOfficeDocument());
        DocxDocumentReader documentReader = DocxReaderFactory.createDocumentReader(
            mPackage, documentPart, mDoc, mLoadOptions, mDoc.getComplianceInfo());
        documentReader.read();

        return documentReader;
    }

    /// <summary>
    /// Creates a DOCX reader for a part that is a child of the package and
    /// at the end of the specified relationship. Returns null if there is no such part.
    /// </summary>
    DocxXmlReader createPackageChildPartReader(String relType) throws Exception
    {
        OpcPackagePart part = mPackage.getPartByRelationshipType(null, relType);
        return (part != null) ? new DocxXmlReader(part, getDocument().getComplianceInfo()) : null;
    }

    /// <summary>
    /// You need to pass the "[Content_Types].xml" stream positioned at the beginning.
    /// Does NOT preserve the stream position.
    /// </summary>
    static /*LoadFormat*/int detectLoadFormat(Stream contentTypesStream) throws Exception
    {
        AnyXmlReader reader = new AnyXmlReader(contentTypesStream);
        while (reader.readChild("Types"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"Default"*/0:
                case /*"Override"*/1:
                {
                    String contentType = reader.readAttribute("ContentType", "");
                    switch (gStringSwitchMap.of(contentType))
                    {
                        case /*DocxContentType.DOCUMENT*/2:
                            return LoadFormat.DOCX;
                        case/*DocxContentType.DOCUMENT_MACRO_ENABLED*/3:
                            return LoadFormat.DOCM;
                        case/*DocxContentType.TEMPLATE*/4:
                            return LoadFormat.DOTX;
                        case/*DocxContentType.TEMPLATE_MACRO_ENABLED*/5:
                            return LoadFormat.DOTM;
                        default:
                            break;
                    }
                    break;
                }
                default:
                    reader.ignoreElement();
                    break;
            }
        }
        return LoadFormat.UNKNOWN;
    }

    /// <summary>
    /// You need to pass the "_rels/.rels" stream positioned at the beginning.
    /// Does NOT preserve the stream position.
    /// </summary>
    static boolean isDigitalSignaturePresent(Stream relsStream) throws Exception
    {
        return containsRelationshipOfType(relsStream, OpcRelationshipType.DIGITAL_SIGNATURE_ORIGIN);
    }

    /// <summary>
    /// You need to pass the "_rels/.rels" stream positioned at the beginning.
    /// Does NOT preserve the stream position.
    /// </summary>
    static boolean isVbaProjectPresent(Stream relsStream) throws Exception
    {
        return containsRelationshipOfType(relsStream, DocxRelationshipTypes.getType(DocxRelationshipType.VBA, false));
    }

    private  static boolean containsRelationshipOfType(Stream relsStream, String relType) throws Exception
    {
        AnyXmlReader reader = new AnyXmlReader(relsStream);
        while (reader.readChild("Relationships"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"Relationship"*/6:
                {
                    if (msString.equals(reader.readAttribute("Type", ""), relType))
                        return true;

                    break;
                }
                default:
                    reader.ignoreElement();
                    break;
            }
        }
        return false;
    }

    DocxRelationshipTypes getRelTypes()
    {
        if (mRelTypes == null)
            mRelTypes = new DocxRelationshipTypes(mDoc.getComplianceInfo().isIsoStrict());
        return mRelTypes;
    }

    Document getDocument() { return mDoc; }

    private /*final*/ OpcPackageBase mPackage;
    private /*final*/ Document mDoc;
    private /*final*/ LoadOptions mLoadOptions;
    private DocxRelationshipTypes mRelTypes;
    private /*final*/ TempFiler mTempFiler;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"Default",
		"Override",
		"application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml",
		"application/vnd.ms-word.document.macroEnabled.main+xml",
		"application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml",
		"application/vnd.ms-word.template.macroEnabledTemplate.main+xml",
		"Relationship"
	);

}

