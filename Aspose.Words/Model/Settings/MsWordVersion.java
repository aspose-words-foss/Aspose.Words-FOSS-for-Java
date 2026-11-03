// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/12/2013 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows Aspose.Wods to mimic MS Word version-specific application behavior.
/// </summary>
public /*enum*/ final class MsWordVersion
{
    private MsWordVersion(){}
    
    /// <summary>
    /// Optimize Aspose.Words behavior to match MS Word 2000 version.
    /// </summary>
    public static final int WORD_2000 = 0;

    /// <summary>
    /// Optimize Aspose.Words behavior to match MS Word 2002 version.
    /// </summary>
    public static final int WORD_2002 = 1;

    /// <summary>
    /// Optimize Aspose.Words behavior to match MS Word 2003 version.
    /// </summary>
    public static final int WORD_2003 = 2;

    /// <summary>
    /// Optimize Aspose.Words behavior to match MS Word 2007 version.
    /// </summary>
    public static final int WORD_2007 = 3;

    /// <summary>
    /// Optimize Aspose.Words behavior to match MS Word 2010 version.
    /// </summary>
    public static final int WORD_2010 = 4;

    /// <summary>
    /// Optimize Aspose.Words behavior to match MS Word 2013 version.
    /// </summary>
    public static final int WORD_2013 = 5;

    /// <summary>
    /// Optimize Aspose.Words behavior to match MS Word 2016 version.
    /// </summary>
    public static final int WORD_2016 = 6;

    /// <summary>
    /// Optimize Aspose.Words behavior to match MS Word 2019 version.
    /// </summary>
    public static final int WORD_2019 = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MsWordVersion value.
    /// </summary>
    public static String getName(/*MsWordVersion*/int msWordVersion)
    {
    	switch (msWordVersion)
    	{
    		case WORD_2000: return "WORD_2000";
    		case WORD_2002: return "WORD_2002";
    		case WORD_2003: return "WORD_2003";
    		case WORD_2007: return "WORD_2007";
    		case WORD_2010: return "WORD_2010";
    		case WORD_2013: return "WORD_2013";
    		case WORD_2016: return "WORD_2016";
    		case WORD_2019: return "WORD_2019";
    		default: return "Unknown MsWordVersion value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MsWordVersion value.
    /// </summary>
    public static String toString(/*MsWordVersion*/int msWordVersion)
    {
    	switch (msWordVersion)
    	{
    		case WORD_2000: return "Word2000";
    		case WORD_2002: return "Word2002";
    		case WORD_2003: return "Word2003";
    		case WORD_2007: return "Word2007";
    		case WORD_2010: return "Word2010";
    		case WORD_2013: return "Word2013";
    		case WORD_2016: return "Word2016";
    		case WORD_2019: return "Word2019";
    		default: return "Unknown MsWordVersion value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MsWordVersion name.
    /// </summary>
    public static /*MsWordVersion*/int fromName(String msWordVersionName)
    {
    	if ("WORD_2000".equals(msWordVersionName)) return WORD_2000;
    	if ("WORD_2002".equals(msWordVersionName)) return WORD_2002;
    	if ("WORD_2003".equals(msWordVersionName)) return WORD_2003;
    	if ("WORD_2007".equals(msWordVersionName)) return WORD_2007;
    	if ("WORD_2010".equals(msWordVersionName)) return WORD_2010;
    	if ("WORD_2013".equals(msWordVersionName)) return WORD_2013;
    	if ("WORD_2016".equals(msWordVersionName)) return WORD_2016;
    	if ("WORD_2019".equals(msWordVersionName)) return WORD_2019;
    	throw new IllegalArgumentException("Unknown MsWordVersion name.");
    }
    
    /// <summary>
    /// Returns array of MsWordVersion values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		WORD_2000,
    		WORD_2002,
    		WORD_2003,
    		WORD_2007,
    		WORD_2010,
    		WORD_2013,
    		WORD_2016,
    		WORD_2019,
    	};
    }

    public static final int length = 8;
}

