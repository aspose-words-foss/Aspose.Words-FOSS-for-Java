// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class PackageWhatFile
{
    private PackageWhatFile(){}
    
    public static final int OUT = 0;
    public static final int GOLD = 1;
    public static final int ORIGINAL_GOLD = 2;
    public static final int MS = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PackageWhatFile value.
    /// </summary>
    public static String getName(/*PackageWhatFile*/int packageWhatFile)
    {
    	switch (packageWhatFile)
    	{
    		case OUT: return "OUT";
    		case GOLD: return "GOLD";
    		case ORIGINAL_GOLD: return "ORIGINAL_GOLD";
    		case MS: return "MS";
    		default: return "Unknown PackageWhatFile value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PackageWhatFile value.
    /// </summary>
    public static String toString(/*PackageWhatFile*/int packageWhatFile)
    {
    	switch (packageWhatFile)
    	{
    		case OUT: return "Out";
    		case GOLD: return "Gold";
    		case ORIGINAL_GOLD: return "OriginalGold";
    		case MS: return "MS";
    		default: return "Unknown PackageWhatFile value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PackageWhatFile name.
    /// </summary>
    public static /*PackageWhatFile*/int fromName(String packageWhatFileName)
    {
    	if ("OUT".equals(packageWhatFileName)) return OUT;
    	if ("GOLD".equals(packageWhatFileName)) return GOLD;
    	if ("ORIGINAL_GOLD".equals(packageWhatFileName)) return ORIGINAL_GOLD;
    	if ("MS".equals(packageWhatFileName)) return MS;
    	throw new IllegalArgumentException("Unknown PackageWhatFile name.");
    }
    
    /// <summary>
    /// Returns array of PackageWhatFile values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OUT,
    		GOLD,
    		ORIGINAL_GOLD,
    		MS,
    	};
    }

    public static final int length = 4;
}

