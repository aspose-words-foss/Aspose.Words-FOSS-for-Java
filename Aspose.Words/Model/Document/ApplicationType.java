// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/07/2021 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Application family or office tools used to create a document.
/// </summary>
/*enum*/ final class ApplicationType
{
    private ApplicationType(){}
    
    /// <summary>
    /// The application is unknown.
    /// </summary>
    public static final int UNKNOWN = 0;

    /// <summary>
    /// Microsoft Office Word.
    /// </summary>
    public static final int MS_WORD = 1;

    /// <summary>
    /// WPS Office or WPS Writer.
    /// </summary>
    public static final int WPS_OFFICE = 2;

    /// <summary>
    /// Open Office Writer.
    /// </summary>
    public static final int OPEN_OFFICE_WRITER = 3;

    /// <summary>
    /// Microsoft Works.
    /// </summary>
    public static final int MS_WORKS = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ApplicationType value.
    /// </summary>
    public static String getName(/*ApplicationType*/int applicationType)
    {
    	switch (applicationType)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case MS_WORD: return "MS_WORD";
    		case WPS_OFFICE: return "WPS_OFFICE";
    		case OPEN_OFFICE_WRITER: return "OPEN_OFFICE_WRITER";
    		case MS_WORKS: return "MS_WORKS";
    		default: return "Unknown ApplicationType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ApplicationType value.
    /// </summary>
    public static String toString(/*ApplicationType*/int applicationType)
    {
    	switch (applicationType)
    	{
    		case UNKNOWN: return "Unknown";
    		case MS_WORD: return "MsWord";
    		case WPS_OFFICE: return "WpsOffice";
    		case OPEN_OFFICE_WRITER: return "OpenOfficeWriter";
    		case MS_WORKS: return "MsWorks";
    		default: return "Unknown ApplicationType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ApplicationType name.
    /// </summary>
    public static /*ApplicationType*/int fromName(String applicationTypeName)
    {
    	if ("UNKNOWN".equals(applicationTypeName)) return UNKNOWN;
    	if ("MS_WORD".equals(applicationTypeName)) return MS_WORD;
    	if ("WPS_OFFICE".equals(applicationTypeName)) return WPS_OFFICE;
    	if ("OPEN_OFFICE_WRITER".equals(applicationTypeName)) return OPEN_OFFICE_WRITER;
    	if ("MS_WORKS".equals(applicationTypeName)) return MS_WORKS;
    	throw new IllegalArgumentException("Unknown ApplicationType name.");
    }
    
    /// <summary>
    /// Returns array of ApplicationType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		MS_WORD,
    		WPS_OFFICE,
    		OPEN_OFFICE_WRITER,
    		MS_WORKS,
    	};
    }

    public static final int length = 5;
}

