// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates values used in Office encryption algorithms.
/// </summary>
/// <remarks>
/// Applications in the 2007 Office system earlier than Service Pack 2 set a Version.vMajor value of 0x0003. 
/// Versions with Service Pack 2 and Office 2010 set a Version.vMajor value of 0x004. 
/// Office 2003 applications set a Version.vMajor version value of 0x0002.
/// </remarks>
/*enum*/ final class EncryptionVersionInfo
{
    private EncryptionVersionInfo(){}
    
    /// <summary>
    /// Office Binary RC4 encryption.
    /// </summary>
    public static final int RC_4 = 0x00010001;

    /// <summary>
    /// Office Binary RC4 CryptoAPI encryption in Office 2003.
    /// </summary>
    public static final int CRYPTO_API = 0x00020002;

    /// <summary>
    /// Office Binary RC4 CryptoAPI encryption in Office 2007.
    /// </summary>
    public static final int CRYPTO_API_2007 = 0x00020003;

    /// <summary>
    /// Office Binary RC4 CryptoAPI encryption in Office 2010.
    /// </summary>
    public static final int CRYPTO_API_2010 = 0x00020004;

    /// <summary>
    /// ECMA-376 Standard encryption in Office 2007 earlier than Service Pack 2.
    /// </summary>
    public static final int STANDARD = 0x00020003;

    /// <summary>
    /// ECMA-376 Standard encryption in Office 2007 with Service Pack 2 and Office 2010.
    /// </summary>
    public static final int STANDARD_2010 = 0x00020004;

    /// <summary>
    /// ECMA-376 Extensible encryption in Office 2007 earlier than Service Pack 2.
    /// </summary>
    public static final int EXTENSIBLE = 0x00030003;

    /// <summary>
    /// ECMA-376 Extensible encryption in Office 2007 with Service Pack 2 and Office 2010.
    /// </summary>
    public static final int EXTENSIBLE_2010 = 0x00030004;

    /// <summary>
    /// ECMA-376 Agile encryption.
    /// </summary>
    public static final int AGILE = 0x00040004;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EncryptionVersionInfo value.
    /// </summary>
    public static String getName(/*EncryptionVersionInfo*/int encryptionVersionInfo)
    {
    	switch (encryptionVersionInfo)
    	{
    		case RC_4: return "RC_4";
    		case CRYPTO_API: return "CRYPTO_API";
    		case CRYPTO_API_2007:
    		/*case STANDARD:*/
    			return "CRYPTO_API_2007" + " | " + "STANDARD";
    		case CRYPTO_API_2010:
    		/*case STANDARD_2010:*/
    			return "CRYPTO_API_2010" + " | " + "STANDARD_2010";
    		case EXTENSIBLE: return "EXTENSIBLE";
    		case EXTENSIBLE_2010: return "EXTENSIBLE_2010";
    		case AGILE: return "AGILE";
    		default: return "Unknown EncryptionVersionInfo value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EncryptionVersionInfo value.
    /// </summary>
    public static String toString(/*EncryptionVersionInfo*/int encryptionVersionInfo)
    {
    	switch (encryptionVersionInfo)
    	{
    		case RC_4: return "RC4";
    		case CRYPTO_API: return "CryptoApi";
    		case CRYPTO_API_2007:
    		/*case STANDARD:*/
    			return "CryptoApi2007" + " | " + "Standard";
    		case CRYPTO_API_2010:
    		/*case STANDARD_2010:*/
    			return "CryptoApi2010" + " | " + "Standard2010";
    		case EXTENSIBLE: return "Extensible";
    		case EXTENSIBLE_2010: return "Extensible2010";
    		case AGILE: return "Agile";
    		default: return "Unknown EncryptionVersionInfo value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EncryptionVersionInfo name.
    /// </summary>
    public static /*EncryptionVersionInfo*/int fromName(String encryptionVersionInfoName)
    {
    	if ("RC_4".equals(encryptionVersionInfoName)) return RC_4;
    	if ("CRYPTO_API".equals(encryptionVersionInfoName)) return CRYPTO_API;
    	if ("CRYPTO_API_2007".equals(encryptionVersionInfoName)) return CRYPTO_API_2007;
    	if ("CRYPTO_API_2010".equals(encryptionVersionInfoName)) return CRYPTO_API_2010;
    	if ("STANDARD".equals(encryptionVersionInfoName)) return STANDARD;
    	if ("STANDARD_2010".equals(encryptionVersionInfoName)) return STANDARD_2010;
    	if ("EXTENSIBLE".equals(encryptionVersionInfoName)) return EXTENSIBLE;
    	if ("EXTENSIBLE_2010".equals(encryptionVersionInfoName)) return EXTENSIBLE_2010;
    	if ("AGILE".equals(encryptionVersionInfoName)) return AGILE;
    	throw new IllegalArgumentException("Unknown EncryptionVersionInfo name.");
    }
    
    /// <summary>
    /// Returns array of EncryptionVersionInfo values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RC_4,
    		CRYPTO_API,
    		CRYPTO_API_2007,
    		CRYPTO_API_2010,
    		STANDARD,
    		STANDARD_2010,
    		EXTENSIBLE,
    		EXTENSIBLE_2010,
    		AGILE,
    	};
    }

    public static final int length = 9;
}

