// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/08/2017 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies available options for leading space handling during import from <see cref="LoadFormat.Text"/> file.
/// </summary>
public /*enum*/ final class TxtLeadingSpacesOptions
{
    private TxtLeadingSpacesOptions(){}
    
    /// <summary>
    /// Leading spaces are removed and converted to left indent.
    /// </summary>
    public static final int CONVERT_TO_INDENT = 0;

    /// <summary>
    /// Leading spaces are trimmed
    /// </summary>
    public static final int TRIM = 1;

    /// <summary>
    /// Leading spaces are preserved. 
    /// </summary>
    public static final int PRESERVE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TxtLeadingSpacesOptions value.
    /// </summary>
    public static String getName(/*TxtLeadingSpacesOptions*/int txtLeadingSpacesOptions)
    {
    	switch (txtLeadingSpacesOptions)
    	{
    		case CONVERT_TO_INDENT: return "CONVERT_TO_INDENT";
    		case TRIM: return "TRIM";
    		case PRESERVE: return "PRESERVE";
    		default: return "Unknown TxtLeadingSpacesOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TxtLeadingSpacesOptions value.
    /// </summary>
    public static String toString(/*TxtLeadingSpacesOptions*/int txtLeadingSpacesOptions)
    {
    	switch (txtLeadingSpacesOptions)
    	{
    		case CONVERT_TO_INDENT: return "ConvertToIndent";
    		case TRIM: return "Trim";
    		case PRESERVE: return "Preserve";
    		default: return "Unknown TxtLeadingSpacesOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TxtLeadingSpacesOptions name.
    /// </summary>
    public static /*TxtLeadingSpacesOptions*/int fromName(String txtLeadingSpacesOptionsName)
    {
    	if ("CONVERT_TO_INDENT".equals(txtLeadingSpacesOptionsName)) return CONVERT_TO_INDENT;
    	if ("TRIM".equals(txtLeadingSpacesOptionsName)) return TRIM;
    	if ("PRESERVE".equals(txtLeadingSpacesOptionsName)) return PRESERVE;
    	throw new IllegalArgumentException("Unknown TxtLeadingSpacesOptions name.");
    }
    
    /// <summary>
    /// Returns array of TxtLeadingSpacesOptions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CONVERT_TO_INDENT,
    		TRIM,
    		PRESERVE,
    	};
    }

    public static final int length = 3;
}

