// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how text is wrapped around the table.
/// </summary>
public /*enum*/ final class TextWrapping
{
    private TextWrapping(){}
    
    /// <summary>
    /// Text and table is displayed in the order of their appearance in the document.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Text is wrapped around the table occupying available side space.
    /// </summary>
    public static final int AROUND = 1;

    /// <summary>
    /// Default value.
    /// </summary>
    public static final int DEFAULT = NONE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextWrapping value.
    /// </summary>
    public static String getName(/*TextWrapping*/int textWrapping)
    {
    	switch (textWrapping)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case AROUND: return "AROUND";
    		default: return "Unknown TextWrapping value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextWrapping value.
    /// </summary>
    public static String toString(/*TextWrapping*/int textWrapping)
    {
    	switch (textWrapping)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case AROUND: return "Around";
    		default: return "Unknown TextWrapping value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextWrapping name.
    /// </summary>
    public static /*TextWrapping*/int fromName(String textWrappingName)
    {
    	if ("NONE".equals(textWrappingName)) return NONE;
    	if ("AROUND".equals(textWrappingName)) return AROUND;
    	if ("DEFAULT".equals(textWrappingName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown TextWrapping name.");
    }
    
    /// <summary>
    /// Returns array of TextWrapping values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		AROUND,
    		DEFAULT,
    	};
    }

    public static final int length = 3;
}

