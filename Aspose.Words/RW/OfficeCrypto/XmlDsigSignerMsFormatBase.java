// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/02/2017 by Dmitry Belov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.AnyXmlBuilder;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Convert;
import com.aspose.CryptoUtilPal;
import com.aspose.CertificateHolderInternal;
import com.aspose.CryptoAlgorithm;
import com.aspose.DsaParameters;
import com.aspose.Rsa;
import com.aspose.DigestAlgorithm;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.AnyXmlReader;
import com.aspose.Debug;


/// <summary>
/// The base class for XmlDsig signature generation for all MS formats (DOC, DOCX and XPS).
/// </summary>
abstract class XmlDsigSignerMsFormatBase extends XmlDsigSignerBase
{
    /// <summary>
    /// Writes Object XML nodes.
    /// </summary>
    protected /*override*/ void writeObjects(AnyXmlBuilder builder, MemoryStream[] objects) throws Exception
    {
        for (MemoryStream objectNode : objects)
        {
            objectNode.setPosition(0);
            builder.writeRaw(objectNode, false);
        }
    }

    /// <summary>
    /// Writes KeyInfo XML element.
    /// </summary>
    protected /*override*/ void writeKeyInfo(AnyXmlBuilder builder, DigitalSignature signature) throws Exception
    {
        builder.startElement("KeyInfo");

        writeKeyValue(builder, signature.getCertificateHolderInternal());

        builder.startElement("X509Data");
        builder.writeElement("X509Certificate", Convert.toBase64String(CryptoUtilPal.getEncodedData(signature.getCertificateHolderInternal())));
        builder.endElement();

        builder.endElement("KeyInfo");
    }

    /// <summary>
    /// Writes KeyValue XML element.
    /// </summary>
    private static void writeKeyValue(AnyXmlBuilder builder, CertificateHolderInternal cert)
    {
        /*CryptoAlgorithm*/byte cryptoAlgorithm = CryptoUtilPal.getCryptoAlgorithmByPublicKey(cert);

        // Seems Word does not write 'KeyValue' element for ECDSA.
        if (cryptoAlgorithm == CryptoAlgorithm.ECDSA)
            return;

        builder.startElement("KeyValue");

        if (cryptoAlgorithm == CryptoAlgorithm.DSA)
            writeDsaKeyValue(builder, cert);
        else
            writeRsaKeyValue(builder, cert);

        builder.endElement("KeyValue");
    }

    private static void writeDsaKeyValue(AnyXmlBuilder builder, CertificateHolderInternal cert)
    {
        DsaParameters parameters = CryptoUtilPal.getDsaParametersFromPublicKey(cert);

        builder.startElement("DSAKeyValue");
        builder.writeElement("P", Convert.toBase64String(parameters.getP()));
        builder.writeElement("Q", Convert.toBase64String(parameters.getQ()));
        builder.writeElement("G", Convert.toBase64String(parameters.getG()));
        builder.writeElement("Y", Convert.toBase64String(parameters.getY()));
        builder.endElement();
    }

    private static void writeRsaKeyValue(AnyXmlBuilder builder, CertificateHolderInternal cert)
    {
        Rsa rsa = CryptoUtilPal.createRsaFromPublicKey(cert);

        builder.startElement("RSAKeyValue");
        builder.writeElement("Modulus", Convert.toBase64String(rsa.getModulus().toByteArray()));
        builder.writeElement("Exponent", Convert.toBase64String(rsa.getExponent().toByteArray()));
        builder.endElement();
    }

    /// <summary>
    /// Writes reference nodes with digest value for specified objects and references.
    /// </summary>
    protected /*override*/ void writeReferences(
        AnyXmlBuilder builder,
        MemoryStream[] objects,
        ReferenceCollection refs,
        /*DigestAlgorithm*/int digestAlgorithm) throws Exception
    {
        for (MemoryStream objectNode : objects)
            writeReference(builder, objectNode, digestAlgorithm);
    }

    /// <summary>
    /// Gets SignatureId.
    /// </summary>
    protected /*override*/ String getSignatureId()
    {
        return "idPackageSignature";
    }


    /// <summary>
    /// Writes Reference XML element.
    /// </summary>
    private static void writeReference(AnyXmlBuilder builder, MemoryStream objectStream, /*DigestAlgorithm*/int digestAlgorithm) throws Exception
    {
        String uri = msString.format("#{0}", getObjectId(objectStream));

        // Check if the specified object stream contains a SignedProperties XML node,
        // then we need to write a reference to this inner node only.
        String signedPropertiesReference = getSignedProperties(objectStream);
        MemoryStream stream = StringUtil.hasChars(signedPropertiesReference)
            ? new MemoryStream(Encoding.getUTF8().getBytes(signedPropertiesReference))
            : objectStream;

        C14Transform c14Transform = new C14Transform();
        c14Transform.propagateNamespace(OfficeCryptoNames.DSIG_REFERENCE_URI);
        String digest = computeBase64(c14Transform.apply(stream), digestAlgorithm);

        String algorithm = DigitalSignatureUtil.getDigestMethod(digestAlgorithm);

        writeReference(builder, digest, uri, algorithm);
    }

    /// <summary>
    /// Returns a string value from an Id element of a specified Object stream.
    /// </summary>
    private static String getObjectId(MemoryStream objectStream) throws Exception
    {
        AnyXmlReader reader = new AnyXmlReader(objectStream);
        Debug.assertTrue("Object".equals(reader.getLocalName()));

        String id = reader.readAttribute("Id", null);
        if (id != null)
            return id;

        while (reader.readChild("Object"))
        {
            id = reader.readAttribute("Id", null);
            if (id != null)
                return id;
        }

        return null;
    }

    /// <summary>
    /// Returns string value with a SignedProperties XML node of a specified object stream.
    /// </summary>
    private static String getSignedProperties(MemoryStream objectStream) throws Exception
    {
        AnyXmlReader reader = new AnyXmlReader(objectStream);
        Debug.assertTrue("Object".equals(reader.getLocalName()));

        while (reader.readChild("Object"))
        {
            if ("SignedProperties".equals(reader.getLocalName()))
                return reader.readOuterXml();
        }

        return null;
    }

    /// <summary>
    /// Writes Reference XML element.
    /// </summary>
    private static void writeReference(AnyXmlBuilder builder, String digest, String uri, String digestMethodAlgorithm)
    {
        builder.startElement("Reference");

        boolean isSignedProperties = ("#idSignedProperties".equals(uri));
        builder.writeAttributeString("Type", (isSignedProperties) ? SIGNED_PROPERTIES_REFERENCE_URI : OBJECT_REFERENCE_URI);

        builder.writeAttributeString("URI", uri);

        if (isSignedProperties)
                writeC14TransformElements(builder);

        builder.startElement("DigestMethod");
        builder.writeAttributeString("Algorithm", digestMethodAlgorithm);
        builder.endElement();

        builder.writeElement("DigestValue", digest);

        builder.endElement("Reference");
    }

    private static final String OBJECT_REFERENCE_URI = "http://www.w3.org/2000/09/xmldsig#Object";
    private static final String SIGNED_PROPERTIES_REFERENCE_URI = "http://uri.etsi.org/01903#SignedProperties";
}

