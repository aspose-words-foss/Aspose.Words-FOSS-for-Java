// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/09/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.CryptoAlgorithm;
import com.aspose.CryptoUtilPal;
import com.aspose.DigestAlgorithm;
import com.aspose.AnyXmlBuilder;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.HashUtil;
import com.aspose.ms.System.Convert;


/// <summary>
/// Base class for XmlDsig signers.
/// </summary>
abstract class XmlDsigSignerBase
{
    abstract MemoryStream getSignature(ReferenceCollection refs, DigitalSignature signature) throws Exception;

    /// <summary>
    /// Base method to write signatures.
    /// </summary>
    protected MemoryStream getSignatureCore(ReferenceCollection refs, DigitalSignature signature, boolean xmlKeyInfo) throws Exception
    {
        // Get Object nodes that will be signed.
        MemoryStream[] objects = getObjects(refs, signature);

        /*CryptoAlgorithm*/byte cryptoAlgorithm = CryptoUtilPal.getCryptoAlgorithmByPrivateKey(signature.getCertificateHolderInternal());
        /*DigestAlgorithm*/int digestAlgorithm = CryptoUtilPal.getDigestAlgorithmByPrivateKey(signature.getCertificateHolderInternal());

        // Get SignedInfo node with signatures for Object nodes.
        MemoryStream signedInfo = getSignedInfo(objects, refs, cryptoAlgorithm, digestAlgorithm);

        // Make initial XML file that will be signed.
        AnyXmlBuilder builder = new AnyXmlBuilder(new MemoryStream(), new UTF8Encoding(false), false);

        // Start document to write XML declaration.
        builder.startDocument("Signature");
        builder.writeAttributeString("xmlns", OfficeCryptoNames.DSIG_REFERENCE_URI);
        builder.writeAttributeString("Id", getSignatureId());

        writeSignedInfo(builder, signedInfo);
        writeSignatureValue(builder, signedInfo, signature);
        if (xmlKeyInfo)
            writeKeyInfo(builder, signature);
        writeObjects(builder, objects);

        builder.fullEndElement("Signature");

        builder.flush();
        return (MemoryStream)builder.getBaseStream();
    }

    /// <summary>
    /// Returns Base64 string of a hash calculated for a specified stream and crypto algorithm.
    /// </summary>
    protected static String computeBase64(MemoryStream stream, /*DigestAlgorithm*/int digestAlgorithm) throws Exception
    {
        byte[] hash = HashUtil.computeHash(CryptoUtilPal.createHashAlgorithm(digestAlgorithm), stream.toArray());
        return Convert.toBase64String(hash);
    }

    /// <summary>
    /// Writes C14Transform elements.
    /// </summary>
    protected static void writeC14TransformElements(AnyXmlBuilder builder)
    {
        builder.startElement("Transforms");

        C14Transform c14Transform = new C14Transform();
        c14Transform.write(builder);

        builder.endElement("Transforms");
    }

    /// <summary>
    /// Gets array of Object XML elements that will be signed.
    /// </summary>
    protected abstract MemoryStream[] getObjects(ReferenceCollection refs, DigitalSignature signature) throws Exception;

    /// <summary>
    /// Writes reference nodes with digest value for specified objects and references.
    /// </summary>
    protected abstract void writeReferences(
        AnyXmlBuilder builder,
        MemoryStream[] objects,
        ReferenceCollection refs,
        /*DigestAlgorithm*/int digestAlgorithm) throws Exception;

    /// <summary>
    /// Writes KeyInfo XML element.
    /// </summary>
    protected abstract void writeKeyInfo(AnyXmlBuilder builder, DigitalSignature signature) throws Exception;

    /// <summary>
    /// Writes array of Object XML elements.
    /// </summary>
    protected abstract void writeObjects(AnyXmlBuilder builder, MemoryStream[] objects) throws Exception;

    /// <summary>
    /// Returns signature id.
    /// </summary>
    protected abstract String getSignatureId();

    /// <summary>
    /// Gets SignedInfo XML element.
    /// </summary>
    private MemoryStream getSignedInfo(
        MemoryStream[] objects,
        ReferenceCollection refs,
        /*CryptoAlgorithm*/byte cryptoAlgorithm,
        /*DigestAlgorithm*/int digestAlgorithm) throws Exception
    {
        AnyXmlBuilder builder = new AnyXmlBuilder(new MemoryStream(), new UTF8Encoding(false), false);

        builder.startElement("SignedInfo");

        builder.startElement("CanonicalizationMethod");
        builder.writeAttributeString("Algorithm", C14Transform.ALGORITHM);
        builder.endElement();

        builder.startElement("SignatureMethod");
        builder.writeAttributeString("Algorithm", getAlgorithmUri(cryptoAlgorithm, digestAlgorithm));
        builder.endElement();

        writeReferences(builder, objects, refs, digestAlgorithm);

        builder.endElement("SignedInfo");

        builder.flush();
        return (MemoryStream)builder.getBaseStream();
    }

    /// <summary>
    /// Returns algorithm URI string by a specified crypto and digest algorithms.
    /// </summary>
    private static String getAlgorithmUri(/*CryptoAlgorithm*/byte algorithm, /*DigestAlgorithm*/int digestAlgorithm)
    {
        switch (algorithm)
        {
            case CryptoAlgorithm.RSA:
                return getRsaUri(digestAlgorithm);

            case CryptoAlgorithm.DSA:
                return "http://www.w3.org/2000/09/xmldsig#dsa-sha1";

            case CryptoAlgorithm.ECDSA:
                return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";

            default:
                throw new IllegalStateException("Unexpected CryptoAlgorithm value.");
        }
    }

    /// <summary>
    /// Returns RSA URI string by a specified digest algorithm.
    /// </summary>
    /// <remarks>
    /// See https://learn.microsoft.com/en-us/windows/win32/seccrypto/xml-digital-signature-cryptographic-algorithms
    /// </remarks>
    private static String getRsaUri(/*DigestAlgorithm*/int digestAlgorithm)
    {
        switch (digestAlgorithm)
        {
            case DigestAlgorithm.SHA_256:
                return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";

            case DigestAlgorithm.SHA_384:
                return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";

            case DigestAlgorithm.SHA_512:
                return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";

            default:
                return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
        }
    }

    /// <summary>
    /// Writes SignedInfo XML element.
    /// </summary>
    private static void writeSignedInfo(AnyXmlBuilder builder, MemoryStream signedInfo) throws Exception
    {
        signedInfo.setPosition(0);
        builder.writeRaw(signedInfo, false);
    }

    /// <summary>
    /// Writes SignatureValue XML element.
    /// </summary>
    private static void writeSignatureValue(AnyXmlBuilder builder, MemoryStream signedInfo, DigitalSignature signature) throws Exception
    {
        builder.startElement("SignatureValue");
        builder.writeRaw(computeSignatureValue(signedInfo, signature));
        builder.endElement();
    }

    /// <summary>
    /// Returns signature value for the specified SignedInfo XML element.
    /// </summary>
    private static String computeSignatureValue(MemoryStream signedInfo, DigitalSignature signature) throws Exception
    {
        /*DigestAlgorithm*/int digestAlgorithm = CryptoUtilPal.getDigestAlgorithmByPrivateKey(signature.getCertificateHolderInternal());

        C14Transform c14Transform = new C14Transform();
        c14Transform.propagateNamespace(OfficeCryptoNames.DSIG_REFERENCE_URI);
        byte[] signedInfoBytes = c14Transform.apply(signedInfo).toArray();
        byte[] signatureBytes = CryptoUtilPal.generateSignature(signature.getCertificateHolderInternal(), signedInfoBytes, digestAlgorithm);

        return Convert.toBase64String(signatureBytes);
    }
}

