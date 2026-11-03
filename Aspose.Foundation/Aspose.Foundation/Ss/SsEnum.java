// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Special codes for FAT entries.
/// </summary>
/*static*/ class FatEntryType
{
	/* Simulation of static class by using private constructor */
	private FatEntryType()
	{}

    /// <summary>
    /// denotes a DIFAT sector in a FAT
    /// </summary>
    static final /*uint*/int DIF_SECT = (int) 0xFFFFFFFCL;
    /// <summary>
    /// denotes a FAT sector in a FAT
    /// </summary>
    static final /*uint*/int FAT_SECT = (int) 0xFFFFFFFDL;
    /// <summary>
    /// end of a virtual stream chain
    /// </summary>
    static final /*uint*/int END_OF_CHAIN = (int) 0xFFFFFFFEL;
    /// <summary>
    /// unallocated sector
    /// </summary>
    static final /*uint*/int FREE_SECT = (int) 0xFFFFFFFFL;
}

/// <summary>
/// Directory entry type.
/// </summary>
/*enum*/ final class DirEntryType
{
    private DirEntryType(){}
    
    /// <summary>
    /// unknown storage type
    /// </summary>
    public static final int INVALID = 0;
    /// <summary>
    /// element is a storage object
    /// </summary>
    public static final int STORAGE = 1;
    /// <summary>
    /// element is a stream object
    /// </summary>
    public static final int STREAM = 2;
    /// <summary>
    /// element is an ILockBytes object
    /// </summary>
    public static final int LOCK_BYTES = 3;
    /// <summary>
    /// element is an IPropertyStorage object
    /// </summary>
    public static final int PROPERTY = 4;
    /// <summary>
    /// element is a root storage
    /// </summary>
    public static final int ROOT = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DirEntryType value.
    /// </summary>
    public static String getName(/*DirEntryType*/int dirEntryType)
    {
    	switch (dirEntryType)
    	{
    		case INVALID: return "INVALID";
    		case STORAGE: return "STORAGE";
    		case STREAM: return "STREAM";
    		case LOCK_BYTES: return "LOCK_BYTES";
    		case PROPERTY: return "PROPERTY";
    		case ROOT: return "ROOT";
    		default: return "Unknown DirEntryType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DirEntryType value.
    /// </summary>
    public static String toString(/*DirEntryType*/int dirEntryType)
    {
    	switch (dirEntryType)
    	{
    		case INVALID: return "Invalid";
    		case STORAGE: return "Storage";
    		case STREAM: return "Stream";
    		case LOCK_BYTES: return "LockBytes";
    		case PROPERTY: return "Property";
    		case ROOT: return "Root";
    		default: return "Unknown DirEntryType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DirEntryType name.
    /// </summary>
    public static /*DirEntryType*/int fromName(String dirEntryTypeName)
    {
    	if ("INVALID".equals(dirEntryTypeName)) return INVALID;
    	if ("STORAGE".equals(dirEntryTypeName)) return STORAGE;
    	if ("STREAM".equals(dirEntryTypeName)) return STREAM;
    	if ("LOCK_BYTES".equals(dirEntryTypeName)) return LOCK_BYTES;
    	if ("PROPERTY".equals(dirEntryTypeName)) return PROPERTY;
    	if ("ROOT".equals(dirEntryTypeName)) return ROOT;
    	throw new IllegalArgumentException("Unknown DirEntryType name.");
    }
    
    /// <summary>
    /// Returns array of DirEntryType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INVALID,
    		STORAGE,
    		STREAM,
    		LOCK_BYTES,
    		PROPERTY,
    		ROOT,
    	};
    }

    public static final int length = 6;
}

/// <summary>
/// Directory entry color for binary tree stuff. I don't really use it.
/// </summary>
/*enum*/ final class DirEntryColor
{
    private DirEntryColor(){}
    
    public static final int RED = 0;
    public static final int BLACK = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DirEntryColor value.
    /// </summary>
    public static String getName(/*DirEntryColor*/int dirEntryColor)
    {
    	switch (dirEntryColor)
    	{
    		case RED: return "RED";
    		case BLACK: return "BLACK";
    		default: return "Unknown DirEntryColor value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DirEntryColor value.
    /// </summary>
    public static String toString(/*DirEntryColor*/int dirEntryColor)
    {
    	switch (dirEntryColor)
    	{
    		case RED: return "Red";
    		case BLACK: return "Black";
    		default: return "Unknown DirEntryColor value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DirEntryColor name.
    /// </summary>
    public static /*DirEntryColor*/int fromName(String dirEntryColorName)
    {
    	if ("RED".equals(dirEntryColorName)) return RED;
    	if ("BLACK".equals(dirEntryColorName)) return BLACK;
    	throw new IllegalArgumentException("Unknown DirEntryColor name.");
    }
    
    /// <summary>
    /// Returns array of DirEntryColor values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RED,
    		BLACK,
    	};
    }

    public static final int length = 2;
}

