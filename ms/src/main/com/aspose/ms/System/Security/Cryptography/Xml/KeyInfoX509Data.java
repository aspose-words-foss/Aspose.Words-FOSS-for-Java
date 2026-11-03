// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2013 by Vyacheslav Durin
package com.aspose.ms.System.Security.Cryptography.Xml;

import com.aspose.ms.System.Security.Cryptography.X509Certificates.X509Certificate2;
import com.aspose.ms.System.Security.Cryptography.X509Certificates.X509IncludeOption;
import com.aspose.ms.java.logging.Log;

import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an <X509Data> subelement of an XMLDSIG <KeyInfo> element.
 * http://msdn.microsoft.com/en-us/library/system.security.cryptography.xml.keyinfox509data(v=vs.71).aspx
 */
public class KeyInfoX509Data extends KeyInfoClause
{
    /**
     * @return a list of the X.509v3 certificates contained in the KeyInfoX509Data object.
     */
    public List<X509Certificate2> getX509Certificates() { return mCertificates; }

    public List<X509IssuerSerial> getIssuerSerials() { return mIssuerSerials; }
    /**
     * Adds the specified issuer name and serial number pair to the KeyInfoX509Data object.
     *
     * @param issuerName   The issuer name portion of the pair to add to the KeyInfoX509Data object.
     * @param serialNumber The serial number portion of the pair to add to the KeyInfoX509Data object.
     */
    public void addIssuerSerial(String issuerName, BigInteger serialNumber)
    {
        X509IssuerSerial issuer = mKeyInfoFactory.newX509IssuerSerial(issuerName, serialNumber);
        mIssuerSerials.add(issuer);
    }

    public void addCertificate(X509Certificate2 certificate)
    {
        if (certificate == null)
            throw new IllegalArgumentException("Certificate cannot be null");
        mCertificates.add(certificate);
    }

    /**
     * Initializes a new instance of the KeyInfoX509Data class from the specified ASN.1 DER encoding of an X.509v3 certificate.
     *
     * @param encoded The ASN.1 DER encoding of an X509Certificate object to initialize the new instance of KeyInfoX509Data from.
     */
    public KeyInfoX509Data(byte[] encoded)
    {
        this(new X509Certificate2(encoded), X509IncludeOption.END_CERT_ONLY);
    }

    /**
     * Initializes a new instance of the KeyInfoX509Data class from the specified X.509v3 certificate.
     *
     * @param certificate   The X509Certificate object to initialize the new instance of KeyInfoX509Data from.
     */
    public KeyInfoX509Data(X509Certificate2 certificate)
    {
        this(certificate, X509IncludeOption.END_CERT_ONLY);
    }

    /**
     * Initializes a new instance of the KeyInfoX509Data class from the specified X.509v3 certificate.
     *
     * @param certificate   The X509Certificate object to initialize the new instance of KeyInfoX509Data from.
     * @param includeOption One of the X509IncludeOption values that specifies how much of the certificate chain to include.
     */
    public KeyInfoX509Data(X509Certificate2 certificate, /*X509IncludeOption*/ int includeOption)
    {
        mKeyInfoFactory = KeyInfoFactory.getInstance();
        switch (includeOption)
        {
            case X509IncludeOption.NONE:
            case X509IncludeOption.END_CERT_ONLY:
                addCertificate(certificate);
                break;
            case X509IncludeOption.EXCLUDE_ROOT:
                addCertificates(certificate, true);
            case X509IncludeOption.WHOLE_CHAIN:
                addCertificates(certificate, false);
                break;
        }
    }

    private void addCertificates(X509Certificate2 certificate, boolean exludeRoot)
    {
        X509Certificate[] chain = certificate.getCertificateChain();
        for (X509Certificate cert : chain)
        {
            if (exludeRoot && isSelfSigned(cert))
                continue;

            try
            {
                addCertificate(new X509Certificate2(cert.getEncoded()));
            }
            catch (CertificateEncodingException e)
            {
                Log.error(e);
            }
        }
    }

    private static boolean isSelfSigned(X509Certificate cert)
    {
        try
        {
            // Try to verify certificate signature with its own public key
            PublicKey key = cert.getPublicKey();
            cert.verify(key);
            return true;
        }
        catch (SignatureException sigEx)
        {
            // Invalid signature --> not self-signed
            return false;
        }
        catch (InvalidKeyException keyEx)
        {
            // Invalid key --> not self-signed
            return false;
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    private final KeyInfoFactory mKeyInfoFactory;
    private List<X509Certificate2> mCertificates = new ArrayList<X509Certificate2>();
    private List<X509IssuerSerial> mIssuerSerials = new ArrayList<X509IssuerSerial>();
}
