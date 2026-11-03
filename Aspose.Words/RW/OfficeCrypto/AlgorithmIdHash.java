// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class AlgorithmIdHash
{
    private AlgorithmIdHash(){}
    
    public static final int SHA_1 = 0x8004;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AlgorithmIdHash value.
    /// </summary>
    public static String getName(/*AlgorithmIdHash*/int algorithmIdHash)
    {
    	switch (algorithmIdHash)
    	{
    		case SHA_1: return "SHA_1";
    		default: return "Unknown AlgorithmIdHash value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AlgorithmIdHash value.
    /// </summary>
    public static String toString(/*AlgorithmIdHash*/int algorithmIdHash)
    {
    	switch (algorithmIdHash)
    	{
    		case SHA_1: return "Sha1";
    		default: return "Unknown AlgorithmIdHash value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AlgorithmIdHash name.
    /// </summary>
    public static /*AlgorithmIdHash*/int fromName(String algorithmIdHashName)
    {
    	if ("SHA_1".equals(algorithmIdHashName)) return SHA_1;
    	throw new IllegalArgumentException("Unknown AlgorithmIdHash name.");
    }
    
    /// <summary>
    /// Returns array of AlgorithmIdHash values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SHA_1,
    	};
    }

    public static final int length = 1;
}

