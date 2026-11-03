// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


// Enumeration used in the init function to specify which CRC algorithm to use
public /*enum*/ final class CrcEncoding
{
    private CrcEncoding(){}
    
    public static final int CRC_16 = 0;
    public static final int CRC_32 = 1;
    public static final int CRC_CCITT = 2;
    public static final int CRC_CCITT_REVERSE = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CrcEncoding value.
    /// </summary>
    public static String getName(/*CrcEncoding*/int crcEncoding)
    {
    	switch (crcEncoding)
    	{
    		case CRC_16: return "CRC_16";
    		case CRC_32: return "CRC_32";
    		case CRC_CCITT: return "CRC_CCITT";
    		case CRC_CCITT_REVERSE: return "CRC_CCITT_REVERSE";
    		default: return "Unknown CrcEncoding value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CrcEncoding value.
    /// </summary>
    public static String toString(/*CrcEncoding*/int crcEncoding)
    {
    	switch (crcEncoding)
    	{
    		case CRC_16: return "CRC16";
    		case CRC_32: return "CRC32";
    		case CRC_CCITT: return "CRC_CCITT";
    		case CRC_CCITT_REVERSE: return "CRC_CCITT_Reverse";
    		default: return "Unknown CrcEncoding value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CrcEncoding name.
    /// </summary>
    public static /*CrcEncoding*/int fromName(String crcEncodingName)
    {
    	if ("CRC_16".equals(crcEncodingName)) return CRC_16;
    	if ("CRC_32".equals(crcEncodingName)) return CRC_32;
    	if ("CRC_CCITT".equals(crcEncodingName)) return CRC_CCITT;
    	if ("CRC_CCITT_REVERSE".equals(crcEncodingName)) return CRC_CCITT_REVERSE;
    	throw new IllegalArgumentException("Unknown CrcEncoding name.");
    }
    
    /// <summary>
    /// Returns array of CrcEncoding values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CRC_16,
    		CRC_32,
    		CRC_CCITT,
    		CRC_CCITT_REVERSE,
    	};
    }

    public static final int length = 4;
}

