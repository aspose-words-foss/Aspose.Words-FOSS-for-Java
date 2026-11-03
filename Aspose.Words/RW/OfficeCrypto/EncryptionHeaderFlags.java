// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2009 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// The EncryptionHeaderFlags structure specifies properties of the encryption algorithm used. 
/// It is always contained within an EncryptionHeader structure.
/// </summary>
/*enum*/ final class EncryptionHeaderFlags
{
    private EncryptionHeaderFlags(){}
    
    /// <summary>
    /// A flag that specifies whether CryptoAPI RC4 or [ECMA-376] encryption is used. 
    /// MUST be 1 unless fExternal is 1. If fExternal is 1, MUST be 0.
    /// </summary>
    public static final int CRYPTO_API = 0x0004;
    /// <summary>
    /// MUST be 0 if document properties are encrypted. Encryption of document properties 
    /// is specified in section 2.3.5.4.
    /// </summary>
    public static final int DOC_PROPS = 0x0008;
    /// <summary>
    /// If extensible encryption is used, MUST be 1. If this field is 1, all other fields 
    /// in this structure MUST be 0.
    /// </summary>
    public static final int EXTERNAL = 0x0010;
    /// <summary>
    /// If the protected content is an [ECMA-376] document, MUST be 1. 
    /// If the fAES bit is 1, the fCryptoAPI bit MUST also be 1.
    /// </summary>
    public static final int AES = 0x0020;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EncryptionHeaderFlags value.
    /// </summary>
    public static String getName(/*EncryptionHeaderFlags*/int encryptionHeaderFlags)
    {
    	switch (encryptionHeaderFlags)
    	{
    		case CRYPTO_API: return "CRYPTO_API";
    		case DOC_PROPS: return "DOC_PROPS";
    		case EXTERNAL: return "EXTERNAL";
    		case AES: return "AES";
    		default: return "Unknown EncryptionHeaderFlags value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer EncryptionHeaderFlags value.
    /// </summary>
    public static Set<String> getNames(/*EncryptionHeaderFlags*/int encryptionHeaderFlags)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((CRYPTO_API & encryptionHeaderFlags) == CRYPTO_API) names.add("CRYPTO_API");
    	if ((DOC_PROPS & encryptionHeaderFlags) == DOC_PROPS) names.add("DOC_PROPS");
    	if ((EXTERNAL & encryptionHeaderFlags) == EXTERNAL) names.add("EXTERNAL");
    	if ((AES & encryptionHeaderFlags) == AES) names.add("AES");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EncryptionHeaderFlags value.
    /// </summary>
    public static String toString(/*EncryptionHeaderFlags*/int encryptionHeaderFlags)
    {
    	switch (encryptionHeaderFlags)
    	{
    		case CRYPTO_API: return "CryptoApi";
    		case DOC_PROPS: return "DocProps";
    		case EXTERNAL: return "External";
    		case AES: return "Aes";
    		default: return toStringSet(encryptionHeaderFlags);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EncryptionHeaderFlags value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by EncryptionHeaderFlags name.
    /// </summary>
    public static /*EncryptionHeaderFlags*/int fromName(String encryptionHeaderFlagsName)
    {
    	if ("CRYPTO_API".equals(encryptionHeaderFlagsName)) return CRYPTO_API;
    	if ("DOC_PROPS".equals(encryptionHeaderFlagsName)) return DOC_PROPS;
    	if ("EXTERNAL".equals(encryptionHeaderFlagsName)) return EXTERNAL;
    	if ("AES".equals(encryptionHeaderFlagsName)) return AES;
    	throw new IllegalArgumentException("Unknown EncryptionHeaderFlags name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by EncryptionHeaderFlags names.
    /// </summary>
    public static /*EncryptionHeaderFlags*/int fromNames(Set<String> encryptionHeaderFlagsNames)
    {
    	/*EncryptionHeaderFlags*/int result = 0;
    	for (String name : encryptionHeaderFlagsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of EncryptionHeaderFlags values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CRYPTO_API,
    		DOC_PROPS,
    		EXTERNAL,
    		AES,
    	};
    }

    public static final int length = 4;
}

