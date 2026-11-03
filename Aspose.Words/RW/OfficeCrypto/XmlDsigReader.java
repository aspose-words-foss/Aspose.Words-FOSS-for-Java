// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.Stream;
import com.aspose.CertificateHolderInternal;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Convert;
import com.aspose.StringUtil;
import com.aspose.CryptoUtilPal;
import com.aspose.DigestAlgorithm;
import com.aspose.HashUtil;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Globalization.DateTimeStyles;


/// <summary>
/// Utility class for XmlDsig signature verification. See http://www.w3.org/TR/xmldsig-core/.
/// Processes signedXml itself, verifies each reference and fills signatures collection.
/// </summary>
class XmlDsigReader
{
    /// <summary>
    /// Avoid instantiate.
    /// </summary>
    private XmlDsigReader(IWarningCallback warningCallback)
    {
        mWarningCallback = warningCallback;
    }

    /// <summary>
    /// Implements reading of XmlDsig document and verifies all external references.
    /// </summary>
    /// <param name="stream">Stream which contains XmlDsig signature</param>
    /// <param name="resolver">Reference resolver for certain document type.</param>
    /// <param name="signatures">Signature collection where signatures and verification result should be put.</param>
    /// <param name="warningCallback">Optional warning callback.</param>
    static void read(Stream stream, IReferenceResolver resolver, DigitalSignatureCollection signatures, IWarningCallback warningCallback) throws Exception
    {
        XmlDsigReader reader = new XmlDsigReader(warningCallback);
        reader.readCore(stream, null, resolver, signatures);
    }

    /// <summary>
    /// Implements reading of XmlDsig document and verifies all external references.
    /// </summary>
    /// <param name="stream">Stream which contains XmlDsig signature</param>
    /// <param name="certificate">Certificate used to sign document</param>
    /// <param name="resolver">Reference resolver for certain document type.</param>
    /// <param name="signatures">Signature collection where signatures and verification result should be put.</param>
    /// <param name="warningCallback">Optional warning callback.</param>
    static void read(Stream stream, CertificateHolderInternal certificate, IReferenceResolver resolver, DigitalSignatureCollection signatures, IWarningCallback warningCallback) throws Exception
    {
        XmlDsigReader reader = new XmlDsigReader(warningCallback);
        reader.readCore(stream, certificate, resolver, signatures);
    }

    private void readCore(Stream stream, CertificateHolderInternal certificate, IReferenceResolver resolver, DigitalSignatureCollection signatures) throws Exception
    {
        // If no XmlDsig stream provided return.
        if (stream == null)
            return;

        AnyXmlReader xmlReader = new AnyXmlReader(stream);

        if ("Signature".equals(xmlReader.getLocalName()))
        {
            // There is only one <Signature> element.
            signatures.add(readAndVerifySignature(stream, certificate, resolver));
        }
        else
        {
            // ODT document holds signatures in one document so we should process them one by one.
            // Extract each <Signature> element.
            boolean isReadChild = xmlReader.readChild("document-signatures");
            while (isReadChild && // Infinite loop without it if <document-signatures .../>.
                   !xmlReader.isEndElement("document-signatures"))
            {
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"Signature"*/0:
                        String signatureXml = xmlReader.readOuterXml();
                        MemoryStream signatureStream = new MemoryStream(Encoding.getUTF8().getBytes(signatureXml));
                        signatures.add(readAndVerifySignature(signatureStream, certificate, resolver));
                        break;
                    default:
                        xmlReader.ignoreElement();
                        break;
                }
            }
        }
    }

    /// <summary>
    /// Reads 'Signature' element. Returns verified DigitalSignature object.
    /// </summary>
    private DigitalSignature readAndVerifySignature(Stream stream, CertificateHolderInternal certificate, IReferenceResolver resolver) throws Exception
    {
        DigitalSignature signature = new DigitalSignature(DigitalSignatureType.XML_DSIG);
        ReferenceCollection referenceCollection = new ReferenceCollection(resolver);

        byte[] certificateBytes = null;

        String signedInfoRaw = getSignedInfoRaw(stream);
        String signatureMethod = "";

        AnyXmlReader xmlReader = new AnyXmlReader(stream);
        while (xmlReader.readChild("Signature"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"SignatureMethod"*/1:
                    signatureMethod = xmlReader.readAttribute("Algorithm", "");
                    break;

                case /*"Reference"*/2:
                {
                    Reference reference = new Reference(xmlReader);
                    referenceCollection.add(reference);

                    // WORDSNET-27192 Added ability to specify XML-DSig signature level.
                    if ("#idSignedProperties".equals(reference.getUri()))
                        signature.setXmlDsigLevel(XmlDsigLevel.X_AD_ES_EPES);

                    break;
                }

                case /*"X509Certificate"*/3:
                    certificateBytes = Convert.fromBase64String(xmlReader.readString());
                    break;

                case /*"SignatureValue"*/4:
                    signature.setSignatureValue(Convert.fromBase64String(xmlReader.readString()));
                    break;

                case /*"Object"*/5:
                    String id = xmlReader.readAttribute("Id", "");
                    if("idValidSigLnImg".equals(id))
                        signature.setImageBytesValid(Convert.fromBase64String(xmlReader.readString()));
                    else if("idInvalidSigLnImg".equals(id))
                        signature.setImageBytesInvalid(Convert.fromBase64String(xmlReader.readString()));
                    break;

                case /*"SignatureProperty"*/6:
                    readSignatureProperty(xmlReader, signature);
                    break;

                // WORDSNET-18890 When algorithm is ECDSA, Word stores signing time in this element.
                case /*"SigningTime"*/7:
                    signature.setSignTime(parseSignTime(xmlReader.readString()));
                    break;

                default:
                    // Do not ignore element, go deeper.
                    break;
            }
        }

        if (StringUtil.hasChars(signedInfoRaw))
        {
            certificate = (certificate == null)
                    ? CryptoUtilPal.createHolder(certificateBytes)
                    : certificate;

            signature.setCertificate(certificate);

            // Verify SignedInfo element.
            boolean totalResult = verifySignedInfo(signedInfoRaw, signatureMethod, certificate, signature.getSignatureValue());

            // Verification process has two stages:
            //  1) Verify signed XML itself i.e SignedInfo element is unchanged.
            //  2) Verify references i.e content of referenced files is unchanged.
            // If signed XML is not valid it means that signature is not valid and further processing is actually not needed
            // but lets continue to get each reference verification result.

            // Verify all references.
            for (Reference r : referenceCollection)
                totalResult &= (r.isExternal())
                    ? verifyExternalReference(r, resolver)
                    : verifyInternalReference(r, stream);

            signature.setIsValid(totalResult);

            // Issue warning for invalid signature.
            if (!signature.isValid())
                warn(WarningType.UNEXPECTED_CONTENT, WarningSource.UNKNOWN, WarningStrings.INVALID_DIGITAL_SIGNATURE);
        }

        return signature;
    }

    /// <summary>
    /// Extracts 'SignedInfo' element as raw string.
    /// </summary>
    private static String getSignedInfoRaw(Stream stream) throws Exception
    {
        AnyXmlReader xmlReader = new AnyXmlReader(stream);

        // SignedInfo MUST be first child of Signature so don't loop.
        xmlReader.readChild("Signature");
        return ("SignedInfo".equals(xmlReader.getLocalName()))
            ? xmlReader.readOuterXml()
            : null;
    }

    /// <summary>
    /// Verifies 'SignedInfo' element using given certificate and signature hash.
    /// </summary>
    private boolean verifySignedInfo(String signedInfoRaw, String signatureMethod, CertificateHolderInternal certificate, byte[] signature) throws Exception
    {
        byte[] signedInfoBytes = canonicalize(signedInfoRaw);

        /*DigestAlgorithm*/int digestAlgorithm = getDigestAlgorithm(signatureMethod);
        if (digestAlgorithm == DigestAlgorithm.UNKNOWN)
            return false;

        return CryptoUtilPal.verifySignature(certificate, digestAlgorithm, signedInfoBytes, signature);
    }

    /// <summary>
    /// Verifies hash using given algorithm.
    /// </summary>
    private boolean verifyHash(byte[] data, String algorithmId, byte[] hash)
    {
        /*DigestAlgorithm*/int digestAlgorithm = getDigestAlgorithm(algorithmId);
        if (digestAlgorithm == DigestAlgorithm.UNKNOWN)
            return false;

        byte[] computedHash = HashUtil.computeHash(CryptoUtilPal.createHashAlgorithm(digestAlgorithm), data);

        return ArrayUtil.compareBytes(hash, computedHash, hash.length);
    }

    /// <summary>
    /// Verifies external reference.
    /// </summary>
    private boolean verifyExternalReference(Reference r, IReferenceResolver resolver) throws Exception
    {
        MemoryStream stream = r.getTransformCollection().apply(resolver.resolve(r));
        r.isValid(verifyHash(stream.toArray(), r.getDigestMethod(), Convert.fromBase64String(r.getDigestValue())));

        return r.isValid();
    }

    /// <summary>
    /// Verifies internal reference.
    /// </summary>
    private boolean verifyInternalReference(Reference r, Stream xml) throws Exception
    {
        String elementRaw = getElementRaw(xml, r.getUri());

        // If element is not found reference is treated not valid.
        r.isValid(StringUtil.hasChars(elementRaw)
            && verifyHash(canonicalize(elementRaw), r.getDigestMethod(), Convert.fromBase64String(r.getDigestValue())));

        return r.isValid();
    }

    /// <summary>
    /// Canonicalize given XML. Returns byte array.
    /// </summary>
    private static byte[] canonicalize(String xml) throws Exception
    {
        MemoryStream stream = new MemoryStream(Encoding.getUTF8().getBytes(xml));

        C14Transform c14Transform = new C14Transform();
        c14Transform.propagateNamespace(OfficeCryptoNames.DSIG_REFERENCE_URI);

        return c14Transform.apply(stream).toArray();
    }

    /// <summary>
    /// Returns digest corresponding to given algorithm.
    /// Null, if algorithm is not supported.
    /// </summary>
    private /*DigestAlgorithm*/int getDigestAlgorithm(String algorithmId)
    {
        switch (gStringSwitchMap.of(algorithmId))
        {
            case /*"http://www.w3.org/2001/04/xmldsig-more#rsa-sha512"*/8:
            case /*"http://www.w3.org/2001/04/xmlenc#sha512"*/9:
                return DigestAlgorithm.SHA_512;

            case /*"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"*/10:
            case /*"http://www.w3.org/2001/04/xmlenc#sha256"*/11:
            case /*"http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256"*/12:
                return DigestAlgorithm.SHA_256;

            case /*"http://www.w3.org/2000/09/xmldsig#dsa-sha1"*/13:
            case /*"http://www.w3.org/2000/09/xmldsig#rsa-sha1"*/14:
            case /*"http://www.w3.org/2000/09/xmldsig#sha1"*/15:
                return DigestAlgorithm.SHA_1;

            default:
                // WORDSNET-12579
                // Now we don't support all algorithms for digest and certificate (etc ECGost3410), but we need to read file without exception.
                // As completed solution We need to support all algorithms and throw exception for unsupported algorithms.
                warn(WarningType.UNEXPECTED_CONTENT, WarningSource.UNKNOWN, msString.format(WarningStrings.NOT_SUPPORTED_ALGORITHM, algorithmId));
                return DigestAlgorithm.UNKNOWN;
        }
    }

    /// <summary>
    /// Extracts XML element with given 'Id' attribute as raw string.
    /// </summary>
    /// <returns>Element as raw string or null if missing.</returns>
    private static String getElementRaw(Stream xmlStream, String elementId) throws Exception
    {
        String stripId = elementId.substring(1);

        AnyXmlReader xmlReader = new AnyXmlReader(xmlStream);

        String tagName = xmlReader.getLocalName();
        while(xmlReader.readChild(tagName))
        {
            String id = xmlReader.readAttribute("Id", "");

            if (!StringUtil.hasChars(id) || (!msString.equals(id, stripId)))
                continue;

            // Element is found, return raw string.
            return xmlReader.readOuterXml();
        }

        return null;
    }

    private void readSignatureProperty(AnyXmlReader xmlReader, DigitalSignature signature)
    {
        while (xmlReader.readChild("SignatureProperty"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"SignatureTime"*/16:
                    // OOXML stores signTime in this attribute.
                    readSignatureTime(xmlReader, signature);
                    break;
                case /*"SignatureInfoV1"*/17:
                    readSignatureInfoV1(xmlReader, signature);
                    break;
                case /*"date"*/18:
                    // ODT stores signTime in this element.
                    signature.setSignTime(parseSignTime(xmlReader.readString()));
                    break;
                default:
                    warnAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Process idSignatureTime property element and extract it's value.
    /// </summary>
    private void readSignatureTime(AnyXmlReader xmlReader, DigitalSignature signature)
    {
        // <SignatureProperty Id="idSignatureTime" Target="#idPackageSignature">
        //   <mdssi:SignatureTime>
        //     <mdssi:Format>YYYY-MM-DDThh:mm:ssTZD</mdssi:Format>
        //     <mdssi:Value>2010-08-30T04:50:01Z</mdssi:Value>
        //   </mdssi:SignatureTime>
        // </SignatureProperty>
        while (xmlReader.readChild("SignatureTime"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"Value"*/19:
                    signature.setSignTime(parseSignTime(xmlReader.readString()));
                    break;
                default:
                    warnAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    private static void readSignatureInfoV1(AnyXmlReader xmlReader, DigitalSignature signature)
    {
        while (xmlReader.readChild("SignatureInfoV1"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"SignatureComments"*/20:
                    signature.setComments(xmlReader.readString());
                    break;
                case /*"SetupID"*/21:
                    String id = xmlReader.readString();
                    signature.setSetupId(StringUtil.hasChars(id) ? new Guid(id) : Guid.Empty);
                    break;
                case /*"SignatureText"*/22:
                    signature.setText(xmlReader.readString());
                    break;
                case /*"SignatureType"*/23:
                    signature.setVisible(("2".equals(xmlReader.readString())));
                    break;
                case /*"SignatureImage"*/24:
                    String image = xmlReader.readString();
                    signature.setImageBytes(StringUtil.hasChars(image) ? Convert.fromBase64String(image) : null);
                    break;
                case /*"SignatureProviderId"*/25:
                    String providerId = xmlReader.readString();
                    signature.setProviderId(StringUtil.hasChars(providerId) ? new Guid(providerId) : Guid.Empty);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static DateTime parseSignTime(String value)
    {
        // AM. It seems I should use Format element to get datetime format but format specifiers are not always the same as for ParseExact().
        return DateTime.parseExact(value, G_INPUT_DATE_TIME_FORMATS, msCultureInfo.getInvariantCulture(), DateTimeStyles.ADJUST_TO_UNIVERSAL);
    }

    private void warn(/*WarningType*/int type, /*WarningSource*/int source, String description)
    {
        if (mWarningCallback != null)
            mWarningCallback.warning(new WarningInfo(type, source, description));
    }

    /// <summary>
    /// Writes warning for ignored element and then ignores element.
    /// </summary>
    private void warnAndIgnoreElement(AnyXmlReader xmlReader)
    {
        // Create warning message.
        String message = msString.format("Import of element '{0}' is not supported by Aspose.Words.", xmlReader.getLocalName());
        warn(WarningType.UNEXPECTED_CONTENT, WarningSource.UNKNOWN, message);

        xmlReader.ignoreElement();
    }

    private /*final*/ IWarningCallback mWarningCallback;

    private static /*final*/ String[] G_INPUT_DATE_TIME_FORMATS =
        {
            "yyyy-MM-ddTHH:mm:ss,FF",
            "yyyy-MM-ddTHH:mm:ssZ",
            "yyyy-MM-ddTHH:mm:sszzz",
            "yyyy-MM-ddTHH:mm:ss.fzzz",
            "yyyy-MM-ddTHH:mm:ss.fffzzz"
        };

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"Signature",
		"SignatureMethod",
		"Reference",
		"X509Certificate",
		"SignatureValue",
		"Object",
		"SignatureProperty",
		"SigningTime",
		"http://www.w3.org/2001/04/xmldsig-more#rsa-sha512",
		"http://www.w3.org/2001/04/xmlenc#sha512",
		"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256",
		"http://www.w3.org/2001/04/xmlenc#sha256",
		"http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256",
		"http://www.w3.org/2000/09/xmldsig#dsa-sha1",
		"http://www.w3.org/2000/09/xmldsig#rsa-sha1",
		"http://www.w3.org/2000/09/xmldsig#sha1",
		"SignatureTime",
		"SignatureInfoV1",
		"date",
		"Value",
		"SignatureComments",
		"SetupID",
		"SignatureText",
		"SignatureType",
		"SignatureImage",
		"SignatureProviderId"
	);

}

