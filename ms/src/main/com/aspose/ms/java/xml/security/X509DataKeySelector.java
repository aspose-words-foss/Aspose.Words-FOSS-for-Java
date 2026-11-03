package com.aspose.ms.java.xml.security;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.*;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.security.Key;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Extract a public key and the certificate from the KeyInfo element.
 */
public class X509DataKeySelector extends KeySelector implements KeySelectorResult
{
    @Override
    public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context) throws KeySelectorException
    {
        if (null == keyInfo)
            throw new KeySelectorException("KeyInfo is not present");

        List<XMLStructure> keyInfoContent = keyInfo.getContent();
        for (XMLStructure keyInfoStructure : keyInfoContent)
        {
            if (keyInfoStructure instanceof X509Data)
            {
                X509Data x509Data = (X509Data) keyInfoStructure;
                List x509DataList = x509Data.getContent();
                Iterator it = x509DataList.iterator();
                while (it.hasNext())
                {
                    Object x509DataObject = it.next();
                    if (x509DataObject instanceof X509Certificate)
                    {
                        mCertificate = (X509Certificate) x509DataObject;
                        return this;
                    }
                }
            }
        }
        throw new KeySelectorException("No KeyValue element found!");
    }

    public Key getKey()
    {
        return mCertificate.getPublicKey();
    }

    public X509Certificate getCertificate()
    {
        return mCertificate;
    }

    private X509Certificate mCertificate;
}
