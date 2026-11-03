// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class AlgorithmId
{
    private AlgorithmId(){}
    
    public static final int RC_4 = 0x6801;
    public static final int AES_128 = 0x660e;
    public static final int AES_192 = 0x660f;
    public static final int AES_256 = 0x6610;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AlgorithmId value.
    /// </summary>
    public static String getName(/*AlgorithmId*/int algorithmId)
    {
    	switch (algorithmId)
    	{
    		case RC_4: return "RC_4";
    		case AES_128: return "AES_128";
    		case AES_192: return "AES_192";
    		case AES_256: return "AES_256";
    		default: return "Unknown AlgorithmId value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AlgorithmId value.
    /// </summary>
    public static String toString(/*AlgorithmId*/int algorithmId)
    {
    	switch (algorithmId)
    	{
    		case RC_4: return "Rc4";
    		case AES_128: return "Aes128";
    		case AES_192: return "Aes192";
    		case AES_256: return "Aes256";
    		default: return "Unknown AlgorithmId value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AlgorithmId name.
    /// </summary>
    public static /*AlgorithmId*/int fromName(String algorithmIdName)
    {
    	if ("RC_4".equals(algorithmIdName)) return RC_4;
    	if ("AES_128".equals(algorithmIdName)) return AES_128;
    	if ("AES_192".equals(algorithmIdName)) return AES_192;
    	if ("AES_256".equals(algorithmIdName)) return AES_256;
    	throw new IllegalArgumentException("Unknown AlgorithmId name.");
    }
    
    /// <summary>
    /// Returns array of AlgorithmId values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RC_4,
    		AES_128,
    		AES_192,
    		AES_256,
    	};
    }

    public static final int length = 4;
}

