// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2018 by Victor Moskvitin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents cryptografic algorithm used for signing.
/// </summary>
public /*enum*/ final class CryptoAlgorithm /*: byte*/
{
    private CryptoAlgorithm(){}
    
    public static final byte RSA = (byte)0;
    public static final byte DSA = (byte)1;
    public static final byte ECDSA = (byte)2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CryptoAlgorithm value.
    /// </summary>
    public static String getName(/*CryptoAlgorithm*/byte cryptoAlgorithm)
    {
    	if (RSA == cryptoAlgorithm) return "RSA";
    	if (DSA == cryptoAlgorithm) return "DSA";
    	if (ECDSA == cryptoAlgorithm) return "ECDSA";
    	return "Unknown CryptoAlgorithm value.";
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CryptoAlgorithm value.
    /// </summary>
    public static String toString(/*CryptoAlgorithm*/byte cryptoAlgorithm)
    {
    	if (RSA == cryptoAlgorithm) return "RSA";
    	if (DSA == cryptoAlgorithm) return "DSA";
    	if (ECDSA == cryptoAlgorithm) return "ECDSA";
    	return "Unknown CryptoAlgorithm value.";
    }
    
    /// <summary>
    /// Returns integer representation by CryptoAlgorithm name.
    /// </summary>
    public static /*CryptoAlgorithm*/byte fromName(String cryptoAlgorithmName)
    {
    	if ("RSA".equals(cryptoAlgorithmName)) return RSA;
    	if ("DSA".equals(cryptoAlgorithmName)) return DSA;
    	if ("ECDSA".equals(cryptoAlgorithmName)) return ECDSA;
    	throw new IllegalArgumentException("Unknown CryptoAlgorithm name.");
    }
    
    /// <summary>
    /// Returns array of CryptoAlgorithm values.
    /// </summary>
    public static byte[] getValues()
    {
    	return new byte[]
    	{
    		RSA,
    		DSA,
    		ECDSA,
    	};
    }

    public static final int length = 3;
}

