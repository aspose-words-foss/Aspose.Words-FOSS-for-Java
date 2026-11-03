// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/06/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the operating system. This enum exists because PlatformID available on .NET is confusing and not enough for our purposes.
/// </summary>
public /*enum*/ final class Platform
{
    private Platform(){}
    
    /// <summary>
    /// Any Windows OS.
    /// </summary>
    public static final int WINDOWS = 0;
    /// <summary>
    /// Any Unix or Linux OS.
    /// </summary>
    public static final int UNIX = 1;
    /// <summary>
    /// Mac OS X.
    /// </summary>
    public static final int MAC_OS = 2;

    /// <summary>
    /// Android.
    /// </summary>
    public static final int ANDROID = 3;

    /// <summary>
    /// Apple iOS.
    /// </summary>
    public static final int I_OS = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Platform value.
    /// </summary>
    public static String getName(/*Platform*/int platform)
    {
    	switch (platform)
    	{
    		case WINDOWS: return "WINDOWS";
    		case UNIX: return "UNIX";
    		case MAC_OS: return "MAC_OS";
    		case ANDROID: return "ANDROID";
    		case I_OS: return "I_OS";
    		default: return "Unknown Platform value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Platform value.
    /// </summary>
    public static String toString(/*Platform*/int platform)
    {
    	switch (platform)
    	{
    		case WINDOWS: return "Windows";
    		case UNIX: return "Unix";
    		case MAC_OS: return "MacOS";
    		case ANDROID: return "Android";
    		case I_OS: return "iOS";
    		default: return "Unknown Platform value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Platform name.
    /// </summary>
    public static /*Platform*/int fromName(String platformName)
    {
    	if ("WINDOWS".equals(platformName)) return WINDOWS;
    	if ("UNIX".equals(platformName)) return UNIX;
    	if ("MAC_OS".equals(platformName)) return MAC_OS;
    	if ("ANDROID".equals(platformName)) return ANDROID;
    	if ("I_OS".equals(platformName)) return I_OS;
    	throw new IllegalArgumentException("Unknown Platform name.");
    }
    
    /// <summary>
    /// Returns array of Platform values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		WINDOWS,
    		UNIX,
    		MAC_OS,
    		ANDROID,
    		I_OS,
    	};
    }

    public static final int length = 5;
}

