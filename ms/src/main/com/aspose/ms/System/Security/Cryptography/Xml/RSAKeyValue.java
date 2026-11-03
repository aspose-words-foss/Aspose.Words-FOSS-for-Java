// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2013 by Vyacheslav Durin
package com.aspose.ms.System.Security.Cryptography.Xml;

import java.security.PrivateKey;

/**
 * Represents a key clause that is used to set an RSA key as part of the definition of KeyInfo for a digital signature.
 * http://msdn.microsoft.com/en-us/library/system.security.cryptography.xml.rsakeyvalue(v=vs.71).aspx
 */
public class RSAKeyValue extends KeyInfoClause
{
    public RSAKeyValue(PrivateKey privateKey)
    {
        this.privateKey = privateKey;
    }

    private final PrivateKey privateKey;
}
