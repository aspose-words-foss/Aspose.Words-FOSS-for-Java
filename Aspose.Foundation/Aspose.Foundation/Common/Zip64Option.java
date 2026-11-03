// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Options for using ZIP64 extensions when saving zip archives. 
/// </summary>
public /*enum*/ final class Zip64Option
{
    private Zip64Option(){}
    
    /// <summary>
    /// The default behavior, which is "Never".
    /// (For COM clients, this is a 0 (zero).)
    /// </summary>
    public static final int DEFAULT = 0;
    /// <summary>
    /// Do not use ZIP64 extensions when writing zip archives.
    /// (For COM clients, this is a 0 (zero).)
    /// </summary>
    public static final int NEVER = 0;
    /// <summary>
    /// Use ZIP64 extensions when writing zip archives, as necessary. 
    /// For example, when a single entry exceeds 0xFFFFFFFF in size, or when the archive as a whole 
    /// exceeds 0xFFFFFFFF in size, or when there are more than 65535 entries in an archive.
    /// (For COM clients, this is a 1.)
    /// </summary>
    public static final int AS_NECESSARY = 1;
    /// <summary>
    /// Always use ZIP64 extensions when writing zip archives, even when unnecessary.
    /// (For COM clients, this is a 2.)
    /// </summary>
    public static final int ALWAYS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Zip64Option value.
    /// </summary>
    public static String getName(/*Zip64Option*/int zip64Option)
    {
    	switch (zip64Option)
    	{
    		case DEFAULT:
    		/*case NEVER:*/
    			return "DEFAULT" + " | " + "NEVER";
    		case AS_NECESSARY: return "AS_NECESSARY";
    		case ALWAYS: return "ALWAYS";
    		default: return "Unknown Zip64Option value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Zip64Option value.
    /// </summary>
    public static String toString(/*Zip64Option*/int zip64Option)
    {
    	switch (zip64Option)
    	{
    		case DEFAULT:
    		/*case NEVER:*/
    			return "Default" + " | " + "Never";
    		case AS_NECESSARY: return "AsNecessary";
    		case ALWAYS: return "Always";
    		default: return "Unknown Zip64Option value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Zip64Option name.
    /// </summary>
    public static /*Zip64Option*/int fromName(String zip64OptionName)
    {
    	if ("DEFAULT".equals(zip64OptionName)) return DEFAULT;
    	if ("NEVER".equals(zip64OptionName)) return NEVER;
    	if ("AS_NECESSARY".equals(zip64OptionName)) return AS_NECESSARY;
    	if ("ALWAYS".equals(zip64OptionName)) return ALWAYS;
    	throw new IllegalArgumentException("Unknown Zip64Option name.");
    }
    
    /// <summary>
    /// Returns array of Zip64Option values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		NEVER,
    		AS_NECESSARY,
    		ALWAYS,
    	};
    }

    public static final int length = 4;
}

