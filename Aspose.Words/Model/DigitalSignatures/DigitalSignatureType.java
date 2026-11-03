// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of a digital signature.
/// </summary>
public /*enum*/ final class DigitalSignatureType
{
    private DigitalSignatureType(){}
    
    /// <summary>
    /// Indicates an error, unknown digital signature type.
    /// </summary>
    public static final int UNKNOWN = 0; 
    /// <summary>
    /// The Crypto API signature method used in Microsoft Word 97-2003 .DOC binary documents.
    /// </summary>
    public static final int CRYPTO_API = 1;
    /// <summary>
    /// The XmlDsig signature method used in OOXML and OpenDocument documents.
    /// </summary>
    public static final int XML_DSIG = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DigitalSignatureType value.
    /// </summary>
    public static String getName(/*DigitalSignatureType*/int digitalSignatureType)
    {
    	switch (digitalSignatureType)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case CRYPTO_API: return "CRYPTO_API";
    		case XML_DSIG: return "XML_DSIG";
    		default: return "Unknown DigitalSignatureType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DigitalSignatureType value.
    /// </summary>
    public static String toString(/*DigitalSignatureType*/int digitalSignatureType)
    {
    	switch (digitalSignatureType)
    	{
    		case UNKNOWN: return "Unknown";
    		case CRYPTO_API: return "CryptoApi";
    		case XML_DSIG: return "XmlDsig";
    		default: return "Unknown DigitalSignatureType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DigitalSignatureType name.
    /// </summary>
    public static /*DigitalSignatureType*/int fromName(String digitalSignatureTypeName)
    {
    	if ("UNKNOWN".equals(digitalSignatureTypeName)) return UNKNOWN;
    	if ("CRYPTO_API".equals(digitalSignatureTypeName)) return CRYPTO_API;
    	if ("XML_DSIG".equals(digitalSignatureTypeName)) return XML_DSIG;
    	throw new IllegalArgumentException("Unknown DigitalSignatureType name.");
    }
    
    /// <summary>
    /// Returns array of DigitalSignatureType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		CRYPTO_API,
    		XML_DSIG,
    	};
    }

    public static final int length = 3;
}

