// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines the target screen size for a Save as Web Page operation.
/// Do not renumber these as they match values in DOC.
/// </summary>
/*enum*/ final class ScreenSize
{
    private ScreenSize(){}
    
    public static final int SIZE_544_X_376 = 0;
    public static final int SIZE_640_X_480 = 1;
    public static final int SIZE_720_X_512 = 2;
    public static final int SIZE_800_X_600 = 3;
    public static final int SIZE_1024_X_768 = 4;
    public static final int SIZE_1152_X_882 = 5;
    public static final int SIZE_1152_X_900 = 6;
    public static final int SIZE_1280_X_1024 = 7;
    public static final int SIZE_1600_X_1200 = 8;
    public static final int SIZE_1800_X_1440 = 9;
    public static final int SIZE_1920_X_1200 = 10;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ScreenSize value.
    /// </summary>
    public static String getName(/*ScreenSize*/int screenSize)
    {
    	switch (screenSize)
    	{
    		case SIZE_544_X_376: return "SIZE_544_X_376";
    		case SIZE_640_X_480: return "SIZE_640_X_480";
    		case SIZE_720_X_512: return "SIZE_720_X_512";
    		case SIZE_800_X_600: return "SIZE_800_X_600";
    		case SIZE_1024_X_768: return "SIZE_1024_X_768";
    		case SIZE_1152_X_882: return "SIZE_1152_X_882";
    		case SIZE_1152_X_900: return "SIZE_1152_X_900";
    		case SIZE_1280_X_1024: return "SIZE_1280_X_1024";
    		case SIZE_1600_X_1200: return "SIZE_1600_X_1200";
    		case SIZE_1800_X_1440: return "SIZE_1800_X_1440";
    		case SIZE_1920_X_1200: return "SIZE_1920_X_1200";
    		default: return "Unknown ScreenSize value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ScreenSize value.
    /// </summary>
    public static String toString(/*ScreenSize*/int screenSize)
    {
    	switch (screenSize)
    	{
    		case SIZE_544_X_376: return "Size544x376";
    		case SIZE_640_X_480: return "Size640x480";
    		case SIZE_720_X_512: return "Size720x512";
    		case SIZE_800_X_600: return "Size800x600";
    		case SIZE_1024_X_768: return "Size1024x768";
    		case SIZE_1152_X_882: return "Size1152x882";
    		case SIZE_1152_X_900: return "Size1152x900";
    		case SIZE_1280_X_1024: return "Size1280x1024";
    		case SIZE_1600_X_1200: return "Size1600x1200";
    		case SIZE_1800_X_1440: return "Size1800x1440";
    		case SIZE_1920_X_1200: return "Size1920x1200";
    		default: return "Unknown ScreenSize value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ScreenSize name.
    /// </summary>
    public static /*ScreenSize*/int fromName(String screenSizeName)
    {
    	if ("SIZE_544_X_376".equals(screenSizeName)) return SIZE_544_X_376;
    	if ("SIZE_640_X_480".equals(screenSizeName)) return SIZE_640_X_480;
    	if ("SIZE_720_X_512".equals(screenSizeName)) return SIZE_720_X_512;
    	if ("SIZE_800_X_600".equals(screenSizeName)) return SIZE_800_X_600;
    	if ("SIZE_1024_X_768".equals(screenSizeName)) return SIZE_1024_X_768;
    	if ("SIZE_1152_X_882".equals(screenSizeName)) return SIZE_1152_X_882;
    	if ("SIZE_1152_X_900".equals(screenSizeName)) return SIZE_1152_X_900;
    	if ("SIZE_1280_X_1024".equals(screenSizeName)) return SIZE_1280_X_1024;
    	if ("SIZE_1600_X_1200".equals(screenSizeName)) return SIZE_1600_X_1200;
    	if ("SIZE_1800_X_1440".equals(screenSizeName)) return SIZE_1800_X_1440;
    	if ("SIZE_1920_X_1200".equals(screenSizeName)) return SIZE_1920_X_1200;
    	throw new IllegalArgumentException("Unknown ScreenSize name.");
    }
    
    /// <summary>
    /// Returns array of ScreenSize values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SIZE_544_X_376,
    		SIZE_640_X_480,
    		SIZE_720_X_512,
    		SIZE_800_X_600,
    		SIZE_1024_X_768,
    		SIZE_1152_X_882,
    		SIZE_1152_X_900,
    		SIZE_1280_X_1024,
    		SIZE_1600_X_1200,
    		SIZE_1800_X_1440,
    		SIZE_1920_X_1200,
    	};
    }

    public static final int length = 11;
}

