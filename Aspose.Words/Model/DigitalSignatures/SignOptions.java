// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/07/2017 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Guid;


/// <summary>
/// Allows to specify options for document signing.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-digital-signatures/">Work with Digital Signatures</a> documentation article.</para>
/// </summary>
public class SignOptions
{
    /// <summary>
    /// Applies all properties to the <paramref name="signature"/>.
    /// </summary>
    void applyTo(DigitalSignature signature)
    {
        signature.setComments(getComments());
        signature.setSignTime(getSignTimeInternal());
        signature.setSetupId(getSignatureLineIdInternal());
        signature.setImageBytes(getSignatureLineImage());
        signature.setProviderId(getProviderIdInternal());
        signature.setXmlDsigLevel(getXmlDsigLevel());
    }

    /// <summary>
    /// Specifies comments on the digital signature.
    /// Default value is <b>empty string</b><ms>(<see cref="string.Empty"/>)</ms>.
    /// </summary>
    public String getComments() { return mComments; }
    public void setComments(String value) { mComments = value; }

    /// <summary>
    /// The date of signing.
    /// Default value is <b>current time</b><ms> (<see cref="DateTime.Now"/>)</ms><cpp> (<see cref="DateTime.Now"/>)</cpp>
    /// </summary>
    //JAVA: property accessor is internalized
	DateTime getSignTimeInternal() { return mSignTime; }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getSignTime() { return DateTime.toJava(getSignTimeInternal()); }
    //JAVA: property accessor is internalized
	void setSignTimeInternal(DateTime value) { mSignTime = value; }
	//JAVA-added public wrapper for internalized property accessor
	public void setSignTime(java.util.Date value) { setSignTimeInternal(DateTime.fromJava(value)); }

    /// <summary>
    /// Signature line identifier.
    /// Default value is <b>Empty (all zeroes) Guid</b>.
    /// </summary>
    /// <remarks>
    /// When set, it associates <see cref="SignatureLine"/> with corresponding <see cref="DigitalSignature"/>.
    /// </remarks>
    //JAVA: property accessor is internalized
	Guid getSignatureLineIdInternal() { return mSignatureLineId; }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.UUID getSignatureLineId() { return Guid.toJava(getSignatureLineIdInternal()); }
    //JAVA: property accessor is internalized
	void setSignatureLineIdInternal(Guid value) { mSignatureLineId = value; }
	//JAVA-added public wrapper for internalized property accessor
	public void setSignatureLineId(java.util.UUID value) { setSignatureLineIdInternal(Guid.fromJava(value)); }

    /// <summary>
    /// The image that will be shown in associated <see cref="SignatureLine"/>.
    /// Default value is <c>null</c>.
    /// </summary>
    
    public byte[] getSignatureLineImage() { return mSignatureLineImage; }
    
    public void setSignatureLineImage(byte[] value) { mSignatureLineImage = value; }

    /// <summary>
    /// The password to decrypt source document.
    /// Default value is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    /// <remarks>
    /// If OOXML document is encrypted, you should provide decryption password
    /// to decrypt source document before it will be signed.
    /// This is not required for documents in binary DOC format.
    /// </remarks>
    public String getDecryptionPassword() { return mDecryptionPassword; }
    public void setDecryptionPassword(String value) { mDecryptionPassword = value; }

    /// <summary>
    /// Specifies the class ID of the signature provider.
    /// Default value is <b>Empty (all zeroes) Guid</b>.
    /// </summary>
    /// <remarks>
    /// <para>The cryptographic service provider (CSP) is an independent software module that actually performs
    /// cryptography algorithms for authentication, encoding, and encryption. MS Office reserves the value
    /// of {00000000-0000-0000-0000-000000000000} for its default signature provider.</para>
    /// <para>The GUID of the additionally installed provider should be obtained from the documentation shipped with the provider.</para>
    /// <para>In addition, all the installed cryptographic providers are enumerated in windows registry.
    /// It can be found in the following path: HKLM\SOFTWARE\Microsoft\Cryptography\Defaults\Provider.
    /// There is a key name "CP Service UUID" which corresponds to a GUID of signature provider.</para>
    /// </remarks>
    //JAVA: property accessor is internalized
	Guid getProviderIdInternal() { return mProviderId; }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.UUID getProviderId() { return Guid.toJava(getProviderIdInternal()); }
    //JAVA: property accessor is internalized
	void setProviderIdInternal(Guid value) { mProviderId = value; }
	//JAVA-added public wrapper for internalized property accessor
	public void setProviderId(java.util.UUID value) { setProviderIdInternal(Guid.fromJava(value)); }

    /// <summary>
    /// Specifies the level of a digital signature based on XML-DSig standard.
    /// The default value is <see cref="DigitalSignatures.XmlDsigLevel.XmlDSig"/>.
    /// </summary>
    /// <remarks>
    /// Different levels of XAdES signatures can be created starting from Office 2010.
    /// </remarks>
    public /*XmlDsigLevel*/int getXmlDsigLevel() { return mXmlDsigLevel; }; public void setXmlDsigLevel(/*XmlDsigLevel*/int value) { mXmlDsigLevel = value; };

    private /*XmlDsigLevel*/int mXmlDsigLevel;

    private String mComments = "";
    private DateTime mSignTime = DateTime.getNow();
    private Guid mSignatureLineId = Guid.Empty;
    private Guid mProviderId = Guid.Empty;
    private byte[] mSignatureLineImage;
    private String mDecryptionPassword = "";
}

