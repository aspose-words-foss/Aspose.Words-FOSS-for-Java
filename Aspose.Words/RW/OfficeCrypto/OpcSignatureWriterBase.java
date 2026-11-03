// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackage;
import com.aspose.OpcPackagePart;
import com.aspose.OpcContentType;
import com.aspose.OpcRelationshipType;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.StreamUtil;


/// <summary>
/// Base class for OPC package base document signature writers.
/// </summary>
abstract class OpcSignatureWriterBase
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="package">OPC package to sign.</param>
    /// <param name="xmlKeyInfo">True if certificate data should be written as KeyInfo XML element rather than separate file.</param>
    protected OpcSignatureWriterBase(OpcPackage _package, boolean xmlKeyInfo)
    {
        mPackage = _package;
        mXmlKeyInfo = xmlKeyInfo;
    }

    /// <summary>
    /// Signs OpcPackage.
    /// </summary>
    void write(DigitalSignature signature) throws Exception
    {
        // Collect References.
        ReferenceCollection refs = new ReferenceCollection(new OpcReferenceResolver(mPackage));

        // Add root relationships.
        addRelationshipReference(mPackage, null, refs);

        // Add parts which are not excluded and their relationships.
        for (OpcPackagePart part : mPackage.getParts())
        {
            if (isExcludedPart(part) || (OpcContentType.RELATIONSHIPS.equals(part.getContentType())))
                continue;

            // Add reference to part relationships.
            if (part.getRels().getCount() > 0)
                addRelationshipReference(mPackage, part, refs);

            refs.add(new Reference(part.getName(), part.getContentType()));
        }

        String signatureOriginPartName = getSignatureOriginPartName();
        OpcPackagePart signatureOriginPart = fetchSignatureOriginPart(mPackage, signatureOriginPartName, OpcRelationshipType.DIGITAL_SIGNATURE_ORIGIN);

        String signaturePartName = getSignaturePartName(mPackage);

        // Append signature to package.
        OpcPackagePart signaturePart = mPackage.createChildPart(
            signatureOriginPart,
            signaturePartName,
            OpcContentType.DIGITAL_SIGNATURE,
            OpcRelationshipType.DIGITAL_SIGNATURE);

        // Get signature stream.
        MemoryStream signatureStream = getSignatureStream(refs, signature);
        signatureStream.setPosition(0);
        StreamUtil.copyStream(signatureStream, signaturePart.getStream());

        if (!mXmlKeyInfo)
        {
            String certificatePartName = getCertificatePartName(mPackage);

            OpcPackagePart certificatePart = mPackage.createChildPart(
                            signaturePart,
                            certificatePartName,
                            OpcContentType.DIGITAL_SIGNATURE_CERTIFICATE,
                            OpcRelationshipType.DIGITAL_SIGNATURE_CERTIFICATE);

            byte[] exportedCert = com.aspose.CryptoUtilPal.getEncodedData(signature.getCertificateHolderInternal());
            certificatePart.getStream().write(exportedCert, 0, exportedCert.length);
        }

        mPackage.updateRelationshipsAndContentTypes();
    }

    protected abstract String getSignatureOriginPartName();

    /// <summary>
    /// Get part name for new signature.
    /// </summary>
    protected abstract String getSignaturePartName(OpcPackage _package);

    /// <summary>
    /// Gets part name for new signature.
    /// </summary>
    protected abstract MemoryStream getSignatureStream(ReferenceCollection refs, DigitalSignature signature) throws Exception;

    /// <summary>
    /// Gets part name for certificate.
    /// </summary>
    protected abstract String getCertificatePartName(OpcPackage _package);

    /// <summary>
    /// Indicates that part should be excluded from signing.
    /// </summary>
    protected abstract boolean isExcludedPart(OpcPackagePart part);

    /// <summary>
    /// Adds reference for relationship collection.
    /// </summary>
    protected abstract void addRelationshipReference(OpcPackage _package, OpcPackagePart part, ReferenceCollection refs) throws Exception;

    /// <summary>
    /// Returns existing or creates new signature origin part.
    /// </summary>
    private static OpcPackagePart fetchSignatureOriginPart(OpcPackage _package, String name, String type) throws Exception
    {
        OpcPackagePart originPart = _package.getPartByRelationshipType(null, type);

        if (originPart == null)
            originPart = _package.createChildPart(null, name, OpcContentType.DIGITAL_SIGNATURE_ORIGIN, type);

        return originPart;
    }

    private /*final*/ OpcPackage mPackage;
    private /*final*/ boolean mXmlKeyInfo;
}

