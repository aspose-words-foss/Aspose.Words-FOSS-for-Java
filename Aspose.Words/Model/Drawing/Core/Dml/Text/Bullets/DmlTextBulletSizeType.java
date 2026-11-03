// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class DmlTextBulletSizeType
{
    private DmlTextBulletSizeType(){}
    
    public static final int FOLOW_TEXT = 0;
    public static final int POINTS = 1;
    public static final int PERCENTAGE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextBulletSizeType value.
    /// </summary>
    public static String getName(/*DmlTextBulletSizeType*/int dmlTextBulletSizeType)
    {
    	switch (dmlTextBulletSizeType)
    	{
    		case FOLOW_TEXT: return "FOLOW_TEXT";
    		case POINTS: return "POINTS";
    		case PERCENTAGE: return "PERCENTAGE";
    		default: return "Unknown DmlTextBulletSizeType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextBulletSizeType value.
    /// </summary>
    public static String toString(/*DmlTextBulletSizeType*/int dmlTextBulletSizeType)
    {
    	switch (dmlTextBulletSizeType)
    	{
    		case FOLOW_TEXT: return "FolowText";
    		case POINTS: return "Points";
    		case PERCENTAGE: return "Percentage";
    		default: return "Unknown DmlTextBulletSizeType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextBulletSizeType name.
    /// </summary>
    public static /*DmlTextBulletSizeType*/int fromName(String dmlTextBulletSizeTypeName)
    {
    	if ("FOLOW_TEXT".equals(dmlTextBulletSizeTypeName)) return FOLOW_TEXT;
    	if ("POINTS".equals(dmlTextBulletSizeTypeName)) return POINTS;
    	if ("PERCENTAGE".equals(dmlTextBulletSizeTypeName)) return PERCENTAGE;
    	throw new IllegalArgumentException("Unknown DmlTextBulletSizeType name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextBulletSizeType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FOLOW_TEXT,
    		POINTS,
    		PERCENTAGE,
    	};
    }

    public static final int length = 3;
}

