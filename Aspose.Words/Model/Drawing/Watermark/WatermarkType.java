// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/22/2020 by Artem Ptitsin.

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the watermark type.
/// </summary>
public /*enum*/ final class WatermarkType
{
    private WatermarkType(){}
    
    /// <summary>
    /// Indicates that the text will be used as a watermark.
    /// <p>Such a watermark corresponds to a WordArt object.</p>
    /// </summary>
    public static final int TEXT = 0;

    /// <summary>
    /// Indicates that the image will be used as a watermark.
    /// <p>Such a watermark corresponds to a shape with image.</p>
    /// </summary>
    public static final int IMAGE = 1;

    /// <summary>
    /// Indicates watermark is no set.
    /// </summary>
    public static final int NONE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WatermarkType value.
    /// </summary>
    public static String getName(/*WatermarkType*/int watermarkType)
    {
    	switch (watermarkType)
    	{
    		case TEXT: return "TEXT";
    		case IMAGE: return "IMAGE";
    		case NONE: return "NONE";
    		default: return "Unknown WatermarkType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WatermarkType value.
    /// </summary>
    public static String toString(/*WatermarkType*/int watermarkType)
    {
    	switch (watermarkType)
    	{
    		case TEXT: return "Text";
    		case IMAGE: return "Image";
    		case NONE: return "None";
    		default: return "Unknown WatermarkType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WatermarkType name.
    /// </summary>
    public static /*WatermarkType*/int fromName(String watermarkTypeName)
    {
    	if ("TEXT".equals(watermarkTypeName)) return TEXT;
    	if ("IMAGE".equals(watermarkTypeName)) return IMAGE;
    	if ("NONE".equals(watermarkTypeName)) return NONE;
    	throw new IllegalArgumentException("Unknown WatermarkType name.");
    }
    
    /// <summary>
    /// Returns array of WatermarkType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT,
    		IMAGE,
    		NONE,
    	};
    }

    public static final int length = 3;
}

