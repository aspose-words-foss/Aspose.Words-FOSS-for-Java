// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/08/2017 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies available options for trailing spaces handling during import from <see cref="LoadFormat.Text"/> file.
/// </summary>
public /*enum*/ final class TxtTrailingSpacesOptions
{
    private TxtTrailingSpacesOptions(){}
    
    /// <summary>
    /// Trailing spaces are trimmed.
    /// </summary>
    public static final int TRIM = 0;

    /// <summary>
    ///  Trailing spaces are preserved.
    /// </summary>
    public static final int PRESERVE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TxtTrailingSpacesOptions value.
    /// </summary>
    public static String getName(/*TxtTrailingSpacesOptions*/int txtTrailingSpacesOptions)
    {
    	switch (txtTrailingSpacesOptions)
    	{
    		case TRIM: return "TRIM";
    		case PRESERVE: return "PRESERVE";
    		default: return "Unknown TxtTrailingSpacesOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TxtTrailingSpacesOptions value.
    /// </summary>
    public static String toString(/*TxtTrailingSpacesOptions*/int txtTrailingSpacesOptions)
    {
    	switch (txtTrailingSpacesOptions)
    	{
    		case TRIM: return "Trim";
    		case PRESERVE: return "Preserve";
    		default: return "Unknown TxtTrailingSpacesOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TxtTrailingSpacesOptions name.
    /// </summary>
    public static /*TxtTrailingSpacesOptions*/int fromName(String txtTrailingSpacesOptionsName)
    {
    	if ("TRIM".equals(txtTrailingSpacesOptionsName)) return TRIM;
    	if ("PRESERVE".equals(txtTrailingSpacesOptionsName)) return PRESERVE;
    	throw new IllegalArgumentException("Unknown TxtTrailingSpacesOptions name.");
    }
    
    /// <summary>
    /// Returns array of TxtTrailingSpacesOptions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TRIM,
    		PRESERVE,
    	};
    }

    public static final int length = 2;
}

