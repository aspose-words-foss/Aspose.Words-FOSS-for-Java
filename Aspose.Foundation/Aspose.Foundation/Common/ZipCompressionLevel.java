// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/05/2020 by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines the method to be used for inflation and deflation.
/// </summary>
public /*enum*/ final class ZipCompressionLevel
{
    private ZipCompressionLevel(){}
    
    /// <summary>
    /// None means that the data will be simply stored, with no change at all.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Same as None.
    /// </summary>
    public static final int LEVEL_0 = 0;

    /// <summary>
    /// The fastest but least effective compression.
    /// </summary>
    public static final int BEST_SPEED = 1;

    /// <summary>
    /// A synonym for BestSpeed.
    /// </summary>
    public static final int LEVEL_1 = 1;

    /// <summary>
    /// A little slower, but better, than level 1.
    /// </summary>
    public static final int LEVEL_2 = 2;

    /// <summary>
    /// A little slower, but better, than level 2.
    /// </summary>
    public static final int LEVEL_3 = 3;

    /// <summary>
    /// A little slower, but better, than level 3.
    /// </summary>
    public static final int LEVEL_4 = 4;

    /// <summary>
    /// A little slower than level 4, but with better compression.
    /// </summary>
    public static final int LEVEL_5 = 5;

    /// <summary>
    /// The default compression level, with a good balance of speed and compression efficiency.   
    /// </summary>
    public static final int DEFAULT = 6;
    /// <summary>
    /// A synonym for Default.
    /// </summary>
    public static final int LEVEL_6 = 6;

    /// <summary>
    /// Pretty good compression!
    /// </summary>
    public static final int LEVEL_7 = 7;

    /// <summary>
    ///  Better compression than Level7!
    /// </summary>
    public static final int LEVEL_8 = 8;

    /// <summary>
    /// The "best" compression, where best means greatest reduction in size of the input data stream. 
    /// This is also the slowest compression.
    /// </summary>
    public static final int BEST_COMPRESSION = 9;

    /// <summary>
    /// A synonym for BestCompression.
    /// </summary>
    public static final int LEVEL_9 = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ZipCompressionLevel value.
    /// </summary>
    public static String getName(/*ZipCompressionLevel*/int zipCompressionLevel)
    {
    	switch (zipCompressionLevel)
    	{
    		case NONE:
    		/*case LEVEL_0:*/
    			return "NONE" + " | " + "LEVEL_0";
    		case BEST_SPEED:
    		/*case LEVEL_1:*/
    			return "BEST_SPEED" + " | " + "LEVEL_1";
    		case LEVEL_2: return "LEVEL_2";
    		case LEVEL_3: return "LEVEL_3";
    		case LEVEL_4: return "LEVEL_4";
    		case LEVEL_5: return "LEVEL_5";
    		case DEFAULT:
    		/*case LEVEL_6:*/
    			return "DEFAULT" + " | " + "LEVEL_6";
    		case LEVEL_7: return "LEVEL_7";
    		case LEVEL_8: return "LEVEL_8";
    		case BEST_COMPRESSION:
    		/*case LEVEL_9:*/
    			return "BEST_COMPRESSION" + " | " + "LEVEL_9";
    		default: return "Unknown ZipCompressionLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ZipCompressionLevel value.
    /// </summary>
    public static String toString(/*ZipCompressionLevel*/int zipCompressionLevel)
    {
    	switch (zipCompressionLevel)
    	{
    		case NONE:
    		/*case LEVEL_0:*/
    			return "None" + " | " + "Level0";
    		case BEST_SPEED:
    		/*case LEVEL_1:*/
    			return "BestSpeed" + " | " + "Level1";
    		case LEVEL_2: return "Level2";
    		case LEVEL_3: return "Level3";
    		case LEVEL_4: return "Level4";
    		case LEVEL_5: return "Level5";
    		case DEFAULT:
    		/*case LEVEL_6:*/
    			return "Default" + " | " + "Level6";
    		case LEVEL_7: return "Level7";
    		case LEVEL_8: return "Level8";
    		case BEST_COMPRESSION:
    		/*case LEVEL_9:*/
    			return "BestCompression" + " | " + "Level9";
    		default: return "Unknown ZipCompressionLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ZipCompressionLevel name.
    /// </summary>
    public static /*ZipCompressionLevel*/int fromName(String zipCompressionLevelName)
    {
    	if ("NONE".equals(zipCompressionLevelName)) return NONE;
    	if ("LEVEL_0".equals(zipCompressionLevelName)) return LEVEL_0;
    	if ("BEST_SPEED".equals(zipCompressionLevelName)) return BEST_SPEED;
    	if ("LEVEL_1".equals(zipCompressionLevelName)) return LEVEL_1;
    	if ("LEVEL_2".equals(zipCompressionLevelName)) return LEVEL_2;
    	if ("LEVEL_3".equals(zipCompressionLevelName)) return LEVEL_3;
    	if ("LEVEL_4".equals(zipCompressionLevelName)) return LEVEL_4;
    	if ("LEVEL_5".equals(zipCompressionLevelName)) return LEVEL_5;
    	if ("DEFAULT".equals(zipCompressionLevelName)) return DEFAULT;
    	if ("LEVEL_6".equals(zipCompressionLevelName)) return LEVEL_6;
    	if ("LEVEL_7".equals(zipCompressionLevelName)) return LEVEL_7;
    	if ("LEVEL_8".equals(zipCompressionLevelName)) return LEVEL_8;
    	if ("BEST_COMPRESSION".equals(zipCompressionLevelName)) return BEST_COMPRESSION;
    	if ("LEVEL_9".equals(zipCompressionLevelName)) return LEVEL_9;
    	throw new IllegalArgumentException("Unknown ZipCompressionLevel name.");
    }
    
    /// <summary>
    /// Returns array of ZipCompressionLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		LEVEL_0,
    		BEST_SPEED,
    		LEVEL_1,
    		LEVEL_2,
    		LEVEL_3,
    		LEVEL_4,
    		LEVEL_5,
    		DEFAULT,
    		LEVEL_6,
    		LEVEL_7,
    		LEVEL_8,
    		BEST_COMPRESSION,
    		LEVEL_9,
    	};
    }

    public static final int length = 14;
}

