// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/09/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.AnyXmlReader;
import com.aspose.AnyXmlBuilder;
import com.aspose.DigestAlgorithm;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.HashUtil;
import com.aspose.CryptoUtilPal;
import com.aspose.ms.System.Convert;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents the Reference element in XmlDsig.
/// </summary>
class Reference
{
    Reference(String uri)
    {
        mUri = uri;
    }

    Reference(String uri, String contentType)
    {
        mUri = msString.format("{0}?ContentType={1}", uri, contentType);
    }

    /// <summary>
    /// Constructs object from XML reader.
    /// </summary>
    Reference(AnyXmlReader xmlReader)
    {
        mUri = xmlReader.readAttribute("URI", null);

        while (xmlReader.readChild("Reference"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"DigestValue"*/0:
                    setDigestValue(xmlReader.readString());
                    break;
                case /*"DigestMethod"*/1:
                    setDigestMethod(xmlReader.readAttribute("Algorithm", ""));
                    break;
                case /*"Transforms"*/2:
                    getTransformCollection().read(xmlReader);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Writes Reference element which contains uri and digest value for signed part.
    /// </summary>
    void write(AnyXmlBuilder writer, /*DigestAlgorithm*/int digestAlgorithm) throws Exception
    {
        writer.startElement("Reference");

        writer.writeAttributeString("URI", entitize(getUri()));

        getTransformCollection().write(writer);

        writer.startElement("DigestMethod");
        writer.writeAttributeString("Algorithm", DigitalSignatureUtil.getDigestMethod(digestAlgorithm));
        writer.endElement();

        writer.writeElement("DigestValue", computeDigestValue(digestAlgorithm));

        writer.endElement("Reference");
    }

    /// <summary>
    /// Computes a digest value using a specified digest algorithm.
    /// </summary>
    private String computeDigestValue(/*DigestAlgorithm*/int digestAlgorithm) throws Exception
    {
        MemoryStream memoryStream = mReferenceResolver.resolve(this);
        byte[] dataBytes = getTransformCollection().apply(memoryStream).toArray();

        byte[] hash = HashUtil.computeHash(CryptoUtilPal.createHashAlgorithm(digestAlgorithm), dataBytes);
        return Convert.toBase64String(hash);
    }

    /// <summary>
    /// Name of document part combined with MIME type.
    /// Name could be treated as stream name for DOC and part name for DOCX, ODT.
    /// </summary>
    String getUri() { return mUri; }
    void setUri(String value) { mUri = value; }

    /// <summary>
    /// Only name of document part without the MIME type.
    /// Additionally Name is decoded from XmlEncoding to Unicode.
    /// </summary>
    String getName() { return msString.split(XmlEntities.expand(mUri), '?')[0]; }

    /// <summary>
    /// Base64 encoded SHA1 hash of referenced document part.
    /// </summary>
    /// <remarks>
    /// Seems that Word uses only SHA1 hash so type of hash isn't stored.
    /// </remarks>
    String getDigestValue() { return mDigestValue; }
    void setDigestValue(String value) { mDigestValue = value; }

    String getDigestMethod() { return mDigestMethod; }
    void setDigestMethod(String value) { mDigestMethod = value; }

    /// <summary>
    /// Transform collection for referenced document part. Should be applied before hash calculation.
    /// </summary>
    TransformCollection getTransformCollection() { return mTransformCollection; }

    /// <summary>
    /// Indicates whether reference is external.
    /// </summary>
    boolean isExternal() { return !msString.startsWith(mUri, "#", StringComparison.ORDINAL); }

    /// <summary>
    /// Reference verification result.
    /// </summary>
    boolean isValid() { return mIsValid; }
    void isValid(boolean value) { mIsValid = value; }

    /// <summary>
    /// IReferenceResolver object used to resolve this Reference.
    /// </summary>
    IReferenceResolver getReferenceResolver() { return mReferenceResolver; }
    void setReferenceResolver(IReferenceResolver value) { mReferenceResolver = value; }

    /// <summary>
    /// Entities given string. Currently replaces only 0x0001..0x0009 characters. This seem to be enough.
    /// </summary>
    private static String entitize(String uri)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < uri.length(); i++)
            if (('\u0001' <= uri.charAt(i)) && (uri.charAt(i) <= '\u0009'))
                msStringBuilder.appendFormat(sb, "&#{0};", (char)('0' + uri.charAt(i)));
            else
                sb.append(uri.charAt(i));

        return sb.toString();
    }


    private IReferenceResolver mReferenceResolver;
    private /*final*/ TransformCollection mTransformCollection = new TransformCollection();
    private String mUri;
    private String mDigestValue;
    private String mDigestMethod;
    private boolean mIsValid;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"DigestValue",
		"DigestMethod",
		"Transforms"
	);

}

