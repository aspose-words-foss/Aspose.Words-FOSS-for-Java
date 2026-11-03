// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of a list level.
/// </summary>
/*enum*/ final class HtmlModelListLevelType
{
    private HtmlModelListLevelType(){}
    
    /// <summary>
    /// List level with a bullet.
    /// </summary>
    public static final int BULLET = 0;

    /// <summary>
    /// List level with a number.
    /// </summary>
    public static final int NUMBER = 1;

    /// <summary>
    /// List level with a picture bullet.
    /// </summary>
    public static final int PICTURE_BULLET = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlModelListLevelType value.
    /// </summary>
    public static String getName(/*HtmlModelListLevelType*/int htmlModelListLevelType)
    {
    	switch (htmlModelListLevelType)
    	{
    		case BULLET: return "BULLET";
    		case NUMBER: return "NUMBER";
    		case PICTURE_BULLET: return "PICTURE_BULLET";
    		default: return "Unknown HtmlModelListLevelType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlModelListLevelType value.
    /// </summary>
    public static String toString(/*HtmlModelListLevelType*/int htmlModelListLevelType)
    {
    	switch (htmlModelListLevelType)
    	{
    		case BULLET: return "Bullet";
    		case NUMBER: return "Number";
    		case PICTURE_BULLET: return "PictureBullet";
    		default: return "Unknown HtmlModelListLevelType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlModelListLevelType name.
    /// </summary>
    public static /*HtmlModelListLevelType*/int fromName(String htmlModelListLevelTypeName)
    {
    	if ("BULLET".equals(htmlModelListLevelTypeName)) return BULLET;
    	if ("NUMBER".equals(htmlModelListLevelTypeName)) return NUMBER;
    	if ("PICTURE_BULLET".equals(htmlModelListLevelTypeName)) return PICTURE_BULLET;
    	throw new IllegalArgumentException("Unknown HtmlModelListLevelType name.");
    }
    
    /// <summary>
    /// Returns array of HtmlModelListLevelType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BULLET,
    		NUMBER,
    		PICTURE_BULLET,
    	};
    }

    public static final int length = 3;
}

