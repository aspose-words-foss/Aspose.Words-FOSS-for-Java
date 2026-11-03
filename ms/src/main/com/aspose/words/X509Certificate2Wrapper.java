package com.aspose.words;

import com.aspose.ms.System.Security.Cryptography.X509Certificates.X509Certificate2;

import java.security.cert.X509Certificate;

/**
 *  JAVA-added public wrapper around ours internal X509Certificate2.
 *  Needed to smoothly emulate .Net api and simplify java user code.
 *  Ideally we have to use java.security.cert.X509Certificate instead this
 *  but we still didn't manage to get private key from java X509Certificate - may be fix later.
 */
public class X509Certificate2Wrapper
{
    public X509Certificate2Wrapper(String fileName, String password)
    {
        mCertificate = new X509Certificate2(fileName, password);
    }

    private X509Certificate2Wrapper(X509Certificate2 certificate2)
    {
        mCertificate = certificate2;
    }

    X509Certificate2 getInternal()
    {
        return mCertificate;
    }

    static X509Certificate2 fromJava(X509Certificate2Wrapper certificate)
    {
        return certificate.getInternal();
    }

    static X509Certificate2Wrapper toJava(X509Certificate2 certificate2)
    {
        return new X509Certificate2Wrapper(certificate2);
    }

    /**
     *  Java certificate is used to get generic certificate info: notBefore, notAfter, etc.
     */
    public X509Certificate getJavaCertificateInfo()
    {
        return mCertificate.getJavaCertificate();
    }

    // real X509Certificate2 that wrapped by this class.
    private X509Certificate2 mCertificate;
}
