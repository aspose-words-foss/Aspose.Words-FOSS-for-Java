// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/11/2014 by Konstantin Sidorenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Security.Cryptography.X509Certificates.X509Certificate2;
import com.aspose.CertificateHolderInternal;



/// <summary>
/// Represents a holder of <b>X509Certificate2</b> instance.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-digital-signatures/">Work with Digital Signatures</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="CertificateHolder"/> can be created by static factory methods only.
/// It contains an instance of <b>X509Certificate2</b> which is used to introduce private, public keys and certificate chains into the system.
/// <ms><see cref="X509Certificate2"/></ms><java><b>X509Certificate2</b></java><cpp><see cref="X509Certificate2"/></cpp> as parameters.</p>
/// </remarks>
public class CertificateHolder
{

    /// <summary>
    /// Returns the instance of <java><b>X509Certificate2Wrapper</b> that holds </java><b>X509Certificate2</b> which holds private, public keys and certificate chain.
    /// </summary>
    /// <returns><see cref="X509Certificate2"/>
    /// <cpp><see cref="X509Certificate2"/></cpp> instance</returns>
    //JAVA: property accessor is internalized
	X509Certificate2 getCertificateInternal() { return mCertificateHolder != null ? mCertificateHolder.getCertificateNative() : null; }
	//JAVA-added public wrapper for internalized property accessor
	public com.aspose.words.X509Certificate2Wrapper getCertificate() { return X509Certificate2Wrapper.toJava(getCertificateInternal()); }

    /// <summary>
    /// Creates <see cref="CertificateHolder"/> object using byte array of PKCS12 store and its password.
    /// </summary>
    /// <param name="certBytes">A byte array that contains data from an X.509 certificate.</param>
    /// <param name="password">The password required to access the X.509 certificate data.</param>
    /// <returns>An instance of <see cref="CertificateHolder"/></returns>
    /// <exception cref="InvalidParameterException">Thrown if <paramref name="certBytes"/> is <c>null</c></exception>
    /// <exception cref="InvalidParameterException">Thrown if <paramref name="password"/> is <c>null</c></exception>
    /// <exception cref="SecurityException">Thrown if PKCS12 store contains no aliases</exception>
    /// <exception cref="IOException">Thrown if there is wrong password or corrupted file.</exception>
    public static CertificateHolder create(byte[] certBytes, String password) throws Exception
    {
        return new CertificateHolder(CertificateHolderInternal.create(certBytes, password));
    }

    /// <summary>
    /// Creates <see cref="CertificateHolder"/> object using path to PKCS12 store and its password.
    /// </summary>
    /// <param name="fileName">The name of a certificate file.</param>
    /// <param name="password">The password required to access the X.509 certificate data.</param>
    /// <returns>An instance of <see cref="CertificateHolder"/></returns>
    /// <exception cref="InvalidParameterException">Thrown if <paramref name="fileName"/> is <c>null</c></exception>
    /// <exception cref="InvalidParameterException">Thrown if <paramref name="password"/> is <c>null</c></exception>
    /// <exception cref="SecurityException">Thrown if PKCS12 store contains no aliases</exception>
    /// <exception cref="IOException">Thrown if there is wrong password or corrupted file.</exception>
    public static CertificateHolder create(String fileName, String password) throws Exception
    {
        return new CertificateHolder(CertificateHolderInternal.create(fileName, password));
    }

    /// <summary>
    /// Creates <see cref="CertificateHolder"/> object using path to PKCS12 store, its password and the alias by using which private key and certificate will be found.
    /// </summary>
    /// <param name="fileName">The name of a certificate file.</param>
    /// <param name="password">The password required to access the X.509 certificate data.</param>
    /// <param name="alias">The associated alias for a certificate and its private key</param>
    /// <returns>An instance of <see cref="CertificateHolder"/></returns>
    /// <exception cref="InvalidParameterException">Thrown if <paramref name="fileName"/> is <c>null</c></exception>
    /// <exception cref="InvalidParameterException">Thrown if <paramref name="password"/> is <c>null</c></exception>
    /// <exception cref="SecurityException">Thrown if PKCS12 store contains no aliases</exception>
    /// <exception cref="IOException">Thrown if there is wrong password or corrupted file.</exception>
    /// <exception cref="SecurityException">Thrown if there is no private key with the given alias</exception>
    public static CertificateHolder create(String fileName, String password, String alias) throws Exception
    {
        return new CertificateHolder(CertificateHolderInternal.create(fileName, password, alias));
    }

    /// <summary>
    /// Converts CertificateHolder to <see cref="CertificateHolderInternal"/> for internal use.
    /// </summary>
    CertificateHolderInternal toInternal()
    {
        return mCertificateHolder;
    }

    /// <summary>
    /// Internal constructor.
    /// </summary>
    CertificateHolder(CertificateHolderInternal holderInternal)
    {
        mCertificateHolder = holderInternal;
    }

    private /*final*/ CertificateHolderInternal mCertificateHolder;
}

