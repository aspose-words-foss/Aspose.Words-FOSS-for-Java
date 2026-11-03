// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2016 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies result type of header reading in <see cref="MhtmlFormatDetector"/> .
/// </summary>
/*enum*/ final class ReadHeaderResult
{
    private ReadHeaderResult(){}
    
    /// <summary>
    /// Result is successful.
    /// </summary>
    public static final int SUCCESS = 0;
    /// <summary>
    /// Result contains anything with illegal chars. 
    /// </summary>
    public static final int CONTAINS_ILLEGAL_CHARS = 1;
    /// <summary>
    /// Result contains only line break chars. 
    /// </summary>
    public static final int EMPTY_LINE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ReadHeaderResult value.
    /// </summary>
    public static String getName(/*ReadHeaderResult*/int readHeaderResult)
    {
    	switch (readHeaderResult)
    	{
    		case SUCCESS: return "SUCCESS";
    		case CONTAINS_ILLEGAL_CHARS: return "CONTAINS_ILLEGAL_CHARS";
    		case EMPTY_LINE: return "EMPTY_LINE";
    		default: return "Unknown ReadHeaderResult value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ReadHeaderResult value.
    /// </summary>
    public static String toString(/*ReadHeaderResult*/int readHeaderResult)
    {
    	switch (readHeaderResult)
    	{
    		case SUCCESS: return "Success";
    		case CONTAINS_ILLEGAL_CHARS: return "ContainsIllegalChars";
    		case EMPTY_LINE: return "EmptyLine";
    		default: return "Unknown ReadHeaderResult value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ReadHeaderResult name.
    /// </summary>
    public static /*ReadHeaderResult*/int fromName(String readHeaderResultName)
    {
    	if ("SUCCESS".equals(readHeaderResultName)) return SUCCESS;
    	if ("CONTAINS_ILLEGAL_CHARS".equals(readHeaderResultName)) return CONTAINS_ILLEGAL_CHARS;
    	if ("EMPTY_LINE".equals(readHeaderResultName)) return EMPTY_LINE;
    	throw new IllegalArgumentException("Unknown ReadHeaderResult name.");
    }
    
    /// <summary>
    /// Returns array of ReadHeaderResult values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SUCCESS,
    		CONTAINS_ILLEGAL_CHARS,
    		EMPTY_LINE,
    	};
    }

    public static final int length = 3;
}
