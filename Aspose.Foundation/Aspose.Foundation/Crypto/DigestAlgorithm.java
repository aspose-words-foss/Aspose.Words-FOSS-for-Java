// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/04/2016 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This is needed to make cryptography portable.
/// </summary>
public /*enum*/ final class DigestAlgorithm
{
    private DigestAlgorithm(){}
    
    public static final int UNKNOWN = 0;
    public static final int SHA_1 = 1;
    public static final int SHA_224 = 2;
    public static final int SHA_256 = 3;
    public static final int SHA_384 = 4;
    public static final int SHA_512 = 5;
    public static final int MD_2 = 6;
    public static final int MD_4 = 7;
    public static final int MD_5 = 8;
    public static final int RIPE_MD_160 = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DigestAlgorithm value.
    /// </summary>
    public static String getName(/*DigestAlgorithm*/int digestAlgorithm)
    {
    	switch (digestAlgorithm)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case SHA_1: return "SHA_1";
    		case SHA_224: return "SHA_224";
    		case SHA_256: return "SHA_256";
    		case SHA_384: return "SHA_384";
    		case SHA_512: return "SHA_512";
    		case MD_2: return "MD_2";
    		case MD_4: return "MD_4";
    		case MD_5: return "MD_5";
    		case RIPE_MD_160: return "RIPE_MD_160";
    		default: return "Unknown DigestAlgorithm value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DigestAlgorithm value.
    /// </summary>
    public static String toString(/*DigestAlgorithm*/int digestAlgorithm)
    {
    	switch (digestAlgorithm)
    	{
    		case UNKNOWN: return "Unknown";
    		case SHA_1: return "Sha1";
    		case SHA_224: return "Sha224";
    		case SHA_256: return "Sha256";
    		case SHA_384: return "Sha384";
    		case SHA_512: return "Sha512";
    		case MD_2: return "MD2";
    		case MD_4: return "MD4";
    		case MD_5: return "MD5";
    		case RIPE_MD_160: return "RipeMD160";
    		default: return "Unknown DigestAlgorithm value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DigestAlgorithm name.
    /// </summary>
    public static /*DigestAlgorithm*/int fromName(String digestAlgorithmName)
    {
    	if ("UNKNOWN".equals(digestAlgorithmName)) return UNKNOWN;
    	if ("SHA_1".equals(digestAlgorithmName)) return SHA_1;
    	if ("SHA_224".equals(digestAlgorithmName)) return SHA_224;
    	if ("SHA_256".equals(digestAlgorithmName)) return SHA_256;
    	if ("SHA_384".equals(digestAlgorithmName)) return SHA_384;
    	if ("SHA_512".equals(digestAlgorithmName)) return SHA_512;
    	if ("MD_2".equals(digestAlgorithmName)) return MD_2;
    	if ("MD_4".equals(digestAlgorithmName)) return MD_4;
    	if ("MD_5".equals(digestAlgorithmName)) return MD_5;
    	if ("RIPE_MD_160".equals(digestAlgorithmName)) return RIPE_MD_160;
    	throw new IllegalArgumentException("Unknown DigestAlgorithm name.");
    }
    
    /// <summary>
    /// Returns array of DigestAlgorithm values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		SHA_1,
    		SHA_224,
    		SHA_256,
    		SHA_384,
    		SHA_512,
    		MD_2,
    		MD_4,
    		MD_5,
    		RIPE_MD_160,
    	};
    }

    public static final int length = 10;
}

