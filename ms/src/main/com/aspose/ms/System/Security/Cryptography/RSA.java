// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/security/cryptography/rsa.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//06/12/17 by Konstantin Sidorenko

package com.aspose.ms.System.Security.Cryptography;

/**
 * Java simplified implementation of
 * https://docs.microsoft.com/en-us/dotnet/api/system.security.cryptography.rsa
 *
 * Extending this class proves that you are implementing an RSA key.
 */
public abstract class RSA extends AsymmetricAlgorithm
{
    protected RSA()
    {}

    static public RSA create()
    {
        return create("System.Security.Cryptography.RSA");
    }

    static public RSA create(String algName)
    {
        //return (RSA) CryptoConfig.CreateFromName(algName);
        throw new RuntimeException("Not Implemented Exception.");
    }

    // Apply the private key to the data.  This function represents a
    // raw RSA operation -- no implicit depadding of the imput value
    abstract public byte[] decryptValue(byte[] rgb);

    // Apply the public key to the data.  Again, this is a raw operation, no
    // automatic padding.
    abstract public byte[] encryptValue(byte[] rgb);

    // We can provide a default implementation of FromXmlString because we require
    // every RSA implementation to implement ImportParameters
    // All we have to do here is parse the XML.
    public void fromXmlString(String xmlString)
    {
        throw new RuntimeException("Not Implemented Exception.");
//        if (xmlString == null) throw new NullPointerException("xmlString");
//        RSAParameters rsaParams = new RSAParameters();
//        Parser p = new Parser(xmlString);
//        SecurityElement topElement = p.GetTopElement();
//
//        // Modulus is always present
//        String modulusString = topElement.SearchForTextOfLocalName("Modulus");
//        if (modulusString == null)
//        {
//            throw new CryptographicException(String.Format(CultureInfo.CurrentCulture,
//                    Environment.GetResourceString("Cryptography_InvalidFromXmlString"), "RSA", "Modulus"));
//        }
//        rsaParams.Modulus = Convert.FromBase64String(Utils.DiscardWhiteSpaces(modulusString));
//
//        // Exponent is always present
//        String exponentString = topElement.SearchForTextOfLocalName("Exponent");
//        if (exponentString == null)
//        {
//            throw new CryptographicException(String.Format(CultureInfo.CurrentCulture,
//                    Environment.GetResourceString("Cryptography_InvalidFromXmlString"), "RSA", "Exponent"));
//        }
//        rsaParams.Exponent = Convert.FromBase64String(Utils.DiscardWhiteSpaces(exponentString));
//
//        // P is optional
//        String pString = topElement.SearchForTextOfLocalName("P");
//        if (pString != null) rsaParams.P = Convert.FromBase64String(Utils.DiscardWhiteSpaces(pString));
//
//        // Q is optional
//        String qString = topElement.SearchForTextOfLocalName("Q");
//        if (qString != null) rsaParams.Q = Convert.FromBase64String(Utils.DiscardWhiteSpaces(qString));
//
//        // DP is optional
//        String dpString = topElement.SearchForTextOfLocalName("DP");
//        if (dpString != null) rsaParams.DP = Convert.FromBase64String(Utils.DiscardWhiteSpaces(dpString));
//
//        // DQ is optional
//        String dqString = topElement.SearchForTextOfLocalName("DQ");
//        if (dqString != null) rsaParams.DQ = Convert.FromBase64String(Utils.DiscardWhiteSpaces(dqString));
//
//        // InverseQ is optional
//        String inverseQString = topElement.SearchForTextOfLocalName("InverseQ");
//        if (inverseQString != null)
//            rsaParams.InverseQ = Convert.FromBase64String(Utils.DiscardWhiteSpaces(inverseQString));
//
//        // D is optional
//        String dString = topElement.SearchForTextOfLocalName("D");
//        if (dString != null) rsaParams.D = Convert.FromBase64String(Utils.DiscardWhiteSpaces(dString));
//
//        ImportParameters(rsaParams);
    }

    // We can provide a default implementation of ToXmlString because we require
    // every RSA implementation to implement ImportParameters
    // If includePrivateParameters is false, this is just an XMLDSIG RSAKeyValue
    // clause.  If includePrivateParameters is true, then we extend RSAKeyValue with
    // the other (private) elements.
    public String toXmlString(boolean includePrivateParameters)
    {
        throw new RuntimeException("Not Implemented Exception.");

        // From the XMLDSIG spec, RFC 3075, Section 6.4.2, an RSAKeyValue looks like this:
            /*
               <element name="RSAKeyValue">
                 <complexType>
                   <sequence>
                     <element name="Modulus" type="ds:CryptoBinary"/>
                     <element name="Exponent" type="ds:CryptoBinary"/>
                   </sequence>
                 </complexType>
               </element>
            */
        // we extend appropriately for private components
//        RSAParameters rsaParams = this.ExportParameters(includePrivateParameters);
//        StringBuilder sb = new StringBuilder();
//        sb.append("<RSAKeyValue>");
//        // Add the modulus
//        sb.append("<Modulus>" + Convert.ToBase64String(rsaParams.Modulus) + "</Modulus>");
//        // Add the exponent
//        sb.append("<Exponent>" + Convert.ToBase64String(rsaParams.Exponent) + "</Exponent>");
//        if (includePrivateParameters)
//        {
//            // Add the private components
//            sb.append("<P>" + Convert.ToBase64String(rsaParams.P) + "</P>");
//            sb.append("<Q>" + Convert.ToBase64String(rsaParams.Q) + "</Q>");
//            sb.append("<DP>" + Convert.ToBase64String(rsaParams.DP) + "</DP>");
//            sb.append("<DQ>" + Convert.ToBase64String(rsaParams.DQ) + "</DQ>");
//            sb.append("<InverseQ>" + Convert.ToBase64String(rsaParams.InverseQ) + "</InverseQ>");
//            sb.append("<D>" + Convert.ToBase64String(rsaParams.D) + "</D>");
//        }
//        sb.append("</RSAKeyValue>");
//        return (sb.toString());
    }

//    abstract public RSAParameters ExportParameters(boolean includePrivateParameters);

//    abstract public void ImportParameters(RSAParameters parameters);
}
