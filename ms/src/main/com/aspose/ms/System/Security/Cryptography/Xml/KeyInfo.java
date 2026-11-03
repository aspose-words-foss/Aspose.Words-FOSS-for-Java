// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2013 by Vyacheslav Durin
package com.aspose.ms.System.Security.Cryptography.Xml;

import com.aspose.ms.System.Security.Cryptography.X509Certificates.X509Certificate2;

import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import java.util.List;

/**
 * Represents an XMLDSIG <KeyInfo> element.
 * http://msdn.microsoft.com/ru-ru/library/system.security.cryptography.xml.keyinfo(v=vs.71).aspx
 */
public class KeyInfo
{
    public void addClause(KeyInfoClause keyInfoClause)
    {
        if (keyInfoClause instanceof RSAKeyValue)
            this.rsaKeyValue = (RSAKeyValue) keyInfoClause;

        if (keyInfoClause instanceof KeyInfoX509Data)
            this.keyInfoX509Data = (KeyInfoX509Data) keyInfoClause;
    }

    public List<X509Certificate2> getX509Certificates()
    {
        return keyInfoX509Data.getX509Certificates();
    }

    public List<X509IssuerSerial> getX509IssuerSerials()
    {
        return keyInfoX509Data.getIssuerSerials();
    }

    private RSAKeyValue rsaKeyValue;
    private KeyInfoX509Data keyInfoX509Data;
}
