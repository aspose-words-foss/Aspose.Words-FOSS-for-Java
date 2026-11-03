// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/11/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies when to use ZIP64 format extensions for OOXML files.
/// </summary>
/// <remarks>
/// OOXML file is a ZIP-archive that has a 4 GB (2^32 bytes) limit on uncompressed size of a file,
/// compressed size of a file, and total size of the archive, as well as a limit of 65,535 (2^16-1) files in archive.
/// ZIP64 format extensions increase the limits to 2^64.
/// </remarks>
/// <seealso cref="OoxmlSaveOptions.Zip64Mode"/>
public /*enum*/ final class Zip64Mode
{
    private Zip64Mode(){}
    
    /// <summary>
    /// Do not use ZIP64 format extensions.
    /// </summary>
    public static final int NEVER = 0;

    /// <summary>
    /// If necessary use ZIP64 format extensions.
    /// </summary>
    public static final int IF_NECESSARY = 1;

    /// <summary>
    /// Always use ZIP64 format extensions.
    /// </summary>
    public static final int ALWAYS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Zip64Mode value.
    /// </summary>
    public static String getName(/*Zip64Mode*/int zip64Mode)
    {
    	switch (zip64Mode)
    	{
    		case NEVER: return "NEVER";
    		case IF_NECESSARY: return "IF_NECESSARY";
    		case ALWAYS: return "ALWAYS";
    		default: return "Unknown Zip64Mode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Zip64Mode value.
    /// </summary>
    public static String toString(/*Zip64Mode*/int zip64Mode)
    {
    	switch (zip64Mode)
    	{
    		case NEVER: return "Never";
    		case IF_NECESSARY: return "IfNecessary";
    		case ALWAYS: return "Always";
    		default: return "Unknown Zip64Mode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Zip64Mode name.
    /// </summary>
    public static /*Zip64Mode*/int fromName(String zip64ModeName)
    {
    	if ("NEVER".equals(zip64ModeName)) return NEVER;
    	if ("IF_NECESSARY".equals(zip64ModeName)) return IF_NECESSARY;
    	if ("ALWAYS".equals(zip64ModeName)) return ALWAYS;
    	throw new IllegalArgumentException("Unknown Zip64Mode name.");
    }
    
    /// <summary>
    /// Returns array of Zip64Mode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NEVER,
    		IF_NECESSARY,
    		ALWAYS,
    	};
    }

    public static final int length = 3;
}

