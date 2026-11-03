// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/07/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether to verify only the exporter or both the exporter and importer against a gold file.
/// </summary>
public /*enum*/ final class GoldLevel
{
    private GoldLevel(){}
    
    /// <summary>
    /// We usually develop exporter first. Therefore this setting is used when developing and exporter.
    /// </summary>
    public static final int EXPORT_ONLY = 0;
    /// <summary>
    /// When both exporter and importer are ready, this setting must be used.
    /// </summary>
    public static final int EXPORT_IMPORT = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GoldLevel value.
    /// </summary>
    public static String getName(/*GoldLevel*/int goldLevel)
    {
    	switch (goldLevel)
    	{
    		case EXPORT_ONLY: return "EXPORT_ONLY";
    		case EXPORT_IMPORT: return "EXPORT_IMPORT";
    		default: return "Unknown GoldLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GoldLevel value.
    /// </summary>
    public static String toString(/*GoldLevel*/int goldLevel)
    {
    	switch (goldLevel)
    	{
    		case EXPORT_ONLY: return "ExportOnly";
    		case EXPORT_IMPORT: return "ExportImport";
    		default: return "Unknown GoldLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by GoldLevel name.
    /// </summary>
    public static /*GoldLevel*/int fromName(String goldLevelName)
    {
    	if ("EXPORT_ONLY".equals(goldLevelName)) return EXPORT_ONLY;
    	if ("EXPORT_IMPORT".equals(goldLevelName)) return EXPORT_IMPORT;
    	throw new IllegalArgumentException("Unknown GoldLevel name.");
    }
    
    /// <summary>
    /// Returns array of GoldLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EXPORT_ONLY,
    		EXPORT_IMPORT,
    	};
    }

    public static final int length = 2;
}
