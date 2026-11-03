// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Contains details for signing a document with a digital signature.
/// </summary>
public class DigitalSignatureDetails
{
    /// <summary>
    /// Initializes a new instance of <see cref="DigitalSignatureDetails"/> class.
    /// </summary>
    /// <param name="certificateHolder">A certificate holder which contains the certificate itself.</param>
    /// <param name="signOptions">Signature options to use for signing a document.</param>
    public DigitalSignatureDetails(CertificateHolder certificateHolder, SignOptions signOptions)
    {
        setCertificateHolder(certificateHolder);
        setSignOptions(signOptions);
    }

    /// <summary>
    /// Gets <see cref="DigitalSignature"/> object corresponded to this signature details.
    /// Can be <c>null</c>, in case of <see cref="CertificateHolder"/> is <c>null</c>.
    /// </summary>
    DigitalSignature getDigitalSignature()
    {
        if (getCertificateHolder() == null)
            return null;

        DigitalSignature signature = new DigitalSignature(getCertificateHolder());

        if (getSignOptions() != null)
            getSignOptions().applyTo(signature);

        return signature;
    }

    /// <summary>
    /// Gets or sets a <see cref="CertificateHolder"/> object that contains the certificate used to sign a document.
    /// </summary>
    public CertificateHolder getCertificateHolder() { return mCertificateHolder; }; public void setCertificateHolder(CertificateHolder value) { mCertificateHolder = value; };

    private CertificateHolder mCertificateHolder;

    /// <summary>
    /// Gets or sets a <see cref="SignOptions"/> object used to sign a document.
    /// </summary>
    public SignOptions getSignOptions() { return mSignOptions; }; public void setSignOptions(SignOptions value) { mSignOptions = value; };

    private SignOptions mSignOptions;
}

