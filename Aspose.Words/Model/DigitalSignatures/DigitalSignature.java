// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/09/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.CertificateHolderInternal;
import com.aspose.ms.System.Guid;
import org.bouncycastle.crypto.AsymmetricKey;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import org.bouncycastle.cert.X509CertificateHolder;
import com.aspose.CryptoUtilPal;
import com.aspose.CryptoAlgorithm;


/// <summary>
/// Represents a digital signature on a document and the result of its verification.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-digital-signatures/">Work with Digital Signatures</a> documentation article.</para>
/// </summary>
public class DigitalSignature
{
    /// <summary>
    /// Creates DigitalSignature object which can be used to sign document. Certificate must have private keys.
    /// </summary>
    DigitalSignature(CertificateHolder holder)
    {
    	this(holder.toInternal());
	
        mCertificateHolder = holder;
        mSignTime = DateTime.getNow();
    }

    DigitalSignature(/*DigitalSignatureType*/int sigType)
    {
    	this(CertificateHolderInternal.create());
	
        mSignatureType = sigType;
        mSignTime = DateTime.MinValue;
        mComments = "";
    }

    private DigitalSignature(CertificateHolderInternal holder)
    {
        mCertificateHolderInternal = holder;
        mCertificateHolder = new CertificateHolder(mCertificateHolderInternal);
        setXmlDsigLevel();
    }

    /// <summary>
    /// Gets the type of the digital signature.
    /// </summary>
    public /*DigitalSignatureType*/int getSignatureType() { return mSignatureType; }

    /// <summary>
    /// Gets the time the document was signed.
    /// </summary>
    //JAVA: property accessor is internalized
	DateTime getSignTimeInternal() { return mSignTime; }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getSignTime() { return DateTime.toJava(getSignTimeInternal()); }
    /// <summary>
    /// Internal setter for public property <see cref="SignTime" />.
    /// </summary>
    void setSignTime(DateTime signTime)
    {
        mSignTime = signTime;
    }

    /// <summary>
    /// Gets the signing purpose comment.
    /// </summary>
    public String getComments() { return mComments; }
    /// <summary>
    /// Internal setter for public property <see cref="Comments" />.
    /// </summary>
    void setComments(String comments)
    {
        mComments = comments;
    }

    /// <summary>
    /// Returns the subject distinguished name of the certificate that was used to sign the document.
    /// </summary>
    public String getSubjectName() { return mCertificateHolder.getCertificateInternal().getSubject(); }

    /// <summary>
    /// Returns the subject distinguished name of the certificate isuuer.
    /// </summary>
    public String getIssuerName() { return mCertificateHolder.getCertificateInternal().getIssuer(); }

    /// <summary>
    /// Returns <c>true</c> if this digital signature is valid and the document has not been tampered with.
    /// </summary>
    public boolean isValid() { return mIsValid; }

    /// <summary>
    /// Internal setter for public property <see cref="IsValid" />.
    /// </summary>
    void setIsValid(boolean result)
    {
        mIsValid = result;
    }

    /// <summary>
    /// Returns the certificate holder object that contains the certificate was used to sign the document.
    /// </summary>
    public CertificateHolder getCertificateHolder() { return mCertificateHolder; }

    /// <summary>
    /// Gets an array of bytes representing a signature value.
    /// </summary>
    public byte[] getSignatureValue() { return mSignatureValue; }; void setSignatureValue(byte[] value) { mSignatureValue = value; };

    private byte[] mSignatureValue;

    /// <summary>
    /// Internal setter for public property <see cref="Words.DigitalSignatures.CertificateHolder.Certificate" />.
    /// </summary>
    void setCertificate(CertificateHolderInternal certificate)
    {
        mCertificateHolderInternal.setCertificateBc(certificate.getCertificateBc());
        setXmlDsigLevel();
    }

    /// <summary>
    /// Returns the certificate holder object that contains the certificate was used to sign the document.
    /// For internal use only.
    /// </summary>
    CertificateHolderInternal getCertificateHolderInternal() { return mCertificateHolderInternal; }

    /// <summary>
    /// Specifies an image for the digital signature.
    /// </summary>
    /// <remarks>
    /// This is image that user selected to sign document.
    /// </remarks>
    byte[] getImageBytes() { return mImage; }
    void setImageBytes(byte[] value) { mImage = value; }

    /// <summary>
    /// Specifies the image of a valid signature.
    /// </summary>
    /// <remarks>
    /// This is rendered image which consist of signature placeholder with
    /// signature image <see cref="DigitalSignature.ImageBytes" /> placed over.
    /// </remarks>
    byte[] getImageBytesValid() { return mImageValid; }
    void setImageBytesValid(byte[] value) { mImageValid = value; }

    /// <summary>
    /// Specifies the image of an invalid signature.
    /// </summary>
    /// <remarks>
    /// This is the same as valid image <see cref="DigitalSignature.ImageBytesValid" /> but red inscription
    /// "Invalid signature" over it.
    /// </remarks>
    byte[] getImageBytesInvalid() { return mImageInvalid; }
    void setImageBytesInvalid(byte[] value) { mImageInvalid = value; }

    /// <summary>
    /// Indicates that the digital signature MUST be printed.
    /// </summary>
    boolean getVisible() { return mVisible; }
    void setVisible(boolean value) { mVisible = value; }

    /// <summary>
    /// Specifies the text of actual signature in the digital signature.
    /// </summary>
    String getText() { return mText; }
    void setText(String value) { mText = value; }

    /// <summary>
    /// Specifies a GUID that can be cross-referenced with the identifier of the signature line
    /// stored in the document content.
    /// </summary>
    Guid getSetupId() { return mSetupId; }
    void setSetupId(Guid value) { mSetupId = value; }

    /// <summary>
    /// Specifies the class identifier of the signature provider.
    /// </summary>
    /// <remarks>
    /// Office 2010 and the 2007 Office system reserve the value of {00000000-0000-0000-0000-000000000000} for
    /// its default signature provider and {000CD6A4-0000-0000-C000-000000000046} for its East Asian signature provider.
    /// </remarks>
    Guid getProviderId() { return mProviderId; }
    void setProviderId(Guid value) { mProviderId = value; }

    
    AsymmetricKey getPrivateKey() { return mCertificateHolderInternal.getPrivateKeyBc(); }
    
    void setPrivateKey(AsymmetricKey value) { mCertificateHolderInternal.setPrivateKeyBc(value); }

    /// <summary>
    /// Specifies the level of a digital signature based on XML-DSig standard.
    /// </summary>
    /*XmlDsigLevel*/int getXmlDsigLevel() { return mXmlDsigLevel; }; void setXmlDsigLevel(/*XmlDsigLevel*/int value) { mXmlDsigLevel = value; };

    private /*XmlDsigLevel*/int mXmlDsigLevel;

    /// <summary>
    /// Utility method extracts name from distinguished name.
    /// Used in testing only.
    /// </summary>
    static String extractCn(String cn)
    {
        int idx1 = msString.indexOf(cn, "CN=", StringComparison.ORDINAL);

        if (idx1 == -1)
            return cn;

        int idx2 = msString.indexOf(cn, ",", idx1, StringComparison.ORDINAL);
        if (idx2 == -1)
            idx2 = cn.length();
        return cn.substring((idx1 + 3), (idx1 + 3) + (idx2 - idx1 - 3));
    }

    /// <summary>
    /// Returns a user-friendly string that displays the value of this object.
    /// </summary>
    public /*override*/ String toString()
    {
        X509CertificateHolder certificate = mCertificateHolderInternal.getCertificateBc();
        return msString.format("Signature [{0}, {1}, {2}/{3}, {4}/{5}]",
            mSignatureType,
            mIsValid ? "Valid" : "Not valid",
            (certificate != null) ? extractCn(certificate.toASN1Structure().getSubject().toString()) : "?",
            (certificate != null) ? extractCn(certificate.toASN1Structure().getIssuer().toString()) : "?",
            !DateTime.equals(mSignTime, DateTime.MinValue) ? mSignTime.toString() : "?",
            (certificate != null) ? certificate.getNotAfter().toString() : "?");
    }

    /// <summary>
    /// Specifies the number of Objects in XML-DSig signature level.
    /// </summary>
    /// <remarks>
    /// XML-DSig signature level has only 2 Objects. The next signature level XAdES-EPES
    /// will have additional object with reference URI="#idSignedProperties".
    /// </remarks>
    static int getObjectsCount(/*XmlDsigLevel*/int xmlDsigLevel)
    {
        return xmlDsigLevel == XmlDsigLevel.XML_D_SIG ? 2 : 3;
    }

    // WORDSNET-18890 For ECDSA Word writes one additional PackageSignature Object.
    private void setXmlDsigLevel()
    {
        if (CryptoUtilPal.getCryptoAlgorithmByPrivateKey(mCertificateHolderInternal) == CryptoAlgorithm.ECDSA)
            setXmlDsigLevel(XmlDsigLevel.X_AD_ES_EPES);
    }

    private /*final*/ /*DigitalSignatureType*/int mSignatureType;
    private /*final*/ CertificateHolder mCertificateHolder;
    private /*final*/ CertificateHolderInternal mCertificateHolderInternal;
    private DateTime mSignTime;
    private String mComments;
    private boolean mIsValid;
    private byte[] mImage;
    private byte[] mImageValid;
    private byte[] mImageInvalid;
    private String mText;
    private Guid mSetupId = Guid.Empty;
    private Guid mProviderId = Guid.Empty;
    private boolean mVisible;
}

