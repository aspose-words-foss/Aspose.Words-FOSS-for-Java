package com.aspose.ms.java.xml.security;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.security.Key;
import java.security.cert.X509Certificate;

/**
 * Provides the public key from the given X509Certificate.
 */
public class CertificateKeySelector extends KeySelector implements KeySelectorResult
{

    public CertificateKeySelector(X509Certificate certificate)
    {
        if (certificate == null)
            throw new IllegalArgumentException("Certificate cannot be null");

        this.mCertificate = certificate;
    }

    @Override
    public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context) throws KeySelectorException
    {
        return this;
    }

    @Override
    public Key getKey()
    {
        return mCertificate.getPublicKey();
    }

    private X509Certificate mCertificate;
}
