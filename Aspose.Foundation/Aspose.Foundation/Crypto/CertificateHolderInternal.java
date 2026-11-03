// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/10/2014 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.crypto.AsymmetricKey;
import com.aspose.ms.System.Security.Cryptography.X509Certificates.X509Certificate2;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;


public class CertificateHolderInternal
{
    public X509CertificateHolder getCertificateBc() { return mCertificateBc; }
    public void setCertificateBc(X509CertificateHolder value)
    {
        mCertificateBc = value;
        setCertificateNative(CryptoUtilPal.toNativeCertificate(mCertificateBc));
    }

    public AsymmetricKey getPrivateKeyBc() { return mPrivateKeyBc; }
    public void setPrivateKeyBc(AsymmetricKey value) { mPrivateKeyBc = value; }

    public X509Certificate2 getCertificateNative() { return mCertificateNative; }
    void setCertificateNative(X509Certificate2 value) { mCertificateNative = value; }

    public String getSubjectX509Principal(ASN1ObjectIdentifier name)
    {
        org.bouncycastle.asn1.x500.RDN[] listValues = mCertificateBc.getSubject().getRDNs(name);
        return listValues != null && listValues.length > 0 && listValues[0] != null ? listValues[0].getFirst().getValue().toString() : null;
    }

    public String getSubjectX509Principal(String name)
    {
        return getSubjectX509Principal(new ASN1ObjectIdentifier(name));
    }

    public static CertificateHolderInternal create(byte[] storeBytes, String password) throws Exception
    {
        Stream storeStream = new MemoryStream(storeBytes);
        try /*JAVA: was using*/
    	{
            return CryptoUtilPal.createHolderForPkcs12(storeStream, password);
    	}
        finally { if (storeStream != null) storeStream.close(); }
    }

    public static CertificateHolderInternal create(String storefileName, String password) throws Exception
    {
        // WORDSNET-25903 We should access certificate file in read-only mode.
        FileStream storeStream = File.open(storefileName, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
    	{
            return CryptoUtilPal.createHolderForPkcs12(storeStream, password);
    	}
        finally { if (storeStream != null) storeStream.close(); }
    }

    public static CertificateHolderInternal create(String storefileName, String password, String alias) throws Exception
    {
        // WORDSNET-25903 We should access certificate file in read-only mode.
        FileStream storeStream = File.open(storefileName, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
    	{
            return CryptoUtilPal.createHolderForPkcs12(storeStream, password, alias);
    	}
        finally { if (storeStream != null) storeStream.close(); }
    }

    public static CertificateHolderInternal create()
    {
        return new CertificateHolderInternal();
    }

    //<<<<<<<< #endregion 

    CertificateHolderInternal()
    {
        //
    }

    private X509Certificate2 mCertificateNative;
    private X509CertificateHolder mCertificateBc;
    private AsymmetricKey mPrivateKeyBc;
}

