// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/09/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.AnyXmlBuilder;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.DigestAlgorithm;
import com.aspose.CryptoUtilPal;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Guid;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.Convert;
import com.aspose.HashUtil;


/// <summary>
/// Implements XmlDsig signature generation for OOXML documents.
/// </summary>
class XmlDsigSignerOoxml extends XmlDsigSignerMsFormatBase
{
    /*override*/ MemoryStream getSignature(ReferenceCollection refs, DigitalSignature signature) throws Exception
    {
        return getSignatureCore(refs, signature, true);
    }

    /// <summary>
    /// Gets array of Object XML elements that will be signed.
    /// </summary>
    protected /*override*/ MemoryStream[] getObjects(ReferenceCollection refs, DigitalSignature signature) throws Exception
    {
        int objectsCount = DigitalSignature.getObjectsCount(signature.getXmlDsigLevel());
        MemoryStream[] objects = new MemoryStream[objectsCount];
        objects[0] = getPackageObject(refs, signature);
        objects[1] = getOfficeObject(signature);

        if (signature.getXmlDsigLevel() >= XmlDsigLevel.X_AD_ES_EPES)
            objects[2] = getSignedProperties(signature);

        return objects;
    }

    /// <summary>
    /// Gets PackageObject XML node.
    /// </summary>
    private static MemoryStream getPackageObject(ReferenceCollection refs, DigitalSignature signature) throws Exception
    {
        AnyXmlBuilder builder = new AnyXmlBuilder(new MemoryStream(), new UTF8Encoding(false), false);

        builder.startElement("Object");
        builder.writeAttributeString("Id", "idPackageObject");
        // IN. Actually Word writes this namespace into SignatureTime element below. However, it computes digest
        // when this namespace is written to this PackageObject. Before I changed this code per WORDSNET-10816,
        // we used system SignedXml class, which is writing this namespace to SignatureTime.
        // BUT then we INTENTIONALLY copied it to the PackageObject node! I've checked and seems both approaches
        // are working in Word and signatures are valid in both cases. So, I leave this decision as it already worked for us.
        builder.writeAttributeString("xmlns:mdssi", DocxNamespaces.getNamespace(DocxNamespace.DIGITAL_SIGNATURE, false));

        builder.startElement("Manifest");
        /*DigestAlgorithm*/int digestAlgorithm = CryptoUtilPal.getDigestAlgorithmByPrivateKey(signature.getCertificateHolderInternal());
        for (Reference r : refs)
            r.write(builder, digestAlgorithm);
        builder.endElement("Manifest");

        builder.startElement("SignatureProperties");
        builder.startElement("SignatureProperty");
        builder.writeAttributeString("Id", "idSignatureTime");
        builder.writeAttributeString("Target", "#idPackageSignature");

        builder.startElement("mdssi:SignatureTime");
        builder.writeElement("mdssi:Format", "YYYY-MM-DDThh:mm:ssTZD");
        builder.writeElement("mdssi:Value", FormatterPal.dateTimeToXmlUtc(signature.getSignTimeInternal()));
        builder.endElement("mdssi:SignatureTime");

        builder.endElement("SignatureProperty");
        builder.endElement("SignatureProperties");

        builder.endElement("Object");

        builder.flush();
        return (MemoryStream)builder.getBaseStream();
    }

    /// <summary>
    /// Gets OfficeObject XML node.
    /// </summary>
    private static MemoryStream getOfficeObject(DigitalSignature signature) throws Exception
    {
        AnyXmlBuilder builder = new AnyXmlBuilder(new MemoryStream(), new UTF8Encoding(false), false);

        builder.startElement("Object");
        builder.writeAttributeString("Id", "idOfficeObject");

        builder.startElement("SignatureProperties");
        builder.startElement("SignatureProperty");
        builder.writeAttributeString("Id", "idOfficeV1Details");
        builder.writeAttributeString("Target", "#idPackageSignature");

        builder.startElement("SignatureInfoV1");
        builder.writeAttributeString("xmlns", DocxNamespaces.getNamespace(DocxNamespace.MICROSOFT_DIGITAL_SIGNATURE, false));

        // See MS-OI29500, 3.7.2.5 ST_SignatureType (Signature Type)
        // https://msdn.microsoft.com/en-us/library/ff531759(v=office.12).aspx
        boolean isVisibleSignatureLine = !signature.getSetupId().equals(Guid.Empty);
        if (isVisibleSignatureLine)
            builder.writeElement("SetupID", signature.getSetupId().toString("B").toUpperCase());
        else
            builder.writeEmptyElement("SetupID");

        builder.writeEmptyElement("SignatureText");

        if (isVisibleSignatureLine && ArrayUtil.hasData(signature.getImageBytes()))
            builder.writeElement("SignatureImage", Convert.toBase64String(signature.getImageBytes()));
        else
            builder.writeEmptyElement("SignatureImage");

        builder.writeElement("SignatureComments", signature.getComments());
        builder.writeElement("WindowsVersion", "6.1");
        builder.writeElement("OfficeVersion", "12.0");
        builder.writeElement("ApplicationVersion", "12.0");
        builder.writeElement("Monitors", "1");
        builder.writeElement("HorizontalResolution", "1920");
        builder.writeElement("VerticalResolution", "1200");
        builder.writeElement("ColorDepth", "32");
        builder.writeElement("SignatureProviderId", signature.getProviderId().equals(Guid.Empty)
            ? "{00000000-0000-0000-0000-000000000000}"
            : signature.getProviderId().toString("B").toUpperCase());
        builder.writeEmptyElement("SignatureProviderUrl");
        builder.writeElement("SignatureProviderDetails", "9");
        builder.writeElement(
            "ManifestHashAlgorithm",
            (signature.getXmlDsigLevel() == XmlDsigLevel.XML_D_SIG) ? "http://www.w3.org/2000/09/xmldsig#sha1" : "");
        builder.writeElement("SignatureType", isVisibleSignatureLine ? "2" : "1");

        builder.endElement("SignatureInfoV1");
        builder.endElement("SignatureProperty");
        builder.endElement("SignatureProperties");
        builder.endElement("Object");

        builder.flush();
        return (MemoryStream)builder.getBaseStream();
    }

    /// <summary>
    /// Gets SignedProperties XML node.
    /// </summary>
    private static MemoryStream getSignedProperties(DigitalSignature signature) throws Exception
    {
        AnyXmlBuilder builder = new AnyXmlBuilder(new MemoryStream(), new UTF8Encoding(false), false);

        builder.startElement("Object");

        builder.startElement("xd:QualifyingProperties");
        builder.writeAttributeString("xmlns:xd", "http://uri.etsi.org/01903/v1.3.2#");
        builder.writeAttributeString("Target", "#idPackageSignature");

        builder.startElement("xd:SignedProperties");
        builder.writeAttributeString("Id", "idSignedProperties");

        builder.startElement("xd:SignedSignatureProperties");
        builder.writeElement("xd:SigningTime", FormatterPal.dateTimeToXmlUtc(signature.getSignTimeInternal()));

        builder.startElement("xd:SigningCertificate");
        builder.startElement("xd:Cert");

        builder.startElement("xd:CertDigest");
        builder.startElement("DigestMethod");
        builder.writeAttributeString("Algorithm", "http://www.w3.org/2001/04/xmlenc#sha256");
        builder.endElement("DigestMethod");

        /*DigestAlgorithm*/int digestAlgorithm = CryptoUtilPal.getDigestAlgorithmByPrivateKey(signature.getCertificateHolderInternal());
        byte[] certHash = HashUtil.computeHash(
            CryptoUtilPal.createHashAlgorithm(digestAlgorithm),
            CryptoUtilPal.getEncodedData(signature.getCertificateHolderInternal()));
        builder.writeElement("DigestValue", Convert.toBase64String(certHash));
        builder.endElement("xd:CertDigest");

        builder.startElement("xd:IssuerSerial");
        builder.writeElement("X509IssuerName", CryptoUtilPal.getIssuer(signature.getCertificateHolderInternal()));
        builder.writeElement("X509SerialNumber", CryptoUtilPal.getSerialNumber(signature.getCertificateHolderInternal()));
        builder.endElement("xd:IssuerSerial");

        builder.endElement("xd:Cert");
        builder.endElement("xd:SigningCertificate");

        builder.startElement("xd:SignaturePolicyIdentifier");
        builder.writeEmptyElement("xd:SignaturePolicyImplied ");
        builder.endElement("xd:SignaturePolicyIdentifier");

        builder.endElement("xd:SignedSignatureProperties");

        builder.endElement("xd:SignedProperties");
        builder.endElement("xd:QualifyingProperties");
        builder.endElement("Object");

        builder.flush();
        return (MemoryStream)builder.getBaseStream();
    }
}

